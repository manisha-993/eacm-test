/*
 * Created on Jan 27, 2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package com.ibm.eannounce.eforms.editform;
import com.ibm.eannounce.eforms.editor.EditorInterface;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.RowSelectableTable;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public interface FormEditorInterface {
	/**
     * verifyNewFocus
     * @param _ei
     * @return
     * @author Anthony C. Liberto
     */
    boolean verifyNewFocus(EditorInterface _ei);
	/**
     * commit
     * @param _ei
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     * @return
     * @author Anthony C. Liberto
     */
    boolean commit(EditorInterface _ei) throws EANBusinessRuleException;
	/**
     * panelCollapsed
     * @param _b
     * @author Anthony C. Liberto
     */
    void panelCollapsed(boolean _b);
	/**
     * getTable
     * @return
     * @author Anthony C. Liberto
     */
    RowSelectableTable getTable();
}
