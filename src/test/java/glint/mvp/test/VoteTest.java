package glint.mvp.test;

import glint.mvp.cache.VoteQueue;
import glint.mvp.cache.VoteResultCache;
import glint.mvp.client.VoteClient;
import glint.mvp.client.service.VoteClientImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ifwonderland on 11/17/14.
 */
public class VoteTest {


    private VoteClient client = null;


    @Before
    public void setup() {
        client = new VoteClientImpl();
    }


    @After
    public void teardown() {
        client = null;

    }


    @Test
    public void test() throws Exception {
        client.vote();
    }

}
