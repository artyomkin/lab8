package clientPackage.controller.listeners;

import clientPackage.controller.InputValidator;
import clientPackage.controller.Notifier;
import clientPackage.controller.Switcher;
import common.Pair;
import common.dataTransferObjects.CoordinatesTransferObject;
import common.dataTransferObjects.FlatTransferObject;
import common.dataTransferObjects.HouseTransferObject;
import common.dataTransferObjects.TransportTransferObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Consumer;

public class TransferAction implements ActionListener {

    private ArrayList<JTextField> fields;
    private int ID;
    private Date date;
    private String creator;
    private Notifier notifier;
    private Consumer<FlatTransferObject> consumer;
    private Switcher switcher;
    public TransferAction(ArrayList<JTextField> fields,Switcher switcher, Notifier notifier){
        this.fields = fields;
        this.notifier = notifier;
        this.switcher = switcher;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        FlatTransferObject flat = new FlatTransferObject();
        flat.setID(ID);
        flat.setName(fields.get(0).getText());
        CoordinatesTransferObject coordinates = new CoordinatesTransferObject();
        try{
            coordinates.setX(Double.valueOf(fields.get(1).getText()));
        } catch (NumberFormatException exception){
            notifier.warn("Incorrect x");
            return;
        }
        try{
            coordinates.setY(Long.valueOf(fields.get(2).getText()));
        } catch (NumberFormatException exception){
            notifier.warn("Incorrect y");
            return;
        }
        flat.setCoordinates(coordinates);
        try{
            flat.setArea(Long.valueOf(fields.get(3).getText()));
        } catch (NumberFormatException exception){
            notifier.warn("Incorrect area");
            return;
        }
        try{
            flat.setNumberOfRooms(Integer.valueOf(fields.get(4).getText()));
        }catch (NumberFormatException exception){
            notifier.warn("Incorrect number of rooms");
            return;
        }
        try{
            flat.setPrice(Double.valueOf(fields.get(5).getText()));
        } catch(NumberFormatException exception){
            notifier.warn("Incorrect price");
            return;
        }
        try{
            flat.setLivingSpace(Integer.valueOf(fields.get(6).getText()));
        }catch (NumberFormatException exception){
            notifier.warn("Incorrect living space");
            return;
        }
        try{
            flat.setTransport(TransportTransferObject.valueOf(fields.get(7).getText()));
        } catch (IllegalArgumentException exception){
            notifier.warn("Incorrect transport");
            return;
        }
        HouseTransferObject house = new HouseTransferObject();
        house.setName(fields.get(8).getText());
        try{
            house.setYear(Integer.valueOf(fields.get(9).getText()));
        }catch (NumberFormatException exception){
            notifier.warn("Incorrect year");
            return;
        }
        try{
            house.setNumberOfFloors(Integer.valueOf(fields.get(10).getText()));
        }catch (NumberFormatException exception){
            notifier.warn("Incorrect number of floors");
            return;
        }
        try{
            house.setNumberOfFlatsOnFloor(Long.valueOf(fields.get(11).getText()));
        }catch (NumberFormatException exception){
            notifier.warn("Incorrect number of flats on floor");
            return;
        }
        try{
            house.setNumberOfLifts(Integer.valueOf(fields.get(12).getText()));
        }catch (NumberFormatException exception){
            notifier.warn("Incorrect number of lifts");
            return;
        }

        flat.setHouse(house);

        if(!new InputValidator().validate(flat)){
            notifier.warn("Incorrect flat data");
            return;
        }
        consumer.accept(flat);
        switcher.switchOnPrev();
    }

    public void setConsumer(Consumer<FlatTransferObject> consumer){
        this.consumer = consumer;
    }
    public void setID(int ID){this.ID = ID;}
    public void setCreator(String creator){this.creator = creator; }
    public void setDate(Date date){this.date = date;}

}
