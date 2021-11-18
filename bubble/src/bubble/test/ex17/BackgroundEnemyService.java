package bubble.test.ex17;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

//메인스레드 바쁨 -키보드 이벤트를 처리하기 바쁨.
//백그라운드에서 계속 관찰 
public class BackgroundEnemyService implements Runnable {
	
	private BufferedImage image;
	private Enemy enemy;


	public BackgroundEnemyService(Enemy enemy) {
		this.enemy=enemy;	
		try {
			image=ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	@Override
	public void run() {
		while(enemy.getState()==0) {
			
			//색상확인 
			Color leftColor=new Color(image.getRGB(enemy.getX()-10, enemy.getY()+25));
			Color rightColor=new Color(image.getRGB(enemy.getX()+50+15, enemy.getY()+25));
			//-2가 나온다는 뜻은 바닥에 색이 없다는 것 
			int bottomColor=image.getRGB(enemy.getX()+10, enemy.getY()+50+5)+image.getRGB(enemy.getX()+50, enemy.getY()+50+5);
		
//			System.out.println("leftColor: "+leftColor);
//			System.out.println("rightColor:: "+rightColor);
			//바닥 충돌 확인 
			if(bottomColor!=-2) {
//				System.out.println("바텀컬러: "+bottomColor);
//				System.out.println("바닥충돌 ");
				enemy.setDown(false);
			}else {
				if(!enemy.isUp()&&!enemy.isDown()) {
					enemy.down();	
				}
				
			}
			//외벽 충돌 확인 
			if(leftColor.getRed()==255&&leftColor.getGreen()==0&&leftColor.getBlue()==0){
//				System.out.println("왼쪽 벽에 충돌");
				enemy.setLeft(false);
				if(!enemy.isRight()) {
					enemy.right();	
				}
				
			}
			else if(rightColor.getRed()==255&&rightColor.getGreen()==0&&rightColor.getBlue()==0){
//				System.out.println("오른쪽 벽에 충돌");
				enemy.setRight(false);
				if(!enemy.isLeft()) {
					enemy.left();	
				}
				
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
	

}
