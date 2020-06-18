//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ExtractActionItem.java,v $
// Revision 1.69  2009/08/26 17:56:18  wendy
// SR5 updates
//
// Revision 1.68  2009/05/18 23:04:57  wendy
// Support dereference for memory release
//
// Revision 1.67  2009/05/11 13:53:02  wendy
// Support turning off domain check for all actions
//
// Revision 1.66  2009/03/12 14:59:14  wendy
// Add methods for metaui access
//
// Revision 1.65  2008/04/29 19:27:56  wendy
// MN35270066 VEEdit rewrite
//
// Revision 1.64  2007/10/29 20:13:32  wendy
// Added check for rs!=null before rs.close()
//
// Revision 1.63  2007/06/19 12:50:02  wendy
// Prevent ArrayIndexOutOfBoundsException on display order
//
// Revision 1.62  2007/04/24 18:16:27  wendy
// Added support for VEEdit delete
//
// Revision 1.61  2006/05/16 15:32:20  tony
// Fix out of bounds exception
//
// Revision 1.60  2006/02/20 21:39:47  joan
// clean up System.out.println
//
// Revision 1.59  2006/02/20 19:23:51  tony
// 6LY42Z
//
// Revision 1.58  2006/01/05 21:30:30  tony
// VEEdit BUI functionality enhancement
//
// Revision 1.57  2005/11/14 15:16:45  tony
// VEEdit_Iteration3
// PRODSTRUCT Functionality.
//
// Revision 1.56  2005/11/10 21:25:17  tony
// VEEdit_Iteration3
//
// Revision 1.55  2005/11/04 16:50:13  tony
// VEEdit_Iteration2
//
// Revision 1.54  2005/11/04 14:52:10  tony
// VEEdit_Iteration2
// updated VEEdit logic to meet new requirements.
//
// Revision 1.53  2005/10/12 19:59:32  tony
// VEEdit_Create
//
// Revision 1.52  2005/10/12 19:24:52  tony
// updated veEdit logic
//
// Revision 1.51  2005/10/12 19:17:56  tony
// VEEdit_Create
//
// Revision 1.50  2005/08/22 19:38:19  tony
// adjusted logic.
//
// Revision 1.49  2005/08/22 19:22:45  tony
// added VEEdit functionality
//
// Revision 1.48  2005/08/10 16:14:23  tony
// improved catalog viewer functionality.
//
// Revision 1.47  2005/08/03 17:09:44  tony
// added datasource logic for catalog mod
//
// Revision 1.46  2005/03/03 18:36:52  dave
// more Jtest
//
// Revision 1.45  2005/01/18 21:46:49  dave
// more parm debug cleanup
//
// Revision 1.44  2004/07/15 17:28:59  joan
// add Single input
//
// Revision 1.43  2003/10/30 00:43:32  dave
// fixing all the profile references
//
// Revision 1.42  2003/07/16 19:16:28  dave
// adding skipcleanup
//
// Revision 1.41  2003/07/02 21:14:06  gregg
// setRoleCodeOverride in copy constructors!
//
// Revision 1.40  2003/07/01 22:08:37  gregg
// hasRoleCodeOverride() method
//
// Revision 1.39  2003/07/01 21:16:24  gregg
// RoleCodeOverride
//
// Revision 1.38  2003/05/09 19:38:41  dave
// making a blobnow
//
// Revision 1.37  2003/04/08 03:00:16  dave
// commit()
//
// Revision 1.36  2003/03/10 17:17:59  dave
// attempting to remove GBL7030 from the abstract Action Item
//
// Revision 1.35  2003/02/04 20:51:13  gregg
// method name change hasInterval->hasIntervalItem
//
// Revision 1.34  2003/02/04 01:13:35  gregg
// add setIntervalItem in copy constructor
//
// Revision 1.33  2003/02/04 01:11:48  gregg
// hasIntervalItem., getIntervalItem made protected
//
// Revision 1.32  2003/02/04 01:09:22  gregg
// hasIntervalItem, getIntervalItem, setIntervalItem methods
//
// Revision 1.31  2002/12/27 20:44:01  gregg
// ssetPullTargetEntitiesOnly(_ai.pullTargetEntitiesOnly()) in copy constructor
//
// Revision 1.30  2002/11/22 22:08:11  dave
// too much static
//
// Revision 1.29  2002/11/22 21:50:02  dave
// some cleanup
//
// Revision 1.28  2002/11/22 20:59:31  dave
// adding more to the map for tree walking
//
// Revision 1.27  2002/11/22 19:46:54  dave
// szin-taughx fix
//
// Revision 1.26  2002/11/22 19:35:28  dave
// shine-tawx foxes
//
// Revision 1.25  2002/11/22 19:21:20  dave
// generalizing step generator
//
// Revision 1.24  2002/11/22 18:39:00  dave
// Extract Action Path
//
// Revision 1.23  2002/08/28 21:38:24  gregg
// more updatePdhMeta
//
// Revision 1.22  2002/08/28 20:47:14  gregg
// insert new records in updatePdhMeta
//
// Revision 1.21  2002/08/28 20:31:41  gregg
// guts of updatePdhMeta method
//
// Revision 1.20  2002/08/23 21:59:54  gregg
// updatePdhMeta method throws MiddlewareException
//
// Revision 1.19  2002/08/23 21:29:45  gregg
// updatePdhMeta(Database,boolean) method
//
// Revision 1.18  2002/08/13 16:39:46  dave
// syntax fix
//
// Revision 1.17  2002/08/13 16:19:56  dave
// fixed syntax
//
// Revision 1.16  2002/08/13 16:08:54  dave
// adding changes when the Extract  Action gets copied via constructor
//
// Revision 1.15  2002/08/13 16:06:10  dave
// added a holding vector for the VE steps
//
// Revision 1.14  2002/08/12 22:36:33  dave
// unit test fixes on Extract and ECCM queue transfer
//
// Revision 1.13  2002/08/12 18:35:34  dave
// syntax
//
// Revision 1.12  2002/08/12 18:14:48  dave
// added the call 8004 to the extract action item
//
// Revision 1.11  2002/07/30 19:21:43  dave
// syntax
//
// Revision 1.10  2002/07/30 18:23:05  dave
// Sytax errors
//
// Revision 1.9  2002/07/30 18:05:47  dave
// QUEUE II logic
//
// Revision 1.8  2002/06/07 00:31:56  gregg
// pullTargetEntitiesOnly
//
// Revision 1.7  2002/06/06 22:53:23  gregg
// some more logic for pullAttributes
//
// Revision 1.6  2002/06/06 22:48:34  gregg
// pullAttributes stuff
//
// Revision 1.5  2002/05/14 23:44:07  dave
// Syntax
//
// Revision 1.4  2002/05/14 23:35:47  dave
// syntax fixes
//
// Revision 1.3  2002/05/14 23:10:44  dave
// changes for joan and my shift right
//
// Revision 1.2  2002/04/05 20:16:55  dave
// syntax fixes
//
// Revision 1.1  2002/04/05 19:47:39  dave
// new ExractActionItem class
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

