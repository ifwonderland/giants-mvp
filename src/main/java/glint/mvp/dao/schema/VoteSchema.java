package glint.mvp.dao.schema;

/**
 * Created by Shaochen Huang on 11/22/14.
 */
public class VoteSchema {

    public static final String tableName = "vote";

    public static enum columns {
        id, player_id, position_id, voteCount;

        public String fullName() {
            return tableName + "." + this;
        }

    }

    public static final String getQuery =
            "select * from "
                    + tableName + "," + PlayerSchema.tableName + "," + PositionSchema.tableName
                    + " where "
                    + columns.player_id.fullName() + "=" + PlayerSchema.columns.id.fullName()
                    + " and "
                    + columns.position_id.fullName() + "=" + PositionSchema.columns.id.fullName()
                    + " and "
                    + columns.id.fullName() + " = ?";

    public static final String listQuery =
            "select * from "
                    + tableName + "," + PlayerSchema.tableName + "," + PositionSchema.tableName
                    + " where "
                    + columns.player_id.fullName() + "=" + PlayerSchema.columns.id.fullName()
                    + " and "
                    + columns.position_id.fullName() + "=" + PositionSchema.columns.id.fullName();

    public static final String deleteQuery = "delete from " + tableName + " where " + columns.id + "=?";

    public static final String rankPlayerQuery =
            "SELECT "
                    + columns.player_id.fullName() + ", "
                    + PlayerSchema.columns.first_name.fullName() + ","
                    + PlayerSchema.columns.last_name.fullName() + ","
                    + "COUNT(*) AS " + columns.voteCount
                    + " FROM "
                    + tableName + ", " + PlayerSchema.tableName
                    + " WHERE " + columns.player_id.fullName() + "=" + PlayerSchema.columns.id.fullName()
                    + " GROUP BY " + columns.player_id.fullName()
                    + " ORDER BY " + columns.voteCount + " DESC";

    public static final String rankPositionQuery =
            "SELECT "
                    + columns.position_id.fullName() + ", "
                    + PositionSchema.columns.name.fullName() + ","
                    + PositionSchema.columns.description.fullName() + ","
                    + "COUNT(*) AS " + columns.voteCount
                    + " FROM "
                    + tableName + ", " + PositionSchema.tableName
                    + " WHERE " + columns.position_id.fullName() + "=" + PositionSchema.columns.id.fullName()
                    + " GROUP BY " + columns.position_id.fullName()
                    + " ORDER BY " + columns.voteCount + " DESC";

}
