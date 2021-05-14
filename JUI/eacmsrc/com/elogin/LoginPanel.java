/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: LoginPanel.java,v $
 * Revision 1.9  2012/04/05 17:34:17  wendy
 * jre142 and win7 changes
 *
 * Revision 1.8  2010/11/03 20:49:01  wendy
 * freeuilock if profileset is empty
 *
 * Revision 1.7  2009/09/10 15:04:09  wendy
 * reset UI lock when user logs out
 *
 * Revision 1.6  2009/05/28 13:56:06  wendy
 * Performance cleanup
 *
 * Revision 1.5  2009/03/06 20:07:31  wendy
 * Allow for failed login and release file lock
 *
 * Revision 1.4  2009/02/27 15:02:45  wendy
 * Part of CQ00021335 - login, bp api chgs
 *
 * Revision 1.3  2008/01/30 16:26:59  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.2  2007/08/01 18:39:04  wendy
 * prevent hang
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.4  2006/11/09 18:55:50  tony
 * more monitor stuff.
 *
 * Revision 1.3  2006/11/09 15:51:06  tony
 * more monitor logic
 *
 * Revision 1.2  2005/09/12 19:03:09  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:39  tony
 * This is the initial load of OPICM
 *
 * Revision 1.24  2005/09/08 17:58:56  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.23  2005/05/24 21:49:17  tony
 * wooden stake
 *
 * Revision 1.22  2005/05/24 21:27:57  tony
 * silverBullet
 *
 * Revision 1.21  2005/02/21 17:16:50  tony
 * adjusted versioning logic by separating out
 * versioning logic to improve update functionality.
 *
 * Revision 1.20  2005/02/04 16:57:41  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.19  2005/02/03 16:38:52  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.18  2005/02/02 17:30:22  tony
 * JTest Second Pass
 *
 * Revision 1.17  2005/01/31 20:47:45  tony
 * JTest Second Pass
 *
 * Revision 1.16  2005/01/27 23:18:18  tony
 * JTest Formatting
 *
 * Revision 1.15  2005/01/20 23:15:22  tony
 * repositioned version label
 *
 * Revision 1.14  2005/01/20 23:03:22  tony
 * added version label per dwb
 *
 * Revision 1.13  2004/10/28 20:08:49  tony
 * adjusted sametime login logic.
 *
 * Revision 1.12  2004/10/21 21:28:02  tony
 * updated sametime activation with an arm file.
 *
 * Revision 1.11  2004/10/06 23:02:25  tony
 * updated loc_chooser functionality to properly
 * select the default when running the application.
 *
 * Revision 1.10  2004/09/03 20:25:37  tony
 * accessibility
 *
 * Revision 1.9  2004/08/04 17:49:14  tony
 * adjusted logic to break function parameterization into
 * arm files and a new function directory.  This way
 * we will eliminate the possibility of translation to
 * accidentally change functionality.
 *
 * Revision 1.8  2004/07/29 22:40:31  tony
 * added sametime funcitonality.
 *
 * Revision 1.7  2004/06/10 20:53:17  tony
 * improved location chooser functionality.
 *
 * Revision 1.6  2004/06/09 20:55:53  tony
 * adjusted spacing of layout when location chooser is present.
 *
 * Revision 1.5  2004/06/09 15:48:52  tony
 * location chooser added to application.  It is controlled by
 * a boolean parameter (LOCATION_CHOOSER_ENABLED)
 * in eAccessConstants.
 *
 * Revision 1.4  2004/04/06 18:15:46  tony
 * 53772
 *
 * Revision 1.3  2004/02/27 23:22:50  tony
 * adjusted chat naming when in test.mode
 *
 * Revision 1.2  2004/02/19 21:34:52  tony
 * e-announce1.3
 *
 * Revision 1.1.1.1  2004/02/10 16:59:26  tony
 * This is the initial load of OPICM
 *
 * Revision 1.23  2004/01/20 20:50:41  tony
 * updated logic
 *
 * Revision 1.22  2004/01/20 19:25:51  tony
 * added password logic to the log file.
 *
 * Revision 1.21  2003/10/29 00:22:23  tony
 * removed System.out. statements.
 *
 * Revision 1.20  2003/10/03 20:49:54  tony
 * updated accessibility.
 *
 * Revision 1.19  2003/07/09 17:56:45  tony
 * updated encryption to AES and improved security by
 * basing the key on an encrypted key.  This will mean that
 * an Encrypted key must be resident on the client computer.
 * Additionally changed to 128 bit encryption.
 *
 * Revision 1.18  2003/06/30 21:21:46  tony
 * improved logic and functionality.
 *
 * Revision 1.17  2003/06/30 18:07:21  tony
 * improved functionality for testing by adding functionality
 * to MWObject that includes the default of the userName and
 * overwrite.properties file to use.  This will give the test
 * team more flexibility in defining properties for the application.
 *
 * Revision 1.16  2003/06/26 16:46:04  tony
 * updated cipher logic.
 *
 * Revision 1.15  2003/06/26 15:38:58  tony
 * updated logic to show the password.
 *
 * Revision 1.14  2003/06/25 23:49:13  tony
 * added eCipher which will encrypt Strings.  This will allow
 * for safe replaying of passwords.
 *
 * Revision 1.13  2003/05/30 21:09:17  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.12  2003/05/28 17:04:33  tony
 * updated eSwingworker.
 *
 * Revision 1.11  2003/05/05 22:51:54  tony
 * changed to use default font
 *
 * Revision 1.10  2003/04/25 22:43:22  tony
 * adjusted insets to improve performance.
 *
 * Revision 1.9  2003/04/25 21:27:29  tony
 * adjusted button fonts.
 *
 * Revision 1.8  2003/04/25 19:23:52  tony
 * updated login panel to take advantage of new
 * e-announce splash screen image.
 *
 * Revision 1.7  2003/04/11 20:02:27  tony
 * added copyright statements.
 *
 */
