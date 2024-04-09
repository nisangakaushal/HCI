import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Login extends JDialog {
    private JTextField textFieldUserName;
    private JPasswordField textFieldPassword;
    private JButton loginButton;
    private JButton cancelButton;
    private JLabel labelUserName;
    private JLabel labelPassword;
    private JPanel loginPanel;
    private JButton registerButton;

    public boolean isAuthenticated = false;

    public Login() {
        setTitle("Borcelle Modern Furniture | Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450,474));
        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = textFieldUserName.getText();
                String password = new String(textFieldPassword.getPassword());
                List<User> userList =  UserList.getUserList();

                boolean authenticatedUser = false;
                for(User user : userList){
                    if(user.getUserName().equals(userName) || user.getPassword().equals(password)){
                        authenticatedUser = true;
                    }
                }
                if(authenticatedUser){
                    dispose();
                    isAuthenticated = true;
                    // Open the home page
                    openHomePage();
                }

                else{
                    JOptionPane.showMessageDialog(Login.this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
//                if (userName.equals("Sudharaka")) { // Use .equals() for string comparison
//                    isAuthenticated = true;
//                    dispose();
//                    // Open the home page
//                   openHomePage();
//                } else {
//                    JOptionPane.showMessageDialog(Login.this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
//                }

            }
        });




        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegisterPage();
            }
        });

        setVisible(true);
    }


    private void openHomePage() {
        dispose();
        Home homePage = new Home();
        homePage.setVisible(true);

         // Close the login dialog
    }

    private void openRegisterPage() {
//        dispose();
        Register register = new Register((JFrame)SwingUtilities.getWindowAncestor(SwingUtilities.getWindowAncestor(Login.this)));
        register.setVisible(true);

        // Close the login dialog
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame parentFrame = new JFrame();
            Login loginDialog = new Login();
            loginDialog.setVisible(true);
        });
    }


}
