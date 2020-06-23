//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit.formgen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.StringReader;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import COM.ibm.eannounce.objects.*;


import com.ibm.eacm.edit.form.BlobCellPanel;
import com.ibm.eacm.edit.form.FlagCellPanel;
import com.ibm.eacm.edit.form.DateCellPanel;
import com.ibm.eacm.edit.form.FormCellPanel;
import com.ibm.eacm.edit.form.LongCellPanel;
import com.ibm.eacm.edit.form.FormTable;
import com.ibm.eacm.edit.form.MultiCellPanel;
import com.ibm.eacm.edit.form.XMLCellPanel;
import com.ibm.eacm.edit.form.TextCellPanel;
import com.ibm.eacm.edit.form.TimeCellPanel;


import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.objects.Utils;
/**
 * form generator for formeditor
 * @author Wendy Stimpson
 */
// $Log: Generator.java,v $
// Revision 1.2  2013/07/18 18:31:38  wendy
// fix compiler warnings
//
// Revision 1.1  2012/09/27 19:39:24  wendy
// Initial code
//
public class Generator implements EACMGlobals {
	private static final int NLS_1 = 1;
	
	private boolean bValidate = Utils.isArmed(FORM_VALIDATE_ARM_FILE);
	private JPanel mainPane = new JPanel(new GridBagLayout());
	private String fileName=null;
	private String XML = null;
	
	// these are used to generate unique keys
	private int iConstantCount = 0;
	private int iErrorCount = 0;

	// CI162 changes for BUI required form modifications, following used to handle layout for fieldsets
	private String lastTagRead="";
	private boolean inFieldSet = false;
	private int rowY=0; // value of y for the current tr, maybe incremented by a td
	private int maxY=0; // max value of y for the current tr, must allow for 2 rows in a one tr
	
	private FormPanel curPanel = null; 
	private TablePanel tablePane = null;

	private GridBagConstraints gbca = new GridBagConstraints();
	private GridBagConstraints panelConstraints = null;
	
	private int x = 0;
	private int y = 1; 
	private int tableX = 0;
	private int tableY = 1;; 

	private HashMap<String, Object> map = new HashMap<String, Object>(); // this maps key to label or panel for editor
	private Vector<String> vOrder = new Vector<String>(); // this is the order of the editor elements
	private boolean bClassified = true;

	private Color fntColor = Color.black;
	private int fntSize = 11;
	private int fntStyle = 0;
	private String fntFace = "Arial,Helvetica";
	private Font defFont = new Font(fntFace, fntStyle, fntSize);

	private Color defColor = Color.black;
	private int defSize = 11;
	private String defFace = "Arial,Helvetica";

	private Color defBckColor = Color.decode("#CCCCCC");
	private Color bckColor = defBckColor;
	
    private long starttime;
    private EntityItem currentItem = null;

	private boolean cmpOpaque = true;//false; if false, colors for required dont flow thru to TextEditors
	private String cmpToolTip = null;

	private FormTable formTable = null;// needed to get entityitem and rst info
	
	/**
	 * @param ft
	 * @param fn
	 */
	public Generator(FormTable ft, String fn){
		fileName = fn;
		formTable = ft; 

		Logger.getLogger(EDIT_PKG_NAME).log(Level.FINER,"retrieving XML(" + fileName + ")"); 
		XML = Utils.readString(RESOURCE_DIRECTORY,fileName,EACM_FILE_ENCODE);

		mainPane.setDoubleBuffered(true);
	}
	
	/**
	 * release memory
	 */
	public void dereference(){
		currentItem = null;
		
		if(curPanel!=null){
			curPanel.derefGenVars();
			curPanel = null; 
		}
		if(tablePane!=null){
			tablePane.derefGenVars();
			tablePane = null;
		}
		
		dereferenceForm();
		
		mainPane.setUI(null);
		mainPane=null;
		
		fileName = null;
		XML=null;
		lastTagRead = null;
		
		gbca = null;
		panelConstraints = null;
		
		map.clear();
		map=null;
		
		vOrder.clear();
		vOrder = null;

		fntColor = null;
		fntFace = null;
		defFont = null;

		defColor = null;
		defFace = null;

		defBckColor = null;
		bckColor = null;

		cmpToolTip = null;

		formTable = null;
	}
	
	/**
	 * remove form items and clear state
	 */
	public void reset(){
		dereferenceForm();
		
		map.clear();
		vOrder.clear();

		Utils.gc();
	}

	/**
	 * release the form components
	 */
	private void dereferenceForm() {
		for (int i = 0; i < mainPane.getComponentCount(); ++i) {
			Component c = mainPane.getComponent(i);     
			if (c instanceof FormPanel) {
				((FormPanel) c).dereference();
			} else if (c instanceof TablePanel) {
				((TablePanel) c).dereference();
			}else if(c instanceof FormCellPanel){
				FormCellPanel fl = (FormCellPanel) c;
				fl.dereference();
			}
		}
		mainPane.removeAll();
	}
	
	public JPanel getMainPanel(){ return mainPane;}
	public HashMap<String, Object> getFormMap() {
		return map;
	}
	public Vector<String> getFormOrder() {
		return vOrder;
	}
	
