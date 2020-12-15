package com.zjjxgobang.jBean;

public class GobangPlayer {
    private String email;
    private String pwd;
    private Integer winNum;
    private Integer defeatNum;

    @Override
    public String toString() {
        return "email:"+getEmail()+";gender:外星人;winNum:"+getWinNum()+";defeatNum:"+getDefeatNum()+"\r\n";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getWinNum() {
        return winNum;
    }

    public void setWinNum(Integer winNum) {
        this.winNum = winNum;
    }

    public Integer getDefeatNum() {
        return defeatNum;
    }

    public void setDefeatNum(Integer defeatNum) {
        this.defeatNum = defeatNum;
    }
}
