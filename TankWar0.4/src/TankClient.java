import java.awt.*;
import java.awt.event.*;
public class TankClient extends Frame{
    int x = 50;
    int y = 50;
	
	public void paint(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);   //坦克的起始坐标，和宽和高。
		g.setColor(c);
		
		y = y+5;
	}

	public void lauchFrame(){
	
		
		this.setLocation(400,300); //location  起始点为左上角
		this.setSize(800,600);    //size
		this.setTitle("TankWar");
		this.setResizable(false);// Resizable-- 缩放 
		this.addWindowListener(new WindowAdapter() {  //窗口监听器        匿名内部类       窗口适配器

			
			public void windowClosing(WindowEvent e) {
			    System.exit(0);
			}
		});
		setVisible(true);        //visible 是否可视
		this.setBackground(Color.GREEN);    //背景颜色
		
		new Thread(new PaintThread()).start();
	}
	  


	public static void main(String[] args) {
		
           TankClient tc = new TankClient();
           tc.lauchFrame();
	}
	
	public class PaintThread implements Runnable{

		@Override
		public void run() {
		  while(true){
			  repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		}
		
	}

}
