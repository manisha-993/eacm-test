// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2006, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.diff;

import java.io.*;
import java.util.*;
import java.text.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.*;

/**********************************************************************************
* This class is created when the VE is flattened.  It represents the entityitem and
* it's position in the VE.  This object's hashkey is used by the diff algo to determine
* if it was added or deleted.  The change found by the diff algo is set in this using one
* of the Diffable callbacks.
* The jsp determines if this object should be displayed or not.  The jsp can set mustHide
* or mustDisplay to override other checks.
*
*/
// $Log: DiffEntity.java,v $
// Revision 1.7  2015/02/04 18:47:08  stimpsow
// IN5947420 - prevent marking item as New if the only update was an added path to the item
//
// Revision 1.6  2010/01/08 12:45:05  wendy
// make nls checks non static, allow override of attr checked
//
// Revision 1.5  2008/04/15 20:51:01  wendy
// Added check for changes in any language
//
// Revision 1.4  2008/03/20 20:28:10  wendy
// Keep changes made for abr performance test - more work needed if used
//
// Revision 1.3  2006/11/15 16:39:16  wendy
// Modify to support VE change for MN29794770
//
// Revision 1.2  2006/07/25 12:28:02  wendy
// Sort attributes using lowercase description
//
// Revision 1.1  2006/07/24 20:50:11  wendy
// Replacement for XML in change reports
//
//
public class DiffEntity implements Diffable {
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2006, 2008  All Rights Reserved.";

    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.7 $";

    private static final String BLANK = "&nbsp;";
    private static final String ADD = "New";
    private static final String DEL = "Deleted";
    private static final String UNC = "Unchanged";
    private static final String CHG = "Changed";
    private static MetaComparator metaComp = new MetaComparator();

    private String hashkey;
    private int depth, level;
    private String eclass;
    private String key,path;
    private String parentkey;
    private String direction;
    private String status=UNC;
    private Vector metaAttrVct;
    private EntityItem priorItem;
    private EntityItem currentItem;
    private int chgdAttrCnt = 0;
    private boolean diffDone = false;
    private boolean mustDisplay = false; // true when relator is deleted, entity must be shown, even if it has no chgs
    private boolean mustHide = false;  // true when WWSEO's child is overridden by LSEOBUNDLE child or a parent was deleted
    private DiffVE diffVE = null;
    
    /***************************************
    * Constructor pkg level access only
    *@param diffve
    *@param eitem EntityItem from prior or current time
    *@param parentitem EntityItem parent item
    *@param dep  int
    *@param lvl  int
    *@param dir  String
    *@param p  String  path to this entity thru VE
    */
    DiffEntity(DiffVE diffve, EntityItem eitem, EntityItem parentitem,int dep,
        int lvl, String dir, String p)
    {
    	diffVE = diffve; // needed to find any overrides or added tests for nls
        metaAttrVct = new Vector(1);
        priorItem = eitem; // save in prioritem because when the priorvct and currentvct are merged
        // priorvct is the source, only added DiffEntity come from the currentvct, adjust these variables
        // when Diffable interface is called for add, delete or unchanged
        depth = dep;
        level=lvl;
        path=p;
        key= eitem.getKey();
        parentkey= parentitem.getKey();
        eclass = (eitem.getEntityGroup().isRelator() ? "R" : (eitem.getEntityGroup().isAssoc() ? "A" : "E"));
        direction = dir;
        // this is VERY important to the diff algo, do not change it!
        hashkey = "" + depth + ":" + level + ":" + eclass + ":" +
            eitem.getEntityType() + ":" + eitem.getEntityID() + ":" + parentitem.getEntityType() + ":" +
            parentitem.getEntityID() + ":" + direction;
    }
    /***************************************
    * release memory, could be called more than once, pkg level access only
    */
    void dereference() {
        priorItem = null;
        currentItem = null;
        key= null;
        path= null;
        status=null;
        parentkey= null;
        eclass = null;
        direction = null;
        hashkey = null;
        if (metaAttrVct!=null) {
            metaAttrVct.clear();
        }
        metaAttrVct = null;
        diffVE = null;
    }
    /***************************************
    * does this represent a relator
    *@return boolean
    */
    public boolean isRelator() { return eclass.equals("R"); }
    /***************************************
    * does this represent a association
    *@return boolean
    */
    public boolean isAssoc() { return eclass.equals("A");   }
    /***************************************
    * does this represent a entity
    *@return boolean
    */
    public boolean isEntity() { return eclass.equals("E");  }
    /***************************************
    * get depth of this in the VE
    *@return int
    */
    public int getDepth() { return depth;   }  // used to determine hierarchy
    /***************************************
    * was this deleted?
    *@return boolean
    */
    public boolean isDeleted() { return status.equals(DEL); }
    /***************************************
    * was this created?
    *@return boolean
    */
    public boolean isNew() { return status.equals(ADD); }
    /***************************************
    * get entity key
    *@return String
    */
    public String getKey() { return key; }
    /***************************************
    * get entity parentkey
    *@return String
    */
    public String getParentKey() { return parentkey; }
    /***************************************
    * get entity level
    *@return int
    */
    public int getLevel() { return level; }
    /***************************************
    * get entity type
    *@return String
    */
    public String getEntityType() {
        String type = "";
        if (currentItem !=null){
            type = currentItem.getEntityType();
        }else{
            type = priorItem.getEntityType();
        }
        return type;
    }
    /***************************************
    * get entity id
    *@return int
    */
    public int getEntityID() {
        int id = 0;
        if (currentItem !=null){
            id = currentItem.getEntityID();
        }else{
            id = priorItem.getEntityID();
        }
        return id;
    }
    /***************************************
    * get prior entityitem time1
    *@return EntityItem
    */
    public EntityItem getPriorEntityItem() {
        return priorItem;
    }
    /***************************************
    * get current entityitem time2
    *@return EntityItem
    */
    public EntityItem getCurrentEntityItem() {
        return currentItem;
    }
    /***************************************
    * [0] prior attr [1] current attr, either may be null
    *@param attrcode String
    *@return EANAttribute[]
    */
    public EANAttribute[] getAttribute(String attrcode) {
        EANAttribute attr[] = new EANAttribute[2];
        if (priorItem!=null) {
            attr[0] = priorItem.getAttribute(attrcode);
        }else {
            attr[0] = null;
        }
        if (currentItem!=null) {
            attr[1] = currentItem.getAttribute(attrcode);
        }else {
            attr[1] = null;
        }
        return attr;
    }
    /***************************************
    * set must display flag
    *@param b boolean
    */
    public void setMustDisplay(boolean b) {
        mustDisplay = b;
    }
    /***************************************
    * set must hide flag
    *@param b boolean
    */
    public void setMustHide(boolean b) {
        mustHide = b;
    }

    /********************************************************************************
    * did this entity change, check attr too
    *@return boolean
    */
    public boolean isChanged() {
        boolean chgd = false;
        // Do not output Associations at all
        if (isAssoc() || mustHide){
            chgd=false;
        }
        // only output relators if they have changed attributes!
        else if (isRelator()){
            chgd = chgdAttrCnt>0;
        }else {  // must be E class
            // If a relator attribute has been changed, but the child of that relator has not,
            // both the relator AND the child entity of that relator need to be displayed.
            if (mustDisplay){
                chgd=true;
            }else {
                chgd = !UNC.equals(status); // check entity status

                // check attr status
                chgd = chgd || chgdAttrCnt>0;
            }
        }

        return chgd;
    }

