package org.ametiste.sns.reports.infrastructure.persistence.tnsnt;

import org.ametiste.metrics.annotations.Chronable;
import org.ametiste.metrics.annotations.Timeable;
import org.ametiste.shared.specification.domain.Specification;
import org.ametiste.sns.reports.model.Report;
import org.ametiste.sns.reports.model.ReportRepository;
import org.ametiste.sns.reports.model.specification.PrimitiveSpecification;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @since
 */
public class TransientReportRepository implements ReportRepository {

    private ConcurrentHashMap<UUID, Report> transientReports = new ConcurrentHashMap();

    @Override
    public void addReport(Report report) {
        // TODO: add repo size limit check
        transientReports.putIfAbsent(report.getId(), report);
    }

    @Override
    public Report getReportById(UUID id) {
        return transientReports.get(id);
    }

    @Override
    public boolean reportExists(UUID id) {
        return transientReports.containsKey(id);
    }

    @Override
    public void updateReport(Report report) {
        transientReports.put(report.getId(), report);
    }

    @Override
    public List<Report> getReportBySpecification(Specification<Report> specification, long offset, int size) {

        List<Report> reports = matchReports(specification);

        if (reports.size() == 0) {
            return reports;
        }

        if (reports.size() > size) {
            reports =  reports.subList(0, size-1);
        }

        Collections.sort(reports, new Comparator<Report>() {
            @Override
            public int compare(Report o1, Report o2) {
                long t1 = o1.getDate().getTime();
                long t2 = o2.getDate().getTime();
                // NOTE: DESC order implemented
                return t1 == t2 ? 0 : (t1 < t2 ? 1 : -1);
            }
        });

        return reports;

    }

    @Override
    public int getCountBySpecification(Specification<Report> specification) {

        // NOTE: optimization for the primitive specification type
        if (specification == PrimitiveSpecification.ALL) {
            return transientReports.size();
        }

        // TODO: can be done by counter only, without temporary array creation
        return matchReports(specification).size();
    }

    private ArrayList<Report> matchReports(Specification<Report> specification) {
        final ArrayList<Report> reports = new ArrayList<Report>();

        for (Report report : transientReports.values()) {
            if (specification.isSatisfiedBy(report)) {
                reports.add(report);
            }
        }
        return reports;
    }

    @Timeable(name="repositories.transient.delete-by-spec.timing")
    @Chronable(value="result", name="repositories.transient.delete-by-spec.deletion-count")
    public int deleteBySpecification(Specification<Report> reportSpecification) {

        final ArrayList<Report> matchedReports = matchReports(reportSpecification);

        for (Report matchedReport : matchedReports) {
            transientReports.remove(matchedReport.getId());
        }

        return matchedReports.size();

    }
}
