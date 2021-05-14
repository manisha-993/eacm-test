//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eacm.preference;


import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.FontUIResource;

import java.awt.event.*;
import java.util.Arrays;
import java.util.prefs.Preferences;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;

/**
 * This class manages preferences for application font
 * @author Wendy Stimpson
 */
// $Log: FontPref.java,v $
// Revision 1.1  2012/09/27 19:39:15  wendy
// Initial code
//
public class FontPref extends JPanel implements ActionListener, ChangeListener,Preferencable
{
	private static final String PREF_FONT_FACE = "font.face";
	private static final String PREF_FONT_SIZE = "font.size";
	private static final String PREF_FONT_STYLE = "font.style";

	/**
	 * default font
	 */
	public static final Font DEFAULT_FONT =UIManager.getLookAndFeelDefaults().getFont("TextField.font"); 
	
	private static final int DEFAULT_FONT_SIZE = DEFAULT_FONT.getSize();
	private static final int DEFAULT_FONT_STYLE = DEFAULT_FONT.getStyle();
	private static final String DEFAULT_FONT_FACE = DEFAULT_FONT.getFontName();
	/**
	 * get the preferred font
	 */
	public static Font getPreferredFont(){
		String fontface =Preferences.userNodeForPackage(PrefMgr.class).get(PREF_FONT_FACE, DEFAULT_FONT_FACE);
		int fontsize = Preferences.userNodeForPackage(PrefMgr.class).getInt(PREF_FONT_SIZE, DEFAULT_FONT_SIZE);
		int fontstyle = Preferences.userNodeForPackage(PrefMgr.class).getInt(PREF_FONT_STYLE, DEFAULT_FONT_STYLE);
		
		if(fontsize==DEFAULT_FONT_SIZE && fontstyle==DEFAULT_FONT_STYLE &&
				DEFAULT_FONT_FACE.equals(fontface)){
			return DEFAULT_FONT;
		}
		return new FontUIResource(fontface,fontstyle,fontsize);
	}

	private static final long serialVersionUID = 1L;
	private static final Dimension spinDimension = new Dimension(30, 20);

	private JPanel fontPnl = new JPanel(new BorderLayout());

	private JLabel lblFont = new JLabel(Utils.getResource("font.name"));
	private JLabel lblSize = new JLabel(Utils.getResource("font.size"));
	private JLabel lblStyle = new JLabel(Utils.getResource("font.style"));

	private JComboBox fontCombo = null;

	private JSpinner fontSize = new JSpinner(new SpinnerNumberModel(DEFAULT_FONT_SIZE, 9, 72, 1));

	private JCheckBox chkBold = new JCheckBox(Utils.getResource("bold"));
	private JCheckBox chkItalic = new JCheckBox(Utils.getResource("italic"));

	private JLabel lblDisplay = new JLabel("e-announce");
	private Font defFnt = null;
	private boolean bFontRefresh = true;		

	private JButton bSave = null;
	private JButton bReset = null;
	private JPanel btnPnl =null;
	private PrefMgr prefMgr=null;

	/**
	 * font Chooser
	 * @param pref
	 */
	protected FontPref(PrefMgr pref) {
		super(new BorderLayout());
		prefMgr = pref;
		init();
	}

