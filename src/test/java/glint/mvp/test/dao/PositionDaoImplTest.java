package glint.mvp.test.dao;

import glint.mvp.dao.PositionDao;
import glint.mvp.model.Position;
import glint.mvp.test.AbstractTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by shuang on 11/23/2014.
 */
public class PositionDaoImplTest extends AbstractTest {

    @Autowired
    @Qualifier("positionDao")
    private PositionDao positionDao;


    private List<Position> allPos;

    @Before
    public void setup() {
        allPos = listAllPos();
    }

    @Transactional(readOnly = true)
    public List<Position> listAllPos() {
        return positionDao.list();
    }


    @Test
    @Transactional(readOnly = true)
    public void test() {
        for (Position expectedPos : allPos) {
            int id = expectedPos.getId();
            Position actualPos = positionDao.get(id);
            Assert.assertEquals(expectedPos, actualPos);
        }
    }


}
