package COM.ibm.opicmpdh.objects;

public class Validator {
	
	
	public static boolean validateInappropriateEncoding(String value) {
		if (value!=null&&value.toLowerCase().contains("<script>")) {
			return false;
		}
		
		return true;
	}

}
