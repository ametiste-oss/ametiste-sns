package org.ametiste.sns.reports.interfaces.facade;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public interface ReportFacade {

	void storeReport(UUID reportId, String persistenceStrategy, String type, String sender, Date date, HashMap<String, Object> report);

}