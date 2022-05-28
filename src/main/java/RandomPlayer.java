import com.github.bhlangonijr.chesslib.move.Move;

import java.util.List;
import java.util.Random;

public class RandomPlayer extends Player{

    public RandomPlayer(GameLogic gameLogic){
        super(gameLogic);
    }
    @Override
    public Move getMove() {
        List<Move> moves = gameLogic.getBoard().legalMoves();
        Random random = new Random();
        int moveId = random.nextInt(moves.size());
        if(moveId < 0){
            System.out.println();
        }
        return moves.get(moveId);
    }
}
