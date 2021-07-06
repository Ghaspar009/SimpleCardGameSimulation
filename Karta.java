package Karty;

public class Karta {

    private String nazwa;
    private int wartosc;

    Karta(String nazwa, int wartosc) {
        this.nazwa = nazwa;
        this.wartosc = wartosc;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getWartosc() {
        return wartosc;
    }
}
