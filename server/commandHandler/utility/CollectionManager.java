package server.commandHandler.utility;

import common.Instruction;
import common.taskClasses.Flat;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Manages the main collection
 * **/
public class CollectionManager{

    private Map<Integer, Flat> collection;
    private Date date;
    private int length;
    private DataBaseManager dataBaseManager;
    private Notifier notifier;
    public CollectionManager(DataBaseManager dataBaseManager, Notifier notifier) throws SQLException {
        this.dataBaseManager = dataBaseManager;
        collection = Collections.synchronizedMap(dataBaseManager.loadCollection());
        date = new Date();
        length = collection.size();
        this.notifier = notifier;
    }
    public boolean update(Integer key, Flat flat) throws SQLException {
        flat.setId(key);
        if(!dataBaseManager.update(key, flat)) return false;
        int oldLength = collection.size();
        collection.put(key,flat);
        length += collection.size()-oldLength;
        notifier.notifyObservers(Instruction.UPDATE_ELEMENT,flat);
        return true;
    }

    public boolean contains(Integer key){
        return collection.keySet().contains(key);
    }
    /**
     * Removes the element by its key
     * @return exit status of removal
     * @param key
     * **/
    public boolean remove(Integer key){
        if(!dataBaseManager.remove(key)) return false;

        synchronized (collection){
            if (collection.isEmpty() || !collection.containsKey(key)){
                return false;
            }
            collection.remove(key);
        }
        length--;
        Flat flat = new Flat();
        flat.setId(key);
        notifier.notifyObservers(Instruction.REMOVE_ELEMENT,flat);
        return true;

    }
    /**
     * Returns flat by its key from collection
     * @return flat
     * @param key
     * **/
    public Flat getElementByKey(Integer key){
        return collection.get(key);
    }

    public Class getCollectionClass(){
        return collection.getClass();
    }
    /**
     * Returns the date when collection was created
     * @return creation date
     * **/
    public Date getInitializationDate(){ return date; };
    /**
     * Returns the lenght of collection
     * @return length
     * **/
    public int getLength(){ return length; }

    public Stream<Flat> getStream(){
        List<Flat> flats = this.collection.values().stream()
                .collect(Collectors.toList());
        flats.sort(Flat::compareTo);
        return flats.stream();
    }

}
