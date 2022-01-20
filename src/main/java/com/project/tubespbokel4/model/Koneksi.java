package com.project.tubespbokel4.model;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Koneksi {

    static final String C_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/belajar_pbo_tubeskel4";

    static final String USER = "root";
    static final String PASS = "";

    public static PreparedStatement pstm;
    public static ResultSet rs;

    /**
     *
     */

    public static Connection connection(){
        Connection conn = null;

        try{
//            Class.forName(C_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

        }catch (SQLException ex) {
            ex.printStackTrace();
        }

        return conn;
    }
}
