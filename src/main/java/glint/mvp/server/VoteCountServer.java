package glint.mvp.server;

import glint.mvp.model.VoteResult;
import glint.mvp.server.task.ServerTask;
import glint.mvp.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Impl for voting processing server.
 * <p/>
 * Created by ifwonderland on 11/17/14.
 */
public class VoteCountServer implements Callable<List<VoteResult>> {

    @Override
    public List<VoteResult> call() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(Constants.numServerThreads);

        List<Future<VoteResult>> futures = new ArrayList<>();

        for (int i = 0; i < Constants.numVotes; i++) {
            Callable<VoteResult> task = new ServerTask();
            Future<VoteResult> future = executor.submit(task);
            futures.add(future);
        }

        List<VoteResult> results = new ArrayList<>();
        for (Future<VoteResult> future : futures)
            results.add(future.get());

        return results;

    }
}
