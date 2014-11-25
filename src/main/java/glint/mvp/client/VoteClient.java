package glint.mvp.client;

import glint.mvp.client.task.VoteTask;
import glint.mvp.dao.PlayerDao;
import glint.mvp.dao.VoteDao;
import glint.mvp.model.Player;
import glint.mvp.model.VoteResult;
import glint.mvp.util.Constants;
import glint.mvp.util.TaskIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Assume there are 10,000 anonymous voters. Each voter votes exactly once.
 * <p/>
 * Use 10 client threads to simulate the voting process, i.e., each client thread will generate 1,000 votes.
 * <p/>
 * <p/>
 * Created by ifwonderland on 11/16/14.
 */

public class VoteClient implements Callable<List<VoteResult>> {

    @Override
    public List<VoteResult> call() throws Exception {
        List<Player> allPlayers = listPlayers();

        List<VoteResult> results = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(Constants.numClientThreads);

        List<Future<List<VoteResult>>> futures = new ArrayList<>();

        for (int i = 0; i < Constants.numClientThreads; i++) {
            Callable<List<VoteResult>> task = new VoteTask(TaskIdGenerator.getNext(), allPlayers, voteDao);
            Future<List<VoteResult>> future = executor.submit(task);
            futures.add(future);
        }

        for (Future<List<VoteResult>> future : futures)
            results.addAll(future.get());


        return results;
    }


    @Transactional(readOnly = true)
    public List<Player> listPlayers() {
        return playerDao.list();
    }


    private PlayerDao playerDao;
    private VoteDao voteDao;


    @Required
    @Autowired
    @Qualifier("playerDao")
    public void setPlayerDao(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @Required
    @Autowired
    @Qualifier("voteDao")
    public void setVoteDao(VoteDao voteDao) {
        this.voteDao = voteDao;
    }


}
