package org.ametiste.sns.reports.interfaces.facade.multithread;

import org.ametiste.sns.reports.interfaces.facade.ReportFacade;
import org.ametiste.sns.reports.interfaces.facade.dto.ReportDTO;

public class ReportStorer implements Runnable {

	private final ReportDTO report;
	private final ReportFacade facade;

	public ReportStorer(ReportDTO report, ReportFacade facade) {
		this.report = report;
		this.facade = facade;
	}

	@Override
	public void run() {
		this.facade.storeReport(
                report.getId(),
                report.getPersistenceStrategy(),
                report.getType(),
                report.getSender(),
                report.getDate(),
				report.getContent()
        );
	}

}
