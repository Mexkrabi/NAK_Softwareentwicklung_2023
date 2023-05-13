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
    public GUI() {
        fenster = new JFrame();
    }
    // Instanzvariablen 
    private JFrame fenster;
    private JPanel startBildschirm, wertezuweisen, auswahlDatei, startwerte;
    public JComboBox<String> cbDateien;
    public String strSpielstand, strAuswahl;
    private String spielstand; //# <-- ersetzen in der Main
    private JLabel lblwilkommen;
    private JButton btStart, btEnde, btAuswahlBestätigen;
    private JLabel lblWirtschaftsleistung, lblModernisierungsgrad, lblLebensqualität, lblBildung;
    private JLabel lblWirtschaftsleistungStand, lblModernisierungsgradStand,lblLebensqualitätStand, lblBildungStand;
    private JButton btWirtschaftHoch, btWirtschaftRunter, btModernHoch, btModernRunter, btLebenHoch, btLebenRunter, btBildungHoch, btBildungRunter;

    /**
     * Die Methode spielstandänderunglegt organisiert anhand einer Variablen den aktuellen Spielstand 
     * 
     * @param  y    (Beschreibung des Parameters)
     */
    public void spielstandänderung()
    {
        // wenn diese Methode aufgerufen wird und der Wert zu einem der Case passt,
        // dann wird der zugehöhrige Code ausgeführt
        switch (strSpielstand) {
            case "START":
                startBildschirm();
                break;
            case "AUSWAHL" :
                dateiAuswal();
                break;
            case "PAUSE":
                break;
            case "STARTWERTE" :
                startwerte();
                break;
            case "WERTZUWEISEN":
                wertezuweisung();

        }
    }

    /**
     * Die Methode erstellt den Startbildschirm und gibt die Wahl zwischen Starten und Beenden 
     * des Programmes
     * 
     * @param  y    (Beschreibung des Parameters)
     * 
     */
    private void startBildschirm()
    {
        // Erstellt ein neues Fenster mit dem Titel "Start-End Frame" 
        // und ein neues JPanel "startBildschirm"
        fenster.setTitle("Start-End Frame");
        startBildschirm = new JPanel();
        // Das Programm wird geschlossen wenn das "X" geklickt wird
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Erzeuge eine 3x1 Matrix mit einem Abstand von 30 Pixeln
        startBildschirm.setLayout(new GridLayout(3, 1, 30, 30));

        // Label & Button erstellen 
        lblwilkommen = new JLabel("Willkommen!");
        btStart = new JButton("Start");
        btEnde = new JButton("Ende");
        
        //fügt einen ActionListener hinzu um auf einen klick zu reagieren
        btStart.addActionListener(this);
        btEnde.addActionListener(this);

        //Fügt das JLabel und die beiden JButtons zum JPanel hinzu
        startBildschirm.add(lblwilkommen);
        startBildschirm.add(btStart);
        startBildschirm.add(btEnde);
        
        //Fügt das JPanel zum JFrame hinzu
        fenster.add(startBildschirm);

        //legt die Größe des Frames fest
        //startBildschirm.setSize(300, 300);
        fenster.setSize(300,300);
        
        // Zentriert das JFrame auf dem Bildschirm
        fenster.setLocationRelativeTo(null);
        
        //setzt die Sichtbarkeit auf true
        fenster.setVisible(true);
        startBildschirm.setVisible(true);
    }

    /**
     * Die Methode erstellt eine Eingabemaske auf der ausgewählt werden kann welches Land 
     * gespielt werden soll und legt somit die auszulesende Datei fest
     * 
     * 
     * @param  y    (Beschreibung des Parameters)
     */
    public void dateiAuswal()
    {
        // tragen Sie hier den Code ein
        fenster.setTitle("zu Simulierendes Land auswählen");
        auswahlDatei = new JPanel();
        
        // Das Programm wird geschlossen wenn das "X" geklickt wird
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //Erzeuge eine 3x1 Matrix mit einem Abstand von 30 Pixeln
        auswahlDatei.setLayout(new GridLayout(3, 2, 40, 40));
        
        
        JLabel lblAuswahl = new JLabel("Wählen Sie das zu Spielende Land aus");
        btAuswahlBestätigen = new JButton("Auswahl bestätigen");
        cbDateien = new JComboBox<String>();
        
        //Fügt Werte in die ComboBox
        cbDateien.addItem("Auswahl");
        cbDateien.addItem("beispielland");
        cbDateien.addItem("Hier die Dateinamen einfügen");

        //fügt einen ActionListener hinzu um auf einen klick zu reagieren
        btAuswahlBestätigen.addActionListener(this);

        //Fügt das JLabel und die beiden JButtons zum JPanel hinzu
        auswahlDatei.add(lblAuswahl);
        auswahlDatei.add(cbDateien); 
        auswahlDatei.add(btAuswahlBestätigen);
        
        //Fügt das JPanel zum JFrame hinzu
        fenster.add(auswahlDatei);

        //legt die Größe des Frames fest
        fenster.setSize(300,300);
        // Zentriert das JFrame auf dem Bildschirm
        fenster.setLocationRelativeTo(null);
        
        //setzt die Sichtbarkeit auf true
        fenster.setVisible(true);
        auswahlDatei.setVisible(true);
    }

    /**
     * Die Methode zeigt dem Spieler die Startwerte seiner Simulation
     * 
     * 
     * @param  y    (Beschreibung des Parameters)
     * @return        (Beschreibung des Rückgabewertes)
     */
    public void startwerte()
    {
        // tragen Sie hier den Code ein
        fenster.setTitle("Startwerte der Simulation");

        startwerte = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//das Programm wird beendet wenn auf X geklickt wird
        startwerte.setLayout(new GridLayout(13, 1, 10, 10));
        
        JLabel lblAusgangslage = new JLabel("***** Ausgangslage *****");
        JLabel lblBevölkerungsgröße = new JLabel("Bevölkerungsgröße = " + Main.logik.startwerteHash.get("Bevölkerungsgröße"));
        JLabel lblBevölkerungswachstum = new JLabel("Bevölkerungswachstum = " + Main.logik.startwerteHash.get("Bevölkerungswachstum"));
        JLabel lblWirtschaftsleistung = new JLabel("Wirtschaftsleistung = " + Main.logik.startwerteHash.get("Wirtschaftsleistung"));
        JLabel lblModernisierungsgrad = new JLabel("Modernisierungsgrad = " + Main.logik.startwerteHash.get("Modernisierungsgrad"));
        JLabel lblPolitischeStabilität = new JLabel("Politische Stabilität = " + Main.logik.startwerteHash.get("Politische Stabilität"));
        JLabel lblUmweltverschmutzung = new JLabel("Umweltverschmutzung = " + Main.logik.startwerteHash.get("Umweltverschmutzung"));
        JLabel lblLebensqualität = new JLabel("Lebensqualität = " + Main.logik.startwerteHash.get("Lebensqualität"));
        JLabel lblBildung = new JLabel("Bildung = " + Main.logik.startwerteHash.get("Bildung"));
        JLabel lblStaatsvermögen = new JLabel("Staatsvermögen = " + Main.logik.startwerteHash.get("Staatsvermögen"));
        JLabel lblLeer = new JLabel("");
        JLabel lblSimulationsablauf = new JLabel("***** Simulationsablauf *****");
        JLabel lblRundenzahl = new JLabel("Rundenzahl = " + Main.logik.rundenzahl);
        
        fenster.add(startwerte);
        
        
        startwerte.add(lblAusgangslage);
        startwerte.add(lblBevölkerungsgröße);
        startwerte.add(lblBevölkerungswachstum);
        startwerte.add(lblWirtschaftsleistung);
        startwerte.add(lblModernisierungsgrad);
        startwerte.add(lblPolitischeStabilität);
        startwerte.add(lblUmweltverschmutzung);
        startwerte.add(lblLebensqualität);
        startwerte.add(lblBildung);
        startwerte.add(lblStaatsvermögen);
        startwerte.add(lblLeer);
        startwerte.add(lblSimulationsablauf);
        startwerte.add(lblRundenzahl);
        
        
        fenster.setVisible(true);
        startwerte.setVisible(true);
        fenster.pack();//Passt das Fenster auf die notwendige Größe an 
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
            setSpielstand("AUSWAHL");
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
            setSpielstand("BREAK");
            spielstandänderung();
            auswahlDatei.setVisible(false);
        }
    }
}
