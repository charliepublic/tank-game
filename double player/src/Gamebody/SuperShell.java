package Gamebody;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class SuperShell extends Shell{

	public  final byte SIZE=30;				//炮弹直径		
	private final byte ANIMATIONDIE=5;		//爆炸动画帧数
	public int x;							//炮弹坐标
	public int y;
	private byte velocity =30;            	//炮弹的速度	
	private Direction dir = Direction.UP;	//炮弹方向	
	private byte damageValue=10;				//炮弹的伤害值
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
	public SuperShell(int ax,int ay){
		super(ax,ay);
	}
	
	
	public void draw(Graphics g){
		if(!isAlive){

			if(dieState<ANIMATIONDIE) {
		  if(dieState==0){
//	    	  GameMenuUI.gameM.musicRun("explode");
	     } 
			g.drawImage(IMGS[dieState],x-SIZE/2, y-SIZE/2,SIZE, SIZE, null);
			dieState++;
			}	else
			isVisible = false;
		}
		if(isAlive){        
			
			Image Shell1 = Toolkit.getDefaultToolkit().getImage("normalpicture/图片2.png");
		     g.drawImage(Shell1, x-SIZE/2, y-SIZE/2, SIZE, SIZE, null);
		}
	}
}
