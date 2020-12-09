package com.zjjxgobang.swing.listener;

import com.zjjxgobang.jBean.Player;
import com.zjjxgobang.server.GobangClient;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;

public class WindowsClosed extends WindowAdapter {
    private GobangClient gobangClient;

    public WindowsClosed(GobangClient gobangClient) {
        this.gobangClient = gobangClient;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        Player player = gobangClient.getPlayer();
        Socket socket = player.getPlayerSocket();
        try {
            OutputStreamWriter out = new OutputStreamWriter(new BufferedOutputStream(socket.getOutputStream()), "UTF-8");
            out.write("end\r\n");
            out.flush();
            out.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        System.exit(2);
    }
}
