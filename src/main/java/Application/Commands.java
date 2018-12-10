package Application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Application.SQLBase.executeQuery;

public class Commands {


    /**
     * Gets the sales of a specific brand name listed by name and year and amount of the
     * particular models of that brand sold.
     * @param con
     * @param brand
     */
    public static void salesOfBrand(Connection con, String brand) {

        ResultSet rs = null;

        try {
            PreparedStatement statement =  con.prepareStatement("SELECT m.name, m.year, COUNT(m.name) " +
                    "FROM Model m JOIN Vehicle v " +
                    "ON m.modelID = v.carModel " +
                    "JOIN Sale s " +
                    "ON s.vehiclePurchased = v.vin " +
                    "WHERE m.brandName = (?) " +
                    "GROUP BY m.name, m.year;");


            statement.setString(1,brand);

            //String query = "Select * FROM brand";


            rs = executeQuery(con, statement);

            System.out.format("%-15s%-15s%-15s","Name","Year", "Count");
            System.out.println();

            while(rs.next()) {
                System.out.format("%-15s%-15s%-15s", rs.getString(1), rs.getString(2),"null");
                System.out.println();
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong.");
        } finally {
            try {
                if (rs != null) { rs.close(); }
            } catch (SQLException e) {
                System.err.println("Something went REALLY wrong.");
            }
        }

//        ResultSet rs = generateResult(con,query);


//        System.out.println("Brand       Name        Year    Amount");
//        System.out.println("Ford        Ecosport    2005    1432");
//        System.out.println("Ford        Flex        2010    1326");
//        System.out.println("Ford        Fiesta_ST   2014    1286");
//        System.out.println("...");
    }

    //Displays sales made to a customer with a specified first name last name
    public static void customerLookup(Connection con, String fName, String lName) {

        // simply making the first and lastname uppercase in case the user forgot to do so himself
        // our database stores the first name and last name starting with upper case letters
        fName = fName.substring(0, 1).toUpperCase() + fName.substring(1);
        lName = lName.substring(0, 1).toUpperCase() + lName.substring(1);

        ResultSet rs = null;

        try {
            System.out.println(con);
            System.out.println(con.isClosed());

            PreparedStatement statement = con.prepareStatement("SELECT * FROM customer WHERE firstName = (?)"+
                    "AND lastName = (?);");

            statement.setString(1, fName);
            statement.setString(2, lName);

            rs = executeQuery(con, statement);


            System.out.format("%-15s%-15s%-15s%-10s%-10s%-30s%-20s%-10s%-10s%-15s","SSN","First Name", "Last Name", "Gender", "Income",
                    "Street Address", "City", "Zip Code", "State", "Phone" );
            System.out.println();

            while(rs.next()) {
                System.out.format("%-15s%-15s%-15s%-10s%-10s%-30s%-20s%-10s%-10s%-15s", rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6), rs.getString(7),
                        rs.getString(8),rs.getString(9), rs.getString(10));
                System.out.println();
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong.");
        } finally {
            try {
                if (rs != null) {

                    rs.close();
                }
            } catch (SQLException e) {
                System.err.println("Something went REALLY wrong.");
            }
        }

    }
    /**
     * Gets the sales of a particular model and year, given listed as a count
     * @param con
     * @param model
     * @param year
     */

    //model = 'thatone' year = '2017' has 2 vehicles
    public static void salesOfModel(Connection con, String model, String year){


        ResultSet rs = null;
        try {

            PreparedStatement statement = con.prepareStatement("SELECT m.name, m.year, COUNT(m.name) " +
                    "FROM model AS m JOIN vehicle AS v " +
                    "ON v.carmodel = m.modelid " +
                    "JOIN sale as s " +
                    "ON s.vehiclepurchased = v.vin " +
                    "WHERE m.name = (?) " +
                    "AND m.year = (?) " +
                    "GROUP BY m.name, m.year;");

            statement.setString(1, model);
            statement.setString(2, year);

            rs = executeQuery(con, statement);
            System.out.format("%-15s%-15s%-15s\n", "Model", "Year", "Number Sold");
            
            while(rs.next()) {
                System.out.format("%-15s%-15s%-15s\n", rs.getString(1), rs.getInt(2), rs.getInt(3));

            }
            
        } catch (SQLException e) {
            System.err.println("salesOfModel broke");
        } finally {
            try {
                if (rs != null) { rs.close(); }
            } catch (SQLException e) {
                System.err.println("Something went REALLY wrong.");
            }
        }

        System.out.println("Brand       Name        Year    Amount");
        System.out.println("Ford        Ecosport    2005    1432");
    }

    /**
     * Groups by brands and returns the sales of each brand
     * @param con
     */
    public static void salesOfBrands(Connection con){
        String Query = "SELECT b.brandName, COUNT(m.name) " +
                "FROM model AS m JOIN vehicle AS v " +
                "ON v.carmodel = m.modelID " +
                "JOIN sale as s " +
                "ON s.vehiclepurchased = v.vin " +
                "JOIN brand as b " +
                "ON b.brandName = m.modelBrand " +
                "GROUP BY b.brandName;";



        System.out.println("Brand       Amount");
        System.out.println("Ford        35643");
        System.out.println("Toyota      32982");
        System.out.println("Chevrolet   23789");
        System.out.println("...");
    }

    /**
     * Selects dealers with the given vehicle model and or year
     * @param con
     * @param model
     * @param year
     */
    public static void vehicleLookupDealers(Connection con, String model, String year){
        String Query = "SELECT d.dealerid, d.Firstname, d.Lastname, COUNT(m.name) " +
                "FROM model AS m JOIN vehicle AS v " +
                "ON v.carmodel = m.modelID " +
//                "JOIN brand as b " +
//                "ON b.brandName = m.modelBrand " +
                "JOIN dealer as d " +
                "ON d.inventoryID = v.inventoryin " +
                "GROUP BY d.dealerid " +
                "HAVING COUNT(m.name) > 0" +
                "WHERE m.name = " + model + " " +
                "AND m.year = " + year + ";";
        System.out.println("Name                Location        Count");
        System.out.println("FordDealership1     NewYork NY      1");
        System.out.println("FordDealership5     Los Angeles CA  3");
        System.out.println("...");
    }


    // ssn = 149861605 has 2 sales
    
    // Displays sale of a given customer
    public static void salesOfCustomer(Connection con, String ssn) {
        String Query = "SELECT firstName, lastName, saleid, price, date, vehiclepurchased, soldby  FROM customer JOIN sale ON sale.soldto=customer.ssn WHERE sale.soldto = "
                + ssn +";";
        System.out.println("SaleID      Price       Date");
        System.out.println("134565      5600.00     7/9/2015");
        System.out.println("...");

    }

    public static void registerCustomer(Connection con, String ssn, String firstName, String lastName){
        String Query = "INSERT INTO customer (ssn, firstName, lastName, lastName) " +
                "VALUES ('" + ssn + "', '" + firstName +"', '" + lastName + "');";
        System.out.println("Customer " + firstName + " " + lastName + " successfully registered!");
    }

    /**
     * Adds a vehicle to the database with the specified information
     */
    public static void addVehicle(Connection con, String vin, String color, String transmission, String engine, String modelKey, String inventoryKey){
        // query for the
        System.out.println("Vehicle " + vin + " succesfully added to database.");
    }

    /**
     * Purchases A vehicle
     */
    public static void purchaseVehicle(Connection con, String ssn, String vin){
        // query for the
        System.out.println("Vehicle " + vin + " succesfully purchased by " + ssn);
    }
    
}
