import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JDialog  {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textFieldUserName;
    private JButton registerButton;
    private JPanel registerPanel;
    private JPasswordField passwordField;
    private JPasswordField passwordFieldConfirm;


    public Register(JFrame parent) {
        super(parent);
        setTitle("Register");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(650,474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                String userName = textFieldUserName.getText();
                String password = new String(passwordField.getPassword()) ;

                User newUser = new User(userName,password);
                UserList.addUser(newUser);

            }
        });
    }
}
