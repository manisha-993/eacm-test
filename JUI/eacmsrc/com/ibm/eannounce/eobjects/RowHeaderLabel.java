/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: RowHeaderLabel.java,v $
 * Revision 1.2  2008/01/30 16:26:56  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:48  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:17  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:19  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:10  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/01/28 19:37:00  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 18:20:09  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:50  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2003/06/04 18:46:09  tony
 * 51112
 *
 * Revision 1.1.1.1  2003/03/03 18:03:52  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2002/11/07 16:58:19  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import com.ibm.eannounce.eforms.table.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class RowHeaderLabel extends ELabel implements MouseListener {
	private static final long serialVersionUID = 1L;
	private RowHeaderIcon icon = new RowHeaderIcon();
    private boolean direction = true;
    private RSTable table = null;
    private GTable m_gtable = null;

    /**
    * rowHeaderLabel
    * @author Anthony C. Liberto
    */
    public RowHeaderLabel() {
        super();
        addMouseListener(this);
        setIcon(icon.getImageIcon());
        setHorizontalTextPosition(JLabel.LEFT);
        return;
    }

    /**
     * setTable
     * @param _table
     * @author Anthony C. Liberto
     */
    public void setTable(RSTable _table) {
        table = _table;
    }

    /**
     * getTable
     * @return
     * @author Anthony C. Liberto
     */
    public RSTable getTable() {
        return table;
    }

    /**
     * setGTable
     * @param _gtable
     * @author Anthony C. Liberto
     */
    public void setGTable(GTable _gtable) {
        m_gtable = _gtable;
    }

    /**
     * getGTable
     * @return
     * @author Anthony C. Liberto
     */
    public GTable getGTable() {
        return m_gtable;
    }

    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseReleased(MouseEvent me) {
    }
    /**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseEntered(MouseEvent me) {
    }
    /**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseExited(MouseEvent me) {
    }
    /**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mousePressed(MouseEvent me) {
    }

    /**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseClicked(MouseEvent me) {
        doClick();
        return;
    }

    private void toggleIcon() {
        if (direction) {
            icon.setImage("des");
            direction = false;
        } else {
            icon.setImage("asc");
            direction = true;
        }
        setIcon(icon.getImageIcon());
        repaint();
        return;
    }

    /**
     * toggleIcon
     * @param _b
     * @author Anthony C. Liberto
     */
    public void toggleIcon(boolean _b) {
        if (_b) {
            icon.setImage("asc");
        } else {
            icon.setImage("des");
        }
        setIcon(icon.getImageIcon());
        repaint();
        return;
    }

    /**
     * getDirection
     * @return
     * @author Anthony C. Liberto
     */
    public boolean getDirection() {
        return direction;
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        removeMouseListener(this);
        icon.dereference();
        icon = null;
        table = null;
        removeAll();
        removeNotify();
        return;
    }

    /*
     51112
    */
    /**
     * doClick
     * @author Anthony C. Liberto
     */
    public void doClick() {
        if (table != null) {
            toggleIcon();
            table.sortHeader(direction);
        }
        return;
    }
}
