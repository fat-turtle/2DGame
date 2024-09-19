package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3; //tile size * scale 16 -> 48
    public final int tileSize = originalTileSize * scale; // 48x48
    final int maxScreenCol = 16; // 16 tiles horizontal
    final int maxScreenRow = 12; // 12 tiles vertical
    final int screenWidth = tileSize * maxScreenCol; //768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    //FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this,keyH);

// set players default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.darkGray);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    //        double drawInterval = 1000000000/FPS; //0.01666 seconds
//        double nextDrawTime = System.nanoTime() + drawInterval;
//
//        while(gameThread != null){
//
//
//            //System.out.println("The game is running");
//            // 1 update: update info such as character position
//            update();
//
//            // 2 draw: draw the screen with updated information
//            repaint();
//            double remainingTime = nextDrawTime - System.nanoTime();
//            remainingTime = remainingTime/1000000;
//
//            if (remainingTime <0){
//                remainingTime = 0;
//            }
//
//            try {
//                Thread.sleep((long) remainingTime);
//
//                nextDrawTime += drawInterval;
//
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//        }
//

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer +=(currentTime-lastTime);
            lastTime = currentTime;

            if(delta >=1){
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000){
                System.out.println("FPS: "+drawCount);
                drawCount = 0;
                timer = 0;
            }


        }

    }
    public void update(){
       player.update();

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        player.draw(g2);
        g2.dispose();
    }
}