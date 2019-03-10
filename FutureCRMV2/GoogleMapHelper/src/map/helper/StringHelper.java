package map.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/*
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

	public static int safeObjToInt(Object obj) {
		if (obj == null)
			return 0;
		String tmp = (String) obj;
		int ret = 0;
		try {
			ret = Integer.parseInt(tmp);
		} catch (NumberFormatException e) {
			return 0;
		}

		return ret;
	}

	public static double safeObjToDouble(Object obj) {
		if (obj == null)
			return 0.0;
		String tmp = (String) obj;
		double ret = 0.0;
		try {
			ret = Double.parseDouble(tmp);
		} catch (NumberFormatException e) {
			return 0.0;
		}

		return ret;
	}

	public static double n2d(Object obj) {
		if (obj == null)
			return 0.0;
		String tmp = (String) obj;
		double ret = 0.0;
		try {
			ret = Double.parseDouble(tmp);
		} catch (NumberFormatException e) {
			return 0.0;
		}

		return ret;
	}

	public static String nullToStringNull(String d) {
		String ret = d;
		if (ret == null) {
			ret = "NULL";
		}
		return ret;
	}

	public static String emptyToStringNull(String d) {
		String ret = d;
		if (ret.equals("")) {
			ret = "NULL";
		}
		return ret;
	}

	public static String n2s(Object d) {
		String ret = "";
		if (ret == null) {
			ret = "";
		} else {
			ret = d.toString().trim();
		}

		return ret;
	}

	public static String n2s(String d) {
		String ret = d;
		if (ret == null) {
			ret = "";
		} else {
			d = d.trim();
		}

		return ret;
	}

	public static String nullToStringEmpty(String d) {
		String ret = d;
		if (ret == null) {
			ret = "";
		} else {
			d = d.trim();
		}

		return ret;
	}

	public static String nullObjectToStringEmpty(Object d) {
		String dual = "";
		if (d == null) {
			dual = "";
		} else
			dual = d.toString().trim();

		return dual;
	}

	public static float n2f(Object d) {
		return nullObjectToFloatEmpty(d);
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

	public static String CovertIdAry2String(int[] id_array) {
		if (id_array != null) {
			String inStr = "";
			for (int i = 0; i < id_array.length; i++) {
				inStr += id_array[i];
				if ((i + 1) < id_array.length)
					inStr += ", ";
			}
			return (inStr.equals("") ? null : inStr);
		}
		return null;
	}

	public static String RemoveParagraph(String o_comment) {
		if (o_comment != null) {
			String temp = RemoveParagraph(
					RemoveParagraph(o_comment, "<P>", ""), "</P>", "<br>");
			return RemoveParagraph(temp, "<P ", ">", "");
		} else {
			return null;
		}
	}

	public static String RemoveParagraph(String o_comment, String strTarget,
			String strReplace) {
		String p_comment = "";
		int idx0 = 0;
		while (true) {
			int idx1 = o_comment.indexOf(strTarget, idx0);
			if (idx1 > -1) {
				p_comment += (o_comment.substring(idx0, idx1) + strReplace);
				idx0 = idx1 + strTarget.length();
			} else {
				if (idx0 <= (o_comment.length() - 1))
					p_comment += o_comment.substring(idx0);
				break;
			}
		}
		return p_comment;
	}

	public static String RemoveParagraph(String o_comment, String strStart,
			String strEnd, String strReplace) {
		String p_comment = "";
		int idx0 = 0;
		while (true) {
			int idx1 = o_comment.indexOf(strStart, idx0);
			if (idx1 > -1) {
				int idx2 = o_comment.indexOf(strEnd, idx1);
				if (idx2 > -1) {
					p_comment += (o_comment.substring(idx0, idx1) + strReplace);
					idx0 = idx2 + 1;
				}
			} else {
				if (idx0 <= (o_comment.length() - 1))
					p_comment += o_comment.substring(idx0);
				break;
			}
		}
		return p_comment;
	}

	public static String makeupFixedLengthString(String str, int length,
			int side) {
		String makeup = "";
		if (str == null)
			return null;
		if (str.length() > length)
			str = str.substring(0, length - 5) + "...";
		for (int i = 0; i < length - str.length(); i++) {
			makeup += " ";
		}
		if (side == 0) {
			makeup += str;
		} else {
			makeup = str + makeup;
		}
		return makeup;
	}

	public static String addLeft0(String s, int length) {
		if (s != null && s.length() >= length)
			return s;
		if (s == null)
			s = "";
		int make_up = length - s.length();
		for (int i = 0; i < make_up; i++)
			s = "0" + s;
		return s;
	}

	public static String formatUserName(String s) {
		StringTokenizer str = null;
		String name = "";
		if (s != null) {
			str = new StringTokenizer(s);
			name = str.nextToken().toUpperCase().trim().charAt(0) + ". ";
			name += str.nextToken();
		}
		return name;
	}

	/**
	 * This method can be used to replace a part of a string by another string.
	 * It also provides the option of adding a string at the point of
	 * replacement. eg. a newline character can be added using \n In case no
	 * string is required pass null. The replacement will be case-sensitive.
	 * 
	 * @param inStr
	 * @param rStr
	 * @param charToAdd
	 * @return
	 */
	public static String replaceString(String inStr, String rStr,
			String charToAdd) {
		int index = 0;
		if (inStr != null && rStr != null) {
			while (index != -1) {
				index = inStr.indexOf(rStr);
				if (index >= 0) {
					String tempStr = inStr.substring(0, index);
					inStr = tempStr
							+ (charToAdd != null ? charToAdd : charToAdd)
							+ inStr.substring(index + rStr.length());
				}
			}
		}
		return inStr;
	}

	/*
	 * Remove all newline characters from a string.
	 * 
	 * @param text The string to strip newline characters from
	 * 
	 * @return The stripped reply
	 */

	public static String stripNewlines(String text) {
		String output1 = null;
		if (text == null) {
			return output1;
		}

		try {
			StringBuffer output = new StringBuffer();

			BufferedReader in = new BufferedReader(new StringReader(text));
			while (true) {
				String line = in.readLine();
				if (line == null) {
					break;
				}

				output.append(line);

			}
			output1 = output.toString();
			return output1;
		} catch (IOException ex) {
			System.out.println("IOExecption unexpected. " + ex);
			return null;
		}
	}

	// End DLWO0023

	public static String createFixedLengthDataString(String data,
			int fixedLength, String filler, String fillerPos) {

		String dataFiller = "";
		int fieldLength = 0;
		String newData = "";

		fieldLength = data.length();
		// System.out.println("data:" + data);
		// System.out.println("fieldLength:" + fieldLength);

		if (fieldLength < fixedLength) {
			for (int i = 0; i < (fixedLength - fieldLength); i++) {
				dataFiller = dataFiller + filler;
			}

			if (fillerPos != null) {
				if (fillerPos.equals("pre")) {
					newData = dataFiller + data;
				} else if (fillerPos.equals("post")) {
					newData = data + dataFiller;
				}
			}
		} else if (fieldLength >= fixedLength) {
			newData = data.substring(0, fixedLength);
		}

		// System.out.println("newData:" + newData);
		return newData;
	}

	// DDWO0070
	public static String nullObjectToStringnbsp(Object d) {
		String dual = "";
		if (d == null || d.equals("")) {
			dual = "&nbsp";
		} else
			dual = d.toString().trim();

		return dual;
	}

	public static String convertToDecimal(double val, String strFromat) {
		String strVal = "";
		try {
			DecimalFormat df = new DecimalFormat(strFromat);
			strVal = df.format(val);
		} catch (Exception ex) {
		}
		return strVal;
	}

	// EDDS0235

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
		// cal.setTime(new SimpleDateFormat("MM/dd/yyyy").parse("03/31/2009" ));
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final Calendar cal = Calendar.getInstance();
		try {

			cal.setTime(new SimpleDateFormat("MM/dd/yyyy").parse("03/31/2009"));
			int i = cal.get(Calendar.MONTH);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			if (cal.get(Calendar.MONTH) == i)
				System.out.println("not last day");
			else
				System.out.println("last day");
		} catch (java.text.ParseException e) {

		}

	}

}
