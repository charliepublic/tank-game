package Gamebody;
import UI.TankGameWindow;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

public class Block {
	private final byte SIZE=50;		//��С
	public int x;					//ʯ�����Ͻ�����
	public int y;
	private byte number=1;			//ʯ������ķ������
	private boolean isVertical=true;//ʯ���Ƿ���������	
	private byte NumberTotal =3;//���ɼ���ǽ
	private int WidthNumber=TankGameWindow.GAME_WIDTH/SIZE;
	private int HeightNumber=TankGameWindow.GAME_HEIGHT/SIZE;

	
	//�Զ��幹�캯��,�������ʯ��
	public Block(){
		Random rand= new Random();				
		isVertical = rand.nextBoolean();
		if(isVertical){
			number = (byte)(rand.nextInt(NumberTotal)+3);//���ʯͷ������
			x = rand.nextInt(WidthNumber)*SIZE+SIZE;//ʹ��һ�в�����ʯͷ����ʹʯͷ��˳��
			y = rand.nextInt(HeightNumber-number)*SIZE+SIZE+50;	//ʹ��һ�в�����ʯͷ����ʹʯͷ��˳��
		}else{	
			number = (byte)(rand.nextInt(NumberTotal)+3);
			x = rand.nextInt(WidthNumber-number)*SIZE+SIZE;	//ʹ��һ�в�����ʯͷ����ʹʯͷ��˳��
			y = rand.nextInt(HeightNumber)*SIZE+SIZE+50;//ʹ��һ�в�����ʯͷ����ʹʯͷ��˳��
		}
	}
	//���캯�����أ�ָ����С����block
	public Block(int ax,int ay,byte aNumber,boolean aIsV){
		x = ax;
		y = ay;
		number = aNumber;
		isVertical = aIsV;
	}
	//֧����ײ̽�⣬���ش�����ľ���
	public Rectangle getRect(){
		Rectangle rect=null;
		if(isVertical)
			rect = new Rectangle(x,y,SIZE,SIZE*number);
		else
			rect = new Rectangle(x,y,SIZE*number,SIZE);
		return rect;
	}

	//�˺���Ϊÿһ֡������õĺ���
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
