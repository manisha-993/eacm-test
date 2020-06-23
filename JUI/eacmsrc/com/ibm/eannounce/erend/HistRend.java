// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2009  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eannounce.erend;
import java.awt.*;
import javax.swing.*;
import com.ibm.eannounce.eforms.navigate.NavHist;

/**
 * 
 */
//$Log: HistRend.java,v $
//Revision 1.1  2009/04/16 17:55:00  wendy
//Cleanup history
//
public class HistRend extends JLabel implements ListCellRenderer {
	private static final long serialVersionUID = 1L;
	private NavHist navHist = null;
    public HistRend(NavHist nh) {
    	navHist = nh;
        setOpaque(true);
    }

    /**
     * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
     */
    public Component getListCellRendererComponent(JList _jc, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (_jc == null) {
	        setText("");
	        return this;
		}
        //if (value instanceof NavSerialObject) {
        if (value instanceof String) {
            Font f = getFont();
            //if (((NavSerialObject) value).isPicklist()) {
            if (navHist.isPickList(value)) {
                if (isSelected) {
                    setBackground(_jc.getSelectionBackground());
                    setForeground(Color.white);
                    setFont(f.deriveFont(Font.BOLD + Font.ITALIC));
                } else {
                    setBackground(Color.white);
                    setForeground(Color.blue);
                    setFont(f.deriveFont(Font.BOLD + Font.ITALIC));
                }
            } else if (isSelected) {
                setBackground(_jc.getSelectionBackground());
                setForeground(_jc.getSelectionForeground());
                setFont(f.deriveFont(Font.PLAIN));
            } else {
                setBackground(_jc.getBackground());
                setForeground(_jc.getForeground());
                setFont(f.deriveFont(Font.PLAIN));
            }
        } else if (isSelected) {
            setBackground(_jc.getSelectionBackground());
            setForeground(_jc.getSelectionForeground());
        } else {
            setBackground(_jc.getBackground());
            setForeground(_jc.getForeground());
        }
        if (value == null) {
            setText("");

        } else {
            setText(value.toString());
        }
        return this;
    }
    
    public void dereference(){
    	navHist = null;
    }
}
