//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANMetaFoundation.java,v $
// Revision 1.53  2005/02/28 19:43:53  dave
// more Jtest fixes from over the weekend
//
// Revision 1.52  2005/02/15 18:42:24  dave
// More JTest cleanup
//
// Revision 1.51  2005/02/14 17:18:34  dave
// more jtest fixing
//
// Revision 1.50  2005/02/10 22:40:16  dave
// trapping null pointer
//
// Revision 1.49  2004/10/21 16:49:54  dave
// trying to share compartor
//
// Revision 1.48  2004/01/14 18:43:44  dave
// also not allowing to post of blank short and
// long descriptions
//
// Revision 1.47  2004/01/13 19:54:41  dave
// space Saver Phase II  m_hsh1 and m_hsh2 created
// as needed instead of always there
//
// Revision 1.46  2004/01/13 17:56:33  dave
// more squeezing
//
// Revision 1.45  2004/01/12 22:37:34  dave
// more squeezing
//
// Revision 1.44  2003/09/15 18:34:06  dave
// misc fixes and usenglish only flag for flag support
//
// Revision 1.43  2003/03/27 23:07:21  dave
// adding some timely commits to free up result sets
//
// Revision 1.42  2002/12/16 18:38:46  gregg
// return a boolean in updatePdhMeta method indicating whether any database updates were performed.
//
// Revision 1.41  2002/12/12 20:32:20  gregg
// made updatePdhMetaDescriptions, expirePdhMetDescriptions methods public (were protected)
//
// Revision 1.40  2002/10/17 23:37:40  gregg
// updatePdhMetadescriptions - only insert into current nlsid if new
//
// Revision 1.39  2002/10/17 22:27:39  gregg
// update/expirePdhMetaDescription methods
//
// Revision 1.38  2002/05/21 16:50:53  gregg
// HtmlDisplayable interface moved up from EANMetaFoundation into parent EANFoundation class
// to encompass both meta & data objects
//
// Revision 1.37  2002/05/20 17:27:37  gregg
// implement HtmlDisplayable interface
//
// Revision 1.36  2002/03/19 00:39:07  dave
// more syntax fixes
//
// Revision 1.35  2002/02/13 22:47:59  dave
// more rearranging in the abstract layer
//
// Revision 1.34  2002/02/02 20:03:06  dave
// ensuring that either a parent exists or a profile exists
//
// Revision 1.33  2002/02/01 17:58:13  dave
// more syntax fixes
//
// Revision 1.32  2002/02/01 17:36:02  dave
// more fixes
//
// Revision 1.31  2002/02/01 17:26:04  dave
// missing import statement
//
// Revision 1.30  2002/02/01 03:16:51  dave
// reworking how Things get implementd for the local text management
// and editing
//
// Revision 1.29  2002/01/31 22:57:19  dave
// more Foundation Cleanup
//
// Revision 1.28  2002/01/31 22:28:20  dave
// more Foundation changes
//
// Revision 1.27  2002/01/31 21:32:22  dave
// more mass abstract changes
//
// Revision 1.26  2002/01/31 20:49:57  dave
// more fixes
//
// Revision 1.25  2002/01/31 20:32:12  dave
// fixes to Foundation Objects
//
// Revision 1.24  2002/01/31 17:58:54  dave
// Rearranging Abrstract levels for more consistiency
//
// Revision 1.23  2002/01/21 21:54:16  dave
// syntax fixes
//
// Revision 1.22  2002/01/21 21:45:55  dave
// more fixes for NLSItem to Profile
//
// Revision 1.21  2002/01/21 21:34:41  dave
// more fixes for NLSItem migration
//
// Revision 1.20  2002/01/21 21:23:29  dave
// more NLSItem to Profile simplication steps
//
// Revision 1.19  2002/01/21 20:59:50  dave
// bit the bullet and use profile to imply NLSItem
//
// Revision 1.18  2002/01/21 20:18:54  dave
// insertion of MetaLinkGroup to manage MetaLinking
//
// Revision 1.17  2002/01/17 18:56:31  dave
// misc fixes for compile
//
// Revision 1.16  2002/01/17 18:33:54  dave
// misc adjustments to help cloning and copying of structures
// in e-announce
//
// Revision 1.15  2001/10/04 20:29:40  dave
// serveral new adds and renames
//
// Revision 1.14  2001/08/09 18:30:04  dave
// syntax
//
// Revision 1.13  2001/08/09 18:04:30  dave
// first attempt at a table model
//
// Revision 1.12  2001/08/08 22:55:55  dave
// finishing touches on trace for displays
//
// Revision 1.11  2001/08/08 21:16:01  dave
// more tracing and cleanup...
//
// Revision 1.10  2001/08/08 20:57:11  dave
// tracking and tracing additions on RequestSearch API
//
// Revision 1.9  2001/08/08 17:41:45  dave
// more generalization is prep for table model on Search API
//
// Revision 1.8  2001/08/03 17:12:58  dave
// fixes to Association object
//
// Revision 1.7  2001/08/03 17:02:33  dave
// Building up the Association Object
//
// Revision 1.6  2001/08/02 23:52:34  dave
// syntax fixes
//
// Revision 1.5  2001/08/01 23:28:19  dave
// final syntax
//
// Revision 1.4  2001/08/01 23:24:46  dave
// sintax
//
// Revision 1.3  2001/08/01 23:10:50  dave
// general syntax
//
// Revision 1.2  2001/08/01 23:02:43  dave
// generic object to house meta entity definition
//
// Revision 1.1  2001/08/01 22:10:10  dave
// more foundation work
//
//
package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.transactions.NLSItem;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;

