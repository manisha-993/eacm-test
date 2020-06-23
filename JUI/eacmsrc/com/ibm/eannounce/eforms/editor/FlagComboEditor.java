// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2008 All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
 *
 * $Log: FlagComboEditor.java,v $
 * Revision 1.5  2012/04/05 18:09:17  wendy
 * jre142 and win7 changes
 *
 * Revision 1.4  2008/02/20 18:32:34  wendy
 * Add mouselistener to popup box when already have focus
 *
 * Revision 1.3  2008/02/19 16:36:37  wendy
 * Fix enhanced flag support
 *
 * Revision 1.2  2008/01/30 16:27:05  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:34  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:13  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:57  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2005/09/08 17:59:03  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.2  2005/05/20 16:48:20  tony
 * refined single flag enhancement logic.
 *
 * Revision 1.1  2005/05/19 20:42:24  tony
 * singleFlag UI Enhancement.
 *
 */
package com.ibm.eannounce.eforms.editor;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.plaf.basic.*;
import javax.swing.text.*;
import javax.swing.*;

import com.elogin.EAccess;
import com.elogin.EAccessConstants;
import com.ibm.eannounce.eforms.editform.FormEditorInterface;

/**
 * This class is instantiated when EAccess.isArmed(ENHANCED_FLAG_EDIT) to allow look ahead on
 * flag attributes.  It is used to allow user to enter characters that select and highlight
 * matching flag values in the FlagEditor combo box.
 */
