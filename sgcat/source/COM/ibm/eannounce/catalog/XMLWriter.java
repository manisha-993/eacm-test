// still need, write byte array
// UTF capability
// choose the replaceString method
// handle the writer
// integrate into Catalog class
// *** do i need close entity for force all member vars to consist value?
// *** case #4 not working

package COM.ibm.eannounce.catalog;

// XML Wrapper over PrintWriter

import java.io.IOException;
import java.io.Writer;
import java.util.Stack;

public class XMLWriter {

	private Writer m_writer = null;
	private Stack m_stack = null;
	private StringBuffer m_strbAttrs = null;
	private boolean m_bClosed = true;
	private boolean m_bEmpty = true;
	private boolean m_bImmediatePush = false;
	private boolean m_bImmediatePop = false;

	public XMLWriter() {
		//public XMLWriter(Writer writer) {
		//m_writer = new Writer();
		m_stack = new Stack();
	}

	static public void main(String[] args) throws Exception {
		XMLWriter xml = new XMLWriter();
		//System.out.println(replaceString("Roger Mc Carty", "Mc ", "Mc"));
		//System.out.println(replaceSpecial("<RO&ER>"));

		System.out.println("Case #1");
		xml.writeEntity("product");
		xml.writeAttribute("id", "12345678");
		xml.writeAttribute("country", "US");
		xml.writeEntity("mtm");
		xml.write("2647-U21");
		xml.endEntity();
		xml.writeEntity("cdrom");
		xml.write("sony");
		xml.endEntity();
		xml.endEntity();
		xml.finishEntity();

		System.out.println("\nCase #2");
		xml.writeEntity("product");
		xml.writeAttribute("id", "12345678");
		xml.writeAttribute("country", "US");
		xml.writeEntity("mtm");
		xml.write("2647-U21");
		xml.endEntity();
		xml.endEntity();
		xml.finishEntity();

		System.out.println("\nCase #3");
		xml.writeEntity("product");
		xml.writeAttribute("id", "12345678");
		xml.writeAttribute("country", "US");
		xml.endEntity();
		xml.finishEntity();

		System.out.println("\nCase #4");
		xml.writeEntity("product");
		xml.writeAttribute("id", "12345678");
		xml.writeAttribute("country", "US");
		xml.endEntity();
		xml.finishEntity();

		System.out.println("\nCase #5");
		xml.writeEntity("product");
		xml.writeAttribute("id", "12345678");
		xml.writeAttribute("country", "US");
		xml.endEntity();
		xml.finishEntity();

		System.out.println("\nCase #6");
		xml.writeEntity("product");
		byte[] baBuffer = { 'A', 'B', 'C' };
		xml.write(baBuffer);
		xml.endEntity();
		xml.finishEntity();
	}

	public static String replaceSpecial(String _strString) {
		_strString = replaceString(_strString, "&", "&amp;");
		_strString = replaceString(_strString, "\"", "&quot;");
		_strString = replaceString(_strString, "'", "&apos;");
		_strString = replaceString(_strString, "<", "&lt;");
		_strString = replaceString(_strString, ">", "&gt;");
		return _strString;
	}

	public static String xreplaceString(
		String _strString,
		String _strSearch,
		String _strReplacement) {
		String strResult = _strString;
		int iFromIndex = 0;
		int iSearchLength = _strSearch.length();

		while (iFromIndex < strResult.length() && iSearchLength > 0) {
			int iIndex = strResult.indexOf(_strSearch, iFromIndex);
			if (iIndex >= 0) {
				strResult =
					strResult.substring(0, iIndex)
						+ _strReplacement
						+ strResult.substring(
							iIndex + _strSearch.length(),
							strResult.length());
				iFromIndex = iIndex;
			} else {
				iFromIndex = strResult.length();
			}
		}
		return strResult;
	}

	static public String replaceString(String text, String repl, String with) {
		return replaceString(text, repl, with, -1);
	}

