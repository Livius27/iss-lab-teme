package model;

public class Rezervare extends Entity<Long> {
    private long idSpectator;
    private long idLocRezervat;
    private String titluSpectacol;

    public Rezervare(long idSpectator, long idLocRezervat, String titluSpectacol) {
        this.idSpectator = idSpectator;
        this.idLocRezervat = idLocRezervat;
        this.titluSpectacol = titluSpectacol;
    }

    public long getIdSpectator() {
        return idSpectator;
    }

    public void setIdSpectator(long idSpectator) {
        this.idSpectator = idSpectator;
    }

    public long getIdLocRezervat() {
        return idLocRezervat;
    }

    public void setIdLocRezervat(long idLocRezervat) {
        this.idLocRezervat = idLocRezervat;
    }

    public String getTitluSpectacol() {
        return titluSpectacol;
    }

    public void setTitluSpectacol(String titluSpectacol) {
        this.titluSpectacol = titluSpectacol;
    }
}
