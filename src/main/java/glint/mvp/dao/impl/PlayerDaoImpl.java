package glint.mvp.dao.impl;

import glint.mvp.dao.PlayerDao;
import glint.mvp.dao.schema.PlayerSchema;
import glint.mvp.dao.schema.PositionSchema;
import glint.mvp.model.Player;
import glint.mvp.model.Position;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ifwonderland on 11/22/14.
 */
public class PlayerDaoImpl extends AbstractDaoImpl<Player> implements PlayerDao {


    @Override
    public Player get(int id) {
        try {
            return jdbcTemplate.queryForObject(PlayerSchema.getQuery, rm, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Player> list() {
        try {
            return jdbcTemplate.query(PlayerSchema.listQuery, rm);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    private final static RowMapper<Player> rm = new RowMapper<Player>() {
        @Override
        public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt(PlayerSchema.columns.id.name());
            String firstName = rs.getString(PlayerSchema.columns.first_name.name());
            String lastName = rs.getString(PlayerSchema.columns.last_name.name());
            int posId = rs.getInt(PlayerSchema.columns.position_id.name());
            String posName = rs.getString(PositionSchema.columns.name.name());
            String posDesc = rs.getString(PositionSchema.columns.description.name());

            Position pos = new Position(posId, posName, posDesc);

            Player p = new Player(id, firstName, lastName, pos);

            return p;

        }
    };

}
