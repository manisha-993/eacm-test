/**
 * <pre>
 * (c) Copyright International Business Machines Corporation, 2003
 * All Rights Reserved.
 *
 * $Log: DDABR.java,v $
 * Revision 1.17  2006/01/24 22:13:54  yang
 * Jtest changes
 *
 * Revision 1.16  2006/01/03 14:19:42  chris
 * Fix for MN25744163. Only look at HD for count.
 *
 * Revision 1.15  2005/11/21 18:49:43  yang
 * adding logs to debug for MN ticket
 *
 * Revision 1.14  2005/01/31 16:30:06  joan
 * make changes for Jtest
 *
 * Revision 1.13  2005/01/25 16:58:47  chris
 * Use ROUND_HALF_UP not ROUND_UP
 *
 * Revision 1.12  2005/01/25 16:21:31  chris
 * Fix for MN22518302 and some JTest cleanup.
 *
 * Revision 1.11  2005/01/05 15:42:37  chris
 * Fix for MN 22283522. Weight rounding error.
 *
 * Revision 1.10  2004/10/28 21:45:58  joan
 * change to different method than ei.getUpLink().values() b/c it's no longer valid
 *
 * Revision 1.9  2004/09/28 15:13:14  wendy
 * CR0702045701 chgs
 *
 * Revision 1.8  2004/08/11 12:50:29  chris
 * Implement fix for TIR USRO-R-NMHR-63QQXY ABR will now Fail under this condition
 *
 * Revision 1.7  2004/07/29 14:59:58  chris
 * Calculate Weight bases on CR0514046924
 *
 * Revision 1.6  2004/02/19 17:55:38  chris
 * Fix for feedback 53637:770C4C
 *
 * Revision 1.5  2004/01/08 16:51:54  chris
 * Fix for feedback 53508:6585EE
 *
 * Revision 1.4  2004/01/05 18:20:34  chris
 * Fix feedback 53458:07A50B
 *
 * Revision 1.3  2003/12/24 17:37:46  chris
 * Fix feedbacks 53466:78DBFD and 53467:7D1FEE
 *
 * Revision 1.2  2003/12/19 19:29:37  chris
 * Fix calculation
 *
 * Revision 1.1  2003/12/18 19:54:25  chris
 * Initial DDABR
 *
 *
 * </pre>
 *
 *@author     Christopher S. Stolpe
 *@created    November 10, 2003
 */
package COM.ibm.eannounce.abr.psg;

//import COM.ibm.opicmpdh.transactions.*;
//import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;

import java.util.*;
import java.math.BigDecimal;
import java.text.*;

/**
 * Derives Data attributes from OF and VAR
 * @author cstolpe
 *
 */
public class DDABR extends PokBaseABR {
	private MessageFormat mfOut = null;
	private Object[] mfParms = new String[10];
	private ResourceBundle msgs = null;
	private StringBuffer rpt = new StringBuffer();
	private StringBuffer traceSb = new StringBuffer();
	private static final int GB_VALUE = 1024;
	private static final BigDecimal ONETHOUSAND = new BigDecimal(1000);
	/**
	 *  Execute ABR.
	 *
	 */
	public void execute_run() {
		StringBuffer navName = new StringBuffer();
		try {
			EntityGroup eg;
			Iterator itMeta;
			boolean system = false;
			start_ABRBuild();
			setReturnCode(PASS);
			// NAME is navigate attributes
			eg = new EntityGroup(null, m_db, m_prof, getRootEntityType(), "Navigate"); //$NON-NLS-1$
			itMeta = eg.getMetaAttribute().values().iterator();
			while (itMeta.hasNext()) {
				EANMetaAttribute ma = (EANMetaAttribute) itMeta.next();
				navName.append(
					getAttributeValue(
						getRootEntityType(),
						getRootEntityID(),
						ma.getAttributeCode()));
				if (itMeta.hasNext()) {
					navName.append(" "); //$NON-NLS-1$
				}
			}
			msgs =
				ResourceBundle.getBundle(
					this.getClass().getName(),
					getLocale(m_prof.getReadLanguage().getNLSID()));
			mfParms = new String[10];

			if ("OF".equals(getEntityType())) { //$NON-NLS-1$
				EANAttribute eanAttr = m_elist.getParentEntityGroup().getEntityItem(0).getAttribute("OFFERINGTYPE"); //$NON-NLS-1$
				if (eanAttr != null && eanAttr instanceof EANFlagAttribute) {
					EANFlagAttribute eanfAttr = (EANFlagAttribute) eanAttr;
					system = eanfAttr.isSelected("0080"); //$NON-NLS-1$
				}
			}

			rpt.append("<ol>"); //$NON-NLS-1$
			if ("VAR".equals(getEntityType()) || system) { //$NON-NLS-1$
				EntityGroup egDD = m_elist.getEntityGroup("DD"); //$NON-NLS-1$
				EntityItem eiDD = null;
				if (egDD.getEntityItemCount() == 0) {
					// Create and link a new one
					CreateActionItem cai = new CreateActionItem(null, m_db, m_prof, "CR" + getEntityType() + "DD"); //$NON-NLS-2$  //$NON-NLS-1$
					EntityItem[] aItems = new EntityItem[1];
					EntityList elDD;
					eg = m_elist.getParentEntityGroup();
					aItems[0] = eg.getEntityItem(0); // This is the VAR or OF
					elDD = new EntityList(m_db, m_prof, cai, aItems);
					egDD = elDD.getEntityGroup("DD"); //$NON-NLS-1$
					if (egDD.getEntityItemCount() == 1) {
						EANAttribute eaaOFFERINGPNUMB;
						eiDD = egDD.getEntityItem(0);
						eaaOFFERINGPNUMB = m_elist.getParentEntityGroup().getEntityItem(0).getAttribute("OFFERINGPNUMB"); //$NON-NLS-1$
						setText(eiDD, "NAME", eaaOFFERINGPNUMB.get() + " - DD"); //$NON-NLS-2$  //$NON-NLS-1$
					} else {
						setReturnCode(FAIL);
						mfOut = new MessageFormat(msgs.getString("DD_CR_ERR")); //$NON-NLS-1$
						rpt.append(mfOut.format(mfParms));
					}
				} else if (egDD.getEntityItemCount() == 1) {
					eiDD = egDD.getEntityItem(0);
				} else if (egDD.getEntityItemCount() > 1) {
					// log an error message
					mfOut = new MessageFormat(msgs.getString("DD_ERR")); //$NON-NLS-1$
					rpt.append(mfOut.format(mfParms));
					setReturnCode(FAIL);
				}
				if (eiDD != null) {
					logMessage("DDABR:setTotalAvailableSlots"); //$NON-NLS-1$
					setTotalAvailableSlots(eiDD);
					logMessage("DDABR:setTotalAvailableBays"); //$NON-NLS-1$
					setTotalAvailableBays(eiDD);
					logMessage("DDABR:setMemoryStandard"); //$NON-NLS-1$
					setMemoryRAMStandard(eiDD);
					logMessage("DDABR:setTotalL2CacheStandard"); //$NON-NLS-1$
					setTotalL2CacheStandard(eiDD);
					/* postponed (but tested)
					logMessage("DDABR:setRAMSocketsAvailable");
					setRAMSocketsAvailable(eiDD);
					*/
					logMessage("DDABR:setNumberOfProcessorsStandard"); //$NON-NLS-1$
					setNumberOfProcessorsStandard(eiDD);
					logMessage("DDABR:setNumberOfInstalledHardDrives"); //$NON-NLS-1$
					setNumberOfInstalledHardDrives(eiDD);
					logMessage("DDABR:setWeight"); //$NON-NLS-1$
					setWeight(eiDD);
					if (eiDD.hasChanges()) {
						EntityItem eiXXXDD;
						eiDD.commit(m_db, null);
						// Commit the relator too
						eiXXXDD = (EntityItem) eiDD.getUpLink(0);
						eiXXXDD.commit(m_db, null);
					}
				}
			} else {
				mfOut = new MessageFormat(msgs.getString("SYS_ERR")); //$NON-NLS-1$
				rpt.append(mfOut.format(mfParms));
				setReturnCode(FAIL);
			}
			rpt.append("</ol>"); //$NON-NLS-1$
			rpt.append("<!-- DEBUG: " + traceSb.toString() + " -->"); //$NON-NLS-2$  //$NON-NLS-1$
		} catch (Throwable exc) {
			java.io.StringWriter exBuf = new java.io.StringWriter();
			setReturnCode(FAIL);
			// Report this error to both the datbase log and the PrintWriter
			mfOut = new MessageFormat(msgs.getString("EXCEPTION_ERROR")); //$NON-NLS-1$
			mfParms[0] = m_abri.getABRCode();
			mfParms[1] = exc.getMessage();
			rpt.append(mfOut.format(mfParms));
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			rpt.append("<!-- "); //$NON-NLS-1$
			rpt.append(exBuf.getBuffer().toString());
			rpt.append(" -->"); //$NON-NLS-1$
			rpt.append("</ol>\n"); //$NON-NLS-1$
			rpt.append("<!-- DEBUG: " + traceSb.toString() + " -->"); //$NON-NLS-2$  //$NON-NLS-1$
		} finally {
			setDGTitle(navName.toString());
			setDGRptName(getShortClassName(getClass()));
			setDGRptClass("WWABR"); //$NON-NLS-1$
			// make sure the lock is released
			if (!isReadOnly()) {
				clearSoftLock();
			}
		}
		// Insert Header into beginning of report
		navName.append((getReturnCode() == PASS) ? " Passed" : " Failed"); //$NON-NLS-2$  //$NON-NLS-1$
		mfOut = new MessageFormat(msgs.getString("HEADER")); //$NON-NLS-1$
		mfParms[0] = getShortClassName(getClass());
		mfParms[1] = navName.toString();
		mfParms[2] = getNow();
		mfParms[3] = m_prof.getOPName();
		mfParms[4] = m_prof.getRoleDescription();
		mfParms[5] = getDescription();
		mfParms[6] = getABRVersion();
		rpt.insert(0, mfOut.format(mfParms));
		println(rpt.toString()); // Output the Report
		printDGSubmitString();
		buildReportFooter();
	}

