//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;

import COM.ibm.eannounce.objects.RowSelectableTable;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.eacm.table.SearchTable;

/**************
 * 
 * this is used for displaying a search editor 
 * @author Wendy Stimpson
 */
public class SearchEditor extends JPanel implements KeyListener {
	private static final long serialVersionUID = 1L;
	private SearchTable table = null;
	private JScrollPane scroll = null;
 
    /**
     * SearchEditor, it doesnt have an entitylist, just an rst
     * @param prof
     * @param rst
     * @param srchkey
     */
    public SearchEditor(Profile prof, RowSelectableTable rst,String srchkey) { 
        super(new BorderLayout());
        
        table = new SearchTable(prof, rst,srchkey);
        
		scroll = new JScrollPane(table);
		
		Dimension scrollDim = scroll.getPreferredSize();
		Dimension tableDim = table.getPreferredSize();
		int prefwidth=scrollDim.width;
		if(scrollDim.width<tableDim.width){
			prefwidth = tableDim.width;
		}
		scrollDim.setSize(prefwidth+23, scrollDim.height);
		
		//scroll.setBorder(titleBorder);
		scroll.setSize(scrollDim);
		scroll.setPreferredSize(scrollDim);
		scroll.setMinimumSize(UIManager.getDimension("eannounce.minimum"));
		add(scroll,BorderLayout.CENTER);
		
		table.addKeyListener(this); // capture delete key and use on all selected cells
    } 
    
    public void setEnabled(boolean enabled) {
    	super.setEnabled(enabled);
    	table.setEnabled(enabled);
    }
    /**
     * called by SearchFrame find action
     * @return
     */
    public boolean canStopEditing() { 
        return table.canStopEditing(); 
    } 
    /**
     * get table used by the search editor, needed to add listeners to it, etc
     * @return
     */
    public SearchTable getTable(){
    	return table;
    }

    /**
     * dereference
     *
     */
    public void dereference() {
    	table.removeKeyListener(this);
    	table.dereference();
    	table = null;
    	
    	scroll.removeAll();
    	scroll.setUI(null);
    	scroll = null;
    	
    	removeAll();
    	setUI(null);
    }
    
    /**
     * called by the resetall action in searchframe
     *
     * rollback
     */
    public void rollback() {
        table.cancelCurrentEdit(); 
     
        table.rollback();
    }

	/* (non-Javadoc)
	 * listen for delete key, without this if multiple cells are selected only the last one gets the delete key
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if(KeyEvent.VK_DELETE == e.getKeyCode()){
			int rows[] = table.getSelectedRows();
			int[] cols = table.getSelectedColumns();
			// use cell editor to delete the attribute
			for (int viewRowid = 0; viewRowid < rows.length; ++viewRowid) {
				for (int viewColid = 0; viewColid < cols.length; ++viewColid) {
					TableCellEditor editor = table.getCellEditor(rows[viewRowid], cols[viewColid]);
					if (editor != null) {
						editor.isCellEditable(e); // this does the delete
					}
				}
			}
			e.consume();
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}

