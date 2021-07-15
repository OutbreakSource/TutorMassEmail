package gui;

import com.sun.org.apache.xpath.internal.operations.Mod;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaMailUtil {

    public static void sendMail(String recepient, String student, int reason, String subject, String messageE) throws MessagingException, IOException {
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
            Message message = Modular(session, account, recepient, student, subject, messageE);
            if (!(message == null)) {
                Transport.send(message);
            } else {
                //System.out.println(student + " did not send---------");
                CheckList.textArea.append("\n" + student + " did not send---------");
            }

        }
    }

    private static Message Modular(Session session, String account, String rec, String student,
                                   String subject, String emailMessage) {
        try {
            emailMessage = emailMessage.replace("[student]", student);
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
            CheckList.textArea.append("\n" + student + " - good to go!");
            CheckList.textPanel.update(CheckList.textPanel.getGraphics());
            CheckList.textArea.update(CheckList.textArea.getGraphics());
            //System.out.println(student + " - good to go!");
            return message;
        } catch (Exception ex){
            //Logger.getLogger(.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


}
