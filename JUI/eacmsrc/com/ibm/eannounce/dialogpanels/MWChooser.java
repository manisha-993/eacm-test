/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2003/04/22
 * @author Anthony C. Liberto
 *
 * $Log: MWChooser.java,v $
 * Revision 1.2  2008/01/30 16:26:53  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:43:24  wendy
 * Reorganized JUI module
 *
 * Revision 1.7  2005/09/21 17:59:21  tony
 * fixed logic
 *
 * Revision 1.6  2005/09/21 17:43:04  tony
 * adjusted logic to prevent error on cancel
 *
 * Revision 1.5  2005/09/20 20:45:11  tony
 * CR approved
 *
 * Revision 1.4  2005/09/20 16:41:18  tony
 * Enhanced logic and added error messaging
 *
 * Revision 1.3  2005/09/20 16:01:59  tony
 * CR092005410
 * Ability to add middleware location on the fly.
 *
 * Revision 1.2  2005/09/12 19:03:10  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2005/09/08 17:59:00  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.7  2005/02/04 15:22:08  tony
 * JTest Format Third Pass
 *
 * Revision 1.6  2005/02/02 17:30:21  tony
 * JTest Second Pass
 *
 * Revision 1.5  2005/01/27 23:18:20  tony
 * JTest Formatting
 *
 * Revision 1.4  2004/09/03 20:26:43  tony
 * accessibility
 *
 * Revision 1.3  2004/08/23 21:38:08  tony
 * TIR USRO-R-RLON-645P76
 *
 * Revision 1.2  2004/02/19 21:34:53  tony
 * e-announce1.3
 *
 * Revision 1.1.1.1  2004/02/10 16:59:28  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2004/01/14 18:47:57  tony
 * acl_20040114
 *   1)  updated logic to allow for manual load of serial pref.
 *   2)  trigger a middleware selection if no default middleware is defined.
 *   3)  prevent a put of parent when non new row is selected.
 *
 * Revision 1.8  2003/12/01 17:45:12  tony
 * accessibility
 *
 * Revision 1.7  2003/09/18 17:29:34  tony
 * 52308
 *
 * Revision 1.6  2003/09/16 22:34:14  tony
 * 52281
 *
 * Revision 1.5  2003/06/30 22:29:19  tony
 * updated mwchooser to display new
 * middleware_client_properties information.
 *
 * Revision 1.4  2003/05/06 00:07:19  tony
 * 50468
 *
 * Revision 1.3  2003/04/25 22:43:44  tony
 * removed menu logic.
 *
 * Revision 1.2  2003/04/25 19:24:21  tony
 * adjusted standAlone logic.
 *
 * Revision 1.1  2003/04/22 16:38:21  tony
 * created MWChooser to update handling of default
 * middlewareLocation.
 *
 *
 */
