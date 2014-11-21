package glint.mvp.test;

import glint.mvp.util.QueryGenerator;

import org.apache.log4j.Logger;
import org.junit.Test;

public class QueryGeneratorTest {

    private Logger log = Logger.getLogger(getClass());

    @Test
    public void test() {
        String insertAllPlayers = QueryGenerator.insertAllPlayers();
        log.info(insertAllPlayers);

        String insertAllPositions = QueryGenerator.insertAllPositions();
        log.info(insertAllPositions);
    }

}
