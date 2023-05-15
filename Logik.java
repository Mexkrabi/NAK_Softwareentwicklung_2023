import java.io.*;
import java.util.*;

/**
 * Hier wird die Spiellogik, die Berechnung der Einflussgrößen und die 
 * Abbruchsbedingungen überprüft und kontrolliert.
 *
 * @author Sven Vazquez de Lara Kallas
 * @version (0.1)
 */
public class Logik
{
    public ArrayList<Sektor> alleSektoren; //hier werden alle Sektoren gespeichert
    
    public HashMap<String, Integer> startwerteHash; //HashMap mit allen Anfangswerten aus der .sim Datei
    public int rundenzahl; //Rundenzahl wird hier gespeichert
    
    //Hashmaps mit Wertebeeinflussung
    private HashMap<Integer, Integer> mg_auf_uwv;
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
    
    public void einflussWerteErzeugen() 
    {
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
        String[] spalteN = {"-4 x BWF", "-4 x BWF", "-3 x BWF", "-3 x BWF", "-3 x BWF", "-2 x BWF", "-2 x BWF", "-2 x BWF", "-2 x BWF", "-1 x BWF", "-1 x BWF", "-1 x BWF", "-1 x BWF", "-1 x BWF", "0 x BWF", "1 x BWF", "1 x BWF", "1 x BWF", "1 x BWF", "1 x BWF", "2 x BWF", "2 x BWF", "2 x BWF", "2 x BWF", "2 x BWF", "3 x BWF", "3 x BWF", "3 x BWF", "3 x BWF", "3 x BWF"};
        int[] SpalteO = {-5, -2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -2, -2, -2, -2, -2, -3, -3, -3, -3, -3, -3, -3, -3, -4, -4, -4, -4, -5, -5, -5, -6, -6, -7, -8, -10};
        String[] SpalteP = {"0 x VL", "0 x VL", "0 x VL", "0 x VL", "0 x VL", "0 x VL", "0 x VL", "0 x VL", "0 x VL", "1 x VL", "1 x VL", "1 x VL", "1 x VL", "1 x VL", "1 x VL", "1 x VL", "2 x VL", "2 x VL", "2 x VL", "2 x VL", "2 x VL", "3 x VL", "3 x VL", "3 x VL", "3 x VL", "4 x VL", "4 x VL", "4 x VL", "4 x VL", "5 x VL", "5 x VL", "5 x VL", "5 x VL", "6 x VL", "6 x VL", "6 x VL", "6 x VL", "7 x VL", "7 x VL", "7 x VL", "7 x VL", "8 x VL", "8 x VL", "8 x VL", "8 x VL", "9 x VL", "9 x VL", "9 x VL", "9 x VL", "9 x VL"};
        int[] spalteQ = {-5, -2, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
        int[] spalteR = {-4, -3, -2, -1, 0, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 8, 8, 9, 10, 11, 0, -2, -5};
        int[] spalteS = {-6, -4, -2, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5};
        int[] spalteU = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
        int[] spalteV = {-4, -4, -4, -3, -3, -3, -2, -2, -2, -1, -1, -1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

        
        mg_auf_uwv = erzeugeHashMapAusInputArray(1, new int[] {0, 0, -1, -1, -1, -1, -1, -2, -2, -2, -2, -2, -3, -3, -3, -3, -3, -4, -4, -4, -5, -5, -6, -6, -7, -7, -8, -8, -9, -10});
        /*
        mg_auf_uwv = new HashMap<>();

        mg_auf_uwv.put(1, 0);
        mg_auf_uwv.put(2, 0);
        mg_auf_uwv.put(3, -1);
        mg_auf_uwv.put(4, -1);
        mg_auf_uwv.put(5, -1);
        mg_auf_uwv.put(6, -1);
        mg_auf_uwv.put(7, -1);
        mg_auf_uwv.put(8, -2);
        mg_auf_uwv.put(9, -2);
        mg_auf_uwv.put(10, -2);
        mg_auf_uwv.put(11, -2);
        mg_auf_uwv.put(12, -2);
        mg_auf_uwv.put(13, -3);
        mg_auf_uwv.put(14, -3);
        mg_auf_uwv.put(15, -3);
        mg_auf_uwv.put(16, -3);
        mg_auf_uwv.put(17, -3);
        mg_auf_uwv.put(18, -4);
        mg_auf_uwv.put(19, -4);
        mg_auf_uwv.put(20, -4);
        mg_auf_uwv.put(21, -5);
        mg_auf_uwv.put(22, -5);
        mg_auf_uwv.put(23, -6);
        mg_auf_uwv.put(24, -6);
        mg_auf_uwv.put(25, -7);
        mg_auf_uwv.put(26, -7);
        mg_auf_uwv.put(27, -8);
        mg_auf_uwv.put(28, -8);
        mg_auf_uwv.put(29, -9);
        mg_auf_uwv.put(30, -10);
        */
        
       // Testausgabe der HashMap
        for (int key = 1; key <= 30; key++) {
            System.out.println("Key: " + key + ", Wert: " + mg_auf_uwv.get(key));
        }
    }
}
