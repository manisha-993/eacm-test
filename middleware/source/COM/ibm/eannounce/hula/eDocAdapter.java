//
// Copyright (c) 2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: eDocAdapter.java,v $
// Revision 1.13  2008/01/31 21:05:17  wendy
// Cleanup RSA warnings
//
// Revision 1.12  2005/02/09 22:13:42  dave
// more JTest Cleanup
//
// Revision 1.11  2004/12/09 18:53:35  gregg
// use eRecordCollection.getProductDetail
//
// Revision 1.10  2004/12/01 22:49:45  gregg
// more fix
//
// Revision 1.9  2004/12/01 22:22:16  gregg
// more fixes
//
// Revision 1.8  2004/11/09 20:53:25  gregg
// fix for refresh entire product
//
// Revision 1.7  2004/11/08 20:52:07  gregg
// to many debugs these days ...
// we need to start over on these to see whats goin on
//
// Revision 1.6  2004/11/04 21:46:23  gregg
// null ptr fix
//
// Revision 1.5  2004/10/29 21:20:54  gregg
// some fixes
//
// Revision 1.4  2004/10/29 21:08:39  gregg
// working on new product update logic
//
// Revision 1.3  2004/10/27 20:30:50  gregg
// some updates - think we're working again w/ eDocAdapter class
//
// Revision 1.2  2004/10/25 17:45:30  gregg
// opening up some accessors in eProductUpdater
//
// Revision 1.1  2004/10/25 17:12:52  gregg
// new eDocAdapter class:
//   moving all eProduct object building logic from eProdctUpdater up into eDocAdapter
//
//

package COM.ibm.eannounce.hula;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.transactions.NLSItem;
import COM.ibm.eannounce.objects.BlobAttribute;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.eannounce.objects.MultiFlagAttribute;
import COM.ibm.eannounce.objects.SingleFlagAttribute;
import COM.ibm.eannounce.objects.StatusAttribute;
import COM.ibm.eannounce.objects.TextAttribute;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

/**
 * this class takes in an eDoc and spits out eProducts in the context of our eProductUpdater
 */
public class eDocAdapter {

    private static final int  NEGATIVE_99 = -99;
    private static final int MAX_ENTITYITEMS_STORED = eProductProperties.getMaxEntityItemsStored();

    /**
     */
    public static final String NEW_LINE = "\n";
    
    private static final String ODS_SCHEMA = eProductProperties.getDatabaseSchema();
    static {
        System.out.println(NEW_LINE + "MAX_ENTITYITEMS_STORED:" + MAX_ENTITYITEMS_STORED + NEW_LINE);
    }

    private eDoc m_ed = null;
    private eProductUpdater m_epu = null;
    // these guys track our rdrs looping
    private int m_iRdrsPtr = 0;
    //
    private Hashtable m_hashGAMAP = null;
    private EANList m_elEntityItems = null;
    //
    private EntityGroup m_egRoot = null;
    private EANList m_elEGLCache = null;
    //
    private boolean m_bDone = false;

    /**
     * eDocAdapter
     *
     * @param _ed
     * @param _epu
     *  @author David Bigelow
     */
    public eDocAdapter(eDoc _ed, eProductUpdater _epu) {
        m_ed = _ed;
        m_epu = _epu;
        m_elEntityItems = new EANList();
        m_egRoot = _ed.getRootEntityGroup();
        m_elEGLCache = _ed.getEntityGroup();
    }

    /**
     * hasProducts
     *
     * @return
     *  @author David Bigelow
     */
    public boolean hasProducts() {
        return (m_ed.getTransactions().getRowCount() > 0);
    }

    /**
     * hasMoreProducts
     *
     * @return
     *  @author David Bigelow
     */
    public boolean hasMoreProducts() {
        return !m_bDone;
    }

