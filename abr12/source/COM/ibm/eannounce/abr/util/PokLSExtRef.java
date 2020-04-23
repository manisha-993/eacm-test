// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2001
// All Rights Reserved.
//
// $Log: PokLSExtRef.java,v $
// Revision 1.1.1.1  2003/06/03 19:02:25  dave
// new 1.1.1 abr 
//
// Revision 1.1.1.1  2002/06/20 16:16:59  bala
// Creating ODS module
//
// Revision 1.1.1.1  2002/02/07 23:29:19  cstolpe
// Initialize
//
// Revision 1.3  2002/02/04 17:14:57  chris
// fix LSWWLIFECYCLE
//
// Revision 1.2  2002/01/15 00:26:59  dave
// branding interface files
// and added logging
//
// Revision 1.1  2002/01/04 00:59:23  dave
// sourcefile rearrangement
//

//package COM.ibm.eannounce.abr.engine;
package COM.ibm.eannounce.abr.util;

public interface PokLSExtRef  {

  public static final String CLASS_BRAND = new String("$Id: PokLSExtRef.java,v 1.1.1.1 2003/06/03 19:02:25 dave Exp $");

	// LSCRSAUDIENCE
	/* flag value|flag description
	  0010|1  Internal Only
	  0020|2  Internal/External Public
	  0030|3  External Public
	  0040|4  Private Only
	  0050|5  External Public/Private
	  0060|6  All
	*/
	public static final String XR_LSCRSAUDIENCE_IntOnly 	= "0010";
	public static final String XR_LSCRSAUDIENCE_IntExtPub 	= "0020";
	public static final String XR_LSCRSAUDIENCE_ExtPub 		= "0030";
	public static final String XR_LSCRSAUDIENCE_PvtOnly 	= "0040";
	public static final String XR_LSCRSAUDIENCE_ExtPubPvt 	= "0050";
	public static final String XR_LSCRSAUDIENCE_All 		= "0060";

	// LSCRSDELIVERY
	/*
	  0010|01 CLASSROOM
	  0020|02 PROFESSIONAL SERVICES
	  0030|03 CONFERENCE
	  0040|04 LEARNING CENTER
	  0050|05 EZED
	  0060|06 IN-TERRITORY
	  0070|07 NATIONAL TECHNOLOGICAL UNIVERSITY (NTU)
	  0080|08 PUBLICATIONS
	  0090|09 IBM TECHNICAL INFORMATION RETRIEVAL CENTER (ITIRC)
	  0100|10 LIBRARY SERVICES
	  0110|11 SELF-STUDY
	  0120|12 CUSTOMIZED DELIVERABLES
	  0130|13 SATELLITE
	  0140|14 TELECONFERENCE
	  0150|15 ADMINISTRATIVE SERVICES
	  0160|16 SCORE
	  0170|17 SOFTWARE
	  0180|18 GRADUATE WORK STUDY
	  0190|19 PID
	  0200|20 DOWNLOAD & PLAY
	  0210|21 DOWNLOAD & VIEW/PRINT
	  0220|22 ON-LINE, INSTRUCTOR-LED
	  0230|23 ON-LINE, SELF-PACED
	*/
	public static final String XR_LSCRSDELIVERY_Classroom 				= "0010";
	public static final String XR_LSCRSDELIVERY_ProfessionalServices 	= "0020";
	public static final String XR_LSCRSDELIVERY_Conference 				= "0030";
	public static final String XR_LSCRSDELIVERY_LearningCenter 			= "0040";
	public static final String XR_LSCRSDELIVERY_EZED				 	= "0050";
	public static final String XR_LSCRSDELIVERY_InTerritory			 	= "0060";
	public static final String XR_LSCRSDELIVERY_NTU					 	= "0070";
	public static final String XR_LSCRSDELIVERY_Publications		 	= "0080";
	public static final String XR_LSCRSDELIVERY_ITIRC				 	= "0090";
	public static final String XR_LSCRSDELIVERY_LibraryServices		 	= "0100";
	public static final String XR_LSCRSDELIVERY_SelfStudy			 	= "0110";
	public static final String XR_LSCRSDELIVERY_CustomizedDeliverables 	= "0120";
	public static final String XR_LSCRSDELIVERY_Satellite		 		= "0130";
	public static final String XR_LSCRSDELIVERY_Teleconference		 	= "0140";
	public static final String XR_LSCRSDELIVERY_AdministrativeServices 	= "0150";
	public static final String XR_LSCRSDELIVERY_Score				 	= "0160";
	public static final String XR_LSCRSDELIVERY_Software			 	= "0170";
	public static final String XR_LSCRSDELIVERY_GraduateWorkStudy	 	= "0180";
	public static final String XR_LSCRSDELIVERY_PID					 	= "0190";
	public static final String XR_LSCRSDELIVERY_DownloadPlay		 	= "0200";
	public static final String XR_LSCRSDELIVERY_DownloadViewPrint	 	= "0210";
	public static final String XR_LSCRSDELIVERY_OnLineInstructorLed	 	= "0220";
	public static final String XR_LSCRSDELIVERY_OnLineSelfPaced	 		= "0230";

