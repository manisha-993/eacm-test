package COM.ibm.eannounce.abr.sg.rfc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CommonUtils 
 * 
 * @author wangyul
 *
 */
public class CommonUtils {
	
	public static String getFirstSubString(String input, int length){		
		if(input==null) input="";
		else input = (input.length()>length) ? input.substring(0,length):input;
		return input;	
	}
	
	public static String getLastSubString(String input, int length){		
		if(input==null) input="";
		else input = (input.length()>length) ? input.substring(input.length()-length,input.length()):input;
		return input;	
	}
	
	public static String frontCompWithZore(int input, int length){		
		String newString = String.format("%0"+length+"d", input);  
		return  newString;		
	}
	
	public static String getNoLetter(String input){
		String sReturn ="";
		if(input==null) return "";		
		String regex=".*[a-zA-Z]+.*";  
        Matcher m=Pattern.compile(regex).matcher(input);
        if(m.matches()) {
        	sReturn= "";
        }else{
        	sReturn= input;
        }
        
		return sReturn;
	}
	
	public static boolean isNoLetter(String input){
		boolean isNoletter = true;
		if(input==null) return true;		
		String regex=".*[a-zA-Z]+.*";  
        Matcher m=Pattern.compile(regex).matcher(input);
        if(m.matches()) {
        	isNoletter= false;
        }else{
        	isNoletter = true;
        }
        
		return isNoletter;
	}
	
	public static boolean hasLetter(String input) {
		boolean hasletter = false;
		if(input==null) return false;
		String regex=".*[a-zA-Z]+.*";  
        Matcher m=Pattern.compile(regex).matcher(input);
        if(m.matches()) {
        	hasletter= true;
        }else{
        	hasletter = false;
        }
		return hasletter;
	}
	
	public static String getSubstrToChar(String input, String chr){		
		if(input==null) return "";
		if(chr==null) return "";
		int endIndex = input.indexOf(chr);
		if(endIndex==-1) return "";
		input = input.substring(0, endIndex).trim();		
		return input;	
	}
	
	
	public static String getPreparedSQL(String sql, Object[] params) {
        int paramNum = 0;
        if (null != params)  paramNum = params.length;
        if (1 > paramNum) return sql;
        StringBuffer returnSQL = new StringBuffer();
        String[] subSQL = sql.split("\\?");
        for (int i = 0; i < paramNum; i++) {
        	returnSQL.append(subSQL[i]).append(" '").append(params[i]).append("' ");
        }
        if (subSQL.length > params.length) {
            returnSQL.append(subSQL[subSQL.length - 1]);
        }
        return returnSQL.toString();
    }
	
	public static boolean contains(String input, String container){
		boolean isContains = false;
		if(input==null) return false;
		if(container==null) return false;
		if("".equals(container.trim())) return false;
		isContains = input.contains(container);
		return isContains;
		
	}
	
	
	
	public static void main(String[] args) {
		
		
		
//		String model="mod12345";		
//		System.out.println("isNoLetter 1=" + isNoLetter(model));
//		model="12345";		
//		System.out.println("isNoLetter 2=" + isNoLetter(model));
//		
//		System.out.println(contains("M,B",""));
//		System.out.println(contains("M,B","A"));
//		System.out.println(contains("M,B","b"));
//		System.out.println(contains("M,B","M"));
//		int input =1;
//		String subffix = frontCompWithZore(input,3);
//		System.out.println("subffix=" + subffix);
		
		Object[] params = new String[2]; 
		params[0] ="11";
		params[1] ="22";
		String sql = "SELECT DISTINCT t2.ATTRIBUTEVALUE AS MODEL, t3.ATTRIBUTEVALUE AS INVNAME FROM OPICM.flag F "
		+ " INNER JOIN opicm.flag t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='MACHTYPEATR' AND T1.ATTRIBUTEVALUE = ? AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP"
		+ " INNER JOIN opicm.text t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='MODELATR' AND T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP"
		+ " INNER JOIN opicm.text t3 ON f.ENTITYID =t3.ENTITYID AND f.ENTITYTYPE =t3.ENTITYTYPE AND t3.ATTRIBUTECODE ='INVNAME' AND t3.NLSID =1 AND T3.VALTO > CURRENT  TIMESTAMP AND T3.EFFTO > CURRENT  TIMESTAMP"
		+ " INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t1.ENTITYID AND F1.ENTITYTYPE =t1.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP"
		+ " INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP"
		+ " WHERE f.ENTITYTYPE ='MODEL' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELTIERPABRSTATUS') "
		+ " AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION=? "
		+ " WITH ur";
		String realSql = CommonUtils.getPreparedSQL(sql, params);
		System.out.println("querySql=" + realSql);
		
		
	}

	

}
