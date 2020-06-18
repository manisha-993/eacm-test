//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: GeneratePartNumberGroup.java,v $
// Revision 1.9  2008/01/31 21:29:04  wendy
// Cleanup RSA warnings
//
// Revision 1.8  2005/03/11 22:42:54  dave
// removing some auto genned stuff
//
// Revision 1.7  2005/01/27 04:02:36  dave
// removing automated readObject from Jtest
//
// Revision 1.6  2005/01/26 20:45:36  dave
// Jtest cleanup effort
//
// Revision 1.5  2001/08/22 16:53:09  roger
// Removed author RM
//
// Revision 1.4  2001/03/21 00:01:12  roger
// Implement java class file branding in getVersion method
//
// Revision 1.3  2001/03/16 16:08:57  roger
// Added Log keyword, and standard copyright
//


package COM.ibm.opicmpdh.transactions;


import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.T;
import java.io.Serializable;
import java.util.Vector;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;


/**
 * This is a container for the data required to request a part number assignment for an entity.
 * A <code>Vector</code> of items of type <code>GeneratePartNumberItem</code> should be passed
 * to the constructor along with ~any~ remaining parameters.  Then, one can use the
 * <code>generatePartNumbers</code>, <code>applyPartNumbers</code>, or <code>validatePartNumbers</code>
 * method of the <code>Database</code> or <code>RemoteDatabase</code> object.
 * @version @date
 * @see GeneratePartNumberGroup
 */

public class GeneratePartNumberGroup
    extends Object
    implements Serializable, Cloneable {

    // Instance variables
    /**
     * @serial
     */
    static final long serialVersionUID = 1L;
    /**
     * TBD
     */
    public int m_iReturnResult = 0;
    /**
     * TBD
     */
    public String m_strStatusMessage = null;
    /**
     * TBD
     */
    public Vector m_vctPNI = null;



    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /**
     * Creates a stored procedure <code>GeneratePartNumberGroup</code> object
     */
    public GeneratePartNumberGroup() {
        super();
        // Set default internal values
        this.m_iReturnResult = 0;
        this.m_strStatusMessage = "";
        this.m_vctPNI = null;
    }

    /**
     * Creates a stored procedure <code>GeneratePartNumberGroup</code> object
     *
     * @param _vctPNI
     */
    public GeneratePartNumberGroup(Vector _vctPNI) {
        super();
        // Set default internal values
        this.m_iReturnResult = 0;
        this.m_strStatusMessage = "";
        // Set from parms
        this.m_vctPNI = _vctPNI;
    }

    /**
     * Display the object and show values
     *
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public void display() throws MiddlewareRequestException {
        D.ebug(
            D.EBUG_DETAIL,
            "GeneratePartNumberGroup:m_iReturnResult " + this.m_iReturnResult);
        T.est(
            this.m_strStatusMessage != null,
            "GeneratePartNumberGroup:m_strStatusMessage is null");
        D.ebug(
            D.EBUG_DETAIL,
            "GeneratePartNumberGroup:m_strStatusMessage "
                + this.m_strStatusMessage);
        T.est(
            this.m_vctPNI != null,
            "GeneratePartNumberGroup:m_vctPNI is null");
        if (this.m_vctPNI != null) {
            GeneratePartNumberItem gpni = null;
            for (int i = 0; i < this.m_vctPNI.size(); i++) {
                if (this.m_vctPNI.elementAt(i)
                    instanceof GeneratePartNumberItem) {
                    D.ebug("Item: " + i + " of " + this.m_vctPNI.size());
                    gpni = (GeneratePartNumberItem) this.m_vctPNI.elementAt(i);
                    gpni.display();
                }
            }
        }
    }

    /**
     * Return the date/time this class was generated
     * @return the date/time this class was generated
     */
    public String getVersion() {
        return "$Id: GeneratePartNumberGroup.java,v 1.9 2008/01/31 21:29:04 wendy Exp $";
    }
}
