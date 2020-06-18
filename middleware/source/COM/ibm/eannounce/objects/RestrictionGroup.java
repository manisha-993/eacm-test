//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: RestrictionGroup.java,v $
// Revision 1.35  2010/11/08 17:53:27  wendy
// Replace EANList with Vector to reduce memory requirements
//
// Revision 1.34  2009/05/14 15:22:08  wendy
// Support dereference for memory release
//
// Revision 1.33  2005/03/10 22:12:06  dave
// Jtest results
//
// Revision 1.32  2005/03/10 20:42:47  dave
// JTest daily ritual
//
// Revision 1.31  2003/11/21 22:12:33  dave
// syntax
//
// Revision 1.30  2003/11/21 21:50:21  dave
// final merge for deferred logic
//
// Revision 1.29  2003/11/21 21:12:12  joan
// fix compile
//
// Revision 1.28  2003/11/21 20:35:45  dave
// added state rememberance
//
// Revision 1.27  2003/11/21 18:42:15  dave
// adding deferred flag
//
// Revision 1.26  2003/04/15 23:31:29  dave
// changed .process to .test
//
// Revision 1.25  2003/04/15 23:23:17  dave
// EvaluatorII implementation
//
// Revision 1.24  2003/01/09 21:30:46  gregg
// ok -- put correct group types in updatePdhMeta (was copied from Required)
//
// Revision 1.23  2003/01/09 17:36:48  gregg
// added updatePdhMeta logic
//
// Revision 1.22  2002/12/23 23:02:49  gregg
// removeAttribibuteCode method
//
// Revision 1.21  2002/11/12 02:20:10  dave
// fixing rollback
//
// Revision 1.20  2002/11/06 18:27:09  dave
// Tracings on reset/required/restrction evals
//
// Revision 1.19  2002/04/11 18:15:09  dave
// Trace statement adjustment and null pointer fix
//
// Revision 1.18  2002/04/02 20:29:30  dave
// Display Statements
//
// Revision 1.17  2002/04/02 01:41:08  dave
// Syntax
//
// Revision 1.16  2002/04/02 01:12:09  dave
// first stab at restriction
//
// Revision 1.15  2002/02/28 00:57:54  dave
// more syntax
//
// Revision 1.14  2002/02/28 00:50:54  dave
// syntax
//
// Revision 1.13  2002/02/28 00:09:00  dave
// more syntax
//
// Revision 1.12  2002/02/27 23:10:44  dave
// added reset, restriction
//
// Revision 1.11  2002/02/02 20:56:54  dave
// tightening up code
//
// Revision 1.10  2002/02/01 01:21:26  dave
// another wave of foundation fixes
//
// Revision 1.9  2002/01/31 23:50:34  dave
// more foundation fixes
//
// Revision 1.8  2002/01/31 23:25:58  dave
// more foundation fixes
//
// Revision 1.7  2002/01/31 22:57:19  dave
// more Foundation Cleanup
//
// Revision 1.6  2001/10/04 20:29:41  dave
// serveral new adds and renames
//
// Revision 1.5  2001/08/19 23:51:41  dave
// changed contains to containsKey in RestrictionGroup
//
// Revision 1.4  2001/08/19 22:37:09  dave
// fixes
//
// Revision 1.3  2001/08/19 22:35:00  dave
// more fixes
//
// Revision 1.2  2001/08/19 21:58:31  dave
// sytax fixes on RestrictionGroup logic
//
// Revision 1.1  2001/08/19 21:20:24  dave
// RestrictionGroup attempt #1
//
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
* This manages a list of AttributeCodes that are effected by this RestrictionGroup
*/
public class RestrictionGroup extends EANMetaFoundation {
	//private static final long serialVersionUID = 1L;
	//memchg private EANList m_el = new EANList();
	private Vector m_el = new Vector();
    private boolean m_bGlobal = false;
    private boolean m_bABR = false;
    private boolean m_bDeferred = false;
    private String m_strEval = null;
    // this tracks the current state of the object.. after the last call to eval
    private boolean m_bCurrentState = false;

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
        return "$Id: RestrictionGroup.java,v 1.35 2010/11/08 17:53:27 wendy Exp $";
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * RestrictionGroup
     *
     * @param _prof
     * @param _strID
     * @param _strEval
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public RestrictionGroup(Profile _prof, String _strID, String _strEval) throws MiddlewareRequestException {
        super(null, _prof, _strID);
        m_strEval = _strEval;
    }

