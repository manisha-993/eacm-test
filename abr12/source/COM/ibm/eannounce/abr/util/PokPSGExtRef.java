// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2000
// All Rights Reserved.
//
// $Log: PokPSGExtRef.java,v $
// Revision 1.1.1.1  2003/06/03 19:02:25  dave
// new 1.1.1 abr 
//
// Revision 1.1.1.1  2002/06/20 16:16:59  bala
// Creating ODS module
//
// Revision 1.1.1.1  2002/02/07 23:29:19  cstolpe
// Initialize
//
// Revision 1.1  2002/01/23 23:45:00  dave
// added new ExtRef Interface for PSG
//
//

//package COM.ibm.eannounce.abr.engine;
package COM.ibm.eannounce.abr.util;

public interface PokPSGExtRef {
   //Phase values
   /*
   0010|Concept
   0020|Develop
   0030|Launch
   0040|Life Cycle
   0050|Plan
   0060|Pre Concept
   0070|Qualify
   */
   public static final String XR_PSGPHASE_Concept                               = "0010";
   public static final String XR_PSGPHASE_Develop                               = "0020";
   public static final String XR_PSGPHASE_Launch                                = "0030";
   public static final String XR_PSGPHASE_Life_Cycle                            = "0040";
   public static final String XR_PSGPHASE_Plan                                  = "0050";
   public static final String XR_PSGPHASE_Pre_Concept                           = "0060";
   public static final String XR_PSGPHASE_Qualify                               = "0070";

}
