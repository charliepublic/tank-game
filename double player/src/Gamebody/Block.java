package Gamebody;
import UI.TankGameWindow;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

public class Block {
	private final byte SIZE=50;		//大小
	public int x;					//石块左上角坐标
	public int y;
	private byte number=1;			//石块包含的方块个数
	private boolean isVertical=true;//石块是否竖向排列	
	private byte NumberTotal =3;//生成几条墙
	private int WidthNumber=TankGameWindow.GAME_WIDTH/SIZE;
	private int HeightNumber=TankGameWindow.GAME_HEIGHT/SIZE;

	
	//自定义构造函数,随机产生石块
	public Block(){
		Random rand= new Random();				
		isVertical = rand.nextBoolean();
		if(isVertical){
			number = (byte)(rand.nextInt(NumberTotal)+3);//随机石头的条数
			x = rand.nextInt(WidthNumber)*SIZE+SIZE;//使第一列不出现石头并且使石头有顺序
			y = rand.nextInt(HeightNumber-number)*SIZE+SIZE+50;	//使第一行不出现石头并且使石头有顺序
		}else{	
			number = (byte)(rand.nextInt(NumberTotal)+3);
			x = rand.nextInt(WidthNumber-number)*SIZE+SIZE;	//使第一列不出现石头并且使石头有顺序
			y = rand.nextInt(HeightNumber)*SIZE+SIZE+50;//使第一行不出现石头并且使石头有顺序
		}
	}
	//构造函数重载，指定大小创建block
	public Block(int ax,int ay,byte aNumber,boolean aIsV){
		x = ax;
		y = ay;
		number = aNumber;
		isVertical = aIsV;
	}
	//支持碰撞探测，返回代表方块的矩形
	public Rectangle getRect(){
		Rectangle rect=null;
		if(isVertical)
			rect = new Rectangle(x,y,SIZE,SIZE*number);
		else
			rect = new Rectangle(x,y,SIZE*number,SIZE);
		return rect;
	}

	//此函数为每一帧都会调用的函数
	public void draw(Graphics g){
		Image blockjpg = Toolkit.getDefaultToolkit().getImage("normalpicture/rocks.png");
		int tmpx,tmpy;
		tmpx = x;
		tmpy = y;
		for(int i=0;i<number;i++){
			g.drawImage(blockjpg, tmpx, tmpy, SIZE, SIZE, null);
			if(isVertical)
				tmpy += SIZE;
			else
				tmpx += SIZE;			
		}		
	}	
}
