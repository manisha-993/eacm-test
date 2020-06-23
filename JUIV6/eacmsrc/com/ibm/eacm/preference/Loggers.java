//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.preference;
 
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.KeyEvent;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
 
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.AlwaysLevel;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Utils;
 
 
/**
 * this class manages package logging levels
 * @author Wendy Stimpson
 */
//$Log: Loggers.java,v $
//Revision 1.2  2014/06/20 18:14:07  wendy
//list log levels
//
//Revision 1.1  2012/09/27 19:39:15  wendy
//Initial code
//
class Loggers extends JPanel implements EACMGlobals {
    private static final long serialVersionUID = 1L;
 
    /**
     * column headers used in jtable 
logging.loggers.name=Logger Name
logging.loggers.level=Logging Level
logging.loggers.desc=Description
     */
    private static final String[] LOGGER_HEADERS = new String[]{
    	Utils.getResource("logging.loggers.name"),
    	Utils.getResource("logging.loggers.level"),
    	Utils.getResource("logging.loggers.desc")
    };
    // com.ibm.eacm is the application level and handled on the generalloginfo panel
    // description is in the eacmRsrc.properties file
    private static final String[] LOGGER_NAMES = new String[]{
        "com.ibm.eacm.cart",
        "com.ibm.eacm.edit",
        "com.ibm.eacm.mtrx",
        "com.ibm.eacm.mw",
        "com.ibm.eacm.nav",
        "com.ibm.eacm.wused",
        "com.ibm.eacm.timing"
    };
    
    private PrefMgr prefMgr;
    private EACMAction saveAction;
    private LoggerTable loggerTable;
    private JScrollPane jsp;
    
    /**
     * list the current logger levels
     */
    protected static void listLogLevels(){
        for(int i=0;i<LOGGER_NAMES.length; i++){
        	Level defaultlvl = Level.INFO;
        	if(LOGGER_NAMES[i].equals(TIMING_LOGGER)){
        		defaultlvl = Level.OFF;
        	}

        	Logger.getLogger(APP_PKG_NAME).log(AlwaysLevel.ALWAYS, Utils.getResource(LOGGER_NAMES[i])+": level: "+
            		PrefMgr.getLoggerLevel(LOGGER_NAMES[i],defaultlvl));
        }
    }
    /**
     *  constructor
     * @param pm
     * @param save
     */
    Loggers(PrefMgr pm, EACMAction save){
        super(new BorderLayout());
        prefMgr = pm;
        saveAction = save; 
        loggerTable = new LoggerTable();
        
        jsp = new JScrollPane(loggerTable);
        add(jsp,BorderLayout.CENTER);
        
        jsp.setPreferredSize(loggerTable.getPreferredSize());
    }
    /**
     *  get the title for the tab
     * @return
     */
    String getTitle(){
        //logging.loggers = Loggers
        return Utils.getResource("logging.loggers");
    }
    /**
     *  did user make any changes
     * @return
     */
    boolean hasChanges(){
        return loggerTable.hasChanges();
    }
    
    /**
     *  save the preferences
     */
    void save(){
        loggerTable.save();
    }
    /**
     *  reset to system settings
     */
    void resetDefaults(){
        loggerTable.resetDefaults();
        repaint();
    }
    
    /**
     *  reload with updated preferences
     */
    void updateFromPrefs(){
        loggerTable.updateFromPrefs();
        repaint();
    }
    
    /**
     *  release memory
     */
    void dereference(){
        prefMgr = null;
        saveAction = null;
        
        jsp.removeAll();
        jsp.setUI(null);
        jsp = null;
        
        loggerTable.dereference();
        loggerTable = null;
    }
    
    private class MWLoggerItem extends LoggerItem{
        MWLoggerItem(String n, Level l, String d){
        	super(n,l,d);
        }
        
        void save(){
        	super.save();
        	RMIMgr.setMwDebugLevel();
        }
        void resetDefaults(){
        	super.resetDefaults();
          	RMIMgr.setMwDebugLevel();
        }
    }
    /**
     *  this class is used for one row in the table and has all information
     * for one logger
     */
    private class LoggerItem {
        private String name;
        private Level level,origLevel;
        private String desc;
 
        /**
         *  constructor
         * @param n
         * @param l
         * @param d
         */
        LoggerItem(String n, Level l, String d){
            name = n;
            origLevel = level = l;
            desc = d;
        }
        /**
         *  release memory
         */
        void dereference(){
            name = null;
            level = null;
            desc = null;
        }
        /**
         *  was the level changed?
         * @return
         */
        boolean hasChanges(){
            return !origLevel.equals(level);
        }
        
