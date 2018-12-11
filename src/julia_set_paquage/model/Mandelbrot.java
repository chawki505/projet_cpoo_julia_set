package julia_set_paquage.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Mandelbrot extends Fractal {


    public Mandelbrot(int maxIteration, double zoom, double moveX, double moveY) {
        super(maxIteration, zoom, moveX, moveY);
    }


    public BufferedImage drawMandelbrot(int largeurMax, int longeurMax) {

        BufferedImage image = new BufferedImage(largeurMax, longeurMax, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < largeurMax; x++) {
            for (int y = 0; y < longeurMax; y++) {

                Complexe c = new Complexe(redimentionX(x, largeurMax), redimentionY(y, longeurMax));
                Complexe z = new Complexe(0, 0);

                int n = divergence(z, c);
                int color = Color.HSBtoRGB((getMaxIteration() / (float) (n) % 1), 1, n > 0 ? 1 : 0);
                image.setRGB(x, y, color);
            }
        }
        return image;
    }


}
