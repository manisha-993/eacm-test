/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: ENavForm.java,v $
 * Revision 1.5  2009/06/12 15:20:54  wendy
 * Performance cleanup - check nav html
 *
 * Revision 1.4  2009/05/26 14:38:00  wendy
 * getSplit is used in loading dualnav
 *
 * Revision 1.3  2009/05/26 13:31:17  wendy
 * Performance cleanup
 *
 * Revision 1.2  2008/01/30 16:27:10  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:46:08  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:18  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:19  tony
 * This is the initial load of OPICM
 *
 * Revision 1.19  2005/09/08 17:59:10  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.18  2005/03/28 17:56:38  tony
 * MN_button_disappear
 * adjust to preven java bug 4664818
 *
 * Revision 1.17  2005/02/23 17:50:36  tony
 * 7342
 *
 * Revision 1.16  2005/02/23 17:30:30  tony
 * Change Request 7342
 *
 * Revision 1.15  2005/02/15 20:25:36  tony
 * CR_1124045346
 *
 * Revision 1.14  2005/02/03 21:26:15  tony
 * JTest Format Third Pass
 *
 * Revision 1.13  2005/02/03 16:38:54  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.12  2005/02/01 15:34:28  tony
 * JTest Second Pass
 * added changeGroup label identification
 *
 * Revision 1.11  2005/01/28 17:54:19  tony
 * JTest Fromat step 2
 *
 * Revision 1.10  2005/01/26 17:43:25  tony
 * JTest Format Mods
 *
 * Revision 1.9  2005/01/13 20:43:40  tony
 * removed invalid reference
 *
 * Revision 1.8  2004/11/19 18:01:51  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.7  2004/06/22 18:08:37  tony
 * accessible
 *
 * Revision 1.6  2004/04/12 17:19:22  tony
 * 53729
 *
 * Revision 1.5  2004/03/26 23:27:22  tony
 *  cr_209046022 -- first pass
 *
 * Revision 1.4  2004/02/24 18:01:32  tony
 * e-announce13 send tab
 *
 * Revision 1.3  2004/02/23 21:30:52  tony
 * e-announce13
 *
 * Revision 1.2  2004/02/20 22:17:59  tony
 * chatAction
 *
 * Revision 1.1.1.1  2004/02/10 16:59:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.66  2004/01/16 23:25:22  tony
 * 53562
 *
 * Revision 1.65  2004/01/09 00:42:44  tony
 * cr_1210035324
 * Bookmarks generate a replayable history
 *
 * Revision 1.64  2004/01/06 16:10:22  tony
 * 53495
 *
 * Revision 1.63  2004/01/05 22:23:18  tony
 * 53497
 *
 * Revision 1.62  2003/12/11 22:28:59  tony
 * acl_20031211
 *
 * Revision 1.61  2003/12/11 16:44:31  tony
 * acl_20031211
 *
 * Revision 1.60  2003/12/08 21:57:18  tony
 * cr_3274
 *
 * Revision 1.59  2003/10/29 19:10:43  tony
 * acl_20031029
 *
 * Revision 1.58  2003/10/29 00:11:58  tony
 * removed System.out. statements.
 * REPLAYABLE_LOGFILE
 *
 * Revision 1.57  2003/10/24 21:29:23  tony
 * 52715
 *
 * Revision 1.56  2003/10/17 18:02:45  tony
 * 52614
 *
 * Revision 1.55  2003/10/08 20:11:28  tony
 * 52476
 *
 * Revision 1.54  2003/10/06 18:33:07  tony
 * 52471
 *
 * Revision 1.53  2003/09/30 16:36:20  tony
 * kehrli_20030929 --
 * logic enhancement to improve modification
 * ability of tab icon, title, tip, and menu items for
 * future modifications.
 *
 * Revision 1.52  2003/09/12 16:13:06  tony
 * 52189
 *
 * Revision 1.51  2003/08/28 18:30:52  tony
 * 51975
 *
 * Revision 1.50  2003/08/25 21:27:11  tony
 * 51922
 *
 * Revision 1.49  2003/07/29 21:33:48  tony
 * removed test system.out.
 *
 * Revision 1.48  2003/07/29 18:25:44  tony
 * updated refresh logic repaired for peer create.
 *
 * Revision 1.47  2003/07/29 17:14:21  tony
 * 51555
 *
 * Revision 1.46  2003/07/29 16:58:29  tony
 * 51555
 *
 * Revision 1.45  2003/07/18 18:59:10  tony
 * acl_20030718 added selectKeys logic as guest
 * Entity  = current entity
 * Entity1 = parent Entity
 * Entity2 = Child Entity
 * then ',' then keys separated by ','.
 *
 * Revision 1.44  2003/07/11 17:00:16  tony
 * updated logic to perform default functions for
 * the preference panel.
 *
 * Revision 1.43  2003/07/03 16:38:05  tony
 * improved scripting logic.
 *
 * Revision 1.42  2003/07/02 21:42:32  tony
 * updated logging to allow parsing of log file to
 * auto navigate development to the proper location
 * via the proper path.
 *
 * Revision 1.41  2003/07/02 16:43:24  tony
 * updated logging capabilities to allow for play back of
 * log files.
 *
 * Revision 1.40  2003/07/01 17:12:02  tony
 * 51392
 * 51395
 *
 * Revision 1.39  2003/06/25 18:19:36  tony
 * 1.2h bookmark enhancements
 *
 * Revision 1.38  2003/06/20 22:33:43  tony
 * 1.2 modification.
 *
 * Revision 1.37  2003/06/19 18:30:08  tony
 * 51318
 *
 * Revision 1.36  2003/06/17 20:49:23  tony
 * added profile management logic
 * 1.2h
 *
 * Revision 1.35  2003/06/16 17:22:55  tony
 * updated logic for 1.2h to allow for the application to
 * automatically select the visible navigate as current when
 * the navigate splitpane is adjusted to its minimum or
 * its maximum.
 *
 * Revision 1.34  2003/06/13 16:08:30  tony
 * 1.2h update, singleCart and various fixes.
 *
 * Revision 1.33  2003/06/12 22:23:41  tony
 * 1.2h modification enhancements.
 *
 * Revision 1.32  2003/06/11 18:09:11  tony
 * 51267
 *
 * Revision 1.31  2003/05/30 21:09:24  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.30  2003/05/29 21:20:46  tony
 * adjusted setParm logic for single parm string.
 *
 * Revision 1.29  2003/05/28 17:04:33  tony
 * updated eSwingworker.
 *
 * Revision 1.28  2003/05/09 16:51:29  tony
 * updated filter icon enabling for filter.
 *
 * Revision 1.27  2003/05/02 20:05:56  tony
 * 50504 -- adjusted messaging and this seems to have
 * resolved the problem in local test.
 *
 * Revision 1.26  2003/05/02 17:50:19  tony
 * 50495
 *
 * Revision 1.25  2003/04/25 20:38:00  tony
 * updated dBase logic.
 *
 * Revision 1.24  2003/04/23 22:03:21  tony
 * added logic to retrieve the locklist if it is null, thus allowing
 * an edit at the end of a bookmark.
 *
 * Revision 1.23  2003/04/18 20:10:29  tony
 * added tab placement to preferences.
 *
 * Revision 1.22  2003/04/16 22:39:56  tony
 * adjusted logic so that tip is shown on nav tab.
 *
 * Revision 1.21  2003/04/11 20:02:33  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.epanels;
