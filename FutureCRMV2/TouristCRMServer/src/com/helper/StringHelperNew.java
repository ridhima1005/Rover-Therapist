package com.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletRequest;

//import javax.servlet.http.HttpServletRequest;

/*
 */
public class StringHelperNew {

	public static String DateToMmddyyyy(java.util.Date myDate) {

		if (myDate == null)
			return "";

		SimpleDateFormat simpledateformat = new SimpleDateFormat("MM/dd/yyyy");

		String dateString = "";

		dateString = simpledateformat.format(myDate);
		return dateString;

	}

	public static String getNowAsMmddyyyy() {

		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("MM/dd/yyyy");

		String dateString = "";

		java.util.Date myDate = rightNow.getTime();
		dateString = simpledateformat.format(myDate);
		return dateString;
	}

	public static boolean isDouble(String checkStr) {
		try {
			Double.parseDouble(checkStr);
			return true; // Did not throw, must be a number
		} catch (NumberFormatException err) {
			return false; // Threw, So is not a number
		}
	}

	public static boolean isLastDayOfMonth() {
		boolean lastDay = false;
		final Calendar cal = Calendar.getInstance();

		cal.setTime(new Date());
		int i = cal.get(Calendar.MONTH);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		if (cal.get(Calendar.MONTH) != i)
			lastDay = true;
		return lastDay;
	}

	/*
	 * date = MM/dd/yyyy format
	 */
	public static boolean isLastDayOfMonth(String date) {
		boolean lastDay = false;
		final Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new SimpleDateFormat("MM/dd/yyyy").parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// cal.setTime(new Date());
		int i = cal.get(Calendar.MONTH);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		if (cal.get(Calendar.MONTH) != i)
			lastDay = true;
		return lastDay;
	}

	public static float n2f(Object d) {
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

	public static String n2s(Object d) {
		String dual = "";
		if (d == null) {
			dual = "";
		} else
			dual = d.toString().trim();

		return dual;
	}

	public static String n2s(String d) {
		String ret = d;
		if (ret == null) {
			ret = "";
		} else {
			ret = ret.trim();
		}

		return ret;
	}

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

	public static HashMap<String, String> displayRequest(ServletRequest request) {
		Enumeration<String> paraNames = request.getParameterNames();
		System.out.println(" URL");

		System.out.println(" ------------------------------");
		System.out.println("parameters:");
		HashMap<String, String> parameters = new HashMap<String, String>();
		String pname;
		String pvalue;
		while (paraNames.hasMoreElements()) {
			pname = (String) paraNames.nextElement();
			pvalue = request.getParameter(pname);
			try {
				pvalue = URLDecoder.decode(pvalue);
				pvalue = pvalue.trim();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(pname + " = " + pvalue + "");
			parameters.put(pname, pvalue);
		}
		System.out.println(" ------------------------------");
		return parameters;
	}

	public static double n2d(Object obj) {
		if (obj == null)
			return 0.0;
		String tmp = obj.toString().trim();
		double ret = 0.0;
		try {
			ret = Double.parseDouble(tmp);
		} catch (NumberFormatException e) {
			return 0.0;
		}

		return ret;
	}

}