package com.elogin;
import COM.ibm.opicmpdh.middleware.ProfileSet;
import com.ibm.eannounce.eobjects.EComboBox;
import com.ibm.eannounce.epanels.*;
import com.ibm.eannounce.version.Version;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class LoginPanel extends EImagePanel implements Accessible, ActionListener, KeyListener, EAccessConstants {
	private static final long serialVersionUID = 1L;
	private EButton btnLog = null;
	private EButton btnExit = null;
	private EComboBox cmbLoc = null;							//loc_choose

	private EPasswordField epfPass = new EPasswordField(20,64);
	private ELenField elfUser = new ELenField(20,64);

	private ELabel lblPass = null;
	private ELabel lblUser = null;
	private ELabel lblCombo = null;								//loc_choose
	private ELabel lblVers = null;								//dwb_20050119

	private EPanel pnlCenter = null;
	private final static Insets INSETS = new Insets(5,125,0,0);

	/**
     * loginPanel
     * @author Anthony C. Liberto
     */
    protected LoginPanel() {
		super(new BorderLayout(5,5),"splash.jpg",IMAGE_CENTERED);
		getAccessibleContext().setAccessibleName(getString("accessible.intro.login"));
	}

	/**
     * @see java.awt.Container#getInsets()
     * @author Anthony C. Liberto
     */
    public Insets getInsets() {
		return INSETS;
	}

	/**
     * init
     * @author Anthony C. Liberto
     */
    protected void init() {
		initButtons();
		initFields();
		initLabels();
		initComponents(false);
		construct();
	}

	/**
     * initComponents
     * @param _b
     * @author Anthony C. Liberto
     */
    private void initComponents(boolean _b) {
		lblUser.setUseFont(_b);
		lblPass.setUseFont(_b);
		lblCombo.setUseFont(_b);
		elfUser.setUseDefined(false);
		epfPass.setUseDefined(false);
		btnLog.setUseFont(_b);
		btnExit.setUseFont(_b);

		setComponentsOpaque(_b);
		setFont(new Font(DEFAULT_FONT_FACE, Font.BOLD, 14));
		setUseFont(false);
	}

	private void setComponentsOpaque(boolean _b) {
		btnLog.setOpaque(_b);
		btnExit.setOpaque(_b);
//		epfPass.setOpaque(_b);
//		elfUser.setOpaque(_b);
	}

	private void construct() {
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		pnlCenter = new EPanel(gbl);
		pnlCenter.setTransparent(true);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		buildConstraints(gbc,1,1,1,1,0,0);
		gbl.setConstraints(elfUser,gbc);
		pnlCenter.add(elfUser);

		buildConstraints(gbc,1,2,1,1,0,0);
		gbl.setConstraints(epfPass,gbc);
		pnlCenter.add(epfPass);

		if (EAccess.isArmed(LOCATION_CHOOSER_ARM_FILE)) {				//loc_choose
            EPanel pnlButton = null;
            buildConstraints(gbc,1,0,1,1,0,0);							//loc_choose
			gbl.setConstraints(cmbLoc,gbc);								//loc_choose
			pnlCenter.add(cmbLoc);										//loc_choose
			buildConstraints(gbc,0,0,1,1,0,0);							//loc_choose
			gbl.setConstraints(lblCombo,gbc);							//loc_choose
			pnlCenter.add(lblCombo);									//loc_choose
			pnlButton = new EPanel(new BorderLayout());			//loc_choose
			pnlButton.setTransparent(true);								//loc_choose
			pnlButton.add("West",btnLog);								//loc_choose
			pnlButton.add("East",btnExit);								//loc_choose
			buildConstraints(gbc,1,3,1,1,0,0);							//loc_choose
			gbl.setConstraints(pnlButton,gbc);							//loc_choose
			pnlCenter.add(pnlButton);									//loc_choose
			btnExit.setPreferredSize(btnLog.getPreferredSize());		//loc_choose
		} else {														//loc_choose
			buildConstraints(gbc,0,3,1,1,0,0);
			gbl.setConstraints(btnLog,gbc);
			pnlCenter.add(btnLog);
			buildConstraints(gbc,0,4,1,1,0,0);
			gbl.setConstraints(btnExit,gbc);
			pnlCenter.add(btnExit);
		}																//loc_choose


		buildConstraints(gbc,0,1,1,1,0,0);
		gbl.setConstraints(lblUser,gbc);
		pnlCenter.add(lblUser);

		buildConstraints(gbc,0,2,1,1,0,0);
		gbl.setConstraints(lblPass,gbc);
		pnlCenter.add(lblPass);

		add("North",pnlCenter);
		buildConstraints(gbc,1,5,1,1,0,0);								//dwb_20050119
		gbl.setConstraints(lblVers,gbc);								//dwb_20050119
		pnlCenter.add(lblVers);											//dwb_20050119
		pack();
	}

	private void buildConstraints(GridBagConstraints _gbc, int _gx, int _gy, int _gw, int _gh, int _wx, int _wy) {
		_gbc.gridx = _gx;
		_gbc.gridy = _gy;
		_gbc.gridwidth = _gw;
		_gbc.gridheight = _gh;
		_gbc.weightx = _wx;
		_gbc.weighty = _wy;
	}

	private void initButtons() {
		btnLog = new EButton(getString("login"));
		btnLog.addActionListener(this);
		btnLog.setMnemonic(getChar("login-s"));
		btnLog.setToolTipText(getString("login-t"));
		btnLog.setActionCommand("login");
		btnLog.setEnabled(false);
		btnExit = new EButton(getString("exit"));
		btnExit.addActionListener(this);
		btnExit.setMnemonic(getChar("exit-s"));
		btnExit.setToolTipText(getString("exit-t"));
		btnExit.setActionCommand("exit");
	}

	/**
     * removeButtons
     * @author Anthony C. Liberto
     */
    private void removeButtons() {
		btnLog.removeActionListener(this);
		btnLog.dereference();
		btnLog = null;
		btnExit.removeActionListener(this);
		btnExit.dereference();
		btnExit = null;
	}

	private void initFields() {
		String sUserName = System.getProperty(MW_USER);
		String mwPass = null;
        if (!Routines.have(sUserName)) {
			sUserName = eaccess().getPrefString(EANNOUNCE_SAVED_USERNAME,"");
		}
		elfUser.setText(sUserName);
		mwPass = System.getProperty(MW_PASS);
		if (Routines.have(mwPass)) {
			String sPass = COM.ibm.opicmpdh.transactions.Cipher.codec(mwPass);
			epfPass.setText(sPass);
			login(sPass,false);
		} else if (EAccess.isArmed(AUTO_LOGIN_ARM_FILE)) {
			Object oPass = eaccess().getPrefObject(EANNOUNCE_SAVED_PASSWORD + sUserName);
			if (oPass instanceof String) {
				String sPass = COM.ibm.opicmpdh.transactions.Cipher.codec(oPass.toString());
				if (Routines.have(sPass)) {
					epfPass.setText(sPass);
					login(sPass,false);
				}
			}
		}
		epfPass.addKeyListener(this);
		elfUser.addKeyListener(this);


		loadLocationChooser();										//loc_choose
	}

	private void removeFields() {
		epfPass.removeKeyListener(this);
		epfPass.dereference();
		epfPass = null;
		elfUser.removeKeyListener(this);
		elfUser.dereference();
		elfUser = null;
		cmbLoc = null;												//loc_choose
	}

	private void initLabels() {
		lblPass = new ELabel(getString("password"));
		lblPass.setLabelFor(epfPass);
		lblUser = new ELabel(getString("user"));
		lblUser.setLabelFor(elfUser);

		lblPass.setDisplayedMnemonic(getChar("password-s"));
		lblUser.setDisplayedMnemonic(getChar("user-s"));

        lblCombo = new ELabel(getString("choLoc") + "  ");			//loc_choose
        lblCombo.setLabelFor(cmbLoc);								//loc_choose
        lblCombo.setDisplayedMnemonic(getChar("choLoc-s"));			//loc_choose

        lblVers = new ELabel(RETURN + Version.getVersion()); 					//dwb_20050119
        lblVers.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);	//dwb_20050119
	}

	private void removeLabels() {
		lblPass.dereference();
		lblPass = null;
		lblUser.dereference();
		lblUser = null;
		lblCombo.dereference();										//loc_choose
		lblCombo = null;											//loc_choose
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		removeLabels();
		removeFields();
		removeButtons();
		pnlCenter.dereference();
		pnlCenter = null;
		removeAll();
		removeNotify();
		return;
	}

	/**
     * getString
     * @author Anthony C. Liberto
     * @return String
     * @param _code
     */
    public String getString(String _code) {
		return eaccess().getString(_code);
	}

	/**
     * getChar
     * @author Anthony C. Liberto
     * @return char
     * @param _code
     */
    public char getChar(String _code) {
		return eaccess().getChar(_code);
	}

	private void login() {
		login(new String(epfPass.getPassword()),true);
	}

	private void login(final String _sPass, final boolean _savePass) {
		final ESwingWorker myWorker = new ESwingWorker() {
			public Object construct() {
				try{
					String sUserName = elfUser.getText();
					if (Routines.have(sUserName)) {
						if (Routines.have(_sPass))  {
							elfUser.setEnabled(false);
							epfPass.setEnabled(false);
							setModalBusy(true);
							if (EAccess.isArmed(LOCATION_CHOOSER_ARM_FILE)) {				//loc_choose
								if (!eaccess().connectMiddleware(getSelectedLocation())) {	//loc_choose
									return null;											//loc_choose
								}															//loc_choose
							}
							String location = "";
							MWObject mwo = getSelectedLocation();
							if (mwo != null){
								location =mwo.getName()+mwo.getIP()+mwo.getPort();
							}

							// check to see if this user has a UI instance for this location already
							if (EAccess.isAlreadyRunning(LoginPanel.this,sUserName,location)){
								return null;
							}

							Object obj = eaccess().getTBase().login(sUserName,_sPass,EANNOUNCE_TOKEN, null);
							if (obj==null){
								EAccess.freeUILock(sUserName, location);
							}else if (obj instanceof ProfileSet){
								if (((ProfileSet)obj).isEmpty()) {
								 	EAccess.freeUILock(sUserName, location);
								}
							}
							return obj;
						}
					}
				}catch(Exception ex){ // prevent hang
					appendLog("Exception in LoginPanel.login.ESwingWorker.construct() "+ex);
					ex.printStackTrace();
					setModalBusy(false);
				}

				return null;
			}

			public void finished() {
				Object o = getValue();
				String sPass = null;
                if (o == null) {
					elfUser.setEnabled(true);
					epfPass.setEnabled(true);
				} else if (o instanceof ProfileSet) {
					String sUser = elfUser.getText();
					if (EAccess.isTestMode()) {
						eaccess().setUserName(System.getProperty("user.name"));
					} else {
						eaccess().setUserName(sUser);
					}
					eaccess().setPrefString(EANNOUNCE_SAVED_USERNAME,sUser);		//53772
					sPass = new String(epfPass.getPassword());
					if (EAccess.isArmed(AUTO_LOGIN_ARM_FILE) && _savePass) {
						eaccess().setPrefObject(EANNOUNCE_SAVED_PASSWORD + sUser,
								COM.ibm.opicmpdh.transactions.Cipher.codec(sPass));
					}
					if (EAccess.isArmed(SAMETIME_ARM_FILE) || EAccess.isArmed(BROADCAST_ARM_FILE)) {
						eaccess().loginSametime(null,sUser,sPass);
					}
					setProfileSet((ProfileSet)o);
					
					if (eaccess().isMonitor()) {
						eaccess().monitor("login " + sUser,new Date());
					}
				}
				setWorker(null);
				setModalBusy(false);
			}
		};
		myWorker.setInterruptable(false);
		setWorker(myWorker);
	}

	/**
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyTyped(KeyEvent _ke) {}
	/**
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyReleased(KeyEvent _ke) {
		adjustEnabled();
	}

	/**
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyPressed(KeyEvent _ke) {
		Object src = _ke.getSource();
		if (_ke.getKeyCode() == KeyEvent.VK_ENTER) {
			if (src == elfUser) {
				String sPass = new String(epfPass.getPassword());
				if (Routines.have(sPass)) {
					login();
//					btnLog.doClick();
				} else {
					epfPass.grabFocus();
				}
			} else if (src == epfPass) {
				if (Routines.have(elfUser.getText())) {
					btnLog.requestFocus();
//					btnLog.doClick();
					login();
				} else {
					elfUser.grabFocus();
				}
			}
			_ke.consume();
		}
	}

	private void adjustEnabled() {
		String strPass = new String(epfPass.getPassword());
		if (Routines.have(elfUser.getText()) && Routines.have(strPass)) {
			btnLog.setEnabled(true);
			repaint();
		} else {
			btnLog.setEnabled(false);
			repaint();
		}
	}

	/**
     * actionPerformed
     * @author Anthony C. Liberto
     * @param _ae
     */
    public void actionPerformed(ActionEvent _ae) {
		String action = _ae.getActionCommand();
		if (action.equals("login")) {
			login();
		} else if (action.equals("exit")) {
			eaccess().exit("exit login");
		}
	}

	/**
     * @see java.awt.Component#requestFocus()
     * @author Anthony C. Liberto
     */
    public void requestFocus() {
		super.requestFocus();
		if (Routines.have(elfUser.getText())) {
			epfPass.requestFocus();
		} else {
			elfUser.requestFocus();
		}
	}

	/**
     * setProfileSet
     * @param _ps
     * @author Anthony C. Liberto
     */
    private void setProfileSet(ProfileSet _ps) {
		if (_ps.isEmpty()) {
			eaccess().setCode("p.s.e");
			eaccess().showError(this);
			epfPass.setEnabled(true);
			elfUser.setEnabled(true);
		}else{
			eaccess().getLogin().process(_ps);
		}
	}
