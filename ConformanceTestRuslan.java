package gametheory.assignment2.students2021;

import gametheory.assignment2.students2021.*;
import gametheory.assignment2.Player;

import java.util.ArrayList;

public class ConformanceTestRuslan {

    private static Player providePlayer() {
        return new Coop();
    }

    public static void main(String[] args) {
        for (int i = 0; i <1000; i++) {
            testConformance();
            System.out.println();
        }

        System.out.println("Test successfully passed!");
    }

    private static void testConformance() {
        ArrayList<Integer> xs = new ArrayList<>();
        xs.add(-1000);
        xs.add(1);
        xs.add(1);
        xs.add(1); // fix this shit

        Player player = providePlayer();

        int lastMove = 0;

        // simulate collisions
        for (int i = 0; i < 6; i++) {
            lastMove = player.move(lastMove, xs.get(1), xs.get(2), xs.get(3));
            processGame(xs, lastMove, lastMove);
        }

        // players went to different cells
        int opponentPrimaryCell = lastMove % 3 + 1;
        int primaryCell = lastMove;
        int waitCell = thirdCell(primaryCell, opponentPrimaryCell);

        //transition into 2 stage
        lastMove = player.move(opponentPrimaryCell, xs.get(1), xs.get(2), xs.get(3));
        if (lastMove != waitCell) {
            throw new AssertionError("Player did not go to waiting position during the transition into 2nd stage.");
        }
        processGame(xs, lastMove, waitCell);
        System.out.println("1st stage(handshake) passed");

        // waiting until xs>6 on primary cells
        while (xs.get(primaryCell) < 6 || xs.get(opponentPrimaryCell) < 6) {
            lastMove = player.move(waitCell, xs.get(1), xs.get(2), xs.get(3));
            if (lastMove != waitCell && xs.get(primaryCell) < 6) {
                throw new AssertionError("Player stepped off waiting position in the 2nd stage");
            }
            processGame(xs, lastMove, waitCell);
        }
        System.out.println("2nd stage(waiting grow) passed");
        int crackerVolleyStep = 1;

        int rounds = 1000;
        for (int i = 0; i < rounds; i++) {
            if (i % 100 == 0) {
                System.out.println(i * 100. / rounds + "% complete");
            }
            if (crackerVolleyStep == 1) {
                lastMove = player.move(waitCell, xs.get(1), xs.get(2), xs.get(3));
                if (lastMove != primaryCell) {
                    throw new AssertionError("Player did not go to the primary position in 3.a stage");
                }
                processGame(xs, lastMove, opponentPrimaryCell);
                crackerVolleyStep = 2;
            } else {
                lastMove = player.move(opponentPrimaryCell, xs.get(1), xs.get(2), xs.get(3));
                if (lastMove != waitCell) {
                    throw new AssertionError("Player did not go to the waiting position in 3.b stage");
                }
                processGame(xs, lastMove, opponentPrimaryCell);
                crackerVolleyStep = 1;
            }
        }
        System.out.println("3rd stage(cracker volley) passed");
    }

    private static int thirdCell(int first, int second) {
        return 6 - (first + second);
    }

    private static void processGame(ArrayList<Integer> xs, int mainPlayerMove, int opponentMove) {
        for (int i = 1; i < 4; i++) {
            if (i != mainPlayerMove && i != opponentMove) {
                xs.set(i, xs.get(i) + 1);
            }
        }
        if (xs.get(mainPlayerMove) > 0) {
            xs.set(mainPlayerMove, xs.get(mainPlayerMove) - 1);
        }
        if (mainPlayerMove != opponentMove && xs.get(opponentMove) > 0) {
            xs.set(opponentMove, xs.get(opponentMove) - 1);
        }
    }


}
