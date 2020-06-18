//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANMetaFlagAttribute.java,v $
// Revision 1.71  2012/11/08 21:30:54  wendy
// rollback if error instead of commit
//
// Revision 1.70  2010/11/08 18:44:04  wendy
// Reuse string objects to reduce memory requirements
//
// Revision 1.69  2009/12/31 12:57:00  wendy
// check for null before rs.close
//
// Revision 1.68  2009/05/11 15:29:50  wendy
// Support dereference for memory release
//
// Revision 1.67  2005/02/28 19:43:53  dave
// more Jtest fixes from over the weekend
//
// Revision 1.66  2004/10/21 16:49:53  dave
// trying to share compartor
//
// Revision 1.65  2004/01/15 00:11:54  dave
// final sqeeze on meta flag
//
// Revision 1.64  2004/01/14 23:41:40  dave
// more reverselookup stuff
//
// Revision 1.63  2004/01/14 22:56:03  dave
// more trimming.. targeting reverse lookup function
//
// Revision 1.62  2004/01/13 22:41:11  dave
// more space squeezing
//
// Revision 1.61  2003/10/30 02:10:00  dave
// more profile fixing
//
// Revision 1.60  2003/10/30 01:43:29  dave
// null pointer fix
//
// Revision 1.59  2003/10/30 00:56:16  dave
// more profile fixes
//
// Revision 1.58  2003/10/30 00:43:31  dave
// fixing all the profile references
//
// Revision 1.57  2003/06/20 19:17:20  dave
// needed another import
//
// Revision 1.56  2003/06/20 18:14:25  dave
// new pdg support changes
//
// Revision 1.55  2003/05/11 02:04:40  dave
// making EANlists bigger
//
// Revision 1.54  2003/04/24 18:32:16  dave
// getting rid of traces and system out printlns
//
// Revision 1.53  2003/04/23 15:57:36  dave
// Clean up.. and first add to autoselector
//
// Revision 1.52  2003/04/08 02:38:28  dave
// commit()
//
// Revision 1.51  2003/03/27 23:07:21  dave
// adding some timely commits to free up result sets
//
// Revision 1.50  2002/12/16 19:37:32  gregg
// better bChagned logic in updatePdhMeta method
//
// Revision 1.49  2002/12/16 18:23:35  gregg
// return a boolean in updatePdhMeta method indicating whether any database updates were performed.
//
// Revision 1.48  2002/11/06 19:12:52  gregg
// introduced deep update for updatePdhMeta method
//
// Revision 1.47  2002/11/05 23:51:25  gregg
// some debug stmts in constructor
//
// Revision 1.46  2002/09/25 22:23:17  gregg
// made int getNextFlagCode method protected
//
// Revision 1.45  2002/09/25 22:20:24  gregg
// getNextFlagCode, getNextFlagCodeString methods
//
// Revision 1.44  2002/09/24 20:54:39  dave
// more dump expansion
//
// Revision 1.43  2002/06/03 22:38:53  gregg
// fix for updatePdhMetaFlags (expire)
//
// Revision 1.42  2002/05/31 22:48:34  gregg
// some fixes to updatePdhMetaFlags
//
// Revision 1.41  2002/05/03 18:23:20  gregg
// replcaed gbl6012 w/ gbl8612
//
// Revision 1.40  2002/05/02 17:07:04  gregg
// added new constructor to retreive all NLS flag values
//
// Revision 1.39  2002/04/15 21:16:03  gregg
// updatePdhMeta -> parent is now correctly set to EntityGroup for Att_db
//
// Revision 1.38  2002/04/11 21:39:13  dave
// minor syntax
//
// Revision 1.37  2002/04/11 21:29:44  dave
// using rdrs on metaflag - to attempt to resolve overtrim
//
// Revision 1.36  2002/04/10 23:41:24  dave
// new containsMetaFlag method
//
// Revision 1.35  2002/03/14 17:55:11  gregg
// updatePdh: only compare w/ database objects if !new Attribute
//
// Revision 1.34  2002/03/08 19:42:59  gregg
// bunch of new update stuff
//
// Revision 1.33  2002/03/05 04:58:02  dave
// more dump display cleanup
//
// Revision 1.32  2002/03/01 23:53:45  gregg
// minor change to removeMetaFlag
//
// Revision 1.31  2002/03/01 23:50:41  gregg
// public putMetaFlag(), remove MetaFlag() methods added
//
// Revision 1.30  2002/02/27 20:37:15  dave
// adding state machines and the such to entityGroup
//
// Revision 1.29  2002/02/26 21:43:59  dave
// ensuring I am setting the rs = null after a close
//
// Revision 1.28  2002/02/02 21:39:20  dave
// more  fixes to tighen up import
//
// Revision 1.27  2002/02/01 17:38:40  dave
// more import fixes
//
// Revision 1.26  2002/02/01 01:21:26  dave
// another wave of foundation fixes
//
// Revision 1.25  2002/02/01 00:42:32  dave
// more foundation fixes for passing _prof
//
// Revision 1.24  2001/11/26 18:42:47  dave
// carry forward for metaflag fix
//
// Revision 1.23  2001/10/12 00:52:41  dave
// formating on dump
//
// Revision 1.22  2001/10/12 00:27:05  dave
// fixes
//
// Revision 1.21  2001/10/09 16:02:34  dave
// fixes
//
// Revision 1.20  2001/10/08 20:53:50  dave
// added final touch to MetaFlagAttributeList
//
// Revision 1.19  2001/10/05 23:04:19  dave
// fix
//
// Revision 1.18  2001/10/05 22:56:19  dave
// fixes
//
// Revision 1.17  2001/10/05 22:33:04  dave
// more encapsulation
//
// Revision 1.16  2001/10/04 22:38:26  dave
// more fixes
//
// Revision 1.15  2001/10/04 22:21:14  dave
// fixes
//
// Revision 1.14  2001/10/04 21:04:43  dave
// more fixes
//
// Revision 1.13  2001/10/04 20:58:12  dave
// more massive changes to eannounce objects
//
// Revision 1.12  2001/10/04 20:29:39  dave
// serveral new adds and renames
//
// Revision 1.11  2001/08/22 16:52:50  roger
// Removed author RM
//
// Revision 1.10  2001/08/09 23:56:34  dave
// syntax fix
//
// Revision 1.9  2001/08/09 23:44:23  dave
// added Association Detail to the Table Model
//
// Revision 1.8  2001/08/08 23:28:52  dave
// mods to better describe association vs attribute of
//
// Revision 1.7  2001/08/08 22:47:28  dave
// fix to correct putMetaFlag .. was not putting new entries
//
// Revision 1.6  2001/08/08 22:08:18  dave
// Traces for troubleshooting
//
// Revision 1.5  2001/08/08 21:38:45  dave
// display fixes for debuging aid
//
// Revision 1.4  2001/08/03 17:02:33  dave
// Building up the Association Object
//
// Revision 1.3  2001/08/02 23:52:34  dave
// syntax fixes
//
// Revision 1.2  2001/08/02 23:46:48  dave
// sintax
//
// Revision 1.1  2001/08/02 23:33:04  dave
// Transfered and cleaned up the PDHMetaFlag
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;

