/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EditForm.java,v $
 * Revision 1.7  2009/05/22 14:16:53  wendy
 * Performance cleanup
 *
 * Revision 1.6  2008/12/08 20:51:55  wendy
 * CI162 compliance
 *
 * Revision 1.5  2008/02/20 15:45:25  wendy
 * Prevent nullptr if deref called more than once
 *
 * Revision 1.4  2008/02/19 16:35:45  wendy
 * Add support for importing an XLS file and fix enhanced flag support
 *
 * Revision 1.3  2008/02/04 14:22:46  wendy
 * Cleanup more RSA warnings
 *
 * Revision 1.2  2008/01/30 16:27:09  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:18  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:12  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:55  tony
 * This is the initial load of OPICM
 *
 * Revision 1.16  2005/09/08 17:59:02  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.15  2005/03/01 19:30:11  tony
 * 6245 enahnced logic to query about current edit
 * before NLS is toggled.
 *
 * Revision 1.14  2005/02/04 16:57:42  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.13  2005/02/04 15:22:09  tony
 * JTest Format Third Pass
 *
 * Revision 1.12  2005/02/03 19:42:21  tony
 * JTest Third pass
 *
 * Revision 1.11  2005/01/31 20:47:45  tony
 * JTest Second Pass
 *
 * Revision 1.10  2005/01/27 19:15:16  tony
 * JTest Format
 *
 * Revision 1.9  2004/11/29 18:14:37  tony
 * USRO-R-RTAR-672S9Y updated paste activation logic.
 *
 * Revision 1.8  2004/11/18 18:02:37  tony
 * adjusted keying logic based on middleware changes.
 *
 * Revision 1.7  2004/08/19 20:57:51  tony
 * xl8r
 *
 * Revision 1.6  2004/08/19 16:58:16  tony
 * xl8r import enhancement
 *
 * Revision 1.5  2004/08/19 15:54:19  tony
 * enhanced xl8r logic
 *
 * Revision 1.4  2004/08/19 15:31:37  tony
 * xl8r
 *
 * Revision 1.3  2004/08/04 17:49:14  tony
 * adjusted logic to break function parameterization into
 * arm files and a new function directory.  This way
 * we will eliminate the possibility of translation to
 * accidentally change functionality.
 *
 * Revision 1.2  2004/02/26 21:53:17  tony
 * remoteDatabaseInterface centralization.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:39  tony
 * This is the initial load of OPICM
 *
 * Revision 1.40  2003/12/08 23:35:52  tony
 * 53344
 *
 * Revision 1.39  2003/11/24 22:14:16  tony
 * 53117
 *
 * Revision 1.38  2003/11/18 20:54:03  tony
 * added formEditorInterface to simplify formEditor modification.
 *
 * Revision 1.37  2003/10/22 17:15:46  tony
 * 52650
 * DEREFERENCE MODS FOR MEMORY
 *
 * Revision 1.36  2003/10/16 20:39:22  tony
 * 52494
 *
 * Revision 1.35  2003/10/13 17:15:52  tony
 * kc_20031012 -- updated date editor on form so that
 * it behaves similar to the editor on the table.
 *
 * Revision 1.34  2003/10/08 20:58:15  tony
 * 52490
 *
 * Revision 1.33  2003/09/15 20:50:40  tony
 * 52189
 *
 * Revision 1.32  2003/09/11 18:09:24  tony
 * acl_20030911
 * updated addRow logic to sort based on a boolean.
 *
 * Revision 1.31  2003/08/26 21:15:27  tony
 * mw_update_20030825
 *
 * Revision 1.30  2003/08/21 18:08:52  tony
 * 51865
 *
 * Revision 1.29  2003/08/21 15:57:34  tony
 * 51869
 *
 * Revision 1.28  2003/08/20 17:59:12  tony
 * 51834
 *
 * Revision 1.27  2003/07/22 15:47:19  tony
 * updated date editor capability to improve performance and
 * functionality.
 *
 * Revision 1.26  2003/07/09 20:47:34  tony
 * 51422 -- blobEditor popup on double click
 *
 * Revision 1.25  2003/07/07 15:26:20  tony
 * updated logic per Wayne to only report 0 errors for
 * spell check when manually invoked.
 *
 * Revision 1.24  2003/06/20 22:35:10  tony
 * 1.2 modification.
 *
 * Revision 1.23  2003/06/10 16:46:47  tony
 * 51260
 *
 * Revision 1.22  2003/05/30 21:09:22  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.21  2003/05/29 21:20:46  tony
 * adjusted setParm logic for single parm string.
 *
 * Revision 1.20  2003/05/29 19:05:19  tony
 * updated report launching.
 *
 * Revision 1.19  2003/05/28 19:59:49  tony
 * 50967
 *
 * Revision 1.18  2003/05/27 21:21:51  tony
 * updated url logic.
 *
 * Revision 1.17  2003/05/08 22:31:09  tony
 * 50550
 *
 * Revision 1.16  2003/05/07 18:03:23  tony
 * 50560
 *
 * Revision 1.15  2003/05/07 17:32:14  tony
 * 50563
 *
 * Revision 1.14  2003/05/06 18:56:32  tony
 * 50546
 *
 * Revision 1.13  2003/05/06 16:52:07  tony
 * updated verify logic to allow for unchanged items to
 * automatically allow information that is no longer valid as
 * long as it was allowed originally.
 *
 * Revision 1.12  2003/05/06 16:38:21  tony
 * acl_20030506 -- enhanced focus logic to prevent error
 * messages from firing when refocusing the component.
 *
 * Revision 1.11  2003/05/05 18:05:35  tony
 * 50526
 *
 * Revision 1.10  2003/05/02 20:05:55  tony
 * 50504 -- adjusted messaging and this seems to have
 * resolved the problem in local test.
 *
 * Revision 1.9  2003/04/29 19:53:14  tony
 * fixed looping issue on doubleClick lock()
 *
 * Revision 1.8  2003/04/18 14:40:44  tony
 * enhanced record toggle logic per KC multiple create
 * did not toggle thru records properly.
 *
 * Revision 1.7  2003/04/11 20:02:29  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eforms.editform;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.eforms.*;
