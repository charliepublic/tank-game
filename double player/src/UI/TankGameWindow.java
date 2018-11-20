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

//�������ڲ���
public class TankGameWindow extends JFrame implements ActionListener 
		
{
	public short player = 1;
	
	int i = 0;
	int m;
	public TankGameMgr tankGameMgr;//����̹�˹�����
	public JButton jb1 ;
	public static  int GAME_WIDTH = 1000;  //��Ϸ���ڿ��
    public static int GAME_HEIGHT = 800;  //��Ϸ���ڳ���
    public static int WINDOW_WIDTH = 1000;//���ڿ��
    public static int WINDOW_HEIGHT = 800;  //��Ϸ���ڳ���
    public boolean pause = false; 
    public Music main ;

	public void initFrame(int player){

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenuItem[] menuItem = new JMenuItem []{ new JMenuItem("������Ϸ"), 
				new JMenuItem("��ͣ��Ϸ") ,
				new JMenuItem("�ص���Ϸ")	,
				new JMenuItem("˫����Ϸ") ,
				
		};
		for(int i =0;i<menuItem.length;i++){
        	menuBar.add(menuItem[i]);
        }
        for(int j = 0;j<menuItem.length;j++){
        	menuItem[j].addActionListener(this);
       
        }
		Icon i=new ImageIcon("normalpicture/233.jpg");
		JButton jb2=new JButton(i);
		//ͼ�������水ť
		jb2.setActionCommand("bb");
		jb2.addActionListener(this);
		jb2.setBounds(930,680,70,68);
		this.getContentPane().add(jb2);
        this.getContentPane().setLayout(null);
		
		this.setLocation(200,200);        //���ô�����ʾ����Ļ��λ��
		this.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);            //���ô���Ĵ�С
		this.setResizable(false);         //���ô����С���ɵ�
		this.setTitle("̹�˴�ս");           //���ô������
		this.setVisible(true);				//ʹ��ɼ�
		this.addWindowListener(new WindowAdapter()    //���ô���ر��¼���Ӧ
        {  
            public void windowClosing(WindowEvent e)  
            {  
                System.exit(0);  
            }  
        });	
	
		this.addKeyListener(new KeyMonitor());  //��Ӽ��̼�����
		
		
		
		tankGameMgr= new TankGameMgr();
		tankGameMgr.initGame(player);//�������е�̹�ˣ�����
		tankGameMgr.setMusic(main);
		
		Thread thread;                           //�����̱߳���
		thread = new Thread(new PaintThread());  //������Ϸ�߳�
		thread.start();                          //������Ϸ�߳�
		main = new Music("main");
		
	}	
	


	private class PaintThread implements Runnable  //�̲߳���
    {  
        public void run(){  
            while(true){ 
               if(pause == false){
            	   
            	   repaint();
               }else{
            	   
               }
            	
        		//�˷����ᵼ�´����ػ����
                try{  
                    Thread.sleep(50);  
                }catch(InterruptedException e){
                	e.printStackTrace();
                }  
            }  
         }  
    }
	
	
	private class KeyMonitor extends KeyAdapter //���̼���  	
	{	  
        public void keyReleased(KeyEvent e)        {  
       
        }    
        public void keyPressed(KeyEvent e) {  
        	tankGameMgr.keyPressed(e);          	//����̹��ļ��봦����
        }          
    }
	
	
	
	
	//˫���弼��������Ƶ��  
    Image offScreenImage = null;      //����һ���µ�Image���󣬼��ڶ�����
    Graphics gOffScreen = null;

    
	//paint���������ػ����Զ�����,�ڴ˺�����ʵ��˫�������  
	public void paint(Graphics g)
    {
		//��һ��ʹ��offScreenImageʱ�����������ڶ����棬�����Ļ�������ȫ�������ڵڶ�������
        if(offScreenImage == null) {  
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT); //��ȡ��������λ�õ�ͼƬ
            gOffScreen = offScreenImage.getGraphics();    //��ý�ȡͼƬ�Ļ���
        }
        
 
        super.paint(gOffScreen);  // ���ø�����ػ淽������ֹ�ٴ���ײ����ػ�
//        gOffScreen.fillRect(0, 55, GAME_WIDTH, GAME_HEIGHT);    //�����Ļ 
        Image Backgrounds = Toolkit.getDefaultToolkit().getImage("normalpicture/3.png");
        gOffScreen.drawImage(Backgrounds, 0, 50, GAME_WIDTH, GAME_HEIGHT,this);
        switch(m){
        
        case 0 :
        	 Image Background = Toolkit.getDefaultToolkit().getImage("normalpicture/3.png"); //���ر���ͼƬ
             
             gOffScreen.drawImage(Background, 0, 50, GAME_WIDTH, GAME_HEIGHT,this);
             break;
             
        case 1:
        	Image background = Toolkit.getDefaultToolkit().getImage("normalpicture/tu.gif"); //���ر���ͼƬ
			gOffScreen.drawImage(background, 0, 50, GAME_WIDTH, GAME_HEIGHT,this);
			break;
        }

        this.requestFocus();

        tankGameMgr.draw(gOffScreen);      //����������Ϸ����
        g.drawImage(offScreenImage, 0,0, null);//���ڶ�����е�����һ����ȫ�����Ƶ���Ļ��
       
        	
    }

	public void actionPerformed(ActionEvent e) {
		
        if(e.getActionCommand().equals("bb")){
			
			i++;
		m = i%2;
		} else if(e.getActionCommand().equals("������Ϸ")){
			
			this.dispose();
			main.stop();
			//�ر����д���
			TankGameWindow gameStart = new TankGameWindow();
			gameStart.initFrame(player);
			//�����µĴ���
		}else if (e.getActionCommand().equals("��ͣ��Ϸ")){
			pause=true;
			main.stop();
			//����ֹͣ
		}else if (e.getActionCommand().equals("�ص���Ϸ")){
			pause=false;
			main = new Music("main");
			//���ֿ�ʼ
		}else if (e.getActionCommand().equals("˫����Ϸ")){
			player = 2;
			this.dispose();
			main.stop();
			//�ر����д���
			TankGameWindow gameStart = new TankGameWindow();
			gameStart.initFrame(player);

		}

	}  
}
