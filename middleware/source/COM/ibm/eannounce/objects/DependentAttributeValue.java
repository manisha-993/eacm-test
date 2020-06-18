// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: DependentAttributeValue.java,v $
// Revision 1.44  2009/05/11 15:37:19  wendy
// Support dereference for memory release
//
// Revision 1.43  2009/03/10 13:47:33  wendy
// Support for CQ00013876- meta chgs are needed to use it
//
// Revision 1.42  2008/06/05 18:40:44  wendy
// MN35817308
//
// Revision 1.41  2008/06/05 18:35:07  wendy
// Allow chgs on entity with a nodel attr, as long as it is a different attr
//
// Revision 1.40  2007/10/02 18:23:40  wendy
// Added more comments
//
// Revision 1.39  2007/06/08 17:55:58  wendy
// Added support for Deletion of Entity for RQ1103063214 "XCC GX SR001.5.001 EACM Enabling Technology - 'No Delete FLAG' Rule"
//
// Revision 1.38  2007/06/08 16:44:23  wendy
// RQ1103063214 "XCC GX SR001.5.001 EACM Enabling Technology - 'No Delete FLAG' Rule"
//
// Revision 1.37  2006/06/16 06:25:04  dave
// EntittyGroup does not need to be
// in the DependentAttributeValue object
//
// Revision 1.36  2006/02/27 19:03:04  gregg
// fix
//
// Revision 1.35  2006/02/27 18:56:33  gregg
// error msg from db
//
// Revision 1.34  2005/06/22 17:06:56  gregg
// null ptr fix
//
// Revision 1.33  2005/06/21 16:16:17  gregg
// rst put fix for search on FLAG case
//
// Revision 1.32  2005/06/17 22:09:40  gregg
// trying to fix
//
// Revision 1.31  2005/06/17 19:59:41  gregg
// oooooops. mind your i's and j's (fun with loops)
//
// Revision 1.30  2005/06/17 18:59:32  gregg
// ok more debugging
//
// Revision 1.29  2005/06/17 18:45:59  gregg
// remvoe some debugs
//
// Revision 1.28  2005/06/17 18:36:11  gregg
// looking at setting correct att vals..
//
// Revision 1.27  2005/03/01 00:30:55  dave
// Jtest and TIR testing
//
// Revision 1.26  2005/02/23 23:06:18  gregg
// more exceptions msgs
//
// Revision 1.25  2005/02/23 23:05:30  gregg
// waedfasdfsa
//
// Revision 1.24  2005/02/23 22:58:46  gregg
// formatting
//
// Revision 1.23  2005/02/23 22:52:27  gregg
// NULL PTR!!!
//
// Revision 1.22  2005/02/23 22:45:46  gregg
// messaging stufff
//
// Revision 1.21  2005/02/23 22:25:41  gregg
// np fix
//
// Revision 1.20  2005/02/23 22:07:08  gregg
// cleaning up Exception messagae for DependentAttributeValue
//
// Revision 1.19  2005/02/22 18:09:45  gregg
// meta comments
//
// Revision 1.18  2005/02/22 18:08:35  gregg
// flag code trickery
//
// Revision 1.17  2005/02/21 23:18:33  gregg
// flags
//
// Revision 1.16  2005/02/21 21:47:57  gregg
// null ptr fix
//
// Revision 1.15  2005/02/21 21:46:09  gregg
// more in validate
//
// Revision 1.14  2005/02/21 21:20:37  gregg
// more SearchActionItem API for DependentAttributeValue
//
// Revision 1.13  2005/02/21 20:06:26  gregg
// some more adjustments to DEpAttVal
//
// Revision 1.12  2005/02/21 19:06:56  gregg
// fix
//
// Revision 1.11  2005/02/21 19:02:43  gregg
// implement Search in DependentAttributeValue
//
// Revision 1.10  2005/02/18 21:49:22  gregg
// remove debugs
//
// Revision 1.9  2005/02/18 21:29:42  gregg
// fix key
//
// Revision 1.8  2005/02/18 19:30:33  gregg
// mulitple values in one group(CR 3148)
//
// Revision 1.7  2005/02/14 17:18:33  dave
// more jtest fixing
//
// Revision 1.6  2005/01/31 18:08:20  gregg
// some fixes to validate
//
// Revision 1.5  2005/01/28 23:30:31  gregg
// fix in validate()
//
// Revision 1.4  2005/01/28 23:07:16  gregg
// updates for DependentAttributeValue
//
// Revision 1.3  2005/01/28 22:13:44  gregg
// kompyle phixksez
//
// Revision 1.2  2005/01/28 22:06:46  gregg
// DependentAttributeValue
//
// Revision 1.1  2005/01/28 21:32:17  gregg
// initial load
//
//
//

