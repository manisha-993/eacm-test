/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: FormItem.java,v $
 * Revision 1.2  2009/05/26 12:57:12  wendy
 * Performance cleanup
 *
 * Revision 1.1  2007/04/18 19:43:40  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:59  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2005/09/08 17:59:01  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.7  2005/02/04 18:16:49  tony
 * JTest Formatter Fourth Pass
 *
 * Revision 1.6  2005/02/04 15:22:08  tony
 * JTest Format Third Pass
 *
 * Revision 1.5  2005/02/01 22:06:31  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/02/01 20:48:34  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/27 19:15:14  tony
 * JTest Format
 *
 * Revision 1.2  2004/02/19 21:34:53  tony
 * e-announce1.3
 *
 * Revision 1.1.1.1  2004/02/10 16:59:42  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2003/06/16 17:22:54  tony
 * updated logic for 1.2h to allow for the application to
 * automatically select the visible navigate as current when
 * the navigate splitpane is adjusted to its minimum or
 * its maximum.
 *
 * Revision 1.2  2003/03/07 21:40:48  tony
 * Accessibility update
 *
 * Revision 1.1.1.1  2003/03/03 18:03:48  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2002/11/09 00:01:57  tony
 * acl_20021108 changed JSplitPane to GSplitPane to
 * simplify controls and add functionality.  This should
 * assist in accessibility concerns.
 *
 * Revision 1.7  2002/11/07 16:58:21  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.epanels.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.ibm.eannounce.eforms.navigate.NavCartDialog;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class FormItem extends EObject implements ActionListener, MouseListener {
    /**
     * JCOMPONENT
     */
	private static final int JCOMPONENT = 0; //
    /**
     * COLLAPSEPANE
     */
	private static final int COLLAPSEPANE = 1; //
    /**
     * GPANEL
     */
	private static final int GPANEL = 2; //
    /**
     * IMAGEPANEL
     */
	private static final int IMAGEPANEL = 3; //
    /**
     * JSPLITPANE
     */
	private static final int JSPLITPANE = 4;
    /**
     * GSCROLLPANE
     */
	private static final int GSCROLLPANE = 5; //
    /**
     * JSCROLLPANE
     */
	private static final int JSCROLLPANE = 6; //
    /**
     * MENU
     */
	protected static final int MENU = 7; //
    /**
     * MENUITEM
     */
	private static final int MENUITEM = 8; //
    /**
     * GBUTTON
     */
	private static final int GBUTTON = 9; //
    /**
     * GCOMBOBOX
     */
	private static final int GCOMBOBOX = 10; //
    /**
     * GLABEL
     */
	private static final int GLABEL = 11; //
    /**
     * GTABLE
     */
	private static final int GTABLE = 12; //
    /**
     * GURL
     */
	private static final int GURL = 13;
    /**
     * GWEBPAGE
     */
	private static final int GWEBPAGE = 14;

    private final int nls = 1;
    private int type = -1;
    private String name = null;
    private LayoutManager layout = new GridBagLayout();
    private GridBagConstraints position = new GridBagConstraints(1, 1, 1, 1, 0, 0, 10, 0, new Insets(2, 2, 3, 3), 0, 0);
    private int clicks = 2;
    private Object object = null;
    private JComponent jComp = null;

    private Vector contents = new Vector();
    private Vector column = new Vector();
    private Color foreColor = null;
    private Color backColor = null;
    private HashMap misc = new HashMap();

    private FormItem() {
    }
    /**
     * formItem
     * @param _type
     * @author Anthony C. Liberto
     * /
    public FormItem(int _type) {
        setType(_type);
    }*/

    /**
     * formItem
     * @param _s
     * @author Anthony C. Liberto
     */
    protected FormItem(String _s) {
        setType(_s);
    }

    /**
     * isValid
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isValid() {
        if (name == null) {
            return false;
        }
        if (type == -1) {
            return false;
        }
        switch (type) {
        case GBUTTON :
            if (getMethod() == null) {
                return false;
            }
            if (getText() == null && getImage() == null) {
                return false;
            }
            break;
        case GPANEL :
            if (contents.isEmpty()) {
                return false;
            }
            break;
        case GLABEL :
            if (getText() == null && getImageIcon() == null) {
                return false;
            }
            break;
        case GSCROLLPANE :
            break;
        case GCOMBOBOX :
            if (getMethod() == null) {
                return false;
            }
            if (contents.isEmpty()) {
                return false;
            }
            break;
        case GTABLE :
            if (contents.isEmpty()) {
                return false;
            }
            if (column.isEmpty()) {
                return false;
            }
            break;
        case IMAGEPANEL :
            if (getImageIcon() == null) {
                return false;
            }
            break;
        case GURL :
            if (getText() == null) {
                return false;
            }
            break;
        case GWEBPAGE :
            if (getText() == null) {
                return false;
            }
            break;
        case JSCROLLPANE :
            if (contents.isEmpty()) {
                return false;
            }
            break;
        case JCOMPONENT :
            if (jComp == null) {
                return false;
            }
            break;
        case COLLAPSEPANE :
            break;
        case MENU :
            break;
        case JSPLITPANE :
            break;
        case MENUITEM :
            if (getMethod() == null) {
                return false;
            }
            if (getText() == null) {
                return false;
            }
            break;
        default:
            break;
        }
        return true;
    }*/

    /**
     * setType
     * @param _i
     * @author Anthony C. Liberto
     */
    private void setType(int _i) {
        type = _i;
        return;
    }

    /**
     * setType
     * @param _s
     * @author Anthony C. Liberto
     */
    private void setType(String _s) {
        if (_s.equalsIgnoreCase("GBUTTON")) {
            setType(GBUTTON);
        } else if (_s.equalsIgnoreCase("GPANEL")) {
            setType(GPANEL);
        } else if (_s.equalsIgnoreCase("GLABEL")) {
            setType(GLABEL);
        } else if (_s.equalsIgnoreCase("GSCROLLPANE")) {
            setType(GSCROLLPANE);
        } else if (_s.equalsIgnoreCase("GCOMBOBOX")) {
            setType(GCOMBOBOX);
        } else if (_s.equalsIgnoreCase("GTABLE")) {
            setType(GTABLE);
        } else if (_s.equalsIgnoreCase("IMAGEPANEL")) {
            setType(IMAGEPANEL);
        } else if (_s.equalsIgnoreCase("GURL")) {
            setType(GURL);
        } else if (_s.equalsIgnoreCase("GWEBPAGE")) {
            setType(GWEBPAGE);
        } else if (_s.equalsIgnoreCase("JSCROLLPANE")) {
            setType(JSCROLLPANE);
        } else if (_s.equalsIgnoreCase("JCOMPONENT")) {
            setType(JCOMPONENT);
        } else if (_s.equalsIgnoreCase("COLLAPSEPANE")) {
            setType(COLLAPSEPANE);
        } else if (_s.equalsIgnoreCase("MENU")) {
            setType(MENU);
        } else if (_s.equalsIgnoreCase("MENUITEM")) {
            setType(MENUITEM);
        } else if (_s.equalsIgnoreCase("JSPLITPANE")) {
            setType(JSPLITPANE);
        } else {
            appendLog("FormItem.Type: " + _s + " is undefined.");
        }
        return;
    }

    /**
     * getType
     * @return
     * @author Anthony C. Liberto
     */
    protected int getType() {
        return type;
    }

    /**
     * setName
     * @param _s
     * @author Anthony C. Liberto
     */
    protected void setName(String _s) {
        name = _s;
    }

    /**
     * getName
     * @return
     * @author Anthony C. Liberto
     */
    protected String getName() {
        return name;
    }

    /**
     * setShortCut
     * @param _s
     * @author Anthony C. Liberto
     */
    protected void setShortCut(String _s) {
        Character c = new Character(_s.charAt(0));
        misc.put("SHORT_CUT", c);
    }

    /**
     * getShortCut
     * @return
     * @author Anthony C. Liberto
     */
    private Character getShortCut() {
        if (misc.containsKey("SHORT_CUT")) {
            return ((Character) misc.get("SHORT_CUT"));
        }
        return null;
    }

    /**
     * setKeyStroke
     * @param _s
     * @author Anthony C. Liberto
     */
    protected void setKeyStroke(String _s) {
        KeyStroke stroke = KeyStroke.getKeyStroke(_s);
        misc.put("KEY_STROKE", stroke);
    }

    /**
     * getKeyStroke
     * @return
     * @author Anthony C. Liberto
     */
    private KeyStroke getKeyStroke() {
        if (misc.containsKey("KEY_STROKE")) {
            return (KeyStroke) misc.get("KEY_STROKE");
        }
        return null;
    }

    /**
     * setMethod
     * @param _method
     * @author Anthony C. Liberto
     */
    protected void setMethod(FormMethod _method) {
        misc.put("METHOD", _method);
    }

    /**
     * getMethod
     * @return
     * @author Anthony C. Liberto
     */
    private FormMethod getMethod() {
        if (misc.containsKey("METHOD")) {
            return (FormMethod) misc.get("METHOD");
        }
        return null;
    }

    /**
     * addColumn
     * @param _v
     * @author Anthony C. Liberto
     * /
    public void setColumn(Vector _v) {
        column = _v;
    }*/

    /**
     * getColumnCount
     * @return
     * @author Anthony C. Liberto
     * /
    public int getColumnCount() {
        return column.size();
    }*/

    /**
     * addColumn
     * @param _o
     * @author Anthony C. Liberto
     */
    protected void addColumn(Object[] _o) {
        int ii = _o.length;
        for (int i = 0; i < ii; ++i) {
            addColumn(_o[i]);
        }
    }

    /**
     * addColumn
     * @param _o
     * @author Anthony C. Liberto
     */
    private void addColumn(Object _o) {
        column.add(_o);
    }

    /**
     * getColumnAt
     * @param _i
     * @return
     * @author Anthony C. Liberto
     * /
    public Object getColumnAt(int _i) {
        return column.get(_i);
    }*/

    /**
     * getColumn
     * @return
     * @author Anthony C. Liberto
     * /
    public Vector getColumn() {
        return column;
    }*/

    /**
     * setContents
     * @param _o
     * @author Anthony C. Liberto
     */
    protected void setContents(Vector _o) {
        contents = _o;
    }

    /**
     * getContents
     * @return
     * @author Anthony C. Liberto
     */
    protected Vector getContents() {
        return contents;
    }

    /**
     * addContents
     * @param _o
     * @author Anthony C. Liberto
     */
    protected void addContents(Object[] _o) {
        int ii = _o.length;
        for (int i = 0; i < ii; ++i) {
            addContents(_o[i]);
        }
    }

    /**
     * addContents
     * @param _o
     * @author Anthony C. Liberto
     */
    protected void addContents(Object _o) {
        contents.add(_o);
    }

    /**
     * getContentsAt
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    protected Object getContentsAt(int _i) {
        Object o = contents.get(_i);
        if (o != null && o instanceof NavCartDialog) {
            ((NavCartDialog) o).setOnForm(true);
        }
        return o;
    }

    /**
     * getContentsSize
     * @return
     * @author Anthony C. Liberto
     */
    protected int getContentsSize() {
        return contents.size();
    }

    /**
     * setX
     * @param _i
     * @author Anthony C. Liberto
     */
    protected void setX(int _i) {
        if (position != null) {
            position.gridx = _i;
        }
    }

    /**
     * getX
     * @return
     * @author Anthony C. Liberto
     * /
    public int getX() {
        if (position != null) {
            return position.gridx;
        }
        return 0;
    }*/

    /**
     * setY
     * @param _i
     * @author Anthony C. Liberto
     */
    protected void setY(int _i) {
        if (position != null) {
            position.gridy = _i;
        }
    }

    /**
     * getY
     * @return
     * @author Anthony C. Liberto
     * /
    public int getY() {
        if (position != null) {
            return position.gridy;
        }
        return 0;
    }*/

    /**
     * setPadX
     * @param _i
     * @author Anthony C. Liberto
     */
    protected void setPadX(int _i) {
        if (position != null) {
            position.ipadx = _i;
        }
    }

    /**
     * getPadX
     * @return
     * @author Anthony C. Liberto
     * /
    public int getPadX() {
        if (position != null) {
            return position.ipadx;
        }
        return 0;
    }*/

    /**
     * setPadY
     * @param _i
     * @author Anthony C. Liberto
     */
    protected void setPadY(int _i) {
        if (position != null) {
            position.ipady = _i;
        }
    }

    /**
     * getPadY
     * @return
     * @author Anthony C. Liberto
     * /
    public int getPadY() {
        if (position != null) {
            return position.ipady;
        }
        return 0;
    }*/

    /**
     * setGridHeight
     * @param _i
     * @author Anthony C. Liberto
     */
    protected void setGridHeight(int _i) {
        if (position != null) {
            position.gridheight = _i;
        }
    }

    /**
     * getGridHeight
     * @return
     * @author Anthony C. Liberto
     * /
    public int getGridHeight() {
        if (position != null) {
            return position.gridheight;
        }
        return 1;
    }*/

    /**
     * setGridWidth
     * @param _i
     * @author Anthony C. Liberto
     */
    protected void setGridWidth(int _i) {
        if (position != null) {
            position.gridwidth = _i;
        }
    }

    /**
     * getGridWidth
     * @return
     * @author Anthony C. Liberto
     * /
    public int getGridWidth() {
        if (position != null) {
            return position.gridwidth;
        }
        return 1;
    }*/

    /**
     * setWeightX
     * @param _i
     * @author Anthony C. Liberto
     * /
    public void setWeightX(double _i) {
        if (position != null) {
            position.weightx = _i;
        }
    }*/

    /**
     * getWeightX
     * @return
     * @author Anthony C. Liberto
     * /
    public double getWeightX() {
        if (position != null) {
            return position.weightx;
        }
        return 0;
    }*/

    /**
     * setWeightY
     * @param _i
     * @author Anthony C. Liberto
     * /
    public void setWeightY(double _i) {
        if (position != null) {
            position.weighty = _i;
        }
    }*/

    /**
     * getWeightY
     * @return
     * @author Anthony C. Liberto
     * /
    public double getWeightY() {
        if (position != null) {
            return position.weighty;
        }
        return 0;
    }*/

    /**
     * setAnchor
     * @param _i
     * @author Anthony C. Liberto
     */
    protected void setAnchor(int _i) {
        if (position != null) {
            position.anchor = _i;
        }
    }

    /**
     * getAnchor
     * @return
     * @author Anthony C. Liberto
     * /
    public int getAnchor() {
        if (position != null) {
            return position.anchor;
        }
        return 0;
    }*/

    /**
     * setFill
     * @param _i
     * @author Anthony C. Liberto
     */
    protected void setFill(int _i) {
        if (position != null) {
            position.fill = _i;
        }
    }

    /**
     * getFill
     * @return
     * @author Anthony C. Liberto
     * /
    public int getFill() {
        if (position != null) {
            return position.fill;
        }
        return 0;
    }*/

    /**
     * setImageIcon
     * @param _icon
     * @author Anthony C. Liberto
     * /
    public void setImageIcon(ImageIcon _icon) {
        misc.put("IMAGE_ICON", _icon);
    }*/

    /**
     * setImageIcon
     * @param _s
     * @author Anthony C. Liberto
     */
    protected void setImageIcon(String _s) {
        ImageIcon icon = getImageIcon(_s);
        misc.put("IMAGE_ICON", icon);
    }

    /**
     * getImageIcon
     * @return
     * @author Anthony C. Liberto
     */
    private ImageIcon getImageIcon() {
        if (misc.containsKey("IMAGE_ICON")) {
            return (ImageIcon) misc.get("IMAGE_ICON");
        }
        return null;
    }

    /**
     * setImage
     * @param _image
     * @author Anthony C. Liberto
     * /
    public void setImage(Image _image) {
        misc.put("IMAGE", _image);
    }*/

    /**
     * setImage
     * @param _s
     * @author Anthony C. Liberto
     */
    protected void setImage(String _s) {
        Image picture = getImage(_s);
        misc.put("IMAGE", picture);
    }

    /**
     * getImage
     * @return
     * @author Anthony C. Liberto
     */
    private Image getImage() {
        if (misc.containsKey("IMAGE")) {
            return (Image) misc.get("IMAGE");
        }
        return null;
    }

    /**
     * setSize
     * @param _d
     * @author Anthony C. Liberto
     */
    protected void setSize(Dimension _d) {
        misc.put("SIZE", _d);
    }

    /**
     * setSize
     * @param _w
     * @param _h
     * @author Anthony C. Liberto
     * /
    public void setSize(int _w, int _h) {
        Dimension d = new Dimension(_w, _h);
        setSize(d);
    }*/

    /**
     * getSize
     * @return
     * @author Anthony C. Liberto
     */
    private Dimension getSize() {
        if (misc.containsKey("SIZE")) {
            return (Dimension) misc.get("SIZE");
        }
        return null;
    }

    /**
     * setText
     * @param _s
     * @author Anthony C. Liberto
     */
    protected void setText(String _s) {
        String txt = new String(_s);
        misc.put("TEXT", txt);
    }

    /**
     * getText
     * @return
     * @author Anthony C. Liberto
     */
    private String getText() {
        if (misc.containsKey("TEXT")) {
            return (String) misc.get("TEXT");
        }
        return null;
    }

    /**
     * setToolTip
     * @param _s
     * @author Anthony C. Liberto
     */
    protected void setToolTip(String _s) {
        String toolTip = new String(_s);
        misc.put("TOOL_TIP", toolTip);
    }

    /**
     * getToolTip
     * @return
     * @author Anthony C. Liberto
     */
    private String getToolTip() {
        if (misc.containsKey("TOOL_TIP")) {
            return (String) misc.get("TOOL_TIP");
        }
        return null;
    }

    /**
     * setNumberClicks
     * @param _i
     * @author Anthony C. Liberto
     */
    protected void setNumberClicks(int _i) {
        clicks = _i;
    }

    /**
     * getNumberClicks
     * @return
     * @author Anthony C. Liberto
     * /
    public int getNumberClicks() {
        return clicks;
    }*/

    /**
     * clearConstraints
     * @author Anthony C. Liberto
     */
    protected void clearConstraints() {
        position = null;
    }

    /**
     * setLayout
     * @param _s
     * @author Anthony C. Liberto
     */
    protected void setLayout(String[] _s) {
        if (_s[0].equalsIgnoreCase("BORDER")) {
            layout = new BorderLayout();

        } else if (_s[0].equalsIgnoreCase("GRIDBAG")) {
            layout = new GridBagLayout();

        } else if (_s[0].equalsIgnoreCase("GRID")) {
            int len = _s.length;
            if (len == 3) {
                layout = new GridLayout(toInt(_s[1]), toInt(_s[2]));

            } else if (len == 5) {
                layout = new GridLayout(toInt(_s[1]), toInt(_s[2]), toInt(_s[3]), toInt(_s[4]));
            }
        }
    }

    private int toInt(String _s) {
        return Routines.toInt(_s);
    }

    /**
     * getLayout
     * @return
     * @author Anthony C. Liberto
     * /
    public LayoutManager getLayout() {
        return layout;
    }*/

    /**
     * setMisc
     * @param _s
     * @author Anthony C. Liberto
     */
    protected void setMisc(String[] _s) {
        if (_s.length != 2) {
            appendLog("misc length is not 2");
            return;
        }
        misc.put(_s[0].toLowerCase(), _s[1]);
    }

    /**
     * getMisc
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    protected Object getMisc(String _s) {
        _s = _s.toLowerCase();
        if (misc.containsKey(_s)) {
            return misc.get(_s);
        }
        return null;
    }

    /**
     * setImagePanelType
     * @param _i
     * @author Anthony C. Liberto
     */
    protected void setImagePanelType(int _i) {
        Integer i = new Integer(_i);
        misc.put("IMAGE_PANEL_TYPE", i);
    }

    /**
     * getImagePanelType
     * @return
     * @author Anthony C. Liberto
     */
    private int getImagePanelType() {
        if (misc.containsKey("IMAGE_PANEL_TYPE")) {
            return ((Integer) misc.get("IMAGE_PANEL_TYPE")).intValue();
        }
        return 0;
    }

    /**
     * setBackground
     * @param c
     * @author Anthony C. Liberto
     */
    protected void setBackground(Color c) {
        backColor = c;
    }

    /**
     * getBackground
     * @return
     * @author Anthony C. Liberto
     * /
    public Color getBackground() {
        return backColor;
    }*/

    /**
     * setForeground
     * @param c
     * @author Anthony C. Liberto
     */
    protected void setForeground(Color c) {
        foreColor = c;
    }

    /**
     * getForeground
     * @return
     * @author Anthony C. Liberto
     * /
    public Color getForeground() {
        return foreColor;
    }*/

    /**
     * setOpaque
     * @param _b
     * @author Anthony C. Liberto
     */
    protected void setOpaque(boolean _b) {
        misc.put("OPAQUE", new Boolean(_b));
    }

    /**
     * isOpaque
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isOpaque() {
        if (misc.containsKey("OPAQUE")) {
            return ((Boolean) misc.get("OPAQUE")).booleanValue();
        }
        return true;
    }

    /**
     * setEnabled
     * @param _b
     * @author Anthony C. Liberto
     */
    protected void setEnabled(boolean _b) {
        misc.put("ENABLED", new Boolean(_b));
    }

    /**
     * isEnabled
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isEnabled() {
        if (misc.containsKey("ENABLED")) {
            return ((Boolean) misc.get("ENABLED")).booleanValue();
        }
        return true;
    }

    /**
     * setVisible
     * @param _b
     * @author Anthony C. Liberto
     */
    protected void setVisible(boolean _b) {
        misc.put("VISIBLE", new Boolean(_b));
    }

    /**
     * isVisible
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isVisible() {
        if (misc.containsKey("VISIBLE")) {
            return ((Boolean) misc.get("VISIBLE")).booleanValue();
        }
        return true;
    }

    /**
     * setJComponent
     * @param _o
     * @author Anthony C. Liberto
     */
    protected void setJComponent(Object _o) {
        if (_o instanceof JComponent) {
            jComp = (JComponent) _o;
        } else if (_o instanceof FormMethod) {
            Object o = ((FormMethod) _o).invoke();
            if (o instanceof JComponent) {
                jComp = (JComponent) o;
            }
        }

        if (jComp != null) { //20011105
            setVisible(jComp.isVisible()); //20011105
            setEnabled(jComp.isEnabled()); //20011105
        } //20011105
    }

    /**
     * getObject
     * @return
     * @author Anthony C. Liberto
     */
    protected Object getObject() {
        Dimension size = getSize();
        String toolTip = getToolTip();
        ImageIcon icon = getImageIcon();
        Image picture = getImage();
        String txt = getText();
        Character shortCut = getShortCut();
        KeyStroke stroke = getKeyStroke();
        FormMethod method = getMethod();
        boolean opaque = isOpaque();
        boolean visible = isVisible();
        boolean enabled = isEnabled();
        EButton button = null;
        ELabel label = null;
        EComboBox combo = null;
        JTable table = null;
        EImagePanel iPanel = null;
        FormPanel collPane = null;
        JMenu menu = null;
        JMenuItem menuItem = null;
        ESplitPane split = null;
        EPanel panel = null;
        Object o = null;
        if (object != null) {
            return object;
        }

        switch (type) {
        case JCOMPONENT :
            if (jComp == null) {
                break;
            }
            if (size != null) {
                jComp.setSize(size);
                jComp.setPreferredSize(size);
            }
            jComp.setOpaque(opaque);
            jComp.setEnabled(enabled);
            jComp.setVisible(visible);
            jComp.setMinimumSize(UIManager.getDimension("eannounce.minimum"));
            object = jComp;
            break;
        case GBUTTON :
            if (icon != null && txt != null) {
                button = new EButton(txt, icon);

            } else if (icon != null) {
                button = new EButton(icon);

            } else if (txt != null) {
                button = new EButton(txt);
            }
            button.addActionListener(this);
            button.setName(name);
            button.setOpaque(opaque);
            button.setEnabled(enabled);
            button.setVisible(visible);
            if (shortCut != null) {
                button.setMnemonic(shortCut.charValue());
            }
            if (size != null) {
                button.setPreferredSize(size);
                button.setSize(size);
            }
            if (toolTip != null) {
                button.setToolTipText(toolTip);
            }
            button.setMinimumSize(UIManager.getDimension("eannounce.minimum"));
            object = button;
            break;
        case GPANEL :
            panel = new EPanel(layout);
            panel.setOpaque(opaque);
            panel.setName(name);
            panel.setEnabled(enabled);
            panel.setVisible(visible);
            if (size != null) {
                panel.setPreferredSize(size);
                panel.setSize(size);
            }
            if (toolTip != null) {
                panel.setToolTipText(toolTip);
            }
            panel.setMinimumSize(UIManager.getDimension("eannounce.minimum"));
            object = panel;
            break;
        case GLABEL :
            if (icon != null && txt != null) {
                label = new ELabel(txt, icon, SwingConstants.LEFT);

            } else if (icon != null) {
                label = new ELabel(icon);

            } else if (txt != null) {
                label = new ELabel(txt);
            }
            if (method != null) {
                label.addMouseListener(this);
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            if (size != null) {
                label.setPreferredSize(size);
                label.setSize(size);
            }
            if (toolTip != null) {
                label.setToolTipText(toolTip);
            }
            label.setName(name);
            label.setOpaque(opaque);
            label.setEnabled(enabled);
            label.setVisible(visible);
            label.setMinimumSize(UIManager.getDimension("eannounce.minimum"));
            object = label;
            break;
        case GSCROLLPANE :
            o = contents.get(0);
            if (o instanceof FormItem) {
                o = ((FormItem) o).getObject();
            }
            if (o instanceof Component) {
                EScrollPane gsp = new EScrollPane((Component) o);
                gsp.setName(name);
                if (size != null) {
                    gsp.setPreferredSize(size);
                    gsp.setSize(size);
                }
                if (toolTip != null) {
                    gsp.setToolTipText(toolTip);
                }
                gsp.setOpaque(opaque);
                gsp.setEnabled(enabled);
                gsp.setVisible(visible);
                gsp.setMinimumSize(UIManager.getDimension("eannounce.minimum"));
                object = gsp;
            }
            break;
        case GCOMBOBOX :
            combo = new EComboBox(contents);
            combo.addActionListener(this);
            combo.setName(name);
            if (size != null) {
                combo.setPreferredSize(size);
                combo.setSize(size);
            }
            if (toolTip != null) {
                combo.setToolTipText(toolTip);
            }
            combo.setOpaque(opaque);
            combo.setEnabled(enabled);
            combo.setVisible(visible);
            combo.setMinimumSize(UIManager.getDimension("eannounce.minimum"));
            object = combo;
            break;
        case GTABLE :
            table = new JTable(contents, column);
            table.setName(name);
            table.setOpaque(opaque);
            table.setEnabled(enabled);
            table.setVisible(visible);
            if (size != null) {
                table.setPreferredSize(size);
                table.setSize(size);
            }
            table.setMinimumSize(UIManager.getDimension("eannounce.minimum"));
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            object = table;
            break;
        case IMAGEPANEL :
            iPanel = new EImagePanel(layout, picture, getImagePanelType());
            if (size != null) {
                iPanel.setPreferredSize(size);
                iPanel.setSize(size);
            } else if (picture != null) {
                iPanel.pack();
            }
            if (toolTip != null) {
                iPanel.setToolTipText(toolTip);
            }
            iPanel.setName(name);
            iPanel.setOpaque(opaque);
            iPanel.setEnabled(enabled);
            iPanel.setVisible(visible);
            iPanel.setMinimumSize(UIManager.getDimension("eannounce.minimum"));
            object = iPanel;
            break;
        case GURL :
            break;
        case GWEBPAGE :
            break;
        case JSCROLLPANE :
            o = contents.get(0);
            if (o instanceof FormItem) {
                o = ((FormItem) o).getObject();
            }
            if (o instanceof Component) {
                EScrollPane jsp = new EScrollPane((Component) o);
                jsp.setName(name);
                if (size != null) {
                    jsp.setPreferredSize(size);
                    jsp.setSize(size);
                }
                if (toolTip != null) {
                    jsp.setToolTipText(toolTip);
                }
                jsp.setOpaque(opaque);
                jsp.setEnabled(enabled);
                jsp.setVisible(visible);
                jsp.setMinimumSize(UIManager.getDimension("eannounce.minimum"));
                object = jsp;
            }
            break;
        case COLLAPSEPANE :
            collPane = new FormPanel();
            collPane.setTitle(txt, nls);
            collPane.setName(name);
            if (size != null) {
                collPane.setPreferredSize(size);
                collPane.setSize(size);
            }
            if (toolTip != null) {
                collPane.setToolTipText(toolTip);
            }
            collPane.setOpaque(opaque);
            collPane.setEnabled(enabled);
            collPane.setVisible(visible);
            collPane.setMinimumSize(UIManager.getDimension("eannounce.minimum"));
            object = collPane;
            break;
        case MENU :
            menu = new JMenu();
            menu.setText(txt);
            menu.setName(name);
            menu.setEnabled(enabled);
            menu.setVisible(visible);
            if (shortCut != null) {
                menu.setMnemonic(shortCut.charValue());
            }
            object = menu;
            break;
        case MENUITEM :
            menuItem = new JMenuItem();
            menuItem.setText(txt);
            menuItem.setName(name);
            menuItem.addActionListener(this);
            menuItem.setEnabled(enabled);
            menuItem.setVisible(visible);
            if (shortCut != null) {
                menuItem.setMnemonic(shortCut.charValue());
            }
            if (stroke != null) {
                menuItem.setAccelerator(stroke);
            }
            object = menuItem;
            break;
        case JSPLITPANE :
            if (jComp instanceof ESplitPane) {
                split = (ESplitPane) jComp;
            } else {
                split = new ESplitPane();
                split.setOrientation(getImagePanelType());
            }

            split.setName(name);
            split.setOpaque(opaque);
            split.setEnabled(enabled);
            split.setVisible(visible);
            split.setMinimumSize(UIManager.getDimension("eannounce.minimum"));
            object = split;
            break;
        default:
            break;
        }
        return object;
    }

    /**
     * getConstraints
     * @return
     * @author Anthony C. Liberto
     */
    protected GridBagConstraints getConstraints() {
        return position;
    }

    /**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseEntered(MouseEvent me) {
    }
    /**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseExited(MouseEvent me) {
    }
    /**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mousePressed(MouseEvent me) {
    }
    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseReleased(MouseEvent me) {
    }

    /**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseClicked(MouseEvent me) {
        FormMethod method = getMethod();
        if (method == null || method.isEmpty()) {
            return;
        }
        if (clicks == me.getClickCount()) {
            method.invoke();
        }
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent ae) {
        FormMethod method = getMethod();
        if (method == null || method.isEmpty()) {
            return;
        }
        method.invoke();
    }

}
