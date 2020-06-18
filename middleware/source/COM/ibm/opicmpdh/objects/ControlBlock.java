//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ControlBlock.java,v $
// Revision 1.21  2008/01/31 21:42:00  wendy
// Cleanup RSA warnings
//
// Revision 1.20  2005/01/27 04:02:36  dave
// removing automated readObject from Jtest
//
// Revision 1.19  2005/01/27 02:42:20  dave
// Jtest format cleanup
//
// Revision 1.18  2004/10/04 21:19:33  gregg
// isOn
//
// Revision 1.17  2001/09/19 15:32:32  roger
// Formatting
//
// Revision 1.16  2001/09/19 15:24:58  roger
// Remove constructors with Profile as parm
//
// Revision 1.15  2001/09/17 22:28:13  roger
// Undo more
//
// Revision 1.14  2001/09/17 22:17:48  roger
// Remove protected from constructors
//
// Revision 1.13  2001/09/17 22:07:32  roger
// Needed import
//
// Revision 1.12  2001/09/17 22:05:20  roger
// Put OPENID back
//
// Revision 1.11  2001/09/17 22:00:00  roger
// Open up constructors and members
//
// Revision 1.10  2001/09/17 21:48:34  roger
// New accessors + mutators
//
// Revision 1.9  2001/09/17 20:24:27  roger
// Use Profile for values of Enterprise, OPENID, TranID, etc
//
// Revision 1.8  2001/08/22 16:53:06  roger
// Removed author RM
//
// Revision 1.7  2001/08/14 19:22:27  roger
// Expose TRANID in constructor
//
// Revision 1.6  2001/03/26 16:46:03  roger
// Misc formatting clean up
//
// Revision 1.5  2001/03/21 00:01:11  roger
// Implement java class file branding in getVersion method
//
// Revision 1.4  2001/03/16 15:52:27  roger
// Added Log keyword
//


package COM.ibm.opicmpdh.objects;


import java.io.Serializable;

/**
 * ControlBlock
 * @version @date
 * @see Attribute
 * @see Blob
 * @see EntitiesAndRelator
 * @see Entity
 * @see Flag
 * @see LongText
 * @see MetaEntity
 * @see MultipleFlag
 * @see Relator
 * @see SingleFlag
 * @see Text
 */
public final class ControlBlock implements Serializable, Cloneable {

    // Class constants

    /**
     * @serial
     */
    static final long serialVersionUID = 10L;

    // Class variables
    // Instance variables
    /**
     * FIELD
     */
    public String m_strValFrom = null;
    /**
     * FIELD
     */
    public String m_strValTo = null;
    /**
     * FIELD
     */
    public String m_strEffFrom = null;
    /**
     * FIELD
     */
    public String m_strEffTo = null;
    /**
     * FIELD
     */
    public int m_iOPENID = 0;
    /**
     * FIELD
     */
    public int m_iOPWGID = 0;
    /**
     * FIELD
     */
    public int m_iTRANID = 0;


    /**
     * Main method which performs a simple test of this class
     *
     * @param args
     */
    public static void main(String args[]) {
    }

    //  /**
    //   * Construct a <code>ControlBlock</code>
    //   */
    //  public ControlBlock(Profile _prof, String strValFrom, String strValTo, String strEffFrom, String strEffTo) {
    //    m_strValFrom = strValFrom;
    //    m_strValTo = strValTo;
    //    m_strEffFrom = strEffFrom;
    //    m_strEffTo = strEffTo;
    //    m_iOPENID = _prof.getOPWGID();
    //    m_iTRANID = _prof.getTranID();
    //  }
    //

    /**
     * Construct a default <code>ControlBlock</code>
     */
    public ControlBlock() {
        this("", "", "", "", 0, 1);
    }

