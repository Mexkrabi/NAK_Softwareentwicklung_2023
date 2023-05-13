import java.util.Scanner;

/**
 * Beschreiben Sie hier die Klasse Main.
 * 
 * @author Sven Vazquez de Lara Kallas, Malte Fischer, Livia Kadenbach 
 * @version 0.1
 */
public class Main
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    
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
        
        GUI gui = new GUI();
        gui.setSpielstand("Start");
        gui.spielstandänderung();

        //Warten auf Start-Knopfdruck
        //# SCHRITT 1 ------------------------------
        //#EVENT: SPIELSTART
        
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
        
        //Sektoren erzeugen
        Sektor bevölkerungsgröße = new Sektor();
        //etc...
        
        //spielstart();
        
        //TESTING vvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
        new Logik(new DateiLeser());
        new Sektor();
        
        //TESTING ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        
        //# SCHRITT 2 ------------------------------
        //# SCHRITT 3 ------------------------------
        //# SCHRITT 4 ------------------------------

    }
    
    /*
     * Sinnvolle Methoden, die unser Leben erleichtern.
     */
    
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