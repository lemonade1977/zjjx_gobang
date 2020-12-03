package com.zjjxgobang;

import com.zjjxgobang.server.GobangClient;


/**
 * 生成游戏界面
 */
public class GamePlayer1 {
    public static void main(String[] args) {
        GobangClient gobangClient = new GobangClient();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gobangClient.createGame();
            }
        });
    }
}
