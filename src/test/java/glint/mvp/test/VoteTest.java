package glint.mvp.test;

import glint.mvp.cache.VoteQueue;
import glint.mvp.cache.VoteResultCache;
import glint.mvp.client.VoteClient;
import glint.mvp.model.VoteResult;
import glint.mvp.server.VoteCountServer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
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




        Assert.assertEquals(clientVotes, serverVotes);




    }





}
