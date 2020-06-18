//
// Copyright (c) 2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: LocalRuleItem.java,v $
// Revision 1.9  2005/03/04 18:26:23  dave
// Jtest actions for the day
//
// Revision 1.8  2005/02/24 19:05:00  gregg
// update
//
// Revision 1.7  2005/02/23 20:10:04  gregg
// full vs. partial - fix
//
// Revision 1.6  2005/02/22 19:46:48  gregg
// fix message string
//
// Revision 1.5  2005/02/22 19:36:01  gregg
// null fix
//
// Revision 1.4  2005/02/22 19:24:27  gregg
// fix
//
// Revision 1.3  2005/02/22 19:18:32  gregg
// messageString on Local Rule
//
// Revision 1.2  2005/02/18 22:09:56  gregg
// fixes
//
// Revision 1.1  2005/02/18 22:03:20  gregg
// initial load
//
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import java.util.StringTokenizer;

/**
 * LocalRuleItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LocalRuleItem extends EANMetaFoundation {

    final static long serialVersionUID = 1L;

    private String m_strEval = null;
    private String m_strMessage = null;

    /**
     * Do all requirements need to be met? Screwy logic, I know: If this is true (full == true), then
     * all attributes in the eval string need to be in existance to perform the check. Else it PASSES by default.
     * If false, we run attributes through evaluator regardless of their presence (i.e. let EvaluatorII figure it out).
     */
    private boolean m_bFull = true;

    /**
     * LocalRuleItem
     *
     * @param _lrg
     * @param _prof
     * @param _strItemKey
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    protected LocalRuleItem(LocalRuleGroup _lrg, Profile _prof, String _strItemKey) throws MiddlewareRequestException {
        super(_lrg, _prof, _strItemKey);
    }

    private LocalRuleItem(LocalRuleItem _lri) throws MiddlewareRequestException {
        this((LocalRuleGroup) _lri.getParent(), _lri.getProfile(), _lri.getKey());
        m_strEval = _lri.m_strEval;
        m_strMessage = _lri.m_strMessage;
        m_bFull = _lri.m_bFull;
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _b) {
        return getKey() + ":";
    }

    /**
     * getCopy
     *
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @return
     *  @author David Bigelow
     */
    public final LocalRuleItem getCopy() throws MiddlewareRequestException {
        return new LocalRuleItem(this);
    }

    /**
     * evaluate
     *
     * @param _ei
     * @return
     *  @author David Bigelow
     */
    public final boolean evaluate(EntityItem _ei) {
        System.out.println("LocalRule: evaluating " + dump(false));
        if (!isFull()) {
            return EvaluatorII.test(_ei, getEvaluationString());
        } else if (allAttributesExist(_ei, getEvaluationString())) { // only evaluate if ALL attributes are there.
            return EvaluatorII.test(_ei, getEvaluationString());
        }
        return true; // i.e. we need all attributes to evaluate and not all are here, so let it slide...
    }

    private final boolean allAttributesExist(EntityItem _ei, String _strEval) {
        StringTokenizer st = new StringTokenizer(_strEval, " ");
        while (st.hasMoreTokens()) {
            String s1 = st.nextToken();
            if (s1.charAt(0) == ':') {
                String strAttCode = s1.substring(1, s1.length());
                EANAttribute att = _ei.getAttribute(strAttCode);
                // Multi Flags might blow this up here!!!
                if (att == null || att.toString() == null || att.toString().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * getEvaluationString
     *
     * @return
     *  @author David Bigelow
     */
    public final String getEvaluationString() {
        return m_strEval;
    }

    /**
     * getExceptionMessage
     *
     * @return
     *  @author David Bigelow
     */
    public final String getExceptionMessage() {
        return m_strMessage;
    }

    /**
     * Are all attributes required for this to pass?
     *
     * @return boolean
     */
    public final boolean isFull() {
        return m_bFull;
    }

    /**
     * setEvaluationString
     *
     * @param _s
     *  @author David Bigelow
     */
    protected final void setEvaluationString(String _s) {
        m_strEval = _s;
    }

    /**
     * setFull
     *
     * @param _b
     *  @author David Bigelow
     */
    protected final void setFull(boolean _b) {
        m_bFull = _b;
    }

    /**
     * setMessageString
     *
     * @param _s
     *  @author David Bigelow
     */
    protected final void setMessageString(String _s) {
        m_strMessage = translateMessageString(_s);
    }

    private final String translateMessageString(String _strPre) {
        StringBuffer sbReturn = new StringBuffer();
        StringTokenizer st = new StringTokenizer(_strPre);
        boolean bInLiteral = false;
        while (st.hasMoreTokens()) {
            String s1 = st.nextToken();
            if (!bInLiteral && s1.charAt(0) == ':') {
                sbReturn.append(getAttributeDesc(s1.substring(1, s1.length())) + " ");
            } else if (s1.charAt(0) == '<') {
                bInLiteral = true;
                sbReturn.append(s1.substring(1, s1.length()) + " ");
            } else if (bInLiteral) {
                if (s1.charAt(s1.length() - 1) == '>') {
                    sbReturn.append(s1.substring(0, s1.length() - 1) + " ");
                    bInLiteral = false;
                } else {
                    sbReturn.append(s1 + " ");
                }
            }
        }
        return sbReturn.toString();
    }

    private final String getAttributeDesc(String _strAttCode) {
        EntityGroup eg = ((LocalRuleGroup) getParent()).getEntityGroup();
        EANMetaAttribute att = eg.getMetaAttribute(_strAttCode);
        String strReturn = _strAttCode;
        if (att == null) {
            System.err.println("LocalRuleItem: Error finding Attribute for " + _strAttCode + " in " + eg.getEntityType());
        } else {
            strReturn = att.getLongDescription();
        }
        return strReturn;
    }

}
