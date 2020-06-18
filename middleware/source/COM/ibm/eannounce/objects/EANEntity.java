//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANEntity.java,v $
// Revision 1.44  2012/11/08 21:30:08  wendy
// make sure up and down links are deref
//
// Revision 1.43  2009/05/11 15:27:43  wendy
// Support dereference for memory release
//
// Revision 1.42  2008/01/11 17:13:56  wendy
// Removed hardcoded check for WGMODEL, meta is now cleaned up
//
// Revision 1.41  2007/08/22 19:34:20  wendy
// MN32841099 prevent using obsolete WGMODEL
//
// Revision 1.40  2005/02/14 17:57:46  dave
// more Jtest
//
// Revision 1.39  2004/11/05 22:54:59  joan
// check for index range for getUpLink and getDownLink
//
// Revision 1.38  2004/10/28 21:34:35  dave
// paren issue
//
// Revision 1.37  2004/10/28 21:24:56  dave
// added for JUI
//
// Revision 1.36  2004/10/28 20:43:50  dave
// syn
//
// Revision 1.35  2004/10/28 20:38:48  dave
// syntax
//
// Revision 1.34  2004/10/28 20:32:20  dave
// only add to vector if not already there
//
// Revision 1.33  2004/10/28 20:01:42  dave
// syntax
//
// Revision 1.32  2004/10/28 19:56:35  dave
// trying out a Vectory for uplink and downlink
//
// Revision 1.31  2004/10/21 16:49:53  dave
// trying to share compartor
//
// Revision 1.30  2004/01/12 23:20:27  dave
// more memory squeezing
//
// Revision 1.29  2004/01/12 18:04:25  dave
// more tossing weight over board
//
// Revision 1.28  2003/05/11 02:04:40  dave
// making EANlists bigger
//
// Revision 1.27  2003/01/10 00:10:56  dave
// added resetEntityItem on the EntityGroup
//
// Revision 1.26  2002/10/10 19:37:26  dave
// syntax
//
// Revision 1.25  2002/10/10 19:30:11  dave
// setting hooks to catch uplink.. down link fishes
//
// Revision 1.24  2002/05/03 16:27:40  gregg
// get() is now getUpLink()
//
// Revision 1.23  2002/04/19 22:06:18  dave
// making sense of swapkey = resetKey
//
// Revision 1.22  2002/04/10 19:50:19  dave
// Added a cloneStructure routine to the EANMetaAttribute
//
// Revision 1.21  2002/04/03 19:13:45  dave
// resetAttribute was reseting downlink on EANEntity
//
// Revision 1.20  2002/03/28 18:37:45  dave
// syntax fix for addRecord closure
//
// Revision 1.19  2002/03/28 18:29:00  dave
//  checking them all in for syntax
//
// Revision 1.18  2002/03/28 18:18:25  dave
// close the loop on add record
//
// Revision 1.17  2002/03/11 20:56:15  dave
// mass changes for beginnings of edit
//
// Revision 1.16  2002/02/27 00:28:52  dave
// syntax fix
//
// Revision 1.15  2002/02/27 00:20:17  dave
// modified isDisplayable. to only display Entity Tabs w/ orphaned items
//
// Revision 1.14  2002/02/19 18:46:55  dave
// syntax fix
//
// Revision 1.13  2002/02/19 18:28:59  dave
// added transfer Attributes function
//
// Revision 1.12  2002/02/15 18:18:31  dave
// more fixes for EAN  table structrure
//
// Revision 1.11  2002/02/15 18:06:52  dave
// expanded EAN Table structures
//
// Revision 1.10  2002/02/14 01:21:17  dave
// created a binder for action groups so relator/entity can be shown
//
// Revision 1.9  2002/02/12 21:01:58  dave
// added toString methods for diplay help
//
// Revision 1.8  2002/02/05 18:01:37  dave
// more import statement fixes
//
// Revision 1.7  2002/02/05 16:39:13  dave
// more expansion of abstract model
//
// Revision 1.6  2002/02/04 18:21:35  dave
// fixes syntax
//
// Revision 1.5  2002/02/04 18:02:46  dave
// added new Abstract EANAttribute
//
// Revision 1.4  2002/02/04 17:39:12  dave
// minor syntax
//
// Revision 1.3  2002/02/04 17:29:52  dave
// more fixes
//
// Revision 1.2  2002/02/04 17:21:16  dave
// misc fixes
//
// Revision 1.1  2002/02/04 17:07:15  dave
// new abstract class EANEntity
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

