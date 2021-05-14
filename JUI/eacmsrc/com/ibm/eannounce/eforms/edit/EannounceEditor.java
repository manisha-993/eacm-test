// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2009 All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eannounce.eforms.edit;
import com.elogin.*;
//import com.ibm.eannounce.eserver.ChatAction;
//import com.ibm.eannounce.eforms.editor.MetaValidator;
import com.ibm.eannounce.eforms.toolbar.*;
import com.ibm.eannounce.eobjects.*;
import COM.ibm.eannounce.objects.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import javax.swing.text.JTextComponent;

/**
 *
 * $Log: EannounceEditor.java,v $
 * Revision 1.2  2009/05/22 14:18:52  wendy
 * Performance cleanup
 *
 * Revision 1.2  2008/02/20 15:44:01  wendy
 * Prevent double scrollpanes to avoid paint resize loop
 *
 * Revision 1.1  2007/04/18 19:44:06  wendy
 * Reorganized JUI module
 *
 * Revision 1.3  2006/04/20 19:08:27  tony
 * adjsuted cursor logic so
 * that it will not be set to default
 *
 * Revision 1.2  2005/09/12 19:03:12  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:54  tony
 * This is the initial load of OPICM
 *
 * Revision 1.13  2005/09/08 17:59:02  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.12  2005/03/28 17:56:37  tony
 * MN_button_disappear
 * adjust to preven java bug 4664818
 *
 * Revision 1.11  2005/03/09 19:54:43  tony
 * 6542 toolbar update
 *
 * Revision 1.10  2005/03/03 21:46:40  tony
 * cr_FlagUpdate
 * Added Flag Modification Capability.
 *
 * Revision 1.9  2005/02/23 20:22:01  tony
 * 6542
 *
 * Revision 1.8  2005/02/04 15:22:09  tony
 * JTest Format Third Pass
 *
 * Revision 1.7  2005/02/01 20:48:33  tony
 * JTest Second Pass
 *
 * Revision 1.6  2005/01/27 19:15:15  tony
 * JTest Format
 *
 * Revision 1.5  2004/08/26 16:26:35  tony
 * accessibility enhancement.  Adjusted menu and toolbar
 * appearance for the accessibility impaired.  Improved
 * accessibility performance by enabling preference toggling.
 *
 * Revision 1.4  2004/08/19 15:12:05  tony
 * xl8r
 *
 * Revision 1.3  2004/02/23 21:30:52  tony
 * e-announce13
 *
 * Revision 1.2  2004/02/20 22:17:59  tony
 * chatAction
 *
 * Revision 1.1.1.1  2004/02/10 16:59:38  tony
 * This is the initial load of OPICM
 *
 * Revision 1.11  2003/10/20 19:02:40  tony
 * fixed null pointer
 *
 * Revision 1.10  2003/10/20 16:37:14  tony
 * memory update.  Updated dereference logic to close memory gaps
 *
 * Revision 1.9  2003/10/10 23:17:34  tony
 * 52527
 *
 * Revision 1.8  2003/09/19 18:11:07  tony
 * 52323
 *
 * Revision 1.7  2003/06/16 23:29:15  tony
 * 51257
 *
 * Revision 1.6  2003/06/05 17:07:37  tony
 * added refreshAppearance logic to the toolbar for the container.
 * This will flow thru to the scrollable popup.
 * Adjusted the sizing logic on the scrollable popup.
 *
 * Revision 1.5  2003/05/13 16:07:43  tony
 * 50621
 *
 * Revision 1.4  2003/04/21 20:03:12  tony
 * enhanced scroll logic to improve flag editor.
 *
 * Revision 1.3  2003/04/02 17:55:19  tony
 * added dump logic.
 *
 * Revision 1.2  2003/03/26 17:06:30  tony
 * per Joan adjusted logic on PDG so
 * that table is properly displayed.  Adjusted logic
 * by trapping possible null pointers.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:46  tony
 * This is the initial load of OPICM
 *
 * Revision 1.59  2002/12/06 17:22:06  tony
 * acl_20021206 -- added synchronize logic to the tables.
 *
 * Revision 1.58  2002/11/21 20:03:45  tony
 * added logic for trapping error on dynaTable search
 *
 * Revision 1.57  2002/11/19 23:57:40  tony
 * removed xprt option from popup menu
 *
 * Revision 1.56  2002/11/11 22:55:38  tony
 * adjusted classification on the toggle
 *
 * Revision 1.55  2002/11/07 16:58:26  tony
 * added/adjusted copyright statement
 *
 */

