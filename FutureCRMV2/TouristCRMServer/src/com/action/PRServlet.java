package com.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.helper.CommonHttpHelper;


public class PRServlet extends CommonHttpHelper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PRServlet() {
		super();
	}

	public void authenticateUser(HttpServletRequest request,
			HttpServletResponse response) {
		String page = "";
		System.out.println("Method Called - Authenticate User");
		forward(request, response, page);
	}

	public void registerUser(HttpServletRequest request,
			HttpServletResponse response) {
		String page = "";
	}

	public void searchStudent(HttpServletRequest request,
			HttpServletResponse response) {

	}
}
