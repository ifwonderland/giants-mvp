package glint.mvp.model;

import java.util.List;

/**
 *
 * Voting results for a current vote
 * Created by ifwonderland on 11/17/14.
 */
public class VoteResult {

    private Vote vote;

    private List<PlayerVotes> result;

    public VoteResult(Vote vote, List<PlayerVotes> result) {
        this.vote = vote;
        this.result = result;
    }

    public Vote getVote() {
        return vote;
    }

    public List<PlayerVotes> getResult() {
        return result;
    }
}