	/**
	 * Postponed=> MB:TOTCARDSLOTS=Sum(PSL:SLOTS_TOTAL)
	 * DD:TOTAVAILCARDSLOTS=Sum(PSLAVAIL:SLOTS_AVAIL)
	 */
	private void setTotalAvailableSlots(EntityItem eiDD)
		throws
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    COM.ibm.eannounce.objects.EANBusinessRuleException {
		try {
			//Rules:
			//If [entityType=VAR] or [If entityType=OF and OF.OFFERINGTYPE = SYSTEM(0080)]
			//  If OF/VAR has 1 MB (OFMB, OFSBBMB, VARSBBMB) and 1+ PSL or SBB-PSL attached
			/* Postponed
			EntityGroup egMB = m_elist.getEntityGroup("MB");
			EntityGroup egPSL = m_elist.getEntityGroup("PSL");
			if (egMB.getEntityItemCount() == 0) {
			    // missing MB error
			}
			else if (egMB.getEntityItemCount() > 1) {
			    // too many MB error
			}
			else {
			    EntityItem eiMB = egMB.getEntityItem(0);
			    EANAttribute att = eiMB.getAttribute("TOTCARDSLOTS");
			    int total = 0;
			    if (att != null) {
			        total = Integer.parseInt(att.get().toString());
			    }
			    Iterator itPSL = egPSL.getEntityItem().values().iterator();
			    // For each OFPSL, OF-SBBPSL, VAR-SBBPSL relator:
			    while (itPSL.hasNext()) {
			        EntityItem eiPSL = (EntityItem) itPSL.next();
			        EANAttribute attSLOTS_TOTAL = eiPSL.getAttribute("SLOTS_TOTAL");
			        // MB.TOTCARDSLOTS = Sum(PSL.SLOTS_TOTAL)
			        if (attSLOTS_TOTAL != null) {
			            try {
			                total = Integer.parseInt(attSLOTS_TOTAL.get().toString());
			            }
			            catch(NumberFormatException e) {
			                // Number format error
			            }
			        }
			        else {
			            // not populated error message
			        }
			    }
			    //  Else Log to Error Report (Identify error to be reported)
			}
			Postponed */

			EntityGroup egPSLAVAIL = m_elist.getEntityGroup("PSLAVAIL"); //$NON-NLS-1$
			// If OF/VAR has 0/1 DD (OFDD, VARDD) and 1+ PSLAVAIL attached
			if (egPSLAVAIL.getEntityItemCount() > 0) {
				// For each OFPSLAVAIL, VARPSLAVAIL relator:
				int iSLOTS_AVAIL = 0;
				Iterator itPSLAVAIL =
					egPSLAVAIL.getEntityItem().values().iterator();
				while (itPSLAVAIL.hasNext()) {
					EntityItem eiPSLAVAIL = (EntityItem) itPSLAVAIL.next();
					EANAttribute eaaSLOTS_AVAIL = eiPSLAVAIL.getAttribute("SLOTS_AVAIL"); //$NON-NLS-1$
					//  DD.TOTAVAILCARDSLOTS = Sum(PSLAVAIL.SLOTS_AVAIL)
					if (eaaSLOTS_AVAIL != null
						&& eaaSLOTS_AVAIL instanceof EANTextAttribute) {
						iSLOTS_AVAIL
							+= Integer.parseInt(eaaSLOTS_AVAIL.get().toString());
					}
				}
				// Assign sum to DD.TOTAVAILCARDSLOTS
				setText(eiDD, "TOTAVAILCARDSLOTS", Integer.toString(iSLOTS_AVAIL)); //$NON-NLS-1$
			} else {
				//  Else Log to Error Report (Identify error to be reported)
				// missing PSLAVAIL
				// Feedback 53458:07A50B
				//setReturnCode(FAIL);
				//mfParms[0] = egPSLAVAIL.getLongDescription();
				//mfOut = new MessageFormat(msgs.getString("MISSING_MSG"));
				//rpt.append(mfOut.format(mfParms));
			}
			//
			//Error Report:
			//  Concatenate errors and produce Error Report in Entity Structure Report Format:
			//OF+DD+MB+SBBMB+PSL+SBBPSL+PSLAVAIL
			//-or-  VAR+DD+SBBMB+SBBPSL+PSLAVAIL
			//Message: The system reported here is missing a Planar, Slots, or Slots Available element.  Please correct and re-run this business rule to validate your changes.
			//  Mapping: RptCat:WWDERDATA, Fail, PR:BRANDCODE
		} catch (Exception e) {
			setReturnCode(FAIL);
			mfOut = new MessageFormat(msgs.getString("Execption06")); //$NON-NLS-1$
			mfParms[0] = e.getMessage();
			rpt.append(mfOut.format(mfParms));
		}
	}

	/**
	 * Postponed=> PP:TOTBAYS=Sum(PBY:TOTAL_BAYS)
	 * DD:TOTAVAILBAYS=Sum(PBYAVAIL:TOTAL_BAYS_AVAIL)
	 */
	private void setTotalAvailableBays(EntityItem eiDD)
		throws
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    COM.ibm.eannounce.objects.EANBusinessRuleException {
		try {
			//Rules:
			//If [entityType=VAR] or [If entityType=OF and OF.OFFERINGTYPE = SYSTEM(0080)]
			//  If OF/VAR has 1 PP (OFPP, OFSBBPP, VARSBBPP) and 1+ PBY or SBB-PBY attached
			//      For each OFPBY, OF-SBBPBY, VAR-SBBPBY relator:
			//          PP.TOTBAYS = Sum(PBY.TOTAL_BAYS)
			//  Else Log to Error Report (Identify error to be reported)
			EntityGroup egPBYAVAIL = m_elist.getEntityGroup("PBYAVAIL"); //$NON-NLS-1$
			//  If OF/VAR has 0/1 DD (OFDD, VARDD) and 1+ PBYAVAIL attached
			if (egPBYAVAIL.getEntityItemCount() > 0) {
				int iTOTAL_BAYS_AVAIL = 0;
				// For each OFPBYAVAIL, VARPBYAVAIL relator:
				Iterator itPBYAVAIL =
					egPBYAVAIL.getEntityItem().values().iterator();
				while (itPBYAVAIL.hasNext()) {
					EntityItem eiPBYAVAIL = (EntityItem) itPBYAVAIL.next();
					EANAttribute eaaTOTAL_BAYS_AVAIL = eiPBYAVAIL.getAttribute("TOTAL_BAYS_AVAIL"); //$NON-NLS-1$
					if (eaaTOTAL_BAYS_AVAIL != null
						&& eaaTOTAL_BAYS_AVAIL instanceof EANTextAttribute) {
						// DD.TOTAVAILBAYS = Sum(PBYAVAIL.TOTAL_BAYS_AVAIL)
						iTOTAL_BAYS_AVAIL
							+= Integer.parseInt(
								eaaTOTAL_BAYS_AVAIL.get().toString());
					}
				}
				// Assign sum to DD:TOTAVAILBAYS
				setText(eiDD, "TOTAVAILBAYS", Integer.toString(iTOTAL_BAYS_AVAIL)); //$NON-NLS-1$
			} else {
				//  Else Log to Error Report (Identify error to be reported)
				// Missing PBYAVAIL
				// Feedback 53458:07A50B
				//setReturnCode(FAIL);
				//mfParms[0] = egPBYAVAIL.getLongDescription();
				//mfOut = new MessageFormat(msgs.getString("MISSING_MSG"));
				//rpt.append(mfOut.format(mfParms));
			}
			//
			//Error Report:
			//  Concatenate errors and produce Error Report in Entity Structure Report Format:
			//OF+DD+PP+SBBPP+PBY+SBBPBY+PBYAVAIL
			//-or-  VAR+DD+SBBPP+SBBPBY+PBYAVAIL
			//Message: The system reported here is missing a Mechanical Package, Bays, or Bays Available element.  Please correct and re-run this business rule to validate your changes.
			//  Mapping: RptCat:WWDERDATA, Fail, PR:BRANDCODE
		} catch (Exception e) {
			setReturnCode(FAIL);
			mfOut = new MessageFormat(msgs.getString("Execption05")); //$NON-NLS-1$
			mfParms[0] = e.getMessage();
			rpt.append(mfOut.format(mfParms));
		}
	}

