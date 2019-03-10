package com.helper;

import java.io.Serializable;

public class UserModel implements Serializable {
	static final long serialVersionUID = 1L;
	String user_id, gcm_regid, username, useremail, created_at, role;
	int errorCode = 0;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	String message = "";
	boolean success = false;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getGcm_regid() {
		return gcm_regid;
	}

	public void setGcm_regid(String gcm_regid) {
		this.gcm_regid = gcm_regid;
	}

	public String getUsername() {
		return username;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

}
