package clientPackage.view.buttons;

import javax.swing.*;
import java.awt.*;

public class GreenButton extends JButton{

    public Color color = new Color(0,153,77);

    public GreenButton(String text, int height, int width) {
        super(text);

        Dimension size = new Dimension(width,height);
        setPreferredSize(size);
        setMaximumSize(size);
        setSize(size);
        setBackground(color);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(color.darker());//Цвет фона при нажатой кнопке
        } else {
            g.setColor(color);//Цвет фона
            setForeground(Color.WHITE);//Цвет надписи
        }
        g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 13, 13);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 13,13);
    }

}
