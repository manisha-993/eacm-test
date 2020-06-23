//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.objects;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An OutputStream that writes contents to a Logger upon each call to flush()
 * @author Wendy Stimpson
 */
//$Log: LoggingOutputStream.java,v $
//Revision 1.1  2012/09/27 19:39:13  wendy
//Initial code
//
public class LoggingOutputStream extends ByteArrayOutputStream implements EACMGlobals {
    private Logger logger;
    private Level level;

    /**
     * Constructor
     * @param logger2 Logger to write to
     * @param level2 Level at which to write the log message
     */
    public LoggingOutputStream(Logger logger2, Level level2) {
        super();
        this.logger = logger2;
        this.level = level2;
    }

    /**
     * upon flush() write the existing contents of the OutputStream
     * to the logger as a log record.
     * @throws java.io.IOException in case of error
     */
    public void flush() throws IOException {
        synchronized(this) {
            super.flush();
            String record = this.toString();
            super.reset();

            if (record.length() == 0 || record.equals(RETURN)) {
                // avoid empty records
                return;
            }

            logger.logp(level, "", "", record);
        }
    }
}