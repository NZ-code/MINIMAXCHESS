import com.github.bhlangonijr.chesslib.move.Move;

abstract public class Player {
    GameLogic gameLogic;
    public Player(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }
    public abstract Move getMove();

}
