package com.zjjxgobang.swing.jframe;

import com.zjjxgobang.swing.listener.WindowsClosed;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FindGameFrame extends JFrame {
    JPanel jPanel = new JPanel();
    private int xOld = 0;
    private int yOld = 0;

    public FindGameFrame(String title) throws HeadlessException {
        super(title);
        jPanel.setSize(new Dimension(400, 300));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(jPanel);
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

                FindGameFrame.this.setLocation(xLo, yLo);
            }
        });

        jPanel.setLayout(new BorderLayout());
        WaitPanel waitPanel = new WaitPanel();
        waitPanel.setSize(new Dimension(400,300));
        jPanel.add(waitPanel, BorderLayout.CENTER);

    }

    private class WaitPanel extends JPanel{
        @Override
        protected void paintComponent(Graphics g) {
            try {
                BufferedImage img = ImageIO.read(new File("src/resource/gobang_wait.png"));
                int height = img.getHeight(this);
                int width = img.getWidth(this);

                Graphics2D g2d = (Graphics2D) g.create();
                g2d.drawImage(img,0,0,width,height,this);
                g2d.dispose();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
