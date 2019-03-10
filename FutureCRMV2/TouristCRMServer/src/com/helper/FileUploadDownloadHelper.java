package com.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//import java.io.IOException;
import java.io.InputStream;

/**
 * @author admin
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class FileUploadDownloadHelper {

	public String uploadFile(InputStream uploadInStream, String filename) {
		try {
			System.out.println(filename);
			FileOutputStream fOut = new FileOutputStream(filename);
			int c = 0;
			while ((c = uploadInStream.read()) != -1) {
				fOut.write(c);
			} // while
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) {
		File uploadedFile = null;
		try {
			InputStream fis = new FileInputStream(
					uploadedFile.getAbsolutePath());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Returns the contents of the file in a byte array.
	public static byte[] getBytesFromFile(File file) {
		if (file.exists()) {
			InputStream is;
			byte[] bytes = null;
			try {
				is = new FileInputStream(file);

				// Get the size of the file
				long length = file.length();

				// You cannot create an array using a long type.
				// It needs to be an int type.
				// Before converting to an int type, check
				// to ensure that file is not larger than Integer.MAX_VALUE.
				if (length > Integer.MAX_VALUE) {
					// File is too large
				}

				// Create the byte array to hold the data
				bytes = new byte[(int) length];

				// Read in the bytes
				int offset = 0;
				int numRead = 0;
				while (offset < bytes.length
						&& (numRead = is.read(bytes, offset, bytes.length
								- offset)) >= 0) {
					offset += numRead;
				}

				// Ensure all the bytes have been read in
				// if (offset < bytes.length) {
				// throw new
				// IOException("Could not completely read file "+file.getName());
				// }

				// Close the input stream and return bytes
				is.close();
			} catch (Exception e) {

				e.printStackTrace();
			}

			return bytes;
		} else {
			return null;
		}
	}
}
