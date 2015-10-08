package org.ametiste.sns.reports.interfaces.spring;

import org.ametiste.sns.reports.infrastructure.persistence.tnsnt.TransientReportRepository;
import org.ametiste.sns.reports.infrastructure.persistence.tnsnt.TransientReportRepositoryFactory;

/**
 *
 * <p>
 *     This factory usually used to provide access to
 *     repository created by external factory inside to repository system.
 * </p>
 *
 * <p>
 *     For example, this factory can be used to provide access to repository created within spring-context. It
 *     may be helpful if you want to use some spring advantages, like AOP, TX, Security or something else.
 * </p>
 *
 * @since
 */
public class TransientRepositoryProxyFactory implements TransientReportRepositoryFactory {

    private final TransientReportRepository reportRepository;

    public TransientRepositoryProxyFactory(TransientReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public TransientReportRepository createRepository() {
        return reportRepository;
    }

}
