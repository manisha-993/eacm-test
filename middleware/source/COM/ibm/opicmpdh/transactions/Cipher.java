// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2009,2011  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.opicmpdh.transactions;

/**
 * This class provides very simple encoding/decoding.  Used to encode and decode passwords in
 * properties files.  Also provides encryption of userid and password for RMI login usage using JCE.
 * 
 * Creation date: 11/05/2008
 * @author: Mike Slocum/Sterling Forest/IBM
 * $Log: Cipher.java,v $
 * Revision 1.4  2011/09/06 21:19:22  wendy
 * Restore encrypted login
 *
 * Revision 1.3  2009/03/12 19:58:41  wendy
 * back out JCE (encryption) for now - requires jre1.4
 *
 * Revision 1.2  2009/03/11 18:26:41  wendy
 * Added encrypted login
 *
 * Revision 1.1  2009/02/04 21:13:07  wendy
 * Add to middleware
 *
 */

import java.util.Arrays;
import java.util.HashMap;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

import COM.ibm.opicmpdh.middleware.MiddlewareException;

public class Cipher
{
	private static final String KEY_ALGO = "AES";
	private static final String CIPHER_ALGO = "AES/CBC/PKCS5Padding";
	private static final HashMap encryptionHashMap;
	static{
		encryptionHashMap = new HashMap();	// create a new hashmap and
		encryptionHashMap.put("a", "Z");	// store the character
		encryptionHashMap.put("b", "Y");	// translations for encryption
		encryptionHashMap.put("c", "X");
		encryptionHashMap.put("d", "W");
		encryptionHashMap.put("e", "V");
		encryptionHashMap.put("f", "U");
		encryptionHashMap.put("g", "T");
		encryptionHashMap.put("h", "S");
		encryptionHashMap.put("i", "R");
		encryptionHashMap.put("j", "Q");
		encryptionHashMap.put("k", "P");
		encryptionHashMap.put("l", "O");
		encryptionHashMap.put("m", "N");
		encryptionHashMap.put("n", "M");
		encryptionHashMap.put("o", "L");
		encryptionHashMap.put("p", "K");
		encryptionHashMap.put("q", "J");
		encryptionHashMap.put("r", "I");
		encryptionHashMap.put("s", "H");
		encryptionHashMap.put("t", "G");
		encryptionHashMap.put("u", "F");
		encryptionHashMap.put("v", "E");
		encryptionHashMap.put("w", "D");
		encryptionHashMap.put("x", "C");
		encryptionHashMap.put("y", "B");
		encryptionHashMap.put("z", "A");
		encryptionHashMap.put("A", "z");
		encryptionHashMap.put("B", "y");
		encryptionHashMap.put("C", "x");
		encryptionHashMap.put("D", "w");
		encryptionHashMap.put("E", "v");
		encryptionHashMap.put("F", "u");
		encryptionHashMap.put("G", "t");
		encryptionHashMap.put("H", "s");
		encryptionHashMap.put("I", "r");
		encryptionHashMap.put("J", "q");
		encryptionHashMap.put("K", "p");
		encryptionHashMap.put("L", "o");
		encryptionHashMap.put("M", "n");
		encryptionHashMap.put("N", "m");
		encryptionHashMap.put("O", "l");
		encryptionHashMap.put("P", "k");
		encryptionHashMap.put("Q", "j");
		encryptionHashMap.put("R", "i");
		encryptionHashMap.put("S", "h");
		encryptionHashMap.put("T", "g");
		encryptionHashMap.put("U", "f");
		encryptionHashMap.put("V", "e");
		encryptionHashMap.put("W", "d");
		encryptionHashMap.put("X", "c");
		encryptionHashMap.put("Y", "b");
		encryptionHashMap.put("Z", "a");
		encryptionHashMap.put("1", "0");
		encryptionHashMap.put("2", "9");
		encryptionHashMap.put("3", "8");
		encryptionHashMap.put("4", "7");
		encryptionHashMap.put("5", "6");
		encryptionHashMap.put("6", "5");
		encryptionHashMap.put("7", "4");
		encryptionHashMap.put("8", "3");
		encryptionHashMap.put("9", "2");
		encryptionHashMap.put("0", "1");
	}
	/**
	 * Cipher constructor.
	 */
	public Cipher()
	{
		super();
	}
	/**
	 * This method provides very simple encoding/decoding.  Used to encode and decode passwords in
	 * properties files.
	 */
	public static String codec(String inWord)
	{
		StringBuffer outWord = new StringBuffer();
		for (int i=0;i<inWord.length();i++)
		{
			if (encryptionHashMap.containsKey(inWord.substring(i,i+1)))
				outWord.append(encryptionHashMap.get(inWord.substring(i,i+1)));
			else
				outWord.append(inWord.substring(i,i+1));
		}

		return outWord.toString();
	}

