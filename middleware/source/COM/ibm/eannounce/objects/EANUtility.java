//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANUtility.java,v $
// Revision 1.127  2013/03/18 20:26:45  wendy
// Create EntityGroup once and reuse it
//
// Revision 1.126  2010/11/08 20:25:26  wendy
// check for null string in object reuse
//
// Revision 1.125  2010/11/08 18:42:44  wendy
// Reuse string objects to reduce memory requirements
//
// Revision 1.124  2010/01/18 16:28:50  wendy
// allow ',' as delimiter in flag values, added with ur to query, added cmts, chg delete msg
//
// Revision 1.123  2009/12/31 13:38:23  wendy
// Expand info in error msg when can not delete
//
// Revision 1.122  2009/12/29 21:31:08  wendy
// Improve error msg when can not delete
//
// Revision 1.121  2009/12/05 19:12:11  wendy
// SR11, SR15 and SR17 restrict delete of entity based on attr value
//
// Revision 1.120  2009/11/03 18:54:26  wendy
// SR11, SR15 and SR17 restrict create of relator
//
// Revision 1.119  2009/05/15 17:52:21  wendy
// Change return type for checkVELockOwners()
//
// Revision 1.118  2009/05/14 17:27:11  wendy
// Check for locks for a set of entityitems - perf improvement
//
// Revision 1.117  2008/06/18 17:49:38  wendy
// Check for null before rs.close()
//
// Revision 1.116  2008/02/01 22:10:08  wendy
// Cleanup RSA warnings
//
// Revision 1.115  2008/01/11 17:13:56  wendy
// Removed hardcoded check for WGMODEL, meta is now cleaned up
//
// Revision 1.114  2007/08/22 19:34:20  wendy
// MN32841099 prevent using obsolete WGMODEL
//
// Revision 1.113  2006/02/20 21:39:46  joan
// clean up System.out.println
//
// Revision 1.112  2006/01/13 00:17:23  joan
// fixes
//
// Revision 1.111  2006/01/12 23:14:18  joan
// work on CR0817052746
//
// Revision 1.110  2005/09/08 18:12:25  joan
// add testing rule for meta maintenance
//
// Revision 1.109  2005/08/22 18:13:46  joan
// fixes
//
// Revision 1.108  2005/05/10 23:25:38  joan
// fix for Delete/Entity/Relator
//
// Revision 1.107  2005/04/26 15:49:56  joan
// fixes
//
// Revision 1.106  2005/04/15 17:25:27  gregg
// add (ok) to VE locked dialogue
//
// Revision 1.105  2005/04/07 22:32:49  gregg
// throw our exception in link
//
// Revision 1.104  2005/03/28 22:02:14  joan
// input prof NLSID for sp
//
// Revision 1.103  2005/03/25 21:10:49  dave
// more fixes
//
// Revision 1.102  2005/03/11 20:41:15  roger
// Foreign ABRs
//
// Revision 1.101  2005/03/11 17:42:20  tony
// remove getLocalVersion call
//
// Revision 1.100  2005/03/11 16:57:40  tony
// added getLocalVersion
//
// Revision 1.99  2005/02/28 23:36:23  dave
// small syntax
//
// Revision 1.98  2005/02/28 23:31:01  dave
// more Jtest
//
// Revision 1.97  2005/01/18 21:33:10  dave
// removing the debug parms from code (sp internalized them)
//
// Revision 1.96  2004/10/21 16:49:54  dave
// trying to share compartor
//
// Revision 1.95  2004/06/11 21:08:52  joan
// check for entities' existence in relator table before deleting them
//
// Revision 1.94  2004/05/20 16:51:24  joan
// fix linkAll
//
// Revision 1.93  2004/04/14 21:53:14  joan
// initial load
//
// Revision 1.92  2004/03/12 23:19:12  joan
// changes from 1.2
//
// Revision 1.91  2003/12/11 20:13:42  joan
// work on link method
//
// Revision 1.90  2003/12/10 21:07:03  joan
// adjust for ExcludeCopy
//
// Revision 1.89  2003/09/26 20:46:27  joan
// add queuedABR method
//
// Revision 1.88  2003/09/19 22:35:32  dave
// unraveling matrix lock commit, etc
//
// Revision 1.87  2003/09/19 21:42:28  dave
// O.K. lets see what we broken for this one
//
// Revision 1.86  2003/09/11 19:36:46  joan
// fb52150
//
// Revision 1.85  2003/06/06 00:04:17  joan
// move changes from v111
//
// Revision 1.84  2003/05/19 17:49:44  dave
// new Migration SP
//
// Revision 1.83  2003/04/29 17:05:47  dave
// clean up and removal of uneeded function
//
// Revision 1.82  2003/04/17 19:24:39  dave
// need OPENID in sp
//
// Revision 1.81  2003/04/17 19:08:29  dave
// Syntax
//
// Revision 1.80  2003/04/17 18:41:34  dave
// syntax fixes
//
// Revision 1.79  2003/04/17 17:56:14  dave
// clean up link,deactivate, tagging
//
// Revision 1.78  2003/04/14 16:43:10  dave
// speed and cleanup
//
// Revision 1.77  2003/03/17 18:28:13  dave
// adding variable declaration
//
// Revision 1.76  2003/03/17 17:46:54  dave
// Tagging I, Relator Table
//
// Revision 1.75  2003/03/17 17:25:01  dave
// Tagging Phase II - entity prep
//
// Revision 1.74  2003/01/20 23:20:17  joan
// fix compiling
//
// Revision 1.73  2003/01/20 23:00:44  joan
// add methods to get VELockOwner and the list for VELock/Entity/Relator
//
// Revision 1.72  2003/01/14 22:05:07  joan
// adjust removeLink method
//
// Revision 1.71  2003/01/14 21:14:17  joan
// fix bug
//
// Revision 1.70  2003/01/14 19:38:12  joan
// debug
//
// Revision 1.69  2003/01/14 01:29:53  joan
// debug
//
// Revision 1.68  2003/01/14 01:06:47  joan
// fix compile
//
// Revision 1.67  2003/01/14 00:45:31  joan
// add checkOrphan
//
// Revision 1.66  2002/11/26 22:07:32  joan
// add deactivateEntity method
//
// Revision 1.65  2002/10/28 19:15:51  joan
// remove System.out
//
// Revision 1.64  2002/10/28 18:47:42  joan
// fix error
//
// Revision 1.63  2002/10/28 16:00:11  joan
// add more work on get country GA
//
// Revision 1.62  2002/10/26 00:35:32  joan
// add more work
//
// Revision 1.61  2002/10/25 23:11:04  joan
// fix compile error
//
// Revision 1.60  2002/10/25 23:00:41  joan
// fix compile
//
// Revision 1.59  2002/10/25 22:47:05  joan
// add getListOfCountriesForAVAIL method
//
// Revision 1.58  2002/10/25 22:07:11  joan
// working on getCountryList
//
// Revision 1.57  2002/10/17 16:33:09  joan
// fix to return all relators with relatortype, entity1type, entity1id
//
// Revision 1.56  2002/10/03 21:49:11  dave
// Found it!
//
// Revision 1.55  2002/10/02 22:56:37  dave
// Need entityList of cart to hook it up properly
//
// Revision 1.54  2002/10/02 22:00:18  dave
// tracking on cloneEntityItem
//
// Revision 1.53  2002/09/10 21:35:28  dave
// fixes for canEdit and canCreate
//
// Revision 1.52  2002/08/14 22:55:33  dave
// clean up in business rule checks
//
// Revision 1.51  2002/08/08 17:07:30  dave
// syntax
//
// Revision 1.50  2002/08/08 16:46:51  dave
// trace statements
//
// Revision 1.49  2002/08/07 23:40:55  dave
// extra throwing
//
// Revision 1.48  2002/08/07 23:31:34  dave
// syntax fix
//
// Revision 1.47  2002/08/07 23:10:52  dave
// first pass at new Clone EntityItem array stuff
//
// Revision 1.46  2002/06/07 22:30:29  joan
// working on business rule
//
// Revision 1.45  2002/06/06 21:32:50  joan
// fixing errors
//
// Revision 1.44  2002/06/06 21:15:22  joan
// fix compiling errors
//
// Revision 1.43  2002/06/06 20:54:02  joan
// working on link
//
// Revision 1.42  2002/05/30 21:48:40  joan
// fix cloneEntityItem
//
// Revision 1.41  2002/05/30 20:58:45  joan
// fix cloneEntityItem
//
// Revision 1.40  2002/05/30 17:34:36  joan
// fixing cloneEntityItem
//
// Revision 1.39  2002/05/20 16:07:36  joan
// working on addRow
//
// Revision 1.38  2002/05/03 22:40:39  joan
// fix throwing exception
//
// Revision 1.37  2002/05/03 22:29:33  joan
// fix throw exception
//
// Revision 1.36  2002/04/17 18:39:52  dave
// whoop.. needed to get the set call for the statetransition into
// the put for the EAN flag Attribute
//
// Revision 1.35  2002/04/17 18:08:37  dave
// put the set call in the EANUtlity
//
// Revision 1.34  2002/04/17 17:54:09  dave
// changes to test set login on state transition
//
// Revision 1.33  2002/04/15 23:03:02  joan
// remove EAN prefix
//
// Revision 1.32  2002/04/15 18:35:28  joan
// work on exception
//
// Revision 1.31  2002/04/15 16:55:04  joan
// syntax
//
// Revision 1.30  2002/04/11 20:46:19  joan
// syntax
//
// Revision 1.29  2002/04/11 16:27:30  joan
// fixing exception
//
// Revision 1.28  2002/04/10 23:46:47  joan
// fixing error
//
// Revision 1.27  2002/04/10 23:35:50  joan
// working on exception
//
// Revision 1.26  2002/04/10 20:15:56  joan
// syntax
//
// Revision 1.25  2002/04/10 20:06:14  joan
// syntax
//
// Revision 1.24  2002/04/10 19:47:07  joan
// working on exception
//
// Revision 1.23  2002/04/10 17:52:10  joan
// fixing bugs
//
// Revision 1.22  2002/04/10 17:45:07  joan
// fixing bugs
//
// Revision 1.21  2002/04/10 17:00:12  joan
// working on throw exception
//
// Revision 1.20  2002/04/09 18:44:46  joan
// working on put
//
// Revision 1.19  2002/04/09 18:22:04  joan
// working on business rule exception
//
// Revision 1.18  2002/04/09 17:59:53  joan
// working on validate
//
// Revision 1.17  2002/04/09 17:18:40  joan
// fixing bugs
//
// Revision 1.16  2002/04/09 17:05:52  joan
// working on put method
//
// Revision 1.15  2002/04/08 16:39:41  joan
// remove getEntityList methods.
//
// Revision 1.14  2002/04/03 22:23:01  joan
// make getEntityLists identical to EntityList 's ones
//
// Revision 1.13  2002/03/26 23:07:36  joan
// working on LINK_MOVE
//
// Revision 1.12  2002/03/26 22:07:03  joan
// fixing LINK_MOVE
//
// Revision 1.11  2002/03/26 21:32:37  joan
// fixing LINK_MOVE
//
// Revision 1.10  2002/03/15 18:51:44  joan
// fix link method
//
// Revision 1.9  2002/03/06 17:54:28  joan
// move static methods from EntityList to EANUtility
//
// Revision 1.8  2002/03/05 19:06:19  joan
// syntax
//
// Revision 1.7  2002/03/05 18:52:34  joan
// move the link method from EntityList to EANUtility
//
// Revision 1.6  2002/03/04 20:57:13  joan
// working on link method
//

package COM.ibm.eannounce.objects;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.T;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnID;
import COM.ibm.opicmpdh.middleware.ReturnRelatorKey;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.transactions.OPICMList;
import COM.ibm.opicmpdh.transactions.PartNo;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;


