package com.zjjxgobang.swing.jpanel;

import javax.swing.*;
import java.awt.*;

public class ConfirmJPanel extends JPanel {

    private JButton confirmButton ;

    public ConfirmJPanel() {
        super();
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setOpaque(false);
        this.setBackground(null);

        this.setSize(new Dimension(300,120));

            confirmButton = new JButton("确定");
            confirmButton.setSize(new Dimension(60,60));

        this.add(confirmButton);
    }

    public JButton getConfirmButton(){
        return confirmButton;
    }
}
