package student_player;

import Saboteur.SaboteurBoard;
import Saboteur.SaboteurBoardState;
import Saboteur.SaboteurMove;
import Saboteur.cardClasses.*;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MyTools {
    public static double getSomething() {
        return Math.random();
    }

//    public static Pair<Integer, Integer> selectTarget(SaboteurBoardState board){
//        Pair<Integer,Integer> target1 = new Pair<Integer,Integer>(12,5);
//        SaboteurTile[][] currentTiles = board.getHiddenBoard();
//        if(currentTiles[target1.getKey()][target1.getValue()].getIdx().contains("8")){
//            return target1;
//        }
//        if(currentTiles[target1.getKey()][target1.getValue()].getIdx().contains("hidden")){
//            target1 = new Pair<Integer,Integer>(12,3);
//            return target1;
//        }
//        if(currentTiles[target1.getKey()][target1.getValue()].getIdx().contains("hidden")){
//            target1 = new Pair<Integer,Integer>(12,7);
//            return target1;
//        }
//        if(currentTiles[target1.getKey()][target1.getValue()].getIdx().equals("nugget")){
//            return target1;
//        }
//        return target1;
//    }

    public static Pair<Integer, Integer> selectTarget(SaboteurBoardState board){
        Pair<Integer,Integer> target1 = new Pair<Integer,Integer>(12,5);
        SaboteurTile[][] currentTiles = board.getHiddenBoard();
        if(currentTiles[12][5].getIdx().contains("8")){
            return target1;
        }
        if(currentTiles[12][3].getIdx().contains("8")){
            target1 = new Pair<>(12, 3);
            return target1;
        }
        if(currentTiles[12][7].getIdx().contains("8")){
            target1 = new Pair<>(12, 7);
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

                   if(x[i][j].getIdx().contains("nugget")){
                       target =new Pair<Integer,Integer>(i,j);
                   }
               }



            }
        }
        return target;

    }

    public static boolean nuggetExists(SaboteurBoardState board){
        SaboteurTile[][] x = board.getHiddenBoard();
        for(int i = 0; i<13; i++){
            for(int j = 0 ; j<13;j++){
                if(x[i][j]!= null){


                    if(x[i][j].getIdx().contains("nugget")){
                        return true;
                    }
                }



            }
        }
        return false;

    }

    public static boolean doMapMove(SaboteurBoardState board,  SaboteurMove mapMove){
        ArrayList<SaboteurCard> hand = board.getCurrentPlayerCards();
        for(SaboteurCard card : hand){
            if (card instanceof SaboteurMap){
//                board.processMove(mapMove);
                SaboteurTile[][] x = board.getHiddenBoard();
                if(nuggetExists(board)){
                    return false;
                }

                return true;
            }
        }
        return false;
    }


    public static SaboteurMove useDestroy(SaboteurBoardState board, int player_id){
        ArrayList<String> droppable = new ArrayList<String>(Arrays.asList("Tile:1","Tile:2","Tile:3","Tile:4","Tile:2_flip",
                "Tile:3_flip","Tile:4_flip","Tile:11","Tile:11_flip","Tile:12","Tile:12_flip","Tile:13",
                "Tile:14","Tile:14_flip","Tile:15"));
        SaboteurTile[][] board_val = board.getHiddenBoard();
        for(int i = 0; i<board_val.length; i++){
            for(int j = 0 ; j<board_val[0].length;j++){
                if(board_val[i][j]!= null){
                   if(droppable.contains(board_val[i][j].getName())){
                       SaboteurMove destroy = new SaboteurMove(new SaboteurDestroy(),i,j,player_id);
                       return destroy;
                   }
                }



            }
        }
        return null;
    }

    public static boolean multipleBonuses(SaboteurBoardState board){
        ArrayList<SaboteurCard> hand = board.getCurrentPlayerCards();
        int count = 0;
        for(SaboteurCard card : hand) {
            if (card instanceof SaboteurBonus){
                count++;
            }
        }
        if(count>1){
            return true;
        }
        return false;
    }

    private static boolean hasMultipleOfCard(SaboteurBoardState board, SaboteurCard pCard) {
        ArrayList<SaboteurCard> playerHand = board.getCurrentPlayerCards();
        int count = 0;
        for (SaboteurCard aCard : playerHand) {
            if (aCard.getName().equals(pCard.getName())) {
                count++;
            }
        }
        return count > 1;
    }

    private static SaboteurMove dropDuplicates(SaboteurBoardState board, int playerID) {
        ArrayList<String> dropDuplicate = new ArrayList<String>(Arrays.asList(
                "Bonus", "Tile:1","Tile:2","Tile:3","Tile:4","Tile:2_flip",
                "Tile:3_flip","Tile:4_flip","Tile:11","Tile:11_flip","Tile:12","Tile:12_flip","Tile:13",
                "Tile:14","Tile:14_flip","Tile:15", "Tile:10", "Tile:5_flip", "Tile:5", "Tile:7", "Tile:7_flip",
                "Tile:9", "Tile:9_flip"));

        ArrayList<SaboteurCard> playerHand = board.getCurrentPlayerCards();

        for (SaboteurCard card : playerHand) {
            boolean isDroppable = dropDuplicate.contains(card.getName());
            SaboteurMove dropMove = new SaboteurMove(new SaboteurDrop(), playerHand.indexOf(card), 0, playerID);

            if (isDroppable && hasMultipleOfCard(board, card)) {
                return dropMove;
            }
        }
        return null;
    }

    public static SaboteurMove dropUnusedCard(SaboteurBoardState board, int player_id){
        ArrayList<String> droppable = new ArrayList<String>(Arrays.asList("Tile:1","Tile:2","Tile:3","Tile:4","Tile:2_flip",
                "Tile:3_flip","Tile:4_flip","Tile:11","Tile:11_flip","Tile:12","Tile:12_flip","Tile:13",
                "Tile:14","Tile:14_flip","Tile:15"));

        ArrayList<SaboteurCard> hand = board.getCurrentPlayerCards();

        for (SaboteurCard card : hand) {
            boolean isDroppable = droppable.contains(card.getName());
            SaboteurMove dropMove = new SaboteurMove(new SaboteurDrop(), hand.indexOf(card), 0, player_id);

            if (card instanceof SaboteurMap && nuggetExists(board)) {
                return dropMove;
            }

            if (isDroppable) {
                return dropMove;
            }
        }
        return null;

//        for(SaboteurCard card : hand){
//            String card_str = card.getName();
//            boolean val = droppable.contains(card_str);
////            if (card instanceof SaboteurDestroy){
////
////                val = true;
////            }
//            if (multipleBonuses(board)){
////
//                val = true;
//            }
//            if (hasMultipleOfCard(board, card)) {
//                val = true;
//            }
//
////            if (card instanceof SaboteurBonus){
//////                board.processMove(mapMove);
////                val = true;
////            }
//            if (card instanceof SaboteurMap){
////                board.processMove(mapMove);
//                if(nuggetExists(board)){
//                    val = true;
//                }
//
//            }
//            if(val){
//                SaboteurMove move = new SaboteurMove(new SaboteurDrop(),hand.indexOf(card),0,player_id);
//                return move;
//            }
//
//        }
//        return null;
    }

    private static double calcDistance(int[] pos, Pair<Integer,Integer> target){
//            double distance = Math.abs(target.getValue()-pos[1]) + Math.abs(target.getKey()-pos[0]);
            double distance =  Math.sqrt((target.getValue() - pos[1]) * (target.getValue() - pos[1]) + (target.getKey() - pos[0]) * (target.getKey() - pos[0]));
            return distance;

    }



    public static int[][] convertBoard(int[][] intBoard){
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
            if(copyBoard[i][0] == 1){
                copyBoard[i][0] = -1;
            }

            for(int j =2; j+1<intBoard[i].length;){
                if(copyBoard[i][j] == 1 ){
                    copyBoard[i][j] = -1;

                }
                if(j+1<intBoard[i].length){
                    if(copyBoard[i][j+1] == 1){
                        copyBoard[i][j+1] = -1;
                    }
                }
                j=j+3;

            }
            if(copyBoard[i][intBoard[i].length-1] == 1){
                copyBoard[i][intBoard[i].length-1] =-1;
            }
        }
        return copyBoard;
    }



    public static SaboteurMove doMove(SaboteurBoardState board, Pair<Integer, Integer> target, int player_id){
        ArrayList<SaboteurMove> legal_moves = board.getAllLegalMoves();
        ArrayList<SaboteurMove> listOfMoves = A_star_search(board,target,player_id);

        System.out.println("PAAAAATHHH     " + listOfMoves.toString());
        for(SaboteurMove move: listOfMoves){
            System.out.println("MOVEEESSS     " + move.toPrettyString());
        }
        for(SaboteurMove move: legal_moves){
            System.out.println("Legal_Moves     " + move.toPrettyString());
        }

        for(int i = listOfMoves.size()-1 ;i >=0; i=i-1) {
            for(int j = 0; j < legal_moves.size(); j++){
                SaboteurMove malus = new SaboteurMove(new SaboteurMalus(), 0 ,0, player_id);
                if(malus.toPrettyString().equals(legal_moves.get(j).toPrettyString())){
                    return malus;
                }
                if(legal_moves.get(j).toPrettyString().equals(listOfMoves.get(i).toPrettyString())) {
                    return listOfMoves.get(i);
                }
                if(board.getNbMalus(player_id)==1){
                    SaboteurMove bonus = new SaboteurMove(new SaboteurBonus(), 0 ,0, player_id);
                    if(bonus.toPrettyString().equals(legal_moves.get(j).toPrettyString())){
                        return bonus;
                    }
                }
                SaboteurMove destroy = useDestroy(board,player_id);
                if(destroy != null) {
                    if (destroy.toPrettyString().equals(legal_moves.get(j).toPrettyString())) {
                        return destroy;
                    }
                }
            }
        }

        SaboteurMove dropCard = dropUnusedCard(board, player_id);
        if (dropCard != null) {
            return dropCard;
        }

        return dropDuplicates(board, player_id);
    }

    private static ArrayList<SaboteurMove> A_star_search(SaboteurBoardState board, Pair<Integer,Integer> target,int player_id){
        int[] target_int = new int[]{target.getKey()*3+1,target.getValue()*3+1};
        int[][] intBoard = convertBoard(board.getHiddenIntBoard());

        AStar as = new AStar(intBoard, 16, 16, false);

        List<AStar.Node> new_path = as.findPathTo(target_int[1], target_int[0]);

        new_path.remove(0);
        new_path.remove(0);
        new_path.remove(new_path.size()-1);
        new_path.remove(new_path.size()-1);

        ArrayList<SaboteurMove> moves= new ArrayList<SaboteurMove>();

        for(int i = 0 ; i<new_path.size();i=i+3) {
            if(i+2<new_path.size()){
                AStar.Node first = new_path.get(i);
                AStar.Node second = new_path.get(i + 1);
                AStar.Node third = new_path.get(i + 2);

                SaboteurMove move_8 = new SaboteurMove(new SaboteurTile("8"), (second.y-1)/3, (second.x-1)/3, player_id);
                SaboteurMove move_6 = new SaboteurMove(new SaboteurTile("6"), (second.y-1)/3, (second.x-1)/3, player_id);
                SaboteurMove move_6_flip = new SaboteurMove(new SaboteurTile("6_flip"), (second.y-1)/3, (second.x-1)/3, player_id);
                SaboteurMove move_10 = new SaboteurMove(new SaboteurTile("10"), (second.y-1)/3, (second.x-1)/3, player_id);
                SaboteurMove move_0 = new SaboteurMove(new SaboteurTile("0"), (second.y-1)/3, (second.x-1)/3, player_id);
                SaboteurMove move_9 = new SaboteurMove(new SaboteurTile("9"), (second.y-1)/3, (second.x-1)/3, player_id);
                SaboteurMove move_9_flip = new SaboteurMove(new SaboteurTile("9_flip"), (second.y-1)/3, (second.x-1)/3, player_id);
                SaboteurMove move_5_flip = new SaboteurMove(new SaboteurTile("5_flip"), (second.x-1)/3, (second.y-1)/3, player_id);
                SaboteurMove move_7 = new SaboteurMove(new SaboteurTile("7"), (second.y-1)/3, (second.x-1)/3, player_id);
                SaboteurMove move_7_flip = new SaboteurMove(new SaboteurTile("7_flip"), (second.y-1)/3, (second.x-1)/3, player_id);
                SaboteurMove move_5 = new SaboteurMove(new SaboteurTile("5"), (second.y-1)/3, (second.x-1)/3, player_id);

                if (first.x == second.x && second.x == third.x) {

                    moves.add(move_0);
                    moves.add(move_8);
                    moves.add(move_6);
                    moves.add(move_6_flip);
                }
                else if(first.y == second.y && second.y == third.y) {

                    moves.add(move_10);
                    moves.add(move_8);
                    moves.add(move_9);
                    moves.add(move_9_flip);
                }
                else if((first.x == second.x - 1 && third.y == second.y - 1 && third.x == second.x && first.y == second.y) ||
                        (first.y == second.y - 1 && third.x == second.x - 1 && third.y ==second.y && first.x == second.x)) {

                    moves.add(move_5_flip);
                    moves.add(move_8);
                    moves.add(move_6);
                    moves.add(move_9);

                }
                else if((first.x == second.x + 1 && second.y == third.y + 1 && first.y==second.y && second.x ==third.x) ||
                        (first.y == second.y - 1 && second.x == third.x - 1 && first.x==second.x && second.y ==third.y)) {

                    moves.add(move_8);
                    moves.add(move_7);
                    moves.add(move_6_flip);
                    moves.add(move_9_flip);

                }
                else if((first.x == second.x - 1 && second.y == third.y - 1 && first.y==second.y && second.x==third.x) ||
                        (first.y == second.y + 1 && second.x == third.x + 1 && first.x ==second.x && second.y==third.y)) {

                    moves.add(move_9);
                    moves.add(move_6);
                    moves.add(move_8);
                    moves.add(move_7_flip);
                }
                else if((first.x == second.x + 1 && second.y == third.y - 1 && first.y==second.y && second.x==third.x) ||
                        (first.y == second.y + 1 && second.x == third.x - 1 && first.x==second.x && second.y==third.y)) {

                    moves.add(move_8);
                    moves.add(move_6_flip);
                    moves.add(move_5);
                    moves.add(move_9);
                }
            }
        }
        return moves;
    }
}