/**
* This is the basis for all eannounce MetaObjects
* @author David Bigelow
* @version @date
*/
public abstract class EANMetaFoundation extends EANFoundation implements EANMeta {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * EANMetaFoundation
     *
     * @param _mf
     * @param _prof
     * @param _s
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public EANMetaFoundation(EANFoundation _mf, Profile _prof, String _s) throws MiddlewareRequestException {
        super(_mf, _prof, _s);
    }

    /**
     * (non-Javadoc)
     * putShortDescription
     *
     * @see COM.ibm.eannounce.objects.EANMeta#putShortDescription(java.lang.String)
     */
    public void putShortDescription(String _s) {
        if (_s.trim().length() == 0) {
            return;
        }
        if (m_hsh2 == null) {
            m_hsh2 = new Hashtable();
        }
        super.m_hsh2.put("" + getNLSID(), new NLSString(getNLSID(), _s));
    }

    /**
     * (non-Javadoc)
     * putShortDescription
     *
     * @see COM.ibm.eannounce.objects.EANMeta#putShortDescription(int, java.lang.String)
     */
    public void putShortDescription(int _i, String _s) {
        if (_s.trim().length() == 0) {
            return;
        }
        if (m_hsh2 == null) {
            m_hsh2 = new Hashtable();
        }
        super.m_hsh2.put("" + _i, new NLSString(_i, _s));
    }

    /**
     * (non-Javadoc)
     * getShortDescription
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getShortDescription()
     */
    public String getShortDescription() {
        String strKey = "" + getNLSID();
        NLSString nstrAnswer = null;

        if (m_hsh2 == null) {
            return "";
        }
        nstrAnswer = (NLSString) super.m_hsh2.get(strKey);
        if (nstrAnswer != null) {
            return nstrAnswer.toString();
        }
        if (getNLSID() == 1) {
            return "";
        }
        // Go get NLSID1
        nstrAnswer = (NLSString) super.m_hsh2.get("1");
        if (nstrAnswer != null) {
            return nstrAnswer.toString();
        }
        return "";
    }

    /**
     * (non-Javadoc)
     * putLongDescription
     *
     * @see COM.ibm.eannounce.objects.EANMeta#putLongDescription(java.lang.String)
     */
    public void putLongDescription(String _s) {
        int iNLS = getNLSID();
        if (_s == null) {
            return;
        }
        if (_s.trim().length() == 0) {
            return;
        }
        if (m_hsh1 == null) {
            m_hsh1 = new Hashtable();
        }
        super.m_hsh1.put("" + iNLS, new NLSString(iNLS, _s));
    }

