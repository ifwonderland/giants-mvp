package glint.mvp.util;

import glint.mvp.model.Player;
import glint.mvp.model.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Generate queries util methods
 * <p/>
 * Created by ifwonderland on 11/21/14.
 */
public class QueryGenerator {

    public static String dropTable(String tableName, boolean forceDrop) {
        StringBuilder sb = new StringBuilder();

        if (forceDrop) {
            sb.append("SET foreign_key_checks = 0;");
        }

        sb.append("drop table ").append(tableName).append(";");

        if (forceDrop) {
            sb.append("SET foreign_key_checks = 1;");
        }

        return sb.toString();
    }

    public static String createTable(TableNames table) {
        switch (table) {
        case position:
            return createPositionTable();
        case player:
            return createPlayerTable();
        case vote:
            return createVoteTable();
        }

        return null;
    }

    private static List<Position> positions = new ArrayList<>();
    private static List<Player> players = new ArrayList<>();

    static {
        String[] names = { "High pitcher", "Low pitcher", "Midrange pitcher", "High catcher",
                "Low catcher", "Midrange catcher", "First base", "Second base", "Third base",
                "High left field", "Low left field", "Mid left field", "High center field",
                "Mid center field", "Low center field", "High right field", "Mid right field",
                "Low right field", "High short stop", "Low short stop" };

        String[] descriptions = { "Pitcher of high targeting", "Pitcher of Low targeting",
                "Pitcher of Midrange targeting", "Catcher of High range", "Catcher of Low range",
                "Catcher of Midrange", "Base of first position", "Base of Second position",
                "Base of Third position", "Left field of high range", "Left field of Low range",
                "Left field of Mid range", "center field of High range",
                "center field of Mid range", "Low field of Mid range", "Right field of High range",
                "Right field of Mid range", "Right field of Low range", "Short stop of high range",
                "Short stop of Low range" };

        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            String desc = descriptions[i];
            Position pos = new Position(name, desc);
            positions.add(pos);
        }

        for (int i = 0; i < 100; i++) {
            String firstName = "firstName" + i;
            String lastName = "lastName" + i;

            int rand = (int) (Math.random() * (positions.size() - 1));

            Position pos = positions.get(rand);

            Player p = new Player(firstName, lastName, pos);
            players.add(p);
        }

    }

    public static String insertAllPositions() {

        StringBuilder sb = new StringBuilder();

        for (Position pos : positions) {
            String query = insertPosition(pos.getName(), pos.getDescription());
            sb.append(query).append("\n");
        }

        return sb.toString();
    }

    public static String insertPosition(String name, String description) {
        return "INSERT INTO vote.position (name, description) SELECT * FROM (SELECT '" + name
                + "', '" + description
                + "') AS tmp WHERE NOT EXISTS (SELECT name FROM vote.position WHERE name = '"
                + name + "') LIMIT 1;";
    }

    public static String insertAllPlayers() {
        StringBuilder sb = new StringBuilder();

        for (Player player : players) {
            String query = insertPlayer(player.getFirstName(), player.getLastName(), player
                    .getPosition().getId());
            sb.append(query).append("\n");
        }

        return sb.toString();

    }

    public static String insertPlayer(String firstName, String lastName, int posId) {
        return "INSERT INTO vote.player (first_name, last_name, position_id) SELECT * FROM (SELECT '"
                + firstName
                + "', '"
                + lastName
                + "', "
                + posId
                + ") AS tmp WHERE NOT EXISTS (SELECT name FROM vote.player WHERE first_name like '"
                + firstName + "'AND last_name like '" + lastName + "') LIMIT 1;";
    }

    private static String createPositionTable() {
        return "CREATE TABLE 'position' ('id' int(11) NOT NULL AUTO_INCREMENT, 'name' varchar(45) DEFAULT NULL, 'description' varchar(45) DEFAULT NULL, PRIMARY KEY ('id'), UNIQUE KEY 'id_UNIQUE' ('id') ) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=latin1;";
    }

    private static String createPlayerTable() {
        return "CREATE TABLE 'player' ('id' int(11) NOT NULL, 'first_name' varchar(45) DEFAULT NULL, 'last_name' varchar(45) DEFAULT NULL, 'category_id' int(11) NOT NULL, PRIMARY KEY ('id'), UNIQUE KEY 'id_UNIQUE' ('id'), KEY 'categoryId_idx' ('category_id'), CONSTRAINT 'categoryId' FOREIGN KEY ('category_id') REFERENCES 'position' ('id') ON DELETE NO ACTION ON UPDATE NO ACTION ) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
    }

    private static String createVoteTable() {
        return "CREATE TABLE 'vote' ('id' int(11) NOT NULL AUTO_INCREMENT, 'playerId' int(11) NOT NULL, 'categoryId' int(11) NOT NULL, PRIMARY KEY ('id'), UNIQUE KEY 'id_UNIQUE' ('id'), KEY 'playerIdIndex' ('playerId'), KEY 'categoryIdIndex' ('categoryId'), CONSTRAINT 'playerId' FOREIGN KEY ('playerId') REFERENCES 'player' ('id') ) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
    }

    public enum TableNames {
        position, player, vote
    }

    public enum PositionTableColumns {
        id, name, description
    }

}
