package server.commandHandler.commands;

import common.Stage;
import common.taskClasses.Flat;
import server.commandHandler.utility.CollectionManager;
import common.Instruction;
import common.Query;
import common.Response;
import server.commandHandler.utility.ServerOutput;

import java.sql.SQLException;

/**
 * Inserts new element with specified key into collection
 * **/
public class InsertCommand extends AbstractCommand{

    private CollectionManager collectionManager;
    public InsertCommand(CollectionManager collectionManager){
        super("insert", "insert new element with specified key");
        this.collectionManager = collectionManager;
    }
    /**
     * Executes the command
     * @return exit status of command
     * **/
    @Override
    public Response execute(Query query){
        if (query.getStage().equals(Stage.BEGINNING)){
            if (query.getDTOCommand().getArgument().isEmpty()){
                ServerOutput.warning("Key for insertion not specified");
                return new Response("Specify a key",true,Instruction.ASK_COMMAND);
            }
            if (collectionManager.contains(Integer.parseInt(query.getDTOCommand().getArgument()))){
                ServerOutput.warning("Key for insertion already exists");
                return new Response("Flat with specified key already exists", true, Instruction.ASK_COMMAND);
            }
            ServerOutput.info("Key accepted. Waiting for flat.");
            return new Response(false, Instruction.ASK_FLAT, Stage.ENDING, query);
        } else {
            Flat flat = new Flat(query.getDTOFlat());
            flat.setId(Integer.parseInt(query.getDTOCommand().getArgument()));
            System.out.println(query.getLogin());
            flat.setCreator(query.getLogin());
            try {
                collectionManager.update(Integer.parseInt(query.getDTOCommand().getArgument()),flat);
            } catch (SQLException throwables) {
                return new Response("Failed to insert into database",true,Instruction.ASK_COMMAND);
            }
            ServerOutput.info("Insertion successful");
            return new Response("Insertion successful",false,Instruction.ASK_COMMAND);
        }
    }
}
