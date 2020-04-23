//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;

import COM.ibm.eannounce.objects.*;

import com.ibm.transform.oim.eacm.diff.DiffEntity;
import com.ibm.transform.oim.eacm.util.*;

/**********************************************************************************
* Class used to generate a column for LSEOBUNDLE
*SysOrOpt	If LSEOBUNDLE.BUNDLETYPE = Hardware then "Initial" else "Both"
*/
// $Log: XLSysOrOptColumn.java,v $
// Revision 1.3  2015/02/04 18:42:47  stimpsow
// IN5947420 - allow for missing prev or current item
//
// Revision 1.2  2009/02/04 21:22:46  wendy
// CQ00016165 - Automated QSM feed from ePIMS HW to support the late change request from BIDS
//
// Revision 1.1  2008/09/30 12:36:19  wendy
// CQ00006066-WI LA CTO - EACM - Support GFS data Load of QSM (report)
//
//

public class XLSysOrOptColumn extends XLColumn 
{
	private static final String HARDWARE="100";
	private static final Set TESTSET;
	static{
		TESTSET = new HashSet();
		TESTSET.add(HARDWARE);
	}

	/**********************************************************************************
	 * Constructor 
	 */
	public XLSysOrOptColumn()
	{
		super("SysOrOpt","LSEOBUNDLE","BUNDLETYPE");
	}
	/*********************************************
	 * check for changes
	 * @param diffitem
	 * @return
	 */
	protected boolean isChanged(DiffEntity diffitem){
		boolean chgsFound;
		EntityItem curritem = diffitem.getCurrentEntityItem();
		EntityItem previtem = diffitem.getPriorEntityItem();

		if (curritem!=null && previtem!=null){ //IN5947420
			chgsFound = PokUtils.contains(curritem, attrCode, TESTSET) != 
					PokUtils.contains(previtem, attrCode, TESTSET);
		}else{
			//either the entity was added or deleted
			chgsFound=true;
		}
	
		return chgsFound;
	}
	
	/**********************************************************************************
	 * get value for this column
	 * If LSEOBUNDLE.BUNDLETYPE = Hardware then "Initial" else "Both"
	 * BUNDLETYPE	100	Hardware
	 * BUNDLETYPE	101	Software
	 * BUNDLETYPE	102	Service
	 * @see COM.ibm.eannounce.abr.util.XLColumn#getValue(org.apache.poi.hssf.usermodel.HSSFCell, COM.ibm.eannounce.objects.EntityItem)
	 */
	protected void getValue(HSSFCell cell, EntityItem item) {
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(new HSSFRichTextString(getValue(item)));
	}	
	
	/**********************************************************************************
	 * get the value for this column
	 * CQ00016165
	 * @param item
	 */
	protected String getValue(EntityItem item) {
		String bdltype = "Both";
		if (item!= null && PokUtils.contains(item, attrCode, TESTSET)){
			bdltype = "Initial";
		}
		return bdltype;
	}	
}
