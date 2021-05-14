// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import java.util.*;

/******************************************************************************
* This is used to create GWA compliant xml for rendering in the browser.
* It also is used to create xml for rendering in the editor to display the result of
* tags set to publish=webonly or printonly.
*
* If we are rendering for a WebOnly view, all 'publish=printonly' tags will be changed
* to class=lshidden and a style sheet will highlight those items.  'publish=webonly' tags
* will be removed.
* If we are rendering for a PrintOnly view, all 'publish=webonly' tags will be changed
* to class=lshidden and a style sheet will highlight those items.  'publish=printonly' tags
* will be removed.
* If we are rendering for a normal view, all 'publish=xxx' tags will be removed.
* Download uses attribute as saved.
*
* GWA compliant XML does not support user defined attributes.
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLGenerator.java,v $
// Revision 1.1  2007/04/18 19:47:48  wendy
// Reorganized JUI module
//
// Revision 1.3  2006/01/25 18:59:04  wendy
// AHE copyright
//
// Revision 1.2  2005/10/12 12:48:58  wendy
// Conform to new jtest configuration
//
// Revision 1.1.1.1  2005/09/09 20:39:20  tony
// This is the initial load of OPICM
//
//
class XMLGenerator implements XMLEditorGlobals
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.1 $";

    // generate html for rendering in reports with publish text highlighted
    // 'publish' is removed because it is not GWA compliant
    static String generateXML(String text, int viewType)
    {
        StringBuffer outputXml = new StringBuffer(text);

        // check the type of view wanted
        switch(viewType)
        {
        case NORMAL_VIEW_TYPE:  // remove all publish=xxx tag attributes
            replaceText(outputXml," publish=\"webonly\"","");
            replaceText(outputXml," publish=\"printonly\"","");
            break;
        case WEBONLY_VIEW_TYPE:   // convert printonly to a formatted style
            replaceText(outputXml," publish=\"webonly\"","");
            replaceText(outputXml," publish=\"printonly\""," class=\"lshidden\"");
            break;
        case PRINTONLY_VIEW_TYPE:   // convert webonly to a formatted style
            replaceText(outputXml," publish=\"printonly\"","");
            replaceText(outputXml," publish=\"webonly\""," class=\"lshidden\"");
            break;
        default:
            break;
        }
        return outputXml.toString();
    }

    static void replaceText(StringBuffer outputXml,String oldTag, String newTag)
    {
        int id = 0;
        while(id != -1)
        {
            id = outputXml.toString().indexOf(oldTag,id);
            if (id != -1) {
                outputXml.replace(id, id+oldTag.length(),newTag);
            }
        }
    }

    // replace tags that are not nested, <p> will be replaced, <li>..<p> will not
    // this is needed when changing from an ul to an ol with nested p
    // also used when selected p are converted to ul or ol
    static void replaceTopLevelTag(StringBuffer outputXml,String oldTag, String newTag)
    {
        int id = 0;
        while(id != -1)
        {
            id = outputXml.toString().indexOf("<"+oldTag,id);
            if (id != -1)
            {
                int newId = outputXml.toString().lastIndexOf("<"+newTag,id);
                int newEndId = outputXml.toString().indexOf("</"+newTag,newId);
                if (newId!=-1&& id> newId && id<newEndId)  // oldtag is nested in new tag
                {
                    id=oldTag.length()+1+id;
                    continue;
                }
                // replace start tag
                outputXml.replace(id, id+oldTag.length()+1,"<"+newTag);
                // replace end tag
                id = outputXml.toString().indexOf("</"+oldTag,id);
                outputXml.replace(id, id+oldTag.length()+2,"</"+newTag);
            }
        }
    }

    static String[] getStructureTags(String theData)
    {
        Vector tagsVct = new Vector();
        String array[];

        StringTokenizer st = new StringTokenizer(theData,"<");
        while(st.hasMoreTokens())
        {
            String token = st.nextToken();
            StringTokenizer stend = new StringTokenizer(token,">");
            if (stend.hasMoreTokens())
            {
                String tag = stend.nextToken();
//System.err.println("found tag "+tag);
                /* jtest flags indexOf()
                Disallows using 'String.indexOf ()' with 'String.substring ()'.
                Note: If the algorithm implemented by the method is not String parsing, then
                StringTokenizer cannot be used and this rule's error message should be ignored.
                */
                int blank = tag.indexOf(" ");// drop any attributes
                if (blank!=-1){
//                    tag = tag.substring(0,blank);
                    tag = getSubString(tag,0,blank);
                }

                if (!tag.startsWith("/") &&
                    !tag.equals("li"))  // only get top level structure
                {
                    tagsVct.addElement(tag);
                }
            }
        }
