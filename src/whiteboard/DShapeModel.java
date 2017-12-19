/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whiteboard;
import java.awt.Color;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

public class DShapeModel implements Serializable{
	private ArrayList<ModelListener> listeners = new ArrayList<>();
	private int x;
	private int y;
	private int width;
	private int height;
	protected Color color;
	private Rectangle rectangle;
	
	public Rectangle getRectangle() {
		return rectangle;
	}
	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
		changedModel();
	}
	public DShapeModel(int x, int y, int width, int height, Color color) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		rectangle = new Rectangle(x,y,width,height);
	}
	
	
	public int getX() {
		
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
		changedModel();
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
		changedModel();
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
		changedModel();
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
		changedModel();
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
		changedModel();
	}
	public void setListener(ModelListener l) {
		this.listeners.add(l);
	}
	private void changedModel() {
		for (ModelListener listener : listeners) {
			listener.ModelChanged(this);
		}
	}
}