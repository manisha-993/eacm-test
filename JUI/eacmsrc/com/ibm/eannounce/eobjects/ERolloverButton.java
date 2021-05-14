/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: ERolloverButton.java,v $
 * Revision 1.5  2011/11/11 00:03:34  wendy
 * win7 looses image with mouse movement
 *
 * Revision 1.4  2009/05/28 13:09:57  wendy
 * Performance cleanup
 *
 * Revision 1.3  2009/04/15 19:47:04  wendy
 * Prevent enabling prev button when there is no prev
 *
 * Revision 1.2  2008/01/30 16:26:56  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:47  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:17  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:17  tony
 * This is the initial load of OPICM
 *
 * Revision 1.15  2005/09/08 17:59:09  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.14  2005/03/28 17:56:38  tony
 * MN_button_disappear
 * adjust to preven java bug 4664818
 *
 * Revision 1.13  2005/03/17 22:04:44  tony
 * fixed painting to improve performance.
 *
 * Revision 1.12  2005/03/16 19:26:44  tony
 * disappearing button fixed, based on bug documentation on
 * sun.com
 *
 * Revision 1.11  2005/02/10 19:01:12  tony
 * Button Animation
 *
 * Revision 1.10  2005/02/08 21:38:16  tony
 * disappearing buttons
 *
 * Revision 1.9  2005/02/08 16:33:58  tony
 * JTest Formatting Fourth pass
 *
 * Revision 1.8  2005/02/03 22:52:14  tony
 * updated fix reporting
 *
 * Revision 1.7  2005/01/28 19:36:59  tony
 * JTest Second Pass
 *
 * Revision 1.6  2005/01/26 18:20:08  tony
 * JTest Formatting
 *
 * Revision 1.5  2005/01/26 16:15:01  tony
 * added tracking for icon rendering issue
 *
 * Revision 1.4  2005/01/25 22:56:48  tony
 * MN21699161
 *
 * Revision 1.3  2005/01/14 19:54:33  tony
 * adjusted button logic to troubleshoot
 * Button disappearing issue.
 *
 * Revision 1.2  2004/11/19 18:01:09  tony
 * MN21699161 -- added MediaTracker and improved icon painting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2003/03/18 22:39:12  tony
 * more accessibility updates.
 *
 * Revision 1.5  2003/03/13 21:17:49  tony
 * accessibility enhancements.
 *
 * Revision 1.4  2003/03/12 23:51:19  tony
 * accessibility and column order
 *
 * Revision 1.3  2003/03/11 00:33:27  tony
 * accessibility changes
 *
 * Revision 1.2  2003/03/07 21:40:49  tony
 * Accessibility update
 *
 * Revision 1.1.1.1  2003/03/03 18:03:52  tony
 * This is the initial load of OPICM
 *
 * Revision 1.14  2002/11/07 16:58:17  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;
