package controller;

import dao.ProductDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateProductController implements Initializable {
    Product product;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField barcodeField;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField stockField;
    @FXML
    private TextField stockDescField;
    @FXML
    private Button backButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    private ProductDAO productDAO;

    public UpdateProductController(Product product) {
        this.product = product;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.productDAO = new ProductDAO();
        //ketika back button diklik (kembali ke main view)
        backButton.setOnAction(event -> {
            switchToMainView(event);
        });

        //ketika edit button diklik
        editButton.setOnAction(event -> {
            editProduct(event);
        });

        //ketika delete button diklik
        deleteButton.setOnAction(event -> {
            deleteProduct(event);
        });

        //set textfield dengan data product yang dipilih
        setData();

        //mencegah user memasukkan input selain angka di field harga dan stok
        priceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                priceField.setText(newValue.replaceAll("['\\D']", ""));
            }
        });
        stockField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                stockField.setText(newValue.replaceAll("['\\D']", ""));
            }
        });
    }

    public void setData() {
        barcodeField.setText(product.getBarcode());
        productNameField.setText(product.getName());
        priceField.setText(String.valueOf(product.getPrice()));
        stockField.setText(String.valueOf(product.getStock()));
        stockDescField.setText(product.getStockDesc());
    }

    //edit product
    public void editProduct(ActionEvent event) {

        try {
            if (!isFieldEmpty()){
                int id = product.getId();
                String barcode = barcodeField.getText();
                String name = productNameField.getText();
                int price = Integer.parseInt(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());
                String stockDesc = stockDescField.getText();
                Product product = new Product(id, barcode, name, price, stock, stockDesc);
                productDAO.update(product.getId(), product.getBarcode(), product.getName(), product.getPrice(), product.getStock(), product.getStockDesc());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Sukses mengupdate produk");
                alert.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Input tidak boleh kosong");
                alert.show();
            }
        } catch (Exception e) {
            e.getMessage();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Gagal mengupdate produk");
            alert.show();
        }
    }

    //delete product
    public void deleteProduct(ActionEvent event) {
        try {
            productDAO.delete(product.getId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Sukses menghapus produk");
            alert.show();
            switchToMainView(event);
        } catch (Exception e) {
            e.getMessage();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Gagal menghapus produk");
            alert.show();
        }
    }

    //cek apakah field kosong
    public boolean isFieldEmpty(){
        if(barcodeField.getText().isEmpty() || productNameField.getText().isEmpty() || priceField.getText().isEmpty() || stockField.getText().isEmpty() || stockDescField.getText().isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    //kembali ke main view
    public void switchToMainView(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


