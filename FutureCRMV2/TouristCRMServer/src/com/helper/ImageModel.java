package com.helper;

import java.sql.Blob;

//for images
public class ImageModel {
	public int imageId = 0;
	public String size = "";
	public String filename = "";
	public Blob filedata = null;
	public int catagoryId = -1;
	public String catagoryDesc = "";
	public String uploadedDate = "";

	public String getUploadedDate() {
		return uploadedDate;
	}

	public void setUploadedDate(String uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Blob getFiledata() {
		return filedata;
	}

	public void setFiledata(Blob filedata) {
		this.filedata = filedata;
	}

	public int getCatagoryId() {
		return catagoryId;
	}

	public void setCatagoryId(int catagoryId) {
		this.catagoryId = catagoryId;
	}

	public String getCatagoryDesc() {
		return catagoryDesc;
	}

	public void setCatagoryDesc(String catagoryDesc) {
		this.catagoryDesc = catagoryDesc;
	}

}
