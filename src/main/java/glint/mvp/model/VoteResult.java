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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoteResult)) return false;

        VoteResult that = (VoteResult) o;

        if (!result.equals(that.result)) return false;
        if (!vote.equals(that.vote)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = vote.hashCode();
        result1 = 31 * result1 + result.hashCode();
        return result1;
    }

    @Override
    public String toString() {
        return "VoteResult{" +
                "vote=" + vote +
                ", result=" + result +
                '}';
    }
}