	/***********************************
	 *
	 * Using Encryption
	 *
	 * Generating a Key
	 * To create a Data Encryption Standard (DES) key, we have to instantiate a KeyGenerator for DES.
	 * We do not specify a provider, because we do not care about a particular DES key generation implementation.
	 * Since we do not initialize the KeyGenerator, a system-provided source of randomness will be used to create
	 * the DES key:
	 *
	 *     KeyGenerator keygen = KeyGenerator.getInstance("DES");
	 *     SecretKey desKey = keygen.generateKey();
	 *
	 * After the key has been generated, the same KeyGenerator object can be re-used to create further keys.
	 *
	 * Creating a Cipher
	 * The next step is to create a Cipher instance. To do this, we use one of the getInstance factory methods
	 * of the Cipher class. We must specify the name of the requested transformation, which includes the following
	 * components, separated by slashes (/):
	 *
	 * the algorithm name
	 * the mode (optional)
	 * the padding scheme (optional)
	 * In this example, we create a DES (Data Encryption Standard) cipher in Electronic Codebook mode, with
	 * PKCS #5-style padding. We do not specify a provider, because we do not care about a particular implementation
	 * of the requested transformation.
	 *
	 * The standard algorithm name for DES is "DES", the standard name for the Electronic Codebook mode is "ECB",
	 * and the standard name for PKCS #5-style padding is "PKCS5Padding":
	 *
	 ***** encrypt/decrypt must share same key or get
	 * javax.crypto.BadPaddingException: Given final block not properly padded
	 *         at com.ibm.crypto.provider.DESCipher.engineDoFinal(Unknown Source)
	 *         at com.ibm.crypto.provider.DESCipher.engineDoFinal(Unknown Source)
	 *         at javax.crypto.Cipher.doFinal(Unknown Source)
	 *         at COM.ibm.opicmpdh.transactions.Cipher.decrypt(Cipher.java:203)
	 *         at COM.ibm.opicmpdh.transactions.Cipher.main(Cipher.java:225)
	 */

	/***********************
	 * Encrypt the uid and pw 
	 * @param uid
	 * @param pw
	 * @return byte[][] [0]=encrypted value, [1] the encoding for the key - required to decode
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalStateException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws IOException
	 */
	public static byte[][] encrypt(String uid, char[] pw) throws
		NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
		IllegalStateException, IllegalBlockSizeException, BadPaddingException, IOException
	{
		javax.crypto.KeyGenerator kg = javax.crypto.KeyGenerator.getInstance(KEY_ALGO);
		javax.crypto.SecretKey key = kg.generateKey();
		// Create the cipher
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(CIPHER_ALGO);

		String str = uid+(new String(pw));
		byte[] data = str.getBytes("UTF8");

		// Initialize the cipher for encryption
		cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
		byte[] result = cipher.doFinal(data);
		byte[][] byteArray = {result,key.getEncoded()};
		return byteArray;
	}

