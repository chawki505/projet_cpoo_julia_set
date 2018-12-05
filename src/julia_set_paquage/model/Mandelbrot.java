package julia_set_paquage.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Mandelbrot extends Julia {


    public Mandelbrot() {
        super();
    }


    public Mandelbrot(Complexe complexe) {
        super(complexe);
    }


    public Mandelbrot(Complexe complexe, int maxIteration, double zoom, double moveX, double moveY) {
        super(complexe, maxIteration, zoom, moveX, moveY);
    }


    public BufferedImage drawMandelbrot(int largeurMax, int longeurMax) {
        BufferedImage image = new BufferedImage(largeurMax, longeurMax, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < largeurMax; x++) {

            for (int y = 0; y < longeurMax; y++) {

                Complexe z = new Complexe(redimentionX(x, largeurMax), redimentionY(y, longeurMax));

                float i = divergence(getComplexe());

                int color = Color.HSBtoRGB((getMaxIteration() / i) % 1, 1, i > 0 ? 1 : 0);

                image.setRGB(x, y, color);
            }
        }
        return image;
    }


}
