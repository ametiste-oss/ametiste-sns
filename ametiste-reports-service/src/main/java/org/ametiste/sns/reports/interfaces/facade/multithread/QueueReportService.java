package org.ametiste.sns.reports.interfaces.facade.multithread;

import org.ametiste.sns.reports.interfaces.facade.ReportFacade;
import org.ametiste.sns.reports.interfaces.facade.dto.ReportDTO;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QueueReportService implements Runnable {

	private final BlockingQueue<ReportDTO> queue;
	private final ExecutorService pool;
	private final ReportFacade facade;

	public QueueReportService(ReportFacade facade, BlockingQueue<ReportDTO> queue, int poolSize) {
		if (facade == null)
			throw new IllegalArgumentException("Facade cant be null");
		if (queue == null)
			throw new IllegalArgumentException("Queue cant be null");

		this.facade = facade;

		this.queue = queue;
		// TODO: use a ThreadFactory implementation there to have custom thread names 
		this.pool = Executors.newFixedThreadPool(poolSize);
	}

	@Override
	public void run() {

		while (true) {
			try {
				this.pool.execute(new ReportStorer(queue.take(), facade));
			} catch (InterruptedException e) {
				return;
			}
		}

	}

}
