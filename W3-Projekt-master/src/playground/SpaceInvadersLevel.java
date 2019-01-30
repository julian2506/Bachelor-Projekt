package playground;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.AttributedString;
import java.util.LinkedList;
import java.util.Locale;

import javax.imageio.ImageIO;

import collider.CircleCollider;
import collider.Collider;
import collider.CompositeCollider;
import collider.RectCollider;
import controller.EnemyController;
import controller.FallingStarController;
import controller.LimitedTimeController;
import controller.MineController;
import controller.ObjectController;
import controller.EgoController;
import gameobjects.AnimatedGameobject;
import gameobjects.BitmapObject;
import gameobjects.FallingStar;
import gameobjects.GameObject;
import gameobjects.EgoObject;
import gameobjects.TextObject;
import playground.CollisionDetector;

import java.util.Scanner;



/**
 * Class that realizes all the game logic of a very simple game level. The level contains for now
 * only two objects that are {@link GameObject} subclasses: {@link FallingStar} and {@link EgoObject}.
 * Functions performed by this class are:
 * <ul>
 * <li>initially set up the level, spawn all object etc., in method {@link #prepareLevel}
 * <li>React to keyboard commands in method {@link #processInputs}
 * <li>define basic object movement rules for all objects in the level in the various
 * ObjectController subclasses: {@link EgoController} and {@link FallingStarController}.
 * </ul>
 */
public class SpaceInvadersLevel extends KeyboardControl {

  public static final int    LEVEL2STARS    = 80;
  public static final double BONUS_DURATION = 1.;
  public static final double ENEMYSPEEDX    = 120;
  public static final double ENEMYSPEEDY    = 80;
  public static final double ENEMYSHOTSPEED = 150;
  public static final int    NRSHARDS       = 50;
  public static final double EXPL_DURATION  = 1.;
  public static final int    NR_ENEMIES     = 30;
  public static final int    NR_COLLECT     = 5;
  public static final Color  EXPL_COLOR     = Color.RED;
  public static final double SHARDSPEED     = 400;
  public static final double STARSPEED      = 200;
  public static final double STARTTEXTSPEED = 150;
  public static final double STARTPERIOD    = 5.;
  public static final double DYING_INTERVAL = 2.0 ;

  protected boolean          lost           = false;
  protected boolean          doneLevel      = false;
  protected long   			 startzeit;
  protected double	 		 showtime	= 0.01; //sekunden
  protected String[]		 abspielmodus 	= null;
  public	File			 smash			= null;
  public static	File		 laser			= null;
  
  public BufferedImage[]	 alienImage     = null;
  public double[]	 	 	 alienshowTime	= null;
  
  public BufferedImage[]  	 heartImage     = null;
  public double[]	 	 	 heartshowTime	= null;
  

  public SpaceInvadersLevel(int SIZEX, int SIZEY) {
    super(SIZEX, SIZEY);
  }

  protected GameObject createEnemyShotObject(GameObject parentObject, String name,
                                             ObjectController limitedTimeController) {
    TextObject to =  new TextObject(name, this,
          limitedTimeController, parentObject.getX(), parentObject.getY(), 0, ENEMYSHOTSPEED, "I", 20);
      to.setTextColor(Color.YELLOW);
      return to ;

  }


  protected void createEnemyShot(GameObject e, double gameTime) {
    double PROB = calcEnemyShotProb() ;
    double diceThrow = Math.random();
    Integer nrEnemyShots = (Integer) (getFlag("enemyShotCounter"));
    if (diceThrow < PROB) {
      setFlag("enemyShotCounter", Integer.valueOf(++nrEnemyShots));

      LimitedTimeController limitedTimeController = new LimitedTimeController(gameTime, 5.);
      GameObject textObject = createEnemyShotObject(e, "enmyShot" + nrEnemyShots, limitedTimeController) ;

      addObject(textObject);
    }
  }

  double calcEnemyShotProb()  {
    return 0.1 * this.getTimestep();
  }

  protected double calcEnemySpeedX() {
    return ENEMYSPEEDX ;
  }


