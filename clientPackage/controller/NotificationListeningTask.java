package clientPackage.controller;

import clientPackage.controller.IO.ResponseReader;
import clientPackage.exceptions.ConnectionException;
import common.Response;
import common.taskClasses.Flat;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class NotificationListeningTask implements Runnable{

    private ArrayList<Observer> observers;
    private ResponseReader notificationListener;
    private Selector notificationSelector;

    public NotificationListeningTask(ArrayList<Observer> observers, ResponseReader notificationListener, Selector notificationSelector){
        this.observers = observers;
        this.notificationListener = notificationListener;
        this.notificationSelector = notificationSelector;
    }

    public void run(){

        try {
            while(true){
                notificationSelector.select();
                Set keySet = notificationSelector.selectedKeys();
                Iterator it = keySet.iterator();
                while(it.hasNext()){
                    SelectionKey key = (SelectionKey) it.next();
                    if(key.isReadable()){
                        Response notification = notificationListener.getResponse();
                        for (Observer observer : observers){
                            Flat flat = new Flat(notification.getCollectionInfo().get(0));
                            observer.update(notification.getInstruction(),flat);
                        }
                    }
                }
            }

        } catch (ConnectionException | IOException e) {
            e.printStackTrace();
        }


    }

}
