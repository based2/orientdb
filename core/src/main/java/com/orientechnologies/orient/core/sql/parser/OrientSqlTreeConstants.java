/* Generated By:JavaCC: Do not edit this line. OrientSqlTreeConstants.java Version 5.0 */
package com.orientechnologies.orient.core.sql.parser;

public interface OrientSqlTreeConstants
{
  public int JJTRID = 0;
  public int JJTPARSE = 1;
  public int JJTIDENTIFIER = 2;
  public int JJTINTEGER = 3;
  public int JJTFLOATINGPOINT = 4;
  public int JJTNUMBER = 5;
  public int JJTSTATEMENT = 6;
  public int JJTQUERYSTATEMENT = 7;
  public int JJTSELECTWITHOUTTARGETSTATEMENT = 8;
  public int JJTSELECTSTATEMENT = 9;
  public int JJTTRAVERSESTATEMENT = 10;
  public int JJTDELETESTATEMENT = 11;
  public int JJTDELETEVERTEXSTATEMENT = 12;
  public int JJTDELETEEDGESTATEMENT = 13;
  public int JJTDELETEEDGEBYRIDSTATEMENT = 14;
  public int JJTDELETEEDGEFROMTOSTATEMENT = 15;
  public int JJTDELETEEDGETOSTATEMENT = 16;
  public int JJTDELETEEDGEVTOSTATEMENT = 17;
  public int JJTDELETEEDGEWHERESTATEMENT = 18;
  public int JJTUPDATESTATEMENT = 19;
  public int JJTUPDATEOPERATIONS = 20;
  public int JJTUPDATEITEM = 21;
  public int JJTUPDATEINCREMENTITEM = 22;
  public int JJTUPDATEREMOVEITEM = 23;
  public int JJTUPDATEPUTITEM = 24;
  public int JJTINSERTSTATEMENT = 25;
  public int JJTINSERTBODY = 26;
  public int JJTCREATEVERTEXSTATEMENTEMPTYNOTARGET = 27;
  public int JJTCREATEVERTEXSTATEMENTEMPTY = 28;
  public int JJTCREATEVERTEXSTATEMENT = 29;
  public int JJTCREATEVERTEXSTATEMENTNOTARGET = 30;
  public int JJTCREATEEDGESTATEMENT = 31;
  public int JJTINPUTPARAMETER = 32;
  public int JJTPOSITIONALPARAMETER = 33;
  public int JJTNAMEDPARAMETER = 34;
  public int JJTPROJECTION = 35;
  public int JJTPROJECTIONITEM = 36;
  public int JJTARRAYSELECTOR = 37;
  public int JJTARRAYNUMBERSELECTOR = 38;
  public int JJTARRAYSINGLEVALUESSELECTOR = 39;
  public int JJTARRAYRANGESELECTOR = 40;
  public int JJTALIAS = 41;
  public int JJTRECORDATTRIBUTE = 42;
  public int JJTFUNCTIONCALL = 43;
  public int JJTMETHODCALL = 44;
  public int JJTLEVELZEROIDENTIFIER = 45;
  public int JJTSUFFIXIDENTIFIER = 46;
  public int JJTBASEIDENTIFIER = 47;
  public int JJTMODIFIER = 48;
  public int JJTEXPRESSION = 49;
  public int JJTMATHEXPRESSION = 50;
  public int JJTMULTEXPRESSION = 51;
  public int JJTFIRSTLEVELEXPRESSION = 52;
  public int JJTPARENTHESISEXPRESSION = 53;
  public int JJTBASEEXPRESSION = 54;
  public int JJTFROMCLAUSE = 55;
  public int JJTLETCLAUSE = 56;
  public int JJTLETITEM = 57;
  public int JJTFROMITEM = 58;
  public int JJTCLUSTER = 59;
  public int JJTCLUSTERLIST = 60;
  public int JJTMETADATAIDENTIFIER = 61;
  public int JJTINDEXIDENTIFIER = 62;
  public int JJTWHERECLAUSE = 63;
  public int JJTORBLOCK = 64;
  public int JJTANDBLOCK = 65;
  public int JJTNOTBLOCK = 66;
  public int JJTPARENTHESISBLOCK = 67;
  public int JJTCONDITIONBLOCK = 68;
  public int JJTCOMPAREOPERATOR = 69;
  public int JJTLTOPERATOR = 70;
  public int JJTGTOPERATOR = 71;
  public int JJTNEOPERATOR = 72;
  public int JJTNEQOPERATOR = 73;
  public int JJTGEOPERATOR = 74;
  public int JJTLEOPERATOR = 75;
  public int JJTLIKEOPERATOR = 76;
  public int JJTLUCENEOPERATOR = 77;
  public int JJTNEAROPERATOR = 78;
  public int JJTWITHINOPERATOR = 79;
  public int JJTCONTAINSKEYOPERATOR = 80;
  public int JJTCONTAINSVALUEOPERATOR = 81;
  public int JJTEQUALSCOMPAREOPERATOR = 82;
  public int JJTBINARYCONDITION = 83;
  public int JJTCONTAINSVALUECONDITION = 84;
  public int JJTINSTANCEOFCONDITION = 85;
  public int JJTINDEXMATCHCONDITION = 86;
  public int JJTBETWEENCONDITION = 87;
  public int JJTISNULLCONDITION = 88;
  public int JJTISNOTNULLCONDITION = 89;
  public int JJTISDEFINEDCONDITION = 90;
  public int JJTISNOTDEFINEDCONDITION = 91;
  public int JJTCONTAINSCONDITION = 92;
  public int JJTINOPERATOR = 93;
  public int JJTINCONDITION = 94;
  public int JJTNOTINCONDITION = 95;
  public int JJTCONTAINSALLCONDITION = 96;
  public int JJTCONTAINSTEXTCONDITION = 97;
  public int JJTMATCHESCONDITION = 98;
  public int JJTORDERBY = 99;
  public int JJTGROUPBY = 100;
  public int JJTUNWIND = 101;
  public int JJTLIMIT = 102;
  public int JJTSKIP = 103;
  public int JJTBATCH = 104;
  public int JJTTIMEOUT = 105;
  public int JJTWAIT = 106;
  public int JJTRETRY = 107;
  public int JJTCOLLECTION = 108;
  public int JJTFETCHPLAN = 109;
  public int JJTFETCHPLANITEM = 110;
  public int JJTTRAVERSEPROJECTIONITEM = 111;
  public int JJTJSON = 112;
  public int JJTFINDREFERENCESSTATEMENT = 113;


