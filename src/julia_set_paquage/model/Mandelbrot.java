package julia_set_paquage.model;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Object Mandelbrot fractal
 */
public class Mandelbrot extends Fractal {


    /**
     * Constructeur
     *
     * @param maxIteration valeur de la constante en nombre complexe
     * @param zoom         valeur du zoom
     * @param moveX        valeur du deplacement dans l'axe des X
     * @param moveY        valeur du deplacement dans l'axe des Y
     */
    public Mandelbrot(int maxIteration, double zoom, double moveX, double moveY) {
        super(maxIteration, zoom, moveX, moveY);
    }


    /**
     * methode pour le calcule de mandelbrot
     *
     * @param largeurMax valeur de la largeur de l'image
     * @param longeurMax valeur de la longeur de l'image
     * @return bufferimage de l'ensemble mandelbrot
     */
    public BufferedImage drawMandelbrot(int largeurMax, int longeurMax) {
        BufferedImage image = new BufferedImage(largeurMax, longeurMax, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < largeurMax; x++) {
            for (int y = 0; y < longeurMax; y++) {

                //init data
                Complexe constante = new Complexe(redimentionX(x, largeurMax), redimentionY(y, longeurMax));
                Complexe z = new Complexe(0, 0);
                //get divergence
                int n = divergence(z, constante);
                //calcule color pixel
                int color = Color.HSBtoRGB((getMaxIteration() / (float) (n) % 1), 1, n > 0 ? 1 : 0);
                //set pixel
                image.setRGB(x, y, color);
            }
        }
        return image;
    }


}
