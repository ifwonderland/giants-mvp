package glint.mvp.server.task;

import glint.mvp.cache.VoteQueue;
import glint.mvp.cache.VoteResultCache;
import glint.mvp.model.Player;
import glint.mvp.model.PlayerVotes;
import glint.mvp.model.Vote;
import glint.mvp.model.VoteResult;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.Callable;

public class ServerTask implements Callable<VoteResult> {
    private final Logger log = Logger.getLogger(ServerTask.class);

    @Override
    public VoteResult call() throws Exception {
        Vote vote = VoteQueue.getInstance().remove();
        log.debug("Starting processing vote: " + vote + " from the vote queue, thread: " + Thread.currentThread());

        List<PlayerVotes> playerVotes = rank(vote);
        VoteResult result = new VoteResult(vote, playerVotes);
        log.debug("Result calculated for vote: " + vote + ", result: " + result);

        VoteResultCache.getInstance().put(vote, result);

        return result;
    }


    private List<PlayerVotes> rank(Vote curVote) {
        Set<Vote> votes = VoteResultCache.getInstance().list();
        votes.add(curVote);

        Map<Player, Integer> playerCount = new HashMap<>();

        for (Vote vote : votes) {
            Integer voteCount = playerCount.get(vote.getPlayer());
            if (voteCount == null)
                voteCount = 1;
            else
                voteCount++;
            playerCount.put(vote.getPlayer(), voteCount);
        }

        List<PlayerVotes> pvList = new ArrayList<>();
        for (Player p : playerCount.keySet()) {
            int pc = playerCount.get(p);
            PlayerVotes pv = new PlayerVotes(p, pc);
            pvList.add(pv);
        }

        Collections.sort(pvList);

        if (pvList.size() <= 5)
            return pvList;
        return pvList.subList(0, 5);
    }


}
