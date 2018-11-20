package Gamebody;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class SuperShell extends Shell{

	public  final byte SIZE=30;				//�ڵ�ֱ��		
	private final byte ANIMATIONDIE=5;		//��ը����֡��
	public int x;							//�ڵ�����
	public int y;
	private byte velocity =30;            	//�ڵ����ٶ�	
	private Direction dir = Direction.UP;	//�ڵ�����	
	private byte damageValue=10;				//�ڵ����˺�ֵ
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
			
			Image Shell1 = Toolkit.getDefaultToolkit().getImage("normalpicture/ͼƬ2.png");
		     g.drawImage(Shell1, x-SIZE/2, y-SIZE/2, SIZE, SIZE, null);
		}
	}
}
