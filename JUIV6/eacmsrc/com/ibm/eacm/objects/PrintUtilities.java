//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.objects;

import java.awt.*;

import com.ibm.eacm.preference.*;

import javax.swing.*;
import java.awt.print.*;
import java.awt.geom.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 * used for print on form only, jtables use print on table 
 */
// $Log: PrintUtilities.java,v $
// Revision 1.1  2012/09/27 19:39:13  wendy
// Initial code
//
public class PrintUtilities implements Printable, EACMGlobals
{
    private static final int SCALE_CUSTOM = 1;
    private static final int SCALE_ALL = 2;
    private static final int SCALE_X = 3;
    private static final int SCALE_Y = 4;
    private static final int NO_SCALE = 0;
    private static final int PRINT_DEFAULT_SCALE = SCALE_CUSTOM;
    
    private PageFormat format = null;

    private Component component = null;
    private int scaleType = PRINT_DEFAULT_SCALE;
    private double mScaleX = 0d;
    private double mScaleY = 0d;
    private Point[] shape = null;

    private static PrintUtilities printUtils = new PrintUtilities();

    /**
     * nothing else can instantiate this class
     */
    private PrintUtilities() {
    	format = new PageFormat();
    	adjustPageFormat(false);
    }

    /**
     * set page format based on user preference change
     */
    public static void reset() {     	
    	printUtils.adjustPageFormat(false);
    }

    /**
     * launch the page setup dialog
     */
    public static void pageSetup() {
    	printUtils.adjustPageFormat(true);
    }
    private void adjustPageFormat(boolean showDialog) {
    	int _orient = Preferences.userNodeForPackage(PrefMgr.class).getInt(PrintPref.PREF_PRINT_ORIENTATION, PrintPref.PRINT_DEFAULT_ORIENTATION);
        if(format.getOrientation() != _orient){
        	format.setOrientation(_orient);
        } 
        if(showDialog){
        	PrinterJob printJob = PrinterJob.getPrinterJob();
        	format = printJob.pageDialog(format);
        }
    }

    /**
     * print this component
     * @param _c
     */
    public static void print(Component _c) {
    	printUtils.print(_c, getPrintScaleType());
    }
    
    /**
     * get scale based on user preferences
     * @return
     */
    private static int getPrintScaleType() {
        boolean bX = Preferences.userNodeForPackage(PrefMgr.class).getBoolean(PrintPref.PREF_PRINT_SCALE_X, PrintPref.PRINT_DEFAULT_SCALE);
        boolean bY = Preferences.userNodeForPackage(PrefMgr.class).getBoolean(PrintPref.PREF_PRINT_SCALE_Y, PrintPref.PRINT_DEFAULT_SCALE);
        if (bX && bY) {
            return SCALE_ALL;
        } else if (bX) {
            return SCALE_X;
        } else if (bY) {
            return SCALE_Y;
        }
        return SCALE_CUSTOM;
    }
    
    /**
     * print
     * @param _component
     * @param _scale
     */
    private void print(Component _component, int _scale) {
        Rectangle componentBounds = null;
        component = _component;
        componentBounds = component.getBounds(null);
        component.setSize(componentBounds.width, componentBounds.height);
        mScaleX = Preferences.userNodeForPackage(PrefMgr.class).getDouble(PrintPref.PREF_PRINT_RATIO, PrintPref.PRINT_DEFAULT_RATIO);
        mScaleY = mScaleX;
        scaleType = _scale;
        if (scaleType == SCALE_ALL) {
            scaleToFit();
        } else if (scaleType == SCALE_X) {
            scaleToFitX();
        } else if (scaleType == SCALE_Y) {
            scaleToFitY();
        }
        
        print();
    }

    /**
     * print
     */
    private void print() { 
        Book book = new Book(); 
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPageable(book);

        book.append(this, format, getPages());
        if (printJob.printDialog()) {
        	PrintWorker printWorker = new PrintWorker(printJob);
        	printWorker.execute();
        }
        book = null;
    }


    /**
     * scaleToFitX
     */
    private void scaleToFitX() {
        Rectangle componentBounds = component.getBounds(null);
        double scaleX = format.getImageableWidth() / componentBounds.width;
        double scaleY = 1; //may distort
        if (scaleX < 1) {
            mScaleX = scaleX;
            mScaleY = scaleY;
        }
    }

    /**
     * scaleToFitY
     */
    private void scaleToFitY() {
        Rectangle componentBounds = component.getBounds(null);
        double scaleY = format.getImageableHeight() / componentBounds.height;
        double scaleX = 1; //may distort
        if (scaleY < 1) {
            mScaleX = scaleX;
            mScaleY = scaleY;
        }
    }

    /**
     * scaleToFit
     */
    private void scaleToFit() {
        Rectangle componentBounds = component.getBounds(null);
        double scaleX = format.getImageableWidth() / componentBounds.width;
        double scaleY = format.getImageableHeight() / componentBounds.height;
        if (scaleX < 1 || scaleY < 1) {
            component.setSize((int) (componentBounds.width * scaleX), (int) (componentBounds.height * scaleY));
            mScaleX = scaleX;
            mScaleY = scaleY;
        }
    }
    private int getPages() {
        int width = (int) format.getImageableWidth();
        int height = (int) format.getImageableHeight();
        int cWidth = (int) (component.getWidth() * mScaleX);
        int cHeight = (int) (component.getHeight() * mScaleY);
        int ww = (cWidth / width) + 1;
        int hh = (cHeight / height) + 1;

        switch (scaleType) {
        case NO_SCALE :
        case SCALE_CUSTOM :
        	Vector<Point> v = new Vector<Point>();
            for (int w = 0; w < ww; ++w) {
                for (int h = 0; h < hh; ++h) {
                    v.add(new Point(w, h));
                }
            }
        
            shape = new Point[v.size()];
            v.copyInto(shape);
            break;
        case SCALE_Y :
            shape = new Point[ww];
            for (int w = 0; w < ww; ++w) {
                shape[w] = new Point(w, 0);
            }
            break;
        case SCALE_X :
            shape = new Point[hh];
            for (int h = 0; h < hh; ++h) {
                shape[h] = new Point(0, h);
            }
            break;
        default:
        case SCALE_ALL :
            return 1;

        }
        return shape.length;
    }

