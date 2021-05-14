/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: FormPanel.java,v $
 * Revision 1.2  2008/01/30 16:27:06  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:43:40  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:11  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:59  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:01  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/03 16:38:52  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.3  2005/02/01 22:06:31  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 19:15:15  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:42  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2003/11/18 20:54:03  tony
 * added formEditorInterface to simplify formEditor modification.
 *
 * Revision 1.3  2003/03/21 22:38:33  tony
 * form accessibilty update.
 *
 * Revision 1.2  2003/03/04 22:34:50  tony
 * *** empty log message ***
 *
 * Revision 1.1.1.1  2003/03/03 18:03:48  tony
 * This is the initial load of OPICM
 *
 * Revision 1.11  2002/11/07 16:58:22  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms;
import com.elogin.*;
import java.awt.*;
import java.awt.event.*;
import com.ibm.eannounce.eforms.editform.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class FormPanel extends EPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private HeaderPanel header = new HeaderPanel();
    private GridBagLayout layout = new GridBagLayout();
    private GridBagConstraints constraints = new GridBagConstraints();
    private FormEditorInterface ef = null;

    /**
     * formPanel
     *
     * @author Anthony C. Liberto
     * @param _ef
     */
    public FormPanel(FormEditorInterface _ef) {
        this();
        this.ef = _ef;
        setTransparent(true);
    }

    /**
     * formPanel
     * @author Anthony C. Liberto
     */
    public FormPanel() {
        super();
        setLayout(layout);
        init();
        header.addActionListener(this);
        setTransparent(true);
        return;
    }

    /**
     * actionPerformed
     *
     * @author Anthony C. Liberto
     * @parm ae
     * @param ae
     */
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        if (action.equals("collapse")) {
            setCollapsed(false);
        } else if (action.equals("expand")) {
            setCollapsed(true);
        }
        return;
    }

    private void init() {
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.fill = GridBagConstraints.NONE;
        layout.setConstraints(header, constraints);
        add(header);
        return;
    }

    /**
     * getGridBagConstraints
     * @return
     * @author Anthony C. Liberto
     */
    public GridBagConstraints getGridBagConstraints() {
        return constraints;
    }

    /**
     * getGridBagLayout
     * @return
     * @author Anthony C. Liberto
     */
    public GridBagLayout getGridBagLayout() {
        return layout;
    }

    /**
     * setTitleColor
     * @param _c
     * @author Anthony C. Liberto
     */
    public void setTitleColor(Color _c) {
        header.setTitleColor(_c);
        return;
    }

    /**
     * getTitleColor
     * @return
     * @author Anthony C. Liberto
     */
    public Color getTitleColor() {
        return header.getTitleColor();
    }

    /**
     * setTitle
     * @param s
     * @param i
     * @author Anthony C. Liberto
     */
    public void setTitle(String s, int i) {
        header.setTitle(s, i);
        return;
    }

    /**
     * getTitle
     * @return
     * @author Anthony C. Liberto
     */
    public String getTitle() {
        return header.getTitle();
    }

    /**
     * setNLS
     * @param _nls
     * @author Anthony C. Liberto
     */
    public void setNLS(int _nls) {
        header.setNLS(_nls);
    }

    /**
     * setCollapsible
     * @param b
     * @author Anthony C. Liberto
     */
    public void setCollapsible(boolean b) {
        header.setCollapsible(b);
        return;
    }

    /**
     * isCollapsible
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isCollapsible() {
        return header.isCollapsible();
    }

    /**
     * setCollapsed
     * @param b
     * @author Anthony C. Liberto
     */
    public void setCollapsed(boolean b) {
        setComponentsVisible(!b);
        header.setCollapsed(b);
        revalidate();
        ef.panelCollapsed(b);
        return;
    }

    /**
     * isCollapsed
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isCollapsed() {
        return header.isCollapsed();
    }


    /**
     * setComponentsVisible
     * @param b
     * @author Anthony C. Liberto
     */
    public void setComponentsVisible(boolean b) {
        int ii = getComponentCount();
        for (int i = 0; i < ii; ++i) {
            Component c = getComponent(i);
            if (!(c instanceof HeaderPanel)) {
                c.setVisible(b);
            }
        }
        return;
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() { //acl_Mem_20020131
        if (header != null) {
            header.removeActionListener(this); //acl_Mem_20020131
            header.dereference(); //acl_Mem_20020131
            header = null; //acl_Mem_20020131
        }
        layout = null; //acl_Mem_20020131
        constraints = null; //acl_Mem_20020131
        ef = null; //acl_Mem_20020131
    } //acl_Mem_20020131

    /**
     * getPanelType
     * @author Anthony C. Liberto
     * @return String
     */
    public String getPanelType() {
        return TYPE_FORMPANEL;
    }

    /**
     * @see javax.swing.JComponent#revalidate()
     * @author Anthony C. Liberto
     */
    public void revalidate() {
        super.revalidate();
        if (header != null) {
            header.revalidate();
        }
        return;
    }
}
