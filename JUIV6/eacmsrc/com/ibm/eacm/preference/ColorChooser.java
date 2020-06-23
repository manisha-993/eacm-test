//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.preference;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.ibm.eacm.objects.Utils;

import java.util.EventListener;
/**
 * Colorchooser dialog invoked from color buttons
 * @author Wendy Stimpson
 */
// $Log: ColorChooser.java,v $
// Revision 1.1  2012/09/27 19:39:15  wendy
// Initial code
//
public class ColorChooser extends JColorChooser implements ActionListener  
{
	private static final long serialVersionUID = 1L;
	private Color color = null;
	private JDialog dialog = null;
	
	/**
     * eColorChooser
     * @param _c
     */
	protected ColorChooser(JPanel c) {
		dialog = createDialog(c, Utils.getResource("chooseColor"), true, this, this, this);
		dialog.addComponentListener(new ComponentAdapter() {
			public void componentHidden(ComponentEvent _ce) { // cancel does not call windowclosing
				dialog.dispose();
			}
		}
		);

		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent _we) {
				dialog.setVisible(false);
			}
		}
		);
	}

	/**
     * getColor
     * @param _color
     * @return
     */
    protected Color getColor(Color _color) {
    	setColor(_color);
    	dialog.setVisible(true);
    	return color;
	}
    
    protected void dereference(){
        EventListener listeners[] = dialog.getListeners(WindowListener.class);
        for(int it=0; it<listeners.length; it++)  {
            dialog.removeWindowListener((WindowListener)listeners[it]);
            listeners[it]=null;
        }
        listeners = dialog.getListeners(ComponentListener.class);
        for(int it=0; it<listeners.length; it++)  {
            dialog.removeComponentListener((ComponentListener)listeners[it]);
            listeners[it]=null;
        }
   
        dialog = null;
        color = null;
    }

	/**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent _ae) {
		String action = _ae.getActionCommand();
		if (action.equals("OK")) {
			color = getColor();
		}
	}
}
