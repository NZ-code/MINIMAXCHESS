import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SmartMiniMaxPlayer extends Player{
    public int depth;

    // Piece-Square Tables
    private int[] pawnPositionEval={
            0,  0,  0,  0,  0,  0,  0,  0,
            50, 50, 50, 50, 50, 50, 50, 50,
            10, 10, 20, 30, 30, 20, 10, 10,
            5,  5, 10, 25, 25, 10,  5,  5,
            0,  0,  0, 20, 20,  0,  0,  0,
            5, -5,-10,  0,  0,-10, -5,  5,
            5, 10, 10,-20,-20, 10, 10,  5,
            0,  0,  0,  0,  0,  0,  0,  0
    };
    private int[] knightPositionEval={
            -50,-40,-30,-30,-30,-30,-40,-50,
            -40,-20,  0,  0,  0,  0,-20,-40,
            -30,  0, 10, 15, 15, 10,  0,-30,
            -30,  5, 15, 20, 20, 15,  5,-30,
            -30,  0, 15, 20, 20, 15,  0,-30,
            -30,  5, 10, 15, 15, 10,  5,-30,
            -40,-20,  0,  5,  5,  0,-20,-40,
            -50,-40,-30,-30,-30,-30,-40,-50,
    };
    private int[] bishopPositionEval= {
            -20,-10,-10,-10,-10,-10,-10,-20,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -10,  0,  5, 10, 10,  5,  0,-10,
            -10,  5,  5, 10, 10,  5,  5,-10,
            -10,  0, 10, 10, 10, 10,  0,-10,
            -10, 10, 10, 10, 10, 10, 10,-10,
            -10,  5,  0,  0,  0,  0,  5,-10,
            -20,-10,-10,-10,-10,-10,-10,-20,
    };
    private int[] rookPositionEval={
            0,  0,  0,  0,  0,  0,  0,  0,
            5, 10, 10, 10, 10, 10, 10,  5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            0,  0,  0,  5,  5,  0,  0,  0
    };
    private int[] queenPositionEval= {
            -20,-10,-10, -5, -5,-10,-10,-20,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -10,  0,  5,  5,  5,  5,  0,-10,
            -5,  0,  5,  5,  5,  5,  0, -5,
            0,  0,  5,  5,  5,  5,  0, -5,
            -10,  5,  5,  5,  5,  5,  0,-10,
            -10,  0,  5,  0,  0,  0,  0,-10,
            -20,-10,-10, -5, -5,-10,-10,-20
    };
    private int[] kingPositionEval= { // middle game
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -20,-30,-30,-40,-40,-30,-30,-20,
            -10,-20,-20,-20,-20,-20,-20,-10,
            20, 20,  0,  0,  0,  0, 20, 20,
            20, 30, 10,  0,  0, 10, 30, 20
    };
    public SmartMiniMaxPlayer(GameLogic gameLogic, int depth){
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
                int posValWhite = 0;
                int posValBlack = 0;
                int y2;
                if(piece == null) continue;
                if(piece.getPieceType() == null) continue;
                switch (piece.getPieceType()){
                    case KING:
                        pieceVal = 20000;
                        posValBlack = kingPositionEval[x + y * 8];
                        y2 = 7-y;
                        posValWhite = kingPositionEval[x + y2*8];
                        break;
                    case QUEEN:
                        pieceVal = 900;
                        posValBlack = queenPositionEval[x + y * 8];
                        y2 = 7-y;
                        posValWhite = queenPositionEval[x + y2*8];
                        break;
                    case BISHOP:
                        pieceVal = 330;
                        posValBlack = bishopPositionEval[x + y * 8];
                        y2 = 7-y;
                        posValWhite = bishopPositionEval[x + y2*8];
                        break;
                    case KNIGHT:
                        pieceVal = 320;
                        posValBlack = knightPositionEval[x + y * 8];
                        y2 = 7-y;
                        posValWhite = knightPositionEval[x + y2*8];
                        break;
                    case ROOK:
                        pieceVal = 500;
                        posValBlack = rookPositionEval[x + y * 8];
                        y2 = 7-y;
                        posValWhite = rookPositionEval[x + y2*8];
                        break;
                    case PAWN:
                        pieceVal = 100;
                        posValBlack = pawnPositionEval[x + y * 8];
                        y2 = 7-y;
                        posValWhite = pawnPositionEval[x + y2*8];
                        break;
                    case NONE:
                        break;
                }
                if(piece.getPieceSide() == Side.WHITE){
                    whiteFigureVal += (pieceVal );
                    whiteFigureVal += posValWhite;

                }
                else{
                    blackFigureVal += pieceVal;
                    blackFigureVal += posValBlack;
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