    /**
     * Lets turn our transaction rdrs into something a little more sturdy
     * ... keep pointer to current rdrs index to save time
     *
     * @param _db
     * @throws java.lang.Exception
     * @return eProduct[]
     */
    public eProduct[] getNextProduct(Database _db) throws Exception {

        Vector vctProds = new Vector();
        eProduct[] aProds = new eProduct[0]; // init to zero size - we will rebuild below if necessary, we dont wanna return a null
        eProduct prodCurr = null;
        EntityItem eiRoot = null;

        ReturnDataResultSet rdrs = m_ed.getTransactions();
        CalendarAdjust calendarAdjust = new CalendarAdjust();
        // REMEMBER TO SET THIS RIGHT LATER!!!
        String strCountryCode = "NoNe";

        if (m_hashGAMAP == null) {
            populateGAMAP(_db);
        }

        //  rdrs is ordered by ROOT ENTITYIDs.
        //  This means that we will process products in chunks based upon the root entity, w/ product details below them.
        RDRS_LOOP : 
        for (/* m_iRdrsPtr is initialize to 0 above */; m_iRdrsPtr < rdrs.getRowCount(); m_iRdrsPtr++) {

            boolean bIsRel = false;
            boolean bProcessAllNLSIDs = false;
            int[] iaNLSIDs = null;

            // from rdrs

            String strGenArea_fc = rdrs.getColumn(m_iRdrsPtr, 0);
            String strRootType = rdrs.getColumn(m_iRdrsPtr, 1);
            int iRootID = rdrs.getColumnInt(m_iRdrsPtr, 2);
            String strRootTran = rdrs.getColumn(m_iRdrsPtr, 3);
            String strChildType = rdrs.getColumn(m_iRdrsPtr, 4);
            int iChildID = rdrs.getColumnInt(m_iRdrsPtr, 5);
            String strChildTran = rdrs.getColumn(m_iRdrsPtr, 6);
           // int iDummy = rdrs.getColumnInt(m_iRdrsPtr, 7);
            String strChildClass = rdrs.getColumn(m_iRdrsPtr, 8);
            String strChildPath = rdrs.getColumn(m_iRdrsPtr, 9);
            String strE2Type = rdrs.getColumn(m_iRdrsPtr, 10); // if childclass == 'R' -> child path's entity2type
            int iE2ID = rdrs.getColumnInt(m_iRdrsPtr, 11); // // if childclass == 'R' -> child path's entity2id

            if (!eProductProperties.includeGenArea(m_ed.getSourceActionItem().getActionItemKey(), strGenArea_fc)) {
                continue RDRS_LOOP;
            }

            if (prodCurr != null && prodCurr.getIntVal(eProduct.ENTITYID) != iRootID) {
                break RDRS_LOOP;
            }

            bIsRel = (strChildClass.equals("R"));
            bProcessAllNLSIDs = eProductProperties.isProcessAllNLSIDs(m_ed.getSourceActionItem().getActionItemKey());

            //
            // NLS processing
            //
            // If we have our Country information then process the indicated NLS ..
            //  --> if this is a country-independent item such as SBB --> process all NLS's for this profile.
            if (bProcessAllNLSIDs) {
                iaNLSIDs = new int[getProfile().getReadLanguages().size()];
                for (int j = 0; j < getProfile().getReadLanguages().size(); j++) {
                    iaNLSIDs[j] = getProfile().getReadLanguage(j).getNLSID();
                }
            } else {
                // NLS setting
                String[] saCountryInfo = getCountryInfo(strGenArea_fc);
                String strNLSID = "1";
                int iNLSID = 1;

                if (saCountryInfo != null) {
                    strNLSID = saCountryInfo[0];
                    strCountryCode = saCountryInfo[1];
                }
                try {
                    iNLSID = Integer.parseInt(strNLSID);
                } catch (NumberFormatException nfe) {
                    eProductUpdater.err("non parseable nlsid from GAMAP for:" + rdrs.getRow(m_iRdrsPtr));
                    throw nfe;
                }
                iaNLSIDs = new int[] { iNLSID };
            }

            //debug("iaNLSIDs.length = " + iaNLSIDs.length);

            //NLS_LOOP : 
            for (int iNLS = 0; iNLS < iaNLSIDs.length; iNLS++) {

                String strDetailEntityType = null;
                int iDetailEntityID = 0;
                
                EntityGroup egDetail = null;
                EntityItem eiDetail = null;
                String strDetailPublishFlag = null;
                
                int iNLSID_curr = iaNLSIDs[iNLS];

                //debug("NLS_LOOP: processing NLSID of " + iNLSID_curr);

                READ_LANGUAGE_LOOP : 
                for (int iReadLangs = 0; iReadLangs < getProfile().getReadLanguages().size(); iReadLangs++) {
                    NLSItem nlsItem = getProfile().getReadLanguage(iReadLangs);
                    if (iNLSID_curr == nlsItem.getNLSID()) {
                        getProfile().setReadLanguage(nlsItem);
                        //debug("NLS_LOOP: read language set to: " + nlsItem.getNLSID());
                        break READ_LANGUAGE_LOOP;
                    }
                }

                if (prodCurr == null) {

                    SingleFlagAttribute attGenAreaName = null;
                    String strGenAreaName = null;
                    String strGenAreaName_fc = null;
                    SingleFlagAttribute attGenAreaNameRegion = null;
                    String strGenAreaNameRegion = null;
                    String strGenAreaNameRegion_fc = null;
                    StatusAttribute attStatus = null;
                    String strStatus = null;
                    String strStatus_fc = null;
                    TextAttribute attPartNumber = null;
                    String strPartNumber = null;
                    TextAttribute attPartNumberDesc = null;
                    String strPartNumberDesc = null;
                    TextAttribute attAnnouncementDate = null;
                    String strAnnouncementDate = null;
                    TextAttribute attWithdrawalDate = null;
                    String strWithdrawalDate = null;
                    java.util.Date date = null;
                    String strFOTDate = null;
                    MultiFlagAttribute attAudience = null;
                    MultiFlagAttribute attControl = null;
                    
                    EntityGroup egRoot = m_ed.getRootEntityGroup();
                    String strProdPublishFlag = (strRootTran.equals("OFF") ? eProduct.UNPUBLISH_VAL : eProduct.PUBLISH_VAL);

                    if (eiRoot == null) {
                        eiRoot = getEntityItem(_db, egRoot, strRootType, iRootID);
                    }

                    prodCurr = null;
                    if (isProcessOneEntity()) {
                        prodCurr = new eProduct(_db, getProfile(), strRootType, iRootID);
                    } else {
                        prodCurr = new eProduct(getProfile(), strRootType, iRootID);
                    }

                    vctProds.addElement(prodCurr);
                    prodCurr.setProductUpdater(m_epu);

                    prodCurr.putIntVal(eProduct.VALID_FLAG, eProduct.VALID_FLAG_NO_VAL);
                    prodCurr.putIntVal(eProduct.FOTQHECK, 0);
                    prodCurr.putIntVal(eProduct.FINALQCHECK, 0);
                    prodCurr.putIntVal(eProduct.FOTQCHECKOVERRIDE, 0);
                    prodCurr.putIntVal(eProduct.FINALQCHECKOVERRIDE, 0);
                    // Control flags added 09/17/04 ... we still need logic to set these,
                    // but 4 now init to 0
                    prodCurr.putIntVal(eProduct.LEAUD, 0);
                    prodCurr.putIntVal(eProduct.LENEWFLAG, 0);
                    prodCurr.putIntVal(eProduct.LEBUYABLE, 0);
                    prodCurr.putIntVal(eProduct.LECUSTCART, 0);
                    prodCurr.putIntVal(eProduct.SHOPAUD, 0);
                    prodCurr.putIntVal(eProduct.SHOPNEWFLAG, 0);
                    prodCurr.putIntVal(eProduct.SHOPBUYABLE, 0);
                    prodCurr.putIntVal(eProduct.SHOPCUSTCART, 0);
                    prodCurr.putIntVal(eProduct.DACAUD, 0);
                    prodCurr.putIntVal(eProduct.DACNEWFLAG, 0);
                    prodCurr.putIntVal(eProduct.DACBUYABLE, 0);
                    prodCurr.putIntVal(eProduct.DACCUSTCART, 0);
                    //
                    prodCurr.putStringVal(eProduct.COUNTRYCODE, strCountryCode);
                    prodCurr.putStringVal(eProduct.PUBLISHFLAG, strProdPublishFlag);
                    prodCurr.putStringVal(eProduct.VALFROM, getNow());

                    ///
                    /// These are attributes on our root entity which map to columns in the PRODUCT table.
                    ///
                    // GENAREANAME
                    //SingleFlagAttribute attGenAreaName = (SingleFlagAttribute)eiRoot.getAttribute(eProductProperties.getProductAttCodeMapping(m_ed.getSourceActionItem().getActionItemKey(),eProduct.GENAREANAME));
                    attGenAreaName = (SingleFlagAttribute) eiRoot.getAttribute("GENAREANAME");
                    strGenAreaName = "";
                    strGenAreaName_fc = "";
                    if (attGenAreaName != null) {
                        strGenAreaName = attGenAreaName.toString();
                        strGenAreaName_fc = attGenAreaName.getFirstActiveFlagCode();
                    }
                    prodCurr.putStringVal(eProduct.GENAREANAME, strGenAreaName);
                    prodCurr.putStringVal(eProduct.GENAREANAME_FC, strGenAreaName_fc);
                    // GENAREANAMEREGION
                    // SingleFlagAttribute attGenAreaNameRegion = (SingleFlagAttribute)eiRoot.getAttribute(eProductProperties.getProductAttCodeMapping(m_ed.getSourceActionItem().getActionItemKey(),eProduct.GENAREANAMEREGION));
                    attGenAreaNameRegion = (SingleFlagAttribute) eiRoot.getAttribute("GENAREANAMEREGION");
                    strGenAreaNameRegion = "";
                    strGenAreaNameRegion_fc = "";
                    if (attGenAreaNameRegion != null) {
                        strGenAreaNameRegion = attGenAreaNameRegion.toString();
                        strGenAreaNameRegion_fc = attGenAreaNameRegion.getFirstActiveFlagCode();
                    }
                    prodCurr.putStringVal(eProduct.GENAREANAMEREGION, strGenAreaNameRegion);
                    prodCurr.putStringVal(eProduct.GENAREANAMEREGION_FC, strGenAreaNameRegion_fc);

                    // STATUS
                    attStatus = (StatusAttribute) eiRoot.getAttribute(eProductProperties.getProductAttCodeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.STATUS));
                    strStatus = "";
                    strStatus_fc = "";
                    if (attStatus != null) {
                        strStatus = attStatus.toString();
                        strStatus_fc = attStatus.getFirstActiveFlagCode();
                    }
                    prodCurr.putStringVal(eProduct.STATUS, strStatus);
                    prodCurr.putStringVal(eProduct.STATUS_FC, strStatus_fc);

                    //  PART NUMBER
                    attPartNumber = (TextAttribute) eiRoot.getAttribute(eProductProperties.getProductAttCodeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.PARTNUMBER));
                    strPartNumber = "";
                    if (attPartNumber != null) {
                        strPartNumber = attPartNumber.toString();
                    }
                    prodCurr.putStringVal(eProduct.PARTNUMBER, strPartNumber);
                    // PART NUMBER DESC
                    attPartNumberDesc = (TextAttribute) eiRoot.getAttribute(eProductProperties.getProductAttCodeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.PARTNUMBERDESC));
                    strPartNumberDesc = "";
                    if (attPartNumberDesc != null) {
                        strPartNumberDesc = attPartNumberDesc.toString();
                    }
                    prodCurr.putStringVal(eProduct.PARTNUMBERDESC, strPartNumberDesc);
                    // ANNOUNCEMENT DATE
                    attAnnouncementDate = (TextAttribute) eiRoot.getAttribute(eProductProperties.getProductAttCodeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.ANNOUNCEMENTDATE));
                    strAnnouncementDate = "9999-12-31";
                    if (attAnnouncementDate != null) {
                        strAnnouncementDate = attAnnouncementDate.toString();
                    }
                    prodCurr.putStringVal(eProduct.ANNOUNCEMENTDATE, strAnnouncementDate);
                    // WITHDRAWAL DATE
                    attWithdrawalDate = (TextAttribute) eiRoot.getAttribute(eProductProperties.getProductAttCodeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.WITHDRAWLDATE));
                    strWithdrawalDate = null;
                    if (attWithdrawalDate != null) {
                        strWithdrawalDate = attWithdrawalDate.toString();
                    }
                    prodCurr.putStringVal(eProduct.WITHDRAWLDATE, strWithdrawalDate);

                    // FOT DATE
                    date = calendarAdjust.getDate("yyyy-MM-dd", strAnnouncementDate);
                    strFOTDate = calendarAdjust.formatDate(calendarAdjust.dateAdd(date, 6, -28), "yyyy-MM-dd");
                    prodCurr.putStringVal(eProduct.FOTDATE, strFOTDate);

                    // 1) Audience -- LEAUD, SHOPAUD, DACAUD
                    attAudience = (MultiFlagAttribute) eiRoot.getAttribute(eProductProperties.getProductAttCodeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.AUDIENCE));
                    if (attAudience == null) {
                        //debug("ERROR getattribute()ting Multi for Audience attribute");
                    } else {
                        MetaFlag[] aMfAud = (MetaFlag[]) attAudience.get();
                        if (aMfAud == null) {
                            //debug("ERROR get()ting MetaFlags for Audience attribute");
                        } else {
                            AUD_FLAG_LOOP : for (int iAud = 0; iAud < aMfAud.length; iAud++) {
                                MetaFlag mfAud = aMfAud[iAud];
                                if (!mfAud.isSelected()) {
                                    continue AUD_FLAG_LOOP;
                                } else {
                                    // LEAUD
                                    String strLEAUD_fc = eProductProperties.getProductFlagCodeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.LEAUD);
                                    String strSHOPAUD_fc = eProductProperties.getProductFlagCodeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.SHOPAUD);
                                    String strDACAUD_fc = eProductProperties.getProductFlagCodeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.DACAUD);
                                    if (mfAud.getFlagCode().equals(strLEAUD_fc)) {
                                        int iLEAUD = 1;
                                        prodCurr.putIntVal(eProduct.LEAUD, iLEAUD);
                                    }
                                    // SHOPAUD
                                    if (mfAud.getFlagCode().equals(strSHOPAUD_fc)) {
                                        int iSHOPAUD = 1;
                                        prodCurr.putIntVal(eProduct.SHOPAUD, iSHOPAUD);
                                    }
                                    // DACAUD
                                    if (mfAud.getFlagCode().equals(strDACAUD_fc)) {
                                        int iDACAUD = 1;
                                        prodCurr.putIntVal(eProduct.DACAUD, iDACAUD);
                                    }
                                }
                            }
                        }
                    }

                    attControl =
                        (MultiFlagAttribute) eiRoot.getAttribute(eProductProperties.getProductAttCodeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.CCECONTROLOVERRIDES));
                    if (attControl == null) {

                    } else {
                        MetaFlag[] aMfControl = (MetaFlag[]) attControl.get();
                        if (aMfControl == null) {
                        } else {
                            CONTROL_FLAG_LOOP : for (int iControl = 0; iControl < aMfControl.length; iControl++) {
                                MetaFlag mfControl = aMfControl[iControl];
                                if (!mfControl.isSelected()) {
                                    continue CONTROL_FLAG_LOOP;
                                } else {
                                    String[] saControlFlagConsts =
                                        new String[] {
                                            eProduct.LENEWFLAG,
                                            eProduct.LEBUYABLE,
                                            eProduct.LECUSTCART,
                                            eProduct.SHOPNEWFLAG,
                                            eProduct.SHOPBUYABLE,
                                            eProduct.SHOPCUSTCART,
                                            eProduct.DACNEWFLAG,
                                            eProduct.DACBUYABLE,
                                            eProduct.DACCUSTCART };
                                    for (int iControlFlagConsts = 0; iControlFlagConsts < saControlFlagConsts.length; iControlFlagConsts++) {
                                        String strControlFlagConst = saControlFlagConsts[iControlFlagConsts];
                                        String[] saControl_sd = eProductProperties.getProductFlagShortDescMappings(m_ed.getSourceActionItem().getActionItemKey(), strControlFlagConst);
                                        for (int iControlFlags = 0; iControlFlags < saControl_sd.length; iControlFlags++) {
                                            if (mfControl.getShortDescription().equals(saControl_sd[iControlFlags])) {
                                                int iControl_sd = -4;
                                                try {
                                                    iControl_sd = Integer.parseInt(saControl_sd[iControlFlags]);
                                                } catch (NumberFormatException nofx) {
                                                    eProductUpdater.err("ERROR parsing " + strControlFlagConst + " Flag:" + nofx.toString());
                                                    System.exit(-1);
                                                }
                                                prodCurr.putIntVal(strControlFlagConst, iControl_sd);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } // END CONTROL FLAGS

                    // we need to do this here for full image mode, b/c we wont get the roottype==childtype
                    // record back in rdrs from eDoc...
                    if (isProcessOneEntity()) {
                        buildProductDetail(_db, prodCurr, eiRoot, strRootType, iRootID, "", strProdPublishFlag, bIsRel);
                    }

                } // end PRODUCT block

                if (prodCurr == null) {
                    eProductUpdater.err("rdrs error at loop [" + m_iRdrsPtr + "]. Product Root is null:" + rdrs.getRow(m_iRdrsPtr).toString());
                    throw new Exception("rdrs error at loop [" + m_iRdrsPtr + "]. Product Root is null:" + rdrs.getRow(m_iRdrsPtr).toString());
                    //continue RDRS_LOOP;
                }

                strDetailEntityType = (bIsRel ? strE2Type : strChildType);
                iDetailEntityID = (bIsRel ? iE2ID : iChildID);

                egDetail = m_ed.getEntityGroup(strChildType);
                eiDetail = getEntityItem(_db, egDetail, strChildType, iChildID);
                strDetailPublishFlag = (strChildTran.equals("ON") ? eProduct.PUBLISH_VAL : eProduct.UNPUBLISH_VAL);

                // MODELNAME
                if (eiDetail.getEntityType().equals(eProductProperties.getProductEntityTypeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.MODELNAME))) {
                    TextAttribute attModelName = (TextAttribute) eiDetail.getAttribute(eProductProperties.getProductAttCodeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.MODELNAME));
                    String strModelName = null;
                    if (attModelName != null) {
                        strModelName = attModelName.toString();
                    }
                    prodCurr.putStringVal(eProduct.MODELNAME, strModelName);
                }
                // WW PART NUMBER
                if (eiDetail.getEntityType().equals(eProductProperties.getProductEntityTypeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.WWPARTNUMBER))) {
                    TextAttribute attWWPartNumber =
                        (TextAttribute) eiDetail.getAttribute(eProductProperties.getProductAttCodeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.WWPARTNUMBER));
                    String strWWPartNumber = null;
                    if (attWWPartNumber != null) {
                        strWWPartNumber = attWWPartNumber.toString();
                    }
                    prodCurr.putStringVal(eProduct.WWPARTNUMBER, strWWPartNumber);
                }
                // CONTRACT INV TITLE
                if (eiDetail.getEntityType().equals(eProductProperties.getProductEntityTypeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.CONTRACTINVTITLE))) {
                    TextAttribute attContractInvTitle =
                        (TextAttribute) eiDetail.getAttribute(eProductProperties.getProductAttCodeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.CONTRACTINVTITLE));
                    String strContractInvTitle = null;
                    if (attContractInvTitle != null) {
                        strContractInvTitle = attContractInvTitle.toString();
                    }
                    prodCurr.putStringVal(eProduct.CONTRACTINVTITLE, strContractInvTitle);
                }
                // TYPE
                if (eiDetail.getEntityType().equals(eProductProperties.getProductEntityTypeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.TYPE))) {
                    SingleFlagAttribute attType =
                        (SingleFlagAttribute) eiDetail.getAttribute(eProductProperties.getProductAttCodeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.TYPE));
                    String strType = null;
                    String strType_fc = null;
                    if (attType != null) {
                        strType = attType.toString();
                        strType_fc = attType.getFirstActiveFlagCode();
                    }
                    prodCurr.putStringVal(eProduct.TYPE, strType);
                    prodCurr.putStringVal(eProduct.TYPE_FC, strType_fc);
                }
                // INSTALL OPT
                if (eiDetail.getEntityType().equals(eProductProperties.getProductEntityTypeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.INSTALLOPT))) {
                    SingleFlagAttribute attInstallOpt =
                        (SingleFlagAttribute) eiDetail.getAttribute(eProductProperties.getProductAttCodeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.INSTALLOPT));
                    String strInstallOpt = null;
                    String strInstallOpt_fc = null;
                    if (attInstallOpt != null) {
                        strInstallOpt = attInstallOpt.toString();
                        strInstallOpt_fc = attInstallOpt.getFirstActiveFlagCode();
                    }
                    prodCurr.putStringVal(eProduct.INSTALLOPT, strInstallOpt);
                    prodCurr.putStringVal(eProduct.INSTALLOPT_FC, strInstallOpt_fc);
                }
                // SPECIAL BID
                if (eiDetail.getEntityType().equals(eProductProperties.getProductEntityTypeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.SPECIALBID))) {
                    SingleFlagAttribute attSpecialBid =
                        (SingleFlagAttribute) eiDetail.getAttribute(eProductProperties.getProductAttCodeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.SPECIALBID));
                    String strSpecialBid = null;
                    String strSpecialBid_fc = null;
                    if (attSpecialBid != null) {
                        strSpecialBid = attSpecialBid.toString();
                        strSpecialBid_fc = attSpecialBid.getFirstActiveFlagCode();
                    }
                    prodCurr.putStringVal(eProduct.SPECIALBID, strSpecialBid);
                    prodCurr.putStringVal(eProduct.SPECIALBID_FC, strSpecialBid_fc);
                }
                // RATE CARD CODE
                if (eiDetail.getEntityType().equals(eProductProperties.getProductEntityTypeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.RATECARDCODE))) {
                    SingleFlagAttribute attRateCardCode =
                        (SingleFlagAttribute) eiDetail.getAttribute(eProductProperties.getProductAttCodeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.RATECARDCODE));
                    String strRateCardCode = null;
                    String strRateCardCode_fc = null;
                    if (attRateCardCode != null) {
                        strRateCardCode = attRateCardCode.toString();
                        strRateCardCode_fc = attRateCardCode.getFirstActiveFlagCode();
                    }
                    prodCurr.putStringVal(eProduct.RATECARDCODE, strRateCardCode);
                    prodCurr.putStringVal(eProduct.RATECARDCODE_FC, strRateCardCode_fc);
                }
                // UNSPSC
                if (eiDetail.getEntityType().equals(eProductProperties.getProductEntityTypeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.UNSPSC))) {
                    SingleFlagAttribute attUnspsc =
                        (SingleFlagAttribute) eiDetail.getAttribute(eProductProperties.getProductAttCodeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.UNSPSC));
                    String strUnspsc = null;
                    String strUnspsc_fc = null;
                    if (attUnspsc != null) {
                        strUnspsc = attUnspsc.toString();
                        strUnspsc_fc = attUnspsc.getFirstActiveFlagCode();
                    }
                    prodCurr.putStringVal(eProduct.UNSPSC, strUnspsc);
                    prodCurr.putStringVal(eProduct.UNSPSC_FC, strUnspsc_fc);
                }
                // UNUOM
                if (eiDetail.getEntityType().equals(eProductProperties.getProductEntityTypeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.UNUOM))) {
                    SingleFlagAttribute attUnuom =
                        (SingleFlagAttribute) eiDetail.getAttribute(eProductProperties.getProductAttCodeMapping(m_ed.getSourceActionItem().getActionItemKey(), eProduct.UNUOM));
                    String strUnuom = null;
                    String strUnuom_fc = null;
                    if (attUnuom != null) {
                        strUnuom = attUnuom.toString();
                        strUnuom_fc = attUnuom.getFirstActiveFlagCode();
                    }
                    prodCurr.putStringVal(eProduct.UNUOM, strUnuom);
                    prodCurr.putStringVal(eProduct.UNUOM_FC, strUnuom_fc);
                }
                // PROJECT ID
                if (eiDetail.getEntityType().equals("PR")) {
                    prodCurr.putIntVal(eProduct.PROJECTID, eiDetail.getEntityID());
                }

                // For Certain Entities, we only want to retain "Stub" information
                if (!eProductProperties.isProductDetailEntity(m_ed.getSourceActionItem().getActionItemKey(), strDetailEntityType)) {
                    //debug("isProductDetailEntity==false for " + strDetailEntityType);
                    eProductDetail prodDetail =
                        eRecordCollection.getRecordCollection().getProductDetail(
                            prodCurr,
                            eRecordCollection.FROM_PDH,
                            strDetailEntityType,
                            iDetailEntityID,
                            -1,
                            -1,
                            -1,
                            -1,
                            strChildPath,
                            eProductDetail.NO_COLUMN_VAL,
                            eProductDetail.NO_COLUMN_VAL,
                            eProductDetail.NO_COLUMN_VAL,
                            eProductDetail.NO_COLUMN_VAL,
                            strDetailPublishFlag,
                            getNow(),
                            NEGATIVE_99);
                    prodCurr.putProductDetail(_db, prodDetail);
                    continue RDRS_LOOP;
                }

                buildProductDetail(_db, prodCurr, eiDetail, strDetailEntityType, iDetailEntityID, strChildPath, strDetailPublishFlag, bIsRel);
                prodCurr.updateSequences(_db);
                prodCurr.processInBoundRules(_db);
            }
        }

        // 'fix' any incomplete Product records....
        if (prodCurr == null) {

        } else if (!isSkipProductCheck() && (prodCurr.getIntVal(eProduct.PROJECTID) == eTableRecord.NO_INT_VAL)) {
            eDocAdapter eda = null;
            m_ed = new eDoc(_db, getProfile(), m_ed.getSourceActionItem(), eiRoot.getEntityType(), eiRoot.getEntityID(), getNow(), m_egRoot, m_elEGLCache);
            m_egRoot = m_ed.getRootEntityGroup();
            m_elEGLCache = m_ed.getEntityGroup();
            eda = new eDocAdapter(m_ed, m_epu);
            aProds = eda.getNextProduct(_db);
        } else {
            aProds = new eProduct[vctProds.size()];
            vctProds.copyInto(aProds);
        }
        // Are we done yet?
        if (m_iRdrsPtr >= (rdrs.getRowCount() - 1)) {
            m_bDone = true;
        }
        return aProds;
    }

    /**
    * lets store EntityItems in a hashtable so we dont have to create em all the time...
    */
    private final EntityItem getEntityItem(Database _db, EntityGroup _egParent, String _strEntityType, int _iEntityID) throws SQLException, MiddlewareException {
        // get rid of some entityitems if there are too many
        String strKey = _strEntityType + _iEntityID;
        EntityItem ei = new EntityItem(_egParent, getProfile(), _db, _strEntityType, _iEntityID);

        if (m_elEntityItems.size() > MAX_ENTITYITEMS_STORED) {
            for (int i = 0; i < (MAX_ENTITYITEMS_STORED / 2); i++) {
                m_elEntityItems.remove(0);
            }
        }
        if (!_strEntityType.equals(getRootType())) {
            if (m_elEntityItems.get(strKey) != null) {
                return (EntityItem) m_elEntityItems.get(strKey);
            }
        }
        if (!_strEntityType.equals(getRootType())) {
            m_elEntityItems.put(ei);
        }
        return ei;
    }

    /**
    * Build all our ProductDetail Objects
    */
    private final void buildProductDetail(
        Database _db,
        eProduct _prodCurr,
        EntityItem _eiDetail,
        String _strDetailEntityType,
        int _iDetailEntityID,
        String _strChildPath,
        String _strDetailPublishFlag,
        boolean _bIsRel)
        throws Exception {

        String strAttValueLT = null;
        String strFlagCodeSingle = null;
        String strAttValueSingle = null;
        String strFlagCodeMulti = null;
        String strAttValueMulti = null;
  
        eProductDetail prodDetailMulti = null;
        eProductDetail prodDetailLT = null;
        eProductDetail prodDetailSingle = null;

        MetaFlag[] aMf = null;
                
        String strChildType = _eiDetail.getEntityType();
        
        ATT_LOOP : 
        for (int i = 0; i < _eiDetail.getAttributeCount(); i++) {
            EANAttribute att = _eiDetail.getAttribute(i);
            EANMetaAttribute metaAtt = att.getMetaAttribute();
            String strAttCode = att.getAttributeCode();
            // if this is a relator, tack on entitytype to attribute code...
            if (_bIsRel) {
                strAttCode = strChildType + "." + strAttCode;
            }
            // if this is a root entity --> we might need to include some detail
            if (strChildType.equals(getRootType())) {
                //debug("Processing detail for Root Type:" + getRootType());
                if (!eProductProperties.isRootProductDetailAttribute(getVEName(), getRootType(), strAttCode)) {
                    continue ATT_LOOP;
                }
            }

            switch (metaAtt.getAttributeTypeInt()) {

            case EANMetaAttribute.IS_TEXT :
            case EANMetaAttribute.IS_SINGLE :
            case EANMetaAttribute.IS_STATUS :
            case EANMetaAttribute.IS_ABR :

                strFlagCodeSingle = (metaAtt.isText() ? eProductDetail.NO_COLUMN_VAL : ((EANFlagAttribute) att).getFirstActiveFlagCode());
                strAttValueSingle = att.toString();

                prodDetailSingle =
                    eRecordCollection.getRecordCollection().getProductDetail(
                        _prodCurr,
                        eRecordCollection.FROM_PDH,
                        _strDetailEntityType,
                        _iDetailEntityID,
                        -1,
                        -1,
                        -1,
                        -1,
                        _strChildPath,
                        strAttCode,
                        att.getMetaAttribute().getAttributeType(),
                        strFlagCodeSingle,
                        strAttValueSingle,
                        _strDetailPublishFlag,
                        getNow(),
                        NEGATIVE_99);
                _prodCurr.putProductDetail(_db, prodDetailSingle);
                break;

            case EANMetaAttribute.IS_MULTI :

                // One ProductDetail for each MultiFlag
                aMf = (MetaFlag[]) ((MultiFlagAttribute) att).get();
                
                MF_LOOP : 
                for (int k = 0; k < aMf.length; k++) {
                    MetaFlag mf = aMf[k];
                    if (!mf.isSelected()) {
                        continue MF_LOOP;
                    }

                    strFlagCodeMulti = mf.getFlagCode();
                    strAttValueMulti = mf.getLongDescription();
                    prodDetailMulti =
                        eRecordCollection.getRecordCollection().getProductDetail(
                            _prodCurr,
                            eRecordCollection.FROM_PDH,
                            _strDetailEntityType,
                            _iDetailEntityID,
                            -1,
                            -1,
                            -1,
                            -1,
                            _strChildPath,
                            strAttCode,
                            att.getMetaAttribute().getAttributeType(),
                            strFlagCodeMulti,
                            strAttValueMulti,
                            _strDetailPublishFlag,
                            getNow(),
                            NEGATIVE_99);
                    _prodCurr.putProductDetail(_db, prodDetailMulti);
                }
                break;

            case EANMetaAttribute.IS_LONGTEXT :

                strAttValueLT = att.toString();
                prodDetailLT =
                    eRecordCollection.getRecordCollection().getProductDetail(
                        _prodCurr,
                        eRecordCollection.FROM_PDH,
                        _strDetailEntityType,
                        _iDetailEntityID,
                        -1,
                        -1,
                        -1,
                        -1,
                        _strChildPath,
                        strAttCode,
                        strAttValueLT,
                        _strDetailPublishFlag,
                        getNow(),
                        eRecordCollection.FROM_PDH,
                        NEGATIVE_99);
                _prodCurr.putProductDetail(_db, prodDetailLT);
                break;

            case EANMetaAttribute.IS_BLOB :

                if (!isSkipBlob()) {
                    eProductDetail prodDetailBlob = null;
                    COM.ibm.opicmpdh.objects.Blob blob = null;

                    String strValFromBlob = ((BlobAttribute) att).getValFrom().replace(' ', '-').replace(':', '.');
                    String strAttFileName = ((BlobAttribute) att).getBlobFileName();
                    byte[] baVal = new byte[0];
                    if (strValFromBlob.compareTo(getLastRuntime()) > 0
                        || isProcessOneEntity()
                        || !blobExistsInODS(_db, _strDetailEntityType, _iDetailEntityID, att.getAttributeCode(), strAttFileName)) {
                        baVal = ((BlobAttribute) att).getBlobValue(null, _db);
                    }
                    blob =
                        new COM.ibm.opicmpdh.objects.Blob(
                            getProfile().getEnterprise(),
                            _strDetailEntityType,
                            _iDetailEntityID,
                            strAttCode,
                            baVal,
                            strAttFileName,
                            getProfile().getReadLanguage().getNLSID());

                    prodDetailBlob =
                        eRecordCollection.getRecordCollection().getProductDetail(
                            _prodCurr,
                            eRecordCollection.FROM_PDH,
                            _strDetailEntityType,
                            _iDetailEntityID,
                            -1,
                            -1,
                            -1,
                            -1,
                            _strChildPath,
                            strAttCode,
                            strAttFileName,
                            blob,
                            _strDetailPublishFlag,
                            getNow(),
                            eRecordCollection.FROM_PDH,
                            NEGATIVE_99);
                    _prodCurr.putProductDetail(_db, prodDetailBlob);
                }
                break;

            default :

                continue ATT_LOOP;

            }
        }
    }

    private final boolean blobExistsInODS(Database _db, String _strEntityType, int _iEntityID, String _strAttCode, String _strAttFileName) throws Exception {
        boolean bExists = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;

        try {
            ps = m_epu.getPreparedStatementCollection().getPS(PreparedStatementCollection.BLOB_EXISTS_QUERY);
            if (ps == null) {
                String strStatement =
                    "SELECT count(*) FROM "
                + ODS_SCHEMA
                + ".PRODUCTDETAILBLOB "
                + " WHERE  ENTERPRISE = ? AND "
                + " CHILDTYPE = ? AND "
                + " CHILDID = ? AND "
                + " ATTRIBUTECODE = ? AND "
                + " ATTRIBUTEFILENAME = ?";

                ps = _db.getODSConnection().prepareStatement(strStatement);
                m_epu.getPreparedStatementCollection().putPS(PreparedStatementCollection.BLOB_EXISTS_QUERY, ps);

            }
            ps.clearParameters();
            ps.setString(1, getProfile().getEnterprise());
            ps.setString(2, _strEntityType);
            ps.setInt(3, _iEntityID);
            ps.setString(4, _strAttCode);
            ps.setString(5, _strAttFileName);

            rs = ps.executeQuery();
            rdrs = new ReturnDataResultSet(rs);
            if (rdrs.getRowCount() > 0) {
                int iCount = rdrs.getColumnInt(0, 0);
                if (iCount > 0) {
                    bExists = true;
                }
            }
            rs.close();
            ps.close();
            rs = null;
            ps = null;

        } catch (SQLException se) {
            _db.debug(D.EBUG_ERR, "blobExistsInODS:ERROR:" + se.getMessage());
            System.exit(-1);
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
        }
        return bExists;
    }

    /**
    * Let's pull GBLI.GAMAP into memory
    */
    private final void populateGAMAP(Database _db) throws Exception {

        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        ReturnDataResultSet rdrs1 = null;
        ReturnDataResultSet rdrs2 = null;

        int iCount = 0;

        String strCountSQL = "SELECT INT(COUNT(*)) from " + ODS_SCHEMA + ".GAMAP";
        String strSQL = "SELECT RTRIM(GENAREANAME_FC), RTRIM(CHAR(NLSID)), RTRIM(CODE) from " + ODS_SCHEMA + ".GAMAP";

        try {

            ps1 = _db.getODSConnection().prepareStatement(strCountSQL);
            rs1 = ps1.executeQuery();
            rdrs1 = new ReturnDataResultSet(rs1);

        } finally {
            rs1.close();
            ps1.close();
            rs1 = null;
            ps1 = null;
        }

        iCount = rdrs1.getColumnInt(0, 0);
        m_hashGAMAP = new Hashtable(iCount);

        try {
            ps1 = _db.getODSConnection().prepareStatement(strSQL);
            rs1 = ps1.executeQuery();
            rdrs2 = new ReturnDataResultSet(rs1);
        } finally {
            rs1.close();
            ps1.close();
            rs1 = null;
            ps1 = null;
        }

        for (int i = 0; i < rdrs2.getRowCount(); i++) {
            String strGenAreaName_fc = rdrs2.getColumn(i, 0);
            String strNLSID = rdrs2.getColumn(i, 1);
            String strCode = rdrs2.getColumn(i, 2);
            m_hashGAMAP.put(strGenAreaName_fc, new String[] { strNLSID, strCode });
        }
    }

    /**
    * Get NLSID:CountryCode from GBLI.GAMAP
    */
    private final String[] getCountryInfo(String _strGenArea_fc) {
        return (String[]) m_hashGAMAP.get(_strGenArea_fc);
    }

    private String getRootType() {
        return m_epu.getRootType();
    }

    private String getVEName() {
        return m_epu.getVEName();
    }

    private Profile getProfile() {
        return m_ed.getProfile();
    }

    private boolean isProcessOneEntity() {
        return m_epu.isProcessOneEntity();
    }

    private String getNow() {
        return m_epu.getNow();
    }

    private boolean isSkipBlob() {
        return m_epu.isSkipBlob();
    }

    private String getLastRuntime() {
        return m_epu.getLastRuntime();
    }

    private boolean isSkipProductCheck() {
        return false;
    }

}
