//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: OPICMABRGroup.java,v $
// Revision 1.23  2008/01/31 21:29:04  wendy
// Cleanup RSA warnings
//
// Revision 1.22  2005/03/11 22:42:54  dave
// removing some auto genned stuff
//
// Revision 1.21  2005/03/11 20:28:56  roger
// Support foreign ABR
//
// Revision 1.20  2005/01/27 04:02:36  dave
// removing automated readObject from Jtest
//
// Revision 1.19  2005/01/26 23:20:02  dave
// Jtest clean up
//
// Revision 1.18  2005/01/26 20:45:36  dave
// Jtest cleanup effort
//
// Revision 1.17  2004/10/13 18:34:57  dave
// syntax
//
// Revision 1.16  2004/10/13 18:28:39  dave
// adding a reset feature to ABREntityGroup
//
// Revision 1.15  2004/02/26 21:32:18  dave
// final pass
//
// Revision 1.14  2004/02/26 21:22:27  dave
// more trace
//
// Revision 1.13  2004/02/26 21:18:36  dave
// better trace
//
// Revision 1.12  2004/02/26 21:16:46  dave
// more syntax argh
//
// Revision 1.11  2004/02/26 20:53:41  dave
// comments
//
// Revision 1.10  2004/02/26 20:52:04  dave
// another fix
//
// Revision 1.9  2004/02/26 20:43:20  dave
// tracking inprocess abri's in the abrGroup
//
// Revision 1.8  2002/07/30 01:15:15  dave
// more cleanup
//
// Revision 1.7  2002/06/26 14:49:00  roger
// strNow not needed ~until~ initPrintWriter
//
// Revision 1.6  2002/06/07 17:24:59  roger
// Now string needed in OPICMABRItem
//
// Revision 1.5  2001/08/22 16:53:13  roger
// Removed author RM
//
// Revision 1.4  2001/05/02 06:44:47  dave
// minor adjustment to createOPICMABRItem
//
// Revision 1.3  2001/05/02 06:05:08  dave
// more ABR wave II stuf
//
// Revision 1.2  2001/04/29 21:58:52  dave
// Syntax Fixes Round 1
//
// Revision 1.1  2001/04/29 21:17:43  dave
// ABR Base Function Work
//
//


package COM.ibm.opicmpdh.transactions;


import COM.ibm.opicmpdh.middleware.D;
import java.io.Serializable;
import java.util.Vector;


