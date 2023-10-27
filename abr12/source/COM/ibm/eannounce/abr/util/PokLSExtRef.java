/*    */ package COM.ibm.eannounce.abr.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface PokLSExtRef
/*    */ {
/* 32 */   public static final String CLASS_BRAND = new String("$Id: PokLSExtRef.java,v 1.1.1.1 2003/06/03 19:02:25 dave Exp $");
/*    */   public static final String XR_LSCRSAUDIENCE_IntOnly = "0010";
/*    */   public static final String XR_LSCRSAUDIENCE_IntExtPub = "0020";
/*    */   public static final String XR_LSCRSAUDIENCE_ExtPub = "0030";
/*    */   public static final String XR_LSCRSAUDIENCE_PvtOnly = "0040";
/*    */   public static final String XR_LSCRSAUDIENCE_ExtPubPvt = "0050";
/*    */   public static final String XR_LSCRSAUDIENCE_All = "0060";
/*    */   public static final String XR_LSCRSDELIVERY_Classroom = "0010";
/*    */   public static final String XR_LSCRSDELIVERY_ProfessionalServices = "0020";
/*    */   public static final String XR_LSCRSDELIVERY_Conference = "0030";
/*    */   public static final String XR_LSCRSDELIVERY_LearningCenter = "0040";
/*    */   public static final String XR_LSCRSDELIVERY_EZED = "0050";
/*    */   public static final String XR_LSCRSDELIVERY_InTerritory = "0060";
/*    */   public static final String XR_LSCRSDELIVERY_NTU = "0070";
/*    */   public static final String XR_LSCRSDELIVERY_Publications = "0080";
/*    */   public static final String XR_LSCRSDELIVERY_ITIRC = "0090";
/*    */   public static final String XR_LSCRSDELIVERY_LibraryServices = "0100";
/*    */   public static final String XR_LSCRSDELIVERY_SelfStudy = "0110";
/*    */   public static final String XR_LSCRSDELIVERY_CustomizedDeliverables = "0120";
/*    */   public static final String XR_LSCRSDELIVERY_Satellite = "0130";
/*    */   public static final String XR_LSCRSDELIVERY_Teleconference = "0140";
/*    */   public static final String XR_LSCRSDELIVERY_AdministrativeServices = "0150";
/*    */   public static final String XR_LSCRSDELIVERY_Score = "0160";
/*    */   public static final String XR_LSCRSDELIVERY_Software = "0170";
/*    */   public static final String XR_LSCRSDELIVERY_GraduateWorkStudy = "0180";
/*    */   public static final String XR_LSCRSDELIVERY_PID = "0190";
/*    */   public static final String XR_LSCRSDELIVERY_DownloadPlay = "0200";
/*    */   public static final String XR_LSCRSDELIVERY_DownloadViewPrint = "0210";
/*    */   public static final String XR_LSCRSDELIVERY_OnLineInstructorLed = "0220";
/*    */   public static final String XR_LSCRSDELIVERY_OnLineSelfPaced = "0230";
/*    */   public static final String XR_LSCRSDELIVERYSTATUS_Optional = "0010";
/*    */   public static final String XR_LSCRSDELIVERYSTATUS_Mandatory = "0020";
/*    */   public static final String XR_LSCRSADVERTISING_Internet = "0010";
/*    */   public static final String XR_LSCRSADVERTISING_Hardcopy = "0020";
/*    */   public static final String XR_LSCRSADVERTISING_Intranet = "0030";
/*    */   public static final String XR_LSCRSADVERTISING_CDRom = "0040";
/*    */   public static final String XR_LSCRSADVERTISING_None = "0050";
/*    */   public static final String XR_LSWWCCLIFECYCLE_UnderDevelopment = "0010";
/*    */   public static final String XR_LSWWCCLIFECYCLE_ValidateAndInitiateWorkflow = "0020";
/*    */   public static final String XR_LSWWCCLIFECYCLE_Failed = "0030";
/*    */   public static final String XR_LSWWCCLIFECYCLE_AwaitingApprovals = "0040";
/*    */   public static final String XR_LSWWCCLIFECYCLE_Available = "0050";
/*    */   public static final String XR_LSWWCCLIFECYCLE_ChangeRequest = "0060";
/*    */   public static final String XR_LSWWCCLIFECYCLE_Retired = "0070";
/*    */   public static final String XR_LSCCLIFECYCLE_UnderDevelopment = "0010";
/*    */   public static final String XR_LSCCLIFECYCLE_ValidateAndInitiateWorkflow = "0020";
/*    */   public static final String XR_LSCCLIFECYCLE_Failed = "0030";
/*    */   public static final String XR_LSCCLIFECYCLE_AwaitingApprovals = "0040";
/*    */   public static final String XR_LSCCLIFECYCLE_Available = "0050";
/*    */   public static final String XR_LSCCLIFECYCLE_ChangeRequest = "0060";
/*    */   public static final String XR_LSCCLIFECYCLE_Retired = "0070";
/*    */   public static final String XR_LSWWLIFECYCLE_New = "0010";
/*    */   public static final String XR_LSWWLIFECYCLE_Verify = "0020";
/*    */   public static final String XR_LSWWLIFECYCLE_Available = "0030";
/*    */   public static final String XR_LSWWLIFECYCLE_ChangeRequest = "0040";
/*    */   public static final String XR_LSWWLIFECYCLE_Retired = "0050";
/*    */   public static final String XR_LSWWLIFECYCLE_Failed = "0060";
/*    */   public static final String XR_LSCURLIFECYCLE_New = "0010";
/*    */   public static final String XR_LSCURLIFECYCLE_Verify = "0020";
/*    */   public static final String XR_LSCURLIFECYCLE_Available = "0030";
/*    */   public static final String XR_LSCURLIFECYCLE_ChangeRequest = "0040";
/*    */   public static final String XR_LSCURLIFECYCLE_Retired = "0050";
/*    */   public static final String XR_LSCURLIFECYCLE_Failed = "0060";
/*    */   public static final String XR_LSPRGLIFECYCLE_New = "0010";
/*    */   public static final String XR_LSPRGLIFECYCLE_Verify = "0020";
/*    */   public static final String XR_LSPRGLIFECYCLE_Available = "0030";
/*    */   public static final String XR_LSPRGLIFECYCLE_ChangeRequest = "0040";
/*    */   public static final String XR_LSPRGLIFECYCLE_Retired = "0050";
/*    */   public static final String XR_LSPRGLIFECYCLE_Failed = "0060";
/*    */   public static final String XR_LSSCLIFECYCLE_New = "0010";
/*    */   public static final String XR_LSSCLIFECYCLE_Verify = "0020";
/*    */   public static final String XR_LSSCLIFECYCLE_Available = "0030";
/*    */   public static final String XR_LSSCLIFECYCLE_ChangeRequest = "0040";
/*    */   public static final String XR_LSSCLIFECYCLE_Retired = "0050";
/*    */   public static final String XR_LSSCLIFECYCLE_Failed = "0060";
/*    */   public static final String XR_LSSEGLIFECYCLE_New = "0010";
/*    */   public static final String XR_LSSEGLIFECYCLE_Verify = "0020";
/*    */   public static final String XR_LSSEGLIFECYCLE_Available = "0030";
/*    */   public static final String XR_LSSEGLIFECYCLE_ChangeRequest = "0040";
/*    */   public static final String XR_LSSEGLIFECYCLE_Retired = "0050";
/*    */   public static final String XR_LSSEGLIFECYCLE_Failed = "0060";
/*    */   public static final String XR_LSGRMLIFECYCLE_New = "0010";
/*    */   public static final String XR_LSGRMLIFECYCLE_Verify = "0020";
/*    */   public static final String XR_LSGRMLIFECYCLE_Available = "0030";
/*    */   public static final String XR_LSGRMLIFECYCLE_ChangeRequest = "0040";
/*    */   public static final String XR_LSGRMLIFECYCLE_Retired = "0050";
/*    */   public static final String XR_LSGRMLIFECYCLE_Failed = "0060";
/*    */   public static final String XR_LSCRSPRICINGTATUS_NotApproved = "0010";
/*    */   public static final String XR_LSCRSPRICINGTATUS_NeedsApproval = "0020";
/*    */   public static final String XR_LSCRSPRICINGTATUS_Approved = "0030";
/*    */   public static final String XR_LSCRSPRICINGTATUS_Denied = "0040";
/*    */   public static final String XR_LSCRSCATEDITSTATUS_ReqCatalogEditing = "0010";
/*    */   public static final String XR_LSCRSCATEDITSTATUS_NotEdited = "0020";
/*    */   public static final String XR_LSCRSCATEDITSTATUS_Edited = "0030";
/*    */   public static final String XR_LSCRSCATEDITSTATUS_EditingNotRequired = "0040";
/*    */   public static final String XR_LSCRSCATEDITSTATUS_UnderEdit = "AAAA";
/*    */   public static final String XR_LSCRSCATEDITSTATUS_Approved = "BBBB";
/*    */   public static final String XR_LSCRSCATEDITSTATUS_Denied = "CCCC";
/*    */   public static final String XR_LSCRSMATLSETSTATUS_UnderDevelopment = "0010";
/*    */   public static final String XR_LSCRSMATLSETSTATUS_Available = "0020";
/*    */   public static final String XR_LSCATADIND_YES = "0010";
/*    */   public static final String XR_LSCATADIND_NO = "0020";
/*    */   public static final String XR_ABR_Untried = "0010";
/*    */   public static final String XR_ABR_Queued = "0020";
/*    */   public static final String XR_ABR_Passed = "0030";
/*    */   public static final String XR_ABR_Failed = "0040";
/*    */   public static final String XR_ABR_In_Process = "0050";
/*    */   public static final String XR_ABR_Error_Update_Unsuccessful = "0060";
/*    */   public static final String XR_ABR_Error_DG_Not_Available = "0070";
/*    */   public static final String XR_ABR_Error_ABR_Internal_Error = "0080";
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\PokLSExtRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */