/**
 * 
 */
package lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author ismail
 *
 */
public class FileOperations {
	/*
	 * Read from file as byte array
	 */
	public byte[] readFile(String path) throws IOException {
		/*
		 * Set path and file object
		 */
		File myFile = new File(path);

		/*
		 * Get file length for byte array
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
}