import java.util.Vector;

/**
* This Object holds all the required information for an Entity
*/
public abstract class EANEntity extends EANDataFoundation {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    private String m_strEntityType = null;
    private int m_iEntityID = 0;

    private EANList m_ela = null; // The Attributes
    private Vector m_vctUpLink = null; // Holds all the UpLink EANEANEntitys
    private Vector m_vctDownLink = null; // Holds all the DownLinkren EANEANEntitys

    // Abstract methods
    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public abstract String toString();
    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public abstract String dump(boolean _brief);

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: EANEntity.java,v 1.44 2012/11/08 21:30:08 wendy Exp $";
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /*
    * Right Now.. there is only one way to create an EANEntity
    * and it is Not through a database call yet
    */
    /**
     * EANEntity
     *
     * @param m_f
     * @param _prof
     * @param _strEntityType
     * @param _iEntityID
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public EANEntity(EANFoundation m_f, Profile _prof, String _strEntityType, int _iEntityID) throws MiddlewareRequestException {
        super(m_f, _prof, _strEntityType + _iEntityID);
        m_strEntityType = _strEntityType;
        m_iEntityID = _iEntityID;
    }

    // DownLink Accessors

    /**
     * getDownLink
     *
     * @return
     *  @author David Bigelow
     */
    public Vector getDownLink() {
        if (m_vctDownLink == null) {
            m_vctDownLink = new Vector();
        }
        return m_vctDownLink;
    }

    /**
     * getDownLink
     *
     * @param _ii
     * @return
     *  @author David Bigelow
     */
    public EANEntity getDownLink(int _ii) {
        if (m_vctDownLink == null) {
            return null;
        }
        if (_ii < 0 || _ii > m_vctDownLink.size() - 1) {
            return null;
        }
        return (EANEntity) m_vctDownLink.elementAt(_ii);
    }

    /**
     * getDownLink
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    public EANEntity getDownLink(String _str) {
        if (m_vctDownLink == null) {
            return null;
        }
        for (int i = 0; i < m_vctDownLink.size(); i++) {
            EANEntity e = (EANEntity) m_vctDownLink.elementAt(i);
            if (e.getKey().equals(_str)) {
                return e;
            }
        }
        return null;
    }

    /**
     * getDownLinkCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getDownLinkCount() {
        if (m_vctDownLink == null) {
            return 0;
        }
        return m_vctDownLink.size();
    }

    /**
     * containsDownLink
     *
     * @param _e
     * @return
     *  @author David Bigelow
     */
    public boolean containsDownLink(EANEntity _e) {
        if (m_vctDownLink == null) {
            return false;
        }
        return m_vctDownLink.contains(_e);
    }

    //  public boolean containsDownLink(String _str) {
    //    if (m_vctDownLink == null) return false;
    //    return m_vctDownLink.containsKey(_str);
    //  }

    /**
     * hasDownLinks
     *
     * @return
     *  @author David Bigelow
     */
    public boolean hasDownLinks() {
        if (m_vctDownLink == null) {
            return false;
        }
        return getDownLinkCount() > 0;
    }

    //  UpLink Accessors

    /**
     * getUpLink
     *
     * @return
     *  @author David Bigelow
     */
    public Vector getUpLink() {
        if (m_vctUpLink == null) {
            m_vctUpLink = new Vector();
        }
        return m_vctUpLink;
    }

    /**
     * getUpLink
     *
     * @param _ii
     * @return
     *  @author David Bigelow
     */
    public EANEntity getUpLink(int _ii) {
        if (m_vctUpLink == null) {
            return null;
        }
        if (_ii < 0 || _ii > m_vctUpLink.size() - 1) {
            return null;
        }

        return (EANEntity) m_vctUpLink.elementAt(_ii);
    }

    /**
     * getUpLink
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    public EANEntity getUpLink(String _str) {
        if (m_vctUpLink == null) {
            return null;
        }
        for (int i = 0; i < m_vctUpLink.size(); i++) {
            EANEntity e = (EANEntity) m_vctUpLink.elementAt(i);
            if (e.getKey().equals(_str)) {
                return e;
            }
        }
        return null;
    }

    /**
     * getUpLinkCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getUpLinkCount() {
        if (m_vctUpLink == null) {
            return 0;
        }
        return m_vctUpLink.size();
    }

    /**
     * hasUpLinks
     *
     * @return
     *  @author David Bigelow
     */
    public boolean hasUpLinks() {
        if (m_vctUpLink == null) {
            return false;
        }
        return getUpLinkCount() > 0;
    }

