package org.ametiste.sns.reports.interfaces.facade;

import org.ametiste.sns.reports.interfaces.facade.dto.ReportCriteriaDTO;
import org.ametiste.sns.reports.model.Report;
import org.ametiste.sns.reports.model.TimeInterval;
import org.ametiste.sns.reports.model.specification.ReportDateSpecification;
import org.ametiste.sns.reports.model.specification.ReportSenderSpecification;
import org.ametiste.sns.reports.model.specification.ReportTypeSpecification;
import org.ametiste.shared.specification.domain.CompositeSpecification;

public class ReportFilterBuilder {

	private final TimeIntervalBuilder timeBuilder;

	public ReportFilterBuilder(TimeIntervalBuilder timeBuilder) {
		this.timeBuilder = timeBuilder;
	}

	public CompositeSpecification<Report> createSpecification(ReportCriteriaDTO criterias) {
		CompositeSpecification<Report> specification = new CompositeSpecification<Report>();
		setDataFilter(specification, criterias.getFromTime(), criterias.getToTime());
		setSenderFilter(specification, criterias.getSender());
		setTypeFilter(specification, criterias.getType());
		return specification;
	}

	private void setDataFilter(CompositeSpecification<Report> specification, String fromTime, String toTime) {
		if (fromTime != null && !fromTime.isEmpty()) {
			TimeInterval interval = timeBuilder.buildTimeInterval(fromTime, toTime);
			specification.addCriteria(new ReportDateSpecification(interval));
		} else {
			if (toTime != null && !toTime.isEmpty())
				throw new IllegalArgumentException("You cant set end of interval without setting start of it");
		}

	}

	private void setSenderFilter(CompositeSpecification<Report> specification, String sender) {
		if (sender != null && !sender.isEmpty()) {
			specification.addCriteria(new ReportSenderSpecification(sender));
		}
	}

	private void setTypeFilter(CompositeSpecification<Report> specification, String type) {
		if (type != null && !type.isEmpty()) {
			specification.addCriteria(new ReportTypeSpecification(type));
		}

	}
}
