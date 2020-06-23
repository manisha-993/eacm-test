/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2003/03/19
 * @author Anthony C. Liberto
 *
 * $Log: BookmarkPanel.java,v $
 * Revision 1.4  2009/09/03 19:34:36  wendy
 * Make copy of action when saving bookmark
 *
 * Revision 1.3  2009/05/26 12:29:24  wendy
 * Performance cleanup
 *
 * Revision 1.2  2008/01/30 16:26:54  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:43:24  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:10  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.17  2005/09/08 17:59:00  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.16  2005/02/09 18:55:23  tony
 * Scout Accessibility
 *
 * Revision 1.15  2005/02/04 15:22:08  tony
 * JTest Format Third Pass
 *
 * Revision 1.14  2005/02/03 16:38:52  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.13  2005/02/02 17:30:21  tony
 * JTest Second Pass
 *
 * Revision 1.12  2005/01/27 23:18:20  tony
 * JTest Formatting
 *
 * Revision 1.11  2004/09/07 21:03:44  tony
 * improved hourglass functionality.
 *
 * Revision 1.10  2004/08/26 16:26:35  tony
 * accessibility enhancement.  Adjusted menu and toolbar
 * appearance for the accessibility impaired.  Improved
 * accessibility performance by enabling preference toggling.
 *
 * Revision 1.9  2004/08/11 21:47:42  tony
 * 5ZKL3K performance enhancement.
 *
 * Revision 1.8  2004/08/11 21:24:24  tony
 * 5ZKL3K
 *
 * Revision 1.7  2004/04/22 18:00:41  tony
 * added toolbar button for refresh.
 *
 * Revision 1.6  2004/04/12 17:17:57  tony
 * 53796
 *
 * Revision 1.5  2004/03/24 16:16:26  tony
 * set duplicate mode
 *
 * Revision 1.4  2004/03/15 18:17:34  tony
 *  cr_6303 take two too many bookmarks
 *
 * Revision 1.3  2004/03/12 23:07:47  tony
 * cr_6303
 * send bookmark to a friend.
 *
 * Revision 1.2  2004/03/04 22:24:54  tony
 * removed display statement.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:27  tony
 * This is the initial load of OPICM
 *
 * Revision 1.29  2004/01/09 00:42:43  tony
 * cr_1210035324
 * Bookmarks generate a replayable history
 *
 * Revision 1.28  2003/12/11 16:45:01  tony
 * acl_20031211
 *
 * Revision 1.27  2003/12/04 23:18:05  tony
 * accessibility
 *
 * Revision 1.26  2003/10/29 00:26:43  tony
 * 52573
 *
 * Revision 1.25  2003/09/30 16:34:19  tony
 * kehrli_20030929 --
 * logic enhancement to improve modification
 * ability of tab icon, title, tip, and menu items for
 * future modifications.
 *
 * Revision 1.24  2003/09/11 21:45:52  tony
 * bookmark filter.
 *
 * Revision 1.23  2003/08/28 18:36:02  tony
 * 51975
 *
 * Revision 1.22  2003/06/30 18:04:48  tony
 * 51373
 *
 * Revision 1.21  2003/06/26 20:11:25  tony
 * updated rename bookmark function to prevent rename to
 * ""
 *
 * Revision 1.20  2003/06/20 22:35:55  tony
 * 51325
 *
 * Revision 1.19  2003/06/19 23:13:40  tony
 * 51217
 *
 * Revision 1.18  2003/06/12 22:23:40  tony
 * 1.2h modification enhancements.
 *
 * Revision 1.17  2003/05/30 21:09:19  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.16  2003/05/28 14:41:22  tony
 * 50916
 *
 * Revision 1.15  2003/04/21 20:03:12  tony
 * enhanced scroll logic to improve flag editor.
 *
 * Revision 1.14  2003/04/17 23:14:15  tony
 * 50406
 *
 * Revision 1.13  2003/04/15 20:17:25  tony
 * added bookmark limit.
 *
 * Revision 1.12  2003/04/11 17:32:22  tony
 * moved replay to a separate thread.
 * at end of load added logic to automatically close dialog.
 *
 * Revision 1.11  2003/04/09 22:16:08  tony
 * 50364
 *
 * Revision 1.10  2003/04/09 17:41:12  tony
 * set maximum length of bookmark description to 254 characters.
 *
 * Revision 1.9  2003/04/03 18:51:47  tony
 * adjusted logic to display individualized icon
 * for each frameDialog/tab.
 *
 * Revision 1.8  2003/04/01 17:12:52  tony
 * switched bookmark display to the bookmark table.
 *
 * Revision 1.7  2003/03/29 00:06:42  tony
 * added toolbar and rmeove menu logic.
 *
 * Revision 1.6  2003/03/28 17:01:33  tony
 * improved logic by enabling and disabling menu items
 * based on the selection and the value of the textfield.
 *
 * Revision 1.5  2003/03/24 21:52:23  tony
 * added modalCursor logic.
 *
 * Revision 1.4  2003/03/24 19:56:14  tony
 * System enhancements to improve usability and
 * functionality of the application.
 *
 * Revision 1.3  2003/03/20 18:12:31  tony
 * stickpin and bookmarking.
 *
 * Revision 1.2  2003/03/20 01:57:08  tony
 * bookmarking and pinning added to the system.
 *
 * Revision 1.1  2003/03/19 20:40:18  tony
 * *** empty log message ***
 *
 *
 */
