import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Separator {

    public static void main(String[] args) throws IOException {
        //remoteOrGroup("C:\\Users\\danie\\IdeaProjects\\MassEmailDPS\\GroupProgress for both sessions Week 2\\GroupProgressinPersonWed2ndWeek.csv",
               // "Student_Progress_Inperson_Week2";
        //inPerson();
        //remoteOrGroup("C:\\Users\\Daniel Martinez\\IdeaProjects\\MassEmailDPS\\Remote 2 Site & Teacher Rosters - Sliter.csv", "Sliter Remote");
        everything("testingbb");
    }

    private static void regex(List<String[]> dataSL, String[] values) {
        if(values[7].matches(".*\\d.*") || values[8].matches(".*\\d.*")){
            if(values[values.length-1].contains("@")){
                dataSL.add(new String[] {values[7].trim(), values[6].trim(), values[values.length-1].trim()});
            }
        }
        else{
            if(values[values.length-1].contains("@")){
                dataSL.add(new String[] {values[8].trim(), values[7].trim(), values[values.length-1].trim()});
            }
        }
    }

    private static void regexRemote(List<String[]> dataSL, String[] values) {
        if(values[0].matches(".*\\d.*") || values[1].matches(".*\\d.*")){
            if(values[values.length-1].contains("@")){
                dataSL.add(new String[] {values[0].trim(), values[1].trim(), values[values.length-1].trim()});
            }
        }
        else{
            if(values[values.length-1].contains("@")){
                dataSL.add(new String[] {values[0].trim(), values[1].trim(), values[values.length-1].trim()});
            }
        }
    }

    public static void writeDataAtOnce(List<String[]> data, String name) throws IOException {
        try {
            FileWriter outfile = new FileWriter(name);
            CSVWriter writer = new CSVWriter(outfile);
            writer.writeAll(data);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void inPerson() throws IOException {
        List<String[]> dataOP = new ArrayList<>();
        List<String[]> dataSL = new ArrayList<>();
        Scanner sc = new Scanner(new File("DMLK - Master - Sheet2.csv"));
        sc.useDelimiter(",");
        while (sc.hasNext()){
            String line = sc.nextLine();
            String[] values = line.split(",");
            if(values.length >= 19) {
                if (values[18].contains("Opferman") || values[17].contains("Opferman") || values[19].contains("Opferman")){
                    regex(dataOP, values);
                }
                else if (values[18].contains("Sliter") || values[17].contains("Sliter") || values[19].contains("Sliter")){
                    regex(dataSL, values);
                }
            }
        }
        sc.close();
        //writeDataAtOnce(dataOP, "test");


        System.out.println("SLITER");
        for (String[] strings : dataSL) {
            System.out.println(Arrays.toString(strings));
        }
        System.out.println("-------------------------------------------------\nOPFERMAN");
        for (String[] strings : dataOP) {
            System.out.println(Arrays.toString(strings));
        }

        writeDataAtOnce(dataOP, "Opferman");
        writeDataAtOnce(dataSL, "Sliter");
    }

    public static void remoteOrGroup(String path, String name) throws IOException {
        List<String[]> dataOP = new ArrayList<>();
        List<String[]> dataSL = new ArrayList<>();
        Scanner sc = new Scanner(new File(path));
        sc.useDelimiter(",");
        while (sc.hasNext()){
            String line = sc.nextLine();
            String[] values = line.split(",");
            if(values.length >= 15) {
                regexRemote(dataSL, values);
            }
        }
        sc.close();
        //writeDataAtOnce(dataOP, "test");


        System.out.println("SLITER");
        for (String[] strings : dataSL) {
            System.out.println(Arrays.toString(strings));
        }
        System.out.println("-------------------------------------------------\nOPFERMAN");
        for (String[] strings : dataOP) {
            System.out.println(Arrays.toString(strings));
        }

        writeDataAtOnce(dataSL, name);
    }


    public static void everything(String title) throws IOException {
        List<String[]> data = new ArrayList<>();


        Scanner sc = new Scanner(new File("C:\\Users\\Daniel Martinez\\IdeaProjects\\MassEmailDPS\\Remote 2 Site & Teacher Rosters - Opferman.csv"));
        sc.useDelimiter(",");
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] values = line.split(",");
            if (values.length >= 15) {
                regexRemote(data, values);
            }
        }
        Scanner scc = new Scanner(new File("C:\\Users\\Daniel Martinez\\IdeaProjects\\MassEmailDPS\\Remote 2 Site & Teacher Rosters - Sliter.csv"));
        scc.useDelimiter(",");
        while (scc.hasNext()) {
            String line = scc.nextLine();
            String[] values = line.split(",");
            if (values.length >= 15) {
                regexRemote(data, values);
            }
        }
        Scanner sccc = new Scanner(new File("C:\\Users\\Daniel Martinez\\IdeaProjects\\MassEmailDPS\\DMLK - Master - Sheet1.csv"));
        sccc.useDelimiter(",");
        while (sccc.hasNext()) {
            String line = sccc.nextLine();
            String[] values = line.split(",");
            if (values.length >= 19) {
                if (values[18].contains("Opferman") || values[17].contains("Opferman") || values[19].contains("Opferman")) {
                    regex(data, values);
                } else if (values[18].contains("Sliter") || values[17].contains("Sliter") || values[19].contains("Sliter")) {
                    regex(data, values);
                }
            }


        }
        sc.close();
        scc.close();
        sccc.close();
        writeDataAtOnce(data, title);
    }
}