    /********************************************************************************
    * if this is the root, generate the html that would be used if no changes are found
    *@param dbCurrent Database
    *@param profile Profile
    *@param metaTbl Hashtable
    *@param out javax.servlet.jsp.JspWriter
    *@param uid String used to make WK happy
    *@param curCountStr String for root header
    *@param diffRsrcTbl Hashtable for column header resources
    *@throws IOException
    *@throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    *@throws COM.ibm.opicmpdh.middleware.MiddlewareException
    *@throws java.sql.SQLException
    */
    public void getNoChgsHtml(Database dbCurrent, Profile profile,Hashtable metaTbl,
        javax.servlet.jsp.JspWriter out,String uid, String curCountStr,
        Hashtable diffRsrcTbl) throws IOException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        java.sql.SQLException
    {
        if (isRoot()) {
            String navName;
            String uniqueStr = key+"_"+uid;
            EntityItem theItem = currentItem;
            if (theItem==null){
                theItem = priorItem;
            }

            navName = HVECUtils.getDisplayName(theItem, metaTbl,dbCurrent, profile," - ",", ");

            // output each entity its own table to improve performance, IE can render right away
            out.println("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"1\" class=\"basic-table\""+
                " summary=\"Structure and attribute changes\">");
            out.println("<colgroup width=\"8%\"/>"); // <!-- change type -->
            out.println("<colgroup width=\"20%\" span=\"3\"/>");// <!-- attribute, prev value and current value -->
            out.println("<colgroup width=\"15%\"/>");// <!-- date changed -->
            out.println("<colgroup width=\"17%\"/>"); // <!-- changed by -->
            out.println("<tr "+PokUtils.getTableHeaderRowCSS()+" title=\"(root) "+key+"\">");
            out.println("<th colspan=\"6\" id=\""+uniqueStr+"\">"+theItem.getEntityGroup().getLongDescription()+
                ": ("+curCountStr+") "+navName+"</th></tr>");
            getColHeads(out, uid, diffRsrcTbl);
            out.println("<tr class=\"odd\">");
            out.println("<td colspan=\"6\" headers=\"chgtype"+uid+
                " attr"+uid+" prev"+uid+" curr"+uid+
                " dateChgd"+uid+" user"+uid+" "+uniqueStr+"\">");
            out.println(diffRsrcTbl.get("Text_NoDataFound")+"</td></tr></table>");
        }
    }

    /********************************************************************************
    * get html title attribute
    *@return String
    */
    public String getTitleTagAttrValue() {
        String title = "";
        if(isRoot()){  // output the root
            title  ="(root) "+key;
        }
        else{
            title = "(D"+depth+":L"+level+") "+key+"["+direction+"]["+eclass+"]:"+path;
        }
        return title;
    }

    /********************************************************************************
    * output the html for this entity to the output stream
    *@param dbCurrent Database
    *@param profile Profile
    *@param metaTbl Hashtable
    *@param out javax.servlet.jsp.JspWriter
    *@param uid String used to make WK happy
    *@param diffRsrcTbl Hashtable for column header resources
    *@param curCountStr String for root header
    *@param appname String for blob attributes
    *@param limitAttrTbl Hashtable for prevent output of attr when same entity is found on diff path
    *@param roleTbl Hashtable with who changed what
    *@param dateTbl Hashtable with who changed when
    *@throws IOException
    *@throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    *@throws COM.ibm.opicmpdh.middleware.MiddlewareException
    *@throws java.sql.SQLException
    */
    public void displayHtml(Database dbCurrent, Profile profile,Hashtable metaTbl,javax.servlet.jsp.JspWriter out,
        String uid, Hashtable diffRsrcTbl, String curCountStr,
        String appname,Hashtable limitAttrTbl, Hashtable roleTbl, Hashtable dateTbl) throws IOException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        java.sql.SQLException
    {
        String navName;
        String uniqueStr = key+"_"+parentkey+"_"+uid;
        EntityItem theItem = currentItem;
        if (theItem==null){
            theItem = priorItem;
        }
        if (isRoot() && isDeleted()){  // current item will not have any values for the attributes
            theItem = priorItem;
        }
        navName = HVECUtils.getDisplayName(theItem, metaTbl,dbCurrent, profile," - ",", ");
        // output each entity its own table to improve performance, IE can render right away
        out.println("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"1\" class=\"basic-table\""+
            " summary=\"Structure and attribute changes\">");
        out.println("<colgroup width=\"8%\"/>"); // <!-- change type -->
        out.println("<colgroup width=\"20%\" span=\"3\"/>");// <!-- attribute, prev value and current value -->
        out.println("<colgroup width=\"15%\"/>");// <!-- date changed -->
        out.println("<colgroup width=\"17%\"/>"); // <!-- changed by -->

        if(isRoot()){  // output the root
            out.println("<tr "+PokUtils.getTableHeaderRowCSS()+" title=\"(root) "+key+"\">");
            if(!status.equals(UNC)){
                // do it like this to meet web accessibility requirements of th-id and td-headers attributes
                out.println("<th id=\""+uniqueStr+"\">"+status+"</th>");
                out.println("<td colspan=\"5\" headers=\""+uniqueStr+"\"><b>"+theItem.getEntityGroup().getLongDescription()+": ("+curCountStr+
                    ") "+navName+"</b></td></tr>");
            }else{
                if (chgdAttrCnt>0) {
                    out.println("<th colspan=\"6\" id=\""+uniqueStr+"\">"+theItem.getEntityGroup().getLongDescription()+": ("+curCountStr+
                      ") "+navName+"</th></tr>");
                }else{
                    out.println("<td colspan=\"6\"><b>"+theItem.getEntityGroup().getLongDescription()+": ("+curCountStr+
                      ") "+navName+"</b></td></tr>");
                }
            }
        }else{
            out.println("<tr "+PokUtils.getRowLevelCSS(level+1)+" title=\""+getTitleTagAttrValue()+"\" >");

            if(!status.equals(UNC)){
                // do it like this to meet web accessibility requirements of th-id and td-headers attributes
                out.println("<th id=\""+uniqueStr+"\">"+status+"</th>");
                out.print("<td colspan=\"5\" headers=\""+uniqueStr+"\"><b>"+theItem.getEntityGroup().getLongDescription()+": "+
                    navName
//fixme remove below
//                    +"<br>"+getTitleTagAttrValue()
                    );
                if(isRelator()){
                    out.print(" (Relator)");
                }
                out.println("</b></td>");
            }else{
              if (chgdAttrCnt>0) {
                    out.print("<th colspan=\"6\" id=\""+uniqueStr+"\">");
                    out.print(theItem.getEntityGroup().getLongDescription()+": "+navName
//fixme remove below
//                    +"<br>"+getTitleTagAttrValue()
                    );
                    if(isRelator()){
                        out.print(" (Relator)");
                    }
                    out.println("</th>");
                }else {
                    // do it like this to meet web accessibility requirements of th-id and td-headers attributes
                    out.print("<td colspan=\"6\"><b>");
                    out.print(theItem.getEntityGroup().getLongDescription()+": "+navName
//fixme remove below
//                    +"<br>"+getTitleTagAttrValue()

                    );
                    if(isRelator()){
                        out.print(" (Relator)");
                    }
                    out.println("</b></td>");
                }
            }
            out.println("</tr>");
        }

        if (limitAttrTbl.containsKey(key)){
            if (chgdAttrCnt>0) {
                String title = "Look under "+key+"["+direction+"]["+
                    (theItem.getEntityGroup().isRelator()?"R":theItem.getEntityGroup().isAssoc()?"A":"E")+"]"+
                    limitAttrTbl.get(key);
                getColHeads(out,uid, diffRsrcTbl);
                out.println("<tr class=\"odd\" title=\""+title+
                    "\"><td colspan=\"6\" headers=\"chgtype"+uid+
                    " attr"+uid+" prev"+uid+" curr"+uid+
                    " dateChgd"+uid+" user"+uid+" "+uniqueStr+"\">"+
                    diffRsrcTbl.get("Text_PrevDisplay")+"</td></tr>");
                    //"Attributes were previously displayed for this entity under a different navigation path"
            }
        }else{
            limitAttrTbl.put(key,path);

/*fixme restore*/
            if (chgdAttrCnt>0) {
                // and row headings in each table if any attributes were changed
                getColHeads(out,uid, diffRsrcTbl);

                // output attributes
                outputAttributes(out,uid, uniqueStr, appname, diffRsrcTbl, roleTbl, dateTbl);
            }
/**/
        }
        out.println("</table>");
    }

