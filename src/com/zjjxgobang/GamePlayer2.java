package com.zjjxgobang;

import com.zjjxgobang.server.GobangClient;

import java.net.InetSocketAddress;

public class GamePlayer2 {
    public static void main(String[] args) {
        InetSocketAddress serverAddress = new InetSocketAddress("localhost", 3300);
        GobangClient gobangClient = new GobangClient(serverAddress);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gobangClient.createGame();
            }
        });
    }
}
