//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaColumnOrderItem.java,v $
// Revision 1.36  2005/03/07 21:10:26  dave
// Jtest cleanup
//
// Revision 1.35  2005/02/25 17:53:04  gregg
// fixing MetacolumnOrderGroup build, remove debugs!
//
// Revision 1.34  2004/11/15 19:37:36  gregg
// add RelTag property to structure
//
// Revision 1.33  2003/12/16 20:34:44  gregg
// sum cleanup
//
// Revision 1.32  2003/12/16 19:07:20  gregg
// s'more proper ClassCasting
//
// Revision 1.31  2003/12/16 18:51:28  gregg
// more tweaking for Matrix...
//
// Revision 1.30  2003/12/15 23:43:55  gregg
// fix getKeyFromFoundation
//
// Revision 1.29  2003/12/15 23:39:47  gregg
// getKeyFromFoundation method
//
// Revision 1.28  2003/12/15 23:10:17  gregg
// Generalizing for use w/ MatrixList
//
// Revision 1.27  2003/04/03 18:37:20  gregg
// fix for rollback
//
// Revision 1.26  2003/04/03 00:23:23  gregg
// fix for resetToDefaults
//
// Revision 1.25  2003/03/27 22:54:13  gregg
// more cleanup
//
// Revision 1.24  2003/03/27 00:16:49  gregg
// remove System.out's
//
// Revision 1.23  2003/03/26 18:16:31  gregg
// default updates
//
// Revision 1.22  2003/03/26 01:14:40  gregg
// default order update logic
//
// Revision 1.21  2003/03/26 00:00:57  gregg
// tying off apply MetaColumnOrder to RST
//
// Revision 1.20  2003/03/25 22:24:34  gregg
// go back to getting egParent from attribute in constructor in the case that mcog's eg parent is clipped
//
// Revision 1.19  2003/03/25 21:27:58  gregg
// setPending(true) on ALL calls to setVisible(_b)
//
// Revision 1.18  2003/03/25 21:23:28  gregg
// changes to 7541 to grab all attributes for an entitytype
//
// Revision 1.17  2003/03/25 20:31:42  gregg
// more applyColumnOrders for relator case
//
// Revision 1.16  2003/03/24 22:18:28  gregg
// apply MetaColOrder logic to Vertical+Horizontal
//
// Revision 1.15  2003/03/21 00:15:38  gregg
// some prearation for apply MetaColumnOrder Logic, but not enforcing it yet due to code drop...
//
// Revision 1.14  2003/03/20 22:34:04  gregg
// more key - to match maImplicator of EntityGroup's column list
//
// Revision 1.13  2003/03/20 22:29:19  gregg
// removed columnOrder from key (its now just entType+attCode)
//
// Revision 1.12  2003/03/20 21:16:43  gregg
// remove EntityType, AttributeCode, AttirbuteType columns
//
// Revision 1.11  2003/03/14 23:43:09  gregg
// some debugs/update
//
// Revision 1.10  2003/03/14 22:47:18  gregg
// changes to update/commit
//
// Revision 1.9  2003/03/14 18:03:43  gregg
// update moved down into item + use db, rdi methods (not MetaRow objects directly)
//
// Revision 1.8  2003/03/13 23:11:09  gregg
// hasChanges method
//
// Revision 1.7  2003/03/12 23:43:02  gregg
// sorting list ...
//
// Revision 1.6  2003/03/12 20:30:33  gregg
// ClassCastException fix
//
// Revision 1.5  2003/03/12 17:50:45  gregg
// ivVisible SimplePicklistAttribute fix
//
// Revision 1.4  2003/03/12 01:45:28  gregg
// temporarily reverting back to code that works
//
// Revision 1.3  2003/03/11 23:27:53  gregg
// is visible column.
// NOTE: still getting attr order the 'old' way until table implementaion is fully tested, etc
//
// Revision 1.2  2003/03/11 22:22:15  gregg
// Grab Attributes from E.G.'s getColumnList! This means that we are REALLY grabbing attributes for ent2type of relators/assocs also!
//
// Revision 1.1  2003/03/10 20:41:41  gregg
// initial load
//
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * This Class was originally written to manage an EntityType (EntityGroup) containing orderable EANMetaAttributes.
 * Hence, the getEntityType(), getAttributeCode() methods.
 * Generalized to be able to use w/ MatrixList.
 * - that is, use EANMetaFoundations instead of EntityGroups, EANMetaAttributes (and check for instanceof).
 * NOTE: getAttributeCode() will always refer to the AttributeCode column of the MetaColOrder table;
 *       getEntityType() will alway refer to the EntityType column.
 */
