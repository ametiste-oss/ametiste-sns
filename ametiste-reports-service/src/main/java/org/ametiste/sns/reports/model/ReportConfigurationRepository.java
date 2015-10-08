package org.ametiste.sns.reports.model;

import java.util.List;

/**
 *
 * @since
 */
public interface ReportConfigurationRepository {

    ReportConfiguration loadSenderConfiguration(String sender);

    void saveReportConfiguration(ReportConfiguration reportConfiguration);

    List<ReportConfiguration> loadReportConfigurations();
}