//System.err.println("tagsvct: "+tagsVct);

        array= new String[tagsVct.size()];
        tagsVct.copyInto(array);

/*    chg'd to use StringTokenizer where possible
        int id = 0;
        while(id != -1)
        {
            id = theData.indexOf("<");
            if (id != -1)
            {
                int endId = theData.indexOf(">",id);
                String tag = theData.substring(id+1,endId);
System.err.println("found tag "+tag);
                int blank = tag.indexOf(" ");
                if (blank!=-1){
                    tag = tag.substring(0,blank);
                }

                if (tag.indexOf("/")==-1 &&
                    !tag.equals("li"))  // only get top level structure
                {
                    tagsVct.addElement(tag);
                }
                theData = theData.substring(endId+1);
            }
        }

        array= new String[tagsVct.size()];
        tagsVct.copyInto(array);
System.err.println("tagsvct: "+tagsVct);*/
        return array;
    }

    static String removeTag(String theData, String tag)
    {
        StringBuffer sb = new StringBuffer(theData);
        // remove start tag
        int startTagId = sb.toString().indexOf("<"+tag);
        while(startTagId!=-1)
        {
            int endTagId = sb.toString().indexOf(">",startTagId);
            sb.delete(startTagId,endTagId+1);
            startTagId = sb.toString().indexOf("<"+tag);
        }

        // remove end tag
        startTagId = sb.toString().indexOf("</"+tag);
        while(startTagId!=-1)
        {
            int endTagId = sb.toString().indexOf(">", startTagId);
            sb.delete(startTagId,endTagId+1);
            startTagId = sb.toString().indexOf("</"+tag);
        }

        return sb.toString();
    }

    // replace <br /> with <br>
    static String replaceEmptyXML(String data)
    {
        StringBuffer dataSb = new StringBuffer(data);
        replaceText(dataSb,"<br />","<br>");
        replaceText(dataSb,"<br/>","<br>");
        //replaceText(dataSb,"<hr />","<hr>");
        return dataSb.toString();
    }

