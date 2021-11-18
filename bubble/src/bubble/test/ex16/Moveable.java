package bubble.test.ex16;

/**
 * 
 * @author parkhyojeong
 *default 인터페이스도 몸체가 있는 메서드를 만들수 있다.(다중 상혹이 안되는 것이 많기 때문)
 */
public interface Moveable {
	
	public abstract void up();
	default public  void down() {};
	public abstract void left();
	public abstract void right(); 
	default public void attack() {};
}
