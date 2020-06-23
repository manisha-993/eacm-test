/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: MWParser.java,v $
 * Revision 1.2  2008/01/30 16:27:00  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.6  2005/09/21 17:59:21  tony
 * fixed logic
 *
 * Revision 1.5  2005/09/20 20:08:43  tony
 * improved error reporting.
 *
 * Revision 1.4  2005/09/20 17:47:38  tony
 * removed println statement
 *
 * Revision 1.3  2005/09/20 16:41:18  tony
 * Enhanced logic and added error messaging
 *
 * Revision 1.2  2005/09/20 16:01:59  tony
 * CR092005410
 * Ability to add middleware location on the fly.
 *
 * Revision 1.1.1.1  2005/09/09 20:37:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:58:56  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/04 16:57:41  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.4  2005/02/02 17:30:22  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/27 23:18:16  tony
 * JTest Formatting
 *
 * Revision 1.2  2004/02/19 21:34:52  tony
 * e-announce1.3
 *
 * Revision 1.1.1.1  2004/02/10 16:59:26  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2004/01/20 19:25:51  tony
 * added password logic to the log file.
 *
 * Revision 1.7  2004/01/14 18:47:57  tony
 * acl_20040114
 *   1)  updated logic to allow for manual load of serial pref.
 *   2)  trigger a middleware selection if no default middleware is defined.
 *   3)  prevent a put of parent when non new row is selected.
 *
 * Revision 1.6  2003/09/18 17:30:08  tony
 * 52308
 *
 * Revision 1.5  2003/07/29 21:34:32  tony
 * updated form functionality.
 *
 * Revision 1.4  2003/06/30 18:07:21  tony
 * improved functionality for testing by adding functionality
 * to MWObject that includes the default of the userName and
 * overwrite.properties file to use.  This will give the test
 * team more flexibility in defining properties for the application.
 *
 * Revision 1.3  2003/05/05 18:04:38  tony
 * 50515
 *
 * Revision 1.2  2003/03/12 23:51:09  tony
 * accessibility and column order
 *
 * Revision 1.1.1.1  2003/03/03 18:03:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2002/11/07 16:58:10  tony
 * added/adjusted copyright statement
 *
 */
package com.elogin;
import java.awt.Component;
import java.io.StringReader;
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
public class MWParser extends EVector implements EAccessConstants {
	private static final long serialVersionUID = 1L;
	private final String fileName = "middleware_client_properties.html";

    private String sDefault = null;
    private String XML = null;

    private String sDesc = null;
    private String sName = null;
    private String sIP = null;
    private int iPort = 0;
    private String sReport = null; //50515
    private String sUser = null; //acl_20030630
    private String sPass = null; //acl_20040120
    private String sProperty = null; //acl_20030630
    private String sChatIP = null;
    private int iChatPort = 0;
    private boolean bDefault = false;
    private boolean bShowMWSel = false; //52308

    /**
     * mwParser
     * @author Anthony C. Liberto
     */
    public MWParser() {
        super();
        generate(getXML());
        return;
    }

    private void clearMW() {
        sDesc = null;
        sName = null;
        sIP = null;
        iPort = 0;
        sReport = null; //50515
        sUser = null; //acl_20030630
        sPass = null; //acl_20040120
        sProperty = null; //acl_20030630
        bDefault = false;
        bShowMWSel = false; //52308
        iChatPort = 0;
        sChatIP = null;
        return;
    }

    private void processMW(Attributes _atts) {
        clearMW();
        for (int i = 0; i < _atts.getLength(); ++i) {
            if (_atts.getType(i).equals("CDATA")) {
                processMW(_atts.getLocalName(i), _atts.getValue(i));
            }
        }
        return;
    }
    private void processMW(String _code, String _value) {
        if (_code.equalsIgnoreCase("DESCRIPTION")) {
            sDesc = _value;
        } else if (_code.equalsIgnoreCase("NAME")) {
            sName = _value;
        } else if (_code.equalsIgnoreCase("PORT")) {
            iPort = Routines.getInt(_value, 1099);
        } else if (_code.equalsIgnoreCase("IP")) {
            sIP = _value;
        } else if (_code.equalsIgnoreCase("REPORT")) { //50515
            sReport = _value; //50515
        } else if (_code.equalsIgnoreCase("USERNAME")) { //acl_20030630
            sUser = _value; //acl_20030630
        } else if (_code.equalsIgnoreCase("PROPERTIES")) { //acl_20030630
            sProperty = _value; //acl_20030630
        } else if (_code.equalsIgnoreCase("CHAT_IP")) {
            sChatIP = _value;
        } else if (_code.equalsIgnoreCase("CHAT_PORT")) {
            iChatPort = Routines.getInt(_value, 1098);
        } else if (_code.equalsIgnoreCase("DEFAULT")) {
            if (_value.equalsIgnoreCase("true")) {
                bDefault = true;
            }
        } else if (_code.equalsIgnoreCase("SHOW_MW_SELECT")) { //52308
            if (_value.equalsIgnoreCase("true")) { //52308
                bShowMWSel = true; //52308
            } //52308
        } else if (_code.equalsIgnoreCase("PASSWORD")) {
            sPass = _value;
        }
        return;
    }

    private void processElement(String _tagName, Attributes _atts) {
        if (_tagName.equalsIgnoreCase("MW")) {
            processMW(_atts);
        }
        return;
    }