  protected double calcEnemySpeedY() {
    return ENEMYSPEEDY ;
  }

  protected int calcNrEnemies() {
    return NR_ENEMIES ;
  }
  
  protected int calcNrCollect() {
	return NR_COLLECT ;
  }


  ObjectController createEnemyController() {
    return new EnemyController() ;
  }
  
  RectCollider createRectColl(String id, Playground playground, 
		  ObjectController controller) { 
	return new RectCollider(id, playground, controller);
  }
  
  CircleCollider createCircColl(String id, Playground playground, ObjectController controller) { 
	return new CircleCollider(id, playground, controller);
  }
  

  protected String getStartupMessage() {
    return "Get ready for level X!" ;
  }


  protected GameObject createSingleEnemy(String name, double x_enemy, double y_enemy,
          double vx_enemy, double vy_enemy, ObjectController enemyController, double gameTime, Collider col) {
      return new AnimatedGameobject(name, this, enemyController, x_enemy, y_enemy,
              vx_enemy, vy_enemy, this.canvasX / 10, this.canvasY / 10, 0.4, 
              this.alienImage, this.alienshowTime, startzeit, "loop", col);
  }
  
  protected GameObject createSingleCollect(String name, double x_collect, double y_collect,
          double vx_collect, double vy_collect, ObjectController collectController, double gameTime, Collider col) {
      return new AnimatedGameobject(name, this, collectController, x_collect, y_collect,
              vx_collect, vy_collect, this.canvasX / 10, this.canvasY / 10, 0.1, 
              this.heartImage, this.heartshowTime, startzeit, "loop", col );
  }


  protected void createEnemies(double gameTime) {
    // create enemies
    double speedx = this.calcEnemySpeedX() ;
    double speedy = this.calcEnemySpeedY() ;
    for (int i = 0; i < this.calcNrEnemies(); i++) {
      double x_enemy = Math.random() * this.canvasX;
      double y_enemy = Math.random() * this.canvasY / 3;
      double vx_enemy = 2 * (Math.random() - 0.5) * speedx;
      double vy_enemy = Math.random() * speedy;

      ObjectController enemyController = createEnemyController();
      
      LinkedList<Collider> enemycol = new LinkedList<Collider>();
      
      Collider rectCol = createRectColl("rectCol", this, 
    		  enemyController);
      
      enemycol.add(rectCol);
      
      Collider compcol = new CompositeCollider("compCol", this, 
    		  enemyController, enemycol);

      GameObject enemy = createSingleEnemy("enemy"+i, x_enemy, 
    		  y_enemy, vx_enemy, vy_enemy, enemyController, 
    		  gameTime, compcol) ;
      
      
      
      addObject(enemy);
    }
  }
  
  protected void createCollectables(double gameTime) {
	    // create collectables
	    double cspeedy = this.calcEnemySpeedY() ;
	    for (int i = 0; i < this.calcNrCollect(); i++) {
	      double x_collect = Math.random() * this.canvasX;
	      double y_collect = Math.random() * this.canvasY / 3;
	      double vx_collect = 2 * (Math.random() - 0.5) * 0;
	      double vy_collect = Math.random() * cspeedy;

	      ObjectController collectController = createEnemyController();
	      
	      RectCollider rectCol1 = createRectColl("rectCol", this, collectController);

	      GameObject collect = createSingleCollect("collect"+i, x_collect, y_collect,
	          vx_collect, vy_collect, collectController, gameTime, rectCol1) ;
	      addObject(collect);
	    }
	  }


  protected void createEgoObject() {
    // add ego to playground at lower bottom
    EgoController egoController = new EgoController();
    Collider egoCol = new RectCollider("circCol", this, egoController);
    
    EgoObject ego = new EgoObject("ego", this, egoController, 50, canvasY - 60, 0, 0, egoCol );
    
    addObject(ego);
  }


