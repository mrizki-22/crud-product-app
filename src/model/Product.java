package model;

public class Product {
    private int id;
    private String barcode;
    private String name;
    private int price;
    private int stock;
    private String stockDesc;

    public Product(int Id, String Barcode, String Name, int Price, int Stock, String StockDesc){
        this.id = Id;
        this.barcode = Barcode;
        this.name = Name;
        this.price = Price;
        this.stock = Stock;
        this.stockDesc = StockDesc;
    }

    public int getId() {
        return id;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getStockDesc() {
        return stockDesc;
    }
}
