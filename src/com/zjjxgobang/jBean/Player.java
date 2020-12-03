package com.zjjxgobang.jBean;

import java.awt.*;
import java.net.Socket;
import java.util.HashSet;

public class Player {
    private String name;
    private int uid;
    private String password;
    private Socket playerSocket;
    private Color playerColor;

    public Player() {
    }

    public Player(Socket playerSocket) {
        this.playerSocket = playerSocket;
    }

    public Player(String name, int uid){
        this.name = name;
        this.uid = uid;
    }

    public Player(String name, int uid, String password) {
        this.name = name;
        this.uid = uid;
        this.password = password;
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
