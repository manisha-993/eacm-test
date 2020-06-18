//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaFlagMaintList.java,v $
// Revision 1.37  2014/02/17 13:43:36  wendy
// IN4836385 - prevent null profile when parent is cleared
//
// Revision 1.36  2013/10/09 20:19:31  wendy
// perf improvement by reducing data sent over rmi
//
// Revision 1.35  2012/09/05 14:36:18  wendy
// add dereference() release memory
//
// Revision 1.34  2008/02/01 22:10:06  wendy
// Cleanup RSA warnings
//
// Revision 1.33  2007/06/05 20:06:02  wendy
// RQ110306297 support max length rule on long description for flags
//
// Revision 1.32  2006/10/05 19:33:48  joan
// MN 29551075
//
// Revision 1.31  2005/11/04 14:52:11  tony
// VEEdit_Iteration2
// updated VEEdit logic to meet new requirements.
//
// Revision 1.30  2005/09/08 19:08:11  joan
// fixes
//
// Revision 1.29  2005/09/08 18:12:25  joan
// add testing rule for meta maintenance
//
// Revision 1.28  2005/03/24 20:19:45  joan
// fixes
//
// Revision 1.27  2005/03/24 18:54:50  joan
// fixes
//
// Revision 1.26  2005/03/24 18:35:54  joan
// fixes
//
// Revision 1.25  2005/03/24 18:30:53  joan
// work on flag maintenance
//
// Revision 1.24  2005/03/24 00:02:40  joan
// work on flag
//
// Revision 1.23  2005/03/23 20:20:57  joan
// work on flag maint
//
// Revision 1.22  2005/03/08 19:40:33  joan
// fixes
//
// Revision 1.21  2005/03/07 23:00:56  dave
// more Jtest
//
// Revision 1.20  2005/03/07 22:00:40  joan
// fixes
//
// Revision 1.19  2005/03/07 21:56:34  joan
// fixes
//
// Revision 1.18  2005/03/07 21:51:56  joan
// fixes
//
// Revision 1.17  2005/03/07 20:24:21  joan
// fix compile
//
// Revision 1.16  2005/03/07 19:58:12  joan
// work on flag maintenance
//
// Revision 1.15  2005/03/04 19:18:53  joan
// work on maintenance flag
//
// Revision 1.14  2005/03/03 21:30:18  joan
// fixes
//
// Revision 1.13  2005/03/03 20:20:15  joan
// fixes
//
// Revision 1.12  2005/03/03 19:56:57  joan
// fixes
//
// Revision 1.11  2005/03/03 19:08:37  joan
// fixes
//
// Revision 1.10  2005/03/03 18:53:02  joan
// fixes
//
// Revision 1.9  2005/03/03 18:40:01  joan
// fixes
//
// Revision 1.8  2005/03/02 22:37:23  joan
// fixes
//
// Revision 1.7  2005/03/02 20:46:22  joan
// fixes
//
// Revision 1.6  2005/02/28 20:43:06  joan
// fixes
//
// Revision 1.5  2005/02/28 20:36:30  joan
// fix compile
//
// Revision 1.4  2005/02/28 20:29:51  joan
// add throw exception
//
// Revision 1.3  2005/02/25 00:34:15  joan
// fixes
//
// Revision 1.2  2005/02/25 00:12:49  joan
// fix compile
//
// Revision 1.1  2005/02/25 00:05:17  joan
// initial load
//

package COM.ibm.eannounce.objects;

import java.util.Hashtable;
import java.util.Vector;
import java.sql.ResultSet;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.transactions.OPICMList;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.D;


// Exceptions
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.LockException;
import java.sql.SQLException;
import java.rmi.RemoteException;

public class MetaFlagMaintList extends EANMetaEntity implements EANTableWrapper {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;
    static final int NLSIDENGLISH = 1;
    private int m_iDescriptionClass = 0;
    private String m_strAttributeCode = null;
    static final String ATTRFLAG = "Attribute/Flag";
    static final String EXPIRED = "Expired";
    //private static String EFFTO = "9999-12-31-00.00.00.000000";
    private MetaMaintActionItem m_mmai = null;
    private EANMetaAttribute m_eanMA = null;

