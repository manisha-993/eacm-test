/**
 * Copyright (c) 2003-2004 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * This should allow for an update directly to the AbstractTableModel
 * and if everything works as advertised no refresh should be necessary.
 *
 * @version 1.2  2003/06/03
 * @author Anthony C. Liberto
 *
 * $Log: AbstractTableComparator.java,v $
 * Revision 1.2  2009/09/01 17:28:47  wendy
 * removed useless code and cleanup
 *
 * Revision 1.1  2007/04/18 19:45:11  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:03  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:06  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/01/31 20:47:47  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/26 22:17:56  tony
 * JTest Modifications
 *
 * Revision 1.2  2004/08/11 21:24:24  tony
 * 5ZKL3K
 *
 * Revision 1.1.1.1  2004/02/10 16:59:44  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1  2003/06/03 22:48:52  tony
 * 51084
 *
 *
 */
package com.ibm.eannounce.eforms.table;
//import com.elogin.EAccessConstants;
import COM.ibm.eannounce.objects.AbstractTableModel;
import java.util.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class AbstractTableComparator implements Comparator//, EAccessConstants 
{
	/**
     * m_bDirection
     */
    private boolean[] m_bDirection = {true};
	/**
     * m_index
     */
    private int[] m_iIndex = {0};
	/**
     * m_table
     */
    private AbstractTableModel m_table = null;

	/**
     * abstractTableComparator
     * @author Anthony C. Liberto
     * /
    public AbstractTableComparator() {
		this(0,true);
		return;
	}*/

	/**
     * abstractTableComparator
     * @param _comp
     * @author Anthony C. Liberto
     * /
    public AbstractTableComparator(AbstractTableComparator _comp) {
		this(_comp.m_iIndex,_comp.m_bDirection);
		return;
	}*/

	/**
     * abstractTableComparator
     * @param _index
     * @param _asc
     * @author Anthony C. Liberto
     */
    protected AbstractTableComparator(int _index, boolean _asc) {
		setDirection(_asc);
		setIndex(_index);
	}

	/**
     * abstractTableComparator
     * @param _index
     * @param _asc
     * @author Anthony C. Liberto
     * /
    public AbstractTableComparator(int[] _index, boolean[] _asc) {
		setDirection(_asc);
		setIndex(_index);
		return;
	}*/

	/**
     * setIndex
     * @param _i
     * @author Anthony C. Liberto
     */
    private void setIndex(int _i) {

		m_iIndex = new int[1];
		m_iIndex[0] = _i;
	}


	/**
     * setIndex
     * @param _i
     * @author Anthony C. Liberto
     */
    private void setIndex(int[] _i) {
		m_iIndex = _i;
	}

	/**
     * setDirection
     * @param _asc
     * @author Anthony C. Liberto
     */
    private void setDirection(boolean _asc) {
		m_bDirection = new boolean[1];
		m_bDirection[0] = _asc;
	}

	/**
     * setDirection
     * @param _asc
     * @author Anthony C. Liberto
     */
    private void setDirection(boolean[] _asc) {
		m_bDirection = _asc;
	}

	/**
     * setTable
     * @param _model
     * @author Anthony C. Liberto
     * /
    public void setTable(AbstractTableModel _model) {
		m_table = _model;
		return;
	}*/

	/**
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     * @author Anthony C. Liberto
     */
    public int compare(Object _o1, Object _o2) {
		String k1 = null;
        String k2 = null;
        int result = 0;
        Object o1 = null;
        Object o2 = null;
        int iRow1 = -1;
        int iRow2 = -1;
        if (m_table == null) {
            return 0;
		}
		k1 = _o1.toString();
		k2 = _o2.toString();

		iRow1 = m_table.getRowIndex(k1);
		iRow2 = m_table.getRowIndex(k2);

		for (int i = 0; i < m_iIndex.length && result == 0 ; i++) {
			if (m_iIndex[i] >= 0) {
				o1 = m_table.get(iRow1,m_iIndex[i]);
				o2 = m_table.get(iRow2,m_iIndex[i]);
				result = compareString(o1.toString(),o2.toString(), i);
			}
		}
		return result;
	}

	/**
     * @see java.lang.Object#equals(java.lang.Object)
     * @author Anthony C. Liberto
     */
    public boolean equals(Object _o3) {
		return true;
	}

	private int compareString(String _s1, String _s2, int _i) {
		if(_s1==null || _s2==null) {
			return -1;
		}
		return getResult(_s1.compareToIgnoreCase(_s2),_i);
	}
/*
	private int compareInteger(Integer _i1, Integer _i2, int _i) {
		return getResult(_i1.compareTo(_i2),_i);
	}
*/
	private int getResult(int _result, int _i) {
		if (!m_bDirection[_i]) {
			return -_result;
		}
		return _result;
	}

/*
 the cool funky stuff
 */
	/**
     * sort
     * @param _index
     * @param _asc
     * @param _model
     * @author Anthony C. Liberto
     * /
    public void sort(int _index, boolean _asc, AbstractTableModel _model) {
		setIndex(_index);
		setDirection(_asc);
		sort(_model);
	}*/

	/**
     * sort
     * @param _model
     * @author Anthony C. Liberto
     */
    protected void sort(AbstractTableModel _model) {
		if (_model != null) {
			int rows = _model.getRowCount();
            m_table = _model;
			if (rows > 1) {
				String[] keys = new String[rows];
				for (int i=0;i<rows;++i) {
					keys[i] = m_table.getRowKey(i);
				}
				Arrays.sort(keys,this);
				moveRow(keys);
				keys = null;
			}
			rows = 0;
			m_table = null;
		}
	}

	/**
     * moveRow
     * @param _str
     * @author Anthony C. Liberto
     */
    private void moveRow(String[] _str) {
		if (m_table != null && _str != null) {
			int ii = _str.length;
			for (int i=0;i<ii;++i) {
				int iOld = m_table.getRowIndex(_str[i]);
				if (iOld >= 0) {
					m_table.moveRow(iOld,i);
				}
			}
		}
	}

	/**
     * set
     * @param _i
     * @param _b
     * @author Anthony C. Liberto
     */
    protected void set(int[] _i, boolean[] _b) {
		setDirection(_b);
		setIndex(_i);
	}
}

