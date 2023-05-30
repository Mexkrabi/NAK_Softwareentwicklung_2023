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
     * @param dateiname Dateiname der .sim-Datei von der man den Pfad haben möchte
     * 
     * @return Gibt den ausgewählten Dateipfad als String zurück
     */
    public String simDateiAuswahl(String dateiname)
    {
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
    
}
