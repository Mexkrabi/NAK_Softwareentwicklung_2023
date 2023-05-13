import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Beschreiben Sie hier die Klasse GUI.
 * 
 * @author (Malte Fischer) 
 * @version (01)
 */
public class GUI extends JFrame implements ActionListener {
    // Instanzvariablen 
    private JFrame fenster;
    private String spielstand;
    private JLabel lblwilkommen;
    private JButton btStart, btEnde;
    private JLabel lblWirtschaftsleistung, lblModernisierungsgrad, lblLebensqualität, lblBildung;
    private JLabel lblWirtschaftsleistungStand, lblModernisierungsgradStand,lblLebensqualitätStand, lblBildungStand;
    private JButton btWirtschaftHoch, btWirtschaftRunter, btModernHoch, btModernRunter, btLebenHoch, btLebenRunter, btBildungHoch, btBildungRunter;



    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     * @param  y    (Beschreibung des Parameters)
     * @return        (Beschreibung des Rückgabewertes)
     */
    public void spielstandänderung()
    {
        switch (spielstand) {
            case "Start":
                startBildschirm();
                break;
            case "Wertezuweisen":
                wertezuweisung();

        }
    }

    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     * @param  y    (Beschreibung des Parameters)
     * @return        (Beschreibung des Rückgabewertes)
     */
    private void startBildschirm()
    {
        // tragen Sie hier den Code ein
        setTitle("Start-End Frame");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 30, 30));
        

        // Label & Button erstellen und hinzufügen
        lblwilkommen = new JLabel("Willkommen!");
        btStart = new JButton("Start");
        btEnde = new JButton("Ende");
        
        
        btStart.addActionListener(this);
        btEnde.addActionListener(this);
        
        //Hinzufügen der Button und des Label
        add(lblwilkommen);
        add(btStart);
        add(btEnde);

        //  Größe des Frames festlegen
        setSize(300, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     * @param  y    (Beschreibung des Parameters)
     * @return        (Beschreibung des Rückgabewertes)
     */
    private void wertezuweisung()
    {
        fenster = new JFrame("Werte zuweisen");
        
        JPanel panel = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//das Programm wird beendet wenn auf X geklickt wird
        panel.setLayout(new GridLayout(4, 4, 10, 10));

        lblWirtschaftsleistung = new JLabel("Wirtschaftsleistung: ");
        lblWirtschaftsleistungStand = new JLabel("BEISPIEL");
        btWirtschaftHoch = new JButton("+");
        btWirtschaftRunter = new JButton("-");

        btWirtschaftHoch.addActionListener(this);
        btWirtschaftRunter.addActionListener(this);

        lblModernisierungsgrad = new JLabel("Modernisierungsgrad: ");
        lblModernisierungsgradStand = new JLabel("BEISPIEL");
        btModernHoch = new JButton("+");
        btModernRunter = new JButton("-");

        btModernHoch.addActionListener(this);
        btModernRunter.addActionListener(this);
        
        lblLebensqualität = new JLabel("Lebensqualität: ");
        lblLebensqualitätStand = new JLabel("BEISPIEL");
        btLebenHoch = new JButton("+");
        btLebenRunter = new JButton("-");

        btLebenHoch.addActionListener(this);
        btLebenRunter.addActionListener(this);
        
        lblBildung = new JLabel("Bildung: ");
        lblBildungStand = new JLabel("BEISPIEL");
        btBildungHoch = new JButton("+");
        btBildungRunter = new JButton("-");
        
        btBildungHoch.addActionListener(this);
        btBildungRunter.addActionListener(this);
        
        fenster.add(panel);
        
        panel.add(lblWirtschaftsleistung);
        panel.add(lblWirtschaftsleistungStand);
        panel.add(btWirtschaftHoch);
        panel.add(btWirtschaftRunter);

        panel.add(lblModernisierungsgrad);
        panel.add(lblModernisierungsgradStand);
        panel.add(btModernHoch);
        panel.add(btModernRunter);

        panel.add(lblLebensqualität);
        panel.add(lblLebensqualitätStand);
        panel.add(btLebenHoch);
        panel.add(btLebenRunter);

        panel.add(lblBildung);
        panel.add(lblBildungStand);
        panel.add(btBildungHoch);
        panel.add(btBildungRunter);

        fenster.setVisible(true);
        panel.setVisible(true);
        fenster.pack();//Passt das Fenster auf die notwendige Größe an 
        fenster.setLocationRelativeTo(null);
    }
    
    //Getter & Setter
    public String getSpielstand() 
    {
        return this.spielstand;
    }
    public void setSpielstand(String neuerSpielstand) 
    {
        this.spielstand = neuerSpielstand;
        //Prüfung hinzufügen
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btStart) {
            // Aktion für Button 1
            System.out.println("Start wurde geklickt.");
            setSpielstand("Auslesen");
            dispose();
        } else if (e.getSource() == btEnde) {
            // Aktion für Button 2
            System.out.println("Ende wurde geklickt.");
            System.exit(0);
        } else if (e.getSource() == btModernHoch) {
            
            System.out.println("Modernisierung um 1 Punkt hoch");
        } else if (e.getSource() == btModernRunter) {
            
            System.out.println("Modernisierung um 1 Punkt runter");
            } else if (e.getSource() == btWirtschaftHoch) {
                
            System.out.println("Wirtschaftleistung um 1 Punkt hoch");
        } else if (e.getSource() == btWirtschaftRunter) {
                
            System.out.println("Wirtschaftleistung um 1 Punkt runter");
        } else if (e.getSource() == btLebenHoch) {
                
            System.out.println("Lebensqualität um 1 Punkt hoch");
        } else if (e.getSource() == btLebenRunter) {
                
            System.out.println("Lebensqualität um 1 Punkt runter");
        } else if (e.getSource() == btBildungHoch) {
                
            System.out.println("Bildung um 1 Punkt hoch");
        } else if (e.getSource() == btBildungRunter) {
                
            System.out.println("Bildung um 1 Punkt runter");
        }
        }
    }
