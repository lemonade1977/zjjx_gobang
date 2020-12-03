package com.zjjxgobang.swing.listener;

import com.zjjxgobang.jBean.Gobang;
import com.zjjxgobang.jBean.Player;
import com.zjjxgobang.swing.jpanel.JGamePanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class JGamePanelMouseListener extends MouseAdapter {
    Gobang gobang;
    Player player;
    protected JGamePanel jGamePanel;
    Socket socket;

    public JGamePanelMouseListener(JGamePanel jGamePanel, Gobang gobang,Player player) {
        this.jGamePanel = jGamePanel;
        this.gobang = gobang;
        this.player = player;
        this.socket = player.getPlayerSocket();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gobang.doPutGobang(jGamePanel.Id) && !gobang.isGameOver() && gobang.getNowPlayerColor().equals(player.getPlayerColor())) {
            try {
                OutputStreamWriter witer = new OutputStreamWriter(socket.getOutputStream(),"UTF-8");
                witer.write(jGamePanel.Id+"\r\n");
                witer.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            mouseClickDraw(jGamePanel.getGraphics());
            gobang.getGobangMap().put(jGamePanel.Id, player.getPlayerColor());
            gobang.changePlayer();
            gobang.isEnd(jGamePanel.Id);
        } else if (gobang.isGameOver()) {
            System.out.println("game over");
            System.out.println(gobang.getGobangMap());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!gobang.doPutGobang(jGamePanel.Id)) {
            mouseEnteredDraw(jGamePanel.getGraphics());
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!gobang.doPutGobang(jGamePanel.Id)) {
            jGamePanel.repaint();
        }
    }

    private void mouseEnteredDraw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.GRAY);
        g2d.drawOval((jGamePanel.width / 2) - jGamePanel.radis, (jGamePanel.height / 2) - jGamePanel.radis, 2 * jGamePanel.radis, 2 * jGamePanel.radis);
        g2d.fillOval((jGamePanel.width / 2) - jGamePanel.radis, (jGamePanel.height / 2) - jGamePanel.radis, 2 * jGamePanel.radis, 2 * jGamePanel.radis);
        g2d.dispose();
    }

    private void mouseClickDraw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(player.getPlayerColor());
        g2d.drawOval((jGamePanel.width / 2) - jGamePanel.gobang_radis, (jGamePanel.height / 2) - jGamePanel.gobang_radis, 2 * jGamePanel.gobang_radis, 2 * jGamePanel.gobang_radis);
        g2d.fillOval((jGamePanel.width / 2) - jGamePanel.gobang_radis, (jGamePanel.height / 2) - jGamePanel.gobang_radis, 2 * jGamePanel.gobang_radis, 2 * jGamePanel.gobang_radis);
        g2d.dispose();
    }
}
