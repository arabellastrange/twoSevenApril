import java.util.ArrayList;
import java.util.Scanner;


public class Interface {
	ArrayList<String> updatedSquares = new ArrayList<String>();
	ArrayList<String> allSquares = new ArrayList<String>(); // make a list of all squares and print them out if you, then print out all the updated squares, in a square is in both all squares and updated squares then clear it in all squares
	
	public static void main(String[] args){
		String account;
		String option;
		String playerColour;
		Settings set = new Settings();
		Player playerOne = new Player();
		Player playerTwo = new Player();
		
		System.out.println("Welcome to Kamisado, please enter your name: ");
		Scanner s = new Scanner(System.in);
		account = s.nextLine().trim().toUpperCase();
		playerOne.setName(account);
		
		System.out.println("Do you wish to play against AI [selelct A] or human [select H]?");
		option = s.nextLine().trim().toUpperCase();
		
		if(option.equals("A")){
			System.out.println("You are playing agint AI");		
		}
		else if(option.equals("H")){
			System.out.println("You are playing against another human! Player two, enter your name: ");
			account = s.nextLine().trim().toUpperCase();
			if(playerOne.getName().equals(account)){
				System.out.println("Both players cannot use the same name. Please enter a unique account name:");
				account = s.nextLine().trim().toUpperCase();
				playerTwo.setName(account);			
			}
			else{
				playerTwo.setName(account);
			}
		
			System.out.println("Player one select your colour! [White or Black]");
			playerColour = s.nextLine().trim().toUpperCase();
			playerOne.setColour(playerColour);
			
			if(playerColour.equals("W")){
				playerTwo.setColour("B");
			}
			else if(playerColour.equals("Q")){
				isQuit(playerColour);
			}
			else{
				playerTwo.setColour("W");
			}
			
			System.out.println("Do you wish to play in speed mode? [Y/N]");
			String time = s.nextLine().trim().toUpperCase();
			
			if(time.equals("Y")){
				System.out.println("Set the timer value you would like to use (in minutes): ");
				double length = s.nextDouble();
				set.setTimer(length);
			}
			
			Interface board = new Interface();
			
			System.out.println("Begin Game! Press S to start or Q to quit at any point");
			option = s.nextLine().trim().toUpperCase();
			isQuit(option);
			do{
				System.out.println("Player One make a move! Select the piece you wish to move: ");
				String piece = s.nextLine().trim().toUpperCase();
				isQuit(piece);
				System.out.println("Player One select the square you wish to move to: ");
				String square = s.nextLine().trim().toUpperCase();
				isQuit(square);
				
				if(playerOne.makeMove(piece, square)){
					board.updateInterface(piece, square);
				}
				
				System.out.println("Player Two make a move! Select the piece you wish to move: ");
				piece = s.nextLine().trim().toUpperCase();
				isQuit(piece);
				System.out.println("Player Two select the square you wish to move to: ");
				square = s.nextLine().trim().toUpperCase();
				isQuit(square);
				
				if(playerTwo.makeMove(piece, square)){
					board.updateInterface(piece, square);
				}
			}
			while(!option.equals("Q"));
		}
		else{
			System.out.println("That is not a valid option");
		}
	}
	
