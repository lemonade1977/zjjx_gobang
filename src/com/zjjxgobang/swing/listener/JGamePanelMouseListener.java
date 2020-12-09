package com.zjjxgobang.swing.listener;

import com.zjjxgobang.jBean.Gobang;
import com.zjjxgobang.jBean.Player;
import com.zjjxgobang.swing.jframe.GameFrame;
import com.zjjxgobang.swing.jpanel.JGamePanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;

public class JGamePanelMouseListener extends MouseAdapter {
    protected JGamePanel jGamePanel;
    int id;
    Gobang gobang;
    Player player;
    Socket socket;

    public JGamePanelMouseListener(JGamePanel jGamePanel,GameFrame gf) {
        this.jGamePanel = jGamePanel;
        GameFrame gameFrame = gf;
        this.gobang = gameFrame.getGobang();
        this.player = gameFrame.getPlayer();
        this.socket = player.getPlayerSocket();
        this.id = jGamePanel.getId();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gobang.doPutGobang(id) && !gobang.isGameOver() && gobang.getNowPlayerColor().equals(player.getPlayerColor())) {
            player.sentGobangIdToServer(id);
            mouseClickDraw();
            gobang.putGobang(id,player.getPlayerColor());
        } else if (gobang.isGameOver()) {
            System.out.println("game over");
            System.out.println(gobang.getGobangMap());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!gobang.doPutGobang(id)) {
            mouseEnteredDraw();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!gobang.doPutGobang(id)) {
            jGamePanel.repaint();
        }
    }

    private void mouseEnteredDraw() {
        jGamePanel.enterGobang();
    }

    private void mouseClickDraw() {
        jGamePanel.drawGobang(player.getPlayerColor());
    }
}
