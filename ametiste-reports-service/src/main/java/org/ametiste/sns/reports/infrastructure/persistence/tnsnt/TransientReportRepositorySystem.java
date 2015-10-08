package org.ametiste.sns.reports.infrastructure.persistence.tnsnt;

import java.util.ArrayList;

/**
 *
 * @since
 */
public class TransientReportRepositorySystem implements TransientReportRepositoryFactory {

    private ArrayList<TransientReportController> controllers = new ArrayList<TransientReportController>();

    private final TransientReportRepositoryFactory concreteRepositoryFactory;

    public TransientReportRepositorySystem(TransientReportRepositoryFactory concreteRepositoryFactory) {
        this.concreteRepositoryFactory = concreteRepositoryFactory;
    }

    @Override
    public TransientReportRepository createRepository() {

        final TransientReportRepository repository = concreteRepositoryFactory.createRepository();
        final TransientReportController controller = new TransientReportController(repository, 10, 30);
        controller.setupRepositoryCleanups();
        controllers.add(controller);

        return repository;
    }

    public void startUp() {
        for (TransientReportController controller : controllers) {
            controller.setupRepositoryCleanups();
        }
    }

    public void shootDown() {
        for (TransientReportController controller : controllers) {
            controller.shootDown();
        }
    }

}
