package clientPackage.controller.listeners;

import clientPackage.controller.Frames;
import clientPackage.controller.Notifier;
import clientPackage.controller.Switcher;
import clientPackage.view.UserOutput;
import clientPackage.exceptions.ConnectionException;
import clientPackage.exceptions.EndOfFileException;
import clientPackage.controller.Authorizer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SignUpAction implements ActionListener {
    private JTextField loginField;
    private JPasswordField passwordField;
    private JPasswordField repeatPasswordField;
    private Authorizer authorizer;
    private Switcher switcher;
    private Notifier notifier;

    public SignUpAction(Switcher switcher, Notifier notifier){
        this.switcher = switcher;
        this.notifier = notifier;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String login = loginField.getText();
        String password = new String(passwordField.getPassword());
        String repeatPassword = new String(repeatPasswordField.getPassword());
        if(!password.equals(repeatPassword)){
            UserOutput.println("Passwords don't match");
            return;
        }
        try {
            boolean success = authorizer.signUp(login,password);
            if(success){
                notifier.info("Successfully signed up");
                switcher.switchFrame(Frames.SIGN_IN);
            } else {
                notifier.warn("User with this login already exists");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (EndOfFileException endOfFileException) {
            endOfFileException.printStackTrace();
        } catch (ConnectionException connectionException) {
            connectionException.printStackTrace();
        }
    }
    public void setLogin(JTextField login){
        this.loginField = login;
    }
    public void setPassword(JPasswordField password){
        this.passwordField = password;
    }
    public void setRepeatPassword(JPasswordField repeatPassword){
        this.repeatPasswordField = repeatPassword;
    }
    public void setAuthorizer(Authorizer authorizer){this.authorizer = authorizer;}
}
