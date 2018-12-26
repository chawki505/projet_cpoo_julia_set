package julia_set_paquage.model;


import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Object Julia fractal
 */
public class Julia extends Fractal {

    private final Complexe constante;

    /**
     * Constructeur
     *
     * @param constante    valeur de la constante en nombre complexe
     * @param maxIteration valeur maximum d'iteration dans la divergence
     * @param zoom         valeur du zoom
     * @param moveX        valeur du deplacement dans l'axe des X
     * @param moveY        valeur du deplacement dans l'axe des Y
     */
    public Julia(Complexe constante, int maxIteration, double zoom, double moveX, double moveY) {
        super(maxIteration, zoom, moveX, moveY);
        this.constante = constante;
    }

    /**
     * @return valeur constante nombre complexe
     */
    public Complexe getConstante() {
        return constante;
    }

    /**
     * methode pour le calcule de julia
     *
     * @param largeurMax valeur de la largeur de l'image
     * @param longeurMax valeur de la longeur de l'image
     * @return imagebuffer de l'ensemble julia de la constante
     */
    public BufferedImage drawJulia(int largeurMax, int longeurMax) {

        BufferedImage image = new BufferedImage(largeurMax, longeurMax, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < largeurMax; x++) {
            for (int y = 0; y < longeurMax; y++) {

                //init data
                Complexe z = new Complexe(redimentionX(x, largeurMax), redimentionY(y, longeurMax));
                //get divergence
                int n = divergence(z, this.constante);
                //calcule color pixel
                int color = Color.HSBtoRGB((getMaxIteration() / (float) (n) % 1), 1, n > 0 ? 1 : 0);
                //set pixel color
                image.setRGB(x, y, color);
            }
        }
        return image;
    }


}
