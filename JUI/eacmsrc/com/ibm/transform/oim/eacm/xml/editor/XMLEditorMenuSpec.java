// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import javax.swing.*;
import java.util.*;
import java.beans.*;


/*******************************************************************************
 * This is a utility class to associate names and actions.  It also allows
 * menu items to be arranged in a hierarchy.
 *
 * @author Wendy Stimpson
 * JRE 1.4.0 change:
 *    One of the new features that was introduced in Windows 2000 has been a display property which will
 * hide the keyboard mnemonic for a menu item or a dialog control until the Alt key is pressed. In other words,
 * all the underlined characters which represent an Alt key combination to invoke that menu or control will not
 * be drawn until the user indicates the intention to invoke a command with the keyboard rather than the mouse.
 * The result of this behavior is to provide an interface which is not visually cluttered with underlined
 * characters for people who choose to navigate the user interface with the mouse.
 *    The keyboard navigation hiding feature will be automatically enabled on Windows 2000 if the application
 * uses the Windows look and feel and the "Hide keyboard navigation indicators until I use the Alt key" property
 * is enabled in the Windows 2000 "Display Properties" control panel applet.
 *
 * Change History:
 */
 // $Log: XMLEditorMenuSpec.java,v $
 // Revision 1.1  2007/04/18 19:47:48  wendy
 // Reorganized JUI module
 //
 // Revision 1.3  2006/01/25 18:59:04  wendy
 // AHE copyright
 //
 // Revision 1.2  2005/10/12 12:48:57  wendy
 // Conform to new jtest configuration
 //
 // Revision 1.1.1.1  2005/09/09 20:39:19  tony
 // This is the initial load of OPICM
 //
 //
class XMLEditorMenuSpec
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.1 $";
    final static String ADD_SEPARATOR = "separator";
    final static int PLAIN = 0;
    final static int CHECKBOX = 1;
    final static int RADIOBUTTON = 2;

    private String name=null;
    private String actionName=null;
    private String actionCommand=null;
    private String accessibleDesc=null;
    private char mnemonic=' ';
    private Action action=null;
    void setAction(Action a) { action=a;}  // jtest req
    private String propertyActionName=null;
    private XMLEditorMenuSpec[] subMenus=null;
    private int menuItemType = PLAIN;
    private JMenuItem menuItem = null;  // needed to store menuitem created for this spec
                                    // it is reused, once by menubar and again by popup
    private KeyStroke keyStroke = null;
    private Icon icon = null;
    void setIcon(Icon i) { icon = i;}  // jtest req

    //private static ResourceBundle menuText = ResourceBundle.getBundle("xmleditor-menu-text");

    /*********************************************************************
    * Use a resource to get the text for the specified menu label
    * This will not be used now because it slows the load of the applet down too
    * much and there is no requirement for multiple languages on the
    * controls and messages
    */
    static String getMenuText(String name1)
    {
        String namex = name1;
    //  try
    //  {
    //      return menuText.getString(name);
    //  }
    //  catch(MissingResourceException mre)
    //  {
        return namex;
    //  }
    }

    /*********************************************************************
    * Constructors
    */
    XMLEditorMenuSpec(String name1, XMLEditorMenuSpec[] submenus, char mnemonic1, String propertyAction)
    {
        this.name = getMenuText(name1);
        this.mnemonic = mnemonic1;
        subMenus = submenus;
        propertyActionName = propertyAction;
    }
//  XMLEditorMenuSpec(String name, XMLEditorMenuSpec[] submenus, char mnemonic)
//  {
//      this.name = getMenuText(name);
//      this.mnemonic = mnemonic;
//      subMenus = submenus;
//  }
    // this is used to add a separator
    XMLEditorMenuSpec(String name1, String actionName1)
    {
        this.name = getMenuText(name1);
        this.actionName = actionName1;
    }
    // this is used for headings
    XMLEditorMenuSpec(String name1, String actionName1, char mnemonic1, String desc1)
    {
        this.name = getMenuText(name1);
        this.actionName = actionName1;
        this.mnemonic = mnemonic1;
        this.accessibleDesc = desc1;
    }
    // this is used for html like p, ol, ul
    XMLEditorMenuSpec(String name1, String actionName1, String actionCommand1,
            char mnemonic1, String desc1)
    {
        this.name = getMenuText(name1);
        this.actionName = actionName1;
        this.actionCommand = actionCommand1;
        this.mnemonic = mnemonic1;
        this.accessibleDesc = desc1;
    }
    // this is used for cut, copy, paste...
    XMLEditorMenuSpec(String name1, String actionName1, KeyStroke keyStroke1,
            char mnemonic1, String desc1)
    {
        this.name = getMenuText(name1);
        this.actionName = actionName1;
        this.keyStroke = keyStroke1;
        this.mnemonic = mnemonic1;
        this.accessibleDesc = desc1;
    }
    // this is used for styles like b, i, u
    XMLEditorMenuSpec(String name1, String actionName1, int type, String actionCommand1,
            char mnemonic1, String desc1)
    {
        this.name = getMenuText(name1);
        this.actionName = actionName1;
        this.menuItemType = type;
        this.actionCommand = actionCommand1;
        this.mnemonic = mnemonic1;
        this.accessibleDesc = desc1;
    }
    // not used now.. was for colors
