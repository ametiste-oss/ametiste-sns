package org.ametiste.sns.reports.infrastructure.persistence.tnsnt;

import org.ametiste.sns.reports.model.specification.ReportLifetimeExceedSpecification;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * <p>
 *     Controller that takes control on transient reports.
 *     Cleaning up transient repository from old reports.
 * </p>
 *
 * @since
 */
public class TransientReportController {

    private final TransientReportRepository reportRepository;

    private final long startDelay;

    private final long period;

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    TransientReportController(TransientReportRepository reportRepository, long startDelay, long period) {
        this.reportRepository = reportRepository;
        this.startDelay = startDelay;
        this.period = period;
    }

    public void setupRepositoryCleanups() {
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                reportRepository.deleteBySpecification(new ReportLifetimeExceedSpecification(
                        TimeUnit.MILLISECONDS.convert(60, TimeUnit.SECONDS)));
            }
        }, startDelay, period, TimeUnit.SECONDS);
    }

    public void shootDown() {
        executor.shutdown();
    }

}
