package clientPackage.view.frames;

import clientPackage.controller.*;
import clientPackage.controller.IO.QuerySender;
import clientPackage.controller.IO.ResponseReader;
import clientPackage.controller.listeners.CommandAction;
import clientPackage.controller.listeners.FilterAction;
import clientPackage.controller.listeners.LocaleChanger;
import clientPackage.view.buttons.GreenButton;
import clientPackage.view.buttons.NoBGButton;
import common.Instruction;
import common.taskClasses.Flat;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TableFrame extends MainFrame implements Notifiable, Switchable, Authorizable, Observer {
    protected JFrame frame;
    protected HeaderPanel header;
    protected JPanel top;
    protected JTable table;
    protected JPanel content;
    protected JTextField command;
    protected String heading;
    protected String login;
    protected Rectangle bounds;
    protected JLabel notification;
    protected JButton submitCommand;
    protected Switcher switcher;
    protected CommandAction commandAction;
    protected DefaultTableModel tableModel;

    public TableFrame(Notifier notifier, String heading, Rectangle bounds, QuerySender querySender, ResponseReader responseReader, Switcher switcher){

        this.heading = heading;
        this.bounds = bounds;
        frame = new JFrame(heading);
        frame.setBounds(bounds);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.switcher = switcher;

        JPanel topLeft = new JPanel();
        GridLayout topLeftLayout = new GridLayout(1,2);
        topLeftLayout.setHgap(20);
        topLeft.setBackground(Color.WHITE);
        topLeft.setPreferredSize(new Dimension(300,30));
        topLeft.setMaximumSize(new Dimension(300,30));
        topLeft.setLayout(topLeftLayout);
        JLabel headingLabel = new JLabel(heading);
        headingLabel.setFont(new Font("Arial", Font.PLAIN, 25));


        JButton visualize = new NoBGButton("Visualize",true).getButton();
        visualize.addActionListener((e)->switcher.switchFrame(Frames.VISUAL));

        topLeft.add(headingLabel);
        topLeft.add(visualize);

        super.fields = new String[]{"ID",
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

        JPanel filterPanel = new JPanel();
        GridLayout filterPanelLayout = new GridLayout(1,3);
        filterPanelLayout.setHgap(20);
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setLayout(filterPanelLayout);
        filterPanel.setPreferredSize(new Dimension(400,30));
        filterPanel.setMaximumSize(new Dimension(400,30));

        JComboBox filter = new JComboBox(fields);

        filter.setPreferredSize(new Dimension(160,30));
        filter.setMaximumSize(new Dimension(160,30));

        JTextField val = new JTextField();
        val.setToolTipText("Enter value...");
        val.setMaximumSize(new Dimension(160, 30));
        val.setPreferredSize(new Dimension(val.getMaximumSize().width, val.getMaximumSize().height));

        JButton submitFilter = new GreenButton("Submit",30,88);
        submitFilter.addActionListener(new FilterAction(notifier,this,val,filter));

        filterPanel.add(filter);
        filterPanel.add(val);
        filterPanel.add(submitFilter);

        top = new JPanel();
        top.setLayout(new BoxLayout(top,BoxLayout.X_AXIS));
        top.setBorder(BorderFactory.createEmptyBorder(0,110,40,110));
        top.add(topLeft);
        top.add(Box.createHorizontalGlue());
        top.add(filterPanel);

        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        table = new JTable(tableModel)
        {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row))
                    c.setBackground(row % 2 == 0 ? getBackground() : new Color(0,153,77,50));
                return c;
            }
        };
        for (String field : fields){
            tableModel.addColumn(field);
        }

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        table.setRowHeight(50);
        table.setGridColor(new Color(212,210,210));
        table.setIntercellSpacing(new Dimension(0,0));
        table.setShowVerticalLines(false);

        JPanel botWrapper = new JPanel();
        botWrapper.setLayout(new BorderLayout());
        botWrapper.setBackground(Color.WHITE);
        botWrapper.setPreferredSize(new Dimension(bounds.x,100));
        botWrapper.setMaximumSize(new Dimension(bounds.x,100));


        JPanel botPanel = new JPanel();
        GridLayout botPanelLayout = new GridLayout(1,3);
        botPanelLayout.setHgap(30);
        botPanel.setPreferredSize(new Dimension(400,30));
        botPanel.setMaximumSize(new Dimension(400,30));

        command = new JTextField();
        command.setMaximumSize(new Dimension(130,40));
        command.setPreferredSize(new Dimension(command.getMaximumSize().width,command.getMaximumSize().height));
        command.setToolTipText("Enter command...");

        submitCommand = new GreenButton("Submit",40,130);
        submitCommand.setSize(new Dimension(130,40));
        submitCommand.setPreferredSize(new Dimension(submitCommand.getMaximumSize().width,submitCommand.getMaximumSize().height));

        commandAction = new CommandAction(querySender,responseReader,command,switcher,switcher.getNotifier(),this);
        submitCommand.addActionListener(commandAction);

        notification = new JLabel("");

        botPanel.add(notification);
        botPanel.add(command);
        botPanel.add(submitCommand);
        botPanel.setBackground(Color.WHITE);
        notification.setBackground(Color.WHITE);
        botWrapper.add(botPanel, BorderLayout.EAST);
        botWrapper.setAlignmentX(Component.RIGHT_ALIGNMENT);
        botWrapper.setBorder(BorderFactory.createEmptyBorder(0,110,0,110));
        content = new JPanel();
        content.setLayout(new BorderLayout());
        top.setBackground(Color.WHITE);

        JScrollPane pane = new JScrollPane(table);
        Dimension d = new Dimension(bounds.width,200);
        pane.setPreferredSize(d);

        content.add(top,BorderLayout.NORTH);
        table.setBackground(Color.WHITE);
        content.add(pane,BorderLayout.CENTER);

        this.header = new HeaderPanel("");
        frame.add(header.getHeader(),BorderLayout.NORTH);

        frame.add(content);
        frame.add(botWrapper, BorderLayout.SOUTH);

    }

    public void setVisible(boolean b){
        frame.setVisible(b);
    }
    public void warn(String msg){
        notification.setForeground(Color.RED);
        notification.setText(msg);
        notification.updateUI();
    }
    public void info(String msg){
        notification.setForeground(Color.GREEN);
        notification.setText(msg);
        notification.updateUI();
    }
    public void setSwitcher(Switcher switcher){
        this.switcher = switcher;
    }
    public void setLogin(String login){
        commandAction.setLogin(login);
        frame.remove(header.getHeader());
        this.header = new HeaderPanel(login);
        header.exitButton.addActionListener((e)->{
            command.setText("exit");
            commandAction.actionPerformed(new ActionEvent(submitCommand,1,""));
        });

        header.getHeader().setBorder(BorderFactory.createEmptyBorder(20,30,55,30));
        frame.add(header.getHeader(),BorderLayout.NORTH);


    }
    public void setPassword(String password){
        commandAction.setPassword(password);
    }

    @Override
    public void update(Instruction instruction, Flat flat) {
        if (instruction.equals(Instruction.REMOVE_ELEMENT)){
            for (int i = 0; i<table.getRowCount(); i++){
                if(table.getValueAt(i,0).equals(((Integer)flat.getID()).toString())){
                    tableModel.removeRow(i);
                    table.updateUI();
                    for (int j = 0; j<collection.size(); j++){
                        if(collection.get(j).getID() == flat.getID()){
                            collection.remove(j);
                        }
                    }
                    return;
                }
            }

        }
        else if (instruction.equals(Instruction.UPDATE_ELEMENT)){
            for(int i = 0; i<table.getRowCount();i++){
                if(table.getValueAt(i,0).equals(((Integer)flat.getID()).toString())){
                    tableModel.removeRow(i);
                    tableModel.insertRow(i,flat.toArrayString());

                    table.updateUI();

                    for (int j = 0; j<collection.size(); j++){
                        if (collection.get(j).getID() == flat.getID()){
                            collection.remove(j);
                            collection.add(j,flat);
                            break;
                        }

                    }
                    return;
                }
            }
            collection.add(flat);
            tableModel.addRow(flat.toArrayString());
            table.updateUI();
        }
    }

    @Override
    public void update(ArrayList<Flat> newData){
        if (super.collection == null){
            super.collection = newData;
        }
        while(table.getRowCount()>0){
            tableModel.removeRow(0);
        }

        for (int i = 0; i<newData.size(); i++){
            tableModel.addRow(newData.get(i).toArrayString());
        }

    }
    @Override
    public void initData(){
        command.setText("show");
        ActionEvent e1 = new ActionEvent(command,
                ActionEvent.ACTION_FIRST,
                "command was pressed programmatically!");
        commandAction.actionPerformed(e1);
        command.setText("");
    }

    public void setLocaleChanger(LocaleChanger localeChanger){
        this.header.setLocaleChanger(localeChanger);
    }
}
