package clientPackage.controller.listeners;

import clientPackage.controller.*;
import clientPackage.view.UserOutput;
import clientPackage.view.frames.Notifiable;
import clientPackage.view.frames.SignFrame;
import clientPackage.view.frames.TableFrame;
import clientPackage.exceptions.ConnectionException;
import clientPackage.exceptions.EndOfFileException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SignInAction implements ActionListener {
    private JTextField loginField;
    private JPasswordField passwordField;
    private Authorizer authorizer;
    private Switcher switcher;
    private Notifier notifier;
    private NotifierAuthorizer notifierAuthorizer;
    public SignInAction(JTextField loginField, JPasswordField passwordField, Switcher switcher, Notifier notifier, NotifierAuthorizer notifierAuthorizer){
        this.switcher = switcher;
        this.notifier = notifier;
        this.notifierAuthorizer = notifierAuthorizer;
        this.loginField = loginField;
        this.passwordField = passwordField;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        try {
            //String login = loginField.getText();
            //String password = new String(passwordField.getPassword());
            String login = "123";
            String password = "123";
            boolean success = authorizer.signIn(login,password);
            if (success){
                notifier.info("Successfully signed in");
                notifierAuthorizer.authorize(login,password);
                switcher.switchFrame(Frames.TABLE);
            } else {
                notifier.warn("Incorrect login or password");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (EndOfFileException endOfFileException) {
            endOfFileException.printStackTrace();
        } catch (ConnectionException connectionException) {
            connectionException.printStackTrace();
        }

    }
    public void setLogin(JTextField loginField){
        this.loginField = loginField;
    }
    public void setPassword(JPasswordField password){
        this.passwordField = passwordField;
    }
    public void setAuthorizer(Authorizer authorizer){ this. authorizer = authorizer;}
}

