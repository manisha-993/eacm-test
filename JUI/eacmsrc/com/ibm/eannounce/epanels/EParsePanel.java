/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EParsePanel.java,v $
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
 * Revision 1.7  2005/09/08 17:59:10  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.6  2005/02/03 21:26:15  tony
 * JTest Format Third Pass
 *
 * Revision 1.5  2005/02/03 19:42:22  tony
 * JTest Third pass
 *
 * Revision 1.4  2005/02/03 16:38:54  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.3  2005/01/28 17:54:19  tony
 * JTest Fromat step 2
 *
 * Revision 1.2  2005/01/26 17:43:25  tony
 * JTest Format Mods
 *
 * Revision 1.1.1.1  2004/02/10 16:59:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2003/04/11 20:02:33  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.epanels;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import java.io.*;
import java.awt.*;
import java.util.*;
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
public class EParsePanel extends EPanel implements EAccessConstants {
	private static final long serialVersionUID = 1L;
	private HashMap m_varMap = new HashMap();
	private Stack m_eStack = new Stack();

	private EComponent curComp = null;

//	Component curComponent
//	Container pContainer
	public void dereference() {
		if (m_eStack!=null){
			m_eStack.clear();
			m_eStack = null;
		}
		if (m_varMap!=null){
			m_varMap.clear();
			m_varMap = null;
		}
		super.dereference();
	}
	/**
     * eParsePanel
     * @author Anthony C. Liberto
     * /
    public EParsePanel() {
		super();
	}

	/**
     * eParsePanel
     * @param _b
     * @author Anthony C. Liberto
     * /
    public EParsePanel(boolean _b) {
		super(_b);
	}

	/**
     * eParsePanel
     * @param _lm
     * @author Anthony C. Liberto
     */
    public EParsePanel(LayoutManager _lm) {
		super(_lm);
	}

	/**
     * eParsePanel
     * @param _lm
     * @param _b
     * @author Anthony C. Liberto
     * /
    public EParsePanel(LayoutManager _lm, boolean _b) {
		super(_lm,_b);
	}*/

	protected void processElement(String _tag, Attributes _atts) {
		if (_tag.equalsIgnoreCase("eComponent")) {
			eComponent(_atts);
		} else if(_tag.equalsIgnoreCase("invoke")) {
			invoke(_atts);
		}
	}

	private void eComponent(Attributes _atts) {
		String strAdd = null;
		String strClass = null;
		for (int i=0;i<_atts.getLength();++i) {
			if (_atts.getType(i).equals("CDATA")) {
				String tag = _atts.getLocalName(i);
				if (tag.equalsIgnoreCase("add")) {
					strAdd = _atts.getValue(i);
				} else if (tag.equalsIgnoreCase("class")) {
					strClass = _atts.getValue(i);
				} else if (tag.equalsIgnoreCase("key")) {
					curComp = new EComponent(_atts.getValue(i));
					m_eStack.push(curComp);
					m_varMap.put(_atts.getValue(i),curComp);
				}
			}
		}
		if (strClass != null) {
			curComp.construct(getValue(strClass).toString());
			addParent(strAdd,curComp);
		}
	}

	private void invoke(Attributes _atts) {
		String name = null;
		Class[] parms = null;
		Object[] args = null;
		Object on = null;
		for (int i=0;i<_atts.getLength();++i) {
			if (_atts.getType(i).equals("CDATA")) {
				invoke(_atts.getLocalName(i),_atts.getValue(i),name,parms,args,on);
			}
		}
		if (on != null) {
			if (on instanceof EComponent) {
				((EComponent)on).invoke(name,parms,args);
			}
		} else {
			curComp.invoke(name,parms,args);
		}
	}

	private void invoke(String _tag, String _value, String _name, Class[] _raClass, Object[] _raArgs, Object _on) {
		if (_tag.equalsIgnoreCase("name")) {
			_name = new String(_value);
		} else if (_tag.equalsIgnoreCase("parms")) {
			_raClass = getClasses(_value);
		} else if (_tag.equalsIgnoreCase("args")) {
			_raArgs = getObjectArray(_value);
		} else if (_tag.equalsIgnoreCase("executeOn")) {
			_on = getVariable(_value);
		}
	}

	private void addParent(String _s, EComponent _eComp) {
		Component comp = null;
        if (_eComp == null) {
			return;
		}
		comp = _eComp.getComponent();
		if (comp != null) {
			/*Container parent = getParentContainer(); this is always null.. so why do it?
			if (parent != null) {
				Object o = getValue(_s);
				if (o == null) {
					parent.add(comp);
				} else if (o instanceof String) {
					parent.add((String)o, comp);
				} else if (o instanceof Integer) {
					parent.add(comp, ((Integer)o).intValue());
				} else if (o instanceof Object[]) {
					Object[] ra = (Object[])o;
					if (ra.length == 2) {
						if (ra[1] instanceof Integer) {
							parent.add(comp, ra[0], ((Integer)ra[1]).intValue());
						}
					}
				} else {
					parent.add(comp,o);
				}
			}*/
		}
	}

	/*private Container getParentContainer() {
		return null;
	}*/

