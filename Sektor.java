import java.util.HashMap;

/**
 * Diese Klasse stellt die Grundstruktur für die einzelnen 
 * Kenngrößen (hier Sektoren genannt) dar.
 * Ein Sektor ist z.B. "Bevölkerungswachstum", "Lebensqualität", "Staatsvermögen"
 *
 * @author Livia Kadenbach
 * @version 3.0
 */
public class Sektor
{
    private int wert; //Aktueller Wert 
    private final String name; //Bezeichnung des Sektors
    private final int min, max; //Minimum und Maximum definieren Wertebereich des Sektors 
    
    public static final int standardStartwert = 1; // <<< Standard-Startwert HIER VERÄNDERN, falls kein Anderer Wert vorgegeben.
    
    public HashMap<Integer, Integer> werte; //Speichert nach und nach die Werte nach jeder Runde ein
    
    /**
     * Konstruktor der Klasse Sektor
     * Prüft bei der Kostruktion, ob der Startwert im Wertebereich liegt. Falls er außerhalb des Werteberichs liegt wird die Konstruktion 
     * abgebrochen und eine IllegalArgumentException geworfen. 
     * 
     * @param name Name des Sektors
     * @param min Kleinster Wert des Sektors
     * @param max Größter Wert des Sektors
     * @param startwert Definiere den Startwert des Sektors 
    */
    public Sektor(String name, int min, int max, int startwert) throws IllegalArgumentException
    {
        this.name = name;
        this.min = min;
        this.max = max;

        if(prüfeObImWertebereich(startwert)) {
            this.wert = startwert;
        } else {
            this.wert = standardStartwert;
            throw new IllegalArgumentException("Eingelesener Startwert für '" + this.name + "' ist außerhalb des Wertebereichs (" + min + " bis " + max + ")! Standard-Startwert wird stattdessen eingefügt.");
        }
        werte = new HashMap<>();
        
        System.out.println("---");
        System.out.println("Neuer Sektor '" + name + "' erzeugt");
        System.out.println("Wertebereich: " + min + " bis " + max);
        System.out.println("Startwert: " + this.wert);
    }
    
    /**
     * Alternativ-Konstruktor, falls kein Startwert vorhanden ist. Standardwert dann = standardStartwert. Siehe Instanzvariablen
     * 
     * @param name Name des Sektors
     * @param min Kleinster Wert des Sektors
     * @param max Größter Wert des Sektors
     */
    public Sektor(String name, int min, int max)
    {
        this.name = name;
        this.min = min;
        this.max = max;
        this.wert = standardStartwert;
        werte = new HashMap<>();
        System.out.println("---");
        System.out.println("Neuer Sektor '" + name + "' erzeugt");
        System.out.println("Wertebereich: " + min + " bis " + max);
        System.out.println("Startwert: " + this.wert);
    }
    
    /**
     * Prüfung des eingegebenen Werts mit min und max dieses Sektors.
     * 
     * @param eingabe Wert, welcher geprüft werden soll
     * @return TRUE: Im Wertebereich, FALSE: Nicht im Wertebereich --> Weiterverwendung in Abbruchsbedingung
     */
    public boolean prüfeObImWertebereich(int eingabe)
    {
        System.out.printf("Prüfe ob " + eingabe + " im Wertebereich ... ");
        if(eingabe <= max && eingabe >= min) {
            System.out.println(eingabe + " ist im Wertebereich");
            return true;
        } else {
            System.out.println(eingabe + " ist NICHT im Wertebereich");
            return false;
        }
    }
    
    /**
     * Ruft aktuellen Wert auf und speichert es in der werte HashMap.
     */
    public void aktuellenWertSpeichern(int runde) 
    {
        //Speicher aktuellen Wert in Hashmap
        werte.put(runde, getWert());
    }
    
    //Getter & Setter
    public String getName() 
    {
        return name;
    }

    public int getMin() 
    {
        return min;
    }

    public int getMax() 
    {
        return max;
    }

    public int getWert() 
    {
        return wert;
    }
    public void setWert(int wert) 
    {
        this.wert = wert;
    }
}


