/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: EFlagList.java,v $
 * Revision 1.2  2008/01/30 16:26:56  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:47  wendy
 * Reorganized JUI module
 *
 * Revision 1.3  2005/10/13 20:13:22  tony
 * TIR USRO-R-NCBE-6H5R9A
 *
 * Revision 1.2  2005/09/12 19:03:17  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:16  tony
 * This is the initial load of OPICM
 *
 * Revision 1.13  2005/09/08 17:59:09  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.12  2005/07/13 15:14:06  tony
 * TIR USRO-R-NMHR-6E9HFX
 *
 * Revision 1.11  2005/05/26 20:53:23  tony
 * mods
 *
 * Revision 1.10  2005/05/26 16:27:28  tony
 * compile
 *
 * Revision 1.9  2005/05/26 16:25:17  tony
 * update cr mods
 *
 * Revision 1.8  2005/05/25 21:33:36  tony
 * functional improvement
 *
 * Revision 1.7  2005/05/25 20:59:57  tony
 * multiple flag enhancement
 *
 * Revision 1.6  2005/02/08 16:33:58  tony
 * JTest Formatting Fourth pass
 *
 * Revision 1.5  2005/02/04 18:16:49  tony
 * JTest Formatter Fourth Pass
 *
 * Revision 1.4  2005/02/03 21:26:14  tony
 * JTest Format Third Pass
 *
 * Revision 1.3  2005/01/28 19:36:59  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 18:20:08  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.12  2003/11/21 17:10:40  tony
 * cleaned-up code
 *
 * Revision 1.11  2003/10/22 17:14:54  tony
 * 52674
 *
 * Revision 1.10  2003/10/01 16:05:56  tony
 * cleaned-up code
 *
 * Revision 1.9  2003/07/24 21:41:39  tony
 * 51519
 *
 * Revision 1.8  2003/04/18 15:42:42  tony
 * enhnaced multiEditor functionality.
 *
 * Revision 1.7  2003/04/17 23:13:25  tony
 * played around with editing Colors
 *
 * Revision 1.6  2003/03/21 20:54:33  tony
 * updated getBackground, getForeground, and getFont logic.
 * added logic so that each can be set for the component.
 *
 * Revision 1.5  2003/03/18 22:39:12  tony
 * more accessibility updates.
 *
 * Revision 1.4  2003/03/12 23:51:18  tony
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
 * Revision 1.5  2002/11/07 16:58:16  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import com.ibm.eannounce.ui.EFlagListUI;
