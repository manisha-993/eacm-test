/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/12/05
 * @author Anthony C. Liberto
 *
 * $Log: FindPanel.java,v $
 * Revision 1.2  2008/01/30 16:26:53  wendy
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
 * Revision 1.5  2005/09/08 17:59:00  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/03/03 21:46:40  tony
 * cr_FlagUpdate
 * Added Flag Modification Capability.
 *
 * Revision 1.3  2005/02/02 17:30:20  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:20  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:28  tony
 * This is the initial load of OPICM
 *
 * Revision 1.13  2003/11/13 23:16:16  tony
 * accessibility
 *
 * Revision 1.12  2003/10/29 00:21:16  tony
 * removed System.out. statements.
 *
 * Revision 1.11  2003/09/05 17:32:43  tony
 * 2003-09-05 memory enhancements
 *
 * Revision 1.10  2003/06/09 15:50:15  tony
 * 51246
 *
 * Revision 1.9  2003/05/28 16:27:40  tony
 * 50924
 *
 * Revision 1.8  2003/05/22 16:23:12  tony
 * 50874 -- filter, find, and sort adjust the object they
 * function on dynamically.
 *
 * Revision 1.7  2003/05/21 21:29:14  tony
 * 50587
 *
 * Revision 1.6  2003/05/12 23:06:23  tony
 * 50623
 *
 * Revision 1.5  2003/05/07 18:03:24  tony
 * 50560
 *
 * Revision 1.4  2003/04/21 20:03:12  tony
 * enhanced scroll logic to improve flag editor.
 *
 * Revision 1.3  2003/04/03 18:51:47  tony
 * adjusted logic to display individualized icon
 * for each frameDialog/tab.
 *
 * Revision 1.2  2003/03/13 18:38:43  tony
 * accessibility and column Order.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:43  tony
 * This is the initial load of OPICM
 *
 *
 */
