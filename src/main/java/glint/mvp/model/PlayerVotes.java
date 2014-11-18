package glint.mvp.model;

/**
 * Represents player vote result. 
 * @author shuang
 *
 */
public class PlayerVotes implements Comparable<PlayerVotes> {

    private int playerId;

    private int voteCount;

    public PlayerVotes(int playerId, int voteCount) {
        this.playerId = playerId;
        this.voteCount = voteCount;
    }

    public long getPlayerId() {
        return playerId;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public int compareTo(PlayerVotes o) {
        return Long.compare(this.voteCount, o.voteCount);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (playerId ^ (playerId >>> 32));
        result = prime * result + (int) (voteCount ^ (voteCount >>> 32));
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
        PlayerVotes other = (PlayerVotes) obj;
        if (playerId != other.playerId)
            return false;
        if (voteCount != other.voteCount)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PlayerVotes [playerId=" + playerId + ", voteCount=" + voteCount + "]";
    }

}
