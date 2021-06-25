package clientPackage.view.frames;

import clientPackage.controller.*;
import clientPackage.controller.IO.QuerySender;
import clientPackage.controller.IO.ResponseReader;
import clientPackage.controller.listeners.LocaleChanger;
import clientPackage.controller.listeners.SignInAction;
import clientPackage.controller.listeners.SignUpAction;
import clientPackage.view.buttons.GreenButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI {

    private Rectangle bounds;
    private Authorizer authorizer;
    private QuerySender querySender;
    private ResponseReader responseReader;
    private SignFrame signInFrame;
    private SignFrame signUpFrame;
    private TableFrame tableFrame;
    private VisualFrame visualFrame;
    private ConnectionReciever connectionReciever;


    public GUI(ConnectionReciever connectionReciever,Authorizer authorizer, QuerySender querySender, ResponseReader responseReader){
        this.authorizer = authorizer;
        this.querySender = querySender;
        this.responseReader = responseReader;
        this.connectionReciever = connectionReciever;
    }
    public void createGUI(){

        bounds = new Rectangle(0,0,520,480);
        signInFrame = new SignFrame("Sign in", bounds);
        signUpFrame = new SignFrame("Sign up", bounds);

        JTextField loginSignIn = new JTextField();
        loginSignIn.setToolTipText("Login...");
        signInFrame.addTextField(loginSignIn);

        JPasswordField passwordSignIn = new JPasswordField();
        passwordSignIn.setToolTipText("Password...");
        signInFrame.addPasswordField(passwordSignIn);

        JTextField loginSignUp = new JTextField();
        loginSignUp.setToolTipText("Login...");
        signUpFrame.addTextField(loginSignUp);

        JPasswordField passwordSignUp = new JPasswordField();
        passwordSignUp.setToolTipText("Password...");
        signUpFrame.addPasswordField(passwordSignUp);

        JPasswordField repeatPasswordSignUp = new JPasswordField();
        repeatPasswordSignUp.setToolTipText("Repeat password...");
        signUpFrame.addRepeatPasswordField(repeatPasswordSignUp);

        Notifier notifier = new Notifier();
        notifier.add(Frames.SIGN_IN,signInFrame);
        notifier.add(Frames.SIGN_UP,signUpFrame);
        notifier.setActive(Frames.SIGN_IN);

        Switcher switcher = new Switcher(notifier);
        switcher.add(Frames.SIGN_IN,signInFrame);
        switcher.add(Frames.SIGN_UP,signUpFrame);
        switcher.switchFrame(Frames.SIGN_IN);

        tableFrame = new TableFrame(notifier,"Collection",new Rectangle(0,0,1530,700),querySender,responseReader,switcher);
        CardFrame cardFrame = new CardFrame(notifier,switcher);

        notifier.add(Frames.TABLE,tableFrame);
        notifier.add(Frames.EDITING, cardFrame);

        switcher.add(Frames.TABLE,tableFrame);
        switcher.setCard(cardFrame);

        NotifierAuthorizer notifierAuthorizer = new NotifierAuthorizer();
        notifierAuthorizer.add(Frames.TABLE,tableFrame);

        visualFrame = new VisualFrame("Collection", querySender, responseReader,switcher,notifier);


        switcher.add(Frames.VISUAL,visualFrame);
        notifier.add(Frames.VISUAL,visualFrame);
        notifierAuthorizer.add(Frames.VISUAL,visualFrame);

        tableFrame.setSwitcher(switcher);
        signInFrame.setSwitcher(switcher);
        signUpFrame.setSwitcher(switcher);

        signInFrame.setRef(Frames.SIGN_UP);
        signUpFrame.setRef(Frames.SIGN_IN);

        JButton submitSignIn = new GreenButton("Submit",50,134);
        SignInAction signInAction = new SignInAction(loginSignIn,passwordSignIn,switcher,notifier,notifierAuthorizer);
        signInAction.setAuthorizer(authorizer);
        signInAction.setLogin(loginSignIn);
        signInAction.setPassword(passwordSignIn);
        submitSignIn.addActionListener(signInAction);

        JButton submitSignUp = new GreenButton("Submit",50,134);
        SignUpAction signUpAction = new SignUpAction(switcher,notifier);
        signUpAction.setLogin(loginSignUp);
        signUpAction.setPassword(passwordSignUp);
        signUpAction.setRepeatPassword(repeatPasswordSignUp);
        signUpAction.setAuthorizer(authorizer);
        submitSignUp.addActionListener(signUpAction);

        signInFrame.setSubmitBtn(submitSignIn);
        signUpFrame.setSubmitBtn(submitSignUp);

        LocaleChanger localeChanger = new LocaleChanger(signUpFrame,signInFrame,tableFrame,visualFrame,cardFrame);

        signInFrame.setLocaleChanger(localeChanger);
        signUpFrame.setLocaleChanger(localeChanger);
        tableFrame.setLocaleChanger(localeChanger);
        visualFrame.setLocaleChanger(localeChanger);
        cardFrame.setLocaleChanger(localeChanger);

        signInFrame.setVisible(true);

    }

    public ArrayList<Observer> getObservers(){
        ArrayList<Observer> observers = new ArrayList();
        observers.add(tableFrame);
        observers.add(visualFrame);
        return observers;
    }
}
