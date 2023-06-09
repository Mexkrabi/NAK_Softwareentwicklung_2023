import java.io.*;
import java.util.*;

/**
 * Hier wird die Spiellogik, die Berechnung der Einflussgrößen und die 
 * Abbruchsbedingungen überprüft und kontrolliert.
 *
 * @author Sven Vazquez de Lara Kallas
 * @version 3.0
 */
public class Logik
{
    
    public int rundenzahl; //Rundenzahl wird hier gespeichert und als Referenz verwendet
    public int aktuelleRunde; //aktuelle Runde hier gespeichert
    public String spielername; //Spielername wird hier gespeichert
    public HashMap<Integer, Integer> simulationsErfolg; //speichert den Simulationserfolg Runde für Runde
    
    //Hashmaps mit Wertebeeinflussung und Berechnungsschritt im Kommentar
    private HashMap<Integer, Integer> mg_auf_uwv; // 5
    private HashMap<Integer, Integer> mg_auf_mg; // 3
    private HashMap<Integer, Integer> wl_auf_wl; // 1
    private HashMap<Integer, Integer> wl_auf_uwv; // 5
    private HashMap<Integer, Integer> uwv_auf_uwv; // 6
    private HashMap<Integer, Integer> uwv_auf_lq; // 7
    private HashMap<Integer, Integer> bl_auf_bl; // 4
    private HashMap<Integer, Integer> bl_auf_lq; // 7
    private HashMap<Integer, Integer> bl_auf_bw; // 9
    private HashMap<Integer, Integer> bg_auf_sv; // 14
    private HashMap<Integer, Integer> ps_auf_sv; // 14
    private HashMap<Integer, Integer> wl_auf_sv; // 14
    private HashMap<Integer, Integer> lq_auf_sv; // 14
    private HashMap<Integer, Integer> lq_auf_lq; // 8
    private HashMap<Integer, Integer> lq_auf_bw; // 9
    private HashMap<Integer, Integer> bg_auf_lq; // 7
    private HashMap<Integer, Integer> lq_auf_ps; // 13
    private HashMap<Integer, Integer> bw_auf_bg; // 11, 12
    public HashMap<Integer, Integer> bg_auf_bwf; // 10, 12
    public HashMap<Integer, Integer> wl_auf_vl; // 2
    
    /**
     * Konstruktor der Klasse Logik
     */
    public Logik()
    {
        this.aktuelleRunde = 1;
        simulationsErfolg = new HashMap<>();
        einflussWerteErzeugen();
    }
    
    /**
     * Diese Methode berechnet einen einzelnen Einflussschritt. Der neue Wert wird direkt im Ziel-Sektor eingesetzt.
     * Automatische Prüfung der Werte integriert. Gibt zurück, ob das Spiel weitergehen kann oder nicht.
     * 
     * @param einflussHash HashMap, aus welcher der Wert eingelesen werden soll.
     * @param sektorVON Sektor, von welchem die Änderung ausgeht (erste Stelle im Hash-Namen, bzw. Key)
     * @param sektorNACH Sektor, dessen Wert geändert werden soll (zweite Stelle im Hash-Namen, bzw. Wert)
     * @return TRUE: Continue, FALSE: Game Over
     */
    public boolean einflussRechner(HashMap<Integer, Integer> einflussHash, Sektor sektorVON, Sektor sektorNACH) 
    {
        System.out.println("Berechne Einfluss von " + sektorVON.getName() + " (" + sektorVON.getWert() + ") auf " 
                                + sektorNACH.getName() + " (" + sektorNACH.getWert() + ") ...");
        
        int delta = -999; //Zahl deutet auf Fehler hin, falls nicht verändert
        
        //Sonderfälle
        if (einflussHash == bw_auf_bg) {
            //Sonderfall 1 - Multiplizieren mit BWF
            System.out.println("Sonderfall erkannt: Verrechne mit BWF");
            delta =  Main.bevölkerungswachstumsfaktor.getWert() * einflussHash.get(sektorVON.getWert());//sucht passenden Wert in der Hashmap 
        } else if (einflussHash == bg_auf_sv) {
            //Sonderfall 2 - Multiplizieren mit VL
            System.out.println("Sonderfall erkannt: Verrechne mit VL");
            delta =  Main.versorgungslage.getWert() * einflussHash.get(sektorVON.getWert());//sucht passenden Wert in der Hashmap 
        } else if (einflussHash == bg_auf_bwf) {
            //Sonderfall - Gleichsetzen BWF
            System.out.println("Sonderfall erkannt: BWF direkt ersetzen");
            sektorNACH.setWert(einflussHash.get(sektorVON.getWert()));
            System.out.println("Erfolgreich!\nNeuer Wert von " + sektorNACH.getName() + ": " + sektorNACH.getWert());
            return true; //Keine Prüfung auf Game Over notwendig, da garantiert im Wertebereich
        } else if (einflussHash == wl_auf_vl) {
            //Sonderfall - Gleichsetzen VL
            System.out.println("Sonderfall erkannt: VL direkt ersetzen");
            sektorNACH.setWert(einflussHash.get(sektorVON.getWert()));
            System.out.println("Erfolgreich!\nNeuer Wert von " + sektorNACH.getName() + ": " + sektorNACH.getWert());
            return true; //Keine Prüfung auf Game Over notwendig, da garantiert im Wertebereich
        } else {
            
            //Normalfall
            delta = einflussHash.get(sektorVON.getWert());//sucht passenden Wert in der Hashmap 
        }
        System.out.println("Änderungswert: " + delta);
        
        int neuerWert = delta + sektorNACH.getWert(); //berechnet neuen Wert
        if (sektorNACH.prüfeObImWertebereich(neuerWert)){
            sektorNACH.setWert(neuerWert); //fügt neuen Wert ein
            System.out.println("Erfolgreich!\nNeuer Wert von " + sektorNACH.getName() + ": " + sektorNACH.getWert());
            return true; //Prüfung erfolgreich - keine falschen Werte erkannt
        } else {
            sektorNACH.setWert(neuerWert); //fügt neuen Wert ein, für bessere Nachvollziehbarkeit in der .res Datei
            gameOver(false);
            return false; //Falsche Werte erkannt --> Abbruchsbedingung einleiten
        }
    }
    
