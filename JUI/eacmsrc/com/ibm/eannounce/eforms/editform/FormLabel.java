/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: FormLabel.java,v $
 * Revision 1.2  2008/01/30 16:27:09  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:18  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:55  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:02  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/04 15:22:09  tony
 * JTest Format Third Pass
 *
 * Revision 1.2  2005/01/27 19:15:16  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:39  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2003/05/01 22:41:35  tony
 * added static borders to address border rendering
 * issues on found.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2002/11/11 17:00:08  tony
 * 23032
 *
 * Revision 1.8  2002/11/07 16:58:27  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.editform;
import com.elogin.*;
import COM.ibm.eannounce.objects.*;
import javax.swing.UIManager;
import javax.swing.border.Border;
import java.awt.Color;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class FormLabel extends EMLabel {
	private static final long serialVersionUID = 1L;
	/**
     * ERROR_LABEL
     */
    public static final int ERROR_LABEL = 0;
    /**
     * CONSTANT_LABEL
     */
    public static final int CONSTANT_LABEL = 1;
    /**
     * META_LABEL
     */
    public static final int META_LABEL = 2;
    /**
     * ENTITY_ITEM_LABEL
     */
    public static final int ENTITY_ITEM_LABEL = 3;
    /**
     * DISPLAY_ONLY_LABEL
     */
    public static final int DISPLAY_ONLY_LABEL = 4;

    /**
     * NO_DESCRIPTION
     */
    public static final int NO_DESCRIPTION = -1;
    /**
     * CAPABILITY_DESCRIPTION
     */
    public static final int CAPABILITY_DESCRIPTION = 0;
    /**
     * CODE_DESCRIPTION
     */
    public static final int CODE_DESCRIPTION = 1;
    /**
     * DEFAULT_DESCRIPTION
     */
    public static final int DEFAULT_DESCRIPTION = 2;
    /**
     * HELP_DESCRIPTION
     */
    public static final int HELP_DESCRIPTION = 3;
    /**
     * LONG_DESCRIPTION
     */
    public static final int LONG_DESCRIPTION = 4;
    /**
     * SHORT_DESCRIPTION
     */
    public static final int SHORT_DESCRIPTION = 5;
    /**
     * TYPE_DESCRIPTION
     */
    public static final int TYPE_DESCRIPTION = 6;

    /**
     * ENTITY_ITEM_DESCRIPTION
     */
    public static final int ENTITY_ITEM_DESCRIPTION = 7;
    /**
     * ENTITY_ITEM_TYPE
     */
    public static final int ENTITY_ITEM_TYPE = 8;
    /**
     * ENTITY_ITEM_ID
     */
    public static final int ENTITY_ITEM_ID = 9;

    private int type = -1;
    private int description = NO_DESCRIPTION;

    private EANMetaAttribute meta = null;
    private EANAttribute att = null;
    private String key = null;

    private boolean found = false;
    //23032	private final Border defBord = null;
    //23032	private final Border foundBord = UIManager.getBorder("eannounce.foundBorder");

    /**
     * formLabel
     * @param _s
     * @param _key
     * @param _type
     * @author Anthony C. Liberto
     */
    public FormLabel(String _s, String _key, int _type) {
        this(_s, _key, _type, NO_DESCRIPTION);
        return;
    }

    /**
     * formLabel
     * @param _s
     * @param _key
     * @param _type
     * @param _description
     * @author Anthony C. Liberto
     */
    public FormLabel(String _s, String _key, int _type, int _description) {
        super(_s);
        setType(_type);
        setKey(_key);
        setDescription(_description);
        return;
    }

    /**
     * formLabel
     * @param _meta
     * @param _key
     * @param _description
     * @author Anthony C. Liberto
     */
    public FormLabel(EANMetaAttribute _meta, String _key, int _description) {
        super();
        setType(META_LABEL);
        setDescription(_description);
        refresh(_meta);
        setKey(_key);
        return;
    }

    /**
     * formLabel
     * @param _att
     * @param _key
     * @param _type
     * @author Anthony C. Liberto
     */
    public FormLabel(EANAttribute _att, String _key, int _type) {
        super();
        setType(_type);
        att = _att;
        setKey(_key);
        refresh(att);
        return;
    }

    /**
     * formLabel
     * @param _ei
     * @param _key
     * @param _description
     * @author Anthony C. Liberto
     */
    public FormLabel(EntityItem _ei, String _key, int _description) {
        super();
        setType(ENTITY_ITEM_LABEL);
        setKey(_key);
        setDescription(_description);
        refresh(_ei);
    }

    /**
     * setDescription
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setDescription(int _i) {
        description = _i;
    }

    /**
     * getDescription
     * @return
     * @author Anthony C. Liberto
     */
    public int getDescription() {
        return description;
    }

    /**
     * setType
     * @param _type
     * @author Anthony C. Liberto
     */
    public void setType(int _type) {
        type = _type;
        if (type == ERROR_LABEL) {
            super.setForeground(Color.red);
        }
        return;
    }

    /**
     * getType
     * @return
     * @author Anthony C. Liberto
     */
    public int getType() {
        return type;
    }

    /**
     * refresh
     * @param _meta
     * @author Anthony C. Liberto
     */
    public void refresh(EANMetaAttribute _meta) {
        if (isType(META_LABEL)) {
            meta = _meta;
            if (description == CAPABILITY_DESCRIPTION) {
                setText(_meta.getCapability());
            } else if (description == CODE_DESCRIPTION) {
                setText(_meta.getAttributeCode());
            } else if (description == DEFAULT_DESCRIPTION) {
                setText(_meta.getDefaultValue());
            } else if (description == HELP_DESCRIPTION) {
                setText(_meta.getMetaHelpValue().getHelpValueText());
            } else if (description == LONG_DESCRIPTION) {
                setText(_meta.getLongDescription());
            } else if (description == SHORT_DESCRIPTION) {
                setText(_meta.getShortDescription());
            } else if (description == TYPE_DESCRIPTION) {
                setText(_meta.getAttributeType());
            }
        }
        return;
    }

    /**
     * refresh
     * @param _att
     * @author Anthony C. Liberto
     */
    public void refresh(EANAttribute _att) {
        setText(_att.toString());
        return;
    }

    /**
     * getAttributeCode
     * @return
     * @author Anthony C. Liberto
     */
    public String getAttributeCode() {
        if (meta != null) {
            return meta.getAttributeCode();

        } else if (att != null) {
            return att.getAttributeCode();
        }
        return null;
    }

    /**
     * refresh
     * @param _item
     * @author Anthony C. Liberto
     */
    public void refresh(EntityItem _item) {
        if (isType(ENTITY_ITEM_LABEL)) {
            if (description == ENTITY_ITEM_DESCRIPTION) {
                setText(_item.getLongDescription());

            } else if (description == ENTITY_ITEM_TYPE) {
                setText(_item.getEntityType());

            } else if (description == ENTITY_ITEM_ID) {
                setText(Integer.toString(_item.getEntityID()));
            }
        }
        return;
    }

    /**
     * getMeta
     * @return
     * @author Anthony C. Liberto
     */
    public EANMetaAttribute getMeta() {
        return meta;
    }

    /**
     * getAttribute
     * @return
     * @author Anthony C. Liberto
     */
    public EANAttribute getAttribute() {
        return att;
    }

    /**
     * setKey
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setKey(String _s) {
        if (_s != null) {
            key = new String(_s);
        }
        return;
    }

    /**
     * getKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getKey() {
        return key;
    }

    /**
     * isType
     * @param _type
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isType(int _type) {
        if (type == _type) {
            return true;
        }
        return false;
    }

    /**
     * @see java.awt.Container#removeAll()
     * @author Anthony C. Liberto
     */
    public void removeAll() {
        type = 0;
        description = 0;
        meta = null;
        super.removeAll();
        return;
    }

    /**
     * @see java.awt.Component#setBackground(java.awt.Color)
     * @author Anthony C. Liberto
     */
    public void setBackground(Color _c) {
        if (!isType(ERROR_LABEL)) {
            super.setBackground(_c);
        }
        return;
    }

    /**
     * @see java.awt.Component#setForeground(java.awt.Color)
     * @author Anthony C. Liberto
     */
    public void setForeground(Color _c) {
        if (!isType(ERROR_LABEL)) {
            super.setForeground(_c);
        }
        return;
    }
    /*
     * find
     */
    /**
     * find
     * @param _strFind
     * @param _bCase
     * @return
     * @author Anthony C. Liberto
     */
    public boolean find(String _strFind, boolean _bCase) {
        boolean bFound = false;
        if (!isShowing()) {
            return false;
        }
        //23032		found = routines.find(getText(),_strFind,_bCase);
        //23032		if (found) {
        bFound = Routines.find(getText(), _strFind, _bCase); //23032
        if (bFound) { //23032
            found = true; //23032
            setBorder(getCurrentBorder());
            requestFocus();
        }
        return found;
    }

    /**
     * isFound
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isFound() {
        return found;
    }

    /**
     * resetFound
     * @author Anthony C. Liberto
     */
    public void resetFound() {
        if (!found) {
            return;
        }
        found = false;
        setBorder(getCurrentBorder());
        return;
    }

    private Border getCurrentBorder() {
        if (isFound()) {
            return EAccess.FOUND_BORDER;
        }
        return UIManager.getBorder("eannounce.emptyBorder");
    }

}