import com.elogin.*;
//import com.ibm.eannounce.eserver.ChatAction;
//import com.ibm.eannounce.database.databaseInterface;
import com.ibm.eannounce.eforms.*;
import com.ibm.eannounce.eforms.navigate.*;
import com.ibm.eannounce.eforms.toolbar.*;
import com.ibm.eannounce.eobjects.ESplitPane;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ENavForm extends ERemotePanel implements ETabable, ActionListener, EAccessConstants {
	private static final long serialVersionUID = 1L;
	private HashMap formMap = new HashMap();
    /**
     * prof
     */
    protected Profile prof = null;

    private EPanel pnlMain = new EPanel(new BorderLayout());

    private EPanel pnlNorth = new EPanel(new BorderLayout());	//cgLabel
    private ELabel sesLbl = new ELabel();
    private ELabel cgLbl = new ELabel();						//cgLabel

    private EForm form = null;

    private LockList lockList = null;

    private Navigate[] nav = null;
    private Navigate curNav = null;

    private boolean bPin = false;

    private Profile parentProfile = null;
    //private String sGif = null;
    private Border selectedBorder = UIManager.getBorder("eannounce.tab.selectedBorder");
    private Border nonSelectedBorder = UIManager.getBorder("eannounce.tab.nonSelectedBorder");

    private ESplitPane split = null;
    private ETabable parTab = null; //51975
    private Vector vChildTab = new Vector(); //51975
    private boolean bMark = false; //kehrli_20030929
    private String strCG = null;

    /**
     * eNavForm
     * @author Anthony C. Liberto
     */
    public ENavForm() {
        super(new BorderLayout());
        setDoubleBuffered(true);
        addContent("Center", pnlMain);
        addContent("North", pnlNorth);
        pnlNorth.add("West", sesLbl);
        pnlNorth.add("East",cgLbl);		//cgLabel
        setSessionTagText("Lake Forest");
        setChangeGroupTagText(null);	//cgLabel
    }

    /**
     * updateTabPlacement
     *
     * @param _revalidate
     * @author Anthony C. Liberto
     */
    public void updateTabPlacement(boolean _revalidate) {
        if (nav[0] != null) {
            nav[0].updateTabPlacement(_revalidate);
        }
        if (nav[1] != null) {
            nav[1].updateTabPlacement(_revalidate);
        }
    }

    /**
     * setGif - not used but in interface
     */
    public void setGif(String _s) {}
//    sGif = _s;
  //  }

    /**
     * getGif- not used but in interface
     */
    public String getGif() { return null;}
    //    return sGif;
   // }

    /**
     * setSessionTagText
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setSessionTagText(String _s) {
        String msg = null;
        if (_s != null) {
            setCode("sesLbl");
            setParm(_s);
            msg = getMessage();
            sesLbl.setText(msg);
            sesLbl.setToolTipText(msg);
        }
        sesLbl.setVisible(_s != null);
    }

    /**
     * addContent
     * @param _position
     * @param _jc
     * @author Anthony C. Liberto
     */
    private void addContent(String _position, JComponent _jc) {
        add(_position, _jc);
    }

    /**
     * setPin
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setPin(boolean _b) {
        bPin = _b;
    }

    /**
     * isPin
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPin() {
        return bPin;
    }

    /**
     * generateForm
     * @author Anthony C. Liberto
     */
    private void generateForm() {
        nav = new Navigate[2];
        nav[0] = new Navigate(this, 0);

        // reduce memory use and improve perf by not creating double navs if not needed      
        if (getHTMLFileName().equals("nav_dual.html")){
        	nav[1] = new Navigate(this, 1);
        }else{
        	nav[1] =null;
        }
    }

    /**
     * init
     * @param _hist
     * @param _prev
     * @param _formName
     * @param _navType
     * @return
     * @author Anthony C. Liberto
     */
    private ENavForm init(Object _hist, Object _prev, String _formName, int _navType) {
        EannounceToolbar bar = null;
        form = new EForm(this, _prev, _formName);
        nav[0].setForm(form);
        if (nav[1] != null) {
        nav[1].setForm(form);
        }
        form.generateForm();
        form.setDoubleBuffered(true);
        if (!formMap.containsKey(_formName)) {
            formMap.put(_formName, form);
        }
        //cr_209046022		eannounceToolbar bar = getEToolbar();
        //cr_209046022		if (bar != null) {
        //cr_209046022			pnlMain.add(bar.getAlignment(), bar);
        //cr_209046022		}
        pnlMain.add("Center", form);
        if (_hist != null) {
            nav[0].loadHistory(_hist, _navType);
            if (nav[1] != null) {
            nav[1].loadHistory(_hist, _navType);
            }
        }
        nav[0].setCurrentNavigate();
        bar = getEToolbar();
        if (bar != null) {
            pnlMain.add(bar.getAlignment(), bar);
        }
        return this;
    }

    //51922	public static void loadFromFile(Object _history, EntityList _list, String _gif, Integer _navType, Profile _prof) {
    /**
     * loadFromFile
     * @param _parTab
     * @param _history
     * @param _list
     * @param _gif
     * @param _navType
     * @param _prof
     * @return
     * @author Anthony C. Liberto
     * /
    public static ENavForm loadFromFile(ETabable _parTab, Object _history, EntityList _list, String _gif, Integer _navType, Profile _prof) { //51922
        return loadFromFile(_parTab, _history, _list, _gif, _navType.intValue(), _prof); //51922
    }*/

    //51922	public static void loadFromFile(Object _history, EntityList _list, String _gif, int _navType, Profile _prof) {
    /**
     * loadFromFile
     * @param _parTab
     * @param _history
     * @param _list
     * @param _gif
     * @param _navType
     * @param _prof
     * @return
     * @author Anthony C. Liberto
     */
    public static ENavForm loadFromFile(ETabable _parTab, Object _history, EntityList _list, String _gif, int _navType, Profile _prof) { //51922
        ENavForm out = new ENavForm();
        //51392		out.prof = _list.getProfile();
        out.prof = eaccess().getNewProfileInstance(_list.getProfile()); //51392
        out.setParentProfile(_prof);
        out.generateForm();
        out.init(_history, _list, out.getHTMLFileName(), _navType);
        if (_gif == null) { //52471
            out.setBookmark(false); //52471
        } else { //52471
            out.setBookmark(_gif.equals(TYPE_BOOKMARK)); //kehrli_20030929
        } //52471
        out.getTabTitle("navTab", out.prof);
        //kehrli_20030929		out.addTab(_parTab,sTitle,sTitle,_gif,out,"");
        out.addTab(_parTab, out); //kehrli_20030929
        return out; //51922
    }

    //cr_1210035324	public eNavForm init(Object _prev, String _formName) {
    /**
     * init
     * @param _prev
     * @param _item
     * @param _formName
     * @return
     * @author Anthony C. Liberto
     */
    public ENavForm init(Object _prev, BookmarkItem _item, String _formName) { //cr_1210035324
        EannounceToolbar bar = null;
        form = new EForm(this, _prev, _formName);
        nav[0].setForm(form);
        if (nav[1] != null) {
        	nav[1].setForm(form);
        }
        form.generateForm();
        form.setDoubleBuffered(true);
        if (!formMap.containsKey(_formName)) {
            formMap.put(_formName, form);
        }
        bar = getEToolbar();
        if (bar != null) {
            pnlMain.add(bar.getAlignment(), bar);
        }
        pnlMain.add("Center", form);
        nav[0].loadBookmarkHistory(_item);
        
        //53729		if (nav[1] != null) {
        //53729			nav[1].loadBookmarkHistory(_item);
        //53729		}
        nav[0].setCurrentNavigate();
        return this;
    }

    /**
     * loadFromFile
     * @param _parTab
     * @param _list
     * @param _book
     * @param _gif
     * @return
     * @author Anthony C. Liberto
     */
    public static ENavForm loadFromFile(ETabable _parTab, EntityList _list, BookmarkItem _book, String _gif) { //cr_1210035324
        if (eaccess().getPrefBoolean(PREF_LOAD_BOOKMARK, DEFAULT_LOAD_BOOKMARK)) {
            ENavForm out = new ENavForm();
            out.prof = _list.getProfile();
            out.setParentProfile(out.prof); //51318
            out.generateForm();
            //cr_1210035324			out.init(_list,out.getHTMLFileName());
            out.init(_list, _book, out.getHTMLFileName()); //cr_1210035324
            out.getTabTitle("navTab", out.prof);
            //kehrli_20030929			out.addTab(_parTab,sTitle,sTitle,_gif,out,"");
            if (_gif == null) { //52471
                out.setBookmark(false); //52471
            } else { //52471
                out.setBookmark(_gif.equals(TYPE_BOOKMARK)); //kehrli_20030929
            } //52471
            out.addTab(_parTab, out); //kehrli_20030929
            return out; //51922
        } else {
            ETabable tab = eaccess().getCurrentTab();
            Profile listProf = null;
            Navigate tmpNav = null;
            if (tab != null) {
                if (tab.isPanelType(TYPE_ENAVFORM)) {
                    ENavForm out = (ENavForm) tab;
                    if (_gif == null) { //52471
                        out.setBookmark(false); //52471
                    } else { //52471
                        out.setBookmark(_gif.equals(TYPE_BOOKMARK)); //kehrli_20030929
                    } //52471
                    tmpNav = out.getNavigate();
                    if (tmpNav != null) {
                        tmpNav.resetHistory();
                        tmpNav.load(_list, _book, Navigate.NAVIGATE_INIT_LOAD); //cr_1210035324
                        //cr_1210035324						tmpNav.load(_list,navigate.NAVIGATE_INIT_LOAD);
                        //kehrli_20030929						eaccess().setIconAt(tab,_gif);
                        listProf = _list.getProfile(); //53562
                        if (listProf != null) { //53562
                            out.prof = listProf; //53562
                            eaccess().setActiveProfile(listProf); //53562
                            eaccess().setTitleAt(tab, out.getTabTitle("navTab", listProf)); //53562
                        } //53562
                    }

                    return out; //51922
                }
            }
        }
        return null; //51922
    }

    /**
     * loadFromFile
     * @param _list
     * @param _gif
     * @return
     * @author Anthony C. Liberto
     */
    public static ENavForm loadFromFile(EntityList _list, String _gif) {
        ENavForm out = new ENavForm();
        String sTitle = null;
        out.prof = _list.getProfile();
        out.setParentProfile(out.prof);
        out.generateForm();
        out.init(_list, null, out.getHTMLFileName());
        sTitle = out.getTabTitle("navTab", out.prof);
        eaccess().addTab(null, out, _gif, sTitle);
        return out;
    }

    /**
     * launch
     * @return
     * @author Anthony C. Liberto
     * /
    public static ENavForm launch() {
        ENavForm out = null;
        Long t1 = eaccess().timestamp("eNavForm.launch():00 at: ");
        out = new ENavForm();
        eaccess().timestamp("eNavForm.launch():01 at: ",t1);
        out.launchChoose(EPanel.eaccess().getActiveProfile(), false);
        eaccess().timestamp("eNavForm.launch():02 at: ",t1);
        return out;
    }*/

    /**
     * getHTMLFileName
     * @return
     * @author Anthony C. Liberto
     */
    private String getHTMLFileName() {
        if (EAccess.isArmed(ACCESSIBLE_ARM_FILE)) {
            return getString("eannounce.accessible.default.form");
        }
        return EAccess.eaccess().getPrefString(LOOK_AND_FEEL, getString("eannounce.default.form")); //"nav.html");
    }

    /**
     * launch
     * @param _p
     * @param _sep
     * @return
     * @author Anthony C. Liberto
     */
    public static ENavForm launch(Profile _p, boolean _sep) {
        ENavForm out = new ENavForm();
        out.launchChoose(_p, _sep);
        return out;
    }

    /**
     * launchChoose
     * @param _prof
     * @param _sepThread
     * @author Anthony C. Liberto
     */
    private void launchChoose(Profile _prof, boolean _sepThread) {
        if (_sepThread) {
            launchSep(_prof);
        } else {
            launchSame(_prof);
        }
    }

    private void launchSep(Profile _prof) {
        prof = _prof;
        final ESwingWorker eWorker = new ESwingWorker() {
        	Long t1= eaccess().timestamp("eNavForm.launch(Profile):00 at: ");
            public Object construct() {
                int navIndex = eaccess().getNavigateIndex(prof);
                int reply = 0;
                if (navIndex >= 0) {
                    setCode("msg12001.0");
                    reply = showConfirm(YES_NO, true);
                    Long t2  =eaccess().timestamp("eNavForm.launch(Profile):09 at: ");
                    if (reply == YES) {
                        prof = eaccess().getNewProfileInstance();
                        eaccess().timestamp("eNavForm.launch(Profile):11 at: ",t2);
                        //51267					} else if (reply == NO) {
                    } else { //51267
                        eaccess().setSelectedIndex(navIndex);
                        eaccess().timestamp("eNavForm.launch(Profile):13 at: ",t2);
                        return null;
                    }
                    eaccess().timestamp("eNavForm.launch(Profile):14 at: ",t2);
                }
                eaccess().setProcessTime(prof, eaccess().getNow(END_OF_DAY));
                eaccess().timestamp("eNavForm.launch(Profile):15 at: ",t1);
                generateForm();
                eaccess().timestamp("eNavForm.launch(Profile):16 at: ",t1);
                if (lockList == null) {
                    lockList = dBase().getLockList(false, pnlMain);
                }
                eaccess().timestamp("eNavForm.launch(Profile):19 at: ",t1);
                return init(null, null, getHTMLFileName());
            }

            public void finished() {
                Object o = getValue();
                eaccess().timestamp("eNavForm.launch(Profile):21 at: ",t1);
                if (o instanceof ENavForm) {
                    getTabTitle("navTab", prof);
                    addTab(null, (ENavForm) o); //kehrli_20030929
                    repaint();  
                }
                eaccess().timestamp("eNavForm.launch(Profile):24 at: ",t1);
                setWorker(null);
                setBusy(false);
            }
        };
        eWorker.setInterruptable(false);
        setWorker(eWorker);
    }

    private void launchSame(Profile _prof) {
        Object o = null;
        int reply = 0;
        int navIndex = 0;
        Long t1 =eaccess().timestamp("eNavForm.launchSame(Profile):00 at: ");
        prof = _prof;
        navIndex = eaccess().getNavigateIndex(prof);
        if (navIndex >= 0) {
            setCode("msg12001.0");
            reply = showConfirm(YES_NO, true);
            Long t2 = eaccess().timestamp("eNavForm.launchSame(Profile):05 at: ");
            if (reply == YES) {
                prof = eaccess().getNewProfileInstance();
                eaccess().timestamp("eNavForm.launchSame(Profile):07 at: ",t2);
            } else { //51267
                eaccess().setSelectedIndex(navIndex);
                eaccess().timestamp("eNavForm.launchSame(Profile):09 at: ",t2);
                return;
            }
            eaccess().timestamp("eNavForm.launchSame(Profile):10 at: ",t2);
        }
        eaccess().setProcessTime(prof, eaccess().getNow(END_OF_DAY));
        eaccess().timestamp("eNavForm.launchSame(Profile):11 at: ",t1);
        generateForm();
        eaccess().timestamp("eNavForm.launchSame(Profile):12 at: ",t1);
        if (lockList == null) {
            lockList = dBase().getLockList(false, pnlMain);
            eaccess().timestamp("eNavForm.launchSame(Profile):14 at: ",t1);
        }
        o = init(null, null, getHTMLFileName());
        eaccess().timestamp("eNavForm.launchSame(Profile):16 at: ",t1);
        if (o instanceof ENavForm) {
            getTabTitle("navTab", prof);
            eaccess().timestamp("eNavForm.launchSame(Profile):18 at: ",t1);
            addTab(this, (ENavForm) o); //kehrli_20030929
            eaccess().timestamp("eNavForm.launchSame(Profile):19 at: ",t1);
            repaint();
        }
        eaccess().timestamp("eNavForm.launchSame(Profile):21 at: ",t1);
    }

    /**
     * getTableTitle
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getTableTitle() {
        return getTabTitle("navTab", prof);
    }

    /**
     * getProfile
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Profile getProfile() {
        return prof;
    }

    /**
     * actionPerformed
     *
     * @author Anthony C. Liberto
     * @param _ae
     */
    public void actionPerformed(ActionEvent _ae) {
        if (curNav == null) {
            if (nav[0] != null) {
                nav[0].performAction(_ae);
            } else if (nav[1] != null) {
                nav[1].performAction(_ae);
            }
        } else if (curNav != null) { //52715
            curNav.performAction(_ae);
        }
    }

    /**
     * getNavigate
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public Navigate getNavigate(int _i) {
        return nav[_i];
    }

    /**
     * getNavigate
     * @return
     * @author Anthony C. Liberto
     */
    public Navigate getNavigate() {
        return curNav;
    }

    /**
     * setNavigate
     * @param _i
     * @param _nav
     * @author Anthony C. Liberto
     *
    public void setNavigate(int _i, Navigate _nav) {
        nav[_i] = _nav;
    }*/

    /**
     * setCurrentNavigate
     * @param _nav
     * @return
     * @author Anthony C. Liberto
     */
    public boolean setCurrentNavigate(Navigate _nav) {
        if (curNav != _nav) {
            curNav = _nav;
            setSessionTagText(_nav.getTagDisplay());
            //52703			appendLog("<navSelect side=\"" + _nav.getSide() + "\" key=\"" + _nav.getKey() + "\" opwgid=\"" + _nav.getOPWGID() + "\"/>");
            if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //52703
                appendLog("<navSelect side=\"" + _nav.getSide() + "\" key=\"" + new String(_nav.getKey()) + "\" opwgid=\"" + _nav.getOPWGID() + "\"/>"); //52703
            } //52703
            repaint();
            return true;
        }
        return false;
    }

    /**
     * getCurrentNavigate
     * @return
     * @author Anthony C. Liberto
     * /
    public Navigate getCurrentNavigate() {
        return curNav;
    }*/

    /*
     * tabbable
     */
    /**
     * setParentKey
     *
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setParentKey(String _s) {
    }

    /**
     * getParentKey
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getParentKey() {
        if (curNav != null) {
            return curNav.getKey();
        }
        return null;
    }

    /**
     * setOPWGID
     *
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setOPWGID(int _i) {
    }

    /**
     * getOPWGID
     *
     * @return
     * @author Anthony C. Liberto
     */
    public int getOPWGID() {
        Profile myProf = getProfile();
        if (myProf != null) {
            return myProf.getOPWGID();
        }
        return -1;
    }

    /**
     * setShouldRefresh
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setShouldRefresh(boolean _b) {
        if (curNav != null) {
            curNav.setShouldRefresh(_b);
        }
    }
    /*
     1.2h
    	public void setShouldRefresh(String _eType, int _opwg) {
    		if (_eType.equals(getParentKey()) && (_opwg == getOPWGID())) {
    			setShouldRefresh(true);
    		}
    		return;
    	}
    */

    /**
     * shouldRefresh
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean shouldRefresh() {
        if (curNav != null && curNav.shouldRefresh()) {
            if (eaccess().alwaysRefresh()) { //cr_3274
                eaccess().repaintImmediately(); //cr_3274
                return true; //cr_3274
            } else if (eaccess().neverRefresh()) { //cr_3274
            } else { //cr_3274
                setCode("msg11012");
                if (showConfirm(YES_NO, true) == YES) {
                    eaccess().repaintImmediately();
                    return true;
                }
            } //cr_3274
        }
        curNav.setShouldRefresh(false); //acl_20031211
        return false;
    }

    /**
     * getEntityType
     *
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public String getEntityType(int _i) {
        if (curNav != null) {
            return curNav.getEntityType(_i);
        }
        return null;
    }

    /**
     * load
     * @param _eList
     * @param _book
     * @author Anthony C. Liberto
     * /
    public void load(EntityList _eList, BookmarkItem _book) {
        if (curNav != null) {
            curNav.load(_eList, _book, EAccessConstants.NAVIGATE_LOAD);
        }
    }*/

    /**
     * loadNextForm
     * @author Anthony C. Liberto
     */
    public void loadNextForm() {
        String nextForm = form.getNextForm();
        String curForm = form.getName();
        EannounceToolbar bar = null;
        if (nextForm == null || curForm == null) {
            return;
        }
        remove(form);
        remove(form.getEToolbar());
        if (formMap.containsKey(nextForm)) {
            form = (EForm) formMap.get(nextForm);
            bar = getEToolbar();
            pnlMain.add(bar.getAlignment(), bar);
            pnlMain.add("Center", form);
            nav[0].setCurrentNavigate();
        } else {
            init(this, null, nextForm);
        }
    }

    /**
     * loadPrevForm
     * @author Anthony C. Liberto
     */
    public void loadPrevForm() {
        String prevForm = form.getPrevForm();
        EannounceToolbar bar = null;
        if (prevForm == null) {
            return;
        }
        if (!formMap.containsKey(prevForm)) {
            return;
        }
        remove(form);
        remove(form.getEToolbar());
        form = (EForm) formMap.get(prevForm);
        nav[0].setForm(form);
        if (nav[1] != null) {
        	nav[1].setForm(form);
        }
        bar = getEToolbar();
        pnlMain.add(bar.getAlignment(), bar);
        pnlMain.add("Center", form);
        nav[0].setCurrentNavigate();
    }

    /**
     * getMenu
     * @return
     * @author Anthony C. Liberto
     */
    private EMenubar getMenu() {
        if (form == null) {
            return null;
        }
        return form.getMenu();
    }

    /**
     * getEToolbar
     * @return
     * @author Anthony C. Liberto
     */
    private EannounceToolbar getEToolbar() {
        return form.getEToolbar();
    }

    /**
     * @see java.awt.Component#requestFocus()
     * @author Anthony C. Liberto
     */
    public void requestFocus() {
        if (curNav == null) {
            if (nav[0] != null) {
                nav[0].requestFocus();
            } else if (nav[1] != null) {
                nav[1].requestFocus();
            } else {
                super.requestFocus();
            }
        } else {
            curNav.requestFocus();
        }
    }

    /**
     * setDefaultFocus
     * @author Anthony C. Liberto
     * /
    public void setDefaultFocus() {
        Component c = form.getDefaultFocus();
        if (c != null) {
            c.requestFocus();
        }
    }*/

    /**
     * refresh
     *
     * @param _breakable
     * @author Anthony C. Liberto
     */
    public void refresh(boolean _breakable) {
        if (curNav != null) {
            curNav.refresh(_breakable);
        }
    }

    /**
     * navigateClose
     * @param _i
     * @author Anthony C. Liberto
     *
    public void navigateClose(int _i) {
        if (nav[_i] != null) {
            nav[_i].dereference();
        }
    }*/

    /**
     * setEnabled
     * @param _key
     * @param _enabled
     * @author Anthony C. Liberto
     * /
    public void setEnabled(String _key, boolean _enabled) {
        if (curNav != null) {
            curNav.setEnabled(_key, _enabled);
        }
    }*/

    /**
     * getCurrentEntityItem
     * @return
     * @author Anthony C. Liberto
     * /
    public EntityItem getCurrentEntityItem() { //eList
        if (curNav == null) {
            if (nav[0] != null) {
                return nav[0].getCurrentEntityItem(true);
            } else if (nav[1] != null) {
                return nav[1].getCurrentEntityItem(true);
            } else {
                return null;
            }
        }
        return curNav.getCurrentEntityItem(true); //eList
    } //eList
    */

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        strCG = null;
        if (formMap != null) {
            formMap.clear();
            formMap = null;
        }
        if (nav!=null){
        	for (int i = 0; i < nav.length; ++i) {
        		if (nav[i] != null) {
        			nav[i].dereference();
        			nav[i] = null;
        		}
        	}
        }
        if (split != null) {
            split.dereference();
            split = null;
        }
        //sGif = null;
        nav = null;
        dereferenceRelated(); //51975
        form.dereference();
        form = null;
        removeAll();
        removeNotify();
    }

    /**
     * close
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean close() {
        return true;
    }

    //52614	public void select() {
    /**
     * select
     *
     * @param _bCloseAll
     * @author Anthony C. Liberto
     */
    public void select(boolean _bCloseAll) { //52614
        if (curNav != null) {
            curNav.select();
        }
        eaccess().setActiveProfile(prof);
        setBusy(true);
        eaccess().setEMenuBar(getMenu());
        if (lockList == null) {
            lockList = dBase().getLockList(false, this);
        }
        eaccess().setLockList(lockList);

        if (!_bCloseAll && shouldRefresh()) { //52614
            setModalBusy(true);
            if (nav[0] != null && nav[0].isShowing() && nav[0].shouldRefresh()) {
                nav[0].refresh(false);
            }
            if (nav[1] != null && nav[1].isShowing() && nav[1].shouldRefresh()) {
                nav[1].refresh(false);
            }
            setModalBusy(false);
            eaccess().setMenuEnabled(SYSTEM_MENU, "resetDate", true); //51295
            setBusy(false);
            return;
        }
        requestFocus();
        eaccess().setFilter(isFiltered());
        eaccess().setHidden(false); //52476
        eaccess().setMenuEnabled(SYSTEM_MENU, "resetDate", true); //50495
        //replay_log		appendLog("<tabSelect tab=\"" + getPanelType() + "\" parentkey=\"" + getParentKey() + "\" opwgid=\"" + getOPWGID() + "\"/>");
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //replay_log
            appendLog("<tabSelect tab=\"" + getPanelType() + "\" parentkey=\"" + new String(getParentKey()) + "\" opwgid=\"" + getOPWGID() + "\"/>"); //replay_log
        } //replay_log
        setBusy(false);
    }

    /**
     * isHidden
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isHidden() {
        return false;
    }*/

    /**
     * isFiltered
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isFiltered() {
        if (curNav != null) {
            return curNav.isFiltered();
        }
        return false;
    }

    /**
     * contains
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _eai
     * @param _ei
     */
    public boolean contains(EntityItem[] _ei, EANActionItem _eai) {
        EntityList eList = curNav.getCurrentEntityList();
        return eList.equivalent(_ei, _eai);
    }

    /**
     * getSearchableObject
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Object getSearchableObject() {
        if (curNav != null) {
            return curNav.getSearchableObject();
        }
        return null;
    }

    /**
     * getHelpText
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getHelpText() {
        if (curNav != null) {
            return curNav.getHelpText();
        }
        return getString("nia");
    }

    /**
     * refreshAppearance
     *
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        for (int i = 0; i < nav.length; ++i) {
            if (nav[i] != null) {
                nav[i].refreshAppearance();
            }
        }
    }

    /**
     * getPanelType
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getPanelType() {
        return TYPE_ENAVFORM;
    }

    /**
     * getVisiblePanelType
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getVisiblePanelType() {
        return getPanelType();
    }

    /**
     * setParentProfile
     *
     * @author Anthony C. Liberto
     * @param _prof
     */
    public void setParentProfile(Profile _prof) {
        parentProfile = _prof;
    }

    /**
     * getParentProfile
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Profile getParentProfile() {
        if (parentProfile != null) { //51318
            return parentProfile;
        } //51318
        return getActiveProfile(); //51318
    }
    /*
     1.2h
     */
    /**
     * isMultipleNavigate
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isMultipleNavigate() {
        if (nav[0] != null && nav[0].isShowing()) {
            if (nav[1] != null && nav[1].isShowing()) {
                return true;
            }
        }
        return false;
    }

    /**
     * getOpposingNavigate
     * @param _nav
     * @return
     * @author Anthony C. Liberto
     */
    public Navigate getOpposingNavigate(Navigate _nav) {
        for (int i = 0; i < nav.length; ++i) {
            if (nav[i] != _nav) {
                return nav[i];
            }
        }
        return null;
    }

    /**
     * getSelectedBorder
     * @param _nav
     * @return
     * @author Anthony C. Liberto
     */
    public Border getSelectedBorder(Navigate _nav) {
        if (_nav != null) {
            if (curNav == _nav) {
                return selectedBorder;
            } else {
                return nonSelectedBorder;
            }
        }
        return null;
    }

    /**
     * getCart
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public NavCartDialog getCart(int _i) {
        if (_i < nav.length) {
            if (nav[_i] != null) {
                return nav[_i].getCart();
            }
        }
        return null;
    }

    /**
     * getSplit
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public ESplitPane getSplit(int _i) {
        if (split == null) {
            split = new ESplitPane(_i) {
            	private static final long serialVersionUID = 1L;
            	public void dividerToggle(int _i) {
                    switch (_i) {
                        case NAVIGATE_DIVIDER_MINIMIZED :
                        case NAVIGATE_DIVIDER_MAXIMIZED :
                            nav[_i].setCurrentNavigate();
                            break;
                        default :
                            break;
                    }
                }
            };
        }
        return split;
    }

    /**
     * refreshBothSides
     * @author Anthony C. Liberto
     */
    public void refreshBothSides() {
        Navigate tmpNav = null;
        setModalBusy(true);
        tmpNav = getOpposingNavigate(curNav);
        if (tmpNav != null) {
            if (tmpNav.isShowing()) {
                tmpNav.refresh(false);
            }
        }
        tmpNav = getOpposingNavigate(tmpNav);
        if (tmpNav != null) {
            if (tmpNav.isShowing()) {
                tmpNav.refresh(false);
            }
        }
        setModalBusy(false);
    }

    /**
     * process
     *
     * @author Anthony C. Liberto
     * @param _action
     * @param _child
     * @param _method
     * @param _parent
     */
    public void process(String _method, String _action, String[] _parent, String[] _child) {
        if (curNav != null) {
            curNav.process(_method, _action, _parent, _child);
        }
    }

    /**
     * select
     * @param _side
     * @param _key
     * @param _opwg
     * @author Anthony C. Liberto
     */
    public void select(int _side, String _key, int _opwg) {
    	if (nav[_side] != null) {
    		nav[_side].setCurrentNavigate();
    	}
    }

    /*
     acl_20030718
     */
    /**
     * selectKeys
     *
     * @author Anthony C. Liberto
     * @param _keys
     */
    public void selectKeys(String[] _keys) {
        if (curNav != null) {
            curNav.selectKeys(_keys);
        }
    }
    /*
     51555
     */

    /**
     * setShouldRefresh
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _eType
     * @param _opwg
     */
    public void setShouldRefresh(String _eType, int _opwg, int _code) {
        if (_eType != null) {
            if (_opwg == getOPWGID()) { //53497
                //53495				if (nav[0] != null && nav[0].isType(_eType,_code)) {			//53497
                if (nav[0] != null && !nav[0].isDereferenced() && nav[0].isType(_eType, _code)) { //53495
                    nav[0].setShouldRefresh(true); //53497
                } //53497
                //53495				if (nav[1] != null && nav[1].isType(_eType,_code)) {			//53497
                if (nav[1] != null && !nav[1].isDereferenced() && nav[1].isType(_eType, _code)) { //53495
                    nav[1].setShouldRefresh(true); //53497
                } //53497
            } //53497
        }
    }

    /**
     * setParentTab
     *
     * @author Anthony C. Liberto
     * @param _tab
     */
    public void setParentTab(ETabable _tab) {
        parTab = _tab;
        if (parTab != null) {
            parTab.addChildTab(this);
        }
    }

    /**
     * getParentTab
     *
     * @return
     * @author Anthony C. Liberto
     */
    public ETabable getParentTab() {
        return parTab;
    }

    /**
     * addChildTab
     *
     * @author Anthony C. Liberto
     * @param _tab
     */
    public void addChildTab(ETabable _tab) {
        vChildTab.add(_tab);
    }

    /**
     * removeChildTab
     *
     * @author Anthony C. Liberto
     * @return eTabable
     * @param _tab
     */
    public ETabable removeChildTab(ETabable _tab) {
        if (vChildTab.remove(_tab)) {
            return _tab;
        }
        return null;
    }

    /**
     * dereferenceRelated
     *
     * @author Anthony C. Liberto
     */
    private void dereferenceRelated() {
        while (!vChildTab.isEmpty()) {
            ETabable tab = (ETabable) vChildTab.remove(0);
            tab.setParentTab(getParentTab());
        }
        vChildTab = null;
        if (parTab != null) {
            parTab.removeChildTab(this);
            parTab = null;
        }
    }

    /*
     52189
     */
    /**
     * deselect
     *
     * @author Anthony C. Liberto
     */
    public void deselect() {
        if (curNav != null) {
            curNav.deselect();
        }
    }
    /*
     kehrli_20030929
     */
    /**
     * getTabToolTipText
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getTabToolTipText() {
        if (curNav != null) {
            return curNav.getTabToolTipText();
        }
        return null;
    }

    /**
     * getTabTitle
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getTabTitle() {
        if (curNav != null) {
            return curNav.getTabTitle();
        }
        return null;
    }

    /**
     * getTabMenuTitle
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getTabMenuTitle() {
        if (curNav != null) {
            return curNav.getTabMenuTitle();
        }
        return null;
    }

    /**
     * getTabIcon
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Icon getTabIcon() {
        if (curNav != null) {
            return curNav.getTabIcon();
        }
        return null;
    }

    /**
     * isBookmark
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isBookmark() {
        return bMark;
    }

    /**
     * setBookmark
     * @param _b
     * @author Anthony C. Liberto
     */
    private void setBookmark(boolean _b) {
        bMark = _b;
    }

    /**
     * getChatAction
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public ChatAction getChatAction() {
        if (curNav != null) {
            return curNav.getChatAction();
        }
        return null;
    }*/

    /*
     cr_209046022
     */
    /**
     * addToolbar
     * @param _bar
     * @author Anthony C. Liberto
     */
    public void addToolbar(EannounceToolbar _bar) {
        if (_bar != null) {
            pnlMain.add(_bar.getAlignment(), _bar);
        }
    }
//cgLabel
    /**
     * setChangeGroupTagText
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setChangeGroupTagText(String _s) {
        String msg = null;
        if (_s != null) {
			strCG = new String(_s);
            setCode("cgLbl");
            setParm(_s);
            msg = getMessage();
            cgLbl.setText(msg);
            cgLbl.setToolTipText(msg);
        }
        cgLbl.setVisible(_s != null);
    }
    /**
     * getChangeGroupTagText
     *
     * @author Anthony C. Liberto
     * @return String
     */
	public String getChangeGroupTagText() {
		if (strCG == null) {
			return "";
		}
		return strCG;
	}
/*
 CR_7342
 */
	/**
     * getParentInformationAtLevel
     *
     * @author Anthony C. Liberto
     * @param _i
     * @return
     */
    public Object[] getParentInformationAtLevel(int _i) {
		if (curNav != null) {
			return curNav.getParentInformationAtLevel(_i);
		}
		return null;
	}

    /**
     * refresh toolbar
     * MN_button_disappear
     * @author Anthony C. Liberto
     */
    public void refreshToolbar() {
		if (curNav != null) {
			curNav.refreshToolbar();
		}
	}

}
