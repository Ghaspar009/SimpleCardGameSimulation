package Karty;

import java.util.LinkedList;
import java.util.Queue;

public class Gracz {

    private String imie;
    private Queue<Karta> reka = new LinkedList<Karta>();

    Gracz(String imie) {
        this.imie = imie;
    }

    public String getImie() {
        return imie;
    }

    public Queue<Karta> getReka() {
        return reka;
    }
}
