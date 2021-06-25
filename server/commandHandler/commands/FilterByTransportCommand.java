package server.commandHandler.commands;

import common.dataTransferObjects.FlatTransferObject;
import common.taskClasses.Flat;
import common.taskClasses.Transport;
import server.commandHandler.utility.CollectionManager;
import common.Instruction;
import common.Query;
import common.Response;

import java.util.ArrayList;

/**
 * Prints all elements that have the same transport as specified one has
 * **/
public class FilterByTransportCommand extends AbstractCommand{
    private CollectionManager collectionManager;
    public FilterByTransportCommand(CollectionManager collectionManager){
        super("filter_by_transport", "print all elements value transport of which equals to specified one");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command
     * @return exit status of command
     * **/
    @Override
    public Response execute(Query query) {
        Transport validatedTransport;
        try{
            validatedTransport = Transport.valueOf(query.getDTOCommand().getArgument());
        } catch (IllegalArgumentException e){
            return new Response("Incorrect transport name", true, Instruction.ASK_COMMAND);
        }
        ArrayList<FlatTransferObject> content = new ArrayList<>();
        collectionManager.getStream()
                .filter(flat->flat.getTransport().equals(validatedTransport))
                .forEachOrdered(flat->content.add(new FlatTransferObject(flat)));
        if(content.isEmpty()){
            return new Response("No flat with specified transport found",false,Instruction.ASK_COMMAND);
        }else{
            return new Response(content, false, Instruction.ASK_COMMAND);
        }

    }
}
