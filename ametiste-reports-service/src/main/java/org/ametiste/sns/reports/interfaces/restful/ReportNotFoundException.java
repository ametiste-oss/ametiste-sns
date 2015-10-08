package org.ametiste.sns.reports.interfaces.restful;

import java.util.UUID;

public class ReportNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1697839156762381724L;
	
	private final UUID reportId;

	public ReportNotFoundException(String message, UUID reportId) {
		super(message);
		
		this.reportId = reportId;
	}

	public UUID getReportId() {
		return reportId;
	}
	
}
