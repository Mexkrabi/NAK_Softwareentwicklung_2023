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
 * @author Sven Vazquez de Lara Kallas, Malte Fischer, Livia Kadenbach 
 * @version 0.7
 */
public class Main
{
    // static Variablen --> erlauben Zugriff von allen Klassen ohne ein Objekt zu erzeugen
    //public static String spielstand; //wird stattdessen in der GUI gespeichert
    public static String pfadStartwerte; //Speichert Dateipfad der .sim
    public static boolean boolNeustarten; //wenn wahr, wird das Spiel neugestartet
    public static boolean fehlerBeimErzeugenEinesSektors = false; //wird wahr, falls beim erzeugen eines Sektors versucht wird, einen ungültigen Wert einzusetzen.
    
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
    
    /**
     * Main Funktion. Hier wird das Zusammenspiel der einzelnen Klassen und Objekte koordiniert und zentral geregelt.
     * Der Ablauf der einzelnen Aktionen wird hier definiert und ausgeführt.
     * Unterteilung in merhreren Schritten (vgl. Kommentare in dieser Funktion)
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
        gui = new GUI(); //GUI generiert
        
        gui.setSpielstand("START"); //Spiel bereit zum starten
        gui.spielstandänderung();
        warteSolangeNoch("START"); //warten, bis Spielstand geändert wird
        
        //# Ab hier alles in der Methode spielAblauf() ausgelagert [Schritt 1 - 4]        
        //do{
            //Main.boolNeustarten = false;
            
            spielAblauf();
            
        //} while(warteBis(boolNeustarten));
        
    }
    
    /**
     * Enthält den gesamten Spielablauf in einer Funktion, um das Spiel erneut starten zu können.
     * Wird erstmalig in der main() aufgerufen. Im Falle einer neuen Runde wird es in der GUI beim drücken des Startknopfes aufgerufen.
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
        Rundenzahl = 10
         */
        //#STARTWERTE EINLESEN UND IN HASHMAP PACKEN
        
        
        
        leseStartwerteFür("Rundenzahl");
        logik.rundenzahl = logik.startwerteHash.get("Rundenzahl");
        
        leseStartwerteFür("Bevölkerungsgröße");
        leseStartwerteFür("Bevölkerungswachstum");
        leseStartwerteFür("Lebensqualität");
        leseStartwerteFür("Wirtschaftsleistung");
        leseStartwerteFür("Modernisierungsgrad");
        leseStartwerteFür("Politische Stabilität");
        leseStartwerteFür("Umweltverschmutzung");
        leseStartwerteFür("Bildung");
        leseStartwerteFür("Staatsvermögen");
        /*
        try {
            //logik.rundenzahl = alsInteger(dateiLeser.auslesen(pfadStartwerte, "Rundenzahl")); //Rundenanzahl festgelegt
            
            //# in HashMap packen
            // Wiederholen für alle Anfangswerte vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
            String str = dateiLeser.auslesen(pfadStartwerte, "Bev�lkerungsgr��e");
            logik.startwerteHash.put("Bevölkerungsgröße", alsInteger(str));
            // Wiederholen für alle Anfangswerte ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
            // hier in kompakter Form: vvvvvv           
            //#Prüfen, ob Umlaute gehen oder nicht
            logik.startwerteHash.put("Bevölkerungswachstum", alsInteger(dateiLeser.auslesen(pfadStartwerte, "Bev�lkerungswachstum"))); 
            logik.startwerteHash.put("Lebensqualität", alsInteger(dateiLeser.auslesen(pfadStartwerte, "Lebensqualit�t")));
            logik.startwerteHash.put("Wirtschaftsleistung", alsInteger(dateiLeser.auslesen(pfadStartwerte, "Wirtschaftsleistung")));
            logik.startwerteHash.put("Modernisierungsgrad", alsInteger(dateiLeser.auslesen(pfadStartwerte, "Modernisierungsgrad")));
            logik.startwerteHash.put("Politische Stabilität", alsInteger(dateiLeser.auslesen(pfadStartwerte, "Politische Stabilit�t")));
            logik.startwerteHash.put("Umweltverschmutzung", alsInteger(dateiLeser.auslesen(pfadStartwerte, "Umweltverschmutzung")));
            logik.startwerteHash.put("Bildung", alsInteger(dateiLeser.auslesen(pfadStartwerte, "Bildung")));
            logik.startwerteHash.put("Staatsvermögen", alsInteger(dateiLeser.auslesen(pfadStartwerte, "Staatsverm�gen")));
        } catch(Exception ex) {
            ex.printStackTrace(); 
        }
        */
        System.out.println("---\nHashmap mit Startwerten:");
        for (String i : logik.startwerteHash.keySet()) {
          System.out.println("Key: " + i + " - Wert: " + logik.startwerteHash.get(i));
        }
        
