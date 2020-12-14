package com.zjjxgobang.swing.jframe;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UserFrame extends JFrame {


    public UserFrame(String title) throws HeadlessException {
        super(title);
        this.setSize(new Dimension(600,500));
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container rootPanel = this.getContentPane();

        rootPanel.setSize(new Dimension(600,500));
        rootPanel.setLayout(new BorderLayout());

            JPanel centerPanel = new UserJPanel();
            centerPanel.setLayout(new BorderLayout());
            centerPanel.setSize(new Dimension(600,200));


                JButton loginButton = new JButton("登录");
                loginButton.setSize(new Dimension(80,60));
                JButton registerButton = new JButton("注册");
                registerButton.setSize(new Dimension(80,60));

            Box verticalBox = Box.createVerticalBox();

                Box horizontalBox = Box.createHorizontalBox();
                Component centerSplit = Box.createHorizontalStrut(40);
                horizontalBox.add(loginButton);
                horizontalBox.add(centerSplit);
                horizontalBox.add(registerButton);

            Component topMargin = Box.createVerticalStrut(200);
            verticalBox.add(topMargin);
            verticalBox.add(horizontalBox);

        centerPanel.add(verticalBox,BorderLayout.CENTER);

        rootPanel.add(centerPanel,BorderLayout.CENTER);

        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createLoginFrame();
            }
        });

        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createRegisterFrame();
            }
        });

    }

    public void createLoginFrame(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginFrame loginFrame = new LoginFrame("用户登录");
                loginFrame.setVisible(true);
            }
        });
    }

    public void createRegisterFrame(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RegisterFrame registerFrame = new RegisterFrame("用户注册");
                registerFrame.setVisible(true);
            }
        });
    }

    private class UserJPanel extends JPanel{
        @Override
        protected void paintComponent(Graphics g) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("src/resource/UserFrame.png"));
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
