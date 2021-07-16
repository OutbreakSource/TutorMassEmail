package mailSystem;

import gui.CheckList;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;


/**
 *
 */
public class JavaMailUtil {

    public static PrintWriter writer;

    static {
        try {
            writer = new PrintWriter("non-sent.txt", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param recepient
     * @param student
     * @param reason
     * @throws MessagingException
     * @throws IOException
     */
    public static void sendMail(String recepient, String[] student, int reason) throws MessagingException, IOException {
        String subject1 = CheckList.subject;
        String message1 = CheckList.message;

        Properties properties = new Properties();
        FileReader reader = new FileReader(".properties");
        properties.load(reader);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        String account = properties.getProperty("user");
        String pass = properties.getProperty("pass");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(account, pass);
            }
        });

        if (reason == 0) {
            Message message = prepareMessageProgress(session, account, recepient, student);
            if (!(message == null)) {
                Transport.send(message);
            } else {
                writer.println(student[0]);
                System.out.println();
            }
        } else if (reason == 2) {
            Message message = Modular(session, account, recepient, student, subject1, message1);
            if (!(message == null)) {
                Transport.send(message);
            } else {
                writer.println(student[0]);
                System.out.println();
            }
        } else if (reason == 3) {
            Message message = prepareMessageProgress1Image(session, account, recepient, student);
            if (!(message == null)) {
                Transport.send(message);
            } else {
                writer.println(student[0]);
                System.out.println();
            }
        }
    }


    /**
     *
     * @param session
     * @param account
     * @param rec
     * @param student
     * @return
     */
    private static Message prepareMessageProgress(Session session, String account, String rec, String[] student) {
        try {
            int flag = 0;
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(account));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(rec));
            message.setSubject("RSS Progress for " + student[0] + "!");
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = " <H1>Both images attached show " + student[0] + "'s progression from last week!" + "</H1> <p>Progress Reports from 6/30-7/7\n-Regional Summer School</p>" +
                    "<strong>Red bar is completion of course and Blue bar is the grade they have in it!<strong>\n" +
                    "<strong>Contact outbreakguy1@gmail.com for any questions or information<strong>";
            messageBodyPart.setContent(htmlText, "text/html");
            multipart.addBodyPart(messageBodyPart);
            Properties properties = new Properties();
            FileReader reader = new FileReader(".properties");
            properties.load(reader);

            try{
                String path = "";
                String path2 = "";
                if(fileFinder(student, properties.getProperty("path2")).equals("")){
                    System.out.println(Arrays.toString(student) + " image is null");
                }
                else{
                    path = fileFinder(student, properties.getProperty("path2"));
                    imgUpload(multipart, path);
                    flag++;
                    System.out.println(Arrays.toString(student) + " - Good to go!");
                }
                if(fileFinder(student, properties.getProperty("path1")).equals("")){
                    System.out.println(Arrays.toString(student) + " image is null");
                }
                else{
                    path2 = fileFinder(student, properties.getProperty("path2"));
                    imgUpload(multipart, path2);
                    flag++;
                    System.out.println(Arrays.toString(student) + " - Good to go!");
                }

            } catch (Exception e) {
                System.out.println("Something went wrong");
            }
            message.setContent(multipart);
            if (flag > 1) {
                return message;
            }
        } catch (Exception ex) {
            System.out.println("Message wasn't sent");
        }
        return null;
    }

    /**
     *
     * @param multipart
     * @param fileName
     * @throws MessagingException
     * @throws IOException
     */
    public static void imgUpload(Multipart multipart, String fileName) throws MessagingException, IOException {
        Random ron = new Random();
        int no = ron.nextInt();
        String cid = Integer.toString(no);
        MimeBodyPart textPart = new MimeBodyPart();
        MimeBodyPart imagePart = new MimeBodyPart();
        imagePart.attachFile(fileName);
        imagePart.setContentID("<" + cid + ">");
        imagePart.setDisposition(MimeBodyPart.INLINE);
        multipart.addBodyPart(imagePart);
    }

    public static String fileFinder(String[] student, String checkerFile) {
        File dir = new File(checkerFile);
        File[] list = dir.listFiles();
        String path = "";
        for (File file : list) {
            String checker = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("file") + 3);

            for (int i = 0; i < 2; i++){
                checker = checker.toLowerCase();
                student[i] = student[i].toLowerCase();

                checker = checker.replaceAll(",", "").trim();
                checker = checker.replaceAll("-", " ").trim();
                student[i] = student[i].replaceAll("-", " ").trim();
                student[i] = student[i].replaceAll("  ", " ").trim();
                checker = checker.replaceAll("  ", " ").trim();
                student[i] = student[i].replaceAll("\\.", " ").trim();
                checker = checker.replaceAll("\\.", " ").trim();
            }
            MyMan function = new MyMan();
            if (checker.contains(student[0]) || student[0].contains(checker) || checker.contains(student[1]) || student[1].contains(checker) || function.myMan(student,checker)) {
                path = file.getAbsolutePath();
                break;
            }
        }
        return path;
    }

    private static Message prepareMessageProgress1Image(Session session, String account, String rec, String[] student) {
        try {
            int flag = 0;
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(account));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(rec));
            message.setSubject("RSS Progress for " + student[0] + "! FINAL WEEK");
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = " <H1>images attached show " + student[0] + "'s progression from today (LAST WEEK)!" + "</H1> <p>-Regional Summer School</p>" +
                    "<strong>Red bar is completion of course and Blue bar is the grade they have in it!<strong>\n" +
                    "<br><strong>Contact outbreakguy1@gmail.com for more information or questions. Have a good summer!<strong>";
            messageBodyPart.setContent(htmlText, "text/html");
            multipart.addBodyPart(messageBodyPart);
            Properties properties = new Properties();
            FileReader reader = new FileReader(".properties");
            properties.load(reader);

            String path = fileFinder(student, "C:\\Users\\Daniel Martinez\\IdeaProjects\\MassEmailDPS\\outputFile\\");

            if (!path.equals("")) {
                flag++;
                imgUpload(multipart, path);
                System.out.println(Arrays.toString(student) + " - Good to go!");
                System.out.println();

            } else {
                System.out.println(Arrays.toString(student) + " - ERROR ------------------------");
                System.out.println();
            }
            message.setContent(multipart);
            if (flag > 0) {
                return message;
            }
        } catch (Exception ex) {
            System.out.println("Message wasn't sent");
        }
        return null;
    }
    private static Message Modular(Session session, String account, String rec, String[] student,
                                   String subject, String emailMessage) {
        try {
            emailMessage = emailMessage.replace("[student]", student[0]);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(account));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(rec));
            message.setSubject(subject);
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText =
                    "<medium>" + emailMessage + "<medium>" +
                            "<img src=\"cid:image\">";
            messageBodyPart.setContent(htmlText, "text/html");
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            CheckList.textArea.append("\n" + Arrays.toString(student) + " - good to go!");
            CheckList.textPanel.update(CheckList.textPanel.getGraphics());
            CheckList.textArea.update(CheckList.textArea.getGraphics());
            //System.out.println(student + " - good to go!");
            return message;
        } catch (Exception ex){
            System.out.println("Message wasn't sent");
        }
        return null;
    }

}
