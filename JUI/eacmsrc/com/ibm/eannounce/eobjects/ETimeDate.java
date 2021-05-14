/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: ETimeDate.java,v $
 * Revision 1.3  2009/06/09 11:34:32  wendy
 * Performance cleanup
 *
 * Revision 1.2  2008/01/30 16:26:55  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:48  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:18  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:10  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/01/28 19:36:59  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 18:20:09  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:50  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2003/10/20 23:25:07  tony
 * acl_20031020
 * adjusted to eliminate misinterpretation of popup location.
 *
 * Revision 1.7  2003/08/21 19:43:10  tony
 * 51391
 *
 * Revision 1.6  2003/07/23 20:41:58  tony
 * added and enhanced restore logic
 *
 * Revision 1.5  2003/07/22 23:30:54  tony
 * updated with new editor.
 *
 * Revision 1.4  2003/07/22 15:47:20  tony
 * updated date editor capability to improve performance and
 * functionality.
 *
 * Revision 1.3  2003/06/17 20:49:39  tony
 * improved layout for 1.2h
 *
 * Revision 1.2  2003/04/11 20:02:33  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eobjects;
import java.awt.*;
import javax.swing.*;
import com.ibm.eannounce.eforms.editor.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ETimeDate extends JPanel {
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc = new GridBagConstraints();
	private GridBagLayout gbl = new GridBagLayout();

	private DateTimeEditor dateEdit = new DateTimeEditor(null,null,null,DateTimeEditor.DATE_VALIDATOR) {
		private static final long serialVersionUID = 1L;
		public Color getBackground() {
			return Color.white;
		}
		public Color getForeground() {
			return Color.black;
		}
	};

	private DateTimeEditor timeEdit = new DateTimeEditor(null,null,null,DateTimeEditor.TIME_VALIDATOR) {
		private static final long serialVersionUID = 1L;
		public Color getBackground() {
			return Color.white;
		}
		public Color getForeground() {
			return Color.black;
		}
	};
//	private ePanel datePnl = new ePanel(new BorderLayout());
//	private boolean wayBack = false;
//	private boolean isEnable = false;

	/**
     * eTimeDate
     * @author Anthony C. Liberto
     */
    public ETimeDate() {
		this(System.getProperty("mw.now.date"), "23:59");
	}

	/**
     * eTimeDate
     * @param _date
     * @param _time
     * @author Anthony C. Liberto
     */
    private ETimeDate(String _date, String _time) {
		setLayout(gbl);
		setDate(_date);
		setTime(_time);

		dateEdit.setValidatorType(0);
		timeEdit.setValidatorType(3);

		setBorder(UIManager.getBorder("eannounce.etchedBorder"));

		setupJPanel();
	}

	/**
     * clearDateBorder
     * @author Anthony C. Liberto
     */
    public void clearDateBorder() {
		dateEdit.setBorder(null);
		timeEdit.setBorder(null);
	}

	/**
     * setDateOpaque
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setDateOpaque(boolean _b) {
		dateEdit.setOpaque(_b);
		timeEdit.setOpaque(_b);
	}

	/**
     * @see java.awt.Component#requestFocus()
     * @author Anthony C. Liberto
     */
    public void requestFocus() {
		if (dateEdit != null) {
			dateEdit.requestFocus();
		} else if (timeEdit != null) {
			timeEdit.requestFocus();
		} else {
			super.requestFocus();
		}
	}

	/**
     * setPastDate
     * @param _b
     * @param _reset
     * @author Anthony C. Liberto
     */
    public void setPastDate(boolean _b, boolean _reset) {
		dateEdit.setPast(_b,_reset);
	}

	/**
     * setFutureDate
     * @param _b
     * @param _reset
     * @author Anthony C. Liberto
     */
    public void setFutureDate(boolean _b, boolean _reset) {
		dateEdit.setFuture(_b,_reset);
	}

	/**
     * setDateValidatorType
     * @param _type
     * @author Anthony C. Liberto
     */
    public void setDateValidatorType(int _type) {
		dateEdit.setValidatorType(_type);
	}

	/**
     * getDateValidatorType
     * @return
     * @author Anthony C. Liberto
     * /
    public int getDateValidatorType() {
		return dateEdit.getValidatorType();
	}*/

	/**
     * resetCalendar
     * @author Anthony C. Liberto
     */
    public void resetCalendar() {
		dateEdit.resetCalendar();
	}

	/**
     * setTimeValidatorType
     * @param _type
     * @author Anthony C. Liberto
     * /
    public void setTimeValidatorType(int _type) {
		timeEdit.setValidatorType(_type);
	}*/

	/**
     * getTimeValidatorType
     * @return
     * @author Anthony C. Liberto
     * /
    public int getTimeValidatorType() {
		return timeEdit.getValidatorType();
	}*/

	/**
     * getButton
     * @return
     * @author Anthony C. Liberto
     * /
    public JButton getButton() {
		return dateEdit.getButton();
	}*/

	/**
     * hidePopup
     * @author Anthony C. Liberto
     */
    public void hidePopup() {
		if (dateEdit != null) {
			dateEdit.hidePopup();
		}
	}

	/**
     * getDateEditor
     * @return
     * @author Anthony C. Liberto
     */
    public DateTimeEditor getDateEditor() {
		return dateEdit;
	}

	/**
     * getTimeEditor
     * @return
     * @author Anthony C. Liberto
     */
    public DateTimeEditor getTimeEditor() {
		return timeEdit;
	}

	/**
     * setupJPanel
     * @author Anthony C. Liberto
     */
    private void setupJPanel() {
		dateEdit.getButton().setBorder(null);

		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.ipadx = 2;

		buildConstraints(gbc,0,0,1,1,0,0);
		gbl.setConstraints(dateEdit,gbc);
		add(dateEdit);

		buildConstraints(gbc,1,0,1,1,0,0);
		gbl.setConstraints(dateEdit.getButton(),gbc);
		add(dateEdit.getButton());

		buildConstraints(gbc,2,0,1,1,0,0);
		gbl.setConstraints(timeEdit,gbc);
		add(timeEdit);
	}

	private void buildConstraints(GridBagConstraints _gbc, int _gx, int _gy, int _gw, int _gh, int _wx, int _wy) {
		_gbc.gridx = _gx;
		_gbc.gridy = _gy;
		_gbc.gridwidth = _gw;
		_gbc.gridheight = _gh;
		_gbc.weightx = _wx;
		_gbc.weighty = _wy;
	}

	/**
     * setDate
     * @param _date
     * @author Anthony C. Liberto
     */
    public void setDate(String _date) {
		dateEdit.setDisplay(_date);
	}

	/**
     * getDate
     * @return
     * @author Anthony C. Liberto
     */
    private String getDate() {
		return dateEdit.getStrippedText();
	}

	/**
     * setTime
     * @param _time
     * @author Anthony C. Liberto
     */
    private void setTime(String _time) {
		timeEdit.setDisplay(_time);
	}

	/**
     * getTime
     * @return
     * @author Anthony C. Liberto
     */
    private String getTime() {
		String out = timeEdit.getText();
		return out.replace(':','.');
	}

	/**
     * getProcessDate
     * @return
     * @author Anthony C. Liberto
     */
    public String getProcessDate() {
		return getDate() + "-" + getTime() + ".59.000000";
	}

	/**
     * setPast
     * @param _b
     * @param _reset
     * @author Anthony C. Liberto
     */
    public void setPast(boolean _b, boolean _reset) {
		dateEdit.setPast(_b,_reset);
	}

	/**
     * setFuture
     * @param _b
     * @param _reset
     * @author Anthony C. Liberto
     * /
    public void setFuture(boolean _b, boolean _reset) {
		dateEdit.setFuture(_b,_reset);
	}*/

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		dateEdit.dereference();
		dateEdit = null;

		timeEdit.dereference();
		timeEdit = null;

		removeAll();
		removeNotify();
	}

	/**
     * getDatePopupPanel
     * @return
     * @author Anthony C. Liberto
     */
    public DatePopupPanel getDatePopupPanel() {
		return dateEdit.getDatePopupPanel();
	}
}