//  XMLEditorMenuSpec(String name, String actionName, int type, Icon icon,
//          String actionCommand)
//  {
//      this.name = getMenuText(name);
//      this.actionName = actionName;
//      this.menuItemType = type;
//      this.icon = icon;
//      this.actionCommand = actionCommand;
//  }
    // not used
//  XMLEditorMenuSpec(String name, Action a)
//  {
//      this.name = getMenuText(name);
//      action = a;
//  }

    private boolean isSeparator() { return name.equals(ADD_SEPARATOR); }
    private boolean isSubMenu() { return subMenus !=null; }
    private boolean isAction() { return action !=null; }
    JMenuItem getMenuItem() { return menuItem; }
    void dereference() { menuItem = null; }  // other fields are reused (MenuSpec is statically defined)

    static JMenu buildMenu(String name2, XMLEditorMenuSpec[] XMLEditorMenuSpecs,
        Hashtable actions, char mnemonic2)
    {
        ButtonGroup grp = null;
        int itemCount;
        int count = XMLEditorMenuSpecs.length;
        int activeItems = count;  // v1.1
        JMenu menu = new JMenu(name2);
        if (mnemonic2!=' ') {
            menu.setMnemonic(mnemonic2);
        }

        for (int i=0; i<count; i++)
        {
            XMLEditorMenuSpec spec = XMLEditorMenuSpecs[i];
            if (spec.isSeparator()) {
                menu.addSeparator();
            }
            else if (spec.isSubMenu())
            {
                // if parent menu didn't have a mnemonic, don't use one for the submenu
                // mnemonics are meaning less on popups
                char subMnemonic = spec.mnemonic;
                if (mnemonic2==' '){
                    subMnemonic = ' ';
                }
                // build submenu
                spec.menuItem = buildMenu(spec.name, spec.subMenus, actions, subMnemonic);
                menu.add(spec.menuItem);
                if (spec.propertyActionName!=null)  // add a listener to handle disabling/enabling
                {
                    Action a = (Action)actions.get(spec.propertyActionName);
                    if (a!=null)
                    {
                        // only used for Headings now..
                        PropertyChangeListener actionPropertyChangeListener =
                            new ActionChangedListener(spec.menuItem,menu);
                        a.addPropertyChangeListener(actionPropertyChangeListener);
                    }
                }
            }
            else if (spec.isAction())
            {
                // its an action so add it
                spec.addMenuItemForAction(menu, spec.action, mnemonic2);
                if (!spec.action.isEnabled()){
                    activeItems--;  //v1.1
                }
            }
            else
            {
                // its an action name add if possible
                Action targetAction = (Action) actions.get(spec.actionName);
                if (targetAction != null)
                {
                    spec.addMenuItemForAction(menu, targetAction, mnemonic2);
                    if (!targetAction.isEnabled()){
                        activeItems--;  //v1.1
                    }
                }
                else
                {
                    spec.menuItem = menu.add(spec.name);
                    // action not known.. disable the menu item
                    spec.menuItem.setEnabled(false);
                    activeItems--;  //v1.1
                }
            }
        }
        if (activeItems==0) { // no actions for this menu v1.1
            menu.setEnabled(false);
        }

        // if menu has JRadioButtons in the set, the buttons are part of a
        // group
        itemCount = menu.getItemCount();
        for (int ii=0; ii<itemCount; ii++)
        {
            JMenuItem item = menu.getItem(ii);
            // if radio button, add to group
            if (item instanceof JRadioButtonMenuItem)
            {
                if (grp ==null) {
                    grp = new ButtonGroup();
                }
                grp.add(item);
            }
        }

        return menu;
    }

    private void addMenuItemForAction(JMenu menu, Action targetAction, char menuMnemonic)
    {
        switch(menuItemType)
        {
        case CHECKBOX:
            menuItem = createCheckBox(targetAction, menu);
            break;
        case RADIOBUTTON:
            menuItem = createRadioButton(targetAction,menu);
            break;
        default:
            // must do it this way to have action control
            // disabling/enabling menuitem and parent menu
//                  menuItem = menu.add(targetAction);
            menuItem = createPlain(targetAction,menu);
            if (keyStroke != null) {
                menuItem.setAccelerator(keyStroke);
            }
            break;
        }
        menuItem.setIcon(icon);
        menuItem.setText(name);
        if (actionCommand != null){
            menuItem.setActionCommand(actionCommand);
        }
        if (mnemonic!=' ' && menuMnemonic!=' '){ // mnemonic is meaningless in popup
            menuItem.setMnemonic(mnemonic);
        }

        if (accessibleDesc!=null){
            menuItem.getAccessibleContext().setAccessibleDescription(accessibleDesc);
        }
    }

    // allow checkboxes and radio buttons to react to enablement/disablement
    // of their corresponding actions. (same code as JMenu.add(Action))
    static JMenuItem createPlain(Action a, JMenu menu)
    {
        PropertyChangeListener actionPropertyChangeListener;
        JMenuItem mi = new JMenuItem((String)a.getValue(Action.NAME),
            (Icon)a.getValue(Action.SMALL_ICON));
        mi.setHorizontalTextPosition(JButton.RIGHT);
        mi.setVerticalTextPosition(JButton.CENTER);
        mi.setEnabled(a.isEnabled());
        mi.addActionListener(a);
        actionPropertyChangeListener = new ActionChangedListener(mi,menu);
        a.addPropertyChangeListener(actionPropertyChangeListener);
        menu.add(mi);
        return mi;
    }

    private JMenuItem createCheckBox(Action a, JMenu menu)
    {
        PropertyChangeListener actionPropertyChangeListener;
        JMenuItem mi = new JCheckBoxMenuItem((String)a.getValue(Action.NAME),
            (Icon)a.getValue(Action.SMALL_ICON));
        mi.setHorizontalTextPosition(JButton.RIGHT);
        mi.setVerticalTextPosition(JButton.CENTER);
        mi.setEnabled(a.isEnabled());
        mi.addActionListener(a);
        actionPropertyChangeListener = new ActionChangedListener(mi,menu);
        a.addPropertyChangeListener(actionPropertyChangeListener);
        menu.add(mi);
        return mi;
    }

    private JMenuItem createRadioButton(Action a, JMenu menu)
    {
        PropertyChangeListener actionPropertyChangeListener;
        JMenuItem mi = new JRadioButtonMenuItem((String)a.getValue(Action.NAME),
            (Icon)a.getValue(Action.SMALL_ICON));
        mi.setHorizontalTextPosition(JButton.RIGHT);
        mi.setVerticalTextPosition(JButton.CENTER);
        mi.setEnabled(a.isEnabled());
        mi.addActionListener(a);
        actionPropertyChangeListener = new ActionChangedListener(mi,menu);
        a.addPropertyChangeListener(actionPropertyChangeListener);
        menu.add(mi);
        return mi;
    }

    private static class ActionChangedListener implements PropertyChangeListener
    {
        private JMenuItem menuItem=null;
        private JMenu menu=null;

        ActionChangedListener(JMenuItem mi, JMenu m) {
            super();
            this.menuItem = mi;
            menu = m;
        }
        public void propertyChange(PropertyChangeEvent e) {
            String propertyName = e.getPropertyName();
            if (propertyName.equals(Action.NAME)) {
                String text = (String) e.getNewValue();
                menuItem.setText(text);
            } else if (propertyName.equals("enabled")) {
                Boolean enabledState = (Boolean) e.getNewValue();
                menuItem.setEnabled(enabledState.booleanValue());
                // don't change 'File', always leave it enabled
                if (menu.getText().equals("File")) {
                    //return;
                }else {
                    // make sure menu is enabled now
                    if (enabledState.booleanValue()){
                        menu.setEnabled(enabledState.booleanValue());
                    }
                    else // if all other menuitems are disabled, disable the menu
                    {
                        int disabledCnt=0;
                        for (int ii=0; ii<menu.getItemCount(); ii++)
                        {
                            JMenuItem item = menu.getItem(ii);
                            if (item==null || !item.isEnabled()){
                                disabledCnt++;
                            }
                        }
                        if (disabledCnt==menu.getItemCount()) {
                            menu.setEnabled(enabledState.booleanValue());
                        }
                    }
                }
            } else if (propertyName.equals(Action.SMALL_ICON)) {
                Icon icon2 = (Icon) e.getNewValue();
                menuItem.setIcon(icon2);
                menuItem.invalidate();
                menuItem.repaint();
            }
        }
    }
}
