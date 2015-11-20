import java.awt.Graphics;



public class DRect extends DShape {

	// Shapes do not have object data!
	// Todo: pointer to DShapeModel that stores coordinate information (x, y, width, height)
	
	public DRect(){
		//paint(null);
	}
	

	public void paint(Graphics g){
		g.drawRect(0, 0, 100, 100);
	}

}