    /**
     * read the xml and generate the main panel
     * 
     */
	public void buildForm(EntityItem ei) {
		currentItem = ei;
		
		//resetKeys
		//iEICount = 0;
		iConstantCount = 0;
		iErrorCount = 0;
		
		mainPane.removeAll();
		
		DefaultHandler defaulthandler = new DefaultHandler() {
			public void characters(char[] _chars, int _start, int _finish) {
				String str = new String(_chars, _start, _finish).trim();
				if (bValidate) {
					Logger.getLogger(EDIT_PKG_NAME).log(Level.FINER,"characters: " + str);
				}
				if (Routines.have(str)) {
					generateConstantLabel(str);
				}
			}
			public void startDocument() {}
			public void endDocument() {}

			public void startElement(String URI, String tagName, String qName, Attributes atts) {
				if (bValidate) {
					Logger.getLogger(EDIT_PKG_NAME).log(Level.FINER,"( " + URI + ", " + tagName + ", " + qName + ", atts) tableX:"+tableX+" tableY:"+tableY);
					Logger.getLogger(EDIT_PKG_NAME).log(Level.FINER,getAttsString(atts));
                    starttime =System.currentTimeMillis();
                    Logger.getLogger(TIMING_LOGGER).log(Level.INFO,tagName);
				}
				processElement(tagName, atts);
				lastTagRead = tagName;
			}

			public void endElement(String URI, String tagName, String qName) {
				if (bValidate) {
					Logger.getLogger(EDIT_PKG_NAME).log(Level.FINER,"( " + URI + ", " + tagName + ", " + qName + ", atts) tableX:"+tableX+" tableY:"+tableY);
				    Logger.getLogger(TIMING_LOGGER).log(Level.INFO,tagName+" ended "+Utils.getDuration(starttime));
				}
				if (tagName.equalsIgnoreCase("TABLE")) {
					if (inFieldSet){ // fieldset has a table, ignore it
						return;
					}
					processEndTable();
				} else if (tagName.equalsIgnoreCase("PANEL")) {
					processEndPanel();
				} else if (tagName.equalsIgnoreCase("MAINPANEL")) {
				} else if (tagName.equalsIgnoreCase("FONT")) {
					processEndFont(tagName);
				} else if (tagName.equalsIgnoreCase("B") || tagName.equalsIgnoreCase("STRONG")) {
					processEndFont("BOLD");
				} else if (tagName.equalsIgnoreCase("I")) {
					processEndFont("ITALIC");
				} else if (tagName.equalsIgnoreCase("TD")) {
					if (inFieldSet){ //fieldset has a table, ignore it
						return;
					}
					processEndTdTr("DETAIL");
					tableY=rowY; // restore any changes done in fieldset for the td so next td is on same line
				} else if (tagName.equalsIgnoreCase("TR")) {
					if (inFieldSet){ // fieldset has a table, ignore it
						return;
					}
					processEndTdTr("ROW");
				} else if (tagName.equalsIgnoreCase("CLASSIFY")) {
					bClassified = true;
				}else if (tagName.equalsIgnoreCase("LEGEND")) { //accessibility and JAWS reader
					tableY++;
					maxY=tableY;
				}else if (tagName.equalsIgnoreCase("FIELDSET")) { //accessibility and JAWS reader
					inFieldSet=false;
				}
			}

			public void error(SAXParseException saxparseexception) throws SAXException {
				if (bValidate) {
					Logger.getLogger(EDIT_PKG_NAME).log(Level.SEVERE,"editform exception( " + saxparseexception.toString() + ")");
				}

				throw saxparseexception;
			}
		};
		SAXParser parser = new SAXParser();
		parser.setContentHandler(defaulthandler);

		try {  	
			parser.parse(new InputSource(new StringReader(XML))); //parses from String
		} catch (Exception x) {
			x.printStackTrace();
			//msg11016.0=Unable to load requested form {0} at this time. Please contact support for assistance.
			com.ibm.eacm.ui.UI.showFYI(null, Utils.getResource("msg11016.0",fileName));
		} finally {
			Utils.gc();
		}
	}
	