import java.util.Enumeration;
import java.util.Hashtable;

/**
*  Manages the EANMetaFlagAttribute Information
*
*@author     David Bigelow
*@created    April 23, 2003
*@version
*@date
*/ 

public abstract class EANMetaFlagAttribute extends EANMetaAttribute {

    // Instance variables
    /**
    *@serial
    */
    final static long serialVersionUID = 1L;
    private boolean m_bFiltered = false;
    private EANList m_el = null;

    /**
     * FIELD NAME
     */
    protected Hashtable m_hshReverseLookUp = null;

    protected void dereference() {
    	super.dereference();
    	if (m_el !=null){
    		for (int i=0; i<m_el.size(); i++){
    			MetaFlag mt = (MetaFlag) m_el.getAt(i);
    			if (mt != null){
    				mt.dereference();
    			}
    		}
    		m_el.clear();
    		m_el = null;
    	}
    	if (m_hshReverseLookUp!= null){
        	Enumeration e = m_hshReverseLookUp.elements();
        	while (e.hasMoreElements()) {
        		MetaFlag uag = (MetaFlag) e.nextElement();
        		uag.dereference();
        	}
        	m_hshReverseLookUp.clear();
        	m_hshReverseLookUp = null;
        }
    }

    //for generating flag codes
    /**
    *  Description of the Field
    */
    public final static String NEXT_FLAG_CODE_KEY = "NEXT_AVAIL_FLAG_CODE";

    /**
    *  Main method which performs a simple test of this class
    *
    *@param  arg  Description of the Parameter
    */
    public static void main(String arg[]) {
    }

