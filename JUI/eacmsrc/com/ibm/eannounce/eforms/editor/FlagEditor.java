/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: FlagEditor.java,v $
 * Revision 1.4  2012/04/05 18:09:51  wendy
 * jre142 and win7 changes
 *
 * Revision 1.3  2008/02/19 16:36:37  wendy
 * Fix enhanced flag support
 *
 * Revision 1.2  2008/01/30 16:27:05  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:34  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:13  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:57  tony
 * This is the initial load of OPICM
 *
 * Revision 1.10  2005/09/08 17:59:03  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.9  2005/05/20 16:48:20  tony
 * refined single flag enhancement logic.
 *
 * Revision 1.8  2005/05/19 20:55:41  tony
 * repaired test logic.
 *
 * Revision 1.7  2005/05/19 20:41:49  tony
 * singleFlag UI Enhancement.
 *
 * Revision 1.6  2005/02/04 18:16:49  tony
 * JTest Formatter Fourth Pass
 *
 * Revision 1.5  2005/02/01 20:48:32  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/27 19:15:16  tony
 * JTest Format
 *
 * Revision 1.3  2004/04/16 20:23:34  tony
 * 53803 no shrink
 *
 * Revision 1.2  2004/04/16 17:16:40  tony
 * 53803
 *
 * Revision 1.1.1.1  2004/02/10 16:59:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.24  2003/12/08 23:35:52  tony
 * 53344
 *
 * Revision 1.23  2003/11/24 17:25:13  tony
 * 52887
 *
 * Revision 1.22  2003/11/18 20:54:04  tony
 * added formEditorInterface to simplify formEditor modification.
 *
 * Revision 1.21  2003/11/14 20:45:09  tony
 * 52862
 *
 * Revision 1.20  2003/09/22 15:33:58  tony
 * fixed null pointer on resize when null attribute.
 *
 * Revision 1.19  2003/09/22 15:21:23  tony
 * acl_20030922 --
 * fixed logic so that the flag attribute would refresh properly.
 *
 * Revision 1.18  2003/09/08 20:44:53  tony
 * 51732
 *
 * Revision 1.17  2003/09/08 19:55:12  tony
 * 51732
 *
 * Revision 1.16  2003/08/28 17:50:51  tony
 * updated popup logic to improve performance
 *
 * Revision 1.15  2003/08/25 16:58:57  tony
 * 51918
 *
 * Revision 1.14  2003/08/21 15:57:34  tony
 * 51869
 *
 * Revision 1.13  2003/08/14 15:34:36  tony
 * 51668
 *
 * Revision 1.12  2003/07/09 20:47:35  tony
 * 51422 -- blobEditor popup on double click
 *
 * Revision 1.11  2003/05/20 21:35:22  tony
 * 50827
 *
 * Revision 1.10  2003/05/14 19:00:11  tony
 * 50651
 *
 * Revision 1.9  2003/05/06 18:56:33  tony
 * 50546
 *
 * Revision 1.8  2003/05/06 16:38:21  tony
 * acl_20030506 -- enhanced focus logic to prevent error
 * messages from firing when refocusing the component.
 *
 * Revision 1.7  2003/05/01 22:41:36  tony
 * added static borders to address border rendering
 * issues on found.
 *
 * Revision 1.6  2003/04/30 21:40:47  tony
 * updated editor rendering on form.
 *
 * Revision 1.5  2003/04/21 17:30:19  tony
 * updated Color Logic by adding edit and found color
 * preferences to appearance.
 *
 * Revision 1.4  2003/04/17 23:13:26  tony
 * played around with editing Colors
 *
 * Revision 1.3  2003/04/14 21:37:23  tony
 * improved lookAndFeel handling.
 *
 * Revision 1.2  2003/03/21 20:54:31  tony
 * updated getBackground, getForeground, and getFont logic.
 * added logic so that each can be set for the component.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.32  2002/11/14 00:37:15  tony
 * made changes per dwb to correct deactivating issues
 *
 * Revision 1.31  2002/11/11 22:55:39  tony
 * adjusted classification on the toggle
 *
 * Revision 1.30  2002/11/11 16:59:15  tony
 * 23032
 *
 * Revision 1.29  2002/11/07 16:58:28  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.editor;
