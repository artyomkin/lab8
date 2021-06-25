package clientPackage.controller.listeners;

import clientPackage.view.frames.MainFrame;
import clientPackage.controller.Notifier;
import common.taskClasses.Flat;
import common.taskClasses.Transport;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.function.Predicate;

public class FilterAction implements ActionListener {
    private MainFrame frame;
    private JTextField textField;
    private JComboBox comboBox;
    private Notifier notifier;
    private ArrayList<Flat> collection;
    private ArrayList<Flat> newCollection;

    public FilterAction(Notifier notifier, MainFrame frame, JTextField textField, JComboBox comboBox){
        this.frame = frame;
        this.textField = textField;
        this.comboBox = comboBox;
        this.notifier = notifier;
    }

    public void actionPerformed(ActionEvent e){
        String filterBy = (String)comboBox.getSelectedItem();
        String val = textField.getText();

        collection = frame.getCollection();
        newCollection = new ArrayList<>();
        String[] fields = frame.getFields();
        try{
            Predicate<Flat> predicate;
            if(filterBy.equals(fields[0])){
                predicate = flat->Integer.valueOf(val).equals(flat.getID());
            } else if (filterBy.equals(fields[1])){
                predicate = flat->val.equals(flat.getName());
            } else if (filterBy.equals(fields[2])){
                predicate = flat->Double.valueOf(val).equals(flat.getCoordinates().getX());
            } else if (filterBy.equals(fields[3])){
                predicate = flat->Long.valueOf(val).equals(flat.getCoordinates().getY());
            } else if (filterBy.equals(fields[4])){
                predicate = flat-> Date.valueOf(val).equals(flat.getDate());
            } else if (filterBy.equals(fields[5])){
                predicate = flat->Long.valueOf(val).equals(flat.getArea());
            } else if (filterBy.equals(fields[6])){
                predicate = flat ->Integer.valueOf(val).equals(flat.getNumberOfRooms());
            } else if (filterBy.equals(fields[7])){
                predicate = flat ->Double.valueOf(val).equals(flat.getPrice());
            } else if (filterBy.equals(fields[8])){
                predicate = flat -> Integer.valueOf(val).equals(flat.getLivingSpace());
            } else if (filterBy.equals(fields[9])){
                predicate = flat-> Transport.valueOf(val).equals(flat.getTransport());
            } else if (filterBy.equals(fields[10])){
                predicate = flat-> val.equals(flat.getHouse().getName());
            } else if (filterBy.equals(fields[11])){
                predicate = flat -> Integer.valueOf(val).equals(flat.getHouse().getYear());
            } else if (filterBy.equals(fields[12])){
                predicate = flat -> Integer.valueOf(val).equals(flat.getHouse().getNumberOfFloors());
            } else if (filterBy.equals(fields[13])){
                predicate = flat-> Long.valueOf(val).equals(flat.getHouse().getNumberOfFlatsOnFloor());
            } else if (filterBy.equals(fields[14])){
                predicate = flat->Integer.valueOf(val).equals(flat.getHouse().getNumberOfLifts());
            } else if (filterBy.equals(fields[15])){
                predicate = flat->val.equals(flat.getCreator());
            } else {
                return;
            }
            filter(predicate);
            notifier.info("Filtered successfully");
            frame.update(newCollection);

        } catch (IllegalArgumentException exception){
            notifier.warn("Incorrect filter data");
        }

    }

    public void filter(Predicate<Flat> predicate){
        collection.stream()
                .filter(predicate)
                .forEach(flat->newCollection.add(flat));
    }
}