	// LSCRSDELIVERYSTATUS
	/*
	0010|Optional
	0020|Mandatory
	*/
	public static final String XR_LSCRSDELIVERYSTATUS_Optional	 		= "0010";
	public static final String XR_LSCRSDELIVERYSTATUS_Mandatory	 		= "0020";

	// LSCRSADVERTISING
	/*
	0010|1 Internet
	0020|2 Hardcopy Catalog
	0030|4 Intranet
	0040|5 CD ROM
	0050|6 No Advertising At This Time
	*/
	public static final String XR_LSCRSADVERTISING_Internet 			= "0010";
	public static final String XR_LSCRSADVERTISING_Hardcopy 			= "0020";
	public static final String XR_LSCRSADVERTISING_Intranet 			= "0030";
	public static final String XR_LSCRSADVERTISING_CDRom    			= "0040";
	public static final String XR_LSCRSADVERTISING_None     			= "0050";

	//LSWWCCLIFECYCLE values
	/*
	0010|Under Development
	0020|Validation and Initiate Workflow
	0030|Failed
	0040|Awaiting Approvals
	0050|Available
	0060|Change Request
	0070|Retired
	*/
	public static final String XR_LSWWCCLIFECYCLE_UnderDevelopment 			 = "0010";
	public static final String XR_LSWWCCLIFECYCLE_ValidateAndInitiateWorkflow  = "0020";
	public static final String XR_LSWWCCLIFECYCLE_Failed 				 	 = "0030";
	public static final String XR_LSWWCCLIFECYCLE_AwaitingApprovals 		 = "0040";
	public static final String XR_LSWWCCLIFECYCLE_Available 				 = "0050";
	public static final String XR_LSWWCCLIFECYCLE_ChangeRequest 			 = "0060";
	public static final String XR_LSWWCCLIFECYCLE_Retired 					 = "0070";

	//LSCCLIFECYCLE values
	/*
	0010|Under Development
	0020|Validation and Initiate Workflow
	0030|Failed
	0040|Awaiting Approvals
	0050|Available
	0060|Change Request
	0070|Retired
	*/
	public static final String XR_LSCCLIFECYCLE_UnderDevelopment 			 = "0010";
	public static final String XR_LSCCLIFECYCLE_ValidateAndInitiateWorkflow  = "0020";
	public static final String XR_LSCCLIFECYCLE_Failed 				 	 	 = "0030";
	public static final String XR_LSCCLIFECYCLE_AwaitingApprovals 			 = "0040";
	public static final String XR_LSCCLIFECYCLE_Available 					 = "0050";
	public static final String XR_LSCCLIFECYCLE_ChangeRequest 				 = "0060";
	public static final String XR_LSCCLIFECYCLE_Retired 					 = "0070";

	//LSWWLIFECYCLE values
	/*
	0010|New
	0020|Verify
	0030|Available
	0040|Change Request
	0050|Retired
	0060|Failed
	*/
	public static final String XR_LSWWLIFECYCLE_New 			 			 = "0010";
	public static final String XR_LSWWLIFECYCLE_Verify  					 = "0020";
	public static final String XR_LSWWLIFECYCLE_Available 					 = "0030";
	public static final String XR_LSWWLIFECYCLE_ChangeRequest 				 = "0040";
	public static final String XR_LSWWLIFECYCLE_Retired 					 = "0050";
	public static final String XR_LSWWLIFECYCLE_Failed 				 	 	 = "0060";

	//LSCURLIFECYCLE values
	/*
	0010|New
	0020|Verify
	0030|Available
	0040|Change Request
	0050|Retired
	0060|Failed
	*/
	public static final String XR_LSCURLIFECYCLE_New	 				= "0010";
	public static final String XR_LSCURLIFECYCLE_Verify 				= "0020";
	public static final String XR_LSCURLIFECYCLE_Available 				= "0030";
	public static final String XR_LSCURLIFECYCLE_ChangeRequest 			= "0040";
	public static final String XR_LSCURLIFECYCLE_Retired 				= "0050";
	public static final String XR_LSCURLIFECYCLE_Failed 				= "0060";

	//LSPRGLIFECYCLE values
	/*
	0010|New
	0020|Verify
	0030|Available
	0040|Change Request
	0050|Retired
	0060|Failed
	*/
	public static final String XR_LSPRGLIFECYCLE_New	 				= "0010";
	public static final String XR_LSPRGLIFECYCLE_Verify 				= "0020";
	public static final String XR_LSPRGLIFECYCLE_Available 				= "0030";
	public static final String XR_LSPRGLIFECYCLE_ChangeRequest 			= "0040";
	public static final String XR_LSPRGLIFECYCLE_Retired 				= "0050";
	public static final String XR_LSPRGLIFECYCLE_Failed 				= "0060";

