/*
 * Created on Jan 26, 2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package com.ibm.eannounce.eforms.table;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class SortDetail {
	private int[] sortInt = null;
	private boolean[] sortBoolean = null;

	/**
     * sortDetail
     * @author Anthony C. Liberto
     */
    public SortDetail() {}

	/**
     * setDetails
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setDetails(int[] _i) {
		sortInt = _i;
		return;
	}

	/**
     * setDetails
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setDetails(boolean[] _b) {
		sortBoolean = _b;
		return;
	}

	/**
     * setDetails
     * @param _i
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setDetails(int[] _i, boolean[] _b) {
		setDetails(_i);
		setDetails(_b);
		return;
	}

	/**
     * hasSort
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasSort() {
		return sortInt != null && sortBoolean != null;
	}

	/**
     * reset
     * @author Anthony C. Liberto
     */
    public void reset() {
		sortInt = null;
		sortBoolean = null;
		return;
	}

	/**
     * getColumns
     * @return
     * @author Anthony C. Liberto
     */
    public int[] getColumns() {
		return sortInt;
	}

	/**
     * getDirection
     * @return
     * @author Anthony C. Liberto
     */
    public boolean[] getDirection() {
		return sortBoolean;
	}
}
