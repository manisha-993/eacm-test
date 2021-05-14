/*
 * Created on Jan 26, 2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package com.ibm.eannounce.sametime;
import com.lotus.sametime.chatui.*;
import com.lotus.sametime.core.comparch.STSession;
import com.elogin.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EancChatFactory extends MyChatFactory implements EAccessConstants {
    /**
     * Constructor
     * @param _session
     * @author Anthony C. Liberto
     */
    public EancChatFactory(STSession _session) {
        super(_session);
        return;
    }

    /**
     * eaccess
     * @return
     * @author Anthony C. Liberto
     */
    public static EAccess eaccess() {
        return EAccess.eaccess();
    }

    /**
     * @see com.lotus.sametime.chatui.DefaultChatFactory#messageReceived(com.lotus.sametime.chatui.ChatMessage, boolean)
     * @author Anthony C. Liberto
     */
    public void messageReceived(ChatMessage _msg, boolean _beep) {
        super.messageReceived(_msg, _beep);
        return;
    }
}
