package com.zjjxgobang.jBean;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Gobang {

    public static final Color GOBANG_PLAYER1_COLOR = Color.BLACK;
    public static final Color GOBANG_PLAYER2_COLOR = Color.BLUE;

    private Color nowPlayerColor = GOBANG_PLAYER1_COLOR;

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

    public void isEnd(int thisId) {
        int left_up = -21;
        int left_down = 19;
        int right_up = -19;
        int right_down = 21;
        int left = -1;
        int right = 1;
        int up = -20;
        int down = 20;


        if (searchGobang(this, thisId, left_up)) {
            return;
        }
        if (searchGobang(this, thisId, left_down)) {
            return;
        }
        if (searchGobang(this, thisId, left)) {
            return;
        }
        if (searchGobang(this, thisId, right_up)) {
            return;
        }
        if (searchGobang(this, thisId, right_down)) {
            return;
        }
        if (searchGobang(this, thisId, right)) {
            return;
        }
        if (searchGobang(this, thisId, up)) {
            return;
        }
        if (searchGobang(this, thisId, down)) {
            return;
        }
    }

    public static boolean searchGobang(Gobang gobang, int thisId, int way) {
        int tmp;
        int time;
        Color thisColor = gobang.getGobangMap().get(thisId);
        String strThisColor;
        if (thisColor.equals(Color.BLACK))
            strThisColor = "黑色";
        else strThisColor = "蓝色";
        time = 1;
        tmp = thisId + way;
        while (true) {
            if (time == 5) {
                gobang.gameOver = true;
                JOptionPane.showMessageDialog(null, "游戏结束:" + strThisColor + "方胜利！",
                        "游戏结束", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
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
}
