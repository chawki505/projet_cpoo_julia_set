package julia_set_paquage.model;

/**
 * Objet nombre Complexe
 */
public class Complexe {

    private final double reel;
    private final double imm;


    /**
     * Constructeur
     *
     * @param reel partie reel du noombre comlexe
     * @param imm  partie imaginaire du noombre comlexe
     */
    public Complexe(double reel, double imm) {
        this.reel = reel;
        this.imm = imm;
    }

    /**
     * Constructeur
     *
     * @param complexe nombre complexe
     */
    public Complexe(Complexe complexe) {
        this.reel = complexe.getReel();
        this.imm = complexe.getImm();
    }


    /**
     * @return reel
     */
    public double getReel() {
        return reel;
    }

    /**
     * @return imaginaire
     */
    public double getImm() {
        return imm;
    }


    /**
     * add two comlexe number
     *
     * @param b nombre complexe
     * @return adition de 2 nombres complexes
     */
    public Complexe plus(Complexe b) {
        Complexe a = this;       // invoking object

        double real = a.getReel() + b.getReel();
        double imag = a.getImm() + b.getImm();

        return new Complexe(real, imag);
    }

    /**
     * @param a nombre complexe
     * @param b nombre complexe
     * @return addition de 2 nombres complexe
     */
    public static Complexe plus(Complexe a, Complexe b) {

        double real = a.getReel() + b.getReel();
        double imag = a.getImm() + b.getImm();

        return new Complexe(real, imag);

    }

    /**
     * multiple two comlexe number
     *
     * @param b nombre complexe
     * @return multiplication de 2 nombres comlexes
     */
    public Complexe times(Complexe b) {
        Complexe a = this;       // invoking object

        double real = a.getReel() * b.getReel() - a.getImm() * b.getImm();
        double imag = a.getReel() * b.getImm() + a.getImm() * b.getReel();

        return new Complexe(real, imag);
    }

    /**
     * @param a nombre complexe
     * @param b nombre complexe
     * @return multiplication de 2 nombres comlexes
     */
    public static Complexe times(Complexe a, Complexe b) {

        double real = a.getReel() * b.getReel() - a.getImm() * b.getImm();

        double imag = a.getReel() * b.getImm() + a.getImm() * b.getReel();

        return new Complexe(real, imag);
    }

    /**
     * calcule modulus to the comlexe number
     *
     * @return module du nombre complexe
     */
    public double modulus() {
        Complexe a = this;       // invoking object
        return Math.sqrt((a.getReel() * a.getReel()) + (a.getImm() * a.getImm()));
    }


    /**
     * @param a nombre complexe
     * @return module du nombre complexe
     */
    // calcule modulus to the comlexe number
    public static double modulus(Complexe a) {
        return Math.sqrt((a.getReel() * a.getReel()) + (a.getImm() * a.getImm()));
    }


    @Override
    public String toString() {
        return "Complexe{" +
                "reel=" + reel +
                ", imm=" + imm +
                '}';
    }
}
