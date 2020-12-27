package cl.prezdev.model;

import cl.prezdev.exceptions.CancelEnterRootPasswordException;

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
        passwordField.requestFocus();
    }

    @Override
    public void ancestorRemoved(AncestorEvent ae) {
    }

    @Override
    public void ancestorMoved(AncestorEvent ae) {
        passwordField.requestFocus();
    }

    public String showPasswordDialog(String message) throws CancelEnterRootPasswordException {
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
            throw new CancelEnterRootPasswordException();
        }

        return new String(passwordField.getPassword());
    }

    private void init() {
        this.label = new JLabel();
        this.passwordField = new JPasswordField(10);

        this.add(label);
        this.add(passwordField);

        passwordField.addAncestorListener(this);
    }

}