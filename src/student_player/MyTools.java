package student_player;

import Saboteur.SaboteurBoardState;
import Saboteur.SaboteurMove;
import Saboteur.cardClasses.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static void A_star_search(SaboteurBoardState board, Pair<Integer,Integer> target){
        int[] target_int = new int[]{target.getKey()*3+1,target.getValue()*3+1};
        int[][] intBoard = board.getHiddenIntBoard();






//37,16
    }



}