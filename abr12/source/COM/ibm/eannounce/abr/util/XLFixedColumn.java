//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.


package COM.ibm.eannounce.abr.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import com.ibm.transform.oim.eacm.diff.DiffEntity;
import COM.ibm.eannounce.objects.*;

/**********************************************************************************
 * Class used to generate a column for a fixed value in a ss
 *
 */
//$Log: XLFixedColumn.java,v $
//Revision 1.2  2009/02/04 21:22:46  wendy
//CQ00016165 - Automated QSM feed from ePIMS HW to support the late change request from BIDS
//
//Revision 1.1  2008/09/30 12:36:19  wendy
//CQ00006066-WI LA CTO - EACM - Support GFS data Load of QSM (report)
//


public class XLFixedColumn extends XLColumn 
{
	/**********************************************************************************
	 * Constructor 
	 *
	 *@param nname String with name of column to be created
	 *@param type String with fixed value
	 */
	public XLFixedColumn(String nname, String type)
	{
		super(nname,type,null);
	}
	/*********************************************
	 * check for changes
	 * @param diffitem
	 * @return
	 */
	protected boolean isChanged(DiffEntity diffitem){
		return false;
	}
	
	/**********************************************************************************
	 * get value for this column
	 * @see COM.ibm.eannounce.abr.util.XLColumn#getValue(org.apache.poi.hssf.usermodel.HSSFCell, COM.ibm.eannounce.objects.EntityItem)
	 */
	protected void getValue(HSSFCell cell, EntityItem item) {
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(new HSSFRichTextString(etype));
	}

	/**********************************************************************************
	 * get the value for this column
	 * CQ00016165
	 * @param item
	 */
	protected String getValue(EntityItem item) {
		return etype;
	}
	/**********************************************************************************
	 * string rep
	 *
	 *@return String
	 */
	public String toString() {
		return "Column:"+getColumnLabel()+" fixed:"+etype;
	}
}
