package com.zjjxgobang.swing.jframe;

import com.zjjxgobang.swing.listener.FrameSetUndecorated;
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
        this.setResizable(false);
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jPanel.setSize(new Dimension(400, 300));
        this.setContentPane(jPanel);

        new FrameSetUndecorated(this).doSet();

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
