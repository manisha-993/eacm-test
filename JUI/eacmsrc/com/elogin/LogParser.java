/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2003/07/02
 * @author Anthony C. Liberto
 *
 * $Log: LogParser.java,v $
 * Revision 1.2  2008/01/30 16:27:00  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:09  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:39  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:56  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 17:30:21  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:16  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:26  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2003/10/29 00:22:23  tony
 * removed System.out. statements.
 *
 * Revision 1.6  2003/07/03 18:43:47  tony
 * adjusted functionality
 *
 * Revision 1.5  2003/07/03 16:38:03  tony
 * improved scripting logic.
 *
 * Revision 1.4  2003/07/03 00:41:40  tony
 * updated event logging so log parser can directly parse
 * the log file.
 *
 * Revision 1.3  2003/07/02 22:59:13  tony
 * improved functionality
 *
 * Revision 1.2  2003/07/02 21:42:30  tony
 * updated logging to allow parsing of log file to
 * auto navigate development to the proper location
 * via the proper path.
 *
 * Revision 1.1  2003/07/02 16:43:23  tony
 * updated logging capabilities to allow for play back of
 * log files.
 *
 *
 */
package com.elogin;
import com.ibm.eannounce.epanels.ENavForm;
import java.awt.*;
import java.io.StringReader;
import javax.swing.*;
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
public class LogParser extends AccessibleDialogPanel implements EAccessConstants {
	private static final long serialVersionUID = 1L;
	private Object[] oArray = null;
    private static LogParser lParse = null;
    private ETabable tab = null;
    private int iCount = 0;
    private int iMaximum = 0;
    private JProgressBar progress = new JProgressBar();
	private boolean bSilent = true;

    private LogParser() {
		super(new BorderLayout());
		progress.setStringPainted(true);
		add("Center", progress);
		return;
	}

	/**
     * parseLog
     * @author Anthony C. Liberto
     */
    public static void parseLog() {
		final ESwingWorker myWorker = new ESwingWorker() {
			public Object construct() {
				String xml = null;
                createParse();
				xml = lParse.getXML();
				if (xml != null) {
					eaccess().closeAll();
					lParse.preProcess(xml);
					lParse.generate(xml);
				}
				return null;
			}
			public boolean isInterruptable() {
				return false;
			}
			public void finished() {}
		};
		myWorker.start();
		return;
	}

	/**
     * createParse
     * @return
     * @author Anthony C. Liberto
     */
    public static LogParser createParse() {
		if (lParse == null) {
			lParse = new LogParser();
		}
		return lParse;
	}

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
     * removeMent
     * @author Anthony C. Liberto
     */
    protected void removeMenu() {
		return;
	}

    private void processElement(String _tagName, Attributes _atts) {
		if (_tagName.equalsIgnoreCase("DATABASE")){
			processDatabase(_atts);
		} else if (_tagName.equalsIgnoreCase("TABSELECT")) {
			processTabSelect(_atts);
		} else if (_tagName.equalsIgnoreCase("NAVSELECT")) {
			processNavSelect(_atts);
		} else if (_tagName.equalsIgnoreCase("ROLESELECT")) {
			processRoleSelect(_atts);
		}
		return;
	}

	private void processEndElement(String _tagName) {
		updateProgress(_tagName + " : " + (String)oArray[1],iCount);
		if (_tagName.equalsIgnoreCase("ROLESELECT")) {
			eaccess().selectRole((String)oArray[0],(String)oArray[1],Routines.toInt((String)oArray[2]));
		} else if (_tagName.equalsIgnoreCase("TABSELECT")) {
			tab = eaccess().selectTab((String)oArray[0], (String)oArray[1], Routines.toInt((String)oArray[2]));
		} else {
			if (tab == null) {
				tab = eaccess().getCurrentTab();
			}
			if (tab != null) {
				if (_tagName.equalsIgnoreCase("DATABASE")){
					tab.process((String)oArray[0],(String)oArray[1],(String[])oArray[2], (String[])oArray[3]);
				} else if (_tagName.equalsIgnoreCase("NAVSELECT")) {
					if (tab instanceof ENavForm) {
						((ENavForm)tab).select(Routines.toInt((String)oArray[0]),(String)oArray[1],Routines.toInt((String)oArray[2]));
					}
				}
			}
		}
		++iCount;
		eaccess().getLogin().repaint();
		return;
	}

	private void processDatabase(Attributes _atts) {
		clear(oArray);
		for (int i=0;i<_atts.getLength();++i) {
			if (_atts.getType(i).equals("CDATA")) {
				processDatabase(_atts.getLocalName(i),_atts.getValue(i));
			}
		}
		return;
	}

	private void processDatabase(String _code, String _value) {
		if (_code.equalsIgnoreCase("METHOD")) {
			oArray[0] = _value;
		} else if (_code.equalsIgnoreCase("ACTIONCOMMAND")) {
			oArray[1] = _value;
		} else if (_code.equalsIgnoreCase("ENTITYITEMS")) {
			oArray[2] = Routines.getStringArray(_value,ARRAY_DELIMIT);
		} else if (_code.equalsIgnoreCase("PARENTITEMS")) {
			oArray[2] = Routines.getStringArray(_value,ARRAY_DELIMIT);
		} else if (_code.equalsIgnoreCase("CHILDITEMS")) {
			oArray[3] = Routines.getStringArray(_value,ARRAY_DELIMIT);
		}
		return;
	}

