//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANList.java,v $
// Revision 1.27  2008/02/01 22:10:06  wendy
// Cleanup RSA warnings
//
// Revision 1.26  2006/02/20 21:50:04  joan
// clean up System.out.println
//
// Revision 1.25  2006/02/20 21:39:45  joan
// clean up System.out.println
//
// Revision 1.24  2005/02/14 18:33:35  dave
// exception never thrown
//
// Revision 1.23  2005/02/14 17:57:47  dave
// more Jtest
//
// Revision 1.22  2004/10/28 22:10:12  dave
// whoops - bad int(eger)
//
// Revision 1.21  2004/10/28 22:05:19  dave
// playing with capacity increament in EANList
//
// Revision 1.20  2004/10/21 16:49:53  dave
// trying to share compartor
//
// Revision 1.19  2003/09/24 17:37:01  gregg
// private getObjectAt(int) method - used in remove(int)
//
// Revision 1.18  2003/09/24 17:27:54  gregg
// adjust remove(int) method to check for EANObject type before class-casting.
//
// Revision 1.17  2003/02/20 22:44:17  gregg
// copyList method
//
// Revision 1.16  2002/11/12 17:18:27  dave
// System.out.println clean up
//
// Revision 1.15  2002/08/15 21:36:52  dave
// fix to EANList
//
// Revision 1.14  2002/08/15 21:13:40  dave
// misc changes to put
//
// Revision 1.13  2002/06/05 17:18:02  gregg
// removed putAt method
//
// Revision 1.12  2002/06/05 16:38:35  gregg
// putAt method added for indexed insert
//
// Revision 1.11  2002/04/19 22:06:18  dave
// making sense of swapkey = resetKey
//
// Revision 1.10  2002/04/10 21:06:56  dave
// massive .close() effort on classes and methods
//
// Revision 1.9  2002/02/15 00:00:31  dave
// added entitygroup tracker and fixed a boudry  text in EANList
//
// Revision 1.8  2002/02/05 16:39:13  dave
// more expansion of abstract model
//
// Revision 1.7  2001/12/12 17:24:45  dave
// tweeks to sync 1.0 and 1.1 for all the maintenance mods
//
// Revision 1.6  2001/08/22 16:52:50  roger
// Removed author RM
//
// Revision 1.5  2001/08/08 17:52:19  dave
// syntax fixes
//
// Revision 1.4  2001/08/08 17:41:45  dave
// more generalization is prep for table model on Search API
//
// Revision 1.3  2001/08/02 17:30:28  dave
// syntax
//
// Revision 1.2  2001/08/02 17:27:00  dave
// Syntax Fixes
//
// Revision 1.1  2001/08/02 16:55:01  dave
// converted structures for use in the search API
//
//

package COM.ibm.eannounce.objects;

import java.util.Hashtable;
import java.util.Vector;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
//import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import COM.ibm.opicmpdh.middleware.D;

/**
* This manages a vector, hashtable combination to provide an indexed way
* to find OPICM data in the PDH and OPICM  Object structures.  The objects this
* class maintains must implement OPICMObject.
* @author  David Bigelow
* @version @date
*/
public class EANList extends Hashtable implements Serializable, Cloneable {

    // Instance variables
    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * FIELD
     */
    protected Vector m_vct = null; // Vector'

    private static final int CAP_INCREMENT = 50;
    private static final int INIT_SIZE = 10;


    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /**
     * Creates the EANList with an initial size
     *
     * @param _i1
     */
    public EANList(int _i1) {
        super(_i1);
        m_vct = new Vector(_i1, CAP_INCREMENT);
    }

    /**
    * Creates the EANList with no initial size
    */
    public EANList() {
        super();
        m_vct = new Vector(INIT_SIZE, CAP_INCREMENT);
    }

    /**
     * adds an OPICMObject to this list.  It only Places things
     * in the list that are not Currently in the List
     *
     * @param _o1
     * @concurrency $none
     */
    public synchronized void put(EANObject _o1) {
        if (super.put(_o1.getKey(), _o1) == null) {
            m_vct.addElement(_o1.getKey());
        }
    }

    /**
     * put
     *
     * @param _s1
     * @param _o1
     *  @author David Bigelow
     * @concurrency $none
     */
    public synchronized void put(String _s1, Object _o1) {
        if (super.put(_s1, _o1) == null) {
            m_vct.addElement(_s1);
        }
    }

    /**
     * get
     *
     * @param _s1
     * @return
     *  @author David Bigelow
     * @concurrency $none
     */
    public synchronized Object get(String _s1) {
        return super.get(_s1);
    }

    /**
     * Returns an Object at a specific index
     *
     * @return the Object at the specific index
     * @param _i1
     * @concurrency $none
     */
    public synchronized EANObject getAt(int _i1) {
        if (_i1 < super.size() && _i1 >= 0) {
            return (EANObject) super.get(m_vct.elementAt(_i1));
        }
        return null;
    }

    /**
    * Returns an Object at a specific index
    * @return the Object at the specific index
    */
    private synchronized Object getObjectAt(int _i1) {
        if (_i1 < super.size() && _i1 >= 0) {
            return super.get(m_vct.elementAt(_i1));
        }
        return null;
    }

    /**
     * Returns an indexOf the object in the hashtable
     *
     * @return the index of Object in the EANList
     * @param _o1
     */
    public int indexOf(EANObject _o1) {
        return m_vct.indexOf(_o1.getKey());
    }

