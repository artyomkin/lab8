package clientPackage.view.frames;

import clientPackage.controller.*;
import clientPackage.controller.IO.QuerySender;
import clientPackage.controller.IO.ResponseReader;
import clientPackage.controller.listeners.CommandAction;
import clientPackage.controller.listeners.FilterAction;
import clientPackage.controller.listeners.LocaleChanger;
import clientPackage.exceptions.ConnectionException;
import clientPackage.view.buttons.GreenButton;
import clientPackage.view.buttons.NoBGButton;
import common.Instruction;
import common.Query;
import common.Stage;
import common.dataTransferObjects.CommandTransferObject;
import common.taskClasses.Flat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VisualFrame extends MainFrame implements Notifiable, Switchable, Authorizable, Observer {

    protected CommandAction commandAction;
    protected String login;
    protected String password;
    protected JTextField commandField;
    protected JButton submitCommand;
    protected JFrame frame;
    protected JPanel center;
    protected Map<String,Color> colors;
    protected HeaderPanel header;
    private QuerySender querySender;
    private ResponseReader responseReader;
    private ResponseHandler responseHandler;

    public VisualFrame(String heading, QuerySender querySender, ResponseReader responseReader, Switcher switcher, Notifier notifier){

        this.responseHandler = new ResponseHandler(querySender,responseReader,notifier,switcher);
        colors = new HashMap<>();
        this.querySender = querySender;
        this.responseReader = responseReader;
        frame = new JFrame(heading);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(0,0,730,630);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(35,110,35,110));

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
        JPanel topInner = new JPanel();
        top.setAlignmentX(Component.LEFT_ALIGNMENT);
        topInner.setAlignmentX(Component.LEFT_ALIGNMENT);
        topInner.setMaximumSize(new Dimension(250,30));
        topInner.setPreferredSize(new Dimension(250,30));
        GridLayout topInnerLayout = new GridLayout(1,2);
        topInnerLayout.setHgap(10);
        topInner.setLayout(topInnerLayout);
        JLabel headingLabel = new JLabel(heading);
        headingLabel.setFont(new Font("Arial", Font.PLAIN, 25));

        topInner.add(headingLabel);
        topInner.setBackground(Color.WHITE);
        top.setBackground(Color.WHITE);

        JButton refToTable = new NoBGButton("Table",true).getButton();
        refToTable.addActionListener((e)->switcher.switchFrame(Frames.TABLE));
        topInner.add(refToTable);
        top.add(topInner);

        top.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        content.add(top);

        center = new JPanel();
        center.setLayout(new FlowLayout());
        content.add(center);
        center.setBackground(Color.WHITE);

        fields = new String[]{"ID",
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

        JPanel botWrapper = new JPanel();
        GridLayout botWrapperLayout = new GridLayout(2,1);
        botWrapperLayout.setVgap(20);
        botWrapper.setLayout(botWrapperLayout);
        botWrapper.setPreferredSize(new Dimension(400,80));
        botWrapper.setMaximumSize(new Dimension(400,80));
        botWrapper.setBackground(Color.WHITE);
        botWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel filterPanel = new JPanel();
        GridLayout filterPanelLayout = new GridLayout(1,3);
        filterPanelLayout.setHgap(20);
        filterPanel.setLayout(filterPanelLayout);
        filterPanel.setMaximumSize(new Dimension(500,30));
        filterPanel.setPreferredSize(new Dimension(500,30));
        filterPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        filterPanel.setBackground(Color.WHITE);
        JComboBox comboBox = new JComboBox(fields);
        comboBox.setMaximumSize(new Dimension(130,30));
        JTextField filterField = new JTextField();
        filterField.setMaximumSize(new Dimension(130,30));
        filterField.setToolTipText("Enter value...");
        JButton submitFilter = new GreenButton("Submit",10,90);

        submitFilter.addActionListener(new FilterAction(notifier,this,filterField,comboBox));

        filterPanel.add(comboBox);
        filterPanel.add(filterField);
        filterPanel.add(submitFilter);

        botWrapper.add(filterPanel);

        JPanel commandPanel = new JPanel();
        GridLayout commandPanelLayout = new GridLayout(1,2);
        commandPanelLayout.setHgap(20);
        commandPanel.setLayout(commandPanelLayout);
        commandPanel.setMaximumSize(new Dimension(285,30));
        commandPanel.setPreferredSize(new Dimension(285,30));
        commandPanel.setBackground(Color.WHITE);
        commandPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        commandField = new JTextField();
        commandField.setMaximumSize(new Dimension(130,30));
        commandField.setToolTipText("Enter value...");
        submitCommand = new GreenButton("Submit", 10, 110);

        commandAction = new CommandAction(querySender,responseReader,commandField,switcher,notifier,this);
        submitCommand.addActionListener(commandAction);
        commandPanel.add(commandField);
        commandPanel.add(submitCommand);
        botWrapper.add(commandPanel);
        content.add(botWrapper);
        content.setBackground(Color.WHITE);
        frame.add(content);
        header = new HeaderPanel("");
        frame.add(header.getHeader(),BorderLayout.NORTH);
    }

    @Override
    public void update(Instruction instruction, Flat flat) {

        if (instruction.equals(Instruction.REMOVE_ELEMENT)){
            for (int i = 0; i<center.getComponents().length; i++){
                HouseGraphics hg = (HouseGraphics) center.getComponent(i);
                if (hg.getFlat().getID() == flat.getID()){
                    center.remove(i);
                    for (int j = 0; j<collection.size(); j++){
                        if(collection.get(j).getID() == flat.getID()){
                            collection.remove(j);
                        }
                    }
                    center.updateUI();
                    return;
                }
            }
        }
        else if (instruction.equals(Instruction.UPDATE_ELEMENT)){
            for (int i = 0; i<center.getComponents().length; i++){

                HouseGraphics hg = (HouseGraphics) center.getComponent(i);
                if (hg.getFlat().getID() == flat.getID()){
                    hg.setFlat(flat);

                    for (int j = 0; j<collection.size(); j++){
                        if (collection.get(j).getID() == flat.getID()){
                            collection.remove(j);
                            collection.add(j,flat);
                            break;
                        }
                    }
                    center.updateUI();
                    return;
                }
            }
            Color color;

            if (colors.containsKey(flat.getCreator())){
                color = colors.get(flat.getCreator());
            } else {
                color = new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
                colors.put(flat.getCreator(),color);
            }
            collection.add(flat);

            HouseGraphics hg = new HouseGraphics(flat,color,(flatInput->{
                Query query = new Query();
                query.setLogin(login);
                query.setPassword(password);
                query.setDTOCommand(new CommandTransferObject().setCommand("update").setArgument(((Integer)(flatInput.getID())).toString()));
                querySender.sendQuery(query);
                try {
                    responseHandler.handleResponse(responseReader.getResponse(),this,login,password);
                } catch (ConnectionException e) {
                    e.printStackTrace();
                }
            }));
            hg.setPreferredSize(new Dimension(110,100));
            center.add(hg);
            center.updateUI();

        }


    }

    @Override
    public void update(ArrayList<Flat> newData) {
        if(super.collection==null) super.collection = newData;
        center.removeAll();

        for (int i = 0; i<newData.size(); i++){
            Color color;
            if (colors.containsKey(newData.get(i).getCreator())){
                color = colors.get(newData.get(i).getCreator());
            } else {
                color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
                colors.put(newData.get(i).getCreator(),color);
            }

            HouseGraphics hg = new HouseGraphics(newData.get(i),color, (flatDTO->{
                Query query = new Query();
                query.setLogin(login);
                query.setPassword(password);
                query.setDTOCommand(new CommandTransferObject().setCommand("update").setArgument(((Integer)(flatDTO.getID())).toString()));
                querySender.sendQuery(query);
                try {
                    responseHandler.handleResponse(responseReader.getResponse(),this,login,password);
                } catch (ConnectionException e) {
                    e.printStackTrace();
                }
            }));
            hg.setPreferredSize(new Dimension(110,100));
            hg.setFlat(newData.get(i));
            center.add(hg);
        }

        center.updateUI();

    }

    @Override
    public void setLogin(String login) {
        this.login = login;
        commandAction.setLogin(login);
        frame.remove(header.getHeader());
        header = new HeaderPanel(login);
        header.exitButton.addActionListener((e)->{
            commandField.setText("exit");
            commandAction.actionPerformed(new ActionEvent(submitCommand,1,""));
        });
        header.getHeader().setBorder(BorderFactory.createEmptyBorder(20,30,55,30));
        frame.add(header.getHeader(),BorderLayout.NORTH);
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
        commandAction.setPassword(password);
    }

    @Override
    public void initData() {
        commandField.setText("show");
        commandAction.actionPerformed(new ActionEvent(submitCommand,ActionEvent.ACTION_FIRST,""));
        commandField.setText("");
    }

    @Override
    public void warn(String msg) {

    }

    @Override
    public void info(String msg) {

    }

    @Override
    public void setVisible(boolean b) {
        frame.setVisible(b);
    }

    public void setLocaleChanger(LocaleChanger localeChanger){
        this.header.setLocaleChanger(localeChanger);
    }
}
