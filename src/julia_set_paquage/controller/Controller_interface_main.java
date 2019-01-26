package julia_set_paquage.controller;

import com.jfoenix.controls.*;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import julia_set_paquage.model.Complexe;
import julia_set_paquage.model.Fractal;
import julia_set_paquage.model.Julia;
import julia_set_paquage.model.Mandelbrot;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller de l'interface graphique
 */
public class Controller_interface_main implements Initializable {


    @FXML
    private JFXTextField textField_real;
    @FXML
    private JFXTextField textField_img;
    @FXML
    private ImageView imageView_image;
    @FXML
    private JFXRadioButton radioButton_julia;
    @FXML
    private JFXRadioButton radioButton_mandelbrot;
    @FXML
    private JFXColorPicker colorPicker_convergence;
    @FXML
    private JFXSlider slider_zoome;
    @FXML
    private JFXSlider slider_moveX;
    @FXML
    private JFXSlider slider_moveY;
    @FXML
    private JFXCheckBox checkBox_color;
    @FXML
    private JFXTextField textField_maxIteration;
    @FXML
    private JFXButton btn_calculer;
    @FXML
    private JFXButton btn_reset;
    @FXML
    private JFXButton btn_save_png;


    private BufferedImage my_fractal;

    /**
     * action pour fermer l'interface et quitter le programme
     *
     * @param event action event de l'interface
     */
    @FXML
    //methode pour le button quitter
    private void quiter(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Runtime.getRuntime().exit(0);
    }

    /**
     * action pour lancer un calcule de la fractal
     *
     * @param event action event de l'interface
     */
    @FXML
    //methode pour lencer le calcule de la fractal julia ou mandelbrot
    private void calculer(ActionEvent event) {

        //test si data correcte
        if (isInputValid()) {
            //change cursor type
            ((Node) (event.getSource())).getScene().setCursor(Cursor.WAIT);
            //desactiver les boutons principal
            btn_calculer.setDisable(true);
            btn_reset.setDisable(true);
            btn_save_png.setDisable(true);


            //lancer un un service de tache de fond pour le calcule
            Service<Void> calculate = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {

                            if (radioButton_julia.isSelected()) {
                                Complexe complexe = new Complexe(Double.parseDouble(textField_real.getText()), Double.parseDouble(textField_img.getText()));
                                Julia julia = new Julia(complexe, Integer.parseInt(textField_maxIteration.getText()), slider_zoome.getValue(), slider_moveX.getValue(), slider_moveY.getValue());
                                //get buffredimage
                                my_fractal = julia.drawJulia((int) imageView_image.getFitWidth(), (int) imageView_image.getFitHeight());
                            }

                            if (radioButton_mandelbrot.isSelected()) {
                                Mandelbrot mandelbrot = new Mandelbrot(Integer.parseInt(textField_maxIteration.getText()), slider_zoome.getValue(), slider_moveX.getValue(), slider_moveY.getValue());
                                //get buffredimage
                                my_fractal = mandelbrot.drawMandelbrot((int) imageView_image.getFitWidth(), (int) imageView_image.getFitHeight());
                            }

                            //test si colorisation activer
                            if (checkBox_color.isSelected()) {
                                //get color
                                javafx.scene.paint.Color fx = colorPicker_convergence.getValue();

                                //convert color
                                Color color = new Color((float) fx.getRed(), (float) fx.getGreen(), (float) fx.getBlue(), (float) fx.getOpacity());

                                //set colorisation
                                Fractal.colorisation(my_fractal, color, 1);
                            }

                            //converting bufferimage to imageView
                            Image image = SwingFXUtils.toFXImage(my_fractal, null);

                            //afficher dans imageview
                            imageView_image.setImage(image);

                            return null;
                        }
                    };
                }
            };

            calculate.stateProperty().addListener((ObservableValue<? extends Worker.State> observableValue, Worker.State oldValue, Worker.State newValue) -> {
                switch (newValue) {
                    case FAILED:
                    case CANCELLED:
                    case SUCCEEDED:
                        ((Node) (event.getSource())).getScene().setCursor(Cursor.DEFAULT);
                        btn_calculer.setDisable(false);
                        btn_reset.setDisable(false);
                        btn_save_png.setDisable(false);
                        break;
                }
            });


            calculate.start();
        }
    }


    /**
     * action pour le choix du mode mandelbrot
     */
    @FXML
    private void action_chooseMandelbrot() {
        textField_img.setDisable(true);
        textField_real.setDisable(true);
    }

    /**
     * action pour le choix du mode julia
     */
    @FXML
    private void action_chooseJulia() {
        textField_img.setDisable(false);
        textField_real.setDisable(false);
    }

    /**
     * action pour l'activation ou la desactivation de la couleur personaliser
     */
    @FXML
    private void action_set_color() {
        if (checkBox_color.isSelected()) {
            colorPicker_convergence.setDisable(false);
        } else {
            colorPicker_convergence.setDisable(true);
        }
    }

    /**
     * action pour rénitialiser les données dans l'interface
     */
    @FXML
    private void reset() {
        my_fractal = null;
        imageView_image.setImage(null);
        textField_img.setText(null);
        textField_real.setText(null);
        colorPicker_convergence.setValue(javafx.scene.paint.Color.valueOf("0x2196f3"));
        radioButton_julia.setSelected(true);
        radioButton_mandelbrot.setSelected(false);
        slider_zoome.setValue(1);
        slider_moveX.setValue(0);
        slider_moveY.setValue(0);
        textField_maxIteration.setText("50");
        btn_save_png.setDisable(true);
        action_chooseJulia();
    }

    /**
     * action pour enregistrer l'image en png
     */
    @FXML
    private void save_png() {

        if (my_fractal != null) {

            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(new Stage());

            if (selectedDirectory == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Sauvegarde fractal");
                alert.setHeaderText("Erreur d'accès");
                alert.setContentText("Le chemin d'accès spécifié est introuvable");
                alert.showAndWait();
            } else {
                if (radioButton_julia.isSelected())
                    Fractal.saveToFile(my_fractal, "JuliaSet", selectedDirectory.getAbsolutePath());
                else
                    Fractal.saveToFile(my_fractal, "MandelbrotSet", selectedDirectory.getAbsolutePath());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sauvegarde fractal");
                alert.setHeaderText("Image enregistrer");
                alert.setContentText("Votre image fractal est enregistrer dans \n" + selectedDirectory.getAbsolutePath());
                alert.showAndWait();
            }
        }
    }


    /**
     * methode pour verifier la saisi correcte  else elle affiche une boite de dialogue de message d'erreur
     *
     * @return false si données saisi est fausse sinon true
     */
    //
    private boolean isInputValid() {

        String errorMessage = "";

        if (radioButton_julia.isSelected()) {

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

    /**
     * methode qui s'execute automatiquement lors du chargement de l'interface fxml
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textField_real.setText("0.285");
        textField_img.setText("0.01");
        slider_zoome.setValue(1);
        slider_moveX.setValue(0);
        slider_moveY.setValue(0);
        textField_maxIteration.setText("50");
        colorPicker_convergence.setValue(javafx.scene.paint.Color.valueOf("0x2F65A5"));
        btn_save_png.setDisable(true);
        action_set_color();
    }
}
