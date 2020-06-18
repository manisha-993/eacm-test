//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MiddlewareMail.java,v $
// Revision 1.22  2008/01/31 22:55:00  wendy
// Cleanup RSA warnings
//
// Revision 1.21  2006/01/03 21:54:43  dave
// <No Comment Entered>
//
// Revision 1.20  2005/09/07 17:03:01  joan
// set file extension
//
// Revision 1.19  2005/08/04 15:16:04  joan
// fix compile
//
// Revision 1.18  2005/08/04 14:47:45  joan
// try to create xml file
//
// Revision 1.17  2005/02/10 04:30:43  bala
// some more tweaks to send multiple attachments
//
// Revision 1.16  2005/02/09 16:03:56  bala
// add new signature to sendContentsByteArray method
//
// Revision 1.15  2004/03/08 22:58:44  roger
// Enable some tracing
//
// Revision 1.14  2003/07/15 21:15:24  bala
// add sendContentsByteArray method
//
// Revision 1.13  2003/07/07 19:39:08  dave
// removing test mailing
//
// Revision 1.12  2003/06/28 00:19:58  bala
// fix typo
//
// Revision 1.11  2003/06/28 00:06:38  bala
// add import statement for blobdatasource
//
// Revision 1.10  2003/06/27 23:26:28  bala
// add method setContentByteArray
//
// Revision 1.9  2003/05/01 15:42:41  roger
// Clean up
//
// Revision 1.8  2003/04/30 20:27:05  roger
// Changes
//
// Revision 1.7  2003/04/30 17:55:00  roger
// TM will now send email when DG entity inserted
//
// Revision 1.6  2003/04/30 17:16:24  roger
// Clean up
//
// Revision 1.5  2003/04/30 17:08:39  roger
// Sheesh
//
// Revision 1.4  2003/04/30 16:55:24  roger
// Fix it
//
// Revision 1.3  2003/04/30 16:03:15  roger
// Fixed it
//
// Revision 1.2  2003/04/30 15:50:19  roger
// Change methods
//
// Revision 1.1  2003/04/29 21:55:42  roger
// New file
//

package COM.ibm.opicmpdh.middleware;

import java.net.URL;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public final class MiddlewareMail
    extends MimeMessage {
    //private static final String NO_SUBJECT = "no subject specified";
    private Multipart mp = new MimeMultipart(); //so that attachments can be added
    private String m_strFileExtension = "";

    public MiddlewareMail(Session session) {
        super(session);

    }

    public static void main(String[] args) {

        try {
            MiddlewareMail x = new MiddlewareMail(MiddlewareMail.getSession());
            x.sendContentText("Text Message", "<p>Basic text message<p>Should NOT show as HTML formatted text<p>");

            x = new MiddlewareMail(MiddlewareMail.getSession());
            x.sendContentHTML("HTML Message", "<p>Basic text message<p>SHOULD show as HTML formatted text<p>");

            x = new MiddlewareMail(MiddlewareMail.getSession());
            x.sendContentFile("Attachment Message", "MiddlewareMail.java");

            x = new MiddlewareMail(MiddlewareMail.getSession());
            x.sendContentURL("URL Message", new URL("http://www.yahoo.com"));
        }
        catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static Session getSession() {
        Properties props = System.getProperties();

        Session session = Session.getDefaultInstance(props, null);
        session.setDebug(true);
        return session;
    }

    public void send() throws Exception {
        this.setSentDate(new Date());
        Transport.send(this);
    }

    public void sendContentFile(String strSubject, String strFileName) throws Exception {
        this.setContentFile(strFileName);
        this.setSubject(strSubject);
        this.send();
    }

    public void sendContentByteArray(String strSubject, byte[] _b) throws Exception {
        this.setContentByteArray(_b);
        this.setSubject(strSubject);
        this.send();
    }

    public void sendContentHTML(String strSubject, String strContent) throws Exception {
        this.setContentHTML(strContent);
        this.setSubject(strSubject);
        this.send();
    }

    public void sendContentText(String strSubject, String strContent) throws Exception {
        this.setContentText(strContent);
        this.setSubject(strSubject);
        this.send();
    }

    public void sendContentURL(String strSubject, URL urlContent) throws Exception {
        this.setContentURL(urlContent);
        this.setSubject(strSubject);
        this.send();
    }

    public void setContentFile(String strFileName) throws Exception {

        FileDataSource fds = new FileDataSource(strFileName);
        MimeBodyPart mbp = new MimeBodyPart();

        mbp.setDataHandler(new DataHandler(fds));
        mbp.setFileName(MimeUtility.encodeWord(fds.getName()));

        mp.addBodyPart(mbp);
        this.setContent(mp);
    }

    public void setContentByteArray(byte[] _b, String _strAttachmentName) throws Exception {

        BlobDataSource bds = new BlobDataSource(_b, _strAttachmentName);
        MimeBodyPart mbp = new MimeBodyPart();

        mbp.setDataHandler(new DataHandler(bds));
        mbp.setFileName(MimeUtility.encodeWord(bds.getName()));

        mp.addBodyPart(mbp);
        this.setContent(mp);
    }

    public void setContentByteArray(byte[] _b) throws Exception {
        setContentByteArray(_b, ( (m_strFileExtension.length() > 0) ? "report." + m_strFileExtension : "report.html"));
    }

    public void setContentHTML(String strContent) throws Exception {
        this.setText(strContent);
        this.setHeader("Content-Type", "text/html");
    }

    public void setContentText(String strContent) throws Exception {
        this.setText(strContent);
        this.setHeader("Content-Type", "text/plain");
    }

    public void setContentURL(URL urlContent) throws Exception {

        URLDataSource uds = new URLDataSource(urlContent);
        MimeBodyPart mbp = new MimeBodyPart();

        mbp.setDataHandler(new DataHandler(uds));

        //mbp.setHeader("Content-Type", "text/html");

        mp.addBodyPart(mbp);
        this.setContent(mp);
    }

    /**
     * Return the date/time this class was generated
     * @return the date/time this class was generated
     */
    public final String getVersion() {
        return new String("$Id: MiddlewareMail.java,v 1.22 2008/01/31 22:55:00 wendy Exp $");
    }

    public void setFileExtension(String _s) {
        m_strFileExtension = _s;
    }

    public String getFileExtension() {
        return m_strFileExtension;
    }
}
