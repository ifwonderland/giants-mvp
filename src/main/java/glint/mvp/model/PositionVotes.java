package glint.mvp.model;

/**
 * Position and its votes
 * <p/>
 * Created by ifwonderland on 11/22/14.
 */
public class PositionVotes implements Comparable<PositionVotes> {

    private Position position;
    private int voteCount;

    public PositionVotes(Position position, int voteCount) {
        this.position = position;
        this.voteCount = voteCount;
    }

    public Position getPosition() {
        return position;
    }

    public int getVoteCount() {
        return voteCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PositionVotes)) return false;

        PositionVotes that = (PositionVotes) o;

        if (voteCount != that.voteCount) return false;
        if (!position.equals(that.position)) return false;

        return true;
    }


    @Override
    public int hashCode() {
        int result = position.hashCode();
        result = 31 * result + voteCount;
        return result;
    }


    @Override
    public String toString() {
        return "PositionVotes{" +
                "position=" + position +
                ", voteCount=" + voteCount +
                '}';
    }

    @Override
    public int compareTo(PositionVotes o) {
        int voteCountDiff = o.voteCount - this.voteCount; //sort by vote counts
        if (voteCountDiff != 0)
            return voteCountDiff;
        else
            return position.getId() - o.position.getId();

    }
}
