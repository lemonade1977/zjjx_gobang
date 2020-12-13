package com.zjjxgobang.server;

import com.zjjxgobang.jBean.Gobang;
import com.zjjxgobang.jBean.Player;
import com.zjjxgobang.swing.jframe.FindGameFrame;
import com.zjjxgobang.swing.jframe.GameFrame;
import com.zjjxgobang.swing.jpanel.JGamePanel;
import com.zjjxgobang.swing.listener.WindowsClosed;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class GobangClient {

    Player player = new Player();
    Gobang gobang = new Gobang();

    private static int closeTime = 5000;
    private InetSocketAddress address;

    public GobangClient(InetSocketAddress address) {
        this.address = address;
    }

    public void createGame() {
        JFrame findGobangJFrame = CreateWaitingGUI();
        WaitForPlayer waitForPlayer = new WaitForPlayer(findGobangJFrame, player);
        waitForPlayer.execute();
    }

    private JFrame CreateWaitingGUI() {
        JFrame findGobangJFrame = new FindGameFrame("Gobang");
        findGobangJFrame.setResizable(false);
        findGobangJFrame.setSize(400, 300);
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
                socket.connect(address);
                player.setPlayerSocket(socket);
                socket.setSoTimeout(8000);
                return player.waitForCreateGame();
            } catch (SocketTimeoutException e) {
                JOptionPane.showMessageDialog(null, "太长时间无响应请重启游戏", "连接超时", JOptionPane.ERROR_MESSAGE);
                player.sentError();
                System.exit(-1);
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
                        player.sentError();
                        System.exit(-1);
                    }
                    int colorIndex = s.indexOf("color:") + 6;
                    String color = s.substring(colorIndex, colorIndex + 2);
                    if (color.equals("BL")) {
                        player.setPlayerColor(Color.BLACK);
                        gameFrame.getGobang().setOwnPlayerColor(Color.BLACK);
                        JOptionPane.showMessageDialog(null, "本局游戏你为先手方，请下棋",
                                "游戏开始", JOptionPane.INFORMATION_MESSAGE);
                        player.sentBegin();
                    } else {
                        player.setPlayerColor(Color.BLUE);
                        gameFrame.getGobang().setOwnPlayerColor(Color.BLUE);
                        JOptionPane.showMessageDialog(null, "本局游戏你为后手方，请等待",
                                "游戏开始", JOptionPane.INFORMATION_MESSAGE);
                        player.sentBegin();
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
        GameFrame gobangJFrame = new GameFrame("Gobang", gobang, player);
        gobangJFrame.addWindowListener(new WindowsClosed(this));
        gobangJFrame.setResizable(false);
        gobangJFrame.setSize(600, 610);
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
            try {
                socket.setSoTimeout(60000);
            } catch (SocketException e) {
                e.printStackTrace();
            }
            GameFrame gameFrame = waitForPlayer.getGameFrame();
            ArrayList<JGamePanel> jPanels = gameFrame.getjPanelArrayList();
            try {
                player.receviceMsg(jPanels);
            } catch (SocketTimeoutException e) {
                JOptionPane.showMessageDialog(null, "太长时间无响应请重启游戏", "连接超时", JOptionPane.ERROR_MESSAGE);
                try {
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.exit(-1);
            } catch (SocketException e) {

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public Player getPlayer() {
        return player;
    }

    public Gobang getGobang() {
        return gobang;
    }

    public static int getCloseTime() {
        return closeTime;
    }

    public static void setCloseTime(int closeTime) {
        GobangClient.closeTime = closeTime;
    }

}
