//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eacm.mw;

import java.awt.Component;
import java.io.IOException;
import java.io.StringReader;
import javax.swing.DefaultComboBoxModel;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import java.util.HashMap;
import java.util.logging.Level;

import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.objects.SerialPref;
import com.ibm.eacm.objects.Utils;

/**
 * This class parses the middleware_client_properties.html and creates MWObjects for each entry
 * It is the model used for middleware selection combo boxes
 * It maintains the current mw location.
 * @author Wendy Stimpson
 */
// $Log: MWParser.java,v $
// Revision 1.2  2013/05/03 19:11:59  wendy
// save mwloc if 'please select' and locchooser.arm exist
//
// Revision 1.1  2012/09/27 19:39:22  wendy
// Initial code
//
public class MWParser extends DefaultComboBoxModel implements EACMGlobals 
{
	private static final long serialVersionUID = 1L;
	public static final String MW_FILENAME = "middleware_client_properties.html";
	public static final String MIDDLEWARE_PROFILE_KEY = "mw.profile.key";

    private String sDefaultKey = null; // hang onto mw key with default=true attribute
    private boolean bDefault = false; // used in parsing a single mw tag
    private HashMap<String, MWObject> map = new HashMap<String, MWObject>();
    private MWObject mwO = null; // used in parsing a single mw tag
    private MWObject currentMwo = null; // the instance currently connected
    /**
     * mwParser
     * @throws IOException 
     */
    protected MWParser() throws IOException {
        generate();
        if (this.getSize()==0){
        	com.ibm.eacm.ui.UI.showMessage(null,"mw.definition.err-title", javax.swing.JOptionPane.ERROR_MESSAGE, 
        			"mw.definition.err-title", Utils.getResource("msg0000"));
        	System.exit(-1);
        }
    }

    /**
     * clear parse variables
     */
    private void clearMW() {  
    	mwO = null;
        bDefault = false;
    }

    /**
     * load attributes for a single mw tag
     * @param _atts
     */
    private void processMW(Attributes _atts) {
        clearMW();
        mwO = new MWObject();
        for (int i = 0; i < _atts.getLength(); ++i) {
            if (_atts.getType(i).equals("CDATA")) {
                processMWAtt(_atts.getLocalName(i), _atts.getValue(i));
            }
        }
    }
    
    /**
     * load specified attribute into the mw object currently getting parsed
     * @param _code
     * @param _value
     */
    private void processMWAtt(String _code, String _value) {
        if (_code.equalsIgnoreCase("DESCRIPTION")) {
            mwO.setDescription(_value);
        } else if (_code.equalsIgnoreCase("NAME")) {
            mwO.setName(_value);
        } else if (_code.equalsIgnoreCase("PORT")) {
        	mwO.setPort(Routines.getInt(_value, 1099));
        } else if (_code.equalsIgnoreCase("IP")) {
        	mwO.setIP(_value);
        } else if (_code.equalsIgnoreCase("REPORT")) { 
        	mwO.setReportPrefix(_value); 
        } else if (_code.equalsIgnoreCase("DEFAULT")) {
            if (_value.equalsIgnoreCase("true")) {
                bDefault = true;
            }
        } 
    }

    /**
     * start to process mw tag, only tag currently supported
     * @param _tagName
     * @param _atts
     */
    private void processElement(String _tagName, Attributes _atts) {
        if (_tagName.equalsIgnoreCase("MW")) {
            processMW(_atts);
        }
    }

    /**
     * end processing for the mw tag
     * @param _tagName
     */
    private void processEndElement(String _tagName) {
        if (_tagName.equalsIgnoreCase("MW")) {
            processEndMW();
        }
    }

    /**
     * save the newly created mw object, hang onto key if it is the default
     */
    private void processEndMW() {
    	if (!bDefault && !(Routines.have(mwO.getIP()) && Routines.have(mwO.getName()) && Routines.have(mwO.getPort()))){
    		//msg0000.1 = Invalid MW Object definition: {0}
    		com.ibm.eacm.ui.UI.showMessage(null,"mw.definition.err-title", javax.swing.JOptionPane.ERROR_MESSAGE, 
        			"mw.definition.err-title", Utils.getResource("msg0000.1",mwO.toString()));
    		RMIMgr.logger.log(Level.SEVERE, "Invalid MW object definition: "+mwO);
    	}else{
    		map.put(mwO.key(), mwO);
    		addElement(mwO);

    		if (bDefault) {
    			sDefaultKey = mwO.key();
    		}
    	}
    }

    /**
     * staring the parse process, clear all references
     */
    private void processStartDocument() {
        map.clear();
        removeAllElements();
    }

