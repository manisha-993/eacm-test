/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: EditorInterface.java,v $
 * Revision 1.1  2007/04/18 19:44:34  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:57  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:03  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/01 20:48:32  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 19:15:16  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2003/11/18 20:54:04  tony
 * added formEditorInterface to simplify formEditor modification.
 *
 * Revision 1.4  2003/08/21 15:57:34  tony
 * 51869
 *
 * Revision 1.3  2003/07/09 20:47:35  tony
 * 51422 -- blobEditor popup on double click
 *
 * Revision 1.2  2003/05/06 16:38:21  tony
 * acl_20030506 -- enhanced focus logic to prevent error
 * messages from firing when refocusing the component.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.22  2002/11/07 16:58:27  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.editor;
import javax.swing.table.TableCellEditor;
import java.awt.Component;									//acl_20030506
import java.awt.event.*;
import java.util.EventObject;								//51422
import com.ibm.eannounce.eforms.editform.FormEditorInterface;			//acl_20030506
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANMetaAttribute;						//22098

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public interface EditorInterface extends TableCellEditor {
	/**
     * setAttribute
     * @param _att
     * @author Anthony C. Liberto
     */
    void setAttribute(EANAttribute _att);
	/**
     * getAttribute
     * @return
     * @author Anthony C. Liberto
     */
    EANAttribute getAttribute();
	/**
     * getMetaAttribute
     * @return
     * @author Anthony C. Liberto
     */
    EANMetaAttribute getMetaAttribute();				//22098
	/**
     * refreshDisplay
     * @param _att
     * @author Anthony C. Liberto
     */
    void refreshDisplay(EANAttribute _att);
	/**
     * canLeave
     * @return
     * @author Anthony C. Liberto
     */
    boolean canLeave();
	/**
     * setDisplayOnly
     * @param _b
     * @author Anthony C. Liberto
     */
    void setDisplayOnly(boolean _b);
	/**
     * isDisplayOnly
     * @return
     * @author Anthony C. Liberto
     */
    boolean isDisplayOnly();
//51422	public abstract void prepareToEdit();
//51869	public abstract void prepareToEdit(EventObject _eo);			//51422
	/**
     * prepareToEdit
     * @param _eo
     * @param _comp
     * @author Anthony C. Liberto
     */
    void prepareToEdit(EventObject _eo, Component _comp);	//51869
	/**
     * cancel
     * @author Anthony C. Liberto
     */
    void cancel();
	/**
     * deactivate
     * @author Anthony C. Liberto
     */
    void deactivate();
	/**
     * setEffectivity
     * @param _from
     * @param _to
     * @author Anthony C. Liberto
     */
    void setEffectivity(String _from, String _to);
	/**
     * getKey
     * @return
     * @author Anthony C. Liberto
     */
    String getKey();
	/**
     * setKey
     * @param _key
     * @author Anthony C. Liberto
     */
    void setKey(String _key);
	/**
     * dereference
     * @author Anthony C. Liberto
     */
    void dereference();
	/**
     * paste
     * @author Anthony C. Liberto
     */
    void paste();
	/**
     * cut
     * @author Anthony C. Liberto
     */
    void cut();
	/**
     * copy
     * @author Anthony C. Liberto
     */
    void copy();
	/**
     * isRequired
     * @return
     * @author Anthony C. Liberto
     */
    boolean isRequired();
	/**
     * setVisible
     * @param _b
     * @author Anthony C. Liberto
     */
    void setVisible(boolean _b);
	/**
     * isVisible
     * @return
     * @author Anthony C. Liberto
     */
    boolean isVisible();

	/**
     * find
     * @param _strFind
     * @param _bCase
     * @return
     * @author Anthony C. Liberto
     */
    boolean find(String _strFind, boolean _bCase);
	/**
     * replace
     * @param _old
     * @param _new
     * @param _bCase
     * @return
     * @author Anthony C. Liberto
     */
    boolean replace(String _old, String _new, boolean _bCase);
	/**
     * isFound
     * @return
     * @author Anthony C. Liberto
     */
    boolean isFound();
	/**
     * resetFound
     * @author Anthony C. Liberto
     */
    void resetFound();
	/**
     * isReplaceable
     * @return
     * @author Anthony C. Liberto
     */
    boolean isReplaceable();							//22632
	/**
     * getInformation
     * @return
     * @author Anthony C. Liberto
     */
    String getInformation();

	/**
     * addKeyListener
     * @param _kl
     * @author Anthony C. Liberto
     */
    void addKeyListener(KeyListener _kl);
	/**
     * removeKeyListener
     * @param _kl
     * @author Anthony C. Liberto
     */
    void removeKeyListener(KeyListener _kl);
	/**
     * addFocusListener
     * @param _fl
     * @author Anthony C. Liberto
     */
    void addFocusListener(FocusListener _fl);
	/**
     * removeFocusListener
     * @param _fl
     * @author Anthony C. Liberto
     */
    void removeFocusListener(FocusListener _fl);
	/**
     * addMouseListener
     * @param _fl
     * @author Anthony C. Liberto
     */
    void addMouseListener(MouseListener _fl);			//21898
	/**
     * removeMouseListener
     * @param _fl
     * @author Anthony C. Liberto
     */
    void removeMouseListener(MouseListener _fl);		//21898

	/**
     * removeEditor
     * @return
     * @author Anthony C. Liberto
     */
    boolean removeEditor();

//acl_20030506	public abstract void setTable(RowSelectableTable _table);
//acl_20030506	public abstract RowSelectableTable getTable();

	/**
     * setDisplay
     * @param _s
     * @author Anthony C. Liberto
     */
    void setDisplay(String _s);

	/**
     * setText
     * @param _s
     * @author Anthony C. Liberto
     */
    void setText(String _s);
	/**
     * getText
     * @return
     * @author Anthony C. Liberto
     */
    String getText();
	/**
     * setEditable
     * @param _b
     * @author Anthony C. Liberto
     */
    void setEditable(boolean _b);
	/**
     * isEditable
     * @return
     * @author Anthony C. Liberto
     */
    boolean isEditable();
	/**
     * requestFocus
     * @author Anthony C. Liberto
     */
    void requestFocus();

	/**
     * setOpaque
     * @param _b
     * @author Anthony C. Liberto
     */
    void setOpaque(boolean _b);
	/**
     * hasChanged
     * @return
     * @author Anthony C. Liberto
     */
    boolean hasChanged();								//acl_20021023

	/**
     * equals
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    boolean equals(Component _c);						//acl_20030506
	/**
     * getEditForm
     * @return
     * @author Anthony C. Liberto
     */
    FormEditorInterface getEditForm();								//acl_20030506
	/**
     * canReceiveFocus
     * @return
     * @author Anthony C. Liberto
     */
    boolean canReceiveFocus();							//acl_20030506
	/**
     * grabFocus
     * @author Anthony C. Liberto
     */
    void grabFocus();									//acl_20030506
	/**
     * requestFocus
     * @param _temp
     * @return
     * @author Anthony C. Liberto
     */
    boolean requestFocus(boolean _temp);				//acl_20030506
}
