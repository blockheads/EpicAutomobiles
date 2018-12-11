package Application;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

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
            PreparedStatement statement =  con.prepareStatement("SELECT m.name, m.year, SUM(s.price) " +
                    "FROM Model AS m JOIN Vehicle AS v " +
                    "ON m.modelID = v.carModel " +
                    "JOIN Sale AS s " +
                    "ON s.vehiclePurchased = v.vin " +
                    "WHERE m.modelBrand = (?) " +
                    "GROUP BY m.name, m.year;");


            statement.setString(1,brand);

            //String query = "Select * FROM brand";


            rs = executeQuery(con, statement);

            System.out.format("%-15s%-15s%-15s","Name","Year", "Sales Totals");
            System.out.println();

            while(rs.next()) {
                System.out.format("%-15s%-15s%-15s", rs.getString(1), rs.getString(2), rs.getString(3));
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

    }

    //Displays sales made to a customer with a specified first name last name
    public static void customerLookup(Connection con, String fName, String lName) {

        // simply making the first and lastname uppercase in case the user forgot to do so himself
        // our database stores the first name and last name starting with upper case letters
        fName = fName.substring(0, 1).toUpperCase() + fName.substring(1);
        lName = lName.substring(0, 1).toUpperCase() + lName.substring(1);

        ResultSet rs = null;

        try {

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

    //model = 'thatone' year = '2012' has 2 vehicles
    public static void salesOfModel(Connection con, String model, int year){


        ResultSet rs = null;
        try {

            PreparedStatement statement = con.prepareStatement("SELECT m.modelbrand, m.name, m.year, COUNT(m.name) " +
                    "FROM model AS m JOIN vehicle AS v " +
                    "ON v.carmodel = m.modelid " +
                    "JOIN sale as s " +
                    "ON s.vehiclepurchased = v.vin " +
                    "WHERE m.name = (?) " +
                    "AND m.year = (?) " +
                    "GROUP BY m.name, m.year, m.modelbrand;");

            statement.setString(1, model);
            statement.setInt(2, year);

            rs = executeQuery(con, statement);

            System.out.format("%-15s%-15s%-15s%-15s\n", "Brand", "Model", "Year", "Number Sold");

            while(rs.next()) {
                System.out.format("%-15s%-15s%-15s%-15s\n", rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4));

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
    }

    /**
     * Groups by brands and returns the sales of each brand
     * @param con
     */
    public static void salesOfBrands(Connection con){


        ResultSet rs = null;

        try {

            PreparedStatement statement = con.prepareStatement("SELECT b.brandName, COUNT(m.name) " +
                    "FROM model AS m JOIN vehicle AS v " +
                    "ON v.carmodel = m.modelID " +
                    "JOIN sale as s " +
                    "ON s.vehiclepurchased = v.vin " +
                    "JOIN brand as b " +
                    "ON b.brandName = m.modelBrand " +
                    "GROUP BY b.brandName;");

            rs = executeQuery(con, statement);


            System.out.format("%-15s%-15s","Brand Name","Models");
            System.out.println();

            while(rs.next()) {

                System.out.format("%-15s%-15s\n", rs.getString(1), rs.getString(2));
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
     * Sales Of Brands which can be specified a time range
     * @param con
     */
    public static void salesOfBrandsTimerange(Connection con, String startDateString, String endDateString){

        ResultSet rs = null;

        Date startDate = Date.valueOf(startDateString);
        Date endDate = Date.valueOf(endDateString);

        try {

            PreparedStatement statement = con.prepareStatement("SELECT b.brandName, s.date " +
                    "FROM model AS m JOIN vehicle AS v " +
                    "ON v.carmodel = m.modelID " +
                    "JOIN sale as s " +
                    "ON s.vehiclepurchased = v.vin " +
                    "JOIN brand as b " +
                    "ON b.brandName = m.modelBrand " +
                    "GROUP BY b.brandName, s.date;");

            rs = executeQuery(con, statement);

            Calendar startCal = Calendar.getInstance();
            startCal.setTime(startDate);

            Calendar endCal = Calendar.getInstance();
            startCal.setTime(endDate);

            System.out.format("%-15s%-15s","Brand Name","Models");
            System.out.println();

            class BrandStorage implements Comparator<BrandStorage> {
                String brand;
                int count;

                BrandStorage(String brand, int count){
                    this.brand = brand;
                    this.count = count;
                }

                BrandStorage(){}

                @Override
                public int compare(BrandStorage o1, BrandStorage o2) {
                    return o2.count - o1.count;
                }
            }

            ArrayList<BrandStorage> brandStorages = new ArrayList<>();

            while(rs.next()) {

                Date date = rs.getDate(2);
                String brand = rs.getString(1);

                if(!startCal.after(date) || !endCal.before(date)){

                    boolean addedBrand = false;

                    for(BrandStorage brandStorage: brandStorages){
                        if(brandStorage.brand.equals( brand )){
                            brandStorage.count++;
                            addedBrand = true;
                            break;
                        }

                    }
                    if(!addedBrand) {
                        brandStorages.add(new BrandStorage(brand, 1));
                    }
                }

            }

            brandStorages.sort(new BrandStorage());

            for(BrandStorage brandStorage: brandStorages){
                System.out.format("%-15s%-15s\n",brandStorage.brand,brandStorage.count);
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

    public static void salesOfBrandsDollarAmount(Connection con){

        ResultSet rs = null;

        try {

            PreparedStatement statement = con.prepareStatement("SELECT b.brandName, SUM(s.price), COUNT(m.name) " +
                    "FROM model AS m JOIN vehicle AS v " +
                    "ON v.carmodel = m.modelID " +
                    "JOIN sale as s " +
                    "ON s.vehiclepurchased = v.vin " +
                    "JOIN brand as b " +
                    "ON b.brandName = m.modelBrand " +
                    "GROUP BY b.brandName " +
                    "ORDER BY SUM(s.price) DESC;");

            rs = executeQuery(con, statement);


            System.out.format("%-15s%-15s%-15s","Brand Name","Total Sales", "Amount");
            System.out.println();

            while(rs.next()) {

                System.out.format("%-15s%-15s%-15s\n", rs.getString(1), rs.getString(2),
                        rs.getString(3));
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
     * Selects dealers with the given vehicle model and or year
     * @param con
     * @param model
     * @param year
     */
    public static void vehicleLookupDealers(Connection con, String model, String year){


        ResultSet rs = null;

        try {

            PreparedStatement statement = con.prepareStatement("SELECT d.dealerid, d.Firstname, d.Lastname, COUNT(m.name) " +
                    "FROM model AS m JOIN vehicle AS v " +
                    "ON v.carmodel = m.modelID " +
//                "JOIN brand as b " +
//                "ON b.brandName = m.modelBrand " +
                    "JOIN dealer AS d " +
                    "ON d.dealerinv = v.inventoryin " +
                    "WHERE m.name = (?) " +
                    "AND m.year = (?) " +
                    "GROUP BY d.dealerid " +
                    "HAVING COUNT(m.name) > 0;");

            statement.setString(1,model);
            statement.setString(2,year);

            rs = executeQuery(con, statement);

            System.out.format("%-20s%-20s%-20s%-20s","Dealer ID","First Name", "Last Name", "Vehicles In Stock");
            System.out.println();

            while(rs.next()) {
                System.out.format("%-20s%-20s%-20s%-20s\n", rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4));
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


    // ssn = 149861605 has 2 sales

    // Displays sale of a given customer
    public static void salesOfCustomer(Connection con, String ssn) {

        ResultSet rs = null;

        try {

            PreparedStatement statement = con.prepareStatement("SELECT firstName, lastName, saleid, price, date, " +
                    "vehiclepurchased, soldby  FROM customer JOIN sale ON sale.soldto = customer.ssn WHERE sale.soldto = "
                    + "(?);");

            statement.setString(1,ssn);

            rs = executeQuery(con, statement);

            System.out.format("%-20s%-20s%-20s%-20s%-20s%-25s%-20s\n","First Name", "Last Name", "Sale ID", "Price", "Date", "Vehicle Purchased", "Sold By");

            while(rs.next()) {
                System.out.format("%-20s%-20s%-20s%-20s%-20s%-25s%-20s\n", rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)
                ,rs.getString(7));
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

    public static void registerCustomer(Connection con, String ssn, String firstName, String lastName, String phoneNumber,
                                        String gender, int annualIncome, String streetAddress, String city, String zipCode,
                                        String state){

        ResultSet rs = null;

        try {

            PreparedStatement statement = con.prepareStatement("INSERT INTO customer (ssn, firstName, lastName, phone, " +
                    "gender, annualIncome, streetAddress, city, zipcode, state) " +
                    "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? );");

            statement.setString(1,ssn);
            statement.setString(2,firstName);
            statement.setString(3,lastName);
            statement.setString(4,phoneNumber);
            statement.setString(5,gender);
            statement.setInt(6, annualIncome);
            statement.setString(7, streetAddress);
            statement.setString(8, city);
            statement.setString(9, zipCode);
            statement.setString(10, state);

            statement.executeUpdate();


        } catch (SQLException e) {
            System.err.println("Something went wrong.");
        }



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