    /**
     * Returns an indexOf the object in the hashtable represented by the hashkey
     *
     * @return the index of Object in the EANList represented by the hashkey
     * @param _s1
     */
    public int indexOf(String _s1) {
        return m_vct.indexOf(_s1);
    }

    /**
     * Removes the element at the index
     *
     * @param _i
     * @return EANObject
     * @concurrency $none
     */
    public synchronized EANObject remove(int _i) {
        // GAB - 092403 - the Object in question could possibly be a non-EANObject (e.g. String). In which case return null.
        Object obj = getObjectAt(_i);
        EANObject eObj = null;
        if (obj instanceof EANObject) {
            eObj = (EANObject) obj;
        }
        super.remove(m_vct.elementAt(_i));
        m_vct.remove(_i);
        return eObj;
    }

    /*
    * Removes all the elements in this list
    */
    /**
     * removeAll
     *
     *  @author David Bigelow
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
     * @param _o
     * @return EANObject
     * @concurrency $none
     */
    public synchronized EANObject remove(EANObject _o) {
        int i = m_vct.indexOf(_o.getKey());
        if (i != -1) {
            return remove(i);
        }
        return null;
    }

    /**
     * Removes the element from the list
     *
     * @param _str
     * @return EANObject
     * @concurrency $none
     */
    public synchronized EANObject remove(String _str) {
        int i = m_vct.indexOf(_str);
        if (i != -1) {
            return remove(i);
        }
        return null;
    }

    /*
    * This will retrieve the object indexed at the old hashkey
    * and insert a new hashkey to represent this object
    * very tricky
    * @param strOldHashKey  represents the old hashkey
    * @param strNewHashKey  represent the neew hashkey to use
    */
    /**
     * resetKey
     *
     * @param _strOldKey
     *  @author David Bigelow
     * @concurrency $none
     */
    protected synchronized void resetKey(String _strOldKey) {
        int i = m_vct.indexOf(_strOldKey);
        EANObject obj = (EANObject) get(_strOldKey);
        if (i >= 0) {
            m_vct.removeElementAt(i);
            super.remove(_strOldKey);
            m_vct.insertElementAt(obj.getKey(), i);
            super.put(obj.getKey(), obj);
        } else {
            D.ebug(D.EBUG_SPEW,"EANList.resetKey().Key Not Found.. Skipping the reset:" + _strOldKey);
        }
    }

    /*
    * Copies the EANList into an array.  The array must be large enough
    * to hold all the values of this EANList
    *@param a Object array to receive the contents of the list
    */
    /**
     * copyTo
     *
     * @param _a
     *  @author David Bigelow
     */
    public void copyTo(EANObject[] _a) {
        for (int x = 0; x < size(); x++) {
            _a[x] = getAt(x);
        }
    }

    /*
    * This guy will replace an existing object with the passed object
    * If currently in the EANList.. else it performs a simple put
    * @param o OPICMObject that needs to be replaced.
    *
    * Note:  This is not the same as swapping hashkeys.. hashkeys are
    * assumed to remain the same here..
    */
    /**
     * replace
     *
     * @param _o
     *  @author David Bigelow
     * @concurrency $none
     */
    public synchronized void replace(EANObject _o) {
        if (m_vct.indexOf(_o.getKey()) >= 0) {
            super.put(_o.getKey(), _o);

        } else {
            put(_o);
        }
    }

    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: EANList.java,v 1.27 2008/02/01 22:10:06 wendy Exp $";
    }

    /* Clones this EANList and everything in it.  You must be extremely carefull here
    *  to ensure that items inside this EANList are not self contained.. otherwise
    *  you will be cloning alot of excess baggage and you may get unpredictable results
    *
    * @param b Deep = true, Shallow = false
    * @return cloned EANList
    */
    /**
     * clone
     *
     * @param _b
     * @return
     *  @author David Bigelow
     */
    public EANList clone(boolean _b) {

        EANList clone = null;
        byte[] byteArray = null;
        ByteArrayOutputStream BAout = null;
        ByteArrayInputStream BAin = null;
        ObjectOutputStream Oout = null;
        ObjectInputStream Oin = null;

        try {

            //put object into stream
            BAout = new ByteArrayOutputStream();
            Oout = new ObjectOutputStream(BAout);
            try {
                Oout.writeObject(this);
                Oout.flush();
                Oout.reset();
                byteArray = BAout.toByteArray();
            } finally {
                Oout.close();
                BAout.close();
            }
            //now turn around and pull object out of stream
            BAin = new ByteArrayInputStream(byteArray);
            Oin = new ObjectInputStream(BAin);
            try {
                clone = (EANList) Oin.readObject();
            } finally {
                Oin.close();
                BAin.close();
            }
            byteArray = null;
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return clone;
    }

    /**
    * Return a NEW EANList containing the SAME EANObjects which were contained in the original list.
    * NOTE: this is NOT a clone - internal objects' references remain the same as the originals.
    * @param _eList the list to copy
    * @return a new EANList containing the original objects
    */
    public EANList copyList(EANList _eList) {
        EANList eListCopy = new EANList();
        for (int i = 0; i < _eList.size(); i++) {
            eListCopy.put(_eList.getAt(i));
        }
        return eListCopy;
    }

}