package com.ibm.eannounce.dialogpanels;
import com.elogin.*;
//import com.ibm.eannounce.eForms.action.tree.*;
import com.ibm.eannounce.eforms.editform.*;
import com.ibm.eannounce.eforms.table.*;
import com.ibm.eannounce.eobjects.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class FindPanel extends AccessibleDialogPanel {
	private static final long serialVersionUID = 1L;
	//    private boolean displayOn = false;

//    private int ReturnStatus = 0;

    private Object searchObject = null; //22377

//    private int iCase = 0;
//    private int bMulti = 0;
//    private boolean dirUp = true;

    // buttons
    private EButton btnFind = new EButton(getString("fnd"));
    private EButton btnReplace = new EButton(getString("rpl"));
    private EButton btnReplaceAll = new EButton(getString("rplAll"));
    private EButton btnReplaceNext = new EButton(getString("rplNxt"));
    private EButton btnReset = new EButton(getString("rstc"));
    private EButton btnCancel = new EButton(getString("cncl"));

    //checkbox
    private ECheckBox chkCase = new ECheckBox(getString("case"));
    private ECheckBox chkCol = new ECheckBox(getString("mult"));

    //label
    private ELabel lblFind = new ELabel(getString("fnd-l"));
    private ELabel lblReplace = new ELabel(getString("rpl-l"));

    //Text fields
    private MemoryBox txtFind = new MemoryBox(10) {
    	private static final long serialVersionUID = 1L;
    	public void processEnter() {//50857
            btnFind.doClick(); //50857
        } //50857
    };
    private MemoryBox txtReplace = new MemoryBox(10) {
    	private static final long serialVersionUID = 1L;
    	public void processEnter() {//50857
            btnFind.doClick(); //50857
        } //50857
    };

    //Radio Buttons
    private ERadioButton rdoUp = new ERadioButton(getString("up"));
    private ERadioButton rdoDwn = new ERadioButton(getString("dwn"));

    private ButtonGroup group = new ButtonGroup();

    /**
     * findPanel
     * @author Anthony C. Liberto
     */
    public FindPanel() {
        super();
        init();
        return;
    }

    /**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
//        BorderLayout ba = new BorderLayout();
//        BorderLayout bb = new BorderLayout();
//        GridLayout gla = new GridLayout(6, 1, 5, 5);
        EPanel pDir = new EPanel(new GridLayout(1, 2, 5, 5));

        group.add(rdoUp);
        group.add(rdoDwn);

        setLayout(gbl);

        pDir.setBorder(new ETitledBorder("Direction"));

        //declare Layouts

        lblFind.setLabelFor(txtFind);
        lblReplace.setLabelFor(txtReplace);

        buildConstraints(c, 0, 0, 1, 1, 0, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        gbl.setConstraints(lblFind, c);
        add(lblFind);
        buildConstraints(c, 1, 0, 1, 1, 0, 0);
        gbl.setConstraints(txtFind, c);
        add(txtFind);
        buildConstraints(c, 2, 0, 1, 1, 0, 0);
        gbl.setConstraints(btnFind, c);
        add(btnFind);

        buildConstraints(c, 0, 1, 1, 1, 0, 0);
        gbl.setConstraints(lblReplace, c);
        add(lblReplace);
        buildConstraints(c, 1, 1, 1, 1, 0, 0);
        gbl.setConstraints(txtReplace, c);
        add(txtReplace);
        buildConstraints(c, 2, 1, 1, 1, 0, 0);
        gbl.setConstraints(btnReplace, c);
        add(btnReplace);

        buildConstraints(c, 0, 2, 1, 1, 0, 0);
        gbl.setConstraints(chkCase, c);
        add(chkCase);
        buildConstraints(c, 2, 2, 1, 1, 0, 0);
        gbl.setConstraints(btnReplaceNext, c);
        add(btnReplaceNext);

        buildConstraints(c, 0, 3, 1, 1, 0, 0);
        gbl.setConstraints(chkCol, c);
        add(chkCol);
        buildConstraints(c, 2, 3, 1, 1, 0, 0);
        gbl.setConstraints(btnReplaceAll, c);
        add(btnReplaceAll);

        buildConstraints(c, 2, 4, 1, 1, 0, 0);
        gbl.setConstraints(btnReset, c);
        add(btnReset);

        buildConstraints(c, 1, 5, 1, 1, 0, 0);
        gbl.setConstraints(pDir, c);
        add(pDir);
        pDir.add(rdoUp);
        pDir.add(rdoDwn);
        buildConstraints(c, 2, 5, 1, 1, 0, 0);
        gbl.setConstraints(btnCancel, c);
        add(btnCancel);

        btnCancel.addActionListener(this);
        btnCancel.setActionCommand("exit");
        btnFind.addActionListener(this);
        btnFind.setActionCommand("find");
        btnReplace.addActionListener(this);
        btnReplace.setActionCommand("replace");
        btnReplaceNext.addActionListener(this); //2.2spe
        btnReplaceNext.setActionCommand("replaceNext");
        btnReplaceAll.addActionListener(this); //2.2spe
        btnReplaceAll.setActionCommand("replaceAll");
        btnReset.addActionListener(this);
        btnReset.setActionCommand("reset");
        setMnemonicsAndTips();
        setSelectors();
        return;
    }

    private void buildConstraints(GridBagConstraints gbc, int gx, int gy, int gw, int gh, int wx, int wy) {
        gbc.gridx = gx;
        gbc.gridy = gy;
        gbc.gridwidth = gw;
        gbc.gridheight = gh;
        gbc.weightx = wx;
        gbc.weighty = wy;
        return;
    }

    /**
     * setMnemonicsAndTips
     * @author Anthony C. Liberto
     */
    public void setMnemonicsAndTips() {
        btnFind.setToolTipText(getString("fnd-t"));
        btnReplace.setToolTipText(getString("rpl-t"));
        btnReplaceAll.setToolTipText(getString("rplAll-t"));
        btnReplaceNext.setToolTipText(getString("rplNxt-t"));
        btnReset.setToolTipText(getString("rstc-t"));
        btnCancel.setToolTipText(getString("cncl-t"));
        chkCase.setToolTipText(getString("case-t"));
        chkCol.setToolTipText(getString("mult-t"));
        rdoUp.setToolTipText(getString("up-t"));
        rdoDwn.setToolTipText(getString("dwn-t"));

        btnFind.setMnemonic(getChar("fnd-s"));
        btnReplace.setMnemonic(getChar("rpl-s"));
        btnReplaceAll.setMnemonic(getChar("rplAll-s"));
        btnReplaceNext.setMnemonic(getChar("rplNxt-s"));
        btnReset.setMnemonic(getChar("rstc-s"));
        btnCancel.setMnemonic(getChar("cncl-s"));
        chkCase.setMnemonic(getChar("case-s"));
        chkCol.setMnemonic(getChar("mult-s"));
        rdoUp.setMnemonic(getChar("up-s"));
        rdoDwn.setMnemonic(getChar("dwn-s"));
        accessibleize();
        return;
    }

    /**
     * setSelectors
     * @author Anthony C. Liberto
     */
    public void setSelectors() {
        chkCase.setSelected(true);
        chkCol.setSelected(true);
        rdoDwn.setSelected(true);
        return;
    }

    /**
     * isCase
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isCase() {
        return chkCase.isSelected();
    }

    /**
     * isMulti
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isMulti() {
        return chkCol.isSelected();
    }

    /**
     * isUp
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isUp() {
        return rdoUp.isSelected();
    }

    /**
     * getIncrement
     * @return
     * @author Anthony C. Liberto
     */
    public int getIncrement() {
        if (isUp()) {
            return -1;
        }
        return 1;
    }

    /**
     * setReplaceable
     * @param b
     * @author Anthony C. Liberto
     */
    public void setReplaceable(boolean b) {
        btnReplace.setEnabled(b);
        btnReplaceAll.setEnabled(b);
        btnReplaceNext.setEnabled(b);
        txtReplace.setEnabled(b);
        return;
    }

    /**
     * setObject
     * @author Anthony C. Liberto
     * @param _o
     */
    public void setObject(Object _o) { //22377
        if (_o == null) { //22377
            resetFind(); //22377
        } else if (_o instanceof ETable) { //22377
            setTable((ETable) _o); //22377
        } else if (_o instanceof EditForm) { //22377
            setForm((EditForm) _o); //22377
            //		} else if (_o instanceof ActionTree) {		//22377
            //			setTree((ActionTree)_o);				//22377
        } else { //22377
            resetFind(); //22377
        } //22377
        return; //22377
    } //22377

    private void resetFind() { //22377
        searchObject = null; //22377
        setReplaceable(false); //22377
        btnReset.setEnabled(false); //22377
        chkCol.setEnabled(false); //22377
        return; //22377
    } //22377

    private void setTable(ETable _table) { //22377
        searchObject = _table; //22377
        setReplaceable(_table.isReplaceable());
        btnReset.setEnabled(_table.hasFound()); //20020301
        chkCol.setEnabled(_table.isMultiColumn());
        return;
    }

    private void setForm(EditForm _form) { //22377
        setReplaceable(_form.isReplaceable());
        btnReset.setEnabled(_form.hasFound());
        chkCol.setEnabled(false);
        searchObject = _form; //22377
        return;
    }
    /*
    	public void setTree(ActionTree _tree) {
    		setReplaceable(false);
    		btnReset.setEnabled(_tree.hasFound());
    		chkCol.setEnabled(false);
    		searchObject = _tree;						//22377
    		return;
    	}
    */
    /**
     * getTable
     * @return
     * @author Anthony C. Liberto
     */
    public ETable getTable() {
        if (searchObject != null && searchObject instanceof ETable) { //22377
            return (ETable) searchObject;
        } //22377
        return null; //22377
    }

    /**
     * findValue
     * @param find
     * @author Anthony C. Liberto
     */
    public void findValue(String find) {
        if (searchObject != null) { //22377
            if (searchObject instanceof ETable) {
                ((ETable) searchObject).findValue(find, isMulti(), isCase(), getIncrement(), false);
            } else if (searchObject instanceof EditForm) {
                ((EditForm) searchObject).findValue(find, isCase(), getIncrement());
            }
        }
        return;
    }

    /**
     * replaceValue
     * @param find
     * @param replace
     * @author Anthony C. Liberto
     */
    public void replaceValue(String find, String replace) {
        if (searchObject != null) { //22377
            if (searchObject instanceof ETable) {
                ((ETable) searchObject).replaceValue(find, replace, isMulti(), isCase(), getIncrement());
            } else if (searchObject instanceof EditForm) {
                ((EditForm) searchObject).replaceValue(find, replace, isCase(), getIncrement());
                //			} else if (searchObject instanceof ActionTree) {
                //				showFYI("actionTree.replaceValue not currently supported");
            }
        } //22377
        return;
    }

    /**
     * replaceNextValue
     * @param find
     * @param replace
     * @author Anthony C. Liberto
     */
    public void replaceNextValue(String find, String replace) {
        if (searchObject != null) { //22377
            if (searchObject instanceof ETable) {
                ((ETable) searchObject).replaceNextValue(find, replace, isMulti(), isCase(), getIncrement());
            } else if (searchObject instanceof EditForm) {
                ((EditForm) searchObject).replaceNextValue(find, replace, isCase(), getIncrement());
                //			} else if (searchObject instanceof ActionTree) {
                //				showFYI("actionTree.replaceNextValue not currently supported");
            }
        } //22377
        return;
    }

    /**
     * replaceAllValue
     * @param find
     * @param replace
     * @author Anthony C. Liberto
     */
    public void replaceAllValue(String find, String replace) {
        resetTouch(); //23354
        if (searchObject != null) { //22377
            if (searchObject instanceof ETable) {
                ((ETable) searchObject).replaceAllValue(find, replace, isMulti(), isCase(), getIncrement());
            } else if (searchObject instanceof EditForm) {
                ((EditForm) searchObject).replaceAllValue(find, replace, isCase(), getIncrement());
                //			} else if (searchObject instanceof ActionTree) {
                //				showFYI("actionTree.replaceAllValue not currently supported");
            }
        } //22377
        return;
    }

    /**
     * resetFindColor
     * @author Anthony C. Liberto
     */
    public void resetFindColor() {
        if (searchObject != null) { //22377
            if (searchObject instanceof ETable) {
                ((ETable) searchObject).resetFound();
            } else if (searchObject instanceof EditForm) {
                ((EditForm) searchObject).resetFound();
                //			} else if (searchObject instanceof ActionTree) {
                //				((ActionTree)searchObject).resetFound();
            }
        } //22377
        return;
    }

    /**
     * actionPerformed
     * @author Anthony C. Liberto
     * @param _action
     */
    public void actionPerformed(String _action) {
        String find = null;
        appendLog("findPanel.actionPerformed(" + _action + ")");
        if (_action.equals("exit")) {
            disposeDialog();
        } else if (_action.equals("guest")) {
            eaccess().getLogin().guest();
        } else if (_action.equals("reset")) {
            resetFindColor();
            btnReset.setEnabled(false); //20020301
            requestFocus(); //22451
        } else {
            if (isBusy()) {
                return;
            }
            setBusy(true);
            find = txtFind.getText();
            if (_action.equals("find")) {
                if (Routines.have(find)) {
                    findValue(find);
                    btnReset.setEnabled(hasFound()); //20020301
                    btnReset.repaint(); //22377
                    txtFind.commit();
                    requestFocus(); //22451
                    btnFind.requestFocus(); //22451
                }
            } else if (_action.equals("replace")) {
                if (Routines.have(find)) {
                    replaceValue(find, txtReplace.getText());
                    txtFind.commit();
                    txtReplace.commit();
                    requestFocus(); //22451
                    btnReplace.grabFocus(); //22451
                }
            } else if (_action.equals("replaceNext")) {
                if (Routines.have(find)) {
                    replaceNextValue(find, txtReplace.getText());
                    txtFind.commit();
                    txtReplace.commit();
                    requestFocus(); //22451
                    btnReplaceNext.grabFocus(); //22451
                }
            } else if (_action.equals("replaceAll")) {
                if (Routines.have(find)) {
                    replaceAllValue(find, txtReplace.getText());
                    txtFind.commit();
                    txtReplace.commit();
                    requestFocus(); //22451
                    btnReplaceAll.grabFocus(); //22451
                    btnReset.setEnabled(hasFound()); //23419
                }
            }
            setBusy(false);
        }
        return;
    }

    private boolean hasFound() {
        if (searchObject != null) { //22377
            if (searchObject instanceof ETable) {
                return ((ETable) searchObject).hasFound();
            } else if (searchObject instanceof EditForm) {
                return ((EditForm) searchObject).hasFound();
                //			} else if (searchObject instanceof ActionTree) {
                //				return ((ActionTree)searchObject).hasFound();
            }
        } //22377
        return false;
    }

    /**
     * disposeDialog
     * @author Anthony C. Liberto
     */
    public void disposeDialog() {
        txtFind.setText(""); //013546
        txtReplace.setText(""); //013546
        searchObject = null;
        super.disposeDialog();
        return;
    }

    private void resetTouch() { //21825
        if (searchObject != null) { //22377
            if (searchObject instanceof ETable) {
                ((ETable) searchObject).resetTouch();
            } else if (searchObject instanceof EditForm) {
                ((EditForm) searchObject).resetTouch();
                //			} else if (searchObject instanceof ActionTree) {
                //				((ActionTree)searchObject).resetTouch();
            }
        } //22377
        return; //21825
    } //21825

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
        return getString("find.panel");
    }

    /**
     * windowClosing
     * @param e
     * @author Anthony C. Liberto
     */
    public void windowClosing(WindowEvent e) {
        disposeDialog();
        return;
    }

    //	public void windowOpened(WindowEvent e) {}
    //	public void windowDeactivated(WindowEvent e) {}
    //	public void windowClosed(WindowEvent e) {}
    //	public void windowIconified(WindowEvent e) {}
    //	public void windowActivated(WindowEvent e) {}
    //	public void windowDeiconified(WindowEvent e) {}

    /**
     * getIconName
     * @author Anthony C. Liberto
     * @return String
     */
    public String getIconName() {
        return "find.gif";
    }

    /**
     * showMe
     * @author Anthony C. Liberto
     */
    public void showMe() {
        txtFind.requestFocus(); //50623
        return; //50623
    }
    /*
     50874
    */
    /**
     * setObject
     * @author Anthony C. Liberto
     */
    public void setObject() {
        Object o = null; //50924
        if (id != null) { //50924
            o = id.getSearchObject(); //50924
        } else { //50924
            o = eaccess().getSearchObject();
        } //50924
        setObject(o);
        return;
    }

    /**
     * activateMe
     * @author Anthony C. Liberto
     */
    public void activateMe() {
        setObject();
        return;
    }

    /*
     51246
     */
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
        return;
    }

    /**
     * removeMenu
     * @author Anthony C. Liberto
     */
    protected void removeMenu() {
        return;
    }

    /*
     accessibility
     */
    private void accessibleize() {
        btnFind.getAccessibleContext().setAccessibleDescription(getString("fnd"));
        btnReplace.getAccessibleContext().setAccessibleDescription(getString("rpl"));
        btnReplaceAll.getAccessibleContext().setAccessibleDescription(getString("rplAll"));
        btnReplaceNext.getAccessibleContext().setAccessibleDescription(getString("rplNxt"));
        btnReset.getAccessibleContext().setAccessibleDescription(getString("rstc"));
        btnCancel.getAccessibleContext().setAccessibleDescription(getString("cncl"));
        chkCase.getAccessibleContext().setAccessibleDescription(getString("case-t"));
        chkCol.getAccessibleContext().setAccessibleDescription(getString("mult-t"));
        rdoUp.getAccessibleContext().setAccessibleDescription(getString("up-t"));
        rdoDwn.getAccessibleContext().setAccessibleDescription(getString("dwn-t"));
        //		getAccessibleContext().setAccessibleDescription(getTitle());
        return;
    }
}
