//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: PDGTemplateActionItem.java,v $
// Revision 1.7  2012/09/05 14:31:06  wendy
// Added getPDGDeleteAction(), makes better use of OOP.
//
// Revision 1.6  2009/05/14 15:04:20  wendy
// Support dereference for memory release
//
// Revision 1.5  2008/06/16 19:30:29  wendy
// MN35609290 fix revealed memory leaks, deref() needed
//
// Revision 1.4  2005/03/11 23:11:11  joan
// back to v1.2
//
// Revision 1.3  2005/03/10 00:17:47  dave
// more Jtest work
//
// Revision 1.2  2003/07/15 19:36:52  joan
// move changes from v111
//

package COM.ibm.eannounce.objects;


import java.sql.SQLException;
import java.rmi.RemoteException;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;


public abstract class PDGTemplateActionItem extends EANActionItem {
 	static final long serialVersionUID = 20011106L;

  	/*
  	* Version info
  	*/

    public PDGTemplateActionItem(EANMetaFoundation  _mf, PDGTemplateActionItem _ai) throws MiddlewareRequestException {
        super(_mf, _ai.getProfile() , _ai.getActionItemKey());
    }

    public PDGTemplateActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
		super(_emf, _db, _prof, _strActionItemKey);
	}

  	public String getVersion() {
  		return "$Id: PDGTemplateActionItem.java,v 1.7 2012/09/05 14:31:06 wendy Exp $";
  	}

  	public void dereference() { super.dereference();}

	public abstract String getEntityType();
	protected abstract void setEntityType (String _str);
	public abstract NavActionItem getPDGNavAction();
	protected abstract void setPDGNavAction(NavActionItem _nai);
	public abstract EditActionItem getPDGEditAction();
	public DeleteActionItem getPDGDeleteAction() { return null; }
	protected abstract void setPDGEditAction(EditActionItem _eai);
	public abstract CreateActionItem getPDGCreateAction();
	protected abstract void setPDGCreateAction(CreateActionItem _cai);
	public abstract void setEntityItem(EntityItem _ei);
    public abstract EntityItem getEntityItem();
	public abstract void resetEntityItem();
	public abstract void exec(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SBRException;
	public abstract void rexec(RemoteDatabaseInterface _rdi, Profile _prof) throws SQLException, MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException, SBRException;
	public abstract void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException;

	public abstract byte[] viewMissingData(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SBRException ;
	public abstract byte[] rviewMissingData(RemoteDatabaseInterface _rdi, Profile _prof) throws SQLException, MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException, SBRException;
	public abstract byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException;

    protected boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {return false;}
}
