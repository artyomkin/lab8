package clientPackage.view.buttons;

import javax.swing.*;
import java.awt.*;

public class TransparentButton {

    private JButton button;

    public TransparentButton(String text, int height, int width){

        button = new JButton(text);
        button.setBorder(new RoundedBorder(10));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setPreferredSize(new Dimension(width,height));
        button.setForeground(new Color(179,179,179));

    }

    public JButton getButton() {
        return button;
    }
}