    /**
     * RestrictionGroup
     *
     * @param _ef
     * @param _strID
     * @param _strEval
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public RestrictionGroup(EANMetaFoundation _ef, String _strID, String _strEval) throws MiddlewareRequestException {
        super(_ef, null, _strID);
        m_strEval = _strEval;
    }

    /**
     * addAttributeCode
     *
     * @param _s
     *  @author David Bigelow
     */
    public void addAttributeCode(String _s) {
        try {
        	//memchg   m_el.put(new MetaTag(this, null, _s));
            if(!containsAttributeCode(_s)){
            	m_el.add(new MetaTag(this, null, _s));
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
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
        MetaTag mt = (MetaTag) m_el.elementAt(_i);//memchg getAt(_i);
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
            sb.append("RestrictionGroup:" + getKey() + ":" + toString());
        } else {
            sb.append("RestrictionGroup:" + getKey() + ":" + toString());
            sb.append(NEW_LINE + "title:" + getShortDescription());
            sb.append(NEW_LINE + "desc:" + getLongDescription());
            sb.append(NEW_LINE + "AtributeCodes:");
            for (int x = 0; x < getAttributeCodeCount(); x++) {
                sb.append(NEW_LINE + TAB + getAttributeCode(x));
            }
        }

        return sb.toString();

    }

    /**
     * setGlobalLock
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setGlobalLock(boolean _b) {
        m_bGlobal = _b;
    }

    /**
     * setABRGlobalLock
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setABRGlobalLock(boolean _b) {
        m_bABR = _b;
    }

    /**
     * isGlobalLock
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isGlobalLock() {
        return m_bGlobal;
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
     * isDeferred
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isDeferred() {
        return m_bDeferred;
    }

    /**
     * setDeferred
     *
     * @param _b
     *  @author David Bigelow
     */
    public final void setDeferred(boolean _b) {
        m_bDeferred = _b;
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
        //     System.out.println("Eval Restriction" + ":" + m_strEval);
        m_bCurrentState = EvaluatorII.test(_ei, m_strEval);
        return m_bCurrentState;
    }

    /**
     * getCurrentEvaluateState
     *
     * @return
     *  @author David Bigelow
     */
    public boolean getCurrentEvaluateState() {
        return m_bCurrentState;
    }

    private String getParentEntityType() throws MiddlewareException {
        if (!(getParent() instanceof EntityGroup)) {
            throw new MiddlewareException("RestrictionGroup.getParentEntityType(): parent is NOT an EntityGroup");
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
            RestrictionGroup rg_db = null;
            EntityGroup eg_db = null;
            //get a fresh object from database for compare
            String strNow = _db.getDates().getNow();
            getProfile().setValOnEffOn(strNow, strNow);
            eg_db = new EntityGroup(null, _db, getProfile(), getParentEntityType(), "Edit");
            for (int i = 0; i < eg_db.getRestrictionGroupCount(); i++) {
                RestrictionGroup rgTest = eg_db.getRestrictionGroup(i);
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
                bUpdatePerformed = (updateMLA(_db, true, "Entity/Group", rg_db.getParentEntityType(), rg_db.getKey(), "Restriction", rg_db.getExpression()) ? true : bUpdatePerformed);
                // B) Group/Attributes (x 'n' attributeCodes)
                for (int i = 0; i < rg_db.getAttributeCodeCount(); i++) {
                    _db.debug(D.EBUG_SPEW, "GB_DEBUG 0");
                    bUpdatePerformed = (updateMLA(_db, true, "Group/Attribute", rg_db.getKey(), rg_db.getAttributeCode(i), "Restriction", "Y") ? true : bUpdatePerformed);
                }
            } else if (!_bExpire && bNewGroup) { // 2) new group - insert blindly
                // A) Entity/Group (Expression)
                bUpdatePerformed = (updateMLA(_db, false, "Entity/Group", this.getParentEntityType(), this.getKey(), "Restriction", this.getExpression()) ? true : bUpdatePerformed);
                // B) Group/Attributes (x 'n' attributeCodes)
                for (int i = 0; i < this.getAttributeCodeCount(); i++) {
                    _db.debug(D.EBUG_SPEW, "GB_DEBUG 1");
                    bUpdatePerformed = (updateMLA(_db, false, "Group/Attribute", this.getKey(), this.getAttributeCode(i), "Restriction", "Y") ? true : bUpdatePerformed);
                }
            } else if (!_bExpire && !bNewGroup) { // 3) existing group to update
                if (!rg_db.getExpression().equals(this.getExpression())) {
                    //we can update b/c l.v. is only change
                    bUpdatePerformed = (updateMLA(_db, false, "Entity/Group", this.getParentEntityType(), this.getKey(), "Restriction", this.getExpression()) ? true : bUpdatePerformed);
                }
                // Group/Attributes
                // A) in db, !in new -> expire db record
                for (int i = 0; i < rg_db.getAttributeCodeCount(); i++) {
                    if (!this.containsAttributeCode(rg_db.getAttributeCode(i))) {
                        _db.debug(D.EBUG_SPEW, "GB_DEBUG 2");
                        bUpdatePerformed = (updateMLA(_db, true, "Group/Attribute", rg_db.getKey(), rg_db.getAttributeCode(i), "Restriction", "Y") ? true : bUpdatePerformed);
                    }
                }
                // B) in new -> !in db -> insert new record
                for (int i = 0; i < this.getAttributeCodeCount(); i++) {
                    if (!rg_db.containsAttributeCode(this.getAttributeCode(i))) {
                        _db.debug(D.EBUG_SPEW, "GB_DEBUG 3");
                        bUpdatePerformed = (updateMLA(_db, false, "Group/Attribute", this.getKey(), this.getAttributeCode(i), "Restriction", "Y") ? true : bUpdatePerformed);
                    }
                }
            }
        } catch (Exception exc) {
            _db.debug(D.EBUG_ERR, "Exception in RestrictionGroup.updatePdhMeta(): " + exc.toString());
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