import com.ibm.eannounce.eforms.editor.MultiEditor;
import COM.ibm.eannounce.objects.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EFlagList extends EList2 implements KeyListener, EAccessConstants {
	private static final long serialVersionUID = 1L;
	private Vector data = new Vector();
    private MultiEditor editor = null;

/*
 cr modified so no longer relevent
    private KeyEvent lastKey = null;
*/
	private StringBuffer sb = null;
    /**
     * eFlagList
     * @author Anthony C. Liberto
     */
    public EFlagList() {
        super();
        setOpaque(false);
        setListData(data);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setCellRenderer(new MyFlagRend());
        addKeyListener(this);
        return;
    }

    /**
     * getKeyListener
     * @return
     * @author Anthony C. Liberto
     */
    public KeyListener getKeyListener() {
        return this;
    }

    /**
     * @see javax.swing.JList#getLeadSelectionIndex()
     * @author Anthony C. Liberto
     */
    public int getLeadSelectionIndex() {
        return getSelectionModel().getLeadSelectionIndex();
    }

    /**
     * acquireFocus
     * @author Anthony C. Liberto
     */
    public void acquireFocus() {
        ListSelectionModel lsm = getSelectionModel();
        requestFocus();
        setSelectionInterval(0, 0);
        lsm.setLeadSelectionIndex(0);
        lsm.setAnchorSelectionIndex(0);
        ensureIndexIsVisible(0);
        return;
    }

    /**
     * setEditor
     * @param _editor
     * @author Anthony C. Liberto
     */
    public void setEditor(MultiEditor _editor) {
        editor = _editor;
    }

    /**
     * getBackground
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Color getBackground() {
        if (editor != null) {
            return editor.getBackground();
        }
        return super.getBackground();
    }

    /**
     * @see javax.swing.JComponent#updateUI()
     * @author Anthony C. Liberto
     */
    public void updateUI() {
		setUI(new EFlagListUI());
        invalidate();
    }

    /**
     * clear
     * @author Anthony C. Liberto
     */
    public void clear() {
        data.clear();
        setListData(data);
        return;
    }

    /**
     * getItemAt
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public Object getItemAt(int _i) {
        return data.get(_i);
    }

    /**
     * removeAt
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public Object removeAt(int _i) {
        return data.remove(_i);
    }

    /**
     * removeItem
     * @param _o
     * @author Anthony C. Liberto
     */
    public void removeItem(Object _o) {
        data.remove(_o);
        return;
    }

    /**
     * addItem
     * @param _o
     * @author Anthony C. Liberto
     */
    public void addItem(Object _o) {
        data.add(_o);
        return;
    }

    /**
     * addItem
     * @param _i
     * @param _o
     * @author Anthony C. Liberto
     */
    public void addItem(int _i, Object _o) {
        data.add(_i, _o);
        return;
    }

    /**
     * getDataSize
     * @return
     * @author Anthony C. Liberto
     */
    public int getDataSize() {
        return data.size();
    }

    /**
     * getData
     * @return
     * @author Anthony C. Liberto
     */
    public Vector getData() {
        return data;
    }

    /**
     * setData
     * @param _v
     * @author Anthony C. Liberto
     */
    public void setData(Vector _v) {
        data = _v;
        setListData(data);
        return;
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        removeKeyListener(this);
        return;
    }

    /*
     * load the flags into the list
     * quick and easy.
     */
    /**
     * load
     * @param _flag
     * @author Anthony C. Liberto
     */
    public void load(MetaFlag[] _flag) {
        int ii = -1;
        data.clear();
        //		Arrays.sort(_flag,new SerialHistoryComparator(true));
        ii = _flag.length;
        for (int i = 0; i < ii; ++i) {
            data.add(_flag[i]);
        }
        setListData(data);
        clearSelection();
        return;
    }

    /**
     * toggle
     * @author Anthony C. Liberto
     */
    public void toggle() {
        int index = getSelectionModel().getLeadSelectionIndex();
        if (index < 0) {
            index = 1;
        }
        toggle(index);
    }

    /**
     * toggle
     * @param _i
     * @author Anthony C. Liberto
     */
    public void toggle(int _i) {
        MetaFlag flag = getFlagAt(_i);
        ListSelectionModel lsm = null;
        int old = -1;
        if (flag.isSelected()) {
            flag.setSelected(false);
        } else {
            flag.setSelected(true);
        }

		if (EAccess.isArmed(ENHANCED_FLAG_EDIT)) {
			if (flag != null && sb != null) {
				String str = flag.toString().toLowerCase();
				String tmp = sb.toString();
				if (!str.startsWith(tmp)) {
					sb.delete(0,sb.length());
					updateText("");
				}
			}
		}

        lsm = getSelectionModel();
        old = lsm.getLeadSelectionIndex();
        if (old != _i) {
            lsm.setLeadSelectionIndex(_i);
            if (old >= 0) {
                paintImmediately(getCellBounds(old, old));
            }
        }
        paintImmediately(getCellBounds(_i, _i));
        return;
    }

    private void toggleAll(boolean _select, boolean _acceptChanges) {
        int ii = data.size();
        for (int i = 0; i < ii; ++i) {
            MetaFlag flag = getFlagAt(i);
            if (flag != null) {
                flag.setSelected(_select);
            }
        }
        if (_acceptChanges) {
            editor.acceptChanges();
        } else {
            paintImmediately(getCellBounds(0, ii - 1));
        }
        return;
    }

    /*
     * should make getting flags a little bit easier
     */
    /**
     * getFlagAt
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public MetaFlag getFlagAt(int _i) {
        Object o = getItemAt(_i);
        if (o instanceof MetaFlag) {
            return (MetaFlag) o;
        }
        return null;
    }

    /*
     * clear selection works a little too well,
     * need to adjust after clear selection call
     * so that flags are displayed properly.
     * This should be called after clearSelection
     * and when cancel is pressed.
     */
    /**
     * resetFlags
     * @author Anthony C. Liberto
     */
    public void resetFlags() {
        int ii = getDataSize();
        for (int i = 0; i < ii; ++i) {
            MetaFlag flag = getFlagAt(i);
            if (flag != null && flag.isSelected()) {
                addSelectionInterval(i, i);
            }
        }
		updateText("");					//USRO-R-NCBE-6H5R9A
		if (sb != null) {				//USRO-R-NCBE-6H5R9A
			sb = new StringBuffer();	//USRO-R-NCBE-6H5R9A
		}								//USRO-R-NCBE-6H5R9A
        return;
    }

    /*
     * scroll the list to the first item on list
     * from your current position that starts with
     * the passed in character
     */
    /*
     51519
    	public void scrollToCharacter(char _c) {
    		if (!Character.isDefined(_c) || Character.isWhitespace(_c)) return;
    		ensureCharacterIsVisible(_c);
    		return;
    	}

    	private void ensureCharacterIsVisible(char _c) {
    		int ii = getDataSize();
    		int index = getSelectionModel().getLeadSelectionIndex();
    		for (int i=(index+1);i<ii;++i) {
    			if (isFlagCharacter(i,_c)) {
    				gotoFlag(i);
    				return;
    			}
    		}
    		for (int i=0;i<=index;++i) {
    			if (isFlagCharacter(i,_c)) {
    				gotoFlag(i);
    				return;
    			}
    		}
    		return;
    	}

    	private void gotoFlag(int _i) {
    		ensureIndexIsVisible(_i);
    		ListSelectionModel lsm = getSelectionModel();
    		int old = lsm.getLeadSelectionIndex();
    		lsm.setLeadSelectionIndex(_i);
    		if (old >= 0)
    			paintImmediately(getCellBounds(old,old));
    		paintImmediately(getCellBounds(_i,_i));
    		return;
    	}

    	private boolean isFlagCharacter(int _i, char _c) {
    		char c = Character.toLowerCase(_c);
    		MetaFlag flag = getFlagAt(_i);
    		if (flag != null) {
    			char flagChar = flag.toString().charAt(0);
    			char lowerChar = Character.toLowerCase(flagChar);
    			if (lowerChar == c)
    				return true;
    		}
    		return false;
    	}
    */
    /*
     * end scroll
     */
    /**
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyTyped(KeyEvent _ke) {
        scrollToCharacter(_ke.getKeyChar());
        return;
    }

    /**
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyPressed(KeyEvent _ke) {
        if (EAccess.isArmed(ENHANCED_FLAG_EDIT)) {
			enhancedKeyPressed(_ke);
		} else {
			originalKeyPressed(_ke);
		}
        return;
    }
    /**
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyReleased(KeyEvent _ke) {

    }

    /*
     * all the default behavior junk that needs to
     * be overridden.
     * At least it is easier than reinventing the
     * wheel.
     */
    private class MyFlagRend extends JCheckBox implements ListCellRenderer {
    	private static final long serialVersionUID = 1L;
    	private boolean focus = false;

        /**
         * flagRend
         * @author Anthony C. Liberto
         */
        private MyFlagRend() {
            setOpaque(true);
        }

        /*
         * I am not sure if any of this focus stuff will
         * work if it doesn't then remove it.  But it is
         * worth a shot, right?
         */
        /**
         * setFocus
         * @param _b
         * @author Anthony C. Liberto
         */
        public void setFocus(boolean _b) {
            focus = _b;
        }

        /**
         * @see java.awt.Component#hasFocus()
         * @author Anthony C. Liberto
         */
        public boolean hasFocus() {
            return focus;
        }

        /**
         * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
         * @author Anthony C. Liberto
         */
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof MetaFlag) {
                setSelected(((MetaFlag) value).isSelected());
            }
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setText(value.toString());
            setFont(list.getFont());
            if (cellHasFocus) {
                setFocus(true);
            } else {
                setFocus(false);
            }
            return this;
        }
	}
    /*
     51519
     */
    /**
     * scrollToCharacter
     * @param _c
     * @author Anthony C. Liberto
     */
    public void scrollToCharacter(char _c) {
        if (!Character.isDefined(_c)) {
            return;
        }
        if (EAccess.isArmed(ENHANCED_FLAG_EDIT)) {
	        if (Character.isSpaceChar(_c)) {
				return;
			}
	        gotoString(_c);
		} else {
			gotoCharacter(_c);
		}
        return;
    }

    private void gotoCharacter(char _c) {
        int ii = getDataSize();
        int iStart = Math.max(0, getSelectionModel().getLeadSelectionIndex()); //52674
        char c = Character.toLowerCase(_c);
        for (int i = iStart; i < ii; ++i) {
            MetaFlag flag = getFlagAt(i);
            if (flag != null) {
                char flagChar = flag.toString().charAt(0);
                char lowerChar = Character.toLowerCase(flagChar);
                if (lowerChar == c) {
                    selectFlag(i);
                    return;
                }
            }
        }
        for (int i = 0; i < iStart; ++i) {
            MetaFlag flag = getFlagAt(i);
            if (flag != null) {
                char flagChar = flag.toString().charAt(0);
                char lowerChar = Character.toLowerCase(flagChar);
                if (lowerChar == c) {
                    selectFlag(i);
                    return;
                }
            }
        }
        return;
    }

    private void selectFlag(int _i) {
        ListSelectionModel lsm = null;
        int old = -1;
//      System.out.println("selectFlag(" + _i + ")");
        ensureIndexIsVisible(_i);
        lsm = getSelectionModel();
        old = lsm.getLeadSelectionIndex();
        lsm.setSelectionInterval(_i, _i); //52674
//USRO-R-NCBE-6H5R9A        super.ensureIndexIsVisible(_i);
        if (old >= 0) {
            paintImmediately(getCellBounds(old, old));
        }
        paintImmediately(getCellBounds(_i, _i));
        super.ensureIndexIsVisible(_i - 1);  //USRO-R-NCBE-6H5R9A
        super.ensureIndexIsVisible(_i);		 //USRO-R-NCBE-6H5R9A
        super.ensureIndexIsVisible(_i + 1);  //USRO-R-NCBE-6H5R9A
        return;
    }