public abstract class EannounceEditor extends EPanel implements EAccessConstants {

	// methods that act on the Editing component
	protected abstract Editable getEditable();
	
	protected boolean commitDefault(){
    	return getEditable().commitDefault();
    }
	protected void historyInfo(){
    	getEditable().historyInfo();
    }
	protected void cancelDefault(){
    	getEditable().cancelDefault();
    }
	protected void updateModel(RowSelectableTable _t){
    	getEditable().updateModel(_t);
    }
	protected boolean hasFiltered() {
        return getEditable().isFiltered();
    }
	protected void prepareToEdit(){
    	getEditable().prepareToEdit();
    }
	protected Object getSelectedObject() { //21765
        return getEditable().getSelectedObject(); //21765
    }
	protected boolean saveCurrentEdit() { //22920
        return getEditable().saveCurrentEdit(); //22920
    }
	protected void highlight(String[] _s){
    	getEditable().highlight(_s);
    }
	protected void selectKeys(String[] _s){
    	getEditable().selectKeys(_s);
    }

	protected void select(){
    	getEditable().select();
    }

	protected void deselect(){
    	getEditable().deselect();
    }

	protected boolean hasHiddenAttributes(){
    	return getEditable().hasHiddenAttributes();
    }

	protected void showHide(boolean _b){
    	getEditable().showHide(_b);
    }

	protected boolean isPasteable(){
    	return getEditable().isPasteable();
    }

	protected boolean isEditing(){
    	return getEditable().isEditing();
    }

	protected void cancelEdit(){
    	getEditable().cancelEdit();
    }
	protected boolean commitEdit(){
    	return getEditable().canStopEditing();
    }

	protected EANMetaAttribute getSelectedEANMetaAttribute(){
    	return getEditable().getSelectedEANMetaAttribute();
    }
    public void deactivateAttribute(){
    	getEditable().deactivateAttribute();
    }
    protected void stopEditing(){
    	getEditable().stopEditing();
    }
    protected void selectAll(){
    	getEditable().selectAll();
    }
    protected void invertSelection(){
    	getEditable().invertSelection();
    }
    protected String getSelectionKey(){
    	return getEditable().getSelectionKey();
    }
    protected void spellCheck(){
    	getEditable().spellCheck();
    }
    /**
     * fillCopy
     *
     * @param _row
     */
    protected void fillCopy(boolean _row){
    	getEditable().fillCopy(_row);
    }

    /**
     * fillPaste
     *
     */
    protected void fillPaste(){
    	getEditable().fillPaste();
    }

    /**
     * fillClear
     */
    protected void fillClear(){
    	getEditable().fillClear();
    }
    /**
     * sort
     *
     * @param _ascending
     * @author Anthony C. Liberto
     */
    protected void sort(boolean _ascending){
    	getEditable().sort(_ascending);
    }
    /**
     * showEffectivity
     */
    protected void showEffectivity(){
    	getEditable().showEffectivity();
    }
    /**
     * filter
     */
    protected void filter(){
    	getEditable().showFilter();
    }
    /**
     * @see java.awt.Component#hasFocus()
     */
    public boolean hasFocus() {
    	return getEditable().hasFocus();
       /* if (table != null && table.hasFocus()) {
            return true;
        }
        return false;*/
    }
    /////////////////
    protected EntityItem[] getEntityItems() {return null;}
    protected void removeNewRows() {}

