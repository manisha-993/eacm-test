//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.ui;

import java.awt.*;
import java.awt.event.*;

import java.lang.reflect.InvocationTargetException;
import java.util.EventListener;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EANFoundation;
import COM.ibm.eannounce.objects.EANObject;
import COM.ibm.eannounce.objects.EntityItem;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.editor.AttrCellEditor;
import com.ibm.eacm.objects.*;
import com.ibm.eacm.preference.BehaviorPref;
import com.ibm.eacm.preference.ColorPref;

/**************
 *
 * User dialogs
 * @author Wendy Stimpson
 */
//$Log: UI.java,v $
//Revision 1.6  2014/04/17 17:24:36  wendy
//add scroll to long messages
//
//Revision 1.5  2013/11/20 21:09:34  wendy
//check for (ok) before showing abort/retry dialog
//
//Revision 1.4  2013/10/10 19:52:01  wendy
//add more info to error msg
//
//Revision 1.3  2013/08/14 16:40:34  wendy
//add cancelpaste dialog
//
//Revision 1.2  2013/08/06 19:16:53  wendy
//log error msgs
//
//Revision 1.1  2012/09/27 19:39:11  wendy
//Initial code
//
public class UI implements EACMGlobals{
    private static final String CHOICE1 = "choice1";
    private static final String CHOICE2 = "choice2";
    private static final String CHOICE3 = "choice3";
    private static final String NOCHOICE = "none";
    
	private UI() {}

    /**
     * Show mw error and prompt user for response
     *
     * @param parentComponent
     * @param exc
     * @return
     */
    public static int showMWExcPrompt(Component parentComponent, Exception exc)
    {
    	// does this message end with an (ok)? if so, do not prompt for abort/retry
    	String errmsg = exc.toString();
    	int r = IGNORE;
    	int okindex = errmsg.lastIndexOf(OK_ERROR);
    	if(okindex >0){
    		errmsg = Routines.replace(errmsg, OK_ERROR, "");
    		showErrorMessage(parentComponent,errmsg);
    	}else{
    		String[] options = {"abort","retry","ignore"};
    		String accdesc[] = { "abort-acc", "retry-acc","ignore-acc" };
    		r = showAccessibleDialog(parentComponent,
    				"mw.req.err-title", //title
    				JOptionPane.QUESTION_MESSAGE, //messageType
    				JOptionPane.YES_NO_CANCEL_OPTION, // optiontype
    				"MWerrDialog-acc", //accDialogDesc
    				//msgMRE = Middleware Request Error
    				Routines.convertToArray(Utils.getResource("msgMRE",errmsg),RETURN), //msgs
    				options,  //button labels
    				accdesc); //accButtonDescs

    		options = null;
    		accdesc = null;
    		Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"MiddlewareError --> " + errmsg + " {User selected (" + r + ")}",exc);

    		if (r==ABORT_BUTTON){  // ABORT
    			EACM.getEACM().exit();
    		}else if (r==RETRY){ // RETRY
    			Routines.sleep(2000);//  should this be a prop?
    		}
    	}
    	
