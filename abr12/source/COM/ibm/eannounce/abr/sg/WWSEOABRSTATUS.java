// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
WWSEOABRSTATUS_class=COM.ibm.eannounce.abr.sg.WWSEOABRSTATUS
WWSEOABRSTATUS_enabled=true
WWSEOABRSTATUS_idler_class=A
WWSEOABRSTATUS_keepfile=true
WWSEOABRSTATUS_report_type=DGTYPE01
WWSEOABRSTATUS_vename=EXRPT3WWSEO1
WWSEOABRSTATUS_CAT1=RPTCLASS.WWSEOABRSTATUS
WWSEOABRSTATUS_CAT2=
WWSEOABRSTATUS_CAT3=RPTSTATUS
WWSEOABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390


 *
 * WWSEOABRSTATUS.java,v
 * Revision 1.42  2009/07/30 18:41:27  wendy
 * Moved BH DQ ABRs to diff pkg
 *
 * Revision 1.40  2008/10/16 18:21:11  wendy
 * CQ00012177-RQ more updates - support Services that are Special Bids
 *
 * Revision 1.39  2008/10/14 18:35:02  wendy
 * CQ00012177-RQ updates - support Services that are Special Bids
 *
 * Revision 1.38  2008/09/22 15:31:57  wendy
 * CQ00006066-WI updates
 *
 * Revision 1.37  2008/04/24 21:48:26  wendy
 * "SG FS ABR Data Quality 20080422.doc" spec updates
 *
 * Revision 1.36  2008/04/23 18:30:31  wendy
 * chg MECHPKG chk from >1 to >0
 *
 * Revision 1.35  2008/04/16 19:48:45  wendy
 * changed MECHPKG count check
 *
 * Revision 1.34  2008/03/10 00:12:12  wendy
 * Updates for spec "SG FS ABR Data Quality 20080308b.doc"
 *
 * Revision 1.33  2008/03/04 15:59:54  wendy
 * SG FS ABR Data Quality 20080304.doc spec change
 *
 * Revision 1.32  2008/02/13 19:58:50  wendy
 * Make ABRWAITODSx into constants, allow easier change in future
 *
 * Revision 1.31  2008/01/21 17:26:55  wendy
 * Default null status to final
 *
 * Revision 1.30  2008/01/20 23:53:22  wendy
 * Updates for 'SG FS ABR Data Quality 20080118.doc'
 *
 * Revision 1.29  2007/11/27 21:45:34  wendy
 * SG FS ABR Data Quality 20071127.doc chgs
 *
 * Revision 1.28  2007/11/16 21:20:01  nancy
 * Spec chg "SG FS ABR Data Quality 20071115.doc"
 *
 * Revision 1.27  2007/10/29 20:50:07  wendy
 * Added check for ELEMENTTYPE
 *
 * Revision 1.26  2007/10/25 21:19:18  wendy
 * Added comments
 *
 * Revision 1.25  2007/10/23 17:47:12  wendy
 * Spec changes
 *
 * Revision 1.24  2007/09/14 17:43:55  wendy
 * Updated for GX
 *
 * Revision 1.23  2007/08/17 16:02:10  wendy
 * RQ0713072645 - support for converged products. Another PDHDOMAIN was added.
 * from 'SG FS xSeries ABRs 20070803.doc'
 *
 * Revision 1.22  2006/06/23 14:59:15  joan
 * add debug
 *
 * Revision 1.21  2006/06/23 14:50:07  joan
 * testing add debug
 *
 * Revision 1.20  2006/06/01 20:30:53  wendy
 * Add dereference of 'alist'
 *
 * Revision 1.19  2006/05/26 19:24:06  wendy
 * Attempt to handle race condition
 *
 * Revision 1.18  2006/03/16 18:24:21  anhtuan
 * Fix null pointer exception.
 *
 * Revision 1.17  2006/03/15 17:05:46  anhtuan
 * TIR USRO-R-LBAR-6MVNBR.
 *
 * Revision 1.16  2006/03/14 05:01:12  anhtuan
 * Fix Jtest. Jtest does not allow multiple returns from a method.
 *
 * Revision 1.15  2006/03/10 17:08:33  anhtuan
 * Updated Specs 30b FS xSeries ABRs 20060309.doc.
 *
 * Revision 1.14  2006/02/28 19:13:20  anhtuan
 * Updated specs for CR0130064137. New attributes: BAYAVAILTYPE, ACCSS, BAYFF.
 *
 * Revision 1.13  2006/02/25 21:23:59  anhtuan
 * Remove redundant stuff.
 *
 * Revision 1.12  2006/02/23 03:33:53  anhtuan
 * AHE compliant.
 *
 * Revision 1.11  2006/02/22 04:00:59  anhtuan
 * Use PokUtils.
 *
 * Revision 1.10  2006/01/27 21:43:26  anhtuan
 * Updated specs.
 *
 * Revision 1.9  2006/01/27 16:29:41  anhtuan
 * Check for null values of DATAQUALITY and/or STATUS.
 *
 * Revision 1.8  2006/01/26 15:03:24  anhtuan
 * AHE copyright.
 *
 * Revision 1.7  2006/01/26 02:00:06  anhtuan
 * Updated specs.
 *
 * Revision 1.5  2005/08/23 16:28:40  anhtuan
 * Data model changes.
 *
 * Revision 1.4  2005/08/17 23:59:32  anhtuan
 * Fix resource file problem.
 *
 * Revision 1.3  2005/08/04 23:24:34  anhtuan
 * Use resource file.
 *
 * Revision 1.2  2005/07/25 17:45:55  anhtuan
 * Use logical & instead of conditional &&.
 *
 * Revision 1.1  2005/07/19 16:19:33  anhtuan
 * Initial version.
 *
 *
 *@author     Anhtuan Nguyen
 *@created    July 1, 2005
 */
package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;

import java.util.*;

/**********************************************************************************
* WWSEOABRSTATUS class
* SG FS ABR Data Quality 20081016.doc
*
*/
public class WWSEOABRSTATUS extends DQABRSTATUS
{
	private Object[] args = new String[3];
	private EntityList mdlList;

	/**********************************
	* must be xseries or convergedproduct
	*/
	protected boolean isVEneeded(String statusFlag) {
		return true;
	}

