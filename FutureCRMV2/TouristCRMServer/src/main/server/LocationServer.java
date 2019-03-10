package main.server;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.util.ConnectionManager;
import com.util.ReverseGeocoder;
import com.helper.*;

public class LocationServer {
	public static void main(String[] args) throws IOException {
		InetSocketAddress addr = new InetSocketAddress(9898);
		HttpServer server = HttpServer.create(addr, 0);

		server.createContext("/", new MyHandler());
		server.setExecutor(Executors.newCachedThreadPool());
		server.start();
		System.out.println("Server is listening on port 9898");

	}
}

class MyHandler implements HttpHandler {
	public void handle(HttpExchange exchange) throws IOException {

		try {
			boolean skipWriting = false;
			String requestMethod = exchange.getRequestMethod();

			String uri = exchange.getRequestURI().getQuery();
			System.out.println("URI " + uri);
			HashMap parameters = new HashMap();
			if (uri != null) {
				String tokens[] = uri.split("&");
				System.out.println("tokens " + tokens.length);
				for (String keyvalue : tokens) {
					String tok[] = keyvalue.split("=");
					String key = "", value = "";
					if (tok.length > 0)
						key = tok[0];
					if (tok.length > 1)
						value = tok[1];
					// System.out.println("key "+key+"  "+value);
					parameters.put(key, URLDecoder.decode(value));
				}
			}

			// if (requestMethod.equalsIgnoreCase("GET")) {
			Headers responseHeaders = exchange.getResponseHeaders();
			responseHeaders.set("Content-Type", "text/plain");
			exchange.sendResponseHeaders(200, 0);

			OutputStream responseBody = exchange.getResponseBody();
			Headers requestHeaders = exchange.getRequestHeaders();

			Object obj = parameters.get("value");
			Object nameObj = parameters.get("name");
			Object msgObj = parameters.get("message");

			System.out.println("Method " + parameters.get("method"));
			System.out.println("m in method");
			StringBuffer sb = new StringBuffer();
			if (parameters.get("method") != null) {
				String method = parameters.get("method").toString();
				if (method.equalsIgnoreCase("img")) {
					// exchange.getResponseHeaders().add("Content Type",
					// "image/png");
					BufferedImage buffer = ImageIO.read(new File(
							"E:/bankingsite.jpg"));

					ImageIO.write(buffer, "jpg", responseBody);
					responseBody.flush();
					responseBody.close();
					skipWriting = true;
				} else if (method.equalsIgnoreCase("checkIMEI")) {

					String imei = StringHelperNew.n2s(parameters.get("imei"));
					boolean success = ConnectionManager.isIMEIExists(imei);
					sb.append(success);
				} else if (method.equalsIgnoreCase("checkParentPhone")) {
					String imei = StringHelperNew.n2s(parameters.get("imei"));
					String incomingno = StringHelperNew.n2s(parameters
							.get("incomingno"));
					boolean success = ConnectionManager.isPhnoExists(imei,
							incomingno);
					sb.append(success);
				} else if (method.equalsIgnoreCase("getLocationUsers")) {
					String imei = StringHelperNew.n2s(parameters.get("imei"));
					List lost = ConnectionManager.getSameLocationUserList(imei);
					ObjectOutputStream os = new ObjectOutputStream(responseBody);
					os.writeObject(lost);
					os.flush();
					os.close();
					skipWriting = true;
				} else if (method.equalsIgnoreCase("getUserPreference")) {
					String imei = StringHelperNew.n2s(parameters.get("imei"));
					int success = ConnectionManager.getDomainId(imei);
					sb.append(success);
				} else if (method.equalsIgnoreCase("saveGeoTags")) {
					String success = ConnectionManager.saveGeoTags(parameters);
					sb.append(success);

				} else if (method.equalsIgnoreCase("getUserDate")) {
					String str = StringHelperNew.n2s(parameters.get("lat"));
					String date = ConnectionManager.getUserDate(str);

					sb.append(date);

				} else if (method.equalsIgnoreCase("saveMsg")) {
					String success = ConnectionManager.saveMsg(parameters);
					sb.append(success);
				} else if (method.equalsIgnoreCase("getUserGeoTag")) {
					String imei = StringHelperNew.n2s(parameters.get("imei"));
					List param = ConnectionManager.getUserGeoTag(imei);
					ObjectOutputStream os = new ObjectOutputStream(responseBody);
					os.writeObject(param);
					os.flush();
					os.close();
					skipWriting = true;
				} else if (method.equalsIgnoreCase("getFriendLocation")) {
					// String imei=StringHelper.n2s(parameters.get("imei"));
					List param = ConnectionManager.getFriendLocation();
					ObjectOutputStream os = new ObjectOutputStream(responseBody);
					os.writeObject(param);
					os.flush();
					os.close();
					skipWriting = true;
				} else if (method.equalsIgnoreCase("saveUser")) {
					String success = ConnectionManager.saveUser(parameters);
					sb.append(success);
				} else if (method.equalsIgnoreCase("insertTrackUser")) {
					boolean success = ConnectionManager
							.insertTrackUser(parameters);
					sb.append(success);
				}

				else if (method.equalsIgnoreCase("getUserLocationDetails")) {
					String name = StringHelperNew.n2s(parameters.get("name"));
					List param = ConnectionManager.getUserLocationDetails(name);
					ObjectOutputStream os = new ObjectOutputStream(responseBody);
					os.writeObject(param);
					os.flush();
					os.close();
					skipWriting = true;
				} else if (method.equalsIgnoreCase("getMyLocation")) {
					String imei = StringHelperNew.n2s(parameters.get("imei"));
					String success[] = ConnectionManager.getLocation(imei);
					sb.append(success[0] + "=" + success[1]);
				}

				else if (method.equalsIgnoreCase("getUserDetails")) {
					String imei = StringHelperNew.n2s(parameters.get("imei"));
					List param = ConnectionManager.getUserDetails(imei);
					ObjectOutputStream os = new ObjectOutputStream(responseBody);
					os.writeObject(param);
					os.flush();
					os.close();
					skipWriting = true;
				} else if (method.equalsIgnoreCase("getGPSLocation")) {
					String lat = StringHelperNew.n2s(parameters.get("lat"));
					String longitude = StringHelperNew.n2s(parameters
							.get("long"));
					String address[] = ReverseGeocoder.getLocation(lat + ","
							+ longitude);
					sb.append(address[0] + "=" + address[1]);
				}

				else if (method.equalsIgnoreCase("domainList")
						|| method.equalsIgnoreCase("domainInfo")
						|| method.equalsIgnoreCase("getDomainListMap")) {
					System.out.println("Get Domain List ");
					List list = null;
					if (method.equalsIgnoreCase("domainList"))
						list = ConnectionManager.getDomainList();
					else if (method.equalsIgnoreCase("domainInfo")) {
						String pincode = StringHelperNew.n2s(parameters
								.get("pincode"));
						list = ConnectionManager.getDomainInfo(pincode);
					} else if (method.equalsIgnoreCase("getDomainListMap")) {

						list = ConnectionManager.getDomainListMap();

					}
					ObjectOutputStream os = new ObjectOutputStream(responseBody);
					os.writeObject(list);
					os.flush();
					os.close();
					skipWriting = true;
				}
			}
			if (!skipWriting) {
				System.out.println("after method");
				System.out.println(sb.toString());

				responseBody.write(sb.toString().getBytes());
				responseBody.flush();
				responseBody.close();
			}
			System.out
					.print("..........Completing Response from here ..............");

			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		// This code ensures that all the pixels in the image are loaded
		image = new ImageIcon(image).getImage();

		// Determine if the image has transparent pixels; for this method's
		// implementation, see Determining If an Image Has Transparent Pixels
		boolean hasAlpha = hasAlpha(image);

		// Create a buffered image with a format that's compatible with the
		// screen
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		try {
			// Determine the type of transparency of the new buffered image
			int transparency = Transparency.OPAQUE;
			if (hasAlpha) {
				transparency = Transparency.BITMASK;
			}

			// Create the buffered image
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null),
					image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			// The system does not have a screen
		}

		if (bimage == null) {
			// Create a buffered image using the default color model
			int type = BufferedImage.TYPE_INT_RGB;
			if (hasAlpha) {
				type = BufferedImage.TYPE_INT_ARGB;
			}
			bimage = new BufferedImage(image.getWidth(null),
					image.getHeight(null), type);
		}

		// Copy image to buffered image
		Graphics g = bimage.createGraphics();

		// Paint the image onto the buffered image
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}

	public static boolean hasAlpha(Image image) {
		// If buffered image, the color model is readily available
		if (image instanceof BufferedImage) {
			BufferedImage bimage = (BufferedImage) image;
			return bimage.getColorModel().hasAlpha();
		}

		// Use a pixel grabber to retrieve the image's color model;
		// grabbing a single pixel is usually sufficient
		PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
		}

		// Get the image's color model
		ColorModel cm = pg.getColorModel();
		return cm.hasAlpha();
	}
}