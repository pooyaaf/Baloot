package Baloot.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BalootRepository {
    private static BalootRepository instance;

    public static BalootRepository getInstance() {
        if (instance == null) {
            try {
                instance = new BalootRepository();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("error in create query");
            }
        }
        return instance;
    }

    private BalootRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        con.setAutoCommit(false);
        Statement stmt = con.createStatement();
//        TODO: complete table creation
        stmt.addBatch("CREATE TABLE IF NOT EXISTS Provider (\n" +
                "    id INT,\n" +
                "    name VARCHAR(100),\n" +
                "    registryDate VARCHAR(100),\n" +
                "    image VARCHAR(500),\n" +
                "    PRIMARY KEY(id)\n" +
                ");");
        int[] updateCounts = stmt.executeBatch();
        stmt.close();
        con.close();
//        TODO: Complete this
//        fillTables();
    }
}