public final class MetaColumnOrderItem extends EANMetaFoundation implements EANTableRowTemplate, EANComparable {

    /**
     * @serial
     */
    static final long serialVersionUID = 1L;

    /**************************
     * Table Column Constants *
     **************************/
    // Entity Description
    public static final String ENTDESC_KEY = "ENTDESC";
    /**
     * FIELD
     */
    protected static final String ENTDESC_DESC = "Entity Description";
    // Attribute Description
    /**
     * FIELD
     */
    public static final String ATTDESC_KEY = "ATTDESC";
    /**
     * FIELD
     */
    protected static final String ATTDESC_DESC = "Attribute Description";
    // Column Order
    /**
     * FIELD
     */
    public static final String COLORDER_KEY = "COLORDER";
    /**
     * FIELD
     */
    protected static final String COLORDER_DESC = "Column Order";
    // Is Visible
    /**
     * FIELD
     */
    public static final String VISIBLE_KEY = "VISIBLE";
    /**
     * FIELD
     */
    protected static final String VISIBLE_DESC = "Visible";
    /**
     * FIELD
     */
    protected static final String VIS_YES_KEY = "YES";
    /**
     * FIELD
     */
    protected static final String VIS_YES_DESC = "Yes";
    /**
     * FIELD
     */
    protected static final String VIS_NO_KEY = "NO";
    /**
     * FIELD
     */
    protected static final String VIS_NO_DESC = "No";
    /**
     * FIELD
     */
    public static final String RELTAG_KEY = "RELTAG";
    /**
     * FIELD
     */
    protected static final String RELTAG_DESC = "Relator Tag";

    private EANList m_elColumns = null;

    private String m_strCompareField = COLORDER_KEY;
    private boolean m_bPendingChanges = false;

    private String m_strEntityType = null;
    private String m_strAttributeCode = null;
    private String m_strAttributeType = null;

    /**
     * Get our unique object key from the passed EANMetaFoundation.
     * This will vary dependending upon EANMetaFoundation's proper subclass.
     * Must be static b/c it's used in Constructor's call to super...
     *
     * @return String
     * @concurrency $none
     * @param _emf 
     */
    protected static final synchronized String getKeyFromFoundation(EANMetaFoundation _emf) {
        String strKey = null;
        if (_emf instanceof EANMetaAttribute) {
            strKey = (((EntityGroup) _emf.getParent()).getEntityType() + ":" + _emf.getKey());
        } else {
            strKey = _emf.getKey();
        }
        return strKey;
    }