  protected void createStars() {
    // add stars to playground
    for (int i = 1; i <= LEVEL2STARS; i++) {
      FallingStarController fallingStartController = new FallingStarController();
      
      RectCollider starcol = createRectColl("rectCol", this, fallingStartController);
      
      FallingStar star = new FallingStar("star" + i, this, fallingStartController,
          Math.random() * canvasX, Math.random() * 15, 0.0, Math.random() * STARSPEED, starcol);
      addObject(star);
    }
  }
  

  /** Adds ego object and stars and displays startup message. */
  protected void setupInitialState(double gameTime) {
    setFlag("gameStatus", "starting");

    this.createStars();

    // set up ego object
    this.createEgoObject();

    // add text object to playground
    ObjectController ctrl = new LimitedTimeController(gameTime, 3.);
    GameObject readyText = new TextObject("ready?", this, ctrl, getSizeX() / 2, 0, 0, 100,
        this.getStartupMessage(), 50);
    addObject(readyText.setActive(true));

  }
  
  
  void createExplosion(double gameTime, GameObject e, String basename, double interval, Color color) {
    // spawn a cloud of exploded shards
    for (int i = 0; i < NRSHARDS; i++) {
      double vx = 2 * (Math.random() - 0.5) * SHARDSPEED + e.getVX();
      double vy = 2 * (Math.random() - 0.5) * SHARDSPEED + e.getVY();
      addObject(new FallingStar("shard" + gameTime + "/" + i, this,
          new LimitedTimeController(gameTime, interval), e.getX(),
          e.getY(), vx, vy, color));
    }
  }
  
  
  void actionIfEnemyIsHit(GameObject e, GameObject shot, double gameTime) {
    createExplosion(gameTime, e, "shard", DYING_INTERVAL, Color.RED) ;
    
    //JW: ton abspielen
    Music.music(smash);

    // spawn a bonus points object
    double vx = 2 * (Math.random() - 0.5) * SHARDSPEED + e.getVX();
    double vy = 2 * (Math.random() - 0.5) * SHARDSPEED + e.getVY();

    LimitedTimeController bonusTextController =
        new LimitedTimeController(gameTime, SpaceInvadersLevel.EXPL_DURATION);
    TextObject bonusText = new TextObject("bonus" + e.getId(), this, bonusTextController,
        e.getX(), e.getY(), vx, vy, "200!", 20).setTextColor(Color.YELLOW);

    addObject(bonusText);

    // delete enemy
    deleteObject(e.getId());
    
    // delete shot
    deleteObject(shot.getId()) ;

    // add to points counter
    Integer pts = (Integer) getFlag("points");
    setFlag("points", pts + 200);
    
  }
  
 void actionIfCollect(GameObject e, GameObject shot, double gameTime) {
	 //createExplosion(gameTime, e, "shard", DYING_INTERVAL, Color.RED) ;
	    
	 //JW: ton abspielen
	 //Music.music(smash);

	 // spawn a bonus points object
	 double vx = 2 * (Math.random() - 0.5) * SHARDSPEED + e.getVX();
	 double vy = 2 * (Math.random() - 0.5) * SHARDSPEED + e.getVY();

	 LimitedTimeController bonusTextController =
	     new LimitedTimeController(gameTime, SpaceInvadersLevel.EXPL_DURATION);
     //TextObject bonusText = new TextObject("bonus" + e.getId(), this, bonusTextController,
	 //e.getX(), e.getY(), vx, vy, "Level up!", 20).setTextColor(Color.YELLOW);

	 //addObject(bonusText);

	 // delete enemy
	 deleteObject(e.getId());
	    
	 // delete shot
	 deleteObject(shot.getId()) ;

	 // add to points counter
	 Integer lives = (Integer) getFlag("egoLives") ;
	 lives++ ;
	 setFlag ("egoLives",lives) ;
 }
  
