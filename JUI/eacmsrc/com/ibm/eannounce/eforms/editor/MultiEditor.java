// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
 *
 * $Log: MultiEditor.java,v $
 * Revision 1.3  2008/02/20 20:13:37  wendy
 * Add focuslistener to make getinfo work
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
 * Revision 1.1.1.1  2005/09/09 20:37:58  tony
 * This is the initial load of OPICM
 *
 */
package com.ibm.eannounce.eforms.editor;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.eforms.editform.FormEditorInterface; //acl_20030506
//acl_20030506 import com.ibm.eannounce.eForms.editor.Verifier.Verifier;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import COM.ibm.eannounce.objects.*;
import javax.accessibility.AccessibleContext;

//52278 public class multiEditor extends eScrollPane implements editorInterface, MouseListener, KeyListener , FocusListener {
/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class MultiEditor extends EScrollPane implements EditorInterface, MouseListener, 
KeyListener, FocusListener, AncestorListener, ActionListener { //52278
	private static final long serialVersionUID = 1L;
	//    private final int BOTTOM_FUDGE_FACTOR = 50;
//    private final int SIDE_FUDGE_FACTOR = 25;

    private boolean found = false;
    transient protected ChangeEvent changeEvent = null;
    protected EventListenerList listenerList = new EventListenerList();

    private ETextArea pEdit = new ETextArea();
    private MultiPopup popup = null;

    private EANAttribute att = null;
    private String key = null;

    private String colKey = null;

    private Dimension formSize = null;

    private RowSelectableTable table = null;
    private FormEditorInterface ef = null; //acl_20030506

    private boolean isForm = false;
    private boolean singleTable = false;

    private boolean editable = true;
    private MultiColHeader colHeader = new MultiColHeader();
    private EViewport colPort = new EViewport(true);
    //acl_20030506	private Verifier verify = null;
    private EButton btnSave = new EButton(getString("save")); //52499
    private EButton btnCncl = new EButton(getString("cncl")); //52499
    private EPanel pnlBtn = new EPanel(new GridLayout(1, 2, 5, 5)); //52499

    /**
     * multiEditor
     * @param _vertical
     * @author Anthony C. Liberto
     */
    public MultiEditor(boolean _vertical) {
        super();
        //52730		if (_vertical) {
        if (_vertical && eaccess().isVertFlagFrame()) { //52730
            initVerticalTable();
        } else {
            initHorizontalTable();
        }
        setSingleTable(_vertical);
        pEdit.setFocusable(false);
        pEdit.setUseBack(true);
        initAccessibility("accessible.multiEditor");
    }

    //acl_20030506	public multiEditor(EANAttribute _att, String _key, RowSelectableTable _table, Verifier _verify) {
    /**
     * multiEditor
     * @param _att
     * @param _key
     * @param _ef
     * @author Anthony C. Liberto
     */
    public MultiEditor(EANAttribute _att, String _key, FormEditorInterface _ef) { //acl_20030506
        super();
        isForm = true; 
        ef = _ef; //acl_20030506
        setAttribute(_att);
        initForm();
        setSingleTable(true);
        setKey(_key);
        if (_att == null) { //52494
            refreshDisplay();
        } else { //52494
            refreshDisplay(_att); //52494
        } //52494
        //		loadPopup();
        //acl_20030506		pEdit.setInputVerifier(_verify);
        //acl_20030506		verify = _verify;
        pEdit.setUseBack(true);
        pEdit.setFocusable(false);
        initAccessibility("accessible.multiEditor");
    }

    /*
     * needs to be adjusted so that horizontal in
     * particular works correctly.  This is currently
     * the only editor that has a problem.  It might
     * be resolvable somewhere else, i am not sure yet.
     */
    /**
     * @see java.awt.Component#setBounds(java.awt.Rectangle)
     * @author Anthony C. Liberto
     */
    public void setBounds(Rectangle _rect) {
        //		if (!isForm)
        //			colHeader.setHorizontalBounds(_rect);
        super.setBounds(_rect);
    }

    /**
     * getBackground
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Color getBackground() {
        if (isRequired()) {
            return getPrefColor(PREF_COLOR_REQUIRED, DEFAULT_COLOR_REQUIRED);
        }
        return getPrefColor(PREF_COLOR_OK, DEFAULT_COLOR_OK);
    }

    /**
     * setSingleTable
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setSingleTable(boolean _b) {
        singleTable = _b;
    }

    private void refreshDisplay() {
        if (table != null) {
            //50474			if (isForm || singleTable) {
            //50474				Object o = table.get(table.getRowIndex(getKey()), 1);
            Object o = table.get(table.getRowIndex(getKey()), 1, singleTable); //50474
            if (o instanceof String) {
                setDisplay((String) o);
            }
            //50474			}
        }
        if (ef != null) { //acl_20030506
            RowSelectableTable tmpTable = ef.getTable(); //acl_20030506
            Object o = tmpTable.get(tmpTable.getRowIndex(getKey()), 1, singleTable); //acl_20030506
            if (o instanceof String) { //acl_20030506
                setDisplay((String) o); //acl_20030506
            } //acl_20030506
        } //acl_20030506
    }

    private void initGenericTable() {
        colPort.setView(colHeader);
        //		setColumnHeaderView(colHeader);
        colHeader.addMouseListener(this);
        colHeader.addKeyListener(this);
        colHeader.addListener(this); //acl_20021023
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setViewportView(pEdit);
        pEdit.setEditable(false);
        pEdit.addMouseListener(this);
        addKeyListener(popup.getKeyListener());
        initGeneric();
    }

    private void initHorizontalTable() {
        popup = new MultiPopupHorz() {
        	private static final long serialVersionUID = 1L;
        	public void popupTextUpdate(String _s) {
				colHeader.setLabelText(_s);
			}
		};
        initGenericTable();
        popup.setColumnHeader(colPort);
    }

    private void initVerticalTable() {
        popup = new MultiPopupVert() {
        	private static final long serialVersionUID = 1L;
        	public void popupTextUpdate(String _s) {
				colHeader.setLabelText(_s);
			}
		};
        colHeader.setButtonsVisible(false);
        btnSave.setActionCommand("btnSave"); //52499
        btnCncl.setActionCommand("btnCncl"); //52499
        btnSave.addActionListener(this); //52499
        btnCncl.addActionListener(this); //52499
        btnSave.setMnemonic(eaccess().getChar("save-s"));
        btnCncl.setMnemonic(eaccess().getChar("cncl-s"));
        popup.setColumnHeader(colPort);
        popup.addButtons(btnSave, btnCncl);
        initGenericTable();
    }

    private void initForm() {
        popup = new MultiPopupHorz() {
        	private static final long serialVersionUID = 1L;
        	public void popupTextUpdate(String _s) {
				colHeader.setLabelText(_s);
			}
		};

		// this is needed to get focus events for things like attribute information
        ((MultiPopupHorz)popup).getDisplayComponent().addFocusListener(this);
     	 		
        pnlBtn.add(btnSave); //52499
        pnlBtn.add(btnCncl); //52499
        btnSave.setActionCommand("btnSave"); //52499
        btnCncl.setActionCommand("btnCncl"); //52499
        btnSave.addActionListener(this); //52499
        btnCncl.addActionListener(this); //52499
        colPort.setView(pnlBtn); //52499

        setColumnHeader(colPort);
        //52499		colHeader.addMouseListener(this);
        //52499		colHeader.addKeyListener(this);
        //52499		colHeader.addListener(this);		//acl_20021023

        //setEditable(true);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //52499		setViewportView(pEdit);
        setViewportView(popup.getDisplayComponent()); //52499

        formSize = generateSize(colHeader.getPreferredWidth());
        setSize(formSize);
        setPreferredSize(formSize);
        setMaximumSize(formSize);
        //52499		pEdit.setEditable(false);
        //52499		pEdit.addMouseListener(this);
        initGeneric();
    }

    private Dimension generateSize(int _width) {
        return new Dimension(_width + 25, 200);
    }

    private void initGeneric() {
    	pEdit.setRequestFocusEnabled(false); 
        getHorizontalScrollBar().setRequestFocusEnabled(false);
        getVerticalScrollBar().setRequestFocusEnabled(false);
        popup.setEditor(this);
        addAncestorListener(this); //52278
    }

    /**
     * setColumnName
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setColumnName(String _s) {
        colHeader.setDescription(_s);
    }

    /**
     * getColumnName
     * @return
     * @author Anthony C. Liberto
     */
    public String getColumnName() {
        return colHeader.getDescription();
    }

    /**
     * setMinimumWidth
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setMinimumWidth(int _i) {
        colHeader.setMinimumWidth(_i);
    }

    /**
     * getMinimumWidth
     * @return
     * @author Anthony C. Liberto
     */
    public int getMinimumWidth() {
        return colHeader.getMinimumWidth();
    }

    /**
     * getHeader
     * @return
     * @author Anthony C. Liberto
     */
    public MultiColHeader getHeader() { //acl_20021023
        return colHeader; //acl_20021023
    } //acl_20021023

    /*
     * listeners
     */
    /**
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyPressed(KeyEvent _ke) {
    }
    /**
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyReleased(KeyEvent _ke) {
    }
    /**
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyTyped(KeyEvent _ke) {
        if (_ke.getKeyCode() == KeyEvent.VK_ENTER) {
            performAction("arrow");
            _ke.consume();
        }
    }

    /**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mousePressed(MouseEvent _me) {
    	Component c = _me.getComponent();
        if (c == pEdit) {
            performAction("arrow");
        } else {
            String action = colHeader.getActionCommand(c);
            performAction(action);
        }
    }

    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseReleased(MouseEvent _me) {
    }

    /**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseClicked(MouseEvent _me) {      	
        //50474		processMouseEvent(_me);						//21898
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
     * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
     * @author Anthony C. Liberto
     */
    public void focusGained(FocusEvent _fe) { //acl_20021023  	
        processFocusEvent(new FocusEvent(this, _fe.getID())); //acl_20021023
    } //acl_20021023

    /**
     * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
     * @author Anthony C. Liberto
     */
    public void focusLost(FocusEvent _fe) { //acl_20021023
        processFocusEvent(new FocusEvent(this, _fe.getID())); //acl_20021023
    } //acl_20021023

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) { //52499
        if (_ae != null) { //52499
            performAction(_ae.getActionCommand()); //52499
        } //52499
    } //52499

    /**
     * performAction
     * @param _action
     * @author Anthony C. Liberto
     */
    private void performAction(String _action) {
    	if (_action.equals("arrow")) {
            if (isForm) { //52499
                acceptChanges(); //52499
            } else if (!isPopupVisible()) {
                showPopup();
            } else {
                acceptChanges();
            }
        } else if (_action.equals("cancel")) {
            if (isForm) { //52499
                discardChanges(); //52499
            } else if (isPopupVisible()) {
                discardChanges();
            } else {
                showPopup();
            }
        } else if (_action.equals("btnSave")) { //52499
            acceptChanges(); //52499
        } else if (_action.equals("btnCncl")) { //52499
            discardChanges(); //52499
        } //52499
    }

    /**
     * isPopupVisible
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPopupVisible() {
        if (isForm) { //53142
            return true; //53142
        } //53142
        return popup.isVisible();
    }

    /**
     * discardChanges
     * @author Anthony C. Liberto
     */
    public void discardChanges() {
        if (!isForm) {
            cancelCellEditing();
        } else {
            hidePopup();
        }
        cancelChanges();
    }

    private void cancelChanges() {
        if (isForm) { //53146
            loadPopup(); //53146
            return; //53146
        } //53146
        if (isPopupVisible()) {
            hidePopup();
        }
        popup.cancelChanges();
    }

    /**
     * acceptChanges
     * @author Anthony C. Liberto
     */
    public void acceptChanges() {
        if (isForm) {
            updateForm();
        } else {
            hidePopup();
            stopCellEditing();
        }
    }

    private void updateForm() {
//        String sKey = getKey(); //21923
        try {
            ef.commit(this); //acl_20030506
            //acl_20030506			table.put(table.getRowIndex(getKey()), 1, getCellEditorValue());
            //acl_20030506			if (verify != null) {
            //acl_20030506				verify.tableUpdate(att);					//21923
            //acl_20030506			}
        } catch (EANBusinessRuleException _bre) {
            _bre.printStackTrace();
            return;
        }
        refreshDisplay();
    }

    /**
     * showPopup
     * @author Anthony C. Liberto
     */
    public void showPopup() {
        //53123		if (isDisplayOnly())
        Point pt = null;
        if (isDisplayOnly() || isForm) { //53123
            return;
        } //53123
        pt = getPopupPosition();

        loadPopup();
        popup.setModalCursor(true); //51336
        popup.show(this, pt.x, pt.y);
        delegateFocus();
    }

    private void delegateFocus() {
        popup.acquireFocus();
    }

    /*
     acl_20030829
    	private Point getPopupPosition() {
    		Dimension pSize = popup.getScrollSize();
    		Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
    		Dimension sSize = eaccess().getLogin().getSize();			//51981
    		Dimension mSize = null;
    		if (isForm) {
    			mSize = colHeader.getSize();
    		} else {
    			mSize = UIManager.getDimension("eannounce.minimum");		//50608
    		}
    		Point pt = getLocationOnScreen();
    		pt.setLocation(getX(pt.x,mSize.width,pSize.width,sSize.width), getY(pt.y,mSize.height,pSize.height,sSize.height));
    		SwingUtilities.convertPointFromScreen(pt,this);
    		return pt;
    	}

    	private int getX(int _x, int _myWidth, int _popupWidth, int _screenWidth) {
    		if ((_x + _popupWidth) > (_screenWidth - SIDE_FUDGE_FACTOR)) {
    			return (_x + _myWidth) - _popupWidth;
    		} else {
    			return _x;
    		}
    	}

    	private int getY(int _y, int _myHeight, int _popupHeight, int _screenHeight) {
    		if ((_y + _popupHeight + _myHeight) > (_screenHeight - BOTTOM_FUDGE_FACTOR)) { //22491
    			return _y -(_popupHeight - _myHeight);
    		} else {
    			return _y;
    		}
    	}
    */
    /**
     * hidePopup
     * @author Anthony C. Liberto
     */
    public void hidePopup() {
        //51981		popup.setVisible(false);
        popup.showHide(false); //51981
    }

    /**
     * loadPopup
     * @author Anthony C. Liberto
     */
    public void loadPopup() {
    	popup.refresh(att);
    }

    /**
     * setEditable
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setEditable(boolean _b) {
        editable = _b;
        if (isForm) { //52499
            JComponent jc = popup.getDisplayComponent(); //52499
            jc.setEnabled(_b); //52499
            setOpaque(!_b); //52499
            btnSave.setEnabled(_b); //52499
            btnCncl.setEnabled(_b); //52499
        } else if (_b) {
            colPort.setView(colHeader);
        } else {
            colPort.setView(null);
        }
        //		colHeader.setButtonsVisible(_b);
        return;
    }

    /**
     * isEditable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * setText
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setText(String _s) {
        pEdit.setText(_s);
    }

    /**
     * getText
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getText() {
        return pEdit.getText();
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
    }

    /**
     * @see javax.swing.CellEditor#getCellEditorValue()
     * @author Anthony C. Liberto
     */
    public Object getCellEditorValue() {
        return popup.getMultipleSelection();
    }

    /**
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     * @author Anthony C. Liberto
     */
    public Component getTableCellEditorComponent(JTable t, Object v, boolean sel, int r, int c) {
        //50474		if (v instanceof MultiFlagAttribute) {
        //50474			MultiFlagAttribute att = (MultiFlagAttribute)v;
        //50474			setDisplay(att.get().toString());
        //50474		}
        return this;
    }

    /**
     * @see javax.swing.CellEditor#isCellEditable(java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean isCellEditable(EventObject e) {
        return true;
    }

    /**
     * @see javax.swing.CellEditor#removeCellEditorListener(javax.swing.event.CellEditorListener)
     * @author Anthony C. Liberto
     */
    public void removeCellEditorListener(CellEditorListener c) {
        hidePopup();
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
        refreshDisplay();
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
        colHeader.setDescription(att.getMetaAttribute().getLongDescription());
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
     * setColumnKey
     * @param _key
     * @author Anthony C. Liberto
     */
    public void setColumnKey(String _key) {
        colKey = new String(_key);
    }

    /**
     * getColumnKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getColumnKey() {
        return colKey;
    }

    /**
     * setDisplay
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setDisplay(String _s) {
        setText(_s);
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
        setEditable(!_b);
        colHeader.setFocusable(!_b); //22616
        //52499		if (_b) {
        //52499			setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        //52499			setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
        //52499		} else {
        //52499			setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //52499			setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        //52499		}
        //50546		setFocusable(!_b);
        setBorder(getCurrentBorder());
    }

    /**
     * isDisplayOnly
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isDisplayOnly() {
        return !isEditable();
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
        requestFocus();
    }

    /**
     * refreshDisplay
     * @author Anthony C. Liberto
     * @param _att
     */
    public void refreshDisplay(EANAttribute _att) {
    	popup.refresh(_att);
    }

    /**
     * copy
     *
     * @author Anthony C. Liberto
     */
    public void copy() {
        showError("msg11004");
    }

    /**
     * cut
     *
     * @author Anthony C. Liberto
     */
    public void cut() {
        showError("msg11004");
    }

    /**
     * paste
     *
     * @author Anthony C. Liberto
     */
    public void paste() {
        showError("msg2008");
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
        discardChanges();
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

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
    	if (isForm){
    		((MultiPopupHorz)popup).getDisplayComponent().removeFocusListener(this);
    	}
        initAccessibility(null);
        btnSave.removeActionListener(this); //52499
        btnSave.removeAll(); //52499
        btnSave.removeNotify(); //52499
        btnSave = null; //52499
        btnCncl.removeActionListener(this); //52499
        btnCncl.removeAll(); //52499
        btnCncl.removeNotify(); //52499
        btnCncl = null; //52499

        table = null;
        ef = null; //acl_20030506
        //acl_20030506		verify = null;
        removeKeyListener(popup.getKeyListener());
        colHeader.removeMouseListener(this);
        colHeader.removeListener(this); //acl_20021023
        pEdit.removeMouseListener(this);
        colHeader.dereference();
        removeAncestorListener(this); //52278
        popup.dereference();
        removeAll();
    }
    /*
     * find replace
     */
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
        //23032		found = com.ibm.opicm.client.routines.routines.find(pEdit.getText(),_strFind, _bCase);
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
        if (isPopupVisible()) {
            hidePopup();
        }
        return true;
    }

    /**
     * setTable
     * @param _table
     * @author Anthony C. Liberto
     */
    public void setTable(RowSelectableTable _table) {
        table = _table;
        refreshDisplay();

    }

    /**
     * getTable
     * @return
     * @author Anthony C. Liberto
     */
    public RowSelectableTable getTable() {
        return table;
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
            if (!isForm && isPopupVisible()) {
                popup.requestFocus();
            } else {
                super.requestFocus();
            }
        }
    }

    /**
     * @see java.awt.Component#requestFocus(boolean)
     * @author Anthony C. Liberto
     */
    public boolean requestFocus(boolean _temp) {
        if (canReceiveFocus()) {
            if (!isForm && isPopupVisible()) {
                return popup.requestFocus(_temp);
            } else {
                return super.requestFocus(_temp);
            }
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
     acl_20030829
    getX(1532, 0, 300, 911,714)
    getY(791, 0, 200, 419, 648)
    */

    private Point getPopupPosition() {
        Dimension pSize = popup.getScrollSize();
        ELogin login = eaccess().getLogin();
        Dimension sSize = login.getSize();
        Dimension mSize = null;
        Point pt = null;
        Point relPt = null;
        if (isForm) {
            mSize = colHeader.getSize();
        } else {
            mSize = getSize();
        }
        pt = getLocationOnScreen();
        relPt = login.getLocation();
        pt.setLocation(getX(pt.x, mSize.width, pSize.width, sSize.width, relPt.x), getY(pt.y, mSize.height, pSize.height, sSize.height, relPt.y));
        SwingUtilities.convertPointFromScreen(pt, this);
        return pt;
    }

    private int getX(int _x, int _myWidth, int _popupWidth, int _frameWidth, int _relX) {
        if ((_x + _popupWidth) >= (_frameWidth + _relX)) {
            return _x - (_popupWidth - _myWidth);
        } else {
            return _x;
        }
    }

    private int getY(int _y, int _myHeight, int _popupHeight, int _frameHeight, int _relY) {
        if ((_y + _popupHeight + _myHeight) > (_frameHeight + _relY - 20)) {
            return _y - (_popupHeight - _myHeight);
        } else {
            return _y;
        }
    }
    /*
     52189
     */
    /**
     * reshowPopup
     * @author Anthony C. Liberto
     */
    public void reshowPopup() {
        if (isForm) { //53123
            return; //53123
        } //53123
        popup.showHide(true);
    }

    /*
     52278
     */
    /**
     * @see javax.swing.event.AncestorListener#ancestorAdded(javax.swing.event.AncestorEvent)
     * @author Anthony C. Liberto
     */
    public void ancestorAdded(AncestorEvent _ae) {
    }
    /**
     * @see javax.swing.event.AncestorListener#ancestorMoved(javax.swing.event.AncestorEvent)
     * @author Anthony C. Liberto
     */
    public void ancestorMoved(AncestorEvent _ae) {
        if (popup != null && popup.isShowing()) {
            if (!isForm) {
                Point pt = getPopupPosition();
                popup.show(this, pt.x, pt.y);
                popup.repaint();
                Routines.yield();
            }
        }
    }
    /**
     * @see javax.swing.event.AncestorListener#ancestorRemoved(javax.swing.event.AncestorEvent)
     * @author Anthony C. Liberto
     */
    public void ancestorRemoved(AncestorEvent _ae) {
    }

    /*
     52674
     */
    /**
     * showPopup
     * @param _ke
     * @author Anthony C. Liberto
     */
    public void showPopup(KeyEvent _ke) {
        //53123		if (isDisplayOnly())
        Point pt = null;
        if (isDisplayOnly() || isForm) { //53123
            return;
        }
        pt = getPopupPosition();

        loadPopup();
        popup.setModalCursor(true);
        popup.show(this, pt.x, pt.y);
        delegateFocus();
        if (popup != null) {
            popup.processPopupKey(_ke);
        }
    }
/*
 accessibility
 */
	/**
     * initAccessibility
     * @author Anthony C. Liberto
     * @param _s
     */
    public void initAccessibility(String _s) {
		if (EAccess.isAccessible()) {
			AccessibleContext ac = getAccessibleContext();
			String strAccessible = null;
			if (ac != null) {
				if (_s == null) {
					ac.setAccessibleName(null);
					ac.setAccessibleDescription(null);
				} else {
					strAccessible = getString(_s);
					ac.setAccessibleName(strAccessible);
					ac.setAccessibleDescription(strAccessible);
				}
			}
			if (pEdit != null) {
				ac = pEdit.getAccessibleContext();
				if (ac != null) {
					if (_s == null) {
						ac.setAccessibleName(null);
						ac.setAccessibleDescription(null);
					} else {
						ac.setAccessibleName(strAccessible);
						ac.setAccessibleDescription(strAccessible);
					}
				}
			}
		}
	}
}