	/**
	 * generate a label for constant text
	 * @param _text
	 */
	private void generateConstantLabel(String _text) {
		++iConstantCount;
		String sKey = "Constant:" + iConstantCount;
		FormLabel label = new FormLabel(_text, sKey);
		vOrder.add(label.getKey());
		map.put(label.getKey(), label);
		
		addToForm(label);
	}
	/**
	 * list attributes
	 * @param _atts
	 * @return
	 */
	private String getAttsString(Attributes _atts) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < _atts.getLength(); ++i) {
			sb.append("     " + _atts.getLocalName(i) + "=" + _atts.getValue(i) + ",");
		}
		return sb.toString();
	}
	
	/**
	 * Methods used to generate the form
	 */
	private void processElement(String tagName, Attributes atts) {
		if (tagName.equalsIgnoreCase("TABLE")) {
			if (inFieldSet){ // fieldset has a table, ignore it
				return;
			}
			processTable(atts);
		} else if (tagName.equalsIgnoreCase("PANEL")) {
			processPanel(atts);
		} else if (tagName.equalsIgnoreCase("MAINPANEL")) {
			processMainPanel(atts);
		} else if (tagName.equalsIgnoreCase("FONT")) {
			processFont(atts);
		} else if (tagName.equalsIgnoreCase("B") || tagName.equalsIgnoreCase("STRONG")) {
			processFont("BOLD", "");
			defFont = new Font(fntFace, fntStyle, fntSize);
		} else if (tagName.equalsIgnoreCase("I")) {
			processFont("ITALIC", "");
			defFont = new Font(fntFace, fntStyle, fntSize);
		} else if (tagName.equalsIgnoreCase("TD")) {
			if (inFieldSet){ //fieldset has a table, ignore it
				return;
			}
		} else if (tagName.equalsIgnoreCase("P")) {
			addBreak();
		} else if (tagName.equalsIgnoreCase("BR")) {
			// handle where uimeta and uidata are in one td cell for accessibility
			if (lastTagRead.equals("UIMETA")){
				tableY++;
				maxY=tableY; // keep track of max y used for this tr
			}else{
				addBreak();
			}
		} else if (tagName.equalsIgnoreCase("TR")) {
			if (inFieldSet){ // fieldset has a table ignore it
				return;
			}
			processTableRow(atts);
			rowY = tableY; // hang onto y when tr is starting, may need to restore during td
		} else if (tagName.equalsIgnoreCase("BODY")) {
			processBody(atts);
		} else if (tagName.equalsIgnoreCase("UIDATA")) {        	
			processUIData(atts);           
		} else if (tagName.equalsIgnoreCase("UIMETA")) {
			processUIMeta(atts);
		} /*else if (tagName.equalsIgnoreCase("UIITEMDATA")) {
			processUIItemData(atts);
		}*/ else if (tagName.equalsIgnoreCase("CLASSIFY")) {
			processClassification(atts);
		} else if (tagName.equalsIgnoreCase("HTML")) {
		} else if (tagName.equalsIgnoreCase("FIELDSET")) {  // accessibility and JAWS
			inFieldSet = true;
		} else if (tagName.equalsIgnoreCase("LEGEND")){
			//ignore it - needed for BUI CI162
		}else {
			Logger.getLogger(EDIT_PKG_NAME).log(Level.WARNING," Unknown Element: " + tagName);
		}
	}
	
	/**
	 * this is the edit control generation
	 * @param _atts
	 */
	private void processUIData(Attributes _atts) {
		String attCode = null;
		boolean bEditable = false;
		String eType = null;
		String eContext = null; 
		String sKey = null;
		for (int i = 0; i < _atts.getLength(); ++i) {
			String localName = _atts.getLocalName(i);
			if (localName.equalsIgnoreCase("AttributeCode")) {
				attCode = _atts.getValue(i);
			} else if (localName.equalsIgnoreCase("Editable")) {
				if (_atts.getValue(i).equalsIgnoreCase("true")) {
					bEditable = true;
				} else {
					bEditable = false;
				}
			} else if (localName.equalsIgnoreCase("EntityType")) {
				eType = _atts.getValue(i);
			} else if (localName.equalsIgnoreCase("EntityContext")) { 
				eContext = _atts.getValue(i).toUpperCase(); 
			} 
		}

		if (attCode == null && eType == null) {
			generateErrorLabel("UIDATA missing attributeCode and entityType.", getErrorKey());
			return;
		} else if (attCode == null) {
			generateErrorLabel("UIDATA missing attributeCode.", getErrorKey());
			return;
		} else if (eType == null) {
			generateErrorLabel("UIDATA missing entityType.", getErrorKey());
			return;
		}

		sKey = generateImplicatorKey(eType, attCode, eContext);    

		if (bClassified) {
			generateField(sKey,eType, attCode,bEditable); 
		}
	}
	/**
	 * generate the key needed for the attribute
	 * @param _eType
	 * @param _attCode
	 * @param _eContext
	 * @return
	 */
	private String generateImplicatorKey(String _eType, String _attCode, String _eContext) {
		String sKey = null;
		if (_eContext == null) {
			_eContext = deriveContext(formTable.getRSTTable(), _eType);
		}
		sKey = _eType + ":" + _attCode + ":" + _eContext;

		return sKey;
	}

	/**
	 *  lswwcc returned (relator)
	 *  showing lsww (child)
	 * @param _rst
	 * @param _eType
	 * @return
	 */
	private String deriveContext(RowSelectableTable _rst, String _eType) {
		if (_rst != null) {
			int actRow = _rst.getActiveRow();
			if (actRow >= 0 && actRow < _rst.getRowCount()) {
				EANFoundation ean = _rst.getRow(actRow);
				if (ean instanceof EntityItem) {
					EntityGroup eg = ((EntityItem) ean).getEntityGroup();
					if (eg != null) {
						if (eg.isRelator()) {
							return "R";
						} else {
							return "C";
						}
					}
				}
			}
		}
		return "C";
	}
	
	/**
	 * This is the generation of a label for the edit control
	 * @param _atts
	 */
	private void processUIMeta(Attributes _atts) {
		String attCode = null;
		String eType = null;
		String eContext = null; 
		int desc = FormMetaLabel.LONG_DESCRIPTION;
		String sKey = null;
		for (int i = 0; i < _atts.getLength(); ++i) {
			String localName = _atts.getLocalName(i);
			if (localName.equalsIgnoreCase("AttributeCode")) {
				attCode = _atts.getValue(i);
			} else if (localName.equalsIgnoreCase("EntityType")) {
				eType = _atts.getValue(i);
			} else if (localName.equalsIgnoreCase("EntityContext")) { 
				eContext = _atts.getValue(i).toUpperCase(); 
			} else if (localName.equalsIgnoreCase("PROPERTY")) {
				String code = _atts.getValue(i);
				if (code.equalsIgnoreCase("CAPABILITY")) {
					desc = FormMetaLabel.CAPABILITY_DESCRIPTION;
				} else if (code.equalsIgnoreCase("CODE")) {
					desc = FormMetaLabel.CODE_DESCRIPTION;
				} else if (code.equalsIgnoreCase("DEFAULT")) {
					desc = FormMetaLabel.DEFAULT_DESCRIPTION;
				} else if (code.equalsIgnoreCase("HELP")) {
					desc = FormMetaLabel.HELP_DESCRIPTION;
				} else if (code.equalsIgnoreCase("SHORT")) {
					desc = FormMetaLabel.SHORT_DESCRIPTION;
				} else if (code.equalsIgnoreCase("TYPE")) {
					desc = FormMetaLabel.TYPE_DESCRIPTION;
				}
			}
		}

		if (attCode == null && eType == null) {
			generateErrorLabel("UIMETADATA tag missing attributeCode and entityType.", getErrorKey());
			return;
		} else if (attCode == null) {
			generateErrorLabel("UIMETADATA missing attributeCode.", getErrorKey());
			return;
		} else if (eType == null) {
			generateErrorLabel("UIMETADATA missing entityType.", getErrorKey());
			return;
		}

		sKey = generateImplicatorKey(eType, attCode, eContext); 

		if (bClassified) {
			generateMetaField(sKey,eType, attCode, desc);
		}
	}
	/**
	 * get the metaattribute for this key and create the label
	 * @param key
	 * @param _entityType
	 * @param _attributeCode
	 * @param _desc
	 */
	private void generateMetaField(String key,String _entityType, String _attributeCode, int _desc) { 
		int row = formTable.getRSTTable().getRowIndex(key);
		Object o = null;
		if (row < 0) {
			generateErrorLabel("UIMETADATA missing key: " + key, key);
			return;
		}
		o = formTable.getRSTTable().getEANObject(row, 0);
		if (o instanceof EANMetaAttribute){// row 0 is metaattr row 1 is attr
			generateMetaLabel((EANMetaAttribute) o, key, _desc);
		} else {
			generateErrorLabel("UIMETADATA missing attribute entityType: " + _entityType + " attributeCode: " + _attributeCode, key);
		}
	}
	/**
	 * create the meta label and add to the form
	 * @param _meta
	 * @param _key
	 * @param _desc
	 */
	private void generateMetaLabel(EANMetaAttribute _meta, String _key, int _desc) {
		String sKey = "meta:" + _key;
		FormLabel label = null;
		if (!map.containsKey(sKey)) {
			label = new FormMetaLabel(_meta, sKey, _desc);
			vOrder.add(label.getKey());
			map.put(label.getKey(), label);
			addToForm(label);
		} else {
			//if it already exists, it will get moved to a different location because a different
			//constraint is used to add the same component
			Object o = map.get(sKey);
			if (o instanceof FormLabel) {
				addToForm((FormLabel) o);
			}
		}
	}
	/**
	 * Font properties...
	 *
	 *	BOLD	--	makes font bold
	 *	ITALIC	--	makes font italic
	 *	FACE	--	adjusts font face
	 *	COLOR	--	adjusts font color
	 *	SIZE	--	adjusts font size
	 * @param atts
	 */
	private void processFont(Attributes atts) {
		for (int i = 0; i < atts.getLength(); ++i) {
			if (atts.getType(i).equals("CDATA")) {
				processFont(atts.getLocalName(i).toUpperCase(), atts.getValue(i));
			}
		}
	}
	/**
	 * @param type
	 * @param s
	 */
	private void processFont(String type, String s) {
		if (type.equalsIgnoreCase("BOLD")) {
			fntStyle += Font.BOLD;
		} else if (type.equalsIgnoreCase("ITALIC")) {
			fntStyle += Font.ITALIC;
		} else if (type.equalsIgnoreCase("FACE")) {
			fntFace = s;
		} else if (type.equalsIgnoreCase("COLOR")) {
			fntColor = getColor(s.trim());
		} else if (type.equalsIgnoreCase("SIZE")) {
			setFontSize(s);
		} else {
			Logger.getLogger(EDIT_PKG_NAME).log(Level.SEVERE," -- unknown attribute: " + type + ", " + s);
		}
	}
	/**
	 * reset all font settings
	 * @param type
	 */
	private void processEndFont(String type) {
		if (type.equalsIgnoreCase("BOLD")) {
			fntStyle -= Font.BOLD;
		} else if (type.equalsIgnoreCase("ITALIC")) {
			fntStyle -= Font.ITALIC;
		} else if (type.equalsIgnoreCase("FONT")) {
			fntFace =defFace;
			fntColor = defColor;
			fntSize = defSize;
		}
		defFont = new Font(fntFace, fntStyle, fntSize);
	}

	/**
	 * @param s
	 */
	private void setFontSize(String s) {
		if (s.startsWith("+")) {
			fntSize += Routines.getInt(s.substring(1));
		} else if (s.startsWith("-")) {
			fntSize -= Routines.getInt(s.substring(1));
		} else {
			fntSize = Routines.getInt(s);
		}
	}

	/**
	 * get the Color object for the specified string
	 * @param _s
	 * @return
	 */
	private Color getColor(String _s) { 
		if (_s.equalsIgnoreCase("black")) { 
			return Color.black; 
		} else if (_s.equalsIgnoreCase("blue")) { 
			return Color.blue; 
		} else if (_s.equalsIgnoreCase("cyan")) { 
			return Color.cyan; 
		} else if (_s.equalsIgnoreCase("darkgray")) { 
			return Color.darkGray; 
		} else if (_s.equalsIgnoreCase("darkgrey")) { 
			return Color.darkGray; 
		} else if (_s.equalsIgnoreCase("gray")) { 
			return Color.gray; 
		} else if (_s.equalsIgnoreCase("green")) { 
			return Color.green; 
		} else if (_s.equalsIgnoreCase("lightgray")) { 
			return Color.lightGray; 
		} else if (_s.equalsIgnoreCase("lightgrey")) { 
			return Color.lightGray; 
		} else if (_s.equalsIgnoreCase("magenta")) { 
			return Color.magenta; 
		} else if (_s.equalsIgnoreCase("orange")) { 
			return Color.orange; 
		} else if (_s.equalsIgnoreCase("pink")) { 
			return Color.pink; 
		} else if (_s.equalsIgnoreCase("red")) { 
			return Color.red; 
		} else if (_s.equalsIgnoreCase("white")) { 
			return Color.white; 
		} else if (_s.equalsIgnoreCase("yellow")) { 
			return Color.yellow; 
		} else { 
			try { 
				return Color.decode(_s); 
			} catch (NumberFormatException _nfe) { 
				_nfe.printStackTrace(); 
			} 
		} 
		return Color.black; 
	} 

	/**
	 * @param atts
	 */
	private void processMainPanel(Attributes atts) {
		for (int i = 0; i < atts.getLength(); ++i) {
			if (atts.getType(i).equals("CDATA")) {
				processMainPanel(atts.getLocalName(i).toUpperCase(), atts.getValue(i));
			}
		}
	}
	/**
	 * @param type
	 * @param val
	 */
	private void processMainPanel(String type, String val) {
		int i = -1;
		if (bValidate) {
			Logger.getLogger(EDIT_PKG_NAME).log(Level.FINER, type + "," + val + ")");
		}
		if (type.equalsIgnoreCase("BGCOLOR")) {
			Color c = getColor(val.trim());
			mainPane.setBackground(c);
			formTable.setBackground(c);
			bckColor = c;
			defBckColor = c;
			return;
		} else if (type.equalsIgnoreCase("OPAQUE")) {
			if (val.equalsIgnoreCase("true")) {
				mainPane.setOpaque(true);
				formTable.setOpaque(true);
			} else {
				mainPane.setOpaque(false);
				formTable.setOpaque(false);
			}
			return;
		} else if (type.equalsIgnoreCase("INSETS")) { 
			gbca.insets = getInsets(val); 
			return; 
		} else if (type.equalsIgnoreCase("FILL")) { 
			if (val.equalsIgnoreCase("HORIZONTAL")) { 
				gbca.fill = GridBagConstraints.HORIZONTAL; 
			} else if (val.equalsIgnoreCase("VERTICAL")) { 
				gbca.fill = GridBagConstraints.VERTICAL; 
			} else if (val.equalsIgnoreCase("BOTH")) { 
				gbca.fill = GridBagConstraints.BOTH; 
			} else if (val.equalsIgnoreCase("NONE")) { 
				gbca.fill = GridBagConstraints.NONE;
			} 
			return; 
		} else if (type.equalsIgnoreCase("ANCHOR")) { 
			if (val.equalsIgnoreCase("WEST")) { 
				gbca.anchor = GridBagConstraints.WEST; 
			} else if (val.equalsIgnoreCase("EAST")) { 
				gbca.anchor = GridBagConstraints.EAST; 
			} else if (val.equalsIgnoreCase("CENTER")) { 
				gbca.anchor = GridBagConstraints.CENTER; 
			} else if (val.equalsIgnoreCase("NONE")) { 
				gbca.anchor = GridBagConstraints.NONE; 
			} else if (val.equalsIgnoreCase("NORTH")) { 
				gbca.anchor = GridBagConstraints.NORTH; 
			} else if (val.equalsIgnoreCase("NORTHEAST")) { 
				gbca.anchor = GridBagConstraints.NORTHEAST; 
			} else if (val.equalsIgnoreCase("NORTHWEST")) { 
				gbca.anchor = GridBagConstraints.NORTHWEST; 
			} else if (val.equalsIgnoreCase("SOUTH")) { 
				gbca.anchor = GridBagConstraints.SOUTH; 
			} else if (val.equalsIgnoreCase("SOUTHEAST")) { 
				gbca.anchor = GridBagConstraints.SOUTHEAST; 
			} else if (val.equalsIgnoreCase("SOUTHWEST")) { 
				gbca.anchor = GridBagConstraints.SOUTHWEST; 
			} 
			return; 
		} else if (type.equalsIgnoreCase("NAME")) { 
			mainPane.setName(val); 
			return; 
		} 
		i = Routines.getInt(val); 
		if (type.equalsIgnoreCase("X")) { 
			gbca.gridx = i; 
		} else if (type.equalsIgnoreCase("Y")) { 
			gbca.gridy = i; 
		} else if (type.equalsIgnoreCase("WIDTH")) { 
			gbca.gridwidth = i; 
		} else if (type.equalsIgnoreCase("HEIGHT")) { 
			gbca.gridheight = i; 
		} else if (type.equalsIgnoreCase("WEIGHTX")) { 
			gbca.weightx = i; 
		} else if (type.equalsIgnoreCase("WEIGHTY")) { 
			gbca.weighty = i; 
		} else { 
			Logger.getLogger(EDIT_PKG_NAME).log(Level.WARNING," -- unKnown parameter " + type + ", " + val);
		} 
	}
	
	/**
	 * Panel properties...
	 * a panel is a separate collapsible section in the form
	 * 
	 *	INSETS	--	Adjust the panel insets
	 *	FILL	--	Adjust the Fill for the constraints
	 *	ANCHOR	--	Adjust the Anchor for the constraints
	 *	NAME	--	Set the Name of the Panel
	 *	GRIDX	--	set the x position of the constraints
	 *	GRIDY	--	set the y position of the constraints
	 *	WIDTH	--	set the width of the constraints
	 *	HEIGHT	--	set the height of the constraints
	 *	WEIGHTX	--	set the weightx of the constraints
	 *	WEIGHTY	--	set the weighty of the constraints
	 * @param atts
	 */
	private void processPanel(Attributes atts) {
		curPanel = new FormPanel(); 
		curPanel.setBackground(defBckColor); 
		panelConstraints = curPanel.getConstraints(); 
		for (int i = 0; i < atts.getLength(); ++i) {
			if (atts.getType(i).equals("CDATA")) {
				processPanel(atts.getLocalName(i).toUpperCase(), atts.getValue(i));
			}
		}
		curPanel.setNLSTitle(NLS_1);
	}
	/**
	 * setup constraints to use for the panel
	 * @param type
	 * @param val
	 */
	private void processPanel(String type, String val) {
		int i = -1;
		if (bValidate) {
			Logger.getLogger(EDIT_PKG_NAME).log(Level.FINER,"GridBagConstraints," + type + "," + val + ")");
		}
		if (type.equalsIgnoreCase("INSETS")) {
			panelConstraints.insets = getInsets(val);
			return;
		} 
		if (type.equalsIgnoreCase("FILL")) {
			if (val.equalsIgnoreCase("HORIZONTAL")) {
				panelConstraints.fill = GridBagConstraints.HORIZONTAL;
				gbca.fill = GridBagConstraints.HORIZONTAL;
			} else if (val.equalsIgnoreCase("VERTICAL")) {
				panelConstraints.fill = GridBagConstraints.VERTICAL;
				gbca.fill = GridBagConstraints.VERTICAL;
			} else if (val.equalsIgnoreCase("BOTH")) {
				panelConstraints.fill = GridBagConstraints.BOTH;
				gbca.fill = GridBagConstraints.BOTH;
			} else if (val.equalsIgnoreCase("NONE")) {
				panelConstraints.fill = GridBagConstraints.NONE;
				gbca.fill = GridBagConstraints.NONE;
			}
			return;
		} 
		if (type.equalsIgnoreCase("ANCHOR")) {
			if (val.equalsIgnoreCase("WEST")) {
				panelConstraints.anchor = GridBagConstraints.WEST;
				gbca.anchor = GridBagConstraints.WEST;
			} else if (val.equalsIgnoreCase("EAST")) {
				panelConstraints.anchor = GridBagConstraints.EAST;
				gbca.anchor = GridBagConstraints.EAST;
			} else if (val.equalsIgnoreCase("CENTER")) {
				panelConstraints.anchor = GridBagConstraints.CENTER;
				gbca.anchor = GridBagConstraints.CENTER;
			} else if (val.equalsIgnoreCase("NONE")) {
				panelConstraints.anchor = GridBagConstraints.NONE;
				gbca.anchor = GridBagConstraints.NONE;
			} else if (val.equalsIgnoreCase("NORTH")) {
				panelConstraints.anchor = GridBagConstraints.NORTH;
				gbca.anchor = GridBagConstraints.NORTH;
			} else if (val.equalsIgnoreCase("NORTHEAST")) {
				panelConstraints.anchor = GridBagConstraints.NORTHEAST;
				gbca.anchor = GridBagConstraints.NORTHEAST;
			} else if (val.equalsIgnoreCase("NORTHWEST")) {
				panelConstraints.anchor = GridBagConstraints.NORTHWEST;
				gbca.anchor = GridBagConstraints.NORTHWEST;
			} else if (val.equalsIgnoreCase("SOUTH")) {
				panelConstraints.anchor = GridBagConstraints.SOUTH;
				gbca.anchor = GridBagConstraints.SOUTH;
			} else if (val.equalsIgnoreCase("SOUTHEAST")) {
				panelConstraints.anchor = GridBagConstraints.SOUTHEAST;
				gbca.anchor = GridBagConstraints.SOUTHEAST;
			} else if (val.equalsIgnoreCase("SOUTHWEST")) {
				panelConstraints.anchor = GridBagConstraints.SOUTHWEST;
				gbca.anchor = GridBagConstraints.SOUTHWEST;
			}
			return;
		} 
		if (type.equalsIgnoreCase("NAME")) {
			curPanel.setName(val);
			return;
		} 
		if (type.equalsIgnoreCase("BGCOLOR")) {
			curPanel.setBackground(getColor(val.trim()));
			return;
		} 
		if (type.equalsIgnoreCase("OPAQUE")) {
			if (val.equalsIgnoreCase("true")) {
				curPanel.setOpaque(true);
			} else {
				curPanel.setOpaque(false);
			}
			return;															
		} 
		if (type.toUpperCase().startsWith("TITLE")) { 
			int titleNLS = -1;
			if (type.length() == 5) {
				curPanel.addTitle(val.trim(), 1);
				return;
			}
			titleNLS = Routines.getInt(type.substring(5)); 
			curPanel.addTitle(val.trim(), titleNLS); 
			return; 
		} 
		if (type.equalsIgnoreCase("TITLECOLOR")) { 
			curPanel.setTitleColor(getColor(val.trim())); 
			return; 
		} 
		if (type.equalsIgnoreCase("COLLAPSIBLE")) { 
			String s = val.trim(); 
			if (s.equalsIgnoreCase("false")) { 
				curPanel.setCollapsible(false); 
			} else { 
				curPanel.setCollapsible(true);
			} 
			return; 
		} 
		if (type.equalsIgnoreCase("DEFAULTCOLLAPSED")) { 
			String s = val.trim(); 
			if (s.equalsIgnoreCase("true")) { 
				curPanel.setCollapsed(true); 
			} else { 
				curPanel.setCollapsed(false);
			} 
			return; 
		}

		i = Routines.getInt(val);
		if (type.equalsIgnoreCase("X")) {
			gbca.gridx = i;
		} else if (type.equalsIgnoreCase("Y")) {
			gbca.gridy = i + 1; 
		} else if (type.equalsIgnoreCase("WIDTH")) {
			gbca.gridwidth = i;
		} else if (type.equalsIgnoreCase("HEIGHT")) {
			gbca.gridheight = i;
		} else if (type.equalsIgnoreCase("WEIGHTX")) {
			gbca.weightx = i;
		} else if (type.equalsIgnoreCase("WEIGHTY")) {
			gbca.weighty = i;
		} else {
			Logger.getLogger(EDIT_PKG_NAME).log(Level.SEVERE," unKnown parameter: " + type + ", " + val);
		}
	}

	/**
	 * complete the processing for a panel
	 */
	private void processEndPanel() {
		if (bValidate) {
			Logger.getLogger(EDIT_PKG_NAME).log(Level.FINER,"");
		}

		mainPane.add(curPanel,gbca);
		
		curPanel.derefGenVars();
		curPanel = null;
		x = 0;
		y = 1; 
	}
	
	/**
	 * determine the insets
	 * @param str
	 * @return
	 */
	private Insets getInsets(String str) {
		StringTokenizer st = new StringTokenizer(str, ",");
		int[] i = new int[4];
		int tmpX = 0;
		while (st.hasMoreTokens() && tmpX < 4) {
			String s = st.nextToken();
			if (Routines.have(s)) {
				try {
					i[tmpX] = Routines.getInt(s);
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
				}
			}
			++tmpX;
		}
		if (tmpX != 4) {
			Logger.getLogger(EDIT_PKG_NAME).log(Level.SEVERE,"invalid insets declaration: " + str);
			return null;
		}
		return new Insets(i[0], i[1], i[2], i[3]);
	}
	
	/**
	 * starting a TABLE element
	 * @param atts
	 */
	private void processTable(Attributes atts) {
		panelConstraints.fill = GridBagConstraints.BOTH;
		tablePane = new TablePanel();
		tablePane.setHoldX(x);
		tablePane.setHoldY(y);
		// this fills in the constraints to use for laying out this table
		for (int i = 0; i < atts.getLength(); ++i) {
			processTable(atts.getLocalName(i), atts.getValue(i));
		}

		curPanel.add(tablePane,tablePane.getConstraints());
	}

	/**
	 * fill in the constraints to use for this table based on values in the form
	 * @param type
	 * @param val
	 */
	private void processTable(String type, String val) {
		 if (type.toUpperCase().equals("FILL")) {
			if (val.equalsIgnoreCase("HORIZONTAL")) {
				tablePane.setFill(GridBagConstraints.HORIZONTAL);
			} else if (val.equalsIgnoreCase("VERTICAL")) {
				tablePane.setFill(GridBagConstraints.VERTICAL);
			} else if (val.equalsIgnoreCase("BOTH")) {
				tablePane.setFill(GridBagConstraints.BOTH);
			} else if (val.equalsIgnoreCase("NONE")) {
				tablePane.setFill(GridBagConstraints.NONE);
			}
		} else if (type.equalsIgnoreCase("ANCHOR")) {
			if (val.equalsIgnoreCase("WEST")) {
				tablePane.setAnchor(GridBagConstraints.WEST);
			} else if (val.equalsIgnoreCase("EAST")) {
				tablePane.setAnchor(GridBagConstraints.EAST);
			} else if (val.equalsIgnoreCase("CENTER")) {
				tablePane.setAnchor(GridBagConstraints.CENTER);
			} else if (val.equalsIgnoreCase("NONE")) {
				tablePane.setAnchor(GridBagConstraints.NONE);
			} else if (val.equalsIgnoreCase("NORTH")) {
				tablePane.setAnchor(GridBagConstraints.NORTH);
			} else if (val.equalsIgnoreCase("NORTHEAST")) {
				tablePane.setAnchor(GridBagConstraints.NORTHEAST);
			} else if (val.equalsIgnoreCase("NORTHWEST")) {
				tablePane.setAnchor(GridBagConstraints.NORTHWEST);
			} else if (val.equalsIgnoreCase("SOUTH")) {
				tablePane.setAnchor(GridBagConstraints.SOUTH);
			} else if (val.equalsIgnoreCase("SOUTHEAST")) {
				tablePane.setAnchor(GridBagConstraints.SOUTHEAST);
			} else if (val.equalsIgnoreCase("SOUTHWEST")) {
				tablePane.setAnchor(GridBagConstraints.SOUTHWEST);
			}
		} else if (type.toUpperCase().equals("CELLFILL")) {
			if (val.equalsIgnoreCase("HORIZONTAL")) {
				panelConstraints.fill = GridBagConstraints.HORIZONTAL;
			} else if (val.equalsIgnoreCase("VERTICAL")) {
				panelConstraints.fill = GridBagConstraints.VERTICAL;
			} else if (val.equalsIgnoreCase("BOTH")) {
				panelConstraints.fill = GridBagConstraints.BOTH;
			} else if (val.equalsIgnoreCase("NONE")) {
				panelConstraints.fill = GridBagConstraints.NONE;
			}
		} else if (type.equalsIgnoreCase("CELLANCHOR")) {
			if (val.equalsIgnoreCase("WEST")) {
				panelConstraints.anchor = GridBagConstraints.WEST;
			} else if (val.equalsIgnoreCase("EAST")) {
				panelConstraints.anchor = GridBagConstraints.EAST;
			} else if (val.equalsIgnoreCase("CENTER")) {
				panelConstraints.anchor = GridBagConstraints.CENTER;
			} else if (val.equalsIgnoreCase("NONE")) {
				panelConstraints.anchor = GridBagConstraints.NONE;
			} else if (val.equalsIgnoreCase("NORTH")) {
				panelConstraints.anchor = GridBagConstraints.NORTH;
			} else if (val.equalsIgnoreCase("NORTHEAST")) {
				panelConstraints.anchor = GridBagConstraints.NORTHEAST;
			} else if (val.equalsIgnoreCase("NORTHWEST")) {
				panelConstraints.anchor = GridBagConstraints.NORTHWEST;
			} else if (val.equalsIgnoreCase("SOUTH")) {
				panelConstraints.anchor = GridBagConstraints.SOUTH;
			} else if (val.equalsIgnoreCase("SOUTHEAST")) {
				panelConstraints.anchor = GridBagConstraints.SOUTHEAST;
			} else if (val.equalsIgnoreCase("SOUTHWEST")) {
				panelConstraints.anchor = GridBagConstraints.SOUTHWEST;
			}
		} else if (type.equalsIgnoreCase("X")) {
			tablePane.setX(Routines.getInt(val));
		} else if (type.equalsIgnoreCase("Y")) {
			tablePane.setY(Routines.getInt(val) + 1);
		} else if (type.equalsIgnoreCase("WEIGHTX")) {
			tablePane.setWeightX(Routines.getInt(val));
		} else if (type.equalsIgnoreCase("WEIGHTY")) {
			tablePane.setWeightY(Routines.getInt(val));
		} else if (type.equalsIgnoreCase("GRIDWIDTH")) {
			tablePane.setGridWidth(Routines.getInt(val));
		} else if (type.equalsIgnoreCase("GRIDHEIGHT")) {
			tablePane.setGridHeight(Routines.getInt(val));
		} else if (type.equalsIgnoreCase("border")) {
			// ignore it
		} else if (type.equalsIgnoreCase("width")) {
			// ignore it
		} else {
			Logger.getLogger(EDIT_PKG_NAME).log(Level.WARNING,"unknown table tag: " + type + ", " + val);
		}
	}
	/**
	 * complete the table processing
	 */
	private void processEndTable() {
		panelConstraints.fill = GridBagConstraints.NONE;
		panelConstraints.anchor = GridBagConstraints.WEST;
		tableX = 0;
		tableY = 1;
		x = tablePane.getAdjustedX();
		y = tablePane.getAdjustedY();
		
		tablePane.derefGenVars();
		tablePane = null;
	}
	/**
	 * @param atts
	 */
	private void processTableRow(Attributes atts) {
		for (int i = 0; i < atts.getLength(); ++i) {
			processTableRow(atts.getLocalName(i), atts.getValue(i));
		}
	}
	/**
	 * @param type
	 * @param val
	 */
	private void processTableRow(String type, String val) {
		if (type.toUpperCase().equals("BGCOLOR")) {
			tablePane.setRowBgColor(getColor(val));
		}
	}
	/**
	 * complete table cell and row processing
	 * @param s
	 */
	private void processEndTdTr(String s) {
		if (s.equalsIgnoreCase("DETAIL")) {
			++tableX;
		} else if (s.equalsIgnoreCase("ROW")) {
			tableX = 0;
			// fieldset may have used 2 'rows' for one tr, account for it here
			if (maxY>tableY){
				tableY=maxY;
			}
			++tableY;
			tablePane.setRowBgColor(null);
		}
	}
	
	/**
	 * add a line break
	 */
	private void addBreak() {
		++y;
		x = 0;
	}

	/**
	 * @param _atts
	 */
	private void processClassification(Attributes _atts) {
		String eType = null;
		String sName = null;
		int ii = -1;
		if (_atts == null) {
			bClassified = true;
			return;
		}
		ii = _atts.getLength();
		for (int i = 0; i < ii; ++i) {
			if (_atts.getType(i).equals("CDATA")) {
				if (_atts.getLocalName(i).equalsIgnoreCase("ENTITYTYPE")) {
					eType = _atts.getValue(i);
				} else if (_atts.getLocalName(i).equalsIgnoreCase("NAME")) {
					sName = _atts.getValue(i);
				}
			}
		}
		if (eType != null && sName != null) {
			if (currentItem != null) {
				bClassified = currentItem.testClassification(eType, sName);
			} else {
				bClassified = true;
			}
		} else {
			bClassified = true;
		}
	}

	/**
	 * process body tag
	 * @param atts
	 */
	private void processBody(Attributes atts) {
		for (int i = 0; i < atts.getLength(); ++i) {
			if (atts.getType(i).equals("CDATA")) {
				processBody(atts.getLocalName(i), atts.getValue(i));
			}
		}
	}

	/**
	 * Body properties...
	 *
	 *	TEXT		--	Adjusts the Text Color
	 *	BGCOLOR		--	Adjusts the background color
	 *	FACE		--	Adjusts the font
	 *	SIZE		--	Adjusts the font size
	 * @param att
	 * @param val
	 */
	private void processBody(String att, String val) {
		if (att.equalsIgnoreCase("TEXT")) {
			Color c = getColor(val.trim());
			fntColor = c;
			defColor = c;
		} else if (att.equalsIgnoreCase("BGCOLOR")) {
			Color c = getColor(val.trim());
			if (curPanel != null) {
				curPanel.setBackground(c);
			}
			bckColor = c;
			defBckColor = c;
		} else if (att.equalsIgnoreCase("FACE")) {
			defFace = val;
			fntFace = val;
		} else if (att.equalsIgnoreCase("SIZE")) {
			defSize = Routines.getInt(val);
			fntSize = Routines.getInt(val);
		} else if (att.equalsIgnoreCase("LINK")) {
			return;
		} else if (att.equalsIgnoreCase("VLINK")) {
			return;
		} else if (att.equalsIgnoreCase("ALINK")) {
			return;
		} else {
			Logger.getLogger(EDIT_PKG_NAME).log(Level.WARNING,"unknown Body Tag: " + att);
		}
	}

	/**
	 * create a label with the error information
	 * @param _text
	 * @param _key
	 */
	private void generateErrorLabel(String _text, String _key) {
		if (map.containsKey(_key)) {
			Object o = map.get(_key);
			if (o instanceof FormLabel) {
				//if it already exists, it will get moved to a different location because a different
				//constraint is used to add the same component, this can happen if meta is missing for
				// the label and the editcontrol
				addToForm((FormLabel) o);
				((FormLabel)o).setOpaque(false);// dont want background to show thru error labels
			}
		} else {
			FormLabel formlbl = new FormErrorLabel("***   " + _text + "   ***", _key);
			map.put(formlbl.getKey(), formlbl);
			vOrder.add(formlbl.getKey());
			addToForm(formlbl);
			formlbl.setOpaque(false);// dont want background to show thru error labels
			Logger.getLogger(EDIT_PKG_NAME).log(Level.SEVERE,"Error in XML: "+XML); 
		}
		Logger.getLogger(EDIT_PKG_NAME).log(Level.SEVERE,"Error in XML tags: " + _text + ", " + _key);    	
	}

	/**
	 * get a unique error key
	 * @return
	 */
	private String getErrorKey() {
		++iErrorCount;
		return "Error:" + iErrorCount;
	}

	/**
	 * create the field in the form for this attribute
	 * @param key
	 * @param _entityType
	 * @param _attributeCode
	 * @param editable
	 */
	private void generateField(String key,String _entityType, String _attributeCode, boolean editable) { 
		int row = -1;
		Object o = null;
		if (bValidate) {
			Logger.getLogger(EDIT_PKG_NAME).log(Level.FINER,"generateField(" + _entityType + ", " + _attributeCode + ")");
		}

		row = formTable.getRSTTable().getRowIndex(key);

		if (row < 0) {
			generateErrorLabel("UIDATA missing key: " + key, key);
			return;
		}
		o = formTable.getRSTTable().getEANObject(row, 1); // row 0 is metaattr row 1 is attr
		if (o instanceof EANAttribute) {
			addToForm((EANAttribute) o, key, editable);
		} else {
			generateErrorLabel("UIDATA missing attribute entityType: " + _entityType + " attributeCode: " + _attributeCode, key);
		}
	}

	/**
	 * add an editor to the form for this attribute
	 * @param _att
	 * @param _key
	 * @param editable
	 */
	private void addToForm(EANAttribute _att, String _key, boolean editable) {
		if (bValidate) {
			Logger.getLogger(EDIT_PKG_NAME).log(Level.FINER,"(" + _att.getKey() + ", " + _key + ") value:"+_att);
		}

		if (map.containsKey(_key)) {
			Object o = map.get(_key);
			if (o instanceof JComponent) {
				JComponent jc = (JComponent) o;
				if (o instanceof FormCellPanel) {
	    			FormCellPanel fl = (FormCellPanel) o;
	    			if(!editable){
	    				fl.setUneditableOverride();
	    			}
	    			setUIValues(fl);
	    			fl.updateMode();
	            }
				//if it already exists, it will get moved to a different location because a different
				//constraint is used to add the same component
				addToForm(jc);
			}
		}else{
			FormCellPanel fcp = null;
			if (_att instanceof SingleFlagAttribute) {
				fcp = new FlagCellPanel(_key, _att, formTable);  	 
			} else if (_att instanceof StatusAttribute) {
				fcp = new FlagCellPanel(_key, _att, formTable);  
			} else if (_att instanceof TaskAttribute) { 
				fcp = new FlagCellPanel(_key, _att, formTable);  
			} else if (_att instanceof MultiFlagAttribute) {	
				fcp = new MultiCellPanel(_key, _att, formTable);  
			} else if (_att instanceof LongTextAttribute) {
				fcp = new LongCellPanel(_key, _att, formTable);  
			} else if (_att instanceof XMLAttribute) {
				fcp = new XMLCellPanel(_key, _att, formTable);  
			} else if (_att instanceof BlobAttribute) {
				fcp = new BlobCellPanel(_key, _att, formTable);  
			} else if (_att instanceof TextAttribute) {
				TextAttribute txt = (TextAttribute) _att;
				EANMetaAttribute meta = txt.getMetaAttribute();
				if (meta.isDate()) {
					fcp = new DateCellPanel(_key, txt, formTable);  
				} else if (meta.isTime()) {
					fcp = new TimeCellPanel(_key, txt, formTable);  
				} else {
					fcp = new TextCellPanel(_key, txt, formTable);          
				}
			}
			if(fcp!=null){
				if(!editable){
					fcp.setUneditableOverride();
				}
				setUIValues(fcp); // must be done before updatemode

				fcp.updateMode();
				vOrder.add(fcp.getRSTKey());
				map.put(fcp.getRSTKey(), fcp);
				addToForm(fcp); 
			}
		}
	}

	/**
	 * add the label or the edit control to the form
	 * @param _jc
	 */
	private void addToForm(JComponent jc) {
		if(jc instanceof FormLabel){
			//FormCellPanel were done before because they create a renderer using the UI values
			setUIValues(jc);
		}

		buildPanelConstraints();

		if (tablePane!=null){
			Color rowColor = tablePane.getRowBgColor();
			if (rowColor != null) {
				jc.setBackground(rowColor);
				jc.setOpaque(true);
			}
			tablePane.add(jc,panelConstraints);
		} else {
			curPanel.add(jc,panelConstraints);
			++x;
		}

	}
	
	/**
	 * set values for this component used to render in the UI
	 * @param jc
	 * @return
	 */
	private void setUIValues(JComponent jc) {
		jc.setForeground(fntColor);
		jc.setBackground(bckColor);
		jc.setFont(defFont);
		jc.setOpaque(cmpOpaque);
		if (cmpToolTip != null) {
			jc.setToolTipText(cmpToolTip);
		}
	}
	
	/**
	 * update constraints to use for laying out this component
	 */
	private void buildPanelConstraints() {
		if (tablePane!=null) {
			panelConstraints.gridx = tableX;
			panelConstraints.gridy = tableY;
		} else {
			panelConstraints.gridx = x;
			panelConstraints.gridy = y;
		}
		panelConstraints.gridwidth = 1;
		panelConstraints.gridheight = 1;
		panelConstraints.weightx = 0;
		panelConstraints.weighty = 0;
	}

    /**
     * called by formtable when state has changed, like lock/unlock
     */
    public void refreshForm() {
        for (int i = 0; i < mainPane.getComponentCount(); ++i) { 
            Component c = mainPane.getComponent(i);
            if (c instanceof FormPanel) {
            	refreshFormPanel((FormPanel) c);
            } else if (c instanceof TablePanel) {
            	refreshTablePanel((TablePanel) c);
            }
        }
    }

    /**
     * @param _fp
     */
    private void refreshFormPanel(FormPanel _fp) {
        for (int i = 0; i < _fp.getComponentCount(); ++i) { 
            Component c = _fp.getComponent(i);
            if (c instanceof FormLabel) {
            	FormLabel fl = (FormLabel) c;
            	refreshLabel(fl);
            } else if (c instanceof TablePanel) {
            	refreshTablePanel((TablePanel) c);
            }else if (c instanceof FormCellPanel) {
            	FormCellPanel fl = (FormCellPanel) c;
            	fl.updateMode();
            }
        }
        _fp.revalidate();
    }

    /**
     * @param _tp
     */
    private void refreshTablePanel(TablePanel _tp) {
        for (int i = 0; i < _tp.getComponentCount(); ++i) { 
            Component c = _tp.getComponent(i);
            if (c instanceof FormLabel) {
                FormLabel fl = (FormLabel) c;
                refreshLabel(fl);
            } else if (c instanceof FormCellPanel) {
    			FormCellPanel fl = (FormCellPanel) c;
    			fl.updateMode();
            }
        }
        _tp.revalidate();
    }

    /**
     * @param _lbl
     */
    private void refreshLabel(FormLabel _lbl) {
        if (_lbl instanceof FormMetaLabel){
        	String key = _lbl.getKey();
        	int row = formTable.getRSTTable().getRowIndex(key);
        	if (row < 0) {
        		return;
        	}
        	Object o = formTable.getRSTTable().getEANObject(row, 0); // row 0 is metaattr row 1 is attr
        	if (o instanceof EANMetaAttribute) {
        		((FormMetaLabel)_lbl).refresh((EANMetaAttribute) o);
        	}
        } else if (_lbl instanceof FormEntityLabel) {
            if (currentItem == null) {
                return;
            }
            ((FormEntityLabel)_lbl).refresh(currentItem);
        }
    }

}