    /**
    * Main method which performs a simple test of this class
    */
    public static void main(String arg[]) {
    }

    public void dereference(){
        for (int i = 0; i < getMetaFlagMaintItemCount(); i++) {
            MetaFlagMaintItem mfmi = getMetaFlagMaintItem(i);
            mfmi.dereference();
        }
    	super.dereference();
    	m_mmai = null;
        m_eanMA = null;
        m_strAttributeCode = null;
    }
    public MetaFlagMaintList(MetaMaintActionItem _ai, Database _db, Profile _prof, String _strAttributeCode) throws MiddlewareRequestException, MiddlewareException, SQLException {
        super(_ai, _prof, _strAttributeCode);
        try {
            setParentActionItem(_ai);
            ResultSet rs;
            ReturnDataResultSet rdrs;
            ReturnStatus returnStatus = new ReturnStatus(-1);

            int iOPWGID = _prof.getOPWGID();
            String strEnterprise = _prof.getEnterprise();

            String strValOn = _prof.getValOn();
            String strEffOn = _prof.getEffOn();

            m_strAttributeCode = _strAttributeCode;
            _db.debug(D.EBUG_SPEW, "gbl8612:parms:" + iOPWGID + ":" + strEnterprise + ":" + m_strAttributeCode + ":" + NLSIDENGLISH + ":" + strValOn + ":" + strEffOn);
            rs = _db.callGBL8612(returnStatus, iOPWGID, strEnterprise, m_strAttributeCode, NLSIDENGLISH, strValOn, strEffOn);
            rdrs = new ReturnDataResultSet(rs);
            rs.close();
            rs = null;
            _db.commit();
            _db.freeStatement();
            _db.isPending();

            for (int ii = 0; ii < rdrs.size(); ii++) {
                String strDescClass = rdrs.getColumn(ii, 0);
                int iNLSID = rdrs.getColumnInt(ii, 1);
                String strLongDesc = rdrs.getColumn(ii, 2);
                String strShortDesc = rdrs.getColumn(ii, 3);
                String strRetired = rdrs.getColumn(ii, 4);
                _db.debug(D.EBUG_SPEW, "gbl8612:answers:" + strDescClass + ":" + iNLSID + ":" + strLongDesc + ":" + strShortDesc + ":" + strRetired);

                MetaFlagMaintItem mfmi = new MetaFlagMaintItem(this, _prof, strDescClass, strDescClass, iNLSID, strLongDesc, strShortDesc, (strRetired.equals("Y") ? true : false), false);
                putMetaFlagMaintItem(mfmi);
            }
        } finally {

            _db.freeStatement();
            _db.isPending();

        }
    }

    /**
     *  Sets the parentActionItem attribute of the MetaFlagMaintList object
     *
     *@param  _mai  The new parentActionItem value
     */
    protected void setParentActionItem(MetaMaintActionItem _mai) {
        m_mmai = _mai;
    }

