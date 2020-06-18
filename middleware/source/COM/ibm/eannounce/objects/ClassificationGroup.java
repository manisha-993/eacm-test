//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ClassificationGroup.java,v $
// Revision 1.28  2010/11/08 18:52:13  wendy
// Replace EANList with Vector to reduce memory requirements
//
// Revision 1.27  2009/05/11 15:20:18  wendy
// Support dereference for memory release
//
// Revision 1.26  2005/03/03 21:25:16  dave
// NEW_LINE on EAN Foundation
//
// Revision 1.25  2005/02/10 01:22:25  dave
// JTest fixes
//
// Revision 1.24  2003/04/15 23:31:29  dave
// changed .process to .test
//
// Revision 1.23  2003/04/15 23:23:16  dave
// EvaluatorII implementation
//
// Revision 1.22  2003/03/27 23:07:20  dave
// adding some timely commits to free up result sets
//
// Revision 1.21  2003/03/11 17:44:06  gregg
// convert operators in getPrettyEvaluationString() method
//
// Revision 1.20  2003/03/04 18:43:55  gregg
// getPrettyEvaluationString method
//
// Revision 1.19  2002/11/12 23:04:44  gregg
// in updatePdhMeta -> return boolean indicating whether or not any updates to PDH were performed
//
// Revision 1.18  2002/11/12 18:35:44  gregg
// ok, removed putCache in updatePdhMeta (this should be managed up in EnityGroup.updatePdhMeta)
//
// Revision 1.17  2002/11/12 17:18:26  dave
// System.out.println clean up
//
// Revision 1.16  2002/11/12 01:09:24  gregg
// in updatePdhMeta : attempting to putCache() for EntityGroup if change in order to speed things up
//
// Revision 1.15  2002/11/11 18:07:30  gregg
// getEmptyGroups method (for updating/expiring Pdh Meta)
//
// Revision 1.14  2002/11/09 00:29:34  gregg
// serialVersionUID
//
// Revision 1.13  2002/11/08 22:07:50  gregg
// in updatePdhMeta -> database EntityGroup for compare must be EditLike
//
// Revision 1.12  2002/11/08 21:51:05  gregg
// m_bNeedCacheRefresh boolean introduced to save needless cache refreshes
//
// Revision 1.11  2002/11/08 19:54:44  gregg
// updatePdhMeta logic
//
// Revision 1.10  2002/11/06 18:27:09  dave
// Tracings on reset/required/restrction evals
//
// Revision 1.9  2002/11/05 22:40:46  gregg
// getEvaluationString, setEvaluationString methods
//
// Revision 1.8  2002/11/05 21:37:52  gregg
// setGlobalClassify method made public (was protected)
//
// Revision 1.7  2002/11/05 19:02:19  gregg
// removeAttributeCode(String) method
//
// Revision 1.6  2002/10/14 21:04:10  dave
// introducing transient parent pointers so when
// we clone.. we do not have a case where the object
// that is being cloned loses its parent momentarily
//
// Revision 1.5  2002/10/01 18:15:12  dave
// tracing for Classification Eval
//
// Revision 1.4  2002/09/24 16:41:36  dave
// dump expand for ClassifyGroup
//
// Revision 1.3  2002/09/23 15:32:15  dave
// more changes for Classify
//
// Revision 1.2  2002/09/20 21:25:11  dave
// syntax fixing for classification wave II
//
// Revision 1.1  2002/09/20 20:54:01  dave
// Classification Wave II and other
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import java.util.Vector;

/**
* This manages a list of AttributeCodes that are effected by this ClassificationGroup
*/
public class ClassificationGroup extends EANMetaFoundation {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;
    
    //memchg private EANList m_el = new EANList();
    private Vector m_el = new Vector();
    private boolean m_bGlobal = false;
    private String m_strEval = null;

    // if PDH Meta has been changed --> we need to refresh the Parent Entities' Meta Cache
    private boolean m_bNeedCacheRefresh = false;

