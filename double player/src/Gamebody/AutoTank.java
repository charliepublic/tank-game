package Gamebody;


import java.util.Random;

public class AutoTank extends Tank{
	private byte turnRatio = 100; 		//ת�����
	private byte fireRatio = 5;		//�����ڵ�����
	private Random random= new Random();
	private byte lifeValue = 3; 		//̹�˵�ǰ����ֵ
	//���캯��
	public AutoTank(int ax,int ay){
		super(ax,ay);
	}
	//��д�˺�����ÿ���ƶ�����һ���Ļ���ת��
	public void turnDirection(){
		switch(random.nextInt(turnRatio)){//�ٷ�֮4�ļ��ʻ���
		case 1:	setDirection(Direction.LEFT);	break;
		case 2: setDirection(Direction.UP);		break;
		case 3: setDirection(Direction.RIGHT);	break;
		case 4:	setDirection(Direction.DOWN);	break;
		}
	}
	//��д�˺���������ȴʱ�䵽����һ���Ļ��ʷ����ڵ�
	public Shell fire(){
		Shell shell = super.fire();
		if(shell!=null){
			if(random.nextInt(fireRatio)!=1){//�ķ�֮һ���ʷ����ӵ�
				shell = null;
			}
		}
		return shell;		
	}
	
	public void damage(byte aValue){
		if(lifeValue > 0)
			lifeValue -= aValue;
		else 
			super.setAlive(false);
	}
}
