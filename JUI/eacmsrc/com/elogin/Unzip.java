/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: Unzip.java,v $
 * Revision 1.1  2007/04/18 19:42:18  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2005/09/08 17:58:58  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.7  2005/02/04 18:16:48  tony
 * JTest Formatter Fourth Pass
 *
 * Revision 1.6  2005/02/04 16:57:41  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.5  2005/02/03 19:42:21  tony
 * JTest Third pass
 *
 * Revision 1.4  2005/02/02 17:30:22  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/27 23:18:16  tony
 * JTest Formatting
 *
 * Revision 1.2  2004/02/19 21:34:52  tony
 * e-announce1.3
 *
 * Revision 1.1.1.1  2004/02/10 16:59:27  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2002/11/07 16:58:10  tony
 * added/adjusted copyright statement
 *
 */
package com.elogin;
import java.io.*;
import java.util.zip.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class Unzip {
    /**
     * Automatically generated constructor for utility class
     */
    private Unzip() {
    }

    /**
     * the main class to run the unzip utility stand alone
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String args[]) throws IOException {
        unZipper(args);
    }

    /**
     * unzip the entire archive
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void unZipper(String[] args) throws IOException {
        InputStream in = new BufferedInputStream(new FileInputStream(args[0]));
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry e;
		try {
	        while ((e = zin.getNextEntry()) != null) {
	            UnZip(zin, args[1] + e.getName());
	        }
		} catch (IOException _ioe) {
			throw _ioe;
		} finally {
	        try {
		        if (zin != null) {
			        zin.close();
				}
				if (in != null) {
			        in.close(); //s80
				}
			} catch (IOException _ioe) {
				throw _ioe;
			}
		}
        return;
    }

    /**
     * unzip
     *
     * @param _dir
     * @param _file
     * @throws java.io.IOException
     * @author Anthony C. Liberto
     * @param _delete
     */
    public static void unzipFile(String _dir, String _file, boolean _delete) throws IOException {
        InputStream in = new BufferedInputStream(new FileInputStream(_dir + _file));
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry e = null;
        File f = null;
        try {
	        while ((e = zin.getNextEntry()) != null) {
	            UnZip(zin, _dir + e.getName());
	        }
		} catch (IOException _ioe) {
			throw _ioe;
		} finally {
	        try {
		        if (zin != null) {
			        zin.close();
				}
				if (in != null) {
			        in.close(); //s80
				}
			} catch (IOException _ioe) {
				throw _ioe;
			} finally {
	        	if (_delete) {
	        	    f = new File(_dir + _file);
	        	    f.delete();
	        	}
			}
		}
        return;
    }

    /**
     * unzip
     *
     * @param _s
     * @throws java.io.IOException
     * @author Anthony C. Liberto
     * @param _delete
     */
    public static void unzipFile(String _s, boolean _delete) throws IOException {
        InputStream in = new BufferedInputStream(new FileInputStream(_s));
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry e = null;
        File f = null;
        try {
	        while ((e = zin.getNextEntry()) != null) {
	            UnZip(zin, e.getName());
	        }
		} catch (IOException _ioe) {
			throw _ioe;
		} finally {
	        try {
		        if (zin != null) {
			        zin.close();
				}
				if (in != null) {
			        in.close(); //s80
				}
			} catch (IOException _ioe) {
				throw _ioe;
			} finally {
				if (_delete) {
					f = new File(_s);
					f.delete();
				}
			}
		}
        return;
    }

    /**
     * unZipperDelete
     * @param args
     * @throws java.io.IOException
     * @author Anthony C. Liberto
     */
    public static void unZipperDelete(String[] args) throws IOException {
        InputStream in = new BufferedInputStream(new FileInputStream(args[0]));
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry e;
		try {
	        while ((e = zin.getNextEntry()) != null) {
	            UnZipDelete(zin, args[1] + e.getName());
			}
		} catch (IOException _ioe) {
			throw _ioe;
		} finally {
	        try {
	        	if (zin != null) {
		        	zin.close();
				}
				if (in != null) {
		        	in.close(); //s80
				}
			} catch (IOException _ioe) {
				throw _ioe;
			}
		}
        return;
    }

    /**
     * UnZipDelete
     * @param zin
     * @param s
     * @throws java.io.IOException
     * @author Anthony C. Liberto
     */
    public static void UnZipDelete(ZipInputStream zin, String s) throws IOException {
        FileOutputStream out = new FileOutputStream(s);
        File f = new File(s);
        byte[] b = new byte[512];
        int len = 0;
        try {
	        while ((len = zin.read(b)) != -1) {
	            out.write(b, 0, len);
	        }
		} catch (IOException _ioe) {
			throw _ioe;
		} finally {
	        try {
		        if (out != null) {
			        out.close();
				}
				if (f != null) {
			        f.deleteOnExit();
				}
			} catch (IOException _ioe) {
				throw _ioe;
			}
		}

    }

    /**
     * From the passed in file name decompress the object and
     * return it to the calling procedure.
     *
     * @return the decompressed Object.
     * @param file
     */
    public static Object decompressObject(String file) {
		FileInputStream fin = null;
		ObjectInputStream objectIn = null;
		Object o = null;
        try {
            fin = new FileInputStream(file);
            objectIn = new ObjectInputStream(new InflaterInputStream(fin));
            o = objectIn.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
			try {
				if (objectIn != null) {
					objectIn.close();
				}
				if (fin != null) {
					fin.close(); //s80
				}
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
		}
        return o;
    }

    /**
     * unzip the specified file from the archive
     *
     * @param s
     * @param zin
     * @throws java.io.IOException
     */
    public static void UnZip(ZipInputStream zin, String s) throws IOException {
        FileOutputStream out = new FileOutputStream(s);
        byte[] b = new byte[512];
        int len = 0;
        try {
        	while ((len = zin.read(b)) != -1) {
        	    out.write(b, 0, len);
        	}
		} catch (IOException _ioe) {
			throw _ioe;
		} finally {
	        try {
	        	if (out != null) {
		        	out.close();
				}
			} catch (IOException _ioe) {
				throw _ioe;
			}
		}
        return;
    }

    /**
     * unzipObject
     * @param byteArray
     * @return
     * @author Anthony C. Liberto
     */
    public static Object unzipObject(byte[] byteArray) { //compress
        Object obj = null; //compress
		ByteArrayInputStream bais = null;
		GZIPInputStream zis = null;
		BufferedInputStream bis = null;
        ObjectInputStream ois = null; //compress
        try {
            bais = new ByteArrayInputStream(byteArray); //compress
            zis = new GZIPInputStream(bais);
            bis = new BufferedInputStream(zis);
            ois = new ObjectInputStream(bis); //compress
            obj = ois.readObject(); //compress
        } catch (Exception exc) { //compress
            exc.printStackTrace();
        } finally {
			try {
				if (ois != null) {
		            ois.close(); //compress
				}
				if (bis != null) {
		            bis.close(); //s80
				}
				if (zis != null) {
		            zis.close(); //s80
				}
				if (bais != null) {
		            bais.close(); //s80
				}
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
		}
        return obj; //compress
    } //compress
}
