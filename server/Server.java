package server;

import server.commandHandler.utility.CollectionManager;
import server.commandHandler.utility.CommandManager;
import server.commandHandler.utility.DataBaseManager;
import server.commandHandler.utility.Notifier;
import server.connectionReciever.ClientConnection;
import server.connectionReciever.DatabaseConnection;

import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class Server {

    private ClientConnection clientConnection;
    private CollectionManager collectionManager;
    private Notifier notifier;

    public Server(CollectionManager collectionManager, Notifier notifier) {
        this.clientConnection = new ClientConnection(2232);
        this.collectionManager = collectionManager;
        this.notifier = notifier;
    }

    public void run(ForkJoinPool forkJoinPool,
                    ExecutorService fixedThreadPool,
                    Connection dataBaseConnection,
                    DataBaseManager dataBaseManager) {
         while(true){
            SocketChannel communicationChannel = clientConnection.getConnection();
            SocketChannel notificationChannel = clientConnection.getConnection();
            notifier.register(notificationChannel);
            new Thread(new QueryHandler(collectionManager, fixedThreadPool, forkJoinPool, communicationChannel, dataBaseConnection, dataBaseManager, notifier)).start();
        }
    }
}
