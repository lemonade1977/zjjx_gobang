package com.zjjxgobang.swing.jframe;

import com.zjjxgobang.jBean.Player;
import com.zjjxgobang.server.GobangClient;
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
import java.net.InetSocketAddress;
import java.net.Socket;

public class LoginFrame extends JFrame {

    private GobangClient gobangClient;
    private LoginFrame loginFrame = this;

    public LoginFrame(String title, GobangClient gobangClient,UserFrame userFrame) throws HeadlessException {
        super(title);
        this.setSize(new Dimension(300, 200));
        this.setResizable(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel contentJpanel = new LoginJPanel();
        this.setContentPane(contentJpanel);
        contentJpanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        contentJpanel.setSize(new Dimension(300, 200));

        InputNormalJPanel emailJpanel = new InputNormalJPanel("邮箱：");
        InputNormalJPanel pwdJpanel = new InputNormalJPanel("密码：", true);

        ConfirmJPanel confirmJpanel = new ConfirmJPanel();
        JButton confirmButton = confirmJpanel.getConfirmButton();

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
                Player player = gobangClient.getPlayer();
                if (player.getPlayerSocket() == null) {
                    Socket socket = new Socket();
                    try {
                        socket.connect(gobangClient.getAddress());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    player.setPlayerSocket(socket);
                }
                player.setName(emailJpanel.getMsg());
                player.setPassword(pwdJpanel.getMsg());
                player.sentLogin();
                if (player.receviceConnectionMsg()) {
                    loginFrame.setVisible(false);
                    userFrame.setVisible(false);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            gobangClient.createGame();
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "连接服务器失败，请重新输入密码或注册用户",
                            "登录错误", JOptionPane.ERROR_MESSAGE);
                    emailJpanel.cleanText();
                    pwdJpanel.cleanText();
                }
            }
        });
    }

    private class LoginJPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("src/resource/bg.png"));
                int width = img.getWidth(this);
                int height = img.getHeight(this);

                Graphics2D g2d = (Graphics2D) g.create();
                g2d.drawImage(img, 0, 0, width, height, this);
                g2d.dispose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
