/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EForm.java,v $
 * Revision 1.4  2009/09/01 17:25:12  wendy
 * removed useless code and cleanup
 *
 * Revision 1.3  2008/02/13 19:29:22  wendy
 * shorten debug output
 *
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
 * Revision 1.7  2005/09/08 17:59:01  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.6  2005/02/04 15:22:08  tony
 * JTest Format Third Pass
 *
 * Revision 1.5  2005/02/01 22:06:31  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/27 19:15:14  tony
 * JTest Format
 *
 * Revision 1.3  2004/08/04 17:49:14  tony
 * adjusted logic to break function parameterization into
 * arm files and a new function directory.  This way
 * we will eliminate the possibility of translation to
 * accidentally change functionality.
 *
 * Revision 1.2  2004/03/26 23:27:22  tony
 *  cr_209046022 -- first pass
 *
 * Revision 1.1.1.1  2004/02/10 16:59:42  tony
 * This is the initial load of OPICM
 *
 * Revision 1.12  2003/10/29 00:21:17  tony
 * removed System.out. statements.
 *
 * Revision 1.11  2003/07/02 22:59:42  tony
 * removed print statement
 *
 * Revision 1.10  2003/06/16 17:22:54  tony
 * updated logic for 1.2h to allow for the application to
 * automatically select the visible navigate as current when
 * the navigate splitpane is adjusted to its minimum or
 * its maximum.
 *
 * Revision 1.9  2003/05/29 21:20:44  tony
 * adjusted setParm logic for single parm string.
 *
 * Revision 1.8  2003/05/29 19:05:19  tony
 * updated report launching.
 *
 * Revision 1.7  2003/05/27 21:21:06  tony
 * updated url launching
 *
 * Revision 1.6  2003/04/21 17:30:18  tony
 * updated Color Logic by adding edit and found color
 * preferences to appearance.
 *
 * Revision 1.5  2003/04/11 20:02:28  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eforms;
import com.elogin.*;
import com.ibm.eannounce.eforms.editform.*;
import com.ibm.eannounce.eforms.toolbar.*;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.epanels.*;