import com.ibm.eannounce.eforms.toolbar.EannounceButton;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ERolloverButton extends EButton implements MouseListener, MouseMotionListener, EannounceButton, EAccessConstants {
	private static final long serialVersionUID = 1L;
	private Border[] bord = new Border[4];
    private static Border normal_Border = new EmptyBorder(2, 2, 2, 2);
    private static Dimension d = UIManager.getDimension("eannounce.buttonsize");
    private int iBord = 0;
    private boolean iconEnabled = true;
    private Icon enabledIcon = null;
    private Icon disabledIcon = null; //MN21699161
    private boolean autoPopup = true;

    /**
     *  mt
     */
    private MediaTracker mt = null; //MN21699161
    /**
     * iEnabled
     */
    private int iEnabled = 0; //MN21699161
    /**
     * iDisabled
     */
    private int iDisabled = 1; //MN21699161
    /**
     * strName
     */
    private String strName = null; //MN21699161

    /**
     * eRolloverButton
     * @author Anthony C. Liberto
     */
    public ERolloverButton() {
        init();
    }

    //MN21699161	public eRolloverButton(Icon _icon) {
    /**
     * eRolloverButton
     * @param _icon
     * @param _name
     * @author Anthony C. Liberto
     */
    public ERolloverButton(Icon _icon, String _name) { //MN21699161
        super(_icon);
        enabledIcon = _icon;
        if (_name != null) { //MN21699161
            strName = new String(_name); //MN21699161
        } //MN21699161
        init();
    }

    /**
     * eRolloverButton
     * @param _string
     * @author Anthony C. Liberto
     */
    public ERolloverButton(String _string) {
        super(_string);
        init();
    }

    /**
     * setAutoPopup
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setAutoPopup(boolean _b) {
        autoPopup = _b;
    }

    /**
     * isAutoPopup
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isAutoPopup() {
        return autoPopup;
    }

    /**
     * @see javax.swing.AbstractButton#setIcon(javax.swing.Icon)
     * @author Anthony C. Liberto
     */
    public void setIcon(Icon _icon) {
        super.setIcon(_icon);
        enabledIcon = _icon;
    }

    /**
     * init
     * @author Anthony C. Liberto
     */
    private void init() {
        createDisabledIcon(enabledIcon);
        bord[0] = normal_Border;
	    bord[1] = UIManager.getBorder("eannounce.raisedBorder");
	    bord[2] = UIManager.getBorder("eannounce.loweredBorder");
	    bord[3] = UIManager.getBorder("eannounce.etchedBorder");
	    addMouseListener(this);
	    if (isAnimate()) {
	        addMouseMotionListener(this);
		}
		
        setFocusPainted(false);
        setFocusable(false);
        initTracker(enabledIcon, disabledIcon); //MN21699161
    }

    private void createDisabledIcon(Icon _icon) {
        if (_icon != null && disabledIcon == null) {
            disabledIcon = new ImageIcon(GrayFilter.createDisabledImage(((ImageIcon) _icon).getImage()));
        }
    }

    /**
     * @see javax.swing.JComponent#updateUI()
     * @author Anthony C. Liberto
     */
    public void updateUI() {
        setUI(new BasicButtonUI() {
                public Dimension getMinimumSize(JComponent _c) {
                    return d;
                }

                public Dimension getPreferredSize(JComponent _c) {
                    return d;
                }

                protected void paintIcon(Graphics _g, JComponent _c, Rectangle _r) { //MN21699161
                    if (isAnimate()) {
	                    paintMyIcon(_g, _c, _r); //MN21699161
					} else {
						super.paintIcon(_g,_c,_r);
					}
                } //MN21699161

                protected void paintButtonPressed(Graphics _g, AbstractButton _ab) {
					if (isAnimate()) {
						super.paintButtonPressed(_g,_ab);
					} else {
						bord[ETCHED_BORDER].paintBorder(_ab,_g, 0, 0,getWidth(),getHeight());
					}
				}
        });
    }

    /**
     * setBorder
     * @param _i
     * @param _b
     * @author Anthony C. Liberto
     * /
    public void setBorder(int _i, Border _b) {
        if (_i < bord.length) {
            bord[_i] = _b;
        }
    }*/

    /**
     * setBorder
     * @param _i
     * @author Anthony C. Liberto
     */
    private void setBorder(int _i) {
        if (_i < bord.length) {
            if (iBord != _i) {
                iBord = _i;
                repaintImmediately(); 
            } else {
                iBord = _i;
            }
        }
        repaint();   //win7 looses image with mouse movement 
    }

    /**
     * @see javax.swing.JComponent#getBorder()
     * @author Anthony C. Liberto
     */
    public Border getBorder() {
        if (!isEnabled()) {
            return normal_Border;
        } else if (eaccess().isBusy()) {
            return normal_Border;
        } else if (bord != null) {
            return bord[iBord];
        }
        return normal_Border;
    }

    /**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseClicked(MouseEvent _me) {
    }
    /**
     * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseDragged(MouseEvent _me) {
    }

    /**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mousePressed(MouseEvent _me) {
		if (!isAnimate()) {
			return;
		}
        lowerBorder();
    }

    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseReleased(MouseEvent _me) {
		if (!isAnimate()) {
			return;
		}
        if (contains(_me.getPoint()) && isAutoPopup()) {
            raiseBorder();
        } else {
            normalBorder();
        }
    }

    /**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseEntered(MouseEvent _me) {
		if (!isAnimate()) {
			return;
		}
        raiseBorder();
    }

    /**
     * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseMoved(MouseEvent _me) {
		if (!isAnimate()) {
			return;
		}
        raiseBorder();
    }

    /**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseExited(MouseEvent _me) {
		if (!isAnimate()) {
			return;
		}
        normalBorder();
    }

    /**
     * repaintImmediately
     * @author Anthony C. Liberto
     */
    private void repaintImmediately() {
        if (isShowing()) {
            update(getGraphics());
        } else {
            repaint();
        }
    }

    /**
     * setIconEnabled
     * @param _b
     * @author Anthony C. Liberto
     * /
    public void setIconEnabled(boolean _b) {
        iconEnabled = _b;
    }*/

    /**
     * isIconEnabled
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isIconEnabled() {
        return iconEnabled;
    }

    /**
     * @see javax.swing.AbstractButton#getIcon()
     * @author Anthony C. Liberto
     */
    public Icon getIcon() {
        if (!isIconEnabled()) {
            return getDisabledIcon();
        }
        return enabledIcon;
    }

    /**
     * @see javax.swing.AbstractButton#getDisabledIcon()
     * @author Anthony C. Liberto
     */
    public Icon getDisabledIcon() {
        return disabledIcon;
    }

    /**
     * raiseBorder
     * @author Anthony C. Liberto
     */
    protected void raiseBorder() {
        setBorder(RAISED_BORDER);
    }

    /**
     * lowerBorder
     * @author Anthony C. Liberto
     */
    private void lowerBorder() {
        setBorder(LOWERED_BORDER);
    }

    /**
     * normalBorder
     * @author Anthony C. Liberto
     */
    private void normalBorder() {
        setBorder(NORMAL_BORDER);
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() { //acl_Mem_20020130
        removeMouseListener(this); //acl_Mem_20020130
        removeMouseMotionListener(this); //acl_Mem_20020130
        mt = null; //MN21699161
        setIcon(null);
        setBorder(null);
        removeAll(); //acl_Mem_20020130
        removeNotify();
    } //acl_Mem_20020130

    /*
     MN21699161
     */
    /**
     * initTracker
     * @param _enabled
     * @param _disabled
     * @author Anthony C. Liberto
     */
    private void initTracker(Icon _enabled, Icon _disabled) {
        if (mt == null) {
            mt = new MediaTracker(this);
        }
        if (_enabled != null && _enabled instanceof ImageIcon) {
            mt.addImage(((ImageIcon) _enabled).getImage(), iEnabled);
        }
        if (_disabled != null && _disabled instanceof ImageIcon) {
            mt.addImage(((ImageIcon) _disabled).getImage(), iDisabled);
        }
    }

    /**
     * paintMyIcon
     * @param _g
     * @param _c
     * @param _r
     * @author Anthony C. Liberto
     * @concurrency $none
     */
    public synchronized void paintMyIcon(Graphics _g, JComponent _c, Rectangle _r) {
        Icon icon = null;
        int id = iEnabled;
        if (isEnabled()) {
            icon = getIcon();
        } else {
			/*
			 paint the icon twice once as enabled then
			 paint correctly as disabled.  This should
			 prevent the disappearing disabled icons.
			 */
//			icon = getIcon();						//first_coat
//			if (icon != null) {						//first_coat
//				icon.paintIcon(_c,_g,_r.x,_r.y);	//first_coat
//			}										//first_coat
            icon = getDisabledIcon();
            id = iDisabled;
        }
		/*
		 the paintImageIcon method is not necessary
		 ImageIcon and normal icons can be printed
		 the sameway.  The method is there to enhance
		 control over the icon painting.
		 */
        if (icon != null) {
            if (icon instanceof ImageIcon) {
                paintImageIcon(_g, (ImageIcon) icon, id, _r, _c);
            } else {
                icon.paintIcon(_c, _g, _r.x, _r.y);
            }
        }
    }

    /**
     * paintImageIcon
     *
     * @param _g
     * @param _i
     * @param _id
     * @param _r
     * @param _c
     * @author Anthony C. Liberto
     *
     */
    private void paintImageIcon(Graphics _g, ImageIcon _i, int _id, Rectangle _r, JComponent _c) {
        boolean bDrawn = false;
        int iCount = 0;
        loadImage(_id);
        try {
//			System.out.println("paintingIcon: " + strName + " at position " + _r.x + ", " + _r.y);
			bDrawn = _g.drawImage(_i.getImage(), _r.x, _r.y, _c);
            /*
             if fails, then try a max of 10 more times.
             loading image is overkill, but doesn't hurt.
             */
            while (!bDrawn && iCount < 10) {
				++iCount;
				System.out.println("****    redrawing image " + iCount + " time(s): " + strName + "    ****");
				loadImage(_id);
				bDrawn = _g.drawImage(_i.getImage(), _r.x, _r.y, _c);
			}
        } catch (Exception _x) {
            EAccess.report(_x,true);
        }
    }

    /**
     * loadImage
     * @param _id
     * @author Anthony C. Liberto
     */
    private void loadImage(int _id) {
        try {
            if (mt != null) {
				mt.waitForID(_id);
            }else{
            	System.err.println("ERolloverButton.loadImage mt is null");
            }
        } catch (InterruptedException _ie) {
            _ie.printStackTrace();
        }
    }

    /**
     * isAnimate
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isAnimate() {
		return eaccess().getPrefBoolean(PREF_ANIMATE_BUTTON, DEFAULT_ANIMATE_BUTTON);
	}

    /**
     * refresh toolbar
     * MN_button_disappear
     * @author Anthony C. Liberto
     */
    public void refreshButton() {
 		//setEnabled(true); prev button was incorrecttly getting enabled
		setEnabled(isEnabled());
		repaint();
	}
}
