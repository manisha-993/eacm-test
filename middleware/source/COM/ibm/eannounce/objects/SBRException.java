//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: SBRException.java,v $
// Revision 1.4  2005/03/11 21:54:59  dave
// more jtest cleanup
//

package COM.ibm.eannounce.objects;
import java.util.Vector;

/**
 * SBRException
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SBRException extends Exception {
    private Vector m_failureVec = null;
    private boolean m_bResetPDGCollectInfo = false;

    /**
    * Constructs a <code>SBRException</code> with no specified detail message
    */
    public SBRException() {
        this("no detail message");
        m_failureVec = new Vector();
    }

    /**
     * Constructs a <code>SBRException</code> with the specified detail message
     *
     * @param s 
     */
    public SBRException(String s) {
        super(s);
        m_failureVec = new Vector();
    }

    /*
    * Adds an Attribute and Message to the failure list
    	*/
    /**
     * add
     *
     * @param _s
     *  @author David Bigelow
     */
    public void add(String _s) {
        m_failureVec.addElement(_s);
    }

    /*
    * Returns the String representation of all errors in this exception
    * @return the String representation of all errors in this exception
    */
    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String strAnswer = "# " + m_failureVec.size() + "\n";
        for (int x = 0; x < m_failureVec.size(); x++) {
            strAnswer = strAnswer + ":" + (String) m_failureVec.elementAt(x) + "\n";
        }
        return strAnswer;
    }

    /*
    * Returns the String representation of all errors in this exception
    * @return the String representation of all errors in this exception
    */
    /**
     * getMessageAsArray
     *
     * @return
     *  @author David Bigelow
     */
    public String[] getMessageAsArray() {
        int size = m_failureVec.size();

        String[] aReturn = new String[size];
        for (int x = 0; x < size; x++) {
            aReturn[x] = (String) m_failureVec.elementAt(x);
        }
        return aReturn;
    }

    /*
    * Number of failures in the exception
    */
    /**
     * getErrorCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getErrorCount() {
        return m_failureVec.size();
    }

    /**
     * clearLists
     *
     *  @author David Bigelow
     */
    protected void clearLists() {
        m_failureVec = new Vector();
    }

    /**
     * setResetPDGCollectInfo
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setResetPDGCollectInfo(boolean _b) {
        m_bResetPDGCollectInfo = _b;
    }

    /**
     * resetPDGCollectInfo
     *
     * @return
     *  @author David Bigelow
     */
    public boolean resetPDGCollectInfo() {
        return m_bResetPDGCollectInfo;
    }

    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: SBRException.java,v 1.4 2005/03/11 21:54:59 dave Exp $";
    }

}
