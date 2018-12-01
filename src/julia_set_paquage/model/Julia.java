package julia_set_paquage.model;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;

public class Julia {

    private final Complexe complexe;
    private final int maxIteration;
    private final double zoom;
    private final double moveX;
    private final double moveY;


    public Julia(Complexe complexe, int maxIteration, double zoom, double moveX, double moveY) {
        this.complexe = complexe;
        this.maxIteration = maxIteration;
        this.zoom = zoom;
        this.moveX = moveX;
        this.moveY = moveY;
    }

    public Julia(Complexe complexe) {
        this.complexe = complexe;
        this.maxIteration = 100;
        this.zoom = 1;
        this.moveX = 0;
        this.moveY = 0;
    }

    public Julia() {
        this.complexe = new Complexe();
        this.maxIteration = 100;
        this.zoom = 1;
        this.moveX = 0;
        this.moveY = 0;
    }


    public Complexe getComplexe() {
        return complexe;
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

    private double redimentionX(int initialisation, int largeurFenetre) {
        return ((1.5 *
                (initialisation - (largeurFenetre / 2)) /
                (0.5 * this.zoom * largeurFenetre))
                + this.moveX);
    }

    private double redimentionY(int initialisation, int longeurFenetre) {
        return (((initialisation - (longeurFenetre / 2))
                / (0.5 * this.zoom * longeurFenetre))
                + this.moveY);
    }


    public BufferedImage drawJulia(int largeurMax, int longeurMax) {
        BufferedImage image = new BufferedImage(largeurMax, longeurMax, BufferedImage.TYPE_INT_RGB);
        Complexe z;
        for (int x = 0; x < largeurMax; x++) {
            for (int y = 0; y < longeurMax; y++) {
                z = new Complexe(redimentionX(x, largeurMax), redimentionY(y, longeurMax));

                double i = divergenceIndex(z, this.complexe, this.maxIteration);

                int color = Color.HSBtoRGB((float) ((this.maxIteration / i) % 1), 1, (i > 0) ? 1 : 0);
                image.setRGB(x, y, color);
            }
        }
        return image;
    }

    public BufferedImage colorisation(BufferedImage image, Color maCouleur, boolean degrade, int tolerance) {
        int taille = image.getHeight() * image.getWidth();
        int couleur[] = new int[taille];
        int x = 0;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color c = new Color(image.getRGB(i, j));
                if (c.getGreen() < tolerance) {
                    Color nc = new Color(0, 0, 0);
                    image.setRGB(i, j, nc.getRGB());
                } else {
                    if (degrade) {
                        int nr = c.getRed() + maCouleur.getRed();
                        int ng = c.getGreen() + maCouleur.getGreen();
                        int nb = c.getBlue() + maCouleur.getBlue();
                        if (nr > 255) {
                            nr = 255;
                        }
                        if (ng > 255) {
                            ng = 255;
                        }
                        if (nb > 255) {
                            nb = 255;
                        }
                        Color nc = new Color(nr, ng, nb);
                        image.setRGB(i, j, nc.getRGB());
                    } else {
                        image.setRGB(i, j, maCouleur.getRGB());
                    }
                }
            }
        }
        return image;
    }

    public BufferedImage drawMandelbrot(int largeurMax, int longeurMax) {
        BufferedImage image = new BufferedImage(largeurMax, longeurMax, BufferedImage.TYPE_INT_RGB);
        Complexe z = new Complexe();
        for (int x = 0; x < largeurMax; x++) {
            for (int y = 0; y < longeurMax; y++) {
                z = new Complexe(redimentionX(x, largeurMax), redimentionY(y, longeurMax));
                double i = divergenceIndex(this.complexe, this.complexe, this.maxIteration);
                int c = Color.HSBtoRGB((float) ((this.maxIteration / i) % 1), 1, i > 0 ? 1 : 0);
                //int complexe = Color.HSBtoRGB(0, 1, i > 0 ? 1 : 0);
                image.setRGB(x, y, c);
            }
        }
        return image;
    }

    public static void saveToFile(BufferedImage image, String imageName, String path) {
        try {
            File file = new File(path, imageName);
            ImageIO.write(image, "PNG", file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int divergenceIndex(Complexe x0, Complexe c, int maxIteration) {

        int i = maxIteration;
        while (x0.modulus() < 4 && i > 0) {
            x0 = c.plus(x0.times(x0));
            i--;
        }
        return i;

    }
}
