package hr.eestec_zg.frmsbackend.config;

import hr.eestec_zg.frmsbackend.utilities.FrmsNamingStrategy;
import hr.eestec_zg.frmsbackend.utilities.Util;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.BATCH_VERSIONED_DATA;
import static org.hibernate.cfg.AvailableSettings.MAX_FETCH_DEPTH;
import static org.hibernate.cfg.AvailableSettings.ORDER_INSERTS;
import static org.hibernate.cfg.AvailableSettings.ORDER_UPDATES;
import static org.hibernate.cfg.AvailableSettings.STATEMENT_BATCH_SIZE;
import static org.hibernate.cfg.AvailableSettings.STATEMENT_FETCH_SIZE;
import static org.hibernate.cfg.AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS;
import static org.hibernate.cfg.AvailableSettings.USE_SQL_COMMENTS;
import static org.hibernate.cfg.AvailableSettings.USE_STREAMS_FOR_BINARY;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class DataSourceConfig implements TransactionManagementConfigurer {

    private final DataSource dataSource;
    private final Properties hibernateProperties;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public DataSourceConfig(DataSource dataSource, Properties hibernateProperties) {
        this.dataSource = dataSource;
        this.hibernateProperties = hibernateProperties;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        final LocalSessionFactoryBean b = new LocalSessionFactoryBean();
        b.setPackagesToScan("hr.eestec_zg.frmsbackend");
        b.setDataSource(dataSource);
        b.setPhysicalNamingStrategy(new FrmsNamingStrategy());
        b.setHibernateProperties(hibernateProperties);
        return b;
    }

    @Bean
    public PlatformTransactionManager hibernateTxManager() {
        return new HibernateTransactionManager(sessionFactory().getObject());
    }



    @Bean
    public TransactionTemplate txTemplate() {
        return new TransactionTemplate(hibernateTxManager());
    }

    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return hibernateTxManager();
    }

    @Profile("production")
    public static class ProductionDataSourceConfig {
        @Bean
        public DataSource dataSource() {
            return new JndiDataSourceLookup().getDataSource("jdbc/frmsbackend");
        }

        @Bean
        public Properties hibernateProperties() {
            return Util.props(
                    USE_NEW_ID_GENERATOR_MAPPINGS, "true",
                    ORDER_INSERTS, "true",
                    ORDER_UPDATES, "true",
                    MAX_FETCH_DEPTH, "0",
                    STATEMENT_FETCH_SIZE, "200",
                    STATEMENT_BATCH_SIZE, "50",
                    BATCH_VERSIONED_DATA, "true",
                    USE_STREAMS_FOR_BINARY, "true",
                    USE_SQL_COMMENTS, "true"
            );
        }
    }
}
