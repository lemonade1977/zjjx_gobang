package com.zjjxgobang.swing.listener;

import com.zjjxgobang.jBean.Player;
import com.zjjxgobang.server.GobangClient;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class WindowsClosed extends WindowAdapter {
    private GobangClient gobangClient;

    public WindowsClosed(GobangClient gobangClient) {
        this.gobangClient = gobangClient;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        Player player = gobangClient.getPlayer();
        player.sentCloseMsg();
        System.exit(2);
    }
}