    /**
     * Führt alle Berechnungsschritte in der richtigen Reihenfolge durch. 
     * Nach jeder Berechnung wird der Wertebereich des betroffenen Sektors geprüft. 
     * Sobald ein Wert außerhalb vom Wertebereich liegt, bricht die Funktion an dieser Stelle ab --> Game Over Screen
     */
    public void rundeBerechnen (){
        System.out.println("Starte die Rundenberechnung ...");
        
        //Prüfe in jedem Schritt, ob Abbruchsbedingung eingeleitet wurde. (Werte außerhalb vom Wertebereich)
        //Falls Abbruchsbedingung zutreffend, wird die weitere Berechnung abgebrochen.
        //Im Normalfall wird die Berechnungskette fortgesetzt und das Spiel geht weiter.
        
        // 1.Wirtschaftsleistung (Rückkopplung)
        if(!einflussRechner(wl_auf_wl, Main.wirtschaftsleistung, Main.wirtschaftsleistung)) {return;} //Prüfe auf Abrruchsbedingung
        
        // 2. Versorgungslage
        if(!einflussRechner(wl_auf_vl, Main.wirtschaftsleistung, Main.versorgungslage)){return;}
            
        // 3. Modernisierungsgrad (Rückkopplung)
        if(!einflussRechner(mg_auf_mg,  Main.modernisierungsgrad, Main.modernisierungsgrad)){return;}
                
        // 4. Bildung (Rückkopplung)
        if(!einflussRechner(bl_auf_bl, Main.bildung, Main.bildung)){return;}
        
        // 5. Umweltverschmutzung
        if(!einflussRechner(wl_auf_uwv, Main.wirtschaftsleistung, Main.umweltverschmutzung)){return;}
        if(!einflussRechner(mg_auf_uwv, Main.modernisierungsgrad, Main.umweltverschmutzung)){return;}
        
        // 6. Umweltverschmutzung (Rückkopplung)
        if(!einflussRechner(uwv_auf_uwv, Main.umweltverschmutzung, Main.umweltverschmutzung)){return;}
        
        // 7. Lebensqualität
        if(!einflussRechner(bl_auf_lq, Main.bildung, Main.lebensqualität)){return;}
        if(!einflussRechner(uwv_auf_lq, Main.umweltverschmutzung, Main.lebensqualität)){return;}
        if(!einflussRechner(bg_auf_lq, Main.bevölkerungsgröße, Main.lebensqualität)){return;}
        
        // 8. Lebensqualität (Rückkopplung)
        if(!einflussRechner(lq_auf_lq, Main.lebensqualität, Main.lebensqualität)){return;}
        
        // 9. Bevölkerungswachstum
        if(!einflussRechner(bl_auf_bw, Main.bildung, Main.bevölkerungswachstum)){return;}
        if(!einflussRechner(lq_auf_bw, Main.lebensqualität, Main.bevölkerungswachstum)){return;}
        
        // 10. Bevölkerungswachstumsfaktor
        if(!einflussRechner(bg_auf_bwf, Main.bevölkerungsgröße, Main.bevölkerungswachstumsfaktor)){return;}
        
        // 11. Bevölkerungsgröße
        if(!einflussRechner(bw_auf_bg, Main.bevölkerungswachstum, Main.bevölkerungsgröße)){return;}
                
        // 12. Bevölkerungsgröße (Rückkopplung)
        if(!einflussRechner(bg_auf_bwf, Main.bevölkerungsgröße, Main.bevölkerungswachstumsfaktor)){return;}
        if(!einflussRechner(bw_auf_bg, Main.bevölkerungswachstum, Main.bevölkerungsgröße)){return;}
        
        // 13. Politische Stabilität
        if(!einflussRechner(lq_auf_ps, Main.lebensqualität, Main.politische_stabilität)){return;}
        
        // 14. Staatsvermögen
        if(!einflussRechner(lq_auf_sv, Main.lebensqualität, Main.staatsvermögen)){return;}
        if(!einflussRechner(wl_auf_sv, Main.wirtschaftsleistung, Main.staatsvermögen)){return;}
        if(!einflussRechner(ps_auf_sv, Main.politische_stabilität, Main.staatsvermögen)){return;}
        if(!einflussRechner(bg_auf_sv, Main.bevölkerungsgröße, Main.staatsvermögen)){return;}
    }
    
