package org.ametiste.sns.reports.model;

// TODO: need "equals" and "hasCode" method implementations
public class TimeInterval {

	private final long startTime;
	private final long endTime;

	public TimeInterval(long start, long end) {
		startTime = start;
		endTime = end;
	}

	public TimeInterval(long start) {
		startTime = start;
		endTime = startTime + 86400000;
	}

	public long getEndTime() {
		return endTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public boolean containsTime(long time) {
		return (time >= startTime && time < endTime);
	}

}
