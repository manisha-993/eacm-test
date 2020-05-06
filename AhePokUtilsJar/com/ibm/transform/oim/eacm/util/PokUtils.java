// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.util;

import java.util.*;
import java.text.*;
import COM.ibm.eannounce.objects.*;

/**********************************************************************************
 * This class contains utility methods used by reports.
 *
 */
// $Log: PokUtils.java,v $
// Revision 1.17  2011/03/21 19:35:06  wendy
// Add debug structure method
//
// Revision 1.16  2010/12/02 12:57:37  wendy
// Do not match empty value
//
// Revision 1.15  2010/07/11 19:55:15  wendy
// check for sb.length before returning from getAttributeFlagValue
//
// Revision 1.14  2008/10/22 12:42:22  wendy
// JAWS and CI162 updates
//
// Revision 1.13  2006/05/25 14:13:13  couto
// WebKing: <th> id duplication fix.
//
// Revision 1.12  2006/01/25 18:31:30  wendy
// AHE copyright
//
// Revision 1.11  2006/01/25 18:27:25  wendy
// Added AHE copyright info
//
// Revision 1.10  2005/12/16 22:54:05  wendy
// DQA changes for th id attributes and td headers attributes
//
// Revision 1.9  2005/12/15 22:57:24  wendy
// Changes for DQA, removed font tag and v6 references
//
// Revision 1.8  2005/10/12 17:49:44  wendy
// Add space to outputlist
//
// Revision 1.7  2005/10/04 12:57:52  wendy
// More chgs for new jtest config
//
// Revision 1.6  2005/10/03 20:09:31  wendy
// Conform to new jtest config
//
// Revision 1.5  2005/09/26 02:37:44  wendy
// More AHE changes
//
// Revision 1.4  2005/09/24 22:27:45  wendy
// Added methods used to comply with AHE format
//
// Revision 1.3  2005/09/13 12:59:49  wendy
// Change default appname
//
// Revision 1.2  2005/09/12 16:54:43  wendy
// Add .wss to servlet name
//
// Revision 1.1  2005/09/08 18:05:10  wendy
// Init OIM3.0b
//
//
public class PokUtils
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /* A utility class only contains static methods and static variables. Since an
    implicit default constructor is "public" and a utility class is not designed to
    be instantiated, the "public" default constructor of a utility class should be
    declared "private".  */
    private PokUtils() {}

    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);

    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.17 $";
    /** not populated string */
    public static final String DEFNOTPOPULATED = "<em>** Not Populated **</em>";
    /** delimiter */
    public static final String DELIMITER = "|";
    /** middleware ve limit */
    public static final int MW_VENTITY_LIMIT=50;
    /** indicator report was invoked from DG */
    public static final String DG_INDICATOR = "targetDGResource";//"raik";
    /** Required field text */
    public static final String REQUIRED_FLD_TXT = "Required fields are marked with an asterisk (*) and must be filled in to complete the form.";


    /**
     * Returns true if the flag is selected. Returns false if any parameter is null,
     * the attribute code is not a flag attribute, or the flag code is not valid for the attribute code.
     *@param item EntityItem to check
     *@param attCode String the attribute code to check
     *@param flagCode String the flagCode to check
     *@return boolean true if it is selected
     */
    public static boolean isSelected(EntityItem item, String attCode, String flagCode) {
        EANAttribute att = null;
        boolean selected=false;
        if (item != null && attCode != null && flagCode != null) {
            // Get the attribute
            att = item.getAttribute(attCode);
            // If it is a Flag
            if (att instanceof EANFlagAttribute) {
                EANFlagAttribute fAtt = (EANFlagAttribute) att;
                EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) item.getEntityGroup().getMetaAttribute(attCode);
                MetaFlag mf = mfa.getMetaFlag(flagCode);
                if (mf != null) {
                    selected= fAtt.isSelected(mf);
                }
            }
        }
        return selected;
    }
    /**
     * Returns true if the flag is selected. Returns false if any parameter is null,
     * the attribute code is not a flag attribute, or the flag code is not valid for the attribute code.
     *@param item EntityItem to check
     *@param attCode String the attribute code to check
     *@param set Set
     *@return boolean
     */
    public static boolean contains(EntityItem item, String attCode, Set set) {
        boolean found=false;
        // Get the attribute
        EANAttribute att = item.getAttribute(attCode);
        // If it is a Flag
        if (att instanceof EANFlagAttribute) {
            EANFlagAttribute fAtt = (EANFlagAttribute) att;
            // Get all the Flag values.
            MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
            // Go through all the flag values
            for (int i = 0; i < mfArray.length; i++) {
                // If this flag is in the set
                if (set.contains(mfArray[i].getFlagCode())) {
                    // Check that it is selected
                    if (mfArray[i].isSelected()) {
                        found=true;
                        break;
                    }
                }
            }
        }
        return found;
    }

    /**
     * Returns true if all values in set are selected
     * the attribute code is not a flag attribute, or the flag code is not valid for the attribute code.
     *@param item EntityItem to check
     *@param attCode String the attribute code to check
     *@param set Set
     *@return boolean
     */
    public static boolean containsAll(EntityItem item, String attCode, Set set) {
        // Assume no flags in the set are selected
        int count = 0;
        // Get the attribute
        EANAttribute att = item.getAttribute(attCode);
        // If it is a Flag
        if (att instanceof EANFlagAttribute) {
            EANFlagAttribute fAtt = (EANFlagAttribute) att;
            // Get all the Flag values
            MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
            // Go through all the flag values
            for (int i = 0; i < mfArray.length; i++) {
                // If this flag is in the set
                if (set.contains(mfArray[i].getFlagCode())) {
                    // Count that it is selected
                    if (mfArray[i].isSelected()){
                        count++;}
                }
            }
        }
        return set.size() == count;
    }
    /********************************************************************************
    * Get the display value for the Attribute.
    *
    * @param item EntityItem
    * @param attCode    String Attribute code
    * @param delim      String delimiter used to separate flag values (descriptions)
    * @param defValue   String used if attribute does not have a value
    * @return String
    */
    public static String getAttributeValue(EntityItem item, String attCode, String delim, String defValue)
    {
        return getAttributeValue(item, attCode, delim, defValue, true, "transform/oim/eacm/bui");
    }

    /********************************************************************************
    * Get the display value for the Attribute.
    *
    * @param item EntityItem
    * @param attCode    String Attribute code
    * @param delim      String delimiter used to separate flag values (descriptions)
    * @param defValue   String used if attribute does not have a value
    * @param convert    boolean if true, value will be converted to valid html
    * @return String
    */
    public static String getAttributeValue(EntityItem item, String attCode, String delim, String defValue,
        boolean convert)
    {
        return getAttributeValue(item, attCode, delim, defValue, convert, "transform/oim/eacm/bui");
    }

    /********************************************************************************
    * Get the display value for the Attribute.
    *
    * @param item EntityItem
    * @param attCode    String Attribute code
    * @param delim      String delimiter used to separate flag values (descriptions)
    * @param defValue   String used if attribute does not have a value
    * @param convert    boolean if true, value will be converted to valid html
    * @param applicationName String with application name for getblob
    * @return String
    */
    public static String getAttributeValue(EntityItem item, String attCode, String delim, String defValue,
        boolean convert, String applicationName)
    {
        EANMetaAttribute metaAttr = item.getEntityGroup().getMetaAttribute(attCode);
        String value=defValue;
        if (metaAttr==null) {
            value= "<span style=\"color:#c00; font-weight:bold;\">Attribute &quot;"+attCode+"&quot; NOT found in &quot;"+
                item.getEntityType()+"&quot; META data.</span>";
        }
        else {
            EANAttribute attr = item.getAttribute(attCode);
            if (attr != null)
            {
                if (attr instanceof EANFlagAttribute)
                {
					if (!metaAttr.getAttributeType().equals("F"))
					{
						value = attr.toString(); // this is faster
						if (convert){
							value = PokUtils.convertToHTML(value);
						}
					}else{
		                StringBuffer sb = new StringBuffer();
						// Get all the Flag values.
						MetaFlag[] mfArray = (MetaFlag[]) attr.get();
						for (int i = 0; i < mfArray.length; i++)
						{
							// get selection
							if (mfArray[i].isSelected())
							{
								if (sb.length()>0) {
									sb.append(delim); }
								// convert all flag descriptions too
								if (convert){
									sb.append(convertToHTML(mfArray[i].toString()));
								}
								else {
									sb.append(mfArray[i].toString());
								}
							}
						}
						value = sb.toString();
					}
                }
                else if (attr instanceof EANTextAttribute)
                {
                    // L and T and I text attributes must be converted to prevent invalid html
                    if (metaAttr.getAttributeType().equals("T") || metaAttr.getAttributeType().equals("L")
                        || metaAttr.getAttributeType().equals("I")) // FB52179
                    {
                        // convert the html special chars
                        if (convert) {
                            value = convertToHTML(attr.get().toString());
                        }
                        else {
                            value = attr.get().toString();
                        }
                    }
                    else {
                        value = attr.get().toString();
                    }
                }
                else if (attr instanceof EANBlobAttribute)
                {
                    // only 'B' binary now
                    if (metaAttr.getAttributeType().equals("B"))
                    {
                        EANBlobAttribute blobAtt = (EANBlobAttribute) attr;
                        // sometimes the entire file name, rather than just
                        // the extension, is stored in the m_strBlobExtension
                        // variable.
                        if (blobAtt.getBlobExtension().toUpperCase().endsWith(".GIF") ||
                            blobAtt.getBlobExtension().toUpperCase().endsWith(".JPG"))
                        {
                            value = "<img src='/"+applicationName+"/GetBlobAttribute.wss?entityID=" + item.getEntityID() +
                                "&entityType=" + item.getEntityType() +
                                "&attributeCode=" + attCode +
                                "' alt='image' />";  // close tag needed for XML
                        }
                        else
                        {
							StringBuffer sb = new StringBuffer();

                            // the HTML field is a link to a temp file generated by the
                            // GetBlobAttribute Servlet. note, we use ouputMode=F to indicate we
                            // want to generate a temp file and
                            // execute a browser-redirect to the temp file.
                            /*sb.append("<a href='/"+applicationName+"/GetBlobAttribute.wss?outputMode=F"+
                                "&entityID=" + item.getEntityID()+
                                "&entityType=" + item.getEntityType()+
                                "&attributeCode=" +attCode);
                            sb.append("' />");
                            sb.append("Download this file for viewing.</a>");*/

                            // add support for other binary types FB53628:6FF425
                            sb.append("<form action=\"/"+applicationName+"/PokXMLDownload.wss\" name=\""+item.getEntityType()
                                    +item.getEntityID()+attCode+"\" method=\"post\"> "+NEWLINE);
                            sb.append("<input type=\"hidden\" name=\"entityType\" value=\"" + item.getEntityType() +"\" />"+NEWLINE);
                            sb.append("<input type=\"hidden\" name=\"entityID\" value=\"" + item.getEntityID() +"\" />"+NEWLINE);
                            sb.append("<input type=\"hidden\" name=\"downloadType\" value=\"blob\" />"+NEWLINE);
                            sb.append("<input type=\"hidden\" name=\"attributeCode\" value=\""+attCode+"\" />"+NEWLINE);
                            sb.append("<input type=\"submit\" value=\"Download\" />"+NEWLINE);
                            sb.append("</form>"+NEWLINE);

                            //sb.append("<span style=\"color:#c00; font-weight:bold;\">Blob Attribute for "+attCode+", extension: "+
                            //  blobAtt.getBlobExtension()+" is NOT yet supported</span>");
                            value = sb.toString();
                        }
                    }
                    else {
                        value = "<span style=\"color:#c00; font-weight:bold;\">Blob Attribute type &quot;"+metaAttr.getAttributeType()+
                            "&quot; for "+attCode+" NOT yet supported</span>";
                    }
                }

                if (value.length()==0) {
                    value=defValue;
                }
            }
        }

        return value;
    }

    /********************************************************************************
    * Convert string into valid html.  Special HTML characters are converted.
    *
    * @param txt    String to convert
    * @return String
    */
    public static String convertToHTML(String txt)
    {
		String retVal=txt;

		if (txt.indexOf("&")!=-1 ||txt.indexOf("<")!=-1 ||txt.indexOf(">")!=-1 ||txt.indexOf("\"")!=-1
			||txt.indexOf("\n")!=-1)
		{

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
					case '\n':  // maintain new lines
						htmlSB.append("<br />");
						break;
					default:
						//if (Character.isSpaceChar(ch))// check for unicode space character
						//{
						//    htmlSB.append("&#32;"); // this fails because extra whitespace, even &#32;, is discarded
							// but left to be consistent with WestCoast code
		//                      htmlSB.append("&nbsp;"); // this will correctly maintain spaces
						//}
						//else {
						htmlSB.append(ch);
							//}
						break;
					}
					ch = sci.next();
				}
				retVal = htmlSB.toString();
			}
		}
        return retVal;
    }

    /********************************************************************************
    * Find entities of the destination type linked to the EntityItems in the EntityGroup
    * through the specified link type.  Both uplinks and downlinks are checked
    * though only one will contain a match.
    * All objects in the source Vector must be EntityItems of the same entity type
    *
    * @param srcGrp     EntityGroup
    * @param linkType   String Association or Relator type linking the entities
    * @param destType   String EntityType to match
    * @return Vector of EntityItems
    */
    public static Vector getAllLinkedEntities(EntityGroup srcGrp, String linkType, String destType)
    {
        // find entities thru 'linkType' relators
        Vector destVct = new Vector(1);
        if (srcGrp != null) {
            for(int ie=0; ie<srcGrp.getEntityItemCount();ie++)
            {
                EntityItem entityItem = srcGrp.getEntityItem(ie);
                getLinkedEntities(entityItem, linkType, destType, destVct);
            }
        }

        return destVct;
    }

    /********************************************************************************
    * Find entities of the destination type linked to the EntityItems in the source
    * vector through the specified link type.  Both uplinks and downlinks are checked
    * though only one will contain a match.
    * All objects in the source Vector must be EntityItems of the same entity type
    *
    * @param srcVct     Vector of EntityItems
    * @param linkType   String Association or Relator type linking the entities
    * @param destType   String EntityType to match
    * @returns Vector of EntityItems
    */
    public static Vector getAllLinkedEntities(Vector srcVct, String linkType, String destType)
    {
        // find entities thru 'linkType' relators
        Vector destVct = new Vector(1);

        Iterator srcItr = srcVct.iterator();
        while (srcItr.hasNext())
        {
            EntityItem entityItem = (EntityItem)srcItr.next();
            getLinkedEntities(entityItem, linkType, destType, destVct);
        }

        return destVct;
    }

    /********************************************************************************
    * Find entities of the destination type linked to the EntityItems in the source
    * vector through the specified link type.  Both uplinks and downlinks are checked
    * though only one will contain a match.
    * All objects in the source Vector must be EntityItems of the same entity type
    *
    * @param entityItem EntityItem
    * @param linkType   String Association or Relator type linking the entities
    * @param destType   String EntityType to match
    * @returns Vector of EntityItems
    */
    public static Vector getAllLinkedEntities(EntityItem entityItem, String linkType, String destType)
    {
        // find entities thru 'linkType' relators
        Vector destVct = new Vector(1);

        getLinkedEntities(entityItem, linkType, destType, destVct);

        return destVct;
    }

    /********************************************************************************
    * Find entities of the destination type linked to the specified EntityItem through
    * the specified link type.  Both uplinks and downlinks are checked though only
    * one will contain a match.
    *
    * @param entityItem EntityItem
    * @param linkType   String Association or Relator type linking the entities
    * @param destType   String EntityType to match
    * @param destVct    Vector EntityItems found are returned in this vector
    */
    private static void getLinkedEntities(EntityItem entityItem, String linkType, String destType,
        Vector destVct)
    {
        if (entityItem==null) {
            return; }

        // see if this relator is used as an uplink
        for (int ui=0; ui<entityItem.getUpLinkCount(); ui++)
        {
            EANEntity entityLink = entityItem.getUpLink(ui);
            if (entityLink.getEntityType().equals(linkType))
            {
                // check for destination entity as an uplink
                for (int i=0; i<entityLink.getUpLinkCount(); i++)
                {
                    EANEntity entity = entityLink.getUpLink(i);
                    if (entity.getEntityType().equals(destType) && !destVct.contains(entity)) {
                        destVct.addElement(entity); }
                }
                // check for destination entity as a downlink
                /*for (int i=0; i<entityLink.getDownLinkCount(); i++)
                {
                    EANEntity entity = entityLink.getDownLink(i);
                    if (entity.getEntityType().equals(destType) && !destVct.contains(entity))
                        destVct.addElement(entity);
                }*/
            }
        }

        // see if this relator is used as a downlink
        for (int ui=0; ui<entityItem.getDownLinkCount(); ui++)
        {
            EANEntity entityLink = entityItem.getDownLink(ui);
            if (entityLink.getEntityType().equals(linkType))
            {
                // check for destination entity as an uplink
                /*for (int i=0; i<entityLink.getUpLinkCount(); i++)
                {
                    EANEntity entity = entityLink.getUpLink(i);
                    if (entity.getEntityType().equals(destType) && !destVct.contains(entity))
                        destVct.addElement(entity);
                }*/
                // check for destination entity as a downlink
                for (int i=0; i<entityLink.getDownLinkCount(); i++)
                {
                    EANEntity entity = entityLink.getDownLink(i);
                    if (entity.getEntityType().equals(destType) && !destVct.contains(entity)) {
                        destVct.addElement(entity); }
                }
            }
        }
    }

    /********************************************************************************
    * Find entities in the specified vector that have an attribute value that
    * matches the passed in value.
    * Flags and text attributes are checked.  If it is a flag, flag codes are checked.
    * If multiple values are needed, separate them with a Delimiter ("|")
    *
    * @param srcVct Vector of EntityItems
    * @param attrCode   String Attribute code
    * @param valToMatch String Value(s) to be matched
    * @return Vector of EntityItems
    */
    public static Vector getEntitiesWithMatchedAttr(Vector srcVct, String attrCode, String valToMatch)
    {
        Vector matchVct = new Vector(1);
        // get list of entities with attr = valToMatch
        Iterator srcItr = srcVct.iterator();
        while (srcItr.hasNext())
        {
            EntityItem entityItem = (EntityItem)srcItr.next();
            if (entityMatchesAttr(entityItem, attrCode, valToMatch)) {
                matchVct.addElement(entityItem); }
        }

        return matchVct;
    }

    /********************************************************************************
    * Find entities in the specified group that have an attribute value that
    * matches the passed in value.
    * Flags and text attributes are checked.  If it is a flag, flag codes are checked.
    * If multiple values are needed, separate them with a Delimiter ("|")
    *
    * @param srcGrp EntityGroup
    * @param attrCode   String Attribute code
    * @param valToMatch String Value(s) to be matched
    * @return Vector of EntityItems
    */
    public static Vector getEntitiesWithMatchedAttr(EntityGroup srcGrp, String attrCode, String valToMatch)
    {
        Vector matchVct = new Vector(1);
        if (srcGrp != null) {
            // get list of entities with attr = valToMatch
            for(int ie=0; ie<srcGrp.getEntityItemCount();ie++)
            {
                EntityItem entityItem = srcGrp.getEntityItem(ie);
                if (entityMatchesAttr(entityItem, attrCode, valToMatch)) {
                    matchVct.addElement(entityItem);}
            }
        }

        return matchVct;
    }

    /********************************************************************************
    * Find entities in the specified vector that have attribute values that
    * match the passed in values.  All values must match.
    * Flags and text attributes are checked.  If it is a flag, flag codes are checked.
    * If multiple values are needed, separate them with a Delimiter ("|")
    *
    * @param srcVct Vector of EntityItems
    * @param attrValTbl Hashtable with Attribute code as key and String to match as value
    * @returns Vector of EntityItems
    */
    public static Vector getEntitiesWithMatchedAttr(Vector srcVct, Hashtable attrValTbl)
    {
        Vector matchVct = new Vector(1);
        // get list of entities with attr = valToMatch
        Iterator srcItr = srcVct.iterator();
        while (srcItr.hasNext())
        {
            EntityItem entityItem = (EntityItem)srcItr.next();
            boolean allMatch=true;
            for (Enumeration e = attrValTbl.keys(); e.hasMoreElements();)
            {
                String attrCode = (String)e.nextElement();
                allMatch=allMatch&&
                    entityMatchesAttr(entityItem, attrCode, attrValTbl.get(attrCode).toString());
            }
            if (allMatch) {
                matchVct.addElement(entityItem);}
        }

        return matchVct;
    }

    /********************************************************************************
    * Find entities in the specified group that have attribute values that
    * match the passed in values.  All values must match.
    * Flags and text attributes are checked.  If it is a flag, flag codes are checked.
    * If multiple values are needed, separate them with a Delimiter ("|")
    *
    * @param srcGrp EntityGroup
    * @param attrValTbl Hashtable with Attribute code as key and String to match as value
    * @returns Vector of EntityItems
    */
    public static Vector getEntitiesWithMatchedAttr(EntityGroup srcGrp, Hashtable attrValTbl)
    {
        Vector matchVct = new Vector(1);
        if (srcGrp != null) {
            // get list of entities with attr = valToMatch
            for(int ie=0; ie<srcGrp.getEntityItemCount();ie++)
            {
                EntityItem entityItem = srcGrp.getEntityItem(ie);
                boolean allMatch=true;
                for (Enumeration e = attrValTbl.keys(); e.hasMoreElements();)
                {
                    String attrCode = (String)e.nextElement();
                    allMatch=allMatch&&
                        entityMatchesAttr(entityItem, attrCode, attrValTbl.get(attrCode).toString());
                }
                if (allMatch) {
                    matchVct.addElement(entityItem);}
            }
        }

        return matchVct;
    }

    /********************************************************************************
    * Check to see if this entity's attribute has a value to match the passed in value.
    * Flags and text attributes are checked.  If it is a flag, flag codes are checked.
    * If multiple values are needed, separate them with a Delimiter ("|")
    *
    * @param entityItem EntityItem
    * @param attrCode   String Attribute code
    * @param valToMatch String Value(s) to be matched
    * @return boolean
    */
    private static boolean entityMatchesAttr(EntityItem entityItem, String attrCode, String valToMatch)
    {
        boolean found=false;
        // Multi-flag values will be separated by |
        EANAttribute attr = entityItem.getAttribute(attrCode);
        if (attr != null && 
        		valToMatch !=null && valToMatch.trim().length()>0) { // do not match empty value
            if (attr instanceof EANFlagAttribute)
            {
                Vector vct = new Vector(1);
                String[] matchArray = null;
                int count=0;
                // Get all the Flag values.
                MetaFlag[] mfArray = (MetaFlag[]) attr.get();
                for (int i = 0; i < mfArray.length; i++)
                {
                    // get selection
                    if (mfArray[i].isSelected())
                    {
                        vct.addElement(mfArray[i].getFlagCode());
                    }
                }

                // convert valToMatch into an array of strings separated by Delimiter ("|")
                matchArray = convertToArray(valToMatch);
                for (int i=0; i<matchArray.length; i++)
                {
                    if (vct.contains(matchArray[i])) {
                        count++; }
                }
                if (count==matchArray.length) {
                    found = true;
                }

            }
            else if (attr instanceof EANTextAttribute)
            {
                // values must match
                found = attr.get().toString().equals(valToMatch);
            }
        }

        return found;
    }

    /********************************************************************************
    * Convert the string into an array.  The string is a list of values delimited by
    * Delimiter
    *
    * @param data String
    * @returns String[] one delimited string per element
    */
    public static String[] convertToArray(String data)
    {
        Vector vct = new Vector();
        String array[] = null;
        // parse the string into substrings
        if (data!=null)
        {
            StringTokenizer st = new StringTokenizer(data,DELIMITER);
            while(st.hasMoreTokens())
            {
                vct.addElement(st.nextToken());
            }
        }
        array= new String[vct.size()];
        vct.copyInto(array);
        return array;
    }

    /*****************************************************************************
    * Get the all Meta Flag Values for the specified attribute
    *
    * @param entityGroup EntityGroup with entity type to find metaattribute
    * @param attrCode String attribute code
    * @returns String[] returned as a | delimited string, "flag value|display text"
    */
    public static String[] getMetaFlags(EntityGroup entityGroup, String attrCode)
    {
        EANMetaAttribute ma = null;
        EANMetaFlagAttribute fma = null;
        String tmp[] = null;
        if (entityGroup!=null) {
            ma = entityGroup.getMetaAttribute(attrCode);
            if (ma!=null && (ma instanceof EANMetaFlagAttribute)) {
                fma = (EANMetaFlagAttribute)ma;
                tmp = new String[fma.getMetaFlagCount()];
                for (int i=0; i<fma.getMetaFlagCount(); i++)
                {
                    MetaFlag omf = fma.getMetaFlag(i);
                    tmp[i] = omf.getFlagCode()+PokUtils.DELIMITER+omf.toString();
                }
            }
        }
        return tmp;
    }

    /********************************************************************************
    * Get table header descriptions
    *
    * @param entityGroup EntityGroup with entity type to find metaattribute
    * @param attrCodes String[] attribute code
    * @returns String
    */
    public static String getTableHeader(EntityGroup entityGroup, String []attrCodes)
    {
    	return getTableHeader(entityGroup, attrCodes, 0);
    }

	/********************************************************************************
	* Get table header descriptions
	*
	* @param entityGroup EntityGroup with entity type to find metaattribute
	* @param attrCodes String[] attribute code
	* @param index Index for making the th tags unique
	* @returns String
	*/
	public static String getTableHeader(EntityGroup entityGroup, String []attrCodes, int index)
	{
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<attrCodes.length; i++)
		{
			EANMetaAttribute ma = entityGroup.getMetaAttribute(attrCodes[i]);
			String desc = attrCodes[i];
			if (ma != null) {
				desc = ma.getActualLongDescription(); }
			sb.append("<th id=\"" + attrCodes[i] + "_" + index + "\">"+desc+"</th>"+NEWLINE);
		}
		return sb.toString();
	}

    /********************************************************************************
    * Find and display data for each attribute in the array
    *
    * @param entityItem EntityItem
    * @param attrCodes String[]
    * @param convertHtml boolean
    * @param flagDelimStr String
    * @param defaultValue String
    * @return String
    */
    public static String getCellData(EntityItem entityItem, String[] attrCodes, boolean convertHtml,
        String flagDelimStr, String defaultValue)
    {
        return getCellData(entityItem, attrCodes, convertHtml, flagDelimStr, defaultValue, 0);
    }

	/********************************************************************************
	* Find and display data for each attribute in the array
	*
	* @param entityItem EntityItem
	* @param attrCodes String[]
	* @param convertHtml boolean
	* @param flagDelimStr String
	* @param defaultValue String
	* @param index int for th id tags
	* @return String
	*/
	public static String getCellData(EntityItem entityItem, String[] attrCodes, boolean convertHtml,
		String flagDelimStr, String defaultValue, int index)
	{
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<attrCodes.length; i++)
		{
			String value = getAttributeValue(entityItem, attrCodes[i], flagDelimStr,
				defaultValue,convertHtml);
			sb.append("<td headers=\"" + attrCodes[i] + "_" + index + "\">"+value+"</td>"+NEWLINE);
		}

		return sb.toString();
	}

    /********************************************************************************
    * Sort on id code
    *
    * @param entityItems Vector
    * @param idCode String
    * @return Vector
    */
    public static Vector sort(Vector entityItems, String idCode)
    {
        Vector sortedVct = null;
        if (entityItems.size()==0) {
            sortedVct= new Vector(1);
        }
        else{
            sortedVct = new Vector(entityItems.size());  // limit size needed
            // look at each element in the vector
            for (int i=0,c=entityItems.size(); i<c; i++)
            {
                EntityItem ei = (EntityItem)entityItems.elementAt(i);
                String value =  getAttributeValue(ei, idCode, "", DEFNOTPOPULATED,false);
                int id=0;
                for(; id<sortedVct.size(); id++)
                {
                    EntityItem ei2 = (EntityItem)sortedVct.elementAt(id);
                    String value2 = getAttributeValue(ei2,idCode,"",DEFNOTPOPULATED,false);
                    if (value2.compareTo(value)>0) {
                        break; }
                }
                // store them in lexical order
                sortedVct.insertElementAt(ei,id);
            }
        }
        return sortedVct;
    }

    /********************************************************************************
    * Get attribute description
    *
    * @param entityGroup EntityGroup
    * @param attrCode String attribute code
    * @param defValue String
    * @return String
    */
    public static String getAttributeDescription(EntityGroup entityGroup, String attrCode, String defValue)
    {
        EANMetaAttribute ma = null;
        String desc=null;
        if (entityGroup==null) {
            desc= defValue;
        }
        else {
            ma = entityGroup.getMetaAttribute(attrCode);
            desc = defValue;
            if (ma != null) {
                desc = ma.getActualLongDescription();
            }
        }

        return desc;
    }

    /*****************************************************************************
    * Get the current Flag Value for the specified attribute, null if not set
    *
    * @param entityItem EntityItem
    * @param attrCode String attribute code to get value for
    * @return String attribute flag code
    */
    public static String getAttributeFlagValue(EntityItem entityItem, String attrCode)
    {
        EANMetaAttribute metaAttr = entityItem.getEntityGroup().getMetaAttribute(attrCode);
        // Multi-flag values will be separated by |
        EANAttribute attr = entityItem.getAttribute(attrCode);
        String val=null;
        if (attr != null) {
            if (attr instanceof EANFlagAttribute)
            {
                StringBuffer sb = new StringBuffer();

                // Get the selected Flag codes.
                MetaFlag[] mfArray = (MetaFlag[]) attr.get();
                for (int i = 0; i < mfArray.length; i++)
                {
                    // get selection
                    if (mfArray[i].isSelected())
                    {
                        if (sb.length()>0) {
                            sb.append(DELIMITER); }
                        sb.append(mfArray[i].getFlagCode());
                        if (metaAttr.getAttributeType().equals("U")) {
                            break; }
                    }
                }
                if(sb.length()>0){
                	val = sb.toString();
                }
            }
        }

        return val;
    }

    /********************************************************************************
    * Use api to get creation date for this entity
    *
    * @param dbCurrent Database object
    * @param profile Profile object
    * @param eItem EntityItem object
    * @returns String
    */
    public static String getCreateDate(COM.ibm.opicmpdh.middleware.Database dbCurrent,
        COM.ibm.opicmpdh.middleware.Profile profile, EntityItem eItem)
    {
        String crtDate = DEFNOTPOPULATED;
        java.sql.ResultSet rs = null;
        try
        {
            COM.ibm.opicmpdh.middleware.ReturnDataResultSet rs2913 = null;
            COM.ibm.opicmpdh.middleware.ReturnStatus returnStatus =
                new COM.ibm.opicmpdh.middleware.ReturnStatus(-1);
            rs = dbCurrent.callGBL2913(returnStatus, profile.getEnterprise(),
                eItem.getEntityType(), eItem.getEntityID(), profile.getValOn(), profile.getEffOn());
            rs2913 = new COM.ibm.opicmpdh.middleware.ReturnDataResultSet(rs);
            //rs.close();
            dbCurrent.freeStatement();

            /*
            col 0: Enterprise
            col 1: entity type
            col 2: entity id
            col 3: Valfrom
            col 4: Valto
            col 5: EffFrom
            col 6: EffTo
            col 7: open id
            col 8: UserName of the update
            It is ordered by valfrom,valto
            */

            if (rs2913.getRowCount()>0)  // entity exists
            {
                // get the first valfrom date
                crtDate = rs2913.getColumn(0,3);
            }
        }
        catch (Exception x){
            System.out.println(x.getMessage());}
        finally{
            try{
                if (rs!=null){
                    rs.close();
                }
            }catch(Exception xs){
                System.out.println(xs.getMessage());}
        }

        return crtDate;
    }

