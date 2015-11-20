
import java.util.ArrayList;
import javax.swing.JPanel;


public class Canvas extends JPanel {
	ArrayList<DShape> collection;

	public Canvas(){
		collection = new ArrayList<DShape>();
	}

	public void paintComponent() {

		for(DShape shape : collection) {
			shape.paint();
		}
	}
	
	public void addShape(DShape shape) {
		collection.add(shape);
	}


}
