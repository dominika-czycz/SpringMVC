package pl.coderslab.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import pl.coderslab.app.domain.repositories.BookRepository;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(basePackageClasses = BookRepository.class)
public class PersistenceConfig {
    @Bean
    public LocalEntityManagerFactoryBean entityManagerFactory() {
        LocalEntityManagerFactoryBean emf
                = new LocalEntityManagerFactoryBean();
        emf.setPersistenceUnitName("myShelfPersistenceUnit");
        return emf;
    }
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