    /**
     * containsUpLink
     *
     * @param _e
     * @return
     *  @author David Bigelow
     */
    public boolean containsUpLink(EANEntity _e) {
        if (m_vctUpLink == null) {
            return false;
        }
        return m_vctUpLink.contains(_e);
    }

    // public boolean containsUpLink(String _str) {
    //    if (m_vctUpLink == null) return false;
    //    return m_vctUpLink.containsKey(_str);
    //  }

    // EANAttribute Accessors

    /**
     * getAttribute
     *
     * @return
     *  @author David Bigelow
     */
    protected EANList getAttribute() {
        if (m_ela == null) {
            m_ela = new EANList();
        }
        return m_ela;
    }

    /**
     * getAttribute
     *
     * @param _ii
     * @return
     *  @author David Bigelow
     */
    public EANAttribute getAttribute(int _ii) {
        if (m_ela == null) {
            return null;
        }
        return (EANAttribute) m_ela.getAt(_ii);
    }

    /**
     * getAttribute
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    public EANAttribute getAttribute(String _str) {
        if (m_ela == null) {
            return null;
        }
        return (EANAttribute) m_ela.get(_str);
    }

    /**
     * getAttributeCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getAttributeCount() {
        if (m_ela == null) {
            return 0;
        }
        return m_ela.size();
    }

    /**
     * containsAttribute
     *
     * @param _att
     * @return
     *  @author David Bigelow
     */
    public boolean containsAttribute(EANAttribute _att) {
        if (m_ela == null) {
            return false;
        }
        return m_ela.containsKey(_att.getKey());
    }

    /**
     * containsAttribute
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    public boolean containsAttribute(String _str) {
        if (m_ela == null) {
            return false;
        }
        return m_ela.containsKey(_str);
    }

    /**
     * Returns the EntityType
     *
     * @return String
     */
    public String getEntityType() {
        return m_strEntityType;
    }

    /**
     * getEntityID
     *
     * @return
     *  @author David Bigelow
     */
    public int getEntityID() {
        return m_iEntityID;
    }

    //
    // MUTATORS
    //

    // DownLink Mutators

    /**
     * resetDownLink
     *
     *  @author David Bigelow
     */
    protected void resetDownLink() {
        m_vctDownLink = new Vector();
    }

    /**
     * resetDownLinkKey
     *
     * @param _strOld
     *  @author David Bigelow
     */
    protected void resetDownLinkKey(String _strOld) {
        // Since we are a vector.. we do not have to do anything here
        //    if (m_vctDownLink == null) return;
        //    m_vctDownLink.resetKey(_strOld);
    }

    /**
     * putDownLink
     *
     * @param _e
     *  @author David Bigelow
     */
    public void putDownLink(EANEntity _e) {
        if (_e == null) {
            System.out.println("EANEntity.putDownLink:ei is null!");
            return;
        }
        if (m_vctDownLink == null) {
            m_vctDownLink = new Vector();
        }
        if (!m_vctDownLink.contains(_e)) {
            m_vctDownLink.add(_e);
        }
        if (!_e.containsUpLink(this)) {
            _e.putUpLink(this);
        }
    }

    /**
     * removeDownLink
     *
     * @param _e
     *  @author David Bigelow
     */
    public void removeDownLink(EANEntity _e) {
        if (m_vctDownLink != null) {
            m_vctDownLink.remove(_e);
        }
        if (_e.containsUpLink(this)) {
            _e.removeUpLink(this);
        }
    }

    // UpLink Mutators

    /**
     * resetUpLink
     *
     *  @author David Bigelow
     */
    protected void resetUpLink() {
        m_vctUpLink = new Vector();
    }

    /**
     * resetUpLinkKey
     *
     * @param _strOld
     *  @author David Bigelow
     */
    protected void resetUpLinkKey(String _strOld) {
        //    if (m_vctUpLink == null) return;
        //    m_vctUpLink.resetKey(_strOld);
    }

    /**
     * putUpLink
     *
     * @param _e
     *  @author David Bigelow
     */
    public void putUpLink(EANEntity _e) {
        if (_e == null) {
            System.out.println("EANEntity.putUpLink:e is null!");
            return;
        }
        if (m_vctUpLink == null) {
            m_vctUpLink = new Vector();
        }
        if (!m_vctUpLink.contains(_e)) {
            m_vctUpLink.add(_e);
        }
        if (!_e.containsDownLink(this)) {
            _e.putDownLink(this);
        }
    }

