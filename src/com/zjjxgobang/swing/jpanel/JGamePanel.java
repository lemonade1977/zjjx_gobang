package com.zjjxgobang.swing.jpanel;

import com.zjjxgobang.jBean.Gobang;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 棋盘中的棋子落点
 */
public class JGamePanel extends JPanel {
    private Gobang gobang;
    public final int height = 30;
    public final int width = 30;
    public final int Id;
    public final int radis = 5;
    public final Color lineColor = new Color(141, 143, 181);
    public final Color enterColor = new Color(165, 149, 166);

    public JGamePanel(int id,Gobang gobang) {
        this.Id = id + 1;
        this.gobang = gobang;
        this.setBackground(null);
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(lineColor);
        g.drawLine(width / 2, 0, width / 2, height);
        g.drawLine(0, height / 2, width, height / 2);
    }

    public void updateGobang(Color color){
        if (!gobang.doPutGobang(this.Id) && !gobang.isGameOver()) {
            putGobang(color);
            gobang.getGobangMap().put(this.Id, color);
            gobang.changePlayer();
            gobang.isEnd(this.Id);
        } else if (gobang.isGameOver()) {
            System.out.println("game over");
            System.out.println(gobang.getGobangMap());
        }
    }

    public void putGobang(Color color){
        Graphics g = this.getGraphics();
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        BufferedImage img=null;
        int width=30;
        int height=30;
        if (color.equals(Color.BLACK)) {
            try {
                img = ImageIO.read(new File("src/resource/bang1.png"));
                width = img.getWidth(this);
                height = img.getHeight(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                img = ImageIO.read(new File("src/resource/bang2.png"));
                width = img.getWidth(this);
                height = img.getHeight(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        g2d.drawImage(img,0,0,width,height,this);
        g2d.dispose();
    }

    public void enterGobang(){
        Graphics g =  this.getGraphics();
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(enterColor);
        g2d.drawOval((width / 2) - radis, (height / 2) - radis, 2 * radis, 2 * radis);
        g2d.fillOval((width / 2) - radis, (height / 2) - radis, 2 * radis, 2 * radis);
        g2d.dispose();
    }

}