/*
 enhanced
 */
    /**
     * originalKeyPressed
     *
     * @param _ke
     * @author Anthony C. Liberto
     */
    public void originalKeyPressed(KeyEvent _ke) {

        int keyCode = -1;
        if (!editor.isPopupVisible()) {
            editor.showPopup();
            return;
        }
        keyCode = _ke.getKeyCode();
        if (_ke.isControlDown()) {
            if (keyCode == KeyEvent.VK_A) {
                toggleAll(true, _ke.isShiftDown());
            } else if (keyCode == KeyEvent.VK_D) {
                toggleAll(false, _ke.isShiftDown());
            }
        } else {
            if (keyCode == KeyEvent.VK_SPACE) {
                toggle();
            } else if (keyCode == KeyEvent.VK_ENTER) {
                if (editor != null) {
                    editor.acceptChanges();
                }
            } else if (keyCode == KeyEvent.VK_ESCAPE) {
                if (editor != null) {
                    editor.discardChanges();
                }
//TIR USRO-R-NMHR-6E9HFX            } else if (keyCode == KeyEvent.VK_UP) {
//TIR USRO-R-NMHR-6E9HFX				scroll(-1);
//TIR USRO-R-NMHR-6E9HFX			} else if (keyCode == KeyEvent.VK_DOWN) {
//TIR USRO-R-NMHR-6E9HFX				scroll(+1);
			}
        }
    	return;
    }

    /**
     * enhancedKeyPressed
     *
     * @param _ke
     * @author Anthony C. Liberto
     */
    public void enhancedKeyPressed(KeyEvent _ke) {
        int keyCode = -1;
        if (!editor.isPopupVisible()) {
            editor.showPopup();
            return;
        }
        keyCode = _ke.getKeyCode();
        if (_ke.isControlDown()) {
            if (keyCode == KeyEvent.VK_A) {
                toggleAll(true, _ke.isShiftDown());
            } else if (keyCode == KeyEvent.VK_D) {
                toggleAll(false, _ke.isShiftDown());
            }
        } else {
            if (keyCode == KeyEvent.VK_ENTER) {
/*
cr modified so no longer relevent
				if (_ke.isControlDown()) {
					toggle();
				} else if (editor != null) {
					editor.acceptChanges();
				}
*/
				if (editor != null) {
					editor.acceptChanges();
				}
/*
cr modified so no longer relevent
                if (lastKey != null && keyCode == lastKey.getKeyCode()) {
					long lLastWhen = lastKey.getWhen();
					long lThisWhen = _ke.getWhen();
					long lDif = lThisWhen - lLastWhen;
					if (lDif <= getDoubleClickRate()) {
						toggle();
						if (editor != null) {
							editor.acceptChanges();
						}
					} else {
						toggle();
					}
				} else {
	                toggle();
				}
*/
            } else if (keyCode == KeyEvent.VK_ESCAPE) {
                if (editor != null) {
                    editor.discardChanges();
                }
            } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
				delete(sb);
				selectFlag(sb.toString());
				repaint();
//TIR USRO-R-NMHR-6E9HFX			} else if (keyCode == KeyEvent.VK_UP) {
//TIR USRO-R-NMHR-6E9HFX				scroll(-1);
//TIR USRO-R-NMHR-6E9HFX			} else if (keyCode == KeyEvent.VK_DOWN) {
//TIR USRO-R-NMHR-6E9HFX				scroll(+1);
/*
cr modified so no longer relevent
			} else if (keyCode == KeyEvent.VK_LEFT) {
				toggle();
			} else if (keyCode == KeyEvent.VK_RIGHT) {
				toggle();
*/
			} else if (keyCode == KeyEvent.VK_SPACE) {
				toggle();
			}
        }
