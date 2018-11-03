package Application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Application.SQLBase.executeQuery;

public class Commands {

    public static void runSimpleCommand(Connection con, String Query){
        ResultSet rs = null;
        try {
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

    public static void Salesof(Connection con,String[] args){
        String Query = "SELECT ";
        runSimpleCommand(con,"TestQuery;");
    }

    public static void SalesofBrand(Connection con, String brand){
        String Query = "SELECT m.name, m.year, COUNT(m.name)" +
                        "FROM model AS m JOIN vehicle AS v" +
                        "ON v.carmodel = m.modelID" +
                        "JOIN sale as s" +
                        "ON s.vehiclepurchased = v.vin" +
                        "GROUP BY m.name" +
                        "WHERE m.brandName =" + brand;
    }

    //Displays sales made to a customer with a specified first name last name
    public static void customerLookup(Connection con, String fName, String lName) {
        String Query = "SELECT * FROM customer AS c " + 
                       "WHERE c.firstName = " + fName +
            " AND c.lastName = " + lName + ";";
    }

    public static void salesOfCustomer(Connection con, String fName, String lName) {
        String Query = "WITH customers(firstName, lastName, vehicle) AS (SELECT firstName, lastName, vehicleOwned FROM customer AS c " +
            "WHERE c.firstName = " + fName + " AND c.lastName = " + lName + ") " +
            "SELECT * FROM customers JOIN sale WHERE customers.vehicle = sale.vehiclePurchased;"
    }
    
}
