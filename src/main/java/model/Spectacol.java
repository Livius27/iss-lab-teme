package model;

import java.util.ArrayList;
import java.util.List;

public class Spectacol extends Entity<Integer> {
    private String titlu;
    private String data;
    private int nrLocuriDisponibile;
    private List<Loc> locuri;

    public Spectacol() {}

    public Spectacol(String titlu, String data, int nrLocuriDisponibile) {
        this.titlu = titlu;
        this.data = data;
        this.nrLocuriDisponibile = nrLocuriDisponibile;
        this.locuri = new ArrayList<Loc>();
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getNrLocuriDisponibile() {
        return nrLocuriDisponibile;
    }

    public void setNrLocuriDisponibile(int nrLocuriDisponibile) {
        this.nrLocuriDisponibile = nrLocuriDisponibile;
    }

    public List<Loc> getLocuri() {
        return locuri;
    }

    public void setLocuri(List<Loc> locuri) {
        this.locuri = locuri;
    }
}
