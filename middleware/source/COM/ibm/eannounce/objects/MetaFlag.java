// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: MetaFlag.java,v $
// Revision 1.44  2010/11/08 17:25:44  wendy
// Replace EANList with Vector to reduce memory requirements
//
// Revision 1.43  2009/05/11 15:21:23  wendy
// Support dereference for memory release
//
// Revision 1.42  2009/02/18 16:18:18  wendy
// Check for null before rs.close()
//
// Revision 1.41  2007/05/29 17:26:28  wendy
// RQ1103065049 support sets of dependent flags
//
// Revision 1.40  2005/03/07 23:00:56  dave
// more Jtest
//
// Revision 1.39  2004/10/28 17:48:19  dave
// some minor fixes
//
// Revision 1.38  2004/01/15 00:21:27  dave
// syntax
//
// Revision 1.37  2004/01/15 00:11:54  dave
// final sqeeze on meta flag
//
// Revision 1.36  2004/01/12 21:57:08  dave
// more cleanup
//
// Revision 1.35  2004/01/12 21:48:39  dave
// more space claiming
//
// Revision 1.34  2003/11/05 17:52:37  gregg
// updatePdhMeta(): when creating in non-English : create NLSID = 1 MetaDescription record also.
//
// Revision 1.33  2003/11/04 23:07:01  gregg
// updatePdhMeta: fix for update MetaDescriptions when NLS!= 1
//
// Revision 1.32  2003/05/01 20:27:25  gregg
// remove commented-out code
//
// Revision 1.31  2003/05/01 20:22:23  gregg
// runAutoSelect logic to union autoselects on multi-selected values
//
// Revision 1.30  2003/04/28 23:23:21  gregg
// runAutoSelects
//
// Revision 1.29  2003/04/28 20:52:18  gregg
// more auto selects
//
// Revision 1.28  2003/04/28 19:43:37  gregg
// auto selects
//
// Revision 1.27  2003/01/30 21:57:14  gregg
// removed getControlBlock method due to apparent unuse
//
// Revision 1.26  2003/01/30 21:51:02  gregg
// removeController method
//
// Revision 1.25  2002/12/16 18:16:11  gregg
// return a boolean in updatePdhMeta method indicating whether any database updates were performed.
//
// Revision 1.24  2002/11/06 22:43:27  gregg
// removing display statements
//
// Revision 1.23  2002/05/20 18:35:14  gregg
// updatePdhMeta for Expired (i.e. Retired) flags
//
// Revision 1.22  2002/04/26 22:49:21  gregg
// some debugging
//
// Revision 1.21  2002/04/26 22:29:05  gregg
// getMetaDescriptionRows - use current time (not profile set times)
//
// Revision 1.20  2002/04/05 17:45:12  gregg
// getControlBlock method added
//
// Revision 1.19  2002/03/12 21:49:33  dave
// syntax
//
// Revision 1.18  2002/03/12 21:39:53  dave
// new Comparitor
//
// Revision 1.17  2002/03/12 01:09:39  gregg
// removeMetaFlag from parent on expire
//
// Revision 1.16  2002/03/08 23:20:24  gregg
// flip-flopped some Long/Short desc's
//
// Revision 1.15  2002/03/08 19:42:59  gregg
// bunch of new update stuff
//
// Revision 1.14  2002/03/05 04:58:02  dave
// more dump display cleanup
//
// Revision 1.13  2002/02/27 20:37:16  dave
// adding state machines and the such to entityGroup
//
// Revision 1.12  2002/02/02 20:56:54  dave
// tightening up code
//
// Revision 1.11  2002/02/01 01:21:26  dave
// another wave of foundation fixes
//
// Revision 1.10  2002/02/01 00:42:32  dave
// more foundation fixes for passing _prof
//
// Revision 1.9  2001/11/26 18:42:48  dave
// carry forward for metaflag fix
//
// Revision 1.8  2001/10/12 00:43:17  dave
// null pointer exception
//
// Revision 1.7  2001/10/12 00:18:18  dave
// test for MetaFlagAttributeList
//
// Revision 1.6  2001/10/09 16:12:10  dave
// more fixes
//
// Revision 1.5  2001/10/09 16:02:35  dave
// fixes
//
// Revision 1.4  2001/10/08 20:53:50  dave
// added final touch to MetaFlagAttributeList
//
// Revision 1.3  2001/10/05 22:33:04  dave
// more encapsulation
//
// Revision 1.2  2001/10/04 20:58:13  dave
// more massive changes to eannounce objects
//
// Revision 1.1  2001/10/04 20:29:41  dave
// serveral new adds and renames
//
// Revision 1.6  2001/08/22 16:52:50  roger
// Removed author RM
//
// Revision 1.5  2001/08/08 22:55:55  dave
// finishing touches on trace for displays
//
// Revision 1.4  2001/08/02 19:08:13  dave
// adjustments for compile
//
// Revision 1.3  2001/08/02 18:56:54  dave
// more fixes and sintax
//
// Revision 1.2  2001/08/02 17:27:00  dave
// Syntax Fixes
//
// Revision 1.1  2001/08/02 16:55:01  dave
// converted structures for use in the search API
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


