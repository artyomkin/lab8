package server.commandHandler.commands;

import common.taskClasses.Flat;
import server.commandHandler.utility.CollectionManager;

import java.util.ArrayList;

import common.*;
/**
 * Removes all flats that are more than specified one
 * **/
public class RemoveGreaterCommand extends AbstractCommand{
    private CollectionManager collectionManager;
    public RemoveGreaterCommand(CollectionManager collectionManager){
        super("remove_greater","removes all elements that more than specified");
        this.collectionManager = collectionManager;
    }
    /**
     * Executes the command
     * @return exit status of command
     * **/
    @Override
    public Response execute(Query query){
        if (query.getStage().equals(Stage.BEGINNING)){
            return new Response(false,Instruction.ASK_FLAT, Stage.ENDING, query);
        } else {
            ArrayList<Integer> flatsToRemove = new ArrayList<>();
            collectionManager.getStream()
                    .filter(flat -> flat.compareTo(new Flat(query.getDTOFlat()))>0)
                    .filter(flat -> flat.getCreator().equals(query.getLogin()))
                    .forEach(flat -> flatsToRemove.add(flat.getID()));
            long removed = flatsToRemove.stream()
                    .map(ID->collectionManager.remove(ID))
                    .count();
            return new Response("Removed " + removed + " elements",false, Instruction.ASK_COMMAND);
        }

    }
}
