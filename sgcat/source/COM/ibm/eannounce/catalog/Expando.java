package COM.ibm.eannounce.catalog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

class XmlGroup {
  public String m_strName = null;
  public Vector m_vctTags = null;

  public XmlGroup(String _strName) {
    m_strName = _strName;
    m_vctTags = new Vector();
  }
}

class SourceLine {
  public static final int LINE_TYPE_NORMAL = 1;
  public static final int LINE_TYPE_EXPANDO = 2;
  public static final int LINE_TYPE_VAR = 3;
  public static final int LINE_TYPE_GEN_VARS = 4;
  public static final int LINE_TYPE_GEN_METHODS = 5;
  public static final int LINE_TYPE_XML_GROUP_DEF = 6;
  public String m_strLine = null;
  public int m_iType = 0;

  public SourceLine(String _strLine, int iType) {
    m_strLine = _strLine;
    m_iType = iType;
  }
}

class VariableDef {
  public String m_strName = null;
  public String m_strDataType = null;
  public String m_strXmlTag = null;

  public VariableDef(String _strName, String _strDataType, String _strXmlTag) {
    m_strName = _strName;
    m_strDataType = _strDataType;
    m_strXmlTag = _strXmlTag;
  }
  public String getName() {
    return m_strName;
  }
  public String getDataType() {
    return m_strDataType;
  }
  public String getXmlTag() {
    return m_strXmlTag;
  }
  public String toString() {
    return m_strName + " " + m_strDataType + " " + m_strXmlTag;
  }
}

public final class Expando {
  private static final String TOKEN_EXPANDO = "//expando";
  private static final int FOUND_EXPANDO = 1;
  private static final String TOKEN_VAR = "//@var";
  private static final int FOUND_VAR = 2;
  private static final String TOKEN_GEN_VARS = "//@genvars";
  private static final int FOUND_GEN_VARS = 3;
  private static final String TOKEN_GEN_METHODS = "//@genmethods";
  private static final int FOUND_GEN_METHODS = 4;
  private static final String TOKEN_XML_GROUP = "//@xmlgroup";
  private static final int FOUND_XML_GROUP = 5;
  private static HashMap c_hashSearchToken = new HashMap(10);
  private static HashMap c_hashLines = new HashMap(100000);

  public static void main(String[] _args) throws Exception {

    if (_args.length != 2) {
      System.out.println("\nWrong number of parameters ...\n");
      usage();
      System.exit(1);
    }

    String strMode = _args[0];
    File f = new File(_args[1]);

    if (!okMode(strMode)) {
      System.out.println("\nBad mode ...\n");
      usage();
      System.exit(1);
    }

    if (!okFile(f)) {
      System.out.println("\nBad file ...\n");
      usage();
      System.exit(1);
    }

    List lFiles = null;

    System.out.println(strMode);

    if (strMode.equalsIgnoreCase("-f")) {
      lFiles = getSingleFile(f);
    }

    if (strMode.equalsIgnoreCase("-d")) {
      lFiles = getFromDirectory(f);
    }

    if (strMode.equalsIgnoreCase("-c")) {
      lFiles = getFromConfigFile(f);
    }

    c_hashSearchToken.put(TOKEN_EXPANDO, new Integer(FOUND_EXPANDO));
    c_hashSearchToken.put(TOKEN_VAR, new Integer(FOUND_VAR));
    c_hashSearchToken.put(TOKEN_GEN_VARS, new Integer(FOUND_GEN_VARS));
    c_hashSearchToken.put(TOKEN_GEN_METHODS, new Integer(FOUND_GEN_METHODS));
    c_hashSearchToken.put(TOKEN_XML_GROUP, new Integer(FOUND_XML_GROUP));
    processFiles(lFiles);
  }
  public static void generateVariableDefs(File _f, BufferedWriter _bw) throws Exception {
    VariableDef v = null;
    int iVar = 1;

    while ((v = (VariableDef) c_hashLines.get(_f + ":var:" + iVar++)) != null) {
      output(_bw, "private " + v.getDataType() + " " + v.getName() + ";");
    }

    output(_bw, "");
  }
  public static void generateAccessorMethods(File _f, BufferedWriter _bw) throws Exception {
    VariableDef v = null;
    int iVar = 1;

    while ((v = (VariableDef) c_hashLines.get(_f + ":var:" + iVar++)) != null) {
      output(_bw, "public " + v.getDataType() + " get" + v.getName() + "() {");
      output(_bw, "   return " + v.getName() + ";");
      output(_bw, "}");
      output(_bw, "");
    }
  }
  public static void generateMutatorMethods(File _f, BufferedWriter _bw) throws Exception {
    VariableDef v = null;
    int iVar = 1;

    while ((v = (VariableDef) c_hashLines.get(_f + ":var:" + iVar++)) != null) {
      output(_bw, "public void set" + v.getName() + "(" + v.getDataType() + " _o) {");
      output(_bw, "   " + v.getName() + " = _o;");
      output(_bw, "}");
      output(_bw, "");
    }
  }
  public static void generateXmlOutputMethod(File _f, BufferedWriter _bw) throws Exception {

    output(_bw, "public void outputXML(XMLWriter _xml, String _strEntityTag) {");
    output(_bw, "   _xml.writeEntity(_strEntityTag);");
    output(_bw, "");

    int iGroup = 1;
    XmlGroup g = null;

    while ((g = (XmlGroup) c_hashLines.get(_f + ":group:" + iGroup++)) != null) {
      output(_bw, "   // XMLgroup " + g.m_strName + " " + g.m_vctTags);
      output(_bw, "   if (_strEntityTag.equalsIgnoreCase(\"" + g.m_strName + "\")) {");

      Vector t = g.m_vctTags;

      for (int i = 0; i < t.size(); ++i) {
        String strTag = (String) t.get(i);
        VariableDef x = (VariableDef) c_hashLines.get(_f + ":tag:" + strTag);

        if (x != null) {
          output(_bw, "      _xml.writeEntity(\"" + x.getXmlTag() + "\");");
          output(_bw, "      _xml.write(" + x.getName() + ");");
          output(_bw, "      _xml.endEntity();");
        }
      }

      output(_bw, "   }");
      output(_bw, "");
    }

    output(_bw, "   _xml.endEntity();");
    output(_bw, "}");
    output(_bw, "");
  }
  public static void saveBackup(File _f) {

    int iLine = 1;
    SourceLine lFile;

    try {
    String filePath = _f + ".bak";
    boolean validate = pathTranelVaild(filePath);
    if(validate) {
     BufferedWriter bwOutput = new BufferedWriter(new FileWriter(_f + ".bak"));

      while ((lFile = (SourceLine) c_hashLines.get(_f + ":" + iLine)) != null) {
        ++iLine;

        output(bwOutput, lFile.m_strLine);
      }

      bwOutput.close();
    }
    } catch (Exception x) {
      System.out.println("error saving backup " + x);
    }
  }
  
