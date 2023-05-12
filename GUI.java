import javax.swing.*;
import java.awt.*;
/**
 * Beschreiben Sie hier die Klasse GUI.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class GUI extends JFrame {
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private JLabel lblWirtschaftsleistung, lblModernisierungsgrad, lblLebensqualität, lblBildung;
    
    private JButton btWirtschaftHoch, btWirtschaftRunter, btModernHoch, btModernRunter, btLebenHoch, btLebenRunter, btBildungHoch, btBildungRunter;


    /**
     * Konstruktor für Objekte der Klasse GUI
     */
    public GUI() {
        setTitle("Ökolopoly");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 3, 10, 10));

        lblWirtschaftsleistung = new JLabel("Wirtschaftsleistung: ");
        btWirtschaftHoch = new JButton("+");
        btWirtschaftRunter = new JButton("-");

        lblModernisierungsgrad = new JLabel("Modernisierungsgrad: ");
        btModernHoch = new JButton("+");
        btModernRunter = new JButton("-");

        lblLebensqualität = new JLabel("Lebensqualität: ");
        btLebenHoch = new JButton("+");
        btLebenRunter = new JButton("-");

        lblBildung = new JLabel("Bildung: ");
        btBildungHoch = new JButton("+");
        btBildungRunter = new JButton("-");

        add(lblWirtschaftsleistung);
        add(btWirtschaftHoch);
        add(btWirtschaftRunter);

        add(lblModernisierungsgrad);
        add(btModernHoch);
        add(btModernRunter);

        add(lblLebensqualität);
        add(btLebenHoch);
        add(btLebenRunter);

        add(lblBildung);
        add(btBildungHoch);
        add(btBildungRunter);
        //Passt das Fenster auf die notwendige Größe an 
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     * @param  y    ein Beispielparameter für eine Methode
     * @return        die Summe aus x und y
     */
    public static void main(String[] args) {
        new GUI();
    }
}
