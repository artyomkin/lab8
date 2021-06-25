package clientPackage.view.buttons;

import javax.swing.*;
import java.awt.*;

public class LocalizeButton {
    private JButton button;

    public LocalizeButton(String URL, int height, int width){
        button = new JButton();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(URL).getImage().getScaledInstance(height,width, Image.SCALE_SMOOTH));
        button.setIcon(imageIcon);
        button.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        button.setContentAreaFilled(false);
    }

    public JButton getButton(){
        return button;
    }

}
