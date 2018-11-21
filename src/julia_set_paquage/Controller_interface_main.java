package julia_set_paquage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_interface_main implements Initializable {


    @FXML
    private JFXTextField textField_real;
    @FXML
    private JFXTextField textField_img;
    @FXML
    private JFXComboBox<Label> comboBox_signe = new JFXComboBox<Label>();
    @FXML
    private JFXButton button_calculer;
    @FXML
    private JFXButton button_quitter;


    @FXML
    //methode action pour le button quiter
    private void quiter(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        comboBox_signe.getItems().add(new Label("+"));
        comboBox_signe.getItems().add(new Label("-"));
        comboBox_signe.setPromptText("Signe");

    }
}