	/********************************
	 * Decrypt the byte array using the keybytes
	 * @param result
	 * @param keyBytes
	 * @return UTF8 string
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalStateException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws IOException
	 */
	public static String decrypt(byte[] result, byte[]keyBytes) throws
		NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
		IllegalStateException, IllegalBlockSizeException, BadPaddingException, IOException
	{
		// convert back to a SecretKey - must use same key that was used for encryption
		javax.crypto.SecretKey key = new javax.crypto.spec.SecretKeySpec(keyBytes, KEY_ALGO);
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(CIPHER_ALGO);
		byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		IvParameterSpec ivspec = new IvParameterSpec(iv);
		cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key,iv);
		byte[] original = cipher.doFinal(result);

		// Decode using utf-8
		return new String(original, "UTF8");
	}

	/**********************
	 * Encrypt the password and userid after concatenating them
	 * store the original uid length in bytearray[0][0]
	 * @param uid
	 * @param pw
	 * @return bytearray[][]
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalStateException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws IOException
	 */
	public static byte[][] encryptUidPw(String uid, char[] pw) throws
	NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
	IllegalStateException, IllegalBlockSizeException, BadPaddingException, IOException
	{
		Integer uidlen = new Integer(uid.length());
		// do the encryption
		byte[][] ba = encrypt(uid,pw);
		//Zero out the password, for security.
		Arrays.fill(pw, '0');
		byte[] baAndlen = new byte[ba[0].length+1];
		baAndlen[0] =  uidlen.byteValue(); // hang onto uid length as first byte in returned array[0][0]
		// copy encrypted bytes into rest of ba[0]
		System.arraycopy(ba[0], 0, baAndlen, 1, ba[0].length);
		ba[0]=baAndlen;
		return ba;
	}
    /**********************
     * Encrypt the password and userid after concatenating them
     * store the original uid length in bytearray[0][0]
     * @param uid
     * @param pw
     * @return bytearray[][]
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalStateException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static byte[][] encryptUidPw(String uid, String pw) throws
    NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
    IllegalStateException, IllegalBlockSizeException, BadPaddingException, IOException
    {
    	return encryptUidPw(uid,pw.toCharArray());
    }
    
	/***********************
	 * Decrypt userid and pw, taking length of uid from ba[0][0]
	 * @param ba
	 * @return String[] [0]=uid, [1]=pw
	 * @throws MiddlewareException 
	 */
	public static String[] decryptUidPw(byte[][] ba) throws MiddlewareException 
	{
		try{
			String uipwArray[] = new String[2];
			byte[] result = ba[0]; 
			byte[] keyBytes = ba[1];
			// get length of uid from result[0]
			int uidLen = result[0];
			byte[] uipw = new byte[result.length-1]; // get length of uid from ba[0][0]

			// restore the encrypted byte array
			System.arraycopy(result, 1, uipw, 0, uipw.length);
			// do the decryption
			String uidpwStr = decrypt(uipw,keyBytes);
			// split back into userid and pw
			uipwArray[0]=uidpwStr.substring(0,uidLen);
			uipwArray[1]=uidpwStr.substring(uidLen, uidpwStr.length());

			return uipwArray;
		}catch(Exception ex){
			ex.printStackTrace();
			throw new MiddlewareException(ex.getMessage());
		}
	}
	/**
	 *=================================================================================*
	 *                           M A I N   M E T H O D                                 *
	 *=================================================================================*
	 * This class accepts a character string as an argument and returns a translated
	 * character string by calling the codec() method.
	 *
	 */
	public static void main(String[] args)
	{
		if (args.length > 0){
			System.out.println(codec(args[0]));
		}
		if (args.length > 1){
			try{
				String uid = args[0];
				String pw = args[1];

				System.err.println("orig: uid "+uid+" pw "+pw);
				byte[][] ba = encryptUidPw(uid,pw.toCharArray());
				String value[] = decryptUidPw(ba);
				System.err.println("after uid "+value[0]+" pw "+value[1]);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
