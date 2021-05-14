/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: RecordToggle.java,v $
 * Revision 1.3  2012/04/05 17:48:57  wendy
 * jre142 and win7 changes
 *
 * Revision 1.2  2008/01/30 16:26:56  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:48  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:19  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2005/09/08 17:59:10  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.6  2005/02/03 21:26:15  tony
 * JTest Format Third Pass
 *
 * Revision 1.5  2005/01/28 19:37:00  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/26 18:20:09  tony
 * JTest Formatting
 *
 * Revision 1.3  2005/01/14 19:54:33  tony
 * adjusted button logic to troubleshoot
 * Button disappearing issue.
 *
 * Revision 1.2  2004/11/19 18:01:51  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:50  tony
 * This is the initial load of OPICM
 *
 * Revision 1.11  2003/10/29 19:10:43  tony
 * acl_20031029
 *
 * Revision 1.10  2003/10/27 22:18:21  tony
 * updated logic to make ACCESSIBLE_ENABLED a boolean
 * that was not computed each time but instead a constant...
 * That will allow for the app to run a bit faster.
 * Parameterized accessibility on the table with the new
 * Parm.
 *
 * Revision 1.9  2003/10/17 22:47:00  tony
 * parameterized setLookAndFeel to prevent memory leak for testing.
 *
 * Revision 1.8  2003/10/07 21:31:19  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.7  2003/09/23 18:27:21  tony
 * 52354
 *
 * Revision 1.6  2003/08/21 15:58:31  tony
 * updated default value to 1
 *
 * Revision 1.5  2003/08/18 21:46:44  tony
 * 51803
 *
 * Revision 1.4  2003/05/16 00:30:56  tony
 * 50696
 *
 * Revision 1.3  2003/04/18 14:40:45  tony
 * enhanced record toggle logic per KC multiple create
 * did not toggle thru records properly.
 *
 * Revision 1.2  2003/04/17 23:15:15  tony
 * cleaned code
 *
 * Revision 1.1.1.1  2003/03/03 18:03:52  tony
 * This is the initial load of OPICM
 *
 * Revision 1.17  2002/11/20 18:03:57  tony
 * 23265b - enhancement to adjust menu enablement
 * base on the number of records displayed and the
 * number of records available.
 *
 * Revision 1.16  2002/11/20 17:10:00  tony
 * 23265
 *
 * Revision 1.15  2002/11/07 16:58:19  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import COM.ibm.eannounce.objects.RowSelectableTable;
import COM.ibm.eannounce.objects.EANFoundation;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto 
 */
public class RecordToggle extends JToolBar implements ActionListener, PropertyChangeListener, EAccessConstants {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ERolloverButton btnLeft = new ERolloverButton(eaccess().getImageIcon("left.gif"),"left.gif");
	private ERolloverButton btnRight = new ERolloverButton(eaccess().getImageIcon("right.gif"),"right.gif");
	private ELabel lblCount = new ELabel("1");
	private int current = 0;
	private RowSelectableTable table = null;

	/*private RecordToggle(RowSelectableTable _table) {
		super("recordToggle");
		setLookAndFeel();
		btnLeft.addActionListener(this);
		btnRight.addActionListener(this);
		table = _table;
		setEnabled(getMin(),getMax(),0);
		init();
		lblCount.setHorizontalAlignment(SwingConstants.CENTER);
		lblCount.setVerticalAlignment(SwingConstants.CENTER);
		setFloatable(false);
		setCurrent(0,false);
		return;
	}*/

	/**
     * eaccess
     * @return
     * @author Anthony C. Liberto
     */
    public static EAccess eaccess() {
		return EAccess.eaccess();
	}

	/**
     * recordToggle
     * @author Anthony C. Liberto
     */
    public RecordToggle() {
		super("recordToggle");
		btnLeft.addActionListener(this);
		btnRight.addActionListener(this);
		return;
	}

//51803	public void setTable(RowSelectableTable _table) {
	/**
     * setTable
     * @param _table
     * @param _index
     * @author Anthony C. Liberto
     */
    public void setTable(RowSelectableTable _table, int _index) {		//51803
		table = _table;
		setEnabled(getMin(),getMax(),0);
		init();
		lblCount.setHorizontalAlignment(SwingConstants.CENTER);
		lblCount.setVerticalAlignment(SwingConstants.CENTER);
		setFloatable(false);
//51803		setCurrent(0,false);
		setCurrent(_index,false);										//51803
		return;
	}

