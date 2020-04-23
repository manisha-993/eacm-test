//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.


package COM.ibm.eannounce.abr.util;

import COM.ibm.eannounce.objects.*;

import com.ibm.transform.oim.eacm.diff.DiffEntity;
import com.ibm.transform.oim.eacm.util.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;

/**********************************************************************************
 * Base Class used to hold info and structure to generate a ss cell/column
 *
 */
//$Log: XLColumn.java,v $
//Revision 1.3  2009/07/30 18:01:54  wendy
//Prevent NPE if DiffEntity has a null current or prev item
//
//Revision 1.2  2009/02/04 21:22:46  wendy
//CQ00016165 - Automated QSM feed from ePIMS HW to support the late change request from BIDS
//
//Revision 1.1  2008/09/30 12:36:19  wendy
//CQ00006066-WI LA CTO - EACM - Support GFS data Load of QSM (report)
//
public class XLColumn
{
	private static final String BLANKS="                                                                                ";
    
	public static final int ATTRVAL = 0; // get value from attribute
	public static final int FLAGVAL = 1; // get value from flag code
	public static final boolean LEFT = true;
	public static final boolean RIGHT = false;
	
	private static final char[] FOOL_JTEST = {'\n'};
	static final String NEWLINE = new String(FOOL_JTEST);

	private String colName;
	private String ffColName;
	protected String etype =null;
	protected String attrCode =null; 
	private int colWidth=0; //CQ00016165
	private boolean isLeftJustified = LEFT; //CQ00016165
	private int attrSrc = ATTRVAL;
	protected boolean alwaysShow = false;
	public void setAlwaysShow(){  alwaysShow = true;}

	/**********************************************************************************
	 * Constructor 
	 *
	 *@param nname String with name of column to be created
	 *@param type String with entity type
	 *@param code String with attribute code
	 */
	public XLColumn(String nname, String type, String code)
	{
		this(nname,type,code,ATTRVAL);
	}

	/**********************************************************************************
	 * Constructor 
	 *
	 *@param cname String with name of column to be created
	 *@param type String with entity type
	 *@param code String with attribute code
	 *@param src int for flag attributes
	 */
	public XLColumn(String cname, String type, String code, int src)
	{
		colName = cname;
		etype = type;
		attrCode = code;
		attrSrc = src;
	}

	/**********************************************************************************
	 * get the column name
	 *@return String
	 */
	public String getColumnLabel() {
		return colName;
	}

	/**********************************************************************************
	 * get the column name for the flatfile
	 *@return String
	 */
	public String getFFColumnLabel() {
		return ffColName;
	}
	/**********************************************************************************
	 * set the column name for the flatfile
	 */
	public void setFFColumnLabel(String s) {
		ffColName = s;
	}	
	/**********************************************************************************
	 * set value for this column - used when creating ss
	 *
	 */
	public void setColumnValue(HSSFCell cell,Hashtable itemTbl) {
		Object obj = itemTbl.get(etype);
		if (obj instanceof DiffEntity){
			DiffEntity diff = (DiffEntity)obj;
			if (alwaysShow || isChanged(diff)){
				getValue(cell,diff.getCurrentEntityItem());
			}
		}else{
			getValue(cell,(EntityItem)obj);
		}
	}
	
	/**********************************************************************************
	 * get value for this column, it will be padded or truncated to meet column width
	 * CQ00016165
	 */
	public String getColumnValue(Hashtable itemTbl) {
		Object obj = itemTbl.get(etype);
		String value = "";
		if (obj instanceof DiffEntity){
			DiffEntity diff = (DiffEntity)obj;
			if (alwaysShow || isChanged(diff)){
				value = getValue(diff.getCurrentEntityItem());
			}
		}else{
			value = getValue((EntityItem)obj);
		}
		return formatToWidth(value, colWidth,isLeftJustified);
	}

	/*****************************************
	 * Set the width used for fixed columns (used for flat file generation)
	 * CQ00016165
	 * @param len
	 */
	public void setColumnWidth(int len){ colWidth = len; }
	
	/*****************************************
	 * Set the justification for this column (used for flat file generation)
	 * CQ00016165
	 * @param b
	 */
	public void setJustified(boolean b){ isLeftJustified = (b==LEFT); }
	
	/****************************************
	 * Get the width used for fixed columns (used for flat file generation)
	 *  CQ00016165
	 * @return int
	 */
	public int getColumnWidth(){ return colWidth; }

