package com.zjjxgobang.swing.jframe;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoserFrame extends JFrame {

    private int xOld = 0;
    private int yOld = 0;

    public LoserFrame(String title) throws HeadlessException {
        super(title);
        LoserPannel winnerJPanel = new LoserPannel();
        winnerJPanel.setSize(new Dimension(550,500));
        this.setContentPane(winnerJPanel);
        this.setUndecorated(true);
        // 以下鼠标监听为实现窗口移动
        this.addMouseListener(new MouseAdapter() {
                                  public void mousePressed(MouseEvent e) {
                                      xOld = e.getX();
                                      yOld = e.getY();
                                  }
                              }
        );
        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int xOnScreen = e.getXOnScreen();
                int yOnScreen = e.getYOnScreen();

                int xLo = xOnScreen - xOld;
                int yLo = yOnScreen - yOld;

                LoserFrame.this.setLocation(xLo, yLo);
            }
        });
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