    protected void moveToError(EANBusinessRuleException _ebre) {}
    protected void showSort() {}

    //public boolean hasRows() {return false;}
    //public boolean hasColumns() {return false;}

    protected boolean isNew() { return false;}
    protected boolean hasChanges() { return false;}
    protected void processMaintAction() {}
    protected boolean okToClose(boolean _reset) { return false;}
    protected boolean commit() {return false;}
  
    protected void addRow(boolean _sort) {}
    //public abstract String getAttributeCode();

    protected void rollback() {}

    protected void rollbackRow() {}

    protected void rollbackSingle() {}
    protected void setParentItem(EntityItem _item) {}

    protected String[] getSelectedKeys() {return null;}
    protected void increment(int _i) {}
    protected EANMetaAttribute getEANMetaAttribute(int _r, int _c) {return null;}
    protected void updateRecordLabel(int _i) {}
    
 
	////////////////////////////
   // protected EntityList eList = null;
    protected EannounceToolbar tBar = null;
    protected EScrollPane scroll = null;
    protected EPopupMenu popup = null;
    protected EditController ec = null;

    private ETitledBorder titleBorder = new ETitledBorder();

    /**
     * EannounceEditor
     * @param _eList
     * @param _ec
     * @param _tBarLayout
     * @param _obj
     * @author Anthony C. Liberto
     */
    protected EannounceEditor(EditController _ec, ComboItem _tBarLayout, Object _obj) {
        super(new BorderLayout());
        //setEntityList(_eList);
        ec = _ec;
        if (!EAccess.isArmed(ACCESSIBLE_ARM_FILE)) {
            tBar = ToolbarController.generateToolbar(_tBarLayout, ec, _obj);
        }
        if (_ec != null && !_ec.canEditDefault()) { //51257
            _ec.setVisible("dsave", false); //51257
            _ec.setVisible("dcncl", false); //51257
        } //51257
    }

    /**
     * EannounceEditor
     * @param _ec
     * @author Anthony C. Liberto
     */
    protected EannounceEditor(EditController _ec) { //dyna
        super(new BorderLayout()); //dyna
        ec = _ec;
    } //dyna

    /**
     * setTitle
     * @param _s
     * @author Anthony C. Liberto
     */
    protected void setTitle(String _s) {
        titleBorder.setTitle(_s);
        if (ec != null) {
            ec.setName(_s);
        }
        scroll.repaint();
    }


    /*
     * stuff that flows up
     */
    /**
     * createScrollPane
     * @param _table
     * @author Anthony C. Liberto
     */
    protected void createScrollPane(JComponent _table) {
        createScrollPane(_table, new Dimension(250, 250));
    }

    /**
     * createScrollPane
     * @param _table
     * @param _d
     * @author Anthony C. Liberto
     */
    protected void createScrollPane(JComponent _table, Dimension _d) {
    	if (_table instanceof EScrollPane){ // prevent double scrollpane, causes error in preferredsize and loops
    		scroll = (EScrollPane)_table;
    	}else{
    		scroll = new EScrollPane(_table);
    	}
        scroll.setBorder(titleBorder);
        scroll.setSize(_d);
        scroll.setPreferredSize(_d);
        scroll.setMinimumSize(UIManager.getDimension("eannounce.minimum"));
        add("Center", scroll);
    }

    /**
     * createMenus
     * @param _s
     * @param _isTable
     * @author Anthony C. Liberto
     */
    protected void createMenus(String _s, boolean _isTable) {
        popup = new EPopupMenu(_s);
        createPopupMenu(_isTable);
        if (tBar != null) {
            add(tBar.getAlignment(), tBar);
        }
    }

    /**
     * setEnabled
     *
     * @author Anthony C. Liberto
     * @param _enabled
     * @param _key
     */
    protected void setEnabled(String _key, boolean _enabled) {
        if (tBar != null) {
            tBar.setEnabled(_key, _enabled);
        }
        if (popup != null) {
            popup.setEnabled(_key, _enabled);
        }
    }