	private void init() {
		fontCombo = new JComboBox(loadFonts());
		fontCombo.setToolTipText(Utils.getToolTip("font.name"));

		lblFont.setLabelFor(fontCombo);
		lblFont.setDisplayedMnemonic(Utils.getMnemonic("font.name"));

		lblSize.setLabelFor(fontSize);
		lblSize.setDisplayedMnemonic(Utils.getMnemonic("font.size"));

		Dimension d = new Dimension(50, 70);

		fontSize.setSize(spinDimension);
		fontSize.setPreferredSize(spinDimension);
		fontSize.setMinimumSize(spinDimension);
		fontSize.setToolTipText(Utils.getToolTip("font.size"));

		setFontSize(prefMgr.getPrefNode().getInt(PREF_FONT_SIZE, DEFAULT_FONT_SIZE));
		setFontStyle(prefMgr.getPrefNode().getInt(PREF_FONT_STYLE, DEFAULT_FONT_STYLE));

		lblDisplay.setSize(d);
		lblDisplay.setPreferredSize(d);
		lblDisplay.setToolTipText(Utils.getResource("font.sample"));
		lblDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisplay.setVerticalAlignment(SwingConstants.CENTER);

		fontCombo.addActionListener(this);
		fontSize.addChangeListener(this);
			
		//prevent invalid typed chars and push them into the spinnermodel immediately
		javax.swing.text.DefaultFormatter defForm = (javax.swing.text.DefaultFormatter)((JSpinner.DefaultEditor)fontSize.getEditor()).getTextField().getFormatter();
		defForm.setAllowsInvalid(false);	
		defForm.setCommitsOnValidEdit(true);	
		
		chkBold.addActionListener(this);
		chkItalic.addActionListener(this);

		chkBold.setMnemonic(Utils.getMnemonic("bold"));
		chkBold.setToolTipText(Utils.getToolTip("bold"));
		chkItalic.setMnemonic(Utils.getMnemonic("italic"));
		chkItalic.setToolTipText(Utils.getToolTip("italic"));

		GroupLayout layout = new GroupLayout(fontPnl);
		fontPnl.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.ParallelGroup leftToRight = layout.createParallelGroup();
		GroupLayout.SequentialGroup columns = layout.createSequentialGroup();
		GroupLayout.ParallelGroup col1 = layout.createParallelGroup();
		col1.addComponent(lblFont);
		col1.addComponent(lblSize);
		col1.addComponent(lblStyle);
		columns.addGroup(col1);
		GroupLayout.ParallelGroup col2 = layout.createParallelGroup();
		col2.addComponent(fontCombo);
		col2.addComponent(fontSize);
		GroupLayout.SequentialGroup styles = layout.createSequentialGroup();
		styles.addComponent(chkItalic);
		styles.addComponent(chkBold);
		col2.addGroup(styles);
		columns.addGroup(col2);
		leftToRight.addGroup(columns);		

		GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
		GroupLayout.ParallelGroup fontRow = layout.createParallelGroup();
		fontRow.addComponent(lblFont);
		fontRow.addComponent(fontCombo);
		topToBottom.addGroup(fontRow);
		GroupLayout.ParallelGroup sizeRow = layout.createParallelGroup();
		sizeRow.addComponent(lblSize);
		sizeRow.addComponent(fontSize);
		topToBottom.addGroup(sizeRow);
		GroupLayout.ParallelGroup styleRow = layout.createParallelGroup();
		styleRow.addComponent(lblStyle);
		styleRow.addComponent(chkBold);
		styleRow.addComponent(chkItalic);
		topToBottom.addGroup(styleRow);
		
		layout.setHorizontalGroup(leftToRight);
		layout.setVerticalGroup(topToBottom);

		add(fontPnl,BorderLayout.NORTH);
		add(lblDisplay,BorderLayout.SOUTH);
		
	    setBorder(BorderFactory.createTitledBorder(Utils.getResource("font.border")));// meet accessiblity 
		createButtonPanel();
	}
	private void createButtonPanel(){
		btnPnl = new JPanel(new BorderLayout(5, 5));

		bSave = new JButton(new SaveAction());
//		this is needed or the mnemonic doesnt activate
		bSave.setMnemonic((char)((Integer)bSave.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		bSave.addKeyListener(prefMgr.getKeyListener());
		
		bReset = new JButton(new ResetAction());
		bReset.setMnemonic((char)((Integer)bReset.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		bReset.addKeyListener(prefMgr.getKeyListener());
		
		btnPnl.add(bReset,BorderLayout.EAST);
		btnPnl.add(bSave,BorderLayout.WEST);	
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.preference.Preferencable#getButtonPanel()
	 */
	public JPanel getButtonPanel() {
		return btnPnl;
	}
	/**
	 * release all memory
	 * 
	 */
	public void dereference() {
		defFnt = null;
		fontCombo.removeActionListener(this);
		fontSize.removeChangeListener(this);
		chkBold.removeActionListener(this);
		chkItalic.removeActionListener(this);

		fontCombo.removeAllItems();
		fontCombo = null;
		fontSize.setUI(null);
		fontSize = null;
		chkBold.setUI(null);
		chkBold = null;
		chkItalic.setUI(null);
		chkItalic = null;
		lblDisplay.setUI(null);
		lblDisplay = null;

		fontPnl.removeAll(); 
		fontPnl.setUI(null);
		fontPnl = null;

		lblFont.setLabelFor(null);
		lblFont.setUI(null);
		lblFont = null;
		lblSize.setLabelFor(null);
		lblSize.setUI(null);
		lblSize = null;
		lblStyle.setUI(null);

		// deref button panel
		btnPnl.removeAll();
		btnPnl.setUI(null);
		btnPnl = null;
		SaveAction sa  = (SaveAction)bSave.getAction();
		sa.dereference();
		bSave.removeKeyListener(prefMgr.getKeyListener());
		bSave.setAction(null);
		bSave.setUI(null);
		bSave = null;

		ResetAction ra  = (ResetAction)bReset.getAction();
		ra.dereference();
		bReset.removeKeyListener(prefMgr.getKeyListener());
		bReset.setAction(null);
		bReset.setUI(null);
		bReset = null;

		prefMgr = null;
		removeAll(); // does removeNotify on all children
		setUI(null);
	}

	private DefaultComboBoxModel loadFonts() {
		DefaultComboBoxModel dcm = new DefaultComboBoxModel();
		GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String fonts[] = gEnv.getAvailableFontFamilyNames();

		Arrays.sort(fonts); 

		String defFF = prefMgr.getPrefNode().get(PREF_FONT_FACE, DEFAULT_FONT_FACE);
		for (int i = 0; i < fonts.length; ++i) {
			dcm.addElement(fonts[i]);
			if (fonts[i].equals(defFF)){
				dcm.setSelectedItem(fonts[i]);
			}
		}

		if (defFF == null) {
			dcm.setSelectedItem(null); 
		}

		return dcm;
	}

	private int getFontSize() {
		Number num = ((SpinnerNumberModel) fontSize.getModel()).getNumber();
		if (num != null) {
			return num.intValue();
		}
		return DEFAULT_FONT_SIZE;
	}

	private void setFontSize(int _i) {
		fontSize.setValue(new Integer(_i));
	}

	private String getFontFace() {
		String str = (String)fontCombo.getSelectedItem();
		if (str==null){
			str = DEFAULT_FONT_FACE;
		}

		return str;
	}

	private int getFontStyle() {
		int out = Font.PLAIN;
		if (chkBold.isSelected()) {
			out += Font.BOLD;
		}
		if (chkItalic.isSelected()) {
			out += Font.ITALIC;
		}
		return out;
	}

	private void setFontStyle(int _i) {
		switch (_i) {
		case Font.PLAIN :
			chkBold.setSelected(false);
			chkItalic.setSelected(false);
			break;
		case Font.BOLD :
			chkBold.setSelected(true);
			chkItalic.setSelected(false);
			break;
		case Font.ITALIC :
			chkBold.setSelected(false);
			chkItalic.setSelected(true);
			break;
		case (Font.BOLD + Font.ITALIC) :
		default :
			chkBold.setSelected(true);
			chkItalic.setSelected(true);
			break;
		}
	}

	/**
	 * Objects whose fonts are set using java.awt.Font are not modified if UI changes occur. 
	 * Objects whose fonts are set using javax.swing.plaf.FontUIResource work.
	 */
	private void updateFont() {
		bSave.getAction().actionPerformed(null); // enable save if possible
		if (bFontRefresh){
			// show the newly selected font in the label
			defFnt = new javax.swing.plaf.FontUIResource(getFontFace(), getFontStyle(), getFontSize());
			lblDisplay.setFont(defFnt);
			lblDisplay.revalidate();
			prefMgr.getTopLevelAncestor().setSize(prefMgr.getTopLevelAncestor().getPreferredSize());
		}
	}

	/**
	 * called when font preference is reset
	 */
	private void resetFont() {
		bFontRefresh = false; // tell listeners to ignore these chgs
		setFontSize(DEFAULT_FONT_SIZE);
		setFontStyle(DEFAULT_FONT_STYLE);
		fontCombo.setSelectedItem(null);
		for (int i = 0; i < fontCombo.getItemCount(); ++i) {
			String o = (String)fontCombo.getItemAt(i);
			if (DEFAULT_FONT_FACE.equals(o)) {
				fontCombo.setSelectedIndex(i);
				break;
			}
		}
		bFontRefresh = true;

		lblDisplay.setFont(DEFAULT_FONT); 
		updateUIwithFont(DEFAULT_FONT);
	}

	private void updateUIwithFont(Font font){
		EACM.updateUIwithFontPref(font);
		SwingUtilities.updateComponentTreeUI(prefMgr.getTopLevelAncestor());
		prefMgr.updateChooserUIs(); //must update other panels too

		Container parent = prefMgr.getTopLevelAncestor();
		if ((parent.getSize().width != parent.getPreferredSize().width)||
				(parent.getSize().height != parent.getPreferredSize().height)){
			parent.setSize(parent.getPreferredSize());
		}
	}
	private void saveFont() {
		prefMgr.getPrefNode().put(PREF_FONT_FACE, getFontFace());
		prefMgr.getPrefNode().putInt(PREF_FONT_SIZE, getFontSize());
		prefMgr.getPrefNode().putInt(PREF_FONT_STYLE, getFontStyle());

		updateUIwithFont(defFnt);
	}

	private class ResetAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "reset.pref";
		ResetAction() {
			super(CMD,"Font");
		}

		public void actionPerformed(ActionEvent e) {
			prefMgr.getPrefNode().put(PREF_FONT_FACE, DEFAULT_FONT_FACE);
			prefMgr.getPrefNode().putInt(PREF_FONT_SIZE, DEFAULT_FONT_SIZE);
			prefMgr.getPrefNode().putInt(PREF_FONT_STYLE, DEFAULT_FONT_STYLE);

			resetFont();
		} 	
	}
	private class SaveAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "save.pref";
		SaveAction() {
			super(CMD,"Font");
			setEnabled(checkValues());
		}

		private boolean checkValues(){
			boolean enable = false;
			String defFF = prefMgr.getPrefNode().get(PREF_FONT_FACE, DEFAULT_FONT_FACE);
			int defFSize = prefMgr.getPrefNode().getInt(PREF_FONT_SIZE, DEFAULT_FONT_SIZE);
			int defFStyle = prefMgr.getPrefNode().getInt(PREF_FONT_STYLE, DEFAULT_FONT_STYLE);

			if(!defFF.equals(getFontFace()) || getFontSize()!=defFSize || getFontStyle()!=defFStyle){
				enable = true;
			}

			return enable;
		}
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			if(e==null){ // some other component changed
				bSave.setEnabled(checkValues());
			}else{
				String fontface = (String)fontCombo.getSelectedItem();
				if (fontface != null) {
					saveFont();
					bSave.setEnabled(checkValues());
				}
			}
		} 	
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		updateFont();
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
		updateFont();
	}
	/* (non-Javadoc)
	 * called when dialog is closing
	 * @see com.ibm.eacm.preference.Preferencable#isClosing()
	 */
	public void isClosing() {}
	/* (non-Javadoc)
	 * notify user that changes have not been saved
	 * @see com.ibm.eacm.preference.Preferencable#hasChanges()
	 */
	public boolean hasChanges() { 
		boolean chgs = false;
		if (bSave!=null){
			chgs = bSave.isEnabled();
		}
		return chgs;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.preference.Preferencable#updateFromPrefs()
	 */
	public void updateFromPrefs() {
		bFontRefresh = false; // tell listeners to ignore these chgs
		setFontSize(prefMgr.getPrefNode().getInt(PREF_FONT_SIZE, DEFAULT_FONT_SIZE));
		setFontStyle(prefMgr.getPrefNode().getInt(PREF_FONT_STYLE, DEFAULT_FONT_STYLE));
		String defFF = prefMgr.getPrefNode().get(PREF_FONT_FACE, DEFAULT_FONT_FACE);
		fontCombo.setSelectedItem(null);
		
		for (int i = 0; i < fontCombo.getItemCount(); ++i) {
			String o = (String)fontCombo.getItemAt(i);
			if (defFF.equals(o)) {
				fontCombo.setSelectedIndex(i);
				break;
			}
		}
		bFontRefresh = true;

		Font preffont = getPreferredFont();
		lblDisplay.setFont(preffont); 
		updateUIwithFont(preffont);
	} 
}
