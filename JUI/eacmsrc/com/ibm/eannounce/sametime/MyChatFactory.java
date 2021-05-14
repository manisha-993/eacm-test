/*
 * Created on Jan 26, 2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package com.ibm.eannounce.sametime;
import com.elogin.EAccess;
import com.lotus.sametime.chatui.*;
import com.lotus.sametime.core.comparch.STSession;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class MyChatFactory extends DefaultChatFactory implements ActionListener {
    /**
     * top
     */
    private Panel top = null;
    /**
     * center
     */
    private Panel center = null;
    /**
     * bottom
     */
    private Panel bottom = null;
    /**
     * model
     */
    protected ChatModel model = null;

    /**
     * Constructor
     * @param _session
     * @author Anthony C. Liberto
     */
    public MyChatFactory(STSession _session) {
        super(_session);
        return;
    }

    /**
     * @see com.lotus.sametime.chatui.DefaultChatFactory#getCustomizedPanels(int, com.lotus.sametime.chatui.ChatFrame)
     * @author Anthony C. Liberto
     */
    public Panel getCustomizedPanels(int _pos, ChatFrame _frame) {
        if (_pos == TOP_PANEL) {
            customize(_frame);
            return getTopPanel();
        } else if (_pos == CENTER_PANEL) {
            return getCenterPanel();
        } else if (_pos == BOTTOM_PANEL) {
            return getBottomPanel();
        }
        return null;
    }
    /**
     * customize
     * @param _frame
     * @author Anthony C. Liberto
     */
    protected void customize(ChatFrame _frame) {
        //System.out.println("customizing frame");
        if (_frame != null) {
            loadModel(_frame.getChatModel());
        } else {
            loadModel(null);
        }
        return;
    }
    /**
     * getTopPanel
     * @return
     * @author Anthony C. Liberto
     */
    protected Panel getTopPanel() {
        if (top == null) {
        }
        return top;
    }
    /**
     * getCenterPanel
     * @return
     * @author Anthony C. Liberto
     */
    protected Panel getCenterPanel() {
        if (center == null) {
        }
        return center;
    }
    /**
     * getBottomPanel
     * @return
     * @author Anthony C. Liberto
     */
    protected Panel getBottomPanel() {
        if (bottom == null) {
        }
        return bottom;
    }

    /**
     * getCustomMenu
     * @see com.lotus.sametime.chatui.DefaultChatFactory#getCustomizedMenu(java.awt.MenuBar)
     * @author Anthony C. Liberto
     */
    public void getCustomizedMenu(MenuBar _mb) {
        return;
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
        return;
    }

    /**
     * @see com.lotus.sametime.chatui.ChatFactory#createView(com.lotus.sametime.chatui.ChatModel, java.lang.String, boolean)
     * @author Anthony C. Liberto
     */
    public void createView(ChatModel _model, String _partner, boolean _origin) {
        super.createView(_model, _partner, _origin);
        return;
    }

    /**
     * @see com.lotus.sametime.chatui.DefaultChatFactory#messageReceived(com.lotus.sametime.chatui.ChatMessage, boolean)
     * @author Anthony C. Liberto
     */
    public void messageReceived(ChatMessage _msg, boolean _beep) {
        super.messageReceived(_msg, _beep);
        return;
    }

    /**
     * loadModel
     * @param _model
     * @author Anthony C. Liberto
     */
    protected void loadModel(ChatModel _model) {
        //System.out.println("loading model");
        model = _model;
        return;
    }

    /**
     * toByte[]
     * @param _o
     * @return
     * @author Anthony C. Liberto
     */
    protected byte[] toByte(Object _o) {
        byte[] data = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;;

        if (_o instanceof byte[]) {
            return (byte[]) _o;
        }

        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(_o);
            oos.reset();			//JTest
            oos.flush();
            data = bos.toByteArray();
        } catch (IOException _ioe) {
            _ioe.printStackTrace();
        } finally {
			try {
				if (oos != null) {
					oos.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (Exception _x) {
				EAccess.report(_x,false);
			}
		}
        return data;
    }

    /**
     * toObject
     * @param _byte
     * @return
     * @author Anthony C. Liberto
     */
    protected Object toObject(byte[] _byte) {
        Object o = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;

        if (_byte != null) {
            try {
                bis= new ByteArrayInputStream(_byte);
                ois = new ObjectInputStream(bis);
                o = ois.readObject();
            } catch (ClassNotFoundException _cnf) {
                _cnf.printStackTrace();
            } catch (InvalidClassException _ice) {
                _ice.printStackTrace();
            } catch (StreamCorruptedException _sce) {
                _sce.printStackTrace();
            } catch (OptionalDataException _ode) {
                _ode.printStackTrace();
            } catch (IOException _ioe) {
                _ioe.printStackTrace();
            } finally {
				try {
					if (ois != null) {
						ois.close();
					}
					if (bis != null) {
						bis.close();
					}
				} catch (Exception _x) {
					EAccess.report(_x,false);
				}
			}
        }
        return o;
    }
}
