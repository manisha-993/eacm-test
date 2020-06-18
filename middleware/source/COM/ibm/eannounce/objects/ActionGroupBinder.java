//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ActionGroupBinder.java,v $
// Revision 1.44  2007/11/06 15:08:29  wendy
// Prevent null ptr in dump()
//
// Revision 1.43  2005/11/04 14:52:09  tony
// VEEdit_Iteration2
// updated VEEdit logic to meet new requirements.
//
// Revision 1.42  2005/08/16 16:20:29  joan
// fix for order
//
// Revision 1.41  2005/02/10 22:38:04  dave
// fix null pointer
//
// Revision 1.40  2005/02/09 22:13:43  dave
// more JTest Cleanup
//
// Revision 1.39  2005/01/10 21:47:48  joan
// work on multiple edit
//
// Revision 1.38  2005/01/05 19:24:07  joan
// add new method
//
// Revision 1.37  2004/06/08 17:51:30  joan
// throw exception
//
// Revision 1.36  2004/06/08 17:28:33  joan
// add method
//
// Revision 1.35  2004/04/09 19:37:16  joan
// add duplicate method
//
// Revision 1.34  2003/08/28 16:28:02  joan
// adjust link method to have link option
//
// Revision 1.33  2003/06/25 18:43:57  joan
// move changes from v111
//
// Revision 1.32.2.2  2003/06/25 00:01:43  joan
// fix compile
//
// Revision 1.32.2.1  2003/06/24 23:37:23  joan
// fix WhereUsedActionItem constructor
//
// Revision 1.32  2003/01/21 00:20:34  joan
// adjust link method to test VE lock
//
// Revision 1.31  2003/01/14 22:05:07  joan
// adjust removeLink method
//
// Revision 1.30  2003/01/08 21:44:03  joan
// add getWhereUsedList
//
// Revision 1.29  2002/10/30 22:39:12  dave
// more cleanup
//
// Revision 1.28  2002/10/30 22:36:18  dave
// clean up
//
// Revision 1.27  2002/10/30 22:02:31  dave
// added exception throwing to commit
//
// Revision 1.26  2002/10/29 00:02:54  dave
// backing out row commit for 1.1
//
// Revision 1.25  2002/10/28 23:49:12  dave
// attempting the first commit with a row index
//
// Revision 1.24  2002/10/18 20:18:51  joan
// add isMatrixEditable method
//
// Revision 1.23  2002/10/09 21:32:55  dave
// added isDynaTable to EANTableWrapper interface
//
// Revision 1.22  2002/09/27 17:10:58  dave
// made addRow a boolean
//
// Revision 1.21  2002/07/16 15:38:19  joan
// working on method to return the array of actionitems
//
// Revision 1.20  2002/07/08 17:53:41  joan
// fix link method
//
// Revision 1.19  2002/07/08 16:05:28  joan
// fix link method
//
// Revision 1.18  2002/06/25 20:36:07  joan
// add create method for whereused
//
// Revision 1.17  2002/06/25 17:49:36  joan
// add link and removeLink methods
//
// Revision 1.16  2002/06/19 15:52:18  joan
// work on add column in matrix
//
// Revision 1.15  2002/06/17 23:53:46  joan
// add addColumn method
//
// Revision 1.14  2002/06/05 22:18:19  joan
// work on put and rollback
//
// Revision 1.13  2002/06/05 16:28:48  joan
// add getMatrixValue method
//
// Revision 1.12  2002/05/30 22:49:52  joan
// throw MiddlewareBusinessRuleException when committing
//
// Revision 1.11  2002/05/08 19:56:40  dave
// attempting to throw the BusinessRuleException on Commit
//
// Revision 1.10  2002/04/24 18:04:37  joan
// add removeRow method
//
// Revision 1.9  2002/04/02 21:25:49  dave
// added hasChanges()
//
// Revision 1.8  2002/03/27 22:34:20  dave
// Row Selectable Table row Add logic. First attempt
//
// Revision 1.7  2002/03/22 21:26:54  dave
// more syntax cleanup
//
// Revision 1.6  2002/03/08 17:28:28  dave
// minor display fixes
//
// Revision 1.5  2002/02/15 22:24:34  dave
// added row methods
//
// Revision 1.4  2002/02/15 21:22:24  dave
// fixing null pointer for the actionGroupBinder
//
// Revision 1.3  2002/02/14 01:44:25  dave
// more syntax fixes
//
// Revision 1.2  2002/02/14 01:29:06  dave
// syntax fixes
//
// Revision 1.1  2002/02/14 01:21:17  dave
// created a binder for action groups so relator/entity can be shown
//
// Revision 1.8  2002/02/12 23:35:36  dave
// added purpose to the NavActionItem
//
// Revision 1.7  2002/02/12 21:29:19  dave
// for syntax
//
// Revision 1.6  2002/02/12 21:01:58  dave
// added toString methods for diplay help
//
// Revision 1.5  2002/02/12 19:33:35  dave
// fixed the class setting when we instantiate from existing action item
//
// Revision 1.4  2002/02/12 19:18:38  dave
// extra debugging to isolate Class problem for action item
//
// Revision 1.3  2002/02/09 21:50:41  dave
// more syntax
//
// Revision 1.2  2002/02/09 21:39:34  dave
// syntax fixes
//
// Revision 1.1  2002/02/09 20:48:25  dave
// re-arrannging the ActionItem to abstract a common base
//
// Revision 1.20  2002/02/06 16:18:28  dave
// more syntax
//
// Revision 1.19  2002/02/06 15:23:06  dave
// more fixes to sytax
//
// Revision 1.18  2002/02/06 00:56:12  dave
// more base changes
//
// Revision 1.17  2002/02/02 21:22:55  dave
// more clean up
//
// Revision 1.16  2002/02/02 21:11:06  dave
// fixing more import statements
//
// Revision 1.15  2002/02/02 20:56:53  dave
// tightening up code
//
// Revision 1.14  2002/01/31 22:57:18  dave
// more Foundation Cleanup
//
// Revision 1.13  2002/01/18 23:33:43  dave
// minor syntax
//
// Revision 1.12  2002/01/18 23:24:28  dave
// fixes to ActionItem
//
// Revision 1.11  2002/01/18 22:57:16  dave
// sp change to swap direction and d,u
//
// Revision 1.10  2002/01/17 18:33:53  dave
// misc adjustments to help cloning and copying of structures
// in e-announce
//
// Revision 1.9  2002/01/17 00:16:18  dave
// DB2 rs read fix
//
// Revision 1.8  2002/01/16 23:50:15  dave
// interject first NavigateObject constructor test
//
// Revision 1.7  2002/01/14 22:07:54  dave
// minor abract fixes
//
// Revision 1.6  2002/01/14 21:36:58  dave
// removed astract for now.. and fixed constructor info
//
// Revision 1.5  2002/01/12 00:47:02  dave
// changed set to put for Descriptions
//
// Revision 1.4  2002/01/12 00:37:28  dave
// syntax fixes
//
// Revision 1.3  2002/01/12 00:06:33  dave
// syntax fixes
//
// Revision 1.2  2002/01/11 23:57:39  dave
// misc syntax error fixes
//
// Revision 1.1  2002/01/11 23:45:00  dave
// generated abstract ActionItem object for workflow start
//
//

