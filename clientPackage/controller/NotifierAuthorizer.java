package clientPackage.controller;

import clientPackage.view.frames.Authorizable;
import clientPackage.view.frames.HeaderPanel;

import java.util.HashMap;

public class NotifierAuthorizer {
    private HashMap<Frames,Authorizable> authorizables;

    public NotifierAuthorizer(){
        authorizables = new HashMap<>();
    }

    public void add(Frames frame, Authorizable authorizable){
        authorizables.put(frame,authorizable);
    }

    public void authorize(String login, String password){

        for(Authorizable authorizable : authorizables.values()){
            authorizable.setLogin(login);
            authorizable.setPassword(password);
            authorizable.initData();
        }
    }
}
