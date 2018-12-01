package Application;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class SQLBase {
    public static Connection getConnection() throws SQLException {
        return getConnection(null);
    }

    public static Connection getConnection(String schema) throws SQLException {
        // Read the password file
        // should be ~/.pgpass and look like
        //
//        String homeDir = System.getProperty("user.home");
//        System.out.println(homeDir);
//        String content = null;
//
//        try {
//            content = new Scanner(new File(homeDir, ".pgpass")).useDelimiter("\\Z").next();
//        } catch (FileNotFoundException e) {
//            System.err.println("Could not load password file.");
//            System.exit(1);
//        }

        String content = null;

        try {
            File file = new File("password.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            content = stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(content == null){
            System.err.println("create password.txt file in root folder...");
            return null;
        }

        System.out.println(content);
        String parts[] = content.split(":");
        String host = parts[0];
        String port = parts[1];
        String username = parts[2];
        System.out.println("username: " + username);
        String password = parts[4];
        System.out.println("password: " + password);

        String url = "jdbc:postgresql://" + host + ":" + port + "/sft6463";
        if (schema != null) {
            url += "?currentSchema=" + schema;
        }
        return DriverManager.getConnection(url, username, password);
    }

    public static ResultSet executeQuery(Connection con, String sql) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            stmt.closeOnCompletion();
        } catch (SQLException e) {
            System.err.println("Something went wrong.");
        }
        return rs;
    }
}
