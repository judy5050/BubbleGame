package bubble.test.ex10;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Bubble extends JLabel implements Moveable {

		//의존성 콤포지션  
		private Player player;
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
		
		public Bubble(Player player) {
			this.player=player;
			initObject();
			initSetting();
			initThread();
		}
		
		private void initObject() {
			bubble =new ImageIcon("image/bubble.png");
			bubbled=new ImageIcon("image/bubbled.png");
			bomb=new ImageIcon("image/bomb.png");
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
		private void initThread() {
			new Thread(()->{
				if(player.getPlayerWay()==PlayerWay.LEFT) {
					left();
				}else {
					right();
				}
			}).start();
		}

		@Override
		public void up() {
			up=true;
			while(up) {
				for(int i=0;i<400;i++) {
					y--;
					setLocation(x,y);
					
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

		@Override
		public void left() {
			left=true;
			// TODO Auto-generated method stub
			for(int i=0;i<400;i++) {
				x--;
				setLocation(x,y);
				
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
