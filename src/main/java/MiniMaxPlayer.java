import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MiniMaxPlayer extends Player{
    public int depth;

    public MiniMaxPlayer(GameLogic gameLogic, int depth){
        super(gameLogic);
        this.depth = depth;
    }
    int getBoardVal(Board board){
        int hValue = 0;
        Piece pieces[] = board.boardToArray();
        int whiteFigureVal = 0;
        int blackFigureVal = 0;

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece piece = pieces[x + y * 8];
                int pieceVal = 0;
                if(piece == null) continue;
                if(piece.getPieceType() == null) continue;
                switch (piece.getPieceType()){
                    case KING:
                        pieceVal = 0;
                        break;
                    case QUEEN:
                        pieceVal = 90;
                        break;
                    case BISHOP:
                        pieceVal = 30;
                        break;
                    case KNIGHT:
                        pieceVal = 30;
                        break;
                    case ROOK:
                        pieceVal = 50;
                        break;
                    case PAWN:
                        pieceVal = 10;
                        break;
                    case NONE:
                        break;
                }
                if(piece.getPieceSide() == Side.WHITE){
                    whiteFigureVal += pieceVal;
                }
                else{
                    blackFigureVal += pieceVal;
                }
            }

        }
        hValue = whiteFigureVal - blackFigureVal;
        int mateVal = 100000;

        if(board.isMated()){
            if(board.getSideToMove() == Side.WHITE){ // black win
                // white win
                hValue -= mateVal;

            }
            else{
                hValue += mateVal;
            }
        }


        return hValue;
    }
    public MoveAndVal miniMax(Move move, Board board, int depth, int alpha, int beta){
        if(depth == 0 || board.isStaleMate() || board.isDraw() || board.isMated()) {

            return new MoveAndVal(move, getBoardVal(board));

        }
        Random random = new Random();
        if(board.getSideToMove() == Side.WHITE){ // maximazing player
            int maxVal = Integer.MIN_VALUE; // -inf
            List<Move> moves = board.legalMoves();
            Collections.shuffle(moves);
            Move maxMove = null;
            for(Move m : moves){
                Board newBoard = board.clone();
                newBoard.doMove(m);
                MoveAndVal moveAndVal = miniMax(m,newBoard, depth-1,alpha,beta);
                if(moveAndVal.val > maxVal){
                    maxVal = moveAndVal.val;
                    maxMove = m;
                }

                alpha = Math.max(alpha, moveAndVal.val);
                if(beta <= alpha){
                    break;
                }
            }
            return new MoveAndVal(maxMove,maxVal);
        }
        else{
                int minVal = Integer.MAX_VALUE; // -inf
                List<Move> moves = board.legalMoves();
                Collections.shuffle(moves);
                Move minMove = null;
                for (Move m : moves) {
                    Board newBoard = board.clone();
                    newBoard.doMove(m);
                    MoveAndVal moveAndVal = miniMax(m, newBoard, depth - 1, alpha, beta);
                    if (moveAndVal.val < minVal) {
                        minVal = moveAndVal.val;
                        minMove = m;
                    }

                    beta = Math.min(beta, moveAndVal.val);
                    if (beta <= alpha) {
                        break;
                    }
                }
                return new MoveAndVal(minMove, minVal);
        }
    }
    @Override
    public Move getMove() {
        Board board = gameLogic.getBoard();

        Move move = miniMax(null, board,depth, Integer.MIN_VALUE, Integer.MAX_VALUE).move;

        return move;
    }
}
