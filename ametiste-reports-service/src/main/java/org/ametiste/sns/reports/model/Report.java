package org.ametiste.sns.reports.model;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class Report {

    public static final String TRANSIENT_REPORT = "TRINSIENT";

    public static final String PERSISTED_REPORT = "PERSISTED";

    public static final String TEMPORARY_REPORT = "TEMPORARY";

	private final UUID id;

    private final String persistencyStrategy;

	private final Date date;

	private final String type;

	private final String sender;

	private final HashMap<String, Object> content;

	public Report(UUID id, Date date, String type, String sender, HashMap<String, Object> content) {
        this(id, TRANSIENT_REPORT, date, type, sender, content);
	}

    public Report(UUID id, String persistencyStrategy, Date date, String type, String sender, HashMap<String, Object> content) {
        super();
        this.id = id;
        this.date = date;
        this.type = type;
        this.sender = sender;
        this.content = content;
        this.persistencyStrategy = persistencyStrategy;
    }

	public UUID getId() {
		return id;
	}

    public String getPersistencyStrategy() {
        return persistencyStrategy;
    }

    public Date getDate() {
		return date;
	}

	public String getType() {
		return type;
	}

	public String getSender() {
		return sender;
	}

	public HashMap<String, Object> getContent() {
		return content;
	}

}
