package com.database;

public class T {
	public static void main(String[] args) {
		String str = "100628811#18.000006#6.999994#2.999996";
		try {
			String[] temp = str.split("#");
			String pop = temp[0];
			String br = temp[1];
			String dr = temp[2];
			String ir = temp[3];
			System.out.println("pop=" + pop + "br=" + br + "dr=" + dr + "ir="
					+ ir);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
