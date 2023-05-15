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
    private Integer intWirtschaftsleistung, intModernisierungsgrad, intLeben, intBildung, intStaatsvermögen, intVerbleibendeRunden;
    private String spielstand; //# <-- ersetzen in der Main
    private JLabel lblwilkommen;
    private JButton btStart, btEnde, btAuswahlBestätigen, btweiter, btPunktezuweisungBestätigen;
    private JLabel lblWirtschaftsleistung, lblModernisierungsgrad, lblLebensqualität, lblBildung, lblVerbleibendesStaatskapital;
    private JLabel lblWirtschaftsleistungStand, lblModernisierungsgradStand,lblLebensqualitätStand, lblBildungStand;
    private JButton btWirtschaftHoch, btWirtschaftRunter, btModernHoch, btModernRunter, btLebenHoch, btLebenRunter, btBildungHoch, btBildungRunter;

    /**
     * Die Methode spielstandänderunglegt organisiert anhand einer Variablen den aktuellen Spielstand 
     * 
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
                break;
            case "BERECHNUNG" :
                System.out.println("HUIIIIIIIIIIII");;

        }
    }

    /**
     * Die Methode erstellt den Startbildschirm und gibt die Wahl zwischen Starten und Beenden 
     * des Programmes
     * 
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
        for (String datei : Main.dateiLeser.dateienFinden()){
            cbDateien.addItem(datei);
        }
        /*cbDateien.addItem("Auswahl");
        cbDateien.addItem("beispielland");
        cbDateien.addItem("Hier die Dateinamen einfügen");*/

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
     */
    public void startwerte()
    {
        // tragen Sie hier den Code ein
        fenster.setTitle("Startwerte der Simulation");

        startwerte = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//das Programm wird beendet wenn auf X geklickt wird
        startwerte.setLayout(new GridLayout(14, 1, 10, 10));
        
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
        
        
        btweiter = new JButton ("Weiter");
        fenster.add(startwerte);
        
        btweiter.addActionListener(this);
        
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
        startwerte.add(btweiter);
        

        fenster.setSize(300,700);//Passt das Fenster auf die notwendige Größe an 
        fenster.setLocationRelativeTo(null);
        fenster.setVisible(true);
        startwerte.setVisible(true);
    }

    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     */
    private void wertezuweisung()
    {
        fenster.setTitle("Werte zuweisen");

        wertezuweisen = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//das Programm wird beendet wenn auf X geklickt wird
        wertezuweisen.setLayout(new GridLayout(5, 4, 10, 10));

        lblWirtschaftsleistung = new JLabel("Wirtschaftsleistung: ");
        intWirtschaftsleistung = Main.logik.startwerteHash.get("Wirtschaftsleistung");
        lblWirtschaftsleistungStand = new JLabel(intWirtschaftsleistung.toString());
        btWirtschaftHoch = new JButton("+");
        btWirtschaftRunter = new JButton("-");

        btWirtschaftHoch.addActionListener(this);
        btWirtschaftRunter.addActionListener(this);
        
        
        lblModernisierungsgrad = new JLabel("Modernisierungsgrad: ");
        intModernisierungsgrad = Main.logik.startwerteHash.get("Modernisierungsgrad");
        lblModernisierungsgradStand = new JLabel(intModernisierungsgrad.toString());
        btModernHoch = new JButton("+");
        btModernRunter = new JButton("-");

        btModernHoch.addActionListener(this);
        btModernRunter.addActionListener(this);

        lblLebensqualität = new JLabel("Lebensqualität: ");
        intLeben = Main.logik.startwerteHash.get("Lebensqualität");
        lblLebensqualitätStand = new JLabel(intLeben.toString());
        btLebenHoch = new JButton("+");
        btLebenRunter = new JButton("-");

        btLebenHoch.addActionListener(this);
        btLebenRunter.addActionListener(this);

        lblBildung = new JLabel("Bildung: ");
        intBildung = Main.logik.startwerteHash.get("Bildung");
        lblBildungStand = new JLabel(intBildung.toString());
        btBildungHoch = new JButton("+");
        btBildungRunter = new JButton("-");

        btPunktezuweisungBestätigen = new JButton("Zuweisung Bestätigen");

        btPunktezuweisungBestätigen.addActionListener(this);
        btBildungHoch.addActionListener(this);
        btBildungRunter.addActionListener(this);

        JLabel lblVerbleibendeRunden = new JLabel("Verbleibende Runden: " + Main.logik.rundenzahl); //System gedribbelt
        intStaatsvermögen = Main.logik.startwerteHash.get("Staatsvermögen");
        lblVerbleibendesStaatskapital = new JLabel("Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
        btPunktezuweisungBestätigen.setBounds(5, 3, 1, 2);
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
        
        wertezuweisen.add(lblVerbleibendeRunden);
        wertezuweisen.add(lblVerbleibendesStaatskapital);
        wertezuweisen.add(btPunktezuweisungBestätigen);
        
        fenster.setVisible(true);
        wertezuweisen.setVisible(true);
        btBildungRunter.setVisible(false);
        btLebenRunter.setVisible(false);
        btModernRunter.setVisible(false);
        fenster.pack();
        //Passt das Fenster auf die notwendige Größe an 
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
            intStaatsvermögen = intStaatsvermögen - 1;
            intModernisierungsgrad = intModernisierungsgrad + 1;
            lblVerbleibendesStaatskapital.setText("Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
            lblModernisierungsgradStand.setText(intModernisierungsgrad.toString());
            btModernRunter.setVisible(true);
            System.out.println("Modernisierung um 1 Punkt hoch");
        } else if (e.getSource() == btModernRunter) {
           intStaatsvermögen = intStaatsvermögen + 1;
            intModernisierungsgrad = intModernisierungsgrad - 1;
                if(intModernisierungsgrad == Main.logik.startwerteHash.get("Modernisierungsgrad")) {
                    btModernRunter.setVisible(false);
                }
            lblVerbleibendesStaatskapital.setText("Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
            lblModernisierungsgradStand.setText(intModernisierungsgrad.toString());

            System.out.println("Modernisierung um 1 Punkt runter");
        } else if (e.getSource() == btWirtschaftHoch) {
            intWirtschaftsleistung = intWirtschaftsleistung + 1;
            //# Prüfe ob wert im Wertebereich wenn nicht ERROR Methode in Logik aufrufen (switch case)
            //# vvvvvvvvvv
                if (Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung) == true){
                
                    System.out.println(Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung));
                    //# ^^^^^^^^^^
                    intStaatsvermögen = intStaatsvermögen - 1;
                    lblVerbleibendesStaatskapital.setText( "Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());lblWirtschaftsleistungStand.setText(intWirtschaftsleistung.toString());
                    System.out.println("Wirtschaftleistung um 1 Punkt hoch");
                }else {
                    System.out.println("Wert nicht im Wertebereich");
                    intWirtschaftsleistung = intWirtschaftsleistung - 1;
                 }
        } else if (e.getSource() == btWirtschaftRunter) {
            
            intWirtschaftsleistung = intWirtschaftsleistung - 1;
            //#Prüfen ob investiert werden darf
            //# Prüfe ob wert im Wertebereich wenn nicht ERROR Methode in Logik aufrufen (switch case)
            //# vvvvvvvvvv
                if (Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung) == true){
                
                    System.out.println(Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung));
            
                    intStaatsvermögen = intStaatsvermögen - 1;
                    
                    //#Prüfe ob wert im Wertebereich wenn nicht ERROR Methode in Logik aufrufen (switch case)
                    Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung);//Prüfen ob Wert im Wertebereich 
            
                    lblVerbleibendesStaatskapital.setText( "Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
                    lblWirtschaftsleistungStand.setText(intWirtschaftsleistung.toString());
                }else {
                    System.out.println("Wert nicht im Wertebereich");
                    intWirtschaftsleistung = intWirtschaftsleistung + 1;
                 }
        } else if (e.getSource() == btLebenHoch) {
            intStaatsvermögen = intStaatsvermögen - 1;
            intLeben = intLeben + 1;
            lblVerbleibendesStaatskapital.setText("Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
            lblLebensqualitätStand.setText(intLeben.toString());
            btLebenRunter.setVisible(true);
            System.out.println("Lebensqualität um 1 Punkt hoch");
        } else if (e.getSource() == btLebenRunter) {
            intStaatsvermögen = intStaatsvermögen + 1;
            intLeben = intLeben - 1;
                if(intLeben == Main.logik.startwerteHash.get("Lebensqualität")) {
                    btLebenRunter.setVisible(false);
                }
            lblVerbleibendesStaatskapital.setText("Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
            lblLebensqualitätStand.setText(intLeben.toString());
            System.out.println("Lebensqualität um 1 Punkt runter");
        } else if (e.getSource() == btBildungHoch) {
            intStaatsvermögen = intStaatsvermögen - 1;
            intBildung = intBildung + 1;
            lblVerbleibendesStaatskapital.setText("Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
            lblBildungStand.setText(intBildung.toString());
            System.out.println("Bildung um 1 Punkt hoch");
            btBildungRunter.setVisible(true);
        } else if (e.getSource() == btBildungRunter) {
            intStaatsvermögen = intStaatsvermögen + 1;
            intBildung = intBildung - 1;
                if(intBildung == Main.logik.startwerteHash.get("Bildung")) {
                    btBildungRunter.setVisible(false);
                }
            lblVerbleibendesStaatskapital.setText("Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
            lblBildungStand.setText(intBildung.toString());

            System.out.println("Bildung um 1 Punkt runter");
        } else if (e.getSource() == btAuswahlBestätigen) {
            strAuswahl = (String) cbDateien.getSelectedItem(); //Dateiname der ausgewählten Datei abspeichern
            System.out.println(strAuswahl);
            setSpielstand("BREAK");
            spielstandänderung();
            auswahlDatei.setVisible(false);
        } else if (e.getSource() == btweiter) {
            setSpielstand("WERTZUWEISEN");
            spielstandänderung();
            System.out.println("Weiter geklickt");
            startwerte.setVisible(false);
        } else if (e.getSource() == btPunktezuweisungBestätigen) {
            setSpielstand("BERECHNUNG");
            spielstandänderung();
            System.out.println("Weiter geklickt");
            wertezuweisen.setVisible(false);
        }
    }
}
