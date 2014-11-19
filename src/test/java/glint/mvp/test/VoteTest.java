package glint.mvp.test;

import glint.mvp.cache.VoteQueue;
import glint.mvp.cache.VoteResultCache;
import glint.mvp.client.VoteClient;
import glint.mvp.model.VoteResult;
import glint.mvp.server.VoteCountServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Test client side voting
 * Created by ifwonderland on 11/17/14.
 */
public class VoteTest {


    private VoteClient client = null;
    private VoteCountServer server = null;

    @Before
    public void setup() {
        client = new VoteClient();
        server = new VoteCountServer();
    }


    @After
    public void teardown() {
        client = null;
        VoteQueue.getInstance().clear();
        VoteResultCache.getInstance().clear();
    }


    @Test
    public void test() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<List<VoteResult>> clientVotesFuture = executor.submit(client);
        Future<List<VoteResult>> serverVotesFuture = executor.submit(server);

        List<VoteResult> clientVotes = clientVotesFuture.get();
        List<VoteResult> serverVotes = serverVotesFuture.get();

        isEquals(clientVotes, serverVotes);
    }


    /**
     * Helper method to check if two
     *
     * @param votes1
     * @param votes2
     * @return
     */
    private boolean isEquals(List<VoteResult> votes1, List<VoteResult> votes2) {
        if (votes1 == null)
            return votes2 == null;
        if (votes2 == null)
            return votes1 == null;

        if (votes1.isEmpty())
            return votes2.isEmpty();

        if (votes2.isEmpty())
            return votes1.isEmpty();

        if (votes1.size() != votes2.size())
            return false;

        Collections.sort(votes1);
        Collections.sort(votes2);

        for(int i=0;i<votes1.size();i++){
            VoteResult v1 = votes1.get(i);
            VoteResult v2 = votes2.get(i);
            if(!v1.equals(v2))
                return false;
        }


        return true;

    }





}
