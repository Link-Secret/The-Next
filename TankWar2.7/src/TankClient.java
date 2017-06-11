/*
 * 4.1的改进主要是为了优化坦克的下落显示。4.0中下落有明显的闪烁--主要是因为 坦克还没paint（画出来）就刷新了（频率越高闪烁越明显）
 * 双缓冲消除闪烁原理：假设有个背面的screen（屏幕），当每次paint时候，先在背后屏幕上画出来，再显示在真正的屏幕上，这样就不会出现闪烁现象了。
 * 每次paint重叠：在每次背面的画出来前都要刷新背景色，因为如果不用update方法，会自动刷新背景色。所以要刷新背景色
 * */

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class TankClient extends Frame {
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HIGHT = 600;

	Tank myTank = new Tank(50, 50, true,Direction.STOP, this);
	
	Wall w1 = new Wall(200,200,20,150,this), w2 = new Wall(300,100,300,20,this);

	List<Explode> Explodes = new ArrayList<Explode>();
	List<Missile> missiles = new ArrayList<Missile>();
	List<Tank> tanks = new ArrayList<Tank>();

	Image offScreenImage = null;
	
	Blood b = new Blood();

	public void paint(Graphics g) {

		g.drawString("missiles count: " + missiles.size(), 10, 50);
		g.drawString("Explodes count: " + Explodes.size(), 10, 70);
		g.drawString("Tanks    count: " + tanks.size(), 10, 90);
		g.drawString("Tanks    life: " + myTank.getLife(), 10, 110);
		
		if(tanks.size() <= 0){
			for(int i=0;i<5;i++){
				tanks.add(new Tank(50*(i+1),50,false,Direction.D,this));
			}
		}

		for (int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
			// if(!m.isLive()) missiles.remove(m);
			// else m.draw(g);
			m.draw(g);
		}

		for (int i = 0; i < Explodes.size(); i++) {
			Explode e = Explodes.get(i);
			e.draw(g);
		}

		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.CollidesWithWall(w1);
			t.CollidesWithWall(w2);
			t.CollidesWithTanks(tanks);
			t.draw(g);
		}
		
		

		myTank.draw(g);
		myTank.eat(b);
        w1.draw(g);
        w2.draw(g);
        b.draw(g);
	}

	@Override
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics(); // 画在背面图片
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HIGHT); // 背景色画出一个方块出来
		gOffScreen.setColor(c);
		paint(gOffScreen); // 调用paint方法

		g.drawImage(offScreenImage, 0, 0, null); // 将背后图片画到前面screen上
	}

	public void lauchFrame() {

		for (int i = 0; i < 10; i++) {
			tanks.add(new Tank(50 + 40 * (i + 1), 50, false,
					Direction.D, this));
		}

		this.setLocation(400, 300); // location 起始点为左上角
		this.setSize(GAME_WIDTH, GAME_HIGHT); // size
		this.setTitle("TankWar");
		this.setResizable(false);// Resizable-- 缩放
		this.addWindowListener(new WindowAdapter() { // 窗口监听器 匿名内部类 窗口适配器

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setVisible(true); // visible 是否可视
		this.setBackground(Color.GREEN); // 背景颜色

		this.addKeyListener(new keyMonitor());

		new Thread(new PaintThread()).start();
	}

	public static void main(String[] args) {

		TankClient tc = new TankClient();
		tc.lauchFrame();
	}

	public class PaintThread implements Runnable {

		@Override
		public void run() {
			while (true) {
				repaint(); // 重画
				try {
					Thread.sleep(50); // 50毫秒刷新
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public class keyMonitor extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}

	}
	

}
