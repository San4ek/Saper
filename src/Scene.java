import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Scene {

    public void showScene(String sceneName, Button button) {
        Stage window = (Stage) button.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(sceneName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        window.setScene(new javafx.scene.Scene(Objects.requireNonNull(root)));
    }
}