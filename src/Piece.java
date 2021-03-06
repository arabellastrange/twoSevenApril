import java.io.Serializable;

public class Piece implements Serializable{
	String colour;
	String position;
	String id;
	
	public Piece(String pieceColour, String piecePosition, String pid){
		colour = pieceColour;
		position = piecePosition;
		id = pid;
	}
	
	public void setColour(String sColour){
		colour = sColour;
	}
	
	public String getColour(){
		return colour;
	}
	
	public String getID(){
		return id;
	}
	
	public String getPiecePosition(){
		return position; 
	}
	
	public void setPiecePosition(String piecePosition){
		position = piecePosition; 
	}
	
}