    private void processEndElement(String _tagName) {
        if (_tagName.equalsIgnoreCase("MW")) {
            processEndMW();
        }
        return;
    }

    private void processEndMW() {
        //50515		MWObject mwO = new MWObject(sDesc,sName,sIP,iPort);
        //acl_20030630		MWObject mwO = new MWObject(sDesc,sName,sIP,iPort,sReport);		//50515
        //52308		MWObject mwO = new MWObject(sDesc,sName,sIP,iPort,sReport,sUser,sProperty);			//acl_20030630
        //acl_20040120		MWObject mwO = new MWObject(sDesc,sName,sIP,iPort,sReport,sUser,sProperty,bShowMWSel);			//52308
        MWObject mwO = new MWObject(sDesc, sName, sIP, iPort, sReport, sUser, sPass, sProperty, bShowMWSel, sChatIP, iChatPort); //acl_20040120
        put(mwO.key(), mwO);
        if (bDefault) {
            sDefault = new String(mwO.key());
        }
        return;
    }

    private void processStartDocument() {
        clear();
        return;
    }

    /*private void processEndDocument() {
        clearMW();
        return;
    }*/

    /**
     * getMWObjects
     * @return
     * @author Anthony C. Liberto
     */
    public MWObject[] getMWObjects() {
        int ii = size();
        MWObject[] out = new MWObject[ii];
        for (int i = 0; i < ii; ++i) {
            out[i] = getMWObject(i);
        }
        return out;
    }

    private MWObject getMWObject(int _i) {
        return (MWObject) get(_i);
    }

    private MWObject getMWObject(String _s) {
        if (_s != null && containsKey(_s)) {
            return (MWObject) get(_s);
        }
        return null;
    }

    /**
     * getDefault
     * @return
     * @author Anthony C. Liberto
     */
    public MWObject getDefault() {
        if (sDefault != null) {
            return getMWObject(sDefault);
        }
        //acl_20040114		if (isEmpty())
        //acl_20040114			return null;
        //acl_20040114		return getMWObject(0);
        return null;
    }

    /**
     * getCurrent
     * @return
     * @author Anthony C. Liberto
     */
    public MWObject getCurrent() {
        return getCurrent(eaccess().getPrefString(getKey(), null));
    }

    /**
     * getCurrent
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public MWObject getCurrent(String _s) {
        MWObject mwo = getMWObject(_s);
        if (mwo != null) {
            return mwo;
        }
        return getDefault();
    }

    /**
     * getKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getKey() {
        return MIDDLEWARE_PROFILE_KEY;
    }

    /*
     * parse
     */
    private String getXML() {
        XML = eaccess().getHTML(fileName);
        return XML;
    }

    private void generate(String _xml) {
        DefaultHandler defaulthandler = new DefaultHandler() {

            public void startElement(String _URI, String _tagName, String _qName, Attributes _atts) {
                processElement(_tagName, _atts);
                return;
            }

            public void endElement(String _URI, String _tagName, String _qName) {
                processEndElement(_tagName);
            }

            public void startDocument() {
                processStartDocument();
            }

            public void endDocument() {
                //processEndDocument();
            }

            public void characters(char[] _rgc, int _nStart, int _nLength) throws SAXException {
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

	/**
	 * isValidXML
	 * CR092005410
	 * @param _s
	 * @return
	 * @author tony
	 */
	public boolean isValidXML(Component _c, String _s) {
        boolean out = false;
        clearMW();
        DefaultHandler defaulthandler = new DefaultHandler() {
            public void startElement(String _URI, String _tagName, String _qName, Attributes _atts) {
                processElement(_tagName, _atts);
                return;
            }
            public void endElement(String _URI, String _tagName, String _qName) {}
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
            parser.parse(new InputSource(new StringReader(_s)));
		} catch (Exception _x) {
			EAccess.report(_x,false);
			eaccess().showError(_c,"msg5025.1");
			return out;
		}

		if (Routines.have(sDesc)) {
			if (Routines.have(sName)) {
				if (iPort > 0) {
					if (Routines.have(sIP)) {
						if (Routines.have(sReport)) {
							String tmpKey = sDesc + ":" + sName + ":" + sIP + ":" + iPort + ":" + sReport;
							out = !containsKey(tmpKey);
							if (!out) {
								eaccess().showError(_c,"msg5025.0");
								return out;
							}
						}
					}
				}
			}
		}
		if (!out) {
			eaccess().showError(_c,"msg5025.1");
		}
		if (out) {
			processEndMW();
		}
		return out;
	}

	/**
	 * addLocation
	 * CR092005410
	 * @param _s
	 * @author tony
	 */
	public void addLocation(String _s) {
		eaccess().appendMiddlewareLocation(fileName,_s);
		return;
	}

	/**
	 * getDescription
	 * CR092005410
	 * @return
	 * @author tony
	 */
	public String getDescription() {
		return sDesc;
	}

	/**
	 * getName
	 * CR092005410
	 * @return
	 * @author tony
	 */
	public String getName() {
		return sName;
	}

	/**
	 * getIPDetails
	 * CR092005410
	 * @return
	 * @author tony
	 */
	public String getIPDetails() {
		return sIP + ":" + iPort;
	}

	/**
	 * getIP
	 * CR092005410
	 * @return
	 * @author tony
	 */
	public String getIP() {
		return sIP;
	}

	/**
	 * getPort
	 * CR092005410
	 * @return
	 * @author tony
	 */
	public int getPort() {
		return iPort;
	}
}
