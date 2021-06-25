package clientPackage.controller.IO;

import common.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class QuerySender {
    private SocketChannel socketChannel;
    public QuerySender(SocketChannel socketChannel){
        this.socketChannel = socketChannel;
    }
    public boolean sendQuery(Query query){
        try{
            byte[] bytes = Serializer.serialize(query);
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            socketChannel.write(byteBuffer);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
