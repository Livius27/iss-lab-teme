package model;

public class Loc extends Entity<Integer> {
    private long numar;
    private int rand;
    private int loja;
    private float pret;
    private StareLoc stareLoc;

    public Loc() {}

    public Loc(long numar, int rand, int loja, float pret, StareLoc stareLoc) {
        this.numar = numar;
        this.rand = rand;
        this.loja = loja;
        this.pret = pret;
        this.stareLoc = stareLoc;
    }

    public long getNumar() {
        return numar;
    }

    public void setNumar(long numar) {
        this.numar = numar;
    }

    public int getRand() {
        return rand;
    }

    public void setRand(int rand) {
        this.rand = rand;
    }

    public int getLoja() {
        return loja;
    }

    public void setLoja(int loja) {
        this.loja = loja;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public StareLoc getStareLoc() {
        return stareLoc;
    }

    public void setStareLoc(StareLoc stareLoc) {
        this.stareLoc = stareLoc;
    }
}
