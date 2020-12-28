package cl.prezdev;

import javax.swing.*;
import java.awt.*;

public class App {
    public static void main( String[] args ){
        JFrame mainFrame = new JFrame("XBrillo");

        Point point = MouseInfo.getPointerInfo().getLocation();

        mainFrame.setContentPane(new MainFrame().getContainerPanel());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocation(point.x, point.y);

        mainFrame.setVisible(true);
    }
}
