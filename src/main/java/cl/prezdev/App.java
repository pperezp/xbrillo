package cl.prezdev;

import javax.swing.*;

public class App {
    public static void main( String[] args ){
        JFrame mainFrame = new JFrame("Main Frame");

        mainFrame.setContentPane(new MainFrame().getContainerPanel());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        //mainFrame.setBounds(mainFrame.getX(), mainFrame.getY(), 400, 80);
        // mainFrame.setAlwaysOnTop(true);
        mainFrame.setVisible(true);
    }
}
