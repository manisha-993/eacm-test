// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import java.awt.event.*;
import java.awt.*;
import java.awt.print.*;
import java.awt.geom.*;
import java.awt.font.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import java.util.*;

/******************************************************************************
* This is used to handle print actions.
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLPrintMgr.java,v $
// Revision 1.1  2012/09/27 19:39:19  wendy
// Initial code
//
// Revision 1.2  2008/01/30 16:27:09  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/04/18 19:47:48  wendy
// Reorganized JUI module
//
// Revision 1.4  2006/05/10 14:43:01  wendy
// Change e-announce to EACM
//
// Revision 1.3  2006/01/25 18:59:05  wendy
// AHE copyright
//
// Revision 1.2  2005/10/12 12:48:58  wendy
// Conform to new jtest configuration
//
// Revision 1.1.1.1  2005/09/09 20:39:20  tony
// This is the initial load of OPICM
//
//
class XMLPrintMgr implements XMLEditorGlobals
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.1 $";
    private PageFormat pageFormat = null; // paper plus page orientation
    private Action[] allActions = new Action[3];

    XMLPrintMgr()
    {
        // order here is reflected in menu items
        allActions[0] = new PrintAction();
        allActions[1] = new PrintPreviewAction();
        allActions[2] = new PageSetupAction();
    }

    void dereference()
    {
        for (int i=0; i<allActions.length; i++)
        {
            if (allActions[i] instanceof XMLDereferencible) {
                ((XMLDereferencible)allActions[i]).dereference();
            }
            allActions[i] = null;
        }

        allActions=null;
        pageFormat = null;
    }

    Action[] getActions()
    {
        return allActions;
    }

    /*********************************************************************
    * This displays the page setup dialog so the PageFormat can be changed.
    */
    private class PageSetupAction extends AbstractAction
    {
    	private static final long serialVersionUID = 1L;
    	private PageSetupAction()
        {
            super(PAGE_SETUP_ACTION);
            putValue(Action.MNEMONIC_KEY, new Integer('U'));
        }
        public void actionPerformed(ActionEvent e)
        {
            // handle asynchronously, editor does not repaint
            Thread printThread = new Thread()
            {
                public void run()
                {
                    try {
                        // Get a PrinterJob to coordinate printing
                        PrinterJob printJob = PrinterJob.getPrinterJob();

                        // Ask user for page format (e.g., portrait/landscape)
                        if (pageFormat==null) {
                            pageFormat = printJob.defaultPage();
                        }
                        // if user cancels, the orig pf object will be returned
                        pageFormat = printJob.pageDialog(pageFormat);
                    }
                    catch(SecurityException se)
                    {
                        // user may have cancelled in an applet ignore it
                        System.out.println("Error printing: " + se);
                    }
                }
            };
            printThread.start();
        }
    }

    /*********************************************************************
    * This executes a print command for the editor
    *
    * Although the system controls the overall printing process, the application has
    * to get the ball rolling by setting up a PrinterJob. The PrinterJob, the key point of
    * control for the printing process, stores the print job properties, controls the display
    * of print dialogs, and is used to initiate printing.
    * To steer the PrinterJob through the printing process, the application needs to:
    *       Get a PrinterJob by calling PrinterJob.getPrinterJob
    *       Tell the PrinterJob where the rendering code is by calling setPrintable or
    *           setPageable
    *       If desired, display the Page Setup and Print dialogs by calling pageDialog and
    *           printDialog
    *       Initiate printing by calling print
    *
    * The rendering of pages is controlled by the printing system through calls to the
    * application's imaging code.
    */
    private class PrintAction extends HTMLEditorKit.StyledTextAction  implements XMLDereferencible
    {
    	private static final long serialVersionUID = 1L;
    	private JEditorPane editor=null;
        private PrintAction()
        {
            super(PRINT_ACTION);
            putValue(Action.MNEMONIC_KEY, new Integer('P'));
            putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));

        }
        public void dereference()
        {
            editor = null;
        }
        public void actionPerformed(ActionEvent e)
        {
            Thread printThread = null;
            editor = getEditor(e);
            if (editor != null) {
                // this causes this to be executed on the event dispatch thread
                // after actionPerformed returns to allow repaint, but doesn't seem to work.. why?
    //          SwingUtilities.invokeLater(new Runnable() {

                // handle asynchronously, print can be time consuming and editor does not repaint
                printThread = new Thread()
                    {
                        public void run()
                        {
                            try {
                                // get a PrinterJob to coordinate printing
                                PrinterJob printJob = PrinterJob.getPrinterJob();

                                if (pageFormat==null) { // user did not use pageSetup so get default
                                    pageFormat = printJob.defaultPage();
                                }

                                // tell the PrinterJob what to print
                                printJob.setPageable(new PagedXML(pageFormat,editor));

                                // pop up an OS-specific print dialog and if the user hasn't
                                // cancelled the printing, print it
                                if (printJob.printDialog()) {
                                    try {
        //System.err.println("**************PRINT job calling print()");
                                        printJob.print();
                                    } catch (PrinterException pex) {
                                        System.err.println("Error printing: " + pex);
                                        pex.printStackTrace();
                                    }
                                }
                            }
                            catch(SecurityException se)
                            {
                                // user may have cancelled in an applet ignore it
                                System.out.println("Error printing: " + se);
                            }
                        }
                    };
    //          });
                printThread.start();
            }
        }
    }

    /*********************************************************************
    * This executes a print preview command for the editor
    */
    private class PrintPreviewAction extends HTMLEditorKit.StyledTextAction
        implements XMLDereferencible
    {
    	private static final long serialVersionUID = 1L;
    	private JEditorPane editor=null;
        private PrintPreviewAction()
        {
            super(PRINT_PREVIEW_ACTION);
            putValue(Action.MNEMONIC_KEY, new Integer('V'));
            putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_P,
                    InputEvent.CTRL_MASK+InputEvent.SHIFT_MASK));
        }

        public void dereference()
        {
            editor = null;
        }

        public void actionPerformed(ActionEvent e)
        {
            Thread printThread = null;
            editor = getEditor(e);
            if (editor != null) {
                // this causes this to be executed on the event dispatch thread
                // after actionPerformed returns to allow repaint, but doesn't seem to work.. why?
    //          SwingUtilities.invokeLater(new Runnable() {

                // handle asynchronously, print can be time consuming and editor does not repaint
                printThread = new Thread()
                {
                    public void run() {
                        try {
                            PrintPreviewDialog dialog = null;

                            if (pageFormat==null) { // user did not use pageSetup so get default
                                // Get a PrinterJob to coordinate printing
                                PrinterJob printJob = PrinterJob.getPrinterJob();
                                pageFormat = printJob.defaultPage();
                            }

                            dialog = new PrintPreviewDialog(editor);
                            dialog.setVisible(true);
                            dialog.dispose();
                            dialog.dereference();
                            dialog=null;
                            // set focus in editor
                            editor.getRootPane().getTopLevelAncestor().requestFocus();
                            editor.requestFocus();  // editor does not get focus in applet
                        } catch(SecurityException se) {
                            // user may have cancelled in an applet ignore it
                            System.out.println("Error printing: " + se);
                        }
                    }
                };
    //          });
                printThread.start();
            }
        }

        private class PrintPreviewDialog extends JDialog implements ActionListener, KeyEventPostProcessor
        {
        	private static final long serialVersionUID = 1L;
        	private JButton nextButton=null, printButton=null, previousButton=null, closeButton=null;
            private PrintPreviewCanvas canvas=null;
            private JLabel pageLabel=null;
            private static final int PAGE_W = 432;//710;
            private static final int PAGE_H = 350;//548;

            PrintPreviewDialog(JEditorPane editor)  {
                super(JOptionPane.getFrameForComponent(editor), XMLEditorGlobals.APP_NAME+" XML Editor Print Preview",true);

                // setup gui using the object to print
                setupGUI(new PagedXML(pageFormat,editor));

                pack();

                setSize(getPreferredSize());
                setLocationRelativeTo(editor.getParent());//editor); jre 1.4.0 positions dialog incorrectly

                setResizable(true);

                KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(this);
            }

            public boolean postProcessKeyEvent(KeyEvent e)
            {
                if (e.getID()==KeyEvent.KEY_RELEASED &&
                    e.getKeyCode()==KeyEvent.VK_ESCAPE)
                {
                	setVisible(false); // caller will dispose();
                }
                return false;
            }

            void dereference()
            {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventPostProcessor(this);

                nextButton.removeActionListener(this);
                previousButton.removeActionListener(this);
                closeButton.removeActionListener(this);
                printButton.removeActionListener(this);
                canvas.dereference();

                canvas=null;
                nextButton=null;
                previousButton=null;
                printButton=null;
                closeButton=null;
                //printJob = null;
                pageLabel = null;

                XMLEditor.dereferenceContainer(this);
            }

            private void setupGUI(Pageable book)
            {
                JPanel lowerPanel = new JPanel();
                JPanel buttonPanel = new JPanel();
                JPanel pagePanel = new JPanel();

                canvas = new PrintPreviewCanvas(book);
                getContentPane().add(new JScrollPane(canvas), "Center");

                if (book.getPageFormat(0).getOrientation()==PageFormat.LANDSCAPE) {
                    canvas.setPreferredSize(new Dimension(PAGE_W,PAGE_H));//760, 590));
                }
                else {
                    canvas.setPreferredSize(new Dimension(PAGE_H,PAGE_W));//590, 760));
                }

                lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.X_AXIS));
                previousButton = new JButton("Previous");
                previousButton.setMnemonic('V');
                buttonPanel.add(previousButton);
                previousButton.addActionListener(this);

                nextButton = new JButton("Next");
                nextButton.setMnemonic('N');
                buttonPanel.add(nextButton);
                nextButton.addActionListener(this);

                closeButton = new JButton("Close");
                closeButton.setMnemonic('C');
                buttonPanel.add(closeButton);
                closeButton.addActionListener(this);

                printButton = new JButton(allActions[0]);
                printButton.setMnemonic(((Integer)(allActions[0].getValue(Action.MNEMONIC_KEY))).intValue());
                printButton.setText(XMLEditorMenuSpec.getMenuText((String)(allActions[0].getValue(Action.NAME))));
                buttonPanel.add(printButton);

                pagePanel.setLayout(new BoxLayout(pagePanel, BoxLayout.X_AXIS));
                pagePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),
                        BorderFactory.createEmptyBorder(4, 5, 4, 5))); // top, left, bottom, and right insets

                pageLabel = new JLabel("Page "+canvas.getCurrentPage()+" of "+book.getNumberOfPages(), JLabel.LEFT);
                pagePanel.add(pageLabel);
                lowerPanel.add(Box.createRigidArea(new Dimension(3, 0)));
                lowerPanel.add(pagePanel);
                lowerPanel.add(buttonPanel);
                lowerPanel.add(Box.createHorizontalGlue());

                getContentPane().add(lowerPanel, "South");

                nextButton.setEnabled(canvas.hasNextPage());
                previousButton.setEnabled(canvas.hasPreviousPage());
            }

            /*********************************************************************
            * process action
            * @param event ActionEvent
            */
            public void actionPerformed(ActionEvent event)
            {
                Object source = event.getSource();
                if (source == nextButton)
                {
                    canvas.turnPage(1);
                    nextButton.setEnabled(canvas.hasNextPage());
                    previousButton.setEnabled(canvas.hasPreviousPage());
                    pageLabel.setText("Page "+canvas.getCurrentPage()+" of "+canvas.getBook().getNumberOfPages());
                }
                else if (source == previousButton)
                {
                    canvas.turnPage(-1);
                    nextButton.setEnabled(canvas.hasNextPage());
                    previousButton.setEnabled(canvas.hasPreviousPage());
                    pageLabel.setText("Page "+canvas.getCurrentPage()+" of "+canvas.getBook().getNumberOfPages());
                }
                else if (source == closeButton)
                {
//System.err.println("canvas size: "+canvas.getSize());
                	setVisible(false);// caller will dispose();
                }
                // printbutton goes to print action
            }
        } // end PrintPreviewDialog

        private class PrintPreviewCanvas extends JComponent
        {
        	private static final long serialVersionUID = 1L;
        	private Pageable book;
            private int currentPage;
            private static final double SCALE = 0.5;

            PrintPreviewCanvas(Pageable b)
            {
                book = b;
                currentPage = 0;
            }

            void dereference()
            {
                ((XMLDereferencible)book).dereference();
                book=null;
            }

            boolean hasNextPage() { return (currentPage < book.getNumberOfPages()-1); }
            boolean hasPreviousPage() { return (currentPage !=0); }
            int getCurrentPage() { return currentPage+1; }// used for display

            Pageable getBook() { return book; }

            public void paintComponent(Graphics g)
            {
                Graphics2D g2 = null;
                PageFormat format = null;
                double xoff; // x offset of page start in window
                double yoff; // y offset of page start in window
                double scale; // scale factor to fit page in window
                double pageW = 0d;
                double pageH = 0d;
                double canvasW = 0d;
                double canvasH = 0d;
                Rectangle2D pageRect = null;
                Printable printable = null;

                super.paintComponent(g);
                g2 = (Graphics2D)g;
                format = book.getPageFormat(currentPage);

                pageW = format.getWidth();
                pageH = format.getHeight();
                canvasW = getWidth() - 1;
                canvasH = getHeight() - 1;

//System.err.println("PrintPrev:paint pageW: "+pageW+" pageH: "+pageH+" canvasW: "+canvasW+" canvasH: "+canvasH);
                if (pageW/pageH < canvasW/canvasH) {// center horizontally
                    scale = canvasH/pageH;
                    xoff = SCALE * (canvasW - scale * pageW);
                    yoff = 0;
                }  else {// center vertically
                    scale = canvasW/pageW;
                    xoff = 0;
                    yoff = SCALE * (canvasH - scale * pageH);
                }

                // position 'paper' on the preview canvas
                g2.translate(xoff, yoff);
                g2.scale(scale, scale);

                // draw page outline (ignoring margins)
                pageRect = new Rectangle2D.Double(0, 0, pageW, pageH);
                g2.setPaint(Color.white);
                g2.fill(pageRect);
                g2.setPaint(Color.black);
                g2.draw(pageRect);

                // set clip to area calculated for this page
               // g2.setClip(new Rectangle2D.Double(format.getImageableX(), format.getImageableY(),
              //          format.getImageableWidth(), format.getImageableHeight()));

                printable = book.getPrintable(currentPage);
                try {
                    printable.print(g2, format, currentPage);
                }  catch (PrinterException exception) {
                    g2.draw(new Line2D.Double(0, 0, pageW, pageH));
                    g2.draw(new Line2D.Double(0, pageW, 0, pageH));
                    System.out.println(exception.getMessage());  // jtest req
                }
            }

            void turnPage(int by)  {
                int newPage = currentPage + by;
                if (0 <= newPage && newPage < book.getNumberOfPages()) {
                    currentPage = newPage;
                    repaint();
                }
            }
        } // end PrintPreviewCanvas
    } // end PrintPreviewAction

    // How lenient are we with the bottom margin in widow/orphan prevention?
    private static final double MARGIN_ADJUST = 0.97;
    //The scale factor is .75 because the default fonts are overly large
    private static final double SCALE_FACTOR= 0.75; // amount to scale before printing
    private static final double MAX_Y = 36.0;
    // The font for printing page numbers
    private static final Font HEADER_FONT = new Font("Serif", Font.PLAIN, 12);

    private class PagedXML implements Pageable, Printable, XMLDereferencible
    {
        private View rootView;          // root View to be printed
        private PageFormat pFormat;     // local copy of pageformat to prevent change during print process
        private int numPages=0;           // number of pages
        private double printX, printY;  // coordinates of upper-left of print area
        private double printWidth;      // printable area width
        private double printHeight;     // printable area height
        private Rectangle drawRect;     // rectangle in which the view is painted
        // For a document of n pages, this list stores the lengths of pages
        // 0 through n-2.  The last page is assumed to have a full length
        private ArrayList<Double> pageLengths = new ArrayList<Double>();

        // For a document of n pages, this list stores the starting offset of
        // pages 1 through n-1.  The offset of page 0 is always 0
        private ArrayList<Double> pageOffsets = new ArrayList<Double>();
        // starting offset for the page currently used in length calculation
        private double pageStart = 0;

        /******************************
         * constructor
         **/
        private PagedXML(PageFormat pf, JEditorPane editor)
        {
            ViewFactory viewFactory = null;
            Double documentHeight, dprintw,dprinth;

            pFormat = pf;
            // use the page format for the printable area
            printX = pFormat.getImageableX()/SCALE_FACTOR;  // get top corner
            printY = pFormat.getImageableY()/SCALE_FACTOR;  // get top corner
            printWidth = pFormat.getImageableWidth()/SCALE_FACTOR; // get printable width
            printHeight = pFormat.getImageableHeight()/SCALE_FACTOR; // get printable height
            dprintw = new Double(printWidth);
            dprinth = new Double(printHeight);

//System.err.println("PagedXML: SCALE_FACTOR: "+SCALE_FACTOR+" printX: "+printX+" printY: "+printY+
//      " printWidth: "+printWidth+" printHeight: "+printHeight+" paper height: "+pf.getHeight()/SCALE_FACTOR);

            // use the ViewFactory to create a root View object for the document
            viewFactory = editor.getEditorKit().getViewFactory();

            // use the root element from the document to get the object to print
            rootView = viewFactory.create(editor.getDocument().getDefaultRootElement());

            // The Swing text architecture requires setParent() on
            // the root View before use or will get null ptr in paint().
            rootView.setParent(new DummyView(rootView, viewFactory, editor));

            // Tell the view how wide the page is; it has to format itself
            // to fit within this width.  The height doesn't really matter here
            //rootView.setSize((float)printWidth, (float)printHeight);
            rootView.setSize(dprintw.floatValue(), dprinth.floatValue());

            // get the height after the view has formatted itself for the specified (scaled)width
            documentHeight = new Double(rootView.getPreferredSpan(View.Y_AXIS));
//System.err.println("PagedXML: documentHeight: "+documentHeight);

            // set up the rectangle that tells the view where to draw itself
            drawRect = new Rectangle(0, 0, dprintw.intValue(), documentHeight.intValue());

            // find page breaks if needed
            if (documentHeight.doubleValue() > printHeight)
            {
                calcPageBreak(rootView, drawRect);
            }

            // figure out how many pages.
            numPages = pageLengths.size() + 1;
/*
for (int i=0; i<pageLengths.size(); i++)
System.err.println("PagedXML: pageLength["+i+"] "+pageLengths.get(i));
for (int i=0; i<pageOffsets.size(); i++)
System.err.println("PagedXML: pageOffsets["+i+"] "+pageOffsets.get(i));
*/      }

        public void dereference()
        {
            ((XMLDereferencible)rootView.getParent()).dereference(); // clear the DummyView parent
            rootView = null;
            pFormat = null;
            drawRect = null;
            pageLengths.clear();
            pageLengths = null;
            pageOffsets.clear();
            pageOffsets = null;
        }

//int xcnt=0;
//StringBuffer blanks = new StringBuffer("");
        /****************************************************************************
         * This method loops through the children of the specified view,
         * recursing as necessary, and inserts pages breaks when needed.
         * It makes a rudimentary attempt to avoid "widows" and "orphans".
         **/
        private void calcPageBreak(View v, Rectangle2D allocation)
        {
            int numKids;
            Double printw = new Double(printWidth);
//System.err.print(blanks+"calcPgBrk: "+(++xcnt)+" entered for allocation: y "+
//allocation.getY()+" h: "+allocation.getHeight()+" view.element: "+v.getElement());
//blanks.append("  ");
            // Figure out how tall this view is, and tell it to allocate
            // that space among its children
            //double vheight = v.getPreferredSpan(View.Y_AXIS);
            Double vheight = new Double(v.getPreferredSpan(View.Y_AXIS));
            // The setSize method is generally called to make sure the View layout is complete
            // prior to trying to perform an operation on it that requires an up-to-date layout.
//            v.setSize((float)printWidth, (float)vheight);
            v.setSize(printw.floatValue(), vheight.floatValue());

            // Now loop through each of the children
            numKids = v.getViewCount();
//System.err.println(blanks+"calcPgBrk: setview size to w: "+printWidth+" h: "+vheight+" numkids: "+numKids);
            for(int i = 0; i < numKids; i++)
            {
                Rectangle kidBox = null;
                double kidEnd;

                View kid = v.getView(i);  // this is the child we're working with
//System.err.print(blanks+"calcPgBrk: loop kid.element["+i+"]: "+kid.getElement());
                // Figure out its size and location
                Shape kidShape = v.getChildAllocation(i, allocation);
                if (kidShape == null) {
                    continue;
                }
                kidBox = kidShape.getBounds();

                // This is the Y coordinate of the bottom of the child on the current page
                kidEnd = kidBox.getY() + kidBox.getHeight() - pageStart;
//System.err.println(blanks+"calcPgBrk: top of kid: "+kidBox.getY()+" y at bottom of kid kidEnd: "+kidEnd);

                // If this is the first child of a group, then we want to ensure
                // that it doesn't get left by itself at the bottom of a page.
                // I.e. we want to prevent "widows"
                if ((numKids > 1) && (i == 0))
                {
                    Double dpageStart;
                    Double dgetY;
                    // If it is not near the end of the page, then just move
                    // on to the next child
                    if (kidEnd < printHeight*MARGIN_ADJUST) {
                        continue;
                    }

                    dpageStart = new Double(pageStart);
                    dgetY = new Double(allocation.getY());
                    // Otherwise, the child is near the bottom of the page, so
                    // break the page before this child and place this child on
                    // the new page.
//                    if (pageStart!=allocation.getY())//kidBox.getY()) // make sure not creating an empty page
                    if (dpageStart.compareTo(dgetY)!=0)//kidBox.getY()) // make sure not creating an empty page
                    {
//System.err.println(blanks+"calcPgBrk: breaking because first kid, is at eop.. force break at start of parent");
                        breakPage(allocation.getY());//kidBox.getY());
                        continue;
                    }
                }

                // If this is the last child of a group, we don't want it to
                // appear by itself at the top of a new page, so allow it to
                // squeeze past the bottom margin if necessary.  This helps to
                // prevent "orphans"
                if ((numKids > 1) && (i == numKids-1))
                {
                    // If it fits normally, just move on to the next one
                    if (kidEnd < printHeight) {
                        continue;
                    }

                    // Otherwise, if it fits with extra space, then break the
                    // page at the end of the group
                    if (kidEnd < printHeight/MARGIN_ADJUST)
                    {
//System.err.println(blanks+"calcPgBrk: breaking after last kid .. after parent allocation");
                        breakPage(allocation.getY() + allocation.getHeight());
                        continue;
                    }
                }

                // If the child is not the first or last of a group, then we use
                // the bottom margin strictly.  If the child fits on the page,
                // then move on to the next child.
                if (kidEnd < printHeight) {
                    continue;
                }

                // If we get here, the child doesn't fit on this page.  If it has
                // no children, then break the page before this child and continue.
                if (kid.getViewCount() == 0)
                {
//System.err.println(blanks+"calcPgBrk: breaking before kid .. has no views");
                    breakPage(kidBox.getY());
                    continue;
                }

                // If we get here, then the child did not fit on the page, but it
                // has kids of its own, so recurse to see if any of those kids
                // will fit on the page.
                calcPageBreak(kid, kidBox);
            } // end of kids loop
//blanks.setLength(blanks.length()-2);
//System.err.println(blanks+"calcPgBrk: "+(xcnt--)+" exiting");
        }

        /***************************************************************
         * Break a page at the specified Y coordinate.  Store the necessary
         * information into the pageLengths and pageOffsets lists
         **/
        private void breakPage(double y)
        {
            double pageLength = y-pageStart;
//System.err.println("PagedXML:breakPage: pageStart: "+pageStart+" at y: "+y+
//" pagelen: "+pageLength);
            pageStart = y;
            pageLengths.add(new Double(pageLength));
            pageOffsets.add(new Double(pageStart));
        }

        // ---Pageable interface---
        // return the number of pages.
        public int getNumberOfPages() {
            return numPages;
        }
        // same printable for all pages
        public Printable getPrintable(int pagenum) {
            return this;
        }

        /*************************************************************
         * Return the PageFormat object for the specified page.
         * Use the computed length of the page in the returned PageFormat object.
         * The PrinterJob will use this as a clipping region, which will prevent
         * extraneous parts of the
         * document from being drawn in the top and bottom margins.
         * @param pagenum int
         * @return PageFormat
         **/
        public PageFormat getPageFormat(int pagenum)
        {
            double pageLength;
            PageFormat f;
            Paper p;

            // On the last page, just return the user-specified page format
            if (pagenum == numPages-1) {
                f = pFormat;
            }else{
                // Otherwise, look up the height of this page and return an
                // appropriate PageFormat.
                pageLength = ((Double)pageLengths.get(pagenum)).doubleValue();
                f = (PageFormat) pFormat.clone();
                p = f.getPaper();
                if (f.getOrientation() == PageFormat.PORTRAIT) {
                    p.setImageableArea(printX*SCALE_FACTOR, printY*SCALE_FACTOR,
                        printWidth*SCALE_FACTOR, pageLength*SCALE_FACTOR);
                }
                else {
                    p.setImageableArea(printY*SCALE_FACTOR, printX*SCALE_FACTOR,
                        pageLength*SCALE_FACTOR, printWidth*SCALE_FACTOR);
                }
                f.setPaper(p);

    //System.err.println("PagedXML:getPageFormat: pagenum: "+pagenum+" orig imageable h: "+pFormat.getImageableHeight()+
    //" scaled new imageable h: "+f.getImageableHeight());
            }
            return f;
        }

        // ---Printable interface---
        /*********************************************************************************
         * This prints a specified page
         * @param g Graphics
         * @param format PageFormat
         * @param pageIndex int
         * @return int
         **/
        public int print(Graphics g, PageFormat format, int pageIndex)
        {
            Graphics2D g2;
            Rectangle clipRect;
            double pageStart = 0.0;//, pageLength = printHeight;
            int page = PAGE_EXISTS;

            // check if past the end of the doc
            if (pageIndex >= numPages) {
                page = NO_SUCH_PAGE;
            }else{

//System.err.println("PagedXML:print pageid "+pageIndex+" pf.x "+format.getImageableX()+
//" pf.y: "+format.getImageableY()+" pf.w: "+format.getImageableWidth()+" pf.h: "+format.getImageableHeight());
                // Cast the Graphics object so we can use Java2D operations
                g2 = (Graphics2D)g;

                // the graphics needs to have a clip region that matches the page format
                clipRect = g2.getClip().getBounds();
                if (Math.ceil(clipRect.getX())!=Math.ceil(format.getImageableX())||
                    Math.ceil(clipRect.getY())!=Math.ceil(format.getImageableY())||
                    Math.ceil(clipRect.getWidth())!=Math.ceil(format.getImageableWidth())||
                    Math.ceil(clipRect.getHeight())!=Math.ceil(format.getImageableHeight()))
                { // clip doesn't match page format so set it now
                    g2.setClip(new Rectangle2D.Double(format.getImageableX(), format.getImageableY(),
                            format.getImageableWidth(), format.getImageableHeight()));
                }

                // Translate to accomodate the top and left margins
    /*
    translate(double x, double y)
    This method translates the origin by (x, y) units. This translation is added to
    any prior translations of the Graphics2D context. The units passed to the
    drawing primitives initially represent 1/72nd of an inch, which on a monitor,
    amounts to one pixel. However, on a printer, one unit might map to 4 or 9 pix-els
    (300 dpi or 600 dpi).
    */
                g2.translate(format.getImageableX(), format.getImageableY());

                g2.setPaint(Color.LIGHT_GRAY);
                BasicStroke origStk = (BasicStroke)g2.getStroke();
                // draw a dotted link around imageable area
                BasicStroke stk = new BasicStroke(origStk.getLineWidth(),BasicStroke.CAP_BUTT,
                		BasicStroke.JOIN_ROUND, 1f,new float[]{5f},0f);
                g2.setStroke(stk);
                g2.draw(new Rectangle2D.Double(0,0,format.getImageableWidth(),format.getImageableHeight()));
                g2.setStroke(origStk);
                g2.setPaint(Color.black);
                
                
                
                // Scale the page by the specified scaling factor
    /*
    scale(double x, double y) method applies a linear scaling to the x- and y-axis. Values greater than 1.0
    expand the axis, and values less than 1.0 shrink the axis. A value of -1 for
    xscale results in a mirror image reflected across the x-axis. A yscale value
    of -1 results in a reflection about the y-axis.
    */
                g2.scale(SCALE_FACTOR, SCALE_FACTOR);
    //System.err.println("entry after scale clip "+g2.getClip());

                // Display a page number centered in the area of the top margin.
                // Set a new clipping region so we can draw into the top margin
                // But remember the original clipping region so we can restore it
                if (pageIndex > 0 && printY>=MAX_Y) // 0.5 margin or more is needed to see page number
                {
                    Shape originalClip = g2.getClip();
                    String numString = "- " + (pageIndex+1) + " -";
                    FontRenderContext frc;
                    Rectangle2D numBounds;
                    LineMetrics metrics;
                    Double width, height;
                    Double iprinty = new Double(printY);
                    Double iprintw = new Double(printWidth);

                    // small rectangle set to area above imageable area
                    // but why not use info from pageformat passed in??
                    //g2.setClip(new Rectangle(0, (int)-printY,(int)printWidth, (int)printY));
                    g2.setClip(new Rectangle(0, -iprinty.intValue(),iprintw.intValue(), iprinty.intValue()));
                    // Compute the header to display, measure it, then display it
                    // Get string and font measurements
                    frc = g2.getFontRenderContext();
                    numBounds = HEADER_FONT.getStringBounds(numString, frc);
                    metrics = HEADER_FONT.getLineMetrics(numString, frc);
                    g2.setFont(HEADER_FONT);    // Set the font
                    g2.setColor(Color.black);  // Print with black ink
                    width = new Double((printWidth-numBounds.getWidth())/2);
                    height = new Double(-(printY-numBounds.getHeight())/2 + metrics.getAscent());
                    g2.drawString(numString,   // Display the string
                        width.intValue(),//(int)((printWidth-numBounds.getWidth())/2),
                        height.intValue());//(int)(-(printY-numBounds.getHeight())/2 + metrics.getAscent()));
                    g2.setClip(originalClip);  // Restore the clipping region
                }

                // Get the staring position within the document
                if (pageIndex > 0) {
                    pageStart = ((Double)pageOffsets.get(pageIndex-1)).doubleValue();
                }

                // Scroll so that the appropriate part of the document is lined up
                // with the upper-left corner of the specified page
                g2.translate(0.0, -pageStart);

                // Now paint the entire document.  Because of the clipping region,
                // only the desired portion of the document will actually be drawn on
                // this sheet of paper.
                rootView.paint(g2, drawRect); // Render a portion of the model.
            }
            return page;//PAGE_EXISTS;
        }
    }

    /*************************************************************************************
     * This is used as the parent of the root View object to print
     * (prevents null ptr in view.paint())
     **/
    private static class DummyView extends View implements XMLDereferencible
    {
        private ViewFactory viewFactory; // The ViewFactory for the hierarchy of views
        private Container container;     // The Container for the hierarchy of views

        DummyView(View v, ViewFactory vf, Container c)
        {
            super(v.getElement());
            viewFactory = vf;
            container = c;
        }

        /*********************************************************************
        * release memory
        */
        public void dereference()
        {
            viewFactory = null;
            container = null;
        }

        // these methods return key pieces of information required by
        // the View hierarchy.
        public ViewFactory getViewFactory() {
            return viewFactory;
        }
        // The coordinate system is the same as the hosting Component (i.e. the Component
        // returned by the getContainer method). This means a child view lives in the same
        // coordinate system as the parent view unless the parent has explicitly changed
        // the coordinate system.
        public Container getContainer() {
            return container;
        }

        // these methods are abstract in View
        public void paint(Graphics g, Shape allocation) {}
        public float getPreferredSpan(int axis) {
            return 0.0f;
        }
        public int viewToModel(float x,float y,Shape a,Position.Bias[] bias) {
            return 0;
        }
        public Shape modelToView(int pos, Shape a, Position.Bias b)
            throws BadLocationException {
            return a;
        }
    }
}