  void actionIfEgoCollidesWithCollect(GameObject e, GameObject ego, double gameTime){

	 /*double vx = 2 * (Math.random() - 0.5) * SHARDSPEED + e.getVX();
	 double vy = 2 * (Math.random() - 0.5) * SHARDSPEED + e.getVY();

	 LimitedTimeController levelTextController =
	     new LimitedTimeController(gameTime, SpaceInvadersLevel.EXPL_DURATION);
	 TextObject bonusText = new TextObject("levelup" + e.getId(), this, levelTextController,
	    e.getX(), e.getY(), vx, vy, "Level up!", 20).setTextColor(Color.YELLOW);

	 addObject(bonusText);*/
	    
	 // delete collect
	 deleteObject(e.getId());

	 // add to points counter
	 Integer lives = (Integer) getFlag("egoLives") ;
	 lives++ ;
	 setFlag ("egoLives",lives); 
	    
  }
  
  void actionIfEgoCollidesWithEnemy(GameObject e, GameObject ego, double gameTime){
    // set temporary text over ego
    if (getObject("AUA" + e.getId()) == null) {
      /*addObject(new TextObject("AUA" + e.getId(), this,
          new LimitedTimeController(gameTime, BONUS_DURATION), ego.getX(), ego.getY() - 20,
          ego.getVX(), ego.getVY(), "AUAA!!", 10));*/
      
      // deduct points
      Integer pts = (Integer) getFlag("points");
      setFlag("points", pts - 500);
    }
    
  }
  
  protected void actionIfEgoObjectIsHit(GameObject eshot, GameObject ego, double gameTime) {
    //System.out.println("collision of " + eshot.getId() + " and " + ego.getId());
    this.deleteObject(eshot.getId()) ;
    
    Integer lives = (Integer) getFlag("egoLives") ;
    lives-- ;
    setFlag ("egoLives",lives) ;
    
    if (lives <= 0) {
      lost = true;
    }
    
    LinkedList<GameObject> eshots = collectObjects("enmyShot", true);
    for (GameObject _eshot : eshots) {
      deleteObject(_eshot.getId()) ;
    }
    setFlag("detailedStatus","dying") ;
    setFlag("t0",gameTime) ;
    ego.setActive(false) ;
    createExplosion(gameTime, ego, "egoexp", DYING_INTERVAL, Color.WHITE) ;
    LinkedList<GameObject> enemies = collectObjects("enemy", false) ;
    for (GameObject enemy : enemies) {
      enemy.setY(0) ;
      enemy.setActive(false) ;
    }
    
  }

  
  /**
   * initially sets up the level. Just called once and not by user.
   *
   * @param id String identifies level.
   */
  @Override
  public void prepareLevel(String id) {
    reset();

    // set up flags that some objects rely on
    setFlag("delete", new LinkedList<String>());
    setFlag("replace", new LinkedList<String>());
    setFlag("points", Integer.valueOf(0));
    setFlag("enemyShotCounter", Integer.valueOf(0));
    setFlag("gameStatus", "start");
    setFlag("detailedStatus", "std");
    setFlag("egoLives", Integer.valueOf(5));
    setFlag("dying", Double.valueOf(-1));
    
    // Zeitmessung starten
    this.startzeit = System.nanoTime();
    
    // Musik laden
    this.smash = new File("./smash.wav");
    laser = new File("./laser.wav");
    
    //----- Alien
    // Datei laden und in Liste speichern
    LinkedList<String>  alienList = new LinkedList<String>();
    LinkedList<Double>  alienTimeList = new LinkedList<Double>();
    
    String dateiName = "sweetAlien.txt"; 
    LadeDatei alien = new LadeDatei(dateiName, alienList, alienTimeList); 
    alien.getImagelist(alienList);
    alien.getShowtimelist(alienTimeList);
    
    this.alienshowTime = new double[alienTimeList.size()];
    for (int i = 0; i < alienshowTime.length; i++ ) {
    	this.alienshowTime[i] = alienTimeList.get(i);
		//System.out.println(showTime[i]);
	}
    
    // Images anhand Liste in Array laden
    this.alienImage = new BufferedImage[alienList.size()];
    try {
    	for (int i = 0; i < alienImage.length; i++ ) {
    		this.alienImage[i] = ImageIO.read(new File(alienList.get(i)));
    		//System.out.println("filled");
    	}
    } catch (IOException e) {
    }
    
    //-----Heart
    // Datei laden und in Liste speichern
    LinkedList <String> heartList = new LinkedList<String>();
    LinkedList <Double> heartTimeList = new LinkedList<Double>();
    
    String heartName = "heart.txt"; 
    LadeDatei herz = new LadeDatei(heartName, heartList, heartTimeList); 
    herz.getImagelist(heartList);
    herz.getShowtimelist(heartTimeList);
    
    this.heartshowTime = new double[heartTimeList.size()];
    for (int i = 0; i < this.heartshowTime.length; i++ ) {
    	this.heartshowTime[i] = heartTimeList.get(i);
		//System.out.println(showTime[i]);
	}
    
    // Images anhand Liste in Array laden
    this.heartImage = new BufferedImage[heartList.size()];
    try {
    	for (int i = 0; i < this.heartImage.length; i++ ) {
    		this.heartImage[i] = ImageIO.read(new File(heartList.get(i)));
    		//System.out.println("filled");
    	}
    } catch (IOException e) {
    }
  }


