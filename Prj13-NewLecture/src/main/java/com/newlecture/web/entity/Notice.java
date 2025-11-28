package com.newlecture.web.entity;

import java.util.Date;

public class Notice {
	
	private int id;
	private String title;
	private Date regDate;
	private String writer_id;
	private int hit;
	private String files;
	private String content;
	private boolean pub;
	
	public Notice(int id, String title, Date regDate, String writer_id, int hit, String files, String content,
			boolean pub) {
		super();
		this.id = id;
		this.title = title;
		this.regDate = regDate;
		this.writer_id = writer_id;
		this.hit = hit;
		this.files = files;
		this.content = content;
		this.pub = pub;
	}

	public Notice() {
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Date getRegDate() {
		return regDate;
	}

	public String getWriter_id() {
		return writer_id;
	}

	public int getHit() {
		return hit;
	}

	public String getFiles() {
		return files;
	}

	public String getContent() {
		return content;
	}

	public boolean getPub() {
		return pub;
	}

	public void setPub(boolean pub) {
		this.pub = pub;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Notice [id=" + id + ", title=" + title + ", regDate=" + regDate + ", writer_id=" + writer_id + ", hit="
				+ hit + ", files=" + files + ", content=" + content + ", pub=" + pub + "]";
	}

}
