package org.ametiste.sns.reports.interfaces.facade.internal;

import org.ametiste.sns.reports.interfaces.facade.ReportReader;
import org.ametiste.sns.reports.interfaces.facade.dto.ReportReplyDTO;
import org.ametiste.sns.reports.interfaces.facade.dto.ReportViewDTO;
import org.ametiste.sns.reports.interfaces.facade.internal.assembler.ReportDTOAssembler;
import org.ametiste.sns.reports.model.Report;
import org.ametiste.sns.reports.model.ReportRepository;
import org.ametiste.shared.specification.domain.Specification;

import java.util.List;
import java.util.UUID;

public class InternalReportReader implements ReportReader {

    private ReportRepository reportRepository;

	private ReportDTOAssembler dtoAssembler;

	private final int MAX_REPORTS_SIZE = 1000;


    public void setReportRepository(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

	public void setReportDTOAssembler(ReportDTOAssembler dtoAssembler) {
		this.dtoAssembler = dtoAssembler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ReportFacade#getReportById(java.util.UUID)
	 */
	@Override
	public ReportViewDTO getReportById(UUID reportId) {

        // NOTE: dunno how to do this logic right now
        // I need to check transient repo than if no transient reports go to persistent.

        Report report = reportRepository.getReportById(reportId);

		// TODO: should be fixed after repository will do throw exceptions
		if (report != null) {
			return dtoAssembler.assembleDTO(report);
		} else {
			return null;
		}
	}

	@Override
	public ReportReplyDTO getReportsBySpecification(String persistenceStrategy, Specification<Report> specification, long offset, int size) {

        final ReportRepository toUse;

        if (persistenceStrategy.equals("transient")) {
            toUse = reportRepository;
        } else {
            throw new IllegalArgumentException("Unsupported persistence strategy: " + persistenceStrategy);
        }

		if (size <= 0 || size > MAX_REPORTS_SIZE)
			throw new IllegalArgumentException("Size cant be negative or more then " + MAX_REPORTS_SIZE);
		if (offset < 0)
			throw new IllegalArgumentException("Offset cant have negative value");

		long count = toUse.getCountBySpecification(specification);

		ReportReplyDTO dto = new ReportReplyDTO();
		List<Report> reportBySpecification = toUse.getReportBySpecification(specification, offset, size);
		dto.setReports(dtoAssembler.assembleDTOList(reportBySpecification));
		dto.setOffset(offset);
		dto.setSize(size);
		dto.setIsTruncated(size + offset < count);

		return dto;
	}

}
