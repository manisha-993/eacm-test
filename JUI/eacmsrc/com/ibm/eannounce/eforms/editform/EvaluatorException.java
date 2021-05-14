/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: EvaluatorException.java,v $
 * Revision 1.2  2008/01/30 16:27:09  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:18  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:55  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:02  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/04 15:22:09  tony
 * JTest Format Third Pass
 *
 * Revision 1.2  2005/01/27 19:15:16  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:39  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2002/11/07 16:58:27  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.editform;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EvaluatorException extends Exception {
	private static final long serialVersionUID = 1L;
	private String key = null;
	private String desc = null;

	/**
     * evaluatorException
     * @param _s
     * @author Anthony C. Liberto
     */
    public EvaluatorException(String _s) {
		super(_s);
		return;
	}

	/**
     * evaluatorException
     * @param _s
     * @param _key
     * @author Anthony C. Liberto
     */
    public EvaluatorException(String _s, String _key) {
		super(_s);
		setKey(_key);
		return;
	}

	/**
     * evaluatorException
     * @param _s
     * @param _key
     * @param _desc
     * @author Anthony C. Liberto
     */
    public EvaluatorException(String _s, String _key, String _desc) {
		super(_s);
		setKey(_key);
		setDescription(_desc);
		return;
	}

	private void setKey(String _s) {
		key = new String(_s);
		return;
	}

	/**
     * getKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getKey() {
		return key;
	}

	private void setDescription(String _s) {
		desc = new String(_s);
		return;
	}

	/**
     * getDescription
     * @return
     * @author Anthony C. Liberto
     */
    public String getDescription() {
		return desc;
	}


	/**
     * @see java.lang.Object#toString()
     * @author Anthony C. Liberto
     */
    public String toString() {
		if (key != null && desc != null) {
			return super.toString() + " key:" + key + ", description: " + desc + ".";

		} else if (key != null) {
			return super.toString() +  " key:" + key +".";

		} else if (desc != null) {
			return super.toString() +  " description: " + desc + ".";
		}
		return super.toString();
	}
}
