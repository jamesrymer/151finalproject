/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whiteboard;
import java.awt.Graphics;
public class DOval extends DShape{
	public DOval(){
		super();
	}
	public void draw(Graphics g) {
        g.setColor(shapeModel.getColor());
        g.fillOval(shapeModel.getX(),shapeModel.getY(),shapeModel.getWidth(),shapeModel.getHeight());
    }
}
