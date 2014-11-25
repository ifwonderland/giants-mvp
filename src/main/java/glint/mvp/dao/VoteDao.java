package glint.mvp.dao;

import glint.mvp.model.PlayerVotes;
import glint.mvp.model.PositionVotes;
import glint.mvp.model.Vote;

import java.util.List;

/**
 * Read and Write access to Vote table in database
 * <p/>
 * Created by ifwonderland on 11/22/14.
 */
public interface VoteDao extends Dao<Vote> {

    /**
     * Insert a new vote to vote table
     *
     * @param vote
     * @return voteId
     */
    int insert(Vote vote);

    /**
     * Delete a vote by id
     *
     * @param id
     */
    void delete(int id);


    /**
     * Rank players
     *
     * @param rank
     * @return
     */
    List<PlayerVotes> rankByPlayer(int rank);


    /**
     * Rank positions
     *
     * @param rank
     * @return
     */
    List<PositionVotes> rankByPosition(int rank);

}