	/**********************************************************************************
	 * fill in value for this attribute code from this entity
	 *
	 * @param cell
	 * @param item
	 */
	protected void getValue(HSSFCell cell,EntityItem item) {
		if (item==null){
			return;
		}

		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(new HSSFRichTextString(getValue(item)));
	}
	/**********************************************************************************
	 * get the value for this attribute code from this entity
	 *
	 * @param item
	 */
	protected String getValue(EntityItem item) {
		String value = "";
		if (item!=null){
			EntityGroup egrp = item.getEntityGroup();		

			EANMetaAttribute metaAttr = egrp.getMetaAttribute(attrCode);
			if (metaAttr==null) {			
				value = "Error: Attribute "+attrCode+" not found in "+
				item.getEntityType()+" META data.";
			}else{ // meta exists for this attribute
				EANAttribute att = item.getAttribute(attrCode);
				if (att instanceof EANTextAttribute){
					value = att.toString();
				}else{					
					if (attrSrc == FLAGVAL){
						if(metaAttr.getAttributeType().equals("U")){ //Unique Flag and flagcode needed
							EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute(attrCode);
							if (fAtt!=null && fAtt.toString().length()>0){
								// Get the selected Flag code
								MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
								for (int i = 0; i < mfArray.length; i++){
									// get selection
									if (mfArray[i].isSelected()){
										value =mfArray[i].getFlagCode();
										break;
									}  // metaflag is selected
								}// end of flagcodes
							}
						}else if(metaAttr.getAttributeType().equals("F")){ //MultiFlagAttribute
							StringBuffer sbb = new StringBuffer();
							// get countrylist attr, it is F
							EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute(attrCode);
							if (fAtt!=null && fAtt.toString().length()>0){
								// Get the selected Flag codes.
								MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
								for (int i = 0; i < mfArray.length; i++){
									// get selection
									if (mfArray[i].isSelected()){
										if (sbb.length()>0){
											sbb.append(", ");
										}
										// get flagcode instead of flag value here
										sbb.append(mfArray[i].getFlagCode());										
									}  // metaflag is selected
								}// end of flagcodes		
							}
							value = sbb.toString();
						}	
					}else{
						value =	PokUtils.getAttributeValue(item, attrCode,", ", "", false);
					}		
				}
			} // end meta ok
		}
		
		return value;
	}	

	//================================================================================
	// methods for DiffEntity
	//================================================================================
	/***********************************************
	 *  is this column changed?
	 *
	 *@return boolean
	 */
	public boolean isChanged(Hashtable itemTbl){
		boolean chgsFound=false;
		
		DiffEntity diffitem = (DiffEntity)itemTbl.get(etype);
		if (diffitem != null){
			if (diffitem.isDeleted() || diffitem.isNew()){
				chgsFound = true;
			}else{
				chgsFound = isChanged(diffitem);
			}
		}
		return chgsFound;
	}
	/*********************************************
	 * check for changes
	 * @param diffitem
	 * @return
	 */
	protected boolean isChanged(DiffEntity diffitem){
		boolean chgsFound=false;
		// must check the attribute value
		EntityItem curritem = diffitem.getCurrentEntityItem();
		EntityItem previtem = diffitem.getPriorEntityItem();
		String currVal = "";
		if (curritem!=null){
			currVal = PokUtils.getAttributeValue(curritem, attrCode,", ", "", false);
		}
		String prevVal = "";
		if (previtem!=null){
			prevVal = PokUtils.getAttributeValue(previtem, attrCode,", ", "", false);
		}
		chgsFound = !currVal.equals(prevVal);
		
		return chgsFound;
	}
	/********************************************************************************
	 * Format string to specifed length, padded with blank if necessary
	    if i edit COMMENTS in the BUI, new lines are saved as '\r\n'..
	    if i edit COMMENTS in the JUI, new lines are '\n'.. causes problems with character counts
	    CQ00016165
	 * @param data String to format
	 * @param len int with length to set
	 * @return String formatted to x chars long
	 */
	public static String formatToWidth(String data, int len)
	{
		return formatToWidth(data, len, LEFT);
	}	
	
	/********************************************************************************
	 * Format string to specifed length, padded with blank if necessary
	    if i edit COMMENTS in the BUI, new lines are saved as '\r\n'..
	    if i edit COMMENTS in the JUI, new lines are '\n'.. causes problems with character counts
	    CQ00016165
	 * @param data String to format
	 * @param len int with length to set
	 * @return String formatted to x chars long
	 */
	public static String formatToWidth(String data, int len, boolean justified)
	{
		if (len==0){ // no adjustments done
			return data;
		}
		if (data ==null) {
			data =""; 
		}
		String adjData = data;
		int dataLen = data.length();
		if (dataLen != len){
			StringBuffer tmp = new StringBuffer(data);
			// remove new line characters
			data = data.replace('\n', ' ');
			data = data.replace('\r', ' ');
			if (justified==LEFT){
				tmp.append(BLANKS);
			}else{
				int diff = len-dataLen;
				if (diff>0){
					tmp.insert(0, BLANKS.substring(0,diff));
				}
			}
			tmp.setLength(len);
			adjData = tmp.toString();
		}
		return adjData;
	}		
	/**********************************************************************************
	 * string rep
	 *
	 *@return String
	 */
	public String toString() {
		return "Column:"+colName+" type:"+etype+" attr:"+attrCode;
	}
}
