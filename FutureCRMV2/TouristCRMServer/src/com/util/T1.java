package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class T1 {
	public static void main(String[] args) {

		try {
			URL url = new URL("http://192.168.0.101:9999/?");

			// Socket socket=new Socket("192.168.0.101",9999);
			InputStream is = url.openStream();
			URLConnection uc = url.openConnection();
			uc.setDoOutput(true);
			OutputStream os = uc.getOutputStream();
			os.flush();
			os.close();

			System.out.println("Connection");

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
