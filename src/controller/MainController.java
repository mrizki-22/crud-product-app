package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import model.Product;
import dao.ProductDAO;


public class MainController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField textInput;

    @FXML
    private TextField filterInput;

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn<Product, Integer> idColumn;

    @FXML
    private TableColumn<Product, String> barcodeColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, Integer> priceColumn;

    @FXML
    private TableColumn<Product, Integer> stockColumn;

    @FXML
    private TableColumn<Product, String> stockDescColumn;


    private ProductDAO productDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.productDAO = new ProductDAO();
        liveSearch();
        filterStock();
        showProducts(getProductsList());
        onRowClick();

    }


    //live search method
    public void liveSearch() {
        textInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showProducts(getSearchProduct(newValue));
            } else {
                //menampilkan data produk
                showProducts(getProductsList());
            }
        });
    }

    //filter stock method
    public void filterStock() {

        //number only
        filterInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                filterInput.setText(newValue.replaceAll("['\\D']", ""));
            }
        });

        filterInput.textProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.equals("")) {
                showProducts(getProductsList());
            } else if (newValue != null) {
                showProducts(getFilterProduct(Integer.parseInt(newValue)));
            }
        });
    }

    //select all products
    public ObservableList<Product> getProductsList() {
        ObservableList<Product> productsList = FXCollections.observableArrayList();
        try {
            ResultSet rs = productDAO.findAll();
            Product product;
            while (rs.next()) {
                product = new Product(rs.getInt("id"), rs.getString("barcode"), rs.getString("name"), rs.getInt("price"), rs.getInt("stock"), rs.getString("stockDesc"));
                productsList.add(product);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Gagal terhubung ke database");
            alert.show();
            System.out.println(e.getMessage());
        }
        return productsList;
    }

    //select product yang dicari
    public ObservableList<Product> getSearchProduct(String keyword) {
        ObservableList<Product> productsList = FXCollections.observableArrayList();

        ResultSet rs = productDAO.search(keyword);
        try {
            Product product;
            while (rs.next()) {
                product = new Product(rs.getInt("id"), rs.getString("barcode"), rs.getString("name"), rs.getInt("price"), rs.getInt("stock"), rs.getString("stockDesc"));
                productsList.add(product);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Gagal terhubung ke database");
            alert.show();
            System.out.println(e.getMessage());
        }
        return productsList;
    }

    //filter produk
    public ObservableList<Product> getFilterProduct(int stock) {
        ObservableList<Product> productsList = FXCollections.observableArrayList();

        ResultSet rs = productDAO.filter(stock);
        try {
            Product product;
            while (rs.next()) {
                product = new Product(rs.getInt("id"), rs.getString("barcode"), rs.getString("name"), rs.getInt("price"), rs.getInt("stock"), rs.getString("stockDesc"));
                productsList.add(product);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Gagal terhubung ke database");
            alert.show();
            System.out.println(e.getMessage());
        }
        return productsList;
    }


    //menampilkan data ke tableview
    public void showProducts(ObservableList<Product> list) {
        ObservableList<Product> productsList = list;



        idColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        barcodeColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("barcode"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        stockDescColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("stockDesc"));

        tableView.setItems(productsList);

    }

    //saat baris di double klik
    public void onRowClick(){
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Product selectedProduct = (Product) tableView.getSelectionModel().getSelectedItem();
                switchToEditProductView( selectedProduct);
            }
        });
    }


    //Ganti halaman ke tambah produk
    public void switchToAddProductView(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/view/AddProductView.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //ganti ke halaman edit produk
    public void switchToEditProductView( Product product) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UpdateProductView.fxml"));
            UpdateProductController updateProductController = new UpdateProductController(product);
            fxmlLoader.setController(updateProductController);
            root = fxmlLoader.load();
            stage = (Stage) tableView.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