/*
	private Cursor getPanelCursor() {
		return eaccess().getCursor();
	}
*/
	/**
     * reset
     * @author Anthony C. Liberto
     */
    protected void reset() {
    	// user is logging out, reset UI lock
    	String sUserName = elfUser.getText();
    	String location = "";
    	MWObject mwo = getSelectedLocation();
    	if (mwo != null){
    		location =mwo.getName()+mwo.getIP()+mwo.getPort();
    	}
    	EAccess.freeUILock(sUserName, location);
    	
		if (epfPass != null) {
			epfPass.setText("");
			epfPass.setEnabled(true);
		}
		if (elfUser != null) {
			elfUser.setText("");
			elfUser.setEnabled(true);
		}
		if (btnLog != null) {
			btnLog.setEnabled(false);
		}
	}

	/**
     * getPanelType
     * @author Anthony C. Liberto
     * @return String
     */
    public String getPanelType() {
		return TYPE_LOGINPANEL;
	}

/*
 loc_choose
 */
/*
	at java.lang.Thread.dumpStack(Thread.java:1101)
	at com.eLogin.thinBase.connect(thinBase.java:310)
	at com.eLogin.thinBase.initiateRMI(thinBase.java:294)
	at com.eLogin.thinBase.RMIControl(thinBase.java:261)
	at com.eLogin.thinBase.databaseConnect(thinBase.java:246)
	at com.eLogin.eAccess.connect(eAccess.java:1131)
	at com.eLogin.eLogin.connect(eLogin.java:733)
	at com.eLogin.eLogin.initLogin(eLogin.java:424)
	at com.eLogin.eLogin.main(eLogin.java:762)
*/

    private void loadLocationChooser() {
		MWParser mwp = null;
        if (cmbLoc == null) {
			cmbLoc = new EComboBox();
			cmbLoc.setFont(this.getFont());
		} else {
			cmbLoc.removeAll();
		}
		mwp = eaccess().getMWParser();
		if (mwp != null) {
			if (cmbLoc != null) {
				MWObject[] obj = mwp.getMWObjects();
				if (obj != null) {
					int ii = obj.length;
					for (int i=0;i<ii;++i) {
						cmbLoc.addItem(obj[i]);
					}
				}
				cmbLoc.setSelectedItem(mwp.getCurrent());
			}
		}
	}

	/**
     * getSelectedLocation
     * @return
     * @author Anthony C. Liberto
     */
    private MWObject getSelectedLocation() {
		if (cmbLoc != null) {
			return (MWObject)cmbLoc.getSelectedItem();
		}
		return null;
	}
}
