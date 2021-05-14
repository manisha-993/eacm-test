//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eacm.objects;
import java.awt.FileDialog;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageProducer;
import java.io.*;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import COM.ibm.eannounce.objects.ActionGroup;
import COM.ibm.eannounce.objects.CopyActionItem;
import COM.ibm.eannounce.objects.CreateActionItem;
import COM.ibm.eannounce.objects.DeleteActionItem;
import COM.ibm.eannounce.objects.EANActionItem;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.InactiveItem;
import COM.ibm.eannounce.objects.LinkActionItem;
import COM.ibm.eannounce.objects.LockActionItem;
import COM.ibm.eannounce.objects.LockGroup;
import COM.ibm.eannounce.objects.LockItem;
import COM.ibm.eannounce.objects.LockList;
import COM.ibm.eannounce.objects.NavActionItem;
import COM.ibm.eannounce.objects.RowSelectableTable;
import COM.ibm.eannounce.objects.SearchActionItem;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Stopwatch;
import COM.ibm.opicmpdh.transactions.NLSItem;

import com.ibm.eacm.EACM;

/**
 * Utilities
 * @author Wendy Stimpson
 */
//$Log: Utils.java,v $
//Revision 1.5  2014/10/03 11:08:08  wendy
//IN5515352 remove F8 keyboard mapping
//
//Revision 1.4  2013/10/24 17:29:15  wendy
//IN4381213 - switch reset and flush - was not able to recreate EOF exception
//
//Revision 1.3  2013/09/09 20:33:24  wendy
//readonly profile is not able to edit
//
//Revision 1.2  2013/05/01 18:35:13  wendy
//perf updates for large amt of data
//
//Revision 1.1  2012/09/27 19:39:13  wendy
//Initial code
//
public class Utils implements EACMGlobals {
	private static ResourceBundle rsBundle = null;  // msg,button,label resources eacmRsrc.properties


	static {
		rsBundle = ResourceBundle.getBundle("eacmRsrc");
	}

	/**
	 * @param code
	 * @return
	 */
	public static String getResource(String code) {
		return getResource(code,(Object[])null);
	}
	/**
	 * @param code
	 * @param args
	 * @return
	 */
	public static String getResource(String code, Object ... args) {
		String lbl = rsBundle.getString(code);
		if (args!=null){
			lbl = MessageFormat.format(lbl, args);
		}
		return lbl;
	}

