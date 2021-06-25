package server.commandHandler.utility;

import common.Instruction;
import common.taskClasses.Flat;

import java.nio.channels.SocketChannel;

public interface Observable {

    void register(SocketChannel socketChannel);
    void delete(SocketChannel socketChannel);
    void notifyObservers(Instruction instruction, Flat flat);

}
