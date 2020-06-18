//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANCommitableTableTemplate.java,v $
// Revision 1.7  2005/02/14 17:25:16  dave
// Variables and formatting
//
// Revision 1.6  2004/10/21 16:49:53  dave
// trying to share compartor
//
// Revision 1.5  2003/03/13 23:02:02  gregg
// rollback return type void
//
// Revision 1.4  2003/03/13 22:52:50  gregg
// change rollback return type to void
//
// Revision 1.3  2003/03/13 22:51:11  gregg
// rollback
//
// Revision 1.2  2003/03/13 18:45:41  gregg
// remove addColumn, removeColumn methods from EANCommitableTableTemplate interface
//
// Revision 1.1  2003/03/07 22:13:19  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import java.sql.SQLException;
import java.rmi.RemoteException;

/**
* Ensures that an object provides extra facilities for manipulating rows/cols
* AND database commit but still generic enough to accommodate various <CODE>EANObjects</CODE>.
*/
public interface EANCommitableTableTemplate extends EANSimpleTableTemplate {

    /**
     * CLASS_BRAND
     */
    String CLASS_BRAND = "$Id: EANCommitableTableTemplate.java,v 1.7 2005/02/14 17:25:16 dave Exp $";

    ///////////////////////
    // Row methods
    ///////////////////////

    /**
     * addRow
     *
     * @return
     *  @author David Bigelow
     */
    boolean addRow();

    /**
     * removeRow
     *
     * @param _strRowKey
     *  @author David Bigelow
     */
    void removeRow(String _strRowKey);

    ///////////
    // others
    ///////////

    /**
     * Refresh our object from the database.
     *
     * @param _db
     * @param _rdi
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.sql.SQLException 
     */
    void rollback(Database _db, RemoteDatabaseInterface _rdi) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException;
    /**
     * hasChanges
     *
     * @return
     *  @author David Bigelow
     */
    boolean hasChanges();
    /**
     * canEdit
     *
     * @return
     *  @author David Bigelow
     */
    boolean canEdit();
    /**
     * canCreate
     *
     * @return
     *  @author David Bigelow
     */
    boolean canCreate();
    /**
     * commit
     *
     * @param _db
     * @param _rdi
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.sql.SQLException
     *  @author David Bigelow
     */
    void commit(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException;

}
