package Application;

import java.sql.*;
import java.io.*;
import java.util.*;

public class Initialize {
    
    public static void initialize(Connection con) {
        //insert(con, "brand");
        //insert(con, "customer");
        //insert(con, "inventory");
        //insert(con, "dealer");
        //insert(con, "model");
        //insert(con, "vehicle");
        insert(con, "sale");

    }

    /*
    public static void delete(Connection con, String table) {
        String del = "delete from " + table + ";";
        PreparedStatement d = null;
        try {
            d = con.prepareStatement(del);
            d.executeUpdate();
            con.commit();
            System.out.println(table + " data was deleted");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    */
    
    public static void insert(Connection con, String table) {
        String insert;
        int numArgs = 0;
        
        switch(table) {
        case "brand":
            numArgs = 2;
            insert = "insert into brand(brandname, country) values(?,?);";
            break;
        case "customer":
            numArgs = 10;
            insert = "insert into customer(ssn, firstname, lastname, phone, gender, annualincome, streetaddress, city, zipcode, state) values(?,?,?,?,?,?,?,?,?,?);";
            break;
        case "dealer":
            numArgs = 6;
            insert = "insert into dealer(dealerid, firstname, lastname, phone, brandsold, dealerinv) values(?,?,?,?,?,?);";
            break;
        case "inventory":
            numArgs = 5;
            insert = "insert into inventory(inventoryid, street, city, zipcode, state) values(?,?,?,?,?);";
            break;
        case "model":
            numArgs = 6;
            insert = "insert into model(modelid, name, year, fuelefficiency, fueltype, modelbrand) values(?,?,?,?,?,?);";
            break;
        case "sale":
            numArgs = 6;
            insert = "insert into sale(saleid, price, date, vehiclepurchased, soldto, soldby) values(?,?,?,?,?,?);";
            break;
        case "vehicle":
            numArgs = 7;
            insert = "insert into vehicle(vin, color, transmission, engine, carmodel, inventoryin, ownedby) values(?,?,?,?,?,?,?);";
            break;
        default:
            System.out.println("invalid table");
            return;
        }

        try {
            PreparedStatement i = null;
            File file = new File("data-gen/" + table + ".csv");
            BufferedReader br = new BufferedReader(new FileReader(file));
        
            String st;
            int ctr = 0;

            while ((st = br.readLine()) != null) {
                System.out.println(st);
                String[] parts = st.split(",");
                for (int v = 0; v < parts.length; v++) {
                    System.out.println("before:" + parts[v]);
                    parts[v] = parts[v].replaceAll("\'","");
                    parts[v] = parts[v].trim();
                    System.out.println("after:" + parts[v]);
                }
                
                i = con.prepareStatement(insert);
                switch(table) {
                case "brand":
                    for (int x = 0; x < numArgs; x++) {
                        i.setString(x+1, parts[x]);
                    }
                    break;
                case "customer":
                    for (int x = 0; x < numArgs; x++) {
                        if (x == 5) {
                            i.setInt(x+1, Integer.parseInt(parts[x]));
                            continue;
                        }
                        i.setString(x+1, parts[x]);
                    }
                    break;
                case "dealer":
                    for (int x = 0; x < numArgs; x++) {
                        if (x == 5) {
                            i.setInt(x+1, Integer.parseInt(parts[x]));
                            continue;
                        }
                        i.setString(x+1, parts[x]);
                    }
                    break;
                case "inventory":
                    for (int x = 0; x < numArgs; x++) {
                        if (x == 0) {
                            i.setInt(x+1, Integer.parseInt(parts[x]));
                            continue;
                        }
                        i.setString(x+1, parts[x]);
                        System.out.println(x+1 + "," + parts[x]);
                    }
                    break;
                case "model":
                    for (int x = 0; x < numArgs; x++) {
                        if (x == 2 || x == 3) {
                            int num = Integer.parseInt(parts[x]);
                            i.setInt(x+1, num);
                            System.out.println("whats going in:" + num);
                            continue;
                        }
                        i.setString(x+1, parts[x]);
                    }
                    break;
                case "sale":
                    for (int x = 0; x < numArgs; x++) {
                        if (x == 1) {
                            int num = Integer.parseInt(parts[x]);
                            i.setInt(x+1, num);
                            continue;
                        }
                        if (x == 2) {
                            i.setDate(x+1, java.sql.Date.valueOf(parts[x]));
                            continue;
                        }
                        i.setString(x+1, parts[x]);
                    }
                    break;

                case "vehicle":
                    System.out.println("ctr = " + ctr);
                    for (int x = 0; x < numArgs; x++) {
                        if (x == 5) {
                            i.setInt(x+1, Integer.parseInt(parts[x]));
                            continue;
                        }
                        if ((x == 6) && (ctr%12 == 5)) 
                            i.setString(x+1, null);
                        i.setString(x+1, parts[x]);
                        ctr++;
                    }
                    break;
                }
                System.out.println(i);
                i.executeUpdate();

                con.commit();
                System.out.println("inserted data into table " + table);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    
    }
    
}
