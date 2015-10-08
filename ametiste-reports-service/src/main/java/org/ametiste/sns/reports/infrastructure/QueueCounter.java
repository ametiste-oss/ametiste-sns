package org.ametiste.sns.reports.infrastructure;

import org.ametiste.metrics.MetricsService;

import java.util.concurrent.BlockingQueue;


// TODO: I want to refactor it as generic solution based on GTE tasks
public class QueueCounter implements Runnable {

	private final BlockingQueue<?> queue;
	private final MetricsService service;
	private final String name;

	public QueueCounter(BlockingQueue<?> queue, MetricsService service, String name) {
		this.queue = queue;
		this.service = service;
		this.name = name;
	}

	@Override
	public void run() {
		service.createEvent(name, queue.size());
	}

}
