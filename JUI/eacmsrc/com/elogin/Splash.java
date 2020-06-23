/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: Splash.java,v $
 * Revision 1.2  2008/01/30 16:27:01  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:18  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:58:50  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/04 16:57:40  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.3  2005/02/02 17:30:21  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:16  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:26  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2003/10/03 20:49:54  tony
 * updated accessibility.
 *
 * Revision 1.4  2003/04/11 20:02:24  tony
 * added copyright statements.
 *
 */
package com.elogin;
import java.awt.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class Splash {
    private Image img = null;
    private SplashWindow splash = null;
    private JFrame jFrame = null;

    /**
     * create a new splash screen from the image
     *
     * @param _s
     */
    public Splash(String _s) {
        img = getImage(getString(_s));
        jFrame = new JFrame();
        jFrame.getAccessibleContext().setAccessibleName(getString("accessible.splash"));
        splash = new SplashWindow(jFrame, img);
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
     * getImage
     * @param _img
     * @return
     * @author Anthony C. Liberto
     */
    public Image getImage(String _img) {
        return eaccess().getImage(_img);
    }

    /**
     * getImageIcon
     * @param _img
     * @return
     * @author Anthony C. Liberto
     */
    public ImageIcon getImageIcon(String _img) {
        return eaccess().getImageIcon(_img);
    }

    /**
     * getString
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public String getString(String _code) {
        return eaccess().getString(_code);
    }

    /**
     * remove the spash screen
     */
    public void remove() {
        splash.dispose();
        splash.dereference();
        splash = null;
        return;
    }

    /**
     * repaint the splashwindow
     */
    public void repaint() {
        splash.repaint();
        return;
    }

    /**
     * isShowing
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isShowing() {
        return splash.isShowing();
    }
}

/**
 * the spalsh window calss that displays the splash scrren to the monitor
 */
class SplashWindow extends Window {
	private static final long serialVersionUID = 1L;
	private Image img = null;
    SplashWindow(Frame _parent, Image _img) {
        super(_parent);
        img = _img;
        init(_parent);
        return;
    }

    private void init(Frame _parent) {
        MediaTracker mt = new MediaTracker(this);
        int iW = -1;
        int iH = -1;
        Dimension screenDim = null;
        Rectangle winDim = null;
        mt.addImage(img, 0);
        try {
            mt.waitForID(0);
        } catch (InterruptedException e) {
            EAccess.report(e,false);
        }

        iW = img.getWidth(_parent);
        iH = img.getHeight(_parent);
        setSize(iW, iH);

        /* Center the window */
        screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        winDim = getBounds();
        setLocation((screenDim.width - winDim.width) / 2, (screenDim.height - winDim.height) / 2);
        show();
        update(getGraphics());
        return;
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        removeAll();
        removeNotify();
        try {
            finalize();
        } catch (Throwable _e) {
            _e.printStackTrace();
        }
        return;
    }

    /**
     * the paint process for the splash screen.
     *
     * @param _g
     */
    public void paint(Graphics _g) {
        if (img != null) {
            _g.drawImage(img, 0, 0, this);
        }
        return;
    }
}