  public static void expandFile(File _f) {

    try {
      // save a backup copy
      saveBackup(_f);

      // processs the file
      BufferedWriter bwOutput = new BufferedWriter(new FileWriter(_f));
      int iLine = 1;
      SourceLine lFile;

      while ((lFile = (SourceLine) c_hashLines.get(_f + ":" + iLine)) != null) {
        ++iLine;

        output(bwOutput, lFile.m_strLine);

        switch (lFile.m_iType) {
          case SourceLine.LINE_TYPE_NORMAL :
          case SourceLine.LINE_TYPE_EXPANDO :
          case SourceLine.LINE_TYPE_VAR :
            break;

          case SourceLine.LINE_TYPE_GEN_VARS :
            generateVariableDefs(_f, bwOutput);
            break;

          case SourceLine.LINE_TYPE_GEN_METHODS :
            generateAccessorMethods(_f, bwOutput);
            generateMutatorMethods(_f, bwOutput);
            generateXmlOutputMethod(_f, bwOutput);
            break;
        }
      }

      bwOutput.close();
    } catch (Exception x) {
      System.out.println("error processing file " + x);
    }
  }
  public static void processFile(File _f) throws IOException {

    HashMap hashLines = new HashMap(100000);
    boolean bFoundExpando = false;
    boolean bInExpandoBlock = false;
    BufferedReader bfr = new BufferedReader(new FileReader(_f));
    String strLine;
    SourceLine lFile;
    int iLine = 1;
    int iVar = 1;
    int iGroup = 1;

    while ((strLine = readTemplate(bfr)) != null) {
      StringTokenizer stTokens = new StringTokenizer(strLine);
      int iTokenCount = 0;
      String strToken = null;

      if (stTokens.hasMoreTokens()) {
        strToken = stTokens.nextToken();
      }

      int iToken = 0;

      try {
        iToken = ((Integer) c_hashSearchToken.get(strToken)).intValue();
      } catch (Exception x) {
        iToken = 0;
      }

      switch (iToken) {
        case FOUND_EXPANDO :
          bFoundExpando = true;
          bInExpandoBlock = !(bInExpandoBlock);
          lFile = new SourceLine(strLine, SourceLine.LINE_TYPE_EXPANDO);

          hashLines.put(_f + ":" + iLine++, lFile);
          break;

        case FOUND_VAR :
          if (bInExpandoBlock) {
            lFile = new SourceLine(strLine, SourceLine.LINE_TYPE_VAR);

            hashLines.put(_f + ":" + iLine++, lFile);

            String strDataType = stTokens.nextToken();
            String strVariable = stTokens.nextToken();
            String strXML = stTokens.nextToken();
            VariableDef v = new VariableDef(strVariable, strDataType, strXML);

            // save by #
            hashLines.put(_f + ":var:" + iVar++, v);
            // save by XmlTag
            hashLines.put(_f + ":tag:" + strXML, v);
          }
          break;

        case FOUND_GEN_VARS :
          if (bInExpandoBlock) {
            lFile = new SourceLine(strLine, SourceLine.LINE_TYPE_GEN_VARS);

            hashLines.put(_f + ":" + iLine++, lFile);
          }
          break;

        case FOUND_GEN_METHODS :
          if (bInExpandoBlock) {
            lFile = new SourceLine(strLine, SourceLine.LINE_TYPE_GEN_METHODS);

            hashLines.put(_f + ":" + iLine++, lFile);
          }
          break;

        case FOUND_XML_GROUP :
          if (bInExpandoBlock) {
            lFile = new SourceLine(strLine, SourceLine.LINE_TYPE_NORMAL);

            hashLines.put(_f + ":" + iLine++, lFile);
          }

          String strGroupName = stTokens.nextToken();
          XmlGroup g = new XmlGroup(strGroupName);

          while (stTokens.hasMoreTokens()) {
            strToken = stTokens.nextToken();

            g.m_vctTags.add(strToken);
          }

          // save XmlGroup by #
          hashLines.put(_f + ":group:" + iGroup++, g);
          break;

        default :
          if (!bInExpandoBlock) {
            lFile = new SourceLine(strLine, SourceLine.LINE_TYPE_NORMAL);

            hashLines.put(_f + ":" + iLine++, lFile);
          }
          break;
      }
    }

    if (bFoundExpando) {
      c_hashLines.putAll(hashLines);
      expandFile(_f);
    }
  }
  public static void processFiles(List _lFiles) throws IOException {
    Iterator itFiles = _lFiles.iterator();

    while (itFiles.hasNext()) {
      processFile((File) itFiles.next());
    }
  }
  public static void usage() {
    System.out.println("Usage: -c <config file> [process the files specified in config file]");
    System.out.println("       -d <directory>   [process ALL files in a directory recursively]");
    System.out.println("       -f <file>        [process a single file]");
  }
  public static boolean okMode(String _strMode) {
    boolean bReturn = false;

    if (_strMode.equalsIgnoreCase("-c") || _strMode.equalsIgnoreCase("-f") || _strMode.equalsIgnoreCase("-d")) {
      bReturn = true;
    }

    return bReturn;
  }
  public static boolean okFile(File _f) {
    boolean bReturn = false;

    if (_f.exists()) {
      bReturn = true;
    }

    return bReturn;
  }
  public static List getSingleFile(File _f) {
    List lReturn = new ArrayList();

    lReturn.add(_f);

    return lReturn;
  }
  public static List getFromDirectory(File _fDir) {

    List lReturn = new ArrayList();
    File[] afFilesAndDirs = _fDir.listFiles();
    List lFilesDirs = Arrays.asList(afFilesAndDirs);
    Iterator itFiles = lFilesDirs.iterator();
    File f = null;

    while (itFiles.hasNext()) {
      f = (File) itFiles.next();

      // if not a directory, save it
      if (!f.isDirectory()) {
        lReturn.add(f);
      }

      // if it is a directory, then recurse
      if (!f.isFile()) {
        List lDeeper = getFromDirectory(f);

        lReturn.addAll(lDeeper);
      }
    }

    // do we really care?
    Collections.sort(lReturn);

    return lReturn;
  }
  public static List getFromConfigFile(File _f) throws IOException {
	  BufferedReader bfr = null;
	 boolean valid =  pathTranelVaild(_f.toString());
     if(valid)
    bfr= new BufferedReader(new FileReader(_f));
    String strLine = null;
    List lReturn = new ArrayList();

    while ((strLine = readTemplate(bfr)) != null) {
    	 valid = pathTranelVaild(strLine);
    	if(valid) {
      File f = new File(strLine);

      // if not a directory, save it
      // if it is a directory, do not recurse
      if (!f.isDirectory()) {
        lReturn.add(f);
      }
    }
    	}

    return lReturn;
  }
  public static boolean pathTranelVaild(String path) {
	  if(path==null||path.contains(".."))
		  return false;
	  return true;
  }
  public static String readTemplate(BufferedReader _brTemplate) throws IOException {
    return _brTemplate.readLine();
  }
  public static boolean okDirectory(File _fDir) {

    boolean bReturn = true;

    if (_fDir == null) {
      // Directory should not be null
      bReturn = false;
    }

    if (!_fDir.exists()) {
      // Directory does not exist
      bReturn = false;
    }

    if (!_fDir.isDirectory()) {
      // Is not a directory
      bReturn = false;
    }

    if (!_fDir.canRead()) {
      // Directory cannot be read
      bReturn = false;
    }

    return bReturn;
  }
  public static void output(BufferedWriter _bw, String _strLine) throws Exception {
    _bw.write(_strLine);
    _bw.newLine();
  }
}
/*
* $Log: Expando.java,v $
* Revision 1.2  2011/05/05 11:21:34  wendy
* src from IBMCHINA
*
* Revision 1.1.1.1  2007/06/05 02:09:16  jingb
* no message
*
* Revision 1.1.1.1  2006/03/30 17:36:28  gregg
* Moving catalog module from middleware to
* its own module.
*
* Revision 1.4  2006/01/26 19:39:09  gregg
* <No Comment Entered>
*
* Revision 1.3  2005/06/23 19:09:02  roger
* XmlGroup support
*
* Revision 1.2  2005/06/21 20:57:40  roger
* XMLGroup stuff
*
* Revision 1.1  2005/06/14 19:46:04  roger
* For expando function
*
*/
