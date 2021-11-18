package bubble.test.ex16;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Bubble extends JLabel implements Moveable {

		//의존성 콤포지션  
		private Player player;
		private BackgroundBubbleService backgroundBubbleService;
		private BubbleFrame mContext;
		private Enemy enemy;
		//위치상태
		private int x;
		private int y;
		
		//움직임 상태(boolean 타입은getter setter메소드 앞에 is가 붙는다)
		private boolean up;
		private boolean right;
		private boolean left;
		
		//적군을 맞춘 상태 
		private int state;//0 (물방울) ,1(적을 가둔 물방울)
		
		private ImageIcon bubble;//물방울 
		private ImageIcon bubbled;//적을 가둔 물방울
		private ImageIcon bomb;//물방울이 터진 상태 
		
		public Bubble(BubbleFrame mContext) {
			this.mContext=mContext;
			this.player=mContext.getPlayer();
			this.enemy=mContext.getEnemy();
			initObject();
			initSetting();
		
		}
		
		private void initObject() {
			bubble =new ImageIcon("image/bubble.png");
			bubbled=new ImageIcon("image/bubbled.png");
			bomb=new ImageIcon("image/bomb.png");
			
			backgroundBubbleService=new BackgroundBubbleService(this);
		}
		private void initSetting() {
			left=false;
			right=false;
			up=false;
			
			x=player.getX();
			y=player.getY();
			setIcon(bubble);
			setSize(50,50);
			
			state=0;
		}
		

		@Override
		public void up() {
			up=true;
			while(up) {
				for(int i=0;i<400;i++) {
					y--;
					setLocation(x,y);
					if(backgroundBubbleService.topWall()) {
						up=false;
						break;
					}
				
					try {
						if(state==0) { //기본 물방울 
							Thread.sleep(1);	
						}else { //적을 가둔 물방울 
							Thread.sleep(10);
						}
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				
				
			}
			if(state==0) {
				clearBubble(); //천장에 버블이 도착하고 나서 3초후에 메모리에서 소멸 	
			}
			
			
		}
		
		@Override
		public void  attack() {
			state=1;
			enemy.setState(1);
			setIcon(bubbled);
			mContext.remove(enemy);//메모리에서 사라지게 한다.(가비지 컬렉션이 즉시 발동하지 않음)
			mContext.repaint();
		}
		
		private void clearBubble() {
			try {
				Thread.sleep(3000);
				setIcon(bomb);
				Thread.sleep(500);
				mContext.remove(this); //BubbleFrame의Bubble 삭제 
				mContext.repaint();//BubbleFrame의 전체를 다시 그린다.
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void clearBubbled() {
			new Thread(()->{
				System.out.println("clearedBubbled");
				try {
					up=false;
					setIcon(bomb);
					Thread.sleep(1000);
					mContext.remove(this);
					mContext.repaint();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}).start();;
		
		}

		@Override
		public void left() {
			left=true;
			// TODO Auto-generated method stub
			for(int i=0;i<400;i++) {
				x--;
				setLocation(x,y);
				if(backgroundBubbleService.leftWall()) {
					left=false;
					break;
				}
				
				//아군과 적군의 거리가 10차이 날때 
				if((Math.abs(x-enemy.getX())<10)&&
						(Math.abs(y-enemy.getY())>0 && Math.abs(y-enemy.getY())<50)){
						System.out.println("물방울과 적군 충돌");
						if(enemy.getState()==0) {
							attack();	
							break;
						}
						
						
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
			up();
		}

		@Override
		public void right() {
			right=true;
			for(int i=0;i<400;i++) {
				x++;
				setLocation(x,y);
				
				if(backgroundBubbleService.rightWall()) {
					right=false;
					break;
				}
				//아군과 적군의 거리가 10차이 날때 
				if((Math.abs(x-enemy.getX())<10)&&
						(Math.abs(y-enemy.getY())>0 && Math.abs(y-enemy.getY())<50)){
						System.out.println("물방울과 적군 충돌");
						if(enemy.getState()==0) {
							attack();	
							break;
						}
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
			up();
			
		}
		
}
