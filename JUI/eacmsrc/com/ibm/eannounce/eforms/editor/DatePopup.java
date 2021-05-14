/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: DatePopup.java,v $
 * Revision 1.3  2009/06/09 11:27:57  wendy
 * Performance cleanup
 *
 * Revision 1.2  2008/01/30 16:27:05  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:34  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:56  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:03  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/01 20:48:32  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 19:15:16  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2003/10/20 23:23:36  tony
 * acl_20031020
 * updated logic to prevent datepanel misinterpretation
 * of popup location.
 *
 * Revision 1.5  2003/10/15 18:10:04  tony
 * 52488
 *
 * Revision 1.4  2003/08/14 18:04:43  tony
 * 51752
 *
 * Revision 1.3  2003/07/18 22:14:44  joan
 * 51336
 *
 * Revision 1.2  2003/03/25 17:58:11  tony
 * dateEditor modification
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2002/11/11 22:55:39  tony
 * adjusted classification on the toggle
 *
 * Revision 1.6  2002/11/07 16:58:27  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.editor;
import com.elogin.*;
import com.ibm.eannounce.eobjects.EDateLabel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class DatePopup extends JPopupMenu implements MouseListener {
	private static final long serialVersionUID = 1L;
	private DatePopupPanel date = null;
	private DateTimeEditor df = null;

	/**
     * datePopup
     * @param _df
     * @author Anthony C. Liberto
     */
    protected DatePopup(DateTimeEditor _df) {
	    df = _df;
		init();
	}

	/**
     * setFuture
     * @param _b
     * @author Anthony C. Liberto
     */
    protected void setFuture(boolean _b) {
		date.setFuture(_b);
	}

	/**
     * isFuture
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isFuture() {
		return date.isFuture();
	}*/

	/**
     * setPast
     * @param _b
     * @author Anthony C. Liberto
     */
    protected void setPast(boolean _b) {
		date.setPast(_b);
	}
    protected void setWarning(boolean _b) {
		date.setWarning(_b);
	}
	/**
     * isPast
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isPast() {
		return date.isPast();
	}*/

	/**
     * resetCalendar
     * @author Anthony C. Liberto
     */
    protected void resetCalendar() {
		date.resetCalendar();
	}

	private void init() {
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setBorderPainted(true);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setOpaque(false);
		date = new DatePopupPanel(this,false,false,true);
		date.setModalCursor(true);		//51336
		add(date);
		setDoubleBuffered(true);
		setRequestFocusEnabled(false);
		pack();
	}

	/**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseClicked(MouseEvent _me) {
		Component c = _me.getComponent();
		String outDate = null;
        if (c instanceof EDateLabel) {
			String action = ((EDateLabel)c).getActionCommand();
			if (action.equals("PREV_YEAR")) {
				date.adjustYear(-1);
				date.setDays();
			} else if (action.equals("NEXT_YEAR")) {
				date.adjustYear(+1);
				date.setDays();
			} else if (action.equals("PREV_MONTH")) {
				date.adjustMonth(-1);
				date.setDays();
			} else if (action.equals("NEXT_MONTH")) {
				date.adjustMonth(+1);
				date.setDays();
			} else if (Routines.have(action)) {
				if (_me.getClickCount() != 1) {
                    return;
				}
				if (!c.isEnabled()) {
                    return;
				}
				outDate = date.getSelectedDate(action);
				df.setSelectedDate(outDate);
			}
		}
	}
	/**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mousePressed(MouseEvent _me) {}
	/**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseReleased(MouseEvent _me) {}
	/**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseExited(MouseEvent _me) {}
	/**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseEntered(MouseEvent _me) {}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		date.dereference();
		df = null;
		removeAll();
	}

	/**
     * @see javax.swing.JPopupMenu#show(java.awt.Component, int, int)
     * @author Anthony C. Liberto
     */
    public void show(Component _c, int _x, int _y) {
//52488		JFrame frame = (JFrame)eaccess().getLogin();
//52488		Dimension scr = frame.getSize();
//52488		Point ptS = frame.getLocationOnScreen();
		Window win = EAccess.eaccess().getParentWindow(_c);					//52488
		Dimension scr = null;
        Point ptS = null;
        Dimension pSize = null;
        Point ptO = null;
        Point pt = null;
        if (win == null) {											//52488
			win = EAccess.eaccess().getLogin();						//52488
		}															//52488
		scr = win.getSize();								//52488
		ptS = win.getLocationOnScreen();						//52488
		pSize = getPreferredSize();
		ptO = _c.getLocationOnScreen();
		pt = new Point(ptO.x - ptS.x, ptO.y - ptS.y);
		super.show(_c,getXLocation(_x,scr,pSize,pt,_c),getYLocation(_y,scr,pSize,pt,_c));
	}

	private int getXLocation(int _x, Dimension _d, Dimension _popSize, Point _pt, Component _c) {
		int x = _x;
		if ((_d.width - (_popSize.width + _pt.x + _x)) < 0) {
			if (_c == null) {
				x = x - _popSize.width;
			} else {
				x = (x - _popSize.width) - _c.getWidth();
			}
		}
		return x;
	}

	private int getYLocation(int _y, Dimension _d, Dimension _popSize, Point _pt, Component _c) {
		int y = _y;
		if ((_d.height - (_popSize.height + _pt.y + _y)) < 0) {
			if (_c == null) {
				y = (y - _popSize.height);
			} else {
				y = (y - _popSize.height) - _c.getHeight();
			}
		}
		return y;
	}


	/**
     * getDatePopupPanel
     * @return
     * @author Anthony C. Liberto
     */
	protected DatePopupPanel getDatePopupPanel() {
		return date;
	}
}