/**
 * MetaFlag
 *
 * @author David Bigelow
 */
public class MetaFlag extends EANMetaFoundation {

    /**
    * @serial
    */

    static final long serialVersionUID = 1L;
    private String m_strFlagCode = null;
    private boolean m_bRestricted = false;
    private boolean m_bExpired = false;
    private Vector m_FilterSetVct = null;  // RQ1103065049 set of metaflags that filter this metaflag
    private Hashtable m_ControllerSetTbl = null; // RQ1103065049 sets of metaflags that share control of metaflags in the vct
    private Vector m_ControllerSetVct = null; // RQ1103065049 list of metaflags controlled by this metaflag
    // memchg  private EANList m_elFilters = null;
    private Vector m_elFilters = null;
    //memchg  private EANList m_elControllers = null;
    private Vector m_elControllers = null;
    //memchg  private EANList m_elAutoSelects = null; // the list of MetaFlags to turn on via autoselect, if this flag is turned on.
    private Vector m_elAutoSelects = null; 
	
    protected void dereference(){
    	super.dereference();
    	
    	m_strFlagCode = null;

        if (m_FilterSetVct!=null){
        	for (int i=0; i<m_FilterSetVct.size(); i++){
    			//memchg EANList eal = (EANList)m_FilterSetVct.elementAt(i);
        		Vector eal = (Vector)m_FilterSetVct.elementAt(i);
    			eal.clear();
    		}
        	m_FilterSetVct.clear();
        	m_FilterSetVct = null;
        }
        if (m_ControllerSetTbl!= null){
        	Enumeration e = m_ControllerSetTbl.elements();
        	while (e.hasMoreElements()) {
        		Vector setVct = (Vector) e.nextElement();
        		for (int i=0; i<setVct.size(); i++){
        			//memchg EANList eal = (EANList)setVct.elementAt(i);
        			Vector eal = (Vector)setVct.elementAt(i);
        			eal.clear();
        		}
        		setVct.clear();
        	}
        	m_ControllerSetTbl.clear();
        	m_ControllerSetTbl = null;
        }
        if (m_ControllerSetVct !=null){
       		m_ControllerSetVct.clear();
    		m_ControllerSetVct = null;
        }
        if (m_elAutoSelects !=null){
    		m_elAutoSelects.clear();
    		m_elAutoSelects = null;
        }
        if (m_elControllers !=null){
    		m_elControllers.clear();
    		m_elControllers = null;
        }
        if (m_elFilters !=null){
    		m_elFilters.clear();
    		m_elFilters = null;
        }
    }
    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /**
     * Creates the MetaFlag with no default flag code value
     *
     * @param _mf
     * @param _prof
     * @param _str
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public MetaFlag(EANMetaFoundation _mf, Profile _prof, String _str) throws MiddlewareRequestException {
        super(_mf, _prof, _mf.getKey() + _str);
        m_strFlagCode = _str;
    }

    /**
     * getFlagCode
     *
     * @return
     *  @author David Bigelow
     */
    public String getFlagCode() {
        return m_strFlagCode;
    }

    /**
     * setRestricted
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setRestricted(boolean _b) {
        m_bRestricted = _b;
    }

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: MetaFlag.java,v 1.44 2010/11/08 17:25:44 wendy Exp $";
    }

    /**
     * addFilter
     *
     * @param _ef
     *  @author David Bigelow
     */
    public void addFilter(MetaFlag _ef) {
        if (m_elFilters == null) {
            m_elFilters = new Vector();//memchg EANList();
        }
        if(!m_elFilters.contains(_ef)){
        	m_elFilters.add(_ef);//memchg .put(_ef);
        }
    }

