package clientPackage.view.frames;

import clientPackage.controller.Frames;
import clientPackage.controller.Switcher;
import clientPackage.controller.listeners.LocaleChanger;
import clientPackage.view.buttons.NoBGButton;
import clientPackage.view.buttons.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class SignFrame implements Notifiable, Switchable{
    protected JFrame frame;
    protected JPanel contentWrapper;
    protected JPanel content;
    protected JPanel contentTop;
    protected JPanel contentHeading;
    protected JPanel contentRef;
    protected JPanel contentBtn;
    protected JPanel notification;
    protected JLabel notificationLabel;
    protected Rectangle bounds;
    protected Switcher switcher;
    public HeaderPanel headerPanel;

    public JLabel headingLabel;

    public String heading;

    public JButton submitBtn;
    public String submitBtnText;

    public String refText;
    public JButton ref;

    public JTextField loginField;
    public JPasswordField passwordField;
    public JPasswordField repeatPasswordField;

    public String exitBtnText;
    public AtomicReference<ResourceBundle> bundle;

    private LocaleChanger localeChanger;
    public SignFrame(String heading, Rectangle bounds){
        this.heading = heading;
        bundle = new AtomicReference<>(ResourceBundle.getBundle("clientPackage.resources.Resource"));
        heading = bundle.get().getString(heading);
        this.bounds = bounds;
        frame = new JFrame(heading);
        this.frame.setResizable(false);
        frame.setBounds(bounds);
        frame.setBackground(Color.WHITE);
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentWrapper = new JPanel();
        contentWrapper.setBackground(Color.WHITE);
        contentWrapper.setLayout(new BoxLayout(contentWrapper, BoxLayout.X_AXIS));
        contentWrapper.setBorder(BorderFactory.createEmptyBorder(40,110,0,110));

        content = new JPanel();
        content.setBackground(Color.WHITE);
        GridLayout contentLayout = new GridLayout(6,1);
        contentLayout.setVgap(25);
        content.setLayout(contentLayout);

        contentTop = new JPanel();
        contentTop.setBackground(Color.WHITE);
        contentTop.setLayout(new BorderLayout());

        contentHeading = new JPanel();
        contentHeading.setBackground(Color.WHITE);
        contentHeading.setLayout(new GridLayout(2,1));
        headingLabel = new JLabel(heading);
        headingLabel.setFont(new Font("Arial",Font.PLAIN,19));
        contentHeading.add(headingLabel);

        contentRef = new JPanel();
        contentRef.setBackground(Color.WHITE);
        contentRef.setLayout(new GridLayout(2,1));

        contentBtn = new JPanel();
        contentBtn.setBackground(Color.WHITE);
        contentBtn.setLayout(new BoxLayout(contentBtn,BoxLayout.X_AXIS));

        notification = new JPanel();
        notification.setBackground(Color.WHITE);
        notification.setLayout(new BorderLayout());
        notificationLabel = new JLabel("");
        notification.add(notificationLabel,BorderLayout.EAST);

        contentTop.add(contentRef,BorderLayout.EAST);
        contentTop.add(contentHeading,BorderLayout.WEST);
        content.add(contentTop);
        content.add(contentBtn);
        content.add(notification);
        contentWrapper.add(content);

        headerPanel = new HeaderPanel("");
        headerPanel.exitButton.addActionListener((e)->System.exit(0));
        exitBtnText = headerPanel.exitButton.getText();
        headerPanel.exitButton.setText(bundle.get().getString(exitBtnText));
        frame.add(headerPanel.getHeader(), BorderLayout.NORTH);
        frame.add(contentWrapper);

    }
    public void setVisible(boolean b){
        frame.setVisible(b);
    }

    public void setSubmitBtn(JButton submitBtn){
        submitBtnText = submitBtn.getText();
        submitBtn.setText(bundle.get().getString(submitBtnText));
        this.submitBtn = submitBtn;
        contentBtn.add(Box.createHorizontalGlue());
        contentBtn.add(submitBtn);
    }
    public void addTextField(JTextField textField){
        textField.setPreferredSize(new Dimension(280,40));
        textField.setSize(new Dimension(280,40));
        textField.setMaximumSize(new Dimension(280,40));
        textField.setBorder(new RoundedBorder(10));
        textField.setToolTipText(bundle.get().getString("Login"));
        this.loginField = textField;
        content.add(loginField,1);
    }
    public void addPasswordField(JPasswordField jPasswordField){
        jPasswordField.setPreferredSize(new Dimension(280,40));
        jPasswordField.setSize(new Dimension(280,40));
        jPasswordField.setMaximumSize(new Dimension(280,40));
        jPasswordField.setBorder(new RoundedBorder(10));
        jPasswordField.setToolTipText(bundle.get().getString("Password"));
        this.passwordField = jPasswordField;
        content.add(passwordField,2);
    }
    public void addRepeatPasswordField(JPasswordField jPasswordField){
        jPasswordField.setPreferredSize(new Dimension(280,40));
        jPasswordField.setSize(new Dimension(280,40));
        jPasswordField.setMaximumSize(new Dimension(280,40));
        jPasswordField.setBorder(new RoundedBorder(10));
        jPasswordField.setToolTipText(bundle.get().getString("Password"));
        this.repeatPasswordField = jPasswordField;
        content.add(passwordField,2);
    }
    public void warn(String msg){
        notificationLabel.setForeground(Color.RED);
        notificationLabel.setText(msg);
        notificationLabel.updateUI();
    }
    public void info(String msg){
        notificationLabel.setForeground(Color.GREEN);
        notificationLabel.setText(msg);
        notificationLabel.updateUI();
    }

    public void setSwitcher(Switcher switcher){
        this.switcher = switcher;
    }

    public void setRef(Frames frame){
        if (frame.equals(Frames.SIGN_UP)){
            refText = "Sign up";
        } else if (frame.equals(Frames.SIGN_IN)){
            refText = "Sign in";
        }
        ref = new NoBGButton(bundle.get().getString(refText), true).getButton();
        ref.addActionListener((e)->{
            switcher.switchFrame(frame);
        });
        contentRef.add(ref);
    }

    public void setLocaleChanger(LocaleChanger localeChanger){
        this.headerPanel.setLocaleChanger(localeChanger);
    }

}
