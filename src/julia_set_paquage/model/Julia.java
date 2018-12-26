package julia_set_paquage.model;


import java.awt.*;
import java.awt.image.BufferedImage;

public class Julia extends Fractal {

    private final Complexe constante;

    public Julia(Complexe constante, int maxIteration, double zoom, double moveX, double moveY) {
        super(maxIteration, zoom, moveX, moveY);
        this.constante = constante;
    }

    public Complexe getConstante() {
        return constante;
    }

    //methode pour le calcule de julia
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
