package com.zjjxgobang.swing.jframe;

import com.zjjxgobang.jBean.Gobang;
import com.zjjxgobang.jBean.Player;
import com.zjjxgobang.swing.jpanel.JGamePanel;
import com.zjjxgobang.swing.listener.JGamePanelMouseListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * 棋盘
 */
public class GameFrame extends JFrame {
    private int rows = 20;
    private int cols = 20;
    private Gobang gobang;

    JPanel jPanel = new JPanel();
    ArrayList<JGamePanel> jPanelArrayList = new ArrayList<>();

    public GameFrame(String title,Gobang gobang,Player player){
        super(title);
        this.gobang = gobang;
        jPanel.setSize(600,600);
        this.setContentPane(jPanel);
        jPanel.setLayout(new GridLayout(rows,cols,0,0));

        JGamePanel jGamePanelTmp;
        for (int i =0;i<rows*cols;i++){
            jGamePanelTmp = new JGamePanel(i,gobang);
            jPanelArrayList.add(jGamePanelTmp);
            JGamePanelMouseListener jGamePanelMouseListener = new JGamePanelMouseListener(jGamePanelTmp,this.gobang,player);
            jGamePanelTmp.addMouseListener(jGamePanelMouseListener);
            jPanel.add(jPanelArrayList.get(i));
        }
    }

    public ArrayList<JGamePanel> getjPanelArrayList() {
        return jPanelArrayList;
    }
}
