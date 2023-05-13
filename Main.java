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
        
        System.out.println("Hello World!");
        
        //TESTING vvvvvvvvvvvvvvvvvvvvvvvvvvvvvv

        GUI gui = new GUI();
        gui.setSpielstand("Start");
        gui.spielstandänderung();
        
        new Logik(new DateiLeser());
        new Sektor();
        
        //TESTING ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        
        /*
         * Grober Ablauf
         * -------------
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