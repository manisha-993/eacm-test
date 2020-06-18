//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANAddressable.java,v $
// Revision 1.42  2005/02/14 17:18:34  dave
// more jtest fixing
//
// Revision 1.41  2004/10/21 16:49:53  dave
// trying to share compartor
//
// Revision 1.40  2004/06/18 17:24:06  joan
// work on edit relator
//
// Revision 1.39  2003/08/18 21:05:07  dave
// Adding  the sequencing chain to the islocked to not
// induced each cell for being locked in the islocked of
// entityItem (kludge)
//
// Revision 1.38  2003/05/13 19:07:44  dave
// return type fix
//
// Revision 1.37  2003/05/13 19:01:54  dave
// added getAL function in EANAddressable.. so I hope I can get
// it working for this sort
//
// Revision 1.36  2002/11/19 23:26:55  joan
// fix hasLock method
//
// Revision 1.35  2002/11/19 18:27:41  joan
// adjust lock, unlock
//
// Revision 1.34  2002/11/19 00:06:26  joan
// adjust isLocked method
//
// Revision 1.33  2002/10/07 17:41:37  joan
// add getLockGroup method
//
// Revision 1.32  2002/09/20 16:21:52  dave
// classification first pass for the structure
//
// Revision 1.31  2002/08/08 20:51:48  joan
// fix setParentEntityItem
//
// Revision 1.30  2002/08/08 20:07:26  joan
// fix setParentEntityItem
//
// Revision 1.29  2002/05/20 21:31:11  joan
// add setParentEntityItem
//
// Revision 1.28  2002/05/14 17:47:06  joan
// working on LockActionItem
//
// Revision 1.27  2002/05/13 20:48:36  joan
// compile error
//
// Revision 1.26  2002/05/13 20:40:33  joan
// add resetLockGroup method
//
// Revision 1.25  2002/05/10 22:04:55  joan
// add hasLock method
//
// Revision 1.24  2002/05/10 20:45:54  joan
// fixing lock
//
// Revision 1.23  2002/04/22 22:18:23  joan
// working on unlock
//
// Revision 1.22  2002/04/22 18:08:38  joan
// add unlock method
//
// Revision 1.21  2002/04/19 22:34:05  joan
// change isLocked interface to include profile as parameter
//
// Revision 1.20  2002/04/19 17:42:08  joan
// fixing compile errors
//
// Revision 1.19  2002/04/19 17:34:22  joan
// fixing compile errors
//
// Revision 1.18  2002/04/19 17:17:02  joan
// change isLocked  interface
//
// Revision 1.17  2002/04/19 00:25:01  joan
// working on isLocked
//
// Revision 1.16  2002/04/18 23:21:20  dave
// basic sketch for lock in RowSelectableTable
//
// Revision 1.15  2002/04/12 22:44:18  joan
// throws exception
//
// Revision 1.14  2002/04/12 16:42:04  dave
// added isLocked to the tableDef
//
// Revision 1.13  2002/03/21 18:38:56  dave
// added getHelp to the EANTableModel
//
// Revision 1.12  2002/03/21 00:22:56  dave
// adding rollback logic to the rowSelectable table
//
// Revision 1.11  2002/03/20 18:33:55  dave
// expanding the get for the EANAddressable to
// include a verbose boolean to dictate what gets sent back
//
// Revision 1.10  2002/03/12 18:33:08  dave
// clean up on EANAddressable - removed the int indexes
// because they make no sense.
// Added standard put /get methods to the EANAttibute
//
// Revision 1.9  2002/03/11 22:35:46  dave
// more syntax errors
//
// Revision 1.8  2002/03/11 20:56:15  dave
// mass changes for beginnings of edit
//
// Revision 1.7  2002/02/15 22:24:34  dave
// added row methods
//
// Revision 1.6  2002/02/15 19:16:35  dave
// added getKey
//
// Revision 1.5  2002/02/15 18:06:52  dave
// expanded EAN Table structures
//
// Revision 1.4  2001/08/09 23:16:08  dave
// changed EANAddressable interface to use String keys
// as opposed to integers
//
// Revision 1.3  2001/08/09 18:59:13  dave
// modifications regarding getColumnClass
//
// Revision 1.2  2001/08/09 18:30:03  dave
// syntax
//
// Revision 1.1  2001/08/09 18:05:31  dave
// new classes to support table interface
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;

/**
* Enforced a Standard way to get and set attributes of
* an Eannounce Object
*/
public interface EANAddressable {

    /**
     * FIELD
     */
    String CLASS_BRAND = "$Id: EANAddressable.java,v 1.42 2005/02/14 17:18:34 dave Exp $";

    // This means that its attributes can be retrieved by String Key;
    // We removed the index options because they mean nothing in getting back to the info
    // Only string keys will work
    /**
     * getEANObject
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    EANFoundation getEANObject(String _str);
    /**
     * get
     *
     * @param _s
     * @param _b
     * @return
     *  @author David Bigelow
     */
    Object get(String _s, boolean _b);
    /**
     * put
     *
     * @param _s
     * @param _o
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     * @return
     *  @author David Bigelow
     */
    boolean put(String _s, Object _o) throws EANBusinessRuleException;
    /**
     * isEditable
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    boolean isEditable(String _s);
    /**
     * isLocked
     *
     * @param _s
     * @param _rdi
     * @param _db
     * @param _ll
     * @param _prof
     * @param _lockOwnerEI
     * @param _iLockType
     * @param _strTime
     * @param _bCreateLock
     * @return
     *  @author David Bigelow
     */
    boolean isLocked(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType, String _strTime, boolean _bCreateLock);
    /**
     * getLockGroup
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    LockGroup getLockGroup(String _s);

    /**
     * hasLock
     *
     * @param _str
     * @param _lockOwnerEI
     * @param _prof
     * @return
     *  @author David Bigelow
     */
    boolean hasLock(String _str, EntityItem _lockOwnerEI, Profile _prof);
    /**
     * rollback
     *
     * @param _str
     *  @author David Bigelow
     */
    void rollback(String _str);
    /**
     * getHelp
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    String getHelp(String _str);

    /**
     * toString
     *
     * @return
     *  @author David Bigelow
     */
    String toString();
    /**
     * getKey
     *
     * @return
     *  @author David Bigelow
     */
    String getKey();

    /**
     * unlock
     *
     * @param _s
     * @param _rdi
     * @param _db
     * @param _ll
     * @param _prof
     * @param _lockOwnerEI
     * @param _iLockType
     *  @author David Bigelow
     */
    void unlock(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType);
    /**
     * resetLockGroup
     *
     * @param _s
     * @param _ll
     *  @author David Bigelow
     */
    void resetLockGroup(String _s, LockList _ll);
    /**
     * setParentEntityItem
     *
     * @param _ei
     *  @author David Bigelow
     */
    void setParentEntityItem(EntityItem _ei);
    /**
     * getALMarker
     *
     * @return
     *  @author David Bigelow
     */
    String getALMarker();
    /**
     * isParentAttribute
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    boolean isParentAttribute(String _str);
    /**
     * isChildAttribute
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    boolean isChildAttribute(String _str);

}
