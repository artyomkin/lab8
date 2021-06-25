package clientPackage.view.buttons;

import javax.swing.*;

public class NoBGButton {

    private JButton button;

    public NoBGButton(String text, boolean underlined){

        button = new JButton(text);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

    }

    public JButton getButton(){
        return this.button;
    }

}
