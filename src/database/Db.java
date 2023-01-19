package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Db {
    private String url = "jdbc:mysql://localhost:3306/product_db";
    private String username = "root";
    private String password = "";

    //Konek ke Database
    public Connection connect() {
        Connection conn;
        try{
            conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (Exception e) {
            System.out.println("Gagal terkoneksi ke database");
            return null;
        }
    }
}
