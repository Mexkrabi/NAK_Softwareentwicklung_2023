import java.io.*;
import java.util.*;

/**
 * Hier wird die Spiellogik, die Berechnung der Einflussgrößen und die 
 * Abbruchsbedingungen überprüft und kontrolliert.
 *
 * @author Sven Vazquez de Lara Kallas, Livia Kadenbach
 * @version (0.1)
 */
public class Logik
{
    public ArrayList<Sektor> alleSektoren; //hier werden alle Sektoren gespeichert
    
    public HashMap<String, Integer> startwerteHash; //HashMap mit allen Anfangswerten aus der .sim Datei
    
    public int rundenzahl; //Rundenzahl wird hier gespeichert und als Referenz verwendet
    public int aktuelleRunde; //aktuelle Runde hier gespeichert
    
    //Hashmaps mit Wertebeeinflussung
    private HashMap<Integer, Integer> mg_auf_uwv;
    private HashMap<Integer, Integer> mg_auf_mg;
    private HashMap<Integer, Integer> wl_auf_wl;
    private HashMap<Integer, Integer> wl_auf_uwv;
    private HashMap<Integer, Integer> uwv_auf_uwv;
    private HashMap<Integer, Integer> uwv_auf_lq;
    private HashMap<Integer, Integer> bl_auf_bl;
    private HashMap<Integer, Integer> bl_auf_lq;
    private HashMap<Integer, Integer> bl_auf_bw;
    private HashMap<Integer, Integer> bg_auf_sv;
    private HashMap<Integer, Integer> ps_auf_sv;
    private HashMap<Integer, Integer> wl_auf_sv;
    private HashMap<Integer, Integer> lq_auf_sv;
    private HashMap<Integer, Integer> bg_auf_bwf;
    private HashMap<Integer, Integer> wl_auf_vl;
    private HashMap<Integer, Integer> lq_auf_lq;
    private HashMap<Integer, Integer> lq_auf_bw;
    private HashMap<Integer, Integer> bg_auf_lq;
    private HashMap<Integer, Integer> lq_auf_ps;
    private HashMap<Integer, Integer> bw_auf_bg;
    
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

