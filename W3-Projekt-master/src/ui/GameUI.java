package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.WindowConstants;
import playground.Playground;
import java.awt.event.* ;

public class GameUI {

  private JFrame frame = null;
  private JPanel panel = null;
  private GamePanel canvas = null;
  
  JMenuItem loadItem ;
  JMenuItem saveItem ;
  JMenuItem quitItem ;
  JMenuItem aboutItem ;
  JMenu gameMenu ;
  JMenu helpMenu ;
  JButton neustart ;
  JButton beenden ;

  public GameUI(int sizeX, int sizeY) {

    // graphische Zeichenfläche des spiels erzeugen
    canvas = new GamePanel(sizeX, sizeY);
    canvas.setVisible(true);

    // contentPane erzeugen
    panel = new JPanel();
    panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));
    panel.add(canvas);

    // Hauptfenster  erzeugen+konfigurieren!
    frame = new JFrame("Prog2 Ballerspiel!");
    frame.setContentPane(panel);
    
    // menuleiste erzeugen und hinzufügen!
   /* this.loadItem = new JMenuItem("Restore game");
    this.saveItem = new JMenuItem("Save game");    
    this.quitItem = new JMenuItem("Exit game");
    this.loadItem.addActionListener(this); 
    this.saveItem.addActionListener(this); 
    this.quitItem.addActionListener(this); 
    this.gameMenu = new JMenu("Game") ;
    this.gameMenu.add(loadItem) ;
    this.gameMenu.add(saveItem) ;
    this.gameMenu.addSeparator();
    this.gameMenu.add(quitItem) ;
    
    this.helpMenu = new JMenu("Help") ;
    this.aboutItem = new JMenuItem("About") ;
    this.helpMenu.add(this.aboutItem) ;
    this.aboutItem.addActionListener(this);
    
    JMenuBar mb = new JMenuBar() ;
    mb.add(this.gameMenu) ;
    mb.add(this.helpMenu) ;
    
    frame.setJMenuBar(mb);
    
    this.neustart = new JButton("Spiel neu starten!") ;
    this.neustart.addActionListener(this);
    this.panel.add(this.neustart);*/

    // showtime!
    frame.pack();
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);    
    frame.setVisible(true);
        
  }

  public HashMap<Integer, Integer> getCurrentKey() {
    return canvas.getCurrentKey();
  }

  public void repaint() {
    canvas.repaint();
  }

  public void setPlayground(Playground pg) {
    canvas.setPlayground(pg);
    
  }

  public boolean isPainting() {
    return canvas.stillPainting();
  }

  public void setPainting() {
    canvas.setPainting();
  }

  public void waitWhilePainting() {
    canvas.setPainting();
    canvas.repaint();
    canvas.waitWhilePainting();
  }



  public void grabFocus() {
    canvas.grabFocus();
  }
  
  public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == this.quitItem) {
      System.exit(0); 
    } else if (ae.getSource() == this.aboutItem) {
      System.out.println("about");
    } else if (ae.getSource() == this.neustart) {
      System.out.println("Spiel wurde neu gestartet");
    } else if (ae.getSource() == this.saveItem) {
      System.out.println("save");
    } else if (ae.getSource() == this.loadItem) {
      System.out.println("load");
    }
  }
  

}
