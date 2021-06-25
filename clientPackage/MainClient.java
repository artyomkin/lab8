package clientPackage;

import clientPackage.view.frames.HouseGraphics;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;


public class MainClient {
    public static void main(String[] args) {
        new Client("127.0.0.1",2232).run();
    }
}
