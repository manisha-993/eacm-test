/*
 * Created on Jan 10, 2005
 *
 * Licensed Materials -- Property of IBM
 *
 * (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
 * The source code for this program is not published or otherwise divested of
 * its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
 */
package com.ibm.transform.oim.eacm.xalan.table;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.transform.oim.eacm.xalan.EntityParam;
import com.ibm.transform.oim.eacm.xalan.Init;
import com.ibm.transform.oim.eacm.xalan.JarURIResolver;
import com.ibm.transform.oim.eacm.xalan.Table;

/**
 * Created for setsing
 *
 * <pre>
 * $Log: TestTable.java,v $
 * Revision 1.2  2006/01/26 15:24:30  wendy
 * AHE copyright
 *
 * Revision 1.1  2005/09/08 19:13:55  wendy
 * New pkg
 *
 * Revision 1.1  2005/02/23 21:13:02  chris
 * Initial XSL Report ABR Code
 *
 * </pre>
 *
 * @author cstolpe
 */
public class TestTable extends SimpleTable implements Init, EntityParam {

    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.Init#setDatabase(COM.ibm.opicmpdh.middleware.Database)
     */
    public boolean setDatabase(Database database) {
        if (database == null) {
            System.err.println("TestTable.setDatabase(Database): database is null");  //$NON-NLS-1$
            return false;
        }
        return true;
    }
    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.Init#setProfile(COM.ibm.opicmpdh.middleware.Profile)
     */
    public boolean setProfile(Profile profile) {
        if (profile == null) {
            System.err.println("TestTable.setProfile(Profile): profile is null");  //$NON-NLS-1$
            return false;
        }
        return true;
    }
    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.Init#initialize()
     */
    public boolean initialize() {
        // TODO Auto-generated method stub
        return true;
    }
    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.Init#dereference()
     */
    public boolean dereference() {
        return true;
    }

    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.EntityParam#setEntityType(java.lang.String)
     */
    public boolean setEntityType(String entityType) {
        if (entityType == null) {
            System.err.println("TestTable.setEntityType(String): parameter is null");  //$NON-NLS-1$
            return false;
        }
        if (entityType.equals("")) {  //$NON-NLS-1$
            System.err.println("TestTable.setEntityType(String): parameter is empty string");  //$NON-NLS-1$
            return false;
        }
        return true;
    }

    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.EntityParam#setEntityID(int)
     */
    public boolean setEntityID(int entityID) {
        if (entityID < 0) {
            System.err.println("TestTable.setEntityID(int): parameter is less than zero");  //$NON-NLS-1$
            return false;
        }
        return true;
    }

    /**
     * Test
     * @param args
     */
    public static void main(String[] args) {
        try {
            // each ABR would have different number of table(s) and different classes for the table(s) and different stylesheet
            // That could be stored in a configuration file
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer xslt;
            Source input = new StreamSource(args[0]); // This would be DOM template of ABR that was filled in
            Source style = new StreamSource(args[1]); // This would be style sheet in DOM form using Eann.getCachedDocumentResource
            Result output = new StreamResult(args[2]); // This would be ABR Stream
            Table table1;
            Table table2;
            factory.setURIResolver(new JarURIResolver());
            xslt = factory.newTransformer(style);
            table1 = new TestTable(); // Creates instance of class that will
            table2 = new SimpleTable();
            xslt.setParameter("table1ref", table1);  //$NON-NLS-1$
            xslt.setParameter("table2ref", table2);  //$NON-NLS-1$
            xslt.transform(input, output);
        } catch (TransformerConfigurationException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (TransformerFactoryConfigurationError e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (TransformerException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