// Temp made this non abstract because we need to compile in general before we whip up
// how actions are suppose to differentiate
// Actually we should be storing the metalink used to do this right
/**
 * ExtractActionItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ExtractActionItem extends EANActionItem {

    static final long serialVersionUID = 20011106L;

    private String m_strEntityType = "";
    private boolean m_bPullAttributes = true;
    private boolean m_bPullTargetEntitiesOnly = false;
    private boolean m_bQueueSource = false;
    private Hashtable m_hshSteps = new Hashtable();
    private IntervalItem m_intervalItem = null;
    private String m_strRoleCodeOverride = null;
    private Hashtable m_hDelete = null;					//VEEdit_Delete MN30841458
    private Hashtable m_hDisplay = null;				//VEEdit_Iteration2
    private Vector m_vCreate = null;					//VEEdit_Iteration2
	private Vector m_vDuplicate = null;					//VEEdit_Iteration2
	private Vector m_vEdit = null;						//VEEdit_Iteration2
	private Vector m_vAttribute = null;					//VEEdit_Iteration3
	private Vector m_vTrailAttribute = null;			//TIR USRO-R-LBAR-6LYL96
    private boolean m_bCreatable = false;				//VEEdit_Iteration2
    private boolean m_bDuplicatable = false;			//VEEdit_Iteration2
    private boolean m_bEditable = false;				//VEEdit_Iteration2
    private String m_vepath = null;

    private boolean m_bBypassCommitChecks = false; // SR5
    /**
     * FIELD
     */
    protected boolean m_bSkipCleanup = false;
    
    public void dereference(){
    	super.dereference();
    	m_strEntityType = null;
    	m_strRoleCodeOverride = null;
    	if(m_hshSteps !=null){
    		m_hshSteps.clear();
    		m_hshSteps = null;
    	}
    	if(m_hDelete !=null){
    		m_hDelete.clear();
    		m_hDelete = null;
    	}
    	if(m_hDisplay !=null){
    		m_hDisplay.clear();
    		m_hDisplay = null;
    	}
    	if (m_intervalItem != null){
    		m_intervalItem.dereference();
    		m_intervalItem = null;
    	}
        
    	if(m_vCreate !=null){
    		m_vCreate.clear();
    		m_vCreate = null;
    	}
    	if(m_vDuplicate !=null){
    		m_vDuplicate.clear();
    		m_vDuplicate = null;
    	}
    	if(m_vEdit !=null){
    		m_vEdit.clear();
    		m_vEdit = null;
    	}
    	if(m_vAttribute !=null){
    		m_vAttribute.clear();
    		m_vAttribute = null;
    	}
    	if(m_vTrailAttribute !=null){
    		m_vTrailAttribute.clear();
    		m_vTrailAttribute = null;
    	}
    	
        m_vepath = null;
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: ExtractActionItem.java,v 1.69 2009/08/26 17:56:18 wendy Exp $";
    }

    /**
     * ExtractActionItem
     * Instantiate a new ActionItem based upon a dereferenced version of the Existing One
     *
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public ExtractActionItem(ExtractActionItem _ai) throws MiddlewareRequestException {
        super(_ai);
        setTargetEntityType(_ai.getTargetEntityType());
        setPullAttributes(_ai.pullAttributes());
        setQueueSource(_ai.isQueueSourced());
        setPullTargetEntitiesOnly(_ai.pullTargetEntitiesOnly());
        m_hshSteps = _ai.getVESteps();
        setIntervalItem(_ai.getIntervalItem());
        setRoleCodeOverride(_ai.getRoleCodeOverride());
        m_bSkipCleanup = _ai.m_bSkipCleanup;
		setCreate(_ai.canCreate());							//VEEdit_Iteration2
		setCreatable(_ai.getCreatable());					//VEEdit_Iteration2
		setEditable(_ai.canEdit());							//VEEdit_Iteration2
		setDisplay(_ai.getDisplay());						//VEEdit_Iteration2
		setDelete(_ai.getDelete());						//VEEdit_Delete MN30841458
		setDuplicate(_ai.canDuplicate());					//VEEdit_Iteration2
		setDuplicatable(_ai.getDuplicatable());				//VEEdit_Iteration2
		setDisplayAttribute(_ai.getDisplayAttributes());	//VEEdit_Iteration3
		setTrailDisplayAttribute(_ai.getTrailDisplayAttributes());		//TIR USRO-R-LBAR-6LYL96
		setEditable(_ai.getEditable());						//VEEdit_Iteration2
		setVEPath(_ai.getVEPath());
		m_bBypassCommitChecks = _ai.m_bBypassCommitChecks;
    }

    /**
     * ExtractActionItem
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public ExtractActionItem(EANMetaFoundation _mf, ExtractActionItem _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
        setTargetEntityType(_ai.getTargetEntityType());
        setPullAttributes(_ai.pullAttributes());
        setQueueSource(_ai.isQueueSourced());
        setPullTargetEntitiesOnly(_ai.pullTargetEntitiesOnly());
        m_hshSteps = _ai.getVESteps();
        setIntervalItem(_ai.getIntervalItem());
        setRoleCodeOverride(_ai.getRoleCodeOverride());
        m_bSkipCleanup = _ai.m_bSkipCleanup;
		setCreate(_ai.canCreate());							//VEEdit_Iteration2
		setCreatable(_ai.getCreatable());					//VEEdit_Iteration2
		setEditable(_ai.canEdit());							//VEEdit_Iteration2
		setDisplay(_ai.getDisplay());						//VEEdit_Iteration2
		setDelete(_ai.getDelete());						//VEEdit_Delete MN30841458
		setDuplicate(_ai.canDuplicate());					//VEEdit_Iteration2
		setDuplicatable(_ai.getDuplicatable());				//VEEdit_Iteration2
		setDisplayAttribute(_ai.getDisplayAttributes());	//VEEdit_Iteration3
		setTrailDisplayAttribute(_ai.getTrailDisplayAttributes());		//TIR USRO-R-LBAR-6LYL96
		setEditable(_ai.getEditable());						//VEEdit_Iteration2
		setVEPath(_ai.getVEPath());
		m_bBypassCommitChecks = _ai.m_bBypassCommitChecks;
    }  

    /**
     * This represents an Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key*
     *
     * @param _emf
     * @param _db
     * @param _prof
     * @param _strActionItemKey
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public ExtractActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey)
    throws SQLException, MiddlewareException, MiddlewareRequestException
    {
        super(_emf, _db, _prof, _strActionItemKey);

        // Lets go get the information pertinent to the Create Action Item

        try {

            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs;
            Profile prof = getProfile();
            Vector attrDisplayVct = new Vector(1);
            Vector trailattrDisplayVct = new Vector(1);

            // Retrieve the Action Class and the Action Description.
            try {
                rs = _db.callGBL7030(returnStatus, prof.getEnterprise(), _strActionItemKey, prof.getValOn(), prof.getEffOn());
                rdrs = new ReturnDataResultSet(rs);
            } finally {
				if (rs !=null){
            	    rs.close();
            	    rs = null;
				}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

			setCreate(false);							//VEEdit_Iteration2
			setDuplicate(false);						//VEEdit_Iteration2
			setEditable(false);							//VEEdit_Iteration2
			setDomainCheck(true);
			
            // Set the class and description...
            for (int ii = 0; ii < rdrs.size(); ii++) {

                String strType = rdrs.getColumn(ii, 0);
                String strCode = rdrs.getColumn(ii, 1);
                String strValue = rdrs.getColumn(ii, 2);

                _db.debug(D.EBUG_SPEW, "gbl7030 answer is:" + strType + ":" + strCode + ":" + strValue + ":");


                // Collect the attributes.. For the extract.. they are yet to be defined.
                // Added the associated EntityType here (instead of the abstract object
                if (strType.equals("TYPE") && strCode.equals("Target")) {
                    setTargetEntityType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("Attributes")) {
                    setPullAttributes((strValue.equals("N") ? false : true));
                } else if (strType.equals("TYPE") && strCode.equals("TargetOnly")) {
                    setPullTargetEntitiesOnly((strValue.equals("Y") ? true : false));
                } else if (strType.equals("TYPE") && strCode.equals("QUEUESOURCE")) {
                    setQueueSource((strValue.equals("Y") ? true : false));
                } else if (strType.equals("ENTITYTYPE") && strCode.equals("Link")) {
                    super.setAssociatedEntityType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("RoleOverride")) {
                    setRoleCodeOverride(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("SKIPCLEANUP")) {
                    setSkipCleanup((strValue.equals("Y") ? true : false));
                } else if (strType.equals("TYPE") && strCode.equals("SingleInput")) {
                    setSingleInput(true);
				} else if (strType.equals("TYPE") && strCode.equals("DataSource")) {	//catalog enhancement
					setDataSource(strValue);											//catalog enhancement
				} else if (strType.equals("TYPE") && strCode.equals("DataSourceParms")) {
					setAdditionalParms(strValue);
 				} else if (strType.equals("TYPE") && strCode.equals("VEEdit")) {		//VEEdit_Iteration2
					setVEEdit(true);													//VEEdit_Iteration2
					setTargetType(strValue);											//VEEdit_Iteration2
				} else if (strType.equals("TYPE") && strCode.equals("Edit")) {			//VEEdit_Iteration2
					setEditable(true);													//VEEdit_Iteration2
					setEditable(strValue);												//VEEdit_Iteration2
				} else if (strType.equals("TYPE") && strCode.equals("Create")) {		//VEEdit_Iteration2
					setCreate(true);													//VEEdit_Iteration2
					setCreatable(strValue);												//VEEdit_Iteration2
                } else if (strType.equals("TYPE") && strCode.equals("Duplicate")) {		//VEEdit_Iteration2
                	setDuplicate(true);													//VEEdit_Iteration2
                	setDuplicatable(strValue);											//VEEdit_Iteration2
				} else if (strType.equals("DISPLAY")) {									//VEEdit_Iteration2
					setDisplay(strCode,strValue);										//VEEdit_Iteration2
				} else if (strType.equals("ATTRIBUTEDISPLAY")) {						//VEEdit_Iteration3
					//setDisplayAttribute(strCode,strValue);								//VEEdit_Iteration3
					// was getting exception if seq was out of order, get all, sort and then add
					attrDisplayVct.addElement(new DisplayValue(strCode,strValue));
				} else if (strType.equals("TRAILATTRIBUTEDISPLAY")) {					//TIR USRO-R-LBAR-6LYL96
					//setTrailDisplayAttribute(strCode,strValue);							//TIR USRO-R-LBAR-6LYL96
					// was getting exception if seq was out of order, get all, sort and then add
					trailattrDisplayVct.addElement(new DisplayValue(strCode,strValue));
				} else if (strType.equals("DELETE")) {		//VEEdit_Delete MN30841458
					setDelete(strCode, strValue);			//VEEdit_Delete MN30841458
                } else if (strType.equals("TYPE") && strCode.equals("VEPath")){ // this is now required for VEEdit
					setVEPath(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("DomainCheck")) { // default is on, allow off
				    setDomainCheck(!strValue.equals("N")); //RQ0713072645
                } else if (strType.equals("TYPE") && strCode.equals("BypassCommitCheck")) { // SR5
					setBypassCommitChecks(strValue.equalsIgnoreCase("Y"));
                } else {
                    _db.debug(D.EBUG_ERR, "*** ACTION ITEM ATTRIBUTE *** No home for this Action/Attribute " + strType + ":" + strCode + ":" + strValue);
                }
            }

            if (isVEEdit()){
				if(getVEPath()==null){
					throw new MiddlewareException(_strActionItemKey+" VEEdit requires a VEPath definition");
				}
				StringTokenizer st = new StringTokenizer(getVEPath(),":"); // D:MODELBOM:BOM
				if (st.countTokens()!=3){
					throw new MiddlewareException(_strActionItemKey+" VEEdit VEPath requires Dir:Relator:Entity format");
				}
			}

            if (attrDisplayVct.size()>0){
				// sort and set display now
				java.util.Collections.sort(attrDisplayVct);
				for (int i=0; i<attrDisplayVct.size(); i++){
					DisplayValue dv = (DisplayValue)attrDisplayVct.elementAt(i);
					setDisplayAttribute(dv.order,dv.value);
					dv.dereference();
				}
			}
            if (trailattrDisplayVct.size()>0){
				// sort and set display now
				java.util.Collections.sort(trailattrDisplayVct);
				for (int i=0; i<trailattrDisplayVct.size(); i++){
					DisplayValue dv = (DisplayValue)trailattrDisplayVct.elementAt(i);
					setTrailDisplayAttribute(dv.order,dv.value);
					dv.dereference();
				}
			}

            attrDisplayVct.clear();
            trailattrDisplayVct.clear();
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }
    private void setBypassCommitChecks(boolean b){ // SR 5
    	m_bBypassCommitChecks = b;
    }
    public boolean isBypassCommitChecks() { // SR 5
        return m_bBypassCommitChecks;
    }
    /**********
    * Used to prevent exception if _seq do not get loaded in order
	* from Vector api doc:add(int index, Object element) throws
	* ArrayIndexOutOfBoundsException - index is out of range (index < 0 || index > size()).
    */
    private static class DisplayValue implements Comparable
    {
        private String order;
        private String value;
        DisplayValue(String _seq, String v)
        {
			order = _seq;
			value = v;
        }
        void dereference()
        {
            order=null;
            value=null;
        }
        public int compareTo(Object o) // used by Collections.sort()
        {
            DisplayValue sma = (DisplayValue)o;
            // sort by order
            return (order).compareTo(sma.order);
        }
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {

        StringBuffer strbResult = new StringBuffer();
        strbResult.append("ExtractActionItem:" + super.dump(_bBrief));
        strbResult.append("\nTargetEntityType:" + getTargetEntityType());
        strbResult.append("\nQueueSource?:" + isQueueSourced());

        return strbResult.toString();

    }

    /**
     * setIntervalItem
     *
     * @param _intervalItem
     *  @author David Bigelow
     */
    public void setIntervalItem(IntervalItem _intervalItem) {
        m_intervalItem = _intervalItem;
    }

    /**
     * hasIntervalItem
     *
     * @return
     *  @author David Bigelow
     */
    protected boolean hasIntervalItem() {
        return getIntervalItem() != null;
    }

    /**
     * getIntervalItem
     *
     * @return
     *  @author David Bigelow
     */
    protected IntervalItem getIntervalItem() {
        return m_intervalItem;
    }

    /**
     * Specify that no attributes should be pulled in extract
     *
     * @param _b
     */
    protected void setPullAttributes(boolean _b) {
        m_bPullAttributes = _b;
    }

    /**
     * setPullTargetEntitiesOnly
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setPullTargetEntitiesOnly(boolean _b) {
        m_bPullTargetEntitiesOnly = _b;
        return;
    }

    /**
     * pullAttributes
     *
     * @return
     *  @author David Bigelow
     */
    public boolean pullAttributes() {
        return m_bPullAttributes;
    }

    /**
     * pullTargetEntitiesOnly
     *
     * @return
     *  @author David Bigelow
     */
    public boolean pullTargetEntitiesOnly() {
        return m_bPullTargetEntitiesOnly;
    }

    /**
     * getTargetEntityType
     *
     * @return
     *  @author David Bigelow
     */
    public String getTargetEntityType() {
        return m_strEntityType;
    }

    /**
     * setTargetEntityType
     *
     * @param _str
     *  @author David Bigelow
     */
    public void setTargetEntityType(String _str) {
        m_strEntityType = _str;
    }

    /**
     * getSkipCleanup
     *
     * @return
     *  @author David Bigelow
     */
    public boolean getSkipCleanup() {
        return m_bSkipCleanup;
    }

    /**
     * setSkipCleanup
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setSkipCleanup(boolean _b) {
        m_bSkipCleanup = _b;
    }
    /*********
     * meta ui must update action
     */
    public void setActionClass(){
    	setActionClass("Extract");
    }
    public void updateAction(boolean bQueueType, boolean bPullAtts){
    	setQueueSource(bQueueType);
        setPullAttributes(bPullAtts);
    }    
    /**
     * setQueueSource
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setQueueSource(boolean _b) {
        m_bQueueSource = _b;
    }

    /**
     * isQueueSourced
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isQueueSourced() {
        return m_bQueueSource;
    }

    /**
     * setRoleCodeOverride
     *
     * @param _strRoleCode
     *  @author David Bigelow
     */
    protected void setRoleCodeOverride(String _strRoleCode) {
        m_strRoleCodeOverride = _strRoleCode;
    }

    /**
     * getRoleCodeOverride
     *
     * @return
     *  @author David Bigelow
     */
    public String getRoleCodeOverride() {
        return m_strRoleCodeOverride;
    }

    /**
     * hasRoleCodeOverride
     *
     * @return
     *  @author David Bigelow
     */
    public boolean hasRoleCodeOverride() {
        return (getRoleCodeOverride() != null);
    }

    /**
     * (non-Javadoc)
     * getPurpose
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#getPurpose()
     */
    public String getPurpose() {
        return "Extract";
    }

    /**
     * getVESteps
     *
     * @return
     *  @author David Bigelow
     */
    protected Hashtable getVESteps() {
        return m_hshSteps;
    }

    /**
     * generateVESteps
     *
     * @param _db
     * @param _prof
     * @param _strEntityType
     * @return
     *  @author David Bigelow
     */
    public Hashtable generateVESteps(Database _db, Profile _prof, String _strEntityType) {

        // Reset the hashTable
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        ReturnStatus returnStatus = new ReturnStatus(-1);

        m_hshSteps = new Hashtable();

        try {

            try {
                rs = _db.callGBL8004(returnStatus, _prof.getEnterprise(), _strEntityType, getKey());
                rdrs = new ReturnDataResultSet(rs);
            } finally {
				if (rs !=null){
            	    rs.close();
            	    rs = null;
				}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

            // Set the class and description...
            for (int ii = 0; ii < rdrs.size(); ii++) {

                int iLevel = rdrs.getColumnInt(ii, 0);
                int iLeaf = rdrs.getColumnInt(ii, 1);
                String strFromEntity = rdrs.getColumn(ii, 2);
                String strToEntity = rdrs.getColumn(ii, 3);
                String strRelator = rdrs.getColumn(ii, 4);
                String strPDirection = rdrs.getColumn(ii, 5);
                String strCDirection = rdrs.getColumn(ii, 6);
                String strCategory = rdrs.getColumn(ii, 7);
                String strRClass = rdrs.getColumn(ii, 8);

                _db.debug(D.EBUG_SPEW, "gbl8004:answers:" + iLevel + ":" + iLeaf + ":" + strFromEntity + ":" + strToEntity + ":" + strRelator + ":" + strPDirection + ":" + strCDirection + ":" + strCategory + ":" + strRClass);
                m_hshSteps.put(iLevel + strRelator + strCDirection, "Hi");
                m_hshSteps.put(iLevel + strFromEntity + strCDirection, "Hi");
                m_hshSteps.put(iLevel + strToEntity + strCDirection, "Hi");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return m_hshSteps;

    }

    /**
     * updatePdhMeta
     *
     * @return true if successful, false if nothing to update or unsuccessful
     * @param _db
     * @param _bExpire
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     */
    protected boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
        try {
            //lets get a fresh object from the database
            ExtractActionItem ex_db = new ExtractActionItem(null, _db, getProfile(), getActionItemKey());
            boolean bNewExtract = false;
            //check for new
            if (ex_db.getActionClass() == null) {
                bNewExtract = true;
            }

            //EXPIRES
            if (_bExpire && !bNewExtract) {
                //Target Entity Type
                if (ex_db.getTargetEntityType() != null) {
                    updateActionAttribute(_db, true, "TYPE", "Target", ex_db.getTargetEntityType());
                }
                //Queue Source
                updateActionAttribute(_db, true, "TYPE", "QUEUESOURCE", (ex_db.isQueueSourced() ? "Y" : "N"));
                //Pull Attributes
                updateActionAttribute(_db, true, "TYPE", "Attributes", (ex_db.pullAttributes() ? "Y" : "N"));
                //Target Entities Only
                updateActionAttribute(_db, true, "TYPE", "TargetOnly", (ex_db.pullTargetEntitiesOnly() ? "Y" : "N"));
                //Action/Entity definitions

            } else {

                // 1) Target Entity Type
                String s1 = this.getTargetEntityType();
                String s1_db = ex_db.getTargetEntityType();

                if (s1_db == null) {
                    s1_db = "";
                }
                if (s1 == null) {
                    s1 = "";
                }
                //used to exist -> now doesnt (is this field required anyways??) --> expire
                if (!s1_db.equals("") && s1.equals("")) {
                    updateActionAttribute(_db, true, "TYPE", "Target", s1_db);
                //new add or this changed --> update

                } else if ((bNewExtract && !s1.equals("")) || (!s1.equals(s1_db))) {
                    updateActionAttribute(_db, false, "TYPE", "Target", s1);
                }

                // 2) Queue Source
                if (bNewExtract || ex_db.isQueueSourced() != this.isQueueSourced()) {
                    updateActionAttribute(_db, false, "TYPE", "QUEUESOURCE", (this.isQueueSourced() ? "Y" : "N"));
                }
                // 3) Pull Attributes
                if (bNewExtract || ex_db.pullAttributes() != this.pullAttributes()) {
                    updateActionAttribute(_db, false, "TYPE", "Attributes", (this.pullAttributes() ? "Y" : "N"));
                }
                // 4) Target Entities Only
                if (bNewExtract || ex_db.pullTargetEntitiesOnly() != this.pullTargetEntitiesOnly()) {
                    updateActionAttribute(_db, false, "TYPE", "TargetOnly", (this.pullTargetEntitiesOnly() ? "Y" : "N"));
                }
            }

        } catch (SQLException sqlExc) {
            _db.debug(D.EBUG_ERR, "ExtractActionItem 327 " + sqlExc.toString());
            return false;
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return true;
    }

    /**
     * to simplify things a bit
     */
    private void updateActionAttribute(Database _db, boolean _bExpire, String _strLinkType2, String _strLinkCode, String _strLinkValue) throws MiddlewareException {
        String strValFrom = _db.getDates().getNow();
        String strValTo = (_bExpire ? strValFrom : _db.getDates().getForever());
        new MetaLinkAttrRow(getProfile(), "Action/Attribute", getActionItemKey(), _strLinkType2, _strLinkCode, _strLinkValue, strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
    }

	/**
	 * set Editable Entity
	 * VEEdit_Iteration2
	 *
	 * @param _string
	 * @author tony
	 */
	 protected void setEditable(String _s) {
		if (m_vEdit == null) {
			m_vEdit = new Vector();
		}
		 m_vEdit.add(_s);
		 return;
	 }

	/**
	 * set Editable Entity
	 * VEEdit_Iteration2
	 *
	 * @param _string
	 * @author tony
	 */
	 protected void setEditable(String[] _s) {
		if (m_vEdit == null) {
			m_vEdit = new Vector();
		}
		 if (_s != null) {
			 int ii = _s.length;
			 for (int i=0;i<ii;++i) {
				 m_vEdit.add(_s[i]);
			 }
		 }
		 return;
	 }

	/**
	 * get Editable Entities
	 * VEEdit_Iteration2
	 *
	 * @return string[]
	 * @author tony
	 */
	public String[] getEditable() {
		if (m_vEdit != null) {
			if (!m_vEdit.isEmpty()) {
				return (String[]) m_vEdit.toArray(new String[m_vEdit.size()]);
			}
		}
		return null;
	}

	/**
	 * is editable
	 *
	 * @param _s
	 * @return boolean
	 * @author Tony
	 */
	public boolean isEditable(String _s) {
		boolean bOut = false;
		if (m_vEdit != null) {
			bOut = m_vEdit.contains(_s);
		}

		return bOut;
	}

	/**
	 * isEditable
	 *
	 * @param _s
	 * @return boolean
	 * @author tony
	 */
	public boolean canEdit() {
		return m_bEditable;
	}


	/**
	 * set editable
	 *
	 * @param _b
	 * @author Tony
	 */
	public void setEditable(boolean _b) {
		m_bEditable = _b;
	}

	protected void setVEPath(String s){
		m_vepath = s;
	}

	public String getVEPath(){
		return m_vepath;
	}
	/**
	 * set Create
	 * VEEdit_Iteration2
	 *
	 * @param _boolean
	 * @author tony
	 */
	protected void setCreate(boolean _b) {
		m_bCreatable = _b;
	}

	/**
	 * can Create
	 * VEEdit_Iteration2
	 *
	 * @return _boolean
	 * @author tony
	 */
	public boolean canCreate() {
		return m_bCreatable;
	}

	/**
	 * set Creatable Entity
	 * VEEdit_Iteration2
	 *
	 * @param _boolean
	 * @author tony
	 */
	protected void setCreatable(String _s) {
		if (m_vCreate == null) {
			m_vCreate = new Vector();
		}
		m_vCreate.add(_s);
	}

	/**
	 * set Creatable Entity
	 * VEEdit_Iteration2
	 *
	 * @param _boolean
	 * @author tony
	 */
	protected void setCreatable(String[] _s) {
		if (m_vCreate == null) {
			m_vCreate = new Vector();
		}
		if (_s != null) {
			 int ii = _s.length;
			 for (int i=0;i<ii;++i) {
				 m_vCreate.add(_s[i]);
			 }
		}
		return;
	}

	/**
	 * get Creatable Entity
	 * VEEdit_Iteration2
	 *
	 * @return string[]
	 * @author tony
	 */
	public String[] getCreatable() {
		if (m_vCreate != null) {
			if (!m_vCreate.isEmpty()) {
				return (String[]) m_vCreate.toArray(new String[m_vCreate.size()]);
			}
		}
		return null;
	}

	/**
	 * isCreatable
	 *
	 * @param _s
	 * @return boolean
	 * @author tony
	 */
	public boolean isCreatable(String _s) {
		boolean bOut = false;
		if (_s.equals(getTargetType())) {
			bOut =  canCreate();
		} else if (m_vCreate != null) {
			bOut = m_vCreate.contains(_s);
		}

		return bOut;
	}

	/**
	 * set Duplicatable Entity
	 * VEEdit_Iteration2
	 *
	 * @param _boolean
	 * @author tony
	 */
	protected void setDuplicate(boolean _b) {
		m_bDuplicatable = _b;
		return;
	}

	/**
	 * can Duplicatable Entity
	 * VEEdit_Iteration2
	 *
	 * @return _boolean
	 * @author tony
	 */
	public boolean canDuplicate() {
		return m_bDuplicatable;
	}

	/**
	 * set duplicatable Entity
	 * VEEdit_Iteration2
	 *
	 * @param _boolean
	 * @author tony
	 */
	protected void setDuplicatable(String _s) {
		if (m_vDuplicate == null) {
			m_vDuplicate = new Vector();
		}
		m_vDuplicate.add(_s);
	}

	/**
	 * set duplicatable Entity
	 * VEEdit_Iteration2
	 *
	 * @param _string
	 * @author tony
	 */
	protected void setDuplicatable(String[] _s) {
		if (m_vDuplicate == null) {
			m_vDuplicate = new Vector();
		}
		if (_s != null) {
			 int ii = _s.length;
			 for (int i=0;i<ii;++i) {
				 m_vDuplicate.add(_s[i]);
			 }
		}
	}

	/**
	 * get Duplicatable Entity
	 * VEEdit_Iteration2
	 *
	 * @return string[]
	 * @author tony
	 */
	public String[] getDuplicatable() {
		if (m_vDuplicate != null) {
			if (!m_vDuplicate.isEmpty()) {
				return (String[]) m_vDuplicate.toArray(new String[m_vDuplicate.size()]);
			}
		}
		return null;
	}

	/**
	 * isCreatable
	 *
	 * @param _s
	 * @return boolean
	 * @author tony
	 */
	public boolean isDuplicatable(String _s) {
		boolean bOut = false;
		if (_s.equals(getTargetType())) {
			bOut = canDuplicate();
		} else if (m_vDuplicate != null) {
			bOut = m_vDuplicate.contains(_s);
		}
//		System.out.println("ExtractActionItem.isDuplicatable(" + _s + "): " + bOut);
		return bOut;
	}


	/**
	 * set delete action for Entity that is editable in veedit
	 * VEEdit_delete MN30841458
	 *
	 * @param _eType
	 * @param _name
	 */
	protected void setDelete(String _eType,String _name) {
		if (m_hDelete == null) {
			m_hDelete = new Hashtable();
		}
		if (!m_hDelete.containsKey(_eType)) {
			m_hDelete.put(_eType,_name);
		}
	}
	protected void setDelete(Hashtable _disp) {
		if (_disp != null) {
			Enumeration numer = _disp.keys();
			while (numer.hasMoreElements()) {
				Object o = numer.nextElement();
				Object obj = _disp.get(o);
				setDelete(o.toString(),obj.toString());
			}
		}
	}
	protected Hashtable getDelete() {
		return m_hDelete;
	}

	/**
	 * get deleteaction
	 * VEEdit_delete MN30841458
	 *
	 * @param _eType
	 * @return string
	 */
	public String getDeleteAction(String _eType) {
		String action = null;
		if (m_hDelete != null) {
			if (m_hDelete.containsKey(_eType)) {
				action =  (String)m_hDelete.get(_eType);
			}
		}
		return action;
	}
	/**
	 * set displayable Entity
	 * VEEdit_Iteration2
	 *
	 * @param entitytype
	 * @param path
	 * @author tony
	 */
	protected void setDisplay(String _eType, String _path) {
		if (m_hDisplay == null) {
			m_hDisplay = new Hashtable();
		}
		if (!m_hDisplay.containsKey(_eType)) {
			m_hDisplay.put(_eType,_path);
		}
	}

	/**
	 * set displayable Entity
	 * VEEdit_Iteration2
	 *
	 * @param disp
	 * @author tony
	 */
	protected void setDisplay(Hashtable _disp) {
		if (_disp != null) {
			Enumeration numer = _disp.keys();
			while (numer.hasMoreElements()) {
				Object o = numer.nextElement();
				Object obj = _disp.get(o);
				setDisplay(o.toString(),obj.toString());
			}
		}
	}

	/**
	 * det displayable Entity
	 * VEEdit_Iteration2
	 *
	 * @return hash
	 * @author tony
	 */
	protected Hashtable getDisplay() {
		return m_hDisplay;
	}

	/**
	 * get Displayable Entity
	 * VEEdit_Iteration2
	 *
	 * @return string[]
	 * @author tony
	 */
	public String[] getDisplayable() {
		if (m_hDisplay != null) {
			Enumeration numer = m_hDisplay.keys();
			Vector v = new Vector();
			while (numer.hasMoreElements()) {
				Object obj = numer.nextElement();
				v.add(obj);
			}
			if (!v.isEmpty()) {
				return (String[]) v.toArray(new String[v.size()]);
			}
		}
		return null;
	}

	/**
	 * based on where i am and how i can navigate
	 * get me where i need to be.
	 * VEEdit BUI
	 *
	 * @param up entity
	 * @param entity
	 * @param down entity
	 * @return which way
	 * @author tony
	 */
	public String getDisplayableEntity(String _eUp, String _e, String _eDown) {
		String[] tmp = getDisplayable();
		String tmpPath = null;
		if (tmp != null) {
			int ii = tmp.length;
			for (int i=0;i<ii;++i) {
				tmpPath = getDisplayPath(tmp[i]);
				if (tmpPath.indexOf(_e + ",D") >= 0) {
					return _eDown;
				} else if (tmpPath.indexOf(_e + ",U") >= 0) {
					return _eUp;
				}
			}
		}
		return null;
	}

	/**
	 * is the entity displayable
	 *
	 * @return boolean
	 * @author tony
	 */
	public boolean isDisplayable(String _s) {
		boolean bOut = false;
		if (m_hDisplay != null) {
			bOut = m_hDisplay.containsKey(_s);
		}
//		D.ebug(D.EBUG_SPEW,"ExtractActionItem.isDisplayable(" + _s + "): " + bOut);
		return bOut;
	}

	/**
	 * get displayPath
	 * VEEdit_Iteration2
	 *
	 * @param Type
	 * @return string
	 * @author tony
	 */
	public String getDisplayPath(String _eType) {
		if (m_hDisplay != null) {
			if (m_hDisplay.containsKey(_eType)) {
				return (String)m_hDisplay.get(_eType);
			}
		}
		return null;
	}

	/**
	 * isNavOnly
	 * VEEdit_Iteration2
	 *
	 * @param string
	 * @author tony
	 */
	public boolean isNavOnly(String _s) {
		boolean bOut = false;
		String s = getDisplayPath(_s);
		if (s != null) {
			bOut = s.startsWith("NavOnly");
		}
//		D.ebug(D.EBUG_SPEW,"ExtractActionItem.isNavOnly(" + _s + "): " + bOut);
		return bOut;
	}

	/**
	 * isFull
	 * VEEdit_Iteration2
	 *
	 * @param string
	 * @author tony
	 */
	public boolean isFull(String _s) {
		boolean bOut = false;
		String s = getDisplayPath(_s);
		if (s != null) {
			bOut = s.startsWith("Full");
		}
//		D.ebug(D.EBUG_SPEW,"ExtractActionItem.isFull(" + _s + "): " + bOut);
		return bOut;
	}

	/**
	 * isAttribute
	 * VEEdit_Iteration3
	 *
	 * @param string
	 * @author tony
	 */
	public boolean isAttribute(String _s) {
		boolean bOut = false;
		String s = getDisplayPath(_s);
		if (s != null) {
			bOut = s.startsWith("Attribute");
		}
//		D.ebug(D.EBUG_SPEW,"ExtractActionItem.isAttribute(" + _s + "): " + bOut);
		return bOut;
	}

	/**
	 * set Attribute
	 * VEEdit_Iteration3
	 *
	 * @param sequence
	 * @param value
	 * @author Tony
	 */
	public void setDisplayAttribute(String _seq, String _val) {
		if (m_vAttribute == null) {
			m_vAttribute = new Vector();
		}
		int iSeq = m_vAttribute.size();//-1;
		try {
			iSeq = Integer.valueOf(_seq).intValue();
		} catch (NumberFormatException _nfe) {
		}
		// this is getting an exception if _seq do not get loaded in order
		// from Vector api doc:add(int index, Object element) throws
		// ArrayIndexOutOfBoundsException - index is out of range (index < 0 || index > size()).
		/*if (iSeq >= 0) {
			m_vAttribute.add(iSeq,_val);
		} else {
			m_vAttribute.add(_val);
		}*/
		if (iSeq > m_vAttribute.size()) {
			iSeq = m_vAttribute.size();
		}
		m_vAttribute.add(iSeq,_val);
	}

	/**
	 * set Attribute
	 * VEEdit_Iteration3
	 *
	 * @param values
	 * @author Tony
	 */
	public void setDisplayAttribute(String[] _vals) {
		if (_vals != null) {
			if (m_vAttribute == null) {
				m_vAttribute = new Vector();
			}
			for (int i=0;i<_vals.length;i++) {
				if (_vals[i] != null) {
					m_vAttribute.add(_vals[i]);
				}
			}
		}
	}

	/**
	 * has Target Attributes
	 * VEEdit_Iteration3
	 *
	 * @return boolean
	 * @author Tony
	 */
	public boolean hasDisplayAttribute() {
		if (m_vAttribute != null) {
			return !m_vAttribute.isEmpty();
		}
		return false;
	}

	/**
	 * get Attribute
	 * VEEdit_Iteration3
	 *
	 * @return values
	 * @author Tony
	 */
	public String[] getDisplayAttributes() {
		if (m_vAttribute != null) {
//			D.ebug(D.EBUG_SPEW,"-->>  I have " + m_vAttribute.size() + " display attribute(s).");
			return (String[]) m_vAttribute.toArray(new String[m_vAttribute.size()]);
		}
		return null;
	}

	/**
	 * get Path As String
	 * VEEdit_Iteration2
	 *
	 * @param entitytype
	 * @return string
	 * @author tony
	 */
	public String getPathAsString(String _s) {
		String out = null;
		String s = getDisplayPath(_s);
		if (s != null) {
			int indx = s.indexOf("|");
			int lIndx = s.lastIndexOf("|");
			if (indx != lIndx) {
				D.ebug(D.EBUG_SPEW,"mismatched path for: " + _s + " is: " + s);
				return null;
			}
			if (indx >= 0) {
				out = s.substring(indx + 1);
			} else {
				out = s;
			}
		}
		return out;
	}

	/**
	 * getPath
	 * VEEdit_Iteration2
	 *
	 * @param s
	 * @return path array
	 * @author tony
	 */
	public VEPath[] getPath(String _s) {
//		D.ebug(D.EBUG_SPEW,"ExtractActionItem.getPath(" + _s + ")");
		String s = getPathAsString(_s);
		VEPath[] out = null;
		if (s != null) {
			out = getPathFromString(s);
		}
		return out;
	}

	/**
	 * get Attribute Display Path
	 * VEEdit_Iteration3
	 *
	 * @param s
	 * @return path array
	 * @author tony
	 */
	public VEPath[] getAttributeDisplayPath(String _s) {
		VEPath[] out = null;
		if (hasDisplayAttribute()) {
			String[] sDispAtt = getDisplayAttributes();
			if (sDispAtt != null) {
				int ii = sDispAtt.length;
				for (int i=0;i<ii && out == null;++i) {
					VEPath[] tmp = getPathFromString(sDispAtt[i]);
					if (tmp != null) {
						int xx = tmp.length -1;
						if (xx >= 0) {
							if (_s.equals(tmp[xx].getType())) {
								out = tmp;
							}
						}
					}
				}
			}
		}
		return out;
	}

	/**
	 * getPathFromString
	 * VEEdit_Iteration2
	 *
	 * @param s
	 * @return path array
	 * @author tony
	 */
	 public VEPath[] getPathFromString(String _s) {
		if (_s != null) {
			Vector v = new Vector();
			StringTokenizer st = new StringTokenizer(_s,":");
			while (st.hasMoreTokens()) {
				v.add(new VEPath(st.nextToken()));
			}
			if (!v.isEmpty()) {
				return (VEPath[])v.toArray(new VEPath[v.size()]);
			}
		}
		return null;
	}
/*
 TIR USRO-R-LBAR-6LYL96
 */
	/**
	 * set Trailing Attribute
	 *
	 * @param sequence
	 * @param value
	 * @author Tony
	 */
	public void setTrailDisplayAttribute(String _seq, String _val) {
		if (m_vTrailAttribute == null) {
			m_vTrailAttribute = new Vector();
		}
		int iSeq = m_vTrailAttribute.size();//-1;
		try {
			iSeq = Integer.valueOf(_seq).intValue();
		} catch (NumberFormatException _nfe) {
		}
		// this is getting an exception if _seq do not get loaded in order
		// from Vector api doc:add(int index, Object element) throws
		// ArrayIndexOutOfBoundsException - index is out of range (index < 0 || index > size()).
		/*if (iSeq >= 0) {
			m_vTrailAttribute.add(iSeq,_val);
		} else {
			m_vTrailAttribute.add(_val);
		}*/

		if (iSeq > m_vTrailAttribute.size()) {
			iSeq = m_vTrailAttribute.size();
		}
		m_vTrailAttribute.add(iSeq,_val);
	}

	/**
	 * set Trailing Attribute
	 *
	 * @param values
	 * @author Tony
	 */
	public void setTrailDisplayAttribute(String[] _vals) {
		if (_vals != null) {
			if (m_vTrailAttribute == null) {
				m_vTrailAttribute = new Vector();
			}
			int ii = _vals.length;
			for (int i=0;i<ii;++i) {
				m_vTrailAttribute.add(_vals[i]);
			}
		}
		return;
	}

	/**
	 * has Trail Target Attributes
	 *
	 * @return boolean
	 * @author Tony
	 */
	public boolean hasTrailDisplayAttribute() {
		if (m_vTrailAttribute != null) {
			return !m_vTrailAttribute.isEmpty();
		}
		return false;
	}

	/**
	 * get Trail Attribute
	 *
	 * @return values
	 * @author Tony
	 */
	public String[] getTrailDisplayAttributes() {
		if (m_vTrailAttribute != null) {
//			D.ebug(D.EBUG_SPEW,"-->>  I have " + m_vTrailAttribute.size() + " display attribute(s).");
			return (String[]) m_vTrailAttribute.toArray(new String[m_vTrailAttribute.size()]);
		}
		return null;
	}
}
