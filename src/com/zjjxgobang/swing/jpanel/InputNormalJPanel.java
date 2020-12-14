package com.zjjxgobang.swing.jpanel;

import javax.swing.*;
import java.awt.*;

public class InputNormalJPanel extends JPanel {

    private JTextField input;

    public InputNormalJPanel(String txt) {
        this(txt,false);
    }

    public InputNormalJPanel(String txt,Boolean isPasswordField) {
        this.setSize(new Dimension(300,60));

        JLabel text = new JLabel(txt);
        if (isPasswordField)
            input = new JPasswordField(16);
        else
            input = new JTextField(16);
        this.add(text);
        this.add(input);
    }

    public String getMsg(){
        return input.getText();
    }

    public void cleanText(){
        input.setText("");
    }
}
