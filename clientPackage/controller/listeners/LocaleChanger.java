package clientPackage.controller.listeners;

import clientPackage.view.frames.CardFrame;
import clientPackage.view.frames.SignFrame;
import clientPackage.view.frames.TableFrame;
import clientPackage.view.frames.VisualFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleChanger implements ActionListener {
    private SignFrame signInFrame;
    private SignFrame signUpFrame;
    private TableFrame tableFrame;
    private VisualFrame visualFrame;
    private CardFrame cardFrame;

    public LocaleChanger(SignFrame signUpFrame, SignFrame signInFrame, TableFrame tableFrame, VisualFrame visualFrame, CardFrame cardFrame){
        this.cardFrame = cardFrame;
        this.signInFrame = signInFrame;
        this.signUpFrame = signUpFrame;
        this.tableFrame = tableFrame;
        this.visualFrame = visualFrame;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        ResourceBundle bundle;
        String actionCommand = e.getActionCommand();
        System.out.println(actionCommand);
        if (actionCommand.equals("bg")){
            bundle = ResourceBundle.getBundle("clientPackage.resources.Resource_bg",new Locale(actionCommand));
        } else if (actionCommand.equals("tr")) {
            bundle = ResourceBundle.getBundle("clientPackage.resources.Resource_tr", new Locale("tr"));
        } else if (actionCommand.equals("en_IE")){
            bundle = ResourceBundle.getBundle("clientPackage.resources.Resource_en_IE", new Locale("en","IE"));
        } else {
            bundle = ResourceBundle.getBundle("clientPackage.resources.Resource");
        }
        updateAll(bundle);
    }

    private void updateAll(ResourceBundle bundle){
        signInFrame.headingLabel.setText(bundle.getString(signInFrame.heading));
        signInFrame.submitBtn.setText(bundle.getString(signInFrame.submitBtnText));
        signInFrame.ref.setText(bundle.getString(signInFrame.refText));
        signInFrame.loginField.setToolTipText(bundle.getString("Login"));
        signInFrame.passwordField.setToolTipText(bundle.getString("Password"));
        signInFrame.headerPanel.exitButton.setText(bundle.getString(signInFrame.exitBtnText));

        signUpFrame.headingLabel.setText(bundle.getString(signUpFrame.heading));
        signUpFrame.submitBtn.setText(bundle.getString(signUpFrame.submitBtnText));
        signUpFrame.ref.setText(bundle.getString(signUpFrame.refText));
        signUpFrame.loginField.setToolTipText(bundle.getString("Login"));
        signUpFrame.passwordField.setToolTipText(bundle.getString("Password"));
        signUpFrame.repeatPasswordField.setToolTipText(bundle.getString("Repeat password"));
        signUpFrame.headerPanel.exitButton.setText(bundle.getString(signUpFrame.exitBtnText));


    }


}