/*
    public static void main(String[] args) throws Exception
    {
        String txt = "This &        is \n<s> \" '.";
        if (args.length>0)
            txt = args[0];
        System.out.println("before txt: "+txt+"\n after txt: "+convertToHTML(txt));
        System.out.println("wc txt: "+COM.ibm.opicmpdh.eannounce.webapp.TextConverter.fromTextToHtml(txt));
    }
    */
    /**
     * Gets the list of selected MetaFlags as an EANList.
     * The key it the String flag code and the values it the MetaFlag itself.
     * If null is passed or if the the EANAttribute is not a EANFlagAttribute or
     * one of it's subclasses it will return an empty EANList
     *
     *@param  att  EANAttribute The flag attribute
     *@return     EANList The selectedMetaFlagList value
     */
    public static EANList getSelectedMetaFlagList(EANAttribute att) {
        EANList l = new EANList();
        if (att != null && att instanceof EANFlagAttribute) {
            MetaFlag[] mfa = (MetaFlag[]) ((EANFlagAttribute) att).get();
            for (int i = 0; i < mfa.length; i++) {
                if (mfa[i].isSelected()) {
                    l.put(mfa[i].getFlagCode(), mfa[i]);
                }
            }
        }
        return l;
    }
    private static final int L11 = 11;

    /**
     * Returns just the revision number of a CVS $Revision: 1.17 $ string.
     * If the cvsRevision is null it returns the empty string.
     * If the cvsRevision is not a CVS Revision string it returns the cvsRevision.
     *
     *@param  cvsRevision String The CVS revision string
     *@return     The CVS revision number
     */
    public static String truncateCVSRevision(String cvsRevision) {
        String val = cvsRevision;
        if (cvsRevision == null) {
            val= "";}
        else
        if (cvsRevision.startsWith("$Revision: ")) {
            val= cvsRevision.substring(L11, cvsRevision.length() - 1);
        }
        return val;
    }

    /**
     *  Create a geo tag based on the entity item
     *
     *@param  theGAL  the GeneralAreaList used to determine the RFA GEO
     *@param  ei      EntityItem to test
     *@return         String representing the tag
     */
    public final static String getGEOTag(GeneralAreaList theGAL, EntityItem ei) {
        StringBuffer geoTag = new StringBuffer();
        boolean one = false;
        if (theGAL.isRfaGeoUS(ei)) {
            geoTag.append("US");
            one = true;
        }
        if (theGAL.isRfaGeoAP(ei)) {
            if (one) {
                geoTag.append(", ");
            }
            geoTag.append("AP");
            one = true;
        }
        if (theGAL.isRfaGeoLA(ei)) {
            if (one) {
                geoTag.append(", ");
            }
            geoTag.append("LA");
            one = true;
        }
        if (theGAL.isRfaGeoCAN(ei)) {
            if (one) {
                geoTag.append(", ");
            }
            geoTag.append("CAN");
            one = true;
        }
        if (theGAL.isRfaGeoEMEA(ei)) {
            if (one) {
                geoTag.append(", ");
            }
            geoTag.append("EMEA");
            one = true;
        }
        return geoTag.toString();
    }

    private static final int LEVEL1 = 1;
    private static final int LEVEL2 = 2;
    private static final int LEVEL3 = 3;
    private static final int LEVEL4 = 4;
    /********************************************************************************
    * Get style to use for the specified row
    *
    * @param level int
    * @returns String
    */
    public static String getRowLevelCSS(int level)
    {
        String css=null;
        switch(level) {
        case LEVEL1:
            css=" style=\"background-color:#69c;\"";  //blue-med
            break;
        case LEVEL2:
            css=" style=\"background-color:#fe2;\"";  //yellow-med
            break;
        case LEVEL3:
            css=" style=\"background-color:#bd6;\"";  //green-med-light
            break;
        case LEVEL4:
            css=" style=\"background-color:#f90;\"";  //orange-med
            break;
        default:
            css=" style=\"background-color:#f76;\"";  //pink-med
            break;
        }
        return css;
    }
    /********************************************************************************
    * Get style to use for table header row
    *
    * @returns String
    */
    public static String getTableHeaderRowCSS()
    {
        return " style=\"background-color:#aaa;\"";  //gray-med
    }
    /********************************************************************************
    * Get style to use for column header row
    *
    * @returns String
    */
    public static String getColumnHeaderRowCSS()
    {
        return " style=\"background-color:#cef;\""; //blue-light
    }

    /********************************************************************************
    * Get javascript warning needed for forms with javascript
    * from http://w3.ibm.com/standards/intranet/design/v8/105_int_content/02_forms.html#javascript
    * 14. If you are using JavaScript in the form, you must notify users who have JavaScript
    * disabled that it should be enabled in order to take full advantage of some functionality.
    *
    * @returns String
    */
    public static String getJavaScriptWarning()
    {
        return"<!-- message to those without javascript enabled   -->"+NEWLINE+
        "<noscript>"+NEWLINE+
        "<h2> This page requires Internet Explorer 5.0 or above.</h2>"+NEWLINE+
        "<p>To make full use of this site, you should have Javascript"+NEWLINE+
        "enabled on your browser.  Your browser either does not support Javascript"+NEWLINE+
        "or has it disabled. If you want to correctly view this page, please"+NEWLINE+
        "upgrade your browser or enable Javascript support.</p>"+NEWLINE+
        "</noscript>"+NEWLINE;
    }

    private static final int MAX_LIST_LEN=256;
    /********************************************************************************
    * display content of entitylist for debug
    *@param  list    EntityList
    *@return String
    */
    public static String outputList(EntityList list) // debug only
    {
        StringBuffer sb = new StringBuffer();
        EntityGroup peg = null;
        if (list==null) {
            sb.append("Null List");
        }else{
            peg =list.getParentEntityGroup();
            if (peg!=null)
            {
                sb.append(peg.getEntityType()+" : "+peg.getEntityItemCount()+" parent items. ");
                if (peg.getEntityItemCount()>0)
                {
                    StringBuffer tmpsb = new StringBuffer();  // prevent more than 2048 chars in a line
                    tmpsb.append("IDs(");
                    for (int e=0; e<peg.getEntityItemCount(); e++)
                    {
                        tmpsb.append(" "+peg.getEntityItem(e).getEntityID());
                        if (tmpsb.length()>MAX_LIST_LEN)
                        {
                            sb.append(tmpsb.toString()+NEWLINE);
                            tmpsb.setLength(0);
                        }
                    }
                    tmpsb.append(")");
                    sb.append(tmpsb.toString());
                }
                sb.append(NEWLINE);
            }

            for (int i=0; i<list.getEntityGroupCount(); i++)
            {
                EntityGroup eg =list.getEntityGroup(i);
                sb.append(eg.getEntityType()+" : "+eg.getEntityItemCount()+" entity items. ");
                if (eg.getEntityItemCount()>0)
                {
                    StringBuffer tmpsb = new StringBuffer();  // prevent more than 2048 chars in a line
                    tmpsb.append("IDs(");
                    for (int e=0; e<eg.getEntityItemCount(); e++)
                    {
                        tmpsb.append(" "+eg.getEntityItem(e).getEntityID());
                        if (tmpsb.length()>MAX_LIST_LEN)
                        {
                            sb.append(tmpsb.toString()+NEWLINE);
                            tmpsb.setLength(0);
                        }
                    }
                    tmpsb.append(")");
                    sb.append(tmpsb.toString());
                }
                sb.append(NEWLINE);
            }
        }
        return sb.toString();
    }

    /**
     * output EntityItem up and down links in the EntityList
     * @param list
     * @return
     */
    public static String outputListStructure(EntityList list){
    	if (list==null){
    		return "Null list";
    	}
    	StringBuffer sb = new StringBuffer();
    	outputGroupStructure(sb, list.getParentEntityGroup());
    	for(int x=0; x<list.getEntityGroupCount(); x++){
    		EntityGroup eg = list.getEntityGroup(x);
    		outputGroupStructure(sb, eg);
    	}
    	return sb.toString();
    }
    
    /**
     * output EntityItem up and down links in the EntityGroup
     * @param sb
     * @param eg
     */
    public static void outputGroupStructure(StringBuffer sb, EntityGroup eg){
        for (int e=0; e<eg.getEntityItemCount(); e++) {
            EntityItem entityItem = eg.getEntityItem(e);
            sb.append(NEWLINE+" "+entityItem.getKey()+" ");
            sb.append(NEWLINE+"   downlinks: "+entityItem.getDownLinkCount());
            for (int d=0; d<entityItem.getDownLinkCount(); d++){
                sb.append(" "+entityItem.getDownLink(d).getKey());
            }
            sb.append(NEWLINE+"   uplinks: "+entityItem.getUpLinkCount());
            for (int d=0; d<entityItem.getUpLinkCount(); d++){
                sb.append(" "+entityItem.getUpLink(d).getKey());
            }
        }
    }
}
