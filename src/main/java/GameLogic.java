import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;

public class GameLogic {
    public boolean mousePressed=false;
    public int xMouse = 0; // x field from mouse handler
    public int yMouse = 0; // y field from mouse handler
    private Board board;
    private Player[] players;
    private GameFrame gameFrame = null;

    public GameLogic(){
        board = new Board();
        players = new Player[2];
        players[0] = new RandomPlayer(this); // white
        players[1] = new MiniMaxPlayer(this,3); // black

    }

    public Piece[] getPieces(){

        return board.boardToArray();
    }
    public Board getBoard(){
        return board;
    }
    public void startGame(){
        int whiteWins = 0;
        int blackWins = 0;
        int draws = 0;
        while (true){
            board = new Board();
            boolean isDraw = false;
            boolean isMated = false;
            int moveId =0;
            while (!isDraw && !isMated){

                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Move move;
                if(board.getSideToMove() == Side.WHITE){
                    move = players[0].getMove();
                }
                else{
                    move = players[1].getMove();
                }



                board.doMove(move);

                isDraw = board.isDraw() || board.isStaleMate();
                isMated = board.isMated();


            }
            if(isMated == true){
                if(board.getSideToMove() == Side.WHITE){
                    blackWins++;

                }
                else{
                    whiteWins++;
                }
            }
            else{
                draws++;
            }
            int all = whiteWins + blackWins + draws;
            System.out.println("ALL GAMES:"+all +" WHITE:" + whiteWins +" BLACK:" + blackWins + " DRAWS:" + draws);

            try {
               Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void addFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;

    }
}
