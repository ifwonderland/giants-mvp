package glint.mvp.dao.impl;

import glint.mvp.dao.PositionDao;
import glint.mvp.dao.schema.PositionSchema;
import glint.mvp.model.Position;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ifwonderland on 11/22/14.
 */
public class PositionDaoImpl extends AbstractDaoImpl<Position> implements PositionDao {

    @Override
    public Position get(int id) {
        try {
            return jdbcTemplate.queryForObject(PositionSchema.getQuery, rm, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Position> list() {
        try {
            return jdbcTemplate.query(PositionSchema.listQuery, rm);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    private final static RowMapper<Position> rm = new RowMapper<Position>() {

        @Override
        public Position mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt(PositionSchema.columns.id.name());
            String name = rs.getString(PositionSchema.columns.name.name());
            String desc = rs.getString(PositionSchema.columns.description.name());

            Position p = new Position(id, name, desc);
            return p;

        }
    };

}
