//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eannounce.eserver;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


/**
 * it is part of eaServer.jar and executes on the server
 * 
 * JarResources maps all resources included in a
 * Zip or Jar file and provides a method to extract one
 * as a blob.
 */
// $Log: JarResources.java,v $
// Revision 1.2  2013/07/18 18:40:26  wendy
// fix compiler warnings
//
// Revision 1.1  2012/09/27 19:39:24  wendy
// Initial code
//
public final class JarResources
{
	public static boolean debugOn=false;

	private Hashtable<String, byte[]> jarContentsTbl=new Hashtable<String, byte[]>();

	/**
	 * creates a JarResources. It extracts all resources from a Jar
	 * into an internal hashtable, keyed by resource names.
	 * @param jarFileName a jar or zip file
	 */
	public JarResources(String jarFileName)	{
		init(jarFileName);
	}

	/**
	 * pull all entries out of jar file
	 * @param jarFileName
	 */
	private void init(String jarFileName)	{
		Hashtable<String, Integer> zeSizes=new Hashtable<String, Integer>();  
		FileInputStream fin=null;
		BufferedInputStream bis=null;
		ZipInputStream zis=null;
		ZipFile zip= null;
		try {
			// extracts just sizes only, they are not available from the ZipInputStream
			zip=new ZipFile(jarFileName);
			Enumeration<?> e=zip.entries();
			while (e.hasMoreElements()) {
				ZipEntry ze=(ZipEntry)e.nextElement();

				if (debugOn){
					System.out.println(dump(ze));
				}

				zeSizes.put(ze.getName(),new Integer((int)ze.getSize()));
			}

			// extract resources and put them into the hashtable.
			fin=new FileInputStream(jarFileName);
			bis=new BufferedInputStream(fin);
			zis=new ZipInputStream(bis);
			ZipEntry ze=null;
			while ((ze=zis.getNextEntry())!=null){
				if (ze.isDirectory()){
					continue;
				}

				int size=(int)ze.getSize();
				// -1 means unknown size, get it from hashtable built from zipfile
				if (size==-1){
					size=((Integer)zeSizes.get(ze.getName())).intValue();
				}

				byte[] b=new byte[(int)size];
				int rb=0;
				int chunk=0;
				while (((int)size - rb) > 0){
					chunk=zis.read(b,rb,(int)size - rb);
					if (chunk==-1)	{
						break;
					}
					rb+=chunk;
				}

				// add to internal resource hashtable
				jarContentsTbl.put(ze.getName(),b);

				if (debugOn){
					System.out.println("JarResources: loaded "+ ze.getName()+" rb="+rb+
							",size="+size+
							",csize="+ze.getCompressedSize() );
				}
			}
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e)	{
			e.printStackTrace();
		} finally {
            try {
                if (zis != null) {
                	zis.close();
                }
                if (bis != null) {
                	bis.close();
                }
                if (fin != null) {
                    fin.close();
                }
                if (zip != null) {
                	zip.close();
                }
            } catch (IOException _ioe) {
                _ioe.printStackTrace();
            }
        }
		
		zeSizes.clear();
	}
	/**
	 * release memory
	 */
	public void dereference(){
		jarContentsTbl.clear();
		jarContentsTbl = null;
	}

	/**
	 * Extracts a jar resource as a blob.
	 * @param name a resource name.
	 */
	public byte[] getResource(String name) {
		return (byte[])jarContentsTbl.get(name);
	}
	/**
	 * Dumps a zip entry into a string.
	 * @param ze a ZipEntry
	 */
	private String dump(ZipEntry ze){
		StringBuffer sb=new StringBuffer("ZipEntry: ");
		if (ze.isDirectory()){
			sb.append("d ");
		}
		else{
			sb.append("f ");
		}

		if (ze.getMethod()==ZipEntry.STORED){
			sb.append("stored   ");
		}
		else{
			sb.append("deflated ");
		}

		sb.append(ze.getName());
		sb.append("\t");
		sb.append(""+ze.getSize());
		if (ze.getMethod()==ZipEntry.DEFLATED){
			sb.append("/"+ze.getCompressedSize());
		}

		return sb.toString();
	}
}	