    /**
     * setVisible
     *
     * @author Anthony C. Liberto
     * @param _key
     * @param _visible
     */
    protected void setVisible(String _key, boolean _visible) {
        if (tBar != null) {
            tBar.setVisible(_key, _visible);
        }
        if (popup != null) {
            popup.setVisible(_key, _visible);
        }
    }

    private void createPopupMenu(boolean _isTable) {
        if (_isTable) {
            addPopupMenu("srt", ec); //21895
            popup.addSeparator();
        }
        addPopupMenu("f/r", ec);
        addPopupMenu("fltr", ec);
        popup.addSeparator();
        if (_isTable) {
            addPopupMenu("selA", ec);
            addPopupMenu("iSel", ec);
            popup.addSeparator();
        }
        addPopupMenu("copy", ec);
        addPopupMenu("pste", ec.canPaste(), ec);
    }

    /**
     * closeLocalMenus
     * @author Anthony C. Liberto
     */
    protected void closeLocalMenus() { //acl_Mem_20020130
        if (popup != null) {
            popup.removeMenu("srt", ec); //21895
            popup.removeMenu("f/r", ec); //acl_Mem_20020130
            popup.removeMenu("fltr", ec); //acl_Mem_20020130
            popup.removeMenu("selA", ec); //acl_Mem_20020130
            popup.removeMenu("iSel", ec); //acl_Mem_20020130
            popup.removeMenu("copy", ec); //acl_Mem_20020130
            popup.removeMenu("pste", ec); //acl_Mem_20020130
            popup.removeAll(); //acl_Mem_20020130
            popup.removeNotify(); //acl_Mem_20020131
            popup = null; // fixme should it be derefed?
        }
    } //acl_Mem_20020130

    private void addPopupMenu(String _s, ActionListener _al) {
        popup.addPopupMenu(_s, _al);
    }

    private void addPopupMenu(String _s, boolean _enabled, ActionListener _al) {
        popup.addPopupMenu(_s, _enabled, _al);
    }

    /**
     * formRefresh - used for Form editor and classification chgs
     */
    protected void formRefresh() {}
 
    /**
     * duplicate - overridden by derived classes
     */
    protected void duplicate() {}

    /**
     * refresh - overridden by derived classes, no point to call editcontroller, it just calls the editors
     *
     */
    protected void refresh() {}
       /* Component c = getParent();
        if (c instanceof EditController) {
            ((EditController) c).refresh();
        }
        */
    //}

    /*
     * lock and unlock
     */

    /**
     * lock - overridden by derived classes
     */
    protected void lock() {}

    /**
     * unlock - overridden by derived classes
     *
     */
    protected void unlock() {}

    /**
     * getNumberCopies
     * @return
     */
    protected int getNumberCopies() {
        return eaccess().getNumber("msg3013");
    }

    /*
     * stuff that flows down
     */

    /**
     * removeExtra
     */
    protected void removeExtra(){}

    /**
     * cut
     *
     */
    protected void cut() {}

    /**
     * copy
     *
     */
    protected void copy() {}

    /**
     * importTable
     *
     */
    protected void importTable() {
    } //xl8r

    /**
     * paste
     *
     */
    protected void paste() {}

    /**
     * freeze - only used in horizontal editor
     */
    protected void freeze() {}

    /**
     * thaw - only used in horizontal editor
     */
    protected void thaw() {}

    /**
     * showInformation
     */
    protected void showInformation() {}


    /**
     * moveColumn
     *
     * @param _left
     */
    protected void moveColumn(boolean _left) {}


    /**
     * getHelpText
     *
     * @return
     */
    protected String getHelpText() { return getString("nia"); }

    /**
     * find
     *
     */
    protected void find() {}


    /**
     * print
     */
    protected void print(){}


    /**
     * getRecordKey
     *
     * @return
     */
    protected String getRecordKey() {return "";}

    /**
     * setSelection
     *
     * @param _colKey
     * @param _recKey
     */
    protected void setSelection(String _recKey, String _colKey) {}

