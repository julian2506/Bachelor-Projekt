package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import controller.ObjectController;
import playground.Playground;
import gameobjects.GameObject;


public class MineController extends ObjectController {
  int rad = 3;
  
  double xSpeed = 0. ;
  double lineSpeed = 0 ;
  
  public MineController(double lineSpeed) {
    this.lineSpeed = lineSpeed ;
  }

  @Override
  public void updateObject(double gameTime) {
    
    if (gameObject.getY() >= this.playground.getSizeY()-10) {
      this.gameObject.setVY(0);
      if(xSpeed == 0.) {
        GameObject ego = playground.getObject("ego") ;
        double egoXPos = ego.getX() ;
        if (egoXPos > this.gameObject.getX()) {
          xSpeed = 50;
        } else {
          xSpeed = -50 ;
        }
        this.gameObject.setVX(xSpeed) ;
        
      }      
      this.gameObject.setVX(xSpeed);
      
    }
    if(this.gameObject.getX() < 0 || (this.gameObject.getX() > this.playground.getSizeX())) {
      System.out.println("deleting"+this.gameObject.getId());
      playground.deleteObject(this.gameObject.getId());
    }
    
      
    
    applySpeedVector();
  }
}

