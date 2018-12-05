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
        this.zoom = 1.5;
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

    protected double redimentionX(int initialisation, float largeurFenetre) {
        return 1.5 * (initialisation - largeurFenetre / 2) / (0.5 * zoom * largeurFenetre) + moveX;
    }

    protected double redimentionY(int initialisation, float longeurFenetre) {
        return (initialisation - longeurFenetre / 2) / (0.5 * zoom * longeurFenetre) + moveY;
    }

    protected float divergence(Complexe z) {
        float i = this.maxIteration;
        while (z.modulus() < 2 && i > 0) {
            //calcul new z
            double cx = z.getReel() * z.getReel() - z.getImm() * z.getImm() + this.complexe.getReel();
            double cy = 2.0 * z.getReel() * z.getImm() + this.complexe.getImm();

            z = new Complexe(cx, cy);

            i--;
        }

        return i;
    }


    public BufferedImage drawJulia(int largeurMax, int longeurMax) {

        BufferedImage image = new BufferedImage(largeurMax, longeurMax, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < largeurMax; x++) {

            for (int y = 0; y < longeurMax; y++) {

                Complexe z = new Complexe(redimentionX(x, largeurMax), redimentionY(y, longeurMax));

                float i = divergence(z);

                int color = Color.HSBtoRGB((this.maxIteration / (i) % 1), 1, i > 0 ? 1 : 0);

                image.setRGB(x, y, color);
            }
        }
        return image;
    }


    public BufferedImage colorisation(BufferedImage image, Color maCouleur, boolean degrade, int tolerance) {


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
        return image;
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


}
