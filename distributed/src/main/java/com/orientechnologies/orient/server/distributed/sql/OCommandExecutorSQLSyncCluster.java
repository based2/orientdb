/*
 *
 *  *  Copyright 2014 Orient Technologies LTD (info(at)orientechnologies.com)
 *  *
 *  *  Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *  *
 *  * For more information: http://www.orientechnologies.com
 *
 */
package com.orientechnologies.orient.server.distributed.sql;

import com.orientechnologies.common.io.OFileUtils;
import com.orientechnologies.orient.core.command.OCommandDistributedReplicateRequest;
import com.orientechnologies.orient.core.command.OCommandRequest;
import com.orientechnologies.orient.core.command.OCommandRequestText;
import com.orientechnologies.orient.core.config.OGlobalConfiguration;
import com.orientechnologies.orient.core.db.ODatabaseDocumentInternal;
import com.orientechnologies.orient.core.exception.OCommandExecutionException;
import com.orientechnologies.orient.core.metadata.security.ORole;
import com.orientechnologies.orient.core.metadata.security.ORule;
import com.orientechnologies.orient.core.sql.OCommandExecutorSQLAbstract;
import com.orientechnologies.orient.core.sql.OCommandSQLParsingException;
import com.orientechnologies.orient.core.storage.impl.local.OAbstractPaginatedStorage;
import com.orientechnologies.orient.core.storage.impl.local.paginated.OPaginatedCluster;
import com.orientechnologies.orient.server.OServer;
import com.orientechnologies.orient.server.OServerMain;
import com.orientechnologies.orient.server.distributed.ODistributedConfiguration;
import com.orientechnologies.orient.server.distributed.ODistributedDatabaseChunk;
import com.orientechnologies.orient.server.distributed.ODistributedException;
import com.orientechnologies.orient.server.distributed.ODistributedRequest;
import com.orientechnologies.orient.server.distributed.ODistributedServerLog;
import com.orientechnologies.orient.server.distributed.task.OCopyDatabaseChunkTask;
import com.orientechnologies.orient.server.distributed.task.OSyncClusterTask;
import com.orientechnologies.orient.server.hazelcast.OHazelcastPlugin;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * SQL SYNC CLUSTER command: synchronize a cluster from distributed servers.
 * 
 * @author Luca Garulli
 * 
 */
@SuppressWarnings("unchecked")
public class OCommandExecutorSQLSyncCluster extends OCommandExecutorSQLAbstract implements OCommandDistributedReplicateRequest {
  public static final String    NAME            = "SYNC CLUSTER";
  public static final String    KEYWORD_SYNC    = "SYNC";
  public static final String    KEYWORD_CLUSTER = "CLUSTER";

  private String                clusterName;
  private OSyncClusterTask.MODE mode            = OSyncClusterTask.MODE.FULL_REPLACE;

  public OCommandExecutorSQLSyncCluster parse(final OCommandRequest iRequest) {
    init((OCommandRequestText) iRequest);

    final StringBuilder word = new StringBuilder();

    int oldPos = 0;
    int pos = nextWord(parserText, parserTextUpperCase, oldPos, word, true);
    if (pos == -1 || !word.toString().equals(KEYWORD_SYNC))
      throw new OCommandSQLParsingException("Keyword " + KEYWORD_SYNC + " not found. Use " + getSyntax(), parserText, oldPos);

    pos = nextWord(parserText, parserTextUpperCase, pos, word, true);
    if (pos == -1 || !word.toString().equals(KEYWORD_CLUSTER))
      throw new OCommandSQLParsingException("Keyword " + KEYWORD_CLUSTER + " not found. Use " + getSyntax(), parserText, oldPos);

    pos = nextWord(parserText, parserTextUpperCase, pos, word, false);
    if (pos == -1)
      throw new OCommandSQLParsingException("Expected <cluster>. Use " + getSyntax(), parserText, pos);

    clusterName = word.toString();
    if (clusterName == null)
      throw new OCommandSQLParsingException("Cluster is null. Use " + getSyntax(), parserText, pos);

    pos = nextWord(parserText, parserTextUpperCase, pos, word, true);
    if (pos > -1) {
      mode = OSyncClusterTask.MODE.valueOf(word.toString());
    }

    return this;
  }

