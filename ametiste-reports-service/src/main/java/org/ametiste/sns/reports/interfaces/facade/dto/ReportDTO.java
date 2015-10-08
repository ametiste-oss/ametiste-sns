package org.ametiste.sns.reports.interfaces.facade.dto;

import org.ametiste.sns.reports.model.Report;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class ReportDTO implements Serializable {

	private static final long serialVersionUID = 1049635502099387634L;

	private UUID id;

    private String persistenceStrategy = Report.TRANSIENT_REPORT;

    private Date date;

	private String type;

	private String sender;

	private HashMap<String, Object> content;

	public ReportDTO(UUID reportId, String persistenceStrategy, Date date, String type, String sender, HashMap<String, Object> report) {
		this.id = reportId;
        this.persistenceStrategy = persistenceStrategy;
        this.date = date;
		this.type = type;
		this.sender = sender;
		this.content = report;
	}

	public ReportDTO() {

	}

    public String getPersistenceStrategy() {
        return persistenceStrategy;
    }

    public void setPersistenceStrategy(String persistenceStrategy) {
        this.persistenceStrategy = persistenceStrategy;
    }

    public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
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
