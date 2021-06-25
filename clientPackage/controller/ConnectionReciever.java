package clientPackage.controller;

import clientPackage.view.UserOutput;
import clientPackage.exceptions.ConnectionException;
import common.Pair;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ConnectionReciever {

    private String host;
    private int port;
    private SocketChannel socketChannel;
    private Selector selector;

    public ConnectionReciever(String host, int port){
        this.host = host;
        this.port = port;
    }

    public Pair<Selector,SocketChannel> connectToServer() throws ConnectionException {
        int maxConnectionAttempts = 10;
        int connectionAttempts = 0;
        Pair<Selector,SocketChannel> connection;
        while(connectionAttempts<=maxConnectionAttempts){
            try{
                connectionAttempts++;
                connection = tryToConnect();
                this.selector = connection.first;
                this.socketChannel = connection.second;
                return connection;
            } catch (IOException e){
                UserOutput.println("Trying to reconnect to server...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                continue;
            }
        }
        throw new ConnectionException("Failed to connect to server");
    }
    private Pair<Selector,SocketChannel> tryToConnect() throws IOException{
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(host,port));
            socketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            return new Pair(selector,socketChannel);

    }
    public Selector getSelector(){return this.selector;}
    public SocketChannel getSocketChannel(){return this.socketChannel;}

}
