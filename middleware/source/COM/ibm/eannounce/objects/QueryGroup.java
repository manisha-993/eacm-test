//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.objects;

import java.rmi.RemoteException;
import java.sql.*;

import COM.ibm.opicmpdh.middleware.*;

/**
 *  used with a db2 view
 *
 */
//$Log: QueryGroup.java,v $
//Revision 1.4  2010/10/18 11:53:15  wendy
//make dereference public
//
//Revision 1.3  2009/05/11 15:39:07  wendy
//Support dereference for memory release
//
//Revision 1.2  2008/11/11 23:01:58  wendy
//Add 'with ur' to qry
//
//Revision 1.1  2008/08/08 21:43:12  wendy
//CQ00006067-WI : LA CTO - More support for QueryAction
//
public class QueryGroup extends EntityGroup {
	private static final long serialVersionUID = 1L;

	/** 
	 * Instantiated from QueryList
	 * @param _db
	 * @param _prof
	 * @param _viewName
	 * @param colList
	 * @throws RemoteException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws SQLException
	 */
	protected QueryGroup(Database _db, Profile _prof, String _viewName, EANList colList) 
	throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException
	{
		super(_prof, _viewName, "Edit"); // edit - indicates all attributes
		putShortDescription(1, _viewName);
        putLongDescription(1, "View "+_viewName);
        setCapability("R");
        // move the meta attributes created in the querylist here
		for (int i=0; i<colList.size(); i++){
			try {
				EANMetaAttribute meta1 = (EANMetaAttribute)colList.getAt(i);
				meta1.setParent(this);		
				getMeta().put(meta1);
			} catch (Exception x) {
				x.printStackTrace();
			}
		}	
		
		getMetaColumnOrderGroup(_db, null); // get any column order now using the database reference
	}
	/** 
	 * Created when loaded from preference panel
	 * @param _db
	 * @param _prof
	 * @param _viewName
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareRequestException
	 */
	public QueryGroup(Database _db, Profile _prof, String _viewName) throws SQLException,
	MiddlewareException, MiddlewareRequestException {
		super(_prof, _viewName, "Edit"); // edit - indicates all attributes
		
		ResultSet rs=null;
		PreparedStatement ps=null;
		
		putShortDescription(1, _viewName);
        putLongDescription(1, "View "+_viewName);
        setCapability("R");
        
		// get the 'meta' for this query
		try {
			StringBuffer querySb = new StringBuffer("select * from "+_viewName+" where entityid=0 with ur");

			Connection con = _db.getPDHConnection();
			ps = con.prepareStatement(querySb.toString());
			_db.debug(D.EBUG_DETAIL, "QueryGroup executing SQL:" + querySb);

			rs = ps.executeQuery();		

			// How many cols in output ResultSet?
			ResultSetMetaData rsmd = rs.getMetaData();
			int iCols = rsmd.getColumnCount();
			// get the column names for display
			for (int i=1; i<=iCols; i++){
				try {
					EANMetaAttribute ml1 = new MetaTextAttribute(this, getProfile(), 
							rsmd.getColumnLabel(i),	"T", "R");
					ml1.putShortDescription(rsmd.getColumnLabel(i));
					ml1.putLongDescription(rsmd.getColumnLabel(i));
					ml1.setParent(this);					
					getMeta().put(ml1);
				} catch (Exception x) {
					x.printStackTrace();
				}
			}	
		}
		finally{
			if (ps!=null){
				ps.close();
			}
			if (rs!=null){
				rs.close();
			}
			_db.commit();
			_db.freeStatement();
			_db.isPending();
		}		
	}
    /**
     *  Gets the metaColumnOrderGroup for the QueryGroup object
     *
     * @param  _db                                        Description of the Parameter
     * @param  _rdi                                       Description of the Parameter
     * @return                                            The metaColumnOrderGroup value
     * @exception  RemoteException                        Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  SQLException                           Description of the Exception
     */
    public MetaColumnOrderGroup getMetaColumnOrderGroup(Database _db, RemoteDatabaseInterface _rdi) throws RemoteException, MiddlewareException,
        MiddlewareShutdownInProgressException, SQLException {
    	MetaColumnOrderGroup mcog = getMetaColumnOrderGroup();
        if (mcog == null) {
            if (_db != null) {
                mcog = new MetaColumnOrderGroup(_db, this);
                mcog.setParent(this);
                setMetaColumnOrderGroup(mcog);
            }
            else if (_rdi != null) {
                mcog = _rdi.getMetaColumnOrderGroup(getEntityType(), getProfile(), true);
                mcog.setParent(this);
                setMetaColumnOrderGroup(mcog);
            }
        }
        return mcog;
    }
    
    /**
     *  release memory
     */
    public void dereference() {
        // Step backwards through the Meta List
        for (int ii = getMetaAttributeCount() - 1; ii >= 0; ii--) {
            EANMetaAttribute ma = getMetaAttribute(ii);
            if (ma != null) {
                ma.dereference();
            }
        }
        super.dereference();
    }
	/*******************
	 *  Gets the column information
	 *
	 *@return    The columnList value
	 */
	public EANList getColumnList() {
		MetaColumnOrderGroup mcog = getMetaColumnOrderGroup();
		EANList el = getMeta();
		EANList elNew = new EANList();
		if (mcog != null) {
			mcog.performSort(MetaColumnOrderGroup.COLORDER_KEY, true);
			// assumption is that key of MetaColumnOrderItem == key of Implicator in columnList
			// so must use attributecode here
			for (int i = 0; i < mcog.getMetaColumnOrderItemCount(); i++) {
				MetaColumnOrderItem mcoi = mcog.getMetaColumnOrderItem(i);
				if (mcoi.isVisible()) {
					EANObject eObj = (EANObject) el.get(mcoi.getAttributeCode());
					if (eObj != null) {
						elNew.put(eObj);
					}
				} 
			}	
			el = elNew;
        }

		return el;
	}

}
