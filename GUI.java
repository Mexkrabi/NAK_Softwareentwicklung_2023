import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Beschreiben Sie hier die Klasse GUI.
 * 
 * @author Malte Fischer
 * @version 0.1
 */
public class GUI extends JFrame implements ActionListener {
    public GUI() {
        fenster = new JFrame();
    }
    // Instanzvariablen 
    private JFrame fenster;
    private JPanel startBildschirm, wertezuweisen, auswahlDatei, startwerte, ladescreen, gameover, victory;
    public JComboBox<String> cbDateien;
    private JProgressBar pbLeben, pbWirtschaftsleistung, pbModernisierungsgrad, pbBildung, pbPolitStab, pbUmwelt, pbVersorgung, pbBevökerungswachstum, pbBevökerungsgröße, Bevölkerungswachstumsfaktor;
    public String strSpielstand, strAuswahl;
    private Integer intWirtschaftsleistung, intModernisierungsgrad, intLeben, intBildung, intStaatsvermögen, intVerbleibendeRunden;
    private String spielstand; //# <-- ersetzen in der Main
    private JLabel lblwilkommen;
    private JButton btStart, btEnde, btAuswahlBestätigen, btweiter, btPunktezuweisungBestätigen, btGameover, btVictory;
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
            case "START" :
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
                break;
            case "LADEN" :
                ladescreen();
                break;
            case "GAMEOVER" :
                gameover();
                break;
            case "VICTORY" :
                victory();
                break;
            default : //falls Spielstand falsch angegeben wird, oder nicht erkannt wird
                System.out.println("! '" + strSpielstand + "' ist kein gültiger Spielstand !");
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
        fenster.setSize(400,400);

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
        fenster.setSize(400,400);
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
        startwerte.setLayout(new GridLayout(14, 2, 10, 10));
        JLabel lblAusgangslage = new JLabel();
        if(Main.logik.aktuelleRunde == Main.logik.rundenzahl) {
            lblAusgangslage.setText("***** Ausgangslage *****");
        } else {
            lblAusgangslage.setText("***** Werte nach Runde " + Main.logik.aktuelleRunde + " *****");
        }

        JLabel lblBevölkerungsgröße = new JLabel("Bevölkerungsgröße = " + Main.bevölkerungsgröße.getWert());
        JLabel lblBevölkerungswachstum = new JLabel("Bevölkerungswachstum = " + Main.bevölkerungswachstum.getWert());
        JLabel lblWirtschaftsleistung = new JLabel("Wirtschaftsleistung = " + Main.wirtschaftsleistung.getWert());
        JLabel lblModernisierungsgrad = new JLabel("Modernisierungsgrad = " + Main.modernisierungsgrad.getWert());
        JLabel lblPolitischeStabilität = new JLabel("Politische Stabilität = " + Main.politische_stabilität.getWert());
        JLabel lblUmweltverschmutzung = new JLabel("Umweltverschmutzung = " + Main.umweltverschmutzung.getWert());
        JLabel lblLebensqualität = new JLabel("Lebensqualität = " + Main.lebensqualität.getWert());
        JLabel lblBildung = new JLabel("Bildung = " + Main.bildung.getWert());
        JLabel lblStaatsvermögen = new JLabel("Staatsvermögen = " + Main.staatsvermögen.getWert());
        JLabel lblLeer = new JLabel("");
        JLabel lblLeer2 = new JLabel("Wertebereich:");
        JLabel lblSimulationsablauf = new JLabel("***** Simulationsablauf *****");
        JLabel lblRundenzahl = new JLabel("Rundenzahl = " + Main.logik.aktuelleRunde);

