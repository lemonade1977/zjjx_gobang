package com.zjjxgobang.server;

import com.zjjxgobang.Utils.mybatisUtil;
import com.zjjxgobang.dao.GobangPlayerDao;
import com.zjjxgobang.jBean.GobangPlayer;
import org.apache.ibatis.session.SqlSession;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

public class GobangServer {


    public void createGame() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(3300));
            serverSocket.setSoTimeout(300000);

            while (true) {
                ArrayList connect1List = createConnect(serverSocket);
                Socket player1 = (Socket) connect1List.get(0);
                GobangPlayer gobangPlayer1 = (GobangPlayer) connect1List.get(1);
                System.out.println("player 1" + player1 + "\t" + gobangPlayer1);

                ArrayList connect2List = createConnect(serverSocket);
                Socket player2 = (Socket) connect2List.get(0);
                GobangPlayer gobangPlayer2 = (GobangPlayer) connect2List.get(1);
                System.out.println("player 2" + player2 + "\t" + gobangPlayer2);


                if (!player1.isClosed() && !player2.isClosed()) {
                    String color1;
                    String color2;
                    if (Math.random() < 0.5) {
                        color1 = "BL";
                        color2 = "BU";
                    } else {
                        color1 = "BU";
                        color2 = "BL";
                    }
                    if (player1.isClosed() || player2.isClosed())
                        continue;

                    createGameConnect(player1, color1);
                    createGameConnect(player2, color2);

                    System.out.println();
                    sentPlayerInfo(player1, gobangPlayer1, gobangPlayer2);
                    sentPlayerInfo(player2, gobangPlayer1, gobangPlayer2);

                    PlayGameTask playGameTask1 = new PlayGameTask(player1, player2);
                    PlayGameTask playGameTask2 = new PlayGameTask(player2, player1);
                    Thread playGameThread1 = new Thread(playGameTask1);
                    Thread playGameThread2 = new Thread(playGameTask2);
                    playGameThread1.start();
                    playGameThread2.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createGameConnect(Socket socket, String color) {
        try {
            OutputStreamWriter write = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            write.write("ok\tcolor:" + color + "\r\n");
            write.flush();
            InputStreamReader reader = new InputStreamReader(socket.getInputStream(), "UTF-8");
            char[] line = new char[96];
            int len = reader.read(line);
            String strLine = String.valueOf(line, 0, len);
            if (!strLine.startsWith("begin"))
                throw new SocketException();
        } catch (SocketException e) {
            System.err.println("Socket has closed");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    private class PlayGameTask implements Runnable {

        private Socket thisPlayerSocket;
        private Socket otPlayerSocket;

        public PlayGameTask(Socket thisPlayerSocket, Socket otPlayerSocket) {
            this.thisPlayerSocket = thisPlayerSocket;
            this.otPlayerSocket = otPlayerSocket;
        }

        @Override
        public void run() {
            try {
                InputStreamReader reader = new InputStreamReader(thisPlayerSocket.getInputStream(), "UTF-8");
                OutputStreamWriter otherWriter = new OutputStreamWriter(otPlayerSocket.getOutputStream(), "UTF-8");

                int len;
                char[] line = new char[96];
                while ((len = reader.read(line)) != -1) {
                    String msg = String.valueOf(line, 0, len);
                    otherWriter.write(msg);
                    otherWriter.flush();
                    if (msg.startsWith("end")) {
                        if (!thisPlayerSocket.isClosed())
                            thisPlayerSocket.close();
                        if (!otPlayerSocket.isClosed())
                            otPlayerSocket.close();
                    }
                }

            } catch (SocketException e) {
                System.err.println("Socket has closed");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (!thisPlayerSocket.isClosed())
                        thisPlayerSocket.close();
                    if (!otPlayerSocket.isClosed())
                        otPlayerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList createConnect(ServerSocket serverSocket) {
        ArrayList<Object> list = new ArrayList<>();
        Socket accept = null;
        try {
            accept = serverSocket.accept();
            GobangPlayer gobangPlayer = tryCreateConnect(accept);
            System.out.println("dao get player" + gobangPlayer);
            list.add(accept);
            list.add(gobangPlayer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return list;
    }

    public GobangPlayer tryCreateConnect(Socket accept) throws IOException {
        InputStreamReader reader = new InputStreamReader(new BufferedInputStream(accept.getInputStream()), "UTF-8");
        char[] line = new char[96];
        int len = reader.read(line);
        String strLine = String.valueOf(line, 0, len);
        String[] split = strLine.split(";");
        GobangPlayer gobangPlayer = null;
        switch (split[0]) {
            case "login":
                String[] loginEmailStr = split[1].split(":");
                String[] loginPwdStr = split[2].split(":");
                String loginEmail = loginEmailStr[1];
                String loginPwd = loginPwdStr[1];
                if (loginEmailStr[0].equals("name") && loginPwdStr[0].equals("pwd")) {
                    SqlSession sqlSession = mybatisUtil.getSqlSession();
                    GobangPlayerDao gobangPlayerDao = sqlSession.getMapper(GobangPlayerDao.class);
                    gobangPlayer = gobangPlayerDao.searchPlayerByEmail(loginEmail);
                    sqlSession.close();
                    OutputStreamWriter writer = new OutputStreamWriter(new BufferedOutputStream(accept.getOutputStream()), "UTF-8");
                    if (gobangPlayer == null) {
                        writer.write("ERROR\r\n");
                        writer.flush();
                        gobangPlayer = tryCreateConnect(accept);
                    } else if (gobangPlayer.getPwd().equals(loginPwd)) {
                        writer.write("OK\r\n");
                        writer.flush();
                    } else {
                        writer.write("ERROR\r\n");
                        writer.flush();
                        gobangPlayer = tryCreateConnect(accept);
                    }
                }
                break;
            case "register":
                String[] registerEmailStr = split[1].split(":");
                String[] registerPwdStr = split[2].split(":");
                String registerEmail = registerEmailStr[1];
                String registerPwd = registerPwdStr[1];

                OutputStreamWriter writer = new OutputStreamWriter(new BufferedOutputStream(accept.getOutputStream()), "UTF-8");

                if (registerEmailStr[0].equals("name") && registerPwdStr[0].equals("pwd")) {
                    SqlSession sqlSession = mybatisUtil.getSqlSession();
                    GobangPlayerDao gobangPlayerDao = sqlSession.getMapper(GobangPlayerDao.class);
                    int i = gobangPlayerDao.registerPlayer(registerEmail, registerPwd);
                    gobangPlayer = gobangPlayerDao.searchPlayerByEmail(registerEmail);
                    sqlSession.close();
                    if (i == 1) {
                        writer.write("OK\r\n");
                        writer.flush();
                    } else {
                        writer.write("ERROR\r\n");
                        writer.flush();
                        gobangPlayer = tryCreateConnect(accept);
                    }
                } else {
                    writer.write("ERROR\r\n");
                    writer.flush();
                    gobangPlayer = tryCreateConnect(accept);
                }
                break;
        }
        return gobangPlayer;
    }

    public void sentPlayerInfo(Socket socket, GobangPlayer player1, GobangPlayer player2) {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(new BufferedOutputStream(socket.getOutputStream()), "UTF-8");
            writer.write("player1;" + player1);
            writer.write("player2;" + player2);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


