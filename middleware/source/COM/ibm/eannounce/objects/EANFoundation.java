//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANFoundation.java,v $
// Revision 1.56  2014/02/17 13:43:36  wendy
// IN4836385 - prevent null profile when parent is cleared
//
// Revision 1.55  2009/05/11 15:13:47  wendy
// Support dereference for memory release
//
// Revision 1.54  2008/02/01 22:10:06  wendy
// Cleanup RSA warnings
//
// Revision 1.53  2007/11/07 14:42:42  wendy
// MN33607291 error when parent and child are same entitytypes MODEL:MODELREL:MODEL
//
// Revision 1.52  2005/08/25 21:18:22  tony
// removed heritage stuff
//
// Revision 1.51  2005/08/23 18:14:24  tony
// adjusted Heritage Key
//
// Revision 1.50  2005/08/22 21:03:50  tony
// Key logic
//
// Revision 1.49  2005/08/22 20:28:22  tony
// improved keying of objects
//
// Revision 1.48  2005/08/22 19:23:42  tony
// added getHeritageKey
//
// Revision 1.47  2005/08/22 16:34:18  tony
// *** empty log message ***
//
// Revision 1.46  2005/08/22 16:24:03  tony
// Test_acl20050822
//
// Revision 1.45  2005/03/11 22:42:53  dave
// removing some auto genned stuff
//
// Revision 1.44  2005/03/09 17:30:12  gregg
// NEW_LINE_HTML
//
// Revision 1.43  2005/03/03 21:25:16  dave
// NEW_LINE on EAN Foundation
//
// Revision 1.42  2005/02/14 17:57:47  dave
// more Jtest
//
// Revision 1.41  2004/08/23 16:56:17  dave
// need to publicize some stuff
//
// Revision 1.40  2004/01/13 22:51:15  dave
// more squeezing
//
// Revision 1.39  2004/01/13 19:54:41  dave
// space Saver Phase II  m_hsh1 and m_hsh2 created
// as needed instead of always there
//
// Revision 1.38  2004/01/12 22:37:34  dave
// more squeezing
//
// Revision 1.37  2003/09/09 22:57:48  dave
// more Profile Crimping
//
// Revision 1.36  2003/05/27 20:55:28  dave
// Looking at Where Used
//
// Revision 1.35  2003/05/13 19:01:54  dave
// added getAL function in EANAddressable.. so I hope I can get
// it working for this sort
//
// Revision 1.34  2003/04/24 00:14:54  dave
// update fixes
//
// Revision 1.33  2003/04/23 23:44:39  dave
// syntax
//
// Revision 1.32  2003/04/23 23:37:02  dave
// trace on isActive
//
// Revision 1.31  2003/04/23 23:29:57  dave
// when someone turns it off
//
// Revision 1.30  2003/04/23 23:23:59  dave
// tracing setActive to false
//
// Revision 1.29  2003/02/15 00:18:21  gregg
// removed interface HtmlDisplayable
//
// Revision 1.28  2002/10/14 21:30:01  dave
// syntax fixes
//
// Revision 1.27  2002/10/14 21:11:07  dave
// syntax
//
// Revision 1.26  2002/10/14 21:04:10  dave
// introducing transient parent pointers so when
// we clone.. we do not have a case where the object
// that is being cloned loses its parent momentarily
//
// Revision 1.25  2002/07/10 00:32:06  gregg
// First Test of Attribute Exlusion Stub
//
// Revision 1.24  2002/05/21 16:50:53  gregg
// HtmlDisplayable interface moved up from EANMetaFoundation into parent EANFoundation class
// to encompass both meta & data objects
//
// Revision 1.23  2002/04/01 21:42:34  dave
// making getParent a public method
//
// Revision 1.22  2002/03/28 18:18:25  dave
// close the loop on add record
//
// Revision 1.21  2002/03/20 21:57:51  dave
// syntax error fixes
//
// Revision 1.20  2002/03/12 20:42:55  dave
// need to abstract getLongDescription, getShortDescription at the EANFoundation
// level
//
// Revision 1.19  2002/02/18 23:02:18  dave
// syntax at low level - adding isActive Boolean to denote 'live'
// things in the meta model
//
// Revision 1.18  2002/02/18 22:55:04  dave
// fixed null pointer
//
// Revision 1.17  2002/02/15 20:13:52  dave
// changed getProfile to look to parent for setting if parent exists
//
// Revision 1.16  2002/02/13 22:47:59  dave
// more rearranging in the abstract layer
//
// Revision 1.15  2002/02/07 17:05:25  dave
// assertion fix
//
// Revision 1.14  2002/02/02 19:48:59  dave
// more foundation work
//
// Revision 1.13  2002/02/01 17:58:13  dave
// more syntax fixes
//
// Revision 1.12  2002/02/01 17:48:46  dave
// more fixes
//
// Revision 1.11  2002/02/01 17:26:04  dave
// missing import statement
//
// Revision 1.10  2002/02/01 03:16:51  dave
// reworking how Things get implementd for the local text management
// and editing
//
// Revision 1.9  2002/01/31 22:57:19  dave
// more Foundation Cleanup
//
// Revision 1.8  2002/01/31 22:28:20  dave
// more Foundation changes
//
// Revision 1.7  2002/01/31 21:32:22  dave
// more mass abstract changes
//
// Revision 1.6  2002/01/31 17:58:54  dave
// Rearranging Abrstract levels for more consistiency
//
//
//
package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.transactions.NLSItem;
import COM.ibm.opicmpdh.middleware.T;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import java.util.Hashtable;
import java.io.Serializable;
//import java.io.IOException;
//import java.io.ObjectInputStream;