    /**
     * Construct a <code>ControlBlock</code>
     *
     * @param strValFrom
     * @param strValTo
     * @param strEffFrom
     * @param strEffTo
     * @param openID
     */
    public ControlBlock(String strValFrom, String strValTo, String strEffFrom, String strEffTo, int openID) {
        this(strValFrom, strValTo, strEffFrom, strEffTo, openID, 1);
    }

    /**
     * Construct a <code>ControlBlock</code>
     *
     * @param strValFrom
     * @param strValTo
     * @param strEffFrom
     * @param strEffTo
     * @param openID
     * @param tranID
     */
    public ControlBlock(String strValFrom, String strValTo, String strEffFrom, String strEffTo, int openID, int tranID) {
        m_strValFrom = strValFrom;
        m_strValTo = strValTo;
        m_strEffFrom = strEffFrom;
        m_strEffTo = strEffTo;
        m_iOPENID = openID;
        m_iTRANID = tranID;
    }

    /**
     * getValFrom
     * @return String
     */
    public String getValFrom() {
        return m_strValFrom;
    }

    /**
     * setValFrom
     *
     * @param _strValFrom
     */
    public void setValFrom(String _strValFrom) {
        this.m_strValFrom = _strValFrom;
    }

    /**
     * getValTo
     * @return String
     */
    public String getValTo() {
        return m_strValTo;
    }

    /**
     * setValTo
     *
     * @param _strValTo
     */
    public void setValTo(String _strValTo) {
        this.m_strValTo = _strValTo;
    }

    /**
     * getEffFrom
     * @return String
     */
    public String getEffFrom() {
        return m_strEffFrom;
    }

    /**
     * setEffFrom
     *
     * @param _strEffFrom
     */
    public void setEffFrom(String _strEffFrom) {
        this.m_strEffFrom = _strEffFrom;
    }

    /**
     * getEffTo
     * @return String
     */
    public String getEffTo() {
        return m_strEffTo;
    }

    /**
     * setEffTo
     *
     * @param _strEffTo
     */
    public void setEffTo(String _strEffTo) {
        this.m_strEffTo = _strEffTo;
    }

    /**
     * getOPENID
     * @return int
     */
    public int getOPENID() {
        return m_iOPENID;
    }

    /**
     * setOPENID
     *
     * @param _iOPENID
     */
    public void setOPENID(int _iOPENID) {
        this.m_iOPENID = _iOPENID;
    }

    /**
     * getOPWGID
     * @return int
     */
    public int getOPWGID() {
        return m_iOPWGID;
    }

    /**
     * setOPWGID
     *
     * @param _iOPWGID
     */
    public void setOPWGID(int _iOPWGID) {
        this.m_iOPWGID = _iOPWGID;
    }

    /**
     * getTranID
     * @return int
     */
    public int getTranID() {
        return m_iTRANID;
    }

    /**
     * setTranID
     *
     * @param _iTRANID
     */
    public void setTranID(int _iTRANID) {
        this.m_iTRANID = _iTRANID;
    }

    /**
     * isOn
     * @return
     * @author Dave
     */
    public boolean isOn() {
        return (!getEffFrom().equals(getEffTo()));
    }

    /**
     * The <code>ControlBlock</code> as a String
     * @return <code>ControlBlock</code> definition as a <code>String</code>
     */
    public final String toString() {
        return " ValFrom:" + m_strValFrom + " ValTo:" + m_strValTo + " EffFrom:" + m_strEffFrom + " EffTo:" + m_strEffTo + " OPENID:" + m_iOPENID + " TRANID: " + m_iTRANID;
    }

    ///**
    // * Returns a clone of the object
    // */
    //  public Object clone() {
    //    Object objClone = null;
    //
    //    try {
    //      objClone = super.clone();
    //    } catch (CloneNotSupportedException x) {}
    //    ;
    //
    //    return objClone;
    //  }
    //

    /**
     * Return the date/time this class was generated
     * @return the date/time this class was generated
     */
    public final String getVersion() {
        return "$Id: ControlBlock.java,v 1.21 2008/01/31 21:42:00 wendy Exp $";
    }
}
