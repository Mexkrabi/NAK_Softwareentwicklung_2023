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
    // HashMap<Integer, Integer> wiraufwir
    public int rundenzahl; //Rundenzahl wird hier gespeichert
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
    public void rundeBerechnen (){
        // 1.Wirtschaftsleistung (Rückkopplung)
        Main.wirtschaftsleistung.getWert();
        
        
        // 2. Versorgungslage
        // 3. Modernisierungsgrad (Rückkopplung)
        // 4. Bildung (Rückkopplung)
        // 5. Umweltverschmutzung
        // 6. Umweltverschmutzung (Rückkopplung)
        // 7. Lebensqualität
        // 8. Lebensqualität (Rückkopplung)
        // 9. Bevölkerungswachstum
        // 10. Bevölkerungswachstumsfakto�
    }
}

