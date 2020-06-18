//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: RequiredGroup.java,v $
// Revision 1.30  2010/11/08 18:05:31  wendy
// Replace EANList with Vector to reduce memory requirements
//
// Revision 1.29  2009/05/11 15:22:22  wendy
// Support dereference for memory release
//
// Revision 1.28  2005/03/10 20:42:47  dave
// JTest daily ritual
//
// Revision 1.27  2003/04/15 23:31:29  dave
// changed .process to .test
//
// Revision 1.26  2003/04/15 23:23:17  dave
// EvaluatorII implementation
//
// Revision 1.25  2003/01/07 00:27:38  gregg
// some debug statements
//
// Revision 1.24  2003/01/03 19:19:56  gregg
// return a boolean in updatePdhMeta methods indicating whether or not any actual update to pdh was made.
//
// Revision 1.23  2003/01/03 19:15:14  gregg
// updatePDHMeta methods
//
// Revision 1.22  2002/12/23 23:47:33  gregg
// setExpression method
//
// Revision 1.21  2002/12/23 23:01:09  gregg
// removeAttributeCode method
//
// Revision 1.20  2002/11/12 02:20:10  dave
// fixing rollback
//
// Revision 1.19  2002/04/02 20:45:42  dave
// syntax fix
//
// Revision 1.18  2002/04/02 20:29:30  dave
// Display Statements
//
// Revision 1.17  2002/04/02 19:00:01  dave
// first pass at required field changes
//
// Revision 1.16  2002/02/28 00:25:24  dave
// syntax errors
//
// Revision 1.15  2002/02/28 00:09:00  dave
// more syntax
//
// Revision 1.14  2002/02/02 21:59:17  dave
// more cleanup
//
// Revision 1.13  2002/02/02 21:11:07  dave
// fixing more import statements
//
// Revision 1.12  2002/02/02 20:56:54  dave
// tightening up code
//
// Revision 1.11  2002/02/01 01:21:26  dave
// another wave of foundation fixes
//
// Revision 1.10  2002/01/31 23:50:34  dave
// more foundation fixes
//
// Revision 1.9  2002/01/31 23:10:13  dave
// more fixes
//
// Revision 1.8  2002/01/31 22:57:19  dave
// more Foundation Cleanup
//
// Revision 1.7  2001/10/04 20:29:41  dave
// serveral new adds and renames
//
// Revision 1.6  2001/08/23 17:19:52  dave
// attempt at building a refresh list for the client to selective refreshes
// for forms based editing
//
// Revision 1.5  2001/08/21 22:17:47  dave
// added Serializable to RequiredGroup
//
// Revision 1.4  2001/08/21 20:42:56  dave
// encorporating basic evaluator into required logic
//
// Revision 1.3  2001/08/21 19:37:50  dave
// Display fixes to include new objects
//
// Revision 1.2  2001/08/21 19:29:24  dave
// minor syntax on contructor (forgot String)
//
// Revision 1.1  2001/08/21 19:26:48  dave
// new object to handle required attribute sets
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
* This manages a list of AttributeCodes that could be Required for a given Entity
* based upon the evaluation of the passed expression
*/
public class RequiredGroup extends EANMetaFoundation {

	private static final long serialVersionUID = 1L;
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

