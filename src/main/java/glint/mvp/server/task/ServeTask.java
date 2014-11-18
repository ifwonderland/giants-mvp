package glint.mvp.server.task;

import glint.mvp.cache.VoteQueue;
import glint.mvp.model.PlayerVotes;
import glint.mvp.model.Vote;
import glint.mvp.model.VoteResult;
import glint.mvp.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class ServeTask implements Callable<VoteResult> {

    private final VoteQueue voteQueue;

    public ServeTask(VoteQueue voteQueue) {
        this.voteQueue = voteQueue;
    }

    @Override
    public VoteResult call() throws Exception {
        Vote vote = voteQueue.dequeue();

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



        return new VoteResult(vote, dummyVotes);
    }

}
