import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

/**
 * Diese Klasse enthält den Code für das Einlesen der externen .sim Dateien.
 * Die Dateien beinhalten Anfangswerte für das Spiel.
 * 
 * @author (your name)
 * @version (0.1)
 */
public class DateiLeser 
{    
    public DateiLeser() 
    {
        
    }
    
    /**
     * Methode erlaubt die Wahl der .sim Datei per Konsoleneingabe
     * 
     * @return Gibt den den ausgewählten Dateipfad zurück als String
     */
    public String simDateiAuswahl()
    {
        String dieserOrdner = System.getProperty("user.dir"); //aktueller Dateipfad
        
        System.out.println("Wie heißt die Datei welche Sie auswählen wollen?");
        Scanner sc = new Scanner(System.in); //Konsoleneingabeleser
        String input = "/" + sc.next() + ".sim"; //Eingabe abspeichern
        
        System.out.println("Pfad des aktuellen Projektordners: " + dieserOrdner + input); //zusammenfügen
        String simFilePath = dieserOrdner + input;
        
        allesAuslesen(simFilePath);

        return simFilePath; //gibt den ausgewählten Dateipfad zurück (gespeichert in Logik() )
    }
    
    /**
     * Liest die komplette Datei aus und gibt sie in der Konsole wieder
     * 
     * @param pfad Pfad der Datei angeben
     */
    public void allesAuslesen(String pfad) 
    {
        try (BufferedReader br = new BufferedReader(new FileReader(pfad))) 
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
    
    public String auslesen(String pfad, String kriterium) 
    {
                try (BufferedReader br = new BufferedReader(new FileReader(pfad))) 
        {
            String line;
            while ((line = br.readLine()) != null) 
            {
                String ausgelesenerWert;
                // Hier kannst du die Zeilen der SIM-Datei auf Werte prüfen und in Variablen speichern
                // Beispiel: Wenn die Zeile mit "name=" beginnt, speichere den Namen in der Variable "name"
                // BEISPIELCODE vvvvvvvvvvvvvvvvvvvvvvvvvvvv
                if (line.startsWith(kriterium)) 
                {
                    System.out.println("Eingabezeile: " + line.substring(0));
                    ausgelesenerWert = line.substring(kriterium.length());
                    System.out.println("Ausgelesener Wert: " + ausgelesenerWert);
                    return ausgelesenerWert;
                }
                //BEISPIELCODE ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        return "Wert konnte nicht gefunden werden.";
    }

    
        //REFERENZ
    public static void main(String[] args) 
    {
        String currentPath = System.getProperty("user.dir");
        System.out.println("Pfad des aktuellen Projektordners: " + currentPath + "/beispielland.sim");
        String simFilePath = currentPath + "/beispielland.sim";
        try (BufferedReader br = new BufferedReader(new FileReader(simFilePath))) 
        {
            String line;
            while ((line = br.readLine()) != null) 
            {
                // Hier kannst du die Zeilen der SIM-Datei auf Werte prüfen und in Variablen speichern
                // Beispiel: Wenn die Zeile mit "name=" beginnt, speichere den Namen in der Variable "name"
                // BEISPIELCODE vvvvvvvvvvvvvvvvvvvvvvvvvvvv
                if (line.startsWith("name=")) 
                {
                    String name = line.substring(5);
                    System.out.println("Name: " + name);
                }
                //BEISPIELCODE ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                System.out.println(line.substring(0));
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
