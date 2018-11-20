package Gamebody;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import UI.TankGameWindow;

public class TankGameMgr implements ActionListener {
	private MainTank mainTank;//主坦克1
	private MainTank mainTank2;	//主坦克2
	private int SIZE=50;//坦克的大小
	private List<MainTank> mainTanks = new ArrayList<>();
	private List<Shell> mainShells 	= new ArrayList<Shell>();	//主坦克发射的炮弹列表
	private List<AutoTank> autoTanks= new ArrayList<AutoTank>();//自动坦克列表
	private List<Shell> autoShells 	= new ArrayList<Shell>();	//自动坦克发射的炮弹列表
	private List<Block> blocks = new ArrayList<Block>();		//障碍物列表
	private List<Item> items = new ArrayList<Item>();		//物品列表
	private short createTankRatio=60;						 	//创建坦克的机率
	public  int NumAuto =0;										//自动坦克的当前数量
	public  int number=5;		//能够生成的总的自动坦克数目
	public  int numberalive = 5;//自动坦克存活数量
	private final int FrameTitleHigh=70;						//窗体标题的高度
	private Random random = new Random();			//随机方向
	public boolean gameover = true;
	public Music music;
	public  int m ;
	public boolean getGameOver(){
		return gameover;
	}
	
	public void initGame(int player){
		Random rand = new Random();
		//生成主坦克
		if(player== 1){
		mainTank = new MainTank(300,55+SIZE/2);
		mainTanks.add(mainTank);
		}else if(player == 2){
		mainTank = new MainTank(300,55+SIZE/2);
		mainTanks.add(mainTank);
		mainTank2 = new MainTank(400,55+SIZE/2);
		mainTanks.add(mainTank2);
		m = 2;
		}
		//生机随机数量的障碍物块
		int num = rand.nextInt(10)+3;
		for(int i=0;i<num;i++)
			blocks.add(new Block());
	}
	//以4/4的机率创建坦克
	private void createAutoTank(){
		AutoTank tank=null;
		int r1 = random.nextInt(createTankRatio); //是否生成坦克，在哪个角生成坦克
		int r2 = random.nextInt(2); //坦克的运行方向
		switch(r1){
		case 0:           //左上角
			tank = new AutoTank(SIZE/2,FrameTitleHigh+SIZE/2);
			if(r2==0)tank.setDirection(Direction.RIGHT);
			else	 tank.setDirection(Direction.DOWN);
			break;
		case 1:            //右上角
			tank = new AutoTank(950+SIZE/2,FrameTitleHigh+SIZE/2);
			if(r2==0)tank.setDirection(Direction.LEFT);
			else  	 tank.setDirection(Direction.DOWN);
			break;
		case 2:            //右下角
			tank = new AutoTank(950+SIZE/2,750+SIZE/2);
			if(r2==0)tank.setDirection(Direction.LEFT);
			else 	 tank.setDirection(Direction.UP);
			break;
		case 3:				//左下角
			tank = new AutoTank(SIZE/2,750+SIZE/2);
			if(r2==0)tank.setDirection(Direction.RIGHT);
			else	 tank.setDirection(Direction.UP);			
		}
		if((tank != null)){
			NumAuto++;
			autoTanks.add(tank);
		}
			
	}
	//检测坦克与blocks的碰撞
	private boolean isTankHitBlock(Tank tank){
		boolean result=false;
		for(int i=0;i<blocks.size();i++){
			Block block = blocks.get(i);
			//探测坦克的下一步是否会碰到障碍物
			if(block.getRect().intersects(tank.getNextRect()))
				result = true;
		}
		return result;
	}

