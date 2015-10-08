package org.ametiste.sns.reports.infrastructure;

import org.ametiste.metrics.MetricsService;
import org.ametiste.sns.reports.model.ReportRepository;
import org.ametiste.sns.reports.model.specification.PrimitiveSpecification;

/**
 *
 * @since
 */
public class RepositorySizeCounter implements Runnable {

    private final ReportRepository reportRepository;
    private final MetricsService metricsService;
    private final String metricName;

    public RepositorySizeCounter(ReportRepository reportRepository, MetricsService metricsService, String metricName) {
        this.reportRepository = reportRepository;
        this.metricsService = metricsService;
        this.metricName = metricName;
    }

    @Override
    public void run() {

        final int allReportsCount = reportRepository
                .getCountBySpecification(PrimitiveSpecification.ALL);

        metricsService.createEvent(metricName, allReportsCount);
    }

}
