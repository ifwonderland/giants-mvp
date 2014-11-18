package glint.mvp.model;

/**
 * Reprents a voter, with voter client id and vote id. 
 * @author shuang
 *
 */
public class Vote {

    private int clientId;

    private int voteId;

    private int playerId;

    public Vote(int clientId, int voteId, int playerId) {
        this.clientId = clientId;
        this.voteId = voteId;
        this.playerId = playerId;
    }


    public int getClientId() {
        return clientId;
    }

    public int getVoteId() {
        return voteId;
    }


    public int getPlayerId() {
        return playerId;
    }

    @Override
    public String toString() {
        return "Vote [clientId=" + clientId + ", voteId=" + voteId + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + clientId;
        result = prime * result + voteId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vote other = (Vote) obj;
        if (clientId != other.clientId)
            return false;
        if (voteId != other.voteId)
            return false;
        return true;
    }

}
