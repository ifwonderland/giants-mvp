package glint.mvp.model;

public class Player {

    private int id; //any int from 0 to 99

    private int position; //any int from 0 to 19

    /**
     * Only position is required to create a new player object
     * @param position
     */
    public Player(int id, int position) {
        this.id = id;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + position;
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
        Player other = (Player) obj;
        if (id != other.id)
            return false;
        if (position != other.position)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Player [id=" + id + ", position=" + position + "]";
    }

}
