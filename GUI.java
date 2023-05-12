import javax.swing.*;
import java.awt.*;
/**
 * Beschreiben Sie hier die Klasse GUI.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class GUI extends JFrame {
    // Instanzvariablen 
    private JLabel lblWirtschaftsleistung, lblModernisierungsgrad, lblLebensqualität, lblBildung;
    private JLabel lblWirtschaftsleistungStand, lblModernisierungsgradStand,lblLebensqualitätStand, lblBildungStand;
    
    private JButton btWirtschaftHoch, btWirtschaftRunter, btModernHoch, btModernRunter, btLebenHoch, btLebenRunter, btBildungHoch, btBildungRunter;


    /**
     * Konstruktor für Objekte der Klasse GUI
     */
    public GUI() {
        setTitle("Werte zuweisen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//das Programm wird beendet wenn auf X geklickt wird
        setLayout(new GridLayout(4, 4, 10, 10));

        lblWirtschaftsleistung = new JLabel("Wirtschaftsleistung: ");
        lblWirtschaftsleistungStand = new JLabel("BEISPIEL");
        btWirtschaftHoch = new JButton("+");
        btWirtschaftRunter = new JButton("-");

        lblModernisierungsgrad = new JLabel("Modernisierungsgrad: ");
        lblModernisierungsgradStand = new JLabel("BEISPIEL");
        btModernHoch = new JButton("+");
        btModernRunter = new JButton("-");

        lblLebensqualität = new JLabel("Lebensqualität: ");
        lblLebensqualitätStand = new JLabel("BEISPIEL");
        btLebenHoch = new JButton("+");
        btLebenRunter = new JButton("-");

        lblBildung = new JLabel("Bildung: ");
        lblBildungStand = new JLabel("BEISPIEL");
        btBildungHoch = new JButton("+");
        btBildungRunter = new JButton("-");

        add(lblWirtschaftsleistung);
        add(lblWirtschaftsleistungStand);
        add(btWirtschaftHoch);
        add(btWirtschaftRunter);

        add(lblModernisierungsgrad);
        add(lblModernisierungsgradStand);
        add(btModernHoch);
        add(btModernRunter);

        add(lblLebensqualität);
        add(lblLebensqualitätStand);
        add(btLebenHoch);
        add(btLebenRunter);

        add(lblBildung);
        add(lblBildungStand);
        add(btBildungHoch);
        add(btBildungRunter);
         
        pack();//Passt das Fenster auf die notwendige Größe an 
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
