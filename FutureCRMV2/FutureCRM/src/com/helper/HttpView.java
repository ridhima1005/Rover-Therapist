package com.helper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ParseException;
import android.util.Log;

public class HttpView {
	public static String result[][] = new String[500][];
	public static int count = -1;
	static String TAG = "HttpView";

	public static String connectToServer(String url) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		System.out.println("URL " + url);
		HttpResponse response;
		String s = "";
		HttpView.count = -1;
		try {
			int i = 0;
			for (i = 0; i < result.length; i++) {
				result[i] = null;
			}

			Log.d(TAG, "UPLOAD: about to execute");
			response = httpclient.execute(httppost);
			Log.d(TAG, "UPLOAD: executed");

			s = HttpView.getResponseBody(response);
			s = s.trim();
			String rows[] = s.split("\n");

			System.out.println("rows.length " + rows.length);
			for (String string : rows) {
				String cols[] = string.split(",");
				result[++count] = cols;

			}

			System.out.println("Data " + s);
			// System.out.println("Data in result "+result[i]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static Bitmap drawable_from_url(String url) {
		Bitmap x = null;
		try {

			System.out.println("URL IS " + url);
			HttpURLConnection connection = (HttpURLConnection) new URL(url)
					.openConnection();
			connection.setRequestProperty("User-agent", "Mozilla/4.0");

			connection.connect();
			InputStream input = connection.getInputStream();

			x = BitmapFactory.decodeStream(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return x;
	}

	public static boolean checkConnectivityServer(String ip, int port) {
		boolean success = false;
		try {
			Socket soc = new Socket();
			SocketAddress socketAddress = new InetSocketAddress(ip, port);
			soc.connect(socketAddress, 3000);
			success = true;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println(" Connecting to server " + success);
		return success;

	}

	public static String createURL(HashMap param) {

		String parameterURLString = "";

		Set set = param.keySet();
		// Get an iterator
		Iterator i = set.iterator();
		// Display elements
		while (i.hasNext()) {
			String key = StringHelper.nullObjectToStringEmpty(i.next());
			String value = StringHelper.nullObjectToStringEmpty(param.get(key));
			value = URLEncoder.encode(value);
			parameterURLString += "&" + key + "=" + value;

		}

		if (parameterURLString.length() > 0) {
			parameterURLString = parameterURLString.substring(1);

		}
		String url = AndroidConstants.MAIN_URL() + parameterURLString;
		System.out.println("url  " + url);
		return url;
	}

	public static String getData(HashMap param) {
		String parameterURLString = connectToServer(createURL(param));
		return parameterURLString;
	}

	public String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("HttpView", ex.toString());
		}
		return null;
	}

	public static String urlEncode(String sUrl) {
		if (sUrl == null) {
			return null;
		}
		int i = 0;
		String urlOK = "";
		while (i < sUrl.length()) {
			if (sUrl.charAt(i) == '<') {
				urlOK = urlOK + "%3C";
			} else if (sUrl.charAt(i) == '/') {
				urlOK = urlOK + "%2F";
			} else if (sUrl.charAt(i) == '\\') {
				urlOK = urlOK + "%2F";
			} else if (sUrl.charAt(i) == '>') {
				urlOK = urlOK + "%3E";
			} else if (sUrl.charAt(i) == ' ') {
				urlOK = urlOK + "%20";
			} else if (sUrl.charAt(i) == ':') {
				urlOK = urlOK + "%3A";
			} else if (sUrl.charAt(i) == '-') {
				urlOK = urlOK + "%2D";
			} else {
				urlOK = urlOK + sUrl.charAt(i);
			}
			i++;
		}
		return (urlOK);
	}

	public static String getResponseBody(HttpResponse response) {
		String response_text = null;
		HttpEntity entity = null;
		try {
			entity = response.getEntity();
			response_text = _getResponseBody(entity);

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			if (entity != null) {
				try {
					entity.consumeContent();
				} catch (IOException e1) {
				}
			}
		}
		return response_text;
	}

	public static String _getResponseBody(final HttpEntity entity)
			throws IOException, ParseException {
		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}
		InputStream instream = entity.getContent();
		if (instream == null) {
			return "";
		}

		if (entity.getContentLength() > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(

			"HTTP entity too large to be buffered in memory");
		}
		String charset = getContentCharSet(entity);
		if (charset == null) {
			charset = HTTP.DEFAULT_CONTENT_CHARSET;
		}
		Reader reader = new InputStreamReader(instream, charset);
		StringBuilder buffer = new StringBuilder();
		try {

			char[] tmp = new char[1024];
			int l;
			while ((l = reader.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}

		} finally {
			reader.close();
		}
		return buffer.toString();
	}

	public static String getContentCharSet(final HttpEntity entity)
			throws ParseException {

		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}

		String charset = null;

		if (entity.getContentType() != null) {

			HeaderElement values[] = entity.getContentType().getElements();

			if (values.length > 0) {

				NameValuePair param = values[0].getParameterByName("charset");

				if (param != null) {

					charset = param.getValue();

				}

			}

		}

		return charset;

	}

	public static ArrayList getArrayServer(HashMap param) {
		return fetchDataArrayServer(createURL(param));
	}

	public static ArrayList fetchDataArrayServer(String url) {
		System.out.println("URL " + url);
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		HttpResponse response;

		String sArr = "";
		ArrayList s = new ArrayList();
		HttpView.count = -1;
		try {
			int i = 0;
			for (i = 0; i < result.length; i++) {
				result[i] = null;
			}

			Log.d(TAG, "Executing " + url);
			response = httpclient.execute(httppost);
			Log.d(TAG, "UPLOAD: executed");

			s = HttpView.getResponseBodyObject(response);
			Log.d(TAG, "Got Response: " + s);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static ArrayList getResponseBodyObject(HttpResponse response) {
		ArrayList response_text = null;
		HttpEntity entity = null;
		try {
			entity = response.getEntity();
			response_text = _getResponseBodyObject(entity);

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			if (entity != null) {
				try {
					entity.consumeContent();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		System.out.println("response_text size " + response_text.size());
		return response_text;
	}

	public static ArrayList _getResponseBodyObject(final HttpEntity entity)
			throws IOException, ParseException {
		ArrayList arr = new ArrayList();
		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}
		InputStream instream = entity.getContent();
		if (instream == null) {
			return null;
		}

		if (entity.getContentLength() > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(

			"HTTP entity too large to be buffered in memory");
		}
		String charset = getContentCharSet(entity);
		if (charset == null) {
			charset = HTTP.DEFAULT_CONTENT_CHARSET;
		}
		ObjectInputStream reader = new ObjectInputStream(instream);
		StringBuilder buffer = new StringBuilder();
		try {
			char[] tmp = new char[1024];
			Object o;
			while ((o = reader.readObject()) != null) {
				if (o instanceof ArrayList) {
					arr = (ArrayList) o;
				}
				System.out.println("no of elements: " + arr.size());
			}
			// for (int a = 0; a < elements.length; a++) {
			// System.out.println(elements[a]);
			// }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			reader.close();
		}
		return arr;
	}

	public static Object connect2ServerObject(String url) {
		Log.v(TAG, "Reading Object");
		Log.v(TAG, url);
		Object o = null;
		System.out.println("URL " + url);
		URL u;
		try {
			u = new URL(url);
			URLConnection uc = u.openConnection();

			ObjectInputStream ois = new ObjectInputStream(uc.getInputStream());
			o = ois.readObject();
			System.out.println("object " + o);
			u = null;

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return o;
	}
}
