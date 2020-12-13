package com.zjjxgobang.swing.jframe;

import com.zjjxgobang.jBean.Gobang;
import com.zjjxgobang.jBean.Player;
import com.zjjxgobang.swing.jpanel.JGamePanel;
import com.zjjxgobang.swing.listener.JGamePanelMouseListener;

import javax.imageio.ImageIO;
import javax.swing.*;
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

    JPanel jPanel = new GobangPanel();
    public GameFrame(String title,Gobang gobang,Player player){
        super(title);

        this.gobang = gobang;
        this.player = player;

        jPanel.setSize(600,600);
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(jPanel,BorderLayout.CENTER);
        jPanel.setLayout(new GridLayout(rows,cols,0,0));

        JGamePanel jGamePanelTmp;
        for (int i =0;i<rows*cols;i++){
            jGamePanelTmp = new JGamePanel(i,this);
            jPanelArrayList.add(jGamePanelTmp);
            JGamePanelMouseListener jGamePanelMouseListener = new JGamePanelMouseListener(jGamePanelTmp,this);
            jGamePanelTmp.addMouseListener(jGamePanelMouseListener);
            jPanel.add(jPanelArrayList.get(i));
        }
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
}
