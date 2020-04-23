//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.


package COM.ibm.eannounce.abr.util;

import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.diff.DiffEntity;
import java.util.*;
/**********************************************************************************
 * Class used to hold info and structure for generating a ss row
 */
//$Log: XLRow.java,v $
//Revision 1.1  2008/09/30 12:36:19  wendy
//CQ00006066-WI LA CTO - EACM - Support GFS data Load of QSM (report)
//

public class XLRow {
	private Object rowitem; // EntityItem or DiffEntity
	private String rowkey;
	private Hashtable rowItemTbl = new Hashtable();

	/*************************************
	 * Constructor
	 * @param ei
	 */
	public XLRow(Object ei){
		rowitem = ei;	
		if (ei instanceof EntityItem){
			rowkey = ((EntityItem)ei).getKey();
		}
		if (ei instanceof DiffEntity){
			rowkey = ((DiffEntity)ei).getKey();
		}
		addRowItem(ei);
	}
	/*************************************
	 * Add an entity that is used for one or more of the cell values
	 * @param ei
	 */
	public void addRowItem(Object ei){
		if (ei instanceof EntityItem){
			rowItemTbl.put(((EntityItem)ei).getEntityType(),ei);
		}
		if (ei instanceof DiffEntity){
			rowItemTbl.put(((DiffEntity)ei).getEntityType(),ei);
		}
	}
	/*************************************
	 * Get the row type
	 * @return
	 */
	public String getRowType() {
		String type = "";
		if (rowitem instanceof EntityItem){
			type = ((EntityItem)rowitem).getEntityType();
		}
		if (rowitem instanceof DiffEntity){
			type = ((DiffEntity)rowitem).getEntityType();
		}
		return type;
	}
	/*************************************
	 * Get the item controlling the row
	 * @return Object (either EntityItem or DiffEntity)
	 */
	public Object getRowItem() { return rowitem;}
	/*************************************
	 * Get the items in the row
	 * @return Hashtable
	 */
	public Hashtable getItemTbl() { return rowItemTbl; }
	/*************************************
	 * Get the key for this row
	 * @return
	 */
	public String getRowKey() { return rowkey;}
	/*************************************
	 * release memory
	 */
	public void dereference(){
		rowitem=null;
		rowkey=null;
		rowItemTbl.clear();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		StringBuffer sb = new StringBuffer("RowItem: "+rowkey+" others:");
		Collection col = rowItemTbl.values();
		Iterator itr = col.iterator();
		if (rowitem instanceof EntityItem){
			while(itr.hasNext()){
				String eikey = ((EntityItem)itr.next()).getKey();
				if (!eikey.equals(rowkey)){
					sb.append(" "+eikey);
				}
			}
		}
		if (rowitem instanceof DiffEntity){		
			while(itr.hasNext()){
				String eikey = ((DiffEntity)itr.next()).getKey();
				if (!eikey.equals(rowkey)){
					sb.append(" "+eikey);
				}
			}
		}
		
		return sb.toString();
	}
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) // used by Vector.contains()
    {
        XLRow row = (XLRow)obj;
        if (!row.getRowKey().equals(this.getRowKey())){
        	return false;
        }
		Collection valcol = this.getItemTbl().values();
		Collection keycol = this.getItemTbl().keySet();
		if(valcol.containsAll(row.getItemTbl().values())&&
				keycol.containsAll(row.getItemTbl().keySet())){
			return true;
		}
		return false;
    }
}
