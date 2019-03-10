package com.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class T {
	public static void main(String[] args) {
		String str = "200,6,18.5211706,73.8467203";
		System.out.println("........." + str.substring(6));
		str = str.substring(6);
		String strrrr[] = str.split(",");
		for (int i = 0; i < strrrr.length; i++) {
			System.out.println(strrrr[i]);
		}
	}
}
