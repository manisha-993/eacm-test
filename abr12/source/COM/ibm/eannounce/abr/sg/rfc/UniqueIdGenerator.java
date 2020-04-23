package COM.ibm.eannounce.abr.sg.rfc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;

public class UniqueIdGenerator {

	private char[] currentIdChars = null;
	private static final int LENGTH = 6;
	private static final String RFC_UNIQUE_FILE_PATH = "rfcabr_filepath";
	private String filePath = null;
	public String fullFilePath = null;
	private static Map<String, UniqueIdGenerator> map = new HashMap<>();
	private static Map<String, String> tempFileMapping = new HashMap<>();
	private BufferedWriter writer = null;
	public static final String TYPE_MODEL = "RFCMODELABR";
	public static final String TYPE_AUOM = "RFCAUOMTRLABR";
	public static final String TYPE_MODELCONVERT = "RFCMODELCONVERTABR";
	public static final String TYPE_PRODSTRUCT = "RFCPRODSTRUCTABR";
	public static final String TYPE_SWPRODSTUCT = "RFCSWPRODSTRUCTABR";
	public static final String TYPE_TRANSACTION = "RFCFCTRANSACTIONABR";
	static {
		tempFileMapping.put(TYPE_PRODSTRUCT, TYPE_MODEL);
	}

	private UniqueIdGenerator(String tmpFileName) {
		filePath = ABRServerProperties.getValue(RFC_UNIQUE_FILE_PATH, "./");
		fullFilePath = filePath + tmpFileName + ".temp";

	}

	public synchronized static UniqueIdGenerator getUniqueIdGenerator(String type) {
		String key = tempFileMapping.get(type) == null ? type : tempFileMapping.get(type);
		UniqueIdGenerator generator = map.get(key);
		if (generator == null) {
			generator = new UniqueIdGenerator(key);
			map.put(key, generator);
		}
		return generator;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * UniqueIdGenerator uniqueIdGenerator = new UniqueIdGenerator(""); for (int i =
		 * 0; i < 10000; i++) System.out.println(uniqueIdGenerator.getUniqueID());
		 */

		/*
		 * UniqueIdGenerator generator =
		 * UniqueIdGenerator.getUniqueIdGenerator(UniqueIdGenerator.TYPE_PRODSTRUCT);
		 * for (int i = 0; i < 100; i++) { System.out.println(generator.getUniqueID());
		 * }
		 */

		StringReader stringReade = new StringReader(null);
	}

	public synchronized String getUniqueID(RFCABRSTATUS abr) {
		if (currentIdChars == null) {
			try {
				File file = new File(fullFilePath);
				if (file.exists()) {
					BufferedReader bufferedReader = new BufferedReader(new FileReader(fullFilePath));
					String line = bufferedReader.readLine();
					/*
					 * while(line!=null) {
					 * 
					 * line = bufferedReader.readLine(); }
					 */

					if (line != null && line.length() == 6) {
						currentIdChars = line.toCharArray();
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				abr.addError(e.toString());
			}

		}
		if (currentIdChars == null) {
			currentIdChars = new String("000000").toCharArray();
		} else {
			for (int i = LENGTH - 1; i >= 0; i--) {
				if (!increase(i))
					break;
			}

		}

		try {
			writer = new BufferedWriter(new FileWriter(fullFilePath));
			writer.write(new String(currentIdChars) + "\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			abr.addError(e.toString());
		}

		String id = new String(currentIdChars);
		abr.addDebug("UniqueIdGenerator getUniqueID:" + id);
		return id;
	}

	private boolean increase(int i) {
		// TODO Auto-generated method stub
		char c = currentIdChars[i]++;

		if (c == '9') {
			currentIdChars[i] = 'A';
			return false;
		} else if (c == 'Z') {
			currentIdChars[i] = '0';
			return true;
		}
		return false;

	}

}
