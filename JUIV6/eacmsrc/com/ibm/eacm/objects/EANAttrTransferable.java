//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.objects;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Vector;


import COM.ibm.eannounce.objects.EANAttribute;

/**
 * @author Wendy Stimpson
 *
 */
// $Log: EANAttrTransferable.java,v $
// Revision 1.1  2012/09/27 19:39:13  wendy
// Initial code
//
public class EANAttrTransferable implements Transferable, EACMGlobals {
	private PasteData[] eanobj = null;
	private static final DataFlavor[] flavors;
	public static final DataFlavor EANDataFlavor = new DataFlavor(PasteData[].class,"Attribute value");
	static{
		flavors = new DataFlavor[2];
		flavors[0] = EANDataFlavor;
		flavors[1] = DataFlavor.stringFlavor;
	}
	public EANAttrTransferable(EANAttribute ean){
		eanobj = new PasteData[1];
		eanobj[0] = new PasteData(ean);
	}

	public EANAttrTransferable(Vector<EANAttribute> eanVct){
		eanobj = new PasteData[eanVct.size()];
		for(int i=0; i<eanVct.size(); i++){	
			eanobj[i] = new PasteData(eanVct.elementAt(i));
		}
	}

	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if(!isDataFlavorSupported(flavor)){
			throw new UnsupportedFlavorException(flavor);
		}
		if(DataFlavor.stringFlavor.equals(flavor)){
			StringBuffer sb = new StringBuffer();
			for (int i=0; i<eanobj.length; i++){
				if (i > 0) {
    				sb.append(RETURN); // delimit each attribute with a carriage return
    			}
				sb.append(eanobj[i].toString());
			}
			return sb.toString();
		}
		
		return eanobj;
	}

	public DataFlavor[] getTransferDataFlavors() {
		return flavors;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return (flavors[0].equals(flavor)||flavors[1].equals(flavor));
	}

}
