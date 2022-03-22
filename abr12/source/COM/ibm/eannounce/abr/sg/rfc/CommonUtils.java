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
	
	
	public static void main(String[] args) {
		
		
		
		String model="mod12345";
		
		
		
		System.out.println("first 3=" + getFirstSubString(model,3));
		System.out.println("last 3=" + getLastSubString(model,3));
		System.out.println("last 3=" + getLastSubString("12",3));
		int str = 1;
		System.out.println("format zer0 3=" +frontCompWithZore(str,3));
		String str2 ="002";
		int istr2 = Integer.parseInt(str2);
		System.out.println("istr2=" +istr2);
		
		System.out.println("null case" + getNoLetter(null));
		System.out.println("1234 case" + getNoLetter("1234"));
		System.out.println("1234 case" + getNoLetter("12a4"));
		System.out.println("1234 case" + getNoLetter("001Z"));	
		
		String input ="C12 (common";
		System.out.println("substrtochar =#" + getSubstrToChar(input,"?")+"#");
		
		
	}

	

}
