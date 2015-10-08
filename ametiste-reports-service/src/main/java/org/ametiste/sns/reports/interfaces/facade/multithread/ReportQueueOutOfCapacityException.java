package org.ametiste.sns.reports.interfaces.facade.multithread;

public class ReportQueueOutOfCapacityException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8458214159925128415L;

	public ReportQueueOutOfCapacityException(String message, Exception e) {
		super(message, e);
	}

}