	private boolean isAutoTankHitTank(Tank tank){
		boolean result=false;
		for(int i=0;i<autoTanks.size();i++){
			Tank ptank =autoTanks.get(i);
			//探测坦克的下一步是否会碰到障碍物
			if(ptank!=tank)
				if(ptank.getRect().intersects(tank.getNextRect()))
					result = true;
		}
		if(mainTank.getRect().intersects(tank.getNextRect()))
			result = true;
		if(m ==2){
		if(mainTank2.getRect().intersects(tank.getNextRect()))
			result = true;
		}
		
		return result;
	}
	//检测主坦克与坦克的碰撞
	private boolean isMainTankHitTank(Tank tank){
		boolean result=false;
		for(int i=0;i<autoTanks.size();i++){
			Tank ptank =autoTanks.get(i);
			//探测坦克的下一步是否会碰到障碍物
			if(ptank.getRect().intersects(tank.getNextRect()))
				result = true;
		}
		return result;
	}
	//检测炮弹与blocks的碰撞
	private boolean isShellHitBlock(Shell shell){
		boolean result=false;
		for(int i=0;i<blocks.size();i++){
			Block block = blocks.get(i);
			if(block.getRect().intersects(shell.getRect()))
				result = true;			
		}
		return result;
	}
	//处理、移动、绘制主坦克
	private void handleDrawMainTank(Graphics g) {
		//移动、绘制主坦克
		for (MainTank m : mainTanks) {
			if (m.getAlive()) {
				m.draw(g);
			}
			if (!mainTank.getAlive()) {
				clear();
				Image over = Toolkit.getDefaultToolkit().getImage("normalpicture/5.jpg");
				g.drawImage(over, 0, 0, TankGameWindow.GAME_WIDTH, TankGameWindow.GAME_HEIGHT, null);
				gameover =false;
				
				Music  M = new Music("lose");
			}
		}
	}
	//isMain==true 处理、移动、绘制主坦克炮弹
	//isMain==false处理、移动、绘制自动坦克炮弹
	private void handleDrawShells(Graphics g,boolean isMain){
		List<Shell> tmpList;
		Shell shell;
		AutoTank tank;
		
		if(isMain)  tmpList = mainShells;	//如果isMain==true，处理主坦克炮弹		
		else 		tmpList = autoShells;	//如果isMain==true，处理自动坦克炮弹
		
		//遍历炮弹，检查、计算、移动并绘制		
		for(int i=tmpList.size()-1;i>=0;i--){
			shell = (Shell)tmpList.get(i);
			if(shell.getAlive()){
				if(isShellHitBlock(shell))//检查炮弹是否碰到障碍物
					shell.setAlive(false);
				else{
					shell.move();	//移动炮弹
					if(isMain){		//如果是主坦克炮弹
						//遍历所有的自动坦克，看该炮弹是否击中之
						for(int j=autoTanks.size()-1;j>=0;j--){
							tank = autoTanks.get(j);
							if(tank.isHitByShell(shell)){
								shell.setAlive(false);
								tank.damage(shell.getDamageValue());					
							}						
						}
					}else{		//如果是自动坦克炮弹						
						//看该炮弹是否击中主坦克				
						if(mainTank.isHitByShell(shell)){
							shell.setAlive(false);	
							mainTank.damage(shell.getDamageValue());					
						}else if(mainTank.isHitByShell(shell)){
							shell.setAlive(false);	
							mainTank.damage(shell.getDamageValue());					
						}
					}					
				}
			}
			//如果子弹还有效，则绘制，否则移除
			if(shell.getVisible())			
				shell.draw(g);	//绘制炮弹
			else
				tmpList.remove(i);
		}
	}
	//处理、移动、绘制自动坦克
	private void handleDrawAutoTanks(Graphics g){
		AutoTank tank;
		Shell shell;
		//遍历自动坦克，移动并绘制
		for(int i=autoTanks.size()-1;i>0;i--){
			tank = (AutoTank)autoTanks.get(i);
			//如果坦克还活着，则移动，并发射炮弹
			if(tank.getAlive()){
				if(!isTankHitBlock(tank)&&!isAutoTankHitTank(tank))
					tank.move();
				tank.turnDirection();
				//这里可以改改条件什么的比如 shell= tank.superFire();
//				if(NumAuto<=3)
//					shell= tank.superFire();
//				else
					shell = tank.fire();
				if(shell != null)
					autoShells.add(shell);
			}
			//如果坦克还可见，则绘制，否则移除
			if(tank.getVisible()){
				tank.draw(g);
			}else {
				autoTanks.remove(i);
				items.add(new Item(tank.x,tank.y,mainTank));//死后增加物品
				Music M= new Music("explode");
				NumAuto--;
				numberalive--;
				mainTank.addLifeValue();///加血
			}
		}
	}
	//处理、移动、绘制障碍物
	private void handleDrawBlocks(Graphics g){
		//遍历blocks块，绘制地图
		for(int i=0;i<blocks.size();i++){
			Block block;
			block = blocks.get(i);
			block.draw(g);
		}
	}
	
