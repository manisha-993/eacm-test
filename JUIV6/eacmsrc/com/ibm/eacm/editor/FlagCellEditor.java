//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.editor;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import com.ibm.eacm.edit.form.FormCellPanel;
import com.ibm.eacm.edit.form.FormTable;
import com.ibm.eacm.objects.PasteData;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.objects.Utils;

import java.awt.event.*;

import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.MetaFlag;

/***************
 * 
 * Cell editor for SingleFlagAttribute and 
 * - used in CrossTable and SearchTable
 * @author Wendy Stimpson
 */
//$Log: FlagCellEditor.java,v $
//Revision 1.5  2014/01/14 19:22:08  wendy
//RCQ00288888 - accept value on loss of focus
//
//Revision 1.4  2013/11/11 20:43:08  wendy
//Force focus into combo, arrow keys were not always accepted
//
//Revision 1.3  2013/08/14 16:50:30  wendy
//paste has cell listener now, remove it before refresh of metaflag
//
//Revision 1.2  2013/07/28 02:25:37  wendy
//force popup for enhancedflag in grid
//
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class FlagCellEditor extends AttrCellEditor 
{
	private static final long serialVersionUID = 1L;
	private static final int MAX_ROW_CNT=5;
	
	private FlagComboBox combo = null;
	private KeyEvent preloadkey = null;
	private ActionListener actListener = null;
	private boolean doNotFireEvent = false; // setSelectedItem() now causes an action event, prediction is lost if that happens
	private boolean escapePressed = false; // must recognize when menu is closed due to escape keypress RCQ00288888


	//===============added for using in a form
	private FormCellPanel fcp = null;
	public void setFormCellPanel(FormCellPanel f) { // added for formeditor
		fcp=f; 
		// remove the popuplistener, size does not need to be modified in the form
		combo.removePopupMenuListener(combo.popuplistener);
		combo.popuplistener = null;
	}
	public FormCellPanel getFormCellPanel(){// added for formeditor
		return fcp;
	}

	public void setForeground(Color fg) {
		combo.setForeground(fg);
	}
	public void setFont(Font font) {
		combo.setFont(font);
	}
	public void setOpaque(boolean isOpaque) {
		combo.setOpaque(isOpaque);
	}
	//====================================
	public FlagCellEditor(){
		this(false);
	}
	public FlagCellEditor(boolean insearch){
		super(insearch);
		combo = new FlagComboBox();
	}

	/**
	 * hang onto the action listener, add it after attribute is set
	 * @param dl
	 */
	public void addActionListener(ActionListener dl){
		actListener = dl;
	}
	/**
	 * use editor rules when pasting
	 * @param pasteObj 
	 * @return
	 */
	public boolean paste(Object pasteObj,boolean editOpen){
		if(pasteObj instanceof PasteData){
			PasteData pd = (PasteData)pasteObj;
			if(!pd.getAttrCode().equals(attr.getAttributeCode())){
				UIManager.getLookAndFeel().provideErrorFeedback(null);
				//msg80000 = Invalid value "{0}" specified for "{1}"
				Object value = pd.getValue();
				String strvalue = value.toString();
				if(value instanceof MetaFlag[]){
					MetaFlag[] mf = (MetaFlag[])value;
					for(int i=0; i<mf.length; i++){
						if(mf[i].isSelected()){
							strvalue = mf[i].toString();
							break; 
						}
					}
				}
				strvalue = pd.getAttrCode()+":"+strvalue;
				//if(editOpen){ this works, but leave out for now
					com.ibm.eacm.ui.UI.showErrorMessage(null, Utils.getResource("msg80000",strvalue,
						attr.getLongDescription()));
				/*}else {
					if(rstTable!=null & rstTable.getSelectedRowCount()>1){
						int reply = com.ibm.eacm.ui.UI.showErrorCancelPaste(null, Utils.getResource("msg80000",strvalue,
								attr.getLongDescription()));
						if (reply == CANCEL_BUTTON){
							rstTable.cancelAllPaste();
						}
					}else{
						com.ibm.eacm.ui.UI.showErrorMessage(null, Utils.getResource("msg80000",strvalue,
								attr.getLongDescription()));
					}
				}*/
				combo.requestFocusInWindow();
				return false;
			}
			
			if(actListener != null){
				combo.removeActionListener(actListener);
			}
			// this does removeall and any listener is notified
			combo.refresh((MetaFlag[])pd.getValue());
			if(actListener != null){
				combo.addActionListener(actListener);
			}
			
			return true;
		}
		if(pasteObj instanceof String){
			if(!combo.refresh((String)pasteObj)){
				UIManager.getLookAndFeel().provideErrorFeedback(null);
				//msg80000 = Invalid value "{0}" specified for "{1}"
				//if(editOpen){ this works but may be confusing, leave it out for now
					com.ibm.eacm.ui.UI.showErrorMessage(null, Utils.getResource("msg80000",pasteObj.toString(),
							attr.getLongDescription()));
				/*}else {
					if(rstTable!=null & rstTable.getSelectedRowCount()>1){
						int reply = com.ibm.eacm.ui.UI.showErrorCancelPaste(null, Utils.getResource("msg80000",pasteObj.toString(),
								attr.getLongDescription()));
						if (reply == CANCEL_BUTTON){
							rstTable.cancelAllPaste();
						}
					}else{
						com.ibm.eacm.ui.UI.showErrorMessage(null, Utils.getResource("msg80000",pasteObj.toString(),
								attr.getLongDescription()));
					}
				}*/
				combo.requestFocusInWindow();
				return false;
			}
			return true;
		}

		UIManager.getLookAndFeel().provideErrorFeedback(null);
		//msg80000 = Invalid value "{0}" specified for "{1}"
		com.ibm.eacm.ui.UI.showErrorMessage(null, Utils.getResource("msg80000",pasteObj.toString(),
				attr.getLongDescription()));
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.AbstractCellEditor#stopCellEditing()
	 */
	public boolean stopCellEditing() { 
		if (Utils.isArmed(ENHANCED_FLAG_EDIT)) {
			((FlagComboBoxEditor)combo.getEditor()).clearPrediction();
		}
		return super.stopCellEditing(); 
	}

    /* (non-Javadoc)
	 * return the component for use in joptionpane
	 * @see com.ibm.eacm.editor.AttrCellEditor#getComponent()
	 */
	public Component getComponent() {
		return combo;
	}


	/* (non-Javadoc)
	 * @see com.ibm.eacm.editor.AttrCellEditor#preloadKeyEvent(java.awt.event.KeyEvent)
	 */
	protected void preloadKeyEvent(KeyEvent anEvent){
		int keyCode = anEvent.getKeyCode();
		if (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9 || 
				keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z ||
    			keyCode >= KeyEvent.VK_NUMPAD0 && keyCode <= KeyEvent.VK_NUMPAD9) {
			// forward keystroke to editor later
			preloadkey = anEvent;
		}
		if(keyCode == KeyEvent.VK_SPACE && !combo.isEditable()){
			preloadkey = anEvent; // allow blank to showpopup
		}
	}
	/**
	 * release memory
	 */
	public void dereference(){
		preloadkey = null;
		fcp = null;

		if(actListener != null){
			combo.removeActionListener(actListener);
			actListener = null;
		}

		combo.dereference();
		combo = null;	

		super.dereference();
	}
	/**
	 * return the edited value
	 * @see javax.swing.CellEditor#getCellEditorValue()
	 */
	public Object getCellEditorValue() {
		Object o = combo.getSelectedItem();
		if (o != null) {
			MetaFlag[] out = new MetaFlag[1];
			out[0] = (MetaFlag) o;
			out[0].setSelected(true);
			return out;
		}
		return new MetaFlag[0];   
	}

	/**
	 * return a component to edit the cell, the editor completely replaces the renderer when editing is in progress
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		//set the value in the editor here
		if (value instanceof EANFlagAttribute) {
			setAttribute((EANFlagAttribute) value);
		}
		return combo;
	} 
	/**
	 * set the attribute to use for this editor execution
	 * @param ean
	 */
	public void setAttribute(EANAttribute ean) {
		super.setAttribute(ean);
		MetaFlag[] value = null;
		if (ean != null) {
			value = (MetaFlag[]) ean.get();
		}
		if(actListener != null){
			combo.removeActionListener(actListener);
		}
		combo.refresh(value);
		if(actListener != null){
			combo.addActionListener(actListener);
		}
	}

	private class PopupListener implements PopupMenuListener {
		private static final int MAX_WIDTH = 500;

		/* (non-Javadoc)
		 * @see javax.swing.event.PopupMenuListener#popupMenuCanceled(javax.swing.event.PopupMenuEvent)
		 */
		public void popupMenuCanceled(PopupMenuEvent e) {
			if(!escapePressed){ //RCQ00288888
				//user clicked someplace else when popup was open, stop the edit
				stopCellEditing();
			}
			escapePressed =false;
		}
		/* (non-Javadoc)
		 * @see javax.swing.event.PopupMenuListener#popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent)
		 */
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}

		/* (non-Javadoc)
		 * @see javax.swing.event.PopupMenuListener#popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent)
		 */
		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			//Set the size of the popup to be wide enough to view all items in the popup.
			JComboBox box = (JComboBox)e.getSource();
			Object comp = box.getUI().getAccessibleChild(box, 0);
			if ((comp instanceof JPopupMenu))	{
				JComponent scrollPane = (JComponent)((Container)comp).getComponent(0);
				JList list = ((ComboPopup)comp).getList();
				Dimension popupSize = box.getSize();
				popupSize.setSize(popupSize.width, getPopupHeightForRowCount(box,list));
				Rectangle popupBounds = computePopup(box,0, box.getBounds().height, 
						Math.min(MAX_WIDTH, box.getPreferredSize().width), popupSize.height);

				Dimension popupSizeAdjusted = popupBounds.getSize();
				// only adjust if the calculation is larger
				if(popupSize.width<popupSizeAdjusted.width){
					scrollPane.setMaximumSize(popupSizeAdjusted);
					scrollPane.setPreferredSize(popupSizeAdjusted);
					scrollPane.setMinimumSize(popupSizeAdjusted);
					scrollPane.setSize(popupSizeAdjusted);
				}
			}
		}
		private int getOffHorz(Rectangle scr, Rectangle r) {
			int w = (scr.x + scr.width) - 5;
			int W = r.x + r.width;
			if (W >= w) {
				return w - W;
			}
			return 0;
		}
		private int getOffVert(Rectangle scr, Rectangle r, int y) {
			int h = (scr.y + scr.height) - 5;
			int H = r.y + r.height;
			if (H >= h) {
				return -r.height;
			}
			return y;
		}
		private Rectangle computePopup(JComboBox comboBox,int x, int y, int w, int h) {
			Rectangle r = new Rectangle(x, y, w, h);
			Point p = new Point(0, 0);
			Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
			SwingUtilities.convertPointFromScreen(p, comboBox);
			Rectangle screen = new Rectangle(p.x, p.y, scr.width, scr.height - 100);
			int myX = getOffHorz(screen, r);
			int myY = getOffVert(screen, r, y);
			return new Rectangle(myX, myY, r.width, r.height);
		}
		private int getPopupHeightForRowCount(JComboBox comboBox, JList list) { 
			int maxRowCount =comboBox.getMaximumRowCount();
			int iRowCount = comboBox.getModel().getSize(); 
			int rowCount = Math.min(maxRowCount, iRowCount); 
			int height = 0; 
			ListCellRenderer rend = list.getCellRenderer(); 

			if (iRowCount >= 1) { 
				Object o = list.getModel().getElementAt(0); 
				if (o != null) { 
					Component c = rend.getListCellRendererComponent(list, o, 0, false, false); 
					if (c != null) { 
						height = (c.getPreferredSize().height * rowCount);
					} 
				} 
			} 

			return height == 0 ? 100 : height; 
		}
	}
	/***************
	 * 
	 * used by the cell editor to display the values
	 *
	 */
	private class FlagComboBox extends JComboBox
	{
		private static final long serialVersionUID = 1L;
		private static final String KEYPAD_PREFIX = "NumPad-";

		private PopupListener popuplistener = new PopupListener();
		FlagComboBox(){
			setAutoscrolls(true);
			setMaximumRowCount(MAX_ROW_CNT);
		
			if (Utils.isArmed(ENHANCED_FLAG_EDIT)) {
				FlagComboBoxEditor fcbe = new FlagComboBoxEditor();
				setEditor(fcbe);
				addActionListener(fcbe);

				setEditable(true);
			}
			this.setRenderer(new BoxListRend());
			this.addPopupMenuListener(popuplistener);
		}

		/* (non-Javadoc)
		 * @see javax.swing.JComboBox#fireActionEvent()
		 */
		protected void fireActionEvent() {
			if(doNotFireEvent){
				return;
			}
			super.fireActionEvent();
		}
		/**
		 * refresh this editor with the flags from the attribute it represents
		 * also called from paste
		 * @param mfa
		 */
		void refresh(MetaFlag[] mfa) {
			FormCellPanel fcp = getFormCellPanel();
			boolean hadformtablelistener = false;
			if(fcp!=null){
				//remove this listener to prevent looping
				FormTable formtable = fcp.getFormTable();
				ActionListener[] mls = (ActionListener[])combo.getListeners(ActionListener.class);
				for(int x=0; x<mls.length; x++){
					if(mls[x] instanceof FormTable){
						hadformtablelistener = true;
						break;
					}
				}
				if(hadformtablelistener){
					combo.removeActionListener(formtable);
				}
			}

			removeAllItems();

			MetaFlag curFlag = null;
			if (mfa != null) {
				for (int i = 0; i < mfa.length; ++i) {
					MetaFlag flag = mfa[i];
					if (flag.isSelected() && curFlag == null) {
						curFlag = flag;
					}

					addItem(flag);
				}
			}

			// do this even if curflag is null, it clears the list box
			setSelectedItem(curFlag);

			if(fcp != null && hadformtablelistener){
				// add this back so it can detect user updates
				FormTable formtable = fcp.getFormTable();
				combo.addActionListener(formtable);
			}			
		}

		/**
		 * called from paste
		 * @param _flagDesc
		 */
		boolean refresh(String flagDesc) {
			boolean descmatches = false;
			for (int i = 0; i < this.getItemCount(); ++i) {
				MetaFlag flag = (MetaFlag)getItemAt(i);
				if(flag.toString().equals(flagDesc)){
					descmatches = true;
					break;
				}
			}

			if(!descmatches){
				return false;
			}

			MetaFlag curFlag = null;

			for (int i = 0; i < this.getItemCount(); ++i) {
				MetaFlag flag = (MetaFlag)getItemAt(i);
				if(flag.toString().equals(flagDesc)){
					flag.setSelected(true);
				}else{
					flag.setSelected(false);
				}
				if (flag.isSelected() && curFlag == null) {
					curFlag = flag;
				}
			}

			// do this even if curflag is null, it clears the list box
			setSelectedItem(curFlag);
			return true;
		}

		/* (non-Javadoc)
		 * @see java.awt.Component#getBackground()
		 */
		public Color getBackground() {
			return FlagCellEditor.this.getBackground("");
		} 
		protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {
			//key typed events go to editor
			if(preloadkey != null && e.getID() == KeyEvent.KEY_TYPED){
				preloadKeyEvent();
				preloadkey = null;
			}

			return super.processKeyBinding(ks, e, condition, pressed); 
		}
		/* (non-Javadoc)
		 * @see javax.swing.JComboBox#processKeyEvent(java.awt.event.KeyEvent)
		 */
		public void processKeyEvent(KeyEvent ke) {
			super.processKeyEvent(ke);

			if (ke.getID()==KeyEvent.KEY_RELEASED){
				int keycode = ke.getKeyCode();
				//ENTER with CTRL opens the editor in a table
				if (keycode==KeyEvent.VK_ENTER && ke.getModifiers()==0){
					//stop editing and accept any partially edited value as the value of the editor.
					stopCellEditing();
				}else if (keycode==KeyEvent.VK_ESCAPE){
					//cancel editing and not accept any partially edited value.
					cancelCellEditing();
				}
			}
		}

		/**
		 * a key was typed to open the editor, use it to find the matching item in the combobox
		 */
		private void preloadKeyEvent() {
			if(preloadkey!=null){
				String str = KeyEvent.getKeyText(preloadkey.getKeyCode());
				if(str.startsWith(KEYPAD_PREFIX)){
					str = str.substring(KEYPAD_PREFIX.length());
				}

				if (getEditor() instanceof FlagComboBoxEditor){
					if (preloadkey.isShiftDown()) {
						((FlagComboBoxEditor)getEditor()).setText(str.toUpperCase());
					} else {
						((FlagComboBoxEditor)getEditor()).setText(str.toLowerCase());
					}
					((FlagComboBoxEditor)getEditor()).requestFocusInWindow();//without this, grid table doesnt show popup
				}else{
					// find item in list that starts with this string and move to it
					for (int i = 0;i<getItemCount();++i) {
						Object o = getItemAt(i);
						if (o != null) {
							String tmp = o.toString().toLowerCase();
							if (tmp.startsWith(str.toLowerCase())) {
								doNotFireEvent = true;
								setSelectedItem(o); 
								doNotFireEvent = false;
								break;
							}
						}
					}
					requestFocusInWindow(); // arrow keys cause combo to close intermittently - force focus to it
					showPopup();
				}
				preloadkey = null;
			}
		}

		/**
		 * release memory
		 */
		void dereference(){
			if(popuplistener!=null){
				this.removePopupMenuListener(popuplistener);
				popuplistener= null;
			}

			((BoxListRend)getRenderer()).dereference();

			setUI(null);
			removeAll();
			ComboBoxEditor cbe = getEditor();
			if (cbe instanceof FlagComboBoxEditor) {
				FlagComboBoxEditor fcbe = (FlagComboBoxEditor)cbe;
				removeActionListener(fcbe);
				fcbe.dereference();
				setEditor(null);
			}
		}
	}

	/**
	 * used when enhanceflag.arm is present, allows typeahead in combobox
	 */
	private class FlagComboBoxEditor extends JTextField implements ComboBoxEditor, ActionListener, 
	FocusListener, KeyListener
	{
		private static final long serialVersionUID = 1L;
		private boolean byPassValidation = false;
		private String prediction = null;
		private Object curvalue = null;
		private MouseListener ml = null;

		FlagComboBoxEditor(){
			super("",9);
			setBorder(null);
		
			ml = new MouseAdapter(){
				/**
				 * MouseListener interface, need to capture mouse clicks to show popup when already have focus
				 */
				public void mouseClicked(MouseEvent me) { 
					if (me.getClickCount() == 2){
						if (!combo.isPopupVisible()){    	
							combo.showPopup();
						}
					}
				}
			};
			addMouseListener(ml);
			addFocusListener(this);
			addKeyListener(this);
		}

		/** This is called because this is the editor for the jcombobox, it is called
		 * when setSelectedItem(..) is called and when an item is added to the list
		 * or when the editor is initialized
		 * @see javax.swing.ComboBoxEditor#setItem(java.lang.Object)
		 */
		public void setItem(Object obj) {  	 
			byPassValidation = true;
			if (obj != null) {
				if (obj != curvalue) {
					setText("");
					prediction = null;
				}
			} else {
				setText("");
			}
			byPassValidation = false;
			curvalue = obj;
		}
		/**
		 * @see javax.swing.ComboBoxEditor#getItem()
		 */
		public Object getItem() {
			return curvalue;
		}
		/**
		 * @see javax.swing.ComboBoxEditor#getEditorComponent()
		 */
		public Component getEditorComponent() {
			return this;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
		 */
		public void focusGained(FocusEvent e) {
			boolean showpopup = true;
			// when editor opens it gets focus and the popup is hidden, show it again only if not in joptionpane parent
			Component comp = e.getComponent();
			while(comp!=null) {
				if (comp instanceof JOptionPane){
					showpopup = false;
					break;
				}
				comp = comp.getParent();
			}
			if (showpopup && combo.isShowing()){
				combo.showPopup();
			}
		}
		/* (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
		 */
		public void focusLost(FocusEvent e) {}

		/* (non-Javadoc)
		 * called when item in box is selected or first item is added
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox)e.getSource();
			curvalue= cb.getSelectedItem();
		}

		/**
		 * release memory
		 */
		void dereference(){
			fcp = null; 
			removeMouseListener(ml);
			removeFocusListener(this);
			removeKeyListener(this);

			ml = null;
			prediction = null;
			curvalue = null;

			setUI(null);
			removeAll();
		}
		/**
		 * getBackground to reflect current state 
		 * if used in an editor dialog for the fill component, the bg isn't always right
		 * required isnt set until the user gets the lock unless it is a new entity
		 */
		public Color getBackground() {    	
			if(combo!=null){//installDefaults runs before combo is instantiated
				return combo.getBackground();
			}

			return super.getBackground();
		}
		/**
		 * @see javax.swing.JTextField#createDefaultModel()
		 */
		protected Document createDefaultModel() {
			return new BoxDoc();
		}

		/**
		 * @see javax.swing.JComponent#updateUI()
		 */
		public void updateUI() {
			// must be here or caret is not initialized
			BasicTextFieldUI myui = new BasicTextFieldUI() {
				protected void paintSafely(Graphics g) {
					super.paintSafely(g);
					paintPrediction(g);
				}
			};
			setUI(myui);
			invalidate();
		}

		/**
		 * paint the predicted text in yellow background after the typed text
		 * @param g
		 */
		private void paintPrediction(Graphics g) {
			String curText = getText();
			FontMetrics fm = g.getFontMetrics();
	
			if (Routines.have(prediction)) {
				Color cOrig = g.getColor();
				// draw the predicted text in yellow background after the typed text
				int x = getWidth(fm,curText);
				g.setColor(Color.yellow);
				g.fillRect(x,0,getWidth(fm,prediction),getHeight());

				g.setColor(Color.black);
				g.drawString(prediction,x,fm.getAscent() + 1);

				g.setColor(cOrig);
			} else {
				if (!Routines.have(curText)) { // nothing typed in editor
					if (curvalue != null) {
						g.drawString(curvalue.toString(),0,fm.getAscent() + 1);
					}
				}
			}
		}

		private int getWidth(FontMetrics fm, String s) {
			if (fm != null && s != null) {
				return fm.stringWidth(s);
			}
			return 0;
		}

		/**
		 * Document for textfield, only allow user to type a char that is in the combo list
		 *
		 */
		private class BoxDoc extends PlainDocument {
			private static final long serialVersionUID = 1L;
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				String str2 = validate(str);
				if (str2 !=null) {
					super.insertString(offs, str2, a);
				}
			}

			public void remove(int offs, int len) throws BadLocationException {
				super.remove(offs,len);
				validate("");
			}
		}

		/**
		 * select combobox index and paint lookahead string in textfield
		 *
		 * @param newStr String to insert
		 * @return String if not null insert into document
		 */
		private String validate(String newstr) {
			if (byPassValidation) {
				return newstr;
			}

			String str = getText() + newstr;
			if (str.length() > 0) {
				for (int i = 0;i<combo.getItemCount();++i) {
					Object o = combo.getItemAt(i);
					if (o != null) {
						String tmp = o.toString();
						if (tmp.toLowerCase().startsWith(str.toLowerCase())) {
							curvalue = o;
							prediction = o.toString().substring(str.length());
							//combo.setSelectedIndex(i); // this ends up commiting the change so prediction is lost
							doNotFireEvent = true;
							combo.setSelectedItem(o); // allow cascade flags to work like LSCCLIFECYCLE
							doNotFireEvent = false;
							if(preloadkey==null){
								combo.showPopup();
							}
							return o.toString().substring(getText().length(),str.length()); // get the same case as the comboitem
						}
					}
				}
			} else {
				//nothing is in the textfield
				prediction = null;
				if (indexOfCurrent() >= 0) {
					doNotFireEvent = true;
					combo.setSelectedItem(curvalue);
					doNotFireEvent = false;
					if (preloadkey==null && combo.isShowing()) {
						combo.showPopup();
					}
				}
			}

			return null;
		}
		/**
		 * Find index of this object in the JComboBox
		 */
		private int indexOfCurrent() {
			for (int i = 0;i<combo.getItemCount();i++) {
				Object o = combo.getItemAt(i);
				if (o != null && o == curvalue){
					return i;
				}
			}

			return -1;
		}

		/* (non-Javadoc)
		 * allow escape or enter in textfield to end the edit
		 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
		 */
		public void keyReleased(KeyEvent e) {
			boolean indialog = false;
			Component comp = e.getComponent();
			while(comp!=null) {
				if (comp instanceof JOptionPane){
					indialog = true;
					break;
				}
				comp = comp.getParent();
			}
			//ENTER with CTRL opens the editor in a jtable
			if (e.getKeyCode() == KeyEvent.VK_ENTER && e.getModifiers()==0) {
				if(indialog){
					// get rid of the yellow prediction
					int selected = indexOfCurrent();
					if(selected>=0){
						setItem(null);
						prediction = null;
						combo.setSelectedIndex(selected);
					}
				}else{
					//stop editing and accept any partially edited value as the value of the editor.
					stopCellEditing();
				}
				e.consume();
			}else if (e.getKeyCode()==KeyEvent.VK_ESCAPE){
				if(indialog){
					// get rid of the yellow prediction
					int selected = indexOfCurrent();
					if(selected>=0){
						setItem(null);
						prediction = null;
						combo.setSelectedIndex(selected);
					}
				}else{
					//cancel editing and not accept any partially edited value.
					cancelCellEditing();
					//form must clear the prediction because editor is not replaced by renderer
					// original value must be restored
					if(getFormCellPanel() !=null){
						prediction = null;
						combo.refresh((MetaFlag[])getAttribute().get());
						combo.hidePopup();
					}
				}
				e.consume();
			}
			escapePressed = false; // reset this
		}
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_ESCAPE){
				escapePressed = true; // edit must be canceled, not stopped when menu is canceled RCQ00288888
			}
		}
		public void keyTyped(KeyEvent e) {}

		protected void clearPrediction(){
			//form must clear the prediction because editor is not replaced by renderer
			if(prediction != null && getFormCellPanel() !=null){
				// get rid of the yellow prediction
				int selected = indexOfCurrent();
				if(selected>=0){
					setItem(null);
					prediction = null;
					combo.setSelectedIndex(selected);
				}
			}
		}
	}
	/**
	 * this is used to set the background color of the dropdown list to the color specified by meta requirements
	 *
	 */
	private class BoxListRend extends JLabel implements ListCellRenderer {
		private static final long serialVersionUID = 1L;

		private BoxListRend() {
			setHorizontalAlignment(SwingConstants.LEFT);
			setOpaque(true);
		}

		/**
		 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
		 */
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			setFont(list.getFont());
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			}else{
				setBackground(combo.getBackground());
				setForeground(combo.getForeground());
			}

			setText((value == null) ? "" : value.toString());
			return this;
		}

		/**
		 * release memory
		 */
		private void dereference() {
			removeAll();
			setUI(null);
		}
		/**
		 * Overridden for performance reasons.
		 */
		public void invalidate() {}
		public void validate() {}
		public void revalidate() {}
		public void repaint(long tm, int x, int y, int width, int height) {}
		public void repaint(Rectangle r) { }
		public void repaint() { }
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {	
			// Strings get interned...
			if (propertyName=="text"
				|| propertyName == "labelFor"
					|| propertyName == "displayedMnemonic"
						|| ((propertyName == "font" || propertyName == "foreground")
								&& oldValue != newValue
								&& getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey) != null)) {

				super.firePropertyChange(propertyName, oldValue, newValue);
			}
		}
		public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) { }
	}
}
