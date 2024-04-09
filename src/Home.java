import javax.swing.*;
import java.awt.*;

public class Home extends JDialog {
    private JPanel homePanel;
    private JButton addMoreDesignsButton;

    public Home() {
//        super(parent);
        setTitle("Home");
        setContentPane(homePanel);
        setMinimumSize(new Dimension(650,474));
        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        addMoreDesignsButton.addActionListener(e -> {
            dispose();
            new RoomDesigner();

        });
    }



    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame parentFrame = new JFrame();
//            Home homeDialog = new Home(parentFrame);
//            homeDialog.setVisible(true);
//        });
        SwingUtilities.invokeLater(Home::new);
    }
}
