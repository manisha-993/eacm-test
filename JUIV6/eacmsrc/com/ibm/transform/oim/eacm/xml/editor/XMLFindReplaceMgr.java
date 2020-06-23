// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.text.*;

import java.util.*;
import java.text.*;

/******************************************************************************
* This is used for the find/replace dialog.  It is a separate class so state can
* be maintained.  It must work in a modal dialog and because of problems in applets
* and the IBM JRE, modal dialogs must be instantiated at time of use.
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLFindReplaceMgr.java,v $
// Revision 1.1  2012/09/27 19:39:19  wendy
// Initial code
//
// Revision 1.2  2008/01/30 16:27:09  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/04/18 19:47:48  wendy
// Reorganized JUI module
//
// Revision 1.3  2006/01/25 18:59:04  wendy
// AHE copyright
//
// Revision 1.2  2005/10/12 12:48:57  wendy
// Conform to new jtest configuration
//
// Revision 1.1.1.1  2005/09/09 20:39:19  tony
// This is the initial load of OPICM
//
//
class XMLFindReplaceMgr extends JPanel implements XMLEditorGlobals, KeyEventPostProcessor
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.1 $";
    // hang onto all strings user has entered between instantiations
    private static Vector<String> replaceVct = new Vector<String>();
    private static Vector<String> findVct = new Vector<String>();
    private static boolean isCaseSensitive=false, isWrap=true, isMatchWord=false, isForward=true;
    private static int findComboIndex=-1, replaceComboIndex=-1;

    private JComboBox findCombo=null, replaceCombo=null;
    private JRadioButton forwardRB=null, backwardRB=null;
    private JCheckBox CBcaseSensitive=null, wrapCB=null, matchWordCB=null;
    private XMLEditorPane editor=null;

    private Action findAction=null, replaceAction=null, replaceAllAction=null;
    private JTextField findField=null, replaceField=null;
    private ActionListener findComboListener=null, replaceComboListener=null; // need ref for later release
    private JButton[] buttons=null;
    private Action[] allActions = new Action[3];

    XMLFindReplaceMgr(XMLEditorPane xeditor)
    {
        super(false);
        this.editor=xeditor;
        getAccessibleContext().setAccessibleDescription("Find or Replace text.");
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        init();
    }
    private void init()
    {
        JPanel fieldPanel = new JPanel(false);
        JPanel textPanel = new JPanel();
        JPanel ctrlPanel = new JPanel(false);
        JPanel ckboxPanel = new JPanel(false);
        JPanel rbPanel = new JPanel(false);
        JPanel buttonPanel = new JPanel(false);
        ButtonGroup bg = new ButtonGroup();
        JButton button;

        JPanel namePanel = new JPanel(false);
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));

        namePanel.add(new JLabel("Find what: ", JLabel.LEFT));
        namePanel.add(new JLabel("Replace with: ", JLabel.LEFT));

        fieldPanel.setLayout(new GridLayout(0, 1));

        findCombo = new JComboBox();
        findCombo.setEditable(true);
        findCombo.getAccessibleContext().setAccessibleDescription("Enter text to search for.");
        fieldPanel.add(findCombo);
        replaceCombo = new JComboBox();
        replaceCombo.setEditable(true);
        replaceCombo.getAccessibleContext().setAccessibleDescription("Enter replacement text.");
        fieldPanel.add(replaceCombo);

        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
        textPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0)); // top, left, bottom, and right insets
        textPanel.add(namePanel);
        textPanel.add(fieldPanel);

        ctrlPanel.setLayout(new BoxLayout(ctrlPanel, BoxLayout.X_AXIS));
        ctrlPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // top, left, bottom, and right insets

        ckboxPanel.setLayout(new BoxLayout(ckboxPanel, BoxLayout.Y_AXIS));

        // add checkboxes
        CBcaseSensitive = new JCheckBox("Case sensitive",isCaseSensitive);//,true);
        CBcaseSensitive.getAccessibleContext().setAccessibleDescription("Set Case Sensitive search on or off.");
        CBcaseSensitive.setMnemonic('e');
        CBcaseSensitive.setToolTipText("Turn case sensitive search on or off");
        wrapCB = new JCheckBox("Wrap around", isWrap);
        wrapCB.getAccessibleContext().setAccessibleDescription("Set Wrap around on or off.");
        wrapCB.setMnemonic('W');
        wrapCB.setToolTipText("Turn wrap around on or off");
        matchWordCB = new JCheckBox("Match on entire word",isMatchWord);
        matchWordCB.getAccessibleContext().setAccessibleDescription("Set Match on entire word on or off.");
        matchWordCB.setMnemonic('M');
        matchWordCB.setToolTipText("Turn match on entire word on or off");
        ckboxPanel.add(CBcaseSensitive);
        ckboxPanel.add(matchWordCB);
        ckboxPanel.add(wrapCB);
        ctrlPanel.add(ckboxPanel);

        rbPanel.setLayout(new BoxLayout(rbPanel, BoxLayout.Y_AXIS));
        rbPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // top, left, bottom, and right insets
        // add radio buttons
        forwardRB = new JRadioButton("Find forwards",isForward);
        forwardRB.getAccessibleContext().setAccessibleDescription("Set forward search direction.");
        forwardRB.setMnemonic('o');
        forwardRB.setToolTipText("Set search direction from top to bottom");
        backwardRB = new JRadioButton("Find backwards",!isForward);
        backwardRB.getAccessibleContext().setAccessibleDescription("Set backward search direction.");
        backwardRB.setMnemonic('b');
        backwardRB.setToolTipText("Set search direction from bottom to top");
        bg.add(forwardRB);
        bg.add(backwardRB);
        rbPanel.add(forwardRB);
        rbPanel.add(backwardRB);
        ctrlPanel.add(rbPanel);

        // create buttons
        buttonPanel.setLayout(new javax.swing.plaf.basic.BasicOptionPaneUI.ButtonAreaLayout(true,4));

        findAction = new FindAction();
        buttons = new JButton[4];  // used to tab to button and press enter.. must be a better way!
        button = new JButton(findAction);
        button.setMnemonic('F');
        button.getAccessibleContext().setAccessibleDescription("Press Find to initiate search.");
        button.setToolTipText("Press Find to initiate search");
        buttons[0]=button;
        button.setMargin(new Insets(2, 4, 2, 4));
        buttonPanel.add(button);

        replaceAction = new ReplaceAction();
        button = new JButton(replaceAction);
        button.setMnemonic('R');
        button.getAccessibleContext().setAccessibleDescription("Press Replace to find and replace text.");
        button.setToolTipText("Press Replace to find and replace text");
        buttons[1]=button;
        button.setMargin(new Insets(2, 4, 2, 4));
        buttonPanel.add(button);
        replaceAllAction = new ReplaceAllAction();
        button = new JButton(replaceAllAction);
        button.setMnemonic('A');
        button.getAccessibleContext().setAccessibleDescription("Press Replace All to find and replace all text.");
        buttons[2]=button;
        button.setToolTipText("Press Replace All to find and replace all text");
        button.setMargin(new Insets(2, 4, 2, 4));
        buttonPanel.add(button);
        button = new JButton("Cancel");
        button.setMnemonic('C');
        button.getAccessibleContext().setAccessibleDescription("Press Cancel to close dialog.");
        button.setToolTipText("Press Cancel to close dialog");
        buttons[3]=button;
        button.setMargin(new Insets(2, 4, 2, 4));
        button.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    JComponent comp = (JComponent)e.getSource();
                    saveSettings();

                    // must be close or cancel
                    ((JDialog)comp.getTopLevelAncestor()).setVisible(false);
                    // dispose() will be called in FindReplaceAction
                }
            });
        buttonPanel.add(button);

        add(textPanel);
        add(ctrlPanel);
        add(buttonPanel);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // top, left, bottom, and right insets

        addListeners();

        allActions[0] = new FindReplaceAction();
        allActions[1] = new FindNextAction((FindReplaceAction)allActions[0]);
        allActions[2] = new ReplaceNextAction((FindReplaceAction)allActions[0]);
    }
    /*********************************************************************
    * process key events
    * @param e KeyEvent
    * @return boolean
    */
    public boolean postProcessKeyEvent(KeyEvent e)
    {
        if (e.getID()==KeyEvent.KEY_RELEASED)
        {
            int keyCode = e.getKeyCode();
            if (keyCode==KeyEvent.VK_ESCAPE)
            {
                saveSettings();

                ((JDialog)getTopLevelAncestor()).setVisible(false);
                // dispose() will be called in FindReplaceAction
            }
            if (keyCode==KeyEvent.VK_ENTER) // was enter key
            {
                boolean done =false;
                for (int i=0; i<buttons.length; i++)
                {
                    if (buttons[i].hasFocus())
                    {
                        buttons[i].doClick();
                        //return false;
                        done = true;
                        break;
                    }
                }
                if (!done){
                    // the following needed for accessibility
                    if (CBcaseSensitive.hasFocus())  {
                        CBcaseSensitive.doClick();
                    }
                    else if (wrapCB.hasFocus()) {
                        wrapCB.doClick();
                    }
                    else if (matchWordCB.hasFocus())  {
                        matchWordCB.doClick();
                    }
                    else if (forwardRB.hasFocus())  {
                        forwardRB.doClick();
                    }
                    else if (backwardRB.hasFocus()) {
                        backwardRB.doClick();
                    }
                }
            }
        }
        return false;
    }

    Action[] getActions()
    {
        return allActions;
    }

    void addListeners()
    {
        // add listener to react to enter key-press in find field
        findComboListener = new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    findAction.actionPerformed(e);
                }
            };
        findCombo.getEditor().addActionListener(findComboListener);
        // this is called when user selection has changed.. and also many other times
        // focusLost will call this too (2x but only the first time, why??)
        findCombo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
        	        if ("comboBoxEdited".equals(e.getActionCommand())) {
    		            // User has typed in a string; only possible with an editable combobox
    		            String curSelection = findField.getText();
    		         	if (findCombo.getSelectedIndex()==-1){
    		         		findCombo.insertItemAt(curSelection,0);
    		         		findCombo.setSelectedIndex(0);
    		         	}else{
    		         		for (int i=0; i<findCombo.getItemCount(); i++) {
    		         			String item = (String)findCombo.getItemAt(i);
    		         			if (item.equals(curSelection)) { // exact match
    		         				findCombo.setSelectedIndex(i);
    		         				break;
    		         			}
    		         		}
    		         	}
    		        }
                    // make sure action is enabled
                    findAction.setEnabled(findField.getText().length()!=0);

                    if (findField.getText().indexOf(" ")!=-1)// disable match on word
                    {
                        matchWordCB.setSelected(false);
                        matchWordCB.setEnabled(false);
                    }
                    else
                    {
                        matchWordCB.setEnabled(true);
                    }
                }
            });

        // add listener to react to enter key-press in replace field
        replaceComboListener = new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    replaceAction.actionPerformed(e);
                }
            };
        replaceCombo.getEditor().addActionListener(replaceComboListener);
        replaceCombo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
    				if ("comboBoxEdited".equals(e.getActionCommand())) {
    					// User has typed in a string; only possible with an editable combobox
    					String curSelection = replaceField.getText();
    		         	if (replaceCombo.getSelectedIndex()==-1){
    		         		replaceCombo.insertItemAt(curSelection,0);
    						replaceCombo.setSelectedIndex(0);
    		         	}else{
    		         		for (int i=0; i<replaceCombo.getItemCount(); i++) {
    		         			String item = (String)replaceCombo.getItemAt(i);
    		         			if (item.equals(curSelection)) { // exact match
    		         				replaceCombo.setSelectedIndex(i);
    		         				break;
    		         			}
    		         		}
    		         	}
    				}
                    if (findAction.isEnabled())
                    {
                        // make sure action is enabled
                        replaceAction.setEnabled(replaceField.getText().length()!=0);
                        replaceAllAction.setEnabled(replaceAction.isEnabled());
                    }
                }
            });

        // add key listener to textfield in combo box to enable/disable actions
        for (int i=0; i<findCombo.getComponentCount(); i++)
        {
            Component comp = findCombo.getComponent(i);
            if (comp instanceof JTextField)
            {
                findField = (JTextField)comp;
                findField.setToolTipText("Enter text to search for");
                findField.addKeyListener(new KeyAdapter()
                    {
                        public void keyReleased(KeyEvent e)
                        {
                            findAction.setEnabled(findField.getText().length()!=0);
                            if (findField.getText().length()==0)
                            {
                                replaceAction.setEnabled(false);
                                replaceAllAction.setEnabled(false);
                                matchWordCB.setEnabled(true); // restore match on word capability
                            }
                            else if(replaceField.getText().length()!=0&&replaceCombo.isEnabled())
                            {
                                replaceAction.setEnabled(true);
                                replaceAllAction.setEnabled(true);
                            }

                            if (findField.getText().indexOf(" ")!=-1)// disable match on word
                            {
                                matchWordCB.setSelected(false);
                                matchWordCB.setEnabled(false);
                            }
                            else
                            {
                                matchWordCB.setEnabled(true);
                            }
                        }
    					public void keyTyped(KeyEvent e) {
    						// popup stays open without this
    						if(findCombo.isPopupVisible()){
    							findCombo.hidePopup();
    						}
    					}
                    });
                findField.addFocusListener(new FocusAdapter()
                    {
                        public void focusGained(FocusEvent e) // Invoked when a component gains the keyboard focus
                        {
                            if (findField.getText().length()>0)
                            {
                                findField.setSelectionStart(0);
                                findField.setSelectionEnd(findField.getText().length());
                            }
                        }
                    });
    			findField.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent e) {
						if(e.getClickCount()>0){
							// popup stays open without this
							if(findCombo.isPopupVisible()){
								findCombo.hidePopup();
							}
						}
					}
				});
                break;
            }
        }
        for (int i=0; i<replaceCombo.getComponentCount(); i++)
        {
            Component comp = replaceCombo.getComponent(i);
            if (comp instanceof JTextField)
            {
                replaceField = (JTextField)comp;
                replaceField.setToolTipText("Enter replacement text");

                replaceField.addKeyListener(new KeyAdapter()
                    {
                        public void keyReleased(KeyEvent e)
                        {
                            replaceAction.setEnabled(replaceField.getText().length()!=0);
                            replaceAllAction.setEnabled(replaceField.getText().length()!=0);
                        }
    					public void keyTyped(KeyEvent e) {
    						// popup stays open without this
    						if(replaceCombo.isPopupVisible()){
    							replaceCombo.hidePopup();
    						}
    					}
                    });
                replaceField.addFocusListener(new FocusAdapter()
                    {
                        public void focusGained(FocusEvent e) // Invoked when a component gains the keyboard focus
                        {
                            if (replaceField.getText().length()>0)
                            {
                                replaceField.setSelectionStart(0);
                                replaceField.setSelectionEnd(replaceField.getText().length());
                            }
                        }
                    });
        		replaceField.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent e) {
						if(e.getClickCount()>0){
							// popup stays open without this
							if(replaceCombo.isPopupVisible()){
								replaceCombo.hidePopup();
							}
						}
					}
				});
                break;
            }
        }
    }

    void dereference()
    {
        EventListener listeners[] = findCombo.getListeners(ActionListener.class);

        findCombo.getEditor().removeActionListener(findComboListener);
        replaceCombo.getEditor().removeActionListener(replaceComboListener);
        findComboListener = null;
        replaceComboListener = null;

        for(int i=0; i<listeners.length; i++) {
            findCombo.removeActionListener((ActionListener)listeners[i]);
        }
        findCombo=null;
        listeners =replaceCombo.getListeners(ActionListener.class);
        for(int i=0; i<listeners.length; i++) {
            replaceCombo.removeActionListener((ActionListener)listeners[i]);
        }
        replaceCombo = null;

        forwardRB = null;
        backwardRB = null;
        CBcaseSensitive = null;
        wrapCB = null;
        matchWordCB = null;
        editor = null;
        findAction = null;
        replaceAction = null;
        replaceAllAction = null;

        listeners = findField.getListeners(KeyListener.class);
        for(int i=0; i<listeners.length; i++) {
            findField.removeKeyListener((KeyListener)listeners[i]);
        }
        listeners = findField.getListeners(FocusListener.class);
        for(int i=0; i<listeners.length; i++) {
            findField.removeFocusListener((FocusListener)listeners[i]);
        }
        listeners = findField.getListeners(MouseListener.class);
        for(int i=0; i<listeners.length; i++) {
            findField.removeMouseListener((MouseListener)listeners[i]);
        }
        findField = null;

        listeners = replaceField.getListeners(KeyListener.class);
        for(int i=0; i<listeners.length; i++)  {
            replaceField.removeKeyListener((KeyListener)listeners[i]);
        }
        listeners = replaceField.getListeners(FocusListener.class);
        for(int i=0; i<listeners.length; i++) {
            replaceField.removeFocusListener((FocusListener)listeners[i]);
        }
        listeners = replaceField.getListeners(MouseListener.class);
        for(int i=0; i<listeners.length; i++) {
        	replaceField.removeMouseListener((MouseListener)listeners[i]);
        }
        replaceField = null;

        for (int i=0; i<buttons.length; i++)
        {
            listeners = buttons[i].getListeners(ActionListener.class);
            for(int ii=0; ii<listeners.length; ii++) {
                buttons[i].removeActionListener((ActionListener)listeners[ii]);
                listeners[ii]=null;
            }
            buttons[i] = null;
        }

        buttons = null;
        removeAll();
        setLayout(null);
        for (int i=0; i<allActions.length; i++)
        {
            if (allActions[i] instanceof XMLDereferencible){
                ((XMLDereferencible)allActions[i]).dereference();
            }
            allActions[i] = null;
        }
        allActions=null;
    }

    // this is used as a default search value, if there is a selection it is the starting
    // point.  'find next' will always use a selection if there is one.  'find/replace' will
    // open a dialog with the selected text in the find field
    private void setSelectedAsDefault()
    {
        // does user have a selection?
        String selText = editor.getSelectedText();
        if (selText!=null)
        {
            ComboBoxEditor cbe;
            // can't include newlines in content
            if (selText.indexOf(XMLEditor.NEWLINE_STR)==-1) {
                boolean addit=true;
                cbe = findCombo.getEditor();
                cbe.setItem(selText);
                // what if it is already in the list?
                for (int i=0; i<findCombo.getItemCount(); i++)
                {
                    String item = (String)findCombo.getItemAt(i);
                    if (item.equals(selText)) { // exact match
                        //return;
                        addit=false;
                        break;
                    }
                    // if case insensitive, don't add case differences to the list
                    if (!CBcaseSensitive.isSelected()&& item.equalsIgnoreCase(selText)) {
                        //return;
                        addit=false;
                        break;
                    }
                }
                if(addit){
                    findCombo.insertItemAt(selText,0);
                    findCombo.setSelectedIndex(0);
                }
            }
        }
    }

    private void saveSettings()
    {
        String txt;
        findComboIndex=findCombo.getSelectedIndex();
        replaceComboIndex=replaceCombo.getSelectedIndex();

        // not using static combo boxes because of listeners and gui setup each time
        // hang onto any text for any other instantiation
        txt = findField.getText();
        if (txt.length()>0 && !findVct.contains(txt))
        {
            findComboIndex=findVct.size();
            findVct.insertElementAt(txt,0);
        }

        txt = replaceField.getText();
        if (txt.length()>0 && !replaceVct.contains(txt))
        {
            replaceComboIndex=replaceVct.size();
            replaceVct.insertElementAt(txt,0);
        }
        isCaseSensitive = CBcaseSensitive.isSelected();
        isWrap = wrapCB.isSelected();
        isMatchWord = matchWordCB.isSelected();
        isForward = forwardRB.isSelected();
    }

    // this is called each time the Dialog is instantiated
    void enableReplace(boolean b)
    {
        ComboBoxEditor cbe;
        String selText;

        // restore any previous settings from another instantiation to maintain user's settings
        // can't use static findrepmgr because of listeners instantiated each time
        CBcaseSensitive.setSelected(isCaseSensitive);
        wrapCB.setSelected(isWrap);
        matchWordCB.setSelected(isMatchWord);
        forwardRB.setSelected(isForward);
        backwardRB.setSelected(!isForward);

        replaceCombo.removeAllItems();
        for (int i=replaceVct.size(); i>0; i--) {
            replaceCombo.addItem(replaceVct.elementAt(i-1));
        }

        findCombo.removeAllItems();
        for (int i=findVct.size(); i>0; i--){
            findCombo.addItem(findVct.elementAt(i-1));
        }

        if (replaceComboIndex!=-1)
        {
            replaceCombo.setSelectedIndex(replaceComboIndex);
            replaceField.setCaretPosition(0); // default is all find and replace text is selected.. weird
        }
        if (findComboIndex!=-1)
        {
            findCombo.setSelectedIndex(findComboIndex);
        }

        replaceCombo.setEnabled(b);
        matchWordCB.setEnabled(true);

        if (!b) // if replace should not be enabled, make sure replace actions are disabled
        {
            replaceAction.setEnabled(b);
            replaceAllAction.setEnabled(b);
        }
        else
        {
            // enable actions if text exists in the replaceCombo
            if (replaceField.getText().length()>0)
            {
                replaceAction.setEnabled(b);
                replaceAllAction.setEnabled(b);
            }
        }

        // if there is a selection, start with that
        cbe = findCombo.getEditor();
        selText = editor.getSelectedText();
        if (selText!=null)
        {
            //jre1.3.0 has bug where terminating new line is sometimes included in the selection
            // even if user didn't select it.. if there is a nl on the end, strip and use text
            /* jtest flags indexOf()
            Disallows using 'String.indexOf ()' with 'String.substring ()'.
            Note: If the algorithm implemented by the method is not String parsing, then
            StringTokenizer cannot be used and this rule's error message should be ignored.
            */
            int nlId = selText.indexOf(XMLEditor.NEWLINE_STR);
            if (nlId == selText.length()-1)  // terminating new line included
            {
//                selText = selText.substring(0,nlId);
                selText = XMLGenerator.getSubString(selText,0,nlId);
                nlId=-1;
            }
            // selected text must not include new lines if it does ignore all text
            if (nlId == -1)
            {
                cbe.setItem(selText);
                cbe.selectAll();
            }
            else // disable match on word
            {
                matchWordCB.setSelected(false);
                matchWordCB.setEnabled(false);
            }

            if (selText.indexOf(" ")!=-1) // disable match on word
            {
                matchWordCB.setSelected(false);
                matchWordCB.setEnabled(false);
            }
        }

        selText = (String)findCombo.getSelectedItem(); // does selected item have spaces?
        if (selText!=null && selText.indexOf(" ")!=-1)// disable match on word
        {
            matchWordCB.setSelected(false);
            matchWordCB.setEnabled(false);
        }

        // enable the find action if there is text in the find field
        findAction.setEnabled(findField.getText().length()!=0);
    }

    // called by findAction or findNextAction
    private void findString()
    {
        if (findField.getText().length()==0)
        {
        	UIManager.getLookAndFeel().provideErrorFeedback(null);
            showAccessibleDialog("A Search string must be specified.", JOptionPane.ERROR_MESSAGE);
            //return;
        }else{
            if (!findString(true))
            {
                String msg = "string";
                // else put up a message dialog
                UIManager.getLookAndFeel().provideErrorFeedback(null);
                if (matchWordCB.isSelected()) {
                    msg = "word";
                }

                showAccessibleDialog("Cannot find "+msg+": '"+findField.getText()+"'",JOptionPane.WARNING_MESSAGE);
            }

            updateComboBox(findCombo,findField, findVct);
        }
    }

    /* replace the next occurrence of the string */
    private void replaceString()
    {
        if (findField.getText().length()==0)
        {
        	UIManager.getLookAndFeel().provideErrorFeedback(null);
            showAccessibleDialog("A Search string must be specified.", JOptionPane.ERROR_MESSAGE);
            //return;
        }else{
            if (replaceField.getText().length()==0)
            {
            	UIManager.getLookAndFeel().provideErrorFeedback(null);
                showAccessibleDialog("A Replacement string must be specified.", JOptionPane.ERROR_MESSAGE);
                //return;
            }else{
                if (findString(false))
                {
                    editor.replaceSelection(replaceField.getText());
                }
                else
                {
                    String msg = "string";
                    // else put up a message dialog
                    UIManager.getLookAndFeel().provideErrorFeedback(null);
                    if (matchWordCB.isSelected()) {
                        msg = "word";
                    }

                    showAccessibleDialog("Cannot find "+msg+": '"+findField.getText()+"'", JOptionPane.WARNING_MESSAGE);
                }

                updateComboBox(replaceCombo,replaceField, replaceVct);
            }
        }
    }

    private void showAccessibleDialog(String msg, int messageType)
    {
        String msgs[] = {msg};
        String accdesc[] = {"Press OK to close dialog." };

        String level = "error";
        if (messageType == JOptionPane.WARNING_MESSAGE) {
            level = "warning";
        }

        XMLEditor.showAccessibleDialog(editor.getParent(),//editor, jre 1.4.0 positions dialog incorrectly
            "Find/Replace",
            messageType, JOptionPane.YES_OPTION,
            "Find/Replace "+level, msgs, accdesc);
    }

    private void updateComboBox(JComboBox cb, JTextField fld, Vector<String> vct)
    {
    	//moved to diff listener
        String curSelection = fld.getText();//(String)cb.getSelectedItem();
        if (curSelection==null || curSelection.length()==0) {
            //return;
        }else{
            if (cb.getSelectedIndex()==-1)  // selection is not currently part of the list
            {
                cb.insertItemAt(curSelection,0);
                cb.setSelectedIndex(0);
                if (!vct.contains(curSelection))
                {
                    vct.insertElementAt(curSelection,0);
                }
            }
            else
            {
                // if user types value into control and hits enter it will not be the selected
                // value when find/repl is re-entered unless the following is done
                String txt = fld.getText();
                for (int i=0; i<cb.getItemCount(); i++)
                {
                    String item = (String)cb.getItemAt(i);
                    if (item.equals(txt))  // exact match
                    {
                        cb.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }
    }

    /* find the next occurrence of the string
    * @param getNext if true, if word is found at current caret position, move past it
    */
    private boolean findString(boolean getNext)
    {
        boolean found;
        if (matchWordCB.isSelected()) // must match the word
        {
            found = findWord(getNext);
        }
        else // find text
        {
            found = findText(getNext);
        }
        return found;
    }

    /* find the next occurrence of the text
    * @param getNext if true, if text is found at current caret position, move past it
    */
    private boolean findText(boolean getNext)
    {
        boolean found=false;
        Document doc = editor.getDocument();
        int startPos=0;
        int index=-1;
        String searchStr = findField.getText();

        try
        {
            // look from start forward
            if (forwardRB.isSelected())
            {
                String theText;
                if (getNext) { // move past current position
                    // from end of selection position
                    startPos= Math.min(editor.getSelectionEnd(),editor.getDocument().getLength());
                }
                else {  // needed for replacement
                    startPos=editor.getSelectionStart();
                }

                // this will get the text without \r\n problems
                theText = doc.getText(startPos,doc.getLength()-startPos);
                if (!CBcaseSensitive.isSelected())
                {
                    theText = theText.toLowerCase();
                    searchStr = searchStr.toLowerCase();
                }

                // look for the word
                index = theText.indexOf(searchStr);
                // if not found, start at 0
                if (index==-1)
                {
                    // if not found, and wrap is specified, start at the beginning
                    if (wrapCB.isSelected())
                    {
                        startPos=0;
                        theText = doc.getText(startPos,doc.getLength()-startPos);
                        if (!CBcaseSensitive.isSelected()) {
                            theText = theText.toLowerCase();
                        }
                        UIManager.getLookAndFeel().provideErrorFeedback(null);
                        index = theText.indexOf(searchStr);
                    }
                }

                if (index!=-1)
                {
                    // select the word
                    editor.setCaretPosition(index+startPos);
                    editor.moveCaretPosition(index+startPos+searchStr.length());
                    //return true;
                    found=true;
                }
            }
            else  // look from cursor backward
            {
                String theText;
                if (getNext) { // move past current position
                    // from start of selection position
                    startPos=editor.getSelectionStart();
                }
                else {
                    startPos=Math.min(editor.getSelectionEnd(),editor.getDocument().getLength());
                }

                // this will get the text without \r\n problems
                theText = doc.getText(0,startPos);
                if (!CBcaseSensitive.isSelected())
                {
                    theText = theText.toLowerCase();
                    searchStr = searchStr.toLowerCase();
                }

                // look for the word
                index = theText.lastIndexOf(searchStr);
                if (index==-1)
                {
                    // if not found, and wrap is specified, get alltext
                    if (wrapCB.isSelected())
                    {
                        theText = doc.getText(0,doc.getLength());
                        if (!CBcaseSensitive.isSelected()) {
                            theText = theText.toLowerCase();
                        }
                        UIManager.getLookAndFeel().provideErrorFeedback(null);
                        index = theText.lastIndexOf(searchStr);
                    }
                }

                if (index !=-1)
                {
                    // select the word
                    editor.setCaretPosition(index);
                    editor.moveCaretPosition(index+searchStr.length());
                    //return true;
                    found = true;
                }
            }
        } catch (Exception ioe)
        {
            ioe.printStackTrace(System.err);
        }
        return found;
    }

    /* find the next occurrence of the string
    * @param getNext if true, if word is found at current caret position, move past it
    */
    private boolean findWord(boolean getNext)
    {
        boolean found = false;
        Document doc = editor.getDocument();
        int startPos=0;
        int index=-1;
        String searchStr = findField.getText();

        try
        {
            // this will get the text without \r\n problems
            String theText = doc.getText(0,doc.getLength());
            if (!CBcaseSensitive.isSelected())
            {
                theText = theText.toLowerCase();
                searchStr = searchStr.toLowerCase();
            }

            // look from start forward
            if (forwardRB.isSelected())
            {
                if (getNext) { // move past current position
                    // from end of selection position
                    startPos= Math.min(editor.getSelectionEnd(),doc.getLength());
                }
                else {  // needed for replacement
                    startPos=editor.getSelectionStart();
                }

                // start at the cursor and look forward
                index = findWord(startPos, theText, searchStr);

                // if not found, start at 0
                if (index==-1)
                {
                    // if not found, and wrap is specified, start at the beginning
                    if (wrapCB.isSelected())
                    {
                        startPos=0;
                        UIManager.getLookAndFeel().provideErrorFeedback(null);
                        index = findWord(startPos, theText, searchStr);
                    }
                }

                if (index!=-1)
                {
                    // select the word
                    editor.setCaretPosition(index);
                    editor.moveCaretPosition(index+searchStr.length());
                    //return true;
                    found = true;
                }
            }
            else  // look from cursor backward
            {
                if (getNext) { // move past current position
                    // from start of selection position
                    startPos=editor.getSelectionStart();
                }
                else {
                    startPos=Math.min(editor.getSelectionEnd(),doc.getLength());
                }

                if (startPos==doc.getLength()){
                    startPos--;
                }

                // look for the word
                index = findWord(startPos, theText, searchStr);
                if (index==-1)
                {
                    // if not found, and wrap is specified, get alltext
                    if (wrapCB.isSelected())
                    {
                        startPos = doc.getLength()-1;

                        UIManager.getLookAndFeel().provideErrorFeedback(null);
                        //index = theText.lastIndexOf(searchStr);
                        index = findWord(startPos, theText, searchStr);
                    }
                }

                if (index !=-1)
                {
                    // select the word
                    editor.setCaretPosition(index);
                    editor.moveCaretPosition(index+searchStr.length());
                    //return true;
                    found = true;
                }
            }
        } catch (Exception ioe)
        {
            ioe.printStackTrace(System.err);
        }
        return found;
    }

    private int findWord(int startPos, String theText, String searchStr)
    {
        int start = 0;
        int index = -1;
        int end = 0;

        // get all words in the document
        java.text.BreakIterator wbi = java.text.BreakIterator.getWordInstance();
        wbi.setText(theText);
        /*
        (The iterator returned by BreakIterator.getWordInstance() is unique in that the break positions
        it returns don't represent both the start and end of the thing being iterated over. That is,
        a sentence-break iterator returns breaks that each represent the end of one sentence and the
        beginning of the next. With the word-break iterator, the characters between two boundaries might
        be a word, or they might be the punctuation or whitespace between two words. The above code uses
        a simple heuristic to determine which boundary is the beginning of a word: If the characters
        between this boundary and the next boundary include at least one letter (this can be an
        alphabetical letter, a CJK ideograph, a Hangul syllable, a Kana character, etc.), then the text
        between this boundary and the next is a word; otherwise, it's the material between words.)
        */
        // look from start forward
        if (forwardRB.isSelected())
        {
            end = wbi.following(startPos); // do this to get correct start value
            start = wbi.previous();
            end = wbi.next(); // set up iterator to correct location

            while (end != BreakIterator.DONE)
            {
//                String word = theText.substring(start,end);
                String word = XMLGenerator.getSubString(theText,start,end);

                // look for the word
                if (Character.isLetterOrDigit(word.charAt(0)))
                {
                    if (word.equals(searchStr))
                    {
                        index = start;
                        break;
                    }
                }
                start = end;
                end = wbi.next();
            }
        }
        else
        {
            start = wbi.preceding(startPos);
            if (start!=BreakIterator.DONE){ // not already at beginning
                end = wbi.next();
                start = wbi.previous();

                while (start != BreakIterator.DONE)
                {
//                    String word = theText.substring(start,end);
                    String word = XMLGenerator.getSubString(theText,start,end);
                    // look for the word
                    if (Character.isLetterOrDigit(word.charAt(0)))
                    {
                        if (word.equals(searchStr))
                        {
                            index = start;
                            break;
                        }
                    }
                    end = start;
                    start = wbi.previous();
                }
            }
        }

        return index;
    }

    /*********************************************************************
    * This is used to find all occurences of a string for replace all FB52443:4A247A
    */
    private Vector findAllStrings()
    {
        Document doc = editor.getDocument();
        int index=0;
        String searchStr = findField.getText();
        Vector<Integer> allMatchVct = new Vector<Integer>(1);

        try
        {
            // this will get the text without \r\n problems
            String theText = doc.getText(0,doc.getLength());
            if (!CBcaseSensitive.isSelected())
            {
                theText = theText.toLowerCase();
                searchStr = searchStr.toLowerCase();
            }

            // start at the beginning and look at all
            // look for the text
            if (matchWordCB.isSelected())
            {
                int start;
                // get all words in the document
                java.text.BreakIterator wbi = java.text.BreakIterator.getWordInstance();
                wbi.setText(theText);

                start = wbi.first();
                for (int end = wbi.next();  end != BreakIterator.DONE; start = end, end = wbi.next())
                {
//                    String word = theText.substring(start,end);
                    String word = XMLGenerator.getSubString(theText,start,end);
                    if (word.equals(searchStr))
                    {
                        // save the location
                        allMatchVct.addElement(new Integer(start));
                    }
                }
            }
            else
            {
                while (index!=-1)
                {
                    /* jtest flags indexOf()
                    Disallows using 'String.indexOf ()' with 'String.substring ()'.
                    Note: If the algorithm implemented by the method is not String parsing, then
                    StringTokenizer cannot be used and this rule's error message should be ignored.
                    */
                    index = theText.indexOf(searchStr, index);
                    if (index!=-1)
                    {
                        // save the location
                        allMatchVct.addElement(new Integer(index));
                        index++;
                    }
                }
            }
        } catch (Exception ioe)
        {
            ioe.printStackTrace(System.err);
        }
        return allMatchVct;
    }

    /* replace the all occurrences of the string */
    private void replaceAll()
    {
        int startLocation;
        int diff = 0;
        String searchStr;
        Vector allMatchesVct;

        if (findField.getText().length()==0)
        {
        	UIManager.getLookAndFeel().provideErrorFeedback(null);
            //JOptionPane.showMessageDialog(editor, "A Search string must be specified.",
            //      "Find/Replace", JOptionPane.ERROR_MESSAGE);
            showAccessibleDialog("A Search string must be specified.", JOptionPane.ERROR_MESSAGE);
            //return;
        }else{
            if (replaceField.getText().length()==0)
            {
            	UIManager.getLookAndFeel().provideErrorFeedback(null);
                //JOptionPane.showMessageDialog(editor, "A Replacement string must be specified.",
                //      "Find/Replace", JOptionPane.ERROR_MESSAGE);

                showAccessibleDialog("A Replacement string must be specified.", JOptionPane.ERROR_MESSAGE);
                //return;
            }else{
                startLocation = editor.getSelectionStart();
                searchStr = findField.getText();

                // get location of each occurence, save in vct and then replace each one
                allMatchesVct = findAllStrings(); // FB52443:4A247A
                for (int x=allMatchesVct.size(); x>0; x--)
                {
                    int index = ((Integer)allMatchesVct.elementAt(x-1)).intValue();
                    editor.setCaretPosition(index);
                    editor.moveCaretPosition(index+searchStr.length());

                    editor.replaceSelection(replaceField.getText());
                    if (index<startLocation) {
                        diff+=(replaceField.getText().length()-searchStr.length());
                    }
                }

                if (allMatchesVct.size()==0)
                {
                    String msg = "string";
                    // else put up a message dialog
                    UIManager.getLookAndFeel().provideErrorFeedback(null);
                    if (matchWordCB.isSelected()) {
                        msg = "word";
                    }

                    showAccessibleDialog("Cannot find "+msg+": '"+findField.getText()+"'", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    // move caret to original start location
                    editor.setCaretPosition(startLocation+diff);
                }

                updateComboBox(replaceCombo,replaceField, replaceVct);
            }
        }
    }

    /*********************************************************************
    * This controls find next action
    */
    private class FindNextAction extends AbstractAction implements XMLDereferencible
    {
    	private static final long serialVersionUID = 1L;
    	private FindReplaceAction findRepAct;
        FindNextAction(FindReplaceAction a) {
            super(FIND_NEXT_ACTION);
            findRepAct=a;
        }

        public void dereference()
        {
            findRepAct = null;
        }

        public void actionPerformed(ActionEvent e)
        {
            setSelectedAsDefault();

            if (findField.getText().length()==0) {
                findRepAct.actionPerformed(null);
            }
            else {
                findString();
            }
        }
    }
    /*********************************************************************
    * This controls replace next action
    */
    private class ReplaceNextAction extends AbstractAction implements XMLDereferencible
    {
    	private static final long serialVersionUID = 1L;
    	private FindReplaceAction findRepAct;
        ReplaceNextAction(FindReplaceAction a){
            super(REPLACE_NEXT_ACTION);
            findRepAct=a;
        }

        public void dereference()
        {
            findRepAct = null;
        }

        public void actionPerformed(ActionEvent e)
        {
            if (findField.getText().length()==0 || replaceField.getText().length()==0) {
                findRepAct.actionPerformed(null);
            }
            else
            {
                editor.getUndoEditMgr().setStartReplaceEdit("replacement");
                replaceString();
                editor.getUndoEditMgr().setEndReplaceEdit();
            }
        }
    }
    /*********************************************************************
    * This controls find/replace dialog
    */
    private class FindReplaceAction extends AbstractAction
    {
    	private static final long serialVersionUID = 1L;
    	FindReplaceAction() {
            super(FIND_REPL_ACTION);
        }

        public void actionPerformed(ActionEvent e)
        {
            // this causes this to be executed on the event dispatch thread
            // after actionPerformed returns
            SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        XMLFindDialog tmp = new XMLFindDialog(editor,XMLFindReplaceMgr.this,!editor.isEditable());
                        tmp.setVisible(true);
                        // dialog was modal, clean up now
                        tmp.dispose();
                        tmp.dereference();
                        tmp = null;
                        editor.setFocusInEditor();  // applet does not return focus to the editor
                    }
                });
        }
    }

    /*********************************************************************
    * This is find string action
    */
    private class FindAction extends AbstractAction
    {
    	private static final long serialVersionUID = 1L;
    	FindAction() {
            super("Find");
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent e)
        {
            JComponent comp = (JComponent)e.getSource();
            // close the find dialog
            ((JDialog)comp.getTopLevelAncestor()).setVisible(false);
            // dispose() called by FindReplaceAction after show() returns((JDialog)comp.getTopLevelAncestor()).dispose();

            findString();

            saveSettings();
        }
    }

    /*********************************************************************
    * This is replace string action
    */
    private class ReplaceAction extends AbstractAction
    {
    	private static final long serialVersionUID = 1L;
    	ReplaceAction(){
            super("Replace");
            setEnabled(false);
        }

        /*********************************************************************
        * process  events
        * @param e ActionEvent
        */
        public void actionPerformed(ActionEvent e)
        {
            JComponent comp = (JComponent)e.getSource();
            // must be close or cancel
            ((JDialog)comp.getTopLevelAncestor()).setVisible(false);
            // dispose() called by FindReplaceAction after show() returns((JDialog)comp.getTopLevelAncestor()).dispose();

            editor.getUndoEditMgr().setStartReplaceEdit("replacement");
            replaceString();
            editor.getUndoEditMgr().setEndReplaceEdit();

            saveSettings();
        }
    }

    /*********************************************************************
    * This is replace all strings action
    */
    private class ReplaceAllAction extends AbstractAction
    {
    	private static final long serialVersionUID = 1L;
    	ReplaceAllAction(){
            super("Replace All");
            setEnabled(false);
        }

        /*********************************************************************
        * process  events
        * @param e ActionEvent
        */
        public void actionPerformed(ActionEvent e)
        {
            JComponent comp = (JComponent)e.getSource();
            // must be close or cancel
            ((JDialog)comp.getTopLevelAncestor()).setVisible(false);
            // dispose() called by FindReplaceAction after show() returns((JDialog)comp.getTopLevelAncestor()).dispose();

            editor.getUndoEditMgr().setStartReplaceEdit("replace All");
            replaceAll();
            editor.getUndoEditMgr().setEndReplaceEdit();

            saveSettings();
        }
    }
}
