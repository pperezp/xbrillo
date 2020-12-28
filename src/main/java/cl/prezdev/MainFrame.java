package cl.prezdev;

import cl.prezdev.events.BrightSliderMouseMotionListener;
import cl.prezdev.services.BrightService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame {
    private JPanel containerPanel;
    private JSlider brightSlider;
    private JLabel percentageLabel;

    private BrightService brightService;

    public MainFrame(){
        brightService = new BrightService(brightSlider, percentageLabel);
        brightService.init();

        brightSlider.addMouseMotionListener(
                new BrightSliderMouseMotionListener(percentageLabel, brightSlider));

        percentageLabel.setBorder(new EmptyBorder(0, 0, 0, 15));
        brightSlider.setBorder(new EmptyBorder(0, 15, 0, 0));
    }

    public Container getContainerPanel() {
        return this.containerPanel;
    }
}
