//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: PingObject.java,v $
// Revision 1.9  2005/01/26 17:47:39  dave
// more JTest reformatting per IBM
//
// Revision 1.8  2005/01/26 01:05:19  dave
// Jtest cleanup
//
// Revision 1.7  2002/10/15 22:09:42  roger
// Show the connection details
//
// Revision 1.6  2002/10/02 23:06:24  roger
// CVS change history/log restored
//
//


package COM.ibm.opicmpdh.middleware.taskmaster;


import java.rmi.Naming;


/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Dave
 */

public final class PingObject {
	/**
	 * Automatically generated constructor for utility class
	 */
	private PingObject() {
	}

	/**
     * mainline
     * 
	 * @param args
	 * @throws java.lang.Exception
	 * @author Dave
	 */
	public static void main(String[] args) throws Exception {

		String strObject = args[0];
		try {
			RemoteRunnable rr = null;
			String strResponse = null;
			System.out.println(
				"Attempting ping of "
					+ strObject
					+ " as "
					+ Connection.createURL(strObject));

			rr =
				(RemoteRunnable) Naming.lookup(Connection.createURL(strObject));

			strResponse = rr.ping();

			System.out.println(
				"ping of "
					+ strObject
					+ " responded with '"
					+ strResponse
					+ "'");
		} catch (Exception x) {
			System.out.println("ping failed " + x);
		}
	}
	/**
	 * Return the date/time this class was generated
	 * @return the date/time this class was generated
	 */
	public static final String getVersion() {
		return "$Id: PingObject.java,v 1.9 2005/01/26 17:47:39 dave Exp $";
	}
}
