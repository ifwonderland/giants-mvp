package glint.mvp.model;

public class Player {

    private int id; //any int from 0 to 99

    private String firstName;

    private String lastName;

    private Position position; //any int from 0 to 19

    /**
     * Only position is required to create a new player object
     *
     * @param position
     */
    public Player(String firstName, String lastName, Position position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
    }

    public Player(int id, String firstName, String lastName, Position position) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + id;
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
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
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (id != other.id)
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Player [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
                + ", position=" + position + "]";
    }

}
