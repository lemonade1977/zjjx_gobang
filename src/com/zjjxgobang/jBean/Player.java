package com.zjjxgobang.jBean;

import com.zjjxgobang.swing.jframe.GameFrame;
import com.zjjxgobang.swing.jpanel.JGamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

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

    public void sentRegister() {
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(playerSocket.getOutputStream());
            OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
            writer.write("register;name:" + name + ";pwd:" + password + ";\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sentLogin() {
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(playerSocket.getOutputStream());
            OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
            writer.write("login;name:" + name + ";pwd:" + password + ";\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sentBegin(){
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(playerSocket.getOutputStream());
            OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
            writer.write("begin");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receviceUserInfo(GameFrame gameFrame) {
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(playerSocket.getInputStream());
            char[] line = new char[256];
            int len = reader.read(line);
            String strLine = String.valueOf(line, 0, len);
            String[] lines = strLine.split("\r\n");
            for (int i = 0; i < 2; i++) {
                String[] split = lines[i].split(";");
                Arrays.asList(split).forEach(System.out::println);
                String emailStr = split[1];
                String email = emailStr.split(":")[1];
                String genderStr = split[2];
                String gender = genderStr.split(":")[1];
                String winNumStr = split[3];
                String winNum = winNumStr.split(":")[1];
                String defeatNumStr = split[4];
                String defeatNum = defeatNumStr.split(":")[1].trim();
                Integer gameNum =  Integer.valueOf(winNum)+Integer.valueOf(defeatNum);
                BigDecimal bigDecimalWinRate;
                if (gameNum.equals(0)){
                    bigDecimalWinRate = new BigDecimal(1);
                }else {
                    Double winRate = Double.valueOf(winNum)/gameNum;
                    bigDecimalWinRate = new BigDecimal(winRate).setScale(2, RoundingMode.UP);
                }
                switch (split[0]) {
                    case "player1":
                        updatePlayerInfo(gameFrame.getPlayer1Panel(), email, gender, winNum, String.valueOf(bigDecimalWinRate));
                        break;
                    case "player2":
                        updatePlayerInfo(gameFrame.getPlayer2Panel(), email, gender, winNum, String.valueOf(bigDecimalWinRate));
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayerInfo(GameFrame.UserInfoPanel userInfoPanel, String email, String gender,
                                 String winNum, String winRate) {
        userInfoPanel.getUserNamePanel().setValue(email);
        userInfoPanel.getGenderPanel().setValue(gender);
        userInfoPanel.getWinNumPanel().setValue(winNum);
        userInfoPanel.getWinRatePanel().setValue(winRate);
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
