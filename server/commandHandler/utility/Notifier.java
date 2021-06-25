package server.commandHandler.utility;

import common.Instruction;
import common.Response;
import common.dataTransferObjects.FlatTransferObject;
import common.taskClasses.Flat;
import server.responseSender.SendingResponseTask;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class Notifier implements Observable{
    private ArrayList<SocketChannel> observers;
    private ExecutorService fixedThreadPool;

    public Notifier(ExecutorService fixedThreadPool){
        observers = new ArrayList<>();
        this.fixedThreadPool = fixedThreadPool;
    }

    @Override
    public void register(SocketChannel socketChannel) {
        observers.add(socketChannel);
    }

    @Override
    public void delete(SocketChannel socketChannel) {
        observers.remove(socketChannel);
    }

    @Override
    public void notifyObservers(Instruction instruction, Flat flat) {
        for (SocketChannel socketChannel : observers){
            ArrayList<FlatTransferObject> changes = new ArrayList<>();
            changes.add(new FlatTransferObject(flat));

            Response response = new Response(changes,false,instruction);
            SendingResponseTask sendingResponseTask = new SendingResponseTask(response,socketChannel);
            System.out.println("Notifier submitted new sending response task");
            fixedThreadPool.submit(sendingResponseTask);
        }


    }
}
