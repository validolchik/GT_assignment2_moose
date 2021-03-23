package gametheory.assignment2.students2021;
// import gametheory.assignment2.students2021.*;
import gametheory.assignment2.Player;
import java.util.Random;
import java.util.Arrays;

/**
 * Class Dummy implements random moving moose, which
 * moves randomly each round
 */
class Dummy implements Player{

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

/**
 * Greedy class implements moose, which moves to one of the fields
 * with max grass on it
 */
class Greedy implements Player{

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

/**
 * Sequential class implements moose which moves through fields sequentially,
 * from first to second, fro, second to third, from third to first
 */
class Sequential implements Player{

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

/**
 * Coop class implements moose which trying to cooperate, randomly moving until no collision with
 * another moose, then moving only to it's own field and common one. If other steps on our field,
 * then turn greedy mode.
 */

class Coop implements Player{
    int lastMove = 0; // our last move
    int ourField = -1; // our own field
    int restField = -1; // rest field number
    int mode = 0; // current behaviour mode of a agent or moose
	final Random random;

    // public constructor of a class
	public Coop(){
		random = new Random();
	}

    // resets mode, last move, our field values to default
    public void reset(){
        lastMove = 0;
        ourField = -1;
        restField = -1;
        mode = 0; 
    };
    
    /*
    make greedy move given amount of grass on fields
    choose fields with max grass on them and choose one of them randomly
    */
    public int greedyMove(int xA, int xB, int xC){
        // finding max amount of grass on fields
        int fields1[] = {xA, xB, xC};
        Arrays.sort(fields1);
        int max_grass = fields1[2];
        int max_fields[] = new int[3];
        
        // number of fields with max amount of grass on them
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
        // chooose random field with max amount of grass
        int random_max_field_index = random.nextInt(count_of_max);
        return max_fields[random_max_field_index];
    }

    // return move of a agent given opponent last move, amount grass on the fields
    public int move(int opponentLastMove, int xA, int xB, int xC){
        if (mode == 0){ // assuming that we versus coop
            // if we step on same field, generate new random field where to go
            if (opponentLastMove == lastMove){ 
                lastMove = random.nextInt(3) + 1;
                return lastMove;
            } else { // else assume that we versus coop moose and change to mode 1, cooperation mode
                mode = 1; // checking for coop
                ourField = lastMove;
                restField = 6 - lastMove - opponentLastMove;
                return restField;
            }
        } else if (mode == 1){ // we in cooperation mode
            /*
            pretend to be coop
            if betrayed then change to mod -1, greedy mode
            */
            int fields[] = {xA, xB, xC};
            if (opponentLastMove == ourField){ // if opponent not cooperating as we expected, turn greedy mode
                mode = -1; // greedy mode
                return greedyMove(xA, xB, xC);
            } else if (fields[ourField-1] >= 6){ // our fields full of grass
                lastMove = ourField;
                return ourField;
            } else if (lastMove == ourField){ // our last move was on our field, next move is to go to rest field
                lastMove = restField;
                return restField;
            } else {
                // unless our field is ours and other moose dont step on it, play nicely
                // and go from rest field to our and back
                return restField;
            }
        } else { // we in greedy mode, mo change back
            return greedyMove(xA, xB, xC);
        }
    };

    public String getEmail(){
        return "r.valeev@innopolis.university";
    };
}

/**
 * The Teesting class provides interface for playing single games
 * and tournaments between mooses, needed sub procedures as
 * calculating scores, current state of fields and manipulating
 * amount of grass on them.
 */
class Testing{

	static int fields[] = new int[3]; // fields state

	// Reset fields state to initial, with 1 on all fields
	public static void reset_fields(){
		for (int i = 0; i < 3;  i++) {
			fields[i] = 1;
		}
	}

	// decrease amount of grass on one field
	public static void decrease_grass(int field){
		fields[field] = fields[field] > 1 ? fields[field] - 1 : 0;
	}

	// print current state of fields on screen
	public static void fields_status(){
		System.out.printf("%d %d %d\n", fields[0], fields[1], fields[2]);
	}

	// print if there is fight between mooses
	public static void fight(){
		System.out.println("Fight");
	}

	// calculate gain of single moose for standind on field with state growth_state
	public static double gain(int growth_state){
		return (10 * Math.exp(growth_state)) / (1 + Math.exp(growth_state)) - 5;
	}

	// play one game between pl1 and pl2 with number_of_rounds of rounds and return their scores
	public static double[] play_game(int number_of_rounds, Player pl1, Player pl2){
		double scores[] = new double[2]; // scores for each player
		// init to zero
		scores[0] = 0; 
		scores[1] = 0;
		
		// init previous moves and current moves
		int prev_move1 = 0;
		int prev_move2 = 0;
		int move1;
		int move2;

		// prepare fields for battle
		reset_fields();

		// play number_of_rounds rounds
		for (int i = 0; i < number_of_rounds; i++) {
			// commented prints for nice looking and debugging
			// System.out.printf("------Round %d-----------\n", i+1);
			// make moves
			move1 = pl1.move(prev_move2, fields[0], fields[1], fields[2]);
			move2 = pl2.move(prev_move1, fields[0], fields[1], fields[2]);
			// System.out.printf("%d %d \n", move1, move2);
			// fields_status();
			// adjasting field according to mooses moves
			for (int y = 1; y < 4; y++){
				if (move1 == move2 && move1 == y){
					// fight();
					decrease_grass(y-1);
				} else if (move1 == y){
					scores[0] += gain(fields[y-1]);
					decrease_grass(y-1);
				} else if(move2 == y){
					scores[1] += gain(fields[y-1]);
					decrease_grass(y-1);
				} else {
					fields[y-1] += 1;
				}
			}
			// fields_status();
			// set current moves as previous for next round
			prev_move1 = move1;
			prev_move2 = move2;
		}
		// return scores of mooses
		return scores;
	}

