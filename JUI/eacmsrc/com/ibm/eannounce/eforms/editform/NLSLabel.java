/**
 * Copyright (c) 2001 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * @version eAnnounce 1.0  2001/09/21
 * @author Anthony C. Liberto
 *
 * $Log: NLSLabel.java,v $
 * Revision 1.2  2008/01/30 16:27:09  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:19  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:55  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:02  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/04 15:22:09  tony
 * JTest Format Third Pass
 *
 * Revision 1.2  2005/01/27 19:15:16  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2003/03/21 22:38:32  tony
 * form accessibilty update.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2002/02/13 17:24:00  tony
 * trace statement adjustment
 *
 * Revision 1.2  2001/12/27 16:41:28  tony
 * mass update
 *
 * Revision 1.1.1.1  2001/11/29 19:00:27  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1  2001/11/06 23:47:23  tony
 * initial form load
 *
 * Revision 1.2  2001/09/24 21:18:22  tony
 * eAnnounce1.0 -- UCD Updates
 *
 * Revision 1.1  2001/09/21 15:33:38  tony
 * eAnnounce1.0 -- added logic to allow for NLS panel
 * labels that will toggle and load according to NLS
 * values.
 *
 *
 */
package com.ibm.eannounce.eforms.editform;
import com.elogin.*;
import java.awt.*;
import java.util.HashMap;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class NLSLabel extends ELabel {
	private static final long serialVersionUID = 1L;
	private HashMap titleMap = new HashMap();
	private Integer defNLS = new Integer(1);
	private int dispNLS = 1;
	private Dimension d = new Dimension();

	/**
     * nlsLabel
     * @author Anthony C. Liberto
     */
    public NLSLabel() {
		super();
	}

	/**
     * nlsLabel
     * @param s
     * @author Anthony C. Liberto
     */
    public NLSLabel(String s) {
		super();
		setTitle(s,defNLS);
		return;
	}

	/**
     * setTitle
     * @param s
     * @param _nls
     * @author Anthony C. Liberto
     */
    public void setTitle(String s, int _nls) {
		setTitle(s, new Integer(_nls));
		return;
	}

	private void setTitle(String s, Integer _nls) {
		titleMap.put(_nls,s);
		return;
	}

	/**
     * @see java.awt.Component#getPreferredSize()
     * @author Anthony C. Liberto
     */
    public Dimension getPreferredSize() {
		FontMetrics fm = getFontMetrics(getFont());
		d.setSize(getStringWidth(fm), fm.getHeight());
		return d;
	}

	private int getStringWidth(FontMetrics _fm) {
		String txt = getText();
		if (txt != null) {
			return (_fm.stringWidth(txt) + 4);
		}
		return 10;
	}

	/**
     * getTitle
     * @param _nls
     * @return
     * @author Anthony C. Liberto
     */
    public String getTitle(int _nls) {
		Integer nls = new Integer(_nls);
		if (titleMap.containsKey(nls)) {
			return (String)titleMap.get(nls);
		} else if (titleMap.containsKey(defNLS)) {
			return (String)titleMap.get(defNLS);
		}
		return "";
	}

	/**
     * setNLS
     * @param _nls
     * @author Anthony C. Liberto
     */
    public void setNLS(int _nls) {
		dispNLS = _nls;
		setText(getTitle(_nls));
		return;
	}

	/**
     * getNLS
     * @return
     * @author Anthony C. Liberto
     */
    public int getNLS() {
		return dispNLS;
	}
}
