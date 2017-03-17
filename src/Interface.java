import java.util.ArrayList;
import java.util.Scanner;

public class Interface {
	ArrayList<String> updatedSquares = new ArrayList<String>();
	
	public static void main(String[] args){
		String account;
		String option;
		String playerColour;
		Player playerOne = new Player();
		System.out.println("Welcome to Kamisado, please enter yor name: ");
		Scanner s = new Scanner(System.in);
		account = s.nextLine().trim().toUpperCase();
		playerOne.setName(account);
		
		System.out.println("Do you wish to play against AI [selelct A] or human [select H]?");
		option = s.nextLine().trim().toUpperCase();
		
		if(option.equals("A")){
			System.out.println("You are playing agint AI");
			Interface i = new Interface();
		}
		else if(option.equals("H")){
			Player playerTwo = new Player();
			System.out.println("You are playing against another human! Player two, enter your name: ");
			account = s.nextLine().trim().toUpperCase();
			playerTwo.setName(account);
			
			System.out.println("Player one select your colour! [White or Black]");
			playerColour = s.nextLine().trim().toUpperCase();
			playerOne.setColour(playerColour);
			
			if(playerColour.equals("W")){
				playerTwo.setColour("B");
			}
			else{
				playerTwo.setColour("W");
			}
			
			Interface i = new Interface();
			
			System.out.println("Begin Game! Press S to start or Q to quit");
			option = s.nextLine().trim().toUpperCase();
			do{
				System.out.println("Player One make a move! Select the piece you wish to move: ");
				String piece = s.nextLine().trim().toUpperCase();
				isQuit(piece);
				System.out.println("Player One select the square you wish to move to: ");
				String square = s.nextLine().trim().toUpperCase();
				isQuit(square);
				if(playerOne.makeMove(piece, square, playerOne.getColour())){
					i.updateInterface(piece, square);
				}
				else{
					System.out.println("That is not a valid move!");
				}
				
				System.out.println("Player Two make a move! Select the piece you wish to move: ");
				piece = s.nextLine().trim().toUpperCase();
				isQuit(piece);
				System.out.println("Player Two select the square you wish to move to: ");
				square = s.nextLine().trim().toUpperCase();
				isQuit(square);
				if(playerTwo.makeMove(piece, square, playerTwo.getColour())){
					i.updateInterface(piece, square);
				}
				else{
					System.out.println("That is not a valid move!");
				}
			}
			while(!option.equals("Q"));
		}
		else{
			System.out.println("That is not a valid option");
		}
	}
	
	public Interface(){
		System.out.println("         Kamisado");
		System.out.println("   A  B  C  D  E  F  G  H");
		for(int m = 0; m <8; m++){	
			System.out.print(m + " ");
			for(int n = 0; n < 8; n++){
				if(m == 0){
					System.out.print("|�|");
				}
				else if(m == 7){
					System.out.println("|�|");
				}
				else{
					System.out.print("|_|");
				}	
			} 
			System.out.println();
		}
	}
	
	public void updateInterface(String piece, String square){
		updatedSquares.add(square);
		
		Coordinates coord = new Coordinates();
		coord.stringToXY(square);
		
		int colour = -1;
		
		if(piece.startsWith("W")){
			colour = 0;
		}
		else if(piece.startsWith("B")){
			colour = 1;
		}
		
		System.out.println("         Kamisado");
		System.out.println("   A  B  C  D  E  F  G  H");
		for(int m = 0; m <8; m++){	
			System.out.print(m + " ");
			for(int n = 0; n < 8; n++){
				if(m == 0){
					System.out.print("|�|");
				}
				else if(m == 7){
					System.out.println("|�|");
				}
				else{
					System.out.print("|_|");
				}	
			} 
			System.out.println();
			}
		
	}
	
	public static void isQuit(String input){
		if(input.equals("Q")){
			System.out.println("Goodbye");
			System.exit(0);
		}
	}
}
