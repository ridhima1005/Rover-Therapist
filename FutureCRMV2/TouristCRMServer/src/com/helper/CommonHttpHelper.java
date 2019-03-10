package com.helper;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonHttpHelper extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected Class[] types = { HttpServletRequest.class,
			HttpServletResponse.class };

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String sMethod = StringHelperNew.n2s(request.getParameter("methodId"));
		Object args[] = { request, response };
		Class cls = this.getClass();
		Method meth;
		try {
			meth = cls.getMethod(sMethod, types);
			System.out.println("Method name is " + sMethod);
			String target = (String) meth.invoke(this, args);
			if (target != null)
				request.getRequestDispatcher(target).forward(request, response);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public void forward(HttpServletRequest request,
			HttpServletResponse response, String page) {
		try {
			request.getRequestDispatcher(page).forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void redirect(HttpServletRequest request,
			HttpServletResponse response, String page) {
		try {
			response.sendRedirect(page);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