    /**
     * MetaColumnOrderItem
     *
     * @param _mcog
     * @param _emf
     * @param _iColumnOrder
     * @param _bVisible
     * @param _strRelTag
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    protected MetaColumnOrderItem(MetaColumnOrderGroup _mcog, EANMetaFoundation _emf, int _iColumnOrder, boolean _bVisible, String _strRelTag) throws MiddlewareRequestException {
        // Basically: using EntityGroup key + EANMetaAttribute key for 'standard' EntityGroup orders.
        //            otherwise use EANMEtaFoundation's key.
        super(_mcog, _mcog.getProfile(), getKeyFromFoundation(_emf) + ":" + _strRelTag);

        try {
        // we must get Attribute's PARENT EntityGroup b/c of the good 'ol relator et2 grab atts thing....
            EANFoundation efParent = _emf.getParent();
            EANList elVisiblePicklist = new EANList();
    
            MetaTag mtYes = new MetaTag(null, getProfile(), VIS_YES_KEY);
            MetaTag mtNo = new MetaTag(null, getProfile(), VIS_NO_KEY);
    
            m_elColumns = new EANList();
    
            putSimpleTextAttribute(ENTDESC_KEY, efParent.getLongDescription());
            putSimpleTextAttribute(ATTDESC_KEY, _emf.getLongDescription());
    
            //System.out.println("  in mcoi constructor for " + getKey() + " putting colorder:" + _iColumnOrder);
            putSimpleTextAttribute(COLORDER_KEY, String.valueOf(_iColumnOrder));
            //System.out.println("  in mcoi constructor for " + getKey() + " now getting colorder:" + getValue(COLORDER_KEY));
    
            putSimpleTextAttribute(RELTAG_KEY, _strRelTag);
            // is visible? picklist either Yes or No...
    
            mtYes.putLongDescription(VIS_YES_DESC);
            mtYes.putShortDescription(VIS_YES_DESC);
            mtYes.setSelected(_bVisible);
            mtNo.putLongDescription(VIS_NO_DESC);
            mtNo.putShortDescription(VIS_NO_DESC);
            mtNo.setSelected(!_bVisible);
            elVisiblePicklist.put(mtYes);
            elVisiblePicklist.put(mtNo);
            m_elColumns.put(new SimplePicklistAttribute(this, getProfile(), VISIBLE_KEY, elVisiblePicklist, false));
            // end isVisible
            m_strEntityType = _mcog.getEntityTypeFromParent();
            if (_emf instanceof EANMetaAttribute) {
                m_strAttributeCode = ((EANMetaAttribute) _emf).getAttributeCode();
                m_strAttributeType = ((EANMetaAttribute) _emf).getAttributeTypeMapping();
            } else if (_emf instanceof MatrixGroup) {
                m_strAttributeCode = ((MatrixGroup) _emf).getEntityType();
                m_strAttributeType = "Entity/Relator"; // dunno what this means for MatrixGroup...?
            } else {
                m_strAttributeCode = _emf.getKey();
                m_strAttributeType = _emf.getKey();
            }
        } finally{
        }
    }

    /*************************
     * Misc Public Accessors *
     *
     * @return boolean
     *************************/
    protected boolean pendingChanges() {
        return m_bPendingChanges;
    }

    /**
     * isVisible
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isVisible() {
        return getValue(VISIBLE_KEY).equals(VIS_YES_DESC);
    }

    /**
     * getColumnOrder
     *
     * @return
     *  @author David Bigelow
     */
    public int getColumnOrder() {
        try {
            return Integer.parseInt(getValue(COLORDER_KEY));
        } catch (NumberFormatException exc) { 
            exc.printStackTrace();
        }
        return -1;
    }

    /**
     * getEntityType
     *
     * @return
     *  @author David Bigelow
     */
    public String getEntityType() {
        return m_strEntityType;
    }

    /**
     * getEntityDescription
     *
     * @return
     *  @author David Bigelow
     */
    public String getEntityDescription() {
        return getValue(ENTDESC_KEY);
    }

    /**
     * getAttributeCode
     *
     * @return
     *  @author David Bigelow
     */
    public String getAttributeCode() {
        return m_strAttributeCode;
    }

    /**
     * getAttributeDescription
     *
     * @return
     *  @author David Bigelow
     */
    public String getAttributeDescription() {
        return getValue(ATTDESC_KEY);
    }

    /**
     * getRelTag
     *
     * @return
     *  @author David Bigelow
     */
    public String getRelTag() {
        return getValue(RELTAG_KEY);
    }

