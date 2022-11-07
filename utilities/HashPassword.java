package com.trangialam.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class HashPassword {

	public static String generatedPasswordHash(String passwordToHash) {

		String generatedPassword = null;
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(passwordToHash.getBytes());
			byte[] bytes = md.digest();

			// This bytes[] has bytes in decimal format. Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return generatedPassword;
	}

	

}