	private void handleDrawItems(Graphics g){//处理item
		for(int i=0;i<items.size();i++){
			Item item;
			item = items.get(i);
			if(!mainTank.isHitByItem(item)){
				item.draw(g);
			}else {
				items.remove(i);		
			}
		}
	}
	
	public void clear(){
		mainShells.clear();
		autoShells.clear();
		autoTanks.clear();
		blocks.clear();
	
	}
	
	public void draw(Graphics g){
		if(NumAuto<=number){
			createAutoTank(); 
			}//以一定机率产生新的自动坦克	
		handleDrawMainTank(g);	//处理、移动、绘制主坦克
		handleDrawShells(g,true);//处理、移动、绘制主坦克炮弹
		handleDrawAutoTanks(g);//处理、移动、绘制自动坦克
		handleDrawShells(g,false);//处理、移动、绘制自动坦克炮弹
		handleDrawBlocks(g);//处理、移动、绘制障碍物
		handleDrawItems(g);//处理、移动、绘制障碍物
	}
	//按下键时进行设置键位标志，用于方向定位判断  
    public void keyPressed(KeyEvent e) {  
    	 int key = e.getKeyCode();
         switch(key) {
         case KeyEvent.VK_SPACE :               
         	Shell shell = mainTank.fire();
         	if(shell!=null)mainShells.add(shell);
         	break;
			 case KeyEvent.VK_LEFT :
				 mainTank.setDirection(Direction.LEFT);
				 if (!isTankHitBlock(mainTank) && !isMainTankHitTank(mainTank)) {
					 mainTank.move();
				 }
				 break;
			 case KeyEvent.VK_UP :
				 mainTank.setDirection(Direction.UP);
				 if (!isTankHitBlock(mainTank) && !isMainTankHitTank(mainTank)) {
					 mainTank.move();
				 }
				 break;
			 case KeyEvent.VK_RIGHT :
				 mainTank.setDirection(Direction.RIGHT);
				 if (!isTankHitBlock(mainTank) && !isMainTankHitTank(mainTank)) {
					 mainTank.move();
				 }
				 break;
			 case KeyEvent.VK_DOWN :
				 mainTank.setDirection(Direction.DOWN);
				 if (!isTankHitBlock(mainTank) && !isMainTankHitTank(mainTank)) {
					 mainTank.move();
				 }
				 break;


			 case KeyEvent.VK_E :
				 Shell shell2 = mainTank2.fire();
				 if(shell2!=null)mainShells.add(shell2);
				 break;

			 case KeyEvent.VK_A :
				 mainTank2.setDirection(Direction.LEFT);	
				 if (!isTankHitBlock(mainTank2) && !isMainTankHitTank(mainTank2
						 )) {
					 mainTank2.move();
				 }
				 break;
			 case KeyEvent.VK_W :
				 mainTank2.setDirection(Direction.UP); 
				 if (!isTankHitBlock(mainTank2) && !isMainTankHitTank(mainTank2)) {
					 mainTank2.move();
				 }
				 break;
			 case KeyEvent.VK_D :
				 mainTank2.setDirection(Direction.RIGHT);
				 if (!isTankHitBlock(mainTank2) && !isMainTankHitTank(mainTank2)) {
					 mainTank2.move();
				 }
				 break;
			 case KeyEvent.VK_S :
				 mainTank2.setDirection(Direction.DOWN);
				 if (!isTankHitBlock(mainTank2) && !isMainTankHitTank(mainTank2)) {
					 mainTank2.move();
				 }
				 break;

         }

    }
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
	public void setMusic (Music music){
		this.music = music;
	}
	
}
