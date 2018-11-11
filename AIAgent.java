import java.util.*;

public class AIAgent {
    Random rand;

    public AIAgent() {
        rand = new Random();
    }

/*
  The method randomMove takes as input a stack of potential moves that the AI agent
  can make. The agent uses a rondom number generator to randomly select a move from
  the inputted Stack and returns this to the calling agent.
*/

    public Move randomMove(Stack possibilities) {

        int moveID = rand.nextInt(possibilities.size());
        System.out.println("Agent randomly selected move : " + moveID);
        for (int i = 1; i < (possibilities.size() - (moveID)); i++) {
            possibilities.pop();
        }
        Move selectedMove = (Move) possibilities.pop();
        return selectedMove;
    }

    /*
      Pawn: 1
      Knight/Bishop : 3
      Rook: 5
      Queen: 9
      King worth game

      Get all possible moves like random agent then apply a utility function to workout which move to make
     */
    public Move nextBestMove(Stack possibilities, List pieceName) {
        return evaluateMove(possibilities, pieceName);
    }

    /*
        Extends function of nextBestMove
        Tries to determine what the player is going to do
        Min max approach

        Capture potential movements of the player pieces exactly like the white pieces
        Once we have the movements, we need a utility function to calculate the value of the movements and estimate which movement the player will make
        and then the AI agent responds to this movement.


        Random --> get all possible movements for white
                --> select a random move

        nextBestMove --> get all possible movements for white
                        --> create utility function based on current move, this could be if we take a piece we score points
                        --> loop through stack of movements and check if we are taking a piece, if so make this move

        tooLevelsDeep --> get all possible movements for white
                     for each of these movements we find the best possible response for the player
                        --> get all possible movements for black after the board changes for each of the movements for white
                        --> rank these according to a utility function
                        --> AI agent makes best possible move that it can make with the least best response from the player

     */
    public Move twoLevelsDeep(Stack possibilities, List pieceName) {
        Move selectedMove = evaluateMove(possibilities, pieceName);
        return selectedMove;
    }

    /*
        Takes in a Stack of possible moves and a List of the landing square pieceNames
        For each possibility it checks which piece will be taken then adds the score of that piece to a list
        We then get the max score from that List and return that move to make
     */

    public Move evaluateMove(Stack possibilities, List pieceName){
        List score = new ArrayList();
        Move selectedMove = new Move();
        for (int i = 0; i < possibilities.size(); i++) {
            String piece = pieceName.get(i).toString();
            if (piece.contains("King")) {
                score.add(999);
            } else if (piece.contains("Queen")) {
                score.add(9);
            } else if (piece.contains("Rook")) {
                score.add(5);
            } else if (piece.contains("Knight") || piece.contains("Bishop")) {
                score.add(3);
            } else if (piece.contains("Pawn")) {
                score.add(1);
            } else if (piece.contains("empty")){
                score.add(0);
            }
        }
        Comparable bestScore = Collections.max(score);
        for(int i=0; i<score.size(); i++){
            if(score.get(i) == bestScore){
                selectedMove = (Move) possibilities.get(i);
            }
        }
        return selectedMove;
    }
}
