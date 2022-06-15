//
// Copyright (c) 2001-2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
//$Log: TemplateBasedWriter.java,v $
//Revision 1.3  2007/07/31 13:03:45  chris
//Rational Software Architect v7
//
//Revision 1.2  2003/05/28 16:49:07  gregg
//getVersion method
//
//Revision 1.1  2003/05/23 22:39:04  gregg
//initial load
//
//Revision 1.2  2003/05/23 22:15:50  gregg
//some comments
//
//Revision 1.1  2003/05/23 22:05:41  gregg
//initial load
//

package COM.ibm.eannounce.pmsync;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import COM.ibm.opicmpdh.middleware.Validate;
import COM.ibm.opicmpdh.objects.Validator;

/**
 * <PRE>
 * Write some output text based on a pre-defined template.
 * Steps: 
 * 1) choose an output file to write to (_strOutFileName in constructor).
 * 2) choose a template file (_strTempalteFileName in constructor).
 * 3) place some sub variables in template file, and include them in the getSubstitutionVariables() array.
 * 4) define what to sub ea. variable w/ in getSubstitutionString() method.
 * 5) writeFile() should do the rest!
 * </PRE> 
 */
public  class TemplateBasedWriter extends PrintWriter {
    
    private String m_strOutFileName = null;
    private String m_strTemplateFileName = null;
    
/**
 * 
 */
    public TemplateBasedWriter(String _strOutFileName, String _strTemplateFileName) throws FileNotFoundException {
        super(new FileOutputStream(_strOutFileName), true); 
        m_strOutFileName = _strOutFileName;
        m_strTemplateFileName = _strTemplateFileName;
    }

/**
 * This will write our output file based on our predefined template file.
 * The template file must exist in the current directory/package (for now)
 */    
    protected void writeFile() throws Exception {
        InputStream is = null;
        BufferedReader bfRdr = null;
        try {
            is = getClass().getResource(getTemplateFileName()).openStream();
            bfRdr = new BufferedReader(new InputStreamReader(is/*,"8859_1"*/));
	        // loop through lines in template file + perform appropriate substitutions
            while (true) {
                // Get next line
                String strLine = bfRdr.readLine();
                if(strLine == null) {
                    return;     
                }
                if(Validator.validateInappropriateEncoding(strLine)) {
                println(performSubstitutions(strLine));   
                }
            }
            //
        } catch(Exception exc) {
            throw exc;    
        } finally {
            if(is != null) {
                is.close();   
            }
            if(bfRdr != null) {
                bfRdr.close();    
            }
        }
    }
        
    private String performSubstitutions(String _strLine) {
        String strVar = null;
        String strToSub = null;
        String strSubLine = _strLine;
        // next two blocks will not occur if no variables are present in the current line
        for(int i = 0; i < getSubstitutionVariables().length; i++) {
            if(_strLine.indexOf(getSubstitutionVariables()[i]) > -1) {
                strVar = getSubstitutionVariables()[i];
                strToSub = getSubstitutionString(getSubstitutionVariables()[i]);
                continue;
            }
        }
        if(strVar != null) {
            int iBeginIndex = _strLine.indexOf(strVar);
            int iEndIndex = iBeginIndex + strVar.length();
            String strBegin = _strLine.substring(0,iBeginIndex);
            String strEnd = _strLine.substring(iEndIndex,_strLine.length());
            strSubLine = strBegin + strToSub + strEnd;
        }
        return strSubLine;
    }
    
/**
 * The template file to read from
 */
    protected String getTemplateFileName() {
        return m_strTemplateFileName; 
    }
    
/**
 * This will return a String based on the passed variable (must be defined in getSubstitutionVariables())
 * @param _strSubVar the variable to substitute in the template file.
 * @return a newly generated String for the given substitution variable.
 */
    protected String getSubstitutionString(String _strSubVar) {return _strSubVar;};
/**
 * These are the variables to replace in the template file
 */
    protected  String[] getSubstitutionVariables() {return null;};

    public static String getVersion() {
        return new String("$Id: TemplateBasedWriter.java,v 1.3 2007/07/31 13:03:45 chris Exp $");    
    }
}