    /**
     * addController
     *
     * @param _ef
     *  @author David Bigelow
     */
    public void addController(MetaFlag _ef) {
        if (m_elControllers == null) {
            m_elControllers = new Vector();//memchg  EANList();
        }
        if(!m_elControllers.contains(_ef)){
        	m_elControllers.add(_ef);//memchg put(_ef);
        }
    }

    /**
     * removeController
     *
     * @param _mf
     *  @author David Bigelow
     */
    protected void removeController(MetaFlag _mf) {
        if (m_elControllers != null) {
	        m_elControllers.remove(_mf);
        }
    }

    /**
     * getFilterCount
     *
     * @return the number of filters
     *  @author David Bigelow
     */
    public int getFilterCount() {
        if (m_elFilters == null) {
            return 0;
        }
        return m_elFilters.size();
    }

    /**
     * getControllerCount
     * @return the number of controllers
     *  @author David Bigelow
     */
    public int getControllerCount() {
        if (m_elControllers == null) {
            return 0;
        }
        return m_elControllers.size();
    }

    /**
     * isFiltered
     * this looks at normal pairs and sets of filters
     * @return boolean
     */
    public boolean isFiltered() {
        if (m_elFilters == null &&
        	m_FilterSetVct == null) { //RQ1103065049
            return false;
        }
        if (m_elFilters != null && m_elFilters.size() > 0) {
            return true;
        }
        if (m_FilterSetVct != null && m_FilterSetVct.size() > 0) {
            return true;
        }

        return false;
    }

