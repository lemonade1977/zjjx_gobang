package com.zjjxgobang.swing.jframe;

import javax.swing.*;
import java.awt.*;

public class FindGameFrame extends JFrame {
    JPanel jPanel = new JPanel();

    public FindGameFrame(String title) throws HeadlessException {
        super(title);
        jPanel.setSize(new Dimension(400, 200));
        this.setContentPane(jPanel);

        jPanel.setLayout(new BorderLayout());

        JTextField jTextField = new JTextField("正在寻找对局....");
        jTextField.setSize(new Dimension(400, 190));
        jTextField.setHorizontalAlignment(JTextField.CENTER);
        jTextField.setEnabled(false);
        jPanel.add(jTextField, BorderLayout.CENTER);

    }

}
