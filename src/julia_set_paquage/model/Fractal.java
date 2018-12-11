package julia_set_paquage.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Fractal {
    private final int maxIteration;
    private final double zoom;
    private final double moveX;
    private final double moveY;


    public Fractal(int maxIteration, double zoom, double moveX, double moveY) {
        this.maxIteration = maxIteration;
        this.zoom = zoom;
        this.moveX = moveX;
        this.moveY = moveY;
    }


    public int getMaxIteration() {
        return maxIteration;
    }

    public double getZoom() {
        return zoom;
    }

    public double getMoveX() {
        return moveX;
    }

    public double getMoveY() {
        return moveY;
    }


    public double redimentionX(int axe_x, float largeurFenetre) {
        return 1.5 * (axe_x - largeurFenetre / 2) / (0.5 * zoom * largeurFenetre) + moveX;
    }

    public double redimentionY(int axe_y, float longeurFenetre) {
        return (axe_y - longeurFenetre / 2) / (0.5 * zoom * longeurFenetre) + moveY;
    }


    public int divergence(Complexe z, Complexe constante) {
        int i = getMaxIteration();
        while (z.modulus() <= 2 && i > 0) {
            //calcul new z
            double cx = z.getReel() * z.getReel() - z.getImm() * z.getImm() + constante.getReel();
            double cy = 2.0 * z.getReel() * z.getImm() + constante.getImm();
            z = new Complexe(cx, cy);
            i--;
        }
        return i;
    }


    public static void colorisation(BufferedImage image, Color maCouleur, boolean degrade, int tolerance) {
        for (int i = 0; i < image.getWidth(); i++) {

            for (int j = 0; j < image.getHeight(); j++) {

                Color c = new Color(image.getRGB(i, j));

                if (c.getGreen() < tolerance) {

                    Color nc = new Color(0, 0, 0);

                    image.setRGB(i, j, nc.getRGB());
                } else {

                    if (degrade) {
                        int nr = (c.getRed() + maCouleur.getRed()) % 256;

                        int ng = (c.getGreen() + maCouleur.getGreen()) % 256;

                        int nb = (c.getBlue() + maCouleur.getBlue()) % 256;

                        Color nc = new Color(nr, ng, nb);

                        image.setRGB(i, j, nc.getRGB());

                    } else {

                        image.setRGB(i, j, maCouleur.getRGB());
                    }
                }
            }
        }
    }


    public static Color set_color(Color actuel, Color maCouleur, boolean degrade, int tolerance) {


        if (actuel.getGreen() < tolerance) {

            return new Color(0, 0, 0);
        } else {

            if (degrade) {
                int nr = (actuel.getRed() + maCouleur.getRed()) % 256;

                int ng = (actuel.getGreen() + maCouleur.getGreen()) % 256;

                int nb = (actuel.getBlue() + maCouleur.getBlue()) % 256;

                return new Color(nr, ng, nb);

            } else {

                return maCouleur;
            }
        }
    }

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
