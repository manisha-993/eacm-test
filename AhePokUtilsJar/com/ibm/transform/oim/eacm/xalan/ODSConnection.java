/*
 * Created on Dec 4, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.transform.oim.eacm.xalan;

import java.sql.Connection;

/**
 * Implement this if you need to execute SQL directly against the ODS database
 * @author cstolpe
 *
 */
public interface ODSConnection {
	boolean setODSconnection(Connection con);

}
