package xbrillo.model;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class PasswordInputPanel extends JPanel implements AncestorListener{
    private JLabel label;
    private JPasswordField passwordField;
    
    public PasswordInputPanel(){
        init();
    }

    @Override
    public void ancestorAdded(AncestorEvent ae) {
    }

    @Override
    public void ancestorRemoved(AncestorEvent ae) {
    }

    @Override
    public void ancestorMoved(AncestorEvent ae) {
        System.out.println("MOVED");
        passwordField.requestFocus();
    }
    
    public char[] showPasswordDialog(String message){
        label.setText(message);
        passwordField.selectAll();
        
        int option = JOptionPane.showOptionDialog(
                null,
                this,
                null,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null, null, null
        );
        
        if (option == JOptionPane.CANCEL_OPTION || 
                option == JOptionPane.CLOSED_OPTION) {
            return null;
        } else {
            return passwordField.getPassword();
        }
    }

    private void init() {
        this.label = new JLabel();
        this.passwordField = new JPasswordField(10);
        
        this.add(label);
        this.add(passwordField);
        
        passwordField.addAncestorListener(this);
    }
    
}
