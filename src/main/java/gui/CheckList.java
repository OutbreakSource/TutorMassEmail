package gui;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javax.swing.*;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import mailSystem.JavaMailUtil;
import org.apache.commons.lang3.text.WordUtils;
import javax.mail.MessagingException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

/**
 *
 */
public class CheckList extends JFrame {


    private JCheckBox all;
    public static String subject = "";
    public static String message = "";
    public static JTextArea textArea = new JTextArea(10, 10);

    public static JScrollPane textPanel = new JScrollPane(textArea);



    @Override
    public String toString() {
        return "CheckList{" +
                "students=\n" + students +
                '}';
    }

    List<String> students = new ArrayList<>(countLineJava8("allStudents.csv"));

    public static void main() throws IOException, MessagingException {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception evt) {}

        CheckList frame = new CheckList();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setSize(1600, 900);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

        frame.setVisible(true);
        frame.setTitle("Dandini");
    }

    /**
     *
     * @throws IOException
     * @throws MessagingException
     */
    public CheckList() throws IOException, MessagingException {
        super("sample.CheckList");
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 25));


        Scanner sc = new Scanner(new File("allStudents.csv"));
        String[] strs = new String[countLineJava8("allStudents.csv")];

        int count = 0;
        sc.useDelimiter(",");
        while(sc.hasNext()){
            String line1 = sc.nextLine();
            line1 = line1.trim();
            String[] line = line1.split(",");
            if(line.length > 2){
                String refactorS = line[1] + ", " + line[0];
                refactorS = refactorS.replace("\"","");
                refactorS = WordUtils.uncapitalize(refactorS);
                refactorS = WordUtils.capitalizeFully(refactorS);
                strs[count] = refactorS;
                count++;
            }
        }
        Arrays.sort(strs);
        JScrollPane sp = checklistThing(strs);
        textArea.setFont(new Font("New Times Roman", Font.PLAIN, 25));

        JButton printButton = new JButton("Email");
        int finalCount = count;
        printButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                textArea.append("Subject: " + subject);
                textArea.append("\n" + message);

                try {
                    int result = JOptionPane.showConfirmDialog(null,"Are you sure?", null,JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if(result == JOptionPane.NO_OPTION){
                        textArea.append("Will not send!");
                    }
                    if(result == JOptionPane.YES_OPTION){
                        if(message.equals("") || subject.equals("")){
                            JOptionPane.showMessageDialog(null, "EMPTY MESSAGE OR SUBJECT!");
                        }
                        else{
                            runit(students);
                        }
                    }
                    textArea.append("\nFinished All Selected");
                } catch (IOException | MessagingException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        JButton clearButton = new JButton("Check");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                textArea.append(CheckList.this.toString());
            }
        });
        JButton editButton = new JButton("Edit Email");
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Popup();
            }
        });
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));
        JComboBox comboBox = new JComboBox(strs);
        AutoCompleteDecorator.decorate(comboBox);
        comboBox.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        JButton add = new JButton("Add Selected");
        add.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        all = new JCheckBox("Select All");
        all.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        all.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(all.isSelected()){
                    students.addAll(Arrays.asList(strs));
                }
                if(!all.isSelected()){
                    students.removeAll(Arrays.asList(strs));
                }
            }
        });
        JButton cred = new JButton("Credit");
        cred.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Written by Daniel Martinez (7/15/21)\n" +
                        "Worked with Sliter and Opferman as a math tutor to create this system.\n" +
                        "Planning was a team effort");
            }
        });

        final String[] highlighted = {null};
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                highlighted[0] = comboBox.getSelectedItem().toString();
            }
        });
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!students.contains(highlighted[0])){
                    students.add(highlighted[0]);
                }
            }
        });

        JButton clear = new JButton("Clear Selected");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                students.removeAll(students);
                textArea.setText("");
                textArea.append(CheckList.this.toString());
            }
        });


        header.add(comboBox);
        header.add(add);
        header.add(all);
        add(header, BorderLayout.NORTH);
        JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.add(clear).setFont(new Font("Times New Roman", Font.PLAIN, 25));
        panel.add(editButton).setFont(new Font("Times New Roman", Font.PLAIN, 25));
        panel.add(printButton).setFont(new Font("Times New Roman", Font.PLAIN, 25));
        panel.add(clearButton).setFont(new Font("Times New Roman", Font.PLAIN, 25));
        panel.add(cred).setFont(new Font("Times New Roman", Font.PLAIN, 25));
        getContentPane().add(sp, BorderLayout.CENTER);
        getContentPane().add(panel, BorderLayout.EAST);
        getContentPane().add(textPanel, BorderLayout.SOUTH);
    }

    private CheckableItem[] createData(String[] strs) {
        int n = strs.length;
        CheckableItem[] items = new CheckableItem[n];
        for (int i = 0; i < n; i++) {
            items[i] = new CheckableItem(strs[i]);
        }
        return items;
    }

    class CheckableItem {
        private String str;
        private boolean isSelected;
        public CheckableItem(String str) {
            this.str = str;
            isSelected = false;
        }
        public void setSelected(boolean b) {
            isSelected = b;
        }
        public boolean isSelected() {
            return isSelected;
        }
        public String toString() {
            return str;
        }
    }

    class CheckListRenderer extends JCheckBox implements ListCellRenderer {
        public CheckListRenderer() {
            setBackground(UIManager.getColor("List.textBackground"));
            setForeground(UIManager.getColor("List.textForeground"));
        }
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
            setEnabled(list.isEnabled());
            setSelected(((CheckableItem) value).isSelected());
            setFont(list.getFont());
            setText(value.toString());
            return this;
        }
    }

    /**
     *
     * @param students
     * @throws IOException
     * @throws MessagingException
     */
    public void runit(List<String> students) throws IOException, MessagingException {
        Scanner sc = new Scanner(new File("allStudents.csv"));
        sc.useDelimiter(",");
        while(sc.hasNext()){
            String line1 = sc.nextLine();
            line1 = line1.trim();
            String[] line = line1.split(",");
            if(line.length >= 3){
                String refactorS = line[1] + " " + line[0]; //remote roster
                String refactorSS = line[0] + " " + line[1]; //for both csv, (in person)
                //remote classes are reversed in first name and last name, check line 26!!
                /*
                String email = line[2];
                email = email.replaceAll(" ", "");
                email = email.replace("\"","");
                email = email.trim();
                 */
                refactorSS = refactorSS.replace("\"","");
                refactorSS = refactorSS.replace("-", " ");
                refactorSS = WordUtils.uncapitalize(refactorSS);
                refactorSS = WordUtils.capitalizeFully(refactorSS);

                refactorS = refactorS.replace("\"","");
                refactorS = refactorS.replace("-", " ");
                refactorS = WordUtils.uncapitalize(refactorS);
                refactorS = WordUtils.capitalizeFully(refactorS);

                String[] bud = {refactorSS,refactorS};

                String formattedString = students.toString()
                        .replace(",", "")  //remove the commas
                        .replace("[", "")  //remove the right bracket
                        .replace("]", "")
                        .replace("  ", " ")
                        .replace("-", " ")//remove the left bracket
                        .trim();
                formattedString = WordUtils.uncapitalize(formattedString);
                formattedString = WordUtils.capitalizeFully(formattedString);

                if(formattedString.contains(bud[0]) || formattedString.contains(bud[1])){
                    JavaMailUtil.sendMail("danieltutordps@gmail.com", bud, 2);
                }
            }
        }
    }

    public static int countLineJava8(String fileName) {
        Path path = Paths.get(fileName);
        int lines = 0;
        try {
            lines = (int) Files.lines(path).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public JScrollPane checklistThing(String[] strs){
        JList list = new JList(createData(strs));
        CheckListRenderer checklist = new CheckListRenderer();
        checklist.setSize(60,60);
        list.setCellRenderer(checklist);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setBorder(new EmptyBorder(0, 4, 0, 0));
        list.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int index = list.locationToIndex(e.getPoint());
                CheckableItem item = (CheckableItem) list.getModel()
                        .getElementAt(index);
                item.setSelected(!item.isSelected());
                String student = item.toString();
                student = student.trim();
                String[] parse = student.split(",");
                if(parse.length>1){
                    String refact = parse[1] + " " + parse[0];
                    if(item.isSelected){
                        students.add(refact);
                    }
                    else{
                        students.remove(refact);
                    }
                }
                Rectangle rect = list.getCellBounds(index, index);
                list.repaint(rect);

            }
        });
        return new JScrollPane(list);
    }

    private void updateTextArea(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                textArea.append(text);
            }
        });
    }

    //Followings are The Methods that do the Redirect, you can simply Ignore them.
    private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                updateTextArea(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                updateTextArea(new String(b, off, len));
            }

            @Override
            public void write(byte[] b) throws IOException {
                write(b, 0, b.length);
            }
        };

        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }

}
