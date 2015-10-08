package org.ametiste.sns.reports.interfaces.facade.multithread;

import org.ametiste.sns.reports.interfaces.facade.ReportFacade;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class DelayReportFacade implements ReportFacade {
	
	private long delay = 1000L;
	
	private final ReportFacade decoratedFacade;

	public DelayReportFacade(ReportFacade decoratedFacade, long delay) {
		this.decoratedFacade = decoratedFacade;
		this.delay = delay;
	}
	
	public DelayReportFacade(ReportFacade decoratedFacade) {
		this(decoratedFacade, 1000L);
	}
	
	@Override
	public void storeReport(UUID reportId, String persistenceStrategy, String type, String sender,
                            Date date, HashMap<String, Object> report) {
		
		decoratedFacade.storeReport(reportId, persistenceStrategy, type, sender, date, report);
		
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			
		}
	}

}
