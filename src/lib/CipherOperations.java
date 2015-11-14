/**
 * 
 */
package lib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;

/**
 * @author ismail
 *
 */
public class CipherOperations {
	public byte[] myDecrypt(SecretKey aesKey, byte[] encryptedBytes) {

		byte[] decryptedBytes = null;

		try {
			/*
			 * Decrypt cipher instance where AES/ECB/PKCS5Padding
			 */
			Cipher decryptCipher = Cipher.getInstance(" AES/ECB/PKCS5Padding");

			/*
			 * Init with AES key
			 */
			decryptCipher.init(Cipher.DECRYPT_MODE, aesKey);

			/*
			 * Create streams to read encryptedBytes and decryptCipher
			 */
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ByteArrayInputStream inStream = new ByteArrayInputStream(encryptedBytes);
			CipherInputStream cipherInputStream = new CipherInputStream(inStream, decryptCipher);

			byte[] buf = new byte[128];
			int bytesRead;
			while ((bytesRead = cipherInputStream.read(buf)) >= 0) {
				outputStream.write(buf, 0, bytesRead);
				decryptedBytes = buf.clone();
			}

			cipherInputStream.read(decryptedBytes);

			cipherInputStream.close();

			/*
			 * System.out.println("Result: " + new String(outputStream.toByteArray()));
			 */
		} catch (Exception ex) {

		}
		return decryptedBytes;

	}

	public byte[] myEncrypt(SecretKey aesKey, byte[] clearBytes) {
		byte[] encryptedBytes = null;

		try {

			Cipher encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			encryptCipher.init(Cipher.ENCRYPT_MODE, aesKey);
			System.out.printf("SecretKey %s\n", aesKey.getEncoded());
			// Encrypt
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, encryptCipher);
			// cipherOutputStream.write(s.getBytes());
			cipherOutputStream.write(clearBytes);
			cipherOutputStream.flush();
			cipherOutputStream.close();
			encryptedBytes = outputStream.toByteArray();

			/*
			 * System.out.printf("Encrypted bytes %s\n", encryptedBytes.toString());
			 */

		} catch (Exception ex) {
			ex.printStackTrace();

		}

		return encryptedBytes;
	}

}
