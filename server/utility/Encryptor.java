package server.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryptor {

    private static Encryptor INSTANCE;
    private MessageDigest md;

    public static Encryptor getINSTANCE() throws NoSuchAlgorithmException {
        if(INSTANCE==null) {
            INSTANCE = new Encryptor();
            return INSTANCE;
        }
        else return INSTANCE;
    }

    private Encryptor() throws NoSuchAlgorithmException {
        md = MessageDigest.getInstance("MD2");
    }

    public String encrypt(String s){
        return new String(md.digest(s.getBytes()));
    }

}
