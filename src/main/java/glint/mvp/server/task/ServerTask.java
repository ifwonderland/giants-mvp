package glint.mvp.server.task;

import glint.mvp.cache.VoteQueue;
import glint.mvp.cache.VoteResultCache;
import glint.mvp.model.PlayerVotes;
import glint.mvp.model.Vote;
import glint.mvp.model.VoteResult;
import glint.mvp.util.Constants;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ServerTask implements Callable<VoteResult> {
    private final Logger log = Logger.getLogger(ServerTask.class);

    @Override
    public VoteResult call() throws Exception {
        Vote vote = VoteQueue.getInstance().remove();
        log.debug("Starting processing vote: " + vote + " from the vote queue, thread: " + Thread.currentThread());

        //persist vote to DB

        //get current vote from DB (synchronized) open transaction here

        //calculate this vote

        //return the results

        //for testing only, returning some dummy data here
        List<PlayerVotes> dummyVotes = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            int playerId = (int) (Math.random() * Constants.numPlayers);
            int voteCount = (int) (Math.random() * Constants.numVotes);
            PlayerVotes pv = new PlayerVotes(playerId, voteCount);
            dummyVotes.add(pv);
        }


        VoteResult result = new VoteResult(vote, dummyVotes);
        log.debug("Result calculated for vote: "+vote+", result: "+result);

        VoteResultCache.getInstance().put(vote, result);

        return result;
    }

}
