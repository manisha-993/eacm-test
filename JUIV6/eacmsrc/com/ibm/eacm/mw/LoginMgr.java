//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.


package com.ibm.eacm.mw;

import COM.ibm.opicmpdh.middleware.LoginException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ProfileSet;
import COM.ibm.opicmpdh.middleware.VersionException;
import COM.ibm.opicmpdh.objects.Blob;
import COM.ibm.opicmpdh.transactions.NLSItem;

import com.ibm.eannounce.version.Version;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import com.ibm.eacm.*;
import com.ibm.eacm.objects.DateRoutines;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.objects.SerialPref;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.preference.*;
import com.ibm.eacm.ui.SWProgressDialog;
import com.ibm.eacm.actions.EACMAction;
import COM.ibm.eannounce.objects.ObjectPool;


/**
 * This class manages login and middleware locations. It maintains the MWParser with all
 * middleware locations
 * @author Wendy Stimpson
 */
//$Log: LoginMgr.java,v $
//Revision 1.7  2014/01/24 12:47:12  wendy
//getro() throw remoteexception to allow reconnect
//
//Revision 1.6  2013/08/06 20:38:42  wendy
//change log level for setactiveprofile
//
//Revision 1.5  2013/05/03 19:11:38  wendy
//save mwloc if 'please select' and locchooser.arm exist
//
//Revision 1.4  2013/03/14 17:33:15  wendy
//try to reconnect when getting new profile if there is a connectionexception
//
//Revision 1.3  2013/01/25 20:39:43  wendy
//Add error title
//
//Revision 1.2  2012/11/09 20:48:00  wendy
//check for null profile from getNewProfileInstance()
//
//Revision 1.1  2012/09/27 19:39:22  wendy
//Initial code
//
public class LoginMgr extends JPanel implements KeyListener, EACMGlobals
{
	private static final long serialVersionUID = 1L;

	private static Hashtable<String, UILock> uiInstanceTbl = new Hashtable<String, UILock>(); // support lock removal of specific instance

	private final static Insets INSETS = new Insets(5,125,0,0);
	private static MWParser mwParse = null;

	/**
	 * if this exists, user can open multiple instances of the UI
	 */
	private static final String MULTIPLE_UI_ARM_FILE = "multipleUI"+ARM_EXTENSION;
	/**
	 * saved user name
	 */
	private static final String EANNOUNCE_SAVED_USERNAME = "eannounce.user.name";

	private JButton btnLog = null;
	private JButton btnExit = null;
	private JComboBox cmbLoc = null;
	private boolean initialMwSelection = false;

	private JPasswordField epfPass = new JPasswordField(20);
	private JTextField elfUser = null;

	private JLabel lblPass = null;
	private JLabel lblUser = null;
	private JLabel lblCombo = null;
	private JLabel lblVers = null;

	private JPanel pnlCenter = null;
	private Image image = null;
	private LoginWorker loginWorker = null;
	private ProfileSet profileSet = null;
	private Profile activeProf = null;

	public static MWParser getMWParser() {
		if (mwParse == null) {
			try {
				mwParse = new MWParser();
			} catch (IOException e) {
				com.ibm.eacm.ui.UI.showException(null,e);
				System.exit(-4); // shut down, cant do anything
			}
		}
		return mwParse;
	}
	public ProfileSet getProfileSet() {
		return profileSet;
	}

	/**
	 * this is called when a reportaction is executed, JuiReportActionObject() needs a ProfileSet
	 * make the specified profile the 'active' profile in the set
	 *
	 * @param _prof
	 * @return
	 */
	public ProfileSet updateActiveProfile(Profile _prof) {
		if (_prof != null) {
			for (int i = 0; i < profileSet.size(); ++i) {
				Profile prof = profileSet.elementAt(i);
				if (prof.getEnterprise().equals(_prof.getEnterprise()) &&
						prof.getOPWGID() == _prof.getOPWGID()) {
					profileSet.setActiveProfile(prof);
					NLSItem langRead = _prof.getReadLanguage();
					if (langRead != null) {
						prof.setReadLanguage(langRead);
					}
					NLSItem langWrite = _prof.getWriteLanguage();
					if (langWrite != null) {
						prof.setWriteLanguage(langWrite);
					}
					break;
				}
			}
		}
		return profileSet;
	}

