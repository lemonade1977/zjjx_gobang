package com.zjjxgobang.server;

import com.zjjxgobang.jBean.Gobang;
import com.zjjxgobang.jBean.Player;
import com.zjjxgobang.swing.jframe.FindGameFrame;
import com.zjjxgobang.swing.jframe.GameFrame;
import com.zjjxgobang.swing.jpanel.JGamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class GobangClient {

    Player player = new Player();

    public void createGame() {
        JFrame findGobangJFrame = CreateWaitingGUI();

        WaitForPlayer waitForPlayer = new WaitForPlayer(findGobangJFrame, player);
        waitForPlayer.execute();

    }

    private JFrame CreateWaitingGUI() {
        JFrame findGobangJFrame = new FindGameFrame("Gobang");
        findGobangJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        findGobangJFrame.setResizable(false);
        findGobangJFrame.setSize(400, 200);
        findGobangJFrame.setVisible(true);
        return findGobangJFrame;
    }

    private class WaitForPlayer extends SwingWorker<String, Object> {

        private Socket socket;
        private JFrame waitFrame;
        private Player player;
        private GameFrame gameFrame = null;

        public WaitForPlayer(JFrame waitFrame, Player player) {
            this.waitFrame = waitFrame;
            this.player = player;
        }

        @Override
        protected String doInBackground() {
            socket = new Socket();
            try {

                socket.connect(new InetSocketAddress("192.168.14.244", 3300));
                player.setPlayerSocket(socket);
                socket.setKeepAlive(true);
                BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
                InputStreamReader reader = new InputStreamReader(in, "UTF-8");
                char[] line = new char[96];
                int len = reader.read(line);
                if (len > 0) {
                    return String.valueOf(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "连接服务器失败", "错误消息", JOptionPane.ERROR_MESSAGE);
                System.exit(-1);
            }
            return null;
        }

        @Override
        protected void done() {
            try {
                String s = get();
                if (s != null) {
                    if (s.startsWith("ok")) {
                        this.gameFrame = showGobang(this.waitFrame, this.player);
                        ClientTask clientTask = new ClientTask(this);
                        Thread task = new Thread(clientTask);
                        task.start();
                    } else {
                        JOptionPane.showMessageDialog(null, "创建对局连接失败", "错误消息", JOptionPane.ERROR_MESSAGE);
                        System.exit(-1);
                    }
                    int colorIndex = s.indexOf("color:") + 6;
                    String color = s.substring(colorIndex, colorIndex + 2);
                    if (color.equals("BL")) {
                        player.setPlayerColor(Color.BLACK);
                        JOptionPane.showMessageDialog(null, "本局游戏你为黑色方先手，请下棋",
                                "游戏开始", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        player.setPlayerColor(Color.BLUE);
                        JOptionPane.showMessageDialog(null, "本局游戏你为蓝色方后手，请等待",
                                "游戏开始", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        public GameFrame getGameFrame() {
            return gameFrame;
        }

        public Socket getSocket() {
            return socket;
        }
    }

    private GameFrame showGobang(JFrame jFrame, Player player) {
        jFrame.setVisible(false);
        Gobang gobang = new Gobang();
        GameFrame gobangJFrame = new GameFrame("Gobang", gobang, player);
        gobangJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gobangJFrame.setResizable(false);
        gobangJFrame.setSize(610, 610);
        gobangJFrame.setVisible(true);

        return gobangJFrame;
    }

    private class ClientTask implements Runnable {

        private WaitForPlayer waitForPlayer;

        public ClientTask(WaitForPlayer waitForPlayer) {
            this.waitForPlayer = waitForPlayer;
        }

        @Override
        public void run() {

            Socket socket = waitForPlayer.getSocket();
            GameFrame gameFrame = waitForPlayer.getGameFrame();
            ArrayList<JGamePanel> jPanels = waitForPlayer.getGameFrame().getjPanelArrayList();
            try {
                InputStreamReader reader = new InputStreamReader(socket.getInputStream());
                int len;
                char[] line = new char[96];
                String strLine;
                while ((len = reader.read(line)) != -1) {
                    strLine = String.valueOf(line, 0, len);
                    String[] split = strLine.split("\r\n");
                    if (split[0].matches("^\\d+$")) {
                        JGamePanel jPanel = jPanels.get(Integer.valueOf(split[0]) - 1);
                        if (player.getPlayerColor().equals(Color.BLACK)) {
                            jPanel.updateGobang(jPanel.getGraphics(), Color.BLUE);
                        } else {
                            jPanel.updateGobang(jPanel.getGraphics(), Color.BLACK);
                        }
                    } else {
                        //  tackle error or GameOver
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
