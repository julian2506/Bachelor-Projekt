package playground;

public class Collision {
	
	public static boolean EnemyHitsPlayer(double px, double py, int radius, double ex, 
			double ey, int width, int height) {
		if (px >= ex && px <= ex + width) 
		{
			if((py - ey) * (py - ey) <= radius * radius) return true;
			if((py - (ey + height) * (py - (ey + height)) <= radius * radius)) return true;
		}
		if (py >= ey && py <= ey + height) 
		{
			if((px - ex) * (px - ex) <= radius * radius) return true;
			if((px - (ex + width) * (px - (ex + width)) <= radius * radius)) return true;
		}
		
		// Fall, dass Player diagonal mit Enemy kollidiert

		for (int x = 0; x < 2; x++)
		{
			for (int y= 0; y < 2; y++)
			{
				double distx = px - (ex + x * width);
				double disty = py - (ey + y * height);
				double dist = distx * distx + disty * disty; 
				if(dist <= radius * radius) return true;
			}
		}
		
		// Fall, dass Enemy auf Player liegt
		if (px >= ex && px <= ex + width && py >= ey && py <= ey + height) return true;
		return false;
	}

}
