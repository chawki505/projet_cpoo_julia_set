package julia_set_paquage.model;

public final class Complexe {

    private final double reel;
    private final double imm;


    //default constructor with init parametre
    public Complexe() {
        this.reel = 0.3;
        this.imm = 0.5;
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


    public Complexe plus(Complexe other) {
        return new Complexe(this.reel + other.reel, this.imm + other.imm);
    }

    public Complexe times(Complexe other) {
        double nr = (this.reel * other.reel) - (this.imm * other.imm);
        double ni = (this.reel * other.imm) + (this.imm * other.reel);
        return new Complexe(nr, ni);
    }

    public double modulus() {
        return Math.sqrt((this.reel * this.reel) + (this.imm * this.imm));
    }


    @Override
    public String toString() {
        return "Complexe{" +
                "reel=" + reel +
                ", imm=" + imm +
                '}';
    }
}
