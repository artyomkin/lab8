package clientPackage.controller;

import clientPackage.view.frames.CardFrame;
import clientPackage.view.frames.Switchable;
import common.dataTransferObjects.FlatTransferObject;
import common.taskClasses.Flat;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Switcher {
    private Map<Frames,Switchable> switchables;
    private Switchable active;
    private Frames activeFrame;
    private Frames prevFrame;
    private Notifier notifier;
    private CardFrame cardFrame;
    private Switchable previous;

    public Switcher(Notifier notifier){
        this.switchables = new HashMap();
        this.notifier = notifier;
    }

    public void add(Frames frame, Switchable switchable){
        switchables.put(frame,switchable);
    }

    public void switchFrame(Frames frame){
        previous = active;
        prevFrame = activeFrame;
        active = switchables.get(frame);
        active.setVisible(true);
        activeFrame = frame;
        if(previous!=null) previous.setVisible(false);
        notifier.setActive(frame);
    }
    public void switchOnCard(Consumer<FlatTransferObject> transferAction){
        previous = active;
        prevFrame = activeFrame;
        cardFrame.setVisible(transferAction);
        active = cardFrame;
        activeFrame = Frames.EDITING;
        previous.setVisible(false);
        notifier.setActive(Frames.EDITING);
        notifier.setActive(activeFrame);
    }

    public void switchOnPrev(){
        Frames tf = activeFrame;
        activeFrame = prevFrame;
        prevFrame = tf;
        Switchable t = active;
        active = previous;
        previous = t;
        previous.setVisible(false);
        active.setVisible(true);
        notifier.setActive(activeFrame);
    }

    public Notifier getNotifier(){return this.notifier;}
    public Frames getActiveFrame(){return this.activeFrame;}
    public void setCard(CardFrame frame){
        this.cardFrame = frame;
    }

}
