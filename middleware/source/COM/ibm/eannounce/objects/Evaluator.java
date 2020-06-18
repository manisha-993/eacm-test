/**
 * Copyright (c) 2001 International Buisiness Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * @version 2.4
 * @date 2001/08/16
 * @author Joan Tran
 *
 * $Log: Evaluator.java,v $
 * Revision 1.17  2008/02/01 22:10:06  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.16  2005/03/03 18:36:51  dave
 * more Jtest
 *
 * Revision 1.15  2002/12/20 21:02:04  dave
 * added import statement for Profile
 *
 * Revision 1.14  2002/12/20 20:44:00  dave
 * new  Role Code Evaluator Logic
 *
 * Revision 1.13  2002/10/14 22:29:00  dave
 * Cleaning up and closing out more system.out.println
 *
 * Revision 1.12  2002/07/30 01:05:05  dave
 * more cleanup
 *
 * Revision 1.11  2002/04/30 19:56:16  dave
 * force serial number to 10L
 *
 * Revision 1.10  2002/04/02 01:53:41  dave
 * more Syntax
 *
 * Revision 1.9  2002/04/02 01:41:07  dave
 * Syntax
 *
 * Revision 1.8  2002/04/02 01:12:09  dave
 * first stab at restriction
 *
 * Revision 1.7  2001/11/26 18:42:48  dave
 * carry forward for metaflag fix
 *
 * Revision 1.6  2001/11/16 00:53:25  dave
 * changes to allow for status in copyValues
 *
 * Revision 1.5  2001/08/21 22:38:22  dave
 * private class internal to the Evaluator was not marked as Serializable
 *
 * Revision 1.4  2001/08/21 22:17:02  dave
 * more trace for PDHGroup not being serializable
 *
 * Revision 1.3  2001/08/21 21:54:10  dave
 * added Serializable to the Evaluator
 *
 * Revision 1.2  2001/08/21 20:50:47  dave
 * Syntax
 *
 * Revision 1.1  2001/08/21 20:45:05  dave
 * added Evaluator class
 *
 *
 */

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import java.util.Stack;
import java.util.StringTokenizer;
import java.io.Serializable;

