// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import java.awt.event.*;

/******************************************************************************
* This is used to toggle th and td table tags
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLToggleTHAction.java,v $
// Revision 1.2  2008/01/30 16:27:08  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/04/18 19:47:48  wendy
// Reorganized JUI module
//
// Revision 1.4  2006/01/25 18:59:05  wendy
// AHE copyright
//
// Revision 1.3  2006/01/10 15:00:45  wendy
// Changes for table accessibility DQA requirements
//
// Revision 1.2  2005/10/12 12:48:58  wendy
// Conform to new jtest configuration
//
// Revision 1.1.1.1  2005/09/09 20:39:21  tony
// This is the initial load of OPICM
//
class XMLToggleTHAction extends HTMLEditorKit.StyledTextAction implements XMLEditorGlobals
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.2 $";

    XMLToggleTHAction() {
        super(TOGGLE_TH_ACTION);
    }

    void toggleHeader(XMLEditorPane editor)
    {
        XMLDocument doc = (XMLDocument)editor.getDocument();
        javax.swing.text.Element table = doc.getParagraphElement(editor.getSelectionStart());
        SimpleAttributeSet sas, tableSas;
        javax.swing.text.Element tr;
        while(table!=null && !table.getName().equals(HTML.Tag.TABLE.toString()))
        {
            table = table.getParentElement();
        }

        sas = new SimpleAttributeSet();
        tableSas = new SimpleAttributeSet();
        tr = table.getElement(0);
        if (tr.getElement(0).getName().equals(HTML.Tag.TH.toString()))
        {
            // change th to td
            sas.addAttribute(StyleConstants.NameAttribute,HTML.Tag.TD);
            // this must now be a layout table
            tableSas.addAttribute(TABLE_SUMMARY,TABLE_LAYOUT);
        }
        else // change td to th
        {
            String summary = getSummary(editor);
            sas.addAttribute(StyleConstants.NameAttribute,HTML.Tag.TH);
            // this cannot be a layout table now
            tableSas.addAttribute(TABLE_SUMMARY,summary);
        }

        // group changes for single undo
        editor.getUndoEditMgr().setStartReplaceEdit("toggle header");

        doc.setParagraphChildAttributes(tr, sas,false);
        doc.setParagraphAttributes(table, tableSas,false);

        editor.getUndoEditMgr().setEndReplaceEdit();  // undo as one action
    }

    /*****
    * AHE accessibility requires a table summary attribute.  th.id and td.headers will be generated
    * when the table is written out.  If a table has th, then it cannot be a layout table and requires
    * th.id and td.headers.  If it does not have th, it must be a layout table.. that is summary='layout'
    * and will not have th.id and td.headers
    */
    private String getSummary(XMLEditorPane editor)
    {
        String summsgs[] = {
            "A table summary is required when headers are used.",
            "The summary is used by assistive technology and",
            "will not be visible.",
            "Please specify a table summary:"
            };

        String summary;

        java.awt.Toolkit.getDefaultToolkit().beep();

        summary = JOptionPane.showInputDialog(editor,summsgs,"Table summary required",JOptionPane.PLAIN_MESSAGE);
        if (summary==null || summary.length()==0) {
            summary="table 0";  // allow XMLWriter to generate this summary later
        }

        return summary;
    }

    /**
     * The operation to perform when this action is triggered.
     *
     * @param e the action event
     */
    public void actionPerformed(ActionEvent e)
    {
        XMLEditorPane editor;
        JTextComponent target = getTextComponent(e);
        if ((target == null) || (!target.isEditable())
            || (!(target instanceof XMLEditorPane))){
            //return;
        }else{
            editor = (XMLEditorPane)target;

            toggleHeader(editor);

            editor.setFocusInEditor();  // applet does not return focus to the editor

            // make sure the actions reflect the latest changes
            editor.ignoreCaret(false);
        }
    }
}