    /* Constructs a new RequiredGroup with the Key _s1, and the Evaluation expression _s2
    */
    /**
     * RequiredGroup
     *
     * @param _prof
     * @param _strID
     * @param _strEval
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public RequiredGroup(Profile _prof, String _strID, String _strEval) throws MiddlewareRequestException {
        super(null, _prof, _strID);
        m_strEval = _strEval;
    }

    /**
     * RequiredGroup
     *
     * @param _ef
     * @param _strID
     * @param _strEval
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public RequiredGroup(EANMetaFoundation _ef, String _strID, String _strEval) throws MiddlewareRequestException {
        super(_ef, null, _strID);
        m_strEval = _strEval;
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

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _brief) {

        StringBuffer sb = new StringBuffer();

        if (_brief) {
            sb.append("RequiredGroup:" + getKey() + ":" + toString());
        } else {
            sb.append("RequiredGroup:" + getKey() + ":" + toString());
            sb.append(NEW_LINE + "title:" + getShortDescription());
            sb.append(NEW_LINE + "desc:" + getLongDescription());
            sb.append(NEW_LINE + "Expression:" + m_strEval);
            sb.append(NEW_LINE + "AtributeCodes:");
            for (int x = 0; x < getAttributeCodeCount(); x++) {
                sb.append(NEW_LINE + TAB + getAttributeCode(x));
            }
        }

        return new String(sb);

    }

    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: RequiredGroup.java,v 1.30 2010/11/08 18:05:31 wendy Exp $";
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
        return EvaluatorII.test(_ei, m_strEval);
    }

    private String getParentEntityType() throws MiddlewareException {
        if (!(getParent() instanceof EntityGroup)) {
            throw new MiddlewareException("RequiredGroup.getParentEntityType(): parent is NOT an EntityGroup");
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
        //track updates to PDH
        boolean bUpdatePerformed = false;
        //is this group new (!in db)
        boolean bNewGroup = true;
        // 0) get a 'fresh' copy IF it exists  
        EntityGroup eg_db = null;
        RequiredGroup rg_db = null;

        try {
            //get a fresh object from database for compare 
            String strNow = _db.getDates().getNow();
            getProfile().setValOnEffOn(strNow, strNow);
            eg_db = new EntityGroup(null, _db, getProfile(), getParentEntityType(), "Edit");
            for (int i = 0; i < eg_db.getRequiredGroupCount(); i++) {
                RequiredGroup rgTest = eg_db.getRequiredGroup(i);
                if (rgTest.getKey().equals(this.getKey())) {
                    rg_db = rgTest;
                    bNewGroup = false; //NOT a new group coz' we found key
                    break;
                }
            }

            // 1) EXPIRE
            if (_bExpire && !bNewGroup) {
                // A) Entity/Group (Expression)
                bUpdatePerformed = (updateMLA(_db, true, "Entity/Group", rg_db.getParentEntityType(), rg_db.getKey(), "Required", rg_db.getExpression()) ? true : bUpdatePerformed);
                // B) Group/Attributes (x 'n' attributeCodes)
                for (int i = 0; i < rg_db.getAttributeCodeCount(); i++) {
                    bUpdatePerformed = (updateMLA(_db, true, "Group/Attribute", rg_db.getKey(), rg_db.getAttributeCode(i), "Required", "Y") ? true : bUpdatePerformed);
                }
            } else if (!_bExpire && bNewGroup) { // 2) new group - insert blindly
                // A) Entity/Group (Expression)
                bUpdatePerformed = (updateMLA(_db, false, "Entity/Group", this.getParentEntityType(), this.getKey(), "Required", this.getExpression()) ? true : bUpdatePerformed);
                // B) Group/Attributes (x 'n' attributeCodes)
                for (int i = 0; i < this.getAttributeCodeCount(); i++) {
                    bUpdatePerformed = (updateMLA(_db, false, "Group/Attribute", this.getKey(), this.getAttributeCode(i), "Required", "Y") ? true : bUpdatePerformed);
                }
            } else if (!_bExpire && !bNewGroup) { // 3) existing group to update
                if (!rg_db.getExpression().equals(this.getExpression())) {
                    //we can update b/c l.v. is only change
                    bUpdatePerformed = (updateMLA(_db, false, "Entity/Group", this.getParentEntityType(), this.getKey(), "Required", this.getExpression()) ? true : bUpdatePerformed);
                }
                // Group/Attributes
                // A) in db, !in new -> expire db record
                for (int i = 0; i < rg_db.getAttributeCodeCount(); i++) {
                    if (!this.containsAttributeCode(rg_db.getAttributeCode(i))) {
                        bUpdatePerformed = (updateMLA(_db, true, "Group/Attribute", rg_db.getKey(), rg_db.getAttributeCode(i), "Required", "Y") ? true : bUpdatePerformed);
                    }
                }
                // B) in new -> !in db -> insert new record
                for (int i = 0; i < this.getAttributeCodeCount(); i++) {
                    if (!rg_db.containsAttributeCode(this.getAttributeCode(i))) {
                        bUpdatePerformed = (updateMLA(_db, false, "Group/Attribute", this.getKey(), this.getAttributeCode(i), "Required", "Y") ? true : bUpdatePerformed);
                    }
                }
            }
        } catch (Exception exc) {
            _db.debug(D.EBUG_ERR, "Exception in RequiredGroup.updatePdhMeta(): " + exc.toString());
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
