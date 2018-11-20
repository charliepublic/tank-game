package Gamebody;
import UI.TankGameWindow;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import UI.GameMenuUI;

public class Shell {	
	private final byte ANIMATIONDIE=5;		//��ը����֡��
	public  final byte SIZE=10;				//�ڵ�ֱ��		
	public int x;							//�ڵ�����
	public int y;
	private byte velocity = 15;            	//�ڵ����ٶ�	
	private Direction dir = Direction.UP;	//�ڵ�����	
	private byte damageValue=1;				//�ڵ����˺�ֵ
	private boolean  isAlive=true;			//�ڵ��Ƿ񻹻���
	private boolean  isVisible=true;		//�ڵ��Ƿ�ɼ�
	private Image IMGS[]=
		{
		  Toolkit.getDefaultToolkit().getImage("explodepicture/10.png"),
		  Toolkit.getDefaultToolkit().getImage("explodepicture/20.png"),
		  Toolkit.getDefaultToolkit().getImage("explodepicture/30.png"),
		  Toolkit.getDefaultToolkit().getImage("explodepicture/32.png"),
		  Toolkit.getDefaultToolkit().getImage("explodepicture/34.png"),


		};
	private byte dieState = 0;      		//��ը����״̬
		
	//�Զ��幹�캯��
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
	//֧����ײ̽�⣬���ش����ڵ��ľ���
	public Rectangle getRect(){
		return new Rectangle(x-SIZE/2, y-SIZE/2, SIZE,SIZE);
	}
	//�˺���Ϊÿһ֡������õĺ���
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
		
	//�ڵ����ƶ������������߽籬ը
	public void move(){
		//���б߽�̽��
		boolean flag = true;
		if((x<0) || (x>TankGameWindow.GAME_WIDTH)
		||(y<50) || (y>TankGameWindow.GAME_HEIGHT)){
			isAlive = false;
			flag = false;
		}
		//̹�˻��ţ�ͬʱû�������߽����ƶ�
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
