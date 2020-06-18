//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANMetaEntity.java,v $
// Revision 1.47  2009/05/11 15:17:08  wendy
// Support dereference for memory release
//
// Revision 1.46  2009/03/12 14:59:14  wendy
// Add methods for metaui access
//
// Revision 1.45  2008/01/11 17:13:56  wendy
// Removed hardcoded check for WGMODEL, meta is now cleaned up
//
// Revision 1.44  2007/08/22 19:34:20  wendy
// MN32841099 prevent using obsolete WGMODEL
//
// Revision 1.43  2005/02/15 18:42:24  dave
// More JTest cleanup
//
// Revision 1.42  2004/01/13 23:39:54  dave
// more squeezing on m_el
//
// Revision 1.41  2004/01/12 22:49:49  dave
// fixed a boobo
//
// Revision 1.40  2004/01/12 22:37:34  dave
// more squeezing
//
// Revision 1.39  2003/05/27 18:31:42  dave
// Rmi clipping and serialization tracking
//
// Revision 1.38  2003/05/11 02:04:40  dave
// making EANlists bigger
//
// Revision 1.37  2002/06/05 17:18:47  gregg
// removed the previously added methods
//
// Revision 1.36  2002/06/05 16:39:01  gregg
// putMetaAt, getIndexOfMeta, putDataAt, getIndexOfData
//
// Revision 1.35  2002/03/13 18:28:19  dave
// attempt at creating the putCache information
//
// Revision 1.34  2002/02/14 01:49:56  dave
// fixed typo
//
// Revision 1.33  2002/02/14 01:44:25  dave
// more syntax fixes
//
// Revision 1.32  2002/02/14 01:21:17  dave
// created a binder for action groups so relator/entity can be shown
//
// Revision 1.31  2002/02/05 16:39:14  dave
// more expansion of abstract model
//
// Revision 1.30  2002/02/02 20:03:06  dave
// ensuring that either a parent exists or a profile exists
//
// Revision 1.29  2002/02/02 19:48:59  dave
// more foundation work
//
// Revision 1.28  2002/01/31 22:57:19  dave
// more Foundation Cleanup
//
// Revision 1.27  2002/01/31 22:28:20  dave
// more Foundation changes
//
// Revision 1.26  2002/01/31 21:32:22  dave
// more mass abstract changes
//
// Revision 1.25  2002/01/31 20:49:57  dave
// more fixes
//
// Revision 1.24  2002/01/31 20:32:11  dave
// fixes to Foundation Objects
//
// Revision 1.23  2002/01/31 19:01:49  dave
// clean up and syntax fix
//
// Revision 1.22  2002/01/31 17:58:54  dave
// Rearranging Abrstract levels for more consistiency
//
// Revision 1.21  2002/01/28 20:53:24  dave
// merging 1.0 maint into 1.1
//
// Revision 1.20  2002/01/17 18:33:54  dave
// misc adjustments to help cloning and copying of structures
// in e-announce
//
// Revision 1.19  2002/01/07 21:28:34  joan
// add removeData method
//
// Revision 1.18  2001/08/14 16:11:09  dave
// corrected object type name issues
//
// Revision 1.17  2001/08/14 16:03:17  dave
// base code for Preview Report Set
//
// Revision 1.16  2001/08/09 18:30:03  dave
// syntax
//
// Revision 1.15  2001/08/09 18:18:36  dave
// syntax fixes
//
// Revision 1.14  2001/08/09 18:04:30  dave
// first attempt at a table model
//
// Revision 1.13  2001/08/08 21:16:00  dave
// more tracing and cleanup...
//
// Revision 1.12  2001/08/08 21:05:23  dave
// compile deficient code fix
//
// Revision 1.11  2001/08/08 21:03:08  dave
// compile issues addressed
//
// Revision 1.10  2001/08/08 20:57:11  dave
// tracking and tracing additions on RequestSearch API
//
// Revision 1.9  2001/08/08 20:20:06  dave
// First Fix for null pointer in constructor of the metaentity
//
// Revision 1.8  2001/08/08 17:56:24  dave
// minor clean up
//
// Revision 1.7  2001/08/08 17:52:19  dave
// syntax fixes
//
// Revision 1.6  2001/08/08 17:41:45  dave
// more generalization is prep for table model on Search API
//
// Revision 1.5  2001/08/03 17:02:33  dave
// Building up the Association Object
//
// Revision 1.4  2001/08/01 23:28:19  dave
// final syntax
//
// Revision 1.3  2001/08/01 23:24:46  dave
// sintax
//
// Revision 1.2  2001/08/01 23:10:50  dave
// general syntax
//
// Revision 1.1  2001/08/01 23:02:42  dave
// generic object to house meta entity definition
//
// Revision 1.1  2001/08/01 22:10:10  dave
// more foundation work
//
//
package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