    /**
     * (non-Javadoc)
     * putLongDescription
     *
     * @see COM.ibm.eannounce.objects.EANMeta#putLongDescription(int, java.lang.String)
     */
    public void putLongDescription(int _i, String _s) {
        if (_s == null) {
            return;
        }
        if (_s.trim().length() == 0) {
            return;
        }
        if (m_hsh1 == null) {
            m_hsh1 = new Hashtable();
        }
        super.m_hsh1.put("" + _i, new NLSString(_i, _s));
    }

    /**
     * getLongDescription
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public String getLongDescription(int _i) {
        String strKey = null;
        NLSString nstrAnswer = null;
        if (m_hsh1 == null) {
            return "";
        }

        strKey = "" + _i;
        nstrAnswer = (NLSString) super.m_hsh1.get(strKey);

        if (nstrAnswer != null) {
            return nstrAnswer.toString();
        }
        if (_i == 1) {
            return "";
        }
        nstrAnswer = (NLSString) super.m_hsh1.get("1");
        if (nstrAnswer != null) {
            return nstrAnswer.toString();
        }
        return "";
    }

    /**
     * (non-Javadoc)
     * getLongDescription
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getLongDescription()
     */
    public String getLongDescription() {
        // This used put1 and put3 if local editing is enabled for this meta object
        String strKey = null;
        NLSString nstrAnswer = null;

        if (m_hsh1 == null) {
            return "";
        }

        strKey = "" + getNLSID();
        nstrAnswer = (NLSString) super.m_hsh1.get(strKey);
        if (nstrAnswer != null) {
            return nstrAnswer.toString();
        }
        if (getNLSID() == 1) {
            return "";
        }
        // Go get NLSID1
        nstrAnswer = (NLSString) super.m_hsh1.get("1");
        if (nstrAnswer != null) {
            return nstrAnswer.toString();
        }
        return "";
    }

    /**
     * copyDescription
     *
     * @param _mf
     *  @author David Bigelow
     */
    protected void copyDescription(EANMetaFoundation _mf) {
        // Wipe out existing
        m_hsh1 = null;
        m_hsh2 = null;

        // Can we get a hashset?
        if (_mf.m_hsh1 != null) {
            Enumeration e = _mf.m_hsh1.keys();
            m_hsh1 = new Hashtable();
            while (e.hasMoreElements()) {
                NLSString nlsstr = (NLSString) _mf.m_hsh1.get(e.nextElement());
                super.m_hsh1.put("" + nlsstr.m_iNLS, new NLSString(nlsstr.m_iNLS, nlsstr.toString()));
            }
        }
        if (_mf.m_hsh2 != null) {
            Enumeration e = _mf.m_hsh2.keys();
            m_hsh2 = new Hashtable();
            while (e.hasMoreElements()) {
                NLSString nlsstr = (NLSString) _mf.m_hsh2.get(e.nextElement());
                super.m_hsh2.put("" + nlsstr.m_iNLS, new NLSString(nlsstr.m_iNLS, nlsstr.toString()));
            }
        }
    }

    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getLongDescription();
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public abstract String dump(boolean _brief);

    // /*
    // * Sets local editing to true
    // * clears out local hashtables
    // */
    //  protected void setLocalEnabled(boolean _b) {
    //      m_bLocalEnabled = _b;
    //      if (_b) {
    //          m_hshLocal1 = new Hashtable();
    //          m_hshLocal2 = new Hashtable();
    //      }
    //  }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////  Meta Update Methods -- take care of updating any MetaDescription data to the PDH -- NLS sensitive.    //
    ////  Only perform updates if a description for a particular NLS has changed.                               //
    ////  Descriptions for subclasses should be updated through these methods.                                  //
    ////  Note: we must know the corresponding descriptionType, descriptionClass in the MetaDescription table   //
    ////        (descType is usually getKey(), but not necessarily).                                                        //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Update pertinent records for all nls's in the profile
     *
     * @return boolean
     * @param _db
     * @param _strDescType
     * @param _strDescClass
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException 
     */
    public boolean updatePdhMetaDescriptions(Database _db, String _strDescType, String _strDescClass) throws MiddlewareException {
        return updatePdhMetaDescriptions(_db, _strDescType, _strDescClass, false);
    }