	public Interface(){
		allSquares.add("\u001b[1;45m|\u001b[0m表u001b[1;45m|\u001b[0m"); // purple
		allSquares.add("\u001b[1;44m|\u001b[0m表u001b[1;44m|\u001b[0m"); // blue
		allSquares.add("\u001b[1;46m|\u001b[0m表u001b[1;46m|\u001b[0m"); // cyan
		allSquares.add("\u001b[0;47m|\u001b[0m表u001b[0;47m|\u001b[0m"); // pink
		allSquares.add("\u001b[1;43m|\u001b[0m表u001b[1;43m|\u001b[0m"); // yellow
		allSquares.add("\u001b[1;41m|\u001b[0m表u001b[1;41m|\u001b[0m"); // red
		allSquares.add("\u001b[1;42m|\u001b[0m表u001b[1;42m|\u001b[0m"); // green
		allSquares.add("\u001b[1;40m|\u001b[0m表u001b[1;40m|\u001b[0m"); // black

		allSquares.add("\u001b[1;41m|_|\u001b[0m"); // row 1
		allSquares.add("\u001b[1;45m|_|\u001b[0m");
		allSquares.add("\u001b[0;47m|_|\u001b[0m");
		allSquares.add("\u001b[1;42m|_|\u001b[0m");
		allSquares.add("\u001b[1;44m|_|\u001b[0m");
		allSquares.add("\u001b[1;43m|_|\u001b[0m");
		allSquares.add("\u001b[1;40m|_|\u001b[0m");
		allSquares.add("\u001b[1;46m|_|\u001b[0m");
		
		allSquares.add("\u001b[1;42m|_|\u001b[0m"); //row 2
		allSquares.add("\u001b[0;47m|_|\u001b[0m"); 
		allSquares.add("\u001b[1;45m|_|\u001b[0m");
		allSquares.add("\u001b[1;41m|_|\u001b[0m");
		allSquares.add("\u001b[1;46m|_|\u001b[0m");
		allSquares.add("\u001b[1;40m|_|\u001b[0m");
		allSquares.add("\u001b[1;43m|_|\u001b[0m");
		allSquares.add("\u001b[1;44m|_|\u001b[0m");
		
		allSquares.add("\u001b[0;47m|_|\u001b[0m"); //row 3
		allSquares.add("\u001b[1;46m|_|\u001b[0m");
		allSquares.add("\u001b[1;44m|_|\u001b[0m");
		allSquares.add("\u001b[1;45m|_|\u001b[0m");
		allSquares.add("\u001b[1;40m|_|\u001b[0m");
		allSquares.add("\u001b[1;42m|_|\u001b[0m");
		allSquares.add("\u001b[1;41m|_|\u001b[0m");
		allSquares.add("\u001b[1;43m|_|\u001b[0m");
		
		allSquares.add("\u001b[1;43m|_|\u001b[0m"); //row 4
		allSquares.add("\u001b[1;41m|_|\u001b[0m");
		allSquares.add("\u001b[1;42m|_|\u001b[0m");
		allSquares.add("\u001b[1;40m|_|\u001b[0m");
		allSquares.add("\u001b[1;45m|_|\u001b[0m");
		allSquares.add("\u001b[1;44m|_|\u001b[0m");
		allSquares.add("\u001b[1;46m|_|\u001b[0m");
		allSquares.add("\u001b[0;47m|_|\u001b[0m");
		
		allSquares.add("\u001b[1;44m|_|\u001b[0m"); //row 5
		allSquares.add("\u001b[1;43m|_|\u001b[0m");
		allSquares.add("\u001b[1;40m|_|\u001b[0m");
		allSquares.add("\u001b[1;46m|_|\u001b[0m");
		allSquares.add("\u001b[1;41m|_|\u001b[0m");
		allSquares.add("\u001b[1;45m|_|\u001b[0m");
		allSquares.add("\u001b[0;47m|_|\u001b[0m");
		allSquares.add("\u001b[1;42m|_|\u001b[0m");
		
		allSquares.add("\u001b[1;46m|_|\u001b[0m"); //row 6
		allSquares.add("\u001b[1;40m|_|\u001b[0m");
		allSquares.add("\u001b[1;43m|_|\u001b[0m");
		allSquares.add("\u001b[1;44m|_|\u001b[0m");
		allSquares.add("\u001b[1;42m|_|\u001b[0m");
		allSquares.add("\u001b[0;47m|_|\u001b[0m");
		allSquares.add("\u001b[1;45m|_|\u001b[0m");
		allSquares.add("\u001b[1;41m|_|\u001b[0m");
		
		allSquares.add("\u001b[1;40m|\u001b[0mｰ\u001b[1;40m|\u001b[0m"); // black
		allSquares.add("\u001b[1;42m|\u001b[0mｰ\u001b[1;42m|\u001b[0m"); // green
		allSquares.add("\u001b[1;41m|\u001b[0mｰ\u001b[1;41m|\u001b[0m"); // red
		allSquares.add("\u001b[1;43m|\u001b[0mｰ\u001b[1;43m|\u001b[0m"); // yellow
		allSquares.add("\u001b[1;47m|\u001b[0mｰ\u001b[1;47m|\u001b[0m"); // pink
		allSquares.add("\u001b[1;46m|\u001b[0mｰ\u001b[1;46m|\u001b[0m"); // cyan
		allSquares.add("\u001b[1;44m|\u001b[0mｰ\u001b[1;44m|\u001b[0m"); // blue
		allSquares.add("\u001b[1;45m|\u001b[0mｰ\u001b[1;45m|\u001b[0m"); // purple
		
		printInterface();
	}
	
	public void printInterface(){
		System.out.println("         Kamisado");
		System.out.println("   A  B  C  D  E  F  G  H");
		int i = 0;
		for(int m = 0; m <8; m++){	
			System.out.print(m + " ");
			for(int n = 0; n < 8; n++){
				System.out.print(allSquares.get(i));
				i++;
			}
			System.out.println();
		}
	}
	
	public void updateInterface(String piece, String square){
		Coordinates coord = new Coordinates();
		coord.stringToXY(square);
		int x = coord.getX();
		int y = coord.getY();
		int sqIndex = x + (y*8);
		
		if(piece.startsWith("W")){
			String newSq = allSquares.get(sqIndex).replace("|_|","|ｰ|");
			allSquares.set(sqIndex, newSq);
		}
		else if(piece.startsWith("B")){
			String newSq = allSquares.get(sqIndex ).replace("|_|","|怖");
			allSquares.set(sqIndex, newSq);
		}
		
		printInterface();
		
	}
	
	public static void isQuit(String input){
		String quit = input.toUpperCase();
		if(quit.equals("Q")){
			System.out.println("Goodbye");
			System.exit(0);
		}
	}
}
