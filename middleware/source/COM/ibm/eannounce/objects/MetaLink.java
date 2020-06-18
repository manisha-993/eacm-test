
//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaLink.java,v $
// Revision 1.53  2012/09/05 14:37:57  wendy
// RCQ00213801  Capability enhancement
//
// Revision 1.52  2009/05/14 14:26:01  wendy
// Support dereference for memory release
//
// Revision 1.51  2009/03/03 17:30:36  wendy
// Check for rs!= null before close
//
// Revision 1.50  2005/03/08 23:15:46  dave
// Jtest checkins from today and last ngith
//
// Revision 1.49  2005/01/20 23:08:28  gregg
// ok put Rule51 back in MetaLink
//
// Revision 1.48  2005/01/20 22:57:00  gregg
// cleaning up after the scourge of Rule51
//
// Revision 1.47  2005/01/18 21:46:50  dave
// more parm debug cleanup
//
// Revision 1.46  2005/01/18 01:03:36  gregg
// store rule51Group on object
//
// Revision 1.45  2004/09/10 17:50:10  joan
// working on link entities to relators for 3.0a
//
// Revision 1.44  2004/01/14 18:41:23  dave
// more squeezing of the short description
//
// Revision 1.43  2004/01/13 18:51:50  dave
// new constructor
//
// Revision 1.42  2004/01/13 18:25:35  dave
// fixing contstructor again
//
// Revision 1.41  2004/01/13 18:04:41  dave
// minor change to contructor
//
// Revision 1.40  2004/01/13 17:56:33  dave
// more squeezing
//
// Revision 1.39  2003/10/30 00:56:16  dave
// more profile fixes
//
// Revision 1.38  2003/10/30 00:43:34  dave
// fixing all the profile references
//
// Revision 1.37  2003/10/24 18:04:48  joan
// fb52626
//
// Revision 1.36  2003/09/22 15:09:19  joan
// work on upgrade paths
//
// Revision 1.35  2003/04/14 16:43:10  dave
// speed and cleanup
//
// Revision 1.34  2003/04/14 15:37:29  dave
// clean up and verification on getMetaLinkGroup
//
// Revision 1.33  2003/04/02 21:38:30  dave
// added _db.commit()
//
// Revision 1.32  2003/04/02 21:37:07  dave
// re-using 7002 to get ML descriptions (as oppossed to 8140)
//
// Revision 1.31  2003/04/02 19:47:09  dave
// removing the need for addperm in 8140
//
// Revision 1.30  2003/01/07 00:40:10  dave
// more Orphan Checking
//
// Revision 1.29  2003/01/07 00:14:52  dave
// adding generic orphan checking
//
// Revision 1.28  2002/06/21 23:49:22  joan
// display entity descriptions
//
// Revision 1.27  2002/05/07 21:51:05  gregg
// replaced gbl 1040 w/ gbl8140
//
// Revision 1.26  2002/05/06 23:06:30  joan
// add code to avoid passing null entitytype
//
// Revision 1.25  2002/05/01 21:49:59  joan
// add logic to obtain longdescription for entity1type and entity2type
//
// Revision 1.24  2002/03/05 03:09:29  dave
// checking in the create issue
//
// Revision 1.23  2002/03/05 01:43:45  dave
// fixing more throws logic
//
// Revision 1.22  2002/03/05 01:31:16  dave
// more syntax fixes
//
// Revision 1.21  2002/03/05 01:18:06  dave
// more syntax
//
// Revision 1.20  2002/03/04 23:35:02  dave
// numerous fixes
//
// Revision 1.19  2002/03/04 23:19:12  dave
// working on the createActionItem and entitylist constructor
//
// Revision 1.18  2002/02/21 19:34:21  dave
// syntax fixes
//
// Revision 1.17  2002/02/21 19:26:13  dave
// added Link to static entityList
//
// Revision 1.16  2002/02/21 17:40:36  dave
// added copyDescription to the MetaLink Constructor
//
// Revision 1.15  2002/02/21 17:10:28  dave
// syntax
//
// Revision 1.14  2002/02/21 17:02:50  dave
// change to entitygroup constructor parm (EntityList = em)
//
// Revision 1.13  2002/02/20 20:55:05  dave
// syntax
//
// Revision 1.12  2002/02/20 20:43:52  dave
// syntax fixes
//
// Revision 1.11  2002/02/20 20:10:50  dave
// Added the getMetaLink method
//
// Revision 1.10  2002/02/13 00:17:10  dave
// clean up on dumps and adding misc toStrings
//
// Revision 1.9  2002/02/11 09:00:49  dave
// more dump rearrangements
//
// Revision 1.8  2002/02/02 20:56:54  dave
// tightening up code
//
// Revision 1.7  2002/02/01 00:42:32  dave
// more foundation fixes for passing _prof
//
// Revision 1.6  2002/01/28 20:04:08  dave
// rearranged to ensure that we have the proper entitytypes
// represented in the MetaLink in the right order
//
// Revision 1.5  2002/01/21 20:59:51  dave
// bit the bullet and use profile to imply NLSItem
//
// Revision 1.4  2002/01/21 19:07:06  dave
// isCreatable was isUpdatable by mistake in dump
//
// Revision 1.3  2002/01/21 18:41:24  dave
// final syntax fixes
//
// Revision 1.2  2002/01/21 18:25:52  dave
// syntax fixes
//
// Revision 1.1  2002/01/21 18:12:41  dave
// added MetaLink to support new NavigateObject
// so we can de-couple Navigation from Linking
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.D;

