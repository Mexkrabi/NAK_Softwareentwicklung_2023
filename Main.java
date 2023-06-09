import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Die Klasse Main.
 * The brain of the operation.
 * 
 * Link zur GitHub Repository:
 * https://github.com/Mexkrabi/NAK_Softwareentwicklung_2023
 * 
 * @author Sven Vazquez de Lara Kallas
 * @version 3.0
 */
public class Main
{
    //static Variablen --> erlauben Zugriff von allen Klassen ohne ein Objekt zu erzeugen
    //public static String spielstand; //wird stattdessen in der GUI gespeichert
    public static String pfadStartwerte; //Speichert Dateipfad der .sim
    public static byte fehlerBeimErzeugenEinesSektors; //Zählt die Fehler beim erzeugen der Sektoren
    
    public static DateiLeser dateiLeser; //Zentraler DateiLeser --> Zugriff von allen Klassen möglich
    public static GUI gui; //Globaler GUI-Handler
    public static Logik logik; //Globaler Logik-Handler
    
    //Alle Sektoren hier als static Variablen
    public static Sektor bevölkerungsgröße;
    public static Sektor bevölkerungswachstum;
    public static Sektor wirtschaftsleistung;
    public static Sektor modernisierungsgrad;
    public static Sektor politische_stabilität;
    public static Sektor umweltverschmutzung;
    public static Sektor lebensqualität;
    public static Sektor bildung;
    public static Sektor staatsvermögen;
    public static Sektor bevölkerungswachstumsfaktor;
    public static Sektor versorgungslage;
    
    public static HashMap<String, Integer> startwerteMap; //HashMap mit allen Anfangswerten aus der .sim Datei
    
    /**
     * Main Funktion. Hier wird das Zusammenspiel der einzelnen Klassen und Objekte koordiniert und zentral geregelt.
     * Der Ablauf der einzelnen Aktionen wird hier definiert und ausgeführt.
     * Unterteilung in merhreren Schritten zur besseren Übersicht (vgl. Kommentare in dieser Funktion)
     * 
     * [Sven Vazquez de Lara Kallas]
     * 
     * @param args LEERE EINGABE
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
         *   -> (evtl. Graph einblenden)
         *   -> (evtl. Highscore anzeigen)
         *   -> Neues Spiel starten (reset aller Werte, Möglichkeit einlesen neuer Datei)
         *
         */
        
        //# Code startet
        
        System.out.println("Code startet...");
        
        //# SCHRITT 0 ------------------------------
        
        dateiLeser = new DateiLeser(); //DateiLeser erzeugt
        gui = new GUI(); //GUI erzeugt
        startwerteMap = new HashMap<>();
        
        gui.setSpielstand("START"); //Spiel bereit zum starten
        gui.spielstandänderung();
        warteSolangeNoch("START"); //warten, bis Spielstand geändert wird
        
        //# Ab hier alles in der Methode spielAblauf() ausgelagert [Schritt 1 - 4]        
                 
