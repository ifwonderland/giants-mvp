package glint.mvp.client.service;

import glint.mvp.cache.VoteQueue;
import glint.mvp.cache.VoteResultCache;
import glint.mvp.client.VoteClient;
import glint.mvp.client.task.TaskIdGenerator;
import glint.mvp.client.task.VoteTask;
import glint.mvp.model.PlayerVotes;
import glint.mvp.model.Vote;
import glint.mvp.model.VoteResult;
import glint.mvp.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Assume there are 10,000 anonymous voters. Each voter votes exactly once.
 * <p/>
 * Use 10 client threads to simulate the voting process, i.e., each client thread will generate 1,000 votes.
 * <p/>
 * <p/>
 * Created by ifwonderland on 11/16/14.
 */
public class VoteClientImpl implements VoteClient {

    @Override
    public List<VoteResult> vote() throws Exception {
        List<VoteResult> results = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(Constants.numClientThreads);

        List<Future<List<VoteResult>>> futures = new ArrayList<>();

        for (int i = 0; i < Constants.numVotes; i++) {
            Callable<List<VoteResult>> task = new VoteTask(TaskIdGenerator.getNext(), VoteQueue.getInstance(), VoteResultCache.getInstance());
            Future<List<VoteResult>> future = executor.submit(task);
            futures.add(future);
        }

        for(Future<List<VoteResult>> future:futures)
            results.addAll(future.get());

        return results;
    }
}