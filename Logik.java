import java.io.*;
import java.util.*;

/**
 * Hier wird die Spiellogik, die Berechnung der Einflussgrößen und die 
 * Abbruchsbedingungen überprüft und kontrolliert.
 *
 * @author Sven Vazquez de Lara Kallas
 * @version 0.1
 */
public class Logik
{
    //public ArrayList<Sektor> alleSektoren; //hier werden alle Sektoren gespeichert (nicht in Verwendung)
    public HashMap<String, Integer> startwerteHash; //HashMap mit allen Anfangswerten aus der .sim Datei
    
    public int rundenzahl; //Rundenzahl wird hier gespeichert und als Referenz verwendet
    public int aktuelleRunde; //aktuelle Runde hier gespeichert
    
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
    private HashMap<Integer, Integer> bg_auf_bwf; // 10, 12
    private HashMap<Integer, Integer> wl_auf_vl; // 2
    private HashMap<Integer, Integer> lq_auf_lq; // 8
    private HashMap<Integer, Integer> lq_auf_bw; // 9
    private HashMap<Integer, Integer> bg_auf_lq; // 7
    private HashMap<Integer, Integer> lq_auf_ps; // 13
    private HashMap<Integer, Integer> bw_auf_bg; // 11, 12
    
    /**
     * Konstruktor der Klasse Logik
     */
    public Logik()
    {
        hashErzeuger();
        einflussWerteErzeugen();
    }
    
    public void hashErzeuger()
    {
        startwerteHash = new HashMap<String, Integer>();
    }

    /**
     * Diese Methode berechnet einen einzelnen Einflussschritt.
     * 
     * @param einflussHash HashMap, aus welcher der Wert eingelesen werden soll.
     * @param sektorVON Sektor, von welchem die Änderung ausgeht (erste Stelle im Hash-Namen, bzw. Key)
     * @param sektorNACH Sektor, dessen Wert geändert werden soll (zweite Stelle im Hash-Namen, bzw. Wert)
     */
    public void einflussRechner(HashMap<Integer, Integer> einflussHash, Sektor sektorVON, Sektor sektorNACH) 
    {
        System.out.println("Berechne Einfluss von " + sektorVON.getName() + " (" + sektorVON.getWert() + ") auf " 
                                + sektorNACH.getName() + " (" + sektorNACH.getWert() + ") ...");
        int delta = -999; //Zahl ohne Bedeutung
        //Sonderfälle mit Multiplikation
        if (einflussHash == bw_auf_bg) {
            //Sonderfall 1
            System.out.println("Sonderfall erkannt: Verrechne mit BWF");
            delta =  Main.bevölkerungswachstumsfaktor.getWert() * einflussHash.get(sektorVON.getWert());//sucht passenden Wert in der Hashmap 
        } else if (einflussHash == bg_auf_sv) {
            //Sonderfall 2
            System.out.println("Sonderfall erkannt: Verrechne mit VL");
            delta =  Main.versorgungslage.getWert() * einflussHash.get(sektorVON.getWert());//sucht passenden Wert in der Hashmap 
        } else if (einflussHash == bg_auf_bwf) {
            //Sonderfall - Gleichsetzen
            System.out.println("Sonderfall erkannt: BWF direkt ersetzen");
            sektorNACH.setWert(einflussHash.get(sektorVON.getWert()));
            System.out.println("Erfolgreich!\nNeuer Wert von " + sektorNACH.getName() + ": " + sektorNACH.getWert());
            return;
        } else if (einflussHash == wl_auf_vl) {
            //Sonderfall - Gleichsetzen
            System.out.println("Sonderfall erkannt: VL direkt ersetzen");
            sektorNACH.setWert(einflussHash.get(sektorVON.getWert()));
            System.out.println("Erfolgreich!\nNeuer Wert von " + sektorNACH.getName() + ": " + sektorNACH.getWert());
            return;
        } else {
            //Normalfall
            delta = einflussHash.get(sektorVON.getWert());//sucht passenden Wert in der Hashmap 
        }
        System.out.println("Änderungswert: " + delta);
        int neuerWert = delta + sektorNACH.getWert(); //berechnet neuen Wert
        if (sektorNACH.prüfeObImWertebereich(neuerWert)){
            sektorNACH.setWert(neuerWert); //fügt neuen Wert ein
            System.out.println("Erfolgreich!\nNeuer Wert von " + sektorNACH.getName() + ": " + sektorNACH.getWert());
        } else {
            gameOver(false);
            return; // bricht methode ab, da nicht weiter rechnen
        }
    }
    
