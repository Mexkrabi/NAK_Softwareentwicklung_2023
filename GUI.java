import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Beschreiben Sie hier die Klasse GUI.
 * 
 * @author Malte Fischer
 * @version (0.1)
 */
public class GUI extends JFrame implements ActionListener {
    // Instanzvariablen 
    private JFrame fenster;
    private JPanel startBildschirm, wertezuweisen, auswahlDatei;
    private JComboBox<String> cbDateien;
    private String strSpielstand, strAuswahl;
    private String spielstand; //# <-- ersetzen in der Main
    private JLabel lblwilkommen;
    private JButton btStart, btEnde, btAuswahlBestätigen;
    private JLabel lblWirtschaftsleistung, lblModernisierungsgrad, lblLebensqualität, lblBildung;
    private JLabel lblWirtschaftsleistungStand, lblModernisierungsgradStand,lblLebensqualitätStand, lblBildungStand;
    private JButton btWirtschaftHoch, btWirtschaftRunter, btModernHoch, btModernRunter, btLebenHoch, btLebenRunter, btBildungHoch, btBildungRunter;
    
    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     * @param  y    (Beschreibung des Parameters)
     */
    public void spielstandänderung()
    {
        switch (strSpielstand) {
            case "Start":
                startBildschirm();
                break;
            case "Auswahl" :
                dateiAuswal();
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
        fenster = new JFrame("Start-End Frame");
        startBildschirm = new JPanel();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        startBildschirm.setLayout(new GridLayout(3, 1, 30, 30));

        // Label & Button erstellen und hinzufügen
        lblwilkommen = new JLabel("Willkommen!");
        btStart = new JButton("Start");
        btEnde = new JButton("Ende");

        btStart.addActionListener(this);
        btEnde.addActionListener(this);

        //Hinzufügen der Button und des Label
        startBildschirm.add(lblwilkommen);
        startBildschirm.add(btStart);
        startBildschirm.add(btEnde);

        fenster.add(startBildschirm);

        //  Größe des Frames festlegen
        startBildschirm.setSize(300, 300);
        fenster.setSize(300,300);
        setLocationRelativeTo(null);
        fenster.setVisible(true);
        startBildschirm.setVisible(true);
    }
    
    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     * @param  y	(Beschreibung des Parameters)
     * @return		(Beschreibung des Rückgabewertes)
     */
    public void dateiAuswal()
    {
        // tragen Sie hier den Code ein
        fenster.setTitle("Werte zuweisen");
        auswahlDatei = new JPanel();
        setDefaultCloseOperation(EXIT_ON_CLOSE);//das Programm wird beendet wenn auf X geklickt wird     
        auswahlDatei.setLayout(new GridLayout(3, 2, 40, 40));
        
        JLabel lblAuswahl = new JLabel("Wählen Sie das zu Spielende Land aus");
        btAuswahlBestätigen = new JButton("Auswahl bestätigen");
        cbDateien = new JComboBox<String>();
    
        cbDateien.addItem("Auswahl");
        cbDateien.addItem("Hier die Dateinamen einfügen");
        
        btAuswahlBestätigen.addActionListener(this);
        
        auswahlDatei.add(lblAuswahl);
        auswahlDatei.add(cbDateien); 
        auswahlDatei.add(btAuswahlBestätigen);
        fenster.add(auswahlDatei);
        
        fenster.setVisible(true);
        auswahlDatei.setVisible(true);
        fenster.setSize(300,300);;//Passt das Fenster auf die notwendige Größe an 
        fenster.setLocationRelativeTo(null);
    }

    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     * @param  y    (Beschreibung des Parameters)
     * @return        (Beschreibung des Rückgabewertes)
     */
    private void wertezuweisung()
    {
        fenster.setTitle("Werte zuweisen");

        wertezuweisen = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//das Programm wird beendet wenn auf X geklickt wird
        wertezuweisen.setLayout(new GridLayout(4, 4, 10, 10));

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

        fenster.add(wertezuweisen);

        wertezuweisen.add(lblWirtschaftsleistung);
        wertezuweisen.add(lblWirtschaftsleistungStand);
        wertezuweisen.add(btWirtschaftHoch);
        wertezuweisen.add(btWirtschaftRunter);

        wertezuweisen.add(lblModernisierungsgrad);
        wertezuweisen.add(lblModernisierungsgradStand);
        wertezuweisen.add(btModernHoch);
        wertezuweisen.add(btModernRunter);

        wertezuweisen.add(lblLebensqualität);
        wertezuweisen.add(lblLebensqualitätStand);
        wertezuweisen.add(btLebenHoch);
        wertezuweisen.add(btLebenRunter);

        wertezuweisen.add(lblBildung);
        wertezuweisen.add(lblBildungStand);
        wertezuweisen.add(btBildungHoch);
        wertezuweisen.add(btBildungRunter);

        fenster.setVisible(true);
        wertezuweisen.setVisible(true);
        fenster.pack();//Passt das Fenster auf die notwendige Größe an 
        fenster.setLocationRelativeTo(null);
    }

    //Getter & Setter
    public String getSpielstand() 
    {
        return this.strSpielstand;
    }

    public void setSpielstand(String neuerSpielstand) 
    {
        this.strSpielstand = neuerSpielstand;
        //Prüfung hinzufügen
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btStart) {
            // Aktion für Button 1
            System.out.println("Start wurde geklickt.");
            setSpielstand("Auswahl");
            spielstandänderung();
            startBildschirm.setVisible(false);
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
        } else if (e.getSource() == btAuswahlBestätigen) {

            strAuswahl = (String) cbDateien.getSelectedItem();
            System.out.println(strAuswahl);
            setSpielstand("Wertezuweisen");
        }
    }
}
