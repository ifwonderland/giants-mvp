package glint.mvp.dao.impl;

import glint.mvp.dao.Dao;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by Shaochen Huang on 11/22/14.
 */
public abstract class AbstractDaoImpl<T> implements Dao<T> {

    protected JdbcTemplate jdbcTemplate;

    @Required
    public void setDataSource(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }


}
