import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SimFileReader {
    public static void main(String[] args) {
        String simFilePath = "path/to/sim/file.sim";
        try (BufferedReader br = new BufferedReader(new FileReader(simFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Hier kannst du die Zeilen der SIM-Datei auf Werte prüfen und in Variablen speichern
                // Beispiel: Wenn die Zeile mit "name=" beginnt, speichere den Namen in der Variable "name"
                if (line.startsWith("name=")) {
                    String name = line.substring(5);
                    System.out.println("Name: " + name);
                }
            }
        } catch (IOException e) {
        e.printStackTrace();
        }
    }
}