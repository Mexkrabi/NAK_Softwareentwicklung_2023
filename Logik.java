import java.io.*;
import java.util.Scanner;

/**
 * Hier wird die Spiellogik, die Berechnung der Einflussgrößen und die 
 * Abbruchsbedingungen überprüft und kontrolliert.
 *
 * @author Sven Vazquez de Lara Kallas
 * @version (0.1)
 */
public class Logik
{
    
    /**
     * Konstruktor der Klasse Logik
     */
    public Logik(DateiLeser d)
    {
        
    }

    /**
     * Auslesen der Anfangswerte in einer Methode
     * mit z.B. String string = "Lebensqualität";
     * dann mit string.length() Zeichenlänge ermitteln
     * daraufhin Wert einlesen und einfügen
     * 
     * Eingabe durch Konsolenaufruf
     */
    public void auslesen(/*hier OHNE Variable - MIT Variable siehe UNTEN*/)
    {
        System.out.println("Suchwort zum auslesen eingeben: (z.B. Lebensqualität)");
        String suchwort = Main.konsoleneingabe() + " = ";     //Erklärung "konsoleneingabe()" siehe Main-Klasse (Vereinfachter Input)
        System.out.println("Länge von '" + suchwort + "' ist: " + suchwort.length()); //suchwort.lenght() gibt String-Länge
        //d.auslesen(Main.pfadStartwerte, suchwort);
    }
    /**
     * Auslesen der Anfangswerte in einer Methode
     * mit z.B. String string = "Lebensqualität";
     * dann mit string.length() Zeichenlänge ermitteln
     * daraufhin Wert einlesen und einfügen
     * 
     * Eingabe durch mitgegebenem String
     * 
     * @param suchwort Suchwort zum auslesen eingeben (z.B. Lebensqualität)
     */
    public void auslesen(String suchwort)
    {
        System.out.println("Suchwort, welches zum auslesen eingegeben wurde: " + suchwort);
        suchwort = suchwort + " = "; //Erweitert mit " = "
        System.out.println("Länge von '" + suchwort + "' ist: " + suchwort.length()); //suchwort.lenght() gibt String-Länge
        //d.auslesen(Main.pfadStartwerte, suchwort);
    }
}