        pbLeben = new JProgressBar(Main.lebensqualität.getMin(),Main.lebensqualität.getMax());
        pbBevökerungsgröße = new JProgressBar(Main.bevölkerungsgröße.getMin(),Main.bevölkerungsgröße.getMax());
        pbBevökerungswachstum = new JProgressBar(Main.bevölkerungswachstum.getMin(),Main.bevölkerungswachstum.getMax());
        pbWirtschaftsleistung = new JProgressBar(Main.wirtschaftsleistung.getMin(),Main.wirtschaftsleistung.getMax());
        pbModernisierungsgrad = new JProgressBar(Main.modernisierungsgrad.getMin(),Main.modernisierungsgrad.getMax());
        pbPolitStab = new JProgressBar(Main.politische_stabilität.getMin(),Main.politische_stabilität.getMax());
        pbUmwelt = new JProgressBar(Main.umweltverschmutzung.getMin(),Main.umweltverschmutzung.getMax());
        pbBildung = new JProgressBar(Main.bildung.getMin(),Main.bildung.getMax());

        pbBevökerungsgröße.setValue(Main.bevölkerungsgröße.getWert());
        pbBevökerungsgröße.setString(Main.bevölkerungsgröße.getMin() +" - " + Main.bevölkerungsgröße.getMax());
        pbBevökerungsgröße.setStringPainted(true);
        pbBevökerungswachstum.setValue(Main.bevölkerungswachstum.getWert());
        pbBevökerungswachstum.setString(Main.bevölkerungswachstum.getMin() +" - " + Main.bevölkerungswachstum.getMax());
        pbBevökerungswachstum.setStringPainted(true);
        pbWirtschaftsleistung.setValue(Main.wirtschaftsleistung.getWert());
        pbWirtschaftsleistung.setString(Main.wirtschaftsleistung.getMin() +" - " + Main.wirtschaftsleistung.getMax());
        pbWirtschaftsleistung.setStringPainted(true);
        pbModernisierungsgrad.setValue(Main.modernisierungsgrad.getWert());
        pbModernisierungsgrad.setString(Main.modernisierungsgrad.getMin() +" - " + Main.modernisierungsgrad.getMax());
        pbModernisierungsgrad.setStringPainted(true);
        pbPolitStab.setValue(Main.politische_stabilität.getWert());
        pbPolitStab.setString(Main.politische_stabilität.getMin() +" - " + Main.politische_stabilität.getMax());
        pbPolitStab.setStringPainted(true);
        pbUmwelt.setValue(Main.umweltverschmutzung.getWert());
        pbUmwelt.setString(Main.umweltverschmutzung.getMin() +" - " + Main.umweltverschmutzung.getMax());
        pbUmwelt.setStringPainted(true);
        pbLeben.setValue(Main.lebensqualität.getWert());
        pbLeben.setString(Main.lebensqualität.getMin() +" - " + Main.lebensqualität.getMax());
        pbLeben.setStringPainted(true);
        pbBildung.setValue(Main.bildung.getWert());
        pbBildung.setString(Main.umweltverschmutzung.getMin() +" - " + Main.umweltverschmutzung.getMax());
        pbBildung.setStringPainted(true);

        btweiter = new JButton ("Weiter");
        fenster.add(startwerte);

        btweiter.addActionListener(this);

        startwerte.add(lblAusgangslage);
        startwerte.add(lblLeer2);
        startwerte.add(lblBevölkerungsgröße);
        startwerte.add(pbBevökerungsgröße);
        startwerte.add(lblBevölkerungswachstum);
        startwerte.add(pbBevökerungswachstum);
        startwerte.add(lblWirtschaftsleistung);
        startwerte.add(pbWirtschaftsleistung);

        startwerte.add(lblModernisierungsgrad);
        startwerte.add(pbModernisierungsgrad);
        startwerte.add(lblPolitischeStabilität);
        startwerte.add(pbPolitStab);
        startwerte.add(lblUmweltverschmutzung);
        startwerte.add(pbUmwelt);
        startwerte.add(lblLebensqualität);
        startwerte.add(pbLeben);
        startwerte.add(lblBildung);
        startwerte.add(pbBildung);
        startwerte.add(lblStaatsvermögen);
        startwerte.add(lblLeer);
        startwerte.add(lblSimulationsablauf);
        startwerte.add(lblRundenzahl);
        startwerte.add(btweiter);

