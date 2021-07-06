package Karty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Gra {

    private List<Karta> talia = new ArrayList<Karta>();
    private List<Gracz> gracze = new ArrayList<Gracz>();
    private List<Gracz> graczePrzegrani = new ArrayList<Gracz>(); //lista na graczy ktorzy przegrali dana ture, a trwa wojna w ktorej nie uczestnicza
    private List<Karta> stol = new ArrayList<Karta>(); //karty ktore gracze wyrzucaja do gry
    private List<Karta> kartyDoZgarniecia = new ArrayList<Karta>(); //lista na karty ktore zgarnie wygrany w danej turze

    public List<Gracz> getGracze() {
        return gracze;
    }

    public void stworzTalie() {
        Karta karta;
        String[] kolor = {"Serce", "Pik", "Karo", "Trefl"};
        for(int i = 0; i < 4; i++) {
            for (int j = 2; j < 15; j++) {
                switch (j) {
                    case 11:
                        karta = new Karta( "Walet " + kolor[i], j);
                        talia.add(karta);
                        break;
                    case 12:
                        karta = new Karta( "Dama " + kolor[i], j);
                        talia.add(karta);
                        break;
                    case 13:
                        karta = new Karta( "Król " + kolor[i], j);
                        talia.add(karta);
                        break;
                    case 14:
                        karta = new Karta( "As " + kolor[i], j);
                        talia.add(karta);
                        break;
                    default:
                        karta = new Karta(j + " " + kolor[i], j);
                        talia.add(karta);
                        break;
                }
            }
        }
        karta = new Karta("Joker", 15);
        talia.add(karta);
    }

    public void dodajGracza(String imie) {
        Gracz gracz = new Gracz(imie);
        gracze.add(gracz);
    }

    public void rozdajKarty() {
        stworzTalie();
        Random random = new Random();
        while(talia.size() > 0) {
            int i = random.nextInt(talia.size());
            gracze.get(talia.size() % gracze.size()).getReka().add(talia.get(i));
            talia.remove(i);
        }
    }

    public int maxNaStole() {
        int maxWartosc = 0;
        for(Karta karta: stol) {
            if(karta.getWartosc() > maxWartosc)
                maxWartosc = karta.getWartosc();
        }
        return maxWartosc;
    }

    public void usunGraczy() {
        for (int i = 0; i < gracze.size();) { // gdy gracz juz nie ma kart przegrywa i zostaje usuniety
            if (gracze.get(i).getReka().size() == 0) {
                gracze.remove(i);
            }
            else i++;
        }
    }

    public void oproznijStol() {
        while(stol.size() != 0) {
            kartyDoZgarniecia.add(stol.get(0));
            stol.remove(0);
        }
    }

    public void zagranie() {
        int maxWartosc = 0;

        for(int i = 0; i < gracze.size(); i++){ // wypisujemy kto ile ma kart
            System.out.println(gracze.get(i).getImie() + " ma " + gracze.get(i).getReka().size() + " kart.");
        }


        for(int i = 0; i < gracze.size(); i++) { // wypisujemy jakie karty rzucaja gracze i dodajemy je na stol
            stol.add(gracze.get(i).getReka().peek());
            System.out.println(gracze.get(i).getImie() + " gra " + gracze.get(i).getReka().remove().getNazwa());
        }

        maxWartosc = maxNaStole(); // wyliczamy najwieksza karte

        for(int i = 0; i < stol.size();) {
            if(stol.get(i).getWartosc() != maxWartosc) { // jesli karta gracza nie jest maxem to przegral dana ture
                graczePrzegrani.add(gracze.get(i));
                gracze.remove(i);
                kartyDoZgarniecia.add(stol.get(i)); //karty mniejsze niz max trafiaja do puli
                stol.remove(i);
            }
            else i++;
        }

        oproznijStol(); //karty juz moga trafic do puli

        System.out.println((kartyDoZgarniecia.size()) + " kart do zgarniecia.");

        if(gracze.size() > 1) { //jesli jest wiecej graczy niz jeden moze dojsc do wojny
            usunGraczy(); // ale jesli jeden z graczy nie ma juz kart to nie moze w niej uczestniczyc i przegral
            if(gracze.size() > 1) {
                for(Gracz gracz : gracze)
                    kartyDoZgarniecia.add(gracz.getReka().remove()); //gracze rzucaja odwrocone karty na stol
                usunGraczy(); //jesli jeden z graczy nie ma juz kart to nie moze w niej uczestniczyc i przegral
                if(gracze.size() > 1) { //jesli mamy dalej wiecej niz 1 gracza w turze, mamy wojne
                    System.out.println("Wojna!");
                    zagranie();
                }
            }
        }

        if(gracze.size() == 1) { //jesli tylko 1 gracz ma maxa mozemy przejsc do nastepnej tury, wygrany zgarnia karty
            while(kartyDoZgarniecia.size() > 0) {
                gracze.get(0).getReka().add(kartyDoZgarniecia.get(0));
                kartyDoZgarniecia.remove(0);
            }
        }

        while(graczePrzegrani.size() > 0) { //przegrani w danej turze wracaja do puli graczy
            gracze.add(graczePrzegrani.get(0));
            graczePrzegrani.remove(0);
        }

        usunGraczy(); //usuwamy nie dobitkow
    }

    public int pobierzLiczbeGraczy() {
        int liczbaGraczy;
        System.out.println("Podaj liczbe graczy" +
                " (Nie mniejsza niz 2 i nie wieksza niz 53)"); // dla 1 gracza rozgrywka nie ma sensu
        Scanner scanner = new Scanner(System.in); // dla wiekszej ilosci niz 53 nie bedzie kart
        liczbaGraczy = scanner.nextInt();
        if (liczbaGraczy <= 1 || liczbaGraczy >= 54)
            throw new IllegalArgumentException();
        return liczbaGraczy;
    }
}
