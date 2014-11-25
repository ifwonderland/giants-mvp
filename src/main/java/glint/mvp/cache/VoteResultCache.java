package glint.mvp.cache;

import glint.mvp.model.Vote;
import glint.mvp.model.VoteResult;

import javax.naming.OperationNotSupportedException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache for current voting result
 * Created by ifwonderland on 11/16/14.
 */
public class VoteResultCache implements Cache<Vote, VoteResult> {

    private static ConcurrentHashMap<Vote, VoteResult> currentVoteResults;

    protected VoteResultCache() {
        currentVoteResults = new ConcurrentHashMap<>();
    }


    @Override
    public VoteResult get(Vote key) {
        VoteResult vr = currentVoteResults.get(key);
        if (vr == null)
            return null;
        return new VoteResult(vr);
    }

    @Override
    public void put(Vote key, VoteResult value) {
        currentVoteResults.put(key, value);
    }

    @Override
    public VoteResult remove(Vote key) {
        return currentVoteResults.remove(key);
    }

    @Override
    public VoteResult remove() throws Exception {
        throw new OperationNotSupportedException("Not supported");
    }

    @Override
    public void clear() {
        currentVoteResults.clear();
    }


    public Set<Vote> list() {
        Set<Vote> votes = new HashSet<>();
        for (Vote vote : currentVoteResults.keySet()) {
            votes.add(vote);
        }
        return votes;
    }




    public static VoteResultCache getInstance() {
        return (VoteResultCache) CacheRepository.getInstance(VoteResultCache.class.getName());
    }

}
