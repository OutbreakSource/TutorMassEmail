package gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 */
class Popup
        extends JFrame
        implements ActionListener {

    // Components of the Form
    private Container c;
    private JLabel title;
    private JLabel subjectL;
    private JTextField subjectT;
    private JLabel messageL;
    private JTextArea messageT;
    private JCheckBox term;
    private JButton sub;
    private JButton reset;
    private JTextArea tout;
    private JLabel res;
    private JTextArea resMe;

    public String subject = "";
    public String messsage = "";


    public String getSubject() {
        return subject;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    // constructor, to initialize the components
    // with default values.
    public Popup()
    {
        setTitle("Email Editor");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Editing Email");
        title.setFont(new Font("New Times Roman", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        c.add(title);

        subjectL = new JLabel("Subject");
        subjectL.setFont(new Font("New Times Roman", Font.PLAIN, 20));
        subjectL.setSize(100, 20);
        subjectL.setLocation(100, 100);
        c.add(subjectL);

        subjectT = new JTextField();
        subjectT.setFont(new Font("New Times Roman", Font.PLAIN, 15));
        subjectT.setSize(190, 20);
        subjectT.setLocation(200, 100);
        c.add(subjectT);

        messageL = new JLabel("Text Body");
        messageL.setFont(new Font("New Times Roman", Font.PLAIN, 20));
        messageL.setSize(100, 20);
        messageL.setLocation(100, 140);
        c.add(messageL);

        messageT = new JTextArea();
        messageT.setFont(new Font("New Times Roman", Font.PLAIN, 15));
        messageT.setSize(350, 200);
        messageT.setLocation(100, 180);
        messageT.setLineWrap(true);
        c.add(messageT);

        term = new JCheckBox("Good to go?");
        term.setFont(new Font("New Times Roman", Font.PLAIN, 15));
        term.setSize(250, 20);
        term.setLocation(150, 400);
        c.add(term);

        sub = new JButton("Submit");
        sub.setFont(new Font("New Times Roman", Font.PLAIN, 15));
        sub.setSize(100, 20);
        sub.setLocation(150, 450);
        sub.addActionListener(this);
        c.add(sub);

        reset = new JButton("Reset");
        reset.setFont(new Font("New Times Roman", Font.PLAIN, 15));
        reset.setSize(100, 20);
        reset.setLocation(270, 450);
        reset.addActionListener(this);
        c.add(reset);

        tout = new JTextArea();
        tout.setFont(new Font("New Times Roman", Font.PLAIN, 15));
        tout.setSize(300, 400);
        tout.setLocation(500, 100);
        tout.setLineWrap(true);
        tout.setEditable(false);
        c.add(tout);

        res = new JLabel("");
        res.setFont(new Font("New Times Roman", Font.PLAIN, 20));
        res.setSize(500, 25);
        res.setLocation(100, 500);
        c.add(res);

        resMe = new JTextArea();
        resMe.setFont(new Font("New Times Roman", Font.PLAIN, 15));
        resMe.setSize(200, 75);
        resMe.setLocation(580, 175);
        resMe.setLineWrap(true);
        c.add(resMe);

        setVisible(true);
    }

    /**
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == sub) {
            if (term.isSelected()) {
                CheckList.message = messageT.getText();
                CheckList.subject = subjectT.getText();
                String data
                        = "Subject : "
                        + subjectT.getText() + "\n\n";

                String data3 = messageT.getText();
                tout.setText(data + data3);
                tout.setEditable(false);
                res.setText("Showing Preview");
            }
            else {
                tout.setText("");
                resMe.setText("");
                res.setText("RESET");
            }
        }

        else if (e.getSource() == reset) {
            String def = "";
            subjectT.setText(def);
            res.setText(def);
            resMe.setText(def);
            messageL.setText(def);
            messageT.setText(def);
            res.setText(def);
            tout.setText(def);
        }
    }
}

