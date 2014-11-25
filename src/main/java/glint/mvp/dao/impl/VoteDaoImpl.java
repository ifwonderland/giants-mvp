package glint.mvp.dao.impl;

import glint.mvp.dao.VoteDao;
import glint.mvp.dao.schema.PlayerSchema;
import glint.mvp.dao.schema.PositionSchema;
import glint.mvp.dao.schema.VoteSchema;
import glint.mvp.model.*;
import glint.mvp.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Created by ifwonderland on 11/22/14.
 */
public class VoteDaoImpl extends AbstractDaoImpl<Vote> implements VoteDao {

    private Logger log = Logger.getLogger(getClass());

    private SimpleJdbcInsert jdbcInsert;

    @Override
    @Required
    public void setDataSource(DataSource ds) {
        super.setDataSource(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName(VoteSchema.tableName).usingGeneratedKeyColumns(VoteSchema.columns.id.name());
    }

    @Override
    public int insert(Vote vote) {
        return jdbcInsert.executeAndReturnKey(getSqlParamSource(vote)).intValue();
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(VoteSchema.deleteQuery, id);
    }


    @Override
    public List<PlayerVotes> rankByPlayer(int rank) {
        if (rank == 0)
            return null;
        try {
            List<PlayerVotes> pvList = jdbcTemplate.query(VoteSchema.rankPlayerQuery, playerVotesRowMapper);

            Collections.sort(pvList);

            List<PlayerVotes> result = null;

            if (pvList.size() < rank)
                result = pvList;
            else
                result = pvList.subList(0, rank);

            log.debug("Top " + rank + " players are: " + result);

            return result;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public List<PositionVotes> rankByPosition(int rank) {
        if (rank == 0)
            return null;
        try {
            List<PositionVotes> pvList = jdbcTemplate.query(VoteSchema.rankPositionQuery, positionVotesRowMapper);

            Collections.sort(pvList);

            List<PositionVotes> result = null;

            if (pvList.size() < rank)
                result = pvList;
            else
                result = pvList.subList(0, rank);

            log.debug("Top " + rank + " positions are: " + result);

            return result;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public Vote get(int id) {
        try {
            return jdbcTemplate.queryForObject(VoteSchema.getQuery, rm, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Vote> list() {
        try {
            return jdbcTemplate.query(VoteSchema.listQuery, rm);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    private SqlParameterSource getSqlParamSource(Vote vote) {
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue(VoteSchema.columns.player_id.name(), vote.getPlayer().getId())
                .addValue(VoteSchema.columns.position_id.name(), vote.getPlayer().getPosition().getId());
        return source;
    }


    private static RowMapper<Vote> rm = new RowMapper<Vote>() {
        @Override
        public Vote mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt(VoteSchema.columns.id.name());
            int playerId = rs.getInt(VoteSchema.columns.player_id.name());
            int posId = rs.getInt(VoteSchema.columns.position_id.name());
            String firstName = rs.getString(PlayerSchema.columns.first_name.name());
            String lastName = rs.getString(PlayerSchema.columns.last_name.name());

            String posName = rs.getString(PositionSchema.columns.name.name());
            String posDesc = rs.getString(PositionSchema.columns.description.name());

            Position position = new Position(posId, posName, posDesc);

            Player player = new Player(playerId, firstName, lastName, position);
            Vote vote = new Vote(Constants.clientId, id, player);
            return vote;
        }
    };


    private static RowMapper<PlayerVotes> playerVotesRowMapper = new RowMapper<PlayerVotes>() {
        @Override
        public PlayerVotes mapRow(ResultSet rs, int rowNum) throws SQLException {
            int playerId = rs.getInt(VoteSchema.columns.player_id.name());
            String firstName = rs.getString(PlayerSchema.columns.first_name.name());
            String lastName = rs.getString(PlayerSchema.columns.last_name.name());

            Player player = new Player(playerId, firstName, lastName, null);
            int voteCount = rs.getInt(VoteSchema.columns.voteCount.name());

            PlayerVotes pv = new PlayerVotes(player, voteCount);
            return pv;
        }
    };


    private static RowMapper<PositionVotes> positionVotesRowMapper = new RowMapper<PositionVotes>() {
        @Override
        public PositionVotes mapRow(ResultSet rs, int rowNum) throws SQLException {
            int posId = rs.getInt(VoteSchema.columns.position_id.name());
            String name = rs.getString(PositionSchema.columns.name.name());
            String desc = rs.getString(PositionSchema.columns.description.name());

            Position pos = new Position(posId, name, desc);
            int voteCount = rs.getInt(VoteSchema.columns.voteCount.name());
            PositionVotes pv = new PositionVotes(pos, voteCount);
            return pv;
        }
    };


}
