package COM.ibm.eannounce.abr.sg.rfc;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.chw.entity.LifecycleData;
import com.ibm.rdh.chw.entity.TypeFeatureUPGGeo;
import com.ibm.transform.oim.eacm.util.PokUtils;

public class RFCFCTRANSACTIONABR extends RfcAbrAdapter {

	String SaleOrg_0147 = "0147";

	public RFCFCTRANSACTIONABR(RFCABRSTATUS rfcAbrStatus)
			throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException,
			EANBusinessRuleException, IOException, MiddlewareShutdownInProgressException {
		super(rfcAbrStatus);
	}

	@Override
	public void processThis() throws RfcAbrException, HWPIMSAbnormalException, Exception {
		abr.addDebug("Start processThis()");
		EntityList t1EntityList = null;
		boolean isResendFull = false;
		EntityItem fctransactionItem = null;
		try {
			// ------------------------------------------------------------------------------------------
			// There is new ABR RFCFCTRANSACTIONABR to handle feature upgrade promotion. In
			// this ABR,
			// we need to handle 'Change' and 'New' for FCTRANSACTION. We do not need to
			// handle 'Delete'
			// ----------------------- Get values from EACM entities -----------------------
			// FCTRANSACTION
			fctransactionItem = getRooEntityItem();
			// Check status
			if (!STATUS_FINAL.equals(getAttributeFlagValue(fctransactionItem, ATTR_STATUS))) {
				throw new RfcAbrException("The status is not final, it will not promote this FCTRANSACTION");
			}
			String sFROMMACHTYPE = getAttributeValue(fctransactionItem, "FROMMACHTYPE");
			String sFROMFEATURECODE = getAttributeValue(fctransactionItem, "FROMFEATURECODE");
			String sTOMACHTYPE = getAttributeValue(fctransactionItem, "TOMACHTYPE");
			String sTOFEATURECODE = getAttributeValue(fctransactionItem, "TOFEATURECODE");

			// step 1 TypeFeatureUPGGeo only need type
			TypeFeatureUPGGeo tfugObj = new TypeFeatureUPGGeo();
			// Type FCTRANSACTION Not support TOMACHTYPE
			tfugObj.setType(sTOMACHTYPE);
			tfugObj.setFromType(sFROMMACHTYPE);
			tfugObj.setFeature(sTOFEATURECODE);
			tfugObj.setFromFeature(sFROMFEATURECODE);
			tfugObj.setGeography("LA");// Geography ‘LA’ Yes US only

			// step2 CHWAnnouncement entity
			CHWAnnouncement chwA = new CHWAnnouncement();
			// AnnDocNo Set to FROMMACHTYPE|FROMFEATURECODE| TOMACHTYPE|TOFEATURECODE non
			// chwA.setAnnDocNo(sFROMMACHTYPE+SPLIT+sFROMFEATURECODE+SPLIT+sTOMACHTYPE+SPLIT+sTOFEATURECODE);
			//String uniqueid = SPLIT + getUniqueId(UniqueIdGenerator.TYPE_TRANSACTION);
			chwA.setAnnDocNo(
					sFROMMACHTYPE + SPLIT + sTOMACHTYPE);
			// AnnouncementTyp “NEW” Empty
			chwA.setAnnouncementType("NEW");

			// step 3 TYPE_FEATURE Upgrade Promote
			String pimsIdentity = "C";
			abr.addDebug("PimsIdentity: " + pimsIdentity);

			// ----------------------- Check Resend full -----------------------
			isResendFull = SYSFEEDRESEND_YES.equals(getAttributeFlagValue(fctransactionItem, ATTR_SYSFEEDRESEND));
			abr.addDebug("Resend full: " + isResendFull);

			// ----------------------- Check feature transaction promoted or changed
			// -----------------------
			// T1 & T2
			AttributeChangeHistoryGroup rfcAbrHistory = getAttributeHistory(fctransactionItem, RFCABRSTATUS);
			boolean isPassedAbrExist = existBefore(rfcAbrHistory, STATUS_PASSED);
			abr.addDebug("Is passed RFCABRSTATUS exist before: " + isPassedAbrExist);
			String t2AnnouncementDate = getAttributeValue(fctransactionItem, ANNDATE);

			EntityItem t1FCTRANSACTIONItem = null;
			boolean tfuGeoPromoted = false;
			boolean tfuGeoChanged = false;
			if (!isResendFull && isPassedAbrExist) {
				tfuGeoPromoted = true;
				String t1DTS = getLatestValFromForAttributeValue(rfcAbrHistory, STATUS_PASSED);
				if (t1DTS == null || "".equals(t1DTS)) { // not found passed value, all value thought change
					abr.addDebug("t1DTS is null");
				} else {
					String t2DTS = abr.getCurrentTime();
					Profile profileT1 = abr.switchRole(ROLE_CODE);
					profileT1.setValOnEffOn(t1DTS, t1DTS);
					profileT1.setEndOfDay(t2DTS);
					profileT1.setReadLanguage(Profile.ENGLISH_LANGUAGE); // default to US english
					profileT1.setLoginTime(t2DTS);
					abr.addDebug("Get t1 entity list for t1DTS: " + t1DTS + " t2DTS: " + t2DTS);
					t1EntityList = getEntityList(profileT1);
					abr.addDebug("EntityList for T1 " + profileT1.getValOn() + " extract " + getVeName()
							+ " contains the following entities: \n" + PokUtils.outputList(t1EntityList));
					t1FCTRANSACTIONItem = t1EntityList.getParentEntityGroup().getEntityItem(0);

					// isTMChanged = isTypeModelChanged(t1FCTRANSACTIONItem, fctransactionItem,
					// modelMarkChangedAttrs);
					// 1.2 To compare following attributes in the table, If any change, set value
					// true or false.
					// FCTRANSACTION.ANNDATE Lifecycle Announcement Set tfuGeoChanged=true
					String t1AnnouncementDate = getAttributeValue(t1FCTRANSACTIONItem, ANNDATE);
					tfuGeoChanged = istfuGeoChanged(t2AnnouncementDate, t1AnnouncementDate);
				}
			}

			// 1.3 Create 300 Classification for MK_FEAT_CONV For UPG [R162]
			rdhRestProxy.r162(tfugObj, null, "UPG", chwA, null, pimsIdentity);

			// 1.4 If tfuGeoPromoted = false || tfuGeoChanged =true
			if (!tfuGeoPromoted || tfuGeoChanged) {
				abr.addDebug("---------start  !tfuGeoPromoted || tfuGeoChanged-----------------");
				// 1.5 Update Ann lifecycle only for SaleOrg 0147 refer to 2.3.2
				CHWGeoAnn chwAg = new CHWGeoAnn();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				chwAg.setAnnouncementDate(sdf.parse(t2AnnouncementDate));
				abr.addDebug("CHWAnnouncementGEO: " + chwAg.toString());

				LifecycleDataGenerator lcdGen = new LifecycleDataGenerator(tfugObj);
				// this.updateAnnLifecyle(lcdGen.getVarCond(), lcdGen .getMaterial(), annDate,
				// annDocNo, chwA .getAnnouncementType(), pimsIdentity, salesOrg);
				this.updateAnnLifecyle(lcdGen.getVarCond(), lcdGen.getMaterial(), chwAg.getAnnouncementDate(),
						chwA.getAnnDocNo(), chwA.getAnnouncementType(), pimsIdentity, SaleOrg_0147);

				// 2 At the end of FCTRANSACTIONRFCABR ABR, need to update parking table
				// if (tfuGeoPromoted == false || tfuGeoChanged == true) {
				// rdhRestProxy.r144(chwA.getAnnDocNo(), "R", pimsIdentity);
				// abr.addDebug("Call R144 update parking table successfully");
				// }
				abr.addDebug("---------end !tfuGeoPromoted || tfuGeoChanged-----------------");
			}

			abr.addDebug(
					"----------------------- Start Promote WDFM Announcement for FCTRANSACTION Withdraw From Market -----------------------");
			boolean isTmwPromoted = false;
			boolean isTmwChanged = false;

			String t1wdfmdate = "";
			String t2wdfmdate = "";

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			t2wdfmdate = getAttributeValue(fctransactionItem, FCTRANSACTION_WTHDRWEFFCTVDATE);
			abr.addDebug("----------- FCTRANSACTION t2 WTHDRWEFFCTVDATE=" + t2wdfmdate);
			if (t2wdfmdate != null && !"".equals(t2wdfmdate)) {
				Date wdfmDate = sdf.parse(t2wdfmdate);
				// If wdfmDate is No Change, then set tfuwpromoted = true, tfuwChanged = false
				// If wdfmDate is ‘New’, then set tfuwpromoted = false.
				// If wdfmDate is ‘Change, then set tfuwChanged = true.
				if (t1FCTRANSACTIONItem == null) {
					t1wdfmdate = "";
				} else {
					t1wdfmdate = getAttributeValue(t1FCTRANSACTIONItem, FCTRANSACTION_WTHDRWEFFCTVDATE);
				}
				abr.addDebug("----------- FCTRANSACTION t1 WTHDRWEFFCTVDATE=" + t1wdfmdate);
				// new
				if (isResendFull || "".equals(t1wdfmdate)) {
					isTmwPromoted = false;
					isTmwChanged = false;
				} else if (t1wdfmdate.equals(t2wdfmdate)) {
					isTmwPromoted = true;
					isTmwChanged = false;
				} else {
					isTmwChanged = true;
				}

				/**
				 * If tfuwpromoted = false or tfuwChanged = true. updateWDFMLifecyle only for
				 * SaleOrg (0147) refer to 1.4.3 End if tfuwpromoted = false or tfuwChanged =
				 * true.
				 */
				abr.addDebug("isTmwPromoted: " + isTmwPromoted + " isTmwChanged: " + isTmwChanged);
				LifecycleDataGenerator lcdGen = new LifecycleDataGenerator(tfugObj);
				String annDocNo = chwA.getAnnDocNo();
				if (isTmwPromoted == false || isTmwChanged == true) {
					LifecycleData lcd = rdhRestProxy.r200(lcdGen.getMaterial(), lcdGen.getVarCond(), annDocNo, "wdfm",
							pimsIdentity, SaleOrg_0147);
					abr.addDebug("Call r200 successfully for SalesOrg 0147 " + lcd);
					updateWDFMLifecyle(lcd, lcdGen.getVarCond(), lcdGen.getMaterial(), wdfmDate, annDocNo, pimsIdentity,
							SaleOrg_0147);
					abr.addDebug("updateWDFMLifecyle successfully for SalesOrg " + SaleOrg_0147);
				}
				if (isTmwPromoted == false || isTmwChanged == true) {
					// rdhRestProxy.r144(chwA.getAnnDocNo() + "_WDFM", "R", pimsIdentity);
					// abr.addDebug("Call R144 update parking table successfully for WDFM");
				}
			}

			abr.addDebug(
					"----------------------- End Promote WDFM Announcement for FCTRANSACTION Withdraw From Market -----------------------");

			// r162 will insert record to parking table, so always call r144
			if (needReleaseParkingTable()) {
				rdhRestProxy.r144(chwA.getAnnDocNo(), "R", pimsIdentity);
				abr.addDebug("Call R144 update parking table successfully");
			} else {
				rdhRestProxy.r144(chwA.getAnnDocNo(), "H", pimsIdentity);
			}
		} finally {
			// Set SYSFEEDRESEND to No
			if (isResendFull && fctransactionItem != null) {
				setFlagValue(ATTR_SYSFEEDRESEND, SYSFEEDRESEND_NO, fctransactionItem);
			}
			// release memory
			if (t1EntityList != null) {
				t1EntityList.dereference();
				t1EntityList = null;
			}
			if (entityList != null) {
				entityList.dereference();
				entityList = null;
			}
			abr.addDebug("End processThis()");
		}
	}

	private boolean istfuGeoChanged(String t2AnnouncementDate, String t1AnnouncementDate) {
		boolean tfuGeoChanged = false;
		if (t2AnnouncementDate == null) {
			if (t1AnnouncementDate == null) {
				tfuGeoChanged = false;
			} else {
				tfuGeoChanged = true;
			}
		} else {
			if (t1AnnouncementDate == null) {
				tfuGeoChanged = true;
			} else {
				if (t1AnnouncementDate.equals(t2AnnouncementDate)) {
					tfuGeoChanged = false;
				} else {
					tfuGeoChanged = true;
				}
			}
		}
		return tfuGeoChanged;
	}

	public String getVeName() {
		return "RFCFCTRANSACTIONVE";
	}

}
