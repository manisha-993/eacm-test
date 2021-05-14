// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;

import com.wallstreetwise.app.jspell.domain.net.*;
import com.wallstreetwise.app.jspell.domain.*;

/******************************************************************************
* This is the applet control for XMLEditor (for the HTML editing).
* Assumption:  Code that generates html to invoke the applet will be responsible for any
* lock handling for the entity. One applet will be used on a page.  launchEditor(...)
* will be used for separate attributes that need editing.
* ONE editor at a time!!
* The RETURNURL parameter will be used if submit is successful.
*
* Caller should set the following parameters:
*   "cookie"     : required for netscape to work
*   "returnUrl"  : url to use after a successful save.
*   "connectMode" : servlet or socket for JSpell connection
*   "port"       : port number for JSpell socket, if null defaults to 5317
*
* @author Wendy Stimpson
* @version 1.0
*/
/*
* Notes:
*   - The IBM JRE must be installed with tnrmt30.ttf in the jre/lib/fonts directory
*   to display Japanese characters.
*
*   - To allow applet to read from system clipboard or to copy/paste structure (wysiwyg):
*   1) Create a public and private key, it will expire in 1000 days
*       keytool -genkey -alias xmleditorkey -validity 1000
*   2) export the certificate
*       keytool -export -alias xmleditorkey -file xmleditor.cert
*   !!!client must import this certificate into their keystore
*   the .keystore file and .java.policy file is in ${user.home} system property
*   3) sign the jar
*       jarsigner opicmXMLEditor.jar xmleditorkey
*   4) !!!client must update the policy file
*       add permissions for code signed by xmleditorkey
*       edit policy:
*           signedby xmleditorkey
*           can't do codebase because it points to a particular server
*           add
*               java.AWT.Permission "accessClipboard";
*               java.awt.AWTPermission "showWindowWithoutWarningBanner";
*               java.lang.RuntimePermission "usePolicy";
*               java.lang.RuntimePermission "queuePrintJob";
*
*   Deployment tips: (see page 776 Core Java)
*   on the client:
*       edit java.security in ${java.home}/jre/lib/security
*           add policy.url.3=http://eannounce.ibm.com/admin/applet.policy
*           (to set up a policy file on an intranet server, and manage the file in one place)
*   on the server:
*       inside the policy file, specify the keystore location with the url
*           keystore "http://eannounce.ibm.com/admin/certs.store","JKS";
*
*           (import the xmleditor.cert to this keystore)
*/
// $Log: XMLEditorDialogApplet.java,v $
// Revision 1.2  2008/01/30 16:27:08  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/04/18 19:47:48  wendy
// Reorganized JUI module
//
// Revision 1.11  2006/11/06 15:19:49  wendy
// Removed original launcheditor(), javascript was still calling it
//
// Revision 1.10  2006/10/24 17:00:06  wendy
// TIR 6UQGPT, enforce spellcheck if meta has rule
//
// Revision 1.9  2006/10/12 20:05:24  wendy
// Changed jspell servlet name
//
// Revision 1.8  2006/10/12 19:52:22  wendy
// Added wss to jspell servlet name
//
// Revision 1.7  2006/10/11 19:38:27  wendy
// Added heritageKey for TIR 6U9HKL, needed to access attribute in servlet
//
// Revision 1.6  2006/05/10 14:43:00  wendy
// Change e-announce to EACM
//
// Revision 1.5  2006/01/25 18:59:03  wendy
// AHE copyright
//
// Revision 1.4  2005/10/12 12:48:57  wendy
// Conform to new jtest configuration
//
// Revision 1.3  2005/10/04 18:20:55  wendy
// Hide dialog after updateRequested returns to prevent BUI hang
//
// Revision 1.2  2005/10/04 15:45:21  wendy
// Add '.wss' to default servlet names
//
// Revision 1.1.1.1  2005/09/09 20:39:18  tony
// This is the initial load of OPICM
//
//
public class XMLEditorDialogApplet extends JApplet implements XMLEditorListener,
XMLEditorGlobals
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.2 $";

    private XMLEditorDialog oed= null;

    // values needed to load/save specified attribute
    private String entityType;
    private String entityId;
    private String attributeCode;
    private String dtdName;
    private String heritageKey;
    private boolean spellChkRequired = false;

    private String cookie;   // required for maintaining session for netscape
    private String getServletName, putServletName;

    private JSpellDictionary dictionary;
    private static String jsExceptionMsg=null; // hang onto this to display msg to user (once)

    // used after update is successful
    private String returnUrl;
    private String editorTitle;

    private PageLoader loader;  // load data asynchronously

    private boolean userCanEdit = false;
    private long isRedirected = 0; // don't allow another editor to open if getting redirected
    private boolean isDemo = false;
    private static final int DEFAULT_PORT= 5317;
    private static final int BATCH_SIZE=200;
    private static final int SECONDS_10 =10000;
    private static final int ERR_DIALOG_W=18;
    private static final int ERR_DIALOG_H=80;

    /*************************************************************************
     * Returns information about this applet.
     * @return a string of information about this applet
     */
    public String getAppletInfo() {
        return "XMLEditorDialogApplet" +XMLEditor.NEWLINE_STR +XMLEditor.NEWLINE_STR+
        "Edits "+XMLEditorGlobals.APP_NAME+" XML attributes in a browser." +XMLEditor.NEWLINE_STR+
        "Creation date: (11/19/05 11:24:54 AM)" +XMLEditor.NEWLINE_STR+
        "@author: Wendy Stimpson"+XMLEditor.NEWLINE_STR + "";
    }

    /*************************************************************************
    * Called by the browser or applet viewer to inform
    * this applet that it should stop its execution. It is called when
    * the Web page that contains this applet has been replaced by
    * another page, and also just before the applet is to be destroyed.
    * <p>
    * A subclass of <code>Applet</code> should override this method if
    * it has any operation that it wants to perform each time the Web
    * page containing it is no longer visible. For example, an applet
    * with animation might want to use the <code>start</code> method to
    * resume animation, and the <code>stop</code> method to suspend the
    * animation.
    *
    */
    public void stop()
    {
//System.out.println("stop() entered oed.isShowing(): "+oed.isShowing());
        // if user closed the browser but not the editor, the editor dialog is closed by the
        // time this runs oed.isShowing() is always false

        loader=null;  // notify thread to stop

        super.stop();
    }

    /**
     * Called by the browser or applet viewer to inform
     * this applet that it is being reclaimed and that it should destroy
     * any resources that it has allocated. The <code>stop</code> method
     * will always be called before <code>destroy</code>.
     *
     */
    public void destroy()
    {
//System.out.println("destroy() entered");
        super.destroy();

        // release all memory
        oed.dispose();
        oed.removeXMLEditorListener(this);
        oed.dereference();
        oed=null;

        cookie=null;
        returnUrl=null;
        entityType=null;
        entityId=null;
        attributeCode=null;
        editorTitle = null;
        dtdName=null;
        heritageKey = null;
        getServletName=null;
        putServletName=null;
        if (dictionary!=null) {
            dictionary.close();  // Closes the socket connection in the socket implementation
        }
        dictionary = null;
//      jsExceptionMsg = null;
    }

    /*************************************************************************
    * Called by the browser or applet viewer to inform this applet
    * that it has been loaded (or reloaded) into the system. It is always
    * called before the first time that the <code>start</code> method is
    * called.
    * <p>
    * A subclass of <code>Applet</code> should override this method if
    * it has initialization to perform. For example, an applet with
    * threads would use the <code>init</code> method to create the
    * threads and the <code>destroy</code> method to kill them.
    * <p>
    *
    */
    public void init()
    {
        String demo;
        String connectMode;
        System.out.println("init() entered version: "+VERSION);
        // show version in status bar
        getAppletContext().showStatus("Initializing the XML editor version: "+
            VERSION.replace('$',' ').trim()+"....");

        returnUrl = getParameter("returnUrl");
        cookie = getParameter("cookie");
        demo = getParameter("demo");
        if (demo !=null && demo.equals("true")){
            isDemo = true;
        }

        getServletName = getParameter("getServletName");
        putServletName = getParameter("putServletName");
        if (getServletName==null){
            getServletName = "PokGetXMLAttribute.wss";
        }
        if (putServletName==null) {
            putServletName = "PokPutXMLAttribute.wss";
        }

/*
From JSpell documentation:
Communicating with the JSpell Servlet or using sockets.
� Import the necessary JSpell packages.
� Create the JSpellDictionary instance, either JSpellDictionaryServlet or JSpellDictionarySocket.
� Set the URL if you are using Servlets or the host name and port for sockets.
Following is done in the editor code.
� Create a JSpellParser.
� Set application dependent options such as turning on/off learning of words, etc.
� Create a JSpellErrorHandler passing it your text component and the JSpellParser.
� Execute the check() method to begin spell checking.
*/

        connectMode = getParameter("connectMode");
        if (connectMode==null) { // default to socket
            connectMode="socket";
        }

        System.out.println("JSpell connectMode: "+connectMode);

        // set up the spell checker
        if (connectMode.equalsIgnoreCase("servlet"))
        {
            String spURLStr;
            // servlet implementation
            dictionary = new JSpellDictionaryServlet();
            // build the url string to the spell checker servlet
            spURLStr = getCodeBase()+ "JSpellServlet.wss";
            ((JSpellDictionaryServlet)dictionary).setURL(spURLStr);
        	System.out.println("JSpell url: "+spURLStr);
        }
        else
        {
            String portStr = getParameter("port");
            int port = DEFAULT_PORT;

            // Socket implementation
            dictionary= new JSpellDictionarySocket(); //Constructor sets the batch size to 100.
            //The default host is "localhost" and the default port is 5317.
            ((JSpellDictionarySocket)dictionary).setHost(getCodeBase().getHost());
            // use port parameter if specified, otherwise use the default of 5317
            if (portStr!=null) {
                port = Integer.parseInt(portStr);
            }
        	System.out.println("JSpell port: "+port);

            ((JSpellDictionarySocket)dictionary).setPort(port);
        }

        // Set the number of words to validate in one call to the dictionary access interface
        dictionary.setBatchSize(BATCH_SIZE);
        try {
            dictionary.open();
            dictionary.setLearnWords(false);
        }catch(JSpellException jse)
        {
            System.err.println(jse);
            jse.printStackTrace(System.err);
            dictionary=null;
            if (jsExceptionMsg==null) {  // msg hasn't been shown yet
                jsExceptionMsg = jse.getMessage();
            }
        }

        // instantiate an editor
        oed = new XMLEditorDialog(this, XMLEditorGlobals.APP_NAME+" XML Editor" ,dictionary);
        oed.addXMLEditorListener(this);
    }

    /*************************************************************************
     * Called by the javascript to set title of dialog to something meaningful
     *
     * @param title
     */
    public void setTitle(String title)
    {
        editorTitle = title;
    }

    /*************************************************************************
     * Called by the javascript to set return url, specific for each attribute
     *
     * @param url
     */
    public void setReturnUrl(String url)
    {
        returnUrl = url;
    }

    /*************************************************************************
    * Called by the javascript to launch the editor. Assumption, one applet per
    * page.  This applet will be used to launch editors for all xml attributes
    * one at a time.
    * @param key   		String needed to access the attribute
    * @param etype 		String specifying entity type
    * @param eid   		String specifying entity id
    * @param attrCode   String specifying attribute code
    * @param isEditable String specifying if the editor should allow updates
    * @param dtd        String specifying dtd name to use, defaults to ls.dtd
    * @param spChkReq   String "true" if meta has spellcheck rule
    */
    public void launchEditor(String key, String etype, String eid, String attrCode,
            String isEditable, String dtd, String spChkReq)
    {
System.err.println("launchEditor for key:"+key+" type:"+etype+" id:"+eid+" code:"+attrCode+
" edit:"+isEditable+" dtd:"+dtd+" spChkReq:"+spChkReq);
        // must find out if showing a dialog.. because save will reload the page
        // or if in process of redirection
        if (isRedirected!=0)
        {
            if (System.currentTimeMillis()-isRedirected>SECONDS_10){ // has been 10 seconds since redirection
                isRedirected=0; // must allow for user to have canceled redirection.. bad if they did
            }
        }
        if (oed.isShowing() || (isRedirected!=0))
        {
        //System.err.println("This editor dialog is showing.. don't display another one");
            getToolkit().beep();
            //return;
        }else {
            // get values needed to load specified attribute
            entityType = etype;
            entityId = eid;
            attributeCode = attrCode;
            dtdName = dtd;
            heritageKey = key;

            if (spChkReq == null) {
				spChkReq = "FALSE";
			}
            spellChkRequired = spChkReq.equalsIgnoreCase("true");

            // if not specified, assume not editable
            // it should not be editable if the user can not get the lock!
            if (isEditable != null) {
                isEditable = isEditable.toUpperCase();

            } else {
                isEditable = "FALSE";
            }

            // don't allow editing of the error message!
            if (etype==null || eid ==null ||attributeCode==null) {
                isEditable = "FALSE";
            }

            // fixme remove this after demo??
            if (dtdName==null) {
                dtdName = "ls.dtd";
            }

            userCanEdit = isEditable.equals("TRUE");

            if (editorTitle!=null)
            {
                oed.setTitle(editorTitle);
                editorTitle = null;
            }
            else {
                oed.setTitle(XMLEditorGlobals.APP_NAME+" XML Editor : " +attributeCode);
            }

            // must be in another thread or browser hangs
            SwingUtilities.invokeLater(new Runnable() {
                    public void run()
                    {
                        loadEditor(); // connection can take some time.. so dialog may not paint
                        oed.setVisible(true);
                    }
            });
        }
    }

    /*************************************************************************
    * Load the editor based on current set of parameters.
    */
    private void loadEditor()
    {
        if (entityType==null || entityId ==null || attributeCode==null)
        {
            String errormsg = "Applet parameters were not set properly!";
            oed.setErrorMsg(errormsg);
        }
        else
        {
            // load a temporary message in the editor
            String msg = "<p>Getting attribute value from "+XMLEditorGlobals.APP_NAME+" ....</p>";
            // this is not asynchronous, it will set the wait cursor
            oed.setTemporaryMsg(msg);

//System.out.println("loading page");
            // load asynchronously from url, connection can be time consuming
            loader = new PageLoader();
            loader.start();
        }
    }

    /*************************************************************************
     * Called whenever the control throws an exception.
     *
     * @param e java.lang.Throwable
     */
    private void handleException(Throwable e)
    {
//System.out.println("XMLEditorDialogApplet:handleexc() isevtthrd "+
//      javax.swing.SwingUtilities.isEventDispatchThread());
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(bs,true);

            System.err.println("XMLEditorDialogApplet:--------- EXCEPTION ---------");
            e.printStackTrace(System.err);
            e.printStackTrace(pw);
            showErrorDialog(bs.toString());
        }catch(Exception ew){
            System.out.println(ew.getMessage()); // jtest req
        }
        finally{
            if (pw!=null){
                pw.close();
            }
        }
    }

    /*************************************************************************
     * Called whenever the document needs to be sent back to the database
     * This code will use multipart/form-data to upload the document as a file.
     * It will mimic the output a browser would create, using a boundary to
     * separate elements.
     *
     * MIME-types:
     * application/x-www-form-urlencoded means that the variable name-value
     *   pairs will be encoded the same way a URL is encoded. Any special
     *   characters, including puctuation, will be encoded as %nn where nn is
     *   the ASCII value for the character in hex.  The default encoding for
     *   all forms is `application/x-www-form-urlencoded', used for forms that
     *   do not include file upload; the data is '&'-separated Name=Value pairs,
     *   one pair for each form field, like for the GET method.
     *
     * multipart/form-data: used for forms that include file upload;
     *  The data will be formated according to the MIME standard.
     *  For each field that is not for file upload, the browser includes a
     *  content-disposition header indicating the name of the
     *  field, followed by an empty line, followed by the value:
     *
     *  ----SeparatorLine
     *  Content-Disposition: form-data; name="FieldName"
     *
     *  FieldValue
     *  ----SeparatorLine
     *
     *  For each file upload field, the browser includes a content-disposition
     *  header indicating the name of the field and the filename, followed by
     *  a content-type line followed by an empty line, followed by the actual
     *  content of the file:  (Netscape doesn't provide the content-type line!)
     *
     *  ----SeparatorLine
     *  Content-Disposition: form-data; name="FieldName"; filename="xxx"
     *
     *  FileContent...
     *  ----SeparatorLine
     *
     *  The main advantages of this method are that it is well suited for large
     *  data transmission, and that the transmitted data is not visible to
     *  the user. The disadvantages are that the user cannot save a bookmark on
     *  the results of using the form, and that the URL mentioned in the ACTION
     *  attribute would have to be changed, i.e. an update of the form is required,
     *  if the script handling the form data is moved to a new location
     *  (a "redirect" on the URL won't work).
     *
     * Example data:
     * Content type: multipart/form-data; boundary=---------------------------299743196816626
     * InputStream contains:
     * -----------------------------299743196816626
     * Content-Disposition: form-data; name="entityId"
     *
     * 1
     * -----------------------------299743196816626
     * Content-Disposition: form-data; name="entityType"
     *
     * PSGPOS
     * -----------------------------299743196816626
     * Content-Disposition: form-data; name="uploadData"; filename="C:\work\opicm\HtmlEditor\ftp_editor"
     * Content-Type: text/plain
     *
     * DATA goes here
     *
     * -----------------------------299743196816626--
     *
     */
    private boolean submitDocument()
    {
        boolean success=true;
        BufferedOutputStream bos=null;
        // don't submit a document that is not editable!!
        // errors are not editable and after one submission, the editor
        // becomes uneditable
        if (!oed.isEditable()) {
            success=true;//return true;
        }else {
            oed.setWaitCursors();
            try {
                int status;
                String error;
                // build url to send data to as a binary stream
                URL url = new URL(getCodeBase(), putServletName);
                // create the boundary.. this could be dynamic but hard code it now
                String boundary = "-----------------------------299743196816626";
                StringBuffer sb = new StringBuffer(boundary);
                String xml;
                String closing = XMLEditor.NEWLINE_STR+boundary+XMLEditor.NEWLINE_STR;

                // open the connection
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                connection.setRequestMethod("POST");

                // used with netscape to maintain session
                if (cookie!=null) {
                    connection.setRequestProperty("cookie",cookie);
                }

                // open an outputstream to get data to the server
                connection.setDoOutput(true);

                // Let the run-time system (RTS) know that we want input.
                connection.setDoInput(true);

                // No caching, we want the real thing.
                connection.setUseCaches(false);

                // Specify the content type
                connection.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary="+boundary);

                // show temporary message in status bar
                getAppletContext().showStatus("Sending updated attribute to "+XMLEditorGlobals.APP_NAME+"....");

                // get an output stream, buffering improves performance
                bos = new BufferedOutputStream(connection.getOutputStream());
                // write the required fields
                sb.append(XMLEditor.NEWLINE_STR+"Content-Disposition: form-data; name=\"entityId\""+XMLEditor.NEWLINE_STR+XMLEditor.NEWLINE_STR);
                sb.append(entityId+XMLEditor.NEWLINE_STR+boundary);
                sb.append(XMLEditor.NEWLINE_STR+"Content-Disposition: form-data; name=\"entityType\""+XMLEditor.NEWLINE_STR+XMLEditor.NEWLINE_STR);
                sb.append(entityType+XMLEditor.NEWLINE_STR+boundary);
                sb.append(XMLEditor.NEWLINE_STR+"Content-Disposition: form-data; name=\"attributeCode\""+XMLEditor.NEWLINE_STR+XMLEditor.NEWLINE_STR);
                sb.append(attributeCode+XMLEditor.NEWLINE_STR+boundary);
                if (heritageKey!=null &&heritageKey.length()>0){
            	    sb.append(XMLEditor.NEWLINE_STR+"Content-Disposition: form-data; name=\"heritageKey\""+XMLEditor.NEWLINE_STR+XMLEditor.NEWLINE_STR);
            	    sb.append(heritageKey+XMLEditor.NEWLINE_STR+boundary);
				}
                sb.append(XMLEditor.NEWLINE_STR+"Content-Disposition: form-data; name=\"prose\"; filename=\"testutf8\"");
                sb.append(XMLEditor.NEWLINE_STR+"Content-Type: text/html"+XMLEditor.NEWLINE_STR+XMLEditor.NEWLINE_STR);

                // write fields to stream
                bos.write(sb.toString().getBytes(CHAR_ENCODING));
                bos.flush();

                // write the document
                xml = oed.getCompletedXML();
                if (xml==null) {
                    xml="";
                }
                bos.write(xml.getBytes(CHAR_ENCODING));

                // complete the content
                bos.write(closing.getBytes(CHAR_ENCODING));
                bos.close();

                // check result, errors will be returned

                // check if there was an error inside my control, not logged in, exception..
                // the header key SERVLET_XML_ATTR_ERR will not be null
                // if there is an error, the inputstream will have information about it
                error = connection.getHeaderField(SERVLET_XML_ATTR_ERR);

                // check if there was an error, errors outside my control will have
                // a bad status, such as server is down, etc..
                status = connection.getResponseCode();
                if (status != HttpURLConnection.HTTP_OK)
                {
                    if (error ==null) {
                        error = "System error";
                    }
                }

                if (error!=null)
                {
                    // get information from the stream
                    InputStream is = connection.getInputStream();
                    int cnt = is.available();
                    if (cnt>0)
                    {
                        // stream will be not be utf-8
                        InputStreamReader rdr=null;
                        try{
                            char[] bytes = new char[cnt];
                            String msg;
                            rdr = new InputStreamReader(is);
                            rdr.read(bytes,0,cnt);
                            msg = new String(bytes);
                            error = msg.trim();
                        }
                        catch(Exception e){
                            System.out.println(e.getMessage()); // jtest req
                        }
                        finally{
                            if (rdr!=null){
                                rdr.close();
                            }
                        }
                    }

                    showErrorDialog(error);
                    //return false;
                    success =false;
                }else {
    // get result as an InputStream.. debugging only to see what we sent
    //oed.loadDocument(new BufferedInputStream(connection.getInputStream()));
                    //return true;
                    success=true;
                }
            }
            catch(ThreadDeath e)
            {
                // An instance of ThreadDeath is thrown in the victim thread when the stop method with
                // zero arguments in class Thread is called.

                // If ThreadDeath is caught by a method, it is important that it be rethrown so that the
                // thread actually dies. The class ThreadDeath is specifically a subclass of Error rather
                // than Exception, even though it is a "normal occurrence", because many applications
                // catch all occurrences of Exception and then discard the exception.
                // The top-level error handler does not print out a message if ThreadDeath is never caught.
                throw e;
            }
            catch(Throwable e)
            {
                //(MalformedURLException e)  // new URL() failed
                //(IOException e)  // openConnection() failed, server may be down
                handleException(e);
                //return false;
                success=false;
            }
            finally{
                oed.restoreCursors();
                getAppletContext().showStatus("");
                if (bos!=null){
                    try{
                        bos.close();
                    }catch(Exception x) {
                        System.out.println(x.getMessage());  // jtest req
                    }
                }
            }
        }
        return success;
    }

    /******************************************************************************
     * Display exceptions or errors in a dialog.
     *
     * @param msg String with error message
     */
    private void showErrorDialog(String msg)
    {
//System.out.println("XMLEditorDialogApplet:showerrdialog() isevtthrd "+
//      javax.swing.SwingUtilities.isEventDispatchThread());

        // textarea
        JTextArea ta = new JTextArea(msg,ERR_DIALOG_W,ERR_DIALOG_H);
        // Messages used as components in dialog
        Object[] messageObj = { new JScrollPane(ta)};
        JButton btnOk = new JButton("OK");
        Object[] options = {btnOk};
        JDialog dialog;

        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setEditable(false);
        ta.setCaretPosition(0);
        //ta.setEnabled(false); this prevents Accessibility tool from reading

        // notify user
        getToolkit().beep();

        // show dialog
//        if (SwingUtilities.isEventDispatchThread())
        //JOptionPane.showMessageDialog(this, messageObj,
        //      XMLEditorGlobals.APP_NAME+" XML Editor Error", JOptionPane.ERROR_MESSAGE);

        btnOk.setMnemonic(btnOk.getText().charAt(0));
        btnOk.getAccessibleContext().setAccessibleDescription("Press OK to close dialog.");

        if (btnOk!=null) { //stupid check to fix jtest
            final JOptionPane optionPane = new JOptionPane(messageObj,
                JOptionPane.ERROR_MESSAGE,
                JOptionPane.YES_OPTION,
                null, options,  options[0]);

            ActionListener acl = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
                    e=null; //jtest req
                }
            };

            btnOk.addActionListener(acl);

            messageObj[0]=null;
            messageObj = null;
            options = null;
            dialog = optionPane.createDialog(this, XMLEditorGlobals.APP_NAME+" XML Editor Error");
            dialog.setVisible(true);
            dialog.dispose();
            btnOk.removeActionListener(acl);
            acl=null;
            btnOk=null;
            dialog = null;
            ta = null;
        }
    }

    /******************************************************************************
     * Thread to load a stream into the editor.  Database access can be very
     * slow.  The actual load into the editor is done on another thread.
     */
    private class PageLoader extends Thread
    {
        /**
         * Try to load the document.
         */
        public void run()
        {
            // make sure this should continue
            if (Thread.currentThread() == loader)
            {
                // wait cursors have been displayed when the temporary message is set
                try {
                    URL url;
                    HttpURLConnection connection;
                    String error;
                    int status;
                    // build url to get attribute as a binary stream
                    String attrSrc = getServletName+"?entityId=" + entityId +
                        "&entityType=" + URLEncoder.encode(entityType,"UTF8") +
                        "&attributeCode=" + URLEncoder.encode(attributeCode,"UTF8");
                    if (heritageKey!=null &&heritageKey.length()>0){
                        attrSrc=attrSrc+"&heritageKey=" + URLEncoder.encode(heritageKey,"UTF8");
					}

                    // for demo, this forces reading file from server
                    if (isDemo) {
                        attrSrc="EditorTest2.html";
                    }

                    url = new URL(getCodeBase(), attrSrc);

                    getAppletContext().showStatus("Connecting to "+getCodeBase()+"....");
                    System.out.println("Using URL "+url);

                    // open the connection
                    connection = (HttpURLConnection)url.openConnection();
    //appletviewer URLConnection connection = url.openConnection();

                    // make sure this should continue.. if url takes an excessive time
                    // and user closes or refreshes.. check again here
                    if (Thread.currentThread() != loader)
                    {
                        getAppletContext().showStatus("");
                        //return;
                    }else {
                        // used with netscape to maintain session
                        if (cookie!=null) {
                            connection.setRequestProperty("cookie",cookie);
                        }

                        // Let the run-time system (RTS) know that we want input.
                        connection.setDoInput(true);

                        // No caching, we want the real thing.
                        connection.setUseCaches(false);

                        // check if there was an error inside my control, not logged in, exception..
                        // the header key SERVLET_XML_ATTR_ERR will not be null
                        // if there is an error, the inputstream will have information about it
                        error = connection.getHeaderField(SERVLET_XML_ATTR_ERR);

                        // check if there was an error, errors outside my control will have
                        // a bad status, such as server is down, etc..
        // comment out for appletviewer
        /**/
                        status = connection.getResponseCode();
                        if (status != HttpURLConnection.HTTP_OK)
                        {
                            if (error ==null) {
                                error = "System error";
                            }
                        }
        /**/// end comment out for appletviewer

                        if (error!=null)
                        {
                            // this is not the event dispatch thread
        //                  JOptionPane.showMessageDialog(XMLEditorDialogApplet.this, adm1,//error,
        //                      XMLEditorGlobals.APP_NAME+" XML Editor Load Error", JOptionPane.ERROR_MESSAGE);
                            String msgs[] = {
                                error
                            };

                            String accdesc[] = {
                                "Press OK to close dialog."
                            };
                            String errorInfo;
                            InputStream is;
                            int cnt;

                            if (Thread.currentThread() == loader) {
                                getToolkit().beep();
                            }

                            if (Thread.currentThread() == loader) {
                                XMLEditor.showAccessibleDialog(XMLEditorDialogApplet.this,
                                    XMLEditorGlobals.APP_NAME+" XML Editor Load Error",
                                    JOptionPane.ERROR_MESSAGE, JOptionPane.YES_OPTION,
                                    "Error loading XML document", msgs, accdesc);
                            }

                            msgs = null;
                            accdesc = null;

                            errorInfo=error;

                            // get information from the stream
                            is = connection.getInputStream();
                            cnt = is.available();
                            if (cnt>0)
                            {
                                // stream will be utf-8
                                InputStreamReader rdr = null;
                                try{
                                    String msg;
                                    char[] bytes = new char[cnt];
                                    rdr=new InputStreamReader(is,CHAR_ENCODING);
                                    rdr.read(bytes,0,cnt);
                                    msg = new String(bytes);
                                    errorInfo = msg.trim();
                                }catch(Exception e){
                                    System.out.println(e.getMessage());
                                }
                                finally{
                                    if (rdr!=null){
                                        rdr.close();
                                    }
                                }
                            }
                            // put information in the control, load can not be done
                            if (Thread.currentThread() == loader) {
                                oed.setErrorMsg(errorInfo);
                            }

                            getAppletContext().showStatus("");

                            //return;
                        }else {
                            getAppletContext().showStatus("Loading editor....");
            //System.out.println("loading document from stream");

                            // buffering improves performance
                            // get value as an InputStream synchronously
                            if (isDemo) {     //for demo
                                oed.loadDocument(new BufferedInputStream(connection.getInputStream()),"8859_1",
                                	userCanEdit,dtdName,spellChkRequired);

                            } else {
                                oed.loadDocument(new BufferedInputStream(connection.getInputStream()),
                                    userCanEdit,dtdName,spellChkRequired);
                            }
                        }
                    }
                }
                catch(ThreadDeath e)
                {
                    // An instance of ThreadDeath is thrown in the victim thread when the stop method with
                    // zero arguments in class Thread is called.

                    // If ThreadDeath is caught by a method, it is important that it be rethrown so that the
                    // thread actually dies. The class ThreadDeath is specifically a subclass of Error rather
                    // than Exception, even though it is a "normal occurrence", because many applications
                    // catch all occurrences of Exception and then discard the exception.
                    // The top-level error handler does not print out a message if ThreadDeath is never caught.
                    throw e;
                }
                catch(Throwable e) {
                    getAppletContext().showStatus("");
                    // cursors will be restored and control will become uneditable
                    if (oed!=null) {
                        oed.setErrorMsg("Exception occurred. Data can not be loaded.");
                    }
                    if (Thread.currentThread() == loader) {
                        handleException(e);

                    } else
                    {
                        ByteArrayOutputStream bs = new ByteArrayOutputStream();
                        PrintWriter pw =null;
                        try{
                            pw = new PrintWriter(bs,true);
                            System.err.println("XMLEditorDialogApplet:--------- EXCEPTION ---------");
                            e.printStackTrace(System.err);
                            e.printStackTrace(pw);
                        }catch(Exception e2){
                            System.out.println(e2.getMessage());  // jtest req
                        }finally{
                            if (pw!=null){
                                pw.close();
                            }
                        }
                    }
                }
            }
        }
    }

    /******************************************************
    * This is called to get the help information for the attribute
    */
    private String getHelpText()
    {
        String helpTxt=null;
        try
        {
            String helpSrc;
            URL url;
            HttpURLConnection connection;
            String error;
            int status;
            // if running in appletviewer, don't go to server
            if (getParameter("codebase") != null && getParameter("codebase").equals(".")) {
                //return helpTxt;
            }else {
                // show temporary message in status bar
                getAppletContext().showStatus("Getting help text from "+XMLEditorGlobals.APP_NAME+"....");

                helpSrc = getServletName+"?entityId=" + entityId +
                    "&entityType=" + URLEncoder.encode(entityType,"UTF8") +
                    "&attributeCode=" + URLEncoder.encode(attributeCode,"UTF8")+
                    "&helpText=true";
                url = new URL(getCodeBase(), helpSrc);

    //System.out.println("getHelp() opening connection");
                // open the connection
                connection = (HttpURLConnection)url.openConnection();

                // used with netscape to maintain session
                if (cookie!=null) {
                    connection.setRequestProperty("cookie",cookie);
                }

                // Let the run-time system (RTS) know that we want input.
                connection.setDoInput(true);

                // No caching, we want the real thing.
                connection.setUseCaches(false);

                // check if there was an error inside my control, not logged in, exception..
                // the header key SERVLET_XML_ATTR_ERR will not be null
                error = connection.getHeaderField(SERVLET_XML_ATTR_ERR);

                // check if there was an error, errors outside my control will have
                // a bad status, such as server is down, etc..
                status = connection.getResponseCode();
                if (status != HttpURLConnection.HTTP_OK)
                {
                    if (error ==null) {
                        error = "System error";
                    }
                }

                // if user is not logged in, error!=null but this takes a very long time
                // to return when user isn't logged it.  should not be a problem because
                // they will never get to the applet without logging in first
                if (error==null)
                {
                    // get information from the stream
                    InputStream is = connection.getInputStream();
                    int cnt = is.available();
                    if (cnt>0)
                    {
                        char[] bytes = new char[cnt];
                        // stream will be utf-8
                        InputStreamReader rdr = null;
                        try{
                            rdr = new InputStreamReader(is,CHAR_ENCODING);
                            rdr.read(bytes,0,cnt);
                            helpTxt = new String(bytes);
    //  System.out.println("help response "+helpTxt);
                        }catch(Exception e){
                            System.out.println(e.getMessage()); // jtest req
                        }
                        finally{
                            if (rdr!=null){
                                rdr.close();
                            }
                        }
                    }
                }

                connection.disconnect();
            }
        }
        catch(ThreadDeath e)
        {
            // An instance of ThreadDeath is thrown in the victim thread when the stop method with
            // zero arguments in class Thread is called.

            // If ThreadDeath is caught by a method, it is important that it be rethrown so that the
            // thread actually dies. The class ThreadDeath is specifically a subclass of Error rather
            // than Exception, even though it is a "normal occurrence", because many applications
            // catch all occurrences of Exception and then discard the exception.
            // The top-level error handler does not print out a message if ThreadDeath is never caught.
            throw e;
        }
        catch (Throwable e) {
            System.err.println("XMLEditorDialogApplet:getHelpText() exc "+e.getMessage());
            e.printStackTrace(System.err);
        }
        finally{
            getAppletContext().showStatus("");
        }

        return helpTxt;
    }

    // interface XMLEditorListener

    /******************************************************
     * This is called when the user wishes to submit completed html to the
     * OPICM database.
     *
     * @return boolean
     */
    public boolean updateRequested()
    {
        // demo set results to true
        boolean results = true;
        if (!isDemo) {
            results = submitDocument();
        }
        if (results)  // submit complete, user can no longer edit
        {
            userCanEdit = false;
            //do this later, if called by window closing event, hangs browser if done inline
            SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        oed.hide();
                        redirect();
                    }
                });
        }

        return results;
    }

    // this is called when the editor is closed either because of a save
    private void redirect()
    {
//System.out.println("****redirect() called");
        // return to the specified url after control is returned to the editor
        if (returnUrl != null)
        {
            isRedirected = System.currentTimeMillis();
            SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            getAppletContext().showDocument(new URL(getCodeBase(),returnUrl));
                        }catch(MalformedURLException e){
                            System.out.println(e.getMessage()); // jtest req
                        }
                    }
            });
        }
    }
    /**************
     * this is called when the editor is closed, a save will not force a close
     */
    public void editorClosing()
    {
//System.out.println("****editorClosing called");
// this should be removed if applet is incorporated into a form
// this is needed to force closure of the jsp used to display the applet
        //redirect();
    }

    /*******************
     * must go to url to get help for this attribute
     * return null for demos....
     *
     * @return String
     */
    public String attributeHelpTextRequested() { return (isDemo? null:getHelpText());}

    private class XMLEditorDialog extends JDialog implements PropertyChangeListener
    {
    	private static final long serialVersionUID = 1L;
    	private XMLEditor xeditor;

        void dereference()
        {
            xeditor.removePropertyChangeListener(this);
            xeditor.dereference();
            xeditor=null;
            XMLEditor.dereferenceContainer(this);
        }

        void loadDocument(InputStream bis, boolean isEditable,String dtdName2, boolean splChkReq)
        {
            BufferedReader rdr=null;
            // isEditable would be true if the user has the ability to modify the data

            // reading from the stream can be time consuming and dialog does not paint
            // so get all text first and then load editor
            try {
                StringBuffer sb = new StringBuffer();
                // append lines until done
                String s=null;
                // Note: character encoding used with inputstreams can not
                // be determined by the inputstream alone.  If the encoding is
                // not specified, it assumes the input is in the default encoding
                // of the platform.
                rdr = new BufferedReader(new InputStreamReader(bis, CHAR_ENCODING));
                while((s=rdr.readLine()) !=null) {
                    sb.append(s+XMLEditor.NEWLINE_STR);
                }  // duplicate the original string

                xeditor.loadXML(sb.toString(), isEditable, dtdName2,splChkReq);
                // editor intermittently fails to repaint after loading, force it here
                // editor blinks.. but calling repaint doesn't work
                setVisible(false);
                setVisible(true);
            }
            catch(Exception t)
            {
                System.err.println("Error while loading document from stream: " + t);
                t.printStackTrace(System.err);
                handleException(t);
            }
            finally{
                if (rdr!=null){
                    try{
                        rdr.close();
                    }catch(Exception ee) {
                        System.out.println(ee.getMessage());  // jtest req
                    }
                }
            }
//          xeditor.loadDocument(bis,isEditable,dtdName);
        }

        /*********************************************************************
        * Constructor
        */
        XMLEditorDialog(Component parentComponent, String title,JSpellDictionary dictionary2)
        {
            // must be modal because save reloads main page if multiple applets are used
            // can be non-modal if only one is allowed at a time
            super(JOptionPane.getFrameForComponent(parentComponent), title,false);

            xeditor = new XMLEditor(getRootPane(),dictionary2,true);

            setSize(getPreferredSize());
            // add listener for notification when done loading
            xeditor.addPropertyChangeListener(this);

            pack();
        }

        void addXMLEditorListener(XMLEditorListener xel) { xeditor.addXMLEditorListener(xel);}
        void removeXMLEditorListener(XMLEditorListener xel) { xeditor.removeXMLEditorListener(xel);}
        void setErrorMsg(String errormsg) { xeditor.setErrorMsg(errormsg);}
        void setTemporaryMsg(String msg) { xeditor.setTemporaryMsg(msg); }
        boolean isEditable() {return xeditor.isEditable();}
        void setWaitCursors() { xeditor.setWaitCursors();}
        void restoreCursors() { xeditor.restoreCursors();}
        String getCompletedXML() throws IOException
        {
            return xeditor.getCompletedXML();
        }

        void loadDocument(InputStream bis, String charset, boolean edit, String dtdName2,boolean splChkReq)
        {
            // used for demo load of file
            xeditor.loadDocument(bis,charset,edit,dtdName2,splChkReq);
        }
        /**************************
         * property change
         * @param evt
         */
        public void propertyChange(PropertyChangeEvent evt)
        {
            if (evt.getPropertyName().equals("page"))
            {
                // page is done loading!!
                getAppletContext().showStatus("");
                if (jsExceptionMsg!=null && jsExceptionMsg.length()>0)
                {
                    // window is not painting.. try yet another thread
                    SwingUtilities.invokeLater(new Runnable() {
                            public void run()
                            {
                                // show the error to the user, once
                                String msgs[] = {
                                    "Spell Check could not be initialized.",
                                    "JSpell message: "+jsExceptionMsg
                                };

                                String accdesc[] = {
                                    "Press OK to close dialog."
                                };
                                getToolkit().beep();

                                XMLEditor.showAccessibleDialog(xeditor,
                                    XMLEditorGlobals.APP_NAME+" XML Editor JSpell Error",
                                    JOptionPane.ERROR_MESSAGE, JOptionPane.YES_OPTION,
                                    "Error initializing Spell check", msgs, accdesc);

                                xeditor.setFocusInEditor();

                                jsExceptionMsg = ""; // reloads due to form error checking, so don't reshow msg
                            }
                    });
                }
            }
        }
    }
}

