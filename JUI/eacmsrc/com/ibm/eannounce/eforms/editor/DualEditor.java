/**
 * Copyright (c) 2005-2006 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 3.0b 2005/06/06
 * @author Anthony C. Liberto
 *
 * $Log: DualEditor.java,v $
 * Revision 1.2  2008/01/30 16:27:05  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:34  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:13  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:57  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2005/09/08 17:59:03  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.2  2005/06/08 14:48:36  tony
 * refined logic
 *
 * Revision 1.1  2005/06/06 18:31:06  tony
 * mult-attribute edit
 *
 */
package com.ibm.eannounce.eforms.editor;
import com.elogin.*;
import com.ibm.eannounce.eforms.editform.FormEditorInterface;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import COM.ibm.eannounce.objects.*;

/**
 * This component should allow for multiple editors to display when editing
 * a specific type of attribute.
 *
 * @author Anthony C. Liberto
 */
public class DualEditor extends EPanel implements EditorInterface, TableCellEditor {
	private static final long serialVersionUID = 1L;
	private boolean displayOnly = false;
    private TableCellEditor[] editors = null;
    private EditorInterface curEdit = null;
    private String key = null;
    private EANAttribute att = null;
    private boolean bEditable = false;

    /**
     * DualEditor
     *
     * @author Anthony C. Liberto
     * @param _lm
     */
    public DualEditor(LayoutManager _lm) {
        super(_lm);
        setUseDefined(true);
        setAutoscrolls(true);
        return;
    }

	/**
     * setEditors
     *
     * @author Anthony C. Liberto
     * @param _editors
     */
    public void setEditors(TableCellEditor[] _editors) {
		if (editors != null) {
			removeAll();
		}
		editors = _editors;
		buildEditor(editors);
		return;
	}

	/**
     * getEditors
     *
     * @author Anthony C. Liberto
     * @return
     */
    public TableCellEditor[] getEditors() {
		return editors;
	}

