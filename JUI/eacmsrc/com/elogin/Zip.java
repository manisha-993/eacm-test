/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: Zip.java,v $
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
 * Revision 1.7  2005/02/08 16:33:58  tony
 * JTest Formatting Fourth pass
 *
 * Revision 1.6  2005/02/04 18:16:48  tony
 * JTest Formatter Fourth Pass
 *
 * Revision 1.5  2005/02/04 16:57:42  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.4  2005/02/03 19:42:21  tony
 * JTest Third pass
 *
 * Revision 1.3  2005/02/02 17:30:21  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:17  tony
 * JTest Formatting
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
public class Zip {

    /**
     * Automatically generated constructor for utility class
     */
    private Zip() {
    }

    /**
     * stand alone zip procedure
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String args[]) throws IOException {
        zipper(args);
        return;
    }

    /**
     * zip the specified files into the archive
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void zipper(String[] args) throws IOException {
        byte b[] = new byte[512];
        FileOutputStream out = null;
        ZipOutputStream zout = null;
        int ii = args.length;
        if (ii < 2) {
            System.exit(0);
        }
        try {
	        out = new FileOutputStream(args[0].replace(File.separatorChar, '/'));
	        zout = new ZipOutputStream(out);
	        for (int i = 1; i < ii; i++) {
	            zipFile(args[i], zout, b);
	        }
		} catch (IOException _ioe) {
			throw _ioe;
		} finally {
			try {
				if (zout != null) {
					zout.close();
				}
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
     * From the passed in object and file name compress the object and
     * create the file of the specified name from the compressed Object.
     *
     * @param file
     * @param o
     */
    public static void compressObject(Object o, String file) {
		FileOutputStream fout = null;
		ObjectOutputStream objectOut = null;
        try {
            fout = new FileOutputStream(file);
            objectOut = new ObjectOutputStream(new DeflaterOutputStream(fout));
            objectOut.writeObject(o);
            objectOut.reset();			//JTest
            objectOut.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
			try {
				if (objectOut != null) {
					objectOut.close();
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
     * zipObject
     * @param in
     * @return
     * @author Anthony C. Liberto
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
            try {
            	if (oos != null) {
	            	oos.close(); //compress
				}
				if (bos != null) {
	            	bos.close(); //s80
				}
				if (zos != null) {
	            	zos.close(); //s80
				}
				if (baos != null) {
	            	baos.close(); //s80
				}
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
		}
        return byteArray; //compress
    } //compress

    /**
     * zip to a new archive based on the file location specified
     *
     * @param infile
     * @param loc
     * @throws java.io.IOException
     */
    public static void wild(String loc, String infile) throws IOException {
        byte b[] = new byte[512];
        FileOutputStream out = null;
        File f = null;
        ZipOutputStream zout = null;
        String[] file = null;
        int ii = -1;
        if (loc == null || infile == null) {
            return;
        }
        try {
	        out = new FileOutputStream(loc + infile);
	        f = new File(loc);
	        zout = new ZipOutputStream(out);
	        file = f.list();
	        ii = file.length;
	        for (int i = 0; i < ii; ++i) {
	            if (!file[i].endsWith(".zip")) {
	                zipFile(loc, file[i], zout, b);
	            }
	        }
		} catch (IOException _ioe) {
			throw _ioe;
		} finally {
	        try {
		        if (zout != null) {
			        zout.close();
				}
				if (out != null) {
			        out.close(); //s80
				}
			} catch (IOException _ioe) {
				throw _ioe;
			}
		}
        return;
    }

    /**
     * zip a single file to the archive based on location and filename
     *
     * @param b
     * @param file
     * @param loc
     * @param zout
     * @throws java.io.IOException
     */
    public static void zipFile(String loc, String file, ZipOutputStream zout, byte[] b) throws IOException {
        InputStream in = new FileInputStream(loc + file);
        ZipEntry e = new ZipEntry(file);
        int len = 0;
        try {
	        zout.putNextEntry(e);
	        while ((len = in.read(b)) != -1) {
	            zout.write(b, 0, len);
	        }
		} catch (IOException _ioe) {
			throw _ioe;
		} finally {
	        try {
		        if (in != null) {
			        in.close(); //9680
				}
				if (zout != null) {
			        zout.closeEntry();
				}
		        print(e);
			} catch (IOException _ioe) {
				throw _ioe;
			}
		}
        return;
    }

    /**
     * zip a single file to the archive
     *
     * @param b
     * @param file
     * @param zout
     * @throws java.io.IOException
     */
    public static void zipFile(String file, ZipOutputStream zout, byte[] b) throws IOException {
        InputStream in = new FileInputStream(file);
        ZipEntry e = new ZipEntry(file);
        int len = 0;
        try {
	        zout.putNextEntry(e);
	        while ((len = in.read(b)) != -1) {
	            zout.write(b, 0, len);
	        }
		} catch (IOException _ioe) {
			throw _ioe;
		} finally {
		    try {
			    if (in != null) {
				    in.close();
				}
				if (zout != null) {
			        zout.closeEntry();
				}
		        print(e);
			} catch (IOException _ioe) {
				throw _ioe;
			}
		}
        return;
    }

    /**
     * zip
     * @param _dir
     * @param _file
     * @param _filter
     * @param delete
     * @throws java.io.IOException
     * @author Anthony C. Liberto
     */
    public static void zipFile(String _dir, String _file, String _filter, boolean delete) throws IOException {
        byte b[] = new byte[512];
        ExtensionFilter filter = new ExtensionFilter(_filter);
        File f = new File(_dir);
        File[] file = f.listFiles(filter);
        FileOutputStream out = null;
        ZipOutputStream zout = null;
        int ii = file.length;
        if (ii <= 0) {
            return;
        }
        try {
	        out = new FileOutputStream(_dir + _file);
	        zout = new ZipOutputStream(out);
	        for (int i = 0; i < ii; ++i) {
	            zipFile(_dir, file[i].getName(), zout, b);
	            if (delete) {
	                file[i].delete();
	            }

	        }
		} catch (IOException _ioe) {
			throw _ioe;
		} finally {
			try {
		        if (zout != null) {
			        zout.close();
				}
				if (out != null) {
			        out.close(); //s80
				}
			} catch (IOException _ioe) {
				throw _ioe;
			}
		}
        return;
    }

    /**
     * parseDir
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static String parseDir(String _s) {
        int indx = _s.lastIndexOf(File.separator) + 1;
        return _s.substring(0, indx);
    }

    /**
     * parseFile
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static String parseFile(String _s) {
        int indx = _s.lastIndexOf(File.separator) + 1;
        return _s.substring(indx);
    }

    /**
     * print out a synopsis of the zip archive
     *
     * @param e
     */
    public static void print(ZipEntry e) {
        PrintStream err = System.err;
        err.print("added " + e.getName());
        if (e.getMethod() == ZipEntry.DEFLATED) {
            long size = e.getSize();
            if (size > 0) {
                long csize = e.getCompressedSize();
                long ratio = ((size - csize) * 100) / size;
                err.println(" (deflated " + ratio + "%)");
            } else {
                err.println(" (deflated 0%)");
            }
        } else {
            err.println(" (stored 0%)");
        }
        return;
    }
}
