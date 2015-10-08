package org.ametiste.sns.reports.interfaces.facade.dto;

public class ReportCriteriaDTO {

	private String sender;
	private String type;
	private String fromTime;
	private String toTime;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String from) {
		this.fromTime = from;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String to) {
		this.toTime = to;
	}

}
