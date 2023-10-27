/*      */ package COM.ibm.eannounce.abr.util;
/*      */ 
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EANObject;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityItemException;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.GeneralAreaList;
/*      */ import COM.ibm.eannounce.objects.LockGroup;
/*      */ import COM.ibm.eannounce.objects.LockItem;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.eannounce.objects.ParentChildList;
/*      */ import COM.ibm.eannounce.objects.StatusAttribute;
/*      */ import COM.ibm.eannounce.objects.TextAttribute;
/*      */ import COM.ibm.opicmpdh.middleware.D;
/*      */ import COM.ibm.opicmpdh.middleware.Database;
/*      */ import COM.ibm.opicmpdh.middleware.DatePackage;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnStatus;
/*      */ import COM.ibm.opicmpdh.middleware.SortUtil;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask;
/*      */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*      */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*      */ import COM.ibm.opicmpdh.transactions.NLSItem;
/*      */ import COM.ibm.opicmpdh.transactions.OPICMABRItem;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.Method;
/*      */ import java.rmi.RemoteException;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Calendar;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class PokBaseABR
/*      */   extends AbstractTask
/*      */   implements PokABRMessages, PokLSExtRef, PokPSGExtRef
/*      */ {
/*      */   public static final String NEW_LINE = "\n";
/*      */   public static final int NONE = 0;
/*      */   public static final int PASS = 0;
/*      */   public static final int FAIL = -1;
/*      */   public static final int RESENDRFR = 1;
/*      */   public static final int RESENDCACHE = 2;
/*      */   public static final int UPDATE_ERROR = -2;
/*      */   public static final int INTERNAL_ERROR = -3;
/*      */   public static final String TOKEN_DELIMITER = "|";
/*      */   public static final String DEF_NOT_POPULATED_HTML = "<em>** Not Populated **</em>";
/*      */   public static final String GENREGION_US = "6197";
/*      */   public static final String GENREGION_EMEA = "6198";
/*      */   public static final String GENREGION_AP = "6199";
/*      */   public static final String GENREGION_CCN = "6200";
/*      */   public static final String GENREGION_LAD = "6204";
/*  981 */   public static final Hashtable c_hshDraft = new Hashtable<>();
/*  982 */   public static final Hashtable c_hshCancel = new Hashtable<>();
/*  983 */   public static final Hashtable c_hshFinal = new Hashtable<>();
/*  984 */   public static final Hashtable c_hshReadyRev = new Hashtable<>();
/*  985 */   public static final Hashtable c_hshChangeRev = new Hashtable<>();
/*  986 */   public static final Hashtable c_hshChangeFinal = new Hashtable<>();
/*  987 */   public static final Hashtable c_hshEntityStatus = new Hashtable<>();
/*  988 */   public static final Hashtable c_hshManagementGroupRelators = new Hashtable<>();
/*  989 */   public static final Hashtable c_hshManagementGroupEntities = new Hashtable<>();
/*  990 */   public static final Hashtable c_hshManagementChildrenGroupEntities = new Hashtable<>();
/*      */   
/*  992 */   public static final Hashtable c_hshSkipEntities = new Hashtable<>();
/*  993 */   public static final Hashtable c_hshDraftAll = new Hashtable<>();
/*  994 */   public static final Hashtable c_hshRFRAll = new Hashtable<>();
/*  995 */   public static final Hashtable c_hshCRRFRAll = new Hashtable<>();
/*  996 */   public static final Hashtable c_hshFinalAll = new Hashtable<>();
/*  997 */   public static final Hashtable c_hshCRFinalAll = new Hashtable<>();
/*      */   private static final int UNIQUE = 0;
/*      */   private static final int ONE_OR_MORE = 1;
/*      */   private static final int ONE_OR_LESS = 2;
/*      */   
/*      */   static {
/* 1003 */     c_hshDraftAll.put("ANCYCLESTATUS", "111");
/* 1004 */     c_hshDraftAll.put("COFCOFSTATUS", "111");
/* 1005 */     c_hshDraftAll.put("COFOOFSTATUS", "111");
/* 1006 */     c_hshDraftAll.put("OFSTATUS", "111");
/* 1007 */     c_hshDraftAll.put("FUPSTATUS", "111");
/* 1008 */     c_hshDraftAll.put("FUPFUPSTATUS", "111");
/* 1009 */     c_hshDraftAll.put("FUPPOFSTATUS", "6060");
/* 1010 */     c_hshDraftAll.put("OOFOOFSTATUS", "6085");
/* 1011 */     c_hshDraftAll.put("POFPOFSTATUS", "111");
/* 1012 */     c_hshDraftAll.put("TECHCAPSTATUS", "111");
/* 1013 */     c_hshDraftAll.put("AVAILSTATUS", "111");
/* 1014 */     c_hshDraftAll.put("BPESTATUS", "111");
/* 1015 */     c_hshDraftAll.put("CATINCSTATUS", "111");
/* 1016 */     c_hshDraftAll.put("CHANSTATUS", "111");
/* 1017 */     c_hshDraftAll.put("CRYPTOSTATUS", "111");
/* 1018 */     c_hshDraftAll.put("ENVISTATUS", "111");
/* 1019 */     c_hshDraftAll.put("IVOCATSTATUS", "111");
/* 1020 */     c_hshDraftAll.put("ORDINFSTATUS", "111");
/* 1021 */     c_hshDraftAll.put("OUSTATUS", "111");
/* 1022 */     c_hshDraftAll.put("PACKSTATUS", "111");
/* 1023 */     c_hshDraftAll.put("PRICESTATUS", "111");
/* 1024 */     c_hshDraftAll.put("PUBSSTATUS", "111");
/* 1025 */     c_hshDraftAll.put("RULESSTATUS", "111");
/* 1026 */     c_hshDraftAll.put("SHIPSTATUS", "111");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1032 */     c_hshRFRAll.put("COFCOFSTATUS", "114");
/* 1033 */     c_hshRFRAll.put("COFOOFSTATUS", "114");
/* 1034 */     c_hshRFRAll.put("OFSTATUS", "115");
/* 1035 */     c_hshRFRAll.put("FUPSTATUS", "114");
/* 1036 */     c_hshRFRAll.put("FUPFUPSTATUS", "114");
/* 1037 */     c_hshRFRAll.put("FUPPOFSTATUS", "6061");
/* 1038 */     c_hshRFRAll.put("OOFOOFSTATUS", "6087");
/* 1039 */     c_hshRFRAll.put("POFPOFSTATUS", "114");
/* 1040 */     c_hshRFRAll.put("TECHCAPSTATUS", "114");
/* 1041 */     c_hshRFRAll.put("AVAILSTATUS", "114");
/* 1042 */     c_hshRFRAll.put("BPESTATUS", "114");
/* 1043 */     c_hshRFRAll.put("CATINCSTATUS", "114");
/* 1044 */     c_hshRFRAll.put("CHANSTATUS", "114");
/* 1045 */     c_hshRFRAll.put("CRYPTOSTATUS", "114");
/* 1046 */     c_hshRFRAll.put("ENVISTATUS", "114");
/* 1047 */     c_hshRFRAll.put("IVOCATSTATUS", "114");
/* 1048 */     c_hshRFRAll.put("ORDINFSTATUS", "114");
/* 1049 */     c_hshRFRAll.put("OUSTATUS", "114");
/* 1050 */     c_hshRFRAll.put("PACKSTATUS", "114");
/* 1051 */     c_hshRFRAll.put("PRICESTATUS", "114");
/* 1052 */     c_hshRFRAll.put("PUBSSTATUS", "114");
/* 1053 */     c_hshRFRAll.put("RULESSTATUS", "114");
/* 1054 */     c_hshRFRAll.put("SHIPSTATUS", "114");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1060 */     c_hshCRRFRAll.put("COFCOFSTATUS", "115");
/* 1061 */     c_hshCRRFRAll.put("COFOOFSTATUS", "115");
/* 1062 */     c_hshCRRFRAll.put("OFSTATUS", "116");
/* 1063 */     c_hshCRRFRAll.put("FUPSTATUS", "115");
/* 1064 */     c_hshCRRFRAll.put("FUPFUPSTATUS", "115");
/* 1065 */     c_hshCRRFRAll.put("FUPPOFSTATUS", "6058");
/* 1066 */     c_hshCRRFRAll.put("OOFOOFSTATUS", "6084");
/* 1067 */     c_hshCRRFRAll.put("POFPOFSTATUS", "115");
/* 1068 */     c_hshCRRFRAll.put("TECHCAPSTATUS", "115");
/* 1069 */     c_hshCRRFRAll.put("AVAILSTATUS", "115");
/* 1070 */     c_hshCRRFRAll.put("BPESTATUS", "115");
/* 1071 */     c_hshCRRFRAll.put("CATINCSTATUS", "115");
/* 1072 */     c_hshCRRFRAll.put("CHANSTATUS", "115");
/* 1073 */     c_hshCRRFRAll.put("CRYPTOSTATUS", "115");
/* 1074 */     c_hshCRRFRAll.put("ENVISTATUS", "115");
/* 1075 */     c_hshCRRFRAll.put("IVOCATSTATUS", "115");
/* 1076 */     c_hshCRRFRAll.put("ORDINFSTATUS", "115");
/* 1077 */     c_hshCRRFRAll.put("OUSTATUS", "115");
/* 1078 */     c_hshCRRFRAll.put("PACKSTATUS", "115");
/* 1079 */     c_hshCRRFRAll.put("PRICESTATUS", "115");
/* 1080 */     c_hshCRRFRAll.put("PUBSSTATUS", "115");
/* 1081 */     c_hshCRRFRAll.put("RULESSTATUS", "115");
/* 1082 */     c_hshCRRFRAll.put("SHIPSTATUS", "115");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1088 */     c_hshFinalAll.put("COFCOFSTATUS", "112");
/* 1089 */     c_hshFinalAll.put("COFOOFSTATUS", "112");
/* 1090 */     c_hshFinalAll.put("OFSTATUS", "112");
/* 1091 */     c_hshFinalAll.put("FUPSTATUS", "112");
/* 1092 */     c_hshFinalAll.put("FUPFUPSTATUS", "112");
/* 1093 */     c_hshFinalAll.put("FUPPOFSTATUS", "6059");
/* 1094 */     c_hshFinalAll.put("OOFOOFSTATUS", "6086");
/* 1095 */     c_hshFinalAll.put("POFPOFSTATUS", "112");
/* 1096 */     c_hshFinalAll.put("TECHCAPSTATUS", "112");
/* 1097 */     c_hshFinalAll.put("AVAILSTATUS", "112");
/* 1098 */     c_hshFinalAll.put("BPESTATUS", "112");
/* 1099 */     c_hshFinalAll.put("CATINCSTATUS", "112");
/* 1100 */     c_hshFinalAll.put("CHANSTATUS", "112");
/* 1101 */     c_hshFinalAll.put("CRYPTOSTATUS", "112");
/* 1102 */     c_hshFinalAll.put("ENVISTATUS", "112");
/* 1103 */     c_hshFinalAll.put("IVOCATSTATUS", "112");
/* 1104 */     c_hshFinalAll.put("ORDINFSTATUS", "112");
/* 1105 */     c_hshFinalAll.put("OUSTATUS", "112");
/* 1106 */     c_hshFinalAll.put("PACKSTATUS", "112");
/* 1107 */     c_hshFinalAll.put("PRICESTATUS", "112");
/* 1108 */     c_hshFinalAll.put("PUBSSTATUS", "112");
/* 1109 */     c_hshFinalAll.put("RULESSTATUS", "112");
/* 1110 */     c_hshFinalAll.put("SHIPSTATUS", "112");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1116 */     c_hshCRFinalAll.put("COFCOFSTATUS", "116");
/* 1117 */     c_hshCRFinalAll.put("COFOOFSTATUS", "116");
/* 1118 */     c_hshCRFinalAll.put("OFSTATUS", "113");
/* 1119 */     c_hshCRFinalAll.put("FUPSTATUS", "116");
/* 1120 */     c_hshCRFinalAll.put("FUPFUPSTATUS", "116");
/* 1121 */     c_hshCRFinalAll.put("FUPPOFSTATUS", "6062");
/* 1122 */     c_hshCRFinalAll.put("OOFOOFSTATUS", "6083");
/* 1123 */     c_hshCRFinalAll.put("POFPOFSTATUS", "116");
/* 1124 */     c_hshCRFinalAll.put("TECHCAPSTATUS", "116");
/* 1125 */     c_hshCRFinalAll.put("AVAILSTATUS", "116");
/* 1126 */     c_hshCRFinalAll.put("BPESTATUS", "116");
/* 1127 */     c_hshCRFinalAll.put("CATINCSTATUS", "116");
/* 1128 */     c_hshCRFinalAll.put("CHANSTATUS", "116");
/* 1129 */     c_hshCRFinalAll.put("CRYPTOSTATUS", "116");
/* 1130 */     c_hshCRFinalAll.put("ENVISTATUS", "116");
/* 1131 */     c_hshCRFinalAll.put("IVOCATSTATUS", "116");
/* 1132 */     c_hshCRFinalAll.put("ORDINFSTATUS", "116");
/* 1133 */     c_hshCRFinalAll.put("OUSTATUS", "116");
/* 1134 */     c_hshCRFinalAll.put("PACKSTATUS", "116");
/* 1135 */     c_hshCRFinalAll.put("PRICESTATUS", "116");
/* 1136 */     c_hshCRFinalAll.put("PUBSSTATUS", "116");
/* 1137 */     c_hshCRFinalAll.put("RULESSTATUS", "116");
/* 1138 */     c_hshCRFinalAll.put("SHIPSTATUS", "116");
/*      */ 
/*      */     
/* 1141 */     c_hshSkipEntities.put("OP", "HI");
/*      */     
/* 1143 */     c_hshManagementGroupEntities.put("COFOOFMGMTGRP", "HI");
/* 1144 */     c_hshManagementGroupEntities.put("COFCOFMGMTGRP", "HI");
/* 1145 */     c_hshManagementGroupEntities.put("FUPFUPMGMTGRP", "HI");
/* 1146 */     c_hshManagementGroupEntities.put("FUPPOFMGMTGRP", "HI");
/* 1147 */     c_hshManagementGroupEntities.put("OOFOOFMGMTGRP", "HI");
/* 1148 */     c_hshManagementGroupEntities.put("POFPOFMGMTGRP", "HI");
/*      */     
/* 1150 */     c_hshManagementChildrenGroupEntities.put("COMMERCIALOF", "HI");
/* 1151 */     c_hshManagementChildrenGroupEntities.put("RULES", "HI");
/* 1152 */     c_hshManagementChildrenGroupEntities.put("AVAIL", "HI");
/* 1153 */     c_hshManagementChildrenGroupEntities.put("ORDEROF", "HI");
/* 1154 */     c_hshManagementChildrenGroupEntities.put("PHYSICALOF", "HI");
/* 1155 */     c_hshManagementChildrenGroupEntities.put("FUP", "HI");
/*      */     
/* 1157 */     c_hshEntityStatus.put("COFOOFMGMTGRP", "COFOOFSTATUS");
/* 1158 */     c_hshEntityStatus.put("COFCOFMGMTGRP", "COFCOFSTATUS");
/* 1159 */     c_hshEntityStatus.put("COMMERCIALOF", "OFSTATUS");
/* 1160 */     c_hshEntityStatus.put("FUP", "FUPSTATUS");
/* 1161 */     c_hshEntityStatus.put("FUPFUPMGMTGRP", "FUPFUPSTATUS");
/* 1162 */     c_hshEntityStatus.put("FUPPOFMGMTGRP", "FUPPOFSTATUS");
/* 1163 */     c_hshEntityStatus.put("OOFOOFMGMTGRP", "OOFOOFSTATUS");
/* 1164 */     c_hshEntityStatus.put("ORDEROF", "OFSTATUS");
/* 1165 */     c_hshEntityStatus.put("PHYSICALOF", "OFSTATUS");
/* 1166 */     c_hshEntityStatus.put("POFPOFMGMTGRP", "POFPOFSTATUS");
/* 1167 */     c_hshEntityStatus.put("TECHCAPABILITY", "TECHCAPSTATUS");
/* 1168 */     c_hshEntityStatus.put("AVAIL", "AVAILSTATUS");
/* 1169 */     c_hshEntityStatus.put("BPEXHIBIT", "BPESTATUS");
/* 1170 */     c_hshEntityStatus.put("CATINCL", "CATINCSTATUS");
/* 1171 */     c_hshEntityStatus.put("CHANNEL", "CHANSTATUS");
/* 1172 */     c_hshEntityStatus.put("CRYPTO", "CRYPTOSTATUS");
/* 1173 */     c_hshEntityStatus.put("ENVIRINFO", "ENVISTATUS");
/* 1174 */     c_hshEntityStatus.put("IVOCAT", "IVOCATSTATUS");
/* 1175 */     c_hshEntityStatus.put("ORDERINFO", "ORDINFSTATUS");
/* 1176 */     c_hshEntityStatus.put("ORGANUNIT", "OUSTATUS");
/* 1177 */     c_hshEntityStatus.put("PACKAGING", "PACKSTATUS");
/* 1178 */     c_hshEntityStatus.put("PRICEFININFO", "PRICESTATUS");
/* 1179 */     c_hshEntityStatus.put("PUBLICATION", "PUBSSTATUS");
/* 1180 */     c_hshEntityStatus.put("RULES", "RULESSTATUS");
/* 1181 */     c_hshEntityStatus.put("SHIPINFO", "SHIPSTATUS");
/* 1182 */     c_hshEntityStatus.put("ANNAREAMKTINFO", "ANAMISTATUS");
/* 1183 */     c_hshEntityStatus.put("ANNDELIVERABLE", "ANDELIVSTATUS");
/* 1184 */     c_hshEntityStatus.put("ANNDESCAREA", "ANDESCASTATUS");
/* 1185 */     c_hshEntityStatus.put("ANNOUNCEMENT", "ANCYCLESTATUS");
/* 1186 */     c_hshEntityStatus.put("CONFIGURATOR", "CONFSTATUS");
/* 1187 */     c_hshEntityStatus.put("EDUCATION", "EDSTATUS");
/* 1188 */     c_hshEntityStatus.put("PDSQUESTIONS", "PDSQSTATUS");
/* 1189 */     c_hshEntityStatus.put("SALESMANCHG", "SALESMANSTATUS");
/* 1190 */     c_hshEntityStatus.put("STANDAMENDTEXT", "SATSTATUS");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1195 */     c_hshDraft.put("OFSTATUS:111", "115");
/* 1196 */     c_hshCancel.put("OFSTATUS:114", "NA");
/* 1197 */     c_hshFinal.put("OFSTATUS:112", "NA");
/* 1198 */     c_hshReadyRev.put("OFSTATUS:115", "112");
/* 1199 */     c_hshChangeRev.put("OFSTATUS:116", "115");
/* 1200 */     c_hshChangeFinal.put("OFSTATUS:113", "112");
/*      */ 
/*      */ 
/*      */     
/* 1204 */     c_hshDraft.put("OOFOOFSTATUS:6085", "6087");
/* 1205 */     c_hshCancel.put("OOFOOFSTATUS:0000", "NA");
/* 1206 */     c_hshFinal.put("OOFOOFSTATUS:6086", "NA");
/* 1207 */     c_hshReadyRev.put("OOFOOFSTATUS:6087", "6086");
/* 1208 */     c_hshChangeRev.put("OOFOOFSTATUS:6084", "6087");
/* 1209 */     c_hshChangeFinal.put("OOFOOFSTATUS:6083", "6086");
/*      */ 
/*      */ 
/*      */     
/* 1213 */     c_hshDraft.put("SHIPSTATUS:111", "114");
/* 1214 */     c_hshCancel.put("SHIPSTATUS:000", "NA");
/* 1215 */     c_hshFinal.put("SHIPSTATUS:112", "NA");
/* 1216 */     c_hshReadyRev.put("SHIPSTATUS:114", "112");
/* 1217 */     c_hshChangeRev.put("SHIPSTATUS:115", "114");
/* 1218 */     c_hshChangeFinal.put("SHIPSTATUS:116", "112");
/*      */ 
/*      */ 
/*      */     
/* 1222 */     c_hshDraft.put("RULESSTATUS:111", "114");
/* 1223 */     c_hshCancel.put("RULESSTATUS:000", "NA");
/* 1224 */     c_hshFinal.put("RULESSTATUS:112", "NA");
/* 1225 */     c_hshReadyRev.put("RULESSTATUS:114", "112");
/* 1226 */     c_hshChangeRev.put("RULESSTATUS:115", "114");
/* 1227 */     c_hshChangeFinal.put("RULESSTATUS:116", "112");
/*      */ 
/*      */ 
/*      */     
/* 1231 */     c_hshDraft.put("PUBSSTATUS:111", "114");
/* 1232 */     c_hshCancel.put("PUBSSTATUS:000", "NA");
/* 1233 */     c_hshFinal.put("PUBSSTATUS:112", "NA");
/* 1234 */     c_hshReadyRev.put("PUBSSTATUS:114", "112");
/* 1235 */     c_hshChangeRev.put("PUBSSTATUS:115", "114");
/* 1236 */     c_hshChangeFinal.put("PUBSSTATUS:116", "112");
/*      */ 
/*      */ 
/*      */     
/* 1240 */     c_hshDraft.put("PRICESTATUS:111", "114");
/* 1241 */     c_hshCancel.put("PRICESTATUS:000", "NA");
/* 1242 */     c_hshFinal.put("PRICESTATUS:112", "NA");
/* 1243 */     c_hshReadyRev.put("PRICESTATUS:114", "112");
/* 1244 */     c_hshChangeRev.put("PRICESTATUS:115", "114");
/* 1245 */     c_hshChangeFinal.put("PRICESTATUS:116", "112");
/*      */ 
/*      */ 
/*      */     
/* 1249 */     c_hshDraft.put("PACKSTATUS:111", "114");
/* 1250 */     c_hshCancel.put("PACKSTATUS:000", "NA");
/* 1251 */     c_hshFinal.put("PACKSTATUS:112", "NA");
/* 1252 */     c_hshReadyRev.put("PACKSTATUS:114", "112");
/* 1253 */     c_hshChangeRev.put("PACKSTATUS:115", "114");
/* 1254 */     c_hshChangeFinal.put("PACKSTATUS:116", "112");
/*      */ 
/*      */ 
/*      */     
/* 1258 */     c_hshDraft.put("OUSTATUS:111", "114");
/* 1259 */     c_hshCancel.put("OUSTATUS:000", "NA");
/* 1260 */     c_hshFinal.put("OUSTATUS:112", "NA");
/* 1261 */     c_hshReadyRev.put("OUSTATUS:114", "112");
/* 1262 */     c_hshChangeRev.put("OUSTATUS:115", "114");
/* 1263 */     c_hshChangeFinal.put("OUSTATUS:116", "112");
/*      */ 
/*      */ 
/*      */     
/* 1267 */     c_hshDraft.put("ORDINFSTATUS:111", "114");
/* 1268 */     c_hshCancel.put("ORDINFSTATUS:000", "NA");
/* 1269 */     c_hshFinal.put("ORDINFSTATUS:112", "NA");
/* 1270 */     c_hshReadyRev.put("ORDINFSTATUS:114", "112");
/* 1271 */     c_hshChangeRev.put("ORDINFSTATUS:115", "114");
/* 1272 */     c_hshChangeFinal.put("ORDINFSTATUS:116", "112");
/*      */ 
/*      */ 
/*      */     
/* 1276 */     c_hshDraft.put("IVOCATSTATUS:111", "114");
/* 1277 */     c_hshCancel.put("IVOCATSTATUS:000", "NA");
/* 1278 */     c_hshFinal.put("IVOCATSTATUS:112", "NA");
/* 1279 */     c_hshReadyRev.put("IVOCATSTATUS:114", "112");
/* 1280 */     c_hshChangeRev.put("IVOCATSTATUS:115", "114");
/* 1281 */     c_hshChangeFinal.put("IVOCATSTATUS:116", "112");
/*      */ 
/*      */ 
/*      */     
/* 1285 */     c_hshDraft.put("ENVISTATUS:111", "114");
/* 1286 */     c_hshCancel.put("ENVISTATUS:000", "NA");
/* 1287 */     c_hshFinal.put("ENVISTATUS:112", "NA");
/* 1288 */     c_hshReadyRev.put("ENVISTATUS:114", "112");
/* 1289 */     c_hshChangeRev.put("ENVISTATUS:115", "114");
/* 1290 */     c_hshChangeFinal.put("ENVISTATUS:116", "112");
/*      */ 
/*      */ 
/*      */     
/* 1294 */     c_hshDraft.put("CRYPTOSTATUS:111", "114");
/* 1295 */     c_hshCancel.put("CRYPTOSTATUS:000", "NA");
/* 1296 */     c_hshFinal.put("CRYPTOSTATUS:112", "NA");
/* 1297 */     c_hshReadyRev.put("CRYPTOSTATUS:114", "112");
/* 1298 */     c_hshChangeRev.put("CRYPTOSTATUS:115", "114");
/* 1299 */     c_hshChangeFinal.put("CRYPTOSTATUS:116", "112");
/*      */ 
/*      */ 
/*      */     
/* 1303 */     c_hshDraft.put("CHANSTATUS:111", "114");
/* 1304 */     c_hshCancel.put("CHANSTATUS:000", "NA");
/* 1305 */     c_hshFinal.put("CHANSTATUS:112", "NA");
/* 1306 */     c_hshReadyRev.put("CHANSTATUS:114", "112");
/* 1307 */     c_hshChangeRev.put("CHANSTATUS:115", "114");
/* 1308 */     c_hshChangeFinal.put("CHANSTATUS:116", "112");
/*      */ 
/*      */ 
/*      */     
/* 1312 */     c_hshDraft.put("CATINCSTATUS:111", "114");
/* 1313 */     c_hshCancel.put("CATINCSTATUS:000", "NA");
/* 1314 */     c_hshFinal.put("CATINCSTATUS:112", "NA");
/* 1315 */     c_hshReadyRev.put("CATINCSTATUS:114", "112");
/* 1316 */     c_hshChangeRev.put("CATINCSTATUS:115", "114");
/* 1317 */     c_hshChangeFinal.put("CATINCSTATUS:116", "112");
/*      */ 
/*      */ 
/*      */     
/* 1321 */     c_hshDraft.put("BPESTATUS:111", "114");
/* 1322 */     c_hshCancel.put("BPESTATUS:000", "NA");
/* 1323 */     c_hshFinal.put("BPESTATUS:112", "NA");
/* 1324 */     c_hshReadyRev.put("BPESTATUS:114", "112");
/* 1325 */     c_hshChangeRev.put("BPESTATUS:115", "114");
/* 1326 */     c_hshChangeFinal.put("BPESTATUS:116", "112");
/*      */ 
/*      */ 
/*      */     
/* 1330 */     c_hshDraft.put("AVAILSTATUS:111", "114");
/* 1331 */     c_hshCancel.put("AVAILSTATUS:000", "NA");
/* 1332 */     c_hshFinal.put("AVAILSTATUS:112", "NA");
/* 1333 */     c_hshReadyRev.put("AVAILSTATUS:114", "112");
/* 1334 */     c_hshChangeRev.put("AVAILSTATUS:115", "114");
/* 1335 */     c_hshChangeFinal.put("AVAILSTATUS:116", "112");
/*      */ 
/*      */     
/* 1338 */     c_hshDraft.put("POFPOFSTATUS:111", "114");
/* 1339 */     c_hshCancel.put("POFPOFSTATUS:0000", "NA");
/* 1340 */     c_hshFinal.put("POFPOFSTATUS:112", "NA");
/* 1341 */     c_hshReadyRev.put("POFPOFSTATUS:114", "112");
/* 1342 */     c_hshChangeRev.put("POFPOFSTATUS:115", "114");
/* 1343 */     c_hshChangeFinal.put("POFPOFSTATUS:116", "112");
/*      */ 
/*      */     
/* 1346 */     c_hshDraft.put("TECHCAPSTATUS:111", "114");
/* 1347 */     c_hshCancel.put("TECHCAPSTATUS:0000", "NA");
/* 1348 */     c_hshFinal.put("TECHCAPSTATUS:112", "NA");
/* 1349 */     c_hshReadyRev.put("TECHCAPSTATUS:114", "112");
/* 1350 */     c_hshChangeRev.put("TECHCAPSTATUS:115", "114");
/* 1351 */     c_hshChangeFinal.put("TECHCAPSTATUS:116", "112");
/*      */ 
/*      */     
/* 1354 */     c_hshDraft.put("FUPSTATUS:111", "114");
/* 1355 */     c_hshCancel.put("FUPSTATUS:0000", "NA");
/* 1356 */     c_hshFinal.put("FUPSTATUS:112", "NA");
/* 1357 */     c_hshReadyRev.put("FUPSTATUS:114", "112");
/* 1358 */     c_hshChangeRev.put("FUPSTATUS:115", "114");
/* 1359 */     c_hshChangeFinal.put("FUPSTATUS:116", "112");
/*      */ 
/*      */     
/* 1362 */     c_hshDraft.put("FUPFUPSTATUS:111", "114");
/* 1363 */     c_hshCancel.put("FUPFUPSTATUS:0000", "NA");
/* 1364 */     c_hshFinal.put("FUPFUPSTATUS:112", "NA");
/* 1365 */     c_hshReadyRev.put("FUPFUPSTATUS:114", "112");
/* 1366 */     c_hshChangeRev.put("FUPFUPSTATUS:115", "114");
/* 1367 */     c_hshChangeFinal.put("FUPFUPSTATUS:116", "112");
/*      */ 
/*      */     
/* 1370 */     c_hshDraft.put("COFCOFSTATUS:111", "114");
/* 1371 */     c_hshCancel.put("COFCOFSTATUS:0000", "NA");
/* 1372 */     c_hshFinal.put("COFCOFSTATUS:112", "NA");
/* 1373 */     c_hshReadyRev.put("COFCOFSTATUS:114", "112");
/* 1374 */     c_hshChangeRev.put("COFCOFSTATUS:115", "114");
/* 1375 */     c_hshChangeFinal.put("COFCOFSTATUS:116", "112");
/*      */ 
/*      */     
/* 1378 */     c_hshDraft.put("COFOOFSTATUS:111", "114");
/* 1379 */     c_hshCancel.put("COFOOFSTATUS:0000", "NA");
/* 1380 */     c_hshFinal.put("COFOOFSTATUS:112", "NA");
/* 1381 */     c_hshReadyRev.put("COFOOFSTATUS:114", "112");
/* 1382 */     c_hshChangeRev.put("COFOOFSTATUS:115", "114");
/* 1383 */     c_hshChangeFinal.put("COFOOFSTATUS:116", "112");
/*      */ 
/*      */     
/* 1386 */     c_hshDraft.put("FUPPOFSTATUS:6060", "6061");
/* 1387 */     c_hshCancel.put("FUPPOFSTATUS:0000", "NA");
/* 1388 */     c_hshFinal.put("FUPPOFSTATUS:6059", "NA");
/* 1389 */     c_hshReadyRev.put("FUPPOFSTATUS:6061", "6059");
/* 1390 */     c_hshChangeRev.put("FUPPOFSTATUS:6058", "6061");
/* 1391 */     c_hshChangeFinal.put("FUPPOFSTATUS:6062", "6059");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDraft(EntityItem paramEntityItem, String paramString) {
/* 1400 */     String str = getStatusAttributeCode(paramEntityItem);
/* 1401 */     return (paramString == null) ? false : c_hshDraftAll.containsKey(str);
/*      */   }
/*      */   
/*      */   public static boolean isReadyForReview(EntityItem paramEntityItem, String paramString) {
/* 1405 */     String str = getStatusAttributeCode(paramEntityItem);
/* 1406 */     return (paramString == null) ? false : c_hshRFRAll.containsKey(str);
/*      */   }
/*      */   
/*      */   public static boolean isFinal(EntityItem paramEntityItem, String paramString) {
/* 1410 */     String str = getStatusAttributeCode(paramEntityItem);
/* 1411 */     return (paramString == null) ? false : c_hshFinalAll.containsKey(str);
/*      */   }
/*      */   
/*      */   public static boolean isChangeRev(EntityItem paramEntityItem, String paramString) {
/* 1415 */     String str = getStatusAttributeCode(paramEntityItem);
/* 1416 */     return (paramString == null) ? false : c_hshCRRFRAll.containsKey(str);
/*      */   }
/*      */   
/*      */   public static boolean isChangeFinal(EntityItem paramEntityItem, String paramString) {
/* 1420 */     String str = getStatusAttributeCode(paramEntityItem);
/* 1421 */     return (paramString == null) ? false : c_hshCRFinalAll.containsKey(str);
/*      */   }
/*      */   
/*      */   public static boolean isSkippable(EntityItem paramEntityItem) {
/* 1425 */     return (paramEntityItem == null) ? false : c_hshSkipEntities.containsKey(paramEntityItem.getEntityType());
/*      */   }
/*      */   
/*      */   public static boolean isStatusableEntity(EntityItem paramEntityItem) {
/* 1429 */     return (paramEntityItem == null) ? false : c_hshEntityStatus.containsKey(paramEntityItem.getEntityType());
/*      */   }
/*      */   
/*      */   public static boolean isManagementGroupEntity(EntityItem paramEntityItem) {
/* 1433 */     return (paramEntityItem == null) ? false : c_hshManagementGroupEntities.containsKey(paramEntityItem.getEntityType());
/*      */   }
/*      */   
/*      */   public static boolean isManagementGroupChildrenEntity(EntityItem paramEntityItem) {
/* 1437 */     return (paramEntityItem == null) ? false : c_hshManagementChildrenGroupEntities.containsKey(paramEntityItem.getEntityType());
/*      */   }
/*      */   
/*      */   public static String getStatusAttributeCode(EntityItem paramEntityItem) {
/* 1441 */     return (String)c_hshEntityStatus.get(paramEntityItem.getEntityType());
/*      */   }
/*      */   
/*      */   public static boolean isDraft(EntityItem paramEntityItem) {
/* 1445 */     String str1 = getStatusAttributeCode(paramEntityItem);
/* 1446 */     String str2 = getFlagCode(paramEntityItem, str1);
/* 1447 */     return (str2 == null) ? false : c_hshDraft.containsKey(str1 + ":" + str2);
/*      */   }
/*      */   
/*      */   public static String getNextDraftState(EntityItem paramEntityItem) {
/* 1451 */     String str1 = getStatusAttributeCode(paramEntityItem);
/* 1452 */     String str2 = getFlagCode(paramEntityItem, str1);
/* 1453 */     return (str2 == null) ? null : (String)c_hshDraft.get(str1 + ":" + str2);
/*      */   }
/*      */   
/*      */   public static boolean isCancel(EntityItem paramEntityItem) {
/* 1457 */     String str1 = getStatusAttributeCode(paramEntityItem);
/* 1458 */     String str2 = getFlagCode(paramEntityItem, str1);
/* 1459 */     return (str2 == null) ? false : c_hshCancel.containsKey(str1 + ":" + str2);
/*      */   }
/*      */   
/*      */   public static String getNextCancelState(EntityItem paramEntityItem) {
/* 1463 */     String str1 = getStatusAttributeCode(paramEntityItem);
/* 1464 */     String str2 = getFlagCode(paramEntityItem, str1);
/* 1465 */     return (str2 == null) ? null : (String)c_hshCancel.get(str1 + ":" + str2);
/*      */   }
/*      */   
/*      */   public static boolean isReadyForReview(EntityItem paramEntityItem) {
/* 1469 */     String str1 = getStatusAttributeCode(paramEntityItem);
/* 1470 */     String str2 = getFlagCode(paramEntityItem, str1);
/* 1471 */     return (str2 == null) ? false : c_hshReadyRev.containsKey(str1 + ":" + str2);
/*      */   }
/*      */   
/*      */   public static String getNextReadyForReviewState(EntityItem paramEntityItem) {
/* 1475 */     String str1 = getStatusAttributeCode(paramEntityItem);
/* 1476 */     String str2 = getFlagCode(paramEntityItem, str1);
/* 1477 */     return (str2 == null) ? null : (String)c_hshReadyRev.get(str1 + ":" + str2);
/*      */   }
/*      */   
/*      */   public static boolean isFinal(EntityItem paramEntityItem) {
/* 1481 */     String str1 = getStatusAttributeCode(paramEntityItem);
/* 1482 */     String str2 = getFlagCode(paramEntityItem, str1);
/* 1483 */     return (str2 == null) ? false : c_hshFinal.containsKey(str1 + ":" + str2);
/*      */   }
/*      */   
/*      */   public static String getNextFinalState(EntityItem paramEntityItem) {
/* 1487 */     String str1 = getStatusAttributeCode(paramEntityItem);
/* 1488 */     String str2 = getFlagCode(paramEntityItem, str1);
/* 1489 */     return (str2 == null) ? null : (String)c_hshFinal.get(str1 + ":" + str2);
/*      */   }
/*      */   
/*      */   public static boolean isChangeRev(EntityItem paramEntityItem) {
/* 1493 */     String str1 = getStatusAttributeCode(paramEntityItem);
/* 1494 */     String str2 = getFlagCode(paramEntityItem, str1);
/* 1495 */     return (str2 == null) ? false : c_hshChangeRev.containsKey(str1 + ":" + str2);
/*      */   }
/*      */   
/*      */   public static String getNextChangeRevState(EntityItem paramEntityItem) {
/* 1499 */     String str1 = getStatusAttributeCode(paramEntityItem);
/* 1500 */     String str2 = getFlagCode(paramEntityItem, str1);
/* 1501 */     return (str2 == null) ? null : (String)c_hshChangeRev.get(str1 + ":" + str2);
/*      */   }
/*      */   
/*      */   public static boolean isChangeFinal(EntityItem paramEntityItem) {
/* 1505 */     String str1 = getStatusAttributeCode(paramEntityItem);
/* 1506 */     String str2 = getFlagCode(paramEntityItem, str1);
/* 1507 */     return (str2 == null) ? false : c_hshChangeFinal.containsKey(str1 + ":" + str2);
/*      */   }
/*      */   
/*      */   public static String getNextChangeFinalState(EntityItem paramEntityItem) {
/* 1511 */     String str1 = getStatusAttributeCode(paramEntityItem);
/* 1512 */     String str2 = getFlagCode(paramEntityItem, str1);
/* 1513 */     return (str2 == null) ? null : (String)c_hshChangeFinal.get(str1 + ":" + str2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1524 */   private LockGroup m_slg = null;
/*      */   
/* 1526 */   private EntityGroup m_pdhg = null;
/* 1527 */   private EntityItem m_pdhi = null;
/* 1528 */   private int m_iReturnCode = 0;
/*      */   
/*      */   private boolean m_bLocked = false;
/*      */   
/*      */   private static final long PAUSE_TIME = 60000L;
/*      */   
/*      */   private boolean m_bReadOnly = false;
/*      */   private boolean m_bVerbose = false;
/*      */   protected boolean m_bOutputAsList = false;
/* 1537 */   protected PrintWriter m_pw = null;
/* 1538 */   protected Database m_db = null;
/* 1539 */   protected Profile m_prof = null;
/* 1540 */   protected NLSItem m_nlsi = null;
/* 1541 */   protected OPICMABRItem m_abri = null;
/* 1542 */   protected String m_strABRSessionID = null;
/* 1543 */   protected Hashtable m_htUpdates = new Hashtable<>();
/* 1544 */   protected EntityList m_elist = null;
/* 1545 */   protected String m_strDGSubmit = "";
/* 1546 */   protected String m_strEpoch = "1980-01-01-00.00.00.000000";
/* 1547 */   protected String m_strEndOfDay = null;
/* 1548 */   protected String m_strForever = "9999-12-31-00.00.00.000000";
/* 1549 */   protected String m_strToday = null;
/* 1550 */   protected String m_strNow = null;
/*      */   
/* 1552 */   protected ControlBlock m_cbOn = null;
/*      */ 
/*      */   
/* 1555 */   private Set m_hsetSkipType = new HashSet();
/* 1556 */   private Hashtable m_hDisplay = new Hashtable<>();
/* 1557 */   private int m_iErrors = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int PRINT_DOWN_LEVEL = 15;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 1577 */     String str1 = "LSCURID";
/* 1578 */     String str2 = "LSCURTITLE";
/*      */     
/* 1580 */     if (paramString == null) {
/* 1581 */       logError("Passing Null _strEntityType parameter!...need a good one!");
/* 1582 */       return null;
/*      */     } 
/* 1584 */     if (paramString.equals("LSWWCC") || paramString.equals("LSCC")) {
/* 1585 */       str1 = "LSCRSID";
/* 1586 */       str2 = "LSCRSTITLE";
/* 1587 */     } else if (paramString.equals("LSWW")) {
/* 1588 */       str1 = "LSWWID";
/* 1589 */       str2 = "LSWWTITLE";
/* 1590 */     } else if (paramString.equals("LSSC")) {
/* 1591 */       str1 = "LSSCID";
/* 1592 */       str2 = "LSSCTITLE";
/* 1593 */     } else if (paramString.equals("LSSEG")) {
/* 1594 */       str1 = "LSSEGID";
/* 1595 */       str2 = "LSSEGTITLE";
/* 1596 */     } else if (paramString.equals("LSPRG")) {
/* 1597 */       str1 = "LSPRGID";
/* 1598 */       str2 = "LSPRGTITLE";
/* 1599 */     } else if (paramString.equals("LSGRM")) {
/* 1600 */       str1 = "LSGRMID";
/* 1601 */       str2 = "LSGRMTITLE";
/* 1602 */     } else if (paramString.equals("LSRC")) {
/* 1603 */       str1 = "LSRCID";
/* 1604 */       str2 = "LSRCTITLE";
/*      */     } 
/*      */     
/* 1607 */     return getEntityDescription(paramString) + " Code: " + 
/* 1608 */       getAttributeValue(paramString, paramInt, str1, "<em>** Not Populated **</em>") + " " + 
/* 1609 */       getAttributeValue(paramString, paramInt, str2, "<em>** Not Populated **</em>");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String getNow() {
/* 1622 */     return this.m_strNow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String getEndOfDay() {
/* 1632 */     return this.m_strEndOfDay;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String getToday() {
/* 1642 */     return this.m_strToday;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String getEpoch() {
/* 1652 */     return this.m_strEpoch;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String getForever() {
/* 1662 */     return this.m_strForever;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getABRReturnCode() {
/* 1672 */     return this.m_iReturnCode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getStyle() {
/* 1685 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getABRSessionID() {
/* 1695 */     return this.m_strABRSessionID;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getShortClassName(Class paramClass) {
/* 1706 */     return paramClass.getName().substring(paramClass.getName().lastIndexOf(".") + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getDGSubmitString() {
/* 1716 */     return this.m_strDGSubmit;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getEnterprise() {
/* 1726 */     if (this.m_prof == null) {
/* 1727 */       return "";
/*      */     }
/* 1729 */     return this.m_prof.getEnterprise();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getEntityType() {
/* 1739 */     if (this.m_abri == null) {
/* 1740 */       return "";
/*      */     }
/* 1742 */     return this.m_abri.getEntityType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getEntityID() {
/* 1752 */     if (this.m_abri == null) {
/* 1753 */       return -1;
/*      */     }
/* 1755 */     return this.m_abri.getEntityID();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getValOn() throws SQLException, MiddlewareException {
/* 1765 */     return this.m_prof.getValOn();
/*      */   }
/*      */   
/*      */   public final String getEffOn() throws SQLException, MiddlewareException {
/* 1769 */     return this.m_prof.getEffOn();
/*      */   }
/*      */   
/*      */   public final void setNow() {
/*      */     try {
/* 1774 */       DatePackage datePackage = this.m_db.getDates();
/* 1775 */       this.m_strNow = datePackage.getNow();
/* 1776 */     } catch (Exception exception) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NLSItem getNLSItem() {
/* 1787 */     return this.m_nlsi;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isReadOnly() {
/* 1799 */     return this.m_bReadOnly;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isVerbose() {
/* 1809 */     return this.m_bVerbose;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isLocked() {
/* 1819 */     return this.m_bLocked;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void setEntityList(EntityList paramEntityList) {
/* 1832 */     this.m_elist = paramEntityList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void setNow(String paramString) {
/* 1841 */     this.m_strNow = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void setEndOfDay(String paramString) {
/* 1850 */     this.m_strEndOfDay = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void setToday(String paramString) {
/* 1859 */     this.m_strToday = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void setEpoch(String paramString) {
/* 1869 */     this.m_strEpoch = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void setForever(String paramString) {
/* 1879 */     this.m_strForever = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setABRSessionID(String paramString) {
/* 1889 */     this.m_strABRSessionID = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNLSItem(NLSItem paramNLSItem) {
/* 1899 */     this.m_nlsi = paramNLSItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOPICMABRItem(OPICMABRItem paramOPICMABRItem) {
/* 1909 */     this.m_abri = paramOPICMABRItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReadOnly(boolean paramBoolean) {
/* 1919 */     this.m_bReadOnly = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setVerbose(boolean paramBoolean) {
/* 1929 */     this.m_bVerbose = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setLocked(boolean paramBoolean) {
/* 1939 */     this.m_bLocked = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void start_ABRBuild(boolean paramBoolean) throws LockPDHEntityException, UpdatePDHEntityException, Exception {
/* 1956 */     this.m_db = getDatabase();
/* 1957 */     this.m_prof = getProfile();
/* 1958 */     this.m_abri = getABRItem();
/*      */ 
/*      */ 
/*      */     
/* 1962 */     setABRSessionID(Integer.toString(getJobID()));
/*      */     
/* 1964 */     logMessage("ABRPROFILE EntityType = " + this.m_abri.getEntityType() + " ID = " + this.m_abri.getEntityID());
/* 1965 */     logMessage("ABRPROFILE " + this.m_prof.dump(true));
/*      */     
/* 1967 */     if (this.m_db == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1972 */     setNow();
/* 1973 */     this.m_pdhi = new EntityItem(null, this.m_prof, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*      */ 
/*      */     
/* 1976 */     EntityItem[] arrayOfEntityItem = (EntityItem[])Array.newInstance(EntityItem.class, 1);
/* 1977 */     Array.set(arrayOfEntityItem, 0, this.m_pdhi);
/*      */     
/* 1979 */     if (paramBoolean) {
/* 1980 */       logMessage("VE name is " + this.m_abri.getVEName());
/* 1981 */       ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, this.m_abri.getVEName());
/*      */       
/* 1983 */       logMessage("Creating Entity List");
/* 1984 */       logMessage("Profile is " + this.m_prof);
/* 1985 */       logMessage("Role is " + this.m_prof.getRoleCode() + " : " + this.m_prof.getRoleDescription());
/* 1986 */       logMessage("Extract action Item is" + extractActionItem);
/* 1987 */       logMessage("Entity Item" + arrayOfEntityItem);
/* 1988 */       this.m_elist = this.m_db.getEntityList(this.m_prof, extractActionItem, arrayOfEntityItem);
/* 1989 */       logMessage("Entity List Created");
/*      */     } else {
/* 1991 */       logMessage("start_ABRBuild instructed not to generate Extract Action Item");
/*      */     } 
/*      */     
/* 1994 */     if (!isReadOnly() && 
/* 1995 */       !getSoftLock()) {
/*      */ 
/*      */       
/*      */       try {
/* 1999 */         log("Could not get the lock, sleeping for 60000 milliseconds.");
/* 2000 */         Thread.sleep(60000L);
/* 2001 */       } catch (InterruptedException interruptedException) {
/*      */         return;
/*      */       } 
/*      */       
/* 2005 */       if (!getSoftLock())
/*      */       {
/*      */         
/* 2008 */         logWarning("IAB1006E: Could not get soft lock.  Execution will continue.");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void start_ABRBuild() throws LockPDHEntityException, UpdatePDHEntityException, Exception {
/* 2022 */     start_ABRBuild(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String getABREntityType() {
/* 2032 */     if (this.m_abri == null) {
/* 2033 */       return "";
/*      */     }
/* 2035 */     return this.m_abri.getEntityType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final int getABREntityID() {
/* 2045 */     if (this.m_abri == null) {
/* 2046 */       return 0;
/*      */     }
/* 2048 */     return this.m_abri.getEntityID();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String getABRCode() {
/* 2058 */     if (this.m_abri == null) {
/* 2059 */       return "";
/*      */     }
/* 2061 */     return this.m_abri.getABRCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setDGString(int paramInt) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void printTestedMessage(String paramString, String[] paramArrayOfString) {
/* 2119 */     log(buildLogMessage(paramString, paramArrayOfString));
/* 2120 */     if (isVerbose()) {
/* 2121 */       println(formatMessage(buildMessage(paramString, paramArrayOfString), false));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String formatMessage(String paramString, boolean paramBoolean) {
/* 2135 */     if (paramBoolean && this.m_bVerbose) {
/* 2136 */       if (this.m_bOutputAsList) {
/* 2137 */         return "\n<font color=red><li>" + paramString + "</li></font>";
/*      */       }
/* 2139 */       return "\n<font color=red>" + paramString + "</font><br />";
/*      */     } 
/*      */     
/* 2142 */     if (this.m_bOutputAsList) {
/* 2143 */       return "<li>" + paramString + "</li>";
/*      */     }
/* 2145 */     return paramString + "<br />";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void printMessage(String paramString, String[] paramArrayOfString) {
/* 2159 */     println(formatMessage(buildMessage(paramString, paramArrayOfString), false));
/* 2160 */     log(buildLogMessage(paramString, paramArrayOfString));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void printMessage(String paramString) {
/* 2170 */     println(paramString);
/* 2171 */     log(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void prettyPrint(String paramString, int paramInt) {
/* 2189 */     String str1 = null;
/* 2190 */     String str2 = null;
/*      */     
/* 2192 */     int i = 0;
/*      */ 
/*      */     
/* 2195 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, "\n", false);
/* 2196 */     while (stringTokenizer.hasMoreElements()) {
/* 2197 */       str2 = stringTokenizer.nextToken();
/* 2198 */       if (str2.length() > paramInt) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2203 */         while (str2.length() > paramInt) {
/* 2204 */           i = (str2.length() >= paramInt) ? paramInt : str2.length();
/* 2205 */           str1 = str2.substring(0, i);
/* 2206 */           if (!str1.substring(i - 1).equals(" "))
/*      */           {
/* 2208 */             if (str1.lastIndexOf(" ") > 0)
/*      */             {
/* 2210 */               str1 = str1.substring(0, str1.lastIndexOf(" "));
/*      */             }
/*      */           }
/* 2213 */           str2 = str2.substring(str1.length());
/* 2214 */           println(str1.trim());
/*      */         } 
/* 2216 */         if (str2.trim().length() > 0)
/* 2217 */           println(str2.trim()); 
/*      */         continue;
/*      */       } 
/* 2220 */       println(str2.trim());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void printWarningMessage(String paramString, String[] paramArrayOfString) {
/* 2233 */     println(formatMessage(buildMessage(paramString, paramArrayOfString), false));
/* 2234 */     logWarning(buildLogMessage(paramString, paramArrayOfString));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void printErrorMessage(String paramString, String[] paramArrayOfString) {
/* 2248 */     println(formatMessage(buildLogMessage(paramString, paramArrayOfString), true));
/* 2249 */     logError(buildLogMessage(paramString, paramArrayOfString));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String buildMessage(String paramString, String[] paramArrayOfString) {
/* 2261 */     return buildMessage(paramString, paramArrayOfString, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String buildLogMessage(String paramString, String[] paramArrayOfString) {
/* 2273 */     return buildMessage(paramString, paramArrayOfString, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String buildMessage(String paramString, String[] paramArrayOfString, boolean paramBoolean) {
/* 2286 */     StringBuffer stringBuffer = new StringBuffer();
/* 2287 */     int i = -1;
/* 2288 */     int j = 0;
/* 2289 */     if (paramArrayOfString != null) {
/* 2290 */       for (byte b = 0; b < paramArrayOfString.length; ) {
/* 2291 */         i = paramString.indexOf("%" + (b + 1), i);
/* 2292 */         if (i != -1) {
/* 2293 */           boolean bool = true;
/* 2294 */           if (paramString.length() > i + ("%" + (b + 1)).length() && paramString.charAt(i + ("%" + (b + 1)).length()) == '#') {
/* 2295 */             bool = false;
/*      */           }
/* 2297 */           String str = paramString.substring(j, i);
/* 2298 */           if (bool) {
/* 2299 */             if (paramBoolean) {
/* 2300 */               stringBuffer.append(str + "\"" + paramArrayOfString[b] + "\"");
/*      */             } else {
/* 2302 */               stringBuffer.append(str + "<em>&quot;" + paramArrayOfString[b] + "&quot;</em>");
/*      */             } 
/* 2304 */             j = i + ("%" + (b + 1)).length();
/*      */           } else {
/* 2306 */             stringBuffer.append(str + paramArrayOfString[b]);
/* 2307 */             j = i + ("%" + (b + 1)).length() + 1;
/*      */           } 
/*      */           
/*      */           b++;
/*      */         } 
/*      */       } 
/*      */     }
/* 2314 */     if (j != paramString.length()) {
/* 2315 */       stringBuffer.append(paramString.substring(j));
/*      */     }
/*      */     
/* 2318 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean checkAttributes(String paramString, int paramInt, String[] paramArrayOfString) {
/* 2332 */     boolean bool = true;
/*      */ 
/*      */     
/* 2335 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*      */ 
/*      */ 
/*      */       
/* 2339 */       String[] arrayOfString = { getABREntityDesc(paramString, paramInt), getAttributeDescription(paramString, paramArrayOfString[b]) };
/*      */       
/* 2341 */       String str = getAttributeValue(paramString, paramInt, paramArrayOfString[b], "<em>** Not Populated **</em>");
/*      */       
/* 2343 */       if (str == null || str.equals("&nbsp;")) {
/* 2344 */         printErrorMessage("IAB1001E: The %1 is missing the data for the Attribute: %2.", arrayOfString);
/* 2345 */         bool = false;
/*      */       } else {
/* 2347 */         printTestedMessage("IAB2011I: The required attribute %1 was populated.", arrayOfString);
/*      */       } 
/*      */     } 
/*      */     
/* 2351 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeDescription(String paramString1, String paramString2) {
/* 2364 */     return getMetaDescription(paramString1, paramString2, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getMetaDescription(String paramString1, String paramString2, boolean paramBoolean) {
/* 2378 */     return getMetaDescription(this.m_elist, paramString1, paramString2, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getMetaDescription(EntityList paramEntityList, String paramString1, String paramString2, boolean paramBoolean) {
/* 2391 */     EntityGroup entityGroup = paramEntityList.getEntityGroup(paramString1);
/* 2392 */     if (entityGroup == null) {
/* 2393 */       logError("Did not find EntityGroup: " + paramString1 + " in entity list to extract getMetaDescription");
/* 2394 */       return null;
/*      */     } 
/*      */     
/* 2397 */     EANMetaAttribute eANMetaAttribute = null;
/* 2398 */     if (entityGroup != null) {
/* 2399 */       eANMetaAttribute = entityGroup.getMetaAttribute(paramString2);
/*      */     }
/* 2401 */     String str = null;
/* 2402 */     if (eANMetaAttribute != null) {
/* 2403 */       if (paramBoolean) {
/* 2404 */         str = eANMetaAttribute.getLongDescription();
/*      */       } else {
/* 2406 */         str = eANMetaAttribute.getShortDescription();
/*      */       } 
/*      */     }
/* 2409 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeMetaFlagDescription(String paramString1, String paramString2, String paramString3) {
/* 2423 */     return getMetaFlagDescription(paramString1, paramString2, paramString3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getMetaFlagDescription(String paramString1, String paramString2, String paramString3) {
/* 2439 */     if (paramString3 == null) {
/* 2440 */       logError("null FlagCode supplied! Returning Error");
/* 2441 */       return null;
/*      */     } 
/* 2443 */     EntityGroup entityGroup = this.m_elist.getEntityGroup(paramString1);
/*      */     
/* 2445 */     if (entityGroup == null) {
/* 2446 */       logError("Did not find EntityGroup: " + paramString1 + " in entity list to extract MetaFlagDescription");
/* 2447 */       return null;
/*      */     } 
/*      */     
/* 2450 */     EntityItem entityItem = entityGroup.getEntityItem(paramString1);
/* 2451 */     EANAttribute eANAttribute = entityItem.getAttribute(paramString1);
/* 2452 */     String str = null;
/* 2453 */     if (eANAttribute instanceof EANFlagAttribute) {
/* 2454 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/* 2455 */       str = eANFlagAttribute.getFlagLongDescription(paramString3);
/*      */     } 
/* 2457 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean anyRetiredParents(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4, String paramString5) {
/* 2477 */     boolean bool = true;
/* 2478 */     Iterator<Integer> iterator = getParentEntityIds(paramString1, paramInt, paramString2, paramString3).iterator();
/* 2479 */     while (iterator.hasNext()) {
/* 2480 */       int i = ((Integer)iterator.next()).intValue();
/* 2481 */       String str1 = getAttributeFlagValue(paramString2, i, paramString4);
/* 2482 */       if (!paramString5.equals(str1)) {
/* 2483 */         bool = false;
/* 2484 */         String str = "<em>** Not Populated **</em>";
/* 2485 */         if (str1 != null) {
/* 2486 */           str = getAttributeMetaFlagDescription(paramString2, paramString4, str1);
/*      */         }
/*      */ 
/*      */         
/* 2490 */         printErrorMessage("IAB2001E: The %1# cannot be %2# because %3 has a current Life Cycle status value of %4.", new String[] {
/*      */ 
/*      */               
/* 2493 */               getEntityDescription(getEntityType()), "retired", 
/*      */               
/* 2495 */               getABREntityDesc(paramString2, i), str
/*      */             });
/*      */         
/*      */         continue;
/*      */       } 
/* 2500 */       String str2 = getAttributeMetaFlagDescription(paramString2, paramString4, paramString5);
/* 2501 */       printTestedMessage("IAB2015I: %1 has a current Life Cycle status value of %2.", new String[] {
/*      */ 
/*      */             
/* 2504 */             getABREntityDesc(paramString2, i), (str2 == null) ? ("Null value returned for " + paramString2 + ":" + paramString4 + ":" + paramString5) : str2
/*      */           });
/*      */     } 
/*      */ 
/*      */     
/* 2509 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean anyRetiredChildren(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4, String paramString5) {
/* 2528 */     boolean bool = true;
/* 2529 */     Iterator<Integer> iterator = getChildrenEntityIds(paramString1, paramInt, paramString2, paramString3).iterator();
/* 2530 */     while (iterator.hasNext()) {
/* 2531 */       int i = ((Integer)iterator.next()).intValue();
/* 2532 */       String str = getAttributeFlagValue(paramString2, i, paramString4);
/* 2533 */       if (!paramString5.equals(str)) {
/* 2534 */         bool = false;
/* 2535 */         String str1 = "<em>** Not Populated **</em>";
/* 2536 */         if (str != null) {
/* 2537 */           str1 = getAttributeMetaFlagDescription(paramString2, paramString4, str);
/*      */         }
/*      */ 
/*      */         
/* 2541 */         printErrorMessage("IAB2001E: The %1# cannot be %2# because %3 has a current Life Cycle status value of %4.", new String[] {
/*      */ 
/*      */               
/* 2544 */               getEntityDescription(getEntityType()), "retired", 
/*      */               
/* 2546 */               getABREntityDesc(paramString2, i), str1
/*      */             });
/*      */         
/*      */         continue;
/*      */       } 
/* 2551 */       printTestedMessage("IAB2015I: %1 has a current Life Cycle status value of %2.", new String[] {
/*      */ 
/*      */             
/* 2554 */             getABREntityDesc(paramString2, i), 
/* 2555 */             getAttributeMetaFlagDescription(paramString2, paramString4, paramString5)
/*      */           });
/*      */     } 
/*      */     
/* 2559 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean checkForAvailableParents(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4, String paramString5) {
/* 2579 */     boolean bool = true;
/* 2580 */     Iterator<Integer> iterator = getParentEntityIds(paramString1, paramInt, paramString2, paramString3).iterator();
/* 2581 */     while (iterator.hasNext()) {
/* 2582 */       int i = ((Integer)iterator.next()).intValue();
/* 2583 */       String str = getAttributeFlagValue(paramString2, i, paramString4);
/* 2584 */       if (!paramString5.equals(str)) {
/* 2585 */         bool = false;
/* 2586 */         String str1 = "<em>** Not Populated **</em>";
/* 2587 */         if (str != null) {
/* 2588 */           str1 = getAttributeMetaFlagDescription(paramString2, paramString4, str);
/*      */         }
/*      */ 
/*      */         
/* 2592 */         printErrorMessage("IAB2001E: The %1# cannot be %2# because %3 has a current Life Cycle status value of %4.", new String[] {
/*      */ 
/*      */               
/* 2595 */               getEntityDescription(getEntityType()), "made available", 
/*      */               
/* 2597 */               getABREntityDesc(paramString2, i), str1
/*      */             });
/*      */         
/*      */         continue;
/*      */       } 
/* 2602 */       printTestedMessage("IAB2015I: %1 has a current Life Cycle status value of %2.", new String[] {
/*      */ 
/*      */             
/* 2605 */             getABREntityDesc(paramString2, i), 
/* 2606 */             getAttributeMetaFlagDescription(paramString2, paramString4, paramString5)
/*      */           });
/*      */     } 
/*      */     
/* 2610 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean checkForAvailableChildren(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4, String paramString5) {
/* 2630 */     boolean bool = true;
/* 2631 */     Iterator<Integer> iterator = getChildrenEntityIds(paramString1, paramInt, paramString2, paramString3).iterator();
/*      */     
/* 2633 */     while (iterator.hasNext()) {
/* 2634 */       int i = ((Integer)iterator.next()).intValue();
/* 2635 */       String str = getAttributeFlagValue(paramString2, i, paramString4);
/* 2636 */       if (!paramString5.equals(str)) {
/* 2637 */         bool = false;
/* 2638 */         String str1 = "<em>** Not Populated **</em>";
/* 2639 */         if (str != null) {
/* 2640 */           str1 = getAttributeMetaFlagDescription(paramString2, paramString4, str);
/*      */         }
/*      */ 
/*      */         
/* 2644 */         printErrorMessage("IAB2001E: The %1# cannot be %2# because %3 has a current Life Cycle status value of %4.", new String[] {
/*      */ 
/*      */               
/* 2647 */               getEntityDescription(getEntityType()), "made available", 
/*      */               
/* 2649 */               getABREntityDesc(paramString2, i), str1
/*      */             });
/*      */         
/*      */         continue;
/*      */       } 
/* 2654 */       printTestedMessage("IAB2015I: %1 has a current Life Cycle status value of %2.", new String[] {
/*      */ 
/*      */             
/* 2657 */             getABREntityDesc(paramString2, i), 
/* 2658 */             getAttributeMetaFlagDescription(paramString2, paramString4, paramString5)
/*      */           });
/*      */     } 
/*      */ 
/*      */     
/* 2663 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean checkOptionalChildren(String[] paramArrayOfString) {
/* 2674 */     return checkRelators(paramArrayOfString, 2, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean checkOptionalChild(String paramString1, String paramString2) {
/* 2686 */     return checkRelator(paramString1, paramString2, 2, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean checkMultipleChildren(String[] paramArrayOfString) {
/* 2698 */     return checkRelators(paramArrayOfString, 1, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean checkMultipleChild(String paramString1, String paramString2) {
/* 2710 */     return checkRelator(paramString1, paramString2, 1, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean checkUniqueChildren(String[] paramArrayOfString) {
/* 2722 */     return checkRelators(paramArrayOfString, 0, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean checkUniqueParents(String[] paramArrayOfString) {
/* 2734 */     return checkRelators(paramArrayOfString, 0, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean checkUniqueChild(String paramString1, String paramString2) {
/* 2747 */     return checkRelator(paramString1, paramString2, 0, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean checkUniqueParent(String paramString1, String paramString2) {
/* 2760 */     EntityGroup entityGroup = this.m_elist.getEntityGroup(paramString2);
/* 2761 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 2762 */     EntityItem entityItem1 = null;
/* 2763 */     EntityItem entityItem2 = null;
/* 2764 */     if (entityGroup.getEntityItemCount() == 0) {
/* 2765 */       return false;
/*      */     }
/* 2767 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2768 */       entityItem1 = entityGroup.getEntityItem(b);
/* 2769 */       for (byte b1 = 0; b1 < entityItem1.getUpLinkCount(); b1++) {
/* 2770 */         entityItem2 = (EntityItem)entityItem1.getUpLink(b1);
/* 2771 */         if (entityItem2.getEntityType().equals(paramString1)) {
/* 2772 */           if (!hashtable.containsKey(entityItem2.getEntityType() + ":" + Integer.toString(getEntityID()))) {
/* 2773 */             hashtable.put(entityItem2.getEntityType() + ":" + Integer.toString(getEntityID()), "Found");
/*      */           } else {
/* 2775 */             return false;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/* 2780 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkRelators(String[] paramArrayOfString, int paramInt, boolean paramBoolean) {
/* 2794 */     boolean bool = true;
/*      */ 
/*      */     
/* 2797 */     for (byte b = 0; b < paramArrayOfString.length; b += 2) {
/* 2798 */       String str1 = paramArrayOfString[b];
/* 2799 */       String str2 = paramArrayOfString[b + 1];
/* 2800 */       if (!checkRelator(str1, str2, paramInt, paramBoolean)) {
/* 2801 */         bool = false;
/*      */       }
/*      */     } 
/*      */     
/* 2805 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkRelator(String paramString1, String paramString2, int paramInt, boolean paramBoolean) {
/* 2820 */     Vector vector = null;
/* 2821 */     String str = getEntityDescription(paramString2);
/*      */ 
/*      */     
/* 2824 */     String[] arrayOfString = { getEntityDescription(getEntityType()), getABREntityDesc(getEntityType(), getEntityID()), str };
/*      */ 
/*      */ 
/*      */     
/* 2828 */     if (paramBoolean) {
/* 2829 */       vector = getChildrenEntityIds(getEntityType(), getEntityID(), paramString1, paramString2);
/*      */     } else {
/* 2831 */       vector = getParentEntityIds(getEntityType(), getEntityID(), paramString1, paramString2);
/*      */     } 
/*      */     
/* 2834 */     int i = vector.size();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2840 */     switch (paramInt) {
/*      */       case 0:
/* 2842 */         switch (i) {
/*      */           case 0:
/* 2844 */             printErrorMessage("IAB1002E: Relators not defined: The %1# %2 must have one and only one %3 relator defined.", arrayOfString);
/* 2845 */             return false;
/*      */           case 1:
/*      */             break;
/*      */         } 
/* 2849 */         printErrorMessage("IAB1003E: Too many relators defined. The %1# %2 has more than one %3 relator defined.", arrayOfString);
/* 2850 */         return false;
/*      */ 
/*      */       
/*      */       case 1:
/* 2854 */         if (i == 0) {
/* 2855 */           printErrorMessage("IAB1004E: The %1# %2 must have one relator to: %3.", arrayOfString);
/* 2856 */           return false;
/*      */         } 
/*      */         break;
/*      */       case 2:
/* 2860 */         if (i > 1) {
/* 2861 */           printErrorMessage("IAB1003E: Too many relators defined. The %1# %2 has more than one %3 relator defined.", arrayOfString);
/* 2862 */           return false;
/*      */         } 
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2869 */     printTestedMessage("IAB2012I: The required relator %1 was found for %2.", arrayOfString);
/* 2870 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getSoftLock() {
/* 2882 */     this.m_slg = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 2889 */       EntityItem entityItem = new EntityItem(null, this.m_prof, Profile.OPWG_TYPE, this.m_prof.getOPWGID());
/* 2890 */       this.m_slg = new LockGroup(this.m_db, this.m_prof, entityItem, this.m_pdhi, entityItem.getKey(), 0, true);
/*      */     }
/* 2892 */     catch (MiddlewareException middlewareException) {
/*      */       
/* 2894 */       logError((Exception)middlewareException, "Error during Softlock");
/* 2895 */       return false;
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 2900 */     catch (Exception exception) {
/* 2901 */       logError(exception, "Error during Softlock");
/* 2902 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 2906 */     if (this.m_slg == null) {
/* 2907 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2912 */     boolean bool = false;
/*      */     try {
/* 2914 */       EntityItem entityItem = new EntityItem(null, this.m_prof, Profile.OPWG_TYPE, this.m_prof.getOPWGID());
/* 2915 */       if (this.m_slg.hasExclusiveLock(entityItem, entityItem.getKey(), this.m_prof)) {
/* 2916 */         bool = true;
/* 2917 */         log(
/* 2918 */             buildLogMessage("IAB1005E: The %1#:%2# entity is locked by %3.", new String[] {
/*      */ 
/*      */                 
/* 2921 */                 this.m_pdhi.getEntityType(), "" + this.m_pdhi
/* 2922 */                 .getEntityID(), 
/* 2923 */                 getShortClassName(getClass())
/*      */               }));
/*      */       } else {
/* 2926 */         bool = false;
/* 2927 */         logWarning(
/* 2928 */             buildLogMessage("IAB1005E: The %1#:%2# entity is locked by %3.", new String[] {
/*      */ 
/*      */                 
/* 2931 */                 this.m_pdhi.getEntityType(), "" + this.m_pdhi
/* 2932 */                 .getEntityID(), 
/* 2933 */                 getShortClassName(getClass())
/*      */               }));
/*      */       } 
/*      */       
/* 2937 */       if (!bool) {
/* 2938 */         logWarning("IAB1006E: Could not get soft lock.  Execution will continue.");
/*      */       } else {
/* 2940 */         bool = true;
/*      */       } 
/* 2942 */       setLocked(bool);
/* 2943 */     } catch (MiddlewareRequestException middlewareRequestException) {
/* 2944 */       logMessage("getSoftLock:MiddlewareRequestException Error while trying to create entityItem");
/*      */     } 
/*      */     
/* 2947 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearSoftLock() {
/* 2958 */     if (this.m_slg == null) {
/*      */       return;
/*      */     }
/*      */     try {
/* 2962 */       EntityItem entityItem = new EntityItem(null, this.m_prof, Profile.OPWG_TYPE, this.m_prof.getOPWGID());
/* 2963 */       this.m_slg = new LockGroup(this.m_db, this.m_prof, entityItem, this.m_pdhi, entityItem.getKey(), 0, false);
/* 2964 */       this.m_slg.removeLockItem(this.m_db, this.m_pdhi, this.m_prof, entityItem, entityItem.getKey(), 0);
/* 2965 */       setLocked(false);
/* 2966 */     } catch (Exception exception) {
/* 2967 */       exception.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void log(String paramString) {
/* 2981 */     logMessage(getABRSessionID() + ":" + getShortClassName(getClass()) + ":" + paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void logWarning(String paramString) {
/* 2994 */     logMessage(getABRSessionID() + ":" + getShortClassName(getClass()) + ":" + paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void logError(String paramString) {
/* 3007 */     logMessage(getABRSessionID() + ":" + getShortClassName(getClass()) + ":" + paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void logError(Exception paramException, String paramString) {
/* 3018 */     logMessage(getABRSessionID() + ":" + getShortClassName(getClass()) + ":" + paramString);
/* 3019 */     StringWriter stringWriter = new StringWriter();
/*      */     
/* 3021 */     paramException.printStackTrace(new PrintWriter(stringWriter));
/*      */     
/* 3023 */     String str = stringWriter.toString();
/*      */     
/* 3025 */     logMessage(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getVersion() {
/* 3036 */     return "$Id: PokBaseABR.java,v 1.216 2013/03/29 06:48:56 wangyulo Exp $";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void buildReportHeader() throws SQLException, MiddlewareException {
/* 3044 */     String str = getVersion();
/* 3045 */     String[] arrayOfString = { getABRDescription(), str, getEnterprise() };
/* 3046 */     buildReportHeader(arrayOfString);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void buildReportHeader(String[] paramArrayOfString) throws SQLException, MiddlewareException {
/* 3052 */     String str1 = getShortClassName(getClass());
/* 3053 */     println("<html>\n<head>\n<title>" + str1 + "</title>\n" + 
/* 3054 */         getStyle() + "</head>\n<body>\n");
/*      */     
/* 3056 */     String str2 = buildMessage("<b>IAB0001I: ABRName:</b> %1# <b>Version:</b> %2# <b>Enterprise:</b> %3#", paramArrayOfString);
/* 3057 */     println("<p>" + str2 + "</p>");
/* 3058 */     println("<table width=\"750\" cellpadding=\"0\" cellspacing=\"0\">\n<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" + 
/*      */         
/* 3060 */         getABRVersion() + "</td></tr>\n<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" + 
/*      */         
/* 3062 */         getValOn() + "</td></tr>\n<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" + 
/*      */         
/* 3064 */         getEffOn() + "</td></tr>\n<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" + 
/*      */         
/* 3066 */         getNow() + "</td></tr>\n</table>");
/*      */     
/* 3068 */     println("<h3>Description: </h3>" + getDescription() + "\n<hr>\n");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void buildReportHeaderII() throws SQLException, MiddlewareException {
/* 3076 */     String[] arrayOfString = { getABRDescription(), getEnterprise() };
/* 3077 */     buildReportHeaderII(arrayOfString);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void buildReportHeaderII(String[] paramArrayOfString) throws SQLException, MiddlewareException {
/* 3083 */     String str = getShortClassName(getClass());
/* 3084 */     println("<html>\n<head>\n<title>" + str + "</title>\n" + 
/* 3085 */         getStyle() + "</head>\n<body>\n");
/*      */ 
/*      */ 
/*      */     
/* 3089 */     println("<table width=\"750\" cellpadding=\"0\" cellspacing=\"0\">\n<tr><td width=\"25%\"><b>ABRName: </b></td><td>" + 
/*      */         
/* 3091 */         getABRCode() + "</td></tr>\n<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" + 
/*      */         
/* 3093 */         getABRVersion() + "</td></tr>\n<tr><td width=\"25%\"><b>Enterprise: </b></td><td>" + 
/*      */         
/* 3095 */         getEnterprise() + "</td></tr>\n<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" + 
/*      */         
/* 3097 */         getValOn() + "</td></tr>\n<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" + 
/*      */         
/* 3099 */         getEffOn() + "</td></tr>\n<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" + 
/*      */         
/* 3101 */         getNow() + "</td></tr>\n</table>");
/*      */     
/* 3103 */     println("<h3>Description: </h3>" + getDescription() + "\n<hr>\n");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void buildReportFooter() {
/* 3114 */     println("\n");
/* 3115 */     println(getDGSubmitString() + "\n</body>\n</html>");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean checkFlagValues(String paramString1, String paramString2) {
/* 3131 */     String str = null;
/* 3132 */     if (paramString2.toLowerCase().equals(paramString2.toUpperCase())) {
/* 3133 */       str = getAttributeFlagEnabledValue(getEntityType(), getEntityID(), paramString1, "not*Found");
/*      */     } else {
/* 3135 */       str = getAttributeFlagValue(getEntityType(), getEntityID(), paramString1);
/*      */     } 
/* 3137 */     log("checkFlagValues: Verify " + paramString1 + ":" + paramString2);
/* 3138 */     log("checkFlagValues: Retrieved Value for " + paramString1 + ":" + str);
/* 3139 */     if (str == null && paramString2 == null) {
/* 3140 */       return true;
/*      */     }
/* 3142 */     if (str == null && paramString2 != null) {
/* 3143 */       return false;
/*      */     }
/* 3145 */     if (str.equals(paramString2)) {
/* 3146 */       return true;
/*      */     }
/* 3148 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void postABRStatusToPDH(String paramString1, String paramString2) {
/*      */     try {
/* 3161 */       ControlBlock controlBlock = new ControlBlock(getNow(), getForever(), getNow(), getForever(), getProfile().getOPWGID());
/* 3162 */       ReturnEntityKey returnEntityKey = new ReturnEntityKey(getEntityType(), getEntityID(), true);
/* 3163 */       Vector<SingleFlag> vector = new Vector();
/* 3164 */       vector.addElement(new SingleFlag(getProfile().getEnterprise(), getEntityType(), getEntityID(), paramString1, paramString2, 1, controlBlock));
/* 3165 */       returnEntityKey.m_vctAttributes = vector;
/* 3166 */       Vector<ReturnEntityKey> vector1 = new Vector();
/* 3167 */       vector1.addElement(returnEntityKey);
/* 3168 */       this.m_db.update(getProfile(), vector1);
/* 3169 */       this.m_db.commit();
/* 3170 */     } catch (Exception exception) {
/* 3171 */       logMessage(exception.toString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void putDataToPDHGroup(Hashtable paramHashtable) throws Exception {
/* 3220 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/* 3221 */     if (entityGroup == null) {
/* 3222 */       printMessage("putDataToPDHGroup:ERROR!: Cannot retrieve group" + getEntityType() + " from EntityList");
/*      */       return;
/*      */     } 
/* 3225 */     EntityItem entityItem1 = entityGroup.getEntityItem(getEntityType(), getEntityID());
/* 3226 */     if (entityItem1 == null) {
/* 3227 */       printMessage("putDataToPDHGroup:ERROR!: Cannot retrieve Item " + getEntityType() + ":" + getEntityID() + " from EntityGroup");
/*      */       return;
/*      */     } 
/* 3230 */     EntityItem entityItem2 = new EntityItem(null, this.m_prof, Profile.OPWG_TYPE, this.m_prof.getOPWGID());
/* 3231 */     if (entityItem2 == null) {
/* 3232 */       printMessage("putDataToPDHGroup:ERROR!: Cannot retrieve Lock Item");
/*      */       return;
/*      */     } 
/* 3235 */     LockGroup lockGroup = new LockGroup(this.m_db, this.m_prof, entityItem2, entityItem1, entityItem2.getKey(), 0, true);
/* 3236 */     if (lockGroup == null) {
/* 3237 */       printMessage("putDataToPDHGroup:ERROR!: Cannot retrieve Lock group");
/*      */       
/*      */       return;
/*      */     } 
/* 3241 */     log("*****LOCK GROUP DUMP*****" + lockGroup.dump(false));
/*      */     
/* 3243 */     log("HASHTABLE DUMP" + paramHashtable.toString());
/*      */     
/* 3245 */     LockItem lockItem = lockGroup.getLockItem(0);
/* 3246 */     if (lockItem == null) {
/* 3247 */       printMessage("putDataToPDHGroup:ERROR!: Cannot retrieve Item from Lock group");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 3252 */     Enumeration<String> enumeration = paramHashtable.keys();
/* 3253 */     String str = enumeration.hasMoreElements() ? enumeration.nextElement() : "NOATTRIBUTEAVAILABLE";
/* 3254 */     log("Checking attribute " + str);
/* 3255 */     if (!entityItem1.isEditable(entityItem1.getEntityType() + ":" + str)) {
/* 3256 */       printMessage("putDataToPDHGroup:Cannot update " + str + " through ABR.. Entity is not Editable");
/*      */       
/*      */       return;
/*      */     } 
/* 3260 */     log("putDataToPDHGroup:Locked Entity :" + getEntityType() + getEntityID() + ": before updating");
/*      */     
/* 3262 */     while (enumeration.hasMoreElements()) {
/* 3263 */       str = enumeration.nextElement();
/* 3264 */       String str1 = (String)paramHashtable.get(str);
/* 3265 */       EANAttribute eANAttribute = entityItem1.getAttribute(str);
/*      */       
/* 3267 */       if (eANAttribute == null) {
/* 3268 */         log("putDataToPDHGroup:ERROR!: Cannot retrieve " + str + " from EntityItem");
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */ 
/*      */       
/* 3275 */       if (eANAttribute instanceof StatusAttribute || eANAttribute instanceof COM.ibm.eannounce.objects.SingleFlagAttribute) {
/* 3276 */         StatusAttribute statusAttribute = (StatusAttribute)eANAttribute;
/* 3277 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])statusAttribute.get();
/* 3278 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/* 3279 */           if (arrayOfMetaFlag[b].getFlagCode().equals(str1)) {
/* 3280 */             arrayOfMetaFlag[b].setSelected(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*      */         try {
/* 3285 */           log("Setting " + getEntityType() + ":" + getEntityID() + "Flag attribute: " + str + " to " + str1);
/* 3286 */           statusAttribute.put(arrayOfMetaFlag);
/* 3287 */         } catch (EANBusinessRuleException eANBusinessRuleException) {
/* 3288 */           log("Business rule error when Setting " + getEntityType() + ":" + getEntityID() + "Flag attribute: " + str + " to " + str1);
/*      */         }  continue;
/* 3290 */       }  if (eANAttribute instanceof TextAttribute) {
/* 3291 */         TextAttribute textAttribute = (TextAttribute)eANAttribute;
/* 3292 */         if (str1.equals("")) {
/* 3293 */           textAttribute.setActive(false); continue;
/*      */         } 
/*      */         try {
/* 3296 */           log("Setting " + getEntityType() + ":" + getEntityID() + "Text attribute: " + str + " to " + str1);
/* 3297 */           textAttribute.put(str1);
/* 3298 */         } catch (EANBusinessRuleException eANBusinessRuleException) {
/* 3299 */           log("Business rule error when Setting " + getEntityType() + ":" + getEntityID() + "Text attribute: " + str + " to " + str1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void commitPDHGroupToPDH() throws Exception {
/* 3313 */     this.m_pdhg.commit(this.m_db, null);
/* 3314 */     log("Commited updates for " + getEntityType() + ":" + getEntityID());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String dump() {
/* 3325 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/* 3327 */     stringBuffer.append("ABRName: " + getABRCode());
/* 3328 */     stringBuffer.append("\nABRSessionID: " + getABRSessionID());
/* 3329 */     stringBuffer.append("\nDescription: " + getABRDescription());
/* 3330 */     stringBuffer.append("\nEntityType: " + getABREntityType());
/* 3331 */     stringBuffer.append("\nEntityID: " + getABREntityID());
/*      */     
/* 3333 */     return new String(stringBuffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] convertToArray(String paramString) {
/* 3349 */     Vector<String> vector = new Vector();
/* 3350 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, "|");
/* 3351 */     while (stringTokenizer.hasMoreTokens()) {
/* 3352 */       vector.addElement(stringTokenizer.nextToken());
/*      */     }
/* 3354 */     String[] arrayOfString = new String[vector.size()];
/* 3355 */     vector.copyInto((Object[])arrayOfString);
/* 3356 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getEntityDescription(String paramString) {
/* 3381 */     return getEntityDescription(this.m_elist, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getEntityDescription(EntityList paramEntityList, String paramString) {
/* 3393 */     EntityGroup entityGroup = paramEntityList.getEntityGroup(paramString);
/* 3394 */     return entityGroup.getLongDescription();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getEntityCapability(String paramString) {
/* 3406 */     return getEntityCapability(this.m_elist, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getEntityCapability(EntityList paramEntityList, String paramString) {
/* 3418 */     EntityGroup entityGroup = paramEntityList.getEntityGroup(paramString);
/*      */     
/* 3420 */     EANMetaAttribute eANMetaAttribute = null;
/* 3421 */     if (entityGroup != null) {
/* 3422 */       eANMetaAttribute = entityGroup.getMetaAttribute(paramString);
/*      */     }
/* 3424 */     if (eANMetaAttribute != null) {
/* 3425 */       return eANMetaAttribute.getCapability();
/*      */     }
/* 3427 */     return "R";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getParents(String paramString1, String paramString2) {
/* 3448 */     Vector<EntityItem> vector1 = new Vector();
/* 3449 */     Vector<EntityItem> vector2 = getAllLinkedParents(getRootEntityType(), getRootEntityID());
/*      */     
/* 3451 */     for (byte b = 0; b < vector2.size(); b++) {
/* 3452 */       EntityItem entityItem = vector2.elementAt(b);
/* 3453 */       if (entityItem.getEntityType().equals(paramString2)) {
/* 3454 */         vector1.addElement(entityItem);
/*      */       }
/*      */     } 
/*      */     
/* 3458 */     return vector1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getAllChildren(String paramString, int paramInt) {
/* 3474 */     return getAllEntities(paramString, paramInt, false, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getAllLinkedParents(String paramString, int paramInt) {
/* 3490 */     return getAllEntities(paramString, paramInt, true, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getAllLinkedChildren(String paramString, int paramInt) {
/* 3506 */     return getAllEntities(this.m_elist, paramString, paramInt, false, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getAllLinkedChildren(EntityList paramEntityList, String paramString, int paramInt) {
/* 3519 */     return getAllEntities(paramEntityList, paramString, paramInt, false, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getEntityIds(String paramString) {
/* 3531 */     return getEntityIds(this.m_elist, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getEntityIds(EntityList paramEntityList, String paramString) {
/* 3544 */     Vector<Integer> vector = new Vector(1);
/*      */     
/* 3546 */     EntityGroup entityGroup = paramEntityList.getEntityGroup(paramString);
/* 3547 */     if (entityGroup == null) {
/* 3548 */       return vector;
/*      */     }
/*      */ 
/*      */     
/* 3552 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */       
/* 3554 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 3555 */       if (entityItem.getEntityType().equals(paramString)) {
/* 3556 */         vector.addElement(new Integer(entityItem.getEntityID()));
/*      */       }
/*      */     } 
/*      */     
/* 3560 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector getAllEntities(String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
/* 3576 */     return getAllEntities(this.m_elist, paramString, paramInt, paramBoolean1, paramBoolean2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector getAllEntities(EntityList paramEntityList, String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
/* 3591 */     Vector<EntityItem> vector = new Vector(1);
/*      */ 
/*      */     
/* 3594 */     for (byte b = 0; b < paramEntityList.getEntityGroupCount(); b++) {
/* 3595 */       EntityGroup entityGroup = paramEntityList.getEntityGroup(b);
/*      */ 
/*      */ 
/*      */       
/* 3599 */       if (paramBoolean2 && entityGroup.getEntityItemCount() == 0)
/*      */       {
/* 3601 */         if (entityGroup.isRelator()) {
/* 3602 */           if (paramBoolean1) {
/* 3603 */             if (entityGroup.getEntity1Type().equals(paramString)) {
/* 3604 */               vector.addElement(entityGroup.getEntityItem(paramString));
/*      */             }
/*      */           }
/* 3607 */           else if (entityGroup.getEntity2Type().equals(paramString)) {
/* 3608 */             vector.addElement(entityGroup.getEntityItem(paramString));
/*      */           } 
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 3615 */       for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/*      */         
/* 3617 */         EntityItem entityItem = entityGroup.getEntityItem(b1);
/*      */         
/* 3619 */         int i = 0;
/* 3620 */         if (paramBoolean1) {
/* 3621 */           i = entityItem.getUpLinkCount();
/*      */         } else {
/* 3623 */           i = entityItem.getDownLinkCount();
/*      */         } 
/* 3625 */         for (byte b2 = 0; b2 < i; b2++) {
/* 3626 */           EntityItem entityItem1 = null;
/* 3627 */           if (paramBoolean1) {
/* 3628 */             entityItem1 = (EntityItem)entityItem.getUpLink(b2);
/*      */           } else {
/* 3630 */             entityItem1 = (EntityItem)entityItem.getDownLink(b2);
/*      */           } 
/* 3632 */           if (paramInt != 0) {
/* 3633 */             if (entityItem1.getEntityType().equals(paramString) && entityItem1.getEntityID() == paramInt)
/*      */             {
/*      */ 
/*      */               
/* 3637 */               vector.addElement(entityItem1);
/*      */             }
/*      */           }
/* 3640 */           else if (entityItem1.getEntityType().equals(paramString)) {
/*      */ 
/*      */ 
/*      */             
/* 3644 */             vector.addElement(entityItem1);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3651 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeValue(String paramString1, int paramInt, String paramString2) {
/* 3665 */     return getAttributeValue(this.m_elist, paramString1, paramInt, paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeValue(String paramString1, int paramInt, String paramString2, String paramString3) {
/* 3679 */     return getAttributeValue(this.m_elist, paramString1, paramInt, paramString2, paramString3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeValue(EntityList paramEntityList, String paramString1, int paramInt, String paramString2, String paramString3) {
/* 3696 */     String str = getAttributeValue(paramEntityList, paramString1, paramInt, paramString2);
/* 3697 */     if (str == null) {
/* 3698 */       str = paramString3;
/*      */     }
/* 3700 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeValue(EntityList paramEntityList, String paramString1, int paramInt, String paramString2) {
/* 3715 */     String str = null;
/*      */ 
/*      */     
/* 3718 */     D.ebug(4, "In getAttributeValue _strEntityType:" + paramString1 + ":_iEid:" + paramInt + ":m_strAttrCode:" + paramString2);
/* 3719 */     EntityGroup entityGroup = paramEntityList.getEntityGroup(paramString1);
/*      */     
/* 3721 */     if (entityGroup == null) {
/* 3722 */       EntityGroup entityGroup1 = paramEntityList.getParentEntityGroup();
/* 3723 */       if (entityGroup1 != null && entityGroup1.getEntityType().equals(paramString1)) {
/* 3724 */         entityGroup = entityGroup1;
/*      */       }
/*      */     } 
/* 3727 */     if (entityGroup == null) {
/* 3728 */       D.ebug(0, "getAttributeValue:entitygroup is null");
/* 3729 */       return null;
/*      */     } 
/*      */     
/* 3732 */     if (paramString1.equals(getEntityType()) && paramInt == getEntityID()) {
/* 3733 */       entityGroup = paramEntityList.getParentEntityGroup();
/*      */     }
/* 3735 */     EntityItem entityItem = entityGroup.getEntityItem(paramString1, paramInt);
/* 3736 */     if (entityItem == null) {
/* 3737 */       logMessage("getAttributeValue:entityitem is null");
/* 3738 */       return null;
/*      */     } 
/*      */     
/* 3741 */     EANAttribute eANAttribute = entityItem.getAttribute(paramString2);
/*      */     
/* 3743 */     if (eANAttribute == null) {
/* 3744 */       logMessage("getAttributeValue:entityattribute is null");
/* 3745 */       return null;
/*      */     } 
/* 3747 */     str = eANAttribute.toString();
/*      */     
/* 3749 */     EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(paramString2);
/* 3750 */     switch (eANMetaAttribute.getAttributeType().charAt(0)) {
/*      */       case 'F':
/*      */       case 'S':
/*      */       case 'U':
/* 3754 */         str = str.replace('\n', ' ');
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 3759 */     D.ebug(4, "Attribute values are " + str);
/*      */     
/* 3761 */     return str;
/*      */   }
/*      */   
/*      */   public String getAttributeValue(EntityItem paramEntityItem, String paramString1, String paramString2) {
/* 3765 */     String str = paramString2;
/*      */ 
/*      */     
/* 3768 */     if (paramEntityItem == null) {
/* 3769 */       logMessage("getAttributeValue:entityitem is null");
/* 3770 */       return str;
/*      */     } 
/*      */     
/* 3773 */     D.ebug(4, "In getAttributeValue " + paramEntityItem.getKey());
/* 3774 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/*      */     
/* 3776 */     if (entityGroup == null) {
/* 3777 */       logMessage("getAttributeValue:entitygroup is null");
/* 3778 */       return str;
/*      */     } 
/*      */ 
/*      */     
/* 3782 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString1);
/*      */     
/* 3784 */     if (eANAttribute == null) {
/* 3785 */       logMessage("getAttributeValue:entityattribute " + paramString1 + " is null");
/* 3786 */       return str;
/*      */     } 
/* 3788 */     str = eANAttribute.toString();
/*      */     
/* 3790 */     EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(paramString1);
/* 3791 */     switch (eANMetaAttribute.getAttributeType().charAt(0)) {
/*      */       case 'F':
/*      */       case 'S':
/*      */       case 'U':
/* 3795 */         str = str.replace('\n', ' ');
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 3800 */     D.ebug(4, "Attribute values are " + str);
/*      */     
/* 3802 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeFlagValue(String paramString1, int paramInt, String paramString2) {
/* 3815 */     return getAttributeValue(paramString1, paramInt, paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeFlagEnabledValue(String paramString1, int paramInt, String paramString2, String paramString3) {
/* 3829 */     return getAttributeFlagEnabledValue(this.m_elist, paramString1, paramInt, paramString2, paramString3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeFlagEnabledValue(EntityList paramEntityList, String paramString1, int paramInt, String paramString2, String paramString3) {
/* 3844 */     String str = getAttributeFlagEnabledValue(paramEntityList, paramString1, paramInt, paramString2);
/* 3845 */     if (str == null) {
/* 3846 */       str = paramString3;
/*      */     }
/* 3848 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeFlagEnabledValue(EntityList paramEntityList, String paramString1, int paramInt, String paramString2) {
/*      */     MetaFlag[] arrayOfMetaFlag;
/*      */     boolean bool;
/*      */     byte b;
/* 3862 */     String str1 = null;
/* 3863 */     String str2 = "";
/*      */     
/* 3865 */     D.ebug(4, "In getAttributeFlagEnabledValue _strEntityType:" + paramString1 + ":_iEid:" + paramInt + ":m_strAttrCode:" + paramString2);
/* 3866 */     EntityGroup entityGroup = paramEntityList.getEntityGroup(paramString1);
/*      */     
/* 3868 */     if (entityGroup == null) {
/* 3869 */       D.ebug(0, "getAttributeFlagEnabledValue:entitygroup is null");
/* 3870 */       return null;
/*      */     } 
/*      */     
/* 3873 */     if (paramString1.equals(getEntityType())) {
/* 3874 */       entityGroup = paramEntityList.getParentEntityGroup();
/*      */     }
/* 3876 */     EntityItem entityItem = entityGroup.getEntityItem(paramString1, paramInt);
/* 3877 */     if (entityItem == null) {
/* 3878 */       D.ebug(0, "getAttributeFlagEnabledValue:entityitem is null");
/* 3879 */       return null;
/*      */     } 
/*      */     
/* 3882 */     EANAttribute eANAttribute = entityItem.getAttribute(paramString2);
/*      */     
/* 3884 */     if (eANAttribute == null) {
/* 3885 */       D.ebug(0, "getAttributeValue:entityattribute is null");
/* 3886 */       return null;
/*      */     } 
/* 3888 */     str1 = eANAttribute.toString();
/*      */     
/* 3890 */     EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(paramString2);
/* 3891 */     switch (eANMetaAttribute.getAttributeType().charAt(0)) {
/*      */       case 'F':
/*      */       case 'S':
/*      */       case 'U':
/* 3895 */         arrayOfMetaFlag = (MetaFlag[])eANAttribute.get();
/* 3896 */         bool = true;
/* 3897 */         for (b = 0; b < arrayOfMetaFlag.length; b++) {
/* 3898 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 3899 */             if (bool) {
/* 3900 */               bool = false;
/* 3901 */               str2 = arrayOfMetaFlag[b].getFlagCode();
/*      */             } else {
/* 3903 */               str2 = str2 + "|" + arrayOfMetaFlag[b].getFlagCode();
/*      */             } 
/*      */           }
/*      */         } 
/* 3907 */         str1 = str2;
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3913 */     return str1;
/*      */   } public String getAttributeFlagEnabledValue(EntityItem paramEntityItem, String paramString) {
/*      */     MetaFlag[] arrayOfMetaFlag;
/*      */     boolean bool;
/*      */     byte b;
/* 3918 */     String str1 = "";
/* 3919 */     String str2 = "";
/* 3920 */     if (paramEntityItem == null) {
/* 3921 */       D.ebug(0, "getAttributeFlagEnabledValue:entityitem is null");
/* 3922 */       return null;
/*      */     } 
/*      */     
/* 3925 */     D.ebug(4, "In getAttributeFlagEnabledValue " + paramEntityItem.getKey() + ":" + paramString);
/* 3926 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/*      */ 
/*      */     
/* 3929 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString);
/*      */     
/* 3931 */     if (eANAttribute == null) {
/* 3932 */       D.ebug(0, "getAttributeFlagEnabledValue:entityattribute " + paramString + " is null");
/* 3933 */       return null;
/*      */     } 
/* 3935 */     str1 = eANAttribute.toString();
/*      */     
/* 3937 */     EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(paramString);
/* 3938 */     switch (eANMetaAttribute.getAttributeType().charAt(0)) {
/*      */       case 'F':
/*      */       case 'S':
/*      */       case 'U':
/* 3942 */         arrayOfMetaFlag = (MetaFlag[])eANAttribute.get();
/* 3943 */         bool = true;
/* 3944 */         for (b = 0; b < arrayOfMetaFlag.length; b++) {
/* 3945 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 3946 */             if (bool) {
/* 3947 */               bool = false;
/* 3948 */               str2 = arrayOfMetaFlag[b].getFlagCode();
/*      */             } else {
/* 3950 */               str2 = str2 + "|" + arrayOfMetaFlag[b].getFlagCode();
/*      */             } 
/*      */           }
/*      */         } 
/* 3954 */         str1 = str2;
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3960 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeShortFlagDesc(String paramString1, int paramInt, String paramString2, String paramString3) {
/* 3975 */     return getAttributeShortFlagDesc(this.m_elist, paramString1, paramInt, paramString2, paramString3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeShortFlagDesc(EntityList paramEntityList, String paramString1, int paramInt, String paramString2, String paramString3) {
/* 3990 */     String str = getAttributeShortFlagDesc(paramEntityList, paramString1, paramInt, paramString2);
/* 3991 */     if (str == null) {
/* 3992 */       str = paramString3;
/*      */     }
/* 3994 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeShortFlagDesc(EntityList paramEntityList, String paramString1, int paramInt, String paramString2) {
/*      */     MetaFlag[] arrayOfMetaFlag;
/*      */     byte b;
/* 4008 */     String str1 = null;
/* 4009 */     String str2 = "";
/*      */     
/* 4011 */     D.ebug(4, "In getAttributeShortFlagDesc _strEntityType:" + paramString1 + ":_iEid:" + paramInt + ":m_strAttrCode:" + paramString2);
/* 4012 */     EntityGroup entityGroup = paramEntityList.getEntityGroup(paramString1);
/*      */     
/* 4014 */     if (entityGroup == null) {
/* 4015 */       D.ebug(0, "getAttributeShortFlagDesc:entitygroup is null");
/* 4016 */       return null;
/*      */     } 
/*      */     
/* 4019 */     if (paramString1.equals(getEntityType())) {
/* 4020 */       entityGroup = paramEntityList.getParentEntityGroup();
/*      */     }
/* 4022 */     EntityItem entityItem = entityGroup.getEntityItem(paramString1, paramInt);
/* 4023 */     if (entityItem == null) {
/* 4024 */       D.ebug(0, "getAttributeShortFlagDesc:entityitem is null");
/* 4025 */       return null;
/*      */     } 
/*      */     
/* 4028 */     EANAttribute eANAttribute = entityItem.getAttribute(paramString2);
/*      */     
/* 4030 */     if (eANAttribute == null) {
/* 4031 */       D.ebug(0, "getAttributeShortFlagDesc:entityattribute is null");
/* 4032 */       return null;
/*      */     } 
/* 4034 */     str1 = eANAttribute.toString();
/*      */     
/* 4036 */     EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(paramString2);
/* 4037 */     switch (eANMetaAttribute.getAttributeType().charAt(0)) {
/*      */       case 'F':
/*      */       case 'S':
/*      */       case 'U':
/* 4041 */         arrayOfMetaFlag = (MetaFlag[])eANAttribute.get();
/* 4042 */         for (b = 0; b < arrayOfMetaFlag.length; b++) {
/* 4043 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 4044 */             if (arrayOfMetaFlag[b].getShortDescription() != null) {
/* 4045 */               if (str2.trim().length() > 0) {
/* 4046 */                 str2 = str2 + ", " + arrayOfMetaFlag[b].getShortDescription();
/*      */               } else {
/* 4048 */                 str2 = arrayOfMetaFlag[b].getShortDescription();
/*      */               } 
/*      */             } else {
/* 4051 */               D.ebug(0, "getAttributeShortFlagDesc:NULL returned for " + arrayOfMetaFlag[b].getFlagCode());
/*      */             } 
/*      */           }
/*      */         } 
/* 4055 */         str1 = str2;
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 4060 */     D.ebug(4, "getAttributeShortFlagDesc:Attribute values are " + str2);
/*      */     
/* 4062 */     return str1;
/*      */   }
/*      */   
/*      */   public String getAttributeShortFlagDesc(EntityItem paramEntityItem, String paramString1, String paramString2) {
/* 4066 */     String str = getAttributeShortFlagDesc(paramEntityItem, paramString1);
/* 4067 */     if (str == null) {
/* 4068 */       str = paramString2;
/*      */     }
/* 4070 */     return str;
/*      */   } public String getAttributeLongFlagDesc(EntityItem paramEntityItem, String paramString) {
/*      */     MetaFlag[] arrayOfMetaFlag;
/*      */     byte b;
/* 4074 */     String str1 = null;
/* 4075 */     String str2 = "";
/* 4076 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString);
/*      */     
/* 4078 */     if (eANAttribute == null) {
/* 4079 */       D.ebug(0, "getAttributeLongFlagDesc:entityattribute is null");
/* 4080 */       return null;
/*      */     } 
/* 4082 */     str1 = eANAttribute.toString();
/* 4083 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/*      */     
/* 4085 */     EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(paramString);
/* 4086 */     switch (eANMetaAttribute.getAttributeType().charAt(0)) {
/*      */       case 'F':
/*      */       case 'S':
/*      */       case 'U':
/* 4090 */         arrayOfMetaFlag = (MetaFlag[])eANAttribute.get();
/* 4091 */         for (b = 0; b < arrayOfMetaFlag.length; b++) {
/* 4092 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 4093 */             if (arrayOfMetaFlag[b].getShortDescription() != null) {
/* 4094 */               if (str2.trim().length() > 0) {
/* 4095 */                 str2 = str2 + "| " + arrayOfMetaFlag[b].getLongDescription();
/*      */               } else {
/* 4097 */                 str2 = arrayOfMetaFlag[b].getLongDescription();
/*      */               } 
/*      */             } else {
/* 4100 */               D.ebug(0, "getAttributeLongFlagDesc:NULL returned for " + arrayOfMetaFlag[b].getFlagCode());
/*      */             } 
/*      */           }
/*      */         } 
/* 4104 */         str1 = str2;
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 4109 */     D.ebug(4, "getAttributeLongFlagDesc:Attribute values are " + str2);
/*      */     
/* 4111 */     return str1;
/*      */   } public String getAttributeShortFlagDesc(EntityItem paramEntityItem, String paramString) {
/*      */     MetaFlag[] arrayOfMetaFlag;
/*      */     byte b;
/* 4115 */     String str1 = null;
/* 4116 */     String str2 = "";
/* 4117 */     if (paramEntityItem == null) {
/* 4118 */       D.ebug(0, "getAttributeShortFlagDesc:entityitem is null");
/* 4119 */       return null;
/*      */     } 
/*      */     
/* 4122 */     D.ebug(4, "In getAttributeShortFlagDesc : " + paramEntityItem.getKey() + "m_strAttrCode:" + paramString);
/* 4123 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/*      */     
/* 4125 */     if (entityGroup == null) {
/* 4126 */       D.ebug(0, "getAttributeShortFlagDesc:entitygroup is null");
/* 4127 */       return null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4132 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString);
/*      */     
/* 4134 */     if (eANAttribute == null) {
/* 4135 */       D.ebug(0, "getAttributeShortFlagDesc:entityattribute is null");
/* 4136 */       return null;
/*      */     } 
/* 4138 */     str1 = eANAttribute.toString();
/*      */     
/* 4140 */     EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(paramString);
/* 4141 */     switch (eANMetaAttribute.getAttributeType().charAt(0)) {
/*      */       case 'F':
/*      */       case 'S':
/*      */       case 'U':
/* 4145 */         arrayOfMetaFlag = (MetaFlag[])eANAttribute.get();
/* 4146 */         for (b = 0; b < arrayOfMetaFlag.length; b++) {
/* 4147 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 4148 */             if (arrayOfMetaFlag[b].getShortDescription() != null) {
/* 4149 */               if (str2.trim().length() > 0) {
/* 4150 */                 str2 = str2 + ", " + arrayOfMetaFlag[b].getShortDescription();
/*      */               } else {
/* 4152 */                 str2 = arrayOfMetaFlag[b].getShortDescription();
/*      */               } 
/*      */             } else {
/* 4155 */               D.ebug(0, "getAttributeShortFlagDesc:NULL returned for " + arrayOfMetaFlag[b].getFlagCode());
/*      */             } 
/*      */           }
/*      */         } 
/* 4159 */         str1 = str2;
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 4164 */     D.ebug(4, "getAttributeShortFlagDesc:Attribute values are " + str2);
/*      */     
/* 4166 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getEntityIds(String paramString1, String paramString2) {
/* 4183 */     return getEntityIds(this.m_elist, paramString1, paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getEntityIds(EntityList paramEntityList, String paramString1, String paramString2) {
/* 4197 */     Vector<Integer> vector = new Vector(1);
/*      */ 
/*      */     
/* 4200 */     EntityGroup entityGroup = paramEntityList.getEntityGroup(paramString2);
/*      */     
/* 4202 */     if (entityGroup == null) {
/* 4203 */       return vector;
/*      */     }
/*      */     
/* 4206 */     if (entityGroup.getEntityItemCount() == 0) {
/* 4207 */       return vector;
/*      */     }
/*      */ 
/*      */     
/* 4211 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 4212 */       EntityItem entityItem = entityGroup.getEntityItem(b); byte b1;
/* 4213 */       for (b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/* 4214 */         if (entityItem.getDownLink(b1).getEntityType().equals(paramString1)) {
/* 4215 */           Integer integer = new Integer(entityItem.getDownLink(b1).getEntityID());
/* 4216 */           if (!vector.contains(integer)) {
/* 4217 */             vector.addElement(integer);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 4222 */       for (b1 = 0; b1 < entityItem.getUpLinkCount(); b1++) {
/* 4223 */         if (entityItem.getUpLink(b1).getEntityType().equals(paramString1)) {
/* 4224 */           Integer integer = new Integer(entityItem.getUpLink(b1).getEntityID());
/* 4225 */           if (!vector.contains(integer)) {
/* 4226 */             vector.addElement(integer);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 4231 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getParentEntityIds(String paramString1, int paramInt, String paramString2, String paramString3) {
/* 4247 */     return getParentEntityIds(this.m_elist, paramString1, paramInt, paramString2, paramString3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getParentEntityIds(EntityList paramEntityList, String paramString1, int paramInt, String paramString2, String paramString3) {
/* 4262 */     Vector<Integer> vector = new Vector(1);
/*      */ 
/*      */     
/* 4265 */     EntityGroup entityGroup = null;
/* 4266 */     if (paramString3.equals(getEntityType())) {
/* 4267 */       entityGroup = paramEntityList.getParentEntityGroup();
/*      */     } else {
/* 4269 */       entityGroup = paramEntityList.getEntityGroup(paramString3);
/*      */     } 
/*      */     
/* 4272 */     if (entityGroup == null) {
/* 4273 */       return vector;
/*      */     }
/*      */     
/* 4276 */     System.out.println("PokBaseABR getParentEntityIds entGroup: " + entityGroup.getEntityType() + ":" + entityGroup.getEntityItemCount());
/* 4277 */     if (entityGroup.getEntityItemCount() == 0) {
/* 4278 */       return vector;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 4283 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 4284 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */ 
/*      */       
/* 4288 */       for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/* 4289 */         if (entityItem.getDownLink(b1).getEntityType().equals(paramString1) && entityItem.getDownLink(b1).getEntityID() == paramInt) {
/* 4290 */           for (byte b2 = 0; b2 < entityItem.getUpLinkCount(); b2++) {
/*      */             
/* 4292 */             if (entityItem.getUpLink(b2).getEntityType().equals(paramString2)) {
/* 4293 */               vector.addElement(new Integer(entityItem.getUpLink(b2).getEntityID()));
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4328 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getParentEntityId(String paramString1, int paramInt, String paramString2, String paramString3) {
/* 4343 */     return getParentEntityId(this.m_elist, paramString1, paramInt, paramString2, paramString3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getParentEntityId(EntityList paramEntityList, String paramString1, int paramInt, String paramString2, String paramString3) {
/* 4358 */     Vector<Integer> vector = getParentEntityIds(paramEntityList, paramString1, paramInt, paramString2, paramString3);
/* 4359 */     if (vector.size() > 0) {
/* 4360 */       Integer integer = vector.elementAt(0);
/* 4361 */       return integer.intValue();
/*      */     } 
/* 4363 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getChildEntityId(String paramString1, int paramInt, String paramString2, String paramString3) {
/* 4379 */     Vector<Integer> vector = getChildrenEntityIds(paramString1, paramInt, paramString2, paramString3);
/* 4380 */     if (vector.size() > 0) {
/* 4381 */       Integer integer = vector.elementAt(0);
/* 4382 */       return integer.intValue();
/*      */     } 
/* 4384 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getChildrenEntityIds(String paramString1, int paramInt, String paramString2, String paramString3) {
/* 4398 */     return getChildrenEntityIds(this.m_elist, paramString1, paramInt, paramString2, paramString3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getChildrenEntityIds(EntityList paramEntityList, String paramString1, int paramInt, String paramString2, String paramString3) {
/* 4413 */     Vector<Integer> vector = new Vector(1);
/*      */ 
/*      */     
/* 4416 */     EntityGroup entityGroup = null;
/* 4417 */     if (paramString3.equals(getEntityType())) {
/* 4418 */       entityGroup = paramEntityList.getParentEntityGroup();
/*      */     } else {
/* 4420 */       entityGroup = paramEntityList.getEntityGroup(paramString3);
/*      */     } 
/*      */     
/* 4423 */     if (entityGroup == null) {
/* 4424 */       return vector;
/*      */     }
/*      */     
/* 4427 */     if (entityGroup.getEntityItemCount() == 0) {
/* 4428 */       return vector;
/*      */     }
/*      */ 
/*      */     
/* 4432 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 4433 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */ 
/*      */       
/* 4437 */       for (byte b1 = 0; b1 < entityItem.getUpLinkCount(); b1++) {
/* 4438 */         if (entityItem.getUpLink(b1).getEntityType().equals(paramString1) && entityItem.getUpLink(b1).getEntityID() == paramInt) {
/* 4439 */           for (byte b2 = 0; b2 < entityItem.getDownLinkCount(); b2++) {
/*      */             
/* 4441 */             if (entityItem.getDownLink(b2).getEntityType().equals(paramString2)) {
/* 4442 */               vector.addElement(new Integer(entityItem.getDownLink(b2).getEntityID()));
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 4449 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void hide(String paramString) {
/* 4460 */     this.m_hsetSkipType.add(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void printPathDown(String paramString1, int paramInt, String paramString2, String paramString3) {
/* 4474 */     printPathDown(paramString1, paramInt, paramString2, paramString3, 0);
/* 4475 */     if (this.m_iErrors > 0) {
/* 4476 */       println("<h3>Warning: There were " + this.m_iErrors + " paths that were not m_hDisplayed because they had more than " + '\017' + " entities in them.</h3>");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void printPathDown(String paramString1, int paramInt1, String paramString2, String paramString3, int paramInt2) {
/* 4492 */     if (paramString2.equals(paramString1)) {
/* 4493 */       println("<tr>\n" + paramString3 + "</tr>");
/*      */     }
/* 4495 */     if (paramInt2 > 15) {
/* 4496 */       this.m_iErrors++;
/*      */       
/*      */       return;
/*      */     } 
/* 4500 */     paramInt2++;
/*      */     
/* 4502 */     String str = getAttributeValue(paramString1, paramInt1, "NAME", "<em>** Not Populated **</em>");
/* 4503 */     if (this.m_hDisplay.containsKey(paramString1))
/*      */     {
/* 4505 */       str = getAttributeValue(paramString1, paramInt1, (String)this.m_hDisplay
/*      */ 
/*      */           
/* 4508 */           .get(paramString1), "<em>** Not Populated **</em>");
/*      */     }
/*      */ 
/*      */     
/* 4512 */     Vector<EntityItem> vector = getAllLinkedChildren(paramString1, paramInt1);
/* 4513 */     for (byte b = 0; b < vector.size(); b++) {
/* 4514 */       EntityItem entityItem = vector.elementAt(b);
/* 4515 */       String str1 = "";
/* 4516 */       if (!this.m_hsetSkipType.contains(paramString1))
/*      */       {
/*      */         
/* 4519 */         str1 = "<td class=\"PsgText\"><!--" + getEntityDescription(paramString1) + "-->" + str + "</td>\n";
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 4524 */       printPathDown(entityItem
/* 4525 */           .getEntityType(), entityItem
/* 4526 */           .getEntityID(), paramString2, paramString3 + str1, paramInt2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetDisplay(String paramString) {
/* 4541 */     this.m_hDisplay.remove(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDisplay(String paramString1, String paramString2) {
/* 4553 */     this.m_hDisplay.put(paramString1, paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void show(String paramString) {
/* 4564 */     this.m_hsetSkipType.remove(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printEntity(String paramString, int paramInt1, int paramInt2) {
/* 4576 */     D.ebug(4, "In printEntity _strEntityType" + paramString + ":_iEntityID:" + paramInt1 + ":_iLevel:" + paramInt2);
/*      */     
/* 4578 */     String str = "";
/* 4579 */     switch (paramInt2) {
/*      */       
/*      */       case 0:
/* 4582 */         str = "PsgReportSection";
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/* 4587 */         str = "PsgReportSectionII";
/*      */         break;
/*      */ 
/*      */       
/*      */       case 2:
/* 4592 */         str = "PsgReportSectionIII";
/*      */         break;
/*      */ 
/*      */       
/*      */       case 3:
/* 4597 */         str = "PsgReportSectionIV";
/*      */         break;
/*      */ 
/*      */       
/*      */       default:
/* 4602 */         str = "PsgReportSectionV";
/*      */         break;
/*      */     } 
/*      */     
/* 4606 */     D.ebug(4, "Printing table width");
/* 4607 */     println("<table width=\"100%\"><tr><td class=\"" + str + "\">" + getEntityDescription(paramString) + ": " + getAttributeValue(paramString, paramInt1, "NAME", "<em>** Not Populated **</em>") + "</td></tr></table>");
/* 4608 */     D.ebug(4, "Printing Attributes");
/* 4609 */     printAttributes(paramString, paramInt1, false, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printAttributes(String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
/* 4622 */     printAttributes(this.m_elist, paramString, paramInt, paramBoolean1, paramBoolean2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printAttributes(EntityList paramEntityList, String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
/* 4637 */     D.ebug(4, "in Print Attributes _strEntityType" + paramString + ":_iEntityID:" + paramInt);
/*      */     
/* 4639 */     EntityItem entityItem = null;
/* 4640 */     EntityGroup entityGroup = null;
/* 4641 */     if (paramString.equals(getEntityType())) {
/* 4642 */       entityGroup = paramEntityList.getParentEntityGroup();
/*      */     } else {
/* 4644 */       entityGroup = paramEntityList.getEntityGroup(paramString);
/*      */     } 
/*      */     
/* 4647 */     if (entityGroup == null) {
/* 4648 */       println("<h3>Warning: Cannot locate an EnityGroup for " + paramString + " so no attributes will be printed.</h3>");
/*      */       
/*      */       return;
/*      */     } 
/* 4652 */     entityItem = entityGroup.getEntityItem(paramString, paramInt);
/*      */     
/* 4654 */     if (entityItem == null) {
/*      */       
/* 4656 */       entityItem = entityGroup.getEntityItem(0);
/* 4657 */       println("<h3>Warning: Attributes for " + paramString + ":" + paramInt + " cannot be printed as it is not available in the Extract.</h3>");
/* 4658 */       println("<h3>Warning: Root Entityis " + getEntityType() + ":" + getEntityID() + ".</h3>");
/*      */       
/*      */       return;
/*      */     } 
/* 4662 */     String str = entityGroup.getLongDescription();
/* 4663 */     D.ebug(4, "Print Attributes Entity desc is " + str);
/* 4664 */     D.ebug(4, "Attribute count is" + entityItem.getAttributeCount());
/* 4665 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 4666 */     Vector<String> vector = new Vector();
/* 4667 */     for (byte b1 = 0; b1 < entityItem.getAttributeCount(); b1++) {
/*      */       
/* 4669 */       EANAttribute eANAttribute = entityItem.getAttribute(b1);
/* 4670 */       D.ebug(4, "printAttributes " + eANAttribute.dump(false));
/* 4671 */       D.ebug(4, "printAttributes " + eANAttribute.dump(true));
/*      */       
/* 4673 */       String str1 = getAttributeValue(paramString, paramInt, eANAttribute.getAttributeCode(), "<em>** Not Populated **</em>");
/* 4674 */       String str2 = "";
/*      */ 
/*      */       
/* 4677 */       if (paramBoolean2) {
/* 4678 */         str2 = getMetaDescription(paramString, eANAttribute.getAttributeCode(), false);
/*      */       } else {
/* 4680 */         str2 = getAttributeDescription(paramString, eANAttribute.getAttributeCode());
/*      */       } 
/*      */       
/* 4683 */       if (str2.length() > str.length() && str2.substring(0, str.length()).equalsIgnoreCase(str)) {
/* 4684 */         str2 = str2.substring(str.length());
/*      */       }
/*      */       
/* 4687 */       if (paramBoolean1 || str1 != null) {
/*      */         
/* 4689 */         hashtable.put(str2, str1);
/*      */         
/* 4691 */         vector.add(str2);
/*      */       } 
/*      */     } 
/* 4694 */     String[] arrayOfString = new String[entityItem.getAttributeCount()];
/*      */     
/* 4696 */     if (!paramBoolean1) {
/* 4697 */       arrayOfString = new String[vector.size()];
/* 4698 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 4699 */         arrayOfString[b] = vector.elementAt(b);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 4704 */     SortUtil sortUtil = new SortUtil();
/* 4705 */     sortUtil.sort(arrayOfString);
/*      */ 
/*      */     
/* 4708 */     println("<table width=\"100%\">");
/* 4709 */     int i = arrayOfString.length - arrayOfString.length / 2;
/* 4710 */     for (byte b2 = 0; b2 < i; b2++) {
/* 4711 */       println("<tr><td class=\"PsgLabel\" valign=\"top\">" + arrayOfString[b2] + "</td><td class=\"PsgText\" valign=\"top\">" + hashtable
/*      */ 
/*      */           
/* 4714 */           .get(arrayOfString[b2]) + "</td>");
/*      */       
/* 4716 */       int j = i + b2;
/* 4717 */       if (j < arrayOfString.length) {
/* 4718 */         println("<td class=\"PsgLabel\" valign=\"top\">" + arrayOfString[j] + "</td><td class=\"PsgText\" valign=\"top\">" + hashtable
/*      */ 
/*      */ 
/*      */             
/* 4722 */             .get(arrayOfString[j]) + "</td><tr>");
/*      */       } else {
/*      */         
/* 4725 */         println("<td class=\"PsgLabel\"></td><td class=\"PsgText\"></td><tr>");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4731 */     println("</table>\n<br />");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set getPathDIVtoSG(Hashtable paramHashtable1, Hashtable paramHashtable2, Hashtable paramHashtable3, Hashtable paramHashtable4) {
/* 4746 */     return getPathDIVtoSG(this.m_elist, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set getPathDIVtoSG(EntityList paramEntityList, Hashtable<Integer, String> paramHashtable1, Hashtable<Integer, String> paramHashtable2, Hashtable<Integer, String> paramHashtable3, Hashtable<Integer, String> paramHashtable4) {
/* 4763 */     HashSet<String> hashSet = new HashSet();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4772 */     Vector<Integer> vector = getEntityIds("PSGDIV", "PSGDIVBR");
/* 4773 */     for (byte b = 0; b < vector.size(); b++) {
/* 4774 */       Integer integer = vector.elementAt(b);
/* 4775 */       String str = getAttributeValue(paramEntityList, "PSGDIV", integer.intValue(), "NAME", "<em>** Not Populated **</em>");
/* 4776 */       paramHashtable1.put(integer, str);
/*      */       
/* 4778 */       Vector<Integer> vector1 = getChildrenEntityIds(paramEntityList, "PSGDIV", integer.intValue(), "PSGBR", "PSGDIVBR");
/* 4779 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 4780 */         Integer integer1 = vector1.elementAt(b1);
/* 4781 */         String str1 = getAttributeValue(paramEntityList, "PSGBR", integer1.intValue(), "NAME", "<em>** Not Populated **</em>");
/* 4782 */         paramHashtable2.put(integer1, str1);
/*      */         
/* 4784 */         Vector<Integer> vector2 = getChildrenEntityIds(paramEntityList, "PSGBR", integer1.intValue(), "PSGMBR", "PSGBRMBR");
/* 4785 */         for (byte b2 = 0; b2 < vector2.size(); b2++) {
/* 4786 */           Integer integer2 = vector2.elementAt(b2);
/* 4787 */           String str2 = getAttributeValue(paramEntityList, "PSGMBR", integer2.intValue(), "NAME", "<em>** Not Populated **</em>");
/* 4788 */           paramHashtable3.put(integer2, str2);
/*      */           
/* 4790 */           Vector<Integer> vector3 = getChildrenEntityIds(paramEntityList, "PSGMBR", integer2.intValue(), "PSGGR", "PSGMBRGR");
/* 4791 */           for (byte b3 = 0; b3 < vector3.size(); b3++) {
/* 4792 */             Integer integer3 = vector3.elementAt(b3);
/* 4793 */             String str3 = getAttributeValue(paramEntityList, "PSGGR", integer3.intValue(), "NAME", "<em>** Not Populated **</em>");
/*      */             
/* 4795 */             Vector<Integer> vector4 = getChildrenEntityIds("PSGGR", integer3.intValue(), "PSGSG", "PSGGRSG");
/* 4796 */             for (byte b4 = 0; b4 < vector4.size(); b4++) {
/* 4797 */               Integer integer4 = vector4.elementAt(b4);
/* 4798 */               String str4 = getAttributeValue(paramEntityList, "PSGSG", integer4.intValue(), "NAME", "<em>** Not Populated **</em>");
/* 4799 */               paramHashtable4.put(integer4, str4);
/* 4800 */               hashSet.add("<td class=\"PsgText\"><!--MBR-->" + str2 + "</td><td class=\"PsgText\"><!--GR-->" + str3 + "</td><td class=\"PsgText\"><!--SG-->" + str4 + "</td>");
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4814 */     return hashSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String concatDIV_MBR_SG(Hashtable paramHashtable1, Hashtable paramHashtable2, Hashtable paramHashtable3) {
/* 4828 */     String str = "";
/* 4829 */     if (paramHashtable1.size() == 0) {
/* 4830 */       str = str + "||Division=ORPHAN-DIV";
/*      */     }
/* 4832 */     Enumeration enumeration = paramHashtable1.keys();
/* 4833 */     while (enumeration.hasMoreElements()) {
/* 4834 */       str = str + "||Division=" + paramHashtable1.get(enumeration.nextElement());
/*      */     }
/* 4836 */     enumeration = paramHashtable2.keys();
/* 4837 */     while (enumeration.hasMoreElements()) {
/* 4838 */       str = str + "||Category2=" + paramHashtable2.get(enumeration.nextElement());
/*      */     }
/* 4840 */     enumeration = paramHashtable3.keys();
/* 4841 */     while (enumeration.hasMoreElements()) {
/* 4842 */       str = str + "||Category3=" + paramHashtable3.get(enumeration.nextElement());
/*      */     }
/* 4844 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String concatDIV_MBR_SG(Hashtable paramHashtable1, Hashtable paramHashtable2, Hashtable paramHashtable3, Hashtable paramHashtable4) {
/* 4859 */     String str = "";
/* 4860 */     if (paramHashtable1.size() == 0) {
/* 4861 */       str = str + "||Division=ORPHAN-DIV";
/*      */     }
/* 4863 */     Enumeration enumeration = paramHashtable1.keys();
/* 4864 */     while (enumeration.hasMoreElements()) {
/* 4865 */       str = str + "||Division=" + paramHashtable1.get(enumeration.nextElement());
/*      */     }
/* 4867 */     enumeration = paramHashtable2.keys();
/* 4868 */     while (enumeration.hasMoreElements()) {
/* 4869 */       str = str + "||Category2=$BR$=" + paramHashtable2.get(enumeration.nextElement());
/*      */     }
/* 4871 */     enumeration = paramHashtable3.keys();
/* 4872 */     while (enumeration.hasMoreElements()) {
/* 4873 */       str = str + "||Category2=$MBR$" + paramHashtable3.get(enumeration.nextElement());
/*      */     }
/* 4875 */     enumeration = paramHashtable4.keys();
/* 4876 */     while (enumeration.hasMoreElements()) {
/* 4877 */       str = str + "||Category3=" + paramHashtable4.get(enumeration.nextElement());
/*      */     }
/* 4879 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printPath(Set paramSet) {
/* 4890 */     SortUtil sortUtil = new SortUtil();
/* 4891 */     String[] arrayOfString = new String[paramSet.size()];
/* 4892 */     Iterator<String> iterator = paramSet.iterator();
/* 4893 */     byte b = 0;
/* 4894 */     while (iterator.hasNext()) {
/* 4895 */       arrayOfString[b++] = iterator.next();
/*      */     }
/* 4897 */     sortUtil.sort(arrayOfString);
/* 4898 */     for (b = 0; b < arrayOfString.length; b++) {
/* 4899 */       println("<tr>" + arrayOfString[b] + "</tr>");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set getPathSOL(Hashtable paramHashtable1, Hashtable paramHashtable2, Hashtable paramHashtable3, Hashtable paramHashtable4) {
/* 4915 */     HashSet hashSet = new HashSet();
/* 4916 */     Vector<Integer> vector1 = getParentEntityIds(getABREntityType(), getABREntityID(), "PSGCSOL", "PSGCSOLSOL");
/* 4917 */     for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 4918 */       Integer integer = vector1.elementAt(b1);
/* 4919 */       String str = getAttributeValue("PSGCSOL", integer.intValue(), "NAME");
/*      */       try {
/* 4921 */         ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTPSGVECSOL1");
/* 4922 */         EntityItem[] arrayOfEntityItem = (EntityItem[])Array.newInstance(EntityItem.class, 1);
/* 4923 */         Array.set(arrayOfEntityItem, 0, new EntityItem(null, this.m_prof, "PSGCSOL", integer.intValue()));
/* 4924 */         D.ebug(4, "Creating Entity List for EXTPSGVECSOL1");
/* 4925 */         D.ebug(4, "Profile is " + this.m_prof);
/* 4926 */         D.ebug(4, "Extractaction Item is" + extractActionItem);
/* 4927 */         D.ebug(4, "Entity Item" + arrayOfEntityItem);
/* 4928 */         EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, arrayOfEntityItem);
/* 4929 */         appendPath(hashSet, getPathCSOL(entityList, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4), "<!--CSOL-->" + str);
/* 4930 */       } catch (Exception exception) {
/* 4931 */         D.ebug(0, "Badness #3" + exception.getMessage());
/*      */       } 
/*      */     } 
/* 4934 */     Vector<Integer> vector2 = getParentEntityIds(getABREntityType(), getABREntityID(), "PSGROF", "PSGROFSOL");
/* 4935 */     for (byte b2 = 0; b2 < vector2.size(); b2++) {
/* 4936 */       Integer integer = vector2.elementAt(b2);
/* 4937 */       String str = getAttributeValue("PSGROF", integer.intValue(), "NAME");
/*      */       try {
/* 4939 */         ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTPSGVEROF1");
/* 4940 */         EntityItem[] arrayOfEntityItem = (EntityItem[])Array.newInstance(EntityItem.class, 1);
/* 4941 */         Array.set(arrayOfEntityItem, 0, new EntityItem(null, this.m_prof, "PSGROF", integer.intValue()));
/* 4942 */         D.ebug(4, "Creating Entity List for EXTPSGVEROF1");
/* 4943 */         D.ebug(4, "Profile is " + this.m_prof);
/* 4944 */         D.ebug(4, "Extractaction Item is" + extractActionItem);
/* 4945 */         D.ebug(4, "Entity Item" + arrayOfEntityItem);
/* 4946 */         EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, arrayOfEntityItem);
/*      */         
/* 4948 */         appendPath(hashSet, getPathROF(entityList, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4), "<!--ROF-->" + str);
/* 4949 */       } catch (Exception exception) {
/* 4950 */         D.ebug(0, "Badness #4" + exception.getMessage());
/*      */       } 
/*      */     } 
/* 4953 */     Vector<Integer> vector3 = getParentEntityIds(getABREntityType(), getABREntityID(), "PSGSGR", "PSGSGRSOL");
/* 4954 */     for (byte b3 = 0; b3 < vector3.size(); b3++) {
/* 4955 */       Integer integer = vector3.elementAt(b3);
/* 4956 */       String str = getAttributeValue("PSGSGR", integer.intValue(), "NAME");
/*      */       
/*      */       try {
/* 4959 */         ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTPSGVESGR1");
/* 4960 */         EntityItem[] arrayOfEntityItem = (EntityItem[])Array.newInstance(EntityItem.class, 1);
/* 4961 */         Array.set(arrayOfEntityItem, 0, new EntityItem(null, this.m_prof, "PSGSGR", integer.intValue()));
/* 4962 */         D.ebug(4, "Creating Entity List for EXTPSGVESGR1");
/* 4963 */         D.ebug(4, "Profile is " + this.m_prof);
/* 4964 */         D.ebug(4, "Extractaction Item is" + extractActionItem);
/* 4965 */         D.ebug(4, "Entity Item" + arrayOfEntityItem);
/* 4966 */         EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, arrayOfEntityItem);
/*      */         
/* 4968 */         appendPath(hashSet, getPathDIVtoSG(entityList, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4), "<!--SGR-->" + str);
/* 4969 */       } catch (Exception exception) {
/* 4970 */         D.ebug(0, "Badness #5" + exception.getMessage());
/*      */       } 
/*      */     } 
/* 4973 */     Vector<Integer> vector4 = getParentEntityIds(getABREntityType(), getABREntityID(), "PSGCATM", "PSGCATMSOL");
/* 4974 */     for (byte b4 = 0; b4 < vector4.size(); b4++) {
/* 4975 */       Integer integer = vector4.elementAt(b4);
/* 4976 */       String str = getAttributeValue("PSGCATM", integer.intValue(), "NAME");
/*      */       try {
/* 4978 */         ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTPSGVECATM1");
/* 4979 */         EntityItem[] arrayOfEntityItem = (EntityItem[])Array.newInstance(EntityItem.class, 1);
/* 4980 */         Array.set(arrayOfEntityItem, 0, new EntityItem(null, this.m_prof, "PSGCATM", integer.intValue()));
/* 4981 */         D.ebug(4, "Creating Entity List for EXTPSGVECATM1");
/* 4982 */         D.ebug(4, "Profile is " + this.m_prof);
/* 4983 */         D.ebug(4, "Extractaction Item is" + extractActionItem);
/* 4984 */         D.ebug(4, "Entity Item" + arrayOfEntityItem);
/* 4985 */         EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, arrayOfEntityItem);
/*      */         
/* 4987 */         appendPath(hashSet, getPathCATM(entityList, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4), "<!--CATM-->" + str);
/* 4988 */       } catch (Exception exception) {
/* 4989 */         D.ebug(0, "Badness #6" + exception.getMessage());
/*      */       } 
/*      */     } 
/*      */     
/* 4993 */     return hashSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set getPathCSOL(Hashtable paramHashtable1, Hashtable paramHashtable2, Hashtable paramHashtable3, Hashtable paramHashtable4) {
/* 5008 */     return getPathCSOL(this.m_elist, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set getPathCSOL(EntityList paramEntityList, Hashtable paramHashtable1, Hashtable paramHashtable2, Hashtable paramHashtable3, Hashtable paramHashtable4) {
/* 5023 */     HashSet hashSet = new HashSet();
/* 5024 */     Vector<Integer> vector = getEntityIds(paramEntityList, "PSGCSGR", "PSGCSGRCSOL");
/* 5025 */     for (byte b = 0; b < vector.size(); b++) {
/* 5026 */       Integer integer = vector.elementAt(b);
/*      */       try {
/* 5028 */         ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTPSGVECSGR1");
/* 5029 */         EntityItem[] arrayOfEntityItem = (EntityItem[])Array.newInstance(EntityItem.class, 1);
/* 5030 */         Array.set(arrayOfEntityItem, 0, new EntityItem(null, this.m_prof, "PSGCSGR", integer.intValue()));
/* 5031 */         D.ebug(4, "Creating Entity List for EXTPSGVECSGR1");
/* 5032 */         D.ebug(4, "Profile is " + this.m_prof);
/* 5033 */         D.ebug(4, "Extractaction Item is" + extractActionItem);
/* 5034 */         D.ebug(4, "Entity Item" + arrayOfEntityItem);
/* 5035 */         EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, arrayOfEntityItem);
/* 5036 */         hashSet.addAll(getPathDIVtoCSGR(entityList, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4));
/* 5037 */       } catch (Exception exception) {
/* 5038 */         D.ebug(0, "Badness:" + exception.getMessage());
/*      */       } 
/*      */     } 
/* 5041 */     return hashSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set getPathDIVtoCSGR(Hashtable paramHashtable1, Hashtable paramHashtable2, Hashtable paramHashtable3, Hashtable paramHashtable4) {
/* 5056 */     return getPathDIVtoCSGR(this.m_elist, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set getPathDIVtoCSGR(EntityList paramEntityList, Hashtable<Integer, String> paramHashtable1, Hashtable<Integer, String> paramHashtable2, Hashtable<Integer, String> paramHashtable3, Hashtable<Integer, String> paramHashtable4) {
/* 5072 */     HashSet<String> hashSet = new HashSet();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5081 */     Vector<Integer> vector = getEntityIds(paramEntityList, "PSGDIV", "PSGDIVBR");
/* 5082 */     for (byte b = 0; b < vector.size(); b++) {
/* 5083 */       Integer integer = vector.elementAt(b);
/* 5084 */       String str = getAttributeValue(paramEntityList, "PSGDIV", integer.intValue(), "NAME");
/* 5085 */       paramHashtable1.put(integer, str);
/*      */       
/* 5087 */       Vector<Integer> vector1 = getChildrenEntityIds(paramEntityList, "PSGDIV", integer.intValue(), "PSGBR", "PSGDIVBR");
/* 5088 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 5089 */         Integer integer1 = vector1.elementAt(b1);
/* 5090 */         String str1 = getAttributeValue(paramEntityList, "PSGBR", integer1.intValue(), "NAME");
/* 5091 */         paramHashtable2.put(integer1, str1);
/*      */         
/* 5093 */         Vector<Integer> vector2 = getChildrenEntityIds(paramEntityList, "PSGBR", integer1.intValue(), "PSGMBR", "PSGBRMBR");
/* 5094 */         for (byte b2 = 0; b2 < vector2.size(); b2++) {
/* 5095 */           Integer integer2 = vector2.elementAt(b2);
/* 5096 */           String str2 = getAttributeValue(paramEntityList, "PSGMBR", integer2.intValue(), "NAME");
/* 5097 */           paramHashtable3.put(integer2, str2);
/*      */           
/* 5099 */           Vector<Integer> vector3 = getChildrenEntityIds(paramEntityList, "PSGMBR", integer2.intValue(), "PSGGR", "PSGMBRGR");
/* 5100 */           for (byte b3 = 0; b3 < vector3.size(); b3++) {
/* 5101 */             Integer integer3 = vector3.elementAt(b3);
/* 5102 */             String str3 = getAttributeValue(paramEntityList, "PSGGR", integer3.intValue(), "NAME");
/*      */             
/* 5104 */             Vector<Integer> vector4 = getChildrenEntityIds(paramEntityList, "PSGGR", integer3.intValue(), "PSGSG", "PSGGRSG");
/* 5105 */             for (byte b4 = 0; b4 < vector4.size(); b4++) {
/* 5106 */               Integer integer4 = vector4.elementAt(b4);
/* 5107 */               String str4 = getAttributeValue(paramEntityList, "PSGSG", integer4.intValue(), "NAME");
/* 5108 */               paramHashtable4.put(integer4, str4);
/* 5109 */               Vector<Integer> vector5 = getChildrenEntityIds(paramEntityList, "PSGSG", integer4.intValue(), "PSGCT", "PSGSGCT");
/* 5110 */               for (byte b5 = 0; b5 < vector5.size(); b5++) {
/* 5111 */                 Integer integer5 = vector5.elementAt(b5);
/* 5112 */                 String str5 = getAttributeValue(paramEntityList, "PSGCT", integer5.intValue(), "NAME");
/* 5113 */                 Vector<Integer> vector6 = getChildrenEntityIds(paramEntityList, "PSGCT", integer5.intValue(), "PSGCSGR", "PSGCTCSGR");
/* 5114 */                 for (byte b6 = 0; b6 < vector6.size(); b6++) {
/* 5115 */                   Integer integer6 = vector6.elementAt(b6);
/* 5116 */                   String str6 = getAttributeValue(paramEntityList, "PSGCSGR", integer6.intValue(), "NAME");
/* 5117 */                   hashSet.add("<td class=\"PsgText\"><!--MBR-->" + str2 + "</td><td class=\"PsgText\"><!--GR-->" + str3 + "</td><td class=\"PsgText\"><!--SG-->" + str4 + "</td><td class=\"PsgText\"><!--CT-->" + str5 + "</td><td class=\"PsgText\"><!--CSGR-->" + str6 + "</td>");
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5137 */     return hashSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set getPathROF(Hashtable paramHashtable1, Hashtable paramHashtable2, Hashtable paramHashtable3, Hashtable paramHashtable4) {
/* 5152 */     return getPathROF(this.m_elist, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set getPathROF(EntityList paramEntityList, Hashtable paramHashtable1, Hashtable paramHashtable2, Hashtable paramHashtable3, Hashtable paramHashtable4) {
/* 5167 */     HashSet<String> hashSet = new HashSet();
/* 5168 */     Set set = getPathDIVtoSG(paramEntityList, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4);
/* 5169 */     Enumeration<Integer> enumeration = paramHashtable4.keys();
/* 5170 */     while (enumeration.hasMoreElements()) {
/* 5171 */       Integer integer = enumeration.nextElement();
/* 5172 */       String str = (String)paramHashtable4.get(integer);
/* 5173 */       Vector<Integer> vector = getChildrenEntityIds(paramEntityList, "PSGSG", integer.intValue(), "PSGSGR", "PSGSGSGR");
/* 5174 */       for (byte b = 0; b < vector.size(); b++) {
/* 5175 */         Integer integer1 = vector.elementAt(b);
/* 5176 */         String str1 = getAttributeValue(paramEntityList, "PSGSGR", integer1.intValue(), "NAME");
/* 5177 */         Iterator<String> iterator = set.iterator();
/* 5178 */         while (iterator.hasNext()) {
/* 5179 */           String str2 = iterator.next();
/* 5180 */           if (str2.endsWith(str + "</td>")) {
/* 5181 */             hashSet.add(str2 + "<td class=\"PsgText\"><!--SGR-->" + str1 + "</td>");
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5190 */     return hashSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set appendPath(Set paramSet, EntityList paramEntityList, Hashtable paramHashtable1, Hashtable paramHashtable2, Hashtable paramHashtable3, Hashtable paramHashtable4, String paramString1, String paramString2, String paramString3, String paramString4, Method paramMethod) {
/* 5213 */     Vector<Integer> vector = getParentEntityIds(paramEntityList, getRootEntityType(paramEntityList), getRootEntityID(paramEntityList), paramString1, paramString2);
/* 5214 */     for (byte b = 0; b < vector.size(); b++) {
/* 5215 */       int i = ((Integer)vector.elementAt(b)).intValue();
/* 5216 */       String str = getAttributeValue(paramEntityList, paramString1, i, paramString3);
/*      */       try {
/* 5218 */         ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, paramString4);
/* 5219 */         EntityItem[] arrayOfEntityItem = (EntityItem[])Array.newInstance(EntityItem.class, 1);
/* 5220 */         Array.set(arrayOfEntityItem, 0, new EntityItem(null, this.m_prof, paramString1, i));
/* 5221 */         D.ebug(4, "Creating Entity List for " + paramString4);
/* 5222 */         D.ebug(4, "Profile is " + this.m_prof);
/* 5223 */         D.ebug(4, "Extractaction Item is" + extractActionItem);
/* 5224 */         D.ebug(4, "Entity Item" + arrayOfEntityItem);
/* 5225 */         EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, arrayOfEntityItem);
/*      */         
/* 5227 */         appendPath(paramSet, (Set)paramMethod.invoke(paramMethod.getClass(), new Object[] { entityList, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4 }), str);
/* 5228 */       } catch (Exception exception) {
/* 5229 */         D.ebug(0, "appendPath:" + exception.getMessage());
/*      */       } 
/*      */     } 
/* 5232 */     return paramSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void appendPath(Set<String> paramSet1, Set paramSet2, String paramString) {
/* 5245 */     Iterator iterator = paramSet2.iterator();
/* 5246 */     while (iterator.hasNext()) {
/* 5247 */       paramSet1.add((new StringBuilder()).append(iterator.next()).append("<td class=\"PsgText\">").append(paramString).append("</td>").toString());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRootEntityType() {
/* 5258 */     return getRootEntityType(this.m_elist);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRootEntityType(EntityList paramEntityList) {
/* 5269 */     EntityGroup entityGroup = paramEntityList.getParentEntityGroup();
/* 5270 */     return (entityGroup == null) ? null : entityGroup.getEntityType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRootEntityID() {
/* 5280 */     return getRootEntityID(this.m_elist);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRootEntityID(EntityList paramEntityList) {
/* 5291 */     int i = 0;
/* 5292 */     EntityGroup entityGroup = paramEntityList.getParentEntityGroup();
/* 5293 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 5294 */       EntityItem entityItem = entityGroup.getEntityItem(0);
/* 5295 */       i = entityItem.getEntityID();
/*      */     } 
/* 5297 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set getPathCATM(Hashtable paramHashtable1, Hashtable paramHashtable2, Hashtable paramHashtable3, Hashtable paramHashtable4) {
/* 5312 */     return getPathCATM(this.m_elist, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set getPathCATM(EntityList paramEntityList, Hashtable paramHashtable1, Hashtable paramHashtable2, Hashtable paramHashtable3, Hashtable paramHashtable4) {
/* 5327 */     HashSet<String> hashSet = new HashSet();
/* 5328 */     Set set = getPathDIVtoSG(paramEntityList, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4);
/* 5329 */     Enumeration<Integer> enumeration = paramHashtable4.keys();
/* 5330 */     while (enumeration.hasMoreElements()) {
/* 5331 */       Integer integer = enumeration.nextElement();
/* 5332 */       String str = (String)paramHashtable4.get(integer);
/* 5333 */       Vector<Integer> vector = getChildrenEntityIds(paramEntityList, "PSGSG", integer.intValue(), "PSGCT", "PSGSGCT");
/* 5334 */       for (byte b = 0; b < vector.size(); b++) {
/* 5335 */         int i = ((Integer)vector.elementAt(b)).intValue();
/* 5336 */         String str1 = getAttributeValue(paramEntityList, "PSGCT", i, "NAME");
/* 5337 */         Iterator<String> iterator = set.iterator();
/* 5338 */         while (iterator.hasNext()) {
/* 5339 */           String str2 = iterator.next();
/* 5340 */           if (str2.endsWith(str + "</td>")) {
/* 5341 */             hashSet.add(str2 + "<td class=\"PsgText\"><!--CT-->" + str1 + "</td>");
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5350 */     return hashSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getChildren(String paramString1, String paramString2) {
/* 5363 */     return getChildren(this.m_elist, paramString1, paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getChildren(EntityList paramEntityList, String paramString1, String paramString2) {
/* 5376 */     return getAllEntities(paramEntityList, paramString1, 0, false, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int printStatus(Vector<EntityItem> paramVector, String paramString1, String paramString2) {
/* 5391 */     byte b = 0;
/* 5392 */     println("<hr /><table width=\"50%\"><tr><td class=\"PsgLabel\">" + paramString1 + "</td><td class=\"PsgLabel\">Status</td></tr>");
/* 5393 */     if (paramVector.size() == 0) {
/* 5394 */       b = -1;
/* 5395 */       println("<tr><td class=\"PsgText\">None Found</td><td class=\"PsgText\"></td></tr>");
/*      */     } 
/* 5397 */     for (byte b1 = 0; b1 < paramVector.size(); b1++) {
/* 5398 */       EntityItem entityItem = paramVector.elementAt(b1);
/* 5399 */       EANAttribute eANAttribute = entityItem.getAttribute(paramString2);
/* 5400 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/*      */       
/* 5402 */       String str1 = eANAttribute.toString();
/* 5403 */       if (!"0020".equals(str1)) {
/* 5404 */         b = -1;
/*      */       }
/*      */       
/* 5407 */       eANAttribute = entityItem.getAttribute("NAME");
/* 5408 */       String str2 = eANAttribute.toString();
/*      */       
/* 5410 */       String str3 = eANFlagAttribute.getFlagLongDescription(str1);
/*      */       
/* 5412 */       println("<tr><td class=\"PsgText\">" + str2 + "</td><td class=\"PsgText\">" + str3 + "</td></tr>");
/*      */     } 
/* 5414 */     println("</table>\n<br />");
/*      */     
/* 5416 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set getPathCPSL(Hashtable paramHashtable1, Hashtable paramHashtable2, Hashtable paramHashtable3, Hashtable paramHashtable4) {
/* 5431 */     return getPathCPSL(this.m_elist, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set getPathCPSL(EntityList paramEntityList, Hashtable paramHashtable1, Hashtable paramHashtable2, Hashtable paramHashtable3, Hashtable paramHashtable4) {
/* 5447 */     HashSet hashSet = new HashSet();
/* 5448 */     Vector<Integer> vector = getEntityIds(paramEntityList, "PSGCSGR", "PSGCSGRCPSL"); byte b;
/* 5449 */     for (b = 0; b < vector.size(); b++) {
/* 5450 */       Integer integer = vector.elementAt(b);
/*      */       try {
/* 5452 */         ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTPSGVECSGR1");
/* 5453 */         EntityItem[] arrayOfEntityItem = (EntityItem[])Array.newInstance(EntityItem.class, 1);
/* 5454 */         Array.set(arrayOfEntityItem, 0, new EntityItem(null, this.m_prof, "PSGCSGR", integer.intValue()));
/* 5455 */         D.ebug(4, "Creating Entity List for EXTPSGVECSGR1");
/* 5456 */         D.ebug(4, "Profile is " + this.m_prof);
/* 5457 */         D.ebug(4, "Extractaction Item is" + extractActionItem);
/* 5458 */         D.ebug(4, "Entity Item" + arrayOfEntityItem);
/* 5459 */         EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, arrayOfEntityItem);
/*      */ 
/*      */         
/* 5462 */         hashSet.addAll(getPathDIVtoCSGR(entityList, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4));
/* 5463 */       } catch (Exception exception) {
/* 5464 */         D.ebug(0, "Badness in getPathCPSL " + exception.getMessage());
/* 5465 */         return null;
/*      */       } 
/*      */     } 
/*      */     
/* 5469 */     vector = getEntityIds(paramEntityList, "PSGCSGR", "PSGCSGRCB");
/* 5470 */     for (b = 0; b < vector.size(); b++) {
/* 5471 */       Integer integer = vector.elementAt(b);
/*      */       
/* 5473 */       EntityList entityList = null;
/*      */       try {
/* 5475 */         ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTPSGELCSGR1");
/* 5476 */         EntityItem[] arrayOfEntityItem = (EntityItem[])Array.newInstance(EntityItem.class, 1);
/* 5477 */         Array.set(arrayOfEntityItem, 0, new EntityItem(null, this.m_prof, "PSGCSGR", integer.intValue()));
/* 5478 */         D.ebug(4, "Creating Entity List for EXTPSGELCSGR1");
/* 5479 */         D.ebug(4, "Profile is " + this.m_prof);
/* 5480 */         D.ebug(4, "Extractaction Item is" + extractActionItem);
/* 5481 */         D.ebug(4, "Entity Item" + arrayOfEntityItem);
/* 5482 */         entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, arrayOfEntityItem);
/*      */       
/*      */       }
/* 5485 */       catch (Exception exception) {
/* 5486 */         D.ebug(0, "Badness in getPathCPSL");
/* 5487 */         return null;
/*      */       } 
/* 5489 */       Vector<Integer> vector1 = getChildrenEntityIds(paramEntityList, "PSGCSGR", integer.intValue(), "PSGCB", "PSGCSGRCB");
/* 5490 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 5491 */         Integer integer1 = vector1.elementAt(b1);
/* 5492 */         String str = getAttributeValue("PSGCB", integer1.intValue(), "NAME");
/* 5493 */         appendPath(hashSet, getPathDIVtoCSGR(entityList, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4), "<!--CB-->" + str);
/*      */       } 
/*      */     } 
/*      */     
/* 5497 */     return hashSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int kbCheck() throws MiddlewareException, RemoteException, MiddlewareShutdownInProgressException {
/* 5514 */     return kbCheck(this.m_elist);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int kbCheck(EntityList paramEntityList) throws MiddlewareException, RemoteException, MiddlewareShutdownInProgressException {
/* 5537 */     byte b1 = 0;
/* 5538 */     Vector<EntityItem> vector = getAllLinkedChildren(paramEntityList, getRootEntityType(paramEntityList), getRootEntityID(paramEntityList));
/*      */     
/* 5540 */     for (byte b2 = 0; b2 < vector.size(); b2++) {
/* 5541 */       EntityItem entityItem = vector.elementAt(b2);
/* 5542 */       if (entityItem.getEntityType().equals("PSGKB")) {
/* 5543 */         b1 = 1;
/*      */       }
/*      */     } 
/*      */     
/* 5547 */     if (b1) {
/*      */       
/* 5549 */       Vector<Integer> vector1 = getEntityIds(paramEntityList, "PSGOF");
/* 5550 */       for (byte b = 0; b < vector1.size(); b++) {
/* 5551 */         Integer integer = vector1.elementAt(b);
/* 5552 */         vector = getChildrenEntityIds(paramEntityList, "PSGOF", integer.intValue(), "PSGKB", "PSGOFKB");
/* 5553 */         if (vector.size() > 0) {
/* 5554 */           b1 = 2;
/*      */         }
/*      */       } 
/*      */     } 
/* 5558 */     return b1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set getPathCVOF(Hashtable paramHashtable1, Hashtable paramHashtable2, Hashtable paramHashtable3, Hashtable paramHashtable4) {
/* 5573 */     return getPathCVOF(this.m_elist, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set getPathCVOF(EntityList paramEntityList, Hashtable paramHashtable1, Hashtable paramHashtable2, Hashtable paramHashtable3, Hashtable paramHashtable4) {
/* 5588 */     HashSet hashSet = new HashSet();
/*      */ 
/*      */     
/*      */     try {
/* 5592 */       ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTPSGVECVOF2");
/* 5593 */       EntityItem[] arrayOfEntityItem = (EntityItem[])Array.newInstance(EntityItem.class, 1);
/* 5594 */       Array.set(arrayOfEntityItem, 0, new EntityItem(null, this.m_prof, getRootEntityType(paramEntityList), getRootEntityID(paramEntityList)));
/* 5595 */       D.ebug(4, "Creating Entity List for EXTPSGVECVOF2");
/* 5596 */       D.ebug(4, "Profile is " + this.m_prof);
/* 5597 */       D.ebug(4, "Extractaction Item is" + extractActionItem);
/* 5598 */       D.ebug(4, "Entity Item" + arrayOfEntityItem);
/* 5599 */       EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, arrayOfEntityItem);
/*      */       
/* 5601 */       hashSet.addAll(getPathDIVtoPR(entityList, paramHashtable1, paramHashtable2));
/* 5602 */     } catch (Exception exception) {
/* 5603 */       D.ebug(0, "Badness in getPathCVOF:vePR" + exception.getMessage());
/*      */     } 
/* 5605 */     Vector<Integer> vector = getEntityIds(paramEntityList, "PSGCVSL", "PSGCVSLCVOF");
/* 5606 */     for (byte b = 0; b < vector.size(); b++) {
/* 5607 */       Integer integer = vector.elementAt(b);
/* 5608 */       String str = getAttributeValue(paramEntityList, "PSGCVSL", integer.intValue(), "NAME");
/*      */       
/*      */       try {
/* 5611 */         ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTPSGVECVSL1");
/* 5612 */         EntityItem[] arrayOfEntityItem = (EntityItem[])Array.newInstance(EntityItem.class, 1);
/* 5613 */         Array.set(arrayOfEntityItem, 0, new EntityItem(null, this.m_prof, getRootEntityType(paramEntityList), integer.intValue()));
/* 5614 */         D.ebug(4, "Creating Entity List for EXTPSGVECVSL1");
/* 5615 */         D.ebug(4, "Profile is " + this.m_prof);
/* 5616 */         D.ebug(4, "Extractaction Item is" + extractActionItem);
/* 5617 */         D.ebug(4, "Entity Item" + arrayOfEntityItem);
/* 5618 */         EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, arrayOfEntityItem);
/* 5619 */         appendPath(hashSet, getPathCVSL(entityList, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4), "<!--CVSL-->" + str);
/* 5620 */       } catch (Exception exception) {
/* 5621 */         D.ebug(0, "Badness in getPathCVOF:CVSL" + exception.getMessage());
/*      */       } 
/*      */     } 
/*      */     
/* 5625 */     return hashSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set getPathDIVtoPR(Hashtable paramHashtable1, Hashtable paramHashtable2) {
/* 5638 */     return getPathDIVtoPR(this.m_elist, paramHashtable1, paramHashtable2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set getPathDIVtoPR(EntityList paramEntityList, Hashtable<Integer, String> paramHashtable1, Hashtable<Integer, String> paramHashtable2) {
/* 5652 */     HashSet<String> hashSet = new HashSet();
/* 5653 */     Vector<Integer> vector = getEntityIds(paramEntityList, "PSGDIV", "PSGDIVBR");
/* 5654 */     for (byte b = 0; b < vector.size(); b++) {
/* 5655 */       Integer integer = vector.elementAt(b);
/* 5656 */       String str = getAttributeValue(paramEntityList, "PSGDIV", integer.intValue(), "NAME");
/* 5657 */       paramHashtable1.put(integer, str);
/*      */       
/* 5659 */       Vector<Integer> vector1 = getChildrenEntityIds(paramEntityList, "PSGDIV", integer.intValue(), "PSGBR", "PSGDIVBR");
/* 5660 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 5661 */         Integer integer1 = vector1.elementAt(b1);
/* 5662 */         String str1 = getAttributeValue(paramEntityList, "PSGBR", integer1.intValue(), "NAME");
/* 5663 */         paramHashtable2.put(integer1, str1);
/*      */         
/* 5665 */         Vector<Integer> vector2 = getChildrenEntityIds(paramEntityList, "PSGBR", integer1.intValue(), "PSGFAM", "PSGBRFAM");
/* 5666 */         for (byte b2 = 0; b2 < vector2.size(); b2++) {
/* 5667 */           Integer integer2 = vector2.elementAt(b2);
/* 5668 */           String str2 = getAttributeValue(paramEntityList, "PSGFAM", integer2.intValue(), "NAME");
/*      */           
/* 5670 */           Vector<Integer> vector3 = getChildrenEntityIds(paramEntityList, "PSGFAM", integer2.intValue(), "PSGSE", "PSGFAMSE");
/* 5671 */           for (byte b3 = 0; b3 < vector3.size(); b3++) {
/* 5672 */             Integer integer3 = vector3.elementAt(b3);
/* 5673 */             String str3 = getAttributeValue(paramEntityList, "PSGSE", integer3.intValue(), "NAME");
/*      */             
/* 5675 */             Vector<Integer> vector4 = getChildrenEntityIds(paramEntityList, "PSGSE", integer3.intValue(), "PSGPR", "PSGSEPR");
/* 5676 */             for (byte b4 = 0; b4 < vector4.size(); b4++) {
/* 5677 */               Integer integer4 = vector4.elementAt(b4);
/* 5678 */               String str4 = getAttributeValue(paramEntityList, "PSGPR", integer4.intValue(), "NAME");
/* 5679 */               hashSet.add("<td class=\"PsgText\"><!--BR-->" + str1 + "<td class=\"PsgText\"><!--FAM-->" + str2 + "</td><td class=\"PsgText\"><!--SE-->" + str3 + "</td><td class=\"PsgText\"><!--PR-->" + str4 + "</td>");
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5695 */     return hashSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set getPathCVSL(Hashtable paramHashtable1, Hashtable paramHashtable2, Hashtable paramHashtable3, Hashtable paramHashtable4) {
/* 5710 */     return getPathCVSL(this.m_elist, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set getPathCVSL(EntityList paramEntityList, Hashtable paramHashtable1, Hashtable paramHashtable2, Hashtable paramHashtable3, Hashtable paramHashtable4) {
/* 5725 */     HashSet hashSet = new HashSet();
/* 5726 */     Vector<Integer> vector1 = getParentEntityIds(paramEntityList, getRootEntityType(paramEntityList), getRootEntityID(paramEntityList), "PSGSGR", "PSGSGRCVSL");
/* 5727 */     for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 5728 */       Integer integer = vector1.elementAt(b1);
/* 5729 */       String str = getAttributeValue(paramEntityList, "PSGSGR", integer.intValue(), "NAME");
/*      */       
/*      */       try {
/* 5732 */         ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTPSGVESGR1");
/* 5733 */         EntityItem[] arrayOfEntityItem = (EntityItem[])Array.newInstance(EntityItem.class, 1);
/* 5734 */         Array.set(arrayOfEntityItem, 0, new EntityItem(null, this.m_prof, "PSGSGR", integer.intValue()));
/* 5735 */         D.ebug(4, "Creating Entity List for EXTPSGVESGR1");
/* 5736 */         D.ebug(4, "Profile is " + this.m_prof);
/* 5737 */         D.ebug(4, "Extractaction Item is" + extractActionItem);
/* 5738 */         D.ebug(4, "Entity Item" + arrayOfEntityItem);
/* 5739 */         EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, arrayOfEntityItem);
/*      */         
/* 5741 */         appendPath(hashSet, getPathDIVtoSG(entityList, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4), "<!--SGR-->" + str);
/* 5742 */       } catch (Exception exception) {
/* 5743 */         logMessage("Badness in getPathCVSL:#1" + exception.getMessage());
/*      */       } 
/*      */     } 
/*      */     
/* 5747 */     Vector<Integer> vector2 = getParentEntityIds(paramEntityList, getRootEntityType(paramEntityList), getRootEntityID(paramEntityList), "PSGCCSL", "PSGCCSLCVSL");
/* 5748 */     for (byte b2 = 0; b2 < vector2.size(); b2++) {
/* 5749 */       Integer integer = vector2.elementAt(b2);
/* 5750 */       String str = getAttributeValue(paramEntityList, "PSGCCSL", integer.intValue(), "NAME");
/*      */ 
/*      */       
/*      */       try {
/* 5754 */         ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTPSGVECCSL1");
/* 5755 */         EntityItem[] arrayOfEntityItem = (EntityItem[])Array.newInstance(EntityItem.class, 1);
/* 5756 */         Array.set(arrayOfEntityItem, 0, new EntityItem(null, this.m_prof, "PSGCCSL", integer.intValue()));
/* 5757 */         D.ebug(4, "Creating Entity List for EXTPSGVECCSL1");
/* 5758 */         D.ebug(4, "Profile is " + this.m_prof);
/* 5759 */         D.ebug(4, "Extractaction Item is" + extractActionItem);
/* 5760 */         D.ebug(4, "Entity Item" + arrayOfEntityItem);
/* 5761 */         EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, arrayOfEntityItem);
/* 5762 */         appendPath(hashSet, getPathDIVtoCSGR(entityList, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4), "<!--CCSL-->" + str);
/* 5763 */       } catch (Exception exception) {
/* 5764 */         logMessage("Badness in getPathCVSL:#2" + exception.getMessage());
/*      */       } 
/*      */     } 
/*      */     
/* 5768 */     Vector<Integer> vector3 = getParentEntityIds(paramEntityList, getRootEntityType(paramEntityList), getRootEntityID(paramEntityList), "PSGPCSL", "PSGPCSLCVSL");
/* 5769 */     for (byte b3 = 0; b3 < vector3.size(); b3++) {
/* 5770 */       Integer integer = vector3.elementAt(b3);
/* 5771 */       String str = getAttributeValue(paramEntityList, "PSGPCSL", integer.intValue(), "NAME");
/*      */ 
/*      */       
/*      */       try {
/* 5775 */         ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTPSGVEPCSL1");
/* 5776 */         EntityItem[] arrayOfEntityItem = (EntityItem[])Array.newInstance(EntityItem.class, 1);
/* 5777 */         Array.set(arrayOfEntityItem, 0, new EntityItem(null, this.m_prof, "PSGPCSL", integer.intValue()));
/* 5778 */         D.ebug(4, "Creating Entity List for EXTPSGVEPCSL1");
/* 5779 */         D.ebug(4, "Profile is " + this.m_prof);
/* 5780 */         D.ebug(4, "Extractaction Item is" + extractActionItem);
/* 5781 */         D.ebug(4, "Entity Item" + arrayOfEntityItem);
/* 5782 */         EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, arrayOfEntityItem);
/* 5783 */         appendPath(hashSet, getPathPCSL(entityList, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4), "<!--PCSL-->" + str);
/* 5784 */       } catch (Exception exception) {
/* 5785 */         logMessage("Badness in getPathCVOF:CVSL" + exception.getMessage());
/*      */       } 
/*      */     } 
/*      */     
/* 5789 */     return hashSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set getPathPCSL(Hashtable paramHashtable1, Hashtable paramHashtable2, Hashtable paramHashtable3, Hashtable paramHashtable4) {
/* 5804 */     return getPathPCSL(this.m_elist, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set getPathPCSL(EntityList paramEntityList, Hashtable paramHashtable1, Hashtable paramHashtable2, Hashtable paramHashtable3, Hashtable paramHashtable4) {
/* 5819 */     HashSet hashSet = new HashSet();
/* 5820 */     Vector<Integer> vector1 = getParentEntityIds(paramEntityList, getRootEntityType(paramEntityList), getRootEntityID(paramEntityList), "PSGSGR", "PSGSGRPCSL");
/* 5821 */     for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 5822 */       Integer integer = vector1.elementAt(b1);
/* 5823 */       String str = getAttributeValue(paramEntityList, "PSGSGR", integer.intValue(), "NAME");
/*      */ 
/*      */       
/*      */       try {
/* 5827 */         ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTPSGVESGR1");
/* 5828 */         EntityItem[] arrayOfEntityItem = (EntityItem[])Array.newInstance(EntityItem.class, 1);
/* 5829 */         Array.set(arrayOfEntityItem, 0, new EntityItem(null, this.m_prof, "PSGSGR", integer.intValue()));
/* 5830 */         D.ebug(4, "Creating Entity List for EXTPSGVESGR1");
/* 5831 */         D.ebug(4, "Profile is " + this.m_prof);
/* 5832 */         D.ebug(4, "Extractaction Item is" + extractActionItem);
/* 5833 */         D.ebug(4, "Entity Item" + arrayOfEntityItem);
/* 5834 */         EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, arrayOfEntityItem);
/* 5835 */         appendPath(hashSet, getPathDIVtoSG(entityList, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4), "<!--SGR-->" + str);
/* 5836 */       } catch (Exception exception) {
/* 5837 */         logMessage("Badness in _elPCSL:viSGR" + exception.getMessage());
/*      */       } 
/*      */     } 
/*      */     
/* 5841 */     Vector<Integer> vector2 = getParentEntityIds(paramEntityList, getRootEntityType(paramEntityList), getRootEntityID(paramEntityList), "PSGCPSL", "PSGCPSLPCSL");
/* 5842 */     for (byte b2 = 0; b2 < vector2.size(); b2++) {
/* 5843 */       Integer integer = vector2.elementAt(b2);
/* 5844 */       String str = getAttributeValue(paramEntityList, "PSGCPSL", integer.intValue(), "NAME");
/*      */ 
/*      */       
/*      */       try {
/* 5848 */         ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTPSGVECPSL1");
/* 5849 */         EntityItem[] arrayOfEntityItem = (EntityItem[])Array.newInstance(EntityItem.class, 1);
/* 5850 */         Array.set(arrayOfEntityItem, 0, new EntityItem(null, this.m_prof, "PSGCPSL", integer.intValue()));
/* 5851 */         D.ebug(4, "Creating Entity List for EXTPSGVECPSL1");
/* 5852 */         D.ebug(4, "Profile is " + this.m_prof);
/* 5853 */         D.ebug(4, "Extractaction Item is" + extractActionItem);
/* 5854 */         D.ebug(4, "Entity Item" + arrayOfEntityItem);
/* 5855 */         EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, arrayOfEntityItem);
/* 5856 */         appendPath(hashSet, getPathCPSL(entityList, paramHashtable1, paramHashtable2, paramHashtable3, paramHashtable4), "<!--CPSL-->" + str);
/* 5857 */       } catch (Exception exception) {
/* 5858 */         logMessage("Badness in _elPCSL:viCPSL" + exception.getMessage());
/*      */       } 
/*      */     } 
/* 5861 */     return hashSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAttributeValue(String paramString1, String paramString2) throws UpdatePDHEntityException, LockPDHEntityException {
/* 5876 */     setAttributeValue(getRootEntityType(), getRootEntityID(), paramString1, paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAttributeValue(String paramString1, int paramInt, String paramString2, String paramString3) throws UpdatePDHEntityException, LockPDHEntityException {
/*      */     StatusAttribute statusAttribute;
/*      */     MetaFlag[] arrayOfMetaFlag;
/*      */     byte b;
/* 5894 */     EntityGroup entityGroup = this.m_elist.getEntityGroup(paramString1);
/* 5895 */     if (entityGroup == null) {
/* 5896 */       throw new UpdatePDHEntityException("Entitygroup was not found for entity type: " + paramString1);
/*      */     }
/*      */     
/* 5899 */     if (!entityGroup.isEditable()) {
/* 5900 */       throw new UpdatePDHEntityException("Entitygroup is not editable for entity type: " + paramString1);
/*      */     }
/*      */     
/* 5903 */     EntityItem entityItem = entityGroup.getEntityItem(paramString1, paramInt);
/* 5904 */     if (entityItem == null) {
/* 5905 */       throw new UpdatePDHEntityException("Item was not found for entity type: " + paramString1 + ":id: " + paramInt);
/*      */     }
/*      */ 
/*      */     
/* 5909 */     EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(paramString2);
/* 5910 */     if (eANMetaAttribute == null) {
/* 5911 */       throw new UpdatePDHEntityException("MetaAttribute was not found for entity type: " + paramString1 + " id: " + paramInt + " code: " + paramString2);
/*      */     }
/*      */     
/* 5914 */     LockItem lockItem = this.m_slg.getLockItem(paramString1);
/* 5915 */     if (!lockItem.getLockedOn().equals(paramString1)) {
/* 5916 */       lockItem.setLockedOn(paramString1);
/*      */     }
/*      */ 
/*      */     
/* 5920 */     EANAttribute eANAttribute = entityItem.getAttribute(paramString2);
/*      */ 
/*      */     
/* 5923 */     if (eANAttribute == null) {
/* 5924 */       this.m_slg.removeLockItem(paramString1);
/* 5925 */       throw new UpdatePDHEntityException("PDHAttribute is not supported for entity type: " + paramString1 + " id: " + paramInt + " code: " + paramString2);
/*      */     } 
/*      */     
/* 5928 */     if (!eANAttribute.isEditable()) {
/* 5929 */       this.m_slg.removeLockItem(paramString1);
/* 5930 */       throw new UpdatePDHEntityException("PDHAttribute is not editable for entity type: " + paramString1 + " id: " + paramInt + " code: " + paramString2);
/*      */     } 
/*      */ 
/*      */     
/* 5934 */     switch (eANMetaAttribute.getAttributeType().charAt(0)) {
/*      */       case 'I':
/*      */       case 'L':
/*      */       case 'T':
/*      */       case 'X':
/*      */         try {
/* 5940 */           log("Setting " + paramString1 + ":" + paramInt + " attribute: " + paramString2 + " to " + paramString3);
/* 5941 */           TextAttribute textAttribute = (TextAttribute)eANAttribute;
/* 5942 */           textAttribute.put(paramString3);
/* 5943 */         } catch (EANBusinessRuleException eANBusinessRuleException) {
/* 5944 */           log("Business rule error when Setting " + paramString1 + ":" + paramInt + "Text attribute: " + paramString2 + " to " + paramString3);
/*      */         } 
/*      */         return;
/*      */       case 'F':
/*      */       case 'S':
/*      */       case 'U':
/* 5950 */         statusAttribute = (StatusAttribute)eANAttribute;
/* 5951 */         arrayOfMetaFlag = (MetaFlag[])statusAttribute.get();
/* 5952 */         for (b = 0; b < arrayOfMetaFlag.length; b++) {
/* 5953 */           if (arrayOfMetaFlag[b].getFlagCode().equals(paramString3)) {
/* 5954 */             arrayOfMetaFlag[b].setSelected(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*      */         try {
/* 5959 */           log("Setting " + paramString1 + ":" + paramInt + "Flag attribute: " + paramString2 + " to " + paramString3);
/* 5960 */           statusAttribute.put(arrayOfMetaFlag);
/* 5961 */         } catch (EANBusinessRuleException eANBusinessRuleException) {
/* 5962 */           log("Business rule error when Setting " + paramString1 + ":" + paramInt + "Flag attribute: " + paramString2 + " to " + paramString3);
/*      */         } 
/*      */         return;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5972 */     this.m_slg.removeLockItem(paramString1);
/*      */     
/* 5974 */     throw new UpdatePDHEntityException(paramString3 + " is not a valid state for entity type: " + paramString1 + " id: " + paramInt + " code: " + paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String setDGName(EntityItem paramEntityItem, String paramString) {
/* 5988 */     String str = null;
/*      */     
/* 5990 */     if (paramEntityItem != null) {
/* 5991 */       String str1 = "";
/* 5992 */       String str2 = "";
/* 5993 */       int i = -1;
/*      */       
/* 5995 */       str1 = paramEntityItem.toString();
/*      */       
/* 5997 */       if (str1.length() > 0) {
/* 5998 */         i = str1.indexOf(':');
/* 5999 */         if (i != -1) {
/* 6000 */           str2 = str1.substring(0, i).trim();
/*      */           
/* 6002 */           if (str1.substring(i).length() > 1) {
/* 6003 */             str1 = str1.substring(i + 1).trim();
/*      */           } else {
/* 6005 */             str1 = null;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 6010 */       str = paramString + ", (" + str2 + ":" + paramEntityItem.getEntityID() + ")" + ((str1 == null) ? "" : (", " + str1));
/*      */ 
/*      */       
/* 6013 */       if (str.length() > 64) {
/* 6014 */         str = str.substring(0, 64).trim();
/* 6015 */         D.ebug(4, "****** strDgName " + str);
/* 6016 */         return str;
/*      */       } 
/* 6018 */       D.ebug(4, "****** strDgName " + str);
/* 6019 */       return str;
/*      */     } 
/* 6021 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printNavigateAttributes(EntityItem paramEntityItem, EntityGroup paramEntityGroup, boolean paramBoolean) {
/* 6032 */     println("<br><BR><table width=\"100%\">\n");
/*      */     
/* 6034 */     for (byte b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 6035 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 6036 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 6037 */       if (paramBoolean) {
/* 6038 */         if (b == 0) {
/* 6039 */           println("<tr><td class=\"PsgLabel\" width=\"50%\">Navigation Attribute</td><td class=\"PsgLabel\" width=\"50%\">Attribute Value</td></tr>");
/*      */         }
/* 6041 */         if (eANMetaAttribute.isNavigate()) {
/* 6042 */           println("<tr><td class=\"PsgText\" width=\"50%\">" + eANMetaAttribute.getLongDescription() + "</td><td class=\"PsgText\" width=\"50%\">" + ((eANAttribute == null || eANAttribute.toString().length() == 0) ? "** Not Populated **" : eANAttribute.toString()) + "</td></tr>");
/*      */         }
/*      */       } else {
/* 6045 */         if (b == 0)
/* 6046 */           println("<tr><td class=\"PsgLabel\" width=\"50%\" >Attribute Description</td><td class=\"PsgLabel\" width=\"50%\">Attribute Value</td></tr>"); 
/* 6047 */         println("<tr><td class=\"PsgText\" width=\"50%\" >" + eANMetaAttribute.getLongDescription() + "</td><td class=\"PsgText\" width=\"50%\">" + ((eANAttribute == null || eANAttribute.toString().length() == 0) ? "** Not Populated **" : eANAttribute.toString()) + "</td></tr>");
/*      */       } 
/*      */     } 
/*      */     
/* 6051 */     println("</table>");
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean flagvalueEquals(String paramString1, int paramInt, String paramString2, String paramString3) {
/*      */     EANFlagAttribute eANFlagAttribute;
/* 6057 */     boolean bool = false;
/* 6058 */     EntityGroup entityGroup = null;
/* 6059 */     D.ebug(4, "**********In flagvalueEquals");
/* 6060 */     if (paramString1.equals(getEntityType()) && paramInt == getEntityID()) {
/* 6061 */       entityGroup = this.m_elist.getParentEntityGroup();
/*      */     } else {
/* 6063 */       entityGroup = this.m_elist.getEntityGroup(paramString1);
/*      */     } 
/*      */     
/* 6066 */     if (entityGroup == null) {
/* 6067 */       logError("Entitygroup was not found for entity type: " + paramString1);
/* 6068 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 6072 */     EntityItem entityItem = entityGroup.getEntityItem(paramString1, paramInt);
/* 6073 */     if (entityItem == null) {
/* 6074 */       logError("Item was not found for entity type: " + paramString1 + ":id: " + paramInt);
/* 6075 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 6079 */     EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(paramString2);
/* 6080 */     if (eANMetaAttribute == null) {
/* 6081 */       logError("MetaAttribute was not found for entity type: " + paramString1 + " id: " + paramInt + " code: " + paramString2);
/* 6082 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 6087 */     EANAttribute eANAttribute = entityItem.getAttribute(paramString2);
/* 6088 */     D.ebug(4, "**********In flagvalueEquals getting flag values for" + paramString2);
/*      */ 
/*      */     
/* 6091 */     switch (eANMetaAttribute.getAttributeType().charAt(0)) {
/*      */       case 'F':
/*      */       case 'S':
/*      */       case 'U':
/* 6095 */         eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/* 6096 */         if (eANFlagAttribute != null) {
/* 6097 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 6098 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */             
/* 6100 */             if (arrayOfMetaFlag[b].isSelected()) {
/* 6101 */               D.ebug(4, "flagvalueEquals****************" + arrayOfMetaFlag[b].getFlagCode() + " is SELECTED!");
/* 6102 */               if (arrayOfMetaFlag[b].getFlagCode().equals(paramString3)) {
/* 6103 */                 D.ebug(4, "flagvalueEquals**********" + paramString3 + " Match found!");
/* 6104 */                 bool = true; break;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         break;
/*      */     } 
/* 6111 */     return bool;
/*      */   }
/*      */   
/*      */   public boolean flagvalueEquals(EntityItem paramEntityItem, String paramString1, String paramString2) {
/*      */     EANFlagAttribute eANFlagAttribute;
/* 6116 */     boolean bool = false;
/* 6117 */     EntityGroup entityGroup = null;
/* 6118 */     D.ebug(4, "**********In flagvalueEquals");
/* 6119 */     if (paramEntityItem == null) {
/* 6120 */       logError("Entity Item is Null");
/* 6121 */       return false;
/*      */     } 
/* 6123 */     entityGroup = paramEntityItem.getEntityGroup();
/* 6124 */     if (entityGroup == null) {
/* 6125 */       logError("Entitygroup was not found for entity type: " + paramEntityItem.getKey());
/* 6126 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6132 */     EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(paramString1);
/* 6133 */     if (eANMetaAttribute == null) {
/* 6134 */       logError("MetaAttribute was not found for entity type: " + paramEntityItem.getKey() + " code: " + paramString1);
/* 6135 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 6140 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString1);
/* 6141 */     D.ebug(4, "**********In flagvalueEquals getting flag values for" + paramString1);
/*      */ 
/*      */     
/* 6144 */     switch (eANMetaAttribute.getAttributeType().charAt(0)) {
/*      */       case 'F':
/*      */       case 'S':
/*      */       case 'U':
/* 6148 */         eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/* 6149 */         if (eANFlagAttribute != null) {
/* 6150 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 6151 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */             
/* 6153 */             if (arrayOfMetaFlag[b].isSelected()) {
/* 6154 */               D.ebug(4, "flagvalueEquals****************" + arrayOfMetaFlag[b].getFlagCode() + " is SELECTED!");
/* 6155 */               if (arrayOfMetaFlag[b].getFlagCode().equals(paramString2)) {
/* 6156 */                 D.ebug(4, "flagvalueEquals**********" + paramString2 + " Match found!");
/* 6157 */                 bool = true; break;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         break;
/*      */     } 
/* 6164 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hashtable processT1Root(EntityGroup paramEntityGroup) {
/* 6173 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 6174 */     EntityItem entityItem = paramEntityGroup.getEntityItem(0);
/*      */     
/* 6176 */     for (byte b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 6177 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 6178 */       String str = eANMetaAttribute.getAttributeCode();
/* 6179 */       EANAttribute eANAttribute = entityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 6180 */       if (eANAttribute != null && 
/* 6181 */         !hashtable.containsKey(str)) {
/* 6182 */         String str1 = eANAttribute.toString();
/* 6183 */         hashtable.put(str, str1);
/*      */       } 
/*      */     } 
/*      */     
/* 6187 */     return hashtable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String processT2Root(EntityGroup paramEntityGroup, Hashtable paramHashtable, String paramString1, String paramString2) {
/* 6194 */     StringBuffer stringBuffer = new StringBuffer();
/* 6195 */     EntityItem entityItem = paramEntityGroup.getEntityItem(0);
/*      */ 
/*      */     
/* 6198 */     boolean bool = (paramEntityGroup.getClassificationGroupCount() > 0) ? true : false;
/* 6199 */     if (bool) {
/* 6200 */       entityItem.refreshClassifications();
/*      */     }
/*      */ 
/*      */     
/* 6204 */     stringBuffer.append("<br><br><b>e-announce structured change report:</b>");
/* 6205 */     stringBuffer.append("<br><br><b>" + paramEntityGroup.getLongDescription() + "</b>");
/* 6206 */     stringBuffer.append("<br><b>T1:</b> " + paramString1);
/* 6207 */     stringBuffer.append("<br><b>T2:</b> " + paramString2);
/* 6208 */     stringBuffer.append("<br><br><table border=1 width=\"100%\">\n");
/* 6209 */     stringBuffer.append("<tr><td class=\"PsgLabel\" width=\"12%\"><b>Transaction</b></td><td class=\"PsgLabel\" width=\"16%\"><b>Attribute Description</b></td><td class=\"PsgLabel\" width=\"30%\"><b>Value at T1</b></td><td class=\"PsgLabel\" width=\"30%\"><b>Value at T2</b></td><td class=\"PsgLabel\" width=\"12%\"><b>Userid</td></tr>");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6216 */     if (paramHashtable != null)
/* 6217 */       for (byte b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 6218 */         String str1 = null;
/* 6219 */         String str2 = null;
/* 6220 */         EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/*      */         
/* 6222 */         if (!bool || entityItem.isClassified(eANMetaAttribute)) {
/*      */ 
/*      */ 
/*      */           
/* 6226 */           String str3 = eANMetaAttribute.getAttributeCode();
/* 6227 */           EANAttribute eANAttribute = entityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 6228 */           String str4 = (eANMetaAttribute != null) ? eANMetaAttribute.getLongDescription() : "<em>** Not Populated **</em>";
/* 6229 */           String str5 = (eANAttribute != null) ? eANAttribute.toString() : "<em>** Not Populated **</em>";
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 6234 */           str1 = "";
/* 6235 */           if (paramHashtable.containsKey(str3)) {
/* 6236 */             str2 = (String)paramHashtable.get(str3);
/* 6237 */             if (!str5.equals(str2)) {
/* 6238 */               str1 = "Change";
/*      */             }
/* 6240 */           } else if (eANAttribute != null) {
/* 6241 */             str1 = "New";
/*      */           } 
/* 6243 */           stringBuffer.append("<tr><td class=\"PsgText\" width=\"12%\">" + str1 + "</td><td class=\"PsgText\" width=\"16%\">" + str4 + "</td><td class=\"PsgText\" width=\"30%\">" + ((str2 != null) ? str2 : "<em>** Not Populated **</em>") + "</td><td class=\"PsgText\" width=\"30%\">" + str5 + "</td><td class=\"PsgText\" width=\"12%\">" + this.m_prof
/*      */ 
/*      */ 
/*      */               
/* 6247 */               .getOPName() + "</td></tr>");
/*      */         } 
/*      */       }  
/* 6250 */     stringBuffer.append("</table>");
/* 6251 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getT1(EntityItem paramEntityItem) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
/* 6261 */     String str1 = null;
/* 6262 */     DatePackage datePackage = this.m_db.getDates();
/* 6263 */     String str2 = datePackage.getNow();
/* 6264 */     String str3 = str2.substring(0, 10) + "-00.00.00.000000";
/*      */     
/* 6266 */     ReturnStatus returnStatus = new ReturnStatus(-1);
/* 6267 */     ReturnDataResultSet returnDataResultSet = null;
/* 6268 */     String str4 = getStatusAttributeCode(paramEntityItem);
/* 6269 */     ResultSet resultSet = this.m_db.callGBL6032(returnStatus, this.m_prof.getOPWGID(), this.m_prof.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), str4, str3, str2, this.m_prof.getValOn(), this.m_prof.getEffOn());
/* 6270 */     returnDataResultSet = new ReturnDataResultSet(resultSet);
/* 6271 */     resultSet.close();
/* 6272 */     resultSet = null;
/* 6273 */     this.m_db.freeStatement();
/* 6274 */     this.m_db.isPending();
/*      */     
/* 6276 */     for (byte b = 0; b < returnDataResultSet.getRowCount(); b++) {
/* 6277 */       String str5 = returnDataResultSet.getColumn(b, 0);
/* 6278 */       String str6 = returnDataResultSet.getColumn(b, 1);
/* 6279 */       String str7 = returnDataResultSet.getColumn(b, 2);
/*      */ 
/*      */       
/* 6282 */       String str8 = returnDataResultSet.getColumn(b, 5);
/*      */       
/* 6284 */       D.ebug(4, "getT1: row " + String.valueOf(b) + ", " + str5 + ", " + str6 + ", " + str7 + "," + str8);
/*      */       
/* 6286 */       if (b == returnDataResultSet.getRowCount() - 2 && returnDataResultSet.getRowCount() > 1) {
/* 6287 */         D.ebug(4, "getT1: row " + String.valueOf(b) + ", strValfrom " + str5 + ", strAttValue " + str6);
/* 6288 */         str1 = str5;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 6293 */     str1 = str1.replace(':', '.');
/* 6294 */     str1 = str1.replace(' ', '-');
/*      */     
/* 6296 */     return str1;
/*      */   }
/*      */ 
/*      */   
/*      */   public String displayAttributes(EntityItem paramEntityItem, EntityGroup paramEntityGroup, boolean paramBoolean, int paramInt) {
/* 6301 */     if (paramBoolean) {
/* 6302 */       return displayNavAttributes(paramEntityItem, paramEntityGroup);
/*      */     }
/* 6304 */     return displayAllAttributes(paramEntityItem, paramEntityGroup);
/*      */   }
/*      */ 
/*      */   
/*      */   public String displayAttributes(EntityItem paramEntityItem, EntityGroup paramEntityGroup, boolean paramBoolean) {
/* 6309 */     if (paramBoolean) {
/* 6310 */       return displayNavAttributes(paramEntityItem, paramEntityGroup);
/*      */     }
/* 6312 */     return displayAllAttributes(paramEntityItem, paramEntityGroup);
/*      */   }
/*      */ 
/*      */   
/*      */   public String displayAllAttributes(EntityItem paramEntityItem, EntityGroup paramEntityGroup) {
/* 6317 */     StringBuffer stringBuffer = new StringBuffer();
/* 6318 */     stringBuffer.append("<br><BR><table width=\"100%\">\n");
/* 6319 */     stringBuffer.append("<tr><td class=\"PsgLabel\" width=\"35%\">Attribute</td><td class=\"PsgLabel\" width=\"65%\">Value</td></tr>");
/* 6320 */     for (byte b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 6321 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 6322 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 6323 */       stringBuffer.append("<tr><td class=\"PsgText\" width=\"35%\">" + eANMetaAttribute.getLongDescription() + "</td><td class=\"PsgText\" width=\"65%\">" + ((eANAttribute == null || eANAttribute.toString().length() == 0) ? "<em>** Not Populated **</em>" : eANAttribute.toString()) + "</td></tr>");
/*      */     } 
/* 6325 */     stringBuffer.append("</table>");
/* 6326 */     return stringBuffer.toString();
/*      */   }
/*      */   
/*      */   public String displayNavAttributes(EntityItem paramEntityItem, EntityGroup paramEntityGroup) {
/* 6330 */     StringBuffer stringBuffer = new StringBuffer();
/* 6331 */     stringBuffer.append("<br><BR><table width=\"100%\">\n");
/* 6332 */     stringBuffer.append("<tr><td class=\"PsgLabel\" width=\"35%\">Navigation Attribute</td><td class=\"PsgLabel\" width=\"65%\">Value</td></tr>");
/* 6333 */     for (byte b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 6334 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 6335 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 6336 */       if (eANMetaAttribute.isNavigate()) {
/* 6337 */         stringBuffer.append("<tr><td class=\"PsgText\" width=\"35%\">" + eANMetaAttribute.getLongDescription() + "</td><td class=\"PsgText\" width=\"65%\">" + ((eANAttribute == null || eANAttribute.toString().length() == 0) ? "<em>** Not Populated **</em>" : eANAttribute.toString()) + "</td></tr>");
/*      */       }
/*      */     } 
/* 6340 */     stringBuffer.append("</table>");
/* 6341 */     return stringBuffer.toString();
/*      */   }
/*      */   
/*      */   public String displayStatuses(EntityItem paramEntityItem, EntityGroup paramEntityGroup) {
/* 6345 */     StringBuffer stringBuffer = new StringBuffer();
/* 6346 */     stringBuffer.append("<br><BR><table width=\"100%\">\n");
/* 6347 */     stringBuffer.append("<tr><td class=\"PsgLabel\" width=\"35%\">Status Attribute</td><td class=\"PsgLabel\" width=\"65%\">Value</td></tr>");
/* 6348 */     for (byte b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 6349 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 6350 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 6351 */       if (eANMetaAttribute instanceof COM.ibm.eannounce.objects.MetaStatusAttribute) {
/* 6352 */         stringBuffer.append("<tr><td class=\"PsgText\" width=\"35%\">" + eANMetaAttribute.getLongDescription() + "</td><td class=\"PsgText\" width=\"65%\">" + ((eANAttribute == null || eANAttribute.toString().length() == 0) ? "<em>** Not Populated **</em>" : eANAttribute.toString()) + "</td></tr>");
/*      */       }
/*      */     } 
/* 6355 */     stringBuffer.append("</table>");
/* 6356 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setControlBlock() {
/* 6363 */     this.m_cbOn = new ControlBlock(this.m_strNow, this.m_strForever, this.m_strNow, this.m_strForever, this.m_prof.getOPWGID(), this.m_prof.getTranID());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFlagValue(EntityItem paramEntityItem, String paramString1, String paramString2) {
/* 6370 */     if (paramString2 != null) {
/*      */       try {
/* 6372 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/* 6373 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/* 6374 */         Vector<SingleFlag> vector = new Vector();
/* 6375 */         Vector<ReturnEntityKey> vector1 = new Vector();
/* 6376 */         vector.addElement(singleFlag);
/* 6377 */         returnEntityKey.m_vctAttributes = vector;
/* 6378 */         vector1.addElement(returnEntityKey);
/* 6379 */         this.m_db.update(this.m_prof, vector1, false, false);
/* 6380 */         this.m_db.commit();
/* 6381 */       } catch (MiddlewareException middlewareException) {
/* 6382 */         D.ebug(4, "setFlagValue: " + middlewareException.getMessage());
/* 6383 */       } catch (Exception exception) {
/* 6384 */         D.ebug(4, "setFlagValue: " + exception.getMessage());
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayHeader(EntityGroup paramEntityGroup, EntityItem paramEntityItem) {
/* 6393 */     if (paramEntityGroup != null && paramEntityGroup != null) {
/* 6394 */       println("<FONT SIZE=6><b>" + paramEntityGroup.getLongDescription() + "</b></FONT><br>");
/* 6395 */       println(displayStatuses(paramEntityItem, paramEntityGroup));
/* 6396 */       println(displayNavAttributes(paramEntityItem, paramEntityGroup));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getFlagCode(EntityItem paramEntityItem, String paramString) {
/* 6404 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(paramString);
/* 6405 */     if (eANFlagAttribute == null) {
/* 6406 */       return "NOT FOUND";
/*      */     }
/* 6408 */     return eANFlagAttribute.getFirstActiveFlagCode();
/*      */   }
/*      */   
/*      */   public static String getMetaAttributeDescription(EntityGroup paramEntityGroup, String paramString) {
/* 6412 */     EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(paramString);
/* 6413 */     if (eANMetaAttribute == null) {
/* 6414 */       return "NOT FOUND";
/*      */     }
/* 6416 */     return eANMetaAttribute.getLongDescription();
/*      */   }
/*      */   
/*      */   public static String getMetaAttributeDescription(EntityItem paramEntityItem, String paramString) {
/* 6420 */     EANMetaAttribute eANMetaAttribute = null;
/* 6421 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/* 6422 */     if (entityGroup == null) {
/* 6423 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString);
/* 6424 */       if (eANAttribute != null) {
/* 6425 */         eANMetaAttribute = eANAttribute.getMetaAttribute();
/*      */       }
/*      */     } else {
/* 6428 */       eANMetaAttribute = entityGroup.getMetaAttribute(paramString);
/*      */     } 
/* 6430 */     if (eANMetaAttribute == null) {
/* 6431 */       return "NOT FOUND";
/*      */     }
/* 6433 */     return eANMetaAttribute.getLongDescription();
/*      */   }
/*      */   
/*      */   public static String getAttributeValue(EntityItem paramEntityItem, String paramString) {
/* 6437 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString);
/* 6438 */     if (eANAttribute != null) {
/* 6439 */       return eANAttribute.toString();
/*      */     }
/* 6441 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getStatusCode(EntityItem paramEntityItem, String paramString) {
/* 6449 */     return getFlagCode(paramEntityItem, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isParentChildClassify(EntityItem paramEntityItem1, EntityItem paramEntityItem2) throws MiddlewareException, SQLException {
/* 6457 */     ParentChildList parentChildList = new ParentChildList(this.m_db, this.m_prof, paramEntityItem1, paramEntityItem2);
/*      */     try {
/* 6459 */       return parentChildList.testParentChild(paramEntityItem1, paramEntityItem2);
/* 6460 */     } catch (EntityItemException entityItemException) {
/* 6461 */       logMessage("EntityItemException: " + entityItemException.toString());
/*      */       
/* 6463 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String processChangeReport(String paramString1, String paramString2) throws MiddlewareException, LockPDHEntityException, UpdatePDHEntityException, Exception {
/* 6470 */     this.m_prof.setValOn(paramString1);
/* 6471 */     this.m_prof.setEffOn(paramString1);
/* 6472 */     start_ABRBuild();
/*      */     
/* 6474 */     EntityGroup entityGroup1 = this.m_elist.getParentEntityGroup();
/*      */ 
/*      */ 
/*      */     
/* 6478 */     this.m_prof.setValOn(paramString2);
/* 6479 */     this.m_prof.setEffOn(paramString2);
/* 6480 */     start_ABRBuild();
/*      */     
/* 6482 */     EntityGroup entityGroup2 = this.m_elist.getParentEntityGroup();
/*      */ 
/*      */     
/* 6485 */     return processT2Root(entityGroup2, processT1Root(entityGroup1), paramString1, paramString2);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getNextStatusCode(EntityItem paramEntityItem) {
/* 6490 */     boolean bool1 = isDraft(paramEntityItem);
/* 6491 */     boolean bool2 = isReadyForReview(paramEntityItem);
/*      */ 
/*      */     
/* 6494 */     boolean bool3 = isChangeRev(paramEntityItem);
/* 6495 */     boolean bool4 = isChangeFinal(paramEntityItem);
/*      */     
/* 6497 */     if (bool1)
/* 6498 */       return getNextDraftState(paramEntityItem); 
/* 6499 */     if (bool3)
/* 6500 */       return getNextChangeRevState(paramEntityItem); 
/* 6501 */     if (bool2)
/* 6502 */       return getNextReadyForReviewState(paramEntityItem); 
/* 6503 */     if (bool4) {
/* 6504 */       return getNextChangeFinalState(paramEntityItem);
/*      */     }
/* 6506 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isStatusLess(EntityItem paramEntityItem1, EntityItem paramEntityItem2) {
/* 6511 */     if (isChangeFinal(paramEntityItem1)) {
/* 6512 */       if (isReadyForReview(paramEntityItem2) || isChangeFinal(paramEntityItem2)) {
/* 6513 */         return true;
/*      */       }
/* 6515 */     } else if (isChangeRev(paramEntityItem1) && (
/* 6516 */       isDraft(paramEntityItem2) || isChangeRev(paramEntityItem2))) {
/* 6517 */       return true;
/*      */     } 
/*      */     
/* 6520 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isChildEqualOrBetterStatus(EntityItem paramEntityItem1, EntityItem paramEntityItem2) {
/* 6524 */     if (isFinal(paramEntityItem1)) {
/* 6525 */       if (isFinal(paramEntityItem2)) {
/* 6526 */         return true;
/*      */       }
/* 6528 */     } else if (isChangeFinal(paramEntityItem1)) {
/* 6529 */       if (isChangeFinal(paramEntityItem2)) {
/* 6530 */         return true;
/*      */       }
/* 6532 */     } else if (isCancel(paramEntityItem1)) {
/* 6533 */       if (isCancel(paramEntityItem2)) {
/* 6534 */         return true;
/*      */       }
/* 6536 */     } else if (isChangeRev(paramEntityItem1)) {
/* 6537 */       if (isFinal(paramEntityItem2) || isChangeFinal(paramEntityItem2) || isChangeRev(paramEntityItem2)) {
/* 6538 */         return true;
/*      */       }
/* 6540 */     } else if (isReadyForReview(paramEntityItem1)) {
/* 6541 */       if (isFinal(paramEntityItem2) || isChangeFinal(paramEntityItem2) || isChangeRev(paramEntityItem2) || isReadyForReview(paramEntityItem2)) {
/* 6542 */         return true;
/*      */       }
/*      */     } else {
/* 6545 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 6549 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isStatusOK(EntityItem paramEntityItem1, EntityItem paramEntityItem2) {
/* 6553 */     if (isDraft(paramEntityItem1) || isChangeRev(paramEntityItem1)) {
/* 6554 */       if (isReadyForReview(paramEntityItem2) || isFinal(paramEntityItem2)) {
/* 6555 */         return true;
/*      */       }
/* 6557 */     } else if ((isReadyForReview(paramEntityItem1) || isChangeFinal(paramEntityItem1) || isFinal(paramEntityItem1)) && 
/* 6558 */       isFinal(paramEntityItem2)) {
/* 6559 */       return true;
/*      */     } 
/*      */     
/* 6562 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkM0008(EntityGroup paramEntityGroup, EntityItem paramEntityItem) throws MiddlewareException, SQLException {
/* 6571 */     Vector<EntityItem[]> vector = new Vector();
/* 6572 */     EntityItem entityItem1 = null;
/* 6573 */     EntityItem entityItem2 = null;
/*      */     
/*      */     byte b;
/* 6576 */     for (b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 6577 */       entityItem1 = (EntityItem)paramEntityItem.getUpLink(b);
/* 6578 */       entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/* 6579 */       if (entityItem2 != null) {
/* 6580 */         EntityGroup entityGroup = entityItem2.getEntityGroup();
/* 6581 */         if (entityGroup.isClassified() && paramEntityGroup.isClassified() && 
/* 6582 */           !isParentChildClassify(entityItem2, paramEntityItem)) {
/* 6583 */           EntityItem[] arrayOfEntityItem = new EntityItem[2];
/* 6584 */           arrayOfEntityItem[0] = entityItem2;
/* 6585 */           arrayOfEntityItem[1] = paramEntityItem;
/* 6586 */           vector.addElement(arrayOfEntityItem);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 6593 */     if (vector.size() > 0) {
/* 6594 */       for (b = 0; b < vector.size(); b++) {
/* 6595 */         println("<br><br><b>M0008 - The following Child is not valid, given its Parent's current classification:</b>");
/* 6596 */         EntityItem[] arrayOfEntityItem = vector.elementAt(b);
/* 6597 */         if (arrayOfEntityItem != null && arrayOfEntityItem.length == 2) {
/* 6598 */           EntityItem entityItem3 = arrayOfEntityItem[0];
/* 6599 */           EntityItem entityItem4 = arrayOfEntityItem[1];
/* 6600 */           if (entityItem3 != null && entityItem4 != null) {
/* 6601 */             EntityGroup entityGroup1 = entityItem3.getEntityGroup();
/* 6602 */             EntityGroup entityGroup2 = entityItem4.getEntityGroup();
/* 6603 */             println("<br><br><b>Parent: " + entityGroup1.getLongDescription() + "</b>");
/* 6604 */             println(displayNavAttributes(entityItem3, entityGroup1));
/* 6605 */             println("<br><br><b>Child: " + entityGroup2.getLongDescription() + "</b>");
/* 6606 */             println(displayNavAttributes(entityItem4, entityGroup2));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 6611 */       return false;
/*      */     } 
/*      */     
/* 6614 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkM0007(EntityGroup paramEntityGroup, EntityItem paramEntityItem) throws MiddlewareException, SQLException {
/* 6624 */     Vector<EntityItem[]> vector = new Vector();
/* 6625 */     EntityItem entityItem1 = null;
/* 6626 */     EntityItem entityItem2 = null;
/*      */     
/*      */     byte b;
/* 6629 */     for (b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 6630 */       entityItem1 = (EntityItem)paramEntityItem.getDownLink(b);
/* 6631 */       entityItem2 = (EntityItem)entityItem1.getDownLink(0);
/* 6632 */       if (entityItem2 != null) {
/* 6633 */         EntityGroup entityGroup = entityItem2.getEntityGroup();
/* 6634 */         if (entityGroup.isClassified() && paramEntityGroup.isClassified() && 
/* 6635 */           !isParentChildClassify(paramEntityItem, entityItem2)) {
/* 6636 */           EntityItem[] arrayOfEntityItem = new EntityItem[2];
/* 6637 */           arrayOfEntityItem[0] = paramEntityItem;
/* 6638 */           arrayOfEntityItem[1] = entityItem2;
/* 6639 */           vector.addElement(arrayOfEntityItem);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6647 */     if (vector.size() > 0) {
/* 6648 */       for (b = 0; b < vector.size(); b++) {
/* 6649 */         println("<br><br><b>M0007 - The following Child is not valid, given its Parent's current classification:</b>");
/* 6650 */         EntityItem[] arrayOfEntityItem = vector.elementAt(b);
/* 6651 */         if (arrayOfEntityItem != null && arrayOfEntityItem.length == 2) {
/* 6652 */           EntityItem entityItem3 = arrayOfEntityItem[0];
/* 6653 */           EntityItem entityItem4 = arrayOfEntityItem[1];
/* 6654 */           if (entityItem3 != null && entityItem4 != null) {
/* 6655 */             EntityGroup entityGroup1 = entityItem3.getEntityGroup();
/* 6656 */             EntityGroup entityGroup2 = entityItem4.getEntityGroup();
/* 6657 */             println("<br><br><b>Parent: " + entityGroup1.getLongDescription() + "</b>");
/* 6658 */             println(displayNavAttributes(entityItem3, entityGroup1));
/* 6659 */             println("<br><br><b>Child: " + entityGroup2.getLongDescription() + "</b>");
/* 6660 */             println(displayNavAttributes(entityItem4, entityGroup2));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 6665 */       return false;
/*      */     } 
/*      */     
/* 6668 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean checkS0002(EntityItem paramEntityItem) {
/* 6673 */     return checkS0002(paramEntityItem, (Hashtable)null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkS0002(EntityItem paramEntityItem, Hashtable paramHashtable) {
/* 6679 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/* 6680 */     boolean bool = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6687 */     println("<br>S0002 - Checking " + entityGroup.getLongDescription() + " for " + paramEntityItem.toString() + ".");
/* 6688 */     println("<br>S0002 - This is an" + ((paramHashtable == null) ? " unrestricted " : " restricted ") + " check.");
/*      */     
/* 6690 */     for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 6691 */       EntityItem entityItem1 = (EntityItem)paramEntityItem.getDownLink(b);
/* 6692 */       if (entityItem1 == null) {
/* 6693 */         println("<br><br><b>S0002 - We have downlink to an non-existant relator... from " + paramEntityItem.getKey() + "</b>");
/* 6694 */         return false;
/*      */       } 
/* 6696 */       EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(0);
/* 6697 */       if (entityItem2 == null) {
/* 6698 */         println("<br><br><b>S0002 - We have a Relator that points to nowhere..." + entityItem1.getKey() + "</b>");
/* 6699 */         return false;
/*      */       } 
/* 6701 */       if ((paramHashtable == null || paramHashtable.containsKey(entityItem2.getEntityType())) && 
/* 6702 */         isStatusableEntity(entityItem2) && !isManagementGroupEntity(entityItem2) && 
/* 6703 */         !isStatusOK(paramEntityItem, entityItem2)) {
/* 6704 */         EntityGroup entityGroup1 = entityItem2.getEntityGroup();
/* 6705 */         println("<br><br>S0002 - This child does not have an acceptable status:");
/* 6706 */         println("<br>" + entityGroup1.getLongDescription());
/* 6707 */         println(displayNavAttributes(entityItem2, entityGroup1));
/* 6708 */         println(displayStatuses(entityItem2, entityGroup1));
/* 6709 */         bool = false;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6716 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkM0005(EntityItem paramEntityItem, String paramString) {
/* 6722 */     EntityGroup entityGroup1 = paramEntityItem.getEntityGroup();
/* 6723 */     EntityGroup entityGroup2 = this.m_elist.getEntityGroup(paramString);
/* 6724 */     if (paramEntityItem.getDownLinkCount() == 0) {
/* 6725 */       println("<br><br><b>M0005 -  " + entityGroup1.getLongDescription() + " requires at least one child entity - none were found.</b>");
/* 6726 */       println(displayNavAttributes(paramEntityItem, entityGroup1));
/* 6727 */       return false;
/*      */     } 
/* 6729 */     boolean bool = false;
/* 6730 */     for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 6731 */       EntityItem entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/* 6732 */       if (entityItem.getEntityType().equals(paramString)) {
/* 6733 */         bool = true;
/*      */         break;
/*      */       } 
/*      */     } 
/* 6737 */     if (!bool) {
/* 6738 */       println("<br><br><b>M0005 -  " + entityGroup1.getLongDescription() + " requires at least one child entity connected via" + ((entityGroup2 == null) ? "Bad Relator passed" : entityGroup2.getLongDescription()) + ".  None were found.</b>");
/* 6739 */       println(displayNavAttributes(paramEntityItem, entityGroup1));
/* 6740 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 6744 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkM0001(EntityItem paramEntityItem) {
/* 6755 */     boolean bool = true;
/* 6756 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/*      */     
/* 6758 */     if (!paramEntityItem.getEntityType().equals("COMMERCIALOF")) {
/* 6759 */       println("<br><br><b>M0001 - Skipping Check .. this is not a Commercial Offering, but a " + entityGroup.getLongDescription());
/*      */     }
/*      */     
/* 6762 */     String str = getFlagCode(paramEntityItem, "COFGRP");
/*      */     
/* 6764 */     if (str.equals("300") || str.equals("301")) {
/* 6765 */       println("<br><br><b>M0001 - Skipping Check ..  This commercial offering is a " + getMetaAttributeDescription(entityGroup, "COFGRP") + " Commercial Offering.");
/* 6766 */       return bool;
/*      */     } 
/*      */     
/* 6769 */     println("<br><br><b>M0001 - Checking. This commercial offering is a " + getMetaAttributeDescription(entityGroup, "COFGRP") + " Commercial Offering.");
/*      */ 
/*      */     
/* 6772 */     for (byte b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 6773 */       EntityItem entityItem = (EntityItem)paramEntityItem.getUpLink(b);
/*      */       
/* 6775 */       if (entityItem.getEntityType().equals("COFMEMBERCOFOMG")) {
/* 6776 */         EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(0);
/*      */         
/* 6778 */         for (byte b1 = 0; b1 < entityItem1.getUpLinkCount(); b1++) {
/* 6779 */           EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(b1);
/* 6780 */           if (entityItem2.getEntityType().equals("COFOWNSCOFOMG")) {
/* 6781 */             EntityItem entityItem3 = (EntityItem)entityItem2.getUpLink(0);
/*      */             
/* 6783 */             String str1 = getFlagCode(entityItem3, "COFGRP");
/* 6784 */             if (str1.equals("300") || str1.equals("301")) {
/*      */               
/* 6786 */               println("<br><br><b>M0001 - Check Passed.  Located the \"Base\" Commercial Offering parent: " + entityItem3.toString());
/* 6787 */               return bool;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 6794 */     if (!isReadyForReview(paramEntityItem, getNextStatusCode(paramEntityItem))) {
/* 6795 */       println("<br><br><b>M0001 - Warning. A Base Commercial Offering Cannot be located..");
/* 6796 */     } else if (!isFinal(paramEntityItem, getNextStatusCode(paramEntityItem))) {
/* 6797 */       println("<br><br><b>M0001 - Warning. A Base Commercial Offering Cannot be located..");
/* 6798 */       bool = false;
/*      */     } 
/*      */     
/* 6801 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkM0002(EntityItem paramEntityItem) {
/* 6810 */     boolean bool = false;
/*      */ 
/*      */     
/* 6813 */     for (byte b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 6814 */       EntityItem entityItem = (EntityItem)paramEntityItem.getUpLink(b);
/*      */       
/* 6816 */       if (entityItem.getEntityType().equals("OOFMEMBERCOFOMG")) {
/* 6817 */         EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(0);
/*      */         
/* 6819 */         for (byte b1 = 0; b1 < entityItem1.getUpLinkCount(); b1++) {
/* 6820 */           EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(b1);
/* 6821 */           if (entityItem2.getEntityType().equals("COFOWNSOOFOMG")) {
/* 6822 */             bool = true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 6828 */     if (!bool) {
/* 6829 */       println("<br><br><b>M0002 - This order Offering is not a child of a Commercial Offering through a Commercial to Order Orffering Management Groop.");
/*      */     }
/*      */     
/* 6832 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkM0003(EntityItem paramEntityItem) {
/* 6838 */     boolean bool1 = true;
/* 6839 */     boolean bool2 = false;
/* 6840 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/*      */     
/* 6842 */     if (entityGroup.getEntityType().equals("ORDEROF")) {
/* 6843 */       println("<br><br><b>M0003 - Passed.  The Entity Needs to be an ORDEROF. The ABR passed in a " + entityGroup.getLongDescription() + "(" + entityGroup.getEntityType() + ")");
/* 6844 */       return bool1;
/*      */     } 
/*      */     
/* 6847 */     String str1 = getFlagCode(paramEntityItem, "OOFSUBCAT");
/* 6848 */     if (!str1.equals("500")) {
/* 6849 */       println("<br><br><b>M0003 - Passed.  This is not a feature Code Order Offering.");
/* 6850 */       return bool1;
/*      */     } 
/*      */     
/* 6853 */     EANAttribute eANAttribute = paramEntityItem.getAttribute("FEATURECODE");
/* 6854 */     String str2 = (eANAttribute == null) ? "NONE" : eANAttribute.toString();
/*      */ 
/*      */ 
/*      */     
/* 6858 */     for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 6859 */       EntityItem entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/* 6860 */       if (entityItem.getEntityType().equals("OOFFUP")) {
/* 6861 */         if (bool2) {
/* 6862 */           bool1 = false;
/*      */         } else {
/*      */           
/* 6865 */           bool2 = true;
/*      */         } 
/* 6867 */         EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 6868 */         EntityGroup entityGroup1 = entityItem1.getEntityGroup();
/*      */         
/* 6870 */         EANAttribute eANAttribute1 = paramEntityItem.getAttribute("FEATURECODE");
/* 6871 */         String str = (eANAttribute1 == null) ? "NONE" : eANAttribute1.toString();
/* 6872 */         if (!str.equals(str2)) {
/* 6873 */           bool1 = false;
/*      */           
/* 6875 */           println("<br><br><b>Feature Code of " + entityGroup.getLongDescription() + "(" + str2 + ") is not identical to the Feature Code of the " + entityGroup1.getLongDescription() + "(" + str + ")</b>");
/* 6876 */           println("<br><br><b> " + entityGroup1.getLongDescription());
/* 6877 */           println(displayNavAttributes(entityItem1, entityGroup1));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 6882 */     return bool1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkM0004GrandChild(EntityItem paramEntityItem) {
/* 6896 */     String str1 = paramEntityItem.getEntityType();
/* 6897 */     String str2 = null;
/* 6898 */     String str3 = null;
/* 6899 */     String str4 = null;
/* 6900 */     String str5 = null;
/* 6901 */     boolean bool = false;
/* 6902 */     String str6 = null;
/* 6903 */     String str7 = null;
/* 6904 */     String str8 = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6909 */     if (str1.equals("COMMERCIALOF")) {
/* 6910 */       str3 = "COFGRP";
/* 6911 */       str2 = getFlagCode(paramEntityItem, str3);
/* 6912 */       str4 = "COFOWNSOOFOMG";
/* 6913 */       str5 = "OOFMEMBERCOFOMG";
/* 6914 */       str7 = "Your Commercial Offering's Group attribute is not set to \"initial\". So no check is needed.";
/* 6915 */       str8 = "Foung an Order Offering";
/* 6916 */       str6 = "No Order Offerings could be found.  There must be at least one Order Offering, attached through an COF to OOF Management Group from this Commercial Offering";
/* 6917 */       bool = (str2 == "302" || str2 == "303") ? true : false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6923 */     if (!bool) {
/*      */       
/* 6925 */       println("<br><br><b>M0004 - Passed.  " + str7 + "</b> ");
/* 6926 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 6930 */     for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 6931 */       EntityItem entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/* 6932 */       if (entityItem.getEntityType().equals(str4)) {
/* 6933 */         EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 6934 */         for (byte b1 = 0; b1 < entityItem1.getDownLinkCount(); b1++) {
/* 6935 */           EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(b1);
/* 6936 */           if (entityItem2.getEntityType().equals(str5)) {
/* 6937 */             EntityItem entityItem3 = (EntityItem)entityItem2.getDownLink(0);
/* 6938 */             println("<br><br><b>M0004 - Passed.  " + str8 + ":  " + entityItem3.toString() + ". </b>");
/* 6939 */             return true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 6945 */     println("<br><br><b> M0004 - Failed.  " + str6 + "<b/>");
/* 6946 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkM0004Child(EntityItem paramEntityItem) {
/* 6957 */     String str1 = paramEntityItem.getEntityType();
/* 6958 */     String str2 = null;
/* 6959 */     boolean bool = false;
/* 6960 */     String str3 = null;
/* 6961 */     String str4 = null;
/* 6962 */     String str5 = null;
/*      */     
/* 6964 */     if (str1.equals("COFOOFMGMTGRP")) {
/* 6965 */       str2 = "OOFMEMBERCOFOMG";
/* 6966 */       str4 = "Your Commercial Offering's Group attribute is not set to \"initial\". So no check is needed.";
/* 6967 */       str5 = "Foung an Order Offering";
/* 6968 */       str3 = "No Order Offerings could be found.  There must be at least one Order Offering, attached through an COF to OOF Management Group.";
/* 6969 */       bool = true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6975 */     if (!bool) {
/*      */       
/* 6977 */       println("<br><br><b>M0004 - Passed.  " + str4 + "</b> ");
/* 6978 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 6982 */     for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 6983 */       EntityItem entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/* 6984 */       if (entityItem.getEntityType().equals(str2)) {
/* 6985 */         EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 6986 */         println("<br><br><b>M0004 - Passed.  " + str5 + ":  " + entityItem1.toString() + ". </b>");
/* 6987 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/* 6991 */     println("<br><br><b> M0004 - Failed.  " + str3 + "<b/>");
/* 6992 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkM0020(EntityItem paramEntityItem) {
/* 6998 */     boolean bool = true;
/*      */     
/* 7000 */     println("<br><br><b><I>M0020:</I> Checking " + paramEntityItem.toString() + "for a Valid Parent");
/* 7001 */     if (!paramEntityItem.getEntityType().equals("PHYSICALOF")) {
/* 7002 */       println("<br><br><b><I>M0020: is not a Physical Offering.. skipping this check");
/* 7003 */       return bool;
/*      */     } 
/*      */     
/* 7006 */     for (byte b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 7007 */       EntityItem entityItem = (EntityItem)paramEntityItem.getUpLink(b);
/* 7008 */       if (entityItem.getEntityType().equals("POFPOFMGMTGRP") || entityItem.getEntityType().equals("FUPPOFMGMTGRP")) {
/* 7009 */         println("<br><br><b><I>M0020: Passed.. Found a valid Management group as parent.");
/* 7010 */         return bool;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7018 */     if (isReadyForReview(paramEntityItem, getNextStatusCode(paramEntityItem))) {
/* 7019 */       println("<br><br><b><I>Warning:</I> This Physical Offering does not have an acceptable parent. </b>");
/* 7020 */     } else if (isFinal(paramEntityItem, getNextStatusCode(paramEntityItem))) {
/* 7021 */       bool = false;
/* 7022 */       println("<br><br><b><I>Failure:</I> This Physical Offering does not have an acceptable parent. </b>");
/*      */     } 
/*      */     
/* 7025 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkA0001(EntityItem paramEntityItem) {
/* 7032 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/* 7033 */     println("<br>A0001 - Checking " + entityGroup.getLongDescription() + " " + paramEntityItem.toString());
/* 7034 */     for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 7035 */       EntityItem entityItem1 = (EntityItem)paramEntityItem.getDownLink(b);
/* 7036 */       EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(0);
/* 7037 */       if (entityItem2.getEntityType().equals("AVAIL") && 
/* 7038 */         getFlagCode(entityItem2, "AVAILTYPE").equals("146")) {
/* 7039 */         return true;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 7044 */     if (isReadyForReview(paramEntityItem)) {
/* 7045 */       println("<br>A0001 - Warning - You need at least one Planned Availability record attached to your " + entityGroup.getLongDescription());
/* 7046 */       return true;
/*      */     } 
/*      */     
/* 7049 */     println("<br>A0001 - Failure - You need at least one Planned Availability record attached to your " + entityGroup.getLongDescription());
/* 7050 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkA0002(EntityItem paramEntityItem) {
/* 7061 */     Vector<EntityItem> vector1 = new Vector();
/* 7062 */     Vector<EntityItem> vector2 = new Vector();
/* 7063 */     Vector<EntityItem> vector3 = new Vector();
/* 7064 */     Vector<EntityItem> vector4 = new Vector();
/* 7065 */     Vector<EntityItem> vector5 = new Vector();
/* 7066 */     Vector<EntityItem> vector6 = new Vector();
/* 7067 */     Vector<EntityItem> vector7 = new Vector();
/* 7068 */     Vector<EntityItem> vector8 = new Vector();
/* 7069 */     Vector<Vector<EntityItem>> vector = new Vector();
/* 7070 */     Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 7071 */     Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/*      */ 
/*      */ 
/*      */     
/* 7075 */     boolean bool = true;
/*      */     
/*      */     byte b;
/* 7078 */     for (b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/*      */       
/* 7080 */       EntityItem entityItem1 = (EntityItem)paramEntityItem.getDownLink(b);
/* 7081 */       EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(0);
/* 7082 */       EntityGroup entityGroup = entityItem2.getEntityGroup();
/*      */ 
/*      */       
/* 7085 */       if (entityItem2.getEntityType().equals("AVAIL")) {
/* 7086 */         String str1 = null;
/*      */         
/* 7088 */         String str2 = null;
/*      */ 
/*      */         
/* 7091 */         EANAttribute eANAttribute = entityItem2.getAttribute("AVAILTYPE");
/* 7092 */         str1 = (eANAttribute == null) ? "" : eANAttribute.toString();
/* 7093 */         if (str1.equals("First Order")) {
/* 7094 */           vector1.addElement(entityItem2);
/* 7095 */         } else if (str1.equals("Planned Availability")) {
/* 7096 */           vector2.addElement(entityItem2);
/* 7097 */         } else if (str1.equals("Last Initial Order")) {
/* 7098 */           vector3.addElement(entityItem2);
/* 7099 */         } else if (str1.equals("Last Order")) {
/* 7100 */           vector4.addElement(entityItem2);
/* 7101 */         } else if (str1.equals("Last Return")) {
/* 7102 */           vector5.addElement(entityItem2);
/* 7103 */         } else if (str1.equals("End of Service")) {
/* 7104 */           vector6.addElement(entityItem2);
/* 7105 */         } else if (str1.equals("End of Dev Support")) {
/* 7106 */           vector7.addElement(entityItem2);
/*      */         } else {
/* 7108 */           vector8.addElement(entityItem2);
/*      */         } 
/* 7110 */         eANAttribute = entityItem2.getAttribute("EFFECTIVEDATE");
/*      */         
/* 7112 */         eANAttribute = entityItem2.getAttribute("ANNCODENAME");
/* 7113 */         str2 = (eANAttribute == null) ? "" : eANAttribute.toString();
/* 7114 */         eANAttribute = entityItem2.getAttribute("COUNTRYLIST");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 7119 */         String str3 = null;
/* 7120 */         if (eANAttribute != null) {
/* 7121 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANAttribute.get();
/* 7122 */           for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/* 7123 */             String str = arrayOfMetaFlag[b1].getFlagCode();
/*      */ 
/*      */             
/* 7126 */             if (arrayOfMetaFlag[b1].isSelected()) {
/* 7127 */               if (hashtable2.containsKey(str1 + "|" + str)) {
/* 7128 */                 bool = false;
/* 7129 */                 println("<br><br><b>The following Avail is referencing a country that has already been referenced " + arrayOfMetaFlag[b1].getLongDescription() + "</b>");
/* 7130 */                 println(displayNavAttributes(entityItem2, entityGroup));
/*      */                 break;
/*      */               } 
/* 7133 */               hashtable2.put(str1 + "|" + str, "HI");
/*      */ 
/*      */ 
/*      */               
/* 7137 */               str3 = str1 + "|" + str2 + "|" + str;
/* 7138 */               if (hashtable1.containsKey(str3)) {
/*      */                 
/* 7140 */                 println("<br><br><b>The following entity has multiple dates for the same Type of AVAIL " + entityGroup.getLongDescription() + "</b>");
/* 7141 */                 println(displayNavAttributes(entityItem2, entityGroup));
/* 7142 */                 bool = false;
/*      */               } else {
/* 7144 */                 hashtable1.put(str3, "HI");
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7155 */     vector.addElement(vector1);
/* 7156 */     vector.addElement(vector2);
/* 7157 */     vector.addElement(vector3);
/* 7158 */     vector.addElement(vector4);
/* 7159 */     vector.addElement(vector5);
/* 7160 */     vector.addElement(vector6);
/* 7161 */     vector.addElement(vector7);
/* 7162 */     vector.addElement(vector8);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7167 */     if (!bool) {
/* 7168 */       println("<br/>A0002 - Available Partition Check - Summary Information Report:");
/* 7169 */       println("<br><BR><table border=1 width=\"100%\">\n");
/* 7170 */       println("<tr><td class=\"PsgLabel\" width=\"25%\"><b>Availability Type</b></td><td class=\"PsgLabel\" width=\"25%\"><b>Effective Date</b></td><td class=\"PsgLabel\" width=\"25%\"><b>Announcement Code Name</b></td><td class=\"PsgLabel\" width=\"25%\"><b>Country List</b></td></tr>");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 7175 */       for (b = 0; b < vector.size(); b++) {
/* 7176 */         Vector<EntityItem> vector9 = vector.elementAt(b);
/* 7177 */         for (byte b1 = 0; b1 < vector9.size(); b1++) {
/* 7178 */           displayAvailRows(vector9.elementAt(b1));
/*      */         }
/*      */       } 
/* 7181 */       println("</table>");
/*      */     } 
/*      */ 
/*      */     
/* 7185 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkS0001(EntityItem paramEntityItem) {
/* 7196 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7201 */     boolean bool = true;
/*      */     
/* 7203 */     println("<br><b>S0001 - Checking " + entityGroup.getLongDescription() + " for " + paramEntityItem.toString() + ".");
/* 7204 */     for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 7205 */       EntityItem entityItem1 = (EntityItem)paramEntityItem.getDownLink(b);
/* 7206 */       EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(0);
/* 7207 */       EntityGroup entityGroup1 = entityItem2.getEntityGroup();
/* 7208 */       if (isManagementGroupEntity(entityItem2) && 
/* 7209 */         !isStatusOK(paramEntityItem, entityItem2)) {
/* 7210 */         for (byte b1 = 0; b1 < entityItem2.getDownLinkCount(); b1++) {
/* 7211 */           EntityItem entityItem3 = (EntityItem)entityItem2.getDownLink(b1);
/* 7212 */           EntityItem entityItem4 = (EntityItem)entityItem3.getDownLink(0);
/* 7213 */           EntityGroup entityGroup2 = entityItem4.getEntityGroup();
/* 7214 */           if (isManagementGroupChildrenEntity(entityItem4) && 
/* 7215 */             !isStatusOK(paramEntityItem, entityItem4)) {
/* 7216 */             println("<br><br><b>S0001 - Management Group and one or more Children do not have an acceptable status");
/* 7217 */             println("<br><br><b>Child: " + entityGroup1.getLongDescription() + "</b>");
/* 7218 */             println(displayNavAttributes(entityItem2, entityGroup1));
/* 7219 */             println("<br><br><b>Grand Child: " + entityGroup2.getLongDescription() + "</b>");
/* 7220 */             println(displayNavAttributes(entityItem4, entityGroup2));
/* 7221 */             bool = false;
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7234 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkA0003(EntityItem paramEntityItem) {
/* 7361 */     if (!checkA0002(paramEntityItem)) return false;
/*      */     
/* 7363 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/*      */     
/* 7365 */     Vector<EntityItem> vector1 = new Vector();
/* 7366 */     Vector<EntityItem> vector2 = new Vector();
/* 7367 */     Vector<EntityItem> vector3 = new Vector();
/* 7368 */     Vector<EntityItem> vector4 = new Vector();
/* 7369 */     Vector<EntityItem> vector5 = new Vector();
/* 7370 */     Vector<EntityItem> vector6 = new Vector();
/* 7371 */     Vector<EntityItem> vector7 = new Vector();
/* 7372 */     Vector<EntityItem> vector8 = new Vector();
/* 7373 */     Vector<Vector<EntityItem>> vector = new Vector();
/*      */ 
/*      */     
/* 7376 */     boolean bool = true;
/*      */ 
/*      */     
/* 7379 */     for (byte b1 = 0; b1 < paramEntityItem.getDownLinkCount(); b1++) {
/*      */       
/* 7381 */       EntityItem entityItem1 = (EntityItem)paramEntityItem.getDownLink(b1);
/* 7382 */       EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(0);
/*      */ 
/*      */ 
/*      */       
/* 7386 */       if (entityItem2.getEntityType().equals("AVAIL")) {
/* 7387 */         String str1 = null;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 7392 */         EANAttribute eANAttribute = entityItem2.getAttribute("AVAILTYPE");
/* 7393 */         str1 = (eANAttribute == null) ? "" : eANAttribute.toString();
/* 7394 */         if (str1.equals("First Order")) {
/* 7395 */           vector1.addElement(entityItem2);
/* 7396 */         } else if (str1.equals("Planned Availability")) {
/* 7397 */           vector2.addElement(entityItem2);
/* 7398 */         } else if (str1.equals("Last Initial Order")) {
/* 7399 */           vector3.addElement(entityItem2);
/* 7400 */         } else if (str1.equals("Last Order")) {
/* 7401 */           vector4.addElement(entityItem2);
/* 7402 */         } else if (str1.equals("Last Return")) {
/* 7403 */           vector5.addElement(entityItem2);
/* 7404 */         } else if (str1.equals("End of Service")) {
/* 7405 */           vector6.addElement(entityItem2);
/* 7406 */         } else if (str1.equals("End of Dev Support")) {
/* 7407 */           vector7.addElement(entityItem2);
/*      */         } else {
/* 7409 */           vector8.addElement(entityItem2);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 7416 */     vector.addElement(vector1);
/* 7417 */     vector.addElement(vector2);
/* 7418 */     vector.addElement(vector3);
/* 7419 */     vector.addElement(vector4);
/* 7420 */     vector.addElement(vector5);
/* 7421 */     vector.addElement(vector6);
/* 7422 */     vector.addElement(vector7);
/* 7423 */     vector.addElement(vector8);
/*      */     
/* 7425 */     String str = null;
/* 7426 */     Calendar calendar1 = Calendar.getInstance();
/* 7427 */     Calendar calendar2 = Calendar.getInstance();
/*      */     byte b2;
/* 7429 */     for (b2 = 0; b2 < vector.size(); b2++) {
/* 7430 */       Vector<EntityItem> vector9 = vector.elementAt(b2);
/* 7431 */       for (byte b = 0; b < vector9.size(); b++) {
/* 7432 */         EntityItem entityItem = vector9.elementAt(b);
/* 7433 */         EANAttribute eANAttribute = entityItem.getAttribute("EFFECTIVEDATE");
/* 7434 */         str = (eANAttribute != null) ? eANAttribute.toString() : "<em>** Not Populated **</em>";
/* 7435 */         println("<br><br> Checking effective date: " + str + " for " + entityItem.toString());
/* 7436 */         if (str.length() == 10 && !str.equals("<em>** Not Populated **</em>")) {
/* 7437 */           String str1 = str.substring(0, 4);
/* 7438 */           String str2 = str.substring(5, 7);
/* 7439 */           String str3 = str.substring(8);
/* 7440 */           int i = (new Integer(str1)).intValue();
/* 7441 */           int j = (new Integer(str2)).intValue();
/* 7442 */           int k = (new Integer(str3)).intValue();
/* 7443 */           if (b2 == 0 && b == 0) {
/* 7444 */             calendar1.set(i, j, k);
/*      */           } else {
/* 7446 */             calendar2.set(i, j, k);
/* 7447 */             if (calendar1.before(calendar2)) {
/* 7448 */               calendar1.set(i, j, k);
/*      */             } else {
/*      */               
/* 7451 */               bool = false;
/* 7452 */               println("<br><br><b>The following entity has inconsistent dates:" + calendar2.get(1) + "-" + calendar2.get(2) + "-" + calendar2.get(5) + " is less than " + calendar1.get(1) + "-" + calendar1.get(2) + "-" + calendar1.get(5) + "</b>");
/* 7453 */               println("<br><b>for one or more Countries specified via Availability</b>");
/* 7454 */               println("<br><br><b>" + entityGroup.getLongDescription() + "</b>");
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7464 */     if (!bool) {
/* 7465 */       println("<br/>A0003 - Available Partition Check - Summary Information Report:");
/* 7466 */       println("<br><BR><table border=1 width=\"100%\">\n");
/* 7467 */       println("<tr><td class=\"PsgLabel\" width=\"25%\"><b>Availability Type</b></td><td class=\"PsgLabel\" width=\"25%\"><b>Effective Date</b></td><td class=\"PsgLabel\" width=\"25%\"><b>Announcement Code Name</b></td><td class=\"PsgLabel\" width=\"25%\"><b>Country List</b></td></tr>");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 7472 */       for (b2 = 0; b2 < vector.size(); b2++) {
/* 7473 */         Vector<EntityItem> vector9 = vector.elementAt(b2);
/* 7474 */         for (byte b = 0; b < vector9.size(); b++) {
/* 7475 */           displayAvailRows(vector9.elementAt(b));
/*      */         }
/*      */       } 
/* 7478 */       println("</table>");
/*      */     } 
/* 7480 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkH0003(EntityItem paramEntityItem) {
/* 7488 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/*      */     
/* 7490 */     String str1 = paramEntityItem.getEntityType();
/* 7491 */     String str2 = getFlagCode(paramEntityItem, "OOFCAT");
/* 7492 */     String str3 = getFlagCode(paramEntityItem, "OOFSUBCAT");
/*      */     
/* 7494 */     boolean bool1 = true;
/* 7495 */     boolean bool2 = false;
/* 7496 */     boolean bool3 = false;
/*      */ 
/*      */     
/* 7499 */     if (str1.equals("ORDEROF") && str2.equals("100") && str3.equals("501")) {
/*      */       
/* 7501 */       println("<br/>H0003 - Checking " + entityGroup.getLongDescription() + ":" + paramEntityItem.toString());
/*      */       
/* 7503 */       String str4 = getAttributeValue(paramEntityItem, "FROMFEATURECODE");
/* 7504 */       String str5 = getAttributeValue(paramEntityItem, "FEATURECODE");
/*      */       
/* 7506 */       for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 7507 */         EntityItem entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/* 7508 */         if (entityItem.getEntityType().equals("OOFFUP")) {
/* 7509 */           String str6 = getFlagCode(entityItem, "RELTYPE");
/* 7510 */           EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 7511 */           String str7 = getAttributeValue(entityItem1, "FEATURECODE");
/* 7512 */           if (str6.equals("6114")) {
/* 7513 */             bool2 = true;
/* 7514 */             if (!str7.equals(str4)) {
/* 7515 */               println("<br/>H0003 - Failure.  Non Matching feature codes in the *From* Case.  " + str4 + " does not match " + str7 + " for " + entityItem1.toString());
/* 7516 */               bool1 = false;
/*      */             } 
/* 7518 */           } else if (str6.equals("6115")) {
/* 7519 */             bool3 = true;
/* 7520 */             if (!str7.equals(str5)) {
/* 7521 */               println("<br/>H0003 - Failure.  Non Matching feature codes in the *To* Case.  " + str5 + " does not match " + str7 + " for " + entityItem1.toString());
/* 7522 */               bool1 = false;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 7528 */       if (!bool2) {
/* 7529 */         println("<br/>H0003 - Failure.  Did not find any *FROM* Feature Codes to Test.");
/* 7530 */         bool1 = false;
/*      */       } 
/* 7532 */       if (!bool3) {
/* 7533 */         println("<br/>H0003 - Failure.  Did not find any *TO* Feature Codes to Test.");
/* 7534 */         bool1 = false;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 7539 */     return bool1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkH0006(EntityItem paramEntityItem) {
/* 7546 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/*      */     
/* 7548 */     String str1 = paramEntityItem.getEntityType();
/* 7549 */     String str2 = getFlagCode(paramEntityItem, "OOFCAT");
/* 7550 */     String str3 = getFlagCode(paramEntityItem, "OOFSUBCAT");
/*      */     
/* 7552 */     boolean bool1 = true;
/* 7553 */     boolean bool2 = false;
/* 7554 */     boolean bool3 = false;
/*      */ 
/*      */     
/* 7557 */     if (str1.equals("ORDEROF") && str2.equals("100") && (str3.equals("503") || str3.equals("503"))) {
/*      */       
/* 7559 */       println("<br/>H0006 - Checking " + entityGroup.getLongDescription() + ":" + paramEntityItem.toString());
/*      */       
/* 7561 */       String str4 = getAttributeValue(paramEntityItem, "FROMMODEL");
/* 7562 */       String str5 = getAttributeValue(paramEntityItem, "MODEL");
/*      */       
/* 7564 */       for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 7565 */         EntityItem entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/* 7566 */         if (entityItem.getEntityType().equals("OOFFUP")) {
/* 7567 */           String str6 = getFlagCode(entityItem, "RELTYPE");
/* 7568 */           EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 7569 */           String str7 = getAttributeValue(entityItem1, "MODEL");
/* 7570 */           if (str6.equals("6114")) {
/* 7571 */             bool2 = true;
/* 7572 */             if (!str7.equals(str4)) {
/* 7573 */               println("<br/>H0006 - Failure.  Non Matching Models in the *From* Case.  " + str4 + " does not match " + str7 + " for " + entityItem1.toString());
/* 7574 */               bool1 = false;
/*      */             } 
/* 7576 */           } else if (str6.equals("6115")) {
/* 7577 */             bool3 = true;
/* 7578 */             if (!str7.equals(str5)) {
/* 7579 */               println("<br/>H0006 - Failure.  Non Matching Models in the *To* Case.  " + str5 + " does not match " + str7 + " for " + entityItem1.toString());
/* 7580 */               bool1 = false;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 7586 */       if (!bool2) {
/* 7587 */         println("<br/>H0006 - Failure.  Did not find any *FROM* Models to Test.");
/* 7588 */         bool1 = false;
/*      */       } 
/* 7590 */       if (!bool3) {
/* 7591 */         println("<br/>H0006 - Failure.  Did not find any *TO* Models to Test.");
/* 7592 */         bool1 = false;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 7597 */     return bool1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkV0001(EntityItem paramEntityItem) {
/* 7604 */     if (isFinal(paramEntityItem, getNextStatusCode(paramEntityItem))) {
/*      */       
/* 7606 */       EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/* 7607 */       println("<br><br><b>V0001 - Checking " + entityGroup.getLongDescription() + " : " + paramEntityItem.toString());
/* 7608 */       String str = getAttributeValue(paramEntityItem, "FEATURECODE");
/* 7609 */       if (str.indexOf("#") != -1) {
/* 7610 */         println("<br><br><b>V0001 - Failure - The Feature Code has a \"#\" present: " + str);
/* 7611 */         return false;
/*      */       } 
/*      */     } 
/* 7614 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkV0002(EntityItem paramEntityItem) {
/* 7626 */     boolean bool = true;
/* 7627 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/*      */ 
/*      */     
/* 7630 */     println("<br><br>V0002 - Checking " + entityGroup.getLongDescription() + ":" + paramEntityItem.toString());
/*      */ 
/*      */     
/* 7633 */     if (isFinal(paramEntityItem, getNextStatusCode(paramEntityItem)) && paramEntityItem.getEntityType().equals("ORDEROF")) {
/*      */       
/* 7635 */       String str = getAttributeValue(paramEntityItem, "FEATURECODE");
/* 7636 */       boolean bool1 = getFlagCode(paramEntityItem, "OOFCAT").equals("100");
/* 7637 */       boolean bool2 = getFlagCode(paramEntityItem, "OOFCAT").equals("101");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 7642 */       GeneralAreaList generalAreaList = null;
/*      */       try {
/* 7644 */         generalAreaList = new GeneralAreaList(this.m_db, this.m_prof);
/* 7645 */       } catch (Exception exception) {
/* 7646 */         exception.printStackTrace();
/*      */       } 
/*      */       
/* 7649 */       if (generalAreaList == null) {
/* 7650 */         println("<br><br>V0002 - Failure - cannot pull the Geo Tree Object in from the Database:");
/* 7651 */         return false;
/*      */       } 
/*      */       
/* 7654 */       for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/*      */         
/* 7656 */         EntityItem entityItem1 = (EntityItem)paramEntityItem.getDownLink(b);
/* 7657 */         EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(0);
/*      */ 
/*      */ 
/*      */         
/* 7661 */         if (entityItem2.getEntityType().equals("AVAIL")) {
/* 7662 */           boolean bool3 = generalAreaList.isRfaGeoUS(entityItem2);
/* 7663 */           boolean bool4 = generalAreaList.isRfaGeoEMEA(entityItem2);
/* 7664 */           boolean bool5 = generalAreaList.isRfaGeoLA(entityItem2);
/* 7665 */           boolean bool6 = generalAreaList.isRfaGeoCAN(entityItem2);
/* 7666 */           boolean bool7 = generalAreaList.isRfaGeoAP(entityItem2);
/*      */           
/* 7668 */           if (bool1 || (bool2 && bool3)) {
/* 7669 */             if (!is4Digit(str.trim())) {
/* 7670 */               println("<br><br>Feature Code is not a 4 digit integer</b>");
/* 7671 */               println("<br><BR><table width=\"100%\">\n");
/* 7672 */               println("<tr><td class=\"PsgLabel\" width=\"50%\" >Attribute Description</td><td class=\"PsgLabel\" width=\"50%\">Attribute Value</td></tr>");
/* 7673 */               println("<tr><td class=\"PsgText\" width=\"50%\" >Feature Code</td><td class=\"PsgText\" width=\"50%\">" + (str.equals("") ? "<em>** Not Populated **</em>" : str) + "</td></tr>");
/* 7674 */               println("</table>");
/* 7675 */               bool = false;
/*      */             } 
/* 7677 */           } else if (bool2 && (bool7 || bool6)) {
/* 7678 */             if (!is4AlphaInteger(str.trim())) {
/* 7679 */               println("<br><br>Feature Code is not a 4 character alpha-numeric</b>");
/* 7680 */               println("<br><BR><table width=\"100%\">\n");
/* 7681 */               println("<tr><td class=\"PsgLabel\" width=\"50%\" >Attribute Description</td><td class=\"PsgLabel\" width=\"50%\">Attribute Value</td></tr>");
/* 7682 */               println("<tr><td class=\"PsgText\" width=\"50%\" >Feature Code</td><td class=\"PsgText\" width=\"50%\">" + (str.equals("") ? "<em>** Not Populated **</em>" : str) + "</td></tr>");
/* 7683 */               println("</table>");
/* 7684 */               bool = false;
/* 7685 */             } else if (bool2 && (bool5 || bool4) && 
/* 7686 */               !is46AlphaInteger(str.trim())) {
/* 7687 */               println("<br><b>Feature Code is not a 4 or 6 character alpha-numeric</b>");
/* 7688 */               println("<br><BR><table width=\"100%\">\n");
/* 7689 */               println("<tr><td class=\"PsgLabel\" width=\"50%\" >Attribute Description</td><td class=\"PsgLabel\" width=\"50%\">Attribute Value</td></tr>");
/* 7690 */               println("<tr><td class=\"PsgText\" width=\"50%\" >Feature Code</td><td class=\"PsgText\" width=\"50%\">" + (str.equals("") ? "<em>** Not Populated **</em>" : str) + "</td></tr>");
/* 7691 */               println("</table>");
/* 7692 */               bool = false;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 7700 */     if (!bool) {
/* 7701 */       println("<br><br>V0002 - Failed");
/*      */     }
/*      */     
/* 7704 */     return bool;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDigit(String paramString) {
/* 7709 */     if (paramString == null || paramString.length() <= 0) {
/* 7710 */       return false;
/*      */     }
/* 7712 */     for (byte b = 0; b < paramString.length(); b++) {
/* 7713 */       char c = paramString.charAt(b);
/* 7714 */       if (!Character.isDigit(c)) {
/* 7715 */         return false;
/*      */       }
/*      */     } 
/* 7718 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isAlphaDigit(String paramString) {
/* 7722 */     for (byte b = 0; b < paramString.length(); b++) {
/* 7723 */       char c = paramString.charAt(b);
/* 7724 */       if (!Character.isLetterOrDigit(c)) {
/* 7725 */         return false;
/*      */       }
/*      */     } 
/* 7728 */     return true;
/*      */   }
/*      */   
/*      */   public boolean containDigit(String paramString) {
/* 7732 */     for (byte b = 0; b < paramString.length(); b++) {
/* 7733 */       char c = paramString.charAt(b);
/* 7734 */       if (Character.isDigit(c)) {
/* 7735 */         return true;
/*      */       }
/*      */     } 
/* 7738 */     return false;
/*      */   }
/*      */   
/*      */   public boolean checkFCFormat(EntityItem paramEntityItem) {
/* 7742 */     String str = getAttributeValue(paramEntityItem, "FEATURECODE");
/* 7743 */     boolean bool = true;
/* 7744 */     if (str != null && str.length() > 0) {
/* 7745 */       if (str.length() == 4) {
/*      */         
/* 7747 */         if (!isAlphaDigit(str)) {
/* 7748 */           println("<br><b>Feature Code is not 4 characters alpha-numeric: " + str + "</b>");
/* 7749 */           bool = false;
/*      */         } 
/* 7751 */       } else if (str.length() == 6) {
/* 7752 */         if (!containDigit(str)) {
/* 7753 */           println("<br><b>Feature Code doesn't contain at least one digit: " + str + "</b>");
/* 7754 */           bool = false;
/*      */         } 
/*      */         
/* 7757 */         if (!isAlphaDigit(str)) {
/* 7758 */           println("<br><b>Feature Code is not 6 characters alpha-numeric: " + str + "</b>");
/* 7759 */           bool = false;
/*      */         } 
/*      */       } else {
/* 7762 */         println("<br><b>Feature Code is invalid length: " + str + "</b>");
/* 7763 */         bool = false;
/*      */       } 
/*      */     }
/* 7766 */     if (!bool) {
/* 7767 */       println("<br><br>Check FEATURECODE format - Failed");
/*      */     }
/*      */     
/* 7770 */     return bool;
/*      */   }
/*      */   
/*      */   public String formatMFlag(String paramString) {
/* 7774 */     String str = null;
/* 7775 */     if (paramString.charAt(0) == '*') {
/* 7776 */       str = paramString.substring(paramString.indexOf('*') + 1);
/* 7777 */       str = str.trim();
/* 7778 */       str = str.replace('*', ',');
/*      */     } 
/*      */     
/* 7781 */     return str;
/*      */   }
/*      */ 
/*      */   
/*      */   private void displayAvailRows(EntityItem paramEntityItem) {
/* 7786 */     if (paramEntityItem != null) {
/* 7787 */       EANAttribute eANAttribute = paramEntityItem.getAttribute("AVAILTYPE");
/* 7788 */       String str1 = (eANAttribute == null) ? "<em>** Not Populated **</em>" : eANAttribute.toString();
/* 7789 */       eANAttribute = paramEntityItem.getAttribute("EFFECTIVEDATE");
/* 7790 */       String str2 = (eANAttribute == null) ? "<em>** Not Populated **</em>" : eANAttribute.toString();
/* 7791 */       eANAttribute = paramEntityItem.getAttribute("ANNCODENAME");
/* 7792 */       String str3 = (eANAttribute == null) ? "<em>** Not Populated **</em>" : eANAttribute.toString();
/* 7793 */       eANAttribute = paramEntityItem.getAttribute("COUNTRYLIST");
/* 7794 */       String str4 = (eANAttribute == null) ? "<em>** Not Populated **</em>" : eANAttribute.toString();
/* 7795 */       println("<tr><td class=\"PsgText\" width=\"25%\">" + str1 + "</td><td class=\"PsgText\" width=\"25%\">" + str2 + "</td><td class=\"PsgText\" width=\"25%\">" + str3 + "</td><td class=\"PsgText\" width=\"25%\">" + str4 + "</td></tr>");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean is4Digit(String paramString) {
/* 7804 */     if (paramString.length() == 4) {
/* 7805 */       for (byte b = 0; b < paramString.length(); b++) {
/* 7806 */         char c = paramString.charAt(b);
/* 7807 */         if (!Character.isDigit(c)) {
/* 7808 */           return false;
/*      */         }
/*      */       } 
/*      */     } else {
/* 7812 */       return false;
/*      */     } 
/* 7814 */     return true;
/*      */   }
/*      */   
/*      */   public boolean is4AlphaInteger(String paramString) {
/* 7818 */     if (paramString.length() == 4) {
/* 7819 */       for (byte b = 0; b < paramString.length(); b++) {
/* 7820 */         char c = paramString.charAt(b);
/* 7821 */         if (!Character.isLetterOrDigit(c) && !Character.isLetter(c)) {
/* 7822 */           return false;
/*      */         }
/*      */       } 
/*      */     } else {
/* 7826 */       return false;
/*      */     } 
/* 7828 */     return true;
/*      */   }
/*      */   
/*      */   public boolean is46AlphaInteger(String paramString) {
/* 7832 */     if (paramString.length() == 4 || paramString.length() == 6) {
/* 7833 */       for (byte b = 0; b < paramString.length(); b++) {
/* 7834 */         char c = paramString.charAt(b);
/* 7835 */         if (!Character.isLetterOrDigit(c) && !Character.isLetter(c)) {
/* 7836 */           return false;
/*      */         }
/*      */       } 
/*      */     } else {
/* 7840 */       return false;
/*      */     } 
/* 7842 */     return true;
/*      */   }
/*      */   
/*      */   public Profile refreshProfValOnEffOn(Profile paramProfile) throws MiddlewareException {
/* 7846 */     DatePackage datePackage = this.m_db.getDates();
/* 7847 */     String str = datePackage.getNow();
/* 7848 */     paramProfile.setValOn(str);
/* 7849 */     paramProfile.setEffOn(str);
/* 7850 */     paramProfile.setNow(str);
/* 7851 */     paramProfile.setEndOfDay(datePackage.getEndOfDay());
/* 7852 */     return paramProfile;
/*      */   }
/*      */   
/*      */   public StringBuffer getCategories(Database paramDatabase, Profile paramProfile, EntityItem[] paramArrayOfEntityItem) {
/* 7856 */     StringBuffer stringBuffer = new StringBuffer();
/* 7857 */     EntityList entityList = null;
/* 7858 */     boolean bool1 = false;
/*      */     
/* 7860 */     String str = this.m_abri.getABRCode();
/*      */     try {
/* 7862 */       ExtractActionItem extractActionItem = new ExtractActionItem(null, paramDatabase, paramProfile, ABRServerProperties.getSubscrVEName(str));
/* 7863 */       entityList = paramDatabase.getEntityList(paramProfile, extractActionItem, paramArrayOfEntityItem);
/* 7864 */     } catch (MiddlewareException middlewareException) {
/* 7865 */       System.out.println("PokBaseABR:getDGCategories:Problem during Extract" + middlewareException.getMessage());
/* 7866 */       middlewareException.printStackTrace();
/* 7867 */       bool1 = true;
/* 7868 */     } catch (SQLException sQLException) {
/* 7869 */       System.out.println("PokBaseABR:getDGCategories:Problem during Extract" + sQLException.getMessage());
/* 7870 */       sQLException.printStackTrace();
/* 7871 */       bool1 = true;
/*      */     } 
/*      */     
/* 7874 */     if (bool1) return stringBuffer;
/*      */ 
/*      */     
/* 7877 */     byte b = 1;
/* 7878 */     boolean bool2 = true;
/*      */     do {
/* 7880 */       String str1 = ABRServerProperties.getCategory(str, "CAT" + b);
/* 7881 */       if (str1 != null) {
/* 7882 */         int i = str1.indexOf(".");
/* 7883 */         if (str1.equals("TASKSTATUS")) {
/* 7884 */           stringBuffer.append("CAT" + b + " = " + ((getReturnCode() == 0) ? "Passed" : "Failed") + "\n");
/*      */         }
/* 7886 */         else if (i >= 0) {
/* 7887 */           String str2 = str1.substring(0, i);
/* 7888 */           String str3 = str1.substring(i + 1);
/* 7889 */           EntityGroup entityGroup = null;
/* 7890 */           if (str2.equals(paramArrayOfEntityItem[0].getEntityType())) {
/* 7891 */             entityGroup = entityList.getParentEntityGroup();
/*      */           } else {
/* 7893 */             entityGroup = entityList.getEntityGroup(str2);
/*      */           } 
/*      */           
/* 7896 */           if (entityGroup != null && 
/* 7897 */             entityGroup.getEntityItemCount() > 0) {
/* 7898 */             EntityItem entityItem = entityGroup.getEntityItem(0);
/* 7899 */             EANAttribute eANAttribute = entityItem.getAttribute(str3);
/* 7900 */             if (eANAttribute != null) {
/* 7901 */               stringBuffer.append("CAT" + b + " = " + eANAttribute.toString() + "\n");
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } else {
/*      */         
/* 7907 */         bool2 = false;
/*      */       } 
/* 7909 */       b++;
/* 7910 */     } while (bool2);
/*      */     
/* 7912 */     return stringBuffer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector sortEntities(Vector paramVector, String[] paramArrayOfString) {
/* 7924 */     SortUtil sortUtil = new SortUtil();
/* 7925 */     EntityItem[] arrayOfEntityItem = getEntityArray(paramVector);
/* 7926 */     sortUtil.sort(arrayOfEntityItem, paramArrayOfString);
/* 7927 */     return getEntityVector(arrayOfEntityItem);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityItem[] getEntityArray(Vector<EntityItem> paramVector) {
/* 7938 */     EntityItem[] arrayOfEntityItem = (EntityItem[])Array.newInstance(EntityItem.class, paramVector.size());
/* 7939 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 7940 */       arrayOfEntityItem[b] = paramVector.elementAt(b);
/*      */     }
/* 7942 */     return arrayOfEntityItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getEntityVector(EntityItem[] paramArrayOfEntityItem) {
/* 7953 */     Vector<EntityItem> vector = new Vector();
/* 7954 */     for (byte b = 0; b < paramArrayOfEntityItem.length; b++) {
/* 7955 */       vector.add(paramArrayOfEntityItem[b]);
/*      */     }
/* 7957 */     return vector;
/*      */   }
/*      */   
/*      */   public EANList getChildren(EntityList paramEntityList, String paramString1, int paramInt, String paramString2, String paramString3) {
/* 7961 */     EANList eANList = new EANList();
/*      */ 
/*      */     
/* 7964 */     EntityGroup entityGroup = null;
/* 7965 */     if (paramString3.equals(getEntityType())) {
/* 7966 */       entityGroup = paramEntityList.getParentEntityGroup();
/*      */     } else {
/* 7968 */       entityGroup = paramEntityList.getEntityGroup(paramString3);
/*      */     } 
/*      */     
/* 7971 */     if (entityGroup == null) {
/* 7972 */       return eANList;
/*      */     }
/*      */     
/* 7975 */     if (entityGroup.getEntityItemCount() == 0) {
/* 7976 */       return eANList;
/*      */     }
/*      */ 
/*      */     
/* 7980 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 7981 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */ 
/*      */       
/* 7985 */       for (byte b1 = 0; b1 < entityItem.getUpLinkCount(); b1++) {
/* 7986 */         if (entityItem.getUpLink(b1).getEntityType().equals(paramString1) && entityItem.getUpLink(b1).getEntityID() == paramInt) {
/* 7987 */           for (byte b2 = 0; b2 < entityItem.getDownLinkCount(); b2++) {
/*      */             
/* 7989 */             if (entityItem.getDownLink(b2).getEntityType().equals(paramString2)) {
/* 7990 */               eANList.put((EANObject)entityItem.getDownLink(b2));
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 7997 */     return eANList;
/*      */   }
/*      */   
/*      */   public EANList getParent(EntityList paramEntityList, String paramString1, int paramInt, String paramString2, String paramString3) {
/* 8001 */     EANList eANList = new EANList();
/*      */ 
/*      */     
/* 8004 */     EntityGroup entityGroup = null;
/* 8005 */     if (paramString3.equals(getEntityType())) {
/* 8006 */       entityGroup = paramEntityList.getParentEntityGroup();
/*      */     } else {
/* 8008 */       entityGroup = paramEntityList.getEntityGroup(paramString3);
/*      */     } 
/*      */     
/* 8011 */     if (entityGroup == null) {
/* 8012 */       return eANList;
/*      */     }
/*      */     
/* 8015 */     if (entityGroup.getEntityItemCount() == 0) {
/* 8016 */       return eANList;
/*      */     }
/*      */ 
/*      */     
/* 8020 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 8021 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */ 
/*      */       
/* 8025 */       for (byte b1 = 0; b1 < entityItem.getUpLinkCount(); b1++) {
/* 8026 */         if (entityItem.getDownLink(b1).getEntityType().equals(paramString1) && entityItem.getDownLink(b1).getEntityID() == paramInt) {
/* 8027 */           for (byte b2 = 0; b2 < entityItem.getUpLinkCount(); b2++) {
/*      */             
/* 8029 */             if (entityItem.getUpLink(b2).getEntityType().equals(paramString2)) {
/* 8030 */               eANList.put((EANObject)entityItem.getUpLink(b2));
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 8037 */     return eANList;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setFileBreakTag(String paramString1, String paramString2) {
/* 8042 */     println(".* <!--STARTFILEBREAKFORMAIL:" + paramString2 + ": FOR :" + paramString1 + "-->");
/*      */   }
/*      */ 
/*      */   
/*      */   public void dereference() {
/* 8047 */     super.dereference();
/*      */     
/* 8049 */     this.m_slg = null;
/* 8050 */     this.m_pdhg = null;
/* 8051 */     this.m_pdhi = null;
/* 8052 */     this.m_pw = null;
/* 8053 */     this.m_db = null;
/* 8054 */     this.m_prof = null;
/* 8055 */     this.m_nlsi = null;
/* 8056 */     this.m_abri = null;
/* 8057 */     this.m_strABRSessionID = null;
/* 8058 */     if (this.m_htUpdates != null) {
/* 8059 */       this.m_htUpdates.clear();
/* 8060 */       this.m_htUpdates = null;
/*      */     } 
/* 8062 */     if (this.m_elist != null) {
/* 8063 */       this.m_elist.dereference();
/* 8064 */       this.m_elist = null;
/*      */     } 
/*      */     
/* 8067 */     this.m_strDGSubmit = null;
/* 8068 */     this.m_strEpoch = null;
/* 8069 */     this.m_strEndOfDay = null;
/* 8070 */     this.m_strForever = null;
/* 8071 */     this.m_strToday = null;
/* 8072 */     this.m_strNow = null;
/* 8073 */     this.m_cbOn = null;
/* 8074 */     if (this.m_hsetSkipType != null) {
/* 8075 */       this.m_hsetSkipType.clear();
/* 8076 */       this.m_hsetSkipType = null;
/*      */     } 
/* 8078 */     if (this.m_hDisplay != null) {
/* 8079 */       this.m_hDisplay.clear();
/* 8080 */       this.m_hDisplay = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public abstract String getDescription();
/*      */   
/*      */   public abstract String getABRVersion();
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\PokBaseABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */