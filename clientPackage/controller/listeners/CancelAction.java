package clientPackage.controller.listeners;

import clientPackage.controller.Frames;
import clientPackage.controller.Switcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CancelAction implements ActionListener {

    private Switcher switcher;

    public CancelAction(Switcher switcher){
        this.switcher = switcher;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        switcher.switchOnPrev();
    }

}
