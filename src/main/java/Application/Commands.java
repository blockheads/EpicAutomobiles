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

    /**
     * Gets the sales of a specific brand name listed by name and year and amount of the
     * particular models of that brand sold.
     * @param con
     * @param brand
     */
    public static void salesofBrand(Connection con, String brand){
        String Query = "SELECT m.name, m.year, COUNT(m.name) " +
                        "FROM model AS m JOIN vehicle AS v " +
                        "ON v.carmodel = m.modelID " +
                        "JOIN sale as s " +
                        "ON s.vehiclepurchased = v.vin " +
                        "GROUP BY m.name " +
                        "WHERE m.brandName = " + brand + ";";
    }

    /**
     * Gets the sales of a particular model and year, given listed as a count
     * @param con
     * @param model
     * @param year
     */
    public static void salesofModel(Connection con, String model, String year){
        String Query = "SELECT m.name, m.year, COUNT(m.name) " +
                "FROM model AS m JOIN vehicle AS v " +
                "ON v.carmodel = m.modelID " +
                "JOIN sale as s " +
                "ON s.vehiclepurchased = v.vin " +
                "GROUP BY m.name " +
                "WHERE m.name = " + model + " " +
                "AND m.year = " + year + ";";
    }

    /**
     * Groups by brands and returns the sales of each brand
     * @param con
     */
    public static void salesofBrands(Connection con){
        String Query = "SELECT b.brandName, COUNT(m.name) " +
                "FROM model AS m JOIN vehicle AS v " +
                "ON v.carmodel = m.modelID " +
                "JOIN sale as s " +
                "ON s.vehiclepurchased = v.vin " +
                "JOIN brand as b " +
                "ON b.brandName = m.modelBrand " +
                "GROUP BY b.brandName;";

    }

    /**
     * Selects dealers with the given vehicle model and year
     * @param con
     * @param model
     * @param year
     */
    public static void VehicleLookupDealers(Connection con, String model, String year){
        String Query = "SELECT d.dealerid, d.Firstname, d.Lastname, COUNT(m.name) " +
                "FROM model AS m JOIN vehicle AS v " +
                "ON v.carmodel = m.modelID " +
//                "JOIN brand as b " +
//                "ON b.brandName = m.modelBrand " +
                "JOIN dealer as d " +
                "ON d.inventoryID = v.inventoryin " +
                "GROUP BY d.dealerid " +
                "WHERE m.name = " + model + " " +
                "AND m.year = " + year +
                "HAVING COUNT(m.name) > 0;";
    }

}
