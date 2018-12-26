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

    public static void main(String[] args) {
        //thread de l'interface graphique
        Thread thread_IG = new Thread(Main::interaction_console);
        //thread de l'interface console
        Thread thread_CONSOLE = new Thread(() -> launch(args));
        //lencement des threads
        thread_IG.start();
        thread_CONSOLE.start();
    }


    /**
     * MODE CONSOLE
     **/
    private static int lecture_int() {
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    private static float lecture_float() {
        Scanner sc = new Scanner(System.in);
        return sc.nextFloat();
    }

    private static String lecture_string() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    private static void interaction_console() {
        boolean status = true;
        Thread thread_calcule;

        while (status) {
            System.out.println("Veuillez saisir un mode :");
            System.out.println("1- Julia Set");
            System.out.println("2- Mandelbrot Set");
            System.out.println("3- Quiter");
            System.out.print("Votre choix ? :");

            String choix = lecture_string();

            try {
                switch (choix) {
                    case "1":
                        //get data
                        System.out.println(" --> Veuillez saisir le nombre complexe de la fomre (reel + i imaginaire) :");
                        System.out.print("   --> Real :");
                        float real = lecture_float();
                        System.out.print("   --> Imaginaire :");
                        float img = lecture_float();
                        System.out.print(" --> Max iteration : ");
                        int max_iterJ = lecture_int();
                        System.out.print(" --> Largeur de votre image : ");
                        int largeur_imageJ = lecture_int();
                        System.out.print(" --> Hauteur de votre image : ");
                        int hauteur_imageJ = lecture_int();

                        Julia julia = new Julia(new Complexe(real, img), max_iterJ, 1, 0, 0);

                        //lencement du calcule dans un autre thread
                        thread_calcule = new Thread(() -> Fractal.saveToFile(julia.drawJulia(largeur_imageJ, hauteur_imageJ),
                                "fractal_julia_image", System.getenv("PWD")));
                        thread_calcule.start();
                        System.out.println(" --> image enregister dans " + System.getenv("PWD"));
                        break;

                    case "2":
                        //get data
                        System.out.print(" --> Max iteration : ");
                        int max_iterM = lecture_int();
                        System.out.print(" --> Largeur de votre image : ");
                        int largeur_imageM = lecture_int();
                        System.out.print(" --> Hauteur de votre image : ");
                        int hauteur_imageM = lecture_int();

                        Mandelbrot mandelbrot = new Mandelbrot(max_iterM, 1, 0, 0);
                        //lencement du thread de calcule
                        thread_calcule = new Thread(() -> Fractal.saveToFile(mandelbrot.drawMandelbrot(largeur_imageM, hauteur_imageM),
                                "fractal_mandelbrot_image", System.getenv("PWD")));
                        thread_calcule.start();
                        System.out.println(" --> image enregister dans " + System.getenv("PWD"));
                        break;

                    case "3":
                        status = false;
                        //arreter le programme
                        Runtime.getRuntime().exit(0);
                        break;
                }
            } catch (Exception e) {
                continue;
            }
            System.out.println();
        }

    }


}


/*TODO: ajouter la doc */