/*
cr modified so no longer relevent
        lastKey = _ke;
*/
    	return;
    }

/*
	private void scroll(int _i) {
		int i = getSelectedIndex() + _i;
		int size = getDataSize();
		if (i <= 0) {
			i = 0;
		} else if (i >= size) {
			i = size - 1;
		}
		super.setSelectedIndex(i);
		super.ensureIndexIsVisible(i);
		if (EAccess.isArmed(ENHANCED_FLAG_EDIT)) {
			if (sb != null) {
				sb.delete(0,sb.length());
			}
			updateText("");
		}
		return;
	}
*/
	private void gotoString(char _c) {
		char c = Character.toLowerCase(_c);
		String str = null;
        if (sb == null) {
			sb = new StringBuffer();
		}
		if (canAppend(sb,c)) {
			appendChar(sb,c);
			str = sb.toString();
			selectFlag(str);
		}
		return;
	}

	private boolean canAppend(StringBuffer _sb, char _c) {
		if (_sb == null) {
			return canAppend(Character.toString(_c));
		}
		return canAppend(_sb.toString() + Character.toString(_c));
	}

	private boolean canAppend(String _s) {
		int ii = getDataSize();
		for (int i = 0; i < ii; ++i) {
			MetaFlag flag = getFlagAt(i);
			if (flag != null) {
				String str = flag.toString().toLowerCase();
				if (str != null) {
					if (str.startsWith(_s)) {
						return true;
					}
				}
			}
		}
		return false;

	}

	private void selectFlag(String _s) {
		int ii = getDataSize();
		for (int i = 0; i < ii; ++i) {
			MetaFlag flag = getFlagAt(i);
			if (flag != null) {
				String strTest = flag.toString().toLowerCase();
				if (strTest.startsWith(_s)) {
					selectFlag(i);
					return;
				}
			}
		}
		return;
	}

	private StringBuffer appendChar(StringBuffer _sb, char _c) {
		_sb.append(_c);
		updateText(_sb.toString());
		return _sb;
	}

	private StringBuffer delete(StringBuffer _sb) {
		int i = _sb.length() -1;
		if (i >= 0) {
			_sb.deleteCharAt(i);
			updateText(_sb.toString());
		}
		return _sb;
	}

	/**
     * setSelectedIndex
     *
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setSelectedIndex(int _i) {
		if (EAccess.isArmed(ENHANCED_FLAG_EDIT)) {
			return;
		}
		super.setSelectedIndex(_i);
		return;
	}

	/**
     * ensureIndexIsVisible
     *
     * @param _i
     * @author Anthony C. Liberto
     */
    public void ensureIndexIsVisible(int _i) {
		if (EAccess.isArmed(ENHANCED_FLAG_EDIT)) {
			return;
		}
		super.ensureIndexIsVisible(_i);
		return;
	}
/*
 cr modified so no longer relevent
    private long getDoubleClickRate() {
		Object o = Toolkit.getDefaultToolkit().getDesktopProperty("awt.multiClickInterval");
		if (o != null && o instanceof Integer) {
			return ((Integer)o).longValue();
		}
		return -1l;
	}
*/
	/**
     * updateText
     *
     * @param _s
     * @author Anthony C. Liberto
     */
    public void updateText(String _s) {}
}
