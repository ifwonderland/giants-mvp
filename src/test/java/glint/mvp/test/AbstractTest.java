package glint.mvp.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.sql.DataSource;

/**
 * Created by shuang on 11/23/2014.
 */

@ContextConfiguration(locations = "classpath:service-context.xml")
public class AbstractTest extends AbstractTransactionalJUnit4SpringContextTests {


    /**
     * Set the {@code DataSource}, typically provided via Dependency Injection.
     * <p>This method also instantiates the {@link #jdbcTemplate} instance variable.
     */
    @Autowired
    @Qualifier("dataSource")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