        /**
         *  set the level in the preferences, flush is needed for persistence
         * flush is done when dialog closes
         */
        void save(){
            Logger.getLogger(name).setLevel(level);
            prefMgr.getPrefNode().put(name, level.getName());
            origLevel = level;
        }
        /**
         *  reset to system settings
         */
        void resetDefaults(){
            origLevel = level = Level.INFO;
            prefMgr.getPrefNode().put(name, level.getName());
            Logger.getLogger(name).setLevel(level);    
        }
        /**
         *  reload with updated preferences
         */
        void updateFromPrefs(){
            origLevel = level = PrefMgr.getLoggerLevel(name,Level.INFO);
            Logger.getLogger(name).setLevel(level);
        }
        /**
         *  get the value
         * @param columnIndex
         * @return
         */
         Object getValueAt(int columnIndex){
             switch(columnIndex){
             case 0:
                 return name;
             case 1:
                 return level;
             case 2:
                 return desc;
             }
             return null;
         }
         /**
          *  set the value
          * @param aValue
          * @param col
          */
         void setValueAt(Object aValue, int col){
             switch(col){
             case 0:
                 name = aValue.toString();
                 break;    
             case 1:
                 level = (Level)aValue;
                 break;
             case 2:
                 desc = aValue.toString();
                 break;
             }
         }
    }
    /**
     *  table used for all loggers except the application root logger
     *
     */
    private class LoggerTable extends JTable {
        private static final long serialVersionUID = 1L;
        private JComboBox levelCombo = null;
        private Vector<LoggerItem> loggersVct = new Vector<LoggerItem>();
 
        /**
         *  constructor
         */
        LoggerTable(){
            loadLoggers();
            setModel(new LogTableModel());
            levelCombo = new JComboBox(){
                private static final long serialVersionUID = 1L;
 
                /* (non-Javadoc)
                 *  allow escape to terminate the edit
                 * @see javax.swing.JComboBox#processKeyEvent(java.awt.event.KeyEvent)
                 */
                public void processKeyEvent(KeyEvent _ke) {
                    if (isPopupVisible()&& (_ke.getKeyCode()==KeyEvent.VK_ESCAPE || _ke.getKeyChar()=='\u001b')){
                        //cancel editing
                        cancelCurrentEdit();
                        _ke.consume();
                        return;
                    }
                    super.processKeyEvent(_ke);
                }
            };
            levelCombo.setModel(new DefaultComboBoxModel());
            levelCombo.setEditable(false);
            LoggingPrefMgr.addLogLevels(levelCombo, null);
            
            DefaultCellEditor tce = new DefaultCellEditor(levelCombo);
            tce.setClickCountToStart(2);
 
            TableColumnModel tcm = getColumnModel();
            tcm.getColumn(1).setCellEditor(tce);
            
            setFillsViewportHeight(true);
            setAutoCreateRowSorter(true);  // allow to be sorted, default sorter is ok
            setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            resizeCells();
        }
        /* (non-Javadoc)
         * @see javax.swing.JTable#setCellEditor(javax.swing.table.TableCellEditor)
         */
        public void setCellEditor(TableCellEditor anEditor) {
            super.setCellEditor(anEditor);
            if(anEditor !=null){
                // if opened with keystrokes, editor was not getting focus
                ((DefaultCellEditor)anEditor).getComponent().requestFocusInWindow();
            }
        }
        /**
         *  look at data widths and set size of cell
         *  
         */
        void resizeCells() {
            Font fnt = getFont();
            FontMetrics fmt = getFontMetrics(fnt);
 
            // find widest col width for each row and set it
            for (int c=0;c<getColumnCount();++c) {
                // get header width
                int width = getColWidth(fmt, getColumnName(c)); 
                
                // find max width in all rows for this column
                for (int r=0; r<getRowCount();++r) {
                    Object o = getValueAt(r,c); // base class gets value from model after converting indexes
                    width = Math.max(width,getColWidth(fmt,o));
                }
                
                TableColumn tc = getColumnModel().getColumn(c);
                tc.setWidth(width);
                tc.setPreferredWidth(width);
            }
        }
 