	/**
	 * DD:MEMRAMSTD=MEM:MEMCAPACITY * (OFSBB:OFSBBQTY or VARSBB:VARSBBQTY)
	 * DD:MEMRAMSTDUNITS=MEM:MEMCAPACITYUNITS
	 */
	private void setMemoryRAMStandard(EntityItem eiDD)
		throws
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    COM.ibm.eannounce.objects.EANBusinessRuleException {
		try {
			//Rules:
			//If [entityType=VAR] or [If entityType=OF and OF.OFFERINGTYPE = SYSTEM(0080)]
			EntityGroup egMEM = m_elist.getEntityGroup("MEM"); //$NON-NLS-1$
			//If OF/VAR has only 1 MEM attached through OFMEM, OF-SBBMEM, or VAR-SBBMEM
			if (egMEM.getEntityItemCount() == 1) {
				EntityItem eiMEM = egMEM.getEntityItem(0);
				// DD.MEMRAMSTDUNITS = MEM.MEMCAPACITYUNITS
				EANAttribute eaaMEMCAPACITY = eiMEM.getAttribute("MEMCAPACITY"); //$NON-NLS-1$
				if (eaaMEMCAPACITY != null
					&& eaaMEMCAPACITY instanceof EANTextAttribute) {
					int iMEMCAPACITY =
						Integer.parseInt(eaaMEMCAPACITY.get().toString());
					String strMEMCAPACITY;
					EANAttribute eaaMEMCAPACITYUNITS;
					iMEMCAPACITY *= getSBBQTY(eiMEM);
					// If related through VARSBB or OFSBB and VARSBBQTY or OFSBBQTY > 1
					//      DD.MEMRAMSTD = (xx.xxSBBQTY) x (MEM.MEMCAPACITY)
					// Else
					//      DD.MEMRAMSTD = MEM.MEMCAPACITY
					//If DD.MEMRAMSTD > 1000 and DD.MEMRAMSTDUNITS = ‘MB’
					strMEMCAPACITY = Integer.toString(iMEMCAPACITY);
					eaaMEMCAPACITYUNITS = eiMEM.getAttribute("MEMCAPACITYUNITS"); //$NON-NLS-1$
					if (eaaMEMCAPACITYUNITS != null
						&& eaaMEMCAPACITYUNITS instanceof EANFlagAttribute) {
						EANFlagAttribute fa =
							(EANFlagAttribute) eaaMEMCAPACITYUNITS;
						String strMEMCAPACITYUNITS =
							fa.getFirstActiveFlagCode();
						String strDesc =
							fa.getFlagLongDescription(strMEMCAPACITYUNITS);
						if (iMEMCAPACITY > 1000 && strMEMCAPACITYUNITS.equals("0010")) { //$NON-NLS-1$
							//  DD.MEMRAMSTD = DD.MEMRAMSTD/1000 (truncated to 1 decimal)
							int point;
							strMEMCAPACITY =
								Double.toString(iMEMCAPACITY / 1000.0);
							point = strMEMCAPACITY.indexOf('.');
							if (point > 0) {
								if (strMEMCAPACITY.charAt(point + 1) == '0') {
									// Remove decimal point and trailing digits
									strMEMCAPACITY =
										strMEMCAPACITY.substring(0, point);
								} else {
									// Remove trailing digits after the tenths place.
									strMEMCAPACITY =
										strMEMCAPACITY.substring(0, point + 2);
								}
							}
							//  DD.MEMRAMSTDUNITS = ‘GB’
							setFlagByCode(eiDD, "MEMRAMSTDUNITS", "0040"); //$NON-NLS-2$  //$NON-NLS-1$
						} else {
							// DD.MEMRAMSTDUNITS = MEM.MEMCAPACITYUNITS
							setFlagByDescription(eiDD, "MEMRAMSTDUNITS", strDesc); //$NON-NLS-1$
						}
					}
					setText(eiDD, "MEMRAMSTD", strMEMCAPACITY); //$NON-NLS-1$
				}
				//Else Log Problems to Error Report
			} else if (egMEM.getEntityItemCount() == 0) {
				//If OF/VAR has 0 MEM attached through OFMEM, OF-SBBMEM, or VAR-SBBMEM
				// Set DD.MEMRAMSTDUNITS = MB (0030)
				setFlagByCode(eiDD, "MEMRAMSTDUNITS", "0030"); //$NON-NLS-2$  //$NON-NLS-1$
				// Set DD.MEMRAMSTD = 0
				setText(eiDD, "MEMRAMSTD", "0"); //$NON-NLS-2$  //$NON-NLS-1$
				//Else Log Problems to Error Report
			} else if (egMEM.getEntityItemCount() > 1) {
				setMemoryStandardCR5701(eiDD, egMEM);
				/* from here comment this out for CR5701
				
				            //If OF/VAR has >1 MEM attached through OFMEM, OF-SBBMEM, or VAR-SBBMEM
				            // If all MEM.MEMCAPACITYUNITS are identical
				            String strMEMCAPACITYUNITS = "";
				            String strDesc = "";
				            boolean identical = true; // Assume they are
				            Iterator itMEM = egMEM.getEntityItem().values().iterator();
				            // Get the string value of the first one
				            EANAttribute eaaMEMCAPACITYUNITS = ((EntityItem) itMEM.next()).getAttribute("MEMCAPACITYUNITS");
				            if (eaaMEMCAPACITYUNITS != null && eaaMEMCAPACITYUNITS instanceof EANFlagAttribute) {
				                EANFlagAttribute fa = (EANFlagAttribute) eaaMEMCAPACITYUNITS;
				                MetaFlag[] mfa = (MetaFlag[]) fa.get();
				                for (int i = 0; i < mfa.length; i++) {
				                    if (mfa[i].isSelected()) {
				                        strMEMCAPACITYUNITS = mfa[i].getFlagCode();
				                        strDesc = mfa[i].getLongDescription();
				                    }
				                }
				            }
				            else {
				                identical = false;
				            }
				            // Test that the rest have the same value
				            while (itMEM.hasNext()) {
				                eaaMEMCAPACITYUNITS = ((EntityItem) itMEM.next()).getAttribute("MEMCAPACITYUNITS");
				                if (eaaMEMCAPACITYUNITS != null && eaaMEMCAPACITYUNITS instanceof EANFlagAttribute) {
				                    EANFlagAttribute fa = (EANFlagAttribute) eaaMEMCAPACITYUNITS;
				                    if (!fa.isSelected(strMEMCAPACITYUNITS)) {
				                        identical = false;
				                    }
				                }
				                else {
				                    identical = false;
				                }
				            }
				            // at this point we have the last eaaMEMCAPACITYUNITS and they should all be itentical
				            if (identical) {
				                //      For each OFMEM, OF-SBBMEM, VAR-SBBMEM relator
				                itMEM = egMEM.getEntityItem().values().iterator();
				                int iMEMCAPACITY = 0;
				                while (itMEM.hasNext()) {
				                    EntityItem eiMEM = (EntityItem) itMEM.next();
				                    EANAttribute eaaMEMCAPACITY = eiMEM.getAttribute("MEMCAPACITY");
				                    int tmp = Integer.parseInt(eaaMEMCAPACITY.get().toString());
				                    tmp *= getSBBQTY(eiMEM);
				                    iMEMCAPACITY += tmp;
				                    //  If any related thru VARSBB/OFSBB and VARSBBQTY/OFSBBQTY >1
				                    //      DD.MEMRAMSTD = OF-MEM.MEMCAPACITY +
				                    //      SBB-MEM.MEMCAPACITY (where QTY<=1) +
				                    //      (xx.xxSBBQTY) x (MEM.MEMCAPACITY) where QTY>1
				                    //  Else
				                    //      DD.MEMRAMSTD = Sum(MEM.MEMCAPACITY)
				                    //Else Log Problems to Error Report
				                }
				                String strMEMCAPACITY = Integer.toString(iMEMCAPACITY);
				                //If DD.MEMRAMSTD > 1000 and DD.MEMRAMSTDUNITS = ‘MB’
				                if (iMEMCAPACITY > 1000 && strMEMCAPACITYUNITS.equals("0010")) {
				                    //  DD.MEMRAMSTD = DD.MEMRAMSTD/1000 (truncated to 1 decimal)
				                    strMEMCAPACITY = Double.toString(iMEMCAPACITY / 1000.0);
				                    int point = strMEMCAPACITY.indexOf('.');
				                    if (point > 0) {
				                        if (strMEMCAPACITY.charAt(point + 1) == '0') {
				                            // Remove decimal point and trailing digits
				                            strMEMCAPACITY = strMEMCAPACITY.substring(0, point);
				                        }
				                        else {
				                            // Remove trailing digits after the tenths place.
				                            strMEMCAPACITY = strMEMCAPACITY.substring(0, point + 2);
				                        }
				                    }
				                    //  DD.MEMRAMSTDUNITS = ‘GB’
				                    setFlagByCode(eiDD, "MEMRAMSTDUNITS", "0040");
				                }
				                else {
				                    // DD.MEMRAMSTDUNITS = MEM.MEMCAPACITYUNITS
				                    setFlagByDescription(eiDD, "MEMRAMSTDUNITS", strDesc);
				                }
				
				                setText(eiDD, "MEMRAMSTD", strMEMCAPACITY);
				            }
				            else {
				                setReturnCode(FAIL);
				                mfOut = new MessageFormat(msgs.getString("MEM_CAP_ERR"));
				                rpt.append(mfOut.format(mfParms));
				            }
				// to here.. comment this out for CR5701
				*/
				//Log any problems to Error Report
			}
			//
			//Error Report:
			//  Concatenate errors and produce Error Report in Entity Structure Report Format:
			//OF+DD+MEM+SBBMEM
			//-or-  VAR+DD+SBBMEM
			//Message: This system has more than one memory element attached and the Memory Capacity Units do not match.  Please correct and re-run this business rule to validate your changes.
			//  Mapping: RptCat:WWDERDATA, Fail, PR:BRANDCODE
		} catch (Exception e) {
			setReturnCode(FAIL);
			mfOut = new MessageFormat(msgs.getString("Execption01")); //$NON-NLS-1$
			mfParms[0] = e.getMessage();
			rpt.append(mfOut.format(mfParms));
		}
	}

