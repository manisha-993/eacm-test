/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/12/05
 * @author Anthony C. Liberto
 *
 * $Log: MessagePanel.java,v $
 * Revision 1.2  2008/01/30 16:26:59  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.4  2006/09/12 14:47:08  tony
 * MN_29256135
 *
 * Revision 1.3  2006/05/02 17:11:28  tony
 * truncate at 75 instead of 100
 *
 * Revision 1.2  2005/09/12 19:03:10  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.11  2005/09/08 17:58:56  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.10  2005/08/18 21:07:43  tony
 * updated messagePanel added more functionality.
 *
 * Revision 1.9  2005/05/25 18:15:42  tony
 * silverBulletReload
 *
 * Revision 1.8  2005/02/02 17:30:22  tony
 * JTest Second Pass
 *
 * Revision 1.7  2005/01/27 23:18:17  tony
 * JTest Formatting
 *
 * Revision 1.6  2004/12/14 23:33:05  tony
 * TIR USRO-R-JSTT-67HTKR
 *
 * Revision 1.5  2004/10/13 17:56:18  tony
 * added on-line update functionality.
 *
 * Revision 1.4  2004/09/23 16:24:21  tony
 * improved logic to acquire focus to textbox on
 * message panel input functionality.
 *
 * Revision 1.3  2004/06/17 18:58:57  tony
 * cr_4215 cr0313024215
 *
 * Revision 1.2  2004/05/18 17:37:01  tony
 *  acl_20040518
 * improved message panel functionality.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:26  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2003/10/29 00:22:22  tony
 * removed System.out. statements.
 *
 * Revision 1.8  2003/06/02 19:47:03  tony
 * 51039
 *
 * Revision 1.7  2003/05/02 20:05:54  tony
 * 50504 -- adjusted messaging and this seems to have
 * resolved the problem in local test.
 *
 * Revision 1.6  2003/04/10 20:06:23  tony
 * updated logic to allow for dialogs to properly
 * eminiate from the dialogs parent.
 *
 * Revision 1.5  2003/03/25 23:29:05  tony
 * added eDisplayable to provide an
 * easier interface into components.  This will
 * assist in ease of programming in the future.
 *
 * Revision 1.4  2003/03/24 21:52:23  tony
 * added modalCursor logic.
 *
 * Revision 1.3  2003/03/13 18:38:43  tony
 * accessibility and column Order.
 *
 * Revision 1.2  2003/03/04 22:34:49  tony
 * *** empty log message ***
 *
 * Revision 1.1.1.1  2003/03/03 18:03:40  tony
 * This is the initial load of OPICM
 *
 *
 */
