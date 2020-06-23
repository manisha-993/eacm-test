/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 *  a simple repositiry for often used io manipulations
 *
 * $Log: Gio.java,v $
 * Revision 1.2  2008/02/07 22:31:11  wendy
 * Notify user of error
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/20 16:01:59  tony
 * CR092005410
 * Ability to add middleware location on the fly.
 *
 * Revision 1.1.1.1  2005/09/09 20:37:39  tony
 * This is the initial load of OPICM
 *
 * Revision 1.12  2005/09/08 17:58:55  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.11  2005/02/08 19:15:40  tony
 * JTEST
 *
 * Revision 1.10  2005/02/08 16:33:58  tony
 * JTest Formatting Fourth pass
 *
 * Revision 1.9  2005/02/04 18:16:48  tony
 * JTest Formatter Fourth Pass
 *
 * Revision 1.8  2005/02/04 16:57:41  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.7  2005/02/03 19:42:21  tony
 * JTest Third pass
 *
 * Revision 1.6  2005/02/02 21:30:07  tony
 * JTest Second Pass
 *
 * Revision 1.5  2005/01/27 23:18:18  tony
 * JTest Formatting
 *
 * Revision 1.4  2004/10/14 21:27:06  tony
 * added ability to copy backups of existing updated items.
 *
 * Revision 1.3  2004/10/13 21:40:53  tony
 * update wrap-up
 *
 * Revision 1.2  2004/10/13 17:56:18  tony
 * added on-line update functionality.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2004/01/20 00:20:31  tony
 * adjusted remove installation folder logic.
 *
 * Revision 1.5  2004/01/19 23:52:45  tony
 * 20040119
 * added deleteFolder logic
 *
 * Revision 1.4  2003/12/04 22:21:23  tony
 * cleaned-up code.
 *
 * Revision 1.3  2003/08/13 16:38:42  joan
 * 51714
 *
 * Revision 1.2  2003/03/12 23:51:09  tony
 * accessibility and column order
 *
 * Revision 1.1.1.1  2003/03/03 18:03:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.10  2002/11/12 23:37:19  tony
 * System.outs removed
 *
 * Revision 1.9  2002/11/11 23:29:13  tony
 * update delete logic
 *
 * Revision 1.8  2002/11/07 16:58:09  tony
 * added/adjusted copyright statement
 *
 */