package COM.ibm.eannounce.objects;

import java.util.*;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.Profile;

// Exceptions
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import java.sql.SQLException;
import java.rmi.RemoteException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.LockException;


/**
 * ActionGroupBinder
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ActionGroupBinder extends EANMetaEntity implements EANTableWrapper {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;
    private ActionGroup m_ag1 = null;
    private ActionGroup m_ag2 = null;

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /*
    * Version info
    */
    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: ActionGroupBinder.java,v 1.44 2007/11/06 15:08:29 wendy Exp $";
    }

    /**
     * ActionGroupBinder
     *
     * @param _mf
     * @param _prof
     * @param _ag1
     * @param _ag2
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public ActionGroupBinder(EANMetaFoundation _mf, Profile _prof, ActionGroup _ag1, ActionGroup _ag2) throws MiddlewareRequestException {
        super(_mf, _prof, _mf.getKey());

        try {
            String strDesc = "";
            m_ag1 = _ag1;
            m_ag2 = _ag2;

            if (m_ag1 != null) {
                strDesc = _ag1.toString();
            }

            if (m_ag2 != null) {
                strDesc = strDesc + (m_ag1 != null ? " - " : "") + _ag2.toString();
            }
            putLongDescription(strDesc);
        } finally {
        }
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {

        StringBuffer strbResult = new StringBuffer();

        if (_bBrief) {
            strbResult.append(getKey());
        } else {
            strbResult.append("ActionGroupBinder:" + getKey());
            if (m_ag1 != null) {
            	strbResult.append("\nActionGroup1:" + m_ag1.dump(_bBrief));
			}else{
            	strbResult.append("\nActionGroup1: null");
			}
            if (m_ag2 != null) {
                strbResult.append("\nActionGroup2:" + m_ag2.dump(_bBrief));
            }else{
            	strbResult.append("\nActionGroup2: null");
			}
        }

        return strbResult.toString();

    }

    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getLongDescription();
    }

    //
    // This is the table wrapper stuff to Render its Action Groups
    //

    /*
    * return the column information here..
    * Here we must build an array of implicators
    * with a unique Key. (EntityType.AttributeCode)
    * We have to get the columns from both the Relator..
    * and the Entity2Type.
    */
    /**
     * (non-Javadoc)
     * getRowList
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getRowList()
     */
    public EANList getRowList() {

        resetData();

		EANList aiList = new EANList();
		Vector v = new Vector();
        if (m_ag1 != null) {
            for (int ii = 0; ii < m_ag1.getActionItemCount(); ii++) {
                EANActionItem ai = m_ag1.getActionItem(ii);
                String strOrder = m_ag1.getOrder(ai);
                String strKey = strOrder + ai.getKey();
                aiList.put(strKey, ai);
                v.addElement(strKey);
/*
                try {
                    putData(ai);
                } catch (Exception x) {
                    x.printStackTrace();
                }
*/
            }
        }

        if (m_ag2 != null) {
            for (int ii = 0; ii < m_ag2.getActionItemCount(); ii++) {
                EANActionItem ai = m_ag2.getActionItem(ii);
                String strOrder = m_ag2.getOrder(ai);
                String strKey = strOrder + ai.getKey();
                aiList.put(strKey, ai);
                v.addElement(strKey);
/*
                try {
                    putData(ai);
                } catch (Exception x) {
                    x.printStackTrace();
                }
*/
            }
        }

		String[] as = new String[v.size()];
		v.copyInto(as);
		Arrays.sort(as);

		for (int i=0; i < as.length; i++) {
			String strKey = as[i];
			EANActionItem ai = (EANActionItem)aiList.get(strKey);
			if (ai != null) {
				putData(ai);
			}
		}

        return getData();
    }

    /*
    *	Return the Relator Rows..
    * we down link to the entity in question to fish out thoese
    * items
    */
    /**
     * (non-Javadoc)
     * getColumnList
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getColumnList()
     */
    public EANList getColumnList() {

        resetMeta();

        try {
            // Meta for a simple table display using description
            putMeta(new MetaLabel(this, getProfile(), EANActionItem.DESCRIPTION, "Description", String.class));
        } catch (Exception x) {
            x.printStackTrace();
        }
        // Now return it

        return getMeta();
    }

    /**
     * (non-Javadoc)
     * commit
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#commit(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface)
     */
    public void commit(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
    }

    /**
     * (non-Javadoc)
     * canEdit
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#canEdit()
     */
    public boolean canEdit() {
        return false;
    }

    /**
     * (non-Javadoc)
     * canCreate
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#canCreate()
     */
    public boolean canCreate() {
        return false;
    }

    /**
     * (non-Javadoc)
     * addRow
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#addRow()
     */
    public boolean addRow() {
        return false;
    }

    /**
     * (non-Javadoc)
     * addRow
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#addRow(String)
     */
    public boolean addRow(String _key) {
        return false;
    }

    /**
     * (non-Javadoc)
     * removeRow
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#removeRow(java.lang.String)
     */
    public void removeRow(String _strKey) {
    }

    /**
     * (non-Javadoc)
     * hasChanges
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#hasChanges()
     */
    public boolean hasChanges() {
        return false;
    }

    /**
     * (non-Javadoc)
     * getMatrixValue
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getMatrixValue(java.lang.String)
     */
    public Object getMatrixValue(String _str) {
        return null;
    }
    /**
     * (non-Javadoc)
     * putMatrixValue
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#putMatrixValue(java.lang.String, java.lang.Object)
     */
    public void putMatrixValue(String _str, Object _o) {
    }
    /**
     * (non-Javadoc)
     * isMatrixEditable
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#isMatrixEditable(java.lang.String)
     */
    public boolean isMatrixEditable(String _str) {
        return false;
    }

    /**
     * (non-Javadoc)
     * rollbackMatrix
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#rollbackMatrix()
     */
    public void rollbackMatrix() {
    }
    /**
     * (non-Javadoc)
     * addColumn
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#addColumn(COM.ibm.eannounce.objects.EANFoundation)
     */
    public void addColumn(EANFoundation _ean) {
    }
    /**
     * (non-Javadoc)
     * generatePickList
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#generatePickList(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
     */
    public EntityList generatePickList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }

    /**
     * (non-Javadoc)
     * removeLink
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#removeLink(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
     */
    public boolean removeLink(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey) throws EANBusinessRuleException {
        return false;
    }

    /**
     * (non-Javadoc)
     * link
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#link(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String, COM.ibm.eannounce.objects.EntityItem[], java.lang.String)
     */
    public EANFoundation[] link(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey, EntityItem[] _aeiChild, String _strLinkOption) throws EANBusinessRuleException {
        return null;
    }

    /**
     * (non-Javadoc)
     * create
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#create(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
     */
    public EntityList create(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }

    /**
     * (non-Javadoc)
     * edit
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#edit(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String[])
     */
    public EntityList edit(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _astrKey) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException {
        return null;
    }

    /**
     * (non-Javadoc)
     * getWhereUsedList
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getWhereUsedList(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
     */
    public WhereUsedList getWhereUsedList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }

    /**
     * (non-Javadoc)
     * getActionItemsAsArray
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getActionItemsAsArray(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
     */
    public Object[] getActionItemsAsArray(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) {
        return null;
    }

    /**
     * (non-Javadoc)
     * isDynaTable
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#isDynaTable()
     */
    public boolean isDynaTable() {
        return false;
    }

    /**
     * (non-Javadoc)
     * duplicate
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#duplicate(java.lang.String, int)
     */
    public boolean duplicate(String _strKey, int _iDup) {
        return false;
    }

    /**
     * (non-Javadoc)
     * linkAndRefresh
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#linkAndRefresh(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.LinkActionItem)
     */
    public Object linkAndRefresh(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, LinkActionItem _lai) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException, RemoteException {
        return null;
    }
}
