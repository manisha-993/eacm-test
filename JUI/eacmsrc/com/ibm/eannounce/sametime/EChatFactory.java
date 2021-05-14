/*
 * Created on Jan 26, 2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package com.ibm.eannounce.sametime;
import com.lotus.sametime.core.comparch.STSession;
import com.lotus.sametime.places.*;
import java.awt.event.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EChatFactory extends MyChatFactory implements ActionListener {
//    private JButton sndData = null;
    /**
     * create a new eChatFactory from a Session
     * @param _session
     * @author Anthony C. Liberto
     */
    public EChatFactory(STSession _session) {
        super(_session);
        return;
    }

    /*
    	protected Panel getCenterPanel() {
    		if (center == null) {
    			center = new Panel(new BorderLayout());
    			sndData = new JButton("Send Data");
    			sndData.setMnemonic('d');
    			sndData.setActionCommand("data");
    			sndData.addActionListener(this);
    			center.add("West",sndData);
    		}
    		return center;
    	}
    */
    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
        String action = _ae.getActionCommand();
        if (action.equals("data")) {
            sendData();
        }
        return;
    }

    /**
     * send Data across the pipe
     *
     * @author Anthony C. Liberto
     */
    public void sendData() {
        //System.out.println("send data 00");
        if (model != null) {
            //System.out.println("send data 01");
            Place plc = model.getPlace();
            //System.out.println("send data 02");
            if (plc != null) {
                //System.out.println("send data 03");
                plc.sendData(0, getByteArray());
            }
            //System.out.println("send data 04");
        }
        //System.out.println("send data 05");
        return;
    }
    /**
     * getByteArray
     *
     * @return byte array
     * @return
     * @author Anthony C. Liberto
     */
    public byte[] getByteArray() {
        //System.out.println("send Data: 03a");
        return toByte("test");
    }
}
