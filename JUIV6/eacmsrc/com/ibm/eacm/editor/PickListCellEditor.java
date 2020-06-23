//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.*;
import java.util.EventListener;
import java.awt.event.*;
import com.ibm.eacm.preference.ColorPref;

import COM.ibm.eannounce.objects.MetaTag;
import COM.ibm.eannounce.objects.SimplePicklistAttribute;

/***************
 * 
 * Cell editor for SimplePicklistAttributes 
 * - used in OrderTable for column order meta
 * - used in FilterTable for key and contents columns
 * @author Wendy Stimpson
 */
//$Log: PickListCellEditor.java,v $
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class PickListCellEditor extends DefaultCellEditor
{
	private static final long serialVersionUID = 1L;
	private SimplePicklistAttribute spa=null;

	/**
	 * constructor
	 */
	public PickListCellEditor() {
		super(new PickListCombo());
		setClickCountToStart(2);
	}
	/**
	 * set the attribute to use for this editor execution
	 * @param _ean
	 */
	public void setPickListAttribute(SimplePicklistAttribute _ean) {
		spa = _ean;
		loadMetaTags();
	}
	/**
	 * release memory
	 */
	public void dereference(){
		spa=null;
		// removeallitems() invokes listeners, remove now
	   	EventListener listeners[] = ((JComboBox)getComponent()).getListeners(ActionListener.class);
    	if (listeners!=null){
    		for(int ii=0; ii<listeners.length; ii++) {
    			((JComboBox)getComponent()).removeActionListener((ActionListener)listeners[ii]);
    			listeners[ii]=null;
    		}
    	}
		((JComboBox)getComponent()).removeAllItems();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.DefaultCellEditor#getCellEditorValue()
	 */
	public Object getCellEditorValue() {
		// mark the selection in the SimplePicklistAttribute
		MetaTag selTag = (MetaTag)((JComboBox)getComponent()).getSelectedItem();
		if (selTag != null) {
			selTag.setSelected(true);
		}
		// turn off all the others
		for (int i=0;i< spa.getCount();++i) {
			MetaTag tag = spa.getAt(i);
			if (tag != null && tag != selTag) {
				tag.setSelected(false);
			}
		}
		return spa;
	}
	private void loadMetaTags() {
		((JComboBox)getComponent()).removeAllItems();
		if (spa != null) {
			for (int i=0;i<spa.getCount();++i) {
				MetaTag tag = spa.getAt(i);
				if (tag != null) {
					String desc = tag.getLongDescription();

					if (desc.startsWith("imp:")){ //Implicators start with this, not sure why i have to trim it here 
						tag.putLongDescription(desc.substring(4));
					}

					((JComboBox)getComponent()).addItem(tag);
					if (tag.isSelected()) {
						((JComboBox)getComponent()).setSelectedItem(tag);
					}
				}
			}
		}
	}

	/***************
	 * 
	 * used by the cell editor to display the values
	 *
	 */
    private static class PickListCombo extends JComboBox {
		private static final long serialVersionUID = 1L;
		PickListCombo() {
    		setRenderer(new PickListRenderer());
    	}
    	//must do it like this or List in renderer doesnt have it
        public Color getBackground() {
        	return ColorPref.getOkColor();  
        }    
    }
    
    /*
     * renderer - controls the display of drop down list of values
     */
    private static class PickListRenderer extends JLabel implements ListCellRenderer {
    	private static final long serialVersionUID = 1L;

        private PickListRenderer() {
            setOpaque(true);
        }

        /**
         * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
         */
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            setFont(list.getFont());
           
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
    
            this.setText(value.toString());
            return this;
        }
        /**
         * Overridden for performance reasons.
         */
        public void invalidate() {}

        /**
         * Overridden for performance reasons.
         */
        public void validate() {}

        /**
         * Overridden for performance reasons.
         */
        public void revalidate() {}

        /**
         * Overridden for performance reasons.
         */
        public void repaint(long tm, int x, int y, int width, int height) {}

        /**
         * Overridden for performance reasons.
         */
        public void repaint(Rectangle r) { }

        /**
         * Overridden for performance reasons.
         */
        public void repaint() { }

        /**
         * Overridden for performance reasons.
         */
        protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {	
    	// Strings get interned...
    	if (propertyName=="text"
                    || propertyName == "labelFor"
                    || propertyName == "displayedMnemonic"
                    || ((propertyName == "font" || propertyName == "foreground")
                        && oldValue != newValue
                        && getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey) != null)) {

                super.firePropertyChange(propertyName, oldValue, newValue);
            }
        }

        /**
         * Overridden for performance reasons.
         */
        public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) { }
    }
}