  /**
   * (re)draws the level but NOT the objects, they draw themselves. Called by the main game loop.
   * This method mainly draws the background and the scoreboard.
   *
   * @param g2 Graphics2D object that can, and should be, draw on.
   */
  @Override
  public void redrawLevel(Graphics2D g2) {
    // Set anti-alias!
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    // Set anti-alias for text
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

    // fill background with black
    int[] x = {0, canvasX, canvasX, 0};
    int[] y = {0, 0, canvasY, canvasY};
    Polygon bg = new Polygon(x, y, 4);
    g2.setColor(Color.BLACK);
    g2.fill(bg);

    // draw score in upper left part of playground
    Integer pts = (Integer) getFlag("points");
    Font drawFont = new Font("SansSerif", Font.PLAIN, 20);
    AttributedString as = new AttributedString("Points: " + pts);
    as.addAttribute(TextAttribute.FONT, drawFont);
    as.addAttribute(TextAttribute.FOREGROUND, Color.yellow);
    g2.drawString(as.getIterator(), 10, 20);
    
    // draw lives counter in upper left part of playground
    Integer lives = (Integer) getFlag("egoLives");
    Font drawFont2 = new Font("SansSerif", Font.PLAIN, 20);
    AttributedString as2 = new AttributedString("Lives: " + lives);
    as2.addAttribute(TextAttribute.FONT, drawFont2);
    as2.addAttribute(TextAttribute.FOREGROUND, Color.yellow);
    g2.drawString(as2.getIterator(), canvasX-100, 20);
    
    //ergaenzt durch JW, draw highscore in left part of playground under score
    Integer highscore = (Integer) getFlag("highscore");
    Font drawFont3 = new Font("SansSerif", Font.PLAIN, 20);
    AttributedString as3 = new AttributedString("Highscore: " + highscore);
    as3.addAttribute(TextAttribute.FONT, drawFont3);
    as3.addAttribute(TextAttribute.FOREGROUND, Color.yellow);
    g2.drawString(as3.getIterator(), 10, 40);
    
    if(isPaused()) {
        Font drawFont4 = new Font("SansSerif", Font.PLAIN, 50);
        AttributedString as4 = new AttributedString("Das Spiel wurde pausiert.");
        as4.addAttribute(TextAttribute.FONT, drawFont4);
        as4.addAttribute(TextAttribute.FOREGROUND, Color.red);
        g2.drawString(as4.getIterator(), 30, 400);
    }
    
    // JW: Highscore aktualisieren
    File f = new File("./highscore.txt");
    DateiHandler dh = new DateiHandler(f);
    int alltimeHighscore = dh.readFile();
    setFlag("highscore",alltimeHighscore);
    int currentScore = (Integer) getFlag("points");
    if (currentScore > alltimeHighscore) {
    	alltimeHighscore = currentScore;
        setFlag("highscore",alltimeHighscore);
    	dh.closeFile();
     }
  }

