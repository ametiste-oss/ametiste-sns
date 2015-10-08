package org.ametiste.sns.reports.interfaces.facade.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class ReportViewDTO implements Serializable {

	private static final long serialVersionUID = 1049635502099387634L;

	private UUID id;

	private Date date;

	private String type;

	private String sender;

	private HashMap<String, Object> content;
	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public ReportViewDTO() {

	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDate() {
		return formatter.format(date);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public HashMap<String, Object> getContent() {
		return content;
	}

	public void setContent(HashMap<String, Object> content) {
		this.content = content;
	}

}
