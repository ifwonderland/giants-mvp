package glint.mvp.test.util;

import glint.mvp.test.AbstractTest;
import glint.mvp.util.QueryGenerator;
import org.apache.log4j.Logger;
import org.junit.Test;

public class QueryGeneratorTest extends AbstractTest {

    private Logger log = Logger.getLogger(getClass());

    @Test
    public void test() {
        String insertAllPlayers = QueryGenerator.insertAllPlayers();
        log.info(insertAllPlayers);

        String insertAllPositions = QueryGenerator.insertAllPositions();
        log.info(insertAllPositions);
    }


}