        //#SEKTOREN ERZEUGEN
        alleSektorenErzeugen(); //Methode erzeugt alle Sektoren inkl. zugehörigen Startwert
        
        if(fehlerBeimErzeugenEinesSektors) { //Ausgabe einer GUI-Fehlermeldung, falls es einen falschen Wert gab.
            gui.popUpAusgeben("Einige Angaben aus der ausgewählten Datei sind fehlerhaft oder wurden nicht erkannt. "
                                + "Die betroffenen Sektoren wurden mit dem Standardwert " 
                                + Sektor.standardStartwert + " erzeugt.");
        }

        //#LOOP ÜBER DIE RUNDENANZAHL
        while (logik.aktuelleRunde <= logik.rundenzahl) {
            
            //# SCHRITT 2 ------------------------------
            //# WERTE ANZEIGEN
            gui.setSpielstand("STARTWERTE");
            gui.spielstandänderung();
            
            //warten, bis Spielstand geändert wird
            //warteSolangeNoch("STARTWERTE");
            //spielstart();
            
            //# INVESTIERE STAATSVERMÖGEN
            //Investieren passiert alles über die GUI
            warteBis("WERTZUWEISEN");
            warteSolangeNoch("WERTZUWEISEN");
            
            
            //# SCHRITT 3 ------------------------------
            //# EVENT: BERECHNUNGSPHASE
            gui.setSpielstand("LADEN"); //Ladescreen
            gui.spielstandänderung();
            
            warteBis("BERECHNUNG");
            logik.rundeBerechnen(); //Berechnung über die Logik
            logik.speichernRundenwerte(logik.aktuelleRunde); //Speichern aller Rundenwerte
            
            if(gui.getSpielstand() != "GAMEOVER") {
                System.out.println("Rundenzahl erhöht");
                logik.aktuelleRunde++;
            } else {
                System.out.println("Spielstand wurde auf 'GAMEOVER' gesetzt, keine weitere Rundenberechnung");
                break;
            }
        }
        
        //# SCHRITT 4 ------------------------------
        
        //Victory or Game Over
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
            
            // Iteriere über die äußere HashMap
            /*for (HashMap<String, Integer> innereMap : logik.masterHash.values()) {
                // Iteriere über die innere HashMap
                String content = hashMapToString(innereMap);
                writer.write(content);

                for (Integer value : innereMap.values()) {
                    // Gib den extrahierten Wert aus
                    System.out.println(value);
                    
                }
            }*/
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
            schreibeInDatei(bevölkerungsgröße, writer);
            schreibeInDatei(bevölkerungswachstum, writer);
            schreibeInDatei(wirtschaftsleistung, writer);
            schreibeInDatei(modernisierungsgrad, writer);
            schreibeInDatei(politische_stabilität, writer);
            schreibeInDatei(umweltverschmutzung, writer);
            schreibeInDatei(lebensqualität, writer);
            schreibeInDatei(bildung, writer);
            schreibeInDatei(staatsvermögen, writer);
            schreibeInDatei(bevölkerungswachstumsfaktor, writer);
            schreibeInDatei(versorgungslage, writer);
            
