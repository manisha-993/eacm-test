//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.navform;

import java.awt.*;
import java.awt.event.KeyEvent;

import java.io.StringReader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.preference.BehaviorPref;
import com.ibm.eacm.preference.NavLayoutPref;
import com.ibm.eacm.tabs.NavController;

/**
 * generator for navigate panels
 * @author Wendy Stimpson
 */
//$Log: Generator.java,v $
//Revision 1.3  2014/10/03 11:08:08  wendy
//IN5515352 remove F8 keyboard mapping
//
//Revision 1.2  2013/07/18 18:28:40  wendy
//fix compiler warnings
//
//Revision 1.1  2012/09/27 19:39:25  wendy
//Initial code
//
public class Generator implements EACMGlobals {
	private boolean bValidate = Utils.isArmed(FORM_VALIDATE_ARM_FILE);

    private JPanel mainPane = null;
    private NavController parent = null;

    private HashMap<String, Object> hash = new HashMap<String, Object>();

    /**
     * Generator
     * @param p
     *
     */
    public Generator(NavController p) {
        parent = p;
        mainPane = new JPanel();
        mainPane.setOpaque(false);
        mainPane.setLayout(new GridBagLayout());
    }

    /**
     * get the generated navigate panel
     * @return
     */
    public JPanel getMainPanel(){
    	return mainPane;
    }

    private void put(FormTag fi) {
        String key = fi.getName();
        if (!hash.containsKey(key)) {
        	  hash.put(key, fi);
        }
    }

    private FormMethod getFormMethod(String s) {
    	if (hash.containsKey(s)) {
    		Object o = hash.get(s);
    		if (o instanceof FormMethod) {
    			return (FormMethod) o;
    		}
    	}

        return null;
    }

    private Object getVariableByKey(String s) {
        if (hash.containsKey(s)) {
            Object out = hash.get(s);
            if (out instanceof FormVariable) {
                return ((FormVariable) out).getObject();
            }
            return out;
        }
        return s;
    }

    private Object getVariable(String s) {
        if (s.equalsIgnoreCase("MAINPANEL")) {
            return mainPane;
        } else if (s.equalsIgnoreCase("PARENT")) {
            return parent;
        } else if (s.equalsIgnoreCase("NAVIGATE0")) {
            return parent.getNavigate(0);
        } else if (s.equalsIgnoreCase("NAVIGATE1")) {
            return parent.getNavigate(1);
        } else if (s.startsWith(":")) {
            return s.substring(1);
        } else {
            return getVariableByKey(s);
        }
    }
    private JComponent getComponent(FormItem fi) {
        Object o = fi.getObject();
        if (o instanceof JComponent) {
            return (JComponent) o;
        }
        return null;
    }

    private Object[] getObjectArray(String s) {
        StringTokenizer st = new StringTokenizer(s, ",");
        Vector<Object> v = new Vector<Object>();
        Object[] out = null;
        while (st.hasMoreTokens()) {
            String s2 = st.nextToken().trim();
            if (Routines.have(s2)) {
                v.add(getVariable(s2));
            }
        }

        out = new Object[v.size()];
        v.copyInto(out);

        return out;
    }

