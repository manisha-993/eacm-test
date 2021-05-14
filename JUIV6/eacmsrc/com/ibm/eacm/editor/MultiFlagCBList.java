//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.editor;


import javax.swing.*;
import javax.swing.border.*;

import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Utils;

import COM.ibm.eannounce.objects.MetaFlag;

import java.awt.*;
import java.awt.event.*;

/**
 * this class is a list of checkboxes used for editing multiflags
 * @author Wendy Stimpson
 */
//$Log: MultiFlagCBList.java,v $
//Revision 1.2  2013/08/07 21:37:59  wendy
//consume keyevent when (de)selecting all
//
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class MultiFlagCBList extends JList implements EACMGlobals
{
	private static final Border NOFOCUSBORDER =	new EmptyBorder(1, 1, 1, 1);
	private static final long serialVersionUID = 1L;

	private MultiPopup myPopup = null;
	private MultiEditor editor = null;
	private DefaultListModel listModel = null;
	private MouseListener ml = null;
	private StringBuffer sb = new StringBuffer();

	/**
	 * constructor
	 * @param me
	 * @param mp
	 */
	protected MultiFlagCBList(MultiEditor me, MultiPopup mp)   {
		myPopup = mp;
		editor = me;
		setOpaque(false);
		listModel = new DefaultListModel();
		this.setModel(listModel);

		setCellRenderer(new CellRenderer());

		ml = new MouseAdapter()	{
			public void mousePressed(MouseEvent e)
			{
				if(!isEnabled()){
					return;
				}
				int index = locationToIndex(e.getPoint());
				if (index != -1) {
					JCheckBox checkbox = (JCheckBox)listModel.getElementAt(index);
					checkbox.setSelected(!checkbox.isSelected());
					e.consume();
					repaint();
				}
			}
		};
		addMouseListener(ml);

		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// this is needed because clicking on item grabs focus and keystrokes dont go to popup
		// but cant be done if popup is a dialog
		if(myPopup instanceof JPopupMenu){
			setRequestFocusEnabled(false);
			setFocusable(false);
		}
	}

	/**
	 * load the list model with checkboxes, one for each metaflag
	 * @param flags
	 */
	protected void loadFlags(MetaFlag[] flags) {
		// make sure this is clear
		clearTypeahead();

		// release memory
		for (int i=0;i<listModel.size();++i) {
			FlagCheckBox fbc = (FlagCheckBox)listModel.get(i);
			fbc.metaflag=null;
		}

		listModel.clear();
		for (int i=0;i<flags.length;++i) {
			FlagCheckBox fbc =new FlagCheckBox(flags[i]);
			listModel.addElement(fbc);
		}

		if (!listModel.isEmpty()){
			super.ensureIndexIsVisible(0);
			setVisibleRowCount(Math.min(8, listModel.size()));
		}

		revalidate();
	}

	/**
	 * get all metaflags used in the list model
	 * @return
	 */
	protected MetaFlag[] getMetaFlags() {
		MetaFlag[] out = new MetaFlag[listModel.size()];
		for (int i=0;i<listModel.size();++i) {
			out[i] = ((FlagCheckBox)listModel.get(i)).metaflag;
		}
		return out;
	}

	/**
	 * release memory
	 */
	protected void dereference() {
		for (int i=0;i<listModel.size();++i) {
			((FlagCheckBox)listModel.get(i)).metaflag=null;
		}
		listModel.clear();
		listModel = null;

		editor=null;
		myPopup = null;

		removeMouseListener(ml);
		ml = null;

		removeAll();
		setUI(null);
		sb = null;
	}

	/**
	 * getBackground
	 *
	 */
	public Color getBackground() {
		if (editor != null) {
			return editor.getBackground();
		}
		return super.getBackground();
	}

	private class CellRenderer implements ListCellRenderer {
		public Component getListCellRendererComponent(
				JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus)
		{
			JCheckBox checkbox = (JCheckBox) value;
			checkbox.setBackground(isSelected ?
					getSelectionBackground() : getBackground());
			checkbox.setForeground(isSelected ?
					getSelectionForeground() : getForeground());
			checkbox.setEnabled(isEnabled());
			checkbox.setFont(getFont());
			checkbox.setFocusPainted(false);
			checkbox.setBorderPainted(true);
			checkbox.setBorder(cellHasFocus ?
					UIManager.getBorder(
					"List.focusCellHighlightBorder") : NOFOCUSBORDER);
			return checkbox;
		}
	}

	private class FlagCheckBox extends JCheckBox {
		private static final long serialVersionUID = 1L;
		private MetaFlag metaflag=null;
		FlagCheckBox(MetaFlag mf){
			metaflag = mf;
			super.setSelected(metaflag.isSelected());
			setText(metaflag.toString());
		}
		public void setSelected(boolean b) {
			// dont change selection if this control should not get focus yet, other control has invalid data
			if(editor.getFormCellPanel()!=null){
				if(!editor.getFormCellPanel().getFormTable().canStopEditing()){
					return;
				}
			}

			metaflag.setSelected(b);
			super.setSelected(b);

			// reset any typeahead
			clearTypeahead();
		}
	}

	//============================================================
	// key event handling
	//============================================================

	/**
	 * called from popup's key listener
	 * @param _ke
	 */
	protected void keyTyped(KeyEvent _ke) {
		scrollToCharacter(_ke.getKeyChar());
	}
	/**
	 * called when user opens the editor with a key or types a key
	 * @param _c
	 */
	protected void scrollToCharacter(char _c) {
		if (!Character.isDefined(_c)) {
			return;
		}
		if (Utils.isArmed(ENHANCED_FLAG_EDIT)) {
			if (Character.isSpaceChar(_c)) {
				return;
			}
			gotoString(_c);
		} else {
			gotoCharacter(_c);
		}
	}

	/**
	 * scroll to flag that starts with this character
	 * @param _c
	 */
	private void gotoCharacter(char _c) {
		int iStart = Math.max(0, getSelectionModel().getLeadSelectionIndex());
		char c = Character.toLowerCase(_c);
		//look from selection forward
		for (int i = iStart; i < listModel.getSize(); ++i) {
			MetaFlag flag = ((FlagCheckBox)listModel.getElementAt(i)).metaflag;
			char lowerChar = Character.toLowerCase(flag.toString().charAt(0));
			if (lowerChar == c) {
				selectFlag(i);
				return;
			}
		}
		//look from 0 to selection
		for (int i = 0; i < iStart; ++i) {
			MetaFlag flag = ((FlagCheckBox)listModel.getElementAt(i)).metaflag;
			char lowerChar = Character.toLowerCase(flag.toString().charAt(0));
			if (lowerChar == c) {
				selectFlag(i);
				return;
			}
		}
	}

	/**
	 * move to this flag in the list
	 * @param _i
	 */
	private void selectFlag(int _i) {
		ensureIndexIsVisible(_i);
		ListSelectionModel lsm = getSelectionModel();
		int old = lsm.getLeadSelectionIndex();
		lsm.setSelectionInterval(_i, _i);
		if (old >= 0) {
			paintImmediately(getCellBounds(old, old));
		}
		paintImmediately(getCellBounds(_i, _i));
		super.ensureIndexIsVisible(_i - 1);
		super.ensureIndexIsVisible(_i);
		super.ensureIndexIsVisible(_i + 1);
	}

	/**
	 * called from popup key listener
	 * @param _ke
	 */
	protected void keyPressed(KeyEvent _ke) {
		int keyCode = _ke.getKeyCode();

		if (_ke.isControlDown()) {
			if (keyCode == KeyEvent.VK_A) { // select all
				toggleAll(true, _ke.isShiftDown());
				_ke.consume();
			} else if (keyCode == KeyEvent.VK_D) { // deselect all
				toggleAll(false, _ke.isShiftDown());
				_ke.consume();
			}
			return;
		}
		if(myPopup instanceof JPopupMenu){ //dialog doesnt need this
			if(keyCode==KeyEvent.VK_DOWN){ // move down one row
				ListSelectionModel lsm = getSelectionModel();
				int old = lsm.getLeadSelectionIndex();
				old++;
				if(old<listModel.size()){
					selectFlag(old);
				}else{
				    UIManager.getLookAndFeel().provideErrorFeedback(null);
				}
				return;
			}
			if(keyCode==KeyEvent.VK_UP){ // move up one row, wrap if necessary
				ListSelectionModel lsm = getSelectionModel();
				int old = lsm.getLeadSelectionIndex();
				old--;
				if(old>=0){
					selectFlag(old);
				}else{
				    UIManager.getLookAndFeel().provideErrorFeedback(null);
				}
				return;
			}
		}

		if (Utils.isArmed(ENHANCED_FLAG_EDIT)) {
			if (keyCode == KeyEvent.VK_BACK_SPACE) {
				delete();
				selectFlag(sb.toString());
				repaint();
			}
		}

		if (keyCode == KeyEvent.VK_SPACE) {
			toggle();
		}
	}

	/**
	 * called when enhancedflag is turned on and user types a key
	 * @param _c
	 */
	private void gotoString(char _c) {
		char c = Character.toLowerCase(_c);

		MetaFlag flag = canAppend(sb.toString() + Character.toString(c));
		if (flag !=null) {
			sb.append(c);
			//use case of string found
			myPopup.setLabelText(flag.toString().substring(0, sb.length()));
			selectFlag(sb.toString());
		}else{
			if(Character.isLetter(c) || Character.isDigit(c)){  // valid character not found
			    UIManager.getLookAndFeel().provideErrorFeedback(null);
			}
		}
	}

	/**
	 * does any flag description start with this string
	 * @param _s
	 * @return
	 */
	private MetaFlag canAppend(String _s) {
		for (int i = 0; i < listModel.getSize(); ++i) {
			MetaFlag flag = ((FlagCheckBox)listModel.getElementAt(i)).metaflag;
			String str = flag.toString().toLowerCase();
			if (str.startsWith(_s)) {
				return flag;
			}
		}
		return null;

	}

	/**
	 * if flag starts with this string, scroll to it
	 * @param _s
	 */
	private void selectFlag(String _s) {
		for (int i = 0; i < listModel.getSize(); ++i) {
			MetaFlag flag = ((FlagCheckBox)listModel.getElementAt(i)).metaflag;
			String strTest = flag.toString().toLowerCase();
			if (strTest.startsWith(_s)) {
				selectFlag(i);
				return;
			}
		}
	}

	/**
	 * called when user presses backspace when enhancedflag is turned on
	 */
	private void delete() {
		int i = sb.length() -1;
		if (i >= 0) {
			sb.deleteCharAt(i);
			// get current case and truncate
			String curtext = myPopup.getLabelText().substring(0,sb.length());
			myPopup.setLabelText(curtext);//sb.toString());
		}
	}
	/**
	 * space was pressed, select or deselect the flag
	 */
	private void toggle() {
		int index = getSelectionModel().getLeadSelectionIndex();
		if (index < 0) {
			index = 1;
		}

		FlagCheckBox flagCB = ((FlagCheckBox)listModel.getElementAt(index));
		ListSelectionModel lsm = null;
		int old = -1;
		if (flagCB.isSelected()) {
			flagCB.setSelected(false);
		} else {
			flagCB.setSelected(true);
		}

		lsm = getSelectionModel();
		old = lsm.getLeadSelectionIndex();
		if (old != index) {
			lsm.setLeadSelectionIndex(index);
			if (old >= 0) {
				paintImmediately(getCellBounds(old, old));
			}
		}
		paintImmediately(getCellBounds(index, index));
	}

	private void clearTypeahead(){
		if(sb.length()>0){
			sb.delete(0,sb.length());
			myPopup.setLabelText("");
		}
	}
	/**
	 * @param _select if true select all
	 * @param _acceptChanges if true save the changes and close the editor
	 */
	private void toggleAll(boolean _select, boolean _acceptChanges) {
		for (int i = 0; i < listModel.getSize(); ++i) {
			FlagCheckBox flagCB = ((FlagCheckBox)listModel.getElementAt(i));
			flagCB.setSelected(_select);
		}
		if (_acceptChanges) {
			editor.acceptChanges();
		} else {
			// this is needed or the selection change can not be seen
			paintImmediately(getCellBounds(0, listModel.getSize() - 1));
		}
	}
}
