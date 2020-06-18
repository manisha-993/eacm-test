//
// Copyright (c) 2001-2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
//$Log: PMLog.java,v $
//Revision 1.4  2007/07/31 13:03:46  chris
//Rational Software Architect v7
//
//Revision 1.3  2005/01/27 18:56:30  gregg
//jtest
//
//Revision 1.2  2003/08/07 20:48:15  gregg
//more debug logging
//
//Revision 1.1  2003/08/07 00:41:52  gregg
//initial load
//
//

package COM.ibm.eannounce.pmsync;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Facilitate PM Sync Feed logging
 */
public final class PMLog extends FileWriter {

    private String m_strFileName = null;
    private static final SimpleDateFormat SDF_TIMESTAMP = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS000");
    
    /**
     * constructor
     *
     * @param _strFileName
     * @throws java.io.IOException
     * @author gb
     */
    public PMLog(String _strFileName) throws IOException {
        super(_strFileName,true); 
        m_strFileName = _strFileName;
        
    }
    
    /**
     * For convenience, we will catch any Exception here and output to System.err w/out Throwing anything
     *
     * @param _s 
     */  
    protected void printf(String _s) {
        String strDate = SDF_TIMESTAMP.format(new Date());
        try {
            write(strDate + ":" + _s + "\n");    
            flush();
        } catch(IOException e) {
            System.err.println("Cannot write to logfile \"" + m_strFileName + "\":");
            e.printStackTrace(System.err);
        }
    } 
    
}

