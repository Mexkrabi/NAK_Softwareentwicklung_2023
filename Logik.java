import java.io.*;
import java.util.*;

/**
 * Hier wird die Spiellogik, die Berechnung der Einflussgrößen und die 
 * Abbruchsbedingungen überprüft und kontrolliert.
 *
 * @author Sven Vazquez de Lara Kallas
 * @version (0.1)
 */
public class Logik
{
    public Sektor[] alleSektoren; //hier werden alle Sektoren im Array gespeichert
    public HashMap<String, Integer> startwerteHash; //HashMap mit allen Anfangswerten aus der .sim Datei
    /**
     * Konstruktor der Klasse Logik
     */
    public Logik()
    {
        hashErzeuger();
    }
    
    public void hashErzeuger()
    {
        startwerteHash = new HashMap<String, Integer>();
    }
    
}