import java.awt.*;
//import java.awt.event.*;
import java.beans.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
//import javax.swing.border.*;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EForm extends EScrollPane implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;
	private boolean bValidate = isArmed(FORM_VALIDATE_ARM_FILE);
    //private boolean ErrorFound = false;
    private String XML = null;
    private Object seed = null;

    private String nextForm = null; //next
    private String prevForm = null; //next
    private String name = null; //next

    private EPanel mainPane = new EPanel();
    private FormPanel curPanel = null;
    private TablePanel tablePane = null;
    private GridBagLayout gl = new GridBagLayout();

    private Stack fontStack = new Stack(); //stack
    private Stack backgroundStack = new Stack(); //stack
    private Stack foregroundStack = new Stack(); //stack

    private FormHash groups = new FormHash(); //group
    private FormHash group = null; //group
    private FormHash hashEnabler = new FormHash(); //enablers
    private HashMap hash = new HashMap();
    private Stack nlsStack = new Stack();

    //private Window owner = null;
    private ENavForm parent = null;

    private EMenubar menuBar = new EMenubar();
    private EannounceToolbar tBar = null;
    private EPopupMenu popup = new EPopupMenu("popup");

    //private Component defFocus = null;
    private String fileName = null;

    /**
     * eForm
     * @param _parent
     * @param _seed
     * @param _name
     * @author Anthony C. Liberto
     */
    public EForm(ENavForm _parent, Object _seed, String _name) {
        parent = _parent;
        //cr_209046022		tBar = toolbarController.generateToolbar(defaultToolbarLayout.NAV_BAR,parent,null);
        seed = _seed;
        XML = getXML(_name);
        fileName = _name;
        setName(_name);
        //ErrorFound = false;
        mainPane.setTransparent(true);
        mainPane.setLayout(gl);
        setViewportView(mainPane);
        getAccessibleContext().setAccessibleDescription(getString("accessible.formTable"));
    }

    /**
     * setDefaultFocus
     * @param _fi
     * @author Anthony C. Liberto
     * /
    public void setDefaultFocus(FormItem _fi) { //form_enable
        JComponent jc = getComponent(_fi); //form_enable
        if (jc != null) { //form_enable
            if (jc instanceof JScrollPane) { //form_enable
                Component c = ((JScrollPane) jc).getViewport().getView(); //form_enable
                if (c != null) { //form_enable
                    defFocus = c;
                } //form_enable
            } else { //form_enable
                defFocus = jc; //form_enable
            } //form_enable
        } //form_enable
    } //form_enable
    */

    /**
     * getDefaultFocus
     * @return
     * @author Anthony C. Liberto
     * /
    public Component getDefaultFocus() { //form_enable
        return defFocus; //form_enable
    } //form_enable
    */

    /**
     * getSeed
     * @return
     * @author Anthony C. Liberto
     */
    public Object getSeed() { //form_enable
        return seed; //form_enable
    } //form_enable

    /**
     * setSeed
     * @param _o
     * @author Anthony C. Liberto
     */
    public void setSeed(Object _o) { //form_enable
        seed = _o; //form_enable
    } //form_enable

    /**
     * @see java.awt.Component#setName(java.lang.String)
     * @author Anthony C. Liberto
     */
    public void setName(String _s) { //next
        name = _s; //next
    } //next

    /**
     * @see java.awt.Component#getName()
     * @author Anthony C. Liberto
     */
    public String getName() { //next
        return name; //next
    } //next

    /**
     * setPrevForm
     * @param _s
     * @author Anthony C. Liberto
     * /
    public void setPrevForm(String _s) { //next
        if (_s != null) {
            prevForm = new String(_s);
        } //next
    } //next
    */

    /**
     * hasPrevForm
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean hasPrevForm() { //next
        if (prevForm == null) { //next
            return false; //next
        } //next
        return true; //next
    } //next
    */

    /**
     * getPrevForm
     * @return
     * @author Anthony C. Liberto
     */
    public String getPrevForm() { //next
        return prevForm; //next
    } //next

    /**
     * loadNextForm
     * @author Anthony C. Liberto
     * /
    public void loadNextForm() { //next
        if (parent != null) {
            parent.loadNextForm();
        } //next
    } //next
    */

    /**
     * loadPrevForm
     * @author Anthony C. Liberto
     * /
    public void loadPrevForm() { //next
        if (parent != null) {
            parent.loadPrevForm();
        } //next
    } //next
    */

    /**
     * getMenu
     * @return
     * @author Anthony C. Liberto
     */
    public EMenubar getMenu() {
        return menuBar;
    }

    /**
     * getEToolbar
     * @return
     * @author Anthony C. Liberto
     */
    public EannounceToolbar getEToolbar() {
        return tBar;
    }

    /**
     * getPopupMenu
     * @return
     * @author Anthony C. Liberto
     */
    public EPopupMenu getPopupMenu() {
        return popup;
    }

    /**
     * setMenuEnabled
     * @param _menu
     * @param _enabled
     * @author Anthony C. Liberto
     * /
    public void setMenuEnabled(String _menu, boolean _enabled) {
        menuBar.setEnabled(_menu, _enabled);
    }*/

    /**
     * setMenuVisible
     * @param _menu
     * @param _enabled
     * @author Anthony C. Liberto
     * /
    public void setMenuVisible(String _menu, boolean _enabled) {
        menuBar.setVisible(_menu, _enabled);
    }*/

    /**
     * addMenu
     * @param _menu
     * @author Anthony C. Liberto
     * /
    public void addMenu(JMenu _menu) {
        menuBar.add(_menu);
    }*/

    /**
     * showLayout
     * @author Anthony C. Liberto
     * /
    public void showLayout() {
        FileOutputStream fout = null;
        ObjectOutputStream outStream = null;
        try {
            File f = new File(System.getProperty("opicm.temp") + fileName);
            if (f.exists()) {
                f.delete();
            }
            fout = new FileOutputStream(f);
            outStream = new ObjectOutputStream(fout);
            outStream.writeChars(XML);
            outStream.flush();
            f.deleteOnExit();
            launchURL(f.getAbsolutePath());
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
    }*/

    /**
     * addMenu
     * @param _menu
     * @param _s
     * @param _al
     * @param _key
     * @param _mod
     * @param _enabled
     * @author Anthony C. Liberto
     * /
    public void addMenu(String _menu, String _s, ActionListener _al, int _key, int _mod, boolean _enabled) {
        menuBar.addMenu(_menu, _s, _al, _key, _mod, _enabled);
    }*/

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
    }*/

    /**
     * panelRevalidate
     * @author Anthony C. Liberto
     * /
    public void panelRevalidate() {
        mainPane.revalidate();
    }*/

    /**
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     * @author Anthony C. Liberto
     */
    public void propertyChange(PropertyChangeEvent pce) {
    }

    /**
     * panelRemoveAll
     * @author Anthony C. Liberto
     * /
    public void panelRemoveAll() {
        mainPane.removeAll();
        gc();
    }*/

    /*
     * cut, copy, paste
     */
    /**
     * copy
     * @author Anthony C. Liberto
     * /
    public void copy() {
    }*/
    /**
     * cut
     * @author Anthony C. Liberto
     * /
    public void cut() {
    }*/
    /**
     * paste
     * @author Anthony C. Liberto
     * /
    public void paste() {
    }*/

    /*
     * puts
     */
    private void put(FormVariable fv) {
        String key = fv.getName();
        if (!hash.containsKey(key)) {
        	hash.put(key, fv);
        }     
    }

    private void put(FormMethod fm) {
        String key = fm.getName();
        if (!hash.containsKey(key)) {
        	 hash.put(key, fm);
        }
    }

    private void put(FormItem fi) {
        String key = fi.getName();
        JComponent comp = null;
        if (hash.containsKey(key)) {
            return;
        }
        comp = getComponent(fi); //stack
        if (comp != null) { //stack
            comp.setBackground(getFormBackground()); //stack
            comp.setForeground(getFormForeground()); //stack
            comp.setFont(getFormFont()); //stack
        } //stack

        hash.put(key, fi);
    }

    /*
     * gets
     */
    private FormMethod getFormMethod(String _s) {
        Object o = null;
        if (!hash.containsKey(_s)) {
            return null;
        }
        o = hash.get(_s);
        if (o instanceof FormMethod) {
            return (FormMethod) o;
        }
        return null;
    }

    /**
     * getFormItem
     *
     * @author Anthony C. Liberto
     * @param _s
     * @return
     */
    protected FormItem getFormItem(String _s) {
        Object o = null;
        if (!hash.containsKey(_s)) {
            return null;
        }
        o = hash.get(_s);
        if (o instanceof FormItem) {
            return (FormItem) o;
        }
        return null;
    }

    /**
     * getFormVariable
     *
     * @author Anthony C. Liberto
     * @param _s
     * @return
     */
    protected FormVariable getFormVariable(String _s) {
        Object o = null;
        if (!hash.containsKey(_s)) {
            return null;
        }
        o = hash.get(_s);
        if (o instanceof FormVariable) {
            return (FormVariable) o;
        }
        return null;
    }

    private Object getVariableByKey(String s) {
        if (hash.containsKey(s)) {
            Object out = hash.get(s);
            if (out instanceof FormVariable) {
                return ((FormVariable) out).getObject();
                //			} else if (out instanceof formItem) {
                //				return ((formItem)out).getObject();
            }
            return out;
        }
        return s;
    }

    private Object getVariable(String s) {
        if (s.equalsIgnoreCase("MAINPANEL")) {
            return mainPane;

        } else if (s.equalsIgnoreCase("THIS")) {
            return this;

        } else if (s.equalsIgnoreCase("SEED")) {
            return seed;

        } else if (s.equalsIgnoreCase("PARENT")) {
            return parent;

        } else if (s.equalsIgnoreCase("NAVIGATE0")) {
            return parent.getNavigate(0);

        } else if (s.equalsIgnoreCase("NAVIGATE1")) {
            return parent.getNavigate(1);

        } else if (s.equalsIgnoreCase("NULL")) {
            return null;

        } else if (s.equalsIgnoreCase("EACCESS")) {
            return eaccess();

        } else if (s.equalsIgnoreCase("ACTIVE_PROFILE")) {
            return getActiveProfile();

        } else if (s.startsWith(":")) {
            return s.substring(1);

        } else {
            return getVariableByKey(s);
        }
    }

    /**
     * getObject
     *
     * @author Anthony C. Liberto
     * @param s
     * @return
     */
    protected Object getObject(String s) {
        if (s.equalsIgnoreCase("THIS")) {
            return mainPane;

        } else if (s.equalsIgnoreCase("SEED")) {
            return seed;

        } else if (s.equalsIgnoreCase("PARENT")) {
            return parent;

        } else if (s.equalsIgnoreCase("NAVIGATE0")) {
            return parent.getNavigate(0);

        } else if (s.equalsIgnoreCase("NAVIGATE1")) {
            return parent.getNavigate(1);

        } else if (s.equalsIgnoreCase("NULL")) {
            return null;

        } else if (s.equalsIgnoreCase("EACCESS")) {
            return eaccess();

        } else if (s.equalsIgnoreCase("ACTIVE_PROFILE")) {
            return getActiveProfile();
        }
        return s;
    }

    private int getNLS() {
        Object o = null;
        if (nlsStack.empty()) {
            return 1;
        }
        o = nlsStack.peek();
        if (o instanceof Integer) {
            return ((Integer) o).intValue();
        }
        return 1;
    }

    private Object getObject(FormItem fi) {
        return fi.getObject();
    }

    private JComponent getComponent(FormItem fi) {
        Object o = getObject(fi);
        if (o instanceof JComponent) {
            return (JComponent) o;
        }
        return null;
    }

    private Object[] getObjectArray(String _s) {
        StringTokenizer st = new StringTokenizer(_s, ",");
        Vector v = new Vector();
        int ii = -1;
        Object[] out = null;
        while (st.hasMoreTokens()) {
            String s = st.nextToken().trim();
            if (Routines.have(s)) {
                v.add(getVariable(s));
            }
        }
        ii = v.size();
        out = new Object[ii];
        for (int i = 0; i < ii; ++i) {
            out[i] = v.get(i);
        }
        return out;
    }

    private String[] getStringArray(String _s) {
        return Routines.getStringArray(_s, ",");
    }

    private int[] getIntArray(String _s) {
        return Routines.getIntArray(_s, ",");
    }

    private int getInt(String _s) {
        return Routines.getInt(_s);
    }

    private boolean getBoolean(String _s) {
        return Routines.getBoolean(_s);
    }

    private boolean getBooleanFromMethod(String _s) {
        FormMethod fm = getFormMethod(_s);
        Object o = null;
        if (fm == null) {
            return false;
        }
        o = fm.invoke();
        if (o instanceof Boolean) {
            return ((Boolean) o).booleanValue();
        }
        return false;
    }

    private Dimension getDimension(String s) {
        int[] i = getIntArray(s);
        if (i.length == 2) {
            return new Dimension(i[0], i[1]);
        }
        return null;
    }

    private Integer getInteger(String _s, int _def) {
        return getInteger(_s, _def);
    }

    private int getAnchorConstant(String _s) {
        if (_s.equalsIgnoreCase("CENTER")) {
            return GridBagConstraints.CENTER;

        } else if (_s.equalsIgnoreCase("NORTH")) {
            return GridBagConstraints.NORTH;

        } else if (_s.equalsIgnoreCase("NORTHEAST")) {
            return GridBagConstraints.NORTHEAST;

        } else if (_s.equalsIgnoreCase("EAST")) {
            return GridBagConstraints.EAST;

        } else if (_s.equalsIgnoreCase("SOUTHEAST")) {
            return GridBagConstraints.SOUTHEAST;

        } else if (_s.equalsIgnoreCase("SOUTH")) {
            return GridBagConstraints.SOUTH;

        } else if (_s.equalsIgnoreCase("SOUTHWEST")) {
            return GridBagConstraints.SOUTHWEST;

        } else if (_s.equalsIgnoreCase("WEST")) {
            return GridBagConstraints.WEST;

        } else if (_s.equalsIgnoreCase("NORTHWEST")) {
            return GridBagConstraints.NORTHWEST;

        } else {
            return GridBagConstraints.CENTER;
        }
    }

    private int getFillConstant(String _s) {
        if (_s.equalsIgnoreCase("BOTH")) {
            return GridBagConstraints.BOTH;

        } else if (_s.equalsIgnoreCase("VERTICAL")) {
            return GridBagConstraints.VERTICAL;

        } else if (_s.equalsIgnoreCase("HORIZONTAL")) {
            return GridBagConstraints.HORIZONTAL;

        } else if (_s.equalsIgnoreCase("NONE")) {
            return GridBagConstraints.NONE;

        } else {
            return GridBagConstraints.NONE;
        }
    }

    private Color getColor(String _s) {
        return Color.decode(_s);
    }
    /**
     * generic section
     */
    private void populateGridBag(GridBagLayout _l, JPanel _p, Vector _v) {
        int ii = _v.size();
        for (int i = 0; i < ii; ++i) {
            Object o = _v.get(i);
            if (o instanceof FormItem) {
                FormItem fi = (FormItem) o;
                JComponent comp = getComponent(fi);
                GridBagConstraints gbc = fi.getConstraints();
                _l.setConstraints(comp, gbc);
                _p.add(comp);
            }
        }
        packPanel(_p);
    }

    private void populateGrid(GridLayout _l, JPanel _p, Vector _v) {
        int ii = _v.size();
        for (int i = 0; i < ii; ++i) {
            Object o = _v.get(i);
            if (o instanceof FormItem) {
                FormItem fi = (FormItem) o;
                JComponent comp = getComponent(fi);
                _p.add(comp);
            }
        }
        packPanel(_p);
    }

    private void populateBorder(BorderLayout _l, JPanel _p, Vector _v) {
        int ii = _v.size();
        for (int i = 0; i < ii; ++i) {
            Object o = _v.get(i);
            if (o instanceof FormItem) {
                FormItem fi = (FormItem) o;
                JComponent comp = getComponent(fi);
                String loc = (String) fi.getMisc("borderlocation");
                if (loc != null) {
                    _p.add(loc, comp);
                } else {
                    appendLog("missing border location");
                }
            }
        }
        packPanel(_p);
    }

    private void packPanel(JPanel _pnl) {
        Dimension d = _pnl.getPreferredSize();
        _pnl.setSize(d);
    }

    private void populatePanel(LayoutManager _l, JPanel _p, FormItem _fi) {
        _p.setBackground(getFormBackground()); //stack
        _p.setForeground(getFormForeground()); //stack
        _p.setFont(getFormFont()); //stack
        if (_l instanceof BorderLayout) {
            populateBorder((BorderLayout) _l, _p, _fi.getContents());

        } else if (_l instanceof GridLayout) {
            populateGrid((GridLayout) _l, _p, _fi.getContents());

        } else if (_l instanceof GridBagLayout) {
            populateGridBag((GridBagLayout) _l, _p, _fi.getContents());
        }
    }

    private void populatePanel(FormItem _fi, JComponent _comp) {
        if (_comp instanceof FormPanel) {
            FormPanel pnl = (FormPanel) _comp;
            GridBagLayout layout = pnl.getGridBagLayout();
            populatePanel(layout, pnl, _fi);
            pnl.setNLS(getNLS());
        } else if (_comp instanceof EImagePanel) {
            EImagePanel pnl = (EImagePanel) _comp;
            LayoutManager layout = pnl.getLayout();
            populatePanel(layout, pnl, _fi);
        } else if (_comp instanceof EPanel) {
            EPanel pnl = (EPanel) _comp;
            LayoutManager layout = pnl.getLayout();
            populatePanel(layout, pnl, _fi);
        }
    }

    private void populateMainGridBag(GridBagLayout _l, JPanel _p, FormItem _fi) {
        GridBagConstraints gbc = _fi.getConstraints();
        _l.setConstraints(_p, gbc);
        mainPane.add(_p);
    }

    private void populateMainGrid(GridLayout _l, JPanel _p, FormItem _fi) {
        mainPane.add(_p);
    }

    private void populateMainBorder(BorderLayout _l, JPanel _p, FormItem _fi) {
        String loc = (String) _fi.getMisc("borderlocation");
        if (loc != null) {
            mainPane.add(loc, _p);
        } else {
            appendLog("missing border location");
        }
    }

    private void populateMainPanel(LayoutManager _l, JPanel _p, FormItem _fi) {
        if (_l instanceof BorderLayout) {
            populateMainBorder((BorderLayout) _l, _p, _fi);

        } else if (_l instanceof GridLayout) {
            populateMainGrid((GridLayout) _l, _p, _fi);

        } else if (_l instanceof GridBagLayout) {
            populateMainGridBag((GridBagLayout) _l, _p, _fi);
        }
    }

    private void populateMainPanel(LayoutManager _layout, FormItem _fi) {
        JComponent comp = getComponent(_fi);
        if (comp == null) {
            return;
        }
        if (comp instanceof FormPanel) {
            FormPanel pnl = (FormPanel) comp;
            populateMainPanel(_layout, pnl, _fi);
            pnl.setNLS(getNLS());
        } else if (comp instanceof EImagePanel) {
            EImagePanel pnl = (EImagePanel) comp;
            populateMainPanel(_layout, pnl, _fi);
        } else if (comp instanceof EPanel) {
            EPanel pnl = (EPanel) comp;
            populateMainPanel(_layout, pnl, _fi);
        }
    }

    private void putPanel(FormItem fi) {
        JComponent jComp = getComponent(fi);
        populatePanel(fi, jComp);
        hash.put(fi.getName(), fi);
    }

    private void putSplitPane(FormItem _fi) {
        ESplitPane jsp = (ESplitPane) getComponent(_fi);
        jsp.setLeftComponent(getComponent(_fi.getContentsAt(0)));
        jsp.setRightComponent(getComponent(_fi.getContentsAt(1)));
        jsp.setDividerLocation(.5D);
        jsp.setResizeWeight(.5D);
        jsp.setOneTouchExpandable(true);
        jsp.setContinuousLayout(true);
        hash.put(_fi.getName(), _fi);
    }

    private Component getComponent(Object _o) {
        if (_o instanceof FormItem) {
            return getComponent((FormItem) _o);

        } else if (_o instanceof Component) {
            return (Component) _o;
        }
        return null;
    }

    private void putMenu(FormItem _fi) {
        JMenu menu = (JMenu) getComponent(_fi);
        int ii = -1;
        menu.setFont(getFormFont());
        ii = _fi.getContentsSize();
        for (int i = 0; i < ii; ++i) {
            Object o = _fi.getContentsAt(i);
            if (o instanceof FormItem) {
                FormItem fi = (FormItem) o;
                JComponent comp = getComponent(fi);
                menu.add(comp);
            } else if (o instanceof String) {
                if (((String) o).equalsIgnoreCase("separator")) {
                    menu.addSeparator();
                }
            }
        }
        menuBar.add(menu);
    }

    private FormItem processFormItem(String attribute, String value, FormItem fi) {
        if (attribute.equalsIgnoreCase("NAME")) {
            fi.setName(value);

        } else if (attribute.equalsIgnoreCase("ANCHOR")) {
            fi.setAnchor(getAnchorConstant(value));

        } else if (attribute.equalsIgnoreCase("FILL")) {
            fi.setFill(getFillConstant(value));

        } else if (attribute.equalsIgnoreCase("X")) {
            fi.setX(getInt(value));

        } else if (attribute.equalsIgnoreCase("Y")) {
            fi.setY(getInt(value));

        } else if (attribute.equalsIgnoreCase("PADX")) {
            fi.setPadX(getInt(value));

        } else if (attribute.equalsIgnoreCase("PADY")) {
            fi.setPadY(getInt(value));

        } else if (attribute.equalsIgnoreCase("OPAQUE")) {
            fi.setOpaque(getBoolean(value));

        } else if (attribute.equalsIgnoreCase("HEIGHT")) {
            fi.setGridHeight(getInt(value));

        } else if (attribute.equalsIgnoreCase("WIDTH")) {
            fi.setGridWidth(getInt(value));

        } else if (attribute.equalsIgnoreCase("IMAGE")) {
            fi.setImage(value);

        } else if (attribute.equalsIgnoreCase("LAYOUT")) {
            fi.setLayout(getStringArray(value));

        } else if (attribute.equalsIgnoreCase("MISC")) {
            fi.setMisc(getStringArray(value));

        } else if (attribute.equalsIgnoreCase("IMAGEPANELTYPE")) {
            fi.setImagePanelType(getInt(value));

        } else if (attribute.equalsIgnoreCase("JSPLITPANELTYPE")) {
            fi.setImagePanelType(getInt(value));

        } else if (attribute.equalsIgnoreCase("CLEARCONSTRAINTS")) {
            fi.clearConstraints();

        } else if (attribute.equalsIgnoreCase("ICON")) {
            fi.setImageIcon(value);

        } else if (attribute.equalsIgnoreCase("TEXT")) {
            fi.setText(value);

        } else if (attribute.equalsIgnoreCase("CLICKS")) {
            fi.setNumberClicks(getInt(value));

        } else if (attribute.equalsIgnoreCase("BACKCOLOR")) {
            fi.setBackground(getColor(value));

        } else if (attribute.equalsIgnoreCase("FORECOLOR")) {
            fi.setForeground(getColor(value));

        } else if (attribute.equalsIgnoreCase("METHOD")) {
            fi.setMethod(getFormMethod(value));

        } else if (attribute.equalsIgnoreCase("SHORTCUT")) {
            fi.setShortCut(value);

        } else if (attribute.equalsIgnoreCase("KEYSTROKE")) {
            fi.setKeyStroke(value);

        } else if (attribute.equalsIgnoreCase("SIZE")) {
            fi.setSize(getDimension(value));

        } else if (attribute.equalsIgnoreCase("TOOLTIP")) {
            fi.setToolTip(value);

        } else if (attribute.equalsIgnoreCase("COLUMNS")) {
            fi.addColumn(getObjectArray(value));

        } else if (attribute.equalsIgnoreCase("ENABLED")) {
            if (value.equalsIgnoreCase("TRUE") || value.equalsIgnoreCase("FALSE")) {
                fi.setEnabled(getBoolean(value));

            } else {
                fi.setEnabled(getBooleanFromMethod(value));
            }
        } else if (attribute.equalsIgnoreCase("VISIBLE")) {
            fi.setVisible(getBoolean(value));

        } else if (attribute.equalsIgnoreCase("COMPONENT")) {
            fi.setJComponent(getVariable(value));

        } else if (attribute.equalsIgnoreCase("ADD") || attribute.equalsIgnoreCase("ROWS")) {
            if (fi.getType() == FormItem.MENU) {
                String[] str = getStringArray(value);
                int ii = str.length;
                Object[] obj = new Object[ii];
                for (int i = 0; i < ii; ++i) {
                    if (str[i].equalsIgnoreCase("SEPARATOR")) {
                        obj[i] = str[i];

                    } else {
                        obj[i] = getVariable(str[i]);
                    }
                }
                fi.addContents(obj);
            } else {
                fi.addContents(getObjectArray(value));
            }
        } else {
            appendLog("navForm.processFormItem0(" + attribute + ") is undefined.");
        }
        return fi;
    }

    /**
     * processFormItem
     *
     * @author Anthony C. Liberto
     * @param attribute
     * @param o
     * @param fi
     * @return
     * /
    protected FormItem processFormItem(String attribute, Object o, FormItem fi) {
        if (attribute.equalsIgnoreCase("CONTENTS")) {
            if (o instanceof Vector) {
                fi.setContents((Vector) o);
            } else {
                appendLog("navForm.processFormItem(" + attribute + ") is " + o.getClass().getName());
            }
        } else if (attribute.equalsIgnoreCase("METHOD")) {
            if (o instanceof FormMethod) {
                fi.setMethod((FormMethod) o);
            } else {
                appendLog("navForm.processFormItem(" + attribute + ") is " + o.getClass().getName());
            }
        } else if (attribute.equalsIgnoreCase("ADD")) {
            fi.addContents(o);
        } else if (attribute.equalsIgnoreCase("SIZE")) {
            if (o instanceof Dimension) {
                fi.setSize((Dimension) o);
            } else {
                appendLog("navForm.processFormItem(" + attribute + ") is " + o.getClass().getName());
            }
        } else {
            appendLog("navForm.processFormItem(" + attribute + ") is undefined.");
        }

        return fi;
    }*/

    /**
     * processFormPanelItem
     *
     * @author Anthony C. Liberto
     * @param attribute
     * @param value
     * @param fi
     * @return
     * /
    protected FormItem processFormPanelItem(String attribute, String value, FormItem fi) {
        return fi;
    }*/

    /**
     * navigate section
     */
    private void processNavigate(Attributes atts) {
        FormItem fi = new FormItem("NAVIGATE");
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }
        putGroup(fi); //group
        getComponent(fi);
        return;
    }
    private void processEndNavigate() {
    }

    /**
     * edit section
     */
    private void processEdit(Attributes atts) {
        FormItem fi = new FormItem("JSCROLLPANE");
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }
        putGroup(fi); //group
        getComponent(fi);
    }
    private void processEndEdit() {
    }

    /**
     * form section
     */
    private void processForm(Attributes atts) {
        FormItem fi = new FormItem("GSCROLLPANE");
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }
        putGroup(fi); //group
        getComponent(fi);
    }
    private void processEndForm() {
    }

    /**
     * matrix section
     */
    private void processMatrix(Attributes atts) {
        FormItem fi = new FormItem("GSCROLLPANE");
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }
        putGroup(fi); //group
        getComponent(fi);
    }
    private void processEndMatrix() {
    }

    /**
     * used section
     */
    private void processUsed(Attributes atts) {
        FormItem fi = new FormItem("GSCROLLPANE");
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }
        putGroup(fi); //group
        getComponent(fi);
    }
    private void processEndUsed() {
    }

    /**
     * locked section
     */
    private void processLocked(Attributes atts) {
        FormItem fi = new FormItem("GSCROLLPANE");
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }
        putGroup(fi); //group
        getComponent(fi);
    }
    private void processEndLocked() {
    }

    /**
     * GButton section
     */
    private void processGButton(Attributes atts) {
        FormItem fi = new FormItem("GBUTTON");
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }
        putGroup(fi); //group
        put(fi);
    }
    private void processEndGButton() {
    }

    /**
     * GPanel section
     */
    private void processGPanel(Attributes atts) {
        FormItem fi = new FormItem("GPANEL");
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }
        putGroup(fi); //group
        putPanel(fi);
    }
    private void processEndGPanel() {
    }

    /**
     * GLabel section
     */
    private void processGLabel(Attributes atts) {
        FormItem fi = new FormItem("GLABEL");
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }
        putGroup(fi); //group
        put(fi);
    }
    private void processEndGLabel() {
    }

    /**
     * GComboBox section
     */
    private void processGComboBox(Attributes atts) {
        FormItem fi = new FormItem("GCOMBOBOX");
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }
        putGroup(fi); //group
        put(fi);
    }
    private void processEndGComboBox() {
    }

    /**
     * GTable section
     */
    private void processGTable(Attributes atts) {
        FormItem fi = new FormItem("GTABLE");
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }
        putGroup(fi); //group
        put(fi);
    }
    private void processEndGTable() {
    }

    /**
     * ImagePanel section
     */
    private void processImagePanel(Attributes atts) {
        FormItem fi = new FormItem("IMAGEPANEL");
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }
        putGroup(fi); //group
        putPanel(fi);
    }
    private void processEndImagePanel() {
    }

    /**
     * GURL section
     */
    private void processGURL(Attributes atts) {
        FormItem fi = new FormItem("GURL");
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }
        putGroup(fi); //group
        put(fi);
    }
    private void processEndGURL() {
    }

    /**
     * GWebPage section
     */
    private void processGWebPage(Attributes atts) {
        FormItem fi = new FormItem("GWEBPAGE");
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }
        putGroup(fi); //group
        put(fi);
    }
    private void processEndGWebPage() {
    }

    /**
     * Object section
     */
    private void processJComponent(Attributes atts) {
        FormItem fi = new FormItem("JCOMPONENT");
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }
        putGroup(fi); //group
        put(fi);
    }
    private void processEndJComponent() {
    }

    /**
     * GScrollPane section
     */
    private void processGScrollPane(Attributes atts) {
        FormItem fi = new FormItem("GSCROLLPANE");
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }
        putGroup(fi); //group
        put(fi);
    }
    private void processEndGScrollPane() {
    }

    /**
     * JScrollPane section
     */
    private void processJScrollPane(Attributes atts) {
        FormItem fi = new FormItem("JSCROLLPANE");
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }
        putGroup(fi); //group
        put(fi);
    }
    private void processEndJScrollPane() {
    }

    /**
     * CollapsePane section
     */
    private void processCollapsePane(Attributes atts) {
        FormItem fi = new FormItem("COLLAPSEPANE");
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
                //				fi = processFormPanelItem(atts.getLocalName(i),atts.getValue(i),fi);
            }
        }
        putGroup(fi); //group
        putPanel(fi);
    }

    private void processEndCollapsePane() {
    }

    /**
     * JSplitPane section
     */
    private void processSplitPane(Attributes atts) {
        FormItem fi = new FormItem("JSPLITPANE");
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }
        putGroup(fi); //group
        putSplitPane(fi);
    }

    private void processEndSplitPane() {
    }

    /**
     * Do section
     */
    private void processDo(Attributes atts) {
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                processDo(atts.getLocalName(i), atts.getValue(i));
            }
        }
    }

    private void processDo(String _name, String _val) {
        if (_name.equalsIgnoreCase("method")) {
            FormMethod fm = getFormMethod(_val);
            if (fm != null) {
                fm.invoke();
            }
        }
    }

    private void processEndDo() {
    }

    /**
     * Method section
     */
    private void processMethod(Attributes atts) {
        FormMethod fm = new FormMethod();
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fm = processMethod(atts.getLocalName(i), atts.getValue(i), fm);
            }
        }
        put(fm);
    }

    private FormMethod processMethod(String attribute, String value, FormMethod fm) {
        if (attribute.equalsIgnoreCase("NAME")) {
            fm.setName(value);
        } else if (attribute.equalsIgnoreCase("PARMS")) {
            fm.setParms(getStringArray(value));
        } else if (attribute.equalsIgnoreCase("ARGS")) {
            fm.setArgs(getObjectArray(value));
        } else if (attribute.equalsIgnoreCase("OBJECT")) {
            fm.setObject(getVariable(value));
            //			fm.setObject(getObject(value));
        } else if (attribute.equalsIgnoreCase("METHOD")) {
            fm.setMethod(value);
        } else {
            appendLog("navForm.processMethod(" + attribute + ") is undefined.");
        }
        return fm;
    }
    private void processEndMethod() {
    }

    /*
     * variable section
     */
    private void processVariable(Attributes atts) {
        FormVariable fv = new FormVariable();
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fv = processVariable(atts.getLocalName(i), atts.getValue(i), fv);
            }
        }
        put(fv);
    }

    private FormVariable processVariable(String attribute, String value, FormVariable fv) {
        if (attribute.equalsIgnoreCase("NAME")) {
            fv.setName(value);

        } else if (attribute.equalsIgnoreCase("CLASS")) {
            fv.setClass(value);

        } else if (attribute.equalsIgnoreCase("PARMS")) {
            fv.setParms(getStringArray(value));

        } else if (attribute.equalsIgnoreCase("ARGS")) {
            fv.setArgs(getObjectArray(value));

        } else if (attribute.equalsIgnoreCase("OBJECT")) {
            fv.setObject(value);

        } else if (attribute.equalsIgnoreCase("VALUE")) {
            fv.setValue(value);

        } else {
            fv.set(attribute, getObjectArray(value));
        }
        return fv;
    }

    private void processEndVariable() {
    }

    /*
     * nls section
     */
    private void processNLS(Attributes atts) {
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                processNLS(atts.getLocalName(i), atts.getValue(i));
            }
        }
    }

    private void processNLS(String attribute, String value) {
        if (attribute.equalsIgnoreCase("VALUE")) {
            Integer i = getInteger(value, 1);
            nlsStack.push(i);
        }
    }

    private void processEndNLS() {
        nlsStack.pop();
    }

    /**
     * Menu section
     */
    private void processMenu(Attributes atts) {
        FormItem fi = new FormItem("MENU");
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }
        putGroup(fi); //group
        putMenu(fi);
    }
    private void processEndMenu() {
    }

    /**
     * Menu section
     */
    private void processMenuItem(Attributes atts) {
        FormItem fi = new FormItem("MENUITEM");
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }
        putGroup(fi); //group
        put(fi);
    }
    private void processEndMenuItem() {
    }

    /*
     * Redefine secion
     */
    private void processRedefine(Attributes atts) {
        String strName = null;
        Object o = null;
        int ii = atts.getLength();
        for (int i = 0; i < ii; ++i) {
            if (atts.getLocalName(i).equalsIgnoreCase("NAME")) {
                strName = new String(atts.getValue(i));
                break;
            }
        }
        if (strName == null) {
            return;
        }
        o = getVariable(strName);
        if (o == null) {
            return;
        }
        if (o instanceof FormMethod) {
            FormMethod fm = (FormMethod) o;
            for (int i = 0; i < ii; ++i) {
                processMethod(atts.getLocalName(i), atts.getValue(i), fm);
            }
        } else if (o instanceof FormVariable) {
            FormVariable fv = (FormVariable) o;
            for (int i = 0; i < ii; ++i) {
                processVariable(atts.getLocalName(i), atts.getValue(i), fv);
            }
        } else if (o instanceof FormItem) {
            FormItem fi = (FormItem) o;
            for (int i = 0; i < ii; ++i) {
                processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }
    }

    private void processEndRedefine() {
    }

    /*
     * this section
     */
    private void processThis(Attributes atts) {
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                processThis(atts.getLocalName(i), atts.getValue(i));
            }
        }
    }

    private void processThis(String attribute, String value) {
        if (attribute.equalsIgnoreCase("METHOD")) {
            Object o = getVariable(value);
            if (o instanceof FormMethod) {
                ((FormMethod) o).invoke();
            }
        }
    }

    private void processEndThis() {
    }

    /*
     * mainPane section
     */
    private void processMainPane(Attributes atts) {
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                processMainPane(atts.getLocalName(i), atts.getValue(i));
            }
        }
    }

    private void processMainPane(String attribute, String value) {
        if (attribute.equalsIgnoreCase("LAYOUT")) {
            String[] s = getStringArray(value);
            if (s[0].equalsIgnoreCase("BORDER")) {
                mainPane.setLayout(new BorderLayout());

            } else if (s[0].equalsIgnoreCase("GRIDBAGLAYOUT")) {
                mainPane.setLayout(new GridBagLayout());

            } else if (s[0].equalsIgnoreCase("GRID")) {
                int len = s.length;
                if (len == 3) {
                    mainPane.setLayout(new GridLayout(getInt(s[1]), getInt(s[2])));

                } else if (len == 5) {
                    mainPane.setLayout(new GridLayout(getInt(s[1]), getInt(s[2]), getInt(s[3]), getInt(s[4])));
                }
            }
        } else if (attribute.equalsIgnoreCase("ADD")) {
            LayoutManager layout = mainPane.getLayout();
            Object[] o = getObjectArray(value);
            int ii = o.length;
            for (int i = 0; i < ii; ++i) {
                if (o[i] instanceof FormItem) {
                    populateMainPanel(layout, (FormItem) o[i]);
                }
            }
        }
    }

    private void processEndMainPane() {
    }

    /*
     * font
     */
    private void processFont(Attributes atts) { //stack
        FormFont ff = new FormFont(); //stack
        for (int i = 0; i < atts.getLength(); ++i) { //stack
            if (atts.getType(i).equals("CDATA")) { //stack
                ff = processFont(atts.getLocalName(i), atts.getValue(i), ff); //stack
            } //stack
        } //stack
        pushFont(ff.getFont()); //stack
    } //stack

    /**
     * getFormFont
     * @return
     * @author Anthony C. Liberto
     */
    private Font getFormFont() { //stack
        if (fontStack.isEmpty()) { //stack
            return UIManager.getFont("Tree.font");
        } //stack
        //			return UIManager.getFont("Label.font");									//stack
        return (Font) fontStack.peek(); //stack
    } //stack

    private FormFont processFont(String attribute, String value, FormFont ff) { //stack
        if (attribute.equalsIgnoreCase("FACE")) { //stack
            ff.setFace(value); //stack

        } else if (attribute.equalsIgnoreCase("SIZE")) { //stack
            ff.setSize(getInt(value)); //stack

        } else if (attribute.equalsIgnoreCase("STYLE")) { //stack
            ff.setStyle(getInt(value));
        } //stack
        return ff; //stack
    } //stack

    private Object pushFont(Font _f) { //stack
        return fontStack.push(_f); //stack
    } //stack

    private void processEndFont() { //stack
        popFont(); //stack
    } //stack

    private Object popFont() { //stack
        return fontStack.pop(); //stack
    } //stack

    /*
     * background
     */
    private void processBackground(Attributes atts) { //stack
        for (int i = 0; i < atts.getLength(); ++i) { //stack
            if (atts.getType(i).equals("CDATA")) { //stack
                processBackground(atts.getLocalName(i), atts.getValue(i)); //stack
            } //stack
        } //stack
    } //stack

    private Color getFormBackground() { //stack
        if (backgroundStack.isEmpty()) { //stack
            return getBackground();
        } //stack
        return Color.decode((String) backgroundStack.peek()); //stack
    } //stack

    private void processBackground(String attribute, String value) { //stack
        if (attribute.equalsIgnoreCase("COLOR")) { //stack
            pushBackground(value);
        } //stack
    } //stack

    private Object pushBackground(String _s) { //stack
        return backgroundStack.push(_s); //stack
    } //stack

    private void processEndBackground() { //stack
        popBackground(); //stack
    } //stack

    private Object popBackground() { //stack
        return backgroundStack.pop(); //stack
    } //stack

    /*
     * foreground
     */
    private void processForeground(Attributes atts) { //stack
        for (int i = 0; i < atts.getLength(); ++i) { //stack
            if (atts.getType(i).equals("CDATA")) { //stack
                processForeground(atts.getLocalName(i), atts.getValue(i)); //stack
            } //stack
        } //stack
    } //stack

    private Color getFormForeground() { //stack
        if (foregroundStack.isEmpty()) { //stack
            return getPrefColor(PREF_COLOR_FOREGROUND, getForeground());
        } //stack
        return Color.decode((String) foregroundStack.peek()); //stack
    } //stack

    private void processForeground(String attribute, String value) { //stack
        if (attribute.equalsIgnoreCase("COLOR")) { //stack
            pushForeground(value);
        } //stack
    } //stack

    private Object pushForeground(String _s) { //stack
        return foregroundStack.push(_s); //stack
    } //stack

    private void processEndForeground() { //stack
        popForeground(); //stack
    } //stack

    private Object popForeground() { //stack
        return foregroundStack.pop(); //stack
    } //stack

    /*
     * mainForm
     */
    private void processMain(Attributes atts) { //stack
        for (int i = 0; i < atts.getLength(); ++i) { //stack
            if (atts.getType(i).equals("CDATA")) { //stack
                processMain(atts.getLocalName(i), atts.getValue(i)); //stack
            } //stack
        } //stack
    } //stack

    private void processMain(String attribute, String value) { //stack
        if (attribute.equalsIgnoreCase("BACKGROUND")) { //stack
            setBackground(Color.decode(value)); //stack

        } else if (attribute.equalsIgnoreCase("FOREGROUND")) { //stack
            setForeground(Color.decode(value));
        } //stack
    } //stack

    private void processEndMain() {
    } //stack

    /*
     * group
     */
    private void processGroup(Attributes atts) { //group
        group = new FormHash(); //group
        for (int i = 0; i < atts.getLength(); ++i) { //group
            if (atts.getType(i).equals("CDATA")) { //group
                processGroup(atts.getLocalName(i), atts.getValue(i)); //group
            } //group
        } //group
    }

    private void processGroup(String _attribute, String _value) { //group
        if (_attribute.equalsIgnoreCase("name")) { //group
            group.setName(_value);
        } //group
    } //group

    private void putGroup(FormItem _fi) { //group
        if (group != null) {
            group.put(_fi.getName(), _fi); //group
        }
    } //group

    /**
     * getGroupItemArray
     *
     * @author Anthony C. Liberto
     * @param _hash
     * @return
     * /
    protected FormItem[] getGroupItemArray(FormHash _hash) { //group
        int ii = _hash.size(); //group
        FormItem[] fi = new FormItem[ii]; //group
        for (int i = 0; i < ii; ++i) { //group
            fi[i] = (FormItem) _hash.get(i);
        } //group
        return fi; //group
    } //group
    */

    /**
     * getGroupItem
     *
     * @author Anthony C. Liberto
     * @param _hash
     * @param _key
     * @return
     * /
    protected Object getGroupItem(FormHash _hash, String _key) { //group
        if (_hash.containsKey(_key)) { //group
            Object o = _hash.get(_key); //group
            if (o instanceof FormItem) { //group
                return getComponent((FormItem) o);
            } //group
        } //group
        return null; //group
    } //group
*/
    /**
     * getGroup
     *
     * @author Anthony C. Liberto
     * @param _key
     * @return
     * /
    protected FormHash getGroup(String _key) { //group
        if (groups.containsKey(_key)) { //group
            return (FormHash) groups.get(_key);
        } //group
        return null; //group
    } //group
    */

    private void processEndGroup() { //group
        groups.put(group.getName(), group); //group
        group = null; //group
    } //group

    /*
     *enablers
     */

    private void processEnablers(Attributes atts) { //enablers
        FormEnabler fe = new FormEnabler(); //enablers
        for (int i = 0; i < atts.getLength(); ++i) { //enablers
            if (atts.getType(i).equals("CDATA")) { //enablers
                fe = processEnablers(atts.getLocalName(i), atts.getValue(i), fe); //enablers
            } //enablers
        } //enablers
        hashEnabler.put(fe.getName(), fe); //enablers
    } //enablers

    private FormEnabler processEnablers(String _attribute, String _value, FormEnabler _fe) { //enablers
        if (_attribute.equalsIgnoreCase("NAME")) { //enablers
            _fe.setName(_value); //enablers

        } else if (_attribute.equalsIgnoreCase("TYPE")) { //enablers
            _fe.setType(_value); //enablers

        } else if (_attribute.equalsIgnoreCase("EQUATION")) { //enablers
            _fe.setEquation(_value); //enablers

        } else if (_attribute.equalsIgnoreCase("COMPONENTS")) { //enablers
            _fe.setComponents(getObjectArray(_value)); //enablers

        } else if (_attribute.equalsIgnoreCase("METHOD")) { //enablers
            _fe.setMethod(getFormMethod(_value)); //enablers

        } else if (_attribute.equalsIgnoreCase("EQUATIONTYPE")) { //enablers
            _fe.setEquationType(_value);
        } //enablers
        return _fe; //enablers
    } //enablers

    private void processEndEnablers() {
    } //enablers

    /**
     * invokeEnabler
     * @param _name
     * @author Anthony C. Liberto
     * /
    public void invokeEnabler(String _name) { //enablers
        //		invokeEnabler(_name,null);																//enablers
    } //enablers
    */

    /**
     * invokeEnablers
     * @param _type
     * @author Anthony C. Liberto
     * /
    public void invokeEnablers(String _type) { //enablers
        //		invokeEnablers(_type, null);															//enablers
    } //enablers
    */

    /**
     * getEnabler
     *
     * @author Anthony C. Liberto
     * @param _name
     * @return
     * /
    protected FormEnabler getEnabler(String _name) {
        if (hashEnabler.containsKey(_name)) { //enablers
            return (FormEnabler) hashEnabler.get(_name);
        } //enablers
        return null; //enablers
    } //enablers
    */

    /**
     * getEnablers
     *
     * @author Anthony C. Liberto
     * @param _type
     * @return
     * /
    protected FormEnabler[] getEnablers(String _type) { //enablers
        int ii = -1;
        Vector v = null;
        int xx = -1;
        FormEnabler[] out = null;
        if (_type.equalsIgnoreCase("ALL")) { //enablers
            ii = hashEnabler.size(); //enablers
            out = new FormEnabler[ii]; //enablers
            for (int i = 0; i < ii; ++i) { //enablers
                out[i] = (FormEnabler) hashEnabler.get(i);
            } //enablers
            return out; //enablers
        } //enablers
        ii = hashEnabler.size(); //enablers
        v = new Vector(); //enablers
        for (int i = 0; i < ii; ++i) { //enablers
            Object o = hashEnabler.get(i); //enablers
            FormEnabler fe = (FormEnabler) o; //enablers
            if (_type.equalsIgnoreCase(fe.getType())) { //enablers
                v.add(fe);
            } //enablers
        } //enablers
        if (v.isEmpty()) {
            return null;
        } //enablers
        xx = v.size(); //enablers
        out = new FormEnabler[xx]; //enablers
        for (int x = 0; x < xx; ++x) { //enablers
            out[x] = (FormEnabler) v.get(x);
        } //enablers
        return out; //enablers
    } //enablers
*/
    /*
     * next
     */
    private void processNext(Attributes atts) { //next
        for (int i = 0; i < atts.getLength(); ++i) { //next
            if (atts.getType(i).equals("CDATA")) { //next
                processNext(atts.getLocalName(i), atts.getValue(i)); //next
            } //next
        } //next
    }

    private void processNext(String _att, String _val) { //next
        if (_att.equalsIgnoreCase("NAME")) { //next
            setNextForm(_val);
        } //next
    } //next

    /**
     * hasNextForm
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean hasNextForm() { //next
        if (nextForm == null) { //next
            return false; //next
        } //next
        return true; //next
    } //next
    */

    /**
     * setNextForm
     * @param _s
     * @author Anthony C. Liberto
     */
    private void setNextForm(String _s) { //next
        nextForm = new String(_s); //next
    } //next

    /**
     * getNextForm
     * @return
     * @author Anthony C. Liberto
     */
    public String getNextForm() { //next
        return nextForm; //next
    } //next

    private void processEndNext() {
    } //next

    /*
     * linked file section
     */
    private void processLink(Attributes atts) { //linkedFile
        for (int i = 0; i < atts.getLength(); ++i) { //linkedFile
            if (atts.getType(i).equals("CDATA")) { //linkedFile
                processLink(atts.getLocalName(i), atts.getValue(i));
            } //linkedFile
        } //linkedFile
    } //linkedFile

    private void processLink(String _att, String _value) { //linkedFile
        if (_att.equalsIgnoreCase("HREF")) { //linkedFile
            generateForm(getXML(_value), true);
        } //linkedFile
    } //linkedFile

    private void processEndLink() { //linkedFile
        //		if (linkedFileStack.empty()) return;													//linkedFile
        //		generateForm((String)linkedFileStack.pop(), true);										//linkedFile
    } //linkedFile

    /**
     * generate form area
     */
    private void processElement(String tagName, Attributes atts) {
        if (tagName.equalsIgnoreCase("NAVIGATE")) {
            processNavigate(atts);
        } else if (tagName.equalsIgnoreCase("EDIT")) {
            processEdit(atts);
        } else if (tagName.equalsIgnoreCase("FORM")) {
            processForm(atts);
        } else if (tagName.equalsIgnoreCase("MATRIX")) {
            processMatrix(atts);
        } else if (tagName.equalsIgnoreCase("USED")) {
            processUsed(atts);
        } else if (tagName.equalsIgnoreCase("LOCKED")) {
            processLocked(atts);
        } else if (tagName.equalsIgnoreCase("GBUTTON")) {
            processGButton(atts);
        } else if (tagName.equalsIgnoreCase("GPANEL")) {
            processGPanel(atts);
        } else if (tagName.equalsIgnoreCase("GLABEL")) {
            processGLabel(atts);
        } else if (tagName.equalsIgnoreCase("GCOMBOBOX")) {
            processGComboBox(atts);
        } else if (tagName.equalsIgnoreCase("GTABLE")) {
            processGTable(atts);
        } else if (tagName.equalsIgnoreCase("IMAGEPANEL")) {
            processImagePanel(atts);
        } else if (tagName.equalsIgnoreCase("GURL")) {
            processGURL(atts);
        } else if (tagName.equalsIgnoreCase("GWEBPAGE")) {
            processGWebPage(atts);
        } else if (tagName.equalsIgnoreCase("JCOMPONENT")) {
            processJComponent(atts);
        } else if (tagName.equalsIgnoreCase("GSCROLLPANE")) {
            processGScrollPane(atts);
        } else if (tagName.equalsIgnoreCase("JSCROLLPANE")) {
            processJScrollPane(atts);
        } else if (tagName.equalsIgnoreCase("COLLAPSEPANE")) {
            processCollapsePane(atts);
        } else if (tagName.equalsIgnoreCase("JSPLITPANE")) {
            processSplitPane(atts);
        } else if (tagName.equalsIgnoreCase("DO")) {
            processDo(atts);
        } else if (tagName.equalsIgnoreCase("METHOD")) {
            processMethod(atts);
        } else if (tagName.equalsIgnoreCase("VARIABLE")) {
            processVariable(atts);
        } else if (tagName.equalsIgnoreCase("NLS")) {
            processNLS(atts);
        } else if (tagName.equalsIgnoreCase("MENU")) {
            processMenu(atts);
        } else if (tagName.equalsIgnoreCase("MENUITEM")) {
            processMenuItem(atts);
        } else if (tagName.equalsIgnoreCase("REDEFINE")) {
            processRedefine(atts);
        } else if (tagName.equalsIgnoreCase("MAINPANE")) {
            processMainPane(atts);
        } else if (tagName.equalsIgnoreCase("THIS")) {
            processThis(atts);
        } else if (tagName.equalsIgnoreCase("FONT")) { //stack
            processFont(atts); //stack
        } else if (tagName.equalsIgnoreCase("MAIN")) { //stack
            processMain(atts); //stack
        } else if (tagName.equalsIgnoreCase("BACKGROUND")) { //stack
            processBackground(atts); //stack
        } else if (tagName.equalsIgnoreCase("FOREGROUND")) { //stack
            processForeground(atts); //stack
        } else if (tagName.equalsIgnoreCase("GROUP")) { //stack
            processGroup(atts); //stack
        } else if (tagName.equalsIgnoreCase("ENABLER")) { //enablers
            processEnablers(atts); //enablers
        } else if (tagName.equalsIgnoreCase("NEXT")) { //next
            processNext(atts); //next
        } else if (tagName.equalsIgnoreCase("LINK")) { //linkedFile
            processLink(atts); //linkedFile
        } else {
            appendLog("navForm.Element " + tagName + " is undefined.");
        }
    }

    private void processEndElement(String tagName) {
        if (tagName.equalsIgnoreCase("NAVIGATE")) {
            processEndNavigate();

        } else if (tagName.equalsIgnoreCase("EDIT")) {
            processEndEdit();

        } else if (tagName.equalsIgnoreCase("FORM")) {
            processEndForm();

        } else if (tagName.equalsIgnoreCase("MATRIX")) {
            processEndMatrix();

        } else if (tagName.equalsIgnoreCase("USED")) {
            processEndUsed();

        } else if (tagName.equalsIgnoreCase("LOCKED")) {
            processEndLocked();

        } else if (tagName.equalsIgnoreCase("GBUTTON")) {
            processEndGButton();

        } else if (tagName.equalsIgnoreCase("GPANEL")) {
            processEndGPanel();

        } else if (tagName.equalsIgnoreCase("GLABEL")) {
            processEndGLabel();

        } else if (tagName.equalsIgnoreCase("GCOMBOBOX")) {
            processEndGComboBox();

        } else if (tagName.equalsIgnoreCase("GTABLE")) {
            processEndGTable();

        } else if (tagName.equalsIgnoreCase("IMAGEPANEL")) {
            processEndImagePanel();

        } else if (tagName.equalsIgnoreCase("GURL")) {
            processEndGURL();

        } else if (tagName.equalsIgnoreCase("GWEBPAGE")) {
            processEndGWebPage();

        } else if (tagName.equalsIgnoreCase("JCOMPONENT")) {
            processEndJComponent();

        } else if (tagName.equalsIgnoreCase("GSCROLLPANE")) {
            processEndGScrollPane();

        } else if (tagName.equalsIgnoreCase("JSCROLLPANE")) {
            processEndJScrollPane();

        } else if (tagName.equalsIgnoreCase("COLLAPSEPANE")) {
            processEndCollapsePane();

        } else if (tagName.equalsIgnoreCase("JSPLITPANE")) {
            processEndSplitPane();

        } else if (tagName.equalsIgnoreCase("DO")) {
            processEndDo();

        } else if (tagName.equalsIgnoreCase("METHOD")) {
            processEndMethod();

        } else if (tagName.equalsIgnoreCase("VARIABLE")) {
            processEndVariable();

        } else if (tagName.equalsIgnoreCase("NLS")) {
            processEndNLS();

        } else if (tagName.equalsIgnoreCase("MENU")) {
            processEndMenu();

        } else if (tagName.equalsIgnoreCase("MENUITEM")) {
            processEndMenuItem();

        } else if (tagName.equalsIgnoreCase("REDEFINE")) {
            processEndRedefine();

        } else if (tagName.equalsIgnoreCase("MAINPANE")) {
            processEndMainPane();

        } else if (tagName.equalsIgnoreCase("THIS")) {
            processEndThis();

        } else if (tagName.equalsIgnoreCase("FONT")) { //stack
            processEndFont(); //stack

        } else if (tagName.equalsIgnoreCase("MAIN")) { //stack
            processEndMain(); //stack

        } else if (tagName.equalsIgnoreCase("BACKGROUND")) { //stack
            processEndBackground(); //stack

        } else if (tagName.equalsIgnoreCase("FOREGROUND")) { //stack
            processEndForeground(); //stack

        } else if (tagName.equalsIgnoreCase("GROUP")) { //group
            processEndGroup(); //group

        } else if (tagName.equalsIgnoreCase("ENABLER")) { //enablers
            processEndEnablers(); //enablers

        } else if (tagName.equalsIgnoreCase("NEXT")) { //next
            processEndNext(); //next

        } else if (tagName.equalsIgnoreCase("LINK")) { //linkedFile
            processEndLink();
        } //linkedFile
    }

    private void processStartDocument() {
    }
    private void processEndDocument() {
    }

    private void processCharacters(String s) {
    }

    /**
     * processCharacters
     *
     * @author Anthony C. Liberto
     * @param s
     * @param sb
     * @param i
     * /
    protected void processCharacters(String s, StringBuffer sb, int i) {
    }*/

    /**
     * getXML
     *
     * @author Anthony C. Liberto
     * @param _byte
     * @param _encoding
     * @return
     * /
    protected String getXML(byte[] _byte, String _encoding) { //next
        XML = gio().getString(_byte, _encoding); //next
        return XML; //next
    } //next
    */

    private String getXML(String _fileName) { //next
        if (gio().exists(_fileName)) {
            XML = gio().readString(_fileName); //next
        } else {
            XML = getHTML(_fileName);
        }
        return XML; //next
    } //next

    /**
     * the parser(s)
     */
    public void generateForm() {
        generateForm(XML);
    }

    private void generateForm(String _xml) {
        generateForm(_xml, false); //linkedFile
    } //linkedFile

    private String getAttsString(Attributes _atts) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < _atts.getLength(); ++i) {
            sb.append("    " + _atts.getLocalName(i) + "=" + _atts.getValue(i));
        }
        return sb.toString();
    }

    private void generateForm(String _xml, boolean _linkedXML) { //linkedFile
        DefaultHandler defaulthandler = new DefaultHandler() {

            public void startElement(String URI, String tagName, String qName, Attributes atts) {
                if (bValidate) {
                    appendLog("startElement( " + URI + ", " + tagName + ", " + qName + ", atts)");
                }
                processElement(tagName, atts);
                if (bValidate) {
                    appendLog(getAttsString(atts));
                }
            }

            public void endElement(String URI, String tagName, String qName) {
                if (bValidate) {
                    appendLog("endElement( " + URI + ", " + tagName + ", " + qName + ", atts)");
                }
                processEndElement(tagName);
            }
            public void startDocument() {
                processStartDocument();
            }

            public void endDocument() {
                processEndDocument();
            }

            public void characters(char[] rgc, int nStart, int nLength) throws SAXException {
                String str = new String(rgc, nStart, nLength);
                if (bValidate) {
                    appendLog("characters: " + str);
                }
                processCharacters(str.trim());
            }

            public void error(SAXParseException saxparseexception) throws SAXException {
                if (bValidate) {
                    appendLog("editform exception( " + saxparseexception.toString() + ")");
                }
                throw saxparseexception;
            }

        };
        SAXParser parser = new SAXParser();
        parser.setContentHandler(defaulthandler);
        if (!_linkedXML) { //linkedFile
            hash.clear(); //linkedFile
            groups.clear(); //linkedFile
            hashEnabler.clear(); //linkedFile
        } //linkedFile
        try {
            //				parser.parse(strXMLInput);										//parses from file
            parser.parse(new InputSource(new StringReader(_xml))); //parses from String
        } catch (Exception x) {
            x.printStackTrace();
            setCode("msg11016.0");
            setParm(fileName);
            showFYI();
            //ErrorFound = true;
        } finally {
            gc();
        }
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        XML = null;
        seed = null;

        nextForm = null; //next
        prevForm = null; //next
        name = null; //next

        mainPane.removeAll();
        mainPane = null;
        if (curPanel != null) {
            curPanel.removeAll();
            curPanel = null;
        }
        if (tablePane != null) {
            tablePane.removeAll();
            tablePane = null;
        }
        gl = null;
        //curLay = null;
        //gbca = null;
        //panelConstraints = null;

        fontStack.clear();
        fontStack = null;
        backgroundStack.clear();
        backgroundStack = null;
        foregroundStack.clear();
        foregroundStack = null;

        groups.clear();
        groups = null;
        if (group != null) {
            group.clear();
            group = null;
        }
        hashEnabler.clear();
        hashEnabler = null;
        hash.clear();
        hash = null;
        nlsStack.clear();
        nlsStack = null;
        //owner = null;
        parent = null;
        //tableBord = null;
        //requiredTableBord = null;
        //requiredBorder = null;

        menuBar.dereference();
        menuBar = null;
        if (tBar != null) {
            tBar.dereference();
            tBar = null;
        }
       // defFocus = null;
        removeAll();
    }

    /*
     cr_209046022
     */
    /**
     * generateToolbar
     * @param _item
     * @return
     * @author Anthony C. Liberto
     */
    public EannounceToolbar generateToolbar(ComboItem _item) {
        if (tBar == null) {
            tBar = ToolbarController.generateToolbar(_item, parent, null);
        }
        return tBar;
    }
}