package com.elogin;
import java.awt.FileDialog;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class Gio implements EAccessConstants {
    private String encoding = EANNOUNCE_FILE_ENCODE;
    private String delim = RETURN;
    private FileDialog fd = null;

    /**  Constructor for the gIO object */
    public Gio() {
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
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        return;
    }

    /**
     *  The main program for the gIO class
     *  NOTE: used for testing purposes only.
     * @deprecated
     */
    public void myMain() {
        String file = "PSGOF.html";
        readStringArray(file);
        return;
    }

    /**
     *  Delete/remove the files that meet the specified criteria
     *
     * @param  _dir     The directory location of the file
     * @param  _filter  The file extension to be processed
     */
    public void delete(String _dir, String _filter) {
        getFiles(_dir, _filter, true);
        return;
    }

    /**
     *  Delete/remove the files that meet the specified criteria
     *  but only do so immediately before the application exits.
     *
     * @param  _dir     The directory location of the file
     * @param  _filter  The file extension to be processed
     */
    public void deleteOnExit(String _dir, String _filter) {
        File[] f = getFiles(_dir, _filter, false);
        int ii = f.length;
        for (int i = 0; i < ii; ++i) {
            f[i].deleteOnExit();
        }
        return;
    }

    /**
     * delete
     * @param _s
     * @author Anthony C. Liberto
     */
    public void delete(String _s) {
        File f = null;
        if (_s == null) { //51714
            return; //51714
        }
        f = new File(_s);
        if (f.exists()) {
            f.delete();
        }
        return;
    }

    /**
     * deleteOnExit
     * @param _s
     * @author Anthony C. Liberto
     */
    public void deleteOnExit(String _s) {
        if (_s != null) {
            File f = new File(_s);
            f.deleteOnExit();
        }
        return;
    }

    /**
     *  return an array of qualifying file[] based on passed in information
     *
     * @param  _dir     The directory location of the file
     * @param  _filter  The file extension to be processed
     * @return          an array of qualifying file[]
     */
    public File[] listFiles(String _dir, String _filter) {
        return getFiles(_dir, _filter, false);
    }

    /**
     * listFiles
     * @param _dir
     * @return
     * @author Anthony C. Liberto
     */
    public File[] listFiles(String _dir) {
        return getFiles(_dir, false);
    }

    /**
     *  Sets the readOnly attribute of the qualifying files
     *
     * @param  _dir     The directory location of the file
     * @param  _filter  The file extension to be processed
     */
/*
    private void setReadOnly(String _dir, String _filter) {
        File[] f = getFiles(_dir, _filter, false);
        int ii = f.length;
        for (int i = 0; i < ii; ++i) {
            f[i].setReadOnly();
        }
        return;
    }
*/
    /**
     *  Gets the uRL attribute of the passed in file
     *
     * @return            The uRL value
     * @param _fileName
     */
    public URL getURL(String _fileName) {
        File f = new File(_fileName);
        try {
            return f.toURL();
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }
        return null;
    }

    /**
     *  Description of the Method
     *
     * @param  _file1  Description of the Parameter
     * @param  _file2  Description of the Parameter
     * @return         Description of the Return Value
     */
    public boolean rename(String _file1, String _file2) {
        File f = new File(_file1);
        return f.renameTo(new File(_file2));
    }

    /**
     *  Gets the files attribute of the gIO class
     *
     * @param  _dir     Description of the Parameter
     * @param  _filter  Description of the Parameter
     * @param  _delete  Description of the Parameter
     * @return          The files value
     */
    private File[] getFiles(String _dir, String _filter, boolean _delete) {
        File f = null;
        ExtensionFilter filter = null;
        File[] file = null;
        if (_dir == null || _filter == null) {
            return null;
        }
        f = new File(_dir);
        filter = new ExtensionFilter(_filter);
        file = f.listFiles(filter);
        if (_delete) {
            int ii = file.length;
            for (int i = 0; i < ii; ++i) {
                file[i].delete();
            }
        }
        return file;
    }

    /**
     *  Gets the files attribute of the gIO class
     *
     * @param  _dir     Description of the Parameter
     * @param  _delete  Description of the Parameter
     * @return          The files value
     */
    private File[] getFiles(String _dir, boolean _delete) {
        File f = null;
        File[] file = null;
        if (_dir == null) {
            return null;
        }
        f= new File(_dir);
        file = f.listFiles();
        if (_delete) {
            int ii = file.length;
            for (int i = 0; i < ii; ++i) {
                file[i].delete();
            }
        }
        return file;
    }

    /**
     *  Description of the Method
     *
     * @param  _dir   Description of the Parameter
     * @param  _file  Description of the Parameter
     * @return        Description of the Return Value
     */
    public boolean exists(String _dir, String _file) {
        return exists(_dir + _file);
    }

    /**
     *  Description of the Method
     *
     * @param  _file  Description of the Parameter
     * @return        Description of the Return Value
     */
    public boolean exists(String _file) {
        File f = new File(_file);
        return f.exists();
    }

    /**
     *  Description of the Method
     *
     * @param  _dir     Description of the Parameter
     * @param  _filter  Description of the Parameter
     * @return          Description of the Return Value
     */
    public String[] list(String _dir, String _filter) {
        File f = new File(_dir);
        ExtensionFilter filter = null;
        String[] file = null;
        if (!f.exists()) {
            return null;
        }
        filter = new ExtensionFilter(_filter);
        file = f.list(filter);
        return file;
    }

    /**
     *  Description of the Method
     *
     * @param  _dir     Description of the Parameter
     * @return          Description of the Return Value
     */
    public String[] list(String _dir) {
        File f = new File(_dir);
        String[] file = null;
        if (!f.exists()) {
            return null;
        }
        file = f.list();
        return file;
    }

    /**
     *  Description of the Method
     *
     * @param  _daysDiff   Description of the Parameter
     * @param  _extension  Description of the Parameter
     */
    public void deleteLogs(int _daysDiff, String _extension) {
        String dateString = eaccess().getDateString(_daysDiff);
        ExtensionFilter filter = new ExtensionFilter(_extension);
        String log = System.getProperty("opicm.log");
        File dir = new File(log);
        String[] list = dir.list(filter);
        if (list.length == 0) {
            return;
        }
        for (int i = 0; i < list.length; i++) {
            File file = new File(log + list[i]);
            int x = list[i].compareTo(dateString);
            if (isNumeric(list[i])) {
                if (x < 0) {
                    file.delete();
                }
            }
        }
        return;
    }

    /**
     *  Gets the numeric attribute of the gIO class
     *
     * @param  s  Description of the Parameter
     * @return    The numeric value
     */
    public boolean isNumeric(String s) {
        int len = s.length() - 4;
        for (int i = 0; i < len; ++i) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /*
     *   file compression
     *   - compress (zip) files
     *   - uncompress (unzip) files
     */
    /**
     *  Description of the Method
     *
     * @param  _dir     Description of the Parameter
     * @param  _file    Description of the Parameter
     * @param  _filter  Description of the Parameter
     */
    public void compress(String _dir, String _file, String _filter) {
        compress(_dir, _file, _filter, false);
    }

    /**
     *  Description of the Method
     *
     * @param  _dir     Description of the Parameter
     * @param  _file    Description of the Parameter
     * @param  _filter  Description of the Parameter
     * @param  _delete  Description of the Parameter
     */
    public void compress(String _dir, String _file, String _filter, boolean _delete) {
        try {
            Zip.zipFile(_dir, _file, _filter, _delete);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     *  Description of the Method
     *
     * @param  _dir   Description of the Parameter
     * @param  _file  Description of the Parameter
     */
    public void uncompress(String _dir, String _file) {
        uncompress(_dir, _file, false);
    }

    /**
     *  Description of the Method
     *
     * @param  _dir     Description of the Parameter
     * @param  _file    Description of the Parameter
     * @param  _delete  Description of the Parameter
     */
    public void uncompress(String _dir, String _file, boolean _delete) {
        try {
            Unzip.unzipFile(_dir, _file, _delete);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /*
     *   file manipulation
     *   - reading from files
     *   (Objects and Strings)
     *   - writing to files
     *   (Objects and Strings)
     *   NOTE:  reading and writing strings requires encoding.
     *   The default is Cp850 but can be reset to anything.
     *   (Assuming it is valid)
     */
    /**
     *  Sets the encoding attribute of the gIO class
     *
     * @param  _s  The new encoding value
     */
    public void setEncoding(String _s) {
        encoding = new String(_s);
    }

    /**
     *  Gets the encoding attribute of the gIO class
     *
     * @return    The encoding value
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     *  Sets the delimiter attribute of the gIO class
     *
     * @param  _s  The new delimiter value
     */
    public void setDelimiter(String _s) {
        delim = new String(_s);
    }

    /**
     *  Gets the delimiter attribute of the gIO class
     *
     * @return    The delimiter value
     */
    public String getDelimiter() {
        return delim;
    }

    /**
     * write
     *  Description of the Method
     *
     * @param  _file  Description of the Parameter
     * @param  _o     Description of the Parameter
     */
    public void write(String _file, Object _o) {
        FileOutputStream fout = null;
        ObjectOutputStream outStream = null;
        if (_file == null) { //51714
            return; //51714
        }
        try {
            fout = new FileOutputStream(_file);
            outStream = new ObjectOutputStream(fout);
            outStream.writeObject(_o);
            outStream.reset();			//JTest
            outStream.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
			try {
				if (outStream != null) {
					outStream.close();
				}
				if (fout != null) {
		            fout.close(); //s80
				}
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
		}
        return;
    }

    /**
     *  Description of the Method
     *
     * @param  _file  Description of the Parameter
     * @param  _txt   Description of the Parameter
     */
    public void writeString(String _file, String _txt) {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter out = null;
        try {
            fos = new FileOutputStream(_file);
            osw = new OutputStreamWriter(fos, encoding);
            out = new BufferedWriter(osw);
            out.write(_txt);
        } catch (IOException ioe) {
        	//let user know what failed
        	eaccess().showException(ioe, null,ERROR_MESSAGE,OK);
        	//ioe.printStackTrace();
        } finally {
			try {
				if (out != null) {
					out.close();
				}
				if (osw != null) {
		            osw.close(); //s80
				}
				if (fos != null) {
		            fos.close(); //s80
				}
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
		}
        return;
    }

    /**
     *  Description of the Method
     *
     * @param  _file  Description of the Parameter
     * @return        Description of the Return Value
     */
    public Object read(String _file) {
		FileInputStream fis = null;
		ObjectInputStream inStream = null;
        Object o = null;
        if (!exists(_file)) {
            return null;
        }
        try {
            fis = new FileInputStream(_file);
            inStream = new ObjectInputStream(fis);
            o = inStream.readObject();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
				if (fis != null) {
					fis.close(); //acl_Mem_20020201
				}
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
		}
        return o;
    }

    /**
     *  Description of the Method
     *
     * @param  _file  Description of the Parameter
     * @return        Description of the Return Value
     */
    public String readDelim(String _file) {
        return readString(_file, true);
    }

    /**
     *  Description of the Method
     *
     * @param  _file  Description of the Parameter
     * @return        Description of the Return Value
     */
    public String readString(String _file) {
        return readString(_file, false);
    }

    /**
     *  Description of the Method
     *
     * @param  _file    Description of the Parameter
     * @param  delimit  Description of the Parameter
     * @return          Description of the Return Value
     */
    private String readString(String _file, boolean delimit) {
        StringBuffer out = new StringBuffer();
        String temp = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader inStream = null;
        try {
            fis = new FileInputStream(_file);
            isr = new InputStreamReader(fis, encoding);
            inStream = new BufferedReader(isr);
            while ((temp = inStream.readLine()) != null) {
                if (delimit) {
                    out.append(temp + delim);
                } //appends delimiter at eol
                else {
                    out.append(temp);
                } //doesn't append return
            }
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
			try {
				if (inStream != null) {
					inStream.close(); //s80
				}
				if (isr != null) {
					isr.close(); //s80
				}
				if (fis != null) {
					fis.close(); //s80
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
        return out.toString();
    }

    /**
     *  Gets the string attribute of the gIO class
     *
     * @param  _byte      Description of the Parameter
     * @param  _encoding  Description of the Parameter
     * @return            The string value
     */
    public String getString(byte[] _byte, String _encoding) {
        try {
            return new String(_byte, _encoding);
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }
        return null;
    }

    /**
     *  Description of the Method
     *
     * @param  _file  Description of the Parameter
     * @return        Description of the Return Value
     */
    public String[] readStringArray(String _file) {
        String temp = null;
        ArrayList ra = new ArrayList();
        String[] out = null;
        int ii = -1;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader inStream = null;
        try {
            fis = new FileInputStream(_file);
            isr = new InputStreamReader(fis, encoding);
            inStream = new BufferedReader(isr);
            while ((temp = inStream.readLine()) != null) {
                ra.add(temp);
            }
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
			try {
				if (inStream != null) {
					inStream.close(); //s80
				}
				if (isr != null) {
					isr.close(); //s80
				}
				if (fis != null) {
					fis.close(); //s80
				}
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
		}
        ii = ra.size();
        out = new String[ii];
        for (int i = 0; i < ii; ++i) {
            out[i] = (String) ra.get(i);
        }
        return out;
    }

    /**
     *  Description of the Method
     *
     * @param  _fileName  Description of the Parameter
     * @return            Description of the Return Value
     */
    public byte[] readByteArray(String _fileName) {
		FileInputStream fis = null;
		int avail = -1;
		byte[] out = null;
        try {
            fis = new FileInputStream(_fileName);
            avail = fis.available();
            out = new byte[avail];
            fis.read(out);
            return out;
        } catch (FileNotFoundException _fnfe) {
            _fnfe.printStackTrace();
        } catch (IOException _ioe) {
            _ioe.printStackTrace();
        } finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
		}
        return out;
    }

    /**
     *  Description of the Method
     *
     * @param  _fileName  Description of the Parameter
     * @param  _bArray    Description of the Parameter
     */
    public void writeByteArray(String _fileName, byte[] _bArray) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(_fileName);
            fos.write(_bArray);
        } catch (FileNotFoundException _fnfe) {
            _fnfe.printStackTrace();
        } catch (IOException _ioe) {
            _ioe.printStackTrace();
        } finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
		}
        return;
    }

    /**
     *  Gets the fileName attribute of the gIO class
     *
     * @param  _mode  Description of the Parameter
     * @return        The fileName value
     */
    public String getFileName(int _mode) {
        String dir = null;
        String file = null;
        if (fd == null) {
            fd = new FileDialog(eaccess().getLogin());
            fd.setDirectory(SAVE_DIRECTORY);
        }
        fd.setMode(_mode);
        fd.show();
        dir = fd.getDirectory();
        file = fd.getFile();
        if (dir != null && file != null) {
            return dir + file;
        }
        return null;
    }

    /**
     *  Gets the fileName attribute of the gIO class
     *
     * @param  _mode  Description of the Parameter
     * @return        The fileName value
     * @param _extension
     */
    public String[] getFileNames(int _mode, String _extension) {
        String dir = null;
        String file = null;
        if (fd == null) {
            fd = new FileDialog(eaccess().getLogin());
            fd.setDirectory(SAVE_DIRECTORY);
            fd.setTitle("Select file to load");
        }
        if (_extension != null) {
            fd.setFilenameFilter(new ExtensionFilter(_extension));
            fd.setFile("*" + _extension);
        } else {
            fd.setFilenameFilter(null);
            fd.setFile(null);
        }
        fd.setMode(_mode);
        fd.show();
        dir = fd.getDirectory();
        file = fd.getFile();
        if (dir != null && file != null) {
            String[] tmp = { dir, file };
            return tmp;
        }
        return null;
    }

    /*
     acl_20040119
     */
    /**
     * deleteInstallationFolders
     * @author Anthony C. Liberto
     */
    public static void deleteInstallationFolders() {
        String[] tmp = { LOGS_FOLDER, TEMP_FOLDER, SAVE_FOLDER, CACHE_FOLDER, PREFERENCE_FILE };
        deleteFolder(tmp);
        return;
    }

    /**
     * deleteFolder
     * @param _s
     * @author Anthony C. Liberto
     */
    public static void deleteFolder(String _s) {
        if (_s != null) {
            File f = new File(_s);
            if (f.exists()) {
                if (!f.delete()) {
                    if (f.isDirectory()) {
                        deleteFolder(f.list());
                    }
                }
            }
        }
        return;
    }

    /**
     * deleteFolder
     * @param _s
     * @author Anthony C. Liberto
     */
    public static void deleteFolder(String[] _s) {
        if (_s != null) {
            int ii = _s.length;
            for (int i = 0; i < ii; ++i) {
                deleteFolder(_s[i]);
            }
        }
        return;
    }

    /**
     * copy
     * @param _from
     * @param _to
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean copy(File _from, File _to) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
        byte[] buf = null;
        int i = 0;
        try {
            fis = new FileInputStream(_from);
            fos = new FileOutputStream(_to);
            buf = new byte[1024];
            try {
                while ((i = fis.read(buf)) != -1) {
                    fos.write(buf, 0, i);
                }
            } catch (IOException _ioe) {
                _ioe.printStackTrace();
                return false;
            }
        } catch (FileNotFoundException _fnf) {
            _fnf.printStackTrace();
            return false;
        } finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (fos != null) {
	                fos.close();
				}
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
		}
        return true;
    }

    /**
     * appendString
     * CR092005410
     * @param _file
     * @param _txt
     * @param _rec
     * @author tony
     */
    public void insertString(String _file, String _txt,int _rec) {
		String[] tmp = readStringArray(_file);
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter out = null;
		if (tmp != null) {
			int ii = tmp.length;
			int recLoc = _rec;
			if (_rec < 0) {
				recLoc = ii +_rec;
			}
			try {
				fos = new FileOutputStream(_file);
				osw = new OutputStreamWriter(fos, encoding);
				out = new BufferedWriter(osw);
				for (int i=0;i<ii;++i) {
					if (i > 0) {
						out.newLine();
					}
					if (i == recLoc) {
						out.write(_txt);
						out.newLine();
					}
					out.write(tmp[i]);
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.close();
					}
					if (osw != null) {
						osw.close();
					}
					if (fos != null) {
						fos.close();
					}
				} catch (IOException _ioe) {
					_ioe.printStackTrace();
				}
			}
		}
        return;
    }
}
