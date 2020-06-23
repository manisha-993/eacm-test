//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit.form;



import com.ibm.eacm.*;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.edit.EditController;
import com.ibm.eacm.edit.SpellCheckHandler;
import com.ibm.eacm.edit.formgen.*;
import com.ibm.eacm.editor.AttrCellEditor;
import com.ibm.eacm.editor.EditorComponent;
import com.ibm.eacm.editor.PasteEditor;

import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.EANAttrTransferable;
import com.ibm.eacm.objects.Findable;
import com.ibm.eacm.objects.PasteData;
import com.ibm.eacm.objects.Utils;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

import java.util.*;

import javax.swing.*;

import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellEditor;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;

/**
 * this wraps the rowselectabletable in a table for use by the form editor
 * 
 * 
 * @author Wendy Stimpson
 */
//$Log: FormTable.java,v $
//Revision 1.2  2013/07/18 18:32:07  wendy
//fix compiler warnings
//
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class FormTable extends JPanel
implements KeyListener, FocusListener, CellEditorListener,ActionListener,MouseListener,
DocumentListener, Findable, EACMGlobals
{ 
	private static final long serialVersionUID = 1L;

	private JPanel scrollPanelNorth = new JPanel(new BorderLayout()); //<--allows for NorthJustify in viewport

	private boolean editable = true;

	private int lastFound = -1;
	private boolean found = false; 
	
	private EditController editController = null;
	private RowSelectableTable table = null; // rst from the current entityitem
	private String curKey = null; // key of the current entityitem
	private FormCellPanel currentEditor = null;
	private boolean isDataEditable = false; // is the entity editable? and profile current
	private Generator generator = null; // used to build form
	
	/**
	 * create the table used in the form
	 * @param ec
	 * @param ei
	 */
	public FormTable(EditController ec, EntityItem ei) {
		super(new BorderLayout());

		editController = ec;
		
		generator = new Generator(this,editController.getFormName());

		scrollPanelNorth.add(generator.getMainPanel(),BorderLayout.NORTH); // <-- NorthJustify mainPane
		add(scrollPanelNorth,BorderLayout.WEST); // <-- WestJustify mainPane
		
		updateModel(ei);
		
		isDataEditable = Utils.isEditable(table,getProfile());
		
		addMouseListener(this);
	}

	/**
	 * used by formcellpanels to disable and enable all actions
	 * @return
	 */
	protected EditController getEditController(){ 
		return editController;
	}

    /**
     * must override JComponent bindings or EACM copy and paste action are not invoked
     * with standard keybindings
     * @param act
     * @param keyCode
     * @param modifiers
     */
    public void registerEACMAction(EACMAction act, int keyCode, int modifiers){
        KeyStroke keystroke = KeyStroke.getKeyStroke(keyCode, modifiers, false);

		for (int i = 0; i < getFormSize(); ++i) { 
			Object obj = getFormElement(i); 
			if (obj instanceof FormCellPanel) { 
				FormCellPanel ef = (FormCellPanel) obj;
		        ef.registerEACMAction(act,keystroke);	
			}
		}
    }
    
    /**
     * call this when dereferencing components that registered copy or paste actions
     * @param act
     * @param keyCode
     * @param modifiers
     */
    public void unregisterEACMAction(EACMAction act,int keyCode, int modifiers){
    	KeyStroke keystroke = KeyStroke.getKeyStroke(keyCode, modifiers, false);

		for (int i = 0; i < getFormSize(); ++i) { 
			Object obj = getFormElement(i); 
			if (obj instanceof FormCellPanel) { 
				FormCellPanel ef = (FormCellPanel) obj;
		    	ef.unregisterEACMAction(act, keystroke);
			}
		}
    }

    //=================================================
    //CUT_ACTION
    /**
     * This cuts from the table and puts text on the clipboard
     */
    public void cut(){
    	currentEditor.cut();
    }
    //=================================================
    //COPY_ACTION
    /**
     * This copies from the table and puts EANAttrTransferable on the clipboard
     */
    public void copy(){
        Clipboard clipboard= getToolkit().getSystemClipboard();

        if (clipboard != null && currentEditor !=null) {
        	//if all text is selected, copy the attribute itself
        	Object selected = currentEditor.getSelection();
        	if(selected instanceof EANAttribute){
            	Vector<EANAttribute> eanVct = new Vector<EANAttribute>();
        		eanVct.add((EANAttribute)selected);

        		// transfer of ean objects
        		EANAttrTransferable eantransfer = new EANAttrTransferable(eanVct);
        		eanVct.clear();

        		clipboard.setContents(eantransfer, null);
        	}else if(selected !=null){
        		StringSelection contents = new StringSelection(selected.toString());
        		clipboard.setContents(contents, null);
        	}
        }
    }
    
	//=================================================
	//PASTE_ACTION
	public void paste() {
		Clipboard clipboard= getToolkit().getSystemClipboard();
		if (clipboard != null) {
			try {
				if(clipboard.isDataFlavorAvailable(EANAttrTransferable.EANDataFlavor)){
					PasteData[] eanobj = (PasteData[])clipboard.getData(EANAttrTransferable.EANDataFlavor);
					TableCellEditor editor = currentEditor.getCellEditor();
	
					PasteData ean = eanobj[0];
					//paste eanfoundation
					// use editor for validation -all AttrCellEditors are PasteEditors
					if(((PasteEditor)editor).paste(ean,true)){
						if(!editor.stopCellEditing()){
							editor.cancelCellEditing();
						}
					}
				}else{ // no eacm object data
					Transferable content = clipboard.getContents(this);
					if (content != null) {
						String data = ((String)(content.getTransferData(DataFlavor.stringFlavor))).trim();

						TableCellEditor editor = currentEditor.getCellEditor();

						// use editor for validation
						if(((PasteEditor)editor).paste(data,true)){
							if(!editor.stopCellEditing()){
								editor.cancelCellEditing();
							}
						}
					}
				}
			}catch(Exception exc){
				UIManager.getLookAndFeel().provideErrorFeedback(null);
				com.ibm.eacm.ui.UI.showException(this, exc);
			}

		} else {
			UIManager.getLookAndFeel().provideErrorFeedback(null);
		}
	}

	/**
	 * close any open editor, some other action was selected
	 */
	public void cancelCurrentEdit(){
		if(currentEditor!=null){
			currentEditor.cancelCellEditing();
		}
	}
	
	private boolean terminating = false;
	/**
	 * editor is closing without any more edit checks, prevent individual editor checks
	 */
	public void terminateEditor(){
		terminating = true;
	}

    /**
     * update the table model with this entityitem
     * @param ei - EntityItem to get RST from
     */
	public void updateModel(EntityItem ei){
		currentEditor = null;
		terminating = false;
		
		curKey = ei.getKey();
		if(table !=null){
			table.dereference();
		}
		table = ei.getEntityItemTable();
		
		masterRefresh(ei);
	}

	/**
	 * getEntityItem
	 * @return
	 */
	private EntityItem getEntityItem() {
		return (EntityItem) editController.getEgRstTable().getRow(curKey);
	}

	/**
	 * load a different entityitem
	 */
	private void masterRefresh(EntityItem ei) { 
		generator.reset();
		generator.buildForm(ei);
		// this is needed when form is collapsed to get the same color
		scrollPanelNorth.setBackground(generator.getMainPanel().getBackground());
		scrollPanelNorth.setOpaque(generator.getMainPanel().isOpaque());
		revalidate();

		// select the first cell
		for (int i = 0; i < getFormSize(); ++i) { 
			Object obj = getFormElement(i); 
			if (obj instanceof FormCellPanel) { 
				FormCellPanel ef = (FormCellPanel) obj;
				if(isLocked()){
					//find first editable cell
					if(!ef.isDisplayOnly()){
						currentEditor=ef;
						currentEditor.requestFocusInWindow();
						break;
					}	
				}else{
					currentEditor=ef;
					this.requestFocusInWindow();// needed to get keystrokes
					break;
				}
			}
		}
	
		highlightCurrentEditor();
		if(currentEditor!=null){
			// make sure the editor is visible
			scrollRectToVisible(currentEditor.getBounds());
		}
	}

	/**
	 * this rebuilds the form using the current entityitem
	 * used when the flag attribute has classified meta
	 */
	public void partialRefresh() { 
		generator.buildForm(getEntityItem());
		// this is needed when form is collapsed to get the same color
		scrollPanelNorth.setBackground(generator.getMainPanel().getBackground());
		scrollPanelNorth.setOpaque(generator.getMainPanel().isOpaque());
		revalidate(); 
	} 

	/**
	 * hasChanges true if any change was posted to the entity or editor has a new change
	 * @return
	 */
	public boolean hasChanges() {
		boolean chgd = table.hasChanges();
		if(!chgd && currentEditor !=null){
			// must have lock if this is true
			chgd = currentEditor.hasNewChanges();
		}
		return chgd;
	}

    /* (non-Javadoc)
     * must disable/enable table/editors
     * @see javax.swing.JComponent#setEnabled(boolean)
     */
    public void setEnabled(boolean enabled) {
    	super.setEnabled(enabled);

		for (int i = 0; i < getFormSize(); ++i) { 
			Object obj = getFormElement(i); 
			if (obj instanceof FormCellPanel) { 
				FormCellPanel ef = (FormCellPanel) obj;
				if (!ef.isDisplayOnly()) {
			    	ef.setEnabled(enabled);
				}
			}
		}
    }

    /**
     * should paste action be enabled or not
     * @return
     */
    public boolean canPaste(){
    	boolean editable = false;
    	FormCellPanel fcp = getCurrentEditor();
    	if(isLocked() && fcp!=null){
    		if(!fcp.isDisplayOnly()){
    			if(fcp instanceof XMLCellPanel || fcp instanceof BlobCellPanel){
    			}else{
    				editable = true;
    			}
    		}
    	}
    	return editable;
    }

    /**
     * should cut action be enabled or not
     * @return
     */
    public boolean canCut(){
    	boolean editable = false;
    	FormCellPanel fcp = getCurrentEditor();
    	if(isLocked() && fcp!=null){
    		if(!fcp.isDisplayOnly()){
    			if(fcp instanceof TextCellPanel || fcp instanceof DateCellPanel || 
    					fcp instanceof LongCellPanel || fcp instanceof TimeCellPanel){
    				editable = true;
    			}
    		}
    	}
    	return editable;
    }
    /**
	 * isLocked
	 * @return
	 */
	public boolean isLocked() { 
		for (int r = 0; r < table.getRowCount(); ++r) { 
			if (hasLock(r)) { 
				return true;
			} 
		} 
		return false; 
	} 

	/**
	 * isLocked
	 * @param rstkey
	 * @return
	 */
	public boolean isLocked(String rstkey) {
		return hasLock(rstkey);
	}


	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.Findable#isReplaceable()
	 */
	public boolean isReplaceable() {
		return editable;
	}

	/**
	 * getInformation
	 * @return
	 */
	public String getInformation() {
		String s = null;
		if (currentEditor != null) {      	
			s = currentEditor.getInformation();
		}else{
			s = Utils.getResource("nia");
		}

		return s;
	}

	/**
	 * used by attribute history action
	 * @return
	 */
	public EANAttribute getSelectedAttribute(){
		EANAttribute attr = null;
		if (currentEditor != null) {
			attr = currentEditor.getAttribute();
		}
		return attr;
	}


	/**
	 * refresh the form 
	 * called after lock/unlock or values have been changed
	 */
	public void refreshForm() {
		generator.refreshForm();
	}

	/**
	 * isAutoRefresh
	 * @param att
	 * @return
	 */
	private boolean isAutoRefresh(EANAttribute att) { 
		EANMetaAttribute meta = att.getMetaAttribute(); 
		if (meta != null) { 
			if (meta.isClassified()) { 
				partialRefresh(); 
			} 

			return meta.isRefreshEnabled(); 
		} 

		return false; 
	} 

	/**
	 * used when all attribute edits have been put into the entity or a flag attr changes that affects other attr
	 * used for rollback and classifcation chg due to flag updates (change or deactivate)
	 */
	public void refreshAll() { 
		for (int i = 0; i < getFormSize(); ++i) { 
			Object obj = getFormElement(i); 
			if (obj instanceof FormCellPanel) { 
				// needed for classification
				FormCellPanel ef = (FormCellPanel) obj;
				if(!ef.isDisplayOnly()){
					ef.refreshDisplay();
				}
			}
		}
	} 

	/**
	 * release memory
	 */
	public void dereference() {
		removeMouseListener(this);
		
		generator.dereference();
		generator = null;

		currentEditor = null;
		table = null;
		curKey = null;

		scrollPanelNorth.removeAll();
		scrollPanelNorth.setUI(null);
		scrollPanelNorth = null;

		removeAll();
		setUI(null);

		editController = null;
	}

	/**
	 * rollbackSingle - a single attribute
	 */
	public void rollbackSingle(String key) {
		int row = table.getRowIndex(key);
		table.rollback(row);
		refreshAll(); 
	}
	
	/**
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent ke) {
		int key = ke.getKeyCode();
		Object src = ke.getSource();
		FormCellPanel fcp = null;
		if(src instanceof Component){
			fcp = getCellPanel((Component)src);
		}
		if(fcp==null){
			return;
		}
		
		this.setCurrentEditor(fcp);
		
		if (key == KeyEvent.VK_ESCAPE) {
			fcp.cancelCellEditing();
			ke.consume();
		}else // this is needed because the keypress is on the editor itself and action doesnt get it
			if (key == KeyEvent.VK_DELETE && ke.getModifiers() == 0) {
			deactivateAttribute();
			ke.consume();
		}else if (key ==KeyEvent.VK_ENTER && ke.isControlDown() ){
			// open the xml editor
			if(currentEditor instanceof XMLCellPanel){
				((com.ibm.eacm.editor.XMLEditorPanel)currentEditor.getEditorComponent()).getXMLCellEditor().shouldSelectCell(ke);
				ke.consume();
			}
		}
	}

	/**
	 * @see java.awt.event.KeyListener
	 */
	public void keyTyped(KeyEvent ke) {}
	public void keyReleased(KeyEvent ke) {}
	
	/**
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 * needed to track current editor
	 */
	public void focusGained(FocusEvent fe) {
		Component c = fe.getComponent();
		FormCellPanel fcp = getCellPanel(c);
		
		setCurrentEditor(fcp);
	}

    /**
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 * 
	 */
	public void focusLost(FocusEvent fe) {
		//Component c = fe.getComponent();
		//FormCellPanel fcp = getCellPanel(c);
	}

    /* (non-Javadoc)
     * closing xml editor loses ability to use keyboard to traverse components - needed for accessibility
     * @see javax.swing.JComponent#requestFocus()
     */
    public boolean requestFocusInWindow() {
        if(currentEditor!=null){	
         	return currentEditor.requestFocusInWindow();
        }
        return super.requestFocusInWindow();
    }
	/**
	 * find the FormCellPanel for this component
	 * @param c
	 * @return
	 */
	protected static FormCellPanel getCellPanel(Component c){
		if(c == null){
			return null;
		}
		if (c instanceof FormCellPanel) { 
			return (FormCellPanel)c;
		}
		if (c instanceof EditorComponent) { 
			return ((EditorComponent)c).getFormCellPanel();
		}

		Component parent = c.getParent();
		while(parent !=null){
			if (parent instanceof FormCellPanel) { 
				return (FormCellPanel)parent;
			}
			if (parent instanceof EditorComponent) { 
				return ((EditorComponent)parent).getFormCellPanel();
			}
			parent = parent.getParent();
		}

		return null;
	}
	
	/**
	 * @see java.awt.event.MouseListener
	 */
	public void mousePressed(MouseEvent me) {}
	public void mouseReleased(MouseEvent me) {} 
	public void mouseEntered(MouseEvent me) {} 
	public void mouseExited(MouseEvent me) {} 
	
	private FormCellPanel fcpClickPoint = null;
	/**
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 * called when event happens in FormTable or the FormCellPanel renderer or editor
	 */
	public void mouseClicked(MouseEvent me) { 
		if (SwingUtilities.isLeftMouseButton(me)){
			Component invoker = me.getComponent();
			Component comp2 = null;
			if(invoker instanceof FormTable){
				Point msepoint = new Point(me.getX(), me.getY());
				comp2 = findComponentAt(msepoint);
				msepoint = null;
			}else{
				Point msepoint = getMousePosition(true);
				comp2 = findComponentAt(msepoint);
				msepoint = null;
			}
			FormCellPanel fcp  = getCellPanel(comp2);
		
			if(fcp !=null && fcp.isDisplayOnly()){
				for (int i = 0; i < getFormSize(); ++i) { 
					Object obj = getFormElement(i); 
					if (obj instanceof FormCellPanel) { 
						FormCellPanel ef = (FormCellPanel) obj;
						if (ef.editorIsActive()){ 
							fcp = ef; // component with focus must stay current editor, dont move it to a disabled cell
							break;
						}
					}
				}
			}

			setCurrentEditor(fcp);
			
			if(me.getClickCount() == 2){
				if(!isLocked()) {
					if(isDataEditable){
						fcpClickPoint = getCellPanel(comp2);
						editController.getAction(LOCK_ACTION).actionPerformed(null);
					}else{
						// open the xml editor - for view only
						if(currentEditor instanceof XMLCellPanel){
							((com.ibm.eacm.editor.XMLEditorPanel)currentEditor.getEditorComponent()).getXMLCellEditor().shouldSelectCell(me);
						}
					}
				}else {
					if (me.isShiftDown()) {
						editController.getAction(UNLOCK_ACTION).actionPerformed(null);
					}else{
						// open the xml editor
						if(currentEditor instanceof XMLCellPanel){
							((com.ibm.eacm.editor.XMLEditorPanel)currentEditor.getEditorComponent()).getXMLCellEditor().shouldSelectCell(me);
						}
					}
				}
			}//end double click
		} // end left click
	} 

	/**
	 * get the current editor, needed to check if canstopEditing should be called from an editor
	 * @return
	 */
	public FormCellPanel getCurrentEditor(){
		return currentEditor;
	}
	
	/**
	 * @param fcp
	 */
	private void setCurrentEditor(FormCellPanel fcp){
		if(terminating){
			return;
		}
		if(fcp==null){
			return;
		}
		if (currentEditor==null){
			currentEditor = fcp;
		}else if(currentEditor != fcp){
			if(currentEditor.isDisplayOnly()){
				// swap editors
				currentEditor = fcp;
			}else{
				if(isLocked()) {
					if(currentEditor.canStopCellEditing()){
						currentEditor.stopCellEditing();
						// swap editors
						currentEditor = fcp;
					}else{
						currentEditor.requestFocusInWindow();
						// editor can not be swapped
						currentEditor.stopCellEditing(); // output msg now
					}
				}else{
					currentEditor = fcp;
				}
			}
		}
		
		highlightCurrentEditor();
		if(currentEditor!=null){
			// make sure the editor is visible
			scrollRectToVisible(currentEditor.getBounds());
		}

		// update any actions that rely on specific attr
		getEditController().updateRowColActions();
	}
	/**
	 * highlight current editor, it may be displayable only
	 */
	private void highlightCurrentEditor(){
		for (int i = 0; i < getFormSize(); ++i) { 
			Object obj = getFormElement(i); 
			if (obj instanceof FormCellPanel) { 
				FormCellPanel ef = (FormCellPanel) obj;
				ef.highlightEditor(ef==currentEditor);
			}
		}
	}

	/**
	 * spellCheck - only called if editor is valid
	 * only text and longtext will have a spellcheck handler
	 * @return
	 */
	public SpellCheckHandler getSpellCheckHandler(){
		return currentEditor.getSpellCheckHandler();
	}
	
	/**
	 * can spell check be done on the selected attributes
	 * used to enable the spellcheckaction
	 * @return
	 */
	public boolean isSpellCheckable(){
		boolean checkable = false;
		if(currentEditor !=null){
			// must have lock if this is true
			checkable = currentEditor.hasPostedChanges() || currentEditor.hasNewChanges();
			if(checkable){
				checkable = currentEditor.getSpellCheckHandler()!=null;
			}
		}
		return checkable;
	}

    private Profile getProfile(){
		return getEntityItem().getProfile();
    }
    /**
     * called after lock completes and is on the eventdispatch thread
     */
    public void checkLockStatus() {
    	// find editable cells and check lock msg, cant look at only 0,1 because it may not be editable
    	for (int r=0; r<table.getRowCount(); r++){
    		boolean editable = table.isEditable(r, 1);
    		if(editable && !hasLock(r)){
    			LockGroup lockGroup = table.getLockGroup(r, 1);
    			if (lockGroup != null) { 
    				com.ibm.eacm.ui.UI.showErrorMessage(null,lockGroup.toString());
    				return;
    			}
    		}
    	}

    	// replace renderers with editors where possible
    	refreshForm();
    	
    	// move focus back to cell where mouse was clicked
    	if(fcpClickPoint !=null){
    		if(!fcpClickPoint.isDisplayOnly()){
    			fcpClickPoint.getEditorComponent().requestFocusInWindow();
    			// open the xml editor
    			if(fcpClickPoint instanceof XMLCellPanel){
    				((com.ibm.eacm.editor.XMLEditorPanel)fcpClickPoint.getEditorComponent()).getXMLCellEditor().shouldSelectCell(null);
    			}
    		}else{
    			//find first formcell that is editable
    			for (int i = 0; i < getFormSize(); ++i) { 
    				Object obj = getFormElement(i); 
    				if (obj instanceof FormCellPanel) { 
    					FormCellPanel ef = (FormCellPanel) obj;
    					if (!ef.isDisplayOnly()) {
    						ef.getEditorComponent().requestFocusInWindow();
    						break;
    					}
    				}
    			}
    		}
	
    		fcpClickPoint = null;
    	}else{
			//find first formcell that is editable
			for (int i = 0; i < getFormSize(); ++i) { 
				Object obj = getFormElement(i); 
				if (obj instanceof FormCellPanel) { 
					FormCellPanel ef = (FormCellPanel) obj;
					if (!ef.isDisplayOnly()) {
				    	ef.getEditorComponent().requestFocusInWindow();
				    	break;
					}
				}
			}
		}
    	revalidate();// is this needed here?  form was regenerated
    }

    /**
     * deactivate the selected attributes
     */
    public void deactivateAttribute(){
    	if(currentEditor !=null){
			if(isLocked()&& !currentEditor.isDisplayOnly()){
				updateAttribute(currentEditor, null);				
			}else{
			    UIManager.getLookAndFeel().provideErrorFeedback(currentEditor);
			}
    	}
    }
    /**
     * used by deactivateattraction to enable it
     * @return
     */
    public boolean hasEditableAttrSelected() { 
       	if(currentEditor !=null){
			return (//isLocked() && is islocked needed?
					!currentEditor.isDisplayOnly());
    	}
    	return false;
    }
	/**
	 * lock on the bg thread - 
	 */
	public void lock() {
		EntityItem lockOwnerEI = EACM.getEACM().getLockMgr().getLockOwner(getProfile()); 
		DBUtils.lock(table, EACM.getEACM().getLockMgr().getLockList(getProfile(),true), 
				getProfile(), lockOwnerEI, LockGroup.LOCK_NORMAL);
		LockGroup lockGroup = table.getLockGroup(0, 1);
		if(lockGroup!=null){ // will be null for a new entity
			// only different values will actually fire an event - make sure actions listening
			// reflect the correct state
			firePropertyChange(DATALOCKED_PROPERTY, true, false);
		}
	}

	/**
	 * hasLock
	 * @param r
	 * @return
	 */
	private boolean hasLock(int r) {
		EntityItem lockOwnerEI = EACM.getEACM().getLockMgr().getLockOwner(getProfile());
		return table.hasLock(r, 1, lockOwnerEI, getProfile());
	}

	/**
	 * hasLock
	 * @param rstkey
	 * @return
	 */
	private boolean hasLock(String rstkey) {
		EntityItem lockOwnerEI = EACM.getEACM().getLockMgr().getLockOwner(getProfile());
		return table.hasLock(table.getRowIndex(rstkey), 1, lockOwnerEI, getProfile());
	}

	/**
	 * unlockRows
	 */
	public void unlockRows() {
		EANFoundation ean = getEntityItem();
		EntityItem lockOwnerEI = EACM.getEACM().getLockMgr().getLockOwner(ean.getProfile());
		if (lockOwnerEI!=null){
			DBUtils.unlock(table, EACM.getEACM().getLockMgr().getLockList(ean.getProfile(),true), ean.getProfile(), lockOwnerEI, LockGroup.LOCK_NORMAL);

			LockGroup lockGroup = table.getLockGroup(0, 1); 
			if(lockGroup!=null){ // will be null for a new entity
				// had the lock, remove the attribute in case entity is unlocked thru lockactiontab
	//			xxx.removeLockGrpItem(lockGroup.getKey(), ean); 
			}
			// only different values will actually fire an event - make sure actions listening
			// reflect the correct state
			firePropertyChange(DATALOCKED_PROPERTY, true, false);
		}
	}

	/**
	 * get the key for the selected 'cell'
	 * @return
	 */
	public String getSelectionKey() {
		String key = null;
		if(currentEditor!=null){
			key = currentEditor.getRSTKey();
		}
		return key;
	}
    /**
     * does the current editor have a change
     * @return
     */
    public boolean hasChangedAttrSelected() { 
    	return (currentEditor!=null && 
    			(currentEditor.hasPostedChanges() || currentEditor.hasNewChanges()));
    }
    /**
     * select the specified attribute
     * @param selKey
     */
    public void setSelection(String selKey) {
    	if(selKey!=null){
    		Object obj = generator.getFormMap().get(selKey);
    		if(obj instanceof FormCellPanel){
    			FormCellPanel fcp = (FormCellPanel)obj;
    			if(fcp.isDisplayOnly()){
    				currentEditor = fcp;
    				this.highlightCurrentEditor();
    				scrollRectToVisible(fcp.getBounds());
    			}else{
    				fcp.requestFocusInWindow();  
    			}
    		}else{
    			currentEditor = null;
    			this.highlightCurrentEditor();
    		}
    	}else{
    		currentEditor = null;
    		this.highlightCurrentEditor();
    	}
    }

	private int getFormSize() { 
		return generator.getFormOrder().size(); 
	}

	private Object getFormElement(int i) { 
		if (i < 0 || i >= generator.getFormOrder().size()) { 
			return null;
		} 
		String sKey = (String) generator.getFormOrder().get(i);
		return generator.getFormMap().get(sKey); 
	} 

	/**
	 * getCurrentEANMetaAttribute
	 * @return
	 */
	public EANMetaAttribute getCurrentEANMetaAttribute() {
		if (currentEditor != null) { 
			EANAttribute att = currentEditor.getAttribute(); 
			return att.getMetaAttribute(); 
		}
		return null;
	}
	
	/**
	 * get RowSelectableTable used in this table
	 *
	 */
	public RowSelectableTable getRSTTable() {
		return table;
	}

	/**
	 * called when editor tab is selected
	 */
	public void select() {
		refreshForm(); // must reflect any chgs made in another tab
	}


	/**
	 * resetFound
	 */
	public void resetFound() {
		Collection<?> c = generator.getFormMap().values();   
		Iterator<?> itr = c.iterator();
		while(itr.hasNext()){
			Object o = itr.next();
			if (o instanceof FormCellPanel) {  
				((FormCellPanel) o).resetFound();  
			} else if (o instanceof FormLabel) {  
				((FormLabel) o).resetFound();  
			}  
		}  
		  
		found = false;  
	}

	/**
	 * hasFound
	 * @return
	 */
	public boolean hasFound() {
		return found;
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.Findable#getLockInformation()
	 */
	public String getLockInformation() { 
		EntityItem lockOwnerEI =EACM.getEACM().getLockMgr().getLockOwner(getProfile()); // use hashtable if possible
		if (!getEntityItem().hasLock(lockOwnerEI, getProfile())) {
			LockGroup lock = getEntityItem().getLockGroup();
			if (lock != null) {
				return lock.toString();
			}
		}
		return null;
	}
	public boolean isMultiColumn() {
		return false; 
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.Findable#findValue(java.lang.String, boolean, boolean, boolean, boolean)
	 */
	public boolean findValue(String findValue, boolean isMulti, boolean useCase, boolean isDown, 
    		boolean isWrap) {
		Object o = getFormElement(lastFound); 
		if ( o instanceof FormCellPanel) { 
			FormCellPanel ei = (FormCellPanel) o;
			if (ei.isFound()) {
				if (getNextValue(findValue, useCase, isDown,isWrap)) {
					found = true;
				}
			} else {
				if (ei.find(findValue, useCase)) {
					found = true;
				} else {
					if (getNextValue(findValue, useCase, isDown,isWrap)) {
						found = true;
					}
				}
			}
		} else {
			if (getNextValue(findValue, useCase, isDown,isWrap)) {
				found = true;
			}
		}
		return found;
	}
	/*
	 * find/replace logic
	 */
	private boolean getNextValue(String findValue, boolean caseSensitive, boolean isDown, boolean isWrap) {
		int max = getFormSize();
		int increment = 1;
		if(isDown){
			increment = -1;
		}
		int indx = lastFound+increment;

		if (indx < 0) { 
			indx = max - 1; 
		} else if (indx >= max) { 
			indx = 0;
		} 
		for (int e = indx; e < max && e >= 0; e += increment) {
			if (searchComponent(e, findValue, caseSensitive)) {
				lastFound = e;
				return true;
			}
		}

		if(!isWrap){
		    UIManager.getLookAndFeel().provideErrorFeedback(this);
			return false;
		}

		repaint(); 

		if (increment < 0) { 
			for (int b = max - 1; b >= indx; b--) { 
				if (searchComponent(b, findValue, caseSensitive)) { 
					lastFound = b; 
					return true; 
				} 
			} 
		} else {
			for (int b = 0; b < indx; b ++) {
				if (searchComponent(b, findValue, caseSensitive)) {
					lastFound = b;
					return true;
				}
			}
		}

		return false;
	}

	private boolean searchComponent(int i, String strFind, boolean bCase) {
		Object o = getFormElement(i); 

		if (o instanceof FormCellPanel) {
			return ((FormCellPanel) o).find(strFind, bCase);
		} else if (o instanceof FormLabel) {
			return ((FormLabel) o).find(strFind, bCase);
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.Findable#replaceValue(java.lang.String, java.lang.String, boolean)
	 */
	public int replaceValue(String sOld, String sNew, boolean bCase) {
		Object o = null;
		if (lastFound < 0 || lastFound >= getFormSize()) { 
			return NOT_FOUND;
		} 

		if (!hasLock(0)) { 
			lock(); 
			checkLockStatus();
			if (!hasLock(0)){
				return CELL_LOCKED;
			}
		} 

		o = getFormElement(lastFound); 
		if (o instanceof FormCellPanel) { 
			FormCellPanel ei = (FormCellPanel) o;
			if (ei.isDisplayOnly()){
				return CELL_UNEDITABLE; 
			} 
			if (!(ei instanceof TextCellPanel)){
				return ATTR_NOTREPLACEABLE; 
			} 
			ei.replace(sOld, sNew);
		} 
		
		return REPLACED;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.Findable#replaceNextValue(java.lang.String, java.lang.String, boolean, boolean, boolean, boolean)
	 */
	public int replaceNextValue(String findValue, String replace, boolean isMulti, boolean useCase, boolean isDown, boolean isWrap)
	{
//		 same crappy behavior as before
		int results = replaceValue(findValue, replace, useCase);
		findValue(findValue, false,useCase, false, false);
		return results;
	}


	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.Findable#replaceAllValue(java.lang.String, java.lang.String, boolean, boolean, boolean)
	 */
	public void replaceAllValue(String findValue, String replace, boolean isMulti, boolean useCase, boolean isDown){
		if (!hasLock(0)) { 
			lock(); 
			checkLockStatus();
			if (!hasLock(0)){
				return;
			}
		} 

		for (int i = 0; i < getFormSize(); ++i) { 
			Object o = getFormElement(i); 
			if (o instanceof FormCellPanel) { 
				FormCellPanel ei = (FormCellPanel) o; 
				if (!ei.isDisplayOnly()){
					if (ei instanceof TextCellPanel){
						ei.replace(findValue, replace); 
						if(!found && ei.isFound()){
							found = true;
						}
					} 
				} 
			}
		}
	}

	
	/**
	 * this is needed so the error dialog can be displayed when user presses the date calendar button
	 * and has invalid edit in another cell or any toolbar button
	 * @return
	 */
	public boolean stopCellEditing(){
		if(currentEditor==null){
			return true;
		}
		currentEditor.requestFocusInWindow();
		return currentEditor.stopCellEditing();
	}
	/**
	 * this is called on the event thread
	 * called from formeditor when other actions want to execute
	 * @return
	 */
	public boolean canStopEditing(){
		if(currentEditor!=null){
			if (!currentEditor.isDisplayOnly() && !currentEditor.canStopCellEditing()) {
				return false;
			}
			//stop the current edit
			if (!currentEditor.isDisplayOnly()) {
				currentEditor.stopCellEditing();
			}
		}
		
		return true;
	}
	
	/**
	 * called when edit was stopped in an editor
	 * @param e
	 */
	public void editingStopped(ChangeEvent e) {
		// Take in the new value
		AttrCellEditor attrcelled = (AttrCellEditor)e.getSource();
		FormCellPanel fcp = attrcelled.getFormCellPanel();
		if(fcp.hasNewChanges()){
			updateAttribute(fcp, fcp.getCellEditorValue());		
		}
	}
	
	/**
	 * put the updated value into the rowselectabletable and notify all listeners of the change
	 * @param fcp - this is most likely always the current editor
	 * @param value
	 */
	private void updateAttribute(FormCellPanel fcp, Object value){
		try {
			table.put(table.getRowIndex(fcp.getRSTKey()), 1,value); 
			// only different values will actually fire an event - save and cancel actions listen for this
			this.firePropertyChange(DATACHANGE_PROPERTY, true, false);

			//flag attrs can affect other flag attrs
			if (fcp.getAttribute() instanceof EANFlagAttribute){
				// must allow for classification
				if (isAutoRefresh(fcp.getAttribute())) { 
					refreshAll(); 
				}else{
					fcp.refreshDisplay();		
				}
			}else{
				fcp.refreshDisplay();	
			}

			repaint(); // needed to see different meta requirements.. green or pink background
		} catch (final EANBusinessRuleException bre) {	
			//business rule failure, must restore panel
			fcp.refreshDisplay();
			fcp.requestFocusInWindow();// put the focus back into this cell
			// put this up later, seems to be a threading issue when it blocks
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					com.ibm.eacm.ui.UI.showBusRuleException(null,bre,"err-title");
				}
			});
		}
	}

	/**
	 * @param e
	 */
	public void editingCanceled(ChangeEvent e) {
		AttrCellEditor attrcelled = (AttrCellEditor)e.getSource();
		FormCellPanel fcp = attrcelled.getFormCellPanel();
		fcp.refreshDisplay();
	}

	/**
	 * used to recognize changes in jcombobox in flagcelleditor
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		FormCellPanel fcp = getCellPanel((Component)e.getSource());
		if(fcp!=null){
			fcp.stopCellEditing();
		}
	}

	/**
	 * used when text document changes to enable actions
	 * @param e
	 */
	public void changedUpdate(DocumentEvent e) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// only different values will actually fire an event - save and cancel actions listen for this
				firePropertyChange(DATACHANGE_PROPERTY, true, false);	
			}
		});
	}

	public void insertUpdate(DocumentEvent e) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// only different values will actually fire an event - save and cancel actions listen for this
				firePropertyChange(DATACHANGE_PROPERTY, true, false);	
			}
		});
	}

	public void removeUpdate(DocumentEvent e) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// only different values will actually fire an event - save and cancel actions listen for this
				firePropertyChange(DATACHANGE_PROPERTY, true, false);	
			}
		});
	}
}
