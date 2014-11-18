package glint.mvp.client;

import glint.mvp.model.PlayerVotes;
import glint.mvp.model.Vote;
import glint.mvp.model.VoteResult;

import java.util.List;
import java.util.Map;

/**
 * Client that manages threads and run voting tasks.
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * Created by ifwonderland on 11/16/14.
 */
public interface VoteClient {


    /**
     * Vote kickoff concurrent voting threads and returns a map between voter and voting results for that voter.
     * <p>
     *
     * @return map between Vote and voting results returned by server, which is top N players voted at the time.
     * @throws Exception
     */
    List<VoteResult> vote() throws Exception;



}
