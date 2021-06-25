package clientPackage.view.frames;

import clientPackage.controller.Frames;
import clientPackage.controller.Notifier;
import clientPackage.controller.Switcher;
import clientPackage.controller.listeners.CancelAction;
import clientPackage.controller.listeners.LocaleChanger;
import clientPackage.controller.listeners.TransferAction;
import clientPackage.view.buttons.GreenButton;
import clientPackage.view.buttons.TransparentButton;
import common.Pair;
import common.dataTransferObjects.FlatTransferObject;
import common.taskClasses.Flat;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class CardFrame implements Notifiable, Switchable {

    private JPanel left;
    private ArrayList<JTextField> textFields;
    private ArrayList<JPanel> entries;
    private JButton submit;
    private JButton cancel;
    private CancelAction cancelAction;
    private TransferAction transferAction;
    private JLabel heading;
    private Notifier notifier;
    private HeaderPanel headerPanel;
    private JPanel leftHeading;

    private final String[] fields = {
            "ID",
            "Name",
            "X",
            "Y",
            "Creation date",
            "Area",
            "Number of rooms",
            "Price",
            "Living space",
            "Transport",
            "House name",
            "Year",
            "Floors",
            "Flats on floor",
            "Lifts",
            "Creator"
    };

    private JFrame frame;

    public CardFrame(Notifier notifier, Switcher switcher){
        this.notifier = notifier;

        headerPanel = new HeaderPanel("");
        JPanel header = headerPanel.getHeader();
        headerPanel.exitButton.addActionListener((e)->{ System.exit(0); });
        header.setBorder(BorderFactory.createEmptyBorder(20,20,0,20));
        frame = new JFrame();
        frame.add(header,BorderLayout.NORTH);
        JPanel main = new JPanel();
        main.setBorder(BorderFactory.createEmptyBorder(20,20,0,20));
        main.setLayout(new GridLayout(1,2));
        main.setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setBounds(0,0,960,930);
        left = new JPanel();
        left.setBackground(Color.WHITE);
        GridLayout leftGridLayout = new GridLayout(20,1);
        leftGridLayout.setVgap(10);
        left.setLayout(leftGridLayout);

        heading = new JLabel("Enter data for flat");
        leftHeading = new JPanel();
        leftHeading.setLayout(new BoxLayout(leftHeading,BoxLayout.X_AXIS));
        leftHeading.add(heading);
        leftHeading.add(Box.createHorizontalGlue());
        heading.setFont(new Font("Arial",Font.PLAIN,25));
        leftHeading.setBackground(Color.WHITE);

        left.add(leftHeading);
        textFields = new ArrayList<>();
        entries = new ArrayList<>();
        for(int i = 0; i<fields.length;i++){
            JPanel entry = new JPanel();
            entry.setLayout(new BoxLayout(entry, BoxLayout.X_AXIS));
            entry.setBackground(Color.WHITE);
            JLabel field = new JLabel(fields[i]);
            field.setPreferredSize(new Dimension(150,30));
            field.setMaximumSize(new Dimension(150,30));
            entry.add(field);
            entry.add(new JLabel(""));
            if(i!=0 && i!=4 && i!=15){
                JTextField textField = new JTextField();
                textField.setPreferredSize(new Dimension(200,50));
                textField.setMaximumSize(new Dimension(200,50));
                textField.setText("1");
                if(i == 9){
                    textField.setText("NONE");
                }
                entry.add(textField);
                textFields.add(textField);
            }
            entry.setAlignmentX(Component.LEFT_ALIGNMENT);

            entries.add(entry);
            left.add(entry);
        }
        JPanel buttons = new JPanel();
        GridLayout buttonsLayout = new GridLayout(1,2);
        buttonsLayout.setHgap(20);
        buttons.setMaximumSize(new Dimension(300,100));
        buttons.setPreferredSize(new Dimension(300,100));
        submit = new GreenButton("Submit",40,130);
        cancel = new TransparentButton("Cancel", 30, 100).getButton();
        cancelAction = new CancelAction(switcher);
        cancel.addActionListener(cancelAction);

        buttons.add(cancel);
        buttons.add(submit);
        buttons.setBackground(Color.WHITE);

        JPanel bot = new JPanel();
        bot.setBackground(Color.WHITE);
        bot.setLayout(new BoxLayout(bot,BoxLayout.X_AXIS));
        bot.setMaximumSize(new Dimension(300,100));
        bot.setPreferredSize(new Dimension(300,100));
        bot.add(buttons);
        left.add(bot);

        transferAction = new TransferAction(textFields,switcher,notifier);
        submit.addActionListener(transferAction);

        main.add(left,BorderLayout.WEST);
        frame.add(main);

    }

    @Override
    public void warn(String msg) {
        System.out.println(msg);
    }

    @Override
    public void info(String msg) {
        System.out.println(msg);
    }

    public void setVisible(Consumer<FlatTransferObject> consumer){
        transferAction.setConsumer(consumer);
        setVisible(true);
    }

    @Override
    public void setVisible(boolean b) {
        frame.setVisible(b);
    }

    public void setLocaleChanger(LocaleChanger localeChanger){
        this.headerPanel.setLocaleChanger(localeChanger);
    }
}
