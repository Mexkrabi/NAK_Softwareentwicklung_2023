import java.util.Scanner;

/**
 * Beschreiben Sie hier die Klasse Main.
 * The brain of the operation.
 * 
 * @author Sven Vazquez de Lara Kallas, Malte Fischer, Livia Kadenbach 
 * @version 0.1
 */
public class Main
{
    // static Variablen --> erlauben Zugriff von allen Klassen ohne ein Objekt zu erzeugen
    public static String spielstand;
    public static String pfadStartwerte; //Speichert Dateipfad der .sim
    
    public static DateiLeser dateiLeser; //DateiLeser --> Zugriff von allen Klassen möglich
    public static GUI gui; //Globaler GUI-Handler
    public static Logik logik; //Globaler Logik-Handler
    /**
     * Main Funktion
     */
    public static void main(String[] args) {
        
        //#  Grober Ablauf
        //#  -------------
        /* 0) Menü
         *   -> Start und Ende Knopf
         *   -> Gibt Signal zum starten weiter
         * 
         * 1) Start des Spiels
         *   -> Einlesen .sim Datei
         *   -> Startwerte einlesen
         *   -> Anzahl Runden festlegen
         *   -> Anzeigen der Startwerte
         *   -> Erzeuge Sektoren und weise Werte zu
         *   
         *   [wiedehole 2 & 3 so oft wie es Runden gibt]
         *   
         * 2) Spieler Input
         *   -> Anzeigen der aktuellen Werte
         *   -> Investiere Staatsvermögen
         *   
         * 3) Berechnung
         *   -> Größen der Sektoren wirken aufeinander in festgelegter Reihenfolge
         *   -> prüfen, ob im Wertebereich (Abbruchsbedingung)
         *   -> Simuilationserfolg ermitteln (pro Runde)
         *   
         *   [wiedehole 2 & 3 so oft wie es Runden gibt]
         *   
         * 4) Ende
         *   -> speichern der Zwischenwerte/Simulationserfolge in .res Datei
         *   -> Graph einblenden
         *   -> (evtl. Highscore anzeigen)
         *   -> Neues Spiel starten (reset aller Werte, Möglichkeit einlesen neuer Datei)
         *
         */
        
        //# Code startet
        
        System.out.println("Code startet...");
    
        //# SCHRITT 0 ------------------------------
        
        gui = new GUI();
        gui.setSpielstand("START");
        gui.spielstandänderung();
        //warten, bis Spielstand geändert wird
        warteSolangeNoch("START");


        //Warten auf Start-Knopfdruck
        //# SCHRITT 1 ------------------------------
        //#EVENT: SPIELSTART
        dateiLeser = new DateiLeser();
        warteSolangeNoch("AUSWAHL"); //Warte, bis Auswahl der .sim Datei in der GUI getätigt
        pfadStartwerte = dateiLeser.simDateiAuswahl(); //Speichert Dateipfad der .sim
        
        //Datei wird eingelesen nach folgenden Daten:
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
        
        //#STARTWERTE EINLESEN
        logik = new Logik(); //Logik-Handler generiert
        try {
            logik.rundenzahl = alsInteger(dateiLeser.auslesen(pfadStartwerte, "Rundenzahl")); //Rundenanzahl festgelegt
            
            //# in HashMap packen
            // Wiederholen für alle Anfangswerte vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
            String str = dateiLeser.auslesen(pfadStartwerte, "Bev�lkerungsgr��e");
            logik.startwerteHash.put("Bevölkerungsgröße", alsInteger(str));
            // Wiederholen für alle Anfangswerte ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
            /* hier in kompakter Form: vvvvvv           *///#Prüfen, ob Umlaute gehen oder nicht
            logik.startwerteHash.put("Bevölkerungswachstum", alsInteger(dateiLeser.auslesen(pfadStartwerte, "Bev�lkerungswachstum"))); 
            logik.startwerteHash.put("Lebensqualität", alsInteger(dateiLeser.auslesen(pfadStartwerte, "Lebensqualit�t")));
            logik.startwerteHash.put("Wirtschaftsleistung", alsInteger(dateiLeser.auslesen(pfadStartwerte, "Wirtschaftsleistung")));
            logik.startwerteHash.put("Modernisierungsgrad", alsInteger(dateiLeser.auslesen(pfadStartwerte, "Modernisierungsgrad")));
            logik.startwerteHash.put("Politische Stabilität", alsInteger(dateiLeser.auslesen(pfadStartwerte, "Politische Stabilit�t")));
            logik.startwerteHash.put("Umweltverschmutzung", alsInteger(dateiLeser.auslesen(pfadStartwerte, "Umweltverschmutzung")));
            logik.startwerteHash.put("Bildung", alsInteger(dateiLeser.auslesen(pfadStartwerte, "Bildung")));
            logik.startwerteHash.put("Staatsvermögen", alsInteger(dateiLeser.auslesen(pfadStartwerte, "Staatsverm�gen")));
        } catch(Exception ex)
        {
            ex.printStackTrace(); 
        }
        
        //#Sektoren erzeugen /////////////////////////////
        /* To Do für jeden Sektor: (9x bzw. 11x)
         * -> Name              String
         * -> min               int
         * -> max               int
         * -> startwert         int
         */
        //Bevölkerungsgröße
        int startwert1 = logik.startwerteHash.get("Bevölkerungsgröße"); //Startwert aus Hashmap ziehen
        Sektor bevölkerungsgröße = new Sektor("Bevölkerungsgröße", 1, 50, startwert1); //min max aus Angabe Tabelle (HA-Dokument)
        //Bevölkerungswachstum
        int startwert2 = logik.startwerteHash.get("Bevölkerungswachstum"); //Startwert aus Hashmap ziehen
        Sektor bevölkerungswachstum = new Sektor("Bevölkerungswachstum", 1, 30, startwert2); //min max aus Angabe Tabelle (HA-Dokument)
        //Wirtschaftsleistung
        int startwert3 = logik.startwerteHash.get("Wirtschaftsleistung"); //Startwert aus Hashmap ziehen
        Sektor wirtschaftsleistung = new Sektor("Wirtschaftsleistung", 1, 30, startwert3); //min max aus Angabe Tabelle (HA-Dokument)
        //Modernisierungsgrad
        int startwert4 = logik.startwerteHash.get("Modernisierungsgrad"); //Startwert aus Hashmap ziehen
        Sektor modernisierungsgrad = new Sektor("Modernisierungsgrad", 1, 30, startwert4); //min max aus Angabe Tabelle (HA-Dokument)
        //Politische Stabilität
        int startwert5 = logik.startwerteHash.get("Politische Stabilität"); //Startwert aus Hashmap ziehen
        Sektor politische_stabilität = new Sektor("Politische Stabilität", -10, 40, startwert5); //min max aus Angabe Tabelle (HA-Dokument)
        //Umweltverschmutzung
        int startwert6 = logik.startwerteHash.get("Umweltverschmutzung"); //Startwert aus Hashmap ziehen
        Sektor umweltverschmutzung = new Sektor("Umweltverschmutzung", 1, 30, startwert6); //min max aus Angabe Tabelle (HA-Dokument)
        //Lebensqualität
        int startwert7 = logik.startwerteHash.get("Lebensqualität"); //Startwert aus Hashmap ziehen
        Sektor lebensqualität = new Sektor("Lebensqualität", 1, 30, startwert7); //min max aus Angabe Tabelle (HA-Dokument)
        //Bildung
        int startwert8 = logik.startwerteHash.get("Bildung"); //Startwert aus Hashmap ziehen
        Sektor bildung = new Sektor("Bildung", 1, 30, startwert8); //min max aus Angabe Tabelle (HA-Dokument)
        //Staatsvermögen
        int startwert9 = logik.startwerteHash.get("Staatsvermögen"); //Startwert aus Hashmap ziehen
        Sektor staatsvermögen = new Sektor("Staatsvermögen", 1, 32000, startwert9); //min max aus Angabe Tabelle (HA-Dokument)

        //Bevölkerungswachstumsfaktor
        Sektor bevölkerungswachstumsfaktor = new Sektor("Bevölkerungswachstumsfaktor", 1, 3, 0); //min max aus Angabe Tabelle (HA-Dokument)
        //Versorgungslage
        Sektor versorgungslage = new Sektor("Versorgungslage", -4, 1, 0); //min max aus Angabe Tabelle (HA-Dokument)

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
        
        //etc...
        gui.setSpielstand("STARTWERTE");
        gui.spielstandänderung();
        //warten, bis Spielstand geändert wird
        //warteSolangeNoch("STARTWERTE");
        //spielstart();
        
        //#TESTING vvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
        System.out.println("###############Testing###############");
        
        System.out.println("Gebe Hashmap mit Startwerten aus:");
        for (String i : logik.startwerteHash.keySet()) {
          System.out.println("key: " + i + " value: " + logik.startwerteHash.get(i));
        }
        
        System.out.println("###############Testing###############");
        //#TESTING ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        
        //# SCHRITT 2 ------------------------------
        //# SCHRITT 3 ------------------------------
        //# SCHRITT 4 ------------------------------

    }
    