/**
* This Object holds ...
*
* @author  David Bigelow
* @version @date
* see PDHGroup
*/
public class OPICMABRGroup
    extends Object
    implements OPICMObject, Serializable, Cloneable {
    // Instance variables
    /**
    * @serial
    */
    static final long serialVersionUID = 1L;
    // used to help define the table model
    /**
     * COLUMN FOR ENTITYTYPE
     */
    public static final int ENTITYTYPE = 0;
    /**
     * COLUMN FOR ENTITYID
     */
    public static final int ENTITYID = 1;
    /**
     * COLUMN FOR ABRCODE
     */
    public static final int ABRCODE = 2;
    /**
     * COLUMN FOR ABRDESC
     */
    public static final int ABRDESC = 3;
    /**
     * COLUMN FOR ABRNEW
     */
    public static final int ABRNEW = 4;
    /**
     * COLUMN FOR ABRQUEUED
     */
    public static final int ABRQUEUED = 5;
    /**
     * COLUMN FOR ABRINPROC
     */
    public static final int ABRINPROC = 6;
    /**
     * COLUMN FOR ABRSUCCESS
     */
    public static final int ABRSUCCESS = 7;
    /**
     * COLUMN FOR ABRFAILURE
     */
    public static final int ABRFAILURE = 8;
    /**
     * COLUMN FOR OPENID
     */
    public static final int OPENID = 9;

    private OPICMList m_olabri = null;
    private Vector m_vctabrip = null;
    private String m_strEnterprise = null;

 
    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /**
     * Creates a new SoftLockGroup for the given EntityType
     *
     * @param _s1
     */
    public OPICMABRGroup(String _s1) {
        super();
        m_olabri = new OPICMList();
        m_vctabrip = new Vector();
        m_strEnterprise = _s1;
    }

    /**
     * returns the number of OPICMABRItem
     *
     * @return int
     */
    public int getOPICMABRItemCount() {
        return m_olabri.size();
    }

    /**
     * returns the hashkey (Enterprise)
     *
     * @return String
     */
    public String hashkey() {
        return m_strEnterprise;
    }

    /**
     * returns the ABRItem at index i
     *
     * @param _i
     * @return OPICMABRItem
     */
    public OPICMABRItem getOPICMABRItem(int _i) {
        return (OPICMABRItem) m_olabri.getAt(_i);
    }

    /**
     * get Item by Hashkey
     *
     * @param _hashkey
     * @return
     * @author Dave
     */
    public OPICMABRItem getOPICMABRItem(String _hashkey) {
        return (OPICMABRItem) m_olabri.get(_hashkey);
    }

    /**
     * Puts an OPICMABRItem in the list not already there -
     *
     * @param _abri
     */
    public void putOPICMABRItem(OPICMABRItem _abri) {
        if (!m_olabri.containsKey(_abri.hashkey())) {
            m_olabri.put(_abri);
        }
    }

    /**
     * Creates a new OPICMABRItem and places it in the list
     * and/or returns an existing one that matched the key
     *
     * @param _s1
     * @param _i1
     * @param _s2
     * @param _s3
     * @param _i2
     * @param _s4
     * @param _s5
     * @return OPICMABRItem
     */
    public OPICMABRItem createOPICMABRItem(
        String _s1,
        int _i1,
        String _s2,
        String _s3,
        int _i2,
        String _s4,
        String _s5,
        int _i3) {

        OPICMABRItem abri = null;

        if (m_olabri.containsKey(_s1 + _i1 + _s2)) {
            return getOPICMABRItem(_s1 + _i1 + _s2);
        }
        abri = new OPICMABRItem(_s1, _i1, _s2, _s3, _i2, _s4, _s5, _i3);
        if (!m_vctabrip.contains(abri)) {
            putOPICMABRItem(abri);
        }
        return abri;

    }

    /**
     * Display the object and show values
     *
     * @param out
     */
    public void display(java.io.PrintStream out) {
        out.println("OPICMABRGroup:" + m_strEnterprise);
        for (int i = 0; i < m_olabri.size(); i++) {
            OPICMABRItem abri = (OPICMABRItem) m_olabri.getAt(i);
            abri.display(out);
        }
    }

    /**
     * We will take any new information from the passed list and add it to this list
     * @param _abrg
     * @author Dave
     */
    protected void mergeOPICMABRGroup(OPICMABRGroup _abrg) {
        for (int x = 0; x < _abrg.getOPICMABRItemCount(); x++) {
            OPICMABRItem abri = _abrg.getOPICMABRItem(x);
            m_olabri.replace(abri);
        }
    }

    /**
     * Removes all the ABRItems from this group that are contained in
     * the ABRGroup that was passed
     * @param _abrg
     * @author Dave
     */
    public void removeOPICMABRItems(OPICMABRGroup _abrg) {
        for (int x = 0; x < _abrg.getOPICMABRItemCount(); x++) {
            m_olabri.remove(_abrg.getOPICMABRItem(x));
        }
    }

    /**
     * remove ABR Item
     * @param _abri
     * @author Dave
     */
    public void removeOPICMABRItem(OPICMABRItem _abri) {
        m_olabri.remove(_abri);
    }

    /**
     * reset all items
     * @author Dave
     */
    public void resetOPICMABRItems() {
        m_olabri = new OPICMList();
    }

    /**
     * remove item at the given index
     *
     * @param x
     * @author Dave
     */
    public void removeOPICMABRItem(int x) {
        m_olabri.remove(x);
    }

    /**
     * remove all inprocess ID's
     * @param _abri
     * @author Dave
     */
    public void removeABRInProcess(OPICMABRItem _abri) {
        D.ebug(
            "removeABRInProcess.Removing "
                + _abri
                + " from the InProccess Vectory");
        m_vctabrip.removeElement(_abri);
    }

    /**
     * addABRInProcess
     *
     * @param _abri
     * @author Dave
     */
    public void addABRInProcess(OPICMABRItem _abri) {

        if (!m_vctabrip.contains(_abri)) {
            D.ebug(
                "addABRInProcess.Adding "
                    + _abri
                    + " to the InProccess Vectory");
            m_vctabrip.addElement(_abri);
        } else {
            D.ebug(
                "addABRInProcess.**** Attempting to add "
                    + _abri
                    + " to the InProccess Vectory Twice");
        }
    }

    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: OPICMABRGroup.java,v 1.23 2008/01/31 21:29:04 wendy Exp $";
    }


    /**
     * Returns a String representation of the cell denoted by the row and col
     * @param _r
     * @param _c
     * @return
     * @author Dave
     */
    public Object get(int _r, int _c) {
        return ((OPICMABRItem) m_olabri.getAt(_r)).get(_c);
    }
}