    public void rundeBerechnen (){
        System.out.println("Starte die Rundenberechnung ...");
        // 1.Wirtschaftsleistung (Rückkopplung)
        einflussRechner(wl_auf_wl, Main.wirtschaftsleistung, Main.wirtschaftsleistung);
        
        // 2. Versorgungslage
        einflussRechner(wl_auf_vl, Main.wirtschaftsleistung, Main.versorgungslage);
        
        // 3. Modernisierungsgrad (Rückkopplung)
        einflussRechner(mg_auf_mg, Main.modernisierungsgrad, Main.modernisierungsgrad);
        
        // 4. Bildung (Rückkopplung)
        einflussRechner(bl_auf_bl, Main.bildung, Main.bildung);
        
        // 5. Umweltverschmutzung
        einflussRechner(wl_auf_uwv, Main.wirtschaftsleistung, Main.umweltverschmutzung);
        einflussRechner(mg_auf_uwv, Main.modernisierungsgrad, Main.umweltverschmutzung);
        
        // 6. Umweltverschmutzung (Rückkopplung)
        einflussRechner(uwv_auf_uwv, Main.umweltverschmutzung, Main.umweltverschmutzung);
        
        // 7. Lebensqualität
        einflussRechner(bl_auf_lq, Main.bildung, Main.lebensqualität);
        einflussRechner(uwv_auf_lq, Main.umweltverschmutzung, Main.lebensqualität);
        einflussRechner(bg_auf_lq, Main.bevölkerungsgröße, Main.lebensqualität);
        
        // 8. Lebensqualität (Rückkopplung)
        einflussRechner(lq_auf_lq, Main.lebensqualität, Main.lebensqualität);
        
        // 9. Bevölkerungswachstum
        einflussRechner(bl_auf_bw, Main.bildung, Main.bevölkerungswachstum);        
        einflussRechner(lq_auf_bw, Main.lebensqualität, Main.bevölkerungswachstum);        
        
        // 10. Bevölkerungswachstumsfaktor
        einflussRechner(bg_auf_bwf, Main.bevölkerungsgröße, Main.bevölkerungswachstumsfaktor);
        
        // 11. Bevölkerungsgröße
        einflussRechner(bw_auf_bg, Main.bevölkerungswachstum, Main.bevölkerungsgröße);
                
        // 12. Bevölkerungsgröße (Rückkopplung)
        einflussRechner(bg_auf_bwf, Main.bevölkerungsgröße, Main.bevölkerungswachstumsfaktor);
        einflussRechner(bw_auf_bg, Main.bevölkerungswachstum, Main.bevölkerungsgröße);
        
        // 13. Politische Stabilität
        einflussRechner(lq_auf_ps, Main.lebensqualität, Main.politische_stabilität);
        
        // 14. Staatsvermögen
        einflussRechner(lq_auf_sv, Main.lebensqualität, Main.staatsvermögen);
        einflussRechner(wl_auf_sv, Main.wirtschaftsleistung, Main.staatsvermögen);
        einflussRechner(ps_auf_sv, Main.politische_stabilität, Main.staatsvermögen);
        einflussRechner(bg_auf_sv, Main.bevölkerungsgröße, Main.staatsvermögen);        
        
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
        // -> Runden zu Ende                WIN/LOSS
        
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
        
        System.out.println("HashMap erzeugt: " + hashmap);
        return hashmap;
    }
    
    public void einflussWerteErzeugen() 
    {
        //Werte aus Excel-Tabelle als int[] Arrays gespeichert
        int[] spalteB = {0, 0, -1, -1, -1, -1, -1, -2, -2, -2, -2, -2, -3, -3, -3, -3, -3, -4, -4, -4, -5, -5, -6, -6, -7, -7, -8, -8, -9, -10};
        int[] spalteC = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -2, -2, -3, -4, -5, -6, -6, -6};
        int[] spalteD = {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 0, -3, -6, -10};
        int[] spalteE = {0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 9, 10, 12, 14, 17, 20, 23};
        int[] spalteF = {0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -2, -2, -2, -2, -2, -2, -3, -3, -3, -3, -4, -3, -2, -1, 0, 0, 0};
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
        mg_auf_uwv = erzeugeHashMapAusInputArray(1, spalteB);
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
        ps_auf_sv = erzeugeHashMapAusInputArray(-10, spalteQ);
        wl_auf_sv = erzeugeHashMapAusInputArray(1, spalteR);
        lq_auf_sv = erzeugeHashMapAusInputArray(1, spalteS);
        bg_auf_bwf = erzeugeHashMapAusInputArray(1, spalteU);
        wl_auf_vl = erzeugeHashMapAusInputArray(1, spalteV);
        
        System.out.println("Alle Einfluss-Hashmaps erfolgreich erzeugt.");

    }
}

