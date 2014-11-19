package glint.mvp.client.task;

import glint.mvp.cache.VoteQueue;
import glint.mvp.cache.VoteResultCache;
import glint.mvp.model.PlayerVotes;
import glint.mvp.model.Vote;
import glint.mvp.model.VoteResult;
import glint.mvp.util.Constants;
import glint.mvp.util.VoteIdGenerator;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class VoteTask implements Callable<List<VoteResult>> {

    private final int taskId;

    private final static Logger log = Logger.getLogger(VoteTask.class);

    public VoteTask(int taskId) {
        this.taskId = taskId;
    }

    public List<VoteResult> call() throws Exception {
        List<VoteResult> results = new ArrayList<>();

        for (int i = 0; i < Constants.numVotes / Constants.numClientThreads; i++) {
            final int curPlayerId = (int) (Math.random() * 100d);

            Vote vote = new Vote(taskId, VoteIdGenerator.getNext(), curPlayerId);
            log.debug("Vote " + vote + " has been created by thread: " + Thread.currentThread());

            VoteQueue.getInstance().put(vote, vote);

            VoteResult result = null;
            while (result == null)
                result = VoteResultCache.getInstance().get(vote);

            log.debug("Vote result found for vote: " + vote + " result: " + result + " thread: " + Thread.currentThread());

            results.add(result);
        }

        return results;
    }
}
