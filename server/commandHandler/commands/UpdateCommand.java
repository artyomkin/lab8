package server.commandHandler.commands;

import common.taskClasses.Flat;
import server.commandHandler.utility.CollectionManager;
import common.*;
import server.commandHandler.utility.ServerOutput;

import java.sql.SQLException;

/**
 * Updates the element by its key
 * **/
public class UpdateCommand extends AbstractCommand{
    private CollectionManager collectionManager;
    public UpdateCommand(CollectionManager collectionManager){
        super("update","updates the element with specified id");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command
     * @return exit status of command
     * **/
    @Override
    public Response execute(Query query){
        System.out.println("Entered update command");
        if (query.getStage().equals(Stage.BEGINNING)) {
            System.out.println("Begin update command");
            Integer key;
            try {
                key = Integer.parseInt(query.getDTOCommand().getArgument());
            } catch (NumberFormatException e){
                ServerOutput.warning("Number format exception");
                return new Response("Incorrect key",true,Instruction.ASK_COMMAND);
            }
            if (!collectionManager.contains(key)) {
                ServerOutput.warning("No flat with key " + key + " found");
                return new Response("No flat with specified key found", true, Instruction.ASK_COMMAND);
            }
            if(!collectionManager.getElementByKey(key).getCreator().equals(query.getLogin())){
                ServerOutput.warning("No permission to update");
                return new Response("You have no permission to update it",true,Instruction.ASK_COMMAND);
            }
            System.out.println("Returning ask flat response");
            return new Response(false,Instruction.ASK_FLAT,Stage.ENDING,query);
        } else {
            System.out.println("Entered ending command");
            Integer key = Integer.parseInt(query.getDTOCommand().getArgument());
            Flat flat = new Flat(query.getDTOFlat());
            flat.setId(key);
            boolean success = false;
            try {
                success = collectionManager.update(key, flat);
            } catch (SQLException throwables) {
                return new Response("Failed to update element in database",true,Instruction.ASK_COMMAND);
            }
            return new Response(success ? "Updating successful" : "Flat wasn't replaced",!success,Instruction.ASK_COMMAND);
        }
    }
}
