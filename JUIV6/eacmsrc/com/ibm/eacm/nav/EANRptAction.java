//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.nav;

import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.*;
import com.ibm.eacm.mw.LoginMgr;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.ShellControl;
import com.ibm.eacm.objects.Utils;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.RMIMgr;

/**
 * this is used for reports from Navigate
 * @author Wendy Stimpson
 */
//$Log: EANRptAction.java,v $
//Revision 1.5  2014/10/20 19:56:07  wendy
//Add worker id to timing log output
//
//Revision 1.4  2014/08/22 11:21:36  wendy
//IN5468431 - SSL connection issue for JUI/BUI
//
//Revision 1.3  2013/10/22 17:35:51  wendy
//close stream before connect
//
//Revision 1.2  2013/07/18 20:06:19  wendy
//throw outofrange exception from base class and catch in derived action classes
//
//Revision 1.1  2012/09/27 19:39:13  wendy
//Initial code
//
public class EANRptAction extends EANNavActionBase {
	private static final long serialVersionUID = 1L;
	private static final String ENCODE_TYPE = "utf8";
	private static final String TUNNEL_RUN_SERVLET = "JuiExecAction.wss?tranID={0}&enterprise={1}";
	private static final String TUNNEL_SET_SERVLET = "JuiSetAction.wss";

	public EANRptAction(EANActionItem act,Navigate n,EANActionSet p) {
		super(act,n,ACTION_PURPOSE_REPORT,p);
	}

	public void actionPerformed(ActionEvent e) {
		outputStartInfo();

		EntityItem[] ei = null;
		try{
			ei = getEntityItems(false);
        } catch (OutOfRangeException range) {
        	com.ibm.eacm.ui.UI.showFYI(getNavigate(),range);
        	return;
        }
		if(!checkValidSingleInput(ei)){ // make sure this action has correct number ei selected
			return;
		}
		if (ei != null) {

			// check the domains here
			// check edit to output warning msg, stop report here too
			try{
				EntityList.checkDomain(getNavigate().getProfile(),getEANActionItem(),ei); // RQ0713072645
			}catch(DomainException de){
				com.ibm.eacm.ui.UI.showException(getNavigate(),de);
				de.dereference();
				return;
			}

			if (ei.length > 100) {
				com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),Utils.getResource("msg12012.0"));
				return;
			}

			outputDebug(ei);

