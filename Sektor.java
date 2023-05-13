
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
    //private String wird_beeinflusst_von; //Sektor, der Einfluss auf diese Kenngröße hat
    //private String beeinflusst;
    private int wert; //Aktueller Wert
    private String name; //Bezeichnung des Sektors

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
     */
    public Sektor(String name)
    {
        this.name = name;
        //TEST
        this.wertebereich = new int[1];
        System.out.println("Neuer Sektor erzeugt");
    }
}
