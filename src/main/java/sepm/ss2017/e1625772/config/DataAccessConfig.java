package sepm.ss2017.e1625772.config;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Configuration
// @ComponentScan(basePackages = {"sepm.ss2017.e1625772.persistence.jdbc"})
public class DataAccessConfig {
    @Bean
    public DataSource dataSource() {
        // OPTIONAL: Load from config file
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:tcp://localhost/~/boxes");
        ds.setUser("sa");
        ds.setPassword("");
        return ds;
    }
}
