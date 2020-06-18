//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ResetGroup.java,v $
// Revision 1.13  2010/11/08 18:03:23  wendy
// Replace EANList with Vector to reduce memory requirements
//
// Revision 1.12  2009/05/14 18:41:01  wendy
// Support dereference for memory release
//
// Revision 1.11  2005/03/10 20:42:47  dave
// JTest daily ritual
//
// Revision 1.10  2003/04/15 23:31:29  dave
// changed .process to .test
//
// Revision 1.9  2003/04/15 23:23:17  dave
// EvaluatorII implementation
//
// Revision 1.8  2003/01/09 21:30:46  gregg
// ok -- put correct group types in updatePdhMeta (was copied from Required)
//
// Revision 1.7  2003/01/09 17:36:48  gregg
// added updatePdhMeta logic
//
// Revision 1.6  2002/12/23 23:47:33  gregg
// setExpression method
//
// Revision 1.5  2002/12/23 23:01:49  gregg
// removeAttributeCode() method
//
// Revision 1.4  2002/11/12 17:18:28  dave
// System.out.println clean up
//
// Revision 1.3  2002/04/04 18:59:03  dave
// missing member variable
//
// Revision 1.2  2002/04/04 18:49:32  dave
// syntax
//
// Revision 1.1  2002/02/28 00:40:20  dave
// more syntax
//
// Revision 1.2  2002/02/28 00:09:00  dave
// more syntax
//
// Revision 1.1  2002/02/27 23:19:33  dave
// added the RestGroupMethod
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import java.sql.SQLException;
import java.util.Vector;

/**
* This manages a list of AttributeCodes that could be Required for a given Entity
* based upon the evaluation of the passed expression
*/
public class ResetGroup extends EANMetaFoundation {

    //memchg private EANList m_el = new EANList();
    private Vector m_el = new Vector();
    private String m_strEval = null;
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
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /* Constructs a new ResetGroup with the Key _s1, and the Evaluation expression _s2
    */
    /**
     * ResetGroup
     *
     * @param _prof
     * @param _strID
     * @param _strEval
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public ResetGroup(Profile _prof, String _strID, String _strEval) throws MiddlewareRequestException {
        super(null, _prof, _strID);
        m_strEval = _strEval;
    }

    /**
     * ResetGroup
     *
     * @param _ef
     * @param _strID
     * @param _strEval
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public ResetGroup(EANMetaFoundation _ef, String _strID, String _strEval) throws MiddlewareRequestException {
        super(_ef, null, _strID);
        m_strEval = _strEval;
    }

    /**
     * removeAttributeCode
     *
     * @param _s
     *  @author David Bigelow
     */
    public void removeAttributeCode(String _s) {
        for(int i=0; i<m_el.size(); i++){
        	MetaTag eaf = (MetaTag)m_el.elementAt(i);
        	if(eaf.getKey().equals(_s)){
        		m_el.remove(i);
        		break;
        	}
        }

       //memchg  m_el.remove(_s);
    }

    /**
     * addAttributeCode
     *
     * @param _s
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public void addAttributeCode(String _s) throws MiddlewareRequestException {
        // memchg m_el.put(new MetaTag(this, null, _s));
        if(!containsAttributeCode(_s)){
        	m_el.add(new MetaTag(this, null, _s));
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
    * Returns the attributecodes this restrict group controls
    */
    /**
     * getAttributeCode
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public String getAttributeCode(int _i) {
        MetaTag mt = null;
        if(_i<m_el.size()){
        	mt =(MetaTag) m_el.elementAt(_i);//memchg .getAt(_i);
        }
        if (mt == null) {
            return null;
        }
        return mt.getKey();
    }

    /*
    * Returns the number of Attributecodes in this group
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
    * Returns the evaluation express that can be tested for true/false
    */
    /**
     * getExpression
     *
     * @return
     *  @author David Bigelow
     */
    public String getExpression() {
        return m_strEval;
    }

    /**
     * setExpression
     *
     * @param _s
     *  @author David Bigelow
     */
    public void setExpression(String _s) {
        m_strEval = _s;
    }

