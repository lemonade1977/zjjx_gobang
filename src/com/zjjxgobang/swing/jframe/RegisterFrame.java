package com.zjjxgobang.swing.jframe;

import com.zjjxgobang.swing.jpanel.InputNormalJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterFrame extends JFrame {
    public RegisterFrame(String title) throws HeadlessException {
        super(title);
        this.setSize(new Dimension(300,200));
        this.setResizable(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel contentJpanel = new JPanel();
        this.setContentPane(contentJpanel);
        contentJpanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        contentJpanel.setSize(new Dimension(300,200));

            InputNormalJPanel emailJpanel = new InputNormalJPanel("注册邮箱：");
            InputNormalJPanel pwdJpanel = new InputNormalJPanel("设置密码：",true);
            InputNormalJPanel reconfirmPwdJpanel = new InputNormalJPanel("确认密码：",true);

            JPanel confirmJpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            confirmJpanel.setSize(new Dimension(300,120));

                JButton confirmButton = new JButton("确定");
                confirmButton.setSize(new Dimension(60,60));

            confirmJpanel.add(confirmButton);

            Box verticalBox = Box.createVerticalBox();
            Component topMargin = Box.createVerticalStrut(20);

                verticalBox.add(topMargin);
                verticalBox.add(emailJpanel);
                verticalBox.add(pwdJpanel);
                verticalBox.add(reconfirmPwdJpanel);
                verticalBox.add(confirmJpanel);

        contentJpanel.add(verticalBox);

        confirmButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(emailJpanel.getMsg());

                String pwd = pwdJpanel.getMsg();
                String rePwd = reconfirmPwdJpanel.getMsg();

                if (!pwd.equals(rePwd)) {
                    JOptionPane.showMessageDialog(null,
                            "两次输入的密码不一致，请重新填写","注册错误",
                            JOptionPane.WARNING_MESSAGE);
                    pwdJpanel.cleanText();
                    reconfirmPwdJpanel.cleanText();
                }

                System.out.println(pwd);
                System.out.println(rePwd);
            }
        });
    }
}
