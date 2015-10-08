package org.ametiste.sns.reports.model.specification;

import org.ametiste.sns.reports.infrastructure.ReportSpecificationTranslator;
import org.ametiste.sns.reports.model.Report;
import org.ametiste.shared.specification.domain.Specification;
import org.ametiste.shared.specification.infrastructure.SpecificationTranslationSupport;

public class ReportTypeSpecification implements Specification<Report>,
		SpecificationTranslationSupport<ReportSpecificationTranslator> {

	private final String type;

	public ReportTypeSpecification(String type) {
		this.type = type;
	}

	@Override
	public boolean isSatisfiedBy(Report object) {
		return object.getType().equals(type);
	}

	@Override
	public void setSpecificationData(ReportSpecificationTranslator object) {
		object.addTypeQuery(type);
	}

}
