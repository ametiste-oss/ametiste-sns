package org.ametiste.sns.reports.model.specification;

import org.ametiste.sns.reports.model.Report;
import org.ametiste.shared.specification.domain.Specification;

/**
 * <p>
 *     Declares primitive types of specifications, this enum can be used by repository implementations
 *     to optimize or provide specific implementations for this specification kinds.
 * </p>
 *
 * @since
 */
public enum PrimitiveSpecification implements Specification<Report> {

    /**
     *  <p>
     *      This specification match any given report.
     *  </p>
     */
    ALL {
        @Override
        public boolean isSatisfiedBy(Report object) {
            return true;
        }
    }

}
