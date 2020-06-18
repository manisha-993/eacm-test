//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: GeneratePartNumberItem.java,v $
// Revision 1.11  2008/01/31 21:29:04  wendy
// Cleanup RSA warnings
//
// Revision 1.10  2005/03/11 22:42:54  dave
// removing some auto genned stuff
//
// Revision 1.9  2005/01/27 04:02:36  dave
// removing automated readObject from Jtest
//
// Revision 1.8  2005/01/26 20:45:36  dave
// Jtest cleanup effort
//
// Revision 1.7  2001/10/30 21:55:01  dave
// type mistake
//
// Revision 1.6  2001/10/30 21:46:48  dave
// trace statements
//
// Revision 1.5  2001/08/22 16:53:10  roger
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
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.T;
import java.io.Serializable;
import java.util.Vector;


/**
 * This is a container for the data required to request a part number assignment for an entity.
 * After creating a <code>Vector</code> of items of this type, and then placing that object
 * into a <code>GeneratePartNumberGroup</code> object, one would use the <code>
 * generatePartNumbers</code>, <code>applyPartNumbers</code>, or <code>validatePartNumbers</code>
 * method of the <code>Database</code> or
 * <code>RemoteDatabase</code> object to either generate or apply the part numbers.
 * @version @date
 * @see GeneratePartNumberGroup
 */