        spielAblauf();
            
        
        
    }
    
    /**
     * Enthält den gesamten Spielablauf in einer Funktion, um das Spiel erneut starten zu können.
     * Wird erstmalig in der main() aufgerufen. Im Falle einer neuen Runde wird es in der GUI beim drücken des Startknopfes aufgerufen.
     * 
     * [Sven Vazquez de Lara Kallas]
     */
    public static void spielAblauf()
    {
        //# SCHRITT 1 ------------------------------
        //#EVENT: SPIELSTART
 
        //gui.setSpielstand("START");
        //gui.spielstandänderung();
        
        warteSolangeNoch("START"); //warten, bis Spielstand geändert wird
        
        logik = new Logik(); //Logik-Handler generiert
        
        warteSolangeNoch("SPIELERNAME"); //warten, bis Spielername eingefügt wurde
        warteSolangeNoch("AUSWAHL"); //warten, bis Auswahl der .sim Datei in der GUI getätigt
        pfadStartwerte = dateiLeser.simDateiAuswahl(gui.strAuswahl); //Speichert Dateipfad der .sim
        
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
        Rundenzahl = 10
         */
        //#STARTWERTE EINLESEN UND IN HASHMAP PACKEN
        
        startwerteMap = dateiLeser.dateiAuslesen(pfadStartwerte);
        
        System.out.println("---\nHashmap mit Startwerten:");
        for (String i : startwerteMap.keySet()) {
          System.out.println("Key: " + i + " - Wert: " + startwerteMap.get(i));
        }
        
        logik.rundenzahl = startwerteMap.get("rundenzahl");
        
        //#SEKTOREN ERZEUGEN
        alleSektorenErzeugen(); //Methode erzeugt alle Sektoren inkl. zugehörigen Startwert
        
        if(fehlerBeimErzeugenEinesSektors > 0) { //Ausgabe einer GUI-Fehlermeldung, falls es mindestens einen falschen Wert gab.
            gui.popUpAusgeben(fehlerBeimErzeugenEinesSektors + (fehlerBeimErzeugenEinesSektors > 1 ? 
                                    " Angaben aus der ausgewählten Datei sind fehlerhaft oder wurden nicht erkannt. Die betroffenen Sektoren wurden"
                                    : " Angabe aus der ausgewählten Datei ist fehlerhaft oder wurde nicht erkannt. Der betroffene Sektor wurde")
                                    + " mit dem Standardwert " + Sektor.standardStartwert + " erzeugt.");
        }

        //#LOOP ÜBER DIE RUNDENANZAHL
        while (logik.aktuelleRunde <= logik.rundenzahl) {
            
            //# SCHRITT 2 ------------------------------
            //# WERTE ANZEIGEN
            gui.setSpielstand("STARTWERTE");
            gui.spielstandänderung();
            
            //warten, bis Spielstand geändert wird
            //GUI zeigt währenddessen die aktuellen Werte an
            
            //# INVESTIERE STAATSVERMÖGEN
            warteBis("WERTZUWEISEN");
            warteSolangeNoch("WERTZUWEISEN"); //Investitionen werden über die GUI getätigt
            
            
            //# SCHRITT 3 ------------------------------
            //# EVENT: BERECHNUNGSPHASE
            gui.setSpielstand("LADEN"); //Ladescreen
            gui.spielstandänderung();
            
            warteBis("BERECHNUNG"); //Warte bis die GUI geladen ist
            logik.rundeBerechnen(); //Berechnung über die Logik
            logik.speichernRundenwerte(); //Speichern aller Rundenwerte für die aktuelle Runde
            
            //Prüfung auf Game Over
            if(gui.getSpielstand() != "GAMEOVER") {
                System.out.println("Rundenzahl erhöht");
                logik.aktuelleRunde++;
            } else {
                System.out.println("Spielstand wurde auf 'GAMEOVER' gesetzt, keine weitere Rundenberechnung");
                break; //Bricht die while-Schleife ab. Somit keine weiteren Berechnungen. Weiter mit Schritt 4 
            }
        }
        
        //# SCHRITT 4 ------------------------------
        
        //Prüft ob Spiel nicht in der letzten Runde verloren wurde; ruft dann den Victory-Screen auf
        if(gui.getSpielstand() != "GAMEOVER") {
            gui.setSpielstand("VICTORY");
            gui.spielstandänderung();
            System.out.println("GEWONNEN!!!!!");
        }
        
        
        //#AUSGABE IN .res DATEI
        String dateiName = logik.spielername + "-Ergebnis_" + zeitstempel() + ".res"; // Dateinamen erstellen
        String dateipfad = System.getProperty("user.dir") + "/res-Dateien"; //Dateipfad des res-Dateien Ordners auslesen
        
        //#Schreiben:
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dateipfad + "/" + dateiName))) {
            // Konvertiere den Hash zu einem Textformat und schreibe in die Datei
            
            writer.write("Simulationserfolg über die Runden:");
            writer.newLine();
            
            writer.newLine();
            HashMap<Integer, Integer> hashMap = logik.simulationsErfolg;
            for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();
                writer.write("Simulationserfolg in Runde " + key + " = " + value);
                writer.newLine();
            } 
            
            //ALLES AUSGELAGERT!!!  
            schreibeSektorInDatei(bevölkerungsgröße, writer);
            schreibeSektorInDatei(bevölkerungswachstum, writer);
            schreibeSektorInDatei(wirtschaftsleistung, writer);
            schreibeSektorInDatei(modernisierungsgrad, writer);
            schreibeSektorInDatei(politische_stabilität, writer);
            schreibeSektorInDatei(umweltverschmutzung, writer);
            schreibeSektorInDatei(lebensqualität, writer);
            schreibeSektorInDatei(bildung, writer);
            schreibeSektorInDatei(staatsvermögen, writer);
            schreibeSektorInDatei(bevölkerungswachstumsfaktor, writer);
            schreibeSektorInDatei(versorgungslage, writer);
            
            System.out.println("Die Datei wurde erfolgreich erstellt.");

        } catch (IOException e) {
            System.out.println("Fehler beim Schreiben der Datei: " + e.getMessage());
        }
        //////Ausgabe beendet ^^^^^^
        
        //# NEUSTART / Neues Spiel
        warteBis("NEUSTART"); //wichtig für den Codeablauf; WARTEN
        
        Main.logik.aktuelleRunde = 1; 
        main(new String[]{}); //Wiederaufrufen der main() Funktion --> Code startet neu
        
        //# Ende der main()
    }
       
    /**
     * Methode erzeugt alle Sektoren inkl. zugehörigen Startwert aus der startwerteHash.
     * Inhalte stammen aus der zuvor eingelesenen .sim Datei
     * 
     * [Livia Kadenbach]
     */
    private static void alleSektorenErzeugen(){
        int startwert;
        
        //Bevölkerungsgröße
        if(startwerteMap.get("bevölkerungsgröße") != null) { //Prüft, ob ein Startwert in der StartwerteMap vorhanden ist
            startwert = startwerteMap.get("bevölkerungsgröße");
            try {
                bevölkerungsgröße = new Sektor("Bevölkerungsgröße", 1, 50, startwert); //min max aus Angabe Tabelle (HA-Dokument)
            }
            catch (IllegalArgumentException e){ // Falls Startwert außerhalb des Werteberichs liegt
                fehlerBeimErzeugenEinesSektors++; //Fehler um 1 erhöhen
                bevölkerungsgröße = new Sektor("Bevölkerungsgröße", 1, 50); //Sektor mit Standard Startwert erzeugen
                System.out.println(e.getMessage()); 
            }
        }
        else{
            bevölkerungsgröße = new Sektor("Bevölkerungsgröße", 1, 50); //min max aus Angabe Tabelle (HA-Dokument)
            fehlerBeimErzeugenEinesSektors++; //Fehler um 1 erhöhen
            System.out.println("Kein Startwert für Bevölkerungsgröße gefunden."); 
        }
        
        
        //Bevölkerungswachstum
        if(startwerteMap.get("bevölkerungswachstum") != null) { //Prüft, ob ein Startwert in der StartwerteMap vorhanden ist
            startwert = startwerteMap.get("bevölkerungswachstum");
            try {
            bevölkerungswachstum = new Sektor("Bevölkerungswachstum", 1, 30, startwert); //min max aus Angabe Tabelle (HA-Dokument)
            }
            catch (IllegalArgumentException e){ // Falls Startwert außerhalb des Werteberichs liegt
                bevölkerungswachstum = new Sektor("Bevölkerungswachstum", 1, 30); //min max aus Angabe Tabelle (HA-Dokument)
                fehlerBeimErzeugenEinesSektors++;
                System.out.println(e.getMessage()); 
            }
        }
        else{
            bevölkerungswachstum = new Sektor("Bevölkerungswachstum", 1, 30); //min max aus Angabe Tabelle (HA-Dokument)
            fehlerBeimErzeugenEinesSektors++;
            System.out.println("Kein Startwert für Bevölkerungswachstum gefunden."); 
            
        }
        
        
        //Wirtschaftsleistung
        if(startwerteMap.get("wirtschaftsleistung") != null) { //Prüft, ob ein Startwert in der StartwerteMap vorhanden ist
            startwert = startwerteMap.get("wirtschaftsleistung");
            try {
            wirtschaftsleistung = new Sektor("Wirtschaftsleistung", 1, 30, startwert); //min max aus Angabe Tabelle (HA-Dokument)
            }
            catch (IllegalArgumentException e){ // Falls Startwert außerhalb des Werteberichs liegt
                wirtschaftsleistung = new Sektor("Wirtschaftsleistung", 1, 30); //min max aus Angabe Tabelle (HA-Dokument)
                fehlerBeimErzeugenEinesSektors++;
                System.out.println(e.getMessage()); 
            }
        }
        else{
            wirtschaftsleistung = new Sektor("Wirtschaftsleistung", 1, 30); //min max aus Angabe Tabelle (HA-Dokument)
            fehlerBeimErzeugenEinesSektors++;
            System.out.println("Kein Startwert für Wirtschaftsleistung gefunden."); 
        }
                
        
        //Modernisierungsgrad
        if(startwerteMap.get("modernisierungsgrad") != null) { //Prüft, ob ein Startwert in der StartwerteMap vorhanden ist
            startwert = startwerteMap.get("modernisierungsgrad");
            try {
            modernisierungsgrad = new Sektor("Modernisierungsgrad", 1, 30, startwert); //min max aus Angabe Tabelle (HA-Dokument)  
            }
            catch (IllegalArgumentException e){ // Falls Startwert außerhalb des Werteberichs liegt
                modernisierungsgrad = new Sektor("Modernisierungsgrad", 1, 30); //min max aus Angabe Tabelle (HA-Dokument)  
                fehlerBeimErzeugenEinesSektors++;
                System.out.println(e.getMessage()); 
            }
        }
        else{
            modernisierungsgrad = new Sektor("Modernisierungsgrad", 1, 30); //min max aus Angabe Tabelle (HA-Dokument)  
            fehlerBeimErzeugenEinesSektors++;
            System.out.println("Kein Startwert für Modernisierungsgrad gefunden."); 
        }
             
        
        
        //Politische Stabilität
        if(startwerteMap.get("politischestabilität") != null) { //Prüft, ob ein Startwert in der StartwerteMap vorhanden ist
            startwert = startwerteMap.get("politischestabilität");
            try {
            politische_stabilität = new Sektor("Politische Stabilität", -10, 40, startwert); //min max aus Angabe Tabelle (HA-Dokument) 
            }
            catch (IllegalArgumentException e){ // Falls Startwert außerhalb des Werteberichs liegt
                politische_stabilität = new Sektor("Politische Stabilität", -10, 40); //min max aus Angabe Tabelle (HA-Dokument) 
                fehlerBeimErzeugenEinesSektors++;
                System.out.println(e.getMessage()); 
            }
        }
        else{
            politische_stabilität = new Sektor("Politische Stabilität", -10, 40); //min max aus Angabe Tabelle (HA-Dokument) 
            fehlerBeimErzeugenEinesSektors++;
            System.out.println("Kein Startwert für Politische Stabilität gefunden."); 
        }
        
        
        //Umweltverschmutzung
        if(startwerteMap.get("umweltverschmutzung") != null) { //Prüft, ob ein Startwert in der StartwerteMap vorhanden ist
            startwert = startwerteMap.get("umweltverschmutzung");
            try {
            umweltverschmutzung = new Sektor("Umweltverschmutzung", 1, 30, startwert); //min max aus Angabe Tabelle (HA-Dokument)
            }
            catch (IllegalArgumentException e){ // Falls Startwert außerhalb des Werteberichs liegt
                umweltverschmutzung = new Sektor("Umweltverschmutzung", 1, 30); //min max aus Angabe Tabelle (HA-Dokument)
                fehlerBeimErzeugenEinesSektors++;
                System.out.println(e.getMessage());
            }
        }
        else{
            umweltverschmutzung = new Sektor("Umweltverschmutzung", 1, 30); //min max aus Angabe Tabelle (HA-Dokument)
            fehlerBeimErzeugenEinesSektors++;
            System.out.println("Kein Startwert für Umweltverschmutzung gefunden."); 
        }
                
        
        //Lebensqualität
        if(startwerteMap.get("lebensqualität") != null) { //Prüft, ob ein Startwert in der StartwerteMap vorhanden ist
            startwert = startwerteMap.get("lebensqualität");
            try {
            lebensqualität = new Sektor("Lebensqualität", 1, 30, startwert); //min max aus Angabe Tabelle (HA-Dokument)
            }
            catch (IllegalArgumentException e){ // Falls Startwert außerhalb des Werteberichs liegt
                lebensqualität = new Sektor("Lebensqualität", 1, 30); //min max aus Angabe Tabelle (HA-Dokument)
                fehlerBeimErzeugenEinesSektors++;
                System.out.println(e.getMessage()); 
            }
        }
        else{
            lebensqualität = new Sektor("Lebensqualität", 1, 30); //min max aus Angabe Tabelle (HA-Dokument)
            fehlerBeimErzeugenEinesSektors++;
            System.out.println("Kein Startwert für Lebensqualität gefunden."); 
        }
                
        
        //Bildung
        if(startwerteMap.get("bildung") != null) { //Prüft, ob ein Startwert in der StartwerteMap vorhanden ist
            startwert = startwerteMap.get("bildung");
            try {
                bildung = new Sektor("Bildung", 1, 30, startwert); //min max aus Angabe Tabelle (HA-Dokument)
            }
            catch (IllegalArgumentException e){ // Falls Startwert außerhalb des Werteberichs liegt
                bildung = new Sektor("Bildung", 1, 30); //min max aus Angabe Tabelle (HA-Dokument=
                fehlerBeimErzeugenEinesSektors++;
                System.out.println(e.getMessage());
            }
        }
        else{
            bildung = new Sektor("Bildung", 1, 30); //min max aus Angabe Tabelle (HA-Dokument=
            fehlerBeimErzeugenEinesSektors++;
            System.out.println("Kein Startwert für Bildung gefunden."); 
        }
              
        
        
        //Staatsvermögen
        if(startwerteMap.get("staatsvermögen") != null) { //Prüft, ob ein Startwert in der StartwerteMap vorhanden ist
            startwert = startwerteMap.get("staatsvermögen");
            try {
            staatsvermögen = new Sektor("Staatsvermögen", 1, 32000, startwert); //min max aus Angabe Tabelle (HA-Dokument) 
            }
            catch (IllegalArgumentException e){ // Falls Startwert außerhalb des Werteberichs liegt
                staatsvermögen = new Sektor("Staatsvermögen", 1, 32000); //min max aus Angabe Tabelle (HA-Dokument) 
                fehlerBeimErzeugenEinesSektors++;
                System.out.println(e.getMessage());
            }
        }
        else{
            staatsvermögen = new Sektor("Staatsvermögen", 1, 32000); //min max aus Angabe Tabelle (HA-Dokument) 
            fehlerBeimErzeugenEinesSektors++;
            System.out.println("Kein Startwert für Staatsvermögen gefunden."); 
        }
          
        

        //Bevölkerungswachstumsfaktor
        bevölkerungswachstumsfaktor = new Sektor("Bevölkerungswachstumsfaktor", 1, 3); //min max aus Angabe Tabelle (HA-Dokument)
        logik.einflussRechner(logik.bg_auf_bwf, bevölkerungsgröße, bevölkerungswachstumsfaktor); //richtiger Startwert hier berechnet
        
        //Versorgungslage
        versorgungslage = new Sektor("Versorgungslage", -4, 1); //min max aus Angabe Tabelle (HA-Dokument)
        logik.einflussRechner(logik.wl_auf_vl, wirtschaftsleistung, versorgungslage); //richtiger Startwert hier berechnet
    }
    
    /**
     * Schreibt innerhalb eines BufferedWriter Kontextes den Verlauf eines Sektors in die Datei
     * 
     * [Livia Kadenbach]
     * 
     * @param sektor Sektor für den der Verlauf in die Datei geschrieben werden soll
     * @param writer Kontext in dem geschrieben wird
     */
    private static void schreibeSektorInDatei(Sektor sektor, BufferedWriter writer) 
    {
        try {
            writer.newLine();
            HashMap<Integer, Integer> hashMap = sektor.werte;
            for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();
                writer.write(sektor.getName() + " in Runde " + key + " = " + value);
                writer.newLine();
            } 
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    //# diese Methode wird nicht mehr verwendet
    /**
     * Methode mit try-catch Block, um Sektoren anahnd von eingelesenem Startwert zu erzeugen.
     * Falls kein Startwert vorhanden/gefunden, wird ein Standardwert eingefügt.
     * 
     * [Sven Vazquez de Lara Kallas]
     * 
     * @param name Sektor-Name
     * @param min Kleinst-möglicher Wert
     * @param max Größt-möglicher Wert
     */
    private static void erzeugeSektor(String name, int min, int max) 
    {
        try {
            int startwert1 = startwerteMap.get(name); //Startwert aus Hashmap ziehen
            bevölkerungsgröße = new Sektor(name, min, max, startwert1); //min max aus Angabe Tabelle (HA-Dokument)
        } catch(Exception ex) {
            bevölkerungsgröße = new Sektor(name, min, max); //Sektor erzeugen, falls kein Startwert gefunden (Startwert in Sektor() definiert)
            ex.printStackTrace(); 
        }
    }
    
    /* 
     * vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
     * Sinnvolle Methoden, die unser Leben erleichtern.
     * vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
     */

    /**
     * Ruft den jetztigen Zeitpunkt auf und gibt diesen als Zeitstempel String zurück.
     * 
     * [Sven Vazquez de Lara Kallas]
     * 
     * Quelle zur Dokumentation: 
     * https://docs.oracle.com/javase/8/docs/api/java/time/package-summary.html
     * 
     * @return Gibt Zeitstempel in der Form "yyyyMMdd_HHmmss" als String zurück
     */
    public static String zeitstempel() 
    {
        //Zeitstempel erzeugen
        LocalDateTime zeitJetzt = LocalDateTime.now(); // Aktuelle Zeit abrufen
        DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"); // Format für den Zeitstempel festlegen
        String zeitstempel = zeitJetzt.format(form); // Zeitstempel in das gewünschte Format konvertieren
        return zeitstempel;
    }
    
    /**
     * Ersetzt Umlaute und vordefinierte Sonderzeichen in "�" für das Einlesen von Dateien, die keine Sonderzeichen unterstützen.
     * 
     * [Sven Vazquez de Lara Kallas]
     * 
     * @param text Zu ersetzender Text
     * @return Gibt eingegebenen Text zurück, mit "�" anstelle von Sonderzeichen. Ansonsten bleibt der Text gleich.
     */
    public static String ersetzeSonderzeichen(String text) 
    {
        try {
            String replacedText = text.replace("ä", "�")
                                      .replace("ö", "�")
                                      .replace("ü", "�")
                                      .replace("Ä", "�")
                                      .replace("Ö", "�")
                                      .replace("Ü", "�")
                                      .replace("ß", "�");
    
            System.out.println("'" + text + "' ersetzt mit '" + replacedText + "'");
            return replacedText;
        } catch (Exception ex) {
            System.out.println(text + " enthält keine Umlaute.");
            return text;
        }
    }

    /**
     * Methode erzeugt einen int Array. Einfach den kleinsten und größten Wert eingeber.
     * Der Rest wird immer +1 hinzugefügt.
     * 
     * [Sven Vazquez de Lara Kallas]
     * 
     * @param start kleinster Wert des Arrays
     * @param ende größter Wert des Arrays
     * @return Gibt fertigen int[] Array zurück
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
     * [Sven Vazquez de Lara Kallas]
     * 
     * @param str Der String, der zu einem Integer umgewandelt werden soll
     * @return Nur die Zahlen aus dem eingegebenen String
     */
    public static Integer alsInteger(String str) throws Exception 
    {
        /////////////////////
        //String to Integer//
        /////////////////////
        try{
            System.out.println("Versuche String in Integer Umwandlung von: " + str);
            String nurZahlen = str.replaceAll("[^\\d-]", ""); // Kürzen des Strings, um alle Nicht-Ziffern und Nicht-Minuszeichen zu entfernen
            int number = Integer.parseInt(nurZahlen); // Umwandeln des gekürzten Strings in einen Integer
            System.out.println("Output: " + number); // output
            return number;
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
            throw new Exception("! Umwandlung von '" + str + "' war nicht erfolgreich - Der String enthält keine Zahlen !"); //Custom Fehlermeldung auswerfen
        }
        
    }
    
    /**
     * while-Schleife, die solange wartet bis die Variable "spielstand" vom mitgegebenem Wert abweicht
     * 
     * Quelle für Dokumentation:
     * https://docs.oracle.com/javase/tutorial/essential/concurrency/sleep.html
     * 
     * [Sven Vazquez de Lara Kallas]
     * 
     * @param woraufGewartetWird Wert, der den Code anhält, solange dieser Spielstand noch bestehen bleibt
     * @return TRUE, sobald Warten beendet.
     */
    public static boolean warteSolangeNoch(String woraufGewartetWird) 
    {
        while(gui.getSpielstand() == woraufGewartetWird)
        {
            try {
                // Hier wird der Thread in der CPU blockiert, bis der Wert der Variable geändert wird
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * while-Schleife, die solange wartet bis die Variable "spielstand" dem mitgegebenem Wert entspricht
     * 
     * Quelle für Dokumentation:
     * https://docs.oracle.com/javase/tutorial/essential/concurrency/sleep.html
     * 
     * [Sven Vazquez de Lara Kallas]
     * 
     * @param woraufGewartetWird Wert, der den Code anhält, bis dieser Spielstand erreicht wird
     * @return TRUE, sobald Warten beendet.
     */
    public static boolean warteBis(String woraufGewartetWird) 
    {
        while(gui.getSpielstand() != woraufGewartetWird)
        {
            try {
                // Hier wird der Thread in der CPU blockiert, bis der Wert der Variable geändert wird
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    
    /**
     * while-Schleife, die solange wartet bis eine beliebige boolean Variable auf TRUE gesetzt wird.
     * 
     * Quelle für Dokumentation:
     * https://docs.oracle.com/javase/tutorial/essential/concurrency/sleep.html
     * 
     * [Sven Vazquez de Lara Kallas]
     * 
     * @param woraufGewartetWird boolean Wert, der den Code anhält, bis dieser auf TRUE gesetzt wird.
     * @return TRUE, sobald Warten beendet.
     */
        public static boolean warteBis(boolean woraufGewartetWird) 
    {
        while(woraufGewartetWird == true)
        {
            try {
                // Hier wird der Thread in der CPU blockiert, bis der Wert der Variable geändert wird
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    
    /**
     * Vereinfachte Methode zum Einlesen von Konsoleneingaben. Funktioniert mit Javas "Scanner" Klasse
     * und wartet, bis ein beliebiger Wert eingetippt wurde.
     * 
     * [Sven Vazquez de Lara Kallas]
     * 
     * @return Gibt Wert wieder, der in der Konsole eingegeben wurde.
     */
    public static String konsoleneingabe() 
    {
        Scanner sc = new Scanner(System.in); //Konsoleneingabeleser
        return sc.next();
    }
}