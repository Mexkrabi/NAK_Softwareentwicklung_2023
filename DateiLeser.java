import java.io.*;
import java.util.*;
import java.nio.file.*;

/**
 * Diese Klasse enthält den Code für das Einlesen der externen .sim Dateien.
 * Die Dateien beinhalten Anfangswerte für das Spiel.
 * 
 * @author Livia Kadenbach
 * @version 0.3
 */
public class DateiLeser 
{   
    private String dieserOrdner; //Ordnerpfad mit .sim Dateien
    
    /**
     * Konstruktor für DateiLeser-Klasse. Speichert sofort den Dateipfad des sim-Dateien Ordners.
     */
    public DateiLeser()
    {
        dieserOrdner = System.getProperty("user.dir") + "/sim-Dateien"; //aktueller Dateipfad + Ordner mit sim-Dateien
    }
    
    /**
     * Methode erlaubt die Wahl der .sim Datei per Konsoleneingabe
     * 
     * @return Gibt den ausgewählten Dateipfad als String zurück
     */
    public String simDateiAuswahl()
    {
        //#String dieserOrdner = System.getProperty("user.dir") + "/sim-Dateien"; //aktueller Dateipfad + Ordner mit sim-Dateien
        
        ////////Hier mit Konsoleneingabe://////////////
        //System.out.println("Wie heißt die Datei welche Sie auswählen wollen? (ohne Endung)");
        //Scanner sc = new Scanner(System.in); //Konsoleneingabeleser
        //String input = "/" + sc.next() + ".sim"; //Eingabe abspeichern
        //////////////////////////////////////////////

        //#Main.warteSolangeNoch("AUSWAHL"); //warten, bis der Ordner gefunden wird 
        
        String dateiname = Main.gui.strAuswahl; //Dateiname der ausgewählten Datei abspeichern 
        //#Main.gui.strAuswahl = (String) Main.gui.cbDateien.getSelectedItem(); 
        
        System.out.println("Pfad des aktuellen Projektordners: " + dieserOrdner + "/" + dateiname + ".sim"); //zusammenfügen
        String simFilePath = dieserOrdner +  "/" + dateiname  + ".sim";
        
        allesAuslesen(simFilePath);

        return simFilePath; //gibt den ausgewählten Dateipfad zurück (gespeichert in Logik() )
    }
    
    /**
     * Liest die komplette Datei aus und gibt sie in der Konsole wieder
     * 
     * Quelle zur Dokumentation: https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/io/package-summary.html#files
     * 
     * @param pfad Pfad der Datei angeben
     */
    public void allesAuslesen(String pfad) 
    {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pfad), "UTF-8"))) //UTF-8 lässt Umlaute erkennen
        {
            String line;
            while ((line = br.readLine()) != null) 
            {
                System.out.println(line.substring(0));
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Methode erlaubt gezieltes Einlesen einer gegebenen Datei, mit beliebigem Suchkriterium.
     * Der unmittelbare Wert nach dem Suchwort wird dann ausgelesen und zurückgegeben.
     * 
     * Die Methode fügt dem Suchwort automatisch ein " = " hinzu.
     * Ist dies nicht gewünscht, kann die Methode nochmal im Code angepasst werden.
     * 
     * Quelle zur Dokumentation: https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/io/package-summary.html#files
     * 
     * @param pfad Pfad der Datei angeben
     * @param suchwort Suchwort aus der Datei eingeben. Der unmittelbare Wert danach wird dann ausgelesen und zurückgegeben.
     * @return Alle Zeichen in der Zeile nach dem gegebenen Suchwort wird wiedergegeben.
     */
    public String auslesen(String pfad, String suchwort) 
    {
        System.out.println("Suchwort, welches zum auslesen eingegeben wurde: " + suchwort);
        suchwort = suchwort + " = "; //Erweitert mit " = "
        System.out.println("Länge von '" + suchwort + "' ist: " + suchwort.length()); //suchwort.lenght() gibt String-Länge
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pfad), "UTF-8"))) //UTF-8 lässt angeblich Umlaute erkennen
        {
            String line;
            while ((line = br.readLine()) != null) 
            {
                String ausgelesenerWert;
                // Suche nach dem Suchwort, start
                if (line.startsWith(suchwort)) 
                {
                    System.out.println("Eingabezeile: " + line.substring(0));
                    ausgelesenerWert = line.substring(suchwort.length());
                    System.out.println("Ausgelesener Wert: " + ausgelesenerWert);
                    return ausgelesenerWert;
                }
                // Suche nach dem Suchwort, ende
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        return "Wert konnte nicht gefunden werden.";
    }

    
    /**
     * Die Methode erstellt ein HashSet mit den Namen der SIM Datein im Projektordner
     * Sie prüft, dass es sich um eine Datei handelt und diese eine .sim Endung hat
     * 
     * Quelle zur Dokumentation: https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html
     * 
     * @Return: HashSet mit SIM Dateinamen ohne Endung
     */
    public Set<String> dateienFinden(){
        Set<String> dateien = new HashSet<String>();
        
        for (File file : new File(dieserOrdner).listFiles()){
            if (!file.isDirectory() && file.getName().endsWith(".sim")){ //prüft dass es kein ordner ist und der dateityp eine .sim Datei ist
                dateien.add(file.getName().substring(0,file.getName().length()-4));//packt den Dateinamen ohne die Endung .sim in die Menge
            }
        }
        return dateien;
    }
    
    //Getter & Setter
    public String getOrdner() 
    {
        return this.dieserOrdner;
    }
}