	/** CR0702045701
	 * Calculations must be able to add MEM.MEMCAPACITY with different MEM.MEMCAPACITYUNITS. There are three
	 * MB values for MEMCAPACITY: 128, 256, 512. There is only one GB value for MEMCAPACITY: 1. MB values
	 * can be added and then converted to GB values. Basic conversion (rounded to tenth decimal if necessary):
	 *  1024MB = 1GB
	 *  1152MB (1GB + 128MB) = 1.1GB
	 *  1280MB (1GB + 256MB) = 1.2GB
	 *  1536MB (1GB + 512MB) = 1.5GB
	 *  2048MB = 2GB
	 * Common examples:
	 *  1GB + 1GB + 512MB + 512MB = 3GB
	 *  512MB + 512MB = 1GB
	 * Other factors to keep in mind:
	 *  If total is 1024 or greater, MEMRAMSTD should be entered as GB value.
	 *  If MEMRAMSTD is a whole number, do not include any decimal.
	 *  Most often, MEMRAMSTD ends up as whole GB or with half (1.5, 2.5, 3.5, etc).
    From Amy:
	1. MEM.MEMCAPACITYUNITS can be kB, MB or GB Don't include kB in the calculations. Will never be used on a system unit.
	2. DD.MEMRAMSTDUNITS doesn't have a kB value, only MB or GB kB will never be used on a system unit. It's an option-only data.
	3. Some of our legacy data does not MEM capacity and units blank. If this MEM is used today, the DD Memory is set to 0MB
	 */
	private void setMemoryStandardCR5701(EntityItem eiDD, EntityGroup egMEM) {
		//DD.MEMRAMSTDUNITS     0030 MB, 0040 GB
		//MEM.MEMCAPACITYUNITS  0010 MB, 0020 kB, 0030 GB

		String strMEMCAPACITYUNITS = ""; //$NON-NLS-1$
		int iMEMCAPACITY = 0;
		String strMEMCAPACITY = ""; //$NON-NLS-1$
		try {
			// get all capacity and calculate units
			Iterator itMEM = egMEM.getEntityItem().values().iterator();
			while (itMEM.hasNext()) {
				EntityItem memItem = (EntityItem) itMEM.next();
				// get units
				String units = getAttributeFlagEnabledValue(memItem, "MEMCAPACITYUNITS"); //$NON-NLS-1$
				EANAttribute capAttr;
				int capacity;
				int qty;
				// if units not set
				if (units == null) {
					units = "0010"; //$NON-NLS-1$
				} // def to MB
				if (units.equals("0020")) // amy says can't get kb, but log and skip it!!  //$NON-NLS-1$
                {
					traceSb.append(memItem.getEntityType() + ":" + memItem.getEntityID() + " MEMCAPACITYUNITS is kB, so skipping\n"); //$NON-NLS-2$  //$NON-NLS-1$
					continue;
				}
				// get capacity
				capAttr = memItem.getAttribute("MEMCAPACITY"); //$NON-NLS-1$
				if (capAttr == null) // like 0 so skip
                {
					traceSb.append(memItem.getEntityType() + ":" + memItem.getEntityID() + " MEMCAPACITY was not set\n"); //$NON-NLS-2$  //$NON-NLS-1$
					continue;
				}
				capacity = Integer.parseInt(capAttr.get().toString());
				qty = getSBBQTY(memItem);
				//  If any related thru VARSBB/OFSBB and VARSBBQTY/OFSBBQTY >1
				//      DD.MEMRAMSTD = OF-MEM.MEMCAPACITY +
				//      SBB-MEM.MEMCAPACITY (where QTY<=1) +
				//      (xx.xxSBBQTY) x (MEM.MEMCAPACITY) where QTY>1
				//  Else
				//      DD.MEMRAMSTD = Sum(MEM.MEMCAPACITY)

				traceSb.append(memItem.getEntityType() + ":" + memItem.getEntityID() + " MEMCAPACITYUNITS = [" + units + "] " + //$NON-NLS-1$  //$NON-NLS-3$  //$NON-NLS-2$
                    getAttributeValue(memItem.getEntityType(), memItem.getEntityID(), "MEMCAPACITYUNITS", "") + //$NON-NLS-2$  //$NON-NLS-1$
                    " MEMCAPACITY: " + capacity + " SBBQTY: " + qty + "\n"); //$NON-NLS-3$  //$NON-NLS-2$  //$NON-NLS-1$
				capacity *= qty;

				if ("0030".equals(units)) { // is GB  //$NON-NLS-1$
					capacity *= GB_VALUE;
				}

				iMEMCAPACITY += capacity; // accum all
			}
			traceSb.append("Total capacity " + iMEMCAPACITY + " (mb) \n"); //$NON-NLS-2$  //$NON-NLS-1$

			if (iMEMCAPACITY < GB_VALUE) //
            {
				strMEMCAPACITYUNITS = "MB"; //$NON-NLS-1$
				strMEMCAPACITY = "" + iMEMCAPACITY; //$NON-NLS-1$
			} else {
				float flMEMCAPACITY = ((float) iMEMCAPACITY) / GB_VALUE;
				// go from MB to GB 1280mb to 1.280gb, 1024mb to 1.024gb
				strMEMCAPACITYUNITS = "GB"; //$NON-NLS-1$
				iMEMCAPACITY = (int) (flMEMCAPACITY * 10.0);
				// drop trailing digits to 12gb, 10gb
				if (iMEMCAPACITY % 10 == 0) // ends with 0
                {
					strMEMCAPACITY = "" + (iMEMCAPACITY / 10); // remove trailing 0, won't have decimal pt as int to 1gb  //$NON-NLS-1$
				} else {
					flMEMCAPACITY = iMEMCAPACITY / (float) 10.0;
					// add decimal pt to 1.2gb
					strMEMCAPACITY = Float.toString(flMEMCAPACITY);
				}
			}

			traceSb.append("Setting MEMRAMSTD to " + strMEMCAPACITY + " and MEMRAMSTDUNITS to " + strMEMCAPACITYUNITS + "\n"); //$NON-NLS-3$  //$NON-NLS-2$  //$NON-NLS-1$

			// DD.MEMRAMSTDUNITS = MEM.MEMCAPACITYUNITS
			setFlagByDescription(eiDD, "MEMRAMSTDUNITS", strMEMCAPACITYUNITS); //$NON-NLS-1$
			setText(eiDD, "MEMRAMSTD", strMEMCAPACITY); //$NON-NLS-1$
		} catch (Exception e) {
			setReturnCode(FAIL);
			mfOut = new MessageFormat(msgs.getString("Execption01")); //$NON-NLS-1$
			mfParms[0] = e.getMessage();
			rpt.append(mfOut.format(mfParms));
		}
	}

