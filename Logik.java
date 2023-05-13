import java.io.*;
import java.util.Scanner;

/**
 * Hier wird die Spiellogik, die Berechnung der Einflussgrößen und die 
 * Abbruchsbedingungen überprüft und kontrolliert.
 *
 * @author (your name)
 * @version (0.1)
 */
public class Logik
{
    public String pfadStartwerte; //Speichert Dateipfad der .sim
    public DateiLeser d; //Dateileser - benötigt zum auslesen der Anfangswerte
    
    /**
     * Konstruktor der Klasse Logik
     */
    public Logik(DateiLeser d)
    {
        this.d = d; //Dateileser abspeichern in dieser Klasse
        pfadStartwerte = d.simDateiAuswahl(); //Liest und speichert den Dateipfad der .sim Datei
        auslesen(); //TEST
    }

    /**
     * Auslesen der Anfangswerte
     * in einer Methode
     * mit z.B. String string = "Lebensqualität";
     * dann mit string.length() Zeichenlänge ermitteln
     * daraufhin Wert einlesen und einfügen
     */
    public void auslesen(/*evtl. Variable*/)
    {
        System.out.println("Suchwort zum auslesen eingeben: (z.B. Lebensqualität");

        String suchwort = Main.konsoleneingabe() + " = ";     //Erklärung "konsoleneingabe()" siehe Main-Klasse (Vereinfachter Input)
        
        System.out.println("Länge von '" + suchwort + "' ist: " + suchwort.length());
        
        d.auslesen(pfadStartwerte, suchwort);
        //////////////////////////////////////////////////////////////


    }
}
