import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.util.Map;
import java.util.*;


/**
 * Beschreiben Sie hier die Klasse GUI.
 * 
 * @author Malte Fischer
 * @version 0.1
 */
public class GUI extends JFrame implements ActionListener {
    public GUI() {
        fenster = new JFrame(); // erstellen eines Fenster
    }
    // Instanzvariablen 
    private JFrame fenster;
    private JPanel startBildschirm, wertezuweisen, auswahlDatei, startwerte, ladescreen, gameover, victory, lastActivePanel, namenEintragen, graph, tabellenPanel;
    public JComboBox<String> cbDateien, cbAuswahl;
    private JProgressBar pbLeben, pbWirtschaftsleistung, pbModernisierungsgrad, pbBildung, pbPolitStab, pbUmwelt, pbVersorgung, pbBevökerungswachstum, pbBevökerungsgröße, Bevölkerungswachstumsfaktor;
    public String strSpielstand, strAuswahl;
    private Integer intWirtschaftsleistung, intModernisierungsgrad, intLeben, intBildung, intStaatsvermögen, intVerbleibendeRunden;
    private String spielstand; 
    private JLabel lblwilkommen,lblVerlauf;
    private JButton btStart, btEnde, btAuswahlBestätigen, btweiter, btPunktezuweisungBestätigen, btHauptmenü, btNamenbestätigen, btVerlaufanzeigen;
    private JLabel lblWirtschaftsleistung, lblModernisierungsgrad, lblLebensqualität, lblBildung, lblVerbleibendesStaatskapital;
    private JLabel lblWirtschaftsleistungStand, lblModernisierungsgradStand,lblLebensqualitätStand, lblBildungStand;
    private JButton btWirtschaftHoch, btWirtschaftRunter, btModernHoch, btModernRunter, btLebenHoch, btLebenRunter, btBildungHoch, btBildungRunter;
    private JButton btVerlauf;
    private JTextField txtName;
    
    
    /**
     * Die Methode spielstandänderunglegt organisiert anhand einer Variablen den aktuellen Spielstand
     * Es wird je nach Spielstand eine Methode aufgerufen, welche den für den jeweiligen Spielstand die 
     * Visualisierung übernimmt.
     * 
     */
    public void spielstandänderung()
    {
        // wenn diese Methode aufgerufen wird und der Wert zu einem der Case passt,
        // dann wird der zugehöhrige Code ausgeführt
        switch (strSpielstand) {
            case "START" :// wenn strSpielstand = "START" dann
                startBildschirm();// wird die Methode für den Startbildschirm aufgerufen
                break;
            case "SPIELERNAME" :
                spielername();
                break;
            case "AUSWAHL" :
                dateiAuswal();
                break;
            case "BREAK" :
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
            case "NEUSTART" :
                Main.logik.neustarten(); // es wird in der Klasse Logik eine Methode für den Neustart aufgerufen
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
        fenster.setTitle("Startbildschirm");
        startBildschirm = new JPanel();
        // Das Fenster wird geschlossen wenn das "X" geklickt wird
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Erzeuge eine 3x1 Matrix mit einem Abstand von 30 Pixeln
        startBildschirm.setLayout(new GridLayout(3, 1, 30, 30));

        lastActivePanel = startBildschirm;
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
        //startBildschirm.setSize(400, 400);
        fenster.setSize(400,400);

        // Zentriert das JFrame auf dem Bildschirm
        fenster.setLocationRelativeTo(null);

        //setzt die Sichtbarkeit auf true
        fenster.setVisible(true);
        startBildschirm.setVisible(true);
    }

    /**
     * Die Methode erstellt ein Fenster in welchem der Spieler seinen Spielernamen in ein Textfeld schreib
     * Dieser Name wird für den Namen der Ausgabedatei verwendet.
     * Der Name wird beim klicken des Bestätigungs Button überprüft und nur bei übereinstimmung mit dem 
     * vorgeschriebenen Format akzeptiert.
     * 
     */
    private void spielername()
    {
        // Erstellt ein neues Fenster mit dem Titel "Spielernamen eintragen" 
        // und ein neues JPanel "namenEintragen"
        fenster.setTitle("Spielernamen eintragen");
        namenEintragen = new JPanel();
        // Das Fenster wird geschlossen wenn das "X" geklickt wird
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Erzeuge eine 3x1 Matrix mit einem Abstand von 30 Pixeln
        namenEintragen.setLayout(new GridLayout(3, 1, 30, 30));

        // Label & Button erstellen 
        JLabel lblNamenEintragen = new JLabel("Bitte tragen Sie den Namen ein unter dem Sie regieren wollen");
        btNamenbestätigen = new JButton("Eingabe bestätigen");
        txtName = new JTextField();

        //fügt einen ActionListener hinzu um auf einen klick zu reagieren
        btNamenbestätigen.addActionListener(this);

        //Fügt das JLabel und die beiden JButtons zum JPanel hinzu
        namenEintragen.add(lblNamenEintragen);
        namenEintragen.add(txtName);
        namenEintragen.add(btNamenbestätigen);

        //Fügt das JPanel zum JFrame hinzu
        fenster.add(namenEintragen);

        //legt die Größe des Frames fest
        fenster.setSize(700,700);

        // Zentriert das JFrame auf dem Bildschirm
        fenster.setLocationRelativeTo(null);

        //setzt die Sichtbarkeit auf true
        fenster.setVisible(true);
        namenEintragen.setVisible(true);
    }

    /**
     * Die Methode erstellt eine Eingabemaske auf der über eine ComboBox die zu spielende Datei 
     * ausgewählt werden kann welches Land gespielt werden soll und legt somit die auszulesende Datei fest
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
     * Die Methode zeigt dem Spieler die Startwerte seiner Simulation und zeigt den Werte bereich der einzelnen 
     * Sektoren in einer ProgressBar an. Es wird zudem überprüft, ob es sich um die erste Runde handelt
     * 
     * 
     */
    public void startwerte()
    {
        fenster.setTitle("Startwerte der Simulation"); // der Name des Fensters wird angepasst

        startwerte = new JPanel();// neues Panel wird erstellt
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//das Fenster wird beendet wenn auf X geklickt wird
        //Erzeuge eine 14x2 Matrix mit einem Abstand von 10 Pixeln 
        startwerte.setLayout(new GridLayout(14, 2, 10, 10));
        //es werden Label erstellt
        JLabel lblAusgangslage = new JLabel();
        JLabel lblRundenzahl = new JLabel();
        //Es wird überprüft, ob es sich um die erste Runde handelt 
        if(Main.logik.aktuelleRunde == 1) {
            // in der ersten Runde werden die Label nach den Startwerten benannt
            lblAusgangslage.setText("***** Ausgangslage *****");
            lblRundenzahl.setText("Insgesamt zu spielende Runden : " + Main.logik.rundenzahl);
        } else {
            // ab der zweiten Runde werden die Label nach den Runden benannt
            lblAusgangslage.setText("***** Werte nach Runde " + Main.logik.aktuelleRunde + " *****");
            lblRundenzahl.setText("Ergebnis nach = " + (Main.logik.aktuelleRunde - 1)+ ". Runden");
        }

        // es werden Label erstellt in die die Namen der Sektoren mit den zugehöhrigen Wert geschrieben wird
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
        
        // es werden ProgressBars erstellt in den aktuellen Wert im Wertebereich veranschaulicht 
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
        
        //erstellt einen Button
        btweiter = new JButton ("Weiter");
        // fügt das Panel startwerte dem Frame hinzu
        fenster.add(startwerte);
        // dem Button wird ein ActionListener hinzugefügt, der beim klicken reagiert. 
        btweiter.addActionListener(this);
        
        // Dem Panel werden die zuvor erstellten Label und ProgressBars hinzugefügt
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

        // Die Größe des Fensters wird auf 600/ 800 angepasst
        fenster.setSize(600,800);
        // das Fenster wird in die mitte des Bildschirm
        fenster.setLocationRelativeTo(null);
        // die Sichtbarkeit des Fensters und des Panels wird auf true gesetzt
        fenster.setVisible(true);
        startwerte.setVisible(true);
    }

    /**
     * Die Methode wertezuweisen ermöglicht das investieren des Staatsvermögen in die 
     * Sektoren: Wirtschaftsleistung, Modernisierungsgrad, Lebensqualität und Bildung. 
     * Es wird dabei in einer ProgressBar der Wert im Wertebereich angezeigt und nach betätigung des 
     * Buttons werden die neuen Werte in den Main Variablen gespeichert
     * 
     */
    private void wertezuweisung()
    {
        // erstellte
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

        JLabel lblVerbleibendeRunden = new JLabel("Verbleibende Runden: " + (Main.logik.rundenzahl - Main.logik.aktuelleRunde +1)); //System gedribbelt
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
        fenster.setTitle("***** Jahr wird simuliert *****");

        ladescreen = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//das Programm wird beendet wenn auf X geklickt wird
        ladescreen.setLayout(new GridLayout(2, 1, 20, 20));
        //Integer intJahr = (Main.Logik.rundenzahl- Main.Logik.aktuelleRunde + 1);
        JLabel lblLadescreen= new JLabel("  Ihr " +  (Main.logik.aktuelleRunde ) + ". Amtsjahr wird Simuliert" );
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
                Thread.sleep(15);
                pbwarten.setValue(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Jahr beendet");
        fenster.setVisible(false);
        ladescreen.setVisible(false);

        setSpielstand("BERECHNUNG");
        spielstandänderung();
    }   

    public void gameover()
    {
        // tragen Sie hier den Code ein
        fenster.setTitle("***** Game over *****");

        gameover = new JPanel();
        gameover.setLayout(new GridLayout(4, 1, 50, 50));
        lastActivePanel = gameover;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//das Programm wird beendet wenn auf X geklickt wird

        JLabel lblGameover= new JLabel(" Sie haben Ihr Land leider inerhalb Ihrer Amtszeit in eine Krise geführt ");
        lblGameover.setFont(lblGameover.getFont().deriveFont(Font.BOLD, 20));
        btHauptmenü = new JButton("Zurück zum Hauptmenü"); 
        btVerlauf = new JButton("Verlauf der Werte Anzeigen");

        //btStart = new JButton (" Neues Spiel Starten");
        btEnde = new JButton("Spiel Beenden");

        //fügt einen ActionListener hinzu um auf einen klick zu reagieren
        btHauptmenü.addActionListener(this);
        btEnde.addActionListener(this);
        btVerlauf.addActionListener(this);

        fenster.add(gameover);

        gameover.add(lblGameover);
        gameover.add(btVerlauf);
        gameover.add(btHauptmenü);
        gameover.add(btEnde);

        fenster.setSize(800,800);
        fenster.setLocationRelativeTo(null);

        fenster.setVisible(true);
        gameover.setVisible(true);
        System.out.println("Game over");

    }

    public void victory()
    {
        // tragen Sie hier den Code ein
        fenster.setTitle("***** Victory *****");

        victory = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//das Programm wird beendet wenn auf X geklickt wird
        victory.setLayout(new GridLayout(5, 1, 50, 50));
        lastActivePanel = victory;
        
        JLabel lblVictory= new JLabel(" Sie haben Ihr Land Erfolgreich regiert und es weit voran getrieben ");
        JLabel lblErgebnis= new JLabel("Simulationserfolg: " + Main.logik.simulationsErfolg.get(Main.logik.aktuelleRunde -1 ));
        lblErgebnis.setFont(lblVictory.getFont().deriveFont(Font.BOLD, 15));

        lblVictory.setFont(lblVictory.getFont().deriveFont(Font.BOLD, 20));
        btHauptmenü = new JButton("Zurück zum Hauptmenü"); 
        btEnde = new JButton("Spiel Beenden");
        btVerlauf = new JButton("Verlauf der Werte Anzeigen");
        
        //fügt einen ActionListener hinzu um auf einen klick zu reagieren
        
        btEnde.addActionListener(this);
        btVerlauf.addActionListener(this);
        btHauptmenü.addActionListener(this);

        fenster.add(victory);
        victory.add(lblVictory);
        victory.add(lblErgebnis);
        victory.add(btVerlauf);
        victory.add(btHauptmenü);
        victory.add(btEnde);

        fenster.setSize(800,800);
        fenster.setLocationRelativeTo(null);

        fenster.setVisible(true);
        victory.setVisible(true);
        System.out.println("Victory");

    }

    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     * @param  y	(Beschreibung des Parameters)
     * @return		(Beschreibung des Rückgabewertes)
     */
    public void graphausgabe()
    {
        // einblenden eines graphen 
        // tragen Sie hier den Code ein
        fenster.setTitle("***** Verlauf *****");

        graph = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//das Programm wird beendet wenn auf X geklickt wird
        graph.setLayout(new GridLayout(4, 1, 50, 50));
        
        lastActivePanel = graph;
        btVerlauf.setText("Zurück zu Übersicht");
        lblVerlauf= new JLabel("Anzeige des Verlaufs des Auswahl");
        cbAuswahl = new JComboBox<String>();
        
        cbAuswahl.addActionListener(this);

        //Fügt Werte in die ComboBox
        cbAuswahl.addItem("Bildung");
        cbAuswahl.addItem("Lebensqualität");
        cbAuswahl.addItem("Bevölkerungsgröße");
        cbAuswahl.addItem("Bevölkerungswachstum");
        cbAuswahl.addItem("Wirtschaftsleistung");
        cbAuswahl.addItem("Modernisierungsgrad");
        cbAuswahl.addItem("Staatsvermögen");
        cbAuswahl.addItem("Bevölkerungswachstumsfaktor");
        cbAuswahl.addItem("Versorgungslage");
        

        cbAuswahl.addActionListener(this);
        
        tabellenPanel = new JPanel();
        erzeugeTabelle(Main.logik.simulationsErfolg);

        fenster.add(graph);
        graph.add(lblVerlauf);
        graph.add(tabellenPanel, 1);
        graph.add(cbAuswahl);
        graph.add(btVerlauf);
        
        fenster.setSize(800,800);
        fenster.setLocationRelativeTo(null);

        fenster.setVisible(true);
        tabellenPanel.setVisible(true);
        graph.setVisible(true);
        System.out.println("Verlauf wird angezeigt");

    }

    /**
     * Gibt ein Pop-Up Fenster aus mit einer beliebigen (Fehler-)Meldung
     * 
     * @param text Text, der angezeigt werden soll.
     */
    public void popUpAusgeben(String text)
    {
        JOptionPane.showMessageDialog(null, text);
    }

    /**
     * 
     */
    public void erzeugeTabelle(HashMap<Integer, Integer> hashMap) {
        //SwingUtilities.invokeLater(() -> 
        //{
            
            //Tabelle aus HashMap erzeugen
            JTable table = createJTableFromHashMap(hashMap);
            
            //Tabelle dem Panel hinzufügen
            tabellenPanel.add(new JScrollPane(table));
            graph.add(tabellenPanel);
            tabellenPanel.setVisible(true);
        //}
        //);
    }

    /**
     * 
     */
    public JTable createJTableFromHashMap(HashMap<Integer, Integer> hashMap) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Schlüssel");
        tableModel.addColumn("Wert");

        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            tableModel.addRow(new Object[]{key, value});
        }

        return new JTable(tableModel);
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
            lastActivePanel.setVisible(false);
            setSpielstand("SPIELERNAME");
            spielstandänderung();
        } else if (e.getSource() == btEnde) {
            // Aktion für Button 2
            System.out.println("Ende wurde geklickt.");
            Main.boolNeustarten = false;
            System.exit(0);
        } else if (e.getSource() == btModernHoch) {
            
            if (Main.modernisierungsgrad.prüfeObImWertebereich(intModernisierungsgrad + 1) == true && intStaatsvermögen - 1 >= 0) {
                intModernisierungsgrad = intModernisierungsgrad + 1;
                intStaatsvermögen = intStaatsvermögen - 1;
                System.out.println(Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung));
 

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
            if(intModernisierungsgrad ==  Main.modernisierungsgrad.getWert()) {
                btModernRunter.setVisible(false);
            }
            lblVerbleibendesStaatskapital.setText("Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
            pbModernisierungsgrad.setValue(intModernisierungsgrad);

            System.out.println("Modernisierung um 1 Punkt runter");
        } else if (e.getSource() == btWirtschaftHoch) {

            if(intWirtschaftsleistung < Main.wirtschaftsleistung.getWert()){
                intWirtschaftsleistung = intWirtschaftsleistung + 1;
                intStaatsvermögen = intStaatsvermögen + 1;
                System.out.println(Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung));
 
                lblVerbleibendesStaatskapital.setText( "Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
                pbWirtschaftsleistung.setValue(intWirtschaftsleistung);
                System.out.println("Wirtschaftleistung um 1 Punkt hoch");
            }else if (Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung + 1) == true && intStaatsvermögen - 1 >= 0) {
                intWirtschaftsleistung = intWirtschaftsleistung + 1;
                intStaatsvermögen = intStaatsvermögen - 1;
                System.out.println(Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung));
 

                lblVerbleibendesStaatskapital.setText( "Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
                pbWirtschaftsleistung.setValue(intWirtschaftsleistung);
                System.out.println("Wirtschaftleistung um 1 Punkt hoch");
            }else {
                System.out.println("Wert nicht im Wertebereich");
            }
        } else if (e.getSource() == btWirtschaftRunter) {
            
            if(intWirtschaftsleistung > Main.wirtschaftsleistung.getWert()){
                intWirtschaftsleistung = intWirtschaftsleistung - 1;
                intStaatsvermögen = intStaatsvermögen + 1;

                System.out.println(Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung));

                Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung);//Prüfen ob Wert im Wertebereich 

