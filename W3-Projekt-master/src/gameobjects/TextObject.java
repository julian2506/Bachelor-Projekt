package gameobjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.LinkedList;

import collider.Collider;
import controller.ObjectController;
import playground.Playground;

public class TextObject extends GameObject {

	private String text = null;
	private int size = 1;
	private Color textColor = null;

	public String getText() {
		return this.text;
	}

	/**
	 * Constructor for a text object
	 * 
	 * @param id
	 *            identifier string
	 * @param playground
	 *            to be drawn on
	 * @param controller
	 *            which takes over the movement or the disappearance
	 * @param x
	 *            position
	 * @param y
	 *            position
	 * @param vx
	 *            velocity (speed)
	 * @param vy
	 *            velocity (speed)
	 * @param text
	 *            which is displayed on the playground
	 * @param size
	 *            of the text
	 */
	public TextObject(String id, Playground playground, ObjectController controller, double x, double y, double vx,
			double vy, String text, int size) {
		super(id, playground, controller, x, y, vx, vy);

		this.size = size;
		this.text = text;

		Font serifFont = new Font("Serif", Font.PLAIN, size);
		FontRenderContext frc = new FontRenderContext(null, false, false);
		int textwidth = (int) (serifFont.getStringBounds(text, frc).getWidth());
		int textheight = (int) (serifFont.getStringBounds(text, frc).getHeight());
		setRectangleMode(textwidth / 2., textheight / 2.);

		if (textColor == null) {
			this.textColor = Color.RED;
		}

	}

	public TextObject(String id, Playground playground, ObjectController controller, 
		double x, double y, double vx, double vy, String text, int size, LinkedList<Collider> col) {
		super(id, playground, controller, x, y, vx, vy, col);

		this.size = size;
		this.text = text;

		Font serifFont = new Font("Serif", Font.PLAIN, size);
		FontRenderContext frc = new FontRenderContext(null, false, false);
		int textwidth = (int) (serifFont.getStringBounds(text, frc).getWidth());
		int textheight = (int) (serifFont.getStringBounds(text, frc).getHeight());
		setRectangleMode(textwidth / 2., textheight / 2.);

		if (textColor == null) {
			this.textColor = Color.RED;
		}

	}

	public TextObject setTextColor(Color textColor) {
		this.textColor = textColor;
		return this;
	}

	public void draw(Graphics2D g) {
		Font serifFont = new Font("Serif", Font.PLAIN, size);
		AttributedString as = new AttributedString(this.text);
		as.addAttribute(TextAttribute.FONT, serifFont);
		as.addAttribute(TextAttribute.FOREGROUND, this.textColor);

		g.drawString(as.getIterator(), (int) Math.round(this.x - this.rx), (int) Math.round(this.y - this.ry));
	}
}