/**
* This is the basis for all eannounce objects used by the search API
* @author David Bigelow
* @version @date
*/
public abstract class EANMetaEntity extends EANMetaFoundation {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;
    /**
     * FIELD
     */
    public final static int LIST_SIZE = 10;

    private EANList m_el = null; // list1
    private EANList m_eld = null; // list2
    
    protected void dereference() {
    	if (m_el != null){
    		m_el.clear();
    		m_el = null;
    	}
    	if (m_eld != null){
    		m_eld.clear();
    		m_eld = null;
    	}

    	super.dereference();
    }

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
        return "$Id: EANMetaEntity.java,v 1.47 2009/05/11 15:17:08 wendy Exp $";
    }

    /*
    * Basic Constructor
    */
    /**
     * EANMetaEntity
     *
     * @param _mf
     * @param _prof
     * @param _s
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public EANMetaEntity(EANMetaFoundation _mf, Profile _prof, String _s) throws MiddlewareRequestException {
        super(_mf, _prof, _s);
    }

    //
    // ACCESSORS
    //

    /**
     * getMeta
     *
     * @return
     *  @author David Bigelow
     */
    protected EANList getMeta() {
        if (m_el == null) {
            m_el = new EANList();
        }
        return m_el;
    }

    /**
     * getData
     *
     * @return
     *  @author David Bigelow
     */
    protected EANList getData() {
        if (m_eld == null) {
            m_eld = new EANList();
        }
        return m_eld;
    }

    /**
     * getMeta
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    protected EANFoundation getMeta(String _s) {
        if (m_el == null) {
            return null;
        }
        return (EANFoundation) m_el.get(_s);
    }

    /**
     * getData
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    protected EANFoundation getData(String _s) {
        if (m_eld == null) {
            return null;
        }
        return (EANFoundation) m_eld.get(_s);
    }

    /**
     * getMeta
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    protected EANFoundation getMeta(int _i) {
        if (m_el == null) {
            return null;
        }
        return (EANFoundation) m_el.getAt(_i);
    }

    /**
     * getData
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    protected EANFoundation getData(int _i) {
        if (m_eld == null) {
            return null;
        }
        return (EANFoundation) m_eld.getAt(_i);
    }

    /**
     * getMetaCount
     *
     * @return
     *  @author David Bigelow
     */
    protected int getMetaCount() {
        if (m_el == null) {
            return 0;
        }
        return m_el.size();
    }

    /**
     * getDataCount
     *
     * @return
     *  @author David Bigelow
     */
    protected int getDataCount() {
        if (m_eld == null) {
            return 0;
        }
        return m_eld.size();
    }

    /**
     * containsMeta
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    protected boolean containsMeta(String _str) {
        if (m_el == null) {
            return false;
        }
        return m_eld.containsKey(_str);
    }

    /**
     * containsData
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    protected boolean containsData(String _str) {
        if (m_eld == null) {
            return false;
        }
        return m_eld.containsKey(_str);
    }

    //
    // Mutators
    //

    /**
     * putMeta
     *
     * @param _o
     *  @author David Bigelow
     */
    protected void putMeta(EANFoundation _o) {
        if (m_el == null) {
            m_el = new EANList();
        }
        m_el.put(_o);
    }

    /**
     * putData
     *
     * @param _o
     *  @author David Bigelow
     */
    protected void putData(EANFoundation _o) {
        if (m_eld == null) {
            m_eld = new EANList();
        }
        m_eld.put(_o);
    }

    /**
     * resetData
     *
     *  @author David Bigelow
     */
    protected void resetData() {
        m_eld = null;
    }

    /*
    * You had better know what you are doing if you are going to call this
    */
    /**
     * setData
     *
     * @param _el
     *  @author David Bigelow
     */
    protected void setData(EANList _el) {
        m_eld = _el;
    }

    /*
    * You had better know what you are doing if you are going to call this
    */
    /**
     * setMeta
     *
     * @param _el
     *  @author David Bigelow
     */
    protected void setMeta(EANList _el) {
        m_el = _el;
    }

    /**
     * resetMeta
     *
     *  @author David Bigelow
     */
    protected void resetMeta() {
        m_el = null;
    }

    /**
     * removeMeta
     *
     * @param _o
     * @return
     *  @author David Bigelow
     */
    public EANFoundation removeMeta(EANFoundation _o) {
        if (m_el == null) {
            return null;
        }
        _o.setParent(null);
        return (EANFoundation) m_el.remove(_o);
    }

    /**
     * removeData
     *
     * @param _o
     * @return
     *  @author David Bigelow
     */
    protected EANFoundation removeData(EANFoundation _o) {
        if (m_eld == null) {
            return null;
        }
        return (EANFoundation) m_eld.remove(_o);
    }

    // right now.. we just copy the Description
    /**
     * copy
     *
     * @param _eanme
     *  @author David Bigelow
     */
    protected void copy(EANMetaEntity _eanme) {
        copyDescription(_eanme);
    }

}
