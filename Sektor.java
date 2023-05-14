
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
    
    //# To Do: Getter und Setter für 'name', 'wertebereich' und 'wert' hinzufügen
    // instance variables - replace the example below with your own
    //private int[] wertebereich; //Akzeptierte Werte laut Aufgabenstellung
    //private String wird_beeinflusst_von; //Sektor, der Einfluss auf diese Kenngröße hat
    //private String beeinflusst;
    private int wert; //Aktueller Wert 
    private String name; //Bezeichnung des Sektors
    private int min, max; //Minimum und Maximum definieren Wertebereich des Sektors

    //# Größen beim Start:
    /*
         +++ Ausgangslage +++
        Bevölkerungsgröße = 32
        Bevölkerungswachstum = 7
        Wirtschaftsleistung = 20
        Modernisierungsgrad = 5
        Politische Stabilität = 6
        Umweltverschmutzung = 16
        Lebensqualität = 20
        Bildung = 2
        Staatsvermögen = 8
        +++ Simulationsablauf +++
        Rundenzahl = 10�
    */
    /**
     * Constructor for objects of class Sektor
     * 
     * @param name Name des Sektors
     * @param min Kleinster Wert des Sektors
     * @param max Größter Wert des Sektors
     * @param startwert Definiere den Startwert des Sektors 
     */
    public Sektor(String name, int min, int max, int startwert /*,int[] wertebereich*/)
    {
        this.name = name;
        this.min = min;
        this.max = max;
        this.wert = startwert;
        //this.wertebereich = wertebereich; //Array mit allen möglichen Werten für den Sektor 
        System.out.println("---");
        System.out.println("Neuer Sektor '" + name + "' erzeugt");
        System.out.println("Wertebereich: " + min + " bis " + max);
        System.out.println("Startwert: " + startwert);
    }
}
