package glint.mvp.test.dao;

import glint.mvp.dao.PlayerDao;
import glint.mvp.dao.VoteDao;
import glint.mvp.model.*;
import glint.mvp.test.AbstractTest;
import glint.mvp.util.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by shuang on 11/23/2014.
 */
public class VoteDaoImplTest extends AbstractTest {

    @Autowired
    @Qualifier("voteDao")
    private VoteDao voteDao;

    @Autowired
    @Qualifier("playerDao")
    private PlayerDao playerDao;

    private List<Player> allPlayers;

    @Before
    public void setup() {
        allPlayers = listPlayers();
    }


    @Test
    @Transactional
    public void testInsertDeleteOne() {
        Vote vote = getRandVote();
        int id = voteDao.insert(vote);
        vote.setVoteId(id);

        Vote actual = voteDao.get(id);
        Assert.assertEquals(vote.getVoteId(), actual.getVoteId());
        Assert.assertEquals(vote.getPlayer().getId(), actual.getPlayer().getId());

        voteDao.delete(id);
        actual = voteDao.get(id);
        Assert.assertNull(actual);
    }


    @Test
    @Transactional
    public void testRank() {
        //get 5 votes
        List<Vote> votes = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Vote vote = getRandVote();
            if (!votes.contains(vote)) {
                int voteId = voteDao.insert(vote);
                vote.setVoteId(voteId);
                votes.add(vote);
            }
        }

        Assert.assertEquals(voteDao.rankByPlayer(0), null);
        Assert.assertEquals(voteDao.rankByPosition(0), null);


        for (int rank = 1; rank < votes.size() + 1; rank++) {
            List<PlayerVotes> actualPvList = voteDao.rankByPlayer(rank);
            List<PlayerVotes> expectedPvList = rankPlayer(rank);
            Collections.sort(actualPvList);
            Collections.sort(expectedPvList);

            Assert.assertEquals(actualPvList.size(), expectedPvList.size());

            for (int i = 0; i < actualPvList.size(); i++) {
                PlayerVotes actual = actualPvList.get(i);
                PlayerVotes expected = expectedPvList.get(i);
                Assert.assertEquals(actual.getPlayer().getId(), expected.getPlayer().getId());
                Assert.assertEquals(actual.getVoteCount(), expected.getVoteCount());
            }

            List<PositionVotes> actualPosVoteList = voteDao.rankByPosition(rank);
            List<PositionVotes> expectedPosVoteList = rankPositions(rank);
            Collections.sort(actualPosVoteList);
            Collections.sort(expectedPosVoteList);

            for (int i = 0; i < actualPosVoteList.size(); i++) {
                PositionVotes actual = actualPosVoteList.get(i);
                PositionVotes expected = expectedPosVoteList.get(i);
                Assert.assertEquals(actual.getPosition().getId(), expected.getPosition().getId());
                Assert.assertEquals(actual.getVoteCount(), expected.getVoteCount());
            }

        }

        //remove these votes from db
        for (Vote vote : votes)
            voteDao.delete(vote.getVoteId());

    }


    private List<PlayerVotes> rankPlayer(int rank) {
        if (rank == 0)
            return null;

        List<Vote> votes = voteDao.list();

        Map<Player, Integer> playerCount = new HashMap<>();

        for (Vote vote : votes) {
            Integer voteCount = playerCount.get(vote.getPlayer());
            if (voteCount == null)
                voteCount = 1;
            else
                voteCount++;
            playerCount.put(vote.getPlayer(), voteCount);
        }

        List<PlayerVotes> pvList = new ArrayList<>();
        for (Player p : playerCount.keySet()) {
            int pc = playerCount.get(p);
            PlayerVotes pv = new PlayerVotes(p, pc);
            pvList.add(pv);
        }

        Collections.sort(pvList);

        if (pvList.size() <= rank)
            return pvList;
        return pvList.subList(0, rank);
    }

    private List<PositionVotes> rankPositions(int rank) {
        if (rank == 0)
            return null;

        List<Vote> votes = voteDao.list();

        Map<Position, Integer> positionCount = new HashMap<>();

        for (Vote vote : votes) {
            Integer voteCount = positionCount.get(vote.getPlayer().getPosition());
            if (voteCount == null)
                voteCount = 1;
            else
                voteCount++;
            positionCount.put(vote.getPlayer().getPosition(), voteCount);
        }

        List<PositionVotes> pvList = new ArrayList<>();
        for (Position p : positionCount.keySet()) {
            int pc = positionCount.get(p);
            PositionVotes pv = new PositionVotes(p, pc);
            pvList.add(pv);
        }

        Collections.sort(pvList);

        if (pvList.size() <= rank)
            return pvList;
        return pvList.subList(0, rank);

    }


    private Vote getRandVote() {
        int randPlayerId = (int) (Math.random() * 10);
        Player player = allPlayers.get(randPlayerId);

        Vote v = new Vote(Constants.clientId, null, player);
        return v;
    }


    @Transactional(readOnly = true)
    public List<Player> listPlayers() {
        return playerDao.list();
    }
}
