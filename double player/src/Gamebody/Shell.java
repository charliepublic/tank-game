package Gamebody;
import UI.TankGameWindow;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import UI.GameMenuUI;

public class Shell {	
	private final byte ANIMATIONDIE=5;		//爆炸动画帧数
	public  final byte SIZE=10;				//炮弹直径		
	public int x;							//炮弹坐标
	public int y;
	private byte velocity = 15;            	//炮弹的速度	
	private Direction dir = Direction.UP;	//炮弹方向	
	private byte damageValue=1;				//炮弹的伤害值
	private boolean  isAlive=true;			//炮弹是否还活着
	private boolean  isVisible=true;		//炮弹是否可见
	private Image IMGS[]=
		{
		  Toolkit.getDefaultToolkit().getImage("explodepicture/10.png"),
		  Toolkit.getDefaultToolkit().getImage("explodepicture/20.png"),
		  Toolkit.getDefaultToolkit().getImage("explodepicture/30.png"),
		  Toolkit.getDefaultToolkit().getImage("explodepicture/32.png"),
		  Toolkit.getDefaultToolkit().getImage("explodepicture/34.png"),


		};
	private byte dieState = 0;      		//爆炸动画状态
		
	//自定义构造函数
	public Shell(int ax,int ay){
		x = ax;
		y = ay;		
	}
	public void setDirection(Direction d){
		dir = d;
	}
	public void setAlive(boolean s){
		isAlive = s;		
	}
	public boolean getAlive(){
		return isAlive;
	}
	public boolean getVisible(){
		return isVisible;
	}
	public byte getDamageValue(){
		return damageValue;
 	}
	//支持碰撞探测，返回代表炮弹的矩形
	public Rectangle getRect(){
		return new Rectangle(x-SIZE/2, y-SIZE/2, SIZE,SIZE);
	}
	//此函数为每一帧都会调用的函数
	public void draw(Graphics g){
			if(!isAlive){
	
				if(dieState<ANIMATIONDIE) {
			  if(dieState==0){
//		    	  GameMenuUI.gameM.musicRun("explode");
		     } 
				g.drawImage(IMGS[dieState],x-SIZE/2, y-SIZE/2,SIZE, SIZE, null);
				dieState++;
				}	else
				isVisible = false;
		}
		if(isAlive){        
			
				Image Shell1 = Toolkit.getDefaultToolkit().getImage("normalpicture/Shell1.png");
				g.drawImage(Shell1, x-SIZE/2, y-SIZE/2, SIZE, SIZE, null);
		}
		
	
	}
		
	//炮弹的移动函数，超出边界爆炸
	public void move(){
		//进行边界探测
		boolean flag = true;
		if((x<0) || (x>TankGameWindow.GAME_WIDTH)
		||(y<50) || (y>TankGameWindow.GAME_HEIGHT)){
			isAlive = false;
			flag = false;
		}
		//坦克活着，同时没有碰到边界则移动
		if(isAlive && flag){
			switch(dir){
			case LEFT:	x -= velocity;	break;
			case RIGHT:	x += velocity;	break;
			case UP:	y -= velocity;	break;
			case DOWN:	y += velocity;	break;
			}		
		}
	}
}
