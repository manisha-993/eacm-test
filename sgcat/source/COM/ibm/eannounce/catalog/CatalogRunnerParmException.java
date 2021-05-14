//
// $Log: CatalogRunnerParmException.java,v $
// Revision 1.7  2011/05/05 11:21:31  wendy
// src from IBMCHINA
//
// Revision 1.1.1.1  2007/06/05 02:09:05  jingb
// no message
//
// Revision 1.6  2006/07/20 22:22:54  gregg
// compile
//
// Revision 1.5  2006/07/20 22:21:08  gregg
// one more msg thang
//
// Revision 1.4  2006/07/20 22:19:57  gregg
// one more little geMessage piece
//
// Revision 1.3  2006/07/20 22:19:01  gregg
// messaging
//
// Revision 1.2  2006/07/20 21:53:45  gregg
// constructor
//
// Revision 1.1  2006/07/20 21:34:43  gregg
// initial load
//
//

package COM.ibm.eannounce.catalog;

public class CatalogRunnerParmException extends Exception {

    private String m_strMessage = null;

    public CatalogRunnerParmException(String _s) {
        m_strMessage = _s;
	}

	public final String getMessage() {
		return m_strMessage;
	}

}
