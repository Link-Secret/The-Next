import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {
	private int x, y;
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	private boolean bL = false, bU = false, bR = false, bD = false;

	enum Direction {
		L, LU, U, RU, R, RD, D, LD, STOP
	};

	private Direction dir = Direction.STOP;

	public Tank(int x, int y) {

		this.x = x;
		this.y = y;
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30); // 坦克的起始坐标，和宽和高。
		g.setColor(c);

		move();
	}

	void move() {
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
		case STOP:
			break;
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		lacateDirection();
	}

	void lacateDirection() {
		if (bL && !bU && !bR && !bD) {
			dir = Direction.L;
		}
		if (bL && bU && !bR && !bD) {
			dir = Direction.LU;
		}
		if (!bL && bU && !bR && !bD) {
			dir = Direction.U;
		}
		if (!bL && bU && bR && !bD) {
			dir = Direction.RU;
		}
		if (!bL && !bU && bR && !bD) {
			dir = Direction.R;
		}
		if (!bL && !bU && bR && bD) {
			dir = Direction.RD;
		}
		if (!bL && !bU && !bR && bD) {
			dir = Direction.D;
		}
		if (bL && !bU && !bR && bD) {
			dir = Direction.LD;
		}
		if (!bL && !bU && !bR && !bD) {
			dir = Direction.STOP;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		lacateDirection();
	}

}
