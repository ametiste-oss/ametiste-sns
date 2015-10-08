package org.ametiste.sns.reports.model;

import org.ametiste.shared.specification.domain.Specification;

import java.util.List;
import java.util.UUID;

// TODO: split this repository into two, for read-model and for the domain model
public interface ReportRepository {

	void addReport(Report report);

	Report getReportById(UUID id);

	boolean reportExists(UUID id);

	void updateReport(Report report);

    /**
     * <p>
     *     Return list of reports satisfied by the given criteria, ordered by report creation date in DESC order.
     * </p>
     *
     * <p>
     *     IMPLEMENTERS NOTE: this method MUST do order a list by report creation date in DESC order.
     * </p>
     *
     */
	List<Report> getReportBySpecification(Specification<Report> specification, long offset, int size);

	int getCountBySpecification(Specification<Report> specification);

}