	// method for plaing each vs each player from players array, with number_of_rounds rounds in each game
	public static double[] play_tournament(Player[] players, int number_of_rounds){
		// init how many players
		int number_of_players = players.length;
		// init scores array
		double[] scores = new double[number_of_players];
		for(int i = 0; i < number_of_players; i++){
			scores[i] = 0;
		}

		// play each vs each
		for (int i = 0; i < number_of_players-1; i++) {
			// System.out.printf("player %d playing\n", i);
			for (int y = i+1; y < number_of_players; y++) {
				// play game and adjast scores accordingly
				double[] game_score = play_game(number_of_rounds, players[i], players[y]);
				scores[i] += game_score[0];
				scores[y] += game_score[1];
				// reset players to next game
				players[i].reset();
				players[y].reset();
			}
		}
		// return scores
		return scores;
	}

	// find averages among various players types
	public static double[] find_averages(double[] scores, int nmbr_greedy, int nmbr_coop, int nmbr_random, int nmbr_seq, int overall){
		// if there error in input data, throw error
		if (nmbr_greedy+nmbr_coop+nmbr_random+nmbr_seq != overall){
			throw new AssertionError("Incorrrect number of players type, check again");
		} 
		// calculating averages 
		double aver_greed = 0;
		for(int i = 0; i < nmbr_greedy; i++){
			aver_greed += scores[i];
		}
		aver_greed = nmbr_greedy == 0 ? 0 : aver_greed / nmbr_greedy;
		double aver_coop = 0;
		for(int i = nmbr_greedy; i < nmbr_greedy+nmbr_coop; i++){
			aver_coop += scores[i];
		}
		aver_coop = nmbr_coop == 0 ? 0 : aver_coop / nmbr_coop;
		double aver_random = 0;
		for(int i = nmbr_greedy+nmbr_coop; i < nmbr_greedy+nmbr_coop+nmbr_random; i++){
			aver_random += scores[i];
		}
		aver_random = nmbr_random == 0 ? 0 : aver_random / nmbr_random;
		double aver_seq = 0;
		for(int i = nmbr_greedy+nmbr_coop+nmbr_random; i < nmbr_greedy+nmbr_coop+nmbr_random+nmbr_seq; i++){
			aver_seq += scores[i];
		}
		aver_seq = nmbr_seq == 0 ? 0 : aver_seq / nmbr_seq;

		// return averages
		return new double[] {aver_greed, aver_coop, aver_random, aver_seq};
	}

	// makes array of players with according number of each player type
	public static Player[] make_players(int nmbr_greedy, int nmbr_coop, int nmbr_random, int nmbr_seq, int overall){
		if (nmbr_greedy+nmbr_coop+nmbr_random+nmbr_seq != overall){
			throw new AssertionError("Incorrrect number of players type, check again");
		}
		Player[] pls = new Player[overall];
		for (int i = 0; i < nmbr_greedy; i++) {
			pls[i] = new Greedy();
		}
		for(int i = nmbr_greedy; i < nmbr_greedy + nmbr_coop; i++){
			pls[i] = new Coop();
		}
		for (int i = nmbr_greedy + nmbr_coop; i < nmbr_greedy + nmbr_coop + nmbr_random; i++) {
			pls[i] = new Dummy();
		}
		for(int i = nmbr_greedy + nmbr_coop + nmbr_random; i < nmbr_greedy + nmbr_coop + nmbr_random + nmbr_seq; i++){
			pls[i] = new Sequential();
		}
		return pls;
	}

	// find index of max element in array of doubles
	public static int index_of_max(double[] array){
		if ( array == null || array.length == 0 ){
			throw new AssertionError("Empty or null array to find max from");
		}; // null or empty
		int max_index = -1;
		double max = -1;
		for(int i = 0; i < array.length; i++){
			if(array[i] > max){
				max = array[i];
				max_index = i;
			}
		}
		return max_index;
	}

	// main function
	public static void main(String[] args) {
		int greedy = 0;
		int coop = 0;
		int random = 10;
		int seq = 10;
		Player[] pls = make_players(greedy, coop, random, seq, greedy+coop+random+seq);
		double[] scores = play_tournament(pls, 100);
		int max_el = index_of_max(scores);
		System.out.printf("Max score of %f for player %d\n", scores[max_el], max_el);
		double[] aver = find_averages(scores, greedy, coop, random, seq, greedy+coop+random+seq);
		System.out.printf("%f \n %f \n %f \n %f", aver[0], aver[1], aver[2], aver[3]);
		
	}
}