import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.util.Map;
import java.util.*;
/**
 * Beschreiben Sie hier die Klasse GUI. 
 * @author Malte Fischer
 * @version 3.0
 */
public class GUI extends JFrame implements ActionListener {
    public GUI() {
        fenster = new JFrame(); // erstellen eines Fenster
        // Quellen:
        //https://stackoverflow.com/questions/13207519/adding-a-new-windowlistener-to-a-jframe
        //https://docs.oracle.com/javase/8/docs/api/java/awt/event/WindowListener.html
        fenster.addWindowListener(new WindowAdapter()
        {
            @Override
            // es wird beim Klicken auf X das Programm Beendet
            public void windowClosing(WindowEvent e) {
                // Ausgabe über Beenden des Spiel
                System.out.println("Das Programm wurde beendet");

                // Das Programm beenden
                System.exit(0);
            }
            }
        ); 
    }
        
    // Instanzvariablen 
    private JFrame fenster;
    private JPanel startBildschirm, wertezuweisen, auswahlDatei, startwerte, ladescreen, gameover, victory, lastActivePanel, namenEintragen, graph, tabellenPanel;
    public JComboBox<String> cbDateien, cbAuswahl;
    private JProgressBar pbLeben, pbWirtschaftsleistung, pbModernisierungsgrad, pbBildung, pbPolitStab, pbUmwelt, pbVersorgung, pbBevökerungswachstum, pbBevökerungsgröße, Bevölkerungswachstumsfaktor;
    public String strSpielstand, strAuswahl, str;
    private Integer intWirtschaftsleistung, intModernisierungsgrad, intLeben, intBildung, intStaatsvermögen, intVerbleibendeRunden;
    private String spielstand; 
    private JLabel lblwilkommen,lblVerlauf;
    private JButton btStart, btEnde, btAuswahlBestätigen, btweiter, btPunktezuweisungBestätigen, btHauptmenü, btNamenbestätigen, btVerlaufanzeigen;
    private JLabel lblWirtschaftsleistung, lblModernisierungsgrad, lblLebensqualität, lblBildung, lblVerbleibendesStaatskapital;
    private JButton btWirtschaftHoch, btWirtschaftRunter, btModernHoch, btModernRunter, btLebenHoch, btLebenRunter, btBildungHoch, btBildungRunter;
    private JButton btVerlauf;
    private JTextField txtName;
    private Boolean boAbschluss; 
    
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
                break;
            default : //falls Spielstand falsch angegeben wird, oder nicht erkannt wird
                System.out.println("! '" + strSpielstand + "' ist kein gültiger Spielstand !");
        }
    }
    
    /**
     * Die Methode erstellt den Startbildschirm und gibt die Wahl zwischen Starten und Beenden 
     * des Programmes
     * Quelle für html und br in Swing Components
     * https://docs.oracle.com/javase/tutorial/uiswing/components/html.html
    */
    private void startBildschirm()
    {
        // Erstellt ein neues Fenster mit dem Titel "Start-End Frame" 
        // und ein neues JPanel "startBildschirm"
        fenster.setTitle("Startbildschirm");
        // erstellt ein extra Panel was für die Plazierung der Buttons dient
        JPanel buttons = new JPanel ();
        //Erzeuge eine 2x1 Matrix mit einem Abstand von 30 Pixeln
        buttons.setLayout(new GridLayout(2, 1, 30, 30));
    
        startBildschirm = new JPanel();
        //Erzeuge eine 2x1 Matrix mit einem Abstand von 30 Pixeln
        startBildschirm.setLayout(new GridLayout(2, 1, 30, 30));

        lastActivePanel = startBildschirm;
        // Label & Button erstellen 
        // html im Label ermöglicht, dass der Text bearbeitet werden kann. br erstellt einen Absatz im Text
        lblwilkommen = new JLabel("<html>Willkommen bei Ökolopoly!<br> <br>In Diesem Spiel sind Sie für Ihre Rundenanzahl als Staatsoberhaubt eingesetzt und können Ihr Staatskapital in die Sektoren ihres Landes investieren. <br> <br> Investieren Sie weise, da ein Verlassen des Wertebereichs eines Sektors das Scheitern Ihrer Amtszeit bedeutet." );
        //Setst die Schriftgröße auf 15
        lblwilkommen.setFont(lblwilkommen.getFont().deriveFont(Font.BOLD, 15));
        btStart = new JButton("Start");
        btEnde = new JButton("Ende");
        
        //fügt einen ActionListener hinzu um auf einen klick zu reagieren
        btStart.addActionListener(this);
        btEnde.addActionListener(this);

        //Fügt das JLabel und die beiden JButtons dem jeweligen JPanel hinzu
        startBildschirm.add(lblwilkommen);
        startBildschirm.add(buttons);
        buttons.add(btStart);
        buttons.add(btEnde);

        fenster.add(startBildschirm); //Fügt das JPanel zum JFrame hinzu
        
        fenster.setSize(700,700); //legt die Größe des Frames fest

        fenster.setLocationRelativeTo(null); // Zentriert das JFrame auf dem Bildschirm

        //setzt die Sichtbarkeit auf true
        fenster.setVisible(true);
        startBildschirm.setVisible(true);
        buttons.setVisible(true);
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
        fenster.setTitle("Spielernamen eintragen"); // Ändert den Titel des Fensters aus dem konstruktor
        namenEintragen = new JPanel(); //erstellt ein neues JPanel "namenEintragen"
        namenEintragen.setLayout(new GridLayout(3, 1, 30, 30));//Erzeuge eine 3x1 Matrix mit einem Abstand von 30 Pixeln

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

        fenster.add(namenEintragen); //Fügt das JPanel zum JFrame hinzu

        fenster.setSize(700,700); //legt die Größe des Frames fest
        
        fenster.setLocationRelativeTo(null); // Zentriert das JFrame auf dem Bildschirm

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
    private void dateiAuswal()
    {
        fenster.setTitle("zu Simulierendes Land auswählen");
        auswahlDatei = new JPanel();
        auswahlDatei.setLayout(new GridLayout(3, 2, 40, 40));

        JLabel lblAuswahl = new JLabel("Wählen Sie das zu Spielende Land aus");
        btAuswahlBestätigen = new JButton("Auswahl bestätigen");
        cbDateien = new JComboBox<String>();

        //Fügt Werte in die ComboBox
        for (String datei : Main.dateiLeser.dateienFinden()){
            cbDateien.addItem(datei);
        }
        
        //fügt einen ActionListener hinzu um auf einen klick zu reagieren
        btAuswahlBestätigen.addActionListener(this);

        //Fügt das JLabel und die beiden JButtons zum JPanel hinzu
        auswahlDatei.add(lblAuswahl);
        auswahlDatei.add(cbDateien); 
        auswahlDatei.add(btAuswahlBestätigen);

        fenster.add(auswahlDatei); //Fügt das JPanel zum JFrame hinzu

        fenster.setSize(400,400);//legt die Größe des Frames fest
        
        fenster.setLocationRelativeTo(null); // Zentriert das JFrame auf dem Bildschirm

        //setzt die Sichtbarkeit auf true
        fenster.setVisible(true);
        auswahlDatei.setVisible(true);
    }

    /**
     * Die Methode zeigt dem Spieler die Startwerte seiner Simulation und zeigt den Werte bereich der einzelnen 
     * Sektoren in einer ProgressBar an. Es wird zudem überprüft, ob es sich um die erste Runde handelt
     * 
     */
    private void startwerte()
    {
        fenster.setTitle("Startwerte der Simulation"); // der Name des Fensters wird angepasst

        startwerte = new JPanel();// neues Panel wird erstellt
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
        fenster.setTitle("Werte zuweisen"); // setzt den Titel des Fensters neu
        
        wertezuweisen = new JPanel(); // erstelle ein neues Panel
        wertezuweisen.setLayout(new GridLayout(5, 4, 10, 10)); //Erzeuge eine 5x4 Matrix mit einem Abstand von 10 Pixeln 
        
        // es werden die Visualisierungen zum investeiren in Wirtschaftsleistung erstellt
        lblWirtschaftsleistung = new JLabel("Wirtschaftsleistung: ");
        intWirtschaftsleistung = Main.wirtschaftsleistung.getWert();
        pbWirtschaftsleistung = new JProgressBar(Main.wirtschaftsleistung.getMin(),Main.wirtschaftsleistung.getMax());
        pbWirtschaftsleistung.setValue(intWirtschaftsleistung);

        btWirtschaftHoch = new JButton("+");
        btWirtschaftRunter = new JButton("-");
        
        //fügt den investitions Button ActionListener hinzu
        btWirtschaftHoch.addActionListener(this);
        btWirtschaftRunter.addActionListener(this);
        
        // es werden die Visualisierungen zum investeiren in Modernisierungsgrad erstellt
        lblModernisierungsgrad = new JLabel("Modernisierungsgrad: ");
        intModernisierungsgrad = Main.modernisierungsgrad.getWert();
        pbModernisierungsgrad = new JProgressBar(Main.modernisierungsgrad.getMin(),Main.modernisierungsgrad.getMax());
        pbModernisierungsgrad.setValue(intModernisierungsgrad);
        btModernHoch = new JButton("+");
        btModernRunter = new JButton("-");
        
        //fügt den investitions Button ActionListener hinzu
        btModernHoch.addActionListener(this);
        btModernRunter.addActionListener(this);

        // es werden die Visualisierungen zum investeiren in Modernisierungsgrad erstellt
        lblLebensqualität = new JLabel("Lebensqualität: ");
        intLeben = Main.lebensqualität.getWert();
        pbLeben = new JProgressBar(Main.lebensqualität.getMin(),Main.lebensqualität.getMax());
        pbLeben.setValue(intLeben);
        btLebenHoch = new JButton("+");
        btLebenRunter = new JButton("-");

        //fügt den investitions Button ActionListener hinzu        
        btLebenHoch.addActionListener(this);
        btLebenRunter.addActionListener(this);

        // es werden die Visualisierungen zum investeiren in Modernisierungsgrad erstellt
        lblBildung = new JLabel("Bildung: ");
        intBildung = Main.bildung.getWert();
        pbBildung = new JProgressBar(Main.bildung.getMin(),Main.bildung.getMax());
        pbBildung.setValue(intBildung);
        btBildungHoch = new JButton("+");
        btBildungRunter = new JButton("-");
        btPunktezuweisungBestätigen = new JButton("Zuweisung Bestätigen");

        //fügt den investitions Button ActionListener hinzu
        btPunktezuweisungBestätigen.addActionListener(this);
        btBildungHoch.addActionListener(this);
        btBildungRunter.addActionListener(this);

        //Fügt die Label für das Anzeigen der Verbleibenden runden und dem Staatskapital hinzu
        JLabel lblVerbleibendeRunden = new JLabel("Verbleibende Runden: " + (Main.logik.rundenzahl - Main.logik.aktuelleRunde +1)); //System gedribbelt
        intStaatsvermögen = Main.staatsvermögen.getWert();
        lblVerbleibendesStaatskapital = new JLabel("Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
        btPunktezuweisungBestätigen.setBounds(5, 3, 1, 2);
        fenster.add(wertezuweisen);

        // Es werden alle zuvor erstellten Label, Button und ProgressBars dem Panel hinzugefügt
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

        //es werden die Sichtbarkeiten angepasst 
        fenster.setVisible(true);
        wertezuweisen.setVisible(true);
        btBildungRunter.setVisible(false);
        btLebenRunter.setVisible(false);
        btModernRunter.setVisible(false);
        
        fenster.pack(); //das Fenster wird auf die notwendige Größe angepasst
        fenster.setLocationRelativeTo(null);// das Fenster wird in die mitte des Bildschirm
    }

    /**
     * Diese Methode dient der Visualisierung einer Runde. Es wird ein Ladescreen simuliert der über
     * eine Zeitverzögerung gesteuert wird
     * 
     * Quelle Thread.sleep():
     * https://docs.oracle.com/javase/tutorial/essential/concurrency/sleep.html
     */
    private void ladescreen()
    {
        fenster.setTitle("***** Jahr wird simuliert *****"); // setzt den Titel des Fensters neu
        
        ladescreen = new JPanel();// erstellt ein neues Panel 
        ladescreen.setLayout(new GridLayout(2, 1, 20, 20)); //Erzeuge eine 2x1 Matrix mit einem Abstand von 20 Pixeln 
        
        JLabel lblLadescreen= new JLabel("  Ihr " +  (Main.logik.aktuelleRunde ) + ". Amtsjahr wird Simuliert" );// erstellt ein Label 
        
        lblLadescreen.setFont(lblLadescreen.getFont().deriveFont(Font.BOLD, 24)); //Die schriftgröße des Labels wird angepasst
        
        JProgressBar pbwarten = new JProgressBar(0,100); //erstellt eine ProgressBar mit einem Werteberich von 0-100
        
        // dem Fenster werden das Panel, das Label und die ProgressBar hinzugefügt
        fenster.add(ladescreen);
        ladescreen.add(lblLadescreen);
        ladescreen.add(pbwarten);
        
        fenster.setVisible(true); //das Fenster wird wieder Sichtbar
        
        //Die Schleife simuliert das Durchlaufen des Programmes und verzögert
        for(int i =0; i<=100;i++){
            try {
                Thread.sleep(7); // das Programm wird bei jeder Zahl zwichen 0-100 für () ms pausiert
                pbwarten.setValue(i);// der Aktuelle werde von i wird in der ProgressBar angezeigt
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Jahr beendet");// es gibt ein Update über die Konsole
        // sets die Sichtbarkeit auf false
        fenster.setVisible(false);
        ladescreen.setVisible(false);
        
        //der Spielstand wird auf Berechnung gesetzt und die Berechnung ausgeführt
        setSpielstand("BERECHNUNG");
        spielstandänderung();
    }   

    /**
     * Diese Methode dient der Visualisierung wenn die Simulation gescheitert ist.
     * Es wird ein neues Panel dem Fenster hinzugefügt in dem es die Möglichkeit gibt sich das Ergebnis
     * anziegen zu lassen, das Spiel neu zu starten, oder es zu beenden
     * 
     * 
     */
    private void gameover()
    {
        fenster.setTitle("***** Game over *****"); // setzt den Titel des Fensters neu 
        gameover = new JPanel();// es wird ein neues Panel erstellt        
        gameover.setLayout(new GridLayout(4, 1, 50, 50));//Erzeuge eine 4x1 Matrix mit einem Abstand von 50 Pixeln 
        
        lastActivePanel = gameover; //weist einer variablen das Panel gameover zu
        
        // es werden Label und Button erstellt 
        JLabel lblGameover= new JLabel(" Sie haben Ihr Land leider innerhalb Ihrer Amtszeit in eine Krise geführt ");
        btHauptmenü = new JButton("Zurück zum Hauptmenü"); 
        btVerlauf = new JButton("Verlauf der Werte Anzeigen");
        btEnde = new JButton("Spiel Beenden");
        
        lblGameover.setFont(lblGameover.getFont().deriveFont(Font.BOLD, 20)); // die Schriftgröße des Labels wird angepasst
        
        //fügt einen ActionListener hinzu um auf einen klick zu reagieren
        btHauptmenü.addActionListener(this);
        btEnde.addActionListener(this);
        btVerlauf.addActionListener(this);

        fenster.add(gameover); // Dem Fenster wird das Panel hinzugefügt

        // dem Panel werden die zuvor erstellten Label und Button hinzugefügt
        gameover.add(lblGameover);
        gameover.add(btVerlauf);
        gameover.add(btHauptmenü);
        gameover.add(btEnde);

        fenster.setSize(800,800); // die größe des Fenster wird auf 800 x 800 Pixel gesetzt
        fenster.setLocationRelativeTo(null);// das Fenster wird in die Mitte des Bildschirm gesetzt
        
        //das Fenster und das Panel werden Sichtbar
        fenster.setVisible(true);
        gameover.setVisible(true);
        // in der Konsol wird Game over ausgegeben
        System.out.println("Game over");
    }

    /**
     * Diese Methode dient der Visualisierung wenn die Simulation erfolgreich abgeschlossen wurde.
     * Es wird ein neues Panel dem Fenster hinzugefügt in dem es die Möglichkeit gibt sich das Ergebnis
     * anziegen zu lassen, das Spiel neu zu starten, oder es zu beenden
     * 
     * 
     */
    private void victory()
    {
        fenster.setTitle("***** Victory *****"); // setzt den Titel des Fensters neu
        
        victory = new JPanel(); // es wird ein neues Panel erstellt
        victory.setLayout(new GridLayout(5, 1, 50, 50)); //Erzeuge eine 5x1 Matrix mit einem Abstand von 50 Pixeln 
        lastActivePanel = victory; //weist einer variablen das Panel gameover zu
        
        JLabel lblVictory= new JLabel(" Sie haben Ihr Land Erfolgreich regiert und es weit voran getrieben ");
        JLabel lblErgebnis= new JLabel("Simulationserfolg: " + Main.logik.simulationsErfolg.get(Main.logik.aktuelleRunde -1 ));
        btHauptmenü = new JButton("Zurück zum Hauptmenü"); 
        btEnde = new JButton("Spiel Beenden");
        btVerlauf = new JButton("Verlauf der Werte Anzeigen");
        
        // die Schriftgröße des Labels wird angepasst
        lblErgebnis.setFont(lblVictory.getFont().deriveFont(Font.BOLD, 15));
        lblVictory.setFont(lblVictory.getFont().deriveFont(Font.BOLD, 20));
        
        //fügt einen ActionListener hinzu um auf einen klick zu reagieren
        btEnde.addActionListener(this);
        btVerlauf.addActionListener(this);
        btHauptmenü.addActionListener(this);

        // dem Panel werden die zuvor erstellten Label und Button hinzugefügt
        fenster.add(victory);
        victory.add(lblVictory);
        victory.add(lblErgebnis);
        victory.add(btVerlauf);
        victory.add(btHauptmenü);
        victory.add(btEnde);

        fenster.setSize(800,800);// die größe des Fenster wird auf 800 x 800 Pixel gesetzt
        fenster.setLocationRelativeTo(null);// das Fenster wird in die Mitte des Bildschirm gesetzt

        //das Fenster und das Panel werden Sichtbar
        fenster.setVisible(true);
        victory.setVisible(true);      
        
        System.out.println("Victory");// in der Konsol wird Victory ausgegeben
    }

    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     */
    private void grafikausgabe()
    {
        fenster.setTitle("***** Verlauf *****"); // setzt den Titel des Fensters neu
        graph = new JPanel(); // es wird ein neues Panel erstellt
        tabellenPanel = new JPanel();
        
        graph.setLayout(new GridLayout(4, 1, 50, 50)); //Erzeuge eine 4x1 Matrix mit einem Abstand von 50 Pixeln
        
        lastActivePanel.setVisible(false); // sets die Sichtbarkeit des zugewiesene Panel auf false
        lastActivePanel = graph; //weist einer variablen das Panel graph zu
        
        btVerlauf.setText("Zurück zu Übersicht"); // weist dem Button einen neuen Text zu
        lblVerlauf= new JLabel("Anzeigen von Simulationserfolg"); // erstellt ein neues Label 
        
        str = "Simulationserfolg"; // weist der String Variable den Wert Simulationserfolg zu 
                
        cbAuswahl = new JComboBox<String>(); // es wird eine ComboBox erstellt
        //Fügt Werte in die ComboBox
        cbAuswahl.addItem("Simulationserfolg");
        cbAuswahl.addItem("Bildung");
        cbAuswahl.addItem("Lebensqualität");
        cbAuswahl.addItem("Bevölkerungsgröße");
        cbAuswahl.addItem("Bevölkerungswachstum");
        cbAuswahl.addItem("Wirtschaftsleistung");
        cbAuswahl.addItem("Modernisierungsgrad");
        cbAuswahl.addItem("Staatsvermögen");
        cbAuswahl.addItem("Umweltverschmutzung");
        cbAuswahl.addItem("Bevölkerungswachstumsfaktor");
        cbAuswahl.addItem("Politische Stabilität");
        cbAuswahl.addItem("Versorgungslage");
        
        cbAuswahl.addActionListener(this);// fügt der ComboBox einen ActionListener hinzu
        
        erzeugeTabelle(Main.logik.simulationsErfolg);// es wird die Methode zum erstellen der Tabelle aufgerfen und die Hash für den Simulationserfolg mitgegeben

        fenster.add(graph); // dem Fenster wird das Panel graph zugewiesen
        graph.add(lblVerlauf);// dem Panel graph werden die Label, Button und das Panel tabellenPanel zugewiesen
        // graph.add(tabellenPanel);
        graph.add(cbAuswahl);
        graph.add(btVerlauf);              
        
        fenster.setSize(800,800); // die größe des Fenster wird auf 800 x 800 Pixel gesetzt
        fenster.setLocationRelativeTo(null); // das Fenster wird in die Mitte des Bildschirm gesetzt

        //das Fenster und das Panel werden Sichtbar
        fenster.setVisible(true);
        tabellenPanel.setVisible(true);
        graph.setVisible(true);
        // Die Konsolo gibt aus, dass der Verlauf aufgrufen wurde
        System.out.println("Verlauf wird angezeigt");

    }

    /**
     * Gibt ein Pop-Up Fenster aus mit einer beliebigen (Fehler-)Meldung
     * @param text Text, der angezeigt werden soll.
     */
    public void popUpAusgeben(String text)
    {
        JOptionPane.showMessageDialog(null, text);
    }

    /**
     * @param Hashmap der Werte die man in der Tabelle haben möchte
     */
    public void erzeugeTabelle(HashMap<Integer, Integer> hashMap) {         
            JTable table = createJTableFromHashMap(hashMap); //Tabelle aus HashMap erzeugen
            //Tabelle dem Panel hinzufügen
            tabellenPanel.add(new JScrollPane(table));
            graph.add(tabellenPanel);
            tabellenPanel.setVisible(true);
    }
    
    /**
     * 
     */
    public JTable createJTableFromHashMap(HashMap<Integer, Integer> hashMap) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Runde");
        tableModel.addColumn("Werte");
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
    }   
    
    @Override
    /** Diese Methode legt für jeden ActionListener fest was nach dem Drücken des Buttons ausgelöst werden soll. 
     * Jeder Button ist in einem einzelnen if fall Programmiert 
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btStart) {
            // Der Button Start löst den wechseln des Spielstandes auf ="SPIELERNAME" aus.
            // es wird ebendfalls bei einem Neustart das letzte aktive fenster auf nicht Sichtbar gesetzt
            System.out.println("Start wurde geklickt.");
            lastActivePanel.setVisible(false);
            setSpielstand("SPIELERNAME");
            spielstandänderung();
        } else if (e.getSource() == btEnde) {
            // Der Button Ende bricht das Programm ab
            System.out.println("Ende wurde geklickt.");
            System.exit(0);
        } else if (e.getSource() == btModernHoch) {
            
            if (Main.modernisierungsgrad.prüfeObImWertebereich(intModernisierungsgrad + 1) == true && intStaatsvermögen - 1 >= 0) {
                //Dieser Button setzt den Wert von Modernisierungsgrad um einen Hoch wenn der nachfolgende Wert 
                //noch im Wertebereich liegt und Staatskapital - 1 >=  0 ist. 
                intModernisierungsgrad = intModernisierungsgrad + 1;
                intStaatsvermögen = intStaatsvermögen - 1;
                System.out.println(Main.wirtschaftsleistung.prüfeObImWertebereich(intModernisierungsgrad));
 
                // Der aktualisierte Wert zum Staatsvermögen wird im Label angezeigt.
                lblVerbleibendesStaatskapital.setText( "Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
                pbModernisierungsgrad.setValue(intModernisierungsgrad);
                System.out.println("Modernisierungsgrad um 1 Punkt hoch");
                btModernRunter.setVisible(true);
            } else {
                System.out.println("Wert nicht im Wertebereich");
            }
        } else if (e.getSource() == btModernRunter) {
            // es wird der Investierte Punkt zurück genommen.
            intStaatsvermögen = intStaatsvermögen + 1;
            intModernisierungsgrad = intModernisierungsgrad - 1;
            ///wenn nach drücken des Buttons der Startwert der Runde wieder ereicht wird. Wird der BUtton ausgeblendet
            if(intModernisierungsgrad ==  Main.modernisierungsgrad.getWert()) {
                btModernRunter.setVisible(false);
            }
            //Die ProgresBar und das Label wird aktueallisiert
            lblVerbleibendesStaatskapital.setText("Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
            pbModernisierungsgrad.setValue(intModernisierungsgrad);

            System.out.println("Modernisierung um 1 Punkt runter");
        } else if (e.getSource() == btWirtschaftHoch) {
            // Es wird geprüft, ob wirtschaftsleistung größer ist als der Startwert.
            if(intWirtschaftsleistung < Main.wirtschaftsleistung.getWert()){
                // Wenn Wirtschaftsleistung bereits einmal veringert wurde wird der Investierte Punkt wieder gut geschrieben
                intWirtschaftsleistung = intWirtschaftsleistung + 1;
                intStaatsvermögen = intStaatsvermögen + 1;
                System.out.println(Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung));
 
                lblVerbleibendesStaatskapital.setText( "Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
                pbWirtschaftsleistung.setValue(intWirtschaftsleistung);
                
                
                System.out.println("Wirtschaftleistung um 1 Punkt hoch");
            } else if (Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung + 1) == true && intStaatsvermögen - 1 >= 0) {
                //Wenn wirtschaftsleistung höher oder gleich des Rundenstartwertes ist. Wird ein Punkt des Staatsvermögens abgezogen und der Wert von Wirtschaftsleistung erhöht.
                intWirtschaftsleistung = intWirtschaftsleistung + 1;
                intStaatsvermögen = intStaatsvermögen - 1;
                System.out.println(Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung));

                // Es wird der Angezeigte Wert in dem Label und der ProgressBar angepasst
                lblVerbleibendesStaatskapital.setText( "Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
                pbWirtschaftsleistung.setValue(intWirtschaftsleistung);
                System.out.println("Wirtschaftleistung um 1 Punkt hoch");
            } else {
                System.out.println("Wert nicht im Wertebereich");
            }
        } else if (e.getSource() == btWirtschaftRunter) {
            
            if(intWirtschaftsleistung > Main.wirtschaftsleistung.getWert()){
                //Wenn bereits in der Runde in Wirtschaftsleistung investiert wurde werden die Punkte wieder gut geschrieben
                intWirtschaftsleistung = intWirtschaftsleistung - 1;
                intStaatsvermögen = intStaatsvermögen + 1;

                System.out.println(Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung));

                Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung);//Prüfen ob Wert im Wertebereich 
                
                //Es wird der Angezeigte Wert in dem Label und der ProgressBar angepasst
                lblVerbleibendesStaatskapital.setText( "Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
                pbWirtschaftsleistung.setValue(intWirtschaftsleistung);
                System.out.println("Wirtschaftleistung um 1 Punkt runter");
            } else if (Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung -1) == true && intStaatsvermögen - 1 >= 0){
                //Wenn wirtschaftslesitung noch nicht erhöht wurde, werden ein Punkt vom Staatsvermögen und ein Punkt abgezogen
                intWirtschaftsleistung = intWirtschaftsleistung - 1;
                intStaatsvermögen = intStaatsvermögen - 1;

                System.out.println(Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung));

                Main.wirtschaftsleistung.prüfeObImWertebereich(intWirtschaftsleistung);//Prüfen ob Wert im Wertebereich 
                
                //Es wird der Angezeigte Wert in dem Label und der ProgressBar angepasst
                lblVerbleibendesStaatskapital.setText( "Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
                pbWirtschaftsleistung.setValue(intWirtschaftsleistung);
                System.out.println("Wirtschaftleistung um 1 Punkt runter");
            } else {
                System.out.println("Wert nicht im Wertebereich");
            }
        } else if (e.getSource() == btLebenHoch) {
            
            if (Main.lebensqualität.prüfeObImWertebereich(intLeben + 1) == true && intStaatsvermögen - 1 >= 0) {
                //Wenn der Wert plus eins noch im Wertebereich ist wird der Wetrt um einen Erhöht und das Staatskapitalum eien veringert
                intLeben = intLeben + 1;
                intStaatsvermögen = intStaatsvermögen - 1;
                System.out.println(Main.lebensqualität.prüfeObImWertebereich(intLeben));
         
                //Es wird der Angezeigte Wert in dem Label und der ProgressBar angepasst
                lblVerbleibendesStaatskapital.setText( "Verbleibendes mögliche Investitionen: " + intStaatsvermögen.toString());
                pbLeben.setValue(intLeben);
                System.out.println("Lebensqualität um 1 Punkt hoch");
                btLebenRunter.setVisible(true);
            } else {
                //Ausgabe der Werte wenn der Maxwert bereits erreicht ist
                System.out.println(intLeben);
                System.out.println(intStaatsvermögen);
            }

        } else if (e.getSource() == btLebenRunter) {
            // Es wird der Wert vom Staatsvermögen um einen erhöht und der Wert der Wert von Leben um einen runter gesetzt
            intStaatsvermögen = intStaatsvermögen + 1;
            intLeben = intLeben - 1;
            //Wenn der Sartwert der Runde wieder ereicht wurde wird der Button ausgeblendet
            if(intLeben == Main.lebensqualität.getWert()) {
                btLebenRunter.setVisible(false);
            }
            //Die Label und die ProgressBar wird angepasst, sowie der
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
            Main.staatsvermögen.setWert(intStaatsvermögen);

            setSpielstand("BERECHNUNG");
            spielstandänderung();
            System.out.println("Weiter geklickt");
            wertezuweisen.setVisible(false);
        } else if (e.getSource() == btHauptmenü) {
            lastActivePanel.setVisible(false);
            fenster.dispose();

            setSpielstand("NEUSTART");
            spielstandänderung();

        } else if (e.getSource() == btNamenbestätigen) {
            String eingabe = txtName.getText();
            if (eingabe.matches(".*[\\\\/:*?\"<>|].*")) {
                // Eingabe enthält ungültige Zeichen
                System.out.println("Ungültige Zeichen in der Eingabe.");
                JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Namen ohne Sonderzeichen ein");
                txtName.setText("");
            } else {
                // Wenn die Eingabe gültig ist:
                System.out.println("Eingabe gültig: " + eingabe);
                
                Main.logik.spielername = eingabe;
                namenEintragen.setVisible(false); // ausblenden des Aktiven panel
                
                setSpielstand("AUSWAHL"); //Spielstand wird auf Auswahl gestzt
                spielstandänderung();
            }

        } else if (e.getSource() == btVerlauf) {
            if (lastActivePanel == victory || lastActivePanel == gameover) {
                lastActivePanel.setVisible(false);
                grafikausgabe();
            }else if (lastActivePanel == graph){
                if (strSpielstand == "GAMEOVER") {
                    lastActivePanel.setVisible(false);
                    gameover();
                }else{
                    lastActivePanel.setVisible(false);
                    victory();
                }
            }
            
        } else if (e.getSource() == cbAuswahl) {
            if(str == cbAuswahl.getSelectedItem().toString()) {return;}
            lblVerlauf.setText("Anzeigen von " + cbAuswahl.getSelectedItem());
            str = cbAuswahl.getSelectedItem().toString();
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
            tabellenPanel.repaint();
            tabellenPanel.setVisible(true);
        }
    }
}