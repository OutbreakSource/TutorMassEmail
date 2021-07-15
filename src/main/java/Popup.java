import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Popup {

    public static void main(String[] args) {
        new Popup();
    }

    public Popup() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        private JTextField Subject;
        private JTextArea textBody;
        String[] email = new String[2];

        public JTextField getSubject() {
            return Subject;
        }

        public JTextArea getTextBody() {
            return textBody;
        }

        public String[] getEmail() {
            return email;
        }

        public TestPane() {
            Subject = new JTextField(10);
            textBody = new JTextArea(6, 30);
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(4, 4, 4, 4);
            gbc.anchor = GridBagConstraints.WEST;
            add(new JLabel("Subject:"), gbc);
            gbc.gridx++;
            add(Subject, gbc);
            gbc.gridx++;

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.anchor = GridBagConstraints.NORTHWEST;
            add(new JLabel("TextBody"), gbc);

            gbc.gridx++;
            gbc.gridwidth = 3;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(new JScrollPane(textBody), gbc);
            //email[1] = textBody.getText();
            //email[0] = Subject.getText();

            System.out.println(getTextBody());

        }



    }

}