	/********************************************************************************
	* this builds entity and attributes as xml - currently missing date and uid
	*/
	protected String getEntityAndAttributes(){
		StringBuffer sb = new StringBuffer("<entity description=\"");
		EntityItem theItem = currentItem;
		if (theItem==null){
			theItem = priorItem;
		}
		if (isRoot() && isDeleted()){  // current item will not have any values for the attributes
			theItem = priorItem;
		}
		sb.append(convertToHTML(theItem.getEntityGroup().getLongDescription())+"\" ");
		sb.append("type=\""+theItem.getEntityType()+"\" eid=\""+theItem.getEntityID()+"\" ");
		sb.append("parentkey=\""+parentkey+"\" depth=\""+getDepth()+"\""+" level=\""+getLevel()+"\" ");
		sb.append("direction=\""+direction+"\" class=\""+eclass+"\""+" status=\""+status+"\" >\n");

		sb.append("<currentvalue>" + (currentItem != null ? convertToHTML(currentItem.toString()) : "") + "</currentvalue>\n");
		sb.append("<priorvalue>" + (priorItem != null ? convertToHTML(priorItem.toString()) : "") + "</priorvalue>\n");

		// get attributes
		sb.append(getAttributesXML());
		return sb.toString();
	}


	/********************************************************************************
	* output attributes - doesnt have uid or role
	* If the entity has a multi flag attribute, all changed flag values should be displayed.
	* Each changed flag value should be displayed in its own row under the entity.
	*/
	private String getAttributesXML()  {
		StringBuffer sb = new StringBuffer();
		int iNavOrder = 0;
		for (int i=0; i<metaAttrVct.size(); i++) {
			EANMetaAttribute ma = (EANMetaAttribute)metaAttrVct.elementAt(i);
			String attrcode = ma.getAttributeCode();
			String attrtype = ma.getAttributeType();
			EANAttribute priorattr = null;
			EANAttribute currattr = null;
			String priorval="";
			String curval = "";
			String attstatus = CHG;
			String description = ma.getLongDescription();

			//              String strInfoKey = null;
			//              String strInfoKeyAttributeValue = "NA";

			if (description.startsWith("*")) {
			  	description = description.substring(1);
			}
			description = convertToHTML(description);

			if (priorItem!=null) {
			  	priorattr = priorItem.getAttribute(attrcode);
			  	if (priorattr !=null){
				  	// o.k. if we are a blob we will have to substitute the image thing here
				  	//
				  	if (attrtype.equals("B")) {
						priorval ="BLOB- how to handle?";
				  	}else{
			//                         if (priorattr instanceof EANFlagAttribute) {
			//                             strInfoKeyAttributeValue = ((EANFlagAttribute) priorattr).getFirstActiveFlagCode();
			//                         }

					  	if (!attrtype.equals("X")) {
						  	priorval = convertToHTML(priorattr.toString());
					  	}else{
						  	priorval = priorattr.toString();
					  	}
				  }
			  }
			//                  strInfoKey = priorItem.getEntityType() + ":" + priorItem.getEntityID() + ":" + attrcode + ":" + strInfoKeyAttributeValue;
			//                  if (currentItem==null){
				  // callGBL8167 doesn't return info on deleted entities so have a workaround in the jsp
			//                      strInfoKey = priorItem.getEntityType() + ":" + priorItem.getEntityID() + ":"  +"ALL";
			//                  }
			}
			if (currentItem!=null) {
			  	currattr = currentItem.getAttribute(attrcode);
			  	if (currattr !=null){
				  	// blob has to substitute the image
				  	if (attrtype.equals("B")) {
					  	curval ="BLOB- how to handle?";
				  	}else{
					  //this will overwrite any prior key value, which is correct
			//                        if (currattr instanceof EANFlagAttribute) {
			//                          strInfoKeyAttributeValue = ((EANFlagAttribute) currattr).getFirstActiveFlagCode();
			//                    }

					  	if (!attrtype.equals("X")) {
						  	curval = convertToHTML(currattr.toString());
					  	}else{
						  	curval = currattr.toString();
					  	}
				  	}
			  	}
			//          strInfoKey = currentItem.getEntityType() + ":" + currentItem.getEntityID() + ":" + attrcode + ":" + strInfoKeyAttributeValue;
			}
			if (currattr ==null && priorattr==null) {
			  	continue; // this attribute was never set
			}

			if (ma.isNavigate()){ // this should be done someplace else.. it really isnt navorder here
				iNavOrder++;
			}

			if(!curval.equals(priorval)){
			  	// Multiflags (F) need to have one flag code per row
			  	if (!attrtype.equals("F")) {
				  	if (currattr==null) {
					  	attstatus = DEL;
				  	}else if (priorattr==null){
					  	attstatus = ADD;
				  	}

					sb.append("<attribute " + (ma.isNavigate() ? "nav = \"" +iNavOrder + "\" " : "") +
						"description = \"" + description + "\" parentkey=\"" + key + "\" code=\"" + attrcode+ "\" type=\"" +
						attrtype + "\" status=\"" + attstatus + "\">\n");
					sb.append("<currentvalue>"+curval+"</currentvalue>\n");
					sb.append("<priorvalue>"+priorval+"</priorvalue>\n");
					sb.append("</attribute>\n");
			  	}
			  	else{  // is multiflag
				  	if (currattr==null) {
					  	attstatus = DEL;
					  	if (currentItem!=null && !status.equals(DEL)){ // root will have a current item
						  	EANFlagAttribute fac = (EANFlagAttribute) priorattr;
						  	MetaFlag mfc[] = (MetaFlag[]) fac.get();
						  	for (int ia = 0; ia < mfc.length; ia++) {
							  	if (mfc[ia].isSelected()) {
			//                                      strInfoKey = priorItem.getEntityType() + ":" + priorItem.getEntityID() +
			//                                        ":" + attrcode + ":" + mfc[ia].getFlagCode();

								  	priorval = convertToHTML(mfc[ia].getLongDescription());
									sb.append("<attribute " + (ma.isNavigate() ? "nav = \"" + iNavOrder + "\" " : "") +
										"description = \"" + description + "\" parentkey=\"" + key + "\" code=\"" + attrcode+ "\" type=\"" +
										attrtype + "\" status=\"" + attstatus + "\">\n");
									sb.append("<currentvalue>"+curval+"</currentvalue>\n");
							  		sb.append("<priorvalue>"+priorval+"</priorvalue>\n");
									sb.append("</attribute>\n");
							  }
						  }
						}else{ // the entity was deleted, just group the flags in one row
							// callGBL8167 doesn't return info on deleted entities so have a workaround in the jsp
							//                          strInfoKey = priorItem.getEntityType() + ":" + priorItem.getEntityID() + ":"  +"ALL";
							priorval = convertToHTML(priorattr.toString());
							sb.append("<attribute " + (ma.isNavigate() ? "nav = \"" + iNavOrder + "\" " : "") +
							"description = \"" + description + "\" parentkey=\"" + key + "\" code=\"" + attrcode+ "\" type=\"" +
							attrtype + "\" status=\"" + attstatus + "\">\n");
							sb.append("<currentvalue>"+curval+"</currentvalue>\n");
							sb.append("<priorvalue>"+priorval+"</priorvalue>\n");
							sb.append("</attribute>\n");
					  	}
				  	}else if (priorattr==null){
					  	attstatus = ADD;
					  	if (priorItem!=null&& !status.equals(ADD)){ // root will have a prior item
							EANFlagAttribute fac = (EANFlagAttribute) currattr;
							MetaFlag mfc[] = (MetaFlag[]) fac.get();
							for (int ia = 0; ia < mfc.length; ia++) {
								if (mfc[ia].isSelected()) {
							//                                strInfoKey = currentItem.getEntityType() + ":" + currentItem.getEntityID() +
							//                                  ":" + attrcode + ":" + mfc[ia].getFlagCode();

									curval = convertToHTML(mfc[ia].getLongDescription());
									sb.append("<attribute " + (ma.isNavigate() ? "nav = \"" + iNavOrder + "\" " : "") +
										"description = \"" + description + "\" parentkey=\"" + key + "\" code=\"" + attrcode+ "\" type=\"" +
										attrtype + "\" status=\"" + attstatus + "\">\n");
									sb.append("<currentvalue>"+curval+"</currentvalue>\n");
									sb.append("<priorvalue>"+priorval+"</priorvalue>\n");
									sb.append("</attribute>\n");
							  }
						  }
					  }else{  // entity was added, so group flags into one row
						  	EANFlagAttribute fac = (EANFlagAttribute) currattr;
			//                    strInfoKey = currentItem.getEntityType() + ":" + currentItem.getEntityID() +
			//                      ":" + attrcode + ":" + fac.getFirstActiveFlagCode();

							curval = convertToHTML(fac.toString());
							sb.append("<attribute " + (ma.isNavigate() ? "nav = \"" + iNavOrder+ "\" " : "") +
								"description = \"" + description + "\" parentkey=\"" + key + "\" code=\"" + attrcode+ "\" type=\"" +
								attrtype + "\" status=\"" + attstatus + "\">\n");
							sb.append("<currentvalue>"+curval+"</currentvalue>\n");
							sb.append("<priorvalue>"+priorval+"</priorvalue>\n");
							sb.append("</attribute>\n");
					  }
				  }else{ // existed at both times so must look at each flag
					  // We need one attribute for every meta flag value...
					  EANFlagAttribute fac = (EANFlagAttribute) currattr;
					  EANFlagAttribute fap = (EANFlagAttribute) priorattr;

					  MetaFlag mfc[] = (MetaFlag[]) fac.get();
					  for (int ia = 0; ia < mfc.length; ia++) {
						  // if selected in both, or not selected in both, skip it
						  if((mfc[ia].isSelected() && fap.isSelected(mfc[ia])) ||
							  (!mfc[ia].isSelected() && !fap.isSelected(mfc[ia]))){
							  continue;
						  }
						  if (mfc[ia].isSelected()) {
							  attstatus = ADD;
							  curval = convertToHTML(mfc[ia].getLongDescription());
							  priorval = "";
						  } else if (fap.isSelected(mfc[ia])) {
							  attstatus = DEL;
							  priorval = convertToHTML(mfc[ia].getLongDescription());
							  curval = "";
						  }

			//                strInfoKey = currentItem.getEntityType() + ":" + currentItem.getEntityID() +
			//                  ":" + attrcode + ":" + mfc[ia].getFlagCode();

							sb.append("<attribute " + (ma.isNavigate() ? "nav = \"" + iNavOrder + "\" " : "") +
								"description = \"" + description + "\" parentkey=\"" + key + "\" code=\"" + attrcode+ "\" type=\"" +
								attrtype + "\" status=\"" + attstatus + "\">\n");
							sb.append("<currentvalue>"+curval+"</currentvalue>\n");
							sb.append("<priorvalue>"+priorval+"</priorvalue>\n");
							sb.append("</attribute>\n");
						}
				  }
			  }// end is multiflag
			}else{ // no changes
				attstatus = UNC;
				sb.append("<attribute " + (ma.isNavigate() ? "nav = \"" +iNavOrder + "\" " : "") +
					"description = \"" + description + "\" parentkey=\"" + key + "\" code=\"" + attrcode+ "\" type=\"" +
					attrtype + "\" status=\"" + attstatus + "\">\n");
				sb.append("<currentvalue>"+curval+"</currentvalue>\n");
				sb.append("<priorvalue>"+priorval+"</priorvalue>\n");
				sb.append("</attribute>\n");
			}
		}

		return sb.toString();
	}

