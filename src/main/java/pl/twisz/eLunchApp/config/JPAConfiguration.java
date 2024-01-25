package pl.twisz.eLunchApp.config;

import com.google.common.collect.ImmutableMap;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories("pl.twisz.eLunchApp.repo")
@EnableTransactionManagement
public class JPAConfiguration {
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/elunchapp?useUnicode=true&characterEncoding=utf8&" +
                "useSSL=false&useLegacyDatetimeCode=false&serverTimeZone=UTC&allowPublicKeyRetrieval=true");
        dataSourceBuilder.username("twisz");
        dataSourceBuilder.password("elunchapp");
        return dataSourceBuilder.build();
    }

    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect");
        return adapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                       HibernateJpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactory.setPackagesToScan(
                "pl.twisz.eLunchApp.model",
                "pl.twisz.eLunchApp.converter"
        );
        entityManagerFactory.setJpaPropertyMap(
                ImmutableMap.of(
//                        AvailableSettings.DIALECT, "org.hibernate.dialect.MySQLDialect",
//                        AvailableSettings.SHOW_SQL, "true",
                        AvailableSettings.HBM2DDL_AUTO, "update"
                )
        );

        return entityManagerFactory;
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager platformTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