  /**
   * applies the logic of the level: For now, this is just about deleting shots that are leaving the
   * screen
   *
   * @param gameTime the time the game has run so far (in ms)
   */
  @Override
  public void applyGameLogic(double gameTime) {

    String gameStatus = (String) getFlag("gameStatus");
    String subStatus = (String) getFlag("detailedStatus");
    //removeObsoleteObjects();
    // System.out.println("Status: "+gameStatus+" "+gameTime);

    if (gameStatus.equals("start") == true) {
      setupInitialState(gameTime) ;

    } else if (gameStatus.equals("starting") == true) {

      if (gameTime > 1.) {
        setFlag("gameStatus", "init");
      }

    } else if (gameStatus.equals("init") == true) {

      this.createEnemies(gameTime);
      this.createCollectables(gameTime);

      setFlag("gameStatus", "playing");

    } else if (gameStatus.equals("playing") == true) {
      GameObject s = this.getObject("ego");

      if (subStatus.equals("std")) {
          
    	// check for collisions of enemy and shots, reuse shots list from before..
        LinkedList<GameObject> enemies = collectObjects("enemy", false);
      
        // check whether all enemies have been destroyed or escaped
        if (enemies.size() == 0) {
          this.doneLevel = true;
        } 
      
        // loop over enemies to check for collisions or suchlike ...
        LinkedList<GameObject> shots = collectObjects("simpleShot", true);
        for (GameObject e : enemies) {
         // if ego collides with enemy..
          if (s.collisionDetection(e)) {
            actionIfEgoCollidesWithEnemy(e, s, gameTime) ;
          }
        	
          // if shot collides with enemy
          for (GameObject shot : shots) {
            if (e.getDistance(shot) < 0) {
              actionIfEnemyIsHit(e, shot, gameTime) ;
            }
          }
        }
        
        // Wenn Herzen einsammeln
         LinkedList<GameObject> collects = collectObjects("collect", false);
         for (GameObject c : collects) {
             if (s.collisionDetection(c)) {
           	   actionIfEgoCollidesWithCollect(c, s, gameTime) ;
             }

         }
     

        // loop over enemies and, with a certain probability, launch an enemy shot for each one
        for (GameObject e : enemies) {
          //createEnemyShot(e, gameTime);
        }

        // check for collisions between ego object and enemy shots
        LinkedList<GameObject> eshots = collectObjects("enmyShot", true);
        for (GameObject eshot : eshots) {

          if (eshot.getDistance(s) < 0) {
            actionIfEgoObjectIsHit(eshot, s, gameTime) ;
          }

        }
        
        
      } // if substatus.. 
      else if (subStatus.equals("dying")) {
        Double t0 = (Double)getFlag("t0") ;
        if (gameTime-t0 > DYING_INTERVAL) {
          LinkedList<GameObject> enemies = collectObjects("enemy", false);
          setFlag("detailedStatus","std") ;
          s.setActive(true) ;
          
          for (GameObject e: enemies) {
            //System.out.println("activating"+e.getId());
            e.setActive(true) ;
          }
        }
      }
      
    } // if (gameStatus == "playing") 
    
    
  }

  public boolean gameOver() {
    return lost;
  }

  public boolean levelFinished() {
    return doneLevel;
  }
  
  
  // Klasse um Highscore umzusetzen
  public static class DateiHandler {
	  Scanner s;
	  
	  public DateiHandler(File f){
		  try {
			  s = new Scanner(f);
		  } catch (FileNotFoundException e) {
			  e.printStackTrace();
		  }
	  }
	  public int readFile(){
		  if(s.hasNext()){
			  int highscore = s.nextInt(); 
			  return highscore;
		  }
		  return 0;  
	  }
	  
	  public static void writeFile() throws IOException{
		  Integer highscore = (Integer) getFlag("highscore");
		  String highscore2 = String.valueOf(highscore);
		  FileWriter fw = new FileWriter("highscore.txt");
		  BufferedWriter bw = new BufferedWriter(fw);
		  bw.write(highscore2);
		  bw.close();
		  System.out.println("Datei wurde geöffnet und geschrieben mit dem Wert " + highscore2);
		  
	  }
	  
	  public void closeFile() {
	  s.close();
	  }
	  
  } 
  	  


}
