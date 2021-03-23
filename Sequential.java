package gametheory.assignment2.students2021;
import gametheory.assignment2.Player;
import java.util.Random;

/**
 * Sequential class implements moose which moves through fields sequentially,
 * from first to second, fro, second to third, from third to first
 */
public class Sequential implements Player{

	Random random;
    int lastMove;

	public Sequential(){
		this.random = new Random();
        this.lastMove = 0;
	}

    public void reset(){
        this.random = new Random();
        this.lastMove = 0;
    };

    public int move(int opponentLastMove, int xA, int xB, int xC){
    	if (opponentLastMove == 0){ // first move - go to random field
            int randomMove = this.random.nextInt(3) + 1;
            this.lastMove = randomMove;
            return randomMove;
        }else{ // go to next field
            this.lastMove = this.lastMove % 3 + 1;
            return lastMove;
        }
    };

    public String getEmail(){
        return "r.valeev@innopolis.university";
    };
}