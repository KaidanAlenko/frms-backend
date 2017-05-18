package hr.eestec_zg.frmsbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

@Configuration(value = "backendDataSourceConfig")
public class DataSourceConfig {

    @Profile("production")
    public static class ProductionDataSourceConfig {
        @Bean
        public DataSource dataSource() {
            return new JndiDataSourceLookup().getDataSource("jdbc/frmsbackend");
        }
    }
}
