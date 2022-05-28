import javax.swing.*;

public class GameFrame extends JFrame {
    private int width = 720;
    private int height = 720;
    private GamePanel gamePanel;

    public GameFrame(GameLogic gameLogic){
        this.setSize(width,height); // width of app screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close when click x in game frame
        this.setResizable(false); // cannot resize game screen
        this.setTitle("Chess"); // will be seen on top of frame
        this.setLocationRelativeTo(null); // putting frame in the center
        this.setVisible(true); // frame is visible
        gamePanel = new GamePanel(gameLogic);
        Thread gamePanelThread = new Thread(gamePanel);
        gamePanelThread.start();
        this.add(gamePanel);

        this.pack(); // pack components of frame >= preferable size
    }

    public void drawGame(){
        gamePanel.repaint();
    }
}