			getNavigate().disableActionsAndWait(); //disable all other actions and also set wait cursor
			worker = new RptWorker(ei); // worker needed because actions dont disable until launch returns
			RMIMgr.getRmiMgr().execute(worker);
		}
	}

	private class RptWorker extends DBSwingWorker<Void, Void> {
		private EntityItem[] eia = null;
		private long t11 = 0L;
		RptWorker(EntityItem[] ei){
			eia = ei;
		}
		@Override
		public Void doInBackground() {
			try{
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
				launchReport((ReportActionItem)getEANActionItem(), eia);
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" bg ended "+Utils.getDuration(t11));
				RMIMgr.getRmiMgr().complete(this);
				worker = null;
			}
			return null;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread
			clearAction(eia);	// why do this?

			Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" dispatch ended "+Utils.getDuration(t11));
			eia = null;
			getNavigate().enableActionsAndRestore();
		}
		public void notExecuted(){
			Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,getIdStr()+" not executed");
			getNavigate().enableActionsAndRestore();
			worker = null;
		}
	}
	/**
	 * launchReport
	 * @param rai
	 * @param ei
	 */
	private  void launchReport(ReportActionItem rai, EntityItem[] ei) {
		Profile prof = getNavigate().getProfile();
		ReportActionItem rai2 = null;
		EntityItem[] eia = null;
		Logger.getLogger(NAV_PKG_NAME).log(Level.FINER,"launchReport running report: " + rai.getURL());
		try {
			rai2 = new ReportActionItem(null,rai);
		} catch (MiddlewareException me) {
			Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"launchReport ERROR null ReportActionItem ",me);
			//msg12005.0=Unable to launch preview report on server: {0} report: {1}
			com.ibm.eacm.ui.UI.showErrorMessage(null,Utils.getResource("msg12005.0",
					LoginMgr.getMWORptPrefix(),rai.getURL()));
			return;
		}

		eia = EntityList.clipShift(rai,ei);
		if (eia != null) {
			java .util.Properties p = System.getProperties(); 
			//IN5468431 remove the values, cannot open socket to BUI if these are set
			Object truststore = p.remove("javax.net.ssl.trustStore");  
			Object tspw = p.remove("javax.net.ssl.trustStorePassword"); 
			
			Logger.getLogger(NAV_PKG_NAME).log(Level.FINER,"launchReport "+Utils.getEntityItemsInfo("reportEntityItems",eia)+"\"");
			String tranid = processReportInput(prof,rai2,eia);
			if (tranid!=null){
				processReport(tranid,prof,rai2);
			}else{
				//msg12005.0=Unable to launch preview report on server: {0} report: {1}
				com.ibm.eacm.ui.UI.showErrorMessage(null,Utils.getResource("msg12005.0",
						LoginMgr.getMWORptPrefix(),rai.getURL()));
			}
			// IN5468431 restore the values
			if(truststore !=null){
				p.setProperty("javax.net.ssl.trustStore", truststore.toString());
			}
			if(tspw !=null){
				p.setProperty("javax.net.ssl.trustStorePassword", tspw.toString());
			}

			rai2.dereference();
		}else{
			Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"launchReport ERROR null EntityItem array");
			com.ibm.eacm.ui.UI.showErrorMessage(null,Utils.getResource("msg12005.0",
					LoginMgr.getMWORptPrefix(),rai.getURL()));
		}
	}
 
	/**
	 * Store profile, report action and entityitem info as a blob, getting a trans id to use later to execute rpt
	 * @param prof
	 * @param rai
	 * @param ei
	 * @return
	 */
	private String processReportInput(Profile prof,ReportActionItem rai, EntityItem[] ei) {
		JuiReportActionObject report = new JuiReportActionObject(EACM.getEACM().updateActiveProfile(prof),rai,ei);

		URL url = null;
		URLConnection connection = null;
		OutputStream os = null;
		DeflaterOutputStream zos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream oos = null;
		String strTranid = null;
		try {
			
			String strReportURL = LoginMgr.getMWORptPrefix() + TUNNEL_SET_SERVLET;
			Logger.getLogger(NAV_PKG_NAME).log(Level.INFO,"report connection: " + strReportURL);
			
			url = new URL(strReportURL);
			connection = url.openConnection();
			connection.setRequestProperty("Content-Type","application/x-java-serialized-object");
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			os = connection.getOutputStream();
			zos = new DeflaterOutputStream(os);
			bos = new BufferedOutputStream(zos);
			oos = new ObjectOutputStream(bos);
			oos.writeObject(report);
			oos.reset();
			oos.flush();
			oos.close(); // this is required before connecting

			strTranid = processReportOutput(connection);
		} catch (IOException ioe) {
			Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"report connection: " + ioe.getMessage(),ioe);
		} finally {
			url = null;
			if (bos != null) {
				try {
					bos.flush();
					bos.close();
				} catch (IOException ioe) {}
				bos = null;
			}
			if (zos != null) {
				try {
					zos.flush();
					zos.close();
				} catch (IOException ioe) {}
				zos = null;
			}
			if (os != null) {
				try {
					os.flush();
					os.close();
				} catch (IOException ioe) {}
				os = null;
			}
		}

		return strTranid;
	}

	private String processReportOutput(URLConnection connection) {
		InputStream is = null;
		InflaterInputStream zis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		String sTranid = null;
		try {
			connection.connect();
			is = connection.getInputStream();
			zis = new InflaterInputStream(is);
			bis = new BufferedInputStream(zis);
			ois = new ObjectInputStream(bis);
			try {
				sTranid = (String)ois.readObject();
			} catch (ClassNotFoundException cnf) {
				cnf.printStackTrace();
			}
		} catch (IOException ioe) {
			Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"report connection: " + ioe.getMessage(),ioe);
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException ioe) {}
				ois = null;
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException ioe) {}
				bis = null;
			}
			if (zis != null) {
				try {
					zis.close();
				} catch (IOException ioe) {}
				zis = null;
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException ioe) {}
				is = null;
			}

		}
		return sTranid;
	}
	
 	private void processReport(String tranid, Profile prof, ReportActionItem rai) {
		if (tranid != null) {
			try {
				String sTunnel = MessageFormat.format(TUNNEL_RUN_SERVLET,
						URLEncoder.encode(tranid,ENCODE_TYPE),prof.getEnterprise());

				Logger.getLogger(NAV_PKG_NAME).log(Level.INFO,"processReport : " +LoginMgr.getMWORptPrefix() + sTunnel);
				ShellControl.launchURL(LoginMgr.getMWORptPrefix() + sTunnel);
			} catch (UnsupportedEncodingException uee) {
				uee.printStackTrace();
				com.ibm.eacm.ui.UI.showErrorMessage(null,Utils.getResource("msg12005.0",
						LoginMgr.getMWORptPrefix(),rai.getURL()));
			}
		} else {
			com.ibm.eacm.ui.UI.showErrorMessage(null,Utils.getResource("msg12005.0",
					LoginMgr.getMWORptPrefix(),rai.getURL()));
		}
	}
}
