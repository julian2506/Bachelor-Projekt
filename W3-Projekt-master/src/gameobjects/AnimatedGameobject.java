package gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import controller.ObjectController;
import playground.Playground;


public class AnimatedGameobject extends GameObject{
	
	private BufferedImage[] imageArray;
	private int totalFrames = 3;
	private int counter = 0;
	int currentFrame = 0;
	int second;
	
	/*Timer timer = new Timer();
	TimerTask task = new TimerTask() {
		public void run() {
			second++;
		}	
	};*/

	
	public AnimatedGameobject(String id, Playground pg, ObjectController o, double x, double y, double vx,
		      double vy, double sizeX, double sizeY, double scale) {
		super(id, pg, o, x, y, vx, vy);
		try {
			imageArray = new BufferedImage[totalFrames];
				for (int i = 0; i < imageArray.length; i++ ) {
					imageArray[i] = ImageIO.read(new File("./boredAlien" + i + ".png"));
					//System.out.println("Gefuellt");

				    setRadiusMode(imageArray[i].getWidth() * scale);
				    double radX = (int) (imageArray[i].getWidth() * scale);
				    double radY = (int) (imageArray[i].getHeight() * scale);
				    setRectangleMode(radX, radY) ;
				    if (imageArray[i].getHeight() * scale > radius) {
				      setRadiusMode(imageArray[i].getHeight() * scale);
				    }
				}
		    } catch (IOException e) {
		    }
		
		//timer.scheduleAtFixedRate(task, 500, 1000);
	}

		
	public void draw(Graphics2D g) {
	    //System.out.println("drawing"+this.getId());
		//System.out.println(second);
		
		if (currentFrame >= imageArray.length)  {
			currentFrame = 0;
			
		}
		
		if(counter >= 100) {
			
		    // painted neues Frame wenn counter hoeher 100
	    	g.drawImage(imageArray[currentFrame], (int) Math.round(x - rx), (int) Math.round(y - ry), (int) rx * 2,
	    	  			(int) ry * 2, null);
	    	
	    	currentFrame++;
	    	counter = 0;
	    	
		} else {
			// zeigt currentFrame weiterhin an waehrend counter hochzaehlt bis neues Frame angezeigt wird
			g.drawImage(imageArray[currentFrame], (int) Math.round(x - rx), (int) Math.round(y - ry), (int) rx * 2,
    	  			(int) ry * 2, null);
			counter++;
		}

	} 
}