	private void processTabSelect(Attributes _atts) {
		clear(oArray);
		for (int i=0;i<_atts.getLength();++i) {
			if (_atts.getType(i).equals("CDATA")) {
				processTabSelect(_atts.getLocalName(i),_atts.getValue(i));
			}
		}
		return;
	}

	private void processTabSelect(String _code, String _value) {
		if (_code.equalsIgnoreCase("TAB")) {
			oArray[0] = _value;
		} else if (_code.equalsIgnoreCase("PARENTKEY")) {
			oArray[1] = _value;
		} else if (_code.equalsIgnoreCase("OPWGID")) {
			oArray[2] = _value;
		}
		return;
	}

	private void processNavSelect(Attributes _atts) {
		clear(oArray);
		for (int i=0;i<_atts.getLength();++i) {
			if (_atts.getType(i).equals("CDATA")) {
				processNavSelect(_atts.getLocalName(i),_atts.getValue(i));
			}
		}
		return;
	}

	private void processNavSelect(String _code, String _value) {
		if (_code.equalsIgnoreCase("SIDE")) {
			oArray[0] = _value;
		} else if (_code.equalsIgnoreCase("KEY")) {
			oArray[1] = _value;
		} else if (_code.equalsIgnoreCase("OPWGID")) {
			oArray[2] = _value;
		}
		return;
	}

	private void processRoleSelect(Attributes _atts) {
		clear(oArray);
		for (int i=0;i<_atts.getLength();++i) {
			if (_atts.getType(i).equals("CDATA")) {
				processRoleSelect(_atts.getLocalName(i),_atts.getValue(i));
			}
		}
		return;
	}

	private void processRoleSelect(String _code, String _value) {
		if (_code.equalsIgnoreCase("ENTERPRISE")) {
			oArray[0] = _value;
		} else if (_code.equalsIgnoreCase("ROLE")) {
			oArray[1] = _value;
		} else if (_code.equalsIgnoreCase("OPWGID")) {
			oArray[2] = _value;
		}
		return;
	}

	private void clear(Object[] _o) {
		if (_o != null) {
			for (int i=0;i<_o.length;++i) {
				_o[i] = null;
			}
		}
		return;
	}

	private void processStartDocument() {
		iCount = 0;
		oArray = new Object[4];
		return;
	}

	private void processEndDocument() {
		clear(oArray);
		hideDialog();
		return;
	}

/*
 * parse
 */
	private String getXML() {
		return getXML(eaccess().gio().getFileName(java.awt.FileDialog.LOAD));
	}

	private String getXML(String _fileName) {
		if (_fileName == null) {
			return null;
		}
		return eaccess().gio().readString(_fileName);
	}

	private void generate(String _xml) {
		DefaultHandler defaulthandler = new DefaultHandler() {

			public void startElement(String _URI, String _tagName, String _qName, Attributes _atts) {
				processElement(_tagName,_atts);
				return;
			}

			public void endElement(String _URI, String _tagName, String _qName) {
				processEndElement(_tagName);
			}

            public void startDocument() {
				processStartDocument();
			}

			public void endDocument() {
				processEndDocument();
			}

			public void characters(char[] _rgc, int _nStart, int _nLength) throws SAXException {}

			public void error(SAXParseException _saxparseexception) throws SAXException {
				throw _saxparseexception;
			}

		};
		SAXParser parser = new SAXParser();
		parser.setContentHandler(defaulthandler);
		try {
			parser.parse(new InputSource(new StringReader(_xml)));
		} catch (Exception x) {
			x.printStackTrace();
		}
		return;
	}

	private void preProcess(String _xml) {
		DefaultHandler defaulthandler = new DefaultHandler() {
			public void startElement(String _URI, String _tagName, String _qName, Attributes _atts) {
				++iMaximum;
				if (_tagName.equalsIgnoreCase("SILENT")) {
					bSilent = true;
				} else if (_tagName.equalsIgnoreCase("VERBOSE")) {
					bSilent = false;
				}
				return;
			}
			public void endElement(String _URI, String _tagName, String _qName) {}
            public void startDocument() {}
			public void endDocument() {
				progress.setMaximum(iMaximum);
			}
			public void characters(char[] _rgc, int _nStart, int _nLength) throws SAXException {
				String str = new String(_rgc,_nStart,_nLength);
				if (str != null && !bSilent) {
					appendLog("processing characters: " + str);
				}
				return;
			}

			public void error(SAXParseException _saxparseexception) throws SAXException {
				throw _saxparseexception;
			}
		};
		SAXParser parser = new SAXParser();
		parser.setContentHandler(defaulthandler);
		try {
			parser.parse(new InputSource(new StringReader(_xml)));
		} catch (Exception x) {
			x.printStackTrace();
		}
		return;
	}

	private void updateProgress(String _status, int _index) {
		progress.setString(_status);
		progress.setValue(_index);
//		progress.revalidate();
		eaccess().show(null,this,false);
		packDialog();
		update(getGraphics());
		return;
	}

}
