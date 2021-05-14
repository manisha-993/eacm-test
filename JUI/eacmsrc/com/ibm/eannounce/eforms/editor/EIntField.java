/**
 * Copyright (c) 2001 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * create a textbox that only allows for the entry of integers
 *
 * @version 2.3
 * @date 2001-02-02
 * @author Anthony C. Liberto
 *
 * $Log: EIntField.java,v $
 * Revision 1.2  2008/01/30 16:27:04  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:34  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:57  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:59:03  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/01 20:48:32  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/27 19:15:16  tony
 * JTest Format
 *
 * Revision 1.3  2004/10/25 17:36:28  tony
 * improved size/sort functionality.
 *
 * Revision 1.2  2004/10/22 22:27:35  tony
 * adjsuted logic for autosize/sort
 *
 * Revision 1.1.1.1  2004/02/10 16:59:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2003/05/16 18:23:35  tony
 * updated logic to enhance functionality
 *
 * Revision 1.2  2003/04/30 21:40:47  tony
 * updated editor rendering on form.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2002/06/28 19:51:06  tony
 * memory update matrix.
 *
 * Revision 1.2  2002/02/13 19:27:45  tony
 * trace statement adjustment.
 *
 * Revision 1.1.1.1  2001/11/29 19:00:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2001/08/06 21:39:12  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2001/06/05 01:07:21  tony
 * 013031 -- Conflict between navigation and dial back in time.
 *
 * Revision 1.2  2001/05/23 15:50:59  tony
 * wayback processing.
 *
 * Revision 1.1.1.1  2001/04/19 00:59:10  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2001/03/22 22:18:06  tony
 * adjusted log information to remove duplicates
 *
 * Revision 1.3  2001/03/22 21:10:10  tony
 * Added standard copyright to all
 * modules
 *
 * Revision 1.2  2001/03/22 18:54:46  tony
 * added log keyword
 *
 */
package com.ibm.eannounce.eforms.editor;
import com.elogin.Routines;
import com.ibm.eannounce.eobjects.ETextField;
import java.awt.event.*;
import javax.swing.text.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EIntField extends ETextField implements KeyListener {
	private static final long serialVersionUID = 1L;
	private int maxL;
    private int maxValue = -1;
    private int minValue = -1;
    private boolean wayBack = false;
    private boolean bPrepend = true;

    /**
     * create a new gintfield
     *
     * @param col
     */
    public EIntField(int col) {
        super(col);
        maxL = col;
        return;
    }

    /**
     * create a new gintfield with a length limitation
     *
     * @param b
     * @param col
     * @param max
     */
    public EIntField(int col, int max, boolean b) {
        super(col);
        maxL = col;
        setMaxValue(max);
        wayBack = b;
        return;
    }

    /**
     * getMaxValue
     * @return
     * @author Anthony C. Liberto
     */
    public int getMaxValue() {
        return maxValue;
    }

    /**
     * setMaxValue
     * @param i
     * @author Anthony C. Liberto
     */
    public void setMaxValue(int i) {
        maxValue = i;
        return;
    }

    /**
     * setMinValue
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setMinValue(int _i) {
        minValue = _i;
        return;
    }

    /**
     * getMinValue
     * @return
     * @author Anthony C. Liberto
     */
    public int getMinValue() {
        return minValue;
    }

    /**
     * setPrepend
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setPrepend(boolean _b) {
        bPrepend = _b;
        return;
    }

    /**
     * @see java.awt.Component#isValid()
     * @author Anthony C. Liberto
     */
    public boolean isValid() {
        int i = getValue();
        if (minValue >= 0) {
            if (i < minValue) {
                return false;
            }
        }
        if (maxValue >= 0) {
            if (i > maxValue) {
                return false;
            }
        }
        return true;
    }

    /**
     * create a new document for the gintfield
     *
     * @return Document
     */
    protected Document createDefaultModel() {
        return new MaxDoc();
    }

    /**
     * retrieve the text from the gintfield
     *
     * @return String
     */

    public String getText() {
        String s = super.getText();
        if (bPrepend) {
            int len = s.length();
            if (len == 1) {
                return "0" + s;
            } else if (len == 0) {
                return "00";
            }
        }
        return s;
    }

    /**
     * getValue
     * @return
     * @author Anthony C. Liberto
     */
    public int getValue() {
        return Integer.valueOf(getText()).intValue();
    }

    /**
     * setValue
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setValue(int _i) {
        setText(Routines.toString(_i));
        return;
    }

    /**
     * inster the string monitor to ensure that only ints are enetered
     */
    private class MaxDoc extends PlainDocument {
    	private static final long serialVersionUID = 1L;
    	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) {
                return;
            }
            if ((getLength() + str.length()) <= maxL) {
                if (charValidation(str)) {
                    super.insertString(offs, str, a);
                }
            } else {
                lengthExceeded();
            }
            return;
        }
    }

    /**
     * validate the enetered character to guarantee that it is an int
     */
    private boolean charValidation(String in) {
        char[] chr = in.toCharArray();
        int x = chr.length;
        for (int i = 0; i < x; ++i) {
            if (!Character.isDigit(chr[i])) {
                nonDigit();
                return false;
            }
        }
        if (maxValue >= 0) {
            try {
                String s = getText() + in;
                int i = Integer.valueOf(s).intValue();
                if (i > maxValue) {
                    maxValueExceeded();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                maxValueExceeded();
                return false;
            }
        }
        return true;
    }

    /**
     * the key was released
     *
     * @param kea
     */
    public void keyReleased(KeyEvent kea) {
        if (kea.getKeyCode() == KeyEvent.VK_ENTER) {
            //restore.populateRestore() {;
            return;
        }
    }
    /**
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyPressed(KeyEvent keb) {
    }
    /**
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyTyped(KeyEvent kec) {
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        removeAll();
        removeNotify();
        return;
    }

    /*
     warning over-rides

     */
    /**
     * nonDigit
     * @author Anthony C. Liberto
     */
    public void nonDigit() {
        return;
    }

    /**
     * lengthExceeded
     * @author Anthony C. Liberto
     */
    public void lengthExceeded() {
        return;
    }

    /**
     * maxValueExceeded
     * @author Anthony C. Liberto
     */
    public void maxValueExceeded() {
        return;
    }
}