	/**
	 * DD:TOT_L2_CACHE_STD=PRC:INTL2CACHESIZE*(OFSBB:OFSBBQTY or VARSBB:VARSBBQTY)
	 * DD:TOTL2CACHESTDUNITS=PRC:INTL2CACHESIZEUNIT
	 */
	private void setTotalL2CacheStandard(EntityItem eiDD)
		throws
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    COM.ibm.eannounce.objects.EANBusinessRuleException {
		EANFlagAttribute fa;
		BigDecimal tmp;
		BigDecimal bdINTL2CACHESIZE;
		MetaFlag[] mfa;
		String strDesc = null;
		try {
			//Rules:
			//If [entityType=VAR] or [If entityType=OF and OF.OFFERINGTYPE = SYSTEM(0080)]
			EntityGroup egPRC = m_elist.getEntityGroup("PRC"); //$NON-NLS-1$
			if (egPRC.getEntityItemCount() == 1) {
				//  If OF/VAR has only 1 PRC attached through OFPRC, OF-SBBPRC, or VAR-SBBPRC
				EntityItem eiPRC = egPRC.getEntityItem(0);
				// DD.TOTL2CACHESTDUNITS = PRC.INTL2CACHESIZEUNIT
				EANAttribute eaaINTL2CACHESIZE = eiPRC.getAttribute("INTL2CACHESIZE"); //$NON-NLS-1$
				if (eaaINTL2CACHESIZE != null
					&& eaaINTL2CACHESIZE instanceof EANFlagAttribute) {
					fa = (EANFlagAttribute) eaaINTL2CACHESIZE;
					strDesc =
						fa.getFlagLongDescription(fa.getFirstActiveFlagCode());
					bdINTL2CACHESIZE = new BigDecimal(strDesc);
					EANAttribute eaaINTL2CACHESIZEUNIT;
					bdINTL2CACHESIZE =
						bdINTL2CACHESIZE.multiply(
							new BigDecimal(getSBBQTY(eiPRC)));
					//      If related through VARSBB or OFSBB and VARSBBQTY or OFSBBQTY > 1
					//          DD. TOT_L2_CACHE_STD = (xx.xxSBBQTY) x (PRC.INTL2CACHESIZE)
					//      Else
					//          DD.TOT_L2_CACHE_STD = PRC.INTL2CACHESIZE
					eaaINTL2CACHESIZEUNIT = eiPRC.getAttribute("INTL2CACHESIZEUNIT"); //$NON-NLS-1$
					if (eaaINTL2CACHESIZEUNIT != null
						&& eaaINTL2CACHESIZEUNIT instanceof EANFlagAttribute) {
						EANFlagAttribute efaINTL2CACHESIZEUNIT =
							(EANFlagAttribute) eaaINTL2CACHESIZEUNIT;
						String strINTL2CACHESIZEUNIT =
							efaINTL2CACHESIZEUNIT.getFirstActiveFlagCode();
						//String strDesc = efaINTL2CACHESIZEUNIT.getFlagLongDescription(strINTL2CACHESIZEUNIT);
						//If DD.TOT_L2_CACHE_STD > 1000 and DD.TOTL2CACHESTDUNITS = ‘KB’
						if (bdINTL2CACHESIZE.compareTo(ONETHOUSAND) > 0 && strINTL2CACHESIZEUNIT.equals("0010")) { //$NON-NLS-1$
							//  DD.TOT_L2_CACHE_STD = DD.TOT_L2_CACHE_STD /1000 (truncated to 1 decimal)
							bdINTL2CACHESIZE =
								bdINTL2CACHESIZE.divide(
									ONETHOUSAND,
									BigDecimal.ROUND_HALF_UP);
							//  DD.TOTL2CACHESTDUNITS = ‘MB’
							setFlagByCode(eiDD, "TOTL2CACHESTDUNITS", "0020"); //$NON-NLS-2$  //$NON-NLS-1$
						} else {
							// DD.TOTL2CACHESTDUNITS = PRC.INTL2CACHESIZEUNIT
							setFlagByCode(eiDD, "TOTL2CACHESTDUNITS", strINTL2CACHESIZEUNIT); //$NON-NLS-1$
						}
					}
					setFlagToClosestNumericalMatch(eiDD, "TOT_L2_CACHE_STD", bdINTL2CACHESIZE); //$NON-NLS-1$
					//  Else Log Problems to Error Report
				}
			} else if (egPRC.getEntityItemCount() == 0) {
				//  If OF/VAR has 0 MB attached through OFPRC, OF-SBBPRC, or VAR-SBBPRC
				//      Set DD.TOTL2CACHESTDUNITS= KB (0010)
				setFlagByCode(eiDD, "TOTL2CACHESTDUNITS", "0010"); //$NON-NLS-2$  //$NON-NLS-1$
				//      Set DD.TOT_L2_CACHE_STD = 0
				setFlagByCode(eiDD, "TOT_L2_CACHE_STD", "0010"); //$NON-NLS-2$  //$NON-NLS-1$
				//  Else Log Problems to Error Report
			} else if (egPRC.getEntityItemCount() > 1) {
				//  If OF/VAR/SBB has >1 PRC attached through OFPRC, OF-SBBPRC, or VAR-SBBPRC
				//      For each OFPRC, OF-SBBPRC, VAR-SBBPRC relator:
				//      If all PRC.INTL2CACHESIZEUNIT are identical
				String strINTL2CACHESIZEUNIT = ""; //$NON-NLS-1$
				boolean identical = true; // Assume they are
				Iterator itPRC = egPRC.getEntityItem().values().iterator();
				// Get the string value of the first one
				EANAttribute eaaINTL2CACHESIZEUNIT = ((EntityItem) itPRC.next()).getAttribute("INTL2CACHESIZEUNIT"); //$NON-NLS-1$
				if (eaaINTL2CACHESIZEUNIT != null
					&& eaaINTL2CACHESIZEUNIT instanceof EANFlagAttribute) {
					fa =
						(EANFlagAttribute) eaaINTL2CACHESIZEUNIT;
					mfa = (MetaFlag[]) fa.get();
					for (int i = 0; i < mfa.length; i++) {
						if (mfa[i].isSelected()) {
							strINTL2CACHESIZEUNIT = mfa[i].getFlagCode();
						}
					}
				} else {
					identical = false;
				}
				// Test that the rest have the same value
				while (itPRC.hasNext()) {
					eaaINTL2CACHESIZEUNIT = ((EntityItem) itPRC.next()).getAttribute("INTL2CACHESIZEUNIT"); //$NON-NLS-1$
					if (eaaINTL2CACHESIZEUNIT != null
						&& eaaINTL2CACHESIZEUNIT instanceof EANFlagAttribute) {
						fa =
							(EANFlagAttribute) eaaINTL2CACHESIZEUNIT;
						if (!fa.isSelected(strINTL2CACHESIZEUNIT)) {
							identical = false;
						}
					} else {
						identical = false;
					}
				}
				// at this point we have the last eaaINTL2CACHESIZEUNIT and they should all be itentical
				if (identical) {
					// DD.TOTL2CACHESTDUNITS = PRC.INTL2CACHESIZEUNIT
					bdINTL2CACHESIZE = new BigDecimal("0"); //$NON-NLS-1$
					itPRC = egPRC.getEntityItem().values().iterator();
					while (itPRC.hasNext()) {
						EntityItem eiPRC = (EntityItem) itPRC.next();
						EANAttribute eaaINTL2CACHESIZE = eiPRC.getAttribute("INTL2CACHESIZE"); //$NON-NLS-1$
						if (eaaINTL2CACHESIZE != null
							&& eaaINTL2CACHESIZE instanceof EANFlagAttribute) {
							logMessage("eaaINTL2CACHESIZE: " + eaaINTL2CACHESIZE); //$NON-NLS-1$
							fa =
								(EANFlagAttribute) eaaINTL2CACHESIZE;
							logMessage("fa: " + fa); //$NON-NLS-1$
							tmp =
								new BigDecimal(
									fa.getFlagLongDescription(
										fa.getFirstActiveFlagCode()));
							tmp.multiply(new BigDecimal(getSBBQTY(eiPRC)));
							logMessage("tmp: " + tmp); //$NON-NLS-1$
							bdINTL2CACHESIZE = bdINTL2CACHESIZE.add(tmp);
							logMessage("bdINTL2CACHESIZE: " + bdINTL2CACHESIZE); //$NON-NLS-1$
							// If any related thru VARSBB/OFSBB and VARSBBQTY/OFSBBQTY >1
							//      DD.TOT_L2_CACHE_STD = OF-PRC.INTL2CACHESIZE +
							//      SBB- PRC.INTL2CACHESIZE (where QTY<=1) +
							//      (xx.xxSBBQTY) x (PRC.INTL2CACHESIZE) where QTY>1
							// Else
							//      DD.TOT_L2_CACHE_STD = Sum(PRC.INTL2CACHESIZE)
							// Else Log Problems to Error Report
						}
					}
					//If DD.TOT_L2_CACHE_STD > 1000 and DD.TOTL2CACHESTDUNITS = ‘KB’
					if (bdINTL2CACHESIZE.compareTo(ONETHOUSAND) > 0 && strINTL2CACHESIZEUNIT.equals("0010")) { //$NON-NLS-1$
						//  DD.TOT_L2_CACHE_STD = DD.TOT_L2_CACHE_STD /1000 (truncated to 1 decimal)
						bdINTL2CACHESIZE =
							bdINTL2CACHESIZE.divide(
								ONETHOUSAND,
								BigDecimal.ROUND_HALF_UP);
						//  DD.TOTL2CACHESTDUNITS = ‘MB’
						setFlagByCode(eiDD, "TOTL2CACHESTDUNITS", "0020"); //$NON-NLS-2$  //$NON-NLS-1$
					} else {
						// DD.TOTL2CACHESTDUNITS = PRC.INTL2CACHESIZEUNIT
						setFlagByCode(eiDD, "TOTL2CACHESTDUNITS", strINTL2CACHESIZEUNIT); //$NON-NLS-1$
					}
					setFlagToClosestNumericalMatch(eiDD, "TOT_L2_CACHE_STD", bdINTL2CACHESIZE); //$NON-NLS-1$
				} else {
					//Log any problems to Error Report
					setReturnCode(FAIL);
					mfOut = new MessageFormat(msgs.getString("L2_UNIT_ERR")); //$NON-NLS-1$
					rpt.append(mfOut.format(mfParms));
				}
			}
			//
			//
			//Error Report
			//  Concatenate errors and produce Error Report in Entity Structure Report Format:
			//OF+DD+PRC+SBBPRC
			//-or-  VAR+DD+SBBPRC
			//Message: This system has more than one processor element attached and the Internal L2 Cache Units on these processors do not match.  Please correct and re-run this business rule to validate your changes.
			//  Mapping: RptCat:WWDERDATA, Fail, PR:BRANDCODE
		} catch (Exception e) {
			logMessage("Exception e" + e); //$NON-NLS-1$
			setReturnCode(FAIL);
			mfOut = new MessageFormat(msgs.getString("Execption07")); //$NON-NLS-1$
			logMessage("mfOut" + mfOut); //$NON-NLS-1$
			mfParms[0] = e.getMessage();
			logMessage("mfParms[0]" + mfParms[0]); //$NON-NLS-1$
			rpt.append(mfOut.format(mfParms));
		}
	}