    /**
     *  Gets the parentActionItem attribute of the WhereUsedList object
     *
     *@return    The parentActionItem value
     */
    public MetaMaintActionItem getParentActionItem() {
        return m_mmai;
    }

    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("MetaFlagMaintList: " + getKey());
        if (!_bBrief) {
            for (int i = 0; i < getMetaFlagMaintItemCount(); i++) {
                MetaFlagMaintItem mfi = getMetaFlagMaintItem(i);
                strbResult.append(mfi.dump(_bBrief));
            }
        }
        return strbResult.toString();
    }

    /*
    * return the column information here
    */
    public EANList getColumnList() {
        EANList colList = new EANList();
        for (int i = 0; i < getMetaFlagMaintItemCount(); i++) {
           // MetaFlagMaintItem ci = getMetaFlagMaintItem(i);

            try {
                MetaLabel ml1 = new MetaLabel(this, getProfile(), MetaFlagMaintItem.DESCRIPTIONCLASS, "Description Class", String.class);
                ml1.putShortDescription("Description Class");
                colList.put(ml1);
                if (!m_mmai.canAutoFill(m_strAttributeCode)) {
					MetaLabel ml2 = new MetaLabel(this, getProfile(), MetaFlagMaintItem.LONGDESCRIPTION, "Long Description", String.class);
					ml2.putShortDescription("Long Description");
					colList.put(ml2);
					MetaLabel ml3 = new MetaLabel(this, getProfile(), MetaFlagMaintItem.SHORTDESCRIPTION, "Short Description", String.class);
					ml3.putShortDescription("Short Description");
					colList.put(ml3);
				}
                MetaLabel ml4 = new MetaLabel(this, getProfile(), MetaFlagMaintItem.EXPIRED, "Expired", String.class);
                ml4.putShortDescription("Expired");
                colList.put(ml4);

            } catch (Exception x) {
                x.printStackTrace();
            }
        }
        return colList;
    }

	protected void setMetaFlag(EANMetaAttribute _ean) {
		m_eanMA = _ean;
	}

	public EANMetaAttribute getMetaFlag() {
		return m_eanMA;
	}

    /*
    *	Return only visible rows
    */
    public EANList getRowList() {
      //  EANList rowList = new EANList();
      //  rowList = getData();
        return  getData();
    }

    public RowSelectableTable getTable() {
        return new RowSelectableTable(this, getKey());
    }

    public int getMetaFlagMaintItemCount() {
        return getDataCount();
    }

    public void putMetaFlagMaintItem(MetaFlagMaintItem _ci) {
        putData(_ci);
    }

    public MetaFlagMaintItem getMetaFlagMaintItem(int _i) {
        return (MetaFlagMaintItem) getData(_i);
    }

    public MetaFlagMaintItem getMetaFlagMaintItem(String _s) {
        return (MetaFlagMaintItem) getData(_s);
    }

    public void removeMetaFlagMaintItem() {
        resetData();
    }

    public void removeMetaFlagMaintItem(String _str) {
        getData().remove(_str);
    }

    public void removeMetaFlagMaintItem(int _i) {
        getData().remove(_i);
    }

    public boolean addRow() {
       // String strTraceBase = "MetaFlagMaintlist addRow method";

        String strNewDescriptionClass = (--m_iDescriptionClass) + "";
        try {

            MetaFlagMaintItem mfmi = new MetaFlagMaintItem(this, getProfile(), strNewDescriptionClass, "", NLSIDENGLISH, "", "", false, true);
            putMetaFlagMaintItem(mfmi);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * VEEdit_iteration2
     *
     * @param key
     * @return boolean
     * @author tony
     */
    public boolean addRow(String _strKey) {
		return addRow();
	}

    public void removeRow(String _str) {
        MetaFlagMaintItem mfmi = getMetaFlagMaintItem(_str);
        if (mfmi != null && mfmi.isNew()) {
            removeMetaFlagMaintItem(_str);
        }
    }

    public boolean hasChanges() {
       // boolean bChanges = false;
        for (int i = 0; i < getMetaFlagMaintItemCount(); i++) {
            MetaFlagMaintItem mfmi = getMetaFlagMaintItem(i);
            if (mfmi.hasChanges()) {
                return true;
            }
        }
        return false;
    }

    public boolean equivalent(EntityItem[] _eia, EANActionItem _ai) {
        return false;
    }

    private boolean isUnique(String _strDescriptionClass) {
        int iCount = 0;
        for (int i = 0; i < getMetaFlagMaintItemCount(); i++) {
            MetaFlagMaintItem mfmi = getMetaFlagMaintItem(i);
            String strDescriptionClass = ((String) mfmi.get(MetaFlagMaintItem.DESCRIPTIONCLASS, true)).trim();
            if (strDescriptionClass.equals(_strDescriptionClass)) {
                iCount++;
            }
        }

        if (iCount <= 1) {
            return true;
        }
        return false;
    }

    private boolean isLongDescriptionUnique(String _strLongDescription) {
        int iCount = 0;
        for (int i = 0; i < getMetaFlagMaintItemCount(); i++) {
            MetaFlagMaintItem mfmi = getMetaFlagMaintItem(i);
            String strLongDescription = ((String) mfmi.get(MetaFlagMaintItem.LONGDESCRIPTION, true)).trim();
            if (strLongDescription.equals(_strLongDescription)) {
                iCount++;
            }
        }

        if (iCount <= 1) {
            return true;
        }
        return false;
    }

    private void clearCache(Database _db, RemoteDatabaseInterface _rdi) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
        if (_db != null) {
            _db.clearCacheForAttribute(getProfile(), m_strAttributeCode);
        } else {
            _rdi.clearCacheForAttribute(getProfile(), m_strAttributeCode);
        }
    }

    public void commit(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
       // int iOPWGID = getProfile().getOPWGID();
        //String strEnterprise = getProfile().getEnterprise();
        //int iTranID = getProfile().getTranID();
        //String strEffFrom = getProfile().getValOn();
    	
		MetaMaintActionItem mmai = getParentActionItem(); // RQ110306297
		int maxLen = mmai.getMaxLength(getMetaFlag()); // RQ110306297

        Vector v = new Vector();
        EntityItemException bre = new EntityItemException();
        for (int i = 0; i < getMetaFlagMaintItemCount(); i++) {
            MetaFlagMaintItem mfmi = getMetaFlagMaintItem(i);

            if (mfmi.hasChanges()) {
                String strDescriptionClass = (String) mfmi.get(MetaFlagMaintItem.DESCRIPTIONCLASS, true);
                if (strDescriptionClass == null || strDescriptionClass.length() <= 0) {
					throw new MiddlewareException("Description Class is blank (ok)");
				}

                if (!isUnique(strDescriptionClass)) {
                    throw new MiddlewareException("Flag code is not unique: " + strDescriptionClass + "(ok)");
                }

				if (m_mmai.canAutoFill(m_strAttributeCode)) {
					mfmi.put(MetaFlagMaintItem.LONGDESCRIPTION, strDescriptionClass);
				}

                String strLongDescription = (String) mfmi.get(MetaFlagMaintItem.LONGDESCRIPTION, true);
                if (strLongDescription == null || strLongDescription.length() <= 0) {
					if (!m_mmai.canAutoFill(m_strAttributeCode)) {
						throw new MiddlewareException("Long Description is blank: " + strDescriptionClass + "(ok)");
					} else {
						mfmi.put(MetaFlagMaintItem.LONGDESCRIPTION, strDescriptionClass);
					}
				}

				// RQ110306297 make sure not commiting a long desc that exceeds maxlen
				if (maxLen>0 && !m_mmai.canAutoFill(m_strAttributeCode)){
					if (strLongDescription.length() > maxLen){
						throw new MiddlewareException("Maximum Length of " + maxLen+
							" Exceeded for Long Description: " + strLongDescription + "(ok)");
					}
				}

                if (!isLongDescriptionUnique(strLongDescription)) {
					bre.add(mfmi, "Warning: Long Description is not unique: " + strLongDescription);
				}

				if (m_eanMA != null) {
					EANUtility.validate(m_eanMA, mfmi, (String) mfmi.get(MetaFlagMaintItem.LONGDESCRIPTION, true));
				} else {
					System.out.println("MetaFlagMaintList meta is null");
				}
				
				// create a clone, do not drag parent info back to the server
				String key = mfmi.getKey().substring(0, mfmi.getKey().length()-1);
				int nlsid = Integer.parseInt((String) mfmi.get(MetaFlagMaintItem.NLSID, true));
				MetaFlagMaintItem mfmiClone = new MetaFlagMaintItem(null, getProfile(), 
		        		key, 
		        		(String) mfmi.get(MetaFlagMaintItem.DESCRIPTIONCLASS, true), 
		        		nlsid, 
		        		(String) mfmi.get(MetaFlagMaintItem.LONGDESCRIPTION, true), 
		        		(String) mfmi.get(MetaFlagMaintItem.SHORTDESCRIPTION, true), false, false);

				v.addElement(mfmiClone);
            }
        }
        
        if (v.size() > 0) {
            MetaFlagMaintItem[] amfmi = new MetaFlagMaintItem[v.size()];
            v.toArray(amfmi);

            OPICMList olFlagCodes = null;
            if (_db != null) {
                olFlagCodes = _db.addFlagCodes(getProfile(), m_strAttributeCode, amfmi);
            } else {
                olFlagCodes = _rdi.addFlagCodes(getProfile(), m_strAttributeCode, amfmi);
            }

            for (int i = 0; i < olFlagCodes.size(); i++) {
                String strKey = (String) olFlagCodes.getAt(i);
                MetaFlagMaintItem mfmi = getMetaFlagMaintItem(strKey);
                if (mfmi != null) {
                    if (mfmi.isNew()) {
                        String strDescriptionClass = (String) mfmi.get(MetaFlagMaintItem.DESCRIPTIONCLASS, true);
                        String strLongDescription = (String) mfmi.get(MetaFlagMaintItem.LONGDESCRIPTION, true);
                        String strShortDescription = (String) mfmi.get(MetaFlagMaintItem.SHORTDESCRIPTION, true);
                        int iNLSID = Integer.parseInt((String) mfmi.get(MetaFlagMaintItem.NLSID, true));
                        removeMetaFlagMaintItem(mfmi.getKey());
                        putMetaFlagMaintItem(new MetaFlagMaintItem(this, getProfile(), strDescriptionClass, strDescriptionClass, iNLSID, strLongDescription, strShortDescription, false, false));
                    }
                    mfmi.commitLocal();
                }
            }
        }

        // clear the cache
        clearCache(_db, _rdi);
        
        if (bre.getErrorCount() > 0) {
			throw bre;
		}

    }
    

    public void retireMetaFlagMaintItems(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _astrKey) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
        //String strTraceBase = "MetaFlagMaintList retireMetaFlagMaintItem method ";

        Vector v = new Vector();
        for (int i = 0; i < _astrKey.length; i++) {
            String strKey = _astrKey[i];
            MetaFlagMaintItem mfmi = getMetaFlagMaintItem(strKey);
            if (mfmi != null && (!mfmi.isNew() && !mfmi.isExpired())) {
                v.addElement(mfmi);
            }
        }

        if (v.size() > 0) {
            MetaFlagMaintItem[] amfmi = new MetaFlagMaintItem[v.size()];
            v.toArray(amfmi);
            OPICMList olFlagCodes = null;
            if (_db != null) {
                olFlagCodes = _db.expireFlagCodes(getProfile(), m_strAttributeCode, amfmi);
            } else {
                olFlagCodes = _rdi.expireFlagCodes(getProfile(), m_strAttributeCode, amfmi);
            }

            for (int i = 0; i < olFlagCodes.size(); i++) {
                String strKey = (String) olFlagCodes.getAt(i);
                MetaFlagMaintItem mfmi = getMetaFlagMaintItem(strKey);
                if (mfmi != null) {
                    mfmi.setExpired(true);
                }
            }
        }
        clearCache(_db, _rdi);
    }

    public void unexpireMetaFlagMaintItems(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _astrKey) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
       // String strTraceBase = "MetaFlagMaintList unexpireMetaFlagMaintItem method ";

        Vector v = new Vector();
        for (int i = 0; i < _astrKey.length; i++) {
            String strKey = _astrKey[i];
            MetaFlagMaintItem mfmi = getMetaFlagMaintItem(strKey);
            if (mfmi != null && (!mfmi.isNew() && mfmi.isExpired())) {
                v.addElement(mfmi);
            }
        }

        if (v.size() > 0) {
            MetaFlagMaintItem[] amfmi = new MetaFlagMaintItem[v.size()];
            v.toArray(amfmi);
            OPICMList olFlagCodes = null;
            if (_db != null) {
                olFlagCodes = _db.unexpireFlagCodes(getProfile(), m_strAttributeCode, amfmi);
            } else {
                olFlagCodes = _rdi.unexpireFlagCodes(getProfile(), m_strAttributeCode, amfmi);
            }

            for (int i = 0; i < olFlagCodes.size(); i++) {
                String strKey = (String) olFlagCodes.getAt(i);
                MetaFlagMaintItem mfmi = getMetaFlagMaintItem(strKey);
                if (mfmi != null) {
                    mfmi.setExpired(false);
                }
            }
        }
        clearCache(_db, _rdi);
    }

    public EANMetaAttribute buildMetaAttribute(Database _db, EANMetaAttribute _meta) throws MiddlewareException, MiddlewareRequestException, SQLException {
        if (_meta == null) {
            return null;
        }

        //IN4836385  - use the meta attribute parent, profile is pulled from it
        EANMetaFoundation mp = this;
        if(_meta.getParent() != null){
        	mp = (EANMetaFoundation)_meta.getParent();
        }
        if (_meta instanceof MetaMultiFlagAttribute) {
            return new MetaMultiFlagAttribute(mp, _db, null, _meta.getKey(), false);
//            return new MetaMultiFlagAttribute(this, _db, null, _meta.getKey(), false);
        } else if (_meta instanceof MetaSingleFlagAttribute) {
            return new MetaSingleFlagAttribute(mp, _db, null, _meta.getKey(), false);
 //           return new MetaSingleFlagAttribute(this, _db, null, _meta.getKey(), false);
        }
        return null;

    }

    public void updateFlagCodes(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, EntityList _el) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
        String strTraceBase = "MetaFlagMaintList updateFlagCodes method ";
        if (m_mmai != null) {
            EntityGroup eg = _el.getEntityGroup(m_mmai.getEntityType());
            if (eg != null) {
                EANMetaAttribute meta = eg.getMetaAttribute(m_strAttributeCode);
                if (meta != null) {
                    if (_db != null) {
                        meta = _db.buildMetaAttribute(this, meta);
                    } else {
                    	//trim for rmi
                    	EANMetaAttribute eanMA = m_eanMA;
                    	EANList mfmilist = this.getData();
                    	Hashtable hsh1 = m_hsh1;
                    	Hashtable hsh2 = m_hsh2;
                    	EANFoundation parent = this.getParent();
                    	m_eanMA = null;
                    	m_hsh1 = null;
                    	m_hsh2 = null;
                    	// break links from metaflagmaintitem to this
                    	for(int i=0;i<this.getMetaFlagMaintItemCount(); i++){
                    		this.getMetaFlagMaintItem(i).setParent(null);
                    	}
                    	
                    	this.removeMetaFlagMaintItem();
                    	this.clipParent();
                    	               	
                        meta = _rdi.buildMetaAttribute(this, meta);
                        
                        // restore after rmi
                        this.setParent(parent);
                        m_eanMA = eanMA;
                        m_hsh1 = hsh1;
                        m_hsh2 = hsh2;
                        this.setData(mfmilist);
                      	// restore links from metaflagmaintitem to this
                    	for(int i=0;i<this.getMetaFlagMaintItemCount(); i++){
                    		this.getMetaFlagMaintItem(i).setParent(this);
                    	}
                    }

                    eg.putMetaAttribute(meta);

                    for (int i=0; i < eg.getEntityItemCount(); i++) {
						EntityItem ei = eg.getEntityItem(i);
						EANAttribute att = (EANAttribute)ei.getEANObject(ei.getEntityType() + ":" + m_strAttributeCode);
						if (att != null) {
							att.setMetaAttribute(meta);
						}
					}
                }
            }
        } else {
            System.out.println(strTraceBase + " m_mmai is null.");
        }
    }

    public boolean canEdit() {
        return true;
    }
    public boolean canCreate() {
        return true;
    }
    public void rollbackMatrix() {
    }
    public void addColumn(EANFoundation _ean) {
    }
    public EntityList generatePickList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }
    public boolean removeLink(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey) throws EANBusinessRuleException {
        return false;
    }
    public EANFoundation[] link(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey, EntityItem[] _aeiChild, String _strLinkOption) throws EANBusinessRuleException {
        return null;
    }
    public EntityList create(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }
    public EntityList edit(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _astrKey) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException {
        return null;
    }

    public WhereUsedList getWhereUsedList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }
    public Object[] getActionItemsAsArray(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) {
        return null;
    }
    public boolean isDynaTable() {
        return false;
    }
    public boolean duplicate(String _strKey, int _iDup) {
        return false;
    }
    public void putMatrixValue(String _str, Object _o) {
    }
    public boolean isMatrixEditable(String _str) {
        return false;
    }

    public Object linkAndRefresh(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, LinkActionItem _lai) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException, RemoteException {
        return null;
    }

    public Object getMatrixValue(String _str) {
        return null;
    }
}