    /********************************************************************************
     * output the xhtml for this entity - doesnt have uid or date
     * used to test performance in abr
     */
     public String getXHTML() {
         StringBuffer sb = new StringBuffer();
         String navName;
         //String uniqueStr = key+"_"+parentkey+"_"+uid;
         EntityItem theItem = currentItem;
         if (theItem==null){
             theItem = priorItem;
         }
         if (isRoot() && isDeleted()){  // current item will not have any values for the attributes
             theItem = priorItem;
         }
         navName = theItem.getLongDescription();//HVECUtils.getDisplayName(theItem, metaTbl,dbCurrent, profile," - ",", ");
         // output each entity its own table to improve performance, IE can render right away
         sb.append("<table>");
         if(isRoot()){  // output the root
             sb.append("<tr "+PokUtils.getTableHeaderRowCSS()+" >");
             if(!status.equals(UNC)){
                 sb.append("<th>"+status+"</th>");
                 sb.append("<td colspan=\"3\"><b>"+theItem.getEntityGroup().getLongDescription()+" "+navName+"</b></td></tr>\n");
             }else{
                 sb.append("<th colspan=\"4\">"+theItem.getEntityGroup().getLongDescription()+" "+navName+"</th></tr>\n");
             }
         }else{
             sb.append("<tr "+PokUtils.getRowLevelCSS(level+1)+" >");

             if(!status.equals(UNC)){
                 sb.append("<th>"+status+"</th>");
                 sb.append("<td colspan=\"3\"><b>"+theItem.getEntityGroup().getLongDescription()+": "+
                     navName
// fixme remove below
//                     +"<br>"+getTitleTagAttrValue()
                     );
                 if(isRelator()){
                     sb.append(" (Relator)");
                 }
                 sb.append("</b></td>");
             }else{
               if (chgdAttrCnt>0) {
                   sb.append("<th colspan=\"4\">");
                   sb.append(theItem.getEntityGroup().getLongDescription()+": "+navName
// fixme remove below
//                     +"<br>"+getTitleTagAttrValue()
                     );
                     if(isRelator()){
                         sb.append(" (Relator)");
                     }
                     sb.append("</th>");
                 }
             }
             sb.append("</tr>\n");
         }

         if (chgdAttrCnt>0) {
             // and row headings in each table if any attributes were changed
             sb.append("<tr "+PokUtils.getColumnHeaderRowCSS()+">");
             sb.append("<th>Change Type</th>");//Change Type
             sb.append("<th>Attribute</th>");//Attribute
             sb.append("<th>Previous Value</th>");//Previous Value
             sb.append("<th>Current Value</th></tr>\n");//Current Value

             // output attributes
             sb.append(getAttributesXHTML());
         }

         sb.append("</table>\n");
         return sb.toString();
     }