package com.ibm.eannounce.dialogpanels;
import com.elogin.*;
import com.ibm.eannounce.eforms.toolbar.*;
import com.ibm.eannounce.eforms.table.*;
import com.ibm.eannounce.eobjects.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class BookmarkPanel extends AccessibleDialogPanel implements ListSelectionListener, PropertyChangeListener, EAccessConstants {
	private static final long serialVersionUID = 1L;
	//5ZKL3K	private GridBagLayout gridBag = new GridBagLayout();
    private EPanel pnlMain = new EPanel(new BorderLayout());
    //5ZKL3K	private ePanel pnlText = new ePanel(gridBag);
    private EPanel pnlText = new EPanel(new BorderLayout());
    private ELabel lblText = new ELabel(getString("bookmark.name"));
    private ELabel lblFilter = new ELabel(eaccess().getImageIcon("fltr.gif"));
    private EPropertyField eText = null;
    private BookmarkTable table = new BookmarkTable() {
    	private static final long serialVersionUID = 1L;
    	public void doubleClick() {
            setModalBusy(true);
            loadBookmark();
        }

        public void setFilterOn(boolean _b) {
            lblFilter.setEnabled(_b);
        }
    };
    private EScrollPane jsp = new EScrollPane(table);
//    private Rectangle defRect = new Rectangle(0, 0, 1, 1);

    private EannounceToolbar tBar = null;

    private BookmarkGroup bGroup = null;
    private EANActionItem eanAction = null;
    private EANActionItem[] eanActions = null;
    private ChooserPanel chooser = new ChooserPanel() {
    	private static final long serialVersionUID = 1L;
    	public Component getParentComponent() {
            return (Component) id;
        }
    };

    /**
     * bookmarkPanel
     * @author Anthony C. Liberto
     */
    public BookmarkPanel() {
        super(new BorderLayout());
        if (!EAccess.isAccessible()) {
            tBar = new EannounceToolbar(this);
            tBar.setFloatable(false);
        }
        eText = new EPropertyField(40, this, MINIMUM_TYPE, "0", DEACTIVATE);
        eText.setMaximumLength(254); //50353
        jsp.setFocusable(false);
        init();
    }

    /**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
        Dimension d = new Dimension(300, 200);
        createToolbar();

        pnlText.add("West", lblText); //5ZKL3K
        pnlText.add("Center", eText); //5ZKL3K
        pnlText.add("South", lblFilter); //5ZKL3K
        lblFilter.setEnabled(false); //5ZKL3K
        lblFilter.setHorizontalAlignment(SwingConstants.CENTER); //5ZKL3K
        lblFilter.setVerticalAlignment(SwingConstants.CENTER); //5ZKL3K
        lblText.setLabelFor(eText);

        pnlMain.add("North", pnlText);

        pnlMain.add("Center", jsp);
        if (tBar != null) {
            add("North", tBar);
        }
        add("Center", pnlMain);

        table.getSelectionModel().addListSelectionListener(this);

        jsp.setPreferredSize(d);
        jsp.setSize(d);
        setModalCursor(true); //51325
        menubar.setModalCursor(true); //51325
        setResizable(true); //5ZKL3K
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        table.getSelectionModel().removeListSelectionListener(this);
        if (jsp != null) {
            jsp.dereference();
            jsp = null;
        }
        if (chooser != null) {
            chooser = null;
        }
        super.dereference();
    }

    /**
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     * @author Anthony C. Liberto
     */
    public void propertyChange(PropertyChangeEvent _pce) {
        toggleSave();
    }

    private boolean canAdd() {
        if (!isBookmarkable()) { //50916
            return false; //50916
        } //50916
        if (table != null) {
            return (table.getRowCount() < BookmarkGroup.MAX_BOOKMARKITEMS);
        }
        return false;
    }

    private void toggleSave() {
        if (Routines.have(getText()) && canAdd()) {
            boolean selected = table.isValidSelection();
            menubar.setEnabled("save", true);
            menubar.setEnabled("renameMark", selected);
            if (tBar != null) {
                tBar.setEnabled("save", true);
                tBar.setEnabled("renameMark", selected);
            }

        } else {
            menubar.setEnabled("save", false);
            menubar.setEnabled("renameMark", false);
            if (tBar != null) {
                tBar.setEnabled("save", false);
                tBar.setEnabled("renameMark", false);
            }
        }
    }

    /**
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     * @author Anthony C. Liberto
     */
    public void valueChanged(ListSelectionEvent _lse) {
        if (!_lse.getValueIsAdjusting()) {
            boolean selected = table.isValidSelection();
            boolean bMultSelect = table.isMultipleSelection(); //50364
            boolean bHasText = Routines.have(getText());
            //50364			menubar.setEnabled("load",selected);
            menubar.setEnabled("load", selected && !bMultSelect); //50364
            menubar.setEnabled("remove", selected); //51214
            menubar.setEnabled("send", selected && !bMultSelect); //cr_6303
            menubar.setEnabled("renameMark", selected && bHasText && !bMultSelect);
            if (tBar != null) {
                //50364				tBar.setEnabled("load",selected);
                tBar.setEnabled("load", selected && !bMultSelect); //50364
                tBar.setEnabled("send", selected && !bMultSelect); //cr_6303
                tBar.setEnabled("renameMark", selected && bHasText && !bMultSelect); //51214
                tBar.setEnabled("remove", selected);
            }
        }
    }

    /**
     * load
     * @param _refresh
     * @author Anthony C. Liberto
     */
    private void load(boolean _refresh) {
        if (_refresh || bGroup == null) {
            bGroup = dBase().getBookmarkGroup(this);
            if (bGroup != null) {
                bGroup.setDupMode(BookmarkGroup.DUP_GEN_NEW_MODE);
            }
        }
        table.updateModel(new BookmarkGroupTable(bGroup, "test"));
    }

    /**
     * setActionItem
     * @param _eanAction
     * @author Anthony C. Liberto
     */
    public void setActionItem(EANActionItem _eanAction) {
        eanAction = _eanAction;
        if (eanAction != null) {
            if (isBookmarkable()) { //50916
                //52573				String str = eanAction.getLongDescription();
                String str = new String(eanAction.getLongDescription()); //52573
                setText(str);
                eText.setEnabled(true); //50916
            } else { //50916
                setText(getString("msg12004.0")); //50916
                eText.setEnabled(false); //50916
            } //50916
            toggleSave();
        }
        load(false);
    }

    /**
     * setText
     * @param _s
     * @author Anthony C. Liberto
     */
    private void setText(String _s) {
        eText.setText(_s);
    }

    /**
     * getText
     * @return
     * @author Anthony C. Liberto
     */
    private String getText() {
        return eText.getText();
    }

    /**
     * getMenuBar
     * @author Anthony C. Liberto
     * @return JMenuBar
     */
    public JMenuBar getMenuBar() {
        return menubar;
    }

    /**
     * createMenu
     * @author Anthony C. Liberto
     */
    protected void createMenu() {
        menubar.addMenu("File", "exit", this, KeyEvent.VK_F4, Event.ALT_MASK, true);
        menubar.setMenuMnemonic("File", 'F');

        menubar.addMenu("Edit", "f/r", this, KeyEvent.VK_F, Event.CTRL_MASK, true); //5ZKL3K
        menubar.setMenuMnemonic("Edit", 'E'); //5ZKL3K
        menubar.addMenu("Table", "srt", this, 0, 0, true); //5ZKL3K
        menubar.addMenu("Table", "fltr", this, KeyEvent.VK_F8, 0, true); //5ZKL3K
        menubar.setMenuMnemonic("Table", 'T'); //5ZKL3K
        menubar.addMenu("Action", "save", this, KeyEvent.VK_S, Event.CTRL_MASK, false);
        menubar.addMenu("Action", "load", this, KeyEvent.VK_L, Event.CTRL_MASK, false);
        menubar.addMenu("Action", "send", this, KeyEvent.VK_S, Event.CTRL_MASK + Event.SHIFT_MASK, false); //cr_6303
        menubar.addMenu("Action", "renameMark", this, KeyEvent.VK_R, Event.CTRL_MASK, false); //51217
        menubar.addMenu("Action", "remove", this, KeyEvent.VK_DELETE, Event.CTRL_MASK, false);
        menubar.addSeparator("Action"); //50406
        menubar.addMenu("Action", "rfrsh", this, KeyEvent.VK_F5, 0, true); //50406
        menubar.setMenuMnemonic("Action", 'A');
    }

    /**
     * createToolbar
     * @author Anthony C. Liberto
     */
    private void createToolbar() {
        if (tBar != null) {
            tBar.addButton("save.gif", "save", "save", false);
            tBar.addButton("load.gif", "load", "load", false);
            tBar.addButton("send.gif", "send", "send", false); //cr_6303
            tBar.addButton("renameMark.gif", "renameMark", "renameMark", false); //51217
            tBar.addButton("refresh.gif", "rfrsh", "rfrsh", true);
            tBar.addButton("waste.gif", "remove", "remove", false);
        }
    }

    /**
     * removeMenu
     * @author Anthony C. Liberto
     */
    public void removeMenu() {
        menubar.removeMenuItem("f/r", this); //5ZKL3K
        menubar.removeMenuItem("fltr", this); //5ZKL3K
        menubar.removeMenuItem("srt", this); //5ZKL3K

        menubar.removeMenuItem("exit", this);
        menubar.removeMenuItem("save", this);
        menubar.removeMenuItem("load", this);
        menubar.removeMenuItem("send", this); //cr_6303
        menubar.removeMenuItem("renameMark", this); //51217
        menubar.removeMenuItem("remove", this);
        menubar.removeMenuItem("rfrsh", this); //50406

        if (tBar != null) {
            tBar.remove("save");
            tBar.remove("load");
            tBar.remove("send"); //cr_6303
            tBar.remove("renameMark"); //51217
            tBar.remove("remove");
            tBar.remove("rfrsh");
        }
    }

    /**
     * showMe
     * @author Anthony C. Liberto
     */
    public void showMe() {
    }

    /**
     * hideMe
     * @author Anthony C. Liberto
     */
    public void hideMe() {
        eanAction = null;
        eanActions = null; //cr_1210035324
    }

    private void saveBookmark() {
    	try {
    		eanAction = new NavActionItem((NavActionItem)eanAction); // prevent null ptr in action profile after list is deref

    		//cr_1210035324		if (table.addBookmarkItem(eanAction,getText())) {
    		if (table.addBookmarkItem(eanAction, eanActions, getText())) { //cr_1210035324
    			setText("");
    		}
    	} catch (MiddlewareRequestException e) {
			e.printStackTrace();
		}
    }

    private void loadBookmark() {
        final ESwingWorker myWorker = new ESwingWorker() {
            final BookmarkItem item = getBookmark(); //cr_1210035324
            public Object construct() {
                //cr_1210035324				BookmarkItem item = getBookmark();
                if (item != null) {
                    return dBase().replay(item, pnlMain);
                }
                return null;
            }

            public void finished() {
                Object o = getValue();
                if (o != null && o instanceof EntityList) {
                    //51975					load((EntityList)o, "bookmark.gif");
                    //kehrli_20030929					load(null,(EntityList)o, "bookmark.gif");		//51975
                    //cr_1210035324					load(null,(EntityList)o, TYPE_bookMark);		//kehrli_20030929
                	eaccess().load(null, (EntityList) o, item, TYPE_BOOKMARK); //cr_1210035324
                    disposeDialog();
                }
                setWorker(null);
                setModalBusy(false);
                setBusy(false);
            }
        };
        setWorker(myWorker);
    }

    /**
     * getBookmark
     * @return
     * @author Anthony C. Liberto
     */
    private BookmarkItem getBookmark() {
        if (table != null) {
            return table.getBookmarkItem();
        }
        return null;
    }

    /**
     * removeBookmark
     * @author Anthony C. Liberto
     * /
    public void removeBookmark() {
        BookmarkItem item = getBookmark();
        if (item != null) {
            table.removeBookmark(item);
        }
    }*/

    /**
     * refreshMenu
     * @param _changed
     * @author Anthony C. Liberto
     * /
    public void refreshMenu(boolean _changed) {
    }*/

    /**
     * actionPerformed
     *
     * @author Anthony C. Liberto
     * @param _action
     */
    public void actionPerformed(String _action) {
        if (isModalBusy()) {
            return;
        }
        setModalBusy(true);
        if (_action.equals("exit")) {
            disposeDialog();
        } else if (_action.equals("f/r")) { //5ZKL3K
            find(); //5ZKL3K
        } else if (_action.equals("fltr")) { //5ZKL3K
            filter(); //5ZKL3K
        } else if (_action.equals("srt")) { //5ZKL3K
            sort(); //5ZKL3K
        } else if (_action.equals("save")) {
            saveBookmark();
        } else if (_action.equals("load")) {
            loadBookmark();
            return;
        } else if (_action.equals("renameMark")) { //51217
            renameBookmark(); //51217
        } else if (_action.equals("rfrsh")) { //50406
            load(true); //50406
        } else if (_action.equals("remove")) {
            //50364			removeBookmark();
            removeBookmarks(); //50364
            toggleSave();
        } else if (_action.equals("send")) { //cr_6303
            send(); //cr_6303
        } //cr_6303
        setModalBusy(false);
    }

    /**
     * getTitle
     * @author Anthony C. Liberto
     * @return String
     */
    public String getTitle() {
        return getString("bookmark.panel");
    }

    /**
     * getIconName
     * @author Anthony C. Liberto
     * @return String
     */
    public String getIconName() {
        return "bookmark.gif";
    }

    /*
     50364
    */
    /**
     * removeBookmarks
     * @author Anthony C. Liberto
     */
    private void removeBookmarks() {
        BookmarkItem[] items = getBookmarks();
        if (items != null) {
            table.removeBookmarks(items);
        }
    }

    /**
     * getBookmarks
     * @return
     * @author Anthony C. Liberto
     */
    private BookmarkItem[] getBookmarks() {
        if (table != null) {
            return table.getBookmarkItems();
        }
        return null;
    }

    /*
     50916
    */
    /**
     * isBookmarkable
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isBookmarkable() {
        if (eanAction != null) {
            return (eanAction instanceof NavActionItem);
        }
        return false;
    }
    /*
     51217
     */
    private void renameBookmark() {
        table.renameBookmark(table.getBookmarkItem(), getText());
    }

    /*
     acl_20031211
     */
    /**
     * reset
     * @author Anthony C. Liberto
     */
    public void reset() {
        bGroup = null;
    }
    /*
     cr_1210035324
     */
    /**
     * setActionItems
     * @param _items
     * @author Anthony C. Liberto
     */
    public void setActionItems(EANActionItem[] _items) {
        eanActions = _items;
    }

    /*
     cr_6303
     */
    private void showChooser() {
        showDialog((Component) id, chooser);
    }

    private void send() {
        final ESwingWorker myWorker = new ESwingWorker() {
            final BookmarkItem item = getBookmark();
            public Object construct() {
                ProfileSet pSet = dBase().getBuddies(item); //53796
                if (pSet != null) { //53796
                    chooser.load(pSet);
                    setModalBusy(false);
                    chooser.setBookmarkItem(item);
                    showChooser();
                } else { //53796
                    eaccess().showError((Component) id, "msg13000.0"); //53796
                } //53796
                return null;
            }

            public void finished() {
                setWorker(null);
                setModalBusy(false);
                setBusy(false);
            }
        };
        setWorker(myWorker);
    }

    /*
     5ZKL3K
     */
    /**
     * getSearchableObject
     * @author Anthony C. Liberto
     * @return Object
     */
    public Object getSearchableObject() {
        return table;
    }

    private void find() {
        table.showFind();
    }

    private void filter() {
        table.showFilter();
    }

    private void sort() {
        table.showSort();
    }
}
