//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: OPICMList.java,v $
// Revision 1.15  2005/03/01 00:48:47  dave
// flush/reset vs reset flush
//
// Revision 1.14  2005/01/26 20:52:04  dave
// syntax on clonenotsupported
//
// Revision 1.13  2005/01/26 20:45:36  dave
// Jtest cleanup effort
//
// Revision 1.12  2002/04/10 21:06:58  dave
// massive .close() effort on classes and methods
//
// Revision 1.11  2001/08/22 20:20:17  dave
// some minor sintax
//
// Revision 1.10  2001/08/22 20:01:39  dave
// bug fix carry forward from V2.3 PN race condition
//
// Revision 1.9  2001/08/22 16:53:16  roger
// Removed author RM
//
// Revision 1.8  2001/05/07 20:35:49  dave
// first attempt at setting up the clearing of the Object Cache
//
// Revision 1.7  2001/04/25 03:37:59  dave
// display messages.. tightening up logic
//
// Revision 1.6  2001/04/11 15:44:29  dave
// Cleaned up OPICMList. Added a clone method
//
// Revision 1.5  2001/03/27 20:54:21  dave
// fix for opicmlist copyto function and misc debug statements
//
// Revision 1.4  2001/03/21 00:01:13  roger
// Implement java class file branding in getVersion method
//
// Revision 1.3  2001/03/18 15:22:40  roger
// Ensure all classes have getVersion method
//
// Revision 1.2  2001/03/16 16:08:59  roger
// Added Log keyword, and standard copyright
//


package COM.ibm.opicmpdh.transactions;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


import java.util.Hashtable;


import java.util.Vector;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;




/**
* This manages a vector, hashtable combination to provide an indexed way
* to find OPICM data in the PDH and OPICM  Object structures.  The objects this
* class maintains must implement OPICMObject.
* @author  David Bigelow
* @version @date
*/
public class OPICMList extends Hashtable implements Cloneable {

    // Instance variables
    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * TBD
     */
    protected Vector m_vct = null; // Vector

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /**
     * Creates the OPICMList with an initial size
     *
     * @param _i1
     */
    public OPICMList(int _i1) {
        super(_i1);
        m_vct = new Vector(_i1);
    }

    /**
    * Creates the OPICMList with no initial size
    */
    public OPICMList() {
        super();
        m_vct = new Vector();
    }

    /**
     * adds an OPICMObject to this list.  It only Places things
     * in the list that are not Currently in the List
     *
     * @concurrency $none
     * @param _o1
     */
    public synchronized void put(OPICMObject _o1) {
        if (super.containsKey(_o1.hashkey())) {
            return;
        }
        m_vct.addElement(_o1.hashkey());
        super.put(_o1.hashkey(), _o1);
    }

    /**
     * adds an Object to this list.  The Caller supplies
     * the hashkey.  It only places things in the list
     * that are NOT currently in the list.  This could
     * be rather dangerous.. since the hashkey we not
     * derived from the passed Object
     *
     * @concurrency $none
     * @param _s1
     * @param _o1
     */
    public synchronized void put(String _s1, Object _o1) {
        if (super.containsKey(_s1)) {
            return;
        }
        m_vct.addElement(_s1);
        super.put(_s1, _o1);
    }

    /**
     * Returns an Object at a specific index
     *
     * @return the Object at the specific index
     * @param _i1
     */
    public Object getAt(int _i1) {
        if (_i1 < super.size()) {
            return super.get(m_vct.elementAt(_i1));
        }
        return null;
    }

    /**
     * Returns an indexOf the object in the hashtable
     *
     * @return the index of Object in the OPICMList
     * @param _o1
     */
    public int indexOf(OPICMObject _o1) {
        return m_vct.indexOf(_o1.hashkey());
    }

    /**
     * Returns an indexOf the object in the hashtable represented by the hashkey
     *
     * @return the index of Object in the OPICMList represented by the hashkey
     * @param _s1
     */
    public int indexOf(String _s1) {
        return m_vct.indexOf(_s1);
    }