    /**
     * getAttributeType
     *
     * @return
     *  @author David Bigelow
     */
    public String getAttributeType() {
        return m_strAttributeType;
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer sb = new StringBuffer();
        sb.append("-------- MetaColumnOrderItem:" + getKey() + " --------" + NEW_LINE);
        if (!_bBrief) {
            sb.append("EntityType:" + getEntityType() + NEW_LINE);
            sb.append("EntityDesc:" + getEntityDescription() + NEW_LINE);
            sb.append("AttibuteCode:" + getAttributeCode() + NEW_LINE);
            sb.append("AttributeDesc:" + getAttributeDescription() + NEW_LINE);
            sb.append("AttributeType:" + getAttributeType() + NEW_LINE);
            sb.append("RelTag:" + getRelTag() + NEW_LINE);
        }
        sb.append("ColumnOrder" + getColumnOrder() + NEW_LINE);
        sb.append("isVisible?" + isVisible() + NEW_LINE);
        return sb.toString();
    }
    /************************
     * Misc Public Mutators *
     *
     * @param _b 
     ************************/
    protected void setPendingChanges(boolean _b) {
        System.out.println(getKey() + " setPendingChanges(" + _b + ")");
        m_bPendingChanges = _b;
    }

    /**
     * setColumnOrder
     *
     * @param _i
     *  @author David Bigelow
     */
    public void setColumnOrder(int _i) {
        System.out.println("setColumnOrder from " + getColumnOrder() + " to " + _i);
        if (_i == getColumnOrder()) {
            return;
        }
        setPendingChanges(true);
        getSimpleTextAttribute(COLORDER_KEY).putValue(String.valueOf(_i));
        System.out.println("...now getColumnOrder() = " + getColumnOrder());
    }

    /**
     * setVisible
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setVisible(boolean _b) {
        //if(_b == isVisible()) {
        //    return;
        //}
        setPendingChanges(true);
        ((SimplePicklistAttribute) m_elColumns.get(VISIBLE_KEY)).setSelectedKey((_b ? VIS_YES_KEY : VIS_NO_KEY));
    }
    /*****************************************
     * EANTableRowTemplate Interface Methods *
     *
     * @return EANFoundation
     * @param _strColKey 
     *****************************************/

    public EANFoundation getEANObject(String _strColKey) {
        EANFoundation ef = (EANFoundation) m_elColumns.get(_strColKey);
        try {
            if (ef instanceof SimpleTextAttribute) {
                return ((SimpleTextAttribute) ef).getCopy();
            }
            if (ef instanceof SimplePicklistAttribute) {
                return ((SimplePicklistAttribute) ef).getCopy();
            }
        } catch (MiddlewareRequestException mre) {
            mre.printStackTrace();
        }
        return null;
    }

    /**
     * Put the value. Only Column Order is acceptable here.
     *
     * @param _strColKey the column to put value in.
     * @param _obj should be a String value.
     * @return boolean
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException 
     */
    public boolean put(String _strColKey, Object _obj) throws EANBusinessRuleException {
        //System.out.println("GB - put(" + _strColKey + "," + _obj.toString());
        if (!isEditable(_strColKey)) {
            return false;
        }
        //
        //if(((Object)m_elColumns.get(_strColKey)).equals(_obj)) {
        //    return true;
        //}
        //
        if (_strColKey.equals(COLORDER_KEY)) {
            try {
                // toString() will work regardless of whether they pass a String or the SimpleTextAttribute!!! Yee-haw!
                setColumnOrder(Integer.parseInt(_obj.toString()));
            } catch (NumberFormatException exc) {
                exc.getMessage();
                throw new IntegerRuleException("Column Order must be an Integer!");
            } // let ClassCastException bubble up....
        } else if (_strColKey.equals(VISIBLE_KEY)) {
            // once again -- we get a bonus here - it doesnt matter whether String value or SimplePicklistAttribute
            // value is passed 'coz of toString()!!
            setVisible(_obj.toString().equals(VIS_YES_DESC));
        }
        return true;
    }

