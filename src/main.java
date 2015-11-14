import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author ismail
 *
 */
public class main {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException {
		// TODO Auto-generated method stub
		String cipher_file = args[0];
		String key_file = args[1];

		/*
		 * System.out.printf("Cipher file %s, Key file %s, Target file %s",
		 * cipher_file, key_file, target_file);
		 * 
		 * 
		 * 
		 * 
		 * String running_path = System.getProperty("user.dir"); File file = new
		 * File("."); for (String fileNames : file.list())
		 * System.out.println(fileNames);
		 * 
		 * byte[] test_byte = new byte[] { 72, 101, 108, 108, 111, 32, 116, 104,
		 * 101, 114, 101, 46, 32, 72, 111, 119, 32, 97, 114, 101, 32, 121, 111,
		 * 117, 63, 32, 72, 97, 118, 101, 32, 97, 32, 110, 105, 99, 101, 32,
		 * 100, 97, 121, 46, 72, 101, 108, 108, 111, 32, 116, 104, 101, 114,
		 * 101, 46, 32, 72, 111, 119, 32, 97, 114, 101, 32, 121, 111, 117, 63,
		 * 32, 72, 97, 118, 101, 32, 97, 32, 110, 105, 99, 101, 32, 100, 97,
		 * 121, 46, 72, 101, 108, 108, 111, 32, 116, 104, 101, 114, 101, 46, 32,
		 * 72, 111, 119, 32, 97, 114, 101, 32, 121, 111, 117, 63, 32, 72, 97,
		 * 118, 101, 32, 97, 32, 110, 105, 99, 101, 32, 100, 97, 121, 46 };
		 * a.writeFile("deneme", test_byte); String s =
		 * "Hello there. How are you? Have a nice day.Hello there. How are you? Have a nice day.Hello there. How are you? Have a nice day."
		 * ; byte[] deneme = s.getBytes();
		 * 
		 * byte[] key_full = new byte[] { key_part1[0], key_part1[1],
		 * key_part1[2], key_part1[3], key_part1[4], key_part1[5], key_part1[6],
		 * key_part1[7], key_part1[8], key_part1[9], key_part1[10],
		 * key_part1[11], key_part1[12], key_part1[13], key_part2[1],
		 * key_part2[0] }; key_part2 = a.increment(key_part2);
		 */

		/*
		 * Create new instance of application
		 */
		main a = new main();

		try {
			/*
			 * Create new AES key instance
			 */
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			/*
			 * Set to the size of the key instance 128 bit
			 */
			kgen.init(128);

			/*
			 * First 12 byte of key is known and stored in the file named
			 * key_file variable
			 */
			byte[] key_part1 = a.readFile(key_file);
			
			/*
			 * Read encrypted file as byte array
			 */
			byte[] encryptedBytes = a.readFile(cipher_file);
			byte[] clear_byte = null;

			/*
			 * Counters with 4 levels because there is 5 byte value as unknown
			 * for byte brute force byte value starts from -127 to 128
			 */
			byte k1 = -127;
			byte k2 = -127;
			byte k3 = -127;
			byte k4 = -127;

			/*
			 * We will record time stats while searching key So we get current
			 * time and end time after findingg key
			 */
			long startTime = System.currentTimeMillis();
			long endTime = 0;

			/*
			 * Change bytes with for different levels
			 */
			for (k1 = -127; k1 < 128; k1++) {
				for (k2 = -127; k2 < 128; k2++) {
					for (k3 = -127; k3 < 128; k3++) {
						for (k4 = -127; k4 < 128; k4++) {

							/*
							 * Create full key with changing byte values where key is 16 byte which equals 128 bit 
							 * AES key
							 */
							byte[] key_full = new byte[] { key_part1[0], key_part1[1], key_part1[2], key_part1[3],
									key_part1[4], key_part1[5], key_part1[6], key_part1[7], key_part1[8], key_part1[9],
									key_part1[10], key_part1[11], k1, k2, k3, k4 };

							/*
							 * Create new AES Key with new byte values
							 */
							SecretKey aesKey = new SecretKeySpec(key_full, "AES");

							/*
							 * Decrypt with key and get bytes
							 */
							clear_byte = a.myDecrypt(aesKey, encryptedBytes);

							/*
							 * We look for specified string which is here "Hello" in the clear text if
							 * we find the string we give full text, time lapse and working
							 * key
							 */
							if (clear_byte[0] == "H".getBytes()[0] && clear_byte[1] == "e".getBytes()[0]
									&& clear_byte[2] == "l".getBytes()[0] && clear_byte[3] == "l".getBytes()[0]
									&& clear_byte[4] == "o".getBytes()[0]) {

								/*
								 * End counting time and calculate time lapse
								 */
								endTime = System.currentTimeMillis();
								long differenceTime = endTime - startTime;
								
								/*
								 * Write key to console
								 */
								System.out.printf("Key: %s", key_full);
								
								/*
								 * Write time lapse to console
								 */
								System.out.printf("Total time: %s", TimeUnit.MILLISECONDS.toSeconds(differenceTime));
								
								/*
								 * Write clear text message to console
								 */
								System.out.printf("Message: %s", clear_byte.toString());

								break;

							}
						}
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/*
	 * Read from file as byte array
	 */
	public byte[] readFile(String path) throws IOException {
		/*
		 * Set path and file object
		 */
		File myFile = new File(path);
		
		/*
		 *Get file length for byte array
		 */
		byte[] byteArray = new byte[(int) myFile.length()];

		/*
		 * Read from file
		 */
		FileInputStream in;
		try {
			in = new FileInputStream(path);
			in.read(byteArray);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return byteArray;

	}

	/*
	 * Write to file as byte array
	 */
	public int writeFile(String path, byte[] byteArray) throws IOException {

		FileOutputStream out;

		try {
			out = new FileOutputStream(path);
			out.write(byteArray);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 1;

	}

	public byte[] myDecrypt(SecretKey aesKey, byte[] encryptedBytes) {

		byte[] decryptedBytes = null;

		try {
			/*
			 *  Decrypt cipher instance where AES/ECB/PKCS5Padding
			 */
			Cipher decryptCipher = Cipher.getInstance(" AES/ECB/PKCS5Padding");

			/*
			 * Init with AES key
			 */
			decryptCipher.init(Cipher.DECRYPT_MODE, aesKey);

			/*
			 *  Create streams to read encryptedBytes and decryptCipher
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

			System.out.println("Result: " + new String(outputStream.toByteArray()));
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

			System.out.printf("Encrypted bytes %s\n", encryptedBytes.toString());

		} catch (Exception ex) {
			ex.printStackTrace();

		}

		return encryptedBytes;
	}

}