	public static String replaceString(
		String text,
		String repl,
		String with,
		int max) {
		if (text == null) {
			return null;
		}
		StringBuffer buffer = new StringBuffer(text.length());
		int start = 0;
		int end = 0;
		while ((end = text.indexOf(repl, start)) != -1) {
			buffer.append(text.substring(start, end)).append(with);
			start = end + repl.length();
			if (--max == 0) {
				break;
			}
		}
		buffer.append(text.substring(start));
		return buffer.toString();
	}

	public void write(byte[] _baBuffer) throws Exception {
		if (1 == 1) {
			throw new Exception("Writing byte array not yet supported");
		}
		for (int i = 0; i < _baBuffer.length; i++) {
			System.out.print(_baBuffer[i]);
		}
	}

	public void write(String _strText) throws Exception {
		try {
			closeOpeningTag();
			m_bEmpty = false;
			m_bImmediatePush = false;
			m_bImmediatePop = false;
			System.out.print(replaceSpecial(_strText));
		} catch (IOException x) {
			x.printStackTrace();
		}
	}

	public void close() throws Exception {
		if (!m_stack.empty()) {
			throw new Exception(
				"XML NOT well-formed, tag '"
					+ m_stack.pop()
					+ "' is not closed.");
		}
	}

	public void endEntity() throws Exception {
		try {
			if (m_stack.empty()) {
				throw new Exception("No unclosed entity to end!");
			}
			m_bImmediatePush = false;
			String strName = (String) m_stack.pop();
			if (strName != null) {
				if (m_bEmpty) {
					writeAttributes();
					System.out.print("/>\n");
				} else {
					if (m_bImmediatePop) {
						for (int x = 0; x < m_stack.size(); x++) {
							System.out.print("\t");
						}
					}
					System.out.print("</");
					System.out.print(strName);
					System.out.print(">\n");
				}
				m_bEmpty = false;
			}
			m_bImmediatePop = true;
		} catch (IOException x) {
			//throw new Exception(x);
			x.printStackTrace();
		}
	}

	public void writeAttribute(String _strAttr, String _strValue)
		throws Exception {
		if (m_strbAttrs == null) {
			m_strbAttrs = new StringBuffer();
		}
		m_strbAttrs.append(" ");
		m_strbAttrs.append(_strAttr);
		m_strbAttrs.append("=\"");
		m_strbAttrs.append(replaceSpecial(_strValue));
		m_strbAttrs.append("\"");
	}

	private void writeAttributes() throws IOException {
		if (m_strbAttrs != null) {
			System.out.print(m_strbAttrs.toString());
			m_strbAttrs.setLength(0);
			m_bEmpty = false;
		}
	}

	private void closeOpeningTag() throws IOException {
		if (!m_bClosed) {
			writeAttributes();
			m_bClosed = true;
			System.out.print(">");
		}
	}

	public void writeAttributeAsEntity(String _strEntity, String _strValue) throws Exception {
		this.writeEntity(_strEntity);
		this.write(_strValue);
		this.endEntity();
	}

	public void writeEntity(String _strName) throws Exception {

		try {
			closeOpeningTag();
			m_bClosed = false;
			// DWB .. just for human readability..
			if (this.m_bImmediatePush) {
				System.out.print("\n");
			}
			for (int x = 0; x < m_stack.size(); x++) {
				System.out.print("\t");
			}
			m_stack.add(_strName);
			System.out.print("<");
			System.out.print(_strName);
			m_bEmpty = true;				
			m_bImmediatePush = true;
			m_bImmediatePop = false;
		} catch (IOException x) {
			//throw new Exception(x);
			x.printStackTrace();
		}
	}

	public void finishEntity() {
		m_bImmediatePush = false;
		m_bImmediatePop = false;
		m_bClosed = true;
		m_bEmpty = true;
	}

	public void resetEntity() {
		m_stack = new Stack();
		finishEntity();
	}
}

/*
 * $XLog$
 */

//append + autoflush in UTF-8
//m_pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(m_strFileName, true), "UTF-8"), true);
