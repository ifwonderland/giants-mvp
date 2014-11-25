package glint.mvp.client.task;

import glint.mvp.cache.VoteQueue;
import glint.mvp.cache.VoteResultCache;
import glint.mvp.dao.VoteDao;
import glint.mvp.model.Player;
import glint.mvp.model.Vote;
import glint.mvp.model.VoteResult;
import glint.mvp.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class VoteTask implements Callable<List<VoteResult>> {

    private final int taskId;
    private final List<Player> allPlayers;

    private final VoteDao voteDao;

    private final static Logger log = Logger.getLogger(VoteTask.class);

    public VoteTask(int taskId, List<Player> allPlayers, VoteDao voteDao) {
        this.taskId = taskId;
        this.allPlayers = allPlayers;
        this.voteDao = voteDao;
    }

    public List<VoteResult> call() throws Exception {
        List<VoteResult> results = new ArrayList<>();

        for (int i = 0; i < Constants.numVotes / Constants.numClientThreads; i++) {
            Vote vote = new Vote(taskId, null, randomPlayer());
            log.debug("Vote " + vote + " has been created by thread: " + Thread.currentThread());

            int voteId = insertVote(vote);
            vote.setVoteId(voteId);
            VoteQueue.getInstance().put(vote, vote);

            VoteResult result = null;
            while (result == null)
                result = VoteResultCache.getInstance().get(vote);

            log.debug("Vote result found for vote: " + vote + " result: " + result + " thread: " + Thread.currentThread());

            results.add(result);
        }

        return results;
    }


    /**
     * Get a random player from all players
     *
     * @return
     */
    private Player randomPlayer() {
        final int curPlayerId = (int) (Math.random() * 100d);
        return allPlayers.get(curPlayerId);

    }

    /**
     * Insert a vote into vote table
     *
     * @param vote
     * @return
     */
    @Transactional(readOnly = true)
    public int insertVote(Vote vote) {
        return voteDao.insert(vote);
    }


}
