package org.ametiste.sns.reports.interfaces.facade.multithread;

import org.ametiste.sns.reports.interfaces.facade.ReportFacade;
import org.ametiste.sns.reports.interfaces.facade.dto.ReportDTO;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class QueueReportFacade implements ReportFacade {

	private final BlockingQueue<ReportDTO> queue;

	public QueueReportFacade(BlockingQueue<ReportDTO> queue) {
		if (queue == null)
			throw new IllegalArgumentException("Queue cant be null");
		this.queue = queue;

	}

	@Override
	public void storeReport(UUID reportId, String persistenceStrategy, String type, String sender, Date date, HashMap<String, Object> report) {
		try {
			queue.add(new ReportDTO(reportId, persistenceStrategy, date, type, sender, report));
		} catch (IllegalStateException e) {
			throw new ReportQueueOutOfCapacityException("Queue is out of capacity", e);
		}
	}
}
