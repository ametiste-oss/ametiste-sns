package org.ametiste.sns.reports.interfaces.facade;

import org.ametiste.sns.reports.interfaces.facade.dto.ReportReplyDTO;
import org.ametiste.sns.reports.interfaces.facade.dto.ReportViewDTO;
import org.ametiste.sns.reports.model.Report;
import org.ametiste.shared.specification.domain.Specification;

import java.util.UUID;

public interface ReportReader {

	ReportViewDTO getReportById(UUID reportId);

	ReportReplyDTO getReportsBySpecification(String persistenceStrategy,
											 Specification<Report> specification, long offset, int size);

}
