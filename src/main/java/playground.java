import mailSystem.JavaMailUtil;
import org.apache.commons.lang3.text.WordUtils;
import javax.mail.MessagingException;
import java.io.*;
import java.util.Properties;
import java.util.Scanner;

/**
 *
 */
public class playground {

    public static void main(String[] args) throws MessagingException, IOException {
        Properties properties = new Properties();
        FileReader reader = new FileReader(".properties");
        properties.load(reader);
        String all = properties.getProperty("all");
        String test = properties.getProperty("test");
        String extra = properties.getProperty("extra");


        Scanner sc = new Scanner(new File(all));
        sc.useDelimiter(",");
        while(sc.hasNext()){
            String line1 = sc.nextLine();
            line1 = line1.trim();
            String[] line = line1.split(",");
            if(line.length >= 3){
                String refactorS = line[1] + " " + line[0];
                String refactorSS = line[0] + " " + line[1];

                /*
                String email = line[2];
                email = email.replaceAll(" ", "");
                email = email.replace("\"","");
                email = email.trim();
                 */

                refactorSS = refactorSS.replace("\"","");
                refactorSS = WordUtils.uncapitalize(refactorSS);
                refactorSS = WordUtils.capitalizeFully(refactorSS);

                refactorS = refactorS.replace("\"","");
                refactorS = WordUtils.uncapitalize(refactorS);
                refactorS = WordUtils.capitalizeFully(refactorS);

                String[] bud = {refactorSS,refactorS};
                JavaMailUtil.sendMail("daniel_ends@hotmail.com", bud, 3);
            }
        }
        JavaMailUtil.writer.close();
    }
}
