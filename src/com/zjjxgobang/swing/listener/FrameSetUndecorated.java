package com.zjjxgobang.swing.listener;

import com.zjjxgobang.swing.jframe.FindGameFrame;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class FrameSetUndecorated {
    private JFrame jFrame;
    private int xOld = 0;
    private int yOld = 0;

    public FrameSetUndecorated(JFrame jFrame) {
        this.jFrame = jFrame;
    }
    public void doSet(){
        jFrame.setUndecorated(true);
        // 以下鼠标监听为实现窗口移动
        jFrame.addMouseListener(new MouseAdapter() {
                                    public void mousePressed(MouseEvent e) {
                                        xOld = e.getX();
                                        yOld = e.getY();
                                    }
                                }
        );
        jFrame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int xOnScreen = e.getXOnScreen();
                int yOnScreen = e.getYOnScreen();

                int xLo = xOnScreen - xOld;
                int yLo = yOnScreen - yOld;

                jFrame.setLocation(xLo, yLo);
            }
        });
    }

}
