/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

//import java.util.Vector;

/**
 * 
 * @author user
 */
public class StringHelper {
	public static StringBuffer readURLContent(String url) {
		System.out.println("URL " + url);
		StringBuffer json = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new URL(url).openStream()));
			String line;
			while ((line = in.readLine()) != null) {
				json.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public static String n2s(Object d) {
		String dual = "";
		if (d == null) {
			dual = "";
		} else
			dual = d.toString().trim();

		return dual;
	}

	public static double n2d(Object d) {
		double i = 0;
		if (d != null) {
			String dual = d.toString().trim();
			try {
				i = Double.parseDouble(dual);
			} catch (Exception e) {
				System.out.println("Unable to find integer value");
			}
		}
		return i;
	}

	public static String nullObjectToStringEmpty(Object d) {
		String dual = "";
		if (d == null) {
			dual = "";
		} else
			dual = d.toString().trim();

		return dual;
	}

	public static float nullObjectToFloatEmpty(Object d) {
		float i = 0;
		if (d != null) {
			String dual = d.toString().trim();
			try {
				i = new Float(dual).floatValue();
			} catch (Exception e) {
				System.out.println("Unable to find integer value");
			}
		}
		return i;
	}

	public static int nullObjectToIntegerEmpty(Object d) {
		int i = 0;
		if (d != null) {
			String dual = d.toString().trim();
			try {
				i = new Integer(dual).intValue();
			} catch (Exception e) {
				System.out.println("Unable to find integer value");
			}
		}
		return i;
	}

	public static int n2i(Object d) {
		int i = 0;
		if (d != null) {
			String dual = d.toString().trim();
			try {
				i = new Integer(dual).intValue();
			} catch (Exception e) {
				System.out.println("Unable to find integer value");
			}
		}
		return i;
	}

	public static void main(String args[]) {
		// new StringHelper().split("sadas:asdasd:asdas");

	}

	public static String getDay(int daynum) {
		switch (daynum) {
		case 0:
			return "Sunday";
		case 1:
			return "Monday";
		case 2:
			return "Tuesday";
		case 3:
			return "Wednesday";
		case 4:
			return "Thrusday";
		case 5:
			return "Friday";
		case 6:
			return "Saturday";

		default:
			return "Invalid Day";

		}

	}

}