  public String[] jjtNodeName = {
    "Rid",
    "parse",
    "Identifier",
    "Integer",
    "FloatingPoint",
    "Number",
    "Statement",
    "QueryStatement",
    "SelectWithoutTargetStatement",
    "SelectStatement",
    "TraverseStatement",
    "DeleteStatement",
    "DeleteVertexStatement",
    "DeleteEdgeStatement",
    "DeleteEdgeByRidStatement",
    "DeleteEdgeFromToStatement",
    "DeleteEdgeToStatement",
    "DeleteEdgeVToStatement",
    "DeleteEdgeWhereStatement",
    "UpdateStatement",
    "UpdateOperations",
    "UpdateItem",
    "UpdateIncrementItem",
    "UpdateRemoveItem",
    "UpdatePutItem",
    "InsertStatement",
    "InsertBody",
    "CreateVertexStatementEmptyNoTarget",
    "CreateVertexStatementEmpty",
    "CreateVertexStatement",
    "CreateVertexStatementNoTarget",
    "CreateEdgeStatement",
    "InputParameter",
    "PositionalParameter",
    "NamedParameter",
    "Projection",
    "ProjectionItem",
    "ArraySelector",
    "ArrayNumberSelector",
    "ArraySingleValuesSelector",
    "ArrayRangeSelector",
    "Alias",
    "RecordAttribute",
    "FunctionCall",
    "MethodCall",
    "LevelZeroIdentifier",
    "SuffixIdentifier",
    "BaseIdentifier",
    "Modifier",
    "Expression",
    "MathExpression",
    "MultExpression",
    "FirstLevelExpression",
    "ParenthesisExpression",
    "BaseExpression",
    "FromClause",
    "LetClause",
    "LetItem",
    "FromItem",
    "Cluster",
    "ClusterList",
    "MetadataIdentifier",
    "IndexIdentifier",
    "WhereClause",
    "OrBlock",
    "AndBlock",
    "NotBlock",
    "ParenthesisBlock",
    "ConditionBlock",
    "CompareOperator",
    "LtOperator",
    "GtOperator",
    "NeOperator",
    "NeqOperator",
    "GeOperator",
    "LeOperator",
    "LikeOperator",
    "LuceneOperator",
    "NearOperator",
    "WithinOperator",
    "ContainsKeyOperator",
    "ContainsValueOperator",
    "EqualsCompareOperator",
    "BinaryCondition",
    "ContainsValueCondition",
    "InstanceofCondition",
    "IndexMatchCondition",
    "BetweenCondition",
    "IsNullCondition",
    "IsNotNullCondition",
    "IsDefinedCondition",
    "IsNotDefinedCondition",
    "ContainsCondition",
    "InOperator",
    "InCondition",
    "NotInCondition",
    "ContainsAllCondition",
    "ContainsTextCondition",
    "MatchesCondition",
    "OrderBy",
    "GroupBy",
    "Unwind",
    "Limit",
    "Skip",
    "Batch",
    "Timeout",
    "Wait",
    "Retry",
    "Collection",
    "FetchPlan",
    "FetchPlanItem",
    "TraverseProjectionItem",
    "Json",
    "FindReferencesStatement",
  };
}
/* JavaCC - OriginalChecksum=6ae37143d416d8efb3f3e35f8842cf2b (do not edit this line) */