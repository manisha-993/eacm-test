package COM.ibm.eannounce.abr.sg.rfc;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import COM.ibm.eannounce.abr.sg.rfc.SVCMOD;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class RdhTssMatCharTest
{
    private static SVCMOD svcmod = null;

    @BeforeClass
    public static void setUpBeforeClass()
    {
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 		
		factory.setNamespaceAware(false);
	    DocumentBuilder builder;
	    Document doc;
		try {
			builder = factory.newDocumentBuilder();

			File file = new File("C:\\Users\\QianYuGao\\Desktop\\695525D.xml");
			String fileStr = file2String(file, "UTF-8").replace("&", "&amp;");
			string2File(fileStr, "C:\\Users\\QianYuGao\\Desktop\\695525D-toUse.xml");
			File fileToUse = new File("C:\\Users\\QianYuGao\\Desktop\\695525D-toUse.xml");
			doc = builder.parse(fileToUse);
			svcmod = getObjFromDoc(doc, SVCMOD.class);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
    }

    @Test
    public void testRdhTssMatChar()
    {
        System.out.println("------------- Test RdhTssMatChar start -------------");
	    
	    RdhTssMatChar matChar = new RdhTssMatChar(svcmod);

	    try {
			String json = matChar.generateJson();
			String formatJson = formatJson(json);
	    	string2File(formatJson, "C:\\Users\\QianYuGao\\Desktop\\695525D-TssMatChar.json");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	    
        try
        {
        	matChar.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = matChar.createLogEntry();
        System.out.println(logEntry);
        assertEquals(matChar.getRfcrc(), 0);
        System.out.println("------------- Test RdhTssMatChar end -------------");
    }
    
    @SuppressWarnings("unchecked")
	private static <T> T getObjFromDoc(Document doc, Class<SVCMOD> class1) throws JAXBException 
    {

		try {
			JAXBContext context = JAXBContext.newInstance(SVCMOD.class);
			Unmarshaller unMarshaller = context.createUnmarshaller();
			return (T) unMarshaller.unmarshal(doc);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			throw e;
		}				
	}
    
    public static boolean string2File(String res, String filePath) { 
        boolean flag = true; 
        BufferedReader bufferedReader = null; 
        BufferedWriter bufferedWriter = null; 
        try { 
            File distFile = new File(filePath); 
            if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs(); 
            bufferedReader = new BufferedReader(new StringReader(res)); 
            bufferedWriter = new BufferedWriter(new FileWriter(distFile)); 
            char buf[] = new char[1024];         
            int len; 
            while ((len = bufferedReader.read(buf)) != -1) { 
                bufferedWriter.write(buf, 0, len); 
            } 
            bufferedWriter.flush(); 
            bufferedReader.close(); 
            bufferedWriter.close(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
            flag = false; 
            return flag; 
        } finally { 
            if (bufferedReader != null) { 
                try { 
                    bufferedReader.close(); 
                } catch (IOException e) { 
                    e.printStackTrace(); 
                } 
            } 
        } 
        return flag; 
    }

    
    public static String file2String(File file, String encoding) { 
        InputStreamReader reader = null; 
        StringWriter writer = new StringWriter(); 
        try { 
            if (encoding == null || "".equals(encoding.trim())) { 
                reader = new InputStreamReader(new FileInputStream(file), encoding); 
            } else { 
                reader = new InputStreamReader(new FileInputStream(file)); 
            } 
            char[] buffer = new char[1024]; 
            int n = 0; 
            while (-1 != (n = reader.read(buffer))) { 
                writer.write(buffer, 0, n); 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
            return null; 
        } finally { 
            if (reader != null) 
                try { 
                    reader.close(); 
                } catch (IOException e) { 
                    e.printStackTrace(); 
                } 
        } 
        if (writer != null) 
            return writer.toString(); 
        else return null; 
    }
    
    public String formatJson(String json)
    {
        StringBuffer result = new StringBuffer();

        int length = json.length();
        int number = 0;
        char key = 0;

        for (int i = 0; i < length; i++)
        {
            key = json.charAt(i);

            if((key == '[') || (key == '{') )
            {
                if((i - 1 > 0) && (json.charAt(i - 1) == ':'))
                {
                    result.append('\n');
                    result.append(indent(number));
                }

                result.append(key);

                result.append('\n');

                number++;
                result.append(indent(number));

                continue;
            }

            if((key == ']') || (key == '}') )
            {
                result.append('\n');

                number--;
                result.append(indent(number));

                result.append(key);

                if(((i + 1) < length) && (json.charAt(i + 1) != ','))
                {
                    result.append('\n');
                }

                continue;
            }

            if((key == ','))
            {
                result.append(key);
                result.append('\n');
                result.append(indent(number));
                continue;
            }

            result.append(key);
        }

        return result.toString();
    }
    
    private String indent(int number)
    {
    	String SPACE = "    ";
    	
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < number; i++)
        {
            result.append(SPACE);
        }
        return result.toString();
    }
    
}
