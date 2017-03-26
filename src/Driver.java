public class Driver {
	Coordinates co = new Coordinates();
	Observer observer = new Observer();
	
	public boolean checkMove(String fromPiece, String toSquare, String pColour){
		Square last = observer.getCurrentState().getLastLandedOn();
		Square movedTo = observer.getCurrentState().getBoard().getStringSquare(toSquare);
		Piece piece = observer.getCurrentState().getPieces().getPiece(fromPiece);
		char movingPieceID = piece.getID().charAt(0); 
		String movingPieceColour = piece.getColour();
		
		if(movingPieceID == pColour.charAt(0)){
			if(last.getColour().equals("Default")){
				if(movedTo.isEmpty()){
					if(co.isMoveForward(piece.getPiecePosition(), movedTo.getSquarePosition(), movingPieceID)){
						observer.getCurrentState().getLastLandedOn().setSquareColour(movedTo.getColour());
						observer.getCurrentState().getLastLandedOn().setOccupied();
						observer.getCurrentState().getBoard().getStringSquare(toSquare).setOccupied();
						observer.getCurrentState().getBoard().getStringSquare(piece.getPiecePosition()).clear();
						piece.setPiecePosition(toSquare);
						if(observer.getCurrentState().gameOver(piece)){
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
			else if(movingPieceColour.equals(last.getColour())){
				if(toSquare.isEmpty()){
					if(co.isMoveForward(piece.getPiecePosition(), toSquare, movingPieceID)){
						observer.getCurrentState().getLastLandedOn().setSquareColour(movedTo.getColour());
						observer.getCurrentState().getLastLandedOn().setOccupied();
						observer.getCurrentState().getBoard().getStringSquare(toSquare).setOccupied();
						observer.getCurrentState().getBoard().getStringSquare(piece.getPiecePosition()).clear();
						piece.setPiecePosition(toSquare);
						if(observer.getCurrentState().gameOver(piece)){
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
		System.out.println("All other options fell thru");
		return false;
	}
}

