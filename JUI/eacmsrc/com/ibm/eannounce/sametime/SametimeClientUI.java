/*
 * Created on Jan 26, 2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package com.ibm.eannounce.sametime;
import com.lotus.sametime.awareness.*;
import com.lotus.sametime.awarenessui.av.AVController;
import com.lotus.sametime.awarenessui.list.AwarenessList;
import com.lotus.sametime.awarenessui.placelist.PlaceAwarenessList;
import com.lotus.sametime.buddylist.*;
import com.lotus.sametime.announcementui.*;
import com.lotus.sametime.chatui.*;
import com.lotus.sametime.commui.*;
import com.lotus.sametime.filetransferui.*;
import com.lotus.sametime.core.comparch.*;
import com.lotus.sametime.core.constants.*;
import com.lotus.sametime.core.types.*;
import com.lotus.sametime.community.*;
import com.lotus.sametime.conference.*;
import com.lotus.sametime.appshare.*;
import com.lotus.sametime.im.*;
import com.lotus.sametime.places.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class SametimeClientUI extends AbstractClientUI implements ActionListener, BLServiceListener, LoginListener, PlacesServiceListener, MeetingListener, FileTransferUIListener, AdminMsgListener {
	private static final long serialVersionUID = 1L;
	private JFrame parent = null;
    private JPanel pnlMain = new JPanel(new BorderLayout());
    private JPanel pnlSouth = new JPanel(new GridLayout(3, 1, 5, 5));
    private JPanel pnlBtn = new JPanel(new GridLayout(1, 4, 5, 5));
    private JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private JPanel pnlLeft = new JPanel(new BorderLayout());
    private JPanel pnlRight = new JPanel(new BorderLayout());
    private boolean bAdmin = false;
    /*
     components
     */
    private STSession session = null;
    private STBase stBase = null;
    private CommunityService commServ = null;
    private MeetingFactoryService meet = null;
    private AppShareComp share = null;
    private ImComp im = null;
    private Place ePlace = null;

    /*
     ui
     */
    private FileTransferUI ftp = null;
    private CommUI comm = null;
    private ChatUI chat = null;
    private AnnouncementUI annc = null;

    /*
     services
     */
    private BLService blServ = null;
    /*
     passed up services
     */
    private PlacesService plcServ = null;

    private JButton btnChat = new JButton("Chat"); //$NON-NLS-1$
    //	private JButton btnFTP = new JButton("FTP");
    private JButton btnAnnc = new JButton("Broadcast"); //$NON-NLS-1$
    private JButton btnAdmin = new JButton("Admin Msg"); //$NON-NLS-1$
    private JButton btnClose = new JButton("Close"); //$NON-NLS-1$
    private JComboBox cmbSelection = new JComboBox();
    private JComboBox cmbType = new JComboBox();

    private JLabel lLbl = new JLabel("Buddies"); //$NON-NLS-1$
    private JLabel rLbl = new JLabel("Users"); //$NON-NLS-1$

    private AwarenessList aList = null;
    private PlaceAwarenessList pList = null;
    private String myPlace = null;
    /**
     * Constructor
     *
     * @param _parent
     * @param _title
     * @param _bAdmin
     * @author Anthony C. Liberto
     */
    public SametimeClientUI(JFrame _parent, String _title, boolean _bAdmin) {
        super(_title);
        parent = _parent;
        init(_bAdmin);
        return;
    }

    /**
     * start
     * @author Anthony C. Liberto
     * @param _host
     * @param _user
     * @param _pass
     * @param _place
     */
    public void start(String _host, String _user, String _pass, String _place) {
        //System.out.println("starting(" + _host + ", " + _user + ", " + _pass + ", " + _place + ")");
        myPlace = _place;
        initSession();
        if (hasSession()) {
            session.loadAllComponents();
            session.start();
            login(_host, _user, _pass);
        }
        return;
    }

    private void init(boolean _bAdmin) {
        bAdmin = _bAdmin;
        getContentPane().add("Center", pnlMain); //$NON-NLS-1$
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        btnChat.addActionListener(this);
        btnChat.setActionCommand("chat"); //$NON-NLS-1$
        btnChat.setMnemonic('c');
        pnlBtn.add(btnChat);

        //		btnFTP.addActionListener(this);
        //		btnFTP.setActionCommand("ftp");
        //		btnFTP.setMnemonic('f');
        //		pnlBtn.add(btnFTP);

        btnAnnc.addActionListener(this);
        btnAnnc.setActionCommand("annc"); //$NON-NLS-1$
        btnAnnc.setMnemonic('b');

        btnAdmin.addActionListener(this);
        btnAdmin.setActionCommand("admin"); //$NON-NLS-1$
        btnAdmin.setMnemonic('a');
        pnlBtn.add(btnAnnc);
        pnlBtn.add(btnAdmin);
        if (!_bAdmin) {
            btnAnnc.setVisible(false);
            btnAnnc.setEnabled(false);
            btnAdmin.setVisible(false);
            btnAdmin.setEnabled(false);
        }

        btnClose.addActionListener(this);
        btnClose.setActionCommand("close"); //$NON-NLS-1$
        btnClose.setMnemonic('c');
        pnlBtn.add(btnClose);

        pnlLeft.add("North", lLbl); //$NON-NLS-1$
        pnlRight.add("North", rLbl); //$NON-NLS-1$

        cmbSelection.addItem("Send Selected Buddies"); //$NON-NLS-1$
        cmbSelection.addItem("Send Selected Users"); //$NON-NLS-1$
        cmbSelection.addItem("Send All Selected"); //$NON-NLS-1$
        cmbSelection.setSelectedIndex(1);

        cmbType.addItem("Chat"); //$NON-NLS-1$
        /*
        		cmbType.addItem(new String("Audio"));
        		cmbType.addItem(new String("Video"));
        		cmbType.addItem(new String("Share"));
        		cmbType.addItem(new String("Collaboration"));
        */
        cmbType.setSelectedIndex(0);

        pnlSouth.add(cmbSelection);
        pnlSouth.add(cmbType);
        pnlSouth.add(pnlBtn);
        if (isAdmin()) {
            split.setLeftComponent(pnlLeft);
            split.setRightComponent(pnlRight);
            pnlMain.add("Center", split); //$NON-NLS-1$
        } else {
            pnlMain.add("Center", pnlRight); //$NON-NLS-1$
            cmbSelection.setVisible(false);
            cmbSelection.setEnabled(false);
        }
        pnlMain.add("South", pnlSouth); //$NON-NLS-1$
        pack();
        btnChat.setEnabled(false);
        //		btnAnnc.setEnabled(false);
        //		btnFTP.setEnabled(false);
        return;
    }

    private void initSession() {
        try {
            session = new STSession("eannounce1.3"); //$NON-NLS-1$
            createComponents();
        } catch (DuplicateObjectException _doe) {
            _doe.printStackTrace();
        }

        return;
    }

    private void createComponents() throws DuplicateObjectException {
        meet = new MeetingFactoryComp(session, null, null, null);
        share = new AppShareComp(session);
        im = new ImComp(session) {
            public Im createIm(STUser _user, EncLevel _level, int _type, boolean _login) {
                Im out = super.createIm(_user, _level, _type, _login);
                return out;
            }
        };
        stBase = new STBase(session);
        return;
    }

    private void stopSession() {
        session.stop();
        session.unloadSession();
        return;
    }

    private boolean hasSession() {
        return session != null;
    }

    private void login(String _host, String _user, String _pass) {
        if (commServ == null) {
            commServ = (CommunityService) session.getCompApi(CommunityService.COMP_NAME);
            commServ.addLoginListener(this);
            commServ.addAdminMsgListener(this);
        }
        if (!commServ.isLoggedIn()) {
            commServ.loginByPassword(_host, _user, _pass);
        }
        return;
    }

    private void logout() {
        commServ.logout();
        commServ.removeLoginListener(this);
        commServ.removeAdminMsgListener(this);
        return;
    }

    /**
     * @see com.lotus.sametime.community.LoginListener#loggedIn(com.lotus.sametime.community.LoginEvent)
     * @author Anthony C. Liberto
     */
    public void loggedIn(LoginEvent _le) {
        startServices();
        sametimeStarted();
        return;
    }

    /**
     * @see com.lotus.sametime.community.LoginListener#loggedOut(com.lotus.sametime.community.LoginEvent)
     * @author Anthony C. Liberto
     */
    public void loggedOut(LoginEvent _le) {
        stopServices();
        logout();
        stopSession();
        return;
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
        String action = _ae.getActionCommand();
        if (action.equals("chat")) { //$NON-NLS-1$
            chat();
        } else if (action.equals("annc")) { //$NON-NLS-1$
            announce();
        } else if (action.equals("admin")) { //$NON-NLS-1$
            sendAdminMessage();
            //		} else if (action.equals("ftp")) {
            //			ftp();
        } else if (action.equals("close")) { //$NON-NLS-1$
            close();
        }
        return;
    }

    private void chat() {
//        System.out.println("share Enabled      : " + chat.isAppShareEnabled()); //$NON-NLS-1$
//        System.out.println("audioBridge Enabled: " + chat.isAudioBridgeEnabled()); //$NON-NLS-1$
//        System.out.println("audio Enabled      : " + chat.isAudioEnabled()); //$NON-NLS-1$
//        System.out.println("meeting Enabled    : " + chat.isMeetingEnabled()); //$NON-NLS-1$
//        System.out.println("video Enabled      : " + chat.isVideoEnabled()); //$NON-NLS-1$
//        System.out.println("whiteboard Enabled : " + chat.isWhiteBoardEnabled()); //$NON-NLS-1$
        STUser[] users = getSelectedUsers();
        MeetingTypes type = getMeetingType();
        int ii = -1;
        if (users != null) {
            ii = users.length;
            if (type == MeetingTypes.ST_CHAT_MEETING && ii == 1) {
                chat.create1On1ChatById(users[0]);
            } else if (isMeetingAllowed(type)) {
                chat.createMeeting(type, "this is my meeting", "please join my meeting", true, users); //$NON-NLS-2$  //$NON-NLS-1$
            } else {
                chat.createMeeting(MeetingTypes.ST_CHAT_MEETING, "this is my meeting", "please join my meeting", true, users); //$NON-NLS-2$  //$NON-NLS-1$
            }
        }
        return;
    }

    private void announce() {
        STUser[] users = getSelectedUsers();
        annc.sendAnnouncement(users);
        return;
    }

    private void sendAdminMessage() {
        if (stBase != null) {
            String msg = getAdminMsg();
            if (msg != null) {
                stBase.adminMsg(msg);
            }
        }
        return;
    }
    /*
     deprecated
    	private void ftp() {
    		STUser[] users = getSelectedUsers();
    		if (users != null) {
    			int ii = users.length;
    			for (int i=0;i<ii;++i) {
    System.out.println("send file to: " + users[i].getDisplayName());
    				ftp.sendFile(users[i]);
    			}
    		}
    		return;
    	}
    */
    private STUser[] getSelectedPlaceUsers() {
        STUser[] in = pList.getSelectedItems();
        if (in != null) {
            int ii = in.length;
            STUser[] out = new STUser[ii];
            for (int i = 0; i < ii; ++i) {
                out[i] = new STUser(in[i].getId(), in[i].getName(), in[i].getDesc());
            }
            return out;
        }
        return in;
    }

    private STUser[] getSelectedBuddyUsers() {
        STUser[] in = aList.getSelectedItems();
        if (in != null) {
            int ii = in.length;
            STUser[] out = new STUser[ii];
            for (int i = 0; i < ii; ++i) {
                out[i] = new STUser(in[i].getId(), in[i].getName(), in[i].getDesc());
            }
            return out;
        }
        return in;
    }
    private STUser[] getSelectedUsers() {
        int i = 0;
        if (!isAdmin()) {
            return getSelectedPlaceUsers();
        }
        i = cmbSelection.getSelectedIndex();
        if (i == 0) {
            return getSelectedBuddyUsers();
        } else if (i == 1) {
            return getSelectedPlaceUsers();
        } else if (i == 2) {
            return getAllSelectedUsers();
        }
        return null;
    }
    private STUser[] getAllSelectedUsers() {
        STUser[] a = getSelectedBuddyUsers();
        STUser[] b = getSelectedPlaceUsers();
        HashMap map = new HashMap();
        for (int i = 0; i < a.length; ++i) {
            String key = a[i].getId().getId();
            if (!map.containsKey(key)) {
                map.put(key, a[i]);
            }
        }
        for (int i = 0; i < b.length; ++i) {
            String key = b[i].getId().getId();
            if (!map.containsKey(key)) {
                map.put(key, b[i]);
            }
        }
        if (!map.isEmpty()) {
            Collection c = map.values();
            return (STUser[]) c.toArray(new STUser[map.size()]);
        }
        return null;
    }

    private MeetingTypes getMeetingType() {
        int i = cmbType.getSelectedIndex();
        switch (i) {
        case 1 :
            System.out.println("create Audio Meeting"); //$NON-NLS-1$
            return MeetingTypes.ST_AUDIO_MEETING;
        case 2 :
            System.out.println("create Video Meeting"); //$NON-NLS-1$
            return MeetingTypes.ST_VIDEO_MEETING;
        case 3 :
            System.out.println("create Share Meeting"); //$NON-NLS-1$
            return MeetingTypes.ST_SHARE_MEETING;
        case 4 :
            System.out.println("create Collaboration Meeting"); //$NON-NLS-1$
            return MeetingTypes.ST_COLLABORATION_MEETING;
        default :
            System.out.println("create Chat Meeting"); //$NON-NLS-1$
            return MeetingTypes.ST_CHAT_MEETING;
        }
    }

    private boolean isMeetingAllowed(MeetingTypes _meet) {
        if (_meet != null && chat != null) {
            if (_meet == MeetingTypes.ST_AUDIO_MEETING) {
                return chat.isAudioBridgeEnabled() && chat.isAudioEnabled();
            } else if (_meet == MeetingTypes.ST_VIDEO_MEETING) {
                return chat.isVideoEnabled();
            } else if (_meet == MeetingTypes.ST_SHARE_MEETING) {
                return chat.isAppShareEnabled();
            } else if (_meet == MeetingTypes.ST_COLLABORATION_MEETING) {
                return chat.isMeetingEnabled();
            } else if (_meet == MeetingTypes.ST_CHAT_MEETING) {
                return true;
            }
        }
        return false;
    }

    /**
     * close
     *
     * @author Anthony C. Liberto
     */
    public void close() {
        parent.show();
        hide();
        dispose();
        return;
    }

    /**
     * stop
     *
     * @author Anthony C. Liberto
     */
    public void stop() {
        leaveMeetingPlace();
        stopServices();
        logout();
        stopSession();
        return;
    }
    /*
     start and stop services

     */
    private void startServices() {
        startAwarenessService();
        startSametimeServices();
        startListServices();
        startUI();
        return;
    }

    private void stopServices() {
        stopAwarenessService();
        stopSametimeServices();
        stopListServices();
        stopUI();
        return;
    }

    /*
     generic services
     */
    private void startAwarenessService() {
        AVController av = null;
        AVController av2 = null;
        if (isAdmin()) {
            aList = new AwarenessList(session, true);
            aList.showOnlineOnly(true);
            av = new AVController(aList.getModel());
            aList.setController(av);
            pnlLeft.add("Center", aList); //$NON-NLS-1$
        }
        pList = new PlaceAwarenessList(session, true);
        av2 = new AVController(pList.getModel());
        pList.setController(av2);
        pnlRight.add("Center", pList); //$NON-NLS-1$

        pack();
        validate();
        return;
    }
    private void stopAwarenessService() {
        return;
    }
    /**
     * serviceAvailable
     *
     * @param _ase
     * @author Anthony C. Liberto
     */
    public void serviceAvailable(AwarenessServiceEvent _ase) {
    }
    /**
     * serviceUnavailable
     *
     * @param _ase
     * @author Anthony C. Liberto
     */
    public void serviceUnavailable(AwarenessServiceEvent _ase) {
    }

    /*
     start required sametime services
     */
    private void startSametimeServices() {
        startPlace();
        return;
    }

    private void stopSametimeServices() {
        stopPlace();
        return;
    }

    private void startPlace() {
        if (plcServ == null) {
            plcServ = (PlacesService) session.getCompApi(PlacesService.COMP_NAME);
            plcServ.addPlacesServiceListener(this);
        }
        return;
    }
    private void stopPlace() {
        if (plcServ != null) {
            plcServ.removePlacesServiceListener(this);
        }
        return;
    }
    /**
     * @see com.lotus.sametime.places.PlacesServiceListener#serviceAvailable(com.lotus.sametime.places.PlacesServiceEvent)
     * @author Anthony C. Liberto
     */
    public void serviceAvailable(PlacesServiceEvent _pse) {
        System.out.println("create meeting place"); //$NON-NLS-1$
        btnChat.setEnabled(true);
        if (isAdmin()) {
            btnAnnc.setEnabled(true);
            btnAdmin.setEnabled(true);
        }
        createMeetingPlace();
        return;
    }
    /**
     * @see com.lotus.sametime.places.PlacesServiceListener#serviceUnavailable(com.lotus.sametime.places.PlacesServiceEvent)
     * @author Anthony C. Liberto
     */
    public void serviceUnavailable(PlacesServiceEvent _pse) {
    }

    private void createMeetingPlace() {
        if (ePlace == null) {
            ePlace = plcServ.createPlace(myPlace, myPlace, EncLevel.ENC_LEVEL_DONT_CARE, 10003, PlacesConstants.PLACE_NOT_PUBLISHED);
            ePlace.enter();
            pList.bindPlace(ePlace);
        }
        return;
    }

    private void leaveMeetingPlace() {
        if (ePlace != null) {
            ePlace.leave(0);
        }
        return;
    }
    /*
     list services

     */
    private void startListServices() {
        startBLService();
        return;
    }

    private void stopListServices() {
        stopBLService();
        return;
    }

    private void startBLService() {
        if (blServ == null) {
            blServ = (BLService) session.getCompApi(BLService.COMP_NAME);
            blServ.addBLServiceListener(this);
        }
        return;
    }
    private void stopBLService() {
        if (blServ != null) {
            blServ.removeBLServiceListener(this);
        }
        return;
    }
    /**
     * @see com.lotus.sametime.buddylist.BLServiceListener#blOverflowed(com.lotus.sametime.buddylist.BLEvent)
     * @author Anthony C. Liberto
     */
    public void blOverflowed(BLEvent _ble) {
        System.out.println("buddyList overflow: " + _ble.getReason()); //$NON-NLS-1$
        return;
    }
    /**
     * @see com.lotus.sametime.buddylist.BLServiceListener#blRetrieveFailed(com.lotus.sametime.buddylist.BLEvent)
     * @author Anthony C. Liberto
     */
    public void blRetrieveFailed(BLEvent _ble) {
        System.out.println("buddyList retrieval failed: " + _ble.getReason()); //$NON-NLS-1$
        return;
    }
    /**
     * @see com.lotus.sametime.buddylist.BLServiceListener#blRetrieveSucceeded(com.lotus.sametime.buddylist.BLEvent)
     * @author Anthony C. Liberto
     */
    public void blRetrieveSucceeded(BLEvent _ble) {
//        System.out.println("buddyList retrieval succeeded"); //$NON-NLS-1$
        BL myBuddyList = _ble.getBL();
        if (myBuddyList != null) {
            processBuddyGroups(myBuddyList.getblGroups());
        } else {
            System.out.println("buddy list is null"); //$NON-NLS-1$
        }
        return;
    }
    /**
     * @see com.lotus.sametime.buddylist.BLServiceListener#blSetFailed(com.lotus.sametime.buddylist.BLEvent)
     * @author Anthony C. Liberto
     */
    public void blSetFailed(BLEvent _ble) {
    }
    /**
     * @see com.lotus.sametime.buddylist.BLServiceListener#blSetSucceeded(com.lotus.sametime.buddylist.BLEvent)
     * @author Anthony C. Liberto
     */
    public void blSetSucceeded(BLEvent _ble) {
    }
    /**
     * @see com.lotus.sametime.buddylist.BLServiceListener#blUpdated(com.lotus.sametime.buddylist.BLEvent)
     * @author Anthony C. Liberto
     */
    public void blUpdated(BLEvent _ble) {
    }
    /**
     * @see com.lotus.sametime.buddylist.BLServiceListener#serviceAvailable(com.lotus.sametime.buddylist.BLEvent)
     * @author Anthony C. Liberto
     */
    public void serviceAvailable(BLEvent _ble) {
        //		System.out.println("getting BuddyList()");
        blServ.getBuddyList();
        return;
    }
    /**
     * @see com.lotus.sametime.buddylist.BLServiceListener#serviceUnavailable(com.lotus.sametime.buddylist.BLEvent)
     * @author Anthony C. Liberto
     */
    public void serviceUnavailable(BLEvent _ble) {
    }
    private void processBuddyGroups(Vector _v) {
        if (_v != null) {
            int ii = _v.size();
            for (int i = 0; i < ii; ++i) {
                //				System.out.println("processing buddyGroup " + i + " of " + ii);
                Object o = _v.get(i);
                if (o instanceof PrivateGroup) {
                    processBuddies(((PrivateGroup) o).getUsersInGroup());
                }
            }
        }
        return;
    }

    private void processBuddies(Vector _v) {
        if (_v != null) {
            int ii = _v.size();
            for (int i = 0; i < ii; ++i) {
                //				System.out.println("    processing buddy " + i + " of " + ii);
                Object o = _v.get(i);
                if (o instanceof STBLUser) {
                    STBLUser user = (STBLUser) o;
                    if (aList != null) {
                        aList.addUser(user);
                    }
                }
            }
        }
        return;
    }

    private void startUI() {
        if (chat == null) {
            chat = (ChatUI) session.getCompApi(ChatUI.COMP_NAME);
            chat.setChatFactory(createFactory());
            chat.addMeetingListener(this);
        }
        if (ftp == null) {
            ftp = (FileTransferUI) session.getCompApi(FileTransferUI.COMP_NAME);
            ftp.addFileTransferUIListener(this);
        }
        if (comm == null) {
            comm = (CommUI) session.getCompApi(CommUI.COMP_NAME);
        }
        if (annc == null) {
            annc = (AnnouncementUI) session.getCompApi(AnnouncementUI.COMP_NAME);
        }
        return;
    }

    private void stopUI() {
        if (ftp != null) {
            ftp.removeFileTransferUIListener(this);
        }
        if (chat != null) {
            chat.removeMeetingListener(this);
        }
        return;
    }
    /**
     * @see com.lotus.sametime.filetransferui.FileTransferUIListener#fileTransferCompleted(com.lotus.sametime.filetransferui.FileTransferUIEvent)
     * @author Anthony C. Liberto
     */
    public void fileTransferCompleted(FileTransferUIEvent _ev) {
        if (!isAdmin()) {
            String fileName = _ev.getFileName().toLowerCase();
            if (fileName.endsWith(".update")) { //$NON-NLS-1$
                processUpdate(_ev.getFileName());
            }
        }
        return;
    }
    /**
     * @see com.lotus.sametime.filetransferui.FileTransferUIListener#fileTransferFailed(com.lotus.sametime.filetransferui.FileTransferUIEvent)
     * @author Anthony C. Liberto
     */
    public void fileTransferFailed(FileTransferUIEvent _ev) {
        System.out.println("ftpFailed: " + _ev.getReason()); //$NON-NLS-1$
    }
    /*
    	public void FileTransferInitiated(FileTransferEvent _ftp) {
    		System.out.println("ftpInitiated");
    	}
    	public void bytesTransferredUpdate(FileTransferEvent _ftp) {
    		System.out.println("bytes transferred");
    	}
    	public void fileTransferCompleted(FileTransferEvent _ftp) {
    		System.out.println("ftp complete");
    	}
    	public void fileTransferDeclined(FileTransferEvent _ftp) {
    		System.out.println("ftp declined");
    	}
    	public void fileTransferStarted(FileTransferEvent _ftp) {
    		System.out.println("ftp started");
    	}
    	public void fileTransferStopped(FileTransferEvent _ftp) {
    		System.out.println("ftp stopped");
    	}
    */

    /**
     * @see com.lotus.sametime.community.AdminMsgListener#adminMsgReceived(com.lotus.sametime.community.AdminMsgEvent)
     * @author Anthony C. Liberto
     */
    public void adminMsgReceived(AdminMsgEvent _ame) {
        System.out.println("adminMsgReceived"); //$NON-NLS-1$
        if (!_ame.isConsumed()) {
            showAdminMessage(_ame.getMsgText());
            _ame.setConsumed(true);
        }
        return;
    }

    /**
     * @see com.lotus.sametime.chatui.MeetingListener#launchMeeting(com.lotus.sametime.chatui.MeetingInfo, java.net.URL)
     * @author Anthony C. Liberto
     */
    public void launchMeeting(MeetingInfo _info, URL _url) {
        System.out.println("launchMeeting"); //$NON-NLS-1$
        launch(_url);
        return;
    }
    /**
     * @see com.lotus.sametime.chatui.MeetingListener#meetingCreationFailed(com.lotus.sametime.chatui.MeetingInfo, int)
     * @author Anthony C. Liberto
     */
    public void meetingCreationFailed(MeetingInfo _info, int _reason) {
        System.out.println("meetingCreationFailed"); //$NON-NLS-1$
    }
    /**
     * urlClicked
     * @param _ev
     * @author Anthony C. Liberto
     */
    public void urlClicked(UrlClickEvent _ev) {
        String str = _ev.getURL();
        URL out = null;
        try {
            out = new URL(str);
        } catch (MalformedURLException _x) {
            _x.printStackTrace();
            return;
        }
        launch(out);
        return;
    }

    private void launch(URL _url) {
        System.out.println("launchURL"); //$NON-NLS-1$
        return;
    }

    /**
     * createFactor
     * @return
     * @author Anthony C. Liberto
     */
    public DefaultChatFactory createFactory() {
        if (!isAdmin()) {
            return new EancChatFactory(session);
        }
        return new EChatFactory(session);
    }

    /**
     * isAdmin
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isAdmin() {
        return bAdmin;
    }

    /**
     * loggedIn
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean loggedIn() {
        if (commServ != null) {
            System.out.println("sametime logged in: " + commServ.isLoggedIn()); //$NON-NLS-1$
            return commServ.isLoggedIn();
        }
        return false;
    }

    /**
     * processUpdate
     * @param _file
     * @author Anthony C. Liberto
     */
    public void processUpdate(String _file) {
        System.out.println("process the update in " + _file); //$NON-NLS-1$
        return;
    }

    /**
     * sametimeStarted
     * @author Anthony C. Liberto
     */
    public void sametimeStarted() {
        return;
    }

    /*
     overridden methods.

     */
    /**
     * showAdminMessage
     * @param _s
     * @author Anthony C. Liberto
     */
    public void showAdminMessage(String _s) {
    }
    /**
     * getAdminMsg
     * @return
     * @author Anthony C. Liberto
     */
    public String getAdminMsg() {
        return "Please excuse the interruption, this is only a test"; //$NON-NLS-1$
    }
}
