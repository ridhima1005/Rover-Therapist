/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import com.constants.ServerConstants;
import com.helper.BeanUtil;
import com.helper.DBUtils;
import com.helper.DomainModel;
import com.helper.ImageModel;
import com.helper.StringHelperNew;
import com.helper.UserAccount;

/**
 * 
 * @author Admin
 */
// connection
public class WebConnectionManager {
	public static Connection getDBConnection() {
		Connection conn = null;
		try {
			Class.forName(ServerConstants.db_driver);
			conn = DriverManager.getConnection(ServerConstants.db_url,
					ServerConstants.db_user, ServerConstants.db_pwd);
			System.out.println("Got Connection");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(
					null,
					"Please start the mysql Service from XAMPP Console.\n"
							+ ex.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;
	}

	// login of admin from web based application
	public static boolean checkLogin(HashMap parameters, HttpSession session) {
		String loginname = StringHelperNew.n2s(parameters.get("userId"));
		String pass = StringHelperNew.n2s(parameters.get("pass"));
		String query = "select * from useraccount  where username=? and password=?";
		List list = DBUtils.getBeanList(UserAccount.class, query, new Object[] {
				loginname, pass });
		if (list.size() > 0) {
			UserAccount um = (UserAccount) list.get(0);
			session.setAttribute("USER_MODEL", um);
			session.setAttribute("MESSAGE", "Login Successful!");
			return true;
		} else {
			session.setAttribute("MESSAGE", "Invalid Credential");
		}

		return false;
	}

	// close connection
	public static void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void main(String[] args) {
		getDBConnection();

	}

	// for dropdown list of the domains
	public static String getDomain() {
		String qu = ServerConstants.DOMAIN_QUERY;
		;
		return getDropDownList(qu);
	}

	public static String getDropDownList(String query) {
		String ret = "";

		System.out.println(query);
		List domainList = DBUtils.getBeanList(DomainModel.class, query);
		for (int i = 0; domainList.size() > 0 && i < domainList.size(); i++) {
			DomainModel dm = (DomainModel) domainList.get(i);
			ret += "<OPTION value='" + dm.getDomainId() + "'>"
					+ dm.getDomainDesc() + "</OPTION>";
		}
		System.out.println("ret  " + ret);
		return ret;
	}

	// add details
	public static int addDetails(String domain, String addDetails,
			String location, String locationPh, String reviewer, String rating,
			String latitude, String longitude) {
		// TODO Auto-generated method stub
		String foratted_Address = ReverseGeocoder.getLocation(latitude + ","
				+ longitude)[0];
		int i = DBUtils.executeUpdate(ServerConstants.INSERT_ADD, new Object[] {
				domain, addDetails, location, locationPh, reviewer, rating,
				latitude, longitude, foratted_Address });
		return i;
	}

	// class UserAccount accessed from helper package

	public static UserAccount getUserName(String name) {

		String sql = "Select * from useraccount where displayName like '"
				+ name + "'";
		List list = DBUtils.getBeanList(UserAccount.class, sql);
		UserAccount um = null;
		if (list.size() > 0) {
			um = (UserAccount) list.get(0);
		}
		return um;
	}

	public static List getAllUsers() {

		String sql = "Select * from useraccount ";
		List list = DBUtils.getBeanList(UserAccount.class, sql);

		return list;
	}

	// fetching details
	public static List fetchDetails(String domain) {
		// TODO Auto-generated method stub
		List i = DBUtils.getParameterizedList(ServerConstants.SELECT_QUERY,
				domain);
		System.out.println("Select query is here " + i);
		return i;
	}

	// delete details

	public static int deleteAdds(String id) {
		// TODO Auto-generated method stub
		int i = DBUtils.executeUpdate(ServerConstants.Delete_Query,
				new Object[] { id });
		return i;
	}

	// delete user
	public static int deleteUser(String imei) {
		// TODO Auto-generated method stub
		System.out.println("imei no for delete user " + imei);
		int i = DBUtils.executeUpdate(ServerConstants.Delete_User,
				new Object[] { imei });
		return i;
	}

	// ====================================================================================
	// add images

	public static boolean saveImage(byte imagedata[], int catagoryId, int size,
			String filename) {
		Connection connection = getDBConnection();
		PreparedStatement psmnt;
		boolean success = false;
		try {
			ByteArrayInputStream inStream = new ByteArrayInputStream(imagedata);
			psmnt = connection
					.prepareStatement("insert into images (imagedata, catagoryId, size, filename) values(?,?,?,?)");

			psmnt.setBinaryStream(1, inStream, imagedata.length);
			psmnt.setInt(2, catagoryId);
			psmnt.setInt(3, imagedata.length);
			psmnt.setString(4, filename);
			int i = psmnt.executeUpdate();
			success = true;
			System.out.println("Image Uploaded To Database ...");
			psmnt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			success = false;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;

	}

	// delete images
	public static boolean deleteImage(HashMap parameters) {
		int imageId = StringHelperNew.n2i(parameters.get("imgid"));
		String query = "delete from images where imageId=" + imageId;
		int i = DBUtils.executeUpdate(query, null);
		if (i > 0) {
			return true;
		}
		System.out.println("Image Uploaded To Database ...");
		return false;
	}

	public static List getImageCatagories() {
		String sql = " 	SELECT `catagoryId` as `key` , `catagoryDesc` as `value` FROM imagecatagory 		";
		List list = DBUtils.getBeanList(BeanUtil.class, sql);
		return list;

	}

	public static String updateUserCCP(HashMap parameters) {
		// String
		// employeeid=StringHelper.n2s(parameters.get("employeeid"));
		String imageids = StringHelperNew.n2s(parameters.get("imageids"));
		String clickp = StringHelperNew.n2s(parameters.get("clickp"));
		String empId = StringHelperNew.n2s(parameters.get("empId"));
		String sql = "update employee  set employeeImages=?,employeeCCP=?,employeeapproved='P' where employeeId=?";
		int list = DBUtils.executeUpdate(sql, imageids, clickp, empId);
		String msg = "";
		if (list > 0) {
			msg = "1";
		} else {
			msg = "-1";
		}

		return msg;

	}

	// Update Password
	public static int updatePhn(String imei, String phn) {

		String sql = "update useraccount set  fathercontacts='" + phn
				+ "' where imei like '" + imei + "'";
		int i = DBUtils.executeUpdate(sql, new Object[] {});
		System.out.println("Number of records updated " + i);
		return i;
	}

	public static List getImageList() {
		String sql = " 	SELECT ic.catagoryId, ic.catagoryDesc, i.imageId, i.size, i.filename,  date_format(i.`uploadedDate`, '%a %d-%b-%y') as uploadedDate FROM imagecatagory ic,images i where ic.catagoryId=i.catagoryId ";
		List list = DBUtils.getBeanList(ImageModel.class, sql);
		return list;
	}

	// checking of username and password

	public void check(String user, String pass) {
		// String user = request.getParameter("userId");
		// String pass = request.getParameter("pass");
		Connection con1 = WebConnectionManager.getDBConnection();
		PreparedStatement ps;
		try {
			String query = "SELECT * FROM useraccount u where u.login='" + user
					+ "' and  u.pass='" + pass + "' ";
			System.out.println(query + " " + con1);
			ps = con1.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				// response.sendRedirect("/pages/home.jsp");
				System.out.println("Home");
			} else {
				// response.sendRedirect("/pages/index.jsp");
				System.out.println("Index");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void getImage(int imageId, OutputStream out) {
		String sql = "SELECT imageData FROM images where imageId=" + imageId;
		Connection c = null;
		try {
			c = getDBConnection();
			Statement stmt;
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				Blob b = rs.getBlob(1);

				InputStream in = b.getBinaryStream();
				int length = 0;
				int bufferSize = 4096;
				byte[] buffer = new byte[bufferSize];
				while ((length = in.read(buffer)) != -1) {
					out.write(buffer, 0, length);
				}
				in.close();
				out.flush();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