	/**
	 * Postponed DD:RAMSLOTSAVAIL=MB:RAMSLOTSTOT + (OFSBB:OFSBBQTY or VARSBB:VARSBBQTY)
	private void setRAMSocketsAvailable(EntityItem eiDD)
	throws
	    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
	    COM.ibm.eannounce.objects.EANBusinessRuleException
	{
	    try {
	    //Rules:
	    //If [entityType=VAR] or [If entityType=OF and OF.OFFERINGTYPE = SYSTEM(0080)]
	    //  If OF/VAR/SBB has >1 MEM attached through OFMEM, OF-SBBMEM, or VAR-SBBMEM
	    //      For each OFMEM, OF-SBBMEM, VAR-SBBMEM relator:
	    int iRAMSLOTSAVAIL = m_elist.getEntityGroup("OFMEM").getEntityItemCount();
	    EntityGroup egSBBMEM = m_elist.getEntityGroup("SBBMEM");
	    Iterator itSBBMEM = egSBBMEM.getEntityItem().values().iterator();
	    while (itSBBMEM.hasNext()) {
	        EntityItem eiSBBMEM = (EntityItem) itSBBMEM.next();
	        EntityItem eiMEM = (EntityItem) eiSBBMEM.getDownLink(0);
	        iRAMSLOTSAVAIL += getSBBQTY(eiMEM);
	        // If any related thru VARSBB/OFSBB and VARSBBQTY/OFSBBQTY >1
	        //      DD.RAMSLOTSAVAIL = Count(OFMEM) +
	        //      Count(SBBMEM) (where QTY<=1) +
	        //      (xxMEM.xxSBBQTY) where QTY>1
	        // Else
	        //      DD.RAMSLOTSAVAIL = Count(OFMEM) + Count(SBBMEM)
	        // Else Log Problems to Error Report
	    }
	    //  If DD.RAMSLOTSAVAIL calculation > MB.RAMSLOTSTOT
	    EntityGroup egMB = m_elist.getEntityGroup("MB");
	    if (egMB.getEntityItemCount() == 1) {
	        EntityItem eiMB = egMB.getEntityItem(0);
	        EANAttribute eaaRAMSLOTSTOT = eiMB.getAttribute("RAMSLOTSTOT");
	        if (eaaRAMSLOTSTOT != null && eaaRAMSLOTSTOT instanceof EANFlagAttribute) {
	            EANFlagAttribute fa = (EANFlagAttribute) eaaRAMSLOTSTOT;
	            // grab leading digits from selected flag
	            String strDesc = fa.getFlagLongDescription(fa.getFirstActiveFlagCode());
	            int iLen = strDesc.length();
	            int i = 0;
	            while (i < iLen && Character.isDigit(strDesc.charAt(i))) {
	                i++;
	            }
	            if (i == iLen) {
	                i = Integer.parseInt(strDesc);
	            }
	            else {
	                i = Integer.parseInt(strDesc.substring(0,i));
	            }
	            if (iRAMSLOTSAVAIL > i) {
	                // Do not write value, Present Error to User
	                setReturnCode(FAIL);
	                mfOut = new MessageFormat(msgs.getString("MB_MEM_ERR"));
	                rpt.append(mfOut.format(mfParms));
	            }
	            else {
	                setFlagByDescription(eiDD, "RAMSLOTSAVAIL", fa.getFlagLongDescription(fa.getFirstActiveFlagCode()));
	            }
	        }
	    }
	    //
	    //Error Report
	    //  Concatenate errors and produce Error Report in Entity Structure Report Format:
	    //OF+DD+MB+SBBMB+MEM+SBBMEM
	    //-or-  VAR+DD+SBBMB+SBBMEM
	    //Message: The total number of memory elements attached through to the Offering, Variant, and SBB exceeds the Total RAM Sockets allowed for this system (as defined on the planar).  Please correct and re-run this business rule to validate your changes.
	    //  Mapping: RptCat:WWDERDATA, Fail, PR:BRANDCODE
	    }
	    catch (Exception e) {
	        setReturnCode(FAIL);
	        mfOut = new MessageFormat(msgs.getString("Execption04"));
	        mfParms[0] = e.getMessage();
	        rpt.append(mfOut.format(mfParms));
	    }
	}
	 */

	/**
	 * DD:NUMPROCSTD=MB:NUMPROCMAX (OFSBB:OFSBBQTY or VARSBB:VARSBBQTY)
	 */
	private void setNumberOfProcessorsStandard(EntityItem eiDD)
		throws
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    COM.ibm.eannounce.objects.EANBusinessRuleException {
		try {
			//Rules:
			//If [entityType=VAR] or [If entityType=OF and OF.OFFERINGTYPE = SYSTEM(0080)]
			//  If OF/VAR/SBB has >1 PRC attached through OFPRC, OF-SBBPRC, or VAR-SBBPRC
			//      For each OFPRC, OF-SBBPRC, VAR-SBBPRC relator:
			int iNUMPROCSTD = 0;
			EntityGroup egPRC = m_elist.getEntityGroup("PRC"); //$NON-NLS-1$
			Iterator itPRC;
			EntityGroup egMB;
			// CR0702045701 If there is not a PRC, populate NUMPROCSTD with 0 (zero).
			if (egPRC.getEntityItemCount() == 0) {
				setText(eiDD, "NUMPROCSTD", Integer.toString(iNUMPROCSTD)); //$NON-NLS-1$
				return; // nothing else to check, no PRC
			}
			itPRC = egPRC.getEntityItem().values().iterator();
			while (itPRC.hasNext()) {
				EntityItem eiPRC = (EntityItem) itPRC.next();
				// If any related thru VARSBB/OFSBB and VARSBBQTY/OFSBBQTY >1
				//      DD.NUMPROCSTD = Count(OFPRC) +
				//      Count(SBBPRC) (where QTY<=1) +
				//      (xxPRC.xxSBBQTY) where QTY>1
				//  Else
				//      DD.NUMPROCSTD = Count(OFPRC) + Count(SBBPRC)
				iNUMPROCSTD += getSBBQTY(eiPRC);
				//Else Log Problems to Error Report
			}
			//  If DD.NUMPROCSTD calculation > MB.NUMPROCMAX
			egMB = m_elist.getEntityGroup("MB"); //$NON-NLS-1$
			if (egMB.getEntityItemCount() == 1) {
				EntityItem eiMB = egMB.getEntityItem(0);
				EANAttribute eaaNUMPROCMAX = eiMB.getAttribute("NUMPROCMAX"); //$NON-NLS-1$
				int iNUMPROCMAX = 1; // assume 1 per feedback 53508:6585EE
				if (eaaNUMPROCMAX != null
					&& eaaNUMPROCMAX instanceof EANTextAttribute) {
					iNUMPROCMAX =
						Integer.parseInt(eaaNUMPROCMAX.get().toString());
				}
				if (iNUMPROCSTD > iNUMPROCMAX) {
					// Do not write value, Present Error to User
					setReturnCode(FAIL);
					mfOut = new MessageFormat(msgs.getString("MAX_PRC_ERR")); //$NON-NLS-1$
					rpt.append(mfOut.format(mfParms));
				} else {
					setText(eiDD, "NUMPROCSTD", Integer.toString(iNUMPROCSTD)); //$NON-NLS-1$
				}
			} else {
				setReturnCode(FAIL);
				mfParms[0] = egMB.getLongDescription();
				if (egMB.getEntityItemCount() == 0) {
					mfOut = new MessageFormat(msgs.getString("MISSING_MSG")); //$NON-NLS-1$
				} else {
					mfOut = new MessageFormat(msgs.getString("TOO_MANY_MSG")); //$NON-NLS-1$
				}
				rpt.append(mfOut.format(mfParms));
			}
			//
			//Error Report
			//  Concatenate errors and produce Error Report in Entity Structure Report Format:
			//OF+DD+MB+SBBMB+PRC+SBBPRC
			//-or-  VAR+DD+SBBMB+SBBPRC
			//Message: The total number of processor elements attached through to the Offering, Variant, and SBB exceeds the Maximum Number of Processors allowed for this system (as defined on the planar).  Please correct and re-run this business rule to validate your changes.
			//  Mapping: RptCat:WWDERDATA, Fail, PR:BRANDCODE
		} catch (Exception e) {
			setReturnCode(FAIL);
			mfOut = new MessageFormat(msgs.getString("Execption03")); //$NON-NLS-1$
			mfParms[0] = e.getMessage();
			rpt.append(mfOut.format(mfParms));
		}
	}

	/**
	 * DD:NUMINSTHD=HD:HDDCAPACITY (OFSBB:OFSBBQTY or VARSBB:VARSBBQTY)
	 */
	private void setNumberOfInstalledHardDrives(EntityItem eiDD)
		throws
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    COM.ibm.eannounce.objects.EANBusinessRuleException {
		try {
			//Rules:
			//If [entityType=VAR] or [If entityType=OF and OF.OFFERINGTYPE = SYSTEM(0080)]
			//	If OF/VAR/SBB has >1 HD attached through OFHD, OF-SBBHD, or VAR-SBBHD
			//	For each OFHD, OF-SBBHD, VAR-SBBHD relator:
			//		If no related HD.HDDCAPACITY > 0 then
			//			DD.NUMINSTHD = 0       Note: zero “0” is a valid count
			//		If 1+ related HD.HDDCAPACITY > 0 then
			//			If any related thru VARSBB/OFSBB and VARSBBQTY/OFSBBQTY >1
			//				DD.NUMINSTHD = Count(OFHD) +
			//				Count(SBBHD) (where QTY<=1) +
			//				(xxHD.xxSBBQTY) where QTY>1
			//			Else
			//				DD.NUMINSTHD = Count(OFHD) + Count(SBBHD)
			int iNUMINSTHD = 0;
			EntityGroup egHD = m_elist.getEntityGroup("HD"); //$NON-NLS-1$
			//EntityGroup egSBBHD;
			Iterator itHD;
			// Only look at HD (MN25744163)
			//if (egOFHD != null) {
			//	Iterator itOFHD = egOFHD.getEntityItem().values().iterator();
			//	while (itOFHD.hasNext()) {
			//		EntityItem eiOFHD = (EntityItem) itOFHD.next();
			//		EntityItem eiHD = (EntityItem) eiOFHD.getDownLink(0);
			//		EANAttribute eaaHDDCAPACITY = eiHD.getAttribute("HDDCAPACITY");  //$NON-NLS-1$
			//		if (eaaHDDCAPACITY != null && eaaHDDCAPACITY instanceof EANTextAttribute) {
			//			if (Double.parseDouble(eaaHDDCAPACITY.get().toString()) > 0) {
			//				iNUMINSTHD++;
			//			}
			//		}
			//	}
			//}
			//egSBBHD = m_elist.getEntityGroup("SBBHD");  //$NON-NLS-1$
			itHD = egHD.getEntityItem().values().iterator();
			while (itHD.hasNext()) {
				EntityItem eiHD = (EntityItem) itHD.next();
				//EntityItem eiHD = (EntityItem) eiSBBHD.getDownLink(0);
				EANAttribute eaaHDDCAPACITY = eiHD.getAttribute("HDDCAPACITY"); //$NON-NLS-1$
				if (eaaHDDCAPACITY != null
					&& eaaHDDCAPACITY instanceof EANTextAttribute) {
					if (Double.parseDouble(eaaHDDCAPACITY.get().toString())
						> 0) {
						iNUMINSTHD += getSBBQTY(eiHD);
					}
				}
			}

			// CR0702045701 If there is not a HD, populate NUMINSTHD with 0 (zero).
			//if (m_elist.getEntityGroup("HD").getEntityItemCount() > 0) {
			if (!setText(eiDD, "NUMINSTHD", Integer.toString(iNUMINSTHD))) { //$NON-NLS-1$
				setReturnCode(FAIL);
				mfOut = new MessageFormat(msgs.getString("HD_ERR")); //$NON-NLS-1$
				rpt.append(mfOut.format(mfParms));
			}
			//}
			//Else Log Problems to Error Report
			//
			//Error Report
			//  Concatenate errors and produce Error Report in Entity Structure Report Format:
			//OF+DD+HD+SBBHD
			//-or-  VAR+DD+SBBHD
			//Message: There was an error calculating the total number of installed hard drives.  Please validate the Hard Drive elements attached to the OF or SBB and re-run this business rule to validate your changes.
			//  Mapping: RptCat:WWDERDATA, Fail, PR:BRANDCODE
		} catch (Exception e) {
			setReturnCode(FAIL);
			mfOut = new MessageFormat(msgs.getString("Execption02")); //$NON-NLS-1$
			mfParms[0] = e.getMessage();
			rpt.append(mfOut.format(mfParms));
		}
	}

