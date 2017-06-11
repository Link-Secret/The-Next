import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tank {
	private int x, y;
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;

	public static final int WIDTH = 30;
	public static final int HIGHT = 30;
	
	private boolean live = true;
	
	private int life = 100;

 
	private static Random r = new Random();

	TankClient tc;
	
	private boolean good;
	
	private int oldx,oldy;


	private boolean bL = false, bU = false, bR = false, bD = false;

	enum Direction {
		L, LU, U, RU, R, RD, D, LD, STOP
	};

	private Direction dir = Direction.STOP;

	private Direction ptDir = Direction.D;
	
	private int step = r.nextInt(12) + 3 ;

	public Tank(int x, int y , boolean good) {

		this.x = x;
		this.y = y;
		
		this.oldx = x;
		this.oldy = y;
		
		this.good = good;
	}

	public Tank(int x, int y, boolean good, Direction dir,TankClient tc) {
		this(x, y,good);
		this.dir = dir;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		if(!live) {
			if(!good){
				tc.tanks.remove(this);
			}
			return;
		}
			
		
		Color c = g.getColor();
		if(good == true) g.setColor(Color.RED);
		else g.setColor(Color.BLUE);
		g.fillOval(x, y, WIDTH, HIGHT); // 坦克的起始坐标，和宽和高。
		g.setColor(c);

		switch (ptDir) {
		case L:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HIGHT / 2, x, y
					+ Tank.HIGHT / 2);
			break;
		case LU:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HIGHT / 2, x, y);
			break;
		case U:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HIGHT / 2, x + Tank.WIDTH
					/ 2, y);
			break;
		case RU:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HIGHT / 2, x + Tank.WIDTH,
					y );
			break;
		case R:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HIGHT / 2, x + Tank.WIDTH,
					y + Tank.HIGHT / 2);
			break;
		case RD:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HIGHT / 2, x + Tank.WIDTH,
					y + Tank.HIGHT);
			break;
		case D:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HIGHT / 2, x + Tank.WIDTH
					/ 2, y + Tank.HIGHT);
			break;
		case LD:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HIGHT / 2, x, y
					+ Tank.HIGHT);
			break;

		}

		move();
	}

	void move() {
		
		this.oldx = x;
		this.oldy = y;
		
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

		if (this.dir != Direction.STOP) {
			ptDir = this.dir;
		}
		
		if(x<0) x=0;
		if(x>TankClient.GAME_WIDTH-Tank.WIDTH) x=TankClient.GAME_WIDTH-Tank.WIDTH;
		if(y<30) y=30;
		if(y>TankClient.GAME_HIGHT-Tank.HIGHT) y=TankClient.GAME_HIGHT-Tank.HIGHT;
		
		if(!good){
			Direction[] dirs = Direction.values();  //将Direction置于数组dirs中
			if(step==0){
		    step = r.nextInt(12) + 3;       //新的方向的随机步数
			int rn = r.nextInt(dirs.length);     //将数组的长度设置为随机整数的上限赋值给RandomNumber(rn) 0--dirs.length-1
			dir = dirs[rn];  //方向为数组dirs中的第rn个数所代表的方向
			}
			step --;   
			
			if(r.nextInt(40)>36) this.fire();
		}
		
	

	}
	
	private void stay(){
		x = oldx;
		y = oldy;
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
		case KeyEvent.VK_CONTROL:   //当按键抬起时候fire开火。
			fire();
			break;
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
		case KeyEvent.VK_A:
			superFire();
			break;
		}
		lacateDirection();
	}

	public Missile fire() {
		if(!live) return null;
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HIGHT / 2 - Missile.HIGHT / 2;
		Missile m = new Missile(x, y, good, ptDir,this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	public Missile fire(Direction dir) {
		if(!live) return null;
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HIGHT / 2 - Missile.HIGHT / 2;
		Missile m = new Missile(x, y, good, dir,this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH,HIGHT);
	}
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
	public boolean isGood() {
		return good;
	}
   
	public boolean CollidesWithWall(Wall w){
		if(this.live && this.getRect().intersects(w.getRect())){
			this.stay();  //stay 方法，回到相撞前的坐标。坦克不会撞墙。
			return true;
			}
		return false;
	}
	
	public boolean CollidesWithTanks(java.util.List<Tank> tanks){
		for(int i= 0; i<tanks.size();i++){
			Tank t = tanks.get(i);
	       if(this != t){
		      if(this.live && t.isLive() && this.getRect().intersects(t.getRect())){ 
		    	                          //this.getRect().intersects(t.getRect()) intersect方法（相交）
			     this.stay();
			     t.stay();
			     return true;
			  }
		     }
	        }
		return false;
	  }
	
	public void superFire(){
		Direction[] dirs =Direction.values();  //Enum还提供了values方法，这个方法使你能够方便的遍历所有的枚举值。
		for(int i=0;i<8;i++){
			fire(dirs[i]);
		}
	}
	
	   public int getLife() {
			return life;
		}

		public void setLife(int life) {
			this.life = life;
		}

	
	}
 

