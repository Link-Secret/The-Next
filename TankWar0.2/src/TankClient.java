import java.awt.*;
import java.awt.event.*;
public class TankClient extends Frame{
   
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
		
	}
	  

	public static void main(String[] args) {
		
           TankClient tc = new TankClient();
           tc.lauchFrame();
	}

}
