package glint.mvp.client.task;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Generate unique sequence for task id.
 * Created by ifwonderland on 11/17/14.
 */
public class TaskIdGenerator {

    private static AtomicInteger counter = new AtomicInteger(-1);

    /**
     * Thread safe: atomic synchorinized
     * @return
     */
    public static int getNext(){
        return counter.addAndGet(1);
    }

}
