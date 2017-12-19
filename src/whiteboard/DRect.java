package whiteboard;
import java.awt.Color;
import java.awt.Graphics;
public class DRect extends DShape {
	public DRect() {
		super();
	}
	public void draw(Graphics g) {
        g.setColor(shapeModel.getColor());
        g.fillRect(shapeModel.getX(),shapeModel.getY(),shapeModel.getWidth(),shapeModel.getHeight());
    }
}
