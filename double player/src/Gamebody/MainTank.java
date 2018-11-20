package Gamebody;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class MainTank extends Tank {
	private byte LIFTVALUE=30; 	//坦克的满生命值数
	private static byte lifeValue = 1;		//坦克当前生命值
	
	public MainTank(int ax,int ay){
		super(ax,ay);
	}
	
	public byte getLifeValue(){
		return lifeValue;
	}
	
	public void addLifeValue(){
		lifeValue ++;
	}
	
	public void damage(byte aValue){
		if(lifeValue > 0)
			lifeValue -= aValue;
		else 
			super.setAlive(false);
	}
	
	public boolean isHitByItem(Item item){
		boolean result = false;
		//得到代表子弹的矩形
		Rectangle rect1 = new Rectangle(item.x-item.SIZE/2, item.y-item.SIZE/2, item.SIZE,item.SIZE);
		//得到代表坦克的矩形
		Rectangle rect2 = new Rectangle(x-SIZE/2,y-SIZE/2,SIZE,SIZE);
		if(rect1.intersects(rect2)){
			result = true;
			item.isAlive = false;
		}
		return result;
	}
	
	public void draw(Graphics g){
		
		super.draw(g);

		//绘制血条，血条颜色为红色
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.drawRect(x-LIFTVALUE/2, y-SIZE+10, LIFTVALUE,4); //绘制血条空的部分
		g.fillRect(x-LIFTVALUE/2, y-SIZE+10, lifeValue,4);//绘制血条满的部分
		g.setColor(c);		
	}

}
