package clientPackage.controller.listeners;

import clientPackage.controller.*;
import clientPackage.controller.IO.QuerySender;
import clientPackage.controller.IO.ResponseReader;
import clientPackage.exceptions.ConnectionException;
import clientPackage.exceptions.IncorrectInputException;
import common.Query;
import common.Response;
import common.dataTransferObjects.CommandTransferObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class CommandAction implements ActionListener {
    private QuerySender querySender;
    private ResponseReader responseReader;
    private JTextField commandField;
    private String login;
    private String password;
    private Notifier notifier;
    private Switcher switcher;
    private Observer observer;
    private ResponseHandler responseHandler;

    public CommandAction(
            QuerySender querySender,
            ResponseReader responseReader,
            JTextField commandField,
            Switcher switcher,
            Notifier notifier,
            Observer observer){
        this.querySender = querySender;
        this.responseReader = responseReader;
        this.commandField = commandField;
        this.switcher = switcher;
        this.notifier = notifier;
        this.observer = observer;
        this.responseHandler = new ResponseHandler(querySender,responseReader,notifier,switcher);
    }

    public void actionPerformed(ActionEvent e){
        Query query;
        try{
            query = generateQuery(commandField.getText());
        } catch (IncorrectInputException exception){
            return;
        }
        query.setLogin(login);
        query.setPassword(password);
        if(!querySender.sendQuery(query)){
            notifier.warn("The selector is not writable");
        }
        Response response;
        try {
            response = responseReader.getResponse();
            responseHandler.handleResponse(response, observer, login, password);
        } catch (ConnectionException connectionException) {
            switcher.switchFrame(Frames.SIGN_IN);
            notifier.warn("Connection with server was disrupted or selector is not readable");
        }

    }

    public Query generateQuery(String command) throws IncorrectInputException {
        if(command.isEmpty()){
            notifier.warn("Enter command");
            throw new IncorrectInputException();
        }
        Query query = new Query();
        CommandTransferObject CTO = new CommandTransferObject();
        String[] res = (command).split(" ");

        CTO.setCommand(res[0]);
        CTO.setArgument(res.length>1? res[1] : " ");
        if(!new InputValidator().validate(CTO)){
            notifier.warn("Incorrect command or argument");
            throw new IncorrectInputException();
        }
        query.setDTOCommand(CTO);
        return query;
    }

    public void setLogin(String login){
        this.login = login;
    }
    public void setPassword(String password){
        this.password = password;
    }

}
