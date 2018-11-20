package UI;
import Gamebody.Music;
import  Gamebody.TankGameMgr;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

//构建窗口部分
public class TankGameWindow extends JFrame implements ActionListener 
		
{
	public short player = 1;
	
	int i = 0;
	int m;
	public TankGameMgr tankGameMgr;//创造坦克管理器
	public JButton jb1 ;
	public static  int GAME_WIDTH = 1000;  //游戏窗口宽度
    public static int GAME_HEIGHT = 800;  //游戏窗口长度
    public static int WINDOW_WIDTH = 1000;//窗口宽度
    public static int WINDOW_HEIGHT = 800;  //游戏窗口长度
    public boolean pause = false; 
    public Music main ;

	public void initFrame(int player){

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenuItem[] menuItem = new JMenuItem []{ new JMenuItem("重启游戏"), 
				new JMenuItem("暂停游戏") ,
				new JMenuItem("回到游戏")	,
				new JMenuItem("双人游戏") ,
				
		};
		for(int i =0;i<menuItem.length;i++){
        	menuBar.add(menuItem[i]);
        }
        for(int j = 0;j<menuItem.length;j++){
        	menuItem[j].addActionListener(this);
       
        }
		Icon i=new ImageIcon("normalpicture/233.jpg");
		JButton jb2=new JButton(i);
		//图标来代替按钮
		jb2.setActionCommand("bb");
		jb2.addActionListener(this);
		jb2.setBounds(930,680,70,68);
		this.getContentPane().add(jb2);
        this.getContentPane().setLayout(null);
		
		this.setLocation(200,200);        //设置窗体显示在屏幕的位置
		this.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);            //设置窗体的大小
		this.setResizable(false);         //设置窗体大小不可调
		this.setTitle("坦克大战");           //设置窗体标题
		this.setVisible(true);				//使其可见
		this.addWindowListener(new WindowAdapter()    //设置窗体关闭事件响应
        {  
            public void windowClosing(WindowEvent e)  
            {  
                System.exit(0);  
            }  
        });	
	
		this.addKeyListener(new KeyMonitor());  //添加键盘监听器
		
		
		
		tankGameMgr= new TankGameMgr();
		tankGameMgr.initGame(player);//生成所有的坦克，方块
		tankGameMgr.setMusic(main);
		
		Thread thread;                           //声明线程变量
		thread = new Thread(new PaintThread());  //创建游戏线程
		thread.start();                          //启动游戏线程
		main = new Music("main");
		
	}	
	


	private class PaintThread implements Runnable  //线程操作
    {  
        public void run(){  
            while(true){ 
               if(pause == false){
            	   
            	   repaint();
               }else{
            	   
               }
            	
        		//此方法会导致窗口重绘操作
                try{  
                    Thread.sleep(50);  
                }catch(InterruptedException e){
                	e.printStackTrace();
                }  
            }  
         }  
    }
	
	
	private class KeyMonitor extends KeyAdapter //键盘监听  	
	{	  
        public void keyReleased(KeyEvent e)        {  
       
        }    
        public void keyPressed(KeyEvent e) {  
        	tankGameMgr.keyPressed(e);          	//调用坦类的键码处理函数
        }          
    }
	
	
	
	
	//双缓冲技术，消除频闪  
    Image offScreenImage = null;      //声明一个新的Image对象，即第二缓存
    Graphics gOffScreen = null;

    
	//paint方法窗口重绘中自动调用,在此函数中实现双缓冲机制  
	public void paint(Graphics g)
    {
		//第一次使用offScreenImage时创建它，即第二缓存，后续的绘制内容全部绘制在第二缓存中
        if(offScreenImage == null) {  
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT); //截取窗体所在位置的图片
            gOffScreen = offScreenImage.getGraphics();    //获得截取图片的画布
        }
        
 
        super.paint(gOffScreen);  // 调用父类的重绘方法，防止再从最底层来重绘
//        gOffScreen.fillRect(0, 55, GAME_WIDTH, GAME_HEIGHT);    //清除屏幕 
        Image Backgrounds = Toolkit.getDefaultToolkit().getImage("normalpicture/3.png");
        gOffScreen.drawImage(Backgrounds, 0, 50, GAME_WIDTH, GAME_HEIGHT,this);
        switch(m){
        
        case 0 :
        	 Image Background = Toolkit.getDefaultToolkit().getImage("normalpicture/3.png"); //加载背景图片
             
             gOffScreen.drawImage(Background, 0, 50, GAME_WIDTH, GAME_HEIGHT,this);
             break;
             
        case 1:
        	Image background = Toolkit.getDefaultToolkit().getImage("normalpicture/tu.gif"); //加载背景图片
			gOffScreen.drawImage(background, 0, 50, GAME_WIDTH, GAME_HEIGHT,this);
			break;
        }

        this.requestFocus();

        tankGameMgr.draw(gOffScreen);      //绘制所有游戏对象
        g.drawImage(offScreenImage, 0,0, null);//将第二绘存中的内容一次性全部绘制到屏幕上
       
        	
    }

	public void actionPerformed(ActionEvent e) {
		
        if(e.getActionCommand().equals("bb")){
			
			i++;
		m = i%2;
		} else if(e.getActionCommand().equals("重启游戏")){
			
			this.dispose();
			main.stop();
			//关闭现有窗口
			TankGameWindow gameStart = new TankGameWindow();
			gameStart.initFrame(player);
			//创建新的窗口
		}else if (e.getActionCommand().equals("暂停游戏")){
			pause=true;
			main.stop();
			//音乐停止
		}else if (e.getActionCommand().equals("回到游戏")){
			pause=false;
			main = new Music("main");
			//音乐开始
		}else if (e.getActionCommand().equals("双人游戏")){
			player = 2;
			this.dispose();
			main.stop();
			//关闭现有窗口
			TankGameWindow gameStart = new TankGameWindow();
			gameStart.initFrame(player);

		}

	}  
}