import com.ibm.eannounce.eforms.editor.*;
//acl_20030506 import com.ibm.eannounce.eForms.editor.Verifier.Verifier;
import com.ibm.eannounce.eforms.edit.EditController;
import com.ibm.eannounce.eforms.edit.Importable;
import com.ibm.eannounce.eforms.edit.Editable;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.beans.*; //e1.0_RefreshHidden
import java.awt.event.*;
import java.awt.GridBagLayout;
//import javax.swing.border.*;
import COM.ibm.opicmpdh.transactions.*; //spelling

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EditForm extends EScrollPane 
implements KeyListener, PropertyChangeListener, FocusListener,
	MouseListener, FormEditorInterface, Importable,Editable
{ //e1.0_RefreshHidden
	private static final long serialVersionUID = 1L;
    private boolean bValidate = isArmed(FORM_VALIDATE_ARM_FILE);
   // private boolean ErrorFound = false;

    private int x = 0;
    private int y = 1; //e1.0_ClosePane
    private int tableX = 0;
    private int tableY = 1;
    //private int curRec = -1;

    private int NLS = 1; //e1.0_NLSTitle
   // private boolean memberNLS = true; //e1.0_NLS

    private boolean isTable = false;
    private HashMap map = new HashMap(); //classification
    private Vector vOrder = new Vector(); //classification
    private boolean bClassified = true; //classification
    private String XML = null;

    private Color fntColor = Color.black;
    private int fntSize = 11;
    private int fntStyle = 0;
    private String fntFace = "Arial,Helvetica";
    private Font defFont = new Font(fntFace, fntStyle, fntSize);

    private Color defColor = Color.black;
    private int defSize = 11;
    private String defFace = "Arial,Helvetica";

    private Color defBckColor = Color.decode("#CCCCCC");
    private Color bckColor = defBckColor;

    private boolean cmpOpaque = true;//false; if false, colors for required dont flow thru to TextEditors
    private String cmpToolTip = null;

    private boolean defOpaque = true;

    private EPanel mainPane = new EPanel();
    private EPanel scrollPanelWest = new EPanel(new BorderLayout()); //<--allows for WestJustify in viewport
    private EPanel scrollPanelNorth = new EPanel(new BorderLayout()); //<--allows for NorthJustify in viewport
    private FormPanel curPanel = null; //e1.0_ClosePane
    private TablePanel tablePane = null;
    private GridBagLayout gl = new GridBagLayout();
    private GridBagLayout curLay = null;
    private GridBagConstraints gbca = new GridBagConstraints();
    private GridBagConstraints panelConstraints = null;

    private boolean editable = true;
    private EditController ec = null;

    //private EntityList eList = null;

    private int lastFound = -1;
    private boolean found = false; //17687b

    private RowSelectableTable controllingTable = null;
    private RowSelectableTable table = null;
    private String curKey = null;
    //acl_20030506	private Verifier verifier = new Verifier();
    private String fileName = null;

    private Component curComponent = null;

    private int iEICount = 0; //classification
    private int iConstantCount = 0; //classification
    private int iErrorCount = 0; //classification

    // CI162 changes for BUI required form modifications, following used to handle layout for fieldsets
    private String lastTagRead="";
    private boolean inFieldSet = false;
    private int rowY=0; // value of y for the current tr, maybe incremented by a td
    private int maxY=0; // max value of y for the current tr, must allow for 2 rows in a one tr

    public boolean hasHiddenAttributes() {   return false; }
    public void showHide(boolean _b) {}
    public void highlight(String[] _s) {}
    public void selectKeys(String[] _s) {}
    public void refreshTable(boolean _b) {}
    public boolean isFiltered() {return false;}
    /**
     * editForm
     * @param _eList
     * @param _fileName
     * @param _ec
     * @author Anthony C. Liberto
     */
    public EditForm(String _fileName, EditController _ec) {
        ec = _ec;
        //eList = _eList;
        fileName = new String(_fileName);
        XML = getXML(fileName); //parses from file
        //ErrorFound = false;
        mainPane.setDoubleBuffered(true);
        mainPane.setLayout(gl);
        scrollPanelNorth.add("North", mainPane); // <-- NorthJustify mainPane
        scrollPanelWest.add("West", scrollPanelNorth); // <-- WestJustify mainPane
        setViewportView(scrollPanelWest);
    }

    /**
     * editForm
     * @param _ec
     * @param _sai
     * @author Anthony C. Liberto
     */
    public EditForm(EditController _ec, SearchActionItem _sai) { //dyna
        //this happens anyway super(); //dyna
        ec = _ec; //dyna
        fileName = new String(_sai.getDynaForm()); //dyna
        XML = getXML(fileName); //dyna
        mainPane.setDoubleBuffered(true); //dyna
        mainPane.setLayout(gl); //dyna
        scrollPanelNorth.add("North", mainPane); //dyna
        scrollPanelWest.add("West", scrollPanelNorth); //dyna
        setViewportView(scrollPanelWest); //dyna
        curKey = "DYNA"; //dyna
        table = _sai.getDynaSearchTable(eaccess().getRemoteDatabaseInterface()); //mw_update_20030825
        table.setLongDescription(true); //dyna
        masterRefresh(); //dyna
    } //dyna

    /**
     * updateModel
     * @param _table
     * @author Anthony C. Liberto
     */
    public void updateModel(RowSelectableTable _table) { //21722
        int indx = controllingTable.getActiveRow(); //21722
        setControllingTable(_table, indx, true); //52494
    } //21722

    /**
     * setControllingTable
     * @param _table
     * @param _i
     * @param _refresh
     * @author Anthony C. Liberto
     */
    public void setControllingTable(RowSelectableTable _table, int _i, boolean _refresh) { //52494
        controllingTable = _table;
        toggleRecord(_table.getRowKey(_i), false);
        _table.setActiveRow(_i);
        if (_refresh) { //52494
            masterRefresh(); //classification
        } //52494
    }

    /**
     * toggleRecord
     * @param _key
     * @param _ean
     * @param _regenerate
     * @author Anthony C. Liberto
     */
    public void toggleRecord(String _key, EANFoundation _ean, boolean _regenerate) {
        if (curKey != null) {
            saveCurrentEdit(curKey);
        }
        curKey = _key;
        if (_ean != null) {
            table = ((EntityItem) _ean).getEntityItemTable();
            table.setLongDescription(true);
            if (_regenerate) {
                regenerateForm();
            }
        }     
    }

    private void toggleRecord(String _key, boolean _regenerate) {
        EANFoundation ean = null;
        if (curKey != null) {
            saveCurrentEdit(curKey);
        } //22794
        curKey = new String(_key);
        ean = controllingTable.getRow(_key);
        if (ean != null) {
            table = ((EntityItem) ean).getEntityItemTable();
            table.setLongDescription(true);
            if (_regenerate) {
                regenerateForm();
            }
        }
    }

    /**
     * saveCurrentEdit
     * @return
     * @author Anthony C. Liberto
     */
    public boolean saveCurrentEdit() { //22794
        return saveCurrentEdit(curKey); //22794
    } //22794

    /**
     * saveCurrentEdit
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    private boolean saveCurrentEdit(String _key) { //22794
        if (curComponent instanceof EditorInterface) { //22263
            EditorInterface ei = (EditorInterface) curComponent; //23012
            if (_key != null){
            	// haslock check was always failing because wrong key was used to check RST
            	_key = ei.getKey();   // curkey is for the entity, the table is for the attributes   
            }
            if (ei.canLeave()) { //23012
                try { //acl_20030506
                    if (!commit(ei)) { //acl_20030506
                        return false; //acl_20030506
                    } //acl_20030506
                } catch (Exception _ex) { //acl_20030506
                    _ex.printStackTrace(); //acl_20030506
                    return false; //acl_20030506
                } //acl_20030506

                if (_key != null && !isLocked(_key)) { //22263
                    grabFocus(); //22263
                } //22263
            } else { //23012
                return false; //23012
            } //23012
        } //23012
        return true; //22794
    } //22794

    private void refreshTable() { //classification
        EANFoundation ean = controllingTable.getRow(curKey); //classification
        if (ean != null) { //classification
            table = ((EntityItem) ean).getEntityItemTable(); //classification
            table.setLongDescription(true); //classification
        } //classification
    } //classification

    /**
     * getEntityItem
     * @return
     * @author Anthony C. Liberto
     */
    private EntityItem getEntityItem() {
        EANFoundation ean = controllingTable.getRow(curKey);
        if (ean != null) {
            return (EntityItem) ean;
        }
        return null;
    }

    //classification	public void generateForm() {
    /**
     * masterRefresh
     * @author Anthony C. Liberto
     */
    private void masterRefresh() { //classification
        resetKeys(); //classification
        dereferenceForm();
        panelRemoveAll();
        buildForm();
        scrollPanelWest.revalidate();
    }

    /**
     * partialRefresh
     * @author Anthony C. Liberto
     */
    public void partialRefresh() { //classification
        resetKeys(); //classification
        refreshTable(); //classification
        mainPane.removeAll(); //classification
        buildForm(); //classification
        scrollPanelWest.revalidate(); //classification
    } //classification

    /**
     * adjustAlignment
     * @param _s
     * @author Anthony C. Liberto
     */
    private void adjustAlignment(String _s) {
        scrollPanelNorth.remove(mainPane);
        scrollPanelWest.remove(scrollPanelNorth);
        if (_s.equalsIgnoreCase("NorthWest")) {
            scrollPanelNorth.add("North", mainPane);
            scrollPanelWest.add("West", scrollPanelNorth);
        } else if (_s.equalsIgnoreCase("NorthEast")) {
            scrollPanelNorth.add("North", mainPane);
            scrollPanelWest.add("East", scrollPanelNorth);
        } else if (_s.equalsIgnoreCase("North")) {
            scrollPanelNorth.add("North", mainPane);
            scrollPanelWest.add("North", scrollPanelNorth);
        } else if (_s.equalsIgnoreCase("NorthCentral")) {
            scrollPanelNorth.add("North", mainPane);
            scrollPanelWest.add("Center", scrollPanelNorth);
        } else if (_s.equalsIgnoreCase("SouthCentral")) {
            scrollPanelNorth.add("South", mainPane);
            scrollPanelWest.add("Center", scrollPanelNorth);
        } else if (_s.equalsIgnoreCase("West")) {
            scrollPanelNorth.add("West", mainPane);
            scrollPanelWest.add("West", scrollPanelNorth);
        } else if (_s.equalsIgnoreCase("East")) {
            scrollPanelNorth.add("East", mainPane);
            scrollPanelWest.add("East", scrollPanelNorth);
        } else if (_s.equalsIgnoreCase("South")) {
            scrollPanelNorth.add("South", mainPane);
            scrollPanelWest.add("South", scrollPanelNorth);
        } else if (_s.equalsIgnoreCase("SouthWest")) {
            scrollPanelNorth.add("South", mainPane);
            scrollPanelWest.add("West", scrollPanelNorth);
        } else if (_s.equalsIgnoreCase("SouthEast")) {
            scrollPanelNorth.add("South", mainPane);
            scrollPanelWest.add("East", scrollPanelNorth);
        } else {
            scrollPanelNorth.add("North", mainPane);
            scrollPanelWest.add("West", scrollPanelNorth);
        }
    }

    /**
     * showLayout
     * @author Anthony C. Liberto
     * /
    public void showLayout() {
        File f = null;
        FileOutputStream fout = null;
        ObjectOutputStream outStream = null;
        try {
            f = new File(System.getProperty("opicm.temp") + fileName);
            if (f.exists()) {
                f.delete();
            }
            fout = new FileOutputStream(f);
            outStream = new ObjectOutputStream(fout);
            outStream.writeChars(XML);
            outStream.flush();
            f.deleteOnExit();
            eaccess().launchURL(f.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException _ioe) {
                _ioe.printStackTrace();
            }
        }
    }*/

    /**
     * getOwner
     * @return
     * @author Anthony C. Liberto
     * /
    public Window getOwner() {
        return owner;
    }

    /**
     * setOwner
     * @param win
     * @author Anthony C. Liberto
     * /
    public void setOwner(Window win) {
        owner = win;
    }*/

    /**
     * printForm
     * @author Anthony C. Liberto
     * /
    public void printForm() {
        print(mainPane);
    }

    /**
     * getRectangle
     * @param c
     * @param rect
     * @return
     * @author Anthony C. Liberto
     * /
    public Rectangle getRectangle(Component c, Rectangle rect) {
        Component parent = c.getParent();
        Rectangle out = null;
        if (parent == null) {
            return rect;
        }
        out = SwingUtilities.convertRectangle(c, rect, parent);
        return getRectangle(parent, out);
    }

    /*
     * the set size call makes everything better in the world.
     */
    /**
     * scrollTo
     * @param _jc
     * @author Anthony C. Liberto
     */
    private void scrollTo(JComponent _jc) {
        Rectangle visRect = _jc.getVisibleRect();
        Rectangle rect = null;
        visRect.setSize(_jc.getWidth(), _jc.getHeight());
        rect = SwingUtilities.convertRectangle(_jc, visRect, this);
        scrollRectToVisible(rect);
        if (_jc instanceof MultiEditor) { //acl_20021023
            if (((MultiEditor) _jc).isEditable()) {
                scrollTo(((MultiEditor) _jc).getHeader());
            } //acl_20021023
        } //acl_20021023
        if (_jc instanceof FlagEditor) { //53117
            ((FlagEditor) _jc).showPopup(); //53117
        } //53117
        repaint();
    }

    /*
     * scrollToLabel(editorInterface _ei)
     * added for 23082
     */

    /**
     * panelRevalidate
     * @author Anthony C. Liberto
     * /
    public void panelRevalidate() {
        mainPane.revalidate();
    }

    /**
     * ReturnStatus
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean returnStatus() {
        return ErrorFound;
    }

    /**
     * setEditable
     * @param b
     * @author Anthony C. Liberto
     * /
    public void setEditable(boolean b) {
        editable = b;
    }

    /**
     * isEditable
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isEditable() {
        return editable;
    }

    /**
     * isLocked
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isLocked() { //22191
        int rr = table.getRowCount(); //22191
        for (int r = 0; r < rr; ++r) { //22191
            if (hasLock(r)) { //22191
                return true;
            } //22191
        } //22191
        return false; //22191
    } //22191

    /**
     * isLocked
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isLocked(String _s) {
        return hasLock(_s, 1);
    }

    /**
     * isReplaceable
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isReplaceable() {
        return editable;
    }

    /**
     * deactivate
     * @return
     * @author Anthony C. Liberto
     */
    public void deactivateAttribute() { //22819
        if (curComponent != null && curComponent instanceof EditorInterface) { //22819
            EditorInterface ei = (EditorInterface) curComponent; //22819
            if (ei.isEditable()) { //51865
                int row = table.getRowIndex(ei.getKey()); //22819
                if (row >= 0) { //22819
                    if (deactivate(row)) { //22819
                        ei.setAttribute((EANAttribute) table.getEANObject(row, 1)); //22819
                        ei.cancel(); //22819
                    } //22819
                } //22819
            } //51865
        } //22819
    } //22819

    public void stopEditing() {}
    public void selectAll() {}
    public void invertSelection() {}
    public String getSelectionKey() {
        return null;
    }
    public void showFilter() {}
    public void showEffectivity() {}
    public void sort(boolean _ascending) {}
    public void fillClear() {}
    public void fillPaste(){}
    public void fillCopy(boolean _row) {}
    
    private boolean deactivate(int _row) {
        try {
            table.put(_row, 1, null);
            return true;
        } catch (EANBusinessRuleException _bre) {
            _bre.printStackTrace();
            showException(_bre, ERROR_MESSAGE, OK); //51260
        }
        return false;
    }

    /**
     * getInformation
     * @return
     * @author Anthony C. Liberto
     */
    public String getInformation() {
        EditorInterface ei = getFocusOwner();
        String s = getString("nia");
        if (ei != null) {      	
            s = ei.getInformation();
        }
       
        return s;
    }

    /**
     * historyInfo
     * @author Anthony C. Liberto
     */
    public void historyInfo() {
        EditorInterface ei = getFocusOwner();
        if (ei != null) {
        	eaccess().setBusy(true); //show hourglass everywhere
        	this.setCursor(eaccess().getCursor());
            eaccess().getChangeHistory(ei.getAttribute());
            eaccess().setBusy(false);//restore cursor
            this.setCursor(eaccess().getCursor());
        }
    }

    /**
     * getFocusOwner
     * @return
     * @author Anthony C. Liberto
     */
    private EditorInterface getFocusOwner() {
         if (curComponent instanceof EditorInterface) {
            return (EditorInterface) curComponent;
        }
        return null;
    }

    /**
     * panelRemoveAll
     * @author Anthony C. Liberto
     */
    private void panelRemoveAll() {
        mainPane.removeAll();
        map.clear();
        vOrder.clear(); //classification
        eaccess().gc();
    }

    /**
     * Methods used to generate the form
     */
    private void processElement(String tagName, Attributes atts) {
        if (tagName.equalsIgnoreCase("TABLE")) {
        	if (inFieldSet){ // fieldset has a table, ignore it
        		return;
        	}
            processTable(atts);
        } else if (tagName.equalsIgnoreCase("PANEL")) {
            processPanel(atts);
        } else if (tagName.equalsIgnoreCase("NLS")) { //e1.0_NLS
            //processNLS(atts); //e1.0_NLS
        } else if (tagName.equalsIgnoreCase("MAINPANEL")) {
            processMainPanel(atts);
        } else if (tagName.equalsIgnoreCase("FONT")) {
            processFont(atts);
        } else if (tagName.equalsIgnoreCase("COMPONENT")) {
            processComponent(atts);
        } else if (tagName.equalsIgnoreCase("B") || tagName.equalsIgnoreCase("STRONG")) {
            processFont("BOLD", "");
            defFont = new Font(fntFace, fntStyle, fntSize);
        } else if (tagName.equalsIgnoreCase("I")) {
            processFont("ITALIC", "");
            defFont = new Font(fntFace, fntStyle, fntSize);
        } else if (tagName.equalsIgnoreCase("TD")) {
        	if (inFieldSet){ //fieldset has a table, ignore it
        		return;
        	}
            processTable("DETAIL");
        } else if (tagName.equalsIgnoreCase("P")) {
            addBreak();
        } else if (tagName.equalsIgnoreCase("BR")) {
        	// handle where uimeta and uidata are in one td cell for accessibility
        	if (lastTagRead.equals("UIMETA")){
        		tableY++;
        		maxY=tableY; // keep track of max y used for this tr
        	}else{
        		addBreak();
        	}
        } else if (tagName.equalsIgnoreCase("TR")) {
        	if (inFieldSet){ // fieldset has a table ignore it
        		return;
        	}
            processTableRow(atts);
            rowY = tableY; // hang onto y when tr is starting, may need to restore during td
        } else if (tagName.equalsIgnoreCase("BODY")) {
            processBody(atts);
        } else if (tagName.equalsIgnoreCase("DIMENSION")) {
            processDimension(atts);
        } else if (tagName.equalsIgnoreCase("UIDATA")) {        	
            processUIData(atts);           
        } else if (tagName.equalsIgnoreCase("UIMETA")) {
            processUIMeta(atts);
        } else if (tagName.equalsIgnoreCase("UIITEMDATA")) {
            processUIItemData(atts);
        } else if (tagName.equalsIgnoreCase("CLASSIFY")) { //classification
            processClassification(atts); //classification
        } else if (tagName.equalsIgnoreCase("HTML")) {
        } else if (tagName.equalsIgnoreCase("FIELDSET")) {  // accessibility and JAWS
        	inFieldSet = true;
        } else if (tagName.equalsIgnoreCase("LEGEND")){
            //ignore it - needed for BUI CI162
        }else {
            appendLog("processElement: Unknown Element: " + tagName);
        }
    }

    private void buildConstraints(GridBagConstraints gbcz) {
        if (isTable) {
            gbcz.gridx = tableX;
            gbcz.gridy = tableY;
        } else {
            gbcz.gridx = x;
            gbcz.gridy = y;
        }
        gbcz.gridwidth = 1;
        gbcz.gridheight = 1;
        gbcz.weightx = 0;
        gbcz.weighty = 0;
    }

    private void addBreak() {
        ++y;
        x = 0;
    }

    private void processClassification(Attributes _atts) { //classification
        String eType = null; //classification
        String sName = null; //classification
        int ii = -1;
        if (_atts == null) { //classification
            bClassified = true; //classification
            return; //classification
        } //classification
        ii = _atts.getLength(); //classification
        for (int i = 0; i < ii; ++i) { //classification
            if (_atts.getType(i).equals("CDATA")) { //classification
                if (_atts.getLocalName(i).equalsIgnoreCase("ENTITYTYPE")) { //classification
                    eType = _atts.getValue(i); //classification
                } else if (_atts.getLocalName(i).equalsIgnoreCase("NAME")) { //classification
                    sName = _atts.getValue(i); //classification
                } //classification
            } //classification
        } //classification
        if (eType != null && sName != null) { //classification
            EntityItem ei = getEntityItem(); //classification
            if (ei != null) { //classification
                bClassified = ei.testClassification(eType, sName); //classification
            } else { //classification
                bClassified = true; //classification
            } //classification
        } else { //classification
            bClassified = true; //classification
        } //classification
    } //classification

    private void processDimension(Attributes atts) {
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                processDimension(atts.getLocalName(i), atts.getValue(i));
            }
        }
    }

    private void processDimension(String att, String val) {
        if (att.equalsIgnoreCase("size")) {
            Dimension d = getDimension(val);
            if (d != null) {
                //ltd = d;
            }
        }
    }

    private void processBody(Attributes atts) {
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                processBody(atts.getLocalName(i), atts.getValue(i));
            }
        }
    }

    /**
     * Body properties...
     *
     *	TEXT		--	Adjusts the Text Color
     *	BGCOLOR		--	Adjusts the background color
     *	FACE		--	Adjusts the font
     *	SIZE		--	Adjusts the font size
     */
    private void processBody(String att, String val) {
        if (att.equalsIgnoreCase("TEXT")) {
            Color c = getColor(val.trim());
            fntColor = c;
            defColor = c;
        } else if (att.equalsIgnoreCase("BGCOLOR")) {
            Color c = getColor(val.trim());
            if (curPanel != null) {
                curPanel.setBackground(c);
            }
            bckColor = c;
            defBckColor = c;
        } else if (att.equalsIgnoreCase("FACE")) {
            defFace = val;
            fntFace = val;
        } else if (att.equalsIgnoreCase("SIZE")) {
            defSize = getInt(val);
            fntSize = getInt(val);
        } else if (att.equalsIgnoreCase("LINK")) {
            return;

        } else if (att.equalsIgnoreCase("VLINK")) {
            return;

        } else if (att.equalsIgnoreCase("ALINK")) {
            return;

        } else {
            appendLog("unknown Body Tag: " + att);
        }
    }
    /* deprecated
    	private void getNLSID(PDHGroup gp) {															//e1.0_NLS
    		NLSItem nls = gp.getNLSItem();																//e1.0_NLS
    		NLS = nls.getNLSID();																		//e1.0_NLS
    		return;																						//e1.0_NLS
    	}																								//e1.0_NLS
    */

    /*private void processNLS(Attributes atts) { //e1.0_NLS
        for (int i = 0; i < atts.getLength(); ++i) { //e1.0_NLS
            processNLS(atts.getLocalName(i), atts.getValue(i));
        } //e1.0_NLS
    } //e1.0_NLS
    */

    /*
    private void processNLS(String type, String value) { //e1.0_NLS
        if (type.equalsIgnoreCase("MEMBERS")) { //e1.0_NLS
            int[] availableNLS = generateAvailableNLS(value.trim()); //e1.0_NLS
            setMemberNLS(availableNLS, NLS); //e1.0_NLS
        } //e1.0_NLS
    } //e1.0_NLS
    */
