package glint.mvp.dao.schema;

/**
 * Created by Shaochen Huang on 11/22/14.
 */
public class PlayerSchema {

    public static final String tableName = "player";

    public static enum columns {
        id, first_name, last_name, position_id;

        public String fullName() {
            return tableName + "." + this;
        }
    }

    public static final String getQuery =
            "select * from "
                    + tableName + "," + PositionSchema.tableName
                    + " where " + columns.position_id.fullName() + "=" + PositionSchema.columns.id.fullName()
                    + " and " + columns.id.fullName() + " = ?";

    public static final String listQuery =
            "select * from "
                    + tableName + "," + PositionSchema.tableName
                    + " where " + columns.position_id.fullName() + "=" + PositionSchema.columns.id.fullName();


}