    /**
     * Removes the element at the index
     *
     * @concurrency $none
     * @param _i
     * @return OPICMObject
     */
    public synchronized OPICMObject remove(int _i) {
        OPICMObject obj = (OPICMObject) getAt(_i);
        super.remove(m_vct.elementAt(_i));
        m_vct.remove(_i);
        return obj;
    }

    /**
     * remove all
     * @author Dave
     * @concurrency $none
     */
    public synchronized void removeAll() {
        for (int i = 0; i < m_vct.size(); i++) {
            super.remove(m_vct.elementAt(i));
        }
        m_vct.removeAllElements();
    }

    /**
     * Removes the element from the list
     *
     * @concurrency $none
     * @param _o
     * @return OPICMObject
     */
    public synchronized OPICMObject remove(OPICMObject _o) {
        int i = m_vct.indexOf(_o.hashkey());
        return remove(i);
    }

    /**
     * remove from the list
     * @param _s
     * @author Dave
     * @concurrency $none
     * @concurrency $none
     */
    public synchronized void remove(String _s) {
        int i = m_vct.indexOf(_s);
        if (i != -1) {
            m_vct.remove(i);
            super.remove(_s);
        }
    }

    /**
     * This will retrieve the object indexed at the old hashkey
     * and insert a new hashkey to represent this object
     * very tricky
     * @param _strOldHashKey
     * @param _strNewHashKey
     * @author Dave
     * @concurrency $none
     * @concurrency $none
     */
    protected synchronized void swapHashKey(
        String _strOldHashKey,
        String _strNewHashKey) {
        int i = m_vct.indexOf(_strOldHashKey);
        OPICMObject obj = (OPICMObject) get(_strOldHashKey);
        if (i >= 0) {
            m_vct.removeElementAt(i);
            super.remove(_strOldHashKey);
            m_vct.insertElementAt(_strNewHashKey, i);
            super.put(_strNewHashKey, obj);
        } else {
            System.out.println("Not Found.. Skipping the swap");
        }
    }

    /**
     * Copies the OPICMList into an array.  The array must be large enough
     * to hold all the values of this OPICMList
     * @param _a
     * @author Dave
     */
    public void copyTo(Object[] _a) {
        for (int x = 0; x < this.size(); x++) {
            _a[x] = this.getAt(x);
        }
    }

    /**
     *
     * This guy will replace an existing object with the passed object
     * If currently in the OPICMList.. else it performs a simple put
     * @param _o
     * @author Dave
     * @concurrency $none
     */
    public synchronized void replace(OPICMObject _o) {
        if (super.remove(_o.hashkey()) == null) {
            put(_o);

        } else {
            super.put(_o.hashkey(), _o);
        }
    }

    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: OPICMList.java,v 1.15 2005/03/01 00:48:47 dave Exp $";
    }

    /**
     * Clones this OPICMList and everything in it.  You must be extremely carefull here
     *  to ensure that items inside this OPICMList are not self contained.. otherwise
     *  you will be cloning alot of excess baggage and you may get unpredictable results
     *
     * @return cloned OPICMList
     * @author Dave
     * @param _b
     */
    public OPICMList clone(boolean _b) {
        OPICMList clone = null;

        byte[] byteArray = null;
        ByteArrayOutputStream BAout = null;
        ByteArrayInputStream BAin = null;
        ObjectOutputStream Oout = null;
        ObjectInputStream Oin = null;

        //Serialization approach...
        try {
            super.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            //put object into stream
            BAout = new ByteArrayOutputStream();
            Oout = new ObjectOutputStream(BAout);
            Oout.writeObject(this);
            Oout.flush();
            Oout.reset();
            Oout.close();
            byteArray = BAout.toByteArray();
            BAout.close();

            //now turn around and pull object out of stream
            BAin = new ByteArrayInputStream(byteArray);
            Oin = new ObjectInputStream(BAin);
            clone = (OPICMList) Oin.readObject();
            Oin.close();
            BAin.close();
            byteArray = null;
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return clone;
    }

}