import com.elogin.*;
import com.ibm.eannounce.eforms.editform.FormEditorInterface; //acl_20030506
//acl_20030506 import com.ibm.eannounce.eForms.editor.Verifier.Verifier;
import com.ibm.eannounce.eobjects.EComboBox;
import com.ibm.eannounce.ui.FlagEditorUI;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.eannounce.eforms.editform.EditForm;

/**
 * This class is the combobox used for editing single flags
 */
public class FlagEditor extends EComboBox implements 
	EditorInterface, KeyListener, EAccessConstants, MouseListener 
{
	private static final long serialVersionUID = 1L;
	/**
     * maximumWidth
     */
    private static final int MAX_WIDTH = 500;
    private boolean displayOnly = false;
    private boolean found = false;
    private EANAttribute att = null;
    private MetaFlag curFlag = null;

    /**
     * changeEvent
     */
    transient protected ChangeEvent changeEvent = null;

    private SingleSelectionRenderer singleRend = new SingleSelectionRenderer();
    private String key = null;
    private FormEditorInterface ef = null; //acl_20030506
    private boolean isForm = false;
    private FlagEditorUI ui = null;
    private int minWidth = 0; //53803
	
    /**
     * flagEditor
     * @author Anthony C. Liberto
     */
    public FlagEditor() {
        super();

        setBorder(getCurrentBorder());
        setRenderer(singleRend);
        ui = new FlagEditorUI(this); // preload key event happens in diff instance.. one that didnt call updateui
		setUI(ui);// init in base class happens after updateui() so ui is lost
        setAutoscrolls(true);
        setMaximumRowCount(5);
        /*breaks win7 if (eaccess().isWindows()) {
            setLookAndFeel();
        }*/

        addKeyListener(this);
        addMouseListener(this); //50546
        if (EAccess.isArmed(ENHANCED_FLAG_EDIT)) {
			setEditable(true);
		}
    }

    /**
     * flagEditor - instantiated in EditForm
     * @param _att
     * @param _key
     * @param _ef
     */
    public FlagEditor(EANAttribute _att, String _key, FormEditorInterface _ef) { //acl_20030506
        this();
        ef = _ef;
        setAttribute(_att);
        setKey(_key);
        isForm = true;
	   
        // if EAccess.isArmed(ENHANCED_FLAG_EDIT) the focus goes to the text control (FlagComboEditor)
        // allow EditForm to receive events
        if ((editor instanceof FlagComboEditor) && (ef instanceof EditForm)){
        	((FlagComboEditor)editor).addFocusListener((com.ibm.eannounce.eforms.editform.EditForm)ef);
        }
    }
    
    /**
     * getBackground
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Color getBackground() {
    	if (isRequired()) {
            return EAccess.eaccess().getPrefColor(PREF_COLOR_REQUIRED, DEFAULT_COLOR_REQUIRED);
        }
    	if (!isDisplayOnly()){
    		return EAccess.eaccess().getPrefColor(PREF_COLOR_OK, DEFAULT_COLOR_OK);
    	}
    	return super.getBackground();
    }    
    /*
     * preferred size adjustment
     * -------------------------
     * should allow the flagEditor to
     * resize properly, the constants can be played
     * with some to make fine adjustments.
     */
    /**
     * @see java.awt.Component#getPreferredSize()
     * @author Anthony C. Liberto
     */
    public Dimension getPreferredSize() {
    	FontMetrics fm = getFontMetrics(getFont());
        if (isForm) { //53803
            minWidth = Math.max(minWidth, getDefaultWidth(fm)); //53803
            return new Dimension(minWidth, getDefaultHeight(fm)); //53803
        } else { //53803
            return new Dimension(getDefaultWidth(fm), getDefaultHeight(fm));
        } //53803
    }
  
    private int getDefaultWidth(FontMetrics fm) {
        int w = 100;
        int ii = getItemCount();
        for (int i = 0; i < ii; ++i) {
            String strItem = "";
            Object o = getItemAt(i);
            if (o != null) {
                strItem = o.toString();
            }
            w = Math.max(w, fm.stringWidth(strItem));
        }
        return w + 35;
    }

    private int getDefaultHeight(FontMetrics fm) {
        return fm.getHeight() + 4;
    }

    /*
     * end preferred size adjustment
     */

    /** JComboBox constructor calls this
     * @see javax.swing.JComponent#updateUI()
     * @author Anthony C. Liberto
     */
    public void updateUI() {
        ui = new FlagEditorUI(this);
        setUI(ui);
    }

    /**
     * setLookAndFeel
     *
     * @author Anthony C. Liberto
     */
    public void setLookAndFeel() {
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

    /**
     * getPopupWidth
     *
     * @return
     * @author Anthony C. Liberto
     */
    public int getPopupWidth() {
        if (isForm) { //22560
            return Math.min(MAX_WIDTH, getSize().width); //22560
        } else { //22560
            return Math.min(MAX_WIDTH, getPreferredSize().width); //22560
        } //22560
    }

    /**
     * repopulate
     * @author Anthony C. Liberto
     */
    public void repopulate() {
        refreshDisplay(getAttribute());
    }

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        removeKeyListener(this);
        removeMouseListener(this); //50546
        ef = null; //acl_20030506
    }

    /**
     * refresh this editor with the flags from the attribute it represents
     * @param _flag
     * @author Anthony C. Liberto
     */
    private void refresh(MetaFlag[] _flag) {
       	if (editor instanceof FlagComboEditor) {
			((FlagComboEditor)editor).refresh();
		}
       	removeAllItems();
       	
        curFlag = null;
        if (_flag != null) {
            for (int i = 0; i < _flag.length; ++i) {
            	MetaFlag flag = _flag[i];
            	if (flag.isSelected() && curFlag == null) {
                    curFlag = flag;
                }
                addItem(flag);
            }
        }
        
        // do this even if curflag is null, it clears the list box
        setSelectedItem(curFlag);
		if (editor instanceof FlagComboEditor) {
			((FlagComboEditor)editor).setOrig(curFlag);
		}
    }

    /**
     * getSelectedFlags
     * @return
     * @author Anthony C. Liberto
     */
    private MetaFlag[] getSelectedFlags() {
        Object o = getSelectedItem();
        if (o != null) {
            MetaFlag[] out = new MetaFlag[1];
            out[0] = (MetaFlag) o;
            out[0].setSelected(true);
            return out;
        }
        return new MetaFlag[0];
    }
    /*
     * renderer
     */
    private class SingleSelectionRenderer extends JLabel implements ListCellRenderer {
    	private static final long serialVersionUID = 1L;
    	/**
         * singleSelectionRenderer
         * @author Anthony C. Liberto
         */
        private SingleSelectionRenderer() {
            //			setOpaque(true);
        }

        /**
         * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
         * @author Anthony C. Liberto
         */
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            setFont(list.getFont());
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setOpaque(!isDisplayOnly());
            this.setText(value.toString());
            return this;
        }
    }

    /*
     * listeners
     */
    /**
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyPressed(KeyEvent kea) {
        int strKey = kea.getKeyCode(); //22819
        FlagEditorUI tmpUI = null;
        if (strKey == KeyEvent.VK_DELETE) { //22819
            return; //22819
        } //22819

        tmpUI = (FlagEditorUI) getUI(); //21551
        if (!tmpUI.isVisible() && isShowing()) { //21551
            showPopup(); //21551
        }
        //51732
    }
    /**
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyTyped(KeyEvent kea) {
    }
    /**
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyReleased(KeyEvent kea) {
        if (kea.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cancelEdit();
        } else if (kea.getKeyCode() == KeyEvent.VK_ENTER) {
            completeEdit();
         }
    }

    /*
     *editorInterface
     */
    /**
     * @see javax.swing.CellEditor#addCellEditorListener(javax.swing.event.CellEditorListener)
     * @author Anthony C. Liberto
     */
    public void addCellEditorListener(CellEditorListener c) {
        listenerList.add(CellEditorListener.class, c);
    }

    /**
     * @see javax.swing.CellEditor#cancelCellEditing()
     * @author Anthony C. Liberto
     */
    public void cancelCellEditing() {
        editingCanceled();
		if (editor instanceof FlagComboEditor) {
			((FlagComboEditor)editor).reset(); // reset anything users may have typed 
		}
    }

    /**
     * @see javax.swing.CellEditor#getCellEditorValue()
     * @author Anthony C. Liberto
     */
    public Object getCellEditorValue() {
        return getSelectedFlags();
    }

    /**
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     * @author Anthony C. Liberto
     */
    public Component getTableCellEditorComponent(JTable t, Object v, boolean sel, int r, int c) {
        setSelectedItem(curFlag);
        return this;
    }

    /**
     * @see javax.swing.CellEditor#isCellEditable(java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean isCellEditable(EventObject e) {
        //		if (att == null)
        //			return false;
        //		return att.getMetaAttribute().isEditable();
        return true;
    }

    /**
     * @see javax.swing.CellEditor#removeCellEditorListener(javax.swing.event.CellEditorListener)
     * @author Anthony C. Liberto
     */
    public void removeCellEditorListener(CellEditorListener c) {
        listenerList.remove(CellEditorListener.class, c);
    }

    /**
     * @see javax.swing.CellEditor#shouldSelectCell(java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean shouldSelectCell(EventObject e) {
        return true;
    }

    /**
     * @see javax.swing.CellEditor#stopCellEditing()
     * @author Anthony C. Liberto
     */
    public boolean stopCellEditing() {
        if (!canLeave()) {
            return false;
        }
        editingStopped();
        return true;
    }

    /**
     * editingStopped
     * @author Anthony C. Liberto
     */
    public void editingStopped() {
        Object[] listen = listenerList.getListenerList();
        for (int i = listen.length - 2; i >= 0; i -= 2) {
            if (listen[i] == CellEditorListener.class) {
                if (changeEvent == null) {
                    changeEvent = new ChangeEvent(this);
                }
                ((CellEditorListener) listen[i + 1]).editingStopped(changeEvent);
            }
        }
    }

    /**
     * editingCanceled
     * @author Anthony C. Liberto
     */
    protected void editingCanceled() {
        Object[] listen = listenerList.getListenerList();
        for (int i = listen.length - 2; i >= 0; i -= 2) {
            if (listen[i] == CellEditorListener.class) {
                if (changeEvent == null) {
                    changeEvent = new ChangeEvent(this);
                }
                ((CellEditorListener) listen[i + 1]).editingCanceled(changeEvent);
            }
        }
    }

    /**
     * setAttribute
     * @author Anthony C. Liberto
     * @param _att
     */
    public void setAttribute(EANAttribute _att) {
    	att = _att;
    	refresh((MetaFlag[]) att.get());
    }

    /**
     * getAttribute
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EANAttribute getAttribute() {
        return att;
    }

    /**
     * getMetaAttribute
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EANMetaAttribute getMetaAttribute() { //22098
        if (att != null) { //22098
            return att.getMetaAttribute();
        } //22098
        return null; //22098
    } //22098

    /**
     * hasChanged
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasChanged() { //acl_20021023
        return true; //acl_20021023
    } //acl_20021023

    /**
     * getKey
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getKey() {
        return key;
    }

    /**
     * setKey
     * @author Anthony C. Liberto
     * @param _key
     */
    public void setKey(String _key) {
        key = new String(_key);
    }

    /**
     * canLeave
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canLeave() {
        return true;
    }

    /**
     * setDisplayOnly
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setDisplayOnly(boolean _b) {
        if (!getAttribute().isEditable()) {
            _b = true;
        }
        displayOnly = _b;
        setEnabled(!_b);
        setBorder(getCurrentBorder());
        ui.setArrowVisible(!displayOnly);
        //50546		setFocusable(!_b);
    }

    /**
     * isDisplayOnly
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isDisplayOnly() {
        return displayOnly;
    }

    //51422	public void prepareToEdit() {
    //51869	public void prepareToEdit(EventObject _eo) {	//51422
    /**
     * prepareToEdit
     * @author Anthony C. Liberto
     * @param _c
     * @param _eo
     */
    public void prepareToEdit(EventObject _eo, Component _c) { //51869
        if (hasFocus()) { //53344
            return; //53344
        } //53344
        requestFocus();
        //51918		showPopup();
    }

    /**
     * refreshDisplay
     * @author Anthony C. Liberto
     * @param _att
     */
    public void refreshDisplay(EANAttribute _att) {
        //acl_20030922		refresh(_att);
        setAttribute(_att); //acl_20030922
        //acl_20031208		setSelectedItem(curFlag);
    }

    /**
     * copy
     *
     * @author Anthony C. Liberto
     */
    public void copy() {
    }

    /**
     * cut
     *
     * @author Anthony C. Liberto
     */
    public void cut() {
    }

    /**
     * paste
     *
     * @author Anthony C. Liberto
     */
    public void paste() {
    }

    /**
     * isRequired
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isRequired() {
        EANAttribute tmpAtt = getAttribute();
        if (tmpAtt != null) {
            return tmpAtt.isRequired();
        }
        return false;
    }

    /**
     * cancel
     *
     * @author Anthony C. Liberto
     */
    public void cancel() {
        refreshDisplay(getAttribute());
    }

    /**
     * deactivate
     *
     * @author Anthony C. Liberto
     */
    public void deactivate() {

    }

    /**
     * setEffectivity
     * @author Anthony C. Liberto
     * @param _from
     * @param _to
     */
    public void setEffectivity(String _from, String _to) {
    }
    /*
     * find replace
     */
    /**
     * getText
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getText() {
        Object o = getSelectedItem();
        if (o != null) {
            return o.toString();
        }
        return "";
    }

    /**
     * find
     * @author Anthony C. Liberto
     * @return boolean
     * @param _bCase
     * @param _strFind
     */
    public boolean find(String _strFind, boolean _bCase) {
        boolean bFound = false;
        if (!isShowing()) {
            return false;
        }
        //23032		found = com.ibm.opicm.client.routines.routines.find(getText(),_strFind,_bCase);
        //23032		if (found) {
        bFound = Routines.find(getText(), _strFind, _bCase); //23032
        if (bFound) { //23032
            found = true; //23032
            setBorder(getCurrentBorder());
        }
        return found;
    }

    /**
     * replace
     * @author Anthony C. Liberto
     * @return boolean
     * @param _bCase
     * @param _new
     * @param _old
     */
    public boolean replace(String _old, String _new, boolean _bCase) {
        return false;
    }

    /**
     * isFound
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isFound() {
        return found;
    }

    /**
     * resetFound
     *
     * @author Anthony C. Liberto
     */
    public void resetFound() {
        if (!found) {
            return;
        }
        found = false;
        setBorder(getCurrentBorder());
    }

    /**
     * removeEditor
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean removeEditor() {
        if (ui != null) { //50827
            setSelectedIndex(ui.getListSelection()); //50827
        } //50827
        return true;
    }

    /**
     * setDisplay
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setDisplay(String _s) {
    }

    private Border getCurrentBorder() {
        if (isFound()) {
            return EAccess.FOUND_BORDER;
        } else if (isDisplayOnly()) {
            return UIManager.getBorder("eannounce.emptyBorder");
        }
        return UIManager.getBorder("TextField.border");
    }

    /*
     * Information
     */
    /**
     * getInformation
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getInformation() {
        return Routines.getInformation(getAttribute());
    }

    /**
     * setText
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setText(String _s) {
    }

    //	public void setEditable(boolean _b) {
    //		dte.setEditable(_b);
    //	}
    //
    //	public boolean isEditable() {
    //		return dte.isEditable();
    //	}
    //
    //	public void requestFocus() {
    //		dte.requestFocus();
    //	}

    /**
     * isReplaceable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isReplaceable() { //22632
        return false; //22632
    } //22632

    /*
    acl_20030506
    */

    /**
     * getEditForm
     *
     * @return
     * @author Anthony C. Liberto
     */
    public FormEditorInterface getEditForm() {
        return ef;
    }

    /**
     * canReceiveFocus
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canReceiveFocus() {
        FormEditorInterface tmpEf = getEditForm();
        if (tmpEf != null) {
            return tmpEf.verifyNewFocus(this);
        }
        return true;
    }

    /**
     * @see java.awt.Component#requestFocus()
     * @author Anthony C. Liberto
     */
    public void requestFocus() {
        if (canReceiveFocus()) {    	
            super.requestFocus();
        }else{   
        	/* cant prevent popup from showing because it is popped up from the InvocationMouseHandler
        	 * after it calls requestFocus
        	 * This entire design is wrong, it should use a documentlistener and validate then
        	 * not by trying to control focus
        	 * 	at com.ibm.eannounce.eforms.editor.MetaUIValidator.generateErrorMessage(MetaUIValidator.java:129)
	at com.ibm.eannounce.eforms.editor.MetaValidator.isDecimalFormatted(MetaValidator.java:1425)
	at com.ibm.eannounce.eforms.editor.MetaValidator.isDecimalFormatted(MetaValidator.java:1369)
	at com.ibm.eannounce.eforms.editor.MetaValidator.canLeave(MetaValidator.java:757)
	at com.ibm.eannounce.eforms.editor.TextEditor.canLeave(TextEditor.java:492)
	at com.ibm.eannounce.eforms.editform.EditForm.verify(EditForm.java:3768)
	at com.ibm.eannounce.eforms.editform.EditForm.verifyNewFocus(EditForm.java:3748)
	at com.ibm.eannounce.eforms.editor.FlagEditor.canReceiveFocus(FlagEditor.java:933)
	at com.ibm.eannounce.eforms.editor.FlagEditor.requestFocus(FlagEditor.java:948)
	at javax.swing.plaf.basic.BasicComboPopup$InvocationMouseHandler.mousePressed(BasicComboPopup.java:657)
	at java.awt.AWTEventMulticaster.mousePressed(AWTEventMulticaster.java:244)
        	 */
			 // do this in a separate thread, later to hide it
            SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                    	hidePopup();
                    }
            });        	
        }
    }

    /**
     * @see java.awt.Component#requestFocus(boolean)
     * @author Anthony C. Liberto
     */
    public boolean requestFocus(boolean _temp) {
        if (canReceiveFocus()) {
            return super.requestFocus(_temp);
        }
        return false;
    }

    /**
     * @see javax.swing.JComponent#grabFocus()
     * @author Anthony C. Liberto
     */
    public void grabFocus() {
        if (canReceiveFocus()) {
            super.grabFocus();
        }
    }

    /**
     * equals
     * @author Anthony C. Liberto
     * @return boolean
     * @param _c
     */
    public boolean equals(Component _c) {
        if (_c != null) {
            if (_c instanceof EditorInterface) {
                return (this == (EditorInterface) _c);
            }
        }
        return true;
    }

    /*
     50546
    */

    /**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mousePressed(MouseEvent _me) {
        if (SwingUtilities.isLeftMouseButton(_me) && !isEnabled()) {
            requestFocus();
        }
    }
    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseReleased(MouseEvent _me) {
    }
    /**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseEntered(MouseEvent _me) {
    }
    /**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseExited(MouseEvent _me) {
    }
    /**
     * mouseDragged
     * @param _me
     * @author Anthony C. Liberto
     */
    public void mouseDragged(MouseEvent _me) {
    }
    /**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseClicked(MouseEvent _me) {
    }

    /**
     * preloadKeyEvent
     * @param _ke
     * @author Anthony C. Liberto
     */
    public void preloadKeyEvent(KeyEvent _ke) {
        if (getSelectedItem() == null) {
            JList list = ui.getList();
            if (list != null) {
                list.clearSelection();
            }
        }
        if (EAccess.isArmed(ENHANCED_FLAG_EDIT)) {
			((FlagComboEditor)editor).preloadKeyEvent(_ke);
		} else {
	        processKeyEvent(_ke);
		}
    }

    /**
     * @see java.awt.Component#processKeyEvent(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void processKeyEvent(KeyEvent _ke) {
        int iCode = _ke.getKeyCode();
        if (!_ke.isConsumed()) {
            if (EAccess.isArmed(ENHANCED_FLAG_EDIT)) {
				if (isEditKey(_ke)) {
					((FlagComboEditor)editor).processKeyEvent(_ke);
				} else if (isExitKey(_ke)) {
					super.processKeyEvent(_ke);
				} else {
					executeListKey(_ke);
				}
			} else {
	            //win7 super.processKeyEvent(_ke);
	            processMyKeyEvent(_ke);
	        }
			_ke.consume();
		}
		if (iCode == KeyEvent.VK_ENTER) {
			completeEdit();
		} else if (iCode == KeyEvent.VK_ESCAPE) {
			cancelEdit();
		}
    }
    /**
     * win7 and jre1.4.2 do not work well together with keystrokes, the display driver fails 
     * @param _ke
     */
    private void processMyKeyEvent(KeyEvent _ke) {
    	if(_ke.getID()==KeyEvent.KEY_PRESSED){
    		Object selectedItem = getModel().getSelectedItem();
    		Object obj =  selectionForKey(_ke.getKeyChar(), getModel());
    		if(obj !=null){
    			if(selectedItem!=obj){
    				hidePopup();
    				this.setSelectedItem(obj);
    				showPopup();
    			}
    		}
    	}
    	super.processKeyEvent(_ke);
    }
    /**
     * win7 and jre1.4.2 do not work well together with keystrokes, the display driver fails 
     * this will find the item that would match the key
     * @param aKey
     * @param aModel
     * @return
     */
    private Object selectionForKey(char aKey,ComboBoxModel aModel) {
        int i,c;
        int currentSelection = -1;
        Object selectedItem = aModel.getSelectedItem();
        String v;
        String pattern;

        if ( selectedItem != null ) {
            for ( i=0,c=aModel.getSize();i<c;i++ ) {
                if ( selectedItem == aModel.getElementAt(i) ) {
                    currentSelection  =  i;
                    break;
                }
            }
        }

        pattern = ("" + aKey).toLowerCase();
        aKey = pattern.charAt(0);

        for ( i = ++currentSelection, c = aModel.getSize() ; i < c ; i++ ) {
            Object elem = aModel.getElementAt(i);
            if (elem != null && elem.toString() != null) {
                v = elem.toString().toLowerCase();
                if ( v.length() > 0 && v.charAt(0) == aKey )
                    return elem;
            }
        }

        for ( i = 0 ; i < currentSelection ; i ++ ) {
            Object elem = aModel.getElementAt(i);
            if (elem != null && elem.toString() != null) {
                v = elem.toString().toLowerCase();
                if ( v.length() > 0 && v.charAt(0) == aKey )
                    return elem;
            }
        }
        return null;
    }   
    /*
     acl_20030922
     */
    /**
     * getAttributeCode
     * @return
     * @author Anthony C. Liberto
     */  
    public String getAttributeCode() {
        if (att != null) {
            return att.getAttributeCode();
        }
        return "no attribute code available";
    }