     /********************************************************************************
      * output attributes - doesnt have uid or date
      * If the entity has a multi flag attribute, all changed flag values should be displayed.
      * Each changed flag value should be displayed in its own row under the entity.
      */
      private String getAttributesXHTML()  {
          StringBuffer sb = new StringBuffer();
//          int lineCnt=0;
          for (int i=0; i<metaAttrVct.size(); i++) {
              EANMetaAttribute ma = (EANMetaAttribute)metaAttrVct.elementAt(i);
              String attrcode = ma.getAttributeCode();
              String attrtype = ma.getAttributeType();
              EANAttribute priorattr = null;
              EANAttribute currattr = null;
              String priorval=BLANK;
              String curval = BLANK;
              String attstatus = CHG;
              String description = ma.getLongDescription();

//              String strInfoKey = null;
//              String strInfoKeyAttributeValue = "NA";

              if (description.startsWith("*")) {
                  description = description.substring(1);
              }

              if (priorItem!=null) {
                  priorattr = priorItem.getAttribute(attrcode);
                  if (priorattr !=null){
                      // o.k. if we are a blob we will have to substitute the image thing here
                      //
                      if (attrtype.equals("B")) {
                          priorval ="BLOB- how to handle?";
                      }else{
 //                         if (priorattr instanceof EANFlagAttribute) {
 //                             strInfoKeyAttributeValue = ((EANFlagAttribute) priorattr).getFirstActiveFlagCode();
 //                         }

                          if (!attrtype.equals("X")) {
                              priorval = convertToHTML(priorattr.toString());
                          }else{
                              priorval = priorattr.toString();
                          }
                      }
                  }
//                  strInfoKey = priorItem.getEntityType() + ":" + priorItem.getEntityID() + ":" + attrcode + ":" + strInfoKeyAttributeValue;
//                  if (currentItem==null){
                      // callGBL8167 doesn't return info on deleted entities so have a workaround in the jsp
//                      strInfoKey = priorItem.getEntityType() + ":" + priorItem.getEntityID() + ":"  +"ALL";
//                  }
              }
              if (currentItem!=null) {
                  currattr = currentItem.getAttribute(attrcode);
                  if (currattr !=null){
                      // blob has to substitute the image
                      if (attrtype.equals("B")) {
                          curval ="BLOB- how to handle?";
                      }else{
                          //this will overwrite any prior key value, which is correct
  //                        if (currattr instanceof EANFlagAttribute) {
    //                          strInfoKeyAttributeValue = ((EANFlagAttribute) currattr).getFirstActiveFlagCode();
      //                    }

                          if (!attrtype.equals("X")) {
                              curval = convertToHTML(currattr.toString());
                          }else{
                              curval = currattr.toString();
                          }
                      }
                  }
        //          strInfoKey = currentItem.getEntityType() + ":" + currentItem.getEntityID() + ":" + attrcode + ":" + strInfoKeyAttributeValue;
              }
              if (currattr ==null && priorattr==null) {
                  continue; // this attribute was never set
              }

              if(!curval.equals(priorval)){
                  // Multiflags (F) need to have one flag code per row
                  if (!attrtype.equals("F")) {
                      if (currattr==null) {
                          attstatus = DEL;
                      }else if (priorattr==null){
                          attstatus = ADD;
                      }

                      sb.append(outputAttribute(attstatus,description,priorval, curval));
                  }
                  else{  // is multiflag
                      if (currattr==null) {
                          attstatus = DEL;
                          if (currentItem!=null && !status.equals(DEL)){ // root will have a current item
                              EANFlagAttribute fac = (EANFlagAttribute) priorattr;
                              MetaFlag mfc[] = (MetaFlag[]) fac.get();
                              for (int ia = 0; ia < mfc.length; ia++) {
                                  if (mfc[ia].isSelected()) {
//                                      strInfoKey = priorItem.getEntityType() + ":" + priorItem.getEntityID() +
  //                                        ":" + attrcode + ":" + mfc[ia].getFlagCode();

                                      priorval = convertToHTML(mfc[ia].getLongDescription());
                                      sb.append(outputAttribute(attstatus,description,priorval, curval));
                                  }
                              }
                          }else{ // the entity was deleted, just group the flags in one row
                              // callGBL8167 doesn't return info on deleted entities so have a workaround in the jsp
    //                          strInfoKey = priorItem.getEntityType() + ":" + priorItem.getEntityID() + ":"  +"ALL";
                              priorval = convertToHTML(priorattr.toString());
                              sb.append(outputAttribute(attstatus,description,priorval, curval));
                          }
                      }else if (priorattr==null){
                          attstatus = ADD;
                          if (priorItem!=null&& !status.equals(ADD)){ // root will have a prior item
                              EANFlagAttribute fac = (EANFlagAttribute) currattr;
                              MetaFlag mfc[] = (MetaFlag[]) fac.get();
                              for (int ia = 0; ia < mfc.length; ia++) {
                                  if (mfc[ia].isSelected()) {
      //                                strInfoKey = currentItem.getEntityType() + ":" + currentItem.getEntityID() +
        //                                  ":" + attrcode + ":" + mfc[ia].getFlagCode();

                                      curval = convertToHTML(mfc[ia].getLongDescription());
                                      sb.append(outputAttribute(attstatus,description,priorval, curval));
                                  }
                              }
                          }else{  // entity was added, so group flags into one row
                              EANFlagAttribute fac = (EANFlagAttribute) currattr;
          //                    strInfoKey = currentItem.getEntityType() + ":" + currentItem.getEntityID() +
            //                      ":" + attrcode + ":" + fac.getFirstActiveFlagCode();

                              curval = convertToHTML(fac.toString());
                              sb.append(outputAttribute(attstatus,description,priorval, curval));
                          }
                      }else{ // existed at both times so must look at each flag
                          // We need one attribute for every meta flag value...
                          EANFlagAttribute fac = (EANFlagAttribute) currattr;
                          EANFlagAttribute fap = (EANFlagAttribute) priorattr;

                          MetaFlag mfc[] = (MetaFlag[]) fac.get();
                          for (int ia = 0; ia < mfc.length; ia++) {
                              // if selected in both, or not selected in both, skip it
                              if((mfc[ia].isSelected() && fap.isSelected(mfc[ia])) ||
                                  (!mfc[ia].isSelected() && !fap.isSelected(mfc[ia]))){
                                  continue;
                              }
                              if (mfc[ia].isSelected()) {
                                  attstatus = ADD;
                                  curval = convertToHTML(mfc[ia].getLongDescription());
                                  priorval = BLANK;
                              } else if (fap.isSelected(mfc[ia])) {
                                  attstatus = DEL;
                                  priorval = convertToHTML(mfc[ia].getLongDescription());
                                  curval = BLANK;
                              }

              //                strInfoKey = currentItem.getEntityType() + ":" + currentItem.getEntityID() +
                //                  ":" + attrcode + ":" + mfc[ia].getFlagCode();

                              sb.append(outputAttribute(attstatus,description,priorval, curval));
                          }
                      }
                  }// end is multiflag
              }
          }

          return sb.toString();
      }
      /********************************************************************************
       * get html for this attribute- no uid or date
       */
       private String outputAttribute(String attstatus, String description,
           String priorval, String curval)
       {
           StringBuffer sb = new StringBuffer();
           sb.append("<tr>");
           sb.append("<td>"+attstatus+"</td>");
           sb.append("<td>"+description+"</td>");
           sb.append("<td>"+priorval+"</td>");
           sb.append("<td>"+curval+"</td></tr>\n");
           return sb.toString();
       }

