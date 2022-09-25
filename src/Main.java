import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource(Const.SAPER_SCENE));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root=loader.getRoot();
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}