    /**
     * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
     */
    public int print(Graphics _g, PageFormat _format, int _page) {
        switch (scaleType) {
        case NO_SCALE :
            return printPanel(_g, _format, _page);
        case SCALE_CUSTOM :
            return printCustom(_g, _format, _page);
        default :
            return printScale(_g, _format, _page);

        }
    }

    /**
     * printScale
     * @param _g
     * @param _format
     * @param _page
     * @return
     */
    private int printScale(Graphics _g, PageFormat _format, int _page) {
        double x = _format.getImageableX();
        double y = _format.getImageableY();
        Graphics2D g2d = (Graphics2D) _g;
        Color oldBack = g2d.getBackground();
        if (_page > 0 || component == null) {
            return NO_SUCH_PAGE;
        }
        g2d.setBackground(Color.white);
        g2d.translate(x, y);
        g2d.scale(mScaleX, mScaleY);
        disableDoubleBuffering(component);
        component.paint(g2d);
        enableDoubleBuffering(component);
        g2d.setBackground(oldBack);
        return PAGE_EXISTS;
    }

    /**
     * printPanel
     * @param _g
     * @param _format
     * @param _page
     * @return
     */
    private int printPanel(Graphics _g, PageFormat _format, int _page) {
        Graphics2D g2d = (Graphics2D) _g;
        Color oldBack = g2d.getBackground();
        double x = 0d;
        double y = 0d;
        double width = 0d;
        double height = 0d;
        Point pt = null;
        double posX = 0d;
        double posY = 0d;

        if (_page >= shape.length || component == null) {
            return NO_SUCH_PAGE;
        }
        g2d.setBackground(Color.white);
        x = _format.getImageableX();
        y = _format.getImageableY();
        width = _format.getImageableWidth() / mScaleX;
        height = _format.getImageableHeight() / mScaleY;
        pt = shape[_page];
        posX = ((-pt.x * width) + x);
        posY = ((-pt.y * height) + y);
        g2d.translate(posX, posY);
        g2d.setClip((int) (pt.x * width), (int) (pt.y * height), (int) width, (int) height);
        disableDoubleBuffering(component);
        component.print(g2d);
        enableDoubleBuffering(component);
        g2d.setBackground(oldBack);
        return PAGE_EXISTS;
    }

    /**
     * printCustom
     * @param _g
     * @param _format
     * @param _page
     * @return
     */
    private int printCustom(Graphics _g, PageFormat _format, int _page) {
        Rectangle2D.Double r2d2 = null;
        Graphics2D g2d = null;
        Color oldBack = null;
        double width = 0d;
        double height = 0d;
        double posX = 0d;
        double posY = 0d;
        double x = 0d;
        double y = 0d;
        double X = 0d;
        double Y = 0d;
        Point pt = null;
        if (_page >= shape.length || component == null) {
            return (NO_SUCH_PAGE);
        }
        r2d2 = new Rectangle.Double();
        g2d = (Graphics2D) _g;
        oldBack = g2d.getBackground();
        g2d.setBackground(Color.white);
        width = _format.getImageableWidth();
        height = _format.getImageableHeight();
        x = _format.getImageableX();
        y = _format.getImageableY();
        pt = shape[_page];
        if (mScaleX == 1d) {
            posX = ((-pt.x * width) + x);
            X = (pt.x * width);
        } else {
            posX = getPosition(-pt.x, width, x);
            width = (width / mScaleX);
            X = (pt.x * width);
        }
        if (mScaleY == 1d) {
            posY = ((-pt.y * height) + y);
            Y = (pt.y * height);
        } else {
            posY = getPosition(-pt.y, height, y);
            height = (height / mScaleY);
            Y = (pt.y * height);
        }
        g2d.translate(posX, posY);
        g2d.scale(mScaleX, mScaleY);
        r2d2.setRect(X, Y, width, height);
        g2d.setClip(r2d2);
        disableDoubleBuffering(component);
        component.paint(g2d);
        enableDoubleBuffering(component);
        g2d.setBackground(oldBack);
        return PAGE_EXISTS;
    }

    private double getPosition(int _offset, double _multiplier, double _adjustment) {
        if (_offset == 0) {
            return _adjustment;
        }
        return ((_offset * _multiplier) + _adjustment);
    }

    /**
     * disableDoubleBuffering
     * @param _c
     */
    private static void disableDoubleBuffering(Component _c) {
        RepaintManager currentManager = RepaintManager.currentManager(_c);
        currentManager.setDoubleBufferingEnabled(false);
    }

    /**
     * enableDoubleBuffering
     * @param _c
     */
    private static void enableDoubleBuffering(Component _c) {
        RepaintManager currentManager = RepaintManager.currentManager(_c);
        currentManager.setDoubleBufferingEnabled(true);
    }
    
	private class PrintWorker extends SwingWorker<Void, Void> { 
		PrinterJob printJob;
		PrintWorker(PrinterJob pj){
			printJob = pj;
		}
		@Override
		public Void doInBackground() {
			try{		
	            printJob.print();
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception ",ex);
			}

			return null;
		}

		@Override
		public void done() {
			 printJob = null;
			 component=null;
		}
	}
}
