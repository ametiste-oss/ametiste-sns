package org.ametiste.sns.reports.interfaces.facade;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import org.ametiste.sns.reports.model.TimeInterval;

public class TimeIntervalBuilder {

	private final List<DateTimeFormatter> formaters;

	public TimeIntervalBuilder(List<String> formatsList) {
		if (formatsList == null || formatsList.isEmpty())
			throw new IllegalArgumentException("List of used date formats cant be null");

		formaters = new ArrayList<DateTimeFormatter>();
		for (String format : formatsList) {
			formaters.add(DateTimeFormat.forPattern(format));
		}
	}

	public TimeInterval buildTimeInterval(String start, String end) {
		if (end == null) {
			return new TimeInterval(this.parseTime(start));
		} else {
			return new TimeInterval(this.parseTime(start), this.parseTime(end));
		}

	}

	private long parseTime(String time) {
		Iterator<DateTimeFormatter> iterator = formaters.iterator();

		while (iterator.hasNext()) {

			try {
				return iterator.next().parseMillis(time);
			} catch (IllegalArgumentException e) {

			}
		}
		throw new IllegalArgumentException("Date format is wrong and cant be parsed. Use one of configured formats");
		// TODO: was hot-fixed after merge 
		// creating of SimpleDateFormat need to be moved to separate method
	}

}