       /********************************************************************************
        * if this is the root, generate the html that would be used if no changes are found
        *@param dbCurrent Database
        *@param profile Profile
        *@param metaTbl Hashtable
        *@param out javax.servlet.jsp.JspWriter
        *@param uid String used to make WK happy
        *@param curCountStr String for root header
        *@param diffRsrcTbl Hashtable for column header resources
        *@throws IOException
        *@throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
        *@throws COM.ibm.opicmpdh.middleware.MiddlewareException
        *@throws java.sql.SQLException
        */
        public String getNoChgsXHtml()
        {
            StringBuffer sb = new StringBuffer();
            if (isRoot()) {
                String navName;
                //String uniqueStr = key+"_"+uid;
                EntityItem theItem = currentItem;
                if (theItem==null){
                    theItem = priorItem;
                }

                navName = theItem.getLongDescription();
                    //HVECUtils.getDisplayName(theItem, metaTbl,dbCurrent, profile," - ",", ");

                // output each entity its own table to improve performance, IE can render right away
                sb.append("<table>");
                sb.append("<tr "+PokUtils.getTableHeaderRowCSS()+">");
                sb.append("<th colspan=\"4\">"+theItem.getEntityGroup().getLongDescription()+
                    ": "+navName+"</th></tr>");
                //getColHeads(out, uid, diffRsrcTbl);
                sb.append("<tr class=\"odd\">");
                sb.append("<td colspan=\"4\" >No Changes found</td></tr></table>");
            }
            return sb.toString();
        }
    /********************************************************************************
    * count attribute changes so dont have to look over and over again
    */
    private void countChgdAttr() {
		Profile currprofile = null;
		Profile prevprofile = null;
		if (currentItem != null){
			currprofile = currentItem.getProfile();
		}
		if (priorItem != null){
			prevprofile = priorItem.getProfile();
		}

        Profile profile = (currprofile==null?prevprofile:currprofile); // both will have the same languages, use one
        NLSItem origNls = profile.getReadLanguage();

        // check if attr changed or not
        for (int i=0; i<metaAttrVct.size(); i++) {
            EANMetaAttribute ma = (EANMetaAttribute)metaAttrVct.elementAt(i);
            String attrcode = ma.getAttributeCode();
            EANAttribute attr = null;
            String priorval="";
            String curval = "";
            String eikey = "";
            
            if (priorItem!=null) {
            	eikey = priorItem.getKey();
                attr = priorItem.getAttribute(attrcode);
                if (attr !=null){
                    priorval = attr.toString();
                }
            }
            if (currentItem!=null) {
            	eikey = currentItem.getKey();
                attr = currentItem.getAttribute(attrcode);
                if (attr !=null){
                    curval = attr.toString();
                }
            }
            if(!curval.equals(priorval)){
                chgdAttrCnt++;
                diffVE.addDebug(eikey+" has chgd "+attrcode);
            }else if (diffVE.getCheckAllNLS()){ // make sure it wasnt changed in a different nls, if needed
            	if (ma instanceof EANMetaTextAttribute){
					// this is NLS sensitive, do each one
					for (int ix = 0; ix < profile.getReadLanguages().size(); ix++) {
						NLSItem nlsitem = profile.getReadLanguage(ix);
						if (nlsitem == origNls){
							continue; // already checked this one
						}
						if (currprofile != null){
							currprofile.setReadLanguage(ix);
						}
						if (prevprofile != null){
							prevprofile.setReadLanguage(ix);
						}

						priorval="";
						curval = "";
						if (priorItem!=null) {
							attr = priorItem.getAttribute(attrcode);
							if (attr instanceof EANTextAttribute){
								int nlsid = nlsitem.getNLSID();
								//true if information for the given NLSID is contained in the Text data
								if (((EANTextAttribute)attr).containsNLS(nlsid)) {
									priorval = attr.toString();
								} // end attr has this language
							}
						}
						if (currentItem!=null) {
							attr = currentItem.getAttribute(attrcode);
							if (attr instanceof EANTextAttribute){
								int nlsid = nlsitem.getNLSID();
								//true if information for the given NLSID is contained in the Text data
								if (((EANTextAttribute)attr).containsNLS(nlsid)) {
									curval = attr.toString();
								} // end attr has this language
							}
						}
						if(!curval.equals(priorval)){
							chgdAttrCnt++;
			                diffVE.addDebug(eikey+" has chgd "+attrcode);
							break; // one difference is enough
						}
					} // end each nls
					if (currprofile != null){
						currprofile.setReadLanguage(origNls);
					}
					if (prevprofile != null){
						prevprofile.setReadLanguage(origNls);
					}
				} // end is meta text attr
			} // end need to check all nls
        }
    }

    /********************************************************************************
    * check for root entity
    *
    * @return boolean
    */
    public boolean isRoot() {
        return ("Root".equals(direction));
    }
    /********************************************************************************
    * save and sort meta attr for later display order
    */
    private void setMeta(EntityItem item)
    {
        EntityGroup eg = item.getEntityGroup();
        boolean anyValidAttr = false;
        for(int i=0; i<eg.getMetaAttributeCount(); i++) {
            EANMetaAttribute ma = eg.getMetaAttribute(i);
            String code = ma.getAttributeCode();
            EANAttribute att = item.getAttribute(code);
            if (att!=null){
                anyValidAttr = true;
            }

            if (!ma.getAttributeType().equals("A")) { // Filter out 'A' attrType
            	if(!diffVE.isIgnored(eg.getEntityType(), code)){ // check for any overrides
            		metaAttrVct.add(ma);
            	}else{
            		diffVE.addDebug("Changes to "+eg.getEntityType()+" "+code+" will be ignored");
            	}
            }
        }
        // if root was deleted it will always be 'unchanged' because VEs return an empty entity
        // for a parent that doesn't exist
       if(isRoot()){
            if(!anyValidAttr){  // root was deleted
                if (item==currentItem && priorItem!=null){  // root was deleted, prioritem can't be null..chk just for safety
                    status=DEL;
                }
            }else{ // was the root added?
                // if prioritem doesn't have any valid attr then it was NEW
                if (priorItem!=null){ // this really can't happen, but chk anyway
                    eg = priorItem.getEntityGroup();
                    anyValidAttr = false;
                    for(int i=0; i<eg.getMetaAttributeCount(); i++) {
                        EANMetaAttribute ma = eg.getMetaAttribute(i);
                        String code = ma.getAttributeCode();
                        EANAttribute att = priorItem.getAttribute(code);
                        if (att!=null){
                            anyValidAttr = true;
                            break;
                        }
                    }
                    if (!anyValidAttr){
                        status=ADD;
                    }
                }
            }
        }

        // sort meta for later output attr display
        Collections.sort(metaAttrVct, metaComp);
        // count changed attr
        countChgdAttr();
    }

