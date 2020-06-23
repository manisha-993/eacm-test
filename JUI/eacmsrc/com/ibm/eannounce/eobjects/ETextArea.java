/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: ETextArea.java,v $
 * Revision 1.3  2012/04/05 17:45:13  wendy
 * jre142 and win7 changes
 *
 * Revision 1.2  2008/01/30 16:26:55  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:48  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:17  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:18  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:59:10  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/03 21:26:14  tony
 * JTest Format Third Pass
 *
 * Revision 1.4  2005/01/28 19:36:59  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/26 18:20:09  tony
 * JTest Formatting
 *
 * Revision 1.2  2004/11/19 18:01:51  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:50  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2003/10/29 19:10:43  tony
 * acl_20031029
 *
 * Revision 1.8  2003/10/27 22:18:21  tony
 * updated logic to make ACCESSIBLE_ENABLED a boolean
 * that was not computed each time but instead a constant...
 * That will allow for the app to run a bit faster.
 * Parameterized accessibility on the table with the new
 * Parm.
 *
 * Revision 1.7  2003/10/17 22:47:00  tony
 * parameterized setLookAndFeel to prevent memory leak for testing.
 *
 * Revision 1.6  2003/10/07 21:32:50  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.5  2003/04/30 21:43:52  tony
 * updated color selction capabilities on textComponents.
 *
 * Revision 1.4  2003/04/14 21:37:24  tony
 * improved lookAndFeel handling.
 *
 * Revision 1.3  2003/04/11 20:02:33  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.Document;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ETextArea extends JTextArea implements EAccessConstants, EDisplayable {
	private static final long serialVersionUID = 1L;
	/**
     * bModalCursor
     */
    private boolean bModalCursor = false;
    /**
     * bUseBack
     */
    private boolean bUseBack = false;
    /**
     * bUseFore
     */
    private boolean bUseFore = true;
    /**
     * bUseFont
     */
    private boolean bUseFont = true;

    /**
     * eTextArea
     * @author Anthony C. Liberto
     */
    public ETextArea() {
        super();
        return;
    }

    /**
     * eTextArea
     * @param _txt
     * @author Anthony C. Liberto
     */
    public ETextArea(String _txt) {
        super(_txt);
        return;
    }

    /**
     * eTextArea
     * @param _doc
     * @author Anthony C. Liberto
     */
    public ETextArea(Document _doc) {
        super(_doc);
        return;
    }

    /**
     * eTextArea
     * @param _r
     * @param _c
     * @author Anthony C. Liberto
     */
    public ETextArea(int _r, int _c) {
        super(_r, _c);
        return;
    }

    /**
     * eTextArea
     * @param _s
     * @param _r
     * @param _c
     * @author Anthony C. Liberto
     */
    public ETextArea(String _s, int _r, int _c) {
        super(_s, _r, _c);
        return;
    }

    /**
     * eTextArea
     * @param _doc
     * @param _s
     * @param _r
     * @param _c
     * @author Anthony C. Liberto
     */
    public ETextArea(Document _doc, String _s, int _r, int _c) {
        super(_doc, _s, _r, _c);
        return;
    }

    /**
     * @see javax.swing.text.JTextComponent#getSelectionColor()
     * @author Anthony C. Liberto
     */
    public Color getSelectionColor() {
        return eaccess().getPrefColor(PREF_COLOR_SELECTION_BACKGROUND, DEFAULT_COLOR_SELECTION_BACKGROUND);
    }

    /**
     * @see javax.swing.text.JTextComponent#getSelectedTextColor()
     * @author Anthony C. Liberto
     */
    public Color getSelectedTextColor() {
        return eaccess().getPrefColor(PREF_COLOR_SELECTION_FOREGROUND, DEFAULT_COLOR_SELECTION_FOREGROUND);
    }

    /**
     * @see java.awt.Component#getCursor()
     * @author Anthony C. Liberto
     */
    public Cursor getCursor() {
        if (isModalCursor()) {
            return eaccess().getModalCursor();
        } else {
            EDisplayable disp = getDisplayable();
            if (disp != null) {
                return disp.getCursor();
            }
        }
        return eaccess().getCursor();
    }

    /**
     * getDisplayable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EDisplayable getDisplayable() {
        Container par = getParent();
        if (par instanceof EDisplayable) {
            return (EDisplayable) par;
        }
        return null;
    }

    /**
     * setModalCursor
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setModalCursor(boolean _b) {
        bModalCursor = _b;
        return;
    }

    /**
     * isModalCursor
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isModalCursor() {
        return bModalCursor;
    }

    /**
     * setUseDefined
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseDefined(boolean _b) {
        setUseBack(_b);
        setUseFore(_b);
        setUseFont(_b);
        return;
    }

    /**
     * setUseBack
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseBack(boolean _b) {
        bUseBack = _b;
        return;
    }

    /**
     * setUseFore
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseFore(boolean _b) {
        bUseFore = _b;
        return;
    }

    /**
     * setUseFont
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseFont(boolean _b) {
        bUseFont = _b;
        return;
    }

    /**
     * eaccess
     * @return
     * @author Anthony C. Liberto
     */
    public static EAccess eaccess() {
        return EAccess.eaccess();
    }

    /**
     * @see java.awt.Component#getBackground()
     * @author Anthony C. Liberto
     */
    public Color getBackground() {
        if (eaccess().canOverrideColor() && bUseBack) {
            if (isEnabled()) {
                return eaccess().getBackground();
            } else {
                return eaccess().getDisabledBackground();
            }
        }
        return super.getBackground();
    }

    /**
     * @see java.awt.Component#getForeground()
     * @author Anthony C. Liberto
     */
    public Color getForeground() {
        if (eaccess().canOverrideColor() && bUseFore) {
            if (isEnabled()) {
                return eaccess().getForeground();
            } else {
                return eaccess().getDisabledForeground();
            }
        }
        return super.getForeground();
    }

    /**
     * @see java.awt.MenuContainer#getFont()
     * @author Anthony C. Liberto
     */
    public Font getFont() {
        if (EANNOUNCE_UPDATE_FONT && bUseFont) {
            return eaccess().getFont();
        }
        return super.getFont();
    }

 /*
    private void init() {
        setOpaque(false);
        //1.4		if (eaccess().isWindows()) {
        //1.4		setLookAndFeel();
        //1.4		}
        return;
    }
*/
    /**
     * set the look and feel for the GCheckBox
     * to the windows look and feel
     * /
    public void setLookAndFeel() {
        if (EAccess.isArmed(ACCESSIBLE_ARM_FILE)) {
            LookAndFeel lnf = UIManager.getLookAndFeel();
            try {
                UIManager.setLookAndFeel(WINDOWS_LNF);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            SwingUtilities.updateComponentTreeUI(this);

            try {
                UIManager.setLookAndFeel(lnf);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return;
    }*/
}