    /**
     * Berechnet den Simulationserfolg für die aktuelle Runde und gibt den errechneten Wert wieder.
     * Formel: sim_erfolg = 3 * lq + ps + sv
     * 
     * @return Berechneter Simulationserfolg als int
     */
    public int berechneSimulationserfolg() 
    {
        //Aktuelle relevante Sektor-Werte einlesen
        int lq = Main.lebensqualität.getWert();
        int ps = Main.politische_stabilität.getWert();
        int sv = Main.staatsvermögen.getWert();
        
        System.out.println("\nBerechne Simulationserfolg für Runde " + aktuelleRunde + " ...");
        
        int simulationserfolg = 3 * lq + ps + sv; //Formel für den Simulaitonserfolg
        
        System.out.println("Simulationserfolg: " + simulationserfolg + "\n");
        
        return simulationserfolg;
    }
    
    /**
     * Methode ruft alle Größen der gegebenen Runde auf und speichert sie in den vorhergesehenen HashMaps.
     * Ruft mehrere Methoden gebündelt auf.
     * 
     * @param runde Runde, für welche die Werte gespeichert werden sollen. [im Normalfall: aktuelleRunde]
     */
    public void speichernRundenwerte() 
    {
        //Speichern vom Simulationserfolg
        simulationsErfolg.put(aktuelleRunde, berechneSimulationserfolg());
        System.out.println("Simulationserfolg gespeichert");
        
        //Speichern von allen einzelnen Werten
        Main.bevölkerungsgröße.aktuellenWertSpeichern(aktuelleRunde);
        Main.bevölkerungswachstum.aktuellenWertSpeichern(aktuelleRunde);
        Main.wirtschaftsleistung.aktuellenWertSpeichern(aktuelleRunde);
        Main.modernisierungsgrad.aktuellenWertSpeichern(aktuelleRunde);
        Main.politische_stabilität.aktuellenWertSpeichern(aktuelleRunde);
        Main.umweltverschmutzung.aktuellenWertSpeichern(aktuelleRunde);
        Main.lebensqualität.aktuellenWertSpeichern(aktuelleRunde);
        Main.bildung.aktuellenWertSpeichern(aktuelleRunde);
        Main.staatsvermögen.aktuellenWertSpeichern(aktuelleRunde);
        Main.bevölkerungswachstumsfaktor.aktuellenWertSpeichern(aktuelleRunde);
        Main.versorgungslage.aktuellenWertSpeichern(aktuelleRunde);
        System.out.println("Sektorenwerte gespeichert");
    }
    
    /**
     * Das Aufrufen dieser Funktion beendet das Spiel. 
     * Es soll übergeben werden, ob das Spiel gewonnen wurde oder nicht.
     * 
     * @param gewonnen TRUE: Spiel gewonnen, FALSE: Spiel verloren
     */
    public void gameOver(boolean gewonnen)
    {
        //"Game Over" - Bedingungen
        // -> Außerhalb vom Wertebereich    LOSS
        // -> Runden zu Ende                WIN
        
        System.out.println("\n------ GAME OVER ------\n");
        if(gewonnen) {
            Main.gui.setSpielstand("VICTORY");
        } else {
            Main.gui.setSpielstand("GAMEOVER");
        }
        Main.gui.spielstandänderung();
    }
    
    /**
     * Erzeugt eine HashMap aus dem Imput eines Arrays.
     * Wird für die Wertebeziehungen benutzt.
     * 
     * @param startKey Kleinster Wert, der referenziert wird. Ab diesem Wert wird hochgezählt
     * @param werteArray Zu implementierenden Werte in Array-Form
     */
    public static HashMap<Integer, Integer> erzeugeHashMapAusInputArray(int startKey, int[] werteArray) {
        HashMap<Integer, Integer> hashmap = new HashMap<>();
        int key = startKey;
        
        for (int i = 0; i < werteArray.length; i++) {
            hashmap.put(key, werteArray[i]);
            key++;
        }
        return hashmap;
    }
    