/**
* This is the basis for all eannounce MetaObjects used by the search API
* @author David Bigelow
* @version @date
*/
public abstract class EANFoundation extends Object implements EANObject, Serializable, Cloneable {

    /**
     * FIELD
     */
    public static final String NEW_LINE = "\n";
    /**
     * FIELD
     */
    public static final String NEW_LINE_HTML = "<HTML>";
    /**
    * FIELD
    */
    public static final String TAB = "\t";


    /**
     * FIELD
     */
    public static final int INIT_CAP = 3;

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;
    /**
     * FIELD
     */
    protected Hashtable m_hsh1 = null;
    /**
     * FIELD
     */
    protected Hashtable m_hsh2 = null;

    private EANFoundation m_f = null;
    private transient EANFoundation mt_f = null;
    private Profile m_prof = null;

    private String m_strK = null;
    private Class m_cl = null;

    private boolean m_bS = false; // Selected
    private boolean m_bA = true; // Active
    
    protected void dereference(){
   		mt_f = null;
   		m_f = null;
     	if (m_hsh1 != null){
     		m_hsh1.clear();
     		m_hsh1 = null;
    	}
     	if (m_hsh2 != null){
     		m_hsh2.clear();
     		m_hsh2 = null;
    	}
    	m_prof = null;
    	m_strK = null;
    	m_cl = null;
    }

    // make sure they have adequate debugging
    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public abstract String dump(boolean _brief);
    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public abstract String toString();

    // Make sure objects can be described
    /**
     * getLongDescription
     *
     * @return
     *  @author David Bigelow
     */
    public abstract String getLongDescription();
    /**
     * getShortDescription
     *
     * @return
     *  @author David Bigelow
     */
    public abstract String getShortDescription();

    /**
     * getVersion
     *
     * @return
     *  @author David Bigelow
     */
    public String getVersion() {
        return "$Id: EANFoundation.java,v 1.56 2014/02/17 13:43:36 wendy Exp $";
    }

    /**
     * EANFoundation
     *
     * @param _f
     * @param _prof
     * @param _s
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public EANFoundation(EANFoundation _f, Profile _prof, String _s) throws MiddlewareRequestException {

        super();

        // If _f == null then we have to have a profile...
        T.est(!(_f == null && _prof == null), "Profile cannot be null if you have a null Parent!");

        // O.K.  we should be fine now
        // Set up all the basic information here.
        // it is simple
        // Lets not make hashtables unless needed..
        // rigth now.. this is a pretty kludgy way.. but we must
        // try to minimize space here
        //m_hsh1 = new Hashtable();
        //m_hsh2 = new Hashtable();

        setKey(_s);
        m_f = _f;
        m_prof = _prof;

    }

    // Setkey and getkey stuff

    /**
     * setKey
     *
     * @param _s
     *  @author David Bigelow
     */
    protected void setKey(String _s) {
        m_strK = _s;
    }

    /**
     * (non-Javadoc)
     * getKey
     *
     * @see COM.ibm.eannounce.objects.EANObject#getKey()
     */
    public String getKey() {
		return m_strK;
    }