        /**
         * get the width needed for this object
         * @param fm
         * @param o
         * @return
         */
        int getColWidth(FontMetrics fm, Object o) {
            int w = MIN_COL_WIDTH;
            if (o != null) {
                 w = fm.stringWidth(o.toString());
            }
            return w;
        }
        /**
         *  load all loggers
         */
        private void loadLoggers(){
            for(int i=0;i<LOGGER_NAMES.length; i++){
            	Level defaultlvl = Level.INFO;
            	if(LOGGER_NAMES[i].equals(TIMING_LOGGER)){
            		defaultlvl = Level.OFF;
            	}
            	LoggerItem li;
            	if(LOGGER_NAMES[i].equals(MW_PKG_NAME)){
                 	li = new MWLoggerItem(LOGGER_NAMES[i],
                            PrefMgr.getLoggerLevel(LOGGER_NAMES[i],defaultlvl),
                            Utils.getResource(LOGGER_NAMES[i]));
            	}else{
                 	li = new LoggerItem(LOGGER_NAMES[i],
                            PrefMgr.getLoggerLevel(LOGGER_NAMES[i],defaultlvl),
                            Utils.getResource(LOGGER_NAMES[i]));
            	}
            	
                loggersVct.add(li);
            }
        }
        
        /**
         *  did user make any changes
         * @return
         */
        boolean hasChanges(){
            boolean chgs = false;
            for(int i=0;i<loggersVct.size(); i++){
                LoggerItem li = loggersVct.elementAt(i);
                if(li.hasChanges()){
                    chgs = true;
                    break;
                }
            }
     
            return chgs;
        }
        /**
         *  save the preferences
         */
        void save(){
            saveCurrentEdit();
            for(int i=0;i<loggersVct.size(); i++){
                LoggerItem li = loggersVct.elementAt(i);
                li.save();
            }
        }
        /**
         *  reset to system settings
         */
        void resetDefaults(){
            cancelCurrentEdit();
            for(int i=0;i<loggersVct.size(); i++){
                LoggerItem li = loggersVct.elementAt(i);
                li.resetDefaults();
            }
        }
        
        /**
         *  reload with updated preferences
         */
        void updateFromPrefs(){
            cancelCurrentEdit();
            for(int i=0;i<loggersVct.size(); i++){
                LoggerItem li = loggersVct.elementAt(i);
                li.updateFromPrefs();
            }
        }
        /**
         *  release memory
         */
        void dereference(){
            cancelCurrentEdit();
            
            TableColumnModel tcm = getColumnModel();
            tcm.getColumn(1).setCellEditor(null);
            
            levelCombo.removeAllItems();
            levelCombo.setUI(null);
            levelCombo.removeAll();
            levelCombo = null;
            
            for(int i=0;i<loggersVct.size(); i++){
                LoggerItem li = loggersVct.elementAt(i);
                li.dereference();
            }
            loggersVct.clear();
            loggersVct = null;
            
            removeAll();
            setUI(null);
        }
        
        /**
         *  make sure current edit ends and is put into the table model
         */
        void saveCurrentEdit(){
            TableCellEditor tce = getCellEditor();
            if (tce != null) {
                tce.stopCellEditing();
            }
        }
        /**
         *  make sure current edit ends and is discarded
         */
        void cancelCurrentEdit(){
            TableCellEditor tce = getCellEditor();
            if (tce != null) {
                tce.cancelCellEditing();
            }
        }
        
        /**
         *  table model representing the event results
         *
         */
        private class LogTableModel extends javax.swing.table.DefaultTableModel { 
            private static final long serialVersionUID = 1L;
 
            
            /* (non-Javadoc)
             * @see javax.swing.table.DefaultTableModel#setValueAt(java.lang.Object, int, int)
             */
            public void setValueAt(Object aValue, int row, int column){
                LoggerItem fi = loggersVct.elementAt(row);
                fi.setValueAt(aValue, column);
                fireTableCellUpdated(row, column);
                saveAction.setEnabled(true);//it will check
            }
            
            /* (non-Javadoc)
             * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
             */
            public boolean isCellEditable(int rowIndex, int columnIndex){
                return columnIndex==1;
            }
            /* (non-Javadoc)
             * @see javax.swing.table.DefaultTableModel#getRowCount()
             */
            public int getRowCount() {
                return loggersVct.size();
            }
 
            /* (non-Javadoc)
             * @see javax.swing.table.DefaultTableModel#getColumnCount()
             */
            public int getColumnCount() {
                return LOGGER_HEADERS.length;
            }
 
            /* (non-Javadoc)
             * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
             */
            public Object getValueAt(int rowIndex, int columnIndex) {
                LoggerItem fi = loggersVct.elementAt(rowIndex);
                return fi.getValueAt(columnIndex);
            }
            
            /* (non-Javadoc)
             * @see javax.swing.table.DefaultTableModel#getColumnName(int)
             */
            public String getColumnName(int columnIndex) {
                return LOGGER_HEADERS[columnIndex];
            }
        }
    }
}