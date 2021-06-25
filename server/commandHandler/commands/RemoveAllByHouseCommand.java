package server.commandHandler.commands;

import common.taskClasses.House;
import server.commandHandler.utility.CollectionManager;

import java.util.ArrayList;

import common.*;
/**
 * Removes all flats that have the same house as specified one
 * **/
public class RemoveAllByHouseCommand extends AbstractCommand{
    private CollectionManager collectionManager;
    public RemoveAllByHouseCommand(CollectionManager collectionManager){
        super("remove_all_by_house","remove all elements house of which equals to specified one");
        this.collectionManager = collectionManager;
    }
    /**
     * Executes the command
     * @return exit status of command
     * **/
    public Response execute(Query query){
        if (query.getStage().equals(Stage.BEGINNING)){
            return new Response(false,Instruction.ASK_HOUSE, Stage.ENDING, query);
        } else {
            ArrayList<Integer> flatsToRemove = new ArrayList<>();
            collectionManager.getStream()
                    .filter(flat->flat.getHouse().equals(new House(query.getDTOHouse())))
                    .filter(flat->flat.getCreator().equals(query.getLogin()))
                    .forEach(flat->flatsToRemove.add(flat.getID()));
            long removed = flatsToRemove.stream()
                    .map(ID->collectionManager.remove(ID))
                    .count();
            return new Response("Removed "+ removed + "elements",false, Instruction.ASK_COMMAND);
        }

    }
}