	/**
	 * Implements CR0514046924
	 * DD.WEIGHT_METRIC = Sum(SBB.WEIGHT_METRIC_SBB * (OFSBB:OFSBBQTY or VARSBB:VARSBBQTY))
	 * DD.WEIGHT_METRICUNITS = SBB.WEIGHT_METRICUNITS_SBB (all units must match)
	 * DD.WEIGHT_US = Sum(SBB.WEIGHT_US_SBB * (OFSBB:OFSBBQTY or VARSBB:VARSBBQTY))
	 * DD.WEIGHT_USUNITS = SBB.WEIGHT_USUNITS_SBB (all units must match)
	 */
	private void setWeight(EntityItem eiDD)
		throws
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    COM.ibm.eannounce.objects.EANBusinessRuleException {
		//Source attributes:
		//SBB.WEIGHT_METRIC_SBB
		//SBB.WEIGHT_METRICUNITS_SBB
		//SBB.WEIGHT_US_SBB
		//SBB.WEIGHT_USUNITS_SBB

		//New DD weight attributes should be based on the SBB linked to the VAR or OF.
		//Not all SBBs will have a weight populated.
		BigDecimal weightMetric = new BigDecimal("0"); //$NON-NLS-1$
		BigDecimal weightUS = new BigDecimal("0"); //$NON-NLS-1$
		String unitsMetric = null;
		String unitsUS = null;
		boolean error = false;
		Iterator itSBB = m_elist.getEntityGroup("SBB").getEntityItem().values().iterator(); //$NON-NLS-1$
		if (!itSBB.hasNext()) {
			mfOut = new MessageFormat(msgs.getString("NO_SBB")); //$NON-NLS-1$
			rpt.append(mfOut.format(mfParms));
		} else {
			while (itSBB.hasNext()) {
				EntityItem eiSBB = (EntityItem) itSBB.next();
				EANAttribute attWEIGHT_METRIC_SBB = eiSBB.getAttribute("WEIGHT_METRIC_SBB"); //$NON-NLS-1$
				EANAttribute attWEIGHT_METUNITS_SBB = eiSBB.getAttribute("WEIGHT_METUNITS_SBB"); //$NON-NLS-1$
				EANAttribute attWEIGHT_US_SBB = eiSBB.getAttribute("WEIGHT_US_SBB"); //$NON-NLS-1$
				EANAttribute attWEIGHT_USUNITS_SBB = eiSBB.getAttribute("WEIGHT_USUNITS_SBB"); //$NON-NLS-1$
				// Get the SBB quantity from the relator to the root entity type
				EntityItem eiXXXSBB = (EntityItem) eiSBB.getUpLink(0);
				// VARSBB or OFSBB
				EANAttribute eaaSBBQTY = eiXXXSBB.getAttribute(getRootEntityType() + "SBBQTY"); //$NON-NLS-1$
				BigDecimal qty = new BigDecimal("1"); //$NON-NLS-1$
				if (eaaSBBQTY != null
					&& eaaSBBQTY instanceof EANTextAttribute) {
					qty = new BigDecimal(eaaSBBQTY.get().toString());
				}
				// null weight values are assumed to be 0
				if (attWEIGHT_METUNITS_SBB != null
					&& attWEIGHT_USUNITS_SBB != null) {
					String unitsMetricTMP = null;
					String unitsUSTMP = null;
					if (attWEIGHT_METUNITS_SBB instanceof EANFlagAttribute) {
						unitsMetricTMP =
							((EANFlagAttribute) attWEIGHT_METUNITS_SBB)
                        .getFirstActiveFlagCode();
					}
					if (attWEIGHT_USUNITS_SBB instanceof EANFlagAttribute) {
						unitsUSTMP =
							((EANFlagAttribute) attWEIGHT_USUNITS_SBB)
						.getFirstActiveFlagCode();
					}
					if (unitsMetric == null) {
						// Initialize metric an US units
						unitsMetric = unitsMetricTMP;
						unitsUS = unitsUSTMP;
					}
					// check metric units against the initial metric units (all must match)
					if (unitsMetric.equals(unitsMetricTMP)) {
						if (attWEIGHT_METRIC_SBB != null) {
							weightMetric =
								weightMetric.add(
									(
										new BigDecimal(
											attWEIGHT_METRIC_SBB
                                        .toString()))
                                .multiply(
										qty));
						}
					} else {
						error = true;
						mfParms[0] =
							eiSBB.getEntityGroup().getLongDescription();
						mfParms[1] = eiSBB.getEntityGroup().getMetaAttribute("WEIGHT_METUNITS_SBB").getLongDescription(); //$NON-NLS-1$
						mfOut = new MessageFormat(msgs.getString("UNITS_MISMATCH_ERR")); //$NON-NLS-1$
						rpt.append(mfOut.format(mfParms));
					}
					// check US units against the initial US units (all must match)
					if (unitsUS.equals(unitsUSTMP)) {
						if (attWEIGHT_US_SBB != null) {
							weightUS =
								weightUS.add(
									(
										new BigDecimal(
											attWEIGHT_US_SBB
                                        .toString()))
                                .multiply(
										qty));
						}
					} else {
						error = true;
						mfParms[0] =
							eiSBB.getEntityGroup().getLongDescription();
						mfParms[1] = eiSBB.getEntityGroup().getMetaAttribute("WEIGHT_USUNITS_SBB").getLongDescription(); //$NON-NLS-1$
						mfOut = new MessageFormat(msgs.getString("UNITS_MISMATCH_ERR")); //$NON-NLS-1$
						rpt.append(mfOut.format(mfParms));
					}
				} else if (
					attWEIGHT_METUNITS_SBB == null
                    && attWEIGHT_USUNITS_SBB == null
					&& attWEIGHT_METRIC_SBB == null
					&& attWEIGHT_US_SBB == null) {
					// nothing is ok
				} else {
					error = true;
					mfParms[0] = eiSBB.getEntityGroup().getLongDescription();
					mfOut = new MessageFormat(msgs.getString("UNITS_POP_ERR")); //$NON-NLS-1$
					rpt.append(mfOut.format(mfParms));
				}
			}

			if (!error) {
				if (unitsMetric != null) {
					// It's possible none of the ABB entities has populated weight attributes
					//Destination attributes:
					//DD.WEIGHT_US
					String usSum = weightUS.toString();
					//DD.WEIGHT_METRIC
					String metricSum = weightMetric.toString();
					if (metricSum.indexOf('.') < 0) {
						metricSum += ".0";
					} //$NON-NLS-1$
					setText(eiDD, "WEIGHT_METRIC", metricSum); //$NON-NLS-1$
					//DD.WEIGHT_METRICUNITS
					setFlagByCode(eiDD, "WEIGHT_METRICUNITS", unitsMetric); //$NON-NLS-1$
					if (usSum.indexOf('.') < 0) {
						usSum += ".0";
					}
					setText(eiDD, "WEIGHT_US", usSum); //$NON-NLS-1$
					//DD.WEIGHT_USUNITS
					setFlagByCode(eiDD, "WEIGHT_USUNITS", unitsUS); //$NON-NLS-1$
				} else {
					mfOut = new MessageFormat(msgs.getString("NO_DATA")); //$NON-NLS-1$
					rpt.append(mfOut.format(mfParms));
				}
			} else {
				setReturnCode(FAIL);
			}
		}

		//Rules:
		//1. Only applies to entityType VAR and OF when OF.OFFERINGTYPE = SYSTEM(0080)
		//2. Workflow should add the weight values on the SBBs linked to the OF or VAR to populate the cooresponding DD attribute.
		//3. A blank SBB weight attribute should be treated as a zero value.
		//4. If there are no SBBs linked to an OF, the workflow would not calculate any value, leaving the fields blank in the DD element.
		//5. If a calculated DD weight is a whole number, value should be entered with a decimal to the tenth (x.0).
		//6. The units attributes are not a summary, but should be based on the SBB units. Ex. DD.WEIGHT_USUNITS = SBB.WEIGHT_USUNITS_SBB

		//Things that would generate an error:
		//1. For the unit attributes, if SBBs have different unit attributes - this would be an error and no data would be populated. Ex. on VAR, there are 2 SBBs. SBB1 has WEIGHT_USUNITS_SBB = "lbs". SBB2 has WEIGHT_USUNITS_SBB = "Lbs" - this would be an error.
		//2. If SBB metric attributes are populated but the US attributes are not. And if the SBB US attributes are populated but the metric attributes are not.
	}

