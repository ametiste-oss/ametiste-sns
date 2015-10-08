package org.ametiste.sns.reports.model.specification;

import org.ametiste.sns.reports.model.Report;
import org.ametiste.shared.specification.domain.Specification;

/**
 *
 * <p>
 *     Specify reports that lifetime is exceed the defined period in millis.
 * </p>
 *
 * <p>
 *     By default the start point to calculate is time of the specification creation.
 *     Take care to do not cache instances of this specification.
 * </p>
 *
 * <p>
 *     If you need more accurate time calculating, or you need calculate lifetime in past or feature
 *     you can use the particular constructor.
 * </p>
 *
 * @since
 */
public class ReportLifetimeExceedSpecification implements Specification<Report> {

    private final long lifeTime;

    private final long startPoint;

    public ReportLifetimeExceedSpecification(long lifeTime) {
        this(System.currentTimeMillis(), lifeTime);
    }

    public ReportLifetimeExceedSpecification(long startPoint, long lifeTime) {
        this.lifeTime = lifeTime;
        this.startPoint = startPoint;
    }

    @Override
    public boolean isSatisfiedBy(Report report) {
        return  startPoint >= (report.getDate().getTime() + lifeTime);
    }

}