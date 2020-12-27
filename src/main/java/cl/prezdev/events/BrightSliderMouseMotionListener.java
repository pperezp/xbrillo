package cl.prezdev.events;

import cl.prezdev.model.Config;
import cl.prezdev.model.Terminal;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class BrightSliderMouseMotionListener extends MouseMotionAdapter {

    private final JLabel percentageLabel;
    private final JSlider brightSlider;
    private final int divider;

    public BrightSliderMouseMotionListener(JLabel percentageLabel, JSlider brightSlider, int divider){
        this.percentageLabel = percentageLabel;
        this.brightSlider = brightSlider;
        this.divider = divider;
    }

    public void mouseDragged(MouseEvent evt) {
        percentageLabel.setText((brightSlider.getValue() / divider)+ "%");

        float brightValue = ((float) brightSlider.getValue() / divider);

        try {
            Terminal.executeAsRoot("brillo -S " + brightValue , Config.ROOT_PASSWORD);
        } catch (Exception ex) {

        }
    }
}