    /**
     * isController
     * this looks at normal pairs and sets that control a metaflag
     * @return boolean
     */
    public boolean isController() {
        if (m_elControllers == null &&
        	m_ControllerSetVct == null) { //RQ1103065049
            return false;
        }
        if (m_elControllers != null && m_elControllers.size() > 0) {
            return true;
        }
        if (m_ControllerSetVct != null && m_ControllerSetVct.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * getFilter - normal pairs
     *
     * @param _i index
     * @return the filter at the given index
     *  @author David Bigelow
     */
    public MetaFlag getFilter(int _i) {
        if (m_elFilters == null || _i >= m_elFilters.size()) {
            return null;
        }
        return (MetaFlag) m_elFilters.elementAt(_i);//memchg ).getAt(_i);
    }

    /**
     * getController
     *
     * @param _i index
     * @return the control at the given index
     *  @author David Bigelow
     */
    public MetaFlag getController(int _i) {
        if (m_elControllers == null || _i >= m_elControllers.size()) {
            return null;
        }
        return (MetaFlag) m_elControllers.elementAt(_i);//memchg .getAt(_i);
    }

    /** RQ1103065049
     * addFilter set of filters for this metaflag
     *
     * @param mfList
     */
    //memchg  public void addFilterSet(EANList mfList) {
    public void addFilterSet(Vector mfList) {
        if (m_FilterSetVct == null) {
            m_FilterSetVct = new Vector();
        }
        m_FilterSetVct.addElement(mfList);
    }
    /** RQ1103065049
     * getFilterSetCount
     *
     * @return int
     */
    public int getFilterSetCount() {
        if (m_FilterSetVct == null) {
            return 0;
        }
        return m_FilterSetVct.size();
    }
    /**RQ1103065049
     * getFilterSet
     *
     * @param _i
     * @return EANList of MetaFlags used for filtering this metaflag
    memchg public EANList getFilterSet(int _i) {
        if (m_FilterSetVct == null) {
            return null;
        }
        return (EANList) m_FilterSetVct.elementAt(_i);
    }
     */
    public Vector getFilterSet(int _i) {
        if (m_FilterSetVct == null || _i >= m_FilterSetVct.size()) {
            return null;
        }
        return (Vector) m_FilterSetVct.elementAt(_i);
    }
    /** RQ1103065049
     * addController set for flag _ef, 'this' metaflag is one of the controller set, list is shared by all controllers
     *
     * @param _ef  controlled metaflag
     * @param mfList set of controllers
     */
   //memchg  public void addControllerSet(MetaFlag _ef,EANList mflist) {
    public void addControllerSet(MetaFlag _ef,Vector mflist) {
        if (m_ControllerSetTbl == null) {
            m_ControllerSetTbl = new Hashtable();
        }
        if (m_ControllerSetVct == null) {
            m_ControllerSetVct = new Vector();
        }

        Vector setVct = (Vector)m_ControllerSetTbl.get(_ef);
        if (setVct==null){
			setVct = new Vector();
	        m_ControllerSetTbl.put(_ef,setVct);
		}
        setVct.addElement(mflist);
        m_ControllerSetVct.addElement(_ef);
    }
    /** RQ1103065049
     * getControllerSetCount
     *
     * @return int
     */
    public int getControllerSetCount() {
        if (m_ControllerSetVct == null) {
            return 0;
        }
        return m_ControllerSetVct.size();
    }
    /**RQ1103065049
     * getControllerSetMF
     * get metaflag controlled by a set of flags, current metaflag is one of them
     * @param _i
     * @return MetaFlag
     */
    public MetaFlag getControllerSetMF(int _i) {
        if (m_ControllerSetVct == null) {
            return null;
        }
        return (MetaFlag) m_ControllerSetVct.elementAt(_i);
    }
    /**RQ1103065049
     * getControllerSet
     * get set of flags controlled by specified metaflag, current metaflag is one of the set
     * @param _mf
     * @return Vector of EANList of MetaFlags used for filtering this metaflag
     */
    public Vector getControllerSet(MetaFlag _mf) {
        if (m_ControllerSetTbl == null) {
            return null;
        }
        return (Vector) m_ControllerSetTbl.get(_mf);
    }
    /*
    * Returns the restictioncode
    */
    // public String getRestrictCode() {
    //   return m_strRestrictCode;
    //  }

    /* Sets the restrictCode
    */
    //  public void setRestrictCode(String _s) {
    //     m_strRestrictCode = _s;
    //  }

    /**
     * isRestricted
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isRestricted() {
        return m_bRestricted;
    }

    /**
     * isExpired
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isExpired() {
        return m_bExpired;
    }

    /**
     * setExpired
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setExpired(boolean _b) {
        m_bExpired = _b;
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public final String dump(boolean _bBrief) {

        StringBuffer strbResult = new StringBuffer();

        if (_bBrief) {
            strbResult.append(getKey());
        } else {
            strbResult.append(":KeyDesc:" + getKey());
            strbResult.append(":FlagCode:" + getFlagCode());
            strbResult.append(":ShortDesc:" + getShortDescription());
            strbResult.append(":LongDesc:" + getLongDescription());
            strbResult.append(":isExpired:" + isExpired());
            strbResult.append(":isRestricted:" + isRestricted());
            //strbResult.append(":RestrictCode:" + getRestrictCode());
            strbResult.append(NEW_LINE + "Filtered By:");
            for (int x = 0; x < getFilterCount(); x++) {
                MetaFlag mf = getFilter(x);
                strbResult.append(NEW_LINE + TAB + mf.getParent().getKey() + ":" + mf.getFlagCode());
            }
            strbResult.append(NEW_LINE + "Filtered By Sets:"); //RQ1103065049
            for (int x = 0; x < getFilterSetCount(); x++) {
                //memchg EANList el = getFilterSet(x);
                Vector el = getFilterSet(x);
                strbResult.append(NEW_LINE + TAB);
                for (int l=0; l<el.size(); l++){
                	MetaFlag mf = (MetaFlag)el.elementAt(l);//memchg getAt(l);
                	if (l>0){
						strbResult.append(":");
					}
                	strbResult.append(mf.getParent().getKey() + ":" + mf.getFlagCode());
				}
            }

            strbResult.append(NEW_LINE + "Controls:");
            for (int x = 0; x < getControllerCount(); x++) {
                MetaFlag mf = getController(x);
                strbResult.append(NEW_LINE + TAB + mf.getParent().getKey() + ":" + mf.getFlagCode());
            }
            strbResult.append(NEW_LINE + "ControlSet for:"); // RQ1103065049
            for (int x = 0; x < getControllerSetCount(); x++) {
                MetaFlag mf2 = getControllerSetMF(x);
				strbResult.append(NEW_LINE + TAB + mf2.getParent().getKey() + ":" + mf2.getFlagCode());
                Vector vct = getControllerSet(mf2);
                for (int v=0; v<vct.size(); v++){
					//memchg EANList el = (EANList)vct.elementAt(v);
					Vector el = (Vector)vct.elementAt(v);
					strbResult.append(NEW_LINE + TAB+"Set: ");
					for (int l=0; l<el.size(); l++){
						MetaFlag mf = (MetaFlag)el.elementAt(l);//memchg .getAt(l);
						if (l>0){
							strbResult.append(":");
						}
						strbResult.append(mf.getParent().getKey() + ":" + mf.getFlagCode());
					}
				}
            }
        }

        return new String(strbResult);

    }

    /**
     * Add a MetaFlag to the list of flag values to be set when *this* MetaFlag is turned on
     *
     * @param _mf
     */
    protected void addAutoSelectFlag(MetaFlag _mf) {
        if (m_elAutoSelects == null) {
            m_elAutoSelects = new Vector();//memchg EANList();
        }
        if(!m_elAutoSelects.contains(_mf)){
        	m_elAutoSelects.add(_mf);//memchg .put(_mf);
        }
    }

    /**
     * removeAutoSelectFlag
     *
     * @param _mf
     *  @author David Bigelow
     */
    protected void removeAutoSelectFlag(MetaFlag _mf) {
        if (m_elAutoSelects != null) {
        	m_elAutoSelects.remove(_mf);
        }
    }

    /**
     * getAutoSelectFlagCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getAutoSelectFlagCount() {
        if (m_elAutoSelects == null) {
            return 0;
        }
        return m_elAutoSelects.size();
    }

    /**
     * getAutoSelectFlag
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public MetaFlag getAutoSelectFlag(int _i) {
        if (m_elAutoSelects == null) {
            return null;
        }
        return (MetaFlag) m_elAutoSelects.elementAt(_i);//memchg .getAt(_i);
    }

    /**
     * getAutoSelectFlag
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public MetaFlag getAutoSelectFlag(String _s) {
    	if (m_elAutoSelects != null) {
    		for(int i=0; i<m_elAutoSelects.size(); i++){
    			MetaFlag eaf = (MetaFlag)m_elAutoSelects.elementAt(i);
    			if(eaf.getKey().equals(_s)){
    				return eaf;
    			}
    		}
    	}
        return null;
        //memchg return (MetaFlag) m_elAutoSelects.get(_s);
    }

    /**
     * hasAutoSelects
     *
     * @return
     *  @author David Bigelow
     */
    public boolean hasAutoSelects() {
        if (m_elAutoSelects == null) {
            return false;
        }
        return m_elAutoSelects.size() > 0;
    }

    /**
     * setAutoSelects
     *
     * @param _el
     *  @author David Bigelow
     */
    //memchg protected void setAutoSelects(EANList _el) {
    protected void setAutoSelects(Vector _el) {
        m_elAutoSelects = _el;
    }

    /**
     * getAutoSelects
     *
     * @return
     *  @author David Bigelow
     */
    //memchg protected EANList getAutoSelects() {
    protected Vector getAutoSelects() {
        if (m_elAutoSelects == null) {
            m_elAutoSelects = new Vector();//memchg EANList();
        }
        return m_elAutoSelects;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////// META UPDATES //////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Update the changes to EANMetaAttribute to the PDH
     *
     * @return boolean
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public boolean updatePdhMeta(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {
        return updatePdhMeta(_db, false);
    }

    /**
     * Expire the EANMetaAttribute in the PDH
     *
     * @return boolean
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public boolean expirePdhMeta(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {
        boolean bChanged = updatePdhMeta(_db, true);
        EANMetaFlagAttribute oEmfa = (EANMetaFlagAttribute) this.getParent();
        //this shouldn't be null if we made it this far, but just in case...
        if (oEmfa == null) {
            throw new MiddlewareException("MetaFlag.expirePdhMeta() parent is null!");
        }
        oEmfa.removeMetaFlag(this);
        return bChanged;
    }

    /**
    * toggle expire
    */
    private boolean updatePdhMeta(Database _db, boolean _bIsExpire) throws SQLException, MiddlewareException, MiddlewareRequestException {

        String strParentAttCode = null;
        boolean bChanged = false;
        boolean bIsNewFlag = false;

        String strFlagCode = getFlagCode();
        EANList oEl = getMetaDescriptionRowsForUpdate(_db, _bIsExpire, bIsNewFlag);

        //if this MetaFlag has no parent, then it is !valid
        EANMetaFlagAttribute oEmfa = (EANMetaFlagAttribute) getParent();
        if (oEmfa == null) {
            String s = "updatePdhMeta";
            if (_bIsExpire) {
                s = "expirePdhMeta";
            }
            throw new MiddlewareException("MetaFlag: in " + s + " method-->getParent() cannot be null!");
        }
        strParentAttCode = oEmfa.getAttributeCode();

        //does this attribute exist in the MetaEntity table??
        bIsNewFlag = MetaEntityList.isNewFlagCode(_db, getProfile(), strParentAttCode, strFlagCode);
        //1) MetaDescription table
        for (int i = 0; i < oEl.size(); i++) {
            MetaDescriptionRow oMdRow = (MetaDescriptionRow) oEl.getAt(i);
            oMdRow.updatePdh(_db);
            bChanged = true;
        }

        //2) MetaLinkAttr table
        oEl = getMetaLinkAttrRowsForUpdate(_db, _bIsExpire, bIsNewFlag);
        for (int i = 0; i < oEl.size(); i++) {
            MetaLinkAttrRow oMlaRow = (MetaLinkAttrRow) oEl.getAt(i);
            oMlaRow.updatePdh(_db);
            bChanged = true;
        }
        return bChanged;
    }

    /**
    * Get the MetaDescriptionRows for any changes relavent to MetaDescription table.
    */
    private EANList getMetaLinkAttrRowsForUpdate(Database _db, boolean _bIsExpire, boolean _bIsNewFlag) throws SQLException, MiddlewareException, MiddlewareRequestException {
        EANList eList = new EANList();
        String strFlagCode = this.getFlagCode();
        String strNow = _db.getDates().getNow();
        String strForever = _db.getDates().getForever();
        String strEnterprise = getProfile().getEnterprise(); //getProfile().getValOn(); //getProfile().getEffOn();
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;

        //if this MetaFlag has no parent, then it is !valid
        EANMetaFlagAttribute oEmfa = (EANMetaFlagAttribute) getParent();
        String strParentAttCode = oEmfa.getAttributeCode();

        boolean bExpired_db = false;
        //check the db to see if this guy is currently expired
        try {
            rs = _db.callGBL7503(new ReturnStatus(-1), strEnterprise, "Attribute/Flag", strParentAttCode, strFlagCode, "Expired", strNow, strNow);
            rdrs = new ReturnDataResultSet(rs);
        } finally {
        	if (rs!=null){
        		rs.close();
        		rs = null;
        	}
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
        //if there is a record here, then it is expired in the database..
        if (rdrs.getRowCount() > 0) {
            bExpired_db = true;
        }

        //UPDATE
        if (!_bIsExpire) {
            //1) new Entity
            if (_bIsNewFlag) {
                if (isExpired()) {
                    MetaLinkAttrRow oMlaRow = new MetaLinkAttrRow(getProfile(), "Attribute/Flag", strParentAttCode, strFlagCode, "Expired", "Y", strNow, strForever, strNow, strForever, 2);
                    eList.put(oMlaRow);
                }
            } else { // update the MLA records if necessary
                if (isExpired() != bExpired_db) {
                    String strToDate = (!isExpired() ? strNow :strForever);
                    MetaLinkAttrRow oMlaRow = new MetaLinkAttrRow(getProfile(), "Attribute/Flag", strParentAttCode, strFlagCode, "Expired", "Y", strNow, strToDate, strNow, strToDate, 2);
                    eList.put(oMlaRow);
                }
            }
        }
        //EXPIRE
        else if (!_bIsNewFlag) { //if it isnt in database -> we dont need to remove it from database (duh..)
            if (bExpired_db) {
                MetaLinkAttrRow oMlaRow = new MetaLinkAttrRow(getProfile(), "Attribute/Flag", strParentAttCode, strFlagCode, "Expired", "Y", strNow, strNow, strNow, strNow, 2);
                eList.put(oMlaRow);
            }
        }
        return eList;
    }

    /**
    * Get the MetaDescriptionRows for any changes relavent to MetaDescription table.
    */
    private EANList getMetaDescriptionRowsForUpdate(Database _db, boolean _bIsExpire, boolean _bIsNewFlag) throws SQLException, MiddlewareException, MiddlewareRequestException {
        EANList eList = new EANList();
        String strFlagCode = this.getFlagCode();

        //_db.debug(D.EBUG_SPEW,"(TESTING) -->entering getMetaDescriptionRowsForUpdate():" + strFlagCode);

        String strShortDesc = this.getShortDescription();
        String strLongDesc = this.getLongDescription();
        String strNow = _db.getDates().getNow();
        String strForever = _db.getDates().getForever();
        String strEnterprise = getProfile().getEnterprise(); //getProfile().getValOn(); //getProfile().getEffOn();
        int iNLSID = getProfile().getReadLanguage().getNLSID();
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;

        //if this MetaFlag has no parent, then it is !valid
        EANMetaFlagAttribute oEmfa = (EANMetaFlagAttribute) getParent();
        String strParentAttCode = oEmfa.getAttributeCode();

        //_db.debug(D.EBUG_SPEW,"(TESTING) " + strParentAttCode + ":" + strFlagCode + ":" + _bIsExpire + ":" + _bIsNewFlag);

        //UPDATE
        if (!_bIsExpire) {
            //1) new Entity
            if (_bIsNewFlag) {
                MetaDescriptionRow oMdRow = new MetaDescriptionRow(getProfile(), strParentAttCode, strFlagCode, strShortDesc, strLongDesc, iNLSID, strNow, strForever, strNow, strForever, 2);
                eList.put(oMdRow);
                // RE: create NLSID ==1 !!!
                if (iNLSID != 1) {
                    eList.put(new MetaDescriptionRow(getProfile(), strParentAttCode, strFlagCode, strShortDesc, strLongDesc, 1, strNow, strForever, strNow, strForever, 2));
                }
            } else { // update the description

                boolean bNLSFound = false;

                //pull out descrips from the database to compare these...
                try {
                    rs = _db.callGBL7511(new ReturnStatus(-1), strEnterprise, strParentAttCode, strFlagCode);
                    rdrs = new ReturnDataResultSet(rs);
                } finally {
                	if (rs!= null){
                		rs.close();
                		rs = null;
                	}
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();
                }

                // track if our current NLSID has been found (for cases when NLSID != 1)
                for (int row = 0; row < rdrs.getRowCount(); row++) {
                    int iNLSID_db = rdrs.getColumnInt(row, 0);
                    String strShortDesc_db = rdrs.getColumn(row, 1);
                    String strLongDesc_db = rdrs.getColumn(row, 2);
                    _db.debug(D.EBUG_SPEW, "gbl7511 answers: " + iNLSID_db + ":" + strShortDesc_db + ":" + strLongDesc_db);
                    //there should be only one valid record for ea. nlsid - update only current nls
                    if (iNLSID == iNLSID_db) {
                        bNLSFound = true;
                        if (!strShortDesc.equals(strShortDesc_db) || !strLongDesc.equals(strLongDesc_db)) {
                            //put new desc's - 2909 will expire old ones
                            eList.put(new MetaDescriptionRow(getProfile(), strParentAttCode, strFlagCode, strShortDesc, strLongDesc, iNLSID, strNow, strForever, strNow, strForever, 2));
                            //row = rdrs.getRowCount(); //break
                        }
                    }
                }
                // now update non-1 new NLS's
                if (!bNLSFound) {
                    eList.put(new MetaDescriptionRow(getProfile(), strParentAttCode, strFlagCode, strShortDesc, strLongDesc, iNLSID, strNow, strForever, strNow, strForever, 2));
                }
            }
        }
        //EXPIRE
        else if (!_bIsNewFlag) { //if it isnt in database -> we dont need to remove it from database (duh..)
            //we must expire ALL nlsId's!
            try {
                rs = _db.callGBL7511(new ReturnStatus(-1), strEnterprise, strParentAttCode, strFlagCode);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs!= null){
            		rs.close();
            		rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                int iNLSID_nls = rdrs.getColumnInt(row, 0);
                String strShortDesc_nls = rdrs.getColumn(row, 1);
                String strLongDesc_nls = rdrs.getColumn(row, 2);
                _db.debug(D.EBUG_SPEW, "gbl7511 answers: " + iNLSID_nls + ":" + strShortDesc_nls + ":" + strLongDesc_nls);
                eList.put(new MetaDescriptionRow(getProfile(), strParentAttCode, strFlagCode, strShortDesc_nls, strLongDesc_nls, iNLSID_nls, strNow, strNow, strNow, strNow, 2));
            }
        }
        return eList;
    }

}
