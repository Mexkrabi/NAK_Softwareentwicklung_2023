import java.io.*;

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
        String s = "Bevölkerungsgröße = ";
        System.out.println(s.length());
        
        d.auslesen(pfadStartwerte, s);
        //////////////////////////////////////////////////////////////


    }

}
