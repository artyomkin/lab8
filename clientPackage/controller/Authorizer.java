package clientPackage.controller;

import clientPackage.view.UserOutput;
import clientPackage.controller.IO.QuerySender;
import clientPackage.controller.IO.ResponseReader;
import clientPackage.exceptions.ConnectionException;
import clientPackage.exceptions.EndOfFileException;
import common.Instruction;
import common.Query;
import common.Response;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Authorizer {
    private QuerySender querySender;
    private ResponseReader responseReader;
    private String login;
    private String password;
    private Selector selector;
    private SocketChannel socketChannel;

    public Authorizer(QuerySender querySender, ResponseReader responseReader, Selector selector, SocketChannel socketChannel){
        this.querySender = querySender;
        this.responseReader = responseReader;
        this.selector = selector;
        this.socketChannel = socketChannel;
    }

    public boolean signUp(String rawLogin, String rawPassword) throws IOException, EndOfFileException, ConnectionException {
        boolean gotResponse = false;
        while(!gotResponse) {


            int selectionAttempts = 0;
            Set keySet;
            do {
                selector.select(100);
                keySet = selector.selectedKeys();
                selectionAttempts++;
            } while (selectionAttempts < 10 && keySet.size() == 0);

            if (selectionAttempts >= 10) {
                throw new ConnectionException("Connection error");
            }

            Iterator it = keySet.iterator();
            while (it.hasNext()) {
                SelectionKey key = (SelectionKey) it.next();
                it.remove();
                if (key.isWritable()) {
                    UserOutput.println("Signing up...");
                    login = rawLogin;
                    password = rawPassword;
                    Query authQuery = new Query()
                            .setLogin(login)
                            .setPassword(password)
                            .setSignIn(false)
                            .setSignUp(true);
                    querySender.sendQuery(authQuery);

                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                if (key.isReadable()) {
                    Response response = responseReader.getResponse();
                    UserOutput.println(response.getAdditionalInfo());
                    if (response.getInstruction().equals(Instruction.SIGN_IN)) {
                        UserOutput.println("You are signed up!");
                        socketChannel.register(selector, SelectionKey.OP_WRITE);
                        return true;
                    }
                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                    gotResponse = true;
                }
            }
        }
        return false;
    }

    public boolean signIn(String rawLogin, String rawPassword) throws IOException, EndOfFileException, ConnectionException {
        boolean gotResponse = false;
        while(!gotResponse) {

            int selectionAttempts = 0;
            Set keySet;
            do {
                selector.select(100);
                keySet = selector.selectedKeys();
                selectionAttempts++;
            } while (selectionAttempts < 10 && keySet.size() == 0);
            if (selectionAttempts >= 10) {
                throw new ConnectionException("Connection error");
            }
            Iterator it = keySet.iterator();
            Query authQuery;
            while (it.hasNext()) {
                SelectionKey key = (SelectionKey) it.next();
                it.remove();
                if (key.isWritable()) {
                    UserOutput.println("Signing in...");
                    login = rawLogin;
                    password = rawPassword;
                    authQuery = new Query()
                            .setLogin(login)
                            .setPassword(password)
                            .setSignUp(false);

                    querySender.sendQuery(authQuery);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                if (key.isReadable()) {
                    Response response = responseReader.getResponse();
                    UserOutput.println(response.getAdditionalInfo());
                    if (response.getInstruction().equals(Instruction.ASK_COMMAND)) {
                        socketChannel.register(selector, SelectionKey.OP_WRITE);
                        return true;
                    }
                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                    gotResponse = true;
                }
            }
        }
        return false;
    }
    public String getLogin(){
        return this.login;
    }
    public String getPassword(){
        return this.password;
    }
}