    /**
     * Will expire all nls records for indicated descType, descType in the MetaDescriptionTable
     *
     * @return boolean
     * @param _db
     * @param _strDescType
     * @param _strDescClass
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException 
     */
    public boolean expirePdhMetaDescriptions(Database _db, String _strDescType, String _strDescClass) throws MiddlewareException {
        return updatePdhMetaDescriptions(_db, _strDescType, _strDescClass, true);
    }

    private boolean updatePdhMetaDescriptions(Database _db, String _strDescType, String _strDescClass, boolean _bExpire) throws MiddlewareException {
        boolean bChanged = false;
        try {
            String strNow = _db.getDates().getNow();
            String strForever = _db.getDates().getForever();
            String strEnterprise = getProfile().getEnterprise();
            //we must update ALL nlsId's!
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            try {
                rs = _db.callGBL7511(new ReturnStatus(-1), strEnterprise, _strDescType, _strDescClass);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            // EXPIRE
            if (_bExpire) {
                for (int row = 0; row < rdrs.getRowCount(); row++) {
                    int iNLSID_db = rdrs.getColumnInt(row, 0);
                    String strShortDesc_db = rdrs.getColumn(row, 1);
                    String strLongDesc_db = rdrs.getColumn(row, 2);
                    _db.debug(D.EBUG_SPEW, "gbl7511 answers: " + iNLSID_db + ":" + strShortDesc_db + ":" + strLongDesc_db);
                    new MetaDescriptionRow(getProfile(), _strDescType, _strDescClass, strShortDesc_db, strLongDesc_db, iNLSID_db, strNow, strNow, strNow, strNow, 2).updatePdh(_db);
                    bChanged = true;
                }
            } else { //UPDATE
                //need to save this
                NLSItem nlsItemSaved = getProfile().getReadLanguage();
                for (int i = 0; i < getProfile().getReadLanguages().size(); i++) {
                    String strShortDesc_this = null;
                    String strLongDesc_this = null;
                    NLSItem nlsItem = (NLSItem) getProfile().getReadLanguages().elementAt(i);
                    int iNLSID_this = nlsItem.getNLSID();
                    boolean bFound = false;
                    boolean bUpdate = false;

                    getProfile().setReadLanguage(nlsItem);
                    strShortDesc_this = getShortDescription();
                    strLongDesc_this = getLongDescription();

                    //find a matching record in the pdh
                    for (int row = 0; row < rdrs.getRowCount(); row++) {
                        int iNLSID_db = rdrs.getColumnInt(row, 0);
                        if (iNLSID_this == iNLSID_db) {
                            String strShortDesc_db = rdrs.getColumn(row, 1);
                            String strLongDesc_db = rdrs.getColumn(row, 2);
                            // only update if changed
                            if (!strShortDesc_db.equals(strShortDesc_this) || !strLongDesc_db.equals(strLongDesc_this)) {
                                bUpdate = true;
                            }
                            bFound = true;
                            i = rdrs.getRowCount(); //break
                            continue;
                        }
                    }
                    if (bUpdate || (!bFound && iNLSID_this == nlsItemSaved.getNLSID())) { //if desc for THIS PROFILES nls doesnt exist in pdh -> insert it
                        new MetaDescriptionRow(getProfile(), _strDescType, _strDescClass, strShortDesc_this, strLongDesc_this, iNLSID_this, strNow, strForever, strNow, strForever, 2).updatePdh(_db);
                        bChanged = true;
                    }
                }
                //restore
                getProfile().setReadLanguage(nlsItemSaved);
            }
        } catch (Exception exc) {
            _db.debug(D.EBUG_ERR, "Exception in EANMetaFoundation.updatePdhMeta():" + exc.toString());
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return bChanged;
    }

}
