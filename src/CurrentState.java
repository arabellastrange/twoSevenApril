import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class CurrentState implements Serializable{

	private static final long serialVersionUID = 1L;
	
	ArrayList<String> gamesSettings = new ArrayList<String>();
	ArrayList<Player> players = new ArrayList<Player>();
	Stack<CurrentState> states = new Stack<CurrentState>();
	Coordinates co = new Coordinates();
	Settings timer;
	Square lastLandedOn; 	
	GamePieces pieces;
	Board board;
	String AImove;
	int roundNumber = 1;
	//AIPlayer AI;
	
	public CurrentState(){
		lastLandedOn = new Square("Default", "Defualt", false);
		timer = new Settings();
		pieces = new GamePieces();
		board = new Board();
		//AI = new AIPlayer();

	}
	
	public void createState(){
		new CurrentState();
	}
	
	public CurrentState getState(){
		return this;
	}
	
	public boolean roundOver(Piece p){
		states.add(this);
		if(p.getID().startsWith("W") && p.getPiecePosition().charAt(1) == 0){
			roundNumber++;
			getActivePlayer().addPoints(1);
			if(gameOver()){
				System.exit(0);
			}
			return true;
		}
		else if(p.getID().startsWith("B") && p.getPiecePosition().charAt(1) == 7){
			roundNumber++;
			getActivePlayer().addPoints(1);
			gameOver();
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean gameOver(){
		for(int i = 0; i < players.size(); i++){
			if(players.get(i).getPoints() == 3){
				return true;
			}
		}
		return false;
	}
		
	public Piece getPiece(String id){
		return pieces.getPiece(id);
	}
	
	public GamePieces getPieces(){
		return pieces;
	}
	
	public Board getBoard(){
		return board;
	}
	
	public Settings getTime(){
		return timer;
	}
	
	public void setCurrentState(Settings time, Board boards, GamePieces piecesUsed, ArrayList<String> Settings, Square last){
		timer = time;
		board = boards;
		pieces = piecesUsed;
		gamesSettings = Settings;
		lastLandedOn = last;
		
		
	}
	public boolean undoMove(){
		try{
			states.pop();
			states.pop();
			setCurrentState(states.peek().getTime(), states.peek().getBoard(), states.peek().getPieces(), states.peek().getSettings(), states.peek().getLastLandedOn());
			return true;
		}
		catch(Exception e){
			System.out.println("Not enough moves to undo");
			return false;
		}
	}
	public Piece getFreePiece(String AIColour){
		String lastCol;
		Square last = getLastLandedOn(); //gets the last landed on square
		lastCol = last.getColour(); //gets the colour of that square

		if(lastCol.equals("Default")){
			Piece p[] = getPieces().getPieces();
			for(Piece i: p){
				if(AIColour.equals("B")){
					if(i.getID().startsWith("B")){
						if(i.getColour().equals("Red")){
							return i;
						}
					}
				}	
				else{
					if(i.getID().startsWith("W")){
						if(i.getColour().equals("Red")){
							return i;
						}
					}
				}
			}
			return null;
		}
		else{
			Piece p[] = getPieces().getPieces();
			for(Piece i: p){
				if(AIColour.equals("B")){
					if(i.getID().startsWith("B")){
						if(i.getColour().equals(lastCol)){
							return i;
						}
					}
				}
				else{
					if(i.getID().startsWith("W")){
						if(i.getColour().equals(lastCol)){
							return i;
						}
					}
				}
			}
			return null;				
		}
	}
	
	public boolean makeAIMove(String AIColour){
		String forwardSquare;
		String leftDiagonalSquare;
		String rightDiagonalSquare;
		
		Piece free = getFreePiece(AIColour);
		co.stringToXY(free.getPiecePosition());
		int x = co.getX();
		int y = co.getY();
		//find piece belonging to AI that is same colour as last landed on square
		if(AIColour.equals("B")){
			forwardSquare = co.XYtoString(x, y + 1); //AI move forward
			leftDiagonalSquare = co.XYtoString(x - 1, y + 1); //AI move left diagonal
			rightDiagonalSquare = co.XYtoString(x + 1, y + 1); //AI move right diagonal
		}
		else{
			forwardSquare = co.XYtoString(x, y - 1); //AI move forward
			leftDiagonalSquare = co.XYtoString(x + 1, y - 1); //AI move left diagonal
			rightDiagonalSquare = co.XYtoString(x - 1, y - 1); //AI move right diagonal
		}
		
		if(makeMove(free.getID(), forwardSquare, AIColour)){ //if forward move is okay, do that
			AImove = forwardSquare;
			return true;
		}
		else if(makeMove(free.getID(), leftDiagonalSquare, AIColour)){ //if not and left diagonal is, do that
			AImove = leftDiagonalSquare;
			return true;
		}
		else if(makeMove(free.getID(), rightDiagonalSquare, AIColour)){ //if not and right is, do that
			AImove = rightDiagonalSquare;
			return true;
		}
		else{
			System.out.println("The AI couldn't make any legal moves."); //else return message and move on
			return false;
		}
			
	}
	
	public boolean makeHardAIMove(String AIColour){
		String forwardSquare = "";
		String leftDiagonalSquare = "";
		String rightDiagonalSquare = "";
		
		Piece free = getFreePiece(AIColour);
		co.stringToXY(free.getPiecePosition());
		int x = co.getX();
		int y = co.getY();
		
		if(AIColour.equals("B")){
			forwardSquare = co.moveDown(free.getPiecePosition());
			leftDiagonalSquare = co.moveDiagonalLeftDown(free.getPiecePosition());
			rightDiagonalSquare = co.moveDiagonalRightDown(free.getPiecePosition());
			
			int i = co.getY();
			int j = co.getY(); //horizontals go too far, must update in loop???
			int k = co.getY();
			int m = co.getX();
			int n = co.getX();
			
			while(!forwardSquare.isEmpty() && i < 6){
				co.stringToXY(forwardSquare);
				x = co.getX();
				y = co.getY();
				forwardSquare = co.XYtoString(x, y + 1); //AI move forward
				i++;
			}
			while(!leftDiagonalSquare.isEmpty()  && j < 8 && m < 8){
				co.stringToXY(leftDiagonalSquare);
				x = co.getX();
				y = co.getY();
				leftDiagonalSquare = co.XYtoString(x - 1, y + 1); //AI move left diagonal
				j++;
				m++;
			}
			while(!rightDiagonalSquare.isEmpty()  && k < 8 && n >= 0){
				co.stringToXY(rightDiagonalSquare);
				x = co.getX();
				y = co.getY();
				rightDiagonalSquare = co.XYtoString(x + 1, y + 1); //AI move right diagonal
				k++;
				n--;
			}
		}
		else{
			forwardSquare = co.moveUp(free.getPiecePosition());
			leftDiagonalSquare = co.moveDiagonalLeftUp(free.getPiecePosition());
			rightDiagonalSquare = co.moveDiagonalRightUp(free.getPiecePosition());
			
			while(!forwardSquare.isEmpty() && co.getY() > 8){
				co.stringToXY(forwardSquare);
				x = co.getX();
				y = co.getY();
				forwardSquare = co.XYtoString(x, y - 1); //AI move forward
			}
			while(!leftDiagonalSquare.isEmpty() && co.getY() < 8 && co.getX() >= 0){
				co.stringToXY(leftDiagonalSquare);
				x = co.getX();
				y = co.getY();
				leftDiagonalSquare = co.XYtoString(x + 1, y - 1); //AI move left diagonal
			}
			while(!rightDiagonalSquare.isEmpty()  && co.getY() < 8 && co.getX() < 8){
				co.stringToXY(rightDiagonalSquare);
				x = co.getX();
				y = co.getY();
				rightDiagonalSquare = co.XYtoString(x - 1, y - 1); //AI move right diagonal
			}
		}
		
		if(makeMove(free.getID(), forwardSquare, AIColour)){ //if forward move is okay, do that
			AImove = forwardSquare;
			return true;
		}
		else if(makeMove(free.getID(), leftDiagonalSquare, AIColour)){ //if not and left diagonal is, do that
			AImove = leftDiagonalSquare;
			return true;
		}
		else if(makeMove(free.getID(), rightDiagonalSquare, AIColour)){ //if not and right is, do that
			AImove = rightDiagonalSquare;
			return true;
		}
		else{
			System.out.println("The AI couldn't make any legal moves."); //else return message and move on
			return false;
		}
		
	}
	
	public void storeSettings(ArrayList<String> settings){
		gamesSettings = settings;
	}
	
	public ArrayList<String> getSettings(){
		return gamesSettings;
	}
	public String getAIMove(){
		return AImove;
	}
	
	public boolean makeMove(String fromPiece, String toSquare, String pColour){
		if(checkInRange(fromPiece, toSquare)){
			Square last = getLastLandedOn();
			Square movedTo = getBoard().getStringSquare(toSquare);
			GamePieces pieces = getPieces();
			Piece piece = pieces.getPiece(fromPiece);
			char movingPieceID = piece.getID().charAt(0);
			String movingPieceColour = piece.getColour();
			
			//System.out.println(movedTo.empty);
			
			if(movingPieceID == pColour.charAt(0)){
				if(last.getColour().equals("Default")){
					if(movedTo.isEmpty()){
						if(co.isMoveForward(piece.getPiecePosition(), movedTo.getSquarePosition(), movingPieceID)){
							getLastLandedOn().setSquareColour(movedTo.getColour());
							getLastLandedOn().setOccupied();
							getBoard().getStringSquare(toSquare).setOccupied();
							getBoard().getStringSquare(piece.getPiecePosition()).clear();
							piece.setPiecePosition(toSquare);
							if(roundOver(piece)){
								//System.out.println("Congrats! You won!");
								//send message back to the GUI????
								//System.exit(0);
								
							}
							return true;
						}
						else{
							System.out.println("You cannot move in this direction - only straight and diagonally forward.");
							return false;
						}
					}
					else{
						System.out.println("You cannot put your piece here as the square is already occupied.");
						return false;
					}
				}
				else if(movingPieceColour.equals(last.getColour())){
					if(!toSquare.isEmpty()){
						if(co.isMoveForward(piece.getPiecePosition(), toSquare, movingPieceID)){
							getLastLandedOn().setSquareColour(movedTo.getColour());
							getLastLandedOn().setOccupied();
							getBoard().getStringSquare(toSquare).setOccupied();
							getBoard().getStringSquare(piece.getPiecePosition()).clear();
							piece.setPiecePosition(toSquare);
							if(roundOver(piece)){
								System.out.println("Congrats! You won!");
								System.exit(0);
							}
							return true;
						}
						else{
							System.out.println("You cannot move in this direction - only straight and diagonally forward.");
							return false;
							}
						}
					else{
						System.out.println("You cannot put your piece here as the square is already occupied.");
						return false;
					}
				}
				else if(!last.getColour().equals("Default") || !movingPieceColour.equals(last.getColour())){
						System.out.println("You cannot move this piece as it is not the same colour as the last landed on square - " + last.getColour());
						return false;
				}
				else{
					System.out.println("This is not one of you pieces.");
					return false;
				}
				
			}
		}
		else{
			System.out.println("Piece or Square out of range");
			return false;
		}
		return false;
	}
	
	public Square getLastLandedOn(){
		return lastLandedOn;
	}
	
	// this should probs be in a diff class tbh 
	public Boolean checkInRange(String piece, String square){
		if(Integer.parseInt(piece.substring(1)) > 8 || Integer.parseInt(piece.substring(1)) < 0){
			return false;
		}
		
		co.stringToXY(square);
		if(co.getY() > 8 || co.getY() < 0){
			return false;
		}
		
		return true;
	}
	
	public void createPlayer(){
		Player player = new Player();
		players.add(player);
	}
	
	public Player getActivePlayer(){
		for(int i = 0; i < players.size(); i++){
			if(players.get(i).isActive()){
				return players.get(i);
			}
		}
		return null;
	}
	
	public Player getPlayer(int playerID){
		if(playerID == 1){
			return players.get(0);
		}
		else{
			return players.get(1);
		}
	}
	
	public Player getNextPlayer(){
		for(int i = 0; i < players.size(); i++){
			if(!players.get(i).isActive()){
				return players.get(i);
			}
		}
		return null;
	}
	
}