    /**
     * removeUpLink
     *
     * @param _e
     *  @author David Bigelow
     */
    public void removeUpLink(EANEntity _e) {
        if (m_vctUpLink != null) {
            m_vctUpLink.remove(_e);
        }
        if (_e.containsDownLink(this)) {
            _e.removeDownLink(this);
        }
    }

    /*
    * This changes the entityID..
    * and any possible references to it
    *
    * Changing the entityID changes the key
    * so a new Key will come back
    */
    /**
     * setEntityID
     *
     * @param _i
     *  @author David Bigelow
     */
    protected void setEntityID(int _i) {

        String strOld = getKey();
        String strNew = getEntityType() + _i;

        m_iEntityID = _i;
        setKey(strNew);
        // Now lets restate the links
        for (int ii = 0; ii < getUpLinkCount(); ii++) {
            EntityItem ei = (EntityItem) getUpLink(ii);
            ei.resetDownLinkKey(strOld);
        }
        for (int ii = 0; ii < getDownLinkCount(); ii++) {
            EntityItem ei = (EntityItem) getDownLink(ii);
            ei.resetUpLinkKey(strOld);
        }

    }

    // Attribute Mutators

    /**
     * resetAttribute
     *
     *  @author David Bigelow
     */
    protected void resetAttribute() {
        if (m_ela == null) {
            return;
        }
        for (int iy = 0; iy < getAttributeCount(); iy++) {
            EANAttribute att = getAttribute(iy);
            att.setParent(null);
        }
        m_ela = null;

    }

    /**
     * putAttribute
     *
     * @param _att
     *  @author David Bigelow
     */
    public void putAttribute(EANAttribute _att) {
        if (m_ela == null) {
            m_ela = new EANList();
        }
        m_ela.put(_att);
        _att.setParent(this);
    }

    /**
     * removeAttribute
     *
     * @param _att
     * @return
     *  @author David Bigelow
     */
    public EANAttribute removeAttribute(EANAttribute _att) {
        if (m_ela == null) {
            return null;
        }
        EANAttribute att = (EANAttribute) m_ela.remove(_att);
        if (att == null) {
            return null;
        }
        att.setParent(null);
        return att;
    }

    /**
     * removeAttribute
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    public EANAttribute removeAttribute(String _str) {
        if (m_ela == null) {
            return null;
        }
        EANAttribute att = (EANAttribute) m_ela.remove(_str);
        if (att == null) {
            return null;
        }
        att.setParent(null);
        return att;
    }

    // Clean up routine
    /**
     * dereference
     *
     *  @author David Bigelow
     */
    protected void dereference() {
    	if(getKey()==null){
    		return; // already derefd
    	}
	
    	m_strEntityType = null;
    	// not all entityitems are in entitygroups - so they are not derefd, check here
    	if(m_vctUpLink!=null){
    		for (int iy = 0; iy < m_vctUpLink.size(); iy++) {
    			EANEntity ent = (EANEntity)m_vctUpLink.elementAt(iy);
    			if(ent.getKey()==null){
    				continue;// already derefd
    			}
    		
    			EANFoundation parent = ent.getParent();
    			if(parent==null){ // if not null the parent will deref
    				ent.dereference();
    			}
    		} 
    		m_vctUpLink.clear();
    		m_vctUpLink = null;
    	}
    
    	if(m_vctDownLink!=null){
    		for (int iy = 0; iy < m_vctDownLink.size(); iy++) {
    			EANEntity ent = (EANEntity)m_vctDownLink.elementAt(iy);
    			if(ent.getKey()==null){
    				continue;// already derefd
    			}
    			EANFoundation parent = ent.getParent();
    			if(parent==null){ // if not null, the parent will deref 
    				ent.dereference();
    			}
    		} 
    		m_vctDownLink.clear();
    		m_vctDownLink = null;
    	}
    
//  	resetAttribute();
    	setParent(null);

    	if (m_ela != null) {
    		for (int iy = 0; iy < getAttributeCount(); iy++) {
    			EANAttribute att = getAttribute(iy);
    			att.dereference();
    		} 
    		m_ela.clear();
    		m_ela = null;
    	}
    	super.dereference();
    }

}