	private boolean setText(EntityItem ei, String attributeCode, String value)
		throws
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    COM.ibm.eannounce.objects.EANBusinessRuleException {
		boolean set = false;
		EANMetaAttribute mAttr =
			ei.getEntityGroup().getMetaAttribute(attributeCode);
		if (mAttr != null && mAttr instanceof MetaTextAttribute) {
			EANTextAttribute tAttr =
				new TextAttribute(ei, m_prof, (MetaTextAttribute) mAttr);
			tAttr.put(value);
			ei.putAttribute(tAttr);
			set = true;
			mfParms[0] = mAttr.getLongDescription();
			mfParms[1] = value;
			mfOut = new MessageFormat(msgs.getString("SET_MSG")); //$NON-NLS-1$
			rpt.append(mfOut.format(mfParms));
		} else {
			setReturnCode(FAIL);
			mfParms[0] = attributeCode;
			if (mAttr == null) {
				mfOut = new MessageFormat(msgs.getString("CODE_ERR")); //$NON-NLS-1$
				mfParms[1] = ei.getLongDescription();
			} else {
				mfOut = new MessageFormat(msgs.getString("TEXT_ERR")); //$NON-NLS-1$
			}
			rpt.append(mfOut.format(mfParms));
		}
		return set;
	}

	/**
	 * Find the Flag Code whose long description is lessthan or equal to desiredValue
	 * @param ei
	 * @param attributeCode
	 * @param desiredValue
	 * @return
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	 * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
	 */
	private boolean setFlagToClosestNumericalMatch(
		EntityItem ei,
		String attributeCode,
		BigDecimal desiredValue)
		throws
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    COM.ibm.eannounce.objects.EANBusinessRuleException {
		SortedMap possibleMatches = new TreeMap();
		EANMetaAttribute mAttr =
			ei.getEntityGroup().getMetaAttribute(attributeCode);
		if (mAttr != null && mAttr instanceof MetaSingleFlagAttribute) {
			EANFlagAttribute efa =
				new SingleFlagAttribute(
					ei,
					m_prof,
					(MetaSingleFlagAttribute) mAttr);
			MetaFlag[] mfa = (MetaFlag[]) efa.get();
			MetaFlag match;
			for (int i = 0; i < mfa.length; i++) {
				possibleMatches.put(
					new BigDecimal(mfa[i].getLongDescription()),
					mfa[i]);
			}
			match = (MetaFlag) possibleMatches.get(desiredValue);
			if (match == null) {
				// no exact match
				// If we put the desired value in with the others in sorted order
				// The one before it will be the closest match
				ArrayList al;
				int index;
				possibleMatches.put(desiredValue, desiredValue);
				// get the values as an array
				al = new ArrayList(possibleMatches.keySet());
				// index of the desired value minus 1 is the closest match
				index = al.indexOf(desiredValue) - 1;
				if (index < 0) {
					setReturnCode(FAIL);
					mfOut = new MessageFormat(msgs.getString("VALUE_ERR")); //$NON-NLS-1$
					mfParms[0] = desiredValue.toString();
					mfParms[1] = mAttr.getLongDescription();
					rpt.append(mfOut.format(mfParms));
					return false;
				}
				match = (MetaFlag) possibleMatches.get(al.get(index));
			}
			match.setSelected(true);
			efa.put(mfa);
			ei.putAttribute(efa);
			mfParms[0] = mAttr.getLongDescription();
			mfParms[1] = match.getLongDescription();
			mfOut = new MessageFormat(msgs.getString("SET_MSG")); //$NON-NLS-1$
			rpt.append(mfOut.format(mfParms));
			return true;
		}
		setReturnCode(FAIL);
		mfParms[0] = attributeCode;
		if (mAttr == null) {
			mfOut = new MessageFormat(msgs.getString("CODE_ERR")); //$NON-NLS-1$
			mfParms[1] = ei.getLongDescription();
		} else {
			mfOut = new MessageFormat(msgs.getString("FLAG_ERR")); //$NON-NLS-1$
		}
		rpt.append(mfOut.format(mfParms));
		return false;
	}

	private boolean setFlagByDescription(
		EntityItem ei,
		String attributeCode,
		String strDesc)
		throws
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    COM.ibm.eannounce.objects.EANBusinessRuleException {
		boolean set = false;
		EANMetaAttribute mAttr =
			ei.getEntityGroup().getMetaAttribute(attributeCode);
		if (mAttr != null && mAttr instanceof MetaSingleFlagAttribute) {
			EANFlagAttribute efa =
				new SingleFlagAttribute(
					ei,
					m_prof,
					(MetaSingleFlagAttribute) mAttr);
			MetaFlag[] mfa = (MetaFlag[]) efa.get();
			for (int i = 0; i < mfa.length; i++) {
				if (mfa[i].getLongDescription().equals(strDesc)) {
					set = true;
					mfa[i].setSelected(true);
					mfParms[0] = mAttr.getLongDescription();
					mfParms[1] = strDesc;
					mfOut = new MessageFormat(msgs.getString("SET_MSG")); //$NON-NLS-1$
					rpt.append(mfOut.format(mfParms));
				}
			}
			if (set) {
				efa.put(mfa);
				ei.putAttribute(efa);
			} else {
				setReturnCode(FAIL);
				mfOut = new MessageFormat(msgs.getString("VALUE_ERR")); //$NON-NLS-1$
				mfParms[0] = strDesc;
				mfParms[1] = mAttr.getLongDescription();
				rpt.append(mfOut.format(mfParms));
			}
		} else {
			setReturnCode(FAIL);
			mfParms[0] = attributeCode;
			if (mAttr == null) {
				mfOut = new MessageFormat(msgs.getString("CODE_ERR")); //$NON-NLS-1$
				mfParms[1] = ei.getLongDescription();
			} else {
				mfOut = new MessageFormat(msgs.getString("FLAG_ERR")); //$NON-NLS-1$
			}
			rpt.append(mfOut.format(mfParms));
		}
		return set;
	}

	private boolean setFlagByCode(
		EntityItem ei,
		String attributeCode,
		String strFlagCode)
		throws
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    COM.ibm.eannounce.objects.EANBusinessRuleException {
		boolean set = false;
		EANMetaAttribute mAttr =
			ei.getEntityGroup().getMetaAttribute(attributeCode);
		if (mAttr != null && mAttr instanceof MetaSingleFlagAttribute) {
			EANFlagAttribute efa =
				new SingleFlagAttribute(
					ei,
					m_prof,
					(MetaSingleFlagAttribute) mAttr);
			MetaFlag[] mfa = (MetaFlag[]) efa.get();
			for (int i = 0; i < mfa.length; i++) {
				if (mfa[i].getFlagCode().equals(strFlagCode)) {
					set = true;
					mfa[i].setSelected(true);
					mfParms[0] = mAttr.getLongDescription();
					mfParms[1] = mfa[i].getLongDescription();
					mfOut = new MessageFormat(msgs.getString("SET_MSG")); //$NON-NLS-1$
					rpt.append(mfOut.format(mfParms));
				}
			}
			if (set) {
				efa.put(mfa);
				ei.putAttribute(efa);
			} else {
				setReturnCode(FAIL);
				mfOut = new MessageFormat(msgs.getString("VALUE_ERR")); //$NON-NLS-1$
				mfParms[0] = strFlagCode;
				mfParms[1] = mAttr.getLongDescription();
				rpt.append(mfOut.format(mfParms));
			}
		} else {
			mfParms[0] = attributeCode;
			setReturnCode(FAIL);
			if (mAttr == null) {
				mfOut = new MessageFormat(msgs.getString("CODE_ERR")); //$NON-NLS-1$
				mfParms[1] = ei.getLongDescription();
			} else {
				mfOut = new MessageFormat(msgs.getString("FLAG_ERR")); //$NON-NLS-1$
			}
			rpt.append(mfOut.format(mfParms));
		}
		return set;
	}

	/**
	 * Method getSBBQTY counts each relator to OF or VAR as 1
	 * if related to SBB it adds in the SBB quantity if populated or 1 otherwize.
	 * @param ei EntityItem to check
	 * @return int Count of relators and SBB Quandity
	 */
	private int getSBBQTY(EntityItem ei) {
		int qty = 0;
		for (int i = 0; i < ei.getUpLinkCount(); i++) {
			EntityItem eiRelator = (EntityItem) ei.getUpLink(i);
			if (eiRelator.getUpLink(0).getEntityType().equals("SBB")) { //$NON-NLS-1$
				EntityItem eiSBB = (EntityItem) eiRelator.getUpLink(0);
				EntityItem eiXXXSBB = (EntityItem) eiSBB.getUpLink(0);
				// VARSBB or OFSBB
				EANAttribute eaaSBBQTY = eiXXXSBB.getAttribute(getRootEntityType() + "SBBQTY"); //$NON-NLS-1$
				if (eaaSBBQTY != null
					&& eaaSBBQTY instanceof EANTextAttribute) {
					qty += Integer.parseInt(eaaSBBQTY.get().toString());
				} else {
					qty += 1;
				}
			} else {
				qty += 1;
			}
		}
		return qty;
	}
	/**
	 *  Get ABR description
	 *
	 *@return    java.lang.String
	 */
	public String getDescription() {
		return msgs.getString("DESCRIPTION"); //$NON-NLS-1$
	}

	/**
	 *  Get the version
	 *
	 *@return    java.lang.String
	 */
	public String getABRVersion() {
		return "$Revision: 1.17 $"; //$NON-NLS-1$
	}
	private Locale getLocale(int nlsID) {
		Locale locale = null;
		switch (nlsID) {
		case 1 :
			locale = Locale.US;
		case 2 :
			locale = Locale.GERMAN;
			break;
		case 3 :
			locale = Locale.ITALIAN;
			break;
		case 4 :
			locale = Locale.JAPANESE;
			break;
		case 5 :
			locale = Locale.FRENCH;
			break;
		case 6 :
			locale = new Locale("es", "ES"); //$NON-NLS-2$  //$NON-NLS-1$
			break;
		case 7 :
			locale = Locale.UK;
			break;
		default :
			locale = Locale.US;
		}
		return locale;
	}
}