/*
Meta looks like this:

insert into opicm.metalinkattr values('SG13',  'Entity/Group',     'COREQ',      'COREQDEPVALGRP1',   'DependentValue',   'SRDPRODSTRUCT',                '2005-02-22-00.00.00.000000', '9999-12-31-00.00.00.000000', '2005-02-22-00.00.00.000000', '9999-12-31-00.00.00.000000',        9999,          -9);
insert into opicm.metalinkattr values('SG13',  'Group/Attribute',  'COREQDEPVALGRP1', 'FEATURECODE',   'FEATURE',  'FEATURECODE:TEXT',                '2005-02-22-00.00.00.000000', '9999-12-31-00.00.00.000000', '2005-02-22-00.00.00.000000', '9999-12-31-00.00.00.000000',        9999,           -9);
insert into opicm.metalinkattr values('SG13',  'Group/Attribute',  'COREQDEPVALGRP1', 'MACHTYPEATR',   'MODEL',  'MACHTYPEATR:FLAG',                '2005-02-22-00.00.00.000000', '9999-12-31-00.00.00.000000', '2005-02-22-00.00.00.000000', '9999-12-31-00.00.00.000000',        9999,           -9);
insert into opicm.metalinkattr values('SG13',  'Group/Attribute',  'COREQDEPVALGRP1', 'MODELATR',      'MODEL',  'MODELATR:TEXT',                '2005-02-22-00.00.00.000000', '9999-12-31-00.00.00.000000', '2005-02-22-00.00.00.000000', '9999-12-31-00.00.00.000000',        9999,           -9);
*/

package COM.ibm.eannounce.objects;

import java.util.Vector;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import java.sql.SQLException;
import java.sql.ResultSet;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;

