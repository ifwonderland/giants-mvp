package glint.mvp.client.task;

import glint.mvp.cache.VoteQueue;
import glint.mvp.cache.VoteResultCache;
import glint.mvp.model.PlayerVotes;
import glint.mvp.model.Vote;
import glint.mvp.model.VoteResult;
import glint.mvp.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class VoteTask implements Callable<List<VoteResult>> {

    private final int taskId;
    private final VoteQueue voteQueue;
    private final VoteResultCache cachedResults;

    public VoteTask(int taskId, VoteQueue voteQueue,
                    VoteResultCache cachedResults) {
        this.taskId = taskId;
        this.voteQueue = voteQueue;
        this.cachedResults = cachedResults;
    }

    public List<VoteResult> call() throws Exception {
        List<VoteResult> results = new ArrayList<>();

        for (int i = 0; i < Constants.numVotes / Constants.numClientThreads; i++) {
            final int curPlayerId = (int) (Math.random() * 100d);
            try {
                Vote vote = new Vote(taskId, i, curPlayerId);
                voteQueue.enqueue(vote);

                VoteResult result = null;
                while (result == null)
                    result = cachedResults.get(vote);
                results.add(result);

            } catch (InterruptedException e) {
                throw e;
            }
        }

        return results;
    }
}
