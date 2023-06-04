import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.util.*;
import javax.swing.JPanel;


/**
 * Beschreiben Sie hier die Klasse Grafik.
 * 
 * @Livia Kadenbach
 * @version 0.1
 */
public class Grafik extends JPanel{
    // Definition zweier Datenfelder
    private int tox = 50;
    private int toy = 50;
    private HashMap<Integer, Integer> werteverlauf;
    private String sektorname;

    // Definition der Getter
    public int getTox() {
        return tox;
    }
    public int getToy() {
        return toy;
    }
    public String getSektorname(){
        return sektorname;
    }
    public HashMap<Integer, Integer> getWerteverlauf(){
        return werteverlauf;}
    // Definition der Setter
    public void setTox(int tox) {
        this.tox = tox;
    }
    public void setToy(int toy) {
        this.toy = toy;
    }
    public void setSektorname(String sektorname){
        this.sektorname = sektorname;
    }
    public void setWerteverlauf(HashMap<Integer, Integer> werteverlauf){
        this.werteverlauf = werteverlauf;
    }
    // Der Constructor
    public Grafik(Sektor name){
        setBackground(Color.WHITE);
        setSektorname(name.getName());
        setWerteverlauf(name.werte);
        
    }

    // Die Methode paintComponents der Klasse JPanel wird überschrieben
    @Override
    protected void paintComponent(Graphics g) {
        // AUfruf der alten JPanel-Methode paintComponents
        super.paintComponent(g);
        // Brauchen wir um Stiftbreite zu ändern
        Graphics2D g2D = (Graphics2D) g;

        // Ermitteln der Größe des Diagramm-JPanel
        int hoehe = getHeight()/2;
        int breite = getWidth();

        // Stiftfarbe zum Zeichnen auf Schwarz
        g.setColor(Color.black);
        
        // Ausgabe eines Kontrolltextes
        System.out.println("Ich zeichne");
        //variablen die gebraucht werden um die Werte der Hashmap für eine Runde zwischen zu speichern
        int x = 0;
        int y = 0;  
        // Linienverlauf Sektorwerte
        
        for (Map.Entry<Integer, Integer> entry : werteverlauf.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
                        if(key == 1){
                g.drawLine(key-1,hoehe-value*5, key*75 , hoehe-(value*5));
                x = key*75;
                y = value*5;
            } else {
                g.drawLine(x , hoehe-y , key*75 , hoehe-(value*5) );
                x = key*75;
                y = value*5;
                            }
            System.out.println("draw line: x:" + x + " y:"+ y +" key:"+ key + " value:"+value);
            g.drawLine(x, hoehe, x, hoehe+10);
            g.drawString( String.valueOf(key) , x , hoehe );        
        }
        g.drawLine( 0, hoehe, x, hoehe);
        g.drawLine(0, 0, 0, hoehe*2);
        // ein Text
        g.drawString(sektorname, breite/2 , 10 );

    }
    
}