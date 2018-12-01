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

        PreparedStatement insertBrand = null;
        PreparedStatement insertCustomer = null;
        PreparedStatement insertDealer = null;
        PreparedStatement insertInventory = null;
        PreparedStatement insertModel = null;
        PreparedStatement insertSale = null;
        PreparedStatement insertVehicle = null;

        
        try {
            con.setAutoCommit(false);

            File file = new File("data-gen/brand.csv");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                String[] parts = st.split(",");
                for (String a: parts)
                    System.out.println(a);
                
                String addBrand = "insert into brand(brandname, country) values" + "(?,?)";
                insertBrand = con.prepareStatement(addBrand);
                
            }
        } catch(Exception e) {

        }
        ui.start();

    }
}
