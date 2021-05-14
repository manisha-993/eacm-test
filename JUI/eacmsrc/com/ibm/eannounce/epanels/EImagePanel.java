/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EImagePanel.java,v $
 * Revision 1.2  2008/01/30 16:27:10  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:46:08  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:17  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:19  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:10  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/03 16:38:54  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.3  2005/01/28 17:54:19  tony
 * JTest Fromat step 2
 *
 * Revision 1.2  2005/01/26 17:43:25  tony
 * JTest Format Mods
 *
 * Revision 1.1.1.1  2004/02/10 16:59:50  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2003/04/25 19:22:32  tony
 * cleaned-up code and moved variables to the eAccessConstants.
 *
 * Revision 1.4  2003/04/11 20:02:33  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.epanels;
import com.elogin.*;
import java.awt.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EImagePanel extends EPanel implements EAccessConstants {
	private static final long serialVersionUID = 1L;
	private Image image = null;
	private int type = -1;
	private boolean bImageVis = true;

	/**
     * eImagePanel
     * @param _b
     * @param _img
     * @param _style
     * @author Anthony C. Liberto
     */
    public EImagePanel(boolean _b, String _img, int _style){
		super(null,_b);
		init(_img,_style);
		return;
	}

	/**
     * eImagePanel
     * @param _layout
     * @param _img
     * @param _style
     * @author Anthony C. Liberto
     */
    public EImagePanel(LayoutManager _layout, String _img, int _style){
		super(_layout);
		init(_img,_style);
		return;
	}

	/**
     * eImagePanel
     * @param _layout
     * @param _img
     * @param _style
     * @author Anthony C. Liberto
     */
    public EImagePanel(LayoutManager _layout, Image _img, int _style){
		super(_layout);
		init(_img,_style);
		return;
	}

	private void init(String _img, int _style) {
		init(eaccess().getImage(_img),_style);
		return;
	}

	private void init(Image _img, int _style) {
		image = _img;
		type = _style;
		loadImage();
		return;
	}

	private void loadImage() {
		MediaTracker mt = new MediaTracker(this);
		mt.addImage(image, 0);
		try {
			mt.waitForID(0);
		} catch (InterruptedException _ie) {
			_ie.printStackTrace();
		}
		return;
	}

	/**
     * imageExists
     * @return
     * @author Anthony C. Liberto
     */
    public boolean imageExists() {
		if (image == null) {
			return false;
		}
		return true;
	}

	/**
     * hideImage
     * @author Anthony C. Liberto
     */
    public void hideImage() {
		if (!bImageVis) {
			return;
		}
		setImageVisible(false);
		refreshImage();
		return;
	}

	/**
     * showImage
     * @author Anthony C. Liberto
     */
    public void showImage() {
		if (bImageVis) {
			return;
		}
		setImageVisible(true);
		refreshImage();
		return;
	}

	/**
     * isImageVisible
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isImageVisible() {
		return bImageVis;
	}

	/**
     * setImageVisible
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setImageVisible(boolean _b) {
		bImageVis = _b;
		return;
	}

	/**
     * refreshImage
     * @author Anthony C. Liberto
     */
    public void refreshImage() {
		validate();
		repaint();
		return;
	}

	/**
     * pack
     * @author Anthony C. Liberto
     */
    public void pack() {
		int w = image.getWidth(this);
		int h = image.getHeight(this);
		Dimension d = new Dimension(w,h);
		setSize(d);
		setPreferredSize(d);
		setMinimumSize(d);
		return;
	}

	/**
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     * @author Anthony C. Liberto
     */
    public void paintComponent(Graphics g){
		Dimension d = null;
        int iW = -1;
        int iH = -1;
        super.paintComponent(g);
		if (image == null || !bImageVis) {
			return;
		}
		d = getSize();
		iW = image.getWidth(eaccess().getLogin());
		iH = image.getHeight(eaccess().getLogin());
		switch(type) {
        case IMAGE_CENTERED:{
				g.drawImage(image,((d.width - iW)/2),((d.height - iH)/2),iW,iH, this);
				break;
			} case IMAGE_SCALED_TO_FIT:{
				g.drawImage(image,0,0,d.width,d.height,this);
				break;
			} case IMAGE_NORMAL:{
				g.drawImage(image,0,0,this);
				break;
			} case IMAGE_SCALED:{
				float iw = (float)iW;
				float ih = (float)iH;
				float cw = (float)d.getWidth();
				float ch = (float)d.getHeight();
				float scale=1f;
				int wscaled = 0;
                int hscaled = 0;
                int xpos = 0;
                int ypos = 0;
                if ((cw<=iw) && (ch<=ih)) {
					g.drawImage(image,0,0,this);
					return;
				} else if(iw/ih > cw/ch) {
					scale = cw/iw;

				} else {
					scale = ch/ih;
				}
				wscaled = (int)(iw*scale);
				hscaled = (int)(ih*scale);
				xpos=(((int)cw-wscaled)/2);
				ypos=(((int)ch-hscaled)/2);
				g.drawImage(image,xpos,ypos,wscaled,hscaled,this);
				break;
			}default: case IMAGE_TILED:{
				int horzCnt = d.width / iW;
				int h = 0;
                int vertCnt = 0;
                if(d.width % iW !=0) {
                    horzCnt++;
				}
				vertCnt = d.height / iH;
				if(d.height % iH !=0) {
                    vertCnt++;
				}
				for(int x=0;x<vertCnt;x++){
					h=0;
					for(int y=0;y<horzCnt;y++){
						g.drawImage(image,h,(x * iH),iW,iH,this);
						h+=iW;
					}
				}
				break;
			}
		}
		return;
	}

    /**
     * getPanelType
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getPanelType() {
		return TYPE_EIMAGEPANEL;
	}
}
