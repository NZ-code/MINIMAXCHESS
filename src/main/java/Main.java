public class Main {
    public static void main(String[] args) {
        System.out.println("Chess game");
        GameLogic gameLogic = new GameLogic();
        GameFrame gameFrame = new GameFrame(gameLogic);
        gameLogic.addFrame(gameFrame);
        gameLogic.startGame();
    }
}