        fenster.setSize(600,800);//Passt die Größe des Fensters an 
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
        intWirtschaftsleistung = Main.wirtschaftsleistung.getWert();
        //lblWirtschaftsleistungStand = new JLabel(intWirtschaftsleistung.toString());
        pbWirtschaftsleistung = new JProgressBar(Main.wirtschaftsleistung.getMin(),Main.wirtschaftsleistung.getMax());
        pbWirtschaftsleistung.setValue(intWirtschaftsleistung);
        btWirtschaftHoch = new JButton("+");
        btWirtschaftRunter = new JButton("-");

        btWirtschaftHoch.addActionListener(this);
        btWirtschaftRunter.addActionListener(this);

        lblModernisierungsgrad = new JLabel("Modernisierungsgrad: ");
        intModernisierungsgrad = Main.modernisierungsgrad.getWert();
        //lblModernisierungsgradStand = new JLabel(intModernisierungsgrad.toString()); Alte Version
        pbModernisierungsgrad = new JProgressBar(Main.modernisierungsgrad.getMin(),Main.modernisierungsgrad.getMax());
        pbModernisierungsgrad.setValue(intModernisierungsgrad);
        btModernHoch = new JButton("+");
        btModernRunter = new JButton("-");

        btModernHoch.addActionListener(this);
        btModernRunter.addActionListener(this);

        lblLebensqualität = new JLabel("Lebensqualität: ");
        intLeben = Main.lebensqualität.getWert();
        //lblLebensqualitätStand = new JLabel(intLeben.toString());
        pbLeben = new JProgressBar(Main.lebensqualität.getMin(),Main.lebensqualität.getMax());
        pbLeben.setValue(intLeben);
        btLebenHoch = new JButton("+");
        btLebenRunter = new JButton("-");

        btLebenHoch.addActionListener(this);
        btLebenRunter.addActionListener(this);

        lblBildung = new JLabel("Bildung: ");
        intBildung = Main.bildung.getWert();
        //lblBildungStand = new JLabel(intBildung.toString());
        pbBildung = new JProgressBar(Main.bildung.getMin(),Main.bildung.getMax());
        pbBildung.setValue(intBildung);
        btBildungHoch = new JButton("+");
        btBildungRunter = new JButton("-");

        btPunktezuweisungBestätigen = new JButton("Zuweisung Bestätigen");

        btPunktezuweisungBestätigen.addActionListener(this);
        btBildungHoch.addActionListener(this);
        btBildungRunter.addActionListener(this);

