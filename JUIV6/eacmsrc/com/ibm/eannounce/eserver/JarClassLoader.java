//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eannounce.eserver;


/**
 * it is part of eaServer.jar and executes on the server
 * it loads Version.class from the version.jar packaged in the Updates.zip file
 */
// $Log: JarClassLoader.java,v $
// Revision 1.2  2013/07/18 18:40:26  wendy
// fix compiler warnings
//
// Revision 1.1  2012/09/27 19:39:24  wendy
// Initial code
//
public class JarClassLoader extends ClassLoader {
	public static boolean  debugOn = false;
	private JarResources jarResources = null;

	/**
	 * instantiate a classloader for this jar file
	 * @param jarName
	 */
	public JarClassLoader(String jarName){
		jarResources = new JarResources(jarName);
	}

	/**
	 * release memory
	 */
	public void dereference(){
		jarResources.dereference();
		jarResources = null;
	}

	/* (non-Javadoc)
	 * the class resolved before it is returned
	 * @see java.lang.ClassLoader#loadClass(java.lang.String)
	 */
	public Class<?> loadClass(String className) throws ClassNotFoundException {
		return (loadClass(className, true));
	}

	/* (non-Javadoc)
	 * @see java.lang.ClassLoader#loadClass(java.lang.String, boolean)
	 */
	public synchronized Class<?> loadClass(String className,
			boolean resolveIt) throws ClassNotFoundException {

		Class<?>   result;
		byte[]  classBytes;
		log(".loadClass(" + className + ", " + resolveIt + ")");
		
		//Try to load it from preferred source
		classBytes = loadClassBytes(className);
		if (classBytes != null) {
			//Define it (parse the class file)
			result = defineClass(className, classBytes, 0, classBytes.length);
			if (result == null) {
				throw new ClassFormatError();
			}

			// Resolve if necessary
			if (resolveIt) {
				resolveClass(result);
			}

			log(" Returning newly loaded class.");
		}else{
			//----- Check system class loader
			result = super.findSystemClass(className);
			log(" returning system class (in CLASSPATH).");
			//gets called to load classes used by the first class
			//looking for lang.NoClassDefFoundError: java.lang.Object
		}
		return result;
	}
	/**
	 * @param className
	 * @return
	 */
	private byte[] loadClassBytes (String className){
		// '/' is used to map the package to the path
		className = className.replace('.', '/') + ".class";

		//Attempt to get the class data from the JarResource.
		return (jarResources.getResource (className));
	}

	private void log(String text) {
		if (debugOn) {
			System.out.println(">> JarClassLoader"+text);
		}
	}
} 