    /**
     * Methode erzeugt alle vordefinierten Einflusswerte aus der Excel-Tabelle (Kein Dateizugriff, nur im Code veränderbar).
     * Daraufhin werden die Beziehungs-HashMaps erzeugt und befüllt.
     */
    private void einflussWerteErzeugen() 
    {
        //Werte aus Excel-Tabelle als int[] Arrays gespeichert
        int[] spalteB = {0, 0, -1, -1, -1, -1, -1, -2, -2, -2, -2, -2, -3, -3, -3, -3, -3, -4, -4, -4, -5, -5, -6, -6, -7, -7, -8, -8, -9, -10};
        int[] spalteC = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -2, -2, -3, -4, -5, -6, -6, -6};
        int[] spalteD = {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 0, -3, -6, -10};
        int[] spalteE = {0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 9, 10, 12, 14, 17, 20, 23};
        int[] spalteF = {0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -2, -2, -2, -2, -2, -2, -3, -3, -3, -3, -4, -3, -2, -1, 0, 0, 0};
        int[] spalteG = {2, 1, 0, 0, 0, 0, 0, 0, -1, -1, -2, -2, -2, -2, -3, -3, -3, -4, -4, -5, -5, -6, -7, -8, -10, -12, -14, -17, -20, -23};
        int[] spalteH = {0, 0, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 0, 0};
        int[] spalteI = {-2, -2, -2, -2, -2, -1, -1, -1, -1, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 6, 6};
        int[] spalteJ = {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 1, 1};
        int[] spalteK = {0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 0, 0, -1, -1, -1, -1, -1, -2, -2, -2, -1, -1, -1, 0, 0};
        int[] spalteL = {-15, -8, -6, -4, -3, -2, -1, 0, 1, 2, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] spalteM = {-10, -8, -6, -3, -2, -1, -1, -1, -1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 5, 5};
        int[] spalteN = {-4, -4, -3, -3, -3, -2, -2, -2, -2, -1, -1, -1, -1, -1, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3};
        int[] spalteO = {-5, -2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -2, -2, -2, -2, -2, -3, -3, -3, -3, -3, -3, -3, -3, -4, -4, -4, -4, -5, -5, -5, -6, -6, -7, -8, -10};
        int[] spalteP = {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8, 9, 9, 9, 9, 9};
        int[] spalteQ = {-5, -2, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
        int[] spalteR = {-4, -3, -2, -1, 0, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 8, 8, 9, 10, 11, 0, -2, -5};
        int[] spalteS = {-6, -4, -2, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5};
        int[] spalteU = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
        int[] spalteV = {-4, -4, -4, -3, -3, -3, -2, -2, -2, -1, -1, -1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

        //Erzeuge alle Einfluss-HashMaps
        mg_auf_uwv = erzeugeHashMapAusInputArray(1, spalteB); //erster Wert bei 1
        mg_auf_mg = erzeugeHashMapAusInputArray(1, spalteC);
        wl_auf_wl = erzeugeHashMapAusInputArray(1, spalteD);
        wl_auf_uwv = erzeugeHashMapAusInputArray(1, spalteE);
        uwv_auf_uwv = erzeugeHashMapAusInputArray(1, spalteF);
        uwv_auf_lq = erzeugeHashMapAusInputArray(1, spalteG);
        bl_auf_bl = erzeugeHashMapAusInputArray(1, spalteH);
        bl_auf_lq = erzeugeHashMapAusInputArray(1, spalteI);
        bl_auf_bw = erzeugeHashMapAusInputArray(1, spalteJ);
        lq_auf_lq = erzeugeHashMapAusInputArray(1, spalteK);
        lq_auf_bw = erzeugeHashMapAusInputArray(1, spalteL);
        lq_auf_ps = erzeugeHashMapAusInputArray(1, spalteM);
        bw_auf_bg = erzeugeHashMapAusInputArray(1, spalteN);
        bg_auf_lq = erzeugeHashMapAusInputArray(1, spalteO);
        bg_auf_sv = erzeugeHashMapAusInputArray(1, spalteP);
        ps_auf_sv = erzeugeHashMapAusInputArray(-10, spalteQ); //erster Wert bei -10
        wl_auf_sv = erzeugeHashMapAusInputArray(1, spalteR);
        lq_auf_sv = erzeugeHashMapAusInputArray(1, spalteS);
        bg_auf_bwf = erzeugeHashMapAusInputArray(1, spalteU);
        wl_auf_vl = erzeugeHashMapAusInputArray(1, spalteV);
        
        System.out.println("Alle Einfluss-Hashmaps erfolgreich erzeugt.");

    }
}

