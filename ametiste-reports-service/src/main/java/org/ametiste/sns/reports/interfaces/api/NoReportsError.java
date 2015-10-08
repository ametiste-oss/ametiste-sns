package org.ametiste.sns.reports.interfaces.api;

import org.ametiste.ifaces.api.error.AbstractApiError;

import java.util.Collections;
import java.util.Map;

public class NoReportsError extends AbstractApiError {
	
	public static final String CODE = "NoReportsError";
	
    public static String MESSAGE_TEMPLATE = "Reports not found.";
    
    private NoReportsError(final Map<String, String> properties) {
        super(CODE, MESSAGE_TEMPLATE, properties);
    }
    
    public static NoReportsError createError() {
        return new NoReportsError(Collections.<String,String>emptyMap());
    }
}