/**
 * This object represents a link record in the PDH.
 *
 * @author     davidbig
 * @created    April 14, 2003
 */
public class MetaLink extends EANMetaFoundation {

    // Instance variables
    /**
     * @serial
     */
    final static long serialVersionUID = 1L;
    private static int m_iRID = -1;
    private String m_strET = null;
    private String m_strE1T = null;
    private String m_strE2T = null;
    private String m_strE1Desc = null;
    private String m_strE2Desc = null;
    //private String m_strCapability = null;
    private boolean m_bC = false;
    private boolean m_bW = false;
    private boolean m_bR = true;
    private boolean m_bOrphanCheck = false;
    private boolean m_bcanLinkAll = true; //RCQ00213801
    private Rule51Group m_rule51Grp = null;
    
    public static final char CREATE = 'C';
    public static final char WRITE = 'W';
    public static final char READ = 'R';
    public static final char NOT_LINKALL = 'N'; //RCQ00213801
    
    protected void dereference(){
    	super.dereference();
    	
    	m_strET = null;
        m_strE1T = null;
        m_strE2T = null;
        m_strE1Desc = null;
        m_strE2Desc = null;
        if (m_rule51Grp != null){
        	m_rule51Grp.dereference();
        	m_rule51Grp = null;
        }
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param  arg  Description of the Parameter
     */
    public static void main(String arg[]) {
    }

    /**
     * Return the date/time this class was generated
     *
     * @return    the date/time this class was generated
     */
    public String getVersion() {
        return "$Id: MetaLink.java,v 1.53 2012/09/05 14:37:57 wendy Exp $";
    }

    /**
     * Creates the MetaLabel with the Default US English LongDescription
     * You can only create this from the prospective of a MetaLinkList
     *
     * @param  _emf                            Description of the Parameter
     * @param  _prof                           Description of the Parameter
     * @param  _s1                             Description of the Parameter
     * @param  _s2                             Description of the Parameter
     * @param  _s3                             Description of the Parameter
     * @param  _bOrphanCheck                   Description of the Parameter
     * @exception  MiddlewareRequestException  Description of the Exception
     * @param _bC
     * @param _bW
     * @param _bR 
     */
    private MetaLink(EANMetaFoundation _emf, Profile _prof, String _s1, String _s2, String _s3, boolean _bC, boolean _bW, boolean _bR, boolean _bOrphanCheck) throws MiddlewareRequestException {
        super(_emf, _prof, _s1);
        setEntityType(_s1);
        setEntity1Type(_s2);
        setEntity2Type(_s3);
        setCreatable(_bC);
        setWritable(_bW);
        setReadable(_bR);
        setOrphanCheck(_bOrphanCheck);
    }

    /**
     *Constructor for the MetaLink object
     *
     * @param  _prof                           Description of the Parameter
     * @param  _ml                             Description of the Parameter
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    protected MetaLink(Profile _prof, MetaLink _ml) throws MiddlewareRequestException {
        this(null, _prof, _ml.getKey(), _ml.getEntity1Type(), _ml.getEntity2Type(), _ml.isCreatable(), _ml.isWritable(), _ml.isReadable(), _ml.checkOrphan());
        copyDescription(_ml);
        setLinkAllAble(_ml.isLinkAllAble()); //RCQ00213801
    }

    /**
     * MetaLink
     *
     * @param _emf
     * @param _prof
     * @param _s1
     * @param _s2
     * @param _s3
     * @param _strCapability
     * @param _bOrphanCheck
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    protected MetaLink(EANMetaFoundation _emf, Profile _prof, String _s1, String _s2, String _s3, String _strCapability, boolean _bOrphanCheck) throws MiddlewareRequestException {
        super(_emf, _prof, _s1);
        setEntityType(_s1);
        setEntity1Type(_s2);
        setEntity2Type(_s3);
        setCapabilities(_strCapability);
        setOrphanCheck(_bOrphanCheck);
    }

    /**
     * need a way to prevent linkall from cart, use linkvalue.. must be shared with current values
     * use 'N' or append it to current value like 'CN' RCQ00213801
     * defaults to R         COALESCE(RTRIM(MLR.LinkValue),'R')
     * SG	Role/Entity	POWERUSER	MODELWWSEO	Capability	N	
     * @param strCapability
     */
    private void setCapabilities(String strCapability){
    	//NOT_LINKALL
    	if(strCapability.charAt(0) == NOT_LINKALL){
    		setLinkAllAble(false);
    		if(strCapability.length()>1){
    			strCapability = strCapability.substring(1); // let rest of code read the next character
    		}else{
    			strCapability="R"; // restore the default
    		}
    	}
        setCreatable(strCapability.charAt(0) == CREATE);
        setWritable(strCapability.charAt(0) == WRITE || strCapability.charAt(0) == CREATE);
        setReadable(strCapability.charAt(0) == WRITE || strCapability.charAt(0) == CREATE || strCapability.charAt(0) == READ);    	
    
		if(strCapability.length()>1){
			setLinkAllAble(strCapability.charAt(1) != NOT_LINKALL);
		}
    }
    /*
     *  Get a specific MetaLink Object from the Database.
     */
    /**
     *Constructor for the MetaLink object
     *
     * @param  _emf                            Description of the Parameter
     * @param  _db                             Description of the Parameter
     * @param  _prof                           Description of the Parameter
     * @param  _strEntityType                  Description of the Parameter
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     */
    protected MetaLink(EANMetaFoundation _emf, Database _db, Profile _prof, String _strEntityType) throws SQLException, MiddlewareRequestException, MiddlewareException {

        super(_emf, _prof, _strEntityType);

        try {
            // Future.. This builds a list of MetaLinks based upon Role and EntityType
            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            Profile prof = getProfile();
            String strEnterprise = prof.getEnterprise();
            String strRoleCode = prof.getRoleCode();
            String strValOn = prof.getValOn();
            String strEffOn = prof.getEffOn();
            int iNLSID = getNLSID();

            // Set the EntityType of this beastie
            setEntityType(_strEntityType);

            // we have to get the group description here..
            // Retrieve the Action Class and the Action Description.
            try {
                rs = _db.callGBL7015(returnStatus, strEnterprise, strRoleCode, getEntityType(), iNLSID, strValOn, strEffOn);
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

            // We need to get the description of the link group here...
            for (int i = 0; i < rdrs.size(); i++) {
                String strCapability = rdrs.getColumn(i, 0);
                int iReturnNLSID = rdrs.getColumnInt(i, 1);
                String strShortDesc = rdrs.getColumn(i, 2);
                String strLongDesc = rdrs.getColumn(i, 3);
                String strEntity1Type = rdrs.getColumn(i, 4);
                String strEntity2Type = rdrs.getColumn(i, 5);
                String strOrphan = rdrs.getColumn(i, 6);
                String strEntity1Desc = rdrs.getColumn(i, 7);
                String strEntity2Desc = rdrs.getColumn(i, 8);
                // YES = no orphan, No = bus as usuall

                _db.debug(D.EBUG_DETAIL, "gbl7015 answer:" + strCapability + ":" + iReturnNLSID + ":" + strShortDesc + ":" + strLongDesc + ":" + strEntity1Type + ":" + strEntity2Type + ":" + strOrphan + ":" + strEntity1Desc + ":" + strEntity2Desc);

                setEntity2Type(strEntity2Type);
                setEntity1Type(strEntity1Type);
                //putShortDescription(iReturnNLSID, strShortDesc);
                putLongDescription(iReturnNLSID, strLongDesc);

                //setCapability(strCapability);
                setCapabilities(strCapability);
                
                setOrphanCheck(strOrphan.equals("YES"));
                setEntity1Description(strEntity1Desc);
                setEntity2Description(strEntity2Desc);
            }

            // We now have everything...

        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /*
     *  ACCESSORS
     */
    /**
     *  Gets the readable attribute of the MetaLink object
     *
     * @return    The readable value
     */
    public boolean isReadable() {
        return m_bR;
    }

    /**
     *  Gets the writable attribute of the MetaLink object
     *
     * @return    The writable value
     */
    public boolean isWritable() {
        return m_bW;
    }

    /**
     *  Gets the creatable attribute of the MetaLink object
     *
     * @return    The creatable value
     */
    public boolean isCreatable() {
        return m_bC;
    }

    /**
     *  Gets the entity1Type attribute of the MetaLink object
     *
     * @return    The entity1Type value
     */
    public String getEntity1Type() {
        return m_strE1T;
    }

    /**
     *  Gets the entity2Type attribute of the MetaLink object
     *
     * @return    The entity2Type value
     */
    public String getEntity2Type() {
        return m_strE2T;
    }

    /**
     *  Sets the entity1Description attribute of the MetaLink object
     *
     * @param  _s  The new entity1Description value
     */
    private void setEntity1Description(String _s) {
        m_strE1Desc = _s;
    }

    /**
     *  Sets the entity2Description attribute of the MetaLink object
     *
     * @param  _s  The new entity2Description value
     */
    private void setEntity2Description(String _s) {
        m_strE2Desc = _s;
    }

    /**
     *  Gets the entity1Description attribute of the MetaLink object
     *
     * @return    The entity1Description value
     */
    public String getEntity1Description() {
        return m_strE1Desc;
    }

    /**
     *  Gets the entity2Description attribute of the MetaLink object
     *
     * @return    The entity2Description value
     */
    public String getEntity2Description() {
        return m_strE2Desc;
    }

    /*
     *  MUTATORS
     */
    /**
     *  Sets the readable attribute of the MetaLink object
     *
     * @param  _b  The new readable value
     */
    protected void setReadable(boolean _b) {
        m_bR = _b;
    }

    /**
     *  Sets the writable attribute of the MetaLink object
     *
     * @param  _b  The new writable value
     */
    protected void setWritable(boolean _b) {
        m_bW = _b;
    }

    /**
     *  Sets the creatable attribute of the MetaLink object
     *
     * @param  _b  The new creatable value
     */
    protected void setCreatable(boolean _b) {
        m_bC = _b;
    }
    /**
     *  Sets the linkall from cart capability 
     *
     * @param  b  
     */
    protected void setLinkAllAble(boolean b) {
    	m_bcanLinkAll = b;
    }

    /**
     *  Sets the entityType attribute of the MetaLink object
     *
     * @param  _str1  The new entityType value
     */
    protected void setEntityType(String _str1) {
        m_strET = _str1;
    }

    /**
     *  Sets the entity1Type attribute of the MetaLink object
     *
     * @param  _s  The new entity1Type value
     */
    protected void setEntity1Type(String _s) {
        m_strE1T = _s;
    }

    /**
     *  Sets the entity2Type attribute of the MetaLink object
     *
     * @param  _s  The new entity2Type value
     */
    protected void setEntity2Type(String _s) {
        m_strE2T = _s;
    }

    /**
     *  Sets the orphanCheck attribute of the MetaLink object
     *
     * @param  _b  The new orphanCheck value
     */
    protected void setOrphanCheck(boolean _b) {
        m_bOrphanCheck = _b;
    }

    /**
     * Display the object and show values
     *
     * @param  _bBrief  Description of the Parameter
     * @return          Description of the Return Value
     */
    public String dump(boolean _bBrief) {

        StringBuffer strbResult = new StringBuffer();

        if (_bBrief) {
            strbResult.append("MetaLink:" + getKey());
        } else {
            strbResult.append("MetaLink:" + getKey());
            strbResult.append(":ShortDesc:" + getShortDescription());
            strbResult.append(":LongDesc:" + getLongDescription());
            strbResult.append(":Entity1Type:" + getEntity1Type());
            strbResult.append(":Entity1Description:" + getEntity1Description());
            strbResult.append(":Entity2Type:" + getEntity2Type());
            strbResult.append(":Entity2Description:" + getEntity2Description());
            strbResult.append(":isReadable:" + isReadable());
            strbResult.append(":isWritable:" + isWritable());
            strbResult.append(":isCreatable:" + isCreatable());
            strbResult.append(":isLinkAllAble:" + isLinkAllAble());
        }

        return strbResult.toString();
    }

    /**
     *  Description of the Method
     *
     * @return    Description of the Return Value
     */
    public String toString() {
        return getLongDescription();
    }

    /**
     *  Sets the capability attribute of the MetaLink object
     *
     * @param  _str  The new capability value
     */
    // protected void setCapability(String _str) {
    //   m_strCapability = _str;
    // }

    /**
     *  Gets the capability attribute of the MetaLink object
     *
     * @return    The capability value
     */
    // protected String getCapability() {
    //   return m_strCapability;
    // }

    /**
     *  Gets the entityType attribute of the MetaLink object
     *
     * @return    The entityType value
     */
    public String getEntityType() {
        return m_strET;
    }

    /**
     *  Gets the nextRID attribute of the MetaLink object
     *
     * @return    The nextRID value
     */
    public int getNextRID() {
        return m_iRID--;
    }

    /**
     *  Description of the Method
     *
     * @return    Description of the Return Value
     */
    public boolean checkOrphan() {
        return m_bOrphanCheck;
    }

    /**
     * setRule51Group
     *
     * @param _rg
     *  @author David Bigelow
     */
    public final void setRule51Group(Rule51Group _rg) {
        m_rule51Grp = _rg;
    }

    /**
     * getRule51Group
     *
     * @return
     *  @author David Bigelow
     */
    public final Rule51Group getRule51Group() {
        return m_rule51Grp;
    }

    /**
     * hasRule51Group
     *
     * @return
     *  @author David Bigelow
     */
    public final boolean hasRule51Group() {
        return (m_rule51Grp != null);
    }
  
    /**
     * RCQ00213801 - turn off link all capability on the JUI workfolder
     * @return
     */
    public final boolean isLinkAllAble(){
    	return m_bcanLinkAll;
    }
}
