package Gamebody;
import UI.TankGameWindow;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Tank {
	public final byte  SIZE = 40;         	//̹�˵Ĵ�С
	private final byte  FIRECOOLTIME=10;  	//̹�˵��ڵ���ȴʱ��
	private final byte ANIMATIONDIE=10;    	//��ը����֡��
	private final int FrameTitleHigh=70;//�������ĸ߶�
	public int x;                         	//̹�����ĵ�x����
	public int  y;							//̹�����ĵ�y����
	private Direction  dir=Direction.UP;	//̹�˷���
	private byte  velocity = 10;				//̹���ٶ�	
	private boolean  isAlive=true;			//̹���Ƿ񻹻���
	private boolean  isVisible=true;		//̹���Ƿ�ɼ�
	private byte  dieState = 0;				//̹�˱�ը����״̬
	private byte fireTime = 0; 				//��¼���ϴη����ڵ�������֡��


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
		//�õ������ӵ��ľ���
		Rectangle rect1 = new Rectangle(shell.x-shell.SIZE/2, shell.y-shell.SIZE/2, shell.SIZE,shell.SIZE);
		//�õ�����̹�˵ľ���
		Rectangle rect2 = new Rectangle(x-SIZE/2,y-SIZE/2,SIZE,SIZE);
		if(rect1.intersects(rect2))
			result = true;
		return result;
	}
	
	//֧����ײ̽�⣬���ش���̹�˵ľ���
	public Rectangle getRect(){
		return new Rectangle(x-SIZE/2,y-SIZE/2,SIZE,SIZE);
	}
	
	//���ݵ�ǰ̹�˷��������һ����̹������
	private int[] nextPosition(){
		int[] result = new int[2];
		result[0]=x;				//�洢̹����һ��λ�õ�x����
		result[1]=y;				//�洢̹����һ��λ�õ�y����
		//������ʻ�������̹�˵���һ��λ��
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
	
	//�õ��ƶ�̹�˺����һ��λ��
	public Rectangle getNextRect(){
		int[] tmpP=nextPosition();
		return new Rectangle(tmpP[0]-SIZE/2,tmpP[1]-SIZE/2,SIZE,SIZE);
	}
	
	//��ÿһ֡ �����ø���״̬
	protected void calculateState(){
		//��¼������ȴ֡��	
		if(fireTime<=FIRECOOLTIME)		fireTime++;   
		//��¼��ը����״̬
		if(!isAlive){
			if(dieState<ANIMATIONDIE)		
				dieState++;
			else
				isVisible = false;
		}
		
	}
	
	//ÿ֡����ִ�еĺ���
	public void draw(Graphics g){
		calculateState();			//ÿ֡����״̬ 
		drawTank(g);
		if((!isAlive)&&(isVisible))
			drawExplode(g);			//���Ʊ�ը����		
	}	
	
	//���Ʊ�ը��������
	private void drawExplode(Graphics g){				
		Image explode = Toolkit.getDefaultToolkit().getImage("explodepicture/34.png");
		g.drawImage(explode, x-25, y-25, SIZE, SIZE, null);
	}
	
	//����̹�˺���
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

	//�ƶ�̹��
	public void move(){
		int[] tmpP=nextPosition();
		x = tmpP[0];
		y = tmpP[1];
	}
	//�����ڵ�����
	public Shell fire(){
    	Shell shell=null;
    	
    	if(fireTime>=FIRECOOLTIME){
    		fireTime = 0;           //�����ڵ���ȴʱ��     		
	    	int shellx=0,shelly=0;	//�ڵ��ĳ�ʼλ��
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
    		fireTime = 0;           //�����ڵ���ȴʱ��     		
	    	int shellx=0,shelly=0;	//�ڵ��ĳ�ʼλ��
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