            System.out.println("Die Datei wurde erfolgreich erstellt.");

        } catch (IOException e) {
            System.out.println("Fehler beim Schreiben der Datei: " + e.getMessage());
        }
        //////Ausgabe beendet ^^^^^^
        
        warteBis("NEUSTART"); //wichtig, warten
        
        main(new String[]{}); //Wiederaufrufen der main() Funktion --> Code startet neu
        
        //# Ende der main()
    }
    
    /**
     * Methode erzeugt Sektoren inkl. zugehörigen Startwert aus der startwerteHash, welche zuvor aus den Inhalten der .sim Datei erzeugt wurde.
     */
    private static void alleSektorenErzeugen(){
        /* To Do für jeden Sektor: (9x bzw. 11x)
         * -> Name              String
         * -> min               int
         * -> max               int
         * -> startwert         int
         */
        //Bevölkerungsgröße
        //erzeugeSektor("Bevölkerungsgröße", 1, 50);
        try {
            int startwert1 = logik.startwerteHash.get("Bevölkerungsgröße"); //Startwert aus Hashmap ziehen
            bevölkerungsgröße = new Sektor("Bevölkerungsgröße", 1, 50, startwert1); //min max aus Angabe Tabelle (HA-Dokument)
        } catch(Exception ex) {
            bevölkerungsgröße = new Sektor("Bevölkerungsgröße", 1, 50);//Sektor erzeugen, falls kein Startwert gefunden (Startwert in Sektor() definiert)
            ex.printStackTrace(); 
        }
        
        //Bevölkerungswachstum
        //erzeugeSektor("Bevölkerungswachstum", 1, 30);
        try {
            int startwert2 = logik.startwerteHash.get("Bevölkerungswachstum"); //Startwert aus Hashmap ziehen
            bevölkerungswachstum = new Sektor("Bevölkerungswachstum", 1, 30, startwert2); //min max aus Angabe Tabelle (HA-Dokument)
        } catch(Exception ex) {
            bevölkerungswachstum = new Sektor("Bevölkerungswachstum", 1, 30); //Sektor erzeugen, falls kein Startwert gefunden (Startwert in Sektor() definiert)
            ex.printStackTrace(); 
        }
        
        //Wirtschaftsleistung
        //erzeugeSektor("Wirtschaftsleistung", 1, 30);
        try {
            int startwert3 = logik.startwerteHash.get("Wirtschaftsleistung"); //Startwert aus Hashmap ziehen
            wirtschaftsleistung = new Sektor("Wirtschaftsleistung", 1, 30, startwert3); //min max aus Angabe Tabelle (HA-Dokument)        
        } catch(Exception ex) {
            wirtschaftsleistung = new Sektor("Wirtschaftsleistung", 1, 30); //Sektor erzeugen, falls kein Startwert gefunden (Startwert in Sektor() definiert)
            ex.printStackTrace(); 
        }
        
        //Modernisierungsgrad
        //erzeugeSektor("Modernisierungsgrad", 1, 30);
        try {
            int startwert4 = logik.startwerteHash.get("Modernisierungsgrad"); //Startwert aus Hashmap ziehen
            modernisierungsgrad = new Sektor("Modernisierungsgrad", 1, 30, startwert4); //min max aus Angabe Tabelle (HA-Dokument)       
        } catch(Exception ex) {
            modernisierungsgrad = new Sektor("Modernisierungsgrad", 1, 30); //Sektor erzeugen, falls kein Startwert gefunden (Startwert in Sektor() definiert)
            ex.printStackTrace(); 
        }
        
        //Politische Stabilität
        //erzeugeSektor("Politische Stabilität", -10, 40);
        try {
            int startwert5 = logik.startwerteHash.get("Politische Stabilität"); //Startwert aus Hashmap ziehen
            politische_stabilität = new Sektor("Politische Stabilität", -10, 40, startwert5); //min max aus Angabe Tabelle (HA-Dokument) 
        } catch(Exception ex) {
            politische_stabilität = new Sektor("Politische Stabilität", -10, 40); //Sektor erzeugen, falls kein Startwert gefunden (Startwert in Sektor() definiert)
            ex.printStackTrace(); 
        }
        
        //Umweltverschmutzung
        //erzeugeSektor("Umweltverschmutzung", 1, 30);
        try {
            int startwert6 = logik.startwerteHash.get("Umweltverschmutzung"); //Startwert aus Hashmap ziehen
            umweltverschmutzung = new Sektor("Umweltverschmutzung", 1, 30, startwert6); //min max aus Angabe Tabelle (HA-Dokument)        
        } catch(Exception ex) {
            umweltverschmutzung = new Sektor("Umweltverschmutzung", 1, 30); //Sektor erzeugen, falls kein Startwert gefunden (Startwert in Sektor() definiert)
            ex.printStackTrace(); 
        }
        
        //Lebensqualität
        //erzeugeSektor("Lebensqualität", 1, 30);
        try {
            int startwert7 = logik.startwerteHash.get("Lebensqualität"); //Startwert aus Hashmap ziehen
            lebensqualität = new Sektor("Lebensqualität", 1, 30, startwert7); //min max aus Angabe Tabelle (HA-Dokument)        
        } catch(Exception ex) {
            lebensqualität = new Sektor("Lebensqualität", 1, 30); //Sektor erzeugen, falls kein Startwert gefunden (Startwert in Sektor() definiert)
            ex.printStackTrace(); 
        }
        
        //Bildung
        //erzeugeSektor("Bildung", 1, 30);
        try {
            int startwert8 = logik.startwerteHash.get("Bildung"); //Startwert aus Hashmap ziehen
            bildung = new Sektor("Bildung", 1, 30, startwert8); //min max aus Angabe Tabelle (HA-Dokument)       
        } catch(Exception ex) {
            bildung = new Sektor("Bildung", 1, 30); //Sektor erzeugen, falls kein Startwert gefunden (Startwert in Sektor() definiert)
            ex.printStackTrace(); 
        }
        
        //Staatsvermögen
        //erzeugeSektor("Staatsvermögen", 1, 32000);
        try {
            int startwert9 = logik.startwerteHash.get("Staatsvermögen"); //Startwert aus Hashmap ziehen
            staatsvermögen = new Sektor("Staatsvermögen", 1, 32000, startwert9); //min max aus Angabe Tabelle (HA-Dokument)   
        } catch(Exception ex) {
            staatsvermögen = new Sektor("Staatsvermögen", 1, 32000); //Sektor erzeugen, falls kein Startwert gefunden (Startwert in Sektor() definiert)
            ex.printStackTrace(); 
        }
        

        //Bevölkerungswachstumsfaktor
        //erzeugeSektor("Bevölkerungswachstumsfaktor", 1, 3);
        bevölkerungswachstumsfaktor = new Sektor("Bevölkerungswachstumsfaktor", 1, 3); //min max aus Angabe Tabelle (HA-Dokument)
        logik.einflussRechner(logik.bg_auf_bwf, bevölkerungsgröße, bevölkerungswachstumsfaktor); //richtiger Startwert hier berechnet
        
        //Versorgungslage
        //erzeugeSektor("Versorgungslage", 1, 30);
        versorgungslage = new Sektor("Versorgungslage", -4, 1); //min max aus Angabe Tabelle (HA-Dokument)
        logik.einflussRechner(logik.wl_auf_vl, wirtschaftsleistung, versorgungslage); //richtiger Startwert hier berechnet
    }
    
    //#TODO @Livia
    /**
     * 
     */
    private static void schreibeInDatei(Sektor sektor, BufferedWriter writer) {
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
    
    /**
     * Methode mit try-catch Block, um Sektoren anahnd von eingelesenem Startwert zu erzeugen.
     * Falls kein Startwert vorhanden/gefunden, wird ein Standardwert eingefügt.
     * 
     * @param name Sektor-Name
     * @param min Kleinst-möglicher Wert
     * @param max Größt-möglicher Wert
     */
    private static void erzeugeSektor(String name, int min, int max) 
    {
        try {
            int startwert1 = logik.startwerteHash.get(name); //Startwert aus Hashmap ziehen
            bevölkerungsgröße = new Sektor(name, min, max, startwert1); //min max aus Angabe Tabelle (HA-Dokument)
        } catch(Exception ex) {
            bevölkerungsgröße = new Sektor(name, min, max); //Sektor erzeugen, falls kein Startwert gefunden (Startwert in Sektor() definiert)
            ex.printStackTrace(); 
        }
    }
    
    /**
     * Liest Startwerte aus der vorher ausgewählten Datei ein und fügt sie zur startWerte HashMap hinzu.
     * 
     * @param input Nach diesem Namen wird in der Datei gesucht, der zugehörige Zahlenwert ausgelesen und gespeichert.
     */
    private static void leseStartwerteFür(String input) 
    {
        //vervollständigen
        String str = dateiLeser.auslesen(pfadStartwerte, ersetzeSonderzeichen(input));
        try {
            logik.startwerteHash.put(input, alsInteger(str));
        } catch (Exception ex) {
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
     * Ersetzt Umlaute und vordefinierte Sonderzeivhen in "�" für das Einlesen von Dateien, die keine Sonderzeichen unterstützen.
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
    
    //#TODO: Quelle für Clock einfügen
    /**
     * while-Schleife, die solange wartet bis die Variable "spielstand" vom mitgegebenem Wert abweicht
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
    
    //#TODO: Quelle für Clock einfügen
    /**
     * while-Schleife, die solange wartet bis die Variable "spielstand" dem mitgegebenem Wert entspricht
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
    
    //#TODO: Quelle für Clock einfügen
    /**
     * while-Schleife, die solange wartet bis eine beliebige boolean Variable auf TRUE gesetzt wird.
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
     * @return Gibt Wert wieder, der in der Konsole eingegeben wurde.
     */
    public static String konsoleneingabe() 
    {
        Scanner sc = new Scanner(System.in); //Konsoleneingabeleser
        return sc.next();
    }
    
    //#TODO @Malte: Description und Quelle
    /**
     * 
     */
    private static String hashMapToString(Map<String, Integer> hashMap) {
        StringBuilder sb = new StringBuilder();

        // Durchlaufe den Hash und konvertiere die Werte zu Text
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            sb.append(key).append("=").append(value).append(System.lineSeparator());
        }

        return sb.toString();
    }
}