	//LSSCLIFECYCLE values
	/*
	0010|New
	0020|Verify
	0030|Available
	0040|Change Request
	0050|Retired
	0060|Failed
	*/
	public static final String XR_LSSCLIFECYCLE_New	 					= "0010";
	public static final String XR_LSSCLIFECYCLE_Verify 					= "0020";
	public static final String XR_LSSCLIFECYCLE_Available 				= "0030";
	public static final String XR_LSSCLIFECYCLE_ChangeRequest 			= "0040";
	public static final String XR_LSSCLIFECYCLE_Retired 				= "0050";
	public static final String XR_LSSCLIFECYCLE_Failed 					= "0060";

	//LSSEGLIFECYCLE values
	/*
	0010|New
	0020|Verify
	0030|Available
	0040|Change Request
	0050|Retired
	0060|Failed
	*/
	public static final String XR_LSSEGLIFECYCLE_New	 				= "0010";
	public static final String XR_LSSEGLIFECYCLE_Verify 				= "0020";
	public static final String XR_LSSEGLIFECYCLE_Available 				= "0030";
	public static final String XR_LSSEGLIFECYCLE_ChangeRequest 			= "0040";
	public static final String XR_LSSEGLIFECYCLE_Retired 				= "0050";
	public static final String XR_LSSEGLIFECYCLE_Failed 				= "0060";

	// LSGRMLIFECYCLE values
	/*
	0010|New
	0020|Verify
	0030|Available
	0040|Change Request
	0050|Retired
	0060|Failed
	*/
	public static final String XR_LSGRMLIFECYCLE_New	 				= "0010";
	public static final String XR_LSGRMLIFECYCLE_Verify 				= "0020";
	public static final String XR_LSGRMLIFECYCLE_Available 				= "0030";
	public static final String XR_LSGRMLIFECYCLE_ChangeRequest 			= "0040";
	public static final String XR_LSGRMLIFECYCLE_Retired 				= "0050";
	public static final String XR_LSGRMLIFECYCLE_Failed 				= "0060";


	// LSCRSPRICINGSTATUS
	/*
	0010|Not Approved
	0020|Needs Pricing Approval
	0030|Approved
	0040|Denied
	*/
	public static final String XR_LSCRSPRICINGTATUS_NotApproved		 = "0010";
	public static final String XR_LSCRSPRICINGTATUS_NeedsApproval 	 = "0020";
	public static final String XR_LSCRSPRICINGTATUS_Approved	 	 = "0030";
	public static final String XR_LSCRSPRICINGTATUS_Denied		 	 = "0040";

	// LSCRSCATEDITSTATUS
	/* fixme.. these need to be updated after meta data change
	0010|Requires Catalog Editing
	0020|Not Edited
	0030|Edited
	0040|Editing Not Required
	*/
	public static final String XR_LSCRSCATEDITSTATUS_ReqCatalogEditing  = "0010";
	public static final String XR_LSCRSCATEDITSTATUS_NotEdited		    = "0020";
	public static final String XR_LSCRSCATEDITSTATUS_Edited			    = "0030";
	public static final String XR_LSCRSCATEDITSTATUS_EditingNotRequired = "0040";

	// added for compilation of catalog editing code.. remove it!
	public static final String XR_LSCRSCATEDITSTATUS_UnderEdit = "AAAA";
	public static final String XR_LSCRSCATEDITSTATUS_Approved  = "BBBB";
	public static final String XR_LSCRSCATEDITSTATUS_Denied    = "CCCC";

	// LSCRSMATLSETSTATUS
	/*
	0010|Under Development
	0020|Available
	*/
	public static final String XR_LSCRSMATLSETSTATUS_UnderDevelopment = "0010";
	public static final String XR_LSCRSMATLSETSTATUS_Available		  = "0020";

	// LSCATADIND
	/*
	0010|YES
	0020|NO
	*/
	public static final String XR_LSCATADIND_YES = "0010";
	public static final String XR_LSCATADIND_NO  = "0020";

	// ABR values
	/*
	0010|Untried
	0020|Queued
	0030|Passed
	0040|Failed
	0050|In Process
	0060|Error - Update Unsuccessful
	0070|Error - DG Not Available
	0080|Error - ABR Internal Error
	*/
	public static final String XR_ABR_Untried 					= "0010";
	public static final String XR_ABR_Queued 					= "0020";
	public static final String XR_ABR_Passed 					= "0030";
	public static final String XR_ABR_Failed 					= "0040";
	public static final String XR_ABR_In_Process				= "0050";
	public static final String XR_ABR_Error_Update_Unsuccessful	= "0060";
	public static final String XR_ABR_Error_DG_Not_Available	= "0070";
	public static final String XR_ABR_Error_ABR_Internal_Error	= "0080";

}