/**
 * EANUtility
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EANUtility {
    /**
     * FIELD
     */
    public static final int LINK_MOVE = 0;
    /**
     * FIELD
     */
    public static final int LINK_COPY = 1;
    /**
     * FIELD
     */
    public static final int LINK_DEFAULT = 2;
    public static final String OPT_REPLACEALL_STR = "REPLACEALL"; 
    public static final String OPT_DEFAULT_STR = "";
    public static final String OPT_NODUPES_STR = "NODUPES";

   // private static EANList c_elEntityItems = null;

    /**
     * EANUtility
     *
     *  @author David Bigelow
     */
    private EANUtility() {
    }

    /**
     * linkEntityItems
     *
     * @param _rdi
     * @param _prof
     * @param _strOptions
     * @param _aeiParent
     * @param _aeiChild
     * @param _ml
     * @param _iSwitch
     * @param _iCount
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.rmi.RemoteException
     * @return
     *  @author David Bigelow
     */
    public static OPICMList linkEntityItems(RemoteDatabaseInterface _rdi, Profile _prof, String _strOptions, EntityItem[] _aeiParent, EntityItem[] _aeiChild, MetaLink _ml, int _iSwitch, int _iCount)
        throws MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException {
        OPICMList olReturn = null;
        try {
            olReturn = linkEntityItems(_rdi, null, _prof, _strOptions, _aeiParent, _aeiChild, _ml, _iSwitch, _iCount, true);
        } catch (SQLException x) {
            x.printStackTrace();
        }
        return olReturn;
    }

    /**
     * linkEntityItems
     *
     * @param _db
     * @param _prof
     * @param _strOptions
     * @param _aeiParent
     * @param _aeiChild
     * @param _ml
     * @param _iSwitch
     * @param _iCount
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.sql.SQLException
     * @return
     *  @author David Bigelow
     */
    public static OPICMList linkEntityItems(Database _db, Profile _prof, String _strOptions, EntityItem[] _aeiParent, EntityItem[] _aeiChild, MetaLink _ml, int _iSwitch, int _iCount) throws MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException {
        OPICMList olReturn = null;
        try {
            olReturn = linkEntityItems(null, _db, _prof, _strOptions, _aeiParent, _aeiChild, _ml, _iSwitch, _iCount, true);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        return olReturn;
    }

    /**
     * linkEntityItems
     *
     * @param _rdi
     * @param _prof
     * @param _strOptions
     * @param _aeiParent
     * @param _aeiChild
     * @param _ml
     * @param _iSwitch
     * @param _iCount
     * @param _bCreateLink
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.rmi.RemoteException
     * @return
     *  @author David Bigelow
     */
    public static OPICMList linkEntityItems(RemoteDatabaseInterface _rdi, Profile _prof, String _strOptions, EntityItem[] _aeiParent, EntityItem[] _aeiChild, MetaLink _ml, int _iSwitch, int _iCount, boolean _bCreateLink)
        throws MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException {
        OPICMList olReturn = null;
        try {
            olReturn = linkEntityItems(_rdi, null, _prof, _strOptions, _aeiParent, _aeiChild, _ml, _iSwitch, _iCount, _bCreateLink);
        } catch (SQLException x) {
            x.printStackTrace();
        }
        return olReturn;
    }

    /**
     * linkEntityItems
     *
     * @param _db
     * @param _prof
     * @param _strOptions
     * @param _aeiParent
     * @param _aeiChild
     * @param _ml
     * @param _iSwitch
     * @param _iCount
     * @param _bCreateLink
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.sql.SQLException
     * @return
     *  @author David Bigelow
     */
    public static OPICMList linkEntityItems(Database _db, Profile _prof, String _strOptions, EntityItem[] _aeiParent, EntityItem[] _aeiChild, MetaLink _ml, int _iSwitch, int _iCount, boolean _bCreateLink)
        throws MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException {
        OPICMList olReturn = null;
        try {
            olReturn = linkEntityItems(null, _db, _prof, _strOptions, _aeiParent, _aeiChild, _ml, _iSwitch, _iCount, _bCreateLink);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        return olReturn;
    }

    /*
    * We are basically Linking a set of parents to a set of children using the metLink guy
    */
    private static OPICMList linkEntityItems(RemoteDatabaseInterface _rdi, Database _db, Profile _prof, String _strOption, EntityItem[] _aeiParent, EntityItem[] _aeiChild, MetaLink _ml, int _iSwitch, int _iCount, boolean _bCreateLink)
        throws MiddlewareRequestException, MiddlewareException, RemoteException, SQLException, MiddlewareShutdownInProgressException {

        Vector vctReturnRelatorKeys = new Vector();
        String strEntityType = _ml.getEntityType();
        String strEntity1Type = _ml.getEntity1Type();
        String strEntity2Type = _ml.getEntity2Type();

        if (_rdi == null && _db == null) {
            return null;
        }

        for (int ii = 0; ii < _aeiParent.length; ii++) {

            EntityItem eiP = _aeiParent[ii];

            // We may need to look down the chain to get the right match

            if (!eiP.getEntityType().equals(strEntity1Type)) {
                for (int ij = 0; ij < eiP.getDownLinkCount(); ij++) {
                    EntityItem eiPDown = (EntityItem) eiP.getDownLink(ij);
                    if (eiPDown.getEntityType().equals(strEntity1Type)) {
                        eiP = eiPDown;
                    }
                }
            }

            for (int iy = 0; iy < _aeiChild.length; iy++) {
                EntityItem eiC = _aeiChild[iy];

                //          vctReturnRelatorKeys.addElement(new ReturnRelatorKey(strEntityType, _ml.getNextRID(),strEntity1Type, eiP.getEntityID(),strEntity2Type, _aeiChild[iy].getEntityID(),true));

                if (_iSwitch == LINK_MOVE) {
                    // for LINK_MOVE, we need to look at the relator
                    if (eiC.getEntityType().equals(strEntityType)) {
                        vctReturnRelatorKeys.addElement(new ReturnRelatorKey(strEntityType, _ml.getNextRID(), strEntity1Type, eiP.getEntityID(), eiC.getEntityType(), eiC.getEntityID(), true));
                    }
                } else if (_iSwitch == LINK_DEFAULT || _iSwitch == LINK_COPY) {
                    // look down the child entity to get the right match
                    if (!eiC.getEntityType().equals(strEntity2Type)) {
                        for (int iz = 0; iz < eiC.getDownLinkCount(); iz++) {
                            EntityItem eiCDown = (EntityItem) eiC.getDownLink(iz);
                            if (eiCDown.getEntityType().equals(strEntity2Type)) {
                                eiC = eiCDown;
                            }
                        }
                    }
                    vctReturnRelatorKeys.addElement(new ReturnRelatorKey(strEntityType, _ml.getNextRID(), strEntity1Type, eiP.getEntityID(), strEntity2Type, eiC.getEntityID(), _bCreateLink));
                }
            }
        }

        // We now have the vectors...
        // Lets make the call.

        if (_db == null) {
            return _rdi.link(_prof, vctReturnRelatorKeys, _strOption, _iSwitch, _iCount, false);
        } else {
            return _db.link(_prof, vctReturnRelatorKeys, _strOption, _iSwitch, _iCount, false);
        }

        // Future synronization rules...

    }

    /**
     * linkAllEntityItems
     *
     * @param _rdi
     * @param _db
     * @param _prof
     * @param _strOption
     * @param _aeiParent
     * @param _aeiChild
     * @param _iSwitch
     * @param _iCount
     * @param _bCreateLink
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.rmi.RemoteException
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @return
     *  @author David Bigelow
     */
    public static OPICMList linkAllEntityItems(RemoteDatabaseInterface _rdi, Database _db, Profile _prof, String _strOption, EntityItem[] _aeiParent, EntityItem[] _aeiChild, int _iSwitch, int _iCount, boolean _bCreateLink)
        throws MiddlewareRequestException, MiddlewareException, RemoteException, SQLException, MiddlewareShutdownInProgressException {

        String strTraceBase = "EANUtility linkAllEntityItems method";
        Vector vctReturnRelatorKeys = new Vector();
        boolean bLinkChild = false;

        if (_rdi == null && _db == null) {
            return null;
        }

        for (int ii = 0; ii < _aeiParent.length; ii++) {

            MetaLinkGroup mlgP = null;

            EntityItem eiP = _aeiParent[ii];
            EntityGroup egP = eiP.getEntityGroup();

            if (egP == null) {
                D.ebug(D.EBUG_SPEW,strTraceBase + " egP is null");
                continue;
            }

            if (egP.isRelator() || egP.isAssoc()) {
                eiP = (EntityItem) eiP.getDownLink(0);
                egP = eiP.getEntityGroup();
            }

            mlgP = egP.getMetaLinkGroup();

            //link to children
            for (int p = 0; p < mlgP.getMetaLinkCount(MetaLinkGroup.DOWN); p++) {
                MetaLink ml = mlgP.getMetaLink(MetaLinkGroup.DOWN, p);
                String strEntityType = ml.getEntityType();
                String strEntity1Type = ml.getEntity1Type();
                String strEntity2Type = ml.getEntity2Type();

                if (!eiP.getEntityType().equals(strEntity1Type)) {
                    D.ebug(D.EBUG_SPEW,strTraceBase + " eiP entity type " + eiP.getEntityType() + " not equal " + strEntity1Type);
                    D.ebug(D.EBUG_SPEW,strTraceBase + " ml " + ml.dump(false));
                    continue;
                }

                for (int iy = 0; iy < _aeiChild.length; iy++) {
                    EntityItem eiC = _aeiChild[iy];

                    if (_iSwitch == LINK_MOVE) {
                        // for LINK_MOVE, we need to look at the relator
                        if (eiC.getEntityType().equals(strEntityType)) {
                            bLinkChild = true;
                            vctReturnRelatorKeys.addElement(new ReturnRelatorKey(strEntityType, ml.getNextRID(), strEntity1Type, eiP.getEntityID(), eiC.getEntityType(), eiC.getEntityID(), true));
                        }
                    } else if (_iSwitch == LINK_DEFAULT || _iSwitch == LINK_COPY) {
                        if (eiC.getEntityType().equals(strEntity2Type)) {
                            bLinkChild = true;
                            vctReturnRelatorKeys.addElement(new ReturnRelatorKey(strEntityType, ml.getNextRID(), strEntity1Type, eiP.getEntityID(), strEntity2Type, eiC.getEntityID(), _bCreateLink));
                        } else { // look down the child entity to get the right match
                            for (int iz = 0; iz < eiC.getDownLinkCount(); iz++) {
                                EntityItem eiCDown = (EntityItem) eiC.getDownLink(iz);
                                if (eiCDown.getEntityType().equals(strEntity2Type)) {
                                    bLinkChild = true;
                                    vctReturnRelatorKeys.addElement(new ReturnRelatorKey(strEntityType, ml.getNextRID(), strEntity1Type, eiP.getEntityID(), strEntity2Type, eiCDown.getEntityID(), _bCreateLink));
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!bLinkChild) {
            for (int ii = 0; ii < _aeiChild.length; ii++) {

                MetaLinkGroup mlgP = null;

                EntityItem eiP = _aeiChild[ii];
                EntityGroup egP = eiP.getEntityGroup();
                if (egP == null) {
                    D.ebug(D.EBUG_SPEW,strTraceBase + " egP is null");
                    continue;
                }

                if (egP.isRelator() || egP.isAssoc()) {
                    eiP = (EntityItem) eiP.getDownLink(0);
                    egP = eiP.getEntityGroup();
                }

                mlgP = egP.getMetaLinkGroup();

                for (int p = 0; p < mlgP.getMetaLinkCount(MetaLinkGroup.DOWN); p++) {
                    MetaLink ml = mlgP.getMetaLink(MetaLinkGroup.DOWN, p);
                    String strEntityType = ml.getEntityType();
                    String strEntity1Type = ml.getEntity1Type();
                    String strEntity2Type = ml.getEntity2Type();

                    if (!eiP.getEntityType().equals(strEntity1Type)) {
                        D.ebug(D.EBUG_SPEW,strTraceBase + " eiP entity type " + eiP.getEntityType() + " not equal " + strEntity1Type);
                        D.ebug(D.EBUG_SPEW,strTraceBase + " ml " + ml.dump(false));
                        continue;
                    }

                    for (int iy = 0; iy < _aeiParent.length; iy++) {
                        EntityItem eiC = _aeiParent[iy];

                        if (_iSwitch == LINK_MOVE) {
                            // for LINK_MOVE, we need to look at the relator
                            if (eiC.getEntityType().equals(strEntityType)) {
                                vctReturnRelatorKeys.addElement(new ReturnRelatorKey(strEntityType, ml.getNextRID(), strEntity1Type, eiP.getEntityID(), eiC.getEntityType(), eiC.getEntityID(), true));
                            }
                        } else if (_iSwitch == LINK_DEFAULT || _iSwitch == LINK_COPY) {
                            if (eiC.getEntityType().equals(strEntity2Type)) {
                                vctReturnRelatorKeys.addElement(new ReturnRelatorKey(strEntityType, ml.getNextRID(), strEntity1Type, eiP.getEntityID(), strEntity2Type, eiC.getEntityID(), _bCreateLink));
                            } else { // look down the child entity to get the right match
                                for (int iz = 0; iz < eiC.getDownLinkCount(); iz++) {
                                    EntityItem eiCDown = (EntityItem) eiC.getDownLink(iz);
                                    if (eiCDown.getEntityType().equals(strEntity2Type)) {
                                        vctReturnRelatorKeys.addElement(new ReturnRelatorKey(strEntityType, ml.getNextRID(), strEntity1Type, eiP.getEntityID(), strEntity2Type, eiCDown.getEntityID(), _bCreateLink));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // We now have the vectors...
        // Lets make the call.
        if (vctReturnRelatorKeys.size() > 0) {
            if (_db == null) {
                return _rdi.link(_prof, vctReturnRelatorKeys, _strOption, _iSwitch, _iCount, false);
            } else {
                return _db.link(_prof, vctReturnRelatorKeys, _strOption, _iSwitch, _iCount, false);
            }
        } else {
            return null;
        }

        // Future synronization rules...

    }

    /**
     * checkOwnership
     *
     * @param _db
     * @param _prof
     * @param _strRelatorType
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public static boolean checkOwnership(Database _db, Profile _prof, String _strRelatorType) throws SQLException, MiddlewareException {
        // Initialize some SP specific objects needed in this method
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
        ReturnStatus returnStatus = new ReturnStatus(-1);

        // Placeholders for dates
        String strNow = null;
       // String strForever = null;

        // Pull some profile info...
        String strEnterprise = _prof.getEnterprise();

        try {
            DatePackage dpNow = _db.getDates();
            strNow = dpNow.getNow();
           // strForever = dpNow.getForever();

            try {
                rs = _db.callGBL8203(returnStatus, strEnterprise, _strRelatorType, strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs != null){
            		rs.close();
                	rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

            for (int ii = 0; ii < rdrs.size(); ii++) {
                String strRelatorType = rdrs.getColumn(ii, 0).trim();
                String strLinkValue = rdrs.getColumn(ii, 3).trim();
                _db.debug(D.EBUG_SPEW, "gbl8203 answer is:" + strRelatorType + ":" + strLinkValue);
                if (strLinkValue.charAt(0) == 'Y') {
                    return true;
                }
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
            _db.commit();
        }
        return false;
    }

    /**
     * isOrphan
     *
     * @param _db
     * @param _prof
     * @param _relatorEI
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public static boolean isOrphan(Database _db, Profile _prof, EntityItem _relatorEI) throws SQLException, MiddlewareException {

        // Initialize some SP specific objects needed in this method
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
        ReturnStatus returnStatus = new ReturnStatus(-1);

        // Placeholders for dates
        String strNow = null;
       // String strForever = null;

        // Pull some profile info...
        int iOPWGID = _prof.getOPWGID();
        String strEnterprise = _prof.getEnterprise();

        try {
            DatePackage dpNow = _db.getDates();
            String strEntityType = _relatorEI.getEntityType();
            int iEntityID = _relatorEI.getEntityID();

            int iCount = 0;

            strNow = dpNow.getNow();
           // strForever = dpNow.getForever();

            try {
                rs = _db.callGBL8202(returnStatus, strEnterprise, iOPWGID, strEntityType, iEntityID, strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs != null){
            		rs.close();
            		rs = null;
            	}
                
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

            for (int ii = 0; ii < rdrs.size(); ii++) {
                String strRelatorType = rdrs.getColumn(ii, 0).trim();
                int iRelatorID = rdrs.getColumnInt(ii, 1);
                _db.debug(D.EBUG_SPEW, "gbl8202 answer is:" + strRelatorType + ":" + iRelatorID);

                if (strRelatorType.equals(strEntityType) && iRelatorID != iEntityID) {
                    iCount++;
                }
            }

            if (iCount <= 0) {
                return true;
            }
        } finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
        return false;
    }

    private static void getVELockERLists(Database _db, Profile _prof, Hashtable VELockERListTbl,
    		String linkEntityType,String childEntityType)
    {
    	VELockERList pList = (VELockERList)VELockERListTbl.get(linkEntityType);                                                                                                                              
    	VELockERList cList = (VELockERList)VELockERListTbl.get(childEntityType);                                                                                                                             
    	
    	try{
    		if (pList==null){
    			pList = _db.getVELockERList(_prof, linkEntityType);
    			VELockERListTbl.put(linkEntityType, pList);
    			cList = _db.getVELockERList(_prof, childEntityType);
    			VELockERListTbl.put(childEntityType, cList);		
    		}
    	}catch(Exception exc){
    		exc.printStackTrace();
    	}
    }
    /**
     * This is the guy that manages the way links get applied in the e-announce world.
     *
     * @return OPICMList
     * @param _db
     * @param _prof
     * @param _vctReturnRelatorKeys
     * @param _strLinkOption
     * @param _iSwitch
     * @param _iCopyCount
     * @param _bCheckOrphan
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     */
    public static final OPICMList link(Database _db, Profile _prof, Vector _vctReturnRelatorKeys, String _strLinkOption, int _iSwitch, int _iCopyCount, boolean _bCheckOrphan) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {

        // The stored procedure ReturnStatus
        OPICMList olReturn = new OPICMList();
        ReturnRelatorKey rrk = null;

        // Placeholders for dates
        String strNow = null;
        String strForever = null;
        String strEndOfDay = null;

        // Some basis EntityType, EntityID objects
        String strEntityType = null;
        int iEntityID = 0;
        String strEntity1Type = null;
        int iEntity1ID = 0;
        String strEntity2Type = null;
        int iEntity2ID = 0;

        EntityItemException eie = new EntityItemException();

        try {

            // this is for checking entities' VE lock before we link them
            Vector vctNetRelatorKeys = new Vector();

            DatePackage dpNow = _db.getDates();
            strNow = dpNow.getNow();
            strForever = dpNow.getForever();
            strEndOfDay = dpNow.getEndOfDay();

            _db.debug(D.EBUG_DETAIL, " EANUtility link method");
            // do initial delete check as a group to reduce io
            EANList removeLinkList = new EANList();
            Hashtable removeRrkTbl = new Hashtable();
            Hashtable VELockERListTbl = new Hashtable(); // hang onto VELockERList by entitytype

            for (int j = 0; j < _vctReturnRelatorKeys.size(); j++) {
                rrk = (ReturnRelatorKey) _vctReturnRelatorKeys.elementAt(j);
                iEntityID = rrk.getEntityID();
                strEntityType = rrk.getEntityType();
                if (iEntityID > 0) {
                    // this is for removing a relator, we need to check relator's ve Lock
                    EntityItem eiRelator = new EntityItem(null, _prof, strEntityType, iEntityID);     
                    String[] aVELock = getVELockOwners(_db,_prof, eiRelator);
                    //if the relator is VE locked, we can't remove the link
                    if (aVELock.length > 0) {
                        buildErrorMsg(_db, _prof, eiRelator, 
                        		"Unable to remove because the relator is VE locked (ok)", eie); 
                        continue;
                    }
                    removeLinkList.put(eiRelator);
                    removeRrkTbl.put(eiRelator.getKey(), rrk);
					/*do all at once
					 * if (!canDelete(_db, _prof, eiRelator)) {
						eie.add(new EntityItem(eiRelator), " Unable to remove the entity because the parents are in final status.");
						continue;
					}*/

                } else { // relator doesnt exist yet
                    // this is for adding a relator, need to check first and second entity
                    strEntity1Type = rrk.getEntity1Type();
                    iEntity1ID = rrk.getEntity1ID();
                    strEntity2Type = rrk.getEntity2Type();
                    iEntity2ID = rrk.getEntity2ID();

                    // get VELockERList for first and second entity
                    getVELockERLists(_db, _prof, VELockERListTbl, strEntity1Type,strEntity2Type);

                    VELockERList pList = (VELockERList)VELockERListTbl.get(strEntity1Type);//_db.getVELockERList(_prof, strEntity1Type);
                    VELockERList cList = (VELockERList)VELockERListTbl.get(strEntity2Type);//_db.getVELockERList(_prof, strEntity2Type);

                    EntityItem ei1 = new EntityItem(null, _prof, strEntity1Type, iEntity1ID);
                    EntityItem ei2 = new EntityItem(null, _prof, strEntity2Type, iEntity2ID);

                    // check first entity VE lock
                    String[] aVELock = getVELockOwners(_db,_prof, ei1);
                    if (aVELock.length > 0 && (!isLinkable(pList, aVELock, strEntity1Type, strEntityType))) {
                        buildErrorMsg(_db, _prof, ei1, 
                        		"Unable to link because the entity is VE locked (ok)", eie); 
                        continue;
                    }

                    // check second entity VE lock
                    aVELock = getVELockOwners(_db,_prof, ei2);
                    if (aVELock.length > 0 && (!isLinkable(cList, aVELock, strEntity2Type, strEntityType))) {
                        buildErrorMsg(_db, _prof, ei2, 
                        		"Unable to link because the entity is VE locked (ok)", eie); 
                        continue;
                    }
                    
                    vctNetRelatorKeys.addElement(rrk);
                    D.ebug(D.EBUG_SPEW," == A vctNetRelatorKeys.size:" + vctNetRelatorKeys.size());
                }
            }
            // check deletes all at once
            if(removeLinkList.size()>0){
            	EANList deleteAbleList = canDelete(_db, _prof, removeLinkList, eie);
            	for (int i = 0; i < deleteAbleList.size(); i++) {
        	    	EntityItem relator = (EntityItem) deleteAbleList.getAt(i);
        	    	vctNetRelatorKeys.addElement(removeRrkTbl.get(relator.getKey()));
            	}
                D.ebug(D.EBUG_SPEW," == R vctNetRelatorKeys.size:" + vctNetRelatorKeys.size());
            	removeLinkList.clear();
            	deleteAbleList.clear();
            	removeRrkTbl.clear();
            }
        	
            // release memory
            Enumeration e = VELockERListTbl.elements();
        	while (e.hasMoreElements()) {
        		VELockERList uag = (VELockERList) e.nextElement();
        		uag.dereference();
        	}
            VELockERListTbl.clear();
            VELockERListTbl=null;
                     
            if (_iSwitch == LINK_MOVE) {
            	linkMove(_db, _prof,vctNetRelatorKeys, olReturn, strNow,eie);
            } else if (_iSwitch == LINK_COPY) {
            	linkCopy(_db, _prof, vctNetRelatorKeys, olReturn, 
                		_strLinkOption, _iCopyCount, strNow, strForever, strEndOfDay,eie);
            } else if (_iSwitch == LINK_DEFAULT) {
            	linkDefault(_db, _prof, vctNetRelatorKeys, olReturn, 
                		_strLinkOption, strNow, strForever, _bCheckOrphan,false,eie);
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
            _db.commit();
        }

        if(eie.getErrorCount() > 0) {
            MiddlewareBusinessRuleException mbrx = new MiddlewareBusinessRuleException("General Failure: Please see details");
			mbrx.add(eie.getObject(0), eie.getMessage());
			throw mbrx;
		}

        return olReturn;
    }
    /**
     * Shared by linkactionitem and EANUtility.link to handle LINK_DEFAULT case 
     * @param _db
     * @param _prof
     * @param vctNetRelatorKeys
     * @param olReturn
     * @param _strLinkOption
     * @param strNow
     * @param strForever
     * @param _bCheckOrphan
     * @param doR51Check
     * @param eie
     * @throws MiddlewareException
     * @throws SQLException
     * @throws MiddlewareShutdownInProgressException
     */
    public static void linkDefault(Database _db, Profile _prof, Vector vctReturnRelatorKeys, OPICMList olReturn, 
    		String _strLinkOption, String strNow, String strForever, boolean _bCheckOrphan,
    		boolean doR51Check, EntityItemException eie) 
    throws MiddlewareException, SQLException, MiddlewareShutdownInProgressException
    {
        // Initialize some SP specific objects needed in this method
        ResultSet rs = null;
        ReturnRelatorKey rrk = null;
        ReturnID idNew = new ReturnID(0);
    	ReturnStatus returnStatus = new ReturnStatus(-1);
        
    	int iOpenID = _prof.getOPWGID();
    	int iTranID = _prof.getTranID();
    	String strEnterprise = _prof.getEnterprise();
        int iSessionID = _prof.getSessionID();
		int iNLSID = _prof.getReadLanguage().getNLSID();
        // Some basic EntityType, EntityID objects
        String strEntityType = null;
        int iEntityID = 0;
        String strEntity1Type = null;
        int iEntity1ID = 0;
        String strEntity2Type = null;
        int iEntity2ID = 0;

        // Some new place holders
        int iEntityIDNew = 0;
        Vector vctNetRelatorKeys = new Vector();
        Hashtable rdrsCRTbl = new Hashtable();

        _db.debug(D.EBUG_DETAIL, "EANUtility.linkDefault:  rrk.size() "+vctReturnRelatorKeys.size()+
        		" _strLinkOption "+_strLinkOption+" _bCheckOrphan "+_bCheckOrphan+" doR51Check "+doR51Check);
        // strip out any that cant be linked
        for (int ii = 0; ii < vctReturnRelatorKeys.size(); ii++) {
            if (vctReturnRelatorKeys.elementAt(ii) instanceof ReturnRelatorKey) {

                rrk = (ReturnRelatorKey) vctReturnRelatorKeys.elementAt(ii);
                _db.test(rrk.getEntityType() != null, "entityType is null");
                _db.test(rrk.getEntity1Type() != null, "entity1Type is null");
                _db.test(rrk.getEntity1ID() > 0, "entity1ID <= 0");
                _db.test(rrk.getEntity2Type() != null, "entity2Type is null");
                _db.test(rrk.getEntity2ID() > 0, "entity2ID <= 0");

            	strEntityType = rrk.getEntityType();
                strEntity1Type = rrk.getEntity1Type();
                iEntity1ID = rrk.getEntity1ID();
                strEntity2Type = rrk.getEntity2Type();
                iEntity2ID = rrk.getEntity2ID();
                
                EntityItem ei1 = new EntityItem(null, _prof, strEntity1Type, iEntity1ID);
                EntityItem ei2 = new EntityItem(null, _prof, strEntity2Type, iEntity2ID);
          
                if(rrk.isActive() && // this is a link request
                		!canCreateRelator(_db, _prof, strEntityType, ei1, ei2,eie,rdrsCRTbl)){
                    _db.debug(D.EBUG_DETAIL, "EANUtility.linkDefault: cannot link "+ei1.getKey()+" to "+
                    		ei2.getKey()+" because of status");
                    ei1.dereference();
                    ei2.dereference();
                	continue;
                }
                
                ei1.dereference();
                ei2.dereference();
                
                vctNetRelatorKeys.add(rrk);
            }
        }

        rdrsCRTbl.clear();
        
        // Process inserts/updates for each Relator.. assume the proper relators were de - activated
        // from the above control in this method (in the case of a move)
        if (OPT_REPLACEALL_STR.equalsIgnoreCase(_strLinkOption)) {
        	replaceAll(_db, _prof,vctNetRelatorKeys, strNow,eie);
        } // end REPLACEALL option

        Hashtable egTbl = new Hashtable();
        
        for (int i = 0; i < vctNetRelatorKeys.size(); i++) {
        	boolean bexists = false;
        	rrk = (ReturnRelatorKey) vctNetRelatorKeys.elementAt(i);

        	_db.debug(D.EBUG_DETAIL, "EANUtility.linkDefault: rrk["+i+"]:" + rrk.toString());

        	strEntity1Type = rrk.getEntity1Type();
        	iEntity1ID = rrk.getEntity1ID();
        	strEntityType = rrk.getEntityType();
        	iEntityID = rrk.getEntityID();
        	strEntity2Type = rrk.getEntity2Type();
        	iEntity2ID = rrk.getEntity2ID();

        	if (OPT_NODUPES_STR.equalsIgnoreCase(_strLinkOption)) {
        		// Check for a dupe
        		try {
        			rs = _db.callGBL2991(returnStatus, iOpenID, strEnterprise, rrk.getEntityType(), rrk.getEntity1Type(), rrk.getEntity1ID(), rrk.getEntity2Type(), rrk.getEntity2ID(), strNow, strNow);
        			if (rs.next()) {
        				bexists = true;
        				_db.debug(D.EBUG_DETAIL, "EANUtility.linkDefault: No Dupes found existing relator.. skipping add. " + rrk.toString());
        			}
        		} finally {
        			if (rs != null){
        				rs.close();
        				rs = null;
        			}
        			_db.commit();
        			_db.freeStatement();
        			_db.isPending();
        		}
        	}

        	if ((OPT_DEFAULT_STR.equalsIgnoreCase(_strLinkOption) || OPT_REPLACEALL_STR.equalsIgnoreCase(_strLinkOption) 
        			|| (OPT_NODUPES_STR.equalsIgnoreCase(_strLinkOption)) 
        			&& !bexists)) {
        		Vector vctPartNos = new Vector();
        		int iPartNoSessionID = -1;
        		boolean bR51Pass = true;
        		if(doR51Check){
        			iPartNoSessionID = PartNo.getSessionId(_db, _prof);
        			bR51Pass= doRule51Check(_db, _prof,rrk,vctPartNos,iPartNoSessionID,eie);
        		}
        		if (bR51Pass){
        			// Create the new relator.. Not sure what is up with the de-activating stuff here... this may serve if the relatorid is positive
        			if (rrk.isActive()) {
        				// TODO NLSREF
        				idNew = _db.callGBL2098(returnStatus, iOpenID, iSessionID, strEnterprise, strEntityType, new ReturnID(iEntityID), strEntity1Type, iEntity1ID, strEntity2Type, iEntity2ID, iTranID, strNow, strForever,iNLSID);
        			} else {// deactivating the relator
        				if (_bCheckOrphan) {
        					if (checkOwnership(_db, _prof, strEntityType)) {
        						if (isOrphan(_db, _prof, new EntityItem(null, _prof, strEntityType, iEntityID))) {
        							rrk.setPosted(false);
        							olReturn.put(rrk);
        							continue;
        						}
        					}
        				} // DWB Should we be using 2099??
        						// TODO NLSREF
        				idNew = _db.callGBL2098(returnStatus, iOpenID, iSessionID, strEnterprise, strEntityType, new ReturnID(iEntityID), strEntity1Type, iEntity1ID, strEntity2Type, iEntity2ID, iTranID, strNow, strNow,iNLSID);
        			}

        			rrk.setReturnID(idNew);
        			rrk.setPosted(true);
        			olReturn.put(rrk);

        			_db.commit();
        			_db.freeStatement();
        			_db.isPending();

        			iEntityIDNew = idNew.intValue();
        			if (rrk.isActive()) {
        				_db.callGBL3001(returnStatus, strEnterprise, iOpenID, strEntityType, iEntityIDNew, iTranID);
        				_db.commit();
        				_db.freeStatement();
        				_db.isPending();

        	    		EntityGroup eg = (EntityGroup)egTbl.get(strEntityType);
                		if(eg ==null){
                			eg = new EntityGroup(null, _db, _prof, strEntityType, "Edit", true);
                			eg.enforceWorkGroupDomain(_db, _prof);
                			egTbl.put(strEntityType, eg);
                		}
        				populateDefaultValues(_db, _prof, eg,strEntityType, iEntityIDNew);
        			}

        			// Release any locks on PartNumbers!!
        			for (int y = 0; y < vctPartNos.size(); y++) {
        				PartNo.removeForSession(_db, (String) vctPartNos.elementAt(y), iPartNoSessionID);
        			}
        		}
        	} // end does not exist yet
        	else {
        		// already exists-NODUPE condition or unrecognized option
        		rrk.setPosted(false);
        		olReturn.put(rrk);
        	}
        } // end rrk loop
        
        // release memory
    	for (Enumeration e = egTbl.elements(); e.hasMoreElements();){
    		EntityGroup eg = (EntityGroup)e.nextElement();
			eg.dereference();
		}
        
        egTbl.clear();
        
        vctNetRelatorKeys.clear();
    }
    /**
     * used by linkcopy and linkdefault when REPLACEALL option is specified
     * @param _db
     * @param _prof
     * @param vctNetRelatorKeys
     * @param strNow
     * @param eie
     * @throws MiddlewareException
     * @throws SQLException
     * @throws MiddlewareShutdownInProgressException
     */
    private static void replaceAll(Database _db, Profile _prof,Vector vctNetRelatorKeys, 
    		String strNow,EntityItemException eie) throws MiddlewareException, SQLException, MiddlewareShutdownInProgressException
    {
        // Initialize some SP specific objects needed in this method
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
    	ReturnStatus returnStatus = new ReturnStatus(-1);
        
    	int iOpenID = _prof.getOPWGID();
    	int iTranID = _prof.getTranID();
        String strRoleCode = _prof.getRoleCode();
    	String strEnterprise = _prof.getEnterprise();
		
    	 EANList elist = new EANList();
         _db.debug(D.EBUG_DETAIL, "EANUtility.replaceAll.REPLACEALL: Generating list of existing relators between the parent and child and deactivating them ");

         _db.debug(D.EBUG_SPEW,"EANUtility.replaceAll: == B vctNetRelatorKeys.size:" + vctNetRelatorKeys.size());

         // Loop through the vectors
         for (int i = 0; i < vctNetRelatorKeys.size(); i++) {
         	String strKey = null;
         	EntityItem ei = null;

         	// Lets pull out the meaningfull information
         	ReturnRelatorKey rrk = (ReturnRelatorKey) vctNetRelatorKeys.elementAt(i);

         	String strEntityType = rrk.getEntityType();
         	String strEntity1Type = rrk.getEntity1Type();
         	int iEntity1ID = rrk.getEntity1ID();

         	// Get a list of existing relators to deactivate
         	try {
         		rs = _db.callGBL7940(returnStatus, strEnterprise, strRoleCode, strEntityType, strEntity1Type, iEntity1ID, strNow, strNow);
         		rdrs = new ReturnDataResultSet(rs);
         	} finally {
         		if (rs != null){
         			rs.close();
         			rs = null;
         		}

         		_db.commit();
         		_db.freeStatement();
         		_db.isPending();
         	}

         	for (int ii = 0; ii < rdrs.size(); ii++) {
         		strEntityType = rdrs.getColumn(ii, 0).trim();
         		int iEntityID = rdrs.getColumnInt(ii, 1);
         		_db.debug(D.EBUG_SPEW, "gbl7940 answer: " + strEntityType + ":" + iEntityID + ":");

         		strKey = strEntityType + iEntityID;
         		ei = (EntityItem)elist.get(strKey);
         		if (ei == null) {
         			_db.debug(D.EBUG_DETAIL, "EANUtility.replaceAll Placing in list:" + strKey);
         			ei = new EntityItem(null, _prof, strEntityType, iEntityID);
         			// check for relator's VE lock
         			String []aLockOwner = EANUtility.getVELockOwners(_db, _prof, ei);
         			if (aLockOwner.length > 0) {
         				buildErrorMsg(_db, _prof, ei, 
         						"Unable to be replaced because the entity is VE locked (ok)", eie);
         				continue;
         			}
         			elist.put(ei);
         		}
         	}

         	// Here we go at turning off all existing... relators before we continue
         	for (int ii = 0; ii < elist.size(); ii++) {
         		ei = (EntityItem) elist.getAt(ii);
         		strEntityType = ei.getEntityType();
         		int iEntityID = ei.getEntityID();
         		_db.callGBL7937(returnStatus, strEnterprise, iOpenID, strEntityType, iEntityID, iTranID);
         		_db.commit();
         		_db.freeStatement();
         		_db.isPending();
         	}
         	elist.clear();
         } // end rrk loop
    }
    /**
     * this was only done in linkactionitem
     * @param _db
     * @param _prof
     * @param rrk
     * @param vctPartNos
     * @param iPartNoSessionID
     * @param eie
     * @return
     * @throws MiddlewareRequestException
     * @throws SQLException
     * @throws MiddlewareException
     */
    private static boolean doRule51Check(Database _db, Profile _prof,ReturnRelatorKey rrk,
    		Vector vctPartNos,int iPartNoSessionID,EntityItemException eie) throws MiddlewareRequestException, SQLException, MiddlewareException
    {
      	 boolean bR51Pass = true;
         if (rrk.hasRule51Group()) {       	 
             Rule51Group r51g = rrk.getRule51Group();
             _db.debug(D.EBUG_DETAIL, "EANUtility.doRule51Check Rule 51 FOUND!!! ");
             if (!r51g.validate(_db, "LinkAction")) {
                 buildErrorMsg(_db, _prof, new EntityItem(null, _prof, rrk.getEntity2Type(), rrk.getEntity2ID()), 
                		 r51g.getExceptionMessage() + "(ok)", eie);
                 bR51Pass = false;
             }
             if (bR51Pass) {
                 for (int ig = 0; ig < r51g.getDomainEntityIDCount(); ig++) {

                     int iDomainEID = r51g.getDomainEntityID(ig);
                     String strPartNo = r51g.getPartNo(iDomainEID);

                     _db.debug(D.EBUG_DETAIL, "EANUtility.doRule51Check Rule51, checking PN reservation for:" + strPartNo + ", " + iDomainEID);

                     if (!PartNo.exists(_db, strPartNo.trim())) {
                         PartNo.put(_db, strPartNo.trim(), iPartNoSessionID);
                         vctPartNos.addElement(strPartNo.trim());
                     } else {
                         bR51Pass = false;
                         buildErrorMsg(_db, _prof, new EntityItem(null, _prof, rrk.getEntity2Type(), rrk.getEntity2ID()), 
                        		 r51g.getExceptionMessage() + " (transaction exists in pending work pool.) (ok)", eie);   
                     }
                 }
             }

         }
         //_db.debug(D.EBUG_DETAIL,"Rule51 error count:" + eie.getErrorCount());
         // end Rule51!!
         return bR51Pass;
    }
    
    /**
     * @param _db
     * @param _prof
     * @param vctReturnRelatorKeys
     * @param olReturn
     * @param _strLinkOption
     * @param _iCopyCount
     * @param strNow
     * @param strForever
     * @param strEndOfDay
     * @param eie
     * @throws MiddlewareException
     * @throws SQLException
     * @throws MiddlewareShutdownInProgressException
     */
    public static void linkCopy(Database _db, Profile _prof, Vector vctReturnRelatorKeys, OPICMList olReturn, 
    		String _strLinkOption, int _iCopyCount, String strNow, String strForever, String strEndOfDay,
    		EntityItemException eie) 
    throws MiddlewareException, SQLException, MiddlewareShutdownInProgressException
    {
        // Initialize some SP specific objects needed in this method
        ReturnRelatorKey rrk = null;
        ReturnID idNew = new ReturnID(0);
    	ReturnStatus returnStatus = new ReturnStatus(-1);
        
    	int iOpenID = _prof.getOPWGID();
    	int iTranID = _prof.getTranID();
        String strRoleCode = _prof.getRoleCode();
    	String strEnterprise = _prof.getEnterprise();
        int iSessionID = _prof.getSessionID();
		int iNLSID = _prof.getReadLanguage().getNLSID();
        // Some basic EntityType, EntityID objects
        String strEntityType = null;
        int iEntityID = 0;
        String strEntity1Type = null;
        int iEntity1ID = 0;
        String strEntity2Type = null;
        int iEntity2ID = 0;

        // Some new place holders
        int iEntity2IDNew = 0;
        
        Vector vctNetRelatorKeys = new Vector();
        Hashtable rdrsCRTbl = new Hashtable();
    
        _db.debug(D.EBUG_DETAIL, "EANUtility.linkCopy:  rrk.size() "+vctReturnRelatorKeys.size()+
        		" _strLinkOption "+_strLinkOption+" _iCopyCount "+_iCopyCount);
        // strip out any that cant be linked
        for (int ii = 0; ii < vctReturnRelatorKeys.size(); ii++) {
            if (vctReturnRelatorKeys.elementAt(ii) instanceof ReturnRelatorKey) {

                rrk = (ReturnRelatorKey) vctReturnRelatorKeys.elementAt(ii);
                _db.test(rrk.getEntityType() != null, "entityType is null");
                _db.test(rrk.getEntity1Type() != null, "entity1Type is null");
                _db.test(rrk.getEntity1ID() > 0, "entity1ID <= 0");
                _db.test(rrk.getEntity2Type() != null, "entity2Type is null");
                _db.test(rrk.getEntity2ID() > 0, "entity2ID <= 0");

            	strEntityType = rrk.getEntityType();
                strEntity1Type = rrk.getEntity1Type();
                iEntity1ID = rrk.getEntity1ID();
                strEntity2Type = rrk.getEntity2Type();
                iEntity2ID = rrk.getEntity2ID();
                
                EntityItem ei1 = new EntityItem(null, _prof, strEntity1Type, iEntity1ID);
                EntityItem ei2 = new EntityItem(null, _prof, strEntity2Type, iEntity2ID);

                if(!canCreateRelator(_db, _prof, strEntityType, ei1, ei2,eie,rdrsCRTbl)){
                    _db.debug(D.EBUG_DETAIL, "EANUtility.linkCopy: cannot link "+ei1.getKey()+" to "+
                    		ei2.getKey()+" because of status");
                	continue;
                }
                vctNetRelatorKeys.add(rrk);
            }
        }

        if (OPT_REPLACEALL_STR.equalsIgnoreCase(_strLinkOption)) {
        	replaceAll(_db, _prof,vctNetRelatorKeys, strNow,eie);
        }

        Hashtable egTbl = new Hashtable();
        for (int ii = 0; ii < vctNetRelatorKeys.size(); ii++) {
        	rrk = (ReturnRelatorKey) vctNetRelatorKeys.elementAt(ii);

        	strEntity1Type = rrk.getEntity1Type();
        	iEntity1ID = rrk.getEntity1ID();
        	strEntityType = rrk.getEntityType();
        	iEntityID = 0;
        	strEntity2Type = rrk.getEntity2Type();
        	iEntity2ID = rrk.getEntity2ID();
        	iEntity2IDNew = 0;
        	
        	for (int x = 0; x < _iCopyCount; x++) {
        		// Make the entity
        		idNew = _db.callGBL2092(returnStatus, iOpenID, iSessionID, strEnterprise, strEntity2Type, new ReturnID(0), iTranID, strNow, strForever, iNLSID);
        		_db.commit();
        		_db.freeStatement();
        		_db.isPending();

        		// Set the new iEntity2ID
        		iEntity2IDNew = idNew.intValue();

        		// Copy the attributes 
        		_db.callGBL2268(returnStatus, iOpenID, strEnterprise, strRoleCode, strEntity2Type, iEntity2ID, strEntity2Type, iEntity2IDNew, iTranID, strEndOfDay, strEndOfDay, "0");
        		_db.commit();
        		_db.freeStatement();
        		_db.isPending();
        		// Now link it up
        		// TODO NLSREF
        		idNew = _db.callGBL2098(returnStatus, iOpenID, iSessionID, strEnterprise, strEntityType, new ReturnID(0), strEntity1Type, iEntity1ID, strEntity2Type, iEntity2IDNew, iTranID, strNow, strForever,iNLSID);
        		iEntityID = idNew.intValue();

        		rrk.setReturnID(idNew);
        		rrk.setPosted(true);
        		olReturn.put(rrk);
        		_db.commit();
        		_db.freeStatement();
        		_db.isPending();

        		// This will gen a new NAME & ID Attribute if the original entityid < 0 (denoting a new link)
        		_db.callGBL3001(returnStatus, strEnterprise, iOpenID, strEntityType, iEntityID, iTranID);
        		_db.commit();
        		_db.freeStatement();
        		_db.isPending();

        		EntityGroup eg = (EntityGroup)egTbl.get(strEntityType);
        		if(eg ==null){
        			eg = new EntityGroup(null, _db, _prof, strEntityType, "Edit", true);
        			eg.enforceWorkGroupDomain(_db, _prof);
        			egTbl.put(strEntityType, eg);
        		}

        		populateDefaultValues(_db, _prof, eg,strEntityType, iEntityID);
        	}
        }
        
        // release memory
    	for (Enumeration e = egTbl.elements(); e.hasMoreElements();){
    		EntityGroup eg = (EntityGroup)e.nextElement();
			eg.dereference();
		}
        
        egTbl.clear();
        
        vctNetRelatorKeys.clear();
        rdrsCRTbl.clear();
    }
    
    /**
     * @param _db
     * @param _prof
     * @param vctReturnRelatorKeys
     * @param olReturn
     * @param strNow
     * @param eie
     * @throws MiddlewareException
     * @throws SQLException
     */
    public static void linkMove(Database _db, Profile _prof, Vector vctReturnRelatorKeys, 
    		OPICMList olReturn, String strNow, EntityItemException eie) 
    throws MiddlewareException, SQLException{
    	ReturnStatus returnStatus = new ReturnStatus(-1);
        // Initialize some SP specific objects needed in this method
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
    	int iOpenID = _prof.getOPWGID();
    	int iTranID = _prof.getTranID();
    	String strEnterprise = _prof.getEnterprise();
    	Vector vctNetRelatorKeys = new Vector();
    	
        // do initial delete check as a group to reduce io
        EANList removeLinkList = new EANList();
        Hashtable removeRrkTbl = new Hashtable();
        Hashtable rdrsCRTbl = new Hashtable();
    	
        _db.debug(D.EBUG_DETAIL, "EANUtility.linkMove:  rrk.size() "+vctReturnRelatorKeys.size());

        // strip out any that cant be linked
        outerloop:for (int ii = 0; ii < vctReturnRelatorKeys.size(); ii++) {
        	if (vctReturnRelatorKeys.elementAt(ii) instanceof ReturnRelatorKey) {

        		ReturnRelatorKey rrk = (ReturnRelatorKey) vctReturnRelatorKeys.elementAt(ii);
        		String strRelatorType = rrk.getEntity2Type();
        		int iRelatorID = rrk.getEntity2ID();
        		String strEntity1Type = rrk.getEntity1Type();// this is the new parent
        		int iEntity1ID = rrk.getEntity1ID();

        		_db.test(strEntity1Type != null, "entity1Type is null");
        		_db.test(iEntity1ID > 0, "entity1ID <= 0");
        		_db.test(strRelatorType != null, "strRelatorType is null");
        		_db.test(iRelatorID > 0, "iRelatorID <= 0");

        		// check that relator can be removed later
        		EntityItem eiRelator = new EntityItem(null, _prof, strRelatorType, iRelatorID);     

        		// get children that will be moved to new parent
        		try {
        			rs = _db.callGBL2933(returnStatus, strEnterprise, strRelatorType, iRelatorID, iOpenID, strNow, strNow);
        			rdrs = new ReturnDataResultSet(rs);
        		} finally {
        			if (rs != null){
        				rs.close();
        				rs = null;
        			}

        			_db.commit();
        			_db.freeStatement();
        			_db.isPending();
        		}

        		EntityItem eiP = new EntityItem(null, _prof, strEntity1Type, iEntity1ID);// new parent to link to

        		for (int i2 = 0; i2 < rdrs.size(); i2++) {
        			String relType = rdrs.getColumn(i2, 0).trim();
        			int relID = rdrs.getColumnInt(i2, 1);
        			String ent1Type = rdrs.getColumn(i2, 2).trim();
        			int ent1ID = rdrs.getColumnInt(i2, 3);
        			String ent2Type = rdrs.getColumn(i2, 4).trim();
        			int ent2ID = rdrs.getColumnInt(i2, 5);
        			_db.debug(D.EBUG_SPEW, "gbl2933 answer: " + relType + ":" + relID + ":"+
        					ent1Type+":"+ent1ID+":"+ent2Type+":"+ent2ID);
        			EntityItem eiC = new EntityItem(null, _prof, ent2Type, ent2ID);

        			if(!canCreateRelator(_db, _prof, strRelatorType, eiP, eiC,eie,rdrsCRTbl)){
        				_db.debug(D.EBUG_DETAIL, "EANUtility.linkMove: cannot link "+eiP.getKey()+" to "+
        						eiC.getKey()+" because of status");
        				continue outerloop;
        			}
        		}

        		removeLinkList.put(eiRelator);
        		removeRrkTbl.put(eiRelator.getKey(), rrk);
        	}
        }
        // check deletes all at once
        if(removeLinkList.size()>0){
        	EANList deleteAbleList = canDelete(_db, _prof, removeLinkList, eie);
        	for (int i = 0; i < deleteAbleList.size(); i++) {
    	    	EntityItem relator = (EntityItem) deleteAbleList.getAt(i);
    	    	vctNetRelatorKeys.addElement(removeRrkTbl.get(relator.getKey()));
        	}
            D.ebug(D.EBUG_SPEW," == R vctNetRelatorKeys.size:" + vctNetRelatorKeys.size());
        	removeLinkList.clear();
        	deleteAbleList.clear();
        	removeRrkTbl.clear();
        }
        rdrsCRTbl.clear();

        // Loop through the vectors
        for (int i = 0; i < vctNetRelatorKeys.size(); i++) {
        	// Lets pull out the meaningfull information
        	ReturnRelatorKey rrk = (ReturnRelatorKey) vctNetRelatorKeys.elementAt(i);

        	String strRelatorType = rrk.getEntity2Type();
        	int iRelatorID = rrk.getEntity2ID();
        	String strEntity1Type = rrk.getEntity1Type();
        	int iEntity1ID = rrk.getEntity1ID();

        	// Now loop through all the relators and assign new parents to them.
        	// we will change the relator's entity1type and ID by calling gbl2937
        	_db.callGBL2937(returnStatus, strEnterprise, iOpenID, strRelatorType, iRelatorID, strEntity1Type, iEntity1ID, iTranID, strNow, strNow);
        	_db.freeStatement();
        	_db.isPending();

        	rrk.setReturnID(new ReturnID(iRelatorID));
        	rrk.setPosted(true);
        	olReturn.put(rrk);

        	// We will now fix up the NAME and ID Attributes
        	_db.callGBL3001(returnStatus, strEnterprise, iOpenID, strRelatorType, iRelatorID, iTranID);
        	_db.commit();
        	_db.freeStatement();
        	_db.isPending();
        }
    	_db.commit();
    }

    /**
     * validate
     *
     * @param _eanAttr
     * @param _strValue
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     *  @author David Bigelow
     */
    public static void validate(EANAttribute _eanAttr, String _strValue) throws EANBusinessRuleException {

        RequiredRuleException rre = new RequiredRuleException();

        _eanAttr.getMetaAttribute();

        // required validation
        rre.validate(_eanAttr, _strValue);
        if (rre.getErrorCount() > 0) {
            throw rre;
        }

        // If it is text.. we need to do basic text checking
        if (_eanAttr instanceof EANTextAttribute) {

            DateRuleException dare = new DateRuleException();
            AlphaNumberRuleException anre = new AlphaNumberRuleException();
            LengthRuleException lre = new LengthRuleException();
            TimeRuleException tre = new TimeRuleException();

            // length validation
            lre.validate(_eanAttr, _strValue);
            if (lre.getErrorCount() > 0) {
                throw lre;
            }

            // date validation
            dare.validate(_eanAttr, _strValue);
            if (dare.getErrorCount() > 0) {
                throw dare;
            }

            //time validation
            tre.validate(_eanAttr, _strValue);
            if (tre.getErrorCount() > 0) {
                throw tre;
            }

            // All Other checks go here Upper.. etc

            anre.validate(_eanAttr, _strValue);
            if (anre.getErrorCount() > 0) {
                throw anre;
            }

        } else if (_eanAttr instanceof EANFlagAttribute) {

            // state transition validation
            if (_eanAttr instanceof StatusAttribute) {
                StateTransitionException ste = new StateTransitionException();
                ste.validate(_eanAttr, _strValue);
                if (ste.getErrorCount() > 0) {
                    throw ste;
                }
            }
        }
    }

    /**
     * cloneEntityItems
     * Returns a cloned group of entity items in a new EntityGroup
     *
     * The assumption is the EntityItems need an existing EntityGroup
     * and that they are all of the same EntityType right now
     *
     * The real trick is to use either a Database Object
     * or a remote database connection to fetch the appropriate
     * EntityGroup(s) given the entityitems.
     *
     * @param _el
     * @param _prof
     * @param _rdi
     * @param _aei
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @return
     *  @author David Bigelow
     */
    public static EntityGroup cloneEntityItems(EntityList _el, Profile _prof, RemoteDatabaseInterface _rdi, EntityItem[] _aei) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

        String strEntityType = null;
        EntityGroup egReturn = null;
        EntityGroup egChild = null;

        boolean bShiftEnabled = false;

        T.est(_aei != null, "cloneEntityItems:1 passed EntityItem  array is null");
        T.est(_rdi != null, "cloneEntityItems:2 passed Remote Database Interface is null");
        T.est(_prof != null, "cloneEntityItems:3 passed _prof is null");
        T.est(_aei.length > 0, "cloneEntityItems:4 and empty EntityItem array was passed");

        if (_aei.length == 0) {
            return null;
        }

        strEntityType = _aei[0].getEntityType();
        egReturn = _rdi.getEntityGroup(_prof, strEntityType, "Navigate");
        T.est(egReturn != null, "cloneEntityItems:5 cannot derive the proper entityGroup");
        _el.putEntityGroup(egReturn);
        egReturn.setParent(_el);

        // We should  be good to go

        bShiftEnabled = egReturn.isRelator() || egReturn.isAssoc();

        // If we need to look at child data.. we need to get an new EntityGroup
        if (bShiftEnabled) {
            egChild = _rdi.getEntityGroup(_prof, egReturn.getEntity2Type(), "Navigate");
            _el.putEntityGroup(egChild);
            egChild.setParent(_el);
        }

        for (int i = 0; i < _aei.length; i++) {
            EntityItem eiClone = new EntityItem(egReturn, _aei[i]);
            egReturn.putEntityItem(eiClone);
            if (bShiftEnabled) {
                EntityItem eiD = (EntityItem) _aei[i].getDownLink(0);
                EntityItem eiChild = new EntityItem(egChild, eiD);
                egChild.putEntityItem(eiChild);
                eiClone.putDownLink(eiChild);
            }
        }

        return egReturn;
    }

    private static EntityItem[] getEntities(EntityList _el, String _strEntityType) {
        Vector v = new Vector();
        int size = 0;
        EntityItem[] aeiReturn = null;

        for (int i = 0; i < _el.getEntityGroupCount(); i++) {
            EntityGroup eg = _el.getEntityGroup(i);
            if (eg.getEntityType().equals(_strEntityType)) {
                for (int j = 0; j < eg.getEntityItemCount(); j++) {
                    EntityItem ei = eg.getEntityItem(j);
                    v.addElement(ei);
                }
            }
        }

        size = v.size();
        aeiReturn = new EntityItem[size];
        for (int i = 0; i < size; i++) {
            aeiReturn[i] = (EntityItem) v.elementAt(i);
        }
        return aeiReturn;
    }

    private static void getCountryGENERALAREA(Database _db, Profile _prof, EntityItem _ei, ExtractActionItem _xai, EANList _eiList) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

        EntityItem[] aei = new EntityItem[1];
        EntityList el = null;
        EntityItem[] aeiGA = null;

        String strTraceBase = "EANUtility getCountriesList method ";
        String strEntityType = _ei.getEntityType();
        int iEntityID = _ei.getEntityID();

        _db.test(strEntityType.equals("GENERALAREA"), strTraceBase + "GENERALAREA Entity only.");
        _db.test(iEntityID > 0, "EntityID < 0.");

        aei[0] = _ei;
        el = EntityList.getEntityList(_db, _prof, _xai, aei);
        aeiGA = getEntities(el, "GENERALAREA");

        for (int i = 0; i < aeiGA.length; i++) {
            EntityItem ei = aeiGA[i];
            EANAttribute att = ei.getAttribute("GENAREATYPE");
            if (att != null) {
                MetaFlag[] amf = (MetaFlag[]) att.get();
                for (int f = 0; f < amf.length; f++) {
                    if (amf[f].isSelected()) {
                        if (amf[f].getLongDescription().equals("Country")) {
                            _eiList.put(ei);
                        } else {
                            getCountryGENERALAREA(_db, _prof, ei, _xai, _eiList);
                        }
                    }
                }
            }
        }

    }

    /**
     * rgetListOfCountriesForAVAIL
     *
     * @param _rdi
     * @param _prof
     * @param _ei
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @return
     *  @author David Bigelow
     */
    public static String[] rgetListOfCountriesForAVAIL(RemoteDatabaseInterface _rdi, Profile _prof, EntityItem _ei) throws MiddlewareException, MiddlewareRequestException, RemoteException, MiddlewareShutdownInProgressException {
        return _rdi.getListOfCountriesForAVAIL(_prof, _ei);
    }

    /**
     * getListOfCountriesForAVAIL
     *
     * @param _db
     * @param _prof
     * @param _ei
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @return
     *  @author David Bigelow
     */
    public static String[] getListOfCountriesForAVAIL(Database _db, Profile _prof, EntityItem _ei) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

        ExtractActionItem xai1 = null;
        ExtractActionItem xai2 = null;
        ExtractActionItem xai3 = null;
        EntityList el = null;
        EntityItem[] aeiGA = null;

        int size = 0;


        EANList eiGeaInclList = new EANList();
        EANList eiGeaExclList = new EANList();
        EANList eiList = new EANList();
        EntityItem[] aei = new EntityItem[1];

        String strTraceBase = "EANUtility getCountriesList method ";
        String[] aReturn = null;
        String strEntityType = _ei.getEntityType();
        int iEntityID = _ei.getEntityID();

        //c_elEntityItems = new EANList();

        _db.test(strEntityType.equals("AVAIL"), strTraceBase + "AVAIL Entity only.");
        _db.test(iEntityID > 0, "EntityID < 0.");

        aei[0] = _ei;

        //get GENERALAREA that match AVAIL's GENAREANAMEINCL
        xai1 = new ExtractActionItem(null, _db, _prof, "EXTAVAILGENAREAA1");
        _db.test(xai1 != null, strTraceBase + "ExtractActionItem EXTAVAILGENAREA1 is null.");

        xai2 = new ExtractActionItem(null, _db, _prof, "EXTGENERALAREA1");
        _db.test(xai2 != null, strTraceBase + "ExtractActionItem EXTGENERALAREA1 is null.");

        el = EntityList.getEntityList(_db, _prof, xai1, aei);
        aeiGA = getEntities(el, "GENERALAREA");

        eiGeaInclList = new EANList();
        for (int i = 0; i < aeiGA.length; i++) {
            EntityItem ei = aeiGA[i];
            //      D.ebug(D.EBUG_SPEW,"i: " + i + ", " + ei.dump(false));
            EANAttribute att = ei.getAttribute("GENAREATYPE");
            if (att != null) {
                MetaFlag[] amf = (MetaFlag[]) att.get();
                for (int f = 0; f < amf.length; f++) {
                    if (amf[f].isSelected()) {
                        if (amf[f].getLongDescription().equals("Country")) {
                            eiGeaInclList.put(ei);
                        } else {
                            getCountryGENERALAREA(_db, _prof, ei, xai2, eiGeaInclList);
                        }
                    }
                }
            }
        }

        //get GENERALAREA entities that match AVAIL's GENAREANAMEEXCL
        xai3 = new ExtractActionItem(null, _db, _prof, "EXTAVAILGENAREAA2");
        _db.test(xai3 != null, strTraceBase + "ExtractActionItem EXTAVAILGENAREA2 is null.");

        el = EntityList.getEntityList(_db, _prof, xai3, aei);
        aeiGA = getEntities(el, "GENERALAREA");

        for (int i = 0; i < aeiGA.length; i++) {
            EntityItem ei = aeiGA[i];
            EANAttribute att = ei.getAttribute("GENAREATYPE");
            if (att != null) {
                MetaFlag[] amf = (MetaFlag[]) att.get();
                for (int f = 0; f < amf.length; f++) {
                    if (amf[f].isSelected()) {
                        if (amf[f].getLongDescription().equals("Country")) {
                            eiGeaExclList.put(ei);
                        } else {
                            getCountryGENERALAREA(_db, _prof, ei, xai2, eiGeaExclList);
                        }
                    }
                }
            }
        }

        // get the list of GENERALAREA entities =  Incl - Excl
        for (int i = 0; i < eiGeaInclList.size(); i++) {
            EntityItem ei = (EntityItem) eiGeaInclList.getAt(i);
            if (eiGeaExclList.get(ei.getKey()) == null) {
                eiList.put(ei);
            }
        }

        size = eiList.size();
        aReturn = new String[size];
        for (int i = 0; i < size; i++) {
            EntityItem ei = (EntityItem) eiList.getAt(i);
            EANAttribute att = ei.getAttribute("GENAREANAME");
            if (att != null) {
                MetaFlag[] amf = (MetaFlag[]) att.get();
                for (int f = 0; f < amf.length; f++) {
                    if (amf[f].isSelected()) {
                        aReturn[i] = amf[f].getLongDescription();
                        break;
                    }
                }
            }
        }
        return aReturn;
    }

    /**
     * deactivateEntity
     *
     * @param _db
     * @param _prof
     * @param _ei
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     *  @author David Bigelow
     */
    public static void deactivateEntity(Database _db, Profile _prof, EntityItem _ei) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
        String strTraceBase = " EANUtility deactivateEntity method";
        // Initialize some SP specific objects needed in this method
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
        ReturnStatus returnStatus = new ReturnStatus(-1);

        // Placeholders for dates
        String strNow = null;
       // String strForever = null;
       // String strEndOfDay = null;
        String strEntityType = null;

        int iEntityID = 0;

        // Pull some profile info...
        int iOPWGID = _prof.getOPWGID();
        int iTranID = _prof.getTranID();
        int iSessionID = _prof.getSessionID();
        String strEnterprise = _prof.getEnterprise();
		int iNLSID = _prof.getReadLanguage().getNLSID();
        try {

            DatePackage dpNow = _db.getDates();
            strNow = dpNow.getNow();
            //strForever = dpNow.getForever();
            //strEndOfDay = dpNow.getEndOfDay();

            _db.debug(D.EBUG_DETAIL, strTraceBase);
            _db.test(_ei != null, strTraceBase + " EntityItem _ei is null.");

            // OK.. we will now deactivate the entity item
            strEntityType = _ei.getEntityType();
            iEntityID = _ei.getEntityID();

            _db.debug(D.EBUG_DETAIL, strTraceBase + " Deactivating Entity: " + strEntityType + ":" + iEntityID);

            // here is where we perform some expiring of entities/relators this entity/relator points to, and things
            // that point to this entity/relator. In the case of a relator pointing to a relator, we will recurse.
            expireRelatorReferences(_db, returnStatus, strEnterprise, iOPWGID, strEntityType, iEntityID, iTranID, 0);

            // delete its attributes
            _db.callGBL2968(returnStatus, strEnterprise, strEntityType, iEntityID, iOPWGID, iTranID);
            _db.commit();
            _db.freeStatement();
            _db.isPending();

            // delete it from relator table
            try {
                rs = _db.callGBL2933(returnStatus, strEnterprise, strEntityType, iEntityID, iOPWGID, strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs != null){
            		rs.close();
                	rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            if (rdrs.size() > 0) {
                _db.callGBL2099(returnStatus, iOPWGID, strEnterprise, strEntityType, iEntityID, iTranID);
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

            // delete it from entity table
            _db.callGBL2092(returnStatus, iOPWGID, iSessionID, strEnterprise, strEntityType, new ReturnID(iEntityID), iTranID, strNow, strNow, iNLSID);
            _db.commit();
            _db.freeStatement();
            _db.isPending();

        } finally {
            _db.freeStatement();
            _db.isPending();
            _db.commit();
        }
    }

/**
 * Here is where we perform some expiring of entities/relators this entity/relator points to, and things
 * that point to this entity/relator. In the case of a relator pointing to a relator, we will recurse.
 */
    public static void expireRelatorReferences(Database _db, ReturnStatus _returnStatus, String _strEnterprise, int _iOPWGID, String _strEntityType, int _iEntityID, int _iTranID, int _iLvl) throws SQLException, MiddlewareException {

        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;

	    // let's get back the answer of what we turned off in the form of an EntityType/EntityID...
		rs = _db.callGBL2266(_returnStatus, _strEnterprise, _iOPWGID, _strEntityType, _iEntityID, _iTranID);
		rdrs = new ReturnDataResultSet(rs);
		rs.close();
		rs = null;
		_db.commit();
		_db.freeStatement();
		_db.isPending();

		// now go through all relators we expired, and recursively whack their references IF itsa T-Bone.
		for(int iii = 0; iii < rdrs.getRowCount(); iii++) {
		    String strRelType = rdrs.getColumn(iii,0);
			int iRelID = rdrs.getColumnInt(iii,1);
			_db.debug(D.EBUG_SPEW,"GBL2266 answer:" + strRelType + ":" + iRelID + ":Lvl" + _iLvl);
			expireRelatorReferences(_db,_returnStatus,_strEnterprise,_iOPWGID, strRelType, iRelID, _iTranID, ++_iLvl);
		}
	}

    /*
    * This method returns an array of VE Lock Owner for a given entity item
    */
    /**
     * getVELockOwners
     *
     * @param _db
     * @param _prof
     * @param _ei
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @return
     *  @author David Bigelow
     */
    public static String[] getVELockOwners(Database _db, Profile _prof, EntityItem _ei) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {

        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
        ReturnStatus returnStatus = new ReturnStatus(-1);
        String[] aReturn = null;
        Vector v = null;

        // Pull some profile info...
        String strEnterprise = _prof.getEnterprise();
        String strEntityType = _ei.getEntityType();
        int iEntityID = _ei.getEntityID();
        
        try {
            rs = _db.callGBL2015(returnStatus, strEnterprise, strEntityType, iEntityID);
            rdrs = new ReturnDataResultSet(rs);
        } finally {
        	if (rs != null){
        		rs.close();
        		rs = null;
        	}
            
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
        v = new Vector();
        for (int j = 0; j < rdrs.size(); j++) {
            int iLockType = rdrs.getColumnInt(j, 6);
            String strLockOwner = rdrs.getColumn(j, 10).trim();
            if (iLockType == LockGroup.LOCK_VE) {
                v.addElement(strLockOwner);
            }
        }

        aReturn = new String[v.size()];
        v.copyInto(aReturn);
        return aReturn;
    }

    /**
     * Check set of EntityItems return a matching array of Boolean, true if locked
     * @param _db
     * @param _prof
     * @param _eia
     * @return
     * @throws SQLException
     * @throws MiddlewareException
     * @throws MiddlewareShutdownInProgressException
     */
    public static String[][] checkVELockOwners(Database _db, Profile _prof, EntityItem[] _eia) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {

    	String[][] aReturn = new String[_eia.length][];

        for (int i=0; i<_eia.length; i++){
        	aReturn[i] = getVELockOwners(_db, _prof, _eia[i]);
        }

        return aReturn;
    }
    
    private static boolean isLinkable(VELockERList _list, String[] _aLockOwner, String _strEntityType, String _strRelatorType) {
        for (int i = 0; i < _aLockOwner.length; i++) {
            String strLockOwner = _aLockOwner[i];
            if (_list.test(strLockOwner, _strEntityType, _strRelatorType)) {
                return true;
            }
        }

        return false;
    }

	/**
	 * used to check if Delete/Entity/Relator meta exists and if a specific relator or entity can be deleted
	 * 
LINKTYPE:	Delete/Entity/Relator
LINKTYPE1:	MODELAVAIL
LINKTYPE2:	MODEL
LINKCODE:	STATUS
LINKVALUE:	0020

prohibit the deletion of a relator when parent status is 0020
SG	Delete/Entity/Relator	SVCMODAVAIL	SVCMOD	STATUS	0020	

prohibit the deletion of an entity when status is 0010 or 0020
SG	Delete/Entity/Relator	SVCMOD	SVCMOD	STATUS	0010:0020	

prohibit the deletion of a relator when its status is 0020
SG	Delete/Entity/Relator	IPSCSTRUC	IPSCSTRUC	STATUS	0020	


	 * @param _db
	 * @param _prof
	 * @param m_el
	 * @param eie
	 * @return
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	public static EANList canDelete(Database _db, Profile _prof, EANList m_el,
			EntityItemException eie) throws MiddlewareException, SQLException {
		
        if(m_el.size()==0){
        	return m_el;
        }
        
        // Initialize some SP specific objects needed in this method
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null; 
        ReturnStatus returnStatus = new ReturnStatus(-1); 
        String strEnterprise = _prof.getEnterprise();
        EANList deleteAbleList = new EANList();
        
        try {
        	String entityType = ((EntityItem) m_el.getAt(0)).getEntityType();
			//_db.debug(D.EBUG_SPEW, "gbl8106:parms:" + strEnterprise + ":" + entityType + ":" + _prof.getValOn() + ":" + _prof.getEffOn());
            //Check for Delete/Entity/Relator for an entity type
			rs = _db.callGBL8106(returnStatus, strEnterprise, entityType, _prof.getValOn(), _prof.getEffOn());
            rdrs = new ReturnDataResultSet(rs);
         	_db.debug(D.EBUG_SPEW, "gbl8106:rdrs size: " + rdrs.size());
        } finally {
        	if (rs != null){
        		rs.close();
        		rs = null;
        	}
            
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }

        if (rdrs.size()>0){ // meta was found
        	// may be multiple if dependency is put on relator attr and entity attr
        	//SG	Delete/Entity/Relator	IPSCSTRUC	SVCMOD	STATUS	0020	- dont remove ref if parent status=0020
        	//SG	Delete/Entity/Relator	IPSCSTRUC	IPSCSTRUC	STATUS	0020 - dont remove rel if rel status=0020	

        	// look at each item, it must pass all tests to be deleted
    	    for (int i = 0; i < m_el.size(); i++) {
    	    	EntityItem ei = (EntityItem) m_el.getAt(i);
    	    	boolean exists = false;
        	
    	    	for (int r=0; r<rdrs.size() && !exists; r++){
    	    		String strType = rdrs.getColumn(r, 0).trim(); // Entity or Relator type for value in linktype1
    	    		String strCheckEntityType = rdrs.getColumn(r, 1).trim(); // type from linktype2
    	    		String strAttributeCode = rdrs.getColumn(r, 2).trim(); // linkcode
    	    		String strAttributeValue = rdrs.getColumn(r, 3).trim(); // linkvalue
    	    		_db.debug(D.EBUG_SPEW, "gbl8106 answer is:" + strType + ":" + strCheckEntityType + ":" + strAttributeCode + ":" + strAttributeValue );

    	    		//strAttributeValue may have multiple values val1:val2     	

    	    		String valArray[] = convertToArray(strAttributeValue,":,");// allow ',' delim too
    	    		for (int ii=0; ii<valArray.length; ii++){
    	    			String attrVal = valArray[ii];
    	    			StringBuffer returnSb = new StringBuffer();
    	    			if(doesAttrValueExist(_db, _prof, ei, strType,	strCheckEntityType, 
    	    					strAttributeCode, attrVal,returnSb)){// a matching value was found
    	    				String flagDesc = getFlagDesc(_db, _prof, strAttributeCode, attrVal);
    	    				String attrDesc = getAttrDesc(_db, _prof, strAttributeCode);
    	    				String errmsg = " Unable to "+(strType.equalsIgnoreCase("Relator")?"remove":"delete")+
    	    					" the "+ei.getEntityType()+" "+strType+" because the "+
    	    					returnSb.toString()+" "+attrDesc+" is "+flagDesc;
    	    				if (strCheckEntityType.equals(ei.getEntityType())){ // check is on the current entity, not parent
    	    					errmsg = " Unable to delete the "+
    	    					ei.getEntityType()+" because "+attrDesc+" is "+flagDesc;
    	    				}

    	    				buildErrorMsg(_db, _prof, ei, errmsg,  eie);
    	    				exists = true;
    	    				break;
    	    			}
    	    		}
    	    	}
    	    	if(!exists){
    	    		deleteAbleList.put(ei);
    	    	}
    	    }
        }else{
        	deleteAbleList = m_el; // all can be deleted
        }
        return deleteAbleList;
	}
	private static String getFlagDesc(Database _db, Profile prof, String attrCode, String flagCode) 
	throws MiddlewareException, SQLException
	{
		String flagDesc = flagCode;
		ReturnStatus returnStatus = new ReturnStatus(-1);
		ResultSet rs = null;
		ReturnDataResultSet rdrs = null;
		// Get the description
		try {
			rs = _db.callGBL8612(returnStatus, prof.getOPWGID(), prof.getEnterprise().trim(), attrCode, 
					prof.getReadLanguage().getNLSID(), prof.getValOn(), prof.getEffOn());
			rdrs = new ReturnDataResultSet(rs);
		} finally {
			if (rs!=null){
				rs.close();
				rs = null;
			}
			_db.commit();
			_db.freeStatement();
			_db.isPending();
		}

		for (int ii = 0; ii < rdrs.size(); ii++) {
			String s1 = rdrs.getColumn(ii, 0);			//flagCode
			//int i2 = rdrs.getColumnInt(ii, 1);		//nlsID
			String s3 = rdrs.getColumn(ii, 2);			//longDescription
			//String s4 = rdrs.getColumn(ii, 3);		//shortDescription
			//String s5 = rdrs.getColumn(ii, 4);		//setExpired
			if (s1.equals(flagCode)){
				flagDesc = s3;
				break;
			}
		}

		return flagDesc;
	}
	private static String getAttrDesc(Database _db, Profile prof, String attrCode) 
	throws MiddlewareException, SQLException
	{
		String attrDesc = attrCode;
		ReturnStatus returnStatus = new ReturnStatus(-1);
		ResultSet rs = null;
		// Get the description
		try {
		    rs = _db.callGBL8610(returnStatus, prof.getEnterprise().trim(), prof.getRoleCode().trim(), attrCode, 
		    		prof.getReadLanguage().getNLSID(), prof.getValOn(), prof.getEffOn());
		
		    if (rs.next()) { 
		      //  String s1 = rs.getString(1).trim(); //attribute type - EntityClass
		      //  String s2 = rs.getString(2).trim(); // capability
		      //  int i3 = rs.getInt(3); // nlsid
		      //  String s4 = rs.getString(4).trim(); // short desc
		    	attrDesc = rs.getString(5).trim(); // long desc
		      //  String s6 = rs.getString(6).trim(); //ValFrom
		      //  String s7 = rs.getString(7).trim();  //ValTo
		    }
		} finally {
			if (rs!=null){
				rs.close();
				rs = null;
			}
			_db.commit();
			_db.freeStatement();
			_db.isPending();
		}

		return attrDesc;
	}

	/**
	 * SR11, SR15 and SR17.
	 * 		Restrict Referencing data if Status is Final to ensure changes are fed downstream. 
	 * used to check if Create/Relator exists and if a specific entity can be linked
LINKTYPE:	Create/Relator
LINKTYPE1:	MODELAVAIL
LINKTYPE2:	Both|Parent|Child|Either  (Both is AND condition, Either is OR condition)
LINKCODE:	STATUS=0020  parent|child attr and value, if matches then cannot link  - use this if Parent|Child
LINKVALUE:	STATUS=0020  child attr and value, if matches then cannot link - use this if Both|Either

	 * @param _db
	 * @param _prof
	 * @param relType
	 * @param _eiParent
	 * @param _eiChild
	 * @param eie
	 * @param rdrsTbl - just for performance
	 * @return
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	public static boolean canCreateRelator(Database _db, Profile _prof, String relType, EntityItem _eiParent,
			EntityItem _eiChild, EntityItemException eie, Hashtable rdrsCRTbl) 
	throws MiddlewareException, SQLException 
	{

        boolean canLink = true;
        ReturnDataResultSet	rdrs = (ReturnDataResultSet)rdrsCRTbl.get(relType);
        if (rdrs==null){
        	rdrs= getCreateRelatorMeta(_db, _prof,relType);
        	rdrsCRTbl.put(relType,rdrs);
        }
        _db.debug(D.EBUG_SPEW, "EANUtility.canCreateRelator:rdrs size: " + rdrs.size());

        if (rdrs.size()>0){ // meta was found
        	String checkType = rdrs.getColumn(0, 0).trim(); // linktype2 = Parent|Child|Both|Either
        	String attrAndValue1 = rdrs.getColumn(0, 1).trim(); //linkcode = STATUS=0020 
        	String attrAndValue2 = rdrs.getColumn(0, 2).trim(); //linkvalue = STATUS=0020 maybe =0 child if Both or Either
     
        	_db.debug(D.EBUG_SPEW, "EANUtility.getCreateRelatorMeta answer is:" + checkType + ":" + attrAndValue1 + ":" + attrAndValue2 );

        	String strAttributeCode="";
        	String strAttributeValue = "";
      
        	if(checkType.equalsIgnoreCase("Parent")){
        		// prevent link if parent attribute has the specified value
        		String array[] = convertToArray(attrAndValue1, "=");
        		strAttributeCode = array[0];
        		strAttributeValue = array[1];
        	  	StringBuffer returnSb = new StringBuffer();
        		if(doesAttrValueExist(_db, _prof, _eiParent, "Entity",	_eiParent.getEntityType(), 
            			strAttributeCode, strAttributeValue,returnSb)) {
        			String flagDesc = getFlagDesc(_db, _prof, strAttributeCode, strAttributeValue);
    				String attrDesc = getAttrDesc(_db, _prof, strAttributeCode);
            		canLink= false;
            		buildErrorMsg(_db, _prof,
            				_eiParent, 
            				//"Unable to link because of "+_eiParent.getEntityType()+" Status (ok)",  eie);
            				"Unable to link because the "+returnSb.toString()+" "+attrDesc+" is "+flagDesc+" (ok)",  eie);
            		
            		buildErrorMsg(_db, _prof,
            				_eiChild, 
            				//"Unable to link because of "+_eiParent.getEntityType()+" Status (ok)",  eie);
            				"Unable to link because the "+returnSb.toString()+" "+attrDesc+" is "+flagDesc+" (ok)",  eie);
            		
            		_db.debug(D.EBUG_SPEW, "EANUtility.canCreateRelator Unable to link because of parent status");
            	}
        	}else if(checkType.equalsIgnoreCase("Child")){
        		// prevent link if child attribute has the specified value
        		String array[] = convertToArray(attrAndValue1, "=");
        		strAttributeCode = array[0];
        		strAttributeValue = array[1];
        	  	StringBuffer returnSb = new StringBuffer();
        		if(doesAttrValueExist(_db, _prof, _eiChild, "Entity",_eiChild.getEntityType(), 
            			strAttributeCode, strAttributeValue,returnSb)) {
            		canLink= false;
            		String flagDesc = getFlagDesc(_db, _prof, strAttributeCode, strAttributeValue);
    				String attrDesc = getAttrDesc(_db, _prof, strAttributeCode);
               		buildErrorMsg(_db, _prof,
               				_eiParent, 
               				//"Unable to link because of "+_eiChild.getEntityType()+" Status (ok)",  eie);
               				"Unable to link because the "+returnSb.toString()+" "+attrDesc+" is "+flagDesc+" (ok)",  eie);
              		buildErrorMsg(_db, _prof,
            				_eiChild, 
            				//"Unable to link because of "+_eiChild.getEntityType()+" Status (ok)",  eie);
              				"Unable to link because the "+returnSb.toString()+" "+attrDesc+" is "+flagDesc+" (ok)",  eie);
            		
            		_db.debug(D.EBUG_SPEW, "EANUtility.canCreateRelator Unable to link because of child status");
            	}
        	}else{
        		// get the first attribute and value from linkcode, must be parent when either or both is specified
        		String array[] = convertToArray(attrAndValue1, "=");
        		if (array.length!=2){
        			throw new MiddlewareRequestException("Invalid meta specfied for 'Create/Relator'");
        		}
        		strAttributeCode = array[0];
        		strAttributeValue = array[1];
        	  	StringBuffer preturnSb = new StringBuffer();
        		boolean parentHasAttr = doesAttrValueExist(_db, _prof, _eiParent, "Entity",	_eiParent.getEntityType(), 
            			strAttributeCode, strAttributeValue,preturnSb);
        		// get the second attribute and value from linkcode, must be child when either or both is specified
        		array = convertToArray(attrAndValue2, "=");
        		if (array.length!=2){
        			throw new MiddlewareRequestException("Invalid meta specfied for 'Create/Relator'");
        		}
         		strAttributeCode = array[0];
         		strAttributeValue = array[1];
         	 	StringBuffer creturnSb = new StringBuffer();
        		boolean childHasAttr = doesAttrValueExist(_db, _prof, _eiChild, "Entity",_eiChild.getEntityType(), 
            			strAttributeCode, strAttributeValue,creturnSb);
            	if(checkType.equalsIgnoreCase("Both")){
            		// prevent link if child attribute AND parent attribute has the specified value
            		if(parentHasAttr && childHasAttr){
            			canLink= false;
            			String flagDesc = getFlagDesc(_db, _prof, strAttributeCode, strAttributeValue);
        				String attrDesc = getAttrDesc(_db, _prof, strAttributeCode);
            			buildErrorMsg(_db, _prof,
        						_eiParent, 
        						//"Unable to link because of "+_eiParent.getEntityType()+" Status (ok)",  eie);
            					"Unable to link because the "+preturnSb.toString()+" "+attrDesc+" is "+flagDesc+" (ok)",  eie);
            			buildErrorMsg(_db, _prof,
                				_eiChild, 
                				//"Unable to link because of "+_eiChild.getEntityType()+" Status (ok)",  eie);
            					"Unable to link because the "+creturnSb.toString()+" "+attrDesc+" is "+flagDesc+" (ok)",  eie);
            			
            			_db.debug(D.EBUG_SPEW, "EANUtility.canCreateRelator Unable to link because of parent and child status");
            		}
            	}else if(checkType.equalsIgnoreCase("Either")){
               		// prevent link if child attribute OR parent attribute has the specified value
            		if(parentHasAttr || childHasAttr){
            			canLink= false;
            			String flagDesc = getFlagDesc(_db, _prof, strAttributeCode, strAttributeValue);
        				String attrDesc = getAttrDesc(_db, _prof, strAttributeCode);
            			if (parentHasAttr){
            				buildErrorMsg(_db, _prof,
            						_eiParent, 
            						//"Unable to link because of "+_eiParent.getEntityType()+" Status (ok)",  eie);
            						"Unable to link because the "+preturnSb.toString()+" "+attrDesc+" is "+flagDesc+" (ok)",  eie);
            				buildErrorMsg(_db, _prof,
            						_eiChild, 
            						//"Unable to link because of "+_eiParent.getEntityType()+" Status (ok)",  eie);
            						"Unable to link because the "+preturnSb.toString()+" "+attrDesc+" is "+flagDesc+" (ok)",  eie);
            				_db.debug(D.EBUG_SPEW, "EANUtility.canCreateRelator Unable to link because of parent status");
            			}
            			if (childHasAttr){
            				buildErrorMsg(_db, _prof,
            						_eiParent, 
            						//"Unable to link because of "+_eiChild.getEntityType()+" Status (ok)",  eie);
            						"Unable to link because the "+creturnSb.toString()+" "+attrDesc+" is "+flagDesc+" (ok)",  eie);
            				buildErrorMsg(_db, _prof,
                    				_eiChild, 
                    				//"Unable to link because of "+_eiChild.getEntityType()+" Status (ok)",  eie);
                    				"Unable to link because the "+creturnSb.toString()+" "+attrDesc+" is "+flagDesc+" (ok)",  eie);
            				_db.debug(D.EBUG_SPEW, "EANUtility.canCreateRelator Unable to link because of child status");
            			}
            		}
            	}
        	}        	
        }
        return canLink;
	}
	/**********
	 * try to get more information into the message
	 * @param _db
	 * @param _prof
	 * @param ei
	 * @param msg
	 * @param eie
	 */
	public static void buildErrorMsg(Database _db, Profile _prof,
			EntityItem ei, String msg, EntityItemException eie)  
	{
		
		if(eie.containsKey(ei.getKey())){ // prevent wasted instantiation, ei and msg were already added
			return;
		}
		
		try{
			if (ei.getLongDescription().trim().length()==0){
				// does an entitygroup already exist for this type
				EntityGroup parent = null;
				int errCnt = eie.getErrorCount();
				for(int i=0;i<errCnt;i++){
					if(eie.getObject(i) instanceof EntityItem){
						EntityItem item = (EntityItem)eie.getObject(i);
						if(item.getEntityType().equals(ei.getEntityType())){
							if(item.getEntityGroup()!=null){
								parent = item.getEntityGroup();
								break;
							}
						}
					}
				}
				
				if(parent==null){
					parent = new EntityGroup(null,_db,_prof,ei.getEntityType(),"Navigate");
				}
				ei = new EntityItem(parent, _prof, _db, ei.getEntityType(), ei.getEntityID());
			}
		}catch(Exception exc){
			exc.printStackTrace();
		}

		eie.add(ei,msg);
	}
	/********************************************************************************
	 * Convert the string into an array.  The string is a list of values delimited by
	 * Delimiter
	 *
	 * @param data String
	 * @returns String[] one delimited string per element
	 */
	public static String[] convertToArray(String data, String delim)
	{
		Vector vct = new Vector();
		String array[] = null;
		// parse the string into substrings
		if (data!=null)
		{
			java.util.StringTokenizer st = new java.util.StringTokenizer(data,delim);
			while(st.hasMoreTokens()) {
				vct.addElement(st.nextToken());
			}
		}
		array= new String[vct.size()];
		vct.copyInto(array);
		return array;
	}

	/**
	 * look for any Create/Relator meta for specified relatortype
	 * check to see if link is allowed SR11, SR15 and SR17.
	 * @param _db
	 * @param _prof
	 * @param relatorType
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private static ReturnDataResultSet getCreateRelatorMeta(Database _db, Profile _prof,String relatorType)
	throws SQLException, MiddlewareException
	{
		ReturnDataResultSet rdrs = null;
		ResultSet rs = null;
    	StringBuffer sb = new StringBuffer();
    	sb.append("select linktype2,linkcode,linkvalue from opicm.metalinkattr where ");		
    	sb.append("enterprise = '" + _prof.getEnterprise() + "'");
    	sb.append(" and linktype = 'Create/Relator' and linktype1='" + relatorType + "'");
    	sb.append(" and ValFrom <= '"+_prof.getValOn()+"' and '"+_prof.getValOn()+"'  < ValTo");
    	sb.append(" and EffFrom <= '"+_prof.getEffOn()+"' and '"+_prof.getEffOn()+"' < EffTo with ur");

    	_db.debug(D.EBUG_SPEW, "EANUtility.getCreateRelatorMeta: executing: "+sb);
    	
    	try {
    		Connection con = _db.getPDHConnection();
    		Statement stmt = con.createStatement();
    		rs = stmt.executeQuery(sb.toString());
    		rdrs = new ReturnDataResultSet(rs);
    	} finally {
    		if (rs != null){
    			rs.close();
    			rs = null;
    		}

    		_db.commit();
    		_db.freeStatement();
    		_db.isPending();
    	}
		
		return rdrs;
	}
	/*********************
	 * Used to check if a specific attribute value exists for the specified entity
	 * used by canDelete() and canCreateRelator()
	 * 
	 * Delete/Entity/Relator   SVCMOD  SVCMOD  LIFECYCLE   LF03:LF04:LF05:LF06
	 * first one is item to be deleted, 2nd is item to check, 3rd is attr, 4th is list of attr values
	 * 
	 * @param _db
	 * @param _prof
	 * @param _ei
	 * @param strType - canCreateRelator() always passes 'Entity' because checktype == ei.entitytype
	 * @param strCheckEntityType
	 * @param strAttributeCode
	 * @param strAttributeValue
	 * @return
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private static boolean doesAttrValueExist(Database _db, Profile _prof, EntityItem _ei,String strType,
			String strCheckEntityType, String strAttributeCode, String strAttributeValue,StringBuffer returnSb) 
	throws MiddlewareException, SQLException 
	{
		boolean exists = false;
        // Initialize some SP specific objects needed in this method
        ReturnDataResultSet rdrs2 = null;
        ResultSet rs = null;
        ReturnStatus returnStatus = new ReturnStatus(-1); 
        String strEnterprise = _prof.getEnterprise();
        try {
        	int icase=0;
         	if (strCheckEntityType.equals(_ei.getEntityType())) {
        		// look for attribute value on the entity itself
         		//Delete/Entity/Relator   SVCMOD  SVCMOD  LIFECYCLE   LF03:LF04:LF05:LF06
         		//Delete/Entity/Relator   IPSCSTRUC   IPSCSTRUC   LIFECYCLE   LF03:LF04:LF05:LF06 
         		icase = 3;
        	} else {
        		if (strType.charAt(0) == 'E') {
        			// entity
        			//checks for attribute value on child if parent is to be deleted
        			//checks for attribute value on parent if child is to be deleted
        			//Delete/Entity/Relator   TAXCATG SVCMOD  STATUS  0020 
        			
        			//checks for attribute value on relator if parent is to be deleted
        			//Delete/Entity/Relator   SVCMOD  IPSCSTRUC   LIFECYCLE   LF03:LF04:LF05:LF06 
        			icase = 1;
        		} else if (strType.charAt(0) == 'R') {
        			// relator is item that will be deleted - both child and parent will be checked for a match on checkentitytype and attr
        			//Delete/Entity/Relator   LSEOAVAIL   LSEO    STATUS  0020    
        			icase = 2;
        		}
        	}
         	
         	rs = _db.callGBL8107(returnStatus, strEnterprise, _ei.getEntityType(), _ei.getEntityID(), strCheckEntityType, strAttributeCode, strAttributeValue, icase, _prof.getValOn(), _prof.getEffOn());
         	rdrs2 = new ReturnDataResultSet(rs);
         	exists=rdrs2.size() > 0;
         	_db.debug(D.EBUG_SPEW, "gbl8107 rdrs.size() "+rdrs2.size());
         	//rdrs2.display(System.err);
         	if(rdrs2.size()>0){
         		returnSb.append(rdrs2.getColumn(0, 0).trim());
         		returnSb.append(rdrs2.getColumn(0, 1).trim());
         	}
        } finally {
        	if (rs != null){
        		rs.close();
        		rs = null;
        	}

        	_db.commit();
        	_db.freeStatement();
        	_db.isPending();
        }

        return exists;
	}

    /**
     * validate
     *
     * @param _eanAttr
     * @param _strValue
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     *  @author Joan Tran
     */
    public static void validate(EANMetaAttribute _eanMA, MetaFlagMaintItem _mi, String _strValue) throws EANBusinessRuleException {
		D.ebug(D.EBUG_SPEW,"EANUtility validate " + _eanMA.getKey() + ":" + _mi.getKey() + ":" + _strValue);
        AlphaNumberRuleException anre = new AlphaNumberRuleException();
        LengthRuleException lre = new LengthRuleException();

        // length validation
        lre.validate(_eanMA, _mi, _strValue);
        if (lre.getErrorCount() > 0) {
            throw lre;
        }


        // All Other checks go here Upper.. etc
        anre.validate(_eanMA, _mi, _strValue);
        if (anre.getErrorCount() > 0) {
           throw anre;
        }
    }

	public static void populateDefaultValues(Database _db, Profile _prof, EntityGroup eg, String _strEntityType, int _iEntityID) {
		try {
			if(eg ==null){
    			eg = new EntityGroup(null, _db, _prof, _strEntityType, "Edit", true);
    			eg.enforceWorkGroupDomain(_db, _prof);
    		}
			EntityItem ei = new EntityItem(eg, _prof, _db, _strEntityType, _iEntityID);
			ei.refreshDefaults();
			ei.commit(_db, null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
    /**
     * reduce memory needs by reusing objects returned from queries
     * @param memTbl - holds previous objects
     * @param tmp - string returned from query
     * @return object matching the string
     */
    public static String reuseObjects(java.util.Hashtable memTbl, String tmp){
    	if(tmp==null){
    		return null;
    	}
    	//attempt to use less memory, hang onto strings already found and reuse the references
    	String prevkey = (String)memTbl.get(tmp);
    	if(prevkey==null){
    		prevkey = tmp;
    		memTbl.put(tmp, tmp);
    	}
    	return prevkey;
    }
    public static void showMemoryUsage(Object obj, String info){
    	if (obj ==null){
            System.err.println("showMemoryUsage: "+info+" was null ");
            return;
    	}
        try {
            byte[] byteArray = null;
            ByteArrayOutputStream BAout = null;
            ObjectOutputStream Oout = null;
            //put object into stream
            try {
                BAout = new ByteArrayOutputStream();
                Oout = new ObjectOutputStream(BAout);
                Oout.writeObject(obj);
                Oout.flush();
                Oout.reset();
            }
            finally {
            	if(Oout!=null){
            		Oout.close();
            	}
            	if(BAout!=null){
            		BAout.close();
            	}
            }
            byteArray = BAout.toByteArray();
            System.err.println("showMemoryUsage: "+info+" byteArray.len "+byteArray.length);
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
