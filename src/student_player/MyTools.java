package student_player;

import Saboteur.SaboteurBoardState;
import Saboteur.SaboteurMove;
import Saboteur.cardClasses.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MyTools {
    public static double getSomething() {
        return Math.random();
    }

    public static Pair<Integer, Integer> selectTarget(SaboteurBoardState board){
        Pair<Integer,Integer> target1 = new Pair<Integer,Integer>(12,5);
        SaboteurTile[][] currentTiles = board.getHiddenBoard();
        if(currentTiles[target1.getKey()][target1.getValue()].getIdx().equals("8")){
            return target1;
        }
        if(currentTiles[target1.getKey()][target1.getValue()].getIdx().equals("hidden1")){
            target1 = new Pair<Integer,Integer>(12,3);
            return target1;
        }
        if(currentTiles[target1.getKey()][target1.getValue()].getIdx().equals("hidden2")){
            target1 = new Pair<Integer,Integer>(12,7);
            return target1;
        }
        if(currentTiles[target1.getKey()][target1.getValue()].getIdx().equals("nugget")){
            return target1;
        }
        return target1;
    }

    public static Pair<Integer, Integer> setTargetWithNugget(SaboteurBoardState board, Pair<Integer,Integer> target){
        SaboteurTile[][] x = board.getHiddenBoard();
        for(int i = 0; i<13; i++){
            for(int j = 0 ; j<13;j++){
               if(x[i][j]!= null){
                   String val = x[i][j].getIdx();

                   if(x[i][j].getIdx().equals("nugget")){
                       target =new Pair<Integer,Integer>(i,j);
                   }
               }





            }
        }
        return target;

    }

    public static boolean doMapMove(SaboteurBoardState board,  SaboteurMove mapMove){
        ArrayList<SaboteurCard> hand = board.getCurrentPlayerCards();
        for(SaboteurCard card : hand){
            if (card instanceof SaboteurMap){
//                board.processMove(mapMove);
                return true;
            }
        }
        return false;
    }
    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


    public static SaboteurMove dropUnusedCard(SaboteurBoardState board, int player_id){
        ArrayList<String> droppable = new ArrayList<String>(Arrays.asList("Tile:1","Tile:2","Tile:3","Tile:4","Tile:2_flip",
                "Tile:3_flip","Tile:4_flip","Tile:11","Tile:11_flip","Tile:12","Tile:12_flip","Tile:13",
                "Tile:14","Tile:14_flip","Tile:15","Tile:0","Tile:9","Tile:9_flip","Tile:6","Tile:6_flip","Tile:5","Tile:5_flip","Tile:7","Tile:7_flip","Tile:10"));
        int index = getRandomNumberInRange(0,droppable.size()-1);
        ArrayList<SaboteurCard> hand = board.getCurrentPlayerCards();
        for(SaboteurCard card : hand){
            String card_str = card.getName();
            boolean val = droppable.contains(card_str);
            if (card instanceof SaboteurDestroy){
//                board.processMove(mapMove);
                val = true;
            }
            if (card instanceof SaboteurMalus){
//
                val = true;
            }
            if (card instanceof SaboteurBonus){
//                board.processMove(mapMove);
                val = true;
            }
            if (card instanceof SaboteurMap){
//                board.processMove(mapMove);
                val = true;
            }
            if(val){
                SaboteurMove move = new SaboteurMove(new SaboteurDrop(),hand.indexOf(card),0,player_id);
                return move;
            }

        }
        return null;
    }

    private static double calcDistance(int[] pos, Pair<Integer,Integer> target){
//            double distance = Math.abs(target.getValue()-pos[1]) + Math.abs(target.getKey()-pos[0]);
            double distance =  Math.sqrt((target.getValue() - pos[1]) * (target.getValue() - pos[1]) + (target.getKey() - pos[0]) * (target.getKey() - pos[0]));
            return distance;

    }

    public static SaboteurMove chooseTile(SaboteurMove leg_move, SaboteurBoardState board,Pair<Integer,Integer> target, ArrayList<SaboteurCard> hand,int player_id){

        if(leg_move == null){
            System.out.println("GOTVEREN");
        }
        int[] pos = leg_move.getPosPlayed();

        for(SaboteurCard card : hand) {
            boolean x = (card  == leg_move.getCardPlayed());
            boolean y = card.getName().contains("8");
            if (card.getName().contains(leg_move.getCardPlayed().getName())&& card.getName().contains("8")) {
                SaboteurMove move = new SaboteurMove(new SaboteurTile("8"), pos[0], pos[1], player_id);
                if(move == null){
                    System.out.println("GOTVEREN");
                }
                return move;
            }
        }
//        for(SaboteurCard card : hand) {
//            boolean x = (card  == leg_move.getCardPlayed());
//            boolean y = card.getName().contains("0");
//            if (card.getName().contains(leg_move.getCardPlayed().getName()) && card.getName().contains("0")) {
//                SaboteurMove move = new SaboteurMove(new SaboteurTile("0"), pos[0], pos[1], player_id);
//                if(move == null){
//                    System.out.println("GOTVEREN");
//                }
//                return move;
//            }
//        }
//        for(SaboteurCard card : hand) {
//            if (card.getName().contains(leg_move.getCardPlayed().getName()) && card.getName().contains("10")) {
//                SaboteurMove move = new SaboteurMove(new SaboteurTile("10"), pos[0], pos[1], player_id);
//                if(move == null){
//                    System.out.println("GOTVEREN");
//                }
//                return move;
//            }
//        }
        SaboteurMove movee = dropUnusedCard(board,player_id);
        if( movee == null){
            dropUnusedCard(board,player_id);
//            return board.getRandomMove();
        }
        return movee;
    }

    public static SaboteurMove selectTile(SaboteurBoardState board,Pair<Integer,Integer> target,int player_id){
        ArrayList<SaboteurMove> legal_moves = board.getAllLegalMoves();

        legal_moves.get(0).getPosPlayed();
        double prev_distance = 9999999;
        ArrayList<SaboteurCard> hand = board.getCurrentPlayerCards();
        SaboteurMove currentMove = null;
        for(int i = 0 ;i <legal_moves.size(); i++){
            int[] pos = legal_moves.get(i).getPosPlayed();
            double distance = calcDistance(pos,target);

            if(distance < prev_distance){
                currentMove = chooseTile(legal_moves.get(i),board,target,hand,player_id);
//                assert currentMove != null;
                prev_distance = distance;
            }
        }
        if(currentMove == null){
            System.out.println("GOTVEREN");
        }
        return currentMove;
    }

    public static int[][] convertBoard(int [][]intBoard){
        int[][] copyBoard = new int[intBoard.length][intBoard[0].length];
        for(int i=0; i<intBoard.length;i++){
            for(int j =0; j<intBoard[i].length;j++){
                if(intBoard[i][j] == -1 ){
                    copyBoard[i][j] = 1;

                }
                else if(intBoard[i][j] == 1){
                    copyBoard[i][j] = 0;
                }
                else if(intBoard[i][j] == 0){
                    copyBoard[i][j] = -1;
                }
            }
        }

        for(int i=0; i<intBoard.length;i=i+2){
            for(int j =0; j<intBoard[i].length;j=j+2){
                if(copyBoard[i][j] == 1 ){
                    copyBoard[i][j] = -1;

                }

            }
        }
        return copyBoard;
    }

    public static SaboteurMove doMove(SaboteurBoardState board,Pair<Integer,Integer> target,int player_id){
        ArrayList<SaboteurMove> legal_moves = board.getAllLegalMoves();
        ArrayList<SaboteurCard> hand = board.getCurrentPlayerCards();

        ArrayList<SaboteurMove> listOfMoves = A_star_search(board,target,player_id);

        for(int i = 0 ;i <listOfMoves.size(); i++) {
            for(int j = 0; j<legal_moves.size();j++) {
                if(legal_moves.get(j).getCardPlayed().getName().equals(listOfMoves.get(i).getCardPlayed().getName())){

                    return listOfMoves.get(i);
                }
            }
        }
        return dropUnusedCard(board,player_id);
    }

    public static ArrayList<SaboteurMove> A_star_search(SaboteurBoardState board, Pair<Integer,Integer> target,int player_id){
        int[] target_int = new int[]{target.getKey()*3+1,target.getValue()*3+1};
        int[][] intBoard = board.getHiddenIntBoard();
//        ArrayList<SaboteurMove> legal_moves = board.getAllLegalMoves();
//        ArrayList<SaboteurCard> hand = board.getCurrentPlayerCards();
        AStar as = new AStar(intBoard, 16, 16, false);

        List<AStar.Node> new_path = as.findPathTo(target_int[0], target_int[1]);
        new_path.remove(0);
        new_path.remove(0);
        new_path.remove(new_path.size()-1);
        new_path.remove(new_path.size()-1);

        ArrayList<SaboteurMove> moves= new ArrayList<SaboteurMove>();

        for(int i = 0 ; i<new_path.size();i=i+3) {
            AStar.Node first = new_path.get(i);
            AStar.Node second = new_path.get(i + 1);
            AStar.Node third = new_path.get(i + 2);

            if (first.x == second.x && second.x == third.x) {
                SaboteurMove move = new SaboteurMove(new SaboteurTile("0"), second.x, second.y, player_id);
                moves.add(move);
            }
            if(first.y == second.y && second.y == third.y) {
                SaboteurMove move = new SaboteurMove(new SaboteurTile("10"), second.x, second.y, player_id);
                moves.add(move);
            }
            if((first.x == second.x - 1 && third.y == second.y + 1) ||
                    (first.y == second.y + 1 && third.x == second.x - 1)) {
                SaboteurMove move = new SaboteurMove(new SaboteurTile("5_flip"), second.x, second.y, player_id);
                moves.add(move);
            }
            if((first.x == second.x + 1 && second.y == third.y - 1) ||
                    (first.y == second.y + 1 && second.x == third.x - 1)) {
                SaboteurMove move = new SaboteurMove(new SaboteurTile("7"), second.x, second.y, player_id);
                moves.add(move);
            }
            if((first.x == second.x - 1 && second.y == third.y + 1) ||
                    (first.y == second.y - 1 && second.x == third.x + 1)) {
                SaboteurMove move = new SaboteurMove(new SaboteurTile("7_flip"), second.x, second.y, player_id);
                moves.add(move);
            }
            if((first.x == second.x + 1 && second.y == third.y + 1) ||
                    (first.y == second.y - 1 && second.x == third.x - 1)) {
                SaboteurMove move = new SaboteurMove(new SaboteurTile("5"), second.x, second.y, player_id);
                moves.add(move);
            }

        }
        return moves;
    }
}