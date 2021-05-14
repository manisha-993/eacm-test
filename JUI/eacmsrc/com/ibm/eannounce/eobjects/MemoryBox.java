/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: MemoryBox.java,v $
 * Revision 1.3  2012/04/19 11:30:24  wendy
 * correct combobox losing newly typed values
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
 * Revision 1.4  2003/05/21 21:29:14  tony
 * 50587
 *
 * Revision 1.3  2003/05/12 23:06:37  tony
 * 50623
 *
 * Revision 1.2  2003/03/17 23:32:31  tony
 * accessibility update.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:52  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2002/11/07 16:58:18  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import com.ibm.eannounce.ui.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class MemoryBox extends EComboBox implements KeyListener {
	private static final long serialVersionUID = 1L;
	private int max = 1;
	private Dimension dDim = new Dimension(250,25);

    private static final String LIGHTWEIGHT_KEYBOARD_NAVIGATION = "JComboBox.lightweightKeyboardNavigation";			//013080
    private static final String LIGHTWEIGHT_KEYBOARD_NAVIGATION_ON = "Lightweight";										//013080
//    private static final String LIGHTWEIGHT_KEYBOARD_NAVIGATION_OFF = "Heavyweight";									//013080

	/**
     * memoryBox
     * @author Anthony C. Liberto
     */
    public MemoryBox() {
		this(10);
		return;
	}

	/**
     * memoryBox
     * @param _max
     * @author Anthony C. Liberto
     */
    public MemoryBox(int _max) {
		super();
		max = _max;
		setEditable(true);
		setMinimumSize(dDim);
		setPreferredSize(dDim);
		setSize(dDim);
		init();
		setUI(new MemoryBoxUI(this));
		((JTextField)getEditor().getEditorComponent()).setInputVerifier(new InputVerifier(){
			  public boolean verify(JComponent input){
					// force any newly typed entry into combolist
					commit();	
					return true;
			  }
		});
		

		return;
	}

	/**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
		setBorder(UIManager.getBorder("eannounce.loweredBorder"));
		setBackground(Color.white);
		putClientProperty(LIGHTWEIGHT_KEYBOARD_NAVIGATION,LIGHTWEIGHT_KEYBOARD_NAVIGATION_ON);
		addKeyListener(this);
		return;
	}

	/**
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyPressed(KeyEvent kea) {}
	/**
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyTyped(KeyEvent kea) {}
	/**
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyReleased(KeyEvent kea) {
		if (kea.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cancelCellEditing();
			hidePopup();
		} else if (kea.getKeyCode() == KeyEvent.VK_ENTER) {
			MemoryBoxUI ui = (MemoryBoxUI)getUI();
			int i = ui.getListSelection();
			returnSelected(i);
			stopCellEditing();
			hidePopup();
		} else if (kea.getKeyCode() == KeyEvent.VK_UP || kea.getKeyCode() == KeyEvent.VK_DOWN) {							//cleanup
			MemoryBoxUI ui = (MemoryBoxUI)getUI();
			if (!ui.isVisible()) {
				showPopup();
			}
		}
		return;
	}

	/**
     * processEnter
     * @author Anthony C. Liberto
     */
    public void processEnter() {}													//50857

	/**
     * commit
     * @author Anthony C. Liberto
     */
    public void commit() {
		String str = getText();
		if (str == null) {
            return;
		}
		insertText(str);
		return;
	}

	private void insertText(String _str) {	
		if (!Routines.have(_str)) {
            return;
		}
		if (firstItem(_str)) {
            return;
		}

		removeDuplicate(_str);
		insertItemAt(_str,0);
		if (getItemCount() > max) {
			removeItemAt(max);
		}
		setText(_str);
		super.setSelectedIndex(0); // force the new item to be the selected item
		return;
	}

	private void removeDuplicate(String _str) {
		int ii = getItemCount();
		for (int i=1;i<ii;++i) {
			Object o = getItemAt(i);
			if (o instanceof String) {
				if (((String)o).equals(_str)) {
					removeItemAt(i);
					return;
				}
			}
		}
		return;
	}

	private boolean firstItem(String _str) {
		Object o = null;
        if (getItemCount() == 0) {
            return false;
		}
		o = getItemAt(0);
		if (o instanceof String) {
			if (((String)o).equals(_str)) {
				return true;
			}
		}
		return false;
	}

	/**
     * getText
     * @return
     * @author Anthony C. Liberto
     */
    public String getText() {
		Object o = getItem();
		if (o != null) {
			return o.toString();
		}
		return null;
	}

	/**
     * setText
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setText(String _s) {
		getEditor().setItem(_s);
	}

	/**
     * setText
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setText(int _i) {
		Object o = getItemAt(_i);
		if (o instanceof String) {
			setText((String)o);
		}
	}

	/**
     * getItem
     * @return
     * @author Anthony C. Liberto
     */
    public Object getItem() {
		return getEditor().getItem();
	}

	/**
     * selectAll
     * @author Anthony C. Liberto
     */
    public void selectAll() {
		getEditor().selectAll();
	}

/*
not used because not sure if find should save found values for the session.
there is additionally only one find for the entire application so it gets
further diluted if you take into account profiles.

	public Object[] getValues() {
		int ii = getItemCount();
		Object[] out = new Object[ii];
		for (int i=0;i<ii;++i) {
			out[i] = getItemAt(i);
		}
	}

	public void setValues(Object[] _o) {
		int ii = _o.length;
		for (int i=0;i<ii;++i)
			addItem(_o[i]);
		return;
	}
*/
	/**
     * cancelCellEditing
     * @author Anthony C. Liberto
     */
    public void cancelCellEditing() {return;}
	/**
     * stopCellEditing
     * @return
     * @author Anthony C. Liberto
     */
    public boolean stopCellEditing() {return true;}
	/**
     * returnSelected
     * @param _i
     * @author Anthony C. Liberto
     */
    public void returnSelected(int _i) {return;}

	/**
     * @see javax.swing.JComboBox#setSelectedIndex(int)
     * @author Anthony C. Liberto
     */
    public void setSelectedIndex(int _i) {
		setText(_i);
		return;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		dDim = null;
		((JTextField)getEditor().getEditorComponent()).setInputVerifier(null);
		removeKeyListener(this);
		removeAll();
		return;
	}

/*
 50623
*/
	/**
     * @see java.awt.Component#requestFocus()
     * @author Anthony C. Liberto
     */
    public void requestFocus() {
		ComboBoxEditor editor = getEditor();
		if (editor != null) {
			Component c = editor.getEditorComponent();
			if (c instanceof JComponent) {
				((JComponent)c).requestFocus();
				return;
			}
		}
		super.requestFocus();
		return;
	}

}
