package gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Popup
        extends JFrame
        implements ActionListener {

    // Components of the Form
    private Container c;
    private JLabel title;
    private JLabel name;
    private JTextField tname;
    private JLabel add;
    private JTextArea tadd;
    private JCheckBox term;
    private JButton sub;
    private JButton reset;
    private JTextArea tout;
    private JLabel res;
    private JTextArea resadd;

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
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        c.add(title);

        name = new JLabel("Subject");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(100, 20);
        name.setLocation(100, 100);
        c.add(name);

        tname = new JTextField();
        tname.setFont(new Font("Arial", Font.PLAIN, 15));
        tname.setSize(190, 20);
        tname.setLocation(200, 100);
        c.add(tname);

        add = new JLabel("Text Body");
        add.setFont(new Font("Arial", Font.PLAIN, 20));
        add.setSize(100, 20);
        add.setLocation(100, 140);
        c.add(add);

        tadd = new JTextArea();
        tadd.setFont(new Font("Arial", Font.PLAIN, 15));
        tadd.setSize(350, 200);
        tadd.setLocation(100, 180);
        tadd.setLineWrap(true);
        c.add(tadd);

        term = new JCheckBox("Good to go?");
        term.setFont(new Font("Arial", Font.PLAIN, 15));
        term.setSize(250, 20);
        term.setLocation(150, 400);
        c.add(term);

        sub = new JButton("Submit");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(100, 20);
        sub.setLocation(150, 450);
        sub.addActionListener(this);
        c.add(sub);

        reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(100, 20);
        reset.setLocation(270, 450);
        reset.addActionListener(this);
        c.add(reset);

        tout = new JTextArea();
        tout.setFont(new Font("Arial", Font.PLAIN, 15));
        tout.setSize(300, 400);
        tout.setLocation(500, 100);
        tout.setLineWrap(true);
        tout.setEditable(false);
        c.add(tout);

        res = new JLabel("");
        res.setFont(new Font("Arial", Font.PLAIN, 20));
        res.setSize(500, 25);
        res.setLocation(100, 500);
        c.add(res);

        resadd = new JTextArea();
        resadd.setFont(new Font("Arial", Font.PLAIN, 15));
        resadd.setSize(200, 75);
        resadd.setLocation(580, 175);
        resadd.setLineWrap(true);
        c.add(resadd);

        setVisible(true);
    }

    // method actionPerformed()
    // to get the action performed
    // by the user and act accordingly
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == sub) {
            if (term.isSelected()) {
                CheckList.message = tadd.getText();
                CheckList.subject = tname.getText();
                String data
                        = "Subject : "
                        + tname.getText() + "\n\n";

                String data3 = tadd.getText();
                tout.setText(data + data3);
                tout.setEditable(false);
                res.setText("Showing Preview");
            }
            else {
                tout.setText("");
                resadd.setText("");
                res.setText("Check everything?");
            }
        }

        else if (e.getSource() == reset) {
            String def = "";
            tname.setText(def);
            res.setText(def);
            resadd.setText(def);
            add.setText(def);
            tadd.setText(def);
            res.setText(def);
            tout.setText(def);
        }
    }
}

