package com.zjjxgobang.swing.jpanel;

import com.zjjxgobang.jBean.Gobang;

import javax.swing.JPanel;
import java.awt.*;

/**
 * 棋盘中的棋子落点
 */
public class JGamePanel extends JPanel {
    private Gobang gobang;
    public final int height = 30;
    public final int width = 30;
    public final int Id;
    public final int radis = 5;
    public final int gobang_radis = 12;

    public JGamePanel(int id,Gobang gobang) {
        this.Id = id + 1;
        this.gobang = gobang;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.drawRect(0, 0, width, height);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.GRAY);
        g.drawLine(width / 2, 0, width / 2, height);
        g.drawLine(0, height / 2, width, height / 2);
    }

    public void updateGobang(Graphics g, Color color){
        if (!gobang.doPutGobang(this.Id) && !gobang.isGameOver()) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.drawOval((width / 2) - gobang_radis, (height / 2) - gobang_radis, 2 * gobang_radis, 2 * gobang_radis);
            g2d.fillOval((width / 2) - gobang_radis, (height / 2) - gobang_radis, 2 * gobang_radis, 2 * gobang_radis);
            g2d.dispose();
            gobang.getGobangMap().put(this.Id, color);
            gobang.changePlayer();
            gobang.isEnd(this.Id);
        } else if (gobang.isGameOver()) {
            System.out.println("game over");
            System.out.println(gobang.getGobangMap());
        }
    }


}
