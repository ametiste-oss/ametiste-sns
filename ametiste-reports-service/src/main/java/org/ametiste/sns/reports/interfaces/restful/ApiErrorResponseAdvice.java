package org.ametiste.sns.reports.interfaces.restful;

import org.ametiste.ifaces.restful.ErrorUtils;
import org.ametiste.ifaces.restful.RequestIdHolder;
import org.ametiste.sns.reports.interfaces.api.NoReportsError;
import org.ametiste.sns.reports.interfaces.api.ReportNotFoundError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * Advice contains mapping rules between an application exception and
 * error response that defined by protocol specification.
 *
 * @author masted
 * @since 0.1.0
 */
@ControllerAdvice
public class ApiErrorResponseAdvice {
	
	@Autowired
	private RequestIdHolder requestIdHolder;

	@ExceptionHandler(value = ReportNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ModelAndView reportNotFoundExceptionHandler(ReportNotFoundException e) {
		return ErrorUtils.toModelAndView(requestIdHolder.getRequestId(),
				ReportNotFoundError.createError(e.getReportId()));
	}

	@ExceptionHandler(value = NoReportsException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ModelAndView noReportsExceptionHandler(NoReportsException e) {
		return ErrorUtils.toModelAndView(requestIdHolder.getRequestId(), NoReportsError.createError());
	}

	
}
