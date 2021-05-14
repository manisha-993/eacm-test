/**
 *
 * Copyright (c) 2003-2004 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * e-announce chat functionality
 *
 * @version 1.3  2004/02/10
 * @author Anthony C. Liberto
 *
 * $Log: EChatClientIO.java,v $
 * Revision 1.1  2007/04/18 19:46:48  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:54  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2005/09/08 17:59:12  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.6  2005/02/08 16:33:58  tony
 * JTest Formatting Fourth pass
 *
 * Revision 1.5  2005/02/03 21:26:15  tony
 * JTest Format Third Pass
 *
 * Revision 1.4  2005/02/03 19:42:22  tony
 * JTest Third pass
 *
 * Revision 1.3  2005/01/28 17:54:21  tony
 * JTest Fromat step 2
 *
 * Revision 1.2  2005/01/26 17:18:50  tony
 * JTest Formatting modifications
 *
 * Revision 1.1  2004/02/19 21:36:57  tony
 * e-announce1.3
 *
 *
 */
package com.ibm.eannounce.eserver;
import java.io.*;
import java.util.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EChatClientIO {
    static final long serialVersionUID = 1L;
    private static Comparator comp = null;

    private EChatClientIO() {
        return;
    }
    /**
     * toObject
     * @param _byte
     * @return
     * @author Anthony C. Liberto
     */
    public static Object toObject(byte[] _byte) {
        Object o = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;

        if (_byte != null) {
            try {
                bis = new ByteArrayInputStream(_byte);
                ois = new ObjectInputStream(bis);
                o = ois.readObject();
            } catch (ClassNotFoundException _cnf) {
                _cnf.printStackTrace();
            } catch (InvalidClassException _ice) {
                _ice.printStackTrace();
            } catch (StreamCorruptedException _sce) {
                _sce.printStackTrace();
            } catch (OptionalDataException _ode) {
                _ode.printStackTrace();
            } catch (IOException _ioe) {
                _ioe.printStackTrace();
            } finally {
				try {
					if (ois != null) {
						ois.close();
					}
					if (bis != null) {
						bis.close();
					}
				} catch (IOException _ioe) {
					_ioe.printStackTrace();
				}
			}
        }
        return o;
    }

    /**
     * toByte
     * @param _o
     * @return
     * @author Anthony C. Liberto
     */
    public static byte[] toByte(Object _o) {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        byte[] data = null;
        if (_o instanceof byte[]) {
            return (byte[]) _o;
        }
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(_o);
            oos.reset();			//JTest
            oos.flush();
            data = bos.toByteArray();
        } catch (IOException _ioe) {
            _ioe.printStackTrace();
        } finally {
			try {
				if (oos != null) {
					oos.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
		}
        return data;
    }

    /**
     * getComparator
     * @return
     * @author Anthony C. Liberto
     */
    public static Comparator getComparator() {
        if (comp == null) {
            comp = new EComparator();
        }
        return comp;
    }
}
