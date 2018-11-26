package julia_set_paquage;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller_interface_julia_set {


    @FXML
    JFXButton btn_retour;
    @FXML
    JFXButton btn_reset;


    @FXML
    private void retour_main_menu(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("interface_main.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Julia Set");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
    }


}
