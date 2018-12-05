package julia_set_paquage.model;

public class Complexe {

    private final double reel;
    private final double imm;


    //default constructor with init parametre
    public Complexe() {
        this.reel = -0.5;
        this.imm = 0.6;
    }

    public Complexe(double reel, double imm) {
        this.reel = reel;
        this.imm = imm;
    }


    public double getReel() {
        return reel;
    }

    public double getImm() {
        return imm;
    }


    // add two comlexe number
    public Complexe plus(Complexe b) {
        Complexe a = this;       // invoking object

        double real = a.getReel() + b.getReel();
        double imag = a.getImm() + b.getImm();

        return new Complexe(real, imag);
    }

    //version static
    public static Complexe plus(Complexe a, Complexe b) {

        double real = a.getReel() + b.getReel();
        double imag = a.getImm() + b.getImm();

        return new Complexe(real, imag);

    }

    // multiple two comlexe number
    public Complexe times(Complexe b) {
        Complexe a = this;       // invoking object

        double real = (a.getReel() * b.getReel()) - (a.getImm() * b.getImm());
        double imag = (a.getReel() * b.getImm()) + (a.getImm() * b.getImm());

        return new Complexe(real, imag);
    }

    //version static
    public static Complexe times(Complexe a, Complexe b) {

        double real = (a.getReel() * b.getReel()) - (a.getImm() * b.getImm());
        double imag = (a.getReel() * b.getImm()) + (a.getImm() * b.getImm());

        return new Complexe(real, imag);
    }

    // calcule modulus to the comlexe number
    public double modulus() {
        Complexe a = this;       // invoking object
        return Math.sqrt((a.getReel() * a.getReel()) + (a.getImm() * a.getImm()));
    }


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
