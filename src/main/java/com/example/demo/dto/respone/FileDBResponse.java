package com.example.demo.dto.respone;

import lombok.Data;

@Data
public class FileDBResponse {
	private String title;
	private String url;
	private String type;
	private long size;

	public FileDBResponse(String title, String url, String type, long size) {
		this.title = title;
		this.url = url;
		this.type = type;
		this.size = size;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

}