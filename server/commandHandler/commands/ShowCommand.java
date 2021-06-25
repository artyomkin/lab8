package server.commandHandler.commands;

import common.dataTransferObjects.FlatTransferObject;
import common.taskClasses.Flat;
import server.commandHandler.utility.CollectionManager;
import common.*;

import java.util.ArrayList;

/**
 * Prints all elements in collection
 * **/
public class ShowCommand extends AbstractCommand{
    private CollectionManager collectionManager;
    public ShowCommand(CollectionManager collectionManager){
        super("show","prints all elements");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command
     * @return exit status of command
     * **/
    @Override
    public Response execute(Query query) {
        if (collectionManager.getLength()==0){
            return new Response("Collection is empty", false, Instruction.ASK_COMMAND);
        } else{
            ArrayList<FlatTransferObject> content = new ArrayList<>();

            collectionManager
                    .getStream()
                    .forEach(flat->content.add(new FlatTransferObject(flat)));
            Response response = new Response(content, false, Instruction.ASK_COMMAND);
            System.out.println("Sent "+content.size());
            return response;
        }
    }

}