    /* 
     * vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
     * Sinnvolle Methoden, die unser Leben erleichtern.
     * vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
     */

    /**
     * Methode erzeugt einen int Array. Einfach den kleinsten und größten Wert eingeber.
     * Der Rest wird immer +1 hinzugefügt.
     * 
     * @param start kleinster Wert des Arrays
     * @param ende größter Wert des Arrays
     */
    public int[] erzeugeArray(int start, int ende) {
        
        int größe = ende - start;
        int[] array = new int[größe]; // Erstelle das Array mit der angegebenen Größe
        
        for (int i = 0; i < größe; i++) {
            array[i] = start + i; // Fülle das Array mit der Zahlenfolge von start bis end
        }
        
        return array; // Gib das fertige Array zurück
    }

    
    /**
     * Wandelt einen String zu einem Integer um
     * 
     * @param str Der String, der zu einem Integer umgewandelt werden soll
     */
    public static Integer alsInteger(String str) throws Exception 
    {
        /////////////////////
        //String to Integer//
        /////////////////////
        try{
            System.out.println("Versuche String in Integer Umwandlung von: " + str);
            String nurZahlen = str.replaceAll("\\D", ""); // Kürzen des Strings, um alle Nicht-Ziffern zu entfernen
            int number = Integer.parseInt(nurZahlen);
            System.out.println("Output: " + number); // output
            return number;
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
            throw new Exception("! Umwandlung von '" + str + "' war nicht erfolgreich"); //Custom Fehlermeldung auswerfen
        }
        
    }
    
    /**
     * while-Schleife, die solange wartet bis die Variable "spielstand" vom mitgegebenem Wert abweicht
     * 
     * @param woraufGewartetWird Wert, der den Code anhält, bis dieser sich ändert
     */
    public static void warteSolangeNoch(String woraufGewartetWird) 
    {
        while(gui.getSpielstand() == woraufGewartetWird)
        {
            try {
                // Hier wird der Thread in der CPU blockiert, bis der Wert der Variable geändert wird
                //# evtl. Überprüfen!
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Vereinfachte Methode zum Einlesen von Konsoleneingaben. Funktioniert mit Javas "Scanner" Klasse
     * und wartet, bis ein beliebiger Wert eingetippt wurde.
     * 
     * @return Gibt Wert wieder, der in der Konsole eingegeben wurde.
     */
    public static String konsoleneingabe() 
    {
        Scanner sc = new Scanner(System.in); //Konsoleneingabeleser
        return sc.next();
    }
}