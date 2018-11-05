package Application;


import java.sql.Connection;
import java.sql.SQLException;

import static Application.SQLBase.getConnection;

public class Main {

    public static void main(String args[]) throws SQLException {
        Connection con = getConnection("epic_auto");
        Interface ui = new Interface(con);
        ui.start();
    }
}