	/**
 	 * @param code
 	 * @return
 	 */
 	public static String getToolTip(String code) {
		return getToolTip(code,(Object[])null);
	}
	/**
	 * @param code
	 * @param args
	 * @return
	 */
	public static String getToolTip(String code, Object ... args) {
		String value = null;
		try{
			value = getResource(code+TOOLTIP_EXT,args);
		}catch(java.util.MissingResourceException mre){
			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE, mre.getMessage());
		}
		return value;
	}
 	/**
 	 * @param code
 	 * @return
 	 */
 	public static char getMnemonic(String code) {
		return getMnemonic(code,(Object[])null);
	}
	/**
	 * @param code
	 * @param args
	 * @return
	 */
	public static char getMnemonic(String code, Object ... args) {
		char c=NO_MNEMONIC;
		String value = null;
		try{
			value = getResource(code+MNEMONIC_EXT,args);
		}catch(java.util.MissingResourceException mre){
			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE, mre.getMessage());
		}
		if (value!=null){
			c = value.charAt(0);
		}
		return c;
	}
    private Utils() {}

    /**
     * gc
     */
    public static void gc() {
//    	put in own thread to improve performance
        Runnable later = new Runnable() {
            public void run() {
                System.out.flush();
                System.gc();
            }
        };

        Thread t = new Thread(later);
        t.start();
    }

    /**
     * get the duration
     * @param startLong
     */
    public static String getDuration(long startLong) {
    	long curtime = System.currentTimeMillis();
    	// use the passed in value as the starttime to calc diff
    	String duration = Stopwatch.format(curtime-startLong);
    	return duration;
    }
    /**
     * @param str
     * @param ei
     * @return
     */
    public static String getEntityItemsInfo(String str, EntityItem[] ei) {
        StringBuffer sb = new StringBuffer();
        sb.append(str + "=\"");
        if (ei == null) {
            return sb.toString();
        }
        for (int i = 0; i < ei.length; ++i) {
            if (i > 0) {
                sb.append(ARRAY_DELIMIT);
            }
            sb.append(ei[i].getKey());
        }
        return sb.toString();
    }

    /**
     * Mark the specified file for deleteOnExit
     * @param s
     *
     */
    public static void deleteOnExit(String s) {
        if (s != null) {
            File f = new File(s);
            f.deleteOnExit();
        }
    }

    /**
     * verify file exists either in the specified directory or in the jar file
     * @param dir
     * @param file
     * @return true if file exists in filesystem or in jar
     */
    public static boolean exists(String dir, String file) {
    	// look on file system
        if(fileExists(dir+file)){
        	return true;
        }
		try {
			// look in jar
			//URL url = resourceLoader.getResource(file); // just file name
			URL url = Utils.class.getResource("/resources/"+file);
			if (url != null) {
				url.getContent();
				return true;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return false;
    }
    private static boolean fileExists(String file){
    	 File f = new File(file);
         return f.exists();
    }

    /**
     * @param dir
     * @param filter
     * @return list of files in dir matching the filter extension
     */
    public static String[] list(String dir, final String filter) {
        File f = new File(dir);
        String[] file = null;
        if (f.exists()) {
        	if (filter !=null){
        		file = f.list(new FilenameFilter(){
        			public boolean accept(File dir, String name) {
        				return (name.toLowerCase().endsWith(filter.toLowerCase()));
        			}
        		});
        	}else{
            	file = f.list();
            }
        }
        return file;
    }

    /**
     * write object to file
     * @param file
     * @param o
     */
    public static void write(String file, Object o) {
        if (file != null) {
            FileOutputStream fout = null;
            ObjectOutputStream outStream = null;
            try {
            	fout = new FileOutputStream(file);
            	outStream = new ObjectOutputStream(fout);
            	outStream.writeObject(o);
            	outStream.flush(); //IN4381213 - Java IO EOFException Error - changed stream to flush then reset
            	outStream.reset(); // was never able to recreate the error
            } catch (IOException ioe) {
            	ioe.printStackTrace();
            } finally {
            	if (outStream != null) {
            		try {
            			outStream.close();
            		} catch (IOException ioe) {}
            		outStream=null;
            	}
            	if (fout != null) {
            		try{
            			fout.close(); 
            		} catch (IOException ioe) {}
            		fout = null;
            	}
            }
        }
    }

    /***********
     * Write the text to the file using specified encoding
     * @param file
     * @param txt
     * @param encoding
     */
    public static void writeString(String file, String txt, String encoding) {
    	writeString(file, txt, encoding, false);
    }

    /***********
     * Write the text to the file using specified encoding
     * @param file
     * @param txt
     * @param encoding
     * @param append - if true, append to current content
     */
    public static void writeString(String file, String txt, String encoding, boolean append) {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter out = null;
        try {
            fos = new FileOutputStream(file, append);
            osw = new OutputStreamWriter(fos, encoding);
            out = new BufferedWriter(osw);
            out.write(txt);
        } catch (IOException ioe) {
        	//let user know what failed
        	com.ibm.eacm.ui.UI.showException(null,ioe);
        } finally {
        	if (out != null) {
        		try{
        			out.close(); 
        		} catch (IOException ioe) {}
        		out = null;
        	}
        	if (osw != null) {
        		try{
        			osw.close(); 
        		} catch (IOException ioe) {}
        		osw = null;
        	}
        	if (fos != null) {
        		try{
        			fos.close(); 
        		} catch (IOException ioe) {}
        		fos = null;
        	}
		}
    }

    /********
     * Load an object from file
     * @param file
     * @return
     */
    public static Object read(String file) {
    	Object o = null;
    	try {
    		o = readWithExceptions(file);
    	} catch (ClassNotFoundException cnfe) {
    		cnfe.printStackTrace();
    	} catch (FileNotFoundException fnfe) {
    		fnfe.printStackTrace();
    	} catch (IOException ioe) {
    		ioe.printStackTrace();
    	} 			

        return o;
    }
    /********
     * Load an object from file, throw exceptions
     * @param file
     * @return
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public static Object readWithExceptions(String file) throws IOException, ClassNotFoundException {
		FileInputStream fis = null;
		ObjectInputStream inStream = null;
        Object o = null;
        if (fileExists(file)) {
        	try {
        		fis = new FileInputStream(file);
        		inStream = new ObjectInputStream(fis);
        		o = inStream.readObject();
        	} 			
        	finally {
            	if (inStream != null) {
            		try{
            			inStream.close(); 
            		} catch (IOException ioe) {}
            		inStream = null;
            	}
            	if (fis != null) {
            		try{
            			fis.close(); 
            		} catch (IOException ioe) {}
            		fis = null;
            	}
        	}
        }
        return o;
    }

    /**********
     * Read file and return contents as a String
     * @param dir
     * @param file
     * @param encoding
     * @return
     */
    public static String readString(String dir, String file, String encoding) {
        StringBuffer out = new StringBuffer();
        String temp = null;
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader inStream = null;
        try {
        	if(fileExists(dir+file)){//requires full path name
        		is = new FileInputStream(dir+file);
        	}else{
        	//	URL url = resourceLoader.getResource(file); // requires just file name
        		URL url = Utils.class.getResource("/resources/"+file);
        		if (url!=null){
        			is = url.openStream();
        		}
        	}
        	if (is!=null){
        		isr = new InputStreamReader(is, encoding);
        		inStream = new BufferedReader(isr);
        		while ((temp = inStream.readLine()) != null) {
        			out.append(temp);
        		}
        	}
        } catch (Exception uee) {
            uee.printStackTrace();
        } finally {
        	if (inStream != null) {
        		try{
        			inStream.close(); 
        		} catch (IOException ioe) {}
        		inStream = null;
        	}
        	if (isr != null) {
        		try{
        			isr.close(); 
        		} catch (IOException ioe) {}
        		isr = null;
        	}
        	if (is != null) {
        		try{
        			is.close(); 
        		} catch (IOException ioe) {}
        		is = null;
        	}
		}

        return out.toString();
    }

    /**
     * Read file and return contents as a String array, one per line
     * @param file
     * @param encoding
     * @return
     */
    public static String[] readStringArray(String file, String encoding) {
        String temp = null;
        java.util.Vector<String> vct = new java.util.Vector <String>();
        String[] out = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader inStream = null;
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis, encoding);
            inStream = new BufferedReader(isr);
            while ((temp = inStream.readLine()) != null) {
                vct.add(temp);
            }
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
        	if (inStream != null) {
        		try{
        			inStream.close(); 
        		} catch (IOException ioe) {}
        		inStream = null;
        	}
        	if (isr != null) {
        		try{
        			isr.close(); 
        		} catch (IOException ioe) {}
        		isr = null;
        	}
        	if (fis != null) {
        		try{
        			fis.close(); 
        		} catch (IOException ioe) {}
        		fis = null;
        	}
		}

        out = new String[vct.size()];
        vct.copyInto(out);

        return out;
    }

    /***********
     * save text to file
     * @param text
     */
    public static void saveToFile(String text) {
    	if(text==null ||text.length()==0){
    		return;
    	}
        String dir = null;
        String file = null;
        FileDialog fd = new FileDialog(EACM.getEACM(), getResource("saveTo"), FileDialog.SAVE);
        fd.setDirectory(SAVE_DIRECTORY);

        fd.setVisible(true);
        dir = fd.getDirectory();
        file = fd.getFile();
        if (dir != null && file != null) {
			String fileName =dir + file;
			Utils.write(fileName, text);
        }
    }

    /*************
     * Delete all files from the specified directory
     * @param dir
     * @param ext String file extension, if null all files are deleted
     * @param age if not 0L then file must have aged that long before deleting it
     */
    public static void deleteFiles(String dir, String ext, long age) {
        String[] files = Utils.list(dir, ext);
        if (files != null) {
            for (int i = 0; i < files.length; ++i) {
                File file = new File(dir, files[i]);
                boolean dodelete = false;
                if (age>0L){
                    if (file.lastModified()
                            < System.currentTimeMillis()-age){ // aged enough, just in case same UI instance is used for 2 ids
                    	dodelete = true;
                    }
                }else{
                	dodelete = true;
                }

                if(dodelete){
                	if (!file.delete()) {
                		file.deleteOnExit();
                	}
                }
            }
        }
    }

    /**********
     * Remove log files prior to this date
     * log filename is used to determine age!
     * @param dateString
     */
    public static void removeOldLogFiles(String dateString) {
        String[] files = Utils.list(LOGS_DIRECTORY, LOG_EXTENSION);
        if (files != null) {
            for (int i = 0; i < files.length; ++i) {
                if (isNumeric(files[i])) {
                    int x = files[i].compareTo(dateString);
                    if (x < 0) {
                        File file = new File(LOGS_DIRECTORY, files[i]);
                        file.delete();
                    }
                }
            }
        }
        // clean up any lck files left
        files = Utils.list(LOGS_DIRECTORY, LCK_EXTENSION);
        if(files !=null){
        	for (int i = 0; i < files.length; ++i) {
        		if (files[i].compareTo(dateString) < 0) {
        			File file = new File(LOGS_DIRECTORY, files[i]);
        			file.delete();
        		}
        	}
        }
    }
    /**
     * isNumeric - look at filename, if all digits then it must be a JUI log
     * @param s
     * @return
     */
    private static boolean isNumeric(String s) {
        int len = s.length() - 4; // remove .log
        for (int i = 0; i < len; ++i) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    /*************
     * Insert string into file
     * @param file
     * @param txt
     * @param rec
     * @param encoding
     */
    public static void insertString(String file, String txt,int rec, String encoding) {
		String[] tmp = readStringArray(file, encoding);
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter out = null;
		if (tmp != null) {
			int ii = tmp.length;
			int recLoc = rec;
			if (rec < 0) {
				recLoc = ii +rec;
			}
			try {
				fos = new FileOutputStream(file);
				osw = new OutputStreamWriter(fos, encoding);
				out = new BufferedWriter(osw);
				for (int i=0;i<ii;++i) {
					if (i > 0) {
						out.newLine();
					}
					if (i == recLoc) {
						out.write(txt);
						out.newLine();
					}
					out.write(tmp[i]);
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
	        	if (out != null) {
	        		try{
	        			out.close(); 
	        		} catch (IOException ioe) {}
	        		out = null;
	        	}
	        	if (osw != null) {
	        		try{
	        			osw.close(); 
	        		} catch (IOException ioe) {}
	        		osw = null;
	        	}
	        	if (fos != null) {
	        		try{
	        			fos.close(); 
	        		} catch (IOException ioe) {}
	        		fos = null;
	        	}
			}
		}
    }

    /***********
     * used to compress an object (UIPreferences)
     * @param in
     * @return
     */
    public static byte[] zipObject(Object in) { //compress
        byte[] byteArray = null; //compress
        ObjectOutputStream oos = null; //compress
		ByteArrayOutputStream baos = null;
		GZIPOutputStream zos = null;
		BufferedOutputStream bos = null;
        try { //compress
            baos = new ByteArrayOutputStream(); //compress
            zos = new GZIPOutputStream(baos);
            bos = new BufferedOutputStream(zos);
            oos = new ObjectOutputStream(bos);
            oos.reset();
            oos.writeObject(in); //compress
            oos.reset();			//JTest
            oos.flush(); //compress
            byteArray = baos.toByteArray(); //compress
            in = null; //compress
        } catch (Exception exc) { //compress
            exc.printStackTrace();
        } finally {
        	if (oos != null) {
        		try{
        			oos.close(); 
        		} catch (IOException ioe) {}
        		oos = null;
        	}
        	if (bos != null) {
        		try{
        			bos.close(); 
        		} catch (IOException ioe) {}
        		bos = null;
        	}
        	if (zos != null) {
        		try{
        			zos.close(); 
        		} catch (IOException ioe) {}
        		zos = null;
        	}
        	if (baos != null) {
        		try{
        			baos.close(); 
        		} catch (IOException ioe) {}
        		baos = null;
        	}
		}
        return byteArray;
    }
    /**
     * used to uncompress an object (UIPreferences)
     * unzipObject
     * @param byteArray
     * @return
     */
    public static Object unzipObject(byte[] byteArray) {
        Object obj = null;
		ByteArrayInputStream bais = null;
		GZIPInputStream zis = null;
		BufferedInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(byteArray);
            zis = new GZIPInputStream(bais);
            bis = new BufferedInputStream(zis);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
        	if (ois != null) {
        		try{
        			ois.close(); 
        		} catch (IOException ioe) {}
        		ois = null;
        	}
        	if (bis != null) {
        		try{
        			bis.close(); 
        		} catch (IOException ioe) {}
        		bis = null;
        	}
        	if (zis != null) {
        		try{
        			zis.close(); 
        		} catch (IOException ioe) {}
        		zis = null;
        	}
        	if (bais != null) {
        		try{
        			bais.close(); 
        		} catch (IOException ioe) {}
        		bais = null;
        	}
		}
        return obj;
    }

    /*************************
     * @param file
     * @return
     */
    public static synchronized Image getImage(String file) {
    	Image img = null;

		if (file == null || file.equalsIgnoreCase("null")) {
			return img;
		}

		URL url = Utils.class.getResource("/images/"+file);
		try {
			if (url != null) {
				ImageProducer ip = (ImageProducer)url.getContent();
				img = Toolkit.getDefaultToolkit().createImage(ip);
			}else{
				img = Toolkit.getDefaultToolkit().createImage(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return img;
	}

    /**
     * @param file
     * @return
     */
    public static synchronized ImageIcon getImageIcon(String file) {
		if (file == null || file.equalsIgnoreCase("null")) {
			return null;
		}
		ImageIcon img= new ImageIcon(Utils.class.getResource("/images/"+file));
	   	return img;
    }
    /********
     * create arm file in Function dir
     * @param sFileName
     */
    public static void arm(String sFileName) {
    	if (sFileName != null) {
    		File armFile = new File(FUNCTION_DIRECTORY + sFileName);
    		try {
    			armFile.createNewFile();
    		} catch (IOException ioe) {
    			ioe.printStackTrace();
    		}
    	}
   }

   /**
    * Remove arm file from Function dir
    *
    * @param sFileName
    */
    public static void disarm(String sFileName) {
      	if (sFileName != null) {
    		File armFile = new File(FUNCTION_DIRECTORY + sFileName);
    		if (!armFile.delete()) {
    			armFile.deleteOnExit();
    		}

    	}
    }

    /**
     * getNLSInfo
     *
     * @param nls
     * @return
     */
    public static String getNLSInfo(NLSItem nls) {
        if (nls == null) {
            return getResource("nia");
        }
        return getResource("nlsInfo",nls.toString(),nls.getNLSDescription(),""+nls.getNLSID());
    }

    /**
     * getProfileInfo
     *
     * @param p
     * @return
     */
    public static String getProfileInfo(Profile p) {
        if (p == null) {
            return Utils.getResource("nia");
        }
        Object obj[] = new Object[19];
        obj[0] =p.getEnterprise();
        obj[1] =p.getOPName();
        obj[2] =Integer.toString(p.getOPID());
        obj[3] =Integer.toString(p.getOPWGID());
        obj[4] =p.getRoleCode();
        obj[5] =p.getRoleDescription();
        obj[6] =p.getWGName();
        obj[7] =Routines.toString(p.isReadOnly());
        obj[8] =Integer.toString(p.getWGID());
        obj[9] =Integer.toString(p.getSessionID());
        obj[10] =Integer.toString(p.getTranID());
        obj[11] =Integer.toString(p.getDefaultIndex());
        obj[12] =p.getEmailAddress();
        obj[13] =p.getEndOfDay();
        obj[14] =p.getLoginTime();
        obj[15] =p.getOPWGName();
        obj[16] =p.getValOn();
        obj[17] =p.dumpPDHDomain(true);
        obj[18] =Profile.getVersion();

        return getResource("profInfo",obj);
    }

    /**
     * get ActionItems that can be executed for this data, profile is checked for dialing back timestamp
     * and data in the entitygroup
     *
     * @param eg
     * @param table
     * @return
     */
    public static EANActionItem[] getExecutableActionItems(EntityGroup eg, RowSelectableTable table) {
    	if (table != null) {
    		NLSItem egNls = eg.getProfile().getReadLanguage();
    		monitor("action(s) on " + eg.getEntityType(),table.toString());

    		Vector<EANActionItem> v = new Vector<EANActionItem>();
    		boolean bTmpPast = isPast(eg.getProfile());
    		boolean bHasData = eg.getEntityItemCount() > 0; //peer_create

    		monitor("    rows", "" + table.getRowCount());
    		monitor("    past", "" + bTmpPast);
    		monitor("    data", "" + bHasData);

    		for (int i = 0; i < table.getRowCount(); ++i) {
    			Object o = table.getRow(i);
    			if (o instanceof EANActionItem) {
    				monitor("        processing action " + i + " of " + table.getRowCount(),((EANActionItem)o).getKey());

    				// sometimes switching languages doesnt get changed in the action item
    				if(!((EANActionItem) o).getProfile().getReadLanguage().equals(egNls)){
    					((EANActionItem) o).getProfile().setReadLanguage(egNls);
    				}
    				if (isArmed(SHOW_ALL_ARM_FILE)) {
    					v.add((EANActionItem) o);
    				} else if (!bTmpPast && !bHasData) { //peer_create
    					if (o instanceof CreateActionItem &&
    							(((CreateActionItem) o).isPeerCreate() ||
    									((CreateActionItem) o).isStandAlone())) {
    						v.add((EANActionItem) o);
    					} else if (o instanceof NavActionItem) {
    						NavActionItem nav = (NavActionItem) o;
    						if (nav.isPicklist() && nav.useRootEI()) {
    							v.add((EANActionItem) o);
    						}
    					} else if (o instanceof SearchActionItem) {
    						if (((SearchActionItem) o).isParentLess()) {
    							v.add((EANActionItem) o);
    						}
    					} else if (o instanceof LinkActionItem) {
    						if (((LinkActionItem) o).isOppSelect()) {
    							v.add((EANActionItem) o);
    						}
    					} //peer_create
    				} else if (isExecutableAction((EANActionItem) o, bTmpPast)) {
    					v.add((EANActionItem) o);
    				}
    			}
    		}
    		if (!v.isEmpty()) {
    			return (EANActionItem[]) v.toArray(new EANActionItem[v.size()]);
    		}
    	} else {
    		monitor("actionLoad","table null");
    	}
    	return null;
    }

    /**
     * if profile is dialed back do not allow any update actions
     *
     * @param ean
     * @param bPast
     * @return
     */
    private static boolean isExecutableAction(EANActionItem ean, boolean bPast) {
        if (bPast) {
            if (ean instanceof LinkActionItem) {
                return false;
            } else if (ean instanceof CreateActionItem) {
                return false;
            } else if (ean instanceof DeleteActionItem) {
                return false;
            } else if (ean instanceof LockActionItem) {
                return false;
            } else if (ean instanceof CopyActionItem) {
                return false;
            } else if (ean instanceof NavActionItem) {
                if (((NavActionItem) ean).isPicklist()) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * getActionTitle
     *
     * @param eg
     * @param table
     * @return
     */
    public static String getActionTitle(EntityGroup eg, RowSelectableTable table) {
        if (eg != null && table != null) {
            if (isTestMode()) {
                ActionGroup ag = eg.getActionGroup();
                return table.getTableTitle() + ((ag == null) ? "" : (" (" + ag.getKey() + ")"));
            }
            return table.getTableTitle();
        }
        return null;
    }
    /**
     * isTestMode
     *
     * @return
     */
    public static boolean isTestMode() {
        return isArmed(TEST_MODE_ARM_FILE);
    }

    /**
     * isDebug
     *
     * @return
     */
    public static boolean isDebug() {
        return isArmed(DEBUG_ARM_FILE);
    }

    /**
     * isArmed
     *
     * @param sFileName
     * @return
     */
    public static boolean isArmed(String sFileName) {
        boolean out = false;
        if ( sFileName != null) {
            File armFile = new File(FUNCTION_DIRECTORY + sFileName);
            out = armFile.exists();
        }
        return out;
    }
	public static boolean isMonitor() {
		return isArmed(MONITOR_ARM_FILE);
	}
    /**
     * is this table editable, look for dialed back profile too
     * @param table
     * @param prof
     * @return
     */
    public static boolean isEditable(RowSelectableTable table, Profile prof) {
        if (prof==null || isPast(prof) || prof.isReadOnly()) {
            return false;
        }
        if (table != null) {
            return table.canEdit();
        }
        return false;
    }
    /**
     * is this profile dialed back
     *
     * @param prof
     * @return
     */
    public static boolean isPast(Profile prof) {
        if (prof != null) {
            String sTime = prof.getValOn();
            if (sTime != null) {
                return !sTime.equals(DateRoutines.getEOD());
            }
        }
        return false;
    }
    /**
     * get key for this entity and attribute
     * @param ei
     * @param code
     * @return
     */
    public static String getAttributeKey(EntityItem ei, String code) {
        if (isRelator(ei)) {
            return ei.getEntityType() + ":" + code + ":R";
        } else {
            return ei.getEntityType() + ":" + code + ":C";
        }
    }

    /**
     * isRelator
     * @param ei
     * @return
     */
    private static boolean isRelator(EntityItem ei) {
        if (ei != null) {
        	EntityGroup eg =ei.getEntityGroup();
            if (eg != null) {
                return eg.isRelator();
            }
        }
        return false;
    }
    /**
     * getTabTitle
     *
     * @param code
     * @param prof
     * @return
     */
    public static String getTabTitle(String code, Profile prof) {
        Object[] parms = getProfileParms(prof);
        return Utils.getResource(code,parms);
    }

    private static String[] getProfileParms(Profile prof) {
        String[] out = { prof.toString(), prof.getEnterprise(), prof.getOPName(), Integer.toString(prof.getOPID()),
        		Integer.toString(prof.getOPWGID()), prof.getRoleCode(), prof.getRoleDescription(), prof.getWGName(), prof.getOPWGName()};
        return out;
    }

    /**
     * IN5515352 remove F8 keyboard mapping
     * @param comp
     * @param keyToRemove
     */
    public static void removeKeyBoardMapping(JComponent comp, KeyStroke keyToRemove){
		InputMap imap = comp.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		while (imap != null)
		{
		    imap.remove(keyToRemove);
		    imap = imap.getParent();
		}
		imap = comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		while (imap != null)
		{
		    imap.remove(keyToRemove);
		    imap = imap.getParent();
		}
		imap = comp.getInputMap(JComponent.WHEN_FOCUSED);
		while (imap != null)
		{
		    imap.remove(keyToRemove);
		    imap = imap.getParent();
		}
    }
    
	public static void monitor(String s, Object o) {
		if(!isMonitor()) return;
		if (o instanceof Date) {
			writeLog(MONITOR_ARM_FILE,s + " " + o.toString());
		} else if (o instanceof InactiveItem[]) {
			writeLog(MONITOR_ARM_FILE,s + " " +  new Date());
			InactiveItem[] inAct = (InactiveItem[])o;
			int ii = inAct.length;
			for (int i=0;i<ii;++i) {
				writeLog(MONITOR_ARM_FILE,"    " + s + " " + inAct[i].getEntityType() + ":" + inAct[i].getEntityID());
			}
		} else if (o instanceof LockList) {
			LockList l = (LockList)o;
			int ii = l.getLockGroupCount();
			writeLog(MONITOR_ARM_FILE,s + " " + new Date());
			for (int i=0;i<ii;++i) {
				LockGroup lg = l.getLockGroup(i);
				if (lg != null) {
					int xx = lg.getLockItemCount();
					for (int x=0;x<xx;++x) {
						LockItem li = lg.getLockItem(x);
						if (li != null) {
							writeLog(MONITOR_ARM_FILE,"    currentlyLocked " + li.getEntityType() + ":" + li.getEntityID());
						}
					}
				}
			}
			writeLog(MONITOR_ARM_FILE,"end " + s);
		} else {
			writeLog(MONITOR_ARM_FILE,s + " " + new Date() + " " + o.toString());
		}
	}

    private static void writeLog(String file, String txt) {
    	Utils.writeString(FUNCTION_DIRECTORY + file, txt + RETURN, EACM_FILE_ENCODE, true);
	}
    public static String outputList(EntityList list) // debug only
    {
        StringBuffer sb = new StringBuffer("************************************\n");
        EntityGroup peg = null;
        if (list==null) {
            sb.append("Null List");
        }else{
            peg =list.getParentEntityGroup();
            if (peg!=null)
            {
                /*sb.append(peg.getEntityType()+" : "+peg.getMetaAttributeCount()+" MetaAttributes. "+NEWLINE);
            	for (int m=0; m<peg.getMetaAttributeCount();m++){
            		EANMetaAttribute ma = peg.getMetaAttribute(m);
                    sb.append("  "+ma.getAttributeCode()+NEWLINE);
                    if(ma instanceof EANMetaFlagAttribute){
                    	EANMetaFlagAttribute mfa = (EANMetaFlagAttribute)ma;
                        sb.append("  getMetaFlagCount "+mfa.getMetaFlagCount()+NEWLINE);
                        for (int x=0; x<mfa.getMetaFlagCount(); x++){
                            sb.append("  getMetaFlag["+x+"] "+mfa.getMetaFlag(x)+NEWLINE);
                        }
                    }
            	}*/
                sb.append(peg.getEntityType()+" : "+peg.getEntityItemCount()+" parent items. ");
                if (peg.getEntityItemCount()>0)
                {
                    StringBuffer tmpsb = new StringBuffer();  // prevent more than 2048 chars in a line
                    tmpsb.append("IDs(");
                    for (int e=0; e<peg.getEntityItemCount(); e++)
                    {
                        tmpsb.append(" "+peg.getEntityItem(e).getEntityID());
                        if (tmpsb.length()>256)
                        {
                            sb.append(tmpsb.toString()+NEWLINE);
                            tmpsb.setLength(0);
                        }
                    }
                    tmpsb.append(")");
                    sb.append(tmpsb.toString());
                }
                sb.append(NEWLINE);
            }

            for (int i=0; i<list.getEntityGroupCount(); i++)
            {
                EntityGroup eg =list.getEntityGroup(i);
                /*sb.append(eg.getEntityType()+" : "+eg.getMetaAttributeCount()+" MetaAttributes. "+NEWLINE);
            	for (int m=0; m<eg.getMetaAttributeCount();m++){
            		EANMetaAttribute ma = eg.getMetaAttribute(m);
                    sb.append("  "+ma.getAttributeCode()+NEWLINE);
                    if(ma instanceof EANMetaFlagAttribute){
                    	EANMetaFlagAttribute mfa = (EANMetaFlagAttribute)ma;
                        sb.append("  getMetaFlagCount "+mfa.getMetaFlagCount()+NEWLINE);
                        for (int x=0; x<mfa.getMetaFlagCount(); x++){
                            sb.append("  getMetaFlag["+x+"] "+mfa.getMetaFlag(x)+NEWLINE);
                        }
                    }
            	}
            	*/

                sb.append(eg.getEntityType()+" : "+eg.getEntityItemCount()+" entity items. ");
                if (eg.getEntityItemCount()>0)
                {
                    StringBuffer tmpsb = new StringBuffer();  // prevent more than 2048 chars in a line
                    tmpsb.append("IDs(");
                    for (int e=0; e<eg.getEntityItemCount(); e++)
                    {
                        tmpsb.append(" "+eg.getEntityItem(e).getEntityID());
                        if (tmpsb.length()>256)
                        {
                            sb.append(tmpsb.toString()+NEWLINE);
                            tmpsb.setLength(0);
                        }
                    }
                    tmpsb.append(")");
                    sb.append(tmpsb.toString());
                }
                sb.append(NEWLINE);
            }
        }
        return sb.toString();
    }

}
