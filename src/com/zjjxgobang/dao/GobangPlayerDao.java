package com.zjjxgobang.dao;

import com.zjjxgobang.jBean.GobangPlayer;
import org.apache.ibatis.annotations.Param;

public interface GobangPlayerDao {
    public GobangPlayer searchPlayerByEmail(String email);

    public int registerPlayer(@Param("email")String email, @Param("pwd") String pwd);
}
