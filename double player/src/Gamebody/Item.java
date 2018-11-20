package Gamebody;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

public class Item {
		public final byte SIZE = 50;
		public int x;
		public int y;
		private int i = 0;
		private Random random= new Random();
		private Image itemJpg;
		public boolean isAlive=true;
		private Tank tank;
		
		public Item(int ax,int ay,Tank tank){
			this.x = ax;
			this.y = ay;
			getItem();
		}
		
		public void getItem(){//这里可以添加不同的东西
			i = random.nextInt(5);
			if(i == 0){
			itemJpg  = Toolkit.getDefaultToolkit().getImage("normalpicture/rocks.png");//需要更改图片
				tank.setAlive(false);//设置一碰就死的东西
			}else if(i == 1){
				
			}else if(i == 2){
				
			}else if(i == 3){
				
			}else if(i == 4){
				
			}
			
		}
		
		public Rectangle getRect(){
			Rectangle rect=null;
			rect = new Rectangle(x-SIZE/2, y-SIZE/2, SIZE,SIZE);
			return rect;
		}
		
		public void draw(Graphics g){
			int tmpx,tmpy;
			tmpx = x;
			tmpy = y;
				g.drawImage(itemJpg , tmpx-SIZE/2, tmpy-SIZE/2, SIZE, SIZE, null);
			}
}
