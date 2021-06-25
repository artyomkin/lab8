package clientPackage.view.frames;

import clientPackage.controller.listeners.LocaleChanger;
import clientPackage.view.buttons.LocalizeButton;
import clientPackage.view.buttons.NoBGButton;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel {

    protected JPanel header;
    protected JLabel login;
    public JButton exitButton;
    protected JButton rusFlag;
    protected JButton bulFlag;
    protected JButton turFlag;
    protected JButton ukFlag;
    private LocaleChanger localeChanger;
    private JPanel countries;

    public HeaderPanel(String loginStr){
            header = new JPanel();
            header.setBackground(Color.WHITE);
            header.setLayout(new BoxLayout(header,BoxLayout.X_AXIS));
            exitButton = new NoBGButton("Exit",true).getButton();
            countries = new JPanel();
            countries.setLayout(new GridLayout(1,0,10,10));
            countries.setMaximumSize(new Dimension(145,25));
            countries.setBackground(Color.WHITE);
            rusFlag = new LocalizeButton("C:\\Users\\User\\IdeaProjects\\Client\\src\\clientPackage\\view\\icons\\russia.png",25,25).getButton();
            rusFlag.setActionCommand("ru");
            bulFlag = new LocalizeButton("C:\\Users\\User\\IdeaProjects\\Client\\src\\clientPackage\\view\\icons\\bulgaria.png",25,25).getButton();
            bulFlag.setActionCommand("bg");
            turFlag = new LocalizeButton("C:\\Users\\User\\IdeaProjects\\Client\\src\\clientPackage\\view\\icons\\turkey.png",25,25).getButton();
            turFlag.setActionCommand("tr");
            ukFlag = new LocalizeButton("C:\\Users\\User\\IdeaProjects\\Client\\src\\clientPackage\\view\\icons\\uk.png",25,25).getButton();
            ukFlag.setActionCommand("en_IE");


            rusFlag.setSize(25,25);
            bulFlag.setSize(25,25);
            turFlag.setSize(25,25);
            ukFlag.setSize(25,25);
            countries.add(rusFlag);
            countries.add(bulFlag);
            countries.add(turFlag);
            countries.add(ukFlag);
            header.add(countries);
            header.add(Box.createHorizontalGlue());
            login = new JLabel(loginStr);
            header.add(login);
            header.add(exitButton);
    }
    public JPanel getHeader(){
        return header;
    }

    public void setLocaleChanger(LocaleChanger localeChanger){
        this.localeChanger = localeChanger;
        for (Component c : countries.getComponents()){
            JButton btn = (JButton) c;
            btn.addActionListener(localeChanger);
        }
    }
}
