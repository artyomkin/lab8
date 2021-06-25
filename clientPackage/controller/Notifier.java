package clientPackage.controller;
import clientPackage.view.frames.Notifiable;

import java.util.HashMap;
import java.util.Map;

public class Notifier {

    private Map<Frames,Notifiable> notifiables;
    private Notifiable active;

    public Notifier(){
        notifiables = new HashMap();
        active = null;
    }

    public void add(Frames frame, Notifiable notifiable){
        notifiables.put(frame,notifiable);
    }

    public void warn(String msg){
        active.warn(msg);
    }

    public void info(String msg){
        active.info(msg);
    }

    public void setActive(Frames frame){
        this.active = notifiables.get(frame);
    }

}