/**
 * Evaluator
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Evaluator implements Serializable, Cloneable {
    private class Precedence implements Serializable, Cloneable {
        /** Automatically generated javadoc for: serialVersionUID */
        private static final long serialVersionUID = 8938050881971984755L;
        /**
         * INPUTSYMBOL
         */
        public int InputSymbol;
        /**
         * TopOfStack
         */
        public int TopOfStack;
        Precedence(int i, int t) {
            InputSymbol = i;
            TopOfStack = t;
        }
 
    }

    private static final int VARIABLE = 0;
    private static final int VALUE = 1;
    private static final int OPAREN = 2;
    private static final int CPAREN = 3;
    private static final int EQUAL = 4;
    private static final int NOTEQUAL = 5;
    private static final int GREATER = 6;
    private static final int GOE = 7;
    private static final int LESSER = 8;
    private static final int LOE = 9;
    private static final int OR = 10;
    private static final int AND = 11;
    private static final int NOT = 12;
    private static final int BLANK = 13;

    private int m_iIndex = 0;
    private String m_strCurrentVariable = null;
    private String m_strCurrentValue = null;
    private Stack m_stkOpStack = new Stack();
    private Stack m_stkResultStack = new Stack();
    private int m_iLastToken = -1;

    /**
     * @serial
     */
    static final long serialVersionUID = 10L;

    private Precedence PrecTable[] = { new Precedence(0, 0), new Precedence(0, 0), //VARIABLE, VALUE
        new Precedence(100, 0), new Precedence(0, 99), //OPAREN, CPAREN
        new Precedence(5, 6), new Precedence(5, 6), //EQUAL, NOTEQUAL
        new Precedence(5, 6), new Precedence(5, 6), //GREATER, GOE
        new Precedence(5, 6), new Precedence(5, 6), //LESSER, LOE
        new Precedence(1, 2), new Precedence(1, 2), //OR, AND
        new Precedence(3, 4)};

    private String m_strOperators = "!=<>()|&:.*";
    private String m_strDot = ".LGTE";

    /**
     * Evaluator
     *
     *  @author David Bigelow
     */
    public Evaluator() {
        return;
    }

    private boolean inDotString(char c) {
        String s = String.valueOf(c).toUpperCase();
        int i = m_strDot.indexOf(s);
        if (i < 0) {
            return false;
        }
        return true;
    }

    private boolean inOpArray(char c) {
        String s = String.valueOf(c);
        int i = m_strOperators.indexOf(s);
        if (i < 0) {
            return false;
        }
        return true;
    }

    private int getToken(String _strCondition) {
        String str1 = null;
        char cToken;
        cToken = _strCondition.charAt(m_iIndex);
        switch (cToken) {
        case '(' :
            return OPAREN;
        case ')' :
            return CPAREN;
        case '=' :
            cToken = _strCondition.charAt(m_iIndex + 1);
            if (cToken == '=') {
                m_iIndex++;
                return EQUAL;
            }
        case '!' :
            cToken = _strCondition.charAt(m_iIndex + 1);
            if (cToken == '=') {
                m_iIndex++;
                return NOTEQUAL;
            } else {
                return NOT;
            }
        case '.' : //use .lt,.gt,.gte,.lte for html version
            str1 = "";
            m_iIndex++;
            for (int i = m_iIndex; i < _strCondition.length(); i++) {
                cToken = _strCondition.charAt(i);
                if (!inDotString(cToken)) {
                    break;
                } else {
                    Character c = new Character(cToken);
                    str1 = str1.concat(c.toString());
                    m_iIndex = i;
                }
            }
            str1 = str1.trim();
            if (str1.equalsIgnoreCase("lt")) {
                return LESSER;
            
            } else if (str1.equalsIgnoreCase("lte")) {
                return LOE;
            
            } else if (str1.equalsIgnoreCase("gt")) {
                return GREATER;
            
            } else if (str1.equalsIgnoreCase("gte")) {
                return GOE;
            }
            return VARIABLE;
        case '>' :
            cToken = _strCondition.charAt(m_iIndex + 1);
            if (cToken == '=') {
                m_iIndex++;
                return GOE;
            } else {
                return GREATER;
            }

        case '<' :
            cToken = _strCondition.charAt(m_iIndex + 1);
            if (cToken == '=') {
                m_iIndex++;
                return LOE;
            } else {
                return LESSER;
            }
        case '|' :
            cToken = _strCondition.charAt(m_iIndex + 1);
            if (cToken == '|') {
                m_iIndex++;
                return OR;
            }
        case '*' : //use for && in html
            cToken = _strCondition.charAt(m_iIndex + 1);
            if (cToken == '*') {
                m_iIndex++;
                return AND;
            }
        case '&' :
            cToken = _strCondition.charAt(m_iIndex + 1);
            if (cToken == '&') {
                m_iIndex++;
                return AND;
            }
        case ' ' : //remove spaces
            return BLANK;
        case ':' : //attributeCode
            m_strCurrentVariable = "";
            m_iIndex++;
            for (int i = m_iIndex; i < _strCondition.length(); i++) {
                cToken = _strCondition.charAt(i);
                if (inOpArray(cToken)) {
                    break;
                } else {
                    Character ch = new Character(cToken);
                    m_strCurrentVariable = m_strCurrentVariable.concat(ch.toString());
                    m_iIndex = i;
                }
            }
            m_strCurrentVariable = m_strCurrentVariable.trim();
            return VARIABLE;
        default :
            m_strCurrentValue = "";
            for (int ii = m_iIndex; ii < _strCondition.length(); ii++) {
                cToken = _strCondition.charAt(ii);
                if (inOpArray(cToken)) {
                    break;
                } else {
                    Character c = new Character(cToken);
                    m_strCurrentValue = m_strCurrentValue.concat(c.toString());
                    m_iIndex = ii;
                }
            }
            m_strCurrentValue = m_strCurrentValue.trim();
            return VALUE;
        }
    }

    private boolean BinaryOp(int _iTopOp, String _strCondition) {

        Object oRhside = null;
        Object oLhside = null;

        if (_iTopOp == NOT) {
            boolean b = ((Boolean) m_stkResultStack.pop()).booleanValue();
            if (b) {
                m_stkResultStack.push(new Boolean(false));
            
            } else {
                m_stkResultStack.push(new Boolean(true));
            }
            m_stkOpStack.pop();
            return true;
        }

        if (!m_stkResultStack.empty()) {
            oRhside = m_stkResultStack.pop();
        
        } else {
            System.out.println(_strCondition + ": missing operands");
        }
        if (!m_stkResultStack.empty()) {
            oLhside = m_stkResultStack.pop();
        
        } else {
            System.out.println(_strCondition + ": missing operands");
        }

        if (oRhside.getClass() != oLhside.getClass()) {
            System.out.println(_strCondition + ": Wrong type comparison");
        }

        if (_iTopOp == EQUAL) {
            String Rhs = oRhside.toString();
            String Lhs = oLhside.toString();
            m_stkResultStack.push(new Boolean(Lhs.equals(Rhs)));
        } else if (_iTopOp == NOTEQUAL) {
            String Rhs = oRhside.toString();
            String Lhs = oLhside.toString();
            m_stkResultStack.push(new Boolean(!Lhs.equals(Rhs)));
        } else if (_iTopOp == GOE) {
            String Rhs = oRhside.toString();
            String Lhs = oLhside.toString();
            m_stkResultStack.push(new Boolean(Lhs.compareToIgnoreCase(Rhs) >= 0));
        } else if (_iTopOp == GREATER) {
            String Rhs = oRhside.toString();
            String Lhs = oLhside.toString();
            m_stkResultStack.push(new Boolean(Lhs.compareToIgnoreCase(Rhs) > 0));
        } else if (_iTopOp == LOE) {
            String Rhs = oRhside.toString();
            String Lhs = oLhside.toString();
            m_stkResultStack.push(new Boolean(Lhs.compareToIgnoreCase(Rhs) <= 0));
        } else if (_iTopOp == LESSER) {
            String Rhs = oRhside.toString();
            String Lhs = oLhside.toString();
            m_stkResultStack.push(new Boolean(Lhs.compareToIgnoreCase(Rhs) < 0));
        } else if (_iTopOp == OR) {
            Boolean Rhs = (Boolean) oRhside;
            Boolean Lhs = (Boolean) oLhside;
            m_stkResultStack.push(new Boolean(Rhs.booleanValue() || Lhs.booleanValue()));
        } else if (_iTopOp == AND) {
            Boolean Rhs = (Boolean) oRhside;
            Boolean Lhs = (Boolean) oLhside;
            m_stkResultStack.push(new Boolean(Rhs.booleanValue() && Lhs.booleanValue()));
        }
        m_stkOpStack.pop();
        return true;
    }

    private String getSetFlag(EANFlagAttribute _fa) {
        MetaFlag[] amf = (MetaFlag[]) _fa.get();
        for (int ii = 0; ii < amf.length; ii++) {
            if (amf[ii].isSelected()) {
                return amf[ii].getFlagCode();
            }
        }
        return "";
    }

    private String getValue(EntityItem _ei, String _strAttCode) {

        EANAttribute att = null;
        EANMetaAttribute ma = null;
        
        if (_ei == null && _strAttCode == null) {
            System.out.println("getValue() -- EntityItem and attributeCode required.");
            return "";
        } else if (_ei == null) {
            System.out.println("Evaluator.getValue requires non-null Entity Item");
            return "";
        } else if (_strAttCode == null) {
            System.out.println("Evaluator.getValue requires non-null attCode");
            return "";
        }

        att = _ei.getAttribute(_strAttCode);
        if (att == null) {
            //			System.out.println("getValue() -- attribute not found for " + _strAttCode + ". Returning empty string");
            return "";
        }
        // Lets get the PDHMeta Attribute
        ma = att.getMetaAttribute();
        if (ma == null) {
            System.out.println("getValue() -- meta not found for " + _strAttCode + ". Returning empty string");
            return "";
        }
        if (att instanceof EANFlagAttribute) {
            EANFlagAttribute fa = (EANFlagAttribute) att;
            return getSetFlag(fa);
        } else {
            EANTextAttribute ta = (EANTextAttribute) att;
            return ta.toString();
        }
    }

    private boolean processToken(EntityItem _ei, String _strCondition) {
        int iTopOp = -1;
        if (m_iLastToken == CPAREN) {
            while ((!m_stkOpStack.empty()) && (iTopOp = ((Integer) m_stkOpStack.peek()).intValue()) != OPAREN) {
                if (!BinaryOp(iTopOp, _strCondition)) {
                    return false;
                }
            }
            if (iTopOp == OPAREN) {
                m_stkOpStack.pop();
            } else {
                System.out.println("processToken() -- missing open parenthesis: " + _strCondition);
            }

        } else if (m_iLastToken == VARIABLE) {
            m_stkResultStack.push(getValue(_ei, m_strCurrentVariable));
        } else if (m_iLastToken == VALUE) {
            m_stkResultStack.push(m_strCurrentValue);
        } else if (m_iLastToken == BLANK) {
            return true;
        } else {
            while ((!m_stkOpStack.empty()) && (PrecTable[m_iLastToken].InputSymbol <= PrecTable[iTopOp = ((Integer) m_stkOpStack.peek()).intValue()].TopOfStack)) {
                if (!BinaryOp(iTopOp, _strCondition)) {
                    return false;
                }
            }
            m_stkOpStack.push(new Integer(m_iLastToken));
        }
        return true;
    }

    /**
     * process
     *
     * @param _ei
     * @param _strCondition
     * @return
     *  @author David Bigelow
     */
    public boolean process(EntityItem _ei, String _strCondition) {

        boolean bResult = false;
        Profile prof = _ei.getProfile();

        if (_strCondition == null || _strCondition.equals("")) {
            return false;
        }

        // O.K.  We want to pick off any role information here... So
        // lets see if the role is present
        // We look because a ? is used as a divider
        // ROLE ? rest of equation

        if (_strCondition.indexOf("?") != -1) {
            // We have to check role
            StringTokenizer st = new StringTokenizer(_strCondition, "?");
            String strRoleCode = st.nextToken();

            // You need to reassign the substring for the rest of the story
            _strCondition = st.nextToken();

            // If you cannot find a matching rolecode.. return false

            if (prof != null) {
                if (!strRoleCode.equals(prof.getRoleCode())) {
                    return false;
                }
            } else {
                return false;
            }

        }

        m_iIndex = 0;

        do {
            m_iLastToken = getToken(_strCondition);
            m_iIndex++;
            if (!processToken(_ei, _strCondition)) {
                return false;
            }
        } while (m_iIndex < _strCondition.length());

        while (!m_stkOpStack.empty()) {

            int iTopOp = ((Integer) m_stkOpStack.peek()).intValue();
            if (!BinaryOp(iTopOp, _strCondition)) {
                return false;
            }
        }

        if (!(m_stkResultStack.peek() instanceof Boolean)) {
            System.out.println("This should be a boolean.. but It is a String:" + ((String) m_stkResultStack.pop()));
            return false;
        }

        bResult = ((Boolean) m_stkResultStack.pop()).booleanValue();
        if (!m_stkResultStack.empty()) {
            System.out.println("process() -- missing operators " + _strCondition);
        }
        return bResult;
    }

    /**
     * Automatically generated method: clone
     *
     * @return Object
     * @exception java.lang.CloneNotSupportedException
     */
    public Object clone() 
        throws CloneNotSupportedException
    {
        super.clone ();
        return null;
    }

 
}
