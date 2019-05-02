package xbrillo.gui;

import java.awt.MouseInfo;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import xbrillo.model.PasswordInputPanel;
import xbrillo.model.Terminal;

public class App extends javax.swing.JFrame {

    private final int MAXIMO = 100000;
    private final int DIVISOR = 1000;
    private Properties config;
    
    private final PasswordInputPanel passwordInputPanel;

    public App() {
        initComponents();

        passwordInputPanel = new PasswordInputPanel();
        init();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        brilloSlider = new javax.swing.JSlider();
        lblPorcentaje = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        brilloSlider.setMaximum(10000);
        brilloSlider.setValue(100);
        brilloSlider.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                brilloSliderMouseDragged(evt);
            }
        });

        lblPorcentaje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPorcentaje.setText("100%");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(brilloSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPorcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPorcentaje)
                    .addComponent(brilloSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void brilloSliderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brilloSliderMouseDragged
        lblPorcentaje.setText((brilloSlider.getValue() / DIVISOR)+ "%");

        float brillo = ((float) brilloSlider.getValue() / DIVISOR);

        try {
            Terminal.execute("brillo -S " + brillo);
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_brilloSliderMouseDragged

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            new App().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSlider brilloSlider;
    private javax.swing.JLabel lblPorcentaje;
    // End of variables declaration//GEN-END:variables

    private void init() {
        brilloSlider.setMaximum(MAXIMO);
        config = new Properties();
        
        try {
            config.load(new FileInputStream("config.properties"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (true) {
            try {
                //char[] pass = passwordInputPanel.showPasswordDialog("Ingrese la contraseña de root: ");
                
               // if(pass != null){
                    Terminal.ROOT_PASSWORD = config.getProperty("rootPassword");
                    
                    String commandOutput = Terminal.execute("brillo -G").trim();
                    Float brilloActual = Float.parseFloat(commandOutput);
                    
                    int value = (int)(brilloActual * DIVISOR);
                    
                    brilloSlider.setValue(value);
                    lblPorcentaje.setText((brilloSlider.getValue() / DIVISOR)+ "%");

                    break;
               // }else{
                    // Cuando el usuario apreta "Cancelar" en el ingreso
                    // de la contraseña
                    //System.exit(0);
                //}
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
        
        Point p = MouseInfo.getPointerInfo().getLocation();
        this.setLocation(p.x,p.y);
    }

}
