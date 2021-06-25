package clientPackage.controller;

import clientPackage.controller.IO.QuerySender;
import clientPackage.controller.IO.ResponseReader;
import clientPackage.controller.listeners.CommandAction;
import clientPackage.exceptions.ConnectionException;
import common.Instruction;
import common.Query;
import common.Response;
import common.Stage;
import common.dataTransferObjects.FlatTransferObject;
import common.taskClasses.Flat;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

public class ResponseHandler {
    private Notifier notifier;
    private Switcher switcher;
    private QuerySender querySender;
    private ResponseReader responseReader;
    public ResponseHandler(QuerySender querySender, ResponseReader responseReader, Notifier notifier, Switcher switcher){
        this.notifier = notifier;
        this.switcher = switcher;
        this.querySender = querySender;
        this.responseReader = responseReader;
    }

    public void handleResponse(Response response, Observer observer, String login, String password){
        if(response.failed()){
            notifier.warn(response.getAdditionalInfo());
        } else {
            if(response.getInstruction().equals(Instruction.EXIT)){
                System.exit(0);
            }
            else if (response.getInstruction().equals(Instruction.ASK_FLAT)){
                Query query = new Query();
                query.setLogin(login).setPassword(password).setDTOCommand(response.getQuery().getDTOCommand());
                Consumer<FlatTransferObject> consumer = (flat)->{
                    query.setDTOFlat(flat);
                    query.setStage(Stage.ENDING);
                    flat.setCreator(login);
                    querySender.sendQuery(query);
                    try {
                        handleResponse(responseReader.getResponse(),observer,login,password);
                    } catch (ConnectionException e) {
                        e.printStackTrace();
                    }
                };
                switcher.switchOnCard(consumer);

            }else{
                if(response.getAdditionalInfo()==null || response.getAdditionalInfo().isEmpty()){
                    notifier.info("Executed successfully");
                } else {
                    notifier.info(response.getAdditionalInfo());
                }
                if (!response.getCollectionInfo().isEmpty()){
                    ArrayList<Flat> flats = new ArrayList<>();
                    response.getCollectionInfo().stream()
                            .forEach(flatDTO -> flats.add(new Flat(flatDTO)));
                    observer.update(flats);
                }
            }

        }
    }
}
