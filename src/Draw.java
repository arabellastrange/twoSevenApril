import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Draw extends JPanel implements MouseMotionListener, MouseListener {
	Observer observer = new Observer();
	
	ArrayList<BufferedImage> draggedPieces = new ArrayList<BufferedImage>();
	ArrayList<Rectangle> draggedRect = new ArrayList<Rectangle>();
	Rectangle draggedRe = new Rectangle();
	BufferedImage dragged;
	
	ArrayList<Rectangle> gamePieces =  new ArrayList<Rectangle>();
	ArrayList<BufferedImage> Pieces = new ArrayList<BufferedImage>();
	
	Point lastLoc;
	int currentX;
	int currentY;
	Point currentPoint;
	
	String square;
	String piece;
	
	public JPanel makeABoard(){
		JPanel board = null;
		 try {
		    	final BufferedImage gboard = ImageIO.read(new File("cutsomGameBoard.jpg"));
		    	final BufferedImage featPanel = ImageIO.read(new File("extraPanel.png"));
		    	
		    	//load white game pieces
		    	Pieces.add(ImageIO.read(new File("rect-lightOrange.png")));
		    	Pieces.add(ImageIO.read(new File("rect-navy.png")));
		    	Pieces.add(ImageIO.read(new File("rect-maroon.png")));	
		    	Pieces.add(ImageIO.read(new File("rect-lilac.png")));
		    	Pieces.add(ImageIO.read(new File("rect-yellow.png")));
		    	Pieces.add(ImageIO.read(new File("rect-orange.png")));
		    	Pieces.add(ImageIO.read(new File("rect-green.png")));
		    	Pieces.add(ImageIO.read(new File("rect-brown.png")));

		    	//load black game pieces
		    	Pieces.add(ImageIO.read(new File("rev-brown.png")));
		    	Pieces.add(ImageIO.read(new File("rev-green.png")));
		    	Pieces.add(ImageIO.read(new File("rev-orange.png")));
		    	Pieces.add(ImageIO.read(new File("rev-yellow.png")));
		    	Pieces.add(ImageIO.read(new File("rev-lilac.png")));
		    	Pieces.add(ImageIO.read(new File("rev-maroon.png")));
		    	Pieces.add(ImageIO.read(new File("rev-navy.png")));
		    	Pieces.add(ImageIO.read(new File("rev-lighOrange.png")));
		    	
		    	int i = 0;
		    	for(Piece p : observer.getCurrentState().getPieces().getPieces()){
		    		p.setIcon(Pieces.get(i));
		    		i++;
		    	}
		    	
		    	board = new JPanel(){
		            protected void paintComponent(Graphics g) {
		                g.drawImage(gboard, 0, 0, this);

		                starterPieces(g);  
		            }
		            @Override
		            public Dimension getPreferredSize() {
		                return new Dimension(480, 480);
		            }   
				};
			}
		    catch (IOException e) {
				e.printStackTrace();
			}
		board.addMouseListener(this);
		board.addMouseMotionListener(this);
		return board;
		    
	}
	
	public void mouseClicked(MouseEvent e) {

		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mouseMoved(MouseEvent arg0) {
		
	}

	public void mousePressed(MouseEvent e) {      
        for(BufferedImage img: Pieces){
     	   for(Rectangle r: gamePieces){
         	   if(r.contains(e.getPoint())){
         		   System.out.println("in");
         		   dragged = img;
         		   draggedRe = r;
         		   //piece =  this one but to string, set piece id W
         		   lastLoc = e.getPoint();
         		   break;
         	   }
         	   else{
         		   System.out.println("out");
         	   }
     	   }
        }
     }
	
 	public void mouseDragged(MouseEvent e) {
 		System.out.println("X is: " + e.getX()); 
 		System.out.println("y is: " + e.getY());
 		currentX  = e.getX();
 		currentY = e.getY();
 		currentPoint = e.getPoint();
			if (dragged != null) {
				repaint();
				System.out.println("dragged");
			}
 	}
 	
 	public void mouseReleased(MouseEvent e) {
     		//square movedTo = this one but to string
     		//if(gameInterface().movePiece()){
     			draggedPieces.add(dragged);
     			draggedRect.add(draggedRe);
     		//}
     		dragged = null;
     		draggedRe = null;
     		lastLoc = null;
            System.out.println("release");
 		
 	} 
 	
 	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform at = new AffineTransform();
		at.translate(currentX - lastLoc.x, currentY - lastLoc.y);
		lastLoc = currentPoint;
		g2.drawImage(dragged, at, this);
		draggedRe.translate(currentX - lastLoc.x, currentX - lastLoc.y);
		updateMovedPieces(g);
 	}
 	
 	public void starterPieces(Graphics g){
        int x = 5;
        int y = 5;
        for(int i = 0; i <8; i++){
        	g.drawImage(Pieces.get(i), x, y, this);
        	x += 60;
        }
        x = 5;
        y = 425;
        for(int i = 8; i <16; i++){
        	g.drawImage(Pieces.get(i), x, y, this);
        	x += 60;
        }
        
        Graphics2D g2 = (Graphics2D) g;
        
		for(int i = 5; i < 480; i+= 60){	
			Rectangle b = new Rectangle(i, 425, 40, 40);
			gamePieces.add(b);
			g2.draw(b);
		}
		
		for(int i = 5; i < 480; i+= 60){
			Rectangle w = new Rectangle(i, 5, 40, 40);
			gamePieces.add(w);
			g2.draw(w);
		}
 		
 	}
 	
 	public void updateMovedPieces(Graphics g){
 		Graphics2D g2 = (Graphics2D) g;
        for(int i = 0; i < draggedPieces.size()-1; i++)
			if(draggedPieces.get(i) != null){
				g.drawImage(draggedPieces.get(i), currentX, currentY, this);
		}
        
        for(int i = 0; i < draggedRect.size()-1; i++)
			if(draggedRect.get(i) != null){
				g2.draw(draggedRect.get(i));
		}
 	}
 	
 	public void clearOldPieces(){
 		
	}
 	
// translate X,Y to a string id for piece and square
// public String translatePoint(){
// 		return "do this";
// 	}
 	
 	public void updateBoard(String pieceInput, String squareInput){
 		Graphics g;
 		Square sq = observer.getCurrentState().getBoard().getStringSquare(squareInput);
 		g.drawImage(observer.getCurrentState().getPiece(pieceInput).getIcon(), sq.getSquareLocation().getX(), sq.getSquareLocation().getY(), this);
 	}
 	
 	//for the drag and drop stuff
 	public String getSelectedPiece(){
 		return piece;
 	}
 	
 	public String getMovedToSquare(){
 		return square;
 	}

}
