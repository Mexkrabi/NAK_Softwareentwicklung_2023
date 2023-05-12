
/**
 * Diese Klasse stellt die Grundstruktur für die einzelnen 
 * Kenngrößen (hier Sektoren genannt) dar.
 * Ein Sektor ist z.B. "Bevölkerungswachstum", "Lebensqualität", "Staatsvermögen"
 *
 * @author (your name)
 * @version (1.0)
 */
public class Sektor
{
    // instance variables - replace the example below with your own
    private int[] wertebereich; //Akzeptierte Werte laut Aufgabenstellung
    private String wird_beeinflusst_von; //Sektor, der Einfluss auf diese Kenngröße hat
    private String beeinflusst;
    private int wert; //Aktueller Wert
    

    /**
     * Constructor for objects of class Sektor
     */
    public Sektor()
    {
        //TEST
        this.wertebereich = new int[1];
    }
}