    /**
     * used by Vector.contains()
     * MN33607291 was getting dupes in uplink/dnlink vector
     * @param obj
     */
    public boolean equals(Object obj)
    {
		if (obj instanceof EANFoundation) {
			EANFoundation other = (EANFoundation)obj;
			String thisKey = getKey();
			if (thisKey==null){
				thisKey = "";
			}

			return (thisKey.equals(other.getKey()));
		}else{
			return super.equals(obj);
		}
    }


    // Profile and NSL related stuff

    /**
     * (non-Javadoc)
     * getProfile
     *
     * @see COM.ibm.eannounce.objects.EANObject#getProfile()
     */
    public Profile getProfile() {
        // We need to bubble up if we have a parent
        if (m_f == null) {
            return m_prof;
        }
        return m_f.getProfile();
    }

    /**
     * setProfile
     *
     * @param _prof
     *  @author David Bigelow
     */
    protected void setProfile(Profile _prof) {
        m_prof = _prof;
    }

    /**
     * (non-Javadoc)
     * getNLSID
     *
     * @see COM.ibm.eannounce.objects.EANObject#getNLSID()
     */
    public int getNLSID() {
        if (m_f == null) {
            if (m_prof == null) {
                return 1;
            } else {
                return m_prof.getReadLanguage().getNLSID();
            }
        }
        return m_f.getNLSID();
    }

    /**
     * (non-Javadoc)
     * getNLSItem
     *
     * @see COM.ibm.eannounce.objects.EANObject#getNLSItem()
     */
    public NLSItem getNLSItem() {
        if (m_f == null) {
            if (m_prof == null) {
                return null;
            } else {
                return m_prof.getReadLanguage();
            }
        } else {
            return m_f.getNLSItem();
        }
    }

    // Selected Related

    /**
     * (non-Javadoc)
     * isSelected
     *
     * @see COM.ibm.eannounce.objects.EANObject#isSelected()
     */
    public boolean isSelected() {
        return m_bS;
    }

    /**
     * (non-Javadoc)
     * setSelected
     *
     * @see COM.ibm.eannounce.objects.EANObject#setSelected(boolean)
     */
    public void setSelected(boolean _b) {
        m_bS = _b;
    }

    /**
     * isActive
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isActive() {
        return m_bA;
    }

    /**
     * setActive
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setActive(boolean _b) {
        m_bA = _b;
    }

    // Parent Related

    /**
     * getParent
     *
     * @return
     * @author David Bigelow
     * @concurrency $none
     */
    public synchronized EANFoundation getParent() {
        if (m_f == null) {
            return mt_f;
        }
        return m_f;
    }

    /**
     * clipParent
     *
     *  @author David Bigelow
     * @concurrency $none
     */
    protected synchronized void clipParent() {
    	//IN4836385 - make sure a profile still exists
    	if(m_f!=null && m_prof==null){
    		m_prof = m_f.getProfile();
    	}
        m_f = null;
    }

    /**
     * setParent
     *
     * @param _f
     *  @author David Bigelow
     * @concurrency $none
     */
    public synchronized void setParent(EANFoundation _f) {
        if (_f != null) {
            m_prof = null;
        }
        m_f = _f;
    }

    /**
     * setTransientParent
     *
     *  @author David Bigelow
     * @concurrency $none
     */
    protected synchronized void setTransientParent() {
        mt_f = m_f;
    }

    /**
     * resetTransientParent
     *
     *  @author David Bigelow
     * @concurrency $none
     */
    protected synchronized void resetTransientParent() {
        mt_f = null;
    }

    /**
     * setTargetClass
     *
     * @param _cl
     *  @author David Bigelow
     */
    public void setTargetClass(Class _cl) {
        m_cl = _cl;
    }

    /**
     * getTargetClass
     *
     * @return
     *  @author David Bigelow
     */
    public Class getTargetClass() {
        if (m_cl == null) {
            return getClass();
        }
        return m_cl;
    }

    /**
     * resetHash1
     *
     *  @author David Bigelow
     */
    protected void resetHash1() {
        m_hsh1 = null;
    }

    /**
     * resetHash2
     *
     *  @author David Bigelow
     */
    protected void resetHash2() {
        m_hsh2 = null;
    }

    /**
     * getALMarker
     *
     * @return
     *  @author David Bigelow
     */
    public String getALMarker() {
        return this.hashCode() + "";
    }


}
