//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: GeneralAreaList.java,v $
// Revision 1.21  2005/03/03 21:12:25  dave
// Jtest cleanup
//
// Revision 1.20  2003/06/11 23:26:19  joan
// move changes
//
// Revision 1.19  2003/05/20 18:31:15  dave
// streamlining abit to remove the looping
//
// Revision 1.18  2003/01/30 00:31:07  joan
// remove System.out
//
// Revision 1.17  2003/01/29 22:24:28  joan
// adjust code
//
// Revision 1.16  2003/01/29 21:43:21  joan
// debug
//
// Revision 1.15  2003/01/29 17:56:14  joan
// make some adjustments
//
// Revision 1.14  2003/01/29 00:12:10  joan
// add getGeneralAreaList
//
// Revision 1.13  2003/01/28 21:20:01  joan
// catch exception
//
// Revision 1.12  2003/01/28 21:10:01  joan
// put more work
//
// Revision 1.11  2003/01/28 17:31:25  joan
// add GAItem and GAGroup
//
// Revision 1.10  2003/01/27 19:13:50  joan
// remove system.out
//
// Revision 1.9  2003/01/25 00:05:16  joan
// fix bug
//
// Revision 1.8  2003/01/24 23:05:36  joan
// adjust gen tree
//
// Revision 1.7  2003/01/24 22:33:09  joan
// fix gen tree
//
// Revision 1.6  2003/01/24 22:15:10  joan
// fix bugs
//
// Revision 1.5  2003/01/24 21:38:01  joan
// add gen tree
//
// Revision 1.4  2003/01/24 20:31:59  joan
// remove System.out
//
// Revision 1.3  2003/01/24 19:17:20  joan
// adjust dump method
//
// Revision 1.2  2003/01/24 18:43:06  joan
// fix wg
//
// Revision 1.1  2003/01/24 17:59:48  joan
// initial load
//

package COM.ibm.eannounce.objects;

import java.util.Hashtable;
import java.util.Vector;
import java.io.PrintWriter;
import java.io.StringWriter;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;

// Exceptions
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.transactions.OPICMList;

import java.sql.SQLException;

