package com.zjjxgobang.swing.jframe;

import com.zjjxgobang.jBean.Gobang;
import com.zjjxgobang.jBean.Player;
import com.zjjxgobang.swing.jpanel.JGamePanel;
import com.zjjxgobang.swing.listener.JGamePanelMouseListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 棋盘
 */
public class GameFrame extends JFrame {
    private int rows = 20;
    private int cols = 20;
    private Gobang gobang;
    private Player player;
    ArrayList<JGamePanel> jPanelArrayList = new ArrayList<>();

    public Gobang getGobang() {
        return gobang;
    }
    public Player getPlayer() {
        return player;
    }

    public ArrayList<JGamePanel> getjPanelArrayList() {
        return jPanelArrayList;
    }

    JPanel gobangJPanel = new GobangPanel();
    public GameFrame(String title,Gobang gobang,Player player){
        super(title);

        this.gobang = gobang;
        this.player = player;

        this.setResizable(true);
        this.setSize(800, 605);

        Container contentPane = this.getContentPane();

        gobangJPanel.setSize(600,600);
        gobangJPanel.setLayout(new GridLayout(rows,cols,0,0));

        JGamePanel jGamePanelTmp;
        for (int i =0;i<rows*cols;i++){
            jGamePanelTmp = new JGamePanel(i,this);
            jPanelArrayList.add(jGamePanelTmp);
            JGamePanelMouseListener jGamePanelMouseListener = new JGamePanelMouseListener(jGamePanelTmp,this);
            jGamePanelTmp.addMouseListener(jGamePanelMouseListener);
            gobangJPanel.add(jPanelArrayList.get(i));
        }

        UserInfoPanel thisPlayerInfoJPanel = new UserInfoPanel("src/resource/player1.png");
        UserInfoPanel otherPlayerInfoJPanel = new UserInfoPanel("src/resource/player2.png");

        Box usersInfoPanelBox = Box.createVerticalBox();
        usersInfoPanelBox.setSize(new Dimension(200,600));
        usersInfoPanelBox.add(thisPlayerInfoJPanel);
        usersInfoPanelBox.add(otherPlayerInfoJPanel);


        contentPane.add(gobangJPanel,0);
        contentPane.add(usersInfoPanelBox,1);
    }

    private class GobangPanel extends JPanel{
        @Override
        protected void paintComponent(Graphics g) {
            try {
                BufferedImage img = ImageIO.read(new File("src/resource/gobang_game.png"));
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
    private class UserInfoPanel extends JPanel{
        private String bgImgURL;
        public UserInfoPanel(String url) {
            super();
            bgImgURL=url;
            this.setSize(new Dimension(200,300));
            this.setLayout(new FlowLayout(FlowLayout.RIGHT));

            JPanel userNamePanel = new RowPanel("用户名");
            JPanel genderPanel = new RowPanel("性别");
            JPanel winNumPanel = new RowPanel("胜场");
            JPanel winRatePanel = new RowPanel("胜率");

            Box verticalBox = Box.createVerticalBox();
            Component topMargin = Box.createVerticalStrut(55);
            verticalBox.add(topMargin);
            verticalBox.add(userNamePanel);
            verticalBox.add(genderPanel);
            verticalBox.add(winNumPanel);
            verticalBox.add(winRatePanel);

            Box horizontalBox = Box.createHorizontalBox();
            Component rightMarigin = Box.createHorizontalStrut(50);

            horizontalBox.add(verticalBox);
            horizontalBox.add(rightMarigin);

            this.add(horizontalBox);
        }

        @Override
        protected void paintComponent(Graphics g) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File(bgImgURL));
                int width = img.getWidth(this);
                int height = img.getHeight(this);

                Graphics2D g2d = (Graphics2D) g.create();
                g2d.drawImage(img,600,0,width,height,this);
                g2d.dispose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class RowPanel extends JPanel{
        private JLabel key;
        private JLabel value;

        public RowPanel(String title) {
            super();
            this.setSize(new Dimension(200,50));
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.setOpaque(false);
            this.setBackground(null);
            key = new JLabel(title+"：");
            value = new JLabel("XXX");
            this.add(key);
            this.add(value);
        }

        public void setValue(String s){
            value.setText(s);
        }
    }
}