	/**
     * element
     *
     * @author Anthony C. Liberto
     * @param _tag
     * @param _value
     */
    protected void element(String _tag, String _value) {

	}

	private Object[] getObjectArray(String _s) {
		String[] raValue = Routines.getStringArray(_s,",");
		int ii = raValue.length;
		Object[] out = new Object[ii];
		for (int i=0;i<ii;++i) {
			out[i] = getValue(raValue[i]);
		}
		return out;
	}

	private Object getValue(String _s) {
        String[] raValue = null;
		if (_s == null || _s.equalsIgnoreCase("null")) {
			return null;
		}
		raValue = Routines.getStringArray(_s,".");
		if (raValue.length == 2) {
			if(raValue[0].equalsIgnoreCase("variable")) {
				return getVariable(_s);
			} else if (raValue[0].equalsIgnoreCase("string")) {
				return raValue[1];
			} else if (raValue[0].equalsIgnoreCase("color")) {
				return getColor(raValue[1]);
			} else if (raValue[0].equalsIgnoreCase("int")) {
				return Routines.getInteger(raValue[1],-1);
			} else if (raValue[0].equalsIgnoreCase("boolean")) {
				return new Boolean(raValue[1].equalsIgnoreCase("true"));
			} else if (raValue[0].equalsIgnoreCase("class")) {
				return getClass(_s.substring(6));
			}
		}
		return _s;
	}

	/**
     * getClasses
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public Class[] getClasses(String _s) {
		String[] raClass = Routines.getStringArray(_s,",");
		int ii = raClass.length;
		Class[] out = new Class[ii];
		for (int i=0;i<ii;++i) {
			out[i] = getClass(raClass[i]);
		}
		return out;
	}

	private Object getVariable(String _key) {
		if (m_varMap.containsKey(_key)) {
			return m_varMap.get(_key);
		}
		return null;
	}

	private Color getColor(String _color) {
		if (_color.equalsIgnoreCase("black")) {
			return Color.black;
		} else if (_color.equalsIgnoreCase("blue")) {
			return Color.blue;
		} else if (_color.equalsIgnoreCase("cyan")) {
			return Color.cyan;
		} else if (_color.equalsIgnoreCase("darkGray")) {
			return Color.darkGray;
		} else if (_color.equalsIgnoreCase("gray")) {
			return Color.gray;
		} else if (_color.equalsIgnoreCase("green")) {
			return Color.green;
		} else if (_color.equalsIgnoreCase("lightGray")) {
			return Color.lightGray;
		} else if (_color.equalsIgnoreCase("magenta")) {
			return Color.magenta;
		} else if (_color.equalsIgnoreCase("orange")) {
			return Color.orange;
		} else if (_color.equalsIgnoreCase("pink")) {
			return Color.pink;
		} else if (_color.equalsIgnoreCase("red")) {
			return Color.red;
		} else if (_color.equalsIgnoreCase("white")) {
			return Color.white;
		} else if (_color.equalsIgnoreCase("yellow")) {
			return Color.yellow;
		}
		try {
			return Color.decode(_color);
		} catch (NumberFormatException _nfe) {
			appendLog("eParsePanel.getColor(" + _color + ")");
			_nfe.printStackTrace();
		}
		return Color.black;
	}

	private Class getClass(String _class) {
		try {
			return Class.forName(_class);
		} catch (ClassNotFoundException _cnf) {
			appendLog("eParsePanel.getClass(" + _class + ")");
			_cnf.printStackTrace();
		}
		return null;
	}

	protected void processEndElement(String _code) {}

/*
static parsing functions
start
 */
	private String readFile(String _file) {
		StringBuffer out = new StringBuffer();
		String temp = null;
		FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader inStream = null;
        try {
			fis = new FileInputStream(_file);
			isr = new InputStreamReader(fis,getString("encode"));
			inStream = new BufferedReader(isr);
			while ((temp = inStream.readLine()) != null) {
				out.append(temp);
			}
		} catch (UnsupportedEncodingException _uee) {
			_uee.printStackTrace();
		} catch (IOException _ioe) {
			_ioe.printStackTrace();
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
		}
		return out.toString();
	}

	/**
     * generate
     * @param _xml
     * @author Anthony C. Liberto
     */
    public void generate(String _xml) {
		DefaultHandler defaulthandler = new DefaultHandler() {
			public void startElement(String _URI, String _tagName, String _qName, Attributes _atts) {
				processElement(_tagName,_atts);
			}

			public void endElement(String _URI, String _tagName, String _qName) {
				processEndElement(_tagName);
			}

            public void startDocument() {}
			public void endDocument() {}
			public void characters(char[] _rgc, int _nStart, int _nLength) throws SAXException {}

			public void error(SAXParseException _saxparseexception) throws SAXException {
				throw _saxparseexception;
			}

		};
		SAXParser parser = new SAXParser();
		parser.setContentHandler(defaulthandler);
		try {
			String xml = readFile(_xml);
			parser.parse(new InputSource(new StringReader(xml)));
		} catch (Exception _x) {
			_x.printStackTrace();
		}
	}

    /**
     * getPanelType
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getPanelType() {
		return TYPE_EPARSEPANEL;
	}
}
