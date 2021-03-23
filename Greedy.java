package gametheory.assignment2.students2021;
import gametheory.assignment2.Player;
import java.util.Random;
import java.util.Arrays;

/**
 * Greedy class implements moose which moves to one of fields
 * with max grass on it
 */
public class Greedy implements Player{

	Random random;

    // constructor; define random as new random
	public Greedy(){
		this.random = new Random();
	}

    // reinitialize random
    public void reset(){
        this.random = new Random();
    };

    // choose one of the field with max amount of grass and return it
    public int move(int opponentLastMove, int xA, int xB, int xC){
        if(opponentLastMove == 0){ // go to random field as they have equal amount of grass
            int random_move = this.random.nextInt(3)+1;
            return random_move;
        } else { // choose one of fields with max amount of grass on it
            // find max amount of grass
            int fields[] = {xA, xB, xC};
            Arrays.sort(fields);
            int max_grass = fields[2];
            fields = new int[] {xA, xB, xC};
            int max_fields[] = new int[3];
            // count how many fields with max amount of grass
            int count_of_max = 0;
            if (xA == max_grass){
                max_fields[count_of_max] = 1;
                count_of_max = count_of_max + 1;
            };
            if (xB == max_grass){
                max_fields[count_of_max] = 2;
                count_of_max = count_of_max + 1;
            };
            if (xC == max_grass){
                max_fields[count_of_max] = 3;
                count_of_max = count_of_max + 1;
            };
            // choose from them
            int random_max_field_index = this.random.nextInt(count_of_max);
            return max_fields[random_max_field_index];
        }
    };

    //return mail
    public String getEmail(){
        return "r.valeev@innopolis.university";
    };
}