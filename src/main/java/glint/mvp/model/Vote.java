package glint.mvp.model;

/**
 * Reprents a voter, with voter client id and vote id.
 *
 * @author shuang
 */
public class Vote implements Comparable<Vote> {

    private int clientId;

    private Integer voteId;

    private Player player;

    public Vote(int clientId, Integer voteId, Player player) {
        this.clientId = clientId;
        this.voteId = voteId;
        this.player = player;
    }

    public int getClientId() {
        return clientId;
    }

    public int getVoteId() {
        return voteId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + clientId;
        result = prime * result + ((player == null) ? 0 : player.hashCode());
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
        if (player == null) {
            if (other.player != null)
                return false;
        } else if (!player.equals(other.player))
            return false;
        if (voteId != other.voteId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Vote [clientId=" + clientId + ", voteId=" + voteId + ", player=" + player + "]";
    }

    @Override
    public int compareTo(Vote o) {
        int voteIdDiff = voteId - o.getVoteId();
        if (voteIdDiff != 0)
            return voteIdDiff;

        return clientId - o.getClientId();
    }
}
