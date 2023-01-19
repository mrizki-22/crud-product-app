package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

public class AddProductController implements Initializable {

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
    private Button addButton;

    private ProductDAO productDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.productDAO = new ProductDAO();

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

    //ketika button tambah diklik
    public void addProduct(){
        try {
            if(!isFieldEmpty()) {
                String barcode = barcodeField.getText();
                String productName = productNameField.getText();
                int price = Integer.parseInt(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());
                String stockDesc = stockDescField.getText();
                productDAO.create(barcode, productName, price, stock, stockDesc);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Sukses menambahkan produk");
                alert.show();

                //clear text field
                barcodeField.clear();
                productNameField.clear();
                priceField.clear();
                stockField.clear();
                stockDescField.clear();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Mohon isi semua input");
                alert.show();
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Gagal menambahkan produk");
            alert.show();
        }
    }

    public boolean isFieldEmpty(){
        if(barcodeField.getText().isEmpty() || productNameField.getText().isEmpty() || priceField.getText().isEmpty() || stockField.getText().isEmpty() || stockDescField.getText().isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public void switchToMainView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
