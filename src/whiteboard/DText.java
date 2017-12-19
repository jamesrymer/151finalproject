package whiteboard;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.LineMetrics;
public class DText extends DShape {
    DTextModel model;
    private String input;
    private String font;
    
    public DText(){
        model = new DTextModel(0,0,0,0, Color.GRAY);
    }
    protected DTextModel getTextModel() {
            return (DTextModel)getDShapeModel(); 
        }
    public void draw(Graphics g){
        FontMetrics metrics = g.getFontMetrics();
        g.setColor((getColor()));
        int y = (metrics.getAscent() + (getHeight() - (metrics.getAscent() + metrics.getDescent())) / 2);
        Font defFont = new Font( font, Font.PLAIN, getHeight()/2);
        g.setFont(defFont);
        g.drawString(input, getX(),getY());
       // g.setColor(getDShapeModel().getColor());
       // g.setFont(getFont(g));
    }
    public String getInput(){
            return input;
    }
    public void setInput(String str){
        this.input = str;
    }
    public void setFont(String ft){
        this.font = ft;
    }
    public void setAll(int x, int y, int width, int height, Color c){
        model.setX(x);
        model.setY(y);
        model.setWidth(width);
        model.setColor(c);
    }
}

/*private Font getFont(Graphics g){
    Rectangle bound = getModel().getBounds();
    Font font = Font.decode.((DTextModel)getModel().getFontFamily());

    return font;
}*/