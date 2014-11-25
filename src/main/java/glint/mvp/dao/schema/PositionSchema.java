package glint.mvp.dao.schema;

/**
 * Schema constants
 * Created by ifwonderland on 11/22/14.
 */
public class PositionSchema {

    public static final String tableName = "position";

    public static enum columns {
        id, name, description;

        public String fullName() {
            return tableName + "." + this;
        }

    }

    public static final String getQuery = "select * from " + tableName + " where " + columns.id + " = ?";

    public static final String listQuery = "select * from " + tableName;
}
