import java.awt.Color;
import java.awt.Graphics;

public class Explode {
	int x, y;
	private boolean live = true;
	int step = 0;

	private TankClient tc;

	int[] diameter = { 4, 7, 12, 18, 26, 32, 49, 30, 14, 6 };

	public Explode(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		if (!live == true) {
			tc.Explodes.remove(this);
			return;
		}

		if (step == diameter.length) {
			live = false;
			step = 0;
			return;
		}

		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, diameter[step], diameter[step]);
		g.setColor(c);

		step++;
	}
}