package bubble.test.ex14;
import javax.swing.*;

import lombok.Getter;
import lombok.Setter;

//class Player -> new 가능한 친구들 !!!게임에 존재할 수 있음(추상메서드를 가질 수 없다.)
@Getter
@Setter
public class Enemy extends JLabel implements Moveable{
	
	private BubbleFrame mContext;
	//위치상태
	private int x;
	private int y;
	
	
	//적군의 방향 
	private EnemyWay enemyWay;
	
	//움직임 상태(boolean 타입은getter setter메소드 앞에 is가 붙는다)
	private boolean up;
	private boolean down;
	private boolean right;
	private boolean left;
	
	//벽에 충돌한 상태 
	private boolean leftWallCrash;
	private boolean rightWallCrash;
	
	//적군 속도 상태 
	private final int SPEED=3;
	private final int JUMPSPEED=1;
	
	private ImageIcon enemyR,enemyL;
	
	public Enemy(BubbleFrame mContext) {
		this.mContext=mContext;
		initObject();
		initSetting();
		initBackgroundEnemyService();
	}
	
	private void initObject() {
		enemyR=new ImageIcon("image/enemyR.png");
		enemyL=new ImageIcon("image/enemyL.png");
	}
	
	private void initSetting() {
		x=480;
		y=178;
		
		left=false;
		right=false;
		up=false;
		down=false;
		
		enemyWay=EnemyWay.RIGHT;
		setIcon(enemyR);
		setSize(50,50);
		setLocation(x,y);
	}
	
	private void initBackgroundEnemyService() {
//		new Thread(new BackgroundEnemyService(this)).start();
	}


	@Override
	public void up() {
//		System.out.println("점프");
		up=true;
		new Thread(()->{
			for(int i=0;i<130/JUMPSPEED;i++) {
				y-=JUMPSPEED;
				setLocation(x,y);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			up=false;
			down();
		}).start();
	}

	@Override
	public void down() {
		
//		System.out.println("down");
		down=true;
		new Thread(()->{
			while(down) {
				y+=JUMPSPEED;
				setLocation(x,y);
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			down=false;
		}).start();
	}

	//이벤트 핸들러 
	@Override
	public void left() {
//		System.out.println("left");
		enemyWay=EnemyWay.LEFT;
		left=true;
			new Thread(()->{
				while(left) {
				setIcon(enemyL);
				x-=SPEED;
				setLocation(x,y);	
					try {
						Thread.sleep(10);//0.01초 
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
//			System.out.println("left 스레드 종료 ");	
		
		
		
	}

	@Override
	public void right() {
//		System.out.println("right");
		enemyWay=EnemyWay.RIGHT;
		right=true;
			new Thread(()->{
				while(right) {
				setIcon(enemyR);
				x+=SPEED;
				setLocation(x,y);
					try {
						Thread.sleep(10);//0.01초 
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
//			System.out.println("right 스레드 종료 ");
		
	
	
	}

}
