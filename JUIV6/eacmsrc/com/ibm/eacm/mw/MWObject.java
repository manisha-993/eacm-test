//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eacm.mw;
import java.io.Serializable;

/**
 * This class represents one entry in the middleware_client_properties.html
 * @author Wendy Stimpson
 */
//$Log: MWObject.java,v $
//Revision 1.1  2012/09/27 19:39:22  wendy
//Initial code
//
public class MWObject implements Serializable {
	static final long serialVersionUID = 19721222L;
	private String sDesc = "";
	private String sIP = "";
	private int iPort = 0;
	private String sName = "";
	private String sReport = "";								

	protected MWObject(){}

	/**
     * setDescription
     * @param _s
     */
	protected void setDescription(String _s) {
		sDesc = _s;
	}

	/**
     * getDescription
     * @return
     */
	public String getDescription() {
		return sDesc;
	}

	/**
     * setName
     * @param _s
     */
    protected void setName(String _s) {
		sName = _s;
	}

	/**
     * getName
     * @return
     */
    public String getName() {
		return sName;
	}

	/**
     * setIP
     * @param _s
     */
    protected void setIP(String _s) {
		sIP = _s;
	}

	/**
     * getIP
     * @return
     */
    public String getIP() {
		return sIP;
	}

	/**
     * setPort
     * @param _i
     */
    protected void setPort(int _i) {
		iPort = _i;
	}

	/**
     * getPort
     * @return
     */
    public int getPort() {
		return iPort;
	}

	/**
     * @see java.lang.Object#toString()
     */
    public String toString() {
		return sDesc;
	}

	/**
     * key
     * @return
     */
    public String key() {
		return sDesc + ":" + sName + ":" + sIP + ":" + iPort + ":" + sReport;
	}

	/**
     * setReportPrefix
     * @param _s
     */
    protected void setReportPrefix(String _s) {
		if (_s != null) {												
			sReport = _s.trim();
			if (!sReport.endsWith("/")){
				sReport+="/";
			}
		}											
	}

	/**
     * getReportPrefix
     * @return
     */
    public String getReportPrefix() {
		return sReport;
	}
}
