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
		
		
		
		String model="mod12345";		
		System.out.println("isNoLetter 1=" + isNoLetter(model));
		model="12345";		
		System.out.println("isNoLetter 2=" + isNoLetter(model));
		
		System.out.println(contains("M,B",""));
		System.out.println(contains("M,B","A"));
		System.out.println(contains("M,B","b"));
		System.out.println(contains("M,B","M"));
		int input =1;
		String subffix = frontCompWithZore(input,3);
		System.out.println("subffix=" + subffix);
		
		
	}

	

}
