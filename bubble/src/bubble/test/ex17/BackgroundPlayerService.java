package bubble.test.ex17;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

//메인스레드 바쁨 -키보드 이벤트를 처리하기 바쁨.
//백그라운드에서 계속 관찰 
public class BackgroundPlayerService implements Runnable {
	
	private BufferedImage image;
	private Player player;
	private List<Bubble> bubbleList;

	public BackgroundPlayerService(Player player) {
		this.player=player;
		this.bubbleList=player.getBubbleList();
		try {
			image=ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	@Override
	public void run() {
		while(true) {
			//1. 버블 충돌 체크
			for(int i=0;i<bubbleList.size();i++) {
				Bubble bubble=bubbleList.get(i);
				if(bubbleList.get(i).getState()==1) {
					if(Math.abs(player.getX()-bubble.getX())<10&&
							(Math.abs(player.getY()-bubble.getY())>0 && Math.abs(player.getY()-bubble.getY())<50)){
							System.out.println("적군 사살 완료 ");
							bubble.clearBubbled();
							break;
						}
					}
				}
			
			
			//2. 벽 충돌 체크 
			//색상확인 
			Color leftColor=new Color(image.getRGB(player.getX()-10, player.getY()+25));
			Color rightColor=new Color(image.getRGB(player.getX()+50+15, player.getY()+25));
			//-2가 나온다는 뜻은 바닥에 색이 없다는 것 
			int bottomColor=image.getRGB(player.getX()+10, player.getY()+50+5)+image.getRGB(player.getX()+50, player.getY()+50+5);
		
//			System.out.println("leftColor: "+leftColor);
//			System.out.println("rightColor:: "+rightColor);
			//바닥 충돌 확인 
			if(bottomColor!=-2) {
//				System.out.println("바텀컬러: "+bottomColor);
//				System.out.println("바닥충돌 ");
				player.setDown(false);
			}else {
				if(!player.isUp()&&!player.isDown()) {
					player.down();	
				}
				
			}
			//외벽 충돌 확인 
			if(leftColor.getRed()==255&&leftColor.getGreen()==0&&leftColor.getBlue()==0){
//				System.out.println("왼쪽 벽에 충돌");
				player.setLeft(false);
				player.setLeftWallCrash(true);
			}
			else if(rightColor.getRed()==255&&rightColor.getGreen()==0&&rightColor.getBlue()==0){
//				System.out.println("오른쪽 벽에 충돌");
				player.setRight(false);
				player.setRightWallCrash(true);
			}else {
				player.setLeftWallCrash(false);
				player.setRightWallCrash(false);
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
	

}
