package org.ametiste.sns.reports.model.specification;

import org.ametiste.sns.reports.infrastructure.ReportSpecificationTranslator;
import org.ametiste.sns.reports.model.Report;
import org.ametiste.sns.reports.model.TimeInterval;
import org.ametiste.shared.specification.domain.Specification;
import org.ametiste.shared.specification.infrastructure.SpecificationTranslationSupport;

public class ReportDateSpecification implements Specification<Report>,
		SpecificationTranslationSupport<ReportSpecificationTranslator> {

	private final TimeInterval timeInterval;

	public ReportDateSpecification(TimeInterval interval) {
		this.timeInterval = interval;
	}

	@Override
	public boolean isSatisfiedBy(Report object) {
		return timeInterval.containsTime(object.getDate().getTime());
	}

	@Override
	public void setSpecificationData(ReportSpecificationTranslator object) {
		object.addDateQuery(timeInterval);
	}

}
