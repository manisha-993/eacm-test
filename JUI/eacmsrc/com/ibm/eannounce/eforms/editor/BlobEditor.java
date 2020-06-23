/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: BlobEditor.java,v $
 * Revision 1.3  2009/05/22 14:20:39  wendy
 * Performance cleanup
 *
 * Revision 1.2  2008/01/30 16:27:04  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:33  wendy
 * Reorganized JUI module
 *
 * Revision 1.3  2006/04/20 19:08:27  tony
 * adjsuted cursor logic so
 * that it will not be set to default
 *
 * Revision 1.2  2005/09/12 19:03:12  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:56  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2005/09/08 17:59:03  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.8  2005/06/21 18:09:50  tony
 * clarified message 3015a
 *
 * Revision 1.7  2005/06/21 17:58:01  tony
 * 24423680 -- updating non-existant blobs.
 *
 * Revision 1.6  2005/02/22 19:47:46  tony
 * //MN_22928975
 *
 * Revision 1.5  2005/02/03 16:38:53  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.4  2005/02/01 20:48:32  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/27 19:15:16  tony
 * JTest Format
 *
 * Revision 1.2  2004/02/26 21:53:17  tony
 * remoteDatabaseInterface centralization.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.12  2003/11/18 20:54:03  tony
 * added formEditorInterface to simplify formEditor modification.
 *
 * Revision 1.11  2003/10/29 00:18:35  tony
 * removed System.out. statements.
 *
 * Revision 1.10  2003/08/21 15:57:34  tony
 * 51869
 *
 * Revision 1.9  2003/07/29 22:54:31  joan
 * 51559
 *
 * Revision 1.8  2003/07/09 20:47:35  tony
 * 51422 -- blobEditor popup on double click
 *
 * Revision 1.7  2003/06/16 19:04:03  tony
 * 51294
 *
 * Revision 1.6  2003/05/19 22:25:13  tony
 * updated blob editor making more user friendly.
 *
 * Revision 1.5  2003/05/14 19:32:36  tony
 * 50661
 *
 * Revision 1.4  2003/05/06 18:56:32  tony
 * 50546
 *
 * Revision 1.3  2003/05/06 16:38:21  tony
 * acl_20030506 -- enhanced focus logic to prevent error
 * messages from firing when refocusing the component.
 *
 * Revision 1.2  2003/03/04 22:34:51  tony
 * *** empty log message ***
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.22  2002/11/07 16:58:27  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.editor;
import com.elogin.*;
import COM.ibm.opicmpdh.transactions.*;
//import com.Updater.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.eannounce.eforms.editform.FormEditorInterface; //acl_20030506
//acl_20030506 import com.ibm.eannounce.eForms.editor.Verifier.Verifier;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class BlobEditor extends EPanel implements ActionListener, EditorInterface {
	private static final long serialVersionUID = 1L;
	/**
     * changeEvent
     */
    transient protected ChangeEvent changeEvent = null;

    //	private eRolloverButton btn[] = new eRolloverButton[3];
    private EButton btn[] = new EButton[3];
    private EPanel pnlBtn = new EPanel(new GridLayout(1, 3, 1, 0));

    private String[] lbl = { "atch", "dtch", "lnch" };

    private boolean editable = false;
    private ELabel lblExtension = new ELabel();

    private FileDialog m_fd = null;
    //private OPICMBlobValue m_val = null;
    private OPICMBlobValue m_newBlob = null;

    private Location m_blobLoc = null;

    //50661	private boolean readyToListen = false;									//17461
    private boolean readyToListen = true; //50661

    private boolean m_bdisplayOnly = false;
    private String m_strKey = null;
    //acl_20030506	private RowSelectableTable m_table = null;
    private FormEditorInterface ef = null; //acl_20030506
    private EANAttribute m_ean = null;
    private boolean m_bfound = false;
//    private Rectangle m_rec = null;
    private boolean isForm = false;
    //acl_20030506	private Verifier m_verifier = null;

    /**
     * blobEditor
     * @author Anthony C. Liberto
     */
    public BlobEditor() {
        super(new BorderLayout());
        init();
        m_fd = new FileDialog(eaccess().getLogin(), "", FileDialog.LOAD);
    }

    //acl_20030506	public blobEditor(EANAttribute _att, String _key, Verifier _verify) {
    /**
     * blobEditor
     * @param _att
     * @param _key
     * @param _ef
     * @author Anthony C. Liberto
     */
    public BlobEditor(EANAttribute _att, String _key, FormEditorInterface _ef) { //acl_20030506
        super();
        ef = _ef;
        //		super(new BorderLayout());
        refreshDisplay(_att);
        setKey(_key);
        //acl_20030506		m_verifier = _verify;
        isForm = true;
        showExtension();
        init();
        m_fd = new FileDialog(eaccess().getLogin(), "", FileDialog.LOAD);
    }

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        changeEvent = null;
        //acl_20030506		m_table = null;
        ef = null;
        btn = null;
        lbl = null;
        m_fd = null;
        //m_val = null;
        m_newBlob = null;
        m_blobLoc = null;
        lblExtension.dereference();
        lblExtension = null;
