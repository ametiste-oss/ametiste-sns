package org.ametiste.sns.reports.application;

import org.ametiste.sns.reports.model.Report;
import org.ametiste.sns.reports.model.ReportRepository;

public class SimpleReportService implements ReportService {

    private final ReportRepository reportRepository;

    public SimpleReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

	@Override
	public void storeReport(Report report) {
        reportRepository.addReport(report);
    }

}