    	return r;
    }

    /**
     * show accessible dialog for confirmation with ok, cancel buttons with radio buttons for other options
     * @param parentComponent
     * @param userMsg
     * @param txt
     * @param numcols
     * @return
     */
    public static int showConfirmYesNoAllCancel(Component parentComponent,String userMsg) {
    	int r = CANCEL_BUTTON;
    	String command = showConfirm(parentComponent,userMsg, "yes", "no", "all");
    	if (CHOICE1.equals(command)){
    		r = YES_BUTTON;
    	}else if (CHOICE2.equals(command)){
    		r = NO_BUTTON;
    	}else if (CHOICE3.equals(command)){
    		r = ALL_BUTTON;
    	}
    	return r;
    }
    public static int showConfirmAllNoneChooseCancel(Component parentComponent,String userMsg) {
    	int r = CANCEL_BUTTON;
    	String command = showConfirm(parentComponent,userMsg, "save_all", "save_none", "save_choose");
    	if (CHOICE1.equals(command)){
    		r = ALL_BUTTON;
    	}else if (CHOICE2.equals(command)){
    		r = NONE_BUTTON;
    	}else if (CHOICE3.equals(command)){
    		r = CHOOSE_BUTTON;
    	}
    	return r;
    }
    private static String showConfirm(Component parentComponent,String userMsg, String txt1,String txt2, String txt3) {
    	String[] options = {"ok","cancel"};
    	String accdesc[] = { "ok-acc", "cancel-confirm-acc"};
    	String command = NOCHOICE;
    	ButtonGroup group = new ButtonGroup();

    	Object obj[] = new Object[] {userMsg,createChoicesBox(group, txt1,txt2, txt3)};
    	int r= showAccessibleDialog(parentComponent,//Component parentComponent
    			"confirmation-title", //title
    			JOptionPane.QUESTION_MESSAGE, //messageType
    			JOptionPane.OK_CANCEL_OPTION, // optiontype
    			"confirmation-acc", //accDialogDesc
    			obj, //msgs
    			options,  //button labels
    			accdesc); //accButtonDescs

    	options = null;
    	accdesc = null;

    	if(r==JOptionPane.OK_OPTION){
    		// get selection from the group
    	     command = group.getSelection().getActionCommand();
    	}
    	return command;
    }

    private static JPanel createChoicesBox(ButtonGroup group, String txt1,String txt2, String txt3) {
        int numButtons = 3;
        JRadioButton[] radioButtons = new JRadioButton[numButtons];

        radioButtons[0] = new JRadioButton(Utils.getResource(txt1));
        radioButtons[0].setActionCommand(CHOICE1);
        radioButtons[0].setMnemonic(Utils.getMnemonic(txt1));

        radioButtons[1] = new JRadioButton(Utils.getResource(txt2));
        radioButtons[1].setActionCommand(CHOICE2);
        radioButtons[1].setMnemonic(Utils.getMnemonic(txt2));

        radioButtons[2] = new JRadioButton(Utils.getResource(txt3));
        radioButtons[2].setActionCommand(CHOICE3);
        radioButtons[2].setMnemonic(Utils.getMnemonic(txt3));

        for (int i = 0; i < numButtons; i++) {
            group.add(radioButtons[i]);
        }
        radioButtons[0].setSelected(true);

        JPanel box = new JPanel();
       // JLabel label = new JLabel(description);

        box.setLayout(new BoxLayout(box, BoxLayout.PAGE_AXIS));
       // box.add(label);

        for (int i = 0; i < numButtons; i++) {
            box.add(radioButtons[i]);
        }

        JPanel pane = new JPanel(new BorderLayout());
        pane.add(box, BorderLayout.PAGE_START);

        return pane;
    }

    public static int showErrorCancelPaste(Component parentComponent,String userMsg) {
    	String[] options = {"ok","cancelpaste"};
    	String accdesc[] = { "ok-close-acc","cancel-confirm-acc" };

    	int r = showAccessibleDialog(parentComponent,//Component parentComponent
    			"err-title", //title
    			JOptionPane.ERROR_MESSAGE, //messageType
    			JOptionPane.OK_CANCEL_OPTION, // optiontype
    			"err-acc", //accDialogDesc
    			Routines.convertToArray(userMsg,RETURN), //msgs
    			options,  //button labels
    			accdesc); //accButtonDescs

    	options = null;
    	accdesc = null;

    	return r;
    }
    
    public static int showConfirmOkCancel(Component parentComponent,String userMsg) {
    	String[] options = {"ok","cancel"};
    	String accdesc[] = { "ok-close-acc","cancel-confirm-acc" };
    	int r = showConfirm(parentComponent,userMsg, options, accdesc,JOptionPane.OK_CANCEL_OPTION);
     	options = null;
    	accdesc = null;

    	return r;
    }
    public static int showConfirmYesNo(Component parentComponent,String userMsg) {
    	String[] options = {"yes","no"};
    	String accdesc[] = { "yes-acc","no-acc" };
    	int r = showConfirm(parentComponent,userMsg, options, accdesc,JOptionPane.YES_NO_OPTION);
     	options = null;
    	accdesc = null;

    	return r;
    }
    public static int showConfirmYesNoCancel(Component parentComponent,String userMsg) {
    	String[] options = {"yes","no","cancel"};
    	String accdesc[] = { "yes-acc","no-acc","cancel-confirm-acc" };
    	int r = showConfirm(parentComponent,userMsg, options, accdesc,JOptionPane.YES_NO_CANCEL_OPTION);
     	options = null;
    	accdesc = null;

    	return r;
    }
