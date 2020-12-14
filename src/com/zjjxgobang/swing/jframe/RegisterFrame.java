package com.zjjxgobang.swing.jframe;

import com.zjjxgobang.swing.jpanel.ConfirmJPanel;
import com.zjjxgobang.swing.jpanel.InputNormalJPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RegisterFrame extends JFrame {
    public RegisterFrame(String title) throws HeadlessException {
        super(title);
        this.setSize(new Dimension(300,200));
        this.setResizable(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel contentJpanel = new RegisterJPanel();
        this.setContentPane(contentJpanel);
        contentJpanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        contentJpanel.setSize(new Dimension(300,200));

            InputNormalJPanel emailJpanel = new InputNormalJPanel("注册邮箱：");
            InputNormalJPanel pwdJpanel = new InputNormalJPanel("设置密码：",true);
            InputNormalJPanel reconfirmPwdJpanel = new InputNormalJPanel("确认密码：",true);

            ConfirmJPanel confirmJpanel = new ConfirmJPanel();
            JButton confirmButton = confirmJpanel.getConfirmButton();

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

    private class RegisterJPanel extends JPanel{
        @Override
        protected void paintComponent(Graphics g) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("src/resource/bg.png"));
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
