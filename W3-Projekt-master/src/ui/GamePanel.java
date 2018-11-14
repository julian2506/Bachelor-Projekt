package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import javax.swing.JPanel;
import playground.Playground;

class GamePanel extends JPanel implements KeyListener {
  private static final long serialVersionUID = 1L;
  protected volatile boolean painting = false;
  private volatile Integer currentKey = null;
  private volatile Boolean releasedFlag = null;
  private Playground playground = null;
  private HashMap<Integer, Integer> keys = new HashMap<Integer, Integer>();

  GamePanel(int sizeX, int sizeY) {
    super();
    setPreferredSize(new Dimension(sizeX, sizeY));
    addKeyListener(this);
  }

  @Override
  public void repaint() {
    super.repaint();
  }

  public void setPainting() {
    painting = true;
  }


  public void waitWhilePainting() {
    while (painting) {
    }
  }

  public void setPlayground(Playground pg) {
    this.playground = pg;
  }

  public boolean stillPainting() {
    return painting;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    // System.out.println(playground) ;
    if (playground != null) {
      playground.redraw((Graphics2D) g);
    }
    painting = false;

  }


  public HashMap<Integer, Integer> getCurrentKey() {
    return keys;
  }


  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    this.keys.put(e.getKeyCode(), 2);
    currentKey = e.getKeyCode();
    // System.out.println("keyCode " + e.getKeyCode());
  }

  @Override
  public void keyReleased(KeyEvent e) {
    this.keys.put(e.getKeyCode(), 1);
    currentKey = e.getKeyCode();
    releasedFlag = true;
  }
}
