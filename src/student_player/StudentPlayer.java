package student_player;

import Saboteur.SaboteurMove;
import Saboteur.cardClasses.SaboteurCard;
import Saboteur.cardClasses.SaboteurMap;
import Saboteur.cardClasses.SaboteurTile;
import boardgame.Move;

import Saboteur.SaboteurPlayer;
import Saboteur.SaboteurBoardState;
import javafx.util.Pair;

import java.util.ArrayList;

/** A player file submitted by a student. */
public class StudentPlayer extends SaboteurPlayer {

    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260713804");
    }

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    public Move chooseMove(SaboteurBoardState boardState) {
        // You probably will make separate functions in MyTools.
        // For example, maybe you'll need to load some pre-processed best opening
        // strategies...
//        SaboteurBoardState bS_copy = (SaboteurBoardState) boardState.clone();
        ArrayList<SaboteurMove> legal_moves = boardState.getAllLegalMoves();
        SaboteurTile[][] currentTiles = boardState.getHiddenBoard();
        ArrayList<SaboteurCard> hand = boardState.getCurrentPlayerCards();
        int[][] val = boardState.getHiddenIntBoard();


        Pair<Integer,Integer> target1;
        target1 = MyTools.selectTarget(boardState);
        target1=  MyTools.setTargetWithNugget(boardState,target1);
        SaboteurMove mapMove = new SaboteurMove(new SaboteurMap(),target1.getKey(),target1.getValue(),player_id);
//        if(MyTools.doMapMove(boardState,mapMove)){
//            return mapMove;
//        }

//        int[][] arr = new int[][]{{0,0,0},{1,1,1},{0,0,0}};
        int[][] value = MyTools.convertBoard(val);
        boardState.printBoard();
//        return MyTools.A_star_search(boardState,target1,player_id);

         return MyTools.selectTile(boardState,target1,player_id);





//        System.out.println(val.toString());
//        MyTools.getSomething();

        // Is random the best you can do?
//        Move myMove = boardState.getRandomMove();

        // Return your move to be processed by the server.
//        return myMove;
    }
}