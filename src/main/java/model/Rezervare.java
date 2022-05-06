package model;

public class Rezervare extends Entity<Integer> {
    private int idSpectator;
    private int idLocRezervat;
    private String titluSpectacol;

    public Rezervare() {}

    public Rezervare(int idSpectator, int idLocRezervat, String titluSpectacol) {
        this.idSpectator = idSpectator;
        this.idLocRezervat = idLocRezervat;
        this.titluSpectacol = titluSpectacol;
    }

    public int getIdSpectator() {
        return idSpectator;
    }

    public void setIdSpectator(int idSpectator) {
        this.idSpectator = idSpectator;
    }

    public int getIdLocRezervat() {
        return idLocRezervat;
    }

    public void setIdLocRezervat(int idLocRezervat) {
        this.idLocRezervat = idLocRezervat;
    }

    public String getTitluSpectacol() {
        return titluSpectacol;
    }

    public void setTitluSpectacol(String titluSpectacol) {
        this.titluSpectacol = titluSpectacol;
    }
}
