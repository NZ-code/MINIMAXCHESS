import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable{
    public MouseHandler mouseHandler;
    private int boardSize = 720;
    private GameLogic gameLogic;
    private int fieldSize = boardSize/8;
    private BufferedImage piecesImage;   // image that contains all pieces
    private Image[] imagesPiecesWhite; // white pieces  image array
    private Image[] imagesPiecesBlack; // black pieces  image array
    public GamePanel(GameLogic gameLogic){
        this.gameLogic = gameLogic;
        this.setPreferredSize(new Dimension(boardSize,boardSize)); // seting desk size
        this.setBackground(Color.decode("#fee4b9")); // setting desk color - white
        this.setDoubleBuffered(true); // Improve game rendering
        this.setFocusable(true); // for key handling
        this.setLayout(null);  // for proper sizing
        mouseHandler = new MouseHandler();
        this.addMouseListener(mouseHandler);
        // reading chess pieces from file
        try {
            piecesImage = ImageIO.read(new File("src/main/resources/images/ChessPiecesArray.png"));
            imagesPiecesWhite = new Image[6];
            imagesPiecesBlack= new Image[6];
            int pieceSize = 90;
            for (int i = 0; i< 6; i++){
                imagesPiecesWhite[i] = piecesImage.getSubimage(i*pieceSize,0,pieceSize,pieceSize);
                imagesPiecesBlack[i] = piecesImage.getSubimage(i*pieceSize,pieceSize,pieceSize,pieceSize);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // drawing desc
        g2.setColor(Color.decode("#613c00"));
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if((i+j) % 2 == 1){
                    g2.fillRect(i*fieldSize,j*fieldSize,fieldSize,fieldSize);
                }
            }
        }
        //  drawing figures
        Piece[] pieces = gameLogic.getPieces();

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++) {
                Piece piece=  pieces[i*8 + j];
                // piece type
                int ind = -1;
                if(piece.getPieceType() == null) continue;
                switch (piece.getPieceType()){
                    case KING:
                        ind = 0;
                        break;
                    case QUEEN:
                        ind = 1;
                        break;
                    case BISHOP:
                        ind = 2;
                        break;
                    case KNIGHT:
                        ind = 3;
                        break;
                    case ROOK:
                        ind = 4;
                        break;
                    case PAWN:
                        ind = 5;
                        break;
                    case NONE:
                        break;
                }
                if(ind == -1) continue; // empty field
                // white or black
                if(piece.getPieceSide() == Side.WHITE){
                    g2.drawImage(imagesPiecesWhite[ind],j*fieldSize,i*fieldSize, this);
                }
                else{
                    g2.drawImage(imagesPiecesBlack[ind],j*fieldSize,i*fieldSize, this);
                }

            }
        }

        g2.dispose();
    }

    @Override
    public void run() {
        while (true){
            repaint();
            if(mouseHandler.ismouseClick){
                int x = mouseHandler.x / fieldSize;
                int y = mouseHandler.y / fieldSize;
                gameLogic.xMouse = x;
                gameLogic.yMouse = y;
                gameLogic.mousePressed = true;

            }


        }

    }
}