// v11 <pre> parser flags <---US as error, replace '<-' inside <pre> with '&lt;-'
    static String convertPRE(String text)
    {
        String txt=text;
        if (text.indexOf("<pre")!=-1)
        {
            StringBuffer sb = new StringBuffer(text);
            // remove <- inside <pre>
            int startPreId = sb.toString().indexOf("<pre");
            while(startPreId!=-1)
            {
                int endPreId = sb.toString().indexOf("</pre>",startPreId);
                //String preText = sb.substring(startPreId+5,endPreId);
                int tagId = sb.toString().indexOf("<-",startPreId+5);
                while(tagId!=-1 && tagId<endPreId)
                {
                    sb.replace(tagId, tagId+1,"&lt;");
                    tagId = sb.toString().indexOf("<-",tagId+4);
                }

                startPreId = sb.toString().indexOf("<pre", endPreId);
            }

            txt= sb.toString();
        }

        return txt;
    }

    // generate html for viewing in the editor
    /*
    The idea is that there are two allowed values for publish:  webonly and printonly
        then you need three views
        web
        print
        normal

        I can have three list items
        1) no tags
        2) webonly
        3) printonly
        then View shows
        Web = 1 & 2
        Print = 1 & 3
        Normal = 1 & 2 & 3
    */
    static String generateViewXML(String text, int viewType)
    {
        String txt=text;
        // check the type of view wanted
        switch(viewType)
        {
        case WEBONLY_VIEW_TYPE:   // remove any printonly
            txt= removePublishText(text," publish=\"printonly\"");
            break;
        case PRINTONLY_VIEW_TYPE:   // remove any webonly
            txt= removePublishText(text," publish=\"webonly\"");
            break;
        case TAG_VIEW_TYPE:  // restore <br />, <br /> has already been converted to <br>
            {
                StringBuffer dataSb = new StringBuffer(text);
                replaceText(dataSb,"<br>","<br />");
                //replaceText(dataSb,"<hr>","<hr />");
                txt= dataSb.toString();
            }
            break;
        case NORMAL_VIEW_TYPE:  // no changes to make, <br /> has already been converted to <br>
        default:
            break;
        }
        return txt;
    }

    private static String removePublishText(String text, String pubAttr)
    {
        StringBuffer sb = new StringBuffer(text);
        Stack tagStk = new Stack();
        int curLen;

        // find all pubAttr content
        // remove the tag and all of the text it contains
        int id = 0;
        while(id != -1)
        {
            String curText = sb.toString();
            /* jtest flags indexOf() but it is ok can't use StringTokenizer because it uses each
            character as a separate delimiter, need to find the complete String
            Disallows using 'String.indexOf ()' with 'String.substring ()'.
            Note: If the algorithm implemented by the method is not String parsing, then
            StringTokenizer cannot be used and this rule's error message should be ignored.
            */
            // get index of publish attribute
            id = curText.indexOf(pubAttr);//,id); FB21322 if first elem completely removed id was indexed past it
            if (id != -1)
            {
                int closeTag;
                // get text preceding attribute it will have the tag
//                String tmp = curText.substring(0,id);
                String tmp = getSubString(curText,0,id);
                // get preceding '<'
                int openTag = tmp.lastIndexOf("<");
//                String tag = curText.substring(openTag+1,id);
                String tag = getSubString(curText,openTag+1,id);
                int blank = tag.indexOf(" ");
                if (blank!=-1) {
//                    tag = tag.substring(0,blank);
                    tag = getSubString(tag,0,blank);
                }
                // check for nested structure
                tagStk.push(tag);

                closeTag = openTag;//curText.indexOf("</"+tag,openTag);
                while(!tagStk.isEmpty())
                {
                    closeTag = curText.indexOf("<",closeTag+1);
                    if (curText.charAt(closeTag+1)=='/') { // end tag, pop the stack
                        tagStk.pop();
                    }
                    else
                    {
                        int endTag = curText.indexOf(">",closeTag);
                        // get tag and push on stack
//                        String tag2 = curText.substring(closeTag+1,endTag);
                        String tag2 = getSubString(curText,closeTag+1,endTag);
                        blank = tag2.indexOf(" ");
                        if (blank!=-1){
//                            tag2 = tag2.substring(0,blank);
                            tag2 = getSubString(tag2,0,blank);
                        }
                        if (!tag2.equals("br")){
                            tagStk.push(tag2);
                        }
                    }
                }

                // remove the affected tag and content
                sb.delete(openTag,closeTag+("</"+tag+">").length());
            }
        }
        // if parent enclosing tag does not have any content, remove the parent too..
        // for example, if all <li> are removed from <ol>, remove the <ol> too.
        curLen = sb.length();
        while(true)
        {
            removeTagsWithOutContent(sb);
            if (curLen==sb.length()) { // nothing changed
                break;
            }
            curLen=sb.length();
        }

        if (sb.length()==0) {
            sb.append("<p></p>"); // force empty content
        }
        return sb.toString();
    }

    // if parent enclosing tag does not have any content, remove the parent too..
    // for example, if all <li> are removed from <ol>, remove the <ol> too.
    private static void removeTagsWithOutContent(StringBuffer sb)
    {
        int id = 0;
        while(id != -1)
        {
            String curText = sb.toString();
            /* jtest flags indexOf() but it is ok can't use StringTokenizer because it uses each
            character as a separate delimiter, need to find the complete String
            Disallows using 'String.indexOf ()' with 'String.substring ()'.
            Note: If the algorithm implemented by the method is not String parsing, then
            StringTokenizer cannot be used and this rule's error message should be ignored.
            */
            id = curText.indexOf("></",id);
            if (id != -1)
            {
//                String tmp = curText.substring(0,id);
                String tmp = getSubString(curText,0,id);
                // get preceding '<'
                int openTag = tmp.lastIndexOf("<");
//                String tag = curText.substring(openTag+1,id);
                String tag = getSubString(curText,openTag+1,id);
                int blank = tag.indexOf(" ");
                int closeTag;
                if (blank!=-1) {
//                    tag = tag.substring(0,blank);
                    tag = getSubString(tag,0,blank);
                }
                if (tag.startsWith("/"))  // it is an end tag
                {
                    id++;
                    continue;
                }
                closeTag = curText.indexOf("</"+tag,openTag);
                if (closeTag==id+1) {
                    sb.delete(openTag,closeTag+("</"+tag+">").length());
                }
                id++;
            }
        }
    }
    // avoid jtest error msgs when using indexof and substring in same method
    static String getSubString(String data, int startid, int endid)
    {
       return data.substring(startid,endid);
    }
    static String getSubString(String data, int startid)
    {
       return data.substring(startid);
    }
    static String getSubString(StringBuffer data, int startid, int endid)
    {
       return data.substring(startid,endid);
    }
}
