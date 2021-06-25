package clientPackage.view.frames;

import clientPackage.controller.Switcher;
import common.dataTransferObjects.FlatTransferObject;
import common.taskClasses.Flat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.function.Consumer;

public class HouseGraphics extends JPanel {
    private Color color;
    private Flat flat;
    int[] xRoofPoints = {6,53,101};
    int[] yRoofPoints = {38,5,38};
    int[] xPoints = {16,16,41,41,66,66,91,91};
    int[] yPoints = {43,78,78,53,53,78,78,43};
    int[] xChimney = {15,15,30};
    int[] yChimney = {22,7,7};

    public HouseGraphics(Flat flat, Color color, Consumer<FlatTransferObject> mouseClickAction){
        setBackground(Color.WHITE);
        this.color = color;
        this.flat = flat;
        this.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent me){
                mouseClickAction.accept(new FlatTransferObject(flat));
            }
        });
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        int nPoints = xPoints.length;
        g2D.setColor(color);
        g2D.setStroke(new BasicStroke(5));
        g2D.drawPolyline(xPoints,yPoints,nPoints);

        int nRoofPoints = xRoofPoints.length;

        g2D.drawPolyline(xRoofPoints,yRoofPoints,nRoofPoints);

        int roofPoints = xChimney.length;
        g2D.drawPolyline(xChimney,yChimney,roofPoints);

        this.setToolTipText("ID: " + flat.getID() + "; Name: " + flat.getName() + "; Creator:    " + flat.getCreator());

    }

    public Flat getFlat(){return this.flat;}

    public void setFlat(Flat flat){this.flat = flat;}

}