    /**
     * (non-Javadoc)
     * isEditable
     *
     * @see COM.ibm.eannounce.objects.EANTableRowTemplate#isEditable(java.lang.String)
     */
    public boolean isEditable(String _s) {
        if (!getParentGroup().canEdit()) {
            return false;
        } else if (_s.equals(ENTDESC_KEY)) {
            return false;
        } else if (_s.equals(ATTDESC_KEY)) {
            return false;
        }
        //Column Order Only AND Visible
        return true;
    }

    /************************
     * private sub-routines *
     ************************/
    private void putSimpleTextAttribute(String _strKey, String _strValue) throws MiddlewareRequestException {
        m_elColumns.put(new SimpleTextAttribute(this, getProfile(), _strKey, _strValue));
    }
    private SimpleTextAttribute getSimpleTextAttribute(String _strKey) {
        return (SimpleTextAttribute) m_elColumns.get(_strKey);
    }
    private String getValue(String _strKey) {
        EANObject obj = (EANObject) m_elColumns.get(_strKey);
        //
        if (obj instanceof SimpleTextAttribute) {
            return ((SimpleTextAttribute) obj).getValue();
        } else if (obj instanceof SimplePicklistAttribute) {
            return ((SimplePicklistAttribute) obj).getFirstSelectedLongDescription();
        }
        return null;
    }
    private MetaColumnOrderGroup getParentGroup() {
        return (MetaColumnOrderGroup) getParent();
    }

    /*************************
     * EANComparable methods *
     *
     * @return String
     *************************/

    public String toCompareString() {
        //!! nice clean fit !!
        return getValue(getCompareField());
    }

    /**
     * Use static KEY's for this.
     *
     * @param _s 
     */
    public void setCompareField(String _s) {
        m_strCompareField = _s;
    }

    /**
     * (non-Javadoc)
     * getCompareField
     *
     * @see COM.ibm.eannounce.objects.EANComparable#getCompareField()
     */
    public String getCompareField() {
        return m_strCompareField;
    }

    /**************
     * PDH Update *
     *
     * @param _db
     * @param _rdi
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.sql.SQLException 
     **************/

    protected void updateMetaColOrderTable(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
        try {
            int iVisible = (isVisible() ? 1 : 0);
            if (_db != null) {
                _db.updatePdhMetaRow(new MetaColOrderRow(getProfile(), getEntityType(), getAttributeCode(), getColumnOrder(), iVisible));
            } else if (_rdi != null) {
                _rdi.updatePdhMetaRow(new MetaColOrderRow(getProfile(), getEntityType(), getAttributeCode(), getColumnOrder(), iVisible));
            }
        } finally {
            if (_db != null) {
                _db.freeStatement();
                _db.isPending();
            }
        }
        setPendingChanges(false);
        return;
    }

    /**
     * updateMetaColOrderTableDefaults
     *
     * @param _db
     * @param _rdi
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.sql.SQLException
     *  @author David Bigelow
     */
    protected void updateMetaColOrderTableDefaults(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
        try {
            int iVisible = (isVisible() ? 1 : 0);
            if (_db != null) {
                MetaColOrderRow mcor = new MetaColOrderRow(getProfile(), 0, getEntityType(), getAttributeCode(), getColumnOrder(), iVisible);
                _db.updatePdhMetaRow(mcor);
            } else if (_rdi != null) {
                MetaColOrderRow mcor = new MetaColOrderRow(getProfile(), 0, getEntityType(), getAttributeCode(), getColumnOrder(), iVisible);
                _rdi.updatePdhMetaRow(mcor);
            }
        } finally {
            if (_db != null) {
                _db.freeStatement();
                _db.isPending();
            }
        }
        return;
    }

}
