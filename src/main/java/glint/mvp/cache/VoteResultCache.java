package glint.mvp.cache;

import glint.mvp.model.PlayerVotes;
import glint.mvp.model.Vote;
import glint.mvp.model.VoteResult;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache for current voting result
 * Created by ifwonderland on 11/16/14.
 */
public class VoteResultCache implements CacheRegistry<Vote, VoteResult> {

    private static ConcurrentHashMap<Vote, VoteResult> currentVoteResults;

    protected VoteResultCache() {
        currentVoteResults = new ConcurrentHashMap<>();
    }


    @Override
    public VoteResult get(Vote key) {
        return currentVoteResults.get(key);
    }

    @Override
    public void remove(Vote key) {
        currentVoteResults.remove(key);
    }

    @Override
    public void clear() {
        currentVoteResults.clear();
    }

    //= new ConcurrentHashMap<Vote, VoteResult>();

    public static VoteResultCache getInstance() {
        return (VoteResultCache) CacheRepository.getInstance(VoteResultCache.class.getName());
    }

}
