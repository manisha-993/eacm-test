/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: EStatusbar.java,v $
 * Revision 1.3  2012/04/05 17:33:08  wendy
 * jre142 and win7 changes
 *
 * Revision 1.2  2008/01/30 16:26:59  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:09  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:38  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2005/09/08 17:58:54  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.6  2005/02/09 18:55:23  tony
 * Scout Accessibility
 *
 * Revision 1.5  2005/02/04 16:57:41  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.4  2005/02/02 21:30:07  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/27 23:18:18  tony
 * JTest Formatting
 *
 * Revision 1.2  2004/11/19 18:01:50  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.16  2003/10/29 19:10:41  tony
 * acl_20031029
 *
 * Revision 1.15  2003/10/27 22:18:20  tony
 * updated logic to make ACCESSIBLE_ENABLED a boolean
 * that was not computed each time but instead a constant...
 * That will allow for the app to run a bit faster.
 * Parameterized accessibility on the table with the new
 * Parm.
 *
 * Revision 1.14  2003/10/17 22:46:59  tony
 * parameterized setLookAndFeel to prevent memory leak for testing.
 *
 * Revision 1.13  2003/10/14 17:25:49  tony
 * 52548
 *
 * Revision 1.12  2003/10/08 20:09:19  tony
 * 52476
 *
 * Revision 1.11  2003/10/07 21:36:21  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.10  2003/04/03 16:19:06  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.9  2003/03/25 23:29:05  tony
 * added eDisplayable to provide an
 * easier interface into components.  This will
 * assist in ease of programming in the future.
 *
 * Revision 1.8  2003/03/24 21:52:23  tony
 * added modalCursor logic.
 *
 * Revision 1.7  2003/03/21 20:54:31  tony
 * updated getBackground, getForeground, and getFont logic.
 * added logic so that each can be set for the component.
 *
 * Revision 1.6  2003/03/18 22:39:10  tony
 * more accessibility updates.
 *
 * Revision 1.5  2003/03/17 23:44:36  tony
 * adjust eStatus.component placement logic.
 *
 * Revision 1.4  2003/03/12 23:51:09  tony
 * accessibility and column order
 *
 * Revision 1.3  2003/03/11 00:33:23  tony
 * accessibility changes
 *
 * Revision 1.2  2003/03/07 21:40:46  tony
 * Accessibility update
 *
 * Revision 1.1.1.1  2003/03/03 18:03:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2002/11/07 16:58:38  tony
 * added/adjusted copyright statement
 *
 */