//acl_20060420        super.dereference();
        removeAll();		//acl_20060420
        removeNotify();		//acl_20060420
    }

    /**
     * showExtension
     * @author Anthony C. Liberto
     */
    private void showExtension() {
        add("West", lblExtension);
        validate();
    }

    /**
     * hideExtension
     * @author Anthony C. Liberto
     * /
    public void hideExtension() {
        remove(lblExtension);
        validate();
    }*/

    private void buildButtons() {
        int ii = lbl.length;
        for (int i = 0; i < ii; ++i) {
            //			btn[i] = new eRolloverButton(getImageIcon(lbl[i]+".gif"));
            //51294			btn[i] = new eButton(getImageIcon(lbl[i]+".gif"));
            btn[i] = new EButton(getString(lbl[i])); //51294
            btn[i].setMnemonic(getChar(lbl[i] + "-s")); //51294
            btn[i].setOpaque(false);
            btn[i].setToolTipText(getString(lbl[i] + "-t"));
            btn[i].addActionListener(this);
            btn[i].setActionCommand(lbl[i]);
            pnlBtn.add(btn[i]);
        }
    }

    private void init() {
        setOpaque(false);
        buildButtons();
        if (isForm) {
            pnlBtn.setOpaque(false);
            add("East", pnlBtn);
        } else {
            add("Center", pnlBtn);
        }
        lblExtension.setFont(getFont());
    }

    /**
     * actionPerformed
     *
     * @author Anthony C. Liberto
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        String source = e.getActionCommand();
        if (!readyToListen) { //17461
            readyToListen = true; //17461
            return; //17461
        } //17461
        if (source.equals(lbl[0])) {
            attach();
        } else if (source.equals(lbl[1])) {
            if (m_ean.isActive()) {
                detach();
            } else {
                showError("msg2004");
            }
        } else if (source.equals(lbl[2])) {
            if (m_ean.isActive()) {
                launch();
            } else {
                showError("msg2004");
            }
        }
    }

    private void attach() {
        String type = m_ean.getMetaAttribute().getAttributeType();
        if (type.equals("B")) {
            attachBlob(null);
        }
    }

    private void detach() {
        String type = m_ean.getMetaAttribute().getAttributeType();
        if (type.equals("B")) {
            detachBlob();
        }
    }

    private void launch() {
        String type = null;
        if (isModalCursor()) { //51559
            if (!isModalBusy()) { //51559
                setModalBusy(true); //51559
                type = m_ean.getMetaAttribute().getAttributeType(); //51559
                if (type.equals("B")) { //51559
                    launchBlob();
                } //51559
                setModalBusy(false); //51559
            }
        } else {
            if (!isBusy()) { //013778
                setBusy(true);
                type = m_ean.getMetaAttribute().getAttributeType();
                if (type.equals("B")) {
                    launchBlob();
                }
                setBusy(false);
            } //013778
        }
    }

    private void attachBlob(String _ext) {
        String dir = null;
        String file = null;
        String strFile = null;
        File f = null;
        m_fd.setTitle(getString("atch-l"));
        m_fd.setMode(FileDialog.LOAD);
        m_fd.show();
        dir = m_fd.getDirectory();
        file = m_fd.getFile();

        if (dir == null || file == null) {
            return;
        }
        f = new File(dir+file);		//24423680

        if (f.exists()) {					//24423680
	        if (validateFileSize(f)) {
	            m_newBlob = new OPICMBlobValue(m_ean.getNLSID(), null, null);
	            m_blobLoc = new Location(dir, file);
	            strFile = Routines.truncateFilename(30, file);
	            m_newBlob.setBlobExtension(strFile);
	            setText(strFile);
	            //acl_20030506			if (isForm)
	            //acl_20030506				m_verifier.commit(this);
	            if (isForm && ef != null) { //acl_20030506
	                try { //acl_20030506
	                    ef.commit(this); //acl_20030506
	                } catch (Exception _ex) { //acl_20030506
	                    _ex.printStackTrace(); //acl_20030506
	                } //acl_20030506
	            } //acl_20030506
	        } else {
        	    showError("msg3015");
        	}
		} else {							//24423680
			String[] parms = new String[1];	//24423680
			parms[0] = dir + file;			//24423680
			eaccess().showError(this, "msg3015a",parms);
		}									//24423680
    }

    private void detachBlob() {
        String file = "";
        String dir = null;
        int size = 0;
        m_fd.setTitle(getString("dtch-l"));
        m_fd.setMode(FileDialog.SAVE);

        if (m_ean instanceof EANBlobAttribute) {
            file = ((EANBlobAttribute) m_ean).getBlobExtension();
        }

        if (!Routines.have(file)) {
            if (m_newBlob != null) {
                file = m_newBlob.getBlobExtension();
            } else {
                showError("msg23041");
                return;
            }
        }

        m_fd.setFile(file);
        m_fd.show();
        dir = m_fd.getDirectory();
        file = m_fd.getFile();
        if (dir == null || file == null) {
            return;
        }

        try {
            OPICMBlobValue opbv = getBlobFromDatabase();
            if (opbv != null) {
                size = opbv.size();
                if (size > 0) {
                    opbv.saveToFile(dir + file);
                } else if (m_blobLoc != null) {
                    m_blobLoc.copyToFile(dir + file);
                } else {
                    showError("msg23041");
                }
            }
        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    private void launchBlob() {
        String dir = System.getProperty("java.io.tmpdir");
        String blobCount = "_" + eaccess().getBlobCount();
        EntityItem ei = (EntityItem) m_ean.getParent();
        String tmpFile = ei.getEntityType() + ei.getEntityID() + m_ean.getAttributeCode() + blobCount + ((EANBlobAttribute) m_ean).getBlobExtension();		//MN_22928975
        String file = Routines.replaceChar(tmpFile,' ', '_');			//MN_22928975
//MN_22928975        String file = ei.getEntityType() + ei.getEntityID() + m_ean.getAttributeCode() + blobCount + ((EANBlobAttribute) m_ean).getBlobExtension();

        File f = new File(dir + file);
        int size = 0; //013244

        appendLog("lauching blob: " + dir + file);						//MN_22928975

        f.deleteOnExit();
        try {
            OPICMBlobValue opbv = getBlobFromDatabase();
            if (opbv == null) {
                showError("msg23022");
                return;
            }
            if (opbv != null) { //013244
                size = opbv.size();
            } //013244
            if (size > 0) { //013244
                opbv.saveToFile(dir + file);
            }
        } catch (IOException x) {
            x.printStackTrace();
        }
        if (size > 0) { //013244
        	eaccess().launch(dir + file);
        } else if (m_blobLoc != null) { //013244
        	eaccess().launch(m_blobLoc.getLocation()); //013244
        } else { //013244
            showError("msg23040");
        }
    }

    private boolean validateFileSize(File _f) {
        if (_f.length() > 9999999) {
            return false;
        }
        return true;
    }

    private OPICMBlobValue getBlobFromDatabase() {
        String ext = "";
        EntityItem ei = null;
        if (!m_ean.isActive()) {
            m_ean.setActive(true);
            m_newBlob = new OPICMBlobValue(m_ean.getNLSID(), null, null);
        }
        if (m_newBlob != null) {
            return m_newBlob;
        }
        m_newBlob = ((EANBlobAttribute) m_ean).getOPICMBlobValue();

        if (m_ean instanceof EANBlobAttribute) {
            ext = ((EANBlobAttribute) m_ean).getBlobExtension();
        }

        ei = (EntityItem) m_ean.getParent();
        if (m_blobLoc != null) {
            try {
                m_newBlob.loadFromFile(m_blobLoc.getLocation());
            } catch (IOException x) {
                x.printStackTrace();
            }
        } else if (Routines.have(ext) && ei.getEntityID() > 0 && m_newBlob.size() <= 0) {
            byte[] baAttributeValue = eaccess().dBase().getBlobValue((EANBlobAttribute) m_ean);
            m_newBlob.setBlobValue(baAttributeValue);
            m_newBlob.setBlobExtension(ext);
        }

        return m_newBlob;
    }

    /*
     * editor Interface
     */
    /**
     * setAttribute
     *
     * @author Anthony C. Liberto
     * @param _ean
     */
    public void setAttribute(EANAttribute _ean) {
        m_ean = _ean;
        m_newBlob = null; //21738
        m_blobLoc = null; //21738
    }

    /**
     * getAttribute
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EANAttribute getAttribute() {
        return m_ean;
    }

    /**
     * getMetaAttribute
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EANMetaAttribute getMetaAttribute() { //22098
        if (m_ean != null) { //22098
            return m_ean.getMetaAttribute();
        } //22098
        return null; //22098
    } //22098

    /**
     * refreshDisplay
     *
     * @author Anthony C. Liberto
     * @param _att
     */
    public void refreshDisplay(EANAttribute _att) {
        setAttribute(_att);
        setText(_att.toString());
    }

    /**
     * canLeave
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canLeave() {
        return true;
    }

    /**
     * hasChanged
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasChanged() { //acl_20021023
        return true; //acl_20021023
    } //acl_20021023

    /**
     * setDisplayOnly
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setDisplayOnly(boolean _b) {
        if (!getAttribute().isEditable()) {
            _b = true;
        }
        if (_b) {
            btn[0].setEnabled(false);
        } else {
            btn[0].setEnabled(true);
        }
        m_bdisplayOnly = _b;
        //50546		setFocusable(!_b);
    }

    /**
     * isDisplayOnly
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isDisplayOnly() {
        return m_bdisplayOnly;
    }

    /**
     * cancel
     *
     * @author Anthony C. Liberto
     */
    public void cancel() {
    }

    /**
     * getKey
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getKey() {
        return m_strKey;
    }

    /**
     * setKey
     * @author Anthony C. Liberto
     * @param _key
     */
    public void setKey(String _key) {
        m_strKey = new String(_key);
    }

    /**
     * paste
     *
     * @author Anthony C. Liberto
     */
    public void paste() {
    }

    /**
     * cut
     *
     * @author Anthony C. Liberto
     */
    public void cut() {
    }

    /**
     * copy
     *
     * @author Anthony C. Liberto
     */
    public void copy() {
    }

    /**
     * isRequired
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isRequired() {
        return getAttribute().isRequired();
    }

    /**
     * find
     * @author Anthony C. Liberto
     * @return boolean
     * @param _bCase
     * @param _strFind
     */
    public boolean find(String _strFind, boolean _bCase) {
        return false;
    }

    /**
     * replace
     * @author Anthony C. Liberto
     * @return boolean
     * @param _bCase
     * @param _new
     * @param _old
     */
    public boolean replace(String _old, String _new, boolean _bCase) {
        return false;
    }

    /**
     * isFound
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isFound() {
        return m_bfound;
    }

    /**
     * getInformation
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getInformation() {
        return Routines.getInformation(getAttribute());
    }

    /**
     * deactivate
     *
     * @author Anthony C. Liberto
     */
    public void deactivate() {
    }

    /**
     * resetFound
     *
     * @author Anthony C. Liberto
     */
    public void resetFound() {
    }

    /**
     * prepareToEdit
     * @author Anthony C. Liberto
     * @param _c
     * @param _eo
     */
    public void prepareToEdit(EventObject _eo, Component _c) { //51869
        if (_eo == null || _eo instanceof KeyEvent) { //51422
            readyToListen = true; //51422
        } else { //51422
            readyToListen = false; //51422
        } //51422
        revalidate();
    }
    /**
     * setEffectivity
     * @author Anthony C. Liberto
     * @param _from
     * @param _to
     */
    public void setEffectivity(String _from, String _to) {
    }
    /**
     * @see java.awt.Component#addKeyListener(java.awt.event.KeyListener)
     * @author Anthony C. Liberto
     */
    public void addKeyListener(KeyListener _kl) {
    }
    /**
     * @see java.awt.Component#removeKeyListener(java.awt.event.KeyListener)
     * @author Anthony C. Liberto
     */
    public void removeKeyListener(KeyListener _kl) {
    }

    /**
     * TableCellEditor
     *
     * @param c
     */
    public void addCellEditorListener(CellEditorListener c) {
        listenerList.add(CellEditorListener.class, c);
    }

    /**
     * @see javax.swing.CellEditor#cancelCellEditing()
     * @author Anthony C. Liberto
     */
    public void cancelCellEditing() {
        m_blobLoc = null;
        readyToListen = false; //17461
        editingCanceled();
    }

    /**
     * @see javax.swing.CellEditor#getCellEditorValue()
     * @author Anthony C. Liberto
     */
    public Object getCellEditorValue() {
        return m_newBlob;
    }

    /**
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     * @author Anthony C. Liberto
     */
    public Component getTableCellEditorComponent(JTable t, Object v, boolean sel, int r, int c) {
        return this;
    }

    /**
     * @see javax.swing.CellEditor#isCellEditable(java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean isCellEditable(EventObject e) {
        return true;
    }

    /**
     * @see javax.swing.CellEditor#removeCellEditorListener(javax.swing.event.CellEditorListener)
     * @author Anthony C. Liberto
     */
    public void removeCellEditorListener(CellEditorListener c) {
        listenerList.remove(CellEditorListener.class, c);
    }

    /**
     * @see javax.swing.CellEditor#shouldSelectCell(java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean shouldSelectCell(EventObject e) {
        return true;
    }

    /**
     * @see javax.swing.CellEditor#stopCellEditing()
     * @author Anthony C. Liberto
     */
    public boolean stopCellEditing() {
        String[] parms = null;
        if (m_newBlob == null) {
            readyToListen = false; //17461
            editingCanceled();
        } else {
            if (m_blobLoc != null) {
                try {
                    m_newBlob.loadFromFile(m_blobLoc.getLocation());
                    m_blobLoc = null;
                } catch (IOException x) {
                    x.printStackTrace();
                    parms = new String[1];		        //24423680
                    parms[0] = m_blobLoc.getLocation();	//24423680
					eaccess().showError(this,"msg3015b",parms);		//24423680
					return false;						//24423680
                }
            }
            readyToListen = false; //17461
            editingStopped();
        }
        return true;
    }

    /**
     * editingStopped
     * @author Anthony C. Liberto
     */
    protected void editingStopped() {
        Object[] listen = listenerList.getListenerList();
        for (int i = listen.length - 2; i >= 0; i -= 2) {
            if (listen[i] == CellEditorListener.class) {
                if (changeEvent == null) {
                    changeEvent = new ChangeEvent(this);
                }
                ((CellEditorListener) listen[i + 1]).editingStopped(changeEvent);
            }
        }
    }

    /**
     * editingCanceled
     * @author Anthony C. Liberto
     */
    protected void editingCanceled() {
        Object[] listen = listenerList.getListenerList();
        for (int i = listen.length - 2; i >= 0; i -= 2) {
            if (listen[i] == CellEditorListener.class) {
                if (changeEvent == null) {
                    changeEvent = new ChangeEvent(this);
                }
                ((CellEditorListener) listen[i + 1]).editingCanceled(changeEvent);
            }
        }
    }

    /**
     * removeEditor
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean removeEditor() {
        return true;
    }

    /**
     * setDisplay
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setDisplay(String _s) {
    }

    /**
     * setText
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setText(String _s) {
        if (isForm) {
            lblExtension.setText(_s);
            lblExtension.setSize(lblExtension.getPreferredSize());
        }
    }

    /**
     * getText
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getText() {
        return lblExtension.getText();
    }

    /**
     * setEditable
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setEditable(boolean _b) {
        editable = _b;
    }

    /**
     * isEditable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * isReplaceable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isReplaceable() { //22632
        return false; //22632
    } //22632

    /**
     * getPanelType
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getPanelType() {
        return TYPE_BLOBEDITOR;
    }
    /*
    acl_20030506
    */
    /**
     * getEditForm
     *
     * @return
     * @author Anthony C. Liberto
     */
    public FormEditorInterface getEditForm() {
        return ef;
    }

    /**
     * canReceiveFocus
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canReceiveFocus() {
        FormEditorInterface tmpEf = getEditForm();
        if (tmpEf != null) {
            return tmpEf.verifyNewFocus(this);
        }
        return true;
    }

    /**
     * @see java.awt.Component#requestFocus()
     * @author Anthony C. Liberto
     */
    public void requestFocus() {
        if (canReceiveFocus()) {
            btn[0].requestFocus();
        }
    }

    /**
     * @see java.awt.Component#requestFocus(boolean)
     * @author Anthony C. Liberto
     */
    public boolean requestFocus(boolean _temp) {
        if (canReceiveFocus()) {
            return btn[0].requestFocus(_temp);
        }
        return false;
    }

    /**
     * @see javax.swing.JComponent#grabFocus()
     * @author Anthony C. Liberto
     */
    public void grabFocus() {
        if (canReceiveFocus()) {
            btn[0].grabFocus();
        }
    }

    /**
     * equals
     * @author Anthony C. Liberto
     * @return boolean
     * @param _c
     */
    public boolean equals(Component _c) {
        if (_c != null) {
            if (_c instanceof EditorInterface) {
                return (this == (EditorInterface) _c);
            }
        }
        return true;
    }

}