    public void rundeBerechnen (){
        // 1.Wirtschaftsleistung (Rückkopplung)
        
        int anderung = wl_auf_wl.get(Main.wirtschaftsleistung.getWert());//passenden Wert in den der Hashmap suchen
        int neuerWert = anderung + Main.wirtschaftsleistung.getWert();
        if (Main.wirtschaftsleistung.prüfeObImWertebereich(neuerWert)){
            Main.wirtschaftsleistung.setWert(neuerWert);
        }
        else {
            gameOver();
            return; // bricht methode ab, da nicht weiter rechnen
        }
        
        // 2. Versorgungslage
        Main.versorgungslage.setWert(wl_auf_vl.get(Main.wirtschaftsleistung.getWert())); // da  Ergeniss nicht berechnet wird, kann der Wert nicht außerhalb des Wertebereichs sein
                
        // 3. Modernisierungsgrad (Rückkopplung)
        anderung = mg_auf_mg.get(Main.modernisierungsgrad.getWert());//passenden Wert in den der Hashmap suchen
        neuerWert = anderung + Main.modernisierungsgrad.getWert();
        if (Main.modernisierungsgrad.prüfeObImWertebereich(neuerWert)){
            Main.modernisierungsgrad.setWert(neuerWert);
        }
        else {
            gameOver();
            return; // bricht methode ab, da nicht weiter rechnen
        }
        
        // 4. Bildung (Rückkopplung)
        anderung = bl_auf_bl.get(Main.bildung.getWert());//passenden Wert in den der Hashmap suchen
        neuerWert = anderung + Main.bildung.getWert();
        if (Main.bildung.prüfeObImWertebereich(neuerWert)){
            Main.bildung.setWert(neuerWert);
        }
        else {
            gameOver();
            return; // bricht methode ab, da nicht weiter rechnen
        }
        // 5. Umweltverschmutzung
        anderung = mg_auf_uwv.get(Main.modernisierungsgrad.getWert()) + wl_auf_uwv.get(Main.wirtschaftsleistung.getWert());//passende Werte in den der Hashmaps suchen und addieren
        neuerWert = anderung + Main.umweltverschmutzung.getWert();
        if (Main.umweltverschmutzung.prüfeObImWertebereich(neuerWert)){
            Main.umweltverschmutzung.setWert(neuerWert);
        }
        else {
            gameOver();
            return; // bricht methode ab, da nicht weiter rechnen
        }
        
        // 6. Umweltverschmutzung (Rückkopplung)
        anderung = uwv_auf_uwv.get(Main.umweltverschmutzung.getWert());//passenden Wert in den der Hashmap suchen
        neuerWert = anderung + Main.umweltverschmutzung.getWert();
        if (Main.umweltverschmutzung.prüfeObImWertebereich(neuerWert)){
            Main.umweltverschmutzung.setWert(neuerWert);
        }
        else {
            gameOver();
            return; // bricht methode ab, da nicht weiter rechnen
        }
        // 7. Lebensqualität
        anderung = uwv_auf_lq.get(Main.umweltverschmutzung.getWert()) + bl_auf_lq.get(Main.bildung.getWert()) + bg_auf_lq.get(Main.bevölkerungsgröße.getWert());//passenden Wert in den der Hashmap suchen
        neuerWert = anderung + Main.lebensqualität.getWert();
        if (Main.lebensqualität.prüfeObImWertebereich(neuerWert)){
            Main.lebensqualität.setWert(neuerWert);
        }
        else {
            gameOver();
            return; // bricht methode ab, da nicht weiter rechnen
        }
        // 8. Lebensqualität (Rückkopplung)
        anderung = lq_auf_lq.get(Main.lebensqualität.getWert());//passenden Wert in den der Hashmap suchen
        neuerWert = anderung + Main.lebensqualität.getWert();
        if (Main.lebensqualität.prüfeObImWertebereich(neuerWert)){
            Main.lebensqualität.setWert(neuerWert);
        }
        else {
            gameOver();
            return; // bricht methode ab, da nicht weiter rechnen
        }
        // 9. Bevölkerungswachstum
        anderung = bl_auf_bw.get(Main.bildung.getWert()) + lq_auf_bw.get(Main.lebensqualität.getWert());//passenden Wert in den der Hashmap suchen
        neuerWert = anderung + Main.bevölkerungswachstum.getWert();
        if (Main.bevölkerungswachstum.prüfeObImWertebereich(neuerWert)){
            Main.bevölkerungswachstum.setWert(neuerWert);
        }
        else {
            gameOver();
            return; // bricht methode ab, da nicht weiter rechnen
        }
        
        // 10. Bevölkerungswachstumsfakto�
        Main.bevölkerungswachstumsfaktor.setWert(bg_auf_bwf.get(Main.bevölkerungsgröße.getWert())); // da  Ergeniss nicht berechnet wird, kann der Wert nicht außerhalb des Wertebereichs sein
        
        // 11. Bevölkerungsgröße
        anderung = bw_auf_bg.get(Main.bevölkerungswachstum.getWert()) * Main.bevölkerungswachstumsfaktor.getWert();//passenden Wert in den der Hashmap suchen
        neuerWert = anderung + Main.bevölkerungsgröße.getWert();
        if (Main.bevölkerungsgröße.prüfeObImWertebereich(neuerWert)){
            Main.bevölkerungsgröße.setWert(neuerWert);
        }
        else {
            gameOver();
            return; // bricht methode ab, da nicht weiter rechnen
        }
        
        // 12. Bevölkerungsgröße (Rückkopplung)
        
        // 13. Politische Stabilität
        anderung = lq_auf_ps.get(Main.lebensqualität.getWert());//passenden Wert in den der Hashmap suchen
        neuerWert = anderung + Main.politische_stabilität.getWert();
        if (Main.politische_stabilität.prüfeObImWertebereich(neuerWert)){
            Main.politische_stabilität.setWert(neuerWert);
        }
        else {
            gameOver();
            return; // bricht methode ab, da nicht weiter rechnen
        }
        // 14. Staatsvermögen
        anderung = (bg_auf_sv.get(Main.bevölkerungsgröße.getWert()) *  Main.versorgungslage.getWert()) + wl_auf_sv.get(Main.wirtschaftsleistung.getWert()) + 
            + ps_auf_sv.get(Main.politische_stabilität.getWert()) + lq_auf_sv.get(Main.lebensqualität.getWert());//passendee Wert in den der Hashmap suchen und Berechnungen machen
        neuerWert = anderung + Main.staatsvermögen.getWert();
        if (Main.staatsvermögen.prüfeObImWertebereich(neuerWert)){
            Main.staatsvermögen.setWert(neuerWert);
        }
        else {
            gameOver();
            return; // bricht methode ab, da nicht weiter rechnen
        }
    }
    public void gameOver(){
        
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

