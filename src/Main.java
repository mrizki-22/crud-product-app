//import javafx
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = (Parent) FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
        Scene scene = new Scene(parent);

        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/asset/icon.jpg")));
        stage.setScene(scene);
        stage.setTitle("Aplikasi Pengelola Produk");
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