    /**
    *  Creates the PDHMetaFlag with the Default NLSID and Value
    *
    *@param  _s1                             is the attributecode that represents
    *      this object
    *@param  _emf                            Description of the Parameter
    *@param  _db                             Description of the Parameter
    *@param  _prof                           Description of the Parameter
    *@exception  SQLException                Description of the Exception
    *@exception  MiddlewareException         Description of the Exception
    *@exception  MiddlewareRequestException  Description of the Exception
    */
    public EANMetaFlagAttribute(EANMetaFoundation _emf, Database _db, Profile _prof, String _s1) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _s1);
        m_el = new EANList();
        initEANMetaFlagAttribute(_emf, _db, getProfile(), _s1, false,null);
    }

    /**
    *  Creates the PDHMetaFlag with ALL available NLSIDs and corresponding values
    *
    *@param  _s1                             is the attributecode that represents
    *      this object
    *@param  _bAllNls                        is whether or not we will pull
    *      values/descs for all nls's
    *@param  _emf                            Description of the Parameter
    *@param  _db                             Description of the Parameter
    *@param  _prof                           Description of the Parameter
    *@exception  SQLException                Description of the Exception
    *@exception  MiddlewareException         Description of the Exception
    *@exception  MiddlewareRequestException  Description of the Exception
    */
    public EANMetaFlagAttribute(EANMetaFoundation _emf, Database _db, Profile _prof, String _s1, boolean _bAllNls) throws SQLException, MiddlewareException, MiddlewareRequestException {
    	this(_emf, _db, _prof, _s1, _bAllNls, null);
    }
    public EANMetaFlagAttribute(EANMetaFoundation _emf, Database _db, Profile _prof, String _s1, boolean _bAllNls,Hashtable memTbl) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _s1,memTbl);
        m_el = new EANList();
        initEANMetaFlagAttribute(_emf, _db, getProfile(), _s1, _bAllNls,memTbl);
    }

    /**
    *  Creates the PDHMetaFlag with the Default NLSID and Value
    *
    *@param  _emf                            Description of the Parameter
    *@param  _prof                           Description of the Parameter
    *@param  _s1                             Description of the Parameter
    *@param  _s2                             Description of the Parameter
    *@param  _s3                             Description of the Parameter
    *@exception  MiddlewareRequestException  Description of the Exception
    */
    public EANMetaFlagAttribute(EANMetaFoundation _emf, Profile _prof, String _s1, String _s2, String _s3) throws MiddlewareRequestException {
        super(_emf, _prof, _s1, _s2, _s3);
        m_el = new EANList();
    }

    /**
    *  perform the 'guts' of the constructor - toggle on retreive all NLS values
    *
    *@param  _emf                            Description of the Parameter
    *@param  _db                             Description of the Parameter
    *@param  _prof                           Description of the Parameter
    *@param  _s1                             Description of the Parameter
    *@param  _bAllNls                        Description of the Parameter
    *@exception  SQLException                Description of the Exception
    *@exception  MiddlewareException         Description of the Exception
    *@exception  MiddlewareRequestException  Description of the Exception
    */
    private void initEANMetaFlagAttribute(EANMetaFoundation _emf, Database _db, Profile _prof, String _s1, 
    		boolean _bAllNls, Hashtable memTbl) throws SQLException, MiddlewareException, MiddlewareRequestException {
        ReturnStatus returnStatus = new ReturnStatus(-1);
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;

        try {
	        //attempt to use less memory, hang onto strings already found
	        Hashtable mymemTbl = null;
	        if(memTbl==null){ // may not be passed in, so create it here
	        	mymemTbl = new Hashtable();
	        	memTbl = mymemTbl;
	        }
            MetaFlag mf = null;
            Profile prof = _prof;
            String strDebug = "gbl8612";
            boolean success = false;
            // Get the description
            try {
                if (_bAllNls) {
                    rs = _db.callGBL6014(returnStatus, prof.getOPWGID(), prof.getEnterprise().trim(), _s1, prof.getValOn(), prof.getEffOn());
                    strDebug = "gbl6014";
                } else {
                    rs = _db.callGBL8612(returnStatus, prof.getOPWGID(), prof.getEnterprise().trim(), _s1, prof.getReadLanguage().getNLSID(), prof.getValOn(), prof.getEffOn());
                }
                rdrs = new ReturnDataResultSet(rs);
                success = true;
            } finally {
            	if (rs!=null){
            		rs.close();
            		rs = null;
            	}
    			if(success){
					_db.commit();
				}else{
					_db.rollback();
				}
                _db.freeStatement();
                _db.isPending();
            }

            for (int ii = 0; ii < rdrs.size(); ii++) {
                String s1 = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 0));
                //flagCode
                int i2 = rdrs.getColumnInt(ii, 1);
                //nlsID
                String s3 = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 2));
                //longDescription
                String s4 = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 3));
                //shortDescription
                String s5 = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 4));
                //setExpired

                _db.debug(D.EBUG_SPEW, strDebug + " answer:" + s1 + ":" + i2 + ":" + s3 + ":" + s4 + ":" + s5 + ":");

                // Create or update the flag value here
                mf = putMetaFlag(_s1, s1, i2, s3, s4);
                if (s5.equals("Y")) {
                    mf.setExpired(true);
                }
            }
		    if(mymemTbl!=null){ // may not be passed in, so release it here
			    mymemTbl.clear();
		    }
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
    *  _s1 attributeCode _s2 flagCode _i1 nlsID _s3 longDescription _s4
    *  shortDescription
    *
    *@param  _s1                             Description of the Parameter
    *@param  _s2                             Description of the Parameter
    *@param  _i1                             Description of the Parameter
    *@param  _s3                             Description of the Parameter
    *@param  _s4                             Description of the Parameter
    *@return                                 Description of the Return Value
    *@exception  MiddlewareRequestException  Description of the Exception
    */
    protected MetaFlag putMetaFlag(String _s1, String _s2, int _i1, String _s3, String _s4) throws MiddlewareRequestException {

        MetaFlag mf = null;
        // Ensure that this Matches the proper AttributeCode
        if (!_s1.equals(getKey())) {
            System.out.println("EANMetaFlagAttribute.putMetaFlag(). ***NO MAS*** " + _s1 + ":NE:" + getKey());
            return null;
        }

        if (m_el.containsKey(getKey() + _s2)) {
            mf = (MetaFlag) m_el.get(getKey() + _s2);
        } else {
            mf = new MetaFlag(this, null, _s2);
            m_el.put(mf);
        }

        mf.putLongDescription(_i1, _s3);
        mf.putShortDescription(_i1, _s4);

        if (super.isReverseLookupEnabled()) {
            if (m_hshReverseLookUp == null) {
                m_hshReverseLookUp = new Hashtable();
            }
            m_hshReverseLookUp.put(_s3, mf);
        }

        return mf;
    }

    /**
    *  returns the MetaFlagValue for the given Flag Code
    *
    *@param  _s1  Description of the Parameter
    *@return      The metaFlag value
    *@returns     the MetaFlagValue for the given key
    */
    public MetaFlag getMetaFlag(String _s1) {
        return (MetaFlag) m_el.get(getKey() + _s1);
    }

    /**
    *  Description of the Method
    *
    *@param  _str  Description of the Parameter
    *@return       Description of the Return Value
    */
    public boolean containsMetaFlag(String _str) {
        return m_el.containsKey(getKey() + _str);
    }

    /**
    *  returns the MetaFlag for the index
    *
    *@param  _i1  Description of the Parameter
    *@return      The metaFlag value
    *@returns     the MetaFlagValue for the given key
    */
    public MetaFlag getMetaFlag(int _i1) {
        return (MetaFlag) m_el.getAt(_i1);
    }

    /**
    *  returns the number of FlagCodes for this Meta Flag Attribute
    *
    *@return     The metaFlagCount value
    *@returns    the number of FlagCodes for this Meta Flag Attribute
    */
    public int getMetaFlagCount() {
        return m_el.size();
    }

    /*
    *  Returns the Long Descriptions of all MetaFlags contained
    *  in this object
    */
    /**
    *  Gets the flagLongDescriptions attribute of the EANMetaFlagAttribute object
    *
    *@return    The flagLongDescriptions value
    */
    public String getFlagLongDescriptions() {
        String str = "";
        boolean bFirstPass = true;
        for (int x = 0; x < getMetaFlagCount(); x++) {
            if (bFirstPass) {
                str = getMetaFlag(x).getLongDescription();
                bFirstPass = false;
            } else {
                str = str + ", " + getMetaFlag(x).getLongDescription();
            }
        }

        return str;
    }

    /**
    *  Description of the Method
    *
    *@param  _bBrief  Description of the Parameter
    *@return          Description of the Return Value
    */
    public String dump(boolean _bBrief) {

        StringBuffer strbResult = new StringBuffer();

        if (_bBrief) {
            strbResult.append(getKey());
        } else {
            strbResult.append(super.dump(_bBrief));
            for (int x = 0; x < getMetaFlagCount(); x++) {
                MetaFlag mf = getMetaFlag(x);
                strbResult.append("\nmf" + x + ":" + mf.dump(_bBrief));
            }
        }

        return strbResult.toString();
    }

    /**
    *  Gets the filtered attribute of the EANMetaFlagAttribute object
    *
    *@return    The filtered value
    */
    public boolean isFiltered() {
        return m_bFiltered;
    }

    /**
    *  Sets the filtered attribute of the EANMetaFlagAttribute object
    *
    *@param  _b  The new filtered value
    */
    public void setFiltered(boolean _b) {
        m_bFiltered = _b;
    }

    /**
    *  Add a MetaFlag
    *
    *@param  _oMf  Description of the Parameter
    */
    public void putMetaFlag(MetaFlag _oMf) {
        m_el.put(_oMf);
        if (super.isReverseLookupEnabled()) {
            if (m_hshReverseLookUp == null) {
                m_hshReverseLookUp = new Hashtable();
            }
            m_hshReverseLookUp.put(_oMf.getLongDescription(), _oMf);//.getFlagCode());
        }
    }

    /**
    *  remove a MetaFlag
    *
    *@param  _oMf  Description of the Parameter
    */
    public void removeMetaFlag(MetaFlag _oMf) {
        m_el.remove(_oMf);
    }

    /**
    *  Return the date/time this class was generated
    *
    *@return    the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: EANMetaFlagAttribute.java,v 1.71 2012/11/08 21:30:54 wendy Exp $";
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////// UPDATE STUFF ////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
    *  Update the changes to EANMetaFlagAttribute to the PDH
    *
    *@param  _bDeepUpdate                    if true go all the way down to
    *      MetaFlags (same as updatEPdhMeta(db))-- else stop at MetaAttribute
    *      level
    *@param  _db                             Description of the Parameter
    *@return                                 Description of the Return Value
    *@exception  SQLException                Description of the Exception
    *@exception  MiddlewareException         Description of the Exception
    *@exception  MiddlewareRequestException  Description of the Exception
    */
    public boolean updatePdhMeta(Database _db, boolean _bDeepUpdate) throws SQLException, MiddlewareException, MiddlewareRequestException {
        //call EANMetaAttribute's method first
        boolean bChanged = super.updatePdhMeta(_db, _bDeepUpdate);
        //now we can update MetaFlags
        //ONLY if NOT a depp update
        if (_bDeepUpdate) {
            bChanged = (updatePdhMetaFlags(_db, false) ? true : bChanged);
        }
        return bChanged;
    }

    /**
    *  Update the changes to EANMetaFlagAttribute to the PDH
    *
    *@param  _db                             Description of the Parameter
    *@return                                 Description of the Return Value
    *@exception  SQLException                Description of the Exception
    *@exception  MiddlewareException         Description of the Exception
    *@exception  MiddlewareRequestException  Description of the Exception
    */
    public boolean updatePdhMeta(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {
        return updatePdhMeta(_db, true);
    }

    /**
    *  Expire the EANMetaFlagAttribute in the PDH
    *
    *@param  _db                             Description of the Parameter
    *@return                                 Description of the Return Value
    *@exception  SQLException                Description of the Exception
    *@exception  MiddlewareException         Description of the Exception
    *@exception  MiddlewareRequestException  Description of the Exception
    */
    public boolean expirePdhMeta(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {
        boolean bChanged = false;

        //save this because it needs to be referenced in updatePdhMetaFlags
        EntityGroup egParent = (EntityGroup) getParent();

        //call EANMetaAttribute's method first
        bChanged = (super.expirePdhMeta(_db) ? true : bChanged);

        //set it back...
        setParent(egParent);

        //now we can expire MetaFlags
        bChanged = (updatePdhMetaFlags(_db, true) ? true : bChanged);
        return bChanged;
    }

    /**
    *  update/expire MetaFlags
    *
    *@param  _db                             Description of the Parameter
    *@param  _bIsExpire                      Description of the Parameter
    *@return                                 Description of the Return Value
    *@exception  SQLException                Description of the Exception
    *@exception  MiddlewareException         Description of the Exception
    *@exception  MiddlewareRequestException  Description of the Exception
    */
    private boolean updatePdhMetaFlags(Database _db, boolean _bIsExpire) throws SQLException, MiddlewareException, MiddlewareRequestException {

        boolean bChanged = false;

        EANMetaFlagAttribute oEmfa_db = null;
        String strAttType = getAttributeType();
        String strAttCode = getAttributeCode();

        EntityGroup egParent = (EntityGroup) this.getParent();

        //does this attribute exist in the MetaEntity table??
        boolean bIsNewAtt = MetaEntityList.isNewAttributeCode(_db, getProfile(), strAttCode);

        if (!bIsNewAtt) {

            switch (strAttType.charAt(0)) {

            case 'F' :

                oEmfa_db = new MetaMultiFlagAttribute(egParent, _db, getProfile(), strAttCode);

                break;
            case 'U' :

                oEmfa_db = new MetaSingleFlagAttribute(egParent, _db, getProfile(), strAttCode);

                break;
            case 'S' :

                oEmfa_db = new MetaStatusAttribute(egParent, _db, getProfile(), strAttCode);

                break;
            case 'A' :

                oEmfa_db = new MetaTaskAttribute(egParent, _db, getProfile(), strAttCode);

                break;
            default :
                break;
            }
        }

        //now go through MetaFlags and perform their updates...

        //EXPIRE
        if (_bIsExpire && !bIsNewAtt) {
            for (int i = 0; i < oEmfa_db.getMetaFlagCount(); i++) {
                MetaFlag oMf_db = oEmfa_db.getMetaFlag(i);
                bChanged = (oMf_db.expirePdhMeta(_db) ? true : bChanged);
            }
        }

        //UPDATE
        if (!_bIsExpire) {
            //1) get MetaFlags that are in db, !in current -> expire these
            //    we only want to do compare if this is already in DB!!
            if (!bIsNewAtt) {
                for (int i = 0; i < oEmfa_db.getMetaFlagCount(); i++) {
                    MetaFlag oMf_db = oEmfa_db.getMetaFlag(i);
                    boolean bFound = false;
                    for (int j = 0; j < this.getMetaFlagCount(); j++) {
                        MetaFlag oMf = this.getMetaFlag(j);
                        if (oMf.getFlagCode().equals(oMf_db.getFlagCode())) {
                            bFound = true;
                            j = this.getMetaFlagCount();
                            //break
                        }
                    }
                    if (!bFound) {
                        bChanged = (oMf_db.expirePdhMeta(_db) ? true : bChanged);
                    }
                }
            }
            //2) update all other MetaFlags that are currently in the MetaFlagAtt
            for (int i = 0; i < this.getMetaFlagCount(); i++) {
                MetaFlag oMf = this.getMetaFlag(i);
                bChanged = (oMf.updatePdhMeta(_db) ? true : bChanged);
            }
        }

        if (egParent != null && _bIsExpire) {
            egParent.removeMetaAttribute(this);
        }

        return bChanged;
    }

    /**
    *  Get and the next available flag code and update the nextid table
    *  accordingly
    *
    *@param  _db                      Description of the Parameter
    *@return                          The nextFlagCode value
    *@exception  MiddlewareException  Description of the Exception
    *@exception  SQLException         Description of the Exception
    */
    protected int getNextFlagCode(Database _db) throws MiddlewareException, SQLException {
        return _db.getNextEntityID(getProfile(), NEXT_FLAG_CODE_KEY);
    }

    /**
    *  Get and the next available flag code and update the nextid table
    *  accordingly
    *
    *@param  _iLength                 preferred number of digits (characters) to
    *      return - for preceding 0's on low numbers (pass 0 here for actual
    *      number).
    *@param  _db                      Description of the Parameter
    *@return                          The nextFlagCodeString value
    *@exception  MiddlewareException  Description of the Exception
    *@exception  SQLException         Description of the Exception
    */
    public String getNextFlagCodeString(Database _db, int _iLength) throws MiddlewareException, SQLException {
        int iFlagCode = getNextFlagCode(_db);
        String strFlagCode = String.valueOf(iFlagCode);
        int iLengthActual = strFlagCode.length();
        if (iLengthActual < _iLength) {
            int iDiff = _iLength - iLengthActual;
            for (int i = 0; i < iDiff; i++) {
                strFlagCode = "0" + strFlagCode;
            }
        }
        return strFlagCode;
    }

}
