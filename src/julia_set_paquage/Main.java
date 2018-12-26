package julia_set_paquage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import julia_set_paquage.model.Complexe;
import julia_set_paquage.model.Fractal;
import julia_set_paquage.model.Julia;
import julia_set_paquage.model.Mandelbrot;

import java.util.Scanner;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/interface_main.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Julia Set");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }


    public static void interaction_console() {

        float real, img;
        int choix;
        boolean status = true;
        Scanner sc = new Scanner(System.in);

        while (status) {
            System.out.println("\nVeuillez saisir un mode :");
            System.out.println("1- Julia Set");
            System.out.println("2- Mandelbrot Set");
            System.out.println("3- Retour");
            System.out.print("Votre choix ? :");
            choix = sc.nextInt();


            switch (choix) {
                case 1:

                    System.out.println("\nVeuillez saisir le nombre complexe de la fomre (reel + i imaginaire) :");
                    System.out.print("Real :");
                    real = sc.nextFloat();
                    System.out.print("Imaginaire");
                    img = sc.nextFloat();
                    Complexe complexe = new Complexe(real, img);
                    Julia julia = new Julia(complexe, 100, 1, 0, 0);
                    Fractal.saveToFile(julia.drawJulia(1000, 1000), "fractal_julia", System.getenv("PWD"));
                    System.out.println("image enregister dans " + System.getenv("PWD"));
                    break;

                case 2:
                    Mandelbrot mandelbrot = new Mandelbrot(100, 1, 0, 0);
                    Fractal.saveToFile(mandelbrot.drawMandelbrot(1000, 1000), "fractal_mandelbrot", System.getenv("PWD"));
                    System.out.println("image enregister dans " + System.getenv("PWD"));
                    break;


                case 3:
                    status = false;
                    break;
            }

        }
    }


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean staus = true;


        while (staus) {

            System.out.println("\nChoisire un mode :");
            System.out.println("1- Console");
            System.out.println("2- Interface graphique");
            System.out.println("3- Quiter");
            System.out.print("mode : ");

            int choix = sc.nextInt();

            switch (choix) {
                case 1:
                    System.out.println("\nmode console (saisir q pour quitter");
                    interaction_console();
                    break;

                case 2:
                    launch(args);
                    break;


                case 3:
                    staus = false;
                    Runtime.getRuntime().exit(0);
                    break;
            }
        }
    }
}


/*TODO: ajouter mode terminal */
/*TODO: ajouter les threads */
/*TODO: ajouter la doc */