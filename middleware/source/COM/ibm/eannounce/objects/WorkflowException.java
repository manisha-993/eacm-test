//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: WorkflowException.java,v $
// Revision 1.2  2002/05/09 21:45:20  joan
// working on throw exception
//
// Revision 1.1  2002/05/09 20:28:23  joan
// initial load
//
//

package COM.ibm.eannounce.objects;
import COM.ibm.opicmpdh.transactions.OPICMList;


public class WorkflowException extends Exception {
  	private EANList m_elFailures = null;
  	private OPICMList m_olMessages = null;

  	/**
	* Constructs a <code>WorkflowException</code> with no specified detail message
 	*/
  	public WorkflowException() {
  		this("no detail message");
		m_elFailures = new EANList();
  		m_olMessages = new OPICMList();
  	}

  	/**
  	* Constructs a <code>WorkflowException</code> with the specified detail message
  	*/
  	public WorkflowException(String s) {
  		super(s);
    	m_elFailures = new EANList();
    	m_olMessages = new OPICMList();
  	}

	/*
	* Adds an Attribute and Message to the failure list
  	*/
  	public void add(EANFoundation _ean,String _s) {
  		m_elFailures.put(_ean);
  		m_olMessages.put(_ean.getKey(),_s);
  	}

  	/*
  	* Returns the String representation of all errors in this exception
  	* @return the String representation of all errors in this exception
  	*/
  	public String toString() {
  		String strAnswer= "# " + m_elFailures.size() + "\n";
  		for (int x = 0;x < m_elFailures.size();x++) {
  		    EANObject obj = m_elFailures.getAt(x);
  		    String strObj = "Unknown Object";
  		    if (obj instanceof EANFoundation) strObj = ((EANFoundation)obj).getKey();
  		    String strDesc = (String) m_olMessages.getAt(x);
  		    strAnswer = strAnswer + ":" + strObj + " - " + strDesc + "\n";
  		}
  		return strAnswer;
  	}

  	/*
  	* Number of failures in the exception
  	*/
  	public int getErrorCount() {
  		return m_elFailures.size();
  	}

  	/*
  	* return the failed PDHAttribute
  	*/
  	public Object getObject(int _i) {
  		if (_i > getErrorCount()) return null;
  		return(Object)m_elFailures.getAt(_i);
  	}

  	protected void clearLists() {
		m_elFailures = new EANList();
  		m_olMessages = new OPICMList();
  	}

  	/**
  	* Return the date/time this class was generated
  	* @return the date/time this class was generated
  	*/
  	public String getVersion() {
  		return new String("$Id: WorkflowException.java,v 1.2 2002/05/09 21:45:20 joan Exp $");
  	}

}
