package org.ametiste.sns.reports.interfaces.facade.internal;

import org.ametiste.sns.reports.application.ReportService;
import org.ametiste.sns.reports.interfaces.facade.ReportFacade;
import org.ametiste.sns.reports.model.Report;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class InternalReportFacade implements ReportFacade {

	private ReportService reportService;

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ReportFacade#storeReport(java.util.UUID, java.lang.String,
	 * java.lang.String, java.util.Date, java.util.HashMap)
	 */
	@Override
	public void storeReport(UUID reportId, String persistenceStrategy, String type, String sender, Date date, HashMap<String, Object> content) {

		// TODO: An one or more test cases from the integration test suite 
		// depends on this throw. Remove it and find a way to do 
		// those test cases by an another way.
		if (type.equals("NOT_A_SIMPLE_REPORT"))
			throw new IllegalStateException("FOR TEST ONLY, NOT A SIMPLE REPORT :E");

		reportService.storeReport(new Report(reportId, persistenceStrategy, date, type, sender, content));

	}

}