/**
 * DependentAttributeValue
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DependentAttributeValue extends EANMetaEntity {

    /**
     * DepAttValItem
     *
     * @author David Bigelow
     * To change the template for this generated type comment go to
     * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
     */
    public class DepAttValItem extends EANMetaFoundation {

        /**
		 * 
		 */
		private static final long serialVersionUID = -1979183828481438081L;
		private String m_strCrEntType = null;
        private String m_strCrAttCode = null;
        private String m_strVerEntType = null;
        private String m_strVerAttCode = null;
        private String m_strAttVal = null;
        private String m_strVerAttType = null;
        private boolean m_bisNoDel = false; // RQ1103063214
        
        protected void dereference(){
        	m_strCrEntType = null;
            m_strCrAttCode = null;
            m_strVerEntType = null;
            m_strVerAttCode = null;
            m_strAttVal = null;
            m_strVerAttType = null;
            
        	super.dereference();
        }

        /**
         * DepAttValItem
         *
         * @param _strCrEntType
         * @param _strCrAttCode
         * @param _strVerEntType
         * @param _strVerAttCode
         * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
         *  @author David Bigelow
         */
        protected DepAttValItem(String _strCrEntType, String _strCrAttCode, String _strVerEntType, String _strVerAttCode) throws MiddlewareRequestException {
            super(DependentAttributeValue.this, DependentAttributeValue.this.getProfile(), _strCrEntType + ":" + _strCrAttCode + ":" + _strVerEntType + ":" + _strVerAttCode);
            m_strCrEntType = _strCrEntType;
            m_strCrAttCode = _strCrAttCode;
            m_strVerEntType = _strVerEntType;
            m_strVerAttCode = _strVerAttCode;
        }

        /**
         * DepAttValItem
         *
         * @param _davi
         * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
         *  @author David Bigelow
         */
        protected DepAttValItem(DepAttValItem _davi) throws MiddlewareRequestException {
            this(_davi.getCreateEntityType(), _davi.getCreateAttributeCode(), _davi.getVerifyEntityType(), _davi.getVerifyAttributeCode());
            setAttributeValue(_davi.getAttributeValue());
            setVerifyAttributeType(_davi.getVerifyAttributeType());
            m_bisNoDel = _davi.isNoDelete(); // RQ1103063214
        }

        /**
         * getCreateEntityType
         *
         * @return
         *  @author David Bigelow
         */
        public final String getCreateEntityType() {
            return m_strCrEntType;
        }

        /**
         * getCreateAttributeCode
         *
         * @return
         *  @author David Bigelow
         */
        public final String getCreateAttributeCode() {
            return m_strCrAttCode;
        }

        /**
         * getVerifyEntityType
         *
         * @return
         *  @author David Bigelow
         */
        public final String getVerifyEntityType() {
            return m_strVerEntType;
        }

        /**
         * getVerifyAttributeCode
         *
         * @return
         *  @author David Bigelow
         */
        public final String getVerifyAttributeCode() {
            return m_strVerAttCode;
        }

        /**
         * setVerifyAttributeType
         *
         * @param _s
         *  @author David Bigelow
         */
        public final void setVerifyAttributeType(String _s) {
			if (_s.indexOf(".")!=-1){ // look for FLAG.NODEL RQ1103063214
				java.util.StringTokenizer st1 = new java.util.StringTokenizer(_s, ".");
				m_strVerAttType = st1.nextToken();
				String strAction = st1.nextToken();
				if (strAction.equals(NODEL)){
					m_bisNoDel=true;
				}
			}else{
	            m_strVerAttType = _s;
			}
        }

        /**
         * getVerifyAttributeType
         *
         * @return
         *  @author David Bigelow
         */
        public final String getVerifyAttributeType() {
            return m_strVerAttType;
        }

		/** RQ1103063214
		* Is this dependent attribute a 'no delete'
		* supports Function:
		* Deletion of either the EntityType = MACHTYPE or a value of MACHTYPEATR is not allowed if:
		* Search for MACHTYPE.MACHTYPEATR in MODEL.MACHTYPEATR and if found, do not allow the delete.
		*@return boolean
		*/
		public final boolean isNoDelete(){
			return m_bisNoDel;

		}
        /**
         * setAttributeValue
         *
         * @param _strAttVal
         *  @author David Bigelow
         */
        public final void setAttributeValue(String _strAttVal) {
			//System.out.println("DependentAttributeValue: setAttributeValue(\"" + _strAttVal + "\")");
            m_strAttVal = _strAttVal;
        }

        /**
         * getAttributeValue
         *
         * @return
         *  @author David Bigelow
         */
        public final String getAttributeValue() {
            return m_strAttVal;
        }

        private final boolean valueExists(EntityItem _ei) {
            EANAttribute att = _ei.getAttribute(getCreateAttributeCode());
            if (att == null || att.toString() == null || att.toString().equals("")) {
                return false;
            }
            return true;
        }

        private final void populateAttributeVal(EntityItem _ei) {
            EANAttribute att = _ei.getAttribute(getCreateAttributeCode());
            String strAttVal = null;
            if (att instanceof SingleFlagAttribute) {
				// only doin this for single flags --- this is really the only case that makes sense..??
				MetaFlag[] amf = (MetaFlag[]) ((SingleFlagAttribute) att).get();
				//System.out.println("DependentAttributeValue: populateAttributeVal, amf.length = " + amf.length);
				if (amf.length > 0) {
					//System.out.println("DependentAttributeValue: populateAttributeVal, amf[0].isActive?" + amf[0].isActive());
					//strAttVal = amf[0].getFlagCode();
					strAttVal = ((SingleFlagAttribute) att).getFirstActiveFlagCode();
				} else {
					System.out.println("ERROR in DependentAttributeValue(item).populateAttributeVal: flag has no value!");
				}
            } else {
                strAttVal = (att != null ? att.toString() : "");
            }
            //System.out.println("DependentAttributeValue: populateAttributeVal, setAttribtueValue(\"" + strAttVal + "\")");
            setAttributeValue(strAttVal);
        }

        private DepAttValItem getCopy() throws MiddlewareRequestException {
            return new DepAttValItem(this);
        }

        /**
         * (non-Javadoc)
         * dump
         *
         * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
         */
        public String dump(boolean _b) {
            StringBuffer sb = new StringBuffer();
            if (!_b) {
                sb.append("	Key:"+getKey() + "\n");
                sb.append("	CreateType:"+getCreateEntityType() + "\n");
            }
            sb.append("	This attribute:" + getCreateAttributeCode() + "\n");
            sb.append("	Dependent Entity:" + getVerifyEntityType() + "\n");
            sb.append("	DependentAttribute:" + getVerifyAttributeCode() + "\n");
            if (!_b) {
                sb.append("	:" + (getVerifyAttributeType() != null ? getVerifyAttributeType() : "<No Type>") + "\n");
            }
            sb.append("	Required Value:"+(getAttributeValue() != null ? getAttributeValue() : "<No Value>"));
            sb.append("\n	No Delete:" + isNoDelete()); // RQ1103063214
            return sb.toString();
        }
    }

    final static long serialVersionUID = 1L;

    /**
     * FIELD
     */
    protected static final String TEXT = "TEXT";

    /**
     * FIELD
     */
    protected static final String FLAG = "FLAG";
    protected static final String NODEL = "NODEL";

    private String m_strSearchAI = null; // search action item to use

    private String m_strExcMsg = null;
    private int entityID = 0; // RQ1103063214

    protected void dereference(){
    	m_strExcMsg = null;
    	m_strSearchAI = null;
        
    	for (int i = 0; i < getItemCount(); i++) {
			DepAttValItem davItem = getItem(i);
			davItem.dereference();
    	}
    	super.dereference();
    }
    /**
     * DependentAttributeValue
     *
     * @param _egParent
     * @param _prof
     * @param _strGroupKey
     * @param _strSearchAI
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    protected DependentAttributeValue(Profile _prof, String _strGroupKey, String _strSearchAI) throws MiddlewareRequestException {
        super(null, _prof, _strGroupKey);
        m_strSearchAI = _strSearchAI;
    }

    private DependentAttributeValue(DependentAttributeValue _dav) throws MiddlewareRequestException {
        this(_dav.getProfile(), _dav.getKey(), _dav.m_strSearchAI);
        for (int i = 0; i < _dav.getItemCount(); i++) {
            putMeta(_dav.getItem(i).getCopy());
        }
        this.entityID = _dav.entityID;

    }

    /**
     * getEntityGroup
     *
     * @return
     *  @author David Bigelow
     */
    public final EntityGroup getEntityGroup() {
        return (EntityGroup) getParent();
    }

    /**
     * getExceptionMessage
     *
     * @return
     *  @author David Bigelow
     */
    public final String getExceptionMessage() {
        return m_strExcMsg;
    }

    public final void setExceptionMessage(String _s) {
		m_strExcMsg = _s;
	}



    //public final String getPartNo() {
    //    return dump(true);
    //}

    /**
     * getItem
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public final DepAttValItem getItem(int _i) {
        return (DepAttValItem) getMeta(_i);
    }

    /**
     * addItem
     *
     * @param _strCrEntType
     * @param _strCrAttCode
     * @param _strVerEntType
     * @param _strVerAttCode
     * @return
     *  @author David Bigelow
     */
    public final DepAttValItem addItem(String _strCrEntType, String _strCrAttCode, String _strVerEntType, String _strVerAttCode) {
        DepAttValItem davi = null;
        try {
            davi = new DepAttValItem(_strCrEntType, _strCrAttCode, _strVerEntType, _strVerAttCode);
            putMeta(davi);
        } catch (MiddlewareRequestException x) {
            System.err.println("ERROR in addItem:" + x.toString()); // shouldnt ever happen
        }
        return davi;
    }

    /**
     * getItemCount
     *
     * @return
     *  @author David Bigelow
     */
    public final int getItemCount() {
        return getMetaCount();
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _b) {
        return getKey() + ":";
    }

    /**
     * getCopy
     *
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @return
     *  @author David Bigelow
     */
    public final DependentAttributeValue getCopy() throws MiddlewareRequestException {
        return new DependentAttributeValue(this);
    }

    /**
     * validate
     *
     * @param _db
     * @param _vctAttributes
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public final boolean validate(Database _db, Vector _vctAttributes) throws SQLException, MiddlewareException {

        // ok, first check our local entity; if this is one of those with both atts in the same entity, we first need
        // to chug through our local attributes (these are not in Database yet!!!)

        SearchActionItem sai = null;
        RowSelectableTable rst = null;

        EntityList el = null;

        LOOPER : for (int a = 0; a < getItemCount(); a++) {
            if (getItem(a).getCreateEntityType().equals(getItem(a).getVerifyEntityType())) {
                for (int i = 0; i < _vctAttributes.size(); i++) {
                    COM.ibm.opicmpdh.objects.Attribute att = (COM.ibm.opicmpdh.objects.Attribute) _vctAttributes.elementAt(i);
                    if (att.getAttributeCode().equals(getItem(a).getVerifyAttributeCode())) {
                        String strVerAttVal = att.getAttributeValue();
                        if (att.getControlBlock().isOn() && strVerAttVal.trim().equals(getItem(a).getAttributeValue())) {
                            _db.debug(D.EBUG_SPEW,"DependentAttributeValue.validate: found value in same transaction:" + dump(true));
                            return true;
                        }
                        break LOOPER;
                    }
                }
            }
        }

        // Now use Search Action Item API to find any matches...
        sai = new SearchActionItem(null, _db, getProfile(), m_strSearchAI, false);
        rst = sai.getDynaSearchTable(_db);

        try {

            for (int i = 0; i < getItemCount(); i++) {

                String strEntityType = getItem(i).getVerifyEntityType();
                String strAttCode = getItem(i).getVerifyAttributeCode();
                String strRowKey = strEntityType + ":" + strAttCode;
                String strAttValue = getItem(i).getAttributeValue();

                _db.debug(D.EBUG_SPEW,"DependentAttributeValue.validate: strAttValue = \"" + strAttValue + "\"");

                int r = rst.getRowIndex(strRowKey);
                if (r < 0) {
                    r = rst.getRowIndex(strRowKey + ":P");
                }

                if (r < 0) {
                    r = rst.getRowIndex(strRowKey + ":C");
                }

                if (r < 0) {
                    r = rst.getRowIndex(strRowKey + ":R");
                }

                //_db.debug(D.EBUG_SPEW,"DependentAttributeValue: Row index is:" + r + " for " + strRowKey);
                if (getItem(i).getVerifyAttributeType().equals("TEXT")) {
                    //_db.debug(D.EBUG_SPEW,"DependentAttributeValue: putting TEXT " + strAttValue + " in row " + r);
                    rst.put(r, 1, strAttValue);
                } else {
                    // single flags only for now!!!!
                    EntityGroup eg = new EntityGroup(null, _db, getProfile(), strEntityType, "Edit");
					if (getItem(i).isNoDelete()) { // RQ1103063214
	                    EntityGroup egOrig = new EntityGroup(null, _db, getProfile(), getItem(i).getCreateEntityType(), "Edit");
						// must get value before this change, it hasnt been saved yet so just get it from pdh
						EntityItem origItem = new EntityItem(egOrig, getProfile(), _db, getItem(i).getCreateEntityType(), entityID);
						EANAttribute origatt = origItem.getAttribute(strAttCode);
						if (origatt instanceof SingleFlagAttribute) {
							// only doin this for single flags --- this is really the only case that makes sense..??
							MetaFlag[] amf = (MetaFlag[]) ((SingleFlagAttribute) origatt).get();
							if (amf.length > 0) {
								// really want to look for entities with this value, if they exist then it cant be changed
								strAttValue = ((SingleFlagAttribute) origatt).getFirstActiveFlagCode();
        						getItem(i).setAttributeValue(strAttValue);
								_db.debug(D.EBUG_SPEW,"DependentAttributeValue.validate: no delete reset strAttValue = \"" + strAttValue + "\"");
							} else {
								_db.debug(D.EBUG_ERR,"DependentAttributeValue.validate: no delete reset flag attr has no value (" + strAttCode + ") ");
							}
						}
					}

                    EANMetaFlagAttribute flagAtt = (EANMetaFlagAttribute) eg.getMetaAttribute(strAttCode);
                   // EANFlagAttribute dataFlagAtt = (EANFlagAttribute)rst.getEANObject(r,1);
                    if (flagAtt == null) {
                        _db.debug(D.EBUG_ERR,"DependentAttributeValue.validate: flag attr is null (" + strAttCode + ") something must be wrong w/ meta");
                    }else{
						FLAG_LOOP : for (int j = 0; j < flagAtt.getMetaFlagCount(); j++) {
							MetaFlag mf = flagAtt.getMetaFlag(j);
							_db.debug(D.EBUG_SPEW,"DependentAttributeValue.validate: checking mf.getFlagCode() == \"" + mf.getFlagCode() + "\", strAttValue = \"" + strAttValue + "\"");
							if (mf.getFlagCode().equals(strAttValue)) {
								_db.debug(D.EBUG_SPEW,"DependentAttributeValue.validate: FOUND MetaFlag:" + mf.toString());
								mf.setSelected(true);
								//dataFlagAtt.put(new MetaFlag[]{mf});
								rst.put(r, 1, new MetaFlag[]{mf});
								break FLAG_LOOP;
							}
						}
					}
                }

            }
        } catch (EANBusinessRuleException exc) {
            _db.debug(D.EBUG_ERR, "DependentAttributeValue.validate exception:" + exc.toString());
            exc.printStackTrace(System.err);
        }

        sai.setCheckLimit(false);
        sai.disableUIAfterCache();
        sai.disableLikeMatching();
        //_db.debug(D.EBUG_SPEW,"DependentAttributeValue sai.getSearchString():" + sai.getSearchString());
        el = sai.executeAction(_db, getProfile());

        _db.debug(D.EBUG_SPEW,"DependentAttributeValue.validate: eg count == " + el.getEntityGroupCount());

        //m_sbExcMsg = null; // null out first

        for (int i = 0; i < getItemCount(); i++) {
            EntityGroup eg = el.getEntityGroup(getItem(i).getVerifyEntityType());
            _db.debug(D.EBUG_SPEW,"DependentAttributeValue.validate: item["+i+"]:\n" + getItem(i).dump(false));
			if (eg == null || eg.getEntityItemCount() == 0) {
				if (!getItem(i).isNoDelete()) { // RQ1103063214 if it is NODEL must check below
					// the following check was originally done, only do it now if it is not a NODEL check
					_db.debug(D.EBUG_SPEW,"DependentAttributeValue.validate: no required "+getItem(i).getVerifyEntityType()+
						" entity items found, validate is false!!!");
					if(m_strExcMsg == null) {
						m_strExcMsg = getErrorMessage(_db,getProfile(),getKey(),el,getItem(i));
					}
					return false;
				}
			}

			if (eg != null){
	            _db.debug(D.EBUG_SPEW,"DependentAttributeValue.validate: search found:" + eg.getEntityType() + " with " + eg.getEntityItemCount() + " entities.");
			}else{
	            _db.debug(D.EBUG_SPEW,"DependentAttributeValue.validate: search found:" + getItem(i).getVerifyEntityType() + " group was null.");
			}
			if (getItem(i).isNoDelete()) { // RQ1103063214
				if (eg != null && eg.getEntityItemCount()> 0) {
					// check to see if this dependent attribute was updated, dont allow it
		            _db.debug(D.EBUG_SPEW,"DependentAttributeValue.validate: 'No Delete' check found " + eg.getEntityType() + " with " +
		            	eg.getEntityItemCount() + " entities");

					// check for the dependent attribute and prevent change MN35817308
					for (int ia = 0; ia < _vctAttributes.size(); ia++) {
						COM.ibm.opicmpdh.objects.Attribute att = (COM.ibm.opicmpdh.objects.Attribute) _vctAttributes.elementAt(ia);
						if (att.getAttributeCode().equals(getItem(i).getVerifyAttributeCode())) {
							String strVerAttVal = att.getAttributeValue();
							if (!strVerAttVal.trim().equals(getItem(i).getAttributeValue())) {
								_db.debug(D.EBUG_SPEW,"DependentAttributeValue.validate: 'No Delete' check found invalid dependent attribute change, validate is false!!!");
								if(m_strExcMsg == null) {
									m_strExcMsg = getErrorMessage(_db,getProfile(),getKey(),el,getItem(i));
								}
								return false;
							}
						}
					}
				}
			}
        }
        return true; //bValid;
    }

    /** RQ1103063214
     * validate if delete entity has dependencies,
     * Deletion of the EntityType = MACHTYPE is not allowed if:
	 * Search for MACHTYPE.MACHTYPEATR in MODEL.MACHTYPEATR and if found, do not allow the delete.
     *
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return boolean
     */
    public final boolean validateDelete(Database _db)
    	throws SQLException, MiddlewareException
    {
		boolean noDeleteFound=false;

        // Now use Search Action Item API to find any matches...
        SearchActionItem sai = new SearchActionItem(null, _db, getProfile(), m_strSearchAI, false);
        RowSelectableTable rst = sai.getDynaSearchTable(_db);

        try {

            for (int i = 0; i < getItemCount(); i++) {

                String strEntityType = getItem(i).getVerifyEntityType();
                String strAttCode = getItem(i).getVerifyAttributeCode();
                String strRowKey = strEntityType + ":" + strAttCode;
                String strAttValue = getItem(i).getAttributeValue();

                _db.debug(D.EBUG_SPEW,"DependentAttributeValue.validateDelete: isNoDelete: "+getItem(i).isNoDelete()+" strAttValue = \"" + strAttValue + "\"");
				if (!getItem(i).isNoDelete()) {
					continue;
				}
				noDeleteFound=true;

                int r = rst.getRowIndex(strRowKey);
                if (r < 0) {
                    r = rst.getRowIndex(strRowKey + ":P");
                }

                if (r < 0) {
                    r = rst.getRowIndex(strRowKey + ":C");
                }

                if (r < 0) {
                    r = rst.getRowIndex(strRowKey + ":R");
                }

                //_db.debug(D.EBUG_SPEW,"DependentAttributeValue: Row index is:" + r + " for " + strRowKey);
                if (getItem(i).getVerifyAttributeType().equals("TEXT")) {
                    //_db.debug(D.EBUG_SPEW,"DependentAttributeValue: putting TEXT " + strAttValue + " in row " + r);
                    rst.put(r, 1, strAttValue);
                } else {
                    // single flags only for now!!!!
                    EntityGroup eg = new EntityGroup(null, _db, getProfile(), strEntityType, "Edit");
					EntityGroup egOrig = new EntityGroup(null, _db, getProfile(), getItem(i).getCreateEntityType(), "Edit");
					// must get value before this change, it hasnt been saved yet so just get it from pdh
					EntityItem origItem = new EntityItem(egOrig, getProfile(), _db, getItem(i).getCreateEntityType(), entityID);
					EANAttribute origatt = origItem.getAttribute(strAttCode);
					if (origatt instanceof SingleFlagAttribute) {
						// only doin this for single flags --- this is really the only case that makes sense..??
						MetaFlag[] amf = (MetaFlag[]) ((SingleFlagAttribute) origatt).get();
						if (amf.length > 0) {
							// really want to look for entities with this value, if they exist then it cant be changed
							strAttValue = ((SingleFlagAttribute) origatt).getFirstActiveFlagCode();
							getItem(i).setAttributeValue(strAttValue);
							_db.debug(D.EBUG_SPEW,"DependentAttributeValue.validateDelete: no delete reset strAttValue = \"" + strAttValue + "\"");
						} else {
							_db.debug(D.EBUG_ERR,"DependentAttributeValue.validateDelete: no delete reset flag attr has no value (" + strAttCode + ") ");
						}
					}

                    EANMetaFlagAttribute flagAtt = (EANMetaFlagAttribute) eg.getMetaAttribute(strAttCode);
                   // EANFlagAttribute dataFlagAtt = (EANFlagAttribute)rst.getEANObject(r,1);
                    if (flagAtt == null) {
                        _db.debug(D.EBUG_ERR,"DependentAttributeValue.validateDelete: flag attr is null (" + strAttCode + ") something must be wrong w/ meta");
                    }else{
						FLAG_LOOP : for (int j = 0; j < flagAtt.getMetaFlagCount(); j++) {
							MetaFlag mf = flagAtt.getMetaFlag(j);
							_db.debug(D.EBUG_SPEW,"DependentAttributeValue.validateDelete: checking mf.getFlagCode() == \"" + mf.getFlagCode() + "\", strAttValue = \"" + strAttValue + "\"");
							if (mf.getFlagCode().equals(strAttValue)) {
								_db.debug(D.EBUG_SPEW,"DependentAttributeValue.validateDelete: FOUND MetaFlag:" + mf.toString());
								mf.setSelected(true);
								//dataFlagAtt.put(new MetaFlag[]{mf});
								rst.put(r, 1, new MetaFlag[]{mf});
								break FLAG_LOOP;
							}
						}
					}
                }

            }
        } catch (EANBusinessRuleException exc) {
            _db.debug(D.EBUG_ERR, "DependentAttributeValue.validateDelete exception:" + exc.toString());
            exc.printStackTrace(System.err);
        }

		if (noDeleteFound){
        	EntityList el = null;
			sai.setCheckLimit(false);
			sai.disableUIAfterCache();
			sai.disableLikeMatching();
			//_db.debug(D.EBUG_SPEW,"DependentAttributeValue sai.getSearchString():" + sai.getSearchString());
			el = sai.executeAction(_db, getProfile());

			_db.debug(D.EBUG_SPEW,"DependentAttributeValue.validateDelete: eg count == " + el.getEntityGroupCount());

			for (int i = 0; i < getItemCount(); i++) {
				EntityGroup eg = el.getEntityGroup(getItem(i).getVerifyEntityType());
				_db.debug(D.EBUG_SPEW,"DependentAttributeValue.validateDelete: item["+i+"]:\n" + getItem(i).dump(false));
				if (eg != null){
					_db.debug(D.EBUG_SPEW,"DependentAttributeValue.validateDelete: search found:" + eg.getEntityType() + " with " + eg.getEntityItemCount() + " entities.");
				}else{
					_db.debug(D.EBUG_SPEW,"DependentAttributeValue.validateDelete: search found:" + getItem(i).getVerifyEntityType() + " group was null.");
				}
				if (getItem(i).isNoDelete()) { // RQ1103063214
					if (eg != null && eg.getEntityItemCount()> 0) {
						_db.debug(D.EBUG_SPEW,"DependentAttributeValue.validateDelete: 'No Delete' check found " + eg.getEntityType() + " with " +
							eg.getEntityItemCount() + " entities, validate is false!!!");
						if(m_strExcMsg == null) {
							m_strExcMsg = getErrorMessage(_db,getProfile(),getKey(),el,getItem(i));
						}
						return false;
					}
				}
			}
		}
        return true;
    }

    /**
     * get the error message filling in needed information if braces are found
     * @param _db
     * @param _prof
     * @param _strKey
     * @param list
     * @param davItem
     * @return
     * @throws SQLException
     * @throws MiddlewareException
     */
    private String getErrorMessage(Database _db, Profile _prof, String _strKey,
    	EntityList list, DepAttValItem davItem)
    	throws SQLException, MiddlewareException {
        // this is default for if we cannot find one in the db.
        String strMsg = "Missing Required Dependent data (" + _strKey + ")";
		if (davItem.isNoDelete()) { // RQ1103063214
			strMsg = "Can not delete since Dependent data exists (" + _strKey + ")";
		}
		ResultSet rs = null;
		ReturnDataResultSet rdrs = null;
        try {
            rs = _db.callGBL7567(new ReturnStatus(-1),_prof.getEnterprise(),_strKey,_prof.getReadLanguage().getNLSID());
            rdrs = new ReturnDataResultSet(rs);
        } finally {
        	if(rs != null) {
        		rs.close();
        		rs = null;
        	}

        	_db.commit();
        	_db.freeStatement();
        	_db.isPending();
        }	
        
        if (rdrs!= null){
            _db.debug(D.EBUG_SPEW,"GBL7567 row count:" + rdrs.getRowCount());
            if(rdrs.getRowCount() > 0) { // nlsid's order ordered desc--> grab first non-1 nlsid, default to 1
                strMsg = rdrs.getColumn(0,0);
                int iNLSID = rdrs.getColumnInt(0,1);
                _db.debug(D.EBUG_SPEW,"GBL7567 answer:" + strMsg + ":" + iNLSID);
           		if (davItem.isNoDelete()) { // RQ1103063214
           			// fill in values using long description
           			//msg='Can not delete since {EntityType.AttributeCode} exists'
           			int openbrace =strMsg.indexOf("{");
           			if (openbrace!=-1){
						int closebrace=strMsg.indexOf("}");
						String src = strMsg.substring(openbrace+1,closebrace);
						if (src.indexOf(".")!=-1){
							java.util.StringTokenizer st1 = new java.util.StringTokenizer(src, ".");
							String type = st1.nextToken();
							EntityGroup eg = list.getEntityGroup(type);
							if (eg!=null){
								type = eg.getLongDescription();
								String code = st1.nextToken();
								EANMetaAttribute ma = eg.getMetaAttribute(code);
								if (ma != null) {
									code = ma.getActualLongDescription();
									// fill in msg
									StringBuffer sb = new StringBuffer(strMsg);
									sb.delete(openbrace,closebrace+1);
									sb.insert(openbrace,type+" "+code+" "+davItem.getAttributeValue());
									strMsg = sb.toString();
								}
							}
						}
					}
				}else{
					// fill in values using davItem.getAttributeValue() based on davItem.getCreateAttributeCode()
           			//origmsg='A TMF (Prodstruct) does not exist with matching To Machine Type, To Model, and To Feature'
					//newmsg = 'A TMF (Prodstruct) does not exist with matching To Machine Type {TOMACHTYPE}, To Model {TOMODEL}, and To Feature {TOFEATURECODE}'
					// warning: there is a maximum length for this column in metalinkattr
           			int openbrace =strMsg.indexOf("{");
           			if (openbrace!=-1){ //CQ00013876
           				strMsg = replaceBraces(_db,strMsg);
           			}
				}
			}
        }
        return strMsg;
	}
 
    /**
     * CQ00013876 - add values to error message
     * @param _db
     * @param strMsg
     * @return
     */
    private String replaceBraces(Database _db,String strMsg) {
    	StringBuffer msgSb = new StringBuffer(strMsg);
    	int openbrace = 0;
    	while(openbrace != -1){
    		openbrace = msgSb.toString().indexOf("{",openbrace);
    		if (openbrace != -1) {
    			int closebrace=msgSb.toString().indexOf("}");
    			String createAttrCode = msgSb.substring(openbrace+1, closebrace);
    			boolean matchFound = false;
    			// find the value for this attribute code
    			for (int i = 0; i < getItemCount(); i++) {
    				DepAttValItem davItem = getItem(i);
    				if (davItem.getCreateAttributeCode().equals(createAttrCode)){
    					msgSb.replace(openbrace, closebrace+1,davItem.getAttributeValue());
    					matchFound = true;
    					break;
    				}
    			}
    	        if (!matchFound){
    	        	// cant fill in the message so return original string
    				_db.debug(D.EBUG_SPEW,"DependentAttributeValue.replaceBraces: Cant find a createAttrCode matching " + createAttrCode);
    	        	return strMsg;
    	        }
    		}
    	}
    	return msgSb.toString();
    }

    /**
     * allValuesExist
     *
     * @param _ei
     * @return
     *  @author David Bigelow
     */
    public final boolean allValuesExist(EntityItem _ei) {
        for (int i = 0; i < getItemCount(); i++) {
            if (!getItem(i).valueExists(_ei)) {
                return false;
            }
        }
        return true;
    }

    /**
     * populateAttributeVals
     *
     * @param _ei
     *  @author David Bigelow
     */
    public final void populateAttributeVals(EntityItem _ei) {
		entityID = _ei.getEntityID(); // RQ1103063214
        for (int i = 0; i < getItemCount(); i++) {
            getItem(i).populateAttributeVal(_ei);
        }
    }

}
