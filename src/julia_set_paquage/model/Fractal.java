package julia_set_paquage.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Object fractal
 */
public class Fractal {
    private final int maxIteration;
    private final double zoom;
    private final double moveX;
    private final double moveY;


    /**
     * Constructeur
     *
     * @param maxIteration nombre d'iteration max
     * @param zoom         valeur du zoom
     * @param moveX        valeur du deplacement dans axe des X
     * @param moveY        valeur deplacement dans axe des Y
     */
    public Fractal(int maxIteration, double zoom, double moveX, double moveY) {
        this.maxIteration = maxIteration;
        this.zoom = zoom;
        this.moveX = moveX;
        this.moveY = moveY;
    }


    /**
     * @return max iteraction valeur
     */
    public int getMaxIteration() {
        return maxIteration;
    }

    /**
     * @return zoom valeur
     */
    public double getZoom() {
        return zoom;
    }

    /**
     * @return deplacement axe x valeur
     */
    public double getMoveX() {
        return moveX;
    }

    /**
     * @return deplacement axe y valeur
     */
    public double getMoveY() {
        return moveY;
    }


    /**
     * @param axe_x          valeur axe des x du pixel
     * @param largeurFenetre valeur de la largeur de l'image
     * @return nouvelle valeur axe des X du pixel
     */
    public double redimentionX(int axe_x, float largeurFenetre) {
        return 1.5 * (axe_x - largeurFenetre / 2) / (0.5 * zoom * largeurFenetre) + moveX;
    }

    /**
     * @param axe_y          valeur axe des Y du pixel
     * @param longeurFenetre valeur de la longeur de l'image
     * @return nouvelle valeur axe des Y du pixel
     */
    public double redimentionY(int axe_y, float longeurFenetre) {
        return (axe_y - longeurFenetre / 2) / (0.5 * zoom * longeurFenetre) + moveY;
    }


    /**
     * calculer la divergence
     *
     * @param z         nombre complexe courant
     * @param constante nombre complexe constante
     * @return la divergence
     */
    public int divergence(Complexe z, Complexe constante) {
        int i = getMaxIteration();
        while (z.modulus() <= 2 && i > 0) {

            //calcul new z
            //double cx = z.getReel() * z.getReel() - z.getImm() * z.getImm() + constante.getReel();
            //double cy = 2.0 * z.getReel() * z.getImm() + constante.getImm();
            //z = new Complexe(cx, cy);

            //formule new_z = (old_z * old_z) + constante
            z = constante.plus(z.times(z));

            i--;
        }
        return i;
    }


    /**
     * methode de colorisation d'une image
     *
     * @param image     image initial
     * @param maCouleur la couleur
     * @param tolerance la valeur de la tolerence
     */
    public static void colorisation(BufferedImage image, Color maCouleur, int tolerance) {
        //parcourir les pixels de l'image (x y)
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {

                //get color du pixel
                Color c = new Color(image.getRGB(i, j));

                //text tolerance
                if (c.getGreen() < tolerance) {
                    //set colot noir
                    Color nc = new Color(0, 0, 0);
                    image.setRGB(i, j, nc.getRGB());
                } else {

                    int nr = (c.getRed() + maCouleur.getRed()) % 256;
                    int ng = (c.getGreen() + maCouleur.getGreen()) % 256;
                    int nb = (c.getBlue() + maCouleur.getBlue()) % 256;

                    Color nc = new Color(nr, ng, nb);

                    image.setRGB(i, j, nc.getRGB());

                }
            }
        }
    }

    /**
     * methode pour sauvgarder l'image en png
     *
     * @param image     image buffer
     * @param imageName nom de l'image
     * @param path      chemin de sauvguarde
     */
    public static void saveToFile(BufferedImage image, String imageName, String path) {
        try {
            File file = new File(path, imageName + ".png");

            if (file.exists()) {
                int i = 0;
                while (file.exists()) {
                    i++;
                    file = new File(path, imageName + "(" + i + ")" + ".png");
                }
                ImageIO.write(image, "PNG", file);

            } else
                ImageIO.write(image, "PNG", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Fractal{" +
                "maxIteration=" + maxIteration +
                ", zoom=" + zoom +
                ", moveX=" + moveX +
                ", moveY=" + moveY +
                '}';
    }
}
