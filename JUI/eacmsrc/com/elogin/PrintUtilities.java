/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: PrintUtilities.java,v $
 * Revision 1.1  2007/04/18 19:42:18  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:56  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 17:30:22  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:18  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:26  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2003/10/29 00:22:23  tony
 * removed System.out. statements.
 *
 * Revision 1.6  2003/07/16 21:08:28  tony
 * updated printing logic to improve
 * functionality.
 *
 * Revision 1.5  2003/06/25 23:47:39  tony
 * fixed SCALE_ALL null pointer.
 *
 * Revision 1.4  2003/04/15 16:46:35  tony
 * 50382
 *
 * Revision 1.3  2003/04/14 21:36:24  tony
 * 50382
 *
 * Revision 1.2  2003/04/02 17:57:14  tony
 * 50331
 *
 * Revision 1.1.1.1  2003/03/03 18:03:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2002/11/11 22:55:41  tony
 * adjusted classification on the toggle
 *
 * Revision 1.4  2002/11/07 16:58:37  tony
 * added/adjusted copyright statement
 *
 */
package com.elogin;
import com.ibm.eannounce.eforms.table.PrintTable;
import java.awt.*;
import javax.swing.*;
import java.awt.print.*;
import java.awt.geom.*;
import java.util.Vector;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class PrintUtilities implements Printable, EAccessConstants {
    private PrinterJob printJob = PrinterJob.getPrinterJob();
    private PageFormat format = new PageFormat();
    private Book book = null;

    private Component component = null;
    private int scaleType = PRINT_DEFAULT_SCALE;
    private double mScaleX = 0d;
    private double mScaleY = 0d;
    private Point[] shape = null;
    private boolean bMultiThread = false;

    private boolean bSetupAdjusted = false; //50331

    private PrintUtilities() {
        this(true);
        return;
    }

    /**
     * printUtilities
     * @param _multiThread
     * @author Anthony C. Liberto
     */
    public PrintUtilities(boolean _multiThread) {
        bMultiThread = _multiThread;
        return;
    }

    /**
     * reset
     * @author Anthony C. Liberto
     */
    public void reset() { //50331
        bSetupAdjusted = false; //50331
        return; //50331
    } //50331

    /**
     * getPrinterJob
     * @return
     * @author Anthony C. Liberto
     */
    public PrinterJob getPrinterJob() {
        return printJob;
    }

    /**
     * getPageFormat
     * @return
     * @author Anthony C. Liberto
     */
    public PageFormat getPageFormat() {
        return format;
    }

    /**
     * adjustPageFormat
     * @param _orient
     * @author Anthony C. Liberto
     */
    public void adjustPageFormat(int _orient) {
        PageFormat pFormat = getPageFormat();
        if (!bSetupAdjusted) { //50331
            pFormat.setOrientation(_orient);
        } //50331
        setPageFormat(printJob.pageDialog(pFormat));
        bSetupAdjusted = true; //50331
        return;
    }

    /**
     * setPageFormat
     * @param _format
     * @author Anthony C. Liberto
     */
    public void setPageFormat(PageFormat _format) {
        format = _format;
        return;
    }

    /**
     * getBook
     * @return
     * @author Anthony C. Liberto
     */
    public Book getBook() {
        return book;
    }

    /**
     * setBook
     * @param _book
     * @author Anthony C. Liberto
     */
    public void setBook(Book _book) {
        book = _book;
        return;
    }

    /**
     * print
     * @param _c
     * @author Anthony C. Liberto
     */
    public void print(Component _c) {
        print(_c, PRINT_DEFAULT_SCALE);
        return;
    }

    /**
     * print
     * @param _component
     * @param _scale
     * @author Anthony C. Liberto
     */
    public void print(Component _component, int _scale) {
        print(_component, PRINT_DEFAULT_RATIO, _scale);
        return;
    }

    /**
     * print
     * @param _component
     * @param _d
     * @param _scale
     * @author Anthony C. Liberto
     */
    public void print(Component _component, double _d, int _scale) {
        print(_component, _d, _scale, PRINT_DEFAULT_ORIENTATION);
        return;
    }

    /**
     * print
     * @param _component
     * @param _d
     * @param _scale
     * @param _orient
     * @author Anthony C. Liberto
     */
    public void print(Component _component, double _d, int _scale, int _orient) {
        Rectangle componentBounds = null;
        component = _component;
        componentBounds = component.getBounds(null);
        component.setSize(componentBounds.width, componentBounds.height);
        setScale(_d, _d);
        print(_scale, _orient);
        return;
    }

    /**
     * setScaleX
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setScaleX(int _i) {
        Integer i = new Integer(_i);
        double dbl = i.doubleValue();
        setScaleX(dbl / 100d);
        return;
    }

    private void setScaleX(double _scale) {
        mScaleX = _scale;
        return;
    }

    /**
     * setScaleY
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setScaleY(int _i) {
        Integer i = new Integer(_i);
        double dbl = i.doubleValue();
        setScaleY(dbl / 100d);
        return;
    }

    private void setScaleY(double _scale) {
        mScaleY = _scale;
        return;
    }

    /**
     * setScale
     * @param _x
     * @param _y
     * @author Anthony C. Liberto
     */
    protected void setScale(double _x, double _y) {
        mScaleX = _x;
        mScaleY = _y;
        return;
    }

    /**
     * scaleToFitX
     * @param _format
     * @author Anthony C. Liberto
     */
    public void scaleToFitX(PageFormat _format) {
        Rectangle componentBounds = component.getBounds(null);
        double scaleX = _format.getImageableWidth() / componentBounds.width;
        double scaleY = 1; //may distort
        if (scaleX < 1) {
            setScale(scaleX, scaleY);
        }
        return;
    }

    /**
     * scaleToFitY
     * @param _format
     * @author Anthony C. Liberto
     */
    public void scaleToFitY(PageFormat _format) {
        Rectangle componentBounds = component.getBounds(null);
        double scaleY = _format.getImageableHeight() / componentBounds.height;
        double scaleX = 1; //may distort
        if (scaleY < 1) {
            setScale(scaleX, scaleY);
        }
        return;
    }

    /**
     * scaleToFit
     * @param _format
     * @param _symmetricScaling
     * @author Anthony C. Liberto
     */
    public void scaleToFit(PageFormat _format, boolean _symmetricScaling) {
        Rectangle componentBounds = component.getBounds(null);
        double scaleX = _format.getImageableWidth() / componentBounds.width;
        double scaleY = _format.getImageableHeight() / componentBounds.height;
        if (scaleX < 1 || scaleY < 1) {
            if (_symmetricScaling) {
                if (scaleX < scaleY) {
                    scaleY = scaleX;
                } else {
                    scaleX = scaleY;
                }
            }
            component.setSize((int) (componentBounds.width * scaleX), (int) (componentBounds.height * scaleY));
            setScale(scaleX, scaleY);
        }
        return;
    }

    /**
     * scaleComponent
     * @author Anthony C. Liberto
     */
    public void scaleComponent() {
        Rectangle componentBounds = component.getBounds(null);
        component.setSize((int) (componentBounds.width * mScaleX), (int) (componentBounds.height * mScaleY));
        return;
    }

    /**
     * print
     * @param _scale
     * @param _orient
     * @author Anthony C. Liberto
     */
    public void print(int _scale, int _orient) {
        if (book == null) {
            book = new Book();
        }
        if (!bSetupAdjusted) { //50331
            format.setOrientation(_orient);
        } //50331
        printJob.setPageable(book);
        scaleType = _scale;
        if (scaleType == SCALE_ALL) {
            scaleToFit(format, false);
        } else if (scaleType == SCALE_X) {
            scaleToFitX(format);
        } else if (scaleType == SCALE_Y) {
            scaleToFitY(format);
        }
        book.append(this, format, getPages(format));
        if (printJob.printDialog()) {
            if (bMultiThread) {
                printMultiThread();
            } else {
                printSingleThread();
            }
        }
        book = null;
        return;
    }

    private void printSingleThread() {
        try {
            printJob.print();
            if (component instanceof PrintTable) { //50382
                ((PrintTable) component).dereference(); //50382
            } //50382
        } catch (PrinterException _pe) {
            _pe.printStackTrace();
        }
        return;
    }

    private void printMultiThread() {
        final ESwingWorker printWorker = new ESwingWorker() {
            public Object construct() {
                try {
                    printJob.print();
                } catch (PrinterException _pe) {
                    _pe.printStackTrace();
                }
                return null;
            }
            public void finalize() { //50382
                if (component instanceof PrintTable) { //50382
                    ((PrintTable) component).dereference(); //50382
                } //50382
            } //50382

        };
        printWorker.start();
        return;
    }

    private int getPages(PageFormat _format) {
        int width = (int) _format.getImageableWidth();
        int height = (int) _format.getImageableHeight();
        int cWidth = (int) (component.getWidth() * mScaleX);
        int cHeight = (int) (component.getHeight() * mScaleY);
        int ww = (cWidth / width) + 1;
        int hh = (cHeight / height) + 1;
        Vector v = null;
        int ii = -1;
        switch (scaleType) {
        case NO_SCALE :
        case SCALE_CUSTOM :
            v = new Vector();
            for (int w = 0; w < ww; ++w) {
                for (int h = 0; h < hh; ++h) {
                    v.add(new Point(w, h));
                }
            }
            ii = v.size();
            shape = new Point[ii];
            for (int i = 0; i < ii; ++i) {
                shape[i] = (Point) v.get(i);
            }
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
     * @author Anthony C. Liberto
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
     * @author Anthony C. Liberto
     */
    public int printScale(Graphics _g, PageFormat _format, int _page) {
        double x = _format.getImageableX();
        double y = _format.getImageableY();
        Graphics2D g2d = (Graphics2D) _g;
        Color oldBack = g2d.getBackground();
        if (_page > 0 || component == null) {
            return (NO_SUCH_PAGE);
        }
        g2d.setBackground(Color.white);
        g2d.translate(x, y);
        g2d.scale(mScaleX, mScaleY);
        disableDoubleBuffering(component);
        component.paint(g2d);
        enableDoubleBuffering(component);
        g2d.setBackground(oldBack);
        return (PAGE_EXISTS);
    }

    /**
     * printPanel
     * @param _g
     * @param _format
     * @param _page
     * @return
     * @author Anthony C. Liberto
     */
    public int printPanel(Graphics _g, PageFormat _format, int _page) {
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
            return (NO_SUCH_PAGE);
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
        component.paint(g2d);
        enableDoubleBuffering(component);
        g2d.setBackground(oldBack);
        return (PAGE_EXISTS);
    }

    /**
     * printCustom
     * @param _g
     * @param _format
     * @param _page
     * @return
     * @author Anthony C. Liberto
     */
    public int printCustom(Graphics _g, PageFormat _format, int _page) {
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
        return (PAGE_EXISTS);
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
     * @author Anthony C. Liberto
     */
    public static void disableDoubleBuffering(Component _c) {
        RepaintManager currentManager = RepaintManager.currentManager(_c);
        currentManager.setDoubleBufferingEnabled(false);
        return;
    }

    /**
     * enableDoubleBuffering
     * @param _c
     * @author Anthony C. Liberto
     */
    public static void enableDoubleBuffering(Component _c) {
        RepaintManager currentManager = RepaintManager.currentManager(_c);
        currentManager.setDoubleBufferingEnabled(true);
        return;
    }
}
