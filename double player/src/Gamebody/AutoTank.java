package Gamebody;


import java.util.Random;

public class AutoTank extends Tank{
	private byte turnRatio = 100; 		//转向机率
	private byte fireRatio = 5;		//发射炮弹机率
	private Random random= new Random();
	private byte lifeValue = 3; 		//坦克当前生命值
	//构造函数
	public AutoTank(int ax,int ay){
		super(ax,ay);
	}
	//重写此函数在每次移动后以一定的机率转向
	public void turnDirection(){
		switch(random.nextInt(turnRatio)){//百分之4的几率换向
		case 1:	setDirection(Direction.LEFT);	break;
		case 2: setDirection(Direction.UP);		break;
		case 3: setDirection(Direction.RIGHT);	break;
		case 4:	setDirection(Direction.DOWN);	break;
		}
	}
	//重写此函数，在冷却时间到后，以一定的机率发射炮弹
	public Shell fire(){
		Shell shell = super.fire();
		if(shell!=null){
			if(random.nextInt(fireRatio)!=1){//四分之一概率发射子弹
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