    /**
     * get MWObject, look for saved preference first
     * return default if not found
     * default may be null
     * @return
     */
    protected MWObject getInitial() {
    	String key = SerialPref.getPref(MIDDLEWARE_PROFILE_KEY, "null");
        MWObject mwo = (MWObject) map.get(key);
        if (mwo == null) {
        	//get the MW marked as default, may not be one
        	if (sDefaultKey != null) {
            	mwo = (MWObject) map.get(sDefaultKey);
        	}else{
        		if (Utils.isArmed(LOCATION_CHOOSER_ARM_FILE)) { 
        			mwo = (MWObject)getElementAt(0); // select the first one so something is in the list
        			//if locChooser.arm exists and there is "Please select.." entry, bypass it
       				if(!(Routines.have(mwo.getIP()) && Routines.have(mwo.getName()) && Routines.have(mwo.getPort()))){
        				if(getSize()>1){
        					mwo = (MWObject)getElementAt(1);
        				}
        			}
        		}else{
        			if(getSize()==1){ //if more, let user pick one when login is attempted
        				mwo = (MWObject)getElementAt(0);
        				SerialPref.putPref(MIDDLEWARE_PROFILE_KEY, mwo.key());
        			}
        		}
        	}
        }
        return mwo;
    }

    /**
     * get current MWObject, may not be selection if user changed preferences
     * but it is the one used to connect to mw
     * @return
     */
    public MWObject getCurrent() {
        return currentMwo;
    }
    /**
     * set mw used for successful connect
     * @param curmo
     */
    public void setCurrent(MWObject curmo) {
        currentMwo = curmo;
    }

    /**********
     * parse the middleware_client_properties.html file creating one MWObject for each entry
     * @throws IOException 
     */
    private void generate() throws IOException {
    	String _xml = Utils.readString(RESOURCE_DIRECTORY,MW_FILENAME,EACM_FILE_ENCODE);
    	if (!Routines.have(_xml)){
    		throw new IOException(MW_FILENAME+" not loaded");
    	}
        DefaultHandler defaulthandler = new DefaultHandler() {
            public void startElement(String _URI, String _tagName, String _qName, Attributes _atts) {
                processElement(_tagName, _atts);
            }
            public void endElement(String _URI, String _tagName, String _qName) {
                processEndElement(_tagName);
            }
            public void startDocument() {
                processStartDocument();
            }
            public void endDocument() { }
            public void characters(char[] _rgc, int _nStart, int _nLength) throws SAXException { }
            public void error(SAXParseException _saxparseexception) throws SAXException {
                throw _saxparseexception;
            }
        };
        SAXParser parser = new SAXParser();
        parser.setContentHandler(defaulthandler);
        try {
            parser.parse(new InputSource(new StringReader(_xml)));
        } catch (Exception x) {
        	com.ibm.eacm.ui.UI.showException(null,x, "mw.definition.err-title");
        }
    }

	/**
	 * called when user is adding a mw location, if valid the new mw object is added to the collection
	 * @param _s
	 * <mw description="test addmw" name ="OPICM-Middleware" ip="pokxea9.pok.ibm.com" port="3012" report="http://pokxea9.pok.ibm.com/transform/oim/eacm/bui/" />
	 * @return
	 */
	public boolean validateXMLAndLoad(Component _c, String _s) {
        boolean out = false;
        StringBuffer errMsg = new StringBuffer();
        if (!Routines.have(_s)){
        	return out;
        }

        clearMW();
        DefaultHandler defaulthandler = new DefaultHandler() {
            public void startElement(String _URI, String _tagName, String _qName, Attributes _atts) {
                processElement(_tagName, _atts);
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
			com.ibm.eacm.ui.UI.showException(_c,_x,"mw.definition.err-title");
			return out;
		}

		if (mwO!=null){
			if (!Routines.have(mwO.getDescription())) {
				//msg5025.2 = "{0}" is required.
				errMsg.append(RETURN+Utils.getResource("msg5025.2","Description"));
			}
			if (!Routines.have(mwO.getName())) {
				//msg5025.2 = "{0}" is required.
				errMsg.append(RETURN+Utils.getResource("msg5025.2","Name"));
			}
			if (mwO.getPort() <= 0) {
				//msg5025.2 = "{0}" is required.
				errMsg.append(RETURN+Utils.getResource("msg5025.2","Port"));
			}
			if (!Routines.have(mwO.getIP())) {
				//msg5025.2 = "{0}" is required.
				errMsg.append(RETURN+Utils.getResource("msg5025.2","Ip"));	
			}
			if (!Routines.have(mwO.getReportPrefix())) {
				//msg5025.2 = "{0}" is required.
				errMsg.append(RETURN+Utils.getResource("msg5025.2","Report"));
			}
			if (errMsg.length()==0) {
				out = !map.containsKey(mwO.key());
				if (!out) {
					//msg5025.3 = "{0}" already exists.
					errMsg.append(RETURN+Utils.getResource("msg5025.3",mwO.toString()));
				}
			}	
		}
		
		if (!out) {
			com.ibm.eacm.ui.UI.showMessage(_c,"mw.definition.err-title", 
				javax.swing.JOptionPane.ERROR_MESSAGE, 
				"mw.definition.err-title", Utils.getResource("msg5025.1",errMsg.toString()));
		}else{
			try{
				processEndMW(); // save the new mwobject
			}catch(Exception exc){
				out=false;
				com.ibm.eacm.ui.UI.showException(_c,exc, "mw.definition.err-title");
			}
		}
		return out;
	}
}
