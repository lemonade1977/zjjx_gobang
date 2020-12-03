package com.zjjxgobang;

import com.zjjxgobang.server.GobangClient;

public class GamePlayer2 {
    public static void main(String[] args) {
        GobangClient gobangClient = new GobangClient();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gobangClient.createGame();
            }
        });
    }
}