public class GeneratePartNumberItem
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
    public String m_strEntityType = null;
    /**
     * TBD
     */
    public String m_strEntityTypeSubset = null;
    /**
     * TBD
     */
    public int m_iEntityID = 0;
    /**
     * TBD
     */
    public String m_strAttributeCode = null;
    /**
     * TBD
     */
    public String m_strRegionCode = null;
    /**
     * TBD
     */
    public String m_strCountryCode = null;
    /**
     * TBD
     */
    public String m_strRangeLowBound = null;
    /**
     * TBD
     */
    public String m_strRangeHighBound = null;
    /**
     * TBD
     */
    public String m_strCurrentPartNumber = null; // current value in DB
    /**
     * TBD
     */
    public String m_strRequestedPartNumber = null;
    /**
     * TBD
     */
    public String m_strValidatedPartNumber = null;
    /**
     * TBD
     */
    public boolean m_bNoAction = false;
    /**
     * TBD
     */
    public boolean m_bDeactivatePartNumber = false;
    /**
     * TBD
     */
    public boolean m_bUpdatePartNumber = false;
    /**
     * TBDE
     */
    public int m_iParentIndex = 0;
    /**
     * TBD
     */
    public Vector m_vctChildIndexes = null;
    /**
     * TBD
     */
    public String m_strStatusMessage = null;


    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /**
     * Creates a stored procedure <code>GeneratePartNumberItem</code> object
     *
     * @param _strEntityType
     * @param _iEntityID
     * @param _strAttributeCode
     * @param _strCountryCode
     * @param _strRegionCode
     * @param _strRangeLowBound
     * @param _strRangeHighBound
     * @param _strCurrentPartNumber
     * @param _strRequestedPartNumber
     * @param _strValidatedPartNumber
     * @param _bNoAction
     * @param _bDeactivatePartNumber
     * @param _bUpdatePartNumber
     * @param _iParentIndex
     * @param _vctChildIndexes
     */
    public GeneratePartNumberItem(
        String _strEntityType,
        int _iEntityID,
        String _strAttributeCode,
        String _strCountryCode,
        String _strRegionCode,
        String _strRangeLowBound,
        String _strRangeHighBound,
        String _strCurrentPartNumber,
        String _strRequestedPartNumber,
        String _strValidatedPartNumber,
        boolean _bNoAction,
        boolean _bDeactivatePartNumber,
        boolean _bUpdatePartNumber,
        int _iParentIndex,
        Vector _vctChildIndexes) {
        super();
        // Set from parms
        if (_bNoAction) {
            _bNoAction = true;
        }
        this.m_strEntityType = _strEntityType;
        this.m_iEntityID = _iEntityID;
        this.m_strAttributeCode = _strAttributeCode;
        this.m_strRegionCode = _strRegionCode;
        this.m_strCountryCode = _strCountryCode;
        this.m_strRangeLowBound = _strRangeLowBound;
        this.m_strRangeHighBound = _strRangeHighBound;
        this.m_strCurrentPartNumber = _strCurrentPartNumber;
        this.m_strRequestedPartNumber = _strRequestedPartNumber;
        this.m_strValidatedPartNumber = _strValidatedPartNumber;
        this.m_bDeactivatePartNumber = _bDeactivatePartNumber;
        this.m_bUpdatePartNumber = _bUpdatePartNumber;
        this.m_iParentIndex = _iParentIndex;
        this.m_vctChildIndexes = _vctChildIndexes;
        // Set default internal values
        this.m_strStatusMessage = "";
    }

    /**
     * Display the object and show values
     *
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public void display() throws MiddlewareRequestException {
        T.est(
            this.m_strEntityType != null,
            "GeneratePartNumberItem:m_strEntityType is null");
        D.ebug(
            "GeneratePartNumberItem:m_strEntityType " + this.m_strEntityType);
        T.est(this.m_iEntityID >= 0, "GeneratePartNumberItem:m_iEntityID <= 0");
        D.ebug("GeneratePartNumberItem:m_iEntityID " + this.m_iEntityID);
        T.est(
            this.m_strAttributeCode != null,
            "GeneratePartNumberItem:m_strAttributeCode is null");
        D.ebug(
            "GeneratePartNumberItem:m_strAttributeCode "
                + this.m_strAttributeCode);
        T.est(
            this.m_strRegionCode != null,
            "GeneratePartNumberItem:m_strRegionCode is null");
        D.ebug(
            "GeneratePartNumberItem:m_strRegionCode " + this.m_strRegionCode);
        T.est(
            this.m_strCountryCode != null,
            "GeneratePartNumberItem:m_strCountryCode is null");
        D.ebug(
            "GeneratePartNumberItem:m_strCountryCode " + this.m_strCountryCode);
        T.est(
            this.m_strRangeLowBound != null,
            "GeneratePartNumberItem:m_strRangeLowBound is null");
        D.ebug(
            "GeneratePartNumberItem:m_strRangeLowBound "
                + this.m_strRangeLowBound);
        T.est(
            this.m_strRangeHighBound != null,
            "GeneratePartNumberItem:m_strRangeHighBound is null");
        D.ebug(
            "GeneratePartNumberItem:m_strRangeHighBound "
                + this.m_strRangeHighBound);
        T.est(
            this.m_strCurrentPartNumber != null,
            "GeneratePartNumberItem:m_strCurrentPartNumber is null");
        D.ebug(
            "GeneratePartNumberItem:m_strCurrentPartNumber "
                + this.m_strCurrentPartNumber);
        T.est(
            this.m_strRequestedPartNumber != null,
            "GeneratePartNumberItem:m_strRequestedPartNumber is null");
        D.ebug(
            "GeneratePartNumberItem:m_strRequestedPartNumber "
                + this.m_strRequestedPartNumber);
        T.est(
            this.m_strValidatedPartNumber != null,
            "GeneratePartNumberItem:m_strValidatedPartNumber is null");
        D.ebug(
            "GeneratePartNumberItem:m_strValidatedPartNumber "
                + this.m_strValidatedPartNumber);
        D.ebug("GeneratePartNumberItem:m_bNoAction " + this.m_bNoAction);
        D.ebug(
            "GeneratePartNumberItem:m_bDeactivatePartNumber "
                + this.m_bDeactivatePartNumber);
        D.ebug(
            "GeneratePartNumberItem:m_bUpdatePartNumber "
                + this.m_bUpdatePartNumber);
        D.ebug("GeneratePartNumberItem:m_iParentIndex " + this.m_iParentIndex);
        if (this.m_vctChildIndexes != null) {
            D.ebug(
                "GeneratePartNumberItem:m_vctChildIndexes "
                    + this.m_vctChildIndexes);
        }
        T.est(
            this.m_strStatusMessage != null,
            "GeneratePartNumberItem:m_strStatusMessage is null");
        D.ebug(
            "GeneratePartNumberItem:m_strStatusMessage "
                + this.m_strStatusMessage);
    }

    /**
     * Return the date/time this class was generated
     * @return the date/time this class was generated
     */
    public String getVersion() {
        return "$Id: GeneratePartNumberItem.java,v 1.11 2008/01/31 21:29:04 wendy Exp $";
    }
}
