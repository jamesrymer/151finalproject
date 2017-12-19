package whiteboard;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.Popup;
import javax.swing.JPanel;
/*
 * Used for displaying shapes
 */
public class Canvas extends JPanel implements ModelListener {
	private ArrayList<DShape> shapes; // Holds shapes
	private DShape selected; // Holds the selected shape
	private volatile int draggedAtX, draggedAtY;
	/*
	 * Constructor: initializes canvas display size and adds a few mouse listeners.
	 */
	public Canvas() {
		this.setBounds(10, 10, 200, 200);
		this.setBackground(Color.WHITE);
		shapes = new ArrayList<>();
		//TODO Add drag support
		this.addMouseMotionListener(new MouseMotionListener() { 

			@Override
			public void mouseDragged(MouseEvent e) {
				Point moving;
				Point anchor;
				int knob = -1;
				
					if(selected instanceof DLine) {
                                            for(Rectangle r: selected.getKnobs())
                                            {
                                                if(r.contains(e.getPoint())){
                                                    knob = selected.getKnobs().indexOf(r);
                                                }
                                            }
					}
                            switch (knob) {
                                case 0:
                                    anchor = selected.getKnobs().get(1).getLocation();
                                    selected.setX(selected.getX() + e.getX() - draggedAtX);
                                    selected.setY(selected.getY() +e.getY() - draggedAtY);
                                    selected.setWidth(selected.getWidth() - e.getX() - draggedAtX); 
                                    selected.setHeight(selected.getHeight() -  e.getY() - draggedAtY);
                                    break;
                            //System.out.println(e.getX());
                                case 1:
                                    anchor = selected.getKnobs().get(0).getLocation();
                                    selected.setWidth(selected.getWidth() + e.getX() - draggedAtX);
                                    selected.setHeight(selected.getHeight() +  e.getY() - draggedAtY);
                                    break;
                                default:
                                    for(Rectangle rectangle : selected.getKnobs()) {
                                        if(rectangle.contains(e.getPoint())) {
                                            knob = selected.getKnobs().indexOf(rectangle);
                                        }
                                    }
                                    if(knob == 3) //Bottom Right
                                    {
                                        anchor = selected.getKnobs().get(0).getLocation();
                                        selected.setWidth(selected.getWidth() + e.getX() - draggedAtX);
                                        selected.setHeight(selected.getHeight() +  e.getY() - draggedAtY);
                                        
                                    }
                                    if (e.getX() >= selected.getX() && e.getX() <= selected.getX() + selected.getWidth() && e.getY() >= selected.getY()
                                            && e.getY() <= selected.getY() + selected.getHeight()) {
                                        moveSelected(e.getX() - draggedAtX + selected.getX(),
                                                e.getY() - draggedAtY + selected.getY());
                                        
                                    }
                                    break;
                            }
			}
			@Override
			public void mouseMoved(MouseEvent e) {
			}
			
		});
		final MouseAdapter mouseAdapter = new MouseAdapter() { // Checks to see if mouse click is inside the bounds of a shape. If so, sets a selected shape.
			@Override
			public void mousePressed(MouseEvent e) {
				// point = e.getPoint();
				draggedAtX = e.getX();
                                draggedAtY = e.getY();
				if(selected != null) {
					selected.setColor(Color.gray);
				}
				
				setSelected(e.getX(), e.getY());
				repaint();
			}
//			public void mouseClicked(MouseEvent e) {
//				// point = e.getPoint();
//				setSelected(e.getX(), e.getY());
//				repaint();
//			}
		};
		this.addMouseListener(mouseAdapter);
		
	}

