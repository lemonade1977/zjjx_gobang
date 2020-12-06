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
            mouseClickDraw();
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
            mouseEnteredDraw();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!gobang.doPutGobang(jGamePanel.Id)) {
            jGamePanel.repaint();
        }
    }

    private void mouseEnteredDraw() {
        jGamePanel.enterGobang();
    }

    private void mouseClickDraw() {
        jGamePanel.putGobang(player.getPlayerColor());
    }
}
