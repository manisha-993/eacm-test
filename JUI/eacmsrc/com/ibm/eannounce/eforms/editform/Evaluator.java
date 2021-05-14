/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: Evaluator.java,v $
 * Revision 1.1  2007/04/18 19:44:18  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:12  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:55  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:02  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/04 18:16:49  tony
 * JTest Formatter Fourth Pass
 *
 * Revision 1.3  2005/02/04 15:22:09  tony
 * JTest Format Third Pass
 *
 * Revision 1.2  2005/01/27 19:15:16  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:39  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2002/11/11 22:55:38  tony
 * adjusted classification on the toggle
 *
 * Revision 1.6  2002/11/07 16:58:26  tony
 * added/adjusted copyright statement
 *
 */
/*
<show name="PANEL_THREE"  equation="%PSGOF:OFCATTYPE==0040 **%PSGPR:OFSUBCATTYPE==0060 **%PSGOF:OFGROUPTYPE==0040 **%PSGOF:OFSUBGROUPTYPE==0050"/>
*/
package com.ibm.eannounce.eforms.editform;
import com.elogin.Routines;
import COM.ibm.eannounce.objects.*;
import java.util.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class Evaluator {
	private class Precedence {
		/**
         * InputSymbil
         */
        private int InputSymbol;
		/**
         * TopOfStack
         */
        private int TopOfStack;
		private Precedence(int i, int t) {
			InputSymbol = i;
			TopOfStack = t;
		}
	}

	private static final int VARIABLE = 0;
//	private static final int ATTRIBUTE = 0;												//e1.0_AttProc
	private static final int VALUE = 1;
	private static final int OPAREN  = 2;
	private static final int CPAREN  = 3;
	private static final int EQUAL  = 4;
	private static final int NOTEQUAL = 5;
	private static final int GREATER  = 6;
	private static final int GOE = 7;
	private static final int LESSER = 8;
	private static final int LOE = 9;
	private static final int OR = 10;
	private static final int AND = 11;
	private static final int NOT = 12;
	private static final int BLANK = 13;

//	private static Evaluator e = null;
	private HashMap attProcessed = new HashMap();
	private HashMap hideList = new HashMap();											//e1.0_AttProc
	private HashMap showList = new HashMap();											//e1.0_AttProc
	private int index = 0;
	private String currentVariable = null;
	private String currentValue = null;
	private Stack opStack = new Stack();
	private Stack resultStack = new Stack();
	private int lastToken = -1;
//	private static String vArray[][] = new String[4][2];
	private final Precedence PrecTable[] =
		{
			new Precedence(0,0), new Precedence(0,0),			//VARIABLE, VALUE
			new Precedence(100, 0), new Precedence(0, 99), 		//OPAREN, CPAREN
			new Precedence(5, 6), new Precedence(5, 6),			//EQUAL, NOTEQUAL
			new Precedence(5, 6), new Precedence(5, 6),			//GREATER, GOE
			new Precedence(5, 6), new Precedence(5, 6),			//LESSER, LOE
			new Precedence(1, 2), new Precedence(1, 2),			//OR, AND
			new Precedence(3, 4)
		};

//	private char opArray[] = { '!', '=', '<', '>', '(', ')', '|', '&', ':', '.'};
	private final String opString = new String("!=<>()|&%.*");
	private final String dotString = new String(".LGTE");
	private RowSelectableTable table = null;
	private int rowKey = -1;									//preview

	/**
     * Evaluator
     * @author Anthony C. Liberto
     */
    public Evaluator () {
		return;
	}

	/**
     * clearHash
     * @author Anthony C. Liberto
     */
    public void clearHash() {
		attProcessed.clear();
		hideList.clear();
		showList.clear();
		return;
	}

	/**
     * doesHashExist
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public boolean doesHashExist(String _key) {
		return attProcessed.containsKey(_key);
	}

	/**
     * isAttributeEvaluated
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isAttributeEvaluated(String _key) {
		return doesHashExist(_key) || isAttOnList(_key);
	}

	/**
     * addList
     * @param _key
     * @param _equation
     * @param _type
     * @author Anthony C. Liberto
     */
    public void addList(String _key,String _equation,String _type) {
		if (_type.equalsIgnoreCase("show")) {
			if (!isOnShowList(_key)) {
				showList.put(_key,_equation);
			} else {
				showList.put(_key,(String)showList.get(_key) + _equation);
				preprocess(_equation);
			}
		} else if (_type.equalsIgnoreCase("hide")) {
			if (!isOnHideList(_key)) {
				hideList.put(_key,_equation);
			} else {
				hideList.put(_key,(String)hideList.get(_key) + _equation);
				preprocess(_equation);
			}
		}
		return;
	}

    /**
     * isAttributeVisible
     *
     * @param _key
     * @throws com.ibm.eannounce.eforms.editform.EvaluatorException
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isAttributeVisible(String _key) throws EvaluatorException {
		if (isOnShowList(_key)) {
			return process((String)showList.get(_key));
		} else if (isOnHideList(_key)) {
			return !process((String)hideList.get(_key));
		}
		return true;
	}

	private boolean isOnShowList(String _key) {
		return showList.containsKey(_key);
	}

	private boolean isOnHideList(String _key) {
		return hideList.containsKey(_key);
	}

	private boolean isAttOnList(String _key) {
		return isOnShowList(_key) || isOnHideList(_key);
	}

	private void addHash(String _key) {
		if (!doesHashExist(_key)) {
			attProcessed.put(_key, null);
		}
		return;
	}

	private boolean inDotString(char c) {
		String s = String.valueOf(c).toUpperCase();
		int i = dotString.indexOf(s);
		if (i < 0) {
			return false;
		}
		return true;
	}

	private boolean inOpArray(char c) {
		String s = String.valueOf(c);
		int i = opString.indexOf(s);
		if (i < 0) {
			return false;
		}
		return true;
	}

	private int getToken(String condSt) {
        String tmpVar = "";
        char token;
		token = condSt.charAt(index);
		switch(token) {
		case '(':
			return OPAREN ;
		case ')':
			return CPAREN;
		case '=':
			token = condSt.charAt(index+1);
			if(token == '=' ) {
				index++;
				return EQUAL;
			}
		case '!':
			token = condSt.charAt(index+1);
			if(token == '=') {
				index++;
				return NOTEQUAL;
			} else {
				return NOT;
			}
		case '.':								//use .lt,.gt,.gte,.lte for html version
			index++;
			for( int i = index; i < condSt.length(); i++) {
				token = condSt.charAt(i);
				if (!inDotString(token)) {
					break;
				} else {
					Character c = new Character(token);
					tmpVar = tmpVar.concat(c.toString());
					index = i;
				}
			}
			tmpVar = tmpVar.trim();
			if (tmpVar.equalsIgnoreCase("lt")) {
				return LESSER;

			} else if (tmpVar.equalsIgnoreCase("lte")) {
				return LOE;

			} else if (tmpVar.equalsIgnoreCase("gt")) {
				return GREATER;

			} else if (tmpVar.equalsIgnoreCase("gte")) {
				return GOE;
			}
			return VARIABLE;
		case '>':
			token = condSt.charAt(index+1);
			if(token == '=') {
				index++;
				return GOE;
			} else {
				return GREATER;
			}

		case '<':
			token = condSt.charAt(index+1);
			if(token == '=') {
				index++;
				return LOE;
			} else {
				return LESSER;
			}
		case '|':
			token = condSt.charAt(index+1);
			if(token == '|') {
				index++;
				return OR;
			}
		case '*':															//use for && in html
			token = condSt.charAt(index+1);
			if(token == '*') {
				index++;
				return AND;
			}
		case '&':
			token = condSt.charAt(index+1);
			if(token == '&') {
				index++;
				return AND;
			}
		case ' ':															//remove spaces
			return BLANK;
		case '%':															//item key
			currentVariable = "";
			index++;
			for( int i = index; i < condSt.length(); i++) {
				token = condSt.charAt(i);
				if (inOpArray(token)) {
					break;
				} else {
					Character c = new Character(token);
					currentVariable = currentVariable.concat(c.toString());
					index = i;
				}
			}
			currentVariable = currentVariable.trim();
			addHash(currentVariable);
			return VARIABLE;
		default:
			currentValue = "";
			for( int i = index; i < condSt.length(); i++) {
				token = condSt.charAt(i);
				if (inOpArray(token)) {
					break;
				} else {
					Character c = new Character(token);
					currentValue = currentValue.concat(c.toString());
					index = i;
				}
			}
			currentValue = currentValue.trim();
			return VALUE;
		}
	}

	private boolean binaryOp(int topOp, String condSt) throws EvaluatorException {
        Object Rhside = null;
        Object Lhside = null;
		if (topOp == NOT) {
			boolean b = ((Boolean) resultStack.pop()).booleanValue();
			if (b) {
				resultStack.push(new Boolean(false));

			} else {
				resultStack.push(new Boolean(true));
			}
			opStack.pop();
			return true;
		}

		if (!resultStack.empty()) {
			Rhside = resultStack.pop();

		} else {
			throw new EvaluatorException("BinaryOp() -- missing operands", condSt);
		}
		if (!resultStack.empty()) {
			Lhside = resultStack.pop();

		} else {
			throw new EvaluatorException("BinaryOp() -- missing operands", condSt);
		}

		if (Rhside.getClass() != Lhside.getClass()) {
			throw new EvaluatorException("BinaryOp() -- Wrong type comparison", condSt);
		}

		if (topOp == EQUAL) {
			String Rhs = Rhside.toString();
			String Lhs = Lhside.toString();
			resultStack.push(new Boolean(Lhs.equals(Rhs)));
		} else if (topOp == NOTEQUAL) {
			String Rhs = Rhside.toString();
			String Lhs = Lhside.toString();
			resultStack.push(new Boolean(!Lhs.equals(Rhs)));
		} else if (topOp == GOE) {
			String Rhs = Rhside.toString();
			String Lhs = Lhside.toString();
			resultStack.push(new Boolean(Lhs.compareToIgnoreCase(Rhs) >= 0));
		} else if (topOp == GREATER) {
			String Rhs = Rhside.toString();
			String Lhs = Lhside.toString();
			resultStack.push(new Boolean(Lhs.compareToIgnoreCase(Rhs) > 0));
		} else if (topOp == LOE) {
			String Rhs = Rhside.toString();
			String Lhs = Lhside.toString();
			resultStack.push(new Boolean(Lhs.compareToIgnoreCase(Rhs) <= 0));
		} else if (topOp == LESSER) {
			String Rhs = Rhside.toString();
			String Lhs = Lhside.toString();
			resultStack.push(new Boolean(Lhs.compareToIgnoreCase(Rhs) < 0));
		} else if (topOp == OR) {
			Boolean Rhs = (Boolean) Rhside;
			Boolean Lhs = (Boolean) Lhside;
			resultStack.push(new Boolean(Rhs.booleanValue() || Lhs.booleanValue()));
		} else if (topOp == AND) {
			Boolean Rhs = (Boolean) Rhside;
			Boolean Lhs = (Boolean) Lhside;
			resultStack.push(new Boolean(Rhs.booleanValue() && Lhs.booleanValue()));
		}
		opStack.pop();
		return true;
	}

	/**
     * getSetFlagArray
     *
     * @author Anthony C. Liberto
     * @param _flag
     * @return
     */
    protected String[] getSetFlagArray(EANFlagAttribute _flag) {
		MetaFlag[] flags = (MetaFlag[])_flag.get();
		int ii = flags.length;
		Vector v = new Vector();
		for (int i=0;i<ii;++i) {
			if (flags[i].isSelected()) {
				v.add(flags[i].getFlagCode());
			}
		}
		if (!v.isEmpty()) {
			int xx = v.size();
			String[] out = new String[xx];
			for (int x=0;x<xx;++x) {
				out[x] = (String)v.get(x);
			}
			return out;
		}
		return null;
	}

	private String getSetFlag(EANFlagAttribute _flag) {
		MetaFlag[] flags = (MetaFlag[])_flag.get();
		int ii = flags.length;
		for (int i=0;i<ii;++i) {
			if (flags[i].isSelected()) {
				return flags[i].getFlagCode();
			}
		}
		return "";
	}

	private String getValue(String _key) throws EvaluatorException {
		EANAttribute att = null;
        Object o = null;
        if ((_key == null || !Routines.have(_key))) {
			throw new EvaluatorException("getValue() -- key required.");
		}
		att = getAttribute(_key);
		if (att == null) {
			throw new EvaluatorException("getValue() -- attribute not found for", _key);
		}
		if (att instanceof EANFlagAttribute) {
			return getSetFlag((EANFlagAttribute)att);
		}
		o = att.get();
		if (o instanceof String) {
			return (String)o;
		}
		return "";
	}

	private EANAttribute getAttribute(String _key) {
        Object o = null;
		if (table == null) {
            return null;
		}
		if (rowKey < 0) {								//preview
			int row = table.getRowIndex(_key);
			if (row < 0) {
				return null;
			}
			o = table.getEANObject(row,1);
		} else {										//preview
			int col = table.getColumnIndex(_key);		//preview
			if (col < 0) {								//preview
				return null;
			}							//preview
			o = table.getEANObject(rowKey, col);		//preview
		}												//preview
		if (o != null && o instanceof EANAttribute) {
			return (EANAttribute)o;
		}
		return null;
	}

	private boolean processToken(String _condSt) throws EvaluatorException {
		int topOp = -1;
		if (lastToken == CPAREN) {
			while ( (!opStack.empty()) && (topOp = ((Integer)opStack.peek()).intValue()) != OPAREN ) {
				if( !binaryOp(topOp,_condSt)) {
					return false;
				}
			}
			if (topOp == OPAREN) {
				opStack.pop();

			} else {
				throw new EvaluatorException("processToken() -- missing open parenthesis", _condSt);
			}

		} else if (lastToken == VARIABLE) {
			resultStack.push(getValue(currentVariable));
		} else if (lastToken == VALUE) {
			resultStack.push(currentValue);
		}
		else if (lastToken == BLANK) {
			return true;

		} else {
			while ( (!opStack.empty()) && (PrecTable[lastToken].InputSymbol <=
					PrecTable[topOp = ((Integer)opStack.peek()).intValue()].TopOfStack) ) {
				if( !binaryOp(topOp,_condSt)) {
					return false;
				}
			}
			opStack.push(new Integer(lastToken));
		}
		return true;
	}

	private void preprocess(String _condSt) {
		if (!Routines.have(_condSt)) {
			return;
		}
		index = 0;
		do {
			lastToken = getToken(_condSt);
			index++;
		} while(index < _condSt.length());
		return;
	}

	private boolean process(String _condSt) throws EvaluatorException{
		boolean result = false;
        if (!Routines.have(_condSt)) {
			return false;
		}
		index = 0;
		do {
			lastToken = getToken(_condSt);
			index++;
			if(!processToken(_condSt)) {
				return false;
			}
		} while( index < _condSt.length());

		while (!opStack.empty()) {
			int topOp = ((Integer)opStack.peek()).intValue();
			if(!binaryOp(topOp,_condSt)) {
				return false;
			}
		}

		result = ((Boolean)resultStack.pop()).booleanValue();
		if (! resultStack.empty()) {
			throw new EvaluatorException("process() -- missing operators", _condSt);
		}
		return result;
	}

/*
 * public methods....
 * this is all you get make sure
 * RowSelectableTable is initialized.
 */
    /**
     * setTable
     * @param _table
     * @author Anthony C. Liberto
     */
    public void setTable(RowSelectableTable _table) {
		table = _table;
	}

	/**
     * getTable
     * @return
     * @author Anthony C. Liberto
     */
    public RowSelectableTable getTable() {
		return table;
	}


    /**
     * process
     *
     * @param _table
     * @param _condSt
     * @throws com.ibm.eannounce.eforms.editform.EvaluatorException
     * @return
     * @author Anthony C. Liberto
     */
    public boolean process(RowSelectableTable _table, String _condSt) throws EvaluatorException{
		setTable(_table);
		setRowKey(-1);
		return process(_condSt);
	}

/*
 * preview modification
 */

    /**
     * process
     *
     * @param _table
     * @param _rowKey
     * @param _condSt
     * @throws com.ibm.eannounce.eforms.editform.EvaluatorException
     * @return
     * @author Anthony C. Liberto
     */
    public boolean process(RowSelectableTable _table, int _rowKey, String _condSt) throws EvaluatorException{
		setTable(_table);
		setRowKey(_rowKey);
		return process(_condSt);
	}

	private void setRowKey(int _i) {
		rowKey = _i;
	}

	/**
     * getRowKey
     * @return
     * @author Anthony C. Liberto
     */
    public int getRowKey() {
		return rowKey;
	}

}
