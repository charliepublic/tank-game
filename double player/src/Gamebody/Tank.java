package Gamebody;
import UI.TankGameWindow;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Tank {
	public final byte  SIZE = 40;         	//坦克的大小
	private final byte  FIRECOOLTIME=10;  	//坦克的炮弹冷却时间
	private final byte ANIMATIONDIE=10;    	//爆炸动画帧数
	private final int FrameTitleHigh=70;//窗体标题的高度
	public int x;                         	//坦克中心的x坐标
	public int  y;							//坦克中心的y坐标
	private Direction  dir=Direction.UP;	//坦克方向
	private byte  velocity = 10;				//坦克速度	
	private boolean  isAlive=true;			//坦克是否还活着
	private boolean  isVisible=true;		//坦克是否可见
	private byte  dieState = 0;				//坦克爆炸动画状态
	private byte fireTime = 0; 				//记录自上次发射炮弹以来的帧数


	public Tank(int ax,int ay){
		x = ax;
		y = ay;
	}
	public void setDirection(Direction adir){
		dir = adir;
	}
	public boolean getVisible(){
		return isVisible;
	}
	public boolean getAlive(){
		return isAlive;
	}
	public void setAlive(boolean alive){
		isAlive = alive; 
	}
	public byte Tankvelocity(){
		return velocity;
	}
	public boolean isHitByShell(Shell shell){
		boolean result = false;
		//得到代表子弹的矩形
		Rectangle rect1 = new Rectangle(shell.x-shell.SIZE/2, shell.y-shell.SIZE/2, shell.SIZE,shell.SIZE);
		//得到代表坦克的矩形
		Rectangle rect2 = new Rectangle(x-SIZE/2,y-SIZE/2,SIZE,SIZE);
		if(rect1.intersects(rect2))
			result = true;
		return result;
	}
	
	//支持碰撞探测，返回代表坦克的矩形
	public Rectangle getRect(){
		return new Rectangle(x-SIZE/2,y-SIZE/2,SIZE,SIZE);
	}
	
	//根据当前坦克方向计算下一步的坦克坐标
	private int[] nextPosition(){
		int[] result = new int[2];
		result[0]=x;				//存储坦克下一个位置的x坐标
		result[1]=y;				//存储坦克下一个位置的y坐标
		//根据行驶方向计算坦克的下一个位置
		switch(dir){
		case LEFT:	
			if(result[0]-SIZE/2-velocity>=0)
				result[0] -= velocity;
			break;
		case RIGHT:
			if(result[0]+ SIZE/2+velocity<=TankGameWindow.GAME_WIDTH)
				result[0] += velocity;
			break;
		case UP:
			if(result[1]-SIZE/2-velocity>=FrameTitleHigh)
				result[1] -= velocity;
			break;
		case DOWN:
			if(result[1]+ SIZE/2+velocity<=TankGameWindow.GAME_HEIGHT)
				result[1] += velocity;
			break;			
		}
		return result;
	}
	
	//得到移动坦克后的下一个位置
	public Rectangle getNextRect(){
		int[] tmpP=nextPosition();
		return new Rectangle(tmpP[0]-SIZE/2,tmpP[1]-SIZE/2,SIZE,SIZE);
	}
	
	//在每一帧 里设置各种状态
	protected void calculateState(){
		//记录发射冷却帧数	
		if(fireTime<=FIRECOOLTIME)		fireTime++;   
		//记录爆炸动画状态
		if(!isAlive){
			if(dieState<ANIMATIONDIE)		
				dieState++;
			else
				isVisible = false;
		}
		
	}
	
	//每帧都会执行的函数
	public void draw(Graphics g){
		calculateState();			//每帧计算状态 
		drawTank(g);
		if((!isAlive)&&(isVisible))
			drawExplode(g);			//绘制爆炸动画		
	}	
	
	//绘制爆炸动画函数
	private void drawExplode(Graphics g){				
		Image explode = Toolkit.getDefaultToolkit().getImage("explodepicture/34.png");
		g.drawImage(explode, x-25, y-25, SIZE, SIZE, null);
	}
	
	//绘制坦克函数
	private void drawTank(Graphics g){		
		switch(dir){
		case LEFT:			
			Image TankToL = Toolkit.getDefaultToolkit().getImage("normalpicture/1L.png");
			g.drawImage(TankToL, x-25, y-25, SIZE, SIZE, null);
			break;
		case RIGHT:
			Image TankToR = Toolkit.getDefaultToolkit().getImage("normalpicture/1R.png");
			g.drawImage(TankToR,  x-25, y-25, SIZE, SIZE, null);
			break;
		case UP:
			Image TankToU = Toolkit.getDefaultToolkit().getImage("normalpicture/1U.png");
			g.drawImage(TankToU,  x-25, y-25, SIZE, SIZE, null);
			break;
		case DOWN:
			Image TankToD = Toolkit.getDefaultToolkit().getImage("normalpicture/1D.png");
			g.drawImage(TankToD,  x-25, y-25, SIZE, SIZE, null);
			break;			
		}
	}

	//移动坦克
	public void move(){
		int[] tmpP=nextPosition();
		x = tmpP[0];
		y = tmpP[1];
	}
	//发射炮弹函数
	public Shell fire(){
    	Shell shell=null;
    	
    	if(fireTime>=FIRECOOLTIME){
    		fireTime = 0;           //重置炮弹冷却时间     		
	    	int shellx=0,shelly=0;	//炮弹的初始位置
	    	switch(dir){
			case LEFT:	shellx = x-24;	shelly = y;	break;
			case RIGHT:	shellx = x+24; 	shelly = y;	break;
			case UP:	shellx = x;  	shelly = y-24;	break;
			case DOWN:	shellx = x;  	shelly = y+24;	break;
			}
	    	shell = new Shell(shellx,shelly);
	    	shell.setDirection(dir);
		}    	
    	return shell;
    }
	
	public Shell superFire(){
    	SuperShell shell=null;
    	
    	if(fireTime>=FIRECOOLTIME){
    		fireTime = 0;           //重置炮弹冷却时间     		
	    	int shellx=0,shelly=0;	//炮弹的初始位置
	    	switch(dir){
			case LEFT:	shellx = x-24;	shelly = y;	break;
			case RIGHT:	shellx = x+24; 	shelly = y;	break;
			case UP:	shellx = x;  	shelly = y-24;	break;
			case DOWN:	shellx = x;  	shelly = y+24;	break;
			}
	    	shell = new SuperShell(shellx,shelly);
	    	shell.setDirection(dir);
		}    	
    	return shell;
    }
}