    /********************************************************************************
    * output attributes to the output stream
    * If the entity has a multi flag attribute, all changed flag values should be displayed.
    * Each changed flag value should be displayed in its own row under the entity.
    */
    private void outputAttributes(javax.servlet.jsp.JspWriter out,String uid, String uniqueStr,
        String appname, Hashtable diffRsrcTbl, Hashtable roleTbl, Hashtable dateTbl)
    throws IOException
    {
        int lineCnt=0;
        for (int i=0; i<metaAttrVct.size(); i++) {
            EANMetaAttribute ma = (EANMetaAttribute)metaAttrVct.elementAt(i);
            String attrcode = ma.getAttributeCode();
            String attrtype = ma.getAttributeType();
            EANAttribute priorattr = null;
            EANAttribute currattr = null;
            String priorval=BLANK;
            String curval = BLANK;
            String attstatus = CHG;
            String description = ma.getLongDescription();

            String strInfoKey = null;
            String strInfoKeyAttributeValue = "NA";

            if (description.startsWith("*")) {
                description = description.substring(1);
            }

            if (priorItem!=null) {
                priorattr = priorItem.getAttribute(attrcode);
                if (priorattr !=null){
                    // o.k. if we are a blob we will have to substitute the image thing here
                    //
                    if (attrtype.equals("B")) {
                        priorval ="<img src='/"+appname+"/GetBlobAttribute.wss?entityID=" + priorItem.getEntityID() +
                                "&entityType=" + priorItem.getEntityType() +
                                "&attributeCode=" + attrcode +
                                "&date=" + priorItem.getProfile().getValOn() +
                                "' alt='image' />";  // close tag needed for XML

                    }else{
                        if (priorattr instanceof EANFlagAttribute) {
                            strInfoKeyAttributeValue = ((EANFlagAttribute) priorattr).getFirstActiveFlagCode();
                        }

                        if (!attrtype.equals("X")) {
                            priorval = convertToHTML(priorattr.toString());
                        }else{
                            priorval = priorattr.toString();
                        }
                    }
                }
                strInfoKey = priorItem.getEntityType() + ":" + priorItem.getEntityID() + ":" + attrcode + ":" + strInfoKeyAttributeValue;
                if (currentItem==null){
                    // callGBL8167 doesn't return info on deleted entities so have a workaround in the jsp
                    strInfoKey = priorItem.getEntityType() + ":" + priorItem.getEntityID() + ":"  +"ALL";
                }
            }
            if (currentItem!=null) {
                currattr = currentItem.getAttribute(attrcode);
                if (currattr !=null){
                    // blob has to substitute the image
                    if (attrtype.equals("B")) {
                        curval ="<img src='/"+appname+"/GetBlobAttribute.wss?entityID=" + currentItem.getEntityID() +
                                "&entityType=" + currentItem.getEntityType() +
                                "&attributeCode=" + attrcode +
                                "&date=" + currentItem.getProfile().getValOn() +
                                "' alt='image' />";  // close tag needed for XML
                    }else{
                        //this will overwrite any prior key value, which is correct
                        if (currattr instanceof EANFlagAttribute) {
                            strInfoKeyAttributeValue = ((EANFlagAttribute) currattr).getFirstActiveFlagCode();
                        }

                        if (!attrtype.equals("X")) {
                            curval = convertToHTML(currattr.toString());
                        }else{
                            curval = currattr.toString();
                        }
                    }
                }
                strInfoKey = currentItem.getEntityType() + ":" + currentItem.getEntityID() + ":" + attrcode + ":" + strInfoKeyAttributeValue;
            }
            if (currattr ==null && priorattr==null) {
                continue; // this attribute was never set
            }

            if(!curval.equals(priorval)){
                // Multiflags (F) need to have one flag code per row
                if (!attrtype.equals("F")) {
                    if (currattr==null) {
                        attstatus = DEL;
                    }else if (priorattr==null){
                        attstatus = ADD;
                    }

                    outputAttribute(out, strInfoKey, attrcode, attrtype, lineCnt++, uid, uniqueStr,
                        attstatus, description, priorval, curval, diffRsrcTbl, roleTbl, dateTbl);
                }
                else{  // is multiflag
                    if (currattr==null) {
                        attstatus = DEL;
                        if (currentItem!=null && !status.equals(DEL)){ // root will have a current item
                            EANFlagAttribute fac = (EANFlagAttribute) priorattr;
                            MetaFlag mfc[] = (MetaFlag[]) fac.get();
                            for (int ia = 0; ia < mfc.length; ia++) {
                                if (mfc[ia].isSelected()) {
                                    strInfoKey = priorItem.getEntityType() + ":" + priorItem.getEntityID() +
                                        ":" + attrcode + ":" + mfc[ia].getFlagCode();

                                    priorval = convertToHTML(mfc[ia].getLongDescription());
                                    outputAttribute(out, strInfoKey, attrcode, attrtype, lineCnt++, uid, uniqueStr,
                                        attstatus, description, priorval, curval, diffRsrcTbl, roleTbl, dateTbl);
                                }
                            }
                        }else{ // the entity was deleted, just group the flags in one row
                            // callGBL8167 doesn't return info on deleted entities so have a workaround in the jsp
                            strInfoKey = priorItem.getEntityType() + ":" + priorItem.getEntityID() + ":"  +"ALL";
                            priorval = convertToHTML(priorattr.toString());
                            outputAttribute(out, strInfoKey, attrcode, attrtype, lineCnt++, uid, uniqueStr,
                                attstatus, description, priorval, curval, diffRsrcTbl, roleTbl, dateTbl);
                        }
                    }else if (priorattr==null){
                        attstatus = ADD;
                        if (priorItem!=null&& !status.equals(ADD)){ // root will have a prior item
                            EANFlagAttribute fac = (EANFlagAttribute) currattr;
                            MetaFlag mfc[] = (MetaFlag[]) fac.get();
                            for (int ia = 0; ia < mfc.length; ia++) {
                                if (mfc[ia].isSelected()) {
                                    strInfoKey = currentItem.getEntityType() + ":" + currentItem.getEntityID() +
                                        ":" + attrcode + ":" + mfc[ia].getFlagCode();

                                    curval = convertToHTML(mfc[ia].getLongDescription());
                                    outputAttribute(out, strInfoKey, attrcode, attrtype, lineCnt++, uid, uniqueStr,
                                        attstatus, description, priorval, curval, diffRsrcTbl, roleTbl, dateTbl);
                                }
                            }
                        }else{  // entity was added, so group flags into one row
                            EANFlagAttribute fac = (EANFlagAttribute) currattr;
                            strInfoKey = currentItem.getEntityType() + ":" + currentItem.getEntityID() +
                                ":" + attrcode + ":" + fac.getFirstActiveFlagCode();

                            curval = convertToHTML(fac.toString());
                            outputAttribute(out, strInfoKey, attrcode, attrtype, lineCnt++, uid, uniqueStr,
                                attstatus, description, priorval, curval, diffRsrcTbl, roleTbl, dateTbl);
                        }
                    }else{ // existed at both times so must look at each flag
                        // We need one attribute for every meta flag value...
                        EANFlagAttribute fac = (EANFlagAttribute) currattr;
                        EANFlagAttribute fap = (EANFlagAttribute) priorattr;

                        MetaFlag mfc[] = (MetaFlag[]) fac.get();
                        for (int ia = 0; ia < mfc.length; ia++) {
                            // if selected in both, or not selected in both, skip it
                            if((mfc[ia].isSelected() && fap.isSelected(mfc[ia])) ||
                                (!mfc[ia].isSelected() && !fap.isSelected(mfc[ia]))){
                                continue;
                            }
                            if (mfc[ia].isSelected()) {
                                attstatus = ADD;
                                curval = convertToHTML(mfc[ia].getLongDescription());
                                priorval = BLANK;
                            } else if (fap.isSelected(mfc[ia])) {
                                attstatus = DEL;
                                priorval = convertToHTML(mfc[ia].getLongDescription());
                                curval = BLANK;
                            }

                            strInfoKey = currentItem.getEntityType() + ":" + currentItem.getEntityID() +
                                ":" + attrcode + ":" + mfc[ia].getFlagCode();

                            outputAttribute(out, strInfoKey, attrcode, attrtype, lineCnt++, uid, uniqueStr,
                                attstatus, description, priorval, curval, diffRsrcTbl, roleTbl, dateTbl);
                        }
                    }
                }// end is multiflag
            }
        }
    }
    /********************************************************************************
    * output html for this attribute
    */
    private void outputAttribute(javax.servlet.jsp.JspWriter out,String strInfoKey, String attrcode,
        String attrtype, int lineCnt, String uid, String uniqueStr,String attstatus, String description,
        String priorval, String curval, Hashtable diffRsrcTbl, Hashtable roleTbl, Hashtable dateTbl) throws IOException
    {
        String changeperson = (String) roleTbl.get(strInfoKey);
        String changedatetime = (String) dateTbl.get(strInfoKey);
        if (changeperson == null) {
            changeperson = ""+diffRsrcTbl.get("Text_NoRoleFound");//"No Role Description Found - No User Token Found"
        } else {
            changeperson = convertToHTML(changeperson);
        }
        if (changedatetime == null) {
            changedatetime = ""+diffRsrcTbl.get("Text_NoDateFound");//"No date found"
        }

        out.println("<tr title=\""+attrcode+":"+attrtype+"\" class=\""+(lineCnt++%2!=0?"even":"odd")+"\">");
        out.println("<td headers=\"chgtype"+uid+" "+uniqueStr+"\">"+attstatus+"</td>");
        out.println("<td headers=\"attr"+uid+" "+uniqueStr+"\">"+description+"</td>");
        out.println("<td headers=\"prev"+uid+" "+uniqueStr+"\">"+priorval+"</td>");
        out.println("<td headers=\"curr"+uid+" "+uniqueStr+"\">"+curval+"</td>");
        out.println("<td headers=\"dateChgd"+uid+" "+uniqueStr+"\">"+changedatetime+"</td>");
        out.println("<td headers=\"user"+uid+" "+uniqueStr+"\">"+changeperson+"</td></tr>");
    }

