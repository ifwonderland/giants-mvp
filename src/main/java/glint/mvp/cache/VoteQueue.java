package glint.mvp.cache;

import com.sun.tools.internal.jxc.apt.Const;
import glint.mvp.model.Vote;
import glint.mvp.util.Constants;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Singleton shared blocking queue where vote client puts vote in and server takes vote out and process it.
 * Created by ifwonderland on 11/16/14.
 */
public final class VoteQueue implements CacheRegistry<Vote, Vote> {

    private static BlockingQueue<Vote> voteQueue = null;

    protected VoteQueue() {
        voteQueue = new ArrayBlockingQueue<Vote>(Constants.numVotes);
    }

    public void enqueue(Vote vote) throws InterruptedException {
        voteQueue.put(vote);
    }

    public Vote dequeue() throws InterruptedException {
        return voteQueue.take();
    }

    @Override
    public Vote get(Vote key) {
        Iterator<Vote> it = voteQueue.iterator();

        while (it.hasNext()) {
            Vote vote = it.next();
            if (vote.equals(key))
                return vote;
        }

        return null;
    }

    @Override
    public void remove(Vote key) {
        voteQueue.remove(key);
    }

    @Override
    public void clear() {
        voteQueue = new ArrayBlockingQueue<Vote>(Constants.numVotes);
    }

    public static VoteQueue getInstance() {
        return (VoteQueue) CacheRepository.getInstance(VoteQueue.class.getName());
    }
}
