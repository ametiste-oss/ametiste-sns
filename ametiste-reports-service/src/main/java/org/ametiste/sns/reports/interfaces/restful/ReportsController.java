package org.ametiste.sns.reports.interfaces.restful;

import org.ametiste.sns.reports.interfaces.facade.ReportFacade;
import org.ametiste.sns.reports.interfaces.facade.ReportFilterBuilder;
import org.ametiste.sns.reports.interfaces.facade.ReportReader;
import org.ametiste.sns.reports.interfaces.facade.dto.ReportCriteriaDTO;
import org.ametiste.sns.reports.interfaces.facade.dto.ReportDTO;
import org.ametiste.sns.reports.interfaces.facade.dto.ReportReplyDTO;
import org.ametiste.sns.reports.interfaces.facade.dto.ReportViewDTO;
import org.ametiste.sns.reports.model.Report;
import org.ametiste.shared.specification.domain.CompositeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/reports")
public class ReportsController {

	@Autowired
	private ReportReader reportReader;

	@Autowired
	@Qualifier("activeFacade")
	private ReportFacade reportFacade;

	@Autowired
	private ReportFilterBuilder builder;
	
	@RequestMapping(value = "/{reportId}", method = RequestMethod.PUT)
	public Object putCommonReport(@RequestBody ReportDTO reportDTO, @PathVariable(value = "reportId") UUID reportId) {

		// TODO: what should we do when reportId and id in dto is not equal?
		// most likely we need to delete id from body

		reportFacade.storeReport(reportId,
                reportDTO.getPersistenceStrategy(),
                reportDTO.getType(),
                reportDTO.getSender(),
                reportDTO.getDate(),
				reportDTO.getContent());

		return new ModelAndView("reportCreated");
	}

	@RequestMapping(value = "/{reportId}", method = RequestMethod.GET)
	@ResponseBody
	public Object getReport(@PathVariable(value = "reportId") UUID reportId) {

		ReportViewDTO report = reportReader.getReportById(reportId);

		if (report != null)
			return report;
		else
			throw new ReportNotFoundException("Report with the given id '" + reportId.toString() + "' not found",
					reportId);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Object searchReport(@RequestParam(value = "from", required = false) String date,
			@RequestParam(value = "to", required = false) String endDate,
			@RequestParam(value = "sender", required = false) String sender,
			@RequestParam(value = "type", required = false) String reportType,
			@RequestParam(value = "size", defaultValue = "100") int limit,
			@RequestParam(value = "offset", defaultValue = "0") long offset) {
        return doReportsSearch("transient", date, endDate, sender, reportType, limit, offset);

	}

    @RequestMapping(value="/persisted", method = RequestMethod.GET)
    @ResponseBody
    public Object searchPersistentReport(@RequestParam(value = "from", required = false) String date,
                               @RequestParam(value = "to", required = false) String endDate,
                               @RequestParam(value = "sender", required = false) String sender,
                               @RequestParam(value = "type", required = false) String reportType,
                               @RequestParam(value = "size", defaultValue = "100") int limit,
                               @RequestParam(value = "offset", defaultValue = "0") long offset) {
        return doReportsSearch("persisted", date, endDate, sender, reportType, limit, offset);

    }

    @RequestMapping(value="/transient", method = RequestMethod.GET)
    @ResponseBody
    public Object searchTransientReport(@RequestParam(value = "from", required = false) String date,
                                         @RequestParam(value = "to", required = false) String endDate,
                                         @RequestParam(value = "sender", required = false) String sender,
                                         @RequestParam(value = "type", required = false) String reportType,
                                         @RequestParam(value = "size", defaultValue = "100") int limit,
                                         @RequestParam(value = "offset", defaultValue = "0") long offset) {
        return doReportsSearch("transient", date, endDate, sender, reportType, limit, offset);

    }

    private Object doReportsSearch(String persistenceStrategy,
                                   String date, String endDate,
                                   String sender, String reportType, int limit, long offset) {
        //TODO replace this after spring validation will be explained

        ReportCriteriaDTO parameters = new ReportCriteriaDTO();
        parameters.setSender(sender);
        parameters.setType(reportType);
        parameters.setFromTime(date);
        parameters.setToTime(endDate);

        CompositeSpecification<Report> filter = builder.createSpecification(parameters);
        ReportReplyDTO dto = reportReader.getReportsBySpecification(persistenceStrategy, filter, offset, limit);

        if (!dto.getReports().isEmpty())
            return dto;
        else
            throw new NoReportsException("Items for this filter are not found");
    }

}