/*
    private int[] generateAvailableNLS(String str) { //e1.0_NLS
        StringTokenizer st = new StringTokenizer(str, ","); //e1.0_NLS
        Vector v = new Vector(); //e1.0_NLS
        int ii = -1;
        int[] out = null;
        while (st.hasMoreTokens()) { //e1.0_NLS
            String s = st.nextToken(); //e1.0_NLS
            if (Routines.have(s)) { //e1.0_NLS
                v.add(s); //e1.0_NLS
            } //e1.0_NLS
        } //e1.0_NLS
        ii = v.size(); //e1.0_NLS
        out = new int[ii]; //e1.0_NLS
        for (int i = 0; i < ii; ++i) { //e1.0_NLS
            out[i] = getInt((String) v.get(i));
        } //e1.0_NLS
        return out; //e1.0_NLS
    } //e1.0_NLS
    */

    /**
     * isMemberNLS
     *
     * @author Anthony C. Liberto
     * @return
     * /
    protected boolean isMemberNLS() { //e1.0_NLS
        return memberNLS; //e1.0_NLS
    } //e1.0_NLS
    

    private void setMemberNLS(int[] availableNLS, int nls) { //e1.0_NLS
        int ii = -1;
        if (availableNLS == null) { //e1.0_NLS
            memberNLS = true; //e1.0_NLS
            return; //e1.0_NLS
        } //e1.0_NLS
        ii = availableNLS.length; //e1.0_NLS
        if (ii == 0) { //e1.0_NLS
            memberNLS = true; //e1.0_NLS
            return; //e1.0_NLS
        } //e1.0_NLS
        for (int i = 0; i < ii; ++i) { //e1.0_NLS
            if (availableNLS[i] == nls) { //e1.0_NLS
                memberNLS = true; //e1.0_NLS
                return; //e1.0_NLS
            } //e1.0_NLS
        } //e1.0_NLS
        memberNLS = false; //e1.0_NLS
    } //e1.0_NLS
    */

    /**
     * Table properties
     *
     *	BORDER_SUNK		--	are the table cells sunk
     */
    private void processTableRow(Attributes atts) {
        for (int i = 0; i < atts.getLength(); ++i) {
            processTableRow(atts.getLocalName(i), atts.getValue(i));
        }
    }
    private void processTableRow(String type, String val) {
        if (type.toUpperCase().equals("BGCOLOR")) {
            tablePane.setBackgroundColor(getColor(val));
        }
    }

    private void processTable(Attributes atts) {
        panelConstraints.fill = GridBagConstraints.BOTH;
        tablePane = new TablePanel();
        tablePane.setHoldX(x);
        tablePane.setHoldY(y);
        isTable = true;
        for (int i = 0; i < atts.getLength(); ++i) {
            processTable(atts.getLocalName(i), atts.getValue(i));
        }
        curLay.setConstraints(tablePane, tablePane.getConstraints());
        curPanel.add(tablePane);
    }

    private void processTable(String type, String val) {
        if (type.toUpperCase().equals("BORDERS_SUNK")) {
            if (val.equals("true")) {
                //bTableBorder = true;
            }
        } else if (type.toUpperCase().equals("FILL")) {
            if (val.equalsIgnoreCase("HORIZONTAL")) {
                tablePane.setFill(GridBagConstraints.HORIZONTAL);

            } else if (val.equalsIgnoreCase("VERTICAL")) {
                tablePane.setFill(GridBagConstraints.VERTICAL);

            } else if (val.equalsIgnoreCase("BOTH")) {
                tablePane.setFill(GridBagConstraints.BOTH);

            } else if (val.equalsIgnoreCase("NONE")) {
                tablePane.setFill(GridBagConstraints.NONE);
            }
        } else if (type.equalsIgnoreCase("ANCHOR")) {
            if (val.equalsIgnoreCase("WEST")) {
                tablePane.setAnchor(GridBagConstraints.WEST);

            } else if (val.equalsIgnoreCase("EAST")) {
                tablePane.setAnchor(GridBagConstraints.EAST);

            } else if (val.equalsIgnoreCase("CENTER")) {
                tablePane.setAnchor(GridBagConstraints.CENTER);

            } else if (val.equalsIgnoreCase("NONE")) {
                tablePane.setAnchor(GridBagConstraints.NONE);

            } else if (val.equalsIgnoreCase("NORTH")) {
                tablePane.setAnchor(GridBagConstraints.NORTH);

            } else if (val.equalsIgnoreCase("NORTHEAST")) {
                tablePane.setAnchor(GridBagConstraints.NORTHEAST);

            } else if (val.equalsIgnoreCase("NORTHWEST")) {
                tablePane.setAnchor(GridBagConstraints.NORTHWEST);

            } else if (val.equalsIgnoreCase("SOUTH")) {
                tablePane.setAnchor(GridBagConstraints.SOUTH);

            } else if (val.equalsIgnoreCase("SOUTHEAST")) {
                tablePane.setAnchor(GridBagConstraints.SOUTHEAST);

            } else if (val.equalsIgnoreCase("SOUTHWEST")) {
                tablePane.setAnchor(GridBagConstraints.SOUTHWEST);
            }
        } else if (type.toUpperCase().equals("CELLFILL")) {
            if (val.equalsIgnoreCase("HORIZONTAL")) {
                panelConstraints.fill = GridBagConstraints.HORIZONTAL;

            } else if (val.equalsIgnoreCase("VERTICAL")) {
                panelConstraints.fill = GridBagConstraints.VERTICAL;

            } else if (val.equalsIgnoreCase("BOTH")) {
                panelConstraints.fill = GridBagConstraints.BOTH;

            } else if (val.equalsIgnoreCase("NONE")) {
                panelConstraints.fill = GridBagConstraints.NONE;
            }
        } else if (type.equalsIgnoreCase("CELLANCHOR")) {
            if (val.equalsIgnoreCase("WEST")) {
                panelConstraints.anchor = GridBagConstraints.WEST;

            } else if (val.equalsIgnoreCase("EAST")) {
                panelConstraints.anchor = GridBagConstraints.EAST;

            } else if (val.equalsIgnoreCase("CENTER")) {
                panelConstraints.anchor = GridBagConstraints.CENTER;

            } else if (val.equalsIgnoreCase("NONE")) {
                panelConstraints.anchor = GridBagConstraints.NONE;

            } else if (val.equalsIgnoreCase("NORTH")) {
                panelConstraints.anchor = GridBagConstraints.NORTH;

            } else if (val.equalsIgnoreCase("NORTHEAST")) {
                panelConstraints.anchor = GridBagConstraints.NORTHEAST;

            } else if (val.equalsIgnoreCase("NORTHWEST")) {
                panelConstraints.anchor = GridBagConstraints.NORTHWEST;

            } else if (val.equalsIgnoreCase("SOUTH")) {
                panelConstraints.anchor = GridBagConstraints.SOUTH;

            } else if (val.equalsIgnoreCase("SOUTHEAST")) {
                panelConstraints.anchor = GridBagConstraints.SOUTHEAST;

            } else if (val.equalsIgnoreCase("SOUTHWEST")) {
                panelConstraints.anchor = GridBagConstraints.SOUTHWEST;
            }
        } else if (type.equalsIgnoreCase("X")) {
            tablePane.setX(getInt(val));
        } else if (type.equalsIgnoreCase("Y")) {
            tablePane.setY(getInt(val) + 1);
        } else if (type.equalsIgnoreCase("WEIGHTX")) {
            tablePane.setWeightX(getInt(val));
        } else if (type.equalsIgnoreCase("WEIGHTY")) {
            tablePane.setWeightY(getInt(val));
        } else if (type.equalsIgnoreCase("GRIDWIDTH")) {
            tablePane.setGridWidth(getInt(val));
        } else if (type.equalsIgnoreCase("GRIDHEIGHT")) {
            tablePane.setGridHeight(getInt(val));
        } else if (type.equalsIgnoreCase("border")) {
           // ignore it
        } else if (type.equalsIgnoreCase("width")) {
           // ignore it
        } else {
            appendLog("unknown table tag: " + type + ", " + val);
        }
    }

    private void processMainPanel(Attributes atts) {
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                gbca = processMainPanel(gbca, atts.getLocalName(i).toUpperCase(), atts.getValue(i));
            }
        }
    }

    private void processEndMainPanel() {
    }

    private void processPanel(Attributes atts) {
        curPanel = new FormPanel(this); //e1.0_ClosePane
        curPanel.setBackground(defBckColor); //e1.0_ClosePane
        curLay = curPanel.getGridBagLayout(); //e1.0_ClosePane
        panelConstraints = curPanel.getGridBagConstraints(); //e1.0_ClosePane
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                panelConstraints = processPanel(panelConstraints, atts.getLocalName(i).toUpperCase(), atts.getValue(i));
            }
        }
        curPanel.setNLS(NLS);
    }

    private Insets getInsets(String str) {
        StringTokenizer st = new StringTokenizer(str, ",");
        int[] i = new int[4];
        int tmpX = 0;
        while (st.hasMoreTokens() && tmpX < 4) {
            String s = st.nextToken();
            if (Routines.have(s)) {
                try {
                    i[tmpX] = getInt(s);
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
            }
            ++tmpX;
        }
        if (tmpX != 4) {
            appendLog("invalid insets declaration: " + str);
            return null;
        }
        return new Insets(i[0], i[1], i[2], i[3]);
    }

    private GridBagConstraints processMainPanel(GridBagConstraints gbc, String type, String val) {
        int i = -1;
        if (bValidate) {
            appendLog("processMainPanel(GridBagConstraints," + type + "," + val + ")");
        }
        if (type.equalsIgnoreCase("BGCOLOR")) {
            Color c = getColor(val.trim());
            mainPane.setBackground(c);
            scrollPanelWest.setBackground(c);
            scrollPanelNorth.setBackground(c);
            setBackground(c);
            bckColor = c;
            defBckColor = c;
            return gbc;
        } else if (type.equalsIgnoreCase("OPAQUE")) {
            if (val.equalsIgnoreCase("true")) {
                mainPane.setOpaque(true);
                scrollPanelWest.setOpaque(true);
                scrollPanelNorth.setOpaque(true);
            } else {
                mainPane.setOpaque(false);
                scrollPanelWest.setOpaque(false);
                scrollPanelNorth.setOpaque(false);
            }
            return gbc;
        } else if (type.equalsIgnoreCase("ALIGNMENT")) {
            adjustAlignment(val);
            return gbc;
        } else if (type.equalsIgnoreCase("INSETS")) { //e1.0_ClosePane
            gbc.insets = getInsets(val); //e1.0_ClosePane
            return gbc; //e1.0_ClosePane
        } else if (type.equalsIgnoreCase("FILL")) { //e1.0_ClosePane
            if (val.equalsIgnoreCase("HORIZONTAL")) { //e1.0_ClosePane
                gbc.fill = GridBagConstraints.HORIZONTAL; //e1.0_ClosePane

            } else if (val.equalsIgnoreCase("VERTICAL")) { //e1.0_ClosePane
                gbc.fill = GridBagConstraints.VERTICAL; //e1.0_ClosePane

            } else if (val.equalsIgnoreCase("BOTH")) { //e1.0_ClosePane
                gbc.fill = GridBagConstraints.BOTH; //e1.0_ClosePane

            } else if (val.equalsIgnoreCase("NONE")) { //e1.0_ClosePane
                gbc.fill = GridBagConstraints.NONE;
            } //e1.0_ClosePane
            return gbc; //e1.0_ClosePane
        } else if (type.equalsIgnoreCase("ANCHOR")) { //e1.0_ClosePane
            if (val.equalsIgnoreCase("WEST")) { //e1.0_ClosePane
                gbc.anchor = GridBagConstraints.WEST; //e1.0_ClosePane
            } else if (val.equalsIgnoreCase("EAST")) { //e1.0_ClosePane
                gbc.anchor = GridBagConstraints.EAST; //e1.0_ClosePane
            } else if (val.equalsIgnoreCase("CENTER")) { //e1.0_ClosePane
                gbc.anchor = GridBagConstraints.CENTER; //e1.0_ClosePane
            } else if (val.equalsIgnoreCase("NONE")) { //e1.0_ClosePane
                gbc.anchor = GridBagConstraints.NONE; //e1.0_ClosePane
            } else if (val.equalsIgnoreCase("NORTH")) { //e1.0_ClosePane
                gbc.anchor = GridBagConstraints.NORTH; //e1.0_ClosePane
            } else if (val.equalsIgnoreCase("NORTHEAST")) { //e1.0_ClosePane
                gbc.anchor = GridBagConstraints.NORTHEAST; //e1.0_ClosePane
            } else if (val.equalsIgnoreCase("NORTHWEST")) { //e1.0_ClosePane
                gbc.anchor = GridBagConstraints.NORTHWEST; //e1.0_ClosePane
            } else if (val.equalsIgnoreCase("SOUTH")) { //e1.0_ClosePane
                gbc.anchor = GridBagConstraints.SOUTH; //e1.0_ClosePane
            } else if (val.equalsIgnoreCase("SOUTHEAST")) { //e1.0_ClosePane
                gbc.anchor = GridBagConstraints.SOUTHEAST; //e1.0_ClosePane
            } else if (val.equalsIgnoreCase("SOUTHWEST")) { //e1.0_ClosePane
                gbc.anchor = GridBagConstraints.SOUTHWEST; //e1.0_ClosePane
            } //e1.0_ClosePane
            return gbc; //e1.0_ClosePane
        } else if (type.equalsIgnoreCase("NAME")) { //e1.0_ClosePane
            mainPane.setName(val); //e1.0_ClosePane
            return gbc; //e1.0_ClosePane
        } //e1.0_ClosePane
        i = getInt(val); //e1.0_ClosePane
        if (type.equalsIgnoreCase("X")) { //e1.0_ClosePane
            gbc.gridx = i; //e1.0_ClosePane

        } else if (type.equalsIgnoreCase("Y")) { //e1.0_ClosePane
            gbc.gridy = i; //e1.0_ClosePane

        } else if (type.equalsIgnoreCase("WIDTH")) { //e1.0_ClosePane
            gbc.gridwidth = i; //e1.0_ClosePane

        } else if (type.equalsIgnoreCase("HEIGHT")) { //e1.0_ClosePane
            gbc.gridheight = i; //e1.0_ClosePane

        } else if (type.equalsIgnoreCase("WEIGHTX")) { //e1.0_ClosePane
            gbc.weightx = i; //e1.0_ClosePane

        } else if (type.equalsIgnoreCase("WEIGHTY")) { //e1.0_ClosePane
            gbc.weighty = i; //e1.0_ClosePane

        } else { //e1.0_ClosePane
            appendLog("processMainPanel() -- unKnown parameter " + type + ", " + val);
        } //e1.0_ClosePane
        return gbc; //e1.0_ClosePane
    }

    /**
     * Panel properties...
     *
     *	INSETS	--	Adjust the panel insets
     *	FILL	--	Adjust the Fill for the constraints
     *	ANCHOR	--	Adjust the Anchor for the constraints
     *	NAME	--	Set the Name of the Panel
     *	GRIDX	--	set the x position of the constraints
     *	GRIDY	--	set the y position of the constraints
     *	WIDTH	--	set the width of the constraints
     *	HEIGHT	--	set the height of the constraints
     *	WEIGHTX	--	set the weightx of the constraints
     *	WEIGHTY	--	set the weighty of the constraints
     *	HIDE	--	Adjust visibility based on passed value
     */
    private GridBagConstraints processPanel(GridBagConstraints gbc, String type, String val) {
        int i = -1;
        if (bValidate) {
            appendLog("processPanel(GridBagConstraints," + type + "," + val + ")");
        }
        if (type.equalsIgnoreCase("INSETS")) {
            gbc.insets = getInsets(val);
            return gbc;
        } else if (type.equalsIgnoreCase("FILL")) {
            if (val.equalsIgnoreCase("HORIZONTAL")) {
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbca.fill = GridBagConstraints.HORIZONTAL;
            } else if (val.equalsIgnoreCase("VERTICAL")) {
                gbc.fill = GridBagConstraints.VERTICAL;
                gbca.fill = GridBagConstraints.VERTICAL;
            } else if (val.equalsIgnoreCase("BOTH")) {
                gbc.fill = GridBagConstraints.BOTH;
                gbca.fill = GridBagConstraints.BOTH;
            } else if (val.equalsIgnoreCase("NONE")) {
                gbc.fill = GridBagConstraints.NONE;
                gbca.fill = GridBagConstraints.NONE;
            }
            return gbc;
        } else if (type.equalsIgnoreCase("ANCHOR")) {
            if (val.equalsIgnoreCase("WEST")) {
                gbc.anchor = GridBagConstraints.WEST;
                gbca.anchor = GridBagConstraints.WEST;
            } else if (val.equalsIgnoreCase("EAST")) {
                gbc.anchor = GridBagConstraints.EAST;
                gbca.anchor = GridBagConstraints.EAST;
            } else if (val.equalsIgnoreCase("CENTER")) {
                gbc.anchor = GridBagConstraints.CENTER;
                gbca.anchor = GridBagConstraints.CENTER;
            } else if (val.equalsIgnoreCase("NONE")) {
                gbc.anchor = GridBagConstraints.NONE;
                gbca.anchor = GridBagConstraints.NONE;
            } else if (val.equalsIgnoreCase("NORTH")) {
                gbc.anchor = GridBagConstraints.NORTH;
                gbca.anchor = GridBagConstraints.NORTH;
            } else if (val.equalsIgnoreCase("NORTHEAST")) {
                gbc.anchor = GridBagConstraints.NORTHEAST;
                gbca.anchor = GridBagConstraints.NORTHEAST;
            } else if (val.equalsIgnoreCase("NORTHWEST")) {
                gbc.anchor = GridBagConstraints.NORTHWEST;
                gbca.anchor = GridBagConstraints.NORTHWEST;
            } else if (val.equalsIgnoreCase("SOUTH")) {
                gbc.anchor = GridBagConstraints.SOUTH;
                gbca.anchor = GridBagConstraints.SOUTH;
            } else if (val.equalsIgnoreCase("SOUTHEAST")) {
                gbc.anchor = GridBagConstraints.SOUTHEAST;
                gbca.anchor = GridBagConstraints.SOUTHEAST;
            } else if (val.equalsIgnoreCase("SOUTHWEST")) {
                gbc.anchor = GridBagConstraints.SOUTHWEST;
                gbca.anchor = GridBagConstraints.SOUTHWEST;
            }
            return gbc;
        } else if (type.equalsIgnoreCase("NAME")) {
            curPanel.setName(val);
            return gbc;
        } else if (type.equalsIgnoreCase("BGCOLOR")) {
            curPanel.setBackground(getColor(val.trim()));
            return gbc;
        } else if (type.equalsIgnoreCase("OPAQUE")) {
            if (val.equalsIgnoreCase("true")) {
                curPanel.setOpaque(true);

            } else {
                curPanel.setOpaque(false);
            }
            return gbc;
            //e1.0_NLSTitle		} else if (type.equalsIgnoreCase("TITLE")) {															//e1.0_ClosePane
        } else if (type.toUpperCase().startsWith("TITLE")) { //e1.0_NLSTitle
            int titleNLS = -1;
            if (type.length() == 5) {
                curPanel.setTitle(val.trim(), 1);
                return gbc;
            }
            titleNLS = getInt(type.substring(5)); //e1.0_NLSTitle
            curPanel.setTitle(val.trim(), titleNLS); //e1.0_ClosePane
            return gbc; //e1.0_ClosePane
        } else if (type.equalsIgnoreCase("TITLECOLOR")) { //e1.0_ClosePane
            curPanel.setTitleColor(getColor(val.trim())); //e1.0_ClosePane
            return gbc; //e1.0_ClosePane
        } else if (type.equalsIgnoreCase("COLLAPSIBLE")) { //e1.0_ClosePane
            String s = val.trim(); //e1.0_ClosePane
            if (s.equalsIgnoreCase("false")) { //e1.0_ClosePane
                curPanel.setCollapsible(false); //e1.0_ClosePane

            } else { //e1.0_ClosePane
                curPanel.setCollapsible(true);
            } //e1.0_ClosePane
            return gbc; //e1.0_ClosePane
        } else if (type.equalsIgnoreCase("DEFAULTCOLLAPSED")) { //e1.0_ClosePane
            String s = val.trim(); //e1.0_ClosePane
            if (s.equalsIgnoreCase("true")) { //e1.0_ClosePane
                curPanel.setCollapsed(true); //e1.0_ClosePane

            } else { //e1.0_ClosePane
                curPanel.setCollapsed(false);
            } //e1.0_ClosePane
            return gbc; //e1.0_ClosePane
        }

        i = getInt(val);
        if (type.equalsIgnoreCase("X")) {
            gbca.gridx = i;
        } else if (type.equalsIgnoreCase("Y")) {
            gbca.gridy = i + 1; //e1.0_ClosePane
        } else if (type.equalsIgnoreCase("WIDTH")) {
            gbca.gridwidth = i;
        } else if (type.equalsIgnoreCase("HEIGHT")) {
            gbca.gridheight = i;
        } else if (type.equalsIgnoreCase("WEIGHTX")) {
            gbca.weightx = i;
        } else if (type.equalsIgnoreCase("WEIGHTY")) {
            gbca.weighty = i;
        } else {
            appendLog("processPanel() -- unKnown parameter: " + type + ", " + val);
        }
        return gbc;
    }

    private void processEndPanel() {
        if (bValidate) {
            appendLog("processEndPanel()");
        }
        gl.setConstraints(curPanel, gbca);
        mainPane.add(curPanel);
        curPanel = null;
        curLay = null;
        x = 0;
        y = 1; //e1.0_ClosePane
    }

    private void processTable(String _s) {
    }

    private void processFont(Attributes atts) {
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                processFont(atts.getLocalName(i).toUpperCase(), atts.getValue(i));
            }
        }
    }

    /**
     * Font properties...
     *
     *	BOLD	--	makes font bold
     *	ITALIC	--	makes font italic
     *	FACE	--	adjusts font face
     *	COLOR	--	adjusts font color
     *	SIZE	--	adjusts font size
     */
    private void processFont(String type, String s) {
        if (type.equalsIgnoreCase("BOLD")) {
            fntStyle += Font.BOLD;
        } else if (type.equalsIgnoreCase("ITALIC")) {
            fntStyle += Font.ITALIC;
        } else if (type.equalsIgnoreCase("FACE")) {
            setFontFace(s);
        } else if (type.equalsIgnoreCase("COLOR")) {
            setFontColor(getColor(s.trim()));
        } else if (type.equalsIgnoreCase("SIZE")) {
            setFontSize(s);
        } else {
            appendLog("processFont() -- unknown attribute: " + type + ", " + s);
        }
    }

    private void processComponent(Attributes atts) {
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                processComponent(atts.getLocalName(i).toUpperCase(), atts.getValue(i));
            }
        }
    }
   
    /**
     * component properties...
     *
     *	OPAQUE		--	setOpaque(boolean) for the component
     *	BGCOLOR		--	setBackground(COLOR) for the component
     *	MINSIZE		--	setMinimumSize(width,height) for Component
     *	MAXSIZE		--	setMaximumSize(width,height) for Component
     *	PREFSIZE	--	setPreferredSize(width,height) for Component
     *	SIZE		--	setSize(width,height) for Component
     *	TOOLTIP		--	setToolTipText(String) for Component
     *	HORZALGN	--	setHorizontalAlignment(int) for Component
     *	VERTALGN	--	setVerticalAlignment(int) for Component
     */
    private void processComponent(String type, String s) {
        if (type.equalsIgnoreCase("OPAQUE")) {
            setOpaque(s);
        } else if (type.equalsIgnoreCase("BGCOLOR")) {
            setBackgroundColor(getColor(s.trim()));
        } else if (type.equalsIgnoreCase("MINSIZE")) {
            //cmpMinSize = getDimension(s);
        } else if (type.equalsIgnoreCase("MAXSIZE")) {
            //cmpMaxSize = getDimension(s);
        } else if (type.equalsIgnoreCase("PREFSIZE")) {
            //cmpPrefSize = getDimension(s);
        } else if (type.equalsIgnoreCase("SIZE")) {
            //cmpSize = getDimension(s);
        } else if (type.equalsIgnoreCase("TOOLTIP")) {
            cmpToolTip = new String(s);
        } else if (type.equalsIgnoreCase("HORZALGN")) {
            //cmpHorzAlgn = getIntValue(s);
        } else if (type.equalsIgnoreCase("VERTALGN")) {
            //cmpVertAlgn = getIntValue(s);
        } else {
            appendLog("processComponent() -- unknown attribute" + type + ", " + s);
        }
    }

    /**
     * convert constants to integers...
     *
     *	LEFT		--	converts to SwingConstant
     *	CENTER		--	converts to SwingConstant
     *	RIGHT		--	converts to SwingConstant
     *	LEADING		--	converts to SwingConstant
     *	TRAILING	--	converts to SwingConstant
     * /
    private int getIntValue(String s) {
        if (s.equalsIgnoreCase("LEFT")) {
            return SwingConstants.LEFT;
        } else if (s.equalsIgnoreCase("CENTER")) {
            return SwingConstants.CENTER;
        } else if (s.equalsIgnoreCase("RIGHT")) {
            return SwingConstants.RIGHT;
        } else if (s.equalsIgnoreCase("LEADING")) {
            return SwingConstants.LEADING;
        } else if (s.equalsIgnoreCase("TRAILING")) {
            return SwingConstants.TRAILING;
        } else {
            appendLog("getIntValue() -- unknown constant: " + s);
        }
        return 0;
    }*/

    private int getInt(String s) {
        return Routines.getInt(s);
    }

    /**
     * creates a Dimension from an array of
     * int in the following format...
     *
     * "width,height"
     */
    private Dimension getDimension(String str) {
        StringTokenizer st = new StringTokenizer(str, ",");
        int[] i = new int[2];
        int tmpX = 0;
        while (st.hasMoreTokens() && tmpX < 2) {
            String s = st.nextToken();
            if (Routines.have(s)) {
                try {
                    i[tmpX] = getInt(s);
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
            }
            ++tmpX;
        }
        if (tmpX != 2) {
            appendLog("getDimension() -- improperly formatted dimension: " + str);
        }
        return new Dimension(i[0], i[1]);
    }

    private void setFontColor(Color _c) {
        fntColor = _c;
    }

    private void setBackgroundColor(Color _c) {
        bckColor = _c;
    }

    /**
     * getColor
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public Color getColor(String _s) { //23202
        if (_s.equalsIgnoreCase("black")) { //23202
            return Color.black; //23202
        } else if (_s.equalsIgnoreCase("blue")) { //23202
            return Color.blue; //23202
        } else if (_s.equalsIgnoreCase("cyan")) { //23202
            return Color.cyan; //23202
        } else if (_s.equalsIgnoreCase("darkgray")) { //23202
            return Color.darkGray; //23202
        } else if (_s.equalsIgnoreCase("darkgrey")) { //23202
            return Color.darkGray; //23202
        } else if (_s.equalsIgnoreCase("gray")) { //23202
            return Color.gray; //23202
        } else if (_s.equalsIgnoreCase("green")) { //23202
            return Color.green; //23202
        } else if (_s.equalsIgnoreCase("lightgray")) { //23202
            return Color.lightGray; //23202
        } else if (_s.equalsIgnoreCase("lightgrey")) { //23202
            return Color.lightGray; //23202
        } else if (_s.equalsIgnoreCase("magenta")) { //23202
            return Color.magenta; //23202
        } else if (_s.equalsIgnoreCase("orange")) { //23202
            return Color.orange; //23202
        } else if (_s.equalsIgnoreCase("pink")) { //23202
            return Color.pink; //23202
        } else if (_s.equalsIgnoreCase("red")) { //23202
            return Color.red; //23202
        } else if (_s.equalsIgnoreCase("white")) { //23202
            return Color.white; //23202
        } else if (_s.equalsIgnoreCase("yellow")) { //23202
            return Color.yellow; //23202
        } else { //23202
            try { //23202
                return Color.decode(_s); //23202
            } catch (NumberFormatException _nfe) { //23202
                _nfe.printStackTrace(); //23202
            } //23202
        } //23202
        return Color.black; //23202
    } //23202

    private void setOpaque(String s) {
        if (s.equalsIgnoreCase("true")) {
            setOpaque(true);

        } else {
            setOpaque(false);
        }
    }

    /**
     * @see javax.swing.JComponent#setOpaque(boolean)
     * @author Anthony C. Liberto
     */
    public void setOpaque(boolean b) {
        cmpOpaque = b;
    }

    private void setFontSize(String s) {
        if (s.startsWith("+")) {
            fntSize += getInt(s.substring(1));

        } else if (s.startsWith("-")) {
            fntSize -= getInt(s.substring(1));

        } else {
            setFontSize(getInt(s));
        }
    }

    private void setFontSize(int i) {
        fntSize = i;
    }

    private void setFontFace(String s) {
        fntFace = s;
    }

    private void processEndFont(String type) {
        if (type.equalsIgnoreCase("BOLD")) {
            fntStyle -= Font.BOLD;

        } else if (type.equalsIgnoreCase("ITALIC")) {
            fntStyle -= Font.ITALIC;

        } else if (type.equalsIgnoreCase("FONT")) {
            setFontFace(defFace);
            setFontColor(defColor);
            setFontSize(defSize);
        }
        defFont = new Font(fntFace, fntStyle, fntSize);
    }

    private void processEndComponent(String type) {
        if (type.equalsIgnoreCase("COMPONENT")) {
            setBackgroundColor(defBckColor);
            setOpaque(defOpaque);
            //cmpMinSize = null;
            //cmpMaxSize = null;
            //cmpPrefSize = null;
            //cmpSize = null;
            cmpToolTip = null;
            //cmpHorzAlgn = -1;
            //cmpVertAlgn = -1;

        }
    }

    private void processEndTable() {
        panelConstraints.fill = GridBagConstraints.NONE;
        panelConstraints.anchor = GridBagConstraints.WEST;
        isTable = false;
        tableX = 0;
        tableY = 1;
        x = tablePane.getAdjustedX();
        y = tablePane.getAdjustedY();
        //bTableBorder = false;
    }

    private void processEndTable(String s) {
        if (s.equalsIgnoreCase("DETAIL")) {
            ++tableX;
        } else if (s.equalsIgnoreCase("ROW")) {
            tableX = 0;
            // fieldset may have used 2 'rows' for one tr, account for it here
            if (maxY>tableY){
            	tableY=maxY;
            }
            ++tableY;
            tablePane.setBackgroundColor(null);
        }
    }

    private void processUIItemData(Attributes _atts) {
        EntityItem ei = (EntityItem) controllingTable.getRow(curKey);
        if (ei == null) {
            return;
        }
        for (int i = 0; i < _atts.getLength(); ++i) {
            String localName = _atts.getLocalName(i);
            if (localName.equalsIgnoreCase("PROPERTY")) {
                String code = _atts.getValue(i);
                if (code.equalsIgnoreCase("DESCRIPTION")) {
                    generateEntityItemLabel(ei, FormLabel.ENTITY_ITEM_DESCRIPTION);
                } else if (code.equalsIgnoreCase("ENTITYTYPE")) {
                    generateEntityItemLabel(ei, FormLabel.ENTITY_ITEM_TYPE);
                } else if (code.equalsIgnoreCase("ENTITYID")) {
                    generateEntityItemLabel(ei, FormLabel.ENTITY_ITEM_ID);
                }
            }
        }
    }

    private void processUIData(Attributes _atts) {
        String attCode = null;
        boolean bEditable = false;
        String eType = null;
        String eContext = null; //dwb_20041117
        String sKey = null;
        for (int i = 0; i < _atts.getLength(); ++i) {
            String localName = _atts.getLocalName(i);
            if (localName.equalsIgnoreCase("AttributeCode")) {
                attCode = _atts.getValue(i);
            } else if (localName.equalsIgnoreCase("Editable")) {
                if (_atts.getValue(i).equalsIgnoreCase("true")) {
                    bEditable = true;

                } else {
                    bEditable = false;
                }
            } else if (localName.equalsIgnoreCase("EntityType")) {
                eType = _atts.getValue(i);
            } else if (localName.equalsIgnoreCase("EntityContext")) { //dwb_20041117
                eContext = _atts.getValue(i).toUpperCase(); //dwb_20041117
            } //dwb_20041117
        }

        if (attCode == null && eType == null) {
            generateErrorLabel("UIDATA missing attributeCode and entityType.", getErrorKey());
            return;
        } else if (attCode == null) {
            generateErrorLabel("UIDATA missing attributeCode.", getErrorKey());
            return;
        } else if (eType == null) {
            generateErrorLabel("UIDATA missing entityType.", getErrorKey());
            return;
        }

        sKey = generateKey(eType, attCode, eContext, true); //dwb_20041117       
        if (!map.containsKey(sKey)) { //classification
            vOrder.add(sKey); //classification
        }

        if (bClassified) { //classification
            if (bEditable) {
                generateField(eType, attCode, eContext); //dwb_20041117
            } else {
                generateLabel(eType, attCode, eContext); //dwb_20041117
            }
        } //classification
    }

    private void processUIMeta(Attributes _atts) {
        String attCode = null;
        String eType = null;
        String eContext = null; //dwb_20041117
        int desc = FormLabel.LONG_DESCRIPTION;
        String sKey = null;
        for (int i = 0; i < _atts.getLength(); ++i) {
            String localName = _atts.getLocalName(i);
            if (localName.equalsIgnoreCase("AttributeCode")) {
                attCode = _atts.getValue(i);
            } else if (localName.equalsIgnoreCase("EntityType")) {
                eType = _atts.getValue(i);
            } else if (localName.equalsIgnoreCase("EntityContext")) { //dwb_20041117
                eContext = _atts.getValue(i).toUpperCase(); //dwb_20041117
            } else if (localName.equalsIgnoreCase("PROPERTY")) {
                String code = _atts.getValue(i);
                if (code.equalsIgnoreCase("CAPABILITY")) {
                    desc = FormLabel.CAPABILITY_DESCRIPTION;
                } else if (code.equalsIgnoreCase("CODE")) {
                    desc = FormLabel.CODE_DESCRIPTION;
                } else if (code.equalsIgnoreCase("DEFAULT")) {
                    desc = FormLabel.DEFAULT_DESCRIPTION;
                } else if (code.equalsIgnoreCase("HELP")) {
                    desc = FormLabel.HELP_DESCRIPTION;
                } else if (code.equalsIgnoreCase("SHORT")) {
                    desc = FormLabel.SHORT_DESCRIPTION;
                } else if (code.equalsIgnoreCase("TYPE")) {
                    desc = FormLabel.TYPE_DESCRIPTION;
                }
            }
        }

        if (attCode == null && eType == null) {
            generateErrorLabel("UIMETADATA tag missing attributeCode and entityType.", getErrorKey());
            return;
        } else if (attCode == null) {
            generateErrorLabel("UIMETADATA missing attributeCode.", getErrorKey());
            return;
        } else if (eType == null) {
            generateErrorLabel("UIMETADATA missing entityType.", getErrorKey());
            return;
        }

        //dwb_20041117		String sKey = generateKey(eType,attCode,true);				//classification
        sKey = generateKey(eType, attCode, eContext, true); //dwb_20041117
        if (!map.containsKey(sKey)) { //classification
            vOrder.add(sKey);
        } //classification

        if (bClassified) { //classification
            //dwb_20041117			generateMetaField(eType, attCode, desc);
            generateMetaField(eType, attCode, eContext, desc); //dwb_20041117
        } //classification
    }

    private void generateErrorLabel(String _text, String _key) {
    	//if (!map.containsKey(_key)) { // impossible logic and error msgs are not getting displayed
    	if (map.containsKey(_key)) {
    		Object o = map.get(_key); //classification
            if (o != null && o instanceof FormLabel) { //classification
                addToForm((FormLabel) o, false);
                ((FormLabel)o).setOpaque(false);// dont want background to show thru error labels
            } //classification
        } else {
        	FormLabel formlbl = new FormLabel("***   " + _text + "   ***", _key, FormLabel.ERROR_LABEL);
            addToForm(formlbl, true);
            formlbl.setOpaque(false);// dont want background to show thru error labels
            appendLog("Error in XML: "+XML); 
        }
    	appendLog("Error in XML tags: " + _text + ", " + _key);    	
    }

    private void generateConstantLabel(String _text) {
        String sKey = getConstantKey();
        if (!map.containsKey(sKey)) { //classification
            FormLabel label = new FormLabel(_text, sKey, FormLabel.CONSTANT_LABEL); //classification
            vOrder.add(sKey); //classification
            addToForm(label, true); //classification
        } else { //classification
            Object o = map.get(sKey); //classification
            if (o != null && o instanceof FormLabel) { //classification
                addToForm((FormLabel) o, false);
            } //classification
        } //classification
        //classification		addToForm(new formLabel(_text, formLabel.CONSTANT_LABEL));
    }

    private void generateEntityItemLabel(EntityItem _ei, int _desc) {
        String sKey = getEntityItemKey(); //classification
        if (!map.containsKey(sKey)) { //classification
            FormLabel label = new FormLabel(_ei, sKey, _desc); //classification
            vOrder.add(sKey); //classification
            addToForm(label, true); //classification
        } else { //classification
            Object o = map.get(sKey); //classification
            if (o != null && o instanceof FormLabel) { //classification
                addToForm((FormLabel) o, false);
            } //classification
        } //classification
        //classification		addToForm(new formLabel(_ei,_desc));
    }

    private String getEntityItemKey() { //classification
        ++iEICount; //classification
        return "EntityItem:" + iEICount; //classification
    } //classification

    //kctola	private String getMetaKey() {									//classification
    //kctola		++iMetaCount;												//classification
    //kctola		return "Meta:" + iMetaCount;								//classification
    private String getMetaKey(String _s) { //kctola
        return "meta:" + _s; //kctola
    } //classification

    private String getConstantKey() { //classification
        ++iConstantCount; //classification
        return "Constant:" + iConstantCount; //classification
    } //classification

    private String getErrorKey() { //classification
        ++iErrorCount; //classification
        return "Error:" + iErrorCount; //classification
    } //classification

    private void resetKeys() { //classification
        iEICount = 0; //classification
        //iMetaCount = 0; //classification
        iConstantCount = 0; //classification
        iErrorCount = 0; //classification
    } //classification

    private void generateMetaLabel(EANMetaAttribute _meta, String _key, int _desc) {
        //kctola		String sKey = getMetaKey();									//classification
        String sKey = getMetaKey(_key); //kctola
        FormLabel label = null;
        if (!map.containsKey(sKey)) { //classification
            vOrder.add(sKey); //classification
            label = new FormLabel(_meta, sKey, _desc); //classification
            addToForm(label, true); //classification
        } else { //classification
            Object o = map.get(sKey); //classification
            if (o != null && o instanceof FormLabel) { //classification
                addToForm((FormLabel) o, false);
            } //classification
        } //classification
        //classification		addToForm(new formLabel(_meta, _desc));
    }

    //dwb_20041117	private void generateMetaField(String _entityType, String _attributeCode, int _desc) {
    //dwb_20041117		String key = generateKey(_entityType, _attributeCode, false);
    private void generateMetaField(String _entityType, String _attributeCode, String _entityContext, int _desc) { //dwb_20041117
        String key = generateKey(_entityType, _attributeCode, _entityContext, false); //dwb_20041117
        int row = table.getRowIndex(key);
        Object o = null;
        if (row < 0) {
            generateErrorLabel("UIMETADATA missing key: " + key, key);
            return;
        }
        o = table.getEANObject(row, 0);
        if (o != null) {
            generateMetaLabel((EANMetaAttribute) o, key, _desc);
        } else {
            generateErrorLabel("UIMETADATA missing attribute entityType: " + _entityType + " attributeCode: " + _attributeCode, key);
        }
    }

    //dwb_20041117	private void generateField(String _entityType, String _attributeCode) {
    //dwb_20041117		String key = generateKey(_entityType, _attributeCode, false);
    private void generateField(String _entityType, String _attributeCode, String _entityContext) { //dwb_20041117
        String key = generateKey(_entityType, _attributeCode, _entityContext, false); //dwb_20041117
        int row = -1;
        EANAttribute o = null;
        if (bValidate) {
            appendLog("generateField(" + _entityType + ", " + _attributeCode + ")");
        }

        row = table.getRowIndex(key);
 
        if (row < 0) {
            generateErrorLabel("UIDATA missing key: " + key, key);
            return;
        }
        o = (EANAttribute)table.getEANObject(row, 1);
        if (o != null) {
            addToForm( o, key);
        } else {
            generateErrorLabel("UIDATA missing attribute entityType: " + _entityType + " attributeCode: " + _attributeCode, key);
        }
    }

    //dwb_20041117	private void generateLabel(String _entityType, String _attributeCode) {
    //dwb_20041117		String key = generateKey(_entityType, _attributeCode, false);
    private void generateLabel(String _entityType, String _attributeCode, String _entityContext) { //dwb_20041117
        String key = generateKey(_entityType, _attributeCode, _entityContext, false); //dwb_20041117
        int row = table.getRowIndex(key);
        Object o = null;
        if (row < 0) {
            generateErrorLabel("UIDATA missing key: " + key, key);
            return;
        }

        o = table.getEANObject(row, 1);

        if (o != null && o instanceof EANAttribute) {
            EANAttribute ean = (EANAttribute) o;
            if (o instanceof MultiFlagAttribute || o instanceof LongTextAttribute || o instanceof XMLAttribute) {
                if (map.containsKey(key)) { //classification
                    Object obj = map.get(key); //classification
                    if (obj != null && obj instanceof FormLabel) { //classification
                        addToForm((FormLabel) obj, false);
                    } //classification
                } else { //classification
                    FormLabel lbl = new FormLabel(ean, key, FormLabel.DISPLAY_ONLY_LABEL);
                    addToForm(new EIScroll(lbl, true), true);
                } //classification
            } else {
                if (map.containsKey(key)) { //classification
                    Object obj = map.get(key); //classification
                    if (obj != null && obj instanceof FormLabel) { //classification
                        addToForm((FormLabel) obj, false);
                    } //classification
                } else { //classification
                    addToForm(new FormLabel(ean, key, FormLabel.DISPLAY_ONLY_LABEL), true);
                } //classification
            }
        }
    }

    private JComponent addToForm(EANAttribute _att, String _key) {
        if (bValidate) {
            appendLog("addToForm(" + _att.getKey() + ", " + _key + ") value:"+_att);
        }
        if (map.containsKey(_key)) { //classification
            Object o = map.get(_key); //classification
            if (o instanceof JComponent) { //classification
                JComponent jc = (JComponent) o; //classification
                if (o instanceof EditorInterface){
                	((EditorInterface)o).setAttribute(_att); // chgs in diff editor were not reflected
                	validate(((EditorInterface)o)); // lock state was not transferred between editors
                }
                addToForm(jc, false); //classification
                return jc; //classification
            } //classification
            return null; //classification
        } //classification

        if (_att instanceof SingleFlagAttribute) {
            FlagEditor fe = new FlagEditor(_att, _key, this); //acl_20030506
            addToForm(fe, true);
            return fe;
        } else if (_att instanceof StatusAttribute) {
            FlagEditor fe = new FlagEditor(_att, _key, this); //acl_20030506
            addToForm(fe, true);
            return fe;
        } else if (_att instanceof TaskAttribute) { //22509
            FlagEditor fe = new FlagEditor(_att, _key, this); //acl_20030506
            addToForm(fe, true); //22509
            return fe; //22509
        } else if (_att instanceof MultiFlagAttribute) {
            MultiEditor me = new MultiEditor(_att, _key, this); //acl_20030506
            addToForm(me, true);
            return me;
        } else if (_att instanceof LongTextAttribute) {
            LongEditor le = new LongEditor(_att, _key, this); //acl_20030506
            EIScroll ei = new EIScroll(le, true);
            ei.setScrollMode(JViewport.BLIT_SCROLL_MODE); //52490
            addToForm(ei, true);
            return le;
        } else if (_att instanceof XMLAttribute) {
            XMLEditor xml = new XMLEditor(_att, _key, this); //acl_20030506
            EIScroll ei = new EIScroll(xml, true);
            ei.setScrollMode(JViewport.BLIT_SCROLL_MODE); //52490
            addToForm(ei, true);
            return xml;
        } else if (_att instanceof BlobAttribute) {
            BlobEditor be = new BlobEditor(_att, _key, this); //acl_20030506
            addToForm(be, true);
            return be;
        } else if (_att instanceof TextAttribute) {
            TextAttribute txt = (TextAttribute) _att;
            EANMetaAttribute meta = txt.getMetaAttribute();
            if (meta.isDate()) {
                DateTimeEditor de = new DateTimeEditor(txt, _key, this, DateTimeEditor.DATE_VALIDATOR); //acl_20030506
                EIPanel pnl = new EIPanel(de, true); //kc_20031012
                pnl.add("East", de.getButton()); //kc_20031012
                pnl.pack(); //kc_20031012
                addToForm(pnl, true); //kc_20031012
                return pnl; //kc_20031012
            } else if (meta.isTime()) {
                DateTimeEditor dte = new DateTimeEditor(txt, _key, this, DateTimeEditor.TIME_VALIDATOR); //acl_20030506
                addToForm(dte, true);
                return dte;
            } else {
                TextEditor te = new TextEditor(txt, _key, this); //acl_20030506
                addToForm(te, true);           
                return te;
            }
        }
        return null;
    }

    /**
     * addToForm
     *
     * @author Anthony C. Liberto
     * @param _meta
     * @param _add
     * @return
     * /
    protected JComponent addToForm(EANMetaAttribute _meta, boolean _add) {
        ELabel lbl = new ELabel(_meta.toString());
        addToForm(lbl, _add);
        return lbl;
    }*/

    //classification	private JComponent addToForm(JComponent _jc) {
    private JComponent addToForm(JComponent _jc, boolean _add) { //classification
        JComponent jc = validateComponent(_jc);
        if (_add) { //classification
            if (_jc instanceof EditorInterface) {
                EditorInterface ei = (EditorInterface) _jc;
                ei.addKeyListener(this);
                ei.addFocusListener(this);
                ei.addMouseListener(this); //21898
                map.put(ei.getKey(), validate(ei)); //classification
            } else if (_jc instanceof FormLabel) {
                FormLabel fl = (FormLabel) _jc;
                map.put(fl.getKey(), fl); //classification
            }
        }

        buildConstraints(panelConstraints);

        if (isTable) {
            Color rowColor = tablePane.getBackgroundColor();
            tablePane.setConstraints(jc, panelConstraints);
            if (rowColor != null) {
                jc.setBackground(rowColor);
                jc.setOpaque(true);
            }
            tablePane.add(jc);
        } else {
            curLay.setConstraints(jc, panelConstraints);
            curPanel.add(jc);
            ++x;
        }
        return jc;
    }

    private EditorInterface validate(EditorInterface _ei) {
        _ei.setDisplayOnly(!isLocked(_ei.getKey()));
        return _ei;
    }

    private JComponent validateComponent(JComponent _jc) {
        _jc.setForeground(fntColor);
        _jc.setBackground(bckColor);
        _jc.setFont(defFont);
        _jc.setOpaque(cmpOpaque);
        if (cmpToolTip != null) {
            _jc.setToolTipText(cmpToolTip);
        }
        return _jc;
    }

    /**
     * regenerateForm
     * @author Anthony C. Liberto
     */
    public void regenerateForm() {
        //22823		int ii = mainPane.getComponentCount();
        //22823		for (int i=0;i<ii;++i) {
        for (int i = 0; i < mainPane.getComponentCount(); ++i) { //22823
            Component c = mainPane.getComponent(i);
            if (c instanceof FormPanel) {
                regenerateForm((FormPanel) c);
            } else if (c instanceof TablePanel) {
                regenerateForm((TablePanel) c);
            }
        }
        revalidate();
    }

    private void regenerateForm(FormPanel _fp) {
        for (int i = 0; i < _fp.getComponentCount(); ++i) { //22823
            Component c = _fp.getComponent(i);
            if (c instanceof FormLabel) {
                FormLabel fl = (FormLabel) c;
                refresh(fl);
                //classification				validate(fl);
            } else if (c instanceof EditorInterface) {
                EditorInterface ei = (EditorInterface) c;
                refresh(ei);
                validate(ei);
            } else if (c instanceof TablePanel) {
                regenerateForm((TablePanel) c);
            }
        }
        _fp.revalidate();
    }

    private void regenerateForm(TablePanel _tp) {
        for (int i = 0; i < _tp.getComponentCount(); ++i) { //22823
            Component c = _tp.getComponent(i);
            if (c instanceof FormLabel) {
                FormLabel fl = (FormLabel) c;
                refresh(fl);
                //classification				validate(fl);
            } else if (c instanceof EditorInterface) {
                EditorInterface ei = (EditorInterface) c;
                refresh(ei);
                validate(ei);
            }
        }
        _tp.revalidate();
    }

    private void refresh(FormLabel _lbl) {
        if (_lbl.isType(FormLabel.META_LABEL)) {
            String key = _lbl.getKey();
            Object o = null;
            if (key != null) {
                int row = table.getRowIndex(key);
                if (row < 0) {
                    return;
                }
                o = table.getEANObject(row, 0);
                if (o instanceof EANMetaAttribute) {
                    _lbl.refresh((EANMetaAttribute) o);
                }
            }
        } else if (_lbl.isType(FormLabel.ENTITY_ITEM_LABEL)) {
            EntityItem ei = (EntityItem) controllingTable.getRow(curKey);
            if (ei == null) {
                return;
            }
            _lbl.refresh(ei);
        }
    }

    private void refresh(EditorInterface _ei) {
        String key = _ei.getKey();
        Object o = null;
        if (key != null) {
            //acl_20030506			_ei.setTable(table);
            int row = table.getRowIndex(key);
            if (row < 0) {
                return;
            }
            o = table.getEANObject(row, 1);
            if (o instanceof EANAttribute) {
                _ei.refreshDisplay((EANAttribute) o);
            }
        }
    }

    /**
     * isAutoRefresh
     * @param _att
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isAutoRefresh(EANAttribute _att) { //21923
        if (_att != null) { //21923
            EANMetaAttribute meta = _att.getMetaAttribute(); //21923
            if (meta != null) { //21923

                if (meta.isClassified()) { //dwb_20021022
                    partialRefresh(); //dwb_20021022
                } //dwb_20021022

                return meta.isRefreshEnabled(); //21923
            } //21923
        } //21923
        return false; //21923
    } //21923

    /**
     * refreshAll
     * @author Anthony C. Liberto
     */
    public void refreshAll() { //21923
        Collection c = map.values();
        Object[] o = c.toArray();
        if (o != null) {
            int ii = o.length;
            for (int i = 0; i < ii; ++i) {
                if (o[i] != null) {
                    if (o[i] instanceof EditorInterface) {
                        EditorInterface ei = (EditorInterface) o[i];
                        String key = ei.getKey();
                        Object ean = null;
                        if (key != null) {
                            //acl_20030506							ei.setTable(table);
                            int row = table.getRowIndex(key);
                            if (row < 0) {
                                continue;
                            }
                            ean = table.getEANObject(row, 1);
                            if (ean instanceof EANAttribute) {
                                EANAttribute att = (EANAttribute) ean;
                                ei.refreshDisplay(att);
                                ei.setDisplayOnly(!isLocked(key));
                            }
                        }
                    }
                }
            }
        }
        revalidate();
    } //21923

    /**
     * getAttribute
     *
     * @author Anthony C. Liberto
     * @param _key
     * @return
     * /
    protected EANAttribute getAttribute(String _key) { //21923
        int i = table.getRowIndex(_key); //21923
        if (i >= 0) { //21923
            return (EANAttribute) table.getEANObject(i, 1);
        } //21923
        return null; //21923
    } //21923

    /**
     * getXML
     *
     * @author Anthony C. Liberto
     * @param _byte
     * @param _encoding
     * @return
     * /
    protected String getXML(byte[] _byte, String _encoding) { //next
        XML = eaccess().gio().getString(_byte, _encoding); //next
        return XML; //next
    } //next
    */

    private String getXML(String _fileName) { //next
        appendLog("retrieving XML(" + _fileName + ")"); 
        XML = eaccess().getHTML(_fileName);     
        return XML; //next
    } //next

    /**
     * end of move
     */
    private String getAttsString(Attributes _atts) {
        StringBuffer sb = new StringBuffer("Attributes: ");
        for (int i = 0; i < _atts.getLength(); ++i) {
            sb.append(" " + _atts.getLocalName(i) + "=" + _atts.getValue(i) + ",");
        }
        return sb.toString();
    }

    private void buildForm() {
        DefaultHandler defaulthandler = new DefaultHandler() {

            public void characters(char[] _chars, int _start, int _finish) {
                String str = new String(_chars, _start, _finish).trim();
                if (bValidate) {
                    appendLog("characters: " + str);
                }
                if (Routines.have(str)) {
                    generateConstantLabel(str);
                }
            }
            public void startDocument() {
            }
            public void endDocument() {
            }

            public void startElement(String URI, String tagName, String qName, Attributes atts) {
                if (bValidate) {
                    appendLog("startElement( " + URI + ", " + tagName + ", " + qName + ", atts) tableX:"+tableX+" tableY:"+tableY);
                }
                processElement(tagName, atts);
                lastTagRead = tagName;
                if (bValidate) {
                    appendLog(getAttsString(atts));
                }
            }

            public void endElement(String URI, String tagName, String qName) {
                if (bValidate) {
                    appendLog("endElement( " + URI + ", " + tagName + ", " + qName + ", atts) tableX:"+tableX+" tableY:"+tableY);
                }
                if (tagName.equalsIgnoreCase("TABLE")) {
                	if (inFieldSet){ // fieldset has a table, ignore it
                		return;
                	}
                    processEndTable();
                } else if (tagName.equalsIgnoreCase("NLS")) { //e1.0_NLS
                	//setMemberNLS(null, NLS); //e1.0_NLS
                } else if (tagName.equalsIgnoreCase("PANEL")) {
                    processEndPanel();
                } else if (tagName.equalsIgnoreCase("MAINPANEL")) {
                    processEndMainPanel();
                } else if (tagName.equalsIgnoreCase("FONT")) {
                    processEndFont(tagName);
                } else if (tagName.equalsIgnoreCase("COMPONENT")) {
                    processEndComponent(tagName);
                } else if (tagName.equalsIgnoreCase("B") || tagName.equalsIgnoreCase("STRONG")) {
                    processEndFont("BOLD");
                } else if (tagName.equalsIgnoreCase("I")) {
                    processEndFont("ITALIC");
                } else if (tagName.equalsIgnoreCase("TD")) {
                	if (inFieldSet){ //fieldset has a table, ignore it
                		return;
                	}
                    processEndTable("DETAIL");
                    tableY=rowY; // restore any changes done in fieldset for the td so next td is on same line
                } else if (tagName.equalsIgnoreCase("TR")) {
                	if (inFieldSet){ // fieldset has a table, ignore it
                		return;
                	}
                    processEndTable("ROW");
     
                } else if (tagName.equalsIgnoreCase("CLASSIFY")) { //classification
                	bClassified = true; //classification
                }else if (tagName.equalsIgnoreCase("LEGEND")) { //accessibility and JAWS reader
                    tableY++;
                    maxY=tableY;
                }else if (tagName.equalsIgnoreCase("FIELDSET")) { //accessibility and JAWS reader
                    inFieldSet=false;
                }
            }

            public void error(SAXParseException saxparseexception) throws SAXException {
                if (bValidate) {
                    appendLog("editform exception( " + saxparseexception.toString() + ")");
                }

                throw saxparseexception;
            }

        };
        //classification		evaluator.clearHash();
        //classification		groupMap.clear();													//e1.0_Group
        SAXParser parser = new SAXParser();
        parser.setContentHandler(defaulthandler);

        try {
            //			parser.parse(XML);												//parses from file    	
            parser.parse(new InputSource(new StringReader(XML))); //parses from String
        } catch (Exception x) {
            x.printStackTrace();
            setCode("msg11016.0");
            setParm(fileName);
            showFYI();
            //ErrorFound = true;
        } finally {
            eaccess().gc();
        }
    }

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        x = 0;
        y = 0;
        tableX = 0;
        tableY = 0;
        //curRec = 0;
        NLS = 0;
        //ltd = null;
        dereferenceForm();
        //acl_20030506		verifier.dereference();
        controllingTable = null;
        //eList = null;
        if (map != null) { //classification
            map.clear(); //classification
            map = null; //classification
        } //classification

        if (vOrder != null) { //classification
            vOrder.clear(); //classification
            vOrder = null; //classification
        } //classification

        XML = null;

        fntColor = null;
        fntSize = 0;
        fntStyle = 0;
        fntFace = null;

        defColor = null;
        defSize = 0;
//        defStyle = 0;
        defFace = null;

        bckColor = null;
        //cmpMinSize = null;
        //cmpMaxSize = null;
        //cmpPrefSize = null;
        //cmpSize = null;
        cmpToolTip = null;
        //cmpHorzAlgn = 0;
        //cmpVertAlgn = 0;
        defBckColor = null;
        curComponent = null;
       
        controllingTable = null;
        table = null;
        curKey = null;
        fileName = null;
        if (mainPane != null) {
            mainPane.removeAll();
            mainPane.removeNotify();
            mainPane = null;
        }

        if (scrollPanelWest != null) {
            scrollPanelWest.removeAll();
            scrollPanelWest.removeNotify();
            scrollPanelWest = null;
        }

        if (scrollPanelNorth != null) {
            scrollPanelNorth.removeAll();
            scrollPanelNorth.removeNotify();
            scrollPanelNorth = null;
        }

        if (curPanel != null) {
            curPanel.removeAll();
            curPanel.removeNotify();
            curPanel.dereference();  // i added
            curPanel = null;
        }
        if (tablePane != null) {
            tablePane.removeAll();
            tablePane.removeNotify();
            tablePane.dereference(); // i added
            tablePane = null;
        }

        gl = null;
        curLay = null;
        gbca = null;
        panelConstraints = null;

        ec = null;
//        owner = null;
        lastFound = 0;
        //tableBord = null;
        //requiredTableBord = null;
        //requiredBorder = null;
        defFont = null; //leak
        super.dereference();
    }

    /**
     * dereferenceForm
     * @author Anthony C. Liberto
     */
    private void dereferenceForm() {
    	if (mainPane != null) {
    		int ii = mainPane.getComponentCount();
    		for (int i = 0; i < ii; ++i) {
    			Component c = mainPane.getComponent(i);        
    			if (c instanceof FormPanel) {
    				dereference((FormPanel) c);
    			} else if (c instanceof TablePanel) {
    				dereference((TablePanel) c);
    			}
    		}
    	}
    }

    private void dereference(FormPanel _fp) {
        int ii = _fp.getComponentCount();
        for (int i = 0; i < ii; ++i) {
            Component c = _fp.getComponent(i);          
            if (c instanceof FormLabel) {
                FormLabel fl = (FormLabel) c;
                fl.removeAll();
            } else if (c instanceof EditorInterface) {
                EditorInterface ei = (EditorInterface) c;
                ei.dereference();
            } else if (c instanceof TablePanel) {
                dereference((TablePanel) c);
            }
        }
    }

    private void dereference(TablePanel _tp) {
        int ii = _tp.getComponentCount();
        for (int i = 0; i < ii; ++i) {
            Component c = _tp.getComponent(i);
            if (c instanceof FormLabel) {
                FormLabel fl = (FormLabel) c;
                fl.removeAll();
            } else if (c instanceof EditorInterface) {
                EditorInterface ei = (EditorInterface) c;
                ei.removeKeyListener(this);
                ei.removeFocusListener(this);
                ei.removeMouseListener(this); //21898
                ei.dereference();
            }
        }
    }

    /**
     * rollback
     * @author Anthony C. Liberto
     * /
    public void rollback() {
        controllingTable.rollback();
        regenerateForm();
    }

    /**
     * rollbackRow
     * @author Anthony C. Liberto
     */
    public void rollbackRow() {
        table.rollback();
        regenerateForm();
    }

    /**
     * rollbackSingle
     * @author Anthony C. Liberto
     */
    public void rollbackSingle() {
        EditorInterface ei = getFocusOwner();
        if (ei != null) {
            String key = ei.getKey();
            int row = table.getRowIndex(key);
            table.rollback(row);
            regenerateForm();
        }
    }

    /**
     * commit
     * @return
     * @author Anthony C. Liberto
     */
    public boolean commit() {
        Exception e = null;
        saveCurrentEdit(curKey); //22794

        e = dBase().commit(table, this);
        if (e != null) {
            if (e instanceof MiddlewareBusinessRuleException) {
                //				moveToError((MiddlewareBusinessRuleException)e);
            } else if (e instanceof EANBusinessRuleException) {
                moveToError((EANBusinessRuleException) e);
            }
            showException(e, ERROR_MESSAGE, OK); //51260
        } else {
            return true;
        }
        return false;
    }

    /**
     * commitDefault
     * @return
     * @author Anthony C. Liberto
     */
    public boolean commitDefault() {
        EntityItem ei = getEntityItem();
        if (ei != null) {
            try {
                ei.updateWGDefault(null, getRemoteDatabaseInterface());
            } catch (MiddlewareBusinessRuleException _mbre) {
                _mbre.printStackTrace();
            } catch (EANBusinessRuleException _bre) {
                _bre.printStackTrace();
            }
        }
        return true;
    }

    /**
     * cancelDefault
     * @author Anthony C. Liberto
     */
    public void cancelDefault() {
        EntityItem ei = getEntityItem();
        if (ei != null) {
            try {
                ei.resetWGDefault(null, getRemoteDatabaseInterface());
            } catch (MiddlewareBusinessRuleException _mbre) {
                _mbre.printStackTrace();
            } catch (EANBusinessRuleException _bre) {
                _bre.printStackTrace();
            }
        }
    }

    /**
     * hasChanges
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasChanges() {
        return table.hasChanges();
    }

    /**
     * addRow
     * @author Anthony C. Liberto
     * /
    public void addRow() {
        String key = null;
        controllingTable.addRow();
        key = controllingTable.getRowKey(controllingTable.getRowCount() - 1);
        toggleRecord(key, true);
    }

    /**
     * duplicate
     * @param _copies
     * @author Anthony C. Liberto
     */
    public void duplicate(int _copies) {
        duplicate(curKey, _copies);
        regenerateForm(); //50967
    }

    /**
     * duplicate
     * @param _recKey
     * @param _copies
     * @author Anthony C. Liberto
     */
    private void duplicate(String _recKey, int _copies) {
        EANFoundation ean = controllingTable.getRow(_recKey);
        String[] newKeys = new String[_copies]; //22089
        int cc = -1;
        OPICMBlobValue blobOut = null;
        if (ean != null) {
            for (int i = 0; i < _copies; ++i) {
                //acl_20030911				ec.addRow();																			//22089
                ec.addRow(false); //acl_20030911
                newKeys[i] = new String(curKey); //22089
            }
            cc = controllingTable.getColumnCount(); //22089
            for (int c = 0; c < cc; ++c) { //22089
                Object o = controllingTable.getEANObject(controllingTable.getRowIndex(_recKey), c); //22089
                for (int i = 0; i < _copies; ++i) { //22089
                    int row = controllingTable.getRowIndex(newKeys[i]); //22809
                    if (o instanceof EANAttribute) { //22468
                        if (isCopyable((EANAttribute) o)) { //22468
                            try { //22089
                                if (o instanceof EANTextAttribute) { //22089
                                    controllingTable.put(row, c, o.toString()); //22089
                                } else if (o instanceof EANFlagAttribute) { //22089
                                    MetaFlag[] flag = (MetaFlag[]) ((EANFlagAttribute) o).get(); //22089
                                    controllingTable.put(row, c, flag); //22089
                                } else if (o instanceof EANBlobAttribute) { //22089
                                    EANBlobAttribute blobAtt = (EANBlobAttribute) o; //22089
                                    //51834									byte[] bArray = 															//22809
                                    //51834										blobAtt.getBlobValue(getRemoteDatabaseInterface(),null);	//22809

                                    byte[] bArray = null; //51834
                                    bArray = blobAtt.getBlobValue(); //51834
                                    if (bArray == null) { //51834
                                        //Nothing local check database											//51834
                                        bArray = eaccess().dBase().getBlobValue(blobAtt); //51834
                                    } //51834
                                    blobOut = new OPICMBlobValue(getActiveNLSItem().getNLSID(), blobAtt.getBlobExtension(), bArray); //22089
                                    if (blobOut.size() > 0) { //50550
                                        controllingTable.put(row, c, blobOut); //22089
                                    } //50550
                                } else { //22089
                                    controllingTable.put(row, c, o); //22089
                                } //22089
                            } catch (EANBusinessRuleException _bre) { //22089
                                _bre.printStackTrace(); //22089
                            } //22089
                        } //22468
                    } //22468
                } //22089
            } //22089
        } //22089
    }

    /**
     * isCopyable
     * @param _att
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isCopyable(EANAttribute _att) { //22468
        if (_att != null) { //22468
            EANMetaAttribute meta = _att.getMetaAttribute(); //22468
            if (meta != null) { //22468
                return !meta.isExcludeFromCopy(); //22468
            } //22468
        } //22468
        return false; //22468
    }

    /**
     * getAttributeObject
     * @param _table
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     * /
    public Object getAttributeObject(RowSelectableTable _table, int _r, int _c) {
        Object o = _table.getEANObject(_r, _c);
        if (o instanceof SingleFlagAttribute) {
            return ((SingleFlagAttribute) o).get();
        } else if (o instanceof StatusAttribute) {
            return ((StatusAttribute) o).get();
        } else if (o instanceof TaskAttribute) { //22509
            return ((TaskAttribute) o).get(); //22509
        } else if (o instanceof MultiFlagAttribute) {
            return ((MultiFlagAttribute) o).get();
        } else if (o instanceof LongTextAttribute) {
            return ((LongTextAttribute) o).get();
        } else if (o instanceof XMLAttribute) {
            return ((XMLAttribute) o).get();
        } else if (o instanceof BlobAttribute) {
            return ((BlobAttribute) o).get();
        } else if (o instanceof TextAttribute) {
            return ((TextAttribute) o).get();
        }
        return null;
    }

    /**
     * panelCollapsed
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void panelCollapsed(boolean _b) {
        eaccess().repaintImmediately();
    }

    /*
     * listeners
     */
    /**
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     * @author Anthony C. Liberto
     */
    public void propertyChange(PropertyChangeEvent pce) { //e1.0_RefreshHidden
    } //e1.0_RefreshHidden

    /**
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyPressed(KeyEvent _ke) {
        int key = _ke.getKeyCode();
        if (key == KeyEvent.VK_ESCAPE) {
            Object src = _ke.getSource();
            if (src instanceof EditorInterface) {
                ((EditorInterface) src).cancel();
            }
            _ke.consume();
        } else if (key == KeyEvent.VK_DELETE && _ke.getModifiers() == 0) {
            Object src = _ke.getSource();
            if (src instanceof EditorInterface) {
                EditorInterface ei = (EditorInterface) src;
                int row = table.getRowIndex(ei.getKey());
                if (deactivate(row)) {
                    ei.setAttribute((EANAttribute) table.getEANObject(row, 1));
                    ei.cancel();
                }
            }
            _ke.consume();
        }
    }

    /**
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyReleased(KeyEvent _ke) {
    }
    /**
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyTyped(KeyEvent _ke) {
    }

    /**
     * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
     * @author Anthony C. Liberto
     */
    public void focusGained(FocusEvent _fe) {
        setFocusOwner(_fe.getComponent());
        if (curComponent instanceof JComponent) {
            scrollTo((JComponent) curComponent);
        }
    }

    /**
     * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
     * @author Anthony C. Liberto
     */
    public void focusLost(FocusEvent _fe) {
    }

    /**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mousePressed(MouseEvent _me) {
    } //21898
    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseReleased(MouseEvent _me) {
    } //21898
    /**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseEntered(MouseEvent _me) {
    } //21898
    /**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseExited(MouseEvent _me) {
    } //21898
    /**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseClicked(MouseEvent _me) { //21898
        if (_me.getClickCount() == 2 && !isLocked()) {
            lock();
        }
    } //21898

    //	public void processMouseEvent(MouseEvent _me) {	//22191
    //		if (!isLocked()) {							//22191
    //			super.processMouseEvent(_me);			//22191
    //		}											//22191
    //		return;										//22191
    //	}												//22191

    /**
     * setFocusOwner - 
     * @param _c
     */
    private void setFocusOwner(Component _c) {
        if (_c instanceof EditorInterface) { //50526
            curComponent = _c;
        }else if(_c instanceof FlagComboEditor){
        	// if EAccess.isArmed(ENHANCED_FLAG_EDIT) the focus goes to the text control (FlagComboEditor)
        	Component tmp = ((FlagComboEditor)_c).getFlagEditor();
        	if (tmp instanceof EditorInterface){
        		curComponent = tmp;
        	}
        }    
    }

    /*
     * find/replace logic
     */
    private boolean getNextValue(String findValue, boolean caseSensitive, int increment, int indx) {
        //classification		int max = hashVect.size();
        int max = formSize(); //classification
        indx += increment;

        if (indx < 0) { //17687
            indx = max - 1; //17687

        } else if (indx >= max) { //17687
            indx = 0;
        } //17687
        for (int e = indx; e < max && e >= 0; e += increment) {
            if (searchComponent(e, findValue, caseSensitive)) {
                lastFound = e;
                return true;
            }
        }
        setCode("msg3016");
        //		if (showConfirm(OK_CANCEL,true) != 0)		//16320
        //			return false;								//16320
        if (eaccess().showConfirm(eaccess().getPanel(FIND_PANEL), OK_CANCEL, true) != FIRST_BUTTON) { //012807
            return false;
        }

        repaint(); //16320

        if (increment < 0) { //17687b
            for (int b = max - 1; b >= indx; b--) { //17687b
                if (searchComponent(b, findValue, caseSensitive)) { //17687b
                    lastFound = b; //17687b
                    return true; //17687b
                } //17687b
            } //17687b
        } else {
            for (int b = 0; b < indx && b >= 0; b += increment) {
                if (searchComponent(b, findValue, caseSensitive)) {
                    lastFound = b;
                    return true;
                }
            }
        }

        return false;
    }

    private boolean searchComponent(int i, String _strFind, boolean _bCase) {
        //classification		Object o = hashVect.get(i);
        Object o = get(i); //classification
        if (o != null) { //classification
            if (o instanceof EditorInterface) {
                if (((EditorInterface) o).find(_strFind, _bCase)) {
                    scrollTo((JComponent) o);
                    return true;
                } else {
                    return false;
                }
            } else if (o instanceof FormLabel) {
                if (((FormLabel) o).find(_strFind, _bCase)) {
                    scrollTo((JComponent) o);
                    return true;
                } else {
                    return false;
                }
            }
        } //classification
        return false;
    }

    /**
     * resetFound
     * @author Anthony C. Liberto
     */
    public void resetFound() {
        Collection c = map.values(); //23032
        Object[] o = c.toArray(); //23032
        if (o != null) { //23032
            int ii = o.length; //23032
            for (int i = 0; i < ii; ++i) { //23032
                if (o[i] != null) { //23032
                    if (o[i] instanceof EditorInterface) { //23032
                        ((EditorInterface) o[i]).resetFound(); //23032
                    } else if (o[i] instanceof FormLabel) { //23032
                        ((FormLabel) o[i]).resetFound(); //23032
                    } //23032
                } //23032
            } //23032
        } //23032
        found = false; //23032
    }

    /**
     * hasFound
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasFound() {
        return found;
    }

    /**
     * findValue
     * @param _fStr
     * @param _bCase
     * @param _inc
     * @author Anthony C. Liberto
     */
    public void findValue(String _fStr, boolean _bCase, int _inc) {
        Object o = get(lastFound); //classification
        if (o != null && o instanceof EditorInterface) { //classification
            EditorInterface ei = (EditorInterface) o;
            if (ei.isFound()) {
                if (getNextValue(_fStr, _bCase, _inc, lastFound)) {
                    found = true;
                }
            } else {
                if (ei.find(_fStr, _bCase)) {
                    found = true;
                } else {
                    if (getNextValue(_fStr, _bCase, _inc, lastFound)) {
                        found = true;
                    }
                }
            }
        } else {
            if (getNextValue(_fStr, _bCase, _inc, lastFound)) {
                found = true;
            }
        }
    }

    /**
     * establishIndex
     * @author Anthony C. Liberto
     * /
    public void establishIndex() {
        EditorInterface ei = getFocusOwner();
        if (ei != null) {
            lastFound = Math.max(0, getIndexOf(ei));
        } else {
            lastFound = 0;
        }
    }

    /**
     * replaceValue
     * @param _sOld
     * @param _sNew
     * @param _bCase
     * @param _inc
     * @author Anthony C. Liberto
     */
    public void replaceValue(String _sOld, String _sNew, boolean _bCase, int _inc) {
        Object o = null;
        if (lastFound < 0 || lastFound >= formSize()) { //classification
            return;
        } //classification

        if (!hasLock(0)) { //23157
            if (lockRows()) { //23157
                regenerateForm(); //23157
            } else { //23157
                return; //23157
            } //23157
        } //23157

        o = get(lastFound); //classification
        if (o != null && o instanceof EditorInterface) { //classification
            EditorInterface ei = (EditorInterface) o;
            if (!ei.isEditable()) { //22805
                showError("msg11023.0"); //22805
                return; //22805
            } //22805
            if (!ei.isReplaceable()) { //22632
                showError("msg3005"); //22632
                return; //22632
            } //22632
            ei.replace(_sOld, _sNew, _bCase);
        } //classification
    }

    /**
     * replaceNextValue
     * @param _sOld
     * @param _sNew
     * @param _bCase
     * @param _inc
     * @author Anthony C. Liberto
     */
    public void replaceNextValue(String _sOld, String _sNew, boolean _bCase, int _inc) {
        replaceValue(_sOld, _sNew, _bCase, _inc);
        findValue(_sOld, _bCase, _inc);
    }

    /**
     * replaceAllValue
     * @param _sOld
     * @param _sNew
     * @param _bCase
     * @param _inc
     * @author Anthony C. Liberto
     */
    public void replaceAllValue(String _sOld, String _sNew, boolean _bCase, int _inc) {
        int ii = -1;
        if (!hasLock(0)) { //23157
            if (lockRows()) { //23157
                regenerateForm(); //23157
            } else { //23157
                return; //23157
            } //23157
        } //23157
        ii = formSize(); //classification
        for (int i = 0; i < ii; ++i) { //classification
            Object o = get(i); //classification
            if (o != null && o instanceof EditorInterface) { //classification
                if (lockRows()) { //23157
                    EditorInterface ei = (EditorInterface) o; //23157
                    if (ei.isEditable()) { //23157
                        if (ei.isReplaceable()) { //23157
                            ei.replace(_sOld, _sNew, _bCase); //23157
                        } //23157
                    } //23157
                } //23157
            }
        }
    }

    /**
     * spellCheck
     * @author Anthony C. Liberto
     */
    public void spellCheck() {
        EditorInterface ei = getFocusOwner();
        if (ei != null) {
            if (ei instanceof TextEditor) {
                eaccess().initSpellCheck();
                eaccess().spellCheck(null, (TextEditor) ei);
                eaccess().completeSpellCheck(true);
            } else if (ei instanceof LongEditor) { //50563
                eaccess().initSpellCheck(); //50563
                eaccess().spellCheck(null, (LongEditor) ei); //50563
                eaccess().completeSpellCheck(true); //50563
            } else { //50563
                eaccess().showError(this, "msg24004"); //50563
            }
        }
    }

    /**
     * prepareToEdit
     * @author Anthony C. Liberto
     */
    public void prepareToEdit() {
        int ii = formSize(); //classification
        for (int i = 0; i < ii; ++i) { //classification
            Object o = get(i); //classification
            if (o != null && o instanceof EditorInterface) { //classification
                EditorInterface ei = (EditorInterface) o;
                if (!ei.isDisplayOnly()) {
                    //51422					ei.prepareToEdit();
                    //51869			ei.prepareToEdit(null);				//51422
                    ei.prepareToEdit(null, this); //51869
                    return;
                }
            }
        }
    }

    /**
     * lock
     * @author Anthony C. Liberto
     */
    public void lock() {
        if (lockRows()) {
            regenerateForm();
        }
    }

    //22283	public void lockRows() {
    private boolean lockRows() { //22283
        String key = getKey();
        RowSelectableTable tmpTable = null;
        Profile prof = getActiveProfile(); //22411
        if (key != null) {
            EntityItem ean = (EntityItem) controllingTable.getRow(key);
            if (ean != null) {
                tmpTable = ean.getEntityItemTable();
                //try {
                    EntityItem lockOwnerEI = eaccess().getLockOwner(); // use hashtable if possible 
                    	//new EntityItem(null, getActiveProfile(), Profile.OPWG_TYPE, getActiveProfile().getOPWGID());
                    if (lockOwnerEI != null){
                    	eaccess().dBase().lock(tmpTable, getLockList(), prof, lockOwnerEI, LockGroup.LOCK_NORMAL);
                    }
                //} catch (MiddlewareRequestException _mre) {
                //    _mre.printStackTrace();
               // }
                //22411				if (!hasLock(key,1)) {
                if (!hasLock(0)) { //22411
                    //22283					LockGroup lockGroup = getLockGroup(key,1);
                    LockGroup lockGroup = tmpTable.getLockGroup(0, 1); //22283
                    if (lockGroup != null && lockGroup.getProfile() != prof) {
                        setMessage(lockGroup.toString());
                        showError();
                        return false; //22283
                    }
                }
            }
        }
        //22283		return;
        return true; //22283
    }

    //22283	public LockGroup getLockGroup(String _key, int _c) {
    //22283		return table.getLockGroup(table.getRowIndex(_key), 1);	//lock_update
    //22283	}

    /**
     * hasLock
     * @param _r
     * @return
     * @author Anthony C. Liberto
     */
    private boolean hasLock(int _r) {
       // try {
            EntityItem lockOwnerEI = eaccess().getLockOwner();
            	//new EntityItem(null, getActiveProfile(), Profile.OPWG_TYPE, getOPWGID());
            if (lockOwnerEI==null){
            	return false;
            }
            return table.hasLock(_r, 1, lockOwnerEI, getActiveProfile());
        //} catch (Exception ex) {
          //  ex.printStackTrace();
           // return false;
        //}
    }
    
    /**
     * hasLock
     * @param _key
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    private boolean hasLock(String _key, int _c) {
       // try {
            EntityItem lockOwnerEI = eaccess().getLockOwner();
            	//new EntityItem(null, getActiveProfile(), Profile.OPWG_TYPE, getOPWGID());
            if (lockOwnerEI==null){
            	return false;
            }
            return table.hasLock(table.getRowIndex(_key), _c, lockOwnerEI, getActiveProfile());
        //} catch (Exception ex) {
         //   ex.printStackTrace();
         //   return false;
        //}
    }

    /**
     * unlockRows
     * @author Anthony C. Liberto
     */
    public void unlockRows() {
        String key = curKey;
        EANFoundation ean = controllingTable.getRow(key);
        if (ean != null) {
            //			RowSelectableTable table = ((EntityItem)ean).getEntityItemTable();
           // try {
                EntityItem lockOwnerEI = eaccess().getLockOwner();
                	//new EntityItem(null, getActiveProfile(), Profile.OPWG_TYPE, getOPWGID());
                if (lockOwnerEI!=null){
                	eaccess().dBase().unlock(table, getLockList(), getActiveProfile(), lockOwnerEI, LockGroup.LOCK_NORMAL);
                }
            //} catch (MiddlewareRequestException _mre) {
            //    _mre.printStackTrace();
            //}
        }
    }

    /**
     * getEditorInterface
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public EditorInterface getEditorInterface(String _key) {
        EditorInterface ef = null;
        int ii = formSize(); //classification
        for (int i = 0; i < ii; ++i) { //classification
            Object obj = get(i); //classification
            if (obj != null && obj instanceof EditorInterface) { //classification
                EditorInterface tempEF = (EditorInterface) obj;
                if (tempEF.getKey().equals(_key)) {
                    ef = tempEF;
                    break;
                }
            }
        }
        return ef;
    }

    private int formSize() { //classification
        return vOrder.size(); //classification
    } //classification

    private Object get(int _i) { //classification
        String sKey = null;
        if (_i < 0 || _i >= vOrder.size()) { //23167
            return null;
        } //23167
        sKey = (String) vOrder.get(_i); //classification
        if (map.containsKey(sKey)) { //classification
            return map.get(sKey); //classification
        } //classification
        return null; //classification
    } //classification

    /*private int getIndexOf(EditorInterface _ei) { //classification
        String sKey = _ei.getKey(); //classification
        if (sKey != null) { //classification
            vOrder.indexOf(sKey); //classification
        } //classification
        return -1; //classification
    } //classification
*/
    /*
     dwb_20041117
    	private String generateKey(String _eType, String _attCode, boolean _new) {		//classification
    		String sKey = _eType + ":" + _attCode;										//classification
    		if (_new) {																	//classification
    			return new String(sKey);												//classification
    		}																			//classification
    		return sKey;																//classification
    	}																				//classification
    */
    /**
     * getCurrentEANMetaAttribute
     * @return
     * @author Anthony C. Liberto
     */
    public EANMetaAttribute getSelectedEANMetaAttribute(){
    	return getCurrentEANMetaAttribute();
    }
    public EANMetaAttribute getCurrentEANMetaAttribute() {
        if (curComponent != null && curComponent instanceof EditorInterface) { //22815
            EANAttribute att = ((EditorInterface) curComponent).getAttribute(); //22815
            if (att != null) {
                return att.getMetaAttribute(); //22815
            }
        }
        return null;
    }

    /**
     * getSelectedObject
     * @return
     * @author Anthony C. Liberto
     */
    public Object getSelectedObject() {
        return controllingTable.getRow(curKey);
    }

    /**
     * isNew
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isNew() { //19937
        return isNew(curKey); //19937
    } //19937

    /**
     * isNew
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isNew(String _key) { //19937
        Object o = controllingTable.getRow(_key); //19937
        if (o instanceof EntityItem) { //19937
            return (((EntityItem) o).isNew() && controllingTable.getRowCount() > 1); //22150
            //22150			return ((EntityItem)o).isNew();			//19937
        }
        return false; //19937
    } //19937

    /**
     * moveToError
     * @param bre
     * @author Anthony C. Liberto
     */
    public void moveToError(EANBusinessRuleException bre) { //20069
        Object o = bre.getObject(0);
        if (o instanceof EANAttribute) {
            EANAttribute att = (EANAttribute) o;
            EntityItem ei = (EntityItem) att.getParent();
            EntityItem eip = (EntityItem) ei.getUpLink(0);
            EANFoundation ean = null;
            String key = null;
            EditorInterface ef = null;
            int rec = controllingTable.getRowIndex(ei.getKey());

            if (rec < 0) {
                rec = controllingTable.getRowIndex(eip.getKey());
            }

            if (rec < 0) {
                return;
            }

            ean = controllingTable.getRow(rec);
            curKey = ean.getKey();
            //dwb_20041117			String key = generateKey(ei.getEntityType(), att.getAttributeCode(), false);
            key = eaccess().getKey(ei, att.getAttributeCode()); //dwb_20041117
            ef = getEditorInterface(key);
            if (ef != null) {
                ef.requestFocus();
                scrollTo((JComponent) ef);
            }
        } else if (o instanceof EntityItem) {
            EntityItem ei = (EntityItem) o;
            EntityItem eip = (EntityItem) ei.getUpLink(0);
            EANFoundation ean = null;
            int rec = controllingTable.getRowIndex(ei.getKey());

            if (rec < 0) {
                rec = controllingTable.getRowIndex(eip.getKey());
            }

            if (rec < 0) {
                return;
            }

            ean = controllingTable.getRow(rec);
            if (ean.getKey() != curKey) {
                toggleRecord(ean.getKey(), true);
            }
        }
    }

    /**
     * setKey
     * @param _s
     * @author Anthony C. Liberto
     */
    private void setKey(String _s) { //21954
        if (_s != null) { //21954
            curKey = new String(_s);
        } //21954
    } //21954

    /**
     * updateKey
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public boolean updateKey(String _s) { //22098
        if (!curKey.equals(_s)) { //22098
            setKey(_s); //22098
            return true; //22098
        } //22098
        return false; //22098
    } //22098

    /**
     * getKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getKey() {
        return curKey;
    }

    /**
     * resetTouch
     * @author Anthony C. Liberto
     */
    public void resetTouch() {
    } //21825

    /**
     * canContinue
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean canContinue() { //22455
        EditorInterface ei = getFocusOwner(); //22455
        if (ei != null) { //22455
            if (!ei.canLeave()) { //22455
                return false; //22455
            } //22455
        } //22455
        return true; //22455
    } //22455
    */

    /*
     acl_20030506
    */
    /**
     * commit
     *
     * @param _ei
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     * @return
     * @author Anthony C. Liberto
     */
    public boolean commit(EditorInterface _ei) throws EANBusinessRuleException {
        if (_ei != null) {
            _ei.stopCellEditing();
            try {
                if (_ei.hasChanged()) {
                    table.put(table.getRowIndex(_ei.getKey()), 1, _ei.getCellEditorValue());
                }
            } catch (EANBusinessRuleException _bre) {
                eaccess().showException(_bre, this, ERROR_MESSAGE, OK); //53344
                _ei.cancel(); //53344
                throw _bre;
            }

            if (ec != null) {
                ec.refreshUpdate();
                ec.revalidateForm(_ei.getAttribute());
            }
            return true;
        }
        return false;
    }

    /**
     * verifyNewFocus
     * @author Anthony C. Liberto
     * @return boolean
     * @param _ei
     */
    public boolean verifyNewFocus(EditorInterface _ei) {
        Component c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        if (c != null && c instanceof EditorInterface) {
            if (!_ei.equals(c)) {
                return verify((EditorInterface) c);
            }
        }
        return true;
    }

    /**
     * verify
     * @param _ei
     * @return
     * @author Anthony C. Liberto
     */
    private boolean verify(EditorInterface _ei) {
        boolean canLeave = false;
        if (_ei != null) {
            if (!_ei.hasChanged() || !_ei.isEditable()) {
                return true;
            }
            canLeave = _ei.canLeave();
            if (canLeave) {
                try {
                    if (!commit(_ei)) {
                        return false;
                    }
                } catch (Exception _ex) {
                    _ex.printStackTrace();
                    return false;
                }
            }else{
            //System.err.println("Editform.verify ei.canleave is false "+_ei.getClass().getName());
            	//_ei.requestFocus();
            }
            return canLeave;
        }
        return true;
    }

    /**
     * getTable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public RowSelectableTable getTable() {
        return table;
    }

    /*
     52189
     */
    /**
     * select
     * @author Anthony C. Liberto
     */
    public void select() {
    }

    /**
     * deselect
     * @author Anthony C. Liberto
     */
    public void deselect() {
        Collection c = map.values();
        Object[] o = c.toArray();
        if (o != null) {
            int ii = o.length;
            for (int i = 0; i < ii; ++i) {
                if (o[i] instanceof MultiEditor) {
                    ((MultiEditor) o[i]).hidePopup();
                }
            }
        }
    }
    /*
     xl8r
     */

    /**
     * importTable
     */
    public void importTable() {
        //importString(eaccess().getImportString(this));
        eaccess().importFromFile(this); // use Importable interface to callback based on filetype selected
    }

    /**
     * Importable interface
     * processXLSImport import from Excel xls ss
     * @param index int counter into number of rows in ss
     * @param attrCodes String[] attribute codes
     * @param attrValue String[], some may be null
     */
    public EANBusinessRuleException processXLSImport(int index, String[] attrCodes, String[] attrValue)
    {
    	// populate a newly created entity with values from xls ss
    	return processImport(index, attrCodes, attrValue);
    }

    /**
     * importString, is the original implementation.. doesnt display errors
     * @param _s
     */
    public void importString(String[] _s) {
        if (_s != null) {
            int ii = _s.length;
            String[] head = Routines.getStringArray(_s[0], TAB_DELIMIT, true);
            for (int i = 1; i < ii; ++i) {
                String[] data = Routines.getStringArray(_s[i], TAB_DELIMIT, true);
                processImport(i, head, data);
            }
        }
    }

    /**
     * processImport
     * @param _head
     * @param _data
     * @author Anthony C. Liberto
     */
    private EANBusinessRuleException processImport(int index, String[] _head, String[] _data) {
       	EANBusinessRuleException bre = null;
       	if (_head != null && _data != null) {
            int hh = _head.length;
            int dd = _data.length;
            EntityItem ei = getEntityItem(); // get current ei
            if (ei !=null && index==0 && ei.getEntityID()<0){
            	// current entity is newly created and is the first one, so fill it in with import values
            }else{
                addRowImport(); // create a new row and entityitem
                ei = getEntityItem();
            }

            if (ei==null){
                bre = new LocalRuleException("EntityItem can not be found");
                return bre;
            }

            EntityGroup eg = ei.getEntityGroup();
            if (eg.isRelator() || eg.isAssoc()) {
                EntityList elist = eg.getEntityList();
                boolean bCreateParent = false;

                if (elist != null) {
                    bCreateParent = elist.isCreateParent();
                }

                if (bCreateParent) {
                    ei = (EntityItem) ei.getUpLink(0);
                } else {
                    ei = (EntityItem) ei.getDownLink(0);
                }
            }

            for (int h = 0; h < hh && h < dd; ++h) {
            	try{
            		processImportAttribute(ei, _head[h], _data[h]);
            	} catch (EANBusinessRuleException _br) {
            		_br.printStackTrace();
            		if (bre==null){
            			bre = _br;
            		}else{
						//only one msg allowed per entityitem, attribute msgs will be accumulated
						// a null ptr will have first msg only
            			bre.addException(_br); // accumulate all msgs
            		}
            	}
            }
            regenerateForm(); // load form with updated values           
        }
        return bre;
    }

    /**
     * addRowImport - anonymous classes override this
     */
    public void addRowImport() {
    }

    /**
     * processImportAttribute
     * @param _ei   EntityItem to add attributes to
     * @param _head String attribute code
     * @param _data String attribute value
     */
    private void processImportAttribute(EntityItem _ei, String _head, String _data)
    throws EANBusinessRuleException
    {
   		appendLog("processImportAttribute: entered for " + _ei.getKey() +" attrcode "+_head+" data "+_data);

        if (Routines.have(_data)) {
            EANFoundation ean = null;
            try {
            	String key = _head;
            	// allow this to work with any old tabbed files that may already have entitytype as key
            	if (!key.startsWith(_ei.getEntityType()+":")){
            		key = _ei.getEntityType()+":"+_head;
            	}
                ean = _ei.getEANObject(key);
            } catch (Exception _ex) {
                _ex.printStackTrace();
                String msg = _ex.getMessage();
                if (msg ==null || msg.length()==0){
                	msg = _ex.getClass().getName()+" exception getting "+_head;
                }

                EANBusinessRuleException bre = new LocalRuleException();
                bre.add(_ei, msg);
                throw bre;
            }
            if (ean != null) {
                if (!((EANAttribute) ean).isEditable()) {
                    appendLog("processImportAttribute: "+_head + " not editable.");
                    EANBusinessRuleException bre = new LocalRuleException();
                    bre.add(ean, _head + " not editable.");
                    throw bre;
                } else if (ean instanceof TextAttribute) {
                    ((TextAttribute) ean).put(_data);
                } else if (ean instanceof LongTextAttribute) {
                    ((LongTextAttribute) ean).put(_data);
                } else if (ean instanceof XMLAttribute) {
                	// make sure data is enclosed in tags
                	if (!_data.trim().startsWith("<")){
                		_data = "<pre>"+_data+"</pre>";
                	}
                    ((XMLAttribute) ean).put(_data);
                } else if ((ean instanceof SingleFlagAttribute) ||
                		(ean instanceof StatusAttribute) ||
                		(ean instanceof TaskAttribute))
                {
                	EANFlagAttribute att = (EANFlagAttribute) ean;
                    MetaFlag[] mFlags = (MetaFlag[]) att.get();
                    boolean foundFlag = false;
                    if (mFlags != null) {
                        int ii = mFlags.length;
                        for (int i = 0; i < ii; ++i) {
                            if (mFlags[i] != null) {
                                if (descriptionEquals(mFlags[i], _data)) {
                                	foundFlag = true;
                                    mFlags[i].setSelected(true);
                                } else if (mFlags[i].isSelected()) {
                                    mFlags[i].setSelected(false);
                                }
                            }
                        }
                        att.put(mFlags);
                    }
                    if (!foundFlag){
                    	StringBuffer sb = new StringBuffer();
                    	appendLog("processImportAttribute: "+_head + " could not find matching flag for "+_data);
                        EANBusinessRuleException bre = new LocalRuleException();
                        EANMetaFlagAttribute metaAttr = (EANMetaFlagAttribute)_ei.getEntityGroup().getMetaAttribute(_head);
                        if (metaAttr !=null){
                        	// find any filtering attributes to warn user, these are needed before this attr
                            for (int x = 0; x < metaAttr.getMetaFlagCount(); x++) {
                                MetaFlag mf = metaAttr.getMetaFlag(x);
                                if (mf.toString().equals(_data)){
                                	for (int m=0; m<mf.getFilterCount();m++){
                                		MetaFlag mff = mf.getFilter(m);
                                		sb.append(mff.getParent().getKey()+" must be set first.");
                                		break;
                                	}
                                }
                            }
                        }

                        bre.add(ean, _head + " could not find matching flag for "+_data+" "+sb.toString());
                        throw bre;
                    }
                } else if (ean instanceof MultiFlagAttribute) {
                    MultiFlagAttribute att = (MultiFlagAttribute) ean;
                    int foundFlagCnt = 0;
                    String[] flags = Routines.getStringArray(_data, FLAG_DELIMIT, true);
                    if (flags != null) {
                        HashMap hash = loadKeys(flags);
                        if (hash != null) {
                            MetaFlag[] mFlags = (MetaFlag[]) att.get();
                            if (mFlags != null) {
                                int ii = mFlags.length;
                                for (int i = 0; i < ii && !hash.isEmpty(); ++i) {
                                    if (mFlags[i] != null) {
                                        if (keyExists(hash, mFlags[i])) {
                                            mFlags[i].setSelected(true);
                                            foundFlagCnt++;
                                        } else if (mFlags[i].isSelected()) {
                                            mFlags[i].setSelected(false);
                                        }
                                    }
                                }
                                att.put(mFlags);
                            }
                        }

                        if (foundFlagCnt != flags.length ){
                        	StringBuffer sb = new StringBuffer();
                        	appendLog("processImportAttribute: "+_head + " could not find matching flags for "+_data);
                        	EANBusinessRuleException bre = new LocalRuleException();
                        	EANMetaFlagAttribute metaAttr = (EANMetaFlagAttribute)_ei.getEntityGroup().getMetaAttribute(_head);
                        	if (metaAttr !=null){
                        		// find any filtering attributes to warn user, these are needed before this attr
                        		for (int x = 0; x < metaAttr.getMetaFlagCount(); x++) {
                        			MetaFlag mf = metaAttr.getMetaFlag(x);
                        			for (int k=0; k<flags.length; k++){
                        				if (mf.toString().equals(flags[k])){
                        					for (int m=0; m<mf.getFilterCount();m++){
                        						MetaFlag mff = mf.getFilter(m);
                        						sb.append("It is filtered by "+mff.getParent().getKey());
                        						break;
                        					}
                        				}
                        			}
                        		}
                        	}

                        	bre.add(ean, _head + " could not find matching flag for "+_data+" "+sb.toString());
                        	throw bre;
                        }
                    }else{   // end flags!=null
                    	appendLog("processImportAttribute: "+_head + " could not find matching flags for "+_data);
                    	EANBusinessRuleException bre = new LocalRuleException();
                    	bre.add(ean, _head + " could not find matching flags for "+_data);
                    	throw bre;
                    }
                } else if (ean instanceof BlobAttribute) {
                    appendLog("processImportAttribute: Blob not supported for "+_head);
                    EANBusinessRuleException bre = new LocalRuleException();
                    bre.add(ean,_head + " Blob attribute is not supported");
                    throw bre;
                } else {
                    appendLog("processImportAttribute: not supported class: " + ean.getClass().getName());
                    EANBusinessRuleException bre = new LocalRuleException();
                    bre.add(ean,_head +" "+ean.getClass().getName()+
                    		" is not supported");
                    throw bre;
                }
            }else{
                // some exceptions are caught when generating an attribute and this fails, so check for null now
            	EANBusinessRuleException bre = new LocalRuleException();
            	bre.add(_ei, _head+" not found in meta for "+_ei.getEntityType());
            	throw bre;
            }
        }else{
        	appendLog("processImportAttribute: not updating "+_head+" data is null");
        }
    }

    /**
     * descriptionEquals
     * @param _flag
     * @param _data
     * @return
     * @author Anthony C. Liberto
     */
    private boolean descriptionEquals(MetaFlag _flag, String _data) {
        if (!caseSensitiveImport()) {
            return _flag.getLongDescription().equalsIgnoreCase(_data);
        }
        return _flag.getLongDescription().equals(_data);
    }

    /**
     * loadKeys
     * @param _desc
     * @return
     * @author Anthony C. Liberto
     */
    private HashMap loadKeys(String[] _desc) {
        HashMap out = null;
        int ii = -1;
        if (_desc != null) {
            out = new HashMap();
            ii = _desc.length;
            for (int i = 0; i < ii; ++i) {
                if (!caseSensitiveImport()) {
                    out.put(_desc[i].toLowerCase(), _desc[i]);
                } else {
                    out.put(_desc[i], _desc[i]);
                }
            }
        }
        return out;
    }

    /**
     * keyExists
     * @param _map
     * @param _flag
     * @return
     * @author Anthony C. Liberto
     */
    private boolean keyExists(HashMap _map, MetaFlag _flag) {
        String key = null;
        boolean out = false;
        if (!caseSensitiveImport()) {
            key = _flag.getLongDescription().toLowerCase();
        } else {
            key = _flag.getLongDescription();
        }
        out = _map.containsKey(key);
        if (out) {
            _map.remove(key);
        }
        return out;
    }

    /*
     broke this out so it was easier to change
     if descide to use less rigid import at a later date.
     */
    /**
     * caseSensitiveImport
     * @return
     * @author Anthony C. Liberto
     */
    private boolean caseSensitiveImport() {
        return true;
    }

    /*
     dwb_20041117
     */
    private String generateKey(String _eType, String _attCode, String _eContext, boolean _new) {
        String sKey = null;
        if (_eContext == null) {
            _eContext = deriveContext(table, _eType);
        }
        sKey = _eType + ":" + _attCode + ":" + _eContext;
        if (_new) {
            return new String(sKey);
        }
        return sKey;
    }

    /*
     lswwcc returned (relator)
     showing lsww (child)
     */
    private String deriveContext(RowSelectableTable _rst, String _eType) {
        if (_rst != null) {
            int actRow = _rst.getActiveRow();
            if (actRow >= 0 && actRow < _rst.getRowCount()) {
                EANFoundation ean = _rst.getRow(actRow);
                if (ean != null && ean instanceof EntityItem) {
                    EntityGroup eg = ((EntityItem) ean).getEntityGroup();
                    if (eg != null) {
                        if (eg.isRelator()) {
                            return "R";
                        } else {
                            return "C";
                        }
                    }
                }
            }
        }
        return "C";
    }

    /*
     USRO-R-RTAR-672S9Y
     */
    /**
     * isPasteable
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPasteable() {
        if (table != null) {
            EditorInterface ei = (EditorInterface) curComponent;
            if (ei != null) {
                return isPasteable(ei.getAttribute());
            }
        }
        return false;
    }

    private boolean isPasteable(EANAttribute _ean) {
        if (_ean instanceof SingleFlagAttribute) {
            return false;
        } else if (_ean instanceof StatusAttribute) {
            return false;
        } else if (_ean instanceof MultiFlagAttribute) {
            return false;
        } else if (_ean instanceof LongTextAttribute) {
            return true;
        } else if (_ean instanceof BlobAttribute) {
            return false;
        } else if (_ean instanceof TextAttribute) {
            return true;
        }
        return false;
    }

/*
 6245
 */
	/**
     * canStopEditing
     *
     * @author Anthony C. Liberto
     * @return
     */
    public boolean canStopEditing() {
		if (curComponent != null && curComponent instanceof EditorInterface) {
			EditorInterface ei = (EditorInterface) curComponent;
			if (ei.canLeave()) {
				try {
					return commit(ei);
				} catch (Exception _x) {
					EAccess.report(_x,false);
				}
			}
		}
		return false;
	}
}
