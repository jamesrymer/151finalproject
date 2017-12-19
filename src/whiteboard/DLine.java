package whiteboard;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
public class DLine extends DShape {
	public DLine(){
		super();
	}
public void draw(Graphics g) {
        g.setColor(shapeModel.getColor());
        g.drawLine(getX(), getY(), getWidth(), getHeight());
    }
public ArrayList<Rectangle> getKnobs() {
	ArrayList<Rectangle> knobs = new ArrayList<Rectangle>();
	knobs.add(new Rectangle(getX()-9,getY()-9,20,20)); //Top Left
//	knobs.add(new Rectangle(getX()-9,getY()+getHeight()-9,20,20)); //Bottom Left
//	knobs.add(new Rectangle(getX()+getWidth()-9,getY()-9,20,20)); //Top Right
	knobs.add(new Rectangle(getX()+getWidth()-9,getY()+getHeight()-9,20,20)); //Bottom Right
	return knobs;
}
}
