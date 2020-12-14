package com.zjjxgobang.swing.jframe;

import com.zjjxgobang.swing.jpanel.InputNormalJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFrame extends JFrame {

    public LoginFrame(String title) throws HeadlessException {
        super(title);
        this.setSize(new Dimension(300,200));
        this.setResizable(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel contentJpanel = new JPanel();
        this.setContentPane(contentJpanel);
        contentJpanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        contentJpanel.setSize(new Dimension(300,200));

            InputNormalJPanel emailJpanel = new InputNormalJPanel("邮箱：");
            InputNormalJPanel pwdJpanel = new InputNormalJPanel("密码：",true);

            JPanel confirmJpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            confirmJpanel.setSize(new Dimension(300,120));

                JButton confirmButton = new JButton("确定");
                confirmButton.setSize(new Dimension(60,60));

            confirmJpanel.add(confirmButton);

            Box verticalBox = Box.createVerticalBox();
            Component topMargin = Box.createVerticalStrut(30);

                verticalBox.add(topMargin);
                verticalBox.add(emailJpanel);
                verticalBox.add(pwdJpanel);
                verticalBox.add(confirmJpanel);

        contentJpanel.add(verticalBox);

        confirmButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(emailJpanel.getMsg());
                System.out.println(pwdJpanel.getMsg());
            }
        });
    }

}
