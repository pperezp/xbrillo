package cl.prezdev.events;

import cl.prezdev.model.Config;
import cl.prezdev.model.Terminal;
import cl.prezdev.services.BrightService;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class BrightSliderMouseMotionListener extends MouseMotionAdapter {

    private final JLabel percentageLabel;
    private final JSlider brightSlider;

    public BrightSliderMouseMotionListener(JLabel percentageLabel, JSlider brightSlider){
        this.percentageLabel = percentageLabel;
        this.brightSlider = brightSlider;
    }

    public void mouseDragged(MouseEvent evt) {
        percentageLabel.setText((brightSlider.getValue() / BrightService.DIVIDER)+ "%");

        float brightValue = ((float) brightSlider.getValue() / BrightService.DIVIDER);

        try {
            Terminal.executeAsRoot("brillo -S " + brightValue , Config.ROOT_PASSWORD);
        } catch (Exception ex) {}
    }
}
