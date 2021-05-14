// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eannounce.eforms.editor.simple;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.text.*;
import COM.ibm.eannounce.objects.*;

/** RQ110306297
 * This is needed to support max length when a user is creating flags
 *
 * $Log: SimpleMaxTextEditor.java,v $
 * Revision 1.3  2008/01/30 16:27:07  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.2  2007/06/05 20:10:52  wendy
 * Validate remove chars too
 *
 * Revision 1.1  2007/06/04 18:41:28  wendy
 * RQ110306297 support max length rule on long description for flags
 *
 */
public class SimpleMaxTextEditor extends SimpleTextEditor {
	private static final long serialVersionUID = 1L;
	private int maxLen=0;
    private boolean byPassValidation = false;
    private EANMetaAttribute metaAttr = null;

    /**
     * simpleTextEditor with a maximum length
     */
    public SimpleMaxTextEditor() {
        super();
    }

    /**
     * set max length
     * @param len int with a maximum length
     */
    public void setMaxLen(int len){
        maxLen = len;
    }

    /**
     * set attribute code, used for error msgs
     * @param s EANMetaAttribute
     */
    public void setMetaAttr(EANMetaAttribute s){
        metaAttr = s;
    }
    /**
     * called by jre, max len validation
     *
     * @return Document
     */
    protected Document createDefaultModel() {
        return new MaxDoc();
    }

    private class MaxDoc extends PlainDocument {
    	private static final long serialVersionUID = 1L;
    	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) {
                return;
            }
            if (byPassValidation) {
                super.insertString(offs, str, a);
            } else if (isEditable() && checkLength(SimpleMaxTextEditor.this.getText()+str)) {
                super.insertString(offs, str, a);
            }
            repaint();
        }
        public void remove(int offs, int len) throws BadLocationException {
            if (!isEditable() && !byPassValidation) {
                return;
            }
            super.remove(offs, len);
            if (len>0){ // something was removed
            	checkLength(SimpleMaxTextEditor.this.getText());
			}
            repaint();
        }
    }

    private boolean checkLength(String str){
        boolean ok = true;
        if (maxLen>0 && str.length()>maxLen){
            ok=false;
            String msg = "";
            if (metaAttr!=null){
				msg = "Attribute " + metaAttr.getAttributeCode() + " failed the following rules..." + RETURN;
            }
            msg = msg + "Maximum Length of " + maxLen+ " Exceeded";
            eaccess().setMessage(msg);
            eaccess().showError(this);
        }

        return ok;
    }

    /**
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     * @author Anthony C. Liberto
     */
    public Component getTableCellEditorComponent(JTable _tbl, Object _o, boolean _selected, int _row, int _col) {
		byPassValidation = true;
        setText(_o.toString());
        byPassValidation = false;
        return this;
    }
}
