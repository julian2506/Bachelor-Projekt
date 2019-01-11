package playground;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

public class LadeDatei {
	
	public LinkedList<String>  imageList		= new LinkedList<String>();
	public LinkedList<Double>  showtimeList		= new LinkedList<Double>();
	
	
	 public LadeDatei(String datName, LinkedList<String> imageList, 
			  LinkedList<Double> showtimeList) { 
		  
		  Scanner scanner;
		  this.imageList = imageList;
		  this.showtimeList = showtimeList;
	          
	      try { 
	    	  scanner = new Scanner(new File(datName), "UTF-8"); 
	    	  
	    	  scanner.useLocale(Locale.GERMANY);
	  
	          String zeile; 
	          double zeit;
	          
		          while (scanner.hasNext()) { 
		        	  if (scanner.hasNextDouble()) {
		        		  zeit = scanner.nextDouble();
		        		  showtimeList.add(zeit);
		        	  } else {
		        		  zeile = scanner.next();
		        		  imageList.add(zeile);
		        	  }
		          }
	          
	          scanner.close(); 
	          
	          } catch (FileNotFoundException e) { 
	              e.printStackTrace(); 
	          }
	  } 
	  
	  public LinkedList<String> getImagelist(LinkedList<String> imageList) {
		  return this.imageList;
	  }
	  
	  public LinkedList<Double> getShowtimelist(LinkedList<Double> showtimeList) {
		  return this.showtimeList;
	  }
}

