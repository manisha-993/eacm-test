package com.ibm.eannounce.wwprt.catcher;

import com.ibm.cloud.cloudant.v1.Cloudant;
import com.ibm.cloud.cloudant.v1.model.FindResult;
import com.ibm.cloud.cloudant.v1.model.PostFindOptions;
import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.connection.ConnectionFactory;
import com.ibm.eannounce.wwprt.*;
import com.ibm.eannounce.wwprt.dao.CountNewPricesXMLQuery;
import com.ibm.eannounce.wwprt.dao.InsertPricesXML;
import com.ibm.eannounce.wwprt.dao.MaxPricesIDQuery;
import com.ibm.eannounce.wwprt.notification.NotificationBuilder;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ibm.db2.jcc.a.a.e.e;

public class CloudantCatcher {

    private SAXParser parser;
    private Connection connection;
    private CloudantListener catcherListener;

    private Transaction transaction = null;
    public int id = 0;

    public CloudantCatcher() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(false);
            parser = factory.newSAXParser();
        } catch (Exception e) {
            throw new IllegalStateException("Unable to create SAX Parser. Java 1.5 is required.");
        }
    }

    public CloudantListener getCatcherListener() {
        return catcherListener;
    }

    public void setCatcherListener(CloudantListener catcherListener) {
        this.catcherListener = catcherListener;
    }

    public void pullPricetoXML(String t1, String t2) {
        System.out.println("pullPricetoXML() called");
        int size = CloudantUtil.getSize();
        Map<String, Object> selector = new HashMap<>();
        HashMap operate = new HashMap();
        operate.put("$gt", t1);
        operate.put("$lt", t2);
        selector.put("UPDATETS", operate);
        int skip = 0;
        int total = 0;
        FindResult result = null;

        do {
            Log.i("Pulling data on skip:" + skip + " size:" + size);
            long start = System.currentTimeMillis();
            result = pullPriceFromCloudant(selector, skip, size);
            Log.v("Result from cloudant");
            size = result.getDocs().size();
            try {
                if (size > 0) {
                    catchAndProcessMessage(result);
                }
            } catch (JAXBException e) {
                Log.e("Error occur when transfer to xml: " + e.getMessage());
                throw new RuntimeException(e);
            }

            skip += size;
            total += size;
            if (size > 0) {
                Log.i("Pulled data size:" + size + ", pulled total:" + total);
                long end = System.currentTimeMillis();
                long timeDiff = (end - start) / 1000; //
                Log.i("Time cost:" + timeDiff + " seconds");
            }

            //catcherListener.onCheck();
        } while (result.getDocs().size() > 0);


    }

    public void close() throws Exception {
        // Close the connection if active
        if (connection != null && !connection.isClosed()) {
            Log.v("Catcher read all messages, closing the connection...");
            ConnectionFactory connectionFactory = Context.get().getConnectionFactory();
            connectionFactory.commit(connection);
            connectionFactory.close(connection);
            connection = null;
            Log.v("Catcher connection closed");
        }
    }

    public boolean initConnection() {
        // First of all check the connection
        Log.v("init Connection");
        System.out.println("initConnection() called");
        ConnectionFactory connectionFactory = CloudantContext.get().getConnectionFactory();
        Log.v("init Connection step 1");
        try {
            if (connection == null || connection.isClosed()) {
                Log.v("init Connection step 2");
                Log.i("trying db connectivity");
                connection = connectionFactory.getConnection();
                Log.v("Catcher started a new db connection");
                transaction = new Transaction(connection);
            }
            return true;
        } catch (SQLException e) {
            Log.v("init Connection step 3 " + e.getStackTrace());
            if (connection != null) {
                Log.e("Disconnected from the DB", e);
                try {
                    connectionFactory.close(connection);
                } catch (SQLException e2) {
                    Log.e("Unable to close the connection: " + e2.getMessage());
                }
            }
        }
        return false;
    }

    public FindResult pullPriceFromCloudant(Map<String, Object> selector, long skip, int limit) {
        Cloudant client = com.ibm.eannounce.wwprt.CloudantUtil.getClient();
        PostFindOptions findOptions = com.ibm.eannounce.wwprt.CloudantUtil.getFindOptions(selector, skip, limit);
        FindResult result = null;
        int times = 0;
        while (true)
            try {
                result = client.postFind(findOptions).execute().getResult();
                return result;
            } catch (Exception e) {

                times++;
                if (times > 5) {
                    com.ibm.eannounce.wwprt.Log.e("Failed more than 5 times, program quiting");
                    throw new RuntimeException("Failed more than 5 times, program quiting");
                }
                com.ibm.eannounce.wwprt.Log.i("Exception :" + e.getMessage() + " trying to pull data again, times:" + times);
            }

    }

    private void catchAndProcessMessage(FindResult result) throws JAXBException {
        //JSONObject object= JsonUtils.getJsonObject(result.getDocs());
        System.out.println("catchAndProcessMessage called");
        //String id = getID();
        String id = "5052445354472E50524943455F6F66666572696E676E616D655F533030303030475F76617269616E746E616D655F41414142303033315F76617269616E74747970655F5641525F7072696365747970655F4D4C435F636F756E7472795F41525F656E64646174655F323032332D30322D31335F6F6E73686F72655F595F63757272656E63795F555344";
        String data = com.ibm.eannounce.wwprt.JsonUtils.Json2Xml(result.getDocs(), id);
        com.ibm.eannounce.wwprt.Log.d("id:" + id);
        com.ibm.eannounce.wwprt.Log.d("Json:" + result.getDocs());
        com.ibm.eannounce.wwprt.Log.d("After converting data to XML " + data);
        writeDataToXML(id, data);
        com.ibm.eannounce.wwprt.Context context = com.ibm.eannounce.wwprt.Context.get();
        boolean ackSent = false;
        String pricesId = "Invalid ID";
        try {
            /* Catcher
             * 1) Read the message
             * 2) Parse and find the message ID
             * 3) Store the XML in WWPRTXML table
             * 4) Send acknowledgment MQ message
             */
            long catcherTime = System.currentTimeMillis();

            //1) Read the message
            com.ibm.eannounce.wwprt.Log.v("Reading Message data...");
            //2) Parse and find the message ID
            com.ibm.eannounce.wwprt.Log.v("Parsing XML...");
            pricesId = parseMessageId(data);
            if (pricesId == null || pricesId.length() == 0) {
                pricesId = "Invalid ID";
                throw new com.ibm.eannounce.wwprt.catcher.CatcherException("Price list don't have a valid 'id' attribute");
            }

            //3) Store the XML in WWPRTXML table
			/*Log.v("Saving XML data to WWPRTXML table...");
			ConnectionFactory connectionFactory = context.getConnectionFactory();
			try {
				Log.v("Connecting:"+connection+","+connectionFactory);
				Log.v("pricesId:"+pricesId);
				if(connection==null){
					initConnection();
				}
				transaction = new Transaction(connection);
				transaction.executeUpdate(new InsertPricesXML(pricesId, data));
				connectionFactory.commit(connection);
			} catch (SQLException e) {
				String errorMsg = "Unable to store the Prices XML in the DB (WWPRTXML table) - Prices ID = "
						+ pricesId + " - " + e.getMessage();
				if (connection != null) {
					Log.e( errorMsg);
					try {
						connectionFactory.rollback(connection);
						connectionFactory.close(connection);
					} catch (SQLException e2) {
						Log.e( "Unable to close the connection: "
								+ e2.getMessage());
					}
				}}*/

            // Save the XML to a local file to use later if needed
            /*File xmlFile = new File("price.xml");

            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(xmlFile));
                com.ibm.eannounce.wwprt.Log.v("Saving data locally");
                out.write(data);
                out.close();
            } catch (IOException e2) {
                com.ibm.eannounce.wwprt.Log.e("Unable save failed xml file " + xmlFile, e2);
            }*/


            //4) Send successful acknowledgment MQ message
            context.sendInitialResponse(true, pricesId, null);
            ackSent = true;

            if (com.ibm.eannounce.wwprt.Log.isLevel(com.ibm.eannounce.wwprt.Log.VERBOSE)) {
                Runtime runtime = Runtime.getRuntime();
                long t = runtime.totalMemory();
                long f = runtime.freeMemory();
                long u = t - f;
                com.ibm.eannounce.wwprt.Log.v("Catcher read, parsed and stored the XML data in "
                        + (System.currentTimeMillis() - catcherTime) + "ms - Mem(t) " + t + ", Mem(f) "
                        + f + " Mem(u) " + u);
            } else {
                com.ibm.eannounce.wwprt.Log.i("Catcher read, parsed and stored the XML data in "
                        + (System.currentTimeMillis() - catcherTime) + "ms");

            }
        } catch (com.ibm.eannounce.wwprt.catcher.CatcherException e) {
            com.ibm.eannounce.wwprt.Log.e(e.getMessage(), e);
            NotificationBuilder nb = new NotificationBuilder("WWPRT Inbound - Catcher problem ("
                    + pricesId + ")");

            try {
                close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    private void writeDataToXML(String id, String data) {
        try {
            Element eElement = null;
            File xmlFile = new File("price.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            eElement = doc.getDocumentElement();
            String idFromXML = eElement.getAttribute("id");
            String conString = id.substring(0, 7);
            if (idFromXML.equalsIgnoreCase(id) || idFromXML.isEmpty()) {
                BufferedWriter out = new BufferedWriter(new FileWriter(xmlFile));
                com.ibm.eannounce.wwprt.Log.v("Saving data locally");
                out.write(data);
                out.close();
            } /*else {
                com.ibm.eannounce.wwprt.Log.v("In else block");
                // Directory where XML files will be saved
                String outputDirectory = "C:\\Users\\003F5Q744\\Documents\\EACM\\cloudant\\EACM-main-dev\\wwprt-inbound\\wwprt-inbound";
                // Create the output directory if it doesn't exist
                File directory = new File(outputDirectory);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                String fileName = "output_" + System.currentTimeMillis() + ".xml";
                StreamResult result = new StreamResult(new File(outputDirectory + fileName));
                transformer.transform(source, result);
                System.out.println("XML file '" + fileName + "' created for value: " + id);

                *//*BufferedWriter out = new BufferedWriter(new FileWriter(("id/.xml")));
                com.ibm.eannounce.wwprt.Log.v("Saving data locally for new id");
                out.write(data);
                out.close();*//*

            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTimestampInTimetable() {
        String strInsertTimestampStmt = getQueryStatement();
        try {
            Statement stmtInsertTimeStamp = connection.createStatement();
            stmtInsertTimeStamp.execute(strInsertTimestampStmt);
            stmtInsertTimeStamp.close();

        } catch (SQLException e) {
            Log.e("setTimestampInTimetable:ERROR:Insert into timetable:" + e.getMessage());
            System.exit(1);
        }
    }

    private void getTimestampInTimetable() {
        String strInsertTimestampStmt = getQueryStatement();
        try {
            Statement stmtInsertTimeStamp = connection.createStatement();
            stmtInsertTimeStamp.execute(strInsertTimestampStmt);
            stmtInsertTimeStamp.close();

        } catch (SQLException e) {
            Log.e("setTimestampInTimetable:ERROR:Insert into timetable:" + e.getMessage());
            System.exit(1);
        }
    }

    public String getQueryStatement() {
        return null;
    }


    private String parseMessageId(String data) throws CatcherException {
        IdFinderHandler idFinderHandler = new IdFinderHandler();
        try {
            InputSource is = new InputSource(new StringReader(data));
            is.setEncoding("UTF-8");
            parser.parse(is, idFinderHandler);
        } catch (StopSAXException e) {
            //This exception will be thrown when the ID is found
            //The parser can stop reading
        } catch (Exception e) {
            throw new CatcherException("Unable to parse the message: " + e.getMessage(), e);
        }
        return idFinderHandler.id;
    }

    class IdFinderHandler extends DefaultHandler {

        String id;

        @Override
        public void startElement(String uri, String localName, String name, Attributes attributes)
                throws SAXException {
            if (name.equalsIgnoreCase("wwprttxn")) {
                id = attributes.getValue("id");
                throw new StopSAXException();
            }
        }
    }

    class StopSAXException extends SAXException {

        private static final long serialVersionUID = 1L;

    }

    public String getID() {
        if (id == 0) {
            MaxPricesIDQuery maxPricesIDQuery = new MaxPricesIDQuery();
            try {
                transaction.executeQuery(maxPricesIDQuery);
                id = maxPricesIDQuery.id;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Log.i("Max id : " + maxPricesIDQuery.id);
        }
        id++;
        return id + "";
    }

}
