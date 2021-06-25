package clientPackage.view.frames;

import common.taskClasses.Flat;

import java.util.ArrayList;

public abstract class MainFrame {

    protected ArrayList<Flat> collection;
    protected String[] fields;

    public String[] getFields() {
        return fields;
    }
    public ArrayList<Flat> getCollection(){
        return collection;
    }
    public abstract void update(ArrayList<Flat> collection);
}