	/**
     * getEditors
     *
     * @author Anthony C. Liberto
     * @param _i
     * @return
     */
    public TableCellEditor getEditors(int _i) {
		if (_i >= 0 && editors != null && _i < editors.length) {
			return editors[_i];
		}
		return null;
	}

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        if (editors != null) {
			int ii = editors.length;
			for (int i=0;i<ii;++i) {
				if (editors[i] instanceof EditorInterface) {
					((EditorInterface)editors[i]).dereference();
				}
				editors[i] = null;
			}
			editors = null;
		}
        removeAll();
        removeNotify();
        return;
    }

    /**
     * @see javax.swing.CellEditor#addCellEditorListener(javax.swing.event.CellEditorListener)
     * @author Anthony C. Liberto
     */
    public void addCellEditorListener(CellEditorListener c) {
        listenerList.add(CellEditorListener.class, c);
        return;
    }

    /**
     * @see javax.swing.CellEditor#cancelCellEditing()
     * @author Anthony C. Liberto
     */
    public void cancelCellEditing() {
        if (editors != null) {
			int ii = editors.length;
			for (int i=0;i<ii;++i) {
				editors[i].cancelCellEditing();
			}
		}
        return;
    }

    /**
     * @see javax.swing.CellEditor#isCellEditable(java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean isCellEditable(EventObject e) {
        return isEditable();
    }

    /**
     * @see javax.swing.CellEditor#removeCellEditorListener(javax.swing.event.CellEditorListener)
     * @author Anthony C. Liberto
     */
    public void removeCellEditorListener(CellEditorListener c) {
        listenerList.remove(CellEditorListener.class, c);
    }

    /**
     * @see javax.swing.CellEditor#shouldSelectCell(java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean shouldSelectCell(EventObject e) {
        return true;
    }

    /**
     * @see javax.swing.CellEditor#stopCellEditing()
     * @author Anthony C. Liberto
     */
    public boolean stopCellEditing() {
		boolean out = true;
		if (editors != null) {
			int ii = editors.length;
			for (int i=0;i<ii && out;++i) {
				if (!editors[i].stopCellEditing()) {
					out = false;
				}
			}
		}
        return out;
    }

    /**
     * getMetaAttribute
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EANMetaAttribute getMetaAttribute() {
        EANAttribute tmpAtt = getAttribute();
        if (tmpAtt != null) {
            return tmpAtt.getMetaAttribute();
        }
        return null;
    }

    /**
     * getKey
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getKey() {
        return key;
    }

    /**
     * setKey
     *
     * @param _key
     * @author Anthony C. Liberto
     */
    public void setKey(String _key) {
        key = new String(_key);
    }

    /**
     * setDisplay
     *
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setDisplay(String _s) {
        return;
    }

    /**
     * canLeave
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canLeave() {
        return true;
    }

    /**
     * setDisplayOnly
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setDisplayOnly(boolean _b) {
        if (!getAttribute().isEditable()) {
            _b = true;
        }
        displayOnly = _b;
        if (displayOnly) {
            setEditable(false);
        } else {
            setEditable(true);
        }
        return;
    }

    /**
     * isDisplayOnly
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isDisplayOnly() {
        return displayOnly;
    }

    /**
     * prepareToEdit
     *
     * @param _eo
     * @param _c
     * @author Anthony C. Liberto
     */
    public void prepareToEdit(EventObject _eo, Component _c) {
        requestFocus();
        return;
    }

    /**
     * isRequired
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isRequired() {
        return getAttribute().isRequired();
    }

    /**
     * cancel
     *
     * @author Anthony C. Liberto
     */
    public void cancel() {
        refreshDisplay(getAttribute());
        return;
    }

    /**
     * getInformation
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getInformation() {
        return Routines.getInformation(getAttribute());
    }

    /**
     * equals
     *
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean equals(Component _c) {
        if (_c != null) {
            if (_c instanceof EditorInterface) {
                return (this == (EditorInterface) _c);
            }
        }
        return true;
    }

	/**
     * buildEditor
     *
     * @author Anthony C. Liberto
     * @param _edit
     */
    public void buildEditor(TableCellEditor[] _edit) {
		add("West",(Component)_edit[0]);
		add("East",(Component)_edit[1]);
		return;
	}

    /**
     * setAttribute
     *
     * @param _att
     * @author Anthony C. Liberto
     */
    public void setAttribute(EANAttribute _att) {
		att = _att;
		if (editors != null) {
			int ii = editors.length;
			for (int i=0;i<ii;++i) {
				if (editors[i] instanceof EditorInterface) {
					((EditorInterface)editors[i]).setAttribute(_att);
				}
			}
		}
		return;
	}

    /**
     * getAttribute
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EANAttribute getAttribute() {
		return att;
    }

    /**
     * setEditable
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setEditable(boolean _b) {
		bEditable = _b;
		if (editors != null) {
			int ii = editors.length;
			for (int i=0;i<ii;++i) {
				if (editors[i] instanceof EditorInterface) {
					((EditorInterface)editors[i]).setEditable(_b);
				}
			}
		}
		return;
	}

    /**
     * isEditable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isEditable() {
		return bEditable;
	}

    /**
     * paste
     *
     * @author Anthony C. Liberto
     */
    public void paste() {
		if (curEdit != null) {
			curEdit.paste();
		}
		return;
	}

    /**
     * cut
     *
     * @author Anthony C. Liberto
     */
    public void cut() {
		if (curEdit != null) {
			curEdit.cut();
		}
		return;
	}

    /**
     * copy
     *
     * @author Anthony C. Liberto
     */
    public void copy() {
		if (curEdit != null) {
			curEdit.copy();
		}
		return;
	}

    /**
     * getEditForm
     *
     * @return
     * @author Anthony C. Liberto
     */
    public FormEditorInterface getEditForm() {
		return null;
	}

    /**
     * hasChanged
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasChanged() {
		boolean out = false;
		if (editors != null) {
			int ii = editors.length;
			for (int i=0;i<ii && !out ;++i) {
				if (editors[i] instanceof EditorInterface) {
					out = ((EditorInterface)editors[i]).hasChanged();
				}
			}
		}
		return out;
	}

    /**
     * isFound
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isFound() {
		boolean out = false;
		if (editors != null) {
			int ii = editors.length;
			for (int i=0;i<ii && !out ;++i) {
				if (editors[i] instanceof EditorInterface) {
					out = ((EditorInterface)editors[i]).isFound();
				}
			}
		}
		return out;
	}

    /**
     * resetFound
     *
     * @author Anthony C. Liberto
     */
    public void resetFound() {
		if (editors != null) {
			int ii = editors.length;
			for (int i=0;i<ii;++i) {
				if (editors[i] instanceof EditorInterface) {
					((EditorInterface)editors[i]).resetFound();
				}
			}
		}
		return;
	}

    /**
     * isReplaceable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isReplaceable() {
		boolean out = false;
		if (editors != null) {
			int ii = editors.length;
			for (int i=0;i<ii && !out ;++i) {
				if (editors[i] instanceof EditorInterface) {
					out = ((EditorInterface)editors[i]).isReplaceable();
				}
			}
		}
		return out;
	}

    /**
     * replace
     *
     * @param _old
     * @param _new
     * @param _bCase
     * @return
     * @author Anthony C. Liberto
     */
    public boolean replace(String _old, String _new, boolean _bCase) {
		boolean out = false;
		if (editors != null) {
			int ii = editors.length;
			for (int i=0;i<ii;++i) {
				if (editors[i] instanceof EditorInterface) {
					if (((EditorInterface)editors[i]).replace(_old,_new,_bCase)) {
						out = true;
					}
				}
			}
		}
		return out;//true;
	}

    /**
     * find
     *
     * @param _strFind
     * @param _bCase
     * @return
     * @author Anthony C. Liberto
     */
    public boolean find(String _strFind, boolean _bCase) {
		if (curEdit != null) {
			return curEdit.find(_strFind,_bCase);
		}
		return false;
	}

    /**
     * deactivate
     *
     * @author Anthony C. Liberto
     */
    public void deactivate() {
		return;
	}

    /**
     * setEffectivity
     *
     * @param _from
     * @param _to
     * @author Anthony C. Liberto
     */
    public void setEffectivity(String _from, String _to) {
		return;
	}

    /**
     * removeEditor
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean removeEditor() {
		if (editors != null) {
			int ii = editors.length;
			for (int i=0;i<ii;++i) {
				if (editors[i] instanceof EditorInterface) {
					((EditorInterface)editors[i]).removeEditor();
				}
			}
		}
		return true;
	}

    /**
     * canReceiveFocus
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canReceiveFocus() {
        FormEditorInterface tmpEf = getEditForm();
        if (tmpEf != null) {
            return tmpEf.verifyNewFocus(this);
        }
        return true;
	}

	/**
     * @see javax.swing.CellEditor#getCellEditorValue()
     * @author Anthony C. Liberto
     */
    public Object getCellEditorValue() {
		Object[] out = null;
		if (editors != null) {
			int ii = editors.length;
			out = new Object[ii];
			for (int i=0;i<ii;++i) {
				out[i] = editors[i].getCellEditorValue();
			}
		}
		return out;
	}

    /**
     * setText
     *
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setText(String _s) {
		return;
	}

    /**
     * getText
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getText() {
		return null;
	}

    /**
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     * @author Anthony C. Liberto
     */
    public Component getTableCellEditorComponent(JTable t, Object v, boolean sel, int r, int c) {return this;}

    /**
     * refreshDisplay
     *
     * @param _att
     * @author Anthony C. Liberto
     */
    public void refreshDisplay(EANAttribute _att) {}
}