    /**
     * inExpression
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public boolean inExpression(String _s) {
        if (m_strEval.indexOf(_s) != -1) {
            return true;
        }
        return false;
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
            sb.append("ResetGroup:" + getKey() + ":" + toString());
        } else {
            sb.append("ResetGroup:" + getKey() + ":" + toString());
            sb.append(NEW_LINE + "title:" + getShortDescription());
            sb.append(NEW_LINE + "desc:" + getLongDescription());
            sb.append(NEW_LINE + "Expression:" + m_strEval);
            sb.append(NEW_LINE + "AtributeCodes:");
            for (int x = 0; x < getAttributeCodeCount(); x++) {
                sb.append(NEW_LINE + TAB + getAttributeCode(x));
            }
        }

        return sb.toString();

    }

    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: ResetGroup.java,v 1.13 2010/11/08 18:03:23 wendy Exp $";
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
        // System.out.println("Eval Reset" + ":" + m_strEval);
        return EvaluatorII.test(_ei, m_strEval);
    }

    private String getParentEntityType() throws MiddlewareException {
        if (!(getParent() instanceof EntityGroup)) {
            throw new MiddlewareException("ResetGroup.getParentEntityType(): parent is NOT an EntityGroup");
        }
        return ((EntityGroup) getParent()).getEntityType();
    }

    //////////////////////////////////////////////////
    //// UPDATE PDH META METHODS
    //////////////////////////////////////////////////

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
     * @return boolean
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public boolean expirePdhMeta(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {
        return updatePdhMeta(_db, true);
    }

    private boolean updatePdhMeta(Database _db, boolean _bExpire) throws SQLException, MiddlewareException, MiddlewareRequestException {
        //track updates to PDH
        boolean bUpdatePerformed = false;
        //is this group new (!in db)
        boolean bNewGroup = true;
        // 0) get a 'fresh' copy IF it exists  
        try {
            //get a fresh object from database for compare 

            EntityGroup eg_db = null;
            ResetGroup rg_db = null;

            String strNow = _db.getDates().getNow();
            getProfile().setValOnEffOn(strNow, strNow);
            eg_db = new EntityGroup(null, _db, getProfile(), getParentEntityType(), "Edit");
            rg_db = null;
            for (int i = 0; i < eg_db.getResetGroupCount(); i++) {
                ResetGroup rgTest = eg_db.getResetGroup(i);
                if (rgTest.getKey().equals(this.getKey())) {
                    rg_db = rgTest;
                    bNewGroup = false; //NOT a new group coz' we found key
                    break;
                }
            }

            _db.debug(D.EBUG_SPEW, "GB_DEBUG _bExpire:" + _bExpire + ",bNewGroup:" + bNewGroup);

            // 1) EXPIRE
            if (_bExpire && !bNewGroup) {
                // A) Entity/Group (Expression)
                bUpdatePerformed = (updateMLA(_db, true, "Entity/Group", rg_db.getParentEntityType(), rg_db.getKey(), "Reset", rg_db.getExpression()) ? true : bUpdatePerformed);
                // B) Group/Attributes (x 'n' attributeCodes)
                for (int i = 0; i < rg_db.getAttributeCodeCount(); i++) {
                    _db.debug(D.EBUG_SPEW, "GB_DEBUG 0");
                    bUpdatePerformed = (updateMLA(_db, true, "Group/Attribute", rg_db.getKey(), rg_db.getAttributeCode(i), "Reset", "Y") ? true : bUpdatePerformed);
                }
            } else if (!_bExpire && bNewGroup) { // 2) new group - insert blindly
                // A) Entity/Group (Expression)
                bUpdatePerformed = (updateMLA(_db, false, "Entity/Group", this.getParentEntityType(), this.getKey(), "Reset", this.getExpression()) ? true : bUpdatePerformed);
                // B) Group/Attributes (x 'n' attributeCodes)
                for (int i = 0; i < this.getAttributeCodeCount(); i++) {
                    _db.debug(D.EBUG_SPEW, "GB_DEBUG 1");
                    bUpdatePerformed = (updateMLA(_db, false, "Group/Attribute", this.getKey(), this.getAttributeCode(i), "Reset", "Y") ? true : bUpdatePerformed);
                }
            } else if (!_bExpire && !bNewGroup) { // 3) existing group to update
                if (!rg_db.getExpression().equals(this.getExpression())) {
                    //we can update b/c l.v. is only change
                    bUpdatePerformed = (updateMLA(_db, false, "Entity/Group", this.getParentEntityType(), this.getKey(), "Reset", this.getExpression()) ? true : bUpdatePerformed);
                }
                // Group/Attributes
                // A) in db, !in new -> expire db record
                for (int i = 0; i < rg_db.getAttributeCodeCount(); i++) {
                    if (!this.containsAttributeCode(rg_db.getAttributeCode(i))) {
                        _db.debug(D.EBUG_SPEW, "GB_DEBUG 2");
                        bUpdatePerformed = (updateMLA(_db, true, "Group/Attribute", rg_db.getKey(), rg_db.getAttributeCode(i), "Reset", "Y") ? true : bUpdatePerformed);
                    }
                }
                // B) in new -> !in db -> insert new record
                for (int i = 0; i < this.getAttributeCodeCount(); i++) {
                    if (!rg_db.containsAttributeCode(this.getAttributeCode(i))) {
                        _db.debug(D.EBUG_SPEW, "GB_DEBUG 3");
                        bUpdatePerformed = (updateMLA(_db, false, "Group/Attribute", this.getKey(), this.getAttributeCode(i), "Reset", "Y") ? true : bUpdatePerformed);
                    }
                }
            }
        } catch (Exception exc) {
            _db.debug(D.EBUG_ERR, "Exception in ResetGroup.updatePdhMeta(): " + exc.toString());
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return bUpdatePerformed;
    }

    /**
     * to simplify things a bit
     */
    private boolean updateMLA(Database _db, boolean _bExpire, String _strLinkType, String _strLinkType1, String _strLinkType2, String _strLinkCode, String _strLinkValue) throws MiddlewareException {
        String strValFrom = _db.getDates().getNow();
        String strValTo = (_bExpire ? strValFrom : _db.getDates().getForever());
        new MetaLinkAttrRow(getProfile(), _strLinkType, _strLinkType1, _strLinkType2, _strLinkCode, _strLinkValue, strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
        return true;
    }
    ////////////////////////////////////////////////////
}
