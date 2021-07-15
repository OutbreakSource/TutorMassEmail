import org.apache.commons.lang3.text.WordUtils;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CheckList extends JFrame {
    @Override
    public String toString() {
        return students.toString().replace(",", "\n");
    }

    List<String> students = new ArrayList<>();

    public static void main(String[] args) throws IOException, MessagingException {
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

    public CheckList() throws IOException, MessagingException {
        super("CheckList");
        Properties properties = new Properties();
        FileReader reader = new FileReader(".properties");
        properties.load(reader);
        Scanner sc = new Scanner(new File(properties.getProperty("both")));
        String lines = new File(properties.getProperty("both")).getAbsolutePath();

        String[] strs = new String[countLineJava8(lines)];

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
        final JList list = new JList(createData(strs));
        list.setCellRenderer(new CheckListRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setBorder(new EmptyBorder(0, 4, 0, 0));
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
                    if(item.toString().equals("11 SELECT ALL 11")){
                        students.addAll(Arrays.asList(strs));
                    }
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
        JScrollPane sp = new JScrollPane(list);

        //This is the small text area below the list of students
        final JTextArea textArea = new JTextArea(10, 10);
        JScrollPane textPanel = new JScrollPane(textArea);
        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
        System.setOut(printStream);
        System.setErr(printStream);

        JButton printButton = new JButton("Email Progress");
        int finalCount = count;
        printButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    runit(students, 0);
                    System.out.println("Finished All Selected");
                    System.out.println(finalCount);
                } catch (IOException | MessagingException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        JButton clearButton = new JButton("Check");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                System.out.println(students);
            }
        });
        JButton editButton = new JButton("Edit Email");
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    final JFrame frame = new JFrame();
                    frame.setTitle("Editing Email");
                    frame.setSize(300,300);
                    frame.setLocationRelativeTo(null);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    new Popup();

                    runit(students, 1);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (MessagingException messagingException) {
                    messagingException.printStackTrace();
                }
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(editButton);
        panel.add(printButton);
        panel.add(clearButton);
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

    public void runit(List<String> students, int reason) throws IOException, MessagingException {
        Properties properties = new Properties();
        FileReader reader = new FileReader(".properties");
        properties.load(reader);
        Scanner sc = new Scanner(new File(properties.getProperty("both")));
        sc.useDelimiter(",");

        while(sc.hasNext()){
            String line1 = sc.nextLine();
            line1 = line1.trim();
            String[] line = line1.split(",");
            if(line.length > 2){
                String refactorS = line[0] + " " + line[1];
                String email = line[2];
                /*
                email = email.replaceAll(" ", "");
                email = email.replace("\"","");
                email = email.trim();
                PARENTS EMAIL
                 */
                refactorS = refactorS.replace("\"","");
                refactorS = WordUtils.uncapitalize(refactorS);
                refactorS = WordUtils.capitalizeFully(refactorS);

                String formattedString = students.toString()
                        .replace(",", "")  //remove the commas
                        .replace("[", "")  //remove the right bracket
                        .replace("]", "")  .replace("  ", " ")//remove the left bracket
                        .trim();
                if(formattedString.contains(refactorS)){
                    //JavaMailUtil.sendMail("danieltutordps@gmail.com", refactorS, 0);
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

    private static Message Modular(Session session, String account, String rec, String student,
                                                 String subject, String emailMessage) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(account));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(rec));
            message.setSubject(subject);
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = " <H1>" + emailMessage + "</H1> " +
                    "<medium>testing<medium>" +
                    "<img src=\"cid:image\">";
            messageBodyPart.setContent(htmlText, "text/html");
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            System.out.println(student + " - good to go!");
            return message;
        } catch (Exception ex){
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
