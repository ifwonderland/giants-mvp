package glint.mvp.test.dao;

import glint.mvp.dao.PlayerDao;
import glint.mvp.model.Player;
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
public class PlayerDaoImplTest extends AbstractTest {

    @Autowired
    @Qualifier("playerDao")
    private PlayerDao playerDao;

    private List<Player> allPlayers;

    @Before
    public void setup() {
        allPlayers = list();
    }


    @Test
    @Transactional(readOnly = true)
    public void test() {
        for (Player expectedPlayer : allPlayers) {
            Player actualPlayer = playerDao.get(expectedPlayer.getId());
            Assert.assertEquals(expectedPlayer, actualPlayer);
        }

    }


    @Transactional(readOnly = true)
    public List<Player> list() {
        return playerDao.list();
    }
}
