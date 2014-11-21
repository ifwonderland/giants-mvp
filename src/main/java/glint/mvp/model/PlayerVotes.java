package glint.mvp.model;

/**
 * Represents player vote result. 
 * @author shuang
 *
 */
public class PlayerVotes implements Comparable<PlayerVotes> {

    private Player player;

    private int voteCount;

    public PlayerVotes(Player player, int voteCount) {
        this.player = player;
        this.voteCount = voteCount;
    }

    public Player getPlayer() {
        return player;
    }

    public int getVoteCount() {
        return voteCount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((player == null) ? 0 : player.hashCode());
        result = prime * result + voteCount;
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
        if (player == null) {
            if (other.player != null)
                return false;
        }
        else if (!player.equals(other.player))
            return false;
        if (voteCount != other.voteCount)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PlayerVotes [player=" + player + ", voteCount=" + voteCount + "]";
    }

    @Override
    public int compareTo(PlayerVotes o) {
        return this.voteCount - o.getVoteCount(); //sort by vote counts
    }

}
