package bubble.test.ex02;

import javax.swing.JFrame;

public class BubbleFrame extends JFrame{
	public BubbleFrame() {
		setSize(1000,640);
		getContentPane().setLayout(null); //absolute레이아웃 (자유롭게 그림을 그릴수있다)
		setLocationRelativeTo(null);//JFrame 가운데 배치하기 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//x버튼으로 창을끌때 JVM같이 종료하기
		setVisible(true);
	}
	
	public static void main(String []args) {
		new BubbleFrame();
	}

}
