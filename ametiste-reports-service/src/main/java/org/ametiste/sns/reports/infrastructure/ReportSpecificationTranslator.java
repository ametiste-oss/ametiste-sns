package org.ametiste.sns.reports.infrastructure;

import org.ametiste.sns.reports.model.TimeInterval;

public interface ReportSpecificationTranslator {

	void addDateQuery(TimeInterval interval);

	void addSenderQuery(String sender);

	void addTypeQuery(String type);

}
