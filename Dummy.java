package gametheory.assignment2.students2021;
import gametheory.assignment2.Player;
import java.util.Random;

/**
 * Class Dummy implements random moving moose, which
 * moves randomly each round
 */
public class Dummy implements Player{

	Random random;

	// constructor; sets random
	public Dummy(){
		this.random = new Random();
	}

	// reinitialize random
    public void reset(){
        this.random = new Random();
    };

    // return random move
    public int move(int opponentLastMove, int xA, int xB, int xC){
    	int random_move = random.nextInt(3)+1;
        return random_move;
    };

    // return email
    public String getEmail(){
        return "r.valeev@innopolis.university";
    };
}