package com.elogin;
import java.awt.*;
import javax.swing.*;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EStatusbar extends JToolBar implements Accessible, EAccessConstants, EDisplayable {
	private static final long serialVersionUID = 1L;
	private EDatePanel date = new EDatePanel();
	private ETimeLabel time = null;
	private EInformationPanel infoPane = null;
	private EVersionPanel versPane = new EVersionPanel("West");
	private EVersionPanel lngPane = new EVersionPanel("West");
	private EVersionPanel rolePane = new EVersionPanel("West");
	/**
     * bModalCursor
     */
    private boolean bModalCursor = false;
	/**
     * bUseBack
     */
    private boolean bUseBack = true;
	/**
     * bUseFont
     */
    private boolean bUseFont = true;
	/**
     * bUseFore
     */
    private boolean bUseFore = true;

	/**
     * eStatusbar
     * @author Anthony C. Liberto
     */
    public EStatusbar() {
		super();
		setLookAndFeel();
		time = new ETimeLabel(date);
		infoPane = new EInformationPanel(this);
		init();
		setFloatable(false);
		setDoubleBuffered(true);
		setOrientation(JToolBar.HORIZONTAL);
		setAlignmentX(JToolBar.BOTTOM_ALIGNMENT);
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
     * getDisplayable
     * @author Anthony C. Liberto
     * @return EDisplayable
     */
    public EDisplayable getDisplayable() {
		Container par = getParent();
		if (par instanceof EDisplayable) {
			return (EDisplayable)par;
		}
		return null;
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
     * setModalCursor
     * @author Anthony C. Liberto
     * @param _b 
     */
    public void setModalCursor(boolean _b) {
		bModalCursor = _b;
		return;
	}

	/**
     * isModalCursor
     * @author Anthony C. Liberto
     * @return boolean
     */
    public boolean isModalCursor() {
		return bModalCursor;
	}

	/**
     * setUseDefined
     * @author Anthony C. Liberto
     * @param _b 
     */
    public void setUseDefined(boolean _b) {
		setUseBack(_b);
		setUseFore(_b);
		setUseFont(_b);
		return;
	}

	/**
     * setUseBaclk
     * @author Anthony C. Liberto
     * @param _b 
     */
    public void setUseBack(boolean _b) {
		bUseBack = _b;
		return;
	}

	/**
     * setUseFore
     * @author Anthony C. Liberto
     * @param _b 
     */
    public void setUseFore(boolean _b) {
		bUseFore = _b;
		return;
	}

	/**
     * setUseFont
     * @author Anthony C. Liberto
     * @param _b 
     */
    public void setUseFont(boolean _b) {
		bUseFont = _b;
		return;
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

	private void init() {
		add(versPane);
		add(lngPane);
		add(rolePane);
		add(infoPane);
		add(date);
		add(time);
		return;
	}

	/**
     * getVersionPanel
     * @return
     * @author Anthony C. Liberto
     */
    public EVersionPanel getVersionPanel() {
		return versPane;
	}

	/**
     * isPastDate
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPastDate() {
		if (infoPane == null) {
            return false;
		}
		return infoPane.isEnabled(EInformationPanel.WAY_BACK);
	}

	/**
     * setFilter
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setFilter(boolean _b){
		infoPane.setEnabled(_b,EInformationPanel.FILTER);
		return;
	}

//52548	public void setPast(boolean _b) {
	/**
     * setPast
     * @param _b
     * @param _time
     * @author Anthony C. Liberto
     */
    public void setPast(boolean _b, String _time) {					//52548
		infoPane.setEnabled(_b,EInformationPanel.WAY_BACK);
		if (_b && _time != null) {									//52548
			time.setTime(_time);									//52548
			time.setHoldTime(true);									//52548
		} else if (!_b) {											//52548
			time.setHoldTime(false);								//52548
			time.incrementTime();									//52548
		}															//52548
		return;
	}

	/**
     * setHidden
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setHidden(boolean _b) {					//52476
		infoPane.setEnabled(_b,EInformationPanel.HIDDEN);		//52476
		return;											//52476
	}													//52476

	/**
     * isHidden
     * @param _b
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isHidden(boolean _b) {				//52476
		return infoPane.isEnabled(EInformationPanel.HIDDEN);		//52476
	}													//52476

	/**
     * isFilter
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isFilter() {							//52476
		return infoPane.isEnabled(EInformationPanel.FILTER);		//52476
	}													//52476

	/**
     * setVersion
     * @param _vers
     * @author Anthony C. Liberto
     */
    public void setVersion(String _vers) {
		versPane.setText(_vers);
		return;
	}

	/**
     * setVersionTip
     * @param _tip
     * @author Anthony C. Liberto
     */
    public void setVersionTip(String _tip) {
		versPane.setToolTipText(_tip);
		return;
	}

	/**
     * setLanguage
     * @param _vers
     * @author Anthony C. Liberto
     */
    public void setLanguage(String _vers) {
		lngPane.setText(_vers);
		return;
	}

	/**
     * setLanguageTip
     * @param _tip
     * @author Anthony C. Liberto
     */
    public void setLanguageTip(String _tip) {
		lngPane.setToolTipText(_tip);
		return;
	}

	/**
     * setRole
     * @param _role
     * @author Anthony C. Liberto
     */
    public void setRole(String _role) {
		rolePane.setText(_role);
		return;
	}

	/**
     * setRoleTip
     * @param _tip
     * @author Anthony C. Liberto
     */
    public void setRoleTip(String _tip) {
		rolePane.setToolTipText(_tip);
		return;
	}

	/**
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
		date.revalidate();
		time.revalidate();
		versPane.revalidate();
		lngPane.revalidate();
		rolePane.revalidate();
		revalidate();
		return;
	}




/*
 acl_20031007
 */
	/**
     * setLookAndFeel
     * @author Anthony C. Liberto
     */
    public void setLookAndFeel() {
    	 /*breaks win7if (EAccess.isAccessible()) {
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
		}*/
		return;
	}
}
