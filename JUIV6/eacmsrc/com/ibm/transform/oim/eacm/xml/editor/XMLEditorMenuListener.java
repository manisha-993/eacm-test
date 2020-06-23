// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.text.html.*;

/*******************************************************************************
 * This class is used for updating menu items like Font styles Bold, Italic, etc.
 * It will update the state based on the location of the caret.
 *
 * @author Wendy Stimpson
 *
 * Change History:
 */
 // $Log: XMLEditorMenuListener.java,v $
 // Revision 1.1  2012/09/27 19:39:19  wendy
 // Initial code
 //
 // Revision 1.1  2007/04/18 19:47:48  wendy
 // Reorganized JUI module
 //
 // Revision 1.2  2006/01/25 18:59:04  wendy
 // AHE copyright
 //
 // Revision 1.1.1.1  2005/09/09 20:39:19  tony
 // This is the initial load of OPICM
 //
 //
class XMLEditorMenuListener implements MenuListener, XMLEditorGlobals
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs revision  */
    public static final String VERSION = "$Revision: 1.1 $";
    private XMLEditorPane editor;

    XMLEditorMenuListener(XMLEditorPane pane) {
        editor = pane;
    }

    void dereference()
    {
        editor = null;
    }

    //MenuListener interface
    /*************************
    * deselect
    * @param e
    */
    public void menuDeselected(MenuEvent e) {}
    /*************************
    * cancel
    * @param e
    */
    public void menuCanceled(MenuEvent e) {}
    /*************************
    * select
    * @param e
    */
    public void menuSelected(MenuEvent e)
    {
        JMenu menu = (JMenu)e.getSource();

        // find settings and try to update menu bar
        StyledEditorKit kit = (StyledEditorKit)editor.getEditorKit();

        // recurse through menu hierarchy
        setState(menu,kit.getInputAttributes());
    }

    /*********************************************************************
    * Set the state of the menu item, like if the cursor is in bold text,
    * Bold will be checked.
    *
    * @param menu   JMenu
    */
    private void setState(JMenu menu, AttributeSet as)
    {
        int itemCount = menu.getItemCount();
        for (int ii=0; ii<itemCount; ii++)
        {
            JMenuItem item = menu.getItem(ii);
            String name;
            if (item instanceof JMenu)
            {
                setState((JMenu)item,as);
                continue;
            }
            // bypass any separators
            if (item ==null) {
                continue;
            }

            name = item.getText().trim();
            // checkboxes are used for styles, like bold, italic...
            if (item instanceof JCheckBoxMenuItem)
            {
                JCheckBoxMenuItem cbitem = (JCheckBoxMenuItem)item;
                // bold is used for <strong>
                if (name.equals(XMLEditorMenuSpec.getMenuText(B_STYLE)))
                {
                    Object o = as.getAttribute(HTML.Tag.STRONG); // strong not part of set
                    cbitem.setState(StyleConstants.isBold(as)&&o==null);
                }
                else if (name.equals(XMLEditorMenuSpec.getMenuText(STRONG_STYLE)))
                {
                    Object o = as.getAttribute(HTML.Tag.STRONG); // strong is part of set
                    cbitem.setState(StyleConstants.isBold(as)&& o!=null);
                }
                else if (name.equals(XMLEditorMenuSpec.getMenuText(S_STYLE))) {
                    cbitem.setState(StyleConstants.isStrikeThrough(as));}
                else if (name.equals(XMLEditorMenuSpec.getMenuText(U_STYLE))) {
                    cbitem.setState(StyleConstants.isUnderline(as));}
                else if (name.equals(XMLEditorMenuSpec.getMenuText(I_STYLE)))
                {
                    Object o = as.getAttribute(HTML.Tag.EM); // em is not part of set
                    cbitem.setState(StyleConstants.isItalic(as)&& o==null);
                }
                else if (name.equals(XMLEditorMenuSpec.getMenuText(EM_STYLE)))
                {
                    Object o = as.getAttribute(HTML.Tag.EM); // em is part of set
                    cbitem.setState(StyleConstants.isItalic(as)&& o!=null);
                }

                continue;
            }
            if (name.toLowerCase().indexOf("border")!=-1)
            {
                if (editor.tableHasBorder()) {
                    item.setText("Remove Border");}
                else{
                    item.setText("Add Border");}
            }
            if (name.toLowerCase().indexOf("header")!=-1)
            {
                if (editor.tableHasHeader()){
                    item.setText("Turn Header off");}
                else{
                    item.setText("Turn Header on");}
            }

        } // end menuitem loop
    }
}