	public void moveSelected(int x, int y) { // Will be used to move shape.
		selected.setX(x - selected.getX());
		selected.setY(y - selected.getY());
		this.repaint();
		System.out.println(selected.getX() + " " + x + "::::::" + selected.getY() + "  " + y);
	}
	/*
	 * Adds a shape to the list then calls repaint to display it on the canvas.
	 */
	public void addShape(DShape shape) {
		shape.getDShapeModel().setListener(this);
		this.shapes.add(shape);
		this.repaint();
	}
	/*
	 * Tells shapes to draw themselves. If shape is selected, draws knobs on each
	 * corner which will be used for resizing.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (DShape shape : shapes) {
			shape.draw(g);
			if (shape == selected) {
				for (Rectangle rectangle : shape.getKnobs()) {
					g.setColor(Color.black);
					g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

				}
			}
		}
	}
	/*
	 * Sets selected shape;
	 */
	public void setSelected(DShape shape) {
		this.selected = shape;
	}
	/*
	 * Sets selected shape;
	 */
	public void setSelected(int x, int y) {
		for (DShape shape : shapes) {
			if (x >= shape.getX() && x <= shape.getX() + shape.getWidth() && y >= shape.getY()
					&& y <= shape.getY() + shape.getHeight()) {
				this.selected = shape;

			}
			System.out.println(selected.getX());
		}
		selected.setColor(Color.MAGENTA);

	}
	/*
	 * Returns selected shape;
	 */
	public DShape getSelected() {
		return selected;
	}
	/*
	 * Its complicated...
	 */
	@Override
	public void ModelChanged(DShapeModel model) {
		// TODO Auto-generated method stub
		selected.shapeModel = model;
		this.repaint();
	}
	/*
	 * Deletes selected shape;
	 */
	public void delete() {
		for (DShape shape : shapes) {
			if (shape == selected) {
				shapes.remove(shape);
				this.repaint();
			}

		}
	}
	/*
	 * Sets selected shape to the front most position (The last element of the
	 * array);
	 */
	public void MoveToFront() {
		shapes.add(selected);
		shapes.remove(selected);
		this.repaint();
	}
	/*
	 * Sets selected shape to the back most position (The first element of the
	 * array);
	 */
	public void MoveToBack() {
		shapes.remove(selected);
		shapes.add(0, selected);

		this.repaint();
	}
	/*
	 * Used for testing
	 */
	public void addX() {
		System.out.println(selected.getX());
		selected.setX(selected.getX() + 50);
		System.out.println(selected.getX());
		// this.repaint();
	}
        public void save() {
		System.out.println("the save button has been pressed...");
		//JMenu menu = new JMenu("Save As");
		//Object[] possibilities = {null};
		Component frame = null;
		Icon icon = null;
		String s = (String)JOptionPane.showInputDialog(
		                    frame,
		                    "Enter Filename:\n",
		                    "Customized Dialog",
		                    JOptionPane.PLAIN_MESSAGE,
		                    icon,
		                    null,
		                    "WhiteBoard");
		//If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
		    System.out.println("Saving file as:  " + s);
		    try{
		        FileOutputStream fos= new FileOutputStream(s);
		        ObjectOutputStream oos= new ObjectOutputStream(fos);
		        oos.writeObject(shapes);
		        oos.close();
		        fos.close();
		        System.out.println("save completed!");
		    }catch(IOException ioe){
		    	System.out.println("filestream failed attempting to save...");
		    	ioe.printStackTrace();
		    }
		    return;
		}
		//If you're here, the user input was null/empty.
		System.out.println("Cannot save without file name!");
	}
	public void open() {
		System.out.println("the open button has been pressed...");
		//do stuff to open a desired file 
		Component frame = null;
		Icon icon = null;
		String t = (String)JOptionPane.showInputDialog(
		                    frame,
		                    "Enter Filename:\n",
		                    "Customized Dialog",
		                    JOptionPane.PLAIN_MESSAGE,
		                    icon,
		                    null,
		                    "WhiteBoard");
		//If a string was returned, say so.
		if ((t != null) && (t.length() > 0)) {
		    System.out.println("Loading file: " + t);
			try
	        {
	            FileInputStream fis = new FileInputStream(t);
	            ObjectInputStream ois = new ObjectInputStream(fis);
	            ArrayList<DShape> tempShapes = (ArrayList) ois.readObject();
	            ois.close();
	            fis.close();
	            for(DShape aShape: tempShapes){
	                System.out.println("adding shape to shapes which calls repaint");
	                this.addShape(aShape);
	            }
	         }catch(IOException ioe){
	             ioe.printStackTrace();
	             return;
	         }catch(ClassNotFoundException c){
	             System.out.println("Class not found");
	             c.printStackTrace();
	             return;
	         }
		}
		//If you're here, the user input was null/empty.
		System.out.println("Cannot load without file name!");
		//clear out the shapes array before repopulating from opened file
		//then load temp array into the object shapes arraylist
	}
	public void serverStart() {
		//do stuff to start that server bro
		System.out.println("You pressed the serverStart button and invoked its method...");
		Component frame = null;
		Icon icon = null;
		String port = (String)JOptionPane.showInputDialog(
		                    frame,
		                    "Enter port number:\n",
		                    "Customized Dialog",
		                    JOptionPane.PLAIN_MESSAGE,
		                    icon,
		                    null,
		                    "39542");
		System.out.println("the user has entered: " + port);
		int portInt = Integer.parseInt(port);
		try {
			this.sender(portInt);
		} catch (IOException e) {
			System.out.println("Failed to send portInt to sender method!");
			e.printStackTrace();
		}	
	}
	public void clientStart() throws IOException, ClassNotFoundException {
		//do stuff to start that client bro
		System.out.println("You pressed the clientStart button and invoked its method...");
		Component frame = null;
		Icon icon = null;
		String clientConnection = (String)JOptionPane.showInputDialog(
		                    frame,
		                    "Enter client connection:\n",
		                    "Customized Dialog",
		                    JOptionPane.PLAIN_MESSAGE,
		                    icon,
		                    null,
                                    "39542");
		System.out.println("the user has entered: " + clientConnection);
            //put program into client mode, with user input disabled (global flag)?
            //start a client connection based on user input by calling reader
            this.receiver(clientConnection);
	}
	private void receiver(String hostPort) throws IOException, ClassNotFoundException{
            System.out.println("Receiver Start");
            System.out.println(hostPort + "received by receiver() method...");
            int portAddress = Integer.parseInt(hostPort);
            SocketChannel sChannel = SocketChannel.open();
            sChannel.configureBlocking(true);
            if(sChannel.connect(new InetSocketAddress("localhost", portAddress))){
                ObjectInputStream ois = new ObjectInputStream(sChannel.socket().getInputStream());
                ArrayList<DShape> tempShapes = (ArrayList<DShape>)ois.readObject();
                System.out.println("ArrayList is: '" + tempShapes + "'");
                this.shapes.clear(); //clear the client's shapes before we draw shapes received from server
                for(DShape aShape: tempShapes){
                    System.out.println("Adding shape to 'shapes' which calls repaint");
                    this.addShape(aShape);
                }
            }
            System.out.println("End Reciever");
        }
	private void sender(int port) throws  IOException {
		ServerSocketChannel ssChannel = ServerSocketChannel.open();
		ssChannel.configureBlocking(true);
                int testPort = 12345;
		ssChannel.socket().bind(new InetSocketAddress(port));
		String obj ="textTextHello";
		while (true) {
			SocketChannel sChannel = ssChannel.accept();
			ObjectOutputStream  oos = new ObjectOutputStream(sChannel.socket().getOutputStream());
                        oos.writeObject(this.shapes);
			oos.writeObject(obj);
			oos.close();
			System.out.println("Connection ended");
		}
	}

   public ArrayList<DShape> getCollection() {
        return shapes;
    }
}