package com.zjjxgobang.swing.jframe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserFrame extends JFrame {


    public UserFrame(String title) throws HeadlessException {
        super(title);
        this.setSize(new Dimension(600,500));
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container rootPanel = this.getContentPane();
        rootPanel.setSize(new Dimension(600,500));
        rootPanel.setLayout(new BorderLayout());

            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BorderLayout());
            centerPanel.setSize(new Dimension(600,200));


                JButton loginButton = new JButton("登录");
                loginButton.setSize(new Dimension(80,60));
                JButton registerButton = new JButton("注册");
                registerButton.setSize(new Dimension(80,60));

                Box horizontalBox = Box.createHorizontalBox();
                Component centerSplit = Box.createHorizontalStrut(40);
                Component leftSplit = Box.createHorizontalStrut(200);
                horizontalBox.add(leftSplit);
                horizontalBox.add(loginButton);
                horizontalBox.add(centerSplit);
                horizontalBox.add(registerButton);

            centerPanel.add(horizontalBox,BorderLayout.CENTER);

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

}
