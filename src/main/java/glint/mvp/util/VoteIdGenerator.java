package glint.mvp.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ifwonderland on 11/18/14.
 */
public class VoteIdGenerator {


    private static AtomicInteger counter = new AtomicInteger(-1);

    /**
     * Thread safe: atomic synchorinized
     * @return
     */
    public static int getNext(){
        return counter.addAndGet(1);
    }
}