    /**********************************
    * complete abr processing after status moved to readyForReview; (status was chgreq)
    *C.  STATUS changed to Ready for Review
    *
    *1.	PropagateOStoWWSEO
	*2.	Set COMPATGENABR = 0020 (Queued)
    */
    protected void completeNowR4RProcessing()throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
		if (mdlList==null){
			try{
				// get VE2 to go from WWSEO-MODELWWSEO-MODEL and other MODEL links
				getModelVE(m_elist.getParentEntityGroup().getEntityItem(0));
			}catch(Exception e){
				e.printStackTrace();
				addDebug("Exception getting model ve "+e.getMessage());
				throw new MiddlewareException(e.getMessage());
			}
		}
       	propagateOStoWWSEO(mdlList.getEntityGroup("MODEL").getEntityItem(0),m_elist.getParentEntityGroup());
		setFlagValue(m_elist.getProfile(),"COMPATGENABR", ABR_QUEUED);
		mdlList.dereference(); // not needed any more
    }

	/**********************************
	* complete abr processing after status moved to final; (status was r4r)
	*D.	STATUS changed to Final
	*
	*1.	PropagateOStoWWSEO
	*2.	Set COMPATGENABR = 0020 (Queued)
	*3.	Set EPIMSABRSTATUS =  ABRWAITODS (Wait for ODS Data)
	*chgd4.	IF (WWSEOLSEO-d: LSEO.STATUS) = 0020 (Final) THEN (WWSEOLSEO-d: LSEO.EPIMSABRSTATUS) = 0020 (Queued)
	*4.	IF (WWSEOLSEO-d: LSEO.STATUS) = 0020 (Final) THEN (WWSEOLSEO-d: LSEO.LSEOABRSTATUS) = 0020 (Queued) CQ00006066-WI
	*/
	protected void completeNowFinalProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		if (mdlList==null){
			try{
				// get VE2 to go from WWSEO-MODELWWSEO-MODEL and other MODEL links
				getModelVE(m_elist.getParentEntityGroup().getEntityItem(0));
			}catch(Exception e){
				e.printStackTrace();
				addDebug("Exception getting model ve "+e.getMessage());
				throw new MiddlewareException(e.getMessage());
			}
		}
        propagateOStoWWSEO(mdlList.getEntityGroup("MODEL").getEntityItem(0),m_elist.getParentEntityGroup());
        setFlagValue(m_elist.getProfile(),"COMPATGENABR", ABR_QUEUED);
        setFlagValue(m_elist.getProfile(),"EPIMSABRSTATUS", ABRWAITODS);

		EntityGroup eg = m_elist.getEntityGroup("LSEO");
		for (int i=0; i<eg.getEntityItemCount(); i++){
			EntityItem item = eg.getEntityItem(i);
			String status = getAttributeFlagEnabledValue(item, "STATUS");
			addDebug(item.getKey()+" status "+status);
            if(status == null || status.length()==0) {
                status = STATUS_FINAL;
            }
			if (STATUS_FINAL.equals(status)){
				//setFlagValue(m_elist.getProfile(),"EPIMSABRSTATUS", ABR_QUEUED,item);
				//CQ00006066-WI
				setFlagValue(m_elist.getProfile(),"LSEOABRSTATUS", ABR_QUEUED,item);
			}
		}
		mdlList.dereference(); // not needed any more
	}

	/**********************************
	* Note the ABR is only called when
	* DATAQUALITY transitions from 'Draft to Ready for Review',
	*	'Change Request to Ready for Review' and from 'Ready for Review to Final'
	*A.	STATUS = Draft | Change Request
	*
	*1.	CountOf(MODELWWSEO-u: MODEL) = 1
	*	ErrorMessage  "must have only one parent" LD(MODEL)
	*2.	IF ValueOf(WWSEO.PDHDOMAIN = 0050 (xSERIES) THEN
	*	a.	IF ValueOf(MODELWWSEO-u: MODEL.COFCAT) = 100 (Hardware) & ValueOf(MODELWWSEO-u: MODEL.COFSUBCAT) = 126 (System) THEN
	*		i.	CountOf(WWSEODERIVEDDATA-d: DERIVEDDATA) = 1
	*		ErrorMessage "must have exactly one" LD(DERIVEDDATE)
	*		ii.	CountOf(SEOCGSEO-u: SEOCG) = 1
	*		ErrorMessage "must be in exactly one" LD(SEOCG)
	*		iii.	((WWSEOPRODSTRUCT. CONFQTY) * (WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREPLANAR.QTY) *  CountOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREPLANAR: PLANAR)) + ((MODELWWSEO.u: MODELPLANAR.QTY) * CountOf(MODELWWSEO-u: MODELPLANAR-d: PLANAR)) = 1
	*		ErrorMessage "must have exactly one" LD(PLANAR)
	*		iv.	((WWSEOPRODSTRUCT. CONFQTY) * (WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREMECHPKG.QTY) *  CountOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREMECHPKG: MECHPKG))+ ((MODELWWSEO.u: MODELMECHPKG.QTY) * CountOf(MODELMECHPKG-u: MODELMECHPKG-d: MECHPKG)) => 1
	*		ErrorMessage "must have exactly one" LD(MECHPKG)
	*		v.	Comment this check out due to the dependent RQ not moving to production
	*		CountOf(WWSEOWEIGHTNDIMN-d: WEIGHTNDIMN) = 1
	*		ErrorMessage "must have exactly one" LD(WEIGHTNDIMN)
	*
	*	b.	IF ValueOf(MODELWWSEO-u: MODEL.COFCAT) = 101 (Software) THEN CountOf(WWSEOSWPRODSTRUCT-d. SWPRODSTRUCT) => 1
	*	ErrorMessage "must have at least one" LD(SWPRODSTRUCT) LD(SWFEATURE)
	*	c.	IF ValueOf(MODELWWSEO-u: MODEL.COFCAT) = 102 (Service) THEN
	*		i.	If ValueOf(MODELWWSEO-u: MODEL.SPECBID)  is not empty THEN
	*			ValueOf(MODELWWSEO-u: MODEL.SPECBID) = ValueOf(WWSEO.SPECBID)
	*			ErrorMessage 'special bid indicator does not match the Model'
	*		ii.	CountOf(SEOGCSEO-u: SEOCG) = 0
	*			ErrorMessage 'a Service is in LD(SEOCG)
	*
	*B.	STATUS = Ready for Review
	*
	*1.	All checks in section A above
	*2.	IF ValueOf(WWSEO.PDHDOMAIN = 0050 (xSERIES) THEN
	*a.	IF ValueOf(MODELWWSEO-u: MODEL.COFCAT) = 100 (Hardware) & ValueOf(MODELWWSEO-u: MODEL.COFSUBCAT) = 126 (System) THEN
	*	i.	CountOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREMECHPKG-d: MECHPKGBAY-d: BAY) => 0
	*	ErrorMessage 'must have at least one' LD(BAY)
	*	ii.	(WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREMECHPKG-d: MECHPKGBAY-d: BAY) UNIQUE (BAYTYPE & ACCSS & BAYFF)
	*	ErrorMessage 'must have unique' LD(BAY) (BAY.BAYTYPE & ACCSS & BAYFF)
	*	iii.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREMECHPKG-d: MECHPKGBAY-d: BAY UNIQUE (BAYTYPE & ACCSS & BAYFF) IN (WWSEOBAYSAVAIL-d: BAYSAVAIL-d. BAYAVAILTYPE & ACCSS & BAYFF)
	*	ErrorMessage 'each BAY Type must have a corresponding' LD(BAYSAVAIL) (BAY.BAYTYPE & ACCSS & BAYFF)
	*	iv.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREMEMORYCARD-d: MEMORYCARDSLOT-d: SLOT.SLOTTYPE) IN (WWSEOSLOTSAVAIL-d: SLOTSAVAIL. SLOTTYPE)
	*	ErrorMessage 'each' LD(MEMORYCARD) LD(SLOT) 'must have a corresponding' LD(SLOTSAVAIL) LD(SLOTTYPE)
	*	v.	(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREMEMORYCARD-d: MEMORYCARDSLOT-d: SLOT.SLOTTYPE) UNIQUE (SLOTTYPE & SLOTSZE)
	*	ErrorMessage 'must have unique' LD(SLOT) (SLOT.SLOTTYPE & SLOTSZE)
	*	vi.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREPLANAR-d: PLANARSLOT-d: SLOT.SLOTTYPE) IN (WWSEOSLOTSAVAIL-d: SLOTSAVAIL.SLOTTYPE)
	*	ErrorMessage 'each' LD(PLANAR) LD(SLOT) 'must have a corresponding' LD(SLOTSAVAIL) LD(SLOTTYPE)
	*	vii.	(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREPLANAR-d: PLANARSLOT-d: SLOT.SLOTTYPE) IN (WWSEOSLOTSAVAIL-d: SLOTSAVAIL.SLOTTYPE) UNIQUE (SLOTTYPE & SLOTSZE)
	*	ErrorMessage 'must have unique' LD(SLOT) (SLOT.SLOTTYPE & SLOTSZE)
	*	viii.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREEXPDUNIT-d: EXPDUNITSLOT-d: SLOT.SLOTTYPE) IN (WWSEOSLOTSAVAIL-d: SLOTSAVAIL.SLOTTYPE)
	*	ErrorMessage 'each' LD(EXPDUNIT) LD(SLOT) 'must have a corresponding' LD(SLOTSAVAIL) LD(SLOTTYPE)
	*	ix.	(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREEXPDUNIT-d: EXPDUNITSLOT-d: SLOT.SLOTTYPE) UNIQUE (SLOTTYPE & SLOTSZE)
	*	ErrorMessage 'must have unique' LD(SLOT) (SLOT.SLOTTYPE & SLOTSZE)
	*3.	IF ValueOf(MODELWWSEO-u: MODEL.COFCAT) = 100 (Hardware) THEN IF CountOf(WWSEOWARR-d: WARR) = 0 THEN CountOf(MODELWWSEO-u: MODELWARR-d: WARR) > 0
	*ErrorMessage 'must have at least one' LD(WARR)
	*4.	AllValuesOf(WWSEOPRODSTRUCT-d:PRODSTRUCT. WITHDRAWDATE) > NOW()
	*ErrorMessage 'references a withdrawn' LD(PRODSTRUCT) NDN(PRODSTRUCT)
	*5.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL:AVAIL.EFFECTIVEDATE > NOW() WHERE AVAIL.AVAILTYPE = 149 (Last Order).
	*ErrorMessage 'references a withdrawn' LD(PRODSTRUCT) NDN(PRODSTRUCT)
	*6.	AllValuesOf(WWSEOSWPRODSTRUCT-d: SWPRODSTRUCT: SWPRODSTRUCTAVAIL:AVAIL.EFFECTIVEDATE > NOW() WHERE AVAIL.AVAILTYPE = 149 (Last Order).
	*ErrorMessage 'references a withdrawn' LD(SWPRODSTRUCT) NDN(SWPRODSTRUCT)
	*7.	AllValuesOf(WWSEOSWPRODSTRUCT-d: SWPRODSTRUCT-u: SWFEATURE. WITHDRAWDATEEFF_T > NOW()
	*ErrorMessage 'references a withdrawn' LD(SWFEATURE) NDN(SWPRODSTRUCT)
	*8.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATURE. WITHDRAWDATEEFF_T > NOW()
	*ErrorMessage 'references a withdrawn' LD(FEATURE) NDN(PRODSTRUCT)
	*9.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT.STATUS) = 0020 (Final) OR Empty
	*ErrorMessage NDN(PRODSTRUCT) 'is not Final'.
	*10.	AllValuesOf(WWSEOSWPRODSTRUCT-d: SWPRODSTRUCT.STATUS) = 0020 (Final) OR Empty
	*ErrorMessage NDN(SWPRODSTRUCT) 'is not Final'.
	*
	*/
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
		// get VE2 to go from WWSEO-MODELWWSEO-MODEL and other MODEL links
		getModelVE(rootEntity);

		//Always check these rules, not dependent on status
		String wwseoSPECBID = getAttributeFlagEnabledValue(rootEntity, "SPECBID");

		EntityGroup mdlGrp = mdlList.getEntityGroup("MODEL");
		//1.  CountOf(MODELWWSEO-u: MODEL) = 1
		//ErrorMessage  'must have only one parent' LD(MODEL)
		EntityGroup relGrp = mdlList.getEntityGroup("MODELWWSEO");
		if (relGrp.getEntityItemCount()!=1){
			//REQUIRES_ONE_PARENT_ERR = must have only one parent {0}
			Object args[] = new Object[]{mdlGrp.getLongDescription()};
			addError("REQUIRES_ONE_PARENT_ERR",args);
		}else{
			EntityItem modelItem = mdlGrp.getEntityItem(0);//get this from VE2 MODELWWSEO-u: MODEL
			String modelCOFCAT = getAttributeFlagEnabledValue(modelItem, "COFCAT");
			String modelCOFSUBCAT = getAttributeFlagEnabledValue(modelItem, "COFSUBCAT");
			addDebug(modelItem.getKey()+" COFCAT: "+modelCOFCAT+" COFSUBCAT: "+modelCOFSUBCAT);

			//2.	IF ValueOf(WWSEO.PDHDOMAIN = 0050 (xSERIES) THEN
            if(PokUtils.isSelected(rootEntity, "PDHDOMAIN", "0050")){
				//a.	IF ValueOf(MODELWWSEO-u: MODEL.COFCAT) = 100 (Hardware) &
				//ValueOf(MODELWWSEO-u: MODEL.COFSUBCAT) = 126 (System) THEN
				if(HARDWARE.equals(modelCOFCAT)&& SYSTEM.equals(modelCOFSUBCAT)){
					//i.	CountOf(WWSEODERIVEDDATA-d: DERIVEDDATA) = 1
					//ErrorMessage 'must have exactly one' LD(DERIVEDDATE)
					int cnt = getCount("WWSEODERIVEDDATA");
					if(cnt != 1)	{
						EntityGroup eGrp = m_elist.getEntityGroup("DERIVEDDATA");
						//NEED_ONLY_ONE_ERR = must have exactly one {0}
						Object args[] = new Object[]{eGrp.getLongDescription()};
						addError("NEED_ONLY_ONE_ERR",args);
					}
					//ii.	CountOf(SEOCGSEO-u: SEOCG) = 1
					//ErrorMessage 'must be in exactly one' LD(SEOCG)
					cnt = getCount("SEOCGSEO");
					if(cnt != 1)	{
						EntityGroup eGrp = m_elist.getEntityGroup("SEOCG");
						//MUST_BE_IN_ONE_ERR = must be in exactly one {0}
						Object args[] = new Object[]{eGrp.getLongDescription()};
						addError("MUST_BE_IN_ONE_ERR",args);
					}

					//iii.	((WWSEOPRODSTRUCT. CONFQTY) * (WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREPLANAR.QTY) *
					// CountOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREPLANAR: PLANAR)) + ((MODELWWSEO.u: MODELPLANAR.QTY) *
					// CountOf(MODELWWSEO-u: MODELPLANAR-d: PLANAR)) = 1
					//ErrorMessage 'must have exactly one' LD(PLANAR)
					checkCount("FEATUREPLANAR", "MODELPLANAR", "PLANAR");

					//iv.	((WWSEOPRODSTRUCT. CONFQTY) * (WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREMECHPKG.QTY) *
					// CountOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREPLANAR: PLANAR)) + ((MODELWWSEO.u: MODELMECHPKG.QTY) *
					// now CountOf(MODELMECHPKG-u: MODELMECHPKG-d: MECHPKG)) => 1
					//ErrorMessage 'must have at least one' LD(MECHPKG)
					checkExists("FEATUREMECHPKG", "MODELMECHPKG", "MECHPKG");

					//v. CountOf(WWSEOPRODSTRUCT-d. PRODSTRUCT => 1
					//ErrorMessage 'must have at least one' LD(PRODSTRUCT) LD(FEATURE)
					cnt = getCount("WWSEOPRODSTRUCT");
					if(cnt == 0)	{
						EntityGroup eGrp = m_elist.getEntityGroup("PRODSTRUCT");
						EntityGroup fGrp = m_elist.getEntityGroup("FEATURE");
						//MINIMUM_ERR = must have at least one {0}
						Object args[] = new Object[]{eGrp.getLongDescription()+" "+
							fGrp.getLongDescription()};
						addError("MINIMUM_ERR",args);
					}
					//vi.CountOf(WWSEOWEIGHTNDIMN-d: WEIGHTNDIMN) = 1
					//ErrorMessage 'must have exactly one' LD(WEIGHTNDIMN)
					/* hold off on this check for now - "SG FS ABR Data Quality 20080304.doc"
					cnt = getCount("WWSEOWEIGHTNDIMN");
					if(cnt != 1)	{
						EntityGroup eGrp = m_elist.getEntityGroup("WEIGHTNDIMN");
						//NEED_ONLY_ONE_ERR = must have exactly one {0}
						Object args[] = new Object[]{eGrp.getLongDescription()};
						addError("NEED_ONLY_ONE_ERR",args);
					}
					*/
				} // end hardware and system
				if(SOFTWARE.equals(modelCOFCAT)) {
					//b.	IF ValueOf(MODELWWSEO-u: MODEL.COFCAT) = 101 (Software) THEN CountOf(WWSEOSWPRODSTRUCT-d. SWPRODSTRUCT) => 1
					//ErrorMessage 'must have at least one' LD(SWPRODSTRUCT) LD(SWFEATURE)
					int cnt = getCount("WWSEOSWPRODSTRUCT");
					if(cnt == 0)	{
						EntityGroup eGrp = m_elist.getEntityGroup("SWPRODSTRUCT");
						EntityGroup fGrp = m_elist.getEntityGroup("SWFEATURE");
						//MINIMUM_ERR = must have at least one {0}
						Object args[] = new Object[]{eGrp.getLongDescription()+" "+
							fGrp.getLongDescription()};
						addError("MINIMUM_ERR",args);
					}
				}

				if(SERVICE.equals(modelCOFCAT)) {
					//c.IF ValueOf(MODELWWSEO-u: MODEL.COFCAT) = 102 (Service) THEN
					//	i.	If ValueOf(MODELWWSEO-u: MODEL.SPECBID)  is not empty THEN
					//		ValueOf(MODELWWSEO-u: MODEL.SPECBID) = ValueOf(WWSEO.SPECBID)
					//		ErrorMessage 'special bid indicator does not match the Model'
					String modelSPECBID = getAttributeFlagEnabledValue(modelItem, "SPECBID");
					addDebug(modelItem.getKey()+" SPECBID: "+modelSPECBID+" "+rootEntity.getKey()+
							" wwseoSPECBID "+wwseoSPECBID);
					if (modelSPECBID != null && !modelSPECBID.equals(wwseoSPECBID)){
						//NO_SPECBID_MATCH = special bid indicator does not match the Model.
						addError("NO_SPECBID_MATCH",null);
					}

					//	ii.	CountOf(SEOCGSEO-u: SEOCG) = 0
					//		ErrorMessage 'a Service is in LD(SEOCG)
					int cnt = getCount("SEOCGSEO");
					if(cnt != 0)	{
						EntityGroup eGrp = m_elist.getEntityGroup("SEOCG");
						//SVC_SEOCG_ERR = is a Service and must not be in a {0}
						Object args[] = new Object[]{eGrp.getLongDescription()};
						addError("SVC_SEOCG_ERR",args);
					}
				}
			} // end xseries domain

			if(STATUS_R4REVIEW.equals(statusFlag)) { // 'Ready for Review to Final' rule
                String strNow = m_db.getDates().getNow().substring(0, 10);
				//2.	IF ValueOf(WWSEO.PDHDOMAIN = 0050 (xSERIES) THEN
				if(PokUtils.isSelected(rootEntity, "PDHDOMAIN", "0050")){
					//a.	IF ValueOf(MODELWWSEO-u: MODEL.COFCAT) = 100 (Hardware) &
					//ValueOf(MODELWWSEO-u: MODEL.COFSUBCAT) = 126 (System) THEN
					if(HARDWARE.equals(modelCOFCAT)&& SYSTEM.equals(modelCOFSUBCAT)){
						//i.	 (WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREMECHPKG-d: MECHPKGBAY-d: BAY) UNIQUE (BAYTYPE & ACCSS & BAYFF)
						//ErrorMessage 'must have unique' LD(BAY) (BAY.BAYTYPE & ACCSS & BAYFF)
						//ii.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREMECHPKG-d: MECHPKGBAY-d: BAY UNIQUE (BAYTYPE & ACCSS & BAYFF) IN (WWSEOBAYSAVAIL-d: BAYSAVAIL-d. BAYAVAILTYPE & ACCSS & BAYFF)
						//ErrorMessage 'each BAY Type must have a corresponding' LD(BAYSAVAIL) (BAY.BAYTYPE & ACCSS & BAYFF)
						//iii.	AllValuesOf(WWSEOBAYSAVAIL-d: BAYSAVAIL-d. BAYAVAILTYPE & ACCSS & BAYFF) IN (WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREMECHPKG-d: MECHPKGBAY-d: BAY UNIQUE (BAYTYPE & ACCSS & BAYFF)
						//ErrorMessage 'each BAYSAVAIL Type must have a corresponding' LD(BAYS) (BAYSAVAILTYPE.BAYAVAILTYPE & ACCSS & BAYFF)
						checkBays();

						//iv.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREMEMORYCARD-d: MEMORYCARDSLOT-d: SLOT.SLOTTYPE) IN (WWSEOSLOTSAVAIL-d: SLOTSAVAIL. SLOTTYPE) where SLOTSAVAIL. ELEMENTTYPE = '0010' (Memory Card)
						//ErrorMessage 'each' LD(MEMORYCARD) LD(SLOT) 'must have a corresponding' LD(SLOTSAVAIL) LD(SLOTTYPE)
						//v.	AllValuesOf(WWSEOSLOTSAVAIL-d: SLOTSAVAIL. SLOTTYPE) where SLOTSAVAIL. ELEMENTTYPE = '0010' (Memory Card) IN (WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREMEMORYCARD-d: MEMORYCARDSLOT-d: SLOT.SLOTTYPE)
						//ErrorMessage "Each" LD(SLOTSAVAIL) LD(SLOTTYPE) 'must have a corresponding' LD(MEMORYCARD) LD(SLOT)
						//vi.	(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREMEMORYCARD-d: MEMORYCARDSLOT-d: SLOT.SLOTTYPE) UNIQUE (SLOTTYPE & SLOTSZE)
						//ErrorMessage ''must have unique' LD(SLOT) (SLOT.SLOTTYPE & SLOTSZE)

						//vii.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREPLANAR-d: PLANARSLOT-d: SLOT.SLOTTYPE) IN (WWSEOSLOTSAVAIL-d: SLOTSAVAIL.SLOTTYPE) where SLOTSAVAIL. ELEMENTTYPE = '0020' (Planar)
						//ErrorMessage 'each' LD(PLANAR) LD(SLOT) 'must have a corresponding' LD(SLOTSAVAIL) LD(SLOTTYPE)
						//viii.	(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREPLANAR-d: PLANARSLOT-d: SLOT.SLOTTYPE) IN (WWSEOSLOTSAVAIL-d: SLOTSAVAIL.SLOTTYPE) UNIQUE (SLOTTYPE & SLOTSZE)
						//ErrorMessage ''must have unique' LD(SLOT) (SLOT.SLOTTYPE & SLOTSZE)

						//ix.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREEXPDUNIT-d: EXPDUNITSLOT-d: SLOT.SLOTTYPE) IN (WWSEOSLOTSAVAIL-d: SLOTSAVAIL.SLOTTYPE) where SLOTSAVAIL. ELEMENTTYPE = '0030' (Expansion Unit)
						//ErrorMessage 'each' LD(EXPDUNIT) LD(SLOT) 'must have a corresponding' LD(SLOTSAVAIL) LD(SLOTTYPE)
						//x.	(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREEXPDUNIT-d: EXPDUNITSLOT-d: SLOT.SLOTTYPE) UNIQUE (SLOTTYPE & SLOTSZE)
						//ErrorMessage ''must have unique' LD(SLOT) (SLOT.SLOTTYPE & SLOTSZE)
						checkSlots();
					} // end hw and system
				} // end xseries
				//3.	IF ValueOf(MODELWWSEO-u: MODEL.COFCAT) = 100 (Hardware) THEN
				//IF CountOf(WWSEOWARR-d: WARR) = 0 THEN CountOf(MODELWWSEO-u: MODELWARR-d: WARR) > 0
				//ErrorMessage 'must have at least one' LD(WARR)
				if(HARDWARE.equals(modelCOFCAT)){
					int cnt = getCount("WWSEOWARR");
					if(cnt == 0) {
						cnt = getCount(mdlList,"MODELWARR");
						if(cnt == 0) {
							EntityGroup eGrp = m_elist.getEntityGroup("WARR");
							//MINIMUM_ERR = must have at least one {0}
							Object args[] = new Object[]{eGrp.getLongDescription()};
							addError("MINIMUM_ERR",args);
						}
					}
				}
				//4.	AllValuesOf(WWSEOPRODSTRUCT-d:PRODSTRUCT. WITHDRAWDATE) > NOW()
				//ErrorMessage 'references a withdrawn' LD(PRODSTRUCT) NDN(PRODSTRUCT)
				//WITHDRAWN_ERR = references a withdrawn {0} {1}
				Vector psVector = PokUtils.getAllLinkedEntities(rootEntity, "WWSEOPRODSTRUCT", "PRODSTRUCT");
                for (int i=0; i<psVector.size(); i++){
					EntityItem psitem = (EntityItem)psVector.elementAt(i);
					String wdDate = PokUtils.getAttributeValue(psitem, "WITHDRAWDATE",", ", "", false);
					addDebug(psitem.getKey()+" WITHDRAWDATE: "+wdDate+" strNow: "+strNow);
					if (wdDate.length()>0 && wdDate.compareTo(strNow)<=0){
						args[0] = psitem.getEntityGroup().getLongDescription();
						args[1] = getNavigationName(psitem);
						addError("WITHDRAWN_ERR",args);
					}
				}
				psVector.clear();


				//5.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL:AVAIL.EFFECTIVEDATE > NOW() WHERE AVAIL.AVAILTYPE = 149 (Last Order).
				//ErrorMessage 'references a withdrawn' LD(PRODSTRUCT) NDN(PRODSTRUCT)
				checkWDAvails(rootEntity,"WWSEOPRODSTRUCT","PRODSTRUCT","OOFAVAIL",strNow);

				//6.	AllValuesOf(WWSEOSWPRODSTRUCT-d: SWPRODSTRUCT: SWPRODSTRUCTAVAIL:AVAIL.EFFECTIVEDATE > NOW() WHERE AVAIL.AVAILTYPE = 149 (Last Order).
				//ErrorMessage 'references a withdrawn' LD(SWPRODSTRUCT) NDN(SWPRODSTRUCT)
				checkWDAvails(rootEntity,"WWSEOSWPRODSTRUCT","SWPRODSTRUCT","SWPRODSTRUCTAVAIL",strNow);

				//7.	AllValuesOf(WWSEOSWPRODSTRUCT-d: SWPRODSTRUCT-u: SWFEATURE. WITHDRAWDATEEFF_T > NOW()
				//ErrorMessage 'references a withdrawn' LD(SWFEATURE) NDN(SWPRODSTRUCT)
				checkWDFeatures(rootEntity,"WWSEOSWPRODSTRUCT","SWPRODSTRUCT", "SWFEATURE", strNow);

				//8.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATURE. WITHDRAWDATEEFF_T > NOW()
				//ErrorMessage 'references a withdrawn' LD(FEATURE) NDN(PRODSTRUCT)
				checkWDFeatures(rootEntity,"WWSEOPRODSTRUCT","PRODSTRUCT", "FEATURE", strNow);

				//9.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT.STATUS) = 0020 (Final) OR Empty
				//ErrorMessage NDN(PRODSTRUCT) 'is not Final'.
				Vector psVct = PokUtils.getAllLinkedEntities(rootEntity, "WWSEOPRODSTRUCT", "PRODSTRUCT");
				addDebug("PRODSTRUCT from WWSEOPRODSTRUCT found: "+psVct.size());
				checkStatus(psVct);
				psVct.clear();

				//10.	AllValuesOf(WWSEOSWPRODSTRUCT-d: SWPRODSTRUCT.STATUS) = 0020 (Final) OR Empty
				//ErrorMessage NDN(SWPRODSTRUCT) 'is not Final'.
				psVct = PokUtils.getAllLinkedEntities(rootEntity, "WWSEOSWPRODSTRUCT", "SWPRODSTRUCT");
				addDebug("SWPRODSTRUCT from WWSEOSWPRODSTRUCT found: "+psVct.size());
				checkStatus(psVct);
				psVct.clear();
			} // end r4r
		}// has one MODEL

		if (getReturnCode()!=PASS){
			mdlList.dereference(); // not needed any more
		}
	}

    /**********************************
    * Must have MODELWWSEO in second VE because end up with PRODSTRUCTs and FEATUREs from this MODEL
    * when all you want is PRODSTRUCTs from WWSEOPRODSTRUCT
    */
    private void getModelVE(EntityItem rootEntity) throws Exception
    {
        String VeName = "EXRPT3WWSEO2";

        mdlList = m_db.getEntityList(m_elist.getProfile(),
                new ExtractActionItem(null, m_db, m_elist.getProfile(), VeName),
                new EntityItem[] { new EntityItem(null, m_elist.getProfile(), rootEntity.getEntityType(), rootEntity.getEntityID()) });
        addDebug("getModelVE:: Extract "+VeName+NEWLINE+PokUtils.outputList(mdlList));
	}

	/****************************************
	* Check for withdrawn date on the feature
	*7.	AllValuesOf(WWSEOSWPRODSTRUCT-d: SWPRODSTRUCT-u: SWFEATURE. WITHDRAWDATEEFF_T > NOW()
	*ErrorMessage 'references a withdrawn' LD(SWFEATURE) NDN(SWPRODSTRUCT)
	*8.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATURE. WITHDRAWDATEEFF_T > NOW()
	*ErrorMessage 'references a withdrawn' LD(FEATURE) NDN(PRODSTRUCT)
	*/
    private void checkWDFeatures(EntityItem rootEntity, String linktype, String etype, String fctype, String strNow)
	throws java.sql.SQLException, MiddlewareException
	{
		Object[] args = new String[2];

		Vector psVct = PokUtils.getAllLinkedEntities(rootEntity, linktype, etype);
		addDebug("checkWDFeatures entered go thru: "+linktype+" to "+etype+" then to "+fctype+" found: "+psVct.size()+" "+etype);
		for (int e=0; e<psVct.size(); e++){ // look at each ps or swps entity
			EntityItem theItem = (EntityItem)psVct.elementAt(e);
			addDebug("checkWDFeatures checking entity: "+theItem.getKey());
			for (int i=0; i<theItem.getUpLinkCount(); i++){ // look at (sw)feature
				EntityItem fcitem = (EntityItem)theItem.getUpLink(i);
				if (fcitem.getEntityType().equals(fctype)){ // right entity
					String effDate = PokUtils.getAttributeValue(fcitem, "WITHDRAWDATEEFF_T",", ", "", false);
					addDebug("checkWDFeatures checking "+fcitem.getKey()+" WITHDRAWDATEEFF_T: "+effDate);
					if (effDate.length()>0 && effDate.compareTo(strNow)<=0){
						//WITHDRAWN_ERR = references a withdrawn {0} {1}
						args[0] = fcitem.getEntityGroup().getLongDescription();
						args[1] = getNavigationName(theItem);
						addError("WITHDRAWN_ERR",args);
					}
				}
			}
		}
		psVct.clear();
	}

    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "WWSEO ABR.";

        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "1.42";
    }

    /**********************************************************************************
    * Given a root entity type, the path including the relative entity type is given.
    * This counts the number of Relatives of the EntityType specified via the path and
    * performs the comparison of this count to the # specified. This is a count of paths
    * to entities of the type specified so that even if the same instance of the entity
    * is linked multiple times it is counted multiple times.
	*
	* e.g.  FEATUREMON.QTY * CountOf( FEATUREMON-d: MON)  < = 1
	*
	* Note:  if FEATUREMON.QTY does not exist, then assume a default of 1
	*		A Feature can have a maximum of 1 Monitor.
	*
    */
    private int getCount(EntityList list, String relatorType) throws MiddlewareException
    {
		int count = 0;

		EntityGroup relGrp = list.getEntityGroup(relatorType);
		if (relGrp ==null){
			throw new MiddlewareException(relatorType+" is missing from extract");
		}
		if (relGrp.getEntityItemCount()>0){
			for(int i=0; i<relGrp.getEntityItemCount(); i++){
				int qty = 1;
				EntityItem relItem = relGrp.getEntityItem(i);
				EANAttribute attr = relItem.getAttribute("QTY");
            	if (attr != null){
					qty = Integer.parseInt(attr.get().toString());
				}
				count+=qty;
        		addDebug("getCount "+relItem.getKey()+" qty "+qty);
			}
		}

        addDebug("getCount Total count found for "+relatorType+" = "+count);
		return count;
	}

    /***********************************************
    *Check for number of PLANAR or MECHPKG using qty on relators
    *
    *iii.	((WWSEOPRODSTRUCT. CONFQTY) * (WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREPLANAR.QTY) *
    * CountOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREPLANAR: PLANAR)) +
	* ((MODELWWSEO.u: MODELPLANAR.QTY) * CountOf(MODELWWSEO-u: MODELPLANAR-d: PLANAR)) = 1
	* ErrorMessage 'must have exactly one' LD(PLANAR)
	*
	*iv.	((WWSEOPRODSTRUCT. CONFQTY) * (WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREMECHPKG.QTY) *
	* CountOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREMECHPKG: MECHPKG)) + ((MODELWWSEO.u: MODELMECHPKG.QTY) *
	* CountOf(MODELMECHPKG-u: MODELMECHPKG-d: MECHPKG)) = 1
	* ErrorMessage 'must have exactly one' LD(MECHPKG)
    */
    private void checkCount(String fcrelator, String mdlrelator, String entity)throws MiddlewareException
    {
		addDebug("checkCount entered for "+fcrelator+" "+mdlrelator+" "+entity);
		EntityGroup eGrp = m_elist.getEntityGroup(entity);
		int fccnt = getCount(m_elist,fcrelator);
		if(fccnt > 1) {
			//NEED_ONLY_ONE_ERR = must have exactly one {0}
			Object args[] = new Object[]{eGrp.getLongDescription()};
			addError("NEED_ONLY_ONE_ERR",args);
		}else{
			int mdlcnt = getCount(mdlList,mdlrelator);
			if(mdlcnt > 1) {
				//NEED_ONLY_ONE_ERR = must have exactly one {0}
				Object args[] = new Object[]{eGrp.getLongDescription()};
				addError("NEED_ONLY_ONE_ERR",args);
			}else{
				// look at the combination
				if (fccnt+mdlcnt>1 || (fccnt+mdlcnt==0)){
					//NEED_ONLY_ONE_ERR = must have exactly one {0}
					Object args[] = new Object[]{eGrp.getLongDescription()};
					addError("NEED_ONLY_ONE_ERR",args);
				}else if (fccnt>0){ // must be one now
					int totalConfQty = 0;
					// check for (WWSEOPRODSTRUCT.CONFQTY) on the WWSEOPRODSTRUCT-PRODSTRUCT-FEATURE-fcrelator
					EntityItem fcrelItem = m_elist.getEntityGroup(fcrelator).getEntityItem(0);
					for (int ui=0; ui<fcrelItem.getUpLinkCount(); ui++)
					{
						EntityItem featItem = (EntityItem)fcrelItem.getUpLink(ui);
						if (featItem.getEntityType().equals("FEATURE"))
						{
							addDebug("checkCount "+fcrelItem.getKey()+" uplink["+ui+"]: "+featItem.getKey());
							for (int i=0; i<featItem.getDownLinkCount(); i++)
							{
								EntityItem psItem = (EntityItem)featItem.getDownLink(i);
								if (psItem.getEntityType().equals("PRODSTRUCT")) {
									addDebug("checkCount "+featItem.getKey()+" dnlink["+i+"]: "+psItem.getKey());
									// find parent WWSEOPS
									for (int uii=0; uii<psItem.getUpLinkCount(); uii++)
									{
										EntityItem wwseopsItem = (EntityItem)psItem.getUpLink(uii);
										if (wwseopsItem.getEntityType().equals("WWSEOPRODSTRUCT"))
										{
											addDebug("checkCount "+psItem.getKey()+" uplink["+uii+"]: "+wwseopsItem.getKey());
											String confQty = PokUtils.getAttributeValue(wwseopsItem, "CONFQTY",", ", "1", false);
											addDebug("checkCount "+wwseopsItem.getKey()+" CONFQTY: "+confQty);
											int iconfQty = Integer.parseInt(confQty);
											totalConfQty+=iconfQty;
										}
									}
								}
							}
						}
					}
					addDebug("checkCount totalConfQty: "+totalConfQty);
					if(totalConfQty > 1) {
						//NEED_ONLY_ONE_ERR = must have exactly one {0}
						Object args[] = new Object[]{eGrp.getLongDescription()};
						addError("NEED_ONLY_ONE_ERR",args);
					}
				}
			}
		}
	}
    /***********************************************
    *Check for number of MECHPKG using qty on relators
	*
	*iv.	((WWSEOPRODSTRUCT. CONFQTY) * (WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREMECHPKG.QTY) *
		CountOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREMECHPKG: MECHPKG))+ ((MODELWWSEO.u: MODELMECHPKG.QTY) *
		CountOf(MODELMECHPKG-u: MODELMECHPKG-d: MECHPKG)) => 1
    */
    private void checkExists(String fcrelator, String mdlrelator, String entity)throws MiddlewareException
    {
		addDebug("checkExists entered for "+fcrelator+" "+mdlrelator+" "+entity);
		EntityGroup eGrp = m_elist.getEntityGroup(entity);
		int fccnt = getCount(m_elist,fcrelator);
		if(fccnt > 0) {
			addDebug("checkExists fccnt "+fccnt);
		}else{
			int mdlcnt = getCount(mdlList,mdlrelator);
			if(mdlcnt > 0) {
				addDebug("checkExists mdlcnt "+mdlcnt);
			}else{
				//MINIMUM_ERR = must have at least one {0}
				Object args[] = new Object[]{eGrp.getLongDescription()};
				addError("MINIMUM_ERR",args);
			}
		}
	}

    /***********************************************
    * check bays
	i.	 (WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREMECHPKG-d: MECHPKGBAY-d: BAY) UNIQUE (BAYTYPE & ACCSS & BAYFF)
	ErrorMessage 'must have unique' LD(BAY) (BAY.BAYTYPE & ACCSS & BAYFF)
	ii.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREMECHPKG-d: MECHPKGBAY-d: BAY UNIQUE (BAYTYPE & ACCSS & BAYFF) IN (WWSEOBAYSAVAIL-d: BAYSAVAIL-d. BAYAVAILTYPE & ACCSS & BAYFF)
	ErrorMessage 'each BAY Type must have a corresponding' LD(BAYSAVAIL) (BAY.BAYTYPE & ACCSS & BAYFF)
	iii.AllValuesOf(WWSEOBAYSAVAIL-d: BAYSAVAIL-d. BAYAVAILTYPE & ACCSS & BAYFF) IN (WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREMECHPKG-d: MECHPKGBAY-d: BAY UNIQUE (BAYTYPE & ACCSS & BAYFF)
	ErrorMessage 'each BAYSAVAIL Type must have a corresponding' LD(BAYS) (BAYSAVAILTYPE.BAYAVAILTYPE & ACCSS & BAYFF)
    */
    private void checkBays()
    {
        Hashtable bayTbl = new Hashtable();
        Hashtable baysAvailTbl = new Hashtable();
        //HashSet baysavailSet = new HashSet();

		Iterator itr;

		addDebug("checkBays: entered");

		// must get FEATUREMECHPKG-d: MECHPKGBAY-d: BAY
		EntityGroup bayGrp = m_elist.getEntityGroup("BAY");
		EntityGroup featGrp = m_elist.getEntityGroup("FEATURE");
		Vector mechpkgVector = PokUtils.getAllLinkedEntities(featGrp, "FEATUREMECHPKG", "MECHPKG");
		Vector bayVector = PokUtils.getAllLinkedEntities(mechpkgVector, "MECHPKGBAY", "BAY");

		addDebug("checkBays: bayVector.size "+bayVector.size());

		//i.	CountOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREMECHPKG-d: MECHPKGBAY-d: BAY) => 0
		//ErrorMessage 'must have at least one' LD(BAY)
		/*"SG FS ABR Data Quality 20080308b.doc" spec chg
		if(bayVector.size() == 0)	{
			//MINIMUM_ERR = must have at least one {0}
			Object args[] = new Object[]{bayGrp.getLongDescription()};
			addError("MINIMUM_ERR",args);
			return; // no BAYS, nothing else to check here
		}*/

		for(int i = 0; i < bayVector.size(); i++)
		{
			EntityItem bayItem = (EntityItem)bayVector.elementAt(i);
			String bayType = PokUtils.getAttributeValue(bayItem, "BAYTYPE",", ", "", false);
			String bayTypeFlag = getAttributeFlagEnabledValue(bayItem, "BAYTYPE");
			String bayAccss = PokUtils.getAttributeValue(bayItem, "ACCSS",", ", "", false);
			String bayAccssFlag = getAttributeFlagEnabledValue(bayItem, "ACCSS");
			String bayFF = PokUtils.getAttributeValue(bayItem, "BAYFF",", ", "", false);
			String bayFFFlag = getAttributeFlagEnabledValue(bayItem, "BAYFF");
			String bayInfo = bayType + ", " + bayAccss + ", " + bayFF;
			String bayFlags = bayTypeFlag + "," + bayAccssFlag + "," + bayFFFlag;
			addDebug("checkBays: "+bayItem.getKey()+" bayInfo: "+bayInfo+"["+bayFlags+"]");
			if (bayTbl.containsKey(bayFlags)){
				//i.  (WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREMECHPKG-d: MECHPKGBAY-d: BAY) UNIQUE
		    	//			(BAYTYPE & ACCSS & BAYFF)
				//ErrorMessage 'must have unique' LD(BAY) (BAY.BAYTYPE & ACCSS & BAYFF)

				//MUST_HAVE_UNIQUE_ERR = must have unique {0} {1}
				Object args[] = new Object[2];
				args[0]=bayGrp.getLongDescription();
				args[1]=bayInfo;
				addError("MUST_HAVE_UNIQUE_ERR",args);

			}else{
				bayTbl.put(bayFlags,bayInfo);
			}
		}

		EntityGroup baysavailGrp = m_elist.getEntityGroup("BAYSAVAIL");
		for(int i = 0; i < baysavailGrp.getEntityItemCount(); i++)
		{
			EntityItem baysavailItem = baysavailGrp.getEntityItem(i);
			String bayAvailType = PokUtils.getAttributeValue(baysavailItem, "BAYAVAILTYPE",", ", "", false);
			String bayAvailTypeFlag = getAttributeFlagEnabledValue(baysavailItem, "BAYAVAILTYPE");
			String bayAccss = PokUtils.getAttributeValue(baysavailItem, "ACCSS",", ", "", false);
			String bayAccssFlag = getAttributeFlagEnabledValue(baysavailItem, "ACCSS");
			String bayFF = PokUtils.getAttributeValue(baysavailItem, "BAYFF",", ", "", false);
			String bayFFFlag = getAttributeFlagEnabledValue(baysavailItem, "BAYFF");
			String bayInfo = bayAvailType + ", " + bayAccss + ", " + bayFF;
			String bayFlags = bayAvailTypeFlag + "," + bayAccssFlag + "," + bayFFFlag;
			addDebug("checkBays: "+baysavailItem.getKey()+" bayInfo: "+bayInfo+"["+bayFlags+"]");

			//baysavailSet.add(bayFlags);
			baysAvailTbl.put(bayFlags,bayInfo);
		}

		//MECHPKG with BAY.BAYTYPE+BAY.ACCSS+BAY.BAYFF must have a matching BAYSAVAIL.BAYAVAILTYPE+BAYSAVAIL.ACCSS+BAYSAVAIL.BAYFF linked via WWSEOBAYSAVAIL
		itr = bayTbl.keySet().iterator();
		while(itr.hasNext())
		{
			String bayInfo = (String) itr.next();
			//ii.  AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREMECHPKG-d: MECHPKGBAY-d: BAY UNIQUE (BAYTYPE & ACCSS & BAYFF) IN (WWSEOBAYSAVAIL-d: BAYSAVAIL-d. BAYAVAILTYPE & ACCSS & BAYFF)
			//ErrorMessage 'each BAY Type must have a corresponding' LD(BAYSAVAIL) (BAY.BAYTYPE & ACCSS & BAYFF)
//			if(!baysavailSet.contains(bayInfo))
			if(!baysAvailTbl.containsKey(bayInfo))
			{
				//CORRESPONDING_ERR = each {0} {1} must have a corresponding {2} {3}
				Object args[] = new Object[4];
				args[0]=bayGrp.getLongDescription();
				args[1]="Type";
				args[2]=baysavailGrp.getLongDescription();
				args[3]=(String)bayTbl.get(bayInfo);
				addError("CORRESPONDING_ERR",args);
			}
		}//end of while(itr.hasNext())

		// make sure baysavail match bays, if no bays then there should not be any baysavail
		itr = baysAvailTbl.keySet().iterator();
		while(itr.hasNext())
		{
			String bayInfo = (String) itr.next();
			//iii.AllValuesOf(WWSEOBAYSAVAIL-d: BAYSAVAIL-d. BAYAVAILTYPE & ACCSS & BAYFF) IN (WWSEOPRODSTRUCT-d: PRODSTRUCT-u: FEATUREMECHPKG-d: MECHPKGBAY-d: BAY UNIQUE (BAYTYPE & ACCSS & BAYFF)
			//ErrorMessage 'each BAYSAVAIL Type must have a corresponding' LD(BAYS) (BAYSAVAILTYPE.BAYAVAILTYPE & ACCSS & BAYFF)

//			if(!baysavailSet.contains(bayInfo))
			if(!bayTbl.containsKey(bayInfo))
			{
				//CORRESPONDING_ERR = each {0} {1} must have a corresponding {2} {3}
				Object args[] = new Object[4];
				args[0]=baysavailGrp.getLongDescription();
				args[1]="Type";
				args[2]=bayGrp.getLongDescription();
				args[3]=(String)baysAvailTbl.get(bayInfo);
				addError("CORRESPONDING_ERR",args);
			}
		}//end of while(itr.hasNext())


		bayTbl.clear();
		bayTbl = null;
		baysAvailTbl.clear();
		baysAvailTbl = null;
		//baysavailSet.clear();
		//baysavailSet = null;
		mechpkgVector.clear();
		bayVector.clear();
    }

    /***********************************************
    *check slots
	*iv.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREMEMORYCARD-d: MEMORYCARDSLOT-d: SLOT.SLOTTYPE) IN (WWSEOSLOTSAVAIL-d: SLOTSAVAIL. SLOTTYPE)
	*ErrorMessage 'each' LD(MEMORYCARD) LD(SLOT) 'must have a corresponding' LD(SLOTSAVAIL) LD(SLOTTYPE)
	*v.	(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREMEMORYCARD-d: MEMORYCARDSLOT-d: SLOT.SLOTTYPE) UNIQUE (SLOTTYPE & SLOTSZE)
	*ErrorMessage 'must have unique' LD(SLOT) (SLOT.SLOTTYPE & SLOTSZE)
	*
	*vi.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREPLANAR-d: PLANARSLOT-d: SLOT.SLOTTYPE) IN (WWSEOSLOTSAVAIL-d: SLOTSAVAIL.SLOTTYPE)
	*ErrorMessage 'each' LD(PLANAR) LD(SLOT) 'must have a corresponding' LD(SLOTSAVAIL) LD(SLOTTYPE)
	*vii.	(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREPLANAR-d: PLANARSLOT-d: SLOT.SLOTTYPE) IN (WWSEOSLOTSAVAIL-d: SLOTSAVAIL.SLOTTYPE) UNIQUE (SLOTTYPE & SLOTSZE)
	*ErrorMessage 'must have unique' LD(SLOT) (SLOT.SLOTTYPE & SLOTSZE)
	*
	*viii.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREEXPDUNIT-d: EXPDUNITSLOT-d: SLOT.SLOTTYPE) IN (WWSEOSLOTSAVAIL-d: SLOTSAVAIL.SLOTTYPE)
	*ErrorMessage 'each' LD(EXPDUNIT) LD(SLOT) 'must have a corresponding' LD(SLOTSAVAIL) LD(SLOTTYPE)
	*ix.	(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREEXPDUNIT-d: EXPDUNITSLOT-d: SLOT.SLOTTYPE) UNIQUE (SLOTTYPE & SLOTSZE)
	*ErrorMessage 'must have unique' LD(SLOT) (SLOT.SLOTTYPE & SLOTSZE)
iv.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREMEMORYCARD-d: MEMORYCARDSLOT-d: SLOT.SLOTTYPE)
	IN (WWSEOSLOTSAVAIL-d: SLOTSAVAIL. SLOTTYPE) where SLOTSAVAIL. ELEMENTTYPE = '0010' (Memory Card)
ErrorMessage 'each' LD(MEMORYCARD) LD(SLOT) 'must have a corresponding' LD(SLOTSAVAIL) LD(SLOTTYPE)
v.	AllValuesOf(WWSEOSLOTSAVAIL-d: SLOTSAVAIL. SLOTTYPE) where SLOTSAVAIL. ELEMENTTYPE = '0010' (Memory Card)
	IN (WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREMEMORYCARD-d: MEMORYCARDSLOT-d: SLOT.SLOTTYPE)
ErrorMessage "Each" LD(SLOTSAVAIL) LD(SLOTTYPE) 'must have a corresponding' LD(MEMORYCARD) LD(SLOT)
vi.	(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREMEMORYCARD-d: MEMORYCARDSLOT-d: SLOT.SLOTTYPE) UNIQUE (SLOTTYPE & SLOTSZE)
ErrorMessage ''must have unique' LD(SLOT) (SLOT.SLOTTYPE & SLOTSZE)

vii.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREPLANAR-d: PLANARSLOT-d: SLOT.SLOTTYPE) IN (WWSEOSLOTSAVAIL-d: SLOTSAVAIL.SLOTTYPE) where SLOTSAVAIL. ELEMENTTYPE = '0020' (Planar)
ErrorMessage 'each' LD(PLANAR) LD(SLOT) 'must have a corresponding' LD(SLOTSAVAIL) LD(SLOTTYPE)
viii.	(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREPLANAR-d: PLANARSLOT-d: SLOT.SLOTTYPE) IN (WWSEOSLOTSAVAIL-d: SLOTSAVAIL.SLOTTYPE) UNIQUE (SLOTTYPE & SLOTSZE)
ErrorMessage ''must have unique' LD(SLOT) (SLOT.SLOTTYPE & SLOTSZE)
ix.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREEXPDUNIT-d: EXPDUNITSLOT-d: SLOT.SLOTTYPE) IN (WWSEOSLOTSAVAIL-d: SLOTSAVAIL.SLOTTYPE) where SLOTSAVAIL. ELEMENTTYPE = '0030' (Expansion Unit)
ErrorMessage 'each' LD(EXPDUNIT) LD(SLOT) 'must have a corresponding' LD(SLOTSAVAIL) LD(SLOTTYPE)
x.	(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREEXPDUNIT-d: EXPDUNITSLOT-d: SLOT.SLOTTYPE) UNIQUE (SLOTTYPE & SLOTSZE)
ErrorMessage ''must have unique' LD(SLOT) (SLOT.SLOTTYPE & SLOTSZE)
    */
    private void checkSlots()
    {
        Hashtable slotsavailMemCardSet  = new Hashtable();
        Hashtable slotsavailPlanarSet  = new Hashtable();
        Hashtable slotsavailExpUnitSet  = new Hashtable();
		/*
		ELEMENTTYPE	0010	Memory Card
		ELEMENTTYPE	0020	Planar
		ELEMENTTYPE	0030	Expansion Unit
		*/
		String memCardTypeFlag = "0010";
		String planarTypeFlag = "0020";
		String expunitTypeFlag = "0030";

		EntityGroup slotsAvailGrp = m_elist.getEntityGroup("SLOTSAVAIL");

        // WWSEO needs to match up the SLOTS with the SLOTSAVAIL by ELEMENTTYPE for  Planar, Memory, and Expansion Unit.
		for(int i = 0; i < slotsAvailGrp.getEntityItemCount(); i++)
		{
			EntityItem slotsavailItem = slotsAvailGrp.getEntityItem(i);
			String slotType = PokUtils.getAttributeValue(slotsavailItem, "SLOTTYPE",", ", "", false);
			String slotTypeFlag = getAttributeFlagEnabledValue(slotsavailItem, "SLOTTYPE");
			String elementType = PokUtils.getAttributeValue(slotsavailItem, "ELEMENTTYPE",", ", "", false);
			String elementTypeFlag = getAttributeFlagEnabledValue(slotsavailItem, "ELEMENTTYPE");
			addDebug("checkSlots: "+slotsavailItem.getKey()+" slotType: "+slotType+
				"["+slotTypeFlag+"] elementType: "+elementType);
			if (elementTypeFlag==null || elementTypeFlag.length()==0){
				addDebug("checkSlots: skipping "+slotsavailItem.getKey()+" slotType: "+slotType+" does not have ELEMENTTYPE defined.");
				continue;
			}

			if (memCardTypeFlag.equals(elementTypeFlag)){
				slotsavailMemCardSet.put(slotTypeFlag,slotType);
			}else if(planarTypeFlag.equals(elementTypeFlag)){
				slotsavailPlanarSet.put(slotTypeFlag,slotType);
			}else if(expunitTypeFlag.equals(elementTypeFlag)){
				slotsavailExpUnitSet.put(slotTypeFlag,slotType);
			}
		}

		addDebug("slotsavailMemCardSet "+slotsavailMemCardSet);
		addDebug("slotsavailPlanarSet "+slotsavailPlanarSet);
		addDebug("slotsavailExpUnitSet "+slotsavailExpUnitSet);

		//iv.	AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREMEMORYCARD-d: MEMORYCARDSLOT-d: SLOT.SLOTTYPE)
		//	IN (WWSEOSLOTSAVAIL-d: SLOTSAVAIL. SLOTTYPE) where SLOTSAVAIL. ELEMENTTYPE = '0010' (Memory Card)
		//ErrorMessage 'each' LD(MEMORYCARD) LD(SLOT) 'must have a corresponding' LD(SLOTSAVAIL) LD(SLOTTYPE)
//v.	AllValuesOf(WWSEOSLOTSAVAIL-d: SLOTSAVAIL. SLOTTYPE) where SLOTSAVAIL. ELEMENTTYPE = '0010' (Memory Card)
//	IN (WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREMEMORYCARD-d: MEMORYCARDSLOT-d: SLOT.SLOTTYPE)
//ErrorMessage "Each" LD(SLOTSAVAIL) LD(SLOTTYPE) 'must have a corresponding' LD(MEMORYCARD) LD(SLOT)
		//vi.	(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREMEMORYCARD-d: MEMORYCARDSLOT-d: SLOT.SLOTTYPE) UNIQUE (SLOTTYPE & SLOTSZE)
		//ErrorMessage ''must have unique' LD(SLOT) (SLOT.SLOTTYPE & SLOTSZE)
		checkElementSlots(slotsavailMemCardSet,"MEMORYCARD","FEATUREMEMORYCARD","MEMORYCARDSLOT", true);

        //vi.  AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREPLANAR-d: PLANARSLOT-d: SLOT.SLOTTYPE) IN
        //  (WWSEOSLOTSAVAIL-d: SLOTSAVAIL.SLOTTYPE)
		//vii.  (WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREPLANAR-d: PLANARSLOT-d: SLOT.SLOTTYPE) IN (WWSEOSLOTSAVAIL-d: SLOTSAVAIL.SLOTTYPE) UNIQUE (SLOTTYPE & SLOTSZE)
		// ErrorMessage 'must have unique' LD(SLOT) (SLOT.SLOTTYPE & SLOTSZE)
		checkElementSlots(slotsavailPlanarSet,"PLANAR","FEATUREPLANAR","PLANARSLOT", false);

		//viii.AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREEXPDUNIT-d: EXPDUNITSLOT-d: SLOT.SLOTTYPE)
		//	IN (WWSEOSLOTSAVAIL-d: SLOTSAVAIL.SLOTTYPE)
		//ErrorMessage 'each' LD(EXPDUNIT) LD(SLOT) 'must have a corresponding' LD(SLOTSAVAIL) LD(SLOTTYPE)
		//ix.(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREEXPDUNIT-d: EXPDUNITSLOT-d: SLOT.SLOTTYPE) UNIQUE (SLOTTYPE & SLOTSZE)
		// ErrorMessage 'must have unique' LD(SLOT) (SLOT.SLOTTYPE & SLOTSZE)
		checkElementSlots(slotsavailExpUnitSet,"EXPDUNIT","FEATUREEXPDUNIT","EXPDUNITSLOT", false);

        slotsavailMemCardSet.clear();
        slotsavailMemCardSet = null;
        slotsavailPlanarSet.clear();
        slotsavailPlanarSet = null;
        slotsavailExpUnitSet.clear();
        slotsavailExpUnitSet = null;
    }

    /***********************************************
    *check element slots.
    * Each slot must have unique slottype and slotsize
    * Each slot must have a corresponding slotsavail
    */
    private void checkElementSlots(Hashtable slotsavailElementTbl,
    	String elemEntity, String elemRelator, String slotRelator, boolean checkConverse)
    {
        Hashtable slotTbl = new Hashtable();
        HashSet uniqueSet  = new HashSet();
		Object args[] = new Object[4];

		Iterator itr;

		addDebug("checkElementSlots: entered "+elemEntity+" "+elemRelator+" "+slotRelator);

		EntityGroup slotGrp = m_elist.getEntityGroup("SLOT");
		EntityGroup featGrp = m_elist.getEntityGroup("FEATURE");
		EntityGroup elementGrp = m_elist.getEntityGroup(elemEntity);
		EntityGroup slotsAvailGrp = m_elist.getEntityGroup("SLOTSAVAIL");
		Vector elementVector = PokUtils.getAllLinkedEntities(featGrp, elemRelator, elemEntity);
		Vector slotVector = PokUtils.getAllLinkedEntities(elementVector, slotRelator, "SLOT");

		for(int i = 0; i < slotVector.size(); i++)
		{
			EntityItem slotItem = (EntityItem) slotVector.get(i);
			String slotType = PokUtils.getAttributeValue(slotItem, "SLOTTYPE",", ", "", false);
			String slotSze = PokUtils.getAttributeValue(slotItem, "SLOTSZE",", ", "", false);
			String slotTypeFlag = getAttributeFlagEnabledValue(slotItem, "SLOTTYPE");
			String slotSzeFlag = getAttributeFlagEnabledValue(slotItem, "SLOTSZE");
			addDebug("checkElementSlots: "+elemEntity+" "+slotItem.getKey()+
				" slotType: "+slotType+"["+slotTypeFlag+"] slotSze: "+slotSze+"["+slotSzeFlag+"]");
			slotTbl.put(slotTypeFlag,slotType);
			if (uniqueSet.contains(slotTypeFlag+slotSzeFlag)){
				//v.(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREMEMORYCARD-d: MEMORYCARDSLOT-d: SLOT.SLOTTYPE) UNIQUE (SLOTTYPE & SLOTSZE)
				// ErrorMessage 'must have unique' LD(SLOT) (SLOT.SLOTTYPE & SLOTSZE)
				//MUST_HAVE_UNIQUE_ERR = must have unique {0} {1}
				args[0]=slotGrp.getLongDescription();
				args[1]=slotType+" "+slotSze;
				addError("MUST_HAVE_UNIQUE_ERR",args);
			}else{
				uniqueSet.add(slotTypeFlag+slotSzeFlag);
			}
		}
		uniqueSet.clear();

		//iv.AllValuesOf(WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREMEMORYCARD-d: MEMORYCARDSLOT-d: SLOT.SLOTTYPE) IN
		//		(WWSEOSLOTSAVAIL-d: SLOTSAVAIL. SLOTTYPE)
		//ErrorMessage 'each' LD(MEMORYCARD) LD(SLOT) 'must have a corresponding' LD(SLOTSAVAIL) LD(SLOTTYPE)
		itr = slotTbl.keySet().iterator();
		while(itr.hasNext())
		{
			String slotTypeFlag = (String) itr.next();
			if(!slotsavailElementTbl.containsKey(slotTypeFlag))
			{
				//CORRESPONDING_ERR = each {0} {1} must have a corresponding {2} {3}
				args[0]=elementGrp.getLongDescription();
				args[1]=slotGrp.getLongDescription();
				args[2]=slotsAvailGrp.getLongDescription();
				args[3]=(String)slotTbl.get(slotTypeFlag);
				addError("CORRESPONDING_ERR",args);
			}
		}

		if(checkConverse){
			// make sure slotsavail match slots, if no slots then there should not be any slotsavail
			itr = slotsavailElementTbl.keySet().iterator();
			while(itr.hasNext())
			{
				String slotInfo = (String) itr.next();
				if(!slotTbl.containsKey(slotInfo))
				{
					//CORRESPONDING_ERR = each {0} {1} must have a corresponding {2} {3}
					args[0]=slotsAvailGrp.getLongDescription();
					args[1]="Type";
					args[2]=slotGrp.getLongDescription();
					args[3]=(String)slotsavailElementTbl.get(slotInfo);
					addError("CORRESPONDING_ERR",args);
				}
			}//end of while(itr.hasNext())
		}

		slotTbl.clear();
		slotTbl = null;
		elementVector.clear();
		slotVector.clear();
		slotVector = null;
		elementVector = null;
    }
}