    /**
     * getPanelType
     *
     * @return
     */
    public String getPanelType() { return "";}

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        closeLocalMenus();
        titleBorder = null;

        if (tBar != null) {
            tBar.dereference();
            tBar = null;
        }
        if (scroll != null) {
            scroll.dereference();
            scroll = null;
        }

        if (popup != null) {
            popup.dereference();
            popup = null;
        }

        ec = null;
        titleBorder = null;
//acl_20060420  super.dereference();
        // these are called when the tab is removed ->JTabbedPane.removeTabAt(int)
       // removeAll();		//acl_20060420
        //removeNotify();		//acl_20060420
    }

    /**
     * hasMasterChanges
     *
     * @return
     */
    protected boolean hasMasterChanges() {
        return ec.hasMasterChanges();
    }

    /**
     * getEntityItemCount
     * @return
     */
    protected int getEntityItemCount() { //j20020402
        return ec.getEntityItemCount();
    }

    /**
     * getSearchableObject
     *
     * @return
     */
    protected Object getSearchableObject() { //22377
        return getEditable(); //22377
    } //22377

    /**
     * canContinue
     *
     * @return
     */
    protected boolean canContinue() { //22624
        return true; //22624
    } //22624

    /**
     * cancel
     */
    protected void cancel() {} 

    /**
     * synchronize
     */
    protected void synchronize() {} 


    /*
     acl_20030505
    */
    /**
     * refreshAppearance
     *
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        if (tBar != null) {
            tBar.refreshAppearance();
        }
    }
    /*
     52323
     */
    /**
     * freezeRefresh - only used in horizontal editor
     *
     */
    protected void freezeRefresh() { }

    /*
     52527
     */
    /**
     * adjustSubAction
     *
     * @param _eai
     * @param _hasData
     * @param _key
     * @param _type
     */
    protected void adjustSubAction(String _key, EANActionItem[] _eai, boolean _hasData, int _type) {
        if (tBar != null) {
            tBar.adjustSubAction(_key, _eai, _hasData, _type);
        }
    }

    /**
     * nlsRefresh
     *
     */
    protected void nlsRefresh() {
        Component c = getParent();
        if (c instanceof EditController) {
            ((EditController) c).refresh();
        }
    }

    /**
     * processMaintAction
     *
     * @param _meta
     */
    protected void processMaintAction(EANMetaAttribute _meta) {
		if (ec != null) {
			ec.processMaintAction(_meta);
		}
	}

    /**
     * refresh toolbar
     * MN_button_disappear
     */
    protected void refreshToolbar() {
		if (tBar != null) {
			tBar.refreshToolbar();
		}
	}
    
    // old stuff or unused

    /**
     * dump
     *
     * @param _b
     * @return
     * @author Anthony C. Liberto
     * /
    public String dump(boolean _b) {
        return "default dump";
    }

    /*
     50621
    */
    /**
     * getValidator
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public MetaValidator getValidator() {
        return null;
    }*/
    /**
     * getChatAction
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public ChatAction getChatAction() {
        EntityGroup eg = null;
        if (eList != null) {
            ChatAction chat = new ChatAction();
            EANActionItem item = eList.getParentActionItem();
            if (item != null) {
                chat.setAction(item);
            }
            eg = eList.getParentEntityGroup();
            if (eg != null) {
                chat.setSelectedItems(eg.getEntityItemsAsArray());
            }
            return chat;
        }
        return null;
    }*/
    /**
     * refreshUpdate
     * @author Anthony C. Liberto
     * /
    public void refreshUpdate() {
        ec.refreshUpdate();
    }

    /**
     * generateOrder
     * @author Anthony C. Liberto
     * /
    public void generateOrder() {
    }*/

    /**
     * canCreate
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean canCreate() {
        return ec.isCreatable();
    }

    /**
     * isEditable
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isEditable() {
        return ec.isEditable();
    }*/
    /**
     * launchMatrix
     *
     * @author Anthony C. Liberto
     */
    //public abstract void launchMatrix();
    /**
     * exportString
     * @return
     * @author Anthony C. Liberto
     */
    //public abstract String exportString();
    /**
     * moveToError
     *
     * @author Anthony C. Liberto
     * @param _mbre
     */
    //public abstract void moveToError(MiddlewareBusinessRuleException _mbre);
    //	public abstract void moveToError(OPICMBusinessRuleException _opbre);			//20020116

    /**
     * lock
     * @param _lock
     * @param _i
     * @author Anthony C. Liberto
     * /
    public void lock(boolean _lock, int _i) {
        /*
         nfw
        		if (_i < 0 || _i >= group.getPDHItemCount()) return;
        		lock(_lock,group.getPDHItem(_i));
        		return;
        * /
    }*/
    /**
     * lockAll
     * @param _lock
     * @author Anthony C. Liberto
     * /
    protected void lockAll(boolean _lock) {

        /*
         nfw
        		int ii = group.getPDHItemCount();
        		if (ii < 1) return;
        		PDHItem[] out = new PDHItem[ii];
        		for (int i=0;i<ii;++i)
        			out[i] = group.getPDHItem(i);
        		if (_lock)
        			lock(out);
        		else
        			unlock(out);
        		return;
        * /
    }*/

    /**
     * export
     * @author Anthony C. Liberto
     * /
    public void export() {
        String export = null;
        FileDialog fd = null;
        String dir = null;
        String file = null;
        if (!isBusy()) {
            setBusy(true);
            export = exportString();
            if (export == null) {
                showError("msg11001");
                setBusy(false);
                return;
            }
            fd = new FileDialog(eaccess().getLogin(), getString("saveTo"), FileDialog.SAVE);
            fd.setModal(true);
            fd.setResizable(false);
            fd.show();
            dir = fd.getDirectory();
            file = fd.getFile();
            if (dir != null && file != null) {
                if (!Routines.endsWithIgnoreCase(file, ".csv")) {
                    file = file + ".csv";
                }
                eaccess().gio.writeString(dir + file, export);
            }
            setBusy(false);
        }
    }*/

    /**
     * showLayout
     * @author Anthony C. Liberto
     * /
    public void showLayout() {
    }*/
    /**
     * spellCheck
     *
     * @author Anthony C. Liberto
     * @param _jtc
     * /
    public void spellCheck(JTextComponent _jtc) {
        getParent();
    }*/

    /**
     * setViewportSize
     * @param _d
     * @author Anthony C. Liberto
     * /
    public void setViewportSize(Dimension _d) { //21722
        JViewport view = null;
        if (scroll == null) {
            return;
        } //21722
        view = scroll.getViewport(); //21722
        view.setSize(_d); //21722
        view.setPreferredSize(_d); //21722
    } //21722

    /**
     * setEntityList
     * @param _eList
     * @author Anthony C. Liberto
     * /
    private void setEntityList(EntityList _eList) {
        eList = _eList;
    }

    /**
     * getGroup
     * @return
     * @author Anthony C. Liberto
     * /
    public EntityList getGroup() {
        return eList;
    }

    /**
     * getRowSelectableTable
     * @return
     * @author Anthony C. Liberto
     * /
    public RowSelectableTable getRowSelectableTable() {
        return ec.getTable();
    }*/

    /**
     * getEditController
     * @return
     * @author Anthony C. Liberto
     * /
    public EditController getEditController() {
        return ec;
    }*/

    /**
     * getEToolbar
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public EannounceToolbar getEToolbar() {
        return tBar;
    }

    /**
     * getTitle
     * @return
     * @author Anthony C. Liberto
     * /
    public String getTitle() {
        return titleBorder.getTitle();
    }*/

    /**
     * setEditController
     * @param _ec
     * @author Anthony C. Liberto
     * /
    private void setEditController(EditController _ec) {
        ec = _ec;
    }*/

    // public void refreshTable(boolean _b){
    // 	getEditable().refreshTable(_b);
    // }
   
}