    /********************************************************************************
    * get column headers for attributes
    */
    private void getColHeads(javax.servlet.jsp.JspWriter out,String uid, Hashtable diffRsrcTbl)
    throws IOException
    {
        out.println("<tr "+PokUtils.getColumnHeaderRowCSS()+">");
        out.println("<th style=\"text-align: center\" id=\"chgtype"+uid+"\">"+diffRsrcTbl.get("Change Type")+"</th>");//Change Type
        out.println("<th style=\"text-align: center\" id=\"attr"+uid+"\">"+diffRsrcTbl.get("Attribute")+"</th>");//Attribute
        out.println("<th style=\"text-align: center\" id=\"prev"+uid+"\">"+diffRsrcTbl.get("Previous Value")+"</th>");//Previous Value
        out.println("<th style=\"text-align: center\" id=\"curr"+uid+"\">"+diffRsrcTbl.get("Current Value")+"</th>");//Current Value
        out.println("<th style=\"text-align: center\" id=\"dateChgd"+uid+"\">"+diffRsrcTbl.get("Date Changed")+"</th>");//Date Changed
        out.println("<th style=\"text-align: center\" id=\"user"+uid+"\">"+diffRsrcTbl.get("Changed By")+"</th></tr>");//Changed By
    }

    /***********************************************************************
    * Called when the object was added so this DiffEntity is a current entityitem
    */
    public void setAdded() {
        status=ADD;
        currentItem = priorItem;
        priorItem = null;
        setMeta(currentItem);
        diffDone = true;
    }
    /***********************************************************************
    * Called when the object was deleted so this DiffEntity is a prior entityitem
    */
    public void setDeleted() {
        status=DEL;
        currentItem = null;
        setMeta(priorItem);
        diffDone = true;
    }
    /***********************************************************************
    * Called when the object was not changed at all, it existed at both times
    * but attributes may have changed
    *@param obj Diffable from current time (priorItem has the entityitem at instantiation time)
    */
    public void setNoChange(Diffable obj) {
        status=UNC;
        currentItem = ((DiffEntity)obj).priorItem;
        setMeta(currentItem);
        diffDone = true;
    }
    /***********************************************************************
    * Called when the object was not changed at all, it existed at both times
    * but attributes may have changed - this was flagged as new, merge the object
    * that was flagged as deleted
    * IN5947420
    *@param obj DiffEntity from previous time (priorItem has the entityitem at instantiation time)
    */
    public void mergeNoChange(DiffEntity obj) {
    	//this was originally ADD and currentItem was set
        status=UNC;
        priorItem = obj.priorItem;
        // recalc meta and attr changes
        metaAttrVct.clear();
        chgdAttrCnt = 0;
        setMeta(currentItem);
        diffDone = true;
    }
    /***********************************************************************
    * string rep
    * this is VERY important to the diff algo, do not change it!
    *@return String
    */
    public String toString() {
        String value = hashkey;
        // add this extra info after diff() algo has completed, just use hashkey for diff()
        if(diffDone){
            value = value+":"+status+" chgdAttrCnt:"+chgdAttrCnt+" path:"+path;
        }

        //String msg = (priorItem==null?"priorNULL":"priorSet")+" "+(currentItem==null?"currentNULL":"currentSet");
        return value;
    }

    /***********************************************************************
    * compare
    *@param obj
    *@return boolean
    */
    public boolean equals(Object obj){
        DiffEntity df = (DiffEntity)obj;
        // this is VERY important to the diff algo, do not change it!
        return hashkey.equals(df.hashkey);
    }
    /***********************************************************************
    * compare used for hashtables
    *@return int
    */
    public int hashCode(){
        // this is VERY important to the diff algo, do not change it!
        return hashkey.hashCode();
    }

    /********************************************************************************
    * Convert string into valid html.  Special HTML characters are converted.
    * SAX parser outputs the character, not the character entity reference;
    *
    * @param txt    String to convert
    * @return String
    */
    private String convertToHTML(String txt)
    {
        String retVal=null;
        StringBuffer htmlSB = new StringBuffer();
        StringCharacterIterator sci = null;
        char ch = ' ';
        if (txt != null) {
            sci = new StringCharacterIterator(txt);
            ch = sci.first();
            while(ch != CharacterIterator.DONE)
            {
                switch(ch)
                {
                case '<': // could be saved as &lt; also. this will be &#60;
                case '>': // could be saved as &gt; also. this will be &#62;
                case '"': // could be saved as &quot; also. this will be &#34;
                case '&': // ignore entity references such as &lt; if user typed it, user will see it
                          // could be saved as &amp; also. this will be &#38;
                    htmlSB.append("&#"+((int)ch)+";");
                    break;
                default:
                    htmlSB.append(ch);
                    break;
                }
                ch = sci.next();
            }
            retVal = htmlSB.toString();
        }

        return retVal;
    }

    // used to control attr display order
    private static class MetaComparator implements java.util.Comparator
    {
        public int compare(Object o1, Object o2) {
            String name1 = ((EANMetaAttribute) o1).getActualLongDescription().toLowerCase();
            String name2 = ((EANMetaAttribute) o2).getActualLongDescription().toLowerCase();
            return name1.compareTo(name2);
        }
    }
}
