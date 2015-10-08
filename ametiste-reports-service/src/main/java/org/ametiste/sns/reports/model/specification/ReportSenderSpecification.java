package org.ametiste.sns.reports.model.specification;

import org.ametiste.sns.reports.infrastructure.ReportSpecificationTranslator;
import org.ametiste.sns.reports.model.Report;
import org.ametiste.shared.specification.domain.Specification;
import org.ametiste.shared.specification.infrastructure.SpecificationTranslationSupport;

public class ReportSenderSpecification implements Specification<Report>,
		SpecificationTranslationSupport<ReportSpecificationTranslator> {

	private final String sender;

	public ReportSenderSpecification(String sender) {
		this.sender = sender;
	}

	@Override
	public boolean isSatisfiedBy(Report object) {
		return object.getSender().equals(sender);
	}

	@Override
	public void setSpecificationData(ReportSpecificationTranslator translator) {
		translator.addSenderQuery(sender);
	}

}
