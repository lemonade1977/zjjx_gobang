package com.zjjxgobang.jBean;

import com.zjjxgobang.server.GobangClient;
import com.zjjxgobang.swing.jframe.LoserFrame;
import com.zjjxgobang.swing.jframe.WinnerFrame;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static java.lang.Thread.sleep;

public class Gobang {

    public static final Color GOBANG_PLAYER1_COLOR = Color.BLACK;
    public static final Color GOBANG_PLAYER2_COLOR = Color.BLUE;

    private Color nowPlayerColor = GOBANG_PLAYER1_COLOR;
    private Color ownPlayerColor;

    public Color getOwnPlayerColor() {
        return ownPlayerColor;
    }

    public void setOwnPlayerColor(Color ownPlayerColor) {
        this.ownPlayerColor = ownPlayerColor;
    }

    private boolean gameOver = false;

    private HashMap<Integer, Color> gobangMap = new HashMap<>();

    public HashMap<Integer, Color> getGobangMap() {
        return gobangMap;
    }

    public void setGobangMap(HashMap<Integer, Color> gobangMap) {
        this.gobangMap = gobangMap;
    }

    public Color getNowPlayerColor() {
        return nowPlayerColor;
    }

    public void setNowPlayerColor(Color nowPlayerColor) {
        this.nowPlayerColor = nowPlayerColor;
    }

    public boolean isEnd(int thisId) {
        int left_up = -21;
        int left_down = 19;
        int right_up = -19;
        int right_down = 21;
        int left = -1;
        int right = 1;
        int up = -20;
        int down = 20;


        if (searchGobang(this, thisId, left_up)) {
            return true;
        }
        if (searchGobang(this, thisId, left_down)) {
            return true;
        }
        if (searchGobang(this, thisId, left)) {
            return true;
        }
        if (searchGobang(this, thisId, right_up)) {
            return true;
        }
        if (searchGobang(this, thisId, right_down)) {
            return true;
        }
        if (searchGobang(this, thisId, right)) {
            return true;
        }
        if (searchGobang(this, thisId, up)) {
            return true;
        }
        if (searchGobang(this, thisId, down)) {
            return true;
        }
        return false;
    }

    public boolean searchGobang(Gobang gobang, int thisId, int way) {
        int tmp;
        int time;
        Color thisColor = gobang.getGobangMap().get(thisId);
        time = 1;
        tmp = thisId + way;
        while (true) {
            if (time == 5) {
                gobang.gameOver = true;
                if (thisColor.equals(this.ownPlayerColor))
                    showWinnerGUI();
                else
                    showLoserGUI();
                Thread closeTask = new Thread(new CloseTask());
                closeTask.start();
                return true;
            }
            if (gobang.getGobangMap().get(tmp) != null) {
                if (gobang.getGobangMap().get(tmp).equals(thisColor)) {
                    time++;
                } else break;
            } else break;
            tmp = tmp + way;
        }
        return false;
    }

    public void putGobang(int id,Color color){
        gobangMap.put(id,color);
        changePlayer();
        isEnd(id);
    }

    public boolean doPutGobang(int id) {
        HashMap<Integer, Color> gobangMap = this.getGobangMap();
        if (gobangMap.get(id) != null)
            return true;
        else
            return false;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public void changePlayer() {
        if (this.getNowPlayerColor().equals(GOBANG_PLAYER1_COLOR))
            this.setNowPlayerColor(GOBANG_PLAYER2_COLOR);
        else
            this.setNowPlayerColor(GOBANG_PLAYER1_COLOR);
    }

    public void showWinnerGUI(){
        WinnerFrame winnerFrame = new WinnerFrame("Winner");
        winnerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        winnerFrame.setResizable(false);
        winnerFrame.setSize(550, 500);
        winnerFrame.setVisible(true);
    }

    public void showLoserGUI(){
        LoserFrame loserFrame = new LoserFrame("Loser");
        loserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loserFrame.setResizable(false);
        loserFrame.setSize(550, 500);
        loserFrame.setVisible(true);
    }

    private class CloseTask implements Runnable{
        @Override
        public void run() {
            try {
                sleep(GobangClient.getCloseTime());
                System.exit(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
