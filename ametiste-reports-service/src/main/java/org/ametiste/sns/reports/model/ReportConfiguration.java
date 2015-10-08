package org.ametiste.sns.reports.model;

/**
 *
 * @since
 */
public class ReportConfiguration {

    public static final ReportConfiguration EMPTY = new ReportConfiguration("_______", "_______");

    private final String reportSender;

    private final String persistencyStrategy;

    public ReportConfiguration(String reportSender, String persistencyStrategy) {
        this.reportSender = reportSender;
        this.persistencyStrategy = persistencyStrategy;
    }

    public String getReportSender() {
        return reportSender;
    }

    public String getPersistencyStrategy() {
        return persistencyStrategy;
    }

}
