import org.apache.commons.lang3.text.WordUtils;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class playground {
    public static void main(String[] args) throws MessagingException, IOException {
        int count = 0;
        Properties properties = new Properties();
        FileReader reader = new FileReader(".properties");
        properties.load(reader);

        String OpferR = properties.getProperty("OpferR");
        String SlitR = properties.getProperty("SlitR");
        String GroupProgress = properties.getProperty("GroupProgress");
        String GroupProgressRemote = properties.getProperty("GroupProgressRemote");
        String ChartsRemote = properties.getProperty("ChartsRemote");
        String Charts = properties.getProperty("Charts");
        String outputFile = properties.getProperty("path");
        String GPCSV = properties.getProperty("GPCSV");
        String GPCSVR = properties.getProperty("GPCSVR");
        String both = properties.getProperty("both");
        String all = properties.getProperty("all");
        String test = properties.getProperty("test");
        String extra = properties.getProperty("extra");




        //JavaMailUtil.sendMail("daniel_ends@yahoo.com", "Daniel Martinez", 0);
        //JavaMailUtil.sendMail("dmart315@msudenver.edu", "Daniel Martinez", 0);
        //JavaMailUtil.sendMail("danieltutordps@gmail.com", "Daniel Martinez", 1);
        Scanner sc = new Scanner(new File(test));
        sc.useDelimiter(",");
        while(sc.hasNext()){
            String line1 = sc.nextLine();
            line1 = line1.trim();
            String[] line = line1.split(",");
            if(line.length >= 3){
                String refactorS = line[1] + " " + line[0]; //remote roster
                String refactorSS = line[0] + " " + line[1]; //for both csv, (in person)
                //remote classes are reversed in first name and last name, check line 26!!

                String email = line[2];
                email = email.replaceAll(" ", "");
                email = email.replace("\"","");
                email = email.trim();

                refactorSS = refactorSS.replace("\"","");
                refactorSS = WordUtils.uncapitalize(refactorSS);
                refactorSS = WordUtils.capitalizeFully(refactorSS);

                refactorS = refactorS.replace("\"","");
                refactorS = WordUtils.uncapitalize(refactorS);
                refactorS = WordUtils.capitalizeFully(refactorS);

                String[] bud = {refactorSS,refactorS};
                JavaMailUtil.sendMail(email, bud, 3);
                //count++;
            }
        }
        //System.out.println(count);
    }
}