package com.elogin;
import com.ibm.eannounce.eobjects.*;
import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class MessagePanel extends AccessibleDialogPanel implements Accessible, EAccessConstants, KeyListener {
	private static final long serialVersionUID = 1L;
	private String[] icon = { "md_stop.gif", "md_info.gif", "md_warn.gif", "md_ques.gif" };
    private String[] txtRa = new String[4];

    private int curButtons = -1;
    private int curType = -1;
    private int response = -1;

    private JTextField txt = new JTextField(20);
    private EButton[] btn = new EButton[4];

    private EMLabel msg = new EMLabel() {
    	private static final long serialVersionUID = 1L;
    	public Cursor getCursor() {
            return Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
        }
    };

    private AccessibleDialogPanel buttonPane = new AccessibleDialogPanel() {
    	private static final long serialVersionUID = 1L;
    	public Cursor getCursor() {
            return Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
        }
    };

    private ECheckBox chkDoNotDisplay = new ECheckBox(getString("DNSAgain")); //cr_4215

    /*
     TIR USRO-R-JSTT-67HTKR
     */
    private EPanel pnlCenter = new EPanel(new BorderLayout());
    private ETextArea msgArea = new ETextArea() {
    	private static final long serialVersionUID = 1L;
    	public Cursor getCursor() {
            return Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
        }
    };

    private EScrollPane msgScroll = new EScrollPane(msgArea);

    /**
     * messagePanel
     * @author Anthony C. Liberto
     */
    public MessagePanel() {
        super(new BorderLayout());
        init();
        return;
    }

    /**
     * getCursor
     * @author Anthony C. Liberto
     * @return Cursor
     */
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    }

    /**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
        btn[0] = new EButton() {
        	private static final long serialVersionUID = 1L;
        	public Cursor getCursor() {
                return Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
            }
        };
        btn[1] = new EButton() {
        	private static final long serialVersionUID = 1L;
        	public Cursor getCursor() {
                return Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
            }
        };
        btn[2] = new EButton() {
        	private static final long serialVersionUID = 1L;
        	public Cursor getCursor() {
                return Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
            }
        };
        btn[3] = new EButton() {
        	private static final long serialVersionUID = 1L;
        	public Cursor getCursor() {
                return Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
            }
        };

        btn[0].setActionCommand("0");
        btn[1].setActionCommand("1");
        btn[2].setActionCommand("2");
        btn[3].setActionCommand("3");
        btn[0].addActionListener(this);
        btn[1].addActionListener(this);
        btn[2].addActionListener(this);
        btn[3].addActionListener(this);

        setModalCursor(true);

        //TIR USRO-R-JSTT-67HTKR		add("Center",txt);
        add("Center", pnlCenter); //TIR USRO-R-JSTT-67HTKR
        pnlCenter.add("North", txt); //TIR USRO-R-JSTT-67HTKR
        pnlCenter.add("South", msgScroll); //TIR USRO-R-JSTT-67HTKR
        initScroll(); //TIR USRO-R-JSTT-67HTKR
        txt.addKeyListener(this);
        add("North", msg);
        txt.setVisible(false);
        add("South", buttonPane);

        msg.setIconTextGap(10);
        msg.setLabelFor(this);
        return;
    }

    /*
     TIR USRO-R-JSTT-67HTKR
    	public void construct(int _dialogType, int _msgType, int _buttons, String _message) {
    		setType(_dialogType);
    		setIcon(_msgType);
    		setButtons(_buttons);
    		setMessage(_message);
    		revalidate();
    		return;
    	}
    */
    /**
     * setType
     * @param _dialogType
     * @author Anthony C. Liberto
     */
    public void setType(int _dialogType) {
        curType = _dialogType;
        response = -1;
        initTextField();
        return;
    }

    /**
     * evaluate
     * @return
     * @author Anthony C. Liberto
     */
    public Object evaluate() {
        if (isInputPanel()) {
            return txt.getText();
        } else if (isConfirmPanel()) {
            if (response >= 0) {
                return new Integer(response);
            }
        }
        return null;
    }

    /**
     * isMessagePanel
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isMessagePanel() {
        return curType == MESSAGE_TYPE;
    }

    /**
     * isInputPanel
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isInputPanel() {
        return curType == INPUT_TYPE;
    }

    /**
     * isConfirmPanel
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isConfirmPanel() {
        return curType == CONFIRM_TYPE;
    }

    /**
     * setIcon
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setIcon(int _i) {
        msg.setIcon(eaccess().getImageIcon(icon[_i]));
        return;
    }

    /**
     * setMessage
     * @author Anthony C. Liberto
     * @param _message
     */
    public void setMessage(String _message) {
        msg.setText(Routines.splitString(_message, 100));
        getAccessibleContext().setAccessibleDescription(_message);
        msg.setUseTrueTypeFont(false);
        return;
    }

    private void setButtons(int _buttons) {
        while (buttonPane.getComponentCount() > 0) {
            buttonPane.remove(0);
        }
        chkDoNotDisplay.setSelected(false); //cr_4215
        buttonPane.removeAll();
        curButtons = _buttons;
        if (curButtons == OK) {
            txtRa[0] = getString("ok");
            buttonPane.add(btn[0]);
            btn[0].setText(txtRa[0]);
            btn[0].setMnemonic(getChar("ok-s"));
            btn[0].getAccessibleContext().setAccessibleDescription(txtRa[0]);
        } else if (curButtons > OK && curButtons < YES_NO_CANCEL) {
            if (curButtons == YES_NO || curButtons == YES_NO_SHOW) {
                txtRa[0] = getString("yes");
                txtRa[1] = getString("no");
                btn[0].setText(txtRa[0]);
                btn[1].setText(txtRa[1]);
                btn[0].setMnemonic(getChar("yes-s"));
                btn[1].setMnemonic(getChar("no-s"));
                btn[0].getAccessibleContext().setAccessibleDescription(txtRa[0]);
                btn[1].getAccessibleContext().setAccessibleDescription(txtRa[1]);
            } else if (curButtons == OK_CANCEL) {
                txtRa[0] = getString("ok");
                txtRa[1] = getString("cncl");
                btn[0].setText(txtRa[0]);
                btn[1].setText(txtRa[1]);
                btn[0].setMnemonic(getChar("ok-s"));
                btn[1].setMnemonic(getChar("cncl-s"));
                btn[0].getAccessibleContext().setAccessibleDescription(txtRa[0]);
                btn[1].getAccessibleContext().setAccessibleDescription(txtRa[1]);
            } else if (curButtons == NOW_LATER) {
                txtRa[0] = getString("now");
                txtRa[1] = getString("later");
                btn[0].setText(txtRa[0]);
                btn[1].setText(txtRa[1]);
                btn[0].setMnemonic(getChar("now-s"));
                btn[1].setMnemonic(getChar("later-s"));
                btn[0].getAccessibleContext().setAccessibleDescription(txtRa[0]);
                btn[1].getAccessibleContext().setAccessibleDescription(txtRa[1]);
            } else if (curButtons == NEW_EXISTING) {
                txtRa[0] = getString("new");
                txtRa[1] = getString("xst");
                btn[0].setText(txtRa[0]);
                btn[1].setText(txtRa[1]);
                btn[0].setMnemonic(getChar("new-s"));
                btn[1].setMnemonic(getChar("xst-s"));
                btn[0].getAccessibleContext().setAccessibleDescription(txtRa[0]);
                btn[1].getAccessibleContext().setAccessibleDescription(txtRa[1]);
            } else if (curButtons == CHOOSE_EXIT) {
                txtRa[0] = getString("chooseMW");
                txtRa[1] = getString("exit");
                btn[0].setText(txtRa[0]);
                btn[1].setText(txtRa[1]);
                btn[0].setMnemonic(getChar("chooseMW-s"));
                btn[1].setMnemonic(getChar("exit-s"));
                btn[0].getAccessibleContext().setAccessibleDescription(txtRa[0]);
                btn[1].getAccessibleContext().setAccessibleDescription(txtRa[1]);
            } else if (curButtons == A_B) {
                txtRa[0] = getString("a");
                txtRa[1] = getString("b");
                btn[0].setText(txtRa[0]);
                btn[1].setText(txtRa[1]);
                btn[0].setMnemonic(getChar("a-s"));
                btn[1].setMnemonic(getChar("b-s"));
                btn[0].getAccessibleContext().setAccessibleDescription(txtRa[0]);
                btn[1].getAccessibleContext().setAccessibleDescription(txtRa[1]);
            }
            buttonPane.add(btn[0]);
            buttonPane.add(btn[1]);
            if (curButtons == YES_NO_SHOW) { //cr_4215
                buttonPane.add(chkDoNotDisplay); //cr_4215
            } //cr_4215
        } else if (curButtons == YES_NO_CANCEL) {
            txtRa[0] = getString("yes");
            txtRa[1] = getString("no");
            txtRa[2] = getString("cncl");
            btn[0].setText(txtRa[0]);
            btn[1].setText(txtRa[1]);
            btn[2].setText(txtRa[2]);
            btn[0].setMnemonic(getChar("yes-s"));
            btn[1].setMnemonic(getChar("no-s"));
            btn[2].setMnemonic(getChar("cncl-s"));
            buttonPane.add(btn[0]);
            buttonPane.add(btn[1]);
            buttonPane.add(btn[2]);
            btn[0].getAccessibleContext().setAccessibleDescription(txtRa[0]);
            btn[1].getAccessibleContext().setAccessibleDescription(txtRa[1]);
            btn[2].getAccessibleContext().setAccessibleDescription(txtRa[2]);
        } else if (curButtons == ABORT_RETRY_IGNORE) {
            txtRa[0] = getString("abort");
            txtRa[1] = getString("retry");
            txtRa[2] = getString("ignore");
            btn[0].setText(txtRa[0]);
            btn[1].setText(txtRa[1]);
            btn[2].setText(txtRa[2]);
            btn[0].setMnemonic(getChar("abort-s"));
            btn[1].setMnemonic(getChar("retry-s"));
            btn[2].setMnemonic(getChar("ignore-s"));
            buttonPane.add(btn[0]);
            buttonPane.add(btn[1]);
            buttonPane.add(btn[2]);
            btn[0].getAccessibleContext().setAccessibleDescription(txtRa[0]);
            btn[1].getAccessibleContext().setAccessibleDescription(txtRa[1]);
            btn[2].getAccessibleContext().setAccessibleDescription(txtRa[2]);
		} else if (curButtons == A_B_C) {
            txtRa[0] = getString("a");
            txtRa[1] = getString("b");
            txtRa[2] = getString("c");
            btn[0].setText(txtRa[0]);
            btn[1].setText(txtRa[1]);
            btn[2].setText(txtRa[2]);
            btn[0].setMnemonic(getChar("a-s"));
            btn[1].setMnemonic(getChar("c-s"));
            btn[2].setMnemonic(getChar("b-s"));
            buttonPane.add(btn[0]);
            buttonPane.add(btn[1]);
            buttonPane.add(btn[2]);
            btn[0].getAccessibleContext().setAccessibleDescription(txtRa[0]);
            btn[1].getAccessibleContext().setAccessibleDescription(txtRa[1]);
            btn[2].getAccessibleContext().setAccessibleDescription(txtRa[2]);
        } else if (curButtons == YES_NO_ALL_CANCEL) {
            txtRa[0] = getString("yes");
            txtRa[1] = getString("no");
            txtRa[2] = getString("all");
            txtRa[3] = getString("cncl");
            btn[0].setText(txtRa[0]);
            btn[1].setText(txtRa[1]);
            btn[2].setText(txtRa[2]);
            btn[3].setText(txtRa[3]);
            btn[0].setMnemonic(getChar("yes-s"));
            btn[1].setMnemonic(getChar("no-s"));
            btn[2].setMnemonic(getChar("all-s"));
            btn[3].setMnemonic(getChar("cncl-s"));
            buttonPane.add(btn[0]);
            buttonPane.add(btn[1]);
            buttonPane.add(btn[2]);
            buttonPane.add(btn[3]);
            btn[0].getAccessibleContext().setAccessibleDescription(txtRa[0]);
            btn[1].getAccessibleContext().setAccessibleDescription(txtRa[1]);
            btn[2].getAccessibleContext().setAccessibleDescription(txtRa[2]);
            btn[3].getAccessibleContext().setAccessibleDescription(txtRa[3]);
        } else if (curButtons == ALL_NONE_CHOOSE_CANCEL) {
            txtRa[0] = getString("saveA");
            txtRa[1] = getString("snone");
            txtRa[2] = getString("choose");
            txtRa[3] = getString("cncl");
            btn[0].setText(txtRa[0]);
            btn[1].setText(txtRa[1]);
            btn[2].setText(txtRa[2]);
            btn[3].setText(txtRa[3]);
            btn[0].setMnemonic(getChar("saveA-s"));
            btn[1].setMnemonic(getChar("snone-s"));
            btn[2].setMnemonic(getChar("choose-s"));
            btn[3].setMnemonic(getChar("cncl-s"));
            buttonPane.add(btn[0]);
            buttonPane.add(btn[1]);
            buttonPane.add(btn[2]);
            buttonPane.add(btn[3]);
            btn[0].getAccessibleContext().setAccessibleDescription(txtRa[0]);
            btn[1].getAccessibleContext().setAccessibleDescription(txtRa[1]);
            btn[2].getAccessibleContext().setAccessibleDescription(txtRa[2]);
            btn[3].getAccessibleContext().setAccessibleDescription(txtRa[3]);
        } else if (curButtons == A_B_C_D) {
            txtRa[0] = getString("a");
            txtRa[1] = getString("b");
            txtRa[2] = getString("c");
            txtRa[3] = getString("d");
            btn[0].setText(txtRa[0]);
            btn[1].setText(txtRa[1]);
            btn[2].setText(txtRa[2]);
            btn[3].setText(txtRa[3]);
            btn[0].setMnemonic(getChar("a-s"));
            btn[1].setMnemonic(getChar("b-s"));
            btn[2].setMnemonic(getChar("c-s"));
            btn[3].setMnemonic(getChar("d-s"));
            buttonPane.add(btn[0]);
            buttonPane.add(btn[1]);
            buttonPane.add(btn[2]);
            buttonPane.add(btn[3]);
            btn[0].getAccessibleContext().setAccessibleDescription(txtRa[0]);
            btn[1].getAccessibleContext().setAccessibleDescription(txtRa[1]);
            btn[2].getAccessibleContext().setAccessibleDescription(txtRa[2]);
            btn[3].getAccessibleContext().setAccessibleDescription(txtRa[2]);
        }
        return;
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        dereferenceScroll(); //TIR USRO-R-JSTT-67HTKR
        for (int i = 0; i < icon.length; ++i) {
            icon[i] = null;
        }
        icon = null;

        txt.removeKeyListener(this);
        txt.removeAll();
        txt.removeNotify();
        txt = null;

        for (int i = 0; i < btn.length; ++i) {
            btn[i].removeAll();
            btn[i].removeActionListener(this);
            btn[i].removeNotify();
            btn[i] = null;
        }
        if (chkDoNotDisplay != null) { //cr_4215
            chkDoNotDisplay.removeAll(); //cr_4215
            chkDoNotDisplay.removeNotify(); //cr_4215
            chkDoNotDisplay = null; //cr_4215
        } //cr_4215
        msg.removeAll();
        msg.removeNotify();
        msg = null;

        buttonPane.removeAll();
        buttonPane.removeNotify();
        buttonPane = null;

        super.dereference();

        return;
    }

    /**
     * getResponse
     * @return
     * @author Anthony C. Liberto
     */
    public int getResponse() {
        return response;
    }

    /**
     * getText
     * @return
     * @author Anthony C. Liberto
     */
    public String getText() {
        return txt.getText();
    }

    /**
     * setTitle
     * @author Anthony C. Liberto
     * @param _title
     */
    public void setTitle(String _title) {
    }
    /**
     * getTitle
     * @author Anthony C. Liberto
     * @return String
     */
    public String getTitle() {
        return getString("message.panel");
    }

    /*
    	Listeners
    */
    /**
     * actionPerformed
     * @author Anthony C. Liberto
     * @param _action
     */
    public void actionPerformed(String _action) {
        if (_action.equals("0")) {
            response = 0;
        } else if (_action.equals("1")) {
            response = 1;
            if (isInputPanel()) {
                if (curButtons == OK_CANCEL) {
                    txt.setText(null);
                }
            }
        } else if (_action.equals("2")) {
            response = 2;
        }
        hideDialog();
        return;
    }
    /**
     * createMenu
     * @author Anthony C. Liberto
     */
    protected void createMenu() {
        return;
    }

    /**
     * getMeuBar
     * @author Anthony C. Liberto
     * @return JMenuBar
     */
    public JMenuBar getMenuBar() {
        return null;
    }

    /*
     51039
     */
    /**
     * showMe
     * @author Anthony C. Liberto
     */
    public void showMe() {
        if (isInputPanel()) {
            txt.requestFocus();
        } else {
            if (btn[0] != null) {
                btn[0].requestFocus();
            }
        }
        return;
    }

    /**
     * activateMe
     * @author Anthony C. Liberto
     */
    public void activateMe() {
        if (isInputPanel()) {
            txt.requestFocus();
        } else {
            if (btn[0] != null) {
                btn[0].requestFocus();
            }
        }
        return;
    }

    /*
     cr_4215
     */
    /**
     * getResponse
     * @param _pref
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public int getResponse(String _pref, int[] _i) {
        if (curButtons == YES_NO_SHOW) {
            if (chkDoNotDisplay.isSelected()) {
                setPrefInt(_pref, _i[response]);
            }
        }
        return response;
    }

    /*
     acl20040923

     */
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
    }

    /**
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyPressed(KeyEvent kea) {
        if (kea.getKeyCode() == KeyEvent.VK_ENTER) {
            actionPerformed("0");
        } else if (kea.getKeyCode() == KeyEvent.VK_ESCAPE) {
            actionPerformed("1");
        }
        return;
    }

    /**
     * @see java.awt.Component#requestFocus()
     * @author Anthony C. Liberto
     */
    public void requestFocus() {
        showMe();
        return;
    }

    /**
     * @see javax.swing.JComponent#grabFocus()
     * @author Anthony C. Liberto
     */
    public void grabFocus() {
        if (isInputPanel()) {
            txt.grabFocus();
        } else {
            if (btn[0] != null) {
                btn[0].grabFocus();
            }
        }
        return;
    }

    private void initTextField() {
        boolean bInput = false;
        txt.setText("");
        txt.setCaretPosition(0);
        bInput = isInputPanel();
        txt.setEnabled(bInput);
        txt.setVisible(bInput);
        return;
    }

    /*
     TIR USRO-R-JSTT-67HTKR
     */
    private void initScroll() {
        Dimension dim = null;
        toggleScroll(false);
        msgArea.setEditable(false);
        msgArea.setBackground(getBackground());
        msgArea.setBorder(null);
        dim = new Dimension(500, 400);
        msgScroll.setSize(dim);
        msgScroll.setPreferredSize(dim);
        return;
    }

    private void dereferenceScroll() {
        if (msgArea != null) {
            msgArea.setText("");
            msgArea = null;
        }

        if (msgScroll != null) {
            msgScroll.dereference();
            msgScroll = null;
        }
        return;
    }

    private void toggleScroll(boolean _b) {
        msgScroll.setEnabled(_b);
        msgScroll.setVisible(_b);
        msgArea.setVisible(_b);
        msg.setVisible(!_b);
        msgArea.setText("");
        msg.setText("");
        return;
    }

    /**
     * construct
     * @param _dialogType
     * @param _msgType
     * @param _buttons
     * @param _message
     * @author Anthony C. Liberto
     */
    public void construct(int _dialogType, int _msgType, int _buttons, String _message) {
		StringTokenizer st = new StringTokenizer(_message,RETURN);							//MN_29256135
		if (st.countTokens() >= 15) {														//MN_29256135
	        toggleScroll(true);																//MN_29256135
	        msgArea.setText(Routines.splitString(_message, 75));							//MN_29256135
	        msgArea.setCaretPosition(0);													//MN_29256135
		} else {																			//MN_29256135
			toggleScroll(false);															//MN_29256135
	        setMessage(_message);															//MN_29256135
		}																					//MN_29256135
//MN_29256135		toggleScroll(false);
		setType(_dialogType);
		setIcon(_msgType);
		setButtons(_buttons);
//MN_29256135		setMessage(_message);
        getAccessibleContext().setAccessibleDescription(_message);							//MN_29256135
        revalidate();
        return;
    }

    /**
     * constructException
     * @param _dialogType
     * @param _msgType
     * @param _buttons
     * @param _message
     * @author Anthony C. Liberto
     */
    public void constructException(int _dialogType, int _msgType, int _buttons, String _message) {
        toggleScroll(true);
        setType(_dialogType);
        setIcon(_msgType);
        setButtons(_buttons);
        msgArea.setText(Routines.splitString(_message, 75));//was 100
        msgArea.setCaretPosition(0);
        getAccessibleContext().setAccessibleDescription(_message);
        revalidate();
        return;
    }

	/**
     * use when desire a true type font display
     *
     * @author tony
     * @param _b
     */
	public void setUseTrueTypeFont(boolean _b) {
		msg.setUseTrueTypeFont(_b);
		return;
	}

}
