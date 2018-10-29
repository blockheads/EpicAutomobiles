package Application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Application.SQLBase.executeQuery;
import static Application.SQLBase.getConnection;

public class Commands {

    public static void runSimpleCommand(String tablename, String Query){
        Connection con = null;
        ResultSet rs = null;
        try {
            con = getConnection(tablename);
            rs = executeQuery(con, Query);
            // we are going to want to change how we print out later probably
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            con.close();
        } catch (SQLException e) {
            System.err.println("Something went wrong.");
        } finally {
            try {
                if (rs != null) { rs.close(); }
            } catch (SQLException e) {
                System.err.println("Something went REALLY wrong.");
            }
        }
    }

    public static void SampleCommand(String[] args){
        runSimpleCommand("TestTable","TestQuery;");
    }
}
