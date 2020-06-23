//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.navform;

import com.ibm.eacm.objects.EACMGlobals;

import javax.swing.*;
import java.awt.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Wendy Stimpson
 */
// $Log: FormItem.java,v $
// Revision 1.3  2013/07/22 18:32:22  wendy
// Make navigation splitpane divider easier to grab
//
// Revision 1.2  2013/07/18 18:28:40  wendy
// fix compiler warnings
//
// Revision 1.1  2012/09/27 19:39:25  wendy
// Initial code
//
public class FormItem extends FormTag implements EACMGlobals
{
	private static final int JCOMPONENT = 0; 
	private static final int GPANEL = 1; 
	private static final int JSPLITPANE = 2;
	private static final int GLABEL = 3; 

    private int type = -1;

    private LayoutManager layout = new GridBagLayout();
    private Object object = null;
    private JComponent jComp = null;

    private Vector<Object> contents = new Vector<Object>();

    private HashMap<String, Object> misc = new HashMap<String, Object>();

	/* (non-Javadoc)
	 * @see com.ibm.eacm.navform.FormTag#dereference()
	 */
	protected void dereference(){
		super.dereference();
		layout = null;
		object = null;
		jComp = null;
		contents.clear();
		contents = null;
		misc.clear();
		misc = null;
	}

	/**
	 * formItem
	 * @param s
	 */
	protected FormItem(String s) {
		if (s.equalsIgnoreCase("GPANEL")) {
			type = GPANEL;
		} else if (s.equalsIgnoreCase("GLABEL")) {
			type = GLABEL;
		}else if (s.equalsIgnoreCase("JCOMPONENT")) {
			type = JCOMPONENT;
		}else if (s.equalsIgnoreCase("JSPLITPANE")) {
			type = JSPLITPANE;
		} else {
			Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,"FormItem.Type: " + s + " is undefined.");
		}
	}

    /**
     * getContents
     * @return
     */
    protected Vector<Object> getContents() {
        return contents;
    }

    /**
     * addContents
     * @param o
     */
    protected void addContents(Object[] o) {
        for (int i = 0; i < o.length; ++i) {
        	 contents.add(o[i]);
        }
    }

    /**
     * getContentsAt
     * @param i
     * @return
     */
    protected Object getContentsAt(int i) {
        return contents.get(i);
    }

    /**
     * setText
     * @param s
     * 
     */
    protected void setText(String s) {
        misc.put("TEXT", s);
    }

    /**
     * getText
     * @return
     * 
     */
    private String getText() {
        if (misc.containsKey("TEXT")) {
            return (String) misc.get("TEXT");
        }
        return null;
    }


    /**
     * setLayout
     * @param s
     * 
     */
    protected void setLayout(String[] s) {
        if (s[0].equalsIgnoreCase("BORDER")) {
            layout = new BorderLayout();
        }
    }

    /**
     * setMisc
     * @param s
     * 
     */
    protected void setMisc(String[] s) {
        if (s.length != 2) {
        	Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,"misc length is not 2");
            return;
        }
        misc.put(s[0].toLowerCase(), s[1]);
    }

    /**
     * getMisc
     * @param s
     * @return
     * 
     */
    protected Object getMisc(String s) {
        s = s.toLowerCase();
        if (misc.containsKey(s)) {
            return misc.get(s);
        }
        return null;
    }

    /**
     * setImagePanelType
     * @param i
     * 
     */
    protected void setImagePanelType(int i) {
        misc.put("IMAGEPANELTYPE", new Integer(i));
    }

    /**
     * getImagePanelType
     * @return
     * 
     */
    private int getImagePanelType() {
        if (misc.containsKey("IMAGEPANELTYPE")) {
            return ((Integer) misc.get("IMAGEPANELTYPE")).intValue();
        }
        return 0;
    }

    /**
     * setOpaque
     * @param b
     * 
     */
    protected void setOpaque(boolean b) {
        misc.put("OPAQUE", new Boolean(b));
    }

    /**
     * isOpaque
     * @return
     * 
     */
    private boolean isOpaque() {
        if (misc.containsKey("OPAQUE")) {
            return ((Boolean) misc.get("OPAQUE")).booleanValue();
        }
        return true;
    }

    /**
     * setEnabled
     * @param b
     * 
     */
    private void setEnabled(boolean b) {
        misc.put("ENABLED", new Boolean(b));
    }

    /**
     * isEnabled
     * @return
     * 
     */
    private boolean isEnabled() {
        if (misc.containsKey("ENABLED")) {
            return ((Boolean) misc.get("ENABLED")).booleanValue();
        }
        return true;
    }

    /**
     * setVisible
     * @param b
     * 
     */
    private void setVisible(boolean b) {
        misc.put("VISIBLE", new Boolean(b));
    }

    /**
     * isVisible
     * @return
     * 
     */
    private boolean isVisible() {
        if (misc.containsKey("VISIBLE")) {
            return ((Boolean) misc.get("VISIBLE")).booleanValue();
        }
        return true;
    }

    /**
     * setJComponent
     * @param o
     * 
     */
    protected void setJComponent(Object o) {
        if (o instanceof JComponent) {
            jComp = (JComponent) o;
        } else if (o instanceof FormMethod) {
            Object o2 = ((FormMethod) o).invoke();
            if (o2 instanceof JComponent) {
                jComp = (JComponent) o2;
            }
        }

        if (jComp != null) { 
            setVisible(jComp.isVisible()); 
            setEnabled(jComp.isEnabled()); 
        } 
    }

    /**
     * getObject
     * @return
     * 
     */
    protected Object getObject() {
      
        String txt = getText();

       // FormMethod method = getMethod();
        boolean opaque = isOpaque();
        boolean visible = isVisible();
        boolean enabled = isEnabled();
       
        JLabel label = null;

        JSplitPane split = null;
        JPanel panel = null;
      
        if (object != null) {
            return object;
        }

        switch (type) {
        case JCOMPONENT :
            if (jComp != null) {     
            	jComp.setOpaque(opaque);
            	jComp.setEnabled(enabled);
            	jComp.setVisible(visible);
            	jComp.setMinimumSize(UIManager.getDimension("eannounce.minimum"));
            	object = jComp;
            }
            break;
        case GPANEL :
            panel = new JPanel(layout);
            panel.setOpaque(opaque);
            panel.setName(getName());
            panel.setEnabled(enabled);
            panel.setVisible(visible);
            panel.setMinimumSize(UIManager.getDimension("eannounce.minimum"));
            object = panel;
            break;
        case GLABEL :
        	if (txt != null) {
        		label = new JLabel(txt);
        		label.setName(getName());
        		label.setOpaque(opaque);
        		label.setEnabled(enabled);
        		label.setVisible(visible);
        		label.setMinimumSize(UIManager.getDimension("eannounce.minimum"));
        	}
            object = label;
            break; 
        case JSPLITPANE :
            if (jComp instanceof JSplitPane) {
                split = (JSplitPane) jComp;
            } else {
                split = new JSplitPane();
                split.setOrientation(getImagePanelType());
            }

            split.setName(getName());
            split.setOpaque(opaque);
            split.setEnabled(enabled);
            split.setVisible(visible);
            split.setMinimumSize(UIManager.getDimension("eannounce.minimum"));
            object = split;
            
            if(split.getDividerSize()<8){
            	split.setDividerSize(8);//when screen is maximized, it is very hard to grab divider, make it bigger
            }
            break;
        default:
            break;
        }
        return object;
    }
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return super.toString()+" formItem type: " + type;
    }
}