  /**
   * Execute the SYNC CLUSTER.
   */
  public Object execute(final Map<Object, Object> iArgs) {
    final ODatabaseDocumentInternal database = getDatabase();
    database.checkSecurity(ORule.ResourceGeneric.CLUSTER, "sync", ORole.PERMISSION_UPDATE);

    final OServer serverInstance = OServerMain.server();
    final OHazelcastPlugin dManager = (OHazelcastPlugin) serverInstance.getDistributedManager();
    if (dManager == null || !dManager.isEnabled())
      throw new OCommandExecutionException("OrientDB is not started in distributed mode");

    final String nodeName = dManager.getLocalNodeName();
    final String databaseName = database.getName();

    final ODistributedConfiguration cfg = dManager.getDatabaseConfiguration(databaseName);

    switch (mode) {
    case FULL_REPLACE:
      final String dbPath = serverInstance.getDatabaseDirectory() + databaseName;

      final List<String> nodesWhereClusterIsCfg = cfg.getServers(clusterName, null);
      nodesWhereClusterIsCfg.remove(nodeName);

      if (nodesWhereClusterIsCfg.isEmpty())
        throw new OCommandExecutionException("Cannot synchronize cluster '" + clusterName
            + "' because is not configured on any running nodes");

      final OSyncClusterTask task = new OSyncClusterTask(clusterName);
      final Map<String, Object> results = (Map<String, Object>) dManager.sendRequest(databaseName, null, nodesWhereClusterIsCfg,
          task, ODistributedRequest.EXECUTION_MODE.RESPONSE);

      final OAbstractPaginatedStorage stg = (OAbstractPaginatedStorage) database.getStorage().getUnderlying();
      final OPaginatedCluster cluster = (OPaginatedCluster) stg.getClusterByName(clusterName);
      final String fileName = cluster.getFileName();

      FileOutputStream out = null;
      try {
        long fileSize = 0;
        out = new FileOutputStream(dbPath + "/" + fileName, false);
        for (Map.Entry<String, Object> r : results.entrySet()) {
          final Object value = r.getValue();

          if (value instanceof Boolean) {
            continue;
          } else if (value instanceof Throwable) {
            ODistributedServerLog.error(this, nodeName, r.getKey(), ODistributedServerLog.DIRECTION.IN,
                "error on installing cluster %s in %s", (Exception) value, databaseName, dbPath);
          } else if (value instanceof ODistributedDatabaseChunk) {
            ODistributedDatabaseChunk chunk = (ODistributedDatabaseChunk) value;

            stg.getWriteCache().close(cluster.getFileId(), false);

            fileSize = writeDatabaseChunk(nodeName, 1, chunk, out);
            for (int chunkNum = 2; !chunk.last; chunkNum++) {
              final Object result = dManager.sendRequest(databaseName, null, Collections.singleton(r.getKey()),
                  new OCopyDatabaseChunkTask(chunk.filePath, chunkNum, chunk.offset + chunk.buffer.length),
                  ODistributedRequest.EXECUTION_MODE.RESPONSE);

              if (result instanceof Boolean)
                continue;
              else if (result instanceof Exception) {
                ODistributedServerLog.error(this, nodeName, r.getKey(), ODistributedServerLog.DIRECTION.IN,
                    "error on installing database %s in %s (chunk #%d)", (Exception) result, databaseName, dbPath, chunkNum);
              } else if (result instanceof ODistributedDatabaseChunk) {
                chunk = (ODistributedDatabaseChunk) result;

                fileSize += writeDatabaseChunk(nodeName, chunkNum, chunk, out);
              }
            }
          }
        }
        return String.format("Cluster correctly replaced, transferred %d bytes", fileSize);

      } catch (Exception e) {
        ODistributedServerLog.error(this, nodeName, null, ODistributedServerLog.DIRECTION.NONE,
            "error on transferring database '%s' to '%s'", e, databaseName, fileName);
        throw new ODistributedException("Error on transferring database", e);
      } finally {
        try {
          if (out != null) {
            out.flush();
            out.close();
          }
        } catch (IOException e) {
        }
      }

    case MERGE:
      int merged = 0;
      return String.format("Merged %d records", merged);
    }

    return "Mode not supported";
  }

  @Override
  public DISTRIBUTED_EXECUTION_MODE getDistributedExecutionMode() {
    return DISTRIBUTED_EXECUTION_MODE.LOCAL;
  }

  @Override
  public long getTimeout() {
    return OGlobalConfiguration.DISTRIBUTED_DEPLOYDB_TASK_SYNCH_TIMEOUT.getValueAsLong();
  }

  @Override
  public QUORUM_TYPE getQuorumType() {
    return QUORUM_TYPE.ALL;
  }

  @Override
  public String getSyntax() {
    return "SYNC CLUSTER <name> [-full_replace|-merge]";
  }

  protected long writeDatabaseChunk(final String iNodeName, final int iChunkId, final ODistributedDatabaseChunk chunk,
      final FileOutputStream out) throws IOException {

    ODistributedServerLog.warn(this, iNodeName, null, ODistributedServerLog.DIRECTION.NONE,
        "- writing chunk #%d offset=%d size=%s", iChunkId, chunk.offset, OFileUtils.getSizeAsString(chunk.buffer.length));
    out.write(chunk.buffer);

    return chunk.buffer.length;
  }
}
