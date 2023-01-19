package dao;

import database.Db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class ProductDAO {
    private Connection conn;

    public ProductDAO() {
        Db db = new Db();
        this.conn = db.connect();
    }

    //select all
    public ResultSet findAll() throws Exception {
        String query = "SELECT * FROM products";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //create data
    public void create(String barcode ,String name, int price, int stock, String stockDesc) throws Exception {
        String query =
                "INSERT INTO products (barcode, name, price, stock, stockDesc) VALUES " +
                        "('"+barcode+"', '"+name+"', '"+price+"', '"+stock+"', '"+stockDesc+"')";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("error catch(): "+ e.getMessage());
            throw e;
        }
    }

    //search data
    public ResultSet search(String keyword) {
        String query = "SELECT * FROM products WHERE name LIKE '%" + keyword +
                "%' OR barcode LIKE '%" + keyword + "%'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //filter stock
    public ResultSet filter(int stock) {
        String query = "SELECT * FROM products WHERE stock < " + stock;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //edit produk
    public void update(int id, String barcode, String name, int price, int stock, String stockDesc) throws Exception {
        String query = "UPDATE products SET barcode = '"+barcode+"', name = '"+name+"', price = '"+price+"', stock = " +
                "'"+stock+"', stockDesc = '"+stockDesc+"' WHERE id = '"+id+"'";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("error catch(): "+ e.getMessage());
            throw e;
        }
    }

    //delete produk
    public void delete(int id) throws Exception {
        String query = "DELETE FROM products WHERE id = '"+id+"'";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("error catch(): "+ e.getMessage());
            throw e;
        }
    }
}