/**
 * GeneralAreaList
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GeneralAreaList extends EANMetaEntity {

    private class TreeInfo {
        private EntityItem m_eiGenArea = null;
        private OPICMList m_childList = new OPICMList();

        TreeInfo(EntityItem _ei) {
            m_eiGenArea = _ei;
        }

        public void putChildList(OPICMList _list) {
            m_childList = _list;
        }

        public OPICMList getChildList() {
            return m_childList;
        }
    }

    private EntityList m_el = null;
    private EntityItem m_eiRootGenArea = null;
    private static final String RFAGEOCODE_US = "200";
    private static final String RFAGEOCODE_LA = "201";
    private static final String RFAGEOCODE_CAN = "202";
    private static final String RFAGEOCODE_EMEA = "203";
    private static final String RFAGEOCODE_AP = "204";
    private static final String GENAREATYPE_COUNTRY = "2452";
    private static final int US = 0;
    private static final int LA = 1;
    private static final int CAN = 2;
    private static final int EMEA = 3;
    private static final int AP = 4;

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * GeneralAreaList Constructor
     *
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException 
     */
    public GeneralAreaList(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
        super(null, _prof, Profile.WG_TYPE + _prof.getWGID());

        String strMethod = "GeneralAreaList constructor";

        try {
            
            EntityItem[] aei = new EntityItem[1];
            EntityList el = null;
            EntityGroup egRel = null;

            String strEnterprise = _prof.getEnterprise();
            int iOPWGID = _prof.getOPWGID();

            EntityItem eiWG = new EntityItem(null, _prof, Profile.WG_TYPE, _prof.getWGID());
            ExtractActionItem xai = new ExtractActionItem(null, _db, _prof, "EXTGEOTREE");
            _db.test(xai != null, strMethod + "ExtractActionItem EXTGEOTREE is null.");
            aei[0] = eiWG;
            el = EntityList.getEntityList(_db, _prof, xai, aei);
            egRel = el.getEntityGroup("WGGENA");

            m_el = el;
 
            _db.debug(D.EBUG_SPEW, strMethod + " transaction");
            _db.debug(D.EBUG_SPEW, "GeneralAreaList: Enterprise: " + strEnterprise);
            _db.debug(D.EBUG_SPEW, "GeneralAreaList: OPENID: " + iOPWGID);
            _db.debug(D.EBUG_SPEW, "GeneralAreaList:" + getKey());

            //get Root GENERALAREA
            if (egRel != null) {
                for (int i = 0; i < egRel.getEntityItemCount(); i++) {
                    EntityItem ei = egRel.getEntityItem(i);
                    EntityItem eid = (EntityItem) ei.getDownLink(0);
                    m_eiRootGenArea = eid;
                }
            }

        } catch (RuntimeException rx) {
            StringWriter writer = new StringWriter();
            String x = writer.toString();

            _db.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + " " + rx);
            rx.printStackTrace(new PrintWriter(writer));
            _db.debug(D.EBUG_ERR, "" + x);
            throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
        } finally {
            // Free any statement
            _db.freeStatement();
            _db.isPending();

            // DO NOT FREE THE CONNECTION
            _db.debug(D.EBUG_DETAIL, strMethod + " complete");
        }
    }

    private OPICMList getChildGEO(EntityItem _eiGAParent) {
        EntityGroup eg = m_el.getEntityGroup("GENGENA");
        OPICMList list = new OPICMList();
        if (eg != null) {
            for (int i = 0; i < eg.getEntityItemCount(); i++) {
                EntityItem ei = eg.getEntityItem(i);
                EntityItem eip = (EntityItem) ei.getUpLink(0);
                if (eip != null) {
                    String key = eip.getKey();
                    if (key.equals(_eiGAParent.getKey())) {
                        EntityItem eic = (EntityItem) ei.getDownLink(0);
                        list.put(eic.getKey(), eic);
                    }
                }
            }
        }
        return list;
    }

    /**
     * getEntityList
     *
     * @return
     *  @author David Bigelow
     */
    public EntityList getEntityList() {
        return m_el;
    }

    /**
     * returns WW GENERALAREA
     *
     * @return EntityItem
     */
    public EntityItem getRootGenArea() {
        return m_eiRootGenArea;
    }

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: GeneralAreaList.java,v 1.21 2005/03/03 21:12:25 dave Exp $";
    }

    private String getTab(int _iLevel) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < _iLevel; i++) {
            sb.append("\t");
        }
        return sb.toString();
    }

    /**
     * This method builds GENERALAREA tree structure
     *
     * @return Hashtable
     */
    public Hashtable buildTree() {
        EntityGroup eg = m_el.getEntityGroup("GENERALAREA");
        Hashtable htReturn = new Hashtable();
        if (eg != null) {
            for (int i = 0; i < eg.getEntityItemCount(); i++) {
                EntityItem ei = eg.getEntityItem(i);
                TreeInfo gai = new TreeInfo(ei);
                gai.putChildList(getChildGEO(ei));
                htReturn.put(ei.getKey(), gai);
            }
        } else {
            System.out.println(" EntityGroup GENERALAREA missing");
        }
        return htReturn;
    }

    /**
     * This method returns GENERALAREA tree structure that can be used to display
     *
     * @param     _ei	WW GENERALAREA EntityItem
     * @param     _iLevel	level of _ei
     * @param     _hshHistory	hashtable to keep track of tree node
     * @param     _treeHt	hashtable from buildTree method
     * @return StringBuffer
     */
    public StringBuffer genGenAreaTree(EntityItem _ei, int _iLevel, Hashtable _hshHistory, Hashtable _treeHt) {
        StringBuffer strbResult = new StringBuffer();

        strbResult.append("\n" + getTab(_iLevel) + _iLevel + ":" + _ei.getKey() + ":" + _ei.toString());

        if (!_hshHistory.containsKey(_ei.getKey() + _iLevel)) {
            TreeInfo gai = (TreeInfo) _treeHt.get(_ei.getKey());
            _hshHistory.put(_ei.getKey() + _iLevel, "hi");
            if (gai != null) {
                OPICMList list = gai.getChildList();
                for (int i = 0; i < list.size(); i++) {
                    EntityItem ei = (EntityItem) list.getAt(i);
                    strbResult.append(genGenAreaTree(ei, _iLevel + 1, _hshHistory, _treeHt));
                }
            }
        }

        return strbResult;
    }

    private String[] getGeoAttrCode(EntityItem _ei) {
        String[] aReturn = null;
        Vector vect = new Vector();

        if (_ei == null) {
            return null;
        }
        for (int i = 0; i < _ei.getAttributeCount(); i++) {
            EANAttribute att = _ei.getAttribute(i);
            EANMetaAttribute ma = att.getMetaAttribute();
            if (ma != null && ma.isGeoIndicator()) {
                if (att instanceof EANTextAttribute) {
                    vect.addElement((String) att.get());
                } else if (att instanceof EANFlagAttribute) {
                    MetaFlag[] amf = (MetaFlag[]) att.get();
                    for (int f = 0; f < amf.length; f++) {
                        String strFlagCode = amf[f].getFlagCode();
                        if (amf[f].isSelected()) {
                            vect.addElement(strFlagCode);
                        }
                    }
                }
            }
        }

        aReturn = new String[vect.size()];
        vect.copyInto(aReturn);
        return aReturn;
    }

    private EntityItem[] getEntityItem(String[] _aGeoAttrCode) {

        EntityItem[] aeiReturn = null;
        Vector vect = new Vector();
        EntityGroup eg = m_el.getEntityGroup("GENERALAREA");

        if (eg != null) {
            for (int i = 0; i < _aGeoAttrCode.length; i++) {
                String s = _aGeoAttrCode[i];
                for (int j = 0; j < eg.getEntityItemCount(); j++) {
                    EntityItem ei = eg.getEntityItem(j);
                    // Here we need to
                    EANFlagAttribute att = (EANFlagAttribute) ei.getAttribute("GENAREANAME");
                    if (att != null) {
                        if (att.isSelected(s)) {
                            vect.addElement(ei);
                        }
                    }
                }
            }
        } else {
            System.out.println(" EntityGroup GENERALAREA missing");
        }

        aeiReturn = new EntityItem[vect.size()];
        vect.copyInto(aeiReturn);
        return aeiReturn;
    }

    private boolean isRfaGeo(EntityItem _ei, int _i) {
        String strRfaGeoCode = "";
        String[] aGeoAttrCode = getGeoAttrCode(_ei);
        EntityItem[] aei = getEntityItem(aGeoAttrCode);
        switch (_i) {
        case US :
            strRfaGeoCode = RFAGEOCODE_US;
            break;
        case LA :
            strRfaGeoCode = RFAGEOCODE_LA;
            break;
        case CAN :
            strRfaGeoCode = RFAGEOCODE_CAN;
            break;
        case EMEA :
            strRfaGeoCode = RFAGEOCODE_EMEA;
            break;
        case AP :
            strRfaGeoCode = RFAGEOCODE_AP;
            break;
        default :
            break;
        }

        for (int i = 0; i < aei.length; i++) {
            EntityItem ei = aei[i];
            EANFlagAttribute att = (EANFlagAttribute) ei.getAttribute("RFAGEO");
            if (att != null) {
                if (att.isSelected(strRfaGeoCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * return true if _ei is in RFA GEO EMEA
     *
     * @param     _ei	EntityItem that has attribute associated with GENERALAREA's GENERALNAME
     * @return boolean
     */
    public boolean isRfaGeoEMEA(EntityItem _ei) {
        return isRfaGeo(_ei, EMEA);
    }

    /**
     * return true if _ei is in RFA GEO US
     *
     * @param     _ei	EntityItem that has attribute associated with GENERALAREA's GENERALNAME
     * @return boolean
     */
    public boolean isRfaGeoUS(EntityItem _ei) {
        return isRfaGeo(_ei, US);
    }

    /**
     * return true if _ei is in RFA GEO AP
     *
     * @param     _ei	EntityItem that has attribute associated with GENERALAREA's GENERALNAME
     * @return boolean
     */
    public boolean isRfaGeoAP(EntityItem _ei) {
        return isRfaGeo(_ei, AP);
    }

    /**
     * return true if _ei is in RFA GEO LA
     *
     * @param     _ei	EntityItem that has attribute associated with GENERALAREA's GENERALNAME
     * @return boolean
     */
    public boolean isRfaGeoLA(EntityItem _ei) {
        return isRfaGeo(_ei, LA);
    }

    /**
     * return true if _ei is in RFA GEO CAN
     *
     * @param     _ei	EntityItem that has attribute associated with GENERALAREA's GENERALNAME
     * @return boolean
     */
    public boolean isRfaGeoCAN(EntityItem _ei) {
        return isRfaGeo(_ei, CAN);
    }

    private GeneralAreaGroup getRfaGeoCountries(int _i) {
        String strRfaGeoCode = "";
        EntityGroup eg = m_el.getEntityGroup("GENERALAREA");
        GeneralAreaGroup gagReturn = new GeneralAreaGroup();

        switch (_i) {
        case US :
            strRfaGeoCode = RFAGEOCODE_US;
            break;
        case LA :
            strRfaGeoCode = RFAGEOCODE_LA;
            break;
        case CAN :
            strRfaGeoCode = RFAGEOCODE_CAN;
            break;
        case EMEA :
            strRfaGeoCode = RFAGEOCODE_EMEA;
            break;
        case AP :
            strRfaGeoCode = RFAGEOCODE_AP;
            break;
        default :
            break;
        }

        if (eg != null) {
            for (int j = 0; j < eg.getEntityItemCount(); j++) {
                EntityItem ei = eg.getEntityItem(j);
                EANAttribute attType = ei.getAttribute("GENAREATYPE");
                if (attType != null) {
                    MetaFlag[] amf1 = (MetaFlag[]) attType.get();
                    boolean bCountry = false;
                    for (int f = 0; f < amf1.length; f++) {
                        String strFlagCode = amf1[f].getFlagCode();
                        if (amf1[f].isSelected() && strFlagCode.equals(GENAREATYPE_COUNTRY)) {
                            bCountry = true;
                        }
                    }

                    if (bCountry) {
                        EANAttribute att = ei.getAttribute("RFAGEO");
                        if (att != null) {
                            MetaFlag[] amf = (MetaFlag[]) att.get();
                            for (int f = 0; f < amf.length; f++) {
                                String strFlagCode = amf[f].getFlagCode();
                                if (amf[f].isSelected() && strFlagCode.equals(strRfaGeoCode)) {
                                    try {
                                        gagReturn.putGeneralAreaItem(new GeneralAreaItem(getProfile(), ei));
                                    } catch (MiddlewareRequestException mre) {
                                        mre.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println(" EntityGroup GENERALAREA missing");
        }

        return gagReturn;

    }

    private GeneralAreaGroup getRfaGeoExclusion(EntityItem _ei, int _i) {
        String[] aGeoCode = getGeoAttrCode(_ei);
        EntityItem[] aei = getEntityItem(aGeoCode);
        GeneralAreaGroup gag = getRfaGeoCountries(_i);
        for (int i = 0; i < aei.length; i++) {
            EntityItem ei = aei[i];
            try {
                GeneralAreaItem gai = new GeneralAreaItem(getProfile(), ei);
                gag.removeGeneralAreaItem(gai);
            } catch (MiddlewareRequestException mre) {
                mre.printStackTrace();
            }
        }
        return gag;
    }

    private GeneralAreaGroup getRfaGeoInclusion(EntityItem _ei, int _i) {
        String[] aGeoCode = getGeoAttrCode(_ei);
        EntityItem[] aei = getEntityItem(aGeoCode);
        GeneralAreaGroup gag = getRfaGeoCountries(_i);
        GeneralAreaGroup gagReturn = new GeneralAreaGroup();

        for (int i = 0; i < gag.getGeneralAreaItemCount(); i++) {
            GeneralAreaItem gai = gag.getGeneralAreaItem(i);

            boolean bFound = false;
            for (int j = 0; j < aei.length; j++) {
                EntityItem ei = aei[j];

                if (gai.getKey().equals(ei.getKey())) {
                    bFound = true;
                    break;
                }
            }

            if (bFound) {
                gagReturn.putGeneralAreaItem(gai);
            }
        }

        return gagReturn;
    }

    /**
     * get a group of Country Exclusion for RFA GEO EMEA
     *
     * @param     _ei	EntityItem that has attribute associated with GENERALAREA's GENERALNAME
     * @return GeneralAreaGroup
     */
    public GeneralAreaGroup getRfaGeoEMEAExclusion(EntityItem _ei) {
        return getRfaGeoExclusion(_ei, EMEA);
    }

    /**
     * get a group of Country Exclusion for RFA GEO US
     *
     * @param     _ei	EntityItem that has attribute associated with GENERALAREA's GENERALNAME
     * @return GeneralAreaGroup
     */
    public GeneralAreaGroup getRfaGeoUSExclusion(EntityItem _ei) {
        return getRfaGeoExclusion(_ei, US);
    }

    /**
     * get a group of Country Exclusion for RFA GEO AP
     *
     * @param     _ei	EntityItem that has attribute associated with GENERALAREA's GENERALNAME
     * @return GeneralAreaGroup
     */
    public GeneralAreaGroup getRfaGeoAPExclusion(EntityItem _ei) {
        return getRfaGeoExclusion(_ei, AP);
    }

    /**
     * get a group of Country Exclusion for RFA GEO LA
     *
     * @param     _ei	EntityItem that has attribute associated with GENERALAREA's GENERALNAME
     * @return GeneralAreaGroup
     */
    public GeneralAreaGroup getRfaGeoLAExclusion(EntityItem _ei) {
        return getRfaGeoExclusion(_ei, LA);
    }

    /**
     * get a group of Country Exclusion for RFA GEO CAN
     *
     * @param     _ei	EntityItem that has attribute associated with GENERALAREA's GENERALNAME
     * @return GeneralAreaGroup
     */
    public GeneralAreaGroup getRfaGeoCANExclusion(EntityItem _ei) {
        return getRfaGeoExclusion(_ei, CAN);
    }

    /**
     * get a group of Country Inclusion for RFA GEO EMEA
     *
     * @param     _ei	EntityItem that has attribute associated with GENERALAREA's GENERALNAME
     * @return GeneralAreaGroup
     */
    public GeneralAreaGroup getRfaGeoEMEAInclusion(EntityItem _ei) {
        return getRfaGeoInclusion(_ei, EMEA);
    }

    /**
     * get a group of Country Inclusion for RFA GEO US
     *
     * @param     _ei	EntityItem that has attribute associated with GENERALAREA's GENERALNAME
     * @return GeneralAreaGroup
     */
    public GeneralAreaGroup getRfaGeoUSInclusion(EntityItem _ei) {
        return getRfaGeoInclusion(_ei, US);
    }

    /**
     * get a group of Country Inclusion for RFA GEO AP
     *
     * @param     _ei	EntityItem that has attribute associated with GENERALAREA's GENERALNAME
     * @return GeneralAreaGroup
     */
    public GeneralAreaGroup getRfaGeoAPInclusion(EntityItem _ei) {
        return getRfaGeoInclusion(_ei, AP);
    }

    /**
     * get a group of Country Inclusion for RFA GEO LA
     *
     * @param     _ei	EntityItem that has attribute associated with GENERALAREA's GENERALNAME
     * @return GeneralAreaGroup
     */
    public GeneralAreaGroup getRfaGeoLAInclusion(EntityItem _ei) {
        return getRfaGeoInclusion(_ei, LA);
    }

    /**
     * get a group of Country Inclusion for RFA GEO CAN
     *
     * @param     _ei	EntityItem that has attribute associated with GENERALAREA's GENERALNAME
     * @return GeneralAreaGroup
     */
    public GeneralAreaGroup getRfaGeoCANInclusion(EntityItem _ei) {
        return getRfaGeoInclusion(_ei, CAN);
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("GeneralAreaList:" + getKey());
        if (!_bBrief) {
            EntityGroup eg = m_el.getEntityGroup("GENERALAREA");
            if (eg != null) {
                for (int i = 0; i < eg.getEntityItemCount(); i++) {
                    EntityItem ei = eg.getEntityItem(i);
                    strbResult.append("\ni: " + i + ":" + ei.getKey() + ":" + ei.toString());
                }
            }
        }
        return new String(strbResult);
    }

}
