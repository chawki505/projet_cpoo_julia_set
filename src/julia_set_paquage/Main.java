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

import java.io.IOException;
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


    private static void interaction_console() {
        float real, img;
        String choix;
        boolean status = true;
        Scanner sc = new Scanner(System.in);

        while (status) {
            System.out.println("Veuillez saisir un mode :");
            System.out.println("1- Julia Set");
            System.out.println("2- Mandelbrot Set");
            System.out.println("3- Quiter");
            System.out.print("Votre choix ? :");
            choix = sc.nextLine();


            switch (choix) {
                case "1":

                    System.out.println(" --> Veuillez saisir le nombre complexe de la fomre (reel + i imaginaire) :");
                    System.out.print("   --> Real :");
                    try {
                        real = Float.valueOf(sc.nextLine());
                    } catch (NumberFormatException e) {
                        break;
                    }
                    System.out.print("   --> Imaginaire :");

                    try {
                        img = Float.valueOf(sc.nextLine());
                    } catch (NumberFormatException e) {
                        break;
                    }
                    Complexe complexe = new Complexe(real, img);
                    Julia julia = new Julia(complexe, 100, 1, 0, 0);
                    Fractal.saveToFile(julia.drawJulia(1000, 1000), "fractal_julia", System.getenv("PWD"));
                    System.out.println("   --> image enregister dans " + System.getenv("PWD"));
                    break;

                case "2":
                    Mandelbrot mandelbrot = new Mandelbrot(100, 1, 0, 0);
                    Fractal.saveToFile(mandelbrot.drawMandelbrot(1000, 1000), "fractal_mandelbrot", System.getenv("PWD"));
                    System.out.println("    --> image enregister dans " + System.getenv("PWD"));
                    break;


                case "3":
                    status = false;
                    Runtime.getRuntime().exit(0);
                    break;
            }

            System.out.println();
        }
    }


    public static void main(String[] args) {
        Thread thread_IG = new Thread(Main::interaction_console);
        Thread thread_CONSOLE = new Thread(() -> launch(args));
        thread_IG.start();
        thread_CONSOLE.start();
    }
}


/*TODO: ajouter mode terminal */
/*TODO: ajouter les threads */
/*TODO: ajouter la doc */