	/**
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     * @author Anthony C. Liberto
     */
    public void propertyChange(PropertyChangeEvent _pce) {
		if (_pce.getPropertyName().equals("orientation")) {
			setOrientation(((Integer)_pce.getNewValue()).intValue());
		}
		return;
	}

	private void init () {
		add(btnLeft);
		add(lblCount);
		add(btnRight);
		return;
	}

/*
	private void pack(Dimension _d) {
		setSize(_d);
		setPreferredSize(_d);
		return;
	}
*/

	private int getMax() {
		if (table != null) {
			return table.getRowCount();
		}
		return 0;
	}

	private int getMin() {
		return 0;
	}

	/**
     * setCurrent
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setCurrent(int _i) {
		setCurrent(_i,true);
	}

	/**
     * setCurrent
     * @param _i
     * @param _fire
     * @author Anthony C. Liberto
     */
    public void setCurrent(int _i, boolean _fire) {
		int min = getMin();
		int max = getMax();
        String key = null;
		if (_i < min || _i >= max) {
            return;
		}
		setEnabled(min,max,_i);
		current = _i;
		lblCount.setText(Integer.toString(_i + 1));
		if (_fire) {
			table.refresh();													//50696
			key = getCurrentKey();
			firePropertyChange(RECORD_TOGGLE, key, getCurrentTable(key));
		}
		return;
	}

	private void setEnabled(int _min, int _max, int _indx) {
		if (_indx > _min) {
			btnLeft.setEnabled(true);
		} else {
			btnLeft.setEnabled(false);
		}
		if (_indx < _max-1) {
			btnRight.setEnabled(true);
		} else {
			btnRight.setEnabled(false);
		}
		return;
	}

	/**
     * getCurrentIndex
     * @return
     * @author Anthony C. Liberto
     */
    public int getCurrentIndex() {
		return current;
	}

	/**
     * getCurrentTable
     * @return
     * @author Anthony C. Liberto
     */
    public EANFoundation getCurrentTable() {
		return getCurrentTable(getCurrentKey());
	}

	/**
     * getCurrentTable
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public EANFoundation getCurrentTable(String _key) {
		EANFoundation ean = table.getRow(_key);
		return ean;
	}

	/**
     * getCurrentKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getCurrentKey() {
		return table.getRowKey(current);
	}

//23265	private void increment(int _i) {
	/**
     * increment
     * @param _i
     * @author Anthony C. Liberto
     */
    public void increment(int _i) {				//23265
		setCurrent(getCurrentIndex() + _i);
		return;
	}

	/**
     * isNextEnabled
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isNextEnabled() {			//23265
		return btnRight.isEnabled();			//23265
	}											//23265

	/**
     * isPrevEnabled
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPrevEnabled() {			//23265
		return btnLeft.isEnabled();				//23265
	}											//23265

	/**
     * @see javax.swing.JToolBar#setOrientation(int)
     * @author Anthony C. Liberto
     */
    public void setOrientation(int _i) {
		super.setOrientation(_i);
		return;
	}

	/**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
		Object source = _ae.getSource();
		if (source == btnLeft) {
			increment(-1);
		} else if (source == btnRight) {
			increment(1);
		}
		return;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		table = null;
		btnLeft.dereference();
		btnLeft = null;
		btnRight.dereference();
		btnRight = null;
		removeAll();
		removeNotify();
		return;
	}
/*
 52354
 */
	/**
     * updateRecordLabel
     * @param _i
     * @author Anthony C. Liberto
     */
    public void updateRecordLabel(int _i) {
		setEnabled(getMin(),getMax(),_i);
		lblCount.setText(Integer.toString(_i+1));
		return;
	}
/*
 acl_20031007
 */
	/**
     * setLookAndFeel
     * @author Anthony C. Liberto
     * /
    public void setLookAndFeel() {
		if (EAccess.isArmed(ACCESSIBLE_ARM_FILE)) {
			LookAndFeel lnf = UIManager.getLookAndFeel();
			try {
				UIManager.setLookAndFeel(WINDOWS_LNF);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			SwingUtilities.updateComponentTreeUI(this);

			try {
				UIManager.setLookAndFeel(lnf);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return;
	}*/
}
