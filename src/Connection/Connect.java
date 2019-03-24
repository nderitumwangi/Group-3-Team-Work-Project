/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    Connection conn = null;
    public static Connection conDB()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/autopedia_cdms", "root", "");
            System.out.println("Connection Successful");
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Connect : "+ex.getMessage());
           return null;
        }
    }
}
