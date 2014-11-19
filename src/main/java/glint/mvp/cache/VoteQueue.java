package glint.mvp.cache;

import glint.mvp.model.Vote;
import glint.mvp.util.Constants;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Singleton shared blocking queue where vote client puts vote in and server takes vote out and process it.
 * Created by ifwonderland on 11/16/14.
 */
public final class VoteQueue implements Cache<Vote, Vote> {

    private static BlockingQueue<Vote> voteQueue = null;

    private static Logger log = Logger.getLogger(VoteQueue.class);

    protected VoteQueue() {
        voteQueue = new ArrayBlockingQueue<Vote>(Constants.numVotes);
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
    public void put(Vote key, Vote value) {
        try {
            enqueue(key);
        } catch (InterruptedException e) {
            log.error("Exception happened when trying to put vote into queue: "+key);
        }
    }

    @Override
    public Vote remove(Vote key) {
        boolean result = voteQueue.remove(key);
        return result?key:null;
    }

    @Override
    public Vote remove(){
        try {
            return dequeue();
        } catch (InterruptedException e) {
            log.error("dequeue throws exception. ",e);
            return null;
        }
    }

    @Override
    public void clear() {
        voteQueue = new ArrayBlockingQueue<Vote>(Constants.numVotes);
    }

    public static VoteQueue getInstance() {
        return (VoteQueue) CacheRepository.getInstance(VoteQueue.class.getName());
    }


    private void enqueue(Vote vote) throws InterruptedException {
        log.info("Vote " + vote + " enqueued into vote queue. ");
        voteQueue.put(vote);
    }

    private Vote dequeue() throws InterruptedException {
        Vote vote = voteQueue.take();
        log.info("Vote " + vote + " dequeued and to be processed. ");
        return vote;
    }
}
