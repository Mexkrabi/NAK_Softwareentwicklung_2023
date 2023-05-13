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
         *   
         *   [wiedehole 2 & 3 so oft wie es Runden gibt]
         *   
         * 2) Spieler Input
         *   -> Anzeigen der Startwerte
         *   -> Investiere Staatsvermögen (erstmalig)
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
        gui.setSpielstand("Start");
        gui.spielstandänderung();
        //warten, bis Spielstand geändert wird
        warteSolangeNoch("Start");


        //Warten auf Start-Knopfdruck
        //# SCHRITT 1 ------------------------------
        //#EVENT: SPIELSTART
        dateiLeser = new DateiLeser();
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
        
        //STARTWERTE EINLESEN
        logik = new Logik();
        
        String str = dateiLeser.auslesen(pfadStartwerte, "Bev�lkerungsgr��e");   //#Prüfen, ob Umlaute gehen oder nicht
        logik.startwerteHash.put("Bevölkerungsgröße",toInteger(str));
        
        dateiLeser.auslesen(pfadStartwerte, "Bev�lkerungswachstum");
        dateiLeser.auslesen(pfadStartwerte, "Wirtschaftsleistung");
        dateiLeser.auslesen(pfadStartwerte, "Modernisierungsgrad");
        dateiLeser.auslesen(pfadStartwerte, "Politische Stabilit�t");
        dateiLeser.auslesen(pfadStartwerte, "Umweltverschmutzung");
        dateiLeser.auslesen(pfadStartwerte, "Bildung");
        dateiLeser.auslesen(pfadStartwerte, "Staatsverm�gen");
        
        dateiLeser.auslesen(pfadStartwerte, "Rundenzahl");

        //#danach in HashMap packen
        
        //Sektoren erzeugen /////////////////////////////
        //Sektor bevölkerungsgröße = new Sektor();
        
        //etc...
        
        //spielstart();
        
        //TESTING vvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
        
        
        //TESTING ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        
        //# SCHRITT 2 ------------------------------
        //# SCHRITT 3 ------------------------------
        //# SCHRITT 4 ------------------------------

    }
    
    /*
     * Sinnvolle Methoden, die unser Leben erleichtern.
     */
    
    /**
     * Wandelt einen String zu einem Integer um
     * 
     * @param str Der String, der zu einem Integer umgewandelt werden soll
     */
    public static Integer toInteger(String str) 
    {
        /////////////////////
        //String to Integer//
        /////////////////////
        try{
            System.out.println("Versuche String in Integer Umwandlung von: " + str);
            int number = Integer.parseInt(str);
            System.out.println("Output: " + number); // output
            return number;
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
        System.out.println("! Umwandlung war nicht erfolgreich");
        return null;
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