public class FlagComboEditor extends JTextField implements ComboBoxEditor, EAccessConstants,
MouseListener 
{
	private static final long serialVersionUID = 1L;

	private JComboBox box = null;
	private  Object value = null;
	private  Object orig = null;
	private  String strPredict = null;
	private  boolean bValid = false;

	private Color cFore = null;
	private Color cBack = null;
	
	/**
     * flagComboEditor
     *
     * @param _box
     */
    public FlagComboEditor(JComboBox _box) {
		super("",9);
		setBorder(null);
		box = _box;	
		
		addMouseListener(this);		
	}
    /**
     * This is used to get the FlagEditor (EditorInterface) to track focus events in EditForm
     * @return FlagEditor
     */
    public FlagEditor getFlagEditor() {
    	 if(box instanceof FlagEditor){
    		 return (FlagEditor)box;
    	 }
    	 return null;
    }
    /**
     * Force this component to display the busy cursor when other operations are occurring
     * but this doesnt stop user from doing anything else
     */
    public Cursor getCursor(){
    	return EAccess.eaccess().getCursor();
    }

    /**
     * @see javax.swing.JTextField#createDefaultModel()
     */
    protected Document createDefaultModel() {
		return new MaxDoc();
	}

	/**
     * @see javax.swing.JComponent#updateUI()
     */
    public void updateUI() {
		BasicTextFieldUI ui = new BasicTextFieldUI() {
			protected void paintSafely(Graphics _g) {
				super.paintSafely(_g);
				paintPrediction(_g);
			}
		};
		setUI(ui);
		invalidate();
	}

	private class MaxDoc extends PlainDocument {
		private static final long serialVersionUID = 1L;
		public void insertString(int _offs, String _str, AttributeSet _a) throws BadLocationException {
			if (valid(_str)) {
				super.insertString(_offs, _str, _a);
			}
        }

        public void remove(int _offs, int _len) throws BadLocationException {
            super.remove(_offs,_len);
            valid("");
        }

		private boolean valid(String _newstr) {
            String curstr = "";
            if (bValid) {
				return true;
			}
			try {
				curstr = getText(0,getLength());
			} catch(BadLocationException _ble) {
				_ble.printStackTrace();
			}
			return select(curstr, _newstr);
		}
    }

    /**
     * select combobox index and paint lookahead string in textfield
     *
     * @param _curStr String with current contents
     * @param _newStr String to insert
     * @return boolean if true insert into document
     */
    private boolean select(String _curStr, String _newStr) {
		if (box != null) {
			String str = _curStr + _newStr;
			if (str.length() > 0) {
				int ii = box.getItemCount();
				if (ii > 0) {
					for (int i = 0;i<ii;++i) {
						Object o = box.getItemAt(i);
						if (o != null) {
							String tmp = o.toString().toLowerCase();
							if (tmp.startsWith(str.toLowerCase())) {
								value = o;
								strPredict = o.toString().substring(str.length());
								box.hidePopup(); // win7 cant handle scrolling to keystroke
								//box.setSelectedIndex(i); // this ends up commiting the change so prediction is lost
								box.setSelectedItem(o); // allow cascade flags to work like LSCCLIFECYCLE
								box.showPopup();
								return true;
							}
						}
					}
				}
			} else {
				strPredict = null;
				if (orig != null) {
					int i = indexOf(orig);
					if (i >= 0) {
						if (box.isShowing()) {
							box.hidePopup(); // win7 cant handle scrolling to keystroke
						}
						//box.setSelectedIndex(i);
						box.setSelectedItem(orig);
						if (box.isShowing()) {
							box.showPopup();
						}
					}
				}
			}
		}
		return false;
	}

	/**
     * @see java.awt.Component#processKeyEvent(java.awt.event.KeyEvent)
     */
    public void processKeyEvent(KeyEvent _ke) {
		if (_ke != null) {
			requestFocus();
			if (!isMotionKey(_ke)) {
				super.processKeyEvent(_ke);
			}
		}
	}

    /** This is called because this is the editor for the jcombobox, it is called
     * when setSelectedItem(..) is called and when an item is added to the list
     * @see javax.swing.ComboBoxEditor#setItem(java.lang.Object)
     */
    public void setItem(Object _obj) {  	
    	bValid = true;
		if (_obj != null) {
			if (_obj != value) {
				setText("");
				strPredict = null;
			}
		} else {
			setText("");
		}
		bValid = false;
		value = _obj;
    }

	/**
     * setOrig - called by FlagEditor when the attribute is loaded into the editor (refreshed)
     * @param _obj
     */
    public void setOrig(Object _obj) {
		if (_obj!=null && orig == null) {
			orig = _obj;
		}
		value = null;
	}

    /**
     * Called when the edit is cancelled
     *
     */
    public void reset(){
    	setText(""); // must go through validation to clear painted text
    	value=null;
    }
    /**
     * Called by FlagEditor when the attribute is refreshed- reset internal variables
     *
     */
    public void refresh(){ 
    	strPredict = null; 
     	value = null;
		orig = null;   	
    	setText("");
    }

	/**
     * clear the prediction and reset the text without going thru validation
     * Called when edit is complete
     */
    public void accepted() {
    	strPredict = null; 
       	bValid = true;
    	setText("");
    	bValid = false;
    	orig = box.getSelectedItem(); // cover both ways of selecting the value, lookahead or list selection
	}
    /**
     * @see javax.swing.ComboBoxEditor#getItem()
     */
    public Object getItem() {
		if (has(getText())) {
			return value;
		}
		return orig;
    }

	/**
     * dereference
     *
     */
    public void dereference() {
		box = null;
		removeMouseListener(this);
	}

	/**
     * @see javax.swing.ComboBoxEditor#getEditorComponent()
     * @author Anthony C. Liberto
     */
    public Component getEditorComponent() {
		return this;
	}

	/**
     * paintPrediction
     *
     * @author Anthony C. Liberto
     * @param _g
     */
    private void paintPrediction(Graphics _g) {
		String tmp = strPredict;
		String curText = getText();
		FontMetrics fm = _g.getFontMetrics();
		if (has(tmp)) {
			Color cOrig = _g.getColor();

			int x = getWidth(fm,curText);
			int y = 0;
			int h = getHeight();

			_g.setColor(getPredictionBackground());
			_g.fillRect(x,y,getWidth(fm,tmp),h);

			_g.setColor(getPredictionForeground());
			_g.drawString(tmp,x,fm.getAscent() + 1);

			_g.setColor(cOrig);
		} else {
			if (!has(curText)) {
				if (value != null) {
					_g.drawString(value.toString(),0,fm.getAscent() + 1);
				} else if (orig != null) {
					_g.drawString(orig.toString(),0,fm.getAscent() + 1);
				}
			}
		}
	}

	private int getWidth(FontMetrics _fm, String _s) {
		if (_fm != null && _s != null) {
			return _fm.stringWidth(_s);
		}
		return 0;
	}

	/**
     * getValue
     *
     * @return
     */
    public String getValue() {
		return getText() + strPredict;
	}

	/**
     * has
     *
     * @param _s
     * @return
     */
    private boolean has(String _s) {
		return (_s != null && _s.length() > 0);
	}

	/**
     * setPredictionForeground - doesnt seem to be used
     *
     * @param _c
     */
    public void setPredictionForeground(Color _c) {
		cFore = _c;
	}

	/**
     * getPredictionForeground
     *
     * @return
     */
    public Color getPredictionForeground() {
		if (cFore != null) {
			return cFore;
		}
		return Color.black;
	}

    /**
     * getBackground to reflect current state
     */
    public Color getBackground() {    	
    	if (super.isFocusOwner()){
    		return UIManager.getColor("Table.selectionBackground"); 
    	}
   	
    	if (box instanceof FlagEditor) {
    		return ((FlagEditor)box).getBackground();	
    	}
    	return super.getBackground();
    }
    
	/**
     * setPredictionBackground - doesnt seem to be used
     *
     * @param _c
     */
    public void setPredictionBackground(Color _c) {
		cBack = _c;
	}

	/**
     * getPredictionBackground
     *
     * @return
     */
    public Color getPredictionBackground() {
		if (cBack != null) {
			return cBack;
		}
		return Color.yellow;
	}

	private boolean isMotionKey(KeyEvent _ke) {
		if (_ke != null) {
			int iCode = _ke.getKeyCode();
			switch (iCode) {
            case KeyEvent.VK_UP:
				if (box instanceof FlagEditor) {
					((FlagEditor)box).executeListKey(_ke);
				}
                return true;
			case KeyEvent.VK_DOWN:
				if (box instanceof FlagEditor) {
					((FlagEditor)box).executeListKey(_ke);
				}
				return true;
			case KeyEvent.VK_LEFT:
				return true;
			case KeyEvent.VK_RIGHT:
				return true;
			case KeyEvent.VK_ENTER:
				if (box instanceof FlagEditor) {
					((FlagEditor)box).completeEdit();					
				}
				return true;
			case KeyEvent.VK_ESCAPE:
				if (box instanceof FlagEditor) {
					((FlagEditor)box).cancelEdit();
					
				}
				return true;
			default:
				return false;
			}
		}
		return false;
	}

	
	/**
	 * Find index of this object in the JComboBox
	 */
	private int indexOf(Object _o) {
		if (box != null) {
			int ii = box.getItemCount();
			if (ii > 0) {
				for (int i = 0;i<ii;++i) {
					Object o = box.getItemAt(i);
					if (o != null && o == _o) {
                        return i;
					}
				}
			}
		}
		return -1;
	}

	/**
     * preloadKeyEvent
     *
     * @param _ke
     */
    public void preloadKeyEvent(KeyEvent _ke) {
		if (_ke != null) {
			String str = KeyEvent.getKeyText(_ke.getKeyCode());
			if (_ke.isShiftDown()) {
				setText(str.toUpperCase());
			} else {
				setText(str.toLowerCase());
			}
		}
	}
    /**
     * canReceiveFocus
     *
     */
    private boolean canReceiveFocus() {
    	if (box instanceof FlagEditor) {
        FormEditorInterface tmpEf = ((FlagEditor)box).getEditForm();
        if (tmpEf != null) {
            return tmpEf.verifyNewFocus(((FlagEditor)box));
        }
    	}
        return true;
    }
    /**
     * @see java.awt.Component#requestFocus()
     */
    public void requestFocus() {
        if (canReceiveFocus()) {     	
            super.requestFocus();
        }else{   
        	/* cant prevent popup from showing because it is popped up from the InvocationMouseHandler
        	 * after it calls requestFocus
        	 * This entire design is wrong, it should use a documentlistener and validate then
        	 * not by trying to control focus
        	 * 	at com.ibm.eannounce.eforms.editor.MetaUIValidator.generateErrorMessage(MetaUIValidator.java:129)
	at com.ibm.eannounce.eforms.editor.MetaValidator.isDecimalFormatted(MetaValidator.java:1425)
	at com.ibm.eannounce.eforms.editor.MetaValidator.isDecimalFormatted(MetaValidator.java:1369)
	at com.ibm.eannounce.eforms.editor.MetaValidator.canLeave(MetaValidator.java:757)
	at com.ibm.eannounce.eforms.editor.TextEditor.canLeave(TextEditor.java:492)
	at com.ibm.eannounce.eforms.editform.EditForm.verify(EditForm.java:3768)
	at com.ibm.eannounce.eforms.editform.EditForm.verifyNewFocus(EditForm.java:3748)
	at com.ibm.eannounce.eforms.editor.FlagEditor.canReceiveFocus(FlagEditor.java:933)
	at com.ibm.eannounce.eforms.editor.FlagEditor.requestFocus(FlagEditor.java:948)
	at javax.swing.plaf.basic.BasicComboPopup$InvocationMouseHandler.mousePressed(BasicComboPopup.java:657)
	at java.awt.AWTEventMulticaster.mousePressed(AWTEventMulticaster.java:244)
        	 */
			 // do this in a separate thread, later
            SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                    	box.hidePopup();
                    }
            });        	
        }
    }
    
    /**
     * MouseListener interface, need to capture mouse clicks to show popup when already have focus
     */
    public void mousePressed(MouseEvent _me) {}
    public void mouseReleased(MouseEvent _me) {}
    public void mouseEntered(MouseEvent _me) {}
    public void mouseExited(MouseEvent _me) {}
    public void mouseDragged(MouseEvent _me) {}
    public void mouseClicked(MouseEvent _me) { 
    	if (_me.getClickCount() == 2){
    		if (!box.isPopupVisible()){    	
    			box.showPopup();
    		}
    	}
    }    
}