                lblVerbleibendesStaatskapital.setText( "Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
                pbWirtschaftsleistung.setValue(intWirtschaftsleistung);
                System.out.println("Wirtschaftleistung um 1 Punkt runter");
            }else if (Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung -1) == true && intStaatsvermögen - 1 >= 0){
                intWirtschaftsleistung = intWirtschaftsleistung - 1;
                intStaatsvermögen = intStaatsvermögen - 1;

                System.out.println(Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung));

                Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung);//Prüfen ob Wert im Wertebereich 

                lblVerbleibendesStaatskapital.setText( "Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
                pbWirtschaftsleistung.setValue(intWirtschaftsleistung);
                System.out.println("Wirtschaftleistung um 1 Punkt runter");
            }else {
                System.out.println("Wert nicht im Wertebereich");
            }
        } else if (e.getSource() == btLebenHoch) {
            
            if (Main.lebensqualität.prüfeObImWertebereich(intLeben + 1) == true && intStaatsvermögen - 1 >= 0) {
                intLeben = intLeben + 1;
                intStaatsvermögen = intStaatsvermögen - 1;
                System.out.println(Main.lebensqualität.prüfeObImWertebereich(intLeben));
         

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
            if(intLeben == Main.lebensqualität.getWert()) {
                btLebenRunter.setVisible(false);
            }
            lblVerbleibendesStaatskapital.setText("Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
            pbLeben.setValue(intLeben);
            System.out.println("Lebensqualität um 1 Punkt runter");
            System.out.println(intLeben);
            System.out.println(intStaatsvermögen);

        } else if (e.getSource() == btBildungHoch) {
   
            if (Main.bildung.prüfeObImWertebereich(intBildung + 1) == true && intStaatsvermögen - 1 >= 0) {
                intBildung = intBildung + 1;
                intStaatsvermögen = intStaatsvermögen - 1;
                System.out.println(Main.bildung.prüfeObImWertebereich(intBildung));
               

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
            if(intBildung ==  Main.bildung.getWert()) {
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
        }   else if (e.getSource() == btHauptmenü) {
            lastActivePanel.setVisible(false);
            fenster.dispose();

            setSpielstand("NEUSTART");
            spielstandänderung();

        }   else if (e.getSource() == btNamenbestätigen) {
            String eingabe = txtName.getText();
            if (eingabe.matches(".*[\\\\/:*?\"<>|].*")) {
                // Eingabe enthält ungültige Zeichen
                System.out.println("Ungültige Zeichen in der Eingabe.");
                JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Namen ohne Sonderzeichen ein");
                txtName.setText("");
                // Hier kannst du eine Fehlermeldung anzeigen oder weitere Aktionen ausführen
            } else {
                // Wenn die Eingabe gültig ist:
                System.out.println("Eingabe gültig: " + eingabe);
                // ausblenden des Aktiven panel
                Main.logik.spielername = eingabe;
                namenEintragen.setVisible(false);
                
                //Spielstand wird auf Auswahl gestzt
                setSpielstand("AUSWAHL");
                spielstandänderung();
            }

        }   else if (e.getSource() == btVerlauf) {
            if (lastActivePanel == victory) {
                lastActivePanel.setVisible(false);
                graphausgabe();
            }else if(lastActivePanel == gameover){
                lastActivePanel.setVisible(false);
                graphausgabe();
            }else{
                lastActivePanel.setVisible(false);
                graphausgabe();
            }
        }   else if (e.getSource() == cbAuswahl) {
            lblVerlauf.setText("Anzeigen von " + cbAuswahl.getSelectedItem());
            String str = cbAuswahl.getSelectedItem().toString();
            try {
                tabellenPanel.removeAll();
            } catch (NullPointerException ex) {}
            switch (str) {
                case "Bildung":
                    erzeugeTabelle(Main.bildung.werte);
                    break;
                
                case "Lebensqualität":
                    erzeugeTabelle(Main.lebensqualität.werte);
                    break;
                
                case "Bevölkerungsgröße":
                    erzeugeTabelle(Main.bevölkerungsgröße.werte);
                    break;
                    
                case "Bevölkerungswachstum":
                    erzeugeTabelle(Main.bevölkerungswachstum.werte);
                    break;
                
                case "Bevölkerungswachstumsfaktor":
                    erzeugeTabelle(Main.bevölkerungswachstumsfaktor.werte);
                    break;
                
                case "Wirtschaftsleistung":
                    erzeugeTabelle(Main.wirtschaftsleistung.werte);
                    break;
                
                case "Modernisierungsgrad":
                    erzeugeTabelle(Main.modernisierungsgrad.werte);
                    break;
                
                case "Versorgungslage":
                    erzeugeTabelle(Main.versorgungslage.werte);
                    break;
                
                case "Staatsvermögen":
                    erzeugeTabelle(Main.staatsvermögen.werte);
                    break;
                
                case "Politische Stabilität":
                    erzeugeTabelle(Main.politische_stabilität.werte);
                    break;
                
                case "Umweltverschmutzung":
                    erzeugeTabelle(Main.umweltverschmutzung.werte);
                    break;
                
                case "Simulationserfolg":
                    erzeugeTabelle(Main.logik.simulationsErfolg);
                    break;
            }
            tabellenPanel.repaint();
            graph.remove(tabellenPanel);
            graph.add(tabellenPanel, 1);
            tabellenPanel.setVisible(true);
        }
    }
}
