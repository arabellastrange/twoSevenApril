import java.awt.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Interface {

	Settings set = new Settings();
	AIPlayer AI = new AIPlayer();
	ReadWrite save = new ReadWrite();
	Observer observer = new Observer();

	public ArrayList<String> loadGame(){
		if(save.loadCurrentState()){
			observer.getCurrentState().createState();
			return observer.getCurrentState().getSettings();
			}
		return null;
	}
	
	public boolean playAI(String piece, String square, String playColour){
		observer.getCurrentState().createState();
		if(observer.getCurrentState().makeMove(piece, square, playColour)){
			String AIMove = "";
			String AIPiece = "";
			if(observer.getCurrentState().makeAIMove(AI.getAIColour())){
				AIMove = observer.getCurrentState().getAIMove();
				AIPiece = observer.getCurrentState().getFreePiece().getID();
				return true;
			}
		}
		else{
			return false;
		}
		return false;
	}
	
	public boolean playTimed(String piece, String square, String playColour, double timeLength){
		observer.getCurrentState().createState();
		System.out.println(set.getTime());
		if(set.getTime() >= timeLength){
			set.clearTimer();
			return false;
		}
		else{
			if(observer.getCurrentState().makeMove(piece, square, playColour)){
				set.clearTimer();
				return true;
			}
		}
		return false;
	}
	
	public boolean play(String piece, String square, String playColour){
		if(observer.getCurrentState().makeMove(piece, square, playColour)){
			return true;
		}
		else{
			return false;
			//back to orignal square?
		}
	}
	

	public void store(ArrayList<String> settings){
		observer.getCurrentState().storeSettings(settings);
		save.saveCurrentState();
	}
}
