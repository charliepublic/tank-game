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
	private MainTank mainTank;//��̹��1
	private MainTank mainTank2;	//��̹��2
	private int SIZE=50;//̹�˵Ĵ�С
	private List<MainTank> mainTanks = new ArrayList<>();
	private List<Shell> mainShells 	= new ArrayList<Shell>();	//��̹�˷�����ڵ��б�
	private List<AutoTank> autoTanks= new ArrayList<AutoTank>();//�Զ�̹���б�
	private List<Shell> autoShells 	= new ArrayList<Shell>();	//�Զ�̹�˷�����ڵ��б�
	private List<Block> blocks = new ArrayList<Block>();		//�ϰ����б�
	private List<Item> items = new ArrayList<Item>();		//��Ʒ�б�
	private short createTankRatio=60;						 	//����̹�˵Ļ���
	public  int NumAuto =0;										//�Զ�̹�˵ĵ�ǰ����
	public  int number=5;		//�ܹ����ɵ��ܵ��Զ�̹����Ŀ
	public  int numberalive = 5;//�Զ�̹�˴������
	private final int FrameTitleHigh=70;						//�������ĸ߶�
	private Random random = new Random();			//�������
	public boolean gameover = true;
	public Music music;
	public  int m ;
	public boolean getGameOver(){
		return gameover;
	}
	
	public void initGame(int player){
		Random rand = new Random();
		//������̹��
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
		//��������������ϰ����
		int num = rand.nextInt(10)+3;
		for(int i=0;i<num;i++)
			blocks.add(new Block());
	}
	//��4/4�Ļ��ʴ���̹��
	private void createAutoTank(){
		AutoTank tank=null;
		int r1 = random.nextInt(createTankRatio); //�Ƿ�����̹�ˣ����ĸ�������̹��
		int r2 = random.nextInt(2); //̹�˵����з���
		switch(r1){
		case 0:           //���Ͻ�
			tank = new AutoTank(SIZE/2,FrameTitleHigh+SIZE/2);
			if(r2==0)tank.setDirection(Direction.RIGHT);
			else	 tank.setDirection(Direction.DOWN);
			break;
		case 1:            //���Ͻ�
			tank = new AutoTank(950+SIZE/2,FrameTitleHigh+SIZE/2);
			if(r2==0)tank.setDirection(Direction.LEFT);
			else  	 tank.setDirection(Direction.DOWN);
			break;
		case 2:            //���½�
			tank = new AutoTank(950+SIZE/2,750+SIZE/2);
			if(r2==0)tank.setDirection(Direction.LEFT);
			else 	 tank.setDirection(Direction.UP);
			break;
		case 3:				//���½�
			tank = new AutoTank(SIZE/2,750+SIZE/2);
			if(r2==0)tank.setDirection(Direction.RIGHT);
			else	 tank.setDirection(Direction.UP);			
		}
		if((tank != null)){
			NumAuto++;
			autoTanks.add(tank);
		}
			
	}
	//���̹����blocks����ײ
	private boolean isTankHitBlock(Tank tank){
		boolean result=false;
		for(int i=0;i<blocks.size();i++){
			Block block = blocks.get(i);
			//̽��̹�˵���һ���Ƿ�������ϰ���
			if(block.getRect().intersects(tank.getNextRect()))
				result = true;
		}
		return result;
	}

	private boolean isAutoTankHitTank(Tank tank){
		boolean result=false;
		for(int i=0;i<autoTanks.size();i++){
			Tank ptank =autoTanks.get(i);
			//̽��̹�˵���һ���Ƿ�������ϰ���
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
	//�����̹����̹�˵���ײ
	private boolean isMainTankHitTank(Tank tank){
		boolean result=false;
		for(int i=0;i<autoTanks.size();i++){
			Tank ptank =autoTanks.get(i);
			//̽��̹�˵���һ���Ƿ�������ϰ���
			if(ptank.getRect().intersects(tank.getNextRect()))
				result = true;
		}
		return result;
	}
	//����ڵ���blocks����ײ
	private boolean isShellHitBlock(Shell shell){
		boolean result=false;
		for(int i=0;i<blocks.size();i++){
			Block block = blocks.get(i);
			if(block.getRect().intersects(shell.getRect()))
				result = true;			
		}
		return result;
	}
	//�����ƶ���������̹��
	private void handleDrawMainTank(Graphics g) {
		//�ƶ���������̹��
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
	//isMain==true �����ƶ���������̹���ڵ�
	//isMain==false�����ƶ��������Զ�̹���ڵ�
	private void handleDrawShells(Graphics g,boolean isMain){
		List<Shell> tmpList;
		Shell shell;
		AutoTank tank;
		
		if(isMain)  tmpList = mainShells;	//���isMain==true��������̹���ڵ�		
		else 		tmpList = autoShells;	//���isMain==true�������Զ�̹���ڵ�
		
		//�����ڵ�����顢���㡢�ƶ�������		
		for(int i=tmpList.size()-1;i>=0;i--){
			shell = (Shell)tmpList.get(i);
			if(shell.getAlive()){
				if(isShellHitBlock(shell))//����ڵ��Ƿ������ϰ���
					shell.setAlive(false);
				else{
					shell.move();	//�ƶ��ڵ�
					if(isMain){		//�������̹���ڵ�
						//�������е��Զ�̹�ˣ������ڵ��Ƿ����֮
						for(int j=autoTanks.size()-1;j>=0;j--){
							tank = autoTanks.get(j);
							if(tank.isHitByShell(shell)){
								shell.setAlive(false);
								tank.damage(shell.getDamageValue());					
							}						
						}
					}else{		//������Զ�̹���ڵ�						
						//�����ڵ��Ƿ������̹��				
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
			//����ӵ�����Ч������ƣ������Ƴ�
			if(shell.getVisible())			
				shell.draw(g);	//�����ڵ�
			else
				tmpList.remove(i);
		}
	}
	//�����ƶ��������Զ�̹��
	private void handleDrawAutoTanks(Graphics g){
		AutoTank tank;
		Shell shell;
		//�����Զ�̹�ˣ��ƶ�������
		for(int i=autoTanks.size()-1;i>0;i--){
			tank = (AutoTank)autoTanks.get(i);
			//���̹�˻����ţ����ƶ����������ڵ�
			if(tank.getAlive()){
				if(!isTankHitBlock(tank)&&!isAutoTankHitTank(tank))
					tank.move();
				tank.turnDirection();
				//������Ըĸ�����ʲô�ı��� shell= tank.superFire();
//				if(NumAuto<=3)
//					shell= tank.superFire();
//				else
					shell = tank.fire();
				if(shell != null)
					autoShells.add(shell);
			}
			//���̹�˻��ɼ�������ƣ������Ƴ�
			if(tank.getVisible()){
				tank.draw(g);
			}else {
				autoTanks.remove(i);
				items.add(new Item(tank.x,tank.y,mainTank));//����������Ʒ
				Music M= new Music("explode");
				NumAuto--;
				numberalive--;
				mainTank.addLifeValue();///��Ѫ
			}
		}
	}
	//�����ƶ��������ϰ���
	private void handleDrawBlocks(Graphics g){
		//����blocks�飬���Ƶ�ͼ
		for(int i=0;i<blocks.size();i++){
			Block block;
			block = blocks.get(i);
			block.draw(g);
		}
	}
	
	private void handleDrawItems(Graphics g){//����item
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
			}//��һ�����ʲ����µ��Զ�̹��	
		handleDrawMainTank(g);	//�����ƶ���������̹��
		handleDrawShells(g,true);//�����ƶ���������̹���ڵ�
		handleDrawAutoTanks(g);//�����ƶ��������Զ�̹��
		handleDrawShells(g,false);//�����ƶ��������Զ�̹���ڵ�
		handleDrawBlocks(g);//�����ƶ��������ϰ���
		handleDrawItems(g);//�����ƶ��������ϰ���
	}
	//���¼�ʱ�������ü�λ��־�����ڷ���λ�ж�  
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
