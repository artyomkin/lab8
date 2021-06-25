package clientPackage;

import clientPackage.controller.*;
import clientPackage.controller.Observer;
import clientPackage.view.frames.GUI;
import clientPackage.exceptions.ConnectionException;
import clientPackage.controller.IO.QuerySender;
import clientPackage.controller.IO.ResponseReader;
import clientPackage.view.UserOutput;
import com.sun.jdi.connect.spi.ClosedConnectionException;
import common.*;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;


public class Client /*implements Runnable*/{

    private InputValidator inputValidator;
    private boolean isRunning;
    private Authorizer authorizer;
    private Stack<String> callStack;
    private QuerySender querySender;
    private ResponseReader responseReader;
    private ResponseReader notificationListener;
    private SocketChannel communicationChannel;
    private SocketChannel notificationChannel;
    private Selector communicationSelector;
    private Selector notificationSelector;
    private ConnectionReciever connectionReciever;

    public Client(String host, int port){
        this.inputValidator = new InputValidator();
        this.isRunning = true;
        this.callStack = new Stack();

        try {
            connectionReciever = new ConnectionReciever(host,port);
            Pair<Selector, SocketChannel> communicationConnection = connectionReciever.connectToServer();
            communicationSelector = communicationConnection.first;
            communicationChannel = communicationConnection.second;
            communicationChannel.register(communicationSelector, SelectionKey.OP_WRITE);
            this.querySender = new QuerySender(communicationChannel);
            this.responseReader = new ResponseReader(communicationChannel);

            Pair<Selector, SocketChannel> notificationConnection = connectionReciever.connectToServer();
            notificationSelector = notificationConnection.first;
            notificationChannel = notificationConnection.second;
            notificationChannel.register(notificationSelector,SelectionKey.OP_READ);
            this.notificationListener = new ResponseReader(notificationChannel);

        } catch (ClosedChannelException | ConnectionException e) {
            UserOutput.println("Connecting with server failed");
        }
        authorizer = new Authorizer(querySender,responseReader,communicationSelector,communicationChannel);
        GUI gui = new GUI(connectionReciever,authorizer,querySender,responseReader);
        gui.createGUI();

        NotificationListeningTask notificationListeningTask = new NotificationListeningTask(gui.getObservers(),notificationListener,notificationSelector);
        new Thread(notificationListeningTask).start();

    }

    public void run(){
        run("");
    }

    public void run(String filepath) {

       /* try {
            Query query = null;
            //int cnt = 0;
            while (isRunning) {
                    int selectionAttempts = 0;
                    Set keySet;
                    do{
                        selector.select(100);
                        keySet = selector.selectedKeys();
                        selectionAttempts++;
                    }while(selectionAttempts<10 && keySet.size()==0);

                    if (selectionAttempts>=10){
                        UserOutput.println("Connection error");
                        return false;
                    }

                    Iterator it = keySet.iterator();
                    while (it.hasNext()) {
                        SelectionKey key = (SelectionKey) it.next();
                        it.remove();
                        if (key.isWritable()) {
                            if (query == null){


                                query = new Query()
                                        .setStage(Stage.BEGINNING)
                                        .setDTOCommand(asker.askValidatedCommand())
                                        //.setDTOCommand(new CommandTransferObject().setCommand("execute_script").setArgument("C:\\Users\\User\\IdeaProjects\\Client\\src\\clientPackage\\inputScript2.txt"))
                                        .setLogin(login)
                                        .setPassword(password);

                            }
                            querySender.sendQuery(query, socketChannel);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        }
                        if (key.isReadable()) {
                            Response response = responseReader.getResponse(socketChannel);
                            if (response.getInstruction() != Instruction.SCRIPT && !response.getContent().isEmpty()) {
                                UserOutput.println(response.getContent());
                            }
                            query = handleResponse(response, socketChannel, selector);
                            if(query!=null){
                                query.setLogin(login).setPassword(password);
                            } else {
                                socketChannel.close();
                                return false;
                            }

                            socketChannel.register(selector, SelectionKey.OP_WRITE);

                        }
                    }
                    //cnt++;
            }
            } catch(IOException e){
                UserOutput.println("Selector exception");
                return false;
            } catch(EndOfFileException e){
                return true;
            } catch(ConnectionException e){
                return false;
            }
            return true;
        }*/

   /* public Query handleResponse (Response response, SocketChannel socketChannel, Selector selector) throws EndOfFileException {

        Query query = new Query();
        query.setDTOCommand(response.getQuery().getDTOCommand());
        query.setStage(response.getStage());

        switch (response.getInstruction()){
            case ASK_COMMAND: {
                CommandTransferObject command = asker.askValidatedCommand();
                if(command!=null) return query.setDTOCommand(command);
                else return null;
            }
            case ASK_FLAT: {
                FlatTransferObject flat = asker.askValidatedFlat();

                if(flat!=null) return query.setDTOFlat(flat);
                else return null;
            }
            case ASK_COORDINATES: {
                CoordinatesTransferObject coords = asker.askValidatedCoordinates();
                if(coords!=null) return query.setDTOCoordinates(coords);
                else return null;
            }
            case ASK_HOUSE: {
                HouseTransferObject house = asker.askValidatedHouse();
                if(house!=null) return query.setDTOHouse(house);
                else return null;
            }
            case SCRIPT:{
                boolean recursion =
                        callStack.stream()
                            .anyMatch(filepath -> filepath.equals(response.getContent()));
                if (recursion){
                    UserOutput.println("Failed to execute because of recursion");
                    return null;
                }
                callStack.push(response.getContent());
                try {
                    socketChannel.register(selector,SelectionKey.OP_WRITE);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
                Asker saveAsker = asker;
                if(!run(response.getContent())){
                    isRunning = false;
                }
                callStack.pop();
                asker = saveAsker;
                if(isRunning){
                    return query.setDTOCommand(asker.askValidatedCommand());
                } else {
                    return null;
                }
            }
            case EXIT:{
                isRunning = false;
                return new Query();
            }
            case SIGN_IN:{
                try {
                     while(!authorizer.signIn(selector,socketChannel));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ConnectionException e) {
                    e.printStackTrace();
                }
            }
            case SIGN_UP:{
                try{
                   while(!authorizer.signUp(selector,socketChannel));
                } catch (IOException e){
                    e.printStackTrace();
                } catch (ConnectionException e){
                    e.printStackTrace();
                }
            }
            default: return null;
        }*/

    }

}

