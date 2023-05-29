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

        return simFilePath; //gibt den ausgewählten Dateipfad zurück (gespeichert in Logik() )
    }

    /**
     * Liest eine Datei ein und packt alle Sektoren, die im Format "sektorname = sektorwert" angegeben sind in eine Hashmap.
     * 
     * @param pfad Pfad der Datei angeben
     * @return hashmap <Sektorname als String, Sektorwert als Int> mit allen in der Datei gefundenen Startwerten 
     */
    public HashMap<String,Integer> dateiAuslesen(String pfad) 
    {
        HashMap<String,Integer> startwerteMap = new HashMap<String,Integer>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pfad), "UTF-8"))) //liest im UTF-8 Format
        {
            String line;

            while ((line = br.readLine()) != null) 
            {
                if(line.contains("�")){ //Datei ist Wahrscheinlich nicht im UTF-8 Format
                    System.out.println("Datei ist Wahrscheinlich nicht im UTF-8 Format. Versuche das Einlesen im ANSI Format.");
                    return dateiAuslesenANSI(pfad); //Bricht Methode mit dem Ansi Ergebniss ab
                }
                
                if(line.contains("=")){
                    line = line.replaceAll("\\s",""); //alle Leerzeichen entfernen
                    line = line.toLowerCase(); //setzt den String auf kleinschreibung um Groß/Kleinschreibungsfehler zu verhindern.
                    String[] sektor = line.split("="); //teilt die Zeile an allen "=" ("=" sind dann nicht mehr enthalten)
                    if(startwerteMap.get(sektor[0]) != null) { //Falls ein Sektor mit diesem Namen bereits eingelesen wurde
                        System.out.println("Sektor " + sektor[0] + " kommt doppelt in der Datei vor. Es wird der erste Wert"+ startwerteMap.get(sektor[0]) + " verwendet.");
                        continue;
                    }
                    if(sektor.length == 2){ //prüft ob ergebniss plausibel (wenn genau 2 Werte vorhanden)
                        try{
                            int sektorwert = Integer.parseInt(sektor[1]); //wandelt den sektorwert in int um
                            startwerteMap.put(sektor[0], sektorwert); //Schreibt den Sektornamen mit dem Sektorwert in die Map
                        }
                        catch (NumberFormatException e){ //Falls umwandlung in int nicht möglich
                            e.printStackTrace();
                            System.out.println("Beim Lesen der Datei ist ein Fehler aufgetreten. Bitte verwenden sie das Format: >sektorname = sektorwert<"); 
                        }
                    }
                    else {
                        System.out.println("Beim Lesen der Datei ist ein Fehler aufgetreten. Bitte verwenden sie das Format: >sektorname = sektorwert<"); 
                    }
                }                
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        return startwerteMap;
    }

    public HashMap<String,Integer> dateiAuslesenANSI(String pfad) 
    {
        HashMap<String,Integer> startwerteMap = new HashMap<String,Integer>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pfad), "Cp1252"))) //liest im ANSI Format
        {
            String line;

            while ((line = br.readLine()) != null) 
            {
                if(line.contains("=")){
                    line = line.replaceAll("\\s",""); //alle Leerzeichen entfernen
                    line = line.toLowerCase(); //setzt den String auf kleinschreibung um Groß/Kleinschreibungsfehler zu verhindern.
                    String[] sektor = line.split("="); //teilt die Zeile an allen "=" ("=" sind dann nicht mehr enthalten)

                    if(sektor.length == 2){ //prüft ob ergebniss plausibel (wenn genau 2 Werte vorhanden)
                        try{
                            int sektorwert = Integer.parseInt(sektor[1]); //wandelt den sektorwert in int um
                            startwerteMap.put(sektor[0], sektorwert); //Schreibt den Sektornamen mit dem Sektorwert in die Map
                        }
                        catch (NumberFormatException e){ //Falls umwandlung in int nicht möglich
                            e.printStackTrace();
                            System.out.println("Beim Lesen der Datei ist ein Fehler aufgetreten. Bitte verwenden sie das Format: >sektorname = sektorwert<"); 
                        }
                    }
                    else {
                        System.out.println("Beim Lesen der Datei ist ein Fehler aufgetreten. Bitte verwenden sie das Format: >sektorname = sektorwert<"); 
                    }
                }                
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        return startwerteMap;
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
    
    //#OBSOLETE
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

    //#OBSOLETE
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

    
}
