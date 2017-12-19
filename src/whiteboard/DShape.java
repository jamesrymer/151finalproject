/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whiteboard;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
public class DShape implements ModelListener {
	DShapeModel shapeModel;
	public DShape() {
		shapeModel = new DShapeModel(0, 0, 0, 0, Color.gray);
	}
	public void draw(Graphics g) {
		g.setColor(shapeModel.getColor());
		g.drawRect(shapeModel.getX(), shapeModel.getY(), shapeModel.getWidth(), shapeModel.getHeight());
	}
	public void setColor(Color color) {
		shapeModel.setColor(color);
	}
	public void setX(int x) {
		shapeModel.setX(x);
	}
	public void setY(int y) {
		shapeModel.setY(y);
	}
	public void setWidth(int width) {
		shapeModel.setWidth(width);
	}
	public void setHeight(int height) {
		shapeModel.setHeight(height);
	}
	public Color getColor() {
		return shapeModel.getColor();
	}
	public int getX() {
		return  shapeModel.getX();
	}
	public int getY() {
		return shapeModel.getY();
	}
	public int getWidth() {
		return  shapeModel.getWidth();
	}
	public int getHeight() {
		return  shapeModel.getHeight();
	}
	public DShapeModel getDShapeModel() {
		return shapeModel;
	}
	public ArrayList<Rectangle> getKnobs() {
		ArrayList<Rectangle> knobs = new ArrayList<Rectangle>();
		knobs.add(new Rectangle(getX()-9,getY()-9,20,20)); //Top Left
		knobs.add(new Rectangle(getX()-9,getY()+getHeight()-9,20,20)); //Bottom Left
		knobs.add(new Rectangle(getX()+getWidth()-9,getY()-9,20,20)); //Top Right
		knobs.add(new Rectangle(getX()+getWidth()-9,getY()+getHeight()-9,20,20)); //Bottom Right
		return knobs;
	}
	@Override
	public void ModelChanged(DShapeModel model) {
		this.shapeModel = model;
		
	}
}
