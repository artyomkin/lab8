package server;

import server.commandHandler.utility.CollectionManager;
import server.commandHandler.utility.DataBaseManager;
import server.commandHandler.utility.Notifier;
import server.commandHandler.utility.ServerOutput;
import server.connectionReciever.DatabaseConnection;

import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;


public class MainServer {
    public static void main(String[] args){
        Connection dataBaseConnection = DatabaseConnection.getConnection();

        if (dataBaseConnection==null) return;

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Notifier notifier = new Notifier(fixedThreadPool);
        CollectionManager collectionManager;
        DataBaseManager dataBaseManager = new DataBaseManager(dataBaseConnection);
        try {
            collectionManager = new CollectionManager(dataBaseManager,notifier);
        } catch (SQLException throwables) {
            ServerOutput.warning("Failed to load collection");
            return;
        }

        new Server(collectionManager, notifier).run(forkJoinPool,fixedThreadPool, dataBaseConnection, dataBaseManager);

    }
    
}