package com.ibm.eannounce.dialogpanels;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import java.awt.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class MWChooser extends AccessibleDialogPanel {
	private static final long serialVersionUID = 1L;
	private EPanel mwDisPnl = new EPanel(new GridLayout(8, 2, 5, 5));
    private EPanel btnPnl = new EPanel(new BorderLayout(5, 5));
    private EPanel btnPnlWest = new EPanel(new BorderLayout(5,5));			//CR092005410

    private EButton bOk = new EButton(getString("save.pref"));
    private EButton bAdd = new EButton(getString("mw.add"));				//CR092005410
    private EButton bReset = new EButton(getString("reset.pref"));

    private MWObject mwObj = null;
    private EComboBox mwCombo = new EComboBox();
    private MWParser mwParse = null;

    private JLabel lblName0 = new JLabel(getString("mw.name"));
    private JLabel lblName1 = new JLabel();
    private JLabel lblIP0 = new JLabel(getString("mw.ip"));
    private JLabel lblIP1 = new JLabel();
    private JLabel lblPort0 = new JLabel(getString("mw.port"));
    private JLabel lblPort1 = new JLabel();

    private JLabel lblChatIP0 = new JLabel(getString("mw.chat.ip"));
    private JLabel lblChatIP1 = new JLabel();
    private JLabel lblChatPort0 = new JLabel(getString("mw.chat.port"));
    private JLabel lblChatPort1 = new JLabel();

    private JLabel lblReport0 = new JLabel(getString("mw.report"));
    private JLabel lblReport1 = new JLabel();
    private JLabel lblUser0 = new JLabel(getString("mw.user"));
    private JLabel lblUser1 = new JLabel();
    private JLabel lblProperty0 = new JLabel(getString("mw.property"));
    private JLabel lblProperty1 = new JLabel();

    private boolean bStandAlone = false;
    private boolean bBusy = false; //52308

    /**
     * mwChooser
     * @author Anthony C. Liberto
     */
    public MWChooser() {
        super(new BorderLayout());
        mwParse = eaccess().getMWParser();
        init();
        return;
    }

    /**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
        initComponents(); //50468
        loadMW();
        mwCombo.addActionListener(this);
        mwCombo.setActionCommand("mwCombo");
        mwDisPnl.add(lblName0);
        lblName0.setLabelFor(lblName1); //access
        mwDisPnl.add(lblName1);
        mwDisPnl.add(lblIP0);
        lblIP0.setLabelFor(lblIP1); //access
        mwDisPnl.add(lblIP1);
        mwDisPnl.add(lblPort0);
        lblPort0.setLabelFor(lblPort1); //access
        mwDisPnl.add(lblPort1);

        mwDisPnl.add(lblChatIP0);
        lblChatIP0.setLabelFor(lblChatIP1);
        mwDisPnl.add(lblChatIP1);
        mwDisPnl.add(lblChatPort0);
        lblChatPort0.setLabelFor(lblChatPort1);
        mwDisPnl.add(lblChatPort1);

        mwDisPnl.add(lblReport0);
        lblReport0.setLabelFor(lblReport1); //access
        mwDisPnl.add(lblReport1);
        mwDisPnl.add(lblUser0);
        lblUser0.setLabelFor(lblUser1); //access
        mwDisPnl.add(lblUser1);
        mwDisPnl.add(lblProperty0);
        lblProperty0.setLabelFor(lblProperty1); //access
        mwDisPnl.add(lblProperty1);

        //		lblName1.setForeground(getPrefColor(PREF_COLOR_FOREGROUND,Color.black));
        //		lblIP1.setForeground(getPrefColor(PREF_COLOR_FOREGROUND,Color.black));
        //		lblPort1.setForeground(getPrefColor(PREF_COLOR_FOREGROUND,Color.black));

//CR092005410        btnPnl.add("West", bOk);
        btnPnl.add("East", bReset);
        btnPnlWest.add("West", bOk);								//CR092005410
        btnPnl.add("West",btnPnlWest);								//CR092005410

        bOk.addActionListener(this);
        bReset.addActionListener(this);
        bOk.setActionCommand("mwSave");
        bReset.setActionCommand("mwReset");
        bOk.setMnemonic(getChar("save.pref-s"));
        bReset.setMnemonic(getChar("reset.pref-s"));

        btnPnlWest.add("East", bAdd);								//CR092005410
        bAdd.addActionListener(this);								//CR092005410
        bAdd.setActionCommand("mwAdd");								//CR092005410
		bAdd.setMnemonic(getChar("mw.add-s"));						//CR092005410

        add("North", mwCombo);
        add("Center", mwDisPnl);
        add("South", btnPnl);
        setModalCursor(true); //52308

        return;
    }

    /**
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        super.refreshAppearance();
        //52281		mwCombo.refreshAppearance();
        return;
    }

    /**
     * dereferencePartial
     * @author Anthony C. Liberto
     */
    public void dereferencePartial() {
        bOk.removeActionListener(this);
        bReset.removeActionListener(this);
        mwCombo.removeActionListener(this);
        bAdd.removeActionListener(this);							//CR092005410
        return;
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        dereferencePartial();
        removeAll();
        removeNotify();
        return;
    }

    private void loadMW() {
        MWObject[] tmpMWObj = mwParse.getMWObjects();
        int ii = tmpMWObj.length;
        for (int i = 0; i < ii; ++i) {
            mwCombo.addItem(tmpMWObj[i]);
        }
        return;
    }

    private void setMiddlewareDefault() {
        String s = getPrefString(mwParse.getKey(), null);
        MWObject mwo = null;
        if (EAccess.isArmed(LOCATION_CHOOSER_ARM_FILE)) { //TIR USRO-R-RLON-645P76
            mwo = eaccess().getCurrentMiddlewareObject(); //TIR USRO-R-RLON-645P76
        } else if (s != null) {
            mwo = mwParse.getCurrent(s);
        } else {
            mwo = mwParse.getDefault();
        }
        mwCombo.setSelectedItem(mwo); //acl_20040114
        if (mwo != null) {
            //acl_20040114			mwCombo.setSelectedItem(mwo);
            loadMWObject(mwo);
        }
        return;
    }

    /**
     * setType
     * @param _standAlone
     * @author Anthony C. Liberto
     */
    public void setType(boolean _standAlone) {
        if (!_standAlone) {
            setMiddlewareDefault();
        }
        bReset.setEnabled(!_standAlone);
        bAdd.setVisible(!_standAlone);					//CR092005410
        bAdd.setEnabled(!_standAlone);					//CR092005410
        bStandAlone = _standAlone;
        return;
    }

    private void loadMWObject(MWObject _obj) {
        mwObj = _obj;
        if (_obj == null) {
            lblName1.setText("");
            lblIP1.setText("");
            lblPort1.setText("");
            lblChatIP1.setText("");
            lblChatPort1.setText("");
            lblReport1.setText("");
            lblUser1.setText("");
            lblProperty1.setText("");
        } else {
            lblName1.setText(_obj.getName());
            lblIP1.setText(_obj.getIP());
            lblPort1.setText(Integer.toString(_obj.getPort()));
            lblChatIP1.setText(_obj.getChatIP());
            lblChatPort1.setText(Integer.toString(_obj.getChatPort()));
            lblReport1.setText(_obj.getReportPrefix());
            lblUser1.setText(_obj.getUserName());
            lblProperty1.setText(_obj.getPropertyFile());
        }
        return;
    }

    /**
     * getMiddleware
     * @return
     * @author Anthony C. Liberto
     */
    public MWObject getMiddleware() {
        return mwObj;
    }

    /**
     * showMe
     * @author Anthony C. Liberto
     */
    public void showMe() {
        mwObj = null;
        setMiddlewareDefault();
        return;
    }

    /**
     * getKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getKey() {
        return mwParse.getKey();
    }

    /**
     * actionPerformed
     * @author Anthony C. Liberto
     * @param _action
     */
    public void actionPerformed(String _action) {
        if (isModalBusy()) {
            return;
        }
        setModalBusy(true);
        if (_action.equals("mwCombo")) {
            Object o = mwCombo.getSelectedItem();
            if (o instanceof MWObject) {
                loadMWObject((MWObject) o);
            }
            setModalBusy(false);
            return;
        } else if (_action.equals("mwSave")) {
            MWObject o = getMiddleware();
            if (o != null) {
                setPrefString(getKey(), o.key());
                if (!bStandAlone) {
                    fyiMsg("msg11017.0");
                }
            }
        } else if (_action.equals("mwReset")) {
            clearPref(getKey(), true);
            if (!bStandAlone) {
                fyiMsg("msg11017.0");
            }
        } else if (_action.equals("mwAdd")) {						//CR092005410
			addMWLocation();										//CR092005410
		}
        setModalBusy(false);
        if (bStandAlone) {
            disposeDialog();
        }
        return;
    }

    /**
     * getMenuBar
     * @author Anthony C. Liberto
     * @return JMenuBar
     */
    public JMenuBar getMenuBar() {
        return null;
    }

    /**
     * createMenu
     * @author Anthony C. Liberto
     */
    protected void createMenu() {
    }
    /**
     * removeMenu
     * @author Anthony C. Liberto
     */
    protected void removeMenu() {
    }

    private void fyiMsg(String _s) {
        eaccess().showFYI((Window) getParentDialog(), _s);
        return;
    }

    /*
    50468
    */
    private void initComponents() {
        bOk.setUseDefined(false);
        bReset.setUseDefined(false);
        bAdd.setUseDefined(false);				//CR092005410
        mwCombo.setUseDefined(false);
        return;
    }

    /*
     52308
     */

    /**
     * isModalBusy
     * @author Anthony C. Liberto
     * @return boolean
     */
    public boolean isModalBusy() {
        return bBusy;
    }

    /**
     * setModalBusy
     * @author Anthony C. Liberto
     * @param _b
     */
    public void setModalBusy(boolean _b) {
        bBusy = _b;
        return;
    }

    /**
     * getCursor
     * @author Anthony C. Liberto
     * @return Cursor
     */
    public Cursor getCursor() {
        if (isModalBusy()) {
            Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
        }
        return Cursor.getDefaultCursor();
    }

	/**
	 * addMWLocation
	 * CR092005410
	 * @author tony
	 */
	protected void addMWLocation() {
		 String sLocation = eaccess().showInput("mw.add.command", null, this);
		 addMWLocation(sLocation);
		 return;
	}

	/**
	 * addMWLocation
	 * CR092005410
	 * @param _s
	 * @author tony
	 */
	protected void addMWLocation(String _s) {
		if (isValidMWLocation(_s)) {
			mwParse.addLocation(_s);
			loadMW();
		}
		return;
	}

	/**
	 * isValidMWLocation
	 * CR092005410
	 * @param _s
	 * @return
	 * @author tony
	 */
	protected boolean isValidMWLocation(String _s) {
		if (mwParse != null) {
			if (Routines.have(_s)) {
				return mwParse.isValidXML(this,_s);
			}
		}
		return false;
	}
}
