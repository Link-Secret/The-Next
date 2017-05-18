import java.awt.*;

public class Missile {
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;

	public static final int WIDTH = 10;
	public static final int HIGHT = 10;

	int x, y;
	Tank.Direction dir;

	private boolean Live = true;
	private TankClient tc;

	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x,int y, Tank.Direction dir, TankClient tc){
		this(x,y,dir);
		this.tc =tc;
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, WIDTH, HIGHT);
		g.setColor(c);

		move();
	}

	private void move() {
		switch (dir) {
		case L:
			x = x - XSPEED;
			break;
		case LU:
			x = x - XSPEED;
			y = y - YSPEED;
			break;
		case U:
			y = y - YSPEED;
			break;
		case RU:
			x = x + XSPEED;
			y = y - YSPEED;
			break;
		case R:
			x = x + XSPEED;
			break;
		case RD:
			x = x + XSPEED;
			y = y + YSPEED;
			break;
		case D:
			y = y + YSPEED;
			break;
		case LD:
			x = x - XSPEED;
			y = y + YSPEED;
			break;
		}

		if (x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HIGHT) {
			 Live = false;
			 tc.missiles.remove(this);
		}
	}

	public boolean isLive() {
		return Live;
	}

}
