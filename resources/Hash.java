package resources;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Rafael Ines Guillen rinesguillen@hawk.iit.edu A20474355
 * 
 *         Final Project
 * 
 *         Hash class
 *
 */
public class Hash {

	public static String createHashes(String passwd) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(passwd.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sbHash = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sbHash.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sbHash.toString();
	}
}