    private void populateBorder(BorderLayout lx, JPanel p, Vector<?> v) {
        for (int i = 0; i < v.size(); ++i) {
            Object o = v.get(i);
            if (o instanceof FormItem) {
                FormItem fi = (FormItem) o;
                JComponent comp = getComponent(fi);
                String loc = (String) fi.getMisc("borderlocation");
                if (loc != null) {
                    p.add(loc, comp);
                } else {
                	Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING," missing borderlocation");
                }
            }
        }
        Dimension d = p.getPreferredSize();
        p.setSize(d);
    }

    private void populatePanel(LayoutManager l, JPanel p, FormItem fi) {
        if (l instanceof BorderLayout) {
            populateBorder((BorderLayout) l, p, fi.getContents());
        }
    }

    private void populatePanel(FormItem fi, JComponent comp) {
      if (comp instanceof JPanel) {
        	JPanel pnl = (JPanel) comp;
            LayoutManager layout = pnl.getLayout();
            populatePanel(layout, pnl, fi);
        }
    }

    private void populateMainBorder(BorderLayout l, JPanel p, FormItem fi) {
        String loc = (String) fi.getMisc("borderlocation");
        if (loc != null) {
            mainPane.add(loc, p);
        } else {
        	Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING," missing borderlocation");
        }
    }

    private void populateMainPanel(LayoutManager l, JPanel p, FormItem fi) {
        if (l instanceof BorderLayout) {
            populateMainBorder((BorderLayout) l, p, fi);
        }
    }

    private void populateMainPanel(LayoutManager layout, FormItem fi) {
        JComponent comp = getComponent(fi);
        if (comp instanceof JPanel) {
        	JPanel pnl = (JPanel) comp;
            populateMainPanel(layout, pnl, fi);
        }
    }

    private void putPanel(FormItem fi) {
        JComponent jComp = getComponent(fi);
        populatePanel(fi, jComp);
        hash.put(fi.getName(), fi);
    }

    private void putSplitPane(FormItem fi) {
        JSplitPane jsp = (JSplitPane) getComponent(fi);
        jsp.setLeftComponent(getComponent(fi.getContentsAt(0)));
        jsp.setRightComponent(getComponent(fi.getContentsAt(1)));

        jsp.setResizeWeight(.5D);
        int location = BehaviorPref.getNavDividerLocation();
        if(location != -1){
        	jsp.setDividerLocation(location);//.5D);
        }else{
         	jsp.setDividerLocation(0.5D);	
        }

        jsp.setOneTouchExpandable(true);
        jsp.setContinuousLayout(true);
        jsp.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, parent);

		//IN5515352 remove F8 keyboard mapping
		KeyStroke keyToRemove = KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0);
		Utils.removeKeyBoardMapping(jsp, keyToRemove);
		
        hash.put(fi.getName(), fi);
    }

    private Component getComponent(Object o) {
        if (o instanceof FormItem) {
            return getComponent((FormItem) o);

        } else if (o instanceof Component) {
            return (Component) o;
        }
        return null;
    }

    private FormItem processFormItem(String attribute, String value, FormItem fi) {
    	if (attribute.equalsIgnoreCase("NAME")) {
    		fi.setName(value);
    	} else if (attribute.equalsIgnoreCase("OPAQUE")) {
    		fi.setOpaque(Routines.getBoolean(value));
    	} else if (attribute.equalsIgnoreCase("LAYOUT")) {
    		fi.setLayout(Routines.getStringArray(value, ","));
    	} else if (attribute.equalsIgnoreCase("MISC")) {
    		fi.setMisc(Routines.getStringArray(value, ","));
    	} else if (attribute.equalsIgnoreCase("JSPLITPANELTYPE")) {
    		fi.setImagePanelType(Routines.getInt(value));
    	} else if (attribute.equalsIgnoreCase("TEXT")) {
    		fi.setText(value);
    	} else if (attribute.equalsIgnoreCase("COMPONENT")) {
    		fi.setJComponent(getVariable(value));
    	} else if (attribute.equalsIgnoreCase("ADD")) {
    		fi.addContents(getObjectArray(value));
    	} else {
    		Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,"(" + attribute + ") is undefined.");
    	}
    	return fi;
    }

    /**
     * navigate section
     */
    private void processNavigate(Attributes atts) {
        FormItem fi = new FormItem("JCOMPONENT"); // force NAVIGATE to be a JCOMPONENT to use it as a panel
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                fi = processFormItem(atts.getLocalName(i), atts.getValue(i), fi);
            }
        }

        putPanel(fi); //use Navigate JPanel
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
        putPanel(fi);
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

        put(fi);
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

        put(fi);
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

        putSplitPane(fi);
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

    private void processDo(String name, String val) {
        if (name.equalsIgnoreCase("method")) {
            FormMethod fm = getFormMethod(val);
            if (fm != null) {
                fm.invoke();
            }
        }
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
            fm.setParms(Routines.getStringArray(value, ","));
        } else if (attribute.equalsIgnoreCase("ARGS")) {
            fm.setArgs(getObjectArray(value));
        } else if (attribute.equalsIgnoreCase("OBJECT")) {
            fm.setObject(getVariable(value));
        } else if (attribute.equalsIgnoreCase("METHOD")) {
            fm.setMethod(value);
        } else {
        	Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,"(" + attribute + ") is undefined.");
        }
        return fm;
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
        } else if (attribute.equalsIgnoreCase("VALUE")) {
            fv.setValue(value);
        } else {
        	Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,attribute + " is undefined.");
        }
        return fv;
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
            String[] s = Routines.getStringArray(value, ",");
            if (s[0].equalsIgnoreCase("BORDER")) {
                mainPane.setLayout(new BorderLayout());
            }
        } else if (attribute.equalsIgnoreCase("ADD")) {
            LayoutManager layout = mainPane.getLayout();
            Object[] o = getObjectArray(value);
            for (int i = 0; i < o.length; ++i) {
                if (o[i] instanceof FormItem) {
                    populateMainPanel(layout, (FormItem) o[i]);
                }
            }
        }
    }

    /**
     * process the specified tag
     * @param tagName
     * @param atts
     */
    private void processElement(String tagName, Attributes atts) {
        if (tagName.equalsIgnoreCase("NAVIGATE")) {
            processNavigate(atts);
        }  else if (tagName.equalsIgnoreCase("GPANEL")) {
            processGPanel(atts);
        } else if (tagName.equalsIgnoreCase("GLABEL")) {
            processGLabel(atts);
        }  else if (tagName.equalsIgnoreCase("JCOMPONENT")) {
            processJComponent(atts);
        } else if (tagName.equalsIgnoreCase("JSPLITPANE")) {
            processSplitPane(atts);
        } else if (tagName.equalsIgnoreCase("DO")) {
            processDo(atts);
        } else if (tagName.equalsIgnoreCase("METHOD")) {
            processMethod(atts);
        } else if (tagName.equalsIgnoreCase("VARIABLE")) {
            processVariable(atts);
        } else if (tagName.equalsIgnoreCase("MAINPANE")) {
            processMainPane(atts);
        }else {
        	if (!tagName.startsWith("eacmForm")){
        		Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,tagName + " is undefined.");
        	}
        }
    }

    /**
     * the parser(s)
     */
    private String getAttsString(Attributes atts) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < atts.getLength(); ++i) {
            sb.append("    " + atts.getLocalName(i) + "=" + atts.getValue(i));
        }
        return sb.toString();
    }

    /**
     * read the xml and generate the main panel
     *
     */
    private long starttime;
    public void generateForm() {
        DefaultHandler defaulthandler = new DefaultHandler() {
            public void startElement(String URI, String tagName, String qName, Attributes atts) {
                if (bValidate) {
                	Logger.getLogger(NAV_PKG_NAME).log(Level.FINER,"( " + URI + ", " + tagName + ", " + qName + ", atts)");
                	Logger.getLogger(NAV_PKG_NAME).log(Level.FINER,getAttsString(atts));
                    starttime =System.currentTimeMillis();
                    Logger.getLogger(TIMING_LOGGER).log(Level.INFO,tagName);
                }

                processElement(tagName, atts);
            }

            public void endElement(String URI, String tagName, String qName) {
                if (bValidate) {
            		Logger.getLogger(NAV_PKG_NAME).log(Level.FINER, URI + ", " + tagName + ", " + qName + ")");
                    Logger.getLogger(TIMING_LOGGER).log(Level.INFO,tagName+" ended "+Utils.getDuration(starttime));
                }
               // nothing needed when element ends
            }
            public void startDocument() {}
            public void endDocument() {}

            public void characters(char[] rgc, int nStart, int nLength) throws SAXException {
                String str = new String(rgc, nStart, nLength);
                if (bValidate) {
                	Logger.getLogger(NAV_PKG_NAME).log(Level.FINER, str);
                }
            }

            public void error(SAXParseException saxparseexception) throws SAXException {
            	Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"Generator exception( " + saxparseexception.toString() + ")");

                throw saxparseexception;
            }
        };
        SAXParser parser = new SAXParser();
        parser.setContentHandler(defaulthandler);

        String fileName = NavLayoutPref.getNavLayout();
        String XML = Utils.readString(RESOURCE_DIRECTORY,fileName,EACM_FILE_ENCODE);

        try {
            parser.parse(new InputSource(new StringReader(XML))); //parses from String
        } catch (Exception x) {
            x.printStackTrace();
            //msg11016.0 = Unable to load requested form {0} at this time.\nPlease contact support for assistance.
            com.ibm.eacm.ui.UI.showFYI(null, Utils.getResource("msg11016.0",fileName));
        } finally {
        	derefFormData();
        	System.gc();
        }
    }
    private void derefFormData(){
    	Iterator<Object> itr = hash.values().iterator();
    	while (itr.hasNext()) {
    		FormTag fb  = (FormTag)itr.next();
    		fb.dereference();
    	}
        hash.clear();
        hash = null;
    }

    /**
     * dereference
     *
     */
    public void dereference() {
        //dont do here mainPane.removeAll();
        mainPane = null;
        parent = null;
    }
}

