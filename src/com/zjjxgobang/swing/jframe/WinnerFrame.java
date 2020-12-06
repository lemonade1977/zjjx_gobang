package com.zjjxgobang.swing.jframe;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WinnerFrame extends JFrame {

    public WinnerFrame(String title) throws HeadlessException {
        super(title);
        WinnerJPanel winnerJPanel = new WinnerJPanel();
        winnerJPanel.setSize(new Dimension(550,500));
        this.setContentPane(winnerJPanel);
    }

    private class WinnerJPanel extends JPanel{
        @Override
        protected void paintComponent(Graphics g) {
            try {
                BufferedImage img = ImageIO.read(new File("src/resource/winner.png"));
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
