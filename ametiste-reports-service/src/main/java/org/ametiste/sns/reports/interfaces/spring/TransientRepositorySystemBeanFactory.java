package org.ametiste.sns.reports.interfaces.spring;

import org.ametiste.sns.reports.infrastructure.persistence.tnsnt.TransientReportRepository;
import org.ametiste.sns.reports.infrastructure.persistence.tnsnt.TransientReportRepositoryFactory;
import org.ametiste.sns.reports.infrastructure.persistence.tnsnt.TransientReportRepositorySystem;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 *
 * @since
 */
public class TransientRepositorySystemBeanFactory implements FactoryBean<TransientReportRepository>, InitializingBean, DisposableBean {

    private TransientReportRepositorySystem system;

    private TransientReportRepositoryFactory repositoryFactory = new TransientReportRepositoryFactory() {
        @Override
        public TransientReportRepository createRepository() {
            return new TransientReportRepository();
        }
    };

    public void setRepositoryFactory(TransientReportRepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    @Override
    public TransientReportRepository getObject() throws Exception {
        return system.createRepository();
    }

    @Override
    public Class<?> getObjectType() {
        return TransientReportRepository.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        system = new TransientReportRepositorySystem(repositoryFactory);
    }

    @Override
    public void destroy() throws Exception {
        system.shootDown();
    }
}
