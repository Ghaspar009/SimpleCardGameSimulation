package Karty;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Rozgrywka {

    public static void main(String[] args) {
        int liczbaGraczy = 0;
        int liczbaTur = 0;
        String imie;
        Scanner scanner = new Scanner(System.in);
        Gra gra = new Gra();
        try { // nie chcemy by ktos podal zla liczbe graczy lub ulamek
            liczbaGraczy = gra.pobierzLiczbeGraczy();

            System.out.println("Podaj imona graczy");
            for (int i = 0; i < liczbaGraczy; i++) {
                imie = scanner.nextLine();
                gra.dodajGracza(imie);
            }

            scanner.close();

            gra.rozdajKarty();

            while (gra.getGracze().size() > 1) { //jak zostanie 1 gracz to rozgrywka sie konczy i mamy zwyciezce
                liczbaTur++;
                System.out.println("---------- Tura " + liczbaTur + " ----------------");
                gra.zagranie();
                System.out.println("Pozostalo " + gra.getGracze().size() + " graczy.");
            }

            System.out.println("------ Wygrywa " + gra.getGracze().get(0).getImie() + " po " + liczbaTur + " turach! ------");
        }
        catch (IllegalArgumentException e) { // lapiemy wyjatek i konczymy dzialanie programu wraz z komunikatem
            System.out.println("Podana liczba nie spe�nia wymaga�. Rozgrywka nie mo�e si� odby�!");
        }

        catch (InputMismatchException e) {
            System.out.println("Gracze nie mog� by� cz�ciowi! Rozgrywka nie mo�e si� odby�!");
        }
        finally {
            System.out.println("Mi�ego dnia!");
        }
    }
}