/*
 Enhanced
 */
	/**
	 * isDisplayablePopup
	 * enhanced
	 * @return boolean
	 * @author Anthony C. Liberto
	 */
	public boolean isDisplayablePopup() {
		return !EAccess.isArmed(ENHANCED_FLAG_HIDE_POPUP);
	}

	/**
	 * setEditor
	 * enhanced
	 * @param _editor
	 * @author Anthony C. Liberto
	 */
	public void setEditor(ComboBoxEditor _editor) {
		if (editor != null && editor instanceof FlagComboEditor && _editor == null) {
			((FlagComboEditor)editor).dereference();
		}
		super.setEditor(_editor);
	}

	private boolean isEditKey(KeyEvent _ke) {
		if (_ke != null) {
			int iCode = _ke.getKeyCode();
			switch (iCode) {
            case KeyEvent.VK_UP:
                return false;
			case KeyEvent.VK_DOWN:
				return false;
			case KeyEvent.VK_ENTER:
				return false;
			case KeyEvent.VK_ESCAPE:
				return false;
			default:
				return true;
			}
		}
		return false;
	}

	private boolean isExitKey(KeyEvent _ke) {
		if (_ke != null) {
			int iCode = _ke.getKeyCode();
			switch (iCode) {
			case KeyEvent.VK_ENTER:
				return true;
			case KeyEvent.VK_ESCAPE:
				return true;
			default:
				return false;
			}
		}
		return false;
	}

    /**
     * @see javax.swing.JComboBox#setSelectedIndex(int)
     * @author Anthony C. Liberto
     */
    public void setSelectedIndex(int _i) {
        super.setSelectedIndex(_i);
        /* this breaks cascading flags if done from setting prediction.. but is needed if user
         * manually selects from list 
         */ 
        if (isForm && ef != null) {
            try {
                ef.commit(this);
            } catch (Exception _x) {
                _x.printStackTrace();
            }
            if (editor instanceof FlagComboEditor) { 
				//reset variables so if ESCAPE is pressed, nothing breaks			
				((FlagComboEditor)editor).accepted();
			}            
        }
    }	
	/**
	 * complete edit
	 * @author Anthony C. Liberto
	 */
	public void completeEdit() {
		// must commit the selected index here for cascading flags when prediction is used
		if (isForm && ef != null) { //must commit the selected index here for cascading flags
			try {
				ef.commit(this);
			} catch (Exception _x) {
				_x.printStackTrace();
			}
		}	

		if (stopCellEditing()){
			if (isForm && editor instanceof FlagComboEditor) { 		
				((FlagComboEditor)editor).accepted();
			}
		}
		hidePopup();
	}

	/**
	 * cancel edit
	 * @author Anthony C. Liberto
	 */
	public void cancelEdit() {
		cancelCellEditing();
        hidePopup();
	}

	/**
     * executeListKey
     *
     * @param _ke
     * @author Anthony C. Liberto
     */
    public void executeListKey(KeyEvent _ke) {
		//win7 super.processKeyEvent(_ke);
		  processMyKeyEvent(_ke);
	}
}
