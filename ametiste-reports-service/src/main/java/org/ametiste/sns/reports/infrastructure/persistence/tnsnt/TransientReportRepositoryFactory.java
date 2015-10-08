package org.ametiste.sns.reports.infrastructure.persistence.tnsnt;

/**
 *
 * <p>
 *     Object that provides this factory interface gives a client the right way to
 *     create {@link TransientReportRepository}
 * </p>
 *
 * <p>
 *     The factory must do cleanup process initialization and must
 *     take care about {@link TransientReportRepository}
 *     implementation dependencies.
 * </p>
 *
 * @since
 */
public interface TransientReportRepositoryFactory {

    TransientReportRepository createRepository();

}
