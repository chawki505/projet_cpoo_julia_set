package julia_set_paquage.controller;

import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import julia_set_paquage.model.Complexe;
import julia_set_paquage.model.Julia;
import julia_set_paquage.model.Mandelbrot;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
    private JFXColorPicker colorPicker_convergence;

    private BufferedImage my_fractal;

    @FXML
    private JFXSlider slider_zome;

    @FXML
    private JFXSlider slider_moveX;

    @FXML
    private JFXSlider slider_moveY;


    @FXML
    private JFXTextField textField_maxIteration;


    @FXML
    //methode action pour le button quiter
    private void quiter(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    //methode to go to interface julia set result
    private void calculer() throws IOException {

        if (isInputValid()) {

            Complexe complexe = new Complexe(Double.parseDouble(textField_real.getText()), Double.parseDouble(textField_img.getText()));


            if (radioButton_julia.isSelected()) {

                Julia julia = new Julia(complexe, Integer.parseInt(textField_maxIteration.getText()), slider_zome.getValue(), slider_moveX.getValue(), slider_moveY.getValue());

                //get buffredimage
                my_fractal = julia.drawJulia((int) imageView_image.getFitWidth(), (int) imageView_image.getFitHeight());

                //get color
                javafx.scene.paint.Color fx = colorPicker_convergence.getValue();

                //convert color
                Color color = new Color((float) fx.getRed(), (float) fx.getGreen(), (float) fx.getBlue(), (float) fx.getOpacity());

                //set colorisation
                my_fractal = julia.colorisation(my_fractal, color, true, 10);


            } else {


                Mandelbrot mandelbrot = new Mandelbrot(complexe);

                //get buffredimage
                my_fractal = mandelbrot.drawMandelbrot((int) imageView_image.getFitWidth(), (int) imageView_image.getFitHeight());

                // my_fractal = julia.colorisation(my_fractal, new Color(244, 29, 0), true, 100);
            }

            Image image = SwingFXUtils.toFXImage(my_fractal, null);

            //afficher dans imageview
            imageView_image.setImage(image);

        }

    }


    @FXML
    //methode to go to interface julia set result
    private void reset() {
        my_fractal = null;
        imageView_image.setImage(null);
        textField_img.setText(null);
        textField_real.setText(null);
        colorPicker_convergence.setValue(null);
        radioButton_julia.setSelected(true);
        radioButton_mandelbrot.setSelected(false);
        slider_zome.setValue(1);
        slider_moveX.setValue(0);
        slider_moveY.setValue(0);
        textField_maxIteration.setText("100");
    }

    @FXML
    //methode to go to interface julia set result
    private void save_png() throws IOException {

        if (my_fractal != null) {

            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(new Stage());

            if (selectedDirectory == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur path");
                alert.setHeaderText("Choose path");
                alert.setContentText("Veuillez selectioner un repertoire");
                alert.showAndWait();

            } else {
                Julia.saveToFile(my_fractal, "JuliaSet", selectedDirectory.getAbsolutePath());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Image set");
                alert.setHeaderText("Image creer");
                alert.setContentText("Votre image est sauvguarder dans \n" + selectedDirectory.getAbsolutePath());
                alert.showAndWait();
            }


        }

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

        if (textField_maxIteration.getText() == null || textField_maxIteration.getText().length() == 0) {
            errorMessage += "- vous n'avez pas saisi le nombre d'iteration max!\n";
        } else {
            try {
                Float.parseFloat(textField_maxIteration.getText());
            } catch (NumberFormatException e) {
                errorMessage += "- votre saisie du nombre d'iteration max n'est pas un nombre correcte !\n";
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        textField_real.setText("0.285");
        textField_img.setText("0.01");
        slider_zome.setValue(1);
        slider_moveX.setValue(0);
        slider_moveY.setValue(0);
        textField_maxIteration.setText("100");


    }
}
