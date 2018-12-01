package julia_set_paquage.controller;

import com.jfoenix.controls.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import julia_set_paquage.model.Julia;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_interface_main implements Initializable {


    @FXML
    private JFXTextField textField_real;
    @FXML
    private JFXTextField textField_img;
    @FXML
    private JFXButton btn_calculer;
    @FXML
    private JFXButton btn_quitter;
    @FXML
    private JFXButton btn_reset;
    @FXML
    private ImageView imageView_image;

    @FXML
    private JFXButton btn_save_png;

    @FXML
    private ToggleGroup choose_set;

    @FXML
    private JFXRadioButton radioButton_julia;

    @FXML
    private JFXRadioButton radioButton_mandelbrot;


    @FXML
    //methode action pour le button quiter
    private void quiter(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }


    @FXML
    //methode to go to interface julia set result
    private void calculer() throws IOException {

        set_image2();
    }


    @FXML
    //methode to go to interface julia set result
    private void reset() {

    }

    @FXML
    //methode to go to interface julia set result
    private void save_png() throws IOException {

    }


    //methode pour verifier la saisi correcte  else elle affiche une boite de dialogue de message d'erreur
    private boolean isInputValid() {

        String errorMessage = "";

        if (textField_real.getText() == null || textField_real.getText().length() == 0) {
            errorMessage += "- vous n'avez pas saisi le nombre reel!\n";
        } else {
            try {
                Float.parseFloat(textField_real.getText());
            } catch (NumberFormatException e) {
                errorMessage += "- votre saisie du nombre reel n'est pas un nombre correcte !\n";
            }
        }

        if (textField_img.getText() == null || textField_img.getText().length() == 0) {
            errorMessage += "- vous n'avez pas saisi le nombre imaginaire!\n";
        } else {
            try {
                Float.parseFloat(textField_img.getText());
            } catch (NumberFormatException e) {
                errorMessage += "- votre saisie du nombre imaginaire n'est pas un nombre correcte !\n";
            }
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champ de saisie non valide");
            alert.setHeaderText("Veuillez corriger les champs d'erreurs");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }


    private void set_image() {
        //get buffredimage
        BufferedImage bf = null;
        try {
            bf = ImageIO.read(new File("img/julia_pic.png"));
        } catch (IOException ex) {
            System.out.println("Image failed to load.");
        }


        //create image pixel par pixel
        WritableImage wr = setWritableImage(bf);

        //afficher dans imageview
        imageView_image.setImage(wr);

    }

    private void set_image2() {


        Julia julia = new Julia();
        //get buffredimage
        BufferedImage bf = julia.drawJulia((int) imageView_image.getFitWidth(), (int) imageView_image.getFitHeight());
        //bf = julia.colorisation(bf, new Color(3, 169, 244), true, 100);
        //  WritableImage wr = setWritableImage(bf);

        Image image = SwingFXUtils.toFXImage(bf, null);

        //afficher dans imageview
        imageView_image.setImage(image);

    }

    private WritableImage setWritableImage(BufferedImage bf) {
        //create image pixel par pixel
        WritableImage wr = null;
        if (bf != null) {
            wr = new WritableImage(bf.getWidth(), bf.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < bf.getWidth(); x++) {
                for (int y = 0; y < bf.getHeight(); y++) {
                    pw.setArgb(x, y, bf.getRGB(x, y));
                }
            }
        }
        return wr;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