        JLabel lblVerbleibendeRunden = new JLabel("Verbleibende Runden: " + Main.logik.rundenzahl); //System gedribbelt
        intStaatsvermögen = Main.staatsvermögen.getWert();
        lblVerbleibendesStaatskapital = new JLabel("Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
        btPunktezuweisungBestätigen.setBounds(5, 3, 1, 2);
        fenster.add(wertezuweisen);

        wertezuweisen.add(lblWirtschaftsleistung);
        wertezuweisen.add(pbWirtschaftsleistung);
        wertezuweisen.add(btWirtschaftHoch);
        wertezuweisen.add(btWirtschaftRunter);

        wertezuweisen.add(lblModernisierungsgrad);
        wertezuweisen.add(pbModernisierungsgrad);
        wertezuweisen.add(btModernHoch);
        wertezuweisen.add(btModernRunter);

        wertezuweisen.add(lblLebensqualität);
        wertezuweisen.add(pbLeben);
        wertezuweisen.add(btLebenHoch);
        wertezuweisen.add(btLebenRunter);

        wertezuweisen.add(lblBildung);
        wertezuweisen.add(pbBildung);
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

    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     * @param  y    (Beschreibung des Parameters)
     * @return        (Beschreibung des Rückgabewertes)
     */
    public void ladescreen()
    {
        // tragen Sie hier den Code ein
        fenster.setTitle("+**** Jahr wird simuliert *****");

        ladescreen = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//das Programm wird beendet wenn auf X geklickt wird
        ladescreen.setLayout(new GridLayout(2, 1, 20, 20));
        //Integer intJahr = (Main.Logik.rundenzahl- Main.Logik.aktuelleRunde + 1);
        JLabel lblLadescreen= new JLabel("  Ihr " +  (Main.logik.aktuelleRunde + 1) + " Amtsjahr wird Simuliert" );
        //JLabel lblLadescreen= new JLabel("  Fortschritt Ihres Amtsjahres");
        lblLadescreen.setFont(lblLadescreen.getFont().deriveFont(Font.BOLD, 24)); 
        JProgressBar pbwarten = new JProgressBar(0,100);
        
        fenster.add(ladescreen);
        ladescreen.add(lblLadescreen);
        ladescreen.add(pbwarten);
        fenster.setVisible(true);
        for(int i =0; i<=100;i++){
            
            try {
                // Hier wird der Thread in der CPU blockiert, bis der Wert der Variable geändert wird
                //# evtl. Überprüfen!
                Thread.sleep(75);
                pbwarten.setValue(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
        System.out.println("Jahr beendet");
        fenster.setVisible(false);
        ladescreen.setVisible(false);
    }   

    public void gameover()
    {
        // tragen Sie hier den Code ein
        fenster.setTitle("+**** Game over *****");

        gameover = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//das Programm wird beendet wenn auf X geklickt wird
        gameover.setLayout(new GridLayout(2, 2, 20, 20));
        JLabel lblGameover= new JLabel(" Sie haben Ihr Land leider inerhalb Ihrer Amtszeit in eine Kriese geführt ");
        lblGameover.setFont(lblGameover.getFont().deriveFont(Font.BOLD, 26));
        lblGameover.setBounds(1, 1, 1, 2);
        btStart = new JButton (" Neues Spiel Starten");
        btEnde = new JButton(" Spiel Beenden");
        
        fenster.add(gameover);
        gameover.add(lblGameover);
        gameover.add(btStart);
        gameover.add(btEnde);

        fenster.setVisible(true);
        gameover.setVisible(true);
        System.out.println("Game over");

    }
        public void victory()
    {
        // tragen Sie hier den Code ein
        fenster.setTitle("+**** Victory *****");

        victory = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//das Programm wird beendet wenn auf X geklickt wird
        victory.setLayout(new GridLayout(2, 2, 20, 20));
        JLabel lblVictory= new JLabel(" Sie haben Ihr Land Erfolgreich regiert und es weit voran getrieben ");
        lblVictory.setFont(lblVictory.getFont().deriveFont(Font.BOLD, 26));
        lblVictory.setBounds(1, 1, 1, 2);
        btStart = new JButton (" Neues Spiel Starten");
        btEnde = new JButton(" Spiel Beenden");
        
        fenster.add(gameover);
        gameover.add(lblVictory);
        gameover.add(btStart);
        gameover.add(btEnde);

        fenster.setVisible(true);
        victory.setVisible(true);
        System.out.println("Game over");

    }
    //Getter & Setter
    public String getSpielstand() 
    {
        return this.strSpielstand;
    }

    public void setSpielstand(String neuerSpielstand) 
    {
        System.out.println("\n--- Spielstand wird von " + this.strSpielstand + " auf " + neuerSpielstand + " geändert ---\n");
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
            //#try-catch Block evtl. notwendig, um Fehler beierstmaligem Button-Click zu verhindern (victory und gameover existieren inch nicht zu dem Zeitpunkt)
                victory.setVisible(false);
                gameover.setVisible(false);
        } else if (e.getSource() == btEnde) {
            // Aktion für Button 2
            System.out.println("Ende wurde geklickt.");
            System.exit(0);
        } else if (e.getSource() == btModernHoch) {
            //# Prüfe ob wert im Wertebereich wenn nicht ERROR Methode in Logik aufrufen (switch case)
            //# vvvvvvvvvv
            if (Main.modernisierungsgrad.prüfeObImWertebereich(intModernisierungsgrad + 1) == true && intStaatsvermögen - 1 >= 0) {
                intModernisierungsgrad = intModernisierungsgrad + 1;
                intStaatsvermögen = intStaatsvermögen - 1;
                System.out.println(Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung));
                //# ^^^^^^^^^^

                lblVerbleibendesStaatskapital.setText( "Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
                pbModernisierungsgrad.setValue(intModernisierungsgrad);
                System.out.println("Modernisierungsgrad um 1 Punkt hoch");
                btModernRunter.setVisible(true);
            }else {
                System.out.println("Wert nicht im Wertebereich");
            }
        } else if (e.getSource() == btModernRunter) {
            intStaatsvermögen = intStaatsvermögen + 1;
            intModernisierungsgrad = intModernisierungsgrad - 1;
            if(intModernisierungsgrad == Main.logik.startwerteHash.get("Modernisierungsgrad")) {
                btModernRunter.setVisible(false);
            }
            lblVerbleibendesStaatskapital.setText("Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
            pbModernisierungsgrad.setValue(intModernisierungsgrad);

            System.out.println("Modernisierung um 1 Punkt runter");
        } else if (e.getSource() == btWirtschaftHoch) {

            //# Prüfe ob wert im Wertebereich wenn nicht ERROR Methode in Logik aufrufen (switch case)
            //# vvvvvvvvvv
            if(intWirtschaftsleistung < Main.wirtschaftsleistung.getWert()){
                intWirtschaftsleistung = intWirtschaftsleistung + 1;
                intStaatsvermögen = intStaatsvermögen + 1;
                System.out.println(Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung));
                //# ^^^^^^^^^^

                lblVerbleibendesStaatskapital.setText( "Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
                pbWirtschaftsleistung.setValue(intWirtschaftsleistung);
                System.out.println("Wirtschaftleistung um 1 Punkt hoch");
            }else if (Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung + 1) == true && intStaatsvermögen - 1 >= 0) {
                intWirtschaftsleistung = intWirtschaftsleistung + 1;
                intStaatsvermögen = intStaatsvermögen - 1;
                System.out.println(Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung));
                //# ^^^^^^^^^^

                lblVerbleibendesStaatskapital.setText( "Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
                pbWirtschaftsleistung.setValue(intWirtschaftsleistung);
                System.out.println("Wirtschaftleistung um 1 Punkt hoch");
            }else {
                System.out.println("Wert nicht im Wertebereich");
            }
        } else if (e.getSource() == btWirtschaftRunter) {
            //#Prüfen ob investiert werden darf
            //# Prüfe ob wert im Wertebereich wenn nicht ERROR Methode in Logik aufrufen (switch case)
            //# vvvvvvvvvv
            if(intWirtschaftsleistung > Main.wirtschaftsleistung.getWert()){
                intWirtschaftsleistung = intWirtschaftsleistung - 1;
                intStaatsvermögen = intStaatsvermögen + 1;

                System.out.println(Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung));

                //#Prüfe ob wert im Wertebereich wenn nicht ERROR Methode in Logik aufrufen (switch case)
                Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung);//Prüfen ob Wert im Wertebereich 

                lblVerbleibendesStaatskapital.setText( "Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
                pbWirtschaftsleistung.setValue(intWirtschaftsleistung);
                System.out.println("Wirtschaftleistung um 1 Punkt runter");
            }else if (Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung -1) == true && intStaatsvermögen - 1 >= 0){
                intWirtschaftsleistung = intWirtschaftsleistung - 1;
                intStaatsvermögen = intStaatsvermögen - 1;

                System.out.println(Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung));

                //#Prüfe ob wert im Wertebereich wenn nicht ERROR Methode in Logik aufrufen (switch case)
                Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung);//Prüfen ob Wert im Wertebereich 

                lblVerbleibendesStaatskapital.setText( "Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
                pbWirtschaftsleistung.setValue(intWirtschaftsleistung);
                System.out.println("Wirtschaftleistung um 1 Punkt runter");
            }else {
                System.out.println("Wert nicht im Wertebereich");
            }
        } else if (e.getSource() == btLebenHoch) {
            //# Prüfe ob wert im Wertebereich wenn nicht ERROR Methode in Logik aufrufen (switch case)
            //# vvvvvvvvvv
            if (Main.lebensqualität.prüfeObImWertebereich(intLeben + 1) == true && intStaatsvermögen - 1 >= 0) {
                intLeben = intLeben + 1;
                intStaatsvermögen = intStaatsvermögen - 1;
                System.out.println(Main.lebensqualität.prüfeObImWertebereich(intLeben));
                //# ^^^^^^^^^^

                lblVerbleibendesStaatskapital.setText( "Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
                pbLeben.setValue(intLeben);
                System.out.println("Lebensqualität um 1 Punkt hoch");
                btLebenRunter.setVisible(true);
            }else {
                System.out.println(intLeben);
                System.out.println(intStaatsvermögen);
            }

        } else if (e.getSource() == btLebenRunter) {
            intStaatsvermögen = intStaatsvermögen + 1;
            intLeben = intLeben - 1;
            if(intLeben == Main.logik.startwerteHash.get("Lebensqualität")) {
                btLebenRunter.setVisible(false);
            }
            lblVerbleibendesStaatskapital.setText("Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
            pbLeben.setValue(intLeben);
            System.out.println("Lebensqualität um 1 Punkt runter");
            System.out.println(intLeben);
            System.out.println(intStaatsvermögen);

        } else if (e.getSource() == btBildungHoch) {
            //# Prüfe ob wert im Wertebereich wenn nicht ERROR Methode in Logik aufrufen (switch case)
            //# vvvvvvvvvv
            if (Main.bildung.prüfeObImWertebereich(intBildung + 1) == true && intStaatsvermögen - 1 >= 0) {
                intBildung = intBildung + 1;
                intStaatsvermögen = intStaatsvermögen - 1;
                System.out.println(Main.bildung.prüfeObImWertebereich(intBildung));
                //# ^^^^^^^^^^

                lblVerbleibendesStaatskapital.setText( "Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
                pbBildung.setValue(intBildung);
                System.out.println("Bildung um 1 Punkt hoch");
                btBildungRunter.setVisible(true);
            }else {
                System.out.println(intBildung);
                System.out.println(intStaatsvermögen);
            }
        } else if (e.getSource() == btBildungRunter) {
            intStaatsvermögen = intStaatsvermögen + 1;
            intBildung = intBildung - 1;
            if(intBildung == Main.logik.startwerteHash.get("Bildung")) {
                btBildungRunter.setVisible(false);
            }
            lblVerbleibendesStaatskapital.setText("Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
            pbBildung.setValue(intBildung);

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
            Main.bildung.setWert(intBildung);
            Main.wirtschaftsleistung.setWert(intWirtschaftsleistung);
            Main.modernisierungsgrad.setWert(intModernisierungsgrad);
            Main.lebensqualität.setWert(intLeben);
            Main.staatsvermögen.setWert(intStaatsvermögen); // <-- Das hat gefehlt

            setSpielstand("BERECHNUNG");
            spielstandänderung();
            System.out.println("Weiter geklickt");
            wertezuweisen.setVisible(false);
        }
    }
}