    protected void dereference(){
    	if (m_el !=null){
    		for (int i=0; i<m_el.size(); i++){
    			MetaTag mt = (MetaTag) m_el.elementAt(i);//memchg .getAt(i);
    			if (mt != null){
    				mt.dereference();
    			}
    		}
    		m_el.clear();
    		m_el = null;
    	}
    	m_strEval = null;
    	super.dereference();
    }
    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: ClassificationGroup.java,v 1.28 2010/11/08 18:52:13 wendy Exp $";
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * ClassificationGroup
     *
     * @param _prof
     * @param _strID
     * @param _strEval
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public ClassificationGroup(Profile _prof, String _strID, String _strEval) throws MiddlewareRequestException {
        super(null, _prof, _strID);
        setEvaluationString(_strEval);
    }

    /**
     * ClassificationGroup
     *
     * @param _ef
     * @param _strID
     * @param _strEval
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public ClassificationGroup(EANMetaFoundation _ef, String _strID, String _strEval) throws MiddlewareRequestException {
        super(_ef, null, _strID);
        setEvaluationString(_strEval);
    }

    /**
     * addAttributeCode
     *
     * @param _s
     *  @author David Bigelow
     */
    public void addAttributeCode(String _s) {
        try {
        	//memchg  m_el.put(new MetaTag(this, null, _s));
        	if(!containsAttributeCode(_s)){
        		m_el.add(new MetaTag(this, null, _s));
        	}
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    /**
     * Remove the AttributeCode if it exists - else do nothing
     *
     * @param _s 
     */
    public void removeAttributeCode(String _s) {
        /*memchg MetaTag mt = (MetaTag) m_el.get(_s);
        if (mt != null) {
            m_el.remove(mt);
        }*/
        for(int i=0; i<m_el.size(); i++){
        	MetaTag eaf = (MetaTag)m_el.elementAt(i);
        	if(eaf.getKey().equals(_s)){
        		m_el.remove(i);
        		break;
        	}
        }
    }

    /*
    * Returns true if the attribute code is contained
    * in this restriction group
    */
    /**
     * containsAttributeCode
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public boolean containsAttributeCode(String _s) {
        for(int i=0; i<m_el.size(); i++){
        	MetaTag eaf = (MetaTag)m_el.elementAt(i);
        	if(eaf.getKey().equals(_s)){
        		return true;
        	}
        }
        return false;
        //memchg m_el.containsKey(_s);
    }

    /*
    * Returns the attributecodes this Classification group controls
    */
    /**
     * getAttributeCode
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public String getAttributeCode(int _i) {
        MetaTag mt = (MetaTag) m_el.elementAt(_i);//memchg getAt(_i);
        if (mt == null) {
            return null;
        }
        return mt.getKey();
    }

    /*
    * Returns the number of AttributeCodes in this group
    */
    /**
     * getAttributeCodeCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getAttributeCodeCount() {
        return m_el.size();
    }

    /*
    * dump method for debuging purposes
    */
    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _brief) {

        StringBuffer sb = new StringBuffer();

        if (_brief) {
            sb.append("ClassificationGroup:" + getKey() + ":" + toString());
        } else {
            sb.append("ClassificationGroup:" + getKey() + ":" + toString());
            sb.append(NEW_LINE + "Sdesc:" + getShortDescription());
            sb.append(NEW_LINE + "LDesc:" + getLongDescription());
            sb.append(NEW_LINE + "isGbl:" + isGlobalClassify());
            sb.append(NEW_LINE + "Eval:" + m_strEval);
            sb.append(NEW_LINE + "AtributeCodes:");
            for (int x = 0; x < getAttributeCodeCount(); x++) {
                sb.append(NEW_LINE + TAB + getAttributeCode(x));
            }
        }

        return sb.toString();

    }

    /**
     * isGlobalClassify
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isGlobalClassify() {
        return m_bGlobal;
    }

    /**
     * setGlobalClassify
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setGlobalClassify(boolean _b) {
        m_bGlobal = _b;
    }

    /**
     * Set the evaluation string for this Classification Group
     *
     * @param _s 
     */
    public void setEvaluationString(String _s) {
        m_strEval = _s;
    }

    /**
     * Get the evaluation string for this Classification Group
     *
     * @return String
     */
    public String getEvaluationString() {
        return m_strEval;
    }

    /*
    * given an Entity Item.  Does this evaluate to true
    * for the given state of the Entity Item.
    */
    /**
     * evaluate
     *
     * @param _ei
     * @return
     *  @author David Bigelow
     */
    public boolean evaluate(EntityItem _ei) {
        if (isGlobalClassify()) {
            return true;
        }
        return EvaluatorII.test(_ei, m_strEval);
    }

    /**
     * Get the EntityType of this group's parent EntityGroup
     */
    private String getParentEntityType() { //method added 11/08/02 by GB
        String strParentEntType = "NULLPARENT";
        try {
            EntityGroup egParent = (EntityGroup) getParent();
            if (egParent != null) {
                strParentEntType = egParent.getEntityType();
            }
        } catch (ClassCastException cce) { 
            cce.printStackTrace();
        }
        return strParentEntType;
    }

    /**
     * Get a 'pretty' version of getEvaluation String - that is, with AttCodes/flagCodes converted to descrips if possible.
     * For presentation in Reports, etc.
     *
     * @return String
     */
    public String getPrettyEvaluationString() {
        // this assumes a format such as:
        // :COMMERCIALOFCAT == 633 && :COMMERCIALOFSUBCAT == 648 && :COMMERCIALOFGRP == 653 && :COMMERCIALOFSUBGRP == 664
        EntityGroup eg = null;
        String strEval = null;
        StringBuffer sb = null;
        StringTokenizer st1 = null;
        EANMetaAttribute maLast = null;

        try {
            eg = (EntityGroup) getParent();
        } catch (ClassCastException cce) {
            cce.printStackTrace();
            return getEvaluationString();
        }
        if (eg == null) {
            return getEvaluationString();
        }
        
        strEval = getEvaluationString();
        sb = new StringBuffer();
        st1 = new StringTokenizer(strEval, ":");
        maLast = null;
        // First parse to each AttributeCode
        while (st1.hasMoreTokens()) {
            boolean bLastAttFound = false; // reset at each ":"
            String s1 = st1.nextToken();
            StringTokenizer st2 = new StringTokenizer(s1, " ");
            // each token - attCode,operator,flagcode,[operator if nec.]
            while (st2.hasMoreTokens()) {
                boolean bAdded = false;
                String strToken = st2.nextToken();
                // 1) check if this is a MetaAttribute and convert if found
                EANMetaAttribute ma = eg.getMetaAttribute(strToken);
                if (ma != null) {
                    sb.append(ma.getActualLongDescription());
                    maLast = ma;
                    bLastAttFound = true;
                    bAdded = true;
                } else if (strToken.equals("==") || strToken.equals("&&")) { // 1.5) check for operators and convert
                    if (strToken.equals("==")) {
                        sb.append(" = ");
                        bAdded = true;
                    } else if (strToken.equals("&&")) {
                        sb.append(" AND ");
                        bAdded = true;
                    }
                } else if (maLast != null && bLastAttFound) { // 2) check if this is a MetaFlag and convert if found
                    // if Flag attr -> convert flag code. We suppose thses aren't necc. flags though?? In which case take token itself
                    if (maLast instanceof EANMetaFlagAttribute) {
                        MetaFlag mf = ((EANMetaFlagAttribute) maLast).getMetaFlag(strToken);
                        if (mf != null) {
                            sb.append(mf.getLongDescription());
                            bLastAttFound = false;
                            bAdded = true;
                        }
                    } else {
                        sb.append(strToken);
                        bLastAttFound = false;
                        bAdded = true;
                    }
                }
                if (!bAdded) { // 3) else add the token
                    sb.append(strToken + " ");
                }
            }
        }
        return sb.toString().trim();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////  Update PDH Meta  - this section added 11/08/02 by GB  /////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Commit any Meta Changes to the PDH
     *
     * @return true if any changes were indeed updated - false otherwise
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public boolean updatePdhMeta(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {
        return updatePdhMeta(_db, false);
    }

    /**
     * Expire all relevant PDH Meta
     *
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @return boolean
     */
    public boolean expirePdhMeta(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {
        return updatePdhMeta(_db, true);
    }

    private boolean updatePdhMeta(Database _db, boolean _bExpire) throws SQLException, MiddlewareException, MiddlewareRequestException {
        //Re: Attribute/Test, CLASSIFIED record managed in EANMetaAttribute
        boolean bUpdatesPerformed = false;
        //does this ClassificationGroup exist in the PDH yet? (key off l.t. = 'Entity/Group' record in MLA table)
        boolean bNewGroup = true;
        //does the parent entity currently contain any groups? (key off l.t. = 'Entity/Char' record in MLA table)
        boolean bNewClassificationEntity = true;
        //is this the last classification for entity - i.e. do we expire Entity/Char record?
        boolean bLastGroup = false;
        
        _db.getDates().getNow();
        _db.getDates().getForever();
 
        try {
            //get a fresh object from database for compare
            ClassificationGroup cg_db = null;
            EntityGroup eg_db = new EntityGroup(null, _db, getProfile(), getParentEntityType(), "Edit");
            eg_db = getEmptyGroups(_db, eg_db);
            for (int i = 0; i < eg_db.getClassificationGroupCount(); i++) {
                if (eg_db.getClassificationGroup(i).getKey().equals(this.getKey())) {
                    bNewGroup = false;
                    break;
                }
            }
            if (eg_db.getClassificationGroupCount() > 0) {
                bNewClassificationEntity = false;
            }
            if (eg_db.getClassificationGroupCount() == 1 && eg_db.getClassificationGroup(0).getKey().equals(this.getKey())) {
                bLastGroup = true;
            }
            if (!bNewGroup) {
                cg_db = eg_db.getClassificationGroup(this.getKey());
            }
            // EXPIRES
            if (_bExpire && !bNewGroup) {
                // 1) l.t. = Entity/Char
                if (bLastGroup) {
                    updateMLA(_db, true, "Entity/Char", getParentEntityType(), "CHAR", "Classify", "Y");
                }
                // 2) l.t. = Entity/Group
                updateMLA(_db, true, "Entity/Group", getParentEntityType(), getKey(), "Classify", (cg_db.isGlobalClassify() ? "Y" : cg_db.getEvaluationString()));
                // 3) global classify
                if (this.isGlobalClassify()) {
                    updateMLA(_db, true, "Group/Attribute", getKey(), "GLOBALCLASSIFY", "Classify", "Y");
                }
                // 4) Group/Attributes
                for (int i = 0; i < cg_db.getAttributeCodeCount(); i++) {
                    updateMLA(_db, true, "Group/Attribute", getKey(), cg_db.getAttributeCode(i), "Classify", "Y");
                }
            }
            //UPDATES
            else if (!_bExpire) {
                //if new group --> we can insert indiscriminately.
                if (bNewGroup) {
                    // 1) l.t. = Entity/Char
                    if (bNewClassificationEntity) {
                        updateMLA(_db, false, "Entity/Char", getParentEntityType(), "CHAR", "Classify", "Y");
                    }
                    // 2) l.t. = Entity/Group
                    updateMLA(_db, false, "Entity/Group", getParentEntityType(), getKey(), "Classify", (this.isGlobalClassify() ? "Y" : this.getEvaluationString()));
                    // 3) global classify
                    if (this.isGlobalClassify()) {
                        updateMLA(_db, false, "Group/Attribute", getKey(), "GLOBALCLASSIFY", "Classify", "Y");
                    }
                    // 4) Group/Attributes
                    for (int i = 0; i < getAttributeCodeCount(); i++) {
                        updateMLA(_db, false, "Group/Attribute", getKey(), getAttributeCode(i), "Classify", "Y");
                    }
                } else { //it is an existing group --> check for updates
                    // 1) l.t. = Entity/Char
                    // dont need to touch this
                    // 2) l.t. = Entity/Group : the change would be on linkvalue, so update should be fine
                    //    --> if global classify status has changed (this probably wont ever happen..)
                    if (this.isGlobalClassify() != cg_db.isGlobalClassify()) {
                        updateMLA(_db, false, "Entity/Group", getParentEntityType(), getKey(), "Classify", (this.isGlobalClassify() ? "Y" : this.getEvaluationString()));
                    //    --> OR evaluation has changed
                    
                    } else if (this.getEvaluationString() != null && (cg_db.getEvaluationString() == null || (!this.getEvaluationString().equals(cg_db.getEvaluationString())))) {
                        updateMLA(_db, false, "Entity/Group", getParentEntityType(), getKey(), "Classify", this.getEvaluationString());
                    }
                    // 3) global classify record
                    if (this.isGlobalClassify() != cg_db.isGlobalClassify()) {
                        updateMLA(_db, (this.isGlobalClassify() ? false : true), "Group/Attribute", getKey(), "GLOBALCLASSIFY", "Classify", "Y");
                    }
                    // 4) Group/Attributes - compare old to new
                    //    a) these were in old --> not in new : expire
                    for (int i = 0; i < cg_db.getAttributeCodeCount(); i++) {
                        if (!this.containsAttributeCode(cg_db.getAttributeCode(i))) {
                            updateMLA(_db, true, "Group/Attribute", getKey(), cg_db.getAttributeCode(i), "Classify", "Y");
                        }
                    }
                    //    b) these were !in old --> ARE in new : insert
                    for (int i = 0; i < this.getAttributeCodeCount(); i++) {
                        if (!cg_db.containsAttributeCode(this.getAttributeCode(i))) {
                            updateMLA(_db, false, "Group/Attribute", getKey(), this.getAttributeCode(i), "Classify", "Y");
                        }
                    }
                }
            }
            //we must expire the cache here !!!!
            if (m_bNeedCacheRefresh) {
                MetaCacheManager mcm = new MetaCacheManager(getProfile());
                mcm.expireEGCacheAllRolesAllNls(_db, getParentEntityType());
                bUpdatesPerformed = true;
                m_bNeedCacheRefresh = false;
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return bUpdatesPerformed;
    }

    private EntityGroup getEmptyGroups(Database _db, EntityGroup _eg) throws SQLException, MiddlewareException, MiddlewareRequestException {
        
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        try {
            String strNow = _db.getDates().getNow();
            try {
                rs = _db.callGBL7508(new ReturnStatus(-1), _eg.getProfile().getEnterprise(), "Entity/Group", _eg.getEntityType(), "Classify", strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strGroupKey = rdrs.getColumn(row, 0);
                if (_eg.getClassificationGroup(strGroupKey) == null) {
                    ClassificationGroup cg = new ClassificationGroup(_eg, strGroupKey, "");
                    _eg.putClassificationGroup(cg);
                }
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return _eg;
    }

    /**
     * to simplify things a bit
     */
    private void updateMLA(Database _db, boolean _bExpire, String _strLinkType, String _strLinkType1, String _strLinkType2, String _strLinkCode, String _strLinkValue) throws MiddlewareException {
        _db.getDates().getNow();
        _db.getDates().getForever();
        m_bNeedCacheRefresh = true;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