	/**
	 * get any profile saved as a preference
	 *
	 * @return
	 */
	public Profile getDefaultProfile() {
		if (profileSet != null) {
			Profile[] prof = profileSet.toArray();
			if (prof.length == 1) {
				return prof[0]; // there is only one, use it
			}
			if (prof.length > 0) {
				String defEnterprise = ProfilePref.getDefaultProfileEnterprise(prof[0].getOPName());
				int defOpwgid = ProfilePref.getDefaultProfileOPWGID(prof[0].getOPName());
				if (defEnterprise!=null){
					for (int i = 0; i < prof.length; ++i) {
						// match saved profile to one in profileset
						if (defOpwgid == prof[i].getOPWGID() &&
								defEnterprise.equals(prof[i].getEnterprise())) {
							return prof[i];
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * get the profile used for wg or currently selected tab, tab may return null for getProfile() if the
	 * entitylist is derefd
	 * @return
	 */
	public synchronized Profile getActiveProfile(){
		return activeProf;
	}
	/**
	 * @param prof
	 */
	public synchronized void setActiveProfile(Profile prof) {
		if (prof != null && !prof.equals(activeProf)) {
			// loggers need this put into separate lines
			String profinfoArray[] = Routines.splitString(Utils.getProfileInfo(prof), NEWLINE);
			for(int i=0;i<profinfoArray.length; i++){
				RMIMgr.logger.log(Level.FINE, profinfoArray[i]);
			}
		}

		if (activeProf != null && prof != null) {
			String ent1 = activeProf.getEnterprise();
			String ent2 = prof.getEnterprise();
			// clear all saved ActionList and EANActionItem when enterprise changes
			if (!ent1.equals(ent2)) {
				ObjectPool.getInstance().clear();
			}
		}

		activeProf = prof;
		EACM.getEACM().setStatus(activeProf);
	}

	/************************************
	 * Enforce a single instance of the JUI unless 'multipleUI.arm' exists
	 * @param _c
	 * @param sUserName
	 * @param location
	 * @return
	 */
	private boolean isAlreadyRunning(String sUserName, String location){
		boolean isrunning = false;
		if (Utils.isArmed(MULTIPLE_UI_ARM_FILE)) {
			// allow multiple instances of UI to run
			RMIMgr.logger.log(Level.CONFIG,"bypassing UI instance checks");
		}else{
			String uiInstance = UILock.getLockFileName(sUserName, location);
			UILock ua = new UILock(uiInstance);
			isrunning = ua.isAppActive();
			if (isrunning) {
				//msg5026.0 = EACM is already running.
				com.ibm.eacm.ui.UI.showMessage(this,"mw.connect.err-title", JOptionPane.ERROR_MESSAGE, "information-acc",
						Utils.getResource("msg5026.0"));
			}else{
				uiInstanceTbl.put(uiInstance, ua);
			}
		}

		return isrunning;
	}
	/************************************
	 * Release the lock, needed if login fails or user logs out
	 * @param sUserName
	 * @param location
	 */
	private void freeUILock(String sUserName, String location){
		String uiInstance = UILock.getLockFileName(sUserName, location);
		UILock ua = (UILock)uiInstanceTbl.get(uiInstance);
		if (ua!=null){
			ua.forceRelease();
		}
	}

	/**
	 * LoginMgr constructor
	 *
	 */
	public LoginMgr(Action exitAction, boolean showCombo) {
		super(new BorderLayout(5,5));
		loadImage();
		getAccessibleContext().setAccessibleName(Utils.getResource("accessible.intro.login"));
		initButtons(exitAction);
		initFields();
		initLabels();
		construct(showCombo);
	}

	private void initButtons(Action exitAction) {
		btnLog = new JButton(new LoginAction()){
			private static final long serialVersionUID = 1L;
			public Font getFont() { // do not pick up user overrides
				/*Font font = UIManager.getLookAndFeelDefaults().getFont("Button.font");
				if (font==null){
					font = super.getFont();
				}*/

				return FontPref.DEFAULT_FONT;
			}
			public Color getForeground(){
				return ColorPref.DEFAULT_COLOR_ENABLED_FOREGROUND;
			}
		};
		btnLog.getAccessibleContext().setAccessibleDescription(
				(String)btnLog.getAction().getValue(Action.SHORT_DESCRIPTION));
		btnExit = new JButton(exitAction){
			private static final long serialVersionUID = 1L;
			public Font getFont() { // do not pick up user overrides
				/*Font font = UIManager.getLookAndFeelDefaults().getFont("Button.font");
				if (font==null){
					font = super.getFont();
				}*/

				return FontPref.DEFAULT_FONT;
			}
			public Color getForeground(){
				return ColorPref.DEFAULT_COLOR_ENABLED_FOREGROUND;
			}
		};
		// this is needed or the mnemonic doesnt activate
		btnExit.setMnemonic((char)((Integer)exitAction.getValue(Action.MNEMONIC_KEY)).intValue());
		btnExit.getAccessibleContext().setAccessibleDescription(
				(String)btnExit.getAction().getValue(Action.SHORT_DESCRIPTION));

		btnLog.setOpaque(false);
		btnExit.setOpaque(false);
	}
	private void initFields() {
		String sUserName = SerialPref.getPref(EANNOUNCE_SAVED_USERNAME,"");
		elfUser = new JTextField(20) {
			private static final long serialVersionUID = 1L;
			public Font getFont() { // do not pick up user overrides
				/*Font font = UIManager.getLookAndFeelDefaults().getFont("TextField.font");
				if (font==null){
					font = super.getFont();
				}*/

				return FontPref.DEFAULT_FONT;
			}
		};
		elfUser.setText(sUserName);
		epfPass.addKeyListener(this);
		elfUser.addKeyListener(this);

		cmbLoc = new JComboBox(getMWParser()){
			private static final long serialVersionUID = 1L;
			public Font getFont() { // do not pick up user overrides
				/*Font font = UIManager.getLookAndFeelDefaults().getFont("ComboBox.font");
				if (font==null){
					font = super.getFont();
				}*/

				return FontPref.DEFAULT_FONT;
			}
		};
		// select any preference or default mw, may be null
		cmbLoc.setSelectedItem(getMWParser().getInitial()); // null will clear the selection
	}

	private void initLabels() {
		lblPass = new JLabel(Utils.getResource("password")+ " "){
			private static final long serialVersionUID = 1L;
			public Font getFont() { // do not pick up user overrides
				/*Font font = UIManager.getLookAndFeelDefaults().getFont("Label.font");
				if (font==null){
					font = super.getFont();
				}*/

				return FontPref.DEFAULT_FONT;
			}
			public Color getForeground(){
				//super.getForeground();
				return ColorPref.DEFAULT_COLOR_ENABLED_FOREGROUND;
			}
		};
		lblPass.setLabelFor(epfPass);

		lblUser = new JLabel(Utils.getResource("user")){
			private static final long serialVersionUID = 1L;
			public Font getFont() { // do not pick up user overrides
				/*Font font = UIManager.getLookAndFeelDefaults().getFont("Label.font");
				if (font==null){
					font = super.getFont();
				}*/

				return FontPref.DEFAULT_FONT;
			}
			public Color getForeground(){
				return ColorPref.DEFAULT_COLOR_ENABLED_FOREGROUND;
			}
		};
		lblUser.setLabelFor(elfUser);

		lblPass.setDisplayedMnemonic(Utils.getMnemonic("password"));
		lblUser.setDisplayedMnemonic(Utils.getMnemonic("user"));

		lblCombo = new JLabel(Utils.getResource("choLoc") + " "){
			private static final long serialVersionUID = 1L;
			public Font getFont() { // do not pick up user overrides
				/*Font font = UIManager.getLookAndFeelDefaults().getFont("Label.font");
				if (font==null){
					font = super.getFont();
				}*/

				return FontPref.DEFAULT_FONT;
			}
			public Color getForeground(){
				return ColorPref.DEFAULT_COLOR_ENABLED_FOREGROUND;
			}
		};
		lblCombo.setLabelFor(cmbLoc);
		lblCombo.setDisplayedMnemonic(Utils.getMnemonic("choLoc"));

		lblVers = new JLabel(Version.getVersion()){
			private static final long serialVersionUID = 1L;
			public Font getFont() { // do not pick up user overrides
				/*Font font = UIManager.getLookAndFeelDefaults().getFont("Label.font");
				if (font==null){
					font = super.getFont();
				}*/

				return FontPref.DEFAULT_FONT;
			}
			public Color getForeground(){
				return ColorPref.DEFAULT_COLOR_ENABLED_FOREGROUND;
			}
		};
	}
	
	private void construct(boolean showCombo) {
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		pnlCenter = new JPanel(gbl);
		pnlCenter.setOpaque(false);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		setConstraints(gbc,0,1,1,1,0,0);
		gbl.setConstraints(lblUser,gbc);
		pnlCenter.add(lblUser);

		setConstraints(gbc,1,1,1,1,0,0);
		gbl.setConstraints(elfUser,gbc);
		pnlCenter.add(elfUser);

		setConstraints(gbc,0,2,1,1,0,0);
		gbl.setConstraints(lblPass,gbc);
		pnlCenter.add(lblPass);

		setConstraints(gbc,1,2,1,1,0,0);
		gbl.setConstraints(epfPass,gbc);
		pnlCenter.add(epfPass);

		// if user is not allowed to select a location, but default is "Please select.."  the combo must be shown
		//if(!showCombo && 
		if(getMWParser().getCurrent()==null){
			MWObject mwO = getMWParser().getInitial();
			if(!(Routines.have(mwO.getIP()) && Routines.have(mwO.getName()) && Routines.have(mwO.getPort()))){
				showCombo = true;
				initialMwSelection =true;
			}
		}
	
		if (showCombo) {	// let user see the combo
			setConstraints(gbc,0,0,1,1,0,0);
			gbl.setConstraints(lblCombo,gbc);
			pnlCenter.add(lblCombo);

			setConstraints(gbc,1,0,1,1,0,0);
			gbl.setConstraints(cmbLoc,gbc);
			pnlCenter.add(cmbLoc);
		}

		JPanel pnlButton = new JPanel(new BorderLayout());
		pnlButton.setOpaque(false);
		pnlButton.add(btnLog,BorderLayout.WEST);
		pnlButton.add(btnExit,BorderLayout.EAST);
		setConstraints(gbc,1,3,1,1,0,0);
		gbl.setConstraints(pnlButton,gbc);
		pnlCenter.add(pnlButton);

		Dimension d = btnLog.getPreferredSize();
		btnLog.setPreferredSize(new Dimension(d.width, d.height-5)); // shrink it so version is visible
		btnExit.setPreferredSize(btnLog.getPreferredSize());

		add(pnlCenter,BorderLayout.NORTH);

		setConstraints(gbc,1,5,1,1,0,0);
		gbl.setConstraints(lblVers,gbc);
		pnlCenter.add(lblVers);

		setSizes();
	}
	private void setSizes() {
		int w = image.getWidth(this);
		int h = image.getHeight(this);
		Dimension d = new Dimension(w,h);
		setSize(d);
		setPreferredSize(d);
		setMinimumSize(d);
	}

	private void setConstraints(GridBagConstraints _gbc, int _gx, int _gy, int _gw, int _gh, int _wx, int _wy) {
		_gbc.gridx = _gx;
		_gbc.gridy = _gy;
		_gbc.gridwidth = _gw;
		_gbc.gridheight = _gh;
		_gbc.weightx = _wx;
		_gbc.weighty = _wy;
	}

	private void loadImage() {
		MediaTracker mt = new MediaTracker(this);
		image = Utils.getImage("splash.jpg");
		mt.addImage(image, 0);
		try {
			mt.waitForID(0);
		} catch (InterruptedException _ie) {
			_ie.printStackTrace();
		}
	}

	/**
	 * called when user has decided to exit the app
	 */
	public void cancelLogin(){
		if (loginWorker!=null){
			loginWorker.cancel(true);
		}
	}

	/********
	 * The following allow access to the current middleware object's information
	 */
	public static String getMWODescription(){
		String desc = "";
		MWObject mwo = getMWParser().getCurrent();
		if (mwo!=null){
			desc = mwo.getDescription();
		}
		return desc;
	}
	public static String getMWOName(){
		String desc = "";
		MWObject mwo = getMWParser().getCurrent();
		if (mwo!=null){
			desc = mwo.getName();
		}
		return desc;
	}
	public static String getMWOIP(){
		String desc = "";
		MWObject mwo = getMWParser().getCurrent();
		if (mwo!=null){
			desc = mwo.getIP();
		}
		return desc;
	}
	public static int getMWOPort(){
		int port = 0;
		MWObject mwo = getMWParser().getCurrent();
		if (mwo!=null){
			port = mwo.getPort();
		}
		return port;
	}
	public static String getMWORptPrefix(){
		String desc = "";
		MWObject mwo = getMWParser().getCurrent();
		if (mwo!=null){
			desc = mwo.getReportPrefix();
		}
		return desc;
	}
	/**
	 * Called when user connects to the middleware
	 * @param mwo - may be null
	 */
	public static void setCurrentMWO(MWObject mwo){
		getMWParser().setCurrent(mwo);
	}
	public static MWObject getCurrentMWO(){
		return getMWParser().getCurrent();
	}

	/**
	 * @return true if more than one location is in the properties file
	 */
	protected static boolean showMWSelectOnFailure() {
		return getMWParser().getSize()>1;
	}
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if (image != null) {
			Dimension d = getSize();
			int iW = image.getWidth(this);
			int iH = image.getHeight(this);

			g.drawImage(image,((d.width - iW)/2),((d.height - iH)/2),iW,iH, this);
		}
	}

	/**
	 * @see java.awt.Container#getInsets()
	 *  controls location of panel on splash.jpg
	 */
	public Insets getInsets() {
		return INSETS;
	}

	/**
	 * dereference 
	 *
	 */
	public void dereference() {
		pnlCenter.removeAll();
		pnlCenter = null;

		removeLabels();
		removeFields();
		removeButtons();
		image = null;
		loginWorker = null;
		profileSet = null;
		activeProf = null;

		removeAll();
		setUI(null);
	}
	private void removeButtons() {
		btnLog.removeAll();
		btnLog.setAction(null);
		btnLog.setUI(null);
		btnLog = null;

		btnExit.removeAll();
		btnExit.setAction(null);
		btnExit.setUI(null); 
		btnExit = null;
	}

	private void removeLabels() {
		lblPass.setUI(null);
		lblPass.setLabelFor(null);
		lblPass = null;
		lblUser.setUI(null);
		lblUser.setLabelFor(null);
		lblUser = null;
		lblCombo.setUI(null);
		lblCombo.setLabelFor(null);
		lblCombo = null;

		lblVers.setUI(null);
		lblVers = null;
	}
	private void removeFields() {
		epfPass.removeKeyListener(this);
		epfPass.removeAll();
		epfPass.setUI(null);
		epfPass = null;
		elfUser.removeKeyListener(this);
		elfUser.setUI(null);
		elfUser.removeAll();
		elfUser = null;
		cmbLoc.removeAllItems();
		cmbLoc.setUI(null);
		cmbLoc = null;
	}

	public void keyTyped(KeyEvent _ke) {}
	public void keyReleased(KeyEvent _ke) {
		String strPass = new String(epfPass.getPassword());
		btnLog.setEnabled(Routines.have(elfUser.getText()) && Routines.have(strPass));
	}

	/**
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 *
	 */
	public void keyPressed(KeyEvent _ke) {
		Object src = _ke.getSource();
		if (_ke.getKeyCode() == KeyEvent.VK_ENTER) {
			char[] pw = epfPass.getPassword();
			if (pw !=null && pw.length>0 && Routines.have(elfUser.getText())) {
				btnLog.doClick();
			}else{
				if (src == elfUser) {
					epfPass.requestFocusInWindow();
				} else if (src == epfPass) {
					elfUser.requestFocusInWindow();
				}
			}
			//Zero out the possible password, for security.
			if (pw !=null){
				Arrays.fill(pw, '0');
			}
			_ke.consume();
		}
	}

	/**
	 * @see java.awt.Component#requestFocusInWindow()
	 *
	 */
	public boolean requestFocusInWindow() {
		boolean ok = false;
		if (Routines.have(elfUser.getText())) {
			ok = epfPass.requestFocusInWindow();
		} else {
			ok = elfUser.requestFocusInWindow();
		}
		return ok;
	}

	/**
	 * clear UILock, remove current MWObject, enable UI components
	 *
	 */
	public void logOff() {
		// user is logging out, reset UI lock
		String sUserName = elfUser.getText();
		String location = "";
		MWObject mwo = mwParse.getCurrent();
		if (mwo != null){
			location =mwo.getName()+mwo.getIP()+mwo.getPort();
		}
		//clear info about mw connection
		setCurrentMWO(null);

		freeUILock(sUserName, location);

		epfPass.setText("");
		epfPass.setEnabled(true);
		elfUser.setEnabled(true);
		btnLog.setEnabled(false);

		RMIMgr.getRmiMgr().logOff();
	}

	private class LoginAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "login";
		LoginAction() {
			super(CMD);
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			elfUser.setEnabled(false);
			epfPass.setEnabled(false);
			loginWorker = new LoginWorker();
			btnLog.setEnabled(false);
			loginWorker.execute(); // do this in a worker thread
		}
	}

	// SwingWorker objects can't be reused
	private class LoginWorker extends SwingWorker<ProfileSet, Void> {
		private boolean connectFail = false;
		private boolean versionException = false;
		private boolean loginException = false;
		private boolean swupdateDone = false;
		@Override
		public ProfileSet doInBackground() {
			ProfileSet pfset = null;
			String sUserName = null;
			String location = null;
			try{
				sUserName = elfUser.getText();

				// mwobj could be null if default was not specified and a preference is not set
				MWObject mwo = (MWObject)cmbLoc.getSelectedItem();
				if (mwo==null){
					EACM.setMWObjectPreference(); // this will select a mw object in mwparser
					mwo = (MWObject)cmbLoc.getSelectedItem();
					if (mwo==null){ // user didnt set anything
						return pfset;
					}
				}

				if (!RMIMgr.getRmiMgr().connect2RMI((MWObject)cmbLoc.getSelectedItem())) {
					connectFail = true;  //display msg or mwselect on event thrd
					loginException = true; // not really, but stop empty profile set msg
					return pfset;
				}

				location =mwo.getName()+mwo.getIP()+mwo.getPort();

				// check to see if this user has a UI instance for this location already
				if (isAlreadyRunning(sUserName,location)){
					loginException = true; // not really, but stop empty profile set msg
					return pfset;
				}

				pfset = RMIMgr.getRmiMgr().login(sUserName,epfPass.getPassword());
				if (pfset==null || pfset.isEmpty()){
					freeUILock(sUserName, location);
				}else{
					//do this here on the bg thread
					swupdateDone = checkForExistingUpdate(); // did user bypass load of an undate before and it was just done?
					if(swupdateDone){
						//do it on bg thread
						Routines.pause(5000);
					}
				}
			} catch(VersionException ve) {
				versionException = true;
				freeUILock(sUserName, location);
			} catch(LoginException le){
				loginException = true;
				freeUILock(sUserName, location);
				//mwuipw-acc = User ID Password combination is incorrect for {0}.
				com.ibm.eacm.ui.UI.showMessage(null,"mw.connect.err-title", JOptionPane.ERROR_MESSAGE, "mwuipw-acc",
						Utils.getResource("msg1003", LoginMgr.getMWODescription()));
				RMIMgr.logger.log(Level.SEVERE,"LoginException: "+Utils.getResource("msg1003", LoginMgr.getMWODescription()));
				if (!Utils.isArmed(LOCATION_CHOOSER_ARM_FILE) && LoginMgr.showMWSelectOnFailure()) {
					// allow user to pick another location
					EACM.setMWObjectPreference();
				}
			}catch(Exception ex){ // prevent hang
				RMIMgr.logger.log(Level.SEVERE,"Error ",ex);
			}
			return pfset;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread
			try {
				if(versionException){
					btnExit.setEnabled(false);
					// progress doesnt work when called on evt thread from here.. not sure why
					// this needs to be done on event thread for window.dispose of eacm so it was moved to here
					// cant allow exit from bg thread, causes java.lang.IllegalStateException: Shutdown in progress
					//msg1004.0 = Client Software is Outdated, Upgrade Process is Initializing.
					com.ibm.eacm.ui.UI.showMessage(null,"mw.connect.err-title", JOptionPane.WARNING_MESSAGE, "mwversion-acc",
							Utils.getResource("msg1004.0"));
					RMIMgr.logger.log(Level.SEVERE,"VersionException: " + Utils.getResource("msg1004.0"));

					blobworker = new BlobWorker();
					blobworker.execute();
				}else if(swupdateDone){
					EACM.getEACM().exit();
				} else {
					if(!isCancelled()){
						ProfileSet o = get();
						if (o == null || o.isEmpty()) {
							elfUser.setEnabled(true);
							epfPass.setEnabled(true);
							if (connectFail){
								UIManager.getLookAndFeel().provideErrorFeedback(null);
								MWObject mwo = (MWObject)cmbLoc.getSelectedItem();
								if (Utils.isArmed(LOCATION_CHOOSER_ARM_FILE) || !showMWSelectOnFailure()) {
									//msg13004.0 = Middleware Connection "{0}" is not available\nPlease try again later.
									com.ibm.eacm.ui.UI.showMessage(null,"mw.connect.err-title",
											JOptionPane.ERROR_MESSAGE, "mwloc-acc", Utils.getResource("msg13004.0",mwo.getDescription()));
									RMIMgr.logger.log(Level.SEVERE,"Error logging in: " + Utils.getResource("msg13004.0",mwo.getDescription()));
								} else {
									String[] options = {"chooseMW","exit"};
									String accdesc[] = { "chooseMW-acc", "exit-acc" };
									int r = com.ibm.eacm.ui.UI.showAccessibleDialog(null,//Component parentComponent
											"mw.connect.err-title", //title
											JOptionPane.ERROR_MESSAGE, //messageType
											JOptionPane.YES_NO_OPTION, // optiontype
											"chooseMWDialog-acc", //accDialogDesc
											//msg1007 = Middleware Connection Unavailable, Please Select from the Following Options.
											Routines.convertToArray(Utils.getResource("msg1007",mwo.getDescription()),RETURN), //msgs
											options,  //button labels
											accdesc); //accButtonDescs

									options = null;
									accdesc = null;
									if (r == JOptionPane.YES_OPTION) {
										EACM.setMWObjectPreference();
									} else if (r == JOptionPane.NO_OPTION) {
										EACM.getEACM().exit();
									}
								}
							}
							if(!loginException){
								if(o==null || o.isEmpty()) {
									//pseMsg = Profile set is empty
									com.ibm.eacm.ui.UI.showMessage(null,"mw.connect.err-title",
											JOptionPane.ERROR_MESSAGE, "mwloc-acc", Utils.getResource("pseMsg"));
									RMIMgr.logger.log(Level.SEVERE,"Error logging in: " + Utils.getResource("pseMsg"));
								}
							}
						} else {
							SerialPref.putPref(EANNOUNCE_SAVED_USERNAME,elfUser.getText());
							profileSet = o;
							EACM.getEACM().process(o, getDefaultProfile());
							// if user can not choose a location and had to select an initial one, save it here
							if(initialMwSelection){
								SerialPref.putPref(MWParser.MIDDLEWARE_PROFILE_KEY,
				    					((MWObject)getMWParser().getSelectedItem()).key());
							}
						}
					}else{
						elfUser.setEnabled(true);
						epfPass.setEnabled(true);
					}
				}// end no version exception
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				String why = null;
				Throwable cause = e.getCause();
				if (cause != null) {
					why = cause.getMessage();
				} else {
					why = e.getMessage();
				}
				RMIMgr.logger.log(Level.SEVERE,"Error logging in: " + why);
			}finally{
				loginWorker = null;
				if(!versionException){
					setCursor(Cursor.getDefaultCursor());
					btnLog.setEnabled(true);
				}
				EACM.getEACM().reportProperties();
			}
		}
	}
	/**
	 * if an update was previously downloaded but not installed, install it now
	 * @return
	 */
	private boolean checkForExistingUpdate() {
		boolean updateDone = false;
		String strUpdate = SerialPref.getPref(UPDATE_TO_INSTALL, (String)null);
		if (strUpdate != null) {//update was downloaded but not installed yet
			RMIMgr.logger.log(Level.CONFIG,"loading existing "+strUpdate);
			File fileUpdate = new File(strUpdate);
			if (fileUpdate.exists()) {
				SWProgressDialog prog = new SWProgressDialog(EACM.getEACM(), "Process Status");
				//installsw=Installing Update File...
				prog.setText(Utils.getResource("installsw"));
				prog.setVisible(true);
				updateDone = DBUtils.updateSoftwareImage(strUpdate);
				prog.setVisible(false);
				prog.dispose();
			}
			SerialPref.removePref(UPDATE_TO_INSTALL);
		}
		return updateDone;
	}
	/**
    called only if you have an invalid token and there was a version exception.
    will trigger the check for update.
	 */
	private BlobWorker blobworker = null;
	private class BlobWorker extends SwingWorker<Boolean, Void> {
		private long t11 = 0L;
		@Override
		public Boolean doInBackground() {
			Boolean bool = new Boolean(false);
			try{
				boolean isok = false;
				//trigger update
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting");
				String eod = DateRoutines.getEOD();
				SWProgressDialog prog = new SWProgressDialog(EACM.getEACM(), "Process Status");;
				prog.setVisible(true);
				Blob updateBlob = getSoftwareImage(eod);
				if(updateBlob !=null){
					String fileName = HOME_DIRECTORY + updateBlob.getBlobExtension();
					RMIMgr.logger.log(Level.FINER,"saving file "+fileName);
					updateBlob.saveToFile(fileName);
					isok = DBUtils.updateSoftwareImage(fileName);
				}

				prog.setVisible(false);
				prog.dispose();
				bool = new Boolean(isok);
				if(isok){
					//do it on bg thread
					Routines.pause(5000);
				}
			}catch(Exception ex){ // prevent hang
				RMIMgr.logger.log(Level.SEVERE,"Error",ex);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"bg ended "+Utils.getDuration(t11));
				blobworker = null;
			}

			return bool;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread
			try {
				if(!isCancelled()){
					Boolean ok = get();
					if (ok.booleanValue()) {
						EACM.getEACM().exit();
					}else{
						// msg1004.1 = No Upgrade Available, Please Try Again Later.
						com.ibm.eacm.ui.UI.showErrorMessage(null,Utils.getResource("msg1004.1"));
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				String why = null;
				Throwable cause = e.getCause();
				if (cause != null) {
					why = cause.getMessage();
				} else {
					why = e.getMessage();
				}
				RMIMgr.logger.log(Level.SEVERE,"Error getting blob: " + why);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"dispatch ended "+Utils.getDuration(t11));
			}
		}
	}

	/**
	 * getSoftwareImage
	 * @param eod
	 * @return
	 */
	private Blob getSoftwareImage(String eod) {
		Blob out = null;
		try {
			out = RMIMgr.getRmiMgr().getRemoteDatabaseInterface().getSoftwareImage("UP", "UPDATE", 1, "IMAGE_UPDATE", eod, eod);
		} catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try {
						out = RMIMgr.getRmiMgr().getRemoteDatabaseInterface().getSoftwareImage("UP", "UPDATE", 1, "IMAGE_UPDATE", eod, eod);
					} catch (Exception e) {
						if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
							if (com.ibm.eacm.ui.UI.showMWExcPrompt(LoginMgr.this, e) == RETRY) {
								out = getSoftwareImage(eod);
							}
						}else{
							com.ibm.eacm.ui.UI.showException(LoginMgr.this,e, "mw.err-title");
						}
					}
				} else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(LoginMgr.this,exc, "mw.err-title");
				}
			}else{ // show user msg and ask what to do
				if(RMIMgr.shouldPromptUser(exc)){
					if (com.ibm.eacm.ui.UI.showMWExcPrompt(LoginMgr.this, exc) == RETRY) {
						out = getSoftwareImage(eod);
					}// else user decide to ignore or exit
				}else{
					com.ibm.eacm.ui.UI.showException(LoginMgr.this,exc, "mw.err-title");
				}
			}
		}
		return out;
	}
	/**
	 * getNewProfileInstance
	 * @param active
	 * @return
	 */
	public static Profile getNewProfileInstance(Profile active) {
	    Profile prof = null;
	    if (active != null) {
	    	try {
	    		prof = active.getNewInstance(RMIMgr.getRmiMgr().getRemoteDatabaseInterface());
	    		if (prof != null) {
	    			EACM.getEACM().setActiveProfile(prof);
	    		}
	    	} catch (Exception re) {
	    		if(RMIMgr.shouldTryReconnect(re)){	// try to reconnect
	    			if (RMIMgr.getRmiMgr().reconnectMain()) {
	    				try {
	    					prof = active.getNewInstance(RMIMgr.getRmiMgr().getRemoteDatabaseInterface());
	    					if (prof != null) {
	    						EACM.getEACM().setActiveProfile(prof);
	    					}
	    				} catch (Exception re2) {
	    					com.ibm.eacm.ui.UI.showException(null,re2,"profile.err-title");
	    				}
	    			}else{ // reconnect failed
	    				com.ibm.eacm.ui.UI.showException(null,re,"profile.err-title");
	    			}
	    		}else{ 
	    			com.ibm.eacm.ui.UI.showException(null,re,"profile.err-title");
	    		}
	    	}
	    }
	    return prof;
	}
}
