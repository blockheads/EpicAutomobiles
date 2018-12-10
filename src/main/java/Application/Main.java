package Application;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.io.*;

import static Application.SQLBase.getConnection;

public class Main {

    public static void main(String args[]) throws SQLException {
        Connection con = getConnection("epic_auto");
        Interface ui = new Interface(con);

        try {
            con.setAutoCommit(false);

            //Initialize.initialize(con);
                
            
        } catch(Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (con != null) {
                con.close();
            }
        }
        ui.start();

    }
}
