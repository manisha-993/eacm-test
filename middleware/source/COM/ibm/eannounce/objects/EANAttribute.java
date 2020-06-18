//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANAttribute.java,v $
// Revision 1.109  2011/10/05 00:13:39  wendy
// add check for specific attribute change
//
// Revision 1.108  2009/05/11 15:26:36  wendy
// Support dereference for memory release
//
// Revision 1.107  2005/02/28 19:43:52  dave
// more Jtest fixes from over the weekend
//
// Revision 1.106  2005/02/16 18:06:42  joan
// go back to 1.104
//
// Revision 1.105  2005/02/14 17:18:34  dave
// more jtest fixing
//
// Revision 1.104  2004/10/26 01:30:51  dave
// undo last change
//
// Revision 1.103  2004/10/26 01:09:41  dave
// lets try this again
//
// Revision 1.102  2004/10/26 00:56:16  dave
// put back direct reference
//
// Revision 1.101  2004/10/26 00:34:23  dave
// some cleanup
//
// Revision 1.100  2004/10/26 00:30:09  dave
// lets try meta attribute by reference
//
// Revision 1.99  2004/10/22 19:39:55  dave
// Calling a double shot of string
//
// Revision 1.98  2004/10/22 19:22:48  dave
// adding stack trace back in
//
// Revision 1.97  2004/10/22 17:12:09  dave
// removing inouts and tmp fix for update
//
// Revision 1.96  2004/10/22 00:15:21  dave
// dumpStack
//
// Revision 1.95  2004/10/21 23:43:09  dave
// trace fix
//
// Revision 1.94  2004/10/21 23:37:25  dave
// trace and no sort if arry is one
//
// Revision 1.93  2004/10/21 22:51:49  dave
// syntax
//
// Revision 1.92  2004/10/21 22:45:55  dave
// attempting to speed up rendering by removing the need to
// create a new String buffer
//
// Revision 1.91  2004/10/21 16:49:53  dave
// trying to share compartor
//
// Revision 1.90  2004/10/20 17:15:24  dave
// trying to save space here
//
// Revision 1.89  2004/08/12 18:02:41  joan
// CR3616
//
// Revision 1.88  2004/06/18 20:28:38  joan
// work on edit relator
//
// Revision 1.87  2004/06/18 17:24:06  joan
// work on edit relator
//
// Revision 1.86  2004/06/17 19:50:37  joan
// add logic so the attribute is editable if it is required and null
//
// Revision 1.85  2004/04/20 19:52:53  joan
// work on duplicate
//
// Revision 1.84  2004/04/09 19:37:18  joan
// add duplicate method
//
// Revision 1.83  2004/03/03 01:30:48  gregg
// store valfrom
//
// Revision 1.82  2004/01/12 23:20:27  dave
// more memory squeezing
//
// Revision 1.81  2003/09/09 22:57:48  dave
// more Profile Crimping
//
// Revision 1.80  2003/08/18 21:05:07  dave
// Adding  the sequencing chain to the islocked to not
// induced each cell for being locked in the islocked of
// entityItem (kludge)
//
// Revision 1.79  2003/06/26 23:52:32  dave
// adding the abstract stuff
//
// Revision 1.78  2003/06/02 19:20:18  gregg
// sync w/ 1.1.1
//
// Revision 1.77  2003/04/24 18:32:15  dave
// getting rid of traces and system out printlns
//
// Revision 1.76  2003/04/24 01:13:05  dave
// isEditable trace cleanup
//
// Revision 1.75  2003/04/24 00:54:11  dave
// traces and selective recalculation on changing a flag value
//
// Revision 1.74  2003/04/24 00:21:03  dave
// syntax fix
//
// Revision 1.73  2003/04/24 00:14:54  dave
// update fixes
//
// Revision 1.72  2003/04/23 21:21:09  dave
// adding all fields back to grid
//
// Revision 1.71  2002/11/19 23:26:55  joan
// fix hasLock method
//
// Revision 1.70  2002/11/19 18:27:42  joan
// adjust lock, unlock
//
// Revision 1.69  2002/11/19 00:06:26  joan
// adjust isLocked method
//
// Revision 1.68  2002/10/30 23:18:47  dave
// syntax and more throwing
//
// Revision 1.67  2002/10/14 21:30:01  dave
// syntax fixes
//
// Revision 1.66  2002/10/14 21:04:10  dave
// introducing transient parent pointers so when
// we clone.. we do not have a case where the object
// that is being cloned loses its parent momentarily
//
// Revision 1.65  2002/10/09 20:38:35  dave
// syntax fix
//
// Revision 1.64  2002/10/09 20:26:36  dave
// making sure we can edit things in an EntityGroup when used
// as part of a SearchActionItem
//
// Revision 1.63  2002/10/07 17:41:37  joan
// add getLockGroup method
//
// Revision 1.62  2002/09/26 19:17:15  dave
// disabling noedit on classification fields
//
// Revision 1.61  2002/09/24 21:13:04  dave
// allow editing of Classified attributes if in create mode
//
// Revision 1.60  2002/09/19 18:25:30  dave
// if MetaAttribute isClassified.. you cannot edit.. unless overt things happen
//
// Revision 1.59  2002/09/09 19:46:03  dave
// tracing for isEditable
//
// Revision 1.58  2002/09/06 20:02:01  dave
// fixing isEditable for EntityGroup,Item, Attribute
//
// Revision 1.57  2002/08/15 16:02:12  dave
// exception handling cleanup
//
// Revision 1.56  2002/08/08 20:51:49  joan
// fix setParentEntityItem
//
// Revision 1.55  2002/08/08 20:07:26  joan
// fix setParentEntityItem
//
// Revision 1.54  2002/05/20 21:31:11  joan
// add setParentEntityItem
//
// Revision 1.53  2002/05/14 18:07:43  joan
// working on LockActionItem
//
// Revision 1.52  2002/05/14 17:47:06  joan
// working on LockActionItem
//
// Revision 1.51  2002/05/13 20:40:33  joan
// add resetLockGroup method
//
// Revision 1.50  2002/05/10 22:04:55  joan
// add hasLock method
//
// Revision 1.49  2002/05/10 21:06:20  joan
// compiling errors
//
// Revision 1.48  2002/05/10 20:45:54  joan
// fixing lock
//
// Revision 1.47  2002/04/25 00:00:52  joan
// working on getRowHeader
//
// Revision 1.46  2002/04/24 22:57:18  joan
// debug getRowHeader
//
// Revision 1.45  2002/04/22 22:18:24  joan
// working on unlock
//
// Revision 1.44  2002/04/22 18:20:12  joan
// fixing compile errors
//
// Revision 1.43  2002/04/22 18:08:38  joan
// add unlock method
//
// Revision 1.42  2002/04/19 22:34:06  joan
// change isLocked interface to include profile as parameter
//
// Revision 1.41  2002/04/19 17:57:23  joan
// fixing compiling error
//
// Revision 1.40  2002/04/19 17:17:02  joan
// change isLocked  interface
//
// Revision 1.39  2002/04/12 22:44:18  joan
// throws exception
//
// Revision 1.38  2002/04/12 18:12:33  joan
// work on exception
//
// Revision 1.37  2002/04/12 16:42:04  dave
// added isLocked to the tableDef
//
// Revision 1.36  2002/04/10 20:44:14  dave
// more closes on io objects
//
// Revision 1.35  2002/04/10 20:26:41  dave
// fix to import statement
//
// Revision 1.34  2002/04/10 20:06:12  dave
// Syntax error fix
//
// Revision 1.33  2002/04/10 19:50:19  dave
// Added a cloneStructure routine to the EANMetaAttribute
//
// Revision 1.32  2002/04/09 00:46:47  dave
// fixing up isEditable to include looking at read/write language
//
// Revision 1.31  2002/04/08 23:19:23  dave
// syntax and introduction of isLanguageUpdatable()
//
// Revision 1.30  2002/04/02 19:12:37  dave
// more isRequiredStuff
//
// Revision 1.29  2002/04/02 19:00:01  dave
// first pass at required field changes
//
// Revision 1.28  2002/04/02 01:12:08  dave
// first stab at restriction
//
// Revision 1.27  2002/03/25 17:19:01  dave
// added control block when generating entity update
//
// Revision 1.26  2002/03/23 01:17:59  dave
// syntax and import fixes
//
// Revision 1.25  2002/03/23 01:08:25  dave
// first attempt at update
//
// Revision 1.24  2002/03/21 18:38:56  dave
// added getHelp to the EANTableModel
//
// Revision 1.23  2002/03/21 00:30:21  dave
// fixes to syntax on rollback function
//
// Revision 1.22  2002/03/21 00:22:56  dave
// adding rollback logic to the rowSelectable table
//
// Revision 1.21  2002/03/20 23:03:53  dave
// made sure the MetaAttribute was returned for getEANObject
// in the Description column for the EANTableModel interface
//
// Revision 1.20  2002/03/20 21:21:11  dave
// syntax fixes, and rollback on the attribute
//
// Revision 1.19  2002/03/20 21:07:37  dave
// starting to place stack information in the EntityItem
//
// Revision 1.18  2002/03/20 19:18:40  dave
// syntax and constructor clean up on strFlagKey
//
// Revision 1.17  2002/03/20 18:45:15  dave
// import fix
//
// Revision 1.16  2002/03/20 18:33:55  dave
// expanding the get for the EANAddressable to
// include a verbose boolean to dictate what gets sent back
//
// Revision 1.15  2002/03/20 00:17:13  dave
// fix for EntityItem RowSelectable table for Column Headers
// and Column 0 display
//
// Revision 1.14  2002/03/19 23:45:17  dave
// more work on vertical table
//
// Revision 1.13  2002/03/19 03:47:33  dave
// first attempt a setting defaults
//
// Revision 1.12  2002/03/19 00:39:07  dave
// more syntax fixes
//
// Revision 1.11  2002/03/19 00:10:49  dave
// first attempt at vertical table stuff
//
// Revision 1.10  2002/03/13 23:07:07  dave
// fix for EANFlagAttribute
//
// Revision 1.9  2002/03/12 19:02:41  dave
// fix on abstract
//
// Revision 1.8  2002/03/12 18:42:31  dave
// abstract fixes
//
// Revision 1.7  2002/03/12 18:33:08  dave
// clean up on EANAddressable - removed the int indexes
// because they make no sense.
// Added standard put /get methods to the EANAttibute
//
// Revision 1.6  2002/02/12 21:01:58  dave
// added toString methods for diplay help
//
// Revision 1.5  2002/02/11 07:43:34  dave
// more fixes
//
// Revision 1.4  2002/02/11 07:35:08  dave
// adding data side
//
// Revision 1.3  2002/02/11 07:23:08  dave
// new objects to commit
//
// Revision 1.2  2002/02/05 16:39:13  dave
// more expansion of abstract model
//
// Revision 1.1  2002/02/04 18:02:46  dave
// added new Abstract EANAttribute
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * EANAttribute
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class EANAttribute extends EANDataFoundation implements EANAddressable {

    // Instance variables
    /**
    *@serial
    */
    final static long serialVersionUID = 1L;
    private static String c_strEffFrom = "1980-01-01-00.00.00.000000";
    private static String c_strEffTo = "9999-12-31-00.00.00.000000";

    private EANMetaAttribute m_ma = null;
    private transient EANMetaAttribute mt_ma = null;
    private transient String m_strValFrom = null;

    protected void dereference(){
    	 m_ma = null; 
    	 mt_ma = null;
    	 m_strValFrom = null;
    	 super.dereference();
    }
    /**
    *  Description of the Field
    */
    public final static String DESCRIPTION = "0";
    /**
    *  Description of the Field
    */
    public final static String VALUE = "1";

 
    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public abstract String dump(boolean _bBrief);
    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public abstract String toString();
    /**
     * refreshDefaults
     *
     *  @author David Bigelow
     */
    public abstract void refreshDefaults();
    /**
     * get
     *
     * @return
     *  @author David Bigelow
     */
    public abstract Object get();
    /**
     * hasData
     *
     * @return
     *  @author David Bigelow
     */
    protected abstract boolean hasData();

    // needs to throw a business exeception

    /**
     * put
     *
     * @param _o
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     *  @author David Bigelow
     */
    public abstract void put(Object _o) throws EANBusinessRuleException;

    /**
     * rollback
     *
     *  @author David Bigelow
     */
    public abstract void rollback();

    // Upate Stuff
    /**
    *  Description of the Method
    *
    *@return    Description of the Return Value
    */
    protected abstract boolean checkBusinessRules();

    /**
    *  Description of the Method
    *
    *@return    Description of the Return Value
    */
    protected abstract Vector generateUpdateAttribute();

    /**
    *  Description of the Method
    */
    protected abstract void commitLocal();

    /**
    *  Gets the eANObject attribute of the EANAttribute object
    *
    *@param  _str  Description of the Parameter
    *@return       The eANObject value
    */
    public EANFoundation getEANObject(String _str) {
        if (_str.equals(DESCRIPTION)) {
            EANMetaAttribute ma = getMetaAttribute();
            if (ma != null) {
                return ma;
            }
        }
        return this;
    }

    // This get cannot generate defaulf values
    // So we just do a to String

    /**
    *  Description of the Method
    *
    *@param  _s  Description of the Parameter
    *@param  _b  Description of the Parameter
    *@return     Description of the Return Value
    */
    public Object get(String _s, boolean _b) {

        EANMetaAttribute ma = getMetaAttribute();
        if (_s.equals(DESCRIPTION)) {
            if (ma != null) {
                return ma.toString();
            }
        }

        // If boolean is false. we string tokenize the answer here.
        // and only return the first row
        if (_b) {
            return toString();
        } else {

            // only do this trick if multi or long
            if (ma.isMulti() || ma.isLongText()) {
                StringTokenizer st = new StringTokenizer(toString() + "\n ", "\n");
                return st.nextToken();
            } else {
                return toString();
            }

        }
    }

    /**
    *  Gets the help attribute of the EANAttribute object
    *
    *@param  _s  Description of the Parameter
    *@return     The help value
    */
    public String getHelp(String _s) {

        EANMetaAttribute ma = getMetaAttribute();
        if (ma != null) {
            return ma.getHelpValueText();
        } else {
            return "No Help Is Available for this Attribute.";
        }
    }

    /**
    *  Description of the Method
    *
    *@param  _s                            Description of the Parameter
    *@param  _o                            Description of the Parameter
    *@return                               Description of the Return Value
    *@exception  EANBusinessRuleException  Description of the Exception
    */
    public boolean put(String _s, Object _o) throws EANBusinessRuleException {
        put(_o);
        return true;
    }

    /**
    *  Gets the editable attribute of the EANAttribute object
    *
    *@param  _s  Description of the Parameter
    *@return     The editable value
    */
    public boolean isEditable(String _s) {

        if (_s.equals(DESCRIPTION)) {
            return false;
        }

        return isEditable();
    }

    /**
     *  Gets the locked attribute of the EANAttribute object
     *
     *@param  _s            Description of the Parameter
     *@param  _rdi          Description of the Parameter
     *@param  _db           Description of the Parameter
     *@param  _ll           Description of the Parameter
     *@param  _prof         Description of the Parameter
     *@param  _lockOwnerEI  Description of the Parameter
     *@param  _iLockType    Description of the Parameter
     *@param  _bCreateLock  Description of the Parameter
     *@return               The locked value
     * @param _strTime 
     */
    public boolean isLocked(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType, String _strTime, boolean _bCreateLock) {

        if (_s.equals(DESCRIPTION)) {
            return false;
        }

        return isLocked(_rdi, _db, _ll, _prof, _lockOwnerEI, _iLockType, _strTime, _bCreateLock);
    }

    /**
     *  Gets the locked attribute of the EANAttribute object
     *
     *@param  _rdi          Description of the Parameter
     *@param  _db           Description of the Parameter
     *@param  _ll           Description of the Parameter
     *@param  _prof         Description of the Parameter
     *@param  _lockOwnerEI  Description of the Parameter
     *@param  _iLockType    Description of the Parameter
     *@param  _bCreateLock  Description of the Parameter
     *@return               The locked value
     * @param _strTime 
     */
    public boolean isLocked(RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType, String _strTime, boolean _bCreateLock) {
        EntityItem ei = (EntityItem) getParent();
        if (ei == null) {
            return false;
        }
        return ei.isLocked(_rdi, _db, _ll, _prof, _lockOwnerEI, _iLockType, _strTime, _bCreateLock);
    }

    /**
    *  Gets the lockGroup attribute of the EANAttribute object
    *
    *@param  _s  Description of the Parameter
    *@return     The lockGroup value
    */
    public LockGroup getLockGroup(String _s) {

        EntityItem ei = null;
        if (_s.equals(DESCRIPTION)) {
            return null;
        }

        ei = (EntityItem) getParent();
        if (ei == null) {
            return null;
        }
        return ei.getLockGroup();
    }

    /**
    *  Description of the Method
    *
    *@param  _str          Description of the Parameter
    *@param  _lockOwnerEI  Description of the Parameter
    *@param  _prof         Description of the Parameter
    *@return               Description of the Return Value
    */
    public boolean hasLock(String _str, EntityItem _lockOwnerEI, Profile _prof) {
        EntityItem ei = null;
        if (_str.equals(DESCRIPTION)) {
            return false;
        }

        ei = (EntityItem) getParent();
        if (ei == null) {
            return false;
        }
        return ei.hasLock(_lockOwnerEI, _prof);
    }
    
    /**
     * does this attribute have changes that were put on the entityitem stack?
     * @return
     */
    public boolean hasChanges(){
        EntityItem ei = (EntityItem) getParent();
        if (ei == null) {
            return false;
        }
        return ei.hasAttributeChange(this);
    }

    /**
    *  Gets the editable attribute of the EANAttribute object
    *
    *@return    The editable value
    */
    public boolean isEditable() {

        // Must be wondering about the value..

        EANMetaAttribute ma = getMetaAttribute();
        EntityItem ei = (EntityItem)getParent();
        EntityGroup eg = null;

        if (ma == null) {
            return false;
        }

        if (ei == null) {
            return false;
        }

        // Make sure we can edit the whole structure first
        if (!ei.canEdit()) {
            return false;
        }

        // OK .. are we spawned from a SearchActionItem?
        if (ei.isUsedInSearch()) {
            return true;
        }

        // Is it restricted
        if (ei.isRestricted(ma)) {
            return false;
        }

        eg = ei.getEntityGroup();
        // Is it currently out of classification?
        if (eg != null && eg.isClassified()) {
            if (!ma.isClassified() && !ei.isClassified(ma)) {
                return false;
            }
        }

        // ok.. if we are a new record.. and the attribute is classified
        // we can edit it no matter what!
        // in create mode .. these fields must be editable in order to save the entity
        if (ei.isNew() && ma.isClassified()) {
            return true;
        }

        //CR3616 if required and null, it should be editable
        /*comments out for now
        String strValue = toString();
        if (isRequired() && (strValue == null || strValue.length() <= 0)) {
        return true;
        }
        */
        // OK.. we rely on the meta definition...
        return ma.isEditable();
    }

    /*
    *  Is this attribute required in the context of the data
    */
    /**
    *  Gets the required attribute of the EANAttribute object
    *
    *@return    The required value
    */
    public boolean isRequired() {

        EANMetaAttribute ma = getMetaAttribute();
        EntityItem ei = (EntityItem)getParent();

        if (ma == null) {
            return false;
        }

        if (ei == null) {
            return false;
        }
        return ei.isRequired(ma);
    }

    /*
    *  This rollback an attribute for the given string index into its structure
    */
    /**
    *  Description of the Method
    *
    *@param  _str  Description of the Parameter
    */
    public void rollback(String _str) {
        if (_str.equals(DESCRIPTION)) {
        } else {
            rollback();
        }
    }

    /**
    *  Gets the version attribute of the EANAttribute object
    *
    *@return    The version value
    */
    public String getVersion() {
        return "$Id: EANAttribute.java,v 1.109 2011/10/05 00:13:39 wendy Exp $";
    }

    /**
    *  Main method which performs a simple test of this class
    *
    *@param  arg  Description of the Parameter
    */
    public static void main(String arg[]) {

    }

    /**
    *  Creates the PDHMetaAttribute with the Default NLSID and Value
    *
    *@param  _edf                            Description of the Parameter
    *@param  _prof                           Description of the Parameter
    *@param  _ma                             Description of the Parameter
    *@exception  MiddlewareRequestException  Description of the Exception
    */
    public EANAttribute(EANDataFoundation _edf, Profile _prof, EANMetaAttribute _ma) throws MiddlewareRequestException {
        super(_edf, _prof, _ma.getAttributeCode());
        setMetaAttribute(_ma);
    }

    /**
     *  Sets the metaAttribute attribute of the EANAttribute object
     *
     *@param  _ma  The new metaAttribute value
     * @concurrency $none
     */
    protected synchronized void setMetaAttribute(EANMetaAttribute _ma) {
        m_ma = _ma;
    }

    /**
     *  Sets the transientMetaAttribute attribute of the EANAttribute object
     *
     * @concurrency $none
     */
    protected synchronized void setTransientMetaAttribute() {
        mt_ma = m_ma;
    }

    /**
     *  Description of the Method
     *
     * @concurrency $none
     */
    protected synchronized void resetTransientMetaAttribute() {
        mt_ma = null;
    }

    /**
     *  Gets the metaAttribute attribute of the EANAttribute object
     *
     *@return    The metaAttribute value
     * @concurrency $none
     */
    public synchronized EANMetaAttribute getMetaAttribute() {
        // lets try the old lookup trick.
        //EntityItem ei = getEntityItem();
        //if (ei == null) return null;

        //EntityGroup eg = ei.getEntityGroup();
        //if (eg == null) return null;

        //return  eg.getMetaAttribute(getAttributeCode());
        if (m_ma == null) {
            return mt_ma;
        }
        return m_ma;
    }

    /**
    *  Gets the attributeCode attribute of the EANAttribute object
    *
    *@return    The attributeCode value
    */
    public String getAttributeCode() {
        return getKey();
    }

    /**
    *  Gets the effFrom attribute of the EANAttribute object
    *
    *@return    The effFrom value
    */
    public String getEffFrom() {
        return c_strEffFrom;
    }

    /**
    *  Gets the effTo attribute of the EANAttribute object
    *
    *@return    The effTo value
    */
    public String getEffTo() {
        return c_strEffTo;
    }

    /**
     * getValFrom
     *
     * @return
     *  @author David Bigelow
     */
    public String getValFrom() {
        return m_strValFrom;
    }

    /**
     * setValFrom
     *
     * @param _s
     *  @author David Bigelow
     */
    public void setValFrom(String _s) {
        m_strValFrom = _s;
    }

    /**
    *  Description of the Method
    *
    *@return    Description of the Return Value
    */
    protected EANAttribute cloneStructure() {

        //Serialization approach...

        EANAttribute clone = null;
        Profile prof = getProfile();
        EANFoundation ef = getParent();
        EANMetaAttribute ma = getMetaAttribute();
        byte[] byteArray = null;
        ByteArrayOutputStream BAout = null;
        ByteArrayInputStream BAin = null;
        ObjectOutputStream Oout = null;
        ObjectInputStream Oin = null;

        // First close all the possible serialization leaks

        setTransientParent();
        setParent(null);
        setTransientMetaAttribute();
        setMetaAttribute(null);
        setProfile(null);

        try {
            //put object into stream
            BAout = new ByteArrayOutputStream();
            Oout = new ObjectOutputStream(BAout);
            Oout.writeObject(this);
            Oout.flush();
            Oout.close();
            byteArray = BAout.toByteArray();
            BAout.close();

            //now turn around and pull object out of stream
            BAin = new ByteArrayInputStream(byteArray);
            Oin = new ObjectInputStream(BAin);
            clone = (EANAttribute) Oin.readObject();
            Oin.close();
            BAin.close();

            byteArray = null;
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {

            // Now .. put it back
            setParent(ef);
            resetTransientParent();
            setMetaAttribute(ma);
            resetTransientMetaAttribute();
            setProfile(prof);
        }

        return clone;
    }

    /*
    *  Nothing to unlock
    */
    /**
    *  Description of the Method
    *
    *@param  _s            Description of the Parameter
    *@param  _rdi          Description of the Parameter
    *@param  _db           Description of the Parameter
    *@param  _ll           Description of the Parameter
    *@param  _prof         Description of the Parameter
    *@param  _lockOwnerEI  Description of the Parameter
    *@param  _iLockType    Description of the Parameter
    */
    public void unlock(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType) {
        if (_s.equals(DESCRIPTION)) {
            return;
        }
        unlock(_rdi, _db, _ll, _prof, _lockOwnerEI, _iLockType);
    }

    /**
    *  Description of the Method
    *
    *@param  _rdi          Description of the Parameter
    *@param  _db           Description of the Parameter
    *@param  _ll           Description of the Parameter
    *@param  _prof         Description of the Parameter
    *@param  _lockOwnerEI  Description of the Parameter
    *@param  _iLockType    Description of the Parameter
    */
    public void unlock(RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType) {
        EntityItem ei = (EntityItem) getParent();
        if (ei != null) {
            ei.unlock(_rdi, _db, _ll, _prof, _lockOwnerEI, _iLockType);
        }
    }

    /**
    *  Description of the Method
    *
    *@param  _s   Description of the Parameter
    *@param  _ll  Description of the Parameter
    */
    public void resetLockGroup(String _s, LockList _ll) {
        if (_s.equals(DESCRIPTION)) {
            return;
        }
        resetLockGroup(_ll);
    }

    /**
    *  Description of the Method
    *
    *@param  _ll  Description of the Parameter
    */
    public void resetLockGroup(LockList _ll) {
        EntityItem ei = (EntityItem) getParent();
        if (ei != null) {
            ei.resetLockGroup(_ll);
        }
    }

    /**
    *  Sets the parentEntityItem attribute of the EANAttribute object
    *
    *@param  _ei  The new parentEntityItem value
    */
    public void setParentEntityItem(EntityItem _ei) {
        EntityItem ei = (EntityItem) getParent();
        if (ei != null) {
            ei.setParentEntityItem(_ei);
        }
    }

    /**
    *  Gets the longDescription attribute of the EANAttribute object
    *
    *@return    The longDescription value
    */
    public String getLongDescription() {
        if (getMetaAttribute() == null) {
            return super.getLongDescription();
        }
        return getMetaAttribute().getLongDescription();
    }

    /**
    *  Gets the entityItem attribute of the EANAttribute object
    *
    *@return    The entityItem value
    */
    public EntityItem getEntityItem() {
        return (EntityItem) getParent();
    }

    /**
    *  Get the AttirbuteChangeHistoryGroup Object for this EANAttribute.
    *
    *@param  _db                             Description of the Parameter
    *@return                                 The changeHistoryGroup value
    *@exception  SQLException                Description of the Exception
    *@exception  MiddlewareRequestException  Description of the Exception
    *@exception  MiddlewareException         Description of the Exception
    */
    public AttributeChangeHistoryGroup getChangeHistoryGroup(Database _db) throws SQLException, MiddlewareRequestException, MiddlewareException {
        return _db.getAttributeChangeHistoryGroup(getProfile(), this);
    }

    /**
    *  Get the AttributeChangeHistoryGroup Object for this EANAttribute.
    *
    *@param  _rdi                                       Description of the
    *      Parameter
    *@return                                            The changeHistoryGroup
    *      value
    *@exception  RemoteException                        Description of the
    *      Exception
    *@exception  MiddlewareRequestException             Description of the
    *      Exception
    *@exception  MiddlewareException                    Description of the
    *      Exception
    *@exception  MiddlewareShutdownInProgressException  Description of the
    *      Exception
    */
    public AttributeChangeHistoryGroup getChangeHistoryGroup(RemoteDatabaseInterface _rdi) throws RemoteException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException {
        return _rdi.getAttributeChangeHistoryGroup(getProfile(), this);
    }

    /**
     * duplicate
     *
     * @param _o
     *  @author David Bigelow
     */
    protected abstract void duplicate(Object _o);

    /**
     * (non-Javadoc)
     * isParentAttribute
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isParentAttribute(java.lang.String)
     */
    public boolean isParentAttribute(String _str) {
        EntityItem ei = getEntityItem();
        return ei.isParentAttribute(ei.getEntityType() + ":" + getKey());
    }
    /**
     * (non-Javadoc)
     * isChildAttribute
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isChildAttribute(java.lang.String)
     */
    public boolean isChildAttribute(String _str) {
        EntityItem ei = getEntityItem();
        return ei.isChildAttribute(ei.getEntityType() + ":" + getKey());
    }

}
