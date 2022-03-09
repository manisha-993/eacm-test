package COM.ibm.eannounce.abr.sg.rfc;

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
		
		
	}

}
