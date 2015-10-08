package org.ametiste.sns.reports.interfaces.api;

import org.ametiste.ifaces.api.error.AbstractApiError;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReportNotFoundError extends AbstractApiError {
	
	public static final String CODE = "ReportNotFound";
	
    public static final String REPORT_ID_FIELD = "report_id";
    
    public static String MESSAGE_TEMPLATE = "{Report {report_id} not found.}";
    
    private ReportNotFoundError(final Map<String, String> properties) {
        super(CODE, MESSAGE_TEMPLATE, properties);
    }
    
    public static ReportNotFoundError createError(final UUID reportId) {
        
        final HashMap<String, String> properties = new HashMap<String, String>(1);
        properties.put(REPORT_ID_FIELD, reportId.toString());
        
        return new ReportNotFoundError(properties);
    }
}