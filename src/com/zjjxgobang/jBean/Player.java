package com.zjjxgobang.jBean;

import com.zjjxgobang.swing.jpanel.JGamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Player {
    private String name;
    private int uid;
    private String password;
    private Socket playerSocket;
    private Color playerColor;

    public Player() {
    }

    public void sentGobangIdToServer(int id) {
        OutputStreamWriter witer = null;
        try {
            witer = new OutputStreamWriter(playerSocket.getOutputStream(), "UTF-8");
            witer.write(id + "\r\n");
            witer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sentCloseMsg() {
        try {
            OutputStreamWriter out = new OutputStreamWriter(new BufferedOutputStream(playerSocket.getOutputStream()), "UTF-8");
            out.write("end\r\n");
            out.flush();
            out.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                playerSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public String waitForCreateGame() throws IOException {
        BufferedInputStream in = null;
        in = new BufferedInputStream(playerSocket.getInputStream());
        InputStreamReader reader = new InputStreamReader(in, "UTF-8");
        char[] line = new char[96];
        int len = reader.read(line);
        if (len > 0) {
            return String.valueOf(line);
        }
        return null;
    }

    public void receviceMsg(ArrayList<JGamePanel> jPanels) throws IOException {
        InputStreamReader reader = new InputStreamReader(playerSocket.getInputStream());
        String strLine;
        int len;
        char[] line = new char[96];
        while ((len = reader.read(line)) != -1) {
            strLine = String.valueOf(line, 0, len);
            String[] split = strLine.split("\r\n");
            if (split[0].matches("^\\d+$")) {
                JGamePanel jPanel = jPanels.get(Integer.valueOf(split[0]) - 1);
                if (this.getPlayerColor().equals(Color.BLACK)) {
                    jPanel.updateGobang(Color.BLUE);
                } else {
                    jPanel.updateGobang(Color.BLACK);
                }
            } else {
                if (split[0].startsWith("end")) {
                    playerSocket.close();
                    JOptionPane.showMessageDialog(null, "对手已退出游戏-游戏结束", "连接中断", JOptionPane.ERROR_MESSAGE);
                    System.exit(4);
                }
            }
        }
    }

    public void sentError() {
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(playerSocket.getOutputStream());
            OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
            writer.write("socketError\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                playerSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sentRegister(){
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(playerSocket.getOutputStream());
            OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
            writer.write("register;name:"+name+";pwd:"+password+";\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sentBegin() {
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(playerSocket.getOutputStream());
            OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
            writer.write("begin;name:"+name+";pwd:"+password+";\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receviceUserInfo() throws IOException {
        InputStreamReader reader = new InputStreamReader(playerSocket.getInputStream());
        char[] line = new char[96];
        int len = reader.read(line);
        String strLine = String.valueOf(line, 0, len);
    }
    public boolean receviceConnectionMsg() {
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(playerSocket.getInputStream());
            char[] line = new char[96];
            int len = reader.read(line);
            String strLine = String.valueOf(line, 0, len);
            if (strLine.startsWith("OK"))
                return true;
            else
                return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Socket getPlayerSocket() {
        return playerSocket;
    }

    public void setPlayerSocket(Socket playerSocket) {
        this.playerSocket = playerSocket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(Color playColor) {
        this.playerColor = playColor;
    }

    @Override
    public String toString() {
        return "player{" +
                "name='" + name + '\'' +
                ", uid=" + uid +
                '}';
    }
}