/*
    public static final int         YES_NO_OPTION = 0;
    public static final int         YES_NO_CANCEL_OPTION = 1;
    public static final int         OK_CANCEL_OPTION = 2;
 */

    public static int showConfirm(Component parentComponent,String userMsg, String[] options,
    		String[] accdesc, int optiontype) {
    	int r = showAccessibleDialog(parentComponent,//Component parentComponent
    			"confirmation-title", //title
    			JOptionPane.QUESTION_MESSAGE, //messageType
    			optiontype, // optiontype
    			"confirmation-acc", //accDialogDesc
    			Routines.convertToArray(userMsg,RETURN), //msgs
    			options,  //button labels
    			accdesc); //accButtonDescs

    	options = null;
    	accdesc = null;

    	return r;
    }
    /**********
     *
     * This class is used to put the JOptionPane on the event thread
     *
     */
    private static class ShowAccessibleDialog implements Runnable{
    	private int results=JOptionPane.CLOSED_OPTION;
    	private Component parentComponent;
    	private String title;
        private int messageType;
        private int optionType;
        private String accDialogDesc;
        private Object []msgs;
        private String[] buttonLabels;
        private String[] accButtonDesc;
        private boolean useTTF = false;

        ShowAccessibleDialog(Component p, String t,	int mtype, int otype, String desc, Object[] msg,
        		String []labels, String[] bdescs, boolean ttf){
        	parentComponent = p;
        	title=t;
        	messageType= mtype;
        	optionType=otype;
        	accDialogDesc = desc;
        	msgs = msg;
        	buttonLabels =labels;
        	accButtonDesc = bdescs;
        	useTTF = ttf;
        }

    	public void run() {
    		results = showAccessibleDialogOnEvtThrd(parentComponent, title,
    	    		messageType, optionType, accDialogDesc, msgs,
    	    		buttonLabels, accButtonDesc, useTTF);
    	}
    	int getResults() {return results;}
    	void dereference(){
    	  	parentComponent = null;
        	title=null;
        	accDialogDesc = null;
        	msgs = null;
        	buttonLabels =null;
        	accButtonDesc = null;
    	}
    }
    /*********************************************************************
     * This is used with JOptionPane Dialogs.  JOptionPane static showXXX are
     * not Accessibile.  Buttons do not have mnemonics or descriptions.
     * @param parentComponent
     * @param title
     * @param messageType
     * @param optionType
     * @param accDialogDesc
     * @param msgs
     * @param buttonLabels
     * @param accButtonDesc
     * @return
     */
    public static int showAccessibleDialog(Component parentComponent, String title,
    		int messageType, int optionType, String accDialogDesc, Object []msgs,
    		String[] buttonLabels, String[] accButtonDesc){
    	return showAccessibleDialog(parentComponent, title,
        		messageType, optionType, accDialogDesc, msgs,
        		buttonLabels, accButtonDesc, false);
    }

    /**
     *  This is used with JOptionPane Dialogs.  JOptionPane static showXXX are
     * not Accessibile.  Buttons do not have mnemonics or descriptions.
     * @param parentComponent
     * @param title
     * @param messageType
     * @param optionType
     * @param accDialogDesc
     * @param msgs
     * @param buttonLabels
     * @param accButtonDesc
     * @param ttf
     * @return
     */
    public static int showAccessibleDialog(Component parentComponent, String title,
    		int messageType, int optionType, String accDialogDesc, Object []msgs,
    		String[] buttonLabels, String[] accButtonDesc, boolean ttf)
    {
    	if ( javax.swing.SwingUtilities.isEventDispatchThread()){
    		return showAccessibleDialogOnEvtThrd(parentComponent, title,
    	    		messageType, optionType, accDialogDesc, msgs,
    	    		buttonLabels, accButtonDesc,ttf);
    	}else{
    		ShowAccessibleDialog diag = new ShowAccessibleDialog(parentComponent, title,
    	    		messageType, optionType, accDialogDesc, msgs,
    	    		buttonLabels, accButtonDesc,ttf);
    		try {
    			EventQueue.invokeAndWait(diag);
    		} catch (InterruptedException e) {
    			e.printStackTrace(); // this can happen if the worker is cancelled
    		} catch (InvocationTargetException e) {
    			e.printStackTrace();
    			Throwable t = e.getCause();
    			if (t!=null){
    				t.printStackTrace();
    			}
    		}
    		diag.dereference();
    		return diag.getResults();
    	}
    }

    private static int showAccessibleDialogOnEvtThrd(Component parentComponent, String title,
    		int messageType, final int optionType, String accDialogDesc, Object []msgs,
    		String[] buttonLabels, String[] accButtonDesc, boolean useTTF)
    {
    	int result=0;
    	Object[] options;
    	JDialog dialog;
    	Object selectedValue;

    	// build message object for option pane
    	// this may not be the way to do this, if done in a textarea, the user must
    	// arrow up and down for each line of information, this way allows the
    	// user to tab from text field to textfield
    	java.util.Vector<Object> msgVct = new java.util.Vector<Object>();
    	JComponent focusAble = null;
    	if (msgs.length >10){ // put it into a textarea and scrollpane, get outofmemory if too many acctextfields
    		StringBuffer sb = new StringBuffer();
    		for (int i=0; i<msgs.length; i++) 	{
    			if(!(msgs[i] instanceof String)){
    				msgVct.add(msgs[i]);
    				continue;
    			}
    			sb.append(msgs[i].toString()+NEWLINE);
    		}
    		
			msgVct.insertElementAt(addScrollPane(new MsgTextArea(sb.toString())),0);
    	}else{
    		// put into scrollpane - some msgs are long
    		MsgPanel panel = new MsgPanel();
    		for (int i=0; i<msgs.length; i++) 	{
    			// allow blank labels to provide spaces between lines
    			if (msgs[i] instanceof String && ((String)msgs[i]).trim().length()>0){
    				//put into textarea if multiline
    				if (((String)msgs[i]).indexOf("\n")!=-1){
    					panel.addComponent(new MsgTextArea(msgs[i].toString()));
    				}else{
    					AccessibleDialogMsg adm = new AccessibleDialogMsg(" "+(String)msgs[i]);// icon is covering part of first char
    				    panel.addComponent(adm);
    					if(useTTF){
    						adm.setUseTrueTypeFont();
    					}
    				}
    			}
    			else{
				    panel.addComponent((Component)msgs[i]);
    				if (msgs[i] instanceof JTextField ){
    					focusAble = (JComponent)msgs[i];
    				}else if (msgs[i] instanceof JSpinner ){
    					focusAble = (JComponent)msgs[i];
    				}else if (msgs[i] instanceof JComboBox ){
    					focusAble = (JComponent)msgs[i];
    				}
    			}
    		}
    		if(panel.getComponentCount()>0){
    			msgVct.insertElementAt(addScrollPane(panel),0);
    		}
    	}
    	Object[] msgArray = new Object[msgVct.size()];
    	msgVct.copyInto(msgArray);
    	msgVct.clear();

    	if (parentComponent==null){
    		parentComponent= EACM.getEACM();
    	}
    	options = new Object[buttonLabels.length];
    	// build array of options, first button is yes, next is no, third is cancel
    	for (int i=0; i<buttonLabels.length; i++) 	{
    		JButton btn = new JButton(Utils.getResource(buttonLabels[i]));
    		btn.setMnemonic(Utils.getMnemonic(buttonLabels[i]));
    		String text = Utils.getResource(accButtonDesc[i]);
    		btn.getAccessibleContext().setAccessibleDescription(text);
    		btn.setToolTipText(text);
    		options[i] = btn;
    	}

//  	JOptionPane(Object message, int messageType, int optionType, Icon icon,
    	//Object[] options, Object initialValue)

    	final JOptionPane optionPane = new JOptionPane(
    			msgArray,
    			messageType,
    			optionType,
    			null,
    			options,
    			//options[0]
    			(focusAble!=null?null:  options[0])     // dont set initial value if focus is needed in textfield
    			);

    	for (int i=0; i<options.length; i++) {
    		JButton btn = (JButton)options[i];
    		switch(i)
    		{
    		case 0:
    			btn.addActionListener(new ActionListener() {
    				public void actionPerformed(ActionEvent e) {
    					optionPane.setValue(new Integer(JOptionPane.YES_OPTION)); //  YES_OPTION = 0; OK_OPTION = 0;
    				}
    			});
    			btn.addKeyListener(new KeyAdapter() {
    				// consume key events here
    				public void keyPressed(KeyEvent e) {
    					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
    						optionPane.setValue(new Integer(JOptionPane.YES_OPTION));
    						e.consume();
    					}
    				}
    			});
    			break;
    		case 1:
    			btn.addActionListener(new ActionListener() {
    				public void actionPerformed(ActionEvent e) {
    					int option = JOptionPane.NO_OPTION;
    					if (optionType==JOptionPane.OK_CANCEL_OPTION){
    						option = JOptionPane.CANCEL_OPTION;
    					}
    					optionPane.setValue(new Integer(option));
    				}
    			});

    			btn.addKeyListener(new KeyAdapter() {
    				// if enter is pressed after tabbing to the button, button0 is selected, force it to this button
    				public void keyPressed(KeyEvent e) {
    					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
    						int option = JOptionPane.NO_OPTION;
        					if (optionType==JOptionPane.OK_CANCEL_OPTION){
        						option = JOptionPane.CANCEL_OPTION;
        					}
        					optionPane.setValue(new Integer(option));
    						e.consume();
    					}
    				}
    			});
    			break;
    		case 2:
    			btn.addActionListener(new ActionListener() {
    				public void actionPerformed(ActionEvent e) {
    					optionPane.setValue(new Integer(JOptionPane.CANCEL_OPTION));
    				}
    			});
    			btn.addKeyListener(new KeyAdapter() {
    				// if enter is pressed after tabbing to the button, button0 is selected, force it to this button
    				public void keyPressed(KeyEvent e) {
    					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
    						optionPane.setValue(new Integer(JOptionPane.CANCEL_OPTION));
    						e.consume();
    					}
    				}
    			});
    			break;
    		default:
    			break;
    		}
    	}

    	dialog = optionPane.createDialog(parentComponent,
    			Utils.getResource("appName")+" "+Utils.getResource(title));
    	dialog.getAccessibleContext().setAccessibleDescription(Utils.getResource(accDialogDesc));

    	TFComponentAdapter tfca = null;
    	if (focusAble!=null){
            //Ensure the text field always gets the first focus and forwards actions
    		tfca = new TFComponentAdapter(optionPane,focusAble);
    		dialog.addComponentListener(tfca);
        }
   	    dialog.setResizable(msgArray[0] instanceof JScrollPane);// need this for a scrollpane?
    	dialog.setVisible(true);
    	// cleanup
    	dialog.dispose();
    	optionPane.setUI(null);
    	optionPane.setLayout(null);
    	if (tfca!=null){
    		dialog.removeComponentListener(tfca);
    		tfca.dereference();
    	}

    	// remove dialog listeners
    	EventListener listeners[] = dialog.getListeners(WindowListener.class);
    	if (listeners!=null){
    		for(int ii=0; ii<listeners.length; ii++) {
    			dialog.removeWindowListener((WindowListener)listeners[ii]);
    			listeners[ii]=null;
    		}
    	}
    	listeners = dialog.getListeners(WindowFocusListener.class);
    	if (listeners!=null){
    		for(int ii=0; ii<listeners.length; ii++) {
    			dialog.removeWindowFocusListener((WindowFocusListener)listeners[ii]);
    			listeners[ii]=null;
    		}
    	}
    	listeners = dialog.getListeners(ComponentListener.class);
    	if (listeners!=null){
    		for(int ii=0; ii<listeners.length; ii++) {
    			dialog.removeComponentListener((ComponentListener)listeners[ii]);
    			listeners[ii]=null;
    		}
    	}
    	listeners = optionPane.getListeners(java.beans.PropertyChangeListener.class);
    	if (listeners!=null){
    		for(int ii=0; ii<listeners.length; ii++) {
    			optionPane.removePropertyChangeListener((java.beans.PropertyChangeListener)listeners[ii]);
    			listeners[ii]=null;
    		}
    	}

    	dialog = null;
    	for (int i=0; i<msgArray.length; i++) {
    		if (msgArray[i] instanceof AccessibleDialogMsg){
    			((AccessibleDialogMsg)msgArray[i]).dereference();
    		}
    	}
    	msgArray = null;

    	for (int i=0; i<options.length; i++) {
    		JButton btn = (JButton)options[i];
    		listeners = btn.getListeners(ActionListener.class);
    		for(int ii=0; ii<listeners.length; ii++) {
    			btn.removeActionListener((ActionListener)listeners[ii]);
    			listeners[ii]=null;
    		}
    		listeners = btn.getListeners(KeyListener.class);
    		for(int ii=0; ii<listeners.length; ii++)  {
    			btn.removeKeyListener((KeyListener)listeners[ii]);
    			listeners[ii]=null;
    		}
    		options[i]=null;
    	}
    	options = null;

    	// get return value
    	selectedValue = optionPane.getValue();
    	result = JOptionPane.CLOSED_OPTION;
    	if(selectedValue != null)  {
    		if(selectedValue instanceof Integer){
    			result = ((Integer)selectedValue).intValue();
    		}
    	}

    	return result;
    }
    
    /**
     * put the message component in a scrollpane if needed
     * @param msgcmp
     * @return
     */
    private static JComponent addScrollPane(JComponent msgcmp){
		Dimension d = new Dimension(500, 250);
		Dimension pd = msgcmp.getPreferredSize();
		// put into scrollpane - some msgs are long
		if(pd.width>d.width || pd.height>d.height){
			JScrollPane jsp = new JScrollPane(msgcmp);
			jsp.setFocusable(false);
			Dimension jsppref = jsp.getPreferredSize();
			if (jsppref.width<d.width){
				d.setSize(jsppref.width, d.height);
			}
			if (jsppref.height<d.height){
				int vh = jsp.getHorizontalScrollBar().getPreferredSize().height+10;
				d.setSize(d.width, jsppref.height+vh); // allow for width of scroll
			}
			jsp.setPreferredSize(d);
			jsp.setSize(d);

			return jsp;
		}else{
			return msgcmp;
		}
    }

    private static class MsgTextArea extends JTextArea {
		private static final long serialVersionUID = 1L;

		MsgTextArea(String txt){
    		super(txt);
			setEditable(false);
			setBorder(null);
			setBackground(ColorPref.getBackgroundColor());
    	}
    }
    private static class MsgPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private GridBagConstraints cons;
    	MsgPanel(){
     		super(new GridBagLayout());
    		cons = new GridBagConstraints();
    		cons.gridx = cons.gridy = 0;
    		cons.gridwidth = GridBagConstraints.REMAINDER;
    		cons.gridheight = 1;
    		cons.anchor = GridBagConstraints.PAGE_START;
	        cons.fill = GridBagConstraints.HORIZONTAL;
	        cons.weightx = 1;
	        cons.weighty = 0;
    		cons.insets = new Insets(0,0,3,0);
    	}
    	void addComponent(Component c){
    		add(c, cons);
    		cons.gridy++;
    	}
    }

    private static class TFComponentAdapter extends ComponentAdapter implements ActionListener,KeyListener{
       	private JComponent tf = null;
    	private JOptionPane optionPane = null;
    	TFComponentAdapter(JOptionPane pane,JComponent jtf){
    		tf = jtf;
    	     //Register an event handler that puts the text into the option pane.
    		if(tf instanceof JTextField){
    			((JTextField)tf).addActionListener(this);
    		}else if(tf instanceof JSpinner){
    			tf = ((JSpinner.DefaultEditor)((JSpinner)tf).getEditor()).getTextField();
    			((JTextField)tf).addActionListener(this);// allow focus to go here when dialog opens
    			// allow enter to get to the ok button
    			tf.addKeyListener(this);
    		}

    		optionPane = pane;
    	}
    	//Ensure the input field always gets the first focus.
        public void componentShown(ComponentEvent ce) {
        	tf.requestFocusInWindow();
        }
        /** This method handles events for the text field. 'Enter' will invoke this*/
        public void actionPerformed(ActionEvent e) {
            optionPane.setValue(new Integer(0));
        }
        void dereference(){
        	if(tf instanceof JTextField){
    			((JTextField)tf).removeActionListener(this);
        	}
        	tf.removeKeyListener(this);
        	tf = null;
        	optionPane=null;
        }
        //spinner control needs this to get Enter presses
        public void keyPressed(KeyEvent e) {
        	if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        		actionPerformed(null);
        	}
        }
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
    }

    /**
     * this is used for crosstable when it must put an editor into a dialog
     * @param parentComponent
     * @param userMsg
     * @param tce
     * @return
     */
    public static Object showTableCellEditor(Component parentComponent,String userMsg,
    		AttrCellEditor tce)
    {
    	String[] options = {"ok","rstR","cancel"};
    	String accdesc[] = { "ok-acc","rstR-acc","cancel-confirm-acc" };

    	Object results = null; // reset is null
    	Object comp = tce;

    	if (tce instanceof AttrCellEditor){
    		comp = ((AttrCellEditor)tce).getComponent();
    	}

    	Object obj[] = new Object[] {userMsg,comp};
    	int r= showAccessibleDialog(parentComponent,//Component parentComponent
    			"input-title", //title
    			JOptionPane.PLAIN_MESSAGE, //messageType
    			JOptionPane.YES_NO_CANCEL_OPTION, // optiontype
    			"input-acc", //accDialogDesc
    			obj, //msgs
    			options,  //button labels
    			accdesc); //accButtonDescs

    	options = null;
    	accdesc = null;
    	obj = null;

    	if (r== CLOSED || r ==JOptionPane.CANCEL_OPTION){
    		results = JOptionPane.UNINITIALIZED_VALUE;
    	}else if (r == JOptionPane.YES_OPTION) {
    		results = tce.getCellEditorValue(); // user set a value
    	}

    	return results;
    }
    /**
     * show accessible dialog with spinner and ok, cancel buttons
     * @param parentComponent
     * @param userMsg
     * @param startvalue
     * @param minimum
     * @param maximum
     * @param stepSize
     * @return
     */
    public static int showIntSpinner(Component parentComponent,String userMsg,
    		int startvalue, int minimum, int maximum, int stepSize)
    {
    	int id = CLOSED;

    	String[] options = {"ok","cancel"};
    	String accdesc[] = { "ok-acc", "cancel-acc"};

    	IntSpinner spinner = new IntSpinner(startvalue, minimum, maximum, stepSize);

    	Object obj[] = new Object[] {userMsg,spinner};
    	int r= showAccessibleDialog(parentComponent,//Component parentComponent
    			"input-title", //title
    			JOptionPane.QUESTION_MESSAGE, //messageType
    			JOptionPane.YES_NO_OPTION, // optiontype
    			"input-acc", //accDialogDesc
    			obj, //msgs
    			options,  //button labels
    			accdesc); //accButtonDescs

    	options = null;
    	accdesc = null;

    	if (r == JOptionPane.YES_OPTION) {
    		id = spinner.getInt();
    	}

    	spinner.dereference();
    	spinner = null;

    	return id;
    }

    /**
     * this is used for capturing Integer input
     * @param parentComponent
     * @param userMsg
     * @return
     */
    public static int showIntInput(Component parentComponent,String userMsg) {
    	String value = showInput(parentComponent,userMsg, null, 0);
    	int id = CLOSED;

    	if (value !=null && value.length()>0) {
    		id = Integer.parseInt(value);
    	}

    	return id;
    }
    /**
     * show accessible dialog for accepting input with ok, cancel buttons
     * @param parentComponent
     * @param userMsg
     * @param txt
     * @param numcols
     * @return
     */
    public static String showInput(Component parentComponent,String userMsg, String txt, int numcols) {
    	String value = null;
    	String[] options = {"ok","cancel"};
    	String accdesc[] = { "ok-acc", "cancel-acc"};

    	JTextField text = null;
    	if (txt==null && numcols==0){// assumption this is getting an integer
    		text = new IntField();
    		/*this doesnt allow an empty field, cant delete first digit
    		JFormattedTextField jtf = new JFormattedTextField(java.text.NumberFormat.getIntegerInstance());
    		text = jtf;
    		jtf.setColumns(10);
    		((javax.swing.text.DefaultFormatter)jtf.getFormatter()).setAllowsInvalid(false);
    		((javax.swing.text.DefaultFormatter)jtf.getFormatter()).setCommitsOnValidEdit(true);
    		((javax.swing.text.DefaultFormatter)jtf.getFormatter()).setOverwriteMode(true);
    		*/
    	}else{
    		text = new JTextField(txt,numcols);
    	}
    	Object obj[] = new Object[] {userMsg,text};
    	int r= showAccessibleDialog(parentComponent,//Component parentComponent
    			"input-title", //title
    			JOptionPane.QUESTION_MESSAGE, //messageType
    			JOptionPane.YES_NO_OPTION, // optiontype
    			"input-acc", //accDialogDesc
    			obj, //msgs
    			options,  //button labels
    			accdesc); //accButtonDescs

    	options = null;
    	accdesc = null;

    	if (r == JOptionPane.YES_OPTION) {
    		value = text.getText();
    	}

    	if (text instanceof IntField){
    		((IntField)text).dereference();
    	}
    	text = null;

    	return value;
    }

    private static String getLinkDefinition() { //link_type
        String[] sDef = EACMProperties.getLinkTypes();
        return Utils.getResource(sDef[BehaviorPref.getLinkType()]);
    }
    /**
     * show link information with a details button
     * @param parentComponent
     * @param userMsg
     */
    public static void showLinkResults(Component parentComponent,String userMsg) {
    	String[] options = {"ok"};
    	String accdesc[] = { "ok-close-acc"};

		Dimension d = new Dimension(600,200);

        String msg = Utils.getResource("eannounce.link.success",getLinkDefinition(), userMsg );

		JTextArea jta = new JTextArea(msg);
		JScrollPane jsp = new JScrollPane(jta);
		jsp.setFocusable(false);
		jta.setEditable(false);
		jta.setBorder(null);

		Dimension jsppref = jsp.getPreferredSize();

		if (jsppref.width<d.width){
			d.setSize(jsppref.width, d.height);
		}
		if (jsppref.height<d.height){
			d.setSize(d.width, jsppref.height+40);
		}
		jsp.setPreferredSize(d);
		jsp.setSize(d);
		jsp.setVisible(false);

		JPanel lblBtnPnl = new JPanel(null);
		//msg3011.0 = The link(s) have been created.
		JLabel lbl = new JLabel(Utils.getResource("msg3011.0"));

		DetailAction act = new DetailAction(jsp);
		JButton detailsBtn = new JButton(act);
		detailsBtn.setMnemonic((char)((Integer)detailsBtn.getAction().getValue(Action.MNEMONIC_KEY)).intValue());

		GroupLayout layout = new GroupLayout(lblBtnPnl);
		lblBtnPnl.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.ParallelGroup leftToRight = layout.createParallelGroup();
        GroupLayout.SequentialGroup  bknm= layout.createSequentialGroup();
        bknm.addComponent(lbl);

        // separate label and button, button moves to right when jsp is shown
        bknm.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);

        bknm.addComponent(detailsBtn,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                GroupLayout.PREFERRED_SIZE); //prevent button from growing
        leftToRight.addGroup(bknm);

        GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
        GroupLayout.ParallelGroup bknmRow = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);//valign button with the text
        bknmRow.addComponent(lbl);
        bknmRow.addComponent(detailsBtn,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                GroupLayout.PREFERRED_SIZE); // prevent vertical growth
        topToBottom.addGroup(bknmRow);

        layout.setHorizontalGroup(leftToRight);
        layout.setVerticalGroup(topToBottom);


    	Object obj[] = new Object[] {lblBtnPnl,jsp};
    	showAccessibleDialog(parentComponent,//Component parentComponent
    			"information-title", //title
    			JOptionPane.PLAIN_MESSAGE, //messageType
    			JOptionPane.OK_CANCEL_OPTION, // optiontype
    			"information-acc", //accDialogDesc
    			obj, //msgs
    			options,  //button labels
    			accdesc); //accButtonDescs

    	options = null;
    	accdesc = null;


    	jsp.removeAll();
    	jsp.setUI(null);
    	jta.removeAll();
    	jta.setUI(null);
    	act.dereference();
    	act = null;

    	lblBtnPnl.removeAll();
    	lblBtnPnl.setUI(null);
    	lblBtnPnl = null;

    	lbl.removeAll();
    	lbl.setUI(null);
    	lbl = null;

    	detailsBtn.setAction(null);
    	detailsBtn.setUI(null);
    	detailsBtn = null;
    }
	private static class DetailAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private JScrollPane jsp = null;
		DetailAction(JScrollPane j) {
			super("data");
			jsp = j;
		}

		public void dereference(){
			jsp = null;
			super.dereference();
		}
		public void actionPerformed(ActionEvent e) {

			jsp.setVisible(!jsp.isShowing());
			jsp.getTopLevelAncestor().validate();
			jsp.getTopLevelAncestor().setSize(jsp.getTopLevelAncestor().getPreferredSize());
			//data = Show Details
			//dataHide = Hide Details
			if (jsp.isVisible()){
				((AbstractButton)e.getSource()).setText(Utils.getResource("dataHide"));
			}else{
				((AbstractButton)e.getSource()).setText(Utils.getResource("data"));
			}
		}
	}
    /**
     * show accessible dialog for displaying list with ok, cancel buttons
     * @param parentComponent
     * @param userMsg
     * @return
     */
    public static Object[] showList(Component parentComponent,String userMsg, Object []data) {
    	Object[] value = null;
    	String[] options = {"ok","cancel"};
    	String accdesc[] = { "ok-acc", "cancel-acc"};

    	JList list = new JList();
    	JScrollPane scroll = new JScrollPane(list);
    	list.setListData(data);

    	Object obj[] = new Object[] {userMsg,scroll};
    	int r= showAccessibleDialog(parentComponent,//Component parentComponent
    			"input-title", //title
    			JOptionPane.PLAIN_MESSAGE, //messageType
    			JOptionPane.OK_CANCEL_OPTION, // optiontype
    			"input-acc", //accDialogDesc
    			obj, //msgs
    			options,  //button labels
    			accdesc); //accButtonDescs

    	options = null;
    	accdesc = null;

    	if (r == JOptionPane.OK_OPTION) {
    		value = list.getSelectedValues();
    	}

    	scroll.removeAll();
    	scroll.setUI(null);
    	list.removeAll();
    	list.setUI(null);

    	return value;
    }

    /**************
     * Show accessible dialog with information with OK button
     * @param parentComponent
     * @param userMsg
     */
    public static void showFYI(Component parentComponent,String userMsg) {
    	String[] options = {"ok"};
    	String accdesc[] = { "ok-close-acc" };
    	showAccessibleDialog(parentComponent,//Component parentComponent
    			"information-title", //title
    			JOptionPane.INFORMATION_MESSAGE, //messageType
    			JOptionPane.YES_OPTION, // optiontype
    			"information-acc", //accDialogDesc
    			Routines.convertToArray(userMsg,RETURN), //msgs
    			options,  //button labels
    			accdesc); //accButtonDescs

    	options = null;
    	accdesc = null;
    }

    public static void showFYI(Component parentComponent,Exception exc) {
    	String[] options = {"ok"};
    	String accdesc[] = { "ok-close-acc" };
    	String userMsg = exc.toString();
        int index = userMsg.lastIndexOf(OK_ERROR);
        if (index > 0) {
        	userMsg = Routines.replace(userMsg, OK_ERROR, "");
        }

    	showAccessibleDialog(parentComponent,//Component parentComponent
    			"information-title", //title
    			JOptionPane.INFORMATION_MESSAGE, //messageType
    			JOptionPane.YES_OPTION, // optiontype
    			"information-acc", //accDialogDesc
    			Routines.convertToArray(userMsg,RETURN), //msgs
    			options,  //button labels
    			accdesc); //accButtonDescs

    	exc.printStackTrace();

    	options = null;
    	accdesc = null;
    }
    /**************
     * Show accessible dialog for exception with OK button
     * @param exc
     * @param title
     */
    public static void showException(Component parentComponent,Throwable exc){
    	showException(parentComponent,exc,"err-title");
    }
    public static void showException(Component parentComponent,Throwable exc,String title){
    	String[] options = {"ok"};
    	String accdesc[] = { "ok-close-acc" };
    	if(!(exc instanceof EANBusinessRuleException)){ // already displayed?
    		Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE, "error",exc);
    	}
    	String userMsg = exc.toString();
        int index = userMsg.lastIndexOf(OK_ERROR);
        if (index > 0) {
        	userMsg = Routines.replace(userMsg, OK_ERROR, "");
        }

    	showAccessibleDialog(parentComponent,//Component parentComponent
    			title, //title
    			JOptionPane.ERROR_MESSAGE, //messageType
    			JOptionPane.YES_OPTION, // optiontype
    			"exception-acc", //accDialogDesc
    			Routines.convertToArray(userMsg,NEWLINE), //msgs
    			options,  //button labels
    			accdesc); //accButtonDescs

    	options = null;
    	accdesc = null;
    }
    public static void showBusRuleException(Component parentComponent,EANBusinessRuleException exc,String title){
    	String[] options = {"ok"};
    	String accdesc[] = { "ok-close-acc" };

    	Vector<String> msgsVct = new Vector<String>();
    	for(int i=0; i<exc.getErrorCount(); i++){
    		StringBuilder sb = new StringBuilder();
    		EANObject obj = (EANObject)exc.getObject(i);
    		String strObj = "Unknown Object";
    		if (obj instanceof EANAttribute) {
    			strObj = ((EANAttribute) obj).getLongDescription();
    		} else if (obj instanceof EntityItem) {
    			strObj = ((EntityItem) obj).getLongDescription();
    		} else if (obj instanceof EANFoundation) {
    			strObj = ((EANFoundation) obj).getLongDescription();
    		}

    		String strDesc = exc.getMessage(i);
    		int colonid = strDesc.indexOf(':');
    		if (colonid!=-1){
    			sb.append(strObj+" - "+strDesc.substring(0,colonid)+NEWLINE+
    					strDesc.substring(colonid+1));
    		}else{
    			if (strDesc.indexOf(strObj)==-1){
    				sb.append(strObj + " - " + strDesc);
    			}else{ // prevent repeat entity info when this exception was created from another
    				sb.append(strDesc.trim()); // remove leading new line
    			}
    		}
    		msgsVct.add(sb.toString());
    	}
    	String msgArray[] = new String[msgsVct.size()];
    	msgsVct.copyInto(msgArray);
    	msgsVct.clear();

    	showAccessibleDialog(parentComponent,//Component parentComponent
    			title, //title
    			JOptionPane.ERROR_MESSAGE, //messageType
    			JOptionPane.YES_OPTION, // optiontype
    			"exception-acc", //accDialogDesc
    			msgArray, //msgs
    			options,  //button labels
    			accdesc); //accButtonDescs

    	options = null;
    	accdesc = null;
    }

    /**************
     * Show Error message in accessible dialog with OK button
     * @param parentComponent
     * @param userMsg
     */
    public static void showErrorMessage(Component parentComponent,String userMsg){
    	String[] options = {"ok"};
    	String accdesc[] = { "ok-close-acc" };
    	Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,userMsg);
    	
    	showAccessibleDialog(parentComponent,//Component parentComponent
    			"err-title", //title
    			JOptionPane.ERROR_MESSAGE, //messageType
    			JOptionPane.YES_OPTION, // optiontype
    			"err-acc", //accDialogDesc
    			Routines.convertToArray(userMsg,RETURN), //msgs
    			options,  //button labels
    			accdesc); //accButtonDescs

    	options = null;
    	accdesc = null;
    }

    /**
     * Show Warning message in accessible dialog with OK button
     * @param parentComponent
     * @param userMsg
     */
    public static void showWarning(Component parentComponent,String userMsg){
 		showMessage(parentComponent,"warning-title", JOptionPane.WARNING_MESSAGE,
 				"warning-acc",	userMsg);
    }
    /**************
     * Show accessible dialog with OK button
     * @param title
     * @param msgType
     * @param accDialogDesc
     * @param userMsg
     */
    public static void showMessage(Component parentComponent,
    		String title, int msgType, String accDialogDesc, String userMsg){
    	String[] options = {"ok"};
    	String accdesc[] = { "ok-close-acc" };
    	showAccessibleDialog(parentComponent,//Component parentComponent
    			title, //title
    			msgType, //messageType
    			JOptionPane.YES_OPTION, // optiontype
    			accDialogDesc, //accDialogDesc
    			Routines.convertToArray(userMsg,RETURN), //msgs
    			options,  //button labels
    			accdesc); //accButtonDescs

    	options = null;
    	accdesc = null;
    }
    /**
     * show message using TrueType Font
     * @param parentComponent
     * @param title
     * @param msgType
     * @param accDialogDesc
     * @param userMsg
     */
    public static void showTTFMessage(Component parentComponent,
    		String title, int msgType, String accDialogDesc, String userMsg){
    	String[] options = {"ok"};
    	String accdesc[] = { "ok-close-acc" };
    	showAccessibleDialog(parentComponent,//Component parentComponent
    			title, //title
    			msgType, //messageType
    			JOptionPane.YES_OPTION, // optiontype
    			accDialogDesc, //accDialogDesc
    			Routines.convertToArray(userMsg,RETURN), //msgs
    			options,  //button labels
    			accdesc,true); //accButtonDescs

    	options = null;
    	accdesc = null;
    }
    /*********************************************************************
     * This is used with JOptionPane and text messages.  This will allow
     * Accessibility to read the information.  It will look like a label.  The
     * user can tab from line to line and hear the text.  They can then tab to
     * the buttons.
     *
     */
    private static class AccessibleDialogMsg extends JTextField implements FocusListener
    {
    	private static final long serialVersionUID = 1L;
    	private static Border border=LineBorder.createBlackLineBorder();
    	private boolean bUseTrueType = false;
    	void setUseTrueTypeFont() {
    		bUseTrueType = true;
    	}

    	AccessibleDialogMsg(String msg)	{
    		super(msg);
    		setEditable(false);
    		setBorder(null);
    		getAccessibleContext().setAccessibleDescription("message line");
    		getAccessibleContext().setAccessibleName("message");
    		addFocusListener(this);
    	}

    	public void focusGained(FocusEvent e) {// Invoked when a component gains the keyboard focus
    		setBorder(border); // highlight it so user can see what has focus (or being read)
    	}
    	public void focusLost(FocusEvent e) {
    		setBorder(null);
    	}

    	public Color getBackground() {
    		// setting the background didnt always work, this does
    		return UIManager.getColor("Label.background");
    	}
    	void dereference()	{
    		getAccessibleContext().setAccessibleDescription(null);
    		getAccessibleContext().setAccessibleName(null);
    		accessibleContext = null;
            removeFocusListener(this);
    	}
    	/**
         * over ride for true type font
         *
         * @return Font
         */
    	public Font getFont() {
    		if (bUseTrueType) {
    			return fTrueType;
    		}
    		return super.getFont();
    	}
    }
    private static final Font fTrueType =  new Font("Courier New", 0, 12);
}
