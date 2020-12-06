package com.zjjxgobang.swing.jframe;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoserFrame extends JFrame {

    public LoserFrame(String title) throws HeadlessException {
        super(title);
        LoserPannel winnerJPanel = new LoserPannel();
        winnerJPanel.setSize(new Dimension(550,500));
        this.setContentPane(winnerJPanel);
    }

    private class LoserPannel extends JPanel{
        @Override
        protected void paintComponent(Graphics g) {
            try {
                BufferedImage img = ImageIO.read(new File("src/resource/loser.png"));
                int width = img.getWidth(this);
                int height = img.getHeight(this);

                Graphics2D g2d = (Graphics2D) g.create();
                g2d.drawImage(img,0,0,width,height,